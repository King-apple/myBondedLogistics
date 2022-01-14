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
  <div class="feemain">
    <vxe-form :data="obj">
      <vxe-form-item title="船号" field="shipNo" size="mini" span="3">
        <vxe-input v-model="obj.shipNo" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="船名" field="shipNam" size="mini" span="3">
        <vxe-input v-model="obj.shipNam" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="离港时间" field="levPortTim" size="mini" span="5">
        <vxe-input v-model="obj.levPortTim" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="预付金额" field="amtAll" size="mini" span="3">
        <vxe-input v-model="obj.amtAll" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="实缴金额" field="amtSjAll" size="mini" span="3">
        <vxe-input v-model="obj.amtSjAll" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="剩余金额" field="amtC" size="mini" span="3">
        <vxe-input v-model="obj.amtC" disabled></vxe-input>
      </vxe-form-item>
    </vxe-form>
    <vxe-toolbar perfect size="mini">
      <template v-slot:buttons>
        <vxe-button v-if="ifCommitButton&&commitBtn" type="text" class="fa fa-arrow-up" @click="commitFee()">上报</vxe-button>
        <vxe-button v-if="ifShButton&&shBtn" type="text" class="fa fa-pencil" @click="sh()">审核</vxe-button>
      </template>
    </vxe-toolbar>
    <vxe-table border show-overflow size="mini" :data="tableData" :cell-style="cellStyle">
      <vxe-table-column type="seq" width="40" fixed="left"></vxe-table-column>
      <vxe-table-column field="unitNam" width="250" title="公司"></vxe-table-column>
      <vxe-table-column field="feeNameCod" title="费用代码"></vxe-table-column>
      <vxe-table-column field="cfeeNam" title="费用名称"></vxe-table-column>
      <vxe-table-column field="feeMny" title="金额"></vxe-table-column>
      <vxe-table-column field="recordTim" title="时间"></vxe-table-column>
      <vxe-table-column field="status" title="状态" :formatter="formatterStatus"></vxe-table-column>
    </vxe-table>
  </div>
</template>

<script>
import {
  getAgentObj,
  commitFee,
  getfeeList,
  getpayList,
  delShipPay,
  commit,
  getCFeeBillList,
  commitJs,
  getTcargoBillHead,
  commitJl
} from "@/api/ship/cdshipgrpt";
import request from "@/router/axios";
import "@/styles/common/edit.scss";
import { mapGetters } from "vuex";
import moment from "moment";
import Vue from "vue";
import VXETable from "vxe-table";
import hljs from "highlight.js";
import { Loading,Message } from "element-ui";
import Feemain from "./feemain";
export default {
  props: ["objrow"],
  data() {
    return {
      obj: {},
      objShip: {},
      tableData: [],
      buttonload: false,
      downTableOption: {},
      dialogFormVisible: false,
      ifCommitButton: false,
      ifShButton: false
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() {
    this.commitBtn = this.permissions["SHIP_GRPT_JS_UPDATE"];
    this.shBtn = this.permissions["SHIP_GRPT_JS_COMMIT"];
  },
  mounted: function() {
    this.obj = this.objrow;
    //查询价格
    getpayList(this.obj.shipNoS)
      .then(response => {
        var amtAll = 0;
        for (var i in response.data.data) {
          amtAll = amtAll + response.data.data[i].voyageMny;
        }
        this.obj.amtAll = amtAll;
        this.getDate();
      })
      .catch(() => {});
  },
  methods: {
    getDate() {
      getCFeeBillList(this.obj.shipNo)
        .then(response => {
          this.tableData = response.data.data;
          var amtAll = 0;
          for (var i in response.data.data) {
            amtAll = amtAll + response.data.data[i].feeMny;
          }
          this.obj.amtSjAll = amtAll;
          this.obj.amtC = -this.numSub(this.obj.amtAll, amtAll);
          this.getTcargoHead();
        })
        .catch(() => {});
    },
    getTcargoHead() {
      if (this.obj.shipNo != null) {
        getTcargoBillHead(this.obj.shipNo)
          .then(response => {
            var datamap = response.data.data;
            if (
              datamap != null &&
              (datamap.DSJ_BEGIN == null || datamap.DSJ_BEGIN != "1")
            ) {
              this.ifCommitButton = true;
            } else {
              if (
                datamap.DSJ == null ||
                datamap.DSJ == undefined ||
                datamap.DSJ != "1"
              ) {
                this.ifShButton = true;
              }
              this.ifCommitButton = false;
              for (var i in this.tableData) {
                if (this.tableData[i].status == null) {
                  this.tableData[i].status = 2;
                }
              }
            }
          })
          .catch(() => {});
      }
    },
    sh() {
      //判断金额是负值
      if(this.obj.amtC<0){
       Message({
      message: "已经欠款，无法进行审核",
      type: 'error'
       })
        return;
      }
      let that = this;
      this.$confirm("是否确认审核" + that.obj.shipNam + "结算费用", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return commitJl(that.obj.shipNoS);
        })
        .then(data => {
          this.$message.success("审核成功");
            this.$emit("selectList");
          this.getDate();
        });
    },
    closeWindows: function() {
      this.$emit("closeWindows");
    },
    ///////////////////////////////表格操作区域方法////////////////////
    dictItemVisible: function() {
      this.dialogFormVisible = false;
    },
    editRow(row) {
      this.objShip = row;
      this.objShip.agentCod_01 = this.objrow.agentCod_01;
      this.dialogFormVisible = true;
    },
    commitFee() {
      let that = this;
      this.$confirm("是否确认上报" + that.obj.shipNam + "结算费用", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return commitJs(that.obj.shipNoS);
        })
        .then(data => {
          this.$message.success("上报成功");
            this.$emit("selectList");
          this.getDate();
        });
    },
    formatterStatus({ cellValue }) {
      if (cellValue == null || cellValue == 1) {
        return "待上报";
      } else if (cellValue == 2) {
        return "已上报";
      }else if(cellValue == 3){
         return "已审核";
      }else if(cellValue == 4){
         return "已结算";
      }
    },
    cellStyle({ row, rowIndex, column, columnIndex }) {
      if (column.property === "status") {
        if (row.status == null || row.status == 1) {
          return {
            color: "green"
          };
        } else if (row.status == 2) {
          return {
            color: "blue"
          };
        } else if (row.status == 3) {
          return {
            color: "blue"
          };
        }else if (row.status == 4) {
          return {
            color: "red"
          };
        }
      }
    },
    numSub(num1, num2) {
      var baseNum, baseNum1, baseNum2;
      var precision; // 精度
      try {
        baseNum1 = num1.toString().split(".")[1].length;
      } catch (e) {
        baseNum1 = 0;
      }
      try {
        baseNum2 = num2.toString().split(".")[1].length;
      } catch (e) {
        baseNum2 = 0;
      }
      baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
      precision = baseNum1 >= baseNum2 ? baseNum1 : baseNum2;
      return ((num1 * baseNum - num2 * baseNum) / baseNum).toFixed(precision);
    }
  }
};
</script>
<style >
.feemain .el-collapse-item__content {
  padding-bottom: 0px;
}
.feemain .vxe-toolbar {
  height: 25px;
}
</style>