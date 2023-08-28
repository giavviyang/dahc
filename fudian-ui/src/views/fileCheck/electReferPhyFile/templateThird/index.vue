<template>
  <div class="app-container-padding">
    <CheckTop :queryParams="queryParams"
              :checkRange="checkRange"
              :processName="receptionProcedure.procedureName"
              :processDes="receptionProcedure.procedureDesc"
              :impeach="isImpeach"
              :showInputCaseNum="showInputCaseNum"
              :notVerifiedCount="notVerifiedCount"
              :notVerifiedCountShow="!isImpeach"
              @handleReceivedPhy="handleReceivedPhy"
              @handleBack="handleBack"/>
    <TablePage class="tablePage1"
               :hasSelection="false"
               :is-title="true"
               :tableTitle="caseTitle"
               :tableData="caseData"
               :isPage="false"
               @handleChange="handleCheckItem">
      <template slot="table">
        <el-table-column
          v-for="(item,index) in caseColumn"
          :key="index"
          :prop="item.prop"
          :label="item.label"
          :min-width="item.width">
          <template slot-scope="scope">
            <el-input v-model="scope.row[item.prop]" size="mini" :disabled='item.disabled'
                      v-if="scope.row.isUpdate &&(item.prop!=='caseNum')"></el-input>
            <div v-else>{{ scope.row[item.prop] }}</div>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"  fixed="right"
          width="150" class-name="operation">
          <template slot-scope="scope">
            <el-button type="text" size="mini" @click="handleSave(scope.row)" v-if="scope.row.isUpdate">
              保存
            </el-button>
            <el-button type="text" size="mini" @click="handleUpdate(scope.row)" v-else>
              编辑
            </el-button>
            <el-button type="text" size="mini" @click="handleAddFile(scope.row)">
              新增文件
            </el-button>
          </template>
        </el-table-column>
      </template>
    </TablePage>
    <div class="checkBox">
      <div class="fileTable">
        <TablePage class="tablePage2"
                   :hasSelection="false"
                   :hasIndex="false"
                   :isPage="false"
                   :is-title="true"
                   ref="tablePage2"
                   :tableTitle="fileTitle"
                   @handleRowClick="handleRowClick"
                   :tableData="fileData">
          <template slot="table">
            <el-table-column label="件号" align="center" width="50" :prop="sequenceNumber"/>
            <!--            <el-table-column label="全宗号" align="center" width="50" :prop="attrOfTheCaseFileNumber"/>-->
            <el-table-column
              label="操作"  fixed="right"
              min-width="100" class-name="operation">
              <template slot-scope="scope">
                <el-button type="text" size="mini" :disabled="scope.$index === 0"
                           @click.native.stop="moveUp(fileData,scope.row, scope.$index)">
                  上移
                </el-button>
                <el-button type="text" size="mini" @click.native.stop="moveDown(fileData,scope.row, scope.$index)"
                           :disabled="getFormLength(fileData,scope.$index)">
                  下移
                </el-button>
                <el-button type="text" size="mini" @click.native.stop="handleDel(scope.row)">
                  移除
                </el-button>
                <el-button type="text" size="mini" @click.native.stop="uploadElectronicFiles(scope.row)">
                  上传电子文件
                </el-button>
              </template>
            </el-table-column>
          </template>
        </TablePage>
      </div>
      <!--          <PicProcess class="filePic"></PicProcess>-->

      <PicProcess class="filePic" :imgList="imgList" :imgDes="imgDes" :currentPage='currentPage'
                  :currentFile="currentFile" :isCurrentPage="isCurrentPage" :isCurrentPageItem="isCurrentPageItem"
                  :theCurrentNumberOfPages="theCurrentNumberOfPages" :fileData="fileData"
                  @handleChange="handleChange" @previousPage="previousPage" @nextPage="nextPage"
                  @imgMoveDown="imgMoveDown" @replacement="replacement" @downloadTheImage="downloadTheImage"
                  @plusPage="plusPage" @pagingDown="pagingDown" @imgMoveUp="imgMoveUp"
                  @previousFile="previousFile" @nextFile="nextFile" @handleCheck="handleCheck"
                  @modifyThePageNumber="modifyThePageNumber"
      ></PicProcess>
      <div class="fileContent">
        <p class="fileContentTitle">
          文件具体信息：
        </p>
        <div class="fileContentForm">
          <el-form ref="form" :model="fileForm" label-width="80px" size="mini">
            <el-form-item v-for="(item,index) in  fileColumn" :label="item.label" :key="index">
              <el-input v-model="fileForm[item.prop]" type="textarea" @change="handleSaveForm"
                        :autosize="{ minRows: 1, maxRows: 10}" :ref="'input'+ index"></el-input>
            </el-form-item>
          </el-form>
        </div>
        <div class="subBtn">
          <el-button type="primary" size="mini" @click="handleSubmit">提交</el-button>
        </div>
      </div>
    </div>
    <el-dialog title="核查信息"
               :visible.sync="dialogVisible"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="90%" class="dialog-style processDialog">
      <CheckTable :isTitle="false" :tableData="checkItemData" :isImpeach="isImpeach"
                  :isShowChangeContent="true" class="checkItemData"/>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" size="mini" @click="handleSaveInspect('processFormRef')">保存</el-button>
        <el-button size="mini" @click="dialogVisible=false">取消</el-button>
      </span>
    </el-dialog>

    <el-dialog title="重置页号"
               :visible.sync="modifyThePageNumberVisible"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="60%" class="dialog-style processDialog">
      <el-form :model="modifyThePageNumberForm" ref="modifyThePageNumberForm" size="mini" :inline="true"
               :rules="modifyThePageNumberRules"
               @submit.native.prevent>
        <el-form-item label="起始排列序号：" prop="theNumberOfStartingPages">
          <el-input v-model="modifyThePageNumberForm.theNumberOfStartingPages" placeholder="请输入起始页码"
                    style="width: 200px"></el-input>
        </el-form-item>
        <p/>
        <el-form-item label="修改范围：" prop="startPages">
          <el-radio v-model="startPages" label="2">第一页</el-radio>
          <el-radio v-model="startPages" label="1">开始页号</el-radio>
          <el-input v-model="modifyThePageNumberForm.startPages" placeholder="请输入起始页码" style="width: 200px"
                    :disabled="startPages == '2'"></el-input>
        </el-form-item>
        <el-form-item label="至" prop="comeToAnEndPages">
          <el-radio v-model="comeToAnEndPages" label="2">最后一页</el-radio>
          <el-radio v-model="comeToAnEndPages" label="1">结束页号</el-radio>
          <el-input v-model="modifyThePageNumberForm.comeToAnEndPages" placeholder="请输入起始页码"
                    :disabled="comeToAnEndPages == '2'"
                    style="width: 200px"></el-input>

        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" size="mini" @click="modifyThePageNumberSave('modifyThePageNumberForm')">保存</el-button>
                <el-button size="mini" @click="modifyThePageNumberVisible=false">取消</el-button>
      </span>
    </el-dialog>
    <!--  分片上传 电子文件  -->

    <SimpleUploader ref="simpleUploader" :projectId="receptionProcedure.projectId" :procedureId="receptionProcedureId"
                    :archivesId="receptionArchiveTypeId" :dialogSimpleUploader="dialogSimpleUploader"
                    :checkStatus="isImpeach" :timerSwitch="timerSwitch" @onFileSuccess="onFileSuccess"
                    @queryCaseLevelArchivesDossierLevelTemplateTwo="queryCaseLevelArchivesDossierLevelTemplateTwo"
                    @closeSimpleUploader="closeSimpleUploader" :caseFileNumber="caseFileNumber" :keyName="keyName"
                    :pid="pid" @getInformation="getInformation"></SimpleUploader>

    <imgUpload :dialogImgUpload="dialogVisibleImgFile" :theCurrentPage="theCurrentPage"
               @closeImgUpload="closeImgUpload" @handleChangeFile="handleChangeFile" @uploadAdd="uploadAdd">
    </imgUpload>
    <imgsUpload :dialogImgUpload="dialogVisibleImgsFile" @closeImgsUpload="closeImgsUpload"
                :theCurrentPage="theCurrentPage"
                @handleImgsChangeFile="handleImgsChangeFile" @uploadImgsAdd="uploadImgsAdd"
    ></imgsUpload>
  </div>
