<template>
  <div class="app-container-padding">
    <el-form :model="queryParams" class="iptAndBtn" ref="queryForm" size="mini" :inline="true" v-show="showSearch"
             @submit.native.prevent>
      <el-form-item label="系统模块" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入系统模块"
          clearable
          size="mini"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作人员" prop="operName">
        <el-input
          v-model="queryParams.operName"
          placeholder="请输入操作人员"
          clearable
          size="mini"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类型" prop="businessType">
        <el-select
          v-model="queryParams.businessType"
          placeholder="操作类型"
          clearable
          size="mini"
          @change="handleQuery">
          <el-option
            v-for="dict in dict.type.sys_oper_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="操作状态"
          clearable
          size="mini"
          @change="handleQuery">
          <el-option
            v-for="dict in dict.type.sys_common_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="操作时间" class="daterange">
        <el-date-picker
          v-model="dateRange"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change="handleQuery"></el-date-picker>
      </el-form-item>
      <!--      <el-form-item>-->
      <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
      <!--      </el-form-item>-->
    </el-form>
    <div class="btn">
      <el-button
        type="primary"
        icon="el-icon-delete"
        size="mini"
        @click="handleDelete"
        v-hasPermi="['monitor:operlog:remove']"
      >删除日志
      </el-button>
      <el-button
        type="primary"
        icon="el-icon-delete-solid"
        size="mini"
        @click="handleClean"
        v-hasPermi="['monitor:operlog:removeAll']"
      >清空日志
      </el-button>
      <el-button
        type="primary"
        size="mini"
        icon="el-icon-download"
        @click="handleExport"
        v-hasPermi="['monitor:operlog:export']"
      ><i class="iconfont icon-daochuwenjian"></i>导出日志
      </el-button>
    </div>
    <TablePage class="tablePage"
               :tableData="list"
               :hasIndex="false"
               :pageSize="queryParams.pageSize"
               :pageNum="queryParams.pageNum"
               :total="total"
               @handleSelectionChange="handleSelectionChange"
               @handleChange="handleChange">
      <template slot="table">
        <el-table-column label="日志编号" align="center" prop="operId"/>
        <el-table-column label="系统模块" align="center" prop="title"/>
        <el-table-column label="操作类型" align="center" prop="businessType" class-name="noticeType">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_oper_type" :value="scope.row.businessType"/>
          </template>
        </el-table-column>
        <el-table-column label="请求方式" align="center" prop="requestMethod"/>
        <el-table-column label="操作人员" align="center" prop="operName" width="100" :show-overflow-tooltip="true"/>
        <!--    <el-table-column label="操作人员" align="center" prop="operName" width="100" :show-overflow-tooltip="true" sortable="custom" :sort-orders="['descending', 'ascending']" />-->
        <el-table-column label="操作地址" align="center" prop="operIp" width="130" :show-overflow-tooltip="true"/>
        <el-table-column label="操作地点" align="center" prop="operLocation" :show-overflow-tooltip="true"/>
        <el-table-column label="操作状态" align="center" prop="status" class-name="noticeType"
                         :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_common_status" :value="scope.row.status"/>
          </template>
        </el-table-column>
        <!--    <el-table-column label="操作日期" align="center" prop="operTime" sortable="custom" :sort-orders="['descending', 'ascending']" width="180">-->
        <el-table-column label="操作日期" align="center" prop="operTime" width="180" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.operTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width operation"
                         :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              @click="handleView(scope.row,scope.index)"
              v-hasPermi="['monitor:operlog:query']"
            >查看详情
            </el-button>
          </template>
        </el-table-column>
      </template>
    </TablePage>


    <!-- 操作日志详细 -->
    <el-dialog title="操作日志详情" :visible.sync="open" width="900px" append-to-body class="dialog-style">
      <el-descriptions class="margin-top" :column="2" :size="size" border>
        <el-descriptions-item label="操作模块" label-class-name="my-label">
          {{ form.title }} / {{ typeFormat(form) }}
        </el-descriptions-item>
        <el-descriptions-item label="请求地址">
          {{ form.operUrl }}
        </el-descriptions-item>
        <el-descriptions-item label="登录信息">
          {{ form.operName }} / {{ form.operIp }} / {{ form.operLocation }}
        </el-descriptions-item>
        <el-descriptions-item label="请求方式">
          {{ form.requestMethod }}
        </el-descriptions-item>
        <el-descriptions-item label="操作方法" :span="2">
          {{ form.method }}
        </el-descriptions-item>
        <el-descriptions-item label="请求参数" :span="2">
          {{ form.operParam }}
        </el-descriptions-item>
        <el-descriptions-item label="返回参数" :span="2">
          {{ form.jsonResult }}
        </el-descriptions-item>
        <el-descriptions-item label="操作状态">
          <div v-if="form.status === 0">正常</div>
          <div v-else-if="form.status === 1">失败</div>
        </el-descriptions-item>
        <el-descriptions-item label="操作时间">
          {{ parseTime(form.operTime) }}
        </el-descriptions-item>
        <el-descriptions-item label="异常信息" :span="2" v-if="form.status === 1">
          {{ form.errorMsg }}
        </el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script>
import {list, delOperlog, cleanOperlog} from "@/api/monitor/operlog";
import TablePage from "@/components/Public/table/TablePage.vue";

export default {
  name: "Operlog",
  dicts: ['sys_oper_type', 'sys_common_status'],
  components: {TablePage},
  data() {
    return {
      size: '',
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 表格数据
      list: [],
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 默认排序
      defaultSort: {prop: 'operTime', order: 'descending'},
      // 表单参数
      form: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        title: undefined,
        operName: undefined,
        businessType: undefined,
        status: undefined
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询登录日志 */
    getList() {
      this.loading = true;
      list(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.list = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // 操作日志类型字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.dict.type.sys_oper_type, row.businessType);
    },
    /** 搜索按钮操作 */
    handleQuery() {
      // this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.$refs.tables.sort(this.defaultSort.prop, this.defaultSort.order)
      this.handleQuery();
    },
    /** 多选框选中数据 */
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.operId)
      this.multiple = !selection.length
    },
    /* 单击表格行 */
    handleRowClick(row, column, event) {
      this.$refs.tables.toggleRowSelection(row, column)
    },
    /*分页器*/
    handleChange(size, num) {
      this.queryParams.pageNum = num;
      this.queryParams.pageSize = size;
      this.handleQuery();
    },
    /** 排序触发事件 */
    handleSortChange(column, prop, order) {
      this.queryParams.orderByColumn = column.prop;
      this.queryParams.isAsc = column.order;
      this.getList();
    },
    /** 详细按钮操作 */
    handleView(row) {
      this.open = true;
      this.form = row;
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      if (this.multiple) {
        this.$message.warning("请选择一条数据进行操作")
        return false
      }
      const operIds = row.operId || this.ids;
      this.$modal.confirm('是否确认删除日志编号为"' + operIds + '"的数据项？').then(function () {
        return delOperlog(operIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    /** 清空按钮操作 */
    handleClean() {
      this.$modal.confirm('是否确认清空所有操作日志数据项？').then(function () {
        return cleanOperlog();
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("清空成功");
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消清空'
        });
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('monitor/operlog/export', {
        ...this.queryParams
      }, `operlog_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style lang="scss" scoped>
.tablePage {
  height: calc(100% - 100px) !important;

  ::v-deep .noticeType {
    .el-tag {
      background-color: transparent;
      color: #909399;
      border: none;
    }

  }
}
::v-deep .my-label{
  min-width: 100px;
}
</style>
