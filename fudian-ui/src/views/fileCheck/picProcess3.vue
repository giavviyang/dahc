<template>
  <div class="pic">
    <p class="filePicTitle">
      电子文件信息
    </p>
    <div class="filePicContent">
      <div class="btnTop">
        <div>
          <el-tooltip class="item" effect="dark" content="缩小" placement="top-start">
            <el-button type="text" icon="el-icon-zoom-out" size="mini" @click="shrink"
                       :disabled="imgList.length==0"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="放大" placement="top-start">
            <el-button type="text" icon="el-icon-zoom-in" size="mini" @click="magnify"
                       :disabled="imgList.length==0"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="旋转" placement="top-start">
            <el-button type="text" icon="el-icon-refresh-right" size="mini" @click="rotate"
                       :disabled="imgList.length==0"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="还原" placement="top-start">
            <el-button type="text" icon="el-icon-refresh" size="mini" @click="restore"
                       :disabled="imgList.length==0"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="上一页" placement="top-start">
            <el-button type="text" icon="el-icon-arrow-left" size="mini" @click="previousPage(currentPage)"
                       :disabled="currentPage===0"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="下一页" placement="top-start">
            <el-button type="text" icon="el-icon-arrow-right" size="mini" @click="nextPage(currentPage)"
                       :disabled="currentPage == imgList.length-1 || theCurrentNumberOfPages == 0 "></el-button>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="上一件" placement="top-start">
            <el-button type="text" icon="el-icon-d-arrow-left" size="mini"
                       @click="previousFile(currentFile)"
                       :disabled="currentFile===0"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="下一件" placement="top-start">
            <el-button type="text" icon="el-icon-d-arrow-right" size="mini" @click="nextFile(currentFile)"
                       :disabled="isCurrentPageItem"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="上移" placement="top-start">
            <el-button type="text" icon="el-icon-top" size="mini" @click="imgMoveUp"
                       :disabled="imgList.length==0 || currentPage==0">
            </el-button>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="下移" placement="top-start">
            <el-button type="text" icon="el-icon-bottom" size="mini" @click="imgMoveDown"
                       :disabled="imgList.length==0 || currentPage== this.imgList.length-1"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="替换" placement="top-start">
            <el-button type="text" icon="el-icon-upload" size="mini" @click="replacement"
                       :disabled="imgList.length==0"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="下载" placement="top-start">
            <el-button type="text" icon="el-icon-download" size="mini" @click="downloadTheImage"
                       :disabled="imgList.length==0"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="加页" placement="top-start">
            <el-button type="text" icon="el-icon-plus" size="mini" @click="plusPage"
                       :disabled="fileData.length ==0"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="减页" placement="top-start">
            <el-button type="text" icon="el-icon-minus" size="mini" @click="pagingDown"
                       :disabled="imgList.length==0"/>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="重置页号" placement="top-start">
            <el-button type="text" icon="el-icon-edit-outline" size="mini" @click="modifyThePageNumber"
                       :disabled="imgList.length==0"/>
          </el-tooltip>
        </div>
        <div>
          <el-tooltip class="item" effect="dark" content="编辑核查项" placement="top-start">
            <el-button type="text" icon="el-icon-edit-outline" size="mini" style="color: #e4c849"
                       @click="handleCheck(imgDes)"/>
          </el-tooltip>
        </div>
      </div>
      <div class="btnContent">
        <div class="picArr">
          <el-carousel trigger="click" :autoplay="false" indicator-position="none" arrow="always"
                       @change="handleChange" id="look-image" ref="carousel" :loop="false">
            <el-carousel-item v-for="(item,index) in imgList" :key="item.id">
              <ElementDrag :translate="translate" :scale="scale" :isHover="isHover" @handleScroll="handleScroll"
                           ref="ElementDrag" :isMousedown="isMousedown" :moveStart="moveStart"
                           @dragMousedown="dragMousedown" @dragMousemove="dragMousemove" @mouseenter="mouseenter"
                           @mouseleave="mouseleave" @mouseup="mouseup">
                <img :src="item.imgUrl" :style="styleObj">
              </ElementDrag>
            </el-carousel-item>
          </el-carousel>
          <div class="picDes">
            <p>当前件数： <span>{{ imgDes.currentFile }}</span></p>
            <p>文件总数： <span>{{ imgDes.totalFile }}</span></p>
            <p>当前页号： <span>{{ imgDes.currentPage }}</span></p>
            <!--            <p>当前页数： <span>{{ theCurrentNumberOfPages }}</span></p>-->
            <p>文件页数：<span>{{ imgDes.totalPage }}</span></p>
            <p>分辨率： <span>{{ imgDes.resolution }}</span></p>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import ElementDrag from "@/views/fileCheck/ElementDrag.vue";

