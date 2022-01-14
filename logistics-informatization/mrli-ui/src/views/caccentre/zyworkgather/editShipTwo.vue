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
  <div class="zyworkgather-editShipTwo">
    <vxe-form :data="dataForm">
      <vxe-form-item title="公司名称" field="zyUnitName" size="mini" span="24">
        <vxe-input v-model="dataForm.zyUnitName" readonly></vxe-input>
      </vxe-form-item>
       <vxe-form-item title="公司类型" field="zyWorkType" size="mini" span="8">
        <vxe-input v-model="dataForm.zyWorkType" readonly></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="在泊船数量" field="zyAtberthShip" size="mini" span="8">
        <vxe-input v-model="dataForm.zyAtberthShip" readonly></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="开工路数数量" field="zyWorkNumber" size="mini" span="8">
        <vxe-input v-model="dataForm.zyWorkNumber"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="备注" field="zyWorkRemark" size="mini" span="24">
        <el-input
          v-model="dataForm.zyWorkRemark"
          type="textarea"
          :autosize="{ minRows: 4, maxRows: 10}"
        ></el-input>
      </vxe-form-item>
    </vxe-form>
    <div align="center">
      <el-button type="primary" size="mini" icon="el-icon-check" @click="handleSubmit">保存</el-button>
      <el-button size="mini" icon="el-icon-close" plain @click="closeWindows">取消</el-button>
    </div>
  </div>
</template>

<script>
import {
  putObjShipTwo,
} from "@/api/caccentre/zyworkgather";
import request from "@/router/axios";
import "@/styles/common/edit.scss";
import { mapGetters } from "vuex";
import moment from "moment";
import Vue from "vue";
import VXETable from "vxe-table";
import hljs from "highlight.js";
import { Loading, Message } from "element-ui";
export default {
  props: ["datanow"],
  data() {
    return {
      dataForm: {}
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() {},
  mounted: function() {
   this.dataForm=this.datanow;
  },
  methods: {
    handleSubmit() {
      putObjShipTwo(this.dataForm)
        .then(data => {
          this.$message.success("保存成功");
          console.log(this.dataForm)
          this.$emit("selectList",this.dataForm.zySeqNo);
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
<style >
</style>