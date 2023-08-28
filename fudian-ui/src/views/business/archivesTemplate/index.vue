<template>
  <div class="app-container">
    <MainFlexibleTree :data="treeData" ref="treeData" :defaultProps="treeDefaultProps"
                      @handleNodeClick="handleNodeClick">
      <div slot="mainRight" style="height: 100%">
        <el-tabs type="border-card" v-model="activeName" class="modelTab">
          <el-tab-pane label="模板信息" name="first" v-if="activeName==='first'">
            <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="iptAndBtn"
                     @submit.native.prevent>
              <el-form-item label="案卷模板名称" prop="modelName">
                <el-input v-model="queryParams.modelName" placeholder="请输入案卷模板名称"
                          @keyup.enter.native="conditionalQueries"
                          clearable/>
              </el-form-item>
              <el-form-item label="模板描述" prop="modelDesc">
                <el-input v-model="queryParams.modelDesc" placeholder="请输入模板描述" @keyup.enter.native="conditionalQueries"
                          clearable/>
              </el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="conditionalQueries"  v-hasPermi="['business:model:list']">搜索</el-button>
            </el-form>
            <div class="btn">
              <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd"  v-hasPermi="['business:model:add']">
                新增模板
              </el-button>
              <el-button type="primary" icon="el-icon-edit" size="mini" @click="handleUpdate"  v-hasPermi="['business:model:edit']">
                编辑模板
              </el-button>
              <el-button type="primary" icon="el-icon-delete" size="mini" @click="handleDeleted"  v-hasPermi="['business:model:delete']">
                删除模板
              </el-button>
            </div>
            <TablePage class="tablePage"
                       :tableData="modelTable"
                       :pageSize="queryParams.pageSize"
                       :pageNum="queryParams.pageNum"
                       :total="total"
                       :rowId="rowId"
                       :isExpandAll="isExpandAll"
                       :treeProps="treeProps"
                       ref="modelTable"
                       :hasIndex="false"
                       @handleSelectionChange="handleSelectionChange"
                       @handleChange="handleChange">
              <template slot="table">
                <el-table-column
                  type="index"
                  label="序号"
                  width="55" show-overflow-tooltip>
                  <template slot-scope="scope">
<!--                    <span v-if="scope.row.archiveTableLevel==1">-->
                          {{ scope.row.rowIndex }}
