<template>
  <div style="font-size: 12px; line-height: 60px; display: flex">

    <div style="flex: 1; font-size: 20px">
      <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>

<!-- 使用-->
      <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">
        <el-breadcrumb-item :to="'/'">首页</el-breadcrumb-item>
        <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
      </el-breadcrumb>
  </div>

    <el-dropdown style="width: 120px; cursor: pointer">
      <img src="../../../files/0f148f8f29b145f180da7c3f085d12ba.webp" alt="" style="width: 20px; position: relative; top: 5px; right: 5px">

      <span>{{user.nickname}}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      <el-dropdown-menu slot="dropdown" style="margin-left :5px;width: 100px;text-align: center">
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <router-link to="/person" style="text-decoration: none" >个人信息</router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding: 5px 0;">
          <span style="text-decoration: none" @click="logout">退出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>

  </div>

</template>

<script>
import store from "@/store";

export default {
  name: "Header",
  props: {
    collapseBtnClass: String,
    collapse: Function,
    user: Object
  },
  computed: {
    currentPathName () {
      return store.state.currentPathName;　　//需要监听的数据
    }
  },
  methods:{
    logout(){
      this.$router.push("/login")
      localStorage.removeItem("user")
      localStorage.removeItem("menus")
      this.$message.success("退出成功")
    }
  }
}
</script>
