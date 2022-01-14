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
  <div class="portside-yqbak-companyedit">
    <vxe-form
      :data="dataForm"
      title-width="170"
      :rules="formRules"
      ref="editHeadForm"
    >
      <vxe-form-item title="企业名称" field="companyName" size="mini" span="12">
        <vxe-input v-model="dataForm.companyName"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="企业法人" field="companyFr" size="mini" span="12">
        <vxe-input v-model="dataForm.companyFr"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="经营期限" field="companyQx" size="mini" span="12">
        <vxe-input
          v-model="dataForm.companyQx"
          type="date"
          clearable
          valueFormat="yyyy-MM-dd"
        ></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="企业代码" field="companyDm" size="mini" span="12">
         <vxe-input v-model="dataForm.companyDm"></vxe-input>
      </vxe-form-item>
      <vxe-form-item
        title="企业地址"
        field="companyPlace"
        size="mini"
        span="12"
      >
        <vxe-input v-model="dataForm.companyPlace"></vxe-input>
      </vxe-form-item>
      <vxe-form-item
        title="公司类型"
        field="companyTypeId"
        size="mini"
        span="12"
      >
       <vxe-select v-model="dataForm.companyTypeId" placeholder="请选择">
          <vxe-option :value="1" label="合资"></vxe-option>
          <vxe-option :value="2" label="独资"></vxe-option>
          <vxe-option :value="3" label="国有"></vxe-option>
          <vxe-option :value="4" label="私营"></vxe-option>
          <vxe-option :value="5" label="股份制"></vxe-option>
          <vxe-option :value="6" label="有限责任"></vxe-option>
          <vxe-option :value="99" label="其他"></vxe-option>
        </vxe-select>
      </vxe-form-item>
      <vxe-form-item
        title="企业经营范围"
        field="companyWork"
        size="mini"
        span="12"
      >
        <vxe-input v-model="dataForm.companyWork"></vxe-input>
      </vxe-form-item>
      <vxe-form-item
        title="企业现有人数"
        field="companyPersonNum"
        size="mini"
        span="12"
      >
        <vxe-input v-model="dataForm.companyPersonNum"></vxe-input>
      </vxe-form-item>
      <vxe-form-item
        title="在边检系统备案业务编号"
        field="companyBabm"
        size="mini"
        span="12"
      >
        <vxe-input v-model="dataForm.companyBabm"></vxe-input>
      </vxe-form-item>
      <vxe-form-item
        title="在边检系统备案有效期"
        field="companyBayxq"
        size="mini"
        span="12"
      >
        <vxe-input
          v-model="dataForm.companyBayxq"
          type="date"
          clearable
          valueFormat="yyyy-MM-dd"
          :transfer="true"
        ></vxe-input>
      </vxe-form-item>
      <vxe-form-item
        title="企业疫情防控联络人"
        field="companyFkllr"
        size="mini"
        span="12"
      >
        <vxe-input v-model="dataForm.companyFkllr"></vxe-input>
      </vxe-form-item>
      <vxe-form-item
        title="联络电话"
        field="companyPhone"
        size="mini"
        span="12"
      >
        <vxe-input v-model="dataForm.companyPhone"></vxe-input>
      </vxe-form-item>
      <vxe-form-item
        title="企业备案手续（行政审批局等政府管理部门颁发）"
        field="urlQybasx"
        size="mini"
        span="24"
      >
        <el-upload
          :headers="headers"
          :show-file-list="false"
          :on-success="handleQybasxSuccess"
          class="avatar-uploader"
          action="/admin/sys-file/upload"
        >
          <img
            v-if="dataForm.urlQybasx != null"
            :src="urlQybasx"
            class="avatar"
            id="urlQybasxId"
          />
          <i v-else class="el-icon-plus avatar-uploader-icon" />
        </el-upload>
      </vxe-form-item>
      <vxe-form-item title="承诺书上传" field="urlCns" size="mini" span="24">
        <el-upload
          :headers="headers"
          :show-file-list="false"
          :on-success="handleurlCnsSuccess"
          class="avatar-uploader"
          action="/admin/sys-file/upload"
        >
          <img
            v-if="dataForm.urlCns != null"
            :src="urlCns"
            class="avatar"
            id="urlCnsId"
          />
          <i v-else class="el-icon-plus avatar-uploader-icon" />
        </el-upload>
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
import { addCompany } from "@/api/portside/yqbak";
import store from "@/store";
import "@/styles/common/edit.scss";
import { mapGetters } from "vuex";

import { handleImg } from "@/util/util";

export default {
  props: ["datanow"],
  data() {
    return {
      dataForm: { urlQybasx: null, urlCns: null },
      headers: {
        Authorization: "Bearer " + store.getters.access_token
      },
      formRules: {
        companyName: [{ required: true, message: "请输入企业名称" }],
        companyFr: [{ required: true, message: "请输入企业法人" }],
        companyDm: [{ required: true, message: "请输入企业代码" }],
        companyWork: [{ required: true, message: "请输入企业经营范围" }],
        companyBabm: [
          { required: true, message: "请输入在边检系统备案业务编号" }
        ],
        companyPlace: [{ required: true, message: "请输入企业地址" }],
        companyFkllr: [{ required: true, message: "请输入企业疫情防控联络人" }],
        companyPhone: [{ required: true, message: "请输入联系方式" }],
        urlQybasx: [{ required: true, message: "请上传企业备案手续" }],
        urlCns: [{ required: true, message: "承诺书上传" }]
      },
      urlQybasx: "",
      urlCns: ""
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() {},
  mounted: function() {
    if (this.datanow != null) {
      this.dataForm = this.datanow;
      if (this.dataForm.urlQybasx != null && this.dataForm.urlQybasx != "") {
        handleImg(this.dataForm.urlQybasx, "urlQybasxId");
      }
      if (this.dataForm.urlCns != null && this.dataForm.urlCns != "") {
        handleImg(this.dataForm.urlCns, "urlCnsId");
      }
    }
  },
  methods: {
    handleSubmit() {
      this.$refs.editHeadForm
        .validate(valid => {})
        .then(valid => {
          addCompany(this.dataForm)
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
    handleQybasxSuccess(res, file) {
      this.urlQybasx = URL.createObjectURL(file.raw);
      this.dataForm.urlQybasx = res.data.url;
    },
    handleurlCnsSuccess(res, file) {
      this.urlCns = URL.createObjectURL(file.raw);
      this.dataForm.urlCns = res.data.url;
    }
  }
};
</script>
<style lang="scss">
.portside-yqbak-companyedit {
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