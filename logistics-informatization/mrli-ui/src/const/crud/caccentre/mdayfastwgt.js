export const tableOption = {
    "border": true,
    "filterBtn": false,
    refreshBtn:false,
    "index": false,
    "stripe": true,
    "menuAlign": "center",
    "align": "center",
    "size": "mini",
    "addBtn":false,
    "searchMenuSpan": 6,
    "selectionWidth": 40,
    "menuWidth": 160,
    "menuPosition": "center",
    columnBtn:false,
    "height": 550,
    "menu":false,
    "column": [
        {
            "type": "input",
            "label": "单位",
            "prop": "unitNam",
            "width":120,
            "fixed":true,
        },
        {
            "type": "input",
            "label": "日期",
            "prop": "workDte",
            "width":140,
            "fixed":true,
        },
        {
            "type": "input",
            "label": "工班判断 ，01：辽港特殊16点制工班，02营口港8点制工班",
            "prop": "classIdType",
            "display": false,
            showColumn:false,
            hide:true,
        },
        {
            "label": "本日完成",
            "fixed":true,
            children: [
                {
                    "type": "input",
                    "label": "艘次",
                    "prop": "dayShipNum",
                    "width":50,
                }, {
                    "type": "input",
                    "label": "合计",
                    "prop": "kind_0000",
                },
                {
                    "label": "外贸",
                    children: [
                        {
                            "type": "input",
                            "label": "小计",
                            "prop": "kind_0100",
                        }, {
                            "type": "input",
                            "label": "出口",
                            "prop": "kind_0110",
                        }, {
                            "type": "input",
                            "label": "进口",
                            "prop": "kind_0120",
                        },
                    ]
                },
                {
                    "label": "内贸",
                    children: [
                        {
                            "type": "input",
                            "label": "小计",
                            "prop": "kind_0200",
                        }, {
                            "type": "input",
                            "label": "出口",
                            "prop": "kind_0210",
                        }, {
                            "type": "input",
                            "label": "进口",
                            "prop": "kind_0220",

                        },
                    ]
                }
            ]
        },
        {
            "label": "本月累计", 
            children: [
                {
                    "type": "input",
                    "label": "计划",
                    "prop": "kindPlan",
                },
                {
                    "type": "input",
                    "label": "合计",
                    "prop": "kind_1000",
                },
                {  
                    "label": "外贸",
                    children: [
                        {
                            "type": "input",
                            "label": "小计",
                            "prop": "kind_1100",
                        }, {
                            "type": "input",
                            "label": "进口",
                            "prop": "kind_1110",
                        }, {
                            "type": "input",
                            "label": "出口",
                            "prop": "kind_1120",
                        }, 
                    ]
                },
                {  
                    "label": "内贸",
                    children: [
                        {
                            "type": "input",
                            "label": "小计",
                            "prop": "kind_1200",
                        }, {
                            "type": "input",
                            "label": "进口",
                            "prop": "kind_1210",
                        }, {
                            "type": "input",
                            "label": "出口",
                            "prop": "kind_1220",
                        } 
                    ]
                }
            ]
        },
        {
            "label": "自年初累计",
            children: [
                {
                    "type": "input",
                    "label": "合计",
                    "prop": "kind_2000",
                    
                },
                {"label": "外贸",
                children:[
                    {
                        "type": "input",
                        "label": "小计",
                        "prop": "kind_2100",
                        
                    }, {
                        "type": "input",
                        "label": "进口",
                        "prop": "kind_2110",
                      
                    }, {
                        "type": "input",
                        "label": "出口",
                        "prop": "kind_2120",
                       
                    }
                ]
                },
                {"label": "内贸",
                children:[
                    {
                        "type": "input",
                        "label": "小计",
                        "prop": "kind_2200",
                     
                    }, {
                        "type": "input",
                        "label": "进口",
                        "prop": "kind_2210",
                        
                    }, {
                        "type": "input",
                        "label": "出口",
                        "prop": "kind_2220",
                        
                    }
                ]
                }
            ]
        },
        {
            "type": "input",
            "label": "操作员",
            "prop": "operNam",
            "display": false,
            showColumn:false,
            hide:true,
        }, {
            "type": "input",
            "label": "记录时间",
            "prop": "recordTim",
            "display": false,
            showColumn:false,
            hide:true,
        }, {
            "type": "input",
            "label": "序号",
            "prop": "seqNo",
            "display": false,
            showColumn:false,
            hide:true,
        }, {
            "type": "input",
            "label": "$column.comments",
            "prop": "teuNum",
            "display": false,
            showColumn:false,
            hide:true,
        }, {
            "type": "input",
            "label": "单位代码",
            "prop": "unitCod",
            "display": false,
            showColumn:false,
            hide:true,
        }, {
            "type": "input",
            "label": "最终修改人",
            "prop": "updateNam",
            "display": false,
            showColumn:false,
            hide:true,
        }, {
            "type": "input",
            "label": "最终修改时间",
            "prop": "updateTim",
            "display": false,
            showColumn:false,
            hide:true,
        }]
}
