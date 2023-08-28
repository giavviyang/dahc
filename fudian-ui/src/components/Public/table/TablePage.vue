<template>
  <div class="tableContainer" :class="{tableMarginTop:isTitle}">
    <div class="tableTitle" v-show="isTitle">{{ tableTitle }}</div>
    <div class="table" :class="{tableHeight:isPage}">
      <el-table
        :data="tableData"
        style="width: 100%"
        stripe
        highlight-current-row
        height="100%"
        ref="tableRef"
        v-if="refreshTable"
        :row-key="rowId"
        :default-expand-all="isExpandAll"
        :tree-props="treeProps"
        :default-sort="defaultSort"
        @selection-change="handleSelectionChange"
        @row-click="handleRowClick"
        :row-style="{height:'20px'}"
        :span-method="spanMethod"
      >
        <el-table-column
          type="selection"
          width="50" v-if="hasSelection">
        </el-table-column>
        <el-table-column
          type="index"
          :index="getIndex"
          label="序号"
          width="55" show-overflow-tooltip v-if="hasIndex">
        </el-table-column>
        <slot name="table"/>
      </el-table>
    </div>
    <div class="page" :class="{pageHeight:isPage}">
      <el-pagination
        :page-size="pageSize"
        :current-page="pageNum"
        :total="total"
        :page-sizes="[20, 50, 100, 200]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handlePageNum"
      />
    </div>
  </div>
</template>
<script>

export default {
  name: "TablePage",
  props: {
    // 是否有表格标题
    isTitle: {
      type: Boolean,
      default: false
    },
    tableTitle: {
      type: String,
      default: '',
    },
    // 表格复选框
    hasSelection: {
      type: Boolean,
      default: true
    },
    // 序号
    hasIndex: {
      type: Boolean,
      default: true
    },
    // 重新渲染表格状态
    refreshTable: {
      type: Boolean,
      default: true
    },
    // 是否展开，默认全部折叠
    isExpandAll: {
      type: Boolean,
      default: false,
    },
    // 是否展开，默认全部折叠
    rowId: {
      type: String,
      default: '',
    },
    treeProps: {
      type: Object,
      default: () => {
      }
    },
    defaultSort: {
      type: Object,
      default: () => {
      }
    },
    // 表格数据
    tableData: {
      type: Array,
      default: () => []
    },
    spanMap: {
      type: Object,
      default: () => {
      }
    },
    //单页数据量
    pageSize: {
      type: Number,
      default: 20
    },
    //当前页码
    pageNum: {
      type: Number,
      default: 1
    },
    //分页数据总数
    total: {
      type: Number,
      default: 0
    },
    isPage: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      tableContainer: '',
      tableHeight: ''
    }
  },
  mounted() {
    let multipleTable = this.$refs.tableRef.$refs.bodyWrapper;
    multipleTable.addEventListener('scroll', () => {
      // 滚动距离
      const scrollLeft = multipleTable.scrollLeft
      this.$refs.tableRef.$refs.headerWrapper.scrollLeft = scrollLeft
    })
  },
  updated() {
    this.$nextTick(() => {
      if (this.$refs.tableRef) {
        this.$refs.tableRef.doLayout();
      }
    })
  },
  methods: {
    /* 翻页后序号连贯 */
    getIndex($index) {
      //  表格序号
      return (this.pageNum - 1) * this.pageSize + $index + 1;
    },
    handleSelectionChange(selection) {
      this.$emit('handleSelectionChange', selection);
    },
    handleRowClick(row, column, event) {
      this.$emit('handleRowClick', {row, column, event});
      this.$refs.tableRef.toggleRowSelection(row, column)
    },
    sortChange(val) {
      this.$emit('sortChange', val);
    },
    spanMethod({rowIndex, column: {property}, columnIndex}) {
      if (this.spanMap && this.spanMap[property]) {
        const _row = this.spanMap[property].spanArr[rowIndex];
        const _col = _row > 0 ? 1 : 0;
        return { // [0,0] 表示这一行不显示， [2,1]表示行的合并数
          rowspan: _row,
          colspan: _col,
        };
      }

    },

    handleSizeChange(pageSize) {
      this.$emit('handleChange', pageSize, this.pageNum);
    },
    handlePageNum(pageNum) {
      this.$emit('handleChange', this.pageSize, pageNum);
    },
  }
}
</script>

