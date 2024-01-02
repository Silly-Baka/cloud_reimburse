<template>
  <div>
    <!-- 查询条件 -->
    <div class="search-bar">
      <div class="bitDiv">
        <div style="display: inline-block; margin: 2px 10px">
          <span class="search-style" style="margin-right: 20px">
            发起日期
          </span>
          <el-date-picker
            v-model="searchForm.dateRange"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          ></el-date-picker>
        </div>
        <div style="display: inline-block; margin: 2px 10px">
          <span class="search-style" style="margin-right: 20px">
            流程状态
          </span>
          <el-select
            v-model="searchForm.state"
            placeholder="请选择"
            style="display: inline-block"
          >
            <el-option label="全部" value=""></el-option>
            <el-option label="报销失败" value="-1"></el-option>
            <el-option label="报销中" value="0"></el-option>
            <el-option label="报销完成" value="1"></el-option>
          </el-select>
          <el-button type="primary" @click="search">查询</el-button>
        </div>
      </div>
    </div>

    <!-- 报销单信息表格 -->
    <el-table :data="expenseReports" style="width: 100%; margin-top: 10px">
      <el-table-column label="报销单名称" prop="name"></el-table-column>
      <el-table-column label="报销类型" prop="type"></el-table-column>
      <el-table-column label="发起日期" prop="createTime"></el-table-column>
      <el-table-column label="报销金额" prop="price"></el-table-column>
      <el-table-column label="流程状态" prop="state"></el-table-column>
      <!-- 其他报销单信息列 -->
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchForm: {
        state: "", // 流程状态
        dateRange: null, // 事件范围
      },
      expenseReports: [
        // 报销单信息数据
        {
          name: "测试用1",
          type: "测试用1",
          createTime: "1",
          price: 1,
          state: "pending",
        },
        {
          name: "测试用2",
          type: "测试用2",
          createTime: "2",
          price: 2,
          state: "approved",
        },
        {
          name: "测试用3",
          type: "测试用3",
          createTime: "3",
          price: 3,
          state: "rejected",
        },
        // 其他报销单信息数据
      ],
      constants: {
        "-1": "报销失败",
        0: "报销中",
        1: "报销完成",
      },
    };
  },
  methods: {
    search() {
      // 根据查询条件进行报销单信息查询逻辑
      // 可以向后端发起请求获取数据，也可以在前端进行过滤
      this.axios
        .post("/reimburse/listSelective", {
          applicantId: this.$store.state.UserId,
          startDate: this.dateRange.startDate,
          endDate: this.dateRange.endDate,
          state: this.state,
        })
        .then((response) => {
          // 将常量值转换为常量名
          response.data.forEach((item) => {
            return Object.keys(this.constants).find(
              (key) => this.constants[key] === item
            );
          });
          this.expenseReports = response.data;
        });
    },
  },
  mounted() {
    // 初始化报销单列表
    this.axios
      .get("/reimburse/listById", {
        params: {
          userId: this.$store.state.userId,
        },
      })
      .then((response) => {
        // 将常量值转换为常量名
        response.data.forEach((item) => {
          return Object.keys(this.constants).find(
            (key) => this.constants[key] === item
          );
        });
        this.expenseReports = response.data;
      });
  },
};
</script>

<style scoped>
.el-table {
  margin-top: 0px;
  line-height: 0px;
}

/* .search-style {
  margin-top: 5px;
  margin-bottom: 5px;
  display: inline-block;
} */
</style>
