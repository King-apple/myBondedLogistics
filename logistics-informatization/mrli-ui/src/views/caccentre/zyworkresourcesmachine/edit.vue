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
  <div class="zyworkresourcesmachineedit">
    <vxe-form :data="dataForm" title-width="80">
      <el-divider content-position="center"
        >3吨叉车、6吨叉车、8吨叉车、10吨叉车</el-divider
      >
      <vxe-form-item title="出勤数" field="oneCq" size="mini" span="12">
        <vxe-input v-model="dataForm.oneCq" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="加班人数" field="oneJb" size="mini" span="12">
        <vxe-input v-model="dataForm.oneJb" type="number"></vxe-input>
      </vxe-form-item>
    </vxe-form>
    <vxe-form :data="dataForm" title-width="80">
      <el-divider content-position="center"
        >16吨叉车、18吨叉车、20吨叉车、25吨叉车</el-divider
      >
      <vxe-form-item title="出勤数" field="twoCq" size="mini" span="12">
        <vxe-input v-model="dataForm.twoCq" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="加班人数" field="twoJb" size="mini" span="12">
        <vxe-input v-model="dataForm.twoJb" type="number"></vxe-input>
      </vxe-form-item>
    </vxe-form>
    <vxe-form :data="dataForm" title-width="80">
      <el-divider content-position="center"
        >28吨叉车、35吨叉车、42吨叉车</el-divider
      >
      <vxe-form-item title="出勤数" field="threeCq" size="mini" span="12">
        <vxe-input v-model="dataForm.threeCq" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="加班人数" field="threeJb" size="mini" span="12">
        <vxe-input v-model="dataForm.threeJb" type="number"></vxe-input>
      </vxe-form-item>
    </vxe-form>
    <vxe-form :data="dataForm" title-width="80">
      <el-divider content-position="center">25吨吊车</el-divider>
      <vxe-form-item title="出勤数" field="fourCq" size="mini" span="12">
        <vxe-input v-model="dataForm.fourCq" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="加班人数" field="fourJb" size="mini" span="12">
        <vxe-input v-model="dataForm.fourJb" type="number"></vxe-input>
      </vxe-form-item>
    </vxe-form>
    <vxe-form :data="dataForm" title-width="80">
      <el-divider content-position="center">40吨吊车、65吨吊车</el-divider>
      <vxe-form-item title="出勤数" field="fifeCq" size="mini" span="12">
        <vxe-input v-model="dataForm.fifeCq" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="加班人数" field="fifeJb" size="mini" span="12">
        <vxe-input v-model="dataForm.fifeJb" type="number"></vxe-input>
      </vxe-form-item>
    </vxe-form>
    <vxe-form :data="dataForm" title-width="80">
      <el-divider content-position="center">50吨吊车、60吨吊车</el-divider>
      <vxe-form-item title="出勤数" field="sixCq" size="mini" span="12">
        <vxe-input v-model="dataForm.sixCq" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="加班人数" field="sixJb" size="mini" span="12">
        <vxe-input v-model="dataForm.sixJb" type="number"></vxe-input>
      </vxe-form-item>
    </vxe-form>
    <vxe-form :data="dataForm" title-width="80">
      <el-divider content-position="center">抓煤机</el-divider>
      <vxe-form-item title="出勤数" field="sevenCq" size="mini" span="12">
        <vxe-input v-model="dataForm.sevenCq" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="加班人数" field="sevenJb" size="mini" span="12">
        <vxe-input v-model="dataForm.sevenJb" type="number"></vxe-input>
      </vxe-form-item>
    </vxe-form>
    <vxe-form :data="dataForm" title-width="80">
      <el-divider content-position="center">挖掘机（铲斗）</el-divider>
      <vxe-form-item title="出勤数" field="eightCq" size="mini" span="12">
        <vxe-input v-model="dataForm.eightCq" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="加班人数" field="eightJb" size="mini" span="12">
        <vxe-input v-model="dataForm.eightJb" type="number"></vxe-input>
      </vxe-form-item>
    </vxe-form>
    <vxe-form :data="dataForm" title-width="80">
      <el-divider content-position="center">挖掘机（串杆）、码垛机</el-divider>
      <vxe-form-item title="出勤数" field="nineCq" size="mini" span="12">
        <vxe-input v-model="dataForm.nineCq" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="加班人数" field="nineJb" size="mini" span="12">
        <vxe-input v-model="dataForm.nineJb" type="number"></vxe-input>
      </vxe-form-item>
    </vxe-form>
    <vxe-form :data="dataForm" title-width="80">
      <el-divider content-position="center">推土机</el-divider>
      <vxe-form-item title="出勤数" field="tenCq" size="mini" span="12">
        <vxe-input v-model="dataForm.tenCq" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="加班人数" field="tenJb" size="mini" span="12">
        <vxe-input v-model="dataForm.tenJb" type="number"></vxe-input>
      </vxe-form-item>
    </vxe-form>
    <vxe-form :data="dataForm" title-width="80">
      <el-divider content-position="center">平仓机</el-divider>
      <vxe-form-item title="出勤数" field="elevenCq" size="mini" span="12">
        <vxe-input v-model="dataForm.elevenCq" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="加班人数" field="elevenJb" size="mini" span="12">
        <vxe-input v-model="dataForm.elevenJb" type="number"></vxe-input>
      </vxe-form-item>
    </vxe-form>
    <vxe-form :data="dataForm" title-width="80">
      <el-divider content-position="center">抓钢机</el-divider>
      <vxe-form-item title="出勤数" field="twelveCq" size="mini" span="12">
        <vxe-input v-model="dataForm.twelveCq" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="加班人数" field="twelveJb" size="mini" span="12">
        <vxe-input v-model="dataForm.twelveJb" type="number"></vxe-input>
      </vxe-form-item>
    </vxe-form>
    <vxe-form :data="dataForm" title-width="80">
      <el-divider content-position="center">门机</el-divider>
      <vxe-form-item title="出勤数" field="thirteenCq" size="mini" span="12">
        <vxe-input v-model="dataForm.thirteenCq" type="number"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="加班人数" field="thirteenJb" size="mini" span="12">
        <vxe-input v-model="dataForm.thirteenJb" type="number"></vxe-input>
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
import { getTjObj, saveTj } from "@/api/caccentre/zyworkresourcesmachine";
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
      dataForm: {},
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  created() {},
  mounted: function () {
    getTjObj(this.datenow)
      .then((response) => {
        this.dataForm = response.data.data;
      })
      .catch(() => {
        this.$emit("dictItemVisible");
      });
  },
  methods: {
    handleSubmit() {
      saveTj(
        Object.assign(
          {
            datenow: this.datenow,
          },
          this.dataForm
        )
      )
        .then((data) => {
          this.$message.success("保存成功");
          this.$emit("selectList");
          this.$emit("dictItemVisible");
        })
        .catch(() => {});
    },
    closeWindows() {
      this.$emit("dictItemVisible");
    },
  },
};
</script>
<style lang="scss" scoped>
.zyworkresourcesmachineedit {
  .el-divider--horizontal {
    margin: 5px 0;
  }
}
</style>