<template>
  <div>
    <div class="toolbar">
      <el-button @click="goBack">返回列表</el-button>
      <el-button type="primary" @click="openBoxEditDialog">编辑基础信息</el-button>
    </div>

    <el-descriptions title="配电箱基础信息" :column="2" border>
      <el-descriptions-item label="配电箱ID">{{ toDisplay(box.id) }}</el-descriptions-item>
      <el-descriptions-item label="*台账号">{{ toDisplay(box.boxId) }}</el-descriptions-item>
      <el-descriptions-item label="配电箱编号">{{ toDisplay(box.boxNo) }}</el-descriptions-item>
            <el-descriptions-item label="车间">{{ toDisplay(box.station) }}</el-descriptions-item>
      <el-descriptions-item label="工区">{{ toDisplay(box.area) }}</el-descriptions-item>
      <el-descriptions-item label="安装地点">{{ toDisplay(box.boxAddress) }}</el-descriptions-item>
      <el-descriptions-item label="规格">{{ toDisplay(box.size) }}</el-descriptions-item>
      <el-descriptions-item label="明装暗装">{{ toDisplay(box.pileType) }}</el-descriptions-item>
      <el-descriptions-item label="室内室外">{{ toDisplay(box.indoorOutdoor) }}</el-descriptions-item>
      <el-descriptions-item label="进线来源及规格">{{ toDisplay(box.incomingSource) }}</el-descriptions-item>
      <el-descriptions-item label="是否与其它单位共用">{{ toDisplay(box.sharedWithOthers) }}</el-descriptions-item>
      <el-descriptions-item label="共用范围">{{ toDisplay(box.sharedScope) }}</el-descriptions-item>
      <el-descriptions-item label="是否为大功率电器">{{ toDisplay(box.highPowerAppliance) }}</el-descriptions-item>
      <el-descriptions-item label="大功率电器名称">{{ toDisplay(box.highPowerName) }}</el-descriptions-item>
      <el-descriptions-item label="系统图">
        <PreviewImage :src="resolvePreviewUrl(box.systemUrl)" width="220px" height="160px" />
      </el-descriptions-item>
      <el-descriptions-item label="图片1">
        <PreviewImage :src="resolvePreviewUrl(box.firstUrl)" width="220px" height="160px" />
      </el-descriptions-item>
      <el-descriptions-item label="图片2">
        <PreviewImage :src="resolvePreviewUrl(box.secondUrl)" width="220px" height="160px" />
      </el-descriptions-item>
    </el-descriptions>

    <el-divider />

    <el-tabs>
      <el-tab-pane label="元器件">
        <div class="sub-toolbar">
          <el-button type="success" @click="openComponentDialog">新增元器件</el-button>
        </div>
        <el-table :data="components" border>
          <el-table-column type="index" label="序号" width="70" />
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

      <el-tab-pane label="回路">
        <div class="sub-toolbar">
          <el-button type="success" @click="openCircuitDialog">新增回路</el-button>
        </div>
        <el-table :data="circuits" border>
          <el-table-column type="index" label="序号" width="70" />
          <el-table-column prop="supplyCircuit" label="供电回路" width="120" />
          <el-table-column prop="startCurrent" label="启动电流" width="100" />
          <el-table-column prop="runCurrent" label="运行电流" width="90" />
          <el-table-column prop="power" label="功率" width="70" />
          <el-table-column prop="electricDevice" label="用电设备" width="100" />
          <el-table-column prop="deviceLocation" label="设备地点" min-width="180" show-overflow-tooltip />
          <el-table-column prop="remark" label="备注" min-width="100" show-overflow-tooltip />
          <el-table-column label="操作" width="160">
            <template #default="scope">
              <el-button size="small" @click="editCircuit(scope.row)">编辑</el-button>
              <el-button size="small" type="danger" @click="removeCircuit(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="检修记录">
        <div class="sub-toolbar">
          <el-button type="success" @click="openOverhaulDialog">新增检修记录</el-button>
        </div>
        <el-table :data="overhaulTasks" border>
          <el-table-column type="index" label="序号" width="70" />
          <el-table-column prop="taskNo" label="任务单号" min-width="180" />
          <el-table-column prop="reportTime" label="报修时间" min-width="140" />
          <el-table-column prop="reportUser" label="报修人" min-width="120" />
          <el-table-column label="操作" width="180">
            <template #default="scope">
              <el-button size="small" type="primary" @click="goOverhaulTask(scope.row.id)">进入工单</el-button>
              <el-button size="small" type="danger" @click="removeOverhaul(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>

      <!-- 维保信息已从配电箱详情移除，改为工单主单维护 -->

    </el-tabs>

    <el-dialog v-model="boxEditDialog" title="编辑配电箱基础信息" width="700px">
      <el-form :model="boxEditForm" label-width="150px" class="compact-form">
        <el-form-item label="台账号">
          <el-input v-model="boxEditForm.boxId" disabled style="width: 430px" />
        </el-form-item>
        <el-form-item label="配电箱编号">
          <el-input v-model="boxEditForm.boxNo" style="width: 430px" />
        </el-form-item>
        <el-form-item label="车间">
          <el-select v-model="boxEditForm.station" style="width: 430px" filterable @change="onEditStationChange">
            <el-option v-for="opt in stationOptions" :key="opt" :label="opt" :value="opt" />
          </el-select>
        </el-form-item>
        <el-form-item label="工区">
          <el-select v-model="boxEditForm.area" style="width: 430px" filterable>
            <el-option v-for="opt in editAreaOptions" :key="opt" :label="opt" :value="opt" />
          </el-select>
        </el-form-item>
        <el-form-item label="安装地点">
          <el-input v-model="boxEditForm.boxAddress" style="width: 430px" />
        </el-form-item>
        <el-form-item label="规格">
          <el-input v-model="boxEditForm.size" style="width: 430px" />
        </el-form-item>
        <el-form-item label="明装暗装">
          <el-select v-model="boxEditForm.pileType" style="width: 430px" clearable>
            <el-option label="明装" value="明装" />
            <el-option label="暗装" value="暗装" />
          </el-select>
        </el-form-item>
        <el-form-item label="室内室外">
          <el-select v-model="boxEditForm.indoorOutdoor" style="width: 430px" clearable>
            <el-option label="室内" value="室内" />
            <el-option label="室外" value="室外" />
          </el-select>
        </el-form-item>
        <el-form-item label="进线来源及规格">
          <el-input v-model="boxEditForm.incomingSource" style="width: 430px" />
        </el-form-item>
        <el-form-item label="是否与其它单位共用">
          <el-select v-model="boxEditForm.sharedWithOthers" style="width: 430px" clearable @change="onEditSharedWithOthersChange">
            <el-option label="是" value="是" />
            <el-option label="否" value="否" />
          </el-select>
        </el-form-item>
        <el-form-item label="共用范围">
          <el-input v-model="boxEditForm.sharedScope" :disabled="boxEditForm.sharedWithOthers !== '是'" placeholder="选择是后必填" style="width: 430px" />
        </el-form-item>
        <el-form-item label="是否有大功率电器">
          <el-select v-model="boxEditForm.highPowerAppliance" style="width: 430px" clearable @change="onEditHighPowerChange">
            <el-option label="是" value="是" />
            <el-option label="否" value="否" />
          </el-select>
        </el-form-item>
        <el-form-item label="大功率电器名称">
          <el-input v-model="boxEditForm.highPowerName" :disabled="boxEditForm.highPowerAppliance !== '是'" placeholder="选择是后必填" style="width: 430px" />
        </el-form-item>
        <el-form-item label="系统图">
          <ImageUpload v-model="boxImageForm.systemUrl" />
        </el-form-item>
        <el-form-item label="图片1">
          <ImageUpload v-model="boxImageForm.firstUrl" />
        </el-form-item>
        <el-form-item label="图片2">
          <ImageUpload v-model="boxImageForm.secondUrl" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="boxEditDialog = false">取消</el-button>
        <el-button type="primary" @click="saveBoxBaseInfo">保存</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="circuitDialog" title="回路" width="900px">
      <el-form :model="circuitForm" label-width="110px">
        <el-form-item label="供电回路"><el-input v-model="circuitForm.supplyCircuit" /></el-form-item>
        <el-form-item label="启动电流"><el-input v-model="circuitForm.startCurrent" /></el-form-item>
        <el-form-item label="运行电流"><el-input v-model="circuitForm.runCurrent" /></el-form-item>
        <el-form-item label="功率"><el-input v-model="circuitForm.power" /></el-form-item>
        <el-form-item label="用电设备"><el-input v-model="circuitForm.electricDevice" /></el-form-item>
        <el-form-item label="设备地点"><el-input v-model="circuitForm.deviceLocation" type="textarea" :rows="2" /></el-form-item>
        <el-form-item label="备注"><el-input v-model="circuitForm.remark" type="textarea" :rows="2" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="circuitDialog = false">取消</el-button>
        <el-button type="primary" @click="saveCircuit">保存</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="overhaulDialog" title="新增检修记录" width="760px">
      <el-form :model="overhaulForm" label-width="110px">
        <el-form-item label="任务单号"><el-input v-model="overhaulForm.taskNo" placeholder="可空自动生成" /></el-form-item>
        <el-form-item label="报修单位"><el-input v-model="overhaulForm.reportUnit" /></el-form-item>
        <el-form-item label="报修时间"><el-date-picker v-model="overhaulForm.reportTime" type="date" value-format="YYYY-MM-DD" style="width:100%" /></el-form-item>
        <el-form-item label="报修人"><el-input v-model="overhaulForm.reportUser" /></el-form-item>
        <el-form-item label="报修接受人"><el-input v-model="overhaulForm.acceptUser" /></el-form-item>
        <el-form-item label="盯控人员"><el-input v-model="overhaulForm.supervisionUser" /></el-form-item>
        <el-form-item label="抢修人员"><el-input v-model="overhaulForm.rescueUsers" /></el-form-item>
        <el-form-item label="故障现象"><el-input v-model="overhaulForm.faultPhenomenon" type="textarea" :rows="2" /></el-form-item>
        <el-form-item label="故障原因"><el-input v-model="overhaulForm.faultReason" type="textarea" :rows="2" /></el-form-item>
        <el-form-item label="抢修情况"><el-input v-model="overhaulForm.rescueSituation" type="textarea" :rows="3" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="overhaulDialog=false">取消</el-button>
        <el-button type="primary" @click="saveOverhaul">保存</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="componentDialog" title="元器件" width="700px">
      <EntityForm v-model="componentForm" :fields="componentFields" />
      <template #footer>
        <el-button @click="componentDialog = false">取消</el-button>
        <el-button type="primary" @click="saveComponent">保存</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="inspectionDialog" title="维保记录" width="760px">
      <EntityForm v-model="inspectionForm" :fields="inspectionFields" />
      <template #footer>
        <el-button @click="inspectionDialog = false">取消</el-button>
        <el-button type="primary" @click="saveInspection">保存</el-button>
      </template>
    </el-dialog>

  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, reactive, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import http from '@/api/http'
