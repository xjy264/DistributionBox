<template>
  <div>
    <div class="toolbar">
      <el-input v-model="pid" placeholder="台帐号" class="field" />
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="success" @click="openDialog">新增</el-button>
    </div>

    <el-table :data="tableData" border>
      <el-table-column prop="pid" label="台帐号" />
      <el-table-column prop="terminalName" label="终端" />
      <el-table-column prop="temperature" label="温度" />
      <el-table-column prop="voltage" label="电压" />
      <el-table-column prop="remark" label="备注" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" @click="edit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="remove(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" title="记录" width="600px">
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

const pid = ref('')
const tableData = ref<any[]>([])
const dialogVisible = ref(false)
const form = reactive<any>({})

const fields = [
  { key: 'pid', label: '台帐号' },
  { key: 'terminalName', label: '终端名称' },
  { key: 'temperature', label: '温度' },
  { key: 'runningCurrent', label: '运行电流' },
  { key: 'startingCurrent', label: '启动电流' },
  { key: 'leakage', label: '漏电' },
  { key: 'voltage', label: '电压' },
  { key: 'insulation', label: '绝缘' },
  { key: 'ground', label: '接地' },
  { key: 'number', label: '编号' },
  { key: 'remark', label: '备注', type: 'textarea' }
]

const load = async () => {
  if (pid.value) {
    const res = await http.get(`/record/getByPid/${pid.value}`)
    tableData.value = res.data.data
  } else {
    const res = await http.get('/record/page', { params: { pageNum: 1, pageSize: 50 } })
    tableData.value = res.data.data.content
  }
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
  await http.post('/record', form)
  dialogVisible.value = false
  load()
}

const remove = async (id: number) => {
  await http.delete(`/record/${id}`)
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
