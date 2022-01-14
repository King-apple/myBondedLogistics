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
  <div class="zyworkgather-edit">
    <vxe-form :data="dataForm">
      <vxe-form-item title="值班主任" field="zyMasterMan" size="mini" span="12">
        <vxe-input v-model="dataForm.zyMasterMan"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="调度员" field="zyDispatcherMan" size="mini" span="12">
        <vxe-input v-model="dataForm.zyDispatcherMan"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="气象情况" field="zyWeatherTxt" size="mini" span="24">
        <el-input
          v-model="dataForm.zyWeatherTxt"
          type="textarea"
          :autosize="{ minRows: 4, maxRows: 10}"
        ></el-input>
      </vxe-form-item>
    </vxe-form>
    <vxe-form :data="dataForm" title-width="200px">
      <vxe-form-item title="昼夜进出港情况" field="zyIoportStatus" size="mini" span="24">
        <el-input
          v-model="dataForm.zyIoportStatus"
          type="textarea"
          :autosize="{ minRows: 3, maxRows: 10}"
        ></el-input>
      </vxe-form-item>
      <vxe-form-item title="船舶计划兑现率考核" field="zySplanCheck" size="mini" span="24">
        <el-input
          v-model="dataForm.zySplanCheck"
          type="textarea"
          :autosize="{ minRows: 3, maxRows: 10}"
        ></el-input>
      </vxe-form-item>
      <vxe-form-item title="船舶生产组织考核" field="zySworkCheck" size="mini" span="24">
        <el-input
          v-model="dataForm.zySworkCheck"
          type="textarea"
          :autosize="{ minRows: 3, maxRows: 10}"
        ></el-input>
      </vxe-form-item>
      <vxe-form-item title="火车计划兑现率考核" field="zyTplanCheck" size="mini" span="24">
        <el-input
          v-model="dataForm.zyTplanCheck"
          type="textarea"
          :autosize="{ minRows: 3, maxRows: 10}"
        ></el-input>
      </vxe-form-item>
      <vxe-form-item title="上昼夜生产情况" field="zyLastStatus" size="mini" span="24">
        <el-input
          v-model="dataForm.zyLastStatus"
          type="textarea"
          :autosize="{ minRows: 3, maxRows: 10}"
        ></el-input>
      </vxe-form-item>
      <vxe-form-item title="本昼夜生产重点" field="zyWorkKeypoint" size="mini" span="24">
        <el-input
          v-model="dataForm.zyWorkKeypoint"
          type="textarea"
          :autosize="{ minRows: 3, maxRows: 10}"
        ></el-input>
      </vxe-form-item>
      <vxe-form-item title="生产亮点" field="zyWorkHighlight" size="mini" span="24">
        <el-input
          v-model="dataForm.zyWorkHighlight"
          type="textarea"
          :autosize="{ minRows: 3, maxRows: 10}"
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
  putObj,
  getObjByDate,
  getFistList,
  dealHead,
  dealfinish
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
  props: ["datenow"],
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
    getObjByDate(Object.assign({ date: this.datenow }))
      .then(response => {
        this.dataForm = response.data.data;     
      })
      .catch(() => {
       this.$emit("dictItemVisible");
      });
  },
  methods: {
    handleSubmit() {
       putObj(this.dataForm)
        .then(data => {
          this.$message.success("保存成功");
          this.$emit("selectList");
           this.$emit("dictItemVisible");
        })
        .catch(() => {
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