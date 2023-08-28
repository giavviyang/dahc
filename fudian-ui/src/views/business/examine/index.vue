<template>
  <div class="app-container">
    <MainFlexibleTree :data="treeData" :defaultProps="treeDefaultProps" @handleNodeClick="handleNodeClick">
      <div slot="mainRight" style="height: 100%">
        <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="iptAndBtn"
                 @submit.native.prevent>
          <el-form-item label="核查项名称" prop="trueingName">
            <el-input
              v-model="queryParams.trueingName"
              placeholder="请输入核查项名称"
              @keyup.enter.native="conditionalQueries"
              clearable/>
          </el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="conditionalQueries"
                     v-hasPermi="['business:trueing:list']">搜索
          </el-button>
        </el-form>
        <div class="btn">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleScopeAdd" v-hasPermi="['business:trueingScope:add']">
            新增核查范围
          </el-button>
          <el-button
            type="primary"
            icon="el-icon-edit"
            size="mini"
            @click="handleScopeUpdate" v-hasPermi="['business:trueingScope:edit']">
            编辑核查范围
          </el-button>
          <el-button
            type="primary"
            icon="el-icon-delete"
            size="mini"
            @click="handleScopeDelete" v-hasPermi="['business:trueingScope:delete']">
            删除核查范围
          </el-button>
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd" v-hasPermi="['business:trueing:add']">
            新增核查项
          </el-button>
          <el-button
            type="primary"
            icon="el-icon-edit"
            size="mini"
            @click="handleUpdate" v-hasPermi="['business:trueing:edit']">
            编辑核查项
          </el-button>
          <el-button
            type="primary"
            icon="el-icon-delete"
            size="mini"
            @click="handleDelete" v-hasPermi="['business:trueing:delete']">
            删除核查项
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
              v-for="(item,index) in tableColumn"
              :key="index"
              :prop="item.prop"
              :label="item.label"
              :formatter="item.formatter"
              :min-width="item.width"
              :class-name="item.cellName">
            </el-table-column>
          </template>
        </TablePage>
      </div>
    </MainFlexibleTree>
    <el-dialog :title="dialogTypeScope==='add'?'新增核查范围':dialogTypeScope==='edit'?'编辑核查范围':''"
               :visible.sync="dialogVisibleScope"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="500px" class="dialog-style trueingScope">
      <el-form ref="trueingScopeFormRef" :model="trueingScopeForm" size="mini" :rules="trueingScopeRules" :inline="true"
               class="ipt">
        <el-form-item label="核查范围名称" prop="fullName" class="remarks">
          <el-input v-model="trueingScopeForm.fullName" placeholder="请输入核查范围名称" maxlength="30" clearable/>
        </el-form-item>

        <el-form-item label="所属核查模版" prop="eparentCode" class="remarks">
          <el-cascader filterable v-model="trueingScopeForm.eparentCode" :options="treeDataScope"
                       @change="handleCascaderScope"
                       clearable
                       :disabled="dialogTypeScope==='add'?false:dialogTypeScope==='edit'?true:''"></el-cascader>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" size="mini" @click="handleScopeSave('trueingScopeFormRef')">保存</el-button>
        <el-button size="mini" @click="dialogVisibleScope=false">取消</el-button>
      </span>
    </el-dialog>
    <el-dialog :title="dialogType==='add'?'新增核查项':dialogType==='edit'?'编辑核查项':''"
               :visible.sync="dialogVisible"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="70%" class="dialog-style">
      <el-form ref="trueingFormRef" :model="trueingForm" size="mini" :rules="trueingRules" :inline="true" class="ipt">
        <el-form-item label="核查项名称" prop="trueingName">
          <el-input v-model="trueingForm.trueingName" placeholder="请输入核查项名称" maxlength="30" clearable/>
        </el-form-item>
        <el-form-item label="核查项描述" prop="trueingDesc">
          <el-input v-model="trueingForm.trueingDesc" placeholder="请输入核查项描述" maxlength="30" clearable/>
        </el-form-item>
        <el-form-item label="核查范围" prop="trueingScopeStair">
          <el-cascader
            v-model="trueingForm.trueingScopeStair"
            :options="treeData"
            @change="handleCascader" clearable
            :disabled="dialogType==='add'?false:dialogType==='edit'?true:''"></el-cascader>
        </el-form-item>
        <el-form-item label="填写内容" prop="fillContent">
          <el-checkbox-group v-model="trueingForm.fillCheckList">
            <el-checkbox v-for="(item,index) in fillCheckOptions" :key="index" :label="item.label">
              {{ item.value }}
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item
          class="dynamicIpt"
          v-for="(trueingSta, index) in trueingForm.examineStas"
          :label="'核查标准' + Number(index+1) "
          :key="trueingSta.key"
          :prop="'examineStas.' + index + '.value'"
          :rules="{required: true, message: '核查标准不能为空', trigger: 'blur'}">
          <el-input v-model="trueingSta.value"></el-input>
          <el-button type="primary" size="mini" @click.prevent="addExamineSta(trueingSta)">新增</el-button>
          <el-button :disabled="index==0" size="mini" @click.prevent="removeExamineSta(trueingSta)">移除</el-button>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" size="mini" v-if="dialogType==='add'?true:dialogType==='edit'?false:false"
                   @click="nextHandleSave('trueingFormRef')">下一个</el-button>
        <el-button type="primary" size="mini" @click="handleSave('trueingFormRef')">保存</el-button>
        <el-button size="mini" @click="dialogVisible=false">取消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import MainFlexibleTree from "@/components/Public/MainBody/MainFlexibleTree.vue";
  import TablePage from "@/components/Public/table/TablePage.vue";
  import {
    queryInspectTree,
    queryDictDataTransition,
    auditManagementAdd,
    updateData,
    delData
  } from "@/api/dahc/sys/dictData";
  import {addManagement, listManagement, delManagement, updateManagement} from "@/api/dahc/trueingManage/trueingManage";
  import {addType, updateType} from "@/api/dahc/sys/dictType";

  export default {
    name: "index",
    components: {TablePage, MainFlexibleTree},

    data() {
      var eparentCodeVerify = (rule, value, callback) => {
        if (value == this.trueingScopeStair[2]) {
          callback(new Error('所属核查模版不能为空'));
        } else {
          callback();
        }
      };
      return {
        treeData: [],
        treeDataScope: [],
        treeDefaultProps: {
          children: 'children',
          label: 'label'
        },
        queryParams: {
          trueingName: '',
          pageSize: 20,
          pageNum: 1,
        },
        tableData: [],
        tableColumn: [
          {label: '核查项名称', prop: 'trueingName', width: '60', cellName: 'cellName'},
          {label: '核查项描述', prop: 'trueingDesc', width: '80', cellName: 'cellName'},
          {label: '核查标准', prop: 'examineStasString', width: '100', cellName: 'cellName'},
          {
            label: '所属核查范围',
            prop: 'trueingScopeStair',
            width: '60',
            cellName: 'cellName',
            formatter: this.trueingScopeStairFormatter
          },
          {
            label: '填写内容',
            prop: 'fillCheckList',
            width: '60',
            cellName: 'cellName',
            formatter: this.fillCheckListFormatter
          },
        ],
        selected: [], //选中数据
        total: 0,
        dialogType: 'add',
        dialogTypeScope: 'add',
        dialogVisible: false,
        dialogVisibleScope: false,

        /*核查范围表单数据*/
        trueingScopeForm: {
          fullName: '',
          type: '',
          eparentCode: '',
          // codeProperty: '',
          dictName: '',
        },
        trueingForm: {
          trueingName: '',
          tableType: '',
          trueingDesc: '',
          fillCheckList: [],
          examineStas: [{
            value: ''
          }],
          trueingRemark: '',
          trueingType: '',
          trueingScopeStair: '',
          trueingTreeRank: 0,
          showPageNumber: '1',
          showRecord: '1',
          showPiece: '1',

        },
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
        dialogVisibleImport: false,
        trueingRules: {
          trueingName: [
            {required: true, message: "核查项名称不能为空", trigger: "blur"},
            {max: 20, message: "核查项名称长度最大为20"}
          ],
          trueingScopeStair: [
            {required: true, message: "核查项范围不能为空", trigger: "change"},
          ],
        },
        trueingScopeRules: {
          fullName: [
            {required: true, message: "核查范围名称不能为空", trigger: "blur"},
            {max: 20, message: "核查范围名称长度最大为20"}
          ],
          eparentCode: [
            {required: true, message: "核查范围不能为空", trigger: "blur"},
            {validator: eparentCodeVerify, trigger: "change"},
          ],
        },
        /*点击tree节点id*/
        treeId: '',
        treeRank: 0,
        /*字典类型表单数据*/
        treeFrom: {},
        /*返回状态码*/
        returnState: 20000,
        dictDataTransition: [],
        transitionIds: [],
        nodeArr: [],
        /*核查范围id数组*/
        trueingScopeStair: [],
        eparentCode: []
      };
    },
    created() {
      this.getInspectTree();
      this.getDictDataTransition()
    },
    methods: {
      /*tree点击事件*/
      handleNodeClick(val, node, component) {
        this.queryParams = {pageNum: 1, pageSize: 20}
        this.trueingForm.trueingScopeStair = [];
        this.transitionIds = [];
        const arr = [];
        this.treeRank = val.type;
        this.treeId = val.value;
        this.treeFrom = val;
        /*三级节点*/
        if (val.type == 2) {
          this.trueingForm.trueingScopeStair.push(node.parent.parent.data.value)
          this.trueingForm.trueingScopeStair.push(node.parent.data.value)
          this.trueingForm.trueingScopeStair.push(val.value)
          this.trueingScopeStair = this.trueingForm.trueingScopeStair;
          this.handleQuery();
          this.nodeArr = [];
          this.nodeArr.push(node.parent.parent.data.value)
          this.nodeArr.push(node.parent.data.value)
        } else if (val.type == 0) {
          /*一级节点*/
          val.children.map(item => {
            item.children.map(item1 => {
              arr.push(item1.value)
            });
          });
        } else {
          /*二级节点*/
          val.children.map(item => {
            arr.push(item.value);
          });
        }
        if (arr != []) {
          this.transitionIds = arr;
          this.handleQuery();
        }
      },
      // 获取字典表
      getDictDataTransition() {
        queryDictDataTransition().then(res => {
          this.dictDataTransition = res
        });
      },
      //表格软件类型字段字典转换
      fillCheckListFormatter(row, column, cellValue) {
        let result = '';
        if (row.showPageNumber == '0') {
          result = result + ' 页号；'
        }
        if (row.showRecord == '0') {
          result = result + ' 元数据；'
        }
        if (row.showPiece == '0') {
          result = result + ' 件号；'
        }
        result = result.slice(0, result.length - 1);
        return result
      },
      //表格核查范围字段字典转换
      trueingScopeStairFormatter(row, column, cellValue) {
        let result = '';
        this.dictDataTransition.map((item) => {
          if (item.value == cellValue) {
            result = item.label;
          }
        });
        return result;
      },
      /*条件查询*/
      conditionalQueries() {
        this.queryParams.pageSize = 20;
        this.queryParams.pageNum = 1;
        this.handleQuery();
      },
      /**
       * 查询
       */
      handleQuery() {
        this.queryParams.trueingScopeStair = this.treeId
        this.queryParams.transitionIds = this.transitionIds
        listManagement(this.queryParams).then(response => {
          if (response.code === this.returnState) {
            this.tableData = response.data;
            if (this.tableData) {
              this.tableData.forEach(item => {
                let str = item.examineStasString.replace(/\//g, '；');
                item.examineStasString = str.slice(0, item.examineStasString.length - 1)
              })
            }
            this.total = response.total;
          }
        });
      },
      /*查询字典表tree数据*/
      getInspectTree() {
        queryInspectTree("100").then(res => {
          if (res.code === this.returnState) {
            if (res.data) {
              this.treeData = res.data;
              this.handleQuery();
            }
          }
        });
      },
      /** 转换菜单数据结构 */
      normalizer(node) {
        if (node.children && !node.children.length) {
          delete node.children;
        }
        return {
          id: node.value,
          label: node.label,
          children: node.children
        };
      },
      /**
       * 递归判断列表，把最后的children设为undefined
       * 只显示两级
       * @param data
       * @returns {*}
       */
      getTreeData(data) {
        for (let i = 0; i < data.length; i++) {
          if (data[i].type === 0) {
            // children若不为空数组，则继续 递归调用 本方法
            this.getTreeData(data[i].children);
          } else if (data[i].type === 1) {
            // children若为空数组，则将children设为undefined
            data[i].children = undefined;
          }
        }
        return data;
      },
      handleCascaderScope(val) {
        console.log(val)
        this.trueingScopeForm.eparentCode = val[1];
      },
      /*新增核查范围*/
      handleScopeAdd() {
        this.trueingScopeForm.fullName = '';
        this.treeDataScope = JSON.parse(JSON.stringify(this.treeData))
        this.getTreeData(this.treeDataScope)
        this.dialogVisibleScope = true;
        this.dialogTypeScope = 'add';
        this.reset();
        this.trueingScopeForm.type = 2;
        this.trueingScopeForm.eparentCode = this.treeId;
        this.trueingScopeForm.dictType = '100';
        // }
      },
      /*编辑核查范围*/
      handleScopeUpdate() {
        // console.log('this.nodeArr',this.nodeArr)
        this.treeDataScope = JSON.parse(JSON.stringify(this.treeData))
        this.getTreeData(this.treeDataScope)
        if (this.treeRank != 2) {
          this.$message({
            message: '请选择需要修改的核查范围信息',
            type: 'warning'
          });
        } else {
          this.dialogVisibleScope = true;
          this.dialogTypeScope = 'edit';
          this.trueingScopeForm.fullName = this.treeFrom.label
          this.trueingScopeForm.id = this.treeFrom.value
          this.trueingScopeForm.eparentCode = this.nodeArr
          this.eparentCode = this.trueingScopeForm.eparentCode;
        }
      },
      /*保存核查范围*/
      handleScopeSave(formName) {
        this.$refs[formName].validate(valid => {
          if (valid) {
            if (this.dialogTypeScope === 'edit') {
              this.trueingScopeForm.eparentCode = this.trueingScopeForm.eparentCode[1];
              updateData(this.trueingScopeForm).then(response => {
                if (response.code === 200) {
                  this.$modal.msgSuccess("修改成功");
                  this.dialogVisibleScope = false;
                  this.getInspectTree();
                }
              }).finally(() => {
                this.trueingScopeForm.eparentCode = this.eparentCode;
              });
            } else if (this.dialogTypeScope === 'add') {
              auditManagementAdd(this.trueingScopeForm).then(response => {
                if (response.code === 200) {
                  this.$modal.msgSuccess("新增成功");
                  this.dialogVisibleScope = false;
                  this.trueingScopeForm.fullName = '';
                  this.getInspectTree();
                }
              });
            }
          }
        });
      },
      /*删除核查项范围*/
      handleScopeDelete() {
        if (this.treeRank !== 2) {
          this.$message({
            message: '请选择需要删除的核查范围信息',
            type: 'warning'
          });
        } else {
          this.queryParams.trueingScopeStair = this.treeFrom.value;
          this.queryParams.transitionIds = this.transitionIds;
          listManagement(this.queryParams).then(response => {
            if (response.code === this.returnState) {
              if (!response.data || response.data === [] || response.data.length === 0) {
                this.$confirm('此操作将永久删除核查范围为' + this.treeFrom.label + '的数据，是否继续?', '提示', {
                  confirmButtonText: '确定',
                  cancelButtonText: '取消',
                  type: 'warning',
                }).then(() => {
                  delData(this.treeFrom.value).then(res => {
                    if (res.code == 20000) {
                      this.$message.success("删除成功")
                      this.getInspectTree();
                    }
                  });
                }).catch(() => {
                  this.$message({
                    type: 'info',
                    message: '已取消删除'
                  });
                });
              } else {
                this.$message({
                  message: `当前核查范围为${this.treeFrom.label}的数据下存在核查项数据，不可继续删除`,
                  type: 'warning'
                });
              }
            }
          });

        }
      },
      /**
       * 新增核查项
       */
      handleAdd() {
        this.addReset();
        // this.trueingForm.trueingScopeStair = this.treeId;
        this.trueingForm.trueingTreeRank = 2;
        this.dialogType = 'add';
        this.dialogVisible = true;
      },

      /**
       * 编辑核查项
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
          this.reset();
          Object.assign(this.trueingForm, this.selected[0])
          if (this.selected[0].showPageNumber == "0") {
            this.trueingForm.fillCheckList.push("pagesNum")
          }
          if (this.selected[0].showRecord == "0") {
            this.trueingForm.fillCheckList.push("clauses")
          }
          if (this.selected[0].showPiece == "0") {
            this.trueingForm.fillCheckList.push("itemNum")
          }
          const arr = this.trueingForm.examineStasString.split('；')
          this.trueingForm.examineStas = [];
          arr.map((item, index) => {
            this.trueingForm.examineStas.push({value: ''})
            this.trueingForm.examineStas[index].value = item;
          });
          this.dialogType = 'edit';
          this.dialogVisible = true;
        }
      },
      /*下一个*/
      nextHandleSave(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.trueingScopeStair = this.trueingForm.trueingScopeStair;
            this.trueingForm.trueingScopeStair = this.trueingForm.trueingScopeStair[2];
            for (let i = 0; i < this.trueingForm.fillCheckList.length; i++) {
              if (this.trueingForm.fillCheckList[i] == "clauses") {
                this.trueingForm.showRecord = '0';
              } else if (this.trueingForm.fillCheckList[i] == "itemNum") {
                this.trueingForm.showPiece = '0';
              } else if (this.trueingForm.fillCheckList[i] == "pagesNum") {
                this.trueingForm.showPageNumber = '0';
              }
            }
            addManagement(this.trueingForm).then(res => {
              if (res.code == this.returnState) {
                this.trueingScopeStair = [];
                this.$modal.msgSuccess(res.msg);
                this.handleQuery();
                this.addReset();

              }
            }).finally(() => {
              this.trueingForm.trueingScopeStair = this.trueingScopeStair;
            });
          } else {
            console.log('error submit!!');
            return false;
          }
        })
      },
      /**
       * 保存核查项弹窗
       */
      handleSave(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.dialogType == 'edit') {
              this.trueingForm.showRecord = '1';
              this.trueingForm.showPiece = '1';
              this.trueingForm.showPageNumber = '1';
              for (let i = 0; i < this.trueingForm.fillCheckList.length; i++) {
                if (this.trueingForm.fillCheckList[i] == "clauses") {
                  this.trueingForm.showRecord = '0';
                } else if (this.trueingForm.fillCheckList[i] == "itemNum") {
                  this.trueingForm.showPiece = '0';
                } else if (this.trueingForm.fillCheckList[i] == "pagesNum") {
                  this.trueingForm.showPageNumber = '0';
                }
              }
              this.trueingScopeStair = this.trueingForm.trueingScopeStair
              updateManagement(this.trueingForm).then(response => {
                if (response.code == this.returnState) {
                  this.$modal.msgSuccess("修改成功");
                  this.trueingScopeStair = [];
                  this.dialogVisible = false;
                  this.handleQuery();
                }
              }).finally(() => {
                this.trueingForm.trueingScopeStair = this.trueingScopeStair
              });
            } else {
              this.trueingForm.trueingScopeStair = this.trueingForm.trueingScopeStair[2];
              for (let i = 0; i < this.trueingForm.fillCheckList.length; i++) {
                if (this.trueingForm.fillCheckList[i] == "clauses") {
                  this.trueingForm.showRecord = '0';
                } else if (this.trueingForm.fillCheckList[i] == "itemNum") {
                  this.trueingForm.showPiece = '0';
                } else if (this.trueingForm.fillCheckList[i] == "pagesNum") {
                  this.trueingForm.showPageNumber = '0';
                }
              }
              addManagement(this.trueingForm).then(res => {
                if (res.code == this.returnState) {
                  this.$modal.msgSuccess(res.msg);
                  this.dialogVisible = false;
                  this.trueingScopeStair = [];
                  this.handleQuery();
                }
              }).finally(() => {
                this.trueingForm.trueingScopeStair = this.trueingScopeStair
              });
            }
          } else {
            console.log('error submit!!');
            return false;
          }
        });
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
            delManagement(ids).then(res => {
              this.$message.success(res.msg)
              this.handleQuery();
            })

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
       *
       */
      handleCascader(value) {
        console.log(value)
      },

      /**
       * 新增核查标准
       */
      addExamineSta() {
        this.trueingForm.examineStas.push({
          value: '',
          key: Date.now(),
        });
      },
      /**
       * 移除核查标准
       */
      removeExamineSta(item) {
        let index = this.trueingForm.examineStas.indexOf(item)
        if (index !== -1) {
          this.trueingForm.examineStas.splice(index, 1)
        }
      },
      /*表单重置*/
      reset() {
        this.trueingForm = {
          trueingName: '',
          tableType: '',
          trueingDesc: '',
          fillCheckList: [],
          examineStas: [{
            value: ''
          }],
          trueingRemark: '',
          trueingType: '',
          trueingScopeStair: '',
          trueingTreeRank: 0,
          showPageNumber: '1',
          showRecord: '1',
          showPiece: '1',
        };
      },
      addReset(val) {
        this.trueingForm = {
          trueingName: '',
          tableType: '',
          trueingDesc: '',
          fillCheckList: [],
          examineStas: [{
            value: ''
          }],
          trueingRemark: '',
          trueingType: '',
          trueingScopeStair: this.trueingScopeStair,
          trueingTreeRank: 0,
          showPageNumber: '1',
          showRecord: '1',
          showPiece: '1',
        };
      },

    }
  }
</script>

<style lang="scss" scoped>

  ::v-deep .tablePage {
    height: calc(100% - 100px) !important;

    .cellName {
      .cell {
        word-break: keep-all;
        word-wrap: break-word;
        text-align: left;
      }
    }
  }
</style>
