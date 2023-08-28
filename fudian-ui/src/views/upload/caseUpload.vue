<template>
  <el-dialog
    title="导入案卷条目"
    :visible.sync="dialogCaseExcel"
    width="600px" class="dialog-style dialogCaseExcel"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :destroy-on-close="true"
    :before-close="closeCaseExcel">
<!--    <el-form ref="caseExcelRef" :model="caseExcelForm" size="mini" :inline="true">-->
<!--      <el-form-item label="导入备注" prop="remarks">-->
<!--        <el-input v-model="caseExcelForm.remarks" placeholder="请输入内容" maxlength="30" clearable/>-->
<!--      </el-form-item>-->
<!--    </el-form>-->
<!--    <el-form ref="form" :model="form" label-width="120px" style="width: 50%">-->
<!--      <el-form-item label="保存至文件夹">-->
<!--        <input type="file" id="file" hidden @change="fileChange" webkitdirectory>-->
<!--        <el-input placeholder="请输入内容" v-model="form.imgSavePath" class="input-with-select">-->
<!--          <el-button slot="append" icon="el-icon-folder" type="success" @click="btnChange"></el-button>-->
<!--        </el-input>-->
<!--      </el-form-item>-->
<!--    </el-form>-->

    <el-upload class="upload-excel"
               :action="actionUrl"
               ref="upload"
               :data="uploadExcel"
               :on-success="recommendedHeader"
               :on-change="handleChangeFile"
               :on-preview="beforeAvatarUpload"
               :before-remove="beforeRemoveFile"
               multiple
               :limit="1"
               :on-exceed="handleExceedFile"
               :file-list="fileList"
               :auto-upload="false">
      <el-button size="mini" type="primary">点击上传</el-button>
      <div slot="tip" class="el-upload__tip">只能上传文件，且不超过10Mb</div>
    </el-upload>
    <div class="tableContainer">
      <div class="tableTitle">选择模板：</div>
      <div class="table">
        <el-table
          :data="tableDataModel"
          style="width: 100%"
          stripe
          border
          highlight-current-row
          height="100%"
          ref="tableUpload"
          @selection-change="handleSelectionChange"
          @row-click="handleRowClick"
          :row-style="{height:'20px'}"
        >
          <el-table-column
            type="selection"
            width="50">
          </el-table-column>
          <el-table-column
            type="index"
            label="序号"
            width="55">
          </el-table-column>
          <el-table-column
            prop="name"
            label="相关度"
            width="100">
          </el-table-column>
          <el-table-column
            prop="modelName"
            label="模板名称"
            min-width="200">
          </el-table-column>
        </el-table>
      </div>
    </div>
    <span slot="footer" class="dialog-footer">
              <el-button type="primary" size="mini" @click="submitCaseExcel">保存</el-button>
              <el-button @click="closeCaseExcel" size="mini">取消</el-button>
            </span>
  </el-dialog>
</template>

<script>
  import {getToken} from "@/utils/auth";
  import {queryTemplateRelevanceAccordingToExcel} from "@/api/projectManage/projectInitialize";
  import {getInformation, importDataVolume} from "@/api/fileData/fileData";

  export default {
    name: "caseUpload",
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
      dialogCaseExcel: {
        type: Boolean,
        default: false,
      },
      fileList: {
        type: Array,
        default: () => []
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
        form: {
          imgSavePath: ''
        },
        // dialogCaseExcel: false,
        caseExcelForm: {
          remarks: ''
        },
        actionUrl: process.env.VUE_APP_BASE_API + '/datatemplate/importData',
        tableDataModel: [],
        uploadExcel: {
          modelId: ''
        },
        selected: [],
        returnState: 20000,
        uploadFiles: [],
        fileUpload: false,
        timerSwitch: ''
      }
    },
    methods: {
      fileChange(e) {
        try {
          const fu = document.getElementById('file')
          if (fu == null) return
          this.form.imgSavePath = fu.files[0].path
          console.log(fu.files[0].path)
        } catch (error) {
          console.debug('choice file err:', error)
        }
      },
      btnChange() {
        var file = document.getElementById('file')
        file.click()
      },
      closeCaseExcel() {
        let show = false;
        this.tableDataModel = [];
        this.caseExcelForm = {
          remarks: ''
        };
        this.$emit("closeCaseExcel", show);
      },
      showPosition() {
        this.dialogCaseExcel = true;
      },
      handleExceedFile(files, fileList) {
        this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
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
      handleChangeFile(file, fileList) {
        this.uploadFiles = file.raw
        const arr = {
          tableLv: 1,
          archivesId: this.archivesId
        }
        let formData = new FormData()
        formData.append('file', file.raw)
        formData.append('archivesId', this.archivesId)
        formData.append('tableLv', 1)
        formData.append('dto', JSON.stringify(arr))
        queryTemplateRelevanceAccordingToExcel(formData).then(res => {
          if (res.code === this.returnState) {
            this.tableDataModel = res.data
          }
        })
      },
      handleSelectionChange(selection) {
        this.selected = selection
        // 清除 所有勾选项
        if (selection.length === 1) {
          this.selected = selection;
          this.uploadExcel.modelId = this.selected[0].id
        }
        if (selection.length > 1) {
          this.$refs.tableUpload.clearSelection();
          this.$refs.tableUpload.toggleRowSelection(selection.at(-1), true);
          this.selected = selection.slice(-1);
          this.uploadExcel.modelId = this.selected[0].id
        }
        if (selection.length === 0) {
          this.selected = [];
          this.uploadExcel.modelId = ''
        }
      },
      handleRowClick(row, column, event) {
        this.$refs.tableUpload.toggleRowSelection(row, column)
      },
      submitCaseExcel() {

        if (this.uploadExcel.modelId == '') {
          this.$message.error("请选择模板");
        } else {
          const fromData = new FormData();
          fromData.append("file", this.uploadFiles)
          fromData.append("id", this.uploadExcel.modelId)
          fromData.append("projectId", this.projectId)
          importDataVolume(fromData).then(res => {
            if (res.code === this.returnState) {
              /*清空模板表格*/
              this.tableDataModel = [];
              this.$message.success(res.msg)
              let show = false;
              this.$emit("closeCaseExcel", show);
              this.timerSwitch = '';
              var interval = setInterval(() => {
                if (this.timerSwitch == '完成但是失败') {
                  clearInterval(interval);
                  console.log("停止1")
                }else if (this.timerSwitch == '成功') {
                  clearInterval(interval);
                  console.log("停止2")
                } else {
                  this.getInformation(res.data);
                }
              }, 5000) //五秒执行一次
            }
          })
        }
      },

      /*获取上传文件是否完成*/
      getInformation(val) {

        getInformation(val).then(res => {
          console.log("执行", res)

          if (res.data == '完成但是失败') {

            this.$message({message: res.msg, type: 'error', showClose: true,duration: 0});
          } else if(res.data == '成功'){
            this.$message({message: res.msg, type: 'success', showClose: true,duration: 0});
          }
          this.timerSwitch = res.data;
        })
      },
    },
  }
