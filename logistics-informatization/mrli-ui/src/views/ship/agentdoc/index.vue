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
      <vxe-form :data="searchForm" @submit="searchEvent" @reset="resetEvent" size="mini">
        <vxe-form-item title="客户代码：" field="agentCod" size="mini" span="4">
          <vxe-input v-model="searchForm.agentCod" clearable></vxe-input>
        </vxe-form-item>
         <vxe-form-item title="客户名称：" field="agentNam" size="mini" span="4">
          <vxe-input v-model="searchForm.agentNam" clearable></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="状态：" field="useId" size="mini" span="4">
          <vxe-select v-model="searchForm.useId" clearable>
            <vxe-option value="1" label="已审核"></vxe-option>
            <vxe-option value="0" label="未审核"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item span="10" size="mini" align="center">
          <vxe-button type="submit" status="primary" icon="fa fa-search">查询</vxe-button>
          <vxe-button type="reset" icon="fa fa-repeat">重置</vxe-button>
        </vxe-form-item>
      </vxe-form>
      <avue-crud
        ref="crud"
        :page="page"
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
      >
       <template slot="useId" slot-scope="scope">
          <span v-if="scope.row.useId=='0'||scope.row.useId==null"><font color="blue">未审核</font></span>
          <span v-if="scope.row.useId=='1'"><font color="red">审核</font></span>
        </template>
        <template slot-scope="{row,type,size}" slot="menu">
          <el-button
             v-if="row.useId=='0'||row.useId==null"
             icon="fa fa-check-square-o"
            :size="size"
            :type="type"
            @click="sh(row)"
          >审核</el-button>
        </template>
        </avue-crud>
    </basic-container>
  </div>
</template>

<script>
import { fetchList, getObj, addObj, putObj, delObj,sh} from "@/api/ship/agentdoc";
import { tableOption } from "@/const/crud/ship/agentdoc";
import { mapGetters } from "vuex";

export default {
  name: "agentdoc",
  data() {
    return {
      searchForm: {agentCod:null,userId:null,agentNam:null},
      tableData: [],
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      tableLoading: false,
      tableOption: tableOption
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
    permissionList() {
      return {
        addBtn: this.vaildData(this.permissions.ship_agentdoc_add, false),
        delBtn: this.vaildData(this.permissions.ship_agentdoc_del, false),
        editBtn: this.vaildData(this.permissions.ship_agentdoc_edit, false)
      };
    }
  },
  methods: {
    getList(page, params) {
      this.tableLoading = true;
      fetchList(
        Object.assign(
          {
            current: page.currentPage,
            size: page.pageSize
          },
          this.searchForm
        )
      )
        .then(response => {
          this.tableData = response.data.data.records;
          this.page.total = response.data.data.total;
          this.tableLoading = false;
        })
        .catch(() => {
          this.tableLoading = false;
        });
    },
    rowDel: function(row, index) {
      this.$confirm("是否确认删除ID为" + row.agentCod, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return delObj(row.agentCod);
        })
        .then(data => {
          this.$message.success("删除成功");
          this.getList(this.page);
        });
    },
    handleUpdate: function(row, index, done, loading) {
      putObj(row)
        .then(data => {
          this.$message.success("修改成功");
          done();
          this.getList(this.page);
        })
        .catch(() => {
          loading();
        });
    },
     searchEvent() {
      this.page= {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      };
      this.getList(this.page);
    },
     resetEvent() {},
    handleSave: function(row, done, loading) {
      addObj(row)
        .then(data => {
          this.$message.success("添加成功");
          done();
          this.getList(this.page);
        })
        .catch(() => {
          loading();
        });
    },
    sizeChange(pageSize) {
      this.page.pageSize = pageSize;
    },
    sh(row){
    this.$confirm("是否审核" + row.agentNam, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return sh(row.agentCod);
        })
        .then(data => {
          this.$message.success("审核成功");
          this.getList(this.page);
        });
    },
    currentChange(current) {
      this.page.currentPage = current;
    },
    searchChange(form, done) {
      this.searchForm = form;
      this.getList(this.page, form);
      done();
    },
    refreshChange() {
      this.getList(this.page);
    }
  }
};
</script>