<template>
  <div class="app-container-padding">
    <el-form :model="queryParams" :inline="true" size="mini" class="iptAndBtn">
      <div style="white-space: nowrap;margin-right: 90px;">
        <el-form-item label="核查项目" prop="projectName">
          <el-select v-model="queryParams.projectName" placeholder="请选择核查项目" maxlength="30"
                     @change="projectChange">
            <el-option v-for="metaDataItem in projectOptions"
                       :key="metaDataItem.id"
                       :label="metaDataItem.projectName"
                       :value="metaDataItem.id"
                       :disabled="metaDataItem.projectState==2"
            ></el-option>
          </el-select>
        </el-form-item>
        <!--        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>-->
      </div>
<!--      <div style="right:30px;position: fixed">-->
<!--        存疑核查项总数: <span style="color: red;">{{ queryParams.questionNum }}</span>-->
<!--      </div>-->
    </el-form>
    <div class="top-box">
      <div class="top-box-item">
        <svg-icon class="svgicon" icon-class="success" style="font-size: 14px"></svg-icon>
        <div class="top-box-tit">
          <div>已领取任务</div>
          <div>{{ count1 }}</div>
        </div>
      </div>
      <div class="top-box-item">
        <svg-icon class="svgicon" icon-class="unclaimed"></svg-icon>
        <div class="top-box-tit">
          <div>待领取任务</div>
          <div>{{ count2 }}</div>
        </div>
      </div>
      <div class="top-box-item">
        <svg-icon class="svgicon" icon-class="checked" style="font-size: 18px"></svg-icon>
        <div class="top-box-tit">
          <div>已核查任务</div>
          <div>{{ count3 }}</div>
        </div>
      </div>
      <div class="top-box-item">
        <svg-icon class="svgicon" icon-class="unverified"></svg-icon>
        <div class="top-box-tit">
          <div>待核查任务</div>
          <div>{{ count4 }}</div>
        </div>
      </div>
      <div class="top-box-item">
        <svg-icon class="svgicon" icon-class="impeach"></svg-icon>
        <div class="top-box-tit">
          <div>存疑任务</div>
          <div>{{ count5 }}</div>
        </div>
      </div>
    </div>
    <div style="margin-top: 10px;">
      <p class="title">核查工序：</p>
      <div class="down-box processArr" ref="processArr">
        <div class="down-box-button" v-for="(item,index) in processArr" :key="index" @click="handleProcess(item)">
          <div class="processArrIcon">
            <svg-icon class="svgicon" icon-class="physicalFile" style="color: #59c4e6"
                      v-show="item.trueingPagePathIcon==='physicalFile'"></svg-icon>
            <svg-icon class="svgicon" icon-class="electronicFile" style="color: #edafda"
                      v-show="item.trueingPagePathIcon==='electronicFile'"></svg-icon>
            <svg-icon class="svgicon" icon-class="electReferPhyFile" style="color: #93b7e3"
                      v-show="item.trueingPagePathIcon==='electReferPhyFile'"></svg-icon>
          </div>
          <div class="processArrContent">
            <p>{{ item.procedureName }}</p>
            <p class="procedureName" v-show="item.trueingPagePathIcon==='physicalFile'">工序 <span
              style="background-color: #59c4e6;"> {{ item.sort }}</span></p>
            <p class="procedureName" v-show="item.trueingPagePathIcon==='electronicFile'">工序 <span
              style="background-color: #edafda;"> {{ item.sort }}</span></p>
            <p class="procedureName" v-show="item.trueingPagePathIcon==='electReferPhyFile'">工序 <span
              style="background-color: #93b7e3;"> {{ item.sort }}</span></p>
          </div>
        </div>
      </div>
    </div>
    <div style="margin-top: 10px;">
      <p class="title">统一处理：</p>
      <div class="down-box">
        <div class="down-box-button" @click="handleResolve" v-hasPermi="['main:index:resolve']">
          <p>解决存疑</p>
        </div>
        <div class="down-box-button" v-hasPermi="['main:index:replace']">
          <p>统一替换</p>
        </div>
        <div class="down-box-button" @click="taskManagement" v-hasPermi="['main:index:management']">
          <p>任务管理</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {getOne, queryUserProject} from "@/api/projectManage/projectInitialize"
  import {queryUserProjectProcedure} from "@/api/projectManage/projectProcedure"
  import {homepageCheckResultQuery,theTaskHasBeenClaimed,homePageToBePickedUpTaskQuery} from "@/api/record/record";
  import {queryProcedureData} from "@/api/dahc/sys/dictData"
  import {selectListAndMetadata} from "@/api/business/archiveType";
  import item from "@/layout/components/Sidebar/Item.vue";

  export default {
    name: "home",
    data() {
      return {
        returnState: 20000,
        queryParams: {
          questionNum: 0,
        },
        projectOptions: [],
        processArr: [],
        /*项目id*/
        projectId: '',
        archiveTypeId: '',
        count1: 0,
        count2: 0,
        count3: 0,
        count4: 0,
        count5: 0,

      }
    },
    created() {
      this.getUserProject();
    },
    methods: {

      /*查询核查结果*/
      checkTheVerificationResults() {
        this.theTaskHasBeenClaimed();
        this.tasksToBeClaimed();
        this.tasksVerified();
        this.tasksToBeVerified();
        this.doubtfulTasks();
      },
      /* 已领取任务*/
      theTaskHasBeenClaimed() {
        const arr = {
          projectId: this.projectId,
          // isProcedureInspect:
        }
        theTaskHasBeenClaimed(arr).then(res => {
          this.count1 = res;
        });
      },
      /*待领取任务*/
      tasksToBeClaimed() {
        const arr = {
          projectId: this.projectId,
          isProcedureInspect: 0
        }
        homePageToBePickedUpTaskQuery(arr).then(res => {
          this.count2 = res;
        });
      },

      /*已核查任务*/
      tasksVerified() {
        const arr = {
          projectId: this.projectId,
          isProcedureInspect: 1
        }
        homepageCheckResultQuery(arr).then(res => {
          this.count3 = res;
        });
      },
      /*存疑任务*/
      doubtfulTasks() {
        const arr = {
          projectId: this.projectId,
          isProcedureInspect: 3
        }
        homepageCheckResultQuery(arr).then(res => {
          this.count5 = res;
        });
      },
      /*待核查任务*/
      tasksToBeVerified() {
        const arr = {
          projectId: this.projectId,
          isProcedureInspect: 2
        }
        homepageCheckResultQuery(arr).then(res => {
          this.count4 = res;
        });
      },


      getUserProject() {
        queryUserProject().then(res => {
          if (res.code === this.returnState) {
            this.projectOptions = res.data;
            this.projectId = this.projectOptions[0].id;
            this.queryParams.projectName = this.projectOptions[0].id;
            this.queryUserProjectProcedure();
            this.checkTheVerificationResults();
          }
        });
      },
      /*下拉框值改变*/
      projectChange(val) {
        this.projectId = val;
        this.queryUserProjectProcedure();
        this.checkTheVerificationResults();
      },
      queryUserProjectProcedure() {
        queryUserProjectProcedure(this.projectId).then(res => {
          if (res.code === this.returnState) {
            this.processArr = res.data;
            if (this.processArr) {
              this.processArr.forEach(item => {
                if (item.trueingPagePath.indexOf('physicalFile') !== -1) {
                  item.trueingPagePathIcon = 'physicalFile'
                }
                if (item.trueingPagePath.indexOf('electronic') !== -1) {
                  item.trueingPagePathIcon = 'electronicFile'
                }
                if (item.trueingPagePath.indexOf('electReferPhy') !== -1) {
                  item.trueingPagePathIcon = 'electReferPhyFile'
                }

              })
            }
          }
        });
      },
      handleProcess(url) {
        getOne(url.projectId).then(res => {
          this.archiveTypeId = res.data.archiveTypeId;
          this.$router.push({
            path: url.trueingPagePath, query: {
              receptionProcedureId: url.id,
              receptionArchiveTypeId: this.archiveTypeId,
              isImpeach: false
            }
          });

        });
      },
      // queryProcedureData() {
      //   queryProcedureData("1a6d05eb1eb94968adca7038092f5bf2").then(res => {
      //     console.log(res,"ddwwww")
      //   });
      // },
      handleResolve() {
        this.$router.push({path: '/resolveDoubts'});
      },

      taskManagement() {
        this.$router.push({
          path: '/taskManage',
          query: {
            receptionProjectId: this.queryParams.projectName,
          }
        });

      }
    }
  }
