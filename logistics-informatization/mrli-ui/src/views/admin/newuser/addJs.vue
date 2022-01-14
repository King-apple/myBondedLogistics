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
    <vxe-form :data="formObj">
      <vxe-form-item title="名称" field="username" size="mini" span="16">
        <vxe-input
          v-model="formObj.username"
          type="input"
          clearable
        ></vxe-input>
      </vxe-form-item>
      <vxe-form-item span="8" size="mini" align="center">
        <vxe-button status="primary" icon="fa fa-search" @click="selectTable()"
          >查询</vxe-button
        >
        <vxe-button type="reset" icon="fa fa-repeat">重置</vxe-button>
      </vxe-form-item>
    </vxe-form>
    <vxe-table
      border
      show-overflow
      keep-source
      highlight-hover-row
      highlight-current-row
      size="mini"
      :data="tableData"
      :loading="tableload"
      :round="true"
      align="center"
      height="550px"
      ref="xTable1"
      @checkbox-change="selectEvent"
    >
      <vxe-column type="checkbox" width="60"></vxe-column>
      <vxe-table-column field="roleName" title="角色名称"></vxe-table-column>
      <vxe-table-column field="roleDesc" title="备注"></vxe-table-column>
    </vxe-table>
    <vxe-pager
      perfect
      size="mini"
      :loading="tableload"
      :current-page="tablePage.currentPage"
      :page-size="tablePage.pageSize"
      :total="tablePage.total"
      :layouts="[
        'PrevPage',
        'JumpNumber',
        'NextPage',
        'FullJump',
        'Sizes',
        'Total'
      ]"
      @page-change="handlePageChange"
    ></vxe-pager>
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
import { addObj, delObj, fetchList, putObj } from "@/api/admin/user";
import { roleLovList } from "@/api/admin/role";
import store from "@/store";
import "@/styles/common/edit.scss";
import request from "@/router/axios";
import { mapGetters } from "vuex";
import XEUtils from "xe-utils";
export default {
  components: {},
  props: ["datanow", "ifView"],
  data() {
    return {
      tableData: [],
      dataForm: null,
      tableload: false,
      formObj: {},
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      }
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() {
    if (this.datanow != null) {
      this.dataForm = this.datanow;
      this.selectTable();
    }
  },
  mounted: function() {},
  methods: {
    async handleSubmit() {
      let role = XEUtils.pluck(this.dataForm.roleList, "roleId");
      this.dataForm.role = role;
      if (this.datanow.userId == null) {
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
    },
    closeWindows() {
      this.$emit("dictItemVisible");
    },
    getDate() {
      this.tableload = true;
      roleLovList(
        Object.assign({
          current: this.tablePage.currentPage,
          size: this.tablePage.pageSize,
          codeName: this.formObj.username
        })
      )
        .then(response => {
          this.tableData = response.data.data.records;
          let dataSelectList = this.dataForm.roleList;
          let tableData = response.data.data.records;
          let dataId = "roleId";
          for (var i in tableData) {
            for (var n in dataSelectList) {
              if (tableData[i][dataId] == dataSelectList[n][dataId]) {
                this.$refs.xTable1.setCheckboxRow([tableData[i]], true);
              }
            }
          }
          this.tablePage.total = response.data.data.total;
          this.tableload = false;
        })
        .catch(() => {
          this.tableload = false;
        });
    },

    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    selectTable() {
      this.tablePage.currentPage = 1;
      this.getDate();
    },
    selectEvent({ checked, row }) {
      let returnlist = this.dataForm.roleList;
      let dataId = 'roleId';
      if (checked) {
        //选中执行
        if (
         returnlist == null ||
         returnlist == 0
        ) {
         returnlist = [row];
        } else {
         returnlist.push(row);
        }
      } else {
        
        //取消时候执行
        let dateBak = [];
        for (let i = 0; i < returnlist.length; i++) {
            console.log(row[dataId]+"----"+returnlist[i][dataId])
          if (row[dataId] != returnlist[i][dataId]) {
            dateBak.push(returnlist[i]);
          }
        }
        this.dataForm.roleList = dateBak;
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