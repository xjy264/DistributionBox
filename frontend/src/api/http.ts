import axios from 'axios'
import { useUserStore } from '@/stores/user'

const http = axios.create({
  baseURL: import.meta.env.VITE_API_BASE || '/api'
})

http.interceptors.request.use((config) => {
  const store = useUserStore()
  if (store.token) {
    config.headers = config.headers || {}
    config.headers.Authorization = `Bearer ${store.token}`
    // 兼容后端 JwtInterceptor 读取 token 请求头的实现
    config.headers.token = store.token
  }
  return config
})

http.interceptors.response.use(
  (response) => response,
  (error) => {
    // 只在明确的 401 未授权响应时才登出，其他错误不处理
    if (error.response && error.response.status === 401) {
      // 避免在登录页面循环跳转，也排除字典接口
      const pathname = window.location.pathname
      const url = error.config?.url || ''
      if (pathname !== '/login' && !url.includes('/dict/') && !url.includes('/location/')) {
        const store = useUserStore()
        store.logout()
        window.location.href = '/login'
      }
    }
    return Promise.reject(error)
  }
)

export default http