</template>

<script>
  import CheckTop from "@/views/fileCheck/checkTop.vue";
  import TablePage from "@/components/Public/table/TablePage.vue";
  import CheckTable from "@/views/fileCheck/checkTable.vue";
  import PicProcess from "@/views/fileCheck/picProcess.vue";
  import imgUpload from "@/views/upload/imgUpload.vue";
  import imgsUpload from "@/views/upload/imgsUpload.vue";
  import SimpleUploader from "@/views/upload/simpleUploader.vue";
  import {
    obtainCaseDataAttr,
    selectListAndMetadata,
    theFileNumberFieldIsThatDatabaseField,
    caseFileNumberAttr
  } from "@/api/business/archiveType";
  import {getOneProjectprocedure} from "@/api/projectManage/projectProcedure";
  import {
    doubtQueryVerificationResultData,
    drawEntityFile,
    getPucter,
    insertProcedureInspect,
    listData,
    queryBasedOnTheIDOfTheVerificationResult,
    queryCaseLevelArchivesDossierLevelTemplateTwo,
    queryElectronicArchivesDossierLevelTemplateTwo,
    submissionOfVerificationResultsInDoubt,
    theNumberOfUncheckedFilesInTheQueryFile,
    thePictureMovesUpAndDown,
    imageReplacement,
    download,
    imagePlusPage,
    imagePlusPageTwo,
    imageMinus,
    associateTheCurrentOperation,
    modifyTheNumberOfPages
  } from "@/api/record/record";
  import {deleteById, getInformation, increaseCases, modifyTheSort, updateFileData} from "@/api/fileData/fileData";
  import {getData} from "@/api/dahc/sys/dictData";
  import {addData, updateData} from "@/api/business/model";
  import {mergeFile} from "@/api/upload/uploadFile";

  export default {
    // name: "index",
    components: {PicProcess, CheckTable, TablePage, CheckTop, imgUpload, imgsUpload, SimpleUploader},

    data() {
      function getFirstIndex(imgList, pageNum) {
        for (var i = 0; i < imgList.length; i++) {
          if (imgList[i].pageNum == pageNum) {
            return i;
          }
        }
        return -1;
      }

      var samePageNumber = (rule, value, callback) => {
        /*起始排序第一次出现的页号*/
        var index = getFirstIndex(this.imgList, value)
        /*开始页排序第一次出现的页号*/
        var index2 = getFirstIndex(this.imgList, this.modifyThePageNumberForm.startPages)
        if (index != -1) {
          if (index < index2) {
            callback(new Error('开始页号之前已存在当前起始页号'));
          } else {
            callback();
          }
        } else {
          callback();
        }
      };
      var comeToAnEndPagesCompare = (rule, value, callback) => {
        if (this.comeToAnEndPages == '1') {
          if (value <= this.modifyThePageNumberForm.startPages) {
            callback(new Error('结束页号必须大于开始页号'));
          }
          if (value <= this.modifyThePageNumberForm.theNumberOfStartingPages) {
            callback(new Error('结束页号必须大于起始页号'));
          } else {
            callback();
          }
        } else {
          callback();
        }
      };

      return {
        queryParams: {
          caseNum: '',
          activeName: 'alreadyReceived',
        },
        caseTitle: '案卷信息：',
        caseData: [],
        caseColumn: [],
        caseTotal: 0,
        caseSize: 10,
        caseNum: 1,
        fileTitle: '文件信息：',
        fileData: [],
        fileForm: {
          fileIndex: '1111',
          caseNum: '',
          date: '',
          name: '',
          address: false,
        },
        fileColumn: [],
        checkRange: '电子档案对照实体',
        processName: '工序名称',
        processDes: '工序描述',
        checkItemData: [],
        isDisabled: true,
        returnState: 20000,
        dialogVisible: false,
        modifyThePageNumberVisible: false,
        imgList: [],
        imgDes: {
          currentFile: 0,
          totalFile: 0,
          currentPage: 0,
          totalPage: 0,
          resolution: ''
        },
        currentPage: 0,
        currentFile: 0,
        // /*核查结果表头*/
        // tableColumn: [
        //   {label: '核查项名称', prop: 'trueingName', width: '100'},
        //   {label: '核查项描述', prop: 'trueingDesc', width: '100'},
        //   {label: '核查标准', prop: 'examineStasString', width: '100', cellName: 'cellName'},
        // ],
        /*工序id*/
        receptionProcedureId: '',
        /*工序对象*/
        receptionProcedure: {
          projectId: '',
          procedureName: '',
          procedureId: '',
          procedureDesc: '',
        },
        /*项目关联档案类型id*/
        receptionArchiveTypeId: '',
        /*查询表头的所有数据*/
        selectListAndMetadataTable: [],
        showInputCaseNum: false,
        /*是否是存疑页面跳转过来的*/
        isImpeach: false,
        /*文件档号的attr*/
        attrOfTheCaseFileNumber: '',
        /*文件顺序号的attr*/
        sequenceNumber: '',
        /*是否属于最后一页*/
        isCurrentPage: false,
        /*是否属于最后一件*/
        isCurrentPageItem: false,
        /*替换文件弹窗*/
        dialogVisibleImgFile: false,
        /*加页弹窗*/
        dialogVisibleImgsFile: false,
        /*图片信息*/
        uploadFiles: [],
        /*加页图片信息*/
        uploadImgsFiles: [],
        /*未核查数量*/
        notVerifiedCount: 0,
        /*当前页数*/
        theCurrentNumberOfPages: 0,
        /*表名称*/
        archiveLevelName: '',
        /*文件档号attr*/
        caseFileAttrOrder: '',
        /*文件上传弹窗*/
        dialogSimpleUploader: false,
        /*文件上传文件档号*/
        caseFileNumber: '',
        /*文件上传案卷档号*/
        keyName: '',
        /*文件上传文件Id*/
        pid: '',
        /*修改页码集合*/
        modifyThePageNumberForm: {
          theNumberOfStartingPages: '',
          startPages: 1,
          comeToAnEndPages: 1,
          projectId: '',
          pid: ''
        },
        startPages: '2',
        comeToAnEndPages: '2',
        modifyThePageNumberRules: {
          theNumberOfStartingPages: [
            {required: true, message: "起始序号不能为空", trigger: "blur"},
            {pattern: /^[1-9]\d*$/, message: "请输入正整数并且不等于0", trigger: "blur"},
            {validator: samePageNumber, trigger: "blur"},
          ],
          startPages: [
            {required: true, message: "开始页号不能为空", trigger: "blur"},
            {pattern: /^[1-9]\d*$/, message: "请输入正整数并且不等于0", trigger: "blur"}
          ],
          comeToAnEndPages: [
            {required: true, message: "结束页号不能为空", trigger: "blur"},
            {pattern: /^[1-9]\d*$/, message: "请输入正整数并且不等于0", trigger: "blur"},
            {validator: comeToAnEndPagesCompare, trigger: "blur"},
          ],
        },
        /*当前页*/
        theCurrentPage: 0,
        timerSwitch: ''
      }
    },
    created() {
      if (this.$route.query.isImpeach == 'true') {
        this.isImpeach = true;
        this.showInputCaseNum = true;
      } else {
        this.isImpeach = false;
      }
      this.receptionProcedureId = this.$route.query.receptionProcedureId;
      this.receptionArchiveTypeId = this.$route.query.receptionArchiveTypeId;
      this.getOne();
      this.theFileNumberFieldIsThatDatabaseField();
    },
    mounted() {
      this.keyDown(0);
    },
    destroyed() {
      document.onkeydown=null;
    },
    methods: {
      /*获取上传文件是否完成*/
      getInformation(val) {
        getInformation(val).then(res => {
          console.log("执行", res)
          if (res.data == '完成但是失败') {
            let a = res.msg
            if (a.lastIndexOf(":") != -1) {
              a = a.substring(a.lastIndexOf(":") + 2, a.length)
            }
            this.$message({message: a, type: 'error', showClose: true, duration: 0});
          } else if (res.data == '成功') {
            this.$message({
              message: res.msg, type: 'success', showClose: true, duration: 0, onClose: () => {
                /*点击关闭执行事件*/
                this.queryCaseLevelArchivesDossierLevelTemplateTwo();
              }
            });
          }
          this.timerSwitch = res.data;
        })
      },
      onFileSuccess(rootFile, file, response, chunk) {
        //refProjectId为预留字段，可关联附件所属目标，例如所属档案，所属工程等
        // file.refProjectId = "123456789";
        file.refProjectId = this.receptionProcedure.projectId;
        file.archivesId = this.receptionArchiveTypeId;
        file.caseFileNumber = this.caseFileNumber;
        file.pid = this.pid;
        file.procedureId = this.receptionProcedureId;
        file.keyName = this.keyName;
        file.checkStatus = this.isImpeach;
        this.timerSwitch = '';
        mergeFile(file).then(responseData => {
          if (responseData.data.code === 415) {
            console.log("合并操作未成功，结果码：" + responseData.data.code);
          }
          this.$message.success(responseData.msg);
          var interval = setInterval(() => {
            console.log(this.timerSwitch,"开始前")
            if (this.timerSwitch == '完成但是失败') {
              console.log(this.timerSwitch)
              this.timerSwitch = '';
              clearInterval(interval);
              console.log("停止1")
            }else if (this.timerSwitch == '成功') {
              this.timerSwitch = ''
              clearInterval(interval);
              console.log(this.timerSwitch,"停止前")
              console.log("停止2")
            } else {
              console.log(this.timerSwitch,"轮询")
              this.getInformation(responseData.data);
            }
          }, 5000) //五秒执行一次
        }).catch(function (error) {
          console.log("合并后捕获的未知异常：" + error);
        });
      },
      /****************************************************图片处理---开始******************************************************/
      /*x修改页码*/
      modifyThePageNumberSave(formName) {
        console.log(this.$refs[formName].validate, "")
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.modifyThePageNumberForm.projectId = this.receptionProcedure.projectId
            this.modifyThePageNumberForm.pid = this.imgList[this.currentPage].pid
            if (this.startPages == '2') {
              this.modifyThePageNumberForm.startPages = this.imgList[0].pageNum
            }
            if (this.comeToAnEndPages == '2') {
              this.modifyThePageNumberForm.comeToAnEndPages = this.imgList[this.imgList.length - 1].pageNum
            }
            this.modifyThePageNumberForm.caseFileNumber = this.caseData[0]["attr" + this.queryParams.attrOrder], //案卷名称
              this.modifyThePageNumberForm.keyName = this.imgList[this.currentPage].keyName, //文件档号
              this.modifyThePageNumberForm.buttonName = "图片重新排序",
              this.modifyThePageNumberForm.checkStatus = this.isImpeach,
              this.modifyThePageNumberForm.procedureId = this.receptionProcedureId, //工序名称,
              modifyTheNumberOfPages(this.modifyThePageNumberForm).then(res => {
                if (res.code == this.returnState) {
                  this.imgList = [];
                  this.$message.success("修改成功");
                  this.getPucter();
                  this.modifyThePageNumberVisible = false;
                  this.modifyThePageNumberForm.startPages = '';
                  this.modifyThePageNumberForm.comeToAnEndPages = 1;
                }
              });
          } else {
            return false;
          }
        });
      },
      /*修改页码按钮*/
      modifyThePageNumber() {
        this.modifyThePageNumberForm.comeToAnEndPages = this.imgList[this.imgList.length - 1].pageNum;
        this.modifyThePageNumberForm.theNumberOfStartingPages = this.imgList[this.currentPage].pageNum
        // this.modifyThePageNumberForm.startPages = this.imgList[this.currentPage].pageNum
        this.modifyThePageNumberVisible = true;
      },
      /*文件上传*/
      uploadElectronicFiles(row) {
        if (row[this.attrOfTheCaseFileNumber] == '' || row[this.attrOfTheCaseFileNumber] == undefined) {
          this.$message.error("档号为空，不能上传文件");
        } else {
          associateTheCurrentOperation(this.receptionProcedureId).then(res => {
            if (res.code == this.returnState) {
              this.caseFileNumber = row['attr' + this.caseFileAttrOrder];
              this.keyName = this.caseData[0]["attr" + this.queryParams.attrOrder];
              this.pid = row.attrId;
              this.dialogSimpleUploader = true;
            }
          });

        }
      },
      /*关闭弹窗*/
      closeSimpleUploader(val) {
        this.dialogSimpleUploader = val;
      },

      /*减页*/
      pagingDown() {
        this.$confirm('此操作将删除文件：' + this.imgList[this.currentPage].keyName + '下图片名称为：' + this.imgList[this.currentPage].fileName + '的数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          const arr = {
            buttonName: "图片减页",
            checkStatus: this.isImpeach,
            keyName: this.caseData[0]["attr" + this.queryParams.attrOrder], //案卷名称
            caseFileNumber: this.imgList[this.currentPage].keyName, //案件名称
            pid: this.fileForm.attrId,//文件id
            projectId: this.receptionProcedure.projectId,
            filePhotoId: this.imgList[this.currentPage].id,//图片id
            procedureId: this.receptionProcedureId
          }
          imageMinus(arr).then(res => {
            if (res.code == this.returnState) {
              this.resetImgDes();
              this.$message.success("减页成功");
              this.getPucter();
            }
          });
        });
      },
      /*清空图片信息*/
      resetImgDes() {
        this.imgList = [];
        this.imgDes = {
          currentFile: 0,
          totalFile: 0,
          currentPage: 0,
          totalPage: 0,
          resolution: ''
        }
      },
      /*上传加页信息*/
      uploadImgsAdd(radio) {
        if (this.uploadImgsFiles.length == 0) {
          this.$message.error("文件为空");
        } else {
          if (this.imgList.length > 0) {
            const arr = {
              radio: radio,
              projectId: this.receptionProcedure.projectId,
              pid: this.imgList[this.currentPage].pid,
              theCurrentNumberOfPages: this.imgList[this.currentPage].currentPage, //当前页数
              filePath: this.imgList[this.currentPage].filePath, //存储路径
              keyName: this.imgList[this.currentPage].keyName, //案件名称
              procedureId: this.receptionProcedureId, //工序名称
              buttonName: "图片加页",
              checkStatus: this.isImpeach,
              caseFileNumber: this.caseData[0]["attr" + this.queryParams.attrOrder], //案卷名称
            };
            let formData = new FormData();
            this.uploadImgsFiles.forEach(item => {
              formData.append('file', item)
            });
            formData.append('dto', JSON.stringify(arr));
            imagePlusPage(formData).then(res => {
              if (res.code == this.returnState) {
                this.imgList = [];
                this.$message.success("加页成功");
                this.getPucter();
                this.dialogVisibleImgsFile = false;
              }
            });
          } else {
            const arr = {
              projectId: this.receptionProcedure.projectId,//项目Id
              projectName: this.receptionProcedure.projectName,//项目名称
              archiveLevelName: this.archiveLevelName,//表名称
              pid: this.fileForm.attrId,//文件id
              keyName: this.caseData[0]["attr" + this.queryParams.attrOrder], //案卷名称
              caseFileNumber: this.fileForm["attr" + this.caseFileAttrOrder], //文件档号
              procedureId: this.receptionProcedureId, //工序名称,
              buttonName: "图片加页",
            };
            let formData = new FormData();
            this.uploadImgsFiles.forEach(item => {
              formData.append('file', item)
            });
            formData.append('dto', JSON.stringify(arr));
            imagePlusPageTwo(formData).then(res => {
              if (res.code == this.returnState) {
                this.imgList = []
                this.$message.success("加页成功");
                this.getPucter();
                this.dialogVisibleImgsFile = false;
              }
            });
          }
        }
      },
      /*加页图片数据*/
      handleImgsChangeFile(file, fileList) {
        this.uploadImgsFiles = [];
        this.uploadImgsFiles = fileList.map(item => {
          return item.raw;
        });
        // this.uploadImgsFiles.push(file.raw);
      },
      /*加页*/
      plusPage() {
        this.uploadImgsFiles = [];
        if (this.imgList.length > 0) {
          this.theCurrentPage = this.imgList[this.currentPage].pageNum;
        } else {
          this.theCurrentPage = 0;
        }
        this.dialogVisibleImgsFile = true;
      },
      /*关闭加页弹窗*/
      closeImgsUpload(val) {
        this.dialogVisibleImgsFile = false;
      },
      /*下载图片*/
      downloadTheImage() {
        if (this.imgList.length == 0) {
          this.$message({message: '不存在图片数据，不能下载图片', type: 'warning'});
        } else {
          this.$confirm('此操作将下载文件：' + this.imgList[this.currentPage].keyName + '下图片名称为：' + this.imgList[this.currentPage].fileName + '的数据, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }).then(() => {
            const arr = {
              filePath: this.imgList[this.currentPage].filePath,
              pid: this.imgList[this.currentPage].pid,//文件id
              id: this.imgList[this.currentPage].id,//文件id
              pageNum: this.imgList[this.currentPage].pageNum,//文件id
              fileName: this.imgList[this.currentPage].fileName,//文件id
              caseFileNumber: this.caseData[0]["attr" + this.queryParams.attrOrder], //案卷名称
              keyName: this.imgList[this.currentPage].keyName, //文件档号
              buttonName: "图片下载",
              checkStatus: this.isImpeach,
              procedureId: this.receptionProcedureId, //工序名称,
              projectId: this.receptionProcedure.projectId,//项目Id
            }
            download(arr).then(res => {
              const fileName = this.imgList[this.currentPage].filePath.lastIndexOf("\\");
              let href = window.URL.createObjectURL(new Blob([res])) // 根据后端返回的url对应的文件流创建URL对象
              const link = document.createElement('a')//创建一个隐藏的a标签
              link.style.display = 'none'
              link.href = href//设置下载的url
              link.download = this.imgList[this.currentPage].filePath.substr(fileName + 1) //设置下载的文件名
              document.body.appendChild(link)
              link.click()
              document.body.removeChild(link)
              window.URL.revokeObjectURL(href) // 释放掉blob对象
            });
          });
        }
      },
      /*提交图片信息*/
      uploadAdd() {
        if (this.uploadFiles.length == 0) {
          this.$message.error("文件为空");
        } else {
          const arr = {
            filePath: this.imgList[this.currentPage].filePath,
            procedureId: this.receptionProcedureId,
            pid: this.imgList[this.currentPage].pid,//文件id
            id: this.imgList[this.currentPage].id,//文件id
            pageNum: this.imgList[this.currentPage].pageNum,//文件id
            fileName: this.imgList[this.currentPage].fileName,//文件id
            keyName: this.caseData[0]["attr" + this.queryParams.attrOrder], //案卷名称
            caseFileNumber: this.imgList[this.currentPage].keyName, //文件档号
            buttonName: "图片替换",
            checkStatus: this.isImpeach,
            projectId: this.receptionProcedure.projectId,//项目Id

          };
          let formData = new FormData()
          formData.append('file', this.uploadFiles)
          formData.append('dto', JSON.stringify(arr))
          imageReplacement(formData).then(res => {
            if (res.code == this.returnState) {
              this.$message.success("修改成功");
              this.getPucter('不刷新位置');
              this.dialogVisibleImgFile = false;
              this.uploadFiles = [];
            }
          });
        }

      },
      /*获取上传图片信息*/
      handleChangeFile(file, fileList) {
        this.uploadFiles = file.raw
      },
      /*关闭上传文件弹窗*/
      closeImgUpload(val) {
        this.dialogVisibleImgFile = false;
        this.uploadFiles = [];
      },
      /*替换*/
      replacement() {
        if (this.imgList.length > 0) {
          this.theCurrentPage = this.imgList[this.currentPage].pageNum;
        } else {
          this.theCurrentPage = 0;
        }
        this.dialogVisibleImgFile = true;
      },
      /*下移*/
      imgMoveDown() {
        if (this.imgList.length == 0) {
          this.$message({message: '不存在图片数据，不能下移', type: 'warning'});
        } else if (this.currentPage == this.imgList.length - 1) {
          this.$message({message: '图片为最后一页，不能下移', type: 'warning'});
        } else {
          const arr = {
            id1: this.imgList[this.currentPage].id,
            id2: this.imgList[this.currentPage + 1].id,
            pid: this.fileForm.attrId,//文件id
            caseFileNumber: this.caseData[0]["attr" + this.queryParams.attrOrder], //案卷名称
            keyName: this.fileForm["attr" + this.caseFileAttrOrder], //文件档号
            procedureId: this.receptionProcedureId, //工序名称,
            buttonName: "图片下移",
            checkStatus: this.isImpeach,
            projectId: this.receptionProcedure.projectId,//项目Id
          }
          this.$confirm('此操作将下移选中的图片数据, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }).then(() => {
            thePictureMovesUpAndDown(arr).then(res => {
              if (this.returnState == res.code) {
                this.imgList = [];
                this.$message.success("修改成功");
                this.getPucter();
              }
            });
          });
        }
      },
      /*图片上移*/
      imgMoveUp() {
        if (this.currentPage == 0) {
          this.$message({message: '图片为第一页，不能上移', type: 'warning'});
        } else {
          const arr = {
            id1: this.imgList[this.currentPage].id,
            id2: this.imgList[this.currentPage - 1].id,
            pid: this.fileForm.attrId,//文件id
            caseFileNumber: this.caseData[0]["attr" + this.queryParams.attrOrder], //案卷名称
            keyName: this.fileForm["attr" + this.caseFileAttrOrder], //文件档号
            procedureId: this.receptionProcedureId, //工序名称,
            buttonName: "图片上移",
            checkStatus: this.isImpeach,
            projectId: this.receptionProcedure.projectId,//项目Id
          }
          this.$confirm('此操作将上移选中的图片数据, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }).then(() => {
            thePictureMovesUpAndDown(arr).then(res => {
              if (this.returnState == res.code) {
                this.imgList = [];
                this.$message.success("修改成功");
                this.getPucter();
              }
            });
          });
        }
      },
      /*获取档号在哪个attr*/
      obtainCaseDataAttr() {
        const arr = {
          metadataName: '档号',
          archiveTypeId: this.receptionArchiveTypeId
        };
        obtainCaseDataAttr(arr).then(res => {
          this.attrOfTheCaseFileNumber = "attr" + res.data;
          this.getPucter();
        });

        const arr1 = {
          metadataName: '顺序号/件号',
          archiveTypeId: this.receptionArchiveTypeId
        };
        obtainCaseDataAttr(arr1).then(res => {
          this.sequenceNumber = "attr" + res.data;
        });

      },

      /*查询图片信息*/
      getPucter(val) {
        const arr = {
          keyName: this.fileForm[this.attrOfTheCaseFileNumber], //档号
          pid: this.fileForm.attrId //pid
        }
        getPucter(arr).then(res => {
          if (res.data.length > 0) {
            this.imgList = res.data;
            this.theCurrentNumberOfPages = this.currentPage + 1;
            this.imgList.map(item => {
              item.imgUrl = 'data:image/jpeg;base64,' + item.imgUrl;
              let url = item.imgUrl;
              const img = new Image();
              img.src = url;
              item.currentFile = this.fileForm[this.sequenceNumber]
              item.totalFile = this.fileData.length;
              item.currentPage = item.pageNum
              setTimeout(() => {
                item.resolution = img.naturalWidth.toString() + " * " + img.naturalHeight.toString();
              }, 300)
            });
            if (val == '不刷新位置') {
              this.imgDes = this.imgList[this.currentPage];
            } else {
              this.imgDes = this.imgList[0];
            }
          } else {
            this.imgDes.currentFile = this.fileForm[this.sequenceNumber]
            this.imgDes.totalFile = this.fileData.length;
            /*页数设置为零*/
            this.theCurrentNumberOfPages = 0;
          }
        });
      },


      reset() {
        this.imgDes = {
          currentFile: 0,
          totalFile: 0,
          currentPage: 0,
          totalPage: 0,
          resolution: ''
        }
      },

      /**
       * 轮播图切换
       */
      handleChange(val) {
        this.isCurrentPage = false;
        if (val + 1 == this.imgList.length) {
          this.isCurrentPage = true;
        }
        this.theCurrentNumberOfPages = val + 1;
        this.currentPage = val;
        this.imgDes = this.imgList[val]
      },


      /**
       * 上一页
       */
      previousPage(val) {
        this.currentPage = val - 1;
        this.theCurrentNumberOfPages = val;
        this.isCurrentPage = false;
        this.fileForm = this.fileData[this.currentPage];
      },
      /**
       * 下一页
       */
      nextPage(val) {
        this.currentPage = val + 1;
        this.theCurrentNumberOfPages = val + 2;
        console.log("下一页", this.theCurrentNumberOfPages)
        if (val + 2 == this.imgList.length) {
          this.isCurrentPage = true;
        }
        this.fileForm = this.fileData[this.currentPage - 1]
      },

      /**
       * 上一件
       */
      previousFile(val) {
        this.isCurrentPageItem = false;
        this.currentFile = val - 1;
        this.isDisabled = false;
        this.fileForm = this.fileData[this.currentFile];
        this.imgList = [];
        this.reset();
        /*查询图片*/
        this.getPucter();
        this.$refs.tablePage2.$refs.tableRef.setCurrentRow(this.fileForm);
      },
      /**
       * 下一件
       */
      nextFile(val) {
        if (val + 2 == this.fileData.length) {
          this.isCurrentPageItem = true;
        }
        this.currentFile = val + 1;
        this.isDisabled = false;
        this.fileForm = this.fileData[this.currentFile];
        this.imgList = [];
        this.reset();
        /*查询图片*/
        this.getPucter();
        this.$refs.tablePage2.$refs.tableRef.setCurrentRow(this.fileForm);
      },
      /**
       * 点击文件单行
       */
      handleRowClick({row, column, event}) {
        if (this.fileData[this.fileData.length - 1].attrId == row.attrId) {
          this.isCurrentPageItem = true;
        } else {
          this.isCurrentPageItem = false;
        }

        this.fileData.map((item, index) => {
          if (item[this.attrOfTheCaseFileNumber] == row[this.attrOfTheCaseFileNumber]) {
            this.currentFile = index;
          }
        });
        this.fileForm = row;
        this.isDisabled = false;
        this.imgList = [];
        this.reset();
        /*查询图片*/
        this.getPucter();
      },

      // 上移
      moveUp(arr, item, index) {
        /*        arr.splice(index - 1, 0, item); // 定位到点击位置的上一行，0即不删除如何元素，在此位置插入item
                arr.splice(index + 1, 1); // 此时数组中有重复元素，把原来被挤下去的元素删除*/

        this.$confirm('此操作将上移选中的文件数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {

          /*上移传选中数据和序列前一个工序数据*/
          const ids = {
            id1: item.attrId,
            id2: arr[index - 1].attrId,
            archiveTypeId: this.receptionArchiveTypeId,
            procedureId: this.receptionProcedureId,
            buttonName: "文件上移",
            checkStatus: this.isImpeach,
            caseNumber: item[this.attrOfTheCaseFileNumber],
            pid: this.caseData[0].filesId,
            caseFileNumber: this.caseData[0]["attr" + this.queryParams.attrOrder],
            projectId: this.receptionProcedure.projectId
          }
          modifyTheSort(ids).then(res => {
            if (res.code === this.returnState) {
              this.imgList = []; /*清空图片信息*/
              this.reset();/*清空图片下方信息*/
              this.$message.success("上移成功");
              // /*查询文件数据*/
              this.queryCaseLevelArchivesDossierLevelTemplateTwo();
            }
          });
        })
      },
      // 控制下移按钮的显示与隐藏
      getFormLength(arr, index) {
        if (index === arr.length - 1) {
          return true;
        } else {
          return false;
        }
      },
      // 下移
      moveDown(arr, item, index) {
        // arr.splice(index + 2, 0, item);
        // arr.splice(index, 1);
        this.$confirm('此操作将下移选中的文件数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {

          /*下移移传选中数据和序列下移一个工序数据*/
          const ids = {
            id1: item.attrId,
            id2: arr[index + 1].attrId,
            archiveTypeId: this.receptionArchiveTypeId,
            procedureId: this.receptionProcedureId,
            buttonName: "文件下移",
            checkStatus: this.isImpeach,
            caseNumber: item[this.attrOfTheCaseFileNumber],
            pid: this.caseData[0].filesId,
            caseFileNumber: this.caseData[0]["attr" + this.queryParams.attrOrder],
            projectId: this.receptionProcedure.projectId
          }
          modifyTheSort(ids).then(res => {
            if (res.code === this.returnState) {
              this.imgList = []; /*清空图片信息*/
              this.reset();/*清空图片下方信息*/
              this.$message.success("下移成功");
              /*查询文件数据*/
              this.queryCaseLevelArchivesDossierLevelTemplateTwo();
            }
          });
        })
      },
      /****************************************************图片处理---结束******************************************************/





      /**************************************************档案案卷、文件处理 --开始**************************/
      /*根据档案类型id查询 档号 在数据库的那个字段*/
      theFileNumberFieldIsThatDatabaseField() {
        theFileNumberFieldIsThatDatabaseField(this.receptionArchiveTypeId).then(res => {
          this.queryParams.attrOrder = res.data.attrOrder;
          this.queryParams.archiveLevelName = res.data.archiveLevelName;
          this.archiveLevelName = res.data.archiveLevelName;
        });

        /*文件案卷attr*/
        caseFileNumberAttr(this.receptionArchiveTypeId).then(res => {
          this.caseFileAttrOrder = res.data.attrOrder;
        });
      },
      /*********************核查范围转换********************/
      /*查询核查项描述*/
      queryTrueingId(row) {
        getData(row).then(res => {
          this.checkRange = res.data.fullName;
        });
      },

      /*查询未核查数量*/
      theNumberOfUncheckedFilesInTheQueryFile(row) {
        const arr = {
          procedureId: row,
          isProcedureInspect: '0'
        }
        theNumberOfUncheckedFilesInTheQueryFile(arr).then(res => {
          this.notVerifiedCount = res.data;
        });
      },
      /*********************核查范围转换********************/

      /*查询工序基本上数据*/
      getOne() {
        this.receptionProcedureId = this.$route.query.receptionProcedureId;
        getOneProjectprocedure(this.receptionProcedureId).then(res => {
          this.receptionProcedure = res.data;
          this.selectListAndMetadata();
          this.queryTrueingId(res.data.trueingId);
          this.theNumberOfUncheckedFilesInTheQueryFile(this.receptionProcedureId);
        });
      },


      /*查询表头*/
      selectListAndMetadata() {
        const arr = {
          // id: res.data.archiveTypeId,
          id: this.receptionArchiveTypeId,
          level: 1 //案卷表
        };
        /*表头*/
        selectListAndMetadata(arr).then(res => {
          this.selectListAndMetadataTable = res.data
          this.selectListAndMetadataTable[0].dahcMetadataList.map(item => {
            item.label = item.metadataName;
            item.prop = 'attr' + item.attrOrder;
            item.width = item.metadataWidth;
            item.disabled = item.disabled;
          });
          this.caseColumn = this.selectListAndMetadataTable[0].dahcMetadataList;
          if (this.isImpeach) {
            /*存疑页面查询*/
            this.queryParams.caseNum = this.$route.query.caseNum;
            this.queryBasedOnTheIDOfTheVerificationResult();
          } else {
            /*查询案卷数据和核查项数据*/
            this.queryInspectList();
          }

        });

        this.selectListAndMetadataCase();
      },


      /*文件表头*/
      selectListAndMetadataCase() {
        const arr2 = {
          // id: res.data.archiveTypeId,
          id: this.receptionArchiveTypeId,
          level: 2 //案卷表
        };
        /*查询系统文件级有那些默认字段*/


        /*文件表头*/
        selectListAndMetadata(arr2).then(res => {
          res.data[0].dahcMetadataList.map(item => {
            item.label = item.metadataName;
            item.prop = 'attr' + item.attrOrder;
            item.width = item.metadataWidth;
            item.disabled = item.disabled
          });
          this.fileColumn = res.data[0].dahcMetadataList;
        });
      },


      /*初始查询核查项数据*/
      queryInspectList() {
        this.queryParams.procedureId = this.receptionProcedureId;
        listData(this.queryParams).then(res => {
          this.queryParams.caseNum = res.data[0].rollId
          this.queryParams.filesId = res.data[0].filesId
          this.queryParams.recordProcedureFilesId = res.data[0].recordProcedureFilesId
          this.handleQuery("2");
          res.data.map(item => {
            if (res.data[0].filesId != '' & res.data[0].filesId != null) {
              this.showInputCaseNum = true;
            }
            if (item.pageNumS.length == 0) {
              item.pageNumS = [{
                key: Date.now(),
              },];
            }
          });
          console.log("核查结果", res.data)
          this.checkItemData = res.data;
          if (this.checkItemData) {
            this.checkItemData.forEach(item => {
              let str = item.examineStasString.replace(/\//g, '；');
              item.examineStasString = str.slice(0, item.examineStasString.length - 1)
            })
          }
        });
      },

      /*查询表数据*/
      handleQuery(val) {
        const arr = {
          procedureId: this.receptionProcedureId,//工序id
          archiveLevelName: this.selectListAndMetadataTable[0].tableLevel1En, //案卷级的表名称
          isProcedureInspect: val, //未处理数据
          attrOrder: 'attr' + this.queryParams.attrOrder,
          caseNum: this.queryParams.caseNum,
          pageNum: 1,
          pageSize: 1,
        };
        queryElectronicArchivesDossierLevelTemplateTwo(arr).then(res => {
          if (val == '2') {
            const arrRes = [];
            if (res.data.length > 0) {
              arrRes.push(res.data[0])
              this.showInputCaseNum = true;
              this.queryParams.caseNum = res.data[0].filesName
            }
            /*已领取*/
            this.caseData = arrRes;
            if (this.caseData.length > 0) {
              /*查询文件数据*/
              this.queryCaseLevelArchivesDossierLevelTemplateTwo();
            } else {
              this.checkItemData = [];
              this.fileData = []; /*清空文件信息*/
              this.imgList = []; /*清空图片信息*/
              this.fileForm = {};/*清空单个文件信息*/
              this.reset();/*清空图片下方信息*/
              // this.clearTheData();
              console.log("文件为空")
              this.isCurrentPageItem = true;
            }
          }
        });
      },

      /*查询文件数据*/
      queryCaseLevelArchivesDossierLevelTemplateTwo() {
        const arr = {
          filesId: this.caseData[0].filesId,
          archiveId: this.receptionArchiveTypeId,
        };
        queryCaseLevelArchivesDossierLevelTemplateTwo(arr).then(res => {
          if (res.data[0] != null) {
            this.fileData = res.data;
            this.fileForm = res.data[0];
            this.$refs.tablePage2.$refs.tableRef.setCurrentRow(this.fileForm);
            /*查询图片*/
            this.obtainCaseDataAttr();
          } else {
            this.fileData = []; /*清空文件信息*/
            this.imgList = []; /*清空图片信息*/
            this.reset();/*清空图片下方信息*/
            this.fileForm = {};/*清空单个文件信息*/
            console.log("文件为空")
            this.isCurrentPageItem = true;
          }
        });

      },
      /**
       * 领取档案
       */
      handleReceivedPhy() {
        this.checkItemData = [];
        this.fileData = []; /*清空文件信息*/
        this.imgList = []; /*清空图片信息*/
        this.fileForm = {};/*清空单个文件信息*/
        this.reset();/*清空图片下方信息*/
        const arr = {
          procedureId: this.receptionProcedureId,
          projectId: this.receptionProcedure.projectId,
          isProcedureInspect: 2, //0-未领取，1-已核查 2-已领取
          filesName: this.queryParams.caseNum,
          // filesId: this.queryParams.caseNum,
          fileTableName: this.queryParams.archiveLevelName,
          fileTableNameAttr: 'attr' + this.queryParams.attrOrder,
          buttonName: "领取档案",
          checkStatus: this.isImpeach
        }
        drawEntityFile(arr).then(res => {
          if (res.code == this.returnState) {
            this.queryParams.filesId = res.data.filesId;
            this.queryParams.recordProcedureFilesId = res.data.recordProcedureFilesId;
            this.$message.success("领取成功");
            this.showInputCaseNum = true;
            // this.handleQuery("2");
            this.queryInspectList();
          }
        });
      },


      /*保存核查结果*/
      handleSaveInspect() {
        if (this.isImpeach) {
          this.checkItemData.map(item => {
              item.rollId = this.queryParams.caseNum;
              item.isInspectAccomplish = 1;
              item.filesId = this.$route.query.filesId;
              item.recordProcedureFilesId = this.$route.query.recordProcedureFilesId;
              item.projectId = this.receptionProcedure.projectId
              item.buttonName = "保存核查结果"
              item.checkStatus = this.isImpeach
            }
          );
          /*提交存疑结果*/
          this.submissionOfVerificationResultsInDoubt(this.checkItemData, 1);
        } else {
          this.checkItemData.map(item => {
              item.rollId = this.caseData[0].filesName; /*档案名称*/
              item.isInspectAccomplish = 1 /*核查结果状态 1-未完成 0-完成*/
              item.filesId = this.caseData[0].filesId /*档案主键id*/
              item.recordProcedureFilesId = this.caseData[0].id /*当前核查结果记录的主键id*/
              item.projectId = this.receptionProcedure.projectId
              item.buttonName = "保存核查结果"
              item.checkStatus = this.isImpeach
            }
          );
          /*保存核查数据*/
          insertProcedureInspect(JSON.stringify(this.checkItemData)).then(res => {
            if (res.code == this.returnState) {
              this.$message.success("保存成功");
              this.dialogVisible = false;
            }
          });
        }
      },

      /*是否为空*/
      whetherItIsEmpty(item) {
        let b = true;
        let msg = '';
        if (item.checkResult[0] == null) {
          msg = "核查结果不能为空";
          b = false;
        }
        if (item.checkResult[0] == "unqualified") {
          if (item.processMode[0] == null) {
            msg = "处理方式不能为空";
            b = false;
          }
          if (item.changeContent[0] == null && item.processMode[0] == "change") {
            msg = "更改内容不能为空";
            b = false;
          }
        }
        const arr = {
          type: b,
          msg: msg
        }
        return arr;
      },
      //分件弹窗监听键盘回车保存事件
      keyDown(i) {
        let that = this
        //监听键盘按钮
        document.onkeydown = function (e) {
          if (e.keyCode === 40) {
            that.$nextTick(() => {
              that.$refs['input' + i][0].blur();
              i++;
              if (that.$refs['input' + i]) {
                that.$refs['input' + i][0].focus()
              } else {
                i=0;
                return
              }
            })
          }
          if (e.keyCode === 38) {
            that.$nextTick(() => {
              that.$refs['input' + i][0].blur();
              i--;
              if (that.$refs['input' + i]) {
                that.$refs['input' + i][0].focus()
              } else {
                i=0;
                return
              }
            })
          }
        }
      },
      /*提交*/
      handleSubmit() {
        /*是否核查*/
        let whetherToVerify = {};
        if (this.isImpeach) {
          let isQuestion = false;
          this.checkItemData.map(item => {
              item.rollId = this.queryParams.caseNum;
              item.isInspectAccomplish = 0;
              item.filesId = this.$route.query.filesId;
              item.recordProcedureFilesId = this.$route.query.recordProcedureFilesId;
              item.projectId = this.receptionProcedure.projectId
              item.buttonName = "提交"
              item.checkStatus = this.isImpeach
              if (item.processMode[0] == 'impeach') {
                isQuestion = true;
              }
            }
          );
          /*提交存疑结果*/
          if (isQuestion) {
            this.$message.warning("存在存疑数据");
            isQuestion = false;
          } else {
            this.submissionOfVerificationResultsInDoubt(this.checkItemData, 0);
          }
        } else {
          if (this.caseData.length == 0) {
            this.$message.error("没有档案需要提交");
          } else {
            var arr = this.checkItemData.every(item => {
                item.rollId = this.queryParams.caseNum;
                item.isInspectAccomplish = 0;
                item.filesId = this.caseData[0].filesId
                item.recordProcedureFilesId = this.queryParams.recordProcedureFilesId
                item.projectId = this.receptionProcedure.projectId
                item.buttonName = "提交"
                item.checkStatus = this.isImpeach
                whetherToVerify = this.whetherItIsEmpty(item);
                return whetherToVerify.type;
              }
            );
            /*等于false不走提交*/
            if (arr) {
              insertProcedureInspect(JSON.stringify(this.checkItemData)).then(res => {
                if (res.code == this.returnState) {
                  this.$message.success("提交成功");
                  this.showInputCaseNum = false;
                  this.queryParams.caseNum = '';
                  this.caseData = []; /*清空案卷信息*/
                  this.fileData = []; /*清空文件信息*/
                  this.imgList = []; /*清空图片信息*/
                  this.reset();/*清空图片下方信息*/
                  this.fileForm = {};/*清空单个文件信息*/
                  this.queryInspectList();
                  this.theNumberOfUncheckedFilesInTheQueryFile(this.receptionProcedureId);
                }
              });
            } else {
              this.$message.error(whetherToVerify.msg);
            }
          }
        }

      },
      /*移除案件数据*/
      handleDel(row) {
        console.log(row, "移除")
        row.isUpdate = !row.isUpdate
        const arr = {
          id: row.attrId,
          archiveTypeId: this.receptionArchiveTypeId,
          level: 2,
          procedureId: this.receptionProcedureId,
          pid: this.caseData[0].filesId,
          caseFileNumber: this.caseData[0]["attr" + this.queryParams.attrOrder],
          projectId: this.receptionProcedure.projectId,
          buttonName: "文件移除",
          checkStatus: this.isImpeach,
          caseNumber: row[this.attrOfTheCaseFileNumber],
        }
        this.$confirm('此操作将永久删除选中数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          deleteById(arr).then(res => {
            if (res.code == this.returnState) {
              this.$message.success("删除成功");
              /*查询文件数据*/
              this.queryCaseLevelArchivesDossierLevelTemplateTwo();
            }
          });
        });
      },
      /*保存案卷档案*/
      handleSave(row) {
        row.isUpdate = !row.isUpdate
        row.id = row.filesId;
        row.archiveTypeId = this.receptionArchiveTypeId
        row.level = 1
        row.procedureId = this.receptionProcedureId
        row.buttonName = "保存案卷档案"
        row.checkStatus = this.isImpeach
        row.pid = row.filesId
        row.projectId = this.receptionProcedure.projectId
        row.caseFileNumber = row["attr" + this.queryParams.attrOrder]
        updateFileData(row).then(res => {
          if (res.code == this.returnState) {
            this.$message.success("修改成功");
          }
        });
      },
      /*保存文件数据*/
      handleSaveForm() {
        if (this.fileForm.attrId == undefined) {
          this.$message.error("未找到文件数据");
        } else {
          this.isDisabled = true;
          this.fileForm.level = 2;
          this.fileForm.archiveTypeId = this.receptionArchiveTypeId
          this.fileForm.id = this.fileForm.attrId
          this.fileForm.procedureId = this.receptionProcedureId
          this.fileForm.buttonName = "保存文件档案"
          this.fileForm.checkStatus = this.isImpeach
          this.fileForm.projectId = this.receptionProcedure.projectId
          this.fileForm.caseFileNumber = this.caseData[0]["attr" + this.queryParams.attrOrder]
          this.fileForm.caseNumber = this.fileForm[this.attrOfTheCaseFileNumber];
          this.fileForm.pid = this.caseData[0].filesId
          this.fileForm.fileId = this.fileForm.attrId
          updateFileData(this.fileForm).then(res => {
            if (res.code == this.returnState) {
              this.$message.success("修改成功");
            }
          });
        }
      },


      /**************************************************** 存疑方法 开始*****************************/
      /*存疑查询数据*/
      queryBasedOnTheIDOfTheVerificationResult() {
        const arr = {
          procedureId: this.receptionProcedureId,//工序id
          archiveLevelName: this.selectListAndMetadataTable[0].tableLevel1En, //案卷级的表名称
          attrOrder: 'attr' + this.queryParams.attrOrder,
          caseNum: this.queryParams.caseNum,
        };
        queryBasedOnTheIDOfTheVerificationResult(arr).then(res => {
          const arrRes = [];
          if (res.data.length > 0) {
            arrRes.push(res.data[0])
          }
          /*已领取*/
          this.caseData = arrRes;
          this.doubtQueryVerificationResultData();
          this.queryCaseLevelArchivesDossierLevelTemplateTwo();
        });
      },

      /*存疑查询核查结果*/
      doubtQueryVerificationResultData() {
        const dtoArr = {
          procedureId: this.receptionProcedureId, //工序id
          fileNumberId: this.caseData[0].filesId,//档案名称
          recordProcedureFilesId: this.$route.query.recordProcedureFilesId
        };
        doubtQueryVerificationResultData(dtoArr).then(res => {
            this.checkItemData = res.data;
            if (this.checkItemData) {
              this.checkItemData.forEach(item => {
                let str = item.examineStasString.replace(/\//g, '；');
                item.examineStasString = str.slice(0, item.examineStasString.length - 1)
              })
            }
          }
        );
      },

      /*存疑提交核查结果*/
      submissionOfVerificationResultsInDoubt(row, i) {
        submissionOfVerificationResultsInDoubt(row).then(res => {
          if (res.code == this.returnState) {
            if (i == 0) {
              this.$message.success("提交成功");
              this.$router.go(-1)
            } else {
              this.$message.success("保存成功");
              this.dialogVisible = false;
            }
          }
        });
      },
      /************************************************** 存疑方法 结束*****************************/
      /**************************************************档案案卷、文件处理 --结束**************************/

      /**
       * 新增文件
       */
      handleAddFile(row) {
        this.$confirm('新增一条文件数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          const arr = {
            archiveTypeId: this.receptionArchiveTypeId,
            projectId: this.receptionProcedure.projectId,
            pid: row.filesId,
            batchNo: row.batch_no,
            level: 2,
            status: 2,
            fileId: this.caseData[0].filesId, //案卷ID
            procedureId: this.receptionProcedureId,
            buttonName: "新增文件档案",
            checkStatus: this.isImpeach,
            caseFileNumber: this.caseData[0]['attr' + this.queryParams.attrOrder],
            caseNumber: this.caseData[0]['attr' + this.queryParams.attrOrder] + "-" + parseInt(parseInt(this.fileData[this.fileData.length - 1][this.sequenceNumber]) + 1),
          }

          for (let i = 0; i <= 50; i++) {
            arr['arr' + i] = ''
          }
          arr[this.sequenceNumber] = parseInt(this.fileData[this.fileData.length - 1][this.sequenceNumber]) + 1
          this.fileData.push(arr)

          increaseCases(arr).then(res => {
            if (res.code === this.returnState) {
              this.$message.success("新增成功");
              // /*查询文件数据*/
              this.queryCaseLevelArchivesDossierLevelTemplateTwo();
            }
          });
        });
      },

      /**
       * 查看核查项
       */
      handleCheck(val) {
        if (this.caseData.length > 0) {
          this.dialogVisible = true;
        } else {
          this.$message.error("不存在案卷数据");
        }
      },

      handleCheckItem(pageSize, pageNum) {
        this.processSize = pageSize;
        this.processNum = pageNum;

      },
      /**
       * 返回
       */
      handleBack() {
        this.$router.back();
      },
      /**
       * 编辑档案
       */
      handleUpdate(row) {
        console.log(row)
        row.isUpdate = !row.isUpdate
      },

      handleUpdateForm() {
        this.isDisabled = false;
      },

    },
  }
