<template>
  <div class="center-container">
    <!-- 报销单基本信息 -->
    <el-card>
      <h2 style="padding-left: 40px">基本信息</h2>
      <el-form
        :model="expenseForm"
        label-width="200px"
        @submit.native.prevent="submitExpense"
        style="padding-left: 40px; margin: 20px auto"
      >
        <el-row>
          <el-col :span="9">
            <el-form-item label="报销金额合计" prop="expenseTitle">
              <div class="underline-text">
                <span style="margin-left: 20px; color: red; font-size: 25px"
                  >9999999999元</span
                >
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="9">
            <el-form-item label="申请日期" prop="expenseDate">
              <div class="underline-text">
                <span style="margin-left: 20px">测试用日期</span>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="9">
            <el-form-item label="标题" prop="expenseDate">
              <div class="underline-text">
                <span style="margin-left: 20px">测试用标题</span>
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="9">
            <el-form-item label="报销人" prop="expenseTitle">
              <div class="underline-text">
                <span style="margin-left: 20px">测试人</span>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="9">
            <el-form-item label="报销状态" prop="expenseDate">
              <div class="underline-text">
                <span style="margin-left: 20px">测试用状态</span>
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
          :data="dailyInfoList"
          style="width: 90%; margin-left: 5%"
          border
          stripe
          v-loading="loading"
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
          <el-table-column label="报销金额" align="price">
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
export default {
  data() {
    return {
      dailyInfoList: [
        {
          costDate: null,
          costType: null,
          description: "",
          price: null,
        },
      ],
      selectedRows: [], // 被选中的行
    };
  },
  methods: {
    submitExpense() {
      // 在这里可以处理提交申请的逻辑，例如通过 API 发送请求
      console.log("Expense Form Submitted:", this.expenseForm);
    },
    addRow() {
      this.dailyInfoList.push({
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
        for (let j = 0; j < this.dailyInfoList.length; j++) {
          if (this.dailyInfoList[j] == this.selectedRows[i]) {
            this.dailyInfoList.splice(j, 1);
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
  width: 200px; /* 可以根据需要调整宽度 */
  white-space: pre-wrap; /* 保留换行符 */
}
</style>
