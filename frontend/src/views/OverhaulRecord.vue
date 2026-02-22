<template>
  <div>
    <div class="toolbar">
      <el-input v-model="taskNo" placeholder="任务单号" class="field" clearable />
      <el-input v-model="reportUser" placeholder="报修人" class="field" clearable />
      <el-input v-model="rescueUsers" placeholder="抢修人员" class="field" clearable />
      <el-date-picker
        v-model="reportTimeRange"
        type="datetimerange"
        range-separator="至"
        start-placeholder="报修开始时间"
        end-placeholder="报修结束时间"
        value-format="YYYY-MM-DD HH:mm:ss"
        class="field-range"
        clearable
        unlink-panels
      />
      <el-button type="primary" @click="load">搜索</el-button>
      <el-button @click="reset">重置</el-button>
      <el-button type="success" @click="openCreateDialog">新建检修主单</el-button>
    </div>

    <el-table :data="list" border>
      <el-table-column prop="id" label="任务ID" width="90" />
      <el-table-column prop="taskNo" label="任务单号" width="180" />
      <el-table-column prop="reportUnit" label="报修单位" min-width="140" />
      <el-table-column prop="reportTime" label="报修时间" width="180" />
      <el-table-column prop="reportUser" label="报修人" width="120" />
      <el-table-column prop="acceptUser" label="报修接受人" width="120" />
      <el-table-column prop="supervisionUser" label="盯控人员" width="120" />
      <el-table-column prop="rescueUsers" label="抢修人员" min-width="160" />
      <el-table-column prop="faultPhenomenon" label="故障现象" min-width="180" show-overflow-tooltip />
      <el-table-column prop="faultReason" label="故障原因" min-width="180" show-overflow-tooltip />
      <el-table-column prop="rescueSituation" label="抢修情况" min-width="180" show-overflow-tooltip />
      <el-table-column label="操作" width="180" fixed="right">
        <template #default="scope">
          <el-button size="small" @click="openEditDialog(scope.row.id)">编辑</el-button>
          <el-button size="small" type="danger" @click="removeTask(scope.row.id)">删除</el-button>
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

    <el-dialog v-model="dialogVisible" :title="form.id ? '编辑检修主单' : '新建检修主单'" width="760px">
      <el-form :model="form" label-width="110px">
        <el-form-item label="任务单号">
          <el-input v-model="form.taskNo" placeholder="可空，后端自动生成" />
        </el-form-item>
        <el-form-item label="报修单位">
          <el-input v-model="form.reportUnit" />
        </el-form-item>
        <el-form-item label="报修时间">
          <el-date-picker v-model="form.reportTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
        </el-form-item>
        <el-form-item label="报修人">
          <el-input v-model="form.reportUser" />
        </el-form-item>
        <el-form-item label="报修接受人">
          <el-input v-model="form.acceptUser" />
        </el-form-item>
        <el-form-item label="盯控人员">
          <el-input v-model="form.supervisionUser" />
        </el-form-item>
        <el-form-item label="抢修人员">
          <el-input v-model="form.rescueUsers" />
        </el-form-item>
        <el-form-item label="故障现象">
          <el-input v-model="form.faultPhenomenon" type="textarea" :rows="3" />
        </el-form-item>
        <el-form-item label="故障原因">
          <el-input v-model="form.faultReason" type="textarea" :rows="3" />
        </el-form-item>
        <el-form-item label="抢修情况">
          <el-input v-model="form.rescueSituation" type="textarea" :rows="4" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveTask">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import http from '@/api/http'

const taskNo = ref('')
const reportUser = ref('')
const rescueUsers = ref('')
const reportTimeRange = ref<string[]>([])
const list = ref<any[]>([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)

const dialogVisible = ref(false)
const form = reactive<any>({
  id: null,
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

const emptyForm = () => ({
  id: null,
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

const setForm = (data: any = {}) => {
  Object.assign(form, emptyForm(), data || {})
}

const load = async () => {
  const res = await http.get('/overhaul-task/page', {
    params: {
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      taskNo: taskNo.value,
      reportUser: reportUser.value,
      rescueUsers: rescueUsers.value,
      reportTimeStart: reportTimeRange.value?.[0] || '',
      reportTimeEnd: reportTimeRange.value?.[1] || ''
    }
  })
  const page = res.data?.data || {}
  list.value = page.records || []
  total.value = page.total || 0
}

const reset = () => {
  taskNo.value = ''
  reportUser.value = ''
  rescueUsers.value = ''
  reportTimeRange.value = []
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

const openCreateDialog = () => {
  setForm()
  dialogVisible.value = true
}

const openEditDialog = async (id: number) => {
  const res = await http.get(`/overhaul-task/${id}`)
  setForm(res.data?.data || {})
  dialogVisible.value = true
}

const saveTask = async () => {
  try {
    const payload = {
      id: form.id,
      taskNo: (form.taskNo || '').trim(),
      reportUnit: form.reportUnit,
      reportTime: form.reportTime,
      reportUser: form.reportUser,
      acceptUser: form.acceptUser,
      supervisionUser: form.supervisionUser,
      rescueUsers: form.rescueUsers,
      faultPhenomenon: form.faultPhenomenon,
      faultReason: form.faultReason,
      rescueSituation: form.rescueSituation
    }
    if (!payload.taskNo) delete (payload as any).taskNo
    await http.post('/overhaul-task/save', payload)
    dialogVisible.value = false
    ElMessage.success('保存成功')
    await load()
  } catch (error: any) {
    ElMessage.error(error?.message || '保存失败')
  }
}

const removeTask = async (id: number) => {
  try {
    await ElMessageBox.confirm('确认删除该检修主单？', '提示', { type: 'warning' })
    await http.delete(`/overhaul-task/${id}`)
    ElMessage.success('删除成功')
    await load()
  } catch {
    // cancel or failed
  }
}

onMounted(load)
</script>

<style scoped>
.toolbar { margin-bottom: 12px; display: flex; gap: 8px; align-items: center; flex-wrap: wrap; }
.field { width: 180px; }
.field-range { width: 360px; }
.pager { margin-top: 12px; display: flex; justify-content: flex-end; }
</style>
