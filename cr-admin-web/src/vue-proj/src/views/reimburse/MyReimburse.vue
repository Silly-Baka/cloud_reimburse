<template>
  <div>
    <!-- 查询条件 -->
    <div class="search-bar">
      <div class="bitDiv">
        <div style="display: inline-block; margin: 2px 10px">
          <span class="search-style"> 发起日期 </span>
          <el-date-picker
            v-model="searchForm.dateRange"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          ></el-date-picker>
        </div>
        <div style="display: inline-block; margin: 2px 10px">
          <span class="search-style"> 流程状态 </span>
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
        </div>
        <el-button type="primary" @click="search">查询</el-button>
      </div>
    </div>

    <!-- 报销单信息表格 -->
    <el-table
      :data="reimburstSheetList"
      style="width: 100%; margin-top: 10px"
      :row-class-name="tableRowClassName"
    >
      <el-table-column label="报销单名称" prop="name" width="280px">
        <template slot-scope="scope">
          <router-link :to="'/reimburse/info/daily/' + scope.row.id">
            {{ scope.row.name }}</router-link
          >
        </template>
      </el-table-column>
      <el-table-column label="报销类型" prop="type" sortable></el-table-column>
      <el-table-column
        label="发起日期"
        prop="createTime"
        sortable
      ></el-table-column>
      <el-table-column label="报销金额" prop="price" sortable></el-table-column>
      <el-table-column label="流程状态" prop="state" sortable></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            type="warning"
            :class="{ 'display-none': scope.row.state === '报销完成' }"
            @click="
              handlerSupervise(
                scope.row.id,
                scope.row.name,
                scope.row.curNodeId
              )
            "
            round
            >督办</el-button
          >
        </template>
      </el-table-column>
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
        dateRange: [], // 事件范围
      },
      reimburstSheetList: [
        {
          state: "报销完成",
        },
      ], // 报销单信息表格数据
      sheetState: {
        "-1": "报销失败",
        0: "报销中",
        1: "报销完成",
      },
      sheetTypes: {
        0: "差旅费报销",
        1: "日常费用报销",
        2: "业务招待费用报销",
      },
    };
  },
  methods: {
    search() {
      // 根据查询条件进行报销单信息查询逻辑
      // 可以向后端发起请求获取数据，也可以在前端进行过滤
      // console.log(this.searchForm.dateRange);
      // console.log(this.searchForm.dateRange[0]);
      // console.log(this.searchForm.dateRange[1]);
      this.axios
        .get("/reimburse/listSelective", {
          params: {
            applicantId: this.$store.state.UserId,
            startDate: this.searchForm.dateRange
              ? this.searchForm.dateRange[0]
              : null,
            endDate: this.searchForm.dateRange
              ? this.searchForm.dateRange[1]
              : null,
            state: this.searchForm.state,
          },
        })
        .then((response) => {
          // 将常量值转换为常量名
          var data = response.data;
          this.changeConstants(data);
          this.reimburstSheetList = data;
        });
    },
    // 将每一个报销单信息的常量值转化为常量名
    changeConstants(sheetList) {
      for (let i = 0; i < sheetList.length; i++) {
        sheetList[i].type = this.sheetTypes[sheetList[i].type];
        sheetList[i].state = this.sheetState[sheetList[i].state];
      }
    },

    handlerSupervise(sheetId, sheetName, curNodeId) {
      this.axios
        .post("/reimburse/supervise", {
          sheetId: sheetId,
          sheetName: sheetName,
          curNodeId: curNodeId,
        })
        .then(() => {
          this.$message({
            message: "成功督办此流程，请耐心等待工作人员处理！",
            type: "success",
          });
        });
    },

    // 根据每一行的状态改变其样式
    tableRowClassName({ row, rowIndex }) {
      console.log(row.state, rowIndex);
      if (row.state === "报销完成") {
        console.log("牛逼！换颜色了");
        return "success-row";
      }
      return "";
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
        var data = response.data;
        this.changeConstants(data);
        this.reimburstSheetList = data;
      });
  },
};
</script>

<style>
.el-table {
  margin-top: 0px;
  line-height: 0px;
}

.el-table .success-row {
  background: #f0f9eb;
}

/* .search-style {
  margin-top: 5px;
  margin-bottom: 5px;
  display: inline-block;
} */
.display-none {
  display: none;
}
</style>
