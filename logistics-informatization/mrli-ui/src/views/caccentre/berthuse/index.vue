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
  <div class="caccentre-berthuse-index">
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
        <vxe-form-item title="结束时间" field="end" size="mini" span="4">
          <vxe-input
            v-model="formObj.end"
            type="date"
            valueFormat="yyyyMMdd"
            clearable
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="center">
          <vxe-button status="primary" icon="fa fa-search" @click="select()"
            >查询</vxe-button
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
        <vxe-table-column field="berthCod" title="泊位"></vxe-table-column>
        <vxe-table-column field="berthLong" title="泊位长度"></vxe-table-column>
        <vxe-table-column
          field="allTimeNum"
          title="日历小时数"
        ></vxe-table-column>
        <vxe-table-colgroup field="group1" title="泊位占用时间">
          <vxe-table-column field="timeNum" title="合计"></vxe-table-column>
          <vxe-table-column
            field="useTimeNum"
            title="作业时间"
          ></vxe-table-column>
        </vxe-table-colgroup>
        <vxe-table-column field="czWeight" title="操作量"></vxe-table-column>
        <vxe-table-column
          field="shipNum"
          title="作业船舶数量"
        ></vxe-table-column>
        <vxe-table-column
          field="ocpRate"
          title="占用率"
          formatter="formatCutNumber"
        ></vxe-table-column>
        <vxe-table-column field="useRate" title="作业率"   formatter="formatCutNumber"></vxe-table-column>
        <vxe-table-colgroup field="group1" title="万吨占用时间">
          <vxe-table-column
            field="timeNumOfBig"
            title="合计"
          ></vxe-table-column>
          <vxe-table-column
            field="useTimeNumOfBig"
            title="作业时间"
            formatter="formatCutNumber"
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
    </basic-container>
  </div>
</template>

<script>
import { fetchList } from "@/api/caccentre/berthuse";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
export default {
  components: {},
  name: "caccentre-berthuse-index",
  data() {
    return {
      formObj: {},
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
    // this.getDate();
  },
  methods: {
    getDate() {
      this.tableload = true;
      fetchList(Object.assign(this.formObj))
        .then(response => {
          this.tableData = response.data.data;

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

    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    // 向下舍入,默认两位数
    formatCutNumber({ cellValue }, digits = 2) {
      return XEUtils.toFixed(XEUtils.floor(cellValue, digits), digits);
    }
  }
};
</script>
<style lang="scss" scoped>
.caccentre-berthuse-index {
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