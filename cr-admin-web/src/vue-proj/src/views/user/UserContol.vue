<template>
  <div>
    <el-container>
      <!-- 发票新增、编辑相关按钮 -->
      <el-header class="header-container">
        <div class="header-title">人员管理</div>
        <el-button class="header-button" @click="addDialogVisable = true"
          >添加账号</el-button
        >
        <el-dialog title="提示" :visible.sync="addDialogVisable" width="30%">
          <el-form :model="addUserInfo">
            <el-form-item label="真实姓名">
              <el-input
                v-model="addUserInfo.realName"
                placeholder="请输入真实姓名"
              ></el-input>
            </el-form-item>
            <el-form-item label="用户名">
              <el-input
                v-model="addUserInfo.username"
                placeholder="请输入用户名"
              ></el-input>
            </el-form-item>
            <el-form-item label="密码">
              <el-input
                v-model="addUserInfo.password"
                placeholder="请输入密码"
                show-password
              ></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="addUser">添加</el-button>
              <el-button @click="addDialogVisable = false">取消</el-button>
            </el-form-item>
          </el-form>
        </el-dialog>
      </el-header>

      <!-- 查询条件 -->
      <div class="search-bar">
        <div class="bitDiv">
          <div style="display: inline-block; margin: 2px 10px">
            <span class="search-style"> 真实姓名 </span>
            <el-input
              v-model="userQryDTO.username"
              placeholder="请输入真实姓名"
              style="width: 160px"
            ></el-input>
          </div>
          <div style="display: inline-block; margin: 2px 10px">
            <span class="search-style"> 所在部门 </span>
            <el-input
              v-model="userQryDTO.deptName"
              placeholder="请输入所在部门"
              style="width: 160px"
            ></el-input>
          </div>
          <div style="display: inline-block; margin: 2px 10px">
            <span class="search-style"> 电话 </span>
            <el-input
              v-model="userQryDTO.phone"
              placeholder="请输入电话"
              style="width: 160px"
            ></el-input>
          </div>
          <div style="display: inline-block; margin: 2px 10px">
            <span class="search-style"> 邮箱 </span>
            <el-input
              v-model="userQryDTO.email"
              placeholder="请输入邮箱"
              style="width: 160px"
            ></el-input>
          </div>
          <el-button type="primary" @click="getUserList">查询</el-button>
        </div>
      </div>

      <!-- 发票信息列表 -->
      <el-main class="main-container">
        <!-- 发票信息表格 -->
        <el-table :data="userList" style="width: 100%" stripe>
          <el-table-column type="selection"></el-table-column>
          <!-- 表格内显示的发票信息 -->
          <el-table-column label="真实姓名" prop="realName"></el-table-column>
          <el-table-column label="用户名" prop="username"></el-table-column>
          <el-table-column label="部门" prop="deptName"></el-table-column>
          <el-table-column label="角色" prop="roleName">
            <template slot-scope="scope">
              <el-select v-model="scope.row.roleName" placeholder="请选择">
                <el-option
                  v-for="role in roleList"
                  :key="role.id"
                  :label="role.roleName"
                  :value="role.roleName"
                >
                </el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="账号状态">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.state"
                active-color="#13ce66"
                inactive-color="#ff4949"
              >
              </el-switch>
            </template>
          </el-table-column>
          <el-table-column label="电话" prop="phone"></el-table-column>
          <el-table-column label="邮箱" prop="email"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                type="success"
                icon="el-icon-check"
                @click="
                  handleUpdateUser(
                    scope.row.id,
                    scope.row.state,
                    scope.row.roleName
                  )
                "
                circle
                >修改</el-button
              >
              <el-dialog title="提示" :visible.sync="dialogVisible" width="30%">
                <span>是否确定修改</span>
                <span slot="footer" class="dialog-footer">
                  <el-button @click="dialogVisible = false">取 消</el-button>
                  <el-button type="primary" @click="updateUser"
                    >确 定</el-button
                  >
                </span>
              </el-dialog>
            </template>
          </el-table-column>

          <!-- 其他报销单信息列 -->
        </el-table>
      </el-main>

      <el-footer class="footer-container">
        <div class="footer-pagination">
          <el-pagination
            :current-page="currentPage"
            :page-sizes="[5, 10, 20, 50, 100]"
            :page-size="20"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
          >
          </el-pagination>
        </div>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentPage: 1, // 当前页号
      total: 1, // 记录总条数
      pageSize: 20, // 一页的数量
      userList: [
        {
          id: null,
          username: "测试用",
          realName: "测试用",
          deptName: "测试用部门",
          email: "123@qq.com",
          phone: "123",
          state: null,
        },
      ],
      roleList: [
        {
          id: null,
          roleName: "",
        },
      ],
      userQryDTO: {
        username: "",
        realName: "",
        deptName: "",
        phone: "",
      },

      dialogVisible: false,
      addDialogVisable: false,

      userId: null,
      roleName: "",
      state: null,

      addUserInfo: {
        realName: "",
        username: "",
        password: "",
      },
    };
  },
  mounted() {
    // 获取角色列表
    this.getRoleList();

    // 获取用户列表
    this.getUserList();
  },
  methods: {
    getRoleList() {
      this.axios.get("/role/list").then((response) => {
        this.roleList = response.data;
      });
    },

    getUserList() {
      this.axios
        .post("/user/list/selective", {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          username: this.userQryDTO.username,
          realName: this.userQryDTO.realName,
          deptName: this.userQryDTO.deptName,
          phone: this.userQryDTO.phone,
        })
        .then((response) => {
          var page = response.data;

          this.total = page.total;
          this.currentPage = page.current;
          this.userList = page.records;
        });
    },

    updateUser() {
      // 修改账号状态
      this.axios.post("/user/update", {
        id: this.userId,
        state: this.state,
      });

      // 修改账号角色
      this.updateUserRole();
    },

    updateUserRole() {
      // 从roleList中找到roleName对应的roleId
      for (let i = 0; i < this.roleList.length; i++) {
        if (this.roleList[i].roleName === this.roleName) {
          this.axios
            .post("/user/updateRole", {
              userId: this.userId,
              roleId: this.roleList[i].id,
            })
            .then(() => {
              this.$message({
                message: "修改成功",
                type: "success",
              });
              this.dialogVisible = false;
            });
          break;
        }
      }
    },
    handleUpdateUser(userId, userState, roleName) {
      this.userId = userId;
      this.state = userState;
      this.roleName = roleName;
      this.dialogVisible = true;
    },

    // 添加账号
    addUser() {
      this.axios
        .post("/user/register", {
          username: this.addUserInfo.username,
          password: this.addUserInfo.password,
          realName: this.addUserInfo.realName,
        })
        .then(() => {
          this.$message({
            message: "添加账号成功",
            type: "success",
          });
          location.reload();
        });
    },
  },
};
</script>

<style scoped>
.header-container {
  position: relative;
  display: block;
  background: #f9f9f9;
}

.search-bar {
  background-color: white;
}
.search-tabs {
  margin-left: 12px;
}

.main-container {
  padding: 0;
}

.header-title {
  position: absolute;
  display: block;
  top: 50%;
  transform: translateY(-50%);
  width: fit-content;
  font-size: 20px;
  font-weight: 550;
}

.header-button {
  position: relative;
  float: right;
  margin-left: 12px;
  top: 50%;
  transform: translateY(-50%);
}
.inner-table {
  background-color: #f9f9f9;
  box-shadow: 0 2px 12px 0 transparent;
}
.inner-table ::v-deep .el-table th.el-table__cell,
::v-deep .el-table th,
::v-deep .el-table tr {
  background-color: transparent !important;
}
.footer-pagination {
  position: relative;
  float: right;
}
</style>
