<template>
  <div style="height: 100vh; display: flex; align-items: center; justify-content: center; background-color: #8ac0f2"
    class="background-login">
    <div style="position: absolute; top: 10px; left: 10px;">
      <img src="@/assets/school.png" alt="" style="width: 20%">
    </div>
    <div style="display: flex; background-color: white; opacity: 0.85;width: 50%; border-radius: 30px; overflow: hidden">
      <div style="flex: 1">
        <img src="@/assets/login.png" alt="" style="width: 100%">
      </div>
      <div style="flex: 1; display: flex; align-items: center; justify-content: center">
        <el-form :model="user" style="width: 80%" :rules="rules" ref="loginRef">
          <div style="font-size: 20px; font-weight: bold; text-align: center; margin-bottom: 20px">欢迎登录后台管理系统</div>
          <el-form-item prop="number">
            <el-input prefix-icon="el-icon-user" size="medium" placeholder="请输入学号" v-model="user.number"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input prefix-icon="el-icon-lock" size="medium" show-password placeholder="请输入密码"
              v-model="user.password"></el-input>
          </el-form-item>
          <el-form-item prop="code">
            <div style="display: flex">
              <el-input placeholder="请输入验证码" prefix-icon="el-icon-circle-check" size="medium" style="flex: 1"
                v-model="user.code"></el-input>
              <div style="flex: 1; height: 36px">
                <valid-code @update:value="getCode" />
              </div>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" style="width: 100%" @click="login" ref="loginBtn">登 录</el-button>
          </el-form-item>
          <div style="display: flex">
            <div style="flex: 1">还没有账号？请 <span style="color: #0f9876; cursor: pointer"
                @click="$router.push('/register')">注册</span></div>
            <div style="flex: 1; text-align: right"><span style="color: #0f9876; cursor: pointer"
                @click="$router.push('/forget')">忘记密码</span></div>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>
  
<script>
import ValidCode from "@/components/ValidCode";

export default {
  name: "LoginView",
  components: {
    ValidCode
  },
  data() {

    // 验证码校验
    const validateCode = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入验证码'))
      } else if (value.toLowerCase() !== this.code) {
        callback(new Error('验证码错误'))
      } else {
        callback()
      }
    }

    return {
      code: '',  // 验证码组件传递过来的code
      user: {
        code: '',   // 表单里用户输入的code 验证码
        number: '',
        password: '',
      },
      rules: {
        number: [
          { required: true, message: '请输入学号', trigger: 'blur' },
          /* { validator: this.validateNumber, trigger: 'blur' } // 添加自定义的学号验证规则 */
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
        code: [
          { validator: validateCode, trigger: 'blur' }
        ],
      }
    }
  },
  created() {

  },
  mounted() {
    // 监听键盘按下事件
    document.addEventListener("keydown", this.handleKeyDown);
  },
  destroyed() {
    // 在组件销毁前移除事件监听器
    document.removeEventListener("keydown", this.handleKeyDown);
  },
  methods: {
    getCode(code) {
      this.code = code.toLowerCase();
      //console.log(code.toLowerCase())
    },
    login() {
      this.$refs['loginRef'].validate((valid) => {
        if (valid) {
          // 验证通过
          this.$request.post('/user/login', this.user).then(res => {
            if (res.code === 1) {
              this.$router.push('/')
              this.$message.success('登录成功')
              localStorage.setItem("ecut-user", JSON.stringify(res.data))  // 存储用户数据
            } else {
              this.$message.error(res.msg)
            }
          })
        }

      })
    },
    handleKeyDown(event) {
      // 当用户按下Enter键时触发点击事件
      if (event.keyCode === 13) {
        this.$refs.loginBtn.$el.click();
      }
    },
    validateNumber: (rule, value, callback) => {
    const currentYear = new Date().getFullYear(); // 获取当前年份
        if (!/^\d{10}$/.test(value)) {
          callback(new Error('学号格式有误'));
        } else if (value.substr(0, 4) < 1956 || value.substr(0, 4) > currentYear) {
          callback(new Error('学号格式有误'));
        } else {
          callback();
        }
      },
  
  }
}
</script>
  
<style scoped></style>