<template>
  <div class="app-container-padding">
    <CheckTop :queryParams="queryParams" :checkRange="checkRange"
              :processName="receptionProcedure.procedureName"
              :processDes="receptionProcedure.procedureDesc"
              :impeach="isImpeach"
              :notVerifiedCount="notVerifiedCount"
              :notVerifiedCountShow="!isImpeach"
              :showInputCaseNum="showInputCaseNum" @handleReceivedPhy="handleReceivedPhy"
              @handleBack="handleBack"></CheckTop>
    <TablePage class="tablePage"
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

          width="100" class-name="operation">
          <template slot-scope="scope">
            <el-button type="text" size="mini" @click="handleSave(scope.row)" v-if="scope.row.isUpdate">
              保存
            </el-button>
            <el-button type="text" size="mini" @click="handleUpdate(scope.row)" v-else>
              编辑
            </el-button>
          </template>
        </el-table-column>
      </template>
    </TablePage>
    <CheckTable :isTitle="true" :tableData="checkItemData" :isImpeach="isImpeach"
                :isShowChangeContent="true"/>
    <div class="subBtn">
      <el-button type="primary" size="mini" @click="handleSubmit">提交</el-button>
    </div>
  </div>
</template>

<script>
  import CheckTop from "@/views/fileCheck/checkTop.vue";
  import TablePage from "@/components/Public/table/TablePage.vue";
  import CheckTable from "@/views/fileCheck/checkTable.vue";
  import {
    doubtQueryVerificationResultData,
    drawEntityFile,
    insertProcedureInspect,
    listData,
    queryBasedOnTheIDOfTheVerificationResult,
    queryElectronicArchivesDossierLevelTemplateTwo,
    submissionOfVerificationResultsInDoubt,
    theNumberOfUncheckedFilesInTheQueryFile
  } from "@/api/record/record";
  import {getOneProjectprocedure} from "@/api/projectManage/projectProcedure";
  import {selectListAndMetadata, theFileNumberFieldIsThatDatabaseField} from "@/api/business/archiveType";
  import {updateFileData} from "@/api/fileData/fileData";
  import {getData} from "@/api/dahc/sys/dictData";

  export default {
    name: "index",
    components: {CheckTable, TablePage, CheckTop},
    data() {
      return {
        queryParams: {
          caseNum: '',
        },
        // /*表头*/
        // tableColumn: [
        //   {label: '核查项名称', prop: 'trueingName', width: '100'},
        //   {label: '核查项描述', prop: 'trueingDesc', width: '100'},
        //   {label: '核查标准', prop: 'examineStasString', width: '100', cellName: 'cellName'},
        // ],
        checkRange: '电子档案对照实体',
        processName: '工序名称',
        processDes: '工序描述',
        caseTitle: '案卷信息：',
        caseData: [],
        caseColumn: [],
        caseTotal: 0,
        caseSize: 10,
        caseNum: 1,
        checkItemData: [],
        returnState: 20000,
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
        /*未核查数量*/
        notVerifiedCount: 0
      }

    },
    created() {
      this.receptionProcedureId = this.$route.query.receptionProcedureId;
      this.receptionArchiveTypeId = this.$route.query.receptionArchiveTypeId;
      if (this.$route.query.isImpeach == 'true') {
        this.isImpeach = true;
        this.showInputCaseNum = true;
      } else {
        this.isImpeach = false;
      }
      this.getOne();
      this.theFileNumberFieldIsThatDatabaseField();
    },
    methods: {
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
            this.queryInspectList();
          }
        });
      },
      /*根据档案类型id查询 档号 在数据库的那个字段*/
      theFileNumberFieldIsThatDatabaseField() {
        theFileNumberFieldIsThatDatabaseField(this.receptionArchiveTypeId).then(res => {
          this.queryParams.attrOrder = res.data.attrOrder;
          this.queryParams.archiveLevelName = res.data.archiveLevelName;
        });
      },
      /*初始查询核查项数据*/
      queryInspectList() {
        this.queryParams.procedureId = this.receptionProcedureId;
        listData(this.queryParams).then(res => {
          console.log(res.data[0], "初审")
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
          this.checkItemData = res.data;
          if (this.checkItemData) {
            this.checkItemData.forEach(item => {
              let str = item.examineStasString.replace(/\//g, '；');
              item.examineStasString = str.slice(0, item.examineStasString.length - 1)
            })
          }
        });
      },
      /**
       * 领取档案
       */
      handleReceivedPhy() {
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
            this.handleQuery("2");
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

            }
            /*已领取*/
            this.caseData = arrRes;
            // this.queryDataOnCompletedVerificationItems();
          }
        });
      },
      cellName({row, column, rowIndex, columnIndex}) {
        console.log({row, column, rowIndex, columnIndex})
      },
      /**
       * 分页
       */
      handleCheckItem(pageSize, pageNum) {
        this.processSize = pageSize;
        this.processNum = pageNum;
      },
      /**
       * 返回
       */
      handleBack() {
        this.$router.go(-1)

        if (this.isImpeach) {
          this.checkItemData.map(item => {
              item.rollId = this.queryParams.caseNum;
              item.isInspectAccomplish = 1;
              item.filesId = this.$route.query.filesId;
              item.recordProcedureFilesId = this.$route.query.recordProcedureFilesId;
              item.buttonName = "返回";
              item.projectId = this.receptionProcedure.projectId
              item.checkStatus = this.isImpeach

            }
          );
          /*提交存疑结果*/
          this.submissionOfVerificationResultsInDoubt(this.checkItemData, 1);
        } else {
          this.checkItemData.map(item => {
              item.rollId = this.queryParams.caseNum;
              item.isInspectAccomplish = 1
              item.filesId = this.queryParams.filesId
              item.buttonName = "返回";
              item.projectId = this.receptionProcedure.projectId
              item.recordProcedureFilesId = this.queryParams.recordProcedureFilesId
              item.checkStatus = this.isImpeach
            }
          );
          if (this.showInputCaseNum) {
            /*保存核查数据*/
            insertProcedureInspect(JSON.stringify(this.checkItemData)).then(res => {
              if (res.code == this.returnState) {
                this.$message.success("保存成功");
              }
            });
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
          /*等于false不走提交*/

          if (this.caseData.length == 0) {
            this.$message.error("没有档案需要提交");
          } else {
            var arr = this.checkItemData.every(item => {
                item.rollId = this.queryParams.caseNum;
                item.isInspectAccomplish = 0;
                item.filesId = this.queryParams.filesId
                item.recordProcedureFilesId = this.queryParams.recordProcedureFilesId
                item.projectId = this.receptionProcedure.projectId
                item.buttonName = "提交"
                item.checkStatus = this.isImpeach
                whetherToVerify = this.whetherItIsEmpty(item);
                return whetherToVerify.type;
              }
            );
            if (arr) {
              insertProcedureInspect(JSON.stringify(this.checkItemData)).then(res => {
                if (res.code == this.returnState) {
                  this.$message.success("提交成功");
                  this.showInputCaseNum = false;
                  this.queryParams.caseNum = '';
                  this.caseData = [];
                  this.queryInspectList();
                }
              });
            } else {
              this.$message.error(whetherToVerify.msg);
            }
          }

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

      /**
       * 编辑档案
       */
      handleUpdate(row) {
        console.log(row)
        row.isUpdate = !row.isUpdate
      },
      /**
       * 保存档案
       */
      handleSave(row) {
        row.isUpdate = !row.isUpdate
        row.id = row.filesId;
        row.archiveTypeId = this.receptionArchiveTypeId
        row.level = 1
        row.procedureId = this.receptionProcedureId
        row.fileId = row.attrId
        row.buttonName = "保存案卷档案"
        row.procedureId = this.receptionProcedureId
        row.pid = this.caseData[0].filesId
        row.projectId = this.receptionProcedure.projectId
        row.caseFileNumber = this.caseData[0]["attr" + this.queryParams.attrOrder]
        row.checkStatus = this.isImpeach
        updateFileData(row).then(res => {
          if (res.code == this.returnState) {
            this.$message.success("修改成功");
          }
        });
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
            }
          }
        });
      },
      /************************************************** 存疑方法 结束*****************************/
    }
  }
</script>

<style lang="scss" scoped>
  .tablePage {
    height: 120px;
    margin-top: 20px;
  }

  .checkTable {
    height: calc(100% - 275px) !important;
    margin-top: 25px;
  }
</style>
