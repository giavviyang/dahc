<template>
  <div class="app-container-padding">
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="iptAndBtn"
             style="position: relative" @submit.native.prevent>
      <div style="white-space: nowrap;margin-right: 90px;">
        <el-form-item label="案卷档号" prop="filesName">
          <el-input
            v-model="queryParams.caseNum"
            placeholder="请输入案卷档号"
            @keyup.enter.native="conditionalQueries"
            clearable/>
        </el-form-item>
        <el-form-item label="项目名称" prop="projectId">
          <el-select v-model="queryParams.projectId" placeholder="请选择项目" maxlength="30" disabled>
            <el-option v-for="metaDataItem in projectIdTable"
                       :key="metaDataItem.value"
                       :label="metaDataItem.label"
                       :value="metaDataItem.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="核查状态" prop="isAccomplishInspect">
          <el-select v-model="queryParams.isAccomplishInspect" placeholder="请选择项目" maxlength="30"
                     @change="conditionalQueries">
            <el-option v-for="metaDataItem in isAccomplishInspectOption"
                       :key="metaDataItem.value"
                       :label="metaDataItem.label"
                       :value="metaDataItem.value"

            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item style="min-width: auto">
          <el-button type="primary" icon="el-icon-search" size="mini" @click="conditionalQueries">搜索</el-button>
        </el-form-item>
      </div>
      <div style="right:5px;position: absolute">
        <el-button type="primary" icon="el-icon-refresh-left" size="mini" @click="handleBack">返回</el-button>
      </div>
    </el-form>
    <div class="btn">
      <el-dropdown>
        <el-button type="primary" size="mini">
          <i class="el-icon-upload" style="display: inline-block;margin-right: 5px;"></i>导入数据<i
          class="el-icon-arrow-down el-icon--right"></i>
        </el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="importExcel('caseExcel')">导入案卷条目</el-dropdown-item>
          <el-dropdown-item @click.native="importExcel('fileExcel')">导入文件条目</el-dropdown-item>
          <el-dropdown-item @click.native="importExcel('imgFile')">导入电子文件</el-dropdown-item>
          <el-dropdown-item @click.native="importExcel('filePathUploader')">根据文件路径导入</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <el-dropdown>
        <el-button type="primary" size="mini">
          <i class="el-icon-download" style="display: inline-block;margin-right: 5px;"></i>导出数据<i
          class="el-icon-arrow-down el-icon--right"></i>
        </el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="exportData('caseExcel')">导出条目信息</el-dropdown-item>
