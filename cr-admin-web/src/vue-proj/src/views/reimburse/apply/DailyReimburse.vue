<template>
  <div class="center-container">
    <!-- 报销单基本信息 -->
    <el-card>
      <div>
        <h2 style="padding-left: 40px; display: inline-block">基本信息</h2>
        <el-button
          type="success"
          style="display: inline-block; float: right"
          round
          disabled
          >当前所处流程：{{ dailySheetInfo.curNodeType }}</el-button
        >
      </div>
      <el-form
        :model="dailySheetInfo"
        label-width="200px"
        @submit.native.prevent="submitExpense"
        style="padding-left: 40px; margin: 20px auto"
      >
        <el-row>
          <el-col :span="9">
            <el-form-item label="报销金额合计" prop="expenseTitle">
              <div class="underline-text">
                <span style="color: red; font-size: 25px">
                  {{ totalPrice }}元
                </span>
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="9">
            <el-form-item label="申请日期" prop="expenseDate">
              <div class="underline-text">
                <span style="margin-left: 20px">{{
                  dailySheetInfo.createTime
                }}</span>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="9">
            <el-form-item label="标题" prop="expenseDate">
              <div class="underline-text">
                <span style="margin-left: 20px">{{ dailySheetInfo.name }}</span>
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="9">
            <el-form-item label="报销人" prop="expenseTitle">
              <div class="underline-text">
                <span style="margin-left: 20px">{{
                  dailySheetInfo.applicantName
                }}</span>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="9">
            <el-form-item label="报销状态" prop="expenseDate">
              <div class="underline-text">
                <span style="margin-left: 20px">{{
                  dailySheetInfo.state
                }}</span>
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="9">
            <el-form-item label="相关项目" prop="relevantProj">
              <div class="underline-text">
                <el-input
                  placeholder="请输入相关项目名"
                  v-model="dailySheetInfo.relevantProj"
                ></el-input>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="9">
            <el-form-item label="相关发票" prop="expenseDate">
              <div>
                <!-- <span style="margin-left: 20px">顶头span</span> -->
                <el-select
                  v-model="selectedInvoiceList"
                  multiple
                  placeholder="请选择相关发票"
                  ref="invoiceSelectBase"
                  @focus="showInvoiceSelectDialog"
                >
                </el-select>
                <el-dialog :visible.sync="invoiceSelectDialogVisible">
                  <span slot="title" class="dialog-title">我的票夹</span>
                  <InvoiceDialog ref="invoiceDialog"></InvoiceDialog>
                  <span slot="footer" class="dialog-footer">
                    <el-button @click="invoiceSelectDialogVisible = false"
                      >取 消</el-button
                    >
                    <el-button
                      type="primary"
                      @click="selectDialogConfirmHandler()"
                      >确 定</el-button
                    >
                  </span>
                </el-dialog>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>

    <!-- 费用条目 -->
    <el-card style="margin-top: 20px">
      <h2 style="padding-left: 40px">费用事项</h2>
      <div class="table-button-container">
        <div style="margin-left: 85%">
          <el-button @click="addRow" class="add-row-btn">+</el-button>
          <el-button @click="removeSelectedRow" class="add-row-btn"
            >-</el-button
          >
        </div>
        <el-table
          :data="dailySheetInfo.dailySheetInfoList"
          style="width: 90%; margin-left: 5%"
          border
          stripe
          @selection-change="handleSelectionChange"
          :row-key="(row) => row.id"
        >
          <el-table-column type="selection"></el-table-column>
          <el-table-column label="费用日期" prop="costDate">
            <template slot-scope="scope">
              <el-date-picker
                v-model="scope.row.costDate"
                type="date"
                placeholder="选择费用日期"
              >
              </el-date-picker>
            </template>
          </el-table-column>
          <el-table-column label="类型" prop="costType">
            <template slot-scope="scope">
              <el-select
                v-model="scope.row.costType"
                placeholder="选择费用类型"
              >
                <el-option label="设备使用成本" value="0"></el-option>
                <el-option label="日常用品采购成本" value="1"></el-option>
                <el-option label="培训课程费用" value="2"></el-option>
                <el-option label="电话费" value="3"></el-option>
                <el-option label="交通费" value="4"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="费用说明" prop="description">
            <template slot-scope="scope">
              <el-input
                v-model="scope.row.description"
                placeholder="填写费用说明"
              ></el-input>
            </template>
          </el-table-column>
          <el-table-column label="报销金额" prop="price">
            <template slot-scope="scope">
              <el-input
                v-model="scope.row.price"
                placeholder="填写报销金额"
              ></el-input>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>
  </div>
</template>

<script>
import InvoiceDialog from "@/components/InvoiceSelectDialog.vue";

