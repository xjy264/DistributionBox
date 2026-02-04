<template>
  <div>


    <!--        <div style="margin: 10px 0;text-align: left;width: 50%;">-->
    <!--          <el-input style="width: 190px" placeholder="请输入名称" suffix-icon="el-icon-search"></el-input>-->
    <!--          <el-input style="width: 190px" placeholder="请输入邮箱" suffix-icon="el-icon-message" class="ml-5"></el-input>-->
    <!--          <el-input style="width: 190px" placeholder="请输入地址" suffix-icon="el-icon-position" class="ml-5"></el-input>-->
    <!--          <el-button class="ml-5 button_01" type="primary" >搜 索</el-button>-->
    <!--        </div>-->

    <div style="margin: 10px 0;text-align: left;width: 50%;">
      <el-button type="primary" class="button_01" @click="changeBox">修改 <i class="el-icon-edit"></i></el-button>
<!--      <el-button type="primary" class="button_01" style="width: 200px" @click="addBox">新增电器元件 <i class="el-icon-plus"></i></el-button>-->
<!--      <el-button type="primary" class="button_01">导入 <i class="el-icon-bottom"></i></el-button>-->
<!--      <el-button type="danger" class="button_01">导出 <i class="el-icon-top"></i></el-button>-->
<!--      <el-button type="warning" class="button_01" style="margin-left: 180px;" @click="fix">报  修 <i class="el-icon-s-claim"></i></el-button>-->
    </div>

    <el-table
        :data="firstTableData"
        border
        :show-header="false"
        class="person-info"
        stripe
    >
      <el-table-column prop="name1" label="档案编号" align="center"></el-table-column>
      <el-table-column align="center">
        <!-- eslint-disable-next-line -->
        <template slot="header" slot-scope="scope">
          <span></span>
        </template>
        <template slot-scope="scope">
          <span>{{scope.row.value1}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="name2" label="姓名" align="center"></el-table-column>
      <el-table-column align="center">
        <!-- eslint-disable-next-line -->
        <template slot="header" slot-scope="scope">
          <span></span>
        </template>
        <template slot-scope="scope">
          <span>{{scope.row.value2}}</span>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: "Box",
  data() {
    return{
      firstTableData:[],
      tableData:[],
      total: 0,
      id: '',
      pageNum: 1,
      pageSize: 5,
      dialogFormVisible: false,
      dialogComponentsVisible: false,
      dialogFixVisible: false,
      form: {},
      maintenanceId: this.$route.query.maintenanceId,
      componentsForm: {
      },
      fixForm: {
      },
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):"",
      headerBg: 'headerBg',
    }
  },
  created() {
    this.load()
  },
  methods: {
    load(){
      this.request.get("/maintenance/getById/"+ this.maintenanceId).then(res =>{
        console.log(res)
        this.firstTableData = [
          {
            name1: '配电箱台帐号',
            value1: res.pid,
            name2: '备注',
            value2: res.remark,
          },
          {
            name1: '报修人员',
            value1: res.reportUser,
            name2: '报修时间',
            value2: res.reportTime,
          },{
            name1: '维修人员',
            value1: res.fixUser,
            name2: '维修时间',
            value2: res.fixTime,
          },{
            name1: '故障现象',
            value1: res.faultPhenomenon,
            name2: '故障原因',
            value2: res.faultReason,
          },{
            name1: '维修过程',
            value1: res.fixProcess,
            name2: '更换元器件情况',
            value2: res.components
          }
          ]
        this.secondTableData = [
          {
            name1: 'A-地绝缘电阻',
            value1: res.aresistance,
            name2: 'A-启动电流',
            value2: res.astarting,
            name3: 'A-运行电流',
            value3: res.arunning,
          },{
            name1: 'B-地绝缘电阻',
            value1: res.bresistance,
            name2: 'B-启动电流',
            value2: res.bstarting,
            name3: 'B-运行电流',
            value3: res.brunning,
          },{
            name1: 'C-地绝缘电阻',
            value1: res.cresistance,
            name2: 'C-启动电流',
            value2: res.cstarting,
            name3: 'C-运行电流',
            value3: res.crunning,
          },{
            name1: 'N-PE电阻',
            value1: res.npeResistance,
            name2: 'N-启动电流',
            value2: res.nstarting,
            name3: 'N-运行电流',
            value3: res.nrunning,
          },{
            name1: 'A-B绝缘电阻',
            value1: res.abResistance,
            name2: 'A-C绝缘电阻',
            value2: res.acResistance,
            name3: 'B-C绝缘电阻',
            value3: res.bcResistance,
          },{
            name1: '系统图',
            value1: res.systemChart,
            name2: '编号标识',
            value2: res.number,
            name3: '箱内配线',
            value3: res.distribution,
          },{
            name1: '避雷保护装置',
            value1: res.lightning,
            name2: '零线端子',
            value2: res.zero,
            name3: '地线端子',
            value3: res.ground,
          },{
            name1: '配电箱密封',
            value1: res.seal,
            name2: '电缆标牌',
            value2: res.label,
            name3: '穿管配线',
            value3: res.pipe,
          },{
            name1: '配电箱门锁',
            value1: res.havelock,
            name2: '清灰保洁',
            value2: res.cleaning,
            name3: '线槽配线',
            value3: res.cable,
          }]
      })
    },

    changeBox(){
      this.dialogFormVisible =true
      this.request.get("/box/"+this.boxId).then(res =>{
        this.form = res
      })
    },

    addBox(){
      this.dialogComponentsVisible =true
      this.componentsForm = {}
      this.componentsForm.boxId = 1
    },

    fix(){
      this.dialogFixVisible =true
      this.fixForm = {}
      this.fixForm.boxId=this.boxId
      this.fixForm.code=this.firstTableData[0].value2
      this.fixForm.address=this.firstTableData[0].value1
      this.fixForm.user=this.user.nickname
    },

    saveBox(){
      this.request.post("/box/save",this.form).then(res =>{
        if (res){
          this.$message.success("保存成功")
          this.dialogFormVisible =false
          this.getBox()
        }else {
          this.$message.error("保存失败")
        }
      })
    },
    changeComponents(row){
      this.dialogComponentsVisible = true
      this.componentsForm = row
    },
    deleteComponents(id){
      this.request.delete("/components/"+id).then(res =>{
        if (res){
          this.$message.success("删除成功")
          this.dialogComponentsVisible =false
          this.load()
        }else {
          this.$message.error("删除失败")
        }
      })
    },
    saveComponents(){
      this.componentsForm.boxId=this.boxId
      this.request.post("/components/save",this.componentsForm).then(res =>{
        if (res){
          this.$message.success("保存成功")
          this.dialogComponentsVisible =false
          this.load()
        }else {
          this.$message.error("保存失败")
        }
      })
    },
    saveFix(){
      this.request.post("/fix/save",this.fixForm).then(res =>{
        if (res){
          this.$message.success("报修成功")
          this.dialogFixVisible =false
          this.load()
        }else {
          this.$message.error("报修失败")
        }
      })
    },
  },
}
</script>

<style>
.headerBg {
  background: #eee!important;
}
</style>
