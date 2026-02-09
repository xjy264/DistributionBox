<template>
  <div class="login">
    <div class="card">
      <h2>DistributionBox</h2>
      <el-form :model="form" @submit.prevent>
        <el-form-item label="用户名">
          <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" />
        </el-form-item>
        <el-button type="primary" class="full" @click="submit">登录</el-button>
        <el-button class="full" @click="goRegister">注册</el-button>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import http from '@/api/http'

const router = useRouter()
const store = useUserStore()

const form = reactive({
  username: '',
  password: ''
})

const submit = async () => {
  const res = await http.post('/auth/login', form)
  store.setAuth(res.data.data.token, res.data.data.user, res.data.data.menus || [])
  router.push('/home')
}

const goRegister = () => router.push('/register')
</script>

<style scoped>
.login {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #0b1f3a, #173f63);
}
.card {
  width: 360px;
  background: #fff;
  padding: 24px;
  border-radius: 12px;
}
.full {
  width: 100%;
  margin-top: 8px;
}
</style>
