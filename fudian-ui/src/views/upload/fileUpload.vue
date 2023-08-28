<template>
  <el-dialog
    title="导入文件条目"
    :visible.sync="dialogFileExcel"
    width="800px" class="dialog-style dialogFileExcel"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :destroy-on-close="true"
    :before-close="closeFileExcel">
    <el-upload class="upload-excel"
               :action="actionUrl"
               ref="upload"
               :data="uploadExcel"
               :on-success="recommendedHeader"
               :on-change="handleChangeFile"
               :on-preview="beforeAvatarUpload"
               :before-remove="beforeRemoveFile"
               multiple
               :limit="10"
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
    <el-form ref="caseExcelRef" :model="fileExcelForm" size="mini" :inline="true" :rules="caseExcelRules" >
      <el-form-item label="选择元数据" prop="checkedMetas">
        <el-checkbox-group v-model="fileExcelForm.checkedMetas" @change="handleCheckedChange">
          <el-checkbox v-for="city in metaDatas"
                       :label="city.sourceName"
                       :value="city.sourceOrder"
                       :key="city.sourceOrder"
                       :data-value="city.sourceOrder"
          >{{ city.sourceName }}
          </el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="设置规则" prop="fileNumberRules" class="fileNumberRules" clearable>
        <el-input  type="textarea" autosize v-model="fileExcelForm.fileNumberRules"  :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="选择批次" prop="batchNo" class="fileNumberRules">
        <el-select v-model="fileExcelForm.batchNo" multiple  placeholder="请选择批次" @change="change">
          <el-option
            v-for="item in batchNos"
            :key="item.batchNo"
            :label="item.batchNo"
            :value="item.batchNo">
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
              <el-button type="primary" size="mini" @click="submitExcel('caseExcelRef')">保存</el-button>
              <el-button @click="closeFileExcel" size="mini">取 消</el-button>
            </span>
  </el-dialog>
</template>

