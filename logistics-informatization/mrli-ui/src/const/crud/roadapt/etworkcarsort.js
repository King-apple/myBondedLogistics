export const tableOption = {
  "border": true,
  "filterBtn":true,  
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
  "column": [
    {
      "type": "input",
      "label": "登记时间",
      "prop": "crtTime",
      "minWidth":"11%", 
      "type":'date' ,
      "valueFormat":"yyyyMMdd",
      "search":true,
      "hide":true
    },
	  {
      "type": "input",
      "label": "登记时间",
      "prop": "crtTim",
      "minWidth":"11%", 
      "type":'date' 
    },	  {
      "type": "input",
      "label": "货主",
      "prop": "consignNam",
      "minWidth":"19%" ,
      "search":true
     
    }, {
      "type": "input",
      "label": "货名",
      "prop": "cargoName",
      "minWidth":"9%",
      "search":true
    },   {
      "type": "input",
      "label": "车号",
      "prop": "trkTrkno",
      "minWidth":"7%",
      "search":true
    },{
      "type": "input",
      "label": "件数",
      "prop": "num",
      "minWidth":"4%"
    }, {
      "type": "input",
      "label": "重量",
      "prop": "weight",
      "minWidth":"4%"
    }, {
      "type": "input",
      "label": "司机",
      "prop": "driverNam",
      "minWidth":"6%"
    }, {
      "type": "input",
      "label": "电话",
      "prop": "phone",
      "minWidth":"9%"
    },  {
      "type": "input",
      "label": "场地名称",
      "prop": "yardNam",
      "minWidth":"9%" 
    }, {
      "type": "input",
      "label": "作业工艺",
      "prop": "techNam",
      "minWidth":"6%"
    }, {
      "type": "input",
      "label": "场地排号",
      "prop": "yardSortNo",
      "minWidth":"6%"
    },	 {
      "type": "input",
      "label": "排号",
      "prop": "sortNo",
      "minWidth":"4%"
    }, {
      "type": "input",
      "label": "过号",
      "prop": "remark1",
      "minWidth":"5%"
    },	{
      "type": "input",
      "label": "主键",
      "prop": "etSeq",
      "width":"150px",
      "hide":true
    },	  {
      "type": "input",
      "label": "场地确认人员",
      "prop": "jcOperCod",
      "width":"150px",
      "hide":true
    },	  {
      "type": "input",
      "label": "场地确认人员",
      "prop": "jcOperNam",
      "width":"150px",
      "hide":true
    },	  {
      "type": "input",
      "label": "场地确认时间",
      "prop": "jcTim",
      "width":"180px",
      "hide":true
    },	  {
      "type": "input",
      "label": "线路",
      "prop": "lineNo",
      "width":"150px",
      "hide":true
    },	  {
      "type": "input",
      "label": "预约确认人员",
      "prop": "operCod",
      "width":"150px",
      "hide":true
    },	  {
      "type": "input",
      "label": "预约确认人员",
      "prop": "operNam",
      "width":"150px",
      "hide":true
    },	  {
      "type": "input",
      "label": "结束操作员",
      "prop": "overOperCod",
      "width":"150px",
      "hide":true
    },	  {
      "type": "input",
      "label": "结束操作员",
      "prop": "overOperNam",
      "width":"150px",
      "hide":true
    },	  {
      "type": "input",
      "label": "结束时间",
      "prop": "overTim",
      "width":"180px",
      "hide":true
    },	  	  {
      "type": "input",
      "label": "派遣申请人员",
      "prop": "sqOperCod",
      "width":"150px",
      "hide":true
    },	  {
      "type": "input",
      "label": "派遣申请人员",
      "prop": "sqOperNam",
      "width":"150px",
      "hide":true
    },	  {
      "type": "input",
      "label": "派遣申请时间",
      "prop": "sqTim",
      "width":"180px",
      "hide":true
    },{
      "type": "select",
      "label": "场地",
      "prop": "yardNo",
      "width":"150px",
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
    },	 	  {
      "type": "select",
      "label": "工艺",
      "prop": "techNo",
      "width":"150px", 
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
    },		  {
      "type": "input",
      "label": "单位代码",
      "prop": "unitCod",
      "width":"150px",
      "hide":true
    },	  {
      "type": "input",
      "label": "修改备注",
      "prop": "updateRemark",
      "width":"150px",
      "hide":true
    },	  {
      "type": "input",
      "label": "日期",
      "prop": "workDte",
      "width":"180px",
      "hide":true
    },	  	  {
      "type": "input",
      "label": "场地排号人员",
      "prop": "yardOperCod",
      "width":"150px",
      "hide":true
    },	  {
      "type": "input",
      "label": "场地排号人员",
      "prop": "yardOperNam",
      "width":"150px",
      "hide":true
    },	  	  {
      "type": "input",
      "label": "场地排号时间",
      "prop": "yardTim",
      "width":"180px",
      "hide":true
    },	  {
      "type": "input",
      "label": "作废人员",
      "prop": "zfOperCod",
      "width":"150px",
      "hide":true
    },	  {
      "type": "input",
      "label": "作废人员",
      "prop": "zfOperNam",
      "width":"150px",
      "hide":true
    },	  {
      "type": "input",
      "label": "$column.comments",
      "prop": "zfRemark",
      "width":"150px",
      "hide":true
    },	  {
      "type": "input",
      "label": "作废时间",
      "prop": "zfTim",
      "width":"180px",
      "hide":true
    },	  {
      "type": "input",
      "label": "状态",
      "prop": "zt",
      "width":"150px",
      "hide":true
    },	  {
      "type": "input",
      "label": "派遣确认人员",
      "prop": "zyOperCod",
      "width":"150px",
      "hide":true
    },	  {
      "type": "input",
      "label": "派遣确认人员",
      "prop": "zyOperNam",
      "width":"150px",
      "hide":true
    },	  {
      "type": "input",
      "label": "派遣确认时间",
      "prop": "zyTim",
      "width":"180px",
      "hide":true
    }  ]
}
