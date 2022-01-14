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
  <div class="shipJjList">
    <vxe-form :data="sumobj">
      <vxe-form-item title="总重量" field="weightWgtSum" size="mini" span="4">
        <vxe-input v-model="sumobj.weightWgtSum" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="总件数" field="piecesNumSum" size="mini" span="4">
        <vxe-input v-model="sumobj.piecesNumSum" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="总体积" field="cargoVolSum" size="mini" span="4">
        <vxe-input v-model="sumobj.cargoVolSum" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="实装总重量" field="workWeightWgtSum" size="mini" span="4">
        <vxe-input v-model="sumobj.workWeightWgtSum" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="实装总件数" field="workPiecesNumSum" size="mini" span="4">
        <vxe-input v-model="sumobj.workPiecesNumSum" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="实装总体积" field="workCargoVolSum" size="mini" span="4">
        <vxe-input v-model="sumobj.workCargoVolSum" disabled></vxe-input>
      </vxe-form-item>
    </vxe-form>
    <vxe-toolbar perfect size="mini" :loading="loading">
      <template v-slot:buttons>
        <vxe-button v-if="commitBtn" type="text" class="fa fa-arrow-up" @click="doJjd()">处理船舶交接单</vxe-button>
        <vxe-button  v-if="commitBtn" type="text" class="fa fa-pencil" @click="overShip()">上报船舶交接单</vxe-button>
      </template>
    </vxe-toolbar>
    <vxe-table
      border
      show-overflow
      size="mini"
      :data="tableData"
      :cell-style="cellStyle"
      :loading="loading"
    >
      <vxe-table-column type="seq" width="50" fixed="left"></vxe-table-column>
      <vxe-table-column field="billNo" width="90" title="运单号"></vxe-table-column>
      <vxe-table-column field="setownerNam" width="250" title="托运人"></vxe-table-column>
      <vxe-table-column field="getownerNam" width="250" title="收货人"></vxe-table-column>
      <vxe-table-column field="cargoMark" width="60" title="标志"></vxe-table-column>
      <vxe-table-column field="cargoNamCod" width="80" title="代码"></vxe-table-column>
      <vxe-table-column field="cargoNam" width="80" title="货名"></vxe-table-column>
      <vxe-table-column field="tradeId" width="60" title="内外贸" :formatter="formatterTradeId"></vxe-table-column>
      <vxe-table-column field="piecesNum" width="50" title="件数"></vxe-table-column>
      <vxe-table-column field="weightWgt" width="80" title="重量"></vxe-table-column>
      <vxe-table-column field="cargoVol" width="50" title="体积"></vxe-table-column>
      <vxe-table-column field="piecesWgt" width="50" title="件重"></vxe-table-column>
      <vxe-table-column field="workPiecesNum" width="80" title="实际件数"></vxe-table-column>
      <vxe-table-column field="workWeightWgt" width="80" title="实际重量"></vxe-table-column>
      <vxe-table-column field="workCargoVol" width="80" title="实际体积"></vxe-table-column>
      <vxe-table-column field="unitCod" width="80" title="作业单位"></vxe-table-column>
      <vxe-table-column field="markTxt" width="120" title="备注"></vxe-table-column>
      <vxe-table-column field="factEndPortNam" width="100" title="到达港"></vxe-table-column>
      <vxe-table-column field="ifendId4" width="80" title="货运确认" :formatter="formatterifendId4"></vxe-table-column>
      <vxe-table-column field="status" width="80" title="线上状态" :formatter="formatterStatus"></vxe-table-column>
    </vxe-table>
    <vxe-pager
      perfect
      size="mini"
      :loading="loading"
      :current-page="tablePage.currentPage"
      :page-size="tablePage.pageSize"
      :total="tablePage.total"
      :layouts="['PrevPage', 'JumpNumber', 'NextPage', 'FullJump', 'Sizes', 'Total']"
      @page-change="handlePageChange"
    ></vxe-pager>
  </div>
</template>

<script>
import {
  shipCargoList,
  shipCargoSum,
  doShipCargoBill,
  commitShipCargoBill
} from "@/api/ship/cdshipgrpt";
import request from "@/router/axios";
import "@/styles/common/edit.scss";
import moment from "moment";
import { mapGetters } from "vuex";
import EditDownTable from "../../baseUtile/EditDownTable";
import EditDown from "../../baseUtile/EditDown";
import Vue from "vue";
import VXETable from "vxe-table";
import hljs from "highlight.js";
import { Loading } from "element-ui";
import Feemain from "./feemain";
Vue.component(EditDownTable.name, EditDownTable);
// 创建一个下拉表格渲染
VXETable.renderer.add("EditDownTable", {
  autofocus: ".vxe-input--inner",
  renderEdit(h, renderOpts, params) {
    return [
      <edit-down-table
        params={params}
        renderOpts={renderOpts}
      ></edit-down-table>
    ];
  }
});

export default {
  props: ["objrow"],
  components: { EditDown, Feemain },
  data() {
    return {
      obj: {},
      objShip: {},
      sumobj: {},
      tableData: [],
      loading: true,
      buttonload: false,
      downTableOption: {},
      dialogFormVisible: false,
      tablePage: {
        currentPage: 1,
        pageSize: 20,
        total: 0
      }
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created: function() {
    this.obj = this.objrow;
    //查询价格
    this.getDate();
    //获取合计数
    this.getSum();
    this.commitBtn = this.permissions["SHIP_GRPT_JE_COMMIT"];
  },
  methods: {
    getDate() {
      this.loading = true;
      shipCargoList(
        Object.assign(
          {
            current: this.tablePage.currentPage,
            size: this.tablePage.pageSize
          },
          { shipNo: this.obj.shipNo }
        )
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
    getSum() {
      shipCargoSum(this.obj.shipNo)
        .then(response => {
          this.sumobj = response.data.data;
        })
        .catch(() => {});
    },
    getDateFromNext() {
      this.$emit("selectList");
      this.getDate();
    },
    closeWindows: function() {
      this.$emit("closeWindows");
    },
    ///////////////////////////////表格操作区域方法////////////////////
    insertEvent() {
      this.$refs.xTable
        .insert()
        .then(({ row }) => this.$refs.xTable.setActiveCell(row, "feeNam"));
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    bj() {
      this.objShip = this.objrow;
      if (this.tableData.length == 0) {
        this.objShip.bj = "正常";
      } else if (this.tableData.length > 0) {
        this.objShip.bj = "补交";
      }

      this.dialogFormVisible = true;
    },
    dictItemVisible: function() {
      this.dialogFormVisible = false;
    },
    editRow(row) {
      this.objShip = row;
      this.objShip.agentCod_01 = this.objrow.agentCod_01;
      this.dialogFormVisible = true;
    },

    formatterifendId4({ cellValue }) {
      if (cellValue == 1) {
        return "已确认";
      } else {
        return "未确认";
      }
    },
     formatterStatus({ cellValue }) {
      if (cellValue == null||cellValue == '0') {
        return "待提交";
      } else if(cellValue == '1'){
        return "已提交";
      }else if(cellValue == '2'){
        return "已审核";
      }
    },
    formatterTradeId({ cellValue }) {
      if (cellValue == 1) {
        return "内贸";
      } else {
        return "外贸";
      }
    },
    overShip() {
      let that = this;
     
      if (this.tableData != null && this.tableData.length > 0) {
        this.$confirm("是否确认上报此船舶交接单", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
            that.loading = true;
           commitShipCargoBill(that.obj.shipNo)
            .then(data => {
              that.$message.success("上报成功");
              that.getDate();
            })
            .catch(() => {
              that.loading = false;
            });
        });
      } else {
        this.$XModal.alert("没有处理船舶交接单，无法进行完结", "提示");
      }
    },
    doJjd() {
      let that = this;
      if (this.tableData != null && this.tableData.length > 0) {
        this.$confirm("已经处理过，是否要重新处理", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          that.loading = true;
          doShipCargoBill(that.obj.shipNo)
            .then(data => {
              that.$message.success("处理成功");
              that.getDate();
              that.getSum();
            })
            .catch(() => {
              that.loading = false;
            });
        });
      } else {
        that.loading = true;
        doShipCargoBill(this.obj.shipNo).then(response => {
          this.$message.success("处理成功");
          that.loading = false;
          this.getDate();
          this.getSum();
        }).catch(() => {
              this.loading = false;
        });
      }
    },
    cellStyle({ row, rowIndex, column, columnIndex }) {
      if (column.property === "status") {
        if (row.status == 1 || row.status == null) {
          return {
            color: "green"
          };
        } else if (row.status == 2) {
          return {
            color: "blue"
          };
        } else if (row.status == 3) {
          return {
            color: "red"
          };
        }
      }
    }
  }
};
</script>
<style >
.feeList .el-collapse-item__content {
  padding-bottom: 0px;
}
.feeList .vxe-toolbar {
  height: 25px;
}
</style>
