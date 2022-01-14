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
  <div class="tkindtuntuplangs">
    <basic-container>
      <vxe-form :data="formObj" size="mini">
        <!-- <vxe-form-item title="公司：" field="unitNam" size="mini" span="6">
          <vxe-input v-model="formObj.unitNam" clearable size="mini"></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="罐容量：" field="capacity" size="mini" span="6">
          <vxe-input
            v-model="formObj.capacity"
            clearable
            size="mini"
          ></vxe-input>
        </vxe-form-item> -->
        <vxe-form-item title="统计月份：" field="workDteString" size="mini" span="5">
          <vxe-input
            v-model="formObj.workDteString"
            clearable
            size="mini"
            type="month"
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="center">
          <vxe-button status="primary" icon="fa fa-search" @click="getDate()"
            >查询</vxe-button
          >
        </vxe-form-item>
      </vxe-form>
      <vxe-toolbar perfect size="mini">
        <template v-slot:buttons>
          <vxe-button type="text" class="fa fa-plus" @click="summary()"
            >数据汇总</vxe-button
          >
          <vxe-button
            type="text"
            class="fa fa-pencil-square-o"
            @click="upload()"
            >数据上报</vxe-button
          >
          <vxe-button type="text" class="fa fa-trash" @click="excelOut()"
            >导出EXCEL表</vxe-button
          >
        </template>
      </vxe-toolbar>
      <vxe-table
        border
        show-overflow
        keep-source
        highlight-current-row
        size="mini"
        :data="tableData"
        :loading="tableload"
        :round="true"
        align="center"
        height="650px"
        ref="tableMan"
        @cell-click="rowclick"
        @cell-dblclick="cellDBLClickEvent"
      >
        <vxe-table-column type="seq" width="60" title="序号"></vxe-table-column>
        <vxe-table-column
          field="portArea"
          title="地区"
          width="120"
        ></vxe-table-column>
        <vxe-table-column
          field="cargoKindNam"
          title="货类名称"
          width="200"
        ></vxe-table-column>
        <vxe-table-column
          field="totalWgt"
          title="总计"
          width="150"
        ></vxe-table-column>
        <vxe-table-column title="内贸">
          <vxe-table-column
            field="gnXjWgt"
            title="小计"
            width="100"
          ></vxe-table-column>
          <vxe-table-column
            field="gnJkWgt"
            title="进口"
            width="100"
          ></vxe-table-column>
          <vxe-table-column
            field="gnCkWgt"
            title="出口"
            width="100"
          ></vxe-table-column>
        </vxe-table-column>
        <vxe-table-column title="外贸">
          <vxe-table-column
            field="gwXjWgt"
            title="小计"
            width="100"
          ></vxe-table-column>
          <vxe-table-column
            field="gwJkWgt"
            title="进口"
            width="100"
          ></vxe-table-column>
          <vxe-table-column
            field="gwCkWgt"
            title="出口"
            width="100"
          ></vxe-table-column>
        </vxe-table-column>
      </vxe-table>
      <vxe-pager
        perfect
        size="mini"
        :loading="tableload"
        :current-page="tablePage.currentPage"
        :page-size="tablePage.pageSize"
        :total="tablePage.total"
        :layouts="[
          'PrevPage',
          'JumpNumber',
          'NextPage',
          'FullJump',
          'Sizes',
          'Total',
        ]"
        @page-change="handlePageChange"
      ></vxe-pager>
    </basic-container>
  </div>
</template>

<script>
import {
  fetchList,
  dataSummary,
  dataUpload,
  excelOutPost,
} from "@/api/caccentre/tkindtuntuplangs";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import moment from "moment";
// import Edit from "./edit";
// import Add from "./add";
export default {
  components: {},
  name: "tkindtuntuplangs",
  data() {
    return {
      formObj: {
        workDteString: moment(new Date()).format("YYYY-MM-DD"),
      },
      editTitle: "月度吞吐量计划（局汇总",
      dialogFormVisibleedit: false,
      dialogFormVisibleAdd: false,
      tableData: [],
      datanow: {},
      dataHeadNow: {},
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      tableload: false,
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  created() {
    this.getDate();
  },
  methods: {
    getDate() {
      this.tableload = true;
      this.dataHeadNow = {};
      fetchList(
        Object.assign(
          {
            current: this.tablePage.currentPage,
            size: this.tablePage.pageSize,
          },
          this.formObj
        )
      )
        .then((response) => {
          this.tableData = response.data.data.records;
          this.tablePage.total = response.data.data.total;
          this.tableload = false;
        })
        .catch(() => {
          this.tableload = false;
        });
    },
    cellDBLClickEvent({ row }) {
      this.datanow = row;
      this.dialogFormVisibleView = true;
    },
    summary() {
      let that = this;
      this.$confirm("是否确认进行【"+this.formObj.workDteString+"】数据汇总", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(function () {
            return dataSummary(that.formObj.workDteString);
          })
          .then((response) => {
            this.$message.success("数据汇总成功");
            this.getDate();
          });
    },
    upload() {
      let that = this;
      this.$confirm("是否确认进行【"+this.formObj.workDteString+"】数据上报", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(function () {
            return dataUpload(that.formObj.workDteString);
          })
          .then((response) => {
            this.$message.success("数据上报成功");
            this.getDate();
          });
    },
    excelOut() {
      if (
        this.formObj.workDteString != null
      ) {
        let loadingInstance = Loading.service({
          fullscreen: true,
          text: "EXCEL生成中……",
        });
        excelOutPost(this.formObj)
          .then(() => {
            loadingInstance.close();
          })
          .catch(() => {
            loadingInstance.close();
          });
      } else {
        this.$message.error("请选择统计月份");
      }
    },
    rowclick({ row }) {
      this.dataHeadNow = row;
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    editCont() {
      this.dialogFormVisibleplant = false;
    },
    closeEdit() {
      this.getDate();
      this.dialogFormVisibleedit = false;
      this.dialogFormVisibleAdd = false;
    },
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
      this.dialogFormVisibleAdd = false;
    },
  },
};
</script>
<style lang="scss" scoped>
.zyworkresourcesman {
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
</style>>