<template>
    <div style="display: flex; justify-content: center; align-items: center; min-height: 100vh;">
        <video src="@/assets/flower.mp4" autoplay muted loop></video>

        <el-form ref="formRef" :model="user" :rules="rules" label-width="80px" style="padding-right: 20px">
            <el-form-item label="学号" prop="number">
                <el-input v-model="user.number" placeholder="请输入学号"></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="newPassword">
                <el-input show-password v-model="user.newPassword" placeholder="请输入新密码"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword">
                <el-input show-password v-model="user.confirmPassword" placeholder="请确认密码"></el-input>
            </el-form-item>
            <el-form-item label="手机号" prop="phone">
                <el-input v-model="user.phone" placeholder="请输入手机号"></el-input>
            </el-form-item>
            <div style="text-align: center; margin-bottom: 20px; z-index: 9999;">
                <el-button type="primary" @click="update">确认修改</el-button>
            </div>
        </el-form>

    </div>
</template>
  
<script>
export default {
    name: "ForgetView",
    data() {
        const validatePassword = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请确认密码'))
            } else if (value !== this.user.newPassword) {
                callback(new Error('两次密码不一致'))
            } else {
                callback()
            }
        }
        const validatePhone = (rule, value, callback) => {
            const regex = /^1[0-9]{10}$/; // 定义手机号正则表达式
            if (!regex.test(value)) {
                callback(new Error('请输入正确的手机号')); // 验证失败，返回错误信息
            } else {
                callback(); // 验证成功，不返回任何信息
            }
        }

        return {
            user: {},
            rules: {
                number: [
                    { required: true, message: '请输入学号', trigger: 'blur' },
                ],
                newPassword: [
                    { required: true, message: '请输入新密码', trigger: 'blur' },
                ],
                phone: [
                    { required: true, message: '请输入手机号', trigger: 'blur' },
                    { validator: validatePhone, required: true, trigger: 'blur' } // 添加自定义的手机号验证规则 
                ],
                confirmPassword: [
                    { validator: validatePassword, required: true, trigger: 'blur' },
                ],
            }
        }
    },
    created() {

    },
    methods: {
        update() {
            this.$refs.formRef.validate((valid) => {
                if (valid) {
                    this.$request.put('/user/forget', this.user).then(res => {
                        if (res.code === 1) {
                            // 成功更新
                            this.$message.success('保存成功');
                            this.$router.push('/login');
                        } else {
                            this.$message.error(res.msg);
                        }
                    });


                }
            })
        },

    }
}
</script>
 
<style scoped>
::v-deep .el-form-item__label {
    font-weight: bold;
    color: #c0938c;
}

.video-container {
    position: relative;
    width: 100%;
    height: 100vh;
}

/* 设置 <video> 元素的样式 */
video {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    object-fit: cover;
    z-index: -1;
}
</style>