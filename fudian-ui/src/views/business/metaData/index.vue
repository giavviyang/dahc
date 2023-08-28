<template>
  <div class="app-container-padding">
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="iptAndBtn" @submit.native.prevent>
      <el-form-item label="元数据名称" prop="metaDataName">
        <el-input
          v-model="queryParams.metadataName"
          placeholder="请输入元数据名称"
          @keyup.enter.native="handleQuery"
          clearable/>
      </el-form-item>
      <el-form-item label="元数据类型" prop="metadataType">
        <el-select v-model="queryParams.metadataType" placeholder="请选择元数据类型" maxlength="30" clearable
                   @change="handleQueryHead">
          <el-option v-for="metaDataItem in metaDataTypeOptions"
                     :key="metaDataItem.value"
                     :label="metaDataItem.label"
                     :value="metaDataItem.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQueryHead" v-hasPermi="['business:metaData:list']">搜索</el-button>
    </el-form>
    <div class="btn">
      <el-button
        type="primary"
        icon="el-icon-plus"
        size="mini"
        @click="handleAdd"
        v-hasPermi="['business:metaData:add']" >
        新增元数据
      </el-button>
      <el-button
        type="primary"
        icon="el-icon-edit"
        size="mini"
        @click="handleUpdate"
        v-hasPermi="['business:metaData:edit']">
        编辑元数据
      </el-button>
      <el-button
        type="primary"
        icon="el-icon-delete"
        size="mini"
        @click="handleDelete"
        v-hasPermi="['business:metaData:delete']">
        删除元数据
      </el-button>
    </div>
    <TablePage class="tablePage"
               :tableData="tableData"
               :pageSize="queryParams.pageSize"
               :pageNum="queryParams.pageNum"
               :total="total"
               @handleSelectionChange="handleSelectionChange"
               @handleChange="handleChange"
    @handleRowClick="handleRowClick">
      <template slot="table">
        <el-table-column
          show-overflow-tooltip
          v-for="(item,index) in tableColumn"
          :key="index"
          :prop="item.prop"
          :label="item.label"
          :formatter="item.formatter"
          :min-width="item.width">
        </el-table-column>
      </template>
    </TablePage>
    <el-dialog :title="dialogType==='add'?'新增元数据':dialogType==='edit'?'编辑元数据':''"
               :visible.sync="dialogVisible"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="600px" class="dialog-style">
      <el-form ref="metaDataFormRef" :model="metaDataForm" size="mini" :rules="metaDataRules" :inline="true"
               class="ipt">
        <el-form-item label="元数据名称" prop="metadataName" class="remarks">
          <el-input v-model="metaDataForm.metadataName" placeholder="请输入元数据名称" maxlength="30" />
        </el-form-item>
        <el-form-item label="元数据类型" prop="metadataType">
          <el-select v-model="metaDataForm.metadataType" placeholder="请选择元数据类型" maxlength="30" >
            <el-option v-for="metaDataItem in metaDataTypeOptions"
                       :key="metaDataItem.value"
                       :label="metaDataItem.label"
                       :value="metaDataItem.value"
            ></el-option>
          </el-select>
        </el-form-item>
<!--        <el-form-item label="输入长度（个）" prop="metadataLong">-->
<!--          <el-input v-model="metaDataForm.metadataLong" placeholder="请输入输入长度（个）" maxlength="30" clearable/>-->
<!--        </el-form-item>-->
        <el-form-item label="显示宽度（px）" prop="metadataWidth">
          <el-input v-model="metaDataForm.metadataWidth" placeholder="请输入显示宽度（px）" maxlength="30" clearable/>
        </el-form-item>
        <el-form-item label="备注" prop="metadataDesc" class="remarks">
          <el-input v-model="metaDataForm.metadataDesc" placeholder="请输入备注" maxlength="60" clearable
                    type="textarea"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" size="mini" v-if="dialogType==='add'" @click="handleNext('metaDataFormRef')">下一个</el-button>
        <el-button type="primary" size="mini" @click="handleSave('metaDataFormRef')">保存</el-button>
        <el-button size="mini" @click="dialogVisible=false">取消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import TablePage from "@/components/Public/table/TablePage.vue";
import {searchData, addData, updateData, delData} from "@/api/business/metadata"
import {queryDictDataTransitionId} from "@/api/dahc/sys/dictData"
import {formatDate} from "@/utils";

