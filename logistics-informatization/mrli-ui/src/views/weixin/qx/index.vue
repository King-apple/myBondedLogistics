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
  <div class="weixin-qx-index">
    <basic-container>
      <vxe-form :data="formObj">
        <vxe-form-item title="菜单名称" field="qxName" size="mini" span="3">
          <vxe-input v-model="formObj.qxName" clearable></vxe-input>
        </vxe-form-item>

        <vxe-form-item span="4" size="mini" align="center">
          <vxe-button
            status="primary"
            icon="fa fa-search"
            @click="selectTable()"
            >查询</vxe-button
          >
          <vxe-button type="reset" icon="fa fa-repeat" @click="resetForm"
            >重置</vxe-button
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
      >
        <vxe-table-column
          field="typeCodeName"
          title="类别名称"
          width="200"
          sortable
        ></vxe-table-column>
        <vxe-table-column
          field="qxName"
          title="菜单名称"
          width="200"
          sortable
        ></vxe-table-column>
        <vxe-table-column
          field="qxIcon"
          title="菜单Icon"
          sortable
        ></vxe-table-column>
        <vxe-table-column
          field="qxUrl"
          title="菜单路径"
          sortable
        ></vxe-table-column>
        <vxe-table-column
          field="qxMethod"
          title="方法"
          sortable
        ></vxe-table-column>
        <vxe-table-column
          field="qxIconColor"
          title="图标颜色"
          sortable
        ></vxe-table-column>
        <vxe-table-column
          field="qxBottomText"
          title="菜单下方文本"
          sortable
        ></vxe-table-column>
        <vxe-table-column
          field="status"
          title="状态"
          sortable
          formatter="formatStatus"
        ></vxe-table-column>
        <vxe-table-column
          field="createTime"
          title="创建时间"
          sortable
        ></vxe-table-column>
        <vxe-table-column
          field="qxBak"
          title="备注"
          sortable
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
      <vxe-modal
        v-if="dialogFormVisibleedit"
        v-model="dialogFormVisibleedit"
        title="应用维护"
        width="700"
        height="800"
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
import { fetchList } from "@/api/weixin/qx";
import { mapGetters } from "vuex";
import Edit from "./edit";
export default {
  components: { Edit },
  name: "weixin-qx-index",
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
      dialogFormVisibleedit: false
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
    }
  }
};
</script>
<style lang="scss" scoped>
.weixin-qx-index {
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