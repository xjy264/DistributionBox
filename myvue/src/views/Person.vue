<template>
  <div>
  <el-card style="width: 800px;" shadow="hover">
        <el-form :model="form" label-width="80px">
          <el-upload
              class="avatar-uploader"
              action="http://39.107.69.103:8888/files/upload"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              align="center"
              style="padding-bottom: 10px;">
            <img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>

          <el-form-item label="用户名" style="width: 650px">
            <el-input v-model="form.username" autocomplete="off"></el-input>
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
          <el-form-item>
            <el-button type="primary" style="width:  125px" @click="save">确 定</el-button>
          </el-form-item>
        </el-form>
  </el-card>
  </div>
</template>

<script>
export default {
  name: "Person",
  data(){
    return {
      form: {
        username: '',
        nickname: '',
        email: '',
        phone: '',
        address: '',
        avatarUrl: ''
      },
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):""
    }
  },
  methods:{
    save() {
      this.request.post("/user", this.form).then(res => {
        if (res.data) {
          this.$message.success("保存成功")

          //触发父级更新User
          this.$emit("refreshUser")
        } else {
          this.$message.error("保存失败")
        }
      })
    },
    handleAvatarSuccess(res) {
      this.form.avatarUrl = res;
    }
  },
  created() {
    this.request.post("/user/username/"+this.user.username).then(res=>{
      if (res.code==="200"){
        this.form=res.data
      }
    })
  }
}
</script>

<style scoped>
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 138px;
  height: 138px;
  line-height: 138px;
  text-align: center;
}
.avatar {
  width: 138px;
  height: 138px;
  display: block;
}
</style>
