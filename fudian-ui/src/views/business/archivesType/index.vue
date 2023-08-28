<template>
  <div class="app-container-padding">
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="iptAndBtn" @submit.native.prevent>
      <el-form-item label="类型名称" prop="archiveTypeName">
        <el-input v-model="queryParams.archiveTypeName" placeholder="请输入类型名称" @keyup.enter.native="conditionalQueries"
                  clearable/>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" size="mini" @click="conditionalQueries"
                 v-hasPermi="['business:archivesType:list']">搜索
      </el-button>
    </el-form>
    <div class="btn">
      <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd"
                 v-hasPermi="['business:archivesType:add']">
        新增类型
      </el-button>
      <el-button type="primary" icon="el-icon-edit" size="mini" @click="handleUpdate"
                 v-hasPermi="['business:archivesType:edit']">
        编辑类型
      </el-button>
      <el-button type="primary" icon="el-icon-delete" size="mini" @click="handleDelete"
                 v-hasPermi="['business:archivesType:delete']">
        删除类型
      </el-button>
    </div>
    <TablePage class="tablePage" ref="archivesRef" :tableData="tableData" :pageSize="queryParams.pageSize" :pageNum="queryParams.pageNum"
               :total="total" @handleSelectionChange="handleSelectionChange" @handleChange="handleChange">
      <template slot="table">
        <el-table-column show-overflow-tooltip
                         v-for="item in tableColumn"
                         :key="item.id"
                         :prop="item.prop"
                         :label="item.label"
                         :min-width="item.width"
                         :formatter="item.formatter">

        </el-table-column>
        <el-table-column
          label="操作"  fixed="right"
          width="100" class-name="operation">
          <template slot-scope="scope">
            <el-button type="text" size="mini" @click="handleSetMapper(scope.row)">
              设置映射
            </el-button>
          </template>
        </el-table-column>
      </template>
    </TablePage>
    <el-dialog :title="dialogType==='add'?'新增档案类型':dialogType==='edit'?'编辑档案类型':''" :visible.sync="dialogVisible"
               :close-on-click-modal="false" :close-on-press-escape="false" :destroy-on-close="true" width="500px"
               class="dialog-style">
      <el-form ref="typeFormRef" :model="typeForm" size="mini" :rules="typeRules" :inline="true" class="ipt">
        <el-form-item label="id" prop="id" v-show="false">
          <el-input v-model="typeForm.id" placeholder="id" maxlength="30"/>
        </el-form-item>
        <el-form-item label="类型名称" prop="archiveName" class="remarks">
          <el-input v-model="typeForm.archiveName" placeholder="类型名称" maxlength="30" clearable/>
        </el-form-item>
        <el-form-item label="类型描述" prop="archiveDesc" class="remarks">
          <el-input v-model="typeForm.archiveDesc" placeholder="类型描述" maxlength="30" clearable type="textarea"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
				<el-button type="primary" size="mini" @click="handleSave('typeFormRef')">保存</el-button>
				<el-button size="mini" @click="dialogVisible=false">取消</el-button>
			</span>
    </el-dialog>
    <el-dialog title="设置映射"
               :visible.sync="setMapperDialog"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="800px" class="dialog-style mapperDialog">
      <el-form ref="mapperFormRef" :model="mapperForm" size="mini" :rules="mapperRules" :inline="true" class="ipt">
        <el-form-item label="档案类型" prop="archiveTypeId">
          <el-select v-model="mapperForm.archiveTypeId" placeholder="请选择档案类型" maxlength="30" disabled>
            <el-option v-for="metaDataItem in archivesTypeNameOptions"
                       :key="metaDataItem.value"
                       :label="metaDataItem.label"
                       :value="metaDataItem.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="映射级别" prop="archiveLevelName">
          <el-select v-model="mapperForm.archiveLevelName" placeholder="请选择映射级别" maxlength="30"
                     @change="rankMap">
            <el-option v-for="metaDataItem in mapperTypeOptions"
                       :key="metaDataItem.value"
                       :label="metaDataItem.label"
                       :value="metaDataItem.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <TablePage class="tablePage"
                 :is-title="true"
                 :tableTitle="tableTitle"
                 :tableData="createMapperData"
                 :isPage="false"
                 :hasSelection="false"
                 ref="setMapperRef">
        <template slot="table">
          <!--          <el-table-column
                      label="表格展示序号" width="160">
                      <template slot-scope="scope">
                        <el-input-number size="mini" v-model="scope.row.sort" :min="1" :step="1" clearable
                                         style="width: 130px;"></el-input-number>
                      </template>
                    </el-table-column>-->
          <el-table-column
            label="元数据名称">
            <template slot-scope="scope">
              <el-select size="mini"
                         v-model="scope.row.metaDataCN"
                         placeholder="请选择元数据中文名称"
                         maxlength="30"
                         filterable
                         @change="handleChangeMeta(scope.row)"
                         clearable>
                <el-option v-for="metaDataItem in metaDataCNOptions"
                           :key="metaDataItem.value"
                           :label="metaDataItem.label"
                           :value="metaDataItem.value"
                           :disabled="!!createMapperData.find(val => val.metaDataCN === metaDataItem.value)"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column
            prop="metadataType"
            label="元数据类型" :formatter="metadataTypeFormatter" width="150px">
          </el-table-column>
          <!--          <el-table-column-->
          <!--            label="类型描述"-->
          <!--            prop="metadataDesc">-->
          <!--          </el-table-column>-->
          <el-table-column
            label="操作"  fixed="right"
            width="200" class-name="operation">
            <template slot-scope="scope">
              <el-button type="text" size="mini" :disabled="scope.$index===0"
                         @click="moveUp(createMapperData,scope.row,scope.$index)">
                上移
              </el-button>
              <el-button type="text" size="mini" :disabled="scope.$index===createMapperData.length-1"
                         @click="moveDown(createMapperData,scope.row,scope.$index)">
                下移
              </el-button>
              <el-button type="text" size="mini" @click="handleAddMapper(createMapperData,scope.$index)">
                新增
              </el-button>
              <el-button type="text" size="mini" :disabled="scope.$index===0"
                         @click="handleMoveMapper(createMapperData,scope.$index)">
                移除
              </el-button>
            </template>
          </el-table-column>
        </template>
      </TablePage>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" size="mini" @click="handleSaveMapper('mapperFormRef')">保存</el-button>
        <el-button size="mini" @click="setMapperDialog=false">取消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import TablePage from "@/components/Public/table/TablePage.vue";
  import {
    listData,
    searchData,
    addData,
    updateData,
    queryArchiveAndMetadata,
    updateTheMapping,
    delData
  } from "@/api/business/archiveType"
  import {queryMetadataSelect} from "@/api/business/metadata"
  import {formatDate} from "@/utils";
  import {queryDictDataTransitionId} from "@/api/dahc/sys/dictData";

  export default {
    name: "index",
    components: {
      TablePage
    },

    data() {
      return {
        queryParams: {
          archiveTypeName: '',
          pageSize: 20,
          pageNum: 1,
        },
        tableData: [],
        tableColumn: [
          {label: '类型名称', prop: 'archiveName', width: '100'},
          {label: '类型描述', prop: 'archiveDesc', width: '100'},
          {label: '创建人', prop: 'createByName', width: '100'},
          {label: '创建时间', prop: 'createTime', width: '100', formatter: formatDate},
          // {label: '修改人', prop: 'updateByName', width: '60'},
          // {label: '修改时间', prop: 'updateTime', width: '100', formatter:formatDate},
        ],
        selected: [], //选中数据
        total: 0,
        dialogType: 'add',
        dialogVisible: false,
        typeForm: {
          id: '',
          archiveDesc: '',
          archiveName: '',
        },
        setMapperDialog: false,
        tableTitle: '选择映射关系：',
        mapperForm: {},
        metaDataCNOptions: [],
        metaDataTypeOptions: [],
        archivesTypeNameOptions: [
          {
            value: '',
            label: ''
          }
        ],
        mapperTypeOptions: [
          {
            value: 'case',
            label: '案卷级'
          },
          {
            value: 'file',
            label: '文件级'
          }
        ],
        createMapperData: [
          {metaDataCN: '', metaDataName: '', sort: ''},
        ],
        mapperRules: {
          metaDataName: [
            {required: true, message: "元数据名称不能为空", trigger: "blur"},
          ],
          mapperRange: [
            {required: true, message: "映射范围不能为空", trigger: "change"},
          ],
        },
        typeRules: {
          archiveName: [
            {required: true, message: "档案类型名称不能为空", trigger: "blur"},
            {max: 10, message: "档案类型名称长度最大为10"}
          ],
        },
      }
    },
    created() {
      this.handleQuery();
      // this.queryMetadataSelect();
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
        this.queryParams.dictType = null;
        searchData(this.queryParams).then(res => {
          if (res.code === 20000) {
            this.tableData = res.data
            this.total = res.total
          }
        });
      },
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
      /**
       * 新增类型
       */
      handleAdd() {
        this.dialogType = 'add';
        this.dialogVisible = true;
        this.typeForm = {
          id: '',
          archiveDesc: '',
          archiveName: '',
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
          this.dialogType = 'edit';
          this.dialogVisible = true;
          Object.assign(this.typeForm, this.selected[0])
        }
      },
      /**
       * 删除档案类型
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
            }).toString()
            delData(ids).then(res => {
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
      /*清空元数据表单*/
      reset() {
        this.mapperForm = {
          metaDataName: '',
          metaDataCN: '',
          metaDataLen: '',
          archivesTypeName: '',
          metadataDtos: [],
          mapperType: '',
          /*元数据id*/
          ids: [],
          sorts: [],
          /*档案类型*/
          archiveTypeId: '',
          /*表名称*/
          archiveLevelName: '',
        }
      },
      /*清空元数据表格*/
      resetCreateMapperData() {
        this.createMapperData = [
          {metaDataCN: '', metaDataName: '', sort: ''},
        ]
      },
      /*映射级别值改变*/
      rankMap(value) {
        /*查询元数据*/
        queryMetadataSelect().then(res => {
          this.metaDataCNOptions = res
          const arr = {archiveLevelName: value}
          /*回显数据*/
          queryArchiveAndMetadata(arr).then(res => {
            if (res.data[0] == undefined) {
              this.resetCreateMapperData();
            } else {
              res.data.map(item => {
                item.metaDataCN = item.metadataId;
                // /*判断是否可选择*/
                // this.metaDataCNOptions.map(item1 => {
                //   if (item1.value == item.metadataId) {
                //     item1.disabled = true;
                //   }
                // });
              });
              this.createMapperData = res.data
            }
          });
        });
      },
      queryMetadataSelect() {
        /*查询元数据*/
        queryMetadataSelect().then(res => {
          this.metaDataCNOptions = res
          console.log(this.metaDataCNOptions, "元数据")
        });
      },
      /**
       * 设置映射
       */
      handleSetMapper(item) {
        this.reset();
        queryMetadataSelect().then(response => {
          this.metaDataCNOptions = response
          /*回显档案类型*/
          this.archivesTypeNameOptions[0].label = item.archiveName
          this.archivesTypeNameOptions[0].value = item.id
          this.mapperForm.archiveTypeId = item.id
          /*回显案卷级别*/
          this.mapperTypeOptions[0].value = item.tableLevel1En
          this.mapperTypeOptions[1].value = item.tableLevel2En
          this.mapperForm.archiveLevelName = item.tableLevel1En
          const arr = {archiveLevelName: this.mapperForm.archiveLevelName}
          /*回显数据*/
          queryArchiveAndMetadata(arr).then(res => {
            if (res.data[0] == undefined) {
              this.resetCreateMapperData();
            } else {
              res.data.map(item => {
                item.metaDataCN = item.metadataId;
              })
              this.createMapperData = res.data;
              // //去重
              // this.createMapperData = Array.from(new Set(res.data.map(item => item.metaDataCN))).map(metaItem => {
              //   return res.data.find(item => item.metaDataCN === metaItem);
              // });
              // // 如果当前映射元数据的数量大于1个，则移除metaDataCN为空的
              // if (this.createMapperData.length > 1) {
              //   let arrIndex = this.createMapperData.findIndex(tableItem => !tableItem.metaDataCN)
              //   this.createMapperData.splice(arrIndex, 1);
              // }
            }
          });
          this.setMapperDialog = true;
        })
      },


      /*保存映射关系*/
      handleSaveMapper(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            //去重
            console.log(this.createMapperData, "集合")
            this.createMapperData = Array.from(new Set(this.createMapperData.map(item => item.metaDataCN))).map(metaItem => {

              return this.createMapperData.find(item => item.metaDataCN === metaItem);
            });
            console.log('   this.createMapperData',   this.createMapperData)
            // 如果当前映射元数据的数量大于1个，则移除metaDataCN为空的
            if (this.createMapperData.length > 1) {
              let arrIndex = this.createMapperData.findIndex(tableItem => tableItem.metaDataName == '');
              if (arrIndex !== -1) {
                this.createMapperData.splice(arrIndex, 1);
              }
            }
            /*          this.createMapperData.forEach(item => {
                        this.mapperForm.ids.push(item.metaDataCN)
                        this.mapperForm.sorts.push(item.sort)
                      });
                      */
            this.createMapperData.forEach((item, index) => {
              this.mapperForm.metadataDtos.push({metadataId: ''})
              this.mapperForm.metadataDtos[index].metadataId = item.metaDataCN
              this.metaDataCNOptions.map(item1 => {
                if (item.metaDataCN == item1.value) {
                  this.mapperForm.metadataDtos[index].metadataName = item1.label;
                }
              });
              this.mapperForm.metadataDtos[index].sort = item.sort
            });
            updateTheMapping(this.mapperForm).then(res => {
              if (res.code == 20000) {
                this.$modal.msgSuccess("修改成功");
                this.setMapperDialog = false;
              }
            }).catch(
              this.mapperForm.metadataDtos = []
            );
          } else {
            return false;
          }
        });
      },

      /**
       * 表格复选框
       */
      handleSelectionChange(selection) {
        this.selected = selection
        // 清除 所有勾选项
        if (selection.length === 1) {
          this.selected = selection;
        }
        if (selection.length > 1) {
          this.$refs.archivesRef.$refs.tableRef.clearSelection();
          this.$refs.archivesRef.$refs.tableRef.toggleRowSelection(selection.at(-1), true);
          this.selected = selection.slice(-1);
        }
        if (selection.length === 0) {
          this.selected = [];
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
      /**
       * 保存弹窗
       */
      handleSave(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.dialogType == 'edit') {
              updateData(this.typeForm).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.dialogVisible = false;
                this.handleQuery();
              });
            } else {
              addData(this.typeForm).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.dialogVisible = false;
                this.handleQuery();
              });
            }
          } else {
            return false;
          }
        });
      },
      /**
       * 映射弹窗表格选中的值发生变化
       */
      handleChangeMeta(row) {
        // 元数据下拉框选择任意一个后，元数据类型、元数据长度同步变成对应的值
        let item = this.metaDataCNOptions.find(item1 => item1.value === row.metaDataCN);
        /*判断是否可选择*/
        if (item) {
          row.metaDataName = item.value;
          row.metadataType = item.metadataType;
          row.metadataLong = item.metadataLong;
          // row.metadataDesc = item.metadataDesc;
          /*判断是否可选择*/
        } else {
          row.metadataType = '';
          row.metadataLong = '';
          // row.metadataDesc = '';
        }
      },
      /**
       * 新增映射
       */
      handleAddMapper(arr, index) {
        console.log('arr', arr, 'index', index)
        let obj = {metaDataCN: '', metaDataName: '', sort: index};
        // arr.push(obj);
        arr.splice(index + 1, 0, obj);
      },
      /*移除*/
      handleMoveMapper(arr, index) {
        arr.splice(index, 1)
      }
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
      width: calc(100% - 10px);
      margin: 5px;

      .el-form-item {
        width: 40%;

        .el-form-item__label {
          width: 65px;
        }
      }
    }

    .tablePage {
      height: calc(90vh - 220px) !important;
    }
  }
</style>
