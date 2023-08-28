<template>
  <div class="app-container">
    <MainFlexibleTree :data="treeData" :defaultProps="treeDefaultProps">
      <el-tabs slot="mainRight"
               type="border-card"
               v-model="activeName"
               @tab-click="handleClick">
        <el-tab-pane label="案卷级" name="first">
          <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="iptAndBtn" @submit.native.prevent>
            <el-form-item label="元数据名称" prop="metaDataName">
              <el-input
                v-model="queryParams.metaDataName"
                placeholder="请输入元数据名称"
                @keyup.enter.native="handleQuery"
                clearable/>
            </el-form-item>
            <el-form-item label="元数据中文名称" prop="metaDataCN">
              <el-input
                v-model="queryParams.metaDataCN"
                placeholder="请输入元数据中文名称"
                @keyup.enter.native="handleQuery"
                clearable/>
            </el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          </el-form>
          <div class="btn">
            <el-button
              type="primary"
              icon="el-icon-plus"
              size="mini"
              @click="handleCreate">
              创建映射
            </el-button>
            <el-button
              type="primary"
              icon="el-icon-edit"
              size="mini"
              @click="handleUpdate">
              编辑映射
            </el-button>
            <el-button
              type="primary"
              icon="el-icon-delete"
              size="mini"
              @click="handleCancel">
              取消映射
            </el-button>
          </div>
          <TablePage class="tablePage"
                     :tableData="tableData"
                     :pageSize="queryParams.pageSize"
                     :pageNum="queryParams.pageNum"
                     :total="total"
                     @handleSelectionChange="handleSelectionChange"
                     @handleChange="handleChange">
            <template slot="table">
              <el-table-column
                show-overflow-tooltip
                v-for="(item,index) in tableColumn"
                :key="index"
                :prop="item.prop"
                :label="item.label"
                :min-width="item.width">
              </el-table-column>
            </template>
          </TablePage>
        </el-tab-pane>
        <el-tab-pane label="文件级" name="second">
          <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="iptAndBtn" @submit.native.prevent>
            <el-form-item label="元数据名称" prop="metaDataName">
              <el-input
                v-model="queryParams.metaDataName"
                placeholder="请输入元数据名称"
                @keyup.enter.native="handleQuery"
                clearable/>
            </el-form-item>
            <el-form-item label="元数据中文名称" prop="metaDataCN">
              <el-input
                v-model="queryParams.metaDataCN"
                placeholder="请输入元数据中文名称"
                @keyup.enter.native="handleQuery"
                clearable/>
            </el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          </el-form>
          <div class="btn">
            <el-button
              type="primary"
              icon="el-icon-plus"
              size="mini"
              @click="handleCreate">
              创建映射
            </el-button>
            <el-button
              type="primary"
              icon="el-icon-edit"
              size="mini"
              @click="handleUpdate">
              编辑映射
            </el-button>
            <el-button
              type="primary"
              icon="el-icon-delete"
              size="mini"
              @click="handleCancel">
              取消映射
            </el-button>
          </div>
          <TablePage class="tablePage"
                     :tableData="tableData"
                     :pageSize="queryParams.pageSize"
                     :pageNum="queryParams.pageNum"
                     :total="total"
                     @handleSelectionChange="handleSelectionChange"
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
            </template>
          </TablePage>
        </el-tab-pane>
      </el-tabs>
    </MainFlexibleTree>
    <el-dialog :title="dialogType==='create'?'创建映射':dialogType==='edit'?'编辑映射':''"
               :visible.sync="dialogVisible"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="70%" class="dialog-style mapperDialog">
      <el-form ref="mapperFormRef" :model="mapperForm" size="mini" :rules="mapperRules" :inline="true" class="ipt">
        <el-form-item label="档案类型" prop="archivesTypeName">
          <el-select v-model="mapperForm.archivesTypeName" placeholder="请选择档案类型" maxlength="30" clearable>
            <el-option v-for="metaDataItem in archivesTypeNameOptions"
                       :key="metaDataItem.value"
                       :label="metaDataItem.label"
                       :value="metaDataItem.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="映射级别" prop="mapperType">
          <el-select v-model="mapperForm.mapperType" placeholder="请选择映射级别" maxlength="30" clearable>
            <el-option v-for="metaDataItem in mapperTypeOptions"
                       :key="metaDataItem.value"
                       :label="metaDataItem.label"
                       :value="metaDataItem.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
