<template>
<div>

  <div style="margin: 10px 0">
    <el-input style="width: 300px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
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
  </div>

  <el-dialog title="菜单信息" :visible.sync="dialogFormVisible" width="800px">
    <el-form :model="form" label-width="80px">
      <el-form-item label="名称" style="width: 650px">
        <el-input v-model="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="路径" style="width: 650px">
        <el-input v-model="form.path" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="页面路径" style="width: 650px">
        <el-input v-model="form.pagePath" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="图标" style="width: 650px">
        <el-select clearable v-model="form.icon" placeholder="请选择" style="width: 100%">
          <el-option v-for="item in options" key="item.name" :label="item.name" :value="item.value">
            <i :class="item.value"/>{{item.name}}
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="描述" style="width: 650px">
        <el-input v-model="form.description" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>

  <el-table
      :data="tableData"
      border
      stripe
      default-expand-all
      row-key="id"
      :header-cell-class-name="headerBg"
      @selection-change="handleSelectionChange"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
    <el-table-column type="selection" width="55"/>
    <el-table-column prop="id" label="id" width="100" align="center"/>
    <el-table-column prop="name" label="名称" width="140" align="center"/>
    <el-table-column prop="path" label="路径" width="120" align="center"/>
    <el-table-column prop="pagePath" label="页面路径" width="120" align="center"/>
    <el-table-column prop="icon" label="图标" width="220" align="center" class-name="fontSize20" label-class-name="fontSize12">
      <template slot-scope="scope">
        <i :class="scope.row.icon"/>
      </template>
    </el-table-column>
    <el-table-column prop="description" label="描述" width="220" align="center"/>
    <el-table-column label="操作" align="center">
      <template slot-scope="scope">
        <el-button type="primary" style="width: 125px; margin-right: 10px" @click="addChildren(scope.row.id)" v-if="!scope.row.pid && !scope.row.path">新增子菜单 <i class="el-icon-circle-plus-outline"></i></el-button>
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
          <el-button type="danger" style="width: 125px;" class="ml-10"  slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
</div>
</template>

<script>
export default {
  name: "Menu",
  data(){
    return {
      tableData: [],
      name: "",
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
      headerBg: 'headerBg',
      dialogFormVisible: false,
      form: {
        name: '',
        path: '',
        icon: '',
        description: ''
      },
      multipleSelection: [],
      options: []
    }
  },
  created(){
    this.load();
  },
  methods: {
    load() {
      this.request.get("/menu", {
        params: {
          name: this.name
        }
      }).then(res => {
        console.log(res)
        this.tableData = res
      })
    },
    reset() {
      this.name = ''
      this.load()
    },
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}

      this.request.get("/menu/icons").then(res => {
        this.options = res.data
      })
    },
    handleEdit(row) {
      this.dialogFormVisible = true
      this.form = row

      this.request.get("/menu/icons").then(res => {
        this.options = res.data
      })
    },
    save() {
      this.request.post("/menu", this.form).then(res => {
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
      this.request.delete("/menu/" + id).then(res => {
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
      this.request.post("/menu/del/batch", ids).then(res => {
        if (res.data) {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
    addChildren(pid){
      this.dialogFormVisible = true
      this.form = {}
      if (pid){
        this.form.pid=pid
      }
    }
  }
}
</script>

<style>
.headerBg {
  background: #eee!important;
}
.fontSize20{
  font-size: 20px;
}
.fontSize12{
  font-size: 12px;
}
</style>
