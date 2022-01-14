var beginTime ;
var endTime ;
var date = new Date();
date.setHours(8);//小时设置为9，下略
date.setMinutes(0);
date.setSeconds(0);
date.setMilliseconds(0);
var datenow = new Date();
if (datenow.getTime() > date.getTime()) {
  var year = date.getFullYear(); 
  var month =(date.getMonth() + 1).toString(); 
  var day = (date.getDate()).toString();  
  var hours=(date.getHours()).toString(); 
  var minutes=(date.getMinutes()).toString(); 
  var seconds=(date.getSeconds()).toString(); 
  if (month.length == 1) { 
      month = "0" + month; 
  } 
  if (day.length == 1) { 
      day = "0" + day; 
  }
  beginTime = year + "-" + month + "-" + day+" "+hours+":"+minutes+":"+seconds;
}
else {
    date.setDate(date.getDate() - 1);
    var year = date.getFullYear(); 
    var month =(date.getMonth() + 1).toString(); 
    var day = (date.getDate()).toString();  
    var hours=(date.getHours()).toString(); 
    var minutes=(date.getMinutes()).toString(); 
    var seconds=(date.getSeconds()).toString(); 
    if (month.length == 1) { 
        month = "0" + month; 
    } 
    if (day.length == 1) { 
        day = "0" + day; 
    }
    if (hours.length == 1) { 
      hours = "0" + hours; 
  } 
  if (minutes.length == 1) { 
    minutes = "0" + minutes; 
  }
  if (seconds.length == 1) { 
    seconds = "0" + seconds; 
  }
    beginTime = year + "-" + month + "-" + day+" "+hours+":"+minutes+":"+seconds;
}
var year = datenow.getFullYear(); 
    var month =(datenow.getMonth() + 1).toString(); 
    var day = (datenow.getDate()).toString();  
    var hours=(datenow.getHours()).toString(); 
    var minutes=(datenow.getMinutes()).toString(); 
    var seconds=(datenow.getSeconds()).toString(); 
    if (month.length == 1) { 
        month = "0" + month; 
    } 
    if (day.length == 1) { 
        day = "0" + day; 
    }
    if (hours.length == 1) { 
      hours = "0" + hours; 
  } 
  if (minutes.length == 1) { 
    minutes = "0" + minutes; 
  }
  if (seconds.length == 1) { 
    seconds = "0" + seconds; 
  }
    endTime = year + "-" + month + "-" + day+" "+hours+":"+minutes+":"+seconds;

 
