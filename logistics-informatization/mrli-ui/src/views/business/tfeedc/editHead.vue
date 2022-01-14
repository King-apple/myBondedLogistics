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
  <div class="zyworkresourcescar-edithead">
    <vxe-form :data="dataForm" title-width="100" :rules="formRules" ref="editHeadForm">
      <vxe-form-item title="年份" field="yearFee" size="mini" span="12">
        <vxe-input v-model="dataForm.yearFee" placeholder="请选择年份" valueFormat="yyyy" type="year"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="月份" field="mon" size="mini" span="12">
        <vxe-input
          v-model="dataForm.mon"
          placeholder="请选择月份"
          valueFormat="yyyyMM"
          type="month"
        ></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="单位名称" field="unitNam" size="mini" span="12">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.unitNam"
          :tableColumns="downTableOption.unitColum"
          :urlapi="downTableOption.uniturl"
          :returnApi="downTableOption.unitreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="价格货类" field="cargoKindNam" size="mini" span="12">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.cargoname"
          :tableColumns="downTableOption.cargoColum"
          :urlapi="downTableOption.cargourl"
          :returnApi="downTableOption.cargoreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>

      <vxe-form-item title="单船下限" field="cargoVolLower" size="mini" span="12">
        <vxe-input v-model="dataForm.cargoVolLower" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="单船上限" field="cargoVolUpper" size="mini" span="12">
        <vxe-input v-model="dataForm.cargoVolUpper" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="下限吨" field="weightLower" size="mini" span="12">
        <vxe-input v-model="dataForm.weightLower" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="上限吨" field="weightUpper" size="mini" span="12">
        <vxe-input v-model="dataForm.weightUpper" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="起始天数" field="beginDay" size="mini" span="12">
        <vxe-input v-model="dataForm.beginDay" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="结束天数" field="endDay" size="mini" span="12">
        <vxe-input v-model="dataForm.endDay" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="免收天数" field="noDays" size="mini" span="12">
        <vxe-input v-model="dataForm.noDays" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="最终费率" field="feeRat" size="mini" span="12">
        <vxe-input v-model="dataForm.feeRat" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="内外贸" field="tradeId" size="mini" span="12">
        <vxe-select v-model="dataForm.tradeId" placeholder="内外贸" :options="listtrad"></vxe-select>
      </vxe-form-item>
      <vxe-form-item title="进出口" field="inoutId" size="mini" span="12">
        <vxe-select v-model="dataForm.inoutId" placeholder="进出口" :options="listio"></vxe-select>
      </vxe-form-item>
      <vxe-form-item title="有效" field="effect" size="mini" span="12">
        <vxe-select v-model="dataForm.effect" placeholder="有效否" :options="listuse"></vxe-select>
      </vxe-form-item>
    </vxe-form>
    <div align="center">
      <el-button type="primary" size="mini" icon="el-icon-check" @click="handleSubmit">保存</el-button>
      <el-button size="mini" icon="el-icon-close" plain @click="closeWindows">取消</el-button>
    </div>
  </div>
</template>

<script>
import { addObj, getUnit } from "@/api/business/tfeedc";
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
      dataForm: { cargoKindCod: null, cargoKindNam: null ,unitCod:null,unitNam:null},
      formRules: {
        noDays: [{ required: true, message: "请输入免收天数" }]
      },
      listtrad: [
        { label: "内贸", value: "1" },
        { label: "外贸", value: "0" }
      ],
      listio: [
        { label: "进口", value: "1" },
        { label: "出口", value: "0" }
      ],
      listuse: [
        { label: "有效", value: 1 },
        { label: "无效", value: 0 }
      ],
      downTableOption: {
       //单位
       //货物
        unitNam: "unitNam",
       unitColum: [
          { field: "UNIT_COD", title: "单位代码" },
          { field: "UNIT_NAM", title: "单位名称" }
        ],
        unitreturnApi: [
          { field: "UNIT_COD", returnName: "unitCod" },
          { field: "UNIT_NAM", returnName: "unitNam" }
        ],
        uniturl: "/basecode/basecod/getUnitNam",
        //货物
        cargoname: "cargoKindNam",
        cargoColum: [
          { field: "CARGO_KIND_COD_PRICE", title: "价格货类代码" },
          { field: "CARGO_KIND_NAM_PRICE", title: "价格货类名称" }
        ],
        cargoreturnApi: [
          { field: "CARGO_KIND_COD_PRICE", returnName: "cargoKindCod" },
          { field: "CARGO_KIND_NAM_PRICE", returnName: "cargoKindNam" }
        ],
        cargourl: "/basecode/basecod/getCargoNam"
      }
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() {
      if(this.datanow.seqNo!=null){ 
      if(this.datanow.mon.length==4){
        this.datanow.mon=null;
      }
      this.dataForm = this.datanow;  
    }else{
     
     }
    ;
  },
  mounted: function() {
  
  },
  methods: {
    handleSubmit() {
      this.$refs.editHeadForm
        .validate(valid => {})
        .then(valid => {
          addObj(this.dataForm)
            .then(data => {
              this.$message.success("保存成功");
              this.$emit("selectList");
              this.$emit("dictItemVisible");
            })
            .catch(() => {});
        });
    },
    closeWindows() {
      this.$emit("dictItemVisible");
    }
  }
};
</script>
<style >
</style>