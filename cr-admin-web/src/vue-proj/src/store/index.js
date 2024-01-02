import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    token: "",
    userId: null,
  },
  mutations: {
    saveToken(state, token) {
      state.token = token;
    },
    saveUserId(state, userId) {
      state.userId = userId;
    },
  },
  plugins: [
    createPersistedState({
      storage: window.sessionStorage,
    }),
  ],
});