</script>

<style scoped lang="scss">
  .tablePage1 {
    height: 110px !important;
    margin-top: 20px;
  }

  ::v-deep .checkBox {
    height: calc(100% - 215px) !important;
    //background-color: wheat;
    margin-top: 5px;
    display: flex;

    .fileTable {
      width: 170px;
      height: 100%;

      .tablePage2 {
        height: calc(100% - 20px) !important;
        margin-top: 20px;

        .operation {
          .cell {
            //justify-content: space-between;//两端对齐，项目之间的间隔都相等。
            flex-wrap: wrap; //换行且第一行在上方
            .el-button {
              width: auto;
            }
          }
        }
      }
    }

    .filePic {
      width: 60%;
      height: 100%;
      margin-left: 5px;
    }

    .fileContent {
      width: calc(40% - 180px) !important;
      margin-left: 5px;
      height: 100%;

      .fileContentTitle {
        font-weight: 600;
        font-size: 13px;
        color: #606266;
      }

      .fileContentForm {
        border-radius: 10px;
        border: 1px solid #eeeeee;
        height: calc(100% - 50px) !important;
        box-shadow: rgba(0, 0, 0, 0.1) 0px 4px 12px;

        .el-form {
          padding: 5px 10px;
          height: 100%;
          display: flex;
          flex-direction: column;
          width: 100%;
          overflow: auto;

          .el-form-item {
            margin-bottom: 10px;

            .el-form-item__label {
              font-size: 12px;
            }
          }
        }
      }

    }
  }

  .checkTable {
    height: calc(70vh) !important;
    //margin-top: 20px;
  }

</style>
