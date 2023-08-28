<template>
  <div class="app-container">
    <MainFlexibleTree :data="treeData" :defaultProps="treeDefaultProps" @handleNodeClick="handleNodeClick">
      <div slot="mainRight" style="height: 100%">
        <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="iptAndBtn"
                 style="position: relative" @submit.native.prevent>
          <div style="white-space: nowrap;margin-right: 90px;">
            <el-form-item label="案卷档号" prop="uploadName">
              <el-input
                v-model="queryParams.caseNum"
                placeholder="请输入案卷档号"
                clearable/>
            </el-form-item>
            <!--            <el-form-item label="上传人" prop="uploadName">-->
            <!--              <el-input-->
            <!--                v-model="queryParams.uploadName"-->
            <!--                placeholder="请输入上传人"-->
            <!--                clearable/>-->
            <!--            </el-form-item>-->
            <el-form-item style="min-width: auto">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="conditionalSearch">搜索</el-button>
            </el-form-item>
          </div>
          <div style="right:5px;position: absolute">
            <el-button type="primary" icon="el-icon-refresh-left" size="mini" @click="handleBack">返回</el-button>
          </div>
        </el-form>
        <TablePage class="tablePage1"
                   :hasSelection="false"
                   :is-title="false"
                   :tableData="caseData"
                   :total="caseTotal"
                   :pageSize="queryParams.pageSize"
                   :pageNum="queryParams.pageNum"
                   @handleRowClick="handleProjectClick"
                   @handleChange="handleCheckItem">
          <template slot="table">
            <el-table-column
              show-overflow-tooltip
              v-for="(item,index) in caseColumn"
              :key="index"
              :prop="item.prop"
              :label="item.label"
              :min-width="item.width"/>

            <el-table-column
              label="操作"  fixed="right"
              width="120" class-name="operation" >
              <template slot-scope="scope">
                <el-button
                  type="text" size="mini" @click="dialogFile=true">查看文件
                </el-button>
              </template>
            </el-table-column>
          </template>
        </TablePage>
        <!--        <TablePage class="tablePage2"-->
        <!--                   :hasSelection="false"-->
        <!--                   :isPage="false"-->
        <!--                   :is-title="true"-->
        <!--                   :tableTitle="fileTitle"-->
        <!--                   :tableData="fileData">-->
        <!--          <template slot="table">-->
        <!--            <el-table-column-->
        <!--              show-overflow-tooltip-->
        <!--              v-for="(item,index) in fileColumn"-->
        <!--              :key="index"-->
        <!--              :prop="item.prop"-->
        <!--              :label="item.label"-->
        <!--              :min-width="item.width">-->
        <!--            </el-table-column>-->
        <!--          </template>-->
        <!--        </TablePage>-->
      </div>
    </MainFlexibleTree>
    <el-dialog title="查看文件"
               :visible.sync="dialogFile"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :destroy-on-close="true"
               width="90%" class="dialog-style dialogFile">
      <TablePage class="tablePage2"
                 :hasSelection="false"
                 :isPage="false"
                 :is-title="true"
                 @handleRowClick="viewTheFileHandleProjectClick"
                 :tableTitle="fileTitle"
                 :tableData="fileData">
        <template slot="table">
          <el-table-column
            show-overflow-tooltip
            v-for="(item,index) in fileColumn"
            :key="index"
            :prop="item.prop"
            :label="item.label"
            :min-width="item.width">
          </el-table-column>
        </template>
      </TablePage>
      <div class="imgContent">
        <p class="imgDes">第{{imgPageItem}}件第{{ this.imgPageNum }}页</p>
        <div class="imgDiv">
          <DigitalPhoto :imgList="imgList" @carouselToggle="carouselToggle"@downloadTheImage="downloadTheImage"></DigitalPhoto>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import MainFlexibleTree from "@/components/Public/MainBody/MainFlexibleTree.vue";
  import TablePage from "@/components/Public/table/TablePage.vue";
  import {getOne, queryArchiveBatchData} from "@/api/projectManage/projectInitialize"
  import {
    obtainCaseDataAttr,
    selectListAndMetadata,
    theFileNumberFieldIsThatDatabaseField
  } from "@/api/business/archiveType";
  import {
    download,
    getPucter,
    queryCaseLevelArchivesDossierLevelTemplateTwo,queryArchivesDossierLevelTemplateTwoBackup,
    queryElectronicArchivesDossierLevel
  } from "@/api/record/record";
  import {queryCaseFileDataBasedOnBatches} from "@/api/fileData/fileData";
  import DigitalPhoto from "@/views/projectManage/projectInitialize/digitalPhoto.vue";

  export default {
    name: "ArchivesData",
    components: {DigitalPhoto, TablePage, MainFlexibleTree},
    data() {
      return {
        treeData: [],
        treeDefaultProps: {
          children: 'children',
          label: 'batchNo',
          value: 'batchNo',
        },
        queryParams: {
          caseNum: '',
          uploadName: '',
          attrOrder: '',  //档号在数据库的arrt
          pageSize: 20,
          pageNum: 1,
        },
        caseTitle: '案卷信息：',
        caseData: [],
        caseColumn: [],
        caseTotal: 0,
        caseSize: 10,
        caseNum: 1,
        fileTitle: '文件信息：',
        fileData: [],
        fileColumn: [],
        /*项目id*/
        receptionProjectId: '',
        /*档案类型id*/
        receptionArchiveTypeId: '',
        /*批次名称*/
        batchNo: '',
        selectListAndMetadataTable: [],
        /*案卷ID*/
        filesId: [],
        dialogFile: false,
        // imgList: [],
        imgList: [],
        /*案件数据*/
        fileForm: {},
        /*文件档号的attr*/
        attrOfTheCaseFileNumber: '',
        /*顺序号*/
        sequenceNumber: '',
        imgPageNum: 0,
        imgPageItem: 0,
        /*轮播图页数*/
        numberOfCarouselPages: 0
      }
    },
    created() {
      // console.log('this.$route',this.$route)
      this.receptionProjectId = this.$route.query.projectId;
      this.getsTheProfileType();
    },
    methods: {

      /**********************************************************图片处理 -- 开始********************************/
      /*下载图片*/
      downloadTheImage() {
        console.log(this.imgList[this.numberOfCarouselPages],"下载")
        if (this.imgList.length == 0) {
          this.$message({message: '不存在图片数据，不能下载图片', type: 'warning'});
        } else {
          this.$confirm('此操作将下载文件：' + this.imgList[this.numberOfCarouselPages].keyName + '下图片名称为：' + this.imgList[this.numberOfCarouselPages].fileName + '的数据, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }).then(() => {
            download({path: this.imgList[this.numberOfCarouselPages].filePath}).then(res => {
              const fileName = this.imgList[this.numberOfCarouselPages].filePath.lastIndexOf("\\");
              let href = window.URL.createObjectURL(new Blob([res])) // 根据后端返回的url对应的文件流创建URL对象
              const link = document.createElement('a')//创建一个隐藏的a标签
              link.style.display = 'none'
              link.href = href//设置下载的url
              link.download = this.imgList[this.numberOfCarouselPages].filePath.substr(fileName + 1) //设置下载的文件名
              document.body.appendChild(link)
              link.click()
              document.body.removeChild(link)
              window.URL.revokeObjectURL(href) // 释放掉blob对象
            });
          });
        }
      },
      /*轮播图切换*/
      carouselToggle(val) {
        this.imgPageNum = this.imgList[val].pageNum;
      },
      /*获取档号在哪个attr*/
      obtainCaseDataAttr() {
        const arr = {
          metadataName: '档号',
          archiveTypeId: this.receptionArchiveTypeId
        };
        obtainCaseDataAttr(arr).then(res => {
          this.attrOfTheCaseFileNumber = "attr" + res.data;
        });
        const arr1 = {
          metadataName: '顺序号/件号',
          archiveTypeId: this.receptionArchiveTypeId
        };
        obtainCaseDataAttr(arr1).then(res => {
          this.sequenceNumber = "attr" + res.data;
        });
      },

      /*查询图片信息*/
      getPucter() {
        const arr = {
          keyName: this.fileForm[this.attrOfTheCaseFileNumber], //档号
          pid: this.fileForm.attrId //pid
        }
        getPucter(arr).then(res => {
          if (res.data.length > 0) {
            this.imgList = res.data;
            this.imgPageNum = res.data[0].pageNum
            this.imgList.map(item => {
              item.imgUrl = 'data:image/jpeg;base64,' + item.imgUrl;
              let url = item.imgUrl;
              const img = new Image();
              img.src = url;
            });
          }
        });
      },
      /**********************************************************图片处理 -- 结束********************************/
      /**************************获取基础数据 -- 开始*******************************/
      /* 获取档案类型*/
      getsTheProfileType() {
        getOne(this.$route.query.projectId).then(res => {
          this.receptionArchiveTypeId = res.data.archiveTypeId;
          this.obtainCaseDataAttr();
          this.theFileNumberFieldIsThatDatabaseField();
          this.queryArchiveBatchData();
        });
      },

      /*根据档案类型id查询 档号 在数据库的那个字段*/
      theFileNumberFieldIsThatDatabaseField() {
        theFileNumberFieldIsThatDatabaseField(this.receptionArchiveTypeId).then(res => {
          this.queryParams.attrOrder = res.data.attrOrder;
        });
      },
      /**************************获取基础数据 -- 结束*******************************/
      /****************************************************************tree树 -- 开始***************************************************/
      /*查询档案批次数据*/
      queryArchiveBatchData() {
        const arr = {
          id: this.receptionProjectId,
          archiveTypeId: this.receptionArchiveTypeId
        }
        queryArchiveBatchData(arr).then(res => {
          this.treeData = res.data;
          this.selectListAndMetadata();
        });
      },

      /**
       * 点击tree树
       * @param val
       * @param node
       * @param component
       */
      handleNodeClick(val, node, component) {
        this.queryParams.pageNum = 1
        this.queryParams.pageSize = 20
        this.batchNo = val.batchNo;
        this.handleQuery();
      },
      /**************************************************************tree树 -- 结束*****************************************************/
      /*************************************************************表格逻辑 -- 开始 ***************************************************/
      /*查询表头*/
      selectListAndMetadata() {
        const arr = {
          id: this.receptionArchiveTypeId,
          level: 1
        };
        /*表头*/
        selectListAndMetadata(arr).then(res => {
          this.selectListAndMetadataTable = res.data
          this.selectListAndMetadataTable[0].dahcMetadataList.map(item => {
            item.label = item.metadataName;
            item.prop = 'attr' + item.attrOrder;
            item.width = item.metadataWidth;
          });
          this.caseColumn = this.selectListAndMetadataTable[0].dahcMetadataList;
          if (this.treeData.length > 0) {
            this.handleQuery();
          }

          this.selectListAndMetadataCase();
        });
      },

      selectListAndMetadataCase() {
        const arr2 = {
          id: this.receptionArchiveTypeId,
          level: 2 //案卷表
        };
        /*文件表头*/
        selectListAndMetadata(arr2).then(res => {
          res.data[0].dahcMetadataList.map(item => {
            item.label = item.metadataName;
            item.prop = 'attr' + item.attrOrder;
            item.width = item.metadataWidth;
          });
          this.fileColumn = res.data[0].dahcMetadataList;
        });
      },

      /*条件搜索*/
      conditionalSearch() {
        this.queryParams.pageNum = 1
        this.queryParams.pageSize = 20
        this.handleQuery();
      },
      /*查询表数据*/
      handleQuery() {
        const arr = {
          batchNo: this.batchNo,//批次名称
          projectId: this.receptionProjectId,
          archiveLevelName: this.selectListAndMetadataTable[0].tableLevel1En + '_backup', //案卷级的表名称
          attrOrder: 'attr' + this.queryParams.attrOrder,
          caseNum: this.queryParams.caseNum,
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize
        };
        queryCaseFileDataBasedOnBatches(arr).then(res => {
          this.caseData = res.data;
          this.caseTotal = res.total;
          if (res.data.length > 0) {
            this.filesId = res.data[0].id;
            // this.queryCaseLevelArchivesDossierLevelTemplateTwo();
          } else {
            this.fileData = [];
          }
        });
      },

      /*查询文件数据*/
      queryCaseLevelArchivesDossierLevelTemplateTwo() {
        const arr = {
          filesId: this.filesId,
          archiveId: this.receptionArchiveTypeId,
          startTheVerification: 1
        };
        queryArchivesDossierLevelTemplateTwoBackup(arr).then(res => {
          this.fileData = res.data
          this.fileForm = res.data[0];
          this.imgPageItem = res.data[0][this.sequenceNumber]
          this.getPucter();
        });

      },

      /*清空*/
      empty() {
        this.imgList = [];
        this.imgPageItem = 0
        this.imgPageNum = 0
      },
      /*表格点击事件*/
      handleProjectClick(row, column, event) {
        this.empty();
        this.filesId = row.row.id
        this.queryCaseLevelArchivesDossierLevelTemplateTwo();
      },

      /*案件表格点击事件*/
      viewTheFileHandleProjectClick(row, column, event) {
        this.empty();
        this.imgPageItem = row.row[this.sequenceNumber]
        this.fileForm = row.row
        this.getPucter();
      },
      /***********************************************************表格逻辑 -- 结束 *****************************************************/

      /**
       * 返回
       */
      handleBack() {
        this.$router.go(-1);
      },
      /**
       * 案卷分页
       */
      handleCheckItem(pageSize, pageNum) {
        this.queryParams.pageSize = pageSize;
        this.queryParams.pageNum = pageNum;
        this.handleQuery();
      },
    }
  }
</script>

<style lang="scss" scoped>
  .tablePage1 {
    height: calc(100% - 55px) !important;
    margin-top: 5px;
  }

  ::v-deep .dialogFile {
    .el-dialog__body {
      display: flex;
      justify-content: space-between;

      .tablePage2 {
        height: calc(83vh);
        margin-top: 20px;
        width: calc(45% - 10px) !important;
      }

      .imgContent {
        height: calc(83vh + 20px) !important;
        width: calc(55% - 10px);

        .imgDes {
          font-weight: 600;
          font-size: 13px;
          color: #606266;
          position: relative;
          left: 5px;
        }

        .imgDiv {
          height: calc(83vh - 3px) !important;
          width: 100%;
          border: 1px solid #dfe6ec;
          margin-top: 3px;
        }
      }
    }
  }

</style>
