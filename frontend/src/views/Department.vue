<template>
  <div>
    <div class="toolbar">
      <el-input v-model="department" placeholder="部门" class="field" />
      <el-button @click="load">查询</el-button>
      <el-button type="success" @click="openDialog">新增</el-button>
    </div>

    <el-table :data="tableData" border>
      <el-table-column prop="department" label="部门" />
      <el-table-column prop="address" label="地址" />
      <el-table-column label="操作" width="160">
        <template #default="scope">
          <el-button size="small" @click="edit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="remove(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" title="部门" width="500px">
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

const department = ref('')
const tableData = ref<any[]>([])
const dialogVisible = ref(false)
const form = reactive<any>({})

const fields = [
  { key: 'department', label: '部门' },
  { key: 'address', label: '地址' }
]

const load = async () => {
  const res = await http.get('/departments/page', { params: { pageNum: 1, pageSize: 100, department: department.value } })
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
  await http.post('/departments', form)
  dialogVisible.value = false
  load()
}

const remove = async (id: number) => {
  await http.delete(`/departments/${id}`)
  load()
}

load()
</script>

<style scoped>
.toolbar {
  display: flex;
  gap: 8px;
  margin-bottom: 12px;
  align-items: center;
}
.field {
  width: 200px;
}
</style>
