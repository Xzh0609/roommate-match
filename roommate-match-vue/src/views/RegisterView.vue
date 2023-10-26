<template>
  <div style="height: 100vh; display: flex; align-items: center; justify-content: center; background-color: #152c56"
    class="background-register">
    <div style="position: absolute; top: 10px; left: 10px;">
      <img src="@/assets/school.png" alt="" style="width: 20%">
    </div>
    <div style="display: flex; background-color: white; opacity: 0.85;width: 50%; border-radius: 30px; overflow: hidden">
      <div style="flex: 1">
        <img src="@/assets/register.png" alt="" style="width: 100%">
      </div>
      <div style="flex: 1; display: flex; align-items: center; justify-content: center">
        <el-form :model="user" style="width: 80%" :rules="rules" ref="registerRef">
          <div style="font-size: 20px; font-weight: bold; text-align: center; margin-bottom: 20px">欢迎注册后台管理系统</div>
          <el-form-item prop="number">
            <el-input prefix-icon="el-icon-user" size="medium" placeholder="请输入学号" v-model="user.number"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input prefix-icon="el-icon-lock" size="medium" show-password placeholder="请输入密码"
              v-model="user.password"></el-input>
          </el-form-item>
          <el-form-item prop="confirmPass">
            <el-input prefix-icon="el-icon-lock" size="medium" show-password placeholder="请确认密码"
              v-model="user.confirmPass"></el-input>
          </el-form-item>
          <el-form-item prop="phone">
            <el-input prefix-icon="el-icon-phone-outline" size="medium" placeholder="请输入手机号" v-model="user.phone"
              @input="handlePhoneInput"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" style="width: 100%" @click="register" ref="loginBtn">注 册</el-button>
          </el-form-item>
          <div style="display: flex">
            <div style="flex: 1">已经有账号了？请 <span style="color: #6e77f2; cursor: pointer"
                @click="$router.push('/login')">登录</span></div>
          </div>
        </el-form>
      </div>
    </div>

  </div>
</template>
  
<script>

export default {
  name: "RegisterView",
  data() {
    // 验证码校验
    const validatePassword = (rule, confirmPass, callback) => {
      if (confirmPass === '') {
        callback(new Error('请确认密码'))
      } else if (confirmPass !== this.user.password) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    }
    return {
      user: {
        number: '',
        password: '',
        confirmPass: '',
        phone: ''
      },
      rules: {
        number: [
          { required: true, message: '请输入账号', trigger: 'blur' },
          { validator: this.validateNumber, trigger: 'blur' } // 添加自定义的学号验证规则
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
        phone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { validator: this.validatePhone, trigger: 'blur' } // 添加自定义的手机号验证规则
        ],
        confirmPass: [
          { validator: validatePassword, trigger: 'blur' }
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
    register() {
      this.$refs['registerRef'].validate((valid) => {
        if (valid) {
          // 验证通过
          this.$request.post('/user/register', this.user).then(res => {
            if (res.code === 1) {
              this.$router.push('/login')
              this.$message.success('注册成功')
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
    validatePhone(rule, value, callback) {
      const regex = /^1[0-9]{10}$/; // 定义手机号正则表达式
      if (!regex.test(value)) {
        callback(new Error('请输入正确的手机号')); // 验证失败，返回错误信息
      } else {
        callback(); // 验证成功，不返回任何信息
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