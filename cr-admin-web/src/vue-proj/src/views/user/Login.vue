<template>
  <div>
    <h1 class="page-title">云报销管理系统</h1>
    <div class="login-container">
      <h2>登录系统</h2>
      <form @submit.prevent="login">
        <label for="username">用户名:</label>
        <input type="text" id="username" v-model="userInfo.username" required />

        <label for="password">密码:</label>
        <input
          type="password"
          id="password"
          v-model="userInfo.password"
          required
        />

        <button type="submit">登录</button>
        <p>
          还没有账号？<router-link to="/register">点击此处注册</router-link>
        </p>
      </form>
      <p v-if="error" class="error-message">{{ error }}</p>
    </div>

    <!-- 登录成功提示框 -->
    <div v-if="showSuccessMessage" class="success-message">登录成功！</div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "login-view",
  data() {
    return {
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
      },
      error: "",
      showSuccessMessage: false, // 控制是否显示成功提示框
    };
  },
  methods: {
    login() {
      axios
        .post("/user/login", {
          username: this.userInfo.username,
          password: this.userInfo.password,
        })
        .then((response) => {
          this.userInfo = response.data;
          // 保存用户信息到vuex中
          this.$store.commit("saveUserId", this.userInfo.id);
          this.$store.commit("saveUserInfo", this.userInfo);

          //TODO：登录成功，并且跳转至内部页面
          this.showSuccessMessage = true;

          setTimeout(() => {
            // 跳转至内部页面
            this.showSuccessMessage = false;
            this.$router.push("/index");
          }, 3000);
        })
        .catch((error) => {
          console.error("请求失败：", error);
          this.error = error;
        });
    },
  },
};
</script>

<style scoped>
.page-title {
  text-align: center;
  font-size: 40px;
  margin-bottom: 20px;
}

.login-container {
  max-width: 300px;
  margin: 100px auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

form {
  display: flex;
  flex-direction: column;
}

label {
  margin-bottom: 5px;
}

input {
  padding: 8px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 3px;
}

button {
  background-color: #4caf50;
  color: white;
  padding: 10px;
  border: none;
  border-radius: 3px;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}

.error-message {
  color: #ff0000;
  margin-top: 10px;
}
.success-message {
  background-color: #4caf50;
  color: white;
  padding: 10px;
  margin-top: 10px;
  border-radius: 5px;
}
</style>
