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
      "label": "",
      "prop": "ykport",
      "fixed":true
    },{
      "type": "input",
      "label": "",
      "prop": "ykportNam",
      "width":120,
      "fixed":true
    },{
      "label": '集装箱',
      "children": [ {
        "type": "input",
        "label": "累计月份",
        "prop": "jzxWgt"
      },{
        "type": "input",
        "label": "同比量",
        "prop": "tbJzxWgt"
      } 	]
    },{
      "label": '矿石',
      "children": [ {
        "type": "input",
        "label": "累计月份",
        "prop": "ksWgt"
      },{
        "type": "input",
        "label": "同比量",
        "prop": "tbKsWgt"
      } 	]
    },{
      "label": '钢材',
      "children": [ {
        "type": "input",
        "label": "累计月份",
        "prop": "gcWgt"
      },{
        "type": "input",
        "label": "同比量",
        "prop": "tbGcWgt"
      } 	]
    },{
      "label": '粮食',
      "children": [ {
        "type": "input",
        "label": "累计月份",
        "prop": "lsWgt"
      },{
        "type": "input",
        "label": "同比量",
        "prop": "tbLsWgt"
      } 	]
    },{
      "label": '油品',
      "children": [ {
        "type": "input",
        "label": "累计月份",
        "prop": "ypWgt"
      },{
        "type": "input",
        "label": "同比量",
        "prop": "tbYpWgt"
      } 	]
    },{
      "label": '煤炭',
      "children": [ {
        "type": "input",
        "label": "累计月份",
        "prop": "mtWgt"
      },{
        "type": "input",
        "label": "同比量",
        "prop": "tbMtWgt"
      } 	]
    },{
      "label": '其它',
      "children": [ {
        "type": "input",
        "label": "累计月份",
        "prop": "qtWgt"
      },{
        "type": "input",
        "label": "同比量",
        "prop": "tbQtWgt"
      } 	]
    },{
      "label": '合计',
      "children": [ {
        "type": "input",
        "label": "累计月份",
        "prop": "totWgt"
      },{
        "type": "input",
        "label": "同比量",
        "prop": "tbTotWgt"
      },{
        "type": "input",
        "label": "增幅",
        "prop": "amplificationWgt"
      } 	]
    }, 	    	   {
      "type": "input",
      "label": "$column.comments",
      "prop": "monTtlTotal",
      "hide":true
    },	   {
      "type": "input",
      "label": "$column.comments",
      "prop": "recordNam",
      "hide":true
    },	  {
      "type": "input",
      "label": "$column.comments",
      "prop": "recordTim",
      "hide":true
    },  {
      "type": "input",
      "label": "$column.comments",
      "prop": "updateNam",
      "hide":true
    },	  {
      "type": "input",
      "label": "$column.comments",
      "prop": "updateTim",
      "hide":true
    },	  {
      "type": "input",
      "label": "$column.comments",
      "prop": "workDte",
      "hide":true
    },	 	  {
      "type": "input",
      "label": "$column.comments",
      "prop": "ykportCod",
      "hide":true
    },	  {
      "type": "input",
      "label": "$column.comments",
      "prop": "ykportId",
      "hide":true
    }   ]
}
