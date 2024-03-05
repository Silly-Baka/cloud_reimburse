<template>
  <div class="sidebar">
    <!-- 导航链接 -->
    <div class="nav-links">
      <el-menu class="el-menu-vertical">
        <!-- 人员管理 -->
        <el-submenu index="personnel">
          <template slot="title">
            <i class="el-icon-user"></i>
            <span>人员管理</span>
          </template>
          <el-menu-item index="addPerson"> 添加人员 </el-menu-item>
          <el-menu-item index="viewPerson">查看人员</el-menu-item>
        </el-submenu>

        <!-- 发票管理 -->
        <el-menu-item index="invoices" @click="goInvoiceFloder">
          <template slot="title">
            <i class="el-icon-document"></i>
            <span>发票管理</span>
          </template>
        </el-menu-item>

        <!-- 报销管理 -->
        <el-submenu index="expenses">
          <template slot="title">
            <i class="el-icon-money"></i>
            <span>报销管理</span>
          </template>
          <!-- <el-menu-item index="preApply">事前申请</el-menu-item> -->
          <el-submenu index="applyReimburse">
            <template slot="title">
              <span>报销申请</span>
            </template>

            <el-menu-item index="dailyReimburse" @click="goApplyDaily"
              >日常费用报销</el-menu-item
            >
            <el-menu-item index="travelReimburse">差旅费报销</el-menu-item>
            <el-menu-item index="businessReceptionReimburse"
              >业务招待费用报销</el-menu-item
            >
          </el-submenu>
          <el-menu-item index="myReimburse" @click="goMyReimburse">
            我的报销
          </el-menu-item>
          <!-- <el-menu-item index="reimbuseReview">报销审核</el-menu-item> -->
        </el-submenu>

        <!-- 财务处理 -->
        <el-submenu index="finance">
          <template slot="title">
            <i class="el-icon-bank-card"></i>
            <span>财务处理</span>
          </template>
          <router-link to="/finance"></router-link>
          <el-menu-item index="accountingCenter" @click="goAccountingCenter">
            结算中心
          </el-menu-item>
        </el-submenu>

        <!-- 待办事件 -->
        <el-menu-item index="todos" @click="goTodoEvent">
          <template slot="title">
            <i class="el-icon-alarm-clock"></i>
            <span>待办事件</span>
          </template>
        </el-menu-item>

        <!-- 其他导航链接 -->
      </el-menu>
    </div>
  </div>
</template>

<script>
export default {
  name: "SideBarComponent",

  methods: {
    goMyReimburse() {
      this.$pushRoute(this.$router, "/myReimburse");
    },
    goApplyDaily() {
      this.$pushRoute(this.$router, "/apply/daily");
    },
    goInvoiceFloder() {
      this.$pushRoute(this.$router, "/invoice/floder");
    },
    goTodoEvent() {
      this.$pushRoute(this.$router, "/event/list");
    },
    goAccountingCenter() {
      this.$pushRoute(this.$router, "/finance/accounting/center");
    },
  },
};
</script>

<style scoped>
/* 样式可以根据实际需求进行调整 */
.sidebar {
  color: white;
}

.nav-links {
  padding-left: 20px;
}

.el-menu-item {
  margin-bottom: 5px;
}

.el-menu-item a {
  color: white;
  text-decoration: none;
}

/* 使用 Element UI 提供的图标样式 */
.el-icon {
  font-size: 40px;
  margin-right: 10px;
}
</style>
