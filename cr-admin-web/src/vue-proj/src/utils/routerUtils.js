function pushRoute(router, path) {
  if (router.currentRoute.path !== path) {
    router.push(path);
  }
}

const RouterUtilsPlugin = {
  install(Vue) {
    Vue.prototype.$pushRoute = pushRoute;
  },
};

export default RouterUtilsPlugin;
