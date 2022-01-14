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
  <div class="zyworkgather-editCar">
    <el-divider content-position="left">
      <strong>货物详情</strong>
    </el-divider>
    <vxe-form :data="dataCargo">
      <vxe-form-item title="货名" field="cargoNam" size="mini" span="8">
        <vxe-input v-model="dataCargo.cargoNam" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="包装" field="CPkgKindNam" size="mini" span="8">
        <vxe-input v-model="dataCargo.CPkgKindNam" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="总重" field="piecesWgt" size="mini" span="8">
        <vxe-input v-model="dataCargo.piecesWgt" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="总件" field="piecesNum" size="mini" span="8">
        <vxe-input v-model="dataCargo.piecesNum" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="总吨重" field="weightWgt" size="mini" span="8">
        <vxe-input v-model="dataCargo.weightWgt" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="总体积" field="cargoVol" size="mini" span="8">
        <vxe-input v-model="dataCargo.cargoVol" disabled></vxe-input>
      </vxe-form-item>
    </vxe-form>
    <el-divider content-position="left">
      <strong>堆存信息填写</strong>
    </el-divider>
    <vxe-form :data="dataForm" title-width="85">
      <vxe-form-item title="进出港" field="inPortId" size="mini" span="8">
        <vxe-select
          v-model="dataForm.inPortId"
          :options="inPortIdList"
        ></vxe-select>
      </vxe-form-item>
     
      <vxe-form-item title="进出库方式" field="yardWay" size="mini" span="8">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.yardWayname"
          :tableColumns="downTableOption.yardWayColum"
          :urlapi="downTableOption.yardWayurl"
          :returnApi="downTableOption.yardWayreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item> 
       <vxe-form-item title="堆存地点" field="addrNam" size="mini" span="8">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.yardname"
          :tableColumns="downTableOption.yardColum"
          :urlapi="downTableOption.yardurl"
          :returnApi="downTableOption.yardreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
     
       <vxe-form-item title="集疏港方式" field="portWay" size="mini" span="8">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.portWayname"
          :tableColumns="downTableOption.portWayColum"
          :urlapi="downTableOption.portWayurl"
          :returnApi="downTableOption.portWayreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
       <vxe-form-item title="运力安排" field="carArrangMan" size="mini" span="8">
        <vxe-select
          v-model="dataForm.carArrangMan"
          :options="carArrangManList"
        ></vxe-select>
      </vxe-form-item>
      <vxe-form-item title="吨数" field="weightWgt" size="mini" span="8">
        <vxe-input v-model="dataForm.weightWgt" type="number"></vxe-input>
      </vxe-form-item>
        <vxe-form-item title="件数" field="piecesNum" size="mini" span="8">
        <vxe-input v-model="dataForm.piecesNum" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="作业工艺" field="workTechnics" size="mini" span="8">
        <vxe-input v-model="dataForm.workTechnics"></vxe-input>
      </vxe-form-item>
       <vxe-form-item title="体积" field="cargoVol" size="mini" span="8">
        <vxe-input v-model="dataForm.cargoVol" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="费率" field="feeRat" size="mini" span="8">
        <vxe-input v-model="dataForm.feeRat" type="number"></vxe-input>
      </vxe-form-item>
        <vxe-form-item  size="mini" span="8">
         <el-button
        type="warning"
        size="mini"
        @click="getFee"
        >获取费率</el-button
      >
      </vxe-form-item>
    </vxe-form>
    <div align="center" style="">
      <el-button
        type="primary"
        size="mini"
        icon="el-icon-check"
        @click="handleSubmit"
        >确定</el-button
      >
      <el-button size="mini" icon="el-icon-close" plain @click="closeWindows"
        >取消</el-button
      >
    </div>
  </div>
</template>

<script>
import { selectFee } from  "@/api/business/cont";
import request from "@/router/axios";
import "@/styles/common/edit.scss";
import { mapGetters } from "vuex";
import moment from "moment";
import Vue from "vue";
import VXETable from "vxe-table";
import hljs from "highlight.js";
import { Loading, Message } from "element-ui";
import EditDown from "../../baseUtile/EditDown";
export default {
  props: ["datanow"],
  components: { EditDown },
  data() {
    return {
      dataForm: {
        ioportWay: null,
        portWay: null,
        ioyardWay: null,
        yardWay: null,
        addrNam:null,
        yardNo:null,
        yardtype:null,
        feeRat:null,
        pknum:null,
      },
      dataCargo: {},
      inPortIdList: [
        { label: "出港", value: 0 },
        { label: "进港", value: 1 },
      ],
      carArrangManList: [
        { label: "甲方", value: "甲方" },
        { label: "乙方", value: "乙方" },
      ],
      downTableOption: {
        portWayname: "portWay",
        portWayColum: [
          { field: "CODE", title: "代码" },
          { field: "MEANING", title: "名称" },
        ],
        portWayreturnApi: [
          { field: "CODE", returnName: "ioportWay" },
          { field: "MEANING", returnName: "portWay" },
        ],
        portWayurl: "/basecode/basecod/getInOutWay",
        yardWayname: "yardWay",
        yardWayColum: [
          { field: "CODE", title: "代码" },
          { field: "MEANING", title: "名称" },
        ],
        yardWayreturnApi: [
          { field: "CODE", returnName: "ioyardWay" },
          { field: "MEANING", returnName: "yardWay" },
        ],
        yardWayurl: "/basecode/basecod/getInOutWay",
        //库场
        yardname: "addrNam",
        yardColum: [
          { field: "YARD_NO", title: "库场代码" },
          { field: "YARD_NAM", title: "库场名称" },
          { field: "YARD_TYP_COD", title: "库场类型" },
        ],
        yardreturnApi: [
          { field: "YARD_NAM", returnName: "addrNam" },
          { field: "YARD_NO", returnName: "yardNo" },
          { field: "YARD_TYP_COD", returnName: "yardtype" },
        ],
        yardurl: "/basecode/basecod/getYard",
        feeLow:null,
      },
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  created() {},
  mounted: function () {
    this.dataCargo = this.datanow;
    this.dataForm.pknum=this.datanow.pknum;
  },
  methods: {
    handleSubmit() {
      if(this.dataForm.feeRat==null){
          this.$message.error("请选择代入费率");
          return 
      }
      if(this.dataForm.feeRat<this.feeLow){
            this.$message.error("费率不得小于最低费率，最低费率："+this.feeLow);
          return 
      }
      if(this.dataForm.weightWgt==null){
          this.$message.error("请填写吨数");
          return 
      }
      this.$emit("saveAssignTable",this.dataForm);
      this.$emit("dictItemVisible");
    },
    getFee(){
        if(this.dataForm.ioportWay==null){
             this.$message.error("请选择集疏港方式");
            return ;
        }
        if(this.dataForm.yardtype==null){
            this.$message.error("请选择库场");
            return;
        }
        var obj={
        inoutId:this.dataCargo.inoutId,
        tradeId:this.dataCargo.tradeId,
        cargoNamCod:this.dataCargo.cargoNamCod,
        pkgKindCod:this.dataCargo.pkgKindCod,
        firstCod:this.dataCargo.shipperCodPrice,
        yardtype:this.dataForm.yardtype,
        ioportway:this.dataForm.ioportWay}
        selectFee(obj)
        .then((response) => {
            console.log(response.data.data.FEE_RAT)
          this.dataForm.feeRat=response.data.data.FEE_RAT;
          this.feeLow=response.data.data.MIN_FEE_RAT;
        })
        .catch(() => {});
    },
    closeWindows() {
      this.$emit("dictItemVisible");
    },
  },
};
</script>
<style >
</style>