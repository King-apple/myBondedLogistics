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
      "label": "识别号",
      "prop": "paramCod",
      "sortable":true,
      "editDisabled": true,
      "span": 12,
      rules: [
        {
          required: true,
          message: '参数识别号必须填写'
        }
      ]
    },	  {
      "type": "input",
      "label": "参数名称",
      "prop": "paramNam",
      "sortable":true,
      "search": true,
      "editDisabled": true,
      "span": 12,
      rules: [
        {
          required: true,
          message: '参数名称必须填写'
        }
      ]
    },	  {
      "type": "input",
      "label": "参数值",
      "prop": "paramValue",
      "span": 12,
      rules: [
        {
          required: true,
          message: '参数值必须填写'
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
      "span": 12,
      "display": false
    },	  {
      "type": "input",
      "label": "添加时间",
      "prop": "recordTim",
      "sortable":true,
      "span": 12,
      "display": false
    }]
}
