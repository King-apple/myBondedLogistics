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
  <div class="feemain">
    <vxe-form
      :data="obj"
      title-width="130"
      :rules="validRules"
      @submit="handleSubmit"
      @reset="closeWindows"
    >
      <vxe-form-item title="权限名称：" field="roleName" size="mini" span="24">
        <vxe-input v-model="obj.roleName"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="权限代码：" field="roleCod" size="mini" span="24">
        <vxe-input v-model="obj.roleCod"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="字段：" field="toColumn" size="mini" span="24">
        <vxe-input v-model="obj.toColumn"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="对应MAPPER：" field="mapperApi" size="mini" span="24">
        <vxe-input v-model="obj.mapperApi"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="sql：" field="roleSql" size="mini" span="24">
        <vxe-input v-model="obj.roleSql"></vxe-input>
      </vxe-form-item>
      <vxe-form-item align="center" span="24">
        <vxe-button type="submit" status="primary" size="mini">保存</vxe-button>
        <vxe-button type="reset" size="mini">取消</vxe-button>
      </vxe-form-item>
    </vxe-form>
  </div>
</template>

<script>
import { addDataRoleObj } from "@/api/admin/menu";
import request from "@/router/axios";
import "@/styles/common/edit.scss";
import moment from "moment";
import EditDownTable from "../../baseUtile/EditDownTable";
import EditDown from "../../baseUtile/EditDown";
import Vue from "vue";
import VXETable from "vxe-table";
import hljs from "highlight.js";
import { Loading } from "element-ui";
import { mapState } from "vuex";
export default {
  props: ["objrow", "objship"],
  components: { EditDown },
  data() {
    const feeMnyValid = ({ cellValue }) => {
      return new Promise((resolve, reject) => {
        var regNeg = /^[0-9]+.?[0-9]*$/;
        if (regNeg.test(cellValue)) {
          resolve();
        } else {
          reject(new Error("格式不对"));
        }
      });
    };
    return {
      obj: {},
      tableData: [],
      buttonload: false,
      activeNames: ["1", "2", "3"],
      validRules: {
        roleName: [{ required: true, message: "必须填写权限名称" }],
        mapperApi: [{ required: true, message: "必须填写对应MAPPER" }],
        roleSql: [{ required: true, message: "必须填写sql" }]
      },
      downTableOption: {}
    };
  },
  computed: {
    ...mapState({
      userInfo: state => state.user.userInfo
    })
  },
  mounted: function() {
    this.obj = this.objrow;
  },
  methods: {
    async handleSubmit() {
      let loadingInstance = Loading.service({ fullscreen: true });
      addDataRoleObj(this.obj)
        .then(data => {
          this.$message.success("编辑成功");
          loadingInstance.close();
          this.buttonload = false;
          this.$emit("getListDate");
          this.$emit("dictItemVisible");
        })
        .catch(() => {
          // loading();
          loadingInstance.close();
          this.buttonload = false;
        });
    },
    closeWindows: function() {
      this.$emit("dictItemVisible");
    },
    ///////////////////////////////表格操作区域方法////////////////////
    insertEvent() {
      this.$refs.xTable
        .insert()
        .then(({ row }) => this.$refs.xTable.setActiveCell(row, "feeNam"));
    }
  }
};
</script>
<style >
.feemain .el-collapse-item__content {
  padding-bottom: 0px;
}
.feemain .avue-form__row {
  height: 35px;
}
.feemain .el-tabs__item {
  height: 30px;
  line-height: 25px;
}
.feemain .vxe-toolbar {
  height: 25px;
}
</style>