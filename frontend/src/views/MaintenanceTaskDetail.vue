<template>
  <div>
    <div class="toolbar">
      <el-button @click="goBack">返回维保记录</el-button>
      <el-button type="primary" @click="saveTask">保存工单与明细</el-button>
    </div>

    <el-card>
      <el-form :model="task" inline label-width="90px">
        <el-form-item label="工单编号">
          <el-input v-model="task.taskNo" placeholder="手动填写" />
        </el-form-item>
        <el-form-item label="维保人">
          <el-input v-model="task.inspectionUser" />
        </el-form-item>
        <el-form-item label="监护人">
          <el-input v-model="task.guardianUser" />
        </el-form-item>
        <el-form-item label="维保时间">
          <el-date-picker v-model="task.inspectionTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" />
        </el-form-item>
        <el-form-item label="备注" style="width: 100%">
          <el-input v-model="task.remark" type="textarea" />
        </el-form-item>
      </el-form>
    </el-card>

    <el-card style="margin-top: 12px">
      <template #header>
        <div class="header-row">
          <span>维保明细项（item）</span>
          <div>
            <el-button @click="openBatchDialog">批量新增</el-button>
            <el-button type="primary" @click="addItem">新增item</el-button>
          </div>
        </div>
      </template>
      <el-table :data="items" border>
        <el-table-column prop="boxId" label="配电箱">
          <template #default="scope">
            <el-select v-model="scope.row.boxId" filterable style="width: 180px">
              <el-option v-for="b in boxOptions" :key="b.value" :label="b.label" :value="b.value" />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column prop="switchModel" label="开关型号">
          <template #default="scope"><el-input v-model="scope.row.switchModel" /></template>
        </el-table-column>
        <el-table-column prop="currentRating" label="额定电流">
          <template #default="scope"><el-input v-model="scope.row.currentRating" /></template>
        </el-table-column>
        <el-table-column prop="remark" label="备注">
          <template #default="scope"><el-input v-model="scope.row.remark" /></template>
        </el-table-column>
        <el-table-column label="操作" width="100">
          <template #default="scope">
            <el-button type="danger" size="small" @click="removeItem(scope.$index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="batchDialog" title="批量新增维保item" width="520px">
      <el-form label-width="90px">
        <el-form-item label="选择配电箱">
          <el-select v-model="selectedBoxIds" multiple filterable style="width: 100%">
            <el-option v-for="b in boxOptions" :key="b.value" :label="b.label" :value="b.value" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="batchDialog = false">取消</el-button>
        <el-button type="primary" @click="confirmBatchAdd">确认</el-button>
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

const task = reactive<any>({ id: undefined, taskNo: '', inspectionUser: '', guardianUser: '', inspectionTime: '', remark: '' })
const items = ref<any[]>([])
const boxOptions = ref<Array<{ label: string; value: number }>>([])

const batchDialog = ref(false)
const selectedBoxIds = ref<number[]>([])

const goBack = () => router.push('/maintenance')

const loadBoxes = async () => {
  const res = await http.get('/box/page', { params: { pageNum: 1, pageSize: 2000 } })
  const page = res.data?.data || {}
  const records = page.records || []
  boxOptions.value = records.map((r: any) => ({ label: `${r.id} / ${r.boxId || '-'} / ${r.boxAddress || '-'}`, value: r.id }))
}

const loadTask = async () => {
  const id = Number(route.params.id)
  if (!id) return
  if (id === -1) return
  const res = await http.get(`/maintenance-task/${id}`)
  const data = res.data?.data || {}
  Object.assign(task, {
    id: data.id,
    taskNo: data.taskNo || '',
    inspectionUser: data.inspectionUser || '',
    guardianUser: data.guardianUser || '',
    inspectionTime: data.inspectionTime || '',
    remark: data.remark || ''
  })
  items.value = (data.items || []).map((i: any) => ({ ...i }))
}

const addItem = () => {
  items.value.push({ boxId: undefined, switchModel: '', currentRating: '', remark: '' })
}

const removeItem = (index: number) => {
  items.value.splice(index, 1)
}

const openBatchDialog = () => {
  selectedBoxIds.value = []
  batchDialog.value = true
}

const confirmBatchAdd = () => {
  selectedBoxIds.value.forEach((boxId) => {
    items.value.push({ boxId, switchModel: '', currentRating: '', remark: '' })
  })
  batchDialog.value = false
}

const saveTask = async () => {
  if (!task.taskNo || !task.taskNo.trim()) {
    ElMessage.error('工单编号必填（手动填写）')
    return
  }

  const payload = {
    id: task.id,
    taskNo: task.taskNo,
    inspectionUser: task.inspectionUser,
    guardianUser: task.guardianUser,
    inspectionTime: task.inspectionTime,
    remark: task.remark,
    items: items.value
  }

  const saveRes = await http.post('/maintenance-task/save', payload)
  const savedId = saveRes.data?.data
  if (!task.id && savedId) {
    task.id = savedId
    router.replace(`/maintenance-task/${savedId}`)
  }
  ElMessage.success('保存成功')
  await loadTask()
}

onMounted(async () => {
  await loadBoxes()
  await loadTask()
})
</script>

<style scoped>
.toolbar { display: flex; gap: 8px; margin-bottom: 12px; }
.header-row { display: flex; align-items: center; justify-content: space-between; }
</style>
