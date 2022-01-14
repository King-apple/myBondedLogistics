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
  <div class="weixin-route-index">
    <basic-container>
      <el-row :gutter="15">
        <el-col :span="16">
          <vxe-form :data="formObj">
            <vxe-form-item field="selectVal" size="mini" span="8">
              <vxe-input v-model="formObj.selectVal" clearable></vxe-input>
            </vxe-form-item>
            <vxe-form-item span="4" size="mini" align="center">
              <vxe-button
                status="primary"
                icon="fa fa-search"
                @click="selectTable()"
                >查询</vxe-button
              >
            </vxe-form-item>
          </vxe-form>
          <vxe-toolbar perfect size="mini">
            <template v-slot:buttons>
              <vxe-button type="text" class="fa fa-plus" @click="QxAdd()"
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
            ref="tableQx"
            :sort-config="{ remote: true }"
            @sort-change="sortChangeEvent"
            @cell-click="rowclick"
          >
            <vxe-table-column
              field="routeName"
              title="路由名称"
              width="200"
              sortable
            ></vxe-table-column>
            <vxe-table-column
              field="routeColumn"
              title="对照字段名称"
              width="200"
              sortable
            ></vxe-table-column>
            <vxe-table-column
              field="columnValue"
              title="判断值，如果是多个用“，”相隔"
              sortable
            ></vxe-table-column>
            <vxe-table-column
              field="status"
              title="状态"
              sortable
              formatter="formatStatus"
            ></vxe-table-column>
            <vxe-table-column title="操作" width="100" show-overflow>
              <template v-slot="{ row }">
                <vxe-button
                  type="text"
                  icon="fa fa-edit"
                  @click="edit(row)"
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
        </el-col>
        <el-col :span="8">
          <vxe-form :data="formObj">
            <vxe-form-item size="mini" span="8"> </vxe-form-item>
          </vxe-form>
          <vxe-toolbar perfect size="mini">
            <template v-slot:buttons>
              <vxe-button type="text" class="fa fa-plus" @click="giveQx()"
                >角色分配</vxe-button
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
            <vxe-table-column
              field="groupCod"
              title="角色代码"
            ></vxe-table-column>
            <vxe-table-column
              field="groupName"
              title="角色名字"
            ></vxe-table-column>
            <vxe-table-column field="bak" title="备注"></vxe-table-column>
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
        v-if="dialogFormVisibleedit"
        v-model="dialogFormVisibleedit"
        title="权限路由维护"
        width="500"
        height="300"
        resize
        storage
      >
        <Edit
          :datanow="dataNow"
          @dictItemVisible="dictItemVisible"
          @selectList="selectTable"
        ></Edit>
      </vxe-modal>
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
          :extId="pkid"
          @dictItemVisible="dictItemVisible"
          @selectList="getBody"
        ></SelectGroup>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import { fetchList, fetchBodyList } from "@/api/weixin/route";
import { mapGetters } from "vuex";
import Edit from "./edit";
import SelectGroup from "./selectGroup";
export default {
  components: { Edit,SelectGroup },
  name: "weixin-route-index",
  data() {
    return {
      formObj: {},
      tableData: [],
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 30 // 每页显示多少条
      },
      dataNow: {},
      tableload: false,
      dialogFormVisibleedit: false,
      dialogFormVisibleGroup: false,
      tableDataBody: [],
      tableBodyPage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 50 // 每页显示多少条
      },
      tableloadBody: false,
      pkid: null
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
      this.dialogFormVisibleGroup = false;
    },
    selectTable() {
      this.tablePage.currentPage = 1;
      this.getDate();
    },
    QxAdd() {
      this.dataNow = null;
      this.dialogFormVisibleedit = true;
    },
    edit(model) {
      this.dataNow = model;
      this.dialogFormVisibleedit = true;
    },
    sortChangeEvent({ column, property, order }) {
      this.formObj.sqlSortNameOfMrLI = property;
      this.formObj.sqlSortOfMrLI = order;
      this.selectTable();
    },
    getBody() {
      if (this.pkid != null) {
        this.tableloadBody = true;
        fetchBodyList(
          Object.assign({
            current: this.tableBodyPage.currentPage,
            size: this.tableBodyPage.pageSize,
            extId: this.pkid
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
    handleBodyPageChange({ currentPage, pageSize }) {
      this.tableBodyPage.currentPage = currentPage;
      this.tableBodyPage.pageSize = pageSize;
      this.bodyList();
    },
    rowclick({ row }) {
      this.pkid = row.pkid;
      this.getBody();
    },
    giveQx() {
      if (this.pkid != null) {
        this.dialogFormVisibleGroup = true;
      } else {
        this.$message.error("没有选择路由");
      }
    }
  }
};
</script>
<style lang="scss" scoped>
.weixin-route-index {
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