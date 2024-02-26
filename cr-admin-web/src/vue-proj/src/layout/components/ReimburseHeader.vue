<template>
  <div class="header-container">
    <el-button
      icon="el-icon-back"
      style="margin-right: 20px"
      @click="goBack"
      type="primary"
      round
      >返回</el-button
    >
    <!-- 页头标签页 -->
    <el-tabs
      v-model="activeTab"
      @tab-click="handleTabClick"
      class="tabs-container"
    >
      <el-tab-pane
        label="报销单信息"
        name="expenseInfo"
        :disabled="isDisabled()"
      >
      </el-tab-pane>
      <el-tab-pane label="流程图" name="flowChart" :disabled="isDisabled()">
      </el-tab-pane>
      <!-- <el-tab-pane label="流程状态" name="flowStatus" :disabled="isDisabled()"></el-tab-pane> -->
    </el-tabs>

    <!-- 操作按钮 -->
    <div class="action-buttons">
      <el-button
        type="primary"
        @click="submitExpense"
        :class="{ 'none-display': !this.$store.state.isApplyView }"
        >提交报销单</el-button
      >
      <el-button
        type="success"
        @click="showApproveDialog"
        :class="{ 'none-display': !this.$store.state.isOprUser }"
        >审批</el-button
      >
      <el-button
        type="warning"
        @click="forwardExpense"
        :class="{ 'none-display': !this.$store.state.isOprUser }"
        >流程转发</el-button
      >
    </div>

    <el-dialog :visible.sync="approveDialogVisible">
      <span slot="title" class="dialog-title">审批</span>
      <el-input
        type="textarea"
        :rows="2"
        placeholder="请输入审批意见（若不通过请输入具体原因）"
        v-model="feedback"
      >
      </el-input>

      <span slot="footer" class="dialog-footer">
        <el-button type="danger" @click="approveFail">拒绝</el-button>
        <el-button type="primary" @click="approvePass">审批通过</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      activeTab: this.$store.state.activeTab, // 默认显示的标签页
      approveDialogVisible: false, // 审批浮窗是否可见
      feedback: "", // 审批或流程转发的反馈信息
    };
  },
  methods: {
    // 返回上一级页面
    goBack() {
      this.$router.go(-1);
    },

    handleTabClick() {
      // 处理标签页点击事件
      if (this.activeTab === "expenseInfo") {
        this.$replaceRoute(
          this.$router,
          "/reimburse/info/daily/" + this.$store.state.sheetId
        );
      } else if (this.activeTab === "flowChart") {
        this.$replaceRoute(
          this.$router,
          "/reimburse/process/graph/" + this.$store.state.sheetId
        );
      }
      this.$store.state.activeTab = this.activeTab;
    },
    submitExpense() {
      // 根据不同路径触发不同的上传方式
      const currentPath = this.$route.path;
      // 日常报销
      if (currentPath === "/apply/daily") {
        this.$emit("submitDailyReimburse");
      }
    },

    showApproveDialog() {
      // 打开浮窗
      this.approveDialogVisible = true;
    },

    // 审批通过
    approvePass() {
      this.axios
        .post("/reimburse/process/change", {
          processNodeId: this.$store.state.curNodeId,
          userId: this.$store.state.userId,
          feedBack: this.feedback,
        })
        .then(() => {
          // 展示审批已通过的浮窗
          this.approveDialogVisible = false;
          this.$message({
            message: "审批已通过，正在跳转待办事件列表...",
            type: "success",
          });

          // 将该事件设置为已处理状态
          this.axios
            .post("/todo/done", {
              sheetId: this.$route.params.id,
              todoUser: this.$store.state.userId,
            })
            .then(() => {
              // 跳转页面至待办事件列表
              this.$pushRoute(this.$router, "/event/list");
            });
        });
    },

    // 审批不通过
    approveFail() {
      this.approveDialogVisible = false;
    },

    forwardExpense() {
      // 流程转发逻辑
      console.log("Forward Expense");
    },

    // 判断当前路径是否允许点击标签页（申请报销单不可点击）
    isDisabled() {
      const currentPath = this.$route.path;
      const pattern = /^\/apply\//;
      return pattern.test(currentPath);
    },
  },
};
</script>

<style scoped>
.header-container {
  display: flex;
  padding: 20px;
}

.tabs-container {
  flex: 0.95; /* 让标签页容器占据剩余空间 */
}

.none-display {
  display: none;
}
</style>
