<template>
  <div class="drag-outer"
       ref="dragWrap"
       :style="outerOptions"
       @mouseenter="mouseenter"
       @mouseleave="mouseleave"
       @mousemove="dragMousemove">
    <div class="drag-inner"
         ref="dragElement"
         :style="innerOptions"
         @mousedown="dragMousedown"
         @mouseup.stop="mouseup">
      <slot></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ElementDrag',
  props: {
    outerOptions: {
      type: Object,
      default() {
        return {
          background: 'transparent'
        }
      }
    },
    innerOptions: {
      type: Object,
      default() {
        return {
          background: 'transparent',
        }
      }
    },
    scaleZoom: {
      type: Object,
      default() {
        return {
          max: 5,
          min: 0.2
        }
      }
    },
    isHover:{
      type:Boolean,
      default:false,
    },
    isMousedown:{
      type:Boolean,
      default:false,
    },

    scale: {
      type: Number,
      default: 1,
    },
    translate: {
      type: Object,
      default() {
        return {
          x: 0, y: 0
        }
      }
    },
    moveStart: {
      type: Object,
      default() {
        return {
          x: 0, y: 0
        }
      }
    }
  },
  methods: {
    handleScroll() {
      this.$emit('handleScroll')
    },
    mouseleave(){
      this.$emit('mouseleave')
    },
    mouseenter(){
      this.$emit('mouseenter')
    },
    mouseup(){
      this.$emit('mouseup')
    },
    dragMousedown() {
      this.$emit('dragMousedown')
    },
    dragMousemove() {
      this.$emit('dragMousemove')
    }
  },
}
</script>

<style lang="scss" scoped>
.drag-outer {
  width: 100%;
  height: 100%;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;

  .drag-inner {
    transform-origin: center center;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: move;
    user-select: none;
    height: 95%;
    //background-color: transparent;
    > * {
      -webkit-user-drag: none;
      user-drag: none;
    }
  }
}
</style>
