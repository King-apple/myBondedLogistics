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
  <div class="zyworkresourcesman">
    <basic-container>
      <vxe-form :data="formObj" size="mini">
        <vxe-form-item title="开始：" field="timeBegin" size="mini" span="4">
          <vxe-input
            v-model="formObj.timeBegin"
            placeholder="开始时间"
            type="date"
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="至  " field="timeEnd" size="mini" span="4">
          <vxe-input
            v-model="formObj.timeEnd"
            placeholder="结束时间"
            type="date"
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="合同号：" field="contNo" size="mini" span="4">
          <vxe-input v-model="formObj.contNo" clearable size="mini"></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="船号：" field="shipNo" size="mini" span="4">
          <vxe-input v-model="formObj.shipNo" clearable size="mini"></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="船名：" field="shipNam" size="mini" span="4">
          <EditDown
            :objs="formObj"
            :property="downTableOption.shipDataname"
            :tableColumns="downTableOption.shipDataColum"
            :urlapi="downTableOption.shipDataurl"
            :returnApi="downTableOption.shipDatareturnApi"
            sizenow="mini"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item title="甲方" field="firstNam" size="mini" span="4">
          <EditDown
            :objs="formObj"
            :property="downTableOption.shipperNam"
            :tableColumns="downTableOption.shipperColum"
            :urlapi="downTableOption.shipperurl"
            :returnApi="downTableOption.shipperreturnApi"
            sizenow="mini"
            pageLayOutType="mini"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item title="内外贸：" field="tradeId" size="mini" span="6">
          <vxe-select v-model="formObj.tradeId" clearable size="mini">
            <vxe-option value="1" label="内贸"></vxe-option>
            <vxe-option value="0" label="外贸"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item title="进出口：" field="inoutId" size="mini" span="5">
          <vxe-select v-model="formObj.inoutId" clearable size="mini">
            <vxe-option value="1" label="进口"></vxe-option>
            <vxe-option value="0" label="出口"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="center">
          <vxe-button
            status="primary"
            icon="fa fa-search"
            @click="selectTabel()"
            >查询</vxe-button
          >
          <vxe-button type="reset" icon="fa fa-repeat" @click="resetForm"
            >重置</vxe-button
          >
        </vxe-form-item>
      </vxe-form>
      <vxe-toolbar perfect size="mini">
        <template v-slot:buttons>
          <vxe-button type="text" class="fa fa-plus" @click="add('addNotPlant')"
            >新增(<font color="red">没有计划通知单</font>)</vxe-button
          >
          <vxe-button type="text" class="fa fa-level-down" @click="add('add')"
            >接收(<font color="red">有计划通知单</font>)</vxe-button
          >
          <vxe-button type="text" class="fa fa-refresh" @click="refresh()"
            >重新接收(<font color="red">有计划通知单</font>)</vxe-button
          >
          <vxe-button type="text" class="fa fa-refresh" @click="contSecond()"
            >合同接收</vxe-button
          >
          <vxe-button
            type="text"
            class="fa fa-pencil-square-o"
            @click="add('edit')"
            >修改</vxe-button
          >
          <vxe-button type="text" class="fa fa-eye" @click="viewCont()"
            >详情查看</vxe-button
          >
          <vxe-button type="text" class="fa fa-plus" @click="openChange()"
            >痕迹查询</vxe-button
          >
          <vxe-button type="text" class="fa fa-arrow-up" @click="commitCont()"
            >提交</vxe-button
          >
          <vxe-button type="text" class="fa fa-print" @click="printOne()"
            >打印</vxe-button
          >
          <vxe-button type="text" class="fa fa-trash" @click="deleteCont()"
            >作废</vxe-button
          >
        </template>
      </vxe-toolbar>
      <vxe-table
        border
        show-overflow
        keep-source
        highlight-current-row
        size="mini"
        :data="tableData"
        :loading="tableload"
        :cell-style="cellStyle"
        :round="true"
        align="center"
        height="650px"
        ref="tableMan"
        @cell-click="rowclick"
        @cell-dblclick="cellDBLClickEvent"
      >
        <vxe-table-column type="seq" width="60" title="序号"></vxe-table-column>
        <vxe-table-column
          field="contNo"
          title="合同号"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="zt"
          title="状态"
          :formatter="formatterZt"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="zffs"
          title="支付方式"
          :formatter="formatterZffs"
          width="80"
        ></vxe-table-column>
        <vxe-table-column
          field="fcId"
          title="签章方式"
          :formatter="formatterfcId"
          width="80"
        ></vxe-table-column>
        <vxe-table-column
          field="prepayId"
          title="结算"
          :formatter="formatterJs"
          width="60"
        ></vxe-table-column>
        <vxe-table-column
          field="advanceNum"
          title="预付金额"
          width="90"
        ></vxe-table-column>
        <vxe-table-column
          field="weightAll"
          title="总重量"
          width="90"
        ></vxe-table-column>
        <vxe-table-column
          field="tradeId"
          title="内外贸"
          :formatter="formatterTradeId"
          width="60"
        ></vxe-table-column>
        <vxe-table-column
          field="inoutId"
          title="进出口"
          :formatter="formatterInoutId"
          width="60"
        ></vxe-table-column>
        <vxe-table-column
          field="firstNam"
          title="甲方"
          width="200"
        ></vxe-table-column>
        <vxe-table-column
          field="secondNam"
          title="乙方"
          width="200"
        ></vxe-table-column>
        <vxe-table-column
          field="plantNo"
          title="计划通知单"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="shipNo"
          title="船号"
          width="80"
        ></vxe-table-column>
        <vxe-table-column
          field="shipNam"
          title="船名"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="firstMan"
          title="甲方经办人"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="secondMan"
          title="乙方经办人"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="recordTim"
          title="录入时间"
          :formatter="['formatDate', 'yyyy-MM-dd HH:mm:ss']"
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
          'Total'
        ]"
        @page-change="handlePageChange"
      ></vxe-pager>
      <vxe-modal
        v-if="dialogFormVisibleplant"
        v-model="dialogFormVisibleplant"
        title="计划通知单选择"
        width="500"
        height="300"
      >
        <Plant
          @dictItemVisible="dictItemVisible"
          @editCont="editCont"
          @selectList="getDate"
        ></Plant>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleedit"
        v-model="dialogFormVisibleedit"
        :title="editTitle"
        width="1200"
        height="900"
        resize
        storage
        fullscreen
      >
        <Edit
          :datanow="dateNow"
          @dictItemVisible="dictItemVisible"
          @selectListplan="getDate"
        ></Edit>
      </vxe-modal>
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
          @selectListplan="getDate"
        ></ViewContract>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleAdd"
        v-model="dialogFormVisibleAdd"
        title="合同新增"
        width="1200"
        height="900"
        resize
        storage
        fullscreen
      >
        <Add
          :datanow="dateNow"
          @dictItemVisible="dictItemVisible"
          @selectListplan="getDate"
        ></Add>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleSecond"
        v-model="dialogFormVisibleSecond"
        title="合同接收"
        width="1250"
        height="910"
        resize
        storage
      >
        <ViewSecond
          @dictItemVisible="dictItemVisible"
          @selectListplan="getDate"
        ></ViewSecond>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleChange"
        v-model="dialogFormVisibleChange"
        title="痕迹查询"
        width="800"
        height="900"
        resize
        storage
      >
        <ContChangeList
          :contNo="dataHeadNow.contNo"
          :unitCod="dataHeadNow.unitCod"
          @dictItemVisible="dictItemVisible"
        ></ContChangeList>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import {
  fetchList,
  saveAll,
  commit,
  delObj,
  backObj
} from "@/api/business/cont";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import Edit from "./edit";
import Add from "./add";
import Plant from "./plant";
import ViewContract from "./viewContract";
import EditDown from "../../baseUtile/EditDown";
import ViewSecond from "./viewSecond";
import ContChangeList from "../jg/contChangeList";
export default {
  components: {
    Edit,
    Plant,
    ViewContract,
    EditDown,
    Add,
    ViewSecond,
    ContChangeList
  },
  name: "zyworkresourcesman",
  data() {
    return {
      formObj: {
        firstNam: null,
        shipNam: null,
        firstCod: null,
        inoutId: null,
        tradeId: null
      },
      editTitle: "合同维护",
      dialogFormVisibleedit: false,
      dialogFormVisibleplant: false,
      dialogFormVisibleView: false,
      dialogFormVisibleAdd: false,
      dialogFormVisibleSecond: false,
      dialogFormVisibleChange: false,
      tableData: [],
      dateNow: {},
      dataHeadNow: {},

      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      tableload: false,
      downTableOption: {
        //货主
        shipperNam: "firstNam",
        shipperColum: [
          { field: "SHIPPER_COD", title: "货主代码" },
          { field: "SHIPPER_DOC", title: "货主名称" }
        ],
        shipperreturnApi: [
          { field: "SHIPPER_COD", returnName: "firstCod" },
          { field: "SHIPPER_DOC", returnName: "firstNam" }
        ],
        shipperurl: "/basecode/basecod/getShipperDoc",
        shipDataColum: [
          { field: "CALL_NO", title: "呼号" },
          { field: "C_SHIP_NAM", title: "船名" },
          { field: "E_SHIP_NAM", title: "英文船名" }
        ],
        shipDataurl: "/ship/baseCod/shipdata/page",
        shipDataname: "shipNam",
        shipDatareturnApi: [{ field: "C_SHIP_NAM", returnName: "shipNam" }]
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
    selectTabel() {
      this.tablePage.currentPage = 1;
      this.getDate();
    },
    getDate() {
      this.tableload = true;
      this.dataHeadNow = {};
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
    cellDBLClickEvent({ row }) {
      this.dateNow = row;
      this.dialogFormVisibleView = true;
    },
    viewCont() {
      if (this.dataHeadNow != null && this.dataHeadNow.fphm != null) {
        this.dateNow = this.dataHeadNow;
        this.dialogFormVisibleView = true;
      } else {
        this.$message.error("请选择一个进行查看");
      }
    },
    add(type) {
      if (type === "edit") {
        if (this.dataHeadNow != null && this.dataHeadNow.fphm != null) {
          if (this.dataHeadNow.zt != 1) {
            this.$message.error("当前状态无法修改合同");
            return;
          }
          this.editTitle = this.dataHeadNow.contNo + "合同修改";
          this.dateNow = this.dataHeadNow;
          this.dialogFormVisibleedit = true;
        } else {
          this.$message.error("请选择一个数据进行修改");
        }
      } else if (type === "add") {
        this.dateNow = {};
        this.dialogFormVisibleplant = true;
      } else if (type === "addNotPlant") {
        this.dateNow = {};
        this.dialogFormVisibleAdd = true;
      }
    },
    refresh() {
      if (this.dataHeadNow != null && this.dataHeadNow.fphm != null) {
        if (this.dataHeadNow.zt != 1) {
          this.$message.error("当前状态无法重新接收，请收回合同后，进行操作");
          return;
        }
        this.dataHeadNow = Object.assign(
          {
            cxjsplant: "1"
          },
          this.dataHeadNow
        );
        this.editTitle = this.dataHeadNow.contNo + "合同重新接收";
        this.dateNow = this.dataHeadNow;
        this.dialogFormVisibleedit = true;
      } else {
        this.$message.error("请选择一个进行重新接收");
      }
    },
    commitCont() {
      if (this.dataHeadNow != null && this.dataHeadNow.fphm != null) {
        commit(this.dataHeadNow.fphm)
          .then(response => {
            this.$message.success("上报成功");
            this.getDate();
          })
          .catch(() => {});
      } else {
        this.$message.error("请选择一个进行提交");
      }
    },
    deleteCont() {
      if (this.dataHeadNow != null && this.dataHeadNow.fphm != null) {
        delObj(this.dataHeadNow.fphm)
          .then(response => {
            this.$message.success("作废成功");
            this.getDate();
          })
          .catch(() => {});
      } else {
        this.$message.error("请选择一个进行作废");
      }
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
    contSecond() {
      this.dialogFormVisibleSecond = true;
    },
    printOne() {
      if (this.dataHeadNow == null || this.dataHeadNow.fphm == null) {
        this.$message.error("请选择一个进行打印");
        return;
      }
      if (this.dataHeadNow.zt == "99") {
        top.Dialog.alert("合同已经作废，无法打印", function() {
          grid.loadData();
        });
        return;
      }
      if (this.dataHeadNow.zt != "3") {
        top.Dialog.alert("没有审核或提交，无法打印。", function() {
          grid.loadData();
        });
        return;
      }
      window.open(
        "http://10.168.111.173:9081/Container/reportJsp/parameterReport.jsp?report=zbxt/CONT.raq&FPHM=" +
          this.dataHeadNow.fphm +
          "",
        "_blank"
      );
    },
    dictItemVisible() {
      this.dialogFormVisibleplant = false;
      this.dialogFormVisibleedit = false;
      this.dialogFormVisibleView = false;
      this.dialogFormVisibleAdd = false;
      this.dialogFormVisibleSecond = false;
      this.dialogFormVisibleChange = false;
    },
    openChange() {
      if (this.dataHeadNow == null || this.dataHeadNow.fphm == null) {
        this.$message.error("请选择一个进行查询");
        return;
      }
      this.dialogFormVisibleChange = true;
    },
    resetForm() {},
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
        if (row.zt == 1) {
          return "待提交";
        } else if (row.zt == 2) {
          return "待审核";
        } else if (row.zt == 3) {
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
    cellStyle({ row, rowIndex, column, columnIndex }) {
      if (column.property === "zt") {
        if (row.zffs == 1) {
          if (row.zt == 1) {
            return {
              color: "green"
            };
          } else if (row.zt == 2) {
            return {
              color: "blue"
            };
          } else if (row.zt == 3) {
            return {
              color: "red"
            };
          }
        } else if (row.zffs == 0 && row.fcId == "01") {
          if (row.zt == 1) {
            return {
              color: "green"
            };
          } else if (row.zt == 2) {
            return {
              color: "blue"
            };
          } else if (row.zt == 3) {
            return {
              color: "red"
            };
          }
        } else if (row.zffs == 0 && (row.fcId == "00" || row.fcId == null)) {
          if (row.zt == 1) {
            return {
              color: "green"
            };
          }
          if (row.zt == 2) {
            return {
              color: "blue"
            };
          }
          if (row.zt == 3) {
            return {
              color: "red"
            };
          }
        }
      } else if (column.property === "zffs") {
        if (row.zffs == 1) {
          return {
            color: "red"
          };
        } else {
          return {
            color: "blue"
          };
        }
      } else if (column.property === "prepayId") {
        if (row.prepayId == 1) {
          return {
            color: "red"
          };
        } else {
          return {
            color: "blue"
          };
        }
      } else if (column.property === "advanceNum") {
        return {
          color: "red"
        };
      } else if (column.property === "contNo") {
        return {
          color: "red"
        };
      } else if (column.property === "fcId") {
        if (row.fcId == "01") {
          return {
            color: "red"
          };
        } else if (row.fcId == "00") {
          return {
            color: "blue"
          };
        }
      }
    }
  }
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