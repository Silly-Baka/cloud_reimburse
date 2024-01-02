<template>
  <div>
    <el-container>
      <!-- 头部 -->
      <el-header>Header</el-header>
      <el-container>
        <!-- 侧边栏 -->
        <el-aside width="200px">
          <SideBar />
        </el-aside>
        <!-- 页面主体内容 -->
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import SideBar from "./components/SideBar.vue";

export default {
  name: "index-view",
  components: {
    SideBar,
  },
  data() {
    return {
      activeMenu: "1", // 设置默认选中的菜单项
    };
  },
};
</script>

<style scoped>
/* 可以根据需要添加样式 */

.el-header,
.el-footer {
  background-color: #b3c0d1;
  color: #333;
  text-align: center;
  line-height: 60px;
}
.el-main {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
  line-height: 10px;
}
body > .el-container {
  margin-bottom: 40px;
}
</style>