import { confirmDeleteAction } from '@/utils/confirmDeleteAction'
import EntityForm from '@/components/EntityForm.vue'
import ImageUpload from '@/components/ImageUpload.vue'
import PreviewImage from '@/components/PreviewImage.vue'
import { normalizeImageField, resolvePreviewUrl } from '@/utils/image'

const route = useRoute()
const router = useRouter()

type TreeNode = {
  name: string
  children?: TreeNode[]
}

const locationTree = ref<TreeNode[]>([])
const stationOptions = computed(() => locationTree.value.map((item) => item.name))
const getAreaOptions = (stationName: string) => {
  const station = locationTree.value.find((item) => item.name === stationName)
  return (station?.children || []).map((item) => item.name)
}

const box = reactive<any>({})
const components = ref<any[]>([])
const inspections = ref<any[]>([])
const circuits = ref<any[]>([])
const overhaulTasks = ref<any[]>([])
const allBoxOptions = ref<{ label: string; value: number }[]>([])

const componentDialog = ref(false)
const inspectionDialog = ref(false)
const boxEditDialog = ref(false)
const circuitDialog = ref(false)
const overhaulDialog = ref(false)

const componentForm = reactive<any>({})
const boxEditForm = reactive<any>({
  boxId: '',
  boxNo: '',
  station: '',
  area: '',
  boxAddress: '',
  size: '',
  pileType: '',
  indoorOutdoor: '',
  incomingSource: '',
  sharedWithOthers: '否',
  sharedScope: '',
  highPowerAppliance: '否',
  highPowerName: ''
})
const editAreaOptions = computed(() => getAreaOptions(boxEditForm.station || ''))
const inspectionForm = reactive<any>({ boxIds: [] })
const circuitForm = reactive<any>({})
const overhaulForm = reactive<any>({})
const boxImageForm = reactive<any>({
  systemUrl: '',
  firstUrl: '',
  secondUrl: '',
})
let currentLoadToken = 0

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
  { key: 'inspectionUser', label: '维保人' },
  { key: 'guardianUser', label: '监护人' },
  { key: 'inspectionTime', label: '维保时间', type: 'datetime' },
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
  { key: 'appearanceUrl', label: '外观图', type: 'image' },
  { key: 'firstUrl', label: '图片1', type: 'image' },
  { key: 'secondUrl', label: '图片2', type: 'image' },
  { key: 'thirdUrl', label: '图片3', type: 'image' },
  { key: 'fourthUrl', label: '图片4', type: 'image' },
  { key: 'fifthUrl', label: '图片5', type: 'image' },
  { key: 'earlierUrl', label: '前期', type: 'image' },
  { key: 'laterUrl', label: '后期', type: 'image' },
  { key: 'remark', label: '备注', type: 'textarea' }
])

