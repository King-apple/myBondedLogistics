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
  <div class="weixin-group-qxSelect">
    <basic-container>
      <vxe-form :data="formObj">
        <vxe-form-item title="菜单名称" field="qxName" size="mini" span="16">
          <vxe-input v-model="formObj.qxName" clearable></vxe-input>
        </vxe-form-item>

        <vxe-form-item span="8" size="mini" align="center">
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
        height="600px"
        ref="tableQx"
        @cell-click="selectQx"
        :checkbox-config="{ trigger: 'row' }"
      >
        <vxe-table-column type="checkbox" width="60"></vxe-table-column>
        <vxe-table-column
          field="typeCodeName"
          title="类别名称"
        ></vxe-table-column>
        <vxe-table-column field="qxName" title="菜单名称"></vxe-table-column>
        <vxe-table-column
          field="qxIconColor"
          title="图标颜色"
        ></vxe-table-column>
        <vxe-table-column
          field="qxBottomText"
          title="菜单下方文本"
        ></vxe-table-column>
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
import { fetchList } from "@/api/weixin/qx";
import { qxAndGroupList, saveQx } from "@/api/weixin/group";
import { mapGetters } from "vuex";
export default {
  props: ["groupId"],
  name: "weixin-group-qxSelect",
  data() {
    return {
      formObj: {},
      tableData: [],
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 30 // 每页显示多少条
      },
      tableload: false,
      groupList: []
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() {
    qxAndGroupList(
      Object.assign({
        groupId: this.groupId
      })
    )
      .then(response => {
        this.groupList = response.data.data;
        this.getDate();
      })
      .catch(() => {});
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
          //判断选中

          if (this.groupList != null) {
            for (var i = 0; i < this.groupList.length; i++) {
              if (this.tableData != null) {
                for (var n = 0; n < this.tableData.length; n++) {
                  if (this.groupList[i].qxId == this.tableData[n].pkid) {
                    this.$refs.tableQx.toggleCheckboxRow(this.tableData[n]);
                  }
                }
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
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
    },
    selectTable() {
      this.tablePage.currentPage = 1;
      this.getDate();
    },
    resetForm() {},
    selectQx({ row }) {
      saveQx(
        Object.assign({
          groupId: this.groupId,
          qxId: row.pkid,
          ifCheck: this.$refs.tableQx.isCheckedByCheckboxRow(row)
        })
      )
        .then(response => {
             this.$emit("selectList");
        })
        .catch(() => {});
    }
  }
};
</script>
<style lang="scss" scoped>
.weixin-group-qxSelect {
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