<style lang="scss" scoped>
.tableContainer {
  width: 100%;
  height: 100%;
  $fontSize: 12px;
  //ie浏览器兼容
  box-sizing: content-box;
  -moz-box-sizing: inherit;
  -webkit-box-sizing: inherit;
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
    //height: calc(100% - 38px);
    overflow: hidden;

    ::v-deep .el-table {
      //border-right: 1px solid #dfe6ec;
      border: 1px solid #dfe6ec;
      color: #909399;


      th {
        height: 34px !important;
      }

      & > .el-table__header-wrapper {
        & > table {
          tr:first-of-type {
            th {
              background: rgba(204, 204, 204, 0.18);
              color: #909399;
              font-size: 12px;
              text-align: center;
              height: 34px !important;
              padding: 5px 0 !important;
              border-right: 1px solid #dfe6ec;
            }
          }

          tr:nth-of-type(2) {
            th {
              background: #fff;
              color: #909399;
              font-size: 12px;
              text-align: center;
              padding: 0 !important;
              height: 34px;

              .el-input__inner {
                border: none;
                padding: 0;
                height: 20px;
              }

              .el-input__suffix {
                width: 12px;

                .el-input__icon {

                  //line-height: 23px;
                }
              }
            }
          }

          //& > colgroup {
          //  col {
          //    &:last-of-type {
          //      width: 17px !important;
          //    }
          //  }
          //}
        }
      }

      .el-table__body-wrapper {
        //overflow: auto !important;

        .el-table__row {
          td {
            text-align: center;
            padding: 5px 0 !important;
            font-size: 12px;
            border-right: 1px solid #dfe6ec;

            .el-input.is-disabled .el-input__inner {
              color: #606266;
            }

            .el-radio {
              margin-right: 20px;
              font-size: 12px;

              .el-radio__input {
                .el-radio__inner {
                  width: 10px;
                  height: 10px;
                }
              }

              .el-radio__label {
                font-size: 12px;
                padding-left: 5px;
                font-weight: 400;
              }
            }

            .el-radio:last-of-type {
              margin-right: 0;
            }
          }

          .textLeft {
            text-align: left;
          }

          .el-table-column--selection {
            .cell {
              padding-right: 10px;
            }
          }

          .operation {
            .cell {
              display: flex;
              justify-content: space-around;
              width: 100%;
              white-space: nowrap;
              overflow: hidden;
              text-overflow: ellipsis;

              .el-button {
                //width: 100%;
                height: 100%;
                position: relative;
                top: 0;
                right: 0;
                margin: 0 2px;
                font-size: 12px;

              }
            }
          }
        }

        .small-padding {
          .cell {
            color: #909399;
          }
        }

        .noticeType {
          .el-tag {
            background-color: transparent;
            color: #909399;
            border: none;
          }
        }
      }

      .el-table__fixed {
        height: 100% !important; //设置高优先，以覆盖内联样式
      }

      .el-table__fixed-right {
        height: 100%;

        .el-table__fixed-header-wrapper {
          .operation {
            .cell {
              text-align: center;
              color: #909399;
            }
          }
        }

        .el-table__fixed-body-wrapper {
          .operation {
            padding:5px;
            .cell {
              display: flex;
              justify-content: space-around;
              width: 100%;
              white-space: nowrap;
              overflow: hidden;
              text-overflow: ellipsis;
              //color: #909399;
              .el-button {
                //width: 100%;
                height: 100%;
                position: relative;
                top: 0;
                right: 0;
                margin: 0 2px;
                font-size: 12px;

              }
            }
          }
        }
      }

      .el-table__cell {
        padding:0 ;
      }
      .cell{
        line-height: 19px;
      }
    }
    .el-table--medium{
      .el-table__cell {
        padding:0;
      }
    }
  }

  .page {
    //height: 38px;
    display: none;
    text-align: right;

    ::v-deep .el-pagination {
      height: 30px;
      margin-top: 2px;
      font-size: $fontSize;

      & > .el-pagination__total {
        font-size: $fontSize;
      }

      & > .el-pagination__sizes {
        .el-input {
          input {
            font-size: $fontSize;
            height: 26px;
          }
        }
      }

      button {
        background-color: transparent;
      }

      & > .el-pager {
        li {
          font-size: $fontSize;
          background-color: transparent;
        }
      }

      & > .el-pagination__jump {
        font-size: $fontSize;

        .el-input {
          input {
            font-size: $fontSize;
            height: 26px;
          }
        }
      }
    }
  }

  .tableHeight {
    height: calc(100% - 32px);
  }

  .pageHeight {
    height: 32px;
    display: block;
  }
}

.tableMarginTop {
  margin-top: 5px;
}
</style>
