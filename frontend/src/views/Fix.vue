<template>
  <div>
    <div class="toolbar">
      <el-button type="success" @click="openDialog">新增</el-button>
    </div>

    <el-table :data="tableData" border>
      <el-table-column prop="code" label="抢修编号" />
      <el-table-column prop="time" label="时间" />
      <el-table-column prop="address" label="地点" />
      <el-table-column prop="user" label="负责人" />
      <el-table-column prop="result" label="结果" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" @click="edit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="remove(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" title="抢修记录" width="650px">
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
  { key: 'code', label: '抢修编号' },
  { key: 'time', label: '时间' },
  { key: 'address', label: '地点' },
  { key: 'result', label: '结果' },
  { key: 'user', label: '负责人' },
  { key: 'remark', label: '备注', type: 'textarea' },
  { key: 'boxId', label: '配电箱ID' }
]

const load = async () => {
  const res = await http.get('/fix/page', { params: { pageNum: 1, pageSize: 50 } })
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
  await http.post('/fix/save', form)
  dialogVisible.value = false
  load()
}

const remove = async (id: number) => {
  await http.delete(`/fix/${id}`)
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
