<template>
  <div>
    <el-container>
      <!-- 头部 -->
      <el-header>
        <ReimburseHeader />
      </el-header>
      <!-- 报销单信息、流程图等情况  -->
      <el-main>
        <router-link to="/apply/daily">测试用-发起日常报销单</router-link>
        <router-view></router-view>
      </el-main>
      <!-- 下部 -->
      <el-footer>Footer</el-footer>
    </el-container>
  </div>
</template>

<script>
import ReimburseHeader from "./components/ReimburseHeader";
export default {
  components: {
    ReimburseHeader,
  },
  data() {
    return {};
  },
};
</script>

<style scoped>
/* 可以根据需要添加样式 */
</style>
