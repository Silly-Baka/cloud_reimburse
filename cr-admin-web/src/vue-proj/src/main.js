import Vue from "vue";
import App from "./App.vue";
import router from "@/router";

import Notification from "vue-notification";
import axios from "axios";

Vue.config.productionTip = false;
Vue.use(Notification);

// 存储用户信息到会话缓存
Vue.prototype.$saveUserInfoToSessionStorage = function (userInfo) {
  const userInfoJson = JSON.stringify(userInfo);
  sessionStorage.setItem("userInfo", userInfoJson);
};

new Vue({
  router,
  render: (h) => h(App),
}).$mount("#app");

// 拦截请求，为其补充信息
axios.interceptors.request.use((config) => {
  // 补充请求前缀
  config.url = "http://localhost:8089/api/v1" + config.url;
  return config;
});

// 拦截所有响应，处理其错误信息
axios.interceptors.response.use((response) => {
  const responseData = response.data;
  if (responseData.isSuccess) {
    return responseData;
  } else {
    // 处理请求失败的情况，抛出异常让调用方捕获
    return Promise.reject(responseData.message || "请求失败");
  }
});
