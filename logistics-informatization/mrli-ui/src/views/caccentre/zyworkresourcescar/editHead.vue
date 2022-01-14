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
      <vxe-form-item title="日期" field="zyWorkDte" size="mini" span="24">
        <vxe-input
          v-model="dataForm.zyWorkDte"
          placeholder="请选择日期"
          valueFormat="yyyy-MM-dd"
          type="date"
        ></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="装载机在册" field="zyZzjNum" size="mini" span="12">
        <vxe-input v-model="dataForm.zyZzjNum" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="装载机出勤" field="zyZzjWokrNum" size="mini" span="12">
        <vxe-input v-model="dataForm.zyZzjWokrNum" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="自卸车在册" field="zyZxcNum" size="mini" span="12">
        <vxe-input v-model="dataForm.zyZxcNum" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="自卸车出勤" field="zyZxcWorkNum" size="mini" span="12">
        <vxe-input v-model="dataForm.zyZxcWorkNum" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="挂车在册" field="zyGcNum" size="mini" span="12">
        <vxe-input v-model="dataForm.zyGcNum" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="挂车出勤" field="zyGcWorkNum" size="mini" span="12">
        <vxe-input v-model="dataForm.zyGcWorkNum" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="美国鹰在册" field="zyMgyNum" size="mini" span="12">
        <vxe-input v-model="dataForm.zyMgyNum" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="美国鹰出勤" field="zyMgyWorkNum" size="mini" span="12">
        <vxe-input v-model="dataForm.zyMgyWorkNum" type="number"></vxe-input>
      </vxe-form-item>
    </vxe-form>
    <div align="center">
      <el-button type="primary" size="mini" icon="el-icon-check" @click="handleSubmit">保存</el-button>
      <el-button size="mini" icon="el-icon-close" plain @click="closeWindows">取消</el-button>
    </div>
  </div>
</template>

<script>
import { putObj } from "@/api/caccentre/zyworkresourcescar";
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
      dataForm: {},
      formRules: {
        zyWorkDte: [{ required: true, message: "请选择时间" }],
        zyZzjNum: [{ required: true, message: "请输入装载机在册" }],
        // zyZzjWokrNum: [{ required: true, message: "请输入装载机出勤" }],
        zyZxcNum: [{ required: true, message: "请输入自卸车在册" }],
        // zyZxcWorkNum: [{ required: true, message: "请输入自卸车出勤" }],
        zyGcNum: [{ required: true, message: "请输入挂车在册" }],
        //zyGcWorkNum: [{ required: true, message: "请输入挂车出勤" }],
        zyMgyNum: [{ required: true, message: "请输入美国鹰在册" }]
        //zyMgyWorkNum: [{ required: true, message: "请输入美国鹰出勤" }]
      }
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() {},
  mounted: function() {
    this.dataForm = this.datanow;
  },
  methods: {
    handleSubmit() {
      this.$refs.editHeadForm.validate(valid => {
      }).then(valid=>{
         putObj(this.dataForm)
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