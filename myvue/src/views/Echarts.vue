<template>
  <div>
    <el-row :gutter="10" style="margin-bottom: 40px">
      <el-col :span="6">
        <el-card>
          <div style="color: #E6A23C">配电箱总数<i class="el-icon-s-order"/> </div>
          <div style="padding: 10px 0;text-align: center; font-weight: bold; font-size: 31px">
            327
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div style="color: #909399">涉及车站总数 <i class="el-icon-s-marketing"/></div>
          <div style="padding: 10px 0;text-align: center; font-weight: bold; font-size: 31px">
            26
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div style="color: #409EFF">待巡检数目 <i class="el-icon-s-data"/> </div>
          <div style="padding: 10px 0;text-align: center; font-weight: bold; font-size: 31px">
            15
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div style="color: #67C23A">用户数量 <i class="el-icon-postcard"/> </div>
          <div style="padding: 10px 0;text-align: center; font-weight: bold; font-size: 31px">
            9
          </div>
        </el-card>
      </el-col>
    </el-row>
    <div>
      <div id="bar" style="width: 700px;height: 600px; display:inline-block" />
      <div id="pie" style="width: 700px;height: 600px; display:inline-block" />
    </div>
  </div>
</template>

<script>
import * as echarts from "echarts";

export default {
  name: "Echarts",
  data(){
    return{

    }
  },
  //页面元素渲染好再触发
  mounted() {
    const barChartDom = document.getElementById('bar');
    const barChart = echarts.init(barChartDom);
    var barOption;

    const pieChartDom = document.getElementById('pie');
    const pieChart = echarts.init(pieChartDom);
    var pieOption;

    barOption = {
      title: {
        text: '各季度未巡检配电箱数量情况'
      },
      xAxis: {
        type: 'category',
        data: ['第一季度','第二季度','第三季度','第四季度']
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [],
          type: 'bar'
        },
        {
          data: [],
          type: 'line'
        }
      ]
    };

    pieOption = {
      title: {
        text: '角色占比',
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        top: '5%',
        left: 'center'
      },
      series: [
        {
          type: 'pie',
          radius: ['40%', '70%'],
          avoidLabelOverlap: false,
          itemStyle: {
            borderRadius: 10,
            borderColor: '#fff',
            borderWidth: 2
          },
          label: {
            show: false,
            position: 'center'
          },
          emphasis: {
            label: {
              show: true,
              fontSize: '60',
              fontWeight: 'bold'
            }
          },
          labelLine: {
            show: false
          },
          data: []
        }
      ]
    }

    this.request.get("/echarts/members").then(res=>{
      barOption.series[0].data=res.data
      barOption.series[1].data=res.data
      //数据准备完毕后再set
      barOption && barChart.setOption(barOption);

      pieOption.series[0].data=[
        {name: "管理员",value: 3},
        {name: "巡检员",value: 6},
        {name: "甲方人员",value: 2},
        {name: "普通用户",value: 10}
      ]
      pieOption && pieChart.setOption(pieOption);
    })

  }
}
</script>

<style scoped>

</style>