export default {
  name: "picProcess",
  components: {ElementDrag},
  props: {
    imgList: {
      type: Array,
      default: () => []
    },
    fileData: {
      type: Array,
      default: () => []
    },
    isCurrentPage: {
      type: Boolean,
      default: false
    },
    isCurrentPageItem: {
      type: Boolean,
      default: false
    },
    imgDes: {
      type: Object,
      default: () => {
      }
    },
    currentPage: {
      type: Number,
      default: 0
    },
    currentFile: {
      type: Number,
      default: 0
    },
    /*当前页数*/
    theCurrentNumberOfPages: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      multiples: 1,       // 放大或者缩小
      deg: 0,             // 旋转的角度
      styleObj: null,       // 拖拽时修改图片的样式
      endX: 0,
      endY: 0,
      isMousedown: false,
      moveStart: {},
      isHover: false,
      scale: 1,
      translate: {
        x: 0, y: 0
      },
      scaleZoom: {
        max: 5,
        min: 0.2
      }
    }
  },
  mounted() {
    window.addEventListener('mousewheel', this.handleScroll, false)
  },
  destroyed() {
    window.removeEventListener('mousewheel', this.handleScroll, false)
  },
  methods: {
    handleScroll(e) {
      let dragElement = this.$refs.ElementDrag[0].$refs.dragElement
      console.log(dragElement)
      if (this.isHover) {
        let speed = e.wheelDelta / 120
        if (e.wheelDelta > 0 && this.scale < this.scaleZoom.max) {
          this.scale += 0.04 * speed
          dragElement.style.transform = `scale(${this.scale}) translate(${this.translate.x}px, ${this.translate.y}px)`
        } else if (e.wheelDelta < 0 && this.scale > this.scaleZoom.min) {
          this.scale += 0.04 * speed
          dragElement.style.transform = `scale(${this.scale}) translate(${this.translate.x}px, ${this.translate.y}px)`
        }
      }
    },
    mouseenter() {
      this.isHover = true;
    },
    mouseleave() {
      this.isHover = false;
      this.isMousedown = false;
    },
    mouseup() {
      this.isMousedown = false;
    },
    dragMousemove() {
      let dragElement = this.$refs.ElementDrag[0].$refs.dragElement
      if (this.isMousedown) {
        this.translate.x += (event.clientX - this.moveStart.x) / this.scale
        this.translate.y += (event.clientY - this.moveStart.y) / this.scale
        dragElement.style.transform = `scale(${this.scale}) translate(${this.translate.x}px, ${this.translate.y}px)`
        this.moveStart.x = event.clientX
        this.moveStart.y = event.clientY
      }
    },
    dragMousedown() {
      this.moveStart.x = event.clientX
      this.moveStart.y = event.clientY
      this.isMousedown = true
    },
    // 缩小
    shrink() {
      if (this.multiples <= 0.25) {
        this.$message({
          message: '当前图片不能继续缩小',
          type: 'warning'
        });
      } else {
        this.multiples -= 0.25;
        this.styleObj = `transform: scale(${this.multiples}) rotateZ(${this.deg}deg);left:${this.endX}px;top:${this.endY}px`;
      }
    },
    // 放大
    magnify() {
      if (this.multiples >= 10) {
        this.$message({
          message: '当前图片不能继续放大',
          type: 'warning'
        });
      } else {
        this.multiples += 0.25;
        this.styleObj = `transform: scale(${this.multiples}) rotateZ(${this.deg}deg);left:${this.endX}px;top:${this.endY}px`;
      }
    },
    /**
     * 还原
     */
    restore() {
      this.multiples = 1;
      this.deg = 0;
      this.scale = 1;
      this.translate = {x: 0, y: 0};
      this.styleObj = `transform: scale(${this.multiples}) rotateZ(${this.deg}deg);left:${this.endX}px;top:${this.endY}px`;
      let dragElement = this.$refs.ElementDrag[0].$refs.dragElement
      dragElement.style.transform = `scale(${this.scale}) translate(${this.translate.x}px, ${this.translate.y}px)`
    },

    // 旋转
    rotate() {
      this.deg += 90;
      if (this.deg >= 360) {
        this.deg = 0
      }

      this.styleObj = `transform: scale(${this.multiples}) rotateZ(${this.deg}deg);left:${this.endX}px;top:${this.endY}px`;
    },
    /**
     * 上一页
     */
    previousPage(val) {
      console.log(val)
      this.$refs.carousel.prev();
      this.$emit('previousPage', val);
    },

    /**
     * 下一页
     */
    nextPage(val) {
      this.$refs.carousel.next();
      this.$emit('nextPage', val);
    },
    /**
     * 上一件
     */
    previousFile(val) {
      this.$emit('previousFile', val);
    },
    /**
     * 下一件
     */
    nextFile(val) {
      this.$emit('nextFile', val);
    },

    /*上移*/
    imgMoveUp(val) {
      this.$emit('imgMoveUp', val);
    },
    /*下移*/
    imgMoveDown(val) {
      this.$emit('imgMoveDown', val);
    },
    /*替换*/
    replacement(val) {
      this.$emit('replacement', val);
    },
    /*下载*/
    downloadTheImage(val) {
      this.$emit('downloadTheImage', val);
    },
    /*加页*/
    plusPage(val) {
      this.$emit('plusPage', val);
    },
    /*减页*/
    pagingDown() {
      this.$emit('pagingDown');
    },
    /*减页*/
    modifyThePageNumber() {
      this.$emit('modifyThePageNumber');
    },
    /**
     * 查看核查项
     */
    handleCheck(val) {
      this.$emit('handleCheck', val);
    },
    /**
     * 轮播图切换
     */
    handleChange(val) {
      // this.currentPage = val;
      // val=this.currentPage;

      this.$emit('handleChange', val);
    },
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

      & > div:first-of-type {
        display: flex;
        flex-wrap: nowrap;
        justify-content: flex-start;

        .el-button {
          margin-right: 5px;
        }
      }

    }

    ::v-deep .btnContent {
      height: calc(100% - 40px);
      width: 100%;

      .picArr {
        height: 100%;
        //width: calc(100% - 100px);
        width: 100%;

        .el-carousel {
          height: calc(100% - 40px);
          width: 100%;

          .el-carousel__container {
            height: 100%;

            .el-carousel__arrow {
              font-size: 24px;
            }

            .el-carousel__item {
              display: inline-flex;
              justify-content: center;

              .drag-inner {
                height: 100%;

                img {
                  width: 100%;
                  height: 100%;

                }
              }

            }
          }
        }

      }

      .picDes {
        height: 40px;
        line-height: 40px;
        width: 100%;
        display: flex;
        justify-content: space-between;
        font-size: 12px;
        padding-left: 10px;

        p {
          display: inline-block;
          width: 110px;
        }

        p:last-of-type {
          width: 160px;
        }

        span {
          color: #00afff;
        }
      }

    }

    .btnBottom {
      height: 50px;
      display: flex;
      justify-content: space-between;
      font-size: 12px;
      padding: 0 10px;

      span {
        color: #00afff;
      }
    }
  }
}

.el-button {
  font-size: 20px;
  margin: 0 10px;
}
</style>
