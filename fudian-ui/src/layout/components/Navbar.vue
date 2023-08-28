<template>
  <div>
    <div class="navbar">
      <hamburger id="hamburger-container" :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

      <breadcrumb id="breadcrumb-container" class="breadcrumb-container" v-if="!topNav"/>
      <top-nav id="topmenu-container" class="topmenu-container" v-if="topNav"/>

      <div class="right-menu">
        <template v-if="device!=='mobile'">
          <!--        <search id="header-search" class="right-menu-item" />-->

          <!--        <el-tooltip content="源码地址" effect="dark" placement="bottom">-->
          <!--          <ruo-yi-git id="dahc-git" class="right-menu-item hover-effect" />-->
          <!--        </el-tooltip>-->

          <!--        <el-tooltip content="文档地址" effect="dark" placement="bottom">-->
          <!--          <ruo-yi-doc id="dahc-doc" class="right-menu-item hover-effect" />-->
          <!--        </el-tooltip>-->

          <screenfull id="screenfull" class="right-menu-item hover-effect" />

          <!--        <el-tooltip content="布局大小" effect="dark" placement="bottom">-->
          <!--          <size-select id="size-select" class="right-menu-item hover-effect" />-->
          <!--        </el-tooltip>-->

        </template>
        <!--      <span style="font-size:16px;color: #5a5e66;">{{ loginUserName }}</span>-->
        <!--      <el-button  style="font-weight:bold;font-size:18px;color: #5a5e66;"-->
        <!--                 type="text" size="mini">个人中心-->
        <!--      </el-button>-->
        <!--      <el-button  style="font-weight:bold;font-size:18px;color: #5a5e66;"-->
        <!--                 type="text" size="mini">退出-->
        <!--      </el-button>-->

        <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
          <div class="avatar-wrapper">
            <!--          <img :src="avatar" class="user-avatar">-->
            <span style="margin-right: 5px;font-weight:bold;">{{ loginUserName }}</span>
            <!--          <i class="el-icon-caret-bottom" />-->
          </div>
          <el-dropdown-menu slot="dropdown">
            <router-link to="/user/profile">
              <el-dropdown-item>个人中心</el-dropdown-item>
            </router-link>
            <!--          <el-dropdown-item @click.native="setting = true">-->
            <!--            <span>布局设置</span>-->
            <!--          </el-dropdown-item>-->

          </el-dropdown-menu>
        </el-dropdown>
        <!--      <el-dropdown-item divided @click.native="logout">-->
        <!--        <span>退出登录</span>-->
        <!--      </el-dropdown-item>-->
        <el-tooltip class="item" effect="dark" content="退出系统" placement="bottom">
          <el-button  style="font-weight:bold;font-size:18px;color: #5a5e66;position:relative;top:-10px"
                      type="text" size="mini" @click.native="logout" icon="el-icon-switch-button">
          </el-button>
        </el-tooltip>
      </div>
    </div>
    <div class="breadcrumbDiv">
      <breadcrumb id="breadcrumb-container" class="breadcrumb-container" />
    </div>
  </div>

</template>

<script>
  import { mapGetters } from 'vuex'
  import Breadcrumb from '@/components/Breadcrumb'
  import TopNav from '@/components/TopNav'
  import Hamburger from '@/components/Hamburger'
  import Screenfull from '@/components/Screenfull'
  import SizeSelect from '@/components/SizeSelect'
  import Search from '@/components/HeaderSearch'
  import {getUserProfile} from "@/api/system/user";
  // import RuoYiGit from '@/components/RuoYi/Git'
  // import RuoYiDoc from '@/components/RuoYi/Doc'

  export default {
    components: {
      Breadcrumb,
      TopNav,
      Hamburger,
      Screenfull,
      SizeSelect,
      Search,
      // RuoYiGit,
      // RuoYiDoc
    },
    computed: {
      ...mapGetters([
        'sidebar',
        'avatar',
        'device'
      ]),
      setting: {
        get() {
          return this.$store.state.settings.showSettings
        },
        set(val) {
          this.$store.dispatch('settings/changeSetting', {
            key: 'showSettings',
            value: val
          })
        }
      },
      topNav: {
        get() {
          return this.$store.state.settings.topNav
        }
      }
    },
    data() {
      return {
        loginUserName: '',
      };
    },
    created() {
      this.getUser();
    },
    methods: {
      getUser() {
        getUserProfile().then(response => {
          this.loginUserName = response.data.nickName
        });
      },
      toggleSideBar() {
        this.$store.dispatch('app/toggleSideBar')
      },
      async logout() {
        this.$confirm('确定注销并退出系统吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$store.dispatch('LogOut').then(() => {
            if (process.env.NODE_ENV === 'production') {
              location.href = '/dahc/';
            } else {
              location.href = '/index';
            }
          })
        }).catch(() => {});
      }
    }
  }
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color:transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .topmenu-container {
    position: absolute;
    left: 50px;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .right-menu {
    float: right;
    margin-right: 20px;
    height: 100%;
    line-height: 50px;
    //display: inline-flex;
    //justify-content: space-between;
    //align-items: center;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 3px 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      //margin-right: 5px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
.breadcrumbDiv {
  padding-left: 10px;
  width: 100%;
  height: 30px;
  background-color: #FFFFFF;
  box-shadow: 0 1px 4px rgba(0, 21, 41, .08);
  border-bottom: 1px solid rgba(0, 21, 41, .08);
  border-top: 1px solid rgba(0, 21, 41, .08);
}
</style>
