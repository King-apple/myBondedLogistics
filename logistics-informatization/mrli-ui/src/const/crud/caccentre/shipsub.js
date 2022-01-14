export const tableOption = {
  "border": true,
  "addBtn":true,
  "index": true,
  "indexLabel": "序号",
  "stripe": true,
  "menuAlign": "center",
  "align": "center",
  "searchMenuSpan": 6,
  "menuWidth":90,
  "refreshBtn":false,
  "columnBtn":false,
  "column": [
	  {
      "type": "input",
      "label": "操作员",
      "prop": "operNam",
      hide:true,
    },	  {
      "type": "input",
      "label": "记录时间",
      "prop": "recordTim",
      hide:true,
    },	  {
      "type": "input",
      "label": "船名",
      "prop": "shipNam"
    },	  {
      "type": "input",
      "label": "船号",
      "prop": "shipNo"
    },	  {
      "type": "input",
      "label": "单位代码",
      "prop": "unitCod",
      width:80,
    },	  {
      "type": "input",
      "label": "单位名称",
      "prop": "unitNam",
      width:280,
    }  ]
}
