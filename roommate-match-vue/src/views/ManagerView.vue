<template>
  <div>
    <el-container>
      <el-aside :width="asideWidth">
        <div
          style="height: 60px; line-height: 60px; font-size: 20px; display: flex; align-items: center; justify-content: center">
          <img src="@/assets/head.png" style="width: 50px;" alt="">
          <span class="logo-title" v-show="!isCollapse">ECUT.HARD</span>
        </div>
        <el-menu router :collapse="isCollapse" :collapse-transition="false" background-color="#001529"
          active-text-color="#fff" text-color="rgba(255, 255, 255, 0.65)" :default-active="$route.path"
          style="border: none">
          <el-menu-item index="/home">
            <i class="el-icon-s-home"></i>
            <span slot="title">系统首页</span>
          </el-menu-item>
          <el-submenu index="2">
            <template slot="title"><i class="el-icon-menu"></i><span>信息管理</span></template>
            <el-menu-item index="/user">用户管理</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-container>
        <el-header
          style="height: 60px; line-height: 60px; display: flex; align-items: center; box-shadow: 2px 0 6px rgba(0, 21, 41, .35);">
          <i :class="collapseIcon" @click="handleCollapse" style="font-size: 26px"></i>
          <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-left: 20px">
            <el-breadcrumb-item :to="{ path: '/' }">主页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: $route.path }">{{ $route.meta.name }}</el-breadcrumb-item>
          </el-breadcrumb>
          <!--           <el-breadcrumb separator="/" style="margin-left: 20px">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: $route.path }">{{ $route.meta.name }}</el-breadcrumb-item>
          </el-breadcrumb> -->

<!--           <div style="flex: auto;">
            <el-button @click="show3 = !show3">Click Me</el-button>

            <div style="margin-top: 20px; height: 200px;">
              <el-collapse-transition>
                <div v-show="show3">
                  <div class="transition-box">el-collapse-transition</div>
                  <div class="transition-box">el-collapse-transition</div>
                </div>
              </el-collapse-transition>
            </div>
          </div> -->

          <div style="flex: 5; display: flex; justify-content: flex-end; align-items: center">
            <el-dropdown placement="bottom">
              <div style="display: flex; align-items: center; cursor: pointer">
                <span>和开发者交流</span>
              </div>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>
                  <img src="@/assets/writer.jpg" style="width: 500px;">
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>




          <div style="flex: 1; display: flex; justify-content: flex-end; align-items: center">
            <i class="el-icon-quanping" @click="handleFullScreen" style="font-size: 25px"></i>
            <el-dropdown placement="bottom">
              <div style="display: flex; align-items: center; cursor: pointer">
                <img src="@/assets/logo1.png" alt="" style="width: 40px; height: 40px; margin: 0 10px">
                <span>ECUTer</span>
              </div>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="$router.push('/person')">个人信息</el-dropdown-item>
                <el-dropdown-item @click.native="$router.push('/password')">修改密码</el-dropdown-item>
                <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </el-header>

        <!-- 主体区域 -->
        <el-main>
          <router-view />
        </el-main>

      </el-container>

    </el-container>

  </div>
</template>

<script>
//import axios from 'axios'
//import request from "@/utils/request"

export default {
  name: 'HomeView',
  data() {
    return {
      isCollapse: false,
      asideWidth: '200px',
      collapseIcon: 'el-icon-s-fold',
      //students: []
    }
  },
  mounted() {
    /* axios.get('http://localhost/user').then(res => {
      console.log(res.data)
      this.students = res.data.data
    }) */

    /*     request.get("/user").then(res => {
          //console.log(res);
          this.students = res.data;
        }) */
  },
  methods: {
    logout() {
      localStorage.removeItem("ecut-user")//清除当前的token和用户数据
      this.$router.push('/login')
    },
    handleFullScreen() {
      document.documentElement.requestFullscreen()
    },
    handleCollapse() {
      this.isCollapse = !this.isCollapse
      this.asideWidth = this.isCollapse ? '64px' : '200px'
      this.collapseIcon = this.isCollapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'
    }
  }
}
</script>

<style>
.el-menu--inline,
.el-menu-item {
  background-color: #000c17 !important;
}

.el-submenu__title {
  height: 40px !important;
  line-height: 40px !important;
  padding: 0 25px !important;
  transition: color 0s;
}

.el-menu--collapse .el-submenu__title {
  padding: 0 20px !important;
}

.el-submenu__title>i:nth-child(1) {
  margin-top: 2px;
}

.el-submenu__title>i.el-submenu__icon-arrow {
  margin-top: -5px;
}

.el-menu-item {
  min-width: 0 !important;
  width: calc(100% - 10px);
  margin: 5px;
  height: 40px !important;
  line-height: 40px !important;
  border-radius: 5px;
}

.el-menu--inline>.el-menu-item {
  padding-left: 50px !important;
}

.el-menu-item.is-active {
  background-color: dodgerblue !important;
}

.el-menu-item:hover {
  color: #fff !important;
}

.el-submenu__title:hover *,
.el-submenu__title:hover {
  color: #fff !important;
}

.el-aside {
  box-shadow: 2px 0 6px rgba(0, 21, 41, .35);
  background-color: #001529;
  color: #fff;
  min-height: 100vh;
  transition: width .3s;
  background: linear-gradient(#001529, #001529) 0 0 / 100% 8% no-repeat, #001529;
}



.el-menu--collapse .el-tooltip {
  padding: 0 15px !important;
}

.logo-title {
  margin-left: 5px;
  transition: all .3s;
  color: #fff;
}

.el-menu {
  transition: all .3s;
}




.transition-box {
    margin-bottom: 10px;
    width: 200px;
    height: 100px;
    border-radius: 4px;
    background-color: #409EFF;
    text-align: center;
    color: #fff;
    padding: 40px 20px;
    box-sizing: border-box;
    margin-right: 20px;
  }
</style>
