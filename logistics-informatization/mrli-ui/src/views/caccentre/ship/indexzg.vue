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
  <div class="caccentre-ship-indexzg">
    <vxe-form :data="searchForm" size="mini">
      <vxe-form-item title="时间段：" field="timebegin" size="mini" span="4">
        <vxe-input
          v-model="searchForm.timebegin"
          placeholder="开始时间"
          type="datetime"
          valueFormat="yyyy-MM-dd HH:mm:ss"
        ></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="至" field="timeend" size="mini" span="4">
        <vxe-input
          v-model="searchForm.timeend"
          placeholder="结束时间"
          type="datetime"
          valueFormat="yyyy-MM-dd HH:mm:ss"
        ></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="船名：" field="shipNam" size="mini" span="3">
        <vxe-input
          v-model="searchForm.shipNam"
          clearable
          size="mini"
        ></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="泊位" field="berthCod" size="mini" span="3">
        <EditDown
          :objs="searchForm"
          :property="downTableOption.berthNam"
          :tableColumns="downTableOption.berthNamColum"
          :urlapi="downTableOption.berthNamurl"
          :returnApi="downTableOption.berthNamreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="内外贸：" field="tradeId" size="mini" span="2">
        <vxe-select v-model="searchForm.tradeId" clearable size="mini">
          <vxe-option value="0" label="外贸"></vxe-option>
          <vxe-option value="1" label="内贸"></vxe-option>
        </vxe-select>
      </vxe-form-item>
      <vxe-form-item span="4" size="mini" align="center">
        <vxe-button status="primary" icon="fa fa-search" @click="searchChange()"
          >查询</vxe-button
        >
        <vxe-button type="reset" icon="fa fa-repeat">重置</vxe-button>
      </vxe-form-item>
    </vxe-form>
    <vxe-toolbar perfect custom size="mini">
      <template v-slot:buttons>
        <vxe-button type="text" >在港船舶数量：{{CountZgAll }}</vxe-button>
        <vxe-button type="text" >鲅鱼圈：{{CountZgByq }}</vxe-button>
        <vxe-button type="text" >仙人岛：{{CountZgXrd }}</vxe-button>
      </template>
    </vxe-toolbar>
    <vxe-table
      border
      show-overflow
      keep-source
      stripe
      highlight-current-row
      highlight-hover-row
      size="mini"
      :data="tableData"
      :loading="tableLoading"
      :round="true"
      align="center"
      height="600px"
      ref="tableShip"
      @cell-click="rowclick"
      :custom-config="{ storage: true }"
      :sort-config="{ remote: true }"
      @sort-change="sortChangeEvent"
    >
      <vxe-table-column
        type="seq"
        title="序号"
        fixed="left"
        width="50"
      ></vxe-table-column>
      <vxe-table-column
        field="berthCod"
        title="泊位"
        fixed="left"
        width="70"
        sortable
      ></vxe-table-column>
      <vxe-table-column
        field="shipNam"
        title="船名"
        fixed="left"
        width="100"
        sortable
      ></vxe-table-column>
      <vxe-table-colgroup title="作业单位" fixed="left">
        <vxe-table-column
          field="unitCod"
          title="代码"
          width="70"
          sortable
        ></vxe-table-column>
        <vxe-table-column
          field="unitNam"
          title="名称"
          width="210"
          sortable
        ></vxe-table-column>
      </vxe-table-colgroup>
      <vxe-table-column
        field="unloadId"
        title="装卸"
        fixed="left"
        width="60"
      ></vxe-table-column>
      <vxe-table-column
        field="cargoNam"
        title="货名"
        fixed="left"
        width="80"
      ></vxe-table-column>
      <vxe-table-column
        field="planWeightNam"
        title="计划量"
        fixed="left"
        width="80"
      ></vxe-table-column>
      <vxe-table-column
        field="weightNam"
        title="完成吨"
        fixed="left"
        width="60"
      ></vxe-table-column>
      <vxe-table-column
        field="zh"
        title="中海标志"
        width="80"
        :formatter="formatterZh"
      ></vxe-table-column>
      <vxe-table-column
        field="toLsportTim"
        title="临时抛锚时间"
        width="140"
        sortable
      ></vxe-table-column>
      <vxe-table-column
        field="toPortTim"
        title="到港时间"
        width="140"
        sortable
          :formatter="['formatDate', 'yyyy-MM-dd HH:mm:ss']"
      ></vxe-table-column>
      <vxe-table-column
        field="toBerthTim"
        title="靠泊时间"
        width="140"
        sortable
        :formatter="['formatDate', 'yyyy-MM-dd HH:mm:ss']"
      ></vxe-table-column>
      <vxe-table-column
        field="beginTim"
        title="开工时间"
        width="140"
        sortable
      ></vxe-table-column>
      <vxe-table-column
        field="endTim"
        title="完成时间"
        width="140"
        sortable
      ></vxe-table-column>
      <vxe-table-column
        field="beginTim"
        title="开工时间"
        width="140"
        sortable
      ></vxe-table-column>
      <vxe-table-column
        field="tradeName"
        title="内外贸"
        width="80"
      ></vxe-table-column>
      <vxe-table-column
        field="inCargoNam"
        title="进口货名"
        width="100"
        sortable
      ></vxe-table-column>
      <vxe-table-column
        field="inShipCap"
        title="进口载重"
        width="100"
        sortable
      ></vxe-table-column>
      <vxe-table-column
        field="outCargoNam"
        title="出口货名"
        width="100"
        sortable
      ></vxe-table-column>
      <vxe-table-column
        field="outShipCap"
        title="出口载重"
        width="100"
        sortable
      ></vxe-table-column>
      <vxe-table-column
        field="shipLongNum"
        title="船长"
        width="80"
        sortable
        sortBy='{"table":"a"}'
      ></vxe-table-column>
      <vxe-table-column
        field="dgcs"
        title="抵港吃水"
        width="100"
        sortable
      ></vxe-table-column>
      <vxe-table-column
        field="lgcs"
        title="离港吃水"
        width="100"
        sortable
      ></vxe-table-column>
      <vxe-table-column
        field="agentShort"
        title="代理"
        width="100"
        sortable
      ></vxe-table-column>
      <vxe-table-column
        field="placeN"
        title="北纬"
        width="80"
        sortable
      ></vxe-table-column>
      <vxe-table-column
        field="placeE"
        title="东经"
        width="80"
        sortable
      ></vxe-table-column>
      <vxe-table-column title="操作" width="280" fixed="right">
        <template v-slot="{ row }">
          <vxe-button type="text" icon="el-icon-check" @click="shipdetail(row)"
              v-if="cxBtn">查看</vxe-button
          >
          <vxe-button type="text" icon="el-icon-warning" @click="shipSub(row)"
             v-if="whBtn">配工船</vxe-button
          >
          <vxe-button
            type="text"
            icon="el-icon-warning"
            @click="shipContract(row)"
            v-if="cxBtn">合同</vxe-button
          >
          <vxe-button type="text" icon="el-icon-warning" @click="shiplg(row)"
            v-if="whBtn">离港</vxe-button
          >
        </template>
      </vxe-table-column>
    </vxe-table>
    <vxe-pager
      perfect
      size="mini"
      :loading="tableLoading"
      :current-page="page.currentPage"
      :page-size="page.pageSize"
      :total="page.total"
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

    <vxe-modal
      v-if="dialogFormVisible"
      v-model="dialogFormVisible"
      title="船舶信息维护"
      width="1000"
      height="800"
      resize
      storage
      fullscreen
    >
      <Form
        :objrow="objrow"
        @getList="getList"
        @dictItemVisible="dictItemVisible"
        readonly="true"
      ></Form>
    </vxe-modal>
    <vxe-modal
      v-if="shipsubFormVisible"
      v-model="shipsubFormVisible"
      title="配工船维护"
      width="1000"
      height="800"
      resize
      storage
    >
      <shipsub
        :objrowsub="objrowsub"
        @getList="getList"
        @dictItemVisible="dictItemVisible"
        readonly="true"
      ></shipsub
      ><!--form标签 -->
    </vxe-modal>
    <vxe-modal
      v-if="shipContractFormVisible"
      v-model="shipContractFormVisible"
      title="合同列表"
      width="1100"
      height="880"
      resize
      storage
    >
      <shipcontract
        :objrowcon="objrowcon"
        @getList="getList"
        @dictItemVisible="dictItemVisible"
        readonly="true"
      ></shipcontract
      ><!--form标签 -->
    </vxe-modal>
    <vxe-modal
      v-if="shiplgFormVisible"
      v-model="shiplgFormVisible"
      title="船舶离港"
      width="1000"
      height="800"
      resize
      storage
    >
      <shiplg
        :objrowlg="objrowlg"
        :objrow="objrow"
        @getList="getListPar"
        @dictItemVisible="shiplgItemVisible"
        readonly="true"
      ></shiplg> 
      <!--form标签 -->
    </vxe-modal>
       <vxe-modal
        v-if="dialogFormVisibledetail"
        v-model="dialogFormVisibledetail"
        title="船舶信息维护"
        width="1000"
        height="800"
        resize
        storage
        fullscreen
      >
         <Formdetail
          :datanow="dataNext"
          @dictItemVisiblenew="dictItemVisiblenew"
          @selectList="getList"
        ></Formdetail>
        </vxe-modal>
  </div>
