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
      <el-form :inline="true" :model="searchForm" class="demo-form-inline">
        <el-form-item label="计划日期">
          <el-date-picker
            v-model="searchForm.tim"
            type="date"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
            placeholder="选择日期"
          ></el-date-picker>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="getList">查询</el-button>
        </el-form-item>
      </el-form>
      <el-divider content-position="left">计划头信息</el-divider>
      <el-divider content-position="left"></el-divider>
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
        @row-click="handleRowClick"
      >
        <template slot="tradeId" slot-scope="scope">
          <span v-if="scope.row.tradeId=='0'">外贸</span>
          <span v-if="scope.row.tradeId=='1'">内贸</span>
        </template>
        <template slot="up" slot-scope="scope">
          <span v-if="scope.row.up=='0'||scope.row.up==null">未审核</span>
          <span v-if="scope.row.up=='1'">已审核</span>
          <span v-if="scope.row.up=='9'">已签章</span>
        </template>
        <template slot-scope="{row,type,size}" slot="menu">
          <el-button
            v-if="row.up=='0'||row.up==null"
            icon="el-icon-check"
            :size="size"
            :type="type"
            @click="shjh(row)"
          >审核</el-button>
          <el-button
            v-if="row.up=='0'||row.up==null"
            icon="el-icon-close"
            :size="size"
            :type="type"
            @click="zfjh(row)"  
          >拒绝</el-button>
          <el-button
            v-if="row.up=='9'"
            icon="el-icon-view"
            :size="size"
            :type="type"
            @click="ckjh(row)"  
          >查看</el-button>
        </template>
      </avue-crud>
      <el-divider content-position="left">计划货信息</el-divider>
      <el-divider content-position="left"></el-divider>
      <avue-crud
        ref="crudb"
        :page="pageb"
        :data="tableDatab"
        :permission="permissionList"
        :table-loading="tableLoading"
        :option="tableOptionb"
        @on-load="getList"
        @search-change="searchChange"
        @refresh-change="refreshChange"
        @size-change="sizeChange"
        @current-change="currentChange"
        @row-update="handleUpdate"
        @row-save="handleSave"
        @row-del="rowDel"
      >
        <template slot="vportId" slot-scope="scope">
          <span v-if="scope.row.vportId=='0'">否</span>
          <span v-if="scope.row.vportId=='1'">是</span>
        </template>
      </avue-crud>
      <div v-if="dialogFormVisible">
        <el-dialog
          :visible.sync="dialogFormVisible"
          title="作废"
          width="20%"           
          :show-close="false"
          @close="dictItemVisible"
        >
          <avue-form ref="form" :option="option" v-model="objrow" @submit="handleSubmit">
            <template slot="menuForm">
              <el-button type="primary" icon="el-icon-close" plain @click="closeWindows">取消</el-button>
            </template>
          </avue-form>
        </el-dialog>
      </div>
    </basic-container>
  </div>
</template>

<script>
import {
  fetchList,
  getObj,
  addObj,
  putObj,
  delObj,
  checkjh,
  zfjh
} from "@/api/caccentre/transporthead";
import { fetchListb } from "@/api/caccentre/transportcargo";
import { tableOption } from "@/const/crud/caccentre/transporthead";
import { tableOptionb } from "@/const/crud/caccentre/transportcargo";
import { mapGetters } from "vuex";
import moment from "moment";
export default {
  name: "transporthead",
  data() {
    return {
      searchForm: { tim: moment(new Date()).format("YYYY-MM-DD") },
      tableData: [],
      objrow:{},
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 10 // 每页显示多少条
      },
      tableLoading: false,
      tableOption: tableOption,
      tableDatab: [],
      pageb: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 10 // 每页显示多少条
      },
      tableLoadingb: false,
      tableOptionb: tableOptionb,
      dialogFormVisible: false,
      option: {
        labelWidth: 100,
        emptyBtn: false,
        column: [
          {
            type: "textarea",
            label: "拒绝原因",
            prop: "contTxt",
            span: 24
          }
        ]
      }
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
    permissionList() {
      return {
        addBtn: this.vaildData(
          this.permissions.caccentre_transporthead_add,
          false
        ),
        delBtn: this.vaildData(
          this.permissions.caccentre_transporthead_del,
          false
        ),
        editBtn: this.vaildData(
          this.permissions.caccentre_transporthead_edit,
          false
        )
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
          params,
          this.searchForm
        )
      )
        .then(response => {
          this.tableData = response.data.data.records;
          this.page.total = response.data.data.total;
          this.getListb(this.pageb, response.data.data.records[0].informNo);
          this.tableLoading = false;
        })
        .catch(() => {
          this.tableLoading = false;
        });
    },
    getListb(pageb, informNo) {
      this.tableLoading = true;

      fetchListb(
        Object.assign(
          {
            current: pageb.currentPage,
            size: pageb.pageSize
          },
          { informNo: informNo }
        )
      )
        .then(response => {
          this.tableDatab = response.data.data.records;
          this.pageb.total = response.data.data.total;
          this.tableLoading = false;
        })
        .catch(() => {
          this.tableLoading = false;
        });
    },
    handleRowClick: function(row, event, column) {
      this.tableLoading = true;
      fetchListb(
        Object.assign(
          {
            current: this.pageb.currentPage,
            size: this.pageb.pageSize
          },
          { informNo: row.informNo }
        )
      )
        .then(response => {
          this.tableDatab = response.data.data.records;
          this.pageb.total = response.data.data.total;
          this.tableLoading = false;
        })
        .catch(() => {
          this.tableLoading = false;
        });
    },
    rowDel: function(row, index) {
      this.$confirm("是否确认删除ID为" + row.informNo, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return delObj(row.informNo);
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
    },
    shjh: function(row) {
      this.tableLoading = true;

      this.$confirm("是否确认审核", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return checkjh(row.informNo);
        })
        .then(data => {
          this.$message.success(data.data.data);
          this.getList(this.page);
          this.tableLoading = false;
        });
    },
    zfjh: function(row) {
      this.objrow = row;
      this.dialogFormVisible = true;
    },
    dictItemVisible: function() {
      this.$refs.crud.toggleSelection();
      this.getList(this.page);
      this.dialogFormVisible = false;
    },
    closeWindows: function() {
      this.$refs.form.resetForm();
      this.dictItemVisible();
    },
    handleSubmit: function(obj) { 
      zfjh(obj.informNo, obj.contTxt)
        .then(data => {
          this.$message.success(data.data.data);
           this.closeWindows();
          this.getList(this.page);
        });
    },
    ckjh: function(row) { 
      window.open(row.fileUrl);
    }
  }
};
</script>