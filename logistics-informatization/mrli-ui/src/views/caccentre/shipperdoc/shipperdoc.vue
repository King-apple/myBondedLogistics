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
  <div class="caccentre-shipperdoc-shipperdoc">
    <basic-container>
      <vxe-form :data="formObj">
        <vxe-form-item title="企业名称" field="shipperDoc" size="mini" span="3">
          <vxe-input
            v-model="formObj.shipperDoc"
            clearable
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="区域名称" field="areaNam" size="mini" span="3">
          <vxe-input
            v-model="formObj.areaNam"
            clearable
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="center">
          <vxe-button
            status="primary"
            icon="fa fa-search"
            @click="selectTable()"
            >查询</vxe-button
          >
          <vxe-button type="reset" icon="fa fa-repeat" @click="resetForm">重置</vxe-button>
        </vxe-form-item>
      </vxe-form>
      <vxe-toolbar perfect size="mini">
        <template v-slot:buttons>
            <vxe-button type="text" class="fa fa-plus" @click="add()"
            >新增</vxe-button
          >
        </template>
      </vxe-toolbar>
      <vxe-table
        border
        show-overflow
        keep-source
        size="mini"
        :data="tableData"
        :loading="tableload"
        :round="true"
        align="center"
        height="700px"
        ref="tableCar"
      >
        <vxe-table-column field="areaNam" title="区域名称"></vxe-table-column>
        <vxe-table-column field="shipperDoc" title="货主名称" ></vxe-table-column>
        <vxe-table-column field="shipperCod" title="货主代码"></vxe-table-column>
       
           <vxe-table-column title="操作" width="100" show-overflow>
          <template v-slot="{ row }">
            <vxe-button
              type="text"
              icon="fa fa-edit"
              @click="edit(row)"
            ></vxe-button>
          </template>
        </vxe-table-column>
        </vxe-table-colgroup>
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
      
    </basic-container>
  </div>
</template>

<script>
import { pageList,pageListOfSql } from "@/api/caccentre/shipperdoc";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import EditDown from "../../baseUtile/EditDown";
export default {
  components: { EditDown },
  name: "portside-yqbak-index",
  data() {
    return {
      formObj: {
       
      },
      dialogFormVisibleedit: false,
      tableData: [],
      dataNow: {},
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      tableload: false
    };
  },
  computed: {
    ...mapGetters(["userInfo", "permissions"])
  },
  created() {
    this.getDate();
  },
  methods: {
    getDate() {
      this.tableload = true;
      pageListOfSql(
        Object.assign(
          {
            current: this.tablePage.currentPage,
            size: this.tablePage.pageSize
          },
          this.formObj,
          {ok:"ok"}
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

    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    selectTable() {
      this.tablePage.currentPage = 1;
      this.getDate();
    },
    resetForm() {
     
    },
    add() {
      this.dataNow = null;
      this.dialogFormVisibleedit = true;
    },
    edit(row) {
      this.dataNow = row;
      this.dialogFormVisibleedit = true;
    },
    delete() {},
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
      this.dataNow = null;
    }
  }
};
</script>
<style lang="scss" scoped>
.caccentre-shipperdoc-shipperdoc {
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
</style>>