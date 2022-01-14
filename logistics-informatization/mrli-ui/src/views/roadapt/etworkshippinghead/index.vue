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
            v-model="searchForm.sendTim"
            type="date"
            format="yyyyMMdd"
            value-format="yyyyMMdd"
            placeholder="选择日期"
          ></el-date-picker>
        </el-form-item>
         
        <el-form-item label="车号">
          <el-input v-model="searchForm.trkTrkno" placeholder="请输入车号"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getList">查询</el-button> 
        </el-form-item>
      </el-form>
            <avue-crud ref="crud"
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
                       @row-click="handleRowClick">
                       <template slot="tradeId" slot-scope="scope">
                       <span v-if="scope.row.tradeId=='0'">外贸</span>
                       <span v-if="scope.row.tradeId=='1'">内贸</span>
                        </template>
                        <template slot="unloadId" slot-scope="scope">
                       <span v-if="scope.row.unloadId=='+'">装</span>
                       <span v-if="scope.row.unloadId=='-'">卸</span>
                        </template>
            </avue-crud>
             <avue-crud ref="crudb"
                       :page="pageSub"
                       :data="tableDatab"
                       :permission="permissionList"
                       :table-loading="subTableLoading"
                       :option="tableOptionb"
                       @on-load="getList"
                       @search-change="searchChange"
                       @refresh-change="refreshChange"
                       @size-change="sizeChange"
                       @current-change="currentChange"
                       @row-update="handleUpdate"
                       @row-save="handleSave"
                       @row-del="rowDel">
            </avue-crud>
        </basic-container>
    </div>
</template>

<script>
    import {fetchList, getObj, addObj, putObj, delObj} from '@/api/roadapt/etworkshippinghead'
     import {fetchSubList } from '@/api/roadapt/etworkshipping'
    import {tableOption} from '@/const/crud/roadapt/etworkshippinghead'
    import {tableOptionb} from '@/const/crud/roadapt/etworkshipping'
    import {mapGetters} from 'vuex'
    import moment from "moment";

    export default {
        name: 'etworkshippinghead',
        data() {
            return {
                searchForm: {sendTim: moment(new Date()).format("YYYYMMDD"),trkTrkno:""},
                tableData: [],
                tableDatab: [],
                page: {
                    total: 0, // 总页数
                    currentPage: 1, // 当前页数
                    pageSize: 20 // 每页显示多少条
                },
                pageSub: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
                tableLoading: false,
                subTableLoading:false,
                tableOption: tableOption,
                tableOptionb: tableOptionb
            }
        },
        computed: {
            ...mapGetters(['permissions']),
            permissionList() {
                return {
                    addBtn: this.vaildData(this.permissions.roadapt_etworkshippinghead_add, false),
                    delBtn: this.vaildData(this.permissions.roadapt_etworkshippinghead_del, false),
                    editBtn: this.vaildData(this.permissions.roadapt_etworkshippinghead_edit, false)
                };
            }
        },
        methods: {
            getList(page, params) {
                this.tableLoading = true
                fetchList(Object.assign({
                    current: page.currentPage,
                    size: page.pageSize
                }, params, this.searchForm )).then(response => {
                    this.tableData = response.data.data.records
                    this.page.total = response.data.data.total
                     this.getSubList(this.pageSub, response.data.data.records[0].pkid);
                    this.tableLoading = false
                }).catch(() => {
                    this.tableLoading=false
                })
            },
            getSubList(pageSub, pkid) {
      this.subTableLoading = true;
     
      fetchSubList(
        Object.assign(
          {
            current: pageSub.currentPage,
            size: pageSub.pageSize
          },
          { headPkid: pkid }
        )
      )
        .then(response => {
          this.tableDatab = response.data.data.records;
          this.pageSub.total = response.data.data.total; 
          this.subTableLoading = false;
        })
        .catch(() => {
          this.subTableLoading = false;
        });
    },
            handleRowClick: function(row, event, column) {
      this.subTableLoading = true;
      fetchSubList(
        Object.assign(
          {
            current: this.pageSub.currentPage,
            size: this.pageSub.pageSize
          },
          { headPkid: row.pkid }
        )
      )
        .then(response => {
          this.tableDatab = response.data.data.records;
          this.pageSub.total = response.data.data.total; 
          this.subTableLoading = false;
        })
        .catch(() => {
          this.subTableLoading = false;
        });
    },
            rowDel: function (row, index) {
                this.$confirm('是否确认删除ID为' + row.pkid, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function () {
                    return delObj(row.pkid)
                }).then(data => {
                    this.$message.success('删除成功')
                    this.getList(this.page)
                })
            },
            handleUpdate: function (row, index, done,loading) {
                putObj(row).then(data => {
                    this.$message.success('修改成功')
                    done()
                    this.getList(this.page)
                }).catch(() => {
                    loading();
                });
            },
            handleSave: function (row, done,loading) {
                addObj(row).then(data => {
                    this.$message.success('添加成功')
                    done()
                    this.getList(this.page)
                }).catch(() => {
                    loading();
                });
            },
            sizeChange(pageSize){
                this.page.pageSize = pageSize
            },
            currentChange(current){
                this.page.currentPage = current
            },
            searchChange(form, done) {
                this.searchForm = form
                this.getList(this.page, form)
                done()
            },
            refreshChange() {
                this.getList(this.page)
            }
        }
    }
</script>