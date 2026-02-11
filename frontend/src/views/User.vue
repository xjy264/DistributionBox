<template>
  <div>
    <div class="toolbar">
      <el-input v-model="filters.username" placeholder="用户名" class="field" />
      <el-input v-model="filters.email" placeholder="邮箱" class="field" />
      <el-input v-model="filters.address" placeholder="地址" class="field" />
      <el-button type="primary" @click="load">搜索</el-button>
      <el-button @click="reset">重置</el-button>
      <el-button type="success" @click="openDialog">新增</el-button>
    </div>

    <el-table :data="tableData" border>
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="nickname" label="昵称" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column prop="phone" label="电话" />
      <el-table-column prop="role" label="角色" />
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

    <el-dialog v-model="dialogVisible" title="用户" width="650px">
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

const filters = reactive({ username: '', email: '', address: '' })
const tableData = ref<any[]>([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const dialogVisible = ref(false)
const form = reactive<any>({})

const fields = [
  { key: 'username', label: '用户名' },
  { key: 'password', label: '密码' },
  { key: 'nickname', label: '昵称' },
  { key: 'email', label: '邮箱' },
  { key: 'phone', label: '电话' },
  { key: 'address', label: '地址' },
  { key: 'avatarUrl', label: '头像URL' },
  { key: 'role', label: '角色' }
]

const USER_API_BASE = '/user'

const load = async () => {
  const res = await http.get(`${USER_API_BASE}/page`, {
    params: {
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      username: filters.username,
      email: filters.email,
      address: filters.address
    }
  })
  const pageData = res.data?.data || {}
  tableData.value = pageData.records || pageData.content || []
  total.value = pageData.total || pageData.totalElements || 0
}

const reset = () => {
  filters.username = ''
  filters.email = ''
  filters.address = ''
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
  await http.post(USER_API_BASE, form)
  dialogVisible.value = false
  load()
}

const remove = async (id: number) => {
  await http.delete(`${USER_API_BASE}/${id}`)
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
  width: 180px;
}
.pager {
  margin-top: 12px;
}
</style>
