<template>
  <div>
    <el-form
      ref="form"
      :model="userInfo"
      label-width="80px"
      class="userinfo-form"
    >
      <el-form-item label="当前角色">
        <el-input v-model="userInfo.roleName" disabled></el-input>
      </el-form-item>
      <el-form-item label="用户名">
        <el-input v-model="userInfo.username"></el-input>
      </el-form-item>
      <el-form-item label="真实姓名">
        <el-input v-model="userInfo.realName"></el-input>
      </el-form-item>
      <el-form-item label="所在部门">
        <el-input v-model="userInfo.deptName" disabled></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="userInfo.email"></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="userInfo.phone"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-radio v-model="userInfo.sex" label="男"></el-radio>
        <el-radio v-model="userInfo.sex" label="女"></el-radio>
      </el-form-item>
      <el-button type="primary" @click="updateUserInfo">保存</el-button>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userInfo: this.$store.state.userInfo,
    };
  },
  methods: {
    updateUserInfo() {
      this.axios.post("/user/update", this.userInfo).then(() => {
        // 更新vuex中的user信息
        this.$store.commit("saveUserInfo", this.userInfo);

        // 提示修改成功
        this.$message({
          message: "成功修改个人用户信息！",
          type: "success",
        });
      });
    },
  },
};
</script>

<style scoped>
.userinfo-form {
  margin-left: 25%;
  margin-top: 4%;
  width: 50%;
}
</style>
