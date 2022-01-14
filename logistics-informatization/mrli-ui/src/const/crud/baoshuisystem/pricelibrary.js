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
      "label": "客户代码",
      "prop": "companyCod",
      "sortable":true,
      "span": 12,
      rules: [
        {
          required: true,
          message: '客户代码必须填写'
        }
      ]
    },	  {
      "type": "input",
      "label": "客户名称",
      "prop": "company",
      "sortable":true,
      "search": true,
      "span": 12
    },	  {
      "type": "input",
      "label": "货物编码",
      "prop": "cargoCod",
      "sortable":true,
      "span": 12,
      rules: [
        {
          required: true,
          message: '货物编码必须填写'
        }
      ]
    },	  {
      "type": "input",
      "label": "货物名称",
      "prop": "cargoNam",
      "sortable":true,
      "search": true,
      "span": 12
    },	  {
      "type": "input",
      "label": "规格型号",
      "prop": "format",
      "span": 12
    },	  {
      "type": "input",
      "label": "包装",
      "prop": "pkgKindNam",
      "span": 12
    },	  {
      "type": "input",
      "label": "费目编码",
      "prop": "feeNameCod",
      "sortable":true,
      "span": 12,
      rules: [
        {
          required: true,
          message: '费目编码必须填写'
        }
      ]
    },	  {
      "type": "input",
      "label": "费目",
      "prop": "feeNam",
      "sortable":true,
      "search": true,
      "span": 12
    },	  {
      "type": "input",
      "label": "费率值",
      "prop": "feeRat",
      "sortable":true,
      "span": 12,
      rules: [
        {
          required: true,
          message: '费率值必须填写'
        }
      ]
    },	  {
      "type": "input",
      "label": "费率单位",
      "prop": "feeUnit",
      "span": 12,
      rules: [
        {
          required: true,
          message: '费率单位必须填写'
        }
      ]
    },	  {
      "type": "input",
      "label": "备注",
      "prop": "markTxt",
      "span": 12
    },	  {
      "type": "input",
      "label": "操作人",
      "prop": "operNam",
      "sortable":true,
      "display": false,
      "span": 12
    },	  {
      "type": "input",
      "label": "操作时间",
      "prop": "recordTim",
      "sortable":true,
      "display": false,
      "span": 12
    }]
}
