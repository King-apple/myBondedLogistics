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
  <div class="caccentre-shipagent-addedit">
    <vxe-form :data="dataForm" title-width="100" :rules="formRules" ref="editForm">
       <vxe-form-item title="代码" field="agentCod" size="mini" span="12">
        <vxe-input v-model="dataForm.agentCod"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="代理名称" field="agentNam" size="mini" span="12">
        <vxe-input v-model="dataForm.agentNam"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="英文名称" field="engAgentNam" size="mini" span="12">
        <vxe-input v-model="dataForm.engAgentNam"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="代理简称" field="agentShort" size="mini" span="12">
        <vxe-input v-model="dataForm.agentShort"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="法人代表" field="corporation" size="mini" span="12">
        <vxe-input v-model="dataForm.corporation"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="电话" field="telephone" size="mini" span="12">
        <vxe-input v-model="dataForm.telephone"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="E_Mail" field="eMail" size="mini" span="12">
        <vxe-input v-model="dataForm.eMail"></vxe-input>
      </vxe-form-item>
     

    </vxe-form>
    <div align="center">
      <el-button type="primary" size="mini" icon="el-icon-check" @click="handleSubmit">保存</el-button>
      <el-button size="mini" icon="el-icon-close" plain @click="closeWindows">取消</el-button>
    </div>
  </div>
</template>

<script>
import { addObj } from "@/api/caccentre/shipagent";
import request from "@/router/axios";
import "@/styles/common/edit.scss";
import { mapGetters } from "vuex";
import EditDown from "@/views/baseUtile/EditDown";
export default {
  props: ["datanow"],
  components: { EditDown },
  name: "caccentre-shipagent-addedit",
  data() {
    return {
      dataForm: { },
      formRules: {
        noDays: [{ required: true, message: "请输入免收天数" }]
      },

    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() {
      if(this.datanow.seqNo!=null){ 
      this.dataForm = this.datanow; 
    }else{
     
     }
    ;
  },
  mounted: function() {
  
  },
  methods: {
    handleSubmit() {
      console.log(this.dataForm.agentCod);
      console.log(this.dataForm.agentNam);
       if (
            this.dataForm.agentCod == null 
          ) {
            this.$message.error("代码不能为空！");
            return;
          }
        if (
            this.dataForm.agentNam == null 
          ) {
            this.$alert("代理名称不能为空！", "错误");
            return;
          }
        if (
            this.dataForm.agentShort == null 
          ) {
            this.$alert("代理简称不能为空！", "错误");
            return;
          }
        if (
            this.dataForm.telephone == null 
          ) {
            this.$alert("电话不能为空！", "错误");
            return;
          }
      this.$refs.editForm
        .validate(valid => {})
        .then(valid => {
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
<style >
</style>