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
  <div class="caccentre-shipquery-index">
    <basic-container>
      <!--查询区-->
      <vxe-form :data="formObj" size="mini">
        <vxe-form-item title="中文船名" field="shipNam" size="mini" span="5">
          <EditDown
            :objs="formObj"
            :property="downTableOption.shipNam"
            :tableColumns="downTableOption.shipNamColum"
            :urlapi="downTableOption.shipNamurl"
            :returnApi="downTableOption.shipNamreturnApi"
            pageLayOutType="mini"
            sizenow="mini"
            pageSize="30"
            height="650"
            width="380"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item title="英文船名" field="eShipNam" size="mini" span="5">
          <EditDown
            :objs="formObj"
            :property="downTableOption.eShipNam"
            :tableColumns="downTableOption.eShipNamColum"
            :urlapi="downTableOption.eShipNamurl"
            :returnApi="downTableOption.eShipNamreturnApi"
            pageLayOutType="mini"
            sizenow="mini"
            pageSize="100"
            height="650"
            width="380"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="left">
          <vxe-button status="primary" icon="fa fa-search" @click="getDate()"
            >查询</vxe-button
          >
        </vxe-form-item>
      </vxe-form>
      <!--功能按钮-->
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
        height="650px"
        ref="tableMan"
      >
        <vxe-table-column type="seq" width="50" title="序号"></vxe-table-column>
        <vxe-table-column
          field="shipNam"
          title="中文船名"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="eShipNam"
          title="英文船名"
          width="80"
        ></vxe-table-column>
        <vxe-table-column
          field="callNo"
          title="呼号"
          width="80"
        ></vxe-table-column>
        <vxe-table-column
          field="imoNo"
          title="IMO_NO"
          width="80"
        ></vxe-table-column>
        <vxe-table-column
          field="berthCod"
          title="泊位"
          width="80"
        ></vxe-table-column>
        <vxe-table-column
          field="tradeId"
          title="内外贸"
          width="60"
          :formatter="formattertradeId"
        ></vxe-table-column>
        <vxe-table-column
          field="inCargoNam"
          title="进口货名"
          width="120"
        ></vxe-table-column>
        <vxe-table-column
          field="inShipCap"
          title="进口载重"
          width="120"
        ></vxe-table-column>
        <vxe-table-column
          field="outCargoNam"
          title="出口货名"
          width="120"
        ></vxe-table-column>
        <vxe-table-column
          field="outShipCap"
          title="出口载重"
          width="100"
        ></vxe-table-column>
        <vxe-table-column field="agent" title="代理" width="120">
          <vxe-table-column
            field="agentCod"
            title="代码"
            width="70"
          ></vxe-table-column>
          <vxe-table-column
            field="agentNam"
            title="名称"
            width="70"
          ></vxe-table-column>
        </vxe-table-column>
        <vxe-table-column
          field="toPortTim"
          title="抵港时间"
          width="150"
        ></vxe-table-column>
        <vxe-table-column
          field="levPortTim"
          title="离港时间"
          width="150"
        ></vxe-table-column>
        <vxe-table-column field="agent" title="理货单位" width="120">
          <vxe-table-column
            field="tallyCod"
            title="代码"
            width="70"
          ></vxe-table-column>
          <vxe-table-column
            field="tallyNam"
            title="名称"
            width="100"
          ></vxe-table-column>
        </vxe-table-column>
        <vxe-table-column
          field="entrustNam"
          title="委托人"
          width="140"
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
          'Total',
        ]"
        @page-change="handlePageChange"
      ></vxe-pager>
    </basic-container>
  </div>
</template>

<script>
import { fetchList } from "@/api/caccentre/shipquery";
import { tableOption } from "@/const/crud/caccentre/shipquery";
import { mapGetters } from "vuex";
import EditDown from "../../baseUtile/EditDown";
export default {
  name: "caccentre-shipquery-index",
  components: { EditDown },
  data() {
    return {
      formObj: {
        shipNam: null,
        eShipNam: null,
      },
      searchForm: {},
      tableData: [],
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      tableload: false,
      tableOption: tableOption,
      downTableOption: {
        //中文船名
        shipNam: "shipNam",
        shipNamColum: [
          { field: "SHIP_NAM", title: "中文船名" },
        ],
        shipNamreturnApi: [
          { field: "SHIP_NAM", returnName: "shipNam" },
        ],

        shipNamurl: "/caccentre/shipquery/getshipNam",
        //英文船名
        eShipNam: "eShipNam",
        eShipNamColum: [
          { field: "E_SHIP_NAM", title: "中文船名" },
        ],
        eShipNamreturnApi: [
          { field: "E_SHIP_NAM", returnName: "eShipNam" },
        ],

        eShipNamurl: "/caccentre/shipquery/geteShipNam",
      },
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
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    sizeChange(pageSize) {
      this.tablePage.pageSize = pageSize;
    },
    currentChange(current) {
      this.tablePage.currentPage = current;
    },
    searchChange(form, done) {
      this.searchForm = form;
      this.tablePage.currentPage = 1;
      this.getList(this.tablePage, form);
      done();
    },
    refreshChange() {
      this.getList(this.tablePage);
    },
    formattertradeId({ cellValue }) {
      if (cellValue == 1) {
        return "内贸";
      } if (cellValue == 0) {
        return "外贸";
      }
    },
  },
};
</script>