<template>
  <div class="app-container-padding">
    <TablePage class="tablePage"
               :hasSelection="false"
               :is-title="false"
               :tableTitle="caseTitle"
               :tableData="caseData"
               :pageSize="queryParams.pageSize"
               :pageNum="queryParams.pageNum"
               :total="total"
               :isPage="false"
               @handleRowClick="viewTheFileHandleProjectClick"
               @handleChange="handleChange">
      <template slot="table">
        <el-table-column
          show-overflow-tooltip
          v-for="(item,index) in caseColumn"
          :key="index"
          :prop="item.prop"
          :label="item.label"
          :min-width="item.width"
          :formatter="item.formatter">
        </el-table-column>
      </template>
    </TablePage>
    <div class="imgContent">
      <p class="imgDes">第{{ imgPageItem }}件第{{ this.imgPageNum }}页</p>
      <div class="imgDiv">
        <DigitalPhoto :imgList="imgList" @carouselToggle="carouselToggle" @downloadTheImage="downloadTheImage"></DigitalPhoto>
      </div>
    </div>
  </div>
</template>

<script>
  import TablePage from "@/components/Public/table/TablePage.vue";
  import DigitalPhoto from "@/views/projectManage/projectInitialize/digitalPhoto.vue";
  import {
    getOne,
  } from "@/api/projectManage/projectInitialize"

  import {
    download,
    getPucter,
    queryCaseLevelArchivesDossierLevelTemplateTwo
  } from "@/api/record/record";
  import {
    obtainCaseDataAttr,
    selectListAndMetadata,
    theFileNumberFieldIsThatDatabaseField
  } from "@/api/business/archiveType";


  export default {
    name: "myTask",
    components: {TablePage,DigitalPhoto},

    data() {
      return {
        queryParams: {
          pageNum: 1,
          pageSize: 20,
          filesName: '',
        },
        caseTitle: '案卷信息',
        fileTitle: '文件信息',
        caseData: [],
        caseColumn: [],
        total: 0,
        fileData: [],
        projectId: '',
        filesId: '',
        projectFrom: [],
        /*图片集合*/
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
      this.projectId = this.$route.query.projectId;
      this.filesId = this.$route.query.filesId;
      this.getOne();
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
        this.numberOfCarouselPages = val
        this.imgPageNum = this.imgList[val].pageNum;
      },
      /*获取档号在哪个attr*/
      obtainCaseDataAttr() {
        const arr = {
          metadataName: '档号',
          archiveTypeId: this.projectFrom.archiveTypeId
        };
        obtainCaseDataAttr(arr).then(res => {
          this.attrOfTheCaseFileNumber = "attr" + res.data;
        });
        const arr1 = {
          metadataName: '顺序号/件号',
          archiveTypeId: this.projectFrom.archiveTypeId
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


      /*清空*/
      empty() {
        this.imgList = [];
        this.imgPageItem = 0
        this.imgPageNum = 0
      },

      /*案件表格点击事件*/
      viewTheFileHandleProjectClick(row, column, event) {
        this.empty();
        this.imgPageItem = row.row[this.sequenceNumber]
        this.fileForm = row.row
        this.getPucter();
      },
      /**********************************************************图片处理 -- 结束********************************/


      getOne() {
        getOne(this.projectId).then(res => {
          this.projectFrom = res.data;
          this.selectListAndMetadataCase();
          this.theFileNumberFieldIsThatDatabaseField();
          this.obtainCaseDataAttr();
        });
      },

      /*根据档案类型id查询 档号 在数据库的那个字段*/
      theFileNumberFieldIsThatDatabaseField() {
        theFileNumberFieldIsThatDatabaseField(this.projectFrom.archiveTypeId).then(res => {
          this.queryParams.attrOrder = res.data.attrOrder;
        });
      },

      selectListAndMetadataCase() {
        const arr2 = {
          id: this.projectFrom.archiveTypeId,
          level: 2 //文件表
        };
        /*文件表头*/
        selectListAndMetadata(arr2).then(res => {
          res.data[0].dahcMetadataList.map(item => {
            item.label = item.metadataName;
            item.prop = 'attr' + item.attrOrder;
            item.width = item.metadataWidth;
          });
          this.caseColumn = res.data[0].dahcMetadataList;

          this.queryCaseLevelArchivesDossierLevelTemplateTwo();
        });
      },


      /*查询文件数据*/
      queryCaseLevelArchivesDossierLevelTemplateTwo() {
        const arr = {
          filesId: this.filesId,
          archiveId: this.projectFrom.archiveTypeId,
          pageNum: this.queryParams.pageNum,
          pageSize: this.queryParams.pageSize,
          attrOrder: 'attr' + this.queryParams.attrOrder,
          caseNum: this.queryParams.filesName,
        };
        queryCaseLevelArchivesDossierLevelTemplateTwo(arr).then(res => {
          this.caseData = res.data
          this.fileForm = res.data[0];
          this.imgPageItem = res.data[0][this.sequenceNumber]
          this.getPucter();
        });

      },

      /**
       * 分页器
       */
      handleChange(pageSize, pageNum) {
        this.queryParams.pageSize = pageSize;
        this.queryParams.pageNum = pageNum;
        this.handleQuery();
      },

    }
  }
</script>

<style lang="scss" scoped>
  .app-container-padding {
    display: flex;

    .tablePage {
      width: 40%;
      height: calc(100% - 70px);
      margin-top: 5px;
    }

    .imgContent {
      height: calc(83vh + 20px);
      width: calc(55% - 10px);

      .imgDes {
        font-weight: 600;
        font-size: 13px;
        color: #606266;
        position: relative;
        left: 5px;
      }

      .imgDiv {
        height: calc(83vh - 3px);
        width: 100%;
        border: 1px solid #dfe6ec;
        margin-top: 3px;
      }
    }
  }
</style>

