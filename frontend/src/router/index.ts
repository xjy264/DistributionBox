import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import { useUserStore } from '@/stores/user'
import MainLayout from '@/layouts/MainLayout.vue'

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
      { path: 'inspection', component: () => import('@/views/Inspection.vue') },
      { path: 'detect', component: () => import('@/views/Detect.vue') },
      { path: 'check', component: () => import('@/views/Check.vue') },
      { path: 'record', component: () => import('@/views/Record.vue') },
      { path: 'maintenance', component: () => import('@/views/Maintenance.vue') },
      { path: 'fix', component: () => import('@/views/Fix.vue') },
      { path: 'files', component: () => import('@/views/Files.vue') },
      { path: 'course', component: () => import('@/views/Course.vue') },
      { path: 'tax', component: () => import('@/views/Tax.vue') },
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
  const store = useUserStore()
  if (to.path === '/login' || to.path === '/register') {
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
