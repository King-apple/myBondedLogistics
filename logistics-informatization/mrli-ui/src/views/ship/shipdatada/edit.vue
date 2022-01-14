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
    <vxe-form
      :data="dataForm"
      title-align="right"
      title-width="100"
      size="mini"
    >
      <vxe-form-item title="呼号：" field="callNo" size="mini" span="6">
        <vxe-input v-model="dataForm.callNo" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="IMO_NO：" field="imoNo" size="mini" span="6">
        <vxe-input v-model="dataForm.imoNo"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="标志TEU：" field="teuNum" size="mini" span="6">
        <vxe-input v-model="dataForm.teuNum"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="精品等级：" field="credit" size="mini" span="6">
        <vxe-input v-model="dataForm.credit"></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="中文船名：" field="cshipNam" size="mini" span="6">
        <vxe-input v-model="dataForm.cshipNam" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="船公司：" field="shipCorp" size="mini" span="6">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.shipCorp"
          :tableColumns="downTableOption.shipCorpColum"
          :urlapi="downTableOption.shipCorpurl"
          :returnApi="downTableOption.shipCorpreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="高度：" field="heightNum" size="mini" span="6">
        <vxe-input v-model="dataForm.heightNum"></vxe-input>
      </vxe-form-item>
      <vxe-form-item
        title="满载艏吃水："
        field="fullHeadWat"
        size="mini"
        span="6"
      >
        <vxe-input v-model="dataForm.fullHeadWat"></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="英文船名：" field="eshipNam" size="mini" span="6">
        <vxe-input v-model="dataForm.eshipNam" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="建造日期：" field="madeDte" size="mini" span="6">
        <vxe-input
          v-model="dataForm.madeDte"
          valueFormat="yyyy-MM-dd"
          type="date"
        ></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="速度：" field="speedNum" size="mini" span="6">
        <vxe-input v-model="dataForm.speedNum"></vxe-input>
      </vxe-form-item>
      <vxe-form-item
        title="满载艉吃水："
        field="fullTailWat"
        size="mini"
        span="6"
      >
        <vxe-input v-model="dataForm.fullTailWat"></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="船籍：" field="ccountryNam" size="mini" span="6">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.ccountryNam"
          :tableColumns="downTableOption.ccountryNamColum"
          :urlapi="downTableOption.ccountryNamurl"
          :returnApi="downTableOption.ccountryNamreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item
        title="船籍港："
        field="portRegisterNam"
        size="mini"
        span="6"
      >
        <EditDown
          :objs="dataForm"
          :property="downTableOption.portRegisterNam"
          :tableColumns="downTableOption.portRegisterNamColum"
          :urlapi="downTableOption.portRegisterNamurl"
          :returnApi="downTableOption.portRegisterNamreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="型深：" field="typeDeepNum" size="mini" span="6">
        <vxe-input v-model="dataForm.typeDeepNum"></vxe-input>
      </vxe-form-item>
      <vxe-form-item
        title="空载艏吃水："
        field="empHeadWat"
        size="mini"
        span="6"
      >
        <vxe-input v-model="dataForm.empHeadWat"></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="船主：" field="shipOwnerNam" size="mini" span="6">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.shipOwnerNam"
          :tableColumns="downTableOption.shipOwnerNamColum"
          :urlapi="downTableOption.shipOwnerNamurl"
          :returnApi="downTableOption.shipOwnerNamreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item
        title="舱口盖形式："
        field="hatLidKnd"
        size="mini"
        span="6"
      >
        <vxe-input v-model="dataForm.hatLidKnd"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="公分浸吨：" field="cmSteepWgt" size="mini" span="6">
        <vxe-input v-model="dataForm.cmSteepWgt"></vxe-input>
      </vxe-form-item>
      <vxe-form-item
        title="空载艉吃水："
        field="empTailWat"
        size="mini"
        span="6"
      >
        <vxe-input v-model="dataForm.empTailWat"></vxe-input>
      </vxe-form-item>

      <vxe-form-item
        title="船主电话："
        field="shipOwnerTele"
        size="mini"
        span="6"
      >
        <vxe-input v-model="dataForm.shipOwnerTele"></vxe-input>
      </vxe-form-item>
      <vxe-form-item
        title="主辅机性能："
        field="particularsEngines"
        size="mini"
        span="6"
      >
        <vxe-input v-model="dataForm.particularsEngines"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="舱容：" field="cabinWgt" size="mini" span="6">
        <vxe-input v-model="dataForm.cabinWgt"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="吊杆付数：" field="derrickNum" size="mini" span="6">
        <vxe-input v-model="dataForm.derrickNum"></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="船级：" field="classNam" size="mini" span="6">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.classNam"
          :tableColumns="downTableOption.classNamColum"
          :urlapi="downTableOption.classNamurl"
          :returnApi="downTableOption.classNamreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="主机型号：" field="mEType" size="mini" span="6">
        <vxe-input v-model="dataForm.mEType"></vxe-input>
      </vxe-form-item>
      <vxe-form-item
        title="散货舱容："
        field="grainCabinWgt"
        size="mini"
        span="6"
      >
        <vxe-input v-model="dataForm.grainCabinWgt"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="吊杆负荷：" field="derrickCap" size="mini" span="6">
        <vxe-input v-model="dataForm.derrickCap"></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="船型：" field="shipTypNam" size="mini" span="6">
         <EditDown
          :objs="dataForm"
          :property="downTableOption.shipTypNam"
          :tableColumns="downTableOption.shipTypNamColum"
          :urlapi="downTableOption.shipTypNamurl"
          :returnApi="downTableOption.shipTypNamreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="主机数量：" field="mENum" size="mini" span="6">
        <vxe-input v-model="dataForm.mENum"></vxe-input>
      </vxe-form-item>
      <vxe-form-item
        title="包货舱容："
        field="balcCabinWgt"
        size="mini"
        span="6"
      >
        <vxe-input v-model="dataForm.balcCabinWgt"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="吊杆型式：" field="derrickKnd" size="mini" span="6">
        <vxe-input v-model="dataForm.derrickKnd"></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="总吨：" field="totalWgt" size="mini" span="6">
        <vxe-input v-model="dataForm.totalWgt"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="辅机型号：" field="pMType" size="mini" span="6">
        <vxe-input v-model="dataForm.pMType"></vxe-input>
      </vxe-form-item>
      <vxe-form-item
        title="液货舱容："
        field="liquidCabinWgt"
        size="mini"
        span="6"
      >
        <vxe-input v-model="dataForm.liquidCabinWgt"></vxe-input>
      </vxe-form-item>
      <vxe-form-item
        title="淡水容量："
        field="freshWaterWgt"
        size="mini"
        span="6"
      >
        <vxe-input v-model="dataForm.freshWaterWgt"></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="净吨：" field="netWgt" size="mini" span="6">
        <vxe-input v-model="dataForm.netWgt"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="电话号码：" field="corpTele" size="mini" span="6">
        <vxe-input v-model="dataForm.corpTele"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="舱口数：" field="hatchNum" size="mini" span="6">
        <vxe-input v-model="dataForm.hatchNum"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="压载：" field="ballast" size="mini" span="6">
        <vxe-input v-model="dataForm.ballast"></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="载重吨：" field="shipCap" size="mini" span="6">
        <vxe-input v-model="dataForm.shipCap"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="辅机数量：" field="pMNum" size="mini" span="6">
        <vxe-input v-model="dataForm.pMNum"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="舱层数：" field="cabinCellNum" size="mini" span="6">
        <vxe-input v-model="dataForm.cabinCellNum"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="重油：" field="fO" size="mini" span="6">
        <vxe-input v-model="dataForm.fO"></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="船长：" field="shipLongNum" size="mini" span="6">
        <vxe-input v-model="dataForm.shipLongNum"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="建造地：" field="buildingland" size="mini" span="6">
        <vxe-input v-model="dataForm.buildingland"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="舱尺寸：" field="cabinSizeStr" size="mini" span="6">
        <vxe-input v-model="dataForm.cabinSizeStr"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="轻油：" field="dO" size="mini" span="6">
        <vxe-input v-model="dataForm.dO"></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="船宽：" field="shipWideNum" size="mini" span="6">
        <vxe-input v-model="dataForm.shipWideNum"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="制造商：" field="maker" size="mini" span="6">
        <vxe-input v-model="dataForm.maker"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="最高航速：" field="fullSpeed" size="mini" span="6">
        <vxe-input v-model="dataForm.fullSpeed"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="油耗情况：" field="at" size="mini" span="6">
        <vxe-input v-model="dataForm.at"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="船东：" field="shipAgentNam" size="mini" span="12">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.shipAgentNam"
          :tableColumns="downTableOption.shipAgentNamColum"
          :urlapi="downTableOption.shipAgentNamurl"
          :returnApi="downTableOption.shipAgentNamreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
    </vxe-form>
    <div align="center">
      <vxe-button
        size="mini"
        status="primary"
        icon="el-icon-close"
        @click="saveOne()"
        >保存</vxe-button
      >
      <vxe-button size="mini" icon="el-icon-close" plain @click="closeWindows"
        >取消</vxe-button
      >
    </div>
  </div>
