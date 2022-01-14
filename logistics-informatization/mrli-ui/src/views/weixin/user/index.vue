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
  <div class="weixin-user-index">
    <basic-container>
      <vxe-form :data="formObj">
       <vxe-form-item title="姓名" field="personName" size="mini" span="3">
          <vxe-input
            v-model="formObj.personName"
            clearable
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="人员性质" field="personType" size="mini" span="3">
          <vxe-select v-model="formObj.personType" clearable size="mini">
            <vxe-option value="1" label="港内"></vxe-option>
            <vxe-option value="2" label="港外"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="center">
          <vxe-button status="primary" icon="fa fa-search" @click="selectTable()"
            >查询</vxe-button
          >
          <vxe-button type="reset" icon="fa fa-repeat" @click="resetForm">重置</vxe-button>
        </vxe-form-item>
      </vxe-form>
      <vxe-toolbar perfect size="mini">
        <template v-slot:buttons>
         
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
        ref="tableCar"
        :sort-config="{remote:true}"
         @sort-change="sortChangeEvent"
      >
       <vxe-table-column field="personName" title="姓名" width="200" sortable></vxe-table-column>
        <vxe-table-column field="weixinNickname" title="微信名" sortable></vxe-table-column>
         <vxe-table-column field="unitName" title="公司" sortable></vxe-table-column>
          <vxe-table-column field="personPhone" title="联系方式" sortable></vxe-table-column>
          <vxe-table-column field="personId" title="身份证" sortable></vxe-table-column>
          <vxe-table-column field="personSex" title="性别" sortable formatter="formatSex"></vxe-table-column>
          <vxe-table-column field="subunitName" title="部门" sortable></vxe-table-column>
           <vxe-table-column field="personWork" title="岗位" sortable></vxe-table-column>
           <vxe-table-column field="createTime" title="创建时间" sortable></vxe-table-column>
           <vxe-table-column title="操作" width="100" show-overflow>
          <template v-slot="{ row }">
            <vxe-button
              type="text"
              icon="fa fa-users"
              @click="editGroup(row)"
            ></vxe-button>
          </template>
        </vxe-table-column>
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
      <vxe-modal
        v-if="dialogFormVisibleGroup"
        v-model="dialogFormVisibleGroup"
        title="维护角色"
        width="700"
        height="800"
        resize
        storage
      >
        <SelectGroup
          :userId="userId"
          @dictItemVisible="dictItemVisible"
          @selectList="getBody"
        ></SelectGroup>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import { fetchList } from "@/api/weixin/user";
import { mapGetters } from "vuex";
import SelectGroup from "./selectGroup"
export default {
  components: {SelectGroup},
  name: "weixin-user-index",
  data() {
    return {
      formObj: {  },
      tableData: [],
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 50 // 每页显示多少条
      },
      tableload: false,
      dialogFormVisibleGroup:false,
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
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
      this.dialogFormVisibleGroup=false;
    },
    selectTable() {
      this.tablePage.currentPage = 1;
      this.getDate();
    },
    editGroup(model){
      this.userId=model.pkid;
      this.dialogFormVisibleGroup=true;
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
.weixin-user-index {
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