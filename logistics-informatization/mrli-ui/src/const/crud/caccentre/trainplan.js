export const tableOption = {
  "title": "局计划信息",
  "border": true,
  "index": true,
  "indexLabel": "序号",
  "stripe": true,
  "editBtn": false,
  "addBtn": false,
  "delBtn": false,
  "menuAlign": "center",
  "align": "center",
  "searchMenuSpan": 6,
  "height": 300,
  "column": [
    {
      "type": "input",
      "label": "审核",
      "prop": "planStatCod"
    }, {
      "type": "input",
      "label": "集团审核",
      "prop": "planStat"
    }, {
      "type": "input",
      "label": "计划内/外",
      "prop": "planId",
      "width": 100,
      "slot": true,
    }, {
      "type": "input",
      "label": "作业公司代码",
      "prop": "unitCod",
      "hide": true
    }, {
      "type": "input",
      "label": "作业公司",
      "prop": "unitNam",
      "width": 200,
      "overHidden": true
    }, {
      "type": "input",
      "label": "建议股位",
      "prop": "traceNam"
    }, {
      "type": "input",
      "label": "货名代码",
      "prop": "cargoNamCod",
      "hide": true
    },
    {
      "type": "input",
      "label": "货名",
      "prop": "cargoNam"
    }, {
      "type": "input",
      "label": "包装代码",
      "prop": "pkgKindCod",
      "hide": true
    }, {
      "type": "input",
      "label": "包装",
      "prop": "cPkgKindNam"
    }, {
      "type": "input",
      "label": "规格",
      "prop": "format"
    }, {
      "type": "input",
      "label": "到发站代码",
      "prop": "fromStatCod",
      "hide": true
    }, {
      "type": "input",
      "label": "到发站",
      "prop": "fromStatNam"
    }, {
      "type": "input",
      "label": "代理货主代码",
      "prop": "consignCod",
      "hide": true
    }, {
      "type": "input",
      "label": "代理货主",
      "prop": "consignNam",
      "width": 200,
      "overHidden": true
    }, {
      "type": "input",
      "label": "港口货主代码",
      "prop": "shipperCod",
      "hide": true
    }, {
      "type": "input",
      "label": "港口货主",
      "prop": "shipperDoc",
      "width": 200,
      "overHidden": true
    }, {
      "type": "input",
      "label": "发货人代码",
      "prop": "setownerCod",
      "hide": true
    }, {
      "type": "input",
      "label": "发货人",
      "prop": "setownerNam",
      "width": 130,
      "overHidden": true
    }, {
      "type": "input",
      "label": "收货人代码",
      "prop": "getownerCod",
      "hide": true
    }, {
      "type": "input",
      "label": "收货人",
      "prop": "getownerNam",
      "width": 130,
      "overHidden": true
    }, {
      "type": "input",
      "label": "计划到港重量",
      "prop": "weightWgt",
      "width": 130,
      "overHidden": true
    }, {
      "type": "input",
      "label": "总车数",
      "prop": "trainNum"
    }, {
      "type": "input",
      "label": "联系电话",
      "prop": "phone"
    }, {
      "type": "input",
      "label": "备注",
      "prop": "markTxt",
      "width": 200,
      "overHidden": true
    }
    //  {
    //   "type": "input",
    //   "label": "货号",
    //   "prop": "cargoNo"
    // },	  {
    //   "type": "input",
    //   "label": "检算",
    //   "prop": "checkId"
    // },	  {
    //   "type": "input",
    //   "label": "内容",
    //   "prop": "checkTxt"
    // },	    	   {
    //   "type": "input",
    //   "label": "完标",
    //   "prop": "ifendId"
    // },	  {
    //   "type": "input",
    //   "label": "进出口",
    //   "prop": "inoutId"
    // },	  {
    //   "type": "input",
    //   "label": "操作员",
    //   "prop": "operNam"
    // },	   {
    //   "type": "input",
    //   "label": "计划日期",
    //   "prop": "planDte"
    // },	  {
    //   "type": "input",
    //   "label": "0.内  1.外",
    //   "prop": "planId"
    // },	  {
    //   "type": "input",
    //   "label": "计划号",
    //   "prop": "planNo"
    // },	  	  {
    //   "type": "input",
    //   "label": "记录时间",
    //   "prop": "recordTim"
    // },	  {
    //   "type": "input",
    //   "label": "序号",
    //   "prop": "seqNo"
    // },	   {
    //   "type": "input",
    //   "label": "船名",
    //   "prop": "shipNam"
    // },	  {
    //   "type": "input",
    //   "label": "船号",
    //   "prop": "shipNo"
    // },	  {
    //   "type": "input",
    //   "label": "卸船数量",
    //   "prop": "shipWgt"
    // },	  {
    //   "type": "input",
    //   "label": "车站呼号",
    //   "prop": "teleNo"
    // },	  {
    //   "type": "input",
    //   "label": "到港代码",
    //   "prop": "toPortCod"
    // },	  {
    //   "type": "input",
    //   "label": "到港",
    //   "prop": "toPortNam"
    // },	   {
    //   "type": "input",
    //   "label": "内外贸",
    //   "prop": "tradeId"
    // },	  {
    //   "type": "input",
    //   "label": "日均车数",
    //   "prop": "trainDayNum"
    // },	  {
    //   "type": "input",
    //   "label": "集疏港别",
    //   "prop": "unloadId"
    // },	  {
    //   "type": "input",
    //   "label": "修改人",
    //   "prop": "updateNam"
    // },	  {
    //   "type": "input",
    //   "label": "修改时间",
    //   "prop": "updateTim"
    // },	    {
    //   "type": "input",
    //   "label": "库名",
    //   "prop": "yardNam"
    // },	  {
    //   "type": "input",
    //   "label": "库代码",
    //   "prop": "yardNo"
    // } 
  ]
}
export const tableOptionSub = {
  "title": "公司计划信息",
  "border": true,
  "index": true,
  "indexLabel": "序号",
  "stripe": true,
  "addBtn": false,
  "menuAlign": "center",
  "align": "center",
  "searchMenuSpan": 6,
  "height": 200,
  "column": [
    {
      "type": "input",
      "label": "股位代码",
      "prop": "trackCod"
    }, {
      "type": "input",
      "label": "股位",
      "prop": "trackNam"
    }, {
      "type": "input",
      "label": "管货库区代码",
      "prop": "subunitCod"
    }, {
      "type": "input",
      "label": "管货库区",
      "prop": "subunitNam"
    }, {
      "type": "input",
      "label": "库场名称",
      "prop": "yardNam"
    }, {
      "type": "input",
      "label": "库场代码",
      "prop": "yardNo"
    }, {
      "type": "input",
      "label": "车数",
      "prop": "trainNum"
    }, {
      "type": "input",
      "label": "重量",
      "prop": "weightWgt"
    }, {
      "type": "input",
      "label": "备注",
      "prop": "markTxt"
    }
    // ,	  {
    //   "type": "input",
    //   "label": "操作员",
    //   "prop": "operNam"
    // },	  {
    //   "type": "input",
    //   "label": "计划日期",
    //   "prop": "planDte"
    // },	  {
    //   "type": "input",
    //   "label": "计划号",
    //   "prop": "planNo"
    // },	  {
    //   "type": "input",
    //   "label": "记录时间",
    //   "prop": "recordTim"
    // },	  {
    //   "type": "input",
    //   "label": "序号",
    //   "prop": "seqNo"
    // },	   {
    //   "type": "input",
    //   "label": "作业公司代码",
    //   "prop": "unitCod"
    // },	  {
    //   "type": "input",
    //   "label": "作业公司",
    //   "prop": "unitNam"
    // },	  {
    //   "type": "input",
    //   "label": "集疏港别",
    //   "prop": "unloadId"
    // },	  {
    //   "type": "input",
    //   "label": "修改人",
    //   "prop": "updateNam"
    // },	  {
    //   "type": "input",
    //   "label": "修改时间",
    //   "prop": "updateTim"
    // } 
  ]
}