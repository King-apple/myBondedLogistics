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
  <div class="yardmiyardheadedit">
    <vxe-form  :data="dataForm" title-align="right" title-width="100" size="mini">
      <vxe-form-item title="呼号：" field="callNo" size="mini" span="6">
        <vxe-input v-model="dataForm.callNo" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="IMO_NO：" field="imoNo" size="mini" span="6">
        <vxe-input v-model="dataForm.imoNo" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="标志TEU：" field="teuNum" size="mini" span="6">
        <vxe-input v-model="dataForm.teuNum" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="精品等级：" field="credit" size="mini" span="6">
        <vxe-input v-model="dataForm.credit" disabled></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="中文船名：" field="cshipNam" size="mini" span="6">
        <vxe-input v-model="dataForm.cshipNam" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="船公司：" field="shipCorp" size="mini" span="6">
        <vxe-input v-model="dataForm.shipCorp" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="高度：" field="heightNum" size="mini" span="6">
        <vxe-input v-model="dataForm.heightNum" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="满载艏吃水：" field="fullHeadWat" size="mini" span="6">
        <vxe-input v-model="dataForm.fullHeadWat" disabled></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="英文船名：" field="eshipNam" size="mini" span="6">
        <vxe-input v-model="dataForm.eshipNam" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="电话号码：" field="corpTele" size="mini" span="6">
        <vxe-input v-model="dataForm.corpTele" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="速度：" field="speedNum" size="mini" span="6">
        <vxe-input v-model="dataForm.teuNum" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="满载艉吃水：" field="fullTailWat" size="mini" span="6">
        <vxe-input v-model="dataForm.fullTailWat" disabled></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="船籍：" field="cCountryNam" size="mini" span="6">
        <vxe-input v-model="dataForm.cCountryNam" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="船籍港：" field="portRegisterNam" size="mini" span="6">
        <vxe-input v-model="dataForm.portRegisterNam" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="型深：" field="typeDeepNum" size="mini" span="6">
        <vxe-input v-model="dataForm.typeDeepNum" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="空载艏吃水：" field="empHeadWat" size="mini" span="6">
        <vxe-input v-model="dataForm.empHeadWat" disabled></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="船主：" field="shipOwnerNam" size="mini" span="6">
        <vxe-input v-model="dataForm.shipOwnerNam" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="舱口盖形式：" field="hatLidKnd" size="mini" span="6">
        <vxe-input v-model="dataForm.hatLidKnd" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="公分浸吨：" field="cmSteepWgt" size="mini" span="6">
        <vxe-input v-model="dataForm.cmSteepWgt" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="空载艉吃水：" field="empTailWat" size="mini" span="6">
        <vxe-input v-model="dataForm.empTailWat" disabled></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="船主电话：" field="shipOwnerTele" size="mini" span="6">
        <vxe-input v-model="dataForm.shipOwnerTele" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="主辅机性能：" field="particularsEngines" size="mini" span="6">
        <vxe-input v-model="dataForm.particularsEngines" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="舱容：" field="cabinWgt" size="mini" span="6">
        <vxe-input v-model="dataForm.cabinWgt" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="吊杆付数：" field="derrickNum" size="mini" span="6">
        <vxe-input v-model="dataForm.derrickNum" disabled></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="船级：" field="classNam" size="mini" span="6">
        <vxe-input v-model="dataForm.classNam" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="主机型号：" field="mEType" size="mini" span="6">
        <vxe-input v-model="dataForm.mEType" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="散货舱容：" field="grainCabinWgt" size="mini" span="6">
        <vxe-input v-model="dataForm.grainCabinWgt" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="吊杆负荷：" field="derrickCap" size="mini" span="6">
        <vxe-input v-model="dataForm.derrickCap" disabled></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="船型：" field="shipTypNam" size="mini" span="6">
        <vxe-input v-model="dataForm.shipTypNam" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="主机数量：" field="mENum" size="mini" span="6">
        <vxe-input v-model="dataForm.mENum" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="包货舱容：" field="balcCabinWgt" size="mini" span="6">
        <vxe-input v-model="dataForm.balcCabinWgt" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="吊杆型式：" field="derrickKnd" size="mini" span="6">
        <vxe-input v-model="dataForm.derrickKnd" disabled></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="总吨：" field="totalWgt" size="mini" span="6">
        <vxe-input v-model="dataForm.totalWgt" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="辅机型号：" field="pMType" size="mini" span="6">
        <vxe-input v-model="dataForm.pMType" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="液货舱容：" field="liquidCabinWgt" size="mini" span="6">
        <vxe-input v-model="dataForm.liquidCabinWgt" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="淡水容量：" field="freshWaterWgt" size="mini" span="6">
        <vxe-input v-model="dataForm.freshWaterWgt" disabled></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="净吨：" field="netWgt" size="mini" span="6">
        <vxe-input v-model="dataForm.netWgt" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="辅机数量：" field="pMNum" size="mini" span="6">
        <vxe-input v-model="dataForm.pMNum" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="舱口数：" field="hatchNum" size="mini" span="6">
        <vxe-input v-model="dataForm.hatchNum" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="压载：" field="ballast" size="mini" span="6">
        <vxe-input v-model="dataForm.ballast" disabled></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="载重吨：" field="shipCap" size="mini" span="6">
        <vxe-input v-model="dataForm.shipCap" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="建造日期：" field="madeDte" size="mini" span="6">
        <vxe-input v-model="dataForm.madeDte" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="舱层数：" field="cabinCellNum" size="mini" span="6">
        <vxe-input v-model="dataForm.cabinCellNum" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="重油：" field="fO" size="mini" span="6">
        <vxe-input v-model="dataForm.fO" disabled></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="船长：" field="shipLongNum" size="mini" span="6">
        <vxe-input v-model="dataForm.shipLongNum" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="建造地：" field="buildingland" size="mini" span="6">
        <vxe-input v-model="dataForm.buildingland" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="舱尺寸：" field="cabinSizeStr" size="mini" span="6">
        <vxe-input v-model="dataForm.cabinSizeStr" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="轻油：" field="dO" size="mini" span="6">
        <vxe-input v-model="dataForm.dO" disabled></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="船宽：" field="shipWideNum" size="mini" span="6">
        <vxe-input v-model="dataForm.shipWideNum" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="制造商：" field="maker" size="mini" span="6">
        <vxe-input v-model="dataForm.maker" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="最高航速：" field="fullSpeed" size="mini" span="6">
        <vxe-input v-model="dataForm.fullSpeed" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="油耗情况：" field="at" size="mini" span="6">
        <vxe-input v-model="dataForm.at" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="船东：" field="shipAgentNam" size="mini" span="12">
        <vxe-input v-model="dataForm.shipAgentNam" disabled></vxe-input>
      </vxe-form-item>
    </vxe-form>
    <div align="center">      
      <el-button size="mini" icon="el-icon-close" plain @click="closeWindows">取消</el-button>
    </div>
  </div>
</template>

<script>
import { saveHead } from "@/api/yard/mioyardhead";
import request from "@/router/axios";
import "@/styles/common/edit.scss";
import { mapGetters } from "vuex";
import moment from "moment";
import { mapState } from "vuex";
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
         
      },
       
    };
  },
  computed: {
     
  },
  created() {
   
      this.dataForm=this.datanow;
     
  },
  mounted: function() {},
  methods: {
    
    closeWindows() {
      this.$emit("dictItemVisible");
    }
  }
};
</script>
<style lang="scss" scoped>
.yardmiyardheadedit {
  .el-divider--horizontal {
    margin: 5px 0;
  }
}
</style>