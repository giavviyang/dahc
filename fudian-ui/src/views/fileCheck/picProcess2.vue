<template>
  <div class="pic">
    <p class="filePicTitle">
      电子文件信息
    </p>
    <div class="filePicContent">
      <div class="btnTop">
        <div >
          <el-popover
            placement="top-start"
            title="缩小"
            width="150"
            trigger="hover"
            content="快捷键">
            <el-button slot="reference" type="text" icon="el-icon-zoom-out" size="mini"/>
          </el-popover>
          <el-popover
            placement="top-start"
            title="放大"
            width="150"
            trigger="hover"
            content="快捷键">
            <el-button slot="reference" type="text" icon="el-icon-zoom-in" size="mini"/>
          </el-popover>
          <el-popover
            placement="top-start"
            title="上一页"
            width="150"
            trigger="hover"
            content="快捷键">
            <el-button slot="reference" type="text" icon="el-icon-arrow-left" size="mini"/>
          </el-popover>
          <el-popover
            placement="top-start"
            title="下一页"
            width="150"
            trigger="hover"
            content="快捷键">
            <el-button slot="reference" type="text" icon="el-icon-arrow-right" size="mini"/>
          </el-popover>
          <el-popover
            placement="top-start"
            title="上一件"
            width="150"
            trigger="hover"
            content="快捷键">
            <el-button slot="reference" type="text" icon="el-icon-d-arrow-left" size="mini"/>
          </el-popover>
          <el-popover
            placement="top-start"
            title="下一件"
            width="150"
            trigger="hover"
            content="快捷键">
            <el-button slot="reference" type="text" icon="el-icon-d-arrow-right" size="mini"/>
          </el-popover>
        </div>
        <div>
          <el-button type="text" icon="el-icon-edit-outline" size="mini" style="color: #e4c849"/>
        </div>
      </div>
      <div class="btnCenter">
        <el-carousel  height="100%" trigger="click" :autoplay="false" indicator-position="none" class="picBox"  arrow="always">
          <el-carousel-item v-for="item in 4" :key="item">
            <!--              <h3>{{ item }}</h3>-->
            <img :src="imgArr[0].imgUrl" >
            <h3>{{ imgArr[0].imgUrl }}</h3>
          </el-carousel-item>
        </el-carousel>
        <div class="picUpdate">
          <div style="height: 50%;width: 100%;">
            <el-popover
              placement="top-start"
              title="上移"
              width="150"
              trigger="hover"
              content="介绍">
              <el-button slot="reference" type="text" icon="el-icon-top" size="mini"/>
            </el-popover>
            <el-popover
              placement="top-start"
              title="下移"
              width="150"
              trigger="hover"
              content="介绍">
              <el-button slot="reference" type="text" icon="el-icon-bottom" size="mini"/>
            </el-popover>
            <el-popover
              placement="top-start"
              title="纠偏"
              width="150"
              trigger="hover"
              content="介绍">
              <el-button slot="reference" type="text" icon="el-icon-top" size="mini"/>
            </el-popover>
            <el-popover
              placement="top-start"
              title="替换"
              width="150"
              trigger="hover"
              content="介绍">
              <el-button slot="reference" type="text" icon="el-icon-top" size="mini"/>
            </el-popover>
            <el-popover
              placement="top-start"
              title="加页"
              width="150"
              trigger="hover"
              content="介绍">
              <el-button slot="reference" type="text" icon="el-icon-bottom" size="mini"/>
            </el-popover>
            <el-popover
              placement="top-start"
              title="减页"
              width="150"
              trigger="hover"
              content="介绍">
              <el-button slot="reference" type="text" icon="el-icon-bottom" size="mini"/>
            </el-popover>
          </div>
          <div style="height: 50%;width: 100%;">
            <el-button type="text" size="mini" style="font-size: 10px">左偏10度</el-button>
            <el-button type="text" size="mini" style="font-size: 10px">左偏10度</el-button>
            <el-button type="text" size="mini" style="font-size: 10px">左偏10度</el-button>
          </div>
        </div>
      </div>
      <div class="btnBottom" >
        <p>当前件数： <span>1</span></p>
        <p>总件数： <span>3</span></p>
        <p>当前页数： <span>100</span></p>
        <p>总页数：<span>300</span></p>
        <p>分辨率： <span>380 X 400</span></p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  // name: "picProcess",
  data(){
    return{
      imgArr:[
        {
          imgUrl:require('../../assets/images/cat1.jpg')
        }
      ]
    }
  }
}
</script>

<style lang="scss" scoped>
.pic {
  width: 100%;
  height: 100%;
  .filePicTitle {
    font-weight: 600;
    font-size: 13px;
    color: #606266;
  }

  .filePicContent {
    width: 100%;
    height: calc(100% - 20px);
    border-radius: 10px;
    border: 1px solid #eeeeee;
    box-shadow: rgba(0, 0, 0, 0.1) 0px 4px 12px;

    .btnTop {
      padding: 5px;
      display: inline-flex;
      width: 100%;
      height: 40px;
      justify-content: space-between;

      &>div:first-of-type{
        .el-button:first-of-type{
          margin-left:5px;
        }
      }

    }
    .btnCenter{
      height: calc(100% - 70px);
      width: 100%;
      display: inline-flex;

      margin-left: 5px;
      .picBox{
        width:calc(100% - 100px);
        height: 100%;
      }
      .picUpdate{
        width: 90px;
        //background-color: #e1f3fb;
        height: 100%;
      }
    }
    .btnBottom{
      height: 50px;
      display: flex;
      justify-content: space-between;
      font-size: 12px;
      padding: 0 10px;
      span{
        color: #00afff;
      }
    }
  }
}
.el-carousel__item h3 {
  color: #475669;
  font-size: 18px;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
.el-button {
  font-size: 20px;
  margin: 0 10px;
}
</style>
