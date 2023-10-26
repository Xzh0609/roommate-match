<template>
    <div>
        <el-card style="width: 50%">
            <el-form :model="form" label-width="80px" style="padding-right: 20px" :rules="rules" ref="personRef">
                <el-form-item label="学号" prop="number">
                    <el-input v-model="form.number" placeholder="请输入学号" clearable @input="handleNumberInput"></el-input>
                </el-form-item>
                <el-form-item label="姓名" prop="name">
                    <el-input v-model="form.name" placeholder="请输入姓名" clearable></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="gender">
                    <el-radio-group v-model="form.gender">
                        <el-radio label="1">男</el-radio>
                        <el-radio label="0">女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="手机号" prop="phone">
                    <el-input v-model="form.phone" placeholder="请输入手机号" clearable @input="handlePhoneInput"></el-input>
                </el-form-item>
                <el-form-item label="qq" prop="qq">
                    <el-input v-model="form.qq" placeholder="请输入qq" clearable v-debounce:5000="validateQQ"></el-input>
                </el-form-item>
                <el-form-item label="专业" prop="major">
                    <el-input v-model="form.major" placeholder="请输入专业" clearable></el-input>
                </el-form-item>

                <el-form-item label="抽烟" prop="smoke">
                    <el-radio-group v-model="form.smoke">
                        <el-radio label="0">是</el-radio>
                        <el-radio label="1">否</el-radio>
                        <el-radio label="2">偶尔</el-radio>
                    </el-radio-group>
                </el-form-item>

                <el-form-item label="睡觉时间">
                    <el-time-picker placeholder="最早时间" v-model="form.sleepBeginTime" value-format="HH:mm:ss"
                        :picker-options="{
                            selectableRange: '19:00:00 - 23:59:59'
                        }" style="margin: 5px">
                    </el-time-picker>
                    <span>-</span>
                    <el-time-picker placeholder="最晚时间" v-model="form.sleepEndTime" value-format="HH:mm:ss" :picker-options="{
                        selectableRange: ['00:00:00 - 02:59:59', '19:00:00 - 23:59:59']
                    }" style="margin: 5px">
                    </el-time-picker>
                </el-form-item>

                <el-form-item label="起床时间">
                    <el-time-picker placeholder="最早时间" v-model="form.getUpBeginTime" value-format="HH:mm:ss"
                        :picker-options="{
                            selectableRange: '05:00:00 - 11:59:59'
                        }" style="margin: 5px">
                    </el-time-picker>
                    <span>-</span>
                    <el-time-picker placeholder="最晚时间" v-model="form.getUpEndTime" value-format="HH:mm:ss" :picker-options="{
                        selectableRange: '04:59:59 - 11:59:59'
                    }" style="margin: 5px">
                    </el-time-picker>
                </el-form-item>

                <el-form-item label="备注" prop="remarks">
                    <el-input type="textarea" v-model="form.remarks" placeholder="请输入备注(最多200字)" clearable :maxlength="200"
                        rows="4"></el-input>
                </el-form-item>
                <div style="text-align: center; margin-bottom: 20px">
                    <el-button type="primary" @click="handleClick" style="width: 100px;"
                        :disabled="isNumberEmpty || isPhoneEmpty">保
                        存</el-button>
                </div>
            </el-form>
        </el-card>
    </div>
</template>


<script>

