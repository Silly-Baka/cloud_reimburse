<template>
  <el-upload
    class="upload-demo"
    ref="upload"
    action="http://localhost:8089/api/v1/invoice/uploadInvoiceFile"
    :before-upload="handleBefore"
    :file-list="fileList"
    :auto-upload="false"
    :data="this.data"
  >
    <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
    <el-button
      style="margin-left: 10px"
      size="small"
      type="success"
      @click="submitUpload"
      >上传到服务器</el-button
    >
    <div slot="tip" class="el-upload__tip">
      只能上传jpg/png文件，且不超过500kb
    </div>
  </el-upload>
</template>

<script>
export default {
  data() {
    return {
      fileList: [],
      data: {
        invoiceId: null,
      },
    };
  },
  methods: {
    submitUpload() {
      this.$refs.upload.submit();
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${
          files.length + fileList.length
        } 个文件`
      );
    },
    handleBefore() {
      this.data.invoiceId = this.$store.state.invoiceId;
    },
    beforeRemove(file) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
  },
};
</script>

<style scoped></style>