const goBack = () => router.push('/box')

const toDisplay = (value: unknown) => {
  if (value === null || value === undefined) return '-'
  if (typeof value === 'string' && !value.trim()) return '-'
  return String(value)
}

const unwrapPayload = (payload: any) => {
  if (payload && typeof payload === 'object' && 'data' in payload) {
    return payload.data
  }
  return payload
}

const safeArray = (value: any): any[] => (Array.isArray(value) ? value : [])

// image utils moved to @/utils/image

const syncBoxImageForm = () => {
  boxImageForm.systemUrl = normalizeImageField(box.systemUrl)
  boxImageForm.firstUrl = normalizeImageField(box.firstUrl)
  boxImageForm.secondUrl = normalizeImageField(box.secondUrl)
}

const refreshBoxOptions = () => {
  inspectionFields.value = inspectionFields.value.map((item) =>
    item.key === 'boxIds' ? { ...item, options: allBoxOptions.value } : item
  )
}

const loadBoxOptions = async () => {
  const res = await http.get('/box/page', { params: { pageNum: 1, pageSize: 1000 } })
  const pageData = unwrapPayload(res.data) || {}
  const rows = safeArray(pageData.records)
  allBoxOptions.value = rows.map((row: any) => ({
    label: `${toDisplay(row.id)} / ${toDisplay(row.boxId)} / ${toDisplay(row.boxAddress)}`,
    value: row.id
  }))
  refreshBoxOptions()
}

