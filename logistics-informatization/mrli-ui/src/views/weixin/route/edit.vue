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
  <div class="weixin-route-edit">
    <vxe-form
      :data="dataForm"
      title-width="170"
      :rules="formRules"
      ref="editHeadForm"
    >
      <vxe-form-item title="路由名称" field="routeName" size="mini" span="24" >
        <vxe-input v-model="dataForm.routeName" clearable></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="对照字段名称" field="routeColumn" size="mini" span="24" >
        <vxe-input v-model="dataForm.routeColumn" clearable></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="判断值" field="columnValue" size="mini" span="24" :title-prefix="{ message: '如果是多个用“，”相隔', icon: 'fa fa-exclamation-circle' }">
        <vxe-input v-model="dataForm.columnValue"></vxe-input>
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
import { addCommit } from "@/api/weixin/route";
import "@/styles/common/edit.scss";
import { mapGetters } from "vuex";
import EditDown from "../../baseUtile/EditDown";
export default {
  components: { EditDown },
  props: ["datanow"],
  data() {
    return {
      dataForm: { typeCodeName: null, qxCode: null },
      formRules: {
        routeName: [{ required: true, message: "请输入路由名称" }],
        routeColumn: [{ required: true, message: "请输入对照字段名称" }],
        columnValue: [{ required: true, message: "请输入判断值" }],
      },
      urlQybasx: "",
      urlCns: ""
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
          addCommit(this.dataForm)
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
.weixin-route-edit {
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