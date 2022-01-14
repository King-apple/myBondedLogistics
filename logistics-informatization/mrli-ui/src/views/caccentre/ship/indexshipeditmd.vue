<!--
  -    Copyright (c) 2018-2025, mrli All rights reserved.
  -
  - Redistribution and use in source and binary forms, with or without
  - modification, are permitted provided that the following conditions are met:
  -
  - Redistributions of source code must retain the above copyright notice,
  - this list of conditions and the following disclaimer.
  - Redistributions in binary form must reproduce the above copyright
  - notice, this list of conditions and the following disclaimer in the
  - documentation and/or other materials provided with the distribution.
  - Neither the name of the pig4cloud.com developer nor the names of its
  - contributors may be used to endorse or promote products derived from
  - this software without specific prior written permission.
  - Author: mrli
  -->
<template>
  <div class="execution">
    <avue-form :option="option" v-model="obj" @submit="handleSubmit">
      <template slot="tallyNamType" slot-scope="{item,value,label}">
        <span>{{item.codeId}}-{{item.codeName}}</span>
      </template>
      <template slot="unitNamType" slot-scope="{item,value,label}">
        <span>{{item.codeId}}</span>
      </template>
      <template slot="prePortNamType" slot-scope="{item,value,label}">
        <span>{{item.codeId}}-{{item.codeName}}</span>
      </template>
      <template slot="fromPortNamType" slot-scope="{item,value,label}">
        <span>{{item.codeId}}-{{item.codeName}}</span>
      </template>
      <template slot="agentNamType" slot-scope="{item,value,label}">
        <span>{{item.codeId}}-{{item.codeName}}</span>
      </template>
      <template slot="toPortNamType" slot-scope="{item,value,label}">
        <span>{{item.codeId}}-{{item.codeName}}</span>
      </template>
      <template slot="inCargoKndType" slot-scope="{item,value,label}">
        <span>{{item.codeId}}-{{item.codeName}}</span>
      </template>
      <template slot="shipLineNamType" slot-scope="{item,value,label}">
        <span>{{item.codeId}}-{{item.codeName}}</span>
      </template>
      <template slot="outCargoKndType" slot-scope="{item,value,label}">
        <span>{{item.codeId}}-{{item.codeName}}</span>
      </template>
      <template slot="berthCodType" slot-scope="{item,value,label}">
        <span>{{item.codeId}}</span>
      </template>
      <template slot="anteCableType" slot-scope="{item,value,label}">
        <span>{{item.codeId}}-{{item.codeName}}</span>
      </template>
      <template slot="stepCableType" slot-scope="{item,value,label}">
        <span>{{item.codeId}}-{{item.codeName}}</span>
      </template>

      <template slot-scope="scope" slot="menuForm">
        <el-button type="primary" icon="el-icon-close" plain @click="closeWindows">取消</el-button>
        
      </template>
      
    </avue-form>
  </div>
</template>

