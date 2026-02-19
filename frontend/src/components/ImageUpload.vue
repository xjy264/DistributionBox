<template>
  <div class="image-upload">
    <el-upload
      class="uploader"
      :action="uploadUrl"
      :headers="headers"
      :show-file-list="false"
      :on-success="handleSuccess"
      :before-upload="beforeUpload"
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
import { useUserStore } from '@/stores/user'

const props = defineProps<{
  modelValue: string
}>()

const emit = defineEmits(['update:modelValue'])

const store = useUserStore()

const uploadUrl = '/api/files/image/upload'

const headers = computed(() => ({
  Authorization: `Bearer ${store.token}`
}))

const extractUuid = (value: string) => {
  const raw = value.split('?')[0].split('#')[0]
  const previewMatch = raw.match(/\/files\/preview\/([^/]+)$/)
  if (previewMatch?.[1]) return previewMatch[1]
  const fileMatch = raw.match(/\/files\/([^/]+)$/)
  if (fileMatch?.[1]) return fileMatch[1]
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

const handleSuccess = (response: any) => {
  const code = String(response?.code ?? '')
  const data = response?.data ?? response
  const url = data?.path || data?.url || data?.previewUrl || response?.url
  if (code === '200' && url) {
    emit('update:modelValue', url)
    return
  }
  if (!response?.code && url) {
    emit('update:modelValue', url)
    return
  }
  ElMessage.error(response?.msg || '上传失败')
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
