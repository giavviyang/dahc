<template>
  <div class="app-container-padding">
    <el-form :model="queryParams" ref="queryForm" class="iptAndBtn" size="mini" :inline="true" @submit.native.prevent>
      <el-form-item label="操作名称" prop="buttonName">
        <el-input
          v-model="queryParams.buttonName"
          placeholder="请输入操作名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目名称" prop="projectName">
        <el-input
          v-model="queryParams.projectName"
          placeholder="请输入项目名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工序名称" prop="procedureName">
        <el-input
          v-model="queryParams.procedureName"
          placeholder="请输入工序名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="档号" prop="caseFileNumber1">
        <el-input
          v-model="queryParams.caseFileNumber1"
          placeholder="请输入档号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!--      <el-form-item style="width: 100px">-->
      <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
      <!--      </el-form-item>-->
    </el-form>
    <div class="btn">
      <el-button
        type="primary"
        icon="el-icon-delete"
        size="mini"
        @click="handleDelete"
      >删除日志
      </el-button>
      <el-button
        type="primary"
        icon="el-icon-delete-solid"
        size="mini"
        @click="removeAll"
      >清空日志
      </el-button>
      <el-button
        type="primary"
        icon="el-icon-download"
        size="mini"
        @click="handleExport">导出
      </el-button>
    </div>
    <TablePage class="tablePage"
               :tableData="logList"
               :hasIndex="true"
               :pageSize="queryParams.pageSize"
               :pageNum="queryParams.pageNum"
               :total="total"
               @handleSelectionChange="handleSelectionChange"
               @handleChange="handleChange" :spanMap="spanMap">
      <template slot="table">
        <el-table-column
          v-for="(item,index) in tableColumn"
          :key="index"
          :prop="item.prop"
          :label="item.label"
          :formatter="item.formatter"
          :min-width="item.width"
          :show-overflow-tooltip="item.tooltip">
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
    <el-dialog title="操作日志详情" :visible.sync="open" width="1000px" append-to-body class="dialog-style">
      <el-descriptions class="margin-top" :column="2" :size="size" border>
        <el-descriptions-item label="项目名称" label-class-name="my-label" :span="2">
          {{ form.projectName }}
        </el-descriptions-item>
        <el-descriptions-item label="工序名称" :span="form.isImg!==1?2:1">
          {{ form.procedureName }}
        </el-descriptions-item>
        <el-descriptions-item label="操作名称">
          {{ form.buttonName }}
        </el-descriptions-item>
        <el-descriptions-item label="案卷档号">
          {{ form.caseFileNumber1 }}
        </el-descriptions-item>
        <el-descriptions-item label="案件序号">
          {{ form.caseNumber2 }}
        </el-descriptions-item>
        <el-descriptions-item label="当前状态">
         <span v-if="form.checkStatus===0">
           正常
         </span>
          <span v-if="form.checkStatus===1">
           存疑
         </span>
        </el-descriptions-item>
        <el-descriptions-item label="核查结果">
         <span v-if="form.submitButton===0">
           正常
         </span>
          <span v-if="form.submitButton===1">
           存疑
         </span>
        </el-descriptions-item>
        <el-descriptions-item label="否是为图片">
          <span v-if="form.isImg===1">是</span>
          <span v-else>否</span>
        </el-descriptions-item>
        <el-descriptions-item label="图片页号" v-if="form.isImg===1">
          {{ form.imgPageNumber }}
        </el-descriptions-item>

        <el-descriptions-item label="源图片本地保存地址" :span="2" v-if="form.isImg===1&&form.sourcePicturePath">
          {{ form.sourcePicturePath }}
        </el-descriptions-item>
        <el-descriptions-item label="源图片名称" :span="2" v-if="form.isImg===1&&form.sourcePictureName">
          {{ form.sourcePictureName }}
        </el-descriptions-item>
        <el-descriptions-item label="新增图片本地保存地址" :span="2"
                              v-if="form.isImg===1&&form.insertSourcePicturePath">
          {{ form.insertSourcePicturePath }}
        </el-descriptions-item>
        <el-descriptions-item label="新增图片名称" :span="2" v-if="form.isImg===1&&form.insertSourcePictureName">
          {{ form.insertSourcePictureName }}
        </el-descriptions-item>
        <el-descriptions-item label="源数据" :span="2">
          {{ form.sourceDataJson }}
        </el-descriptions-item>
        <el-descriptions-item label="现数据" :span="2">
          {{ form.nowDataJson }}
        </el-descriptions-item>
        <el-descriptions-item label="用户名称">
          {{ form.createBy }}
        </el-descriptions-item>
        <el-descriptions-item label="创建时间">
          {{ form.createTime }}
        </el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script>
  import {listLog, getLog, delLog, addLog, updateLog, removeAll} from "@/api/log/checkLog";
  import TablePage from "@/components/Public/table/TablePage";

  export default {
    name: "Log",
    components: {TablePage},
    data() {
      return {
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 总条数
        total: 0,
        // 【请填写功能名称】表格数据
        logList: [],
        tableColumn: [
          {label: '项目名称', prop: 'projectName', width: '100', tooltip: false},
          {label: '工序名称', prop: 'procedureName', width: '100', tooltip: false},
          {label: '操作名称', prop: 'buttonName', width: '100', tooltip: false},
          {label: '案卷档号', prop: 'caseFileNumber1', width: '120', formatter: this.caseFileNumber1Formatter},
          {label: '案件序号', prop: 'caseNumber2', width: '100',formatter: this.caseNumber2Formatter},
          {label: '当前状态', prop: 'checkStatus', width: '100', formatter: this.submitButtonFormatter},
          {label: '核查结果', prop: 'submitButton', width: '100', formatter: this.submitButtonFormatter},
          {label: '否是为图片', prop: 'isImg', width: '100', formatter: this.isImgFormatter},
          // {label: '图片页号', prop: 'imgPageNumber', width: '80',},
          {label: '用户名称', prop: 'createBy', width: '100'},
          {label: '创建时间', prop: 'createTime', width: '150'},
        ],
        // 弹出层标题
        title: "",
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 20,
          buttonName: null,
          projectName: null,
          projectId: null,
          procedureName: null,
          procedureId: null,
          caseTableName: null,
          fileTableName: null,
          caseFileNumber1: null,
          caseNumber2: null,
          fileTableNameId: null,
          caseNumberId: null,
          isImg: null,
          imgPageNumber: null,
          sourcePicturePath: null,
          sourcePictureName: null,
          sourceFilePhotoId: null,
          insertSourcePicturePath: null,
          insertSourcePictureName: null,
          insertFilePhotoId: null,
          submitButton: null,
          sourceDataJson: null,
          nowDataJson: null,
        },

        spanMap: {},
        // 是否显示弹出层
        open: false,
        // 表单参数
        form: {},
        size: '',
      };
    },
    created() {
      this.getList();
    },
    methods: {
      caseFileNumber1Formatter(row, column, cellValue) {
        let result = '';
        result = cellValue.split("[").join("");
        result = result.split("]").join("");
        result = result.replace(/\"/g, "");
        return result;
      },
      caseNumber2Formatter(row, column, cellValue) {
        let result = '';
        let lastIndex = cellValue.lastIndexOf("-"); // 获取最后一个"-"的位置
        result = cellValue.substring(lastIndex+1);
        return result;
      },
      initSpanArr(data, column) {
        let contactDot = 0;
        this.spanMap[column] = {};
        this.spanMap[column].spanArr = [];
        data.forEach((item, index) => {
          if (index === 0) {
            this.spanMap[column].spanArr.push(1); // 第一行数据 不合并
          } else {
            if (item[column] === data[index - 1][column]) {
              this.spanMap[column].spanArr[contactDot] += 1; // 下一行，如果数据相同，合并行数加一
              this.spanMap[column].spanArr.push(0);
            } else {
              contactDot = index;
              this.spanMap[column].spanArr.push(1);
            }
          }
        });
      },
      isImgFormatter(row, column, cellValue) {
        let result = '';
        if (cellValue == '1') {
          result = "是";
        } else {
          result = "否";
        }
        return result;
      },
      submitButtonFormatter(row, column, cellValue) {
        let result = '';
        if (cellValue == '0') {
          result = "正常";
        } else if (cellValue == '1') {
          result = "存疑";
        } else {
          result = "";
        }
        return result;
      },
      /** 查询【请填写功能名称】列表 */
      getList() {
        listLog(this.queryParams).then(response => {

          this.logList = response.data;
          this.total = response.total;
          if (response.data) {
            this.initSpanArr(this.logList, 'projectName');
            this.initSpanArr(this.logList, 'procedureName');
          }
        });
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        if (ids.length == 0) {
          this.$message.warning("请选择一条数据进行操作");
        } else {
          this.$modal.confirm('是否确认删除编号为"' + ids + '"的数据项？').then(function () {
            return delLog(ids);
          }).then(() => {
            this.getList();
            this.$modal.msgSuccess("删除成功");
          }).catch(() => {
          });
        }

      },
      /** 删除按钮操作 */
      removeAll() {
        this.$modal.confirm('是否确认清空所有核查日志数据项？').then(function () {
          return removeAll();
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {
        });
      },
      /** 导出按钮操作 */
      handleExport() {
        let obj = this.queryParams;
        delete obj.pageNum
        delete obj.pageSize
        this.download('checkRecordLog/export', {
          ...this.queryParams
        }, `log_${new Date().getTime()}.xlsx`)
      },
      handleChange(size, num) {
        this.queryParams.pageNum = num;
        this.queryParams.pageSize = size;
        this.getList();
      },
      /** 详细按钮操作 */
      handleView(row) {
        this.open = true;
        this.form = row;
        console.log(this.form)
      },
    }
  };
</script>
<style lang="scss" scoped>
  .tablePage {
    height: calc(100% - 100px) !important;
  }

  ::v-deep .my-label {
    max-width: 180px;
    min-width: 150px;
  }

  //::v-deep .el-descriptions-item__content{
  //  width: 200px;
  //}
</style>
