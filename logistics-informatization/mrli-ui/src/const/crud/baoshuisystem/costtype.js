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
      "label": "费用类型编码",
      "prop": "costTypeCode",
      "span": 12,
      "sortable": true,
      rules: [
              {
                required: true,
                message: '费用类型编码必须填写'
              }
            ]
    },	  {
      "type": "input",
      "label": "费用类型名称",
      "search": true,
      "prop": "costType",
      "span": 12,
      "sortable": true,
      rules: [
              {
                required: true,
                message: '费用类型名称必须填写'
              }
            ]
    },	  {
      "type": "input",
      "label": "费率",
      "prop": "rate",
      "span": 12,
      "sortable": true,
      rules: [
              {
                required: true,
                message: '费率必须填写'
              }],
    },
    {
      "type": "select",
      "label": "费率单位",
      "prop": "rateUnit",
      "span": 12,
      "display": true,
      "sortable": true,
      rules: [
      {
        required: true,
        message: '费率单位必须填写'
      }
      ],
      "cascaderItem": [],
      props: {
      label: 'label',
      value: 'value'
      },
      "dicUrl": '/admin/dict/type/rate_unit',
      "filterable": true
    },
    {
        "type": "select",
        "label": "货类",
        "sortable": true,
        "prop": "goodsCategory",
        "span": 12,
        "cascaderItem": [],
        "display": true,
        props: {
          label: 'categoryName',
          value: 'categoryName'
        },
        "dicUrl": '/baoshuisystem/goodscategory/dict',
        "filterable": true,
      },
      {
          "type": "select",
          "label": "是否为协议用户",
          "sortable": true,
          "prop": "userAgreement",
          "span": 12,
          "cascaderItem": [],
          "display": true,
          props: {
            label: 'label',
            value: 'value'
          },
          "dicUrl": '/admin/dict/type/user_agreement',
          "filterable": true,
        },
        {
          "type": 'input',
          "label": '存放地点',
          "sortable": true,
          "prop": 'storagePlace',
          "span": 12,
          "display": true
        },
        {
            "type": "select",
            "label": "内外贸",
            "sortable": true,
            "prop": "tradeType",
            "span": 12,
            "cascaderItem": [],
            "display": true,
            props: {
              label: 'label',
              value: 'value'
            },
            "dicUrl": '/admin/dict/type/trade_type',
            "filterable": true,
          },
          {
              "type": "select",
              "label": "进出口",
              "sortable": true,
              "prop": "importExport",
              "span": 12,
              "cascaderItem": [],
              "display": true,
              props: {
                label: 'label',
                value: 'value'
              },
              "dicUrl": '/admin/dict/type/import_export',
              "filterable": true,
            },
    {
      "type": "input",
      "label": "备注",
      "prop": "markTxt",
      "span": 12,
      "sortable": true
    },	  {
      "type": "input",
      "label": "添加人",
      "prop": "operNam",
      "span": 12,
      "display": false,
      "sortable": true
    },	  {
      "type": "input",
      "label": "添加时间",
      "prop": "recordTim",
      "span": 12,
      "display": false,
      "sortable": true
    }]
}
