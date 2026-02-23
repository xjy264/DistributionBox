<template>
  <div>
    <div class="toolbar">
      <el-input v-model="filters.inspectionUser" placeholder="巡检人" class="field" clearable />
      <el-button type="primary" @click="load">搜索</el-button>
      <el-button @click="reset">重置</el-button>
      <el-button type="success" @click="openDialog">新增任务</el-button>
    </div>

    <el-table :data="tableData" border>
      <el-table-column prop="id" label="任务ID" width="90" />
      <el-table-column prop="taskNo" label="任务单号" width="180" />
      <el-table-column label="覆盖配电箱">
        <template #default="scope">
          {{ (scope.row.boxIds || []).join(', ') || '-' }}
        </template>
      </el-table-column>
      <el-table-column prop="boxCount" label="箱数" width="80" />
      <el-table-column prop="inspectionUser" label="巡检人" />
      <el-table-column prop="guardianUser" label="监护人" />
      <el-table-column prop="inspectionTime" label="巡检时间" />
      <el-table-column prop="remark" label="任务备注" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" @click="edit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="remove(scope.row.id)">删除</el-button>
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

    <el-dialog v-model="dialogVisible" title="巡检任务" width="760px">
      <EntityForm v-model="form" :fields="fields" />
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { ElMessageBox } from 'element-plus'
import http from '@/api/http'
import { confirmDeleteAction } from '@/utils/confirmDeleteAction'
import EntityForm from '@/components/EntityForm.vue'

const filters = reactive({ inspectionUser: '' })
const tableData = ref<any[]>([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const dialogVisible = ref(false)
const form = reactive<any>({ boxIds: [] })
const boxOptions = ref<{ label: string; value: number }[]>([])

const fields = ref<any[]>([
  { key: 'taskNo', label: '任务单号（可空自动生成）' },
  { key: 'boxIds', label: '关联配电箱', type: 'multi-select', options: boxOptions.value },
  { key: 'inspectionUser', label: '巡检人' },
  { key: 'guardianUser', label: '监护人' },
  { key: 'inspectionTime', label: '巡检时间', type: 'datetime' },
  { key: 'switchModel', label: '开关型号' },
  { key: 'currentRating', label: '额定电流' },
  { key: 'traverseSection', label: '截面积' },
  { key: 'supplyVoltage', label: '供电电压' },
  { key: 'aStarting', label: 'A起动' },
  { key: 'aRunning', label: 'A运行' },
  { key: 'bStarting', label: 'B起动' },
  { key: 'bRunning', label: 'B运行' },
  { key: 'cStarting', label: 'C起动' },
  { key: 'cRunning', label: 'C运行' },
  { key: 'appearanceUrl', label: '外观图', type: 'image' },
  { key: 'firstUrl', label: '图片1', type: 'image' },
  { key: 'secondUrl', label: '图片2', type: 'image' },
  { key: 'thirdUrl', label: '图片3', type: 'image' },
  { key: 'fourthUrl', label: '图片4', type: 'image' },
  { key: 'fifthUrl', label: '图片5', type: 'image' },
  { key: 'earlierUrl', label: '前期', type: 'image' },
  { key: 'laterUrl', label: '后期', type: 'image' },
  { key: 'remark', label: '备注', type: 'textarea' }
])

const refreshFieldOptions = () => {
  fields.value = fields.value.map((item) =>
    item.key === 'boxIds' ? { ...item, options: boxOptions.value } : item
  )
}

const loadBoxes = async () => {
  const res = await http.get('/box/page', { params: { pageNum: 1, pageSize: 1000 } })
  const records = res.data?.records || []
  boxOptions.value = records.map((row: any) => ({
    label: `${row.id} / ${row.boxId || '-'} / ${row.boxAddress || '-'}`,
    value: row.id
  }))
  refreshFieldOptions()
}

const load = async () => {
  const res = await http.get('/inspection-task/page', {
    params: {
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      inspectionUser: filters.inspectionUser
    }
  })
  const pageData = res.data?.data || {}
  tableData.value = pageData.records || []
  total.value = pageData.total || 0
}

const reset = () => {
  filters.inspectionUser = ''
  pageNum.value = 1
  load()
}

const openDialog = () => {
  Object.keys(form).forEach((k) => delete form[k])
  Object.assign(form, { boxIds: [] })
  dialogVisible.value = true
}

const edit = async (row: any) => {
  const detail = await http.get(`/inspection-task/${row.id}`)
  const data = detail.data?.data || {}
  const firstItem = data.items?.[0] || {}
  Object.keys(form).forEach((k) => delete form[k])
  Object.assign(form, {
    id: data.id,
    taskNo: data.taskNo,
    inspectionUser: data.inspectionUser,
    guardianUser: data.guardianUser,
    inspectionTime: data.inspectionTime,
    remark: data.remark,
    boxIds: data.boxIds || [],
    ...firstItem
  })
  dialogVisible.value = true
}

const save = async () => {
  const boxIds: number[] = (form.boxIds || []).map((v: any) => Number(v)).filter((v: number) => v > 0)
  const itemTemplate = {
    switchModel: form.switchModel,
    currentRating: form.currentRating,
    traverseSection: form.traverseSection,
    supplyVoltage: form.supplyVoltage,
    aStarting: form.aStarting,
    aRunning: form.aRunning,
    bStarting: form.bStarting,
    bRunning: form.bRunning,
    cStarting: form.cStarting,
    cRunning: form.cRunning,
    appearanceUrl: form.appearanceUrl,
    firstUrl: form.firstUrl,
    secondUrl: form.secondUrl,
    thirdUrl: form.thirdUrl,
    fourthUrl: form.fourthUrl,
    fifthUrl: form.fifthUrl,
    earlierUrl: form.earlierUrl,
    laterUrl: form.laterUrl,
    remark: form.remark
  }
  const payload = {
    id: form.id,
    taskNo: form.taskNo,
    inspectionUser: form.inspectionUser,
    guardianUser: form.guardianUser,
    inspectionTime: form.inspectionTime,
    remark: form.remark,
    items: boxIds.map((boxId) => ({ boxId, ...itemTemplate }))
  }
  await http.post('/inspection-task/save', payload)
  dialogVisible.value = false
  load()
}

const remove = async (id: number) => {
  if (!(await confirmDeleteAction())) return
  await http.delete(`/inspection-task/${id}`)
  load()
}

const onSizeChange = (size: number) => {
  pageSize.value = size
  load()
}

const onCurrentChange = (page: number) => {
  pageNum.value = page
  load()
}

loadBoxes().then(load)
</script>

<style scoped>
.toolbar {
  display: flex;
  gap: 8px;
  margin-bottom: 12px;
  align-items: center;
  flex-wrap: wrap;
}
.field {
  width: 220px;
}
.pager {
  margin-top: 12px;
}
</style>
