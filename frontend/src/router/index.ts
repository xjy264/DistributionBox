import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import MainLayout from '@/layouts/MainLayout.vue'
import { useUserStore } from '@/stores/user'
import { pinia } from '@/stores'

const routes: RouteRecordRaw[] = [
  { path: '/login', component: () => import('@/views/Login.vue') },
  { path: '/register', component: () => import('@/views/Register.vue') },
  {
    path: '/',
    component: MainLayout,
    children: [
      { path: '', redirect: '/box' },
      { path: 'box', component: () => import('@/views/Box.vue') },
      { path: 'box-detail/:id', component: () => import('@/views/BoxDetail.vue') },
      { path: 'dict', component: () => import('@/views/Dict.vue') },
      { path: 'components', component: () => import('@/views/Components.vue') },
      { path: 'overhaul', component: () => import('@/views/OverhaulRecord.vue') },
      { path: 'overhaul-task/:id', component: () => import('@/views/OverhaulTaskDetail.vue') },
      { path: 'files', component: () => import('@/views/Files.vue') },
      { path: 'users', component: () => import('@/views/User.vue') },
      { path: 'roles', component: () => import('@/views/Role.vue') },
      { path: 'menus', component: () => import('@/views/Menu.vue') },
      { path: 'departments', component: () => import('@/views/Department.vue') },
      { path: 'echarts', component: () => import('@/views/Echarts.vue') },
      { path: 'map', component: () => import('@/views/Map.vue') },
      { path: 'station', component: () => import('@/views/Station.vue') },
      { path: 'person', component: () => import('@/views/Person.vue') }
    ]
  },
  { path: '/:pathMatch(.*)*', component: () => import('@/views/NotFound.vue') }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, _from, next) => {
  const whiteList = ['/login', '/register']
  const store = useUserStore(pinia)
  if (whiteList.includes(to.path)) {
    next()
    return
  }
  if (!store.token) {
    next('/login')
    return
  }
  next()
})

export default router
