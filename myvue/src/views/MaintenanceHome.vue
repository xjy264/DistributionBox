<template>
  <div>
    <div style="margin: 10px 0;text-align: left;width: 50%;">
      <!--      <el-input style="width: 190px" placeholder="请输入id" suffix-icon="el-icon-search" v-model="id"></el-input>-->
      <!--      <el-input style="width: 190px" placeholder="请输入车间名" suffix-icon="el-icon-message" class="ml-5" v-model="station"></el-input>-->
            <el-input style="width: 190px" placeholder="请输入故障现象" suffix-icon="el-icon-message" class="ml-5" v-model="faultPhenomenon"></el-input>
<!--      <el-cascader-->
<!--          v-model="value"-->
<!--          :options="options"-->
<!--          @change="getArea">-->
<!--      </el-cascader>-->
<!--      <el-input style="width: 190px" placeholder="请输入巡检员姓名" suffix-icon="el-icon-user" class="ml-5" v-model="user"></el-input>-->
      <el-button class="ml-10 button_01" type="primary" @click="load">搜 索</el-button>
      <el-button class="button_01"  type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0;text-align: left;width: 50%;">
      <el-button type="primary" class="button_01" style="width: 200px" @click="add">新增 <i class="el-icon-plus"></i></el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg">
      <el-table-column prop="id" label="抢修编号" width="150"></el-table-column>
      <el-table-column prop="pid" label="配电箱台帐号" width="150"></el-table-column>
      <el-table-column prop="reportTime" label="维修时间" width="200"></el-table-column>
<!--      <el-table-column prop="area" label="工区" width="220"></el-table-column>-->
      <!--      <el-table-column prop="user" label="巡检员" width="220"></el-table-column>-->
      <el-table-column prop="faultPhenomenon" label="故障现象" width="220"></el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="success" class="button_01" style=" margin-right: 20px;" @click="changeHome(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="deleteById(scope.row.id)"
          >
            <el-button type="danger" class="button_01" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
          <el-button type="primary" class="button_01 ml-20"  @click="skip(scope.row.id)" style="background-color: #008CBA">抢修信息 <i class="el-icon-remove-outline"></i></el-button>
        </template>
      </el-table-column>
    </el-table>

    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[2, 5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog
        title="抢修信息"
        :visible.sync="dialogVisible"
        width="50%">
      <el-form label-width="150px" :model="homeForm">
        <el-form-item label="配电箱台帐号">
          <el-input v-model=this.pid disabled autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="报修人员">
          <el-input v-model="homeForm.reportUser" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="报修时间">
          <el-date-picker type="date" value-format="yyyy-MM-dd" placeholder="选择报修时间" v-model="homeForm.reportTime" style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item label="抢修人员">
          <el-input v-model="homeForm.fixUser" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="抢修时间">
          <el-date-picker type="date" value-format="yyyy-MM-dd" placeholder="选择抢修时间" v-model="homeForm.fixTime" style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item label="故障现象">
          <el-input v-model="homeForm.faultPhenomenon" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="故障原因">
          <el-input v-model="homeForm.faultReason" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="维修过程">
          <el-input v-model="homeForm.fixProcess" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="更换元器件情况">
          <el-input v-model="homeForm.components" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="homeForm.remark" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import moment from "moment";

export default {
  name: "Home",
  data() {
    return{
      firstTableData:[],
      secondTableData:[],
      tableData:[],
      total: 0,
      id: '',
      station:'',
      area:'',
      user:'',
      pageNum: 1,
      pageSize: 5,
      boxId: 2,
      pid: this.$route.query.pid,
      dialogFormVisible: false,
      dialogVisible: false,
      form: {},
      homeForm: {},
      headerBg: 'headerBg',
      options: [{
        value: '秦皇岛房建车间',
        label: '秦皇岛房建车间',
        children: [{
          value: '西张庄综合维修工区',
          label: '西张庄综合维修工区',
        }, {
          value: '秦东综合维修工区',
          label: '秦东综合维修工区',
        }, {
          value: '柳村综合维修工区',
          label: '柳村综合维修工区',
        },{
          value: '迁安综合维修工区',
          label: '迁安综合维修工区',
        },{
          value: '遵化综合维修工区',
          label: '遵化综合维修工区',
        }]
      },
        {
          value: '茶坞房建车间',
          label: '茶坞房建车间',
          children: [{
            value: '茶坞综合维修工区',
            label: '茶坞综合维修工区',
          }, {
            value: '蓟县西综合维修工区',
            label: '蓟县西综合维修工区',
          }]
        },
        {
          value: '湖东房建车间',
          label: '湖东房建车间',
          children: [{
            value: '湖东供热维修工区',
            label: '湖东供热维修工区',
          }, {
            value: '湖东房建维修工区',
            label: '湖东房建维修工区',
          }, {
            value: '逐鹿综合维修工区',
            label: '逐鹿综合维修工区',
          }]
        },
        {
          value: '大同东房建车间',
          label: '大同东房建车间',
          children: [{
            value: '新华后街房建维修工区',
            label: '新华后街房建维修工区',
          }, {
            value: '站北街供热维修工区',
            label: '站北街供热维修工区',
          }, {
            value: '同丰电气维修工区',
            label: '同丰电气维修工区',
          },{
            value: '卧虎湾房建维修工区',
            label: '卧虎湾房建维修工区',
          },{
            value: '柴沟堡综合维修工区',
            label: '柴沟堡综合维修工区',
          }]
        },
        {
          value: '大同西房建车间',
          label: '大同西房建车间',
          children: [{
            value: '口泉综合维修工区',
            label: '口泉综合维修工区',
          }, {
            value: '大同西房建维修工区',
            label: '大同西房建维修工区',
          }, {
            value: '大同西房建巡检工区',
            label: '大同西房建巡检工区',
          },{
            value: '西线供热维修工区',
            label: '西线供热维修工区',
          },{
            value: '拥军路供热维修工区',
            label: '拥军路供热维修工区',
          },{
            value: '桥西供热维修工区',
            label: '桥西供热维修工区',
          }]
        },
        {
          value: '大同南房建车间',
          label: '大同南房建车间',
          children: [{
            value: '大同南高铁综合维修工区',
            label: '大同南高铁综合维修工区',
          }, {
            value: '阳高南高铁综合维修工区',
            label: '阳高南高铁综合维修工区',
          }, {
            value: '应县综合维修工区',
            label: '应县综合维修工区',
          },{
            value: '大同南综合工区',
            label: '大同南综合工区',
          },{
            value: '大同房管工区',
            label: '大同房管工区',
          }]
        },{
          value: '朔州房建车间',
          label: '朔州房建车间',
          children: [{
            value: '朔州房建维修工区',
            label: '朔州房建维修工区',
          }, {
            value: '朔州供热维修工区',
            label: '朔州供热维修工区',
          }, {
            value: '偏关综合维修工区',
            label: '偏关综合维修工区',
          }]
        },{
          value: '原平房建车间',
          label: '原平房建车间',
          children: [{
            value: '忻州房建维修工区',
            label: '忻州房建维修工区',
          }, {
            value: '原平房建维修工区',
            label: '原平房建维修工区',
          }, {
            value: '灵丘房建维修工区',
            label: '灵丘房建维修工区',
          },{
            value: '原平供热维修工区',
            label: '原平供热维修工区',
          },{
            value: '忻州西高铁综合维修工区',
            label: '忻州西高铁综合维修工区',
          }]
        }]
    }
  },
  created() {
    // 请求分页查询数据
    this.load()
  },
  methods: {
    load() {
      this.request.get("/maintenance/page",{
        params:{
          pid: this.pid,
          faultPhenomenon: this.faultPhenomenon,
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res =>{
        this.tableData = res.records
        this.total =res.total
      })
    },

    reset() {
      this.id = ""
      this.station = ""
      this.load()
    },

    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },

    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },

    add(){
      this.dialogVisible =true
      this.homeForm = {}
    },

    save(){
      this.homeForm.pid = this.pid
      this.request.post("/maintenance/save",this.homeForm).then(res =>{
        if (res){
          this.$message.success("保存成功")
          this.dialogVisible =false
          this.load()
          console.log(this.homeForm)
        }else {
          this.$message.error("保存失败")
        }
      })
    },
    changeHome(row){
      this.dialogVisible = true
      this.homeForm = row
    },
    deleteById(id){
      this.request.delete("/maintenance/"+id).then(res =>{
        if (res){
          this.$message.success("删除成功")
          this.dialogVisible =false
          this.load()
        }else {
          this.$message.error("删除失败")
        }
      })
    },
    handleExcelImportSuccess() {
      this.$message.success("导入成功")
      this.load()
    },
    exp(){
      window.open("http://39.107.69.103:8888/home/export")
    },
    skip(id){
      this.$router.push({path:'maintenance',query:{maintenanceId:id}})
},
    getArea(value) {
      this.area= value[1]
    },

    // 时间格式化
    // dateFormat(row, column) {
    //   var date = row[column.property];
    //   if (date == undefined) {
    //     return "";
    //   }
    //   return moment(date).format("YYYY-MM-DD HH:mm");
    // },
  },
}
</script>

<style>
.headerBg {
  background: #eee!important;
}
</style>
