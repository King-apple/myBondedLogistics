export const tableOption15 = {
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
    "label": '国际航线',     
      "children": [  {
        "type": "input",
        "label": "合计",
        "prop": "gjHj"
      }, {
        "type": "input",
        "label": "出口",
        "prop": "gjCk"
      },	 {
        "type": "input",
        "label": "重箱",
        "prop": "gjFull"
      },  {
        "type": "input",
        "label": "出口重箱",
        "prop": "gjCkfull"
      } 	 	 ]
    },{
      "label": '内支线(外贸)',     
        "children": [  {
          "type": "input",
          "label": "合计",
          "prop": "nzHj"
        }, {
          "type": "input",
          "label": "出口",
          "prop": "nzCk"
        },	 {
          "type": "input",
          "label": "重箱",
          "prop": "nzFull"
        },  {
          "type": "input",
          "label": "出口重箱",
          "prop": "nzCkfull"
        } 	 	 ]
      },{
        "label": '内贸',     
          "children": [  {
            "type": "input",
            "label": "合计",
            "prop": "nmHj"
          }, {
            "type": "input",
            "label": "出口",
            "prop": "nmCk"
          },	 {
            "type": "input",
            "label": "重箱",
            "prop": "nmFull"
          },  {
            "type": "input",
            "label": "出口重箱",
            "prop": "nmCkfull"
          } 	 	 ]
        },{
          "label": '货重(万吨)',     
            "children": [ {
              "type": "input",
              "label": "合计",
              "prop": "cargoHj"
            },	 {
              "type": "input",
              "label": "外贸重箱",
              "prop": "cargoWm"
            },	    {
              "type": "input",
              "label": "出口重箱",
              "prop": "cargoCk"
            } 	 	 ]
          } ]
}
