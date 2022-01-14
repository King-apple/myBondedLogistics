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
  <div class="dcjfioyardhead">
    <basic-container>
      <vxe-form :data="formObj" @reset="resetForm">
        <vxe-form-item title="时间" field="selectDate" size="mini" span="4">
          <vxe-input v-model="formObj.selectDate" type="date" clearable></vxe-input>
        </vxe-form-item>
         <vxe-form-item title="合同号：" field="contNo" size="mini" span="6">
          <vxe-input v-model="formObj.contNo" clearable size="mini"></vxe-input>
        </vxe-form-item>
         <vxe-form-item title="船号：" field="shipNo" size="mini" span="6">
          <vxe-input v-model="formObj.shipNo" clearable size="mini"></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="船名：" field="shipNam" size="mini" span="6">
          <EditDown
            :objs="formObj"
            :property="downTableOption.shipDataname"
            :tableColumns="downTableOption.shipDataColum"
            :urlapi="downTableOption.shipDataurl"
            :returnApi="downTableOption.shipDatareturnApi"
            sizenow="mini"
          ></EditDown>
        </vxe-form-item>
         <vxe-form-item title="内外贸：" field="tradeId" size="mini" span="5">
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
          <vxe-button status="primary" icon="fa fa-search" @click="getDate()">查询</vxe-button>
          <vxe-button type="reset" icon="fa fa-repeat">重置</vxe-button>
        </vxe-form-item>
      </vxe-form>
      <vxe-toolbar perfect size="mini">
        <template v-slot:buttons>
          <vxe-button type="text" class="fa fa-lock" @click="deal()">自动计费</vxe-button>
        </template>
      </vxe-toolbar>
      <el-row :gutter="15">
        <el-col :span="24">
          <vxe-table
            border
            show-overflow
            keep-source
            highlight-current-row
            size="mini"
            :data="tableDataMioYardHead"
            :loading="tableloadMioYardHead"
            :cell-style="cellStyle"
            :round="true"
            align="center"
            height="300px"
            ref="tableMioYardHead"
            @cell-click="rowclick"
          >
            <vxe-table-column field="informNo" title="单号" width="110px"></vxe-table-column>
            <vxe-table-column field="contNo" title="合同号" width="100"></vxe-table-column>
            <vxe-table-column field="ifendId" title="是否封存" width="100" :formatter="formatterIfEnd"></vxe-table-column>
            <vxe-table-column field="consignNam" title="作业委托人" width="200"></vxe-table-column>
            <vxe-table-column field="tradeId" title="内外贸" width="80" :formatter="formatterTradeId"></vxe-table-column>
            <vxe-table-column field="inoutId" title="进出口" width="80" :formatter="formatterInoutId"></vxe-table-column>
            <vxe-table-column field="shipNo" title="船号" width="100"></vxe-table-column>
            <vxe-table-column field="shipNam" title="船名" width="100"></vxe-table-column>
            <vxe-table-column field="dcType" title="堆存方式" width="100"></vxe-table-column>
            <vxe-table-column field="outNo" title="出库单号" width="100"></vxe-table-column>
            <vxe-table-column field="inYardWay" title="入库方式" width="100"></vxe-table-column>
            <vxe-table-column field="outYardWay" title="出库方式" width="100"></vxe-table-column>
            <vxe-table-column field="subunitNam" title="保管单位" width="100"></vxe-table-column>
            <vxe-table-column field="recordDte" title="记录日期" width="150"></vxe-table-column>
            <vxe-table-column field="cargoNam" title="货名" width="100"></vxe-table-column>
            <vxe-table-column field="cargoKindNam" title="价格货类" width="100"></vxe-table-column>
            <vxe-table-column field="cargoKindNamDl" title="大货类" width="100"></vxe-table-column>
            <vxe-table-column field="checkNam" title="检算员" width="100"></vxe-table-column>
            <vxe-table-column field="ifendDate" title="封存日期" width="100"></vxe-table-column>
          </vxe-table>
          <vxe-pager
            perfect
            size="mini"
            :loading="tableloadMioYardHead"
            :current-page="tablePage.currentPage"
            :page-size="tablePage.pageSize"
            :total="tablePage.total"
            :layouts="['PrevPage', 'JumpNumber', 'NextPage', 'FullJump', 'Sizes', 'Total']"
            @page-change="handlePageChange"
          ></vxe-pager>
        </el-col>
        <el-col :span="12">
          <vxe-table
            border
            show-overflow
            keep-source
            highlight-current-row
            size="mini"
            :data="tableDataMioYardBody"
            :loading="tableloadMioYardBody"
            :round="true"
            align="center"
            height="340px"
            ref="tableMioYardBody"
            @cell-click="rowclick2"
          >
            <vxe-table-column type="seq" width="60" title="序号"></vxe-table-column>
            <vxe-table-column field="billNo" title="提单号" width="120"></vxe-table-column>
            <vxe-table-column
              field="inyardDte"
              title="入库日期"
              width="100"
              :formatter="['formatDate', 'yyyy-MM-dd']"
            ></vxe-table-column>
            <vxe-table-column field="ipiecesNum" title="入库件数" width="80"></vxe-table-column>
            <vxe-table-column field="iweightWgt" title="入库吨数" width="80"></vxe-table-column>
            <vxe-table-column
              field="outyardDte"
              title="出库日期"
              width="100"
              :formatter="['formatDate', 'yyyy-MM-dd']"
            ></vxe-table-column>
            <vxe-table-column field="opiecesNum" title="出库件数" width="80"></vxe-table-column>
            <vxe-table-column field="oweightWgt" title="出库吨数" width="80"></vxe-table-column>
            <vxe-table-column title="单件重<=45">
              <vxe-table-column field="pieceNum1" title="件数" width="80"></vxe-table-column>
              <vxe-table-column field="weightWgt1" title="件重" width="80"></vxe-table-column>
            </vxe-table-column>
            <vxe-table-column title="单件重>45">
              <vxe-table-column field="pieceNum2" title="件数" width="80"></vxe-table-column>
              <vxe-table-column field="weightWgt2" title="件重" width="80"></vxe-table-column>
            </vxe-table-column>
            <vxe-table-column field="contNo" title="备注">
              <vxe-table-column field="rremark" title="入库说明" width="80"></vxe-table-column>
              <vxe-table-column field="cremark" title="出库说明" width="80"></vxe-table-column>
            </vxe-table-column>
          </vxe-table>
        </el-col>
        <el-col :span="12">
          <vxe-table
            border
            show-overflow
            keep-source
            size="mini"
            :data="tableDataCargoBody"
            :loading="tableloadCargoBody"
            :round="true"
            align="center"
            height="340px"
            ref="tableCargoBody"
          >
            <vxe-table-column type="seq" width="60" title="序号"></vxe-table-column>
            <vxe-table-column field="weightWgt" title="重量" width="80"></vxe-table-column>
            <vxe-table-column field="feeRat" title="费率" width="80"></vxe-table-column>
            <vxe-table-column field="dcfeeNum" title="堆存天数" width="80"></vxe-table-column>
            <vxe-table-column field="dts" title="超期天数" width="80"></vxe-table-column>
            <vxe-table-column field="ysfeeNum" title="应收天数" width="80"></vxe-table-column>
            <vxe-table-column field="ysfeeMny" title="应收金额" width="80"></vxe-table-column>
            <vxe-table-column field="feeNum" title="实收天数" width="80"></vxe-table-column>
            <vxe-table-column field="feeMny" title="实收金额" width="80"></vxe-table-column>
            <vxe-table-column field="taxFeeMny" title="差额" width="80"></vxe-table-column>
          </vxe-table>
        </el-col>
      </el-row>
    </basic-container>
  </div>
