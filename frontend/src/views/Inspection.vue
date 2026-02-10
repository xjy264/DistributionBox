<template>
  <div>
    <div class="toolbar">
      <el-button type="success" @click="openDialog">新增</el-button>
    </div>

    <el-table :data="tableData" border>
      <el-table-column prop="pid" label="台帐号" />
      <el-table-column prop="inspectionUser" label="巡检人" />
      <el-table-column prop="inspectionTime" label="巡检时间" />
      <el-table-column prop="remark" label="备注" />
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

    <el-dialog v-model="dialogVisible" title="巡检记录" width="700px">
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
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const dialogVisible = ref(false)
const form = reactive<any>({})

const fields = [
  { key: 'pid', label: '台帐号' },
  { key: 'inspectionUser', label: '巡检人' },
  { key: 'guardianUser', label: '监护人' },
  { key: 'inspectionTime', label: '巡检时间' },
  { key: 'switchModel', label: '开关型号' },
  { key: 'currentRating', label: '额定电流' },
  { key: 'traverseSection', label: '截面积' },
  { key: 'supplyVoltage', label: '供电电压' },
  { key: 'aStarting', label: 'A起动' },
  { key: 'aRunning', label: 'A运行' },
  { key: 'bStarting', label: 'B起动' },
  { key: 'bRunning', label: 'B运行' },
  { key: 'cStarting', label: 'C起动' },
  { key: 'cRunning', label: 'C运行' },
  { key: 'appearanceUrl', label: '外观图URL' },
  { key: 'firstUrl', label: '图片1URL' },
  { key: 'secondUrl', label: '图片2URL' },
  { key: 'thirdUrl', label: '图片3URL' },
  { key: 'fourthUrl', label: '图片4URL' },
  { key: 'fifthUrl', label: '图片5URL' },
  { key: 'earlierUrl', label: '前期URL' },
  { key: 'laterUrl', label: '后期URL' },
  { key: 'remark', label: '备注', type: 'textarea' }
]

const load = async () => {
  const res = await http.get('/inspection/page', {
    params: { pageNum: pageNum.value, pageSize: pageSize.value }
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
  await http.post('/inspection/save', form)
  dialogVisible.value = false
  load()
}

const remove = async (id: number) => {
  await http.delete(`/inspection/${id}`)
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
}
.pager {
  margin-top: 12px;
}
</style>
