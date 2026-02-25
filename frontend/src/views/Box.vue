<template>
  <div>
    <div class="toolbar">
      <el-input v-model="filters.boxId" placeholder="台账号" class="field" clearable />
      <el-select v-model="filters.station" placeholder="车间" class="field" clearable filterable @change="onFilterStationChange">
        <el-option v-for="opt in stationOptions" :key="opt" :label="opt" :value="opt" />
      </el-select>
      <el-select v-model="filters.area" placeholder="工区" class="field" clearable filterable @change="onFilterAreaChange">
        <el-option v-for="opt in filterAreaOptions" :key="opt" :label="opt" :value="opt" />
      </el-select>
      <el-input v-model="filters.address" placeholder="安装地点" class="field" clearable />
      <el-button type="primary" @click="load">搜索</el-button>
      <el-button @click="reset">重置</el-button>
      <el-button type="success" @click="openDialog">新增</el-button>
    </div>

    <el-table :data="tableData" border>
      <el-table-column prop="boxId" label="台账号" />
      <el-table-column prop="boxNo" label="配电箱编号" />
      <el-table-column prop="station" label="车间" />
      <el-table-column prop="area" label="工区" />
      <el-table-column prop="boxAddress" label="安装地点" />
      <el-table-column prop="size" label="规格" />
      <el-table-column label="操作" width="340">
        <template #default="scope">
          <el-button size="small" type="danger" @click="remove(scope.row.id)">删除</el-button>
          <el-button size="small" type="primary" @click="print(scope.row.id)">打印</el-button>
          <el-button size="small" type="success" @click="viewDetail(scope.row)">查看详细信息</el-button>
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

    <el-dialog v-model="dialogVisible" title="配电箱" width="700px">
      <el-form :model="form" label-width="150px" class="compact-form">
        <el-form-item label="*台账号">
          <el-input v-model="form.boxId" style="width: 430px" />
        </el-form-item>
        <el-form-item label="配电箱编号">
          <el-input v-model="form.boxNo" style="width: 430px" />
        </el-form-item>
        <el-form-item label="车间">
          <el-select v-model="form.station" style="width: 430px" filterable @change="onFormStationChange">
            <el-option v-for="opt in stationOptions" :key="opt" :label="opt" :value="opt" />
          </el-select>
        </el-form-item>
        <el-form-item label="工区">
          <el-select v-model="form.area" style="width: 430px" filterable @change="onFormAreaChange">
            <el-option v-for="opt in formAreaOptions" :key="opt" :label="opt" :value="opt" />
          </el-select>
        </el-form-item>
        <el-form-item label="安装地点">
          <el-input v-model="form.boxAddress" style="width: 430px" />
        </el-form-item>
        <el-form-item label="规格">
          <el-input v-model="form.size" style="width: 430px" />
        </el-form-item>
        <el-form-item label="明装暗装">
          <el-select v-model="form.pileType" style="width: 430px" clearable>
            <el-option label="明装" value="明装" />
            <el-option label="暗装" value="暗装" />
          </el-select>
        </el-form-item>
        <el-form-item label="室内室外">
          <el-select v-model="form.indoorOutdoor" style="width: 430px" clearable>
            <el-option label="室内" value="室内" />
            <el-option label="室外" value="室外" />
          </el-select>
        </el-form-item>
        <el-form-item label="是否与其它单位共用">
          <el-select v-model="form.sharedWithOthers" style="width: 430px" clearable @change="onSharedWithOthersChange">
            <el-option label="是" value="是" />
            <el-option label="否" value="否" />
          </el-select>
        </el-form-item>
        <el-form-item label="共用范围">
          <el-input v-model="form.sharedScope" :disabled="form.sharedWithOthers !== '是'" placeholder="选择是后必填" style="width: 430px" />
        </el-form-item>
        <el-form-item label="是否有大功率电器">
          <el-select v-model="form.highPowerAppliance" style="width: 430px" clearable @change="onHighPowerChange">
            <el-option label="是" value="是" />
            <el-option label="否" value="否" />
          </el-select>
        </el-form-item>
        <el-form-item label="大功率电器名称">
          <el-input v-model="form.highPowerName" :disabled="form.highPowerAppliance !== '是'" placeholder="选择是后必填" style="width: 430px" />
        </el-form-item>
        <el-form-item label="系统图">
          <ImageUpload v-model="form.systemUrl" />
        </el-form-item>
        <el-form-item label="图片1">
          <ImageUpload v-model="form.firstUrl" />
        </el-form-item>
        <el-form-item label="图片2">
          <ImageUpload v-model="form.secondUrl" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import http from '@/api/http'
import { confirmDeleteAction } from '@/utils/confirmDeleteAction'
import ImageUpload from '@/components/ImageUpload.vue'

type TreeNode = {
  name: string
  children?: TreeNode[]
}

const filters = reactive({ boxId: '', station: '', area: '', address: '' })
const tableData = ref<any[]>([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const dialogVisible = ref(false)
const form = reactive<any>({})

const initForm = () => {
  Object.assign(form, {
    id: undefined,
    boxId: '',
    boxNo: '',
    station: '',
    area: '',
    boxAddress: '',
    size: '',
    pileType: '',
    indoorOutdoor: '',
    sharedWithOthers: '否',
    sharedScope: '',
    highPowerAppliance: '否',
    highPowerName: '',
    systemUrl: '',
    firstUrl: '',
    secondUrl: ''
  })
}
const locationTree = ref<TreeNode[]>([])
const router = useRouter()

const stationOptions = computed(() => locationTree.value.map((item) => item.name))

const getAreaOptions = (stationName: string) => {
  const station = locationTree.value.find((item) => item.name === stationName)
  return (station?.children || []).map((item) => item.name)
}

const filterAreaOptions = computed(() => getAreaOptions(filters.station))
const formAreaOptions = computed(() => getAreaOptions(form.station || ''))

const loadLocations = async () => {
  try {
    const res = await http.get('/location/tree')
    locationTree.value = res.data || []
  } catch {
    const res = await http.get('/box/page', { params: { pageNum: 1, pageSize: 1000 } })
    const records = res.data?.records || []
    const map = new Map<string, Map<string, Set<string>>>()
    records.forEach((row: any) => {
      const station = row.station || ''
      const area = row.area || ''
      const address = row.boxAddress || ''
      if (!station || !area || !address) return
      if (!map.has(station)) map.set(station, new Map())
      const areaMap = map.get(station)!
      if (!areaMap.has(area)) areaMap.set(area, new Set())
      areaMap.get(area)!.add(address)
    })

    locationTree.value = Array.from(map.entries()).map(([station, areaMap]) => ({
      name: station,
      children: Array.from(areaMap.entries()).map(([area, addresses]) => ({
        name: area,
        children: Array.from(addresses).map((address) => ({ name: address }))
      }))
    }))
  }
}

const load = async () => {
  const res = await http.get('/box/page', {
    params: {
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      boxId: (filters.boxId || "").trim(),
      boxAccount: (filters.boxId || "").trim(),
      station: filters.station,
      area: filters.area,
      address: filters.address
    }
  })
  const data = res.data
  tableData.value = data.records || []
  total.value = data.total || 0
}

const reset = () => {
  filters.boxId = ''
  filters.station = ''
  filters.area = ''
  filters.address = ''
  load()
}

const onFilterStationChange = () => {
  if (!filterAreaOptions.value.includes(filters.area)) {
    filters.area = ''
  }
}

const onFilterAreaChange = () => {}

const openDialog = () => {
  initForm()
  dialogVisible.value = true
}

const onFormStationChange = () => {
  if (!form.station) {
    form.area = ''
    return
  }
  if (!formAreaOptions.value.includes(form.area)) {
    form.area = ''
  }
}

const onFormAreaChange = () => {}

const onSharedWithOthersChange = () => {
  if (form.sharedWithOthers !== "是") form.sharedScope = ""
}

const onHighPowerChange = () => {
  if (form.highPowerAppliance !== "是") form.highPowerName = ""
}

const save = async () => {
  if (!form.boxId || !String(form.boxId).trim()) {
    ElMessage.error('请输入台帐号')
    return
  }
  if (form.sharedWithOthers === '是' && !String(form.sharedScope || '').trim()) {
    ElMessage.error('选择与其它单位共用后，必须填写共用范围')
    return
  }
  if (form.highPowerAppliance === '是' && !String(form.highPowerName || '').trim()) {
    ElMessage.error('选择为大功率电器后，必须填写大功率电器名称')
    return
  }
  const payload = {
    id: form.id,
    boxId: form.boxId,
    boxNo: form.boxNo,
    station: form.station,
    area: form.area,
    boxAddress: form.boxAddress,
    size: form.size,
    pileType: form.pileType,
    indoorOutdoor: form.indoorOutdoor,
    sharedWithOthers: form.sharedWithOthers,
    sharedScope: form.sharedWithOthers === '是' ? form.sharedScope : '',
    highPowerAppliance: form.highPowerAppliance,
    highPowerName: form.highPowerAppliance === '是' ? form.highPowerName : '',
    systemUrl: form.systemUrl,
    firstUrl: form.firstUrl,
    secondUrl: form.secondUrl
  }
  try {
    await http.post('/box/save', payload)
    dialogVisible.value = false
    await load()
    ElMessage.success('保存成功')
  } catch (error: any) {
    ElMessage.error(error?.response?.data?.msg || '保存失败')
  }
}

const remove = async (id: number) => {
  if (!(await confirmDeleteAction())) return
  await http.delete(`/box/${id}`)
  load()
}

const viewDetail = (row: any) => {
  router.push({
    path: `/box-detail/${row.id}`,
    query: { boxId: row.boxId || '' }
  })
}

const print = (id: number) => {
  window.open(`/api/box/print/${id}`, '_blank')
}

const onSizeChange = (size: number) => {
  pageSize.value = size
  load()
}

const onCurrentChange = (page: number) => {
  pageNum.value = page
  load()
}

onMounted(async () => {
  await loadLocations()
  await load()
})
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
.compact-form :deep(.el-form-item__label) {
  white-space: nowrap;
}
</style>
