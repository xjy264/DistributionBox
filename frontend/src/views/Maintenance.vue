<template>
  <div>
    <div class="toolbar">
      <el-button type="success" @click="openDialog">新增</el-button>
    </div>

    <el-table :data="tableData" border>
      <el-table-column prop="pid" label="台帐号" />
      <el-table-column prop="reportUser" label="报修人" />
      <el-table-column prop="reportTime" label="报修时间" />
      <el-table-column prop="faultPhenomenon" label="故障现象" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" @click="edit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="remove(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" title="维保记录" width="700px">
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

const tableData = ref<any[]>([])
const dialogVisible = ref(false)
const form = reactive<any>({})

const fields = [
  { key: 'pid', label: '台帐号' },
  { key: 'reportUser', label: '报修人' },
  { key: 'reportTime', label: '报修时间' },
  { key: 'fixUser', label: '抢修人' },
  { key: 'fixTime', label: '抢修时间' },
  { key: 'faultPhenomenon', label: '故障现象' },
  { key: 'faultReason', label: '故障原因' },
  { key: 'fixProcess', label: '维修过程', type: 'textarea' },
  { key: 'components', label: '更换元器件' },
  { key: 'remark', label: '备注', type: 'textarea' },
  { key: 'firstUrl', label: '图片1URL' },
  { key: 'secondUrl', label: '图片2URL' },
  { key: 'thirdUrl', label: '图片3URL' },
  { key: 'fourthUrl', label: '图片4URL' }
]

const load = async () => {
  const res = await http.get('/maintenance/page', { params: { pageNum: 1, pageSize: 50 } })
  tableData.value = res.data.data.content
}

const openDialog = () => {
  Object.keys(form).forEach((k) => delete form[k])
  dialogVisible.value = true
}

const edit = (row: any) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const save = async () => {
  await http.post('/maintenance/save', form)
  dialogVisible.value = false
  load()
}

const remove = async (id: number) => {
  await http.delete(`/maintenance/${id}`)
  load()
}

load()
</script>

<style scoped>
.toolbar {
  display: flex;
  gap: 8px;
  margin-bottom: 12px;
}
</style>
