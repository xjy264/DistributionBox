<template>
  <div>
    <div style="margin: 10px 0;text-align: left;width: 50%;">
      <el-input style="width: 190px" placeholder="请输入配电箱id" suffix-icon="el-icon-search" class="ml-5" v-model="boxId"></el-input>
      <el-input style="width: 190px" placeholder="请输入配电箱代码" suffix-icon="el-icon-message" class="ml-5" v-model="code"></el-input>
      <el-input style="width: 190px" placeholder="请输入巡检员姓名" suffix-icon="el-icon-user" class="ml-5" v-model="user"></el-input>
      <el-button class="ml-5 button_01" type="primary" @click="load">搜 索</el-button>
<!--      <el-button class="ml-5 button_01"  type="warning" @click="reset">重置</el-button>-->
    </div>

<!--    <div style="margin: 10px 0;text-align: left;width: 50%;">-->
<!--&lt;!&ndash;      <el-button type="primary" class="button_01" style="width: 200px" @click="addHome">新增 <i class="el-icon-plus"></i></el-button>&ndash;&gt;-->
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
<!--    </div>-->

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg">
      <el-table-column prop="boxId" label="配电箱 id" width="70" align="center"></el-table-column>
      <el-table-column prop="code" label="配电箱代码" width="160" align="center"></el-table-column>
      <el-table-column prop="time" label="报修时间" width="180" align="center" :formatter="dateFormat" ></el-table-column>
      <el-table-column prop="user" label="巡检员" width="160" align="center"></el-table-column>
      <el-table-column prop="address" label="安装地点" width="200" align="center"></el-table-column>
      <el-table-column prop="result" label="报修原因" width="120" align="center"></el-table-column>
      <el-table-column prop="remark" label="备注" width="220" align="center"></el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="success" class="button_01" style=" margin-right: 20px;" @click="changeHome(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="deleteHome(scope.row.id)"
          >
            <el-button type="danger" class="button_01"  slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
          <el-button type="primary" class="button_01 ml-20"  @click="skip(scope.row.boxId)">跳转 <i class="el-icon-remove-outline"></i></el-button>
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
        title="报修记录信息"
        :visible.sync="dialogVisible"
        width="50%">
      <el-form label-width="150px" :model="homeForm">
        <el-form-item label="配电箱编号">
          <el-input v-model="homeForm.boxId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="配电箱代码">
          <el-input v-model="homeForm.code" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="巡检员">
          <el-input v-model="homeForm.user" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="安装地点">
          <el-input v-model="homeForm.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="巡检结果">
          <el-input v-model="homeForm.result" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="homeForm.remark" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveFix">确 定</el-button>
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
      time:'',
      id: '',
      boxId: '',
      station:'',
      code:'',
      user:'',
      pageNum: 1,
      pageSize: 5,
      // boxId: 2,
      dialogFormVisible: false,
      dialogVisible: false,
      form: {},
      homeForm: {},
      headerBg: 'headerBg',
    }
  },

  created() {
    // 请求分页查询数据
    this.load()
  },
  methods: {
    load() {
      this.request.get("/fix/page",{
        params:{
          boxId: this.boxId,
          code: this.code,
          user: this.user,
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res =>{
        this.tableData = res.records
        this.total =res.total
        this.fixTime= this.$moment(tableData.time).format('YYYY-MM-DD')
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

    saveFix(){
      this.request.post("/fix/save",this.homeForm).then(res =>{
        if (res){
          this.$message.success("保存成功")
          this.dialogVisible =false
          this.load()
        }else {
          this.$message.error("保存失败")
        }
      })
    },

    changeHome(row){
      this.dialogVisible = true
      this.homeForm = row
    },

    deleteHome(id){
      this.request.delete("/fix/"+id).then(res =>{
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
      this.$router.push({path:'box',query:{id:id}})

      this.request.get("/box/"+id).then(res =>{
        if (res){
          this.$message.success("跳转成功")
        }else {
          this.addBox(id)
        }
      })
    },

    // 时间格式化
    dateFormat(row, column) {
      var date = row[column.property];
      if (date == undefined) {
        return "";
      }
      return moment(date).format("YYYY-MM-DD HH:mm");
    },

    addBox(id){
      this.request.post("/box/saveById/"+id).then(res =>{
        if (res){
          this.$message.success("新建配电箱成功")
        }else {
          this.$message.error("新建配电箱失败")
        }
      })
    }
  },
}
</script>

<style>
.headerBg {
  background: #eee!important;
}
</style>
