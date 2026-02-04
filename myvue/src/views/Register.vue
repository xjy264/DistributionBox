<template>
  <div class="wrapper">
    <div style="margin: 200px auto; background-color: #fff; width: 550px; height: 430px; padding: 30px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>注 册</b></div>
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="输入用户名" prop="username">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="ruleForm.username"></el-input>
        </el-form-item>
        <el-form-item label="输入密码" prop="password">
          <el-input size="medium" style="margin: 10px 0" v-model="ruleForm.password" prefix-icon="el-icon-lock" show-password></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPassword">
          <el-input type="password" style="margin: 10px 0" v-model="ruleForm.checkPassword" prefix-icon="el-icon-lock" show-password></el-input>
        </el-form-item>
        <el-form-item style="margin: 10px 0; text-align: right">
          <el-button type="warning" style="width: 100px" autocomplete="off"  @click="submitForm('ruleForm')">注册</el-button>
          <el-button type="primary" style="width: 100px" autocomplete="off" @click="backLogin">返回登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>

export default {
  name: "Login",
  data() {
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.checkPassword !== '') {
          this.$refs.ruleForm.validateField('checkPassword');
        }
        callback();
      }
    };
    const validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return{
      ruleForm: {
        password: '',
        username: ''
      },
      rules: {
        password: [
          {  required: true, validator: validatePass, trigger: 'blur' },
          { min: 5, max: 10, message: '长度在 5 到 10 个字符' }
        ],
        checkPassword: [
          {   required: true, validator: validatePass2, trigger: 'blur' },
          { min: 5, max: 10, message: '长度在 5 到 10 个字符', trigger: 'blur' }
        ],
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },//失焦时触发
          { min: 5, max: 10, message: '长度在 5 到 10 个字符', trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.request.post("/register",this.ruleForm).then(res=>{
            if (res.code =="200"){
              this.$message.success("注册成功,请登录")
              this.$router.push("/login")
            }
            else {
              this.$message.error(res.msg)
            }
          })
        }
      });
    },
    backLogin(){
      this.$router.push("/login")
    }
  }
}
</script>

<style>
.wrapper {
  height: 100vh;
  background-image: linear-gradient(to bottom right, #FC466B , #3F5EFB);
  overflow: hidden;
}
</style>