<!--                    </span>-->
                  </template>
                </el-table-column>
                <el-table-column show-overflow-tooltip v-for="(item,index) in tableColumn" :key="item.id"
                                 :prop="item.prop"
                                 :label="item.label"
                                 :min-width="item.width"
                                 :formatter="item.formatter"
                                 :class-name="item.className">
                </el-table-column>
                <el-table-column
                  label="操作"  fixed="right"
                  width="100" class-name="operation">
                  <template slot-scope="scope">
                    <el-button type="text" size="mini" @click="handleSetMapper(scope.row)"  v-hasPermi="['business:model:setMetaData']">
                      字段维护
                    </el-button>
                  </template>
                </el-table-column>
              </template>
            </TablePage>
          </el-tab-pane>
          <el-tab-pane label="字段信息" name="second" v-if="activeName==='second'">
            <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="iptAndBtn"
                     @submit.native.prevent>
              <el-form-item label="元数据名称" prop="mapperName">
                <el-input v-model="queryParams.mapperName" placeholder="请输入元数据名称"
                          @keyup.enter.native="handleQuery"
                          clearable/>
              </el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery"  v-hasPermi="['business:model:metaDataList']">搜索</el-button>
            </el-form>
            <div class="btn">
              <el-button type="primary" icon="el-icon-edit-outline" size="mini" @click="handleSetMapper(currentVal)"  v-hasPermi="['business:model:setMetaData']">
                设置字段
              </el-button>
              <el-button type="primary" icon="el-icon-delete" size="mini" @click="handleRemove"  v-hasPermi="['business:model:removeMetaData']">
                移除字段
              </el-button>
            </div>
            <TablePage class="tablePage" ref="metaTable" :tableData="metaTable"  :hasIndex="false" :isPage="false"
                       @handleSelectionChange="handleSelectionChange">
              <template slot="table">
                <el-table-column
                  type="index"
                  label="序号"
                  width="55" show-overflow-tooltip>
                  <template slot-scope="scope">
                    <!--                    <span v-if="scope.row.archiveTableLevel==1">-->
                    {{ scope.row.rowIndex }}
                    <!--                    </span>-->
                  </template>
                </el-table-column>
                <el-table-column show-overflow-tooltip v-for="(item,index) in metaTableColumn"
                                 :key="index"
                                 :prop="item.prop"
                                 :label="item.label"
                                 :min-width="item.width"
                                 :formatter="item.formatter">
                </el-table-column>
              </template>
            </TablePage>
          </el-tab-pane>
        </el-tabs>
      </div>
    </MainFlexibleTree>
    <el-dialog :title="dialogType==='add'?'新增模板':dialogType==='edit'?'编辑模板':''" :visible.sync="dialogVisible"
               :close-on-click-modal="false" :close-on-press-escape="false" :destroy-on-close="true" width="800px"
               class="dialog-style">
      <el-form ref="modelFormRef" :model="modelForm" size="mini" :rules="modelRules" :inline="true"
               class="ipt">
        <el-form-item label="模板名称" prop="modelName">
          <el-input v-model="modelForm.modelName" placeholder="请输入模板名称" maxlength="30" clearable/>
        </el-form-item>
        <el-form-item label="模板描述"
                      prop="modelDesc">
          <el-input v-model="modelForm.modelDesc" placeholder="请输入模板描述" maxlength="30" clearable/>
        </el-form-item>
        <el-form-item label="模板类型" prop="archiveTableLevel">
          <el-select v-model="modelForm.archiveTableLevel" placeholder="请选择模板类型" maxlength="30" clearable
                     @change="archiveTableLevelChange" :disabled="dialogType==='add'?false:dialogType==='edit'?true:''">
            <el-option v-for="modelItem in modelTypeOptions" :key="modelItem.value" :label="modelItem.label"
                       :value="modelItem.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="档案类型" prop="archiveTypeIds" v-if="dialogType==='add'">
          <el-cascader
            filterable
            v-model="modelForm.archiveTypeIds"
            :options="archiveTypeNameOptions"
            @change="handleCascader"
            :props="{ expandTrigger: 'hover' }"
            ref="modelCascader"
            placeholder="请选择档案类型"
          ></el-cascader>
        </el-form-item>
        <el-form-item label="档案类型"
                      prop="archiveTypeName" v-if="dialogType==='edit'">
          <el-input v-model="modelForm.archiveTypeName" placeholder="请输入档案类型" maxlength="30" disabled clearable/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
				<el-button type="primary" size="mini" @click="handleSave('modelFormRef')">保存</el-button>
				<el-button size="mini" @click="dialogVisible=false">取消</el-button>
			</span>
    </el-dialog>
    <el-dialog :title="dialogType==='case'?'设置案卷字段':dialogType==='file'?'设置文件字段':''"
               :visible.sync="dialogSetting"
               :close-on-click-modal="false" :close-on-press-escape="false" :destroy-on-close="true" width="900px"
               class="dialog-style mapperDialog">
      <el-form ref="mapperFormRef" :model="modelForm" size="mini" :inline="true" class="ipt">
        <el-form-item label="模板名称" prop="modelName">
          <el-input v-model="modelForm.modelName" maxlength="30" disabled/>
        </el-form-item>
        <el-form-item label="档案类型" prop="archiveTypeName">
          <el-input v-model="modelForm.archiveTypeName" maxlength="30" disabled/>
        </el-form-item>
      </el-form>
      <TablePage class="tablePage"
                 :tableData="selectedTableData"
                 :isPage="false"
                 :hasSelection="false"
                 :isTitle="true"
                 :tableTitle="selectedTableTitle">
        <template slot="table">
          <el-table-column
            label="映射字段名称" width="250">
            <template slot-scope="scope">
              <el-input size="mini" v-model="scope.row.sourceName" clearable></el-input>
            </template>
          </el-table-column>
