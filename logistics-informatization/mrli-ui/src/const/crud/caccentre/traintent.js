import moment from 'moment'
 
export const tableOption = {
  "border": true,
  "index": true,
  "indexLabel": "序号",
  "stripe": true,
  "addBtn":false, 
  "refreshBtn":false,
  "columnBtn":false,
  "editBtn":false,
  "menuAlign": "center",
  "menuWidth":150,
  "align": "center",
  "searchMenuSpan": 6,  
  "height":400,
  "column": [
	     {
      "type": "input",
      "label": "车皮号",
      "prop": "trainCod",
      "fixed":true
    },{
      "type": "input",
      "label": "货名",
      "prop": "cargoNam",
      "fixed":true
    },	 {
      "type": "input",
      "label": "车站电报呼号",
      "prop": "teleNo",
      "hide":true
    }, {
      "type": "input",
      "label": "车站省略名",
      "prop": "sanothNam",
      "hide":true
    },	 {
      "type": "input",
      "label": "站名",
      "prop": "stationNam",
      "fixed":true
    },	  {
      "type": "input",
      "label": "计划号",
      "prop": "planNo",
      "fixed":true
    },	   {
      "type": "input",
      "label": "原发件数",
      "prop": "oldPiecesNum",
      "fixed":true
    },	  {
      "type": "input",
      "label": "原发吨数",
      "prop": "oldWeightWgt",
      "fixed":true
    },	 {
      "type": "input",
      "label": "包装代码",
      "prop": "pkgKindCod",
      "hide":true
    }, {
      "type": "input",
      "label": "包装",
      "prop": "cPkgKindNam",
      "fixed":true
    }, 	 {
      "type": "input",
      "label": "规格型号",
      "prop": "format",
      "fixed":true
    },	 {
      "type": "input",
      "label": "收发货人代码",
      "prop": "ownerCod",
      "hide":true
    },	  {
      "type": "input",
      "label": "收发货人",
      "prop": "ownerNam",
      "width":200,
      "overHidden":true
    },	  {
      "type": "input",
      "label": "港口货主代码",
      "prop": "shipperCod",
      "hide":true
    },	  {
      "type": "input",
      "label": "港口货主",
      "prop": "shipperDoc",
      "width":200,
      "overHidden":true
    },	 {
      "type": "input",
      "label": "代理货主代码",
      "prop": "consignCod",
      "hide":true 
    },	  {
      "type": "input",
      "label": "代理货主",
      "prop": "consignNam",
      "width":200,
      "overHidden":true
    },  {
      "type": "input",
      "label": "箱号1",
      "prop": "containerNo1"
    },	  {
      "type": "input",
      "label": "箱号2",
      "prop": "containerNo2"
    },	{
      "type": "input",
      "label": "备注",
      "prop": "remarkTxt",
      "width":200
    }  ]
}
export const tableOptionSub = {
  "border": true,
  "index": true,
  "indexLabel": "序号",
  "stripe": true,
  "menuAlign": "center",
  "align": "center",
  "searchMenuSpan": 6,
  "column": [
	  {
      "type": "input",
      "label": "备注",
      "prop": "markTxt"
    },	  {
      "type": "input",
      "label": "操作员",
      "prop": "operNam"
    },	  {
      "type": "input",
      "label": "计划日期",
      "prop": "planDte"
    },	  {
      "type": "input",
      "label": "计划号",
      "prop": "planNo"
    },	  {
      "type": "input",
      "label": "记录时间",
      "prop": "recordTim"
    },	  {
      "type": "input",
      "label": "序号",
      "prop": "seqNo"
    },	  {
      "type": "input",
      "label": "管货库区代码",
      "prop": "subunitCod"
    },	  {
      "type": "input",
      "label": "管货库区",
      "prop": "subunitNam"
    },	  {
      "type": "input",
      "label": "股位代码",
      "prop": "trackCod"
    },	  {
      "type": "input",
      "label": "股位",
      "prop": "trackNam"
    },	  {
      "type": "input",
      "label": "车数",
      "prop": "trainNum"
    },	  {
      "type": "input",
      "label": "作业公司代码",
      "prop": "unitCod"
    },	  {
      "type": "input",
      "label": "作业公司",
      "prop": "unitNam"
    },	  {
      "type": "input",
      "label": "集疏港别",
      "prop": "unloadId"
    },	  {
      "type": "input",
      "label": "修改人",
      "prop": "updateNam"
    },	  {
      "type": "input",
      "label": "修改时间",
      "prop": "updateTim"
    },	  {
      "type": "input",
      "label": "重量",
      "prop": "weightWgt"
    },	  {
      "type": "input",
      "label": "库场名称",
      "prop": "yardNam"
    },	  {
      "type": "input",
      "label": "库场代码",
      "prop": "yardNo"
    }  ]
}
