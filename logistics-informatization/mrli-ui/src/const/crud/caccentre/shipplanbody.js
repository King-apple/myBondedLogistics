import moment from 'moment'
export const option ={
    "menuBtn": false,
    "submitBtn": false,
    "emptyBtn": false,
    "menu": false,
    "border": true,
    "size":"small",
    "stripe": true,
    "menuAlign": "center",
    "align": "center",
    "column":[
        {
            "type": "input",
            "label": "主任",
            "prop": "masterMan",
            "span":8,
            "placeholder": "",
        },
        {
            "type": "input",
            "label": "计划员",
            "prop": "planMan",
            "span":8,
            "placeholder": "",
        },
        {
            "type": "textarea",
            "label": "重点说明",
            "prop": "contentTxt",
            "span":30,
            "placeholder": "null",
        },
    ]
}
export const tableOption = {
    "addBtn": false,
    "delBtn": false,
    excelBtn :true,
    printBtn :true,
    "border": true,
    "filterBtn":true,
    "index": true,
    "indexLabel": "序号",
    "stripe": true,
    "menuAlign": "center",
    "align": "center",
    "size":"mini",
    "selection":true,
    "searchMenuSpan": 6,
    "selectionWidth":40,
    "menuWidth":160,
    "menuPosition":"center",
    "height":300,
    "column": [
                    {
                "type": "input",
                "label": "泊位",
                "prop": "berthCod"
            },                    {
                "type": "input",
                "label": "船名",
                "prop": "shipNam"
            },                    {
                "type": "input",
                "label": "清污协议",
                "prop": "cleanprot"
            },                    {
                "type": "input",
                "label": "代理代码",
                "prop": "agentCod"
            },                    {
                "type": "input",
                "label": "代理名称",
                "prop": "agentNam"
            },                    {
                "type": "input",
                "label": "单位代码",
                "prop": "unitCod"
            },                    {
                "type": "input",
                "label": "单位名称",
                "prop": "unitNam"
            },                    {
                "type": "input",
                "label": "装卸别",
                "prop": "unloadId"
            },                    {
                "type": "input",
                "label": "货名",
                "prop": "cargoNam"
            },                    {
                "type": "input",
                "label": "重量",
                "prop": "weightWgt"
            },                    {
                "type": "input",
                "label": "余吨",
                "prop": "surplusTon"
            },                    {
                "type": "input",
                "label": "起地点",
                "prop": "beginPlace"
            },                    {
                "type": "input",
                "label": "止地点",
                "prop": "endPlace"
            },                    {
                "type": "input",
                "label": "动向",
                "prop": "statusCod"
            },                    {
                "type": "input",
                "label": "动向",
                "prop": "statusNam"
            },                    {
                "type": "input",
                "label": "靠泊刻度",
                "prop": "firstNum"
            },                    {
                "type": "input",
                "label": "临时抛锚时间",
                "prop": "toLsportTim"
            },                    {
                "type": "input",
                "label": "抵港时间",
                "prop": "toPortTim"
            },                    {
                "type": "input",
                "label": "计划靠泊时间",
                "prop": "planBerthTim"
            },                    {
                "type": "input",
                "label": "计划开始时间",
                "prop": "planBeginTim"
            },                    {
                "type": "input",
                "label": "计划完工时间",
                "prop": "planEndTim"
            },                    {
                "type": "input",
                "label": "计划离港时间",
                "prop": "planLeavTim"
            },                    {
                "type": "input",
                "label": "计划移泊时间",
                "prop": "moveBerthTim"
            },                    {
                "type": "input",
                "label": "船长",
                "prop": "shipLongNum"
            },                    {
                "type": "input",
                "label": "抵港艏吃水",
                "prop": "arrivHeadWat"
            },                    {
                "type": "input",
                "label": "抵港平均吃水",
                "hide": "true",
                "editDisplay":false,
                "prop": "arrivMidWat"
            },                    {
                "type": "input",
                "label": "抵港艉吃水",
                "prop": "arrivTailWat"
            },                    {
                "type": "input",
                "label": "离港艏吃水",
                "prop": "levHeadWat"
            },                    {
                "type": "input",
                "label": "离港平均吃水",
                "hide": "true",
                "editDisplay":false,
                "prop": "levMidWat"
            },                    {
                "type": "input",
                "label": "离港艉吃水",
                "prop": "levTailWat"
            },                    {
                "type": "input",
                "label": "拖轮否（1是0否）",
                "prop": "tugboatId"
            },                    {
                "type": "input",
                "label": "引航否（1是0否）",
                "prop": "pilotId"
            },                    {
                "type": "input",
                "label": "备注",
                "prop": "remarkTxt"
            },                    {
                "type": "input",
                "label": "货名代码",
                "hide": "true",
                "editDisplay":false,
                "prop": "cargoNamCod"
            },                    {
                "type": "input",
                "label": "修改标志",
                "hide": "true",
                "editDisplay":false,
                "prop": "changeId"
            },                    {
                "type": "input",
                "label": "$column.comments",
                "hide": "true",
                "editDisplay":false,
                "prop": "eShipNam"
            },                    {
                "type": "input",
                "label": "$column.comments",
                "hide": "true",
                "editDisplay":false,
                "prop": "fstRegNo"
            },                    {
                "type": "input",
                "label": "计划头主键",
                "hide": "true",
                "editDisplay":false,
                "prop": "headSequenceId"
            },                    {
                "type": "input",
                "label": "$column.comments",
                "hide": "true",
                "editDisplay":false,
                "prop": "id"
            },                    {
                "type": "input",
                "label": "操作员",
                "hide": "true",
                "editDisplay":false,
                "prop": "operNam"
            },                    {
                "type": "input",
                "label": "$column.comments",
                "hide": "true",
                "editDisplay":false,
                "prop": "perClassTon"
            },                    {   
                "type": "input",
                "label": "$column.comments",
                "hide": "true",
                "editDisplay":false,
                "prop": "pilotTxt"
            },                    {
                "type": "date",
                "label": "计划日期",
                "hide": "true",
                "editDisplay":false,
                "prop": "planDte",
            },                    {
                
                "type": "input",
                "label": "计划去锚时间",
                "hide": "true",
                "editDisplay":false,
                "prop": "planPortTim"
            },                    {
                "type": "input",
                "label": "记录时间",
                "hide": "true",
                "editDisplay":false,
                "prop": "recordTim"
            },                    {
                "type": "input",
                "label": "二班次吨数",
                "hide": "true",
                "editDisplay":false,
                "prop": "secondNum"
            },                    {
                "type": "input",
                "label": "计划体主键",
                "hide": "true",
                "editDisplay":false,
                "prop": "sequenceId"
            },                    {
                "type": "input",
                "label": "$column.comments",
                "hide": "true",
                "editDisplay":false,
                "prop": "sequenceIdUp"
            },                    {
                "type": "input",
                "label": "船号",
                "hide": "true",
                "editDisplay":false,
                "prop": "shipNo"
            },                    {               
                "type": "input",
                "label": "三班次吨数",
                "hide": "true",
                "editDisplay":false,
                "prop": "thirdNum"
            },                    {
                "type": "input",
                "label": "$column.comments",
                "hide": "true",
                "editDisplay":false,
                "prop": "tradeId"
            },                    {
                "type": "input",
                "label": "$column.comments",
                "hide": "true",
                "editDisplay":false,
                "prop": "tugboatTxt"
            },                    {
                "type": "input",
                "label": "最终修改人",
                "hide": "true",
                "editDisplay":false,
                "prop": "updateNam"
            },                    {
                "type": "input",
                "label": "最终修改时间",
                "hide": "true",
                "editDisplay":false,
                "prop": "updateTim"
            }  
         ]
}
