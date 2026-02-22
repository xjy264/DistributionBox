<template>
  <div>
    <div class="toolbar">
      <el-button @click="goBack">返回检修记录</el-button>
      <el-tag type="success">工单ID：{{ task.id || '-' }}</el-tag>
      <el-tag>工单号：{{ task.taskNo || '-' }}</el-tag>
      <el-tag>报修时间：{{ task.reportTime || '-' }}</el-tag>
      <el-tag>报修人：{{ task.reportUser || '-' }}</el-tag>
      <el-button type="primary" @click="openEdit">修改</el-button>
    </div>

    <el-descriptions title="检修工单信息" :column="2" border>
      <el-descriptions-item label="报修单位">{{ task.reportUnit || '-' }}</el-descriptions-item>
      <el-descriptions-item label="报修时间">{{ task.reportTime || '-' }}</el-descriptions-item>
      <el-descriptions-item label="报修人">{{ task.reportUser || '-' }}</el-descriptions-item>
      <el-descriptions-item label="报修接受人">{{ task.acceptUser || '-' }}</el-descriptions-item>
      <el-descriptions-item label="盯控人员">{{ task.supervisionUser || '-' }}</el-descriptions-item>
      <el-descriptions-item label="抢修人员">{{ task.rescueUsers || '-' }}</el-descriptions-item>
      <el-descriptions-item label="故障现象" :span="2">{{ task.faultPhenomenon || '-' }}</el-descriptions-item>
      <el-descriptions-item label="故障原因" :span="2">{{ task.faultReason || '-' }}</el-descriptions-item>
      <el-descriptions-item label="抢修情况" :span="2">{{ task.rescueSituation || '-' }}</el-descriptions-item>
    </el-descriptions>

    <el-dialog v-model="dialog" title="修改检修工单" width="760px">
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
        <el-button @click="dialog=false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import http from '@/api/http'

const route = useRoute()
const router = useRouter()
const id = Number(route.params.id)
const task = reactive<any>({})
const form = reactive<any>({})
const dialog = ref(false)

const load = async () => {
  const res = await http.get(`/overhaul-task/${id}`)
  Object.assign(task, res.data?.data || {})
}

const openEdit = () => {
  Object.keys(form).forEach(k => delete form[k])
  Object.assign(form, task)
  dialog.value = true
}

const save = async () => {
  const payload = { ...form, taskNo: (form.taskNo || '').trim() }
  if (!payload.taskNo) delete (payload as any).taskNo
  await http.post('/overhaul-task/save', payload)
  dialog.value = false
  ElMessage.success('保存成功')
  await load()
}

const goBack = () => router.push('/overhaul')

onMounted(load)
</script>

<style scoped>
.toolbar{display:flex;gap:8px;align-items:center;flex-wrap:wrap;margin-bottom:10px}
</style>
