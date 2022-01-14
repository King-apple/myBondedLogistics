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
  <div class="portside-aboardwaixie-addoredit">
    <vxe-form :data="dataForm" title-width="100" :rules="formRules" ref="editHeadForm">
      <vxe-form-item title="人名" field="manName" size="mini" span="24">
        <vxe-input v-model="dataForm.manName" ></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="身份证" field="manId" size="mini" span="24">
        <vxe-input v-model="dataForm.manId"></vxe-input>
      </vxe-form-item>
       <vxe-form-item title="联系方式" field="manPhone" size="mini" span="24">
        <vxe-input v-model="dataForm.manPhone"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="性别" field="sex" size="mini" span="24">
        <vxe-select v-model="dataForm.sex" placeholder="请选择">
            <vxe-option value="1" label="男"></vxe-option>
            <vxe-option value="0" label="女"></vxe-option>
          </vxe-select>
      </vxe-form-item>
      <vxe-form-item title="登船证" field="manAboardId" size="mini" span="24">
        <vxe-input v-model="dataForm.manAboardId" ></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="岗位" field="adminTyp" size="mini" span="24">
        <vxe-input v-model="dataForm.adminTyp" ></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="部门" field="subunitNam" size="mini" span="24">
        <vxe-input v-model="dataForm.subunitNam" ></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="外协公司名称" field="waixieUnitName" size="mini" span="24">
        <vxe-input v-model="dataForm.waixieUnitName"></vxe-input>
      </vxe-form-item>
     
    </vxe-form>
    <div align="center">
      <el-button type="primary" size="mini" icon="el-icon-check" @click="handleSubmit">保存</el-button>
      <el-button size="mini" icon="el-icon-close" plain @click="closeWindows">取消</el-button>
    </div>
  </div>
</template>

<script>
import { addObj } from "@/api/portside/aboardwaixie";
import request from "@/router/axios";
import "@/styles/common/edit.scss";
import { mapGetters } from "vuex";

export default {
  props: ["datanow"],
  data() {
    return {
      dataForm: {},
      formRules: {
        manName: [{ required: true, message: "请输入姓名" }],
        manId: [{ required: true, message: "请输入身份证" }],
        manPhone: [{ required: true, message: "请输入联系方式" }],
        sex: [{ required: true, message: "请选择性别" }],
        manAboardId: [{ required: true, message: "请输入登船证号" },
         ]
      },
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() {},
  mounted: function() {
    if(this.datanow!=null){
      this.dataForm = this.datanow;
    }
  },
  methods: {
    handleSubmit() {
      this.$refs.editHeadForm.validate(valid => {
      }).then(valid=>{
         addObj(this.dataForm)
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
<style lang="scss" scoped>
.portside-aboardwaixie-addoredit {
  .el-tag {
    width: 100%;
  }
  .el-divider--horizontal {
    margin: 10px 0;
  }
  .vxe-toolbar {
    height: 25px;
  }
}
</style>