export const tableOption = {
  "border": true,
  "index": true,
  "indexLabel": "序号",
  "stripe": true,
  "selection":true,  
  "selectionWidth":40,
  "menuAlign": "center",
  "align": "center",
  "searchMenuSpan": 6,
  "menu":false,
  "addBtn":false,
  "refreshBtn":false,
  "columnBtn":false,
  "searchBtn":false,
  "height":300,
  "column": [
	  {
      "type": "input",
      "label": "清单号",
      "prop": "shipBillNo",
      "fixed":true,
      "width":120 
    }, {
      "type": "input",
      "label": "版本",
      "prop": "verNo" ,
      "fixed":true
    },{
      "type": "input",
      "label": "船号",
      "prop": "shipNo",
      "fixed":true
    },{
      "type": "input",
      "label": "船名",
      "prop": "shipNam",
      "width":120 ,
      "fixed":true
    }, {
      "type": "input",
      "label": "作业委托人",
      "prop": "consignNam",
      "width":200,
      "overHidden":true 
    }, {
      "type": "input",
      "label": "进出口",
      "slot": true,
      "prop": "inoutId"
    },	  {
      "type": "input",
      "label": "内外贸",
      "slot": true,
      "prop": "tradeId"
    },	 {
      "type": "input",
      "label": "货物种类",
      "prop": "meaning"
    },   {
      "type": "input",
      "label": "中转港",
      "prop": "transferPort"
    },	 {
      "type": "input",
      "label": "到达港",
      "prop": "cPortNam"
    },	  {
      "type": "input",
      "label": "到港时间",
      "prop": "toPortTim",
      "width":160 
    },   {
      "type": "input",
      "label": "港建费状态",
      "prop": "zfzt",
      "width":100 
    } ,	 {
      "type": "input",
      "label": "港建费标志",
      "prop": "buildId",
      "slot": true,
      "width":100 
    }  ]
}
