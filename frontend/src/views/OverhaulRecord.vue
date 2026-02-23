<template>
  <div>
    <div class="toolbar">
      <el-button type="primary" @click="load">刷新</el-button>
      <el-button type="success" @click="openCreateDialog">新增检修记录</el-button>
    </div>

    <el-table :data="list" border class="full-table">
      <el-table-column prop="id" label="任务ID" width="100" />
      <el-table-column prop="taskNo" label="任务单号" min-width="260" />
      <el-table-column prop="reportTime" label="报修时间" min-width="260" />
      <el-table-column prop="reportUser" label="报修人" min-width="220" />
      <el-table-column label="操作" width="160" fixed="right">
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

    <el-dialog v-model="dialogVisible" title="新增检修记录" width="760px">
      <el-form :model="form" label-width="110px">
        <el-form-item label="任务单号"><el-input v-model="form.taskNo" placeholder="可空自动生成" /></el-form-item>
        <el-form-item label="报修单位"><el-input v-model="form.reportUnit" /></el-form-item>
        <el-form-item label="报修时间"><el-date-picker v-model="form.reportTime" type="date" value-format="YYYY-MM-DD" style="width:100%" /></el-form-item>
        <el-form-item label="报修人"><el-input v-model="form.reportUser" /></el-form-item>
        <el-form-item label="报修接受人"><el-input v-model="form.acceptUser" /></el-form-item>
        <el-form-item label="盯控人员"><el-input v-model="form.supervisionUser" /></el-form-item>
        <el-form-item label="抢修人员"><el-input v-model="form.rescueUsers" /></el-form-item>
        <el-form-item label="故障现象"><el-input v-model="form.faultPhenomenon" type="textarea" :rows="2" /></el-form-item>
        <el-form-item label="故障原因"><el-input v-model="form.faultReason" type="textarea" :rows="2" /></el-form-item>
        <el-form-item label="抢修情况"><el-input v-model="form.rescueSituation" type="textarea" :rows="3" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="saveCreate">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import http from '@/api/http'

const router = useRouter()
const list = ref<any[]>([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const dialogVisible = ref(false)
const form = reactive<any>({
  taskNo: '',
  reportUnit: '',
  reportTime: '',
  reportUser: '',
  acceptUser: '',
  supervisionUser: '',
  rescueUsers: '',
  faultPhenomenon: '',
  faultReason: '',
  rescueSituation: ''
})

const load = async () => {
  const res = await http.get('/overhaul-task/page', { params: { pageNum: pageNum.value, pageSize: pageSize.value } })
  const page = res.data?.data || {}
  list.value = page.records || []
  total.value = page.total || 0
}

const onSizeChange = (size: number) => { pageSize.value = size; load() }
const onCurrentChange = (current: number) => { pageNum.value = current; load() }
const goDetail = (id: number) => router.push(`/overhaul-task/${id}`)

const openCreateDialog = () => {
  Object.assign(form, {
    taskNo: '', reportUnit: '', reportTime: '', reportUser: '', acceptUser: '',
    supervisionUser: '', rescueUsers: '', faultPhenomenon: '', faultReason: '', rescueSituation: ''
  })
  dialogVisible.value = true
}

const saveCreate = async () => {
  const payload = { ...form, taskNo: (form.taskNo || '').trim() }
  if (!payload.taskNo) delete (payload as any).taskNo
  await http.post('/overhaul-task/save', payload)
  dialogVisible.value = false
  ElMessage.success('新增成功')
  await load()
}

onMounted(load)
</script>

<style scoped>
.toolbar { margin-bottom: 12px; display: flex; gap: 8px; align-items: center; }
.full-table { width: 100%; }
.pager { margin-top: 12px; display: flex; justify-content: flex-end; }
</style>
