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
  <div class="transport-transferBc-index">
    <basic-container>
      <!--查询区-->
      <vxe-form :data="formObj" size="mini">
        <vxe-form-item title="货票号" field="bh" size="mini" span="4">
          <vxe-input v-model="formObj.bh"></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="称别组" field="cbs" size="mini" span="2">
          <EditDown
            :objs="formObj"
            :property="downTableOption.cbsNam"
            :tableColumns="downTableOption.cbsColum"
            :urlapi="downTableOption.cbsurl"
            :returnApi="downTableOption.cbsreturnApi"
            sizenow="mini"
            pageSize="30"
            height="450"
            width="150"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item title="时间段：" field="zbrqbegin" size="mini" span="3">
          <vxe-input
            v-model="formObj.zbrqbegin"
            placeholder="开始时间"
            type="date"
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="至" field="zbrqend" size="mini" span="3">
          <vxe-input
            v-model="formObj.zbrqend"
            placeholder="结束时间"
            type="date"
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="船编号" field="cbh" size="mini" span="4">
          <EditDown
            :objs="formObj"
            :property="downTableOption.cbhNam"
            :tableColumns="downTableOption.cbhColum"
            :urlapi="downTableOption.cbhurl"
            :returnApi="downTableOption.cbhreturnApi"
            sizenow="mini"
            pageSize="100"
            height="750"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="left">
          <vxe-button
            status="primary"
            icon="fa fa-search"
            @click="selectTable()"
            >查询</vxe-button
          >
        </vxe-form-item>
      </vxe-form>
      <!--功能按钮-->
      <vxe-toolbar perfect size="mini">
        <template v-slot:buttons>
          <vxe-button
            type="text"
            class="fa fa-cogs"
            @click="viewZc()"
          >
            转 称</vxe-button
          >
          <vxe-button
            type="text"
            class="fa fa-cogs"
            @click="viewZh()"
          >
            置 回</vxe-button
          >
        </template>
      </vxe-toolbar>
      <!--表格-->
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
        height="700px"
        ref="tableTransfer"
        :sort-config="{remote:true}"
         @sort-change="sortChangeEvent"
      >
        <vxe-table-column type="checkbox" width="60"></vxe-table-column>
        <vxe-table-column type="seq" width="50" title="序号"></vxe-table-column>
        <vxe-table-column
          field="lxdm"
          title="代码"
          width="50"
          sortable
        ></vxe-table-column>
        <vxe-table-column
          field="bh"
          title="货票号"
          width="80" sortable
        ></vxe-table-column>
        <vxe-table-column
          field="hm"
          title="货名"
          width="100" sortable
        ></vxe-table-column>
        <vxe-table-column
          field="tdh"
          title="提单号"
          width="100" sortable
        ></vxe-table-column>
        <vxe-table-column
          field="cbh"
          title="船编号"
          width="100" sortable
        ></vxe-table-column>
        <vxe-table-column
          field="cm"
          title="船名"
          width="100" sortable
        ></vxe-table-column>
        <vxe-table-column
          field="jhl"
          title="计划量"
          width="120" sortable
        ></vxe-table-column>
        <vxe-table-column
          field="wcl"
          title="完成量"
          width="120" sortable
        ></vxe-table-column>
        <vxe-table-column
          field="syl"
          title="剩余量"
          width="120" sortable
        ></vxe-table-column>
        <vxe-table-column
          field="csl"
          title="捣运车"
          width="100" sortable
        ></vxe-table-column>
        <vxe-table-column
          field="cbs"
          title="称别组"
          width="70" sortable
        ></vxe-table-column>
        <vxe-table-column
          field="sqdw"
          title="申请单位"
          width="200" sortable
        ></vxe-table-column>
        <vxe-table-column
          field="bpwc"
          title="完成否"
          :formatter="formatterBpwc"
          width="60" sortable
        ></vxe-table-column>
        <vxe-table-column
          field="nwm"
          title="内外贸"
          width="60" sortable
        ></vxe-table-column>
        <vxe-table-column
          field="zbrq"
          title="值班日期"
          width="140" sortable
        ></vxe-table-column>
      </vxe-table>
      <!--分页区-->
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
        v-if="dialogFormVisibleViewZc"
        v-model="dialogFormVisibleViewZc"
        title="转称"
        width="1200"
        height="800"
        resize
        storage
      >
        <Zcindex
          @dictItemVisible="dictItemVisible"
          @selectParent="getDate"
          :dataList="selectDataList"
        ></Zcindex>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleViewZh"
        v-model="dialogFormVisibleViewZh"
        title="置回"
        width="800"
        height="400"
        resize
        storage
      >
        <Zhindex
          @dictItemVisible="dictItemVisible"
          @selectParent="getDate"
        ></Zhindex>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import { fetchList } from "@/api/transport/transferbc";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import EditDown from "../../baseUtile/EditDown";
import Zcindex from "./zcindex";
import Zhindex from "./zhindex";
export default {
  components: { EditDown, Zcindex, Zhindex },
  name: "transport-base-index",
  data() {
    return {
      formObj: {
        cbs: null,
        cbh: null,
        shipNam: null,
        firstCod: null,
        hpxx: "A"
      },
      tableData: [],
      selectDataList: [],
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      tableload: false,
      dialogFormVisibleViewZc: false,
      dialogFormVisibleViewZh: false,
      downTableOption: {
        //称别组
        cbsNam: "cbs",
        cbsColum: [{ field: "CBS", title: "称别组" }],
        cbsreturnApi: [{ field: "CBS", returnName: "cbs" }],

        cbsurl: "/transport/hpmx/getCbsDoc",
        //船编号
        cbhNam: "cbh",
        cbhColum: [
          { field: "CBH", title: "船编号" },
          { field: "CM", title: "船名" }
        ],
        cbhreturnApi: [
          { field: "CBH", returnName: "cbh" },
          { field: "CM", returnName: "cm" }
        ],

        cbhurl: "/transport/hpmx/getCbhDoc"
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
      this.dataHeadNow = {};
      fetchList(
        Object.assign(
          {
            current: this.tablePage.currentPage,
            size: this.tablePage.pageSize,
            hpxx: "A"
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
    selectTable() {
      this.tablePage.currentPage = 1;
      this.tablePage.pageSize = 20;
      this.getDate();
    },
    viewZc() {
      this.selectDataList = this.$refs.tableTransfer.getCheckboxRecords();
      if (this.selectDataList.length == 0) {
        this.$message.error("请选择一个磅单");
        return;
      }
      this.dialogFormVisibleViewZc = true;
    },
    viewZh() {
      this.dialogFormVisibleViewZh = true;
    },
    dictItemVisible() {
      this.dialogFormVisibleViewZc = false;
      this.dialogFormVisibleViewZh = false;
    },
    formatterBpwc({ cellValue }) {
      if (cellValue == 1) {
        return "已完成";
      } else {
        return "未完成";
      }
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    sortChangeEvent({ column, property, order }) {
      this.formObj.sqlSortNameOfMrLI = property;
      this.formObj.sqlSortOfMrLI = order;
      this.selectTable();
    },
  }
};
</script>
<style lang="scss" scoped>
.transport-transferBc-index {
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