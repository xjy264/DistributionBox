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

  <el-dialog title="分配菜单" :visible.sync="menuDialogFormVisible" width="800px" style="padding: 0 50px">
    <el-tree
        :data="menuData"
        show-checkbox
        default-expand-all
        node-key="id"
        ref="tree"
        :default-checked-keys="checks"
        :props="props">
        <span class="custom-tree-node" slot-scope="{ node, data }">
          <span><i :class="data.icon"/> {{ data.name }}</span>
        </span>
    </el-tree>
    <div slot="footer" class="dialog-footer">
      <el-button @click="menuDialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="getCheckedKeys">确 定</el-button>
    </div>
  </el-dialog>

  <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="800px">
    <el-form :model="form" label-width="80px">
      <el-form-item label="名称" style="width: 650px">
        <el-input v-model="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="唯一标识" style="width: 650px">
        <el-input v-model="form.identity" autocomplete="off"></el-input>
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
      ref="tree"
      :data="tableData"
      border
      stripe
      :header-cell-class-name="headerBg"
      row-key="id"
      default-expand-all
      @selection-change="handleSelectionChange"
  >
    <el-table-column type="selection" width="55"/>
    <el-table-column prop="id" label="id" width="100" align="center"/>
    <el-table-column prop="name" label="名称" width="140" align="center"/>
    <el-table-column prop="identity" label="唯一标识" width="140" align="center"/>
    <el-table-column prop="description" label="描述" width="200" align="center"/>
    <el-table-column label="操作" align="center">
      <template slot-scope="scope">
        <el-button type="primary" style="width: 125px;" class="ml-20"  slot="reference" @click="selectMenu(scope.row)">分配菜单 <i class="el-icon-menu"></i></el-button>
        <el-button type="success" style="width: 125px;" class="ml-20" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
        <el-popconfirm
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
  name: "Role",
  data(){
    return {
      menuData: [],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
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
      menuDialogFormVisible: false,
      form: {
        name: '',
        description: ''
      },
      multipleSelection: [],
      props: {
        label: 'name'
      },
      roleId: "",
      checks: [],
      roleIdentity: []
    }
  },
  created(){
    this.load();
  },
  methods: {
    load() {
      this.request.get("/role/page", {
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
    save() {
      this.request.post("/role", this.form).then(res => {
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
      this.request.delete("/role/" + id).then(res => {
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
      this.request.post("/role/del/batch", ids).then(res => {
        if (res.data) {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
    getCheckedKeys() {
      console.log(this.$refs.tree.getCheckedKeys());
      this.request.post("/role/roleMenu/" + this.roleId,this.$refs.tree.getCheckedKeys().concat(this.$refs.tree.getHalfCheckedKeys())).then(res=>{
        if (res.code ==="200"){
          this.$message.success("绑定成功")
          this.menuDialogFormVisible = false
//        操作管理员角色后需要重新登录
          if (this.roleIdentity === 'ROLE_ADMIN'){
            this.$router.push("/login")
            localStorage.removeItem("user")
            localStorage.removeItem("menus")
          }
        }else {
          this.$message.error("绑定失败")
        }
      })
    },
    selectMenu(role) {
      this.menuDialogFormVisible = true
      this.roleId=role.id
      this.roleIdentity=role.identity
      //菜单数据
      this.request.get("/menu", {
        params: {
          name: ""
        }
      }).then(res => {
        this.menuData=res
        console.log(res)
      })

      this.request.get("/role/checks/"+this.roleId).then(res=>{
        this.checks = res.data

        this.request.get("/menu/ids").then(r=>{
          const ids =r.data
          ids.forEach(id =>{
            if(!this.checks.includes(id)){
              this.$refs.tree.setChecked(id,false)
            }
          })
        })
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
