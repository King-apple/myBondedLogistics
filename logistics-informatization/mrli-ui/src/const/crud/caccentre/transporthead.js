export const tableOption = {
    "border": true,
    "index": true,
    "indexLabel": "序号",
    "stripe": true,
    "menuAlign": "center",
    "menuWidth": 120,
    "align": "center",
    "searchMenuSpan": 6,
    "addBtn": false,
    "refreshBtn": false,
    "columnBtn": false,
    "searchBtn": false,
    "editBtn": false,
    "delBtn": false,
    "height": 200,
    "column": [
        {
            "type": "input",
            "label": "计划号",
            "prop": "informNo",
            "width":100 
        }, {
            "type": "input",
            "label": "内外贸",
            "prop": "tradeId",
            "slot":true,
            "width":80 
        }, {
            "type": "input",
            "label": "发货单位",
            "prop": "setownerNam",
            "width":200, 
            "overHidden":true
        }, {
            "type": "input",
            "label": "委托人",
            "prop": "consignNam",
            "width":200,
            "overHidden":true
        }, {
            "type": "input",
            "label": "联系方式",
            "prop": "releWay",
            "width":100 ,
            "overHidden":true
        }, {
            "type": "input",
            "label": "作业公司",
            "prop": "unitNam",
            "width":200,
            "overHidden":true
        }, {
            "type": "input",
            "label": "保管单位",
            "prop": "subunitNam",
            "width":150,
            "overHidden":true
        }, {
            "type": "input",
            "label": "出发地",
            "prop": "startNam",
            "width":100,
            "overHidden":true
        }, {
            "type": "input",
            "label": "目的地",
            "prop": "endNam",
            "width":100,
            "overHidden":true
        }, {
            "type": "input",
            "label": "承运人",
            "prop": "carPortNam",
            "width":200,
            "overHidden":true
        },{
            "type": "input",
            "label": "备注",
            "prop": "remarkTxt",
            "width":100,
            "overHidden":true
        }, {
            "type": "input",
            "label": "状态",
            "prop": "up",
            "width":80 ,
            "slot":true
        }]
}
