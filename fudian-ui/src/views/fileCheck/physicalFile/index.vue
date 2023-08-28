<template>
  <div class="app-container-padding">
    <CheckTop :queryParams="queryParams" :checkRange="checkRange" :processName="receptionProcedureName"
              :processDes="processDes" :showInputCaseNum="showInputCaseNum" :notVerifiedCount="notVerifiedCount"
              :impeach="isImpeach" :notVerifiedCountShow="!isImpeach"
              @handleReceivedPhy="handleReceivedPhy" @handleBack="handleBack"></CheckTop>
    <CheckTable :tableData="tableData" :tableColumn="tableColumn" :isShowChangeContent="true" :isImpeach="isImpeach"
                :sourcePage="sourcePage"></CheckTable>
    <div class="subBtn">
      <el-button type="primary" size="mini" @click="handleSubmit">提交</el-button>
    </div>
  </div>
</template>

<script>
  import CheckTable from "@/views/fileCheck/checkTable.vue";
  import CheckTop from "@/views/fileCheck/checkTop.vue";
  import {
    listData,
    insertProcedureInspect,
    drawEntityFile,
    theNumberOfUncheckedFilesInTheQueryFile,
    queryBasedOnTheIDOfTheVerificationResult, doubtQueryVerificationResultData, submissionOfVerificationResultsInDoubt
  } from "@/api/record/record"
  import {theFileNumberFieldIsThatDatabaseField} from "@/api/business/archiveType"
  import {getOneProjectprocedure} from "@/api/projectManage/projectProcedure"
  import {getData} from "@/api/dahc/sys/dictData"

  export default {
    name: "index",
    components: {CheckTop, CheckTable},
    data() {
      return {
        sourcePage: '',
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          procedureId: '',
          attrOrder: '',
          archiveLevelName: '',
          caseNum: '',
          filesId: '',
          recordProcedureFilesId: '',
        },
        /*表头*/
        tableColumn: [
          {label: '核查项名称', prop: 'trueingName', width: '100'},
          {label: '核查项描述', prop: 'trueingDesc', width: '100'},
          {label: '核查标准', prop: 'examineStasString', width: '100', cellName: 'cellName'},
        ],
        checkRange: '实体档案',
        processName: '工序名称',
        processDes: '工序描述',
        returnState: 20000,
        tableData: [],
        /*跳转页面接收对象,工序参数*/
        /*工序id*/
        receptionProcedureId: '',
        /*工序名称*/
        receptionProcedureName: '',
        /*工序描述*/
        receptionProcedureDesc: '',
        /*项目id*/
        receptionProjectId: '',
        /*项目关联档案类型id*/
        receptionArchiveTypeId: '',
        /*核查项名称*/
        receptionTrueingId: '',
        showInputCaseNum: false,
        /*是否是存疑页面跳转过来的*/
        isImpeach: false,
        /*未核查数量*/
        notVerifiedCount: 0
      };
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
      /*根据档案类型id查询档号是哪个字段*/
      this.theFileNumberFieldIsThatDatabaseField();
      this.query();

    },
    methods: {

      query() {
        if (this.isImpeach) {
          /*存疑页面查询*/
          this.queryParams.caseNum = this.$route.query.caseNum;
          this.doubtQueryVerificationResultData();
        } else {
          this.queryInspectList();
        }

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
          console.log(res.data, this.notVerifiedCount, "未核查")
        });
      },
      /*********************核查范围转换********************/
      getOne() {
        getOneProjectprocedure(this.receptionProcedureId).then(res => {
          this.receptionProcedureName = res.data.procedureName;
          this.processDes = res.data.procedureDesc;
          this.receptionProjectId = res.data.projectId;
          this.queryTrueingId(res.data.trueingId);
          this.theNumberOfUncheckedFilesInTheQueryFile(this.receptionProcedureId);
        });
      },
      /******************************************************核查项处理 --开始*******************************************************/
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
          this.queryParams.caseNum = res.data[0].rollId
          this.queryParams.filesId = res.data[0].filesId
          this.queryParams.recordProcedureFilesId = res.data[0].recordProcedureFilesId
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
          this.tableData = res.data;
          if (this.tableData) {
            this.tableData.forEach(item => {
              let str = item.examineStasString.replace(/\//g, '；');
              item.examineStasString = str.slice(0, item.examineStasString.length - 1)
            })
          }
        });
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
      /*提交*/
      handleSubmit() {
        /*是否核查*/
        let whetherToVerify = {};
        if (this.isImpeach) {
          /*提交存疑时调用方法*/
          let isQuestion = false;
          this.tableData.map(item => {
              item.rollId = this.queryParams.caseNum;
              item.isInspectAccomplish = 0;
              item.filesId = this.$route.query.filesId;
              item.recordProcedureFilesId = this.$route.query.recordProcedureFilesId;
              item.projectId = this.receptionProjectId
              item.buttonName = "提交"
              item.checkStatus = this.isImpeach
              if (item.processMode[0] == 'impeach') {
                isQuestion = true;
              }
            }
          );
          if (isQuestion) {
            this.$message.warning("存在存疑数据");
            isQuestion = false;
          } else {
            /*提交存疑结果*/
            this.submissionOfVerificationResultsInDoubt(this.tableData, 0);
          }
        } else {
          if (this.showInputCaseNum == false) {
            this.$message.error("没有领取档案的数据");
          } else {
            /*正常核查时调用方法*/
            if (this.queryParams.caseNum != null) {
              var arr = this.tableData.every(item => {
                  item.buttonName = "提交"
                  item.rollId = this.queryParams.caseNum;
                  item.isInspectAccomplish = 0;
                  item.filesId = this.queryParams.filesId
                  item.recordProcedureFilesId = this.queryParams.recordProcedureFilesId
                  item.checkStatus = this.isImpeach
                  whetherToVerify = this.whetherItIsEmpty(item);
                  return whetherToVerify.type;
                }
              );
              /*等于false不走提交*/
              if (arr) {
                insertProcedureInspect(JSON.stringify(this.tableData)).then(res => {
                  if (res.code == this.returnState) {
                    this.$message.success("提交成功");
                    this.showInputCaseNum = false
                    this.queryParams.caseNum = '';
                    this.queryInspectList();
                    /*查询未核查数量*/
                    this.theNumberOfUncheckedFilesInTheQueryFile(this.receptionProcedureId);
                  }
                });
              } else {
                this.$message.error(whetherToVerify.msg);
              }

            } else {
              this.$message.error("没有档案需要提交");
            }
          }


        }
      },
      /******************************************************核查项处理 --结束*******************************************************/


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
          // fileNumberId: this.caseData[0].filesId,//档案名称
          recordProcedureFilesId: this.$route.query.recordProcedureFilesId
        };
        doubtQueryVerificationResultData(dtoArr).then(res => {
            this.tableData = res.data;
            if (this.tableData) {
              this.tableData.forEach(item => {
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
              this.$router.go(-1);
            } else {
              this.$message.success("保存成功");
            }
          }
        });
      },
      /************************************************** 存疑方法 结束*****************************/

      /**
       * 领取档案
       */
      handleReceivedPhy() {
        const arr = {
          procedureId: this.receptionProcedureId,
          projectId: this.receptionProjectId,
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
          }
        });
      },
      /**
       * 返回
       */
      handleBack() {
        this.$router.go(-1);
        if (this.isImpeach) {
          this.tableData.map(item => {
              item.rollId = this.queryParams.caseNum;
              item.isInspectAccomplish = 1
              item.filesId = this.$route.query.filesId;
              item.recordProcedureFilesId = this.$route.query.recordProcedureFilesId;
              item.buttonName = "返回"
              item.projectId = this.receptionProjectId
              item.checkStatus = this.isImpeach
            }
          );
          /*提交存疑结果*/
          this.submissionOfVerificationResultsInDoubt(this.tableData, 1);
        } else {
          if (this.showInputCaseNum) {
            /*保存核查数据*/
            this.tableData.map(item => {
                item.rollId = this.queryParams.caseNum;
                item.isInspectAccomplish = 1
                item.filesId = this.queryParams.filesId
                item.recordProcedureFilesId = this.queryParams.recordProcedureFilesId
                item.projectId = this.receptionProjectId
                item.buttonName = "返回"
                item.checkStatus = this.isImpeach
              }
            );
            insertProcedureInspect(JSON.stringify(this.tableData)).then(res => {
              if (res.code == this.returnState) {
                this.$message.success("保存成功");
              }
            });
          }
        }

      },

    }
  }
</script>

<style scoped lang="scss">
  .checkTable {
    height: calc(100% - 130px) !important;
    margin-top: 20px;

  }
</style>
