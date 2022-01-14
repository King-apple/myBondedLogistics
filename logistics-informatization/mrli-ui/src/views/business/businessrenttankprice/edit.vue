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
      title-width="100"
    >
      <!-- <vxe-form-item title="年份" field="year" size="mini" span="12">
        <vxe-input v-model="dataForm.year" placeholder="请选择年份" valueFormat="yyyy" type="year"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="月份" field="month" size="mini" span="12">
        <vxe-input
          v-model="dataForm.month"
          placeholder="请选择月份"
          valueFormat="yyyyMM"
          type="month"
        ></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="开始日期" field="beginDate" size="mini" span="12">
        <vxe-input
          v-model="dataForm.beginDate"
          placeholder="请选择开始日期"
          format="yyyy-MM-dd"
          value-format="yyyy-MM-dd"
          type="date"
        ></vxe-input>
      </vxe-form-item> -->
      <vxe-form-item title="结束日期" field="endDate" size="mini" span="12">
        <vxe-input
          v-model="dataForm.endDate"
          placeholder="请选择结束日期"
          format="yyyy-MM-dd"
          value-format="yyyy-MM-dd"
          type="date"
        ></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="公司" field="unitNam" size="mini" span="12">
        <EditDown
          v-if="ajaxOver"
          :objs="dataForm"
          :property="downTableOption.unitNam"
          :tableColumns="downTableOption.unitColum"
          :urlapi="downTableOption.uniturl"
          :returnApi="downTableOption.unitreturnApi"
          @datereturn="tankCapacityReturn"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="货主" field="companyName" size="mini" span="12">
        <EditDown
          v-if="ajaxOver"
          :objs="dataForm"
          :property="downTableOption.companyName"
          :tableColumns="downTableOption.companyColum"
          :urlapi="downTableOption.companyurl"
          :returnApi="downTableOption.companyreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="罐容量" field="tankCapacity" size="mini" span="12">
        <vxe-input v-model="dataForm.tankCapacity"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="费率" field="feeRate" size="mini" span="12">
        <vxe-input v-model="dataForm.feeRate"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="备注" field="remark" size="medium" span="12">
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
import { save } from "@/api/business/businessrenttankprice";
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
        // year: [{ required: true, message: "请选择年份" }],
        // month: [{ required: true, message: "请选择月份" }],
        beginDate: [{ required: true, message: "请选择开始日期" }],
        endDate: [{ required: true, message: "请选择结束日期" }],
        unitNam: [{ required: true, message: "请选择公司" }],
        companyName: [{ required: true, message: "请选择货主" }],
        tankCapacity: [{ required: true, message: "请填写罐容量" }],
        feeRate: [{ required: true, message: "请填写费率" }],
      },
      // amtAll: 0,
      // weightAll: 0,
      // textnum: 1,
      dataForm: {
        // plantNo: null,
        // shipNo: null,
        // shipNam: null,
        // inoutId: null,
        // tradeId: null,
        // toPortTim: null,
        // firstNam: "",
        unitCod: "",
        unitNam: "",
        companyCod: "",
        companyName: "",
        // firstMan: null,
        // firstFax: null,
        // firstPhone: null,
      },
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
        //货主
        companyName: "companyName",
        companyColum: [
          { field: "SHIPPER_COD", title: "货主代码" },
          { field: "SHIPPER_DOC", title: "货主名称" },
        ],
        companyreturnApi: [
          { field: "SHIPPER_COD", returnName: "companyCod" },
          { field: "SHIPPER_DOC", returnName: "companyName" },
        ],
        companyurl: "/basecode/basecod/getShipperDoc",
        //罐容量
        tankCapacityName: "tankCapacity",
        tankCapacityColum: [
          { field: "UNIT_NAM", title: "单位名称" },
          { field: "CAPACITY", title: "罐容量" },
        ],
        tankCapacityreturnApi: [
          { field: "CAPACITY", returnName: "tankCapacity" },
        ],
        tankCapacityurl: "/business/businesstankcapacity/getCapacity",
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
    this.dataForm = this.datanow;
  },
  mounted: function () {},
  methods: {
    handleSubmit() {
      if(this.dataForm.endDate<this.dataForm.beginDate){
          this.$message.error("结束时间不能小于结束时间");
          return
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
    tankCapacityReturn() {
      this.ajaxOver = true;
      this.downTableOption.tankCapacityurl =
        "/business/businesstankcapacity/getCapacity" +
        "?unitCod=" +
        this.dataForm.unitCod;
      console.log(this.downTableOption.tankCapacityurl);
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