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
  <div class="feeList">
    <vxe-toolbar perfect size="mini">
      <template v-slot:buttons>
        <vxe-button v-if="addBtn" type="text" class="fa fa-plus" @click="bj()">新增</vxe-button>
      </template>
    </vxe-toolbar>
    <vxe-table border show-overflow size="mini" :data="tableData" :cell-style="cellStyle" :loading="loading">
      <vxe-table-column type="seq" width="60" fixed="left"></vxe-table-column>
      <vxe-table-column field="payNo" title="单号"></vxe-table-column>
      <vxe-table-column field="bj" title="报价类型"></vxe-table-column>
      <vxe-table-column field="agentNam" title="客户"></vxe-table-column>
      <vxe-table-column field="voyageMny" title="最终金额"></vxe-table-column>
      <vxe-table-column field="payWay" title="报价方式" :formatter="formatterPayWay"></vxe-table-column>
      <vxe-table-column field="status" title="状态" :formatter="formatterStatus"></vxe-table-column>
      <vxe-table-column title="操作" fixed="right" width="200">
        <template v-slot="{ row }">
          <vxe-button v-if="(row.status==1||row.status==null)&&addBtn" type="text" icon="fa fa-arrow-up" @click="commitRow(row)">上报</vxe-button>
          <vxe-button v-if="(row.status==1||row.status==null)&&addBtn" type="text" icon="fa fa-edit" @click="editRow(row)">编辑</vxe-button>
          <vxe-button v-if="(row.status==1||row.status==null)&&addBtn" type="text" icon="fa fa-trash" @click="removeEvent(row)">删除</vxe-button>
          <vxe-button v-if="(row.status!=1)" type="text" icon="fa fa-eye" @click="viewRow(row)">详情</vxe-button>
        </template>
      </vxe-table-column>
    </vxe-table>
    <vxe-modal
      v-if="dialogFormVisible"
      v-model="dialogFormVisible"
      id="myModal6"
      title="大数据客户报价"
      width="1200"
      height="830"
      min-width="1100"
      min-height="810"
      resize
      storage
      transfer
    >
      <Feemain :objrow="objShip" @dictItemVisible="dictItemVisible" @getListDate="getDate" :objshipnew="obj" :ifView="ifView"></Feemain>
    </vxe-modal>
  </div>
</template>

<script>
import {
  getAgentObj,
  commitFee,
  getfeeList,
  getpayList,
  delShipPay,
  commit
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
      tableData: [],
      buttonload: false,
      downTableOption: {},
      ifView:false,
      loading: true,
      dialogFormVisible: false,
      payWay: [
        {
          label: "电汇",
          value: "0"
        },
        {
          label: "支票",
          value: "1"
        },
        {
          label: "现金",
          value: "2"
        },
        {
          label: "月结",
          value: "3"
        },
        {
          label: "有款",
          value: "4"
        },
        {
          label: "刷卡",
          value: "5"
        },
        {
          label: "次结",
          value: "6"
        },
        {
          label: "平台结算",
          value: "7"
        }
      ]
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created: function() {
    this.obj = this.objrow;
    //查询价格
    this.addBtn = this.permissions["SHIP_GRPT_YF_ADD"];
     this.yfBtn = this.permissions["SHIP_GRPT_YF_SELECT"];
    this.getDate();
  },
  methods: {
    getDate() {
      getpayList(this.obj.shipNoS)
        .then(response => {
          this.tableData = response.data.data;
          this.loading=false;
        })
        .catch(() => {});
    },
    getDateFromNext(){
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
    bj() {
      this.objShip = this.objrow;
       this.objShip.bj = "正常";
      if (this.tableData.length == 0) {
        this.objShip.bj = "正常";
      } else if (this.tableData.length > 0) {
        for(var num = 1;num<this.tableData.length;num++){
          if(this.tableData[num].status==null&&this.tableData[num].status!=0){
            this.objShip.bj = "补交";  
          }
        }
      }
      this.dialogFormVisible = true;
      this.ifView=false;
    },
    dictItemVisible: function() {
      this.dialogFormVisible = false;
    },
    editRow(row) {
      this.objShip = row;
      this.objShip.agentCod_01 = this.objrow.agentCod_01;
      this.dialogFormVisible = true;
       this.ifView=false;
    },
    viewRow(row){
       this.objShip = row;
      this.objShip.agentCod_01 = this.objrow.agentCod_01;
      this.dialogFormVisible = true;
      this.ifView=true;
    },
    formatterPayWay({ cellValue }) {
      let item = this.payWay.find(item => item.value === cellValue);
      return item ? item.label : "";
    },
    formatterStatus({ cellValue }) {
      if (cellValue == 1||cellValue==null) {
        return "待提交";
      } else if (cellValue == 2) {
        return "已提交";
      }else if (cellValue == 0) {
        return "已作废";
      }else if (cellValue == 3) {
        return "已支付";
      }
    },
    removeEvent(row) {
      this.$confirm("是否确认删除" + row.payNo + "报价单", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return delShipPay(row.payNo);
        })
        .then(data => {
          this.$message.success("删除成功");
          this.getDate();
        });
    },
    commitRow(row) {
      let that=this
      this.$confirm("是否确认上报" + row.payNo + "报价单", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          that.loading=true;
          return commit(row);
        })
        .then(data => {
          this.$message.success("上报成功");
           this.$emit("selectList");
          that.loading=false;
          this.getDate();
        }).catch(() => {
              that.loading = false;
        });
    },
    cellStyle({ row, rowIndex, column, columnIndex }) {
      if (column.property === "status") {
        if (row.status==1||row.status==null) {
          return {
            color: "green"
          };
        } else if (row.status==2) {
          return {
            color: "blue"
          };
        } else if (row.status==3) {
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
