<template>
  <div>
    <div class="toolbar">
      <el-input v-model="name" placeholder="课程名称" class="field" />
      <el-button @click="load">查询</el-button>
      <el-button type="success" @click="openDialog">新增</el-button>
    </div>

    <div class="toolbar">
      <el-input v-model="teacherId" placeholder="教师ID" class="field" />
      <el-button @click="loadTeacher">教师课程</el-button>
      <el-input v-model="studentId" placeholder="学生ID" class="field" />
      <el-button @click="loadStudent">学生课程</el-button>
      <el-input v-model="selectCourseId" placeholder="课程ID" class="field" />
      <el-button type="primary" @click="selectCourse">选课</el-button>
    </div>

    <el-table :data="tableData" border>
      <el-table-column prop="name" label="课程" />
      <el-table-column prop="score" label="学分" />
      <el-table-column prop="times" label="时间" />
      <el-table-column prop="teacher" label="教师" />
      <el-table-column prop="state" label="状态" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" @click="edit(scope.row)">编辑</el-button>
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

    <el-dialog v-model="dialogVisible" title="课程" width="600px">
      <EntityForm v-model="form" :fields="fields" />
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import http from '@/api/http'
import EntityForm from '@/components/EntityForm.vue'

const name = ref('')
const teacherId = ref('')
const studentId = ref('')
const selectCourseId = ref('')

const tableData = ref<any[]>([])
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const dialogVisible = ref(false)
const form = reactive<any>({})

const fields = [
  { key: 'name', label: '课程名称' },
  { key: 'score', label: '学分' },
  { key: 'times', label: '上课时间' },
  { key: 'state', label: '是否开课' },
  { key: 'teacherId', label: '教师ID' }
]

const load = async () => {
  const res = await http.get('/course/page', {
    params: { pageNum: pageNum.value, pageSize: pageSize.value, name: name.value }
  })
  tableData.value = res.data.data.content
  total.value = res.data.data.totalElements
}

const loadTeacher = async () => {
  if (!teacherId.value) return
  const res = await http.get(`/course/find/teacherCourses/${teacherId.value}`)
  tableData.value = res.data.data
  total.value = res.data.data.length
}

const loadStudent = async () => {
  if (!studentId.value) return
  const res = await http.get(`/course/find/studentCourses/${studentId.value}`)
  tableData.value = res.data.data
  total.value = res.data.data.length
}

const selectCourse = async () => {
  if (!studentId.value || !selectCourseId.value) return
  await http.get('/course/selectCourse', { params: { studentId: studentId.value, courseId: selectCourseId.value } })
  loadStudent()
}

const openDialog = () => {
  Object.keys(form).forEach((k) => delete form[k])
  dialogVisible.value = true
}

const edit = (row: any) => {
  Object.assign(form, row)
  dialogVisible.value = true
}

const save = async () => {
  await http.post('/course/save', form)
  dialogVisible.value = false
  load()
}

const remove = async (id: number) => {
  await http.delete(`/course/${id}`)
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
  flex-wrap: wrap;
}
.field {
  width: 160px;
}
.pager {
  margin-top: 12px;
}
</style>
