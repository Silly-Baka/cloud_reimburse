<template>
  <div class="header-container">
    <!-- 页头标签页 -->
    <el-tabs
      v-model="activeTab"
      @tab-click="handleTabClick"
      class="tabs-container"
    >
      <el-tab-pane label="报销单信息" name="expenseInfo"> </el-tab-pane>
      <el-tab-pane label="流程图" name="flowChart"> </el-tab-pane>
      <el-tab-pane label="流程状态" name="flowStatus"> </el-tab-pane>
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
        @click="approveExpense"
        :class="{ 'none-display': !this.$store.state.isOprUser }"
        >审核通过</el-button
      >
      <el-button
        type="warning"
        @click="forwardExpense"
        :class="{ 'none-display': !this.$store.state.isOprUser }"
        >流程转发</el-button
      >
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      activeTab: "expenseInfo", // 默认显示的标签页
    };
  },
  methods: {
    handleTabClick(tab) {
      // 处理标签页点击事件
      console.log("Tab clicked:", tab);
    },
    submitExpense() {
      // 根据不同路径触发不同的上传方式
      const currentPath = this.$route.path;
      // 日常报销
      if (currentPath === "/apply/daily") {
        this.$emit("submitDailyReimburse");
      }
    },

    approveExpense() {
      // 审核通过逻辑
      console.log("Approve Expense");
    },
    forwardExpense() {
      // 流程转发逻辑
      console.log("Forward Expense");
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
