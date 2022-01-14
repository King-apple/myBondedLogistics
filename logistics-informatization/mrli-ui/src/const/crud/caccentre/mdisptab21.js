export const tableOption2 = {
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
    "label": '外轮',     
      "children": [   {
        "type": "input",
        "label": "在港",
        "prop": "forInNum"
      },  {
        "type": "input",
        "label": "作业",
        "prop": "forWorkNum"
      }, {
        "type": "input",
        "label": "到港",
        "prop": "forToNum"
      },	  	  {
        "type": "input",
        "label": "离港",
        "prop": "forLevNum"
      },	{
        "type": "input",
        "label": "超月船",
        "prop": "forMonNum"
      } 	 ]
    },{
      "label": '外贸国轮',     
        "children": [   {
          "type": "input",
          "label": "在港",
          "prop": "natInNum"
        },  {
          "type": "input",
          "label": "作业",
          "prop": "natWorkNum"
        }, {
          "type": "input",
          "label": "到港",
          "prop": "natToNum"
        },	  	  {
          "type": "input",
          "label": "离港",
          "prop": "natLevNum"
        },	{
          "type": "input",
          "label": "超月船",
          "prop": "natMonNum"
        } 	 ]
      },{
        "label": '海轮',     
          "children": [   {
            "type": "input",
            "label": "在港",
            "prop": "seaInNum"
          },  {
            "type": "input",
            "label": "作业",
            "prop": "seaWorkNum"
          }, {
            "type": "input",
            "label": "到港",
            "prop": "seaToNum"
          },	  	  {
            "type": "input",
            "label": "离港",
            "prop": "seaLevNum"
          },	{
            "type": "input",
            "label": "超月船",
            "prop": "seaMonNum"
          } 	 ]
        },{
          "label": '其它',     
            "children": [   {
              "type": "input",
              "label": "在港",
              "prop": "othInNum"
            },  {
              "type": "input",
              "label": "作业",
              "prop": "othWorkNum"
            }  ]
          },{
            "label": '外贸船待作业原因',     
              "children": [   {
                "type": "input",
                "label": "港方",
                "prop": "forPortNum"
              },	   {
                "type": "input",
                "label": "航方",
                "prop": "forShipNum"
              },	  {
                "type": "input",
                "label": "货方",
                "prop": "forCargoNum"
              },	   {
                "type": "input",
                "label": "自然",
                "prop": "forNatureNum"
              },	  {
                "type": "input",
                "label": "其它",
                "prop": "forOtherNum"
              }	 ]
            },{
              "label": '船舶进出港艘数',     
                "children": [ {
                  "type": "input",
                  "label": "进港",
                  "prop": "inPort"
                },  {
                  "type": "input",
                  "label": "外贸船舶",
                  "prop": "oinPort"
                },	 {
                  "type": "input",
                  "label": "在港",
                  "prop": "inShip"
                }, {
                  "type": "input",
                  "label": "离港",
                  "prop": "outShip"
                }]
              }  ]
}
