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
  <div class="mioyardhead">
    <basic-container>
      <vxe-form :data="searchForm" size="mini">
        <vxe-form-item title="日期：" field="datePlan" span="6">
          <vxe-input
            v-model="searchForm.datePlan"
            type="date"
            placeholder="请选择日期"
            clearable
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item span="18" size="mini" align="center">
          <vxe-button status="primary" icon="fa fa-search" @click="getDate()"
            >查询</vxe-button
          >
          <vxe-button status="primary" icon="fa fa-search" @click="insert()"
            >接收到港船信息</vxe-button
          >
          <vxe-button status="primary" icon="fa fa-search" @click="copyText()"
            >下载备注信息</vxe-button
          >
           <vxe-button status="primary" icon="fa fa-search" @click="copyPlanDataView()"
            >接收船舶计划</vxe-button
          >
        </vxe-form-item>
      </vxe-form>
      <vxe-form :data="datahead" size="mini">
        <vxe-form-item title="主任：" field="masterMan" span="12">
          <vxe-input v-model="datahead.masterMan"></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="计划员：" field="planMan" span="12">
          <vxe-select v-model="datahead.planMan" clearable>
            <vxe-option value="步延民" label="步延民"></vxe-option>
            <vxe-option value="吕健" label="吕健"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item title="重点说明：" field="contentTxt" span="24">
          <vxe-textarea v-model="datahead.contentTxt"></vxe-textarea>
        </vxe-form-item>
      </vxe-form>
      <vxe-toolbar perfect size="mini">
        <template v-slot:buttons>
          <vxe-button type="text" icon="fa fa-plus" @click="insertEvent()"
            >新增</vxe-button
          >
          <vxe-button type="text" icon="fa fa-floppy-o" @click="saveAll()"
            >保存</vxe-button
          >
          <vxe-button type="text" icon="fa fa-trash" @click="del()"
            >删除</vxe-button
          >
          <vxe-button type="text" class="fa fa-file-excel-o" @click="excelOut()"
            >EXCEL导出</vxe-button
          >
          <vxe-button type="text" class="fa fa-print" @click="printAll()"
            >综合打印</vxe-button
          >
          <vxe-button
            type="text"
            class="fa fa-info-circle"
            @click="shipdetail()"
            >船信息 </vxe-button
          >
          <vxe-button
            type="text"
            class="fa fa-id-card-o"
            @click="getshipdata()"
            >船资料 </vxe-button
          >
          <vxe-button
            type="text"
            class="fa fa-newspaper-o"
            @click="shipContract()"
            >查看合同 </vxe-button
          >
        </template>
      </vxe-toolbar>
      <el-row>
        <el-col :span="24">
          <vxe-table
            border
            show-overflow
            keep-source
            highlight-current-row
            :export-config="{}"
            size="mini"
            :data="tableData"
            :loading="tableload"
            :round="true"
            align="center"
            height="500"
            ref="tableShipBody"
            :edit-config="{ trigger: 'click', mode: 'cell', showStatus: true }"
            :mouse-config="{ selected: true }"
            :keyboard-config="{
              isArrow: true,
              isEnter: true,
              isTab: true,
              isEdit: true,
              enterToTab: true,
            }"
            :row-style="rowStyle"
            :sort-config="{ remote: true }"
            @cell-click="rowclick"
            @sort-change="sortChangeEvent"
          >
            <vxe-table-column
              title="泊位"
              field="berthCod"
              width="80"
              fixed="left"
              sortable
              :edit-render="{
                name: 'EditDownTable',
                props: {
                  checkField: 'berthCod',
                  tableColumn: this.downTableOption.berthColum,
                  urlapi: this.downTableOption.berthurl,
                  returnApi: this.downTableOption.berthreturnApi,
                },
              }"
            ></vxe-table-column>
            <vxe-table-column
              title="船名"
              field="shipNam"
              width="100"
              fixed="left"
              :edit-render="{
                name: 'EditDownTable',
                props: {
                  checkField: 'shipNam',
                  tableColumn: this.downTableOption.shipColum,
                  urlapi: this.downTableOption.shipurl,
                  returnApi: this.downTableOption.shipreturnApi,
                },
              }"
            ></vxe-table-column>
            <vxe-table-column
              title="清污协议"
              field="cleanprot"
              width="100"
              :edit-render="{ name: 'input' }"
            ></vxe-table-column>
            <vxe-table-column
              title="代理名称"
              field="agentNam"
              width="150"
              :edit-render="{
                name: 'EditDownTable',
                props: {
                  checkField: 'agentNam',
                  tableColumn: this.downTableOption.agentColum,
                  urlapi: this.downTableOption.agenturl,
                  returnApi: this.downTableOption.agentreturnApi,
                },
              }"
            ></vxe-table-column>
            <vxe-table-column
              title="单位名称"
              field="unitNam"
              width="200"
              :edit-render="{
                name: 'EditDownTable',
                props: {
                  checkField: 'unitNam',
                  tableColumn: this.downTableOption.unitColum,
                  urlapi: this.downTableOption.uniturl,
                  returnApi: this.downTableOption.unitreturnApi,
                },
              }"
            ></vxe-table-column>
            <vxe-table-column
              title="装卸别"
              field="unloadId"
              width="80"
              :edit-render="{ name: 'input' }"
            ></vxe-table-column>
            <vxe-table-column
              title="货名"
              field="cargoNam"
              width="100"
              :edit-render="{ name: 'input' }"
            ></vxe-table-column>
            <vxe-table-column
              title="重量"
              field="weightWgt"
              width="70"
              :edit-render="{ name: 'input' }"
            ></vxe-table-column>
            <vxe-table-column
              title="余吨"
              field="surplusTon"
              width="70"
              :edit-render="{ name: 'input' }"
            ></vxe-table-column>
            <vxe-table-column
              title="起地点"
              field="beginPlace"
              width="100"
              :edit-render="{
                name: 'EditDownTable',
                props: {
                  checkField: 'beginPlace',
                  tableColumn: this.downTableOption.berthColum,
                  urlapi: this.downTableOption.berthurl,
                  returnApi: this.downTableOption.beginreturnApi,
                },
              }"
            ></vxe-table-column>
            <vxe-table-column
              title="止地点"
              field="endPlace"
              width="100"
              :edit-render="{
                name: 'EditDownTable',
                props: {
                  checkField: 'endPlace',
                  tableColumn: this.downTableOption.berthColum,
                  urlapi: this.downTableOption.berthurl,
                  returnApi: this.downTableOption.endreturnApi,
                },
              }"
            ></vxe-table-column>
            <vxe-table-column
              title="动向"
              field="statusNam"
              width="80"
              :edit-render="{
                name: 'EditDownTable',
                props: {
                  checkField: 'statusNam',
                  tableColumn: this.downTableOption.statusColum,
                  urlapi: this.downTableOption.statusurl,
                  returnApi: this.downTableOption.statusreturnApi,
                },
              }"
            ></vxe-table-column>
            <vxe-table-column
              title="靠泊刻度"
              field="firstNum"
              width="100"
              :edit-render="{ name: 'input' }"
            ></vxe-table-column>
            <vxe-table-column
              title="临时抛锚时间"
              field="toLsportTim"
              width="110"
              :formatter="['formatDate', 'MM/dd HH:mm']"
              :edit-render="{
                name: 'EditTimeTable',
                props: {
                  type: 'datetimeInput',
                  format: 'MM/dd HH:mm',
                  valueFormat: 'MMDDHH',
                },
              }"
            ></vxe-table-column>
            <vxe-table-column
              title="抵港时间"
              field="toPortTim"
              width="120"
              sortable
              :formatter="['formatDate', 'MM/dd HH:mm']"
              :edit-render="{
                name: 'EditTimeTable',
                props: {
                  type: 'datetimeInput',
                  format: 'MM/dd HH:mm',
                  valueFormat: 'MMDDHH',
                },
              }"
            ></vxe-table-column>
            <vxe-table-column
              title="靠泊时间"
              field="planBerthTim"
              width="120"
              sortable
              :formatter="['formatDate', 'MM/dd HH:mm']"
              :edit-render="{
                name: 'EditTimeTable',
                props: {
                  type: 'datetimeInput',
                  format: 'MM/dd HH:mm',
                  valueFormat: 'MMDDHH',
                },
              }"
            ></vxe-table-column>
            <vxe-table-column
              title="开工时间"
              field="planBeginTim"
              width="120"
              sortable
              :formatter="['formatDate', 'MM/dd HH:mm']"
              :edit-render="{
                name: 'EditTimeTable',
                props: {
                  type: 'datetimeInput',
                  format: 'MM/dd HH:mm',
                  valueFormat: 'MMDDHH',
                },
              }"
            ></vxe-table-column>
            <vxe-table-column
              title="完工时间"
              field="planEndTim"
              width="120"
              sortable
              :formatter="['formatDate', 'MM/dd HH:mm']"
              :edit-render="{
                name: 'EditTimeTable',
                props: {
                  type: 'datetimeInput',
                  format: 'MM/dd HH:mm',
                  valueFormat: 'MMDDHH',
                },
              }"
            ></vxe-table-column>
            <vxe-table-column
              title="离港时间"
              field="planLeavTim"
              width="120"
              sortable
              :formatter="['formatDate', 'MM/dd HH:mm']"
              :edit-render="{
                name: 'EditTimeTable',
                props: {
                  type: 'datetimeInput',
                  format: 'MM/dd HH:mm',
                  valueFormat: 'MMDDHH',
                },
              }"
            ></vxe-table-column>
            <vxe-table-column
              title="移泊时间"
              field="moveBerthTim"
              width="120"
              sortable
              :formatter="['formatDate', 'MM/dd HH:mm']"
              :edit-render="{
                name: 'EditTimeTable',
                props: {
                  type: 'datetimeInput',
                  format: 'MM/dd HH:mm',
                  valueFormat: 'MMDDHH',
                },
              }"
            ></vxe-table-column>
            <vxe-table-column
              title="船长"
              field="shipLongNum"
              width="70"
              :edit-render="{ name: 'input' }"
            ></vxe-table-column>
            <vxe-table-column
              title="抵港艏吃水"
              field="arrivHeadWat"
              width="100"
              :edit-render="{ name: 'input' }"
            ></vxe-table-column>
            <vxe-table-column
              title="抵港艉吃水"
              field="arrivTailWat"
              width="100"
              :edit-render="{ name: 'input' }"
            ></vxe-table-column>
            <vxe-table-column
              title="离港艏吃水"
              field="levHeadWat"
              width="100"
              :edit-render="{ name: 'input' }"
            ></vxe-table-column>
            <vxe-table-column
              title="离港艉吃水"
              field="levTailWat"
              width="100"
              :edit-render="{ name: 'input' }"
            ></vxe-table-column>
            <vxe-table-column
              title="拖轮"
              field="tugboatId"
              width="60"
              :formatter="formatterTl"
              :edit-render="{ name: '$select', options: tlList }"
            ></vxe-table-column>
            <vxe-table-column
              title="引航"
              field="pilotId"
              width="60"
              :formatter="formatterTl"
              :edit-render="{ name: '$select', options: tlList }"
            ></vxe-table-column>
            <vxe-table-column
              title="备注"
              field="remarkTxt"
              width="120"
              :edit-render="{ name: 'input' }"
            ></vxe-table-column>
          </vxe-table>
          <vxe-pager
            perfect
            size="mini"
            :loading="tableload"
            :current-page="tablePage.currentPage"
            :page-size="tablePage.pageSize"
            :total="tablePage.total"
            :page-sizes="[ 100, 200, 300]"
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
        </el-col>
      </el-row>
      <vxe-modal
        v-if="dialogFormVisibleViewPlan"
        v-model="dialogFormVisibleViewPlan"
        title="接收各公司船舶计划"
        width="500"
        height="750"
        resize
        storage
      >
        <PlanByUnit :dataForm="dataNext" @dictItemVisible="dictItemVisible"></PlanByUnit>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibledetail"
        v-model="dialogFormVisibledetail"
        title="船舶信息"
        width="1000"
        height="800"
        resize
        storage
        fullscreen
      >
         <Formdetail
          :datanow="dataNext"
          @dictItemVisiblenew="dictItemVisiblenew"
          
        ></Formdetail>
         </vxe-modal>
         <vxe-modal
        v-if="dialogFormVisibleshipdata"
        v-model="dialogFormVisibleshipdata"
        title="船舶资料"
        width="1000"
        height="800"
        resize
        storage
        fullscreen
      >
         <Shipdata
          :datanow="dataNext"
          @dictItemVisibleshipdata="dictItemVisibleshipdata"
          
        ></Shipdata>
         </vxe-modal>
        <vxe-modal  v-if="shipContractFormVisible"
        v-model="shipContractFormVisible"
        title="合同列表"
        width="1100"
        height="880"
        resize
        storage>
        <shipcontract :objrowcon="objrowcon" @dictItemVisibleshipContract="dictItemVisibleshipContract" readonly="true"></shipcontract><!--form标签 -->
      </vxe-modal>
        
    </basic-container>
  </div>
</template>

<script>
import {
  fetchList,
  insertOne,
  copyText,
  copyPlanData,
  insertPlanData,
  List,
  sjpd,
  saveBodyData,
  excelOutPost,
  deleteb,
} from "@/api/caccentre/shipplanbody";
import {
  getObj,
  } from "@/api/caccentre/ship";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import EditDown from "../../baseUtile/EditDown";
import PlanByUnit from "./planByUnit";
import Formdetail from "../ship/indexshipeditCjh";
import Shipdata from "../shipdata/shipdata";
import shipcontract from "../../business/businesscontractship/index";
import moment from "moment";
export default {
  components: { EditDown,PlanByUnit,Formdetail,Shipdata,shipcontract },
  name: "mioyardhead",
  data() {
    return {
      datahead: { masterMan: "杨传祥", planMan: "", contentTxt: "" },
      searchForm: { datePlan: moment(new Date()).format("YYYY-MM-DD") },
      dataNext:{},
      tableData: [],
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 100, // 每页显示多少条
      },
      tableload: false,
      dialogFormVisibleViewPlan: false,
      dialogFormVisibledetail: false,
      dialogFormVisibleshipdata: false,
      shipContractFormVisible: false,
      dataNow: {},
      tlList: [
        { label: "", value: "" },
        { label: "是", value: "1" },
        { label: "否", value: "0" },
      ],
      downTableOption: {
        //泊位下拉菜单展示项
        berthColum: [
          { field: "BERTH_COD", title: "泊位代码" },
          { field: "BERTH_NAM", title: "泊位名称" },
          { field: "UNIT_NAM", title: "公司名称" },
        ],
        //泊位下拉菜单的返回项
        berthreturnApi: [{ field: "BERTH_COD", returnName: "berthCod" }],
        beginreturnApi: [{ field: "BERTH_NAM", returnName: "beginPlace" }],
        endreturnApi: [{ field: "BERTH_NAM", returnName: "endPlace" }],
        //泊位下拉菜单的路径
        berthurl: "/basecode/basecod/getBerth",

        //代理下拉菜单展示项
        agentColum: [
          { field: "AGENT_COD", title: "代理代码" },
          { field: "AGENT_NAM", title: "代理名称" },
        ],
        //代理下拉菜单的返回项
        agentreturnApi: [
          { field: "AGENT_COD", returnName: "agentCod" },
          { field: "AGENT_NAM", returnName: "agentNam" },
        ],
        //代理下拉菜单的路径
        agenturl: "/basecode/basecod/getAgent",

        //单位下拉菜单展示项
        unitColum: [
          { field: "UNIT_COD", title: "单位代码" },
          { field: "UNIT_NAM", title: "单位名称" },
        ],
        //单位下拉菜单的返回项
        unitreturnApi: [
          { field: "UNIT_COD", returnName: "unitCod" },
          { field: "UNIT_NAM", returnName: "unitNam" },
        ],
        //单位下拉菜单的路径
        uniturl: "/basecode/basecod/getUnitNam?id=0",

        //动向下拉菜单展示项
        statusColum: [
          { field: "CODE", title: "动向代码" },
          { field: "MEANING", title: "动向名称" },
        ],
        //动向下拉菜单的返回项
        statusreturnApi: [
          { field: "CODE", returnName: "statusCod" },
          { field: "MEANING", returnName: "statusNam" },
        ],
        //动向下拉菜单的路径
        statusurl: "/basecode/basecod/getShipPlanStatus",
        //船舶下拉菜单展示项
        shipColum: [
          { field: "SHIP_NO", title: "船号", width: "12%" },
          { field: "SHIP_NAM", title: "船名", width: "14%" },
          { field: "BERTH_COD", title: "泊位", width: "14%" },
          { field: "UNLOAD_ID", title: "装卸", width: "12%" },
          { field: "CARGO_NAM", title: "货名", width: "14%" },
          { field: "WEIGHT_WGT", title: "重量", width: "14%" },
          { field: "TO_PORT_TIM", title: "抵港时间", width: "20%" },
          { field: "PORT_ID", title: "标志", width: "14%" },
        ],
        //船舶下拉菜单的返回项
        shipreturnApi: [
          { field: "SHIP_NO", returnName: "shipNo" },
          { field: "SHIP_NAM", returnName: "shipNam" },
          { field: "CARGO_NAM_COD", returnName: "cargoNamCod" },
          { field: "UNLOAD_ID", returnName: "unloadId" },
          { field: "CARGO_NAM", returnName: "cargoNam" },
          { field: "WEIGHT_WGT", returnName: "weightWgt" },
          { field: "TO_PORT_TIM", returnName: "toPortTim" },
          { field: "TO_LSPORT_TIM", returnName: "toLsportTim" },
          { field: "BEGIN_TIM", returnName: "planBeginTim" },
          { field: "END_TIM", returnName: "planEndTim" },
          { field: "TO_BERTH_TIM", returnName: "planBerthTim" },
          { field: "FST_REG_NO", returnName: "fstRegNo" },
          { field: "CLEANPROT", returnName: "cleanprot" },
          { field: "SHIP_LONG_NUM", returnName: "shipLongNum" },
          { field: "ARRIV_HEAD_WAT", returnName: "arrivHeadWat" },
          { field: "ARRIV_TAIL_WAT", returnName: "arrivTailWat" },
          { field: "LEV_HEAD_WAT", returnName: "levHeadWat" },
          { field: "LEV_TAIL_WAT", returnName: "levTailWat" },
          { field: "AGENT_COD", returnName: "agentCod" },
          { field: "AGENT_NAM", returnName: "agentNam" },
          { field: "CALL_NO", returnName: "callNo" },
        ],
        //船舶下拉菜单的路径
        shipurl: "/caccentre/shipplanbody/getShip",
      },
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  created() {
    this.getDate();
    this.getHead();
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
          this.searchForm
        )
      )
        .then((response) => {
          this.tableData = response.data.data.records;
          this.tablePage.total = response.data.data.total;
          this.tableload = false;
          this.dataNow = {};
          this.getHead();
        })
        .catch(() => {
          this.tableload = false;
        });
    },

    getHead() {
      List(
        Object.assign(
          {
            current: 1,
            size: 20,
          },
          this.searchForm
        )
      )
        .then((response) => {
          if (response.data.data.total > 0) {
            this.datahead = response.data.data.records[0];
          }
        })
        .catch(() => {});
    },

    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },

    formatterTl({ cellValue }) {
      if (cellValue == "1") {
        return "是";
      } else if (cellValue == "0") {
        return "否";
      }
    },
    
    excelOut() {
      if (
        this.datahead != null &&
        this.datahead.planMan != null &&
        this.datahead.planMan != ""
      ) {
        this.tableload = true;
        excelOutPost(this.searchForm.datePlan)
          .then(() => {
            this.tableload = false;
          })
          .catch(() => {
            this.tableload = false;
          });
      } else {
        this.$message.error("请选择一天数据进行导出");
      }
    },
    printAll() {
      var rq = this.searchForm.datePlan.replace(new RegExp("-", "gm"), "");
      window.open(
        "http://10.135.149.248:8080/webroot/decision/view/report?viewlet=ddzhpt/港口船舶作业综合计划表.cpt&wl_plan_dte=" +
          rq,
        "_blank"
      );
    },
    rowclick({ row }) {
      this.dataNow = row;
    },

    shipdetail() {
      if (this.dataNow != null && this.dataNow.shipNo != null) {
        console.log("dataNow.shipno=="+this.dataNow.shipNo);
        var that = this;
        getObj(this.dataNow.shipNo)
        .then((response) => {
          if(response.data.data ==null)
          {this.$alert("无法获取该船信息!", "错误");}
          else{
          console.log("ship=="+response.data.data.shipNam);
          that.dataNext = response.data.data; //后台执行结果，赋给dataNext
          this.dialogFormVisibledetail = true;
          }
        })
        .catch(() => {
          loading();
        });
         } else {
          this.$message.error("请选择一个数据进行查看");
        }
    },
    dictItemVisiblenew() {
      this.dialogFormVisibledetail = false;
    },
    getshipdata() {
      if (this.dataNow != null && this.dataNow.shipNo != null) {
        console.log("dataNow.shipno=="+this.dataNow.shipNo);
        var that = this;
        getObj(this.dataNow.shipNo)
        .then((response) => {
          if(response.data.data ==null)
          {this.$alert("无法获取该船资料!", "错误");}
          else{
          console.log("dataNext.callNo=="+response.data.data.callNo);
          that.dataNext.callNo = response.data.data.callNo; //后台执行结果，赋给dataNext
          that.dialogFormVisibleshipdata = true;}
        })
        .catch(() => {
          
        });
         } else {
          this.$message.error("请选择一个数据进行查看");
        }
    },
    dictItemVisibleshipdata() {
      this.dialogFormVisibleshipdata = false;
    },

    //-----点击合同--------开始
    shipContract() {
      if (this.dataNow != null && this.dataNow.shipNo != null) {
        console.log("dataNow.shipno=="+this.dataNow.shipNo);
      this.objrowcon = this.dataNow; //将本行数据赋给dataNow，传到下个页面
      this.shipContractFormVisible = true;
         } else {
          this.$message.error("请选择一个数据进行查看");
        }
    },
    dictItemVisibleshipContract() {
      this.shipContractFormVisible = false;
    },
    //--------------结束


    insert() {
      if (this.datahead.planMan == "") {
        this.$message.success("请输入计划员");
        return;
      }
      let that = this;
      this.tableload = true;
      sjpd(this.searchForm.datePlan)
        .then((data) => {
          this.searchForm.masterMan = this.datahead.masterMan;
          this.searchForm.planMan = this.datahead.planMan;
          this.searchForm.contentTxt = this.datahead.contentTxt;
          this.searchForm.dataKnd = '0';
          if (data.data.data == "1") {
            this.$confirm("该天数据已经产生，是否确定重新处理", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning",
            }).then(
              function () {
                return insertOne(that.searchForm).then((data) => {
                  that.$message.success("接收成功");
                  that.getDate();
                  that.tableload = false;
                });
              },
              function () {
                that.tableload = false;
              }
            );
          } else {
            insertOne(this.searchForm).then((data) => {
              this.$message.success("接收成功");
              this.getDate();
              this.tableload = false;
            });
          }
        })
        .catch(() => {
          this.tableload = false;
        });
    },
    copyText() {
      this.tableload = true;
      copyText(this.searchForm)
        .then((data) => {
          this.$message.success("下载成功");
          this.getDate();
        })
        .catch(() => {
          this.tableload = false;
        });
    },
    
//----------接收船舶计划--------
    copyPlanDataView() {
      if (this.datahead.planMan == "") {
        this.$message.success("请输入计划员");
        return;
      } 
      this.searchForm.masterMan = this.datahead.masterMan;
      this.searchForm.planMan = this.datahead.planMan;
      this.searchForm.contentTxt = this.datahead.contentTxt;
      this.searchForm.dataKnd = '1';
      this.dataNext = this.searchForm;
        this.dialogFormVisibleViewPlan = true;
        
      
    },
    dictItemVisible() {
      this.dialogFormVisibleViewPlan = false;
      this.getDate();
    },
//----------接收船舶计划-结束--------
    async insertEvent(row) {
      if (this.datahead.planMan == "") {
        this.$message.success("请输入计划员");
        return;
      }
      let record = {
        planDte: this.searchForm.datePlan,
        masterMan: this.datahead.masterMan,
        planMan: this.datahead.planMan,
        contentTxt: this.datahead.contentTxt,
      };
      let { row: newRow } = await this.$refs.tableShipBody.insertAt(
        record,
        row
      );
      await this.$refs.tableShipBody.setActiveCell(newRow, "berthCod");
    },

    async saveAll() {
      const errMap = await this.$refs.tableShipBody
        .fullValidate()
        .catch((errMap) => errMap);
      if (errMap) {
      } else {
        this.tableload = true;
        saveBodyData(this.$refs.tableShipBody.getRecordset())
          .then((response) => {
            this.$message.success(response.data.data);
            this.tableload = false;
            this.getDate();
          })
          .catch(() => {
            this.tableload = false;
          });
      }
    },
    
    del() {
      if (this.dataNow == null || this.dataNow.sequenceId == null) {
        this.$message.error("请选择一条数据进行删除");
        return;
      }
      this.tableload = true;
      deleteb(this.dataNow.sequenceId)
        .then((response) => {
          this.$message.success(response.data.data);
          this.tableload = false;
          this.getDate();
        })
        .catch(() => {
          this.tableload = false;
        });
    },
    rowStyle({ row, rowIndex }) {
      if (row.ifCont === "00") {
        return {
          color: "red",
        };
      }
    },
    sortChangeEvent({ column, property, order }) {
      this.searchForm.sqlSortNameOfMrLI = property;
      this.searchForm.sqlSortOfMrLI = order;
      this.getDate();
    },
  },
};
</script>
<style lang="scss" scoped>
.mioyardhead {
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