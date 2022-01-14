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
  <div class="zyworkgather-editTrainFourth">
    <vxe-form :data="dataForm">
      <el-divider content-position="center">
        白班
      </el-divider>
      <vxe-form-item title="进站列数" field="zyAInNum" size="mini" span="12">
        <vxe-input v-model="dataForm.zyAInNum"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="出站列数" field="zyAOutNum" size="mini" span="12">
        <vxe-input v-model="dataForm.zyAOutNum"></vxe-input>
      </vxe-form-item>
    </vxe-form>
    <vxe-form :data="dataForm">
      <el-divider content-position="center">
        夜班
      </el-divider>

      <vxe-form-item title="进站列数" field="zyPInNum" size="mini" span="12">
        <vxe-input v-model="dataForm.zyPInNum"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="出站列数" field="zyPOutNum" size="mini" span="12">
        <vxe-input v-model="dataForm.zyPOutNum"></vxe-input>
      </vxe-form-item>
    </vxe-form>
    <vxe-form :data="dataForm">
      <el-divider content-position="center">
        保有量
      </el-divider>
      <vxe-form-item title="外站车数" field="zyWzCarNum" size="mini" span="8">
        <vxe-input v-model="dataForm.zyWzCarNum"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="港内车数" field="zyGnCarNum" size="mini" span="8">
        <vxe-input v-model="dataForm.zyGnCarNum"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="排空" field="zyPPkNum" size="mini" span="8">
        <vxe-input v-model="dataForm.zyPPkNum"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="备注" field="zyWorkRemark" size="mini" span="24">
        <el-input
          v-model="dataForm.zyWorkRemark"
          type="textarea"
          :autosize="{ minRows: 4, maxRows: 10 }"
        ></el-input>
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
import {
  getTrainFourthObject,
  putObjTrainFourth
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
  props: ["dateId"],
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
    getTrainFourthObject(this.dateId)
      .then(response => {
        this.dataForm = response.data.data;
      })
      .catch(() => {
        this.$emit("dictItemVisible");
      });
  },
  methods: {
    handleSubmit() {
      putObjTrainFourth(this.dataForm)
        .then(data => {
          this.$message.success("保存成功");
          console.log(this.dataForm);
          this.$emit("selectList", this.dataForm.zySeqNo);
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
<style lang="scss" scoped>
.zyworkgather-editTrainFourth {
  .el-divider--horizontal {
    margin: 5px 0;
  }
}
</style>