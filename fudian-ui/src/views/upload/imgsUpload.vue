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
    :title="'加页（当前页号为：'+theCurrentPage+'）'"
    :visible.sync="dialogImgUpload"
    width="600px" class="dialog-style"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :destroy-on-close="true"
    :before-close="closeImgsUpload">


    <el-radio v-model="radio" label="1">本页前</el-radio>
    <el-radio v-model="radio" label="2">本页后</el-radio>


  <el-upload
    class="upload-demo"
    drag
    :action="actionUrl"
    :on-preview="handlePreview"
    :on-remove="handleRemove"
    :before-remove="beforeRemove"
    :on-change="handleImgsChangeFile"
    multiple
    :limit="3"
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
        @click="uploadImgsAdd()">
        保存
      </el-button>
      <el-button

        size="mini"
        @click="closeImgsUpload">
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
    // radio: {
    //   type: String,
    //   default: '1',
    // },
  },
  data() {
    return {
      radio: '1',
      fileList: [],
      actionUrl: process.env.VUE_APP_BASE_API + '/photoCheck/imagePlusPage',
    };
  },
  methods: {
    uploadImgsAdd() {
      this.$emit("uploadImgsAdd",this.radio);
    },
    closeImgsUpload() {
      this.$emit("closeImgsUpload", false);
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handleImgsChangeFile(file, fileList) {
      this.$emit("handleImgsChangeFile", file,fileList);
    },
    handlePreview(file) {
      console.log(file,"点击");
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${ file.name }？`);
    }
  }
}
</script>

<style scoped>

</style>
