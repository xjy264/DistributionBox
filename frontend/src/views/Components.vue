<template>
  <div>
    <div class="toolbar">
      <el-input v-model="filters.boxId" placeholder="配电箱ID" class="field" />
      <el-button type="primary" @click="load">搜索</el-button>
      <el-button @click="reset">重置</el-button>
      <el-button type="success" @click="openDialog">新增</el-button>
    </div>

    <el-table :data="tableData" border>
      <el-table-column prop="componentsName" label="元件" />
      <el-table-column prop="componentsUnit" label="单位" />
      <el-table-column prop="componentsQuantity" label="数量" />
      <el-table-column prop="componentsType" label="厂家/型号" />
      <el-table-column prop="componentsState" label="状态" />
      <el-table-column prop="boxId" label="配电箱" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" @click="edit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="remove(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" title="元器件" width="650px">
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

const filters = reactive({ boxId: '' })
const tableData = ref<any[]>([])
const dialogVisible = ref(false)
const form = reactive<any>({})

const fields = [
  { key: 'componentsName', label: '元件' },
  { key: 'componentsUnit', label: '单位' },
  { key: 'componentsQuantity', label: '数量' },
  { key: 'componentsType', label: '厂家/型号' },
  { key: 'componentsState', label: '技术状态' },
  { key: 'boxId', label: '配电箱ID' },
  { key: 'controlScope', label: '回路控制范围' },
  { key: 'inSpecifications', label: '进线规格' },
  { key: 'outSpecifications', label: '出线规格' }
]

const load = async () => {
  if (filters.boxId) {
    const res = await http.get(`/components/${filters.boxId}`)
    tableData.value = Array.isArray(res.data?.data) ? res.data.data : Array.isArray(res.data) ? res.data : []
  } else {
    const res = await http.get('/components/getAll')
    tableData.value = Array.isArray(res.data?.data) ? res.data.data : Array.isArray(res.data) ? res.data : []
  }
}

const reset = () => {
  filters.boxId = ''
  load()
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
  await http.post('/components/save', form)
  dialogVisible.value = false
  load()
}

const remove = async (id: number) => {
  await http.delete(`/components/${id}`)
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
  flex-wrap: wrap;
}
.field {
  width: 200px;
}
</style>
