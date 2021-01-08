var map;
require([
  "esri/Map",
  "esri/views/MapView",
  "esri/symbols/WebStyleSymbol",
], function (Map, MapView, WebStyleSymbol) {
  map = new Map({
    basemap: "topo-vector",
  });
  var point_template_area = {
    title: "{Name}",
    content: "Khu vực này ở {Location}.",
  };
  const webStyleSymbol = new WebStyleSymbol({
    name: "hospital",
    styleName: "Esri2DPointSymbolsStyle",
  });

  // ajax lấy dữ liệu api
  let arrPoint = [];
  axios
      .get("http://localhost:8080/rest/getList?startX=10.878071642199501&endX=106.80000283677727&startY=10.877147892530843&endY=106.80054812996096")
      .then((res) => {
        // let point = [];
        for (let i = 0; i < res.data.length;i++){
          // console.log(res.data[i].x);
          // console.log(res.data.y);
          let point = [];
          point.push(res.data[i].y);
          point.push(res.data[i].x);
          arrPoint.push(point);
        }
        return arrPoint;
      })
      .then((arrPoint) => {
        console.log(arrPoint);
        var jsonX = [
          {
            geometry: {
              type: "point",
              longitude: 106.80383222383759,
              latitude: 10.880625043102254,
            },
            symbol: webStyleSymbol,
            attributes: {
              name: "Bệnh viện",
              location: "Bệnh viện Y Dược",
            },
            popupTemplate: {
              title: "{name}",
              content: "{name} này ở <b>{location}</b>.",
            },
          },
        ];
        var jsondata = {
          lines: [
            {
              type: "polyline",
              // paths: [
              //   [106.79516086788163, 10.877265062700753],
              //   [106.79177726055457, 10.872512990539324],
              //   [106.79146826955217, 10.868568194942823],
              //   [106.79450667540044, 10.866393476810664],
              //   [106.80161345535876, 10.867152101124956],
              //   [106.80511534902031, 10.869478534939649],
              //   [106.80810225593163, 10.873372746726085],
              //   [106.8046518638264, 10.875952000416788],
              //   [106.80032599503159, 10.877722064100807],
              //   [106.79533065053371, 10.877823211308575],
              //   [106.79516086788163, 10.877265062700753],
              // ],

              // gán danh sách điểm lấy từ api:
              paths: arrPoint,
              symbol: {
                type: "simple-line",
                color: [226, 119, 40], // orange
                width: 2,
              },
              Name: "Làng đại học",
              Location: "Quận Thủ Đức",
              popupTemplate: point_template_area,
            },
          ],
        };
        require([
          "esri/Map",
          "esri/views/MapView",
          "esri/Graphic",
          "esri/layers/GraphicsLayer",
        ], function (Map, MapView, Graphic, GraphicsLayer) {
          var map = new Map({
            basemap: "topo-vector",
          });
          map.on("load", function () {
            map.graphics.enableMouseEvents();
          });

          var view = new MapView({
            container: "viewDiv",
            map: map,
            center: [106.8033387, 10.8739831],
            zoom: 15,
            highlightOptions: {
              color: "blue",
            },
          });

          view.on("click", function (event) {
            let pointStartId = document.getElementById("point-start");
            pointStartId.value = `${event.mapPoint.longitude},${event.mapPoint.latitude}`;
          });

          var createGraphic = function (data) {
            return new Graphic({
              geometry: data,
              symbol: data.symbol,
              attributes: data,
              popupTemplate: data.popupTemplate,
            });
          };
          var graphicsLayer = new GraphicsLayer();
          jsondata.lines.forEach(function (data) {
            graphicsLayer.add(createGraphic(data));
          });

          jsonX.forEach(function (data) {
            graphicsLayer.add(data);
          });
          map.add(graphicsLayer);

          //event click go
          document.getElementById("go").addEventListener("click", () => {});
        });
      })
      .catch((err) => {
        alert("Loi api");
        console.log(err);
      });

});
