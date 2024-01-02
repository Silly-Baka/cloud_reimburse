<template>
  <div>
    <div class="register-container">
      <h2>注册新账号</h2>
      <form @submit.prevent="register">
        <label for="newUsername">新用户名:</label>
        <input type="text" id="newUsername" v-model="newUsername" required />

        <label for="newPassword">新密码:</label>
        <input
          type="password"
          id="newPassword"
          v-model="newPassword"
          required
        />

        <button type="submit">注册</button>
      </form>
      <p v-if="error" class="error-message">
        {{ error }}
      </p>
    </div>
    <!-- 登录成功提示框 -->
    <div v-if="showSuccessMessage" class="success-message">
      注册用户成功，正在跳转至登陆页面...
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "register-view",
  data() {
    return {
      newUsername: "",
      newPassword: "",
      error: "",
      showSuccessMessage: false,
    };
  },
  methods: {
    register() {
      axios
        .post("/user/register", {
          username: this.newUsername,
          password: this.newPassword,
        })
        .then(() => {
          // 注册成功，跳转回登录页面
          this.showSuccessMessage = true;
          setTimeout(() => {
            this.showSuccessMessage = false;
            this.$router.push("/login");
          }, 1500);
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
.register-container {
  max-width: 300px;
  margin: 50px auto;
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
</style>
