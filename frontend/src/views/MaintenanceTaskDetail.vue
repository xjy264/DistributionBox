<template>
  <div>
    <div class="toolbar">
      <el-button @click="goBack">返回维保记录</el-button>
      <el-tag type="success">工单ID：{{ task.id || '-' }}</el-tag>
      <el-tag>工单号：{{ task.taskNo || '-' }}</el-tag>
    </div>

    <el-tabs>
      <el-tab-pane label="维保工作记录表">
        <el-form :model="task" label-width="110px" style="max-width:900px">
          <el-form-item label="工单编号"><el-input v-model="task.taskNo" placeholder="手动填写"/></el-form-item>
          <el-form-item label="维保类型">
            <el-select v-model="task.maintenanceType" style="width:100%">
              <el-option label="月度维保" value="MONTHLY"/>
              <el-option label="季度维保" value="QUARTERLY"/>
              <el-option label="年度维保" value="YEARLY"/>
            </el-select>
          </el-form-item>
          <el-form-item label="维保地点"><el-input v-model="task.maintenanceLocation"/></el-form-item>
          <el-form-item label="维保时间"><el-date-picker v-model="task.inspectionTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" style="width:100%"/></el-form-item>
          <el-form-item label="维保工作内容"><el-input v-model="task.maintenanceContent" type="textarea" :rows="4"/></el-form-item>
          <el-form-item label="维保工作小结"><el-input v-model="task.maintenanceSummary" type="textarea" :rows="4"/></el-form-item>
          <el-form-item><el-button type="primary" @click="saveTask">保存工单</el-button></el-form-item>
        </el-form>
      </el-tab-pane>

      <el-tab-pane label="维保病害及处置记录">
        <div class="toolbar"><el-button type="primary" @click="openDisease">新增病害记录</el-button></div>
        <el-table :data="diseases" border>
          <el-table-column prop="seqNo" label="序号" width="80"/>
          <el-table-column prop="diseaseLocation" label="病害位置"/>
          <el-table-column prop="diseaseDesc" label="病害及缺陷描述"/>
          <el-table-column prop="quantity" label="数量" width="90"/>
          <el-table-column prop="disposalMethod" label="处置方法"/>
          <el-table-column prop="remark" label="备注"/>
          <el-table-column label="操作" width="160"><template #default="s"><el-button size="small" @click="editDisease(s.row)">编辑</el-button><el-button size="small" type="danger" @click="delDisease(s.row.id)">删除</el-button></template></el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="维保病害处置前后对比图">
        <div class="toolbar"><el-button type="primary" @click="openCompare">新增对比记录</el-button></div>
        <el-table :data="compares" border>
          <el-table-column prop="diseaseLocation" label="病害位置"/>
          <el-table-column label="维保前图片"><template #default="s"><img v-if="s.row.beforeImageUrl" :src="preview(s.row.beforeImageUrl)" class="thumb"/></template></el-table-column>
          <el-table-column prop="diseaseNote" label="病害说明"/>
          <el-table-column label="维保后图片"><template #default="s"><img v-if="s.row.afterImageUrl" :src="preview(s.row.afterImageUrl)" class="thumb"/></template></el-table-column>
          <el-table-column prop="disposalNote" label="处置说明"/>
          <el-table-column label="操作" width="160"><template #default="s"><el-button size="small" @click="editCompare(s.row)">编辑</el-button><el-button size="small" type="danger" @click="delCompare(s.row.id)">删除</el-button></template></el-table-column>
        </el-table>
      </el-tab-pane>

      <el-tab-pane label="维保过程图片">
        <div class="toolbar"><el-button type="primary" @click="openProcess">新增过程图片</el-button></div>
        <el-table :data="processes" border>
          <el-table-column label="图片"><template #default="s"><img v-if="s.row.imageUrl" :src="preview(s.row.imageUrl)" class="thumb"/></template></el-table-column>
          <el-table-column label="操作" width="120"><template #default="s"><el-button size="small" type="danger" @click="delProcess(s.row.id)">删除</el-button></template></el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>

    <el-dialog v-model="diseaseDialog" title="病害记录" width="680px">
      <el-form :model="diseaseForm" label-width="110px">
        <el-form-item label="序号"><el-input-number v-model="diseaseForm.seqNo" :min="1"/></el-form-item>
        <el-form-item label="病害位置"><el-input v-model="diseaseForm.diseaseLocation"/></el-form-item>
        <el-form-item label="病害及缺陷描述"><el-input v-model="diseaseForm.diseaseDesc"/></el-form-item>
        <el-form-item label="数量"><el-input-number v-model="diseaseForm.quantity" :min="0"/></el-form-item>
        <el-form-item label="处置方法"><el-input v-model="diseaseForm.disposalMethod"/></el-form-item>
        <el-form-item label="备注"><el-input v-model="diseaseForm.remark"/></el-form-item>
      </el-form>
      <template #footer><el-button @click="diseaseDialog=false">取消</el-button><el-button type="primary" @click="saveDisease">保存</el-button></template>
    </el-dialog>

    <el-dialog v-model="compareDialog" title="前后对比" width="700px">
      <el-form :model="compareForm" label-width="110px">
        <el-form-item label="病害位置"><el-input v-model="compareForm.diseaseLocation"/></el-form-item>
        <el-form-item label="维保前图片"><ImageUpload v-model="compareForm.beforeImageUrl"/></el-form-item>
        <el-form-item label="病害说明"><el-input v-model="compareForm.diseaseNote"/></el-form-item>
        <el-form-item label="维保后图片"><ImageUpload v-model="compareForm.afterImageUrl"/></el-form-item>
        <el-form-item label="处置说明"><el-input v-model="compareForm.disposalNote"/></el-form-item>
      </el-form>
      <template #footer><el-button @click="compareDialog=false">取消</el-button><el-button type="primary" @click="saveCompare">保存</el-button></template>
    </el-dialog>

    <el-dialog v-model="processDialog" title="过程图片" width="600px">
      <el-form :model="processForm" label-width="90px">
        <el-form-item label="图片"><ImageUpload v-model="processForm.imageUrl"/></el-form-item>
      </el-form>
      <template #footer><el-button @click="processDialog=false">取消</el-button><el-button type="primary" @click="saveProcess">保存</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import ImageUpload from '@/components/ImageUpload.vue'
