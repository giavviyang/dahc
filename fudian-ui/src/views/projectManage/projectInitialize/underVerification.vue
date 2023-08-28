<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="iptAndBtn">
      <el-form-item label="项目名称" prop="projectName">
        <el-input
          v-model="queryParams.projectName"
          placeholder="请输入项目名称"
          @keyup.enter.native="handleQuery"
          clearable/>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
    </el-form>
    <div class="btn">
      <el-dropdown>
        <el-button type="primary" size="mini">
          <i class="el-icon-upload" style="display: inline-block;margin-right: 5px;"></i> 导入数据<i
          class="el-icon-arrow-down el-icon--right"></i>
        </el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>全部导入</el-dropdown-item>
          <el-dropdown-item>导入案卷级条目</el-dropdown-item>
          <el-dropdown-item>导入文件级条目</el-dropdown-item>
          <el-dropdown-item>导入电子文件</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <el-button
        type="primary"
        icon="el-icon-circle-check"
        size="mini"
        @click="handleEnd">
        结束核查
      </el-button>
    </div>
    <TablePage class="tablePage"
               ref="projectRef"
               :tableData="projectData"
               :pageSize="queryParams.projectSize"
               :pageNum="queryParams.projectNum"
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
          :min-width="item.width">
        </el-table-column>
        <el-table-column
          label="操作"  fixed="right"
          width="120" class-name="operation">
          <template slot-scope="scope">
            <el-button @click="handleExamineProgress(scope.row)"
                       type="text" size="mini">查看核查进度
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
               @handleChange="handleProcessChange"
    >
      <template slot="table">
        <el-table-column
          show-overflow-tooltip
          v-for="(item,index) in processColumn"
          :key="index"
          :prop="item.prop"
          :label="item.label"
          :min-width="item.width">
        </el-table-column>
        <el-table-column
          label="操作"  fixed="right"
          width="120" class-name="operation">
          <template slot-scope="scope">
            <el-button @click="handleExamineDetail(scope.row)"
                       type="text" size="mini">查看核查项
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
                 @handleChange="handleProgressChange"
      >
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
    <el-dialog title="查看核查项详情"
               :visible.sync="dialogExamine"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="80%" class="dialog-style">
      <TablePage class="tablePage"
                 :hasSelection="false"
                 :tableData="examineData"
                 :pageSize="examineSize"
                 :pageNum="examineNum"
                 :total="examineTotal"
                 @handleChange="handleExamineChange"
      >
        <template slot="table">
          <el-table-column
            show-overflow-tooltip
            v-for="(item,index) in examineColumn"
            :key="index"
            :prop="item.prop"
            :label="item.label"
            :min-width="item.width">
          </el-table-column>
        </template>
      </TablePage>
    </el-dialog>
  </div>
</template>

<script>
import TablePage from "@/components/Public/table/TablePage.vue";
import {search} from "@/api/projectManage/projectInitialize";

export default {
  name: "underVerification",
  components: {TablePage},

  data() {
    return {
      queryParams: {
        projectState: 1,
        projectName: '',
        pageSize: 20,
        pageNum: 1,
      },
      projectData: [],
      projectColumn: [
        {label: '项目名称', prop: 'projectName', width: '100'},
        {label: '项目描述', prop: 'projectDesc', width: '100'},
        {label: '档案类型', prop: 'archiveTypeId', width: '100',formatter: this.archiveTypeIdFormatter},
        {label: '创建人', prop: 'createBy', width: '100'},
        {label: '创建时间', prop: 'createTime', width: '100',formatter: this.startTimeFormatter},
        {label: '项目状态', prop: 'projectState', width: '100',formatter: this.projectStateFormatter},
        {label: '预计开始时间', prop: 'startTime', width: '100',formatter: this.startTimeFormatter},
        {label: '预计结束时间', prop: 'endTime', width: '100',formatter: this.endTimeFormatter},
      ],
      selected: [], //选中数据
      projectTotal: 0,
      tableProcessTitle:'工序信息：',
      processData: [{
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1517 弄'
      }],
      processColumn: [
        {label: '工序名称', prop: 'date', width: '100'},
        {label: '工序描述', prop: 'name', width: '100'},
        {label: '核查项范围', prop: 'address', width: '100'},
        {label: '核查项数量', prop: 'remarks', width: '100'},
        {label: '可核查人数', prop: 'remarks', width: '100'},
      ],
      processTotal: 0,
      processSize: 10,
      processNum: 1,
      dialogType: 'add',
      dialogProgress: false,
      progressData: [{
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1517 弄'
      }],
      progressColumn: [
        {label: '工序名称', prop: 'date', width: '100'},
        {label: '工序描述', prop: 'name', width: '100'},
        {label: '核查项范围', prop: 'address', width: '100'},
        {label: '核查项数量', prop: 'remarks', width: '100'},
        {label: '可核查人数', prop: 'remarks', width: '100'},
      ],
      progressTotal: 0,
      progressSize: 10,
      progressNum: 1,
      dialogExamine: false,
      examineData: [{
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1517 弄'
      }],
      examineColumn: [
        {label: '工序名称', prop: 'date', width: '100'},
        {label: '工序描述', prop: 'name', width: '100'},
        {label: '核查项范围', prop: 'address', width: '100'},
        {label: '核查项数量', prop: 'remarks', width: '100'},
        {label: '可核查人数', prop: 'remarks', width: '100'},
      ],
      examineTotal: 0,
      examineSize: 10,
      examineNum: 1,
    }
  },
  created() {
    this.handleQuery();
  },
  methods: {
    /**
     * 查询
     */
    handleQuery() {
      search(this.queryParams).then(res => {
        if (res.code == this.returnState) {
          this.projectData = res.data;
          this.projectTotal = res.total;
          /*查询工序*/
          // this.queryProcessParams.projectId = res.data[0].id
          // this.searchProcedureList()
        }
      });
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
          console.log(this.selected)
          // let ids = this.selected.map(item=>{
          //   return item.deptId
          // }).toString()
          // delDept(ids).then(res=>{
          //   this.$message.success(res.msg)
          //   this.selected = [];
          //   this.getList();
          //   this.getTreeList();
          // })

        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消开始核查'
          });
        });
      }
    },
    /**
     * 表格复选框，只支持单选
     */
    handleProjectSelection(selection) {
      // 清除 所有勾选项
      if (selection.length === 1) {
        this.selected = selection;
        // this.handleClick(this.activeName)
      }
      if (selection.length > 1) {
        this.$refs.projectRef.$refs.tableRef.clearSelection();
        this.$refs.projectRef.$refs.tableRef.toggleRowSelection(selection.at(-1), true);
        this.selected = selection.slice(-1);
        // this.handleClick(this.activeName)
      }
      if (selection.length === 0) {
        this.selected = [];
      }
    },
    handleProjectClick(row, column, event) {
      console.log(row, column, event)
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
      this.queryParams.projectSize = pageSize;
      this.queryParams.projectNum = pageNum;
      this.handleQuery();
    },
    /**
     * 查看核查项详情
     */
    handleExamineDetail() {
      this.dialogExamine = true;
    },
    handleExamineChange(pageSize, pageNum) {
      this.examineSize = pageSize;
      this.examineNum = pageNum;
    },
    handleProcessChange(pageSize, pageNum) {
      this.processSize = pageSize;
      this.processNum = pageNum;
    },


  }
}
</script>

<style lang="scss" scoped>

.tablePage {
  height: calc(50% - 52px);
}
</style>


