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
  <div class="trainplan">
    <basic-container>
      <vxe-form
        :data="formObj"
        size="mini"
        title-width="80"
        title-align="right"
      >
        <el-row>
          <!-- <vxe-form-item
            title="计划月份："
            size="mini"
            field="datePlan"
            span="6"
          >
            <vxe-input
              v-model="formObj.datePlan"
              type="month"
              format="YYYY-MM"
              data-format="YYYY-MM"
              placeholder="请选择日期"
              @change="getDate"
              size="mini"
              span="6"
              clearable
            ></vxe-input>
          </vxe-form-item> -->
          <vxe-form-item title="计划月份：" field="datePlan" size="mini" span="6">
         <vxe-input
              v-model="formObj.datePlan"
              type="month"
              format="YYYY-MM"
              data-format="YYYY-MM"
              placeholder="请选择日期"
              @change="getDate"
              size="mini"
              span="6"
              clearable
            ></vxe-input>
      </vxe-form-item>
          <vxe-form-item
            title="代理货主："
            field="consignNam"
            size="mini"
            span="6"
          >
            <EditDown
              :objs="formObj"
              :property="downTableOption.consignNam"
              :tableColumns="downTableOption.consignNamColum"
              :urlapi="downTableOption.consignNamurl"
              :returnApi="downTableOption.consignNamreturnApi"
              sizenow="mini"
            ></EditDown>
          </vxe-form-item>

          <vxe-form-item
            title="作业公司："
            field="unitNam"
            size="mini"
            span="6"
          >
            <EditDown
              :objs="formObj"
              :property="downTableOption.unitNam"
              :tableColumns="downTableOption.unitNamColum"
              :urlapi="downTableOption.unitNamurl"
              :returnApi="downTableOption.unitNamreturnApi"
              sizenow="mini"
            ></EditDown>
          </vxe-form-item>
          <vxe-form-item
            title="计划内/外："
            field="planId"
            size="mini"
            span="6"
          >
            <vxe-select v-model="formObj.planId" clearable size="mini">
              <vxe-option value="0" label="计划内"></vxe-option>
              <vxe-option value="1" label="计划外"></vxe-option>
            </vxe-select>
          </vxe-form-item>
          <vxe-form-item
            title="港口货主："
            field="shipperDoc"
            size="mini"
            span="6"
          >
            <EditDown
              :objs="formObj"
              :property="downTableOption.shipperDoc"
              :tableColumns="downTableOption.shipperDocColum"
              :urlapi="downTableOption.shipperDocurl"
              :returnApi="downTableOption.shipperDocreturnApi"
              sizenow="mini"
            ></EditDown>
          </vxe-form-item>
          <vxe-form-item title="货名：" field="cargoNam" size="mini" span="6">
            <EditDown
              :objs="formObj"
              :property="downTableOption.cargoNam"
              :tableColumns="downTableOption.cargoNamColum"
              :urlapi="downTableOption.cargoNamurl"
              :returnApi="downTableOption.cargoNamreturnApi"
              sizenow="mini"
            ></EditDown>
          </vxe-form-item>
        </el-row>
        <el-row>
          <vxe-form-item
            title="包装："
            field="cPkgKindNam"
            size="mini"
            span="6"
          >
            <EditDown
              :objs="formObj"
              :property="downTableOption.cPkgKindNam"
              :tableColumns="downTableOption.cPkgKindNamColum"
              :urlapi="downTableOption.cPkgKindNamurl"
              :returnApi="downTableOption.cPkgKindNamreturnApi"
              sizenow="mini"
            ></EditDown>
          </vxe-form-item>
          <vxe-form-item
            title="规格："
            field="format"
            size="mini"
            span="6"
          >
            <EditDown
              :objs="formObj"
              :property="downTableOption.format"
              :tableColumns="downTableOption.formatColum"
              :urlapi="downTableOption.formaturl"
              :returnApi="downTableOption.formatreturnApi"
              sizenow="mini"
            ></EditDown>
          </vxe-form-item>
          <vxe-form-item span="4" size="mini" align="center">
            <vxe-button status="primary" icon="fa fa-search" @click="getDate()"
              >查询</vxe-button
            >
          </vxe-form-item>
        </el-row>
      </vxe-form>
      <el-row>
        <el-col :span="24">
          <el-divider content-position="left">局计划信息</el-divider>
        </el-col>
        <vxe-toolbar perfect size="mini">
          <template v-slot:buttons>
            <vxe-button type="text" class="fa fa-sliders" @click="add('add')"
              >新增</vxe-button
            >
            <vxe-button type="text" class="fa fa-trash" @click="add('edit')"
              >修改</vxe-button
            >
            <vxe-button type="text" class="fa fa-male" @click="toDisable()"
              >删除</vxe-button
            >
          </template>
        </vxe-toolbar>

        <el-col :span="24">
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
            height="300px"
            ref="table"
            show-footer
            :footer-method="footerMethod"
            @cell-click="rowclick"
            @cell-dblclick="cellDBLClickEvent"
          >
            <vxe-table-column
              type="seq"
              width="60"
              title="序号"
              fixed="left"
            ></vxe-table-column>
            <vxe-table-column
              field="planStatCod"
              title="审核"
              width="50"
              fixed="left"
            ></vxe-table-column>
            <vxe-table-column
              field="planStat"
              title="集团审核"
              width="80"
              fixed="left"
            ></vxe-table-column>
            <vxe-table-column
              field="planId"
              title="计划内/外"
              width="80"
              :formatter="formatternw"
            ></vxe-table-column>
            <vxe-table-column
              field="unitNam"
              title="作业公司"
              width="230"
            ></vxe-table-column>
            <vxe-table-column
              field="traceNam"
              title="建议股位"
              width="120"
            ></vxe-table-column>
            <vxe-table-column
              field="cargoNam"
              title="货名"
              width="80"
            ></vxe-table-column>
            <vxe-table-column
              field="cpkgKindNam"
              title="包装"
              width="80"
            ></vxe-table-column>
            <vxe-table-column
              field="format"
              title="规格"
              width="80"
            ></vxe-table-column>
            <vxe-table-column
              field="fromStatNam"
              title="发站"
              width="150"
            ></vxe-table-column>
            <vxe-table-column
              field="consignNam"
              title="代理货主"
              width="250"
            ></vxe-table-column>
            <vxe-table-column
              field="shipperDoc"
              title="港口货主"
              width="250"
            ></vxe-table-column>
            <vxe-table-column
              field="setownerNam"
              title="发货人"
              width="250"
            ></vxe-table-column>
            <vxe-table-column
              field="getownerNam"
              title="收货人"
              width="250"
            ></vxe-table-column>
            <vxe-table-column
              field="weightWgt"
              title="计划到港重量"
              width="120"
            ></vxe-table-column>
            <vxe-table-column
              field="trainNum"
              title="总车数"
              width="120"
            ></vxe-table-column>
            <vxe-table-column
              field="phone"
              title="联系电话"
              width="120"
            ></vxe-table-column>
            <vxe-table-column
              field="markTxt"
              title="备注"
              width="150"
            ></vxe-table-column>
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
        </el-col>
        <el-col :span="24">
          <el-divider content-position="left">公司计划信息</el-divider>
        </el-col>
        <el-col :span="24">
          <vxe-table
            border
            show-overflow
            keep-source
            highlight-current-row
            size="mini"
            :data="tableMxData"
            :loading="tableMxload"
            :round="true"
            align="center"
            height="200px"
            ref="tableMx"
          >
            <vxe-table-column
              type="seq"
              width="60"
              title="序号"
            ></vxe-table-column>
            <vxe-table-column
              field="trackCod"
              title="股位代码"
              width="150"
            ></vxe-table-column>
            <vxe-table-column
              field="trackNam"
              title="股位"
              width="80"
            ></vxe-table-column>
            <vxe-table-column
              field="subunitCod"
              title="管货库区代码"
              width="150"
            ></vxe-table-column>
            <vxe-table-column
              field="subunitNam"
              title="管货库区"
              width="150"
            ></vxe-table-column>
            <vxe-table-column
              field="yardNo"
              title="库场代码"
              width="150"
            ></vxe-table-column>
            <vxe-table-column
              field="yardNam"
              title="库场名称"
              width="80"
            ></vxe-table-column>
            <vxe-table-column
              field="trainNum"
              title="车数"
              width="80"
            ></vxe-table-column>
            <vxe-table-column
              field="weightWgt"
              title="重量"
              width="150"
            ></vxe-table-column>
            <vxe-table-column
              field="markTxt"
              title="备注"
              width="200"
            ></vxe-table-column>
          </vxe-table>
        </el-col>
      </el-row>
      <vxe-modal
        v-if="dialogFormVisibleAdd"
        v-model="dialogFormVisibleAdd"
        title="铁路集港计划"
        width="600"
        height="600"
        resize
        storage
        @close="closeEdit()"
      >
        <Add
          :datanow="datanow"
          @dictItemVisible="dictItemVisible"
          @selectListplan="getDate"
        ></Add>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleedit"
        v-model="dialogFormVisibleedit"
        title="铁路集港计划"
        width="600"
        height="600"
        resize
        storage
        @close="closeEdit()"
      >
        <Edit
          :datanow="datanow"
          @dictItemVisible="dictItemVisible"
          @selectListplan="getDate"
        ></Edit>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import {
  fetchList,
  fetchSubList,
  deleteObj,
} from "@/api/caccentre/trainplan";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import XEUtils from "xe-utils";
import EditDown from "../../baseUtile/EditDown";
import moment from "moment";
import Edit from "./edit";
import Add from "./add";
// import Add from "./add";
export default {
  components: { Add, Edit, EditDown },
  name: "tkindcargo",
  data() {
    return {
      formObj: {
        consignCod: null,
        consignNam: null,
        unitCod: null,
        unitNam: null,
        shipperCod: null,
        shipperDoc: null,
        cargoNamCod: null,
        cargoNam: null,
        pkgKindCod: null,
        cpkgKindNam: null,
        format: null,
        unloadId:'-',
        datePlan: moment(new Date()).format("YYYY-MM") ,
      },
      editTitle: "铁路集港计划",
      dialogFormVisibleedit: false,
      dialogFormVisibleAdd: false,
      tableData: [],
      tableMxData: [],
      datanow: {},
      dataHeadNow: {},
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      tableload: false,
      tableMxload: false,
      downTableOption: {
        //代理货主
        consignNam: "consignNam",
        consignNamColum: [
          { field: "SHIPPER_COD", title: "代理货主代码" },
          { field: "SHIPPER_DOC", title: "港口货主" },
        ],
        consignNamreturnApi: [
          { field: "SHIPPER_COD", returnName: "consignCod" },
          { field: "SHIPPER_DOC", returnName: "consignNam" },
        ],
        consignNamurl: "/basecode/basecod/getShipperDoc",
        //作业公司
        unitNam: "unitNam",
        unitNamColum: [
          { field: "UNIT_COD", title: "单位代码" },
          { field: "UNIT_NAM", title: "单位" },
        ],
        unitNamreturnApi: [
          { field: "UNIT_COD", returnName: "unitCod" },
          { field: "UNIT_NAM", returnName: "unitNam" },
        ],
        unitNamurl: "/basecode/basecod/getUnitNam?id=0",
        //港口货主
        shipperDoc: "shipperDoc",
        shipperDocColum: [
          { field: "SHIPPER_COD", title: "港口货主代码" },
          { field: "SHIPPER_DOC", title: "港口货主" },
        ],
        shipperDocreturnApi: [
          { field: "SHIPPER_COD", returnName: "shipperCod" },
          { field: "SHIPPER_DOC", returnName: "shipperDoc" },
        ],
        shipperDocurl: "/basecode/basecod/getShipperDoc",
        //货名
        cargoNam: "cargoNam",
        cargoNamColum: [
          { field: "CARGO_NAM_COD", title: "货名代码" },
          { field: "CARGO_NAM", title: "货名" },
        ],
        cargoNamreturnApi: [
          { field: "CARGO_NAM_COD", returnName: "cargoNamCod" },
          { field: "CARGO_NAM", returnName: "cargoNam" },
        ],
        cargoNamurl: "/basecode/basecod/getCargoNamCod",
        //包装
        cPkgKindNam: "cPkgKindNam",
        cPkgKindNamColum: [
          { field: "PKG_KIND_COD", title: "代码" },
          { field: "C_PKG_KIND_NAM", title: "包装" },
        ],
        cPkgKindNamreturnApi: [
          { field: "PKG_KIND_COD", returnName: "pkgKindCod" },
          { field: "C_PKG_KIND_NAM", returnName: "cpkgKindNam" },
        ],
        cPkgKindNamurl: "/basecode/basecod/getPkgKind",
        //规格
        format: "format",
        formatColum: [{ field: "FORMAT", title: "规格" }],
        formatreturnApi: [{ field: "FORMAT", returnName: "format" }],
        formaturl: "/basecode/basecod/getFormat",
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
      this.dataHeadNow = {};
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
          this.tableMxData = null;
          this.tableload = false;
          this.dataHeadNow=this.tableData[0];
          this.$refs.table.setCurrentRow(this.tableData[0]);
           console.log(response.data.data.records[0].planNo);
          this.getSubList(response.data.data.records[0].planNo);
          
        })
        .catch(() => {
          this.tableload = false;
        });
    },
    getSubList(planNo) {
      this.tableMxload = true;
      // console.log(this.dataHeadNow.pkid);
      fetchSubList(planNo)
        .then((response) => {
          this.tableMxData = response.data.data;
          this.tableMxload = false;
        })
        .catch(() => {
          this.tableMxload = false;
        });
    },
    cellDBLClickEvent({ row }) {
      this.dataHeadNow = row;
      this.dialogFormVisibleView = true;
    },
    add(type) {
      if (type === "edit") {
        console.log(this.dataHeadNow);
        if (this.dataHeadNow != null && this.dataHeadNow.planNo != null) {
          // if (this.dataHeadNow.zt != 1) {
          //   this.$message.error("当前状态无法修改合同");
          //   return;
          // }
          this.editTitle = "铁路集港计划修改";
          this.datanow = this.dataHeadNow;
          this.dialogFormVisibleedit = true;
        } else {
          this.$message.error("请选择一个数据进行修改");
        }
      } else if (type === "add") {
        this.datanow = {};
        this.dialogFormVisibleAdd = true;
      }
    },
    toDisable() {
      let that = this;
      if (this.dataHeadNow != null && this.dataHeadNow.planNo != null) {
        // if (this.dataHeadNow.status == "1") {
          this.$confirm("是否确认删除", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
            .then(function () {
              return deleteObj(that.dataHeadNow);
            })
            .then((response) => {
              this.$message.success("作废成功");
              this.getDate();
            });
        // } else {
        //   this.$message.error("当前状态不能作废");
        // }

        // deleteObj(this.dataHeadNow)
        //   .then((response) => {
        //     this.$message.success("作废成功");
        //     this.getDate();
        //   })
        //   .catch(() => {});
      } else {
        this.$message.error("请选择一个进行作废");
      }
    },
    footerMethod({ columns, data }) {
      return [
        columns.map((column, columnIndex) => {
          if (columnIndex === 0) {
            return "合计";
          }
          if (["weightWgt","trainNum"].includes(column.property)) {
            return XEUtils.sum(data, column.property);
          }
          return null;
        }),
      ];
    },
    formatterType({ cellValue }) {
      if (cellValue == null) {
        return "/";
      } else {
        return cellValue + "%";
      }
    },
    formatternw({ cellValue }) {
      if (cellValue == "1") {
        return "计划外";
      } else if (cellValue == "0") {
        return "计划内";
      }
    },
    formatterzx({ cellValue }) {
      if (cellValue == "-") {
        return "卸";
      } else if (cellValue == "+") {
        return "装";
      }
    },
    formattergb({ cellValue }) {
      if (cellValue == "0") {
        return "否";
      } else if (cellValue == "1") {
        return "是";
      }
    },
    formatterzt({ cellValue }) {
      if (cellValue == "0") {
        return "作废";
      } else if (cellValue == "1") {
        return "有效";
      } else if (cellValue == "3") {
        return "完工";
      }
    },
    rowclick({ row }) {
      this.dataHeadNow = row;
      this.getSubList(this.dataHeadNow.planNo);
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    editCont() {
      this.dialogFormVisibleplant = false;
    },
    closeEdit() {
      this.getDate();
      this.dialogFormVisibleedit = false;
      this.dialogFormVisibleAdd = false;
    },
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
      this.dialogFormVisibleAdd = false;
    },
  },
};
</script>
<style lang="scss" scoped>
.zyworkresourcesman {
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