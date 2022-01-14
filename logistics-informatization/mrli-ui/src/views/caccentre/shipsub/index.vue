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
            
            <el-container>
            <avue-form ref="form" v-model="searchForm" :option="option" >
           <template slot-scope="scope" slot="menuForm">
                    
                     <el-button type="primary" @click="handleSubmit">新增</el-button>
           </template>
            </avue-form>
            </el-container>
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
                       <template slot-scope="{row,type,size}" slot="menu">
          <el-button icon="el-icon-close" :size="size" :type="type" @click.stop="rowDel(row)">删除</el-button>
        </template>
            </avue-crud>
        </basic-container>
    </div>
</template>

<script>
    import {fetchList, getObj, addObj, putObj, delObj} from '@/api/caccentre/shipsub'
    import request from "@/router/axios";
    import {tableOption} from '@/const/crud/caccentre/shipsub'
    import {mapGetters} from 'vuex'
    import moment from "moment";

    export default {
        props: ["objrowsub"],//加载传过来的
        data() {
            return {
                searchForm: {},
                tableData: [],
                page: {
                    total: 0, // 总页数
                    currentPage: 1, // 当前页数
                    pageSize: 20 // 每页显示多少条
                },
                tableLoading: false,
                tableOption: tableOption,
                 option: {
                    "submitBtn": false,
                    "emptyBtn": false,
                    "menu": true,
                    "border": true,
                    "size":"small",
                    "stripe": true,
                    "menuAlign": "center",
                    "align": "center",
                    "column":[ 
                        {
                            
                            "label": "船名",
                            "prop": "shipNam",
                            "span":12,
                            "readonly":true
                        },
                        
                        {
                            label: "单位", //c_unit_nam
                            span: 12,
                            prop: "unitNam",
                            type: "select",
                            props: {
                            label: "codeName",
                            value: "codeId"
                            },
                            remote: true,
                            typeslot: true,
                            dicUrl: `/basecode/basecod/unit/{{key}}`,
                            change: ({ value, column, item, event }) => {
                            if (value != null) {
                                var values = value.split(",");
                                if(values.length>1){
                                this.searchForm.unitNam = values[1];
                                this.searchForm.unitCod = values[0];
                                
                                }else{
                                this.searchForm.unitNam = values[0];
                                }
                            }
                            }
                        },
                        
                    ]
                },
            }
        },
        computed: {
            ...mapGetters(['permissions']),
            permissionList() {
                return {
                    addBtn: this.vaildData(this.permissions.caccentre_shipsub_add, false),
                    delBtn: this.vaildData(this.permissions.caccentre_shipsub_del, false),
                    editBtn: this.vaildData(this.permissions.caccentre_shipsub_edit, false)
                };
            }
        },
        mounted: function() {
            this.searchForm.shipNam = this.objrowsub.shipNam;
        
        },
        methods: {
            getList(page, params) {
                this.tableLoading = true
                this.searchForm.shipNo=this.objrowsub.shipNo;//将传过来的整行内容中的shipno赋值给本页searchForm，执行方法传给后台
                this.searchForm.unitNam=null;
                 console.log( this.searchForm);
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
                this.$confirm('是否确认删除【' + row.unitNam+'】', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function () {
                    return delObj(row)
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
            

            handleSubmit: function(searchForm, done) {
                 if(this.searchForm.unitNam==""||this.searchForm.unitNam == null ||String(this.searchForm.unitNam).trim()==""){
                    this.$message.success("请选择公司");
                    return;
                }
                 else {
                    this.searchForm.unitNam=this.searchForm.$unitNam;
                    return request({
                    url: "/caccentre/shipsub",
                    method: "post",
                    data: this.searchForm
                    }).then(data => {
                    this.$message.success('新增成功')
                    console.log(data);
                    //this.$refs.form.resetForm();清空form内容
                    this.getList(this.page,this.searchForm);//刷新本页内容
                    this.$emit("dictItemVisible");
                    });
                }
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