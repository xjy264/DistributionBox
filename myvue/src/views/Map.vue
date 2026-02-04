<template>
  <div id="container">

  </div>
</template>

<script>
// 添加高德安全密钥
window._AMapSecurityConfig = {
  securityJsCode: "7b4b5208b5674502004a950812f7614a"
};
import AMapLoader from "@amap/amap-jsapi-loader";

export default {
  name: "Map",
  data(){
    return{
      map: null,
      lib: null,
      house: null,
      food: null,
      houseContent: [],
      libContent: [],
    }
  },
  created() {},
  mounted() {
    this.initAMap();
  },
  methods:{
    initAMap() {
      AMapLoader.load({
        key: "817e75604470d1ac03335b979bf791d1", //设置您的key
        version: "2.0",
        plugins: ["AMap.ToolBar", "AMap.Driving"],
        AMapUI: {
          version: "1.1",
          plugins: []
        },
        Local: {
          version: "2.0"
        }
      }).then(AMap => {
          this.map = new AMap.Map("container", {
          zoom: 17,
          center: [112.7212,37.7495],
          resizable: true
          });

            this.libContent.push("名称：太原理工大学图书馆");
            this.libContent.push("开放时间：9：00-22：00");
            this.libContent.push("地址：明向校区");

          // 创建 infoWindow 实例
            var libInfoWindow = new AMap.InfoWindow({
            isCustom: true,  //使用自定义窗体
            content: this.libContent.join("<br>"),
            offset: new AMap.Pixel(16, -40)
          });

            this.houseContent = [];
            this.houseContent.push("名称：明泽苑一号楼");
            this.houseContent.push("开放时间：全天");
            this.houseContent.push("地址：明向校区");

          // 创建 infoWindow 实例
            var infoWindow = new AMap.InfoWindow({
            isCustom: true,  //使用自定义窗体
            content: this.houseContent.join("<br>"),
            offset: new AMap.Pixel(16, -40)
          });

            this.lib = new AMap.Marker({
            position: new AMap.LngLat(112.7212,37.7495),   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
            title: '太原理工大学图书馆',
          })
            this.food = new AMap.Marker({
            position: new AMap.LngLat(112.522612,37.861402),   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
            title: '太原理工大学迎西校区'
          })
            this.house = new AMap.Marker({
            position: new AMap.LngLat(112.7256,37.74706),   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
            title: '太原理工大学宿舍'
          })

          var driving = new AMap.Driving({
            map: this.map
          });
          driving.search(
              new AMap.LngLat(112.7212,37.7495),
              new AMap.LngLat(112.522612,37.861402),

              function(status, result) {
                // result 即是对应的驾车导航信息，相关数据结构文档请参考  https://lbs.amap.com/api/javascript-api/reference/route-search#m_DrivingResult
                if (status === "complete") {
                  console.log("绘制驾车路线完成");
                } else {
                  console.log("获取驾车数据失败：" + result);
                }
              }
          )
          this.map.add(this.lib);
          this.map.add(this.food);
          this.map.add(this.house);

            this.lib.on('click', function () {
              libInfoWindow.open(this.map, [112.7212,37.7495]);
            });
            this.house.on('click', function () {
              infoWindow.open(this.map, [112.7256,37.74706]);
            });
          }
        ).catch(e => {
          console.log(e);
        });
    }
  }
}
</script>

<style scoped>
#container{
  padding:0px;
  margin: 0px;
  width: 100%;
  height: 800px;
}
</style>
