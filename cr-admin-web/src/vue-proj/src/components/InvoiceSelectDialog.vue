<template>
  <div>
    <el-container>
      <div class="search-component">
        <el-tabs
          class="search-tabs"
          v-model="selectedTab"
          @tab-click="changeReimburseList"
        >
          <el-tab-pane label="可报销" name="canReimburse"></el-tab-pane>
          <el-tab-pane label="不可报销" name="canNotReimburse"></el-tab-pane>
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
import format from "date-fns/format";
export default {
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

      selectedTab: null,
    };
  },
  mounted() {
    // 获取发票列表
    this.getOwnerInvoiceList();
  },
  methods: {
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

    // 记录被选中的行
    handleSelectionChange(selection) {
      this.selectedRows = selection;
      // console.log(selection);
    },

    // 改变显示的列表
    changeReimburseList() {
      // 可报销
      if (this.selectedTab === "canReimburse") {
        this.getInvoiceListCanReimburse();
      } else {
        // 不可报销
        this.getInvoiceListCanNotReimburse();
      }
    },

    // 获取未报销的发票列表
    getInvoiceListCanReimburse() {
      this.axios
        .post("/invoice/list/selective", {
          ownerId: this.$store.state.userId,
          isReimbursed: 0,
        })
        .then((response) => {
          var data = response.data;
          this.formatReimburseList(data);
          this.invoiceList = data;
        });
    },

    // 获取不可报销的发票列表
    getInvoiceListCanNotReimburse() {
      this.axios
        .get("/invoice/list/canNotReimburse", {
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
  },
};
</script>

<style scoped>
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
  margin-top: 4%;
}
</style>
