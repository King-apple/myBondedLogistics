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
  <div class="weixin-group-index">
    <basic-container>
        <el-row :gutter="15">
       <el-col :span="8">
      <vxe-form :data="formObj">
       <vxe-form-item  field="companyName" size="mini" span="8">
          <vxe-input
            v-model="formObj.companyName"
            clearable
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="center">
          <vxe-button status="primary" icon="fa fa-search" @click="selectTable()"
            >查询</vxe-button
          >
        </vxe-form-item>
      </vxe-form>
      <vxe-toolbar perfect size="mini">
        <template v-slot:buttons>
            <vxe-button type="text" class="fa fa-plus" @click="GroupAdd()" 
            >新增</vxe-button
          >
        </template>
      </vxe-toolbar>
      <vxe-table
        border
        show-overflow
        keep-source
        stripe
        highlight-current-row
        highlight-hover-row
        size="mini"
        :data="tableData"
        :loading="tableload"
        :round="true"
        align="center"
        height="700px"
        ref="tableGroup"
         @cell-click="rowclick"
      >
       <vxe-table-column field="groupCod" title="角色代码"  ></vxe-table-column>
        <vxe-table-column field="groupName" title="角色名字" ></vxe-table-column>
          <vxe-table-column field="status" title="状态"  formatter="formatStatus"></vxe-table-column>
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
      </el-col>
      <el-col :span="16">
          <vxe-form :data="formObj">
       <vxe-form-item   size="mini" span="24">
        <el-tag style="80px">此权限针对微信小程序，“更多”中菜单的管理，分配后即可生效</el-tag>
        </vxe-form-item>
      </vxe-form>
          <vxe-toolbar perfect size="mini">
        <template v-slot:buttons>
            <vxe-button type="text" class="fa fa-plus" @click="giveQx()" 
            >权限分配</vxe-button
          >
        </template>
      </vxe-toolbar>
          <vxe-table
        border
        show-overflow
        keep-source
        stripe
        highlight-current-row
        highlight-hover-row
        size="mini"
        :data="tableDataBody"
        :loading="tableloadBody"
        :round="true"
        align="center"
        height="700px"
        ref="tableQxAndGroup"
      >
       <vxe-table-column field="typeCodeName" title="应用类别"  ></vxe-table-column>
        <vxe-table-column field="qxName" title="应用名称" ></vxe-table-column>
          <vxe-table-column field="qxBottomText" title="ICON下面名称"></vxe-table-column>
      </vxe-table>
      <vxe-pager
        perfect
        size="mini"
        :loading="tableloadBody"
        :current-page="tableBodyPage.currentPage"
        :page-size="tableBodyPage.pageSize"
        :total="tableBodyPage.total"
        :layouts="[
          'PrevPage',
          'JumpNumber',
          'NextPage',
          'FullJump',
          'Sizes',
          'Total'
        ]"
        @page-change="handleBodyPageChange"
      ></vxe-pager>
      </el-col>
      </el-row>
       <vxe-modal
        v-if="dialogFormVisibleQxAndGroup"
        v-model="dialogFormVisibleQxAndGroup"
        title="权限选择"
        width="700"
        height="800"
        resize
        storage
      >
        <QxSelect
          :groupId="groupId"
          @dictItemVisible="dictItemVisible"
          @selectList="getBody"
        ></QxSelect>
      </vxe-modal>
       <vxe-modal
        v-if="dialogFormVisibleGroup"
        v-model="dialogFormVisibleGroup"
        title="角色维护"
        width="400"
        height="500"
        resize
        storage
      >
        <Edit
          :datanow="dataNow"
          @dictItemVisible="dictItemVisible"
          @selectList="selectTable"
        ></Edit>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import { fetchList, bodyList } from "@/api/weixin/group";
import { mapGetters } from "vuex";
import QxSelect from "./qxSelect";
import Edit from "./edit";
export default {
  components: { QxSelect, Edit },
  name: "weixin-group-index",
  data() {
    return {
      formObj: {},
      tableData: [],
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 50 // 每页显示多少条
      },
      tableload: false,
      tableDataBody: [],
      tableBodyPage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 50 // 每页显示多少条
      },
      tableloadBody: false,
      groupId: null,
      dialogFormVisibleQxAndGroup: false,
      dialogFormVisibleGroup: false,
      dataNow:null,
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() {
    this.getDate();
  },
  methods: {
    getDate() {
      this.tableload = true;
      fetchList(
        Object.assign(
          {
            current: this.tablePage.currentPage,
            size: this.tablePage.pageSize
          },
          this.formObj
        )
      )
        .then(response => {
          this.tableData = response.data.data.records;
          this.tablePage.total = response.data.data.total;
          this.tableload = false;
        })
        .catch(() => {
          this.tableload = false;
        });
    },
    getBody() {
      if (this.groupId != null) {
        this.tableloadBody = true;
        bodyList(
          Object.assign({
            current: this.tableBodyPage.currentPage,
            size: this.tableBodyPage.pageSize,
            groupId: this.groupId
          })
        )
          .then(response => {
            this.tableDataBody = response.data.data.records;
            this.tableBodyPage.total = response.data.data.total;
            this.tableloadBody = false;
          })
          .catch(() => {
            this.tableloadBody = false;
          });
      }
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    handleBodyPageChange({ currentPage, pageSize }) {
      this.tableBodyPage.currentPage = currentPage;
      this.tableBodyPage.pageSize = pageSize;
      this.bodyList();
    },
    selectTable() {
      this.tablePage.currentPage = 1;
      this.getDate();
    },
    rowclick({ row }) {
      this.groupId = row.pkid;
      this.getBody();
    },
    giveQx() {
      if (this.groupId != null) {
        this.dialogFormVisibleQxAndGroup = true;
      } else {
        this.$message.error("没有选择角色");
      }
    },
    GroupAdd() {
      this.dialogFormVisibleGroup = true;
    },
    GroupEdit(model) {
      this.dataNow = model;
      this.dialogFormVisibleGroup = true;
    },
    dictItemVisible() {
      this.dialogFormVisibleQxAndGroup = false;
        this.dialogFormVisibleGroup = false;
    },
    sortChangeEvent({ column, property, order }) {
      this.formObj.sqlSortNameOfMrLI = property;
      this.formObj.sqlSortOfMrLI = order;
      this.selectTable();
    },
  }
};
</script>
<style lang="scss" scoped>
.weixin-group-index {
  .el-tag {
    width: 100%;
  }
  .el-divider--horizontal {
    margin: 10px 0;
  }
  .vxe-toolbar {
    height: 25px;
  }
  .el-tag--small {
    height: 30px;
  }
}
</style>