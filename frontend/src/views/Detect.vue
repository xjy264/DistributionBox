<template>
  <div>
    <div class="toolbar">
      <el-button type="success" @click="openDialog">新增</el-button>
    </div>

    <el-table :data="tableData" border>
      <el-table-column prop="pid" label="台帐号" />
      <el-table-column prop="numberState" label="编号状态" />
      <el-table-column prop="appearanceState" label="外观状态" />
      <el-table-column prop="temperatureState" label="温度状态" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" @click="edit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="remove(scope.row.pid)">删除</el-button>
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

    <el-dialog v-model="dialogVisible" title="检测记录" width="760px">
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
  { key: 'numberState', label: '编号状态' },
  { key: 'numberRemark', label: '编号备注' },
  { key: 'electronicState', label: '触电状态' },
  { key: 'electronicRemark', label: '触电备注' },
  { key: 'appearanceState', label: '外观状态' },
  { key: 'appearanceRemark', label: '外观备注' },
  { key: 'robustnessState', label: '稳固状态' },
  { key: 'robustnessRemark', label: '稳固备注' },
  { key: 'lockState', label: '门锁状态' },
  { key: 'lockRemark', label: '门锁备注' },
  { key: 'cableState', label: '电缆标识状态' },
  { key: 'cableRemark', label: '电缆标识备注' },
  { key: 'systemState', label: '系统图状态' },
  { key: 'systemRemark', label: '系统图备注' },
  { key: 'componentsState', label: '元器件标签状态' },
  { key: 'componentsRemark', label: '元器件标签备注' },
  { key: 'wiringState', label: '配线状态' },
  { key: 'wiringRemark', label: '配线备注' },
  { key: 'looseState', label: '松动状态' },
  { key: 'looseRemark', label: '松动备注' },
  { key: 'temperatureState', label: '温度状态' },
  { key: 'temperatureRemark', label: '温度备注' },
  { key: 'lightningState', label: '避雷状态' },
  { key: 'lightningRemark', label: '避雷备注' },
  { key: 'jumperState', label: '跨接线状态' },
  { key: 'jumperRemark', label: '跨接线备注' },
  { key: 'inoutState', label: '进出线密封状态' },
  { key: 'inoutRemark', label: '进出线密封备注' },
  { key: 'zeroState', label: '零排状态' },
  { key: 'zeroRemark', label: '零排备注' },
  { key: 'groundState', label: '地排状态' },
  { key: 'groundRemark', label: '地排备注' },
  { key: 'barrierState', label: '隔弧挡片状态' },
  { key: 'barrierRemark', label: '隔弧挡片备注' },
  { key: 'cleaningState', label: '清灰保洁状态' },
  { key: 'cleaningRemark', label: '清灰保洁备注' },
  { key: 'screwsState', label: '螺丝状态' },
  { key: 'screwsRemark', label: '螺丝备注' },
  { key: 'firstOtherState', label: '其他状态' },
  { key: 'firstOtherRemark', label: '其他备注' },
  { key: 'workingState', label: '线路工作状态' },
  { key: 'workingRemark', label: '线路工作备注' },
  { key: 'crackingState', label: '老化开裂状态' },
  { key: 'crackingRemark', label: '老化开裂备注' },
  { key: 'workingtemperatureState', label: '连接处温度状态' },
  { key: 'workingtemperatureRemark', label: '连接处温度备注' },
  { key: 'insulationState', label: '出线绝缘状态' },
  { key: 'insulationRemark', label: '出线绝缘备注' },
  { key: 'discolorationState', label: '放电变色状态' },
  { key: 'discolorationRemark', label: '放电变色备注' },
  { key: 'orderState', label: '相序状态' },
  { key: 'orderRemark', label: '相序备注' },
  { key: 'bridgeState', label: '桥架状态' },
  { key: 'bridgeRemark', label: '桥架备注' },
  { key: 'bridgeconnectState', label: '桥架接地跨接状态' },
  { key: 'bridgeconnectRemark', label: '桥架接地跨接备注' },
  { key: 'bridgelidState', label: '桥架盖状态' },
  { key: 'bridgelidRemark', label: '桥架盖备注' },
  { key: 'thirdState', label: '其他状态2' },
  { key: 'thirdRemark', label: '其他备注2' }
]

const load = async () => {
  const res = await http.get('/detect/page', {
    params: { pageNum: pageNum.value, pageSize: pageSize.value }
  })
  const pageData = res.data?.data || res.data || {}
  tableData.value = pageData.records || pageData.content || []
  total.value = pageData.total || pageData.totalElements || 0
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
  await http.post('/detect', form)
  dialogVisible.value = false
  load()
}

const remove = async (id: number) => {
  await http.delete(`/detect/${id}`)
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
