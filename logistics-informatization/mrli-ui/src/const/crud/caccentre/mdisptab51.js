export const tableOption5 = {
  "border": true, 
  "stripe": true,
  "menuAlign": "center",
  "align": "center",
  "searchMenuSpan": 6,
  "menu":false,
  "addBtn":false,
  "refreshBtn":false,
  "columnBtn":false,
  "searchBtn":false,
  "page":false,
  "height":140,
  "column": [{
    "label": '外贸进口',     
      "children": [ {
        "type": "input",
        "label": "金矿",
        "prop": "oiKind03"
      },	  {
        "type": "input",
        "label": "钢铁",
        "prop": "oiKind04"
      },	  {
        "type": "input",
        "label": "水泥",
        "prop": "oiKind06"
      },	  {
        "type": "input",
        "label": "木材",
        "prop": "oiKind07"
      },	  {
        "type": "input",
        "label": "化肥",
        "prop": "oiKind09"
      },	  {
        "type": "input",
        "label": "粮食",
        "prop": "oiKind11"
      },	  {
        "type": "input",
        "label": "纸",
        "prop": "oiKind151"
      },	  {
        "type": "input",
        "label": "糖",
        "prop": "oiKind1530"
      },	  {
        "type": "input",
        "label": "其它",
        "prop": "oiKind17"
      }  ]
    },{
      "label": '外贸出口',     
        "children": [  {
          "type": "input",
          "label": "煤炭",
          "prop": "ooKind01"
        },	  {
          "type": "input",
          "label": "焦炭",
          "prop": "ooKind0110"
        },	  {
          "type": "input",
          "label": "水泥",
          "prop": "ooKind06"
        },	  {
          "type": "input",
          "label": "非矿",
          "prop": "ooKind08"
        },	  {
          "type": "input",
          "label": "玉米",
          "prop": "ooKind10"
        },	  {
          "type": "input",
          "label": "豆类",
          "prop": "ooKind1120"
        },	  {
          "type": "input",
          "label": "盐",
          "prop": "ooKind1130"
        },	  {
          "type": "input",
          "label": "其它",
          "prop": "ooKind17"
        } ]
      },{"label": '疏运物资',     
      "children": [ {
        "type": "input",
        "label": "铁路",
        "prop": "waimTrainNum"
      },	  {
        "type": "input",
        "label": "水路",
        "prop": "waimWaterNum"
      } ,  {
        "type": "input",
        "label": "市提",
        "prop": "waimCarNum"
      },	  {
        "type": "input",
        "label": "公路",
        "prop": "waimRoadNum"
      } 	  ]
    }   ]
}
