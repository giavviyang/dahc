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
        </div>
        <SummaryTable :tableTitle="tableTitle"
                      :tableData="tableData1"
                      id="tableData1"
                      ref="SummaryTable"
                      v-show="treeVal.label==='核查档案总量统计表（全宗）'">
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
            <el-table-column label="图像数量">
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
          </template>
        </SummaryTable>
        <SummaryTable :tableTitle="tableTitle"
                      :tableData="tableData2"
                      id="tableData2"
                      ref="SummaryTable" :commonTable="false" :spanTable="true"
                      v-show="treeVal.label==='核查档案类型保管期限总表'">
          <template slot="table">
            <el-table-column
              prop="fileType"
              label="档案类型"
              :min-width="getWidth(5)">
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
                      v-if="treeVal.label==='核查档案总量统计表（年度）'">
          <template slot="table">
            <el-table-column
              prop="year"
              label="年度"
              :min-width="getWidth(15)"
            >
            </el-table-column>
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
            <el-table-column label="图像数量">
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
          </template>
        </SummaryTable>
        <SummaryTable :tableTitle="tableTitle"
                      :tableData="tableData4"
                      id="tableData4"
                      ref="SummaryTable" :commonTable="false" :spanTable="true"
                      v-show="treeVal.label==='核查档案类型数量总表'">
          <template slot="table">
            <el-table-column
              prop="fileType"
              label="档案类型"
              :min-width="getWidth(5)">
            </el-table-column>
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
            <el-table-column
              prop="caseChildNum"
              label="卷内件图像数（个）"
              :min-width="getWidth(5)"
            >
            </el-table-column>
          </template>
        </SummaryTable>
        <SummaryTable :tableTitle="tableTitle"
                      :tableData="tableData8"
                      id="tableData8"
                      ref="SummaryTable" :commonTable="false" :spanTable="true"
                      v-show="treeVal.label==='核查项目统计'">
          <template slot="table">
            <el-table-column
              prop="projectName"
              label="项目名称"
              :min-width="getWidth(5)">
            </el-table-column>
            <el-table-column
              prop="checkTheStatus"
              label="核查状态"
              :formatter="checkTheStatusFormatter"
              :min-width="getWidth(5)">
            </el-table-column>
            <el-table-column
              prop="numberOfDaysToVerify"
              label="核查时长/天"
              :min-width="getWidth(5)"
            >
            </el-table-column>
            <el-table-column
              prop="numberOfPeopleVerified"
              label="核查人数"
              :min-width="getWidth(5)">
            </el-table-column>

            <el-table-column
              prop="numberOfFiles"
              label="案卷数"
              :min-width="getWidth(5)">
            </el-table-column>

            <el-table-column
              prop="numberOfFilesInTheVolume"
              label="卷内文件数"
              :min-width="getWidth(5)"
            >
            </el-table-column>
            <el-table-column
              prop="numberOfImagesInTheVolume"
              label="卷内图像数"
              :min-width="getWidth(5)"
            >
            </el-table-column>
          </template>
        </SummaryTable>
        <SummaryTable :tableTitle="tableTitle"
                      :tableData="tableData7"
                      id="tableData7"
                      ref="SummaryTable" :commonTable="false" :spanTable="true" :spanMap="spanMap"
                      v-show="treeVal.label==='核查项目与人数统计'">
          <template slot="table">
            <el-table-column
              prop="year"
              label="年度"
              :min-width="getWidth(6)">
            </el-table-column>
            <el-table-column
              prop="months"
              label="月份"
              :min-width="getWidth(6)">
            </el-table-column>
            <el-table-column
              prop="projectName"
              label="项目名称"
              :min-width="getWidth(6)"
            >
            </el-table-column>
            <el-table-column
              prop="numberOfPeopleVerified"
              label="人次"
              :min-width="getWidth(6)"
            >
            </el-table-column>
            <el-table-column
              prop="numberOfFiles"
              label="案卷数"
              :min-width="getWidth(6)"
            >
            </el-table-column>
            <el-table-column
              prop="numberOfFilesInTheVolume"
              label="卷内文件数"
              :min-width="getWidth(6)"
            >
            </el-table-column>
            <el-table-column
              prop="numberOfImagesInTheVolume"
              label="卷内图像数"
              :min-width="getWidth(7)"
            >
            </el-table-column>
          </template>
        </SummaryTable>
        <SummaryTable :tableTitle="tableTitle"
                      :tableData="tableData9"
                      id="tableData9"
                      ref="SummaryTable"
                      :span-map="spanMap"
                      v-show="treeVal.label==='核查项目结果统计'">
          <template slot="table">
            <el-table-column
              prop="projectName"
              label="项目名称"
              :min-width="getWidth(10)"
            >
            </el-table-column>
