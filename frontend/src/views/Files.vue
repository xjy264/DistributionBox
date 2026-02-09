<template>
  <div>
    <div class="toolbar">
      <el-upload
        action="/api/files/upload"
        :show-file-list="false"
        :on-success="onUploadSuccess"
      >
        <el-button type="primary">上传文件</el-button>
      </el-upload>
      <el-input v-model="name" placeholder="文件名" class="field" />
      <el-button @click="load">查询</el-button>
    </div>

    <el-table :data="tableData" border>
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="type" label="类型" />
      <el-table-column prop="size" label="大小(KB)" />
      <el-table-column prop="url" label="链接" />
      <el-table-column label="操作" width="200">
        <template #default="scope">
          <el-button size="small" @click="download(scope.row.uuid)">下载</el-button>
          <el-button size="small" type="danger" @click="remove(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pager">
      <el-pagination
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        :page-size="pageSize"
        :current-page="pageNum"
        :page-sizes="[5, 10, 20]"
        @size-change="onSizeChange"
        @current-change="onCurrentChange"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import http from '@/api/http'

const tableData = ref<any[]>([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const name = ref('')

const load = async () => {
  const res = await http.get('/files/page', {
    params: { pageNum: pageNum.value, pageSize: pageSize.value, name: name.value }
  })
  tableData.value = res.data.data.content
  total.value = res.data.data.totalElements
}

const onUploadSuccess = () => {
  load()
}

const download = (uuid: string) => {
  window.open(`/api/files/${uuid}`)
}

const remove = async (id: number) => {
  await http.delete(`/files/${id}`)
  load()
}

const onSizeChange = (size: number) => {
  pageSize.value = size
  load()
}

const onCurrentChange = (page: number) => {
  pageNum.value = page
  load()
}

load()
</script>

<style scoped>
.toolbar {
  display: flex;
  gap: 8px;
  margin-bottom: 12px;
  align-items: center;
}
.field {
  width: 200px;
}
.pager {
  margin-top: 12px;
}
</style>
