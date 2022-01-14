export const tableOption = {
  "border": true,
  "index": true,
  "indexLabel": "序号",
  "stripe": true,
  "menuAlign": "center",
  "selection": true,
  "align": "center",
  "searchMenuSpan": 6,
  "column": [
    {
      "type": "input",
      "label": "承办单位编号",
      "prop": "organizerCode",
      "sortable": true,
      "search": true,
      "span": 28,
      rules: [
              {
                required: true,
                message: '承办单位编号必须填写'
              }
            ]
    },	  {
      "type": "input",
      "label": "承办单位名称",
      "prop": "organizer",
      "sortable": true,
      "span": 28,
      rules: [
              {
                required: true,
                message: '承办单位名称必须填写'
              }
            ]
    },	  {
      "type": "input",
      "label": "备注",
      "prop": "remark",
      "sortable": true,
      "span": 28
    },	  {
      "type": "input",
      "label": "添加人",
      "prop": "operNam",
      "span": 28,
      "sortable": true,
      "display": false
    },	  {
      "type": "input",
      "label": "添加时间",
      "prop": "recordTim",
      "span": 28,
      "sortable": true,
      "display":false
    }]
}
