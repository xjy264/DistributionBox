<template>
  <div>
    <div class="toolbar">
      <el-button @click="goBack">返回列表</el-button>
      <el-tag type="success">配电箱ID：{{ box.id || '-' }}</el-tag>
      <el-tag type="warning">台帐号：{{ box.boxId || '-' }}</el-tag>
      <el-tag>车间：{{ box.station || '-' }}</el-tag>
      <el-tag>工区：{{ box.area || '-' }}</el-tag>
    </div>

    <el-descriptions title="配电箱基础信息" :column="2" border>
      <el-descriptions-item label="安装地点">{{ box.boxAddress || '-' }}</el-descriptions-item>
      <el-descriptions-item label="规格">{{ box.size || '-' }}</el-descriptions-item>
      <el-descriptions-item label="系统图">{{ box.systemUrl || '-' }}</el-descriptions-item>
      <el-descriptions-item label="图片1">{{ box.firstUrl || '-' }}</el-descriptions-item>
      <el-descriptions-item label="图片2">{{ box.secondUrl || '-' }}</el-descriptions-item>
      <el-descriptions-item label="图片3">{{ box.thirdUrl || '-' }}</el-descriptions-item>
      <el-descriptions-item label="图片4">{{ box.fourthUrl || '-' }}</el-descriptions-item>
    </el-descriptions>

    <el-divider />

    <el-tabs>
      <el-tab-pane label="元器件">
        <div class="sub-toolbar">
          <el-button type="success" @click="openComponentDialog">新增元器件</el-button>
        </div>
        <el-table :data="components" border>
          <el-table-column prop="componentsName" label="电器元件" />
          <el-table-column prop="componentsUnit" label="单位" />
          <el-table-column prop="componentsQuantity" label="数量" />
          <el-table-column prop="componentsType" label="厂家/型号" />
          <el-table-column prop="controlScope" label="回路控制范围" />
          <el-table-column prop="inSpecifications" label="进线规格" />
          <el-table-column prop="outSpecifications" label="出线规格" />
          <el-table-column prop="componentsState" label="技术状态" />
          <el-table-column label="操作" width="180">
            <template #default="scope">
              <el-button size="small" @click="editComponent(scope.row)">编辑</el-button>
              <el-button size="small" type="danger" @click="removeComponent(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="巡检明细历史">
        <div class="sub-toolbar">
          <el-button type="success" @click="openInspectionDialog">新增巡检任务</el-button>
        </div>
        <el-table :data="inspections" border>
          <el-table-column prop="id" label="明细ID" width="90" />
          <el-table-column prop="taskNo" label="任务单号" width="180" />
          <el-table-column prop="inspectionUser" label="巡检人" />
          <el-table-column prop="guardianUser" label="监护人" />
          <el-table-column prop="inspectionTime" label="巡检时间" />
          <el-table-column prop="switchModel" label="开关型号" />
          <el-table-column prop="remark" label="明细备注" />
          <el-table-column label="操作" width="180">
            <template #default="scope">
              <el-button size="small" @click="editInspection(scope.row)">编辑任务</el-button>
              <el-button size="small" type="danger" @click="removeInspection(scope.row.taskId)">删除任务</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="抢修记录">
        <div class="sub-toolbar">
          <el-button type="success" @click="openRepairDialog">新增抢修任务</el-button>
        </div>
        <el-table :data="repairs" border>
          <el-table-column prop="id" label="明细ID" width="90" />
          <el-table-column prop="taskNo" label="任务单号" width="180" />
          <el-table-column prop="reportUser" label="报修人" />
          <el-table-column prop="reportTime" label="报修时间" />
          <el-table-column prop="fixUser" label="抢修人" />
          <el-table-column prop="fixTime" label="抢修时间" />
          <el-table-column prop="faultPhenomenon" label="故障现象" />
          <el-table-column label="操作" width="180">
            <template #default="scope">
              <el-button size="small" @click="editRepair(scope.row)">编辑任务</el-button>
              <el-button size="small" type="danger" @click="removeRepair(scope.row.taskId)">删除任务</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>

    <el-dialog v-model="componentDialog" title="元器件" width="700px">
      <EntityForm v-model="componentForm" :fields="componentFields" />
      <template #footer>
        <el-button @click="componentDialog = false">取消</el-button>
        <el-button type="primary" @click="saveComponent">保存</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="inspectionDialog" title="巡检记录" width="760px">
      <EntityForm v-model="inspectionForm" :fields="inspectionFields" />
      <template #footer>
        <el-button @click="inspectionDialog = false">取消</el-button>
        <el-button type="primary" @click="saveInspection">保存</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="repairDialog" title="抢修任务" width="760px">
      <EntityForm v-model="repairForm" :fields="repairFields" />
      <template #footer>
        <el-button @click="repairDialog = false">取消</el-button>
        <el-button type="primary" @click="saveRepair">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import http from '@/api/http'
