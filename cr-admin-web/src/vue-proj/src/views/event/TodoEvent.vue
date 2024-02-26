<template>
  <div class="todo-list">
    <el-header>
      <h1>待办事件列表</h1>

      <el-tabs
        class="search-tabs"
        v-model="showType"
        @tab-click="changeShowTypeHandler"
      >
        <el-tab-pane label="全部事件" name="all"></el-tab-pane>
        <el-tab-pane label="未办事件" name="todo"></el-tab-pane>
        <el-tab-pane label="已办事件" name="done"></el-tab-pane>
        <el-tab-pane label="超时事件" name="overdue"></el-tab-pane>
        <el-tab-pane label="被督办事件" name="supervised"></el-tab-pane>
      </el-tabs>
    </el-header>

    <el-main>
      <!-- 根据展示类型过滤事件列表 -->
      <el-card
        v-for="event in todoEventList"
        :key="event.id"
        class="event-card"
      >
        <!-- 标题-事件类型？ -->
        <div class="event-type-state-container">
          <span class="event-type">{{ eventTypes[event.type] }}</span>
          <el-button class="event-state" :type="getButtonType(event)" round>{{
            getButtonContent(event)
          }}</el-button>
        </div>
        <!-- 事件内容-报销单名字？ -->
        <div class="event-description">事件内容：{{ event.description }}</div>
        <div class="event-date-container">
          <span class="event-createTime">
            发起日期：{{ event.createTime }}
          </span>
          <span
            class="event-expectTime"
            :class="{ 'none-display': event.state === 1 }"
          >
            剩余天数：{{ getDiffDay(event.expectTime) }}天
          </span>
          <span
            class="event-finishTime"
            :class="{ 'none-display': !(event.state === 1) }"
          >
            完成日期：{{ event.finishTime }}
          </span>
        </div>
        <el-button
          class="event-redirect-button"
          type="primary"
          :class="{ 'none-display': event.state === 1 }"
          @click="eventRedirectBtnHandler(event)"
          >前往处理</el-button
        >
      </el-card>
    </el-main>

    <!-- 分页信息 -->
    <el-footer class="footer-container">
      <div class="footer-pagination">
        <el-pagination
          :current-page="currentPage"
          :page-sizes="[5, 10, 20, 50, 100]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        >
        </el-pagination>
      </div>
    </el-footer>
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentPage: null, // 当前页号
      total: null, // 总记录条数
      pageSize: 20, // 一页的数量
      todoEventList: [
        {
          id: 1,
          title: "事件1",
          status: "todo",
          createTime: "2024-01-25",
          expectTime: "2024-01-30",
          state: 0,
          type: 1,
          description: "报销单222-业务审批-牛逼",
        },
        {
          id: 2,
          title: "事件2",
          status: "done",
          createTime: "2024-01-25",
          expectTime: "2024-01-10",
          state: 1,
          type: 1,
          description: "报销单222-出纳付款-牛逼",
          finishTime: "2024-01-28",
        },
        {
          id: 3,
          title: "事件3",
          status: "overdue",
          createTime: "2024-01-25",
          dueDate: "2024-01-05",
          state: 2,
          type: 2,
        },
        // 其他事件
        {
          type: null, // 事件类型，1-业务审批、2-出纳付款
          sheetId: null, // 事件目标报销单id
          sheetType: null, // 目标报销单类型
          state: null, // 事件状态，0-未处理，1-已处理，2-被督办
          description: "", // 事件描述
          createTime: null, // 发起时间
          expectTime: null, // 预期完成时间
          finishTime: null, // 完成时间
        },
      ],
      eventTypes: {
        1: "业务审批",
        2: "出纳付款",
      },
      reimburseSheetTypes: {
        0: "差旅费",
        1: "日常费用",
        2: "业务招待费用",
      },
      showType: "all",
    };
  },
  mounted() {
    this.getTodoEventList(-1);
  },
  methods: {
    // 根据事件状态获取按钮类型
    getButtonType(event) {
      var state = event.state;
      if (state === 0) {
        // 未处理
        return "info";
      } else if (state === 1) {
        // 已处理
        return "success";
      } else if (state === 2) {
        // 被督办
        return "warning";
      } else if (state === 3) {
        // 已超时
        return "danger";
      }
    },
    // 根据事件状态获取按钮内容
    getButtonContent(event) {
      var state = event.state;
      if (state === 0) {
        return "未处理";
      } else if (state === 1) {
        return "已处理";
      } else if (state === 2) {
        return "被督办";
      } else if (state === 3) {
        return "已超时";
      }
    },

    // 获取相差的天数
    getDiffDay(expectTime) {
      const givenDate = new Date(expectTime);
      const today = new Date();

      // 将日期对象转换为时间戳（以毫秒为单位）
      const givenTimestamp = givenDate.getTime();
      const todayTimestamp = today.getTime();

      console.log(expectTime);
      console.log(givenDate);
      console.log(today);

      // 计算日期差距并转换为天数
      const differenceInMilliseconds = givenTimestamp - todayTimestamp;
      const differenceInDays = Math.ceil(
        differenceInMilliseconds / (1000 * 60 * 60 * 24)
      );

      return differenceInDays;
    },

    // 获取当前用户的待办事件列表
    getTodoEventList(state) {
      if (state === -1) {
        state = null;
      }
      this.axios
        .post("/todo/list", {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          todoUser: this.$store.state.userId,
          state: state,
        })
        .then((response) => {
          var page = response.data;
          this.total = page.total;
          this.currentPage = page.current;
          this.todoEventList = page.records;
        });
    },

    changeShowTypeHandler() {
      if (this.showType === "all") {
        this.getTodoEventList(-1);
      } else if (this.showType === "todo") {
        this.getTodoEventList(0);
      } else if (this.showType === "done") {
        this.getTodoEventList(1);
      } else if (this.showType === "overdue") {
        this.getTodoEventList(2);
      } else {
        this.getTodoEventList(3);
      }
    },

    /**
     * 事件处理按钮，实际逻辑
     * @param {事件信息} event
     */
    eventRedirectBtnHandler(event) {
      // 正在跳转至处理界面。。。
      console.log(
        "目标报销单id为：" + event.sheetId + "--- 类型为：" + event.sheetType
      );
      console.log("事件类型为：" + this.eventTypes[event.type]);
      // 根据事件类型跳转不同地址（财务、审批）
      if (event.type === 1) {
        // 业务审批，跳转至报销单页面
        this.toReimburseSheet(event.sheetId, event.sheetType);
      } else if (event.type === 2) {
        // 出纳付款，跳转至财务页面
      }
    },

    /**
     * 根据报销单id及类型跳转到相应页面
     * @param {报销单id} sheetId
     * @param {报销单类型} sheetType
     */
    toReimburseSheet(sheetId, sheetType) {
      // 把state状态设置为当前用户为操作者，便于样式改变
      this.$store.state.isOprUser = true;

      if (sheetType === 0) {
        // 差旅费页面
      } else if (sheetType === 1) {
        // 日常费用页面
        this.$pushRoute(this.$router, "/reimburse/info/daily/" + sheetId);
      } else if (sheetType === 2) {
        // 业务招待费页面
      }
    },
  },
};
</script>

<style scoped>
.todo-list {
  max-width: 800px;
  margin: 0 auto;
}

.event-card {
  margin-bottom: 20px;
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.event-title {
  font-size: 18px;
  font-weight: bold;
}
.event-type-state-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.event-type {
  font-size: 20px;
  font-weight: 600;
}

.event-description {
  margin-top: 8px;
}

.event-date-container {
  margin-top: 16px;
}

.event-createTime {
  margin-right: 26px;
}
.event-expectTime {
  color: #e74c3c;
}

.event-finishTime {
  color: #e74c3c;
}

.event-redirect-button {
  margin-top: 8px;
}

.none-display {
  display: none;
}
</style>
