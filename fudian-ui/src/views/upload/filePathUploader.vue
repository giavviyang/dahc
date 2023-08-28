<template>
  <!-- 上传器 -->
  <el-dialog
    title="文件上传"
    :visible.sync="dialogSimpleUploader"
    width="600px" class="dialog-style"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :destroy-on-close="true"
    :before-close="closeFilePath">




    <el-form ref="refFile" :rules="rules" :model="fileFrom">
      <el-form-item label="文件夹路径" prop="filePath">
        <el-tooltip content="例如”D:\fudian\uploadPath“，上传服务器该目录下的所有图片数据" placement="top">
          <i class="el-icon-question"></i>
        </el-tooltip>
        <el-input
          type="textarea"
          :rows="2"
          placeholder="请输入内容"
          v-model="fileFrom.filePath">
        </el-input>
      </el-form-item>
    </el-form>

    <span slot="footer" class="dialog-footer">
      <el-button type="primary" size="mini" @click="uploadAdd('refFile')">
        上传
      </el-button>
      <el-button size="mini" @click="closeFilePath">
        取消
      </el-button>
    </span>
  </el-dialog>
</template>

<script>
  import {ACCEPT_CONFIG} from '@/utils/acceptConfig';
  import SparkMD5 from 'spark-md5';
  import Upload from "@/views/upload/Upload";
  import {mergeFile} from "@/api/upload/uploadFile";
  import {getInformation, readingPicturesFromAFileDirectory} from "@/api/fileData/fileData";

  export default {
    components: {Upload},
    name: 'simpleUploader',
    props: {
      dialogSimpleUploader: {
        type: Boolean,
        default: false
      },
      projectId: {
        type: String,
        default: '',
      },
      archivesId: {
        type: String,
        default: '',
      },
    },
    data() {
      return {
        // dialogSimpleUploader:false,
        options: {
          //目标上传 URL，默认POST
          target: process.env.VUE_APP_BASE_API + "/uploader/chunk",
          //分块大小(单位：字节)
          chunkSize: '2048000',
          //上传文件时文件内容的参数名，对应chunk里的Multipart对象名，默认对象名为file
          fileParameterName: 'upfile',
          //失败后最多自动重试上传次数
          maxChunkRetries: 3,
          //是否开启服务器分片校验，对应GET类型同名的target URL
          testChunks: true,
          /*
          服务器分片校验函数，判断秒传及断点续传,传入的参数是Uploader.Chunk实例以及请求响应信息
          reponse码是successStatuses码时，才会进入该方法
          reponse码如果返回的是permanentErrors 中的状态码，不会进入该方法，直接进入onFileError函数 ，并显示上传失败
          reponse码是其他状态码，不会进入该方法，正常走标准上传
          checkChunkUploadedByResponse函数直接return true的话，不再调用上传接口
          */
          checkChunkUploadedByResponse: function (chunk, response_msg) {
            let objMessage = JSON.parse(response_msg);
            if (objMessage.skipUpload) {
              return true;
            }
            return (objMessage.uploadedChunks || []).indexOf(chunk.offset + 1) >= 0;
          }
        },
        attrs: {
          accept: ACCEPT_CONFIG.getAll()
        },
        fileStatusText: {
          success: '上传成功',
          error: '上传失败',
          uploading: '上传中',
          paused: '暂停',
          waiting: '等待上传'
        },
        /*文件路径*/
        fileFrom: {
          filePath: '',
        },
        timerSwitch: '',
        // filePath: '',
        rules: {
          filePath: [{required: true, message: '请输入文件夹路径', trigger: 'blur'}]
        }
      }
    },
    methods: {

      closeFileExcel() {
        let show = false;
        this.$emit("closeFileExcel", show);
      },
      /*获取上传文件是否完成*/
      getInformation(val) {
        getInformation(val).then(res => {
          this.timerSwitch = res.data;
          if (res.data == '完成但是失败') {
            this.$message({message: res.msg, type: 'error', showClose: true, duration: 0});
          } else if (res.data == '成功') {
            this.$message({message: res.msg, type: 'success', showClose: true, duration: 0});
          }
        });
      },
      /*上传文件*/
      uploadAdd(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            const arr = {
              archivesId: this.archivesId,
              projectId: this.projectId,
              filePath: this.fileFrom.filePath,
            }
            readingPicturesFromAFileDirectory(arr).then(res => {
              if (res.code === 20000) {
                this.$message.success(res.msg)
                this.closeFilePath();
                this.timerSwitch = '';
                this.fileFrom.filePath = '';
                var interval = setInterval(() => {
                  if (this.timerSwitch == '完成但是失败') {
                    clearInterval(interval);
                    console.log("停止1")
                  } else if (this.timerSwitch == '成功') {
                    clearInterval(interval);
                    console.log("停止2")
                  } else {
                    this.getInformation(res.data);
                  }
                }, 5000) //五秒执行一次
              }
            });
          }
        });
      },
      closeFilePath() {
        let show = false;
        this.$emit("closeFilePath", show);
      },
      onFileAdded(file) {
        this.computeMD5(file);
      },
      /*
      第一个参数 rootFile 就是成功上传的文件所属的根 Uploader.File 对象，它应该包含或者等于成功上传文件；
      第二个参数 file 就是当前成功的 Uploader.File 对象本身；
      第三个参数就是 message 就是服务端响应内容，永远都是字符串；
      第四个参数 chunk 就是 Uploader.Chunk 实例，它就是该文件的最后一个块实例，如果你想得到请求响应码的话，chunk.xhr.status就是
      */
      onFileSuccess(rootFile, file, response, chunk) {
        //refProjectId为预留字段，可关联附件所属目标，例如所属档案，所属工程等
        file.projectId = this.projectId;
        file.archivesId = this.archivesId;
        mergeFile(file).then(responseData => {
          if (responseData.data.code === 415) {
            console.log("合并操作未成功，结果码：" + responseData.data.code);
          }

        }).catch(function (error) {
          console.log("合并后捕获的未知异常：" + error);
        });
      },
      onFileError(rootFile, file, response, chunk) {
        console.log('上传完成后异常信息：' + response);
      },

      /**
       * 计算md5，实现断点续传及秒传
       * @param file
       */
      computeMD5(file) {
        file.pause();

        //单个文件的大小限制2G
        let fileSizeLimit = 2 * 1024 * 1024 * 1024;
        console.log("文件大小：" + file.size);
        console.log("限制大小：" + fileSizeLimit);
        if (file.size > fileSizeLimit) {
          this.$message({
            showClose: true,
            message: '文件大小不能超过2G'
          });
          file.cancel();
        }

        let fileReader = new FileReader();
        let time = new Date().getTime();
        let blobSlice = File.prototype.slice || File.prototype.mozSlice || File.prototype.webkitSlice;
        let currentChunk = 0;
        const chunkSize = 10 * 1024 * 1000;
        let chunks = Math.ceil(file.size / chunkSize);
        let spark = new SparkMD5.ArrayBuffer();
        //由于计算整个文件的Md5太慢，因此采用只计算第1块文件的md5的方式
        let chunkNumberMD5 = 1;

        loadNext();

        fileReader.onload = (e => {
          spark.append(e.target.result);

          if (currentChunk < chunkNumberMD5) {
            loadNext();
          } else {
            let md5 = spark.end();
            file.uniqueIdentifier = md5;
            file.resume();
            console.log(`MD5计算完毕：${file.name} \nMD5：${md5} \n分片：${chunks} 大小:${file.size} 用时：${new Date().getTime() - time} ms`);
          }
        });

        fileReader.onerror = function () {
          this.error(`文件${file.name}读取出错，请检查该文件`)
          file.cancel();
        };

        function loadNext() {
          let start = currentChunk * chunkSize;
          let end = ((start + chunkSize) >= file.size) ? file.size : start + chunkSize;

          fileReader.readAsArrayBuffer(blobSlice.call(file.file, start, end));
          currentChunk++;
          console.log("计算第" + currentChunk + "块");
        }
      },
      close() {
        this.uploader.cancel();
      },
      error(msg) {
        this.$notify({
          title: '错误',
          message: msg,
          type: 'error',
          duration: 2000
        })
      }
    }
  }
</script>

<style>
  .uploader-ui {
    padding: 30px;
    margin: 10px;
    font-size: 12px;
    font-family: Microsoft YaHei;
    box-shadow: 0 0 10px rgba(0, 0, 0, .4);
  }

  .uploader-ui .uploader-btn {
    margin-right: 4px;
    font-size: 12px;
    border-radius: 3px;
    color: #FFF;
    background-color: #409EFF;
    border-color: #409EFF;
    display: inline-block;
    line-height: 1;
    white-space: nowrap;
  }

  .uploader-ui .uploader-list {
    max-height: 440px;
    overflow: auto;
    overflow-x: hidden;
    overflow-y: auto;
  }
</style>
