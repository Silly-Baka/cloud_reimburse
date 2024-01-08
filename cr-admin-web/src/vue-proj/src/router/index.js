import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
  {
    path: "/login",
    component: () => import("../views/user/Login.vue"),
  },
  {
    path: "/",
    component: () => import("../views/user/Login.vue"),
  },
  {
    path: "/register",
    component: () => import("../views/user/Register.vue"),
  },
  {
    path: "/index",
    component: () => import("../layout/Index.vue"),
    children: [
      {
        path: "/myReimburse",
        component: () => import("../views/reimburse/MyReimburse.vue"),
      },
    ],
  },
  {
    path: "/reimburse/info",
    component: () => import("../layout/ReimburseInfo.vue"),
    children: [
      {
        path: "/apply/daily",
        component: () => import("../views/reimburse/apply/DailyReimburse.vue"),
      },
      {
        path: "/reimburse/info/daily/:id",
        component: () => import("../views/reimburse/apply/DailyReimburse.vue"),
      },
    ],
  },
];

const router = new VueRouter({
  routes,
});
export default router;
