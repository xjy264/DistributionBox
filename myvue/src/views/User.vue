 <template>
<div>

  <div style="margin: 10px 0">
    <el-input style="width: 300px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="username"></el-input>
    <el-button class="ml-10" type="primary" style="width: 125px" @click="load">搜索</el-button>
    <el-button class="ml-10" type="warning" style="width: 125px" @click="reset">重置</el-button>
  </div>

  <div style="margin: 10px 0">
    <el-button type="primary" style="width: 125px" class="ml-10" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
    <el-popconfirm
        class="ml-10"
        confirm-button-text='确定'
        cancel-button-text='我再想想'
        icon="el-icon-info"
        title="确定删除该条数据吗？"
        @confirm="delBatch"
    >
      <el-button type="danger" style="width: 125px" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
    </el-popconfirm>
    <el-upload action="http://39.107.69.103:8888/user/import"
               style="display: inline-block"
               :show-file-list="false"
               accept="xlsx"
               :on-success="handleExcelImportSuccess">
      <el-button type="primary" style="width: 125px" class="ml-10">导入 <i class="el-icon-bottom"></i></el-button>
    </el-upload>
    <el-button type="primary" style="width: 125px" class="ml-10" @click="exp">导出 <i class="el-icon-top"></i></el-button>

  </div>

  <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="800px">
    <el-form :model="form" label-width="80px">
      <el-form-item label="用户名" style="width: 650px">
        <el-input v-model="form.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="角色" style="width: 650px">
        <el-select clearable v-model="form.role" placeholder="请选择角色" style="width: 100%">
          <el-option v-for="item in roles" :key="item.name" :label="item.name" :value="item.identity" style="width: 100%"/>
        </el-select>
      </el-form-item>
      <el-form-item label="昵称" style="width: 650px">
        <el-input v-model="form.nickname" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" style="width: 650px">
        <el-input v-model="form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="联系方式" style="width: 650px">
        <el-input v-model="form.phone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="地址" style="width: 650px">
        <el-input v-model="form.address" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>

  <el-dialog title="任课信息" :visible.sync="vis" width="30%">
    <el-table :data="courses" border stripe>
      <el-table-column prop="name" label="课程名称"/>
      <el-table-column prop="score" label="学分"/>
    </el-table>
  </el-dialog>

  <el-dialog title="课程信息" :visible.sync="visStudent" width="30%">
    <el-table :data="courses" border stripe>
      <el-table-column prop="name" label="课程名称"/>
      <el-table-column prop="score" label="学分"/>
      <el-table-column prop="times" label="课时"/>
      <el-table-column prop="teacher" label="任课教师"/>
    </el-table>
  </el-dialog>

  <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55"/>
    <el-table-column prop="id" label="id" width="100" align="center"/>
    <el-table-column prop="username" label="用户名" width="140" align="center"/>
    <el-table-column prop="nickname" label="昵称" width="120" align="center"/>
    <el-table-column prop="role" label="角色信息" width="140" align="center">
      <template slot-scope="scope">
        <el-tag type="primary" v-if="scope.row.role==='ROLE_PARTYA'">维修人员</el-tag>
        <el-tag type="warning" v-if="scope.row.role==='ROLE_ADMIN'">管理员</el-tag>
        <el-tag type="success" v-if="scope.row.role==='ROLE_INSPECTORS'">巡检员</el-tag>
        <el-tag type="info" v-if="scope.row.role==='ROLE_USER'">普通用户</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="email" label="邮箱" width="200" align="center"/>
    <el-table-column prop="phone" label="联系方式" width="120" align="center"/>
    <el-table-column prop="address" label="地址" width="250" align="center">
<!--      <template slot-scope="scope">-->
<!--        <el-tag type="primary" v-if="scope.row.address==='教授'">教授</el-tag>-->
<!--        <el-tag type="success" v-if="scope.row.address==='副教授'">副教授</el-tag>-->
<!--        <el-tag type="info" v-if="scope.row.address==='讲师'">讲师</el-tag>-->
<!--        <el-tag type="warning" v-if="scope.row.address==='辅导员'">辅导员</el-tag>-->
<!--      </template>-->
    </el-table-column>
    <el-table-column label="操作" align="center">
      <template slot-scope="scope">
        <el-button type="success" style="width: 125px;" class="ml-10" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
        <el-popconfirm
            class="ml-10"
            confirm-button-text='确定'
            cancel-button-text='我再想想'
            icon="el-icon-info"
            icon-color="red"
            title="确定删除该条数据吗？"
            @confirm="del(scope.row.id)"
        >
          <el-button type="danger" style="width: 125px;" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
        </el-popconfirm>
<!--        <el-button type="info" style="width: 125px;" class="ml-10" @click="lookTeacherCourse(scope.row.id)" v-if="scope.row.role==='ROLE_TEACHER'">查看任课 <i class="el-icon-document-remove"></i></el-button>-->
<!--        <el-button type="primary" style="width: 125px;" class="ml-10" @click="lookStudentCourse(scope.row.id)" v-if="scope.row.role==='ROLE_STUDENT'">课程安排 <i class="el-icon-document-remove"></i></el-button>-->
      </template>
    </el-table-column>
  </el-table>

  <div style="padding: 10px 0">
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
  </div>
</div>
</template>

<script>
export default {
  name: "User",
  data(){
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      username: "",
      address: "",
      email: "",
      age: "",
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
      headerBg: 'headerBg',
      dialogFormVisible: false,
      form: {
        username: '',
        nickname: '',
        email: '',
        phone: '',
        address: ''
      },
      multipleSelection: [],
      roles: [],
      vis: false,
      courses: [],
      visStudent: false,
    }
  },
  created(){
    this.load();
  },
  methods: {
    load() {
      this.request.get("/user/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
          email: this.email,
          address: this.address
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })

      this.request.get("/role").then(res=>{
        this.roles = res.data
      })
    },
    reset() {
      this.username = ''
          this.email = ''
          this.address = ''
          this.pageNum = 1
          this.pageSize = 5
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
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },
    handleEdit(row) {
      this.dialogFormVisible = true
      this.form = row
    },
    save() {
      this.request.post("/user", this.form).then(res => {
        if (res.data) {
          this.$message.success("保存成功")
          this.load()
        } else {
          this.$message.error("保存失败")
        }
      })
      this.dialogFormVisible = false
      this.form = {};
    },
    del(id) {
      this.request.delete("/user/" + id).then(res => {
        if (res.data) {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id)
      this.request.post("/user/del/batch", ids).then(res => {
        if (res.data) {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
    handleExcelImportSuccess(){
      this.$message.success("导入成功")
      this.load()
    },
    exp(){
      window.open("http://39.107.69.103:8888/user/export")
      this.$message.success("导出成功")
    },
    lookTeacherCourse(id){
      this.vis = true
      this.request.get("/course/find/teacherCourses/"+id).then(res=>{
        this.courses = res.data
      })
    },
    lookStudentCourse(id){
      this.visStudent = true
      this.request.get("/course/find/studentCourses/"+id).then(res=>{
        this.courses = res.data
      })
    }
  }
}
</script>

<style>
.headerBg {
  background: #eee!important;
}
</style>
