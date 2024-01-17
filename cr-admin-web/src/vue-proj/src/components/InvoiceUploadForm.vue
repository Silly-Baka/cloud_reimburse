<template>
  <div>
    <el-form
      ref="invoiceForm"
      :model="invoice"
      label-width="138px"
      @submit.native.prevent="uploadInvoice"
      :inline="true"
      :rules="rules"
    >
      <el-form-item label="发票类型" prop="invType">
        <el-select v-model="invoice.invType" placeholder="选择发票类型">
          <el-option label="增值税普通发票" value="0"></el-option>
          <el-option label="增值税专用发票" value="1"></el-option>
          <el-option label="增值税电子普通发票" value="2"></el-option>
          <el-option label="增值税电子专用发票" value="3"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="开票日期" prop="invDate">
        <el-date-picker
          v-model="invoice.invDate"
          type="date"
          placeholder="选择费用日期"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="发票代码" prop="invCode">
        <el-input v-model="invoice.invCode" placeholder="未填写"></el-input>
      </el-form-item>
      <el-form-item label="发票号码" prop="invNum">
        <el-input v-model="invoice.invNum" placeholder="未填写"></el-input>
      </el-form-item>
      <el-form-item label="销售方" prop="sellName">
        <el-input v-model="invoice.sellName" placeholder="未填写"></el-input>
      </el-form-item>
      <el-form-item label="销售方纳税人识别号" prop="sellTaxpayerNum">
        <el-input
          v-model="invoice.sellTaxpayerNum"
          placeholder="未填写"
        ></el-input>
      </el-form-item>
      <el-form-item label="购买方" prop="buyName">
        <el-input v-model="invoice.buyName" placeholder="未填写"></el-input>
      </el-form-item>
      <el-form-item label="购买方纳税人识别号" prop="buyTaxpayerNum">
        <el-input
          v-model="invoice.buyTaxpayerNum"
          placeholder="未填写"
        ></el-input>
      </el-form-item>
      <el-form-item label="价税合计" prop="priceTaxTotal">
        <el-input
          v-model="invoice.priceTaxTotal"
          placeholder="未填写"
        ></el-input>
      </el-form-item>
      <el-form-item label="不含税金额" prop="priceTotal">
        <el-input v-model="invoice.priceTotal" placeholder="未填写"></el-input>
      </el-form-item>
      <el-form-item label="税额" prop="taxTotal">
        <el-input v-model="invoice.taxTotal" placeholder="未填写"></el-input>
      </el-form-item>
    </el-form>

    <div class="invoice-table-container">
      <!-- 发票详细信息 -->
      <div class="invoice-table-title">发票详细信息</div>
      <div style="margin-left: 80%">
        <el-button @click="addRow" class="add-row-btn">+</el-button>
        <el-button @click="removeSelectedRow" class="add-row-btn">-</el-button>
      </div>

      <el-table
        :data="invoice.invoiceInfoList"
        style="width: 95%; margin-left: 2.5%; top: 10px"
        border
        stripe
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection"></el-table-column>
        <el-table-column label="名称" prop="name" width="150">
          <template slot-scope="scope">
            <el-input v-model="scope.row.name" placeholder="未填写"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="总金额" prop="totalPrice" width="90">
          <template slot-scope="scope">
            <el-input
              v-model="scope.row.totalPrice"
              placeholder="未填写"
            ></el-input>
          </template>
        </el-table-column>
        <el-table-column label="税率" prop="taxRate">
          <template slot-scope="scope">
            <el-input
              v-model="scope.row.taxRate"
              placeholder="未填写"
            ></el-input>
          </template>
        </el-table-column>
        <el-table-column label="税额" prop="taxPrice">
          <template slot-scope="scope">
            <el-input
              v-model="scope.row.taxPrice"
              placeholder="未填写"
            ></el-input>
          </template>
        </el-table-column>
        <el-table-column label="数量" prop="quantity">
          <template slot-scope="scope">
            <el-input
              v-model="scope.row.quantity"
              placeholder="未填写"
            ></el-input>
          </template>
        </el-table-column>
        <el-table-column label="计数单位" prop="unit">
          <template slot-scope="scope">
            <el-input v-model="scope.row.unit" placeholder="未填写"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="单价" prop="unitPrice">
          <template slot-scope="scope">
            <el-input
              v-model="scope.row.unitPrice"
              placeholder="未填写"
            ></el-input>
          </template>
        </el-table-column>
        <el-table-column label="规格型号" prop="spec">
          <template slot-scope="scope">
            <el-input v-model="scope.row.spec" placeholder="未填写"></el-input>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      invoice: {
        // 发票的基本信息
        ownerId: null,
        invCode: "",
        invNum: "",
        invType: null,
        invDate: null,
        priceTotal: null,
        taxTotal: null,
        priceTaxTotal: null,
        buyName: "",
        buyTaxpayerNum: "",
        sellName: "",
        sellTaxpayerNum: "",
        invoiceInfoList: [],
      },
      selectedRows: [],

      // 验证逻辑
      rules: {
        invType: [
          {
            required: true,
            message: "请选择发票类型",
            trigger: "blur",
          },
        ],
        invDate: [
          { required: true, message: "请输入开票日期", trigger: "blur" },
        ],
        invCode: [
          {
            required: true,
            message: "请输入发票代码",
            trigger: "blur",
          },
        ],
        invNum: [
          {
            required: true,
            message: "请输入发票号码",
            trigger: "blur",
          },
        ],
        priceTaxTotal: [
          {
            required: true,
            message: "请输入价税合计",
            trigger: "blur",
          },
        ],
        taxTotal: [
          {
            required: true,
            message: "请输入税额",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    async uploadInvoice() {
      // 上传发票信息
      await this.axios
        .post("/invoice/upload", {
          ownerId: this.$store.state.userId,
          invCode: this.invoice.invCode,
          invNum: this.invoice.invNum,
          invType: this.invoice.invType,
          invDate: this.invoice.invDate,
          priceTotal: this.invoice.priceTotal,
          taxTotal: this.invoice.taxTotal,
          priceTaxTotal: this.invoice.priceTaxTotal,
          buyName: this.invoice.buyName,
          buyTaxpayerNum: this.invoice.buyTaxpayerNum,
          sellName: this.invoice.sellName,
          sellTaxpayerNum: this.invoice.sellTaxpayerNum,
          invoiceInfoList: this.invoice.invoiceInfoList,
        })
        .then((response) => {
          var invoiceId = response.data;

          // 保存到vuex中
          this.$store.state.invoiceId = invoiceId;
        });
    },
    addRow() {
      this.invoice.invoiceInfoList.push({});
    },
    // 记录被选中的行
    handleSelectionChange(selection) {
      this.selectedRows = selection;
    },
    // 删掉被选中的行
    removeSelectedRow() {
      for (let i = 0; i < this.selectedRows.length; i++) {
        for (let j = 0; j < this.invoice.invoiceInfoList.length; j++) {
          if (this.invoice.invoiceInfoList[j] == this.selectedRows[i]) {
            this.invoice.invoiceInfoList.splice(j, 1);
            break;
          }
        }
      }
      this.selectedRows = [];
    },
  },
};
</script>

<style scoped>
.invoice-table-container {
  position: relative;
}
.invoice-table-title {
  width: fit-content;
  font-size: 18px;
  font-weight: 600;
}
</style>
