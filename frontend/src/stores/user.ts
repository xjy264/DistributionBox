import { defineStore } from 'pinia'

interface User {
  id?: number
  username?: string
  nickname?: string
  role?: string
}

interface Menu {
  id: number
  name: string
  path: string
  icon?: string
  children?: Menu[]
}

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    user: (localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user') as string) : {}) as User,
    menus: (localStorage.getItem('menus') ? JSON.parse(localStorage.getItem('menus') as string) : []) as Menu[]
  }),
  actions: {
    setAuth(token: string, user: User, menus: Menu[]) {
      this.token = token
      this.user = user
      this.menus = menus
      localStorage.setItem('token', token)
      localStorage.setItem('user', JSON.stringify(user))
      localStorage.setItem('menus', JSON.stringify(menus))
    },
    logout() {
      this.token = ''
      this.user = {}
      this.menus = []
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      localStorage.removeItem('menus')
    }
  }
})
