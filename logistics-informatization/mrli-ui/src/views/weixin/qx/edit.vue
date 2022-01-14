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
  <div class="weixin-qx-edit">
    <vxe-form
      :data="dataForm"
      title-width="170"
      :rules="formRules"
      ref="editHeadForm"
    >
      <vxe-form-item
        title="类别名称"
        field="typeCodeName"
        size="mini"
        span="24"
      >
        <EditDown
          :objs="dataForm"
          :property="downTableOption.typeCodeName"
          :tableColumns="downTableOption.typeCodeNameColum"
          :urlapi="downTableOption.typeCodeNameurl"
          :returnApi="downTableOption.typeCodeNamereturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="菜单名称" field="qxName" size="mini" span="24" :title-prefix="{ message: '不会显示在小程序中！', icon: 'fa fa-exclamation-circle' }">
        <vxe-input v-model="dataForm.qxName" clearable></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="菜单Icon" field="qxIcon" size="mini" span="24" >
        <vxe-input v-model="dataForm.qxIcon" clearable></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="菜单路径" field="qxUrl" size="mini" span="24">
        <vxe-input v-model="dataForm.qxUrl"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="方法" field="qxMethod" size="mini" span="24">
        <vxe-input v-model="dataForm.qxMethod"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="图标颜色" field="qxIconColor" size="mini" span="24">
        <vxe-input v-model="dataForm.qxIconColor"></vxe-input>
      </vxe-form-item>
      <vxe-form-item
        title="菜单下方文本"
        field="qxBottomText"
        size="mini"
        span="24"
      >
        <vxe-input v-model="dataForm.qxBottomText"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="备注" field="qxBak" size="mini" span="24">
        <vxe-input v-model="dataForm.qxBak"></vxe-input>
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
import { addCommit } from "@/api/weixin/qx";
import store from "@/store";
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
        typeCodeName: [{ required: true, message: "请选择应用类型" }],
        qxName: [{ required: true, message: "请输入名称" }],
        qxIcon: [{ required: true, message: "请输入图标" }],
        qxIconColor: [{ required: true, message: "请输入颜色" }],
        qxUrl: [{ required: true, message: "请输入路径" }],
        qxBottomText: [{ required: true, message: "请输入图标下方文字" }]
      },
      downTableOption: {
        typeCodeName: "typeCodeName",
        typeCodeNameColum: [
          { field: "baseCode", title: "代码" },
          { field: "baseName", title: "名称" }
        ],
        typeCodeNamereturnApi: [
          { field: "baseCode", returnName: "qxCode" },
          { field: "baseName", returnName: "typeCodeName" }
        ],
        typeCodeNameurl: "/portside/weixincode/getcodeLov?type=QX"
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
.weixin-qx-edit {
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