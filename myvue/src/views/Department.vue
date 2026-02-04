<template>
<div>

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
  </div>

  <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="800px">
    <el-form :model="form" label-width="80px">
      <el-form-item label="学院" style="width: 650px">
        <el-input v-model="form.department" autocomplete="off"></el-input>
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

  <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55"/>
    <el-table-column prop="id" label="id" width="100" align="center"/>
    <el-table-column prop="department" label="学院" align="center"/>
    <el-table-column prop="address" label="地址"align="center"></el-table-column>
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
        <el-button type="info" style="width: 125px;" class="ml-10" @click="lookTeacherCourse(scope.row.id)" v-if="scope.row.role==='ROLE_TEACHER'">查看任课 <i class="el-icon-document-remove"></i></el-button>
        <el-button type="primary" style="width: 125px;" class="ml-10" @click="lookStudentCourse(scope.row.id)" v-if="scope.row.role==='ROLE_STUDENT'">课程安排 <i class="el-icon-document-remove"></i></el-button>
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
  name: "Department",
  data(){
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      username: "",
      address: "",
      email: "",
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
      headerBg: 'headerBg',
      dialogFormVisible: false,
      form: {
        department: '',
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
      this.request.get("/department/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    reset() {
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
      this.request.post("/department", this.form).then(res => {
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
      this.request.delete("/department/" + id).then(res => {
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
      this.request.post("/department/del/batch", ids).then(res => {
        if (res.data) {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
        }
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
