<template>
  <div>
    <div class="toolbar">
      <el-select v-model="filters.station" placeholder="车间" class="field" clearable filterable @change="onFilterStationChange">
        <el-option v-for="opt in stationOptions" :key="opt" :label="opt" :value="opt" />
      </el-select>
      <el-select v-model="filters.area" placeholder="工区" class="field" clearable filterable @change="onFilterAreaChange">
        <el-option v-for="opt in filterAreaOptions" :key="opt" :label="opt" :value="opt" />
      </el-select>
      <el-select v-model="filters.address" placeholder="安装地点" class="field" clearable filterable>
        <el-option v-for="opt in filterAddressOptions" :key="opt" :label="opt" :value="opt" />
      </el-select>
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

    <el-dialog v-model="dialogVisible" title="配电箱" width="700px">
      <el-form :model="form" label-width="90px">
        <el-form-item label="台帐号">
          <el-input v-model="form.boxId" />
        </el-form-item>
        <el-form-item label="车间">
          <el-select v-model="form.station" style="width: 100%" filterable @change="onFormStationChange">
            <el-option v-for="opt in stationOptions" :key="opt" :label="opt" :value="opt" />
          </el-select>
        </el-form-item>
        <el-form-item label="工区">
          <el-select v-model="form.area" style="width: 100%" filterable @change="onFormAreaChange">
            <el-option v-for="opt in formAreaOptions" :key="opt" :label="opt" :value="opt" />
          </el-select>
        </el-form-item>
        <el-form-item label="安装地点">
          <el-select v-model="form.boxAddress" style="width: 100%" filterable allow-create default-first-option>
            <el-option v-for="opt in formAddressOptions" :key="opt" :label="opt" :value="opt" />
          </el-select>
        </el-form-item>
        <el-form-item label="规格">
          <el-input v-model="form.size" />
        </el-form-item>
        <el-form-item label="系统图">
          <el-input v-model="form.systemUrl" />
        </el-form-item>
        <el-form-item label="图片1">
          <el-input v-model="form.firstUrl" />
        </el-form-item>
        <el-form-item label="图片2">
          <el-input v-model="form.secondUrl" />
        </el-form-item>
        <el-form-item label="图片3">
          <el-input v-model="form.thirdUrl" />
        </el-form-item>
        <el-form-item label="图片4">
          <el-input v-model="form.fourthUrl" />
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
import { ElMessage } from 'element-plus'
import http from '@/api/http'

type TreeNode = {
  name: string
  children?: TreeNode[]
}

const filters = reactive({ station: '', area: '', address: '' })
const tableData = ref<any[]>([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const dialogVisible = ref(false)
const form = reactive<any>({})
const locationTree = ref<TreeNode[]>([])

const stationOptions = computed(() => locationTree.value.map((item) => item.name))

const getAreaOptions = (stationName: string) => {
  const station = locationTree.value.find((item) => item.name === stationName)
  return (station?.children || []).map((item) => item.name)
}

const getAddressOptions = (stationName: string, areaName: string) => {
  const station = locationTree.value.find((item) => item.name === stationName)
  const area = (station?.children || []).find((item) => item.name === areaName)
  return (area?.children || []).map((item) => item.name)
}

const filterAreaOptions = computed(() => getAreaOptions(filters.station))
const filterAddressOptions = computed(() => getAddressOptions(filters.station, filters.area))
const formAreaOptions = computed(() => getAreaOptions(form.station || ''))
const formAddressOptions = computed(() => getAddressOptions(form.station || '', form.area || ''))

const loadLocations = async () => {
  try {
    const res = await http.get('/location/tree')
    locationTree.value = res.data || []
  } catch {
    // 后端 location 接口未就绪时，降级为从现有配电箱数据推导联动选项，避免登录后被401链路影响
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
  filters.station = ''
  filters.area = ''
  filters.address = ''
  load()
}

const onFilterStationChange = () => {
  if (!filterAreaOptions.value.includes(filters.area)) {
    filters.area = ''
  }
  if (!filterAddressOptions.value.includes(filters.address)) {
    filters.address = ''
  }
}

const onFilterAreaChange = () => {
  if (!filterAddressOptions.value.includes(filters.address)) {
    filters.address = ''
  }
}

const openDialog = () => {
  Object.keys(form).forEach((k) => delete form[k])
  dialogVisible.value = true
}

const edit = (row: any) => {
  Object.keys(form).forEach((k) => delete form[k])
  Object.assign(form, row)
  dialogVisible.value = true
}

const onFormStationChange = () => {
  if (!formAreaOptions.value.includes(form.area)) {
    form.area = ''
  }
  if (!formAddressOptions.value.includes(form.boxAddress)) {
    form.boxAddress = ''
  }
}

const onFormAreaChange = () => {
  if (!formAddressOptions.value.includes(form.boxAddress)) {
    form.boxAddress = ''
  }
}

const save = async () => {
  if (!form.boxId || !String(form.boxId).trim()) {
    ElMessage.error('请输入台帐号')
    return
  }
  if (!form.station || !form.area || !form.boxAddress) {
    ElMessage.error('请选择车间、工区和安装地点')
    return
  }
  await http.post('/box/save', form)
  dialogVisible.value = false
  load()
}

const remove = async (id: number) => {
  await http.delete(`/box/${id}`)
  load()
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
</style>