export default {
  components: {
    InvoiceDialog,
  },
  data() {
    return {
      // 日常费用报销单信息
      dailySheetInfo: {
        // 报销单基本信息
        applicantId: this.$store.state.userId,
        price: 0, // 报销总金额
        name: "", // 报销标题
        createTime: null, // 申请日期
        relevantProj: "", // 相关项目名
        applicantName: "",
        state: "报销发起", // 报销单状态
        curNodeType: "", // 流程状态
        curNodeOprUser: null,

        // 费用事项列表
        dailySheetInfoList: [
          // {
          //   costDate: null,
          //   costType: null,
          //   description: "",
          //   invoiceId: null,
          //   price: null,
          // },
        ],
      },
      sheetState: {
        "-1": "报销失败",
        0: "报销中",
        1: "报销完成",
      },
      processNodeType: {
        0: "报销发起",
        1: "业务审批",
        2: "出纳付款",
        3: "流程转发",
        4: "报销完成",
      },
      costTypes: {
        0: "设备使用成本",
        1: "日常用品采购成本",
        2: "培训课程费用",
        3: "电话费",
        4: "交通费",
      },
      selectedRows: [], // 被选中的行

      selectedInvoiceList: [], // 被选中的发票
      invoiceSelectDialogVisible: false, // 是否展示发票选择dialog
    };
  },
  mounted() {
    const url = this.$route.path;

    console.log(this.$store.state.isApplyView);
    // 如果是申请页面，则自动生成信息
    if (url === "/apply/daily") {
      this.getCurrentDate();
      this.getSheetName();
      this.getApplicantName();
      this.dailySheetInfo.curNodeType = "报销发起";
      this.$store.state.isApplyView = true;
    } else {
      // 查看信息页面，获取信息并渲染
      this.getDailyReimburseInfo(this.$route.params.id);
      this.$store.state.sheetId = this.$route.params.id;

      this.isApplyView = false;
      this.$store.state.isApplyView = false;

      // 判断当前用户是否负责操作此报销单
      if (this.dailySheetInfo.curNodeOprUser === this.$store.state.userId) {
        this.$store.state.isOprUser = true;
      }
    }
  },
  computed: {
    totalPrice() {
      let price = 0;
      for (let i = 0; i < this.dailySheetInfo.dailySheetInfoList.length; i++) {
        let addPrice = Number(this.dailySheetInfo.dailySheetInfoList[i].price);
        price += addPrice;
      }
      return price;
    },
  },
  methods: {
    addRow() {
      this.dailySheetInfo.dailySheetInfoList.push({
        costDate: null,
        costType: null,
        description: "",
        price: null,
      });
    },
    // 记录被选中的行
    handleSelectionChange(selection) {
      this.selectedRows = selection;
    },
    // 删掉被选中的行
    removeSelectedRow() {
      for (let i = 0; i < this.selectedRows.length; i++) {
        for (
          let j = 0;
          j < this.dailySheetInfo.dailySheetInfoList.length;
          j++
        ) {
          if (
            this.dailySheetInfo.dailySheetInfoList[j] == this.selectedRows[i]
          ) {
            this.dailySheetInfo.dailySheetInfoList.splice(j, 1);
            break;
          }
        }
      }
      this.selectedRows = [];
    },
    // 获取当前日期
    getCurrentDate() {
      const today = new Date();
      const year = today.getFullYear();
      const month = String(today.getMonth() + 1).padStart(2, "0");
      const day = String(today.getDate()).padStart(2, "0");

      this.dailySheetInfo.createTime = `${year}-${month}-${day}`;
    },
    // 获取报销单标题
    getSheetName() {
      let name = "";
      name += "日常费用报销";
      name += "-";
      name += this.$store.state.userInfo.realName || "无名氏";
      name += "-";
      name += this.dailySheetInfo.createTime;

      this.dailySheetInfo.name = name;
    },
    getApplicantName() {
      this.dailySheetInfo.applicantName =
        this.$store.state.userInfo.realName || "未登录";
    },

    // 提交日常报销单
    submitDailyReimburse() {
      this.axios.post("/reimburse/applyDaily", {
        applicantId: this.dailySheetInfo.applicantId,
        price: this.totalPrice,
        name: this.dailySheetInfo.name,
        createTime: this.dailySheetInfo.createTime,
        relevantProj: this.dailySheetInfo.relevantProj,
        dailySheetInfoReqDTOList: this.dailySheetInfo.dailySheetInfoList,
      });
    },
    // 根据报销单id，获取日常报销单信息
    getDailyReimburseInfo(id) {
      this.axios
        .get("/reimburse/daily/info", {
          params: {
            sheetId: id,
          },
        })
        .then((response) => {
          var data = response.data;
          // 将常量值转换为常量名
          data.state = this.sheetState[data.state];
          for (let i = 0; i < data.dailySheetInfoList.length; i++) {
            data.dailySheetInfoList[i].costType =
              this.costTypes[data.dailySheetInfoList[i].costType];
          }
          this.dailySheetInfo = data;
        });
    },

    // 展示发票选择dialog
    showInvoiceSelectDialog() {
      // 关闭选择框的下拉
      this.$refs.invoiceSelectBase.blur();
      this.invoiceSelectDialogVisible = true;
    },

    selectDialogConfirmHandler() {
      var selectedRows = this.$refs.invoiceDialog.selectedRows;
      // 格式化每个值
      for (let i = 0; i < selectedRows.length; i++) {
        selectedRows[i].value = "牛逼" + i;
        selectedRows[i].key = "牛逼" + i;
        selectedRows[i].label = "牛逼" + i;
      }
      this.selectedInvoiceList = selectedRows;

      console.log(this.selectedInvoiceList);
      this.invoiceSelectDialogVisible = false;
    },
  },
};
</script>

<style scoped>
/* 根据需要添加样式 */

.el-form-item__label {
  font-size: 200px; /* 设置标签的字体大小 */
}
.el-input,
.el-date-picker,
.el-button {
  font-size: 16px; /* 设置输入框和按钮的字体大小 */
}

.el-card {
  width: 80%;
  margin-left: 10%;
}

.underline-text {
  border: none;
  border-bottom: 2px solid #ccc; /* 下划线样式，可以根据需要调整颜色和粗细 */
  width: 250px; /* 可以根据需要调整宽度 */
  white-space: pre-wrap; /* 保留换行符 */
}

.dialog-title {
  width: fit-content;
  font-size: 26px;
  font-weight: 600;
}
</style>