<!--            <el-table-column-->
<!--              prop="checkTheStatus"-->
<!--              label="核查状态"-->
<!--              :formatter="checkTheStatusFormatter"-->
<!--              :min-width="getWidth(20)">-->
<!--            </el-table-column>-->
            <el-table-column
              prop="numberOfDaysToVerify"
              label="核查时长/天"
              :min-width="getWidth(20)">
            </el-table-column>
            <el-table-column
              prop="numberOfFiles"
              label="案卷总数"
              :min-width="getWidth(20)">
            </el-table-column>
            <el-table-column
              prop="procedureName"
              label="工序名称"
              :min-width="getWidth(10)">
            </el-table-column>
            <el-table-column label="实体档案">
              <el-table-column
                prop="entityQualifiedCount"
                label="合格"
                :min-width="getWidth(16)">
              </el-table-column>
              <el-table-column
                prop="entityUnqualifiedCount"
                label="不合格"
                :min-width="getWidth(16)">
              </el-table-column>
              <el-table-column
                prop="entityImpeachCount"
                label="存疑"
                :min-width="getWidth(16)">
              </el-table-column>
            </el-table-column>
            <el-table-column label="电子档案">
              <el-table-column
                prop="electronQualifiedCount"
                label="合格"
                :min-width="getWidth(16)">
              </el-table-column>
              <el-table-column
                prop="electronUnqualifiedCount"
                label="不合格"
                :min-width="getWidth(16)">
              </el-table-column>
              <el-table-column
                prop="electronImpeachCount"
                label="存疑数"
                :min-width="getWidth(16)">
              </el-table-column>
            </el-table-column>
            <el-table-column label="电子档案对照实体">
              <el-table-column
                prop="electronicQualifiedCount"
                label="合格"
                :min-width="getWidth(16)">
              </el-table-column>
              <el-table-column
                prop="electronicUnqualifiedCount"
                label="不合格"
                :min-width="getWidth(16)">
              </el-table-column>
              <el-table-column
                prop="electronicImpeachCount"
                label="存疑"
                :min-width="getWidth(16)">
              </el-table-column>
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
  import {
    verificationOfProjectStatistics,
    verificationItemsAndPeopleCounting,
    checkTheStatisticsOfTheResultsOfTheProject
  } from "@/api/statistics/statistics"

  export default {
    name: "index",
    components: {SummaryTable, MainFlexibleTree},
    data() {
      return {
        treeData: [
          {
            label: '全部数据',
            children: [
              {
                label: '核查档案总量统计表（全宗）',
              }, {
                label: '核查档案类型保管期限总表',
              }, {
                label: '核查档案总量统计表（年度）',
              }, {
                label: '核查档案类型数量总表',
              }, {
                label: '核查项目统计',
                value: '核查项目统计',
                state: 1 //0-核查中 1-所有数据
              }, {
                label: '核查项目与人数统计',
                value: '核查项目与人数统计',
                state: 1 //0-核查中 1-所有数据
              }, {
                label: '核查项目结果统计',
                value: '核查项目结果统计',
                state: 1 //0-核查中 1-所有数据
              }, {
                label: '档案类型数量',
              }
            ]
          },
          {
            label: '核查中数据',
            children: [
              {
                label: '核查档案总量统计表（全宗）',
              }, {
                label: '核查档案类型保管期限总表',
              }, {
                label: '核查档案总量统计表（年度）',
              }, {
                label: '核查档案类型数量总表',
              }, {
                label: '核查项目统计',
                value: '核查项目统计',
                state: 0 //0-核查中 1-所有数据
              }, {
                label: '核查项目与人数统计',
                value: '核查项目与人数统计',
                state: 0 //0-核查中 1-所有数据
              }, {
                label: '核查项目结果统计',
                value: '核查项目结果统计',
                state: 0 //0-核查中 1-所有数据
              }, {
                label: '档案类型数量',
              }
            ]
          }
          //
        ],
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        tableWidth: 0,
        treeVal: {
          label: '核查档案总量统计（全宗）',
        },
        tableTitle: '核查档案总量统计（全宗）',
        tableData1: [
          {
            recordNum: '001',
            recordName: '280',
            caseYJByCase: '0',
            caseLongByCase: '0',
            caseShortByCase: '',
            caseSumByCase: '0',
            fileYJByCase: '0',
            fileLongByCase: '0',
            fileShortByCase: '',
            fileSumByCase: '0',
            fileYJByFile: '0',
            fileLongByFile: '',
            file30ByFile: '0',
            file10ByFile: '',
            fileShortByFile: '',
            fileSumByFile: '0',
          }, {
            recordNum: '002',
            recordName: '280',
            caseYJByCase: '0',
            caseLongByCase: '0',
            caseShortByCase: '',
            caseSumByCase: '0',
            fileYJByCase: '1',
            fileLongByCase: '0',
            fileShortByCase: '',
            fileSumByCase: '0',
            fileYJByFile: '0',
            fileLongByFile: '',
            file30ByFile: '0',
            file10ByFile: '',
            fileShortByFile: '',
            fileSumByFile: '0',
          }, {
            recordNum: '003',
            recordName: '280',
            caseYJByCase: '0',
            caseLongByCase: '0',
            caseShortByCase: '',
            caseSumByCase: '0',
            fileYJByCase: '0',
            fileLongByCase: '0',
            fileShortByCase: '',
            fileSumByCase: '0',
            fileYJByFile: '0',
            fileLongByFile: '',
            file30ByFile: '0',
            file10ByFile: '',
            fileShortByFile: '',
            fileSumByFile: '0',
          }, {
            recordNum: '004',
            recordName: '280',
            caseYJByCase: '0',
            caseLongByCase: '0',
            caseShortByCase: '',
            caseSumByCase: '0',
            fileYJByCase: '0',
            fileLongByCase: '0',
            fileShortByCase: '',
            fileSumByCase: '0',
            fileYJByFile: '0',
            fileLongByFile: '',
            file30ByFile: '0',
            file10ByFile: '',
            fileShortByFile: '',
            fileSumByFile: '0',
          }, {
            recordNum: '005',
            recordName: '280',
            caseYJByCase: '0',
            caseLongByCase: '0',
            caseShortByCase: '',
            caseSumByCase: '0',
            fileYJByCase: '0',
            fileLongByCase: '0',
            fileShortByCase: '',
            fileSumByCase: '0',
            fileYJByFile: '0',
            fileLongByFile: '',
            file30ByFile: '',
            file10ByFile: '',
            fileShortByFile: '',
            fileSumByFile: '',
          }, {
            recordNum: '006',
            recordName: '280',
            caseYJByCase: '0',
            caseLongByCase: '0',
            caseShortByCase: '',
            caseSumByCase: '0',
            fileYJByCase: '0',
            fileLongByCase: '0',
            fileShortByCase: '',
            fileSumByCase: '0',
            fileYJByFile: '',
            fileLongByFile: '',
            file30ByFile: '',
            file10ByFile: '',
            fileShortByFile: '',
            fileSumByFile: '',
          }, {
            recordNum: '007',
            recordName: '280',
            caseYJByCase: '0',
            caseLongByCase: '0',
            caseShortByCase: '',
            caseSumByCase: '0',
            fileYJByCase: '0',
            fileLongByCase: '0',
            fileShortByCase: '',
            fileSumByCase: '0',
            fileLongByFile: '0',
            file30ByFile: '0',
            file10ByFile: '',
            fileShortByFile: '',
            fileSumByFile: '0',
          }, {
            recordNum: '008',
            recordName: '280',
            caseYJByCase: '0',
            caseLongByCase: '0',
            caseShortByCase: '',
            caseSumByCase: '0',
            fileYJByCase: '0',
            fileLongByCase: '0',
            fileShortByCase: '',
            fileSumByCase: '0',
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
            YJ: 40,
            Long: 0,
            total: 0,
          }, {
            fileType: '文书档案',
            typeNameChild: '文书档案（件）',
            YJ: 40,
            Long: 0,
            total: 0,
          }, {
            fileType: '科技档案',
            typeNameChild: '照片档案（件）',
            YJ: 40,
            Long: 0,
            total: 0,
          }, {
            fileType: '声像档案',
            typeNameChild: '视频档案（件）',
            YJ: 59,
            Long: 40,
            total: 0,
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
            caseTitleNum: 20,
            caseChildNum: 0,
            fileChildNum: 0,
          }, {
            fileType: '文书档案',
            typeNameChild: '文书档案（件）',
            caseTitleNum: 0,
            caseChildNum: 0,
            fileChildNum: 1,
          }, {
            fileType: '声像档案',
            typeNameChild: '照片档案（件）',
            caseTitleNum: 0,
            caseChildNum: 0,
            fileChildNum: 3,
          }, {
            fileType: '声像档案',
            typeNameChild: '视频档案（件）',
            caseTitleNum: 0,
            caseChildNum: 0,
            fileChildNum: 2,
          }, {
            fileType: '声像档案',
            typeNameChild: '音频档案（件）',
            caseTitleNum: 0,
            caseChildNum: 0,
            fileChildNum: 20,
          }],
        tableData7: [
          {
            years: '3',
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
          // {
          //   projectName: '',
          //   checkTheStatus: '',
          //   numberOfDaysToVerify: '',
          //   numberOfPeopleVerified: '',
          //   numberOfFiles: '',
          //   numberOfFilesInTheVolume: '',
          //   numberOfImagesInTheVolume: '',
          // },
          // {
          //   years: '13',
          //   mouths: '9',
          //   manTime: '9',
          //   haveFound: '8',
          //   noFound: '1'
          // }, {
          //   years: '12',
          //   mouths: '11',
          //   manTime: '3',
          //   haveFound: '3',
          //   noFound: '0'
          // }, {
          //   years: '12',
          //   mouths: '12',
          //   manTime: '1',
          //   haveFound: '1',
          //   noFound: '0'
          // }, {
          //   years: '12',
          //   mouths: '1',
          //   manTime: '3',
          //   haveFound: '3',
          //   noFound: '0'
          // }, {
          //   years: '12',
          //   mouths: '2',
          //   manTime: '4',
          //   haveFound: '4',
          //   noFound: '0'
          // }
        ],
        spanMap: {},
        tableData9: []

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
        })();
      };
      this.treeVal.label = '核查档案总量统计表（全宗）';

    },
    // 监控data中的数据变化

    methods: {
      initSpanArr(data, column) {
        let contactDot = 0;
        this.spanMap[column] = {};
        this.spanMap[column].spanArr = [];
        data.forEach((item, index) => {
          if (index === 0) {
            this.spanMap[column].spanArr.push(1); // 第一行数据 不合并
          } else {
            if (item[column] === data[index - 1][column]) {
              this.spanMap[column].spanArr[contactDot] += 1; // 下一行，如果数据相同，合并行数加一
              this.spanMap[column].spanArr.push(0);
            } else {
              contactDot = index;
              this.spanMap[column].spanArr.push(1);
            }
          }
        });
        console.log('this.spanMap', this.spanMap)
      },
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
        if (val.value == '核查项目统计') {
          verificationOfProjectStatistics(val.state).then(res => {
            this.tableData8 = res;
          });
        } else if (val.value == '核查项目与人数统计') {
          verificationItemsAndPeopleCounting(val.state).then(res => {
            this.tableData7 = res;
            this.initSpanArr(this.tableData7, 'year')
            this.initSpanArr(this.tableData7, 'months')
          });
        } else if (val.value == '核查项目结果统计') {
          checkTheStatisticsOfTheResultsOfTheProject(val.state).then(res => {
            this.tableData9 = res;
            this.initSpanArr(this.tableData9, 'projectName')
            this.initSpanArr(this.tableData9, 'checkTheStatus')
            this.initSpanArr(this.tableData9, 'numberOfDaysToVerify')
            this.initSpanArr(this.tableData9, 'numberOfFiles')
          });
        }


        // if(this.tableTitle=='档案类型保管期限总表'){
        //   console.log('111',this.$refs.SummaryTable.initSpanArr(this.tableData2,'fileType'));
        // }
      },
      checkTheStatusFormatter(row, column, cellValue) {
        let result = '';
        if ('0' == cellValue) {
          result = '未开始';
        } else if ('1' == cellValue) {
          result = '核查中';
        } else {
          result = '已完成';
        }
        return result;
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
