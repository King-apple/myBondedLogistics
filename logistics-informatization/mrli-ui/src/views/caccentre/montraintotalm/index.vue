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
      <el-row style="height:px">
        <el-col :span="6">
          <div class="grid-content bg-purple">
            <el-form>
              <el-form-item label="统计月份：">
                <el-date-picker
                  v-model="searchForm.workTim"
                  type="month"
                  format="yyyyMM"
                  value-format="yyyyMM"
                  placeholder="选择月份"
                ></el-date-picker>
              </el-form-item>
            </el-form>
          </div>
        </el-col>
        <el-col :span="18">
          <div class="grid-content bg-purple">
            <el-button type="primary" icon="el-icon-search" size="small" @click="getList">查询</el-button>
            <el-button type="primary" icon="el-icon-s-tools" size="small" @click="dosjcl">数据处理</el-button>
          </div>
        </el-col>
      </el-row>
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
      ></avue-crud>
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
  sjcl,
  sjpd
} from "@/api/caccentre/montraintotalm";
import { tableOption } from "@/const/crud/caccentre/montraintotalm";
import { mapGetters } from "vuex";
import moment from "moment";
export default {
  name: "montraintotalm",
  data() {
    return {
      searchForm: { workTim: moment(new Date()).format("YYYYMM") },
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
        addBtn: this.vaildData(
          this.permissions.caccentre_montraintotalm_add,
          false
        ),
        delBtn: this.vaildData(
          this.permissions.caccentre_montraintotalm_del,
          false
        ),
        editBtn: this.vaildData(
          this.permissions.caccentre_montraintotalm_edit,
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
      this.$confirm("是否确认删除ID为" + row.workDte, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return delObj(row.workDte);
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
    dosjcl() {
      let that = this;
      this.tableLoading = true;
      sjpd(this.searchForm.workTim)
        .then(data => {
          if (data.data.data == "1") {
            this.$confirm("该月数据已经产生，是否确认重新处理", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning"
            })
              .then(
                function() {
                  return sjcl(that.searchForm.workTim);
                },
                function() {
                  that.tableLoading = false;
                }
              )
              .then(data => {
                this.$message.success("处理成功");
                this.getList(this.page);
                this.tableLoading = false;
              });
          } else {
            sjcl(this.searchForm.workTim).then(data => {
              this.$message.success("处理成功");
              this.getList(this.page);
              this.tableLoading = false;
            });
          }
        })
        .catch(() => {
          this.tableLoading = false;
        });
    }
  }
};
</script>