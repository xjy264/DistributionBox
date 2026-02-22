<template>
  <div>
    <div class="toolbar">
      <el-input v-model="taskNo" placeholder="任务单号" class="field" clearable />
      <el-date-picker
        v-model="inspectionTimeRange"
        type="datetimerange"
        range-separator="至"
        start-placeholder="维保开始时间"
        end-placeholder="维保结束时间"
        value-format="YYYY-MM-DD HH:mm:ss"
        class="field-range"
      />
      <el-input v-model="boxAccount" placeholder="配电箱台账号" class="field" clearable />
      <el-input v-model="inspectionUser" placeholder="维保人" class="field" clearable />
      <el-button type="primary" @click="load">搜索</el-button>
      <el-button @click="reset">重置</el-button>
      <el-button type="success" @click="openCreateDialog">新建维保工单</el-button>
    </div>

    <el-table :data="list" border>
      <el-table-column prop="id" label="任务ID" width="90" />
      <el-table-column prop="taskNo" label="任务单号" width="180" />
      <el-table-column prop="inspectionUser" label="维保人" />
      <el-table-column prop="guardianUser" label="监护人" />
      <el-table-column prop="inspectionTime" label="维保时间" />
      <el-table-column label="关联配电箱台账号" min-width="220">
        <template #default="scope">
          <el-tooltip :content="(scope.row.boxAccounts || []).join(', ') || '-'" placement="top">
            <span>{{ shortBoxAccounts(scope.row.boxAccounts) }}</span>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template #default="scope">
          <el-button size="small" type="primary" @click="goTaskDetail(scope.row.id)">进入工单</el-button>
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

    <el-dialog v-model="createDialog" title="新建维保工单" width="620px">
      <el-form :model="createForm" label-width="100px">
        <el-form-item label="工单编号">
          <el-input v-model="createForm.taskNo" placeholder="手动填写" />
        </el-form-item>
        <el-form-item label="维保人">
          <el-input v-model="createForm.inspectionUser" />
        </el-form-item>
        <el-form-item label="监护人">
          <el-input v-model="createForm.guardianUser" />
        </el-form-item>
        <el-form-item label="维保时间">
          <el-date-picker v-model="createForm.inspectionTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="createForm.remark" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="createDialog = false">取消</el-button>
        <el-button type="primary" @click="createTask">创建并进入</el-button>
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

const taskNo = ref('')
const inspectionTimeRange = ref<string[]>([])
const boxAccount = ref('')
const inspectionUser = ref('')
const list = ref<any[]>([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)

const createDialog = ref(false)
const createForm = reactive<any>({ taskNo: '', inspectionUser: '', guardianUser: '', inspectionTime: '', remark: '' })

const load = async () => {
  const res = await http.get('/maintenance-task/page', {
    params: {
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      inspectionUser: inspectionUser.value,
      taskNo: taskNo.value,
      inspectionTimeStart: inspectionTimeRange.value?.[0] || '',
      inspectionTimeEnd: inspectionTimeRange.value?.[1] || '',
      boxAccount: boxAccount.value
    }
  })
  const page = res.data?.data || {}
  list.value = page.records || []
  total.value = page.total || 0
}

const reset = () => {
  taskNo.value = ''
  inspectionTimeRange.value = []
  boxAccount.value = ''
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

const shortBoxAccounts = (accounts?: string[]) => {
  const arr = Array.isArray(accounts) ? accounts.filter(Boolean) : []
  if (!arr.length) return '-'
  if (arr.length <= 2) return arr.join(', ')
  return `${arr.slice(0, 2).join(', ')} ...`
}

const goTaskDetail = (id: number) => {
  router.push(`/maintenance-task/${id}`)
}

const openCreateDialog = () => {
  Object.assign(createForm, { taskNo: '', inspectionUser: '', guardianUser: '', inspectionTime: '', remark: '' })
  createDialog.value = true
}

const createTask = async () => {
  if (!createForm.taskNo || !createForm.taskNo.trim()) {
    ElMessage.error('工单编号必填（手动填写）')
    return
  }
  const res = await http.post('/maintenance-task/save', {
    taskNo: createForm.taskNo,
    inspectionUser: createForm.inspectionUser,
    guardianUser: createForm.guardianUser,
    inspectionTime: createForm.inspectionTime,
    remark: createForm.remark
  })
  const id = res.data?.data
  createDialog.value = false
  if (id) {
    router.push(`/maintenance-task/${id}`)
  } else {
    await load()
  }
}

onMounted(load)
</script>

<style scoped>
.toolbar { margin-bottom: 12px; display: flex; gap: 8px; align-items: center; }
.field { width: 180px; }
.field-range { width: 360px; }
.pager { margin-top: 12px; display: flex; justify-content: flex-end; }
</style>
