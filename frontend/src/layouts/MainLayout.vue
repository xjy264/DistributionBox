<template>
  <el-container class="layout">
    <el-aside width="220px" class="aside">
      <div class="logo">DistributionBox</div>
      <el-menu :default-active="active" class="menu" router>
        <el-menu-item v-for="item in visibleMenus" :key="item.path" :index="item.path">
          <span>{{ item.label }}</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <div class="header-left">
          <span class="title">配电箱管理系统</span>
        </div>
        <div class="header-right">
          <span class="user">{{ userLabel }}</span>
          <el-button size="small" @click="logout">退出</el-button>
        </div>
      </el-header>
      <el-main class="main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'

const route = useRoute()
const router = useRouter()
const store = useUserStore()

const menuConfig = [
  { label: '首页', path: '/home' },
  { label: '配电箱', path: '/box' },
  { label: '元器件', path: '/components' },
  { label: '巡检', path: '/inspection' },
  { label: '检测', path: '/detect' },
  { label: '检查项', path: '/check' },
  { label: '记录', path: '/record' },
  { label: '维保', path: '/maintenance' },
  { label: '抢修', path: '/fix' },
  { label: '文件', path: '/files' },
  { label: '课程', path: '/course' },
  { label: '税务', path: '/tax' },
  { label: '用户', path: '/users' },
  { label: '角色', path: '/roles' },
  { label: '菜单', path: '/menus' },
  { label: '部门', path: '/departments' },
  { label: '图表', path: '/echarts' },
  { label: '地图', path: '/map' },
  { label: '站点', path: '/station' },
  { label: '人员', path: '/person' }
]

const active = computed(() => route.path)

const visibleMenus = computed(() => {
  if (!store.menus.length) {
    return menuConfig
  }
  const allowedPaths = new Set<string>()
  const collectPaths = (menus: any[]) => {
    menus.forEach((menu) => {
      if (menu.path) allowedPaths.add(menu.path)
      if (menu.children) collectPaths(menu.children)
    })
  }
  collectPaths(store.menus as any[])
  return menuConfig.filter((item) => allowedPaths.has(item.path))
})

const userLabel = computed(() => store.user.nickname || store.user.username || '用户')

const logout = () => {
  store.logout()
  router.push('/login')
}
</script>

<style scoped>
.layout {
  min-height: 100vh;
  background: #f4f7fb;
}
.aside {
  background: #0b1f3a;
  color: #fff;
  padding: 16px 0;
}
.logo {
  font-weight: 700;
  font-size: 18px;
  text-align: center;
  margin-bottom: 16px;
}
.menu {
  background: transparent;
  color: #fff;
  border-right: none;
}
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: #fff;
  border-bottom: 1px solid #e5e7eb;
}
.title {
  font-weight: 600;
}
.header-right {
  display: flex;
  gap: 12px;
  align-items: center;
}
.main {
  padding: 24px;
}
</style>
