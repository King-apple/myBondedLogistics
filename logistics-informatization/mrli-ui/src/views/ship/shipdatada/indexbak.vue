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
                       <template slot="status" slot-scope="scope">
          <span v-if="scope.row.status==0||scope.row.status==null" style="color:red;">未审核</span>
          <span v-if="scope.row.status==1" style="color:blue;">已审核</span>
        </template>
             <template slot-scope="{row,type,size}" slot="menu">
             
            <el-button icon="el-icon-check" :size="size" :type="type" @click.stop="handleApt(row)">详情</el-button>
          </template>
          </avue-crud>
           <el-dialog v-if="dialogFormVisible"
        :visible.sync="dialogFormVisible"
        title="船舶档案"
        width="1000px"
        @close="dictItemVisible">
        <Form :objrow="objrow" @getList="selectTable" @dictItemVisible="dictItemVisible" readonly="true"></Form>
      </el-dialog>
        </basic-container>
    </div>
</template>

<script>
    import {fetchList, getObj, addObj, putObj, delObj} from '@/api/ship/shipdatada'
    import {tableOption} from '@/const/crud/ship/shipdatada'
    import {mapGetters} from 'vuex'
    import Form from "./shipdatadadetail";

    export default {
        name: 'shipdatada',
         components:{Form},
        data() {
            return {
                searchForm: {},
                tableData: [],
                dialogFormVisible:false,
                page: {
                    total: 0, // 总页数
                    currentPage: 1, // 当前页数
                    pageSize: 20 // 每页显示多少条
                },
                tableLoading: false,
                tableOption: tableOption,
                objrow:{}//编辑弹出form方法
            }
        },
        computed: {
            ...mapGetters(['permissions']),
            permissionList() {
                return {
                    addBtn: this.vaildData(this.permissions.ship_shipdatada_add, false),
                    delBtn: this.vaildData(this.permissions.ship_shipdatada_del, false),
                    editBtn: this.vaildData(this.permissions.ship_shipdatada_edit, false)
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
                    console.log(this.tableData);
                    this.page.total = response.data.data.total
                    this.tableLoading = false
                }).catch(() => {
                    this.tableLoading=false
                })
            },
            selectTable(){
                this.getList(this.page,null);
            },
            rowDel: function (row, index) {
                this.$confirm('是否确认删除ID为' + row.callNo, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function () {
                    return delObj(row.callNo)
                }).then(data => {
                    this.$message.success('删除成功')
                    this.getList(this.page)
                })
            },
             //点击编辑弹出form-------开始
            handleApt:function(row) {
            this.objrow=row;
            console.log(this.objrow.cshipNam);
            console.log(this.objrow.status);
            this.dialogFormVisible = true;
            },
            dictItemVisible: function() {
            this.objrow={};
            this.dialogFormVisible = false;
            },
            //--------------结束
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