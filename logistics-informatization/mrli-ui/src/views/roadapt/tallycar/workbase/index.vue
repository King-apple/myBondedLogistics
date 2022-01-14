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
  <div class="roadapt-tallycar-workbase-index">
    <basic-container>
      <vxe-form :data="formObj">
        <vxe-form-item title="车辆名称" field="tallycarName" size="mini" span="5">
          <vxe-input
            v-model="formObj.tallycarName"
            clearable
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="车辆ID" field="tallycarId" size="mini" span="5">
          <vxe-input
            v-model="formObj.tallycarId"
            clearable
          ></vxe-input>
        </vxe-form-item>
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
      <vxe-toolbar custom perfect size="mini">
        <template v-slot:buttons>
            <vxe-button  v-if="!ifSelectAll" type="text" class="fa fa-plus" @click="add()"
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
        ref="tableOne"
        :sort-config="{remote:true}"
         @sort-change="sortChangeEvent"
      >
        <vxe-table-column field="tallycarName" title="理货车辆名称" ></vxe-table-column>
         <vxe-table-column field="tallycarId" title="理货车辆ID"  ></vxe-table-column>
          <vxe-table-column field="etCheckSeq" title="指令号" ></vxe-table-column>
          <vxe-table-column field="createTime" title="创建时间" ></vxe-table-column>
          <vxe-table-column field="createOpt" title="创建人" ></vxe-table-column>
          <vxe-table-column field="updateTime" title="最后更新时间" ></vxe-table-column>
           <vxe-table-column field="updateOpt" title="最后更新人" ></vxe-table-column>
           <vxe-table-column title="操作" width="100" show-overflow>
          <template v-slot="{ row }">
            <vxe-button
              v-if="!ifSelectAll"
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
       <vxe-modal
        v-if="dialogFormVisibleedit"
        v-model="dialogFormVisibleedit"
        title="指令绑定"
        width="600"
        height="400"
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
import { fetchList } from "@/api/roadapt/tallycar/workbase";
import { mapGetters } from "vuex";
import Edit from "./edit";
import EditDown from "@/views/baseUtile/EditDown";
export default {
  components: { EditDown, Edit },
  name: "roadapt-tallycar-workbase-index",
  data() {
    return {
      formObj: {
        unitName: null,
        unitCod: null,
        shipNo: null,
        shipNam: null,
        selectTime: null
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
      fetchList(
        Object.assign(
          {
            current: this.tablePage.currentPage,
            size: this.tablePage.pageSize,
            ifSelectAll:this.ifSelectAll
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
    selectTable() {
      this.tablePage.currentPage = 1;
      this.getDate();
    },
    resetForm() {},
    add() {
      this.dataNow = null;
      this.dialogFormVisibleedit = true;
    },
    edit(row) {
      this.dataNow = row;
      this.dialogFormVisibleedit = true;
    },
    delete() {

    },
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
      this.dataNow = null;
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
.roadapt-tallycar-workbase-index {
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