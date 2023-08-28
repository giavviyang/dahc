<template>
  <div class="uploadContainer">
    <el-upload
      class="upload-demo"
      drag
      :action="actionUrl"
      ref="upload"
      :on-success="recommendedHeader"
      :on-preview="beforeAvatarUpload"
      :before-remove="beforeRemoveFile"
      multiple
      :limit="10"
      :on-exceed="handleExceedFile"
      :file-list="fileList"
      :auto-upload="false">
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
    </el-upload>
  </div>
</template>

<script>
import {getToken} from "@/utils/auth";
import {queryTemplateRelevanceAccordingToExcel} from "@/api/projectManage/projectInitialize";

export default {
  name: "zipUpload",
  props: {
    // // 图片数量限制
    // limit: {
    //   type: Number,
    //   default: 5,
    // },
    // // 大小限制(MB)
    // fileSize: {
    //   type: Number,
    //   default: 5,
    // },
    // // 文件类型, 例如['png', 'jpg', 'jpeg']
    // fileType: {
    //   type: Array,
    //   default: () => ["png", "jpg", "jpeg"],
    // },
    fileList:{
      type:Array,
      default:()=>[]
    }
  },
  data() {
    return {
      actionUrl: process.env.VUE_APP_BASE_API + '/datatemplate/uploadFileOfZip',
      returnState:20000,
      uploadFiles:[],
      fileUpload:false,
    }
  },
  methods: {
    handleExceedFile(files, fileList) {
      this.$message.warning(`当前限制选择 10 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    beforeRemoveFile(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    recommendedHeader(res, file, fileList) {
      if (res.code === this.returnState) {
        this.$message({
          message: res.msg,
          type: 'success'
        });
      }
    },
    //上传前对文件进行解析和推荐表头
    beforeAvatarUpload(file) {
      let formData = new FormData()
      formData.append('file', file.raw)
    },

  },
}
</script>

<style lang="scss" scoped>
.uploadContainer{
  width: 100%;

.el-upload{
    width: 100%;
    .el-upload-dragger{
      width: 100%;
    }
  }
  .el-upload__tip{
    text-align: center;
  }
}

</style>
