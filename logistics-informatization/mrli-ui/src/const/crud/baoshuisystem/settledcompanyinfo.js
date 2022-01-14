export const tableOption = {
  "border": true,
  "index": true,
  "indexLabel": "序号",
  "stripe": true,
  "menuAlign": "center",
  "align": "center",
  "searchMenuSpan": 6,
  "column": [{
      "type": "input",
      "label": "入驻企业代码",
      "prop": "companyCode",
	  "sortable": true,
      "span": 12,
	  rules: [
	          {
	            required: true,
	            message: '入驻企业代码必须填写'
	          }
	        ]
    },	  {
      "type": "input",
      "label": "入驻企业名称",
      "prop": "companyName",
	  "search": true,
	  "sortable": true,
      "span": 12,
	  rules: [
	          {
	            required: true,
	            message: '入驻企业名称必须填写'
	          }
	        ]
    },	  {
      "type": "input",
      "label": "电子账册号",
      "prop": "electronicAccountNum",
      "span": 12,
	  "sortable": true,
	  rules: [
	          {
	            required: true,
	            message: '电子账册号必须填写'
	          }
	        ]
    },	  {
      "type": "input",
      "label": "备注",
      "prop": "markTxt",
	  "sortable": true,
      "span": 12
    },	  {
      "type": "input",
      "label": "添加人",
      "prop": "operNam",
	  "sortable": true,
    "display": false,
      "span": 12
    },	  {
      "type": "input",
      "label": "添加时间",
      "prop": "recordTim",
	  "sortable": true,
    "display": false,
      "span": 12
    }]
}