export const tableOption = {
  "border": true,  
  "stripe": true,
  "menuAlign": "center",
  "align": "center",
  "size":"mini",
  "addBtn":false, 
  "selection":true,
  "searchMenuSpan": 6, 
  "selectionWidth":40,
  "menuWidth":160,
  "menuPosition":"center",
  "menu":false,
  "addBtn":false,
  "searchBtn":false,
  "refreshBtn":false,
  "columnBtn":false,
  "column": [
    {
      "type": "datetime",
      "label": "时间",
      "prop": "dateList",
      "format": "yyyy-MM-dd HH:mm:ss",
      "valueFormat": "yyyy-MM-dd HH:mm:ss",      
      "hide": true,      
      "search": true,
      "searchSpan": 12,
      searchValue:[beginTime,endTime],  
      searchRange:true
  },
	 	  {
      "type": "input",
      "label": "货主",
      "prop": "consignNam",
      "width":"180px" ,
      "search": true,
      "fixed":true
     
    }, {
      "type": "input",
      "label": "货名",
      "prop": "cargoName",
      "width":"100px" ,
      "search": true,
      "fixed":true
    },   {
      "type": "input",
      "label": "车号",
      "prop": "trkTrkno",
      "width":"100px" ,
      "search": true,
      "fixed":true
    },{
      "type": "input",
      "label": "件数",
      "prop": "num",
      "width":"60px",
      "fixed":true
    }, {
      "type": "input",
      "label": "重量",
      "prop": "weight",
      "width":"60px",
      "fixed":true
    }, {
      "type": "input",
      "label": "司机",
      "prop": "driverNam",
      "width":"100px" 
    }, {
      "type": "input",
      "label": "电话",
      "prop": "phone",
      "width":"100px"
    },  {
      "type": "select",
      "label": "保管站",
      "prop": "subunitNam",
      "width":"100px" ,
      "slot": true,
      "multiple": true,
      "search": true,
      "dicData": [{
          "label": '第一保管站',
          "value": '第一保管站'
      }, {
          "label": '第二保管站',
          "value": '第二保管站'
      } ]
    },  {
      "type": "select",
      "label": "场地名称",
      "prop": "yardNam",
      "width":"100px" 
    },  {
      "type": "select",
      "label": "场地名称",
      "prop": "yardNo",
      "width":"100px",
      "hide":true,
      "search":true , 
      "props": {
        label: "codeName",
        value: "codeId"
      },
      "remote": true,
      "dicUrl": `/basecode/basecod/yard/{{key}}`, 
      typeformat(item, label, value) {
        return `${item["codeName"]}`;
      } 
    }, {
      "type": "select",
      "label": "作业工艺",
      "prop": "techNam",
      "width":"100px" 
        
    },  {
      "type": "select",
      "label": "作业工艺",
      "prop": "techNo",
      "width":"100px",
      "search":true , 
      "hide":true,
      props: {
        label: "codeName",
        value: "codeId"
      },
      remote: true,
       dicUrl: `/basecode/basecod/process/{{key}}`,
      typeformat(item, label, value) {
        return `${item["codeName"]}`;
      } 
    },{
      "type": "input",
      "label": "场地排号",
      "prop": "yardSortNo",
      "width":"100px"
    }, {
      "type": "input",
      "label": "过号",
      "prop": "remark1",
      "width":"60px"
    },	 {
      "type": "input",
      "label": "排号",
      "prop": "sortNo",
      "width":"150px",
      "hide":true
    }, {
      "type": "input",
      "label": "登记人员",
      "prop": "operNam",      
      "width":"100px" 
      
    },	 {
      "type": "input",
      "label": "登记时间",
      "prop": "crtTim",
      "width":"150px",
      "type":'date' 
    },	  {
      "type": "input",
      "label": "派车人员",
      "prop": "zyOperNam",
      "width":"100px" 
    },	  {
      "type": "input",
      "label": "派车时间",
      "prop": "zyTim",
      "width":"150px" 
    },  {
      "type": "input",
      "label": "进场人员",
      "prop": "jcOperNam",
      "width":"100px" 
    },	  {
      "type": "input",
      "label": "进场时间",
      "prop": "jcTim",
      "width":"150px" 
    },	  {
      "type": "input",
      "label": "完工人员",
      "prop": "overOperNam",
      "width":"100px" 
    },	  {
      "type": "input",
      "label": "完工时间",
      "prop": "overTim",
      "width":"150px" 
    }, {
      "type": "input",
      "label": "作废人员",
      "prop": "zfOperNam",
      "width":"100px" 
    },	   {
      "type": "input",
      "label": "作废时间",
      "prop": "zfTim",
      "width":"150px" 
    } ,	 {
      "type": "input",
      "label": "作废原因",
      "prop": "zfRemark",
      "width":"150px",
    },	  {
      "type": "select",
      "label": "状态",
      "prop": "zt",
      "width":"50px" ,
      "slot": true,
      "multiple": true,
      "search": true,
      "dicData": [{
          "label": '登记',
          "value": '02'
      }, {
          "label": '入场',
          "value": '03'
      }, {
        "label": '作业',
        "value": '04'
      }, {
        "label": '完工',
        "value": '05'
      }, {
        "label": '作废',
        "value": '00'
      }]
    },	  {
      "type": "input",
      "label": "主键",
      "prop": "etSeq",
      "width":"150px",
      "hide":true
    } ]
}
