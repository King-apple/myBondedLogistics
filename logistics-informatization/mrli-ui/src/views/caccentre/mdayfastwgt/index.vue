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
              <el-form-item label="日期：">
                <el-date-picker
                  v-model="searchForm.workDateSearch"
                  type="date"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  placeholder="选择日期"
                ></el-date-picker>
              </el-form-item>
            </el-form>
          </div>
        </el-col>
        <el-col :span="18">
          <div class="grid-content bg-purple">
            <el-button
              type="primary"
              autofocus="false"
              icon="el-icon-search"
              size="mini"
              @click="getList"
            >查询</el-button>
           
          </div>
        </el-col>
      </el-row>
      <el-tabs v-model="activeName" type="card">
        <el-tab-pane label="8点制数据" name="first">
          <index8
            :tableLoadingnow="this.tableLoading"
            :tablePreDate="this.tableData"
            :dataNow="this.searchForm.workDateSearch"
          />
        </el-tab-pane>
         <el-tab-pane label="18点制数据" name="second">
          <index16
            :tableLoadingnow="this.tableLoading2"
            :tablePreDate="this.tableData2"
            :dataNow="this.searchForm.workDateSearch"
          />
        </el-tab-pane>
      </el-tabs>
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
  fetchListlg,
  doOfDay
} from "@/api/caccentre/mdayfastwgt";
import { tableOption } from "@/const/crud/caccentre/mdayfastwgt";
import { mapGetters } from "vuex";
import index8 from "./index8";
import index16 from "./index16";
import moment from "moment";
export default {
  components: { index8, index16 },
  name: "mdayfastwgt",
  data() {
    return {
      searchForm: { workDateSearch: moment(new Date()).format("YYYY-MM-DD") },
      tableData: [],
      tableData2: [],
      activeName: "first",
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 30 // 每页显示多少条
      },
      tableLoading: false,
      tableLoading2: false,
      tableOption: tableOption
    };
  },
  mounted: function() {
    this.getList(this.page, this.searchForm);
  },
  computed: {
    ...mapGetters(["permissions"]),
    permissionList() {
      return {
        addBtn: this.vaildData(
          this.permissions.caccentre_mdayfastwgt_add,
          false
        ),
        delBtn: this.vaildData(
          this.permissions.caccentre_mdayfastwgt_del,
          false
        ),
        editBtn: this.vaildData(
          this.permissions.caccentre_mdayfastwgt_edit,
          false
        )
      };
    }
  },
  methods: {
    doData() {
      doOfDay(this.searchForm)
        .then(response => {
          this.$message.success("处理成功");
          getList(this.page,this.searchForm);
        })
        .catch(() => {
          this.tableLoading = false;
        });
    },
    getList(page, params) {
      this.tableLoading = true;
      this.tableLoading2 = true;
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
          this.tableData = response.data.data;
          this.tableLoading = false;
        })
        .catch(() => {
          this.tableLoading = false;
        });
      fetchListlg(
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
          this.tableData2 = response.data.data;
          this.tableLoading2 = false;
        })
        .catch(() => {
          this.tableLoading2 = false;
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
    }
  }
};
</script>