<template>
  <div>
    <div class="toolbar">
      <el-button type="success" @click="openDialog">新增</el-button>
    </div>

    <el-table :data="tableData" border>
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="pre" label="税前" />
      <el-table-column prop="after" label="税后" />
      <el-table-column prop="rate" label="税率" />
      <el-table-column prop="fund" label="公积金" />
      <el-table-column label="操作" width="160">
        <template #default="scope">
          <el-button size="small" @click="edit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="remove(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" title="税务" width="600px">
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
  { key: 'name', label: '名称' },
  { key: 'pre', label: '税前' },
  { key: 'after', label: '税后' },
  { key: 'rate', label: '税率' },
  { key: 'fund', label: '公积金' }
]

const load = async () => {
  const res = await http.get('/tax/page', { params: { pageNum: 1, pageSize: 100 } })
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
  await http.post('/tax', form)
  dialogVisible.value = false
  load()
}

const remove = async (id: number) => {
  await http.delete(`/tax/${id}`)
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
