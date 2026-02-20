<template>
  <div>
    <div class="toolbar">
      <el-input v-model="fixUser" placeholder="检修人" class="field" clearable />
      <el-button type="primary" @click="load">搜索</el-button>
      <el-button @click="reset">重置</el-button>
    </div>

    <el-table :data="list" border>
      <el-table-column prop="id" label="任务ID" width="90" />
      <el-table-column prop="taskNo" label="任务单号" width="180" />
      <el-table-column prop="reportUser" label="提报人" />
      <el-table-column prop="reportTime" label="提报时间" />
      <el-table-column prop="fixUser" label="检修人" />
      <el-table-column prop="fixTime" label="检修时间" />
      <el-table-column prop="faultPhenomenon" label="故障现象" />
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

const fixUser = ref('')
const list = ref<any[]>([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)

const load = async () => {
  const res = await http.get('/overhaul-task/page', {
    params: { pageNum: pageNum.value, pageSize: pageSize.value, fixUser: fixUser.value }
  })
  const page = res.data?.data || {}
  list.value = page.records || []
  total.value = page.total || 0
}

const reset = () => {
  fixUser.value = ''
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
