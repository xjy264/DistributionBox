<template>
  <div>

    <div style="margin: 10px 0">
      <el-input style="width: 300px" placeholder="请输入文件名称" suffix-icon="el-icon-search" v-model="name"></el-input>
     <el-button class="ml-10" type="primary" style="width: 125px" @click="load">搜索</el-button>
      <el-button class="ml-10" type="warning" style="width: 125px" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-upload action="http://39.107.69.103:8888/files/upload"
                 style="display: inline-block"
                 :show-file-list="false"
                 accept="xlsx"
                 :on-success="handleExcelImportSuccess">
        <el-button type="primary" style="width: 125px" class="ml-10">上传 <i class="el-icon-circle-plus-outline"></i></el-button>
      </el-upload>
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

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"/>
      <el-table-column prop="id" label="id" width="100" align="center"/>
      <el-table-column prop="name" label="文件名称" width="200" align="center"/>
      <el-table-column prop="type" label="文件类型" width="120" align="center"/>
      <el-table-column prop="size" label="文件大小(kb)" width="120" align="center"/>
      <el-table-column label="下载" align="center" width="220">
        <template slot-scope="scope">
          <el-button type="primary" @click="download(scope.row.url)" style="width: 125px">下载</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
<!--          <el-button type="success" style="width: 125px;" class="ml-10" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>-->
          <el-popconfirm
              class="ml-10"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除该条数据吗？"
              @confirm="del(scope.row.id)"
          >
            <el-button type="danger" style="width: 125px;" class="ml-10"  slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
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
      name: "",
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
      headerBg: 'headerBg',
      dialogFormVisible: false,
      form: {},
      multipleSelection: []
    }
  },
  created(){
    this.load();
  },
  methods: {
    load() {
      this.request.get("/files/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name
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
      this.form = {}
    },
    handleEdit(row) {
      this.dialogFormVisible = true
      this.form = row
    },
    del(id) {
      this.request.delete("/files/" + id).then(res => {
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
      this.request.post("/files/del/batch", ids).then(res => {
        if (res.data) {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
    handleExcelImportSuccess(){
      this.$message.success("上传成功")
      this.load()
    },
    download(url) {
      window.open(url)
    },
    changeEnable(row){
      this.request.post("/files/update",row).then(res=>{
        if (res.code==='200'){
          this.$message.success("操作成功")
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
