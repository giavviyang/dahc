<template>
  <div class="app-container">
    <el-tabs :tab-position="tabPosition" v-model="queryParams.activeName" @tab-click="handleTabClick">
      <el-tab-pane label="已领取" name="alreadyReceived" :disabled="tabDisabled">
        <CheckTop :queryParams="queryParams"
                  :checkRange="checkRange"
                  :processName="receptionProcedure.procedureName"
                  :processDes="receptionProcedure.procedureDesc"
                  :showInputCaseNum="isImpeach"
                  :impeach="isImpeach"
                  :notVerifiedCountShow="!isImpeach"
                  :notVerifiedCount="notVerifiedCount"
                  @enterQuery="handleQuery('2')"
                  @handleQuery="handleQuery('2')"
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
              :min-width="item.width"/>
          </template>
        </TablePage>
        <TablePage class="tablePage2"
                   :hasSelection="false"
                   :isPage="false"
                   :is-title="true"
                   :tableTitle="fileTitle"
                   :tableData="fileData">
          <template slot="table">
            <el-table-column
              v-for="(item,index) in fileColumn"
              :key="index"
              :prop="item.prop"
              :label="item.label"
              :min-width="item.width">
              <template slot-scope="scope">
                <el-input v-model="scope.row[item.prop]" size="mini"
                          v-if="scope.row.isUpdate &&(item.prop!=='caseNum')"></el-input>
                <div v-else>{{ scope.row[item.prop] }}</div>
              </template>
            </el-table-column>
            <el-table-column
              label="操作"  fixed="right"

              width="120" class-name="operation">
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
        <CheckTable :isTitle="true" :isImpeach="isImpeach" :tableData="checkItemData"
                    :isShowChangeContent="false"/>
        <div class="subBtn">
          <el-button type="primary" size="mini" @click="handleSubmit">提交</el-button>
        </div>
      </el-tab-pane>
      <el-tab-pane label="未领取" name="noReceived" :disabled="tabDisabled">
        <CheckTop :queryParams="queryParams"
                  :checkRange="checkRange"
                  :notVerifiedCount="notVerifiedCount"
                  :processName="receptionProcedure.procedureName"
                  :processDes="receptionProcedure.procedureDesc"
                  @handleQuery="conditionalQueries"
                  @enterQuery="conditionalQueries"
                  @handlePool="handlePool"
                  @handleBack="handleBack"/>
        <TablePage class="tablePage"
                   :hasSelection="false"
                   :is-title="true"
                   :tableTitle="caseTitle"
                   :tableData="notClaimedData"
                   :pageSize="caseSize"
                   :pageNum="caseNum"
                   :total="caseTotal"
                   @handleChange="handleCheckItemNoReceived">
          <template slot="table">
            <el-table-column
              show-overflow-tooltip
              v-for="(item,index) in caseColumn"
              :key="index"
              :prop="item.prop"
              :label="item.label"
              :min-width="item.width"/>
          </template>
        </TablePage>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import CheckTop from "@/views/fileCheck/checkTop.vue";
  import TablePage from "@/components/Public/table/TablePage.vue";
  import CheckTable from "@/views/fileCheck/checkTable.vue";
  import {
    collectYourFileByIds,
    queryElectronicArchivesDossierLevel,
    queryElectronicArchivesDossierLevelTemplateTwo,
    listData,
    queryProcedureInspectElectronicArchives,
    queryBasedOnTheIDOfTheVerificationResult,
    doubtQueryVerificationResultData,
    submissionOfVerificationResultsInDoubt,
    queryElectronicProfileTemplateIIArchives,
    insertProcedureInspect,
    queryCaseLevelArchivesDossierLevelTemplateTwo, theNumberOfUncheckedFilesInTheQueryFile
  } from "@/api/record/record";
  import {getOneProjectprocedure} from "@/api/projectManage/projectProcedure";
  import {
    obtainCaseDataAttr,
    selectListAndMetadata,
    theFileNumberFieldIsThatDatabaseField
  } from "@/api/business/archiveType";
  import {updateFileData} from "@/api/fileData/fileData";
  import {getData} from "@/api/dahc/sys/dictData";

  export default {
    name: "index",
    components: {CheckTable, TablePage, CheckTop},
    data() {
      return {
        tabPosition: 'left',
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
        fileColumn: [],
        checkRange: '电子档案',
        processName: '工序名称',
        processDes: '工序描述',
        checkItemData: [],
        // /*核查结果表头*/
        // tableColumn: [
        //   {label: '核查项名称', prop: 'trueingName', width: '100'},
        //   {label: '核查项描述', prop: 'trueingDesc', width: '100'},
        //   {label: '核查标准', prop: 'examineStasString', width: '100', cellName: 'cellName'},
        // ],
        /*工序对象*/
        receptionProcedure: {
          projectId: '',
          procedureName: '',
          procedureId: '',
          procedureDesc: '',
          caseNum: '',
        },
        returnState: 20000,
        /*查询表头的所有数据*/
        selectListAndMetadataTable: [],
        /*项目关联档案类型id*/
        receptionArchiveTypeId: '',
        /*未领取表格*/
        notClaimedData: [],
        receptionProcedureId: '',
        /*是否是存疑页面跳转过来的*/
        isImpeach: false,
        tabDisabled: false,
        notVerifiedCount: 0,
        /*文件档号的attr*/
        attrOfTheCaseFileNumber: '',

      }
    },
    created() {
      this.receptionArchiveTypeId = this.$route.query.receptionArchiveTypeId;


      // this.receptionProcedure.caseNum = this.$route.query.caseNum;
      if (this.$route.query.isImpeach == 'true') {
        this.isImpeach = true;
        this.tabDisabled = true;
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
          isProcedureInspect: '2'
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
      /*根据档案类型id查询 档号 在数据库的那个字段*/
      theFileNumberFieldIsThatDatabaseField() {
        theFileNumberFieldIsThatDatabaseField(this.receptionArchiveTypeId).then(res => {
          this.queryParams.attrOrder = res.data.attrOrder;
        });
        const arr = {
          metadataName: '档号',
          archiveTypeId: this.receptionArchiveTypeId
        };
        obtainCaseDataAttr(arr).then(res => {
          this.attrOfTheCaseFileNumber = "attr" + res.data;
          // this.getPucter();
        });

      },
      /*查询表头*/
      selectListAndMetadata() {
        // getOne(this.receptionProcedure.projectId).then(res => {
        const arr = {
          // id: res.data.archiveTypeId,
          id: this.receptionArchiveTypeId,
          level: 1
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
          // if (this.isImpeach == 'true') {
          if (this.isImpeach) {
            /*存疑页面查询*/
            this.queryParams.caseNum = this.$route.query.caseNum;
            this.queryBasedOnTheIDOfTheVerificationResult();
          } else {
            /*正常核查页面查询*/
            this.handleQuery('2');
          }

        });
        this.selectListAndMetadataCase();

        // });
      },

      selectListAndMetadataCase() {
        const arr2 = {
          id: this.receptionArchiveTypeId,
          level: 2 //案卷表
        };
        /*文件表头*/
        selectListAndMetadata(arr2).then(res => {
          res.data[0].dahcMetadataList.map(item => {
            item.label = item.metadataName;
            item.prop = 'attr' + item.attrOrder;
            item.width = item.metadataWidth;
            item.disabled = item.disabled;
          });
          this.fileColumn = res.data[0].dahcMetadataList;
        });
      },
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
          if (this.caseData.length > 0) {
            this.queryCaseLevelArchivesDossierLevelTemplateTwo();
          }
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

      /*搜索表数据*/
      conditionalQueries() {
        this.caseNum = 1;
        this.caseSize = 20;
        this.handleQuery("0");
      },

      /*查询表数据*/
      handleQuery(val) {
        const arr = {
          procedureId: this.receptionProcedureId,//工序id
          archiveLevelName: this.selectListAndMetadataTable[0].tableLevel1En, //案卷级的表名称
          isProcedureInspect: val, //未处理数据
          attrOrder: 'attr' + this.queryParams.attrOrder,
          caseNum: this.queryParams.caseNum,
          pageNum: this.caseNum,
          pageSize: this.caseSize,
        };
        queryElectronicArchivesDossierLevelTemplateTwo(arr).then(res => {
          if (val == '2') {
            const arrRes = [];
            if (res.data.length > 0) {
              arrRes.push(res.data[0]);
            } else {
              this.fileData = [];
            }
            /*已领取*/
            this.caseData = arrRes;
            if (this.caseData.length > 0) {
              this.queryCaseLevelArchivesDossierLevelTemplateTwo();
              this.queryDataOnCompletedVerificationItems();
            } else {
              this.checkItemData = [];
            }

          } else {
            /*未领取*/
            this.notClaimedData = res.data;
            this.caseTotal = res.total;
          }
        });
      },
      /*只查询核查结果*/
      queryDataOnCompletedVerificationItems() {
        const dtoArr = {
          procedureId: this.receptionProcedureId, //工序id
        };
        if (this.caseData.length > 0) {
          dtoArr.filesId = this.caseData[0].filesId//档案名称
        }
        /*查询核查结果*/
        listData(dtoArr).then(res => {
          res.data.map(item => {
            if (item.pageNumS.length == 0) {
              item.pageNumS = [{
                value: '',
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
       * 单击tab页
       * @param tab
       * @param event
       */
      handleTabClick(tab, event) {
        this.theNumberOfUncheckedFilesInTheQueryFile(this.receptionProcedureId);
        this.queryParams.caseNum = '';
        if (tab.label == '未领取') {
          this.handleQuery("0");
        } else {
          this.handleQuery("2");
        }
      },


      /*查询文件数据*/
      queryCaseLevelArchivesDossierLevelTemplateTwo() {
        const arr = {
          filesId: this.caseData[0].filesId,
          archiveId: this.receptionArchiveTypeId,
        };
        queryCaseLevelArchivesDossierLevelTemplateTwo(arr).then(res => {
          this.fileData = res.data
        });

      },

      handleCheckItemNoReceived(pageSize, pageNum) {
        this.caseSize = pageSize;
        this.caseNum = pageNum;
        this.handleQuery('0');
      },
      handleCheckItem(pageSize, pageNum) {
        this.processSize = pageSize;
        this.processNum = pageNum;
      },


      /*领取档案*/
      handlePool(val) {
        this.$confirm('当前领取' + val + '条案卷数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          if (this.notClaimedData.length > 0) {
            /*档案id集合*/
            const arr = [];
            const arrFileName = [];
            for (let i = 0; i < new Number(val); i++) {
              if (this.notClaimedData[i] == undefined) {
                break;
              } else {
                arr.push(this.notClaimedData[i].id)
                arrFileName.push(this.notClaimedData[i].filesName);
              }
            }
            const obj = {
              filesName: JSON.stringify(arrFileName),
              fileIds: arr,
              procedureId: this.receptionProcedureId,
              projectId: this.receptionProcedure.projectId,
              buttonName: "领取档案",
              checkStatus: this.isImpeach
            };
            /*领取*/
            collectYourFileByIds(obj).then(res => {
              if (res.code == this.returnState) {
                this.$message.success("领取成功");
                this.handleQuery("0");
              }
            });
          } else {
            this.$message({showClose: true, message: '当前工序不存在未领取的电子档案数据', type: 'warning'});
          }
        });
      },


      handleSubmit() {
        /*是否核查*/
        let whetherToVerify = {};
        if (this.caseData.length > 0) {
          let isQuestion = false;
          var arr = this.checkItemData.every(item => {
              item.rollId = this.caseData[0].filesName; /*档案名称*/
              item.isInspectAccomplish = 0 /*核查结果状态 1-未完成 0-完成*/
              item.filesId = this.caseData[0].filesId /*档案主键id*/
              item.recordProcedureFilesId = this.caseData[0].id /*当前核查结果记录的主键id*/
              item.projectId = this.receptionProcedure.projectId
              item.buttonName = "提交"
              item.checkStatus = this.isImpeach
              if (item.processMode[0] == 'impeach') {
                isQuestion = true;
              }
              whetherToVerify = this.whetherItIsEmpty(item);
              return whetherToVerify.type;
            }
          );
          if (this.isImpeach) {
            /*提交存疑结果*/
            if (isQuestion) {
              this.$message.warning("存在存疑数据")
              isQuestion = false;
            } else {
              this.submissionOfVerificationResultsInDoubt(this.checkItemData, 0);
              this.$router.go(-1);
            }

          } else {
            /*等于false不走提交*/
            if (arr) {
              /*保存核查数据*/
              insertProcedureInspect(JSON.stringify(this.checkItemData)).then(res => {
                if (res.code == this.returnState) {
                  this.$message.success("提交成功");
                  this.theNumberOfUncheckedFilesInTheQueryFile(this.receptionProcedureId);
                  this.handleQuery("2");
                }
              });
            } else {
              this.$message.error(whetherToVerify.msg);
            }
          }
        } else {
          this.$message.error("没有档案需要提交");
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
        }
        const arr = {
          type: b,
          msg: msg
        }
        return arr;
      },
      /*存疑提交核查结果*/
      submissionOfVerificationResultsInDoubt(row, i) {
        submissionOfVerificationResultsInDoubt(row).then(res => {
          if (res.code == this.returnState) {
            if (i == 0) {
              this.$message.success("提交成功");
            } else {
              this.$message.success("保存成功");
            }
          }
        });
      },
      /**
       * 返回
       */
      handleBack() {
        this.$router.go(-1);
        this.checkItemData.map(item => {
            item.rollId = this.caseData[0].filesName; /*档案名称*/
            item.isInspectAccomplish = 1 /*核查结果状态 1-未完成 0-完成*/
            item.filesId = this.caseData[0].filesId /*档案主键id*/
            item.recordProcedureFilesId = this.caseData[0].id /*当前核查结果记录的主键id*/
            item.projectId = this.receptionProcedure.projectId
            item.buttonName = "返回"
            item.checkStatus = this.isImpeach
          }
        );
        if (this.isImpeach) {
          /*提交存疑结果*/
          this.submissionOfVerificationResultsInDoubt(this.checkItemData, 1);
        } else {
          /*保存核查数据*/
          if (this.caseData.length > 0) {
            insertProcedureInspect(JSON.stringify(this.checkItemData)).then(res => {
              if (res.code == this.returnState) {
                this.$message.success("保存成功");
              }
            });
          }
        }

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
        row.id = row.attrId
        row.archiveTypeId = this.receptionArchiveTypeId
        row.level = 2
        row.procedureId = this.receptionProcedureId
        row.fileId = row.attrId
        row.buttonName = "保存文件档案"
        row.pid = this.caseData[0].filesId
        row.projectId = this.receptionProcedure.projectId
        row.caseFileNumber = this.caseData[0]["attr" + this.queryParams.attrOrder]
        row.caseNumber = row[this.attrOfTheCaseFileNumber];
        row.checkStatus = this.isImpeach;
        updateFileData(row).then(res => {
          if (res.code == this.returnState) {
            this.$message.success("修改成功");
          }
        });
      },


    },
  }
</script>

<style scoped lang="scss">
  ::v-deep .el-tabs__content {
    height: 100%;
    padding-left: 0;

    .el-tab-pane:first-of-type {
      .tablePage1 {
        height: 100px;
        margin-top: 20px;
      }

      .tablePage2 {
        height: 40%;
        margin-top: 25px;
      }

      .checkTable {
        height: calc(60% - 280px) !important;
        margin-top: 25px;
      }
    }

    .el-tab-pane:last-of-type {
      .tablePage {
        height: calc(100% - 95px) !important;
        margin-top: 20px;
      }
    }
  }

  ::v-deep .is-active {
    color: #1890ff;
    cursor: default;
  }
</style>
