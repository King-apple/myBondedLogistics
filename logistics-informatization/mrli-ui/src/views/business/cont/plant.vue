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
  <div class="yardmiyardheadedit">
    <vxe-form :data="dataForm" size="mini">
      <vxe-form-item title="计划通知单" field="planNo" size="mini" span="24">
        <vxe-input v-model="dataForm.planNo" readonly @click="operLov"></vxe-input>
      </vxe-form-item>
    </vxe-form>
    <div align="center">
      <el-button type="primary" size="mini" icon="el-icon-check" @click="handleSubmit">确定</el-button>
      <el-button size="mini" icon="el-icon-close" plain @click="closeWindows">取消</el-button>
    </div>
    <vxe-modal
      v-if="dialogFormVisible"
      v-model="dialogFormVisible"
      title="选择计划通知单"
      width="900"
      height="710"
      resize
      storage
    >
      <vxe-form :data="dataForm" size="mini">
        <vxe-form-item title="查询条件：" field="planNoselect" size="mini" span="24">
          <vxe-input v-model="dataForm.planNoselect" @input="getData"></vxe-input>
        </vxe-form-item>
      </vxe-form>
      <vxe-grid
        highlight-hover-row
        auto-resize
        height="600"
        border
        :loading="loading"
        :pager-config="tablePage"
        :data="tableData"
        :columns="downTableOption.plantNoColum"
        @cell-click="selectEvent"
        @page-change="pageChangeEvent"
      ></vxe-grid>
    </vxe-modal>
    <vxe-modal
      v-if="dialogFormVisibleedit"
      v-model="dialogFormVisibleedit"
      title="合同新增"
      width="1200"
      height="900"
      resize
      storage
      fullscreen
    >
      <Edit :datanow="dateNow" @dictItemVisible="dictItemVisible" @selectListplan="getDatenow" ></Edit>
    </vxe-modal>
  </div>
</template>

<script>
import { fetchList } from "@/api/basecod/basecod";
import request from "@/router/axios";
import "@/styles/common/edit.scss";
import { mapGetters } from "vuex";
import moment from "moment";
import { mapState } from "vuex";
import Vue from "vue";
import VXETable from "vxe-table";
import hljs from "highlight.js";
import { Loading, Message } from "element-ui";
import EditDown from "../../baseUtile/EditDown";
import Edit from "./edit";
export default {
  props: ["datanow"],
  components: { EditDown, Edit },
  data() {
    return {
      activeName: "first",
      tableData: [],
      loading: false,
      dateNow:{},
      tablePage: {
        total: 0,
        currentPage: 1,
        pageSize: 10
      },
      dialogFormVisible: false,
      dialogFormVisibleedit: false,
      dataForm: {
        plantNo: null
      },
      inoutList: [
        { label: "进口", value: 1 },
        { label: "出口", value: 0 }
      ],
      tradeList: [
        { label: "内贸", value: 1 },
        { label: "外贸", value: 0 }
      ],
      portMnyList: [
        { label: "是", value: 1 },
        { label: "否", value: 0 }
      ],
      downTableOption: {
        //合同
        plantNoColum: [
          { field: "informNo", title: "计划通知单号", width: "110px" },
          { field: "shipNo", title: "船号", width: "100px" },
          { field: "shipNam", title: "船名", width: "80px" },
          { field: "consignNam", title: "委托人", width: "200px" },
          {
            field: "tradeId",
            title: "内外贸",
            width: "80px",
            formatter: this.formatterTradeId
          },
          {
            field: "inoutId",
            title: "进出口",
            width: "80px",
            formatter: this.formatterInoutId
          },
          {
            field: "toPortTim",
            title: "到港时间",
            width: "140px",
            formatter: ["formatDate", "yyyy-MM-dd"]
          }
        ],
        plantNourl: "/business/cargoreadyinformhead/pageOfSql"
      }
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
    ...mapState({
      userInfo: state => state.user.userInfo
    })
  },
  created() {},
  mounted: function() {},
  methods: {
    handleSubmit() {
      if (this.dataForm.planNo == null) {
        this.$message.error("没有选择计划通知单无法生成合同");
        return;
      }
       this.dateNow = {planNo:this.dataForm.planNo };
      this.dialogFormVisibleedit=true
    },
    closeWindows() {
      this.$emit("dictItemVisible");
    },
    dictItemVisible() {
      this.dialogFormVisibleedit=false;
      this.$emit("dictItemVisible");
    },
    getDatenow(){
      this.$emit("selectList");
    },
    formatterTradeId({ cellValue }) {
      if (cellValue == 1) {
        return "内贸";
      } else if (cellValue == 0) {
        return "外贸";
      }
    },
    rowclick({ row }) {},
    formatterInoutId({ cellValue }) {
      if (cellValue == 1) {
        return "进口";
      } else if (cellValue == 0) {
        return "出口";
      }
    },
    operLov() {
      this.dataForm.planNoselect = null;
      this.getData();
      this.dialogFormVisible = true;
    },
    getData() {
      this.loading = true;
      fetchList(
        Object.assign({
          current: this.tablePage.currentPage,
          size: this.tablePage.pageSize,
          key: this.dataForm.planNoselect
        }),
        this.downTableOption.plantNourl
      )
        .then(response => {
          this.tableData = response.data.data.records;
          this.tablePage.total = response.data.data.total;
          this.loading = false;
        })
        .catch(() => {
          this.loading = false;
        });
    },
    pageChangeEvent({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.loading = true;
      this.getData().then(data => {
        this.loading = false;
        this.tableData = data;
      });
    },
    selectEvent(params) {
      this.dataForm.planNo = params.row.informNo;
      this.dialogFormVisible = false;
    }
  }
};
</script>
<style lang="scss" scoped>
.yardmiyardheadedit {
  .el-divider--horizontal {
    margin: 8px 0;
  }
}
</style>