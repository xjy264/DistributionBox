<template>
  <div>
    <div class="toolbar">
      <el-button type="primary" @click="load">刷新</el-button>
    </div>

    <el-table :data="list" border>
      <el-table-column prop="id" label="任务ID" width="90" />
      <el-table-column prop="taskNo" label="任务单号" width="180" />
      <el-table-column prop="reportTime" label="报修时间" width="180" />
      <el-table-column prop="reportUser" label="报修人" width="140" />
      <el-table-column label="操作" width="140" fixed="right">
        <template #default="scope">
          <el-button size="small" type="primary" @click="goDetail(scope.row.id)">进入工单</el-button>
        </template>
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
import { useRouter } from 'vue-router'
import http from '@/api/http'

const router = useRouter()
const list = ref<any[]>([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)

const load = async () => {
  const res = await http.get('/overhaul-task/page', { params: { pageNum: pageNum.value, pageSize: pageSize.value } })
  const page = res.data?.data || {}
  list.value = page.records || []
  total.value = page.total || 0
}

const onSizeChange = (size: number) => { pageSize.value = size; load() }
const onCurrentChange = (current: number) => { pageNum.value = current; load() }
const goDetail = (id: number) => router.push(`/overhaul-task/${id}`)

onMounted(load)
</script>

<style scoped>
.toolbar { margin-bottom: 12px; display: flex; gap: 8px; align-items: center; }
.pager { margin-top: 12px; display: flex; justify-content: flex-end; }
</style>