const loadLocations = async () => {
  try {
    const res = await http.get('/location/tree')
    locationTree.value = res.data || []
  } catch {
    const res = await http.get('/box/page', { params: { pageNum: 1, pageSize: 1000 } })
    const rows = safeArray((res.data || {}).records)
    const map = new Map<string, Set<string>>()
    rows.forEach((row: any) => {
      const station = row.station || ''
      const area = row.area || ''
      if (!station || !area) return
      if (!map.has(station)) map.set(station, new Set())
      map.get(station)!.add(area)
    })
    locationTree.value = Array.from(map.entries()).map(([station, areaSet]) => ({
      name: station,
      children: Array.from(areaSet).map((area) => ({ name: area }))
    }))
  }
}

const onEditStationChange = () => {
  if (!editAreaOptions.value.includes(boxEditForm.area)) {
    boxEditForm.area = ''
  }
}

const onEditSharedWithOthersChange = () => {
  if (boxEditForm.sharedWithOthers !== '是') boxEditForm.sharedScope = ''
}

const onEditHighPowerChange = () => {
  if (boxEditForm.highPowerAppliance !== '是') boxEditForm.highPowerName = ''
}

const load = async () => {
  const id = Number(route.params.id)
  if (!id) {
    Object.keys(box).forEach((k) => delete box[k])
    components.value = []
    inspections.value = []
    syncBoxImageForm()
    return
  }

  const token = ++currentLoadToken
  Object.keys(box).forEach((k) => delete box[k])

  const [boxRes, compRes, circuitRes, overhaulRes] = await Promise.allSettled([
    http.get(`/box/${id}`),
    http.get(`/components/${id}`),
    http.get(`/box-circuit/${id}`),
    http.get(`/overhaul-task/page`, { params: { pageNum: 1, pageSize: 1000, boxId: id } })
  ])

  if (token !== currentLoadToken) return

  const boxData = boxRes.status === 'fulfilled' ? unwrapPayload(boxRes.value.data) : {}
  Object.assign(box, boxData || {})
  syncBoxImageForm()

  if (compRes.status === 'fulfilled') {
    const compData = unwrapPayload(compRes.value.data)
    components.value = safeArray(compData)
  } else {
    components.value = []
  }

  if (circuitRes.status === 'fulfilled') {
    const circuitData = unwrapPayload(circuitRes.value.data)
    circuits.value = safeArray(circuitData)
  } else {
    circuits.value = []
  }

  if (overhaulRes.status === 'fulfilled') {
    const pageData = overhaulRes.value.data?.data || {}
    overhaulTasks.value = pageData.records || []
  } else {
    overhaulTasks.value = []
  }

  inspections.value = []

}

