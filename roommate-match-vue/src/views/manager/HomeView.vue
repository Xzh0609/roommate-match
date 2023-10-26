<template>
  <div>
    <el-container>

      <el-main>
        <div style="text-align: center; font-size: 16px; box-shadow: 0 0 10px rgb(9, 131, 237);
           padding: 15px 20px; border-radius: 5px; margin-bottom: 20px; font-family: '幼圆'">
          <span style="color: rgb(35, 122, 209); font-weight: bold;margin: 2px">{{ students.name }}</span> 欢迎来到
          <br>

          <span style="margin: 5px;display: inline-block;margin-top: 10px;margin-left: 10%;">——</span>
          <span style="color: red;font-weight: bold;margin: 5px; display: inline-block; margin-top: 10px;">室友匹配系统</span>
        </div>
        <el-card style="width: 100%; text-align: center;margin-bottom: 10px; margin-bottom: 20px">
          <div style="font-size:16px;font-family: '幼圆'">
            是否还在为找不到合适的室友而烦恼呢？别担心！
            <br>
            <span style="color: red; font-weight: bold; display: inline-block; margin-top: 10px;">室友匹配系统</span> 正式来啦
          </div>
        </el-card>

        <!-- 添加图片 -->
        <el-carousel :interval="4000" type="card" height="400px">
          <el-carousel-item v-for="item in imgList" :key="item.id">
            <img style="object-fit:scale-down; width:100%; height:100%;" :src="item.idView" class="image">
          </el-carousel-item>
        </el-carousel>
 

      </el-main>

    </el-container>

  </div>
</template>


<script>


export default {
  name: 'HomeView',
  data() {
    return {
      students: {},
      imgList: [
        { id: 0, idView: require("@/assets/login.png") },
        { id: 1, idView: require("@/assets/login.png") },
        { id: 2, idView: require("@/assets/login.png") },
        { id: 3, idView: require("@/assets/login.png") },
        { id: 4, idView: require("@/assets/login.png") },
        { id: 5, idView: require("@/assets/login.png") },
        { id: 6, idView: require("@/assets/login.png") },
        { id: 7, idView: require("@/assets/login.png") },
        { id: 8, idView: require("@/assets/login.png") },

      ]
    }
  },
  mounted() {
    this.$request.get("/user").then(res => {
      //console.log(res);
      this.students = res.data;
    })
  },
  methods: {


  }
}
</script>

<style>
  .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
  }
  
  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }
  
  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
</style>

