<template>
  <div>
    <div class="toolbar">
      <el-button @click="goBack">返回维保记录</el-button>
      <el-tag type="success">工单ID：{{ task.id || '-' }}</el-tag>
      <el-tag>工单号：{{ task.taskNo || '-' }}</el-tag>
      <el-tag>维保人：{{ task.inspectionUser || '-' }}</el-tag>
    </div>

    <el-card shadow="never" style="margin-bottom:12px;">
      <el-form :model="task" inline>
        <el-form-item label="工单号">
          <el-input v-model="task.taskNo" placeholder="手动填写" />
        </el-form-item>
        <el-form-item label="维保人"><el-input v-model="task.inspectionUser" /></el-form-item>
        <el-form-item label="监护人"><el-input v-model="task.guardianUser" /></el-form-item>
        <el-form-item label="维保时间"><el-date-picker v-model="task.inspectionTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" /></el-form-item>
        <el-form-item label="备注"><el-input v-model="task.remark" /></el-form-item>
        <el-form-item><el-button type="primary" @click="saveTaskOnly">保存工单</el-button></el-form-item>
      </el-form>
    </el-card>

    <div class="toolbar">
      <el-button type="success" @click="openBatch">批量新增item</el-button>
      <el-button type="primary" @click="openSingle">新增item</el-button>
    </div>

    <el-table :data="items" border>
      <el-table-column prop="boxId" label="配电箱ID" width="120" />
      <el-table-column prop="switchModel" label="开关型号" />
      <el-table-column prop="currentRating" label="额定电流" />
      <el-table-column prop="remark" label="备注" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" @click="editItem(scope.$index)">编辑</el-button>
          <el-button size="small" type="danger" @click="removeItem(scope.$index)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="itemDialog" title="维保item" width="680px">
      <el-form :model="itemForm" label-width="110px">
        <el-form-item label="配电箱"><el-select v-model="itemForm.boxId" filterable style="width:100%"><el-option v-for="b in boxOptions" :key="b.value" :label="b.label" :value="b.value"/></el-select></el-form-item>
        <el-form-item label="开关型号"><el-input v-model="itemForm.switchModel" /></el-form-item>
        <el-form-item label="额定电流"><el-input v-model="itemForm.currentRating" /></el-form-item>
        <el-form-item label="截面积"><el-input v-model="itemForm.traverseSection" /></el-form-item>
        <el-form-item label="供电电压"><el-input v-model="itemForm.supplyVoltage" /></el-form-item>
        <el-form-item label="备注"><el-input v-model="itemForm.remark" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="itemDialog=false">取消</el-button>
        <el-button type="primary" @click="saveItem">保存</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="batchDialog" title="批量新增item" width="700px">
      <el-form :model="batchForm" label-width="110px">
        <el-form-item label="配电箱(多选)"><el-select v-model="batchForm.boxIds" multiple filterable style="width:100%"><el-option v-for="b in boxOptions" :key="b.value" :label="b.label" :value="b.value"/></el-select></el-form-item>
        <el-form-item label="开关型号"><el-input v-model="batchForm.switchModel" /></el-form-item>
        <el-form-item label="额定电流"><el-input v-model="batchForm.currentRating" /></el-form-item>
        <el-form-item label="备注"><el-input v-model="batchForm.remark" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="batchDialog=false">取消</el-button>
        <el-button type="primary" @click="saveBatch">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import http from '@/api/http'

const route = useRoute(); const router = useRouter();
const id = Number(route.params.id)
const task = reactive<any>({ id, taskNo:'', inspectionUser:'', guardianUser:'', inspectionTime:'', remark:'' })
const items = ref<any[]>([])
const boxOptions = ref<any[]>([])

const itemDialog = ref(false); const batchDialog = ref(false)
const editIndex = ref(-1)
const itemForm = reactive<any>({ boxId: undefined, switchModel:'', currentRating:'', traverseSection:'', supplyVoltage:'', remark:'' })
const batchForm = reactive<any>({ boxIds: [], switchModel:'', currentRating:'', remark:'' })

const loadBoxes = async ()=>{
  const res = await http.get('/box/page',{ params:{ pageNum:1, pageSize:1000 }})
  const rows = res.data?.data?.records || []
  boxOptions.value = rows.map((r:any)=>({ label:`${r.id} / ${r.boxId||'-'} / ${r.boxAddress||'-'}`, value:r.id }))
}
const load = async ()=>{
  const res = await http.get(`/maintenance-task/${id}`)
  const data = res.data?.data || {}
  Object.assign(task, data)
  items.value = data.items || []
}
const persist = async ()=>{
  if (!task.taskNo) return ElMessage.error('工单号必填')
  await http.post('/maintenance-task/save',{ ...task, id, items: items.value })
}
const saveTaskOnly = async ()=>{ await persist(); ElMessage.success('工单已保存') }

const openSingle = ()=>{ editIndex.value=-1; Object.assign(itemForm,{ boxId:undefined,switchModel:'',currentRating:'',traverseSection:'',supplyVoltage:'',remark:''}); itemDialog.value=true }
const editItem = (idx:number)=>{ editIndex.value=idx; Object.assign(itemForm, items.value[idx]); itemDialog.value=true }
const saveItem = async ()=>{
  if (!itemForm.boxId) return ElMessage.error('请选择配电箱')
  const payload = { ...itemForm }
  if (editIndex.value >= 0) items.value.splice(editIndex.value,1,payload)
  else items.value.push(payload)
  itemDialog.value=false
  await persist(); await load(); ElMessage.success('item已保存')
}
const removeItem = async (idx:number)=>{ items.value.splice(idx,1); await persist(); await load(); ElMessage.success('已删除') }

const openBatch = ()=>{ Object.assign(batchForm,{ boxIds:[], switchModel:'', currentRating:'', remark:''}); batchDialog.value=true }
const saveBatch = async ()=>{
  const ids = batchForm.boxIds || []
  if (!ids.length) return ElMessage.error('请至少选择一个配电箱')
  ids.forEach((boxId:number)=> items.value.push({ boxId, switchModel:batchForm.switchModel, currentRating:batchForm.currentRating, remark:batchForm.remark }))
  batchDialog.value=false
  await persist(); await load(); ElMessage.success('批量新增成功')
}

const goBack = ()=> router.push('/maintenance')
onMounted(async()=>{ await loadBoxes(); await load() })
</script>

<style scoped>
.toolbar{display:flex;gap:8px;align-items:center;margin-bottom:10px;flex-wrap:wrap}
</style>
