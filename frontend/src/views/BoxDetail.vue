<template>
  <div>
    <div class="toolbar">
      <el-button @click="goBack">返回列表</el-button>
      <el-tag type="success">台帐号：{{ box.boxId || route.query.boxId || '-' }}</el-tag>
      <el-tag>车间：{{ box.station || '-' }}</el-tag>
      <el-tag>工区：{{ box.area || '-' }}</el-tag>
    </div>

    <el-descriptions title="配电箱基础信息" :column="2" border>
      <el-descriptions-item label="安装地点">{{ box.boxAddress || '-' }}</el-descriptions-item>
      <el-descriptions-item label="规格">{{ box.size || '-' }}</el-descriptions-item>
      <el-descriptions-item label="系统图">{{ box.systemUrl || '-' }}</el-descriptions-item>
      <el-descriptions-item label="图片1">{{ box.firstUrl || '-' }}</el-descriptions-item>
      <el-descriptions-item label="图片2">{{ box.secondUrl || '-' }}</el-descriptions-item>
      <el-descriptions-item label="图片3">{{ box.thirdUrl || '-' }}</el-descriptions-item>
    </el-descriptions>

    <el-divider />

    <el-table :data="components" border>
      <el-table-column prop="componentsName" label="电器元件" />
      <el-table-column prop="componentsUnit" label="单位" />
      <el-table-column prop="componentsQuantity" label="数量" />
      <el-table-column prop="componentsType" label="厂家/型号" />
      <el-table-column prop="controlScope" label="回路控制范围" />
      <el-table-column prop="inSpecifications" label="进线规格" />
      <el-table-column prop="outSpecifications" label="出线规格" />
      <el-table-column prop="componentsState" label="技术状态" />
    </el-table>
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import http from '@/api/http'

const route = useRoute()
const router = useRouter()

const box = reactive<any>({})
const components = ref<any[]>([])

const goBack = () => router.push('/box')

const load = async () => {
  const id = route.params.id as string
  const boxId = (route.query.boxId as string) || ''

  if (id) {
    const res = await http.get(`/box/${id}`)
    Object.assign(box, res.data || {})
  }

  const componentKey = box.boxId || boxId || id
  if (componentKey) {
    const res = await http.get(`/components/${componentKey}`)
    components.value = Array.isArray(res.data?.data) ? res.data.data : Array.isArray(res.data) ? res.data : []

    if (!components.value.length && boxId && boxId !== String(componentKey)) {
      const fallback = await http.get(`/components/${boxId}`)
      components.value = Array.isArray(fallback.data?.data) ? fallback.data.data : Array.isArray(fallback.data) ? fallback.data : []
    }
  }
}

onMounted(load)
</script>

<style scoped>
.toolbar {
  display: flex;
  gap: 8px;
  align-items: center;
  margin-bottom: 12px;
  flex-wrap: wrap;
}
</style>
