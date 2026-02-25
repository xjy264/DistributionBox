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
  ['配电箱（柜）周围有无杂物','周围无杂物'],
  ['防触电警示标识是否完好','警示标识完好'],
  ['配电箱（柜）门锁是否完好','门锁完好'],
  ['配电箱（柜）箱体有无锈蚀变形情况，柜门是否密封良好','箱体无锈蚀变形，柜门密封良好'],
  ['配电箱（柜）内是否有灰土积尘','无积土灰尘'],
  ['配电箱（柜）内有无异响、异味','无异响和异味'],
  ['配电箱（柜）内配线是否整齐','配线整齐'],
  ['配电箱（柜）内标识是否齐全一致','标识齐全一致'],
  ['配电箱内电线、电缆接头有无过热、变色、变形、异味','接头无过热、变色变形、异味'],
  ['配电箱（柜）内零排安装是否稳固，有无N标识','安装稳固，有N标识'],
  ['零排同一个接线端子最多并接俩根同线径导线','同一个接线端子最多并接俩根同线径导线'],
  ['配电箱（柜）内地排安装是否稳固，有无PE标识','安装稳固，有PE标识'],
  ['地排同一个接线端子最多并接俩根同线径导线','同一个接线端子最多并接俩根同线径导线'],
  ['地排与箱体是否用不小于4平方黄绿铜线紧固连接','地排与箱体用不小于4平方黄绿铜线紧固连接'],
  ['地排与箱门是否用不小于4平方黄绿铜线紧固连接','地排与箱门用不小于4平方黄绿铜线紧固连接'],
  ['配电箱内元器件是否外观完好，排列整齐，附件齐全','元器件外观完好，排列整齐，附件齐全'],
  ['配电箱内是否遗留有可能带电的导线及裸露端子','配电箱内无遗留可能带电导线及裸露端子'],
  ['配电箱内不同极性带电裸露部分电气间隙是否在安全范围内','电气间隙在安全范围内'],
  ['配电箱内电气元件是否在安全范围内','配电箱内电气元件在安全范围内'],
  ['二次接线是否整齐、有无绝缘老化、断裂或接触不良','排线整齐无老化、断裂现象'],
  ['指示灯、仪表显示是否正常','指示灯、仪表等正常'],
  ['配电箱（柜）进出线是否防火封堵','进出线处做好防火封堵'],
  ['塑壳断路器是否有隔弧挡片','必须有隔弧片']
]

