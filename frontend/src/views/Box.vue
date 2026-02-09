<template>
  <div>
    <div class="toolbar">
      <el-input v-model="filters.address" placeholder="安装地点" class="field" />
      <el-input v-model="filters.area" placeholder="工区" class="field" />
      <el-button type="primary" @click="load">搜索</el-button>
      <el-button @click="reset">重置</el-button>
      <el-button type="success" @click="openDialog">新增</el-button>
    </div>

    <el-table :data="tableData" border>
      <el-table-column prop="boxId" label="台帐号" />
      <el-table-column prop="station" label="车间" />
      <el-table-column prop="area" label="工区" />
      <el-table-column prop="boxAddress" label="安装地点" />
      <el-table-column prop="size" label="规格" />
      <el-table-column label="操作" width="240">
        <template #default="scope">
          <el-button size="small" @click="edit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="remove(scope.row.id)">删除</el-button>
          <el-button size="small" type="primary" @click="print(scope.row.id)">打印</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pager">
      <el-pagination
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        :page-size="pageSize"
        :current-page="pageNum"
        :page-sizes="[5, 10, 20, 50]"
        @size-change="onSizeChange"
        @current-change="onCurrentChange"
      />
    </div>

    <el-dialog v-model="dialogVisible" title="配电箱" width="650px">
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

const filters = reactive({ address: '', area: '' })
const tableData = ref<any[]>([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const dialogVisible = ref(false)
const form = reactive<any>({})

const fields = [
  { key: 'boxId', label: '台帐号' },
  { key: 'station', label: '车间' },
  { key: 'area', label: '工区' },
  { key: 'boxAddress', label: '安装地点' },
  { key: 'size', label: '规格' },
  { key: 'systemUrl', label: '系统图URL' },
  { key: 'firstUrl', label: '图片1URL' },
  { key: 'secondUrl', label: '图片2URL' },
  { key: 'thirdUrl', label: '图片3URL' },
  { key: 'fourthUrl', label: '图片4URL' }
]

const load = async () => {
  const res = await http.get('/boxes/page', {
    params: {
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      address: filters.address,
      area: filters.area
    }
  })
  tableData.value = res.data.data.content
  total.value = res.data.data.totalElements
}

const reset = () => {
  filters.address = ''
  filters.area = ''
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
  await http.post('/boxes/save', form)
  dialogVisible.value = false
  load()
}

const remove = async (id: number) => {
  await http.delete(`/boxes/${id}`)
  load()
}

const print = (id: number) => {
  window.open(`/api/boxes/print/${id}`, '_blank')
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
