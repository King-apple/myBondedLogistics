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
    <vxe-form
      :data="dataForm"
      title-width="100"
      :rules="formRules"
      ref="editHeadForm"
    >
      <vxe-form-item title="开始日期" field="beginTim" size="mini" span="24">
         <EditTime  :objs="dataForm" property="beginTim" sizenow="mini" valueFormat="yyyyMMddHHmm" ifSs="ok" inputType="datetimeInput">
        </EditTime>
       
      </vxe-form-item>
      
      <vxe-form-item title="状态" field="status" size="mini" span="24">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.status"
          :tableColumns="downTableOption.statusColum"
          :urlapi="downTableOption.statusurl"
          :returnApi="downTableOption.statusreturnApi"
          pageSize="100"
          @datereturn="statusReturn"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="所开仓" field="cabinStr" size="mini" span="24">
        <vxe-input v-model="dataForm.cabinStr"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="TEU" field="teuNum" size="mini" span="24">
        <vxe-input v-model="dataForm.teuNum"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="作业进度" field="weightWgt" size="mini" span="24">
        <vxe-input v-model="dataForm.weightWgt"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="累计完成" field="totWgt" size="mini" span="24" >
        <vxe-input v-model="dataForm.totWgt"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="单位" field="unitNam" size="mini" span="24">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.unitNam"
          :tableColumns="downTableOption.unitNamColum"
          :urlapi="downTableOption.unitNamurl"
          :returnApi="downTableOption.unitNamreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>

      <vxe-form-item title="船名" field="shipNam" size="mini" span="24" > 
        <vxe-input v-model="ship.shipNam" readonly="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="泊位" field="berthCod" size="mini" span="24" v-if="bwView">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.berthNam"
          :tableColumns="downTableOption.berthNamColum"
          :urlapi="downTableOption.berthNamurl"
          :returnApi="downTableOption.berthNamreturnApi"
          sizenow="mini"
          @datereturn="berthReturn"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="前缆桩" field="anteCable" size="mini" span="24" v-if="bwView">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.anteCable"
          :tableColumns="downTableOption.anteCableColum"
          :urlapi="downTableOption.anteCableurl"
          :returnApi="downTableOption.anteCablereturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="后缆桩" field="stepCable" size="mini" span="24" v-if="bwView">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.stepCable"
          :tableColumns="downTableOption.anteCableColum"
          :urlapi="downTableOption.anteCableurl"
          :returnApi="downTableOption.stepCablereturnApi"
          sizenow="mini"
        ></EditDown>
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
import { putObj } from "@/api/caccentre/shipstatus";
import request from "@/router/axios";
import "@/styles/common/edit.scss";
import { mapGetters } from "vuex";
import moment from "moment";
import Vue from "vue";
import VXETable from "vxe-table";
import hljs from "highlight.js";
import EditDown from "@/views/baseUtile/EditDown";
import EditTime from "@/views/baseUtile/EditTime";

import { Loading, Message } from "element-ui";
export default {
  props: ["ship", "editNow", "editType"],
  components: { EditDown,EditTime },
  data() {
    return {
      dataForm: {
        beginTim:null,
        status: null,
        statCod: null,
        unitNam: null,
        unitCod: null,
        shipNo: null,
        shipNam: null,
        berthNam: null,
        berthCod:null,
        anteCable: null,
        stepCable: null,
      },
      bwView:false,
      formRules: {
        zyWorkDte: [{ required: true, message: "请选择时间" }],
      },
      downTableOption: {
        //动态代码
        status: "status",
        statusColum: [
          { field: "STAT_COD", title: "代码" },
          { field: "STATUS", title: "名称" },
        ],
        statusreturnApi: [
          { field: "STATUS", returnName: "status" },
          { field: "STAT_COD", returnName: "statCod" },
          { field: "CODE", returnName: "code" },
        ],
        statusurl: "/basecode/basecod/getShipStatus",
        //单位
        unitNam: "unitNam",
        unitNamColum: [
          { field: "UNIT_COD", title: "单位代码" },
          { field: "UNIT_NAM", title: "单位名称" },
        ],
        unitNamreturnApi: [
          { field: "UNIT_COD", returnName: "unitCod" },
          { field: "UNIT_NAM", returnName: "unitNam" },
        ],
        unitNamurl: "/basecode/basecod/getUnitNam",
        //泊位
        berthNam: "berthCod",
        berthNamColum: [
          { field: "BERTH_COD", title: "泊位代码" },
          { field: "BERTH_NAM", title: "泊位名称" },
        ],
        berthNamreturnApi: [
          { field: "BERTH_COD", returnName: "berthCod" },
          { field: "BERTH_NAM", returnName: "berthNam" },
        ],
        berthNamurl: "/basecode/basecod/getBerth",
        //前缆桩
        anteCable: "anteCable",
        anteCableColum: [
          { field: "PICKET_NO", title: "缆桩代码" },
          { field: "CODE_NAME", title: "缆桩名称" },
        ],
        anteCablereturnApi: [
          { field: "PICKET_NO", returnName: "anteCable" },
          { field: "CODE_NAME", returnName: "anteCableNam" },
        ],
        anteCableurl: "/basecode/basecod/getPicket",
        //后缆桩
        stepCable: "stepCable",
        stepCablereturnApi: [
          { field: "PICKET_NO", returnName: "stepCable" },
          { field: "CODE_NAME", returnName: "stepCableNam" },
        ],
      },
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  created() {
     this.dataForm.shipNo = this.ship.shipNo;
    this.dataForm.shipNam = this.ship.shipNam;
    console.log(this.ship);
    this.dataForm.unitCod=this.ship.unitCod;
    this.dataForm.unitNam=this.ship.unitNam;
    if (this.editType != null && this.editType == "edit") {
      this.dataForm = this.editNow;
    }
  },
  mounted: function () {
   
  },
  methods: {
    handleSubmit() {
      if (
        this.dataForm.statCod =="10" || this.dataForm.statCod =="14"
      ){
          if (
            this.dataForm.berthCod == null 
          ) {
            this.$alert("泊位代码不能为空！", "错误");
            return;
          }
          if (
            this.dataForm.anteCable == null 
          ) {
            this.$alert("请输入前缆桩！", "错误");
            return;
          }
          if (
            this.dataForm.stepCable == null 
          ) {
            this.$alert("请输入后缆桩！", "错误");
            return;
          }
      }
      this.$refs.editHeadForm
        .validate((valid) => {})
        .then((valid) => {
          putObj(this.dataForm)
            .then((data) => {
              this.$message.success("保存成功");
              this.$emit("selectList", this.dataForm.shipNo);
              this.$emit("dictItemVisible");
            })
            .catch(() => {});
        });
    },
    closeWindows() {
      this.$emit("dictItemVisible");
    },
    statusReturn(value){
        if(value.STAT_COD=='10'||value.STAT_COD=='14'){
          this.bwView=true;
        }else{
           this.bwView=false;
        }
        if(value.STAT_COD=='00'){
           this.dataForm.status= null;
           this.dataForm.statCod= null;
          this.$alert("下锚操作请在<船舶信息维护-预报船舶>功能下进行！", "错误");
            return;
        }
    },
    berthReturn(row){
      this.downTableOption.anteCableurl="/basecode/basecod/getPicket?berthCod="+encodeURIComponent(row.BERTH_COD);
    },
  },
};
</script>
<style >
</style>