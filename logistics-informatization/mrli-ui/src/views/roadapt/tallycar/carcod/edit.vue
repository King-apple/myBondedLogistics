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
  <div >
    <vxe-form
      :data="dataForm"
      title-width="170"
      :rules="formRules"
      ref="editHeadForm"
    >
      <vxe-form-item title="理货车辆名称" field="tallycarName" size="mini" span="24">
        <vxe-input v-model="dataForm.tallycarName"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="理货车辆ID" field="tallycarId" size="mini" span="24">
        <vxe-input v-model="dataForm.tallycarId"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="状态" field="status" size="mini" span="24">
          <vxe-select v-model="dataForm.status" placeholder="请选择">
          <vxe-option :value="1" label="启用"></vxe-option>
          <vxe-option :value="0" label="停用"></vxe-option>
        </vxe-select>
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
import { editOne } from "@/api/roadapt/tallycar/carcod";
import store from "@/store";
import "@/styles/common/edit.scss";
import { mapGetters } from "vuex";
export default {
  props: ["datanow"],
  data() {
    return {
      dataForm: { },
      formRules: {
        tallycarName: [{ required: true, message: "请输入理货车名称" }],
        tallycarId: [{ required: true, message: "请输入理货车ID" }],
        status: [{ required: true, message: "请选择状态" }],
      },
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() {},
  mounted: function() {
    if (this.datanow != null) {
      this.dataForm = this.datanow;
    }
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
    },
   
  }
};
</script>
<style lang="scss">

</style>