<!--          <el-dropdown-item @click.native="exportData('fileExcel')">导出文件条目</el-dropdown-item>-->
          <el-dropdown-item @click.native="exportData('zipExcel')">导出电子文件</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <el-button
        type="primary"
        icon="el-icon-circle-check"
        size="mini"
        @click="handleEnd">
        完成核查
      </el-button>
    </div>
    <TablePage class="tablePage"
               :tableData="projectData"
               :pageSize="queryParams.pageSize"
               :pageNum="queryParams.pageNum"
               :total="projectTotal"
               @handleSelectionChange="handleProjectSelection"
               @handleChange="handleProjectChange"
               @handleRowClick="handleProjectClick">
      <template slot="table">
        <el-table-column
          show-overflow-tooltip
          v-for="(item,index) in projectColumn"
          :key="index"
          :prop="item.prop"
          :label="item.label"
          :min-width="item.width" :formatter="item.formatter">
        </el-table-column>
        <el-table-column
          label="操作"  fixed="right"
          width="120" class-name="operation">
          <template slot-scope="scope">
            <el-button @click="viewCases(scope.row)"
                       type="text" size="mini">查看文件
            </el-button>
          </template>
        </el-table-column>
      </template>
    </TablePage>
    <TablePage class="tablePage"
               :hasSelection="false"
               :is-title="true"
               :tableTitle="tableProcessTitle"
               :tableData="processData"
               :pageSize="processSize"
               :pageNum="processNum"
               :total="processTotal"
               @handleChange="handleProcessChange">
      <template slot="table">
        <el-table-column
          show-overflow-tooltip
          v-for="(item,index) in processColumn"
          :key="index"
          :prop="item.prop"
          :formatter="item.formatter"
          :label="item.label"
          :min-width="item.width">
        </el-table-column>
        <el-table-column
          label="操作"  fixed="right"
          width="200" class-name="operation">
          <template slot-scope="scope">
            <el-button @click="handleExamineDetail(scope.row)"
                       type="text" size="mini">查看核查结果
            </el-button>
            <el-button @click="transferOfTheInspectors(scope.row)"
                       type="text" size="mini">任务转移
            </el-button>
          </template>
        </el-table-column>
      </template>
    </TablePage>
    <el-dialog title="查看核查进度"
               :visible.sync="dialogProgress"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="80%" class="dialog-style">
      <TablePage class="tablePage"
                 :hasSelection="false"
                 :tableData="progressData"
                 :pageSize="progressSize"
                 :pageNum="progressNum"
                 :total="progressTotal"
                 @handleChange="handleProgressChange">
        <template slot="table">
          <el-table-column
            show-overflow-tooltip
            v-for="(item,index) in progressColumn"
            :key="index"
            :prop="item.prop"
            :label="item.label"
            :min-width="item.width">
          </el-table-column>
        </template>
      </TablePage>
    </el-dialog>
    <el-dialog title="查看核查结果"
               :visible.sync="dialogExamine"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="1300px" class="dialog-style">
      <TablePage class="tablePage1"
                 :isPage="false"
                 :hasSelection="false"
                 :is-title="false"
                 :tableTitle="checkTitle"
                 :tableData="examineData"
                 :pageSize="checkQueryParams.pageSize"
                 :pageNum="checkQueryParams.pageNum"
      >
        <template slot="table">
          <el-table-column
            v-for="(item,index) in examineColumn"
            :key="index"
            :prop="item.prop"
            :label="item.label"
            :min-width="item.width"
            :formatter="item.formatter"
          :class-name="item.cellName">
          </el-table-column>
        </template>
      </TablePage>
    </el-dialog>
    <el-dialog title="移交核查人"
               :visible.sync="dialogHandOver"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="80%" class="dialog-style">
      <!--      <CheckTable :isTitle="true" :tableData="examineData" :tableColumn="examineColumn" :isImpeach="true"-->
      <!--                  :isShowChangeContent="true" style="margin-top: 20px"/>-->

      <TablePage class="tablePage1"
                 ref="userProjectRef"
                 :isPage="true"
                 :hasSelection="true"
                 :is-title="false"
                 :tableTitle="checkTitle"
                 :tableData="userData"
                 :total="userTotal"
                 @handleSelectionChange="userHandleSelectionChange" @handleChange="userHandleChange"
                 :pageSize="userQueryParams.pageSize"
                 :pageNum="userQueryParams.pageNum"
      >
        <template slot="table">
          <el-table-column
            show-overflow-tooltip
            v-for="(item,index) in userColumn"
            :key="index"
            :prop="item.prop"
            :label="item.label"
            :min-width="item.width"
            :formatter="item.formatter">
          </el-table-column>
        </template>
      </TablePage>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" size="mini" @click="userSave('processFormRef')">保存</el-button>
        <el-button size="mini" @click="dialogHandOver=false">取消</el-button>
      </span>
    </el-dialog>
    <CaseUpload ref="CaseUpload" :dialogCaseExcel="dialogCaseExcel" :projectId="tableProjectId" :archivesId="archivesId"
                @closeCaseExcel="closeCaseExcel"></CaseUpload>
    <!--  导入文件条目  -->
    <FileUpload ref="FileUpload" :dialogFileExcel="dialogFileExcel" :projectId="tableProjectId" :archivesId="archivesId"
                @closeFileExcel="closeFileExcel"></FileUpload>

    <FilePathUploader ref="FilePathUploader" :dialogSimpleUploader="dialogFilePathUploader" :projectId="tableProjectId"
                      :archivesId="archivesId" @closeFilePath="closeFilePath"></FilePathUploader>
    <!--  分片上传 电子文件  -->

    <ProjectUpload ref="simpleUploader" :projectId="tableProjectId" :archivesId="archivesId"
                    :dialogSimpleUploader="dialogSimpleUploader"
                    @closeSimpleUploader="closeSimpleUploader"></ProjectUpload>
    <el-dialog title="查看文件"
               :visible.sync="dialogFile"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="90%" class="dialog-style dialogFile">
      <TablePage class="tablePage2"
                 :hasSelection="false"
                 :is-title="false"
                 :tableTitle="caseTitle"
                 :tableData="caseData"
                 :pageSize="queryParams.pageSize"
                 :pageNum="queryParams.pageNum"
                 :total="total"
                 :isPage="false"
                 @handleRowClick="viewTheFileHandleProjectClick"
                 @handleChange="handleChange">
        <template slot="table">
          <el-table-column
            show-overflow-tooltip
            v-for="(item,index) in caseColumn"
            :key="index"
            :prop="item.prop"
            :label="item.label"
            :min-width="item.width"
            :formatter="item.formatter">
          </el-table-column>
        </template>
      </TablePage>
      <div class="imgContent">
        <p class="imgDes">第{{ imgPageItem }}件第{{ this.imgPageNum }}页</p>
        <div class="imgDiv">
          <DigitalPhoto :imgList="imgList" @carouselToggle="carouselToggle"
                        @downloadTheImage="downloadTheImage"></DigitalPhoto>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>

  import CheckTable from "@/views/fileCheck/checkTable.vue";
  import TablePage from "@/components/Public/table/TablePage.vue";
  import {getOne} from "@/api/projectManage/projectInitialize";
  import {formatDate} from "@/utils";
  import {
    exportExcelAccordingToFileType,
    getInformation,
    getTheFileInformationAndCompressIt,
    getzipInformation,
    downloadFileBig
  } from "@/api/fileData/fileData";
  import {
    theTaskManagementPageQueriesTheSuspectData,
    taskManagementPageQueriesTheSuspectData,
    taskManagementQueryOperations,
    taskManagementCompletesTheVerification,
    queryElectronicProfileTemplateIIArchives,
    doubtQueryVerificationResultData,
    listData,
    modifyTheVerifier,
    download,
    getPucter, queryCaseLevelArchivesDossierLevelTemplateTwo
  } from "@/api/record/record";
  import {queryProjectPullDown} from "@/api/projectManage/projectInitialize";
  import {queryUser, listUser} from "@/api/system/user";
  import {query} from "@/api/projectManage/projectProcedure";
  import {
    obtainCaseDataAttr,
    selectListAndMetadata,
    theFileNumberFieldIsThatDatabaseField
  } from "@/api/business/archiveType";
  import FilePathUploader from "@/views/upload/filePathUploader";
  import FileUpload from "@/views/upload/fileUpload";
  import SimpleUploader from "@/views/upload/simpleUploader";
  import ProjectUpload from "@/views/upload/projectUpload";
  import CaseUpload from "@/views/upload/caseUpload";
  import DigitalPhoto from "@/views/projectManage/projectInitialize/digitalPhoto";

  export default {
    name: "index",
    components: {DigitalPhoto, TablePage, CheckTable, FilePathUploader, FileUpload, SimpleUploader, CaseUpload,ProjectUpload},

    data() {
      return {
        checkQueryParams: {
          pageSize: 20,
          pageNum: 1,
        },
        userQueryParams: {
          pageSize: 20,
          pageNum: 1,
          status: 0,
        },
        checkTitle: '核查项信息',
        queryParams: {
          projectName: '',
          projectId: '',
          // impeachSolveId: 1,
          isAccomplishInspect: 1,
          pageSize: 20,
          pageNum: 1,
        },
        queryFileParams: {
          pageNum: 1,
          pageSize: 20,
          attrOrder: ''
        },
        taskQueryParams: {
          projectName: '',
          projectId: '',
          // impeachSolveId: 1,
          isAccomplishInspect: 1,
        },
        projectData: [],
        projectColumn: [
          // // {label: '案卷档号', prop: 'date', width: '100'},
          // // {label: '项目描述', prop: 'name', width: '100'},
          // // {label: '创建人', prop: 'address', width: '100'},
          // // {label: '创建时间', prop: 'remarks', width: '100',formatter:formatDate},
          // // {label: '备注', prop: 'remarks', width: '100'},
          // {label: '案卷档号', prop: 'filesName', width: '100'},
          // // {label: '当前工序', prop: 'procedureId', width: '100', formatter: this.procedureIdFormatter},
          // {label: '项目', prop: 'projectId', width: '100', formatter: this.projectIdFormatter},
          // // {label: '是否已完成当前工序核查', prop: 'isProcedureInspect', width: '100'},
          // // {label: '核查人姓名', prop: 'inspectId', width: '100', formatter: this.inspectIdFormatter},
        ],
        selected: [], //选中数据
        userSelected: [], //选中数据
        projectTotal: 0,
        tableProcessTitle: '工序信息：',
        processData: [],
        processColumn: [
          {label: '案卷档号', prop: 'filesName', width: '100'},
          // {label: '项目', prop: 'projectId', width: '100', formatter: this.projectIdFormatter},
          {label: '当前工序', prop: 'procedureId', width: '100', formatter: this.procedureIdFormatter},
          {label: '工序描述', prop: 'procedureDesc', width: '100'},
          {label: '状态', prop: 'isProcedureInspect', width: '100', formatter: this.isProcedureInspectFormatter},
          {label: '核查人', prop: 'inspectId', width: '100', formatter: this.inspectIdFormatter},
          {label: '是否存疑过', prop: 'isImpeach', width: '100', formatter: this.isImpeachFormatter},
          {label: '解决存疑人', prop: 'impeachSolveId', width: '100', formatter: this.inspectIdFormatter},
          {label: '工序备注', prop: 'remark', width: '100'},
        ],
        processTotal: 0,
        processSize: 20,
        processNum: 1,
        dialogType: 'add',
        dialogProgress: false,
        progressData: [],
        progressColumn: [
          {label: '工序名称', prop: 'date', width: '100'},
          {label: '工序描述', prop: 'name', width: '100'},
          {label: '核查项范围', prop: 'address', width: '100'},
          {label: '核查项数量', prop: 'remarks', width: '100'},
          {label: '可核查人数', prop: 'remarks', width: '100'},
        ],
        userData: [],
        userTotal: 0,
        userColumn: [
          {label: '用户名称', prop: 'userName'},
          {label: '用户昵称', prop: 'nickName'},
          {label: '手机号码', prop: 'phonenumber', width: '120'},
          {label: '邮箱', prop: 'email', width: '120'},
        ],
        progressTotal: 0,
        progressSize: 10,
        progressNum: 1,
        dialogExamine: false,
        dialogHandOver: false,
        examineData: [],
        examineColumn: [
          {label: '核查项名称', prop: 'trueingName', width: '120', cellName: 'cellName'},
          {label: '核查项描述', prop: 'trueingDesc', width: '150', cellName: 'cellName'},
          {label: '核查标准', prop: 'examineStasString', width: '180', cellName: 'cellName'},
          {label: '核查结果', prop: 'checkResultState', width: '60', formatter: this.checkResultStateFormatter},
          {label: '不合格件/页号', prop: 'nonConforming', width: '80'},
          // {label: '不合格页号', prop: 'pageNumS', width: '100'},
          {label: '处理方式', prop: 'isQuestion', width: '60', formatter: this.isQuestionFormatter},
          {label: '更改内容', prop: 'isMasterCopy', width: '60', formatter: this.isMasterCopyFormatter}
        ],
        examineTotal: 0,
        examineSize: 10,
        examineNum: 1,
        /*项目id集合*/
        projectIdTable: [],
        projectProcedureIdTable: [],
        userIdTable: [],
        /*档案id*/
        filesId: '',
        isAccomplishInspectOption: [
          {value: 0, label: "已完成"},
          {value: 1, label: "未完成"}
        ],
        receptionProjectId: '',
        /*弹窗开关*/
        dialogCaseExcel: false,
        dialogFileExcel: false,
        dialogFilePathUploader: false,
        dialogSimpleUploader: false,
        /*点击项目表格获取项目id*/
        tableProjectId: '',
        archivesId: '',
        userId: '',
        /*核查结果*/
        verificationResultsFrom: {},
        /*图片集合*/
        imgList: [],
        /*案件数据*/
        fileForm: {},
        /*文件档号的attr*/
        attrOfTheCaseFileNumber: '',
        /*顺序号*/
        sequenceNumber: '',
        imgPageNum: 0,
        imgPageItem: 0,
        /*轮播图页数*/
        numberOfCarouselPages: 0,
        caseColumn: [],
        caseTitle: '案卷信息',
        dialogFile: false,
        caseData: [],
        total: 0,
        timerSwitch: '',
      }
    },
    created() {

      this.queryProjectPullDown();
      this.queryProjectProcedure();
      this.queryUser();
      this.selectListAndMetadata();


    },
    methods: {
      /*格式化时间*/
      formatTime(date) {
        let year = date.getFullYear();      //年
        let month = date.getMonth() + 1;    //月
        let strDate = date.getDate();       //日
        let hours = date.getHours();        //时
        let minutes = date.getMinutes();    //分
        let seconds = date.getSeconds();    //秒
        let weekDay = date.getDay(); //星期
        return year.toString() + month.toString() + strDate.toString() + hours.toString() + minutes.toString() + seconds.toString();
      },
      /**********************************************************导出处理 -- 开始********************************/
      /*导出下拉框*/
      exportData(val) {
        const projects = this.projectIdTable.filter(a => a.value == this.tableProjectId)
        let ids = this.selected.map(item => {
          return item.filesId
        })
        const arr = {
          archiveTypeId: this.archivesId, //档案类型Id
          projectId: this.tableProjectId, //项目Id
          projectName: projects[0].label, //项目名称
          level: 0, //1-案卷 2-案件
          fileIds: ids//案卷Id集合
        };
        /*案卷*/
        if (val == 'caseExcel') {
          this.exportExcelAccordingToFileType(arr);
        } else if (val == 'zipExcel') {
          this.getTheFileInformationAndCompressIt()
        }


      },

      /*获取上传文件是否完成*/
      getzipInformation(val) {
        getzipInformation(val).then(res => {
          this.timerSwitch = res.data;
          if (res.data == '失败') {
            this.$message({message: res.msg, type: 'error', showClose: false});
          } else if (res.data == '成功') {
            const id = res.msg.split('-')[1]
            if (id != "") {
              this.$message({message: res.msg.split('-')[0], type: 'success', showClose: false});
              /*执行下载*/
              /*会跳转页面*/
              window.location.href = process.env.VUE_APP_BASE_API + 'uploader/downloadFileBig?id=' + id;
              // downloadFileBig(id).then(res => {
              //   console.log("下载成功")
              // });
            } else {
              this.$message.error("下载失败");
            }
          }
        });
      },
      /*压缩到本地*/
      getTheFileInformationAndCompressIt() {
        let ids = this.selected.map(item => {
          return item.filesId
        })
        const arr = {
          projectId: this.tableProjectId,
          archivesId: this.archivesId,
          fileIds: ids
        }
        getTheFileInformationAndCompressIt(arr).then(res => {
          if (res.code === 20000) {
            this.timerSwitch = '';
            this.$message.success(res.msg)
            var interval = setInterval(() => {
              if (this.timerSwitch == '失败') {
                clearInterval(interval);
                console.log("停止1")
              } else if (this.timerSwitch == '成功') {
                clearInterval(interval);
                console.log("停止2")
              } else {
                this.getzipInformation(res.data);
              }
            }, 3000) //五秒执行一次
          }
        });
      },
      /*导出案卷数据*/
      exportExcelAccordingToFileType(arr) {
        exportExcelAccordingToFileType(arr).then(res => {
          if (res.code == 20000) {
            this.$message.success("压缩文件成功，开始下载");
            window.location.href = process.env.VUE_APP_BASE_API + 'uploader/downloadFileBig?id=' + res.data;
          }
        });
      },
      /**********************************************************导出处理 -- 结束********************************/
      /**********************************************************图片处理 -- 开始********************************/
      /*根据档案类型id查询 档号 在数据库的那个字段*/
      theFileNumberFieldIsThatDatabaseField() {
        theFileNumberFieldIsThatDatabaseField(this.archivesId).then(res => {
          // this.queryFileParams.attrOrder = res.data.attrOrder;
          this.queryParams.attrOrder = "attr" + res.data.attrOrder;
          /*查询档案数据*/
          this.taskManageQuery();
        });
      },
      selectListAndMetadataCase() {
        const arr2 = {
          id: this.archivesId,
          level: 2 //文件表
        };
        /*文件表头*/
        selectListAndMetadata(arr2).then(res => {
          res.data[0].dahcMetadataList.map(item => {
            item.label = item.metadataName;
            item.prop = 'attr' + item.attrOrder;
            item.width = item.metadataWidth;
          });
          this.caseColumn = res.data[0].dahcMetadataList;

          this.queryCaseLevelArchivesDossierLevelTemplateTwo();
        });
      },
      /*查询文件数据*/
      queryCaseLevelArchivesDossierLevelTemplateTwo() {
        const arr = {
          filesId: this.filesId,
          archiveId: this.archivesId,
          pageNum: this.queryFileParams.pageNum,
          pageSize: this.queryFileParams.pageSize,
          attrOrder: this.attrOfTheCaseFileNumber,
          caseNum: this.queryFileParams.filesName,
        };
        queryCaseLevelArchivesDossierLevelTemplateTwo(arr).then(res => {
          this.caseData = res.data
          this.fileForm = res.data[0];
          this.imgPageItem = res.data[0][this.sequenceNumber]
          this.getPucter();
        });

      },
      /**
       * 分页器
       */
      handleChange(pageSize, pageNum) {
        this.queryFileParams.pageSize = pageSize;
        this.queryFileParams.pageNum = pageNum;
        // this.handleQuery();
      },
      /*下载图片*/
      downloadTheImage() {
        console.log(this.imgList[this.numberOfCarouselPages], "下载")
        if (this.imgList.length == 0) {
          this.$message({message: '不存在图片数据，不能下载图片', type: 'warning'});
        } else {
          this.$confirm('此操作将下载文件：' + this.imgList[this.numberOfCarouselPages].keyName + '下图片名称为：' + this.imgList[this.numberOfCarouselPages].fileName + '的数据, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }).then(() => {
            download({path: this.imgList[this.numberOfCarouselPages].filePath}).then(res => {
              const fileName = this.imgList[this.numberOfCarouselPages].filePath.lastIndexOf("\\");
              let href = window.URL.createObjectURL(new Blob([res])) // 根据后端返回的url对应的文件流创建URL对象
              const link = document.createElement('a')//创建一个隐藏的a标签
              link.style.display = 'none'
              link.href = href//设置下载的url
              link.download = this.imgList[this.numberOfCarouselPages].filePath.substr(fileName + 1) //设置下载的文件名
              document.body.appendChild(link)
              link.click()
              document.body.removeChild(link)
              window.URL.revokeObjectURL(href) // 释放掉blob对象
            });
          });
        }
      },


      /*轮播图切换*/
      carouselToggle(val) {
        this.numberOfCarouselPages = val
        this.imgPageNum = this.imgList[val].pageNum;
      },
      /*获取文件档号在哪个attr*/
      obtainCaseDataAttr() {
        const arr = {
          metadataName: '档号',
          archiveTypeId: this.archivesId
        };
        obtainCaseDataAttr(arr).then(res => {
          this.attrOfTheCaseFileNumber = "attr" + res.data;
        });
        const arr1 = {
          metadataName: '顺序号/件号',
          archiveTypeId: this.archivesId
        };
        obtainCaseDataAttr(arr1).then(res => {
          this.sequenceNumber = "attr" + res.data;
        });
      },

      /*查询图片信息*/
      getPucter() {
        const arr = {
          keyName: this.fileForm[this.attrOfTheCaseFileNumber], //档号
          pid: this.fileForm.attrId //pid
        }
        getPucter(arr).then(res => {
          if (res.data.length > 0) {
            this.imgList = res.data;
            this.imgPageNum = res.data[0].pageNum
            this.imgList.map(item => {
              item.imgUrl = 'data:image/jpeg;base64,' + item.imgUrl;
              let url = item.imgUrl;
              const img = new Image();
              img.src = url;
            });
          }
        });
      },


      /*清空*/
      empty() {
        this.imgList = [];
        this.imgPageItem = 0
        this.imgPageNum = 0
      },

      /*案件表格点击事件*/
      viewTheFileHandleProjectClick(row, column, event) {
        this.empty();
        this.imgPageItem = row.row[this.sequenceNumber]
        this.fileForm = row.row
        this.getPucter();
      },
      /**********************************************************图片处理 -- 结束********************************/

      /************************************************导入处理 --开始***********************************/
      closeSimpleUploader(val) {
        this.dialogSimpleUploader = val;
      },
      closeFilePath(val) {
        this.dialogFilePathUploader = val;
      },
      closeFileExcel(val) {
        this.dialogFileExcel = val;
      },
      closeCaseExcel(val) {
        this.dialogCaseExcel = val;
      },
      importExcel(key) {
        if (key === 'caseExcel') {
          this.dialogCaseExcel = true;
        } else if (key === 'fileExcel') {
          this.dialogFileExcel = true;
          // console.log(this.$refs.FileUpload)
          // this.$refs.FileUpload.templateAssociatedMetadataFields();
          this.$refs.FileUpload.queryArchiveBatchData();
        } else if (key === 'filePathUploader') {
          this.dialogFilePathUploader = true;
          // this.$refs.FileUpload.templateAssociatedMetadataFields();
        } else {
          this.dialogSimpleUploader = true;
        }
      },


      /************************************************导入处理 --结束***********************************/
      /***********************************************表格字段转换 -- 开始 *************************/
      /*查询档案状态表格转换*/
      checkResultStateFormatter(row, column, cellValue) {
        let result = '';
        switch (cellValue) {
          case "qualified":
            result = "合格";
            break;
          case "unqualified":
            result = "不合格";
            break;
        }
        return result;
      },
      isQuestionFormatter(row, column, cellValue) {
        let result = '';
        switch (cellValue) {
          case "change":
            result = "更改";
            break;
          case "impeach":
            result = "存疑";
            break;
          case null:
            result = "无";
            break;
        }
        return result;
      },
      isMasterCopyFormatter(row, column, cellValue) {
        let result = '';
        switch (cellValue) {
          case "original":
            result = "原件";
            break;
          case "accessory":
            result = "复印件";
            break;
          case null:
            result = "无";
            break;
        }
        return result;
      },
      /*查询项目*/
      queryProjectPullDown() {
        this.queryParams.projectId = this.$route.query.receptionProjectId;
        this.tableProjectId = this.$route.query.receptionProjectId;
        queryProjectPullDown().then(res => {
          this.projectIdTable = res.data;
          // this.queryParams.projectId = this.projectIdTable[0].value

        });

      },
      /*查询用户*/
      queryUser() {
        queryUser().then(res => {
          this.userIdTable = res;
        });
      },
      /*查询工序*/
      queryProjectProcedure() {
        query().then(res => {
          this.projectProcedureIdTable = res;
        });
      },
      /*查询项目表格转换*/
      projectIdFormatter(row, column, cellValue) {
        let result = '';
        this.projectIdTable.map((item) => {
          if (item.value == cellValue) {
            result = item.label;
          }
        });
        return result;
      },
      /*查询用户表格转换*/
      inspectIdFormatter(row, column, cellValue) {
        let result = '';
        this.userIdTable.map((item) => {
          if (item.userId == cellValue) {
            result = item.nickName;
          }
        });
        return result;
      },
      /*查询工序表格转换*/
      procedureIdFormatter(row, column, cellValue) {
        let result = '';
        this.projectProcedureIdTable.map((item) => {
          if (item.id == cellValue) {
            result = item.procedureName;
          }
        });
        return result;
      },

      /*查询档案状态表格转换*/
      isProcedureInspectFormatter(row, column, cellValue) {
        let result = '';
        switch (cellValue) {
          case "0":
            result = "未领取";
            break;
          case "1":
            result = "已完成";
            break;
          case "2":
            result = "已领取";
            break;
          case "3":
            result = "存疑中";
            break;

        }
        return result;
      },
      /*查询档案状态表格转换*/
      isImpeachFormatter(row, column, cellValue) {
        let result = '';
        switch (cellValue) {
          case 0:
            result = "是";
            break;
          case "1":
            result = "否";
            break;
        }
        return result;
      },
      /***********************************************表格字段转换 -- 结束 *************************/
      /*查看文件*/
      viewCases(row) {
        // this.$router.push({path: "/fileMyTask", query: {projectId: this.tableProjectId, filesId: row.filesId}});
        this.selectListAndMetadataCase();
        this.dialogFile = true;
      },
      /*查询表头*/
      selectListAndMetadata() {
        getOne(this.$route.query.receptionProjectId).then(res => {
          this.archivesId = res.data.archiveTypeId;
          const arr = {
            id: res.data.archiveTypeId,
            level: 1
          };
          /*表头*/
          selectListAndMetadata(arr).then(res => {
            this.projectColumn = res.data[0].dahcMetadataList;
            this.projectColumn.map(item => {
              item.label = item.metadataName;
              item.label = item.metadataName;
              item.prop = 'attr' + item.attrOrder;
              item.width = item.metadataWidth;
            });
          });
          this.theFileNumberFieldIsThatDatabaseField();
          this.obtainCaseDataAttr();
        });
      },
      /*条件查询*/
      conditionalQueries() {
        this.queryParams.pageNum = 1
        this.queryParams.pageSize = 5
        this.taskManageQuery();
      },
      /*查询档案数据*/
      taskManageQuery() {
        this.taskQueryParams.projectId = this.queryParams.projectId
        this.taskQueryParams.isAccomplishInspect = this.queryParams.isAccomplishInspect
        taskManagementPageQueriesTheSuspectData(this.taskQueryParams).then(res => {
          this.queryParams.ids = res
          console.log(this.queryParams, "呵呵哈哈哈")
          theTaskManagementPageQueriesTheSuspectData(this.queryParams).then(res => {
            this.projectData = res.data
            this.projectTotal = res.total
            if (this.projectData.length > 0) {
              this.filesId = this.projectData[0].filesId;
              this.taskManagementQueryOperations();
            } else {
              this.processData = []
              this.processTotal = 0;
            }
          });
        });

      },

      /*查询下方工序数据*/
      taskManagementQueryOperations() {
        const arr = {
          filesId: this.filesId,
          pageNum: this.processNum,
          pageSize: this.processSize,
        }
        taskManagementQueryOperations(arr).then(res => {
          this.processData = res.data
          this.processTotal = res.total;
        });
      },

      /*表格点击事件*/
      handleProjectClick(row, column, event) {
        this.filesId = row.row.filesId
        this.taskManagementQueryOperations();
      },

      /**
       * 查看核查项详情
       */
      handleExamineDetail(row) {
        const dtoArr = {
          filesId: row.filesId, //工序id
          procedureId: row.procedureId,//档案名称
          isProcedureInspect: '有值',//档案名称
        };
        this.doubtQueryVerificationResultData(dtoArr);
      },

      /*移交核查人员保存*/
      userSave() {
        if (this.userId == '' || this.userId == undefined) {
          this.$message.error("请选择移交人员");
        } else {
          if (this.verificationResultsFrom.inspectId == '' || this.verificationResultsFrom.inspectId == undefined) {
            this.$message.error("没有核查人员信息无法修改");
          } else {
            this.verificationResultsFrom.inspectId = this.userId;
            modifyTheVerifier(this.verificationResultsFrom).then(res => {
              if (res.code == 20000) {
                this.$message.success("修改成功");
                this.dialogHandOver = false;
              }
            });
          }
        }
      },
      /*移交核查人员*/
      transferOfTheInspectors(row) {
        this.verificationResultsFrom = row;
        this.dialogHandOver = true
        this.userQuery();
      },
      userQuery() {
        listUser(this.userQueryParams).then(res => {
          this.userData = res.rows;
          this.userTotal = res.total;
        });
      },
      /**
       * 表格复选框
       */
      userHandleSelectionChange(selection) {
        this.userId = '';
        this.userSelected = selection
        // 清除 所有勾选项
        if (selection.length === 1) {
          this.userSelected = selection;
          this.userId = this.userSelected[0].userId

        }
        if (selection.length > 1) {
          this.$refs.userProjectRef.$refs.tableRef.clearSelection();
          this.$refs.userProjectRef.$refs.tableRef.toggleRowSelection(selection.at(-1), true);

          this.userSelected = selection.slice(-1);
          this.userId = this.userSelected[0].userId

        }
        if (selection.length === 0) {
          this.userSelected = [];
          this.userId = '';
        }
      },
      /**
       * 分页器
       */
      userHandleChange(pageSize, pageNum) {
        this.userQueryParams.pageSize = pageSize;
        this.userQueryParams.pageNum = pageNum;
        this.userQuery();
      },


      /*查询核查结果*/
      doubtQueryVerificationResultData(row) {
        listData(row).then(res => {
            this.examineData = res.data;
            if (this.examineData) {
              this.examineData.forEach(item => {
                item.checkResultState = item.checkResult[0]
                item.isMasterCopy = item.changeContent[0]
                item.isQuestion = item.processMode[0]
                let nonConforming = '';
                item.pageNumS.forEach(item1 => {
                  if (item1.arrItem != null) {
                    nonConforming = nonConforming + "第" + item1.arrItem + "件";
                  }
                  if (item1.arrPage != null) {
                    nonConforming = nonConforming + item1.arrPage + "页；";
                  }
                  if (item1.arrItem != null && item1.arrPage == null) {
                    nonConforming = nonConforming + ";"
                  }
                });
                item.nonConforming = nonConforming;
                if (item.nonConforming == '') {
                  item.nonConforming = '无'
                }
                item.pageNumS = item.pageNumS.slice(0, -1);
                item.caseNumS = item.caseNumS.slice(0, -1);
                let str = item.examineStasString.replace(/\//g, '；');
                item.examineStasString = str.slice(0, item.examineStasString.length - 1)
              })
            }
            this.dialogExamine = true;
          }
        );
      },


      handleBack() {
        this.$router.go(-1);
      },
      /**
       * 结束核查
       */
      handleEnd() {
        if (!this.selected || this.selected === [] || this.selected.length === 0) {
          this.$message({
            message: '请至少选择一条数据',
            type: 'warning'
          });
        } else {
          this.$confirm('此操作将结束当前项目的核查任务, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }).then(() => {
            let ids = this.selected.map(item => {
              return item.filesId
            })
            taskManagementCompletesTheVerification(ids).then(res => {
              this.$message.success(res.msg)
              this.selected = [];
              this.taskManageQuery();
            })

          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消开始核查'
            });
          });
        }
      },
      /**
       * 表格复选框
       */
      handleProjectSelection(selection) {
        this.selected = selection
      },

      /**
       * 查看核查进度
       */
      handleExamineProgress() {
        this.dialogProgress = true
      },
      handleProgressChange(pageSize, pageNum) {
        this.progressSize = pageSize;
        this.progressNum = pageNum;
      },
      /**
       * 分页器
       */
      handleProjectChange(pageSize, pageNum) {
        this.queryParams.pageSize = pageSize;
        this.queryParams.pageNum = pageNum;
        this.taskManageQuery();
      },

      handleExamineChange(pageSize, pageNum) {
        this.examineSize = pageSize;
        this.examineNum = pageNum;
      },
      handleProcessChange(pageSize, pageNum) {
        this.processSize = pageSize;
        this.processNum = pageNum;
        this.taskManagementQueryOperations();
      },


    }
  }
</script>

<style lang="scss" scoped>

  .tablePage {
    height: calc(50% - 52px) !important;
  }

  ::v-deep .dialogFile {
    .el-dialog__body {
      display: flex;
      justify-content: space-between;

      .tablePage2 {
        height: calc(83vh);
        margin-top: 20px;
        width: calc(45% - 10px) !important;
      }

      .imgContent {
        height: calc(83vh + 20px) !important;
        width: calc(55% - 10px);

        .imgDes {
          font-weight: 600;
          font-size: 13px;
          color: #606266;
          position: relative;
          left: 5px;
        }

        .imgDiv {
          height: calc(83vh - 3px) !important;
          width: 100%;
          border: 1px solid #dfe6ec;
          margin-top: 3px;
        }
      }
    }
  }

  .tablePage1 {
    //height: calc(100% - 0px);
    height: 400px !important;
  }
  ::v-deep .el-table__body-wrapper {
   .cellName {
      .cell {
        word-break: keep-all;
        word-wrap: break-word;
        text-align: left;
        //color: red;
      }
    }
  }
</style>


