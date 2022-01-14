export const tableOption = {
  "menuBtn": false,
  "submitBtn": false,
  "emptyBtn": false,
  "addBtn": false,
  "delBtn": false,
  "menu": true,
  "menuWidth": 60,
  "searchBtn":false,
  "refreshBtn":false,
  "columnBtn":false,
  "border": true,
  "index": false, 
  "stripe": true,
  "height":450,
  "menuAlign": "left",
  "align": "center",
  "searchMenuSpan": 6,
   
  "column": [
	  {
      "type": "input",
      "label": "代码",
      "prop": "unitCod", 
      "width":"50px" , 
      "fixed":true
    },{
      "type": "input",
      "label": "作业公司",
      "prop": "unitNam",
      "width":"180px" , 
      "overHidden":true,
      "fixed":true
    },	  {
      "type": "input",
      "label": "代码",
      "prop": "trackCod",
      "width":"50px" , 
      "fixed":true
    },	  {
      "type": "input",
      "label": "股道",
      "prop": "trackNam",
      "width":"80px" , 
      "fixed":true,
      "overHidden":true,
    },	  {
      "type": "input",
      "label": "装卸",
      "prop": "unloadId",
      "slot": true,
      "width":"50px" , 
      "fixed":true,
    },	  {
      "type": "input",
      "label": "货类代码",
      "prop": "cargoKindCod",
      "width":"80px" , 
      "fixed":true,
    },	  {
      "type": "input",
      "label": "货物名称",
      "prop": "cargoNam",
      "width":"100px" , 
      "fixed":true,
      "overHidden":true,
    },	  {
      "type": "input",
      "label": "计划数",
      "prop": "oldTrainNum",
      "width":"60px" ,
      "fixed":true,
    },	  {
      "type": "input",
      "label": "车数",
      "prop": "trainNum",
      "width":"60px" ,
      "fixed":true,
    },	  {
      "type": "input",
      "label": "吨数",
      "prop": "weightTon",
      "width":"60px" ,
      "fixed":true,
    },	  {
      "type": "input",
      "label": "抵港时间",
      "width":"150px" ,
      "prop": "arrivTim"
    },	  {
      "type": "input",
      "label": "开工前检车时间",
      "width":"150px" ,
      "prop": "warmBegTim"
    },	  {
      "type": "input",
      "label": "开始时间",
      "width":"150px" ,
      "prop": "beginTim"
    },	  {
      "type": "input",
      "label": "结束时间",
      "width":"150px" ,
      "prop": "endTim"
    },	  {
      "type": "input",
      "label": "完工后检车时间",
      "width":"150px" ,
      "prop": "warmEndTim"
    },	  {
      "type": "input",
      "label": "离港时间",
      "width":"150px" ,
      "prop": "levPortTim"
    },	  {
      "type": "input",
      "label": "空重",
      "prop": "natureId",
      "slot": true,
      "width":"50px" , 
    },	  {
      "type": "input",
      "label": "货主代码",
      "prop": "consignCod",
      "hide": "true",
    },	  {
      "type": "input",
      "label": "货主名称",
      "prop": "consignNam",
      "width":"120px" ,  
      "overHidden":true,
    },	  {
      "type": "input",
      "label": "到发站代码",
      "prop": "fromStatCod",
      "hide": "true",
    },	  {
      "type": "input",
      "label": "到发站",
      "prop": "fromStatNam",
      "width":"80px" ,  
      "overHidden":true,
    },	  {
      "type": "input",
      "label": "监控录入日期",
      "width":"150px" ,  
      "prop": "recordTim1"
    },	  {
      "type": "input",
      "label": "监控修改日期",
      "width":"150px" ,  
      "prop": "updateTim1"
    },	  
    
    {
       "type": "input",
      "label": "操作员",
      "hide": "true",
      "prop": "operNam"
    },	  {
      "type": "input",
      "label": "记录时间",
      "hide": "true",
      "prop": "recordTim"
    },	  {
      "type": "input",
      "label": "$column.comments",
      "hide": "true",
      "prop": "shipNam"
    },	  {
      "type": "input",
      "label": "$column.comments",
      "hide": "true",
      "prop": "shipNo"
    },	  {
      "type": "input",
      "label": "计划月份",
      "hide": "true",
      "prop": "totMon"
    },	  {
      "type": "input",
      "label": "序号",
      "hide": "true",
      "prop": "trainStatSeq"
    },	  {
      "type": "input",
      "label": "最终修改人",
      "hide": "true",
      "prop": "updateNam"
    },	  {
      "type": "input",
      "label": "最终修改时间",
      "hide": "true",
      "prop": "updateTim"
    },	  {
      "type": "input",
      "label": "作业工时",
      "hide": "true",
      "prop": "workNum"
    }  ]
}
