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
          <!--          <el-form-item label="核查状态" prop="caseNum">-->
          <!--            <el-input-->
          <!--              v-model="queryParams.filesName"-->
          <!--              placeholder="请输入案卷档号"-->
          <!--              clearable/>-->
          <!--          </el-form-item>-->
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
              prop="procedureId"
              label="工序名称"
              min-width="120"
              :show-overflow-tooltip='true'
              :formatter=procedureIdFormatter
            >
            </el-table-column>
              <el-table-column
                prop="isProcedureInspect"
                label="核查状态"
                min-width="120"
                :show-overflow-tooltip='true'
                :formatter="isProcedureInspectFormatter"
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
          </template>
        </TablePage>
        <!--        <TablePage class="tablePage"-->
        <!--                   :hasSelection="false"-->
        <!--                   :isPage="false"-->
        <!--                   :is-title="true"-->
        <!--                   :tableTitle="fileTitle"-->
        <!--                   :tableData="fileData">-->
        <!--          <template slot="table">-->
        <!--            <el-table-column-->
        <!--              show-overflow-tooltip-->
        <!--              v-for="(item,index) in fileColumn"-->
        <!--              :key="index"-->
        <!--              :prop="item.prop"-->
        <!--              :label="item.label"-->
        <!--              :min-width="item.width"-->
        <!--            :formatter="item.formatter">-->
        <!--            </el-table-column>-->
        <!--            <el-table-column-->
        <!--              label="操作"  fixed="right"-->
        <!--              width="100" class-name="operation">-->
        <!--              <template slot-scope="scope">-->
        <!--                <el-button type="text" size="mini">-->
        <!--                  查看电子文件-->
        <!--                </el-button>-->
        <!--              </template>-->
        <!--            </el-table-column>-->
        <!--          </template>-->
        <!--        </TablePage>-->

        <el-dialog title="查看核查项详情"
                   :visible.sync="dialogExamine"
                   :close-on-click-modal="false"
                   :close-on-press-escape="false"
                   :destroy-on-close="true"
                   width="80%" class="dialog-style">
          <CheckTable :isTitle="true" :tableData="examineData" :tableColumn="examineColumn"
                      :isShowChangeContent="false" style="margin-top: 20px"/>
        </el-dialog>
      </div>
    </MainFlexibleTree>
  </div>
</template>

<script>
  import MainFlexibleTree from "@/components/Public/MainBody/MainFlexibleTree.vue";
  import TablePage from "@/components/Public/table/TablePage.vue";
  import {formatDate} from "@/utils";
  import {getOne, queryThePersonalManagementProjectTree} from "@/api/projectManage/projectInitialize"
  import {query} from "@/api/projectManage/projectProcedure";
  import CheckTable from "@/views/fileCheck/checkTable";
  import {selectListAndMetadata} from "@/api/business/archiveType";
  import {accessPersonalVerificationData, queryPersonalDoubtfulData} from "@/api/fileData/fileData";

  export default {
    name: "myResolve",
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
        },
        caseTitle: '案卷信息',
        fileTitle: '文件信息',
        caseData: [],
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
        ],
        /*项目Id*/
        projectId: '',
        procedureId: '',
        /*档案类型Id*/
        archiveTypeId: '',
        selectListAndMetadataTable: [],
      }
    },
    created() {

      this.queryTree();
      this.queryProjectProcedure();

    },
    methods: {
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
      /*查询工序*/
      queryProjectProcedure() {
        query().then(res => {
          this.projectProcedureIdTable = res;
        });
      },
      /*查询工序表格转换*/
      procedureIdFormatter(row, column, cellValue) {
        console.log(row, column, cellValue)
        let result = '';
        this.projectProcedureIdTable.map((item) => {
          if (item.id == cellValue) {
            result = item.procedureName;
          }
        });
        return result;
      },
      queryTree() {
        queryThePersonalManagementProjectTree().then(res => {
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
        }
      },
      /*点击tree事件*/
      handleNodeClick(val, node, component) {
        this.queryParams.pageSize = 20
        this.queryParams.pageNum = 1
        console.log(val, node, component)
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
      /*条件查询*/
      conditionalQueries() {
        this.queryParams.pageSize = 20;
        this.queryParams.pageNum = 1;
        this.handleQuery();
      },
      /*查询档案结果数据*/
      handleQuery() {
        const arr = {
          projectId: this.projectId,//批次名称
          archiveLevelName: this.selectListAndMetadataTable[0].tableLevel1En, //案卷级的表名称
          procedureId: this.procedureId,
          filesName: this.queryParams.filesName,
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize
        };
        queryPersonalDoubtfulData(arr).then(res => {
          this.caseData = res.data;
          this.total = res.total;
        });
      },

      /*获取档案类型Id*/
      handleProcess(projectId) {
        getOne(projectId).then(res => {
          this.archiveTypeId = res.data.archiveTypeId;
          this.selectListAndMetadata();
        });
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

  //.tablePage:last-of-type{
  //  margin-top: 0;
  //}
</style>