</template>

<script>
import {
  fetchList,
  fetchBodyList,
  fetchCargoList,
  zdFee
} from "@/api/account/dcjfioyardhead";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import EditDown from "../../baseUtile/EditDown";
export default {
  components: {EditDown},
  name: "mioyardhead",
  data() {
    return {
      formObj: {  
        shipNo: null,
        shipNam: null,
        tradeId: null,
        inoutId: null,
        ifendId: null,
        contType: null,},
      dialogFormVisibleedit: false,
      tableDataMioYardHead: [],
      tableDataMioYardBody: [],
      tableDataCargoBody: [],
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      tableloadMioYardHead: false,
      tableloadMioYardBody: false,
      tableloadCargoBody: false,
      dataHeadNow: {},
      dataBodyNow:{},
      dateNow: {},
      downTableOption: {
        shipDataColum: [
          { field: "CALL_NO", title: "呼号" },
          { field: "C_SHIP_NAM", title: "船名" },
          { field: "E_SHIP_NAM", title: "英文船名" },
        ],
        //下拉菜单的路径
        shipDataurl: "/ship/baseCod/shipdata/page",
        shipDataname: "shipNam",
        shipDatareturnApi: [{ field: "C_SHIP_NAM", returnName: "shipNam" }],
      },
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() {
    this.getDate();
  },
  methods: {
    getDate() {
      this.tableloadMioYardHead = true;
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
          this.tableDataMioYardHead = response.data.data.records;
          this.tablePage.total = response.data.data.total;
          this.tableloadMioYardHead = false;
        })
        .catch(() => {
          this.tableloadMioYardHead = false;
        });
    },
    getBody() {
      this.tableloadMioYardBody = true;
      fetchBodyList(this.dataHeadNow.informNo)
        .then(response => {
          this.tableDataMioYardBody = response.data.data;
          this.tableloadMioYardBody = false;
        })
        .catch(() => {
          this.tableloadMioYardBody = false;
        });
    },
    getCargo() {
      this.tableloadCargoBody = true;
      fetchCargoList(this.dataHeadNow.informNo,this.dataBodyNow.dno)
        .then(response => {
          this.tableDataCargoBody = response.data.data;
          this.tableloadCargoBody = false;
        })
        .catch(() => {
          this.tableloadCargoBody = false;
        });
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    rowclick({ row }) {
      this.dataHeadNow = row;
       this.dataBodyNow={dno:""};
      this.getBody();
      this.getCargo();
    },
    rowclick2({row}){
        this.dataBodyNow=row;
         this.getCargo();
    },
    lockOne() {
      if (this.dataHeadNow != null && this.dataHeadNow.informNo != null) {
        let loadingInstance = Loading.service({
          fullscreen: true,
          text: "封存中……"
        });
        lockHead(this.dataHeadNow.informNo)
          .then(data => {
            this.$message.success("封存成功");
            loadingInstance.close();
            this.getDate();
          })
          .catch(() => {
            loadingInstance.close();
          });
      } else {
        this.$message.error("请选择一条数据进行提取");
      }
    },
    deal() {
      if (this.dataHeadNow != null && this.dataHeadNow.informNo != null) {
        let loadingInstance = Loading.service({
          fullscreen: true,
          text: "自动计算中……"
        });
        zdFee(this.dataHeadNow.informNo)
          .then(data => {
            this.$message.success("自动计算成功");
            loadingInstance.close();
            this.getCargo();
          })
          .catch(() => {
            loadingInstance.close();
          });
      } else {
        this.$message.error("请选择一条数据进行自动计费");
      }
    },
    unlockOne() {
      if (this.dataHeadNow != null && this.dataHeadNow.informNo != null) {
        let loadingInstance = Loading.service({
          fullscreen: true,
          text: "解封中……"
        });
        unlockHead(this.dataHeadNow.informNo)
          .then(data => {
            this.$message.success("解封成功");
            loadingInstance.close();
            this.getDate();
          })
          .catch(() => {
            loadingInstance.close();
          });
      } else {
        this.$message.error("请选择一条数据进行提取");
      }
    },
    formatterTradeId({ cellValue }) {
      if (cellValue == "1") {
        return "内贸";
      } else if (cellValue == "0") {
        return "外贸";
      }
    },
    formatterInoutId({ cellValue }) {
      if (cellValue == "1") {
        return "进口";
      } else if (cellValue == "0") {
        return "出口";
      }
    },
    formatterIfEnd({ cellValue }) {
      if (cellValue == 1) {
        return "封存";
      } else {
        return "未封存";
      }
    },
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
    },
     resetForm() {
      this.getDate();
    },
    cellStyle({ row, rowIndex, column, columnIndex }) {
      if (column.property === "ifendId") {
        if (row.ifendId == 1) {
          return {
            color: "red"
          };
        } else {
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
.dcjfioyardhead {
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