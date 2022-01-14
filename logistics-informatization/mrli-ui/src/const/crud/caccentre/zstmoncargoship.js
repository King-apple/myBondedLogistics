export const tableOption = {
  "border": true,
  "index": true,
  "indexLabel": "序号",
  "stripe": true,
  "menuAlign": "center",
  "align": "center",
  "searchMenuSpan": 6,
  "menu":false,
  "addBtn":false,
  "refreshBtn":false,
  "columnBtn":false,
  "searchBtn":false,  
  "height":500,
  "column": [
	  {
      "type": "input",
      "label": "货类代码",
      "prop": "itemCod",
      "fixed":true
    },	  {
      "type": "input",
      "label": "货类名称",
      "prop": "itemNam",
      "fixed":true,
      "width":150
    }, {
      "label": '营口港',
      "children": [{
        "label": '小计',
        "children": [ {
          "type": "input",
          "label": "当月",
          "prop": "ykzjWgt"
        },{
          "type": "input",
          "label": "同比",
          "prop": "ykzjTb"
        },	  	  {
          "type": "input",
          "label": "增幅",
          "prop": "ykzjZf"
        }	]
      },{
        "label": '营口',
        "children": [ {
          "type": "input",
          "label": "当月",
          "prop": "ykWgt"
        },{
          "type": "input",
          "label": "同比",
          "prop": "ykTb"
        },	  	  {
          "type": "input",
          "label": "增幅",
          "prop": "ykZf"
        }	]
      },{
        "label": '盘锦',
        "children": [ {
          "type": "input",
          "label": "当月",
          "prop": "pjWgt"
        },{
          "type": "input",
          "label": "同比",
          "prop": "pjTb"
        },	  	  {
          "type": "input",
          "label": "增幅",
          "prop": "pjZf"
        }	]
      },{
        "label": '绥中',
        "children": [ {
          "type": "input",
          "label": "当月",
          "prop": "szWgt"
        },{
          "type": "input",
          "label": "同比",
          "prop": "szTb"
        },	  	  {
          "type": "input",
          "label": "增幅",
          "prop": "szZf"
        }	]
      } ]
    },	{
      "label": '集团',
      "children": [ {
        "type": "input",
        "label": "小计",
        "prop": "jtWgt"
      },	  {
        "type": "input",
        "label": "同比",
        "prop": "jtTb"
      },	  {
        "type": "input",
        "label": "增幅",
        "prop": "jtZf"
      }]
    }, {
      "label": '大连',
      "children": [ {
        "type": "input",
        "label": "小计",
        "prop": "dlWgt"
      },	  {
        "type": "input",
        "label": "同比",
        "prop": "dlTb"
      },	  {
        "type": "input",
        "label": "增幅",
        "prop": "dlZf"
      }]
    },      {
      "type": "input",
      "label": "$column.comments",
      "prop": "operNam",
      "hide":true
    },	   	  {
      "type": "input",
      "label": "$column.comments",
      "prop": "recordDte",
      "hide":true
    },	  {
      "type": "input",
      "label": "$column.comments",
      "prop": "recordTim",
      "hide":true
    },  {
      "type": "input",
      "label": "$column.comments",
      "prop": "unitCod",
      "hide":true
    },	  {
      "type": "input",
      "label": "$column.comments",
      "prop": "updateNam",
      "hide":true
    },	  {
      "type": "input",
      "label": "$column.comments",
      "prop": "updateTim",
      "hide":true
    } 	  ]
}
