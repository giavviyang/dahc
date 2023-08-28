<template>
  <div class="app-container">
    <MainFlexibleTree :data="treeData" :defaultProps="treeDefaultProps" @handleNodeClick="handleNodeClick">
      <div class="app-container" slot="mainRight">
        <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="iptAndBtn"
                 @submit.native.prevent>
          <el-form-item label="案卷档号" prop="caseNum">
            <el-input
              v-model="queryParams.filesName"
              placeholder="请输入案卷档号"
              @keyup.enter.native="conditionalQueries"
              clearable/>
          </el-form-item>
          <el-form-item label="核查状态" prop="isProcedureInspect">
            <el-select v-model="queryParams.isProcedureInspect" placeholder="请选择元数据类型" maxlength="30" clearable
                       @change="conditionalQueries">
              <el-option v-for="metaDataItem in isProcedureInspectOptions"
                         :key="metaDataItem.value"
                         :label="metaDataItem.label"
                         :value="metaDataItem.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="conditionalQueries">搜索</el-button>
          </el-form-item>
        </el-form>
        <TablePage class="tablePage"
                   :hasSelection="false"
                   :is-title="false"
                   :tableTitle="caseTitle"
                   :tableData="caseData"
                   :pageSize="queryParams.pageSize"
                   :pageNum="queryParams.pageNum"
                   :total="total"
                   @handleChange="handleChange">
          <template slot="table">
            <el-table-column
              :show-overflow-tooltip='true'
              prop="procedureId"
              label="工序名称"
              min-width="120"
              :formatter=procedureIdFormatter
            >
            </el-table-column>
            <el-table-column
              :show-overflow-tooltip='true'
              prop="isProcedureInspect"
              label="核查状态"
              width="100"
              :formatter=isProcedureInspectFormatter
            >
            </el-table-column>
            <el-table-column
              :show-overflow-tooltip='true'
              prop="inspectId"
              label="核查人"
              width="100"
              :formatter=inspectIdFormatter
            >
            </el-table-column>
            <el-table-column
              :show-overflow-tooltip='true'
              prop="impeachSolveId"
              label="解决存疑人"
              width="100"
              :formatter=inspectIdFormatter
            >
            </el-table-column>
            <el-table-column
              :show-overflow-tooltip='true'
              show-overflow-tooltip
              v-for="(item,index) in caseColumn"
              :key="index"
              :prop="item.prop"
              :label="item.label"
              :min-width="item.width"
              :formatter="item.formatter">
            </el-table-column>
            <el-table-column
              label="操作"  fixed="right"

              width="200" class-name="operation">
              <template slot-scope="scope">
                <el-button type="text" size="mini" @click="handleExamineDetail(scope.row)"
                           :disabled="!buttonShow(scope.row)">
                  查看核查结果
                </el-button>
                <!--                <el-button type="text" size="mini" @click="viewFileInformation(scope.row)">-->
                <!--                  查看文件信息-->
                <!--                </el-button>-->
              </template>

            </el-table-column>
          </template>
        </TablePage>

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
                     @handleChange="handleChange">
            <template slot="table">
              <el-table-column
                show-overflow-tooltip
                v-for="(item,index) in examineColumn"
                :key="index"
                :prop="item.prop"
                :label="item.label"
                :min-width="item.width"
                :formatter="item.formatter">
              </el-table-column>
            </template>
          </TablePage>
        </el-dialog>
      </div>
    </MainFlexibleTree>
  </div>
</template>

