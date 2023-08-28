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
    <TablePage class="tablePage"
               :tableData="projectData"
               :pageSize="queryParams.projectSize"
               :pageNum="queryParams.projectNum"
               :total="projectTotal"
               :hasSelection = false
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
            <el-button @click="handleExamineResult(scope.row)"
                       type="text" size="mini">查看核查结果
            </el-button>
          </template>
        </el-table-column>
      </template>
    </TablePage>
    <TablePage class="tablePage"
               :is-title="true"
               :tableTitle="tableProcessTitle"
               :hasSelection="false"
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
    <el-dialog title="查看核查结果"
               :visible.sync="dialogResult"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="80%" class="dialog-style">
      <TablePage class="tablePage"
                 :hasSelection="false"
                 :tableData="resultData"
                 :pageSize="resultSize"
                 :pageNum="resultNum"
                 :total="resultTotal"
                 @handleChange="handleResultChange"
      >
        <template slot="table">
          <el-table-column
            show-overflow-tooltip
            v-for="(item,index) in resultColumn"
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

export default {
  name: "completed",
  components: {TablePage},

  data() {
    return {
      queryParams: {
        projectName: '',
        pageSize: 20,
        pageNum: 1,
      },
      projectData: [{
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1517 弄'
      }],
      projectColumn: [
        {label: '项目名称', prop: 'date', width: '100'},
        {label: '项目描述', prop: 'name', width: '100'},
        {label: '创建人', prop: 'address', width: '100'},
        {label: '创建时间', prop: 'remarks', width: '100'},
        {label: '备注', prop: 'remarks', width: '100'},
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
      dialogResult: false,
      resultData: [{
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1517 弄'
      }],
      resultColumn: [
        {label: '工序名称', prop: 'date', width: '100'},
        {label: '工序描述', prop: 'name', width: '100'},
        {label: '核查项范围', prop: 'address', width: '100'},
        {label: '核查项数量', prop: 'remarks', width: '100'},
        {label: '可核查人数', prop: 'remarks', width: '100'},
      ],
      resultTotal: 0,
      resultSize: 10,
      resultNum: 1,
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
     * 表格复选框
     */
    handleProjectSelection(selection) {
      this.selected = selection
    },
    handleProjectClick(row, column, event) {
      console.log(row, column, event)
    },
    /**
     * 查看核查结果
     */
    handleExamineResult() {
      this.dialogResult = true
    },
    handleResultChange(pageSize, pageNum) {
      this.resultSize = pageSize;
      this.resultNum = pageNum;
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
  height: calc(50% - 30px);
  margin-top: 5px;
}
.tablePage:last-of-type{
  margin-top: 0;
}
</style>


