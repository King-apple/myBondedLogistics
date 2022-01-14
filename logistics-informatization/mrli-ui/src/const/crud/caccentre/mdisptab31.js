export const tableOption3 = {
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
  "column": [
	  {
      "label": '当日卸车情况',
       
        "children": [  {
          "type": "input",
          "label": "煤炭",
          
          "prop": "toKind_01"
        },	  {
          "type": "input",
          "label": "石油",
          
          "prop": "toKind_02"
        },	  {
          "type": "input",
          "label": "钢铁",
          
          "prop": "toKind_04"
        },	  {
          "type": "input",
          "label": "木材",
          
          "prop": "toKind_07"
        },	  {
          "type": "input",
          "label": "非矿",
          
          "prop": "toKind_08"
        },	  {
          "type": "input",
          "label": "粮食",
          
          "prop": "toKind_11"
        },	  {
          "type": "input",
          "label": "其它",
          
          "prop": "toKind_17"
        }	]
      },{
        "label": '当日待卸车情况',
         
          "children": [  {
            "type": "input",
            "label": "煤炭",            
            "prop": "unKind_01"
          },	  {
            "type": "input",
            "label": "石油",            
            "prop": "unKind_02"
          },	  {
            "type": "input",
            "label": "钢铁",            
            "prop": "unKind_04"
          },	  {
            "type": "input",
            "label": "木材",            
            "prop": "unKind_07"
          },	  {
            "type": "input",
            "label": "非矿",            
            "prop": "unKind_08"
          },	  {
            "type": "input",
            "label": "粮食",            
            "prop": "unKind_11"
          },	  {
            "type": "input",
            "label": "其它",            
            "prop": "unKind_17"
          },	  {
            "type": "input",
            "label": "无效",            
            "prop": "unKindNul"
          }]
        },{
          "label": '当日装车情况',
           
            "children": [{
              "type": "input",
              "label": "金矿",              
              "prop": "loKind_03"
            },	  {
              "type": "input",
              "label": "钢铁",              
              "prop": "loKind_04"
            },	  {
              "type": "input",
              "label": "木材",              
              "prop": "loKind_07"
            },	  {
              "type": "input",
              "label": "非矿",              
              "prop": "loKind_08"
            },	  {
              "type": "input",
              "label": "化肥",              
              "prop": "loKind_09"
            },	  {
              "type": "input",
              "label": "粮食",              
              "prop": "loKind_11"
            },	  {
              "type": "input",
              "label": "其它",              
              "prop": "loKind_17"
            }]
          }  ]
}
