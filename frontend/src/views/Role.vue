<template>
  <div>
    <div class="toolbar">
      <el-input v-model="name" placeholder="角色名称" class="field" />
      <el-button @click="load">查询</el-button>
      <el-button type="success" @click="openDialog">新增</el-button>
    </div>

    <el-table :data="tableData" border>
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="identity" label="标识" />
      <el-table-column prop="description" label="描述" />
      <el-table-column label="操作" width="220">
        <template #default="scope">
          <el-button size="small" @click="edit(scope.row)">编辑</el-button>
          <el-button size="small" type="primary" @click="openBind(scope.row)">绑定菜单</el-button>
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

    <el-dialog v-model="dialogVisible" title="角色" width="600px">
      <EntityForm v-model="form" :fields="fields" />
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="bindVisible" title="绑定菜单" width="500px">
      <el-select v-model="menuIds" multiple filterable style="width: 100%">
        <el-option v-for="id in allMenuIds" :key="id" :value="id" :label="String(id)" />
      </el-select>
      <template #footer>
        <el-button @click="bindVisible = false">取消</el-button>
        <el-button type="primary" @click="saveBind">保存</el-button>
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
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const dialogVisible = ref(false)
const bindVisible = ref(false)
const form = reactive<any>({})
const roleId = ref<number | null>(null)
const allMenuIds = ref<number[]>([])
const menuIds = ref<number[]>([])

const fields = [
  { key: 'name', label: '名称' },
  { key: 'identity', label: '标识' },
  { key: 'description', label: '描述' }
]

const load = async () => {
  const res = await http.get('/roles/page', {
    params: { pageNum: pageNum.value, pageSize: pageSize.value, name: name.value }
  })
  tableData.value = res.data.data.content
  total.value = res.data.data.totalElements
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
  await http.post('/roles', form)
  dialogVisible.value = false
  load()
}

const remove = async (id: number) => {
  await http.delete(`/roles/${id}`)
  load()
}

const openBind = async (row: any) => {
  roleId.value = row.id
  const idsRes = await http.get('/menus/ids')
  allMenuIds.value = idsRes.data.data
  const res = await http.get(`/roles/checks/${row.id}`)
  menuIds.value = res.data.data
  bindVisible.value = true
}

const saveBind = async () => {
  if (!roleId.value) return
  await http.post(`/roles/roleMenu/${roleId.value}`, menuIds.value)
  bindVisible.value = false
}

const onSizeChange = (size: number) => {
  pageSize.value = size
  load()
}

const onCurrentChange = (page: number) => {
  pageNum.value = page
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
.pager {
  margin-top: 12px;
}
</style>
