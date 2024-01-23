<template>
  <div>
    <el-container>
      <!-- 发票新增、编辑相关按钮 -->
      <el-header class="header-container">
        <div class="header-title">我的发票夹</div>
        <el-dropdown class="header-button">
          <el-button>
            批量操作<i class="el-icon-arrow-down el-icon--right"></i>
          </el-button>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>查验</el-dropdown-item>
            <el-dropdown-item>核销</el-dropdown-item>
            <el-dropdown-item>下载</el-dropdown-item>
            <el-dropdown-item @click.native="removeSelectedInvoice"
              >删除</el-dropdown-item
            >
            <!-- <el-dropdown-item>编辑</el-dropdown-item> -->
          </el-dropdown-menu>
        </el-dropdown>
        <el-button class="header-button">发起报销</el-button>
        <el-button class="header-button" @click="addDialogVisable = true"
          >手动录入</el-button
        >
        <el-dialog title="录入发票" :visible.sync="addDialogVisable">
          <!-- 发票文件-上传附件 -->
          <div class="inv-file">
            <div class="inv-file-content">发票文件</div>
            <UploadComponent
              class="inv-file-upload"
              ref="infUpload"
            ></UploadComponent>
          </div>
          <div class="inv-form">
            <div class="inv-form-content">发票信息</div>
            <InvoiceUploadForm
              class="inf-form-info"
              ref="infForm"
            ></InvoiceUploadForm>
          </div>
          <div class="inv-button-container">
            <el-button class="inv-button" size="small" @click="cancelUpload"
              >取消</el-button
            >
            <el-button
              class="inv-button"
              type="primary"
              size="small"
              @click="uploadInvoice"
              >保存</el-button
            >
          </div>
        </el-dialog>
        <el-button class="header-button">文件识别</el-button>
      </el-header>

      <div class="search-component">
        <el-tabs
          class="search-tabs"
          v-model="isReimbursed"
          @tab-click="changeReimburseList"
        >
          <el-tab-pane label="全部" name="-1"></el-tab-pane>
          <el-tab-pane label="未报销" name="0"></el-tab-pane>
          <el-tab-pane label="报销中" name="1"></el-tab-pane>
          <el-tab-pane label="已报销" name="2"></el-tab-pane>
        </el-tabs>
      </div>

      <!-- 发票信息列表 -->
      <el-main class="main-container">
        <!-- 发票信息表格 -->
        <el-table
          :data="invoiceList"
          style="width: 100%"
          stripe
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection"></el-table-column>
          <!-- 展示费项信息的下拉框 -->
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-table
                class="inner-table"
                :data="props.row.invoiceInfoList"
                style="width: 100%"
                stripe
              >
                <el-table-column label="名称" prop="name"></el-table-column>
                <el-table-column
                  label="总金额"
                  prop="totalPrice"
                ></el-table-column>
                <el-table-column label="税率" prop="taxRate"></el-table-column>
                <el-table-column label="税额" prop="taxPrice"></el-table-column>
                <el-table-column
                  label="单价"
                  prop="unitPrice"
                ></el-table-column>
                <el-table-column label="数量" prop="quantity"></el-table-column>
                <el-table-column label="单位" prop="unit"></el-table-column>
                <el-table-column label="规格型号" prop="spec"></el-table-column>
              </el-table>
            </template>
          </el-table-column>

          <!-- 表格内显示的发票信息 -->
          <el-table-column label="开票日期" prop="invDate" width="110" sortable>
          </el-table-column>
          <el-table-column
            label="发票代码"
            prop="invCode"
            width="120"
          ></el-table-column>
          <el-table-column
            label="发票号码"
            prop="invNum"
            width="120"
          ></el-table-column>
          <el-table-column
            label="发票类型"
            prop="invType"
            width="120"
            sortable
          ></el-table-column>
          <el-table-column
            label="销售方"
            prop="sellName"
            width="120"
          ></el-table-column>
          <el-table-column
            label="销售方纳税人识别号"
            prop="sellTaxpayerNum"
            width="150"
          ></el-table-column>
          <el-table-column
            label="购买方"
            prop="buyName"
            width="120"
          ></el-table-column>
          <el-table-column
            label="购买方纳税人识别号"
            prop="buyTaxpayerNum"
            width="150"
          ></el-table-column>
          <el-table-column
            label="价税合计"
            prop="priceTaxTotal"
            width="110"
            sortable
          ></el-table-column>
          <el-table-column
            label="税额"
            prop="taxTotal"
            sortable
          ></el-table-column>
          <el-table-column
            label="报销状态"
            prop="isReimbursed"
            width="110"
            sortable
          ></el-table-column>
          <el-table-column
            label="报销日期"
            prop="reimbursedDate"
            width="110"
            sortable
          ></el-table-column>
          <el-table-column
            label="所处报销单"
            prop="sheetId"
            width="120"
          ></el-table-column>

          <!-- 其他报销单信息列 -->
        </el-table>
      </el-main>

      <!-- 分页、税额合计等信息 -->
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
import UploadComponent from "../../components/UploadComponent.vue";
import InvoiceUploadForm from "@/components/InvoiceUploadForm.vue";
import format from "date-fns/format";
export default {
  components: {
    UploadComponent,
    InvoiceUploadForm,
  },
  data() {
    return {
      addDialogVisable: false, // 是否展示手动录入发票的对话框
      currentPage: 1, // 当前页号
      total: 1, // 记录总条数
      fileList: [], // 上传文件列表
      invoiceList: [
        {
          id: null,
          invCode: "031002100411",
          invNum: "41191345",
          invType: "增值税普通发票", // 发票类型，0-增值税普通发票，1-增值税专用发票，2-增值税电子普通发票，3-增值税电子专用发票
          invDate: "2022-01-19",
          priceTotal: 66.4, // 不算税的总金额
          taxTotal: "2.0", // 税总额
          priceTaxTotal: 68.4, // 价税合计
          buyName: "购买方测试用",
          buyTaxpayerNum: "91310000703228XXXD",
          sellName: "销售方测试用",
          sellTaxpayerNum: "91310114MA1GW6UUUU",
          invFileId: null,
          isReimbursed: "已报销", // 是否已报销，0-否，1-报销中，2-已报销
          reimbursedDate: "2024-01-13", // 报销日期
          sheetId: null, // 所处报销单id

          // 具体费项信息列表
          invoiceInfoList: [
            {
              id: null,
              invId: null,
              name: "*运输服务*客运服务费 ",
              spec: "",
              unit: "次",
              quantity: 1,
              unitPrice: 67.87,
              totalPrice: 67.87,
              taxRate: "3.00%",
              taxPrice: 2.04,
            },
          ],
        },
      ],
      reimburseStates: {
        0: "未报销",
        1: "报销中",
        2: "已报销",
      },
      invoiceTypes: {
        0: "增值税普通发票",
        1: "增值税专用发票",
        2: "增值税电子普通发票",
        3: "增值税电子专用发票",
      },

      selectedRows: [], // 被选中的行

      isReimbursed: null,
    };
  },
  mounted() {
    // 获取发票列表
    this.getOwnerInvoiceList();
  },
  methods: {
    // 上传发票信息
    async uploadInvoice() {
      var flag = true;
      // 检查表单合法性，若合法则上传
      await this.$refs.infForm.$refs.invoiceForm.validate((valid) => {
        // 合法，上传表单信息
        if (valid) {
          console.log("已触发上传发票");
          this.$refs.infForm.uploadInvoice();
        } else {
          console.log("上传发票表单内容不合法，上传失败");
          flag = false;
        }
      });
      if (!flag) {
        this.$message({
          message: "请检查发票必要信息是否已填写！",
          type: "warning",
        });
        return;
      }

      console.log("目前的发票id为：" + this.$store.state.invoiceId);
      // 再上传发票文件
      this.$refs.infUpload.$refs.upload.submit();
      console.log("已触发上传发票文件");

      // 关闭dialog，并显示上传成功
      this.addDialogVisable = false;
      this.$message({
        message: "上传发票成功",
        type: "success",
      });
    },
    // 取消上传
    cancelUpload() {
      this.addDialogVisable = false;
      this.$message("已取消手动录入");
    },

    // 获取自己的发票列表
    getOwnerInvoiceList() {
      this.axios
        .get("/invoice/getOwnInvoice", {
          params: {
            ownerId: this.$store.state.userId,
          },
        })
        .then((response) => {
          var data = response.data;
          this.formatReimburseList(data);
          this.invoiceList = data;
        });
    },

    formatReimburseList(list) {
      for (let i = 0; i < list.length; i++) {
        // 格式化开票日期
        var tmpDate = new Date(list[i].invDate);
        list[i].invDate = format(tmpDate, "yyyy-MM-dd");

        // 格式化报销状态
        list[i].isReimbursed = this.reimburseStates[list[i].isReimbursed];

        // 格式化发票类型
        list[i].invType = this.invoiceTypes[list[i].invType];
      }
    },

    // 删除被选中的发票
    removeSelectedInvoice() {
      console.log("已触发删除");
      this.$confirm(
        "此操作将删除被选中的发票信息包括其文件，是否确认?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).then(() => {
        // 发请求让后台删除指定列
        var invoiceIds = [];
        for (let i = 0; i < this.selectedRows.length; i++) {
          invoiceIds.push(this.selectedRows[i].id);
        }
        this.axios.post("/invoice/delete/list", invoiceIds).then((response) => {
          if (response.data) {
            console.log("删除发票列表成功");
          } else {
            console.log("删除发票列表失败");
          }
        });

        // 删掉列表中相应的列
        for (let i = 0; i < this.selectedRows.length; i++) {
          for (let j = 0; j < this.invoiceList.length; j++) {
            if (this.invoiceList[j] == this.selectedRows[i]) {
              this.invoiceList.splice(j, 1);
              break;
            }
          }
        }
        this.selectedRows = [];

        // this.$message({
        //   type: "success",
        //   message: "删除发票成功",
        // });
      });
    },

    // 记录被选中的行
    handleSelectionChange(selection) {
      this.selectedRows = selection;
      console.log(selection);
    },

    // 根据reimburseShowState改变显示的列表
    changeReimburseList() {
      if (this.isReimbursed === "-1") {
        this.isReimbursed = null;
      }
      this.getInvoiceListSelective();
    },

    // 根据条件获取发票列表
    getInvoiceListSelective() {
      this.axios
        .post("/invoice/list/selective", {
          isReimbursed: this.isReimbursed,
        })
        .then((response) => {
          var data = response.data;
          this.formatReimburseList(data);
          this.invoiceList = data;
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

.search-component {
  background-color: white;
  height: 40px;
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

.inv-file {
  position: relative;
  /* background: aqua; */
}
.inv-file-content {
  width: fit-content;
  font-size: 18px;
  font-weight: 600;
}
.inv-file-upload {
  /* position: relative;
  float: right; */
}

.inv-form {
  position: relative;
  margin-top: 30px;
  /* background: pink; */
}
.inv-form-content {
  width: fit-content;
  font-size: 18px;
  font-weight: 600;
}
.inf-form-info {
}

.inv-button-container {
  position: relative;
  top: 20px;
  height: 50px;
}
.inv-button {
  position: relative;
  float: right;
  margin-left: 12px;
  top: 50%;
  transform: translateY(-50%);
}
</style>
