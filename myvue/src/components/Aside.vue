<template>
  <el-menu :default-openeds="opens" style="min-height: 100%; overflow-x: hidden"
           background-color="rgb(48, 65, 86)"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
  >
    <div style="height: 60px; line-height: 60px; text-align: center">
      <img src="../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px; right: 5px">
      <b style="color: white" v-show="logoTextShow">配电管理系统</b>
    </div>
    <div v-for="item in visibleMenus" :key="item.id">
      <div v-if="item.path">
        <el-menu-item :index="item.path">
          <template slot="title">
            <i :class="item.icon"></i>
            <span slot="title">{{ item.name }}</span>
          </template>
        </el-menu-item>
      </div>
      <div v-else>
        <el-submenu :index="item.id+''">
          <template slot="title">
            <i :class="item.icon"></i>
            <span slot="title">{{ item.name }}</span>
          </template>
          <div v-for="child in item.children" :key="child.id">
            <el-menu-item :index="child.path">
              <template slot="title">
                <i :class="child.icon"></i>
                <span slot="title">{{ child.name }}</span>
              </template>
            </el-menu-item>
          </div>
        </el-submenu>
      </div>
    </div>
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props: {
    isCollapse : Boolean,
    logoTextShow: Boolean
  },
  data(){
    return{
      menus:localStorage.getItem("menus")?JSON.parse(localStorage.getItem("menus")):"",
      opens:localStorage.getItem("menus")?JSON.parse(localStorage.getItem("menus")).map(v=>v.id+''):""
    }
  },
  computed: {
    visibleMenus() {
      // 过滤掉 hideMenu 为 1 的菜单项
      console.log(this.menus.filter(menu => menu.hideMenu != 1))
      return this.menus.filter(menu => menu.hideMenu != 1);
    }
  }
}
</script>

<style scoped>
.el-menu-item.is-active {
  background-color: rgb(38, 52, 69) !important;
}
.el-menu-item:hover {
  background-color: rgb(38, 52, 69) !important;
}

.el-submenu__title:hover {
  background-color: rgb(38, 52, 69) !important;
}
/*解决收缩菜单文字不消失问题*/
.el-menu--collapse span {
  visibility: hidden;
}
</style>
