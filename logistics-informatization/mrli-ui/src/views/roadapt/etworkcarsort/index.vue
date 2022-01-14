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
        @filter-change="filterChange"
        @selection-change="selectionChange"
      >
        <template  slot="menuLeft" >
          <el-button
            type="primary"
            icon="el-icon-check"
            size="mini"
            plain
            @click="checkCar()"             
          >确认派车</el-button>
      <el-button
            type="primary"
            icon="el-icon-close"
            size="mini"
            plain
            @click="passCar()"             
          >过号</el-button>
      <el-button
            type="primary"
            icon="el-icon-edit"
            size="mini"
            plain
            @click.stop="handleApt()"            
          >修改场地</el-button>
        </template>
         
      </avue-crud>

      <div v-if="dialogFormVisible">
       <el-dialog
        :visible.sync="dialogFormVisible"
        title="修改场地"
        width="90%"
        :show-close="false"
        @close="dictItemVisible"
        
      >
        <Form   :objrow="this.objrow" @dictItemVisible="dictItemVisible"></Form>
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
  checkObj
} from "@/api/roadapt/etworkcarsort";
import { tableOption } from "@/const/crud/roadapt/etworkcarsort";
import { mapGetters } from "vuex";
import Form from "./form";
export default {
  components: {
    Form
  },
  name: "etworkcarsort",
  data() {
    return {
      searchForm: {"zt":"02"},
      tableData: [],
      multipleSelection: [],
       objrow:{},
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 10 // 每页显示多少条
      },
      tableLoading: false,
      tableOption: tableOption,
      permission: {},
      dialogFormVisible: false
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
    permissionList() {
      this.permission=this.permissions
      console.log(this.permission)
      return {
        addBtn: this.vaildData(
          this.permissions.roadapt_etworkcarsort_add,
          false
        ),
        delBtn: this.vaildData(
          this.permissions.roadapt_etworkcarsort_del,
          false
        ),
        editBtn: this.vaildData(
          this.permissions.roadapt_etworkcarsort_edit,
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
      this.$confirm("是否确认删除ID为" + row.etSeq, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return delObj(row.etSeq);
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
    checkCar: function() {      
      let that=this;
      if(that.multipleSelection.length==0){
         this.$message.success("请至少选择一辆车");
         return;
      }
      this.$confirm("是否确认派车", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {

          return checkObj(that.multipleSelection,"03");
           
        })
        .then(data => {
          this.$refs.crud.toggleSelection();
          this.$message.success("派车成功");
          this.getList(this.page);
        });
    },
    passCar: function() {
      let that=this;
      if(that.multipleSelection.length==0){
         this.$message.success("请至少选择一辆车");
         return;
      }
      this.$confirm("是否确认该车过号", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {

          return checkObj(that.multipleSelection,"09");
           
        })
        .then(data => {
          this.$refs.crud.toggleSelection();
          this.$message.success("操作成功");
          this.getList(this.page);
        });
    },
    handleAdd(){
        this.$refs.crud.rowAdd();
    },
    handleApt:function() {
      if(this.multipleSelection.length!=1){
         this.$message.success("请选择一辆车");
         return;
      }
      this.objrow=this.multipleSelection[0];
      this.dialogFormVisible = true;
    },
    dictItemVisible: function() {       
       this.$refs.crud.toggleSelection(); 
       this.getList(this.page); 
      this.dialogFormVisible = false;
    },
    sizeChange(pageSize) {
      this.page.pageSize = pageSize;
    },
    currentChange(current) {
      this.page.currentPage = current;
    },
    searchChange(form, done) {
      this.searchForm = form;
      form.zt="02";
      this.getList(this.page, form);
      done();
    },
    refreshChange() {
      this.getList(this.page);
    },
    selectionChange(list){
       this.multipleSelection=list;
      },
    filterChange(result) {
      this.$message.success("过滤器" + JSON.stringify(result));
    }
  }
};
</script>
<style>

</style>