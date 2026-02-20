<template>
  <div>
    <div class="toolbar">
      <el-input v-model="inspectionUser" placeholder="维保人" class="field" clearable />
      <el-button type="primary" @click="load">搜索</el-button>
      <el-button @click="reset">重置</el-button>
    </div>

    <el-table :data="list" border>
      <el-table-column prop="id" label="任务ID" width="90" />
      <el-table-column prop="taskNo" label="任务单号" width="180" />
      <el-table-column prop="inspectionUser" label="维保人" />
      <el-table-column prop="guardianUser" label="监护人" />
      <el-table-column prop="inspectionTime" label="维保时间" />
      <el-table-column label="关联配电箱">
        <template #default="scope">{{ (scope.row.boxIds || []).join(', ') || '-' }}</template>
      </el-table-column>
    </el-table>

    <div class="pager">
      <el-pagination
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        :page-size="pageSize"
        :current-page="pageNum"
        :page-sizes="[5, 10, 20]"
        @size-change="onSizeChange"
        @current-change="onCurrentChange"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import http from '@/api/http'

const inspectionUser = ref('')
const list = ref<any[]>([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)

const load = async () => {
  const res = await http.get('/maintenance-task/page', {
    params: { pageNum: pageNum.value, pageSize: pageSize.value, inspectionUser: inspectionUser.value }
  })
  const page = res.data?.data || {}
  list.value = page.records || []
  total.value = page.total || 0
}

const reset = () => {
  inspectionUser.value = ''
  pageNum.value = 1
  load()
}

const onSizeChange = (size: number) => {
  pageSize.value = size
  load()
}

const onCurrentChange = (current: number) => {
  pageNum.value = current
  load()
}

onMounted(load)
</script>

<style scoped>
.toolbar { margin-bottom: 12px; display: flex; gap: 8px; align-items: center; }
.field { width: 200px; }
.pager { margin-top: 12px; display: flex; justify-content: flex-end; }
</style>
