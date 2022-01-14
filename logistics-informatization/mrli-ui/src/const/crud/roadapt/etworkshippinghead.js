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
  "height":200,
  "column": [
    {
      "type": "input",
      "label": "车牌号",
      "prop": "trkTrkno",
      "fixed":true 
    },	   {
      "type": "input",
      "label": "内外贸",
      "prop": "tradeId",
      "fixed":true ,
      "slot": true
    },	{
      "type": "input",
      "label": "装卸别",
      "prop": "unloadId",
      "fixed":true ,
      "slot": true,
    },{
      "type": "input",
      "label": "港口经营人",
      "prop": "unitNam",
      "width":200,
      "overHidden":true
    },	  {
      "type": "input",
      "label": "经营人电话",
      "prop": "unitPhone",
      "width":120,
      "overHidden":true
    }, {
      "type": "input",
      "label": "港口作业委托人",
      "prop": "shipperDoc",
      "width":200,
      "overHidden":true
    },		 {
      "type": "input",
      "label": "作业委托人地址",
      "prop": "shipperAdress",
      "width":200,
      "overHidden":true
    },	   {
      "type": "input",
      "label": "作业委托人电话",
      "prop": "shipperPhone",
      "width":120,
      "overHidden":true
    }, {
      "type": "input",
      "label": "托运人",
      "prop": "consignNam",
      "width":200,
      "overHidden":true
    },		 {
      "type": "input",
      "label": "托运人地址",
      "prop": "consignAdress",
      "width":200,
      "overHidden":true
    },	   {
      "type": "input",
      "label": "托运人电话",
      "prop": "consignPhone",
      "width":120,
      "overHidden":true
    },	  {
      "type": "input",
      "label": "实际收货人",
      "prop": "receiver",
      "width":200,
      "overHidden":true
    }, {
      "type": "input",
      "label": "保管单位",
      "prop": "subunitNam",
      "width":120,
      "overHidden":true
    },	  {
      "type": "input",
      "label": "保管单位电话",
      "prop": "subunitPhone",
      "width":120,
      "overHidden":true
    } 	 ,	   {
      "type": "input",
      "label": "运输单位",
      "prop": "transportNam",
      "width":200,
      "overHidden":true
    },	 {
      "type": "input",
      "label": "发货时间",
      "prop": "sendShippingTim",
      "width":160
    }   ,	 {
      "type": "input",
      "label": "装车时间",
      "prop": "loadingTim",
      "width":160
    },	   {
      "type": "input",
      "label": "预计到港时间",
      "prop": "eta",
      "width":160,
      "overHidden":true
    },	  {
      "type": "input",
      "label": "司机姓名",
      "prop": "driverNam"
    },	  {
      "type": "input",
      "label": "司机电话",
      "prop": "driverPhone",
      "width":120,
    },	  {
      "type": "input",
      "label": "会员代码",
      "prop": "hydm",
      "hide":true
    },	   {
      "type": "input",
      "label": "制单日期",
      "prop": "makeOrderTim",
      "hide":true
    },	  {
      "type": "input",
      "label": "主键",
      "prop": "pkid",
      "hide":true
    }  ]
}
