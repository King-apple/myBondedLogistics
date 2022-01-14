export const option7 = {
   
  "menuBtn": false, 
  group:[
    { 
      label: '分货类吞吐量外贸进口',
       
      column: [ {
        "type": "input",
        "label": "铁矿石",
        span: 8,
        "prop": "ksWeightWgt"
      }, {
        "type": "input",
        "label": "在锚船",
        span: 8,
        "prop": "m2ShipNum"
      },	  {
        "type": "input",
        "label": "在锚货重",
        span: 8,
        "prop": "m2ShipWgt"
      }	]
    },{ 
      label: '分货类吞吐量内贸出口',
      
      column: [{
        "type": "input",
        "label": "煤炭",
        span: 8,
        "prop": "oiWeightWgt"
      }, {
        "type": "input",
        "label": "在锚船",
        span: 8,
        "prop": "m3ShipNum"
      },	  {
        "type": "input",
        "label": "在锚货重",
        span: 8,
        "prop": "m3ShipWgt"
      }	]
    },{ 
      label: '分货类吞吐量外贸进口',
      
      column: [{
        "type": "input",
        "label": "煤炭",
        span: 8,
        "prop": "outWeightWgt"
      }, {
        "type": "input",
        "label": "在锚船",
        span: 8,
        "prop": "m4ShipNum"
      },	  {
        "type": "input",
        "label": "在锚货重",
        span: 8,
        "prop": "m4ShipWgt"
      },{
        "type": "input",
        "label": "原油",
        span: 8,
        "prop": "outWeightWgtYy"
      }, {
        "type": "input",
        "label": "在锚船",
        span: 8,
        "prop": "m5ShipNum"
      },	  {
        "type": "input",
        "label": "在锚货重",
        span: 8,
        "prop": "m5ShipWgt"
      }]
    },{ 
      label: '重点物资集疏运',
       
      column: [{
        "type": "input",
        "label": "集港铁矿石",
        "prop": "inTrainWgt"
      }, {
        "type": "input",
        "label": "疏港铁矿石",
        "prop": "outTrainWgt"
      }]
    }
  ]
  
}
