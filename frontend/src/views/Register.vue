<template>
  <div class="login">
    <div class="card">
      <h2>注册用户</h2>
      <el-form :model="form" @submit.prevent>
        <el-form-item label="用户名">
          <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" />
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" />
        </el-form-item>
        <el-form-item label="角色">
          <el-input v-model="form.role" placeholder="ADMIN / MANAGER / TECHNICIAN / VIEWER" />
        </el-form-item>
        <el-button type="primary" class="full" @click="submit">注册</el-button>
        <el-button class="full" @click="goLogin">返回登录</el-button>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import http from '@/api/http'

const router = useRouter()

const form = reactive({
  username: '',
  password: '',
  nickname: '',
  role: 'TECHNICIAN'
})

const submit = async () => {
  await http.post('/auth/register', form)
  router.push('/login')
}

const goLogin = () => router.push('/login')
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
