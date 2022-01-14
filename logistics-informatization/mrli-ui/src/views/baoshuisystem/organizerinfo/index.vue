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
  <div class="execution">
    <basic-container>
      <avue-crud
        ref="crud"
        :page.sync="page"
        :data="tableData"
        :permission="permissionList"
        :table-loading="tableLoading"
        :option="tableOption"
        @on-load="getList"
        @search-change="searchChange"
        @refresh-change="refreshChange"
        @size-change="sizeChange"
        @current-change="currentChange"
        @row-update="handleUpdate"
        @row-save="handleSave"
        @row-del="rowDel"
        @selection-change="selectionChange"
      >
        <template slot="menuLeft" v-if="permissions.organizerinfo_user_add">
          <el-button
            type="warning"
            size="small"
            icon="el-icon-edit"
            @click="getGrList"
            application_business_trip
            >绑定用户
          </el-button>
        </template>
        <template slot="menuLeft" v-if="permissions.organizerinfo_user_view">
          <el-button
            type="success"
            size="small"
            icon="el-icon-edit"
            @click="getBindList"
            application_business_trip
            >查看已绑定用户
          </el-button>
        </template>
      </avue-crud>

      <div v-if="dialogFormVisible">
        <el-dialog
          :visible.sync="dialogFormVisible"
          title="用户信息表"
          width="80%"
          @close="dictItemVisible"
        >
          <user
            @dictItemVisible="dictItemVisible"
            :organizerData="multipleSelection"
          ></user>
        </el-dialog>
      </div>

      <div v-if="dialogBindVisible">
        <el-dialog
          :visible.sync="dialogBindVisible"
          title="用户信息表"
          width="80%"
          @close="dictItemVisible"
        >
          <userbind
            @dictItemVisible="dictItemVisible"
            :organizerData="multipleSelection"
          ></userbind>
        </el-dialog>
      </div>
    </basic-container>
  </div>
</template>

<script>
import { fetchList, getObj, addObj, putObj, delObj } from '@/api/baoshuisystem/organizerinfo'
import { tableOption } from '@/const/crud/baoshuisystem/organizerinfo'
import { mapGetters } from 'vuex'
import user from "./userindex";
import userbind from "./userbindindex";

export default {
  components: {
    user, userbind
  },
  name: 'organizerinfo',
  data() {
    return {
      searchForm: {},
      tableData: [],
      multipleSelection: [],
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      tableLoading: false,
      tableOption: tableOption,
      dialogFormVisible: false,
      dialogBindVisible: false,
    }
  },
  computed: {
    ...mapGetters(['permissions']),
    permissionList() {
      return {
        addBtn: this.vaildData(this.permissions.organizerinfo_add, false),
        delBtn: this.vaildData(this.permissions.organizerinfo_del, false),
        editBtn: this.vaildData(this.permissions.organizerinfo_edit, false)
      };
    }
  },
  methods: {
    getList(page, params) {
      this.tableLoading = true
      fetchList(Object.assign({
        current: page.currentPage,
        size: page.pageSize
      }, params, this.searchForm)).then(response => {
        this.tableData = response.data.data.records
        this.page.total = response.data.data.total
        this.tableLoading = false
      }).catch(() => {
        this.tableLoading = false
      })
    },
    rowDel: function (row, index) {
      this.$confirm('是否确认删除承办单位编号为:' + row.organizerCode + '的数据', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function () {
        return delObj(row.fid)
      }).then(data => {
        this.$message.success('删除成功')
        this.getList(this.page)
      })
    },
    handleUpdate: function (row, index, done, loading) {
      putObj(row).then(data => {
        this.$message.success('修改成功')
        done()
        this.getList(this.page)
      }).catch(() => {
        loading();
      });
    },
    handleSave: function (row, done, loading) {
      addObj(row).then(data => {
        this.$message.success('添加成功')
        done()
        this.getList(this.page)
      }).catch(() => {
        loading();
      });
    },
    sizeChange(pageSize) {
      this.page.pageSize = pageSize
    },
    currentChange(current) {
      this.page.currentPage = current
    },
    searchChange(form, done) {
      this.searchForm = form
      this.page.currentPage = 1
      this.getList(this.page, form)
      done()
    },
    refreshChange() {
      this.getList(this.page)
    },
    getGrList: function () {
      if (this.multipleSelection.length != 1) {
        this.$message.success("请选择一个承办单位");
        return;
      }
      this.dialogFormVisible = true;
    },
    getBindList: function () {
      if (this.multipleSelection.length != 1) {
        this.$message.success("请选择一个承办单位");
        return;
      }
      this.dialogBindVisible = true;
    },
    dictItemVisible: function () {
      this.dialogFormVisible = false;
      this.dialogBindVisible = false;
    },
    selectionChange(list) {
      this.multipleSelection = list;
    }
  }
}
</script>