export default {
  name: "index",
  components: {TablePage},

  data() {
    // var checkCN = (rule, value, callback) => {
    //   let reg = /^[A-Za-z-_]+$/;
    //   if (!reg.test(value)) {
    //     callback(new Error('请输入英文和下划线'));
    //   } else {
    //     callback();
    //   }
    // };
    return {
      queryParams: {
        metaDataName: '',
        metaDataType: '',
        pageSize: 20,
        pageNum: 1,
      },
      tableData: [],
      tableColumn: [
        {label: '元数据名称', prop: 'metadataName', width: '100'},
        {label: '元数据类型', prop: 'metadataType', width: '100', formatter: this.metadataTypeFormatter},
        // {label: '输入长度（个）', prop: 'metadataLong', width: '60'},
        {label: '显示宽度（px）', prop: 'metadataWidth', width: '60'},
        {label: '创建人', prop: 'createByName', width: '60'},
        {label: '创建时间', prop: 'createTime', width: '80', formatter: formatDate},

        // {label: '修改时间', prop: 'updateTime', width: '100', formatter:formatDate},
        {label: '备注', prop: 'metadataDesc', width: '80'},
      ],
      selected: [], //选中数据
      total: 0,
      dialogType: 'add',
      dialogVisible: false,
      metaDataForm: {
        metadataName: '',
        metadataType: '',
        // metadataLong:'',
        metadataDesc: '',
        metadataWidth: '100',
      },
      metaDataTypeOptions: [],
      metaDataRules: {
        metadataName: [
          {required: true, message: "元数据名称不能为空", trigger: "blur"},
          {max: 10,message: "元数据名称长度最大为10"}
        ],
        metadataType: [
          {required: true, message: "元数据类型不能为空", trigger: "blur"},
        ],
        metadataWidth: [
          {required: true, message: "显示宽度（px）不能为空", trigger: "blur"},
          {pattern: /^[1-9]\d*$/, message: '请输入正整数', trigger: "blur"},
          {pattern: /^([1-9]|[1-9]\d|1\d{2}|200)$/,message: "显示宽度（px）最大为200"}
        ],
        metadataDesc: [{max: 125, message: '字符长度最大不得超过125', trigger: 'blur'}],
      },
      returnState: 20000
    }
  },
  created() {
    this.handleQuery();
    this.getDictDataTransitionId();
  },
  methods: {
    handleQueryHead() {
      this.queryParams.pageNum = 1
      this.queryParams.pageSize = 20
      this.handleQuery();
    },
    /**
     * 查询
     */
    handleQuery() {
      searchData(this.queryParams).then(response => {
        if (response.code === this.returnState) {
          this.tableData = response.data;
          this.total = response.total;
        }
      });
    },
    /*查询字典表元数据类型转换*/
    getDictDataTransitionId() {
      queryDictDataTransitionId("23de3c7d2bea43c3ad18ebe74f856e84").then(res => {
        this.metaDataTypeOptions = res
      });
    },
    //表格类型字段字典转换
    metadataTypeFormatter(row, column, cellValue) {
      let result = '';
      this.metaDataTypeOptions.map((item) => {
        if (item.value == cellValue) {
          result = item.label;
        }
      });
      return result;
    },
    /**
     * 新增元数据
     */
    handleAdd() {
      this.reset();
      this.dialogType = 'add';
      this.dialogVisible = true;

    },
    /**
     * 编辑核查项
     */
    handleUpdate() {
      console.log(this.selected)
      if (!this.selected || this.selected === [] || this.selected.length === 0) {
        this.$message({
          message: '请选择一条数据',
          type: 'warning'
        });
      } else if (this.selected.length !== 1) {
        this.$message({
          message: '请选择一条数据',
          type: 'warning'
        });
      } else  {
        let nonSelected =this.selected.find(item=>item.metadataName==='案卷档号');
        if(nonSelected){
          this.$message({
            message: '当前元数据不可编辑',
            type: 'warning'
          });
        }else {
          this.reset()
          this.dialogType = 'edit';
          this.dialogVisible = true;
          Object.assign(this.metaDataForm, this.selected[0])
        }
      }
    },
    /**
     * 删除核查项
     */
    handleDelete() {
      if (!this.selected || this.selected === [] || this.selected.length === 0) {
        this.$message({
          message: '请至少选择一条数据',
          type: 'warning'
        });
      } else {
        this.$confirm('此操作将永久删除选中数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          let ids = this.selected.map(item => {
            return item.id
          })
          delData(ids).then(res => {
            if (res.code == this.returnState) {
              this.$modal.msgSuccess("删除成功");
              this.dialogVisible = false;
              this.handleQuery()
            }
          });

        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      }
    },
    /**
     * 表格复选框
     */
    handleSelectionChange(selection) {
      this.selected = selection
    },
    /*重置表单数据*/
    reset() {
      this.metaDataForm = {
        metadataName: '',
        metadataType: 'varchar',
        metadataWidth: '100',
        metadataDesc: '',
      }
    },
    /**
     * 分页器
     */
    handleChange(pageSize, pageNum) {
      this.queryParams.pageSize = pageSize;
      this.queryParams.pageNum = pageNum;
      this.handleQuery();
    },
    handleRowClick({row, column, event}){
      console.log({row, column, event})
      row=this.tableData[3];
    },
    /**
     * 下一个
     */
    handleNext(formName){
      this.$refs[formName].validate((valid) => {
        if (valid) {
          addData(this.metaDataForm).then(res => {
            if (res.code === this.returnState) {
              this.$modal.msgSuccess("添加成功");
              this.handleQuery();
              this.reset();
            }
          });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    /**
     * 保存核查项弹窗
     */
    handleSave(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.dialogType === 'edit') {
            updateData(this.metaDataForm).then(res => {
              if (res.code === this.returnState) {
                this.$modal.msgSuccess("修改成功");
                this.dialogVisible = false;
                this.handleQuery()
              }
            });
          } else {
            addData(this.metaDataForm).then(res => {
              if (res.code === this.returnState) {
                this.$modal.msgSuccess("添加成功");
                this.dialogVisible = false;
                this.handleQuery()
              }
            });
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },

  }
}
</script>

<style lang="scss" scoped>

.tablePage {
  height: calc(100% - 100px) !important;
}
::v-deep .ipt {

  .el-form-item {

    .el-form-item__label {
      width: 120px;
    }
    .el-form-item__content {
      width: calc(100% - 130px);
    }
  }
}
</style>

