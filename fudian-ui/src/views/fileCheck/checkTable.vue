<template>
  <div class="checkTable">
    <div class="tableTitle" v-show="isTitle">核查信息：</div>
    <div class="table">
      <el-table
        :data="tableData"
        style="width: 100%"
        border
        height="100%"
        ref="checkTableRef"
        @row-click="handleRowClick"
        :row-style="{height:'20px'}">
        <el-table-column
          type="index"
          label="序号"
          width="55">
        </el-table-column>
        <el-table-column
          v-for="(item,index) in tableColumn"
          :key="index"
          :prop="item.prop"
          :label="item.label"
          :min-width="item.width"
          :class-name="item.className">
        </el-table-column>
        <el-table-column label="核查结果" v-if="!isImpeach" width="100" class-name="checkCell">
          <template slot-scope="scope">
            <el-checkbox-group v-model="scope.row.checkResult"
                               @change="changeCheckResult(scope.row.checkResult,scope.$index,tableData,'processMode')">
              <el-checkbox v-for="(checkItem, index) in checkList" :key="index" :label="checkItem.label"
                           :checked="checkItem.checked">{{ checkItem.value }}
              </el-checkbox>
            </el-checkbox-group>
          </template>
        </el-table-column>
        <el-table-column label="不合格件号/页号" class-name="inputNum" width="240"
                         v-if="!isImpeach&&(this.routePath!=='First')">
          <template slot-scope="scope">
            <div v-for="(item, index) in scope.row.pageNumS" class="caseNumS">
              <div style="display: inline-flex;align-items: center"
                   v-show="scope.row.showPageNumber==0 || scope.row.showPiece==0">
                <!--                <div v-show="scope.row.showPageNumber==0 || scope.row.showPiece==0">-->
                第
                <el-input v-model="item.arrItem" size="mini" onkeyup="value=value.replace(/^(0+)|[^\d]+/g,'') "
                          :disabled="scope.row.checkResult=='qualified'"></el-input>
                件
                <!--                </div>-->
                <div v-show="scope.row.showPageNumber!==1">
                  <el-input v-model="item.arrPage" size="mini" onkeyup="value=value.replace(/^(0+)|[^\d]+/g,'') "
                            :disabled="scope.row.checkResult=='qualified'"></el-input>
                  页
                </div>

              </div>
              <div v-show="scope.row.showPageNumber==0 || scope.row.showPiece==0">
                <el-button type="text" size="mini" @click.prevent="addNumS(scope.row.pageNumS,index)"
                           :disabled="scope.row.pageNumS &&(scope.row.checkResult[0]!=='unqualified')"
                           style="margin-left: 10px;">新增
                </el-button>
                <el-button type="text" size="mini"
                           @click.prevent="removeNumS(scope.row.pageNumS,item)"
                           :disabled="index===0||(scope.row.pageNumS &&(scope.row.checkResult[0]!=='unqualified'))">移除
                </el-button>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="处理方式" width="100">
          <template slot-scope="scope">
            <el-checkbox-group v-model="scope.row.processMode"
                               @change="changeCheckResult(scope.row.processMode,scope.$index,tableData,'changeContent')"
                               :disabled="scope.row.checkResult[0]!=='unqualified'">
              <el-checkbox v-for="(modeItem, index) in modeList" :key="index" :label="modeItem.label"
                           :checked="modeItem.checked">{{ modeItem.value }}
              </el-checkbox>
            </el-checkbox-group>
          </template>
        </el-table-column>
        <el-table-column label="存疑件号/页号" class-name="inputNum" width="210"
                         v-if="isShowPiece && isImpeach">
          <template slot-scope="scope">
            <div v-for="(item, index) in scope.row.impeachPageNumS" class="caseNumS">
              <div style="display: inline-flex;align-items: center">
                <div v-show="scope.row.showPageNumber==0 || scope.row.showPiece==0">
                  第
                  <el-input v-model="item.arrItem" size="mini"
                            onkeyup="value=value.replace(/^(0+)|[^\d]+/g,'') "></el-input>
                  件
                </div>
                <div v-show="scope.row.showPageNumber!==1">
                  <el-input v-model="item.arrPage" size="mini"
                            onkeyup="value=value.replace(/^(0+)|[^\d]+/g,'') "></el-input>
                  页
                </div>

              </div>
              <div>
                <!--                <el-button type="text" size="mini" @click.prevent="addNumS(scope.row.impeachPageNumS)"
                                           :disabled="scope.row.impeachPageNumS &&(scope.row.checkResult[0]!=='unqualified')"
                                           style="margin-left: 10px;">新增
                                </el-button>-->
                <el-button type="text" size="mini"
                           @click.prevent="removeNumS(scope.row.impeachPageNumS,item)"
                >
                  移除
                </el-button>
              </div>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="更改内容" v-if="isShowChangeContent" width="100">
          <template slot-scope="scope">
            <el-checkbox-group v-model="scope.row.changeContent" @change="changeCheckResult(scope.row.changeContent)"
                               :disabled="scope.row.checkResult[0]==='qualified'||(scope.row.processMode[0]!=='change')">
              <el-checkbox v-for="(contentItem, index) in contentList" :key="index" :label="contentItem.label"
                           :checked="contentItem.checked">{{ contentItem.value }}
              </el-checkbox>
            </el-checkbox-group>
          </template>
        </el-table-column>
        <el-table-column
          label="备注" width="100">
          <template slot-scope="scope">
            <el-input size="mini" v-model="scope.row.remark" type="textarea" :rows="1"></el-input>
          </template>
        </el-table-column>
      </el-table>
    </div>

  </div>