</script>

<style lang="scss" scoped>
  .dialogCaseExcel {
    width: 100%;

    .upload-excel {
      width: 100%;
      position: relative;

      .el-upload__tip {
        position: absolute;
        top: 5px;
        left: 85px;
        color: red;
        font-size: 12px;
        margin-top: 0;
      }
    }

    ::v-deep .el-form-item {
      width: 100%;

      .el-form-item__content {
        width: calc(100% - 70px);
      }
    }

    .tableContainer {
      margin: 30px 0 20px;
      width: 100%;
      min-height: 200px;
      height: 30vh;
      $fontSize: 12px;
      //ie浏览器兼容
      box-sizing: content-box;
      -moz-box-sizing: inherit;
      -webkit-box-sizing: inherit;
      position: relative;

      .tableTitle {
        font-weight: 600;
        font-size: 13px;
        color: #606266;
        position: absolute;
        left: 5px;
        top: -20px

      }

      .table {
        height: 100%;
        overflow: hidden;

        ::v-deep .el-table {
          border-right: 1px solid #dfe6ec;
          color: #909399;

          th {
            height: 34px !important;
          }

          & > .el-table__header-wrapper {
            & > table {
              tr:first-of-type {
                th {
                  background: rgba(204, 204, 204, 0.18);
                  color: #909399;
                  font-size: 12px;
                  text-align: center;
                  height: 34px !important;
                  padding: 5px 0 !important;
                }
              }

              tr:nth-of-type(2) {
                th {
                  background: #fff;
                  color: #909399;
                  font-size: 12px;
                  text-align: center;
                  padding: 0 !important;
                  height: 34px;

                  .el-input__inner {
                    border: none;
                    padding: 0;
                    height: 20px;
                  }

                  .el-input__suffix {
                    width: 12px;

                    .el-input__icon {

                      line-height: 23px;
                    }
                  }
                }
              }

              & > colgroup {
                col {
                  &:last-of-type {
                    width: 17px !important;
                  }
                }
              }
            }
          }

          .el-table__body-wrapper {
            //overflow: auto !important;

            .el-table__row {
              td {
                text-align: center;
                padding: 5px 0 !important;
                font-size: 12px;

                .el-input.is-disabled .el-input__inner {
                  color: #606266;
                }

                .el-radio {
                  margin-right: 20px;
                  font-size: 12px;

                  .el-radio__input {
                    .el-radio__inner {
                      width: 10px;
                      height: 10px;
                    }
                  }

                  .el-radio__label {
                    font-size: 12px;
                    padding-left: 5px;
                    font-weight: 400;
                  }
                }

                .el-radio:last-of-type {
                  margin-right: 0;
                }
              }

              .textLeft {
                text-align: left;
              }

              .el-table-column--selection {
                .cell {
                  padding-right: 10px;
                }
              }

              .operation {
                .cell {
                  display: flex;
                  justify-content: space-around;
                  width: 100%;
                  white-space: nowrap;
                  overflow: hidden;
                  text-overflow: ellipsis;

                  .el-button {
                    //width: 100%;
                    height: 100%;
                    position: relative;
                    top: 0;
                    right: 0;
                    margin: 0 2px;
                    font-size: 12px;

                  }
                }
              }
            }

            .small-padding {
              .cell {
                color: #909399;
              }
            }

            .noticeType {
              .el-tag {
                background-color: transparent;
                color: #909399;
                border: none;
              }

            }
          }

          .el-table__cell {
            padding: 5px 0 !important;
          }
        }
      }
    }
  }
</style>
