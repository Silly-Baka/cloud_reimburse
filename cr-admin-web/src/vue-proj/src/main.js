import Vue from "vue";
import App from "./App.vue";
import router from "@/router";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";

import Notification from "vue-notification";
import axios from "axios";
import VueAxios from "vue-axios";
import store from "./store";
import RouterUtilsPlugin from "./utils/routerUtils";

Vue.config.productionTip = false;
Vue.use(Notification);
Vue.use(VueAxios, axios);
Vue.use(ElementUI);
Vue.use(RouterUtilsPlugin);

new Vue({
  router,
  store,
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
  if (responseData.success) {
    return responseData;
  } else {
    // 处理请求失败的情况，抛出异常让调用方捕获
    return Promise.reject(responseData.message || "请求失败");
  }
});
