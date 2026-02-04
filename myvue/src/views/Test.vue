<template>
  <div class="home_div">
    <div class="map_title">
      <h3>JSAPI Vue2地图组件示例</h3>
    </div>
    <div id="container"></div>
  </div>
</template>
<script>
// 添加高德安全密钥
window._AMapSecurityConfig = {
  securityJsCode: "7b4b5208b5674502004a950812f7614a"
};
import AMapLoader from "@amap/amap-jsapi-loader";

export default {
  name: "Test",
  data() {
    return {
      map: null
    };
  },
  created() {},
  mounted() {
    this.initAMap();
  },
  methods: {
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
      })
          .then(AMap => {
            this.map = new AMap.Map("container", {
              viewMode: "3D",
              zoom: 12,
              zooms: [2, 22],
              center: [116.391935, 39.895534]
            });
            var driving = new AMap.Driving({
              map: this.map
            });
            // 根据起终点经纬度规划驾车导航路线
            driving.search(
                new AMap.LngLat(116.379028, 39.865042),
                new AMap.LngLat(116.427281, 39.903719),

                function(status, result) {
                  // result 即是对应的驾车导航信息，相关数据结构文档请参考  https://lbs.amap.com/api/javascript-api/reference/route-search#m_DrivingResult
                  if (status === "complete") {
                    console.log("绘制驾车路线完成");
                  } else {
                    console.log("获取驾车数据失败：" + result);
                  }
                }
            );
          })
          .catch(e => {
            console.log(e);
          });
    }
  }
};
</script>
<style scoped>
.home_div {
  padding: 0px;
  margin: 0px;
  width: 90%;
  height: 100%;
  position: relative;
}
#container {
  padding: 0px;
  margin: 0px;
  width: 100%;
  height: 100%;
  position: absolute;
}
.map_title {
  position: absolute;
  z-index: 1;
  width: 100%;
  height: 50px;
  background-color: rgba(27, 25, 27, 0.884);
}
h3 {
  position: absolute;
  left: 10px;
  z-index: 2;
  color: white;
}
</style>

