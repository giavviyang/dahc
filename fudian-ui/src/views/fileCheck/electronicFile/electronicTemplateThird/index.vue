<template>
  <div class="app-container">
    <el-tabs :tab-position="tabPosition" v-model="queryParams.activeName" @tab-click="handleTabClick">
      <el-tab-pane label="已领取" name="alreadyReceived">
        <CheckTop :queryParams="queryParams"
                  :checkRange="checkRange"
                  :processName="processName"
                  :processDes="processDes"
                  @handleBack="handleBack"/>
        <TablePage class="tablePage1"
                   :hasSelection="false"
                   :is-title="true"
                   :tableTitle="caseTitle"
                   :tableData="caseData"
                   :isPage="false"
                   @handleChange="handleCheckItem">
          <template slot="table">
            <el-table-column
              show-overflow-tooltip
              v-for="(item,index) in caseColumn"
              :key="index"
              :prop="item.prop"
              :label="item.label"
              :min-width="item.width">
              <template slot-scope="scope">
                <el-input v-model="scope.row[item.prop]" size="mini"
                          v-if="scope.row.isUpdate &&(item.prop!=='caseNum')"></el-input>
                <div v-else>{{ scope.row[item.prop] }}</div>
              </template>
            </el-table-column>
            <el-table-column
              label="操作"  fixed="right"
              min-width="100" class-name="operation">
              <template slot-scope="scope">
                <el-button type="text" size="mini" @click="handleSave(scope.row)" v-if="scope.row.isUpdate">
                  保存
                </el-button>
                <el-button type="text" size="mini" @click="handleUpdate(scope.row)" v-else>
                  编辑
                </el-button>
                <el-button type="text" size="mini" >
                  新增文件
                </el-button>
              </template>
            </el-table-column>
          </template>
        </TablePage>
        <div class="checkBox">
          <div class="fileTable">
            <TablePage class="tablePage2"
                       :hasSelection="false"
                       :hasIndex="false"
                       :isPage="false"
                       :is-title="true"
                       :tableTitle="fileTitle"
                       :tableData="fileData">
              <template slot="table">
                <el-table-column label="顺序" align="center" width="60" prop="fileIndex"/>
                <el-table-column
                  label="操作"  fixed="right"
                  min-width="100" class-name="operation">
                  <template slot-scope="scope">
                    <el-button type="text" size="mini">
                      上移
                    </el-button>
                    <el-button type="text" size="mini">
                      下移
                    </el-button>
                    <el-button type="text" size="mini">
                      上传电子文件
                    </el-button>
                  </template>
                </el-table-column>
              </template>
            </TablePage>
          </div>
          <PicProcess class="filePic"></PicProcess>
          <div class="fileContent">
            <p class="fileContentTitle">
              文件具体信息：
            </p>
            <el-form ref="form" :model="fileData[1]" label-width="80px" size="mini">
              <div>
                <el-form-item  v-for="(item,index) in  fileColumn" :label="item.label" :key="index">
                  <el-input v-model="fileData[1][item.prop]"  type="textarea" :disabled="isDisabled"  :autosize="{ minRows: 1, maxRows: 10}"></el-input>
                </el-form-item>
              </div>
          <div>
            <el-form-item>
              <el-button type="primary" @click="handleUpdateForm" v-if="isDisabled"  icon="el-icon-edit">编辑</el-button>
              <el-button v-else type="primary" @click="handleSaveForm">保存</el-button>
            </el-form-item>
          </div>

            </el-form>
          </div>
        </div>
        <div class="subBtn">
          <el-button type="primary" icon="el-icon-refresh-left" size="mini" @click="handleBack">提交</el-button>
        </div>
      </el-tab-pane>
      <el-tab-pane label="未领取" name="noReceived">
        <CheckTop :queryParams="queryParams"
                  :checkRange="checkRange"
                  :processName="processName"
                  :processDes="processDes"
                  @handleBack="handleBack"/>
        <TablePage class="tablePage"
                   :hasSelection="false"
                   :is-title="true"
                   :tableTitle="caseTitle"
                   :tableData="caseData"
                   :pageSize="caseSize"
                   :pageNum="caseNum"
                   :total="caseTotal"
                   @handleChange="handleCheckItem">
          <template slot="table">
            <el-table-column
              show-overflow-tooltip
              v-for="(item,index) in caseColumn"
              :key="index"
              :prop="item.prop"
              :label="item.label"
              :min-width="item.width"/>
          </template>
        </TablePage>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import CheckTop from "@/views/fileCheck/checkTop.vue";
import TablePage from "@/components/Public/table/TablePage.vue";
import CheckTable from "@/views/fileCheck/checkTable.vue";
import PicProcess from "@/views/fileCheck/picProcess.vue";
import fa from "element-ui/src/locale/lang/fa";

export default {
  computed: {
    fa() {
      return fa
    }
  },
  // name: "index",
  components: {PicProcess, CheckTable, TablePage, CheckTop},
  data() {
    return {
      tabPosition: 'left',
      queryParams: {
        caseNum: '',
        activeName: 'alreadyReceived',
      },
      caseTitle: '案卷信息：',
      caseData: [{
        caseNum: '1111',
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄',
        isUpdate: false,
      }, {
        caseNum: '22222',
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1517 弄',
        isUpdate: false,
      }, {
        caseNum: '3333',
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1517 弄',
        isUpdate: false,
      }, {
        caseNum: '44444',
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1517 弄',
        isUpdate: false,
      }],
      caseColumn: [
        {label: '案卷档号', prop: 'caseNum', width: '100'},
        {label: '核查项名称', prop: 'date', width: '100'},
        {label: '核查项描述', prop: 'name', width: '100'},
        {label: '核查标准', prop: 'address', width: '100'},
        {label: '核查范围', prop: 'remarks', width: '100'},
      ],
      caseTotal: 0,
      caseSize: 10,
      caseNum: 1,
      fileTitle: '文件信息：',
      fileData: [{
        fileIndex: '001',
        caseNum: '1111',
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄22222222222',
        isUpdate: false,
      }, {
        fileIndex: '002',
        caseNum: '22222',
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1517 弄222222222222222',
        isUpdate: false,
      }, {
        fileIndex: '003',
        caseNum: '3333',
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1517 弄',
        isUpdate: false,
      }, {
        fileIndex: '004',
        caseNum: '44444',
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1517 弄',
        isUpdate: false,
      }],
      fileForm: {
        fileIndex: '1111',
        caseNum: '',
        date: '',
        name: '',
        address: false,
      },
      fileColumn: [
        {label: '文件号', prop: 'caseNum', width: '100'},
        {label: '核查项名称', prop: 'date', width: '100'},
        {label: '核查项描述', prop: 'name', width: '100'},
        {label: '核查标准', prop: 'address', width: '100'},
        {label: '核查范围', prop: 'remarks', width: '100'},
      ],
      checkRange: '核查范围',
      processName: '工序名称',
      processDes: '工序描述',
      checkItemData: [{
        trueingName: '2016-05-02',
        trueingDesc: '王小虎',
        codeProperty: '上海市普陀区金沙江路 1518 弄',
        checkResult: [],
        processMode: [],
        changeContent: [],
        remark: '',
        showPiece: 0,
        pageNumS: [{
          value: '',
          key: Date.now(),
        },],
        caseNumS: [{
          value: '',
          key: Date.now(),
        }],
      }, {
        trueingName: '2016-05-02',
        trueingDesc: '王小虎',
        codeProperty: '上海市普陀区金沙江路 1518 弄',
        checkResult: [],
        processMode: [],
        changeContent: [],
        remark: '',
        showPiece: 0,
        pageNumS: [{
          value: '',
          key: Date.now(),
        },],
      },],
      isDisabled:true,
    }
  },
  created() {
    this.handleQuery();
  },
  methods: {
    /**
     * 查询
     */
    handleQuery() {

    },
    /**
     * 单击tab页
     * @param tab
     * @param event
     */
    handleTabClick(tab, event) {
      console.log(tab, event);
    },
    /**
     * 领取档案
     */
    handleCheckItem(pageSize, pageNum) {
      this.processSize = pageSize;
      this.processNum = pageNum;

    },
    /**
     * 返回
     */
    handleBack() {
      this.$router.go(-1);
    },
    /**
     * 编辑档案
     */
    handleUpdate(row) {
      console.log(row)
      row.isUpdate = !row.isUpdate
    },
    /**
     * 保存档案
     */
    handleSave(row) {
      row.isUpdate = !row.isUpdate
    },
    handleUpdateForm(){
      this.isDisabled=false;
    },
    handleSaveForm(){
      this.isDisabled=true;
    }
  },
}
</script>

<style scoped lang="scss">
::v-deep .el-tabs__content {
  height: 100%;
  padding-left: 0;

  .el-tab-pane:first-of-type {
    .tablePage1 {
      height: 110px;
      margin-top: 20px;
    }

    .checkBox {
      height: calc(100% - 245px);
      //background-color: wheat;
      margin-top: 5px;
      display: flex;

      .fileTable {
        width: 170px;
        height: 100%;

        .tablePage2 {
          height: calc(100% - 20px);
          margin-top: 20px;
          .operation{
            .cell{
              //justify-content: space-between;//两端对齐，项目之间的间隔都相等。
              flex-wrap: wrap;//换行且第一行在上方
              .el-button{
                width: auto;
              }
            }
          }
        }
      }

      .filePic {
        width: 60%;
        height: 100%;
        margin-left: 5px;
      }
      .fileContent{
        width:calc(40% - 180px);
        //border: 1px solid #eeeeee;
        //box-shadow: rgba(0, 0, 0, 0.1) 0px 4px 12px;
        margin-left: 5px;
        height: 100%;
        //background-color: #e1f3fb;
        .fileContentTitle{
          font-weight: 600;
          font-size: 13px;
          color: #606266;

        }
        .el-form{
          //font-size: 12px;
          padding: 5px 5px 0 0;
          height: calc(100% - 20px);
          display: flex;
          position: relative;
          border-radius: 10px;
          border: 1px solid #eeeeee;
          width: 100%;
          box-shadow: rgba(0, 0, 0, 0.1) 0px 4px 12px;
          .el-form-item__label{
            font-size: 12px;
          }
          &>div:first-of-type{
            height: calc(100% - 50px);
            overflow: auto;
            width: 100%;
          }
          &>div:last-of-type{
            position: absolute;
            right: 5px;
            bottom: 0;
          }
        }

      }
    }

  }

  .el-tab-pane:last-of-type {
    .tablePage {
      height: calc(100% - 95px);
      margin-top: 20px;
    }
  }
}

</style>
