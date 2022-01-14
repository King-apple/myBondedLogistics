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
        <el-form-item label="日期">
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
        <el-form-item>
          <el-button type="primary" @click="getGrList">接收港融</el-button>
        </el-form-item>
      </el-form>
      <el-divider content-position="left">清单头信息</el-divider>
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
        >
        <template slot="inoutId" slot-scope="scope">
          <span v-if="scope.row.inoutId=='0'">出口</span>
          <span v-if="scope.row.inoutId=='1'">进口</span>
        </template>
        <template slot="tradeId" slot-scope="scope">
          <span v-if="scope.row.tradeId=='0'">外贸</span>
          <span v-if="scope.row.tradeId=='1'">内贸</span>
        </template>
        <template slot="buildId" slot-scope="scope">
          <span v-if="scope.row.buildId=='0'">未交</span>
          <span v-if="scope.row.buildId=='1'">已交</span>
        </template>
        <template slot="danId" slot-scope="scope">
          <span v-if="scope.row.danId=='0'">未报</span>
          <span v-if="scope.row.danId=='1'">已报</span>
        </template>
        <template slot-scope="{row,type,size}" slot="menu">
          <el-button  v-if="row.upId!='1'" icon="el-icon-check" :size="size" :type="type" @click="sbjt(row)">上报集团</el-button>
           <el-button  v-if="row.upId=='1'" icon="el-icon-check" :size="size" :type="type" disabled >已报集团</el-button>
        </template>
      </avue-crud>
      <el-divider content-position="left">清单体信息</el-divider>
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
          title="港融装船清单"
          width="90%"
          @close="dictItemVisible"
        >
          <Grqd @dictItemVisible="dictItemVisible"></Grqd>
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
  checkjt
} from "@/api/ship/loadshipbillhead";
import { fetchListb } from "@/api/ship/loadshipbillbody";
import { tableOption } from "@/const/crud/ship/loadshipbillhead";
import { tableOptionb } from "@/const/crud/ship/loadshipbillbody";
import { mapGetters } from "vuex";
import Grqd from "./Grqdindex";
import moment from "moment";
export default {
  components: {
    Grqd
  },
  name: "loadshipbillhead",
  data() {
    return {
      searchForm: { tim: moment(new Date()).format("YYYY-MM-DD") },
      tableData: [],
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
      dialogFormVisible: false
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
    permissionList() {
      return {
        addBtn: this.vaildData(
          this.permissions.ship_loadshipbillhead_add,
          false
        ),
        delBtn: this.vaildData(
          this.permissions.ship_loadshipbillhead_del,
          false
        ),
        editBtn: this.vaildData(
          this.permissions.ship_loadshipbillhead_edit,
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
          this.getListb(
            this.pageb,
            response.data.data.records[0].shipBillNo,
            response.data.data.records[0].verNo
          );
          this.tableLoading = false;
        })
        .catch(() => {
          this.tableLoading = false;
        });
    },
    getListb(pageb, shipBillNo, verNo) {
      this.tableLoading = true;

      fetchListb(
        Object.assign(
          {
            current: pageb.currentPage,
            size: pageb.pageSize
          },
          { shipBillNo: shipBillNo, verNo: verNo }
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
      this.$confirm("是否确认删除ID为" + row.shipBillNo, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return delObj(row.shipBillNo);
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
    handleRowClick: function(row, event, column) {
      this.tableLoading = true;
      fetchListb(
        Object.assign(
          {
            current: this.pageb.currentPage,
            size: this.pageb.pageSize
          },
          { shipBillNo: row.shipBillNo, verNo: row.verNo }
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
    getGrList: function() {
      this.dialogFormVisible = true;
    },
    dictItemVisible: function() {
      this.getList(this.page);
      this.dialogFormVisible = false;
    },
    sbjt: function(row) {
      this.tableLoading = true;
      if (row.tradeId == "1" && row.zfzt == "5") {
        if (
          row.buildId == "0" ||
          row.buildId == null ||
          row.buildId == undefined ||
          row.buildId == ""
        ) {
          this.$message.error("港建费未确认");
          return;
        }
      }
      this.$confirm("是否确认上报", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return checkjt(row.shipBillNo, row.verNo);
        })
        .then(data => {
          this.$message.success(data.data.data);
         this.getList(this.page);
        this.tableLoading = false;
        }); 
        
      
    }
  }
};
</script>