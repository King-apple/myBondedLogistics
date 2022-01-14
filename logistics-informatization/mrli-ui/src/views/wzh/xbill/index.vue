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
        <div class="wzh-wzhbill-index">
            <basic-container>
        <vxe-form :data="formObj">
            <vxe-form-item span="5" size="mini" align="center">
                <vxe-button status="primary" icon="fa fa-search" @click="selectTable()"
                >查询
                </vxe-button>
                <vxe-button type="reset" icon="fa fa-repeat" @click="resetForm">
                    重置
                </vxe-button>
            </vxe-form-item>
        </vxe-form>
        <vxe-toolbar custom perfect size="mini">
            <template v-slot:buttons>
                <vxe-button type="text" class="fa fa-plus" @click="editNow()">
                    新增
                </vxe-button>
            </template>
        </vxe-toolbar>
        <vxe-table border show-overflow keep-source stripe highlight-current-row highlight-hover-row
         size="mini" :data="tableData" :loading="tableload" :round="true" align="center"
         height="700px" ref="tablewzhbill" :sort-config="{remote:true}" @sort-change="sortChangeEvent">
                       <vxe-table-column field="pkid" title="主键" sortable/>
                       <vxe-table-column field="consignCod" title="货主代码" sortable/>
                       <vxe-table-column field="consignNam" title="货主名称" sortable/>
                       <vxe-table-column field="shipNo" title="船号" sortable/>
                       <vxe-table-column field="voyage" title="航次" sortable/>
                       <vxe-table-column field="unitCod" title="公司代码" sortable/>
                       <vxe-table-column field="billNo" title="提单号" sortable/>
                       <vxe-table-column field="piecesNum" title="件数" sortable/>
                       <vxe-table-column field="weight" title="重量" sortable/>
                       <vxe-table-column field="marks" title="唛头" sortable/>
                       <vxe-table-column field="cargoName" title="货名" sortable/>
                       <vxe-table-column field="cargoNameCod" title="货名代码" sortable/>
                       <vxe-table-column field="bak" title="备注" sortable/>
                       <vxe-table-column field="createTime" title="创建时间" sortable/>
                       <vxe-table-column field="createOpt" title="创建人" sortable/>
                       <vxe-table-column field="updateTime" title="更新时间" sortable/>
                       <vxe-table-column field="updateOpt" title="更新人" sortable/>
                       <vxe-table-column field="status" title="状态" sortable/>
                       <vxe-table-column field="extId" title="关联外系统主键" sortable/>
                       <vxe-table-column field="extSystem" title="关联外系统代码" sortable/>
                    </vxe-table>
        <vxe-pager perfect size="mini" :loading="tableload" :current-page="tablePage.currentPage"
                   :page-size="tablePage.pageSize" :total="tablePage.total"
                   :layouts="['PrevPage','JumpNumber','NextPage','FullJump','Sizes','Total']"
                   @page-change="handlePageChange"
        ></vxe-pager>
        <vxe-modal
                v-if="dialogFormVisibleedit"
                v-model="dialogFormVisibleedit"
                title="维护"
                width="700"
                height="800"
                resize
                storage>
            <Edit :datanow="dataNow"
                  @dictItemVisible="dictItemVisible"
                  @selectList="selectTable"/>
        </vxe-modal>
    </basic-container>
</div>
</template>

<script>
                import {fetchList} from '@/api/wzh/wzhbill'
                    import {mapGetters} from 'vuex'
    import EditDown from "@/views/baseUtile/EditDown";
    import Edit from "./edit.vue";

    export default {
        components: {EditDown, Edit},
        data() {
            return {
                formObj: {},
                tableData: [],
                tablePage: {
                    total: 0, // 总页数
                    currentPage: 1, // 当前页数
                    pageSize: 20 // 每页显示多少条
                },
                tableload: false,
                dialogFormVisibleedit: false,
                dataNow: {},
            }
        },
        computed: {
            ...mapGetters(['permissions'])
        },
        created() {
            this.selectTable();
        },
        methods: {
            getDate() {
                this.tableload = true;
                fetchList(Object.assign({
                            current: this.tablePage.currentPage,
                            size: this.tablePage.pageSize
                        }, this.formObj)
                ).then(response => {
                    this.tableData = response.data.data.records;
                    this.tablePage.total = response.data.data.total;
                    this.tableload = false;
                }).
                catch(() => {
                    this.tableload = false;
                });
            },
            handlePageChange({currentPage, pageSize}) {
                this.tablePage.currentPage = currentPage;
                this.tablePage.pageSize = pageSize;
                this.getDate();
            },
            selectTable() {
                this.tablePage.currentPage = 1;
                this.getDate();
            },
            resetForm() {
            },
            sortChangeEvent({column, property, order}) {
                this.formObj.sqlSortNameOfMrLI = property;
                this.formObj.sqlSortOfMrLI = order;
                this.selectTable();
            },
            dictItemVisible() {
                this.dialogFormVisibleedit = false;
                this.dataNow = null;
            },
            editNow() {
                this.dialogFormVisibleSm = true;
            },
        }
    }
</script>
<style lang="scss" scoped>
                .wzh-wzhbill-index {
                    .el-tag {
        width: 100%;
    }

    .el-divider--horizontal {
        margin: 10px 0;
    }

    .vxe-toolbar {
        height: 25px;
    }

    }
</style>