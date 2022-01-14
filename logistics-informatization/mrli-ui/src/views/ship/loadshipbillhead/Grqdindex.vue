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
        @selection-change="selectionChange"
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
        <template slot="menuLeft">
          <el-button type="primary" icon="el-icon-check" size="mini" plain @click="checkCar()">确认接收</el-button>
        </template>
      </avue-crud>
    </basic-container>
  </div>
</template>

<script>
import {
  fetchList,
  getObj,
  addObj,
  putObj,
  delObj
} from "@/api/ship/cdbillheadtemp";
import {   
  addHead 
} from "@/api/ship/loadshipbillhead";
import { tableOption } from "@/const/crud/ship/cdbillheadtemp";
import { mapGetters } from "vuex";

export default {
  name: "cdbillheadtemp",
  data() {
    return {
      searchForm: {},
      tableData: [],
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 10 // 每页显示多少条
      },
      tableLoading: false,
      tableOption: tableOption,
      multipleSelection: []
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
    permissionList() {
      return {
        addBtn: this.vaildData(this.permissions.ship_cdbillheadtemp_add, false),
        delBtn: this.vaildData(this.permissions.ship_cdbillheadtemp_del, false),
        editBtn: this.vaildData(
          this.permissions.ship_cdbillheadtemp_edit,
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
    selectionChange(list){
       this.multipleSelection=list;
      },
    checkCar: function() {
      let that = this;
      if (that.multipleSelection.length == 0) {
        this.$message.success("请至少选择一辆车");
        return;
      }
      this.$confirm("是否确认接收", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() { 
            return addHead(that.multipleSelection);          
          
        })
        .then(data => {
          this.$refs.crud.toggleSelection();
          this.$message.success(data.data.data);
          this.$emit("dictItemVisible");
        });
    }
  }
};
</script>