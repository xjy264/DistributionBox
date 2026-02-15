<template>
  <div class="login">
    <div class="overlay"></div>
    <div class="card">
      <div class="brand">配电箱管理系统</div>

      <el-form :model="form" label-position="top" @submit.prevent>
        <el-form-item label="用户名" class="form-item">
          <el-input v-model="form.username" size="large" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码" class="form-item">
          <el-input v-model="form.password" type="password" size="large" show-password placeholder="请输入密码" />
        </el-form-item>
        <el-button type="primary" class="full primary" :loading="submitting" :disabled="submitting" @click="submit">登录</el-button>
        <el-button class="full secondary" @click="goRegister">注册</el-button>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import http from '@/api/http'
import { ElMessage } from 'element-plus'

const router = useRouter()
const store = useUserStore()

const form = reactive({
  username: '',
  password: ''
})
const submitting = ref(false)

const submit = async () => {
  if (submitting.value) return
  submitting.value = true
  try {
    const res = await http.post('/login', form)
    if (res.data.code !== '200') {
      ElMessage.error(res.data.msg || '登录失败')
      return
    }
    const data = res.data.data
    const user = {
      id: data.id,
      username: data.username,
      nickname: data.nickname,
      role: data.role
    }
    store.setAuth(data.token, user, data.menus || [])
    router.push('/box')
  } finally {
    submitting.value = false
  }
}

const goRegister = () => router.push('/register')
</script>

<style scoped>
.login {
  position: relative;
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 24px;
  background:
    radial-gradient(circle at 20% 20%, rgba(80, 143, 255, 0.18), transparent 38%),
    radial-gradient(circle at 80% 85%, rgba(65, 111, 215, 0.2), transparent 35%),
    linear-gradient(135deg, #07152b 0%, #0f2e54 50%, #173f63 100%);
}

.overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0.04), rgba(255, 255, 255, 0));
  pointer-events: none;
}

.card {
  position: relative;
  z-index: 1;
  width: 420px;
  max-width: 100%;
  background: #ffffff;
  border-radius: 16px;
  padding: 28px 28px 22px;
  box-shadow: 0 20px 60px rgba(5, 19, 42, 0.35);
}

.brand {
  font-size: 34px;
  font-weight: 800;
  line-height: 1.2;
  color: #0f2e54;
  letter-spacing: 0.4px;
  margin-bottom: 18px;
  text-align: left;
}

.form-item {
  margin-bottom: 14px;
}

.full {
  width: 100%;
  height: 39px;
  margin-top: 8px;
  border-radius: 8px;
  font-size: 15px;
  font-weight: 600;
  line-height: 39px;
  padding: 0;
}

.full :deep(span) {
  display: inline-flex;
  width: 100%;
  height: 39px;
  justify-content: center;
  align-items: center;
  line-height: 1;
}

.primary {
  background: linear-gradient(135deg, #1e5aa8, #2673d6);
  border: none;
}

.secondary {
  color: #1e5aa8;
  border-color: #c9d9ef;
  background: #f7fbff;
}
</style>
