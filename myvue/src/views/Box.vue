<template>
  <div>
    <div style="margin: 10px 0;text-align: left;width: 50%;">
      <el-button type="primary" class="button_01" @click="changeBox">修改 <i class="el-icon-edit"></i></el-button>
      <el-button type="primary" class="button_01" style="width: 200px" @click="addBox">新增电器元件 <i class="el-icon-plus"></i></el-button>
      <el-button type="warning" class="button_01" style="width: 200px" @click="addBoxRecord">保存配电箱数据记录 <i class="el-icon-top"></i></el-button>
      <el-button type="warning" class="button_01" style="width: 200px" @click="print">打印 <i class="el-icon-top"></i></el-button>
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

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg">
      <el-table-column prop="componentsName" label="电器元件" width="140"></el-table-column>
      <el-table-column prop="componentsUnit" label="单位" width="120"></el-table-column>
      <el-table-column prop="componentsQuantity" label="数量" width="120"></el-table-column>
      <el-table-column prop="componentsType" label="厂家/型号" width="200"></el-table-column>
      <el-table-column prop="controlScope" label="回路控制范围" width="200"></el-table-column>
      <el-table-column prop="inSpecifications" label="进线规格" width="100"></el-table-column>
      <el-table-column prop="outSpecifications" label="出线规格" width="100"></el-table-column>
      <el-table-column prop="componentsState" label="技术状态" width="200"></el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="success" class="button_01" style=" margin-right: 20px;" @click="changeComponents(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="deleteComponents(scope.row.id)"
          >
            <el-button type="danger" class="button_01"  slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

<!--    <div class="demo-image__preview">-->
<!--      <el-image-->
<!--          style="width: 100px; height: 100px"-->
<!--          :src="systemUrl"-->
<!--          :preview-src-list="srcList">-->
<!--      </el-image>-->
<!--    </div>-->
    <el-tag style="font-size: medium" >系统图：</el-tag>
    <div style="display: flex;justify-content: center;">
      <el-image
          :src="url+this.systemUrl"
          class="w_500"
          v-viewer>
      </el-image>
    </div>

    <el-tag type="success" style="font-size: medium">配电箱图片1：</el-tag>
    <div style="display: flex;justify-content: center;">
      <el-image
          :src="url+this.firstUrl"
          class="w_500"
          v-viewer>
      </el-image>
    </div>

    <el-tag type="success" style="font-size: medium">配电箱图片2：</el-tag>
    <div style="display: flex;justify-content: center;">
      <el-image
          :src="url+this.secondUrl"
          class="w_500"
          v-viewer>
      </el-image>
    </div>

    <el-tag type="success" style="font-size: medium">配电箱图片3：</el-tag>
    <div style="display: flex;justify-content: center;">
      <el-image
          :src="url+this.thirdUrl"
          class="w_500"
          v-viewer>
      </el-image>
    </div>

    <el-tag type="success" style="font-size: medium">配电箱图片4：</el-tag>
    <div style="display: flex;justify-content: center;">
      <el-image
          :src="url+this.fourthUrl"
          class="w_500"
          style="margin: auto"
          v-viewer>
      </el-image>
    </div>

    <el-dialog
        title="配电箱信息"
        :visible.sync="dialogFormVisible"
        width="50%">
      <el-form label-width="150px" :model="form">
        <el-form-item label="车间">
          <el-input v-model="form.station" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="工区">
          <el-input v-model="form.area" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="安装地点">
          <el-input v-model="form.boxAddress" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="台帐号">
          <el-input disabled="disabled" v-model="form.boxId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="配电箱规格">
          <el-input v-model="form.size" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="系统图">
          <el-upload action="http://39.107.69.103:8888/files/upload"
                     style="display: inline-block"
                     :show-file-list="false"
                     accept="xlsx"
                     :on-success="systemUrlSuccess">
            <el-button type="primary" style="width: 125px" class="ml-10">上传 <i class="el-icon-circle-plus-outline"></i></el-button>
          </el-upload>
          <el-popconfirm
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="deleteSystemUrl()"
          >
            <el-button type="danger" class="button_01 ml-20"  slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </el-form-item>

        <el-form-item label="配电箱图片1">
          <el-upload action="http://39.107.69.103:8888/files/upload"
                     style="display: inline-block"
                     :show-file-list="false"
                     accept="xlsx"
                     :on-success="firstUrlSuccess">
            <el-button type="primary" style="width: 125px" class="ml-10">上传 <i class="el-icon-circle-plus-outline"></i></el-button>
          </el-upload>
          <el-popconfirm
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="deleteFirstUrl()"
          >
            <el-button type="danger" class="button_01 ml-20"  slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </el-form-item>

        <el-form-item label="配电箱图片2">
          <el-upload action="http://39.107.69.103:8888/files/upload"
                     style="display: inline-block"
                     :show-file-list="false"
                     accept="xlsx"
                     :on-success="secondUrlSuccess">
            <el-button type="primary" style="width: 125px" class="ml-10">上传 <i class="el-icon-circle-plus-outline"></i></el-button>
          </el-upload>
          <el-popconfirm
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="deleteSecondUrl()"
          >
            <el-button type="danger" class="button_01 ml-20"  slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </el-form-item>

        <el-form-item label="配电箱图片3">
          <el-upload action="http://39.107.69.103:8888/files/upload"
                     style="display: inline-block"
                     :show-file-list="false"
                     accept="xlsx"
                     :on-success="thirdUrlSuccess">
            <el-button type="primary" style="width: 125px" class="ml-10">上传 <i class="el-icon-circle-plus-outline"></i></el-button>
          </el-upload>
          <el-popconfirm
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="deleteThirdUrl()"
          >
            <el-button type="danger" class="button_01  ml-20"  slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </el-form-item>

        <el-form-item label="配电箱图片4">
          <el-upload action="http://39.107.69.103:8888/files/upload"
                     style="display: inline-block"
                     :show-file-list="false"
                     accept="xlsx"
                     :on-success="fourthUrlSuccess">
            <el-button type="primary" style="width: 125px" class="ml-10">上传 <i class="el-icon-circle-plus-outline"></i></el-button>
          </el-upload>
          <el-popconfirm
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="deleteFourthUrl()"
          >
            <el-button type="danger" class="button_01 ml-20"  slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveBox">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog
        title="电器元件信息"
        :visible.sync="dialogComponentsVisible"
        width="50%">
      <el-form label-width="150px" :model="componentsForm">
        <el-form-item label="电器元件">
          <el-input v-model="componentsForm.componentsName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="单位">
          <el-input v-model="componentsForm.componentsUnit" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="数量">
          <el-input v-model="componentsForm.componentsQuantity" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="厂家/型号">
          <el-input v-model="componentsForm.componentsType" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="回路控制范围">
          <el-input v-model="componentsForm.controlScope" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="进线规格">
          <el-input v-model="componentsForm.inSpecifications" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="出线规格">
          <el-input v-model="componentsForm.outSpecifications" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="技术状态">
          <el-input v-model="componentsForm.componentsState" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogComponentsVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveComponents">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {serverIp} from "../../public/config";

