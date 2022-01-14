export const tableOption = {
  "border": true,   
  "stripe": true,
  "menuAlign": "center",
  "align": "center",
  "searchMenuSpan": 6,
  "menu":false,
  "addBtn":false,
  "refreshBtn":false,
  "columnBtn":false,
  "searchBtn":false,
  "page":false,
  "height":140,
  "column": [
    {
      "label": '货物吞吐量(吨)',
       
        "children": [ {
          "type": "input",
          "label": "内贸进口",
          "prop": "cargoIiWgt"
        },	  {
          "type": "input",
          "label": "内贸出口",
          "prop": "cargoIoWgt"
        }, {
          "type": "input",
          "label": "外贸进口",
          "prop": "cargoOiWgt"
        },	  {
          "type": "input",
          "label": "外贸出口",
          "prop": "cargoOoWgt"
        },{
          "type": "input",
          "label": "合计",
          "prop": "cargoHjWgt"
        }	]
      },	{
        "label": '旅客吞吐量(人)',
         
          "children": [  {
            "type": "input",
            "label": "内贸进口",
            "prop": "manIiNum"
          },	  {
            "type": "input",
            "label": "内贸出口",
            "prop": "manIoNum"
          },	  {
            "type": "input",
            "label": "外贸进口",
            "prop": "manOiNum"
          },	  {
            "type": "input",
            "label": "外贸出口",
            "prop": "manOoNum"
          } 	]
        }   ]
}