</template>

<script>
export default {
  name: "checkTable",
  props: {
    // 是否有表格标题
    isTitle: {
      type: Boolean,
      default: true
    },
    // 是否有表格存疑表头
    isImpeach: {
      type: Boolean,
      default: false
    },
    // 是否显示更改内容
    isShowChangeContent: {
      type: Boolean,
      default: false
    },
    sourcePage: {
      type: String,
      default: '',
    },
    // 表格数据
    tableData: {
      type: Array,
      default: () => []
    },
  },
  data() {
    return {
      checkList: [
        {value: '合格', label: 'qualified', checked: false},
        {value: '不合格', label: 'unqualified', checked: false},
      ],
      modeList: [
        {value: '更改', label: 'change', checked: false},
        {value: '存疑', label: 'impeach', checked: false},
      ],
      contentList: [
        {value: '原件', label: 'original', checked: false},
        {value: '复印件', label: 'accessory', checked: false},
      ],
      /*表头*/
      tableColumn: [
        {label: '核查项名称', prop: 'trueingName', width: '100', className: 'cellName'},
        {label: '核查项描述', prop: 'trueingDesc', width: '120', className: 'cellName'},
        {label: '核查标准', prop: 'examineStasString', width: '100', className: 'cellName'},
      ],
      routePath: '',
    }
  },

  mounted() {
    this.routePath = this.$route.path;
    if (this.$route.path.indexOf('First') !== -1) {
      this.routePath = 'First'
    }
    console.log('this.$route', this.routePath)
    let multipleTable = this.$refs.checkTableRef.$refs.bodyWrapper;
    multipleTable.addEventListener('scroll', () => {
      // 滚动距离
      const scrollLeft = multipleTable.scrollLeft
      this.$refs.checkTableRef.$refs.headerWrapper.scrollLeft = scrollLeft
    })
  },
  updated() {
    this.$nextTick(() => {
      if (this.$refs.checkTableRef) {
        this.$refs.checkTableRef.doLayout();
      }
    })
  },
  computed: {
    isShowPageNum() {
      if (this.tableData) {
        let pageNum = 0;
        for (let i = 0; i < this.tableData.length; i++) {
          if (this.tableData[i].showPageNumber == 0) {
            pageNum++;
          }
        }
        if (pageNum) {
          return true
        }
      } else {
        return false
      }
    },
    isShowPiece() {
      if (this.tableData) {
        let pageNum = 0;
        for (let i = 0; i < this.tableData.length; i++) {
          if (this.tableData[i].showPiece == 0) {
            pageNum++;
          }
        }
        if (pageNum) {
          return true
        }
      } else {
        return false
      }
    }
  },
  methods: {
    handleRowClick(row, column, event) {
      // this.$emit('handleRowClick', {row, column, event});
      this.$refs.checkTableRef.toggleRowSelection(row, column)
    },
    // 使用多选按钮实现单选功能
    changeCheckResult(checkList, index, arr, column) {
      checkList.length > 1 && checkList.shift()
      if (checkList[0] === 'qualified' || checkList[0] === 'impeach') {
        arr[index][column] = []
      }

    },
    /**
     * 新增核查标准
     */
    addNumS(arr, index) {
      let obj = {
        value: '',
        key: Date.now(),
      }
      arr.splice(index + 1, 0, obj);
      this.$refs.checkTableRef.doLayout();
    },
    /**
     * 移除核查标准
     */
    removeNumS(row, item) {
      console.log(item)
      let index = row.indexOf(item)
      if (index !== -1) {
        row.splice(index, 1)
      }
    },
    impeachRemoveNumS(row, item) {
      console.log(item, "hhhh")
      let index = row.indexOf(item)
      if (index !== -1) {
        row.splice(index, 1)
      }
    },
  }
}
</script>

