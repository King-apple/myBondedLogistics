export const tableOption = {
    "border": true,
    "addBtn": false,
    "editBtn": false,
    "delBtn": false,
    columnBtn:false,
    filterBtn:false,
    refreshBtn:false,
    searchBtn:false,
    columnBtn:false,
    "index": true,
    "indexLabel": "序号",
    "stripe": true,
    "menuAlign": "center",
    "align": "center",
    "size":"mini",
    "selection":false,
    "searchMenuSpan": 6,
    "selectionWidth":40,
    "menuWidth":260,
    "menuPosition":"center",
    "height":550,
    "column": [
            {
                "type": "input",
                "label": "泊位",
                "prop": "berthCod",
                width:50,
                fixed:true,
                
            },                    {
                "type": "input",
                "label": "船名",
                "prop": "shipNam",
                fixed:true,
                width:80,
            },                    {
                "type": "input",
                "label": "作业单位",
                fixed:true,    
                
                children: [{
                    label: '代码',
                    prop: 'unitCod',
 
                    width:50,
                  }, {
                    label: '名称',
                    prop: 'unitNam',
                    width:230,
                  }],
                  

            },                    {
                "type": "input",
                "label": "装卸", 
                "prop": "unloadId",
                width:50,
                fixed:true,
            },                    {
                "type": "input",
                "label": "货名",
                "prop": "cargoNam",
                "width": 60,
                fixed:true,
            },                    {
                "type": "input",
                "label": "计划量",
                "prop": "planWeightNam",
                "width": 60,
                fixed:true,
            },                    {
                "type": "input",
                "label": "完成吨",
                "prop": "weightNam",
                "width": 60,
                fixed:true,
            },                    {
                "type": "input",
                "label": "中海标志",
                "prop": "zh",
                width:70,
                "editDisplay":false,
                "slot": true,
            },                    {
                "type": "input",
                "label": "临时抛锚时间",
                "width": "100px",
                "prop": "toLsportTim",
                "width": 90,
                
            },                    {
                "type": "input",
                "label": "到港时间",
                "prop": "toPortTim",
                "width": 90,
                
            },                    {
                "type": "input",
                "label": "靠泊时间",
                "prop": "toBerthTim",
                "editDisplay":false,
                "width": 90,
                
            },                    {
                "type": "input",
                "label": "开工时间",
                "prop": "beginTim",
                "editDisplay":false,
                "width": 90,
                
            },                    {
                "type": "input",
                "label": "完成时间",
                "prop": "endTim",
                "editDisplay":false,
                "width": 90,
                
            },                    {
                "type": "input",
                "label": "船长",
                "prop": "shipLongNum",
                "width": 60,
            },                    {
                "type": "input",
                "label": "抵港吃水",
                "width": "120px",
                "prop": "dgcs",
                
            },                    {
			    "type": "input",
                "label": "离港吃水",
                "width": "120px",
                "prop": "lgcs",
                
            },                    
            {
			    "type": "input",
                "label": "船舶状态",
                "prop": "statusid",
                hide:true,
            },{



                "type": "input",
                "label": "船呼号",
                "prop": "callNo",
                hide:true,
            },                    {
                
                "type": "input",
                "label": "进口货名",
                "prop": "inCargoNam",
                hide:true,
            },                    {	
			    "type": "input",
                "label": "进口载重",
                "prop": "inShipCap",
                hide:true,
            },                    {
			    "type": "input",
                "label": "出口货名",
                "prop": "outCargoNam",
                hide:true,
            },                    {	
			    "type": "input",
                "label": "出口载重",
                "prop": "outShipCap",
                hide:true,
            },                    {
                "type": "input",
                "label": "代理",
                "prop": "agentNam",
                hide:true,
            },                    {
                "type": "input",
                "label": "离港时间",
                "prop": "levPortTim",
                "editDisplay":false,
                "width": 90,
                hide:true,
            }, {
                "type": "input",
                "label": "代理",
                "prop": "agentShort",
                hide:true,
            },                    {
                "type": "input",
                "label": "北纬",
                "prop": "placeN",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "东经",
                "prop": "placeE",
                "editDisplay":false,
                hide:true,
            },                    {
                


			    
			    "type": "input",
                "label": "国籍",
                "prop": "ccountryNam",
                hide:true,
            },                    {  
			    "type": "input",
                "label": "内外贸",
                "prop": "tradeId",
                hide:true,
            },                    {
                
			    "type": "input",
                "label": "清污协议",
                "prop": "cleanprot",
                hide:true,
            },                    {

			    "type": "input",
                "label": "预抵时间",
                "width": "150px",
                "prop": "edtArivTim",
                "width": 90,
                hide:true,
            },                    {
			    
			    
                "type": "input",
                "label": "船宽",
                "prop": "shipWideNum",
                hide:true,
            },                    {
                "type": "input",
                "label": "型深",
                "prop": "typeDeepNum",
                hide:true,
            },                    {
                
				"type": "input",
                "label": "净吨",
                "prop": "netWgt",
                hide:true,
            },                    {
			    "type": "input",
                "label": "载重吨",
                "prop": "shipCap",
                hide:true,
            },                    {
			
			    "type": "input",
                "label": "舱口数",
                "prop": "impHatchNum",
                hide:true,
            },                    {
			    "type": "input",
                "label": "开来港",
                "prop": "fromPortNam",
                hide:true,
            },                    {
			    "type": "input",
                "label": "上一港",
                "prop": "prePortNam",
                hide:true,
            },                    {
			    "type": "input",
                "label": "目的港",
                "prop": "toPortNam",
                hide:true,
            },                    {
			    "type": "input",
                "label": "理货单位",
                "width": "120px",
                "prop": "tallyNam",
                hide:true,
            },                    {
                "type": "input",
                "label": "$column.comments",
                "prop": "agentCheck",
                hide:true,
            },                    {
                "type": "input",
                "label": "代理代码",
                "prop": "agentCod",
                hide:true,
            },                    {
                
                "type": "input",
                "label": "$column.comments",
                "prop": "agentCod01",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "$column.comments",
                "prop": "agentKnd",
                "editDisplay":false,
                hide:true,
            },                    {

                "type": "input",
                "label": "$column.comments",
                "prop": "agentNam02",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "$column.comments",
                "prop": "agentPhone",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "$column.comments",
                "prop": "agentXingming",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "锚位",
                "prop": "anchorPositioan",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "前缆桩",
                "prop": "anteCable",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "抵港标志,1在港，3离港",
                "prop": "arivportId",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "抵港艏吃水",
                "prop": "arrivHeadWat",
                hide:true,
            },                    {
                "type": "input",
                "label": "抵港中吃水",
                "prop": "arrivMidWat",
                hide:true,
            },                    {
                "type": "input",
                "label": "抵港艉吃水",
                "prop": "arrivTailWat",
                hide:true,
            },                    {
                "type": "input",
                "label": "回退标志 1回退 0不回退",
                "prop": "backId",
                "editDisplay":false,
                hide:true,
            },                    {
               
                "type": "input",
                "label": "停靠方式",
                "prop": "berthWay",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "备注",
                "prop": "boomStr",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "$column.comments",
                "prop": "by1",
                "editDisplay":false,
                hide:true,
            },                    {
            
                "type": "input",
                "label": "$column.comments",
                "prop": "captainTel",
                "editDisplay":false,
                hide:true,
            },                    {
            
                "type": "input",
                "label": "船籍代码",
                "prop": "countryCod",
                hide:true,
            },                    {
                "type": "input",
                "label": "海关编码",
                "prop": "customNo",
                "editDisplay":false,
                hide:true,
            },                    {
            
                "type": "input",
                "label": "离港地点代码",
                "prop": "departPortCod",
                hide:true,
            },                    {
                "type": "input",
                "label": "吊杆负荷",
                "prop": "derrickCap",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "卸货开始时间",
                "prop": "discBegTim",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "卸货结束时间",
                "prop": "discEndTim",
                "editDisplay":false,
                hide:true,
            },                    {
            
                "type": "input",
                "label": "委托人代码",
                "prop": "entrustCod",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "委托人",
                "prop": "entrustNam",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "出口舱口数",
                "prop": "expHatchNum",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "$column.comments",
                "prop": "fapiaoLei",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "$column.comments",
                "prop": "flagId",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "预报标志",
                "prop": "forCastId",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "来港代码",
                "prop": "fromPortCod",
                hide:true,
            },                    {
            
                "type": "input",
                "label": "$column.comments",
                "prop": "fstRegNo",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "是否是来源于物流平台，是＝1，否＝0",
                "prop": "ifPtId",
                "editDisplay":false,
                hide:true,
            },                    {
            
                "type": "input",
                "label": "进口货名代码",
                "prop": "inCargoCod",
                hide:true,
            },                    {
                "type": "input",
                "label": "进口货类",
                "prop": "inCargoKnd",
                hide:true,
            },                    {
            
                "type": "input",
                "label": "实际卸货",
                "prop": "inFactTon",
                "editDisplay":false,
                hide:true,
            },                    {
            
                "type": "input",
                "label": "进口TEU",
                "prop": "inTeuNum",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "进口航次",
                "prop": "inVoyage",
                hide:true,
            },                    {
                "type": "input",
                "label": "$column.comments",
                "prop": "kpKind",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "离港艏吃水",
                "prop": "levHeadWat",
                hide:true,
            },                    {
                "type": "input",
                "label": "离港中吃水",
                "prop": "levMidWat",
                hide:true,
            },                    {
                
                "type": "input",
                "label": "离港记录时间",
                "prop": "levRecordTim",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "离港艉吃水",
                "prop": "levTailWat",
                hide:true,
            },                    {
                "type": "input",
                "label": "装货开始时间",
                "prop": "loadBegTim",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "装货结束时间",
                "prop": "loadEndTim",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "$column.comments",
                "prop": "mainShipperCod",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "$column.comments",
                "prop": "mainShipperName",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "备注",
                "prop": "markTxt",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "集港开始时间",
                "prop": "massBegTim",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "集港结束时间",
                "prop": "massEndTim",
                "editDisplay":false,
                hide:true,
            },                    {
            
                "type": "input",
                "label": "上油量",
                "prop": "oilTon",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "操作员",
                "prop": "operNam",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "出口货名代码",
                "prop": "outCargoCod",
                hide:true,
            },                    {
                "type": "input",
                "label": "出口货类",
                "prop": "outCargoKnd",
                hide:true,
            },                    {
            
                "type": "input",
                "label": "实际装货",
                "prop": "outFactTon",
                "editDisplay":false,
                hide:true,
            },                    {
            
                "type": "input",
                "label": "出口TEU",
                "prop": "outTeuNum",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "出口航次",
                "prop": "outVoyage",
                hide:true,
            },                    {
                "type": "input",
                "label": "引航否",
                "prop": "pilotId",
                "editDisplay":false,
                hide:true,
            },                    {
                
                "type": "input",
                "label": "备车时间",
                "prop": "prepareTim",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "$column.comments",
                "prop": "prePortCod",
                "editDisplay":false,
                hide:true,
            },                    {
            
                "type": "input",
                "label": "集船号",
                "prop": "pShipNo",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "速遣标志",
                "prop": "quickId",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "记录时间",
                "prop": "recordTim",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "班轮否(1班轮，0普通）",
                "prop": "regularId",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "派船方，0 对派 1我派",
                "prop": "sendShipCod",
                hide:true,
            },                    {
                "type": "input",
                "label": "开户行及账号",
                "prop": "shipperAcn",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "地址、电话",
                "prop": "shipperAdd",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "AGENT_COD",
                "prop": "shipperCod",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "AGENT_NAM",
                "prop": "shipperDoc",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "纳税人识别号",
                "prop": "shipperIrdNo",
                "editDisplay":false,
                hide:true,
            },                    {
            
                "type": "input",
                "label": "船舶种类",
                "prop": "shipKindId",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "航线代码",
                "prop": "shipLineCod",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "航线名称",
                "prop": "shipLineNam",
                "editDisplay":false,
                hide:true,
            },                    {
            
            
                "type": "input",
                "label": "船舶性质",
                "prop": "shipNatId",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "船号",
                "prop": "shipNo",
                "editDisplay":false,    
                hide:true,
            },                    {
                "type": "input",
                "label": "船主代码",
                "prop": "shipOwnerCod",
                hide:true,
            },                    {
                "type": "input",
                "label": "船主",
                "prop": "shipOwnerNam",
                hide:true,
            },                    {
                "type": "input",
                "label": "船型代码",
                "prop": "shipTypCod",
                hide:true,
            },                    {
                "type": "input",
                "label": "船型",
                "prop": "shipTypNam",
                hide:true,
            },                    {
                "type": "input",
                "label": "后缆桩",
                "prop": "stepCable",
                hide:true,
            },                    {
                "type": "input",
                "label": "理货单位代码",
                "prop": "tallyCod",
                hide:true,
            }, {
            
                "type": "input",
                "label": "往港代码",
                "prop": "toPortCod",
                hide:true,
            },                    {
            
               
                "type": "input",
                "label": "抵港记录时间",
                "prop": "toRecordTim",
                "editDisplay":false,
                hide:true,
            },                    {
            
                "type": "input",
                "label": "拖轮否",
                "prop": "tugboatId",
                "editDisplay":false,
                hide:true,
            },                    {
                
                "type": "input",
                "label": "最终修改人",
                "prop": "updateNam",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "最终修改时间",
                "prop": "updateTim",
                "editDisplay":false,
                hide:true,
            },                    {
                "type": "input",
                "label": "疏港开始时间",
                "prop": "vacatBegTim",
                hide:true,
            },                    {
                "type": "input",
                "label": "疏港结束时间",
                "prop": "vacatEndTim",
                "editDisplay":false,
                hide:true,
            },
             //============MJ=MJ=MJ=MJ====查询靠泊时间====================================
           {
            "type": "datetime",
            "label": "靠泊时间",
            "prop": "kbdateList",
            "format": "yyyy-MM-dd HH:mm:ss",
            "value-format": "yyyy-MM-dd HH:mm:ss",
            "addDisplay": false,
            "editDisplay": false,
            "searchSpan": 12,
            "hide": true,
            "slot": true,
            "search": true,
            "searchRange": true,
        }
        
        ]
}
