<template>
  <div style="width: 100%;height: 100%;position: relative">
    <el-carousel trigger="click" :autoplay="false" indicator-position="none" arrow="always"
                 @change="handleChange" id="look-image" ref="carousel" :loop="false">
      <el-carousel-item v-for="item in imgList" :key="item.id">
        <ElementDrag :translate="translate" :scale="scale" :isHover="isHover" @handleScroll="handleScroll"
                     ref="ElementDrag" :isMousedown="isMousedown" :moveStart="moveStart"
                     @dragMousedown="dragMousedown" @dragMousemove="dragMousemove" @mouseenter="mouseenter"
                     @mouseleave="mouseleave" @mouseup="mouseup">
          <img :src="item.imgUrl" :style="styleObj" >
        </ElementDrag>
      </el-carousel-item>
    </el-carousel>
    <div class="imgIcon">
      <el-button type="text" icon="el-icon-zoom-out" size="mini" @click="shrink" :disabled="imgList.length==0"/>
      <el-button type="text" icon="el-icon-zoom-in" size="mini" @click="magnify" :disabled="imgList.length==0"/>
      <el-button type="text" icon="el-icon-refresh-right" size="mini" @click="rotate" :disabled="imgList.length==0"/>
      <el-button type="text" icon="el-icon-refresh" size="mini" @click="restore" :disabled="imgList.length==0"/>
      <!--  </el-tooltip>-->
      <!--  <el-tooltip class="item" effect="dark" content="上一页" placement="top-start">-->
      <!--    <el-button type="text" icon="el-icon-arrow-left" size="mini" @click="previousPage(currentPage)"-->
      <!--               :disabled="currentPage===0"/>-->
      <!--  </el-tooltip>-->
      <!--  <el-tooltip class="item" effect="dark" content="下一页" placement="top-start">-->
      <!--    <el-button type="text" icon="el-icon-arrow-right" size="mini" @click="nextPage(currentPage)"-->
      <!--               :disabled="currentPage == imgList.length-1 || theCurrentNumberOfPages == 0 "></el-button>-->
      <!--  </el-tooltip>-->
      <el-button type="text" icon="el-icon-download" size="mini" @click="downloadTheImage"
                 :disabled="imgList.length==0"/>
    </div>
  </div>
</template>

<script>
import ElementDrag from "@/views/fileCheck/ElementDrag.vue";

export default {
  name: "digitalPhoto",
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
      isDrag: false,      // 是否开始拖拽
      startX: 0,          // 鼠标的点击X轴
      startY: 0,          // 鼠标的点击Y轴
      moveX: 0,           // 鼠标移动的X轴
      moveY: 0,            // 鼠标移动的Y轴
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
    /**
     * 轮播图切换
     */
    handleChange(val) {
      this.$emit('carouselToggle', val);
    },
    // 缩小
    shrink() {
      if (this.multiples <= 0.25) {
        // console.log('multiples',this.multiples)
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
      // console.log(this.currentPage)
      this.$emit('previousPage', val);
    },

    /**
     * 下一页
     */
    nextPage(val) {
      this.$refs.carousel.next();
      this.$emit('nextPage', val);
    },
    /*下载*/
    downloadTheImage(val) {
      this.$emit('downloadTheImage', val);
    },
  },
}
</script>

<style lang="scss" scoped>
::v-deep .el-carousel {
  height: 100%;


  .el-carousel__container {
    height: 100%;
    width: calc(100% - 100px);
    margin: 0 auto;
    img {
      width: calc(100% - 60px);
      height: 100%;

    }
    .el-carousel__arrow {
      width: 40px;
      height: 40px;

      i {
        font-size: 24px;
      }
    }
    .el-carousel__arrow--left{
      left:-40px
    }
    .el-carousel__arrow--right{
      right:-40px
    }

  }
}

.imgIcon {
  z-index: 2000;
  height: 50px;
  width: 40%;
  position: absolute;
  border-radius: 25px;
  bottom: 20px;
  background-color: rgba(0, 0, 0, 0.2);
  left: 50%;
  padding: 0 20px;
  transform: translateX(-50%);
  display: flex;
  justify-content: space-between;

  .el-button {
    font-size: 20px;
    color: #fff;
  }
}
</style>
