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
    <vxe-form :data="dataForm" size="mini">
      <vxe-form-item title="单号" field="informNo" size="mini" span="4">
        <vxe-input v-model="dataForm.informNo" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="合同号" field="contNo" size="mini" span="4">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.contname"
          :tableColumns="downTableOption.contColum"
          :urlapi="downTableOption.conturl"
          :returnApi="downTableOption.contreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="合同类型" field="contType" size="mini" span="4">
         <vxe-select v-model="dataForm.contType" placeholder="请选择合同类型" clearable transfer>
            <vxe-option value="01" label="正常"></vxe-option>
            <vxe-option value="02" label="大件"></vxe-option>
          </vxe-select>
      </vxe-form-item>
      <vxe-form-item title="作业委托人" field="consignCod" size="mini" span="4">
        <vxe-input v-model="dataForm.consignCod" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item field="consignNam" size="mini" span="8">
        <vxe-input v-model="dataForm.consignNam" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="船号" field="shipNo" size="mini" span="6">
        <vxe-input v-model="dataForm.shipNo" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="船名" field="shipNam" size="mini" span="6">
        <vxe-input v-model="dataForm.shipNam" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="出入库单号" field="outNo" size="mini" span="6">
        <vxe-input v-model="dataForm.outNo" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="内外贸" field="tradeId" size="mini" span="6">
        <vxe-input v-model="dataForm.tradeId" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="进出口" field="inoutId" size="mini" span="6">
        <vxe-input v-model="dataForm.inoutId" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="装卸" field="winId" size="mini" span="6">
        <vxe-input v-model="dataForm.winId" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="保管队" field="subunitCod" size="mini" span="6">
        <vxe-input v-model="dataForm.subunitCod" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="检算员" field="checkNam" size="mini" span="6">
        <vxe-input v-model="dataForm.checkNam" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="堆存方式" field="dcType" size="mini" span="6">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.dcname"
          :tableColumns="downTableOption.dcColum"
          :urlapi="downTableOption.dcurl"
          :returnApi="downTableOption.dcreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="入库方式" field="inYardWay" size="mini" span="6">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.inname"
          :tableColumns="downTableOption.inColum"
          :urlapi="downTableOption.inurl"
          :returnApi="downTableOption.inreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="出库方式" field="outYardWay" size="mini" span="6">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.outname"
          :tableColumns="downTableOption.outColum"
          :urlapi="downTableOption.outurl"
          :returnApi="downTableOption.outreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="货名" field="cargoNam" size="mini" span="6">
         <EditDown
          :objs="dataForm"
          :property="downTableOption.cargoname"
          :tableColumns="downTableOption.cargoColum"
          :urlapi="downTableOption.cargourl"
          :returnApi="downTableOption.cargoreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="价格货类" field="cargoKindNam" size="mini" span="6">
        <vxe-input v-model="dataForm.cargoKindNam" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="大货类" field="cargoKindNamDl" size="mini" span="6">
        <vxe-input v-model="dataForm.cargoKindNamDl" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="堆存地点" field="yardNam" size="mini" span="6">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.yardname"
          :tableColumns="downTableOption.yardColum"
          :urlapi="downTableOption.yardurl"
          :returnApi="downTableOption.yardreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
    </vxe-form>
    <div align="center">
      <el-button type="primary" size="mini" icon="el-icon-check" @click="handleSubmit">保存</el-button>
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
        informNo: null,
        contNo: null,
        shipNo: null,
        shipNam: null,
        tradeId: null,
        inoutId: null,
        winId: null,
        consignCod: null,
        consignNam: null,
        checkNam: null,
        outYardWay:null,
        inYardWay:null,
        cargoNam:null,
        cargoKindNam:null,
        cargoKindNamDl:null,
        dcType:null,
        yardNam:null,
      },
      downTableOption: {
        //合同
        contname: "contNo",
        contColum: [
          { field: "INFORM_NO", title: "合同号", width: "100px" },
          { field: "CONSIGN_NAM", title: "货主", width: "300px" },
          { field: "SHIP_NO", title: "船号", width: "80px" },
          { field: "SHIP_NAM", title: "船名", width: "80px" }
        ],
        contreturnApi: [
          { field: "INFORM_NO", returnName: "contNo" },
          { field: "SHIP_NO", returnName: "shipNo" },
          { field: "SHIP_NAM", returnName: "shipNam" },
          { field: "INOUT_ID", returnName: "inoutId" },
          { field: "TRADE_ID", returnName: "tradeId" },
          { field: "UNLOAD_ID", returnName: "winId" },
          { field: "CONSIGN_NAM", returnName: "consignNam" },
          { field: "CONSIGN_COD", returnName: "consignCod" },
          { field: "INOUT_YARD_NO", returnName: "outNo" }
        ],
        conturl: "/yard/mioyardhead/geContNo",
        //堆存方式
        dcname: "dcType",
        dcColum: [
          { field: "WAY_COD", title: "代码" },
          { field: "WAY_NAM", title: "名称" }
        ],
        dcreturnApi: [{ field: "WAY_NAM", returnName: "dcType" }],
        dcurl: "/basecode/basecod/getDcWay",
        //入库方式
        inname: "inYardWay",
        inColum: [
          { field: "CODE", title: "代码" },
          { field: "MEANING", title: "名称" }
        ],
        inreturnApi: [{ field: "MEANING", returnName: "inYardWay" }],
        inurl: "/basecode/basecod/getInOutWay",
        //出库方式
        outname: "outYardWay",
        outColum: [
          { field: "CODE", title: "代码" },
          { field: "MEANING", title: "名称" }
        ],
        outreturnApi: [{ field: "MEANING", returnName: "outYardWay" }],
        outurl: "/basecode/basecod/getInOutWay",
        //货物
        cargoname: "cargoNam",
        cargoColum: [
          { field: "CARGO_NAM_COD", title: "货物代码" },
          { field: "CARGO_NAM", title: "货物名称" },
          { field: "CARGO_KIND_COD_PRICE", title: "价格货类代码" },
          { field: "CARGO_KIND_NAM_PRICE", title: "价格货类名称" },
          { field: "CARGO_KIND_COD_DL", title: "大货类代码" },
          { field: "CARGO_KIND_NAM_DL", title: "大货类名称" }
        ],
        cargoreturnApi: [
          { field: "CARGO_NAM_COD", returnName: "cargoNamCod" },
          { field: "CARGO_NAM", returnName: "cargoNam" },
          { field: "CARGO_KIND_COD_PRICE", returnName: "cargoKindCod" },
          { field: "CARGO_KIND_NAM_PRICE", returnName: "cargoKindNam" },
          { field: "CARGO_KIND_COD_DL", returnName: "cargoKindCodDl" },
          { field: "CARGO_KIND_NAM_DL", returnName: "cargoKindNamDl" }
        ],
        cargourl: "/yard/mioyardhead/getCargoNam",
         //库场
        yardname: "yardNam",
        yardColum: [
          { field: "YARD_NO", title: "库场代码" },
          { field: "YARD_NAM", title: "库场名称" }
        ],
        yardreturnApi: [
          { field: "YARD_NAM", returnName: "yardNam" },
        ],
        yardurl: "/basecode/basecod/getYard",
      }
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
    ...mapState({
      userInfo: state => state.user.userInfo
    })
  },
  created() {
    if(this.datanow.informNo!=null){
      this.dataForm=this.datanow;
    }else{
     this.dataForm.checkNam = this.userInfo.manName;
     this.dataForm.contType='02';
     }
  },
  mounted: function() {},
  methods: {
    handleSubmit() {
      saveHead(
        Object.assign(
          this.dataForm
        )
      )
        .then(data => {
          this.$message.success("保存成功");
          this.$emit("selectList");
          this.$emit("dictItemVisible");
        })
        .catch(() => {});
    },
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