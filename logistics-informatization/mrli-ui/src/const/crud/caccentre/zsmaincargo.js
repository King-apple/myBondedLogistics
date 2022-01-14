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
    "label": "组织机构",
    "prop": "ykportNam",
    "minWidth":"8%"
    },{
      "type": "input",
      "label": "流向",
      "prop": "inoutId",
      "slot": true,
      "multiple": true,   
      "minWidth":"5%",   
      "dicData": [{
          "label": '出口',
          "value": '0'
      }, {
          "label": '进口',
          "value": '1'
      } ]
    },	 {
      "type": "input",
      "label": "内外贸",
      "prop": "tradeId",
      "slot": true,
      "multiple": true, 
      "minWidth":"6%",     
      "dicData": [{
          "label": '外贸',
          "value": '0'
      }, {
          "label": '内贸',
          "value": '1'
      } ]
    },  {
      "type": "input",
      "label": "货类代码",
      "prop": "cargoKindCod",
      "minWidth":"8%"
    },	  {
      "type": "input",
      "label": "货类名称",
      "prop": "cargoKindNam",
      "minWidth":"12%",
      "overHidden":true
    },	 {
      "type": "input",
      "label": "重量/TEU",
      "prop": "weightWgt",
      "minWidth":"9%"
    },	  {
      "type": "input",
      "label": "公司代码",
      "prop": "unitCod",
      "minWidth":"7%"
    }, {
      "type": "input",
      "label": "公司名称",
      "prop": "unitNam",
      "minWidth":"26%",
      "overHidden":true
    },	   {
      "type": "input",
      "label": "大连代码",
      "prop": "unitCodZs",
      "minWidth":"7%"
    },		  {
      "type": "input",
      "label": "单位",
      "prop": "unit",
      "minWidth":"6%"
    },		 {
      "type": "checkbox",
      "label": "传",
      "prop": "upId",
      "minWidth":"6%",
      "slot": true
    },     {
      "type": "input",
      "label": "0港区，1",
      "prop": "dataKnd",
      "hide":true
    },	  	  {
      "type": "input",
      "label": "$column.comments",
      "prop": "operNam",
      "hide":true
    },	  {
      "type": "input",
      "label": "$column.comments",
      "prop": "pkid",
      "hide":true
    },	  {
      "type": "input",
      "label": "$column.comments",
      "prop": "recordTim",
      "hide":true
    },	  	  {
      "type": "input",
      "label": "$column.comments",
      "prop": "updateNam",
      "hide":true
    },	  {
      "type": "input",
      "label": "$column.comments",
      "prop": "updateTim",
      "hide":true
    },	 	 {
      "type": "input",
      "label": "$column.comments",
      "prop": "workDte",
      "hide":true
    },	  {
      "type": "input",
      "label": "$column.comments",
      "prop": "ykportCod",
      "hide":true
    }    ]
}
