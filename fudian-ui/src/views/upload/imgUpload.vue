<template>
<div>
<!--  <el-upload-->
<!--    class="upload-demo"-->
<!--    :action="actionUrl"-->
<!--    :on-preview="handlePreview"-->
<!--    :on-remove="handleRemove"-->
<!--    :before-remove="beforeRemove"-->
<!--    multiple-->
<!--    :limit="1"-->
<!--    :on-exceed="handleExceed"-->
<!--    :file-list="fileList">-->
<!--    <el-button size="small" type="primary">点击上传</el-button>-->
<!--    <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>-->
<!--  </el-upload>-->
  <el-dialog
    :title="'替换（当前页号为：'+theCurrentPage+'）'"
    :visible.sync="dialogImgUpload"
    width="600px" class="dialog-style"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :destroy-on-close="true"
    :before-close="closeImgUpload">

  <el-upload
    class="upload-demo"
    drag
    :action="actionUrl"
    :on-preview="handlePreview"
    :on-remove="handleRemove"
    :before-remove="beforeRemove"
    :on-change="handleChangeFile"
    multiple
    :limit="1"
    :on-exceed="handleExceed"
    :file-list="fileList"
    >
    <i class="el-icon-upload"></i>
    <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
    <div class="el-upload__tip" slot="tip">只能上传jpg/png文件</div>
  </el-upload>


    <span slot="footer" class="dialog-footer">
      <el-button
        type="primary"
        size="mini"
        @click="uploadAdd()">
        保存
      </el-button>
      <el-button

        size="mini"
        @click="closeImgUpload">
        取消
      </el-button>

    </span>
  </el-dialog>
</div>
</template>

<script>
export default {
  name: "imgUpload",
  props: {
    dialogImgUpload: {
      type: Boolean,
      default: false,
    },
    theCurrentPage: {
      type: Number,
      default: 0,
    },
  },
  data() {
    return {

      fileList: [],
      actionUrl: process.env.VUE_APP_BASE_API + '/photoCheck/imagePlusPage',
    };
  },
  methods: {
    uploadAdd() {
      this.$emit("uploadAdd");
    },
    closeImgUpload() {
      this.$emit("closeImgUpload", false);
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handleChangeFile(file, fileList) {
      // console.log(file,"图片数据")
      this.$emit("handleChangeFile", file,fileList);
    },
    handlePreview(file) {
      console.log(file,"点击");
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${ file.name }？`);
    }
  }
}
</script>

<style scoped>

</style>
