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
  <div class="caccentre-basecode-edit">
    <vxe-form :data="dataForm" title-width="100" :rules="formRules" ref="editForm">
       <vxe-form-item title="泊位号" field="berthCod" size="mini" span="8">
        <vxe-input v-model="dataForm.berthCod"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="泊位名称" field="berthNam" size="mini" span="8">
        <vxe-input v-model="dataForm.berthNam"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="泊位(上传)" field="berthCodNew" size="mini" span="8">
        <vxe-input v-model="dataForm.berthCodNew"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="靠泊能力" field="berthCapa" size="mini" span="8">
        <vxe-input v-model="dataForm.berthCapa"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="长度" field="berthLen" size="mini" span="8">
        <vxe-input v-model="dataForm.berthLen"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="前水深" field="berthFDeep" size="mini" span="8">
        <vxe-input v-model="dataForm.berthFDeep"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="中水深" field="berthMDeep" size="mini" span="8">
        <vxe-input v-model="dataForm.berthMDeep"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="后水深" field="berthBDeep" size="mini" span="8">
        <vxe-input v-model="dataForm.berthBDeep"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="平均水深" field="berthDeep" size="mini" span="8">
        <vxe-input v-model="dataForm.berthDeep"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="缆桩范围" field="berthBittRang" size="mini" span="8">
        <vxe-input v-model="dataForm.berthBittRang"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="缆桩距离" field="berthBittDist" size="mini" span="8">
        <vxe-input v-model="dataForm.berthBittDist"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="泊位性质" field="berthKnd" size="mini" span="8">
        <vxe-input v-model="dataForm.berthKnd"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="泊位用途" field="berthUse" size="mini" span="8">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.berthUse"
          :tableColumns="downTableOption.berthUseColum"
          :urlapi="downTableOption.berthUseurl"
          :returnApi="downTableOption.berthUsereturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="货类代码" field="cargoKindName" size="mini" span="8">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.cargoKindName"
          :tableColumns="downTableOption.cargoKindNameColum"
          :urlapi="downTableOption.cargoKindNameurl"
          :returnApi="downTableOption.cargoKindNamereturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="服务类型" field="serveTypName" size="mini" span="8">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.serveTypName"
          :tableColumns="downTableOption.serveTypNameColum"
          :urlapi="downTableOption.serveTypNameurl"
          :returnApi="downTableOption.serveTypNamereturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="结构类型" field="berthTypName" size="mini" span="8">
         <EditDown
          :objs="dataForm"
          :property="downTableOption.berthTypName"
          :tableColumns="downTableOption.berthTypColum"
          :urlapi="downTableOption.berthTypurl"
          :returnApi="downTableOption.berthTypreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
       <vxe-form-item title="X坐标" field="xpos" size="mini" span="8">
        <vxe-input v-model="dataForm.xpos" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="Y坐标" field="ypos" size="mini" span="8">
        <vxe-input v-model="dataForm.ypos" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="泊位标识" field="berthId" size="mini" span="8">
        <vxe-input v-model="dataForm.berthId"></vxe-input>
      </vxe-form-item>
     <vxe-form-item title="单位名称" field="unitNam" size="mini" span="8">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.unitNam"
          :tableColumns="downTableOption.unitColum"
          :urlapi="downTableOption.uniturl"
          :returnApi="downTableOption.unitreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="港区" field="portArea" size="mini" span="8">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.portArea"
          :tableColumns="downTableOption.portAreaColum"
          :urlapi="downTableOption.portAreaurl"
          :returnApi="downTableOption.portAreareturnApi"
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
import { putObj } from "@/api/caccentre/cberth";
import request from "@/router/axios";
import "@/styles/common/edit.scss";
import { mapGetters } from "vuex";
import EditDown from "@/views/baseUtile/EditDown";
export default {
  props: ["datanow"],
  components: { EditDown },
  name: "caccentre-basecode-edit",
  data() {
    return {
      dataForm: { cargoKindCod: null, cargoKindName: null ,unitCod:null,unitNam:null,berthUse: null, serveTypName: null ,berthTypName:null,portAreaCod:null,portArea:null},
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
        cargoKindName: "cargoKindName",
        cargoKindNameColum: [
          { field: "CARGO_KIND_COD", title: "货名代码" },
          { field: "CARGO_KIND_NAM", title: "货名" },
        ],
        cargoKindNamereturnApi: [
          { field: "CARGO_KIND_COD", returnName: "cargoKindCod" },
          { field: "CARGO_KIND_NAM", returnName: "cargoKindName" }
          ],
        cargoKindNameurl: "/basecode/basecod/getCargoKind",
        //泊位用途C_Berth_Typ
        berthUse: "berthUse",
        berthUseColum: [
          { field: "BERTH_TYP_COD", title: "用途代码" },
          { field: "BERTH_TYP", title: "泊位用途" },
        ],
        berthUsereturnApi: [
          { field: "BERTH_TYP_COD", returnName: "berthTypCod" },
          { field: "BERTH_TYP", returnName: "berthUse" }
          ],
        berthUseurl: "/basecode/basecod/getCberthTyp?markId=2",
        //服务类型C_Berth_Typ
        serveTypName: "serveTypName",
        serveTypNameColum: [
          { field: "BERTH_TYP_COD", title: "代码" },
          { field: "BERTH_TYP", title: "服务类型" },
        ],
        serveTypNamereturnApi: [
          { field: "BERTH_TYP_COD", returnName: "serveTyp" },
          { field: "BERTH_TYP", returnName: "serveTypName" }
          ],
        serveTypNameurl: "/basecode/basecod/getCberthTyp?markId=0",
        //结构类型C_Berth_Typ
        berthTypName: "berthTypName",
        berthTypColum: [
          { field: "BERTH_TYP_COD", title: "代码" },
          { field: "BERTH_TYP", title: "服务类型" },
        ],
        berthTypreturnApi: [
          { field: "BERTH_TYP_COD", returnName: "berthTyp" },
          { field: "BERTH_TYP", returnName: "berthTypName" }
          ],
        berthTypurl: "/basecode/basecod/getCberthTyp?markId=1",
        //港区
        portArea: "portArea",
        portAreaColum: [
            { field: "PORT_AREA_COD", title: "港区代码" },
            { field: "PORT_AREA", title: "港区名称" }
          ],
          portAreareturnApi: [
            { field: "PORT_AREA_COD", returnName: "portAreaCod" },
            { field: "PORT_AREA", returnName: "portArea" }
          ],
          portAreaurl: "/basecode/basecod/getPortArea",
      }
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() {
      if(this.datanow.berthCod!=null){ 
      this.dataForm = this.datanow; 
    }else{
     
     }
    ;
  },
  mounted: function() {
  
  },
  methods: {
    handleSubmit() {
       if (
            this.dataForm.berthCod == null 
          ) {
            this.$message.error("泊位代码不能为空！");
            return;
          }
        if (
            this.dataForm.berthNam == null 
          ) {
            this.$alert("泊位名称不能为空！", "错误");
            return;
          }
        if (
            this.dataForm.unitNam == null 
          ) {
            this.$alert("单位名称不能为空！", "错误");
            return;
          }
        if (
            this.dataForm.portArea == null 
          ) {
            this.$alert("港区名称不能为空！", "错误");
            return;
          }
      this.$refs.editForm
        .validate(valid => {})
        .then(valid => {
          putObj(this.dataForm)
            .then(data => {
              this.$message.success("保存成功");
              this.$emit("selectListplan");
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