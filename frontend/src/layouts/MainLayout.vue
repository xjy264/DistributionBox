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
  { label: '配电箱', path: '/box' },
  { label: '地点配置', path: '/dict' },
  { label: '元器件', path: '/components' },
  { label: '巡检', path: '/inspection' },
  { label: '检测', path: '/detect' },
  { label: '维保记录', path: '/maintenance' },
  { label: '检修记录', path: '/fix' },

  { label: '文件', path: '/files' },
  { label: '用户', path: '/users' },
  { label: '角色', path: '/roles' },
  { label: '菜单', path: '/menus' },
  { label: '图表', path: '/echarts' },
  { label: '人员', path: '/person' }
]

const active = computed(() => route.path)

const adminOnlyPaths = new Set(['/users', '/roles', '/menus'])

const visibleMenus = computed(() => {
  const isAdmin = store.user.role === 'ROLE_ADMIN'

  if (!store.menus.length) {
    return menuConfig.filter((item) => isAdmin || !adminOnlyPaths.has(item.path))
  }

  const allowedPaths = new Set<string>()
  const collectPaths = (menus: any[]) => {
    menus.forEach((menu) => {
      if (menu.path) allowedPaths.add(menu.path)
      if (menu.children) collectPaths(menu.children)
    })
  }
  collectPaths(store.menus as any[])

  return menuConfig.filter((item) => {
    if (!allowedPaths.has(item.path)) return false
    if (!isAdmin && adminOnlyPaths.has(item.path)) return false
    return true
  })
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
  color: #fff;
}
.menu {
  background: transparent;
  border-right: none;
}
.menu :deep(.el-menu-item) {
  color: #fff;
}
.menu :deep(.el-menu-item:hover) {
  background-color: rgba(255, 255, 255, 0.1);
  color: #fff;
}
.menu :deep(.el-menu-item.is-active) {
  background-color: rgba(255, 255, 255, 0.2);
  color: #fff;
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
