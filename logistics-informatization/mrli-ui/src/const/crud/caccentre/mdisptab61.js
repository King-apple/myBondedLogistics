export const tableOption6 = {
  "border": true,
  "stripe": true,
  "menuAlign": "center",
  "align": "center",
  "searchMenuSpan": 6,
  "menu": false,
  "addBtn": false,
  "refreshBtn": false,
  "columnBtn": false,
  "searchBtn": false,
  "page": false,
  "height":450,
  "column": [{
    "type": "input",
    "label": "编号",
    "prop": "shipClaNo"
  }, {
    "type": "input",
    "label": "船名",
    "prop": "shipNam"
  }, {
    "type": "input",
    "label": "内外贸",
    "prop": "tradeId"
  }, {
    "type": "input",
    "label": "船籍",
    "prop": "shipHomeCod"
  }, {
    "type": "input",
    "label": "抵港时间",
    "prop": "toAnchTim",
    "width": "160px",
  }, {
    "type": "input",
    "label": "靠泊时间",
    "prop": "toBerthTim",
    "width": "160px",
  }, {
    "label": '开工时间',
    "children": [{
      "type": "input",
      "label": "进口",
      "prop": "inBeginTim",
      "width": "160px",
    }, {
      "type": "input",
      "label": "出口",
      "prop": "outBeginTim",
      "width": "160px",
    }]
  }, {
    "label": '地点',
    "children": [{
      "type": "input",
      "label": "进口",
      "prop": "inBerthNo"
    }, {
      "type": "input",
      "label": "出口",
      "prop": "outBerthNo"
    }]
  }, {
    "label": '完工时间',
    "children": [{
      "type": "input",
      "label": "进口",
      "prop": "inEndTim",
      "width": "160px",
    }, {
      "type": "input",
      "label": "出口",
      "prop": "outEndTim",
      "width": "160px",
    }]
  }, {
    "type": "input",
    "label": "离港时间",
    "prop": "levPortTim",
    "width": "160px",
  }, {
    "label": '进口货物1',
    "children": [{
      "type": "input",
      "label": "货名",
      "prop": "inCargoNam",
      "overHidden":true
    }, {
      "type": "input",
      "label": "吨数",
      "prop": "inShipCap1"
    }, {
      "type": "input",
      "label": "箱数",
      "prop": "inTeuNum1"
    }]
  }, {
    "label": '进口货物2',
    "children": [{
      "type": "input",
      "label": "货名",
      "prop": " "
    }, {
      "type": "input",
      "label": "吨数",
      "prop": "inShipCap2"
    }, {
      "type": "input",
      "label": "箱数",
      "prop": "inTeuNum2"
    }]
  }, {
    "label": '进口货物3',
    "children": [{
      "type": "input",
      "label": "货名",
      "prop": " "
    }, {
      "type": "input",
      "label": "吨数",
      "prop": "inShipCap3"
    }, {
      "type": "input",
      "label": "箱数",
      "prop": "inTeuNum3"
    }]
  }, {
    "label": '出口货物1',
    "children": [{
      "type": "input",
      "label": "货名",
      "prop": "outCargoNam",
      "overHidden":true
    }, {
      "type": "input",
      "label": "吨数",
      "prop": "outShipCap1"
    }, {
      "type": "input",
      "label": "箱数",
      "prop": "outTeuNum1"
    }]
  }, {
    "label": '出口货物2',
    "children": [{
      "type": "input",
      "label": "货名",
      "prop": " "
    }, {
      "type": "input",
      "label": "吨数",
      "prop": "outShipCap2"
    }, {
      "type": "input",
      "label": "箱数",
      "prop": "outTeuNum2"
    }]
  }, {
    "label": '出口货物3',
    "children": [{
      "type": "input",
      "label": "货名",
      "prop": " "
    }, {
      "type": "input",
      "label": "吨数",
      "prop": "outShipCap3"
    }, {
      "type": "input",
      "label": "箱数",
      "prop": "outTeuNum3"
    }]
  }, {
    "type": "input",
    "label": "剩吨",
    "prop": "surplusTon"
  }, {
    "type": "input",
    "label": "通信",
    "prop": "commId"
  }]
}
