<template>
  <div class="checkTop">
    <el-form :model="queryParams" ref="queryForm" size="mini" :inline="true" class="iptAndBtn"
             style="position: relative" @submit.native.prevent>
      <div style="white-space: nowrap;margin-right: 90px;">
        <el-form-item label="案卷档号" prop="caseNum" class="caseNum">
          <el-input
            v-model="queryParams.caseNum"
            placeholder="请输入案卷档号"
            :disabled="showInputCaseNum"
            @keyup.enter.native="enterQuery"
            clearable/>
        </el-form-item>
        <el-form-item v-show="!routeName.includes('ElectronicTemplate')&&!impeach" >
          <el-button type="primary" icon="el-icon-edit-outline" size="mini" @click="handleReceivedPhy">领取档案
          </el-button>
        </el-form-item>
        <el-form-item v-show="routeName.includes('Electronic')" >
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery" v-if="!impeach">搜索
          </el-button>
        </el-form-item>
        <el-form-item
          v-show="routeName.includes('Electronic')&& queryParams.activeName!=='alreadyReceived'" >
          <el-dropdown @command="handlePool">
            <el-button type="primary" size="mini">
              <i class="el-icon-circle-check" style="display: inline-block;margin-right: 5px;"></i>领取电子档案<i
              class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="1">领取1卷</el-dropdown-item>
              <el-dropdown-item command="5">领取5卷</el-dropdown-item>
              <el-dropdown-item command="10">领取10卷</el-dropdown-item>
              <el-dropdown-item command="20">领取20卷</el-dropdown-item>
              <el-dropdown-item command="30">领取50卷</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-form-item>
<!--        <el-form-item v-show="routeName.includes('Electronic')&& queryParams.activeName!=='alreadyReceived'" >
          <el-dropdown>
            <el-button type="primary" size="mini">
              <i class="el-icon-upload" style="display: inline-block;margin-right: 5px;"></i>
              导入数据<i class="el-icon-arrow-down el-icon&#45;&#45;right"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>全部导入</el-dropdown-item>
              <el-dropdown-item>导入案卷级条目</el-dropdown-item>
              <el-dropdown-item>导入文件级条目</el-dropdown-item>
              <el-dropdown-item>导入电子文件</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-form-item>-->
      </div>
      <div style="right:5px;position: absolute">
        <el-button type="primary" icon="el-icon-refresh-left" size="mini" @click="handleBack">返回</el-button>
      </div>
    </el-form>
    <div class="checkTableDes">
      <p>核查范围：<span>{{ checkRange }}</span></p>
      <p>工序名称：<span>{{ processName }}</span></p>
      <p>工序描述：<span>{{ processDes }}</span></p>
      <p v-if="notVerifiedCountShow">剩余未核查数量：<span>{{ notVerifiedCount }}</span></p>
    </div>
  </div>
</template>

<script>
export default {
  name: "checkTop",
  data() {
    return {
      routeName: '',
    }
  },
  props: {
    // 查询
    queryParams: {
      type: Object,
      default: () => {
        return {
          caseNum: '',
        }
      }
    },
    // 核查范围
    checkRange: {
      type: String,
      default: '',
    },

    /*是否展示*/
    notVerifiedCountShow: {
      type: Boolean,
      default: true,
    },
    // 存疑页面
    impeach: {
      type: Boolean,
      default: false,
    },
    // 工序名称
    processName: {
      type: String,
      default: '',
    },
    // 工序描述
    processDes: {
      type: String,
      default: '',
    },
    // 工序描述
    notVerifiedCount: {
      type: Number,
      default: 0,
    },
    // 文本框是否可选择
    showInputCaseNum: {
      type: Boolean,
      default: false,
    },
  },
  mounted() {
    console.log(this.$route)
    this.routeName = this.$route.name;
  },
  methods: {
    /**
     * 领取档案
     */
    handleReceivedPhy(val) {
      this.$emit('handleReceivedPhy', val);
    },

    enterQuery(val) {
      this.$emit("enterQuery");
    },
    /*领取电子档案*/
    handlePool(command) {
      this.$emit('handlePool', command);
    },
    handleQuery({caseNum}) {
      this.$emit('handleQuery', {caseNum});
      // this.$refs.checkTableRef.toggleRowSelection(row, column)
    },
    handleBack(val) {
      this.$emit('handleBack', val);
    },
  }
}
</script>

<style scoped lang="scss">
::v-deep .iptAndBtn {
  .el-form-item{
    .el-form-item__content{
      width: auto;
    }
  }
  .caseNum {
    width: 290px;

    .el-form-item__content {
      max-width: 220px;
      width: 220px;
    }
  }
}

.checkTableDes {
  font-weight: 600;
  font-size: 13px;
  //color: #606266;
  color: #00afff;
  display: flex;
  justify-content: space-between;
  //margin-bottom: 5px;
  //padding: 5px 0 0 10px;
  padding: 0 5px;
  margin: 5px 0;

  p {
    display: inline-block;

  }
}
</style>