<!--          <el-table-column-->
<!--            label="文件字段序号" width="140">-->
<!--            <template slot-scope="scope">-->
<!--              <el-input-number size="mini" v-model="scope.row.sourceOrder" :min="1" :step="1" clearable-->
<!--                               style="width: 100px;"></el-input-number>-->
<!--            </template>-->
<!--          </el-table-column>-->
          <el-table-column
            label="元数据名称" width="250">
            <template slot-scope="scope">
              <el-select size="mini"
                         v-model="scope.row.metadataId"
                         placeholder="请选择元数据中文名称"
                         maxlength="30"
                         filterable
                         @change="handleChangeMeta(scope.row)"
                         @clear="handleClear(scope.row)"
                         >
                <el-option v-for="metaDataItem in metaDataCNOptions"
                           :key="metaDataItem.id"
                           :label="metaDataItem.metadataName"
                           :value="metaDataItem.id"
                           :disabled="!!selectedTableData.find(val => val.metadataId === metaDataItem.id)"/>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column
            label="元数据类型" min-width="150" prop="metadataType">
          </el-table-column>
          <el-table-column label="操作" min-width="120" class-name="operation">
            <template slot-scope="scope">
              <el-button type="text" size="mini" :disabled="scope.$index===0"
                         @click="moveUp(selectedTableData,scope.row,scope.$index)">
                上移
              </el-button>
              <el-button type="text" size="mini" :disabled="scope.$index===selectedTableData.length-1"
                         @click="moveDown(selectedTableData,scope.row,scope.$index)">
                下移
              </el-button>
              <el-button type="text" size="mini" @click="addItem(selectedTableData,scope.$index)">
                新增
              </el-button>
              <el-button type="text" size="mini" :disabled="scope.$index===0"
                         @click="deleteItem(selectedTableData,scope.$index)">移除
              </el-button>
            </template>
          </el-table-column>
        </template>
      </TablePage>
      <span slot="footer" class="dialog-footer">
            <el-button type="primary" size="mini" @click="handleSaveMapper()">保存</el-button>
            <el-button size="mini" @click="dialogSetting=false">取消</el-button>
          </span>
    </el-dialog>
  </div>
</template>

<script>
import MainFlexibleTree from "@/components/Public/MainBody/MainFlexibleTree.vue";
import {
  listData,
  searchData,
  addData,
  updateData,
  delData,
  selecctModelByArchiveType,
  selectMapperByModel,
  addMapperData,
  updateMapperData,
  delMapperData, bulkAdditions, modelSearchData
} from "@/api/business/model"
import TablePage from "@/components/Public/table/TablePage.vue";
import {queryDictDataTransition, queryDictDataTransitionId} from "@/api/dahc/sys/dictData";
import {queryMetadataSelect} from "@/api/business/metadata";
import {queryArchiveAndMetadata, selectListAndMetadata} from "@/api/business/archiveType";
import {formatDate} from "@/utils";
import {mapperSearchData} from "@/api/business/mapper";

