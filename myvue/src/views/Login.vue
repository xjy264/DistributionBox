<template>
  <div class="wrapper">
    <div style="margin: 200px auto; background-color: #fff; width: 450px; height: 330px; padding: 30px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>登 录</b></div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item style="margin: 10px 0; text-align: right">
          <el-button type="warning" style="width: 100px" autocomplete="off" @click="register">注册</el-button>
          <el-button type="primary" style="width: 100px" autocomplete="off" @click="login">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import {setRoutes} from "@/router"
export default {
  name: "Login",
  data() {
    return {
      user: {},
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },//失焦时触发
          { min: 5, max: 10, message: '长度在 5 到 10 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 5, max: 10, message: '长度在 5 到 10 个字符', trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
    login: function () {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {
          this.request.post("/login", this.user).then(res => {
            if (res.code === '200') {
              localStorage.setItem("user", JSON.stringify(res.data))//把用户信息存到浏览器中
              localStorage.setItem("menus", JSON.stringify(res.data.menus))//把用户信息存到浏览器中

              //动态设置当前用户的路由
              setRoutes()
              this.$router.push("/home")
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
    register(){
      this.$router.push("/register")
    }
  }
}
</script>

<style>
.wrapper {
  height: 100vh;
  background-image: linear-gradient(to bottom right, #2951d9, #e52525);
  overflow: hidden;
}
</style>
