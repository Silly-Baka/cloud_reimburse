<template>
  <div class="header">
    <el-dropdown trigger="click" @command="handleChangeAccountCommand">
      <el-button class="btn-changeAccount" icon="el-icon-refresh"
        >演示账号切换<i class="el-icon-arrow-down el-icon--right"></i
      ></el-button>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item command="normal">普通员工</el-dropdown-item>
        <el-dropdown-item command="reimburseControl"
          >报销管控人员</el-dropdown-item
        >
        <el-dropdown-item command="finance">财务人员</el-dropdown-item>
        <el-dropdown-item command="manager">部门经理</el-dropdown-item>
        <el-dropdown-item command="systemControl">系统管理员</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>

    <el-popover trigger="hover">
      <!-- 表面按钮 -->
      <el-badge
        slot="reference"
        :value="messageCount"
        class="header-message"
        @click="showMessage"
      >
        <el-button type="primary" icon="el-icon-message-solid"></el-button>
      </el-badge>

      <!-- 鼠标停留时浮现，站内信框 -->
      <div class="message-box">
        <el-tabs v-model="activeMessagePane" @tab-click="handleClick">
          <el-tab-pane
            label="系统通知"
            name="systemNotice"
            class="message-content-container"
          >
            <!-- 标为全部已读按钮 -->
            <el-button
              type="success"
              round
              icon="el-icon-check"
              style="margin-left: 65%"
              @click="readAllMessage"
              :class="{ 'display-none': this.messages.length === 0 }"
              >标为全部已读</el-button
            >
            <el-card
              v-for="(message, index) in messages"
              :key="index"
              class="message-card"
            >
              <div slot="header" class="message-header">
                <span class="message-time">{{ message.time }}</span>
              </div>
              <div class="message-content-text">
                <p>{{ message.content }}</p>
                <router-link :to="getSheetInfo(message.entityId)"
                  >跳转链接</router-link
                >
              </div>
            </el-card>

            <div
              class="no-message-tip"
              :class="{ 'display-none': this.messages.length > 0 }"
            >
              <el-image src="no-message.png"></el-image>
              你已查看所有通知
            </div>
          </el-tab-pane>
          <el-tab-pane
            label="事件提醒"
            name="eventNotice"
            class="message-content-container"
          >
            <!-- 标为全部已读按钮 -->
            <el-button
              type="success"
              round
              icon="el-icon-check"
              style="margin-left: 65%"
              @click="readAllMessage"
              :class="{ 'display-none': this.messages.length === 0 }"
              >标为全部已读</el-button
            >
            <!-- 站内信展示框 -->
            <el-card
              v-for="(message, index) in messages"
              :key="index"
              class="message-card"
            >
              <div slot="header" class="message-header">
                <span class="message-time">{{ message.createTime }}</span>
              </div>
              <div class="message-content-text">
                <p>{{ message.content }}</p>
              </div>
            </el-card>

            <div
              class="no-message-tip"
              :class="{ 'display-none': this.messages.length > 0 }"
            >
              <el-image src="no-message.png"></el-image>
              你已查看所有通知
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </el-popover>
    <el-dropdown
      class="header-user"
      trigger="click"
      @command="handleHeaderCommand"
    >
      <el-image class="header-user-image" :src="user.image"></el-image>
      <span class="header-user-username"
        >{{ user.name }}<i class="el-icon-arrow-down el-icon--right"></i
      ></span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item command="showUserInfo">个人主页</el-dropdown-item>
        <el-dropdown-item command="logout">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user: {
        name: this.$store.state.userInfo.realName,
        image: "https://randomuser.me/api/portraits/men/32.jpg", // 用户头像链接
      },
      activeMessagePane: "systemNotice",
      messageCount: 3, // 站内信数量
      messages: [
        {
          title: "Message 1",
          content: "This is message content 1",
          time: "2023-01-01 08:00",
        },
        {
          title: "Message 2",
          content: "This is message content 2",
          time: "2023-01-02 09:00",
        },
        {
          title: "Message 3",
          content: "This is message content 3",
          time: "2023-01-03 10:00",
        },
        // Add more messages as needed
      ],
    };
  },
  methods: {
    // 处理下拉框click逻辑
    handleHeaderCommand(command) {
      if (command === "showUserInfo") {
        this.showUserInfo();
      }
      if (command === "logout") {
        this.logout();
      }
    },
    handleChangeAccountCommand(command) {
      if (command === "normal") {
        this.login("admin");
      }
      if (command === "reimburseControl") {
        this.login("control");
      }
      if (command === "finance") {
        this.login("finance");
      }
      if (command === "manager") {
        this.login("manager");
      }
      if (command === "systemControl") {
        this.login("systemControl");
      }
    },

    login(username) {
      this.axios
        .post("/user/login", {
          username: username,
          password: "admin",
        })
        .then((response) => {
          var userInfo = response.data;
          // 保存用户信息到vuex中
          this.$store.commit("saveUserId", userInfo.id);
          this.$store.commit("saveUserInfo", userInfo);

          // 刷新页面
          location.reload();
        })
        .catch((error) => {
          console.error("请求失败：", error);
          this.error = error;
        });
    },

    showMessage() {
      // 显示站内信
      console.log("Show messages");
    },

    // 展示用户主页
    showUserInfo() {
      console.log("展示用户个人主页");
      this.$pushRoute(this.$router, "/user/info");
    },

    // 退出登录
    logout() {
      console.log("已退出登录");
    },

    // 获取站内信信息
    handleClick() {
      // 获取系统通知
      if (this.activeMessagePane === "systemNotice") {
        this.axios
          .get("/message/systemNotice", {
            params: {
              userId: this.$store.state.userId,
            },
          })
          .then((response) => {
            this.messages = response.data;
          });
      }
      // 获取事件提醒、督办等消息
      if (this.activeMessagePane === "eventNotice") {
        this.axios
          .get("/message/eventNotice", {
            params: {
              userId: this.$store.state.userId,
            },
          })
          .then((response) => {
            this.messages = response.data;
          });
      }
    },
    getSheetInfo(entityId) {
      return "/reimburse/info/daily/" + entityId;
    },

    // 将当前的站内信列表都更新为已读状态
    readAllMessage() {
      this.axios
        .post("/message/read", {
          messages: this.messages,
        })
        .then(() => {
          this.$message({
            message: "已将当前站内信标记为已读，正在刷新列表...",
            type: "success",
          });
          // 刷新站内信列表
          this.messages = null;
          this.handleClick();
        });
    },

    // 根据目前是否有站内信，决定是否展示no-message-tip
    isNoMessage() {
      if (this.messages.length === 0) {
        return "display-none";
      }
      return "";
    },
  },
  mounted() {
    // 先获取系统通知，防止bug
    this.axios
      .get("/message/systemNotice", {
        params: {
          userId: this.$store.state.userId,
        },
      })
      .then((response) => {
        this.messages = response.data;
      });
  },
};
</script>

<style scoped>
.header {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  padding: 10px;
}

.header-user {
  display: flex;
  align-items: center;
}

.header-user-image {
  width: 45px;
  height: 45px;
  border-radius: 50%;
  cursor: pointer;
  margin-right: 8px;
}

.header-user-username {
  font-weight: bold;
  font-size: 18px;
}

.header-message {
  margin-right: 20px;
}

.btn-changeAccount {
  margin-right: 20px;
}

.message-box {
  width: 500px;
}

.message-content-container {
  height: 400px; /* 设置容器高度 */
  overflow-y: auto; /* 当内容超出容器高度时显示滚动条 */
}

.message-card {
  margin-bottom: 20px;
  height: 200px;
}

.message-header {
  display: flex;
  justify-content: space-between;
}

.message-time {
  color: #999;
}

.no-message-tip {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: rgba(0, 0, 0, 0.45);
  height: 300px;
  line-height: 50px;
}

.display-none {
  display: none;
}
</style>
