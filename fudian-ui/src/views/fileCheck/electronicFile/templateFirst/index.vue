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
                  @handlePool="handlePool"
                  @enterQuery="conditionalQueries"
                  @handleQuery="conditionalQueries"
                  @handleBack="handleBack"/>
        <TablePage class="tablePage"
                   :hasSelection="false"
                   :is-title="true"
                   :tableTitle="caseTitle"
                   :tableData="notClaimedData"
                   :pageSize="caseSize"
                   :pageNum="caseNum"
                   :total="caseTotal"
                   @handleChange="notClaimedHandleCheckItem">
          <template slot="table">
            <el-table-column

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
  import {selectListAndMetadata, theFileNumberFieldIsThatDatabaseField} from "@/api/business/archiveType";
  import {getOneProjectprocedure} from "@/api/projectManage/projectProcedure";
  import {updateFileData} from "@/api/fileData/fileData";
  import {
    collectYourFileByIds,
    doubtQueryVerificationResultData,
    insertProcedureInspect, listData,
    queryBasedOnTheIDOfTheVerificationResult,
    queryElectronicArchivesDossierLevel, queryElectronicArchivesDossierLevelTemplateTwo,
    queryProcedureInspectElectronicArchives,
    submissionOfVerificationResultsInDoubt,
    theNumberOfUncheckedFilesInTheQueryFile
  } from "@/api/record/record"
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
          attrOrder: '',
        },
        caseTitle: '案卷信息：',
        caseData: [],
        caseColumn: [],
        caseTotal: 0,
        caseSize: 20,
        caseNum: 1,
        checkRange: '电子档案',
        processName: '工序名称',
        processDes: '工序描述',
        checkItemData: [],
        returnState: 20000,
        /*工序id*/
        receptionProcedureId: '',
        /*工序名称*/
        receptionProcedureName: '',
        /*工序描述*/
        receptionProcedureDesc: '',
        /*项目id*/
        receptionProjectId: '',
        /*工序对象*/
        receptionProcedure: {
          projectId: '',
          procedureName: '',
          procedureId: '',
          procedureDesc: '',
        },
        /*查询表头的所有数据*/
        selectListAndMetadataTable: [],
        /*未领取的table数据*/
        notClaimedData: [],
        /*项目关联档案类型id*/
        receptionArchiveTypeId: '',
        showInputCaseNum: false,
        // /*核查结果表头*/
        // tableColumn: [
        //   {label: '核查项名称', prop: 'trueingName', width: '100'},
        //   {label: '核查项描述', prop: 'trueingDesc', width: '100'},
        //   {label: '核查标准', prop: 'examineStasString', width: '100', cellName: 'cellName'},
        // ],
        /*是否是存疑页面跳转过来的*/
        isImpeach: false,
        tabDisabled: false,
        notVerifiedCount: 0
      }
    },
    created() {
      this.receptionArchiveTypeId = this.$route.query.receptionArchiveTypeId;
      if (this.$route.query.isImpeach == 'true') {
        this.isImpeach = true;
        this.tabDisabled = true;
      } else {
        this.isImpeach = false;
      }
      this.theFileNumberFieldIsThatDatabaseField();
      this.getOne();
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
          console.log("未核查", res)
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
            // item.label = item.metadataName;
            item.label = item.metadataName;
            item.prop = 'attr' + item.attrOrder;
            item.width = item.metadataWidth;
            item.disabled = item.disabled;
          });
          this.caseColumn = this.selectListAndMetadataTable[0].dahcMetadataList;
          // this.handleQuery("2");
          if (this.isImpeach) {
            /*存疑页面查询*/
            this.queryParams.caseNum = this.$route.query.caseNum;
            this.queryBasedOnTheIDOfTheVerificationResult();
          } else {
            this.handleQuery('2');
          }

        });
        // });
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
              arrRes.push(res.data[0])
            }
            /*已领取*/
            this.caseData = arrRes;
            this.queryVerificationItems();
          } else {
            /*未领取*/
            this.notClaimedData = res.data;
            this.caseTotal = res.total;
          }
        });
      },
      /*搜索表数据*/
      conditionalQueries() {
        console.log("查询")
        this.caseNum = 1;
        this.caseSize = 20;
        this.handleQuery("0");
      },
      queryVerificationItems() {
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
      /*回显表数据*/
      handleEcho(val) {
        const arr = {
          procedureId: this.receptionProcedureId,//工序id
          archiveLevelName: this.selectListAndMetadataTable[0].tableLevel1En, //案卷级的表名称
          isProcedureInspect: val, //未处理数据
          attrOrder: 'attr' + this.queryParams.attrOrder,
          // caseNum: this.queryParams.caseNum,
        };
        queryElectronicArchivesDossierLevel(arr).then(res => {
          if (val == '2') {
            const arrRes = [];
            if (res.data.length > 0) {
              arrRes.push(res.data[0])
            }
            /*已领取*/
            this.caseData = arrRes;

          }
        });
      },
      /*查询是否有为完成的核查项数据*/
      dataOnCompletedVerificationItems() {
        const dtoArr = {
          procedureId: this.receptionProcedureId, //工序id
        };
        if (this.caseData.length > 0) {
          dtoArr.fileNumberId = this.caseData[0].filesId//档案名称
        }
        /*查询核查结果*/
        queryProcedureInspectElectronicArchives(dtoArr).then(res => {
          res.data.map(item => {
            if (res.data[0].filesId != '' & res.data[0].filesId != null) {
              this.showInputCaseNum = true;
            }
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

          /*等于true证明，有未完成的核查结果数据，回显表格*/
          if (this.showInputCaseNum) {
            console.log("——————————————————————————————————————————————回显数据————————————————————————————————")
            this.queryParams.caseNum = this.checkItemData[0].rollId;
            /*先查询是否存在核查一半的数据，如果有，回显档案数据。如果没有，先查询档案数据再查询核查结果*/
            this.handleEcho("2");
          } else {
            console.log("——————————————————————————————————————————————没有回显数据————————————————————————————————")
            /*等于false证明，不存在未完成的核查结果数据， 查询档案数据*/
            this.handleQuery("2");
          }
        });
      },
      /*只查询核查结果*/
      queryDataOnCompletedVerificationItems() {
        const dtoArr = {
          procedureId: this.receptionProcedureId, //工序id
        };
        if (this.caseData.length > 0) {
          dtoArr.fileNumberId = this.caseData[0].filesId//档案名称
        }
        /*查询核查结果*/
        queryProcedureInspectElectronicArchives(dtoArr).then(res => {
          res.data.map(item => {
            if (item.pageNumS.length > 0 || item.caseNumS.length > 0) {
              this.showInputCaseNum = true;
            }
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
      /*未领取分页*/
      notClaimedHandleCheckItem(pageSize, pageNum) {
        this.caseSize = pageSize;
        this.caseNum = pageNum;
        this.handleQuery('0');
      },
      /**
       * 分页
       */
      handleCheckItem(pageSize, pageNum) {
        this.processSize = pageSize;
        this.processNum = pageNum;
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
              this.$message.warning("存在存疑数据");
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
                  this.dataOnCompletedVerificationItems()
                  /*查询未核查数量*/
                  this.theNumberOfUncheckedFilesInTheQueryFile(this.receptionProcedureId);
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
          // if (this.showInputCaseNum) {
          /*保存核查数据*/
          if (this.caseData.length > 0) {
            insertProcedureInspect(JSON.stringify(this.checkItemData)).then(res => {
              if (res.code == this.returnState) {
                this.$message.success("保存成功");
              }
            });
            // }
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
        row.id = row.filesId;
        row.archiveTypeId = this.receptionArchiveTypeId
        row.level = 1
        row.procedureId = this.receptionProcedureId
        row.projectId = this.receptionProcedure.projectId
        row.buttonName = "保存案卷档案"
        row.checkStatus = this.isImpeach
        row.caseFileNumber = this.caseData[0].filesName; /*档案名称*/
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
            } else {
              this.$message.success("保存成功");
            }
          }
        });
      },
      /************************************************** 存疑方法 结束*****************************/

    },
  }
</script>

<style scoped lang="scss">
  ::v-deep .el-tabs__content {
    height: 100%;
    padding-left: 0;

    .el-tab-pane:first-of-type {
      .tablePage {
        height: 120px;
        margin-top: 20px;
      }

      .checkTable {
        height: calc(100% - 275px) !important;
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
//::v-deep .el-table__cell{
//  padding: 0 !important;
//}
</style>
