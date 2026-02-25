<template>
  <div>
    <div class="toolbar">
      <el-select v-model="filters.boxId" placeholder="关联配电箱" clearable filterable style="width:220px">
        <el-option v-for="opt in boxOptions" :key="opt.value" :label="opt.label" :value="opt.value" />
      </el-select>
      <el-button type="primary" @click="load">刷新</el-button>
    </div>

    <el-table :data="list" border class="full-table">
      <el-table-column prop="id" label="任务ID" width="100" />
      <el-table-column prop="taskNo" label="任务单号" min-width="220" />
      <el-table-column prop="boxAccount" label="关联配电箱" min-width="180" />
      <el-table-column prop="reportTime" label="报修时间" min-width="260" />
      <el-table-column prop="reportUser" label="报修人" min-width="220" />
      <el-table-column label="操作" width="230" fixed="right">
        <template #default="scope">
          <el-button size="small" type="primary" @click="goDetail(scope.row.id)">进入工单</el-button>
          <el-button size="small" type="danger" @click="removeTask(scope.row.id)">删除工单</el-button>
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
import { onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import http from '@/api/http'
import { confirmDeleteAction } from '@/utils/confirmDeleteAction'

const router = useRouter()
const list = ref<any[]>([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const filters = reactive<any>({ boxId: undefined })
const boxOptions = ref<{label:string;value:number}[]>([])

const load = async () => {
  const res = await http.get('/overhaul-task/page', { params: { pageNum: pageNum.value, pageSize: pageSize.value, boxId: filters.boxId } })
  const page = res.data?.data || {}
  list.value = page.records || []
  total.value = page.total || 0
}

const loadBoxes = async () => {
  const res = await http.get('/box/page', { params: { pageNum: 1, pageSize: 1000 } })
  const rows = res.data?.records || []
  boxOptions.value = rows.map((r:any)=>({ value: r.id, label: `${r.boxId || '-'} / ${r.boxNo || '-'} / ${r.boxAddress || '-'}` }))
}

const onSizeChange = (size: number) => { pageSize.value = size; load() }
const onCurrentChange = (current: number) => { pageNum.value = current; load() }
const goDetail = (id: number) => router.push(`/overhaul-task/${id}`)
const removeTask = async (id: number) => {
  if (!(await confirmDeleteAction('确认删除该工单？'))) return
  await http.delete(`/overhaul-task/${id}`)
  await load()
  ElMessage.success('删除成功')
}


onMounted(async () => { await loadBoxes(); await load() })
</script>

<style scoped>
.toolbar { margin-bottom: 12px; display: flex; gap: 8px; align-items: center; }
.full-table { width: 100%; }
.pager { margin-top: 12px; display: flex; justify-content: flex-end; }
</style>
