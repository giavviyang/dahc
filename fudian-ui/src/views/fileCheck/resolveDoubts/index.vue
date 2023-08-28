<template>
  <div class="app-container">
    <MainFlexibleTree :data="treeData"  @handleNodeClick="handleNodeClick"
                      :defaultProps="treeDefaultProps">
      <div class="app-container" slot="mainRight">
        <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="iptAndBtn"
                 style="position: relative" @submit.native.prevent>
          <div style="white-space: nowrap;margin-right: 90px;">
            <el-form-item label="案卷档号" prop="caseNum">
              <el-input
                v-model="queryParams.filesName"
                placeholder="请输入案卷档号"
                clearable/>
            </el-form-item>
            <el-form-item style="min-width: auto">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="conditionalQueries">搜索</el-button>
            </el-form-item>
          </div>
          <div style="right:5px;position: absolute">
            <el-button type="primary" icon="el-icon-refresh-left" size="mini" @click="handleBack">返回</el-button>
          </div>
        </el-form>
        <TablePage class="tablePage"
                   :hasSelection="false"
                   :is-title="false"
                   :tableData="tableData"
                   :pageSize="queryParams.pageSize"
                   :pageNum="queryParams.pageNum"
                   :total="total"
                   @handleChange="handleChange">
          <template slot="table">
            <el-table-column
              show-overflow-tooltip
              v-for="(item,index) in tableColumn"
              :key="index"
              :prop="item.prop"
              :label="item.label"
              :min-width="item.width" :formatter="item.formatter">
            </el-table-column>
            <el-table-column
              label="操作"  fixed="right"
              width="100" class-name="operation">
              <template slot-scope="scope">
                <el-button type="text" size="mini" @click="handleResolve(scope.row)">
                解决存疑
                </el-button>
              </template>
            </el-table-column>
          </template>
        </TablePage>
      </div>
    </MainFlexibleTree>
  </div>
</template>

<script>
import MainFlexibleTree from "@/components/Public/MainBody/MainFlexibleTree.vue";
import TablePage from "@/components/Public/table/TablePage.vue";
import CheckTop from "@/views/fileCheck/checkTop.vue";
import {formatDate} from "@/utils";
import {getOne, queryTheProjectOperationTree} from "@/api/projectManage/projectInitialize"
import {conditionalQueries} from "@/api/record/record"
import {queryProjectPullDown} from "@/api/projectManage/projectInitialize"
import {query} from "@/api/projectManage/projectProcedure"
import {getOneProjectprocedure} from "@/api/projectManage/projectProcedure"
import {queryUser} from "@/api/system/user"
import {queryDictDataTransition} from "@/api/dahc/sys/dictData";

export default {
  name: "index",
  components: {CheckTop, TablePage, MainFlexibleTree},

  data() {
    return {
      treeData: [],
      treeDefaultProps: {
        children: 'children',
        label: 'label'
      },
      queryParams: {
        metaDataName: '',
        mapperDes: '',
        metaDataCN: '',
        archivesType: '',
        isProcedureInspect: '3',
        pageSize: 20,
        pageNum: 1,
      },
      tableData: [],
      tableColumn: [
        {label: '案卷档号', prop: 'filesName', width: '100'},
        // {label: '项目名称', prop: 'projectId', width: '100',formatter: this.projectIdFormatter},
        {label: '当前工序', prop: 'procedureId', width: '100',formatter: this.procedureIdFormatter},
        {label: '核查范围', prop: 'trueingId', width: '100',formatter: this.trueingIdFormatter},
        // {label: '是否已完成当前工序核查', prop: 'isProcedureInspect', width: '100'},
        {label: '核查人姓名', prop: 'inspectId', width: '100',formatter: this.inspectIdFormatter},
      ],
      total: 0,
      procedureForm: {},
      projectIdTable: [],
      projectProcedureIdTable: [],
      userIdTable: [],
      dictDataTransition: []
    }
  },
  created() {
    this.queryTheProjectOperationTree();
    this.queryProjectPullDown();
    this.queryProjectProcedure();
    this.queryUser();
    this.queryDictDataTransition();
  },
  methods: {
    /*工序核查范围*/
    trueingIdFormatter(row, column, cellValue) {
      let result = '';
      this.dictDataTransition.map((item) => {
        if (item.value == cellValue) {
          result = item.label;
        }
      });
      return result;
    },
    /*查询核查范围，表格转换问题*/
    queryDictDataTransition() {
      queryDictDataTransition().then(res => {
        this.dictDataTransition = res
      });
    },
    /*查询项目*/
    queryProjectPullDown() {
      queryProjectPullDown().then(res => {
        this.projectIdTable = res.data;
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
    /*查询tree树*/
    queryTheProjectOperationTree() {
      queryTheProjectOperationTree().then(res => {
        this.treeData = res.data;
        this.treeData = JSON.parse(JSON.stringify(this.treeData).replace(/projectName/g, "label"));
        this.treeData = JSON.parse(JSON.stringify(this.treeData).replace(/id/g, "value"));
        this.treeData = JSON.parse(JSON.stringify(this.treeData).replace(/procedureName/g, "label"));
        this.handleQuery();
      });
    },

    /*点击tree事件*/
    handleNodeClick(val, node, component) {
      this.queryParams.pageSize = 20
      this.queryParams.pageNum = 1
      console.log(val, node)
      this.resetQuery();
      if (val.projectAndProcessState == 0) {
        /*项目数据*/
        this.queryParams.projectId = val.value;
      } else {
        /*工序数据*/
        this.queryParams.procedureId = val.value;
      }
      this.handleQuery(this.queryParams);
    },
    resetQuery() {
      this.queryParams = {
        metaDataName: '',
        projectId: '',
        procedureId: '',
        mapperDes: '',
        metaDataCN: '',
        archivesType: '',
        isProcedureInspect: '3',
        pageSize: 20,
        pageNum: 1,
      };
    },
    /*条件查询*/
    conditionalQueries() {
      this.queryParams.pageSize = 20;
      this.queryParams.pageNum = 1;
      this.handleQuery();
    },
    /*查询*/
    handleQuery() {
      conditionalQueries(this.queryParams).then(res => {
        this.tableData = res.data
        this.total = res.total
      });
    },
    /**
     * 返回
     */
    handleBack() {
      this.$router.go(-1);
    },
    /**
     * 解决存疑
     */
    handleResolve(row){
      getOneProjectprocedure(row.procedureId).then(res => {
        getOne(res.data.projectId).then(res1 => {
          this.$router.push({
            path: res.data.trueingPagePath,
            query: {
              receptionProcedureId: row.procedureId, // 工序id
              receptionArchiveTypeId: res1.data.archiveTypeId, //档案类型id
              isImpeach: true, // 是否是存疑页面
              caseNum: row.filesName, //档号
              recordProcedureFilesId: row.id, //档案核查结果id
              filesId: row.filesId //档案id
            }});
        });
      });
    },

    /*查询工序*/
    getOneProjectprocedure(procedureId) {
      getOneProjectprocedure(procedureId).then(res => {
        this.procedureForm = res.data;
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
  margin-top: 5px;
  height: calc(100% - 55px) !important;
}

</style>

