<template>
  <div class="tableContainer">
    <div class="tableTitle">{{ tableTitle }}</div>
    <div class="table">
      <el-table
        :data="tableData"
        style="width:100%"
        border
        :summary-method="getSummaries"
        :span-method="spanMethod"
        show-summary
        highlight-current-row
        height="100%"
        ref="tableRef"
        :row-style="{height:'20px'}">
        <slot name="table"/>
      </el-table>
    </div>
  </div>
</template>
<script>
export default {
  name: "summaryTable",
  props: {
    tableTitle: {
      type: String,
      default: '',
    },
    // 表格数据
    tableData: {
      type: Array,
      default: () => []
    },
    spanMap: {
      type: Object,
      default: () => {}
    },
  },
  data() {
    return {
    }
  },
  mounted() {
    let multipleTable = this.$refs.tableRef.$refs.bodyWrapper;
    multipleTable.addEventListener('scroll', () => {
      // 滚动距离
      const scrollLeft = multipleTable.scrollLeft
      this.$refs.tableRef.$refs.headerWrapper.scrollLeft = scrollLeft
    });
  },
  updated() {
    this.$nextTick(() => {
      if (this.$refs.tableRef) {
        this.$refs.tableRef.doLayout();
      }
    })
    console.log('this.tableTitle',this.tableTitle)
  },

  methods: {
    // 合计
    getSummaries(param) {
      const {columns, data} = param;
      const sums = [];
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '小计';
          return;
        }
        const values = data.map(item => Number(item[column.property]));
        if (!values.every(value => isNaN(value))) {
          sums[index] = values.reduce((prev, curr) => {
            const value = Number(curr);
            if (!isNaN(value)) {
              return prev + curr;
            } else {
              return prev;
            }
          }, 0);
          sums[index] += '';
        } else {
          sums[index] = '';
        }
      });

      return sums;
    },
    spanMethod({rowIndex, column: {property},columnIndex}) {
      if(this.spanMap&&this.spanMap[property]){
        const _row = this.spanMap[property].spanArr[rowIndex];
        const _col = _row > 0 ? 1 : 0;
        return { // [0,0] 表示这一行不显示， [2,1]表示行的合并数
          rowspan: _row,
          colspan: _col,
        };
      }

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

  .tableTitle {
    font-weight: 600;
    font-size: 13px;
    color: #606266;
    width: 100%;
    height: 35px;
    background: rgba(204, 204, 204, 0.18);
    border: 1px solid #dfe6ec;
    border-bottom: none;
    text-align: center;
    line-height: 35px;
  }

  .table {
    height: calc(100% - 90px);
    overflow: auto;

    ::v-deep .el-table {
      color: #909399;
      border-right: 1px solid #dfe6ec;

      table {
        .cell {
          text-align: center;
          padding: 0 10px;
        }

        th, td {
          padding: 5px 0;
        }
      }

      & > .el-table__header-wrapper {
        & > table {
          tr {
            th {
              background: rgba(204, 204, 204, 0.18);
              color: #909399;
              font-size: 12px;
              text-align: center;
              height: 34px;
              padding: 5px 0 !important;
            }
          }
        }
      }

      .el-table__body-wrapper {
        border-bottom: none;

        .el-table__row {
          td {
            width: auto;
            padding: 5px 0 !important;
          }
        }
      }

      //合计行滚动条在表格最底部显示
      .el-table--scrollable-x .el-table__body-wrapper {
        overflow-x: hidden !important;
        z-index: 2 !important;
      }

      .el-table__footer-wrapper {
        overflow-x: auto;
        border-top: 1px solid #f4f4f4;
      }
    }
  }
}
</style>
