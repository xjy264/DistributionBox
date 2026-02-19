<template>
  <div class="image-upload">
    <el-upload
      class="uploader"
      :http-request="uploadImage"
      :show-file-list="false"
      :on-success="handleSuccess"
      :before-upload="beforeUpload"
      :on-error="handleError"
      accept=".jpg,.jpeg,.png,.webp,.gif,.bmp"
    >
      <img v-if="modelValue" :src="previewUrl" class="preview" />
      <div v-else class="placeholder">
        <el-icon class="icon"><Plus /></el-icon>
        <span class="text">点击上传</span>
      </div>
    </el-upload>
    <el-button v-if="modelValue" size="small" type="danger" @click="clear" class="clear-btn">
      清除
    </el-button>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { Plus } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import type { UploadRequestOptions } from 'element-plus'
import http from '@/api/http'
import { useUserStore } from '@/stores/user'

const props = defineProps<{
  modelValue: string
}>()

const emit = defineEmits(['update:modelValue'])

let uploadedThisTurn = false

const store = useUserStore()

const headers = computed(() => ({
  Authorization: store.token ? `Bearer ${store.token}` : '',
  token: store.token || ''
}))

const extractUuid = (value: string) => {
  const raw = value.split('?')[0].split('#')[0]
  const previewMatch = raw.match(/\/files\/preview\/([^/]+)$/)
  if (previewMatch?.[1]) return previewMatch[1]
  const fileMatch = raw.match(/\/files\/([^/]+)$/)
  if (fileMatch?.[1]) return fileMatch[1]
  if (!raw.includes('/') && /^[a-zA-Z0-9_-]{16,}$/.test(raw)) return raw
  return ''
}

const previewUrl = computed(() => {
  const value = (props.modelValue || '').trim()
  if (!value) return ''
  const uuid = extractUuid(value)
  if (uuid) return `/api/files/preview/${uuid}`
  return value
})

const beforeUpload = (file: File) => {
  const ext = (file.name.split('.').pop() || '').toLowerCase()
  const allowed = ['jpg', 'jpeg', 'png', 'webp', 'gif', 'bmp']
  const isImage = file.type.startsWith('image/') && allowed.includes(ext)
  const isLt10M = file.size / 1024 / 1024 < 10
  if (!isImage) {
    ElMessage.error('只能上传 jpg/jpeg/png/webp/gif/bmp 图片文件')
    return false
  }
  if (!isLt10M) {
    ElMessage.error('图片大小不能超过 10MB')
    return false
  }
  return true
}

const parseUploadValue = (response: any): string => {
  const payload = response?.data ?? response
  if (typeof payload === 'string' && payload.trim()) {
    const raw = payload.trim()
    return raw.startsWith('/files/') ? raw : `/files/${raw}`
  }
  if (typeof response === 'string' && response.trim()) {
    const raw = response.trim()
    return raw.startsWith('/files/') ? raw : `/files/${raw}`
  }
  const value = payload?.path || payload?.url || payload?.previewUrl || response?.url
  return typeof value === 'string' ? value : ''
}

const handleSuccess = (response: any) => {
  const code = String(response?.code ?? '')
  const url = parseUploadValue(response)

  // 只要拿到可用URL，就判定上传成功
  if (url) {
    uploadedThisTurn = true
    emit('update:modelValue', url)
    return
  }

  // 后端明确失败才提示失败
  if (code && code !== '200') {
    ElMessage.error(response?.msg || '上传失败')
    return
  }

  // 后端返回成功但未带可解析地址：不再误报失败
  if (code === '200') {
    return
  }

  ElMessage.error(response?.msg || '上传失败')
}

const handleError = (err?: any) => {
  // 若本次已成功回填并回显，则忽略后续误触发的 error 回调
  if (uploadedThisTurn || (props.modelValue && props.modelValue.trim())) {
    uploadedThisTurn = false
    return
  }
  const msg = err?.message || err?.msg || '上传失败'
  ElMessage.error(msg)
}

const uploadImage = async (options: UploadRequestOptions) => {
  const formData = new FormData()
  formData.append('file', options.file)
  const reqHeaders = headers.value
  try {
    const res = await http.post('/files/image/upload', formData, {
      headers: {
        ...reqHeaders,
        'Content-Type': 'multipart/form-data'
      }
    })
    options.onSuccess?.(res.data)
  } catch (e) {
    try {
      const res = await http.post('/files/upload', formData, {
        headers: {
          ...reqHeaders,
          'Content-Type': 'multipart/form-data'
        }
      })
      options.onSuccess?.(res.data)
    } catch (err) {
      options.onError?.(err as Error)
    }
  }
}

const clear = () => {
  emit('update:modelValue', '')
}
</script>

<style scoped>
.image-upload {
  display: inline-block;
}
.uploader {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  overflow: hidden;
  transition: border-color 0.2s;
}
.uploader:hover {
  border-color: #409eff;
}
.preview {
  width: 148px;
  height: 148px;
  object-fit: cover;
  display: block;
}
.placeholder {
  width: 148px;
  height: 148px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #8c939d;
  background: #fafafa;
}
.icon {
  font-size: 28px;
  margin-bottom: 8px;
}
.text {
  font-size: 12px;
}
.clear-btn {
  margin-top: 8px;
}
</style>