<!--      <div class="tableDes">选择映射关系:</div>-->
      <TablePage class="tablePage"
                 :is-title="true"
                 :tableTitle="tableTitle"
                 :tableData="createMapperData"
                 :isPage="false"
                 :hasSelection="false">
        <template slot="table">
          <el-table-column
            label="字段" prop="fieldName" show-overflow-tooltip width="100">
          </el-table-column>
          <el-table-column
            label="元数据中文名称">
            <template slot-scope="scope">
              <el-select size="mini"
                         v-model="scope.row.metaDataCN"
                         placeholder="请选择元数据中文名称"
                         maxlength="30"
                         clearable
                         @change="handleChangeMeta(scope.row)">
                <el-option v-for="metaDataItem in metaDataCNOptions"
                           :key="metaDataItem.value"
                           :label="metaDataItem.label"
                           :value="metaDataItem.value"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column
            label="元数据名称">
            <template slot-scope="scope">
              <el-input size="mini" v-model="scope.row.metaDataName" disabled></el-input>
            </template>
          </el-table-column>
          <el-table-column
            label="备注">
            <template slot-scope="scope">
              <el-input size="mini" v-model="scope.row.remark"></el-input>
            </template>
          </el-table-column>
        </template>
      </TablePage>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" size="mini" @click="handleSave('mapperFormRef')">保存</el-button>
        <el-button size="mini" @click="dialogVisible=false">取消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import MainFlexibleTree from "@/components/Public/MainBody/MainFlexibleTree.vue";
import TablePage from "@/components/Public/table/TablePage.vue";
import {formatDate} from "@/utils";

