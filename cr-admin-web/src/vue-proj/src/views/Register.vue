<template>
  <div class="register-container">
    <h2>注册新账号</h2>
    <form @submit.prevent="register">
      <label for="newUsername">新用户名:</label>
      <input type="text" id="newUsername" v-model="newUsername" required />

      <label for="newPassword">新密码:</label>
      <input type="password" id="newPassword" v-model="newPassword" required />

      <button type="submit">注册</button>
    </form>
    <p v-if="registrationError" class="error-message">
      {{ registrationError }}
    </p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      newUsername: "",
      newPassword: "",
      registrationError: "",
    };
  },
  methods: {
    register() {
      // 在这里可以添加注册逻辑
      if (this.newUsername && this.newPassword) {
        // 注册成功的逻辑，可以保存用户信息等
        alert("注册成功");
        // 清空输入框
        this.newUsername = "";
        this.newPassword = "";
        this.registrationError = "";
      } else {
        // 注册失败，显示错误消息
        this.registrationError = "请填写用户名和密码";
      }
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
