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
  <div class="business-jg-feeList">
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
      height="500px"
      ref="tableloadHeadRef"
    >
      <vxe-table-column field="CARGO_NAM" title="货名"></vxe-table-column>
      <vxe-table-column field="C_FEE_NAM" title="费用名称"></vxe-table-column>
      <vxe-table-column field="EX_FEE_NAM" title="公开费用"></vxe-table-column>
      <vxe-table-column field="IN_OUT_PORT_WAY" title="进港方式"></vxe-table-column>
      <vxe-table-column field="WEIGHT_WGT" title="重量"></vxe-table-column>
      <vxe-table-column field="FEE_RAT" title="费率"></vxe-table-column>
      <vxe-table-column field="FEE_MNY" title="金额"></vxe-table-column>
      <vxe-table-column field="GS_TAX_RAT" title="税率"></vxe-table-column>
      <vxe-table-column field="GS_TAX_FEE" title="税金"></vxe-table-column>
      <vxe-table-column field="GS_SE_FEE" title="收入"></vxe-table-column>
    </vxe-table>
    <vxe-pager
      perfect
      size="mini"
      :loading="tableload"
      :current-page="tablePage.currentPage"
      :page-size="tablePage.pageSize"
      :total="tablePage.total"
      :layouts="['PrevPage', 'JumpNumber', 'NextPage', 'FullJump', 'Sizes', 'Total']"
      @page-change="handlePageChange"
    ></vxe-pager>
  </div>
</template>

<script>
import { selectFeeBodyPageGet } from "@/api/business/jg";
import request from "@/router/axios";
import "@/styles/common/edit.scss";
import { mapGetters } from "vuex";
import moment from "moment";
import Vue from "vue";
import VXETable from "vxe-table";
import hljs from "highlight.js";
import { Loading, Message } from "element-ui";
export default {
  props: ["datanow"],
  components: {},
  data() {
    return {
      tableload: false,
      tableData: [],
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  created() {
    this.getDate();
  },
  mounted: function () {},
  methods: {
    getDate() {
      this.tableload = true;
      selectFeeBodyPageGet(
        Object.assign(
          {
            current: this.tablePage.currentPage,
            size: this.tablePage.pageSize,
            feeBillNo: this.datanow.contNo,
            unitCod: this.datanow.unitCod,
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
    handlePageChange() {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    closeWindows() {
      this.$emit("dictItemVisible");
    },
  },
};
</script>
<style lang="scss" >
.business-jg-feeList {
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