export default {
  name: "index",
  components: {
    TablePage,
    MainFlexibleTree
  },

  data() {
    return {
      /*元数据下拉框*/
      metaDataCNOptions: [],
      treeData: [],
      // archiveTypeData: [],
      treeDefaultProps: {
        label: 'archiveName',
        value: 'id',
        children: 'dahcModelLv'
      },
      activeName: 'first',
      queryParams: {
        archiveTypeId: '',
        modelName: '',
        modelDesc: '',
        pageSize: 20,
        pageNum: 1,
        id: '',
        mapperName: '',
        modelId: '',
      },
      currentVal: {},
      modelTable: [],
      metaTable: [],
      isExpandAll: false,
      rowId: 'id',
      treeProps: {children: 'dahcModelLv', hasChildren: 'hasChildren'},
      tableColumn: [
        {label: '模板名称', prop: 'modelName', width: '100', className: "textLeft"},
        {label: '模板描述', prop: 'modelDesc', width: '100'},
        // {label: '模板类型', prop: 'modelType', width: '60'},
        {label: '档案类型', prop: 'archiveTypeName', width: '100'},
        {label: '创建人', prop: 'createByName', width: '60'},
        {label: '创建时间', prop: 'createTime', width: '80', formatter: formatDate}
      ],
      metaTableColumn: [
        {label: 'Excel字段', prop: 'sourceName', width: '100'},
        // {label: 'Excel序号', prop: 'sourceOrder', width: '100'},
        {label: '元数据名', prop: 'metadataName', width: '100'},
        {label: '元数据类型', prop: 'metadataType', width: '100',},
        // {label: '元数据宽度', prop: 'metadataWidth', width: '100'},
      ],
      selected: [], //选中数据
      total: 0,
      dialogType: 'add',
      dialogVisible: false,
      dialogSetting: false,
      selectedTableTitle: '选择案卷元数据：',
      returnState: 20000,
      dictDataTransition: [],
      metaDataTypeOptions: [],
      modelForm: {
        pid: '',
        modelName: '',
        modelDesc: '',
        modelType: '',
        archiveTypeIds: '',
        archiveTypeName: '',
        archiveTableLevel: '1',
      },
      archiveTypeNameOptions: [],
      modelTypeOptions: [{
        value: '1',
        label: '案卷级'
      }, {
        value: '2',
        label: '文件级'
      }],
      modelDescOptions: [],
      mapperForm: {
        metadataName: '',
        metadataId: '',
        metaDataLen: '',
        archiveTypeName: '',
        mapperType: '',
      },
      selectedTableData: [
        // {metadataId: '', metadataName: '', sourceName: '', sourceOrder: '', metadataType: '', attrOrdinal: ''},
        {metadataId: '', metadataName: '', sourceName: '',  metadataType: '', attrOrdinal: ''},
      ],
      selectedTableArr: [], //被选择元数据弹窗
      modelRules: {
        modelName:
          [{required: true, message: "模板名称不能为空", trigger: "blur"},
            {max: 20, message: '模板名称长度最大为20'}],
        archiveTypeIds: [{required: true, message: "档案类型不能为空", trigger: "change"},],
        archiveTableLevel: [{required: true, message: "模板类型不能为空", trigger: "change"},],

      },
    }
  },
  created() {
    this.selecctModel();
    this.getDictDataTransitionId();
  },
  methods: {
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
        if (item.metadataType == cellValue) {
          result = item.label;
        }
      });
      return result;
    },
    /**
     * 根据档案类型查询模板
     */
    selecctModel() {
      selecctModelByArchiveType(this.queryParams.archiveTypeId).then(res => {
        if (res.code === this.returnState) {
          if (res.data)
            this.treeData = JSON.parse(JSON.stringify(res.data).replace(/modelName/g, "archiveName"));
          this.handleQuery();
        }
      })
    },
    /*条件查询*/
    conditionalQueries() {
      this.queryParams.pageSize = 20;
      this.queryParams.pageNum = 1;
      this.handleQuery();
    },
    /**
     * 查询模板
     */
    handleQuery() {
      if (this.activeName === 'first') {
        searchData(this.queryParams).then(res => {
          if (res.code === this.returnState) {
            this.modelTable = res.data;
            this.total = res.total;
            if (this.modelTable) {
              this.modelTable.forEach((item, index) => {
                item.rowIndex = (this.queryParams.pageNum - 1) * this.queryParams.pageSize + index + 1
              })
            }
          }
        })
      } else if (this.activeName === 'second') {
        mapperSearchData(this.queryParams).then(res => {
          if (res.code === this.returnState) {
            this.metaTable = res.data;
            if (this.metaTable) {
              this.metaTable.forEach((item, index) => {
                item.rowIndex =  index + 1
              })
            }
          }
        });
      }
    },
    /*点击tree事件*/
    handleNodeClick(val, node, component) {
      if (node.level === 1) {
        this.queryParams.archiveTypeId = val.id;
        this.activeName = 'first';
        this.handleQuery();
      } else if (node.level === 2 || node.level === 3) {
        this.currentVal = val;
        // this.queryParams={};
        this.queryParams.modelId = val.id;
        this.activeName = 'second';
        this.handleQuery();
      }
    },
    /*清空模板表单*/
    resetModel() {
      this.modelForm = {
        modelName: '',
        pid: '',
        modelDesc: '',
        modelType: '',
        archiveTypeName: '',
        archiveTypeId: '',
        archiveTypeIds: [],
        archiveTableLevel: '1',
      }
    },
    /**
     * 新增模板
     */
    handleAdd() {
      this.dialogType = 'add';
      this.resetModel();
      this.dialogVisible = true;
      this.modelForm.archiveTableLevel = '1';
      this.archiveTypeChange('1');
    },
    /**
     * 弹窗内模板类型变化
     */
    archiveTableLevelChange(val) {
      this.archiveTypeChange(val)
    },
    /**
     * 级联面板切换
     */
    handleCascader(val) {
      if (this.modelForm.archiveTableLevel === '1') {
        this.modelForm.archiveTableName = this.$refs.modelCascader.getCheckedNodes()[0].data.tableLevel1En;
        this.modelForm.archiveTypeName = this.$refs.modelCascader.getCheckedNodes()[0].data.label;
      } else if (this.modelForm.archiveTableLevel === '2') {
        this.modelForm.archiveTableName = this.$refs.modelCascader.getCheckedNodes()[0].parent.data.tableLevel2En;
        this.modelForm.archiveTypeName = this.$refs.modelCascader.getCheckedNodes()[0].parent.data.label;
      }
    },
    /**
     * 弹窗内档案类型变化
     */
    archiveTypeChange(val) {
      this.archiveTypeNameOptions = JSON.parse(JSON.stringify(this.treeData));
      this.archiveTypeNameOptions = JSON.parse(JSON.stringify(this.archiveTypeNameOptions).replace(/archiveName/g, "label"));
      this.archiveTypeNameOptions = JSON.parse(JSON.stringify(this.archiveTypeNameOptions).replace(/id/g, "value"));
      this.archiveTypeNameOptions = JSON.parse(JSON.stringify(this.archiveTypeNameOptions).replace(/dahcModelLv/g, "children"));
      /*案卷级*/
      if (val === '1') {
        this.archiveTypeNameOptions.map(item => {
          item.children = undefined;
        });
      } else if (val === '2') {
        /*文件级*/
        this.archiveTypeNameOptions.map(item => {
          if (!item.children) {
            item.disabled = true;
          } else {
            item.children.map(item1 => {
              item1.children = undefined;
            });
          }
        });
      }
      this.modelForm.archiveTypeIds = [];
    },
    /**
     * 保存模板弹窗
     */
    handleSave(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.dialogType == 'add') {
            console.log(this.modelForm,"模版")
            if (this.modelForm.archiveTableLevel === '1') {
              this.modelForm.pid = '0';
              this.modelForm.archiveTypeId = this.modelForm.archiveTypeIds[0];
            } else {
              this.modelForm.pid = this.modelForm.archiveTypeIds[1];
              this.modelForm.archiveTypeId = this.modelForm.archiveTypeIds[0];
            }
            addData(this.modelForm).then(res => {
              if (res.code == this.returnState) {
                this.$message.success("新增成功")
                this.dialogVisible = false;
                this.selecctModel();
              }
            });
          } else {
            updateData(this.modelForm).then(res => {
              if (res.code == this.returnState) {
                this.$message.success("修改成功")
                this.dialogVisible = false;
                this.selecctModel();
              }
            });
          }
        } else {
          return false;
        }
      });
    },

    /**
     * 编辑模板
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
        this.dialogType = 'edit';
        this.dialogVisible = true;
        Object.assign(this.modelForm, this.selected[0]);
      }
    },
    /**
     * 删除模板
     */
    handleDeleted() {
      console.log('this.selected', this.selected)
      if (!this.selected || this.selected === [] || this.selected.length === 0 || this.selected.length > 1) {
        this.$message({
          message: '请选择一条数据',
          type: 'warning'
        });
      } else {
        if (!this.selected[0].dahcModelLv|| this.selected[0].dahcModelLv === [] ||this.selected[0].dahcModelLv.length === 0) {
          this.$confirm('此操作将永久删除选中数据, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }).then(() => {
            delData(this.selected[0].id).then(res => {
              this.$message.success("删除成功")
              this.selecctModel();
            });
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除'
            });
          });
        } else {
          this.$message({
            message: `当前案卷模版的数据下存在文件模版数据，不可继续删除`,
            type: 'warning'
          });
        }
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
     * 设置映射
     */
    handleSetMapper(item) {
      this.dialogSetting = true;
      this.modelForm = item;
      this.modelForm.modelName = item.modelName || item.archiveName;
      if (item.archiveTableLevel === '1') {
        this.dialogType = 'case';
        this.selectedTableTitle = '选择案卷元数据';
      } else if (item.archiveTableLevel === '2') {
        this.dialogType = 'file';
        this.selectedTableTitle = '选择文件元数据';
      }
      let obj = {};
      obj.id = item.archiveTypeId;
      obj.level = item.archiveTableLevel;
      selectListAndMetadata(obj).then(res => {
        if (res.code === this.returnState) {
          this.metaDataCNOptions = res.data[0].dahcMetadataList;
        }
      })
      let mataObj = {};
      mataObj.archiveTypeId = item.archiveTypeId;
      mataObj.id = item.id;
      selectMapperByModel(mataObj).then(response => {
        if (response.code === this.returnState) {
          // this.selectedTableData = res.data[0].dahcBusinessMapperList;
          let arr = response.data[0].dahcBusinessMapperList;
          if (arr.length === 0 || !arr || arr == undefined) {
            this.selectedTableData = [{
              metadataId: '',
              metadataName: '',
              sourceName: '',
              // sourceOrder: '',
              metadataType: ''
            },];
          } else {
            this.selectedTableData = arr;
            this.selectedTableData.map(item => {
              this.metaDataTypeOptions.forEach((item2) => {
                if (item.metadataType === item2.value) {
                  item.metadataType = item2.label;
                }
              });
            })
          }
        }
      })
    },
    /**
     * 移除字段
     */
    handleRemove() {
      if (!this.selected || this.selected === [] || this.selected.length === 0) {
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
          let ids = this.selected.map(item => item.id).toString()
          delMapperData(ids).then(res => {
            this.$message.success("删除成功")
            this.selecctModel();
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
     * 映射弹窗表格选中的值发生变化
     */
    handleChangeMeta(row) {
      // if (this.metaDataCNOptions) {
      //   this.metaDataCNOptions.forEach(item => {
      //     row.metadataId = item.id;
      //     row.metadataName = item.metadataName;
      //     row.metadataType = item.metadataType;
      //     row.metadataWidth = item.metadataWidth;
      //     this.metaDataTypeOptions.forEach((item2) => {
      //       if (row.metadataType === item2.value) {
      //         row.metadataType = item2.label;
      //       }
      //     });
      //   })
      // }
      // 元数据下拉框选择任意一个后，元数据类型、元数据长度同步变成对应的值
      let item = this.metaDataCNOptions.find(item => item.id === row.metadataId);
      /*判断是否可选择*/
      if (item) {
        row.metadataName = item.metadataName;
        row.sourceName = item.metadataName;
        row.metadataType = item.metadataType;
        row.metadataLong = item.metadataLong;
        row.metadataDesc = item.metadataDesc;
        row.attrOrdinal = item.attrOrder;
        this.metaDataTypeOptions.forEach((item2) => {
          if (row.metadataType === item2.value) {
            row.metadataType = item2.label;
          }
        });
        /*判断是否可选择*/
      } else {
        row.metadataName = '';
        row.metadataType = '';
        row.metadataLong = '';
        row.metadataDesc = '';
        row.attrOrdinal = '';
      }
    },
    /**
     * 清空时，被清空的值可被再次选择，判断tableData与选择数组中metaDataCNOptions的
     */
    handleClear(row) {
      row.metadataName = '';
      row.metadataType = '';
      row.metadataWidth = '';
      row.sourceName = '';
      row.id = '';
      // row.sourceOrder = 1;
      row.attrOrdinal = '';
    },
    /**
     * 设置映射弹窗内新增映射
     */
    addItem(arr,index) {
      // let obj = {metadataId: '', metadataName: '', sourceName: '', sourceOrder: '', metadataType: '', attrOrdinal: ''};
      let obj = {metadataId: '', metadataName: '', sourceName: '',  metadataType: '', attrOrdinal: ''};
      // arr.push(obj);
      arr.splice(index+1,0,obj);
    },
    // 移除映射
    deleteItem(arr, index) {
      arr.splice(index, 1);
    },
    /**
     * 保存映射
     */
    handleSaveMapper() {
      if (this.selectedTableData.length === 1) {
        if (this.selectedTableData.metadataName) {
          if (!this.selectedTableData.sourceName) {
            this.$message.warning("映射字段名称不能为空");
          }
        } else if (this.selectedTableData.sourceName) {
          if (!this.selectedTableData.metadataName) {
            this.$message.warning("元数据名称不能为空");
          }
        } else {
          this.modelForm.dahcBusinessMapperList = this.selectedTableData;
          bulkAdditions(this.modelForm).then(res => {
            if (res.code === this.returnState) {
              this.$message.success("设置成功");
              this.dialogSetting = false;
              this.selecctModel();
            }
          })
        }
      } else {
        // let orders = this.selectedTableData.map(item => item.sourceOrder);
        let sourceNames = this.selectedTableData.map(item => item.sourceName.replace(/\s+/g, ''));
        // let orderSet = new Set(orders);
        let sourceNameSet = new Set(sourceNames);
        // let sourceOrderSome = this.selectedTableData.some(tableItem => !tableItem.sourceOrder);
        let metadataNameSome = this.selectedTableData.some(tableItem => !tableItem.metadataName);
        let sourceNameSome = this.selectedTableData.some(tableItem => !tableItem.sourceName);
        let arrIndex = this.selectedTableData.findIndex(tableItem => tableItem.metaDataName == '');
        if (arrIndex !== -1) {
          this.selectedTableData.splice(arrIndex, 1);
        }
      if (sourceNameSet.size !== sourceNames.length) {
          this.$message.warning("映射字段名称不能重复");
        }  else if (metadataNameSome) {
          this.$message.warning("元数据名称不能为空");
        } else if (sourceNameSome) {
          this.$message.warning("映射字段名称不能为空");
        } else {
          this.modelForm.dahcBusinessMapperList = this.selectedTableData;
          bulkAdditions(this.modelForm).then(res => {
            if (res.code === this.returnState) {
              this.$message.success("设置成功");
              this.dialogSetting = false;
              this.selecctModel();
            }
          })
        }
      }
    },
    /*清空元数据表格*/
    resetCreateMapperData() {
      this.selectedTableData = [
        // {metadataId: '', metadataName: '', sourceName: '', sourceOrder: '', metadataType: '', attrOrdinal: ''}
        {metadataId: '', metadataName: '', sourceName: '',  metadataType: '', attrOrdinal: ''}
      ]
    },
/*    // 上移
    moveUp(item, index) {
      this.selectedTableData.splice(index - 1, 0, item); // 定位到点击位置的上一行，0即不删除如何元素，在此位置插入item
      this.selectedTableData.splice(index + 1, 1); // 此时数组中有重复元素，把原来被挤下去的元素删除
    },
    // 下移
    moveDown(item, index) {
      this.selectedTableData.splice(index + 2, 0, item);
      this.selectedTableData.splice(index, 1);
    },*/
    /*下移*/
    moveDown(arr, item, index) {
      arr.splice(index + 2, 0, item);
      arr.splice(index, 1);
    },
    /*上移*/
    moveUp(arr, item, index) {
      arr.splice(index - 1, 0, item);  // 定位到点击位置的上一行，0即不删除如何元素，在此位置插入item
      arr.splice(index + 1, 1); // 此时数组中有重复元素，把原来被挤下去的元素删除
    },
    // 控制下移按钮的显示与隐藏
    getFormLength(index) {
      if (index === this.selectedTableData.length - 1) {
        return true;
      } else {
        return false;
      }
    },


  }
}
</script>

<style lang="scss" scoped>
.modelTab {
  height: 100%;

  ::v-deep .el-tabs__content {

    .tablePage {
      height: calc(100% - 100px) !important;
    }
  }
}

.mapperDialog {
  ::v-deep .ipt {
    justify-content: start;
    margin-top: 5px;
    padding-left: 5px;

    .el-form-item {
      width: 40%;

      .el-form-item__label {
        width: 65px;
      }
    }
  }

  .tablePage {
    height: calc(85vh - 220px) !important;
  }
}
</style>
