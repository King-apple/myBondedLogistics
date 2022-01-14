export const tableOption = {
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
      "label": "国别编号",
      "prop": "countryCode",
      "sortable":true,
      "search": true,
      "span": 12,
      rules: [
        {
          required: true,
          message: '国别编号必须填写'
        }
      ]
    },	  {
      "type": "input",
      "label": "国别名称",
      "prop": "countryName",
      "search": true,
      "sortable":true,
      "span": 12,
      rules: [
        {
          required: true,
          message: '国别名称必须填写'
        }
      ]
    },	  {
      "type": "input",
      "label": "备注",
      "prop": "markTxt",
      "span": 12
    },	  {
      "type": "input",
      "label": "添加人",
      "prop": "operNam",
      "sortable":true,
      "display": false,
      "span": 12
    },	  {
      "type": "input",
      "label": "添加时间",
      "prop": "recordTim",
      "sortable":true,
      "display": false,
      "span": 12
    }]
}