<script>
import request from "@/router/axios";
import "@/styles/common/edit.scss";
import moment from "moment";
export default {
  props: ["objrow"],
  data() {
    return {
      obj: {},
      option: {
        labelWidth: 100,
        emptyBtn: false,
        detail:false,
        submitBtn:false,
        column: [
          {
            type: "input",
            label: "船名",
            span: 8,
            prop: "shipNam",
            placeholder:" ",
            readonly:true,
          },
          {
            label: "集船号",
            prop: "pShipNo",
            placeholder:" ",
            display: false
          },
          {
            type: "input",
            label: "呼号",
            span: 8,
            prop: "callNo",
            placeholder:" ",
            readonly:true,
          },
          {
            label: "理货单位", //C_TALLY_COD
            span: 8,
            prop: "tallyNam",
            placeholder:" ",
            readonly:true,
          },
          {
            type: "input",
            label: "船籍",
            span: 8,
            prop: "ccountryNam",
            placeholder:" ",
            readonly:true,
          },
          {
            label: "单位", //c_unit_nam
            span: 8,
            prop: "unitNam",
            placeholder:" ",
            // type: "select",
            // props: {
            //   label: "codeName",
            //   value: "codeId"
            // },
            // remote: true,
            // typeslot: true,
            // dicUrl: `/basecode/basecod/unit/{{key}}`,
            // change: ({ value, column, item, event }) => {
            //   if (value != null) {
            //     var values = value.split(",");
            //     if(values.length>1){
            //     this.obj.unitNam = values[1];
            //     this.obj.unitCod = values[0];
            //     }else{
            //       this.obj.unitNam = values[0];
            //     }
            //   }
            // },
            readonly:true,
          },
          {
            label: "上一港", //C_PORT
            prop: "prePortNam",
            placeholder:" ",
            span: 8,
            readonly:true,
          },
          {
            type: "input",
            label: "载重吨",
            span: 8,
            prop: "shipCap",
            placeholder:" ",
            readonly:true,
          },
          {
            type: "input",
            label: "船长",
            span: 8,
            prop: "shipLongNum",
            placeholder:" ",
            readonly:true,
          },
          {
            type: "input",
            label: "代理名称", //C_SHIP_AGENT
            span: 8,
            prop: "agentNam",
            placeholder:" ",
            readonly:true,
          },
          {
            type: "input",
            label: "来港", //C_PORT
            span: 8,
            prop: "fromPortNam",
            placeholder:" ",
            readonly:true,
          },
          {
            type: "input",
            label: "派船方",
            span: 8,
            prop: "sendShipC",
            placeholder:" ",
            readonly:true,
          },
          {
            type: "input",
            label: "船类型",
            span: 8,
            prop: "shipTypNam",
            placeholder:" ",
            readonly:true,
          },
          {
            type: "input",
            label: "往港", //C_PORT
            span: 8,
            prop: "toPortNam",
            placeholder:" ",
            readonly:true,
          },
          {
            type: "input",
            label: "内外贸",
            span: 8,
            prop: "tradeName",
            placeholder:" ",
            readonly:true,
          },
          {
            type: "input",
            label: "船主",
            span: 8,
            prop: "shipOwnerNam",
            placeholder:" ",
            readonly:true,
          },
          {
            type: "input",
            label: "进口货种", //c_cargo_nam
            span: 8,
            prop: "inCargoNam",
            placeholder:" ",
            readonly:true,
          },
          {
            type: "input",
            label: "进口载重",
            span: 8,
            prop: "inShipCap",
            dataType:"number",
            placeholder:" ",
            readonly:true,
          },
          {
            type: "input",
            label: "航线", //C_SHIP_LINE
            span: 8,
            prop: "shipLineNam",
            placeholder:" ",
            readonly:true,
          },
          {
            type: "input",
            label: "出口货种", //c_cargo_nam
            span: 8,
            prop: "outCargoNam",
             placeholder:" ",
            readonly:true,
          },
          {
            type: "input",
            label: "出口载重",
            span: 8,
            prop: "outShipCap",
             placeholder:" ",
            dataType:"number",
            readonly:true,
          },
          {
            type: "input",
            label: "进口航次",
             placeholder:" ",
            span: 8,
            prop: "inVoyage",
            readonly:true,
          },
          {
            type: "input",
            label: "预抵时间",
             placeholder:" ",
            span: 8,
            prop: "edtArivTim",
            readonly:true,
          },
          {
            type: "datetime",
            label: "抵锚时间",
             placeholder:" ",
            span: 8,
            prop: "toPortTim",
            readonly:true,
          },
          {
            type: "input",
            label: "出口航次",
             placeholder:" ",
            span: 8,
            prop: "outVoyage",
            readonly:true,
          },
          {
            type: "input",
            label: "抵港艏吃水",
             placeholder:" ",
            span: 8,
            prop: "arrivHeadWat",
            readonly:true,
          },
          {
            type: "input",
            label: "抵港中吃水",
             placeholder:" ",
            span: 8,
            prop: "arrivMidWat",
            readonly:true,
          },
          {
            type: "input",
            label: "抵港艉吃水",
             placeholder:" ",
            span: 8,
            prop: "arrivTailWat",
            readonly:true,
          },
          {
            type: "input",
            label: "离港艏吃水",
             placeholder:" ",
            span: 8,
            prop: "levHeadWat",
            readonly:true,
          },
          {
            type: "input",
            label: "离港中吃水",
             placeholder:" ",
            span: 8,
            prop: "levMidWat",
            readonly:true,
          },
          {
            type: "input",
            label: "离港艉吃水",
             placeholder:" ",
            span: 8,
            prop: "levTailWat",
            readonly:true,
          },
          {
            type: "input",
            label: "泊位", //C_BERTH
            span: 8,
            prop: "berthCod",
            // type: "select",
            //  placeholder:" ",
            // props: {
            //   label: "codeName",
            //   value: "codeId"
            // },
            // remote: true,
            // typeslot: true,
            // dicUrl: `/basecode/basecod/berth/{{key}}`,
            // change: ({ value, column, item, event }) => {
            //   if (value != null) {
            //     var values = value.split(",");
            //     if(values.length>1){
            //     this.obj.berthNam = values[1];
            //     this.obj.berthCodIndex = values[0];
            //     }else{
            //       this.obj.berthCod = values[0];
            //     }
            //   }
            // },
            // disabled:true,
            readonly:true,
          },
          {
            type: "input",
            label: "前缆桩", //C_CABLE_PICKET
            span: 8,
            prop: "anteCable",
             placeholder:" ",
            // type: "select",
            // props: {
            //   label: "codeName",
            //   value: "codeId"
            // },
            // remote: true,
            // typeslot: true,
            // dicUrl: `/basecode/basecod/picket/{{key}}`,
            // tpyeformat(item, label, value) {
            //   return `值:${item[label]}-名:${item[value]}`;
            // },
            // disabled:true,
            readonly:true,
          },
          {
            type: "datetime",
            label: "靠泊时间",
             placeholder:" ",
            span: 8,
            prop: "toBerthTim",
            // "format": "yyyy-MM-dd HH:mm:ss",
            // "value-format": "yyyy-MM-dd HH:mm:ss",
            // "addDisplay": false,
            // "editDisplay": false,
            // blur: () => {
            //   if(this.obj.toBerthTim!=null){

            //     let Ldate=moment().subtract(1, 'year').format('YYYY-MM-DD HH:mm:ss');
            //     console.log(Ldate);

            //     if(moment(new Date())<moment(this.obj.toBerthTim)){
            //       this.obj.toBerthTim=null;
            //     this.$alert('靠泊时间应该小于当前时间！', '错误');
            //     }
            //     if(moment(Ldate)>moment(this.obj.toBerthTim)){
            //       this.obj.toBerthTim=null;
            //     this.$alert('靠泊时间应该大于'+Ldate+'！', '错误');
            //     }
            //   }
            // },
            // disabled:true,
            readonly:true,
          },
          {
            type: "input",
            label: "北纬",
             placeholder:" ",
            span: 8,
            prop: "placeN",
            readonly:true,
          },
          {
            type: "input",
            label: "后缆桩", //C_CABLE_PICKET
            span: 8,
            prop: "stepCable",
             placeholder:" ",
            // type: "select",
            // props: {
            //   label: "codeName",
            //   value: "codeId"
            // },
            // remote: true,
            // typeslot: true,
            // dicUrl: `/basecode/basecod/picket/{{key}}`,
            // tpyeformat(item, label, value) {
            //   return `值:${item[label]}-名:${item[value]}`;
            // },
            // disabled:true,
            readonly:true,
          },
          {
            type: "input",
            label: "吊杆负荷",
             placeholder:" ",
            span: 8,
            prop: "derrickCap",
            readonly:true,
          },
          {
            type: "input",
            label: "东经",
             placeholder:" ",
            span: 8,
            prop: "placeE",
            readonly:true,
          },
          {
            type: "datetime",
            label: "临时抛锚时间",
             placeholder:" ",
            span: 8,
            prop: "toLsportTim",
            readonly:true,
           
          },
          {
            type: "input",
            label: "船长电话",
             placeholder:" ",
            span: 8,
            prop: "captainTel",
            readonly:true,
          },
          {
            type: "input",
            label: "代理公司",
            placeholder:" ",
            span: 8,
            prop: "agentNam02",
            readonly:true,
          },
          {
            type: "input",
            label: "上报经办人",
             placeholder:" ",
            span: 8,
            prop: "agentXingming",
            readonly:true,
          },
          {
            type: "input",
            label: "上报人电话",
             placeholder:" ",
            span: 8,
            prop: "agentPhone",
            readonly:true,
          }
        ]
      }
    };
  },
  mounted: function() {
    this.obj = this.objrow;
  },
  methods: {
    handleSubmit: function(obj,done) {
      obj.unitNam= this.obj.$unitNam;
      obj.berthCod= this.obj.berthCodIndex;
      // console.log("123:"+typeof obj.inShipCap);
      // console.log("1234:"+obj.inShipCap);
      // console.log(String(obj.inShipCap).trim());
      // alert(obj.inShipCap.value);
      if((obj.inCargoNam === null ||obj.inCargoNam==="")&&(obj.outCargoNam === null||obj.outCargoNam==="")){
      this.$alert('进出口货名不能同时为空!', '错误');
            done();
	        	return;
	        }
          if((obj.inShipCap==""||obj.inShipCap == null ||String(obj.inShipCap).trim()=="")&&(obj.outShipCap==""||obj.outShipCap == null||String(obj.outShipCap).trim()=="")){
      this.$alert('进出口载货量不能同时为空！', '错误');
            done();
	        	return;
	        }
          if((obj.inCargoNam !== null &&obj.inCargoNam.trim()!=="")&&(obj.inShipCap===""||obj.inShipCap === null||String(obj.inShipCap).trim()=="")){
      this.$alert('进口载货量不能为空！', '错误');
            done();
	        	return;
	        }
           if((obj.outCargoNam !== null &&obj.outCargoNam.trim()!=="")&&(obj.outShipCap===""||obj.outShipCap === null||String(obj.outShipCap).trim()=="")){
      this.$alert('出口载货量不能为空！', '错误');
            done();
	        	return;
	        }
          if((obj.inCargoNam === null ||obj.inCargoNam.trim()==="")&&(obj.inShipCap !==null&&String(obj.inShipCap).trim()!=="")){
      this.$alert('进口货名不能为空！', '错误');
            done();
	        	return;
	        }
           if((obj.outCargoNam === null ||obj.outCargoNam.trim()==="")&&(obj.outShipCap !== null&&String(obj.outShipCap).trim()!=="")){
      this.$alert('出口货名不能为空！', '错误');
            done();
	        	return;
	        }
           if(obj.inShipCap >99999999.999||obj.inShipCap<0){
      this.$alert('进口重量输入不合法！', '错误');
            done();
	        	return;
	        }
          if(obj.outShipCap >99999999.999||obj.outShipCap<0){
      this.$alert('出口重量输入不合法！', '错误');
            done();
	        	return;
	        }
          if(obj.arrivHeadWat >999.99||obj.arrivHeadWat<0){
      this.$alert('抵港艏吃水输入不合法！', '错误');
            done();
	        	return;
	        }
          if(obj.arrivMidWat >999.99||obj.arrivMidWat<0){
      this.$alert('抵港中吃水输入不合法！', '错误');
            done();
	        	return;
	        }
          if(obj.arrivTailWat >999.99||obj.arrivTailWat<0){
      this.$alert('抵港艉吃水输入不合法！', '错误');
            done();
	        	return;
	        }
           if(obj.levHeadWat >999.99||obj.levHeadWat<0){
      this.$alert('离港艏吃水输入不合法！', '错误');
            done();
	        	return;
	        }
           if(obj.levMidWat >999.99||obj.levMidWat<0){
      this.$alert('离港中吃水输入不合法！', '错误');
            done();
	        	return;
	        }
          if(obj.levTailWat >999.99||obj.levTailWat<0){
      this.$alert('离港艉吃水输入不合法！', '错误');
            done();
	        	return;
	        }
          if(obj.fromPortNam==""||obj.fromPortNam == null ||String(obj.fromPortNam).trim()==""){
      this.$alert('来港不能为空!', '错误');
            done();
	        	return;
	        }
          if(obj.toPortNam==""||obj.toPortNam == null ||String(obj.toPortNam).trim()==""){
      this.$alert('往港不能为空!', '错误');
            done();
	        	return;
	        }
           if((obj.toBerthTim==""||obj.toBerthTim == null ||String(obj.toBerthTim).trim()=="")&&(obj.toPortTim==""||obj.toPortTim == null||String(obj.toPortTim).trim()=="")&&(obj.toLsportTim==""||obj.toLsportTim == null||String(obj.toLsportTim).trim()=="")){
      this.$alert('船抵港，临时抛锚时间或抵锚时间和靠泊时间不能同时为空！', '错误');
            done();
	        	return;
	        }
          if((obj.toBerthTim==""||obj.toBerthTim == null ||String(obj.toBerthTim).trim()=="")&&obj.toPortTim > obj.toBerthTim){
      this.$alert('计划靠泊时间应该大于计划抵锚时间！', '错误');
            done();
	        	return;
	        }
          if((obj.toPortTim!==""&&obj.toPortTim !== null&&String(obj.toPortTim).trim()!=="")&&(obj.toBerthTim==""||obj.toBerthTim == null ||String(obj.toBerthTim).trim()=="")){
            if(obj.berthCod===""||obj.berthCod == null ||String(obj.berthCod).trim()==""){
              console.log("123::berthCod:"+this.obj.berthCod);
              done();
               this.$alert('船舶抵锚泊位不能为空！', '错误');
               return;
            }
            if(obj.berthCod!==""&&obj.berthCod !== null &&String(obj.berthCod).trim()!==""){
               if(String(obj.berthCod).indexOf("M")!==0){
                  done();
                  this.$alert('船舶抵锚泊位必须以M为开头！', '错误');
                  return;
               }
            }
            
	        }
          if((obj.toLsportTim!==""&&obj.toLsportTim !== null&&String(obj.toLsportTim).trim()!=="")&&(obj.toBerthTim==""||obj.toBerthTim == null ||String(obj.toBerthTim).trim()=="")){
            if(obj.berthCod===""||obj.berthCod == null ||String(obj.berthCod).trim()==""){
              done();
               this.$alert('船舶临时下锚泊位不能为空！', '错误');
               return;
            }
            if(obj.berthCod!==""&&obj.berthCod !== null &&String(obj.berthCod).trim()!==""){
               if(String(obj.berthCod).indexOf("M")!==0){
                  done();
                  this.$alert('船舶临时下锚泊位必须以M为开头！', '错误');
                  return;
               }
            }
            
	        }
          if((obj.toBerthTim!==""&&obj.toBerthTim !== null &&String(obj.toBerthTim).trim()=="")&&String(obj.berthCod).indexOf("M")===0){
            console.log("1234:"+obj.toBerthTim);
      this.$alert('船已抵港，泊位不能是锚地！', '错误');
            done();
	        	return;
	        }
           if((obj.toPortTim!==""&&obj.toPortTim !== null&&String(obj.toPortTim).trim()!=="")&&(obj.toBerthTim==""||obj.toBerthTim == null ||String(obj.toBerthTim).trim()=="")&&(obj.berthCod==""||obj.berthCod == null ||String(obj.berthCod).trim()=="")){
      this.$alert('船舶抵港后泊位号不能空，请选择！', '错误');
            done();
	        	return;
	        }

      putObj(obj)
        .then(data => {
          this.$message.success("编辑成功");
          //done();
          //this.getList(this.page);
          this.$emit("getList");
          this.$emit("dictItemVisible");
        })
        .catch(() => {
          // loading();
        });
    },
    
    closeWindows: function() {
      this.$emit("dictItemVisible");
    }
  }

  
};
export function putObj(obj) {
  return request({
    url: "/caccentre/ship",
    method: "put",
    data: obj
  });
}
</script>
<style >
</style>