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
  <div>
    <vxe-form
      :data="dataForm"
      title-width="170"
      :rules="formRules"
      ref="editHeadForm"
    >
      <vxe-form-item
        title="理货车辆"
        field="tallycarName"
        size="mini"
        span="24"
      >
        <EditDown
          :objs="dataForm"
          property="tallycarName"
          :tableColumns="downTableOption.tallycarColum"
          :urlapi="downTableOption.tallycarurl"
          :returnApi="downTableOption.tallycarreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item
        title="理货车辆ID"
        field="tallycarId"
        size="mini"
        span="24"
      >
        <vxe-input v-model="dataForm.tallycarId" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="指令号" field="etCheckSeq" size="mini" span="24">
         <EditDown
          :objs="dataForm"
          property="etCheckSeq"
          :tableColumns="downTableOption.etCheckSeqColum"
          :urlapi="downTableOption.etCheckSequrl"
          :returnApi="downTableOption.etCheckSeqreturnApi"
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
import { editOne } from "@/api/roadapt/tallycar/workbase";
import EditDown from "@/views/baseUtile/EditDown";
import "@/styles/common/edit.scss";
import { mapGetters } from "vuex";
export default {
    components: { EditDown },
  props: ["datanow"],
  data() {
    return {
      dataForm: { tallycarName: null, tallycarId: null, etCheckSeq: null },
      formRules: {
        tallycarName: [{ required: true, message: "请选择理货车" }],
        tallycarId: [{ required: true, message: "请选择理货车" }],
        etCheckSeq: [{ required: true, message: "请选择指令" }]
      },
      downTableOption: {
        //理货车
        tallycarColum: [
          { field: "TALLYCAR_ID", title: "理货车ID" },
          { field: "TALLYCAR_NAME", title: "理货车名称" }
        ],
        tallycarreturnApi: [
          { field: "TALLYCAR_ID", returnName: "tallycarId" },
          { field: "TALLYCAR_NAME", returnName: "tallycarName" }
        ],
        tallycarurl: "/roadapt/baseCod/getTallyCar",
        //指令
        etCheckSeqColum: [
          { field: "ET_CHECK_SEQ", title: "指令号" },
          { field: "SHIP_NAM", title: "船名" },
           { field: "SHIPPER_DOC", title: "货主" },
        ],
        etCheckSeqreturnApi: [
          { field: "ET_CHECK_SEQ", returnName: "etCheckSeq" }
        ],
        etCheckSequrl: "/roadapt/baseCod/getEtWorkBase",
      }
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() { if (this.datanow != null) {
      this.dataForm = this.datanow;
    }},
  mounted: function() {
   
  },
  methods: {
    handleSubmit() {
      this.$refs.editHeadForm
        .validate(valid => {})
        .then(valid => {
          editOne(this.dataForm)
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
<style lang="scss">
</style>