export default {
  name: "index",
  components: {TablePage, MainFlexibleTree},

  data() {
    return {
      treeData: [],
      treeDefaultProps: {
        children: 'children',
        label: 'label'
      },
      activeName: 'second',
      queryParams: {
        metaDataName: '',
        mapperDes: '',
        metaDataCN: '',
        archivesType: '',
        pageSize: 20,
        pageNum: 1,
      },
      tableData: [],
      tableColumn: [
        {label: '字段', prop: 'date', width: '100'},
        {label: '元数据名称', prop: 'name', width: '100'},
        {label: '元数据中文名称', prop: 'address', width: '100'},
        {label: '档案类型', prop: 'remarks', width: '100'},
        {label: '创建人', prop: 'remarks', width: '100'},
        {label: '创建时间', prop: 'remarks', width: '100',formatter:formatDate},
        {label: '备注', prop: 'remarks', width: '100'},
      ],
      selected: [], //选中数据
      total: 0,
      dialogType: 'create',
      dialogVisible: false,
      tableTitle:'选择映射关系：',
      mapperForm: {
        metaDataName: '',
        metaDataCN: '',
        metaDataLen: '',
        archivesTypeName: '',
        mapperType: '',
      },
      archivesTypeNameOptions: [
        {
          value: '元数据',
          label: 'varchar'
        }, {
          value: '件号',
          label: 'int'
        }
      ],
      mapperTypeOptions: [
        {
          value: '案卷级',
          label: '案卷级'
        }, {
          value: '文件级',
          label: '文件级'
        }
      ],
      createMapperData: [
        {fieldName: 'attr0', metaDataCN: '题名', metaDataName: ''},
        {fieldName: 'attr1', metaDataCN: '题名', metaDataName: ''},
        {fieldName: 'attr2', metaDataCN: '题名', metaDataName: ''},
        {fieldName: 'attr3', metaDataCN: '题名', metaDataName: ''},
        {fieldName: 'attr4', metaDataCN: '题名', metaDataName: ''},
        {fieldName: 'attr5', metaDataCN: '题名', metaDataName: ''},
        {fieldName: 'attr6', metaDataCN: '题名', metaDataName: ''},
        {fieldName: 'attr7', metaDataCN: '题名', metaDataName: ''},
        {fieldName: 'attr8', metaDataCN: '题名', metaDataName: ''},
        {fieldName: 'attr9', metaDataCN: '题名', metaDataName: ''},
        {fieldName: 'attr10', metaDataCN: '题名', metaDataName: ''},
        {fieldName: 'attr11', metaDataCN: '题名', metaDataName: ''},
        {fieldName: 'attr12', metaDataCN: '题名', metaDataName: ''},
        {fieldName: 'attr13', metaDataCN: '题名', metaDataName: ''},
        {fieldName: 'attr14', metaDataCN: '题名', metaDataName: ''},
        {fieldName: 'attr15', metaDataCN: '题名', metaDataName: ''},
        {fieldName: 'attr16', metaDataCN: '题名', metaDataName: ''},
        {fieldName: 'attr17', metaDataCN: '题名', metaDataName: ''},
        {fieldName: 'attr18', metaDataCN: '题名', metaDataName: ''},
        {fieldName: 'attr19', metaDataCN: '题名', metaDataName: ''},
        {fieldName: 'attr20', metaDataCN: '题名', metaDataName: ''}
      ],
      metaDataCNOptions: [
        {
          label: '题名',
          value: 'timing'
        }, {
          label: '保管期限',
          value: 'baoguanqixian'
        }
      ],

      mapperRules: {
        metaDataName: [
          {required: true, message: "元数据名称不能为空", trigger: "blur"},
        ],
        mapperRange: [
          {required: true, message: "映射范围不能为空", trigger: "change"},
        ],
      },
    }
  },
  created() {
    this.handleQuery();
  },
  methods: {
    /**
     * 单击tab页
     * @param tab
     * @param event
     */
    handleClick(tab, event) {
      console.log(tab, event);
    },
    /**
     * 查询
     */
    handleQuery() {

    },
    /**
     * 创建映射
     */
    handleCreate() {
      this.dialogType = 'create';
      this.dialogVisible = true;
      this.mapperForm = {
        mapperRange: [],
        metaDataName: '',
        mapperDes: '',
        fillCheckList: [],
        metaDataCNs: [{
          value: ''
        }],
      };
    },
    /**
     * 编辑映射
     */
    handleUpdate() {
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
      } else {
        // this.form = JSON.parse(JSON.stringify(this.selected[0]))
        this.dialogType = 'edit';
        this.dialogVisible = true;
      }
    },
    /**
     * 取消映射
     */
    handleCancel() {
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
    /**
     * 分页器
     */
    handleChange(pageSize, pageNum) {
      this.queryParams.pageSize = pageSize;
      this.queryParams.pageNum = pageNum;
      this.handleQuery();
    },
    /**
     * 弹窗表格选中的值发生变化
     */
    handleChangeMeta(row) {
      // console.log(row)
      this.metaDataCNOptions.forEach(item => {
        // if(row.metaDataCN==item.value){
        //   console.log('111')
        //   row.metaDataName=item.value
        // }
        if (!row.metaDataCN) {
          row.metaDataName = ''
        } else if (row.metaDataCN == item.value) {
          row.metaDataName = item.value;
        }

      })
    },
    /**
     * 保存映射弹窗
     */
    handleSave(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert('submit!');
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

.mapperDialog {
  ::v-deep .ipt {
    justify-content: start;

    .el-form-item {
      width: 30%;

      .el-form-item__label {
        width: 65px;
      }
    }
  }
  .tablePage {
    height: calc(85vh - 220px) !important;
  }
}

//.createMapperData{
//  height: calc(85vh - 180px);
//}
</style>

