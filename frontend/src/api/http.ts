import axios from 'axios'
import { useUserStore } from '@/stores/user'
import { pinia } from '@/stores'

const http = axios.create({
  baseURL: import.meta.env.VITE_API_BASE || '/api'
})

http.interceptors.request.use((config) => {
  const store = useUserStore(pinia)
  if (store.token) {
    config.headers = config.headers || {}
    config.headers.Authorization = `Bearer ${store.token}`
    // 兼容后端 JwtInterceptor 读取 token 请求头的实现
    config.headers.token = store.token
  }
  return config
})

// 防止重复跳转的标志
let isRedirecting = false

http.interceptors.response.use(
  (response) => response,
  (error) => {
    // 只在明确的 401 未授权响应时才登出，其他错误不处理
    if (error.response && error.response.status === 401) {
      const pathname = window.location.pathname
      const url = error.config?.url || ''
      const shouldIgnore = url.includes('/dict/')
        || url.includes('/location/')
        || url.includes('/files/')
        || url.includes('/api/files/')
        || url.includes('/box-maintenance/')
        || url.includes('/box-maintenance-compare/')
        || url.includes('/box-maintenance-process-image/')

      if (pathname !== '/login' && !shouldIgnore) {
        if (!isRedirecting) {
          isRedirecting = true
          const store = useUserStore(pinia)
          store.logout()
          window.location.href = '/login'
          setTimeout(() => { isRedirecting = false }, 1000)
        }
      }
    }
    return Promise.reject(error)
  }
)

export default http