const openBoxEditDialog = () => {
  boxEditForm.boxId = box.boxId || ''
  boxEditForm.boxNo = box.boxNo || ''
  boxEditForm.station = box.station || ''
  boxEditForm.area = box.area || ''
  boxEditForm.boxAddress = box.boxAddress || ''
  boxEditForm.size = box.size || ''
  boxEditForm.pileType = box.pileType || ''
  boxEditForm.indoorOutdoor = box.indoorOutdoor || ''
  boxEditForm.incomingSource = box.incomingSource || ''
  boxEditForm.sharedWithOthers = box.sharedWithOthers || '否'
  boxEditForm.sharedScope = box.sharedScope || ''
  boxEditForm.highPowerAppliance = box.highPowerAppliance || '否'
  boxEditForm.highPowerName = box.highPowerName || ''
  boxEditDialog.value = true
}

const saveBoxBaseInfo = async () => {
  if (!box.id) return
  if (boxEditForm.sharedWithOthers === '是' && !String(boxEditForm.sharedScope || '').trim()) {
    ElMessage.error('选择与其它单位共用后，必须填写共用范围')
    return
  }
  if (boxEditForm.highPowerAppliance === '是' && !String(boxEditForm.highPowerName || '').trim()) {
    ElMessage.error('选择为大功率电器后，必须填写大功率电器名称')
    return
  }
  const payload = {
    ...box,
    id: box.id,
    boxId: box.boxId,
    boxNo: boxEditForm.boxNo,
    station: boxEditForm.station,
    area: boxEditForm.area,
    boxAddress: boxEditForm.boxAddress,
    size: boxEditForm.size,
    pileType: boxEditForm.pileType,
    indoorOutdoor: boxEditForm.indoorOutdoor,
    incomingSource: boxEditForm.incomingSource,
    sharedWithOthers: boxEditForm.sharedWithOthers,
    sharedScope: boxEditForm.sharedWithOthers === '是' ? boxEditForm.sharedScope : '',
    highPowerAppliance: boxEditForm.highPowerAppliance,
    highPowerName: boxEditForm.highPowerAppliance === '是' ? boxEditForm.highPowerName : '',
    systemUrl: normalizeImageField(boxImageForm.systemUrl),
    firstUrl: normalizeImageField(boxImageForm.firstUrl),
    secondUrl: normalizeImageField(boxImageForm.secondUrl),
  }
  try {
    await http.post('/box/save', payload)
    boxEditDialog.value = false
    await load()
    ElMessage.success('基础信息保存成功')
  } catch (error: any) {
    ElMessage.error(error?.response?.data?.msg || '基础信息保存失败')
  }
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
  if (!(await confirmDeleteAction())) return
  await http.delete(`/components/${id}`)
  load()
}