const maintenanceTemplates: Record<string, { section: string; seq: number; prefix: string; content: string; standard: string }[]> = {
  monthly: [
    ...baseExteriorItems.map((it, idx) => ({ section: '一、配电箱（柜）外观检查', seq: idx + 1, prefix: `m${idx + 1}`, content: it[0], standard: it[1] })),
    { section: '二、配电箱（柜）系统图检查', seq: 24, prefix: 'm24', content: '设备编号标识是否与系统图对应一致', standard: '设备编号标识与系统图对应一致' },
    { section: '二、配电箱（柜）系统图检查', seq: 25, prefix: 'm25', content: '配电箱（柜）系统图与上次检查是否一致', standard: '与上次检查一致' },
    { section: '三、站名字检查', seq: 26, prefix: 'm26', content: '站名字发光是否均匀无瞎点', standard: '发光均匀无瞎点' },
    { section: '三、站名字检查', seq: 27, prefix: 'm27', content: '站名字亚克力板是否变形开裂', standard: '无变形开裂' },
    { section: '三、站名字检查', seq: 28, prefix: 'm28', content: '站名字整体结构是否结实可靠', standard: '整体结构结实可靠' },
    { section: '三、站名字检查', seq: 29, prefix: 'm29', content: '站名字电线路是否有发热老化开裂现象', standard: '连接部位无松动、缺失' },
    { section: '四、检查与维护维保', seq: 30, prefix: 'm30', content: '配电箱（柜）内除尘清洁', standard: '配电箱（柜）内无积土灰尘' },
    { section: '四、检查与维护维保', seq: 31, prefix: 'm31', content: '紧固电线、电缆接头、接头端子、螺栓', standard: '接头紧固' },
    { section: '四、检查与维护维保', seq: 32, prefix: 'm32', content: '红外测试仪测量电缆、接头是否有过热现象', standard: '测试点温度符合要求规范' },
    { section: '四、检查与维护维保', seq: 33, prefix: 'm33', content: '红外测试仪测量箱内元器件是否有过热现象', standard: '元器件无过热现象' },
    { section: '四、检查与维护维保', seq: 34, prefix: 'm34', content: '核对电压表、电流表等显示是否正确', standard: '各种仪表正常' },
    { section: '四、检查与维护维保', seq: 35, prefix: 'm35', content: '缺失或新增标识补充', standard: '按现场补充' },
    { section: '四、检查与维护维保', seq: 36, prefix: 'm36', content: '发现零小病害的现场整治', standard: '病害处置无隐患' }
  ],
  quarterly: [
    ...baseExteriorItems.map((it, idx) => ({ section: '一、配电箱（柜）外观检查', seq: idx + 1, prefix: `q${idx + 1}`, content: it[0], standard: it[1] })),
    { section: '二、配电箱（柜）系统图检查', seq: 24, prefix: 'q24', content: '设备编号标识是否与系统图对应一致', standard: '设备编号标识与系统图对应一致' },
    { section: '二、配电箱（柜）系统图检查', seq: 25, prefix: 'q25', content: '配电箱（柜）系统图与上次检查是否一致', standard: '与上次检查一致' },
    { section: '三、站名字检查', seq: 26, prefix: 'q26', content: '站名字发光是否均匀无瞎点', standard: '发光均匀无瞎点' },
    { section: '三、站名字检查', seq: 27, prefix: 'q27', content: '站名字亚克力板是否变形开裂', standard: '无变形开裂' },
    { section: '三、站名字检查', seq: 28, prefix: 'q28', content: '站名字整体结构是否结实可靠', standard: '整体结构结实可靠' },
    { section: '三、站名字检查', seq: 29, prefix: 'q29', content: '站名字电线路是否有发热老化开裂现象', standard: '连接部位无松动、缺失' },
    { section: '三、站名字检查', seq: 30, prefix: 'q30', content: '站名字电源是否有老化损坏现象', standard: '电源无老化损坏' },
    { section: '三、站名字检查', seq: 31, prefix: 'q31', content: '站名字电源安装是否牢靠', standard: '安装牢靠' },
    { section: '三、站名字检查', seq: 32, prefix: 'q32', content: '发光二极管之间导线连接是否稳固无裸露', standard: '连接稳固无裸露' },
    { section: '四、照明电线路检查与维护保养', seq: 33, prefix: 'q33', content: '电线路是否排列整齐，编号是否正确', standard: '电线路排列整齐，编号正确' },
    { section: '四、照明电线路检查与维护保养', seq: 34, prefix: 'q34', content: '电缆起始段、拐弯处是否悬挂功能齐全的标识牌', standard: '悬挂功能齐全的标识牌' },
    { section: '四、照明电线路检查与维护保养', seq: 35, prefix: 'q35', content: '电缆内绝缘层颜色与接线火地零一致', standard: '颜色与火地零一致' },
    { section: '四、照明电线路检查与维护保养', seq: 36, prefix: 'q36', content: '电线电缆绝缘层是否有破损、变色、老化现象', standard: '绝缘层无破损、变色、老化' },
    { section: '四、照明电线路检查与维护保养', seq: 37, prefix: 'q37', content: '对用电负荷进行核对', standard: '负荷核对无异常' },
    { section: '五、电源电缆（含接箱）的检查与维护保养', seq: 38, prefix: 'q38', content: '电线路是否排列整齐，编号是否正确', standard: '电线路排列整齐，编号正确' },
    { section: '五、电源电缆（含接箱）的检查与维护保养', seq: 39, prefix: 'q39', content: '电缆起始段、拐弯处是否悬挂功能齐全的标识牌', standard: '悬挂功能齐全的标识牌' },
    { section: '五、电源电缆（含接箱）的检查与维护保养', seq: 40, prefix: 'q40', content: '电缆内绝缘层颜色与接线火地零一致', standard: '颜色与火地零一致' },
    { section: '五、电源电缆（含接箱）的检查与维护保养', seq: 41, prefix: 'q41', content: '电线电缆绝缘层是否有破损、变色、老化现象', standard: '绝缘层无破损、变色、老化' },
    { section: '五、电源电缆（含接箱）的检查与维护保养', seq: 42, prefix: 'q42', content: '电缆端子连接是否紧固无变色', standard: '连接紧固无变色' },
    { section: '六、电缆桥架、线管的检查与维护保养', seq: 43, prefix: 'q43', content: '桥架线管是否有变形、锈蚀、涂层脱落', standard: '无变形、锈蚀、涂层脱落' },
    { section: '六、电缆桥架、线管的检查与维护保养', seq: 44, prefix: 'q44', content: '桥架连接部位是否松动或缺失', standard: '无松动或缺失' },
    { section: '六、电缆桥架、线管的检查与维护保养', seq: 45, prefix: 'q45', content: '桥架线管接地线是否完好', standard: '接地线完好' },
    { section: '六、电缆桥架、线管的检查与维护保养', seq: 46, prefix: 'q46', content: '桥架线管内有无杂物，如有则清理', standard: '无杂物或已清理' },
    { section: '六、电缆桥架、线管的检查与维护保养', seq: 47, prefix: 'q47', content: '桥架线管支架系统、管卡螺栓紧固或更换', standard: '紧固可靠' },
    { section: '六、电缆桥架、线管的检查与维护保养', seq: 48, prefix: 'q48', content: '桥架、线管涂层脱落处的喷漆保养', standard: '喷漆保养到位' },
    { section: '七、检查与维护维保', seq: 49, prefix: 'q49', content: '配电箱（柜）内除尘清洁', standard: '配电箱（柜）内无积土灰尘' },
    { section: '七、检查与维护维保', seq: 50, prefix: 'q50', content: '紧固电线、电缆接头、接头端子、螺栓', standard: '接头紧固' },
    { section: '七、检查与维护维保', seq: 51, prefix: 'q51', content: '红外测试仪测量电缆、接头是否有过热现象', standard: '测试点温度符合要求规范' },
    { section: '七、检查与维护维保', seq: 52, prefix: 'q52', content: '红外测试仪测量箱内元器件是否有过热现象', standard: '元器件无过热现象' },
    { section: '七、检查与维护维保', seq: 53, prefix: 'q53', content: '核对电压表、电流表等显示是否正确', standard: '各种仪表正常' },
    { section: '七、检查与维护维保', seq: 54, prefix: 'q54', content: '对电线缆的运行温度进行测量', standard: '测量值符合要求' },
    { section: '七、检查与维护维保', seq: 55, prefix: 'q55', content: '对电线电缆的绝缘阻值进行测量', standard: '阻值符合要求' },
    { section: '七、检查与维护维保', seq: 56, prefix: 'q56', content: '缺失或新增标识补充', standard: '按现场补充' },
    { section: '七、检查与维护维保', seq: 57, prefix: 'q57', content: '发现零小病害的现场整治', standard: '病害处置无隐患' }
  ],
  yearly: [
    ...baseExteriorItems.map((it, idx) => ({ section: '一、配电箱（柜）外观检查', seq: idx + 1, prefix: `y${idx + 1}`, content: it[0], standard: it[1] })),
    { section: '二、配电箱（柜）系统图检查', seq: 24, prefix: 'y24', content: '设备编号标识是否与系统图对应一致', standard: '设备编号标识与系统图对应一致' },
    { section: '二、配电箱（柜）系统图检查', seq: 25, prefix: 'y25', content: '配电箱（柜）系统图与上次检查是否一致', standard: '与上次检查一致' },
    { section: '三、站名字检查', seq: 26, prefix: 'y26', content: '站名字发光是否均匀无瞎点', standard: '发光均匀无瞎点' },
    { section: '三、站名字检查', seq: 27, prefix: 'y27', content: '站名字亚克力板是否变形开裂', standard: '无变形开裂' },
    { section: '三、站名字检查', seq: 28, prefix: 'y28', content: '站名字整体结构是否结实可靠', standard: '整体结构结实可靠' },
    { section: '三、站名字检查', seq: 29, prefix: 'y29', content: '站名字电线路是否有发热老化开裂现象', standard: '连接部位无松动、缺失' },
    { section: '三、站名字检查', seq: 30, prefix: 'y30', content: '站名字电源是否有老化损坏现象', standard: '电源无老化损坏' },
    { section: '三、站名字检查', seq: 31, prefix: 'y31', content: '站名字电源安装是否牢靠', standard: '安装牢靠' },
    { section: '三、站名字检查', seq: 32, prefix: 'y32', content: '发光二极管之间导线连接是否稳固无裸露', standard: '连接稳固无裸露' },
    { section: '三、站名字检查', seq: 33, prefix: 'y33', content: '站字电流电压的测量', standard: '测量值符合要求' },
    { section: '四、照明电线路检查与维护保养', seq: 34, prefix: 'y34', content: '电线路是否排列整齐，编号是否正确', standard: '电线路排列整齐，编号正确' },
    { section: '四、照明电线路检查与维护保养', seq: 35, prefix: 'y35', content: '电缆起始段、拐弯处是否悬挂功能齐全的标识牌', standard: '悬挂功能齐全的标识牌' },
    { section: '四、照明电线路检查与维护保养', seq: 36, prefix: 'y36', content: '电缆内绝缘层颜色与接线火地零一致', standard: '颜色与火地零一致' },
    { section: '四、照明电线路检查与维护保养', seq: 37, prefix: 'y37', content: '电线电缆绝缘层是否有破损、变色、老化现象', standard: '绝缘层无破损、变色、老化' },
    { section: '四、照明电线路检查与维护保养', seq: 38, prefix: 'y38', content: '对用电负荷进行核对', standard: '负荷核对无异常' },
    { section: '五、电源电缆（含接箱）的检查与维护保养', seq: 39, prefix: 'y39', content: '电线路是否排列整齐，编号是否正确', standard: '电线路排列整齐，编号正确' },
    { section: '五、电源电缆（含接箱）的检查与维护保养', seq: 40, prefix: 'y40', content: '电缆起始段、拐弯处是否悬挂功能齐全的标识牌', standard: '悬挂功能齐全的标识牌' },
    { section: '五、电源电缆（含接箱）的检查与维护保养', seq: 41, prefix: 'y41', content: '电缆内绝缘层颜色与接线火地零一致', standard: '颜色与火地零一致' },
    { section: '五、电源电缆（含接箱）的检查与维护保养', seq: 42, prefix: 'y42', content: '电线电缆绝缘层是否有破损、变色、老化现象', standard: '绝缘层无破损、变色、老化' },
    { section: '五、电源电缆（含接箱）的检查与维护保养', seq: 43, prefix: 'y43', content: '电缆端子连接是否紧固无变色', standard: '连接紧固无变色' },
    { section: '六、电缆桥架、线管的检查与维护保养', seq: 44, prefix: 'y44', content: '桥架线管是否有变形、锈蚀、涂层脱落', standard: '无变形、锈蚀、涂层脱落' },
    { section: '六、电缆桥架、线管的检查与维护保养', seq: 45, prefix: 'y45', content: '桥架连接部位是否松动或缺失', standard: '无松动或缺失' },
    { section: '六、电缆桥架、线管的检查与维护保养', seq: 46, prefix: 'y46', content: '桥架线管接地线是否完好', standard: '接地线完好' },
    { section: '六、电缆桥架、线管的检查与维护保养', seq: 47, prefix: 'y47', content: '桥架线管内有无杂物，如有则清理', standard: '无杂物或已清理' },
    { section: '六、电缆桥架、线管的检查与维护保养', seq: 48, prefix: 'y48', content: '桥架线管支架系统、管卡螺栓紧固或更换', standard: '紧固可靠' },
    { section: '六、电缆桥架、线管的检查与维护保养', seq: 49, prefix: 'y49', content: '桥架、线管涂层脱落处的喷漆保养', standard: '喷漆保养到位' },
    { section: '七、站台灯具接线盒', seq: 50, prefix: 'y50', content: '灯具接线盒内电线接头是否松动、完好', standard: '电线接头无松动、完好' },
    { section: '七、站台灯具接线盒', seq: 51, prefix: 'y51', content: '灯具悬挂或安装支架是否牢固', standard: '灯具悬挂或安装支架牢固' },
    { section: '七、站台灯具接线盒', seq: 52, prefix: 'y52', content: '灯具接线盒内是否有积水、灰尘', standard: '接线盒内无积水、灰尘' },
    { section: '七、站台灯具接线盒', seq: 53, prefix: 'y53', content: '清理灯具内杂物', standard: '已清理' },
    { section: '七、站台灯具接线盒', seq: 54, prefix: 'y54', content: '接线盒是否变形、锈蚀，螺栓是否缺失', standard: '无变形、锈蚀、缺失' },
    { section: '七、站台灯具接线盒', seq: 55, prefix: 'y55', content: '相关病害的处理', standard: '病害处置完成' },
    { section: '八、检查与维护维保', seq: 56, prefix: 'y56', content: '配电箱（柜）内除尘清洁', standard: '配电箱（柜）内无积土灰尘' },
    { section: '八、检查与维护维保', seq: 57, prefix: 'y57', content: '紧固电线、电缆接头、接头端子、螺栓', standard: '接头紧固' },
    { section: '八、检查与维护维保', seq: 58, prefix: 'y58', content: '红外测试仪测量电缆、接头是否有过热现象', standard: '测试点温度符合要求规范' },
    { section: '八、检查与维护维保', seq: 59, prefix: 'y59', content: '红外测试仪测量箱内元器件是否有过热现象', standard: '元器件无过热现象' },
    { section: '八、检查与维护维保', seq: 60, prefix: 'y60', content: '核对电压表、电流表等显示是否正确', standard: '各种仪表正常' },
    { section: '八、检查与维护维保', seq: 61, prefix: 'y61', content: '对电线缆的运行温度进行测量', standard: '测量值符合要求' },
    { section: '八、检查与维护维保', seq: 62, prefix: 'y62', content: '对电线电缆的绝缘阻值进行测量', standard: '阻值符合要求' },
    { section: '八、检查与维护维保', seq: 63, prefix: 'y63', content: '缺失或新增标识补充', standard: '按现场补充' },
    { section: '八、检查与维护维保', seq: 64, prefix: 'y64', content: '发现零小病害的现场整治', standard: '病害处置无隐患' }
  ]
}

const displayRows = computed(() => {
  const src = maintenanceTemplates[type] || []
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
