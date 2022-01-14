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
  <div class="businessContAdd">
    <vxe-form
      :data="dataForm"
      size="mini"
      ref="editHeadForm"
      :rules="formRules"
      title-width="120"
    >
      <vxe-form-item title="罐号" field="tankNo" size="mini" span="12">
        <!-- <vxe-input v-model="dataForm.tankNo"></vxe-input> -->
        <EditDown
          v-if="ajaxOver"
          :objs="dataForm"
          :property="downTableOption.tankNo"
          :tableColumns="downTableOption.tankNoColum"
          :urlapi="downTableOption.tankNourl"
          :returnApi="downTableOption.tankNoreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="罐容量" field="tankCapacity" size="mini" span="12">
        <!-- <vxe-input v-model="dataForm.tankCapacity"></vxe-input> -->
        <EditDown
          v-if="ajaxOver"
          :objs="dataForm"
          :property="downTableOption.tankCapacity"
          :tableColumns="downTableOption.tankCapacityColum"
          :urlapi="downTableOption.tankCapacityurl"
          :returnApi="downTableOption.tankCapacityreturnApi"
          @datereturn="feeRateReturn"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="单位" field="feeUnit" size="mini" span="12">
        <!-- <vxe-input v-model="dataForm.feeUnit"></vxe-input> -->
        <EditDown
          v-if="ajaxOver"
          :objs="dataForm"
          :property="downTableOption.feeUnit"
          :tableColumns="downTableOption.feeUnitColum"
          :urlapi="downTableOption.feeUniturl"
          :returnApi="downTableOption.feeUnitreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="货名" field="cargoNam" size="mini" span="12">
        <!-- <vxe-input v-model="dataForm.cargoNam"></vxe-input> -->
        <EditDown
          v-if="ajaxOver"
          :objs="dataForm"
          :property="downTableOption.cargoNam"
          :tableColumns="downTableOption.cargoNamColum"
          :urlapi="downTableOption.cargoNamurl"
          :returnApi="downTableOption.cargoNamreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="包装" field="pkgKindNam" size="mini" span="12">
        <!-- <vxe-input v-model="dataForm.pkgKindNam"></vxe-input> -->
        <EditDown
          v-if="ajaxOver"
          :objs="dataForm"
          :property="downTableOption.pkgKindNam"
          :tableColumns="downTableOption.pkgKindNamColum"
          :urlapi="downTableOption.pkgKindNamurl"
          :returnApi="downTableOption.pkgKindNamreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="费率" field="feeRate" size="mini" span="12">
        <!-- <vxe-input v-model="dataForm.feeRate"></vxe-input> -->
        <EditDown
          v-if="ajaxOver"
          :objs="dataForm"
          :property="downTableOption.feeRate"
          :tableColumns="downTableOption.feeRateColum"
          :urlapi="downTableOption.feeRateurl"
          :returnApi="downTableOption.feeRatereturnApi"
          @datereturn="ifChangeReturn"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="开始日期" field="beginDate" size="mini" span="12">
        <vxe-input
          v-model="dataForm.beginDate"
          placeholder="请选择开始日期"
          type="date"
        ></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="结束日期" field="endDate" size="mini" span="12">
        <vxe-input
          v-model="dataForm.endDate"
          placeholder="请选择结束日期"
          type="date"
        ></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="作业委托人" field="shiperNam" size="mini" span="24">
        <EditDown
          v-if="ajaxOver"
          :objs="dataForm"
          :property="downTableOption.shiperNam"
          :tableColumns="downTableOption.shiperColum"
          :urlapi="downTableOption.shiperurl"
          :returnApi="downTableOption.shiperreturnApi"
          @datereturn="ifChangeReturn"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>

      <vxe-form-item title="是否交租罐费" field="ifPay" size="mini" span="8">
        <!-- <vxe-input v-model="dataForm.ifPay"></vxe-input> -->
        <vxe-select
          v-model="dataForm.ifPay"
          placeholder="是否交租罐费"
          :options="listifpay"
          @change="ifpayChange"
        ></vxe-select>
      </vxe-form-item>
      <vxe-form-item title="" field="ifPay" size="mini" span="12">
      </vxe-form-item>
      <vxe-form-item
        title="作业委托人（交）"
        field="shiperPayNam"
        size="mini"
        span="24"
      >
        <EditDown
          v-if="ajaxOver1"
          :objs="dataForm"
          :property="downTableOption.shiperPayNam"
          :tableColumns="downTableOption.shiperPayColum"
          :urlapi="downTableOption.shiperPayurl"
          :returnApi="downTableOption.shiperPayreturnApi"
          @datereturn="ifChangeReturn"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="经办人" field="jbr" size="mini" span="12">
        <vxe-input v-model="dataForm.jbr"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="联系人电话" field="tele" size="mini" span="12">
        <vxe-input v-model="dataForm.tele"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="发票名头" field="billTitle" size="mini" span="12">
        <vxe-input v-model="dataForm.billTitle"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="备注" field="remark" size="medium" span="24">
        <vxe-textarea v-model="dataForm.remark"></vxe-textarea>
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
import { save } from "@/api/business/businessrenttankagreement";
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
        tankNo: [{ required: true, message: "请选择罐号" }],
        tankCapacity: [{ required: true, message: "请选择罐容量" }],
        feeUnit: [{ required: true, message: "请选择单位" }],
        cargoNam: [{ required: true, message: "请选择货名" }],
        pkgKindNam: [{ required: true, message: "请选择包装" }],
        companyName: [{ required: true, message: "请选择货主" }],
        feeRate: [{ required: true, message: "请填写费率" }],
        beginDate: [{ required: true, message: "请选择开始日期" }],
        endDate: [{ required: true, message: "请选择结束日期" }],
        jbr: [{ required: true, message: "请填写经办人" }],
        tele: [{ required: true, message: "请填写联系人电话" }],
        // billTitle: [{ required: true, message: "请填写发票名头" }],
        ifPay: [{ required: true, message: "请选择是否交租罐费" }],
      },
      // amtAll: 0,
      // weightAll: 0,
      // textnum: 1,
      dataForm: {
        tankNo: "",
        tankCapacity: "",
        feeUnitCod: "",
        feeUnit: "",
        cargoNamCod: "",
        cargoNam: "",
        pkgKindCod: "",
        pkgKindNam: "",
        feeRate: "",
        shiperCod: "",
        shiperNam: "",
        shiperPayCod: "",
        shiperPayNam: "",
        companyCodRate: "",
        billTitle: "",
      },
      listifpay: [
        { label: "是", value: "01" },
        { label: "否", value: "00" },
      ],
      downTableOption: {
        //单位
        unitNam: "unitNam",
        unitColum: [
          { field: "UNIT_COD", title: "单位代码" },
          { field: "UNIT_NAM", title: "单位名称" },
        ],
        unitreturnApi: [
          { field: "UNIT_COD", returnName: "unitCod" },
          { field: "UNIT_NAM", returnName: "unitNam" },
          { field: "UNIT_COD", returnName: "unitcod" },
          { field: "UNIT_NAM", returnName: "unitNam" },
        ],
        uniturl: "/basecode/basecod/getUnitNam",
        //作业委托人
        shiperNam: "shiperNam",
        shiperColum: [
          { field: "SHIPPER_COD", title: "货主代码" },
          { field: "SHIPPER_DOC", title: "货主名称" },
        ],
        shiperreturnApi: [
          { field: "SHIPPER_COD", returnName: "shiperCod" },
          { field: "SHIPPER_DOC", returnName: "shiperNam" },
          { field: "SHIPPER_DOC", returnName: "billTitle" },
        ],
        shiperreturnApi2: [
          { field: "SHIPPER_COD", returnName: "shiperCod" },
          { field: "SHIPPER_DOC", returnName: "shiperNam" },
        ],
        shiperreturnApi3: [
          { field: "SHIPPER_COD", returnName: "shiperCod" },
          { field: "SHIPPER_DOC", returnName: "shiperNam" },
          { field: "SHIPPER_DOC", returnName: "billTitle" },
        ],
        shiperurl: "/basecode/basecod/getShipperDoc",
        //作业委托人（交）
        shiperPayNam: "shiperPayNam",
        shiperPayColum: [
          { field: "SHIPPER_COD", title: "货主代码" },
          { field: "SHIPPER_DOC", title: "货主名称" },
        ],
        shiperPayreturnApi: [
          { field: "SHIPPER_COD", returnName: "shiperPayCod" },
          { field: "SHIPPER_DOC", returnName: "shiperPayNam" },
          { field: "SHIPPER_DOC", returnName: "billTitle" },
        ],
        shiperPayreturnApi2: [
          { field: "SHIPPER_COD", returnName: "shiperPayCod" },
          { field: "SHIPPER_DOC", returnName: "shiperPayNam" },
        ],
        shiperPayreturnApi3: [
          { field: "SHIPPER_COD", returnName: "shiperPayCod" },
          { field: "SHIPPER_DOC", returnName: "shiperPayNam" },
          { field: "SHIPPER_DOC", returnName: "billTitle" },
        ],
        shiperPayurl: "/basecode/basecod/getShipperDoc",
        //罐号
        tankNo: "tankNo",
        tankNoColum: [
          { field: "YARD_NAM", title: "库长名称" },
          { field: "LOC_NO", title: "罐号" },
        ],
        tankNoreturnApi: [
          { field: "YARD_NO", returnName: "yardNo" },
          { field: "YARD_NAM", returnName: "yardNam" },
          { field: "LOC_NO", returnName: "tankNo" },
        ],
        tankNourl: "/basecode/basecod/getCLoc",
        //罐容
        tankCapacity: "tankCapacity",
        tankCapacityColum: [
          // { field: "SHIPPER_COD", title: "货主代码" },
          { field: "CAPACITY", title: "罐容" },
        ],
        tankCapacityreturnApi: [
          // { field: "SHIPPER_COD", returnName: "shiperPayCod" },
          { field: "CAPACITY", returnName: "tankCapacity" },
        ],
        tankCapacityurl: "/basecode/basecod/getCapacity",
        //单位
        feeUnit: "feeUnit",
        feeUnitColum: [
          { field: "CODE", title: "单位代码" },
          { field: "C_FEE_UNIT", title: "单位名称" },
        ],
        feeUnitreturnApi: [
          { field: "CODE", returnName: "feeUnitCod" },
          { field: "C_FEE_UNIT", returnName: "feeUnit" },
        ],
        feeUniturl: "/basecode/basecod/getFeeUnt",
        //货名
        cargoNam: "cargoNam",
        cargoNamColum: [
          { field: "CARGO_KIND_COD_PRICE", title: "货类代码" },
          { field: "CARGO_KIND_NAM_PRICE", title: "货类名称" },
        ],
        cargoNamreturnApi: [
          { field: "CARGO_KIND_COD_PRICE", returnName: "cargoNamCod" },
          { field: "CARGO_KIND_NAM_PRICE", returnName: "cargoNam" },
        ],
        cargoNamurl: "/basecode/basecod/getCargoNam",
        //包装
        pkgKindNam: "pkgKindNam",
        pkgKindNamColum: [
          { field: "PKG_KIND_COD", title: "包装代码" },
          { field: "C_PKG_KIND_NAM", title: "包装名称" },
        ],
        pkgKindNamreturnApi: [
          { field: "PKG_KIND_COD", returnName: "pkgKindCod" },
          { field: "C_PKG_KIND_NAM", returnName: "pkgKindNam" },
        ],
        pkgKindNamurl: "/basecode/basecod/getPkgKind",
        //费率
        feeRate: "feeRate",
        feeRateColum: [
          { field: "COMPANY_NAME", title: "货主" },
          { field: "TANK_CAPACITY", title: "罐容量" },
          { field: "FEE_RATE", title: "费率" },
        ],
        feeRatereturnApi: [
          { field: "COMPANY_COD", returnName: "companyCodRate" },
          { field: "FEE_RATE", returnName: "feeRate" },
        ],
        feeRateurl: "/business/businessrenttankprice/getFeerate",
      },
      ajaxOver1: true,
      EditType: true,
      ifChange: false,
      companyCodRate:"",
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
    this.dataForm = this.datanow;
    if (this.dataForm.ifPay == "01") {
      this.ajaxOver1 = false;
    }
    
    
  },
  mounted: function () {
    if(this.dataForm.ifPay=="01"){
        this.dataForm.companyCodRate=this.datanow.shiperCod;
    }
    if(this.dataForm.ifPay=="00"){
        this.dataForm.companyCodRate=this.datanow.shiperPayCod;
    }
  },
  methods: {
    handleSubmit() {
      if (this.dataForm.ifPay == "01" && this.dataForm.shiperNam == "") {
        this.$message.error("请选择作业委托人");
        return;
      }
      if (this.dataForm.ifPay == "00" && this.dataForm.shiperPayNam == "") {
        this.$message.error("请选择作业委托人(交)");
        return;
      }
      if (this.EditType && this.ifChange) {
        if(this.dataForm.ifPay == "01" &&
          this.dataForm.companyCodRate ==""){
          this.$message.error("作业委托人已变更，请重新选择费率");
          return;
        }else if (
          this.dataForm.ifPay == "01" &&this.dataForm.companyCodRate != ""&&
          this.dataForm.companyCodRate != this.dataForm.shiperCod
        ) {
          this.$message.error("当前费率与作业委托人不匹配，请重新选择");
          return;
        }
        if(this.dataForm.ifPay == "00" &&
          this.dataForm.companyCodRate ==""){
          this.$message.error("作业委托人（交）已变更，请重新选择费率");
          return;
        }else if (
          this.dataForm.ifPay == "00" &&this.dataForm.companyCodRate != ""&&
          this.dataForm.companyCodRate != this.dataForm.shiperPayCod
        ) {
          this.$message.error("当前费率与作业委托人（交）不匹配，请重新选择");
          return;
        }
        
      }
      if (this.dataForm.endDate < this.dataForm.beginDate) {
        this.$message.error("结束时间不能小于结束时间");
        return;
      }
      this.$refs.editHeadForm
        .validate((valid) => {})
        .then((valid) => {
          save(this.dataForm)
            .then((data) => {
              this.$message.success("保存成功");
              this.$emit("selectListplan");
              this.$emit("dictItemVisible");
            })
            .catch(() => {});
        });
    },
    feeRateReturn() {
      this.ajaxOver = true;
      this.downTableOption.feeRateurl =
        "/business/businessrenttankprice/getFeerate" +
        "?capacity=" +
        this.dataForm.tankCapacity;
    },
    ifChangeReturn() {
      this.ifChange = true;
    },
    ifpayChange() {
      if (this.dataForm.ifPay == "01") {
        this.ajaxOver1 = false;
        this.dataForm.shiperPayCod = "";
        this.dataForm.shiperPayNam = "";
        this.dataForm.billTitle=this.dataForm.shiperNam;
        this.downTableOption.shiperreturnApi = this.downTableOption.shiperreturnApi3;
        this.downTableOption.shiperPayreturnApi = this.downTableOption.shiperPayreturnApi2;
      } else if (this.dataForm.ifPay == "00") {
        this.ajaxOver1 = true;
        this.downTableOption.shiperreturnApi = this.downTableOption.shiperreturnApi2;
        this.downTableOption.shiperPayreturnApi = this.downTableOption.shiperPayreturnApi3;
        this.dataForm.billTitle="";
      }
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