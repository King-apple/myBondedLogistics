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
  <div class="portside-yqbak-personedit">
    <vxe-form
      :data="dataForm"
      title-width="100"
      :rules="formRules"
      ref="editHeadForm"
    >
      <vxe-form-item title="登录名" field="username" span="24">
        <vxe-input v-model="dataForm.username"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="用户姓名" field="manName" span="24">
        <vxe-input v-model="dataForm.manName"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="密码" field="password" span="24">
        <vxe-input v-model="dataForm.password" type="password"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="所属公司" field="deptName" span="24">
        <EditDownTree
          :objs="dataForm"
          property="deptName"
          :tableColumns="downTableOption.deptNameColum"
          :urlapi="downTableOption.deptNameUrl"
          :returnApi="downTableOption.deptNameReturnApi"
          sizenow="mini"
          @datereturn="unitReturn"
        ></EditDownTree>
      </vxe-form-item>
      <vxe-form-item title="所属部门" field="deptSubName" span="24">
        <EditDownTree
          :objs="dataForm"
          property="deptSubName"
          :tableColumns="downTableOption.deptSubNameColum"
          :urlapi="downTableOption.deptSubNameUrl"
          :returnApi="downTableOption.deptSubNameReturnApi"
          sizenow="mini"
          :disabled="downTableOption.deptSubNameDisabled"
        ></EditDownTree>
      </vxe-form-item>
      <vxe-form-item title="手机号" field="phone" span="24">
        <vxe-input v-model="dataForm.phone"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="角色" field="roles" span="24"  v-if="ifJs">
        <EditDownMore
          :objs="dataForm"
          property="roles"
          returnlist="roleList"
          dataId="roleId"
          seeName="roleName"
          :tableColumns="downTableOption.rolesColum"
          :urlapi="downTableOption.rolesUrl"
          sizenow="mini"
        ></EditDownMore>
      </vxe-form-item>
      <vxe-form-item title="状态" field="lockFlag" span="24">
        <vxe-radio-group v-model="dataForm.lockFlag">
          <vxe-radio-button label="0" content="有效"></vxe-radio-button>
          <vxe-radio-button label="9" content="锁定"></vxe-radio-button>
        </vxe-radio-group>
      </vxe-form-item>
    </vxe-form>
    <div align="center">
      <el-button
        type="primary"
        size="mini"
        icon="el-icon-check"
        @click="handleSubmit"
        v-if="ifSave"
        >保存</el-button
      >
      <el-button size="mini" icon="el-icon-close" plain @click="closeWindows"
        >取消</el-button
      >
    </div>
  </div>
</template>

<script>
import { addObj, delObj, fetchList, putObj } from "@/api/admin/user";
import store from "@/store";
import "@/styles/common/edit.scss";
import request from "@/router/axios";
import { mapGetters } from "vuex";
import EditDown from "@/views/baseUtile/EditDown";
import EditDownMore from "@/views/baseUtile/EditDownMore";
import EditDownTree from "@/views/baseUtile/EditDownTree";
import XEUtils from "xe-utils";
export default {
  components: { EditDown, EditDownMore, EditDownTree },
  props: ["datanow", "ifView"],
  data() {
    return {
      ifJs: false,
      dataForm:{
        roles: null,
        role: null,
        deptName: null,
        deptId: null,
        deptSubName: null,
        deptSubId: null,
        userId:null,
      },
      formRules: {
        username: [
          { required: true, message: "请输入登录名" },
          { min: 3, max: 20, message: "长度在 3 到 20 个字符" }
        ],
        password: [
          { required: true, message: "请输入密码" },
          {
            validator({ itemValue }) {
              // 自定义校验
              const reg = /(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9])/;
              if (!reg.test(itemValue) && itemValue != "") {
                return new Error(
                  "密码必须是大写字母，小写字母，数字，特殊字符组成，且长度为8到12位！"
                );
              }
            }
          }
        ],
        manName: [{ required: true, message: "请输入姓名" }],
        deptName: [{ required: true, message: "请选择公司" }],
        phone: [
          { required: true, message: "请输入手机号" },
          {
            validator({ itemValue }) {
              // 自定义校验
              const reg = /^((0\d{2,3}-\d{7,8})|(1[34578]\d{9}))$/;
              if (!reg.test(itemValue) && itemValue != "") {
                return new Error("请输入正确的电话号码或者固话号码");
              }
            }
          }
        ],
        lockFlag: [{ required: true, message: "请选择有效性" }]
      },
      ifSave: true,
      downTableOption: {
        //动态代码
        rolesColum: [
          { field: "roleName", title: "名称" },
          { field: "roleDesc", title: "备注" }
        ],
        rolesUrl: "/admin/role/lovPage",
        //公司
        deptNameColum: [
          { field: "name", type: "radio", treeNode: true, width: "150" },
          { field: "name", title: "名称" }
        ],
        deptNameReturnApi: [
          { field: "name", returnName: "deptName" },
          { field: "id", returnName: "deptId" }
        ],
        deptNameUrl: "/admin/dept/tree?type=1,2",
        //部门
        deptSubNameColum: [
          { field: "name", type: "radio", treeNode: true, width: "150" },
          { field: "name", title: "名称" }
        ],
        deptSubNameReturnApi: [
          { field: "name", returnName: "deptSubName" },
          { field: "id", returnName: "deptSubId" }
        ],
        deptSubNameUrl: "/admin/dept/tree",
        deptSubNameDisabled: true
      }
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() {
    if (this.datanow != null) {
      this.dataForm = this.datanow;
      this.formRules.phone = [];
      this.formRules.password = [];
      if (this.ifView != null && this.ifView == "yes") {
        this.ifSave = false;
      }
      if (this.datanow.deptId != null) {
        this.downTableOption.deptSubNameUrl =
          "/admin/dept/tree?type=2,3,4&rootId=" + this.datanow.deptId;
        this.downTableOption.deptSubNameDisabled = false;
      }
      this.ifJs = false;
    } else {
      this.ifJs = true;
    }
  },
  mounted: function() {},
  methods: {
    async handleSubmit() {
      this.$refs.editHeadForm
        .validate(valid => {})
        .then(valid => {
          let role = XEUtils.pluck(this.dataForm.roleList, "roleId");
          this.dataForm.role = role;
          if (this.dataForm.userId == null) {
            addObj(this.dataForm)
              .then(data => {
                this.$message.success("保存成功");
                this.$emit("selectList");
                this.$emit("dictItemVisible");
              })
              .catch(() => {});
          } else {
            if (this.dataForm.phone && this.dataForm.phone.indexOf("*") > 0) {
              this.dataForm.phone = undefined;
            }
            putObj(this.dataForm)
              .then(() => {
                this.$message.success("保存成功");
                this.$emit("selectList");
                this.$emit("dictItemVisible");
              })
              .catch(() => {});
          }
        });
    },
    closeWindows() {
      this.$emit("dictItemVisible");
    },
    unitReturn(row) {
      if (row != null) {
        this.downTableOption.deptSubNameUrl =
          "/admin/dept/tree?type=2,3,4&rootId=" + row.id;
        this.downTableOption.deptSubNameDisabled = false;
      }
    }
  }
};
</script>
<style lang="scss">
.admin-newuser-add {
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