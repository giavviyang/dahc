<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="iptAndBtn">
      <el-form-item label="项目名称" prop="projectName">
        <el-input
          v-model="queryParams.projectTableName"
          placeholder="请输入项目名称"
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
        @click="handleAdd">
        新增项目
      </el-button>
      <el-button
        type="primary"
        icon="el-icon-edit"
        size="mini"
        @click="handleUpdate">
        编辑项目
      </el-button>
      <el-button
        type="primary"
        icon="el-icon-delete"
        size="mini"
        @click="handleDelete">
        删除项目
      </el-button>
      <el-button
        type="primary"
        icon="el-icon-delete"
        size="mini"
        @click="handleDefine">
        定义工序
      </el-button>
      <el-button
        type="primary"
        icon="el-icon-delete"
        size="mini"
        @click="handleStart">
        开始核查
      </el-button>
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
          :formatter="item.formatter"
          :label="item.label"
          :min-width="item.width">
        </el-table-column>
      </template>
    </TablePage>
    <TablePage class="tablePage"
               :hasSelection="false"
               :is-title="true"
               :tableTitle="tableProcessTitle"
               :tableData="processData"
               :pageSize="queryProcessParams.processSize"
               :pageNum="queryProcessParams.processNum"
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
          width="270" class-name="operation">
          <template slot-scope="scope">
            <el-button @click="handleExamineDetail(scope.row)"
                       type="text" size="mini">查看核查项
            </el-button>
            <el-button @click="moveUpdateProcess(scope.row)"
                       type="text" size="mini">编辑工序
            </el-button>
            <el-button @click="moveUpTable(processData,scope.row, scope.$index)"
                       :disabled="scope.$index === 0" type="text" size="mini">上移
            </el-button>
            <el-button @click="moveDownTable(processData,scope.row, scope.$index)"
                       :disabled="getFormLength(scope.$index,processData)" type="text" size="mini">下移
            </el-button>
            <el-button type="text" size="mini" @click="deleteItem(processData,scope.row,scope.$index)">移除</el-button>
          </template>
        </el-table-column>
      </template>
    </TablePage>
    <el-dialog :title="dialogType==='add'?'新增项目':dialogType==='edit'?'编辑项目':''"
               :visible.sync="dialogVisible"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="50%" class="dialog-style">
      <el-form ref="projectFormRef" :model="projectForm" size="mini" :rules="projectRules" :inline="true" class="ipt">
        <el-form-item label="项目名称" prop="projectName">
          <el-input v-model="projectForm.projectName" placeholder="请输入项目名称" maxlength="30" clearable/>
        </el-form-item>
        <el-form-item label="项目描述" prop="projectDes">
          <el-input v-model="projectForm.projectDesc" placeholder="请输入项目描述" maxlength="30" clearable/>
        </el-form-item>
        <el-form-item label="档案类型" prop="projectDes">
          <el-select v-model="projectForm.archiveTypeId" placeholder="请选择档案类型" maxlength="30" >
            <el-option v-for="metaDataItem in archivesTypeNameOptions"
                       :key="metaDataItem.value"
                       :label="metaDataItem.label"
                       :value="metaDataItem.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="预计开始时间" prop="projectDes">
          <el-date-picker
            v-model="projectForm.startTime"
            type="datetime"
            placeholder="选择日期时间"
            align="right">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="预计结束时间" prop="projectDes">
          <el-date-picker
            v-model="projectForm.endTime"
            type="datetime"
            placeholder="选择日期时间"
            align="right">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remarks" class="remarks">
          <el-input v-model="projectForm.remarks" placeholder="请输入备注" maxlength="30" clearable type="textarea"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" size="mini" @click="handleSave('projectFormRef')">保存</el-button>
        <el-button size="mini" @click="dialogVisible=false">取消</el-button>
      </span>
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
                 :pageSize="queryExamineParams.examineSize"
                 :pageNum="queryExamineParams.examineNum"
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
            :formatter="item.formatter"
            :min-width="item.width">
          </el-table-column>
        </template>
      </TablePage>
    </el-dialog>
    <el-dialog title="编辑工序"
               :visible.sync="dialogUpdateProcess"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="85%" class="dialog-style processDialog">
      <el-form ref="processFormRef" :model="processForm" size="mini" :rules="processRules" :inline="true" class="ipt">
        <el-form-item label="工序排序" prop="sort">
          <el-input v-model="processForm.sort" placeholder="请输入工序排序" maxlength="30" clearable/>
          <!--          <el-input-number v-model="processForm.processSort"  :min="1" :max="10" label="描述文字"></el-input-number>-->
        </el-form-item>
        <el-form-item label="工序名称" prop="procedureName">
          <el-input v-model="processForm.procedureName" placeholder="请输入工序名称" maxlength="30" clearable/>
        </el-form-item>
        <el-form-item label="核查模版" prop="trueingId">
          <el-cascader
            disabled
            v-model="processForm.trueingId"
            :options="examineTemplateData"
            :props="{ expandTrigger: 'hover' }"
            @change="handleCascader"></el-cascader>
        </el-form-item>
        <el-form-item label="工序描述" prop="procedureDesc">
          <el-input v-model="processForm.procedureDesc" placeholder="请输入工序描述" maxlength="30" clearable/>
        </el-form-item>
      </el-form>
      <div class="tableDes">可选择核查项</div>
      <TablePage class="tablePage"
                 :hasSelection="false"
                 :tableData="noSelectData"
                 :isPage="false">
        <template slot="table">
          <el-table-column
            show-overflow-tooltip
            v-for="(item,index) in checkItemColumn"
            :key="index"
            :prop="item.prop"
            :label="item.label"
            :formatter="item.formatter"
            :min-width="item.width">
          </el-table-column>
          <el-table-column
            label="操作"  fixed="right"
            width="100" class-name="operation">
            <template slot-scope="scope">
              <el-button @click="moveSelect(noSelectData,scope.row, scope.$index,haveSelectData)" type="text"
                         size="mini">选择
              </el-button>
            </template>
          </el-table-column>
        </template>
      </TablePage>
      <div class="tableDes">已选择核查项</div>
      <TablePage class="tablePage"
                 :hasSelection="false"
                 :tableData="haveSelectData"
                 :isPage="false">
        <template slot="table">
          <el-table-column
            show-overflow-tooltip
            v-for="(item,index) in checkItemColumn"
            :key="index"
            :prop="item.prop"
            :label="item.label"
            :formatter="item.formatter"
            :min-width="item.width">
          </el-table-column>
          <el-table-column
            label="操作"  fixed="right"
            width="150" class-name="operation">
            <template slot-scope="scope">
              <el-button type="text" size="mini"
                         @click="removeSelect(haveSelectData,scope.row,scope.$index,noSelectData)">取消选择
              </el-button>
              <el-button @click="moveUp(haveSelectData,scope.row, scope.$index)"
                         :disabled="scope.$index === 0" type="text" size="mini">上移
              </el-button>
              <el-button @click="moveDown(haveSelectData,scope.row, scope.$index)"
                         :disabled="getFormLength(scope.$index,haveSelectData)" type="text" size="mini">下移
              </el-button>
            </template>
          </el-table-column>
        </template>
      </TablePage>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" size="mini" @click="handleProcessSave('processFormRef')">保存</el-button>
        <el-button size="mini" @click="dialogUpdateProcess=false">取消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import moment from 'moment'
  import TablePage from "@/components/Public/table/TablePage.vue";
  import {
    search,
    addProjectTable,
    batchDeleteByIdsProjectTable,
    updateProjectTable,
    startInspect
  } from "@/api/projectManage/projectInitialize"
  import {
    addProcedure, batchDeleteByIdsProcedure, procedureInspectUpAndDown, procedureUpAndDown,
    queryInspectTableList,
    searchProcedureList,
    searchProcedureUnselectedList, updateProcedure
  } from "@/api/projectManage/projectProcedure";
  import {queryDictDataTransition, queryInspectTree} from "@/api/dahc/sys/dictData";
  import {queryArchiveTransition} from "@/api/business/archiveType";

  export default {
    name: "haveNoStart",
    components: {TablePage},

    data() {
      return {
        queryParams: {
          projectTableName: '',
          pageSize: 20,
          pageNum: 1,
          projectState: 0
        },
        /*工序表格参数*/
        queryProcessParams: {
          projectId: '',
          pageSize: 20,
          pageNum: 1,
        },
        archivesTypeNameOptions: [],
        /*核查项表格参数*/
        queryExamineParams: {
          id: '',
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
          // {label: '备注', prop: 'remarks', width: '100'},
        ],
        selected: [], //选中数据
        projectTotal: 0,
        tableProcessTitle: '工序信息：',
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
          {label: '工序名称', prop: 'procedureName', width: '100'},
          {label: '工序描述', prop: 'procedureDesc', width: '100'},
          {label: '核查项数量', prop: 'inspectNumber', width: '100'},
          {label: '创建时间', prop: 'createTime', width: '100'},
          {label: '可参与人数', prop: 'remarks', width: '100'},
          {label: '可参与人员', prop: 'remarks', width: '100'},
          {label: '排序', prop: 'sort', width: '100'},
        ],
        processTotal: 0,
        processSize: 10,
        processNum: 1,
        dialogType: 'add',
        dialogVisible: false,
        projectForm: {
          projectName: '',
          projectDesc: '',
          archiveTypeId: '',
          startTime: '',
          endTime: '',
          remarks: '',
          projectState: 0,
        },
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
          {label: '核查项名称', prop: 'trueingName', width: '100'},
          {label: '核查项描述', prop: 'trueingDesc', width: '100'},
          {label: '核查标准', prop: 'examineStasString', width: '100'},
          {label: '核查范围', prop: 'trueingScopeStair', width: '100', formatter: this.trueingScopeStairFormatter},
        ],
        examineTotal: 0,
        examineSize: 10,
        examineNum: 1,
        dialogUpdateProcess: false,
        processForm: {
          procedureDesc: '',
          procedureName: '',
          sort: '',
          projectId: '',
          trueingId: '',
          examineTemplate: '',
          dahcHcxTrueingManageList: []
        },
        noSelectData: [{
          date: '2016-05-0111',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄'
        }, {
          date: '2016-05-0222',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄'
        }],
        haveSelectData: [{
          date: '2016-05-0333',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄'
        }, {
          date: '2016-05-04444',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄'
        }, {
          date: '2016-05-0555',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄'
        }, {
          date: '2016-05-0666',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄'
        }, {
          date: '2016-05-0777',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄'
        }],
        examineTemplateData: [],
        checkItemColumn: [
          {label: '核查项名称', prop: 'trueingName', width: '100'},
          {label: '核查项描述', prop: 'trueingDesc', width: '100'},
          {label: '核查标准', prop: 'examineStasString', width: '100'},
          {label: '核查范围', prop: 'trueingScopeStair', width: '100', formatter: this.trueingScopeStairFormatter},
        ],
        checkItemTotal: 0,
        checkItemSize: 10,
        checkItemNum: 1,
        projectRules: {},
        processRules: {
          procedureName: [
            {required: true, message: "工序名称不能为空", trigger: "blur"},
          ],
          trueingId: [
            {required: true, message: "核查模板不能为空", trigger: "blur"},
          ],
          sort: [
            {required: true, message: "排序不能为空", trigger: "blur"},
            {
              pattern: /^(0|[1-9][0-9]*)$/,
              message: '请输入数字',
              trigger: 'blur'
            }
          ],
        },
        returnState: 20000,
        /*核查范围字段*/
        dictDataTransition: [],
      }
    },
    created() {
      this.handleQuery();
      this.queryDictDataTransition()
      this.queryArchiveTransition();
    },
    methods: {
      /*表格档案类型转换*/
      archiveTypeIdFormatter(row, column, cellValue) {
        let result = '';
        this.archivesTypeNameOptions.map((item) => {
          if (item.value == cellValue) {
            result = item.label;
          }
        });
        return result;
      },
      /*表格时间类型转换*/
      startTimeFormatter(row, column, cellValue) {
        var date = row[column.property];
        if (date == undefined) {
          return "";
        }
        return moment(date).format("YYYY-MM-DD HH:mm:ss");
      },
      endTimeFormatter(row, column, cellValue) {
        var date = row[column.property];
        if (date == undefined) {
          return "";
        }
        return moment(date).format("YYYY-MM-DD HH:mm:ss");
      },
      /*项目表格状态字典转换*/
      projectStateFormatter(row, column, cellValue) {
        let result = '';
        if (cellValue == 0) {
          result = ' 未开始 '
        } else if (row.showRecord == 1) {
          result = ' 核查中 '
        }
        if (row.showPiece == 2) {
          result = ' 核查完成 '
        }
        return result;
      },
      /**
       * 项目查询
       */
      handleQuery() {
        search(this.queryParams).then(res => {
          if (res.code == this.returnState) {
            this.projectData = res.data;
            this.projectTotal = res.total;
            /*查询工序*/
            this.queryProcessParams.projectId = res.data[0].id
            this.searchProcedureList()
          }
        });
      },
      /*档案类型下拉框*/
      queryArchiveTransition() {
        queryArchiveTransition().then(res => {
          this.archivesTypeNameOptions = res.data
        });
      },
      /**
       * 新增项目
       */
      handleAdd() {
        this.projectReset();
        this.dialogType = 'add';
        this.dialogVisible = true;
        // this.projectForm = {};
      },
      /*重置项目表单*/
      projectReset() {
        this.projectForm = {
          projectName: '',
          projectDesc: '',
          archiveTypeId: '',
          startTime: '',
          endTime: '',
          remarks: '',
          projectState: 0,
        };
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
          this.projectReset();
          this.dialogType = 'edit';
          this.dialogVisible = true;
          console.log(this.selected[0],this.archivesTypeNameOptions)
          Object.assign(this.projectForm, this.selected[0]);
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
            batchDeleteByIdsProjectTable(ids).then(res => {
              if (res.code == this.returnState) {
                this.$message.success("删除成功")
                this.dialogVisible = false;
                this.handleQuery();
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
       * 定义工序
       */
      handleDefine() {
        if (!this.selected || this.selected === [] || this.selected.length === 0) {
          this.$message({
            message: '请至少选择一条数据',
            type: 'warning'
          });
        } else {
          // this.$router.push('/projectManage/definingProcess');
          this.$router.push({path: '/projectManage/definingProcess', query: {id: this.selected[0].id}});
        }
      },
      /**
       * 开始核查
       */
      handleStart() {
        if (!this.selected || this.selected === [] || this.selected.length === 0) {
          this.$message({
            message: '请至少选择一条数据',
            type: 'warning'
          });
        } else {
          this.$confirm('此操作将开始当前项目的核查任务, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }).then(() => {
            this.selected[0].projectState = 1;
            startInspect(this.selected[0]).then(res => {
              if (res.code == this.returnState) {
                this.$message.success("修改成功")
                this.handleQuery();
              }
            });

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


      /**
       * 分页器
       */
      handleProjectChange(pageSize, pageNum) {
        // this.queryParams.projectSize = pageSize;
        // this.queryParams.projectNum = pageNum;
        this.queryParams.pageSize = pageSize;
        this.queryParams.pageNum = pageNum;
        this.handleQuery();
      },


      // 选择
      moveSelect(arr1, item, index, arr2) {
        arr1.splice(index, 1);
        arr2.push(item);
        console.log('arr1', arr1);
        console.log('arr2', arr2)
      },
      // 取消选择
      removeSelect(arr1, item, index, arr2) {
        arr1.splice(index, 1);
        arr2.push(item);
        console.log('arr1', arr1);
        console.log('arr2', arr2)
      },
      // 上移
      moveUp(arr, item, index) {
        arr.splice(index - 1, 0, item);  // 定位到点击位置的上一行，0即不删除如何元素，在此位置插入item
        arr.splice(index + 1, 1); // 此时数组中有重复元素，把原来被挤下去的元素删除
      },
      // 下移
      moveDown(arr, item, index) {
        arr.splice(index + 2, 0, item);
        arr.splice(index, 1);
      },

      // 控制下移按钮的显示于隐藏
      getFormLength(index, arr) {
        if (index === arr.length - 1) {
          return true;
        } else {
          return false;
        }
      },

      /**
       *
       */
      handleCascader(value) {
        console.log(value)
      },
      /**
       * 保存项目数据
       */
      handleSave(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.dialogType == 'edit') {
              updateProjectTable(this.projectForm).then(res => {
                if (res.code == this.returnState) {
                  this.$message.success("修改成功")
                  this.dialogVisible = false;
                  this.handleQuery();
                }
              });
            } else {
              addProjectTable(this.projectForm).then(res => {
                if (res.code == this.returnState) {
                  this.$message.success("新增成功")
                  this.dialogVisible = false;
                  this.handleQuery();
                }
              });
            }
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },

      /***********************************************************下方表格工序--------开始*****************************************/
      /*表格点击事件*/
      handleProjectClick(row, column, event) {
        console.log(row, column, event)
        this.procedureId = row.row.id
        this.queryProcessParams.projectId=  row.row.id
        this.searchProcedureList();
      },
      /*工序初始查询*/
      searchProcedureList() {
        searchProcedureList(this.queryProcessParams).then(res => {
          this.processData = res.data
          this.processTotal = res.total
        });
      },
      /*分页*/
      handleProcessChange(pageSize, pageNum) {
        this.queryProcessParams.pageSize = pageSize;
        this.queryProcessParams.pageNum = pageNum;
        this.searchProcedureList()
      },
      /**
       * 查看核查项详情
       */
      handleExamineDetail(item) {
        this.dialogExamine = true;
        this.queryExamineParams.id = item.id
        this.queryInspectTableList();
      },
      /*查看核查项分页*/
      handleExamineChange() {
        this.queryExamineParams.pageSize = pageSize;
        this.queryExamineParams.pageNum = pageNum;
        this.queryInspectTableList();
      },
      /*查看核查项*/
      queryInspectTableList() {
        queryInspectTableList(this.queryExamineParams).then(res => {
          this.examineData = res.data
          this.examineTotal = res.total
        });
      },
      /*查询核查范围，表格转换问题*/
      queryDictDataTransition() {
        queryDictDataTransition().then(res => {
          this.dictDataTransition = res
        });
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
      /*查询字典表tree字典范围数据*/
      queryInspectTree() {
        queryInspectTree("100").then(res => {
          this.examineTemplateData = res.data
        });
      },
      // 编辑工序
      moveUpdateProcess(item) {
        this.definingReset();
        this.queryInspectTree();
        this.dialogUpdateProcess = true;
        Object.assign(this.processForm, item);
        const arrNot = {
          procedureId: item.id,
          boolean: false,
          trueingId: item.trueingId
        }
        /*查询工序未选择的核查项*/
        searchProcedureUnselectedList(arrNot).then(res => {
          this.noSelectData = res.data
        });
        const arr = {
          procedureId: item.id,
          boolean: true,
          trueingId: item.trueingId
        }
        /*查询工序选择的核查项*/
        searchProcedureUnselectedList(arr).then(res => {
          this.haveSelectData = res.data
        });
      },
      /*保存编辑工序按钮*/
      handleProcessSave(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            /*编辑*/
            this.processForm.dahcHcxTrueingManageList = this.haveSelectData
            updateProcedure(this.processForm).then(res => {
              if (res.code == this.returnState) {
                this.$message.success("修改成功")
                this.dialogUpdateProcess = false;
                this.searchProcedureList();
              }
            });
          }
        });
      },
      /*工序表单清空*/
      definingReset() {
        this.processForm = {
          procedureDesc: '',
          procedureName: '',
          sort: '',
          trueingId: '',
          examineTemplate: '',
          dahcHcxTrueingManageList: []
        }
      },
      /*工序上移动*/
      moveUpTable(arr, item, index) {
        /*上移传选中数据和序列前一个工序数据*/
        const ids = {
          'id1': item.id,
          'id2': arr[index-1].id
        }
        procedureUpAndDown(ids).then(res => {
          // arr.splice(index - 1, 0, item);  // 定位到点击位置的上一行，0即不删除如何元素，在此位置插入item
          // arr.splice(index + 1, 1); // 此时数组中有重复元素，把原来被挤下去的元素删除
          this.searchProcedureList();
        });
      },
      /*工序下移动*/
      moveDownTable(arr, item, index) {
        const ids = {
          'id1': item.id,
          'id2': arr[index+1].id
        }
        procedureUpAndDown(ids).then(res => {
          this.searchProcedureList()
        });
      },
      // 删除
      deleteItem(arr,row, index) {
        // arr.splice(index, 1);
        const ids = [];
        ids.push(row.id);
        this.$confirm('此操作将永久删除本行数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          batchDeleteByIdsProcedure(ids).then(res => {
            if (res.code == this.returnState) {
              this.$message.success("删除成功");
              this.searchProcedureList();
            }
          })
        });
      },
      /***********************************************************下方表格工序--------结束*****************************************/
    }

  }
</script>

<style lang="scss" scoped>

  .tablePage {
    height: calc(50% - 52px);
  }

  .processDialog {
    .tablePage {
      height: calc(40vh - 110px);
    }

    .tableDes:first-of-type {
      padding-top: 0;
    }
  }
</style>

