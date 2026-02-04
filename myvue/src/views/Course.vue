<template>
<div>

  <div style="margin: 10px 0">
    <el-input style="width: 300px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
<!--    <el-input style="width: 300px" placeholder="请输入邮箱" suffix-icon="el-icon-message" v-model="email" class="ml-5"></el-input>-->
<!--    <el-input style="width: 300px" placeholder="请输入地址" suffix-icon="el-icon-position" v-model="address" class="ml-5"></el-input>-->
    <el-button class="ml-10" type="primary" style="width: 125px" @click="load">搜索</el-button>
    <el-button class="ml-10" type="warning" style="width: 125px" @click="reset">重置</el-button>
  </div>

  <div style="margin: 10px 0">
    <el-button type="primary" style="width: 125px" class="ml-10" @click="handleAdd" v-if="user.role ==='ROLE_ADMIN'">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
    <el-popconfirm
        class="ml-10"
        confirm-button-text='确定'
        cancel-button-text='我再想想'
        icon="el-icon-info"
        title="确定删除该条数据吗？"
        @confirm="delBatch"
    >
      <el-button type="danger" style="width: 125px" slot="reference"  v-if="user.role ==='ROLE_ADMIN'">批量删除 <i class="el-icon-remove-outline"></i></el-button>
    </el-popconfirm>
  </div>

  <el-dialog title="课程信息" :visible.sync="dialogFormVisible" width="800px">
    <el-form :model="form" label-width="80px">
      <el-form-item label="课程名称" style="width: 650px">
        <el-input v-model="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="学分" style="width: 650px">
        <el-input v-model="form.score" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="课时" style="width: 650px">
        <el-input v-model="form.times" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="开课状态" style="width: 650px">
        <el-select v-model="form.state" placeholder="请选择开课状态" style="width: 100%">
          <el-option label="课程开放" value="true" key="true"></el-option>
          <el-option label="课程未开放" value="false" key="false"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="任课教师" style="width: 650px">
        <el-select clearable v-model="form.teacherId" placeholder="请选择任课教师" style="width: 100%">
          <el-option v-for="item in teacher" :key="item.id" :label="item.nickname" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>

  <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55"/>
    <el-table-column prop="id" label="id" width="100" align="center"/>
    <el-table-column prop="name" label="课程名称" width="200" align="center"/>
    <el-table-column prop="score" label="学分" width="150" align="center"/>
    <el-table-column prop="times" label="课时" width="150" align="center"/>
    <el-table-column prop="state" label="开课状态" width="150" align="center">
      <template slot-scope="scope">
        <el-switch v-model="scope.row.state==1?true:false"/>
      </template>
    </el-table-column>
    <el-table-column prop="teacher" label="任课教师" width="220" align="center"/>
    <el-table-column label="操作" align="center">
      <template slot-scope="scope">
        <el-button type="success" style="width: 125px;" class="ml-10" @click="handleEdit(scope.row)" v-if="user.role ==='ROLE_ADMIN'">编辑 <i class="el-icon-edit"></i></el-button>
        <el-popconfirm
            class="ml-10"
            confirm-button-text='确定'
            cancel-button-text='我再想想'
            icon="el-icon-info"
            icon-color="red"
            title="确定删除该条数据吗？"
            @confirm="del(scope.row.id)"
        >
          <el-button type="danger" style="width: 125px;" class="ml-10"  slot="reference" v-if="user.role ==='ROLE_ADMIN'">删除 <i class="el-icon-remove-outline"></i></el-button>
        </el-popconfirm>
        <el-button type="success" style="width: 125px;" class="ml-10" @click="selectCourse(scope.row.id)" v-if="user.role ==='ROLE_STUDENT'">选课 <i class="el-icon-s-opportunity"></i></el-button>
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
  name: "Course",
  data(){
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      name: "",
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
      headerBg: 'headerBg',
      dialogFormVisible: false,
      form: {},
      multipleSelection: [],
      boolValue: true,
      teacher: [],
      user: localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):"",
      courseId: "",
    }
  },
  created(){
    this.load();
  },
  methods: {
    load() {
      this.request.get("/course/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    reset() {
          this.name = ''
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
      this.findTeacher()
      this.form = {}
    },
    handleEdit(row) {
      this.dialogFormVisible = true
      this.findTeacher()
      this.form = row
    },
    save() {
      this.request.post("/course", this.form).then(res => {
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
      this.request.delete("/course/" + id).then(res => {
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
      this.request.post("/course/del/batch", ids).then(res => {
        if (res.data) {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
    findTeacher(){
      this.request.get("/user/role/ROLE_TEACHER").then(res => {
        this.teacher = res.data
      })
    },
    selectCourse(id){
      this.courseId = id
      this.request.get("/course/selectCourse", {
        params: {
          studentId: this.user.id,
          courseId: this.courseId
        }
      }).then(res=>{
        if (res){
          this.$message.success("选课成功")
        }
      })
    },
  }
}
</script>

<style>
.headerBg {
  background: #eee!important;
}
</style>
