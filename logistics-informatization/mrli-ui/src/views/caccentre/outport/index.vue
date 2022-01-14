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
  <div class="zyworkcarinfo-index">
    <basic-container>
      <vxe-form :data="formObj">
        <vxe-form-item title="开始时间" field="beginDate" size="mini" span="4">
          <vxe-input
            v-model="formObj.beginDate"
            type="date"
            valueFormat="yyyyMMdd"
            clearable
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="结束时间" field="endDate" size="mini" span="4">
          <vxe-input
            v-model="formObj.endDate"
            type="date"
            valueFormat="yyyyMMdd"
            clearable
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="center">
          <vxe-button status="primary" icon="fa fa-search" @click="select()"
            >查询</vxe-button
          >
          <vxe-button status="primary" icon="fa fa-search" @click="print()"
            >打印</vxe-button
          >
        </vxe-form-item>
      </vxe-form>
      <vxe-toolbar perfect size="mini"> </vxe-toolbar>
      <vxe-table
        border
        show-overflow
        keep-source
        size="mini"
        :data="tableData"
        :loading="tableload"
        :round="true"
        align="center"
        height="700px"
        ref="table"
      >
        <vxe-table-column
          field="RECORD_DTE"
          title="出门证时间"
        ></vxe-table-column>
        <vxe-table-column field="UNIT_NAM" title="作业单位"></vxe-table-column>
        <vxe-table-column field="AGENT_NAM" title="客户"></vxe-table-column>
        <vxe-table-column field="CARGO_NAM" title="货名"></vxe-table-column>
        <vxe-table-column field="SHIP_NO" title="船号"></vxe-table-column>
        <vxe-table-column field="SHIP_NAM" title="船名"></vxe-table-column>
        <vxe-table-column field="IN_VOYAGE" title="进口航次"></vxe-table-column>
        <vxe-table-column field="BILL_NO" title="提单号"></vxe-table-column>
        <vxe-table-column field="CAR_NO" title="车号"></vxe-table-column>
        <vxe-table-column field="WEIGHT_WGT" title="重量"></vxe-table-column>
        <vxe-table-column field="ZCCB" title="称别"></vxe-table-column>
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
    </basic-container>
  </div>
</template>

<script>
import { fetchList } from "@/api/caccentre/outport";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import moment from "moment";
import Vue from "vue";
import VXETable from "vxe-table";
export default {
  components: {},
  name: "zyworkcarinfo-index",
  data() {
    return {
      formObj: {
        beginDate: moment(new Date()).format("YYYYMMDD"),
        endDate: moment(new Date()).format("YYYYMMDD")
      },
      dialogFormVisibleedit: false,
      tableData: [],
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      tableload: false,

      downTableOption: {
        //作业公司下拉菜单展示项
        unitColum: [
          { field: "SHOT_NAM", title: "公司代码" },
          { field: "UNIT_COD", title: "公司名称" }
        ],
        //作业公司下拉菜单的返回项
        unitreturnApi: [
          { field: "UNIT_COD", returnName: "unitCod" },
          { field: "SHOT_NAM", returnName: "unitName" }
        ],
        //作业公司下拉菜单的路径
        uniturl: "/caccentre/basecod/unit/page?sqlParam=1"
      }
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
    select() {
      this.tablePage.currentPage = 1;
      this.getDate();
    },
    print() {
      
      if (
        this.formObj.beginDate == null ||
        this.formObj.beginDate == "" ||
        this.formObj.beginDate == undefined
      ) {
        this.$message.error("请选择开始时间");
        return;
      }if (
        this.formObj.endDate == null ||
        this.formObj.endDate == "" ||
        this.formObj.endDate == undefined
      ) {
        this.$message.error("请选择结束时间");
        return;
      }
         
      window.open(
        "https://www.ykgportal.com/webroot/decision/view/report?viewlet=ddzhpt%252Foutport.cpt&record_dte="+this.formObj.beginDate+"&record_dte1="+this.formObj.endDate
      );
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    }
  }
};
</script>
<style lang="scss" scoped>
.zyworkcarinfo-index {
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