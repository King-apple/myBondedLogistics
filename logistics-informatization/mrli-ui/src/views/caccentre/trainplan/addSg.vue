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
  <div class="trainPlanJAdd">
    <vxe-form
      :data="dataForm"
      size="mini"
      ref="editHeadForm"
      :rules="formRules"
      title-width="90"
    >
    
      <vxe-form-item title="计划日期" field="datePlan" size="mini" span="12">
         <vxe-input
              v-model="dataForm.datePlan"
              type="date"
              format="YYYY-MM-DD"
              data-format="YYYY-MM-DD"
              placeholder="请选择日期"
              size="mini"
              span="6"
              clearable
            ></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="" field="" size="mini" span="12">
      </vxe-form-item>
      <vxe-form-item title="作业公司" field="unitNam" size="mini" span="12">
        <EditDown
              :objs="dataForm"
              :property="downTableOption.unitNam"
              :tableColumns="downTableOption.unitNamColum"
              :urlapi="downTableOption.unitNamurl"
              :returnApi="downTableOption.unitNamreturnApi"
              sizenow="mini"
            ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="建议股位" field="traceNam" size="mini" span="12">
        <EditDown
          v-if="ajaxOver"
          :objs="dataForm"
          :property="downTableOption.traceNam"
          :tableColumns="downTableOption.traceNamColum"
          :urlapi="downTableOption.traceNamurl"
          :returnApi="downTableOption.traceNamreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="货名" field="cargoNam" size="mini" span="12">
        <EditDown
              :objs="dataForm"
              :property="downTableOption.cargoNam"
              :tableColumns="downTableOption.cargoNamColum"
              :urlapi="downTableOption.cargoNamurl"
              :returnApi="downTableOption.cargoNamreturnApi"
              sizenow="mini"
            ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="规格" field="format" size="mini" span="12">
        <EditDown
              :objs="dataForm"
              :property="downTableOption.format"
              :tableColumns="downTableOption.formatColum"
              :urlapi="downTableOption.formaturl"
              :returnApi="downTableOption.formatreturnApi"
              sizenow="mini"
            ></EditDown>
      </vxe-form-item>
       <vxe-form-item title="包装" field="cpkgKindNam" size="mini" span="12">
        <EditDown
              :objs="dataForm"
              :property="downTableOption.cpkgKindNam"
              :tableColumns="downTableOption.cPkgKindNamColum"
              :urlapi="downTableOption.cPkgKindNamurl"
              :returnApi="downTableOption.cPkgKindNamreturnApi"
              sizenow="mini"
            ></EditDown>
      </vxe-form-item>
       <vxe-form-item title="到站" field="fromStatNam" size="mini" span="12">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.fromStatNam"
          :tableColumns="downTableOption.fromStatNamColum"
          :urlapi="downTableOption.fromStatNamurl"
          :returnApi="downTableOption.fromStatNamreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
       <vxe-form-item title="代理货主" field="consignNam" size="mini" span="12">
        <EditDown
              :objs="dataForm"
              :property="downTableOption.consignNam"
              :tableColumns="downTableOption.consignNamColum"
              :urlapi="downTableOption.consignNamurl"
              :returnApi="downTableOption.consignNamreturnApi"
              sizenow="mini"
            ></EditDown>
      </vxe-form-item>
       <vxe-form-item title="港口货主" field="shipperDoc" size="mini" span="12">
        <EditDown
              :objs="dataForm"
              :property="downTableOption.shipperDoc"
              :tableColumns="downTableOption.shipperDocColum"
              :urlapi="downTableOption.shipperDocurl"
              :returnApi="downTableOption.shipperDocreturnApi"
              sizenow="mini"
            ></EditDown>
      </vxe-form-item>
       <vxe-form-item title="发货人" field="setownerNam" size="mini" span="12">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.setownerNam"
          :tableColumns="downTableOption.setownerNamColum"
          :urlapi="downTableOption.setownerNamurl"
          :returnApi="downTableOption.setownerNamreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
       <vxe-form-item title="收货人" field="getownerNam" size="mini" span="12">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.getownerNam"
          :tableColumns="downTableOption.getownerNamColum"
          :urlapi="downTableOption.getownerNamurl"
          :returnApi="downTableOption.getownerNamreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="船名" field="shipNam" size="mini" span="12">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.shipNam"
          :tableColumns="downTableOption.shipNamColum"
          :urlapi="downTableOption.shipNamurl"
          :returnApi="downTableOption.shipNamreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="卸船重量" field="shipWgt" size="mini" span="12">
        <vxe-input v-model="dataForm.shipWgt"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="港存取量" field="weightWgt" size="mini" span="12">
        <vxe-input v-model="dataForm.weightWgt"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="总车数" field="trainNum" size="mini" span="12">
        <vxe-input v-model="dataForm.trainNum"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="联系电话" field="phone" size="mini" span="12">
        <vxe-input v-model="dataForm.phone"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="备注" field="markTxt" size="medium" span="24">
        <vxe-textarea v-model="dataForm.markTxt"></vxe-textarea>
      </vxe-form-item>
    </vxe-form>
    <div align="center">
      <el-button
        type="primary"
        size="mini"
        icon="el-icon-check"
        @click="handleSubmit"
        >保存</el-button
      >
      <el-button size="mini" icon="el-icon-close" plain @click="closeWindows"
        >取消</el-button
      >
    </div>
  </div>
</template>

<script>
import { saveSg } from "@/api/caccentre/trainplan";
import request from "@/router/axios";
import "@/styles/common/edit.scss";
import { mapGetters } from "vuex";
import moment from "moment";
import { mapState } from "vuex";
import Vue from "vue";
import VXETable from "vxe-table";
import hljs from "highlight.js";
import { Loading, Message } from "element-ui";
import { xj, jf } from "@/util/util";
import EditDown from "../../baseUtile/EditDown";
export default {
  props: ["datanow"],
  components: { EditDown },
  data() {
    return {
      // activeName: "first",
      // ajaxOver: false,
      // ajaxOverFirstMan: false,
      // tableDataCargo: [],
      // tableDataAssign: [],
      // tableDataScep: [],
      // tableDataText: [],
      // tableloadCargo: false,
      // tableloadAssign: false,
      // tableloadText: false,
      // tableloadScep: false,
      // advanceNumReadonly: true,
      // validRules: {},
      formRules: {
        cargoNam: [{ required: true, message: "请选择货名" }],
        cpkgKindNam: [{ required: true, message: "请选择包装" }],
        begfromStatNaminDate: [{ required: true, message: "请选择发站" }],
        consignNam: [{ required: true, message: "请选择代理货主" }],
        unitNam: [{ required: true, message: "请选择作业公司" }],
        shipperDoc: [{ required: true, message: "请选择港口货主" }],
        // tankCapacity: [{ required: true, message: "请填写罐容量" }],
        // feeRate: [{ required: true, message: "请填写费率" }],
      },
      // amtAll: 0,
      // weightAll: 0,
      // textnum: 1,
      dataForm: {
        consignCod: null,
        consignNam: null,
        unitCod: null,
        unitNam: null,
        shipperCod: null,
        shipperDoc: null,
        cargoNamCod: null,
        cargoNam: null,
        pkgKindCod: null,
        cpkgKindNam: null,
        traceNam: null,
        teleNo: null,
        fromStatCod: null,
        fromStatNam: null,
        setownerCod: null,
        setownerNam: null,
        getownerCod: null,
        getownerNam: null,
        shipNam:null,
        shipNo:null,
        format: null,
        unloadId:'+',
        planId:'1',
        datePlan: moment(new Date()).add("month",1).format("YYYY-MM-DD") ,
      },
      downTableOption: {
        //代理货主
        consignNam: "consignNam",
        consignNamColum: [
          { field: "SHIPPER_COD", title: "代理货主代码" },
          { field: "SHIPPER_DOC", title: "港口货主" },
        ],
        consignNamreturnApi: [
          { field: "SHIPPER_COD", returnName: "consignCod" },
          { field: "SHIPPER_DOC", returnName: "consignNam" },
        ],
        consignNamurl: "/basecode/basecod/getShipperDoc",
        //作业公司
        unitNam: "unitNam",
        unitNamColum: [
          { field: "UNIT_COD", title: "单位代码" },
          { field: "UNIT_NAM", title: "单位" },
        ],
        unitNamreturnApi: [
          { field: "UNIT_COD", returnName: "unitCod" },
          { field: "UNIT_NAM", returnName: "unitNam" },
        ],
        unitNamurl: "/basecode/basecod/getUnitNam?id=0",
        //港口货主
        shipperDoc: "shipperDoc",
        shipperDocColum: [
          { field: "SHIPPER_COD", title: "港口货主代码" },
          { field: "SHIPPER_DOC", title: "港口货主" },
        ],
        shipperDocreturnApi: [
          { field: "SHIPPER_COD", returnName: "shipperCod" },
          { field: "SHIPPER_DOC", returnName: "shipperDoc" },
        ],
        shipperDocurl: "/basecode/basecod/getShipperDoc",
        //货名
        cargoNam: "cargoNam",
        cargoNamColum: [
          { field: "CARGO_NAM_COD", title: "货名代码" },
          { field: "CARGO_NAM", title: "货名" },
        ],
        cargoNamreturnApi: [
          { field: "CARGO_NAM_COD", returnName: "cargoNamCod" },
          { field: "CARGO_NAM", returnName: "cargoNam" },
        ],
        cargoNamurl: "/basecode/basecod/getCargoNamCod",
        //包装
        cpkgKindNam: "cpkgKindNam",
        cPkgKindNamColum: [
          { field: "PKG_KIND_COD", title: "代码" },
          { field: "C_PKG_KIND_NAM", title: "包装" },
        ],
        cPkgKindNamreturnApi: [
          { field: "PKG_KIND_COD", returnName: "pkgKindCod" },
          { field: "C_PKG_KIND_NAM", returnName: "cpkgKindNam" },
        ],
        cPkgKindNamurl: "/basecode/basecod/getPkgKind",
        //规格
        format: "format",
        formatColum: [{ field: "FORMAT", title: "规格" }],
        formatreturnApi: [{ field: "FORMAT", returnName: "format" }],
        formaturl: "/basecode/basecod/getFormat",
        //建议股位 TRACE_NAM_LOV
        traceNam: "traceNam",
        traceNamColum: [
          { field: "TRACE_COD", title: "股位代码" },
          { field: "TRACE_NAM", title: "股位" },
        ],
        traceNamreturnApi: [
          { field: "TRACE_COD", returnName: "traceNam" },
        ],
        traceNamurl: "/basecode/basecod/getTraceNam",
        //发站 FROM_STAT_LOV
        fromStatNam: "fromStatNam",
        fromStatNamColum: [
          { field: "TELE_NO", title: "电报呼号" },
          { field: "SANOTH_NAM", title: "车站代码" },
          { field: "STATION_NAM", title: "车站" },
        ],
        fromStatNamreturnApi: [
          { field: "TELE_NO", returnName: "teleNo" },
          { field: "SANOTH_NAM", returnName: "fromStatCod" },
          { field: "STATION_NAM", returnName: "fromStatNam" },
        ],
        fromStatNamurl: "/basecode/basecod/getFromStatNam",
        //发货人 SETOWNER_LOV
        setownerNam: "setownerNam",
        setownerNamColum: [
          { field: "SHIPPER_COD", title: "发货人代码" },
          { field: "SHIPPER_DOC", title: "发货人" },
        ],
        setownerNamreturnApi: [
          { field: "SHIPPER_COD", returnName: "setownerCod" },
          { field: "SHIPPER_DOC", returnName: "setownerNam" },
        ],
        setownerNamurl: "/basecode/basecod/getSetownerNam",
        //收货人 GETOWNER_LOV
        getownerNam: "getownerNam",
        getownerNamColum: [
          { field: "SHIPPER_COD", title: "收货人代码" },
          { field: "SHIPPER_DOC", title: "收货人" },
        ],
        getownerNamreturnApi: [
          { field: "SHIPPER_COD", returnName: "getownerCod" },
          { field: "SHIPPER_DOC", returnName: "getownerNam" },
        ],
        getownerNamurl: "/basecode/basecod/getSetownerNam",
        //集疏港计划获取船舶 SHIP_LOV
        shipNam: "shipNam",
        shipNamColum: [
          { field: "SHIP_NO", title: "收货人代码" },
          { field: "SHIP_NAM", title: "收货人" },
        ],
        shipNamreturnApi: [
          { field: "SHIP_NO", returnName: "shipNo" },
          { field: "SHIP_NAM", returnName: "shipNam" },
        ],
        shipNamurl: "/basecode/basecod/getShipNoJsg",
      },
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
    ...mapState({
      userInfo: (state) => state.user.userInfo,
    }),
  },
  created() {
    this.ajaxOver = true;
  },
  mounted: function () {},
  methods: {
    handleSubmit() {
      
      this.$refs.editHeadForm
        .validate((valid) => {})
        .then((valid) => {
          saveSg(this.dataForm)
            .then((data) => {
              this.$message.success("保存成功");
              this.$emit("selectListplan");
              this.$emit("dictItemVisible");
            })
            .catch(() => {});
        });
    },
    closeWindows() {
      this.$emit("selectListplan");
      this.$emit("dictItemVisible");
    },
  },
};
</script>
<style lang="scss" scoped>
.businessContAdd {
  .el-divider--horizontal {
    margin: 8px 0;
  }
  .vxe-toolbar {
    height: 25px;
  }
}
</style>