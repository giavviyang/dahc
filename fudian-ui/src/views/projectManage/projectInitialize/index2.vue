<template>
  <div class="app-container-padding">
    <el-tabs type="border-card" >
      <el-tab-pane label="未开始">
        <HaveNoStart></HaveNoStart>
      </el-tab-pane>
      <el-tab-pane label="核查中">
        <UnderVerification></UnderVerification>
      </el-tab-pane>
      <el-tab-pane label="已完成">
        <completed></completed>
      </el-tab-pane>
    </el-tabs>
  </div>

</template>

<script>
import HaveNoStart from "@/views/projectManage/projectInitialize/haveNoStart.vue";
import UnderVerification from "@/views/projectManage/projectInitialize/underVerification.vue";
import Completed from "@/views/projectManage/projectInitialize/completed.vue";
export default {
  name: "index",
  components:{Completed, UnderVerification, HaveNoStart}
}
</script>

<style scoped>

</style>