import EntityForm from '@/components/EntityForm.vue'

const route = useRoute()
const router = useRouter()

const box = reactive<any>({})
const components = ref<any[]>([])
const inspections = ref<any[]>([])
const repairs = ref<any[]>([])
const allBoxOptions = ref<{ label: string; value: number }[]>([])

const componentDialog = ref(false)
const inspectionDialog = ref(false)
const repairDialog = ref(false)

const componentForm = reactive<any>({})
const inspectionForm = reactive<any>({ boxIds: [] })
const repairForm = reactive<any>({ boxIds: [] })

const componentFields = [
  { key: 'componentsName', label: '元件' },
  { key: 'componentsUnit', label: '单位' },
  { key: 'componentsQuantity', label: '数量' },
  { key: 'componentsType', label: '厂家/型号' },
  { key: 'componentsState', label: '技术状态' },
  { key: 'controlScope', label: '回路控制范围' },
  { key: 'inSpecifications', label: '进线规格' },
  { key: 'outSpecifications', label: '出线规格' }
]

const inspectionFields = ref<any[]>([
  { key: 'taskNo', label: '任务单号（可空自动生成）' },
  { key: 'boxIds', label: '关联配电箱', type: 'multi-select', options: allBoxOptions.value },
  { key: 'inspectionUser', label: '巡检人' },
  { key: 'guardianUser', label: '监护人' },
  { key: 'inspectionTime', label: '巡检时间', type: 'datetime' },
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
])

const repairFields = ref<any[]>([
  { key: 'taskNo', label: '任务单号（可空自动生成）' },
  { key: 'boxIds', label: '关联配电箱', type: 'multi-select', options: allBoxOptions.value },
  { key: 'reportUser', label: '报修人' },
  { key: 'reportTime', label: '报修时间', type: 'date' },
  { key: 'fixUser', label: '抢修人' },
  { key: 'fixTime', label: '抢修时间', type: 'date' },
  { key: 'faultPhenomenon', label: '故障现象' },
  { key: 'faultReason', label: '故障原因' },
  { key: 'fixProcess', label: '维修过程', type: 'textarea' },
  { key: 'components', label: '更换元器件' },
  { key: 'remark', label: '备注', type: 'textarea' },
  { key: 'firstUrl', label: '图片1URL' },
  { key: 'secondUrl', label: '图片2URL' },
  { key: 'thirdUrl', label: '图片3URL' },
  { key: 'fourthUrl', label: '图片4URL' }
])

const goBack = () => router.push('/box')

const refreshBoxOptions = () => {
  inspectionFields.value = inspectionFields.value.map((item) =>
    item.key === 'boxIds' ? { ...item, options: allBoxOptions.value } : item
  )
  repairFields.value = repairFields.value.map((item) =>
    item.key === 'boxIds' ? { ...item, options: allBoxOptions.value } : item
  )
}

const loadBoxOptions = async () => {
  const res = await http.get('/box/page', { params: { pageNum: 1, pageSize: 1000 } })
  const rows = res.data?.records || []
  allBoxOptions.value = rows.map((row: any) => ({
    label: `${row.id} / ${row.boxId || '-'} / ${row.boxAddress || '-'}`,
    value: row.id
  }))
  refreshBoxOptions()
}

const load = async () => {
  const id = Number(route.params.id)
  if (!id) return

  const [boxRes, compRes, inspectRes, repairRes] = await Promise.all([
    http.get(`/box/${id}`),
    http.get(`/components/${id}`),
    http.get('/inspection-item/page', { params: { pageNum: 1, pageSize: 200, boxId: id } }),
    http.get('/repair-item/page', { params: { pageNum: 1, pageSize: 200, boxId: id } })
  ])

  Object.keys(box).forEach((k) => delete box[k])
  Object.assign(box, boxRes.data || {})
  components.value = Array.isArray(compRes.data?.data) ? compRes.data.data : Array.isArray(compRes.data) ? compRes.data : []
  inspections.value = inspectRes.data?.data?.records || []
  repairs.value = repairRes.data?.data?.records || []
}

const openComponentDialog = () => {
  Object.keys(componentForm).forEach((k) => delete componentForm[k])
  componentDialog.value = true
}

const editComponent = (row: any) => {
  Object.keys(componentForm).forEach((k) => delete componentForm[k])
  Object.assign(componentForm, row)
  componentDialog.value = true
}

const saveComponent = async () => {
  componentForm.boxId = String(box.id)
  await http.post('/components/save', componentForm)
  componentDialog.value = false
  load()
}

const removeComponent = async (id: number) => {
  await http.delete(`/components/${id}`)
  load()
}

const openInspectionDialog = () => {
  Object.keys(inspectionForm).forEach((k) => delete inspectionForm[k])
  Object.assign(inspectionForm, { boxIds: box.id ? [box.id] : [] })
  inspectionDialog.value = true
}