export default {
  name: "Box",
  data() {
    return{
      firstTableData:[],
      secondTableData:[],
      tableData:[],
      total: 0,
      id: '',
      pageNum: 1,
      pageSize: 5,
      boxId: this.$route.query.id,
      dialogFormVisible: false,
      dialogComponentsVisible: false,
      dialogFixVisible: false,
      form: {},
      systemUrl: '',
      firstUrl: '',
      secondUrl: '',
      thirdUrl: '',
      fourthUrl: '',
      componentsForm: {
        boxId: 1
      },
      fixForm: {
        boxId: 1,
        // address: this.firstTableData[0].value1,
        // code: this.firstTableData[0].value2
      },
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):"",
      headerBg: 'headerBg',
      url: 'http://39.107.69.103/files/',
      url1: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg'
    }
  },
  created() {
    this.getBox()
    // 请求分页查询数据
    this.load()
  },
  methods: {
    load() {
      this.request.get("/components/"+this.boxId).then(res =>{
        this.tableData = res
      })
    },

    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },

    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },

    getBox(){
      this.request.get("/box/"+this.boxId).then(res =>{
        this.firstTableData = [
          {
            name1: '所属车间',
            value1: res.station,
            name2: '所属工区',
            value2: res.area,
          },{
            name1: '安装地点',
            value1: res.boxAddress,
            name2: '台帐号',
            value2: res.boxId,
          },{
            name1: '配电箱规格',
            value1: res.size,
          }]
        this.systemUrl = res.systemUrl
        this.firstUrl = res.firstUrl
        this.secondUrl = res.secondUrl
        this.thirdUrl = res.thirdUrl
        this.fourthUrl = res.fourthUrl
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
          console.log(this.form)
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
    systemUrlSuccess(res){
      this.$message.success("系统图上传成功")
      this.form.systemUrl=res
    },
    firstUrlSuccess(res){
      this.$message.success("配电箱图片1上传成功")
      this.form.firstUrl=res
    },
    secondUrlSuccess(res){
      this.$message.success("配电箱图片2上传成功")
      this.form.secondUrl=res
    },
    thirdUrlSuccess(res){
      this.$message.success("配电箱图片3上传成功")
      this.form.thirdUrl=res
    },
    fourthUrlSuccess(res){
      this.$message.success("配电箱图片4上传成功")
      this.form.fourthUrl=res
    },
    deleteSystemUrl(){
      this.form.systemUrl = '0d0bb679724743399745578319259715.jpg'
      this.$message.success("系统图删除成功")
    },
    deleteFirstUrl(){
      this.form.firstUrl = '0d0bb679724743399745578319259715.jpg'
      this.$message.success("配电箱图片1删除成功")
    },
    deleteSecondUrl(){
      this.form.secondUrl = '0d0bb679724743399745578319259715.jpg'
      this.$message.success("配电箱图片2删除成功")
    },
    deleteThirdUrl(){
      this.form.thirdUrl = '0d0bb679724743399745578319259715.jpg'
      this.$message.success("配电箱图片3删除成功")
    },
    deleteFourthUrl(){
      this.form.fourthUrl = '0d0bb679724743399745578319259715.jpg'
      this.$message.success("配电箱图片4删除成功")
    },
    addBoxRecord(){

    },
    print(){
      window.open("http://39.107.69.103:8888/box/print/"+this.boxId)
    }
  },
}
</script>

<style>
.headerBg {
  background: #eee!important;
}
</style>
