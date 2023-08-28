<template>
  <div class="app-container">
    <MainFlexibleTree :data="treeData" :defaultProps="treeDefaultProps" @handleNodeClick="handleNodeClick">
      <div slot="mainRight" style="height: 100%">
        <el-form v-model="queryParams" ref="queryForm" size="mini" :inline="true" class="iptAndBtn"
                 @submit.native.prevent>
          <el-form-item label="字典名称" prop="dictName">
            <el-input
              v-model="queryParams.fullName"
              placeholder="请输入字典名称"
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
            @click="handleAddType">
            新增类型
          </el-button>
          <el-button
            type="primary"
            icon="el-icon-edit"
            size="mini"
            @click="handleUpdateType">
            编辑类型
          </el-button>
          <el-button
            type="primary"
            icon="el-icon-delete"
            size="mini"
            @click="handleDeleteType">
            删除类型
          </el-button>
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd">
            新增字典
          </el-button>
        </div>
        <TablePage class="tablePage"
                   :tableData="tableData"
                   :hasIndex="false"
                   :pageSize="queryParams.pageSize"
                   :pageNum="queryParams.pageNum"
                   :total="total"
                   :rowId="rowId"
                   :treeProps="treeProps"
                   :isExpandAll="isExpandAll"
                   :hasSelection="false"
                   @handleChange="handleChange">
          <template slot="table">
            <el-table-column
              type="index"
              label="序号"
              width="55" show-overflow-tooltip class-name="textLeft">
              <template slot-scope="scope">
                   {{ scope.row.rowIndex }}
              </template>
            </el-table-column>
            <el-table-column
              show-overflow-tooltip
              v-for="(item,index) in tableColumn"
              :key="index"
              :formatter="item.formatter"
              :prop="item.prop"
              :label="item.label"
              :min-width="item.width"
            :class-name="item.className">

            </el-table-column>
            <el-table-column width="200" label="操作" align="center" show-overflow-tooltip class-name="operation">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  @click="handleAdd(scope.row)"
                  v-hasPermi="['system:menu:add']"
                >新增
                </el-button>
                <el-button
                  size="mini"
                  type="text"
                  @click="handleUpdate(scope.row)"
                  v-hasPermi="['system:menu:edit']"
                >编辑
                </el-button>
                <el-button
                  size="mini"
                  type="text"
                  @click="handleDelete(scope.row)"
                  v-hasPermi="['system:menu:remove']"
                >删除
                </el-button>
              </template>
            </el-table-column>
          </template>
        </TablePage>
      </div>
    </MainFlexibleTree>
    <el-dialog :title="dialogTypeLeiXing==='add'?'新增字典类型':dialogTypeLeiXing==='edit'?'编辑字典类型':''"
               :visible.sync="dialogVisibleDictType"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="450px" class="dialog-style">
      <el-form ref="dictTypeFormRef" :model="dictTypeForm" size="mini" :rules="dictTypeRules" :inline="true"
               class="ipt">
        <el-form-item label="字典类型名称" prop="dictName" class="remarks">
          <el-input v-model="dictTypeForm.dictName" placeholder="请输入字典类型名称" maxlength="30" clearable/>
        </el-form-item>
        <!--        <el-form-item label="上级类型" prop="dictName" class="remarks">-->
        <!--          <el-input v-model="dictTypeForm.dictName" placeholder="请输入字典类型名称" maxlength="30" clearable/>-->
        <!--        </el-form-item>-->
        <el-form-item label="备注" prop="remark" class="remarks">
          <el-input v-model="dictTypeForm.remark" type="textarea" placeholder="请输入内容" maxlength="30" clearable/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" size="mini" @click="handleDictTypeSave('dictTypeFormRef')">保存</el-button>
        <el-button size="mini" @click="dialogVisibleDictType=false">取消</el-button>
      </span>
    </el-dialog>
    <el-dialog :title="dialogType==='add'?'新增字典':dialogType==='edit'?'编辑字典':''"
               :visible.sync="dialogVisible"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="700px" class="dialog-style">
      <el-form ref="dictFormRef" :model="dictForm" size="mini" :rules="dictRules" :inline="true" class="ipt">
        <el-form-item label="字典名称" prop="fullName">
          <el-input v-model="dictForm.fullName" placeholder="请输入字典名称" maxlength="30" clearable/>
        </el-form-item>
        <el-form-item label="代码类型" prop="dictType">
          <el-select v-model="dictForm.dictType" placeholder="请选择" :disabled="dictForm.dictType ===treeId||dictForm.type === 1||dialogType==='edit'">
            <el-option
              v-for="item in dictTypeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="代码属性" prop="codeProperty">
          <el-input v-model="dictForm.codeProperty" placeholder="请输入代码属性" maxlength="50" clearable :disabled="dictForm.codeProperty.includes('physicalFile')||dictForm.codeProperty.includes('electReferPhy')||dictForm.codeProperty.includes('electronic')"/>
        </el-form-item>
        <el-form-item label="级别" prop="type">
          <el-select v-model="dictForm.type" placeholder="请选择" disabled>
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark" class="remarks">
          <el-input v-model="dictForm.remark" type="textarea" placeholder="请输入内容" maxlength="30" clearable/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" size="mini" @click="handleSave('dictFormRef')">保存</el-button>
        <el-button size="mini" @click="dialogVisible=false">取消</el-button>
      </span>
    </el-dialog>


  </div>