</script>

<style scoped lang="scss">

  .iptAndBtn {
    background-color: transparent;
    height: 40px;

    .el-form-item {
      width: 500px;

      ::v-deep .el-form-item__content {
        width: 400px;
        max-width: 500px;

        .el-select {
          width: 100%;
        }
      }
    }
  }

  .title {
    padding-left: 10px;
    font-size: 14px;
    font-weight: 600;
    color: #00afff;
  }

  .top-box {
    display: flex;
    justify-content: space-between;
    width: 100%;
    padding: 5px;

    .top-box-item {
      width: 19%;
      height: 150px;
      border-radius: 10px;
      display: flex;
      align-items: center;
      justify-content: center;
      color: #FFFFFF;

      .top-box-tit {
        width: 60%;

        div:first-child {
          font-size: 14px;
          color: #fff;
          margin-left: 20px;
        }

        div:last-child {
          font-size: 38px;
          color: #fff;
          font-weight: bold;
          margin-left: 20px;
        }
      }

      .svgicon {
        zoom: 5;
      }
    }

    .top-box-item:first-child {
      box-shadow:  #8ec5fc 0px 1px 1px 0px inset,#3C8CE7  0px 15px 60px -30px;
      background-image: linear-gradient(to right, #8ec5fc ,#3C8CE7 );
    }

    .top-box-item:nth-child(2) {
      box-shadow: #d5aaff 0px 1px 1px 0px inset, #ae70ff 0px 15px 60px -30px;
      background-image: linear-gradient(to right, #d5aaff, #ae70ff);
    }

    .top-box-item:nth-child(3) {
      box-shadow: #87e0be 0px 1px 1px 0px inset, #55c693 0px 15px 60px -30px;
      background-image: linear-gradient(to right, #87e0be, #55c693);
    }

    .top-box-item:nth-child(4) {
      box-shadow: #e5df68 0px 1px 1px 0px inset, #e4c849 0px 15px 60px -30px;
      background-image: linear-gradient(to right, #e5df68, #e4c849);
    }
    .top-box-item:nth-child(5) {
      box-shadow: #ff9dbd 0px 1px 1px 0px inset, #ff5a83 0px 15px 60px -30px;
      background-image: linear-gradient(to right, #ff9dbd, #ff5a83);
    }
  }

  .down-box {
    //margin-top: 10px;
    width: 100%;
    //padding: 10px;
    display: flex;
    //justify-content: space-between;
    flex-wrap: wrap;
    align-items: center;
    overflow: auto;
    //border-bottom: 2px solid #eeeeee;


    .down-box-button {
      width: 19%;
      height: 150px;
      border-radius: 10px;
      padding: 10px 0;
      margin: 5px 10px 5px 0;
      border: 1px solid #eeeeee;
      box-shadow: rgba(0, 0, 0, 0.1) 0px 4px 12px;
      display: flex;
      align-items: center;
      justify-content: center;

      .processArrIcon {
        width: 30%;
        display: inline-flex;
        justify-content: center;
        align-items: center;

        .svgicon {
          font-size: 50px;
        }
      }

      .processArrContent {
        width: 70%;

        p:first-of-type {
          font-size: 16px;
          margin-bottom: 10px;
        }

        .procedureName {
          font-size: 14px;

          span {
            display: inline-flex;
            justify-content: center;
            align-items: center;
            width: 20px;
            height: 20px;
            border-radius: 50%;
            color: #fff
          }

        }
      }
    }
  }

  .processArr {
    max-height: calc((100vh - 380px) / 3 * 2);
    border-top: 3px solid #eeeeee;
    border-bottom: 3px solid #eeeeee;
    padding: 10px;
  }
</style>
