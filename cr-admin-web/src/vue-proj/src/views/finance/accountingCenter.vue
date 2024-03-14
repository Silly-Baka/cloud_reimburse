<template>
  <div>
    <!-- 查询条件 -->
    <div class="search-bar">
      <div class="bitDiv">
        <div style="display: inline-block; margin: 2px 10px">
          <span class="search-style"> 报销单名称 </span>
          <el-input
            v-model="searchForm.sheetName"
            placeholder="请输入报销单名称"
            style="width: 200px"
          ></el-input>
        </div>
        <div style="display: inline-block; margin: 2px 10px">
          <span class="search-style"> 发起日期 </span>
          <el-date-picker
            v-model="searchForm.dateRange"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          ></el-date-picker>
        </div>
        <div style="display: inline-block; margin: 2px 10px">
          <span class="search-style"> 支付状态 </span>
          <el-select
            v-model="searchForm.state"
            placeholder="请选择"
            style="display: inline-block"
          >
            <el-option label="全部" value=""></el-option>
            <el-option label="已支付" value="-1"></el-option>
            <el-option label="未支付" value="0"></el-option>
          </el-select>
        </div>
        <el-button type="primary" @click="searchSelective">查询</el-button>
        <el-button type="primary" @click="handleMultiPay()">批量支付</el-button>
      </div>
    </div>

    <!-- 报销单信息表格 -->
    <el-table
      :data="toPaySheetList"
      style="width: 100%; margin-top: 10px"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection"></el-table-column>
      <el-table-column label="报销单名称" prop="name"> </el-table-column>
      <el-table-column label="报销类型" prop="type" sortable></el-table-column>
      <el-table-column
        label="申请日期"
        prop="createTime"
        sortable
      ></el-table-column>
      <el-table-column
        label="申请人"
        prop="applicantName"
        sortable
      ></el-table-column>
      <el-table-column label="金额" prop="price" sortable></el-table-column>
      <el-table-column
        label="支付状态"
        prop="payState"
        sortable
      ></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            type="warning"
            @click="handleSinglePay(scope.row.id, scope.row.price)"
            plain
            :class="{ 'display-none': scope.row.payState === '已支付' }"
            >支付</el-button
          >
        </template>
      </el-table-column>
      <!-- 其他报销单信息列 -->
    </el-table>

    <el-dialog :visible.sync="payDialogVisible">
      <span slot="title" class="dialog-title">付款支付</span>
      <PaymentDialog ref="paymentDialog"></PaymentDialog>
      <span slot="footer" class="dialog-footer">
        <el-button @click="payDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleRealPay">支付</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import PaymentDialog from "../../components/PaymentDialog";

export default {
  components: {
    PaymentDialog,
  },
  data() {
    return {
      searchForm: {
        sheetName: "", // 报销单名称
        state: "", // 流程状态
        dateRange: [], // 事件范围
      },
      toPaySheetList: [
        {
          id: 0,
          name: "牛逼",
          type: "测试用类型",
          createTime: "0000-00-00",
          applicantName: "测试人员",
          price: "666.05",
          payState: "未支付",
        },
        {
          id: 1,
          name: "牛逼",
          type: "测试用类型",
          createTime: "0000-00-00",
          applicantName: "测试人员",
          price: "333.10",
          payState: "未支付",
        },
      ], // 报销单信息表格数据

      selectedRows: [], // 被选中的行
      payDialogVisible: false, // 支付浮窗是否可见

      paySheetIdList: [], // 进行支付的报销单id列表

      sheetTypes: {
        0: "差旅费报销",
        1: "日常费用报销",
        2: "业务招待费用报销",
      },

      payStates: {
        0: "未支付",
        1: "已支付",
      },
    };
  },

  mounted() {
    // 获取当前用户需支付的报销单列表

    this.axios
      .get("/finance/toPay/list", {
        params: {
          userId: this.$store.state.userId,
        },
      })
      .then((response) => {
        var data = response.data;
        for (let i = 0; i < data.length; i++) {
          data[i].type = this.sheetTypes[data[i].type];
          data[i].payState = this.payStates[data[i].payState];
        }

        this.toPaySheetList = data;
      });
  },

  methods: {
    // 记录被选中的行
    handleSelectionChange(selection) {
      this.selectedRows = selection;
    },

    // 单报销单支付
    handleSinglePay(sheetId, price) {
      let arr = new Array();
      arr.push(sheetId);

      this.$store.state.payAmount = price;
      this.paySheetIdList = arr;
      this.payDialogVisible = true;
    },

    // 批量支付
    handleMultiPay() {
      let total = 0;
      let arr = new Array();
      for (let i = 0; i < this.selectedRows.length; i++) {
        total = total + Number(this.selectedRows[i].price);
        arr.push(this.selectedRows[i].id);
      }
      this.$store.state.payAmount = total;
      this.paySheetIdList = arr;
      this.payDialogVisible = true;
    },

    // 真正的支付逻辑
    handleRealPay() {
      console.log(this.paySheetIdList);

      this.axios
        .post("/finance/pay", {
          reimburseSheetIdList: this.paySheetIdList,
          todoUser: this.$store.state.userId,
        })
        .then(() => {
          // 展示支付成功界面
          this.$message({
            message: "支付成功",
            type: "success",
          });
          this.payDialogVisible = false;

          location.reload();
        });
    },

    // 条件查询
    searchSelective() {},
  },
};
</script>

<style scoped>
.el-table {
  margin-top: 0px;
  line-height: 0px;
}

.display-none {
  display: none;
}
</style>