<script>
  import MainFlexibleTree from "@/components/Public/MainBody/MainFlexibleTree.vue";
  import TablePage from "@/components/Public/table/TablePage.vue";
  import {formatDate} from "@/utils";
  import {
    getOne,
    queryThePersonalManagementProjectTree,queryThePersonalTaskTree
  } from "@/api/projectManage/projectInitialize"
  import {accessPersonalVerificationData} from "@/api/fileData/fileData";
  import CheckTable from "@/views/fileCheck/checkTable";
  import {selectListAndMetadata} from "@/api/business/archiveType";
  import {query} from "@/api/projectManage/projectProcedure";
  import {doubtQueryVerificationResultData, listData} from "@/api/record/record";
  import {queryUser} from "@/api/system/user";

  export default {
    name: "myTask",
    components: {TablePage, MainFlexibleTree, CheckTable},

    data() {
      return {
        treeData: [],
        treeDefaultProps: {
          children: 'children',
          label: 'label'
        },
        queryParams: {
          pageSize: 20,
          inspectId: 0,
          pageNum: 1,
          filesName: '',
          isProcedureInspect: '1',
        },
        checkQueryParams: {
          pageSize: 20,
          pageNum: 1,
        },
        caseTitle: '案卷信息',
        checkTitle: '核查项信息',
        fileTitle: '文件信息',
        caseData: [],
        checkData: [],
        caseColumn: [],
        total: 0,
        fileData: [],
        /*项目id集合*/
        projectIdTable: [],
        projectProcedureIdTable: [],
        userIdTable: [],
        fileColumn: [
          {label: '文件序号', prop: 'date', width: '100'},
          {label: '元数据名称', prop: 'name', width: '100'},
          {label: '元数据中文名称', prop: 'address', width: '100'},
          {label: '档案类型', prop: 'remarks', width: '100'},
          {label: '创建人', prop: 'remarks', width: '100'},
          {label: '创建时间', prop: 'remarks', width: '100', formatter: formatDate},
          {label: '备注', prop: 'remarks', width: '100'},
        ],
        examineData: [],
        dialogExamine: false,
        examineColumn: [
          {label: '核查项名称', prop: 'trueingName', width: '100'},
          {label: '核查项描述', prop: 'trueingDesc', width: '100'},
          {label: '核查标准', prop: 'examineStasString', width: '100', cellName: 'cellName'},
          {label: '核查结果', prop: 'checkResultState', width: '100', formatter: this.checkResultStateFormatter},
          {label: '不合格件/页号', prop: 'nonConforming', width: '100'},
          {label: '处理方式', prop: 'isQuestion', width: '100', formatter: this.isQuestionFormatter},
          {label: '更改内容', prop: 'isMasterCopy', width: '100', formatter: this.isMasterCopyFormatter}
        ],
        /*项目Id*/
        projectId: '',
        procedureId: '',
        /*档案类型Id*/
        archiveTypeId: '',
        selectListAndMetadataTable: [],
        isProcedureInspectOptions: [
          {value: '0', label: '未领取'},
          {value: '1', label: '已完成'},
          {value: '2', label: '已领取'},
          {value: '3', label: '存疑中'},
        ],

      }
    },
    created() {

      this.queryTree();
      this.queryUser();
      this.queryProjectProcedure();
    },
    methods: {
      /**************************************************表格字段转换 **************************************/
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
      /*查询核查人表格转换*/
      inspectIdFormatter(row, column, cellValue) {
        let result = '';
        this.userIdTable.map((item) => {
          if (item.userId == cellValue) {
            result = item.nickName;
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

      /**************************************************表格字段转换 **************************************/
      buttonShow(val) {
        if (val.isProcedureInspect == '2' || val.isProcedureInspect == '0') {
          return false;
        } else {
          return true;
        }
      },
      queryTree() {
        queryThePersonalTaskTree().then(res => {
          this.projectId = res.data[0].id;
          this.handleProcess(res.data[0].id);
          this.treeData = res.data
          this.treeData = JSON.parse(JSON.stringify(this.treeData).replace(/id/g, "value"));
          this.treeData = JSON.parse(JSON.stringify(this.treeData).replace(/projectName/g, "label"));
          this.treeData = JSON.parse(JSON.stringify(this.treeData).replace(/procedureName/g, "label"));
        });
      },

      resetQuery() {
        this.queryParams = {
          inspectId: 0,
          filesName: '',
          procedureId: '',
          projectId: '',
          pageSize: 20,
          pageNum: 1,
          isProcedureInspect: '1',
        }
      },
      /*点击tree事件*/
      handleNodeClick(val, node, component) {
        this.queryParams.pageSize = 20
        this.queryParams.pageNum = 1
        this.projectId = ''
        this.procedureId = ''
        if (val.projectAndProcessState == 0) {
          /*项目*/
          this.projectId = val.value;

        } else {
          this.projectId = val.projectId;
          this.procedureId = val.value;
        }
        this.handleProcess(this.projectId);
      },
      /*获取档案类型Id*/
      handleProcess(projectId) {
        getOne(projectId).then(res => {
          this.archiveTypeId = res.data.archiveTypeId;
          this.selectListAndMetadata();
        });
      },
      /*条件查询*/
      conditionalQueries() {
        this.queryParams.pageNum = 1
        this.queryParams.pageSize = 20
        this.handleQuery();
      },

      /*查询表头*/
      selectListAndMetadata() {
        const arr = {
          id: this.archiveTypeId,
          level: 1
        };
        /*表头*/
        selectListAndMetadata(arr).then(res => {
          this.selectListAndMetadataTable = res.data
          res.data[0].dahcMetadataList.map(item => {
            item.label = item.metadataName;
            item.prop = 'attr' + item.attrOrder;
            item.width = item.metadataWidth;
          });
          this.caseColumn = res.data[0].dahcMetadataList;
          // this.pushHeader();
          this.handleQuery();
        });
      },
      /*push表头*/
      pushHeader() {
        this.caseColumn.push({label: "工序名称", prop: 'procedureId', width: 80, formatter: this.procedureIdFormatter})
        this.caseColumn.push({
          label: "核查状态",
          prop: 'isProcedureInspect',
          width: 80,
          formatter: this.isProcedureInspectFormatter
        })
      },

      /*查询个人核查数据*/
      handleQuery() {
        const arr = {
          projectId: this.projectId,//批次名称
          archiveLevelName: this.selectListAndMetadataTable[0].tableLevel1En, //案卷级的表名称
          procedureId: this.procedureId,
          filesName: this.queryParams.filesName,
          isProcedureInspect: this.queryParams.isProcedureInspect,
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize
        };
        accessPersonalVerificationData(arr).then(res => {
          this.caseData = res.data;
          this.total = res.total;
        });
      },

      handleExamineDetail(row) {
        const dtoArr = {
          filesId: row.filesId, //工序id
          procedureId: row.procedureId,//档案名称
          isProcedureInspect: '有值',//档案名称
        };
        this.doubtQueryVerificationResultData(dtoArr);

      },

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
                let str = item.examineStasString.replace(/\//g, '；');
                item.examineStasString = str.slice(0, item.examineStasString.length - 1)
              })
            }
            this.dialogExamine = true;
          }
        );
      },
      /*      /!*点击tree事件*!/
            handleNodeClick(val, node, component) {
              console.log(val, node,component)
              this.caseData = [];
              this.resetQuery();
              if (val.projectAndProcessState == 0) {
                this.queryParams.projectId = val.value;
                this.handleQuery(this.queryParams);
              } else {
                this.queryParams.procedureId = val.value;
                this.handleQuery(this.queryParams);
              }
            },

            /!*查询档案结果数据*!/
            handleQuery() {
              this.userIdTable.map(item => {
                if (item.userName == this.$store.state.user.name) {
                  this.queryParams.inspectId = item.userId
                }
              });
              conditionalQueries(this.queryParams).then(res => {
                this.caseData = res.data
                this.total = res.total
              });
            },

            /!**
             * 查看核查项详情
             *!/
            handleExamineDetail(row) {

              const dtoArr = {
                procedureId: row.procedureId, //工序id
                fileNumberId: row.filesId,//档案名称
                isImpeach: 1,//是否只查询存疑数据 1-不是存疑模块
                recordProcedureFilesId: row.id
              };
              this.doubtQueryVerificationResultData(dtoArr);

            },
            /!*查询核查结果*!/
            doubtQueryVerificationResultData(row) {
              doubtQueryVerificationResultData(row).then(res => {
                  this.examineData = res.data;
                  if (this.examineData) {
                    this.examineData.forEach(item => {
                      let str = item.examineStasString.replace(/\//g, '；');
                      item.examineStasString = str.slice(0, item.examineStasString.length - 1)
                    })
                  }
                  this.dialogExamine = true;
                }
              );
            },*/
      /*查看文件信息*/
      viewFileInformation(row) {
        console.log(row, "fdf")
        this.$router.push({
          path: '/fileMyTask', query: {
            projectId: row.projectId,
            filesId: row.filesId
          }
        });
      },
      /**
       * 分页器
       */
      handleChange(pageSize, pageNum) {
        this.queryParams.pageSize = pageSize;
        this.queryParams.pageNum = pageNum;
        this.handleQuery();
      },

    }
  }
</script>

<style lang="scss" scoped>
  .tablePage {
    //height: calc(50% - 30px);
    height: calc(100% - 55px) !important;
    margin-top: 5px;
  }
  .tablePage1 {
    //height: calc(100% - 0px);
    height: 400px !important;
  }
</style>

