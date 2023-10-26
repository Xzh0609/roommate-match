<template>
    <div>
        <el-card style="width: 50%">
            <el-form ref="formRef" :model="user" :rules="rules" label-width="80px" style="padding-right: 20px">
                <el-form-item label="原始密码" prop="password">
                    <el-input show-password v-model="user.password" placeholder="请输入原始密码"></el-input>
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
                <div style="text-align: center; margin-bottom: 20px">
                    <el-button type="primary" @click="update">确认修改</el-button>
                </div>
            </el-form>
        </el-card>
    </div>
</template>
  
<script>
export default {
    name: "PasswordView",
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

        return {
            user: JSON.parse(localStorage.getItem('ecut-user') || '{}'),
            rules: {
                password: [
                    { required: true, message: '请输入原始密码', trigger: 'blur' },
                ],
                newPassword: [
                    { required: true, message: '请输入新密码', trigger: 'blur' },
                ],
                phone: [
                    { required: true, message: '请输入手机号', trigger: 'blur' },
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

                    this.$alert('确认之后将退出并重新登录', '注意', {
                        confirmButtonText: '确定',
                        callback: action => {
                            if (action === 'confirm') {
                                this.$request.put('/user/password', this.user).then(res => {
                                    if (res.code === 1) {
                                        this.$message({
                                            type: 'info',
                                            message: `action: ${action}`
                                        });
                                        // 成功更新
                                        this.$message.success('保存成功');
                                        this.$router.push('/login');
                                    } else {
                                        this.$message.error(res.msg);
                                    }
                                });
                            }
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
} 
</style>