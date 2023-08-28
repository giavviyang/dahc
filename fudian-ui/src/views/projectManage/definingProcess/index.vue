<template>
  <div class="app-container-padding">
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="iptAndBtn"
             style="position: relative" @submit.native.prevent>
      <div style="white-space: nowrap;margin-right: 90px;">
        <el-form-item label="工序名称" prop="processName">
          <el-input
            v-model="queryParams.projectProcedureName"
            placeholder="请输入工序名称"
            @keyup.enter.native="conditionalQueries"
            clearable/>
        </el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="conditionalQueries"
                   v-hasPermi="['projectManage:process:list']">搜索
        </el-button>
      </div>
      <div style="right:5px;position: absolute">
        <el-button type="primary" icon="el-icon-refresh-left" size="mini" @click="handleBack"
                   v-hasPermi="['projectManage:process:back']">返回
        </el-button>
      </div>
    </el-form>
    <div class="btn">
      <el-button
        type="primary"
        icon="el-icon-plus"
        size="mini"
        @click="handleAdd" v-hasPermi="['projectManage:process:add']">
        新增工序
      </el-button>
      <el-button
        type="primary"
        icon="el-icon-edit"
        size="mini"
        @click="handleUpdate" v-hasPermi="['projectManage:process:edit']">
        编辑工序
      </el-button>
      <el-button
        type="primary"
        icon="el-icon-delete"
        size="mini"
        @click="handleDelete" v-hasPermi="['projectManage:process:delete']">
        删除工序
      </el-button>
    </div>
    <TablePage class="tablePage"
               :tableData="processData"
               ref="processRef"
               :hasIndex="false"
               :pageSize="queryParams.pageSize"
               :pageNum="queryParams.pageNum"
               :total="processTotal"
               :defaultSort="defaultSort"
               @handleSelectionChange="handleProcessSelection"
               @handleChange="handleProcessChange"
               @handleRowClick="handleProcessClick">
      <template slot="table">
        <el-table-column
          label="排序"
          prop="sort"
          width="80"
        />
        <el-table-column
          show-overflow-tooltip
          v-for="(item,index) in processColumn"
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
            <el-button @click.stop="moveUp(processData,scope.row, scope.$index)"
                       :disabled="scope.$index === 0" type="text" size="mini" v-hasPermi="['projectManage:process:up']">
              上移
            </el-button>
            <el-button @click.stop="moveDown(processData,scope.row, scope.$index)"
                       :disabled="getFormLength(scope.$index,processData)" type="text" size="mini"
                       v-hasPermi="['projectManage:process:down']">下移
            </el-button>
          </template>
        </el-table-column>
      </template>
    </TablePage>
    <TablePage class="tablePage"
               :hasSelection="false"
               :is-title="true"
               :tableTitle="checkTableTitle"
               :tableData="examineData"
               :pageSize="queryInspectParams.pageSize"
               :pageNum="queryInspectParams.pageNum"
               :total="examineTotal"
               @handleChange="handleCheckItem"
    >
      <template slot="table">
        <el-table-column
          show-overflow-tooltip
          v-for="(item,index) in examineColumn"
          :key="index"
          :prop="item.prop"
          :label="item.label"
          :formatter="item.formatter"
          :min-width="item.width"
          :class-name="item.cellName">
        </el-table-column>
        <el-table-column
          label="操作"  fixed="right"
          width="150" class-name="operation">
          <template slot-scope="scope">
            <el-button @click="moveUpInspectTable(examineData,scope.row, scope.$index)"
                       :disabled="scope.$index === 0" type="text" size="mini"
                       v-hasPermi="['projectManage:process:upExamine']">上移
            </el-button>
            <el-button @click="moveDownInspectTable(examineData,scope.row, scope.$index)"
                       :disabled="getFormLength(scope.$index,examineData)" type="text" size="mini"
                       v-hasPermi="['projectManage:process:downExamine']">下移
            </el-button>
            <el-button type="text" size="mini" @click="deleteItem(examineData,scope.row,scope.$index)"
                       v-hasPermi="['projectManage:process:removeExamine']">移除
            </el-button>
          </template>
        </el-table-column>
      </template>
    </TablePage>
    <el-dialog :title="dialogType==='add'?'新增工序':dialogType==='edit'?'编辑工序':''"
               :visible.sync="dialogVisible"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="85%" class="dialog-style processDialog">
      <el-form ref="processFormRef" :model="processForm" size="mini" :rules="processRules" :inline="true" class="ipt">
        <el-form-item label="工序排序" prop="sort">
          <el-input-number v-model="processForm.sort" placeholder="请输入工序排序" maxlength="30" clearable :min="1"
                           :step="1"/>
          <!--          <el-input-number v-model="processForm.processSort"  :min="1" :max="10" label="描述文字"></el-input-number>-->
        </el-form-item>
        <el-form-item label="工序名称" prop="procedureName">
          <el-input v-model="processForm.procedureName" placeholder="请输入工序名称" maxlength="30" clearable/>
        </el-form-item>
        <el-form-item label="核查模版" prop="trueingId">
          <!--          <el-cascader-->
          <!--            v-model="processForm.trueingId"-->
          <!--            :options="examineTemplateData"-->
          <!--            :props="{ expandTrigger: 'hover' }"-->
          <!--            @change="handleCascader" :disabled="dialogType==='add'?false:dialogType==='edit'?true:false"></el-cascader> -->
          <el-cascader
            v-model="processForm.trueingId"
            :options="examineTemplateData"
            :props="{ expandTrigger: 'hover' }"
            @change="handleCascader"></el-cascader>
        </el-form-item>
        <el-form-item label="工序描述" prop="procedureDesc">
          <el-input v-model="processForm.procedureDesc" placeholder="请输入工序描述" maxlength="30" clearable/>
        </el-form-item>
      </el-form>
      <TablePage class="tablePage"
                 :hasSelection="false"
                 :tableData="noSelectData"
                 :isPage="false"
                 :isTitle="true"
                 :tableTitle="noSelectTableTitle">
        <template slot="table">
          <el-table-column
            v-for="(item,index) in examineColumn"
            :key="index"
            :prop="item.prop"
            :label="item.label"
            :formatter="item.formatter"
            :min-width="item.width"
            :class-name="item.cellName">
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
      <TablePage class="tablePage"
                 :hasSelection="false"
                 :tableData="haveSelectData"
                 :isPage="false"
                 :isTitle="true"
                 :tableTitle="haveSelectTableTitle">
        <template slot="table">
          <el-table-column
            v-for="(item,index) in examineColumn"
            :key="index"
            :prop="item.prop"
            :label="item.label"
            :formatter="item.formatter"
            :min-width="item.width"
            :class-name="item.cellName">
          </el-table-column>
          <el-table-column
            label="操作"  fixed="right"
            width="150" class-name="operation">
            <template slot-scope="scope">
              <el-button type="text" size="mini"
                         @click="removeSelect(haveSelectData,scope.row,scope.$index,noSelectData)">取消选择
              </el-button>
              <el-button @click="moveUpInspect(haveSelectData,scope.row, scope.$index)"
                         :disabled="scope.$index === 0" type="text" size="mini">上移
              </el-button>
              <el-button @click="moveDownInspect(haveSelectData,scope.row, scope.$index)"
                         :disabled="getFormLength(scope.$index,haveSelectData)" type="text" size="mini">下移
              </el-button>
            </template>
          </el-table-column>
        </template>
      </TablePage>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" size="mini" @click="handleNext('processFormRef')"
                   v-show="dialogType==='add'">下一个</el-button>
        <el-button type="primary" size="mini" @click="handleSave('processFormRef')">保存</el-button>
        <el-button size="mini" @click="dialogVisible=false">取消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import TablePage from "@/components/Public/table/TablePage.vue";
  import {
    searchProcedureList,
    addProcedure,
    procedureUpAndDown,
    procedureInspectUpAndDown,
    searchProcedureUnselectedList,
    updateProcedure,
    batchDeleteByIdsProcedure,
    deleteProcedureTrueingId,
    queryInspectTableList
  } from "@/api/projectManage/projectProcedure"
  import {queryDictDataTransition, queryInspectTree} from "@/api/dahc/sys/dictData";
  import {listManagement} from "@/api/dahc/trueingManage/trueingManage";
  import moment from "moment";
  import {formatDate} from "@/utils";

  export default {
    name: "index",
    components: {TablePage},

    data() {
      return {
        queryParams: {
          projectProcedureName: '',
          processName: '',
          processSize: 20,
          processNum: 1,
          pageSize: 20,
          pageNum: 1,
          projectId: '',
        },
        queryInspectParams: {
          pageSize: 20,
          pageNum: 1,
        },
        processData: [],
        processColumn: [
          {label: '工序名称', prop: 'procedureName', width: '100'},
          {label: '工序描述', prop: 'procedureDesc', width: '100'},
          // {label: '核查项数量', prop: 'inspectNumber', width: '100'},
          {label: '创建时间', prop: 'createTime', width: '100', formatter: formatDate},
          // {label: '可参与人数', prop: 'remarks', width: '100'},
          // {label: '可参与人员', prop: 'remarks', width: '100'},
          // {label: '排序', prop: 'sort', width: '100'},
          // {label: '备注', prop: 'remarks', width: '100'},
        ],
        selected: [], //选中数据
        processTotal: 0,
        defaultSort: {prop: 'sort', order: 'asc'},
        checkTableTitle: '核查项信息：',
        noSelectTableTitle: '可选择核查项',
        haveSelectTableTitle: '已选择核查项',
        examineData: [],
        examineColumn: [
          // {label: '核查项名称', prop: 'trueingName', width: '120'},
          // {label: '核查项描述', prop: 'trueingDesc', width: '100'},
          // {label: '核查标准', prop: 'examineStasString', width: '100'},
          // {label: '核查范围', prop: 'trueingScopeStair', width: '100', formatter: this.trueingScopeStairFormatter},
          {label: '核查项名称', prop: 'trueingName', width: '120', cellName: 'cellName'},
          {label: '核查项描述', prop: 'trueingDesc', width: '150', cellName: 'cellName'},
          {label: '核查标准', prop: 'examineStasString', width: '180', cellName: 'cellName'},
          {label: '核查范围', prop: 'trueingScopeStair', width: '80', formatter: this.trueingScopeStairFormatter},
        ],
        examineTotal: 0,
        examineSize: 10,
        examineNum: 1,
        dialogType: 'add',
        dialogVisible: false,
        processForm: {
          procedureDesc: '',
          procedureName: '',
          sort: '',
          projectId: '',
          trueingId: [],
          examineTemplate: '',
          dahcHcxTrueingManageList: []
        },
        /*添加工序弹窗-未选择表格数据*/
        noSelectData: [],
        /*添加工序弹窗-已选择表格数据*/
        haveSelectData: [],
        examineTemplateData: [],
        processRules: {
          procedureName: [
            {required: true, message: "工序名称不能为空", trigger: "blur"},
            {max: 20, message: "项目名称长度最大为20"}
          ],
          trueingId: [
            {required: true, message: "核查模板不能为空", trigger: "blur"},
          ],
          sort: [
            {required: true, message: "排序不能为空", trigger: "blur"},
            {pattern: /^([1-9]|[1-9]\d|100)$/, message: "排序最大值为100"},
            {pattern: /^(0|[1-9][0-9]*)$/, message: '请输入整数值'}
          ],
        },
        /*核查项关联核查范围id*/
        trueingScopeStair: '',
        trueingSecondLevelId: '',
        /*新增工序弹窗-核查范围字段*/
        dictDataTransition: [],
        returnState: 20000,
        /*工序id*/
        procedureId: ''

      }
    },
    created() {
      this.gainRouter();
      this.queryDictDataTransition();
      /*字典范围下拉框*/
      this.queryInspectTree();
    },
    methods: {
      /*表格时间类型转换*/
      startTimeFormatter(row, column, cellValue) {
        var date = row[column.property];
        if (date == undefined) {
          return "";
        }
        return moment(date).format("YYYY-MM-DD HH:mm:ss");
      },
      /*条件查询*/
      conditionalQueries() {
        this.queryParams.pageSize = 20;
        this.queryParams.pageNum = 1;
        this.handleQuery();
      },
      /*************************************************************工序新增方法***********************************************/

      /*获取传来的值*/
      gainRouter() {
        this.queryParams.projectId = this.$route.query.id;
        this.handleQuery();
      },
      /*查询字典表tree字典范围数据*/
      queryInspectTree() {
        queryInspectTree("100").then(res => {
          this.examineTemplateData = res.data
        });
      },
      /*新增核查项数据查询，未选择表格*/
      queryInspect() {
        this.queryParams.trueingScopeStair = this.trueingScopeStair
        listManagement(this.queryParams).then(response => {
          // this.noSelectData = response.data;
          this.noSelectData = JSON.parse(JSON.stringify(response.data));
          if (this.noSelectData) {
            this.noSelectData.forEach(item => {
              // console.log(item.examineStasString)
              let str = item.examineStasString.replace(/\//g, '；');
              item.examineStasString = str.slice(0, item.examineStasString.length - 1)
            })
          }
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
      /**
       *点击核查范围触发事件
       */
      handleCascader(value) {
        this.noSelectData = [];
        this.haveSelectData = [];
        /*根据核查范围id查询*/
        this.trueingScopeStair = value[2]
        this.trueingSecondLevelId = value[1]
        this.queryInspect();
      },
      /**
       * 新增工序
       */
      handleAdd() {
        this.resetTable();
        this.definingReset();
        this.dialogType = 'add';
        this.dialogVisible = true;

      },
      /*************************************************************工序新增方法-----结束***********************************************/
      /*************************************************************工序编辑方法-----开始***********************************************/
      /*清空编辑或添加表格数据*/
      resetTable() {
        this.noSelectData = [];
        this.haveSelectData = [];
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
          this.definingReset();
          this.resetTable();
          this.dialogType = 'edit';
          this.dialogVisible = true;
          Object.assign(this.processForm, this.selected[0]);
          const arrNot = {
            procedureId: this.selected[0].id,
            boolean: false,
            trueingId: this.selected[0].trueingId
          }
          /*查询工序未选择的核查项*/
          searchProcedureUnselectedList(arrNot).then(res => {
            this.noSelectData = JSON.parse(JSON.stringify(res.data));
            if (this.noSelectData) {
              this.noSelectData.forEach(item => {
                // console.log(item.examineStasString)
                let str = item.examineStasString.replace(/\//g, '；');
                item.examineStasString = str.slice(0, item.examineStasString.length - 1)
              })
            }
          });
          const arr = {
            procedureId: this.selected[0].id,
            boolean: true,
            trueingId: this.selected[0].trueingId
          }
          /*查询工序选择的核查项*/
          searchProcedureUnselectedList(arr).then(res => {
            this.haveSelectData = JSON.parse(JSON.stringify(res.data))
            if (this.haveSelectData) {
              this.haveSelectData.forEach(item => {
                // console.log(item.examineStasString)
                let str = item.examineStasString.replace(/\//g, '；');
                item.examineStasString = str.slice(0, item.examineStasString.length - 1)
              })
            }
          });
        }
      },
      /*上移*/
      moveUpInspect(arr, item, index) {
        arr.splice(index - 1, 0, item);  // 定位到点击位置的上一行，0即不删除如何元素，在此位置插入item
        arr.splice(index + 1, 1); // 此时数组中有重复元素，把原来被挤下去的元素删除
      },
      /*下移*/
      moveDownInspect(arr, item, index) {
        arr.splice(index + 2, 0, item);
        arr.splice(index, 1);
      },

      /*************************************************************工序编辑方法-----结束***********************************************/
      /*************************************************************下方核查项表格事件-----开始***********************************************/
      /*表格点击事件*/
      // rowClick(record,index) {
      //   // console.log(record,index,"hhhh")
      //   this.queryInspectTable(record.row.id);
      // },
      handleProcessClick(row, column, event) {
        this.procedureId = row.row.id
        this.queryInspectTable(row.row.id);
      },
      /*查询下方核查项表格*/
      queryInspectTable(id) {
        this.queryInspectParams.id = id;
        queryInspectTableList(this.queryInspectParams).then(res => {
          this.examineData = JSON.parse(JSON.stringify(res.data))
          if (this.examineData) {
            this.examineData.forEach(item => {
              // console.log(item.examineStasString)
              let str = item.examineStasString.replace(/\//g, '；');
              item.examineStasString = str.slice(0, item.examineStasString.length - 1)
            })
          }
          this.examineTotal = res.total
        });
      },
// 上移
      moveUp(arr, item, index) {
        this.$confirm('此操作将上移工序名称为：' + item.procedureName + '的排序, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          /*上移传选中数据和序列前一个工序数据*/
          const ids = {
            'id1': item.id,
            'id2': arr[index - 1].id
          }
          procedureUpAndDown(ids).then(res => {
            if (res.code === this.returnState) {
              this.$message.success("上移成功");
              // arr.splice(index - 1, 0, item);  // 定位到点击位置的上一行，0即不删除如何元素，在此位置插入item
              // arr.splice(index + 1, 1); // 此时数组中有重复元素，把原来被挤下去的元素删除
              this.handleQuery();
            }
          });
        })


      },
      // 下移
      moveDown(arr, item, index) {
        this.$confirm('此操作将下移工序名称为：' + item.procedureName + '的排序, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          /*上移传选中数据和序列前一个工序数据*/
          const ids = {
            'id1': item.id,
            'id2': arr[index + 1].id
          }
          procedureUpAndDown(ids).then(res => {
            if (res.code === this.returnState) {
              this.$message.success("下移成功");
              // arr.splice(index - 1, 0, item);  // 定位到点击位置的上一行，0即不删除如何元素，在此位置插入item
              // arr.splice(index + 1, 1); // 此时数组中有重复元素，把原来被挤下去的元素删除
              this.handleQuery();
            }
          });
        })
      },
      /*************************************************************下方核查项表格事件-----结束***********************************************/
      /*核查项上移动*/
      moveUpInspectTable(arr, item, index) {
        this.$confirm('此操作将上移核查项名称为：' + item.trueingName + '的排序, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          /*上移传选中数据和序列前一个工序数据*/
          const ids = {
            'procedureId': this.procedureId,
            'id1': item.id,
            'id2': arr[index - 1].id
          }
          procedureInspectUpAndDown(ids).then(res => {
            if (res.code === this.returnState) {
              this.$message.success("上移成功");
              // arr.splice(index - 1, 0, item);  // 定位到点击位置的上一行，0即不删除如何元素，在此位置插入item
              // arr.splice(index + 1, 1); // 此时数组中有重复元素，把原来被挤下去的元素删除
              this.queryInspectTable(this.procedureId);
            }
          });
        })
      },
      /*核查项上移动*/
      moveDownInspectTable(arr, item, index) {
        this.$confirm('此操作将下移核查项名称为：' + item.trueingName + '的排序, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          /*上移传选中数据和序列前一个工序数据*/
          const ids = {
            'procedureId': this.procedureId,
            'id1': item.id,
            'id2': arr[index + 1].id
          }
          procedureInspectUpAndDown(ids).then(res => {
            if (res.code === this.returnState) {
              this.$message.success("下移成功");
              // arr.splice(index - 1, 0, item);  // 定位到点击位置的上一行，0即不删除如何元素，在此位置插入item
              // arr.splice(index + 1, 1); // 此时数组中有重复元素，把原来被挤下去的元素删除
              this.queryInspectTable(this.procedureId);
            }
          });
        })
      },
      /**
       * 查询
       */
      handleQuery() {
        searchProcedureList(this.queryParams).then(res => {
          this.processData = res.data;
          this.processTotal = res.total;
          /*查询下方核查项表格*/
          if (res.data.length > 0) {
            this.procedureId = res.data[0].id;
            this.queryInspectTable(res.data[0].id);
          } else {
            this.examineData = [];
            this.examineTotal = 0;
          }
        });
      },
      /**
       * 返回
       */
      handleBack() {
        this.$router.go(-1);
        // this.$router.push('projectInitialize');
      },
      /*工序表单清空*/
      definingReset() {
        this.processForm = {
          procedureDesc: '',
          procedureName: '',
          sort: '',
          trueingId: [],
          examineTemplate: '',
          dahcHcxTrueingManageList: []
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
            batchDeleteByIdsProcedure(ids).then(res => {
              if (res.code == this.returnState) {
                this.$message.success("删除成功");
                this.selected = [];
                this.handleQuery();
              }
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
      handleProcessSelection(selection) {
        // 清除 所有勾选项
        if (selection.length === 1) {
          this.selected = selection;
          // this.handleClick(this.activeName)
        }
        if (selection.length > 1) {
          this.$refs.processRef.$refs.tableRef.clearSelection();
          this.$refs.processRef.$refs.tableRef.toggleRowSelection(selection.at(-1), true);
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
      handleProcessChange(pageSize, pageNum) {
        console.log(pageSize, pageNum)
        this.queryParams.pageSize = pageSize;
        this.queryParams.pageNum = pageNum;
        this.handleQuery();
      },
      // 选择
      moveSelect(arr1, item, index, arr2) {
        arr1.splice(index, 1);
        arr2.push(item);

      },
      // 取消选择
      removeSelect(arr1, item, index, arr2) {
        arr1.splice(index, 1);
        arr2.push(item);
      },

      // 删除
      deleteItem(arr, row, index) {
        this.$confirm('此操作将移除名称为：' + row.trueingName + '的核查项, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          deleteProcedureTrueingId(row.id).then(res => {
            if (res.code == this.returnState) {
              this.$message.success("删除成功");
              arr.splice(index, 1);
            }
          });
        })
      },
      // 控制下移按钮的显示于隐藏
      getFormLength(index, arr) {
        if (index === arr.length - 1) {
          return true;
        } else {
          return false;
        }
      },
      /*核查项表格*/
      handleCheckItem(pageSize, pageNum) {
        this.queryInspectParams.pageSize = pageSize;
        this.queryInspectParams.pageNum = pageNum;
        this.queryInspectTable(this.procedureId);
      },

      /**
       * 下一个工序
       */
      handleNext(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            // if (this.haveSelectData.length > 0) {
            /*新增*/
            this.processForm.projectId = this.queryParams.projectId;
            this.processForm.dahcHcxTrueingManageList = this.haveSelectData;
            this.processForm.trueingId = this.trueingScopeStair;
            this.processForm.trueingPagePathId = this.trueingSecondLevelId;
            let newSort = this.processForm.sort;
            addProcedure(this.processForm).then(res => {
              if (res.code == this.returnState) {
                this.$message.success("新增成功")
                this.handleQuery();
                this.dialogVisible = true;
                this.processForm = {};
                this.processForm.sort = newSort + 1;
                this.processForm.trueingId = '';
              }
            })
            // } else {
            //   this.$message.error("没有核查项信息");
            // }
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
            // if (this.haveSelectData.length > 0) {
            /*新增*/
            if (this.dialogType == "add") {
              this.processForm.projectId = this.queryParams.projectId;
              this.processForm.dahcHcxTrueingManageList = this.haveSelectData;
              this.processForm.trueingId = this.trueingScopeStair;
              this.processForm.trueingPagePathId = this.trueingSecondLevelId;
              addProcedure(this.processForm).then(res => {
                if (res.code == this.returnState) {
                  this.$message.success("新增成功")
                  this.dialogVisible = false;
                  this.handleQuery();
                }
              });
            } else {
              /*编辑*/
              this.processForm.dahcHcxTrueingManageList = this.haveSelectData;
              this.processForm.trueingPagePathId = this.trueingSecondLevelId;
              // this.processForm.trueingId = this.trueingScopeStair;
              if (this.trueingScopeStair == '') {
                this.trueingScopeStair = this.processForm.trueingId;
              } else {
                this.processForm.trueingId = this.trueingScopeStair;
              }
              updateProcedure(this.processForm).then(res => {
                if (res.code == this.returnState) {
                  this.$message.success("修改成功")
                  this.dialogVisible = false;
                  this.handleQuery();
                }
              });
            }

            // }else {
            //   this.$message.error("没有核查项信息");
            // }

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
    height: calc(50% - 50px) !important;
  }

  ::v-deep .processDialog {
    .tablePage {
      height: calc(40vh - 110px) !important;

    }

    .tablePage:last-of-type {
      margin-top: 25px;
    }
  }

  ::v-deep .el-table__body-wrapper {
    .cellName {
      .cell {
        word-break: keep-all;
        word-wrap: break-word;
        text-align: left;
        //color: red;
      }
    }
  }
</style>

