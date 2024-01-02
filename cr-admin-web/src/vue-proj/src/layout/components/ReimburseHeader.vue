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
      <el-button type="primary" @click="submitExpense">提交报销单</el-button>
      <el-button type="success" @click="approveExpense">审核通过</el-button>
      <el-button type="warning" @click="forwardExpense">流程转发</el-button>
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
      // 提交报销单逻辑
      console.log("Submit Expense");
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
  justify-content: space-between;
  align-items: center;
  padding: 20px;
}

.tabs-container {
  flex: 1; /* 让标签页容器占据剩余空间 */
}

.action-buttons {
  display: flex;
  gap: 10px; /* 按钮之间的间距 */
}
</style>