</template>

<script>
import { saveObj } from "@/api/ship/shipdata";
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
        shipAgentNam: null,
        shipAgentCod: null,
        countryCod: null,
        ccountryNam: null,
        shipCorp: null,
        shipCorpCod: null,
        portRegisterCod: null,
        portRegisterNam: null,
        shipOwnerCod: null,
        shipOwnerNam: null,
        shipTypCod:null,
        shipTypNam:null,
        classCod:null,
        classNam:null,
      },
      downTableOption: {
        //货主
        shipAgentNam: "shipAgentNam",
        shipAgentNamColum: [
          { field: "agentNam", title: "船东名称" },
          { field: "agentCod", title: "船东代码" }
        ],
        shipAgentNamreturnApi: [
          { field: "agentNam", returnName: "shipAgentNam" },
          { field: "agentCod", returnName: "shipAgentCod" }
        ],
        shipAgentNamurl: "/ship/shipagent/page",
        //船籍
        ccountryNam: "ccountryNam",
        ccountryNamColum: [
          { field: "COUNTRY_COD", title: "代码" },
          { field: "C_COUNTRY_NAM", title: "名称" }
        ],
        ccountryNamreturnApi: [
          { field: "COUNTRY_COD", returnName: "countryCod" },
          { field: "C_COUNTRY_NAM", returnName: "ccountryNam" }
        ],
        ccountryNamurl: "/basecode/basecod/country",
        //船公司
        shipCorp: "shipCorp",
        shipCorpColum: [
          { field: "SHIP_CORP_COD", title: "代码" },
          { field: "C_SHIP_CORP_NAM", title: "名称" }
        ],
        shipCorpreturnApi: [
          { field: "SHIP_CORP_COD", returnName: "shipCorpCod" },
          { field: "C_SHIP_CORP_NAM", returnName: "shipCorp" }
        ],
        shipCorpurl: "/basecode/basecod/shipCorp",
        //船籍港
        portRegisterNam: "portRegisterNam",
        portRegisterNamColum: [
          { field: "TRANS_PORT_COD", title: "代码" },
          { field: "C_PORT_NAM", title: "名称" }
        ],
        portRegisterNamreturnApi: [
          { field: "TRANS_PORT_COD", returnName: "portRegisterCod" },
          { field: "C_PORT_NAM", returnName: "portRegisterNam" }
        ],
        portRegisterNamurl: "/basecode/basecod/portForDown",
        //船主
        shipOwnerNam: "shipOwnerNam",
        shipOwnerNamColum: [
          { field: "SHIP_OWNER_COD", title: "代码" },
          { field: "SHIP_OWNER_NAM", title: "名称" }
        ],
        shipOwnerNamreturnApi: [
          { field: "SHIP_OWNER_COD", returnName: "shipOwnerCod" },
          { field: "SHIP_OWNER_NAM", returnName: "shipOwnerNam" }
        ],
        shipOwnerNamurl: "/basecode/basecod/shipOwner",
        //船级别
        classNam:"classNam",
         classNamColum: [
          { field: "CLASS_COD", title: "代码" },
          { field: "CLASS_NAM", title: "名称" }
        ],
        classNamreturnApi: [
          { field: "CLASS_COD", returnName: "classCod" },
          { field: "CLASS_NAM", returnName: "classNam" }
        ],
        classNamurl: "/basecode/basecod/shipClassCod",
        //船型
        shipTypNam:"shipTypNam",
         shipTypNamColum: [
          { field: "SHIP_TYP_COD", title: "代码" },
          { field: "SHIP_TYP_NAM", title: "名称" }
        ],
        shipTypNamreturnApi: [
          { field: "SHIP_TYP_COD", returnName: "shipTypCod" },
          { field: "SHIP_TYP_NAM", returnName: "shipTypNam" }
        ],
        shipTypNamurl: "/basecode/basecod/shipType",
      }
    };
  },
  computed: {},
  created() {
    this.dataForm = this.datanow;
  },
  mounted: function() {},
  methods: {
    closeWindows() {
      this.$emit("dictItemVisible");
    },
    saveOne() {
      let loadingInstance = Loading.service({
        fullscreen: true,
        text: "正在保存………"
      });
      saveObj(this.dataForm)
        .then(data => {
          this.$message.success("保存成功");
          loadingInstance.close();
          this.$emit("selectListplan");
          this.$emit("dictItemVisible");
        })
        .catch(() => {
          loadingInstance.close();
        });
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