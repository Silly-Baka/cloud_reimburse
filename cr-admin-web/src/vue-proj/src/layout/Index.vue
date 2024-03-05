<template>
  <div>
    <el-container style="height: 100vh">
      <!-- 头部 -->
      <el-header>
        <IndexHeader></IndexHeader>
      </el-header>
      <el-container height="100%">
        <!-- 侧边栏 -->
        <el-aside width="15%">
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
import IndexHeader from "./components/IndexHeader.vue";

export default {
  name: "index-view",
  components: {
    SideBar,
    IndexHeader,
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
