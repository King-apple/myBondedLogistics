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
  <div class="weixin-group-edit">
    <vxe-form
      :data="dataForm"
      title-width="100"
      :rules="formRules"
      ref="editHeadForm"
    >
      <vxe-form-item title="角色代码" field="groupCod" size="mini" span="24" :title-prefix="{ message: '代码长度为2位', icon: 'fa fa-exclamation-circle' }">
        <vxe-input v-model="dataForm.groupCod" clearable></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="角色名称" field="groupName" size="mini" span="24" >
        <vxe-input v-model="dataForm.groupName" clearable></vxe-input>
      </vxe-form-item>
       <vxe-form-item title="备注" field="bak" size="mini" span="24" >
        <vxe-input v-model="dataForm.bak" clearable></vxe-input>
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
import { saveGroup } from "@/api/weixin/group";
import "@/styles/common/edit.scss";
import { mapGetters } from "vuex";
export default {
  components: {},
  props: ["datanow"],
  data() {
    return {
      dataForm: {},
      formRules: {
        groupCod: [{ required: true, message: "请填写角色代码" }],
        groupName: [{ required: true, message: "请填写角色名称" }],
      },
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() {
      if (this.datanow != null) {
      this.dataForm = this.datanow;
    }
  },
  mounted: function() {
    
  },
  methods: {
    handleSubmit() {
      this.$refs.editHeadForm
        .validate(valid => {})
        .then(valid => {
          saveGroup(this.dataForm)
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
.weixin-group-edit {
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #409eff;
  }

  .avatar-uploader-icon {
    font-size: 28px !important;
    color: #8c939d !important;
    width: 140px !important;
    height: 140px !important;
    line-height: 140px !important;
    text-align: center !important;
  }

  .avatar {
    width: 140px;
    height: 140px;
    display: block;
  }
}
</style>