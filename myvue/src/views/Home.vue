<template>
  <div>
    <div style="margin: 10px 0;text-align: left;width: 50%;">
<!--      <el-input style="width: 190px" placeholder="请输入id" suffix-icon="el-icon-search" v-model="id"></el-input>-->
<!--      <el-input style="width: 190px" placeholder="请输入车间名" suffix-icon="el-icon-message" class="ml-5" v-model="station"></el-input>-->
      <el-cascader
            ref="cascaderHandle"
            v-model="value"
            :options="options"
            @change="getArea"
            placeholder="请选择所属车间及所属工区"
            clearable>
        </el-cascader>
      <el-input style="width: 190px" placeholder="请输入安装位置" suffix-icon="el-icon-location-outline" class="ml-10" v-model="boxAddress"></el-input>
      <el-button class="ml-10 button_01" type="primary" @click="load">搜 索</el-button>
      <el-button class="ml-10 button_01"  type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0;text-align: left;width: 50%;">
      <el-button type="primary" class="button_01" style="width: 200px" @click="addBox">新增 <i class="el-icon-plus"></i></el-button>
<!--      <el-upload-->
<!--          action="http://localhost:8888/home/import"-->
<!--          :show-file-list="false"-->
<!--          accept="xlsx"-->
<!--          :on-success="handleExcelImportSuccess"-->
<!--          style="display: inline-block"-->
<!--          class="ml-20">-->
<!--        <el-button type="primary" class="button_01">导入 <i class="el-icon-bottom"></i></el-button>-->
<!--      </el-upload>-->
<!--      <el-button type="danger" class="button_01 ml-20" @click="exp">导出 <i class="el-icon-top"></i></el-button>-->
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg">
      <el-table-column prop="boxId" label="配电箱台帐号" width="150"></el-table-column>
      <el-table-column prop="station" label="车间" width="200"></el-table-column>
      <el-table-column prop="area" label="工区" width="220"></el-table-column>
      <el-table-column prop="boxAddress" label="安装位置" width="220"></el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="primary" class="button_01 ml-20" plain @click="skipBox(scope.row.id)">配电箱信息 <i class="el-icon-remove-outline"></i></el-button>
          <el-button type="primary" class="button_01 ml-20" plain @click="skipInspection(scope.row.boxId)">巡检信息 <i class="el-icon-remove-outline"></i></el-button>
          <el-button type="primary" class="button_01 ml-20" plain @click="skipMaintenance(scope.row.boxId)">抢修信息 <i class="el-icon-remove-outline"></i></el-button>
          <el-popconfirm
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="deleteHome(scope.row.id)"
          >
            <el-button type="danger" class="button_01 ml-10" plain slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
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
        title="新增配电箱"
        :visible.sync="dialogVisible"
        width="50%">
      <el-form
          label-width="150px"
          :model="homeForm"
          :rules="rules">
        <el-form-item label="车间及工区">
          <el-cascader
              style="width: 100%"
              v-model="valueDialog"
              :options="options"
              @change="setArea">
          </el-cascader>
        </el-form-item>
        <el-form-item label="安装位置">
          <el-input v-model="homeForm.boxAddress" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="台帐号" prop="boxId">
          <el-input v-model="homeForm.boxId" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveHome">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
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
      boxAddress:'',
      boxId: 2,
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
        },
        {
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
        },
        {
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
        },
        {
          value: '太原北房建车间',
          label: '太原北房建车间',
          children: [{
            value: '路局西院维修工区',
            label: '路局西院维修工区',
          } ]
        }]
      ,optionsDialog: [{
        valueDialog: '秦皇岛房建车间',
        label: '秦皇岛房建车间',
        children: [{
          valueDialog: '西张庄综合维修工区',
          label: '西张庄综合维修工区',
        }, {
          valueDialog: '秦东综合维修工区',
          label: '秦东综合维修工区',
        }, {
          valueDialog: '柳村综合维修工区',
          label: '柳村综合维修工区',
        },{
          valueDialog: '迁安综合维修工区',
          label: '迁安综合维修工区',
        },{
          valueDialog: '遵化综合维修工区',
          label: '遵化综合维修工区',
        }]
      },
        {
          valueDialog: '茶坞房建车间',
          label: '茶坞房建车间',
          children: [{
            valueDialog: '茶坞综合维修工区',
            label: '茶坞综合维修工区',
          }, {
            valueDialog: '蓟县西综合维修工区',
            label: '蓟县西综合维修工区',
          }]
        },
        {
          valueDialog: '湖东房建车间',
          label: '湖东房建车间',
          children: [{
            valueDialog: '湖东供热维修工区',
            label: '湖东供热维修工区',
          }, {
            valueDialog: '湖东房建维修工区',
            label: '湖东房建维修工区',
          }, {
            valueDialog: '逐鹿综合维修工区',
            label: '逐鹿综合维修工区',
          }]
        },
        {
          valueDialog: '大同东房建车间',
          label: '大同东房建车间',
          children: [{
            valueDialog: '新华后街房建维修工区',
            label: '新华后街房建维修工区',
          }, {
            valueDialog: '站北街供热维修工区',
            label: '站北街供热维修工区',
          }, {
            valueDialog: '同丰电气维修工区',
            label: '同丰电气维修工区',
          },{
            valueDialog: '卧虎湾房建维修工区',
            label: '卧虎湾房建维修工区',
          },{
            valueDialog: '柴沟堡综合维修工区',
            label: '柴沟堡综合维修工区',
          }]
        },
        {
          valueDialog: '大同西房建车间',
          label: '大同西房建车间',
          children: [{
            valueDialog: '口泉综合维修工区',
            label: '口泉综合维修工区',
          }, {
            valueDialog: '大同西房建维修工区',
            label: '大同西房建维修工区',
          }, {
            valueDialog: '大同西房建巡检工区',
            label: '大同西房建巡检工区',
          },{
            valueDialog: '西线供热维修工区',
            label: '西线供热维修工区',
          },{
            valueDialog: '拥军路供热维修工区',
            label: '拥军路供热维修工区',
          },{
            valueDialog: '桥西供热维修工区',
            label: '桥西供热维修工区',
          }]
        },
        {
          valueDialog: '大同南房建车间',
          label: '大同南房建车间',
          children: [{
            valueDialog: '大同南高铁综合维修工区',
            label: '大同南高铁综合维修工区',
          }, {
            valueDialog: '阳高南高铁综合维修工区',
            label: '阳高南高铁综合维修工区',
          }, {
            valueDialog: '应县综合维修工区',
            label: '应县综合维修工区',
          },{
            valueDialog: '大同南综合工区',
            label: '大同南综合工区',
          },{
            valueDialog: '大同房管工区',
            label: '大同房管工区',
          }]
        },
        {
          valueDialog: '朔州房建车间',
          label: '朔州房建车间',
          children: [{
            valueDialog: '朔州房建维修工区',
            label: '朔州房建维修工区',
          }, {
            valueDialog: '朔州供热维修工区',
            label: '朔州供热维修工区',
          }, {
            valueDialog: '偏关综合维修工区',
            label: '偏关综合维修工区',
          }]
        },
        {
          valueDialog: '原平房建车间',
          label: '原平房建车间',
          children: [{
            valueDialog: '忻州房建维修工区',
            label: '忻州房建维修工区',
          }, {
            valueDialog: '原平房建维修工区',
            label: '原平房建维修工区',
          }, {
            valueDialog: '灵丘房建维修工区',
            label: '灵丘房建维修工区',
          }, {
            valueDialog: '原平供热维修工区',
            label: '原平供热维修工区',
          }, {
            valueDialog: '忻州西高铁综合维修工区',
            label: '忻州西高铁综合维修工区',
          }]
        },
        {
          value: '太原北房建车间',
          label: '太原北房建车间',
          children: [{
            value: '路局西院维修工区',
            label: '路局西院维修工区',
          } ]
        }
      ],
      errors: {},
      rules: {
        boxId: [
          { required: true, message: '请输入台帐号', trigger: 'blur' },//失焦时触发
        ]
      },
      valueDialog:[],
      value:[],
    }
  },
  created() {
    // 请求分页查询数据
    this.load()
  },
  methods: {
    load() {
      this.request.get("/box/page",{
        params:{
          id: this.id,
          area: this.area,
          address: this.boxAddress,
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res =>{
        this.tableData = res.records
        this.total =res.total
      })
    },

    reset() {
      this.$refs.cascaderHandle.checkedValue=""
      this.area = ''
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

    addBox(){
      this.dialogVisible =true
      this.homeForm = {}
    },

    saveHome(){
      this.homeForm.systemUrl = '0d0bb679724743399745578319259715.jpg'
      this.homeForm.firstUrl = '0d0bb679724743399745578319259715.jpg'
      this.homeForm.secondUrl = '0d0bb679724743399745578319259715.jpg'
      this.homeForm.thirdUrl = '0d0bb679724743399745578319259715.jpg'
      this.homeForm.fourthUrl = '0d0bb679724743399745578319259715.jpg'
      if (this.homeForm.boxId){
        this.request.post("/box/save",this.homeForm).then(res =>{
          if (res){
            this.$message.success("保存成功")
            this.dialogVisible =false
            this.load()
          }else {
            this.$message.error("保存失败")
          }
        })
      }else {
        this.$message.error("请输入台帐号")
      }

    },
    changeHome(row){
      this.dialogVisible = true
      this.homeForm = row
    },
    deleteHome(id){
      this.request.delete("/box/"+id).then(res =>{
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
    skipBox(id){
      this.$router.push({path:'box',query:{id:id}})
      this.request.get("/box/"+id).then(res =>{
        if (res){
          this.$message.success("跳转至配电箱信息")
        }else {
          this.$message.error("跳转失败")
        }
      })
    },
    skipMaintenance(pid){
      this.$router.push({path:'maintenanceHome',query:{pid:pid}})
      this.$message.success("跳转至抢修信息")
    },
    skipInspection(pid){
      this.request.get("/inspection/getByPid/"+pid).then(res =>{
        if (res){
          this.$router.push({path:'inspection',query:{pid:pid}})
          this.$message.success("跳转至巡检信息")
        }else{
          this.addInspection(pid)
        }
      })
      // this.$router.push({path:'inspection',query:{pid:pid}})
      // this.$message.success("跳转至巡检信息")
    },
    addInspection(pid){
      this.request.post("/check/addCheck/"+pid).then(res =>{

      })

      this.request.post("/inspection/saveByPid/"+pid).then(res =>{
        if (res){
          this.$router.push({path:'inspection',query:{pid:pid}})
          this.$message.success("跳转至巡检信息")
        }else {
          this.$message.error("新建巡检记录失败")
        }
      })
    },
    getArea(value) {
      this.area= value[1]
    },
    setArea(value) {
      this.homeForm.station= value[0]
      this.homeForm.area= value[1]
    },
    handleSubmit() {
      this.errors = {}; // Clear previous errors
      if (!this.homeForm.boxId) {
        this.errors.boxId = '台帐号不能为空';
      }
    }
  },
}
</script>

<style>
.headerBg {
  background: #eee!important;
}
</style>
