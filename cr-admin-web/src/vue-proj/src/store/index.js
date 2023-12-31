import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    token: "",
    userId: null, // 个人id
    userInfo: {
      id: null,
      username: "",
      realName: "",
      deptName: "",
      email: "",
      phone: "",
      sex: "",
      headerUrl: "",
      token: "",
    }, // 个人信息

    // // 日常报销单信息
    // dailySheetInfo: {
    //   applicantId: null,
    //   name: "",
    //   price: null,
    //   createTime: null,
    //   relevantProj: "",
    //   dailyInfoList: [],
    // },
    isApplyView: false, // 是否申请页面，用于动态修改界面样式
    isOprUser: false, // 当前用户是否负责操作当前报销单
  },
  mutations: {
    saveToken(state, token) {
      state.token = token;
    },
    saveUserId(state, userId) {
      state.userId = userId;
    },
    saveUserInfo(state, userInfo) {
      state.userInfo = userInfo;
    },
    saveIsApplyView(state, isApplyView) {
      state.isApplyView = isApplyView;
    },
  },
  plugins: [
    createPersistedState({
      storage: window.sessionStorage,
    }),
  ],
});