</template>

<script>
import MainFlexibleTree from "@/components/Public/MainBody/MainFlexibleTree.vue";
import TablePage from "@/components/Public/table/TablePage.vue";
import {listType, addType, updateType, delType, queryDictType} from "@/api/dahc/sys/dictType";
import {listAllData, listData, addData, delData, updateData, getData} from "@/api/dahc/sys/dictData";
import {queryMetadataSelect} from "@/api/business/metadata";
// import {groupBy} from "@/utils/groupBy";

//分页大小
const params = {
  pageNum: 1,
  pageSize: 20
}
export default {
  // name: "index",
  components: {TablePage, MainFlexibleTree},

  data() {
    return {
      treeData: [
        {
          label: '字典类型',
          children: []
        }
      ],
      treeDefaultProps: {
        children: 'children',
        label: 'label'
      },
      queryParams: {
        fullName: '',
        dictName: '',
        dictType: '',
        dictSta: '',
        archivesType: '',
        pageSize: 20,
        pageNum: 1,
      },
      tableData: [],
      tableColumn: [
        {label: '字典名称', prop: 'fullName', width: '100',className:"textLeft"},
        {label: '代码类型', prop: 'dictType', width: '100', formatter: this.dictTypeFormatter},
        {label: '代码属性', prop: 'codeProperty', width: '120',formatter: this.codePropertyFormatter},
        {label: '级别', prop: 'type', width: '60', formatter: this.typeFormatter},
        {label: '备注', prop: 'remark', width: '60'},
      ],
      // selected: [], //选中数据
      // 是否展开，默认全部折叠
      isExpandAll: false,
      rowId: 'id',
      treeProps: {children: 'children', hasChildren: 'hasChildren'},
      total: 0,
      dialogType: 'add',
      /*字典类型弹窗名称*/
      dialogTypeLeiXing: 'add',
      /*字典类型控制弹窗*/
      dialogVisibleDictType: false,
      dialogVisible: false,
      /*字典类型data数据*/
      dictTypeForm: {
        dictId: '',
        dictName: '',
        dictType: '',
        status: 0,
        remark: ''
      },
      dictForm: {
        fullName: '',
        type: '',
        eparentCode: '',
        codeProperty: '',
        dictType:'',
        dictName: '',
      },
      statusCode: 20000,
      fillCheckOptions: [
        {
          value: '元数据',
          label: 'clauses'
        }, {
          value: '件号',
          label: 'itemNum'
        }, {
          value: '页号',
          label: 'pagesNum'
        },
      ],
      options: [{
        value: 0,
        label: '父级'
      }, {
        value: 1,
        label: '子级'
      }],
      dictTypeOptions: [],
      dialogVisibleImport: false,
      /*文本框验证*/
      dictTypeRules: {
        dictName: [
          {required: true, message: "字典类型名称不能为空", trigger: "blur"}
        ],
      },
      dictRules: {
        // number: [
        //   // {type: 'number', message: '请输入整数', trigger: 'blur' }
        //   {pattern: /^[1-9]\d*$/, message: '请输入整数'}
        // ],
        // area: [
        //   // {validator: checkArea, change: 'blur'}
        //   {pattern: /^[+]?(0|([1-9]\d*))(\.\d+)?$/g, message: '请输入整数或小数'}
        // ],
        // weight: [
        //   {pattern: /^[+]?(0|([1-9]\d*))(\.\d+)?$/g, message: '请输入整数或小数'}
        // ],
        // deptName: [
        //   {required: true, message: "部门名称不能为空", trigger: "blur"}
        // ],
        // orderNum: [
        //   {required: true, message: "排序不能为空", trigger: "blur"},
        //   {type: 'number', message: '排序必须为数值', trigger: "blur"},
        //   {pattern: /^[0-9]*$/, message: '排序必须为正整数', trigger: "blur"},
        // ],
        fullName: [
          {required: true, message: "字典名称不能为空", trigger: "blur"},
          // {type: 'number', message: '排序必须为数值', trigger: "blur"},
          // {pattern: /^[0-9]*$/, message: '排序必须为正整数', trigger: "blur"},
        ],
        dictType: [
          {required: true, message: "代码类型不能为空", trigger: "blur"},
        ],
      },
      /*点击tree节点id*/
      treeId: '',
      treeName: '',
      /*字典类型表单数据*/
      treeFrom: {},
      dictTypeTableTransition: [],
      metadataSelect: []
    };
  },
  created() {
    this.treeHandleQuery();
    this.queryDictType();
    this.queryMetadataSelect();

  },
  methods: {
    queryMetadataSelect() {
      queryMetadataSelect().then(res => {
        this.metadataSelect = res;
      });
    },
    /*查询字典类型转换*/
    queryDictType() {
      queryDictType().then(res => {
        this.dictTypeTableTransition = res.map((item) => {
          return item;
        });
        this.dictTypeOptions = this.dictTypeTableTransition
        console.log(this.dictTypeOptions,"转换")
      });
      // console.log(this.dictTypeOptions,"dd")
    },
    //表格软件类型字段字典转换
    codePropertyFormatter(row, column, cellValue) {
      let result
      if (row.dictType == "102" || row.dictType == "101") {
        this.metadataSelect.map((item) => {
          if (item.value == cellValue) {
            result = item.label;
          }
        });
      } else {
        result = row.codeProperty
      }
      return result;
    },

    //表格软件类型字段字典转换
    typeFormatter(row, column, cellValue) {
      let result
      if (cellValue == '0') {
        result = '父级';
      } else if (cellValue == '1') {
        result = '子级'
      }
      return result;
    },
    //表格字典类型字段字典转换
    dictTypeFormatter(row, column, cellValue) {
      let result
      this.dictTypeTableTransition.map((item) => {
        if (item.value == cellValue) {
          result = item.label;
        }
      });
      return result;
    },
    /*进入页面首次查询*/
    listAllData() {
      listAllData(this.queryParams).then(res => {
        if (res.code == this.statusCode) {
          this.tableData = res.data;
          this.total = res.total;
          if (this.tableData) {
            this.tableData.forEach((item, index) => {
              item.rowIndex = (this.queryParams.pageNum - 1) * this.queryParams.pageSize + index + 1
              if (item.children) {
                item.children.map((childItem, index) => {
                  childItem.rowIndex = `${item.rowIndex}.${index + 1}`
                })
              }
            })
          }
        }

      });
    },
    /**
     * 初始查询
     */
    handleQuery() {
      this.queryParams.dictType = this.treeId;
      listData(this.queryParams).then(res => {
        if (res.code == this.statusCode) {
          this.tableData = res.data;
          this.total = res.total;
          if (this.tableData) {
            this.tableData.forEach((item, index) => {
              console.log((this.queryParams.pageNum - 1) * this.queryParams.pageSize + index + 1)
              item.rowIndex = (this.queryParams.pageNum - 1) * this.queryParams.pageSize + index + 1
              if (item.children) {
                item.children.map((childItem, index) => {
                  childItem.rowChildIndex = `${item.rowIndex}.${index + 1}`
                })
              }
            })
          }
        }
      });
    },
    /**
     * tree查询
     */
    treeHandleQuery() {
      listType().then(res => {
        if (res.code == this.statusCode) {
          this.treeData[0].children = res.data;
          console.log(res.data,"哈哈哈哈")
          // this.treeId = res.data[0].value;
          // this.treeName = res.data[0].label;
          // this.treeFrom = res.data[0];
          this.listAllData();
          this.queryDictType();
        }
      });
    },
    /*tree点击事件*/
    handleNodeClick(val, node, component) {
      this.treeId = val.value;
      this.treeFrom = val;
      this.handleQuery()
    },
    /*保存字典类型*/
    handleDictTypeSave(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          if (this.dialogTypeLeiXing != 'add') {
            updateType(this.dictTypeForm).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.dialogVisibleDictType = false;
              this.treeHandleQuery();
            });
          } else {
            addType(this.dictTypeForm).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.dialogVisibleDictType = false;
              this.treeHandleQuery();
            });
          }
        }
      });
    },
    /**
     * 新增字典类型
     */
    handleAddType() {
      this.dictTypeReset()
      this.dialogTypeLeiXing = 'add';
      this.dialogVisibleDictType = true;
    },
    /**
     * 编辑字典类型
     */
    handleUpdateType() {
      if (this.treeId.length === 0) {
        this.$message({
          message: '请在左侧字典类型树选择需要修改的字典类型节点',
          type: 'warning'
        });
      } else {
        this.dictTypeReset()
        this.dialogTypeLeiXing = 'edit';
        this.dialogVisibleDictType = true;
        this.dictTypeForm.dictName = this.treeFrom.label;
        this.dictTypeForm.dictId = this.treeFrom.value;
        this.dictTypeForm.remark = this.treeFrom.remark;
      }
    },
    /**
     * 删除字典类型
     */
    handleDeleteType() {
      if (this.treeId === undefined) {
        this.$message({
          message: '请选择字典类型子级节点',
          type: 'warning'
        });
      } else if (this.treeId.length === 0) {
        this.$message({
          message: '请选择一条数据',
          type: 'warning'
        });
      } else {
        this.$confirm('此操作将永久删除选中数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          const dictId = this.treeId;
          delType(dictId).then(res => {
            if (res.code == 200) {
              this.$message.success("删除成功")
              this.treeHandleQuery();
              this.treeId = '';
            }
          });
        });
      }
    },
    /**
     * 新增字典
     */
    handleAdd(row) {
      this.dialogType = 'add';
      this.dictForm = {
        fullName: '',
        type: '',
        codeProperty: '',
        dictName: '',
      };
      console.log(row,"新增弹窗")
      if (row != null && row.id) {
        if (row.type === 0) {
          this.dictForm.type = 1;
          this.dictForm.eparentCode = row.id
          this.dialogVisible = true;
          this.dictForm.dictType = row.dictType
         }else {
          this.$message({
            message: '子级不允许添加',
            type: 'warning'
          });
        }
      }else {
          this.dictForm.type = 0;
        this.dictForm.eparentCode = '-'
        this.dialogVisible = true;
        if(this.treeId){
          this.dictForm.dictType = this.treeId
          console.log(this.treeId,"treeId ")
        }
      }
    },
    /**
     * 编辑字典
     */
    handleUpdate(row) {
        this.dictForm = {
          fullName: '',
          type: '',
          codeProperty: '',
          dictName: '',
        };
        getData(row.id).then(response => {
          this.dictForm = response.data;
          this.metadataSelect.map(item => {
            if (item.value == this.dictForm.codeProperty) {
              this.dictForm.codeProperty = item.label;
            }
          });
          this.dialogType = 'edit';
          this.dialogVisible = true;
        });
      },
    /**
     * 删除字典
     */
    handleDelete(row) {
        this.$confirm('此操作将永久删除选中数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          const roleIds = row.id
          delData(roleIds).then((res) => {
            if (res.code == 20000) {
              this.$message.success("删除成功");
              if (this.treeId === undefined || this.treeId.length === 0) {
                this.listAllData();
              } else {
                this.handleQuery();
              }
            }
          });

        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
    },
    // /**
    //  * 表格复选框
    //  */
    // handleSelectionChange(selection) {
    //   this.selected = selection
    // },
    /**
     * 分页器
     */
    handleChange(pageSize, pageNum) {
      this.queryParams.pageSize = pageSize;
      this.queryParams.pageNum = pageNum;
      this.handleQuery();
    },
    /**
     *
     */
    handleCascader(value) {
      console.log(value)
    },
    /**
     * 保存字典弹窗
     */
    handleSave(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.dialogType == 'edit') {
            updateData(this.dictForm).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.dialogVisible = false;
              if (this.treeId === undefined || this.treeId.length === 0) {
                this.listAllData();
              } else {
                this.handleQuery();
              }
            });
          } else {
            addData(this.dictForm).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.dialogVisible = false;
              if (this.treeId === undefined || this.treeId.length === 0) {
                this.listAllData();
              } else {
                this.handleQuery();
              }
            });
          }
        }
      });
    },
    /*表单重置*/
    dictTypeReset() {
      this.dictTypeForm = {
        dictId: null,
        dictName: null,
        dictType: null,
        status: 0,
        remark: null
      };
      this.resetForm("form");
    },}

  }
</script>

<style lang="scss" scoped>

.tablePage {
  height: calc(100% - 100px);
}
</style>