const openCircuitDialog = () => {
  Object.keys(circuitForm).forEach((k) => delete circuitForm[k])
  circuitDialog.value = true
}

const editCircuit = (row: any) => {
  Object.keys(circuitForm).forEach((k) => delete circuitForm[k])
  Object.assign(circuitForm, row)
  circuitDialog.value = true
}

const saveCircuit = async () => {
  if (!box.id) return
  await http.post('/box-circuit/save', {
    ...circuitForm,
    boxId: box.id
  })
  circuitDialog.value = false
  await load()
  ElMessage.success('回路保存成功')
}

const removeCircuit = async (id: number) => {
  if (!(await confirmDeleteAction())) return
  await http.delete(`/box-circuit/${id}`)
  await load()
}


const openOverhaulDialog = () => {
  Object.keys(overhaulForm).forEach((k) => delete overhaulForm[k])
  Object.assign(overhaulForm, { boxId: box.id })
  overhaulDialog.value = true
}

const saveOverhaul = async () => {
  if (!box.id) return
  await http.post('/overhaul-task/save', { ...overhaulForm, boxId: box.id })
  overhaulDialog.value = false
  await load()
  ElMessage.success('检修记录保存成功')
}

const removeOverhaul = async (id: number) => {
  if (!(await confirmDeleteAction('确认删除该检修记录？'))) return
  await http.delete(`/overhaul-task/${id}`)
  await load()
}

const goOverhaulTask = (id: number) => {
  if (!id) return
  router.push(`/overhaul-task/${id}`)
}

const openInspectionDialog = () => {
  try {
    Object.keys(inspectionForm).forEach((k) => delete inspectionForm[k])
    Object.assign(inspectionForm, { boxIds: box.id ? [box.id] : [] })
    inspectionDialog.value = true
  } catch (error: any) {
    ElMessage.error(error?.message || '打开维保弹窗失败')
  }
}

const editInspection = async (row: any) => {
  const detail = await http.get(`/maintenance-task/${row.taskId}`)
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
  try {
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
    await http.post('/maintenance-task/save', {
      id: inspectionForm.id,
      taskNo: inspectionForm.taskNo,
      inspectionUser: inspectionForm.inspectionUser,
      guardianUser: inspectionForm.guardianUser,
      inspectionTime: inspectionForm.inspectionTime,
      remark: inspectionForm.remark,
      items: boxIds.map((boxId) => ({ boxId, ...itemTemplate }))
    })
    inspectionDialog.value = false
    await load()
    ElMessage.success('维保任务保存成功')
  } catch (error: any) {
    ElMessage.error(error?.message || '维保任务保存失败')
  }
}

const removeInspection = async (id: number) => {
  if (!(await confirmDeleteAction())) return
  await http.delete(`/maintenance-task/${id}`)
  load()
}

const goMaintenanceTask = (taskId: number) => {
  if (!taskId) return
  router.push(`/maintenance-task/${taskId}`)
}

onMounted(async () => {
  await loadLocations()
  await loadBoxOptions()
  await load()
})

watch(
  () => route.params.id,
  async () => {
    await load()
  }
)
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


/* image edit section moved into base info dialog */
.compact-form :deep(.el-form-item__label) {
  white-space: nowrap;
}
</style>
