<template>
  <div>
    <div ref="chartRef" style="width: 100%; height: 360px;"></div>
    <div ref="memberRef" style="width: 100%; height: 360px; margin-top: 24px;"></div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import * as echarts from 'echarts'
import http from '@/api/http'

const chartRef = ref<HTMLDivElement | null>(null)
const memberRef = ref<HTMLDivElement | null>(null)

onMounted(async () => {
  const res = await http.get('/echarts')
  const data = res.data.data
  if (chartRef.value) {
    const chart = echarts.init(chartRef.value)
    chart.setOption({
      tooltip: {},
      xAxis: { data: data.x },
      yAxis: {},
      series: [{ type: 'bar', data: data.y }]
    })
  }

  const membersRes = await http.get('/echarts/members')
  const members = membersRes.data.data
  if (memberRef.value) {
    const chart = echarts.init(memberRef.value)
    chart.setOption({
      tooltip: {},
      xAxis: { data: ['Q1', 'Q2', 'Q3', 'Q4'] },
      yAxis: {},
      series: [{ type: 'line', data: members }]
    })
  }
})
</script>
