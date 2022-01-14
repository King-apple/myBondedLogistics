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
       <template slot="zt" slot-scope="scope">
          <span v-if="scope.row.zt=='02'" >登记</span>
          <span v-if="scope.row.zt=='03'" >入场</span>
          <span v-if="scope.row.zt=='04'" >作业</span>
          <span v-if="scope.row.zt=='05'" >完工</span>
          <span v-if="scope.row.zt=='00'" >作废</span>
        </template>  
         <template slot="subunitNam" slot-scope="scope">
          <span v-if="scope.row.subunitNam=='第一保管站'"> 第一保管站</span>
          <span v-if="scope.row.subunitNam=='第二保管站'" >第二保管站</span> 
        </template>  

        <template  slot="menuLeft" >
          <el-button
            type="primary"
            icon="el-icon-check"
            size="mini"
            plain
            @click="zfCar()"             
          >作废</el-button>
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
       <avue-form ref="form"  :option="option" v-model="objrow" @submit="handleSubmit">
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
  checkObj
} from "@/api/roadapt/etworkcarsort";
import { tableOption } from "@/const/crud/roadapt/etworkcarquery";
import { mapGetters } from "vuex"; 
export default {  
  name: "etworkcarquery",
  data() {
    return {
      searchForm: {},
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
      dialogFormVisible: false,
      option: {
        labelWidth: 100,
        emptyBtn: false,
        column: [
          {
            type: "textarea",
            label: "作废原因",
            prop: "zfRemark",
            span:24
          } 
        ]
      }
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
     
     
    sizeChange(pageSize) {
      this.page.pageSize = pageSize;
    },
    currentChange(current) {
      this.page.currentPage = current;
    },
    searchChange(form, done) {       
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
    },
    zfCar:function() {
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
     closeWindows: function() {   
     this.$refs.form.resetForm();
      this.dictItemVisible();
    },
    handleSubmit: function(obj) {    
      this.multipleSelection[0]=obj;
       checkObj( this.multipleSelection,"00")
        .then(data => {
          this.$message.success("作废成功");
          done();
          this.getList(this.page);
        })
        .catch(() => {
          loading();
        });  
        
        
    },
  }
};
</script>
<style>

</style>