<template>
  <div>
    <div class="toolbar">
      <el-select v-model="filters.level" placeholder="层级" class="field" clearable>
        <el-option label="车间" :value="1" />
        <el-option label="工区" :value="2" />
        <el-option label="安装地点" :value="3" />
      </el-select>
      <el-input v-model="filters.keyword" placeholder="名称关键字" class="field" />
      <el-button type="primary" @click="load">搜索</el-button>
      <el-button @click="reset">重置</el-button>
      <el-button type="success" @click="openDialog">新增</el-button>
    </div>

    <el-table :data="tableData" border>
      <el-table-column prop="station" label="车间" min-width="180" />
      <el-table-column prop="area" label="工区" min-width="180" />
      <el-table-column prop="address" label="安装地点" min-width="180" />
      <el-table-column prop="level" label="层级" width="100">
        <template #default="scope">
          {{ levelLabel(scope.row.level) }}
        </template>
      </el-table-column>
      <el-table-column prop="name" label="节点名称" min-width="180" />
      <el-table-column prop="path" label="完整路径" min-width="280" />
      <el-table-column prop="sortNum" label="排序" width="100" />
      <el-table-column label="操作" width="160">
        <template #default="scope">
          <el-button size="small" @click="edit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="remove(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" title="地点配置" width="520px">
      <el-form :model="form" label-width="90px">
        <el-form-item label="层级">
          <el-select v-model="form.level" style="width: 100%" @change="onLevelChange">
            <el-option label="车间" :value="1" />
            <el-option label="工区" :value="2" />
            <el-option label="安装地点" :value="3" />
          </el-select>
        </el-form-item>

        <el-form-item v-if="form.level === 2" label="所属车间">
          <el-select v-model="form.parentId" style="width: 100%" filterable>
            <el-option v-for="item in stationParents" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>

        <template v-if="form.level === 3">
          <el-form-item label="所属车间">
            <el-select v-model="form.stationParentId" style="width: 100%" filterable @change="onStationParentChange">
              <el-option v-for="item in stationParents" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>
          </el-form-item>
          <el-form-item label="所属工区">
            <el-select v-model="form.parentId" style="width: 100%" filterable>
              <el-option v-for="item in areaParents" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>
          </el-form-item>
        </template>

        <el-form-item label="名称">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.sortNum" :min="0" />
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
import { computed, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import http from '@/api/http'

type TreeNode = {
  id: number
  name: string
  level: number
  children?: TreeNode[]
}

const filters = reactive({ level: undefined as number | undefined, keyword: '' })
const allRows = ref<any[]>([])
const tableData = ref<any[]>([])
const treeData = ref<TreeNode[]>([])
const dialogVisible = ref(false)

const form = reactive<any>({
  id: undefined,
  level: 1,
  parentId: 0,
  stationParentId: undefined,
  name: '',
  sortNum: 0
})

const stationParents = computed(() => treeData.value.map((item) => ({ id: item.id, name: item.name })))

const areaParents = computed(() => {
  if (!form.stationParentId) {
    return []
  }
  const station = treeData.value.find((item) => item.id === form.stationParentId)
  return (station?.children || []).map((item) => ({ id: item.id, name: item.name }))
})

const levelLabel = (level: number) => {
  if (level === 1) return '车间'
  if (level === 2) return '工区'
  if (level === 3) return '安装地点'
  return ''
}

const applyFilter = () => {
  tableData.value = allRows.value.filter((item) => {
    if (filters.level && item.level !== filters.level) {
      return false
    }
    if (filters.keyword && !String(item.name || '').includes(filters.keyword)) {
      return false
    }
    return true
  })
}

const load = async () => {
  const [listRes, treeRes] = await Promise.all([http.get('/location/list'), http.get('/location/tree')])
  allRows.value = listRes.data || []
  treeData.value = treeRes.data || []
  applyFilter()
}

const reset = () => {
  filters.level = undefined
  filters.keyword = ''
  applyFilter()
}

const onLevelChange = () => {
  if (form.level === 1) {
    form.parentId = 0
    form.stationParentId = undefined
  } else if (form.level === 2) {
    form.parentId = undefined
    form.stationParentId = undefined
  } else {
    form.parentId = undefined
    form.stationParentId = undefined
  }
}

const onStationParentChange = () => {
  form.parentId = undefined
}

const openDialog = () => {
  form.id = undefined
  form.level = 1
  form.parentId = 0
  form.stationParentId = undefined
  form.name = ''
  form.sortNum = 0
  dialogVisible.value = true
}

const edit = (row: any) => {
  form.id = row.id
  form.level = row.level
  form.name = row.name
  form.sortNum = row.sortNum || 0

  if (row.level === 1) {
    form.parentId = 0
    form.stationParentId = undefined
  } else if (row.level === 2) {
    const station = treeData.value.find((item) => item.name === row.station)
    form.parentId = station?.id
    form.stationParentId = undefined
  } else {
    const station = treeData.value.find((item) => item.name === row.station)
    form.stationParentId = station?.id
    const area = (station?.children || []).find((item) => item.name === row.area)
    form.parentId = area?.id
  }

  dialogVisible.value = true
}

const save = async () => {
  if (!form.name || !String(form.name).trim()) {
    ElMessage.error('请输入名称')
    return
  }

  if (form.level !== 1 && !form.parentId) {
    ElMessage.error('请选择上级节点')
    return
  }

  await http.post('/location/save', {
    id: form.id,
    level: form.level,
    parentId: form.level === 1 ? 0 : form.parentId,
    name: String(form.name).trim(),
    sortNum: form.sortNum || 0
  })

  dialogVisible.value = false
  await load()
}

const remove = async (id: number) => {
  await http.delete(`/location/${id}`)
  await load()
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
</style>