import http from '@/api/http'

const route = useRoute(); const router = useRouter();
const id = Number(route.params.id)
const task = reactive<any>({ id, taskNo:'', maintenanceType:'MONTHLY', maintenanceLocation:'', inspectionTime:'', maintenanceContent:'', maintenanceSummary:'' })
const diseases = ref<any[]>([]); const compares = ref<any[]>([]); const processes = ref<any[]>([])
const diseaseDialog = ref(false); const compareDialog = ref(false); const processDialog = ref(false)
const diseaseForm = reactive<any>({ id:null, taskId:id, seqNo:1, diseaseLocation:'', diseaseDesc:'', quantity:0, disposalMethod:'', remark:'' })
const compareForm = reactive<any>({ id:null, taskId:id, diseaseLocation:'', beforeImageUrl:'', diseaseNote:'', afterImageUrl:'', disposalNote:'' })
const processForm = reactive<any>({ id:null, taskId:id, imageUrl:'' })

const preview = (u:string)=> u ? (u.startsWith('/files/') ? `/api${u}` : u) : ''
const goBack = ()=>router.push('/maintenance')

const loadTask = async()=>{ const r=await http.get(`/maintenance-task/${id}`); Object.assign(task, r.data?.data||{}) }
const loadModules = async()=>{
  diseases.value = (await http.get('/maintenance-module/disease',{params:{taskId:id}})).data?.data||[]
  compares.value = (await http.get('/maintenance-module/compare',{params:{taskId:id}})).data?.data||[]
  processes.value = (await http.get('/maintenance-module/process',{params:{taskId:id}})).data?.data||[]
}
const saveTask = async()=>{ if(!task.taskNo) return ElMessage.error('工单号必填'); await http.post('/maintenance-task/save', task); ElMessage.success('保存成功'); await loadTask() }

const openDisease = ()=>{ Object.assign(diseaseForm,{id:null,taskId:id,seqNo:diseases.value.length+1,diseaseLocation:'',diseaseDesc:'',quantity:0,disposalMethod:'',remark:''}); diseaseDialog.value=true }
const editDisease = (r:any)=>{ Object.assign(diseaseForm,r); diseaseDialog.value=true }
const saveDisease = async()=>{ if(!diseaseForm.seqNo) diseaseForm.seqNo=1; if(diseaseForm.id) await http.post('/maintenance-module/disease/update',diseaseForm); else await http.post('/maintenance-module/disease/save',diseaseForm); diseaseDialog.value=false; await loadModules() }
const delDisease = async(id:number)=>{ await http.delete(`/maintenance-module/disease/${id}`); await loadModules() }

const openCompare = ()=>{ Object.assign(compareForm,{id:null,taskId:id,diseaseLocation:'',beforeImageUrl:'',diseaseNote:'',afterImageUrl:'',disposalNote:''}); compareDialog.value=true }
const editCompare = (r:any)=>{ Object.assign(compareForm,r); compareDialog.value=true }
const saveCompare = async()=>{ await http.post('/maintenance-module/compare/save',compareForm); compareDialog.value=false; await loadModules() }
const delCompare = async(id:number)=>{ await http.delete(`/maintenance-module/compare/${id}`); await loadModules() }

const openProcess = ()=>{ Object.assign(processForm,{id:null,taskId:id,imageUrl:''}); processDialog.value=true }
const saveProcess = async()=>{ await http.post('/maintenance-module/process/save',processForm); processDialog.value=false; await loadModules() }
const delProcess = async(id:number)=>{ await http.delete(`/maintenance-module/process/${id}`); await loadModules() }

onMounted(async()=>{ await loadTask(); await loadModules() })
</script>

<style scoped>
.toolbar{display:flex;gap:8px;align-items:center;flex-wrap:wrap;margin-bottom:10px}
.thumb{width:72px;height:72px;object-fit:cover;border-radius:4px;border:1px solid #eee}
</style>