<script>
  import {queryTemplateRelevanceAccordingToExcel, queryArchiveBatchData} from "@/api/projectManage/projectInitialize";
  import {getInformation, importDataPiece} from "@/api/fileData/fileData";
  import {selectMapperByModel} from "@/api/business/model";

  export default {
    name: "fileUpload",
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
      dialogFileExcel: {
        type: Boolean,
        default: false
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
        caseExcelRules: {
          checkedMetas: [{required: true, message: "元数据选择不能为空", trigger: 'change'}],
          batchNo: [{
            type: 'array',
            required: true,
            message: "案卷批次不能为空",
            trigger: "change"}],
        },
        actionUrl: process.env.VUE_APP_BASE_API + '/datatemplate/importData',
        tableDataModel: [],
        uploadExcel: {
          modelId: ''
        },
        fileExcelForm: {
          checkedMetas: [],
          fileNumberRules: '',
          batchNo:[]
        },
        // fileList:[],
        selected: [],
        returnState: 20000,
        /*上传文件信息*/
        uploadFiles: [],
        fileUpload: false,
        metaDatas: [],
        batchNos: [],
        /*档号规则*/
        sourceOrderList: [],
        timerSwitch: '',
        disabledTooltip: true
      }
    },
    methods: {
      change(val){
        console.log('111')
        this.$set(this.fileExcelForm,'batchNo',val)
        console.log(this.fileExcelForm.batchNo)
        this.$forceUpdate()
      },
      closeFileExcel() {
        this.tableDataModel = [];
        this.fileExcelForm = {
          checkedMetas: [],
          fileNumberRules: '',
          batchNo: []
        };
        this.metaDatas = [];
        this.batchNos = [];
        let show = false;
        this.$emit("closeFileExcel", show);
      },
      /********************************************拼接规则**************************/

      /*查询模版关联元数据字段*/
      templateAssociatedMetadataFields() {
        const arr = {
          archiveTypeId: this.archivesId,
          id: this.uploadExcel.modelId //模版id
        }
        selectMapperByModel(arr).then(response => {
          if (response.code === this.returnState) {
            this.metaDatas = response.data[0].dahcBusinessMapperList
          }
        })
      },


      /*拼接规则*/
      handleCheckedChange(val) {
        this.sourceOrderList = [];
        val.map((item1, index) => {
          this.metaDatas.map(item => {
            if (item.sourceName == item1) {
              this.sourceOrderList.push({"sourceOrder": item.sourceOrder, "order": index, "name": item.sourceName})
            }
          });
        });
        this.fileExcelForm.fileNumberRules = val.join("-")
      },


      /*获取批次数组*/
      queryArchiveBatchData() {
        const arr = {
          id: this.projectId,
          archiveTypeId: this.archivesId
        }
        queryArchiveBatchData(arr).then(res => {
          this.batchNos = res.data
        });
      },
      /********************************************拼接规则**************************/
      /*获取上传文件是否完成*/
      getInformation(val) {
        getInformation(val).then(res => {
          this.timerSwitch = res.data;
          if (res.data == '完成但是失败') {
            let a = res.msg
            if (a.lastIndexOf(":") != -1) {
              a = a.substring(a.lastIndexOf(":") + 2, a.length)
            }
            this.$message({message: a, type: 'error', showClose: true, duration: 0});
          } else if (res.data == '成功') {
            this.$message({message: res.msg, type: 'success', showClose: true, duration: 0});
          }
        });
      },
      submitExcel(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.uploadFiles.length == 0) {
              this.$message.error("没有文件数据")
            } else {
              if (this.uploadExcel.modelId == '') {
                this.$message.error("请选择模板");
              } else {
                let arr = {
                  modelId: this.uploadExcel.modelId,
                  projectId: this.projectId,
                  list: this.sourceOrderList,
                  // batchNo: 'admin-20230331164230'
                  batchNoList: this.fileExcelForm.batchNo
                };
                const fromData = new FormData();
                fromData.append("file", this.uploadFiles)
                fromData.append("modelId", this.uploadExcel.modelId)
                fromData.append("projectId", this.projectId)
                //fromData.append("sortList", this.sourceOrderList)
                fromData.append("sortList", JSON.stringify(this.sourceOrderList))
                fromData.append("batchNoList", this.fileExcelForm.batchNo)
                fromData.append("dtoJson", JSON.stringify(arr))
                // fromData.append("id", this.uploadExcel.modelId)
                // fromData.append("projectId", this.projectId)

                importDataPiece(fromData).then(res => {
                  if (res.code === this.returnState) {
                    /*清空模板表格*/
                    this.tableDataModel = [];
                    this.fileExcelForm = {
                      checkedMetas: [],
                      fileNumberRules: '',
                      batchNo: [],
                    };
                      this.$message.success(res.msg)
                    let show = false;
                    this.$emit("closeFileExcel", show);
                    this.timerSwitch = '';
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
            }
          }
        });
      },

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
      /*上传文件， 获取文件信息*/
      handleChangeFile(file, fileList) {
        let formData = new FormData()
        this.uploadFiles = file.raw
        const arr = {
          tableLv: 2,
          archivesId: this.archivesId
        }
        formData.append('file', file.raw)
        formData.append('archivesId', this.archivesId)
        formData.append('tableLv', 2)
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
        if (selection.length > 0) {
          this.templateAssociatedMetadataFields();
        }
      },
      handleRowClick(row, column, event) {
        this.$refs.tableUpload.toggleRowSelection(row, column)
      },

    },
  }
</script>

<style lang="scss" scoped>
  .dialogFileExcel {
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

      .el-form-item__label {
        width: 100px;
      }

      .el-form-item__content {
        width: calc(100% - 110px);
      }
    }

    .fileNumberRules {
      width: calc(50% - 10px);

      .el-select {
        width: 100%;
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