export default {
    beforeRouteLeave(to, from, next) {
        this.$confirm('未保存的结果将消失哦，是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            next();
        }).catch(() => {
            this.$message({
                type: 'success',
                message: '已取消跳转'
            });
        });
    },
    name: "PersonView",
    data() {
        return {
            user: JSON.parse(localStorage.getItem('ecut-user') || '{}'),
            fromVisible: false,
            form: {
                /* number: '',
                phone: '' */
            },
            rules: {
                number: [
                    { required: true, message: '请输入学号', trigger: 'blur' },
                    /* { validator: this.validateNumber, trigger: 'blur' } // 添加自定义的学号验证规则  */
                ],
                phone: [
                    { required: true, message: '请输入手机号', trigger: 'blur' },
                    { validator: this.validatePhone, trigger: 'blur' } // 添加自定义的手机号验证规则 
                ],
                qq: [
                    { validator: this.validateQQ, trigger: 'blur' } // 添加自定义的qq号验证规则 
                ],
            },

        }
    },

    computed: {
        isNumberEmpty() {
            return this.form.number === ''; // 判断学号是否为空
        },
        isPhoneEmpty() {
            return this.form.phone === ''; // 判断学号是否为空
        }
    },
    created() {
        window.addEventListener('beforeunload', this.handleBeforeUnload)
    },
    mounted() {
        this.$request.get("/user").then(res => {
            this.form = res.data;
        })
        /*         this.$router.beforeEach((to, from,next) => {
                    this.$confirm('未保存的结果将消失哦, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        next();
                    }).catch(() => {
                        this.$message({
                            type: 'success',
                            message: '已取消跳转'
                        });
                    });
                }); */
    },
    methods: {
        handleBeforeUnload(e) {
            var confirmationMessage = '确定重新加载此页面吗？';
            e.returnValue = confirmationMessage;
            return confirmationMessage;
        },
        checkSecondsTime() {
            let beginTime1 = this.form.getUpBeginTime;
            let endTime1 = this.form.getUpEndTime;
            let beginTime2 = this.form.sleepBeginTime;
            let endTime2 = this.form.sleepEndTime;

            // 解析时间字符串中的小时、分钟和秒
            let timeArray1 = beginTime1.split(':');
            let timeArray2 = endTime1.split(':');
            let timeArray3 = beginTime2.split(':');
            let timeArray4 = endTime2.split(':');

            let seconds1 = parseInt(timeArray1[2]);
            let seconds2 = parseInt(timeArray2[2]);
            let seconds3 = parseInt(timeArray3[2]);
            let seconds4 = parseInt(timeArray4[2]);

            // 判断是否为00
            if (seconds1 != 0 || seconds2 != 0 || seconds3 != 0 || seconds4 != 0) {
                // 发送一条element的通知，可以使用ElNotification组件或者ElMessage组件
                // 例如：
                this.$notify({
                    title: '细啊！',
                    message: '竟然精确到秒',
                    type: 'success'
                });
            }
        },
        handleClick() {
            this.update();
            this.checkSecondsTime();
        },
        update() {
            this.$refs['personRef'].validate((valid) => {
                if (valid) {
                    // 保存当前的用户信息到数据库
                    this.$request.put('/user', this.form).then(res => {
                        if (res.code === 1) {
                            // 成功更新
                            this.$message.success('保存成功')

                            // 获取 ecut-user 对象并解析为 JavaScript 对象
                            const user = JSON.parse(localStorage.getItem('ecut-user'));
                            // 给 number 属性赋新值
                            user.number = this.form.number;
                            // 更新保存到 localStorage 中
                            localStorage.setItem('ecut-user', JSON.stringify(user));
                        } else {
                            this.$message.error(res.msg)
                        }
                    })
                }

            })
        },

        handleNumberInput() {
            this.isNumberEmpty = this.form.number === '';
        },

        handlePhoneInput() {
            this.isPhoneEmpty = this.form.phone === '';
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
        validateQQ(rule, value, callback) {
            if (value == null) {
                callback();
            }
            const regex = /^(?:[1-9][0-9]{4,14})?$/; // 定义qq号正则表达式
            if (!regex.test(value)) {
                callback(new Error('请输入正确的QQ号')); // 验证失败，返回错误信息
            } else {
                callback(); // 验证成功，不返回任何信息
            }
        },
    }
}
</script>
  
<style scoped>
::v-deep .el-form-item__label {
    font-weight: bold;
}


.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
    border-radius: 50%;
}

.avatar {
    width: 178px;
    height: 178px;
    display: block;
    border-radius: 50%;
}
</style>
<!-- 切换页面时弹出提示框 -->