</template>
<script>
import {
  fetchList,
  getObj,
  addObj,
  putObj,
  delObj,
  getCountShipt,
  getShipStatus,
  shipLg,
  getCountZg,
} from "@/api/caccentre/shipzg";
import { mapGetters } from "vuex";
import Form from "./indexshipeditzg";
import shipsub from "../shipsub/index";
import EditDown from "@/views/baseUtile/EditDown";
import shiplg from "./shiplg";
import Formdetail from "./indexshipdetail";
import shipcontract from "../../business/businesscontractship/index";
import Detail from '../../activiti/detail.vue';

export default {
  components: { EditDown, Form, shiplg, shipsub, shipcontract,Formdetail }, //加载的form名称，对应上方的标签名称
  name: "shipzg",
  data() {
    return {
      searchForm: { berthCod: null },
      tableData: [],
      dialogFormVisibleView: false,
      dialogFormVisible: false,
      shiplgFormVisible: false,
      shipsubFormVisible: false,
      shipContractFormVisible: false,
      dialogFormVisibledetail: false,
      CountZgAll: "",
      CountZgByq: "",
      CountZgXrd: "",
      activeName: "first",
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      tableLoading: false,
      objrow: {}, //编辑弹出form方法
      dataHeadNow: {},
      dateNow: {},
      objrowlg: {},
      downTableOption: {
        //泊位
        berthNam: "berthCod",
        berthNamColum: [
          { field: "BERTH_COD", title: "泊位代码" },
          { field: "BERTH_NAM", title: "泊位名称" },
        ],
        berthNamreturnApi: [
          { field: "BERTH_COD", returnName: "berthCod" },
          { field: "BERTH_NAM", returnName: "berthNam" },
        ],
        berthNamurl: "/basecode/basecod/getBerth",
      },
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  created() {
    this.searchChange();
    this.whBtn = this.permissions["CACCENTRE_SHIP_INDEX_WH"];
    this.cxBtn = this.permissions["CACCENTRE_SHIP_INDEX_CX"];
    this.dyBtn = this.permissions["CACCENTRE_SHIP_INDEX_DY"];
  },
  methods: {
    getList() {
      this.tableLoading = true;
      this.dataHeadNow = {};
      this.CountZg();
      fetchList(
        Object.assign(
          {
            current: this.page.currentPage,
            size: this.page.pageSize,
          },
          this.searchForm
        )
      )
        .then((response) => {
          this.tableData = response.data.data.records;
          this.page.total = response.data.data.total;
          this.tableLoading = false;
        })
        .catch(() => {
          this.tableLoading = false;
        });
      
    },
    getListPar() {
      this.getList();
    },

    CountZg() {
      getCountZg()
        .then((response) => {
          this.CountZgAll = response.data.data.CountZg;
          this.CountZgByq = response.data.data.CountZgByq;
          this.CountZgXrd = response.data.data.CountZgXrd;
        })
        .catch(() => {});
    },
    shipdetail(row) {
        var that = this;
        getObj(row.shipNo)
        .then((response) => {
          that.dataNext = response.data.data; //后台执行结果，赋给dataNext
          this.dialogFormVisibledetail = true;
        })
        .catch(() => {
          loading();
        });
    },
    dictItemVisiblenew() {
      this.dialogFormVisibledetail = false;
    },

    //点击编辑弹出form-------开始--不用了
    handleApt00: function (row) {
      this.objrow = row;
      this.dialogFormVisible = true;
    },
    dictItemVisible: function () {
      this.objrow = {};
      this.dialogFormVisible = false;
    },
    //--------------结束
    //-----点击配工船--------开始
    shipSub: function (row) {
      this.objrowsub = row; //将本行数据赋给objrowsub，传到下个页面
      this.shipsubFormVisible = true;
    },
    shipsubItemVisible: function () {
      this.objrow = {};
      this.shipsubFormVisible = false;
    },
    //--------------结束
    //点击编辑弹出form按照id获取-------开始
    handleApt: function (row) {
      var that = this;
      getObj(row.shipNo)
        .then((response) => {
          that.objrow = response.data.data; //后台执行结果，赋给objrow
          this.dialogFormVisible = true;
        })
        .catch(() => {
          loading();
        });
    },
    dictItemVisible: function () {
      this.objrow = {};
      this.dialogFormVisible = false;
    },

    //--------------结束

    //-----点击合同--------开始
    shipContract: function (row) {
      this.objrowcon = row; //将本行数据赋给objrowsub，传到下个页面
      this.shipContractFormVisible = true;
    },
    shipsubItemVisible: function () {
      this.objrow = {};
      this.shipContractFormVisible = false;
    },
    //--------------结束

    //-----点击离港--------开始
    shiplg: function (row) {
      this.objrowlg = row; //将本行数据赋给objrowlg，传到下个页面
      getCountShipt(row.shipNo)
        .then((response) => {
          let wl_count_end = response.data.data;
          console.log(row.shipNo);
          if (wl_count_end > 0) {
            this.$alert("船舶未完工，不能进行离港！", "错误");
          } else {
            getShipStatus(row.shipNo).then((response) => {
              this.objrowlg = response.data.data;
              this.objrow = row;
              this.objrow.statusId = "3";
              this.shiplgFormVisible = true;
            });
          }
        })
        .catch((response) => {
          console.log(response);
        });
    },
    shiplgItemVisible: function () {
      this.objrowlg = {};
      this.shiplgFormVisible = false;
    },
    //--------------结束

    rowclick({ row }) {
      this.dataHeadNow = row;
    },
    handlePageChange({ currentPage, pageSize }) {
      this.page.currentPage = currentPage;
      this.page.pageSize = pageSize;
      this.getList();
    },
    searchChange() {
      this.page.currentPage = 1;
      this.searchForm.sqlSortNameOfMrLI = null;
      this.searchForm.sqlSortOfMrLI = null;
      this.searchForm.sqlSortByTable = null;
      this.getList();
    },
    sortChangeEvent({ column, property, order }) {
      this.searchForm.sqlSortNameOfMrLI = property;
      if (column.sortBy != null && column.sortBy != undefined) {
        var jsonOject = JSON.parse(column.sortBy);
        if (jsonOject.table != null) {
          this.searchForm.sqlSortByTable = jsonOject.table;
        }
        if (jsonOject.nameExt != null) {
          this.searchForm.sqlSortNameOfMrLI = jsonOject.nameExt;
        }
      }
      this.searchForm.sqlSortOfMrLI = order;
      this.getList();
    },
    formatterZh({ cellValue }) {
      if (cellValue == 1) {
        return "是";
      } else {
        return "否";
      }
    },
  },
};
</script>
<style lang="scss" scoped>
.caccentre-ship-indexzg {
  .el-divider--horizontal {
    margin: 8px 0;
  }
  .vxe-toolbar {
    height: 30px;
  }
}
</style>