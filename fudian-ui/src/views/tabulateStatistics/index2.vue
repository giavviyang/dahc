<template>
  <div class="app-container">
    <MainFlexibleTree :data="treeData" :defaultProps="defaultProps" @handleNodeClick="handleNodeClick">
      <div slot="mainRight" style="height: 100%">
        <div class="btn">
          <el-button
            type="primary"
            size="mini">
            打印
          </el-button>
          <el-button
            type="primary"
            size="mini">
            导出
          </el-button>
          <el-button
            type="primary"
            size="mini">
            返回
          </el-button>
        </div>
        <SummaryTable :tableTitle="tableTitle"
                       :tableData="tableData1"
                       id="tableData1"
                       ref="SummaryTable"
                       v-show="treeVal.label==='馆藏档案总量统计表（全宗）'"  >
          <template slot="table">
            <el-table-column
              prop="recordNum"
              label="全宗号"
              :min-width="getWidth(16)"
            >
            </el-table-column>
            <el-table-column
              prop="recordName"
              label="全宗名称"
              :min-width="getWidth(16)">
            </el-table-column>
            <el-table-column label="以卷整理">
              <el-table-column label="案卷数量">
                <el-table-column
                  prop="caseYJByCase"
                  label="永久"
                  :min-width="getWidth(16)">
                </el-table-column>
                <el-table-column
                  prop="caseLongByCase"
                  label="长期"
                  :min-width="getWidth(16)">
                </el-table-column>
                <el-table-column
                  prop="caseShortByCase"
                  label="短期"
                  :min-width="getWidth(16)">
                </el-table-column>
                <el-table-column
                  prop="caseSumByCase"
                  label="合计"
                  :min-width="getWidth(16)">
                </el-table-column>
              </el-table-column>
              <el-table-column label="文件数量">
                <el-table-column
                  prop="fileYJByCase"
                  label="永久"
                  :min-width="getWidth(16)">
                </el-table-column>
                <el-table-column
                  prop="fileLongByCase"
                  label="长期"
                  :min-width="getWidth(16)">
                </el-table-column>
                <el-table-column
                  prop="fileShortByCase"
                  label="短期"
                  :min-width="getWidth(16)">
                </el-table-column>
                <el-table-column
                  prop="fileSumByCase"
                  label="合计"
                  :min-width="getWidth(16)">
                </el-table-column>
              </el-table-column>
            </el-table-column>
            <el-table-column label="以件整理">
              <el-table-column label="文件数量">
                <el-table-column
                  prop="fileYJByFile"
                  label="永久"
                  :min-width="getWidth(16)">
                </el-table-column>
                <el-table-column
                  prop="fileLongByFile"
                  label="长期"
                  :min-width="getWidth(16)">
                </el-table-column>
                <el-table-column
                  prop="file30ByFile"
                  label="30年"
                  :min-width="getWidth(16)">
                </el-table-column>
                <el-table-column
                  prop="file10ByFile"
                  label="10年"
                  :min-width="getWidth(16)">
                </el-table-column>
                <el-table-column
                  prop="fileShortByFile"
                  label="短期"
                  :min-width="getWidth(16)">
                </el-table-column>
                <el-table-column
                  prop="fileSumByFile"
                  label="合计"
                  :min-width="getWidth(16)">
                </el-table-column>
              </el-table-column>
            </el-table-column>
          </template>
        </SummaryTable>
        <SummaryTable :tableTitle="tableTitle"
                       :tableData="tableData2"
                       id="tableData2"
                       ref="SummaryTable" :commonTable="false" :spanTable="true"
                       v-show="treeVal.label==='档案类型保管期限总表'">
          <template slot="table">
            <el-table-column
              prop="fileType"
              label="档案类型"
              :min-width="getWidth(5)">
            </el-table-column>
            <el-table-column
              prop="typeNameChild"
              label="档案子类型"
              :min-width="getWidth(5)"
            >
            </el-table-column>
            <el-table-column
              prop="YJ"
              label="永久"
              :min-width="getWidth(5)"
            >
            </el-table-column>
            <el-table-column
              prop="Long"
              label="长期"
              :min-width="getWidth(5)"
            >
            </el-table-column>
            <el-table-column
              prop="total"
              label="总计"
              :min-width="getWidth(5)"
            >
            </el-table-column>
          </template>
        </SummaryTable>
        <SummaryTable :tableTitle="tableTitle"
                       :tableData="tableData3"
                       ref="SummaryTable"
                       v-if="treeVal.label==='馆藏档案总量统计表（年度）'"  >
          <template slot="table">
            <el-table-column
              prop="year"
              label="年度"
              :min-width="getWidth(15)"
            >
            </el-table-column>
            <el-table-column label="以卷整理">
              <el-table-column label="案卷数量">
                <el-table-column
                  prop="caseYJByCase"
                  label="永久"
                  :min-width="getWidth(15)">
                </el-table-column>
                <el-table-column
                  prop="caseLongByCase"
                  label="长期"
                  :min-width="getWidth(15)">
                </el-table-column>
                <el-table-column
                  prop="caseShortByCase"
                  label="短期"
                  :min-width="getWidth(15)">
                </el-table-column>
                <el-table-column
                  prop="caseSumByCase"
                  label="合计"
                  :min-width="getWidth(15)">
                </el-table-column>
              </el-table-column>
              <el-table-column label="文件数量">
                <el-table-column
                  prop="fileYJByCase"
                  label="永久"
                  :min-width="getWidth(15)">
                </el-table-column>
                <el-table-column
                  prop="fileLongByCase"
                  label="长期"
                  :min-width="getWidth(15)">
                </el-table-column>
                <el-table-column
                  prop="fileShortByCase"
                  label="短期"
                  :min-width="getWidth(15)">
                </el-table-column>
                <el-table-column
                  prop="fileSumByCase"
                  label="合计"
                  :min-width="getWidth(15)">
                </el-table-column>
              </el-table-column>
            </el-table-column>
            <el-table-column label="以件整理">
              <el-table-column label="文件数量">
                <el-table-column
                  prop="fileYJByFile"
                  label="永久"
                  :min-width="getWidth(15)">
                </el-table-column>
                <el-table-column
                  prop="fileLongByFile"
                  label="长期"
                  :min-width="getWidth(15)">
                </el-table-column>
                <el-table-column
                  prop="file30ByFile"
                  label="30年"
                  :min-width="getWidth(15)">
                </el-table-column>
                <el-table-column
                  prop="file10ByFile"
                  label="10年"
                  :min-width="getWidth(15)">
                </el-table-column>
                <el-table-column
                  prop="fileShortByFile"
                  label="短期"
                  :min-width="getWidth(15)">
                </el-table-column>
                <el-table-column
                  prop="fileSumByFile"
                  label="合计"
                  :min-width="getWidth(15)">
                </el-table-column>
              </el-table-column>
            </el-table-column>
          </template>
        </SummaryTable>
        <SummaryTable :tableTitle="tableTitle"
                       :tableData="tableData4"
                       id="tableData4"
                       ref="SummaryTable" :commonTable="false" :spanTable="true"
                       v-show="treeVal.label==='档案类型数量总表'">
          <template slot="table">
            <el-table-column
              prop="fileType"
              label="档案类型"
              :min-width="getWidth(5)">
            </el-table-column>
            <el-table-column
              prop="typeNameChild"
              label="档案子类型"
              :min-width="getWidth(5)"
            >
            </el-table-column>
            <el-table-column
              label="案卷"
            >
              <el-table-column
                prop="caseTitleNum"
                label="案卷级目录数"
                :min-width="getWidth(5)"
              >
              </el-table-column>
              <el-table-column
                prop="caseChildNum"
                label="卷内件目录数（条）"
                :min-width="getWidth(5)"
              >
              </el-table-column>
            </el-table-column>
            <el-table-column
              label="以件归档"
            >
              <el-table-column
                prop="fileChildNum"
                label="文件目录数（条）"
                :min-width="getWidth(5)"
              >
              </el-table-column>
            </el-table-column>
          </template>
        </SummaryTable>
        <SummaryTable :tableTitle="tableTitle"
                       :tableData="tableData5"
                       id="tableData5"
                       ref="SummaryTable" :commonTable="false" :spanTable="true"
                       v-show="treeVal.label==='馆藏档案类型数量统计（文本）'">
          <template slot="table">
            <el-table-column
              type="index"
              label="序号"
              width="55">
            </el-table-column>
            <el-table-column
              prop="fileType"
              label="档案类型"
              :min-width="getWidth(6)">
            </el-table-column>
            <el-table-column
              prop="recordNum"
              label="全宗数"
              :min-width="getWidth(6)">
            </el-table-column>
            <el-table-column
              label="以卷整理"
            >
              <el-table-column
                prop="caseNum"
                label="案卷数"
                :min-width="getWidth(6)"
              >
              </el-table-column>
              <el-table-column
                prop="caseChildNum"
                label="卷内文件数"
                :min-width="getWidth(6)"
              >
              </el-table-column>
            </el-table-column>
            <el-table-column
              label="以件整理"
            >
              <el-table-column
                prop="fileChildNum"
                label="文件数"
                :min-width="getWidth(6)"
              >
              </el-table-column>
            </el-table-column>
          </template>
        </SummaryTable>
        <SummaryTable :tableTitle="tableTitle"
                       :tableData="tableData6"
                       id="tableData6"
                       ref="SummaryTable" :commonTable="false" :spanTable="true"
                       v-show="treeVal.label==='管理库统计'">
          <template slot="table">
            <el-table-column
              prop="fileType"
              label="档案类型"
              :min-width="getWidth(7)">
            </el-table-column>
            <el-table-column
              prop="typeNameChild"
              label="档案子类型"
              :min-width="getWidth(7)"
            >
            </el-table-column>
            <el-table-column
              label="案卷"
            >
              <el-table-column
                prop="caseTitleNum"
                label="案卷级目录数"
                :min-width="getWidth(7)"
              >
              </el-table-column>
              <el-table-column
                prop="caseChildNum"
                label="卷内件目录数（条）"
                :min-width="getWidth(7)"
              >
              </el-table-column>
              <el-table-column
                prop="caseFullText"
                label="电子全文数"
                :min-width="getWidth(7)"
              >
              </el-table-column>
            </el-table-column>
            <el-table-column
              label="以件归档"
            >
              <el-table-column
                prop="fileChildNum"
                label="文件目录数（条）"
                :min-width="getWidth(7)"
              >
              </el-table-column>
              <el-table-column
                prop="fileFullText"
                label="电子全文数"
                :min-width="getWidth(7)"
              >
              </el-table-column>
            </el-table-column>
          </template>
        </SummaryTable>
        <SummaryTable :tableTitle="tableTitle"
                       :tableData="tableData7"
                       id="tableData7"
                       ref="SummaryTable" :commonTable="false" :spanTable="true"
                       v-show="treeVal.label==='利用目的与人次统计'">
          <template slot="table">
            <el-table-column
              prop="years"
              label="年度"
              :min-width="getWidth(6)">
            </el-table-column>
            <el-table-column
              prop="mouths"
              label="月份"
              :min-width="getWidth(6)">
            </el-table-column>
            <el-table-column
              prop="usePurpose"
              label="利用目的"
              :min-width="getWidth(6)"
            >
            </el-table-column>
            <el-table-column
              prop="manTime"
              label="人次"
              :min-width="getWidth(6)"
            >
            </el-table-column>
            <el-table-column
              prop="caseNum"
              label="卷数"
              :min-width="getWidth(6)"
            >
            </el-table-column>
            <el-table-column
              prop="fileNum"
              label="件数"
              :min-width="getWidth(6)"
            >
            </el-table-column>
          </template>
        </SummaryTable>
        <SummaryTable :tableTitle="tableTitle"
                       :tableData="tableData8"
                       id="tableData8"
                       ref="SummaryTable" :commonTable="false" :spanTable="true"
                       v-show="treeVal.label==='利用效果统计'">
          <template slot="table">
            <el-table-column
              prop="years"
              label="年度"
              :min-width="getWidth(5)">
            </el-table-column>
            <el-table-column
              prop="mouths"
              label="月份"
              :min-width="getWidth(5)">
            </el-table-column>
            <el-table-column
              prop="manTime"
              label="人次"
              :min-width="getWidth(5)"
            >
            </el-table-column>
            <el-table-column
              prop="haveFound"
              label="已查到"
              :min-width="getWidth(5)"
            >
            </el-table-column>
            <el-table-column
              prop="noFound"
              label="未查到"
              :min-width="getWidth(5)"
            >
            </el-table-column>
          </template>
        </SummaryTable>
        <SummaryTable :tableTitle="tableTitle"
                       :tableData="tableData9"
                       ref="SummaryTable"
                       v-show="treeVal.label==='利用件次统计'"  >
          <template slot="table">
            <el-table-column
              prop="fileType"
              label="档案类型"
              :min-width="getWidth(2)">
            </el-table-column>
            <el-table-column
              prop="fileTotal"
              label="统计件次"
              :min-width="getWidth(2)">
            </el-table-column>
          </template>
        </SummaryTable>
        <SummaryTable :tableTitle="tableTitle"
                       :tableData="tableData10"
                       ref="SummaryTable"
                       v-show="treeVal.label==='利用年度统计'"  >
          <template slot="table">
            <el-table-column
              prop="years"
              label="年度"
              :min-width="getWidth(4)">
            </el-table-column>
            <el-table-column
              prop="manTime"
              label="人次"
              :min-width="getWidth(4)">
            </el-table-column>
            <el-table-column
              prop="caseNum"
              label="卷数"
              :min-width="getWidth(4)">
            </el-table-column>
            <el-table-column
              prop="fileNum"
              label="件数"
              :min-width="getWidth(4)">
            </el-table-column>
          </template>
        </SummaryTable>
      </div>
    </MainFlexibleTree>
  </div>
</template>

<script>
import MainFlexibleTree from "@/components/Public/MainBody/MainFlexibleTree.vue";
import SummaryTable from "@/components/Public/table/summaryTable.vue";

export default {
  name: "index2",
  components: {SummaryTable, MainFlexibleTree},
  data() {
    return {
      treeData: [
        {
          label: '管理统计',
          children: [{
            label: '馆藏档案总量统计表（全宗）',
          }, {
            label: '档案类型保管期限总表',
          }, {
            label: '馆藏档案总量统计表（年度）',
          }, {
            label: '档案类型数量总表',
          }, {
            label: '馆藏档案类型数量统计（文本）',
          }, {
            label: '管理库统计',
          },]
        },
        {
          label: '利用统计',
          children: [{
            label: '利用目的与人次统计',
          }, {
            label: '利用效果统计',
          }, {
            label: '利用件次统计',
          }, {
            label: '利用年度统计',
          }]
        },
      ],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      tableWidth: 0,
      treeVal: {
        label: '馆藏档案总量统计表（全宗）',
      },
      tableTitle: '馆藏档案总量统计表（全宗）',
      tableData1: [
        {
          recordNum: '001',
          recordName: '中共洛阳市委',
          caseYJByCase: '2024',
          caseLongByCase: '2044',
          caseShortByCase: '',
          caseSumByCase: '4068',
          fileYJByCase: '26151',
          fileLongByCase: '42503',
          fileShortByCase: '',
          fileSumByCase: '68654',
          fileYJByFile: '42755',
          fileLongByFile: '',
          file30ByFile: '8979',
          file10ByFile: '',
          fileShortByFile: '',
          fileSumByFile: '51734',
        }, {
          recordNum: '002',
          recordName: '洛阳市妇女联合会',
          caseYJByCase: '291',
          caseLongByCase: '94',
          caseShortByCase: '',
          caseSumByCase: '385',
          fileYJByCase: '3450',
          fileLongByCase: '1446',
          fileShortByCase: '',
          fileSumByCase: '4896',
          fileYJByFile: '1029',
          fileLongByFile: '',
          file30ByFile: '164',
          file10ByFile: '',
          fileShortByFile: '',
          fileSumByFile: '1193',
        }, {
          recordNum: '003',
          recordName: '洛阳市人民政府',
          caseYJByCase: '3246',
          caseLongByCase: '696',
          caseShortByCase: '',
          caseSumByCase: '3942',
          fileYJByCase: '36623',
          fileLongByCase: '9291',
          fileShortByCase: '',
          fileSumByCase: '45914',
          fileYJByFile: '23518',
          fileLongByFile: '',
          file30ByFile: '6533',
          file10ByFile: '',
          fileShortByFile: '',
          fileSumByFile: '30051',
        }, {
          recordNum: '004',
          recordName: '洛阳市计划委员会',
          caseYJByCase: '1196',
          caseLongByCase: '271',
          caseShortByCase: '',
          caseSumByCase: '1467',
          fileYJByCase: '12223',
          fileLongByCase: '3573',
          fileShortByCase: '',
          fileSumByCase: '15796',
          fileYJByFile: '4058',
          fileLongByFile: '',
          file30ByFile: '2971',
          file10ByFile: '',
          fileShortByFile: '',
          fileSumByFile: '7029',
        }, {
          recordNum: '005',
          recordName: '洛阳市财贸委员会',
          caseYJByCase: '54',
          caseLongByCase: '79',
          caseShortByCase: '',
          caseSumByCase: '133',
          fileYJByCase: '619',
          fileLongByCase: '1413',
          fileShortByCase: '',
          fileSumByCase: '2032',
          fileYJByFile: '42755',
          fileLongByFile: '',
          file30ByFile: '',
          file10ByFile: '',
          fileShortByFile: '',
          fileSumByFile: '',
        }, {
          recordNum: '006',
          recordName: '洛阳市监察局',
          caseYJByCase: '143',
          caseLongByCase: '26',
          caseShortByCase: '',
          caseSumByCase: '169',
          fileYJByCase: '1311',
          fileLongByCase: '334',
          fileShortByCase: '',
          fileSumByCase: '1645',
          fileYJByFile: '',
          fileLongByFile: '',
          file30ByFile: '',
          file10ByFile: '',
          fileShortByFile: '',
          fileSumByFile: '',
        }, {
          recordNum: '007',
          recordName: '洛阳市财政局',
          caseYJByCase: '508',
          caseLongByCase: '1082',
          caseShortByCase: '',
          caseSumByCase: '1590',
          fileYJByCase: '4311',
          fileLongByCase: '14378',
          fileShortByCase: '',
          fileSumByCase: '18689',
          fileYJByFile: '28805',
          fileLongByFile: '',
          file30ByFile: '1375',
          file10ByFile: '',
          fileShortByFile: '',
          fileSumByFile: '30180',
        }, {
          recordNum: '008',
          recordName: '洛阳市税务局',
          caseYJByCase: '251',
          caseLongByCase: '436',
          caseShortByCase: '',
          caseSumByCase: '687',
          fileYJByCase: '4458',
          fileLongByCase: '5435',
          fileShortByCase: '',
          fileSumByCase: '9893',
          fileYJByFile: '',
          fileLongByFile: '',
          file30ByFile: '',
          file10ByFile: '',
          fileShortByFile: '',
          fileSumByFile: '',
        }],
      tableData2: [
        {
          fileType: '文书档案',
          typeNameChild: '文书档案（卷）',
          YJ: 588542,
          Long: 505411,
          total: 1093953,
        }, {
          fileType: '文书档案',
          typeNameChild: '文书档案（件）',
          YJ: 224953,
          Long: 0,
          total: 224953,
        }, {
          fileType: '声像档案',
          typeNameChild: '照片档案（件）',
          YJ: 1619,
          Long: 10000,
          total: 11619,
        }, {
          fileType: '声像档案',
          typeNameChild: '视频档案（件）',
          YJ: 59,
          Long: 299,
          total: 358,
        }, {
          fileType: '声像档案',
          typeNameChild: '音频档案（件）',
          YJ: 1,
          Long: 19,
          total: 20,
        }
      ],
      tableData3: [
        {
          year: '1924',
          caseYJByCase: '2',
          caseLongByCase: '',
          caseShortByCase: '',
          caseSumByCase: '2',
          fileYJByCase: '',
          fileLongByCase: '',
          fileShortByCase: '',
          fileSumByCase: '',
          fileYJByFile: '',
          fileLongByFile: '',
          file30ByFile: '',
          file10ByFile: '',
          fileShortByFile: '',
          fileSumByFile: '',
        }, {
          year: '1926',
          caseYJByCase: '1',
          caseLongByCase: '',
          caseShortByCase: '',
          caseSumByCase: '1',
          fileYJByCase: '',
          fileLongByCase: '',
          fileShortByCase: '',
          fileSumByCase: '',
          fileYJByFile: '',
          fileLongByFile: '',
          file30ByFile: '',
          file10ByFile: '',
          fileShortByFile: '',
          fileSumByFile: '',
        }, {
          year: '1931',
          caseYJByCase: '1',
          caseLongByCase: '',
          caseShortByCase: '',
          caseSumByCase: '1',
          fileYJByCase: '',
          fileLongByCase: '',
          fileShortByCase: '',
          fileSumByCase: '',
          fileYJByFile: '',
          fileLongByFile: '',
          file30ByFile: '',
          file10ByFile: '',
          fileShortByFile: '',
          fileSumByFile: '',
        }, {
          year: '1932',
          caseYJByCase: '2',
          caseLongByCase: '',
          caseShortByCase: '',
          caseSumByCase: '2',
          fileYJByCase: '',
          fileLongByCase: '',
          fileShortByCase: '',
          fileSumByCase: '',
          fileYJByFile: '',
          fileLongByFile: '',
          file30ByFile: '',
          file10ByFile: '',
          fileShortByFile: '',
          fileSumByFile: '',
        }, {
          year: '1934',
          caseYJByCase: '4',
          caseLongByCase: '',
          caseShortByCase: '',
          caseSumByCase: '4',
          fileYJByCase: '',
          fileLongByCase: '',
          fileShortByCase: '',
          fileSumByCase: '',
          fileYJByFile: '',
          fileLongByFile: '',
          file30ByFile: '',
          file10ByFile: '',
          fileShortByFile: '',
          fileSumByFile: '',
        }, {
          year: '1935',
          caseYJByCase: '7',
          caseLongByCase: '',
          caseShortByCase: '',
          caseSumByCase: '7',
          fileYJByCase: '',
          fileLongByCase: '',
          fileShortByCase: '',
          fileSumByCase: '',
          fileYJByFile: '',
          fileLongByFile: '',
          file30ByFile: '',
          file10ByFile: '',
          fileShortByFile: '',
          fileSumByFile: '',
        }, {
          year: '1936',
          caseYJByCase: '7',
          caseLongByCase: '',
          caseShortByCase: '',
          caseSumByCase: '7',
          fileYJByCase: '',
          fileLongByCase: '',
          fileShortByCase: '',
          fileSumByCase: '',
          fileYJByFile: '',
          fileLongByFile: '',
          file30ByFile: '',
          file10ByFile: '',
          fileShortByFile: '',
          fileSumByFile: '',
        }, {
          year: '1937',
          caseYJByCase: '2',
          caseLongByCase: '',
          caseShortByCase: '',
          caseSumByCase: '2',
          fileYJByCase: '',
          fileLongByCase: '',
          fileShortByCase: '',
          fileSumByCase: '',
          fileYJByFile: '',
          fileLongByFile: '',
          file30ByFile: '',
          file10ByFile: '',
          fileShortByFile: '',
          fileSumByFile: '',
        }, {
          year: '1938',
          caseYJByCase: '29',
          caseLongByCase: '',
          caseShortByCase: '',
          caseSumByCase: '29',
          fileYJByCase: '',
          fileLongByCase: '',
          fileShortByCase: '',
          fileSumByCase: '',
          fileYJByFile: '',
          fileLongByFile: '',
          file30ByFile: '',
          file10ByFile: '',
          fileShortByFile: '',
          fileSumByFile: '',
        }, {
          year: '1939',
          caseYJByCase: '1',
          caseLongByCase: '',
          caseShortByCase: '',
          caseSumByCase: '1',
          fileYJByCase: '',
          fileLongByCase: '',
          fileShortByCase: '',
          fileSumByCase: '',
          fileYJByFile: '',
          fileLongByFile: '',
          file30ByFile: '',
          file10ByFile: '',
          fileShortByFile: '',
          fileSumByFile: '',
        }, {
          year: '1940',
          caseYJByCase: '4',
          caseLongByCase: '',
          caseShortByCase: '',
          caseSumByCase: '4',
          fileYJByCase: '',
          fileLongByCase: '',
          fileShortByCase: '',
          fileSumByCase: '',
          fileYJByFile: '',
          fileLongByFile: '',
          file30ByFile: '',
          file10ByFile: '',
          fileShortByFile: '',
          fileSumByFile: '',
        }, {
          year: '1941',
          caseYJByCase: '59',
          caseLongByCase: '',
          caseShortByCase: '',
          caseSumByCase: '59',
          fileYJByCase: '',
          fileLongByCase: '',
          fileShortByCase: '',
          fileSumByCase: '',
          fileYJByFile: '',
          fileLongByFile: '',
          file30ByFile: '',
          file10ByFile: '',
          fileShortByFile: '',
          fileSumByFile: '',
        }, {
          year: '1942',
          caseYJByCase: '6',
          caseLongByCase: '',
          caseShortByCase: '',
          caseSumByCase: '6',
          fileYJByCase: '',
          fileLongByCase: '',
          fileShortByCase: '',
          fileSumByCase: '',
          fileYJByFile: '',
          fileLongByFile: '',
          file30ByFile: '',
          file10ByFile: '',
          fileShortByFile: '',
          fileSumByFile: '',
        }],
      tableData4: [
        {
          fileType: '文书档案',
          typeNameChild: '文书档案（卷）',
          caseTitleNum: 93135,
          caseChildNum: 1094061,
          fileChildNum: 0,
        }, {
          fileType: '文书档案',
          typeNameChild: '文书档案（件）',
          caseTitleNum: 0,
          caseChildNum: 0,
          fileChildNum: 284090,
        }, {
          fileType: '声像档案',
          typeNameChild: '照片档案（件）',
          caseTitleNum: 0,
          caseChildNum: 0,
          fileChildNum: 11619,
        }, {
          fileType: '声像档案',
          typeNameChild: '视频档案（件）',
          caseTitleNum: 0,
          caseChildNum: 0,
          fileChildNum: 358,
        }, {
          fileType: '声像档案',
          typeNameChild: '音频档案（件）',
          caseTitleNum: 0,
          caseChildNum: 0,
          fileChildNum: 20,
        }],
      tableData5: [
        {
          fileType: '文书档案（卷）',
          recordNum: 365,
          caseNum: 93135,
          caseChildNum: 1094061,
          fileChildNum: '',
        }, {
          fileType: '文书档案（件）',
          recordNum: 54,
          caseNum: '',
          caseChildNum: '',
          fileChildNum: 254090,
        }],
      tableData6: [
        {
          fileType: '文书档案',
          typeNameChild: '文书档案（卷）',
          caseTitleNum: '93135',
          caseChildNum: '1093953',
          caseFullText: '1093828',
          fileChildNum: '0',
          fileFullText: '0',
        }, {
          fileType: '文书档案',
          typeNameChild: '文书档案（件）',
          caseTitleNum: '0',
          caseChildNum: '0',
          caseFullText: '0',
          fileChildNum: '284050',
          fileFullText: '384080',
        }, {
          fileType: '声像档案',
          typeNameChild: '照片档案（件）',
          caseTitleNum: '0',
          caseChildNum: '0',
          caseFullText: '0',
          fileChildNum: '11619',
          fileFullText: '11619',
        }, {
          fileType: '声像档案',
          typeNameChild: '视频档案（件）',
          caseTitleNum: '0',
          caseChildNum: '0',
          caseFullText: '0',
          fileChildNum: '358',
          fileFullText: '358',
        }, {
          fileType: '声像档案',
          typeNameChild: '音频档案（件）',
          caseTitleNum: '0',
          caseChildNum: '0',
          caseFullText: '0',
          fileChildNum: '20',
          fileFullText: '20',
        }
      ],
      tableData7: [
        {
          years: '2022',
          mouths: '08',
          usePurpose: '职工证明',
          manTime: '1',
          caseNum: '0',
          fileNum: '6'
        }, {
          years: '2022',
          mouths: '08',
          usePurpose: '政策核对',
          manTime: '2',
          caseNum: '0',
          fileNum: '2'
        }, {
          years: '2022',
          mouths: '09',
          usePurpose: '职工证明',
          manTime: '1',
          caseNum: '0',
          fileNum: '1'
        }, {
          years: '2022',
          mouths: '09',
          usePurpose: '政策核对',
          manTime: '1',
          caseNum: '0',
          fileNum: '39'
        }, {
          years: '2023',
          mouths: '09',
          usePurpose: '工作查考',
          manTime: '1',
          caseNum: '0',
          fileNum: '113'
        }, {
          years: '2022',
          mouths: '11',
          usePurpose: '职工证明',
          manTime: '2',
          caseNum: '0',
          fileNum: '3'
        }, {
          years: '2022',
          mouths: '11',
          usePurpose: '宣传教育',
          manTime: '1',
          caseNum: '0',
          fileNum: '1'
        }
      ],
      tableData8: [
        {
          years: '2022',
          mouths: '8',
          manTime: '6',
          haveFound: '5',
          noFound: '1'
        }, {
          years: '2022',
          mouths: '9',
          manTime: '9',
          haveFound: '8',
          noFound: '1'
        }, {
          years: '2022',
          mouths: '11',
          manTime: '3',
          haveFound: '3',
          noFound: '0'
        }, {
          years: '2022',
          mouths: '12',
          manTime: '1',
          haveFound: '1',
          noFound: '0'
        }, {
          years: '2023',
          mouths: '1',
          manTime: '3',
          haveFound: '3',
          noFound: '0'
        }, {
          years: '2023',
          mouths: '2',
          manTime: '4',
          haveFound: '4',
          noFound: '0'
        }
      ],
      tableData9: [
        {
          fileType: '文件档案（件）',
          fileTotal: '10'
        }, {
          fileType: '文书档案（卷）',
          fileTotal: '341'
        }],
      tableData10: [
        {
          years: '2022',
          manTime: '19',
          caseNum: '0',
          fileNum: '211'
        }, {
          years: '2023',
          manTime: '7',
          caseNum: '0',
          fileNum: '4'
        },],

    }
  },
  mounted() {
    // console.log('111',this.$refs.SummaryTable.$el.clientWidth)
    this.$nextTick(() => {
      this.tableWidth = this.$refs.SummaryTable.$el.clientWidth;
    })
    window.onresize = () => {
      return (() => {
        this.tableWidth = this.$refs.SummaryTable.$el.clientWidth;
        console.log(this.tableWidth)
      })();
    };
    this.treeVal.label = '馆藏档案总量统计表（全宗）'
  },
  // 监控data中的数据变化
  watch: {
    tableData1: {
      immediate: true,
      async handler() {
        //await this.$nextTick(); 根据实际选择延迟调用
        await this.$nextTick();
        const tableFooter = document.querySelectorAll('#tableData1 .el-table__footer-wrapper tr>td');
        // console.log('tableFooter',tableFooter);
        tableFooter[0].colSpan = 2;
        tableFooter[0].style.textAlign = 'center'
        tableFooter[1].style.display = 'none'
        // tableFooter[2].style.display='none'
        // tableFooter[3].colSpan=3;
        // tableFooter[3].style.textAlign='center'
        // tableFooter[4].style.display='none'
        // tableFooter[5].style.display='none'
      }
    },
    tableData2: {
      immediate: true,
      async handler() {
        await this.$nextTick();
        const tableHead = document.querySelectorAll('#tableData2 .el-table__header-wrapper tr>th');
        const tableFooter = document.querySelectorAll('#tableData2 .el-table__footer-wrapper tr>td');
        tableHead[0].colSpan = 2;
        tableHead[0].style.textAlign = 'center'
        tableHead[1].style.display = 'none'
        tableFooter[0].colSpan = 2;
        tableFooter[0].style.textAlign = 'center'
        tableFooter[1].style.display = 'none'
      }
    },
    tableData4: {
      immediate: true,
      async handler() {
        await this.$nextTick();
        const tableHead = document.querySelectorAll('#tableData4 .el-table__header-wrapper tr>th');
        const tableFooter = document.querySelectorAll('#tableData4 .el-table__footer-wrapper tr>td');
        tableHead[0].colSpan = 2;
        tableHead[0].style.textAlign = 'center'
        tableHead[1].style.display = 'none'
        tableFooter[0].colSpan = 2;
        tableFooter[0].style.textAlign = 'center'
        tableFooter[1].style.display = 'none'
      }
    },
    tableData5: {
      immediate: true,
      async handler() {
        await this.$nextTick();
        const tableFooter = document.querySelectorAll('#tableData5 .el-table__footer-wrapper tr>td');
        tableFooter[0].colSpan = 2;
        tableFooter[0].style.textAlign = 'center'
        tableFooter[1].style.display = 'none'
      }
    },
    tableData6: {
      immediate: true,
      async handler() {
        await this.$nextTick();
        const tableHead = document.querySelectorAll('#tableData6 .el-table__header-wrapper tr>th');
        const tableFooter = document.querySelectorAll('#tableData6 .el-table__footer-wrapper tr>td');
        tableHead[0].colSpan = 2;
        tableHead[0].style.textAlign = 'center'
        tableHead[1].style.display = 'none'
        tableFooter[0].colSpan = 2;
        tableFooter[0].style.textAlign = 'center'
        tableFooter[1].style.display = 'none'
      }
    },
    tableData7: {
      immediate: true,
      async handler() {
        await this.$nextTick();
        const tableFooter = document.querySelectorAll('#tableData7 .el-table__footer-wrapper tr>td');
        tableFooter[0].colSpan = 3;
        tableFooter[0].style.textAlign = 'center'
        tableFooter[1].style.display = 'none'
        tableFooter[2].style.display = 'none'
      }
    },
    tableData8: {
      immediate: true,
      async handler() {
        await this.$nextTick();
        const tableFooter = document.querySelectorAll('#tableData8 .el-table__footer-wrapper tr>td');
        tableFooter[0].colSpan = 2;
        tableFooter[0].style.textAlign = 'center'
        tableFooter[1].style.display = 'none'
      }
    },
  },
  methods: {
    handleNodeClick(val, node, component) {
      console.log(val, node, component);
      // this.treeVal = val;

      if (val.children) {
        this.treeVal = val.children[0];
        this.tableTitle = val.children[0].label;
      } else {
        this.treeVal = val;
        this.tableTitle = val.label;
      }
      console.log(this.tableTitle)
      // if(this.tableTitle=='档案类型保管期限总表'){
      //   console.log('111',this.$refs.SummaryTable.initSpanArr(this.tableData2,'fileType'));
      // }
    },
    getWidth(len) {
      return this.tableWidth / len - 1
    },


  }
}
</script>

<style lang="scss" scoped>
::v-deep .mainRight {
  padding: 0 10px 5px 10px !important;
}

</style>
