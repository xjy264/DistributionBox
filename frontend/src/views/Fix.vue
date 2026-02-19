<template>
  <div>
    <div class="toolbar">
      <el-input v-model="filters.fixUser" placeholder="抢修人" class="field" clearable />
      <el-button type="primary" @click="load">搜索</el-button>
      <el-button @click="reset">重置</el-button>
      <el-button type="success" @click="openDialog">新增任务</el-button>
    </div>

    <el-table :data="tableData" border>
      <el-table-column prop="id" label="任务ID" width="90" />
      <el-table-column prop="taskNo" label="任务单号" width="180" />
      <el-table-column label="关联配电箱">
        <template #default="scope">
          {{ (scope.row.boxIds || []).join(', ') || '-' }}
        </template>
      </el-table-column>
      <el-table-column prop="reportUser" label="报修人" />
      <el-table-column prop="reportTime" label="报修时间" />
      <el-table-column prop="fixUser" label="抢修人" />
      <el-table-column prop="fixTime" label="抢修时间" />
      <el-table-column prop="faultPhenomenon" label="故障现象" />
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

    <el-dialog v-model="dialogVisible" title="抢修任务" width="760px">
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
import http from '@/api/http'
import EntityForm from '@/components/EntityForm.vue'

const filters = reactive({ fixUser: '' })
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
  { key: 'reportUser', label: '报修人' },
  { key: 'reportTime', label: '报修时间', type: 'date' },
  { key: 'fixUser', label: '抢修人' },
  { key: 'fixTime', label: '抢修时间', type: 'date' },
  { key: 'faultPhenomenon', label: '故障现象' },
  { key: 'faultReason', label: '故障原因' },
  { key: 'fixProcess', label: '维修过程', type: 'textarea' },
  { key: 'components', label: '更换元器件' },
  { key: 'remark', label: '备注', type: 'textarea' },
  { key: 'firstUrl', label: '图片1', type: 'image' },
  { key: 'secondUrl', label: '图片2', type: 'image' },
  { key: 'thirdUrl', label: '图片3', type: 'image' },
  { key: 'fourthUrl', label: '图片4', type: 'image' }
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
  const res = await http.get('/repair-task/page', {
    params: {
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      fixUser: filters.fixUser
    }
  })
  const pageData = res.data?.data || {}
  tableData.value = pageData.records || []
  total.value = pageData.total || 0
}

const reset = () => {
  filters.fixUser = ''
  pageNum.value = 1
  load()
}

const openDialog = () => {
  Object.keys(form).forEach((k) => delete form[k])
  Object.assign(form, { boxIds: [] })
  dialogVisible.value = true
}

const edit = async (row: any) => {
  const detail = await http.get(`/repair-task/${row.id}`)
  const data = detail.data?.data || {}
  const firstItem = data.items?.[0] || {}
  Object.keys(form).forEach((k) => delete form[k])
  Object.assign(form, {
    id: data.id,
    taskNo: data.taskNo,
    reportUser: data.reportUser,
    reportTime: data.reportTime,
    fixUser: data.fixUser,
    fixTime: data.fixTime,
    remark: data.remark,
    boxIds: data.boxIds || [],
    ...firstItem
  })
  dialogVisible.value = true
}

const save = async () => {
  const boxIds: number[] = (form.boxIds || []).map((v: any) => Number(v)).filter((v: number) => v > 0)
  const itemTemplate = {
    components: form.components,
    faultPhenomenon: form.faultPhenomenon,
    faultReason: form.faultReason,
    fixProcess: form.fixProcess,
    remark: form.remark,
    firstUrl: form.firstUrl,
    secondUrl: form.secondUrl,
    thirdUrl: form.thirdUrl,
    fourthUrl: form.fourthUrl
  }
  await http.post('/repair-task/save', {
    id: form.id,
    taskNo: form.taskNo,
    reportUser: form.reportUser,
    reportTime: form.reportTime,
    fixUser: form.fixUser,
    fixTime: form.fixTime,
    remark: form.remark,
    items: boxIds.map((boxId) => ({ boxId, ...itemTemplate }))
  })
  dialogVisible.value = false
  load()
}

const remove = async (id: number) => {
  await http.delete(`/repair-task/${id}`)
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