const editInspection = async (row: any) => {
  const detail = await http.get(`/inspection-task/${row.taskId}`)
  const data = detail.data?.data || {}
  const firstItem = data.items?.[0] || {}
  Object.keys(inspectionForm).forEach((k) => delete inspectionForm[k])
  Object.assign(inspectionForm, {
    id: data.id,
    taskNo: data.taskNo,
    inspectionUser: data.inspectionUser,
    guardianUser: data.guardianUser,
    inspectionTime: data.inspectionTime,
    remark: data.remark,
    boxIds: data.boxIds || [],
    ...firstItem
  })
  inspectionDialog.value = true
}

const saveInspection = async () => {
  const boxIds: number[] = (inspectionForm.boxIds || []).map((v: any) => Number(v)).filter((v: number) => v > 0)
  const itemTemplate = {
    switchModel: inspectionForm.switchModel,
    currentRating: inspectionForm.currentRating,
    traverseSection: inspectionForm.traverseSection,
    supplyVoltage: inspectionForm.supplyVoltage,
    aStarting: inspectionForm.aStarting,
    aRunning: inspectionForm.aRunning,
    bStarting: inspectionForm.bStarting,
    bRunning: inspectionForm.bRunning,
    cStarting: inspectionForm.cStarting,
    cRunning: inspectionForm.cRunning,
    appearanceUrl: inspectionForm.appearanceUrl,
    firstUrl: inspectionForm.firstUrl,
    secondUrl: inspectionForm.secondUrl,
    thirdUrl: inspectionForm.thirdUrl,
    fourthUrl: inspectionForm.fourthUrl,
    fifthUrl: inspectionForm.fifthUrl,
    earlierUrl: inspectionForm.earlierUrl,
    laterUrl: inspectionForm.laterUrl,
    remark: inspectionForm.remark
  }
  await http.post('/inspection-task/save', {
    id: inspectionForm.id,
    taskNo: inspectionForm.taskNo,
    inspectionUser: inspectionForm.inspectionUser,
    guardianUser: inspectionForm.guardianUser,
    inspectionTime: inspectionForm.inspectionTime,
    remark: inspectionForm.remark,
    items: boxIds.map((boxId) => ({ boxId, ...itemTemplate }))
  })
  inspectionDialog.value = false
  load()
}

const removeInspection = async (id: number) => {
  await http.delete(`/inspection-task/${id}`)
  load()
}

const openRepairDialog = () => {
  Object.keys(repairForm).forEach((k) => delete repairForm[k])
  Object.assign(repairForm, { boxIds: box.id ? [box.id] : [] })
  repairDialog.value = true
}

const editRepair = async (row: any) => {
  const detail = await http.get(`/repair-task/${row.taskId}`)
  const data = detail.data?.data || {}
  const firstItem = data.items?.[0] || {}
  Object.keys(repairForm).forEach((k) => delete repairForm[k])
  Object.assign(repairForm, {
    id: data.id,
    taskNo: data.taskNo,
    reportUser: data.reportUser,
    reportTime: data.reportTime,
    fixUser: data.fixUser,
    fixTime: data.fixTime,
    remark: data.remark,
    boxIds: data.boxIds || [],
    ...firstItem
  })
  repairDialog.value = true
}

const saveRepair = async () => {
  const boxIds: number[] = (repairForm.boxIds || []).map((v: any) => Number(v)).filter((v: number) => v > 0)
  const itemTemplate = {
    components: repairForm.components,
    faultPhenomenon: repairForm.faultPhenomenon,
    faultReason: repairForm.faultReason,
    fixProcess: repairForm.fixProcess,
    remark: repairForm.remark,
    firstUrl: repairForm.firstUrl,
    secondUrl: repairForm.secondUrl,
    thirdUrl: repairForm.thirdUrl,
    fourthUrl: repairForm.fourthUrl
  }
  await http.post('/repair-task/save', {
    id: repairForm.id,
    taskNo: repairForm.taskNo,
    reportUser: repairForm.reportUser,
    reportTime: repairForm.reportTime,
    fixUser: repairForm.fixUser,
    fixTime: repairForm.fixTime,
    remark: repairForm.remark,
    items: boxIds.map((boxId) => ({ boxId, ...itemTemplate }))
  })
  repairDialog.value = false
  load()
}

const removeRepair = async (id: number) => {
  await http.delete(`/repair-task/${id}`)
  load()
}

onMounted(async () => {
  await loadBoxOptions()
  await load()
})
</script>

<style scoped>
.toolbar {
  display: flex;
  gap: 8px;
  align-items: center;
  margin-bottom: 12px;
  flex-wrap: wrap;
}
.sub-toolbar {
  margin-bottom: 10px;
}
</style>
