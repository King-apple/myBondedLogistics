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
  <div class="yard-cnt-emp-index">
    <basic-container>
      <vxe-form :data="formObj">
        <vxe-form-item title="箱号" field="extCntNo" size="mini" span="4">
          <vxe-input
            v-model="formObj.extCntNo"
            type="input"
            clearable
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="出场火车号" field="trainNo" size="mini" span="4">
          <vxe-input
            v-model="formObj.trainNo"
            type="input"
            clearable
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="出场车皮号" field="trainCod" size="mini" span="4">
          <vxe-input
            v-model="formObj.trainCod"
            type="input"
            clearable
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="状态" field="status" size="mini" span="4">
          <vxe-select v-model="formObj.status" clearable size="mini">
            <vxe-option value="01" label="待进场"></vxe-option>
            <vxe-option value="02" label="在场箱"></vxe-option>
            <vxe-option value="03" label="待出场确认"></vxe-option>
            <vxe-option value="04" label="已出场"></vxe-option>
            <vxe-option value="00" label="作废"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="center">
          <vxe-button
            status="primary"
            icon="fa fa-search"
            @click="selectTable()"
            >查询</vxe-button
          >
          <vxe-button type="reset" icon="fa fa-repeat">重置</vxe-button>
        </vxe-form-item>
      </vxe-form>
      <vxe-toolbar perfect size="mini">
        <template v-slot:buttons>
          <vxe-button type="text" icon="fa fa-superpowers" @click="openDt()"
            >调箱</vxe-button
          >
        </template>
      </vxe-toolbar>
      <vxe-table
        border
        show-overflow
        keep-source
        highlight-hover-row
        highlight-current-row
        size="mini"
        :data="tableData"
        :loading="tableload"
        :round="true"
        align="center"
        height="700px"
        ref="tableCnt"
        :checkbox-config="{ range: true }"
      >
        <vxe-table-column type="checkbox" width="60"></vxe-table-column>
        <vxe-table-column
          field="extCntNo"
          title="箱号"
          width="95"
        ></vxe-table-column>
        <vxe-table-column
          field="realCntNo"
          title="正确箱号"
          width="95"
        ></vxe-table-column>
        <vxe-table-column
          field="status"
          title="状态"
          :formatter="formatterStatus"
          width="60"
        ></vxe-table-column>
        <vxe-table-column
          field="cntMasterName"
          title="箱主"
          width="80"
        ></vxe-table-column>
        <vxe-table-column field="placeName" title="堆存地点"></vxe-table-column>
        <vxe-table-colgroup field="group1" title="箱计费时间">
          <vxe-table-column
            field="cntDate"
            title="开始时间"
            width="135"
          ></vxe-table-column>
          <vxe-table-column
            field="cntDateEnd"
            title="结束时间"
            width="135"
          ></vxe-table-column>
        </vxe-table-colgroup>
        <vxe-table-colgroup field="group2" title="入场信息">
          <vxe-table-column
            field="createTime"
            title="创建时间"
            width="135"
          ></vxe-table-column>
          <vxe-table-column
            field="inYardCommitTime"
            title="进场确认时间"
            width="135"
          ></vxe-table-column>
          <vxe-table-column
            field="inYardCommitOpt"
            title="进场确认人员"
          ></vxe-table-column>
        </vxe-table-colgroup>
        <vxe-table-colgroup field="group3" title="出场信息">
          <vxe-table-column field="carNo" title="出场车辆"></vxe-table-column>
          <vxe-table-column field="trainNo" title="火车号"></vxe-table-column>
          <vxe-table-column field="trainCod" title="车皮号"></vxe-table-column>
          <vxe-table-column
            field="outYardTime"
            title="出场时间"
            width="135"
          ></vxe-table-column>
          <vxe-table-column
            field="outYardOpt"
            title="出场操作人员"
          ></vxe-table-column>
          <vxe-table-column
            field="outYardCommitTime"
            title="出场确认时间"
            width="135"
          ></vxe-table-column>
          <vxe-table-column
            field="outYardCommitOpt"
            title="出场确认人员"
          ></vxe-table-column>
        </vxe-table-colgroup>
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
          'Total'
        ]"
        @page-change="handlePageChange"
      ></vxe-pager>
      <vxe-modal
        v-if="dialogFormVisibleView"
        v-model="dialogFormVisibleView"
        title="选择调动的状态"
        width="400"
        height="300"
        resize
        storage
      >
        <vxe-form :data="formtp" size="mini">
          <vxe-form-item title="状态" field="status" size="mini" span="24">
            <vxe-select v-model="formtp.status" clearable size="mini">
              <vxe-option value="01" label="待进场"></vxe-option>
              <vxe-option value="02" label="在场箱"></vxe-option>
              <vxe-option value="03" label="待出场确认"></vxe-option>
              <vxe-option value="00" label="作废"></vxe-option>
            </vxe-select>
          </vxe-form-item>
        </vxe-form>
        <div align="center">
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-check"
            @click="splitSubmit"
            >确定</el-button
          >
          <el-button
            size="mini"
            icon="el-icon-close"
            plain
            @click="dictItemVisible"
            >取消</el-button
          >
        </div>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import { fetchList, changCnt } from "@/api/yard/empcnt";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import EditDown from "../../../baseUtile/EditDown";
export default {
  components: { EditDown },
  name: "yard-cnt-emp-index",
  data() {
    return {
      formObj: { status: null },
      dialogFormVisibleedit: false,
      tableData: [],
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      tableload: false,
      validRules: {
        workDate: [{ required: true, message: "必须选择时间" }],
        unitName: [{ required: true, message: "必须选择公司" }],
        cargoName: [{ required: true, message: "必须选择货物" }],
        unloadId: [{ required: true, message: "必须选择类型" }]
      },
      formtp: { status: null },
      dialogFormVisibleView: false
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() {
    this.getDate();
  },
  methods: {
    getDate() {
      this.tableload = true;
      fetchList(
        Object.assign(
          {
            current: this.tablePage.currentPage,
            size: this.tablePage.pageSize
          },
          this.formObj
        )
      )
        .then(response => {
          this.tableData = response.data.data.records;
          this.tablePage.total = response.data.data.total;
          this.tableload = false;
        })
        .catch(() => {
          this.tableload = false;
        });
    },

    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    selectTable() {
      this.tablePage.currentPage = 1;
      this.getDate();
    },
    resetForm() {},
    openDt() {
      var dataList = this.$refs.tableCnt.getCheckboxRecords();
      if (dataList == null || dataList.length == 0) {
        this.$message.error("请勾选要调的箱");
        return;
      }
      this.dialogFormVisibleView = true;
    },
    formatterStatus({ cellValue }) {
      if (cellValue == "01") {
        return "待进场";
      } else if (cellValue == "02") {
        return "在场箱";
      } else if (cellValue == "03") {
        return "待出场确认";
      } else if (cellValue == "04") {
        return "已出场";
      } else if (cellValue == "00") {
        return "作废";
      }
    },
    splitSubmit() {
      console.log(this.$refs.tableCnt.getCheckboxRecords())
      changCnt(this.$refs.tableCnt.getCheckboxRecords(), this.formtp.status)
        .then(response => {
          this.$message.success(response.data.data);
          this.getDate();
          this.dictItemVisible();
        })
        .catch(() => {});
    },
    dictItemVisible() {
      this.dialogFormVisibleView = false;
    }
  }
};
</script>
<style lang="scss" scoped>
.yard-cnt-emp-index {
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