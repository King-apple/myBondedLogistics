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
        <el-form-item label="合同日期">
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
          <el-button type="primary" @click="getGrList">新增</el-button>
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
                       @row-del="rowDel">
                        <template slot="zqId" slot-scope="scope">
          <span v-if="scope.row.zqId=='0'">非直取</span>
          <span v-if="scope.row.zqId=='1'">直取</span>
        </template>
            </avue-crud>
            <div v-if="dialogFormVisible">
        <el-dialog
          :visible.sync="dialogFormVisible"
          title="出库申请单"
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
    import {fetchList, getObj, addObj, putObj, delObj} from '@/api/caccentre/transportcontract'
    import {tableOption} from '@/const/crud/caccentre/transportcontract'
    import {mapGetters} from 'vuex'
    import Grqd from "./Grqdindex";
import moment from "moment";
    export default {
        components: {
    Grqd
  },
        name: 'transportcontract',
        data() {
            return {
                searchForm: {tim: moment(new Date()).format("YYYY-MM-DD") },
                tableData: [],
                page: {
                    total: 0, // 总页数
                    currentPage: 1, // 当前页数
                    pageSize: 20 // 每页显示多少条
                },
                tableLoading: false,
                tableOption: tableOption,
                 dialogFormVisible: false
            }
        },
        computed: {
            ...mapGetters(['permissions']),
            permissionList() {
                return {
                    addBtn: this.vaildData(this.permissions.caccentre_transportcontract_add, false),
                    delBtn: this.vaildData(this.permissions.caccentre_transportcontract_del, false),
                    editBtn: this.vaildData(this.permissions.caccentre_transportcontract_edit, false)
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
                    this.tableLoading = false
                }).catch(() => {
                    this.tableLoading=false
                })
            },
            rowDel: function (row, index) {
                this.$confirm('是否确认删除ID为' + row.contNo, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function () {
                    return delObj(row.contNo)
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
            },
            getGrList: function() {
      this.dialogFormVisible = true;
    },
    dictItemVisible: function() {
      this.getList(this.page);
      this.dialogFormVisible = false;
    }
        }
    }
</script>