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
  <div class="feeList">
    <vxe-toolbar perfect size="mini">
      <template v-slot:buttons>
        <vxe-button type="text" class="fa fa-plus" @click="bj()">新增</vxe-button>
      </template>
    </vxe-toolbar>
    <vxe-table border show-overflow size="mini" :data="tableData">
      <vxe-table-column type="seq" width="60" fixed="left"></vxe-table-column>
      <vxe-table-column field="roleName" width="150" title="权限名称"></vxe-table-column>
      <vxe-table-column field="createTime" width="150" title="创建时间"></vxe-table-column>
      <vxe-table-column field="mapperApi" title="关联mapper" ></vxe-table-column>
      <vxe-table-column field="roleSql" title="条件"></vxe-table-column>
      <vxe-table-column title="操作" fixed="right" width="200">
        <template v-slot="{ row }">
          <vxe-button  type="text" icon="fa fa-edit" @click="editRow(row)">编辑</vxe-button>
          <vxe-button  type="text" icon="fa fa-trash" @click="removeEvent(row)">删除</vxe-button>
        </template>
      </vxe-table-column>
    </vxe-table>
    <vxe-modal
      v-if="dialogFormVisible"
      v-model="dialogFormVisible"
      id="myModal6"
      title="数据权限新增"
      width="600"
      height="400"
      min-width="1100"
      min-height="800"
      resize
      storage
      transfer
    >
      <EditDataRole :objrow="objRole" @dictItemVisible="dictItemVisible" @getListDate="getDate" :objship="objrow"></EditDataRole>
    </vxe-modal>
  </div>
</template>

<script>
import {
  getDataRole,delDataRoleObj
} from "@/api/admin/menu";
import request from "@/router/axios";
import "@/styles/common/edit.scss";
import moment from "moment";
import EditDownTable from "../../baseUtile/EditDownTable";
import EditDown from "../../baseUtile/EditDown";
import Vue from "vue";
import VXETable from "vxe-table";
import hljs from "highlight.js";
import { Loading } from "element-ui";
import EditDataRole from './editDataRole';
export default {
  props: ["objrow"],
  components: { EditDataRole },
  data() {
    return {
      obj: {},
      objRole: {},
      tableData: [],
      buttonload: false,
      downTableOption: {},
      dialogFormVisible: false,
      
    };
  },
  created: function() {
    this.obj = this.objrow;
    //查询价格
    this.getDate();
  },
  methods: {
    getDate() {
      getDataRole(this.obj.id)
        .then(response => {
          this.tableData = response.data.data;
        })
        .catch(() => {});
    },
    getDateFromNext(){
       this.getDate();

    },
    closeWindows: function() {
      this.$emit("closeWindows");
    },
    ///////////////////////////////表格操作区域方法////////////////////
    insertEvent() {
      this.$refs.xTable
        .insert()
        .then(({ row }) => this.$refs.xTable.setActiveCell(row, "feeNam"));
    },
    bj() {
      this.objRole = {
        roleName:null,roleCod:null,toColumn:null,mapperApi:null,roleSql:null,menuId:this.obj.id,roleType:3
      };
      this.dialogFormVisible = true;
    },
    dictItemVisible() {
      this.dialogFormVisible = false;
    },
    editRow(row) {
      this.objRole = row;
      this.dialogFormVisible = true;
    },
   
    removeEvent(row) {
      this.$confirm("是否确认删除" + row.roleName + "报价单", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return delDataRoleObj(row.pkid);
        })
        .then(data => {
          this.$message.success("删除成功");
          this.getDate();
        });
    },
  }
};
</script>
<style >
.feeList .el-collapse-item__content {
  padding-bottom: 0px;
}
.feeList .vxe-toolbar {
  height: 25px;
}
</style>
