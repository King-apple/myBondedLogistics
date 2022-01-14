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
<div class="shipdatada">
    <vxe-form :data="obj">
      <vxe-form-item title="中文船名" field="cshipNam" size="mini" span="6">
        <vxe-input v-model="obj.cshipNam" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="船呼号" field="callNo" size="mini" span="6">
        <vxe-input v-model="obj.callNo" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="IMO_NO" field="imoNo" size="mini" span="6">
        <vxe-input v-model="obj.imoNo" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="标准TEU" field="teuNum" size="mini" span="6">
        <vxe-input v-model="obj.teuNum" disabled></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="英文船名" field="eshipNam" size="mini" span="6">
        <vxe-input v-model="obj.eshipNam" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="船公司" field="shipCorp" size="mini" span="6">
        <vxe-input v-model="obj.shipCorp" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="船主" field="shipOwnerNam" size="mini" span="6">
        <vxe-input v-model="obj.shipOwnerNam" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="船主电话" field="shipOwnerTele" size="mini" span="6">
        <vxe-input v-model="obj.shipOwnerTele" disabled></vxe-input>
      </vxe-form-item>

       <vxe-form-item title="船籍" field="ccountryNam" size="mini" span="6">
        <vxe-input v-model="obj.ccountryNam" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="船籍港" field="portRegisterNam" size="mini" span="6">
        <vxe-input v-model="obj.portRegisterNam" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="联系人" field="shipLinkNam" size="mini" span="6">
        <vxe-input v-model="obj.shipLinkNam" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="电话" field="corpTele" size="mini" span="6">
        <vxe-input v-model="obj.corpTele" disabled></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="船级" field="classNam" size="mini" span="6">
        <vxe-input v-model="obj.classNam" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="型深" field="typeDeepNum" size="mini" span="6">
        <vxe-input v-model="obj.typeDeepNum" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="高度" field="heightNum" size="mini" span="6">
        <vxe-input v-model="obj.heightNum" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="满载艏吃水" field="fullHeadWat" size="mini" span="6">
        <vxe-input v-model="obj.fullHeadWat" disabled></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="船型" field="shipTypNam" size="mini" span="6">
        <vxe-input v-model="obj.shipTypNam" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="公分浸吨" field="cmSteepWgt" size="mini" span="6">
        <vxe-input v-model="obj.cmSteepWgt" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="速度" field="speedNum" size="mini" span="6">
        <vxe-input v-model="obj.speedNum" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="满载艉吃水" field="fullTailWat" size="mini" span="6">
        <vxe-input v-model="obj.fullTailWat" disabled></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="总吨" field="totalWgt" size="mini" span="6">
        <vxe-input v-model="obj.totalWgt" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="舱容" field="cabinWgt" size="mini" span="6">
        <vxe-input v-model="obj.cabinWgt" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="吊杆付数" field="derrickNum" size="mini" span="6">
        <vxe-input v-model="obj.derrickNum" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="空载艏吃水" field="empHeadWat" size="mini" span="6">
        <vxe-input v-model="obj.empHeadWat" disabled></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="净吨" field="netWgt" size="mini" span="6">
        <vxe-input v-model="obj.netWgt" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="散货仓容" field="grainCabinWgt" size="mini" span="6">
        <vxe-input v-model="obj.grainCabinWgt" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="吊杆负荷" field="derrickCap" size="mini" span="6">
        <vxe-input v-model="obj.derrickCap" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="空载艉吃水" field="empTailWat" size="mini" span="6">
        <vxe-input v-model="obj.empTailWat" disabled></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="载重吨" field="shipCap" size="mini" span="6">
        <vxe-input v-model="obj.shipCap" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="包货仓容" field="balcCabinWgt" size="mini" span="6">
        <vxe-input v-model="obj.balcCabinWgt" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="吊杆型式" field="derrickKnd" size="mini" span="6">
        <vxe-input v-model="obj.derrickKnd" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="舱口盖形式" field="hatLidKnd" size="mini" span="6">
        <vxe-input v-model="obj.hatLidKnd" disabled></vxe-input>
      </vxe-form-item>

       <vxe-form-item title="船长" field="shipLongNum" size="mini" span="6">
        <vxe-input v-model="obj.shipLongNum" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="液货仓容" field="liquidCabinWgt" size="mini" span="6">
        <vxe-input v-model="obj.liquidCabinWgt" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="舱口数" field="hatchNum" size="mini" span="6">
        <vxe-input v-model="obj.hatchNum" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="淡水容量" field="freshWaterWgt" size="mini" span="6">
        <vxe-input v-model="obj.freshWaterWgt" disabled></vxe-input>
      </vxe-form-item>

       <vxe-form-item title="船宽" field="shipWideNum" size="mini" span="6">
        <vxe-input v-model="obj.shipWideNum" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="压载" field="ballast" size="mini" span="6">
        <vxe-input v-model="obj.ballast" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="舱层数" field="cabinCellNum" size="mini" span="6">
        <vxe-input v-model="obj.cabinCellNum" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="辅机型号" field="pMType" size="mini" span="6">
        <vxe-input v-model="obj.pMType" disabled></vxe-input>
      </vxe-form-item>

       <vxe-form-item title="建造日期" field="madeDte" size="mini" span="6">
        <vxe-input v-model="obj.madeDte" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="重油" field="fO" size="mini" span="6">
        <vxe-input v-model="obj.fO" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="在油耗情况下" field="at" size="mini" span="6">
        <vxe-input v-model="obj.at" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="辅机数量" field="pMNum" size="mini" span="6">
        <vxe-input v-model="obj.pMNum" disabled></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="建造地" field="buildingland" size="mini" span="6">
        <vxe-input v-model="obj.buildingland" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="轻油" field="dO" size="mini" span="6">
        <vxe-input v-model="obj.dO" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="主机型号" field="mEType" size="mini" span="6">
        <vxe-input v-model="obj.mEType" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="主机数量" field="mENum" size="mini" span="6">
        <vxe-input v-model="obj.mENum" disabled></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="" field="maker" size="mini" span="6" >
       <vxe-input  v-model="obj.maker" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="最高航速" field="fullSpeed" size="mini" span="6">
        <vxe-input v-model="obj.fullSpeed" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="主辅机性能" field="particularsEngines" size="mini" span="6">
        <vxe-input v-model="obj.particularsEngines" disabled></vxe-input>
      </vxe-form-item>
     <!--<vxe-button icon="el-icon-warning" :size="mini" :type="primary" @click.stop="handleApt(row)">舱口及吊杆情况</vxe-button>-->

    </vxe-form>
   
        <div align="center">
      <el-button
        v-if="obj.status==0||obj.status==null"
        type="primary"
        size="size"
        icon="el-icon-check"
        @click="handleSubmit"
        :disabled="buttonload"
      >提交审核</el-button>
      <el-button size="size" icon="el-icon-close" plain @click="closeWindows">取消</el-button>
    </div>
  </div>
  </div>
