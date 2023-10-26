<template>
  <div>
    <div>
      <el-input style="width: 150px; margin: 3px 5px" placeholder="查询学号" v-model="number"></el-input>
      <el-input style="width: 150px; margin: 3px 5px" placeholder="查询姓名" v-model="name"></el-input>
      <el-input style="width: 150px; margin: 3px 5px" placeholder="查询性别" v-model="genderText"
        @change="handleGenderChange"></el-input>


      <!-- 增加查询条件1 -->
      <el-input style="width: 150px; margin: 3px 5px" placeholder="查询qq" v-model="qq"></el-input>
      <el-input style="width: 150px; margin: 3px 5px" placeholder="查询专业" v-model="major"></el-input>
      <el-input style="width: 150px; margin: 3px 5px" placeholder="查询备注" v-model="remarks"></el-input>
      <el-input style="width: 150px; margin: 3px 5px" placeholder="查询是否抽烟" v-model="smokeText"
        @change="handleSmokeChange"></el-input>
      <el-time-picker placeholder="睡觉最早时间" v-model="sleepBeginTime" value-format="HH:mm:ss" :picker-options="{
        selectableRange: '19:00:00 - 23:59:59'
      }" style="width: 150px; margin: 3px 5px">
      </el-time-picker>
      <el-time-picker placeholder="睡觉最晚时间" v-model="sleepEndTime" value-format="HH:mm:ss" :picker-options="{
        selectableRange: ['00:00:00 - 02:59:59', '19:00:00 - 23:59:59']
      }" style="width: 150px; margin: 3px 5px">
      </el-time-picker>
      <el-time-picker placeholder="起床最早时间" v-model="getUpBeginTime" value-format="HH:mm:ss" :picker-options="{
        selectableRange: '05:00:00 - 11:59:59'
      }" style="width: 150px; margin: 3px 5px">
      </el-time-picker>
      <el-time-picker placeholder="起床最晚时间" v-model="getUpEndTime" value-format="HH:mm:ss" :picker-options="{
        selectableRange: '04:59:59 - 11:59:59'
      }" style="width: 150px; margin: 3px 5px">
      </el-time-picker>

      <br />
      <el-button style="width: 150px; margin: 3px 5px" type="primary" @click="load(1)">查询</el-button>
      <el-button style="width: 150px; margin: 3px 5px" type="info" @click="reset">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <!-- <el-button type="primary" plain @click="handleAdd">新增</el-button> -->
      <!-- <el-button type="danger" plain @click="delBatch">批量删除</el-button> -->
      <!--       <el-button type="info" plain @click="exportData">批量导出</el-button>
      <el-upload :action="$baseUrl + '/user/import'" :headers="{ token: user.token }" :on-success="handleImport"
        style="display: inline-block; margin-left: 10px" :show-file-list="false">
        <el-button type="primary" plain>批量导入</el-button>
      </el-upload> -->
    </div>


    <el-table :data="tableData" stripe :header-cell-style="{ backgroundColor: 'aliceblue', color: '#666' }"
      @selection-change="handleSelectionChange"><!-- height动态改变？ -->
      <el-table-column prop="number" align="center" label="学号" width="100" fixed></el-table-column>
      <el-table-column prop="name" align="center" label="姓名" width="180"> </el-table-column>
      <el-table-column prop="gender" align="center" label="性别" width="180">
        <template slot-scope="scope">
          <div v-if="scope.row.gender == '1'">男</div>
          <div v-if="scope.row.gender == '0'">女</div>
          <div v-else></div>
        </template>
      </el-table-column>
      <el-table-column prop="qq" align="center" label="qq" width="180"></el-table-column>
      <el-table-column prop="major" align="center" label="专业" width="180"></el-table-column>
      <el-table-column prop="smoke" align="center" label="是否抽烟" width="180">
        <template slot-scope="scope">
          <div v-if="scope.row.smoke == '0'">是</div>
          <div v-if="scope.row.smoke == '1'">否</div>
          <div v-if="scope.row.smoke == '2'">偶尔</div>
          <div v-else></div>
        </template>
      </el-table-column>
      <el-table-column prop="sleepBeginTime" align="center" label="睡觉最早时间" width="180"></el-table-column>
      <el-table-column prop="sleepEndTime" align="center" label="睡觉最晚时间" width="180"></el-table-column>
      <el-table-column prop="getUpBeginTime" align="center" label="起床最早时间" width="180"></el-table-column>
      <el-table-column prop="getUpEndTime" align="center" label="起床最晚时间" width="180"></el-table-column>

      <el-table-column prop="remarks" align="center" label="备注" width="180" show-overflow-tooltip>
        <template v-slot="scope">
          <el-button size="mini" type="primary" plain @click="showRemarks(scope.row)"
            v-if="scope.row.remarks !== null">点我查看备注</el-button>
          <el-button size="mini" type="warning" plain @click="showRemarks(scope.row)" v-else>该同学暂未填写备注</el-button>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" width="180" fixed="right">
        <template v-slot="scope">
          <el-button v-if="isCurrentUser(scope.row.number)" size="mini" type="primary" plain
            @click="handleEdit(scope.row)">编辑</el-button>
          <el-button v-if="isCurrentUser(scope.row.number)" size="mini" type="danger" plain
            @click="del(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div>
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
        :page-sizes="[2, 3, 4, 5, 10, 20, 50]" :page-size=pageSize layout="total, sizes, prev, pager, next, jumper"
        :total=this.total>
      </el-pagination>
    </div>


    <el-dialog title="用户信息" :visible.sync="fromVisible" width="45%" :close-on-click-modal="false">
      <el-form :model="form" label-width="100px" style="padding-right: 20px" :rules="rules" ref="formRef">
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
        <el-form-item label="qq" prop="qq">
          <el-input v-model="form.qq" placeholder="qq" clearable></el-input>
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
          <el-time-picker placeholder="最早时间" v-model="form.sleepBeginTime" value-format="HH:mm:ss" :picker-options="{
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
          <el-time-picker placeholder="最早时间" v-model="form.getUpBeginTime" value-format="HH:mm:ss" :picker-options="{
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
          <el-input type="textarea" v-model="form.remarks" placeholder="请输入备注(最多200字)" clearable
            :maxlength="200"></el-input>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSecondsClick" :disabled="isNumberEmpty">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>

export default {
  name: "UserView",
  data() {
    return {
      tableData: [],  // 所有的数据
      page: 1,   // 当前的页码
      pageSize: 5,  // 每页显示的个数
      number: '',
      numberId: JSON.parse(localStorage.getItem('ecut-user') || '{}').number,
      name: '',
      gender: '',
      //增加查询条件2
      qq: '',
      major: '',
      remarks: '',
      smoke: '',
      sleepBeginTime: '',
      sleepEndTime: '',
      getUpBeginTime: '',
      getUpEndTime: '',


      genderText: '',
      smokeText: '',
      total: 0,
      fromVisible: false,
      form: {},
      rules: {
        number: [
          { required: true, message: '请输入学号', trigger: 'blur' },
          /* { validator: this.validateNumber, trigger: 'blur' } // 添加自定义的学号验证规则  */
        ],
        qq: [
          { validator: this.validateQQ, trigger: 'blur' } // 添加自定义的qq验证规则 
        ],
      },
    }
  },
  computed: {
    /*     genderValue() {
          if (this.genderText === '男') {
            return '1';
          } else if (this.genderText === '女') {
            return '0';
          } else {
            return this.gender;
          }
        }, 
        smokeValue() {
          if (this.smokeText === '是') {
            return '0';
          } else if (this.smokeText === '否') {
            return '1';
          } else if (this.smokeText === '偶尔') {
            return '2';
          } else {
            return this.smoke;
          }
        },
    */
    isNumberEmpty() {
      return this.form.number === ''; // 判断学号是否为空
    }

  },

  created() {
    this.load();
    //this.fetchTableData();



  },
  mounted() {
    // 监听整个文档的点击事件
    document.addEventListener('click', this.handleClick)
    /* window.addEventListener('beforeunload', this.handleBeforeUnload) */
  },
  beforeDestroy() {
    // 在组件销毁前移除事件监听
    document.removeEventListener('click', this.handleClick)
  },
  methods: {
/*     handleBeforeUnload(e) {
      var confirmationMessage = '确定离开此页面吗？';
      e.returnValue = confirmationMessage;
      return confirmationMessage;
    }, */

    handleSecondsClick() {
      this.save();
      this.checkSecondsTime();
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
    del(id) {
      this.$confirm('您确认删除吗？删除后将自动退出登录。', '确认删除', { type: "warning" }).then(() => {
        this.$request.delete('/messages/' + id).then(res => {
          if (res.code === 1) {   // 表示操作成功
            this.$message.success('操作成功')
            this.$router.push('/login')
          } else {
            this.$message.error(res.msg)  // 弹出错误的信息
          }
        })
      }).catch(() => { })
    },
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.fromVisible = true   // 打开弹窗
    },

    showRemarks(row) {
      let name = row.name || "无名大侠";
      let remarks = row.remarks || "该同学暂未填写备注"
      this.$alert(remarks, name + '的备注', {
        confirmButtonText: '确定',
      });
    },

    save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.id ? '/messages' : '/messages',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === 1) {  // 表示成功保存
              this.$message.success('保存成功')
              // 获取 ecut-user 对象并解析为 JavaScript 对象
              const user = JSON.parse(localStorage.getItem('ecut-user'));
              // 给 number 属性赋新值
              user.number = this.form.number;
              // 更新保存到 localStorage 中
              localStorage.setItem('ecut-user', JSON.stringify(user));
              this.load(1)
              this.fromVisible = false
            } else {
              this.$message.error(res.msg)  // 弹出错误的信息
            }
          })
        }
      })
    },

    reset() {
      this.name = ''
      this.number = ''
      this.gender = ''//让实际值为空
      this.genderText = ''//让显示的为空
      this.qq = ''
      this.major = ''
      this.smoke = ''
      this.smokeText = ''
      this.sleepBeginTime = ''
      this.sleepEndTime = ''
      this.getUpBeginTime = ''
      this.getUpEndTime = ''
      this.remarks = ''
      this.load()
    },

    load(page) {  // 分页查询
      if (page) {
        this.page = page;
      }

      //增加查询条件3
      const params = {
        page: this.page,
        pageSize: this.pageSize,
        number: this.number,
        name: this.name,
        gender: this.gender,
        numberId: this.numberId,
        qq: this.qq,
        major: this.major,
        remarks: this.remarks,
        smoke: this.smoke,
        sleepBeginTime: this.sleepBeginTime,
        sleepEndTime: this.sleepEndTime,
        getUpBeginTime: this.getUpBeginTime,
        getUpEndTime: this.getUpEndTime
      };

      this.$request.get('/messages', { params })
        .then(res => {
          this.tableData = res.data.records;
          this.total = res.data.total;
        })
        .catch(error => {
          console.error('请求错误:', error);
        });
    },

    handleCurrentChange(page) {
      this.load(page)
    },

    handleGenderChange() {
      if (this.genderText === '男') {
        this.gender = '1';
      } else if (this.genderText === '女') {
        this.gender = '0';
      } else if (this.genderText === '1') {
        this.gender = '2';
      } else if (this.genderText === '0') {
        this.gender = '2';
      } else {
        this.gender = this.genderText;
      }
    },

    handleSmokeChange() {
      if (this.smokeText === '是') {
        this.smoke = '0';
      } else if (this.smokeText === '否') {
        this.smoke = '1';
      } else if (this.smokeText === '偶尔') {
        this.smoke = '2';
      } else if (this.smokeText === '2') {
        this.smoke = '3';
      } else if (this.smokeText === '1') {
        this.smoke = '3';
      } else if (this.smokeText === '0') {
        this.smoke = '3';
      } else {
        this.smoke = this.smokeText;
      }
    },

    handleSizeChange(newSize) {
      this.pageSize = newSize;
      this.load(this.page);
    },

    isCurrentUser(user) {
      // 已经从后端获取到了当前用户的信息
      const currentUser = JSON.parse(localStorage.getItem('ecut-user') || '{}')

      // 判断当前用户的用户名或用户 ID 是否与内容的作者一致
      if (currentUser && currentUser.number === user) {
        return true; // 当前用户是内容的作者
      } else {
        return false; // 当前用户不是内容的作者
      }
    },

    handleNumberInput() {
      this.isNumberEmpty = this.form.number === '';
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
.custom-tooltip .el-tooltip__content {
  width: 200px;
  /* 设置宽度 */
}
</style>