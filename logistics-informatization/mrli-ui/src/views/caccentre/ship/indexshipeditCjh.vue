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
  <div class="caccentre-ship-indexshipeditCjh">
    <vxe-form :data="dataForm" title-width="100" :rules="formRules" ref="editForm">
       <vxe-form-item title="船名" field="shipNam" size="mini" span="8">
        <vxe-input v-model="dataForm.shipNam" readonly="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="呼号" field="callNo" size="mini" span="8">
        <vxe-input v-model="dataForm.callNo" readonly="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="理货单位" field="tallyNam" size="mini" span="8">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.tallyNam"
          :tableColumns="downTableOption.tallyNamColum"
          :urlapi="downTableOption.tallyNamurl"
          :returnApi="downTableOption.tallyNamreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="船籍" field="ccountryNam" size="mini" span="8">
        <vxe-input v-model="dataForm.ccountryNam" readonly="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="单位" field="unitNam" size="mini" span="8">
        <vxe-input v-model="dataForm.unitNam" readonly="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="上一港" field="prePortNam" size="mini" span="8">
        <vxe-input v-model="dataForm.prePortNam" readonly="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="载重吨" field="shipCap" size="mini" span="8">
        <vxe-input v-model="dataForm.shipCap" readonly="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="船长" field="shipLongNum" size="mini" span="8">
        <vxe-input v-model="dataForm.shipLongNum" readonly="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="代理名称" field="agentNam" size="mini" span="8">
        <vxe-input v-model="dataForm.agentNam" readonly="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="来港" field="fromPortNam" size="mini" span="8">
        <vxe-input v-model="dataForm.fromPortNam" readonly="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="派船方" field="sendShipC" size="mini" span="8">
        <vxe-input v-model="dataForm.sendShipC" readonly="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="船类型" field="shipTypNam" size="mini" span="8">
        <vxe-input v-model="dataForm.shipTypNam" readonly="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="往港" field="toPortNam" size="mini" span="8">
         <vxe-input v-model="dataForm.toPortNam" readonly="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="内外贸" field="tradeName" size="mini" span="8">
       <vxe-input v-model="dataForm.tradeName" readonly="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="船主" field="shipOwnerNam" size="mini" span="8">
       <vxe-input v-model="dataForm.shipOwnerNam" readonly="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="进口货种" field="inCargoNam" size="mini" span="8">
        <vxe-input v-model="dataForm.inCargoNam" readonly="true"></vxe-input>
      </vxe-form-item>
       <vxe-form-item title="进口载重" field="inShipCap" size="mini" span="8">
        <vxe-input v-model="dataForm.inShipCap"  readonly="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="航线" field="shipLineNam" size="mini" span="8">
        <vxe-input v-model="dataForm.shipLineNam" readonly="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="出口货种" field="outCargoNam" size="mini" span="8">
        <vxe-input v-model="dataForm.outCargoNam" readonly="true"></vxe-input>
      </vxe-form-item>
     <vxe-form-item title="出口载重" field="outShipCap" size="mini" span="8">
       <vxe-input v-model="dataForm.outShipCap" readonly="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="进口航次" field="inVoyage" size="mini" span="8">
         <vxe-input v-model="dataForm.inVoyage" readonly="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="预抵时间" field="edtArivTim" size="mini" span="8">
        <vxe-input v-model="dataForm.edtArivTim" readonly="true"></vxe-input>
      </vxe-form-item>
       <vxe-form-item title="抵锚时间" field="toPortTim" size="mini" span="8">
        <vxe-input v-model="dataForm.toPortTim"  readonly="true" ></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="出口航次" field="outVoyage" size="mini" span="8">
        <vxe-input v-model="dataForm.outVoyage" readonly="true" ></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="抵港艏吃水" field="arrivHeadWat" size="mini" span="8">
        <vxe-input v-model="dataForm.arrivHeadWat" readonly="true"></vxe-input>
      </vxe-form-item>
     <vxe-form-item title="抵港中吃水" field="arrivMidWat" size="mini" span="8">
       <vxe-input v-model="dataForm.arrivMidWat" readonly="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="抵港艉吃水" field="arrivTailWat" size="mini" span="8">
         <vxe-input v-model="dataForm.arrivTailWat" readonly="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="离港艏吃水" field="levHeadWat" size="mini" span="8">
        <vxe-input v-model="dataForm.levHeadWat" readonly="true"></vxe-input>
      </vxe-form-item>
       <vxe-form-item title="离港中吃水" field="levMidWat" size="mini" span="8">
        <vxe-input v-model="dataForm.levMidWat"  readonly="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="离港艉吃水" field="levTailWat" size="mini" span="8">
        <vxe-input v-model="dataForm.levTailWat" readonly="true" ></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="泊位" field="berthCod" size="mini" span="8">
         <vxe-input v-model="dataForm.berthCod" readonly="true" ></vxe-input>
      </vxe-form-item>
     <vxe-form-item title="前缆桩" field="anteCable" size="mini" span="8">
       <vxe-input v-model="dataForm.anteCable" readonly="true" ></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="靠泊时间" field="toBerthTim" size="mini" span="8">
        <vxe-input v-model="dataForm.toBerthTim" readonly="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="北纬" field="placeN" size="mini" span="8">
        <vxe-input v-model="dataForm.placeN" readonly="true"></vxe-input>
      </vxe-form-item>
       <vxe-form-item title="后缆桩" field="stepCable" size="mini" span="8">
        <vxe-input v-model="dataForm.stepCable" readonly="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="吊杆负荷" field="derrickCap" size="mini" span="8">
        <vxe-input v-model="dataForm.derrickCap" readonly="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="东经" field="placeE" size="mini" span="8">
        <vxe-input v-model="dataForm.placeE" readonly="true"></vxe-input>
      </vxe-form-item>
     <vxe-form-item title="临时抛锚时间" field="toLsportTim" size="mini" span="8">
       <vxe-input v-model="dataForm.toLsportTim"  readonly="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="船长电话" field="captainTel" size="mini" span="8">
         <vxe-input v-model="dataForm.captainTel" readonly="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="代理公司" field="agentNam02" size="mini" span="8">
        <vxe-input v-model="dataForm.agentNam02" readonly="true"></vxe-input>
      </vxe-form-item>
       <vxe-form-item title="上报经办人" field="agentXingming" size="mini" span="8">
        <vxe-input v-model="dataForm.agentXingming"  readonly="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="上报人电话" field="agentPhone" size="mini" span="8">
        <vxe-input v-model="dataForm.agentPhone" readonly="true"></vxe-input>
      </vxe-form-item>
      
      


    </vxe-form>
    <div align="center">
      <el-button type="primary" size="mini" icon="el-icon-check" @click="handleSubmit">保存</el-button>
      <el-button size="mini" icon="el-icon-close" plain @click="closeWindows">关闭</el-button>
    </div>
  </div>
</template>

<script>
import { putObj } from "@/api/caccentre/ship";
import request from "@/router/axios";
import "@/styles/common/edit.scss";
import { mapGetters } from "vuex";
import EditDown from "@/views/baseUtile/EditDown";
import moment from "moment";
export default {
  props: ["datanow"],
  components: { EditDown },
  name: "caccentre-ship-indexshipeditCjh",
  data() {
    return {
      dataForm: { },
      formRules: {
        noDays: [{ required: true, message: "请输入免收天数" }]
      },
      downTableOption: {
       //单位
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
        //理货单位
        tallyNam: "tallyNam",
       tallyNamColum: [
          { field: "TALLY_COD", title: "单位代码" },
          { field: "TALLY_NAM", title: "单位名称" }
        ],
        tallyNamreturnApi: [
          { field: "TALLY_COD", returnName: "tallyCod" },
          { field: "TALLY_NAM", returnName: "tallyNam" }
        ],
        tallyNamurl: "/basecode/basecod/getTally",
        //港口
        prePortNam: "prePortNam",
       prePortNamColum: [
          { field: "TRANS_PORT_COD", title: "港口代码" },
          { field: "C_PORT_NAM", title: "港口名称" }
        ],
        prePortNamreturnApi: [
          { field: "TRANS_PORT_COD", returnName: "prePortCod" },
          { field: "C_PORT_NAM", returnName: "prePortNam" }
        ],
        prePortNamurl: "/basecode/basecod/portForDown",
        //来港
        fromPortNam: "fromPortNam",
        fromPortNamreturnApi: [
          { field: "TRANS_PORT_COD", returnName: "fromPortCod" },
          { field: "C_PORT_NAM", returnName: "fromPortNam" }
        ],
        prePortNamurl: "/basecode/basecod/portForDown",
        //往港
        toPortNam: "toPortNam",
        toPortNamreturnApi: [
          { field: "TRANS_PORT_COD", returnName: "toPortCod" },
          { field: "C_PORT_NAM", returnName: "toPortNam" }
        ],
        prePortNamurl: "/basecode/basecod/portForDown",
        //代理单位
        agentNam: "agentNam",
       agentNamColum: [
          { field: "AGENT_COD", title: "代理单位代码" },
          { field: "AGENT_NAM", title: "代理单位名称" }
        ],
        agentNamreturnApi: [
          { field: "AGENT_COD", returnName: "agentCod" },
          { field: "AGENT_NAM", returnName: "agentNam" }
        ],
        agentNamurl: "/basecode/basecod/getAgent",
        //进口货物
        inCargoNam: "inCargoNam",
        CargoNamColum: [
          { field: "CARGO_NAM_COD", title: "货名代码" },
          { field: "CARGO_NAM", title: "货名" },
        ],
        inCargoNamreturnApi: [
          { field: "CARGO_NAM_COD", returnName: "inCargoCod" },
          { field: "CARGO_NAM", returnName: "inCargoNam" }
          ],
        CargoNamurl: "/basecode/basecod/getCargoNamCod",
        //出口货物
        outCargoNam: "outCargoNam",
        outCargoNamreturnApi: [
          { field: "CARGO_NAM_COD", returnName: "outCargoCod" },
          { field: "CARGO_NAM", returnName: "outCargoNam" }
          ],
        //航线
        shipLineNam: "shipLineNam",
        shipLineNamColum: [
          { field: "SHIP_LINE_COD", title: "代理单位代码" },
          { field: "SHIP_LINE_NAM", title: "代理单位名称" }
        ],
        shipLineNamreturnApi: [
          { field: "SHIP_LINE_COD", returnName: "shipLineCod" },
          { field: "SHIP_LINE_NAM", returnName: "shipLineNam" }
        ],
        shipLineNamurl: "/basecode/basecod/shipLinedown",
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
        
      }
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() {
      this.dataForm = this.datanow; 
  },
  mounted: function() {
  
  },
  methods: {
    handleSubmit() {
       //this.dataForm.unitNam = this.dataForm.$unitNam;
       //this.dataForm.berthCod = this.dataForm.berthCodIndex;
       if (
        (this.dataForm.inCargoNam === null || this.dataForm.inCargoNam === "" ) &&
        (this.dataForm.outCargoNam === null || this.dataForm.outCargoNam === "" )
      ) {
        this.$alert("进出口货名不能同时为空!", "错误");
        return;
      }
      if (
        (this.dataForm.inShipCap == "" ||
          this.dataForm.inShipCap == null ||
          String(this.dataForm.inShipCap).trim() == "") &&
        (this.dataForm.outShipCap == "" ||
          this.dataForm.outShipCap == null ||
          String(this.dataForm.outShipCap).trim() == "")
      ) {
        this.$alert("进出口载货量不能同时为空！", "错误");
        return;
      }
      if (
        this.dataForm.inCargoNam !== null &&
        this.dataForm.inCargoNam.trim() !== "" &&
        (this.dataForm.inShipCap === "" ||
          this.dataForm.inShipCap === null ||
          String(this.dataForm.inShipCap).trim() == "")
      ) {
        this.$alert("进口载货量不能为空！", "错误");
        return;
      }
      if (
        this.dataForm.outCargoNam !== null &&
        this.dataForm.outCargoNam.trim() !== "" &&
        (this.dataForm.outShipCap === "" ||
          this.dataForm.outShipCap === null ||
          String(this.dataForm.outShipCap).trim() == "")
      ) {
        this.$alert("出口载货量不能为空！", "错误");
        return;
      }
      if (
        (this.dataForm.inCargoNam === null || this.dataForm.inCargoNam.trim() === "") &&
        this.dataForm.inShipCap !== null &&
        String(this.dataForm.inShipCap).trim() !== ""
      ) {
        this.$alert("进口货名不能为空！", "错误");
        return;
      }
      if (
        (this.dataForm.outCargoNam === null || this.dataForm.outCargoNam.trim() === "") &&
        this.dataForm.outShipCap !== null &&
        String(this.dataForm.outShipCap).trim() !== ""
      ) {
        this.$alert("出口货名不能为空！", "错误");
        return;
      }
      if (this.dataForm.inShipCap > 99999999.999 || this.dataForm.inShipCap < 0) {
        this.$alert("进口重量输入不合法！", "错误");
        return;
      }
      if (this.dataForm.outShipCap > 99999999.999 || this.dataForm.outShipCap < 0) {
        this.$alert("出口重量输入不合法！", "错误");
        return;
      }
      if (this.dataForm.arrivHeadWat > 999.99 || this.dataForm.arrivHeadWat < 0) {
        this.$alert("抵港艏吃水输入不合法！", "错误");
        return;
      }
      if (this.dataForm.arrivMidWat > 999.99 || this.dataForm.arrivMidWat < 0) {
        this.$alert("抵港中吃水输入不合法！", "错误");
        return;
      }
      if (this.dataForm.arrivTailWat > 999.99 || this.dataForm.arrivTailWat < 0) {
        this.$alert("抵港艉吃水输入不合法！", "错误");
        return;
      }
       if (this.dataForm.levHeadWat > 999.99 || this.dataForm.levHeadWat < 0) {
        this.$alert("离港艏吃水输入不合法！", "错误");
        return;
      }
      if (this.dataForm.levMidWat > 999.99 || this.dataForm.levMidWat < 0) {
        this.$alert("离港中吃水输入不合法！", "错误");
        return;
      }
      if (this.dataForm.levTailWat > 999.99 || this.dataForm.levTailWat < 0) {
        this.$alert("离港艉吃水输入不合法！", "错误");
        return;
      }
       if (
        this.dataForm.fromPortNam == "" ||
        this.dataForm.fromPortNam == null ||
        String(this.dataForm.fromPortNam).trim() == ""
      ) {
        this.$alert("来港不能为空!", "错误");
        return;
      }
      if (
        this.dataForm.toPortNam == "" ||
        this.dataForm.toPortNam == null ||
        String(this.dataForm.toPortNam).trim() == ""
      ) {
        this.$alert("往港不能为空!", "错误");
        return;
      }
      if (
        (this.dataForm.toBerthTim == "" ||
          this.dataForm.toBerthTim == null ||
          String(this.dataForm.toBerthTim).trim() == "") &&
        (this.dataForm.toPortTim == "" ||
          this.dataForm.toPortTim == null ||
          String(this.dataForm.toPortTim).trim() == "") &&
        (this.dataForm.toLsportTim == "" ||
          this.dataForm.toLsportTim == null ||
          String(this.dataForm.toLsportTim).trim() == "")
      ) {
        this.$alert(
          "船抵港，临时抛锚时间或抵锚时间和靠泊时间不能同时为空！",
          "错误"
        );
        return;
      }
      if (
        (this.dataForm.toBerthTim == "" ||
          this.dataForm.toBerthTim == null ||
          String(this.dataForm.toBerthTim).trim() == "") &&
        this.dataForm.toPortTim > this.dataForm.toBerthTim
      ) {
        this.$alert("计划靠泊时间应该大于计划抵锚时间！", "错误");
        return;
      }
        if (
        this.dataForm.toPortTim !== "" &&
        this.dataForm.toPortTim !== null &&
        String(this.dataForm.toPortTim).trim() !== "" &&
        (this.dataForm.toBerthTim == "" ||
          this.dataForm.toBerthTim == null ||
          String(this.dataForm.toBerthTim).trim() == "")
      ) {
        if (
          this.dataForm.berthCod === "" ||
          this.dataForm.berthCod == null ||
          String(this.dataForm.berthCod).trim() == ""
        ) {
          console.log("123::berthCod:" + this.dataForm.berthCod);
          this.$alert("船舶抵锚泊位不能为空！", "错误");
          return;
        }
        if (
          this.dataForm.berthCod !== "" &&
          this.dataForm.berthCod !== null &&
          String(this.dataForm.berthCod).trim() !== ""
        ) {
          if (String(this.dataForm.berthCod).indexOf("M") !== 0) {
            this.$alert("船舶抵锚泊位必须以M为开头！", "错误");
            return;
          }
        }
      }
      if (
        this.dataForm.toLsportTim !== "" &&
        this.dataForm.toLsportTim !== null &&
        String(this.dataForm.toLsportTim).trim() !== "" &&
        (this.dataForm.toBerthTim == "" ||
          this.dataForm.toBerthTim == null ||
          String(this.dataForm.toBerthTim).trim() == "")
      ) {
        if (
          this.dataForm.berthCod === "" ||
          this.dataForm.berthCod == null ||
          String(this.dataForm.berthCod).trim() == ""
        ) {
          console.log("123::berthCod:" + this.dataForm.berthCod);
          this.$alert("船舶临时下锚泊位不能为空！", "错误");
          return;
        }
        if (
          this.dataForm.berthCod !== "" &&
          this.dataForm.berthCod !== null &&
          String(this.dataForm.berthCod).trim() !== ""
        ) {
          if (String(this.dataForm.berthCod).indexOf("M") !== 0) {
            this.$alert("船舶临时下锚泊位必须以M为开头！", "错误");
            return;
          }
        }
      }
        if (
        this.dataForm.toBerthTim !== "" &&
        this.dataForm.toBerthTim !== null &&
        String(this.dataForm.toBerthTim).trim() == "" &&
        String(this.dataForm.berthCod).indexOf("M") === 0
      ) {
        console.log("1234:" + this.dataForm.toBerthTim);
        this.$alert("船已抵港，泊位不能是锚地！", "错误");
        return;
      }
      if (
        this.dataForm.toPortTim !== "" &&
        this.dataForm.toPortTim !== null &&
        String(this.dataForm.toPortTim).trim() !== "" &&
        (this.dataForm.toBerthTim == "" ||
          this.dataForm.toBerthTim == null ||
          String(this.dataForm.toBerthTim).trim() == "") &&
        (this.dataForm.berthCod == "" ||
          this.dataForm.berthCod == null ||
          String(this.dataForm.berthCod).trim() == "")
      ) {
        this.$alert("船舶抵港后泊位号不能空，请选择！", "错误");
        return;
      }
        
      this.$refs.editForm
        .validate(valid => {})
        .then(valid => {
          putObj(this.dataForm)
            .then(data => {
              this.$message.success("保存成功");
              this.$emit("selectList");
              this.$emit("dictItemVisiblenew");
            })
            .catch(() => {});
        });
    },
    closeWindows() {
      this.$emit("dictItemVisiblenew");
    },
     inputKeyPort(event) {
      if (event.$event.keyCode == 13) {
      this.inputChangePort(this.dataForm.toPortTim);
      } 
    },
    inputKeyLsport(event) {
      if (event.$event.keyCode == 13) {
      this.inputChangeLsport(this.dataForm.toLsportTim);
      } 
    },

    inputChangePort(value) {
      if (value != null) {
                let CsDATE = moment(value, 'YYYY-MM-DD HH:mm:ss', false).format();
                let Indate = moment(CsDATE)
                    .format("YYYY-MM-DD HH:mm:ss");
                    console.log(Indate);
                if(Indate =="Invalid date"){
                  this.$alert("抵锚时间" + value + "格式输入错误！", "错误");
                  this.dataForm.toPortTim = null;
                }
                else{
                  this.dataForm.toPortTim = Indate;
                  
                  if (value != null) {
                    let Ldate = moment()
                      .subtract(1, "year")
                      .format("YYYY-MM-DD HH:mm:ss");
                    if (moment(new Date()) < moment(value)) {
                     this.dataForm.toPortTim = null;
                      this.$alert("抵锚时间应该小于当前时间！", "错误");
                    }
                    if (moment(Ldate) > moment(value)) {
                      this.dataForm.toPortTim = null;
                      this.$alert("抵锚时间应该大于" + Ldate + "！", "错误");
                    }
                  }
                }
              }
    },
    inputChangeLsport(value) {
      if (value != null) {
                let CsDATE = moment(value, 'YYYY-MM-DD HH:mm:ss', false).format();
                let Indate = moment(CsDATE)
                    .format("YYYY-MM-DD HH:mm:ss");
                    console.log(Indate);
                if(Indate =="Invalid date"){
                  this.$alert("临时抛锚时间" + value + "格式输入错误！", "错误");
                  this.dataForm.toLsportTim = null;
                }
                else{
                  this.dataForm.toLsportTim = Indate;
                  
                  if (value != null) {
                    let Ldate = moment()
                      .subtract(1, "year")
                      .format("YYYY-MM-DD HH:mm:ss");

                    if (moment(new Date()) < moment(value)) {
                     this.dataForm.toLsportTim = null;
                      this.$alert("临时抛锚应该小于当前时间！", "错误");
                    }
                    if (moment(Ldate) > moment(value)) {
                      this.dataForm.toLsportTim = null;
                      this.$alert("临时抛锚应该大于" + Ldate + "！", "错误");
                    }
                  }
                }
              }
    },
  }
};
</script>
<style >
</style>