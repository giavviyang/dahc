<template>
  <section class="app-main">
<!--    鼠标向左向右滑动，路由跳转   -->
    <transition name="fade-transform" mode="out-in">
      <keep-alive :include="cachedViews">
<!--      <keep-alive>-->
        <router-view v-if="!$route.meta.link" :key="key" />
      </keep-alive>
    </transition>
    <iframe-toggle />
  </section>
</template>

<script>
import iframeToggle from "./IframeToggle/index"

export default {
  name: 'AppMain',
  components: { iframeToggle },
  computed: {
    cachedViews() {
      console.log(this.$store.state.tagsView,"页面跳转")
      return this.$store.state.tagsView.cachedViews
      // return ['projectInitialize','home']
    },
    key() {
      return this.$route.path
    }
  }
}
</script>

<!--<style lang="scss" scoped>
.app-main {
  /* 50= navbar  50  */
  min-height: calc(100vh - 50px);
  width: 100%;
  position: relative;
  overflow: hidden;
}

.fixed-header + .app-main {
  padding-top: 50px;
}

.hasTagsView {
  .app-main {
    /* 84 = navbar + tags-view = 50 + 34 */
    min-height: calc(100vh - 84px);
  }

  .fixed-header + .app-main {
    padding-top: 84px;
  }
}
</style>-->
<style lang="scss" scoped>
.app-main {
  height: calc(100vh - 85px);
  width: 100%;
  position: relative;
  overflow: hidden;
  background-color: #FFFFFF;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);
  min-width: 1100px;
  min-height: 600px;
}
</style>

<style lang="scss">
.el-popup-parent--hidden {
  .fixed-header {
    padding-right: 17px;
  }
}
</style>

<style lang="scss">
// fix css style bug in open el-dialog
.el-popup-parent--hidden {
  .fixed-header {
    padding-right: 17px;
  }
}
</style>
