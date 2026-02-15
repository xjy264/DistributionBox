<template>
  <div class="image-upload">
    <el-upload
      class="uploader"
      :action="uploadUrl"
      :headers="headers"
      :show-file-list="false"
      :on-success="handleSuccess"
      :before-upload="beforeUpload"
      accept="image/*"
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
import { useUserStore } from '@/stores/user'

const props = defineProps<{
  modelValue: string
}>()

const emit = defineEmits(['update:modelValue'])

const store = useUserStore()

const uploadUrl = '/api/files/upload'

const headers = computed(() => ({
  Authorization: `Bearer ${store.token}`
}))

const previewUrl = computed(() => {
  if (!props.modelValue) return ''
  // 如果是完整 URL 直接返回，否则拼接预览地址
  if (props.modelValue.startsWith('http')) {
    return props.modelValue.replace('/files/', '/api/files/preview/')
  }
  return `/api/files/preview/${props.modelValue}`
})

const beforeUpload = (file: File) => {
  const isImage = file.type.startsWith('image/')
  const isLt10M = file.size / 1024 / 1024 < 10
  if (!isImage) {
    alert('只能上传图片文件')
    return false
  }
  if (!isLt10M) {
    alert('图片大小不能超过 10MB')
    return false
  }
  return true
}

const handleSuccess = (response: any) => {
  if (response.code === '200' && response.data) {
    emit('update:modelValue', response.data.url)
  } else {
    alert('上传失败')
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