<!--
  <div class="execution">
    <avue-form :option="option" v-model="obj" @submit="handleSubmit">
      <template slot="tallyNamType" slot-scope="{item,value,label}">
        <span>{{item.codeId}}-{{item.codeName}}</span>
      </template>
      

      <template slot-scope="scope" slot="menuForm">
        <el-button type="primary" icon="el-icon-close" plain @click="closeWindows">取消</el-button>
        
      </template>
      
    </avue-form>
  </div>-->
</template>

<script>
import request from "@/router/axios";
import "@/styles/common/edit.scss";
import moment from "moment";
import Vue from "vue";
import VXETable from "vxe-table";
import hljs from "highlight.js";
import { Loading } from "element-ui";
export default {
  props: ["objrow"],
  data() {
    return {
      obj: {},
      option: {
        labelWidth: 100,
        emptyBtn: false,
        detail:false,
        column: [
          {
            type: "input",
            label: "中文船名",
            span: 8,
            prop: "cshipNam",
            placeholder:" ",
            readonly:true,
          },
          {
            label: "英文船名",
            prop: "eshipNam",
            placeholder:" ",
            readonly:true,
          },
          {
            type: "input",
            label: "船呼号",
            span: 8,
            prop: "callNo",
            placeholder:" ",
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
      this.buttonload = true;
      
       console.log("handleSubmit:"+this.obj.callNo);
      
      //      if((obj.toPortTim!==""&&obj.toPortTim !== null&&String(obj.toPortTim).trim()!=="")&&(obj.toBerthTim==""||obj.toBerthTim == null ||String(obj.toBerthTim).trim()=="")&&(obj.berthCod==""||obj.berthCod == null ||String(obj.berthCod).trim()=="")){
      // this.$alert('船舶抵港后泊位号不能空，请选择！', '错误');
      //       done();
	    //     	return;
	    //     }

      upObj(this.obj.callNo)
        .then(data => {
          this.$message.success("审核成功");
          this.buttonload = false;
          //done();
          //this.getList(this.page);
          this.$emit("getList");
          this.$emit("dictItemVisible");
        })
        .catch(() => {
          this.buttonload = false;
          // loading();
        });
    },
    
    closeWindows: function() {
      this.$emit("dictItemVisible");
    }
  }

  
};
export function upObj(id) {
  return request({
    url: '/ship/shipdatada/upById/' + id,
    method: 'get'
  })
}
</script>
<style >
.shipdatada .vxe-form .vxe-form--item {
    padding: .1em .8em .1em 0;
}
.shipdatada .vxe-input--inner{
    background-color: #ffffff;
}
.feemain .avue-form__row {
  height: 35px;
}
.feemain .vxe-toolbar {
  height: 25px;
}
</style>