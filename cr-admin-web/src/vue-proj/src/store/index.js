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
    sheetId: null,
    activeTab: "", // 目前所在的标签页
    invoiceId: null, // 目前正在编写的发票id
    curNodeId: null, // 报销单当前流程节点id
    payAmount: null,
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
    saveSheetId(state, sheetId) {
      state.sheetId = sheetId;
    },
    saveActiveTab(state, activeTab) {
      state.activeTab = activeTab;
    },
    saveInvoiceId(state, invoiceId) {
      state.invoiceId = invoiceId;
    },
    saveCurNodeId(state, curNodeId) {
      state.curNodeId = curNodeId;
    },
    savePayAmount(state, payAmount) {
      state.payAmount = payAmount;
    },
  },
  plugins: [
    createPersistedState({
      storage: window.sessionStorage,
    }),
  ],
});
