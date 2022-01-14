export const tableOption = {
    "border": true,
    "filterBtn":true,
    "index": true,
    "indexLabel": "序号",
    "stripe": true,
    "menuAlign": "center",
    "align": "center",
    "size":"mini",
    "viewBtn":true,
    "selection":true,
    "searchMenuSpan": 6,
    "selectionWidth":40,
    "menuWidth":160,
    "menuPosition":"center",
    "height":550,
    //定义用于存储数据的两个数组
    data() {
        return {
           groupArr: [],
           groupList: [],
      }
    },
    "column": [
        {
            "type": "input",
            "label": "序号",
            "prop": "seqNo",
            "hide":true
        },          
        {
            "type": "input",
            "label": "在港状态",
            "prop": "arivportID",
            "slot": true
        },                    {
            "type": "input",
            "label": "船号",
            "prop": "shipNo"
          
        },              {
            "type": "input",
            "label": "船名",
            "prop": "shipNam"
        },              
        {
            "type": "input",
            "label": "泊位",
            "prop": "berthCode",
            "slot": true
        },               
                    {
                "type": "input",
                "label": "开始时间",
                "prop": "beginTim"
            },                    {
                "type": "input",
                "label": "开舱数",
                "prop": "cabinNum"
            },                    {
                "type": "input",
                "label": "所开舱",
                "prop": "cabinStr"
            },                    {
                "type": "input",
                "label": "标志码",
                "prop": "code"
            },                    {
                "type": "input",
                "label": "结束时间",
                "prop": "endTim"
            },                    {
                "type": "input",
                "label": "操作员",
                "prop": "operNam"
            },                    {
                "type": "input",
                "label": "记录时间",
                "prop": "recordTim"
            },                    {
                "type": "input",
                "label": "$column.comments",
                "prop": "sendManNum",
                "hide":true
            },                           {
                "type": "input",
                "label": "动态",
                "prop": "status"
            },                    {
                "type": "input",
                "label": "动态代码",
                "prop": "statCod"
            },                    {
                "type": "input",
                "label": "$column.comments",
                "prop": "teuNum",
                 "hide":true
            },                    {
                "type": "input",
                "label": "累计吨数",
                "prop": "totWgt"
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
                "label": "最终修改人",
                "prop": "updateNam"
            },                    {
                "type": "input",
                "label": "最终修改时间",
                "prop": "updateTim"
            },                    {
                "type": "input",
                "label": "工班作业吨数",
                "prop": "weightWgt"
            }            ]
}
