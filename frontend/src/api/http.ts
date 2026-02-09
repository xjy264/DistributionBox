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
  }
  return config
})

http.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      const store = useUserStore()
      store.logout()
      window.location.href = '/login'
    }
    return Promise.reject(error)
  }
)

export default http
