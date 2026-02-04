<template>
  <div>
    <div style="margin: 10px 0;text-align: left;width: 50%;">
      <el-button type="primary" class="button_01" @click="changeInspection">修改 <i class="el-icon-edit"></i></el-button>
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
      <el-table-column prop="name3" label="姓名" align="center"></el-table-column>
      <el-table-column align="center">
        <!-- eslint-disable-next-line -->
        <template slot="header" slot-scope="scope">
          <span></span>
        </template>
        <template slot-scope="scope">
          <span>{{scope.row.value3}}</span>
        </template>
      </el-table-column>
    </el-table>

    <h1  align="center">配电箱及终端设备检测记录</h1>

<!--    <el-button type="primary" class="button_01" @click="changeInspection">修改 <i class="el-icon-edit"></i></el-button>-->

    <el-table :data="secondTableData" border stripe :header-cell-class-name="headerBg">
      <el-table-column prop="id" label="id" width="340" visible="false"></el-table-column>
      <el-table-column prop="content" label="检测内容" width="340"></el-table-column>
      <el-table-column prop="state" label="状态" width="320"></el-table-column>
      <el-table-column prop="remark" label="备注" width="320"></el-table-column>
      <el-table-column label="操作" align="center" >
        <template slot-scope="scope">
          <el-button type="success" class="button_01" style=" margin-right: 20px;" @click="changeCheck(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="deleteCheck(scope.row.id)"
          >
            <el-button type="danger" class="button_01"  slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-button type="primary" class="button_01" style="width: 180px" @click="changeData">修改数据记录 <i class="el-icon-edit"></i></el-button>

    <h1  align="center">配电箱及终端设备数据记录</h1>

    <el-table
        :data="thirdTableData"
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
      <el-table-column prop="name3" label="姓名" align="center"></el-table-column>
      <el-table-column align="center">
        <!-- eslint-disable-next-line -->
        <template slot="header" slot-scope="scope">
          <span></span>
        </template>
        <template slot-scope="scope">
          <span>{{scope.row.value3}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="name4" label="姓名" align="center"></el-table-column>
      <el-table-column align="center">
        <!-- eslint-disable-next-line -->
        <template slot="header" slot-scope="scope">
          <span></span>
        </template>
        <template slot-scope="scope">
          <span>{{scope.row.value4}}</span>
        </template>
      </el-table-column>
    </el-table>

    <el-button type="primary" class="button_01" style="width: 180px" @click="addRecord">新增终端信息 <i class="el-icon-edit"></i></el-button>

    <el-table :data="fourthTableData" border stripe :header-cell-class-name="headerBg">
      <el-table-column prop="number" label="序号" width="50px"></el-table-column>
      <el-table-column prop="terminalName" label="供电回路（终端名称）" width="180px"></el-table-column>
      <el-table-column prop="temperature" label="温度"></el-table-column>
      <el-table-column prop="runningCurrent" label="运行电流"></el-table-column>
      <el-table-column prop="startingCurrent" label="启动电流"></el-table-column>
      <el-table-column prop="leakage" label="漏电流"></el-table-column>
      <el-table-column prop="voltage" label="电压"></el-table-column>
      <el-table-column prop="insulation" label="绝缘电阻"></el-table-column>
      <el-table-column prop="ground" label="接地电阻"></el-table-column>
      <el-table-column prop="remark" label="备注"></el-table-column>
      <el-table-column label="操作" align="center" width="300px">
        <template slot-scope="scope">
          <el-button type="success" class="button_01" style=" margin-right: 20px;" @click="changeRecord(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="deleteRecord(scope.row.id)"
          >
            <el-button type="danger" class="button_01"  slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>


    <el-tag style="font-size: medium" >维保病害处图片：</el-tag>
    <div style="display: flex;justify-content: center;">
      <el-image
          :src="url+this.appearanceUrl"
          class="w_500"
          v-viewer>
      </el-image>
    </div>

    <el-tag type="success" style="font-size: medium" >巡检图片1：</el-tag>
    <div style="display: flex;justify-content: center;">
      <el-image
          :src="url+this.firstUrl"
          class="w_500"
          v-viewer>
      </el-image>
    </div>

    <el-tag type="success" style="font-size: medium" >巡检图片2：</el-tag>
    <div style="display: flex;justify-content: center;">
      <el-image
          :src="url+this.secondUrl"
          class="w_500"
          v-viewer>
      </el-image>
    </div>

    <el-tag type="success" style="font-size: medium" >巡检图片3：</el-tag>
    <div style="display: flex;justify-content: center;">
      <el-image
          :src="url+this.thirdUrl"
          class="w_500"
          v-viewer>
      </el-image>
    </div>

    <el-tag type="success" style="font-size: medium" >巡检图片4：</el-tag>
    <div style="display: flex;justify-content: center;">
      <el-image
          :src="url+this.fourthUrl"
          class="w_500"
          v-viewer>
      </el-image>
    </div>

    <el-tag type="success" style="font-size: medium" >巡检图片5：</el-tag>
    <div style="display: flex;justify-content: center;">
      <el-image
          :src="url+this.fifthUrl"
          class="w_500"
          v-viewer>
      </el-image>
    </div>

    <el-tag type="warning" style="font-size: medium" >维保前图片：</el-tag>
    <div style="display: flex;justify-content: center;">
      <el-image
          :src="url+this.earlierUrl"
          class="w_500"
          v-viewer>
      </el-image>
    </div>

    <el-tag type="warning" style="font-size: medium" >维保后图片：</el-tag>
    <div style="display: flex;justify-content: center;">
      <el-image
          :src="url+this.laterUrl"
          class="w_500"
          v-viewer>
      </el-image>
    </div>

    <el-dialog
        title="巡检信息"
        :visible.sync="dialogFormVisible"
        width="50%">
      <el-form label-width="150px" :model="form">
        <el-form-item label="台帐号">
          <el-input disabled="disabled" v-model="form.pid" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="巡检人员">
          <el-input v-model="form.inspectionUser" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="巡检时间">
          <el-input v-model="form.inspectionTime" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="监护人员">
          <el-input v-model="form.guardianUser" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="维保病害处图片">
          <el-upload action="http://39.107.69.103:8888/files/upload"
                     style="display: inline-block"
                     :show-file-list="false"
                     accept="xlsx"
                     :on-success="appearanceUrlSuccess">
            <el-button type="primary" style="width: 125px" class="ml-10">上传 <i class="el-icon-circle-plus-outline"></i></el-button>
          </el-upload>
          <el-popconfirm
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="deleteAppearanceUrl()"
          >
            <el-button type="danger" class="button_01 ml-20"  slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </el-form-item>

        <el-form-item label="巡检图片1">
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

        <el-form-item label="巡检图片2">
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

        <el-form-item label="巡检图片3">
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
            <el-button type="danger" class="button_01 ml-20"  slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </el-form-item>

        <el-form-item label="巡检图片4">
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

        <el-form-item label="巡检图片5">
          <el-upload action="http://39.107.69.103:8888/files/upload"
                     style="display: inline-block"
                     :show-file-list="false"
                     accept="xlsx"
                     :on-success="fifthUrlSuccess">
            <el-button type="primary" style="width: 125px" class="ml-10">上传 <i class="el-icon-circle-plus-outline"></i></el-button>
          </el-upload>
          <el-popconfirm
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="deleteFifthUrl()"
          >
            <el-button type="danger" class="button_01 ml-20"  slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </el-form-item>

        <el-form-item label="维保前图片">
          <el-upload action="http://39.107.69.103:8888/files/upload"
                     style="display: inline-block"
                     :show-file-list="false"
                     accept="xlsx"
                     :on-success="earlierUrlSuccess">
            <el-button type="primary" style="width: 125px" class="ml-10">上传 <i class="el-icon-circle-plus-outline"></i></el-button>
          </el-upload>
          <el-popconfirm
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="deleteEarlierUrl()"
          >
            <el-button type="danger" class="button_01 ml-20"  slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </el-form-item>

        <el-form-item label="维保后图片">
          <el-upload action="http://39.107.69.103:8888/files/upload"
                     style="display: inline-block"
                     :show-file-list="false"
                     accept="xlsx"
                     :on-success="laterUrlSuccess">
            <el-button type="primary" style="width: 125px" class="ml-10">上传 <i class="el-icon-circle-plus-outline"></i></el-button>
          </el-upload>
          <el-popconfirm
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="deleteLaterUrl()"
          >
            <el-button type="danger" class="button_01 ml-20"  slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveInspection">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog
        title="检测记录信息"
        :visible.sync="dialogCheckVisible"
        width="50%">
      <el-form label-width="150px" :model="checkForm">
        <el-form-item label="id" >
          <el-input disabled="disabled" v-model="checkForm.id" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="检测内容" >
          <el-input v-model="checkForm.content" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-input v-model="checkForm.state" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="checkForm.remark" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="recordDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveCheck">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog
        title="修改数据记录"
        :visible.sync="dataDialogFormVisible"
        width="50%">
      <el-form label-width="150px" :model="form">
        <el-form-item label="电源总开关型号">
          <el-input v-model="form.switchModel" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="额定电流">
          <el-input v-model="form.currentRating" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="导线截面">
          <el-input v-model="form.traverseSection" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电源电压">
          <el-input v-model="form.supplyVoltage" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="A相启动电流">
          <el-input v-model="form.astarting" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="A相运行电流">
          <el-input v-model="form.arunning" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="B相启动电流">
          <el-input v-model="form.bstarting" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="B相运行电流">
          <el-input v-model="form.brunning" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="C相启动电流">
          <el-input v-model="form.cstarting" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="C相运行电流">
          <el-input v-model="form.crunning" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dataDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveInspection">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog
        title="终端信息"
        :visible.sync="recordDialogFormVisible"
        width="50%">
      <el-form label-width="150px" :model="recordForm">
        <el-form-item label="序号">
          <el-input v-model="recordForm.number" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="供电回路（终端名称）">
          <el-input v-model="recordForm.terminalName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="温度">
          <el-input v-model="recordForm.temperature" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="运行电流">
          <el-input v-model="recordForm.runningCurrent" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="启动电流">
          <el-input v-model="recordForm.startingCurrent" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="漏电流">
          <el-input v-model="recordForm.leakage" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电压">
          <el-input v-model="recordForm.voltage" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="绝缘电阻">
          <el-input v-model="recordForm.insulation" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="接地电阻">
          <el-input v-model="recordForm.ground" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="recordForm.remark" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="recordDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRecord">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import 'viewerjs/dist/viewer.css';
export default {
  name: "Box",
  data() {
    return{
      firstTableData:[],
      secondTableData:[],
      thirdTableData:[],
      fourthTableData:[],
      tableData:[],
      total: 0,
      id: '',
      pageNum: 1,
      pageSize: 5,
      lock: 1,
      pid: this.$route.query.pid,
      dialogFormVisible: false,
      dialogComponentsVisible: false,
      dialogFixVisible: false,
      dialogCheckVisible: false,
      dataDialogFormVisible: false,
      recordDialogFormVisible:false,
      checkForm:{},
      form: {},
      recordForm: {},
      switchForm: {
        checkLock: 0,
        checkSanitation: 0,
        checkAppearance: 0,
        checkTags: 0,
        checkSystem: 0,
        checkFirewall: 0,
        checkJumper:0,
        checkBarriers: 0,
        checkZero: 0,
        checkGround: 0,
        checkTemperature: 0,
        checkComponents: 0,
      },
      firstUrl: '',
      secondUrl: '',
      thirdUrl: '',
      fourthUrl: '',
      fifthUrl: '',
      appearanceUrl: '',
      earlierUrl: '',
      laterUrl: '',
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
      imageList: [
        'https://img0.baidu.com/it/u=1033018635,7901815&fm=253&fmt=auto&app=138&f=JPEG?w=750&h=500',
     ],
      url: 'http://39.107.69.103/files/',
    }
  },
  created() {
    // 请求分页查询数据
    this.load()
  },
  methods: {
    load(){
      this.request.get("/inspection/getByPid/"+this.pid).then(res =>{
        this.form = res
        this.firstTableData = [
          {
            name1: '台帐号',
            value1: res.pid,
            name2: '巡检人员',
            value2: res.inspectionUser,
            name3: '巡检时间',
            value3: res.inspectionTime,
          },{
            name1: '监护人员',
            value1: res.guardianUser,
          }]

        this.thirdTableData = [
          {
            name1: '电源总开关型号',
            value1: res.switchModel,
            name2: '额定电流',
            value2: res.currentRating,
            name3: '导线截面',
            value3: res.traverseSection,
            name4: '电源电压',
            value4: res.supplyVoltage,
          },{
            name1: 'A相启动电流',
            value1: res.astarting,
            name2: 'A相运行电流',
            value2: res.arunning,
            name3: 'B相启动电流',
            value3: res.bstarting,
            name4: 'B相运行电流',
            value4: res.brunning,
          },{
            name1: 'C相启动电流',
            value1: res.cstarting,
            name2: 'C相运行电流',
            value2: res.crunning,
            name3: '备注',
            value3: res.remark,
          }]
        this.firstUrl = res.firstUrl
        this.secondUrl = res.secondUrl
        this.thirdUrl = res.thirdUrl
        this.fourthUrl = res.fourthUrl
        this.fifthUrl = res.fifthUrl
        this.appearanceUrl = res.appearanceUrl
        this.earlierUrl = res.earlierUrl
        this.laterUrl = res.laterUrl
      })

      this.request.get("/check/getByPid/"+this.pid).then(res =>{
        this.secondTableData = res
      })

      this.request.get("/record/getByPid/"+this.pid).then(res =>{
        this.fourthTableData = res
      })
    },

    changeInspection(){
      this.dialogFormVisible =true
    },

    changeData(){
      this.dataDialogFormVisible =true
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

    saveInspection(){
      this.request.post("/inspection/save",this.form).then(res =>{
        if (res){
          this.$message.success("保存成功")
          this.dialogFormVisible =false
          this.dataDialogFormVisible =false
          this.load()
        }else {
          this.$message.error("保存失败")
        }
      })
    },
    appearanceUrlSuccess(res){
      this.$message.success("维保病害处图片上传成功")
      this.form.appearanceUrl=res
    },
    firstUrlSuccess(res){
      this.$message.success("维保图片1上传成功")
      this.form.firstUrl=res
    },
    secondUrlSuccess(res){
      this.$message.success("维保图片2上传成功")
      this.form.secondUrl=res
      console.log(res)
    },
    thirdUrlSuccess(res){
      this.$message.success("维保图片3上传成功")
      this.form.thirdUrl=res
    },
    fourthUrlSuccess(res){
      this.$message.success("维保图片4上传成功")
      this.form.fourthUrl=res
    },
    fifthUrlSuccess(res){
      this.$message.success("维保图片5上传成功")
      this.form.fourthUrl=res
    },
    earlierUrlSuccess(res){
      this.$message.success("维保前图片上传成功")
      this.form.earlierUrl=res
    },
    laterUrlSuccess(res){
      this.$message.success("维保后图片上传成功")
      this.form.laterUrl=res
    },
    show() {
      this.$viewerApi({
        images: this.images,
      })
    },
    deleteFirstUrl(){
      this.form.firstUrl = '0d0bb679724743399745578319259715.jpg'
      this.$message.success("巡检图片1删除成功")
    },
    deleteSecondUrl(){
      this.form.secondUrl = '0d0bb679724743399745578319259715.jpg'
      this.$message.success("巡检图片2删除成功")
    },
    deleteThirdUrl(){
      this.form.thirdUrl = '0d0bb679724743399745578319259715.jpg'
      this.$message.success("巡检图片3删除成功")
    },
    deleteFourthUrl(){
      this.form.fourthUrl = '0d0bb679724743399745578319259715.jpg'
      this.$message.success("巡检图片4删除成功")
    },
    deleteFifthUrl(){
      this.form.fifthUrl = '0d0bb679724743399745578319259715.jpg'
      this.$message.success("巡检图片5删除成功")
    },
    deleteAppearanceUrl(){
      this.form.appearanceUrl = '0d0bb679724743399745578319259715.jpg'
      this.$message.success("维保病害处图片删除成功")
    },
    deleteEarlierUrl(){
      this.form.earlierUrl = '0d0bb679724743399745578319259715.jpg'
      this.$message.success("维保前图片删除成功")
    },
    deleteLaterUrl(){
      this.form.laterUrl = '0d0bb679724743399745578319259715.jpg'
      this.$message.success("维保前图片删除成功")
    },
    changeCheck(row){
      this.dialogCheckVisible = true
      this.checkForm = row
    },
    saveCheck() {
      this.request.post("/check", this.checkForm).then(res => {
        if (res) {
          this.$message.success("保存成功")
          this.load()
        } else {
          this.$message.error("保存失败")
        }
      })
      this.dialogCheckVisible = false
      this.checkForm = {};
    },
    deleteCheck(id){
      this.request.delete("/check/"+id).then(res =>{
        if (res){
          this.$message.success("删除成功")
          this.dialogCheckVisible =false
          this.load()
        }else {
          this.$message.error("删除失败")
        }
      })
    },
    addRecord() {
      this.recordDialogFormVisible = true
      this.recordForm = {}
    },
    changeRecord(row) {
      this.recordDialogFormVisible = true
      this.recordForm = row
    },
    deleteRecord(id){
      this.request.delete("/record/"+id).then(res =>{
        if (res){
          this.$message.success("删除成功")
          this.recordDialogFormVisible =false
          this.load()
        }else {
          this.$message.error("删除失败")
        }
      })
    },
    saveRecord() {
      this.recordForm.pid = this.pid
      this.request.post("/record", this.recordForm).then(res => {
        if (res.data) {
          this.$message.success("保存成功")
          this.load()
        } else {
          this.$message.error("保存失败")
        }
      })
      this.recordDialogFormVisible = false
      this.RecordForm = {};
    },
  },

}
</script>

<style>
.headerBg {
  background: #eee!important;
}
</style>