<style lang="scss" scoped>
.checkTable {
  width: 100%;
  height: 100%;
  $fontSize: 12px;
  //margin-top: 10px;
  //ie浏览器兼容
  box-sizing: content-box;
  -moz-box-sizing: inherit;
  -webkit-box-sizing: inherit;
  //overflow: hidden;
  position: relative;

  .tableTitle {
    font-weight: 600;
    font-size: 13px;
    color: #606266;
    //color: #00afff;
    //margin-bottom: 5px;
    //padding: 5px 0 0 10px;
    position: absolute;
    left: 5px;
    top: -20px


  }

  .table {
    height: 100%;
    overflow: hidden;

    ::v-deep .el-table {
      border-right: 1px solid #dfe6ec;
      color: #909399;

      th {
        height: 34px !important;
      }

      & > .el-table__header-wrapper {
        & > table {
          tr {
            th {
              background: rgba(204, 204, 204, 0.18);
              color: #909399;
              font-size: 12px;
              text-align: center;
              height: 34px !important;
              padding: 5px 0 !important;
            }
          }

          & > colgroup {
            col {
              &:last-of-type {
                width: 17px !important;
              }
            }
          }
        }
      }

      .el-table__body-wrapper {
        .el-table__row {
          td {
            text-align: center;
            padding: 5px 0;
            font-size: 12px;

            .el-input.is-disabled .el-input__inner {
              color: #909399;
            }


            .el-checkbox-group {
              //padding-left: 10px;

              .el-checkbox {
                margin-right: 5px;
                font-size: 12px;
                width: 50px;
                max-width: 60px;
                text-align: left;
              }

              .el-checkbox__input {
                .el-checkbox__inner {
                  width: 12px;
                  height: 12px;
                }

                .el-checkbox__inner::after {
                  left: 3px;
                  top: 0;
                }
              }

              .el-checkbox__label {
                font-size: 12px;
                padding-left: 5px;
                font-weight: 400;
                color: #909399;
              }
            }
          }

          .checkCell {
            padding: 0;

            .cell {
              padding: 0 0 0 5px;
            }
          }

          .inputNum {
            .cell {
              padding: 0 5px;

              .caseNumS {
                width: 100%;
                margin-bottom: 5px;
                display: inline-flex;
                align-items: center;
                justify-content: space-between;
              }

              .el-input {
                width: 50px;
                margin: 0 3px;

                span {
                  width: 25px;
                }

                .el-input__inner {
                  padding: 0 5px;
                }
              }
            }
          }

          .cellName {
            .cell {
              word-break: keep-all;
              word-wrap: break-word;
              text-align: left;
            }
          }
        }
      }

      .el-table__cell {
        padding: 5px 0 !important;
      }
    }
  }
}


</style>


