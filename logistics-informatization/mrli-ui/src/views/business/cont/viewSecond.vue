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
  <div class="business-cont-viewSecond">
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
          <vxe-button type="text" class="fa fa-plus" @click="commitCont()"
            >确认</vxe-button
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
    </basic-container>
  </div>
</template>

<script>
import { fetchListSecond, copyOtherCont } from "@/api/business/cont";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import Edit from "./edit";
import Add from "./add";
import Plant from "./plant";
import ViewContract from "./viewContract";
import EditDown from "../../baseUtile/EditDown";
export default {
  components: { EditDown },
  name: "business-cont-viewSecond",
  data() {
    return {
      formObj: {
        firstNam: null,
        shipNam: null,
        firstCod: null,
        inoutId: null,
        tradeId: null
      },
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
      fetchListSecond(
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
    rowclick({ row }) {
      this.dataHeadNow = row;
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    commitCont() {
      if (this.dataHeadNow == null && this.dataHeadNow.fphm == null) {
        this.$message.error("请选择一个进行确认");
        return;
      }
      let loadingInstance = Loading.service({
        fullscreen: true,
        text: "正在保存………"
      });
      copyOtherCont(this.dataHeadNow.fphm)
        .then(data => {
          this.$message.success("保存成功");
          loadingInstance.close();
          this.$emit("selectListplan");
          this.$emit("dictItemVisible");
        })
        .catch(() => {
          loadingInstance.close();
        });
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
        } else if (row.zffs == 0 && row.fcId == "00") {
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
.business-cont-viewSecond {
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