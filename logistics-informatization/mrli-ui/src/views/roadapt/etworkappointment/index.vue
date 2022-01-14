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
      >
        <template slot="status" slot-scope="scope">
          <span v-if="scope.row.status=='01'" style="color:red;">待排号</span>
          <span v-if="scope.row.status=='02'" style="color:blue;">已排号</span>
        </template>
        <!-- <template slot-scope="scope" slot="menuLeft" >
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="mini"
            plain
            @click.stop="handleAdd()"
            :v-if="permission.roadapt_etworkappointment_add"
          >新增</el-button>
        </template>-->
        <template slot-scope="{row,type,size}" slot="menu">
          <el-button  v-if="row.status=='01'" icon="el-icon-check" :size="size" :type="type" @click.stop="handleApt(row)">预约</el-button>
        </template>
      </avue-crud>
      <el-dialog v-if="dialogFormVisible"
        :visible.sync="dialogFormVisible"
        title="车辆预约"
        width="90%"
        @close="dictItemVisible"
        
      >
        <Form :objrow="this.objrow" @dictItemVisible="dictItemVisible" @getList="refalsh"></Form>
      </el-dialog>
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
} from "@/api/roadapt/etworkappointment";
import { tableOption } from "@/const/crud/roadapt/etworkappointment";
import { mapGetters } from "vuex";
import Form from "./form";
export default {
  components: {
    Form
  },
  name: "etworkappointment",
  data() {
    return {
      searchForm: {},
      tableData: [],
      objrow:{},
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      tableLoading: false,
      tableOption: tableOption,
      dialogFormVisible: false,
      permission: {}
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
    permissionList() {
      this.permission = this.permissions;
      return {
        addBtn: this.vaildData(
          this.permissions.roadapt_etworkappointment_add,
          false
        ),
        delBtn: this.vaildData(
          this.permissions.roadapt_etworkappointment_del,
          false
        ),
        editBtn: this.vaildData(
          this.permissions.roadapt_etworkappointment_edit,
          false
        )
      };
    }
  },
  methods: {
    refalsh(){
       this.getList(this.page, this.params);
    },
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
      this.$confirm("是否确认删除车号为" + row.trkTrkno, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return delObj(row.pkid);
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
    // handleApt() {
    //   this.dialogFormVisible = true;
    // },
    handleApt:function(row) {
      this.objrow=row;
      this.dialogFormVisible = true;
    },
    dictItemVisible: function() {
      this.dialogFormVisible = false;
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
    }
  }
};
</script>