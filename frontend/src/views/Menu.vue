<template>
  <div>
    <div class="toolbar">
      <el-input v-model="name" placeholder="菜单名称" class="field" />
      <el-button @click="load">查询</el-button>
      <el-button type="success" @click="openDialog">新增</el-button>
    </div>

    <el-table :data="tableData" border>
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="path" label="路径" />
      <el-table-column prop="icon" label="图标" />
      <el-table-column prop="pid" label="父级" />
      <el-table-column prop="pagePath" label="页面" />
      <el-table-column prop="hideMenu" label="隐藏" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" @click="edit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="remove(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" title="菜单" width="650px">
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

const name = ref('')
const tableData = ref<any[]>([])
const dialogVisible = ref(false)
const form = reactive<any>({})

const fields = [
  { key: 'name', label: '名称' },
  { key: 'path', label: '路径' },
  { key: 'icon', label: '图标' },
  { key: 'description', label: '描述' },
  { key: 'pid', label: '父级ID' },
  { key: 'pagePath', label: '页面路径' },
  { key: 'hideMenu', label: '隐藏' }
]

const load = async () => {
  const res = await http.get('/menus', { params: { name: name.value } })
  tableData.value = res.data.data
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
  await http.post('/menus', form)
  dialogVisible.value = false
  load()
}

const remove = async (id: number) => {
  await http.delete(`/menus/${id}`)
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
