function pushRoute(router, path) {
  if (router.currentRoute.path !== path) {
    router.push(path);
  }
}

function replaceRoute(router, path) {
  if (router.currentRoute.path !== path) {
    router.replace(path);
  }
}

const RouterUtilsPlugin = {
  install(Vue) {
    Vue.prototype.$pushRoute = pushRoute;
    Vue.prototype.$replaceRoute = replaceRoute;
  },
};

export default RouterUtilsPlugin;
