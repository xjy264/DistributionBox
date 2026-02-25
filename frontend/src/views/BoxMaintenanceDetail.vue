<template>
  <div>
    <div class="toolbar">
      <el-button @click="goBack">返回配电箱详情</el-button>
      <el-tag>维保类型：{{ typeLabel }}</el-tag>
      <el-tag>记录ID：{{ record.id || '-' }}</el-tag>
      <el-tag>配电箱ID：{{ record.box_id || '-' }}</el-tag>
    </div>

    <el-descriptions title="维保头信息" :column="2" border>
      <el-descriptions-item label="盯控人员">{{ record.supervise_user || '-' }}</el-descriptions-item>
      <el-descriptions-item label="维保人员">{{ record.maintenance_user || '-' }}</el-descriptions-item>
      <el-descriptions-item label="创建时间">{{ record.created_time || '-' }}</el-descriptions-item>
      <el-descriptions-item label="更新时间">{{ record.updated_time || '-' }}</el-descriptions-item>
    </el-descriptions>

    <el-divider />

    <el-table :data="displayRows" border size="small" :span-method="spanMethod" row-key="rowKey">
      <el-table-column label="序号" width="120">
        <template #default="scope">
          <span v-if="scope.row.isSection" class="section-title">{{ scope.row.sectionTitle }}</span>
          <span v-else>{{ scope.row.sectionSeq }}</span>
        </template>
      </el-table-column>
      <el-table-column label="检查内容" min-width="260" show-overflow-tooltip>
        <template #default="scope"><span v-if="!scope.row.isSection">{{ scope.row.content }}</span></template>
      </el-table-column>
      <el-table-column label="检查标准" min-width="260" show-overflow-tooltip>
        <template #default="scope"><span v-if="!scope.row.isSection">{{ scope.row.standard }}</span></template>
      </el-table-column>
      <el-table-column label="检查结果" min-width="180">
        <template #default="scope"><span v-if="!scope.row.isSection">{{ record[scope.row.prefix + '_result'] || '-' }}</span></template>
      </el-table-column>
      <el-table-column label="是否正常" min-width="140">
        <template #default="scope"><span v-if="!scope.row.isSection">{{ record[scope.row.prefix + '_status'] || '-' }}</span></template>
      </el-table-column>
      <el-table-column label="备注" min-width="180">
        <template #default="scope"><span v-if="!scope.row.isSection">{{ record[scope.row.prefix + '_remark'] || '-' }}</span></template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, reactive } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import http from '@/api/http'

const route = useRoute()
const router = useRouter()
const type = String(route.params.type || 'monthly')
const id = Number(route.params.id)
const boxId = Number(route.query.boxId || 0)
const record = reactive<any>({})

const typeLabel = computed(() => ({ monthly: '月检', quarterly: '季检', yearly: '年检' } as any)[type] || type)

const baseExteriorItems = [
  ['配电箱（柜）周围有无杂物','周围无杂物'],['防触电警示标识是否完好','警示标识完好'],['配电箱（柜）门锁是否完好','门锁完好'],
  ['配电箱（柜）箱体有无锈蚀变形情况，柜门是否密封良好','箱体无锈蚀变形，柜门密封良好'],['配电箱（柜）内是否有灰土积尘','无积土灰尘'],
  ['配电箱（柜）内有无异响、异味','无异响和异味'],['配电箱（柜）内配线是否整齐','配线整齐'],['配电箱（柜）内标识是否齐全一致','标识齐全一致'],
  ['配电箱内电线、电缆接头有无过热、变色、变形、异味','接头无过热、变色变形、异味'],['配电箱（柜）内零排安装是否稳固，有无N标识','安装稳固，有N标识'],
  ['零排同一个接线端子最多并接俩根同线径导线','同一个接线端子最多并接俩根同线径导线'],['配电箱（柜）内地排安装是否稳固，有无PE标识','安装稳固，有PE标识'],
  ['地排同一个接线端子最多并接俩根同线径导线','同一个接线端子最多并接俩根同线径导线'],['地排与箱体是否用不小于4平方黄绿铜线紧固连接','地排与箱体用不小于4平方黄绿铜线紧固连接'],
  ['地排与箱门是否用不小于4平方黄绿铜线紧固连接','地排与箱门用不小于4平方黄绿铜线紧固连接'],['配电箱内元器件是否外观完好，排列整齐，附件齐全','元器件外观完好，排列整齐，附件齐全'],
  ['配电箱内是否遗留有可能带电的导线及裸露端子','配电箱内无遗留可能带电导线及裸露端子'],['配电箱内不同极性带电裸露部分电气间隙是否在安全范围内','电气间隙在安全范围内'],
  ['配电箱内电气元件是否在安全范围内','配电箱内电气元件在安全范围内'],['二次接线是否整齐、有无绝缘老化、断裂或接触不良','排线整齐无老化、断裂现象'],
  ['指示灯、仪表显示是否正常','指示灯、仪表等正常'],['配电箱（柜）进出线是否防火封堵','进出线处做好防火封堵'],['塑壳断路器是否有隔弧挡片','必须有隔弧片']
]

const templates: any = {
  monthly: [
    ...baseExteriorItems.map((it, i)=>({section:'一、配电箱（柜）外观检查',prefix:`m${i+1}`,content:it[0],standard:it[1]})),
    {section:'二、配电箱（柜）系统图检查',prefix:'m24',content:'设备编号标识是否与系统图对应一致',standard:'设备编号标识与系统图对应一致'},
    {section:'二、配电箱（柜）系统图检查',prefix:'m25',content:'配电箱（柜）系统图与上次检查是否一致',standard:'与上次检查一致'}
  ],
  quarterly: [...baseExteriorItems.map((it, i)=>({section:'一、配电箱（柜）外观检查',prefix:`q${i+1}`,content:it[0],standard:it[1]}))],
  yearly: [...baseExteriorItems.map((it, i)=>({section:'一、配电箱（柜）外观检查',prefix:`y${i+1}`,content:it[0],standard:it[1]}))]
}

const displayRows = computed(() => {
  const src = templates[type] || []
  const rows: any[] = []
  let section = ''
  let idx = 0
  src.forEach((item: any) => {
    if (item.section !== section) {
      section = item.section
      idx = 0
      rows.push({ isSection: true, sectionTitle: section, rowKey: `section-${section}` })
    }
    idx += 1
    rows.push({ ...item, sectionSeq: idx, isSection: false, rowKey: `item-${item.prefix}` })
  })
  return rows
})

const spanMethod = ({ row, columnIndex }: any) => {
  if (row?.isSection) {
    if (columnIndex === 0) return [1, 6]
    return [0, 0]
  }
  return [1, 1]
}

const load = async () => {
  const res = await http.get(`/box-maintenance/${type}/${id}`)
  Object.assign(record, res.data?.data || {})
}

const goBack = () => {
  if (boxId > 0) router.push(`/box-detail/${boxId}`)
  else router.push('/box')
}

onMounted(load)
</script>

<style scoped>
.toolbar{display:flex;gap:8px;align-items:center;flex-wrap:wrap;margin-bottom:10px}
.section-title{font-weight:700;color:#1f2937}
</style>
