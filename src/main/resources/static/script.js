require([
  "esri/Map",
  "esri/views/MapView",
  "esri/symbols/WebStyleSymbol",
  "esri/Graphic",
  "esri/layers/GraphicsLayer",
], function (Map, MapView, WebStyleSymbol, Graphic, GraphicsLayer) {
  var point_template_area = {
    title: "{Name}",
    content: "Khu vực này ở {Location}.",
  };
  const webStyleSymbol = new WebStyleSymbol({
    name: "hospital",
    styleName: "Esri2DPointSymbolsStyle",
  });
  var jsonX = [
    {
      geometry: {
        type: "point",
        longitude: 106.80166553804607,
        latitude: 10.877492685516533,
      },
      symbol: webStyleSymbol,
      attributes: {
        name: "Bệnh viện",
        location: "Bệnh viện Thủ Đức",
      },
      popupTemplate: {
        title: "{name}",
        content: "{name} này ở <b>{location}</b>.",
      },
    },
  ];

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

  var graphicsLayer = new GraphicsLayer();
  var graphicsLayer2 = new GraphicsLayer();


  jsonX.forEach(function (data) {
    graphicsLayer2.add(data);
  });

  map.add(graphicsLayer2);

  const btnGo = document.getElementById("go");
  btnGo.addEventListener("click", () => {
    let start = [];
    const input = document.getElementById("point-start");
    let time = document.getElementById("timeId").value;
    
    let strInput = input.value.split(",");
    strInput.map((item) => {
      start.push(Number(item));
    });
    graphicsLayer.removeAll();
    axios
        .get(`http://localhost:8080/rest/roads/bad?startPointX=${start[1]}&startPointY=${start[0]}&endPointX=10.877492685516533&endPointY=106.80166553804607&startTime=${time}&endTime=${time}`
        )
        .then(async(res) => {
          console.log("bad",res.data);
          let x = [];

          for(let j=0;j<res.data.length;j++){
            await axios.get(`http://localhost:8080/rest/roads/arc?arcId=${res.data[j].arcId}`).then(res=>{
              let listPoint = res.data;
              console.log("vo dc bad");
              let arrPoint = [];
              for (let i = 0; i < listPoint.length; i++) {
                let point = [];
                point.push(res.data[i].y);
                point.push(res.data[i].x);
                arrPoint.push(point);
              }
              console.log("arrp:",arrPoint);
              x.push(arrPoint);
              console.log(x);
            }).catch(err=>{
              console.log("loi api get diem");
            })
          }

          // graphicsLayer.removeAll();

          x.map(item=>{


              var jsondata = {
                lines: [
                  {
                    type: "polyline",

                    // gán danh sách điểm lấy từ api:
                    paths: item,
                    symbol: {
                      type: "simple-line",
                      color: [26,27,29], // orange
                      width: 7,
                    },
                  },
                ],
              };
              var createGraphic = function (data) {
                return new Graphic({
                  geometry: data,
                  symbol: data.symbol,
                  attributes: data,
                  popupTemplate: data.popupTemplate,
                });
              };
              jsondata.lines.forEach(function (data) {
                graphicsLayer.add(createGraphic(data));
              });


          })

          map.add(graphicsLayer);
        })
        .catch((err) => {
          console.log(err);
          alert("Loi api get cung bad");
        });





    axios
      .get(`http://localhost:8080/rest/roads?startPointX=${start[1]}&startPointY=${start[0]}&endPointX=10.877492685516533&endPointY=106.80166553804607&startTime=${time}&endTime=${time}`
      )
      .then(async(res) => {
        // console.log(res.data);
        let x = [];

        for(let j=0;j<res.data.length;j++){
          await axios.get(`http://localhost:8080/rest/roads/arc?arcId=${res.data[j].arcId}`).then(res=>{
                let listPoint = res.data;
                let arrPoint = [];
                for (let i = 0; i < listPoint.length; i++) {
                  let point = [];
                  point.push(res.data[i].y);
                  point.push(res.data[i].x);
                  arrPoint.push(point);
                }
                console.log("arrp:",arrPoint);
                x.push(arrPoint);
                console.log(x);
              }).catch(err=>{
                console.log("loi api get diem");
              })
        }

        // graphicsLayer.removeAll();
        let k = 0;
        x.map(item=>{
          k++;
          if(k===1){
            var jsondata1 = {
              lines: [
                {
                  type: "polyline",

                  // gán danh sách điểm lấy từ api:
                  paths: item,
                  symbol: {
                    type: "simple-line",
                    color: [42, 97, 197], // orange
                    width: 4,
                  },
                },
              ],
            };
            var createGraphic1 = function (data) {
              return new Graphic({
                geometry: data,
                symbol: data.symbol,
                attributes: data,
                popupTemplate: data.popupTemplate,
              });
            };
            jsondata1.lines.forEach(function (data) {
              graphicsLayer.add(createGraphic1(data));
            });
          }
          else{
            var jsondata = {
              lines: [
                {
                  type: "polyline",

                  // gán danh sách điểm lấy từ api:
                  paths: item,
                  symbol: {
                    type: "simple-line",
                    color: [33,197,46], // orange
                    width: 2,
                  },
                },
              ],
            };
            var createGraphic = function (data) {
              return new Graphic({
                geometry: data,
                symbol: data.symbol,
                attributes: data,
                popupTemplate: data.popupTemplate,
              });
            };
            jsondata.lines.forEach(function (data) {
              graphicsLayer.add(createGraphic(data));
            });
          }

      })

        map.add(graphicsLayer);
      })
      .catch((err) => {
        console.log(err);
        alert("Loi api get cung");
      });
  });

});