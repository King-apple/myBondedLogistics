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
  <div class="business-businesscontractship-index">
    <basic-container>
      <vxe-form :data="formObj" size="mini">
        <vxe-form-item title="船名：" field="shipNo" size="mini" span="3">
          <vxe-input
            v-model="objrowcon.shipNo"
            size="mini"
            readonly="true"
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item field="shipNam" size="mini" span="3">
          <vxe-input
            v-model="objrowcon.shipNam"
            size="mini"
            readonly="true"
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="进出口：" field="inoutId" size="mini" span="3">
          <vxe-select v-model="formObj.inoutId" clearable size="mini">
            <vxe-option value="1" label="进口"></vxe-option>
            <vxe-option value="0" label="出口"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item title="内外贸：" field="tradeId" size="mini" span="4">
          <vxe-select v-model="formObj.tradeId" clearable size="mini">
            <vxe-option value="0" label="外贸"></vxe-option>
            <vxe-option value="1" label="内贸"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item title="货名：" field="hm" size="mini" span="4">
          <EditDown
            :objs="formObj"
            :property="downTableOption.hmNam"
            :tableColumns="downTableOption.hmColum"
            :urlapi="downTableOption.hmurl"
            :returnApi="downTableOption.hmreturnApi"
            sizenow="mini"
            pageSize="100"
            height="650"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item title="货主：" field="firstNam" size="mini" span="5">
          <EditDown
            :objs="formObj"
            :property="downTableOption.shipperNam"
            :tableColumns="downTableOption.shipperColum"
            :urlapi="downTableOption.shipperurl"
            :returnApi="downTableOption.shipperreturnApi"
            sizenow="mini"
            pageSize="100"
            height="650"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item span="2" size="mini" align="center">
          <vxe-button
            status="primary"
            icon="fa fa-search"
            @click="selectTabel()"
            >查询</vxe-button
          >
        </vxe-form-item>
      </vxe-form>
      <vxe-toolbar perfect size="mini">
        <template v-slot:buttons> </template>
      </vxe-toolbar>
      <vxe-table
        border
        show-overflow
        keep-source
        highlight-current-row
        size="mini"
        show-footer
        :footer-method="footerMethod"
        :data="tableData"
        :loading="tableload"
        :round="true"
        align="center"
        height="650px"
        ref="tableMan"
        @cell-click="rowclick"
      >
        <vxe-table-column type="seq" width="60" title="序号"></vxe-table-column>
        <vxe-table-column
          field="contNo"
          title="合同号"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="contDte"
          title="合同日期"
          width="140"
        ></vxe-table-column>
        <vxe-table-column
          field="consignNam"
          title="货主"
          width="150"
        ></vxe-table-column>
        <vxe-table-column
          field="cargoNam"
          title="货名"
          width="80"
        ></vxe-table-column>
        <vxe-table-column
          field="cPkgKindNam"
          title="包装"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="format"
          title="规格"
          width="60"
        ></vxe-table-column>
        <vxe-table-column
          field="piecesNum"
          title="件数"
          width="70"
        ></vxe-table-column>
        <vxe-table-column
          field="weightWgt"
          title="重量"
          width="90"
        ></vxe-table-column>
        <vxe-table-column
          field="cargoVol"
          title="体积"
          width="90"
        ></vxe-table-column>
        <vxe-table-column
          field="portWay"
          title="集疏港方式"
          width="80"
        ></vxe-table-column>
        <vxe-table-column
          field="yardWay"
          title="进出库方式"
          width="80"
        ></vxe-table-column>
        <vxe-table-column
          field="carArrangMan"
          title="运力安排"
          width="80"
        ></vxe-table-column>
        <vxe-table-column
          field="addrNam"
          title="堆存地点"
          width="110"
        ></vxe-table-column>
        <vxe-table-column
          field="workTechnics"
          title="作业工艺"
          width="80"
        ></vxe-table-column>
        <vxe-table-column
          field="feeRat"
          title="费率"
          width="70"
        ></vxe-table-column>
        <vxe-table-column
          field="tradeId"
          title="内外贸"
          :formatter="formatterTradeId"
          width="70"
        ></vxe-table-column>
        <vxe-table-column
          field="inoutId"
          title="进出口"
          :formatter="formatterInoutId"
          width="70"
        ></vxe-table-column>
        <vxe-table-column field="port" title="来往港" width="150">
          <vxe-table-column
            field="portCod"
            title="代码"
            width="70"
          ></vxe-table-column>
          <vxe-table-column
            field="cPortNam"
            title="来往港"
            width="80"
          ></vxe-table-column>
        </vxe-table-column>
        <vxe-table-column field="unit" title="作业公司" width="200">
          <vxe-table-column
            field="unitCod"
            title="代码"
            width="60"
          ></vxe-table-column>
          <vxe-table-column
            field="unitNam"
            title="作业公司"
            width="220"
          ></vxe-table-column>
        </vxe-table-column>
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
          'Total',
        ]"
        @page-change="handlePageChange"
      ></vxe-pager>

      <vxe-modal
        v-if="dialogFormVisibleView"
        v-model="dialogFormVisibleView"
        title="合同详情"
        width="1200"
        height="900"
        resize
        storage
        fullscreen
      >
        <ViewContract
          :datanow="dateNow"
          @dictItemVisible="dictItemVisible"
        ></ViewContract>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import {
  fetchListShip,
  saveAll,
  commit,
  delObj,
  backObj,
  getSumContrat,
} from "@/api/business/cont";
import { mapGetters } from "vuex";
import ViewContract from "./viewContract";
import EditDown from "../../baseUtile/EditDown";
export default {
  components: { ViewContract, EditDown },
  name: "business-businesscontractship-index",
  props: ["objrowcon"],
  data() {
    return {
      formObj: {
        firstNam: null,
        shipNam: null,
        firstCod: null,
        hm: null,
        cargoNamCod: null,
      },
      editTitle: "合同维护",
      dialogFormVisibleView: false,
      tableData: [],
      dateNow: {},
      dataConHeadNow: {},
      dataHeadNow: {},
      sumPiecesNum: null,
      sumWeightWgt: "",
      sumCargoVol: "",
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      tableload: false,
      downTableOption: {
        //货主
        shipperNam: "firstNam",
        shipperColum: [
          { field: "SHIPPER_COD", title: "货主代码" },
          { field: "SHIPPER_DOC", title: "货主名称" },
        ],
        shipperreturnApi: [
          { field: "SHIPPER_COD", returnName: "firstCod" },
          { field: "SHIPPER_DOC", returnName: "firstNam" },
        ],
        shipperurl: "/basecode/basecod/getShipperDoc",

        //货名
        hmNam: "hm",
        hmColum: [
          { field: "CARGO_NAM_COD", title: "货名代码" },
          { field: "CARGO_NAM", title: "货名" },
        ],
        hmreturnApi: [
          { field: "CARGO_NAM", returnName: "hm" },
          { field: "CARGO_NAM_COD", returnName: "cargoNamCod" },
        ],
        hmurl: "/basecode/basecod/getCargoNamAllPage",
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
    selectTabel() {
      this.tablePage.currentPage = 1;
      this.getDate();
    },
    getDate() {
      this.tableload = true;
      this.dataConHeadNow = {};
      this.dataConHeadNow.shipNo = this.objrowcon.shipNo;
      this.SumContrat();
      fetchListShip(
        Object.assign(
          {
            current: this.tablePage.currentPage,
            size: this.tablePage.pageSize,
            shipNo: this.dataConHeadNow.shipNo,
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

    viewCont() {
      if (this.dataHeadNow != null && this.dataHeadNow.fphm != null) {
        this.dateNow = this.dataHeadNow;
        this.dialogFormVisibleView = true;
      } else {
        this.$message.error("请选择一个进行查看");
      }
    },

    SumContrat() {
      getSumContrat(Object.assign(
        {shipNo: this.objrowcon.shipNo,},
        this.formObj
        )
      )
        .then((response) => {
          this.sumPiecesNum = response.data.data.SUMPIECESNUM;
          this.sumWeightWgt = response.data.data.SUMWEIGHTWGT;
          this.sumCargoVol = response.data.data.SUMCARGOVOL;
        })
        .catch(() => {});
    },

    footerMethod({ columns, data }) {
      const footerData = [
        columns.map((column, _columnIndex) => {
          if (_columnIndex === 1) {
            return "合计";
          }
          if (["piecesNum"].includes(column.property)) {
            return this.sumPiecesNum;
          }

          if (["weightWgt"].includes(column.property)) {
            return this.sumWeightWgt;
          }
          if (["cargoVol"].includes(column.property)) {
            return this.sumCargoVol;
          }
          return null;
        }),
      ];
      return footerData;
    },

    rowclick({ row }) {
      this.dataHeadNow = row;
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    editCont() {
      this.dialogFormVisibleplant = false;
    },
    dictItemVisible() {
      this.dialogFormVisibleplant = false;
      this.dialogFormVisibleedit = false;
      this.dialogFormVisibleView = false;
      this.dialogFormVisibleAdd = false;
    },
    formatterZt({ row }) {
      if (row.zffs == 1) {
        if (row.zt == 1) {
          return "待提交";
        } else if (row.zt == 2) {
          return "待支付";
        } else if (row.zt == 3) {
          return row.ztName;
        } else if (row.zt == 99) {
          return "作废";
        }
      } else if (row.zffs == 0 && row.fcId == "01") {
        if (row.zt == 1) {
          return "待提交";
        } else if (row.zt == 2) {
          return "待签章";
        } else if (row.zt == 3) {
          return "合同已签章";
        } else if (row.zt == 99) {
          return "作废";
        }
      } else if (row.zffs == 0 && (row.fcId == "00" || row.fcId == null)) {
        if (row.zt == 3) {
          return "已审核";
        } else if (row.zt == 99) {
          return "作废";
        }
      }
    },
    formatterZffs({ cellValue }) {
      if (cellValue == 1) {
        return "线上";
      } else {
        return "线下";
      }
    },
    formatterJs({ cellValue }) {
      if (cellValue == 1) {
        return "月结";
      } else if (cellValue == 2) {
        return "次结";
      }
    },
    formatterTradeId({ cellValue }) {
      if (cellValue == 1) {
        return "内贸";
      } else if (cellValue == 0) {
        return "外贸";
      }
    },
    formatterInoutId({ cellValue }) {
      if (cellValue == 1) {
        return "进口";
      } else if (cellValue == 0) {
        return "出口";
      }
    },
    formatterfcId({ cellValue }) {
      if (cellValue == "01") {
        return "线上";
      } else {
        return "线下";
      }
    },
  },
};
</script>
<style lang="scss" scoped>
.business-businesscontractship-index {
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