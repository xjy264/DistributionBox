<template>
  <div>
    <el-row  style="text-align:center">
      <el-button type="primary" class="button_station" plain @click="inputStation1">秦皇岛房建车间</el-button>
      <el-button type="primary" class="button_station" plain @click="inputStation2">茶坞房建车间</el-button>
      <el-button type="primary" class="button_station" plain @click="inputStation3">湖东房建车间</el-button>
      <el-button type="primary" class="button_station" plain @click="inputStation4">朔州房建车间</el-button>
    </el-row>

    <el-row style="text-align:center">
      <el-button type="primary" class="button_station" plain @click="inputStation5">大同东房建车间</el-button>
      <el-button type="primary" class="button_station" plain @click="inputStation6">大同西房建车间</el-button>
      <el-button type="primary" class="button_station" plain @click="inputStation7">大同南房建车间</el-button>
      <el-button type="primary" class="button_station" plain @click="inputStation8">原平房建车间</el-button>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "Station",
  data() {
    return{
    }
  },
  created() {
    // 请求分页查询数据
    this.load()
  },
  methods: {
    inputStation1(){
      this.$router.push({path:'/home',query:{station:'秦皇岛房建车间'}});
    },
    inputStation2(){
      this.$router.push({path:'/home',query:{station:'茶坞房建车间'}});
    },
    inputStation3(){
      this.$router.push({path:'/home',query:{station:'湖东房建车间'}});
    },
    inputStation4(){
      this.$router.push({path:'/home',query:{station:'朔州房建车间'}});
    },
    inputStation5(){
      this.$router.push({path:'/home',query:{station:'大同东房建车间'}});
    },
    inputStation6(){
      this.$router.push({path:'/home',query:{station:'大同西房建车间'}});
    },
    inputStation7(){
      this.$router.push({path:'/home',query:{station:'大同南房建车间'}});
    },
    inputStation8(){
      this.$router.push({path:'/home',query:{station:'原平房建车间'}});
    },
  },
}
</script>

<style>
.headerBg {
  background: #eee!important;
}
</style>
