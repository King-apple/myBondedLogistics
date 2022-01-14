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
  <div class="business-jg-index">
    <basic-container>
      <vxe-form :data="formObj" size="mini" @reset="resetForm">
        <vxe-form-item
          title="时间段："
          field="shipTimebegin"
          size="mini"
          span="6"
        >
          <vxe-input
            v-model="formObj.shipTimebegin"
            placeholder="开始时间"
            type="date"
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="至" field="shipTimeend" size="mini" span="6">
          <vxe-input
            v-model="formObj.shipTimeend"
            placeholder="结束时间"
            type="date"
          ></vxe-input>
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
        <vxe-form-item title="作业公司" field="unitNam" size="mini" span="5">
          <EditDown
            :objs="formObj"
            :property="downTableOption.unitNam"
            :tableColumns="downTableOption.unitColum"
            :urlapi="downTableOption.uniturl"
            :returnApi="downTableOption.unitreturnApi"
            sizenow="mini"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item title="进出口：" field="inoutId" size="mini" span="5">
          <vxe-select v-model="formObj.inoutId" clearable size="mini">
            <vxe-option value="1" label="进口"></vxe-option>
            <vxe-option value="0" label="出口"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item title="状态：" field="statusType" size="mini" span="5">
          <vxe-select v-model="formObj.statusType" clearable size="mini">
            <vxe-option value="1" label="已计费"></vxe-option>
            <vxe-option value="0" label="未计费"></vxe-option>
            <vxe-option value="0" label="开发票"></vxe-option>
            <vxe-option value="0" label="解封"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="center">
          <vxe-button status="primary" icon="fa fa-search" @click="getDate()"
            >查询</vxe-button
          >
          <vxe-button type="reset" icon="fa fa-repeat">重置</vxe-button>
        </vxe-form-item>
      </vxe-form>
      <el-row :gutter="10">
        <el-col :span="24">
          <vxe-toolbar perfect size="mini">
            <template v-slot:buttons>
              <vxe-button
                type="text"
                class="fa fa-plus"
                @click="openContChange()"
                >船舶合同修改查询</vxe-button
              >
            </template>
          </vxe-toolbar>
          <vxe-table
            border
            show-overflow
            keep-source
            highlight-hover-row
            highlight-current-row
            size="mini"
            :data="tableData"
            :loading="tableload"
            class="mytable-style"
            :round="true"
            align="center"
            height="400px"
            ref="tableloadHeadRef"
            :row-class-name="rowClassName"
            @cell-dblclick="feeClick"
            @cell-click="cellClickEvent"
            :expand-config="{
              accordion: true,
              lazy: true,
              loadMethod: loadContentMethod,
              visibleMethod: expandVisibleMethod,
            }"
          >
            <vxe-table-column type="expand" width="100" title="疏港合同数">
              <template v-slot="{ row, rowIndex }">
                <span>
                  <font color="red">{{ row.ysNum }}个</font>
                </span>
              </template>
              <template v-slot:content="{ row }">
                <vxe-grid
                show-footer
                  :columns="row.childCols"
                  :data="row.childData"
                  :footer-method="footerMethod"
                ></vxe-grid>
              </template>
            </vxe-table-column>
            <vxe-table-column
              field="shipNam"
              title="船名"
              width="100"
            ></vxe-table-column>
            <vxe-table-column
              field="shipTime"
              title="完工时间"
              width="140"
            ></vxe-table-column>
            <vxe-table-column
              field="inoutId"
              title="进出口"
              width="60"
              :formatter="formatterInoutId"
            ></vxe-table-column>
            <vxe-table-column
              field="tradeId"
              title="内外贸"
              width="60"
              :formatter="formatterTradeId"
            ></vxe-table-column>
            <vxe-table-column
              field="contNo"
              title="合同号"
              width="90"
            ></vxe-table-column>
            <vxe-table-column
              field="statusType"
              title="状态"
              width="60"
            ></vxe-table-column>
            <vxe-table-column
              field="firstNam"
              title="作业委托人"
              width="170"
            ></vxe-table-column>
            <vxe-table-column
              field="shipperDocPrice"
              title="价格货主"
              width="170"
            ></vxe-table-column>
            <vxe-table-column
              field="weightWgtHt"
              title="卸船合同计划量"
              width="110"
            ></vxe-table-column>
            <vxe-table-column
              field="weightWgtGkbg"
              title="计费吨"
              width="100"
            ></vxe-table-column>
            <vxe-table-column
              field="feeMnyGkbg"
              title="港口费金额"
              width="100"
            ></vxe-table-column>
            <vxe-table-column
              field="feeMnyDcfzg"
              title="暂估堆存费"
              width="100"
            ></vxe-table-column>
            <vxe-table-column
              field="feeMnyCgf"
              title="船港费"
              width="100"
            ></vxe-table-column>
            <vxe-table-column
              field="weightWgtScargo"
              title="作业量"
              width="100"
            ></vxe-table-column>
            <vxe-table-column
              field="unitNam"
              title="作业公司"
              width="170"
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
          <el-col :span="8">
            <div ref="chart1" style="width: 500px; height: 300px"></div>
          </el-col>
          <el-col :span="8">
            <div ref="chart2" style="width: 500px; height: 300px"></div>
          </el-col>
          <el-col :span="8">
            <div ref="chart3" style="width: 500px; height: 300px"></div>
          </el-col>
        </el-col>
      </el-row>
      <vxe-modal
        v-if="dialogFormVisibleFee"
        v-model="dialogFormVisibleFee"
        title="计费信息"
        width="1000"
        height="600"
        resize
        storage
      >
        <FeeList
          :datanow="dateNow"
          @dictItemVisible="dictItemVisible"
        ></FeeList>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleContChange"
        v-model="dialogFormVisibleContChange"
        title="船舶合同痕迹信息"
        width="1000"
        height="600"
        resize
        storage
      >
        <ContChangeList
          :contNo="contNo"
          :unitCod="unitCod"
          @dictItemVisible="dictItemVisible"
        ></ContChangeList>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import { selectGet, selectBodyGet } from "@/api/business/jg";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import EditDown from "../../baseUtile/EditDown";
import FeeList from "./feeList";
import ContChangeList from "./contChangeList";
import XEUtils from "xe-utils";
export default {
  components: { EditDown, FeeList, ContChangeList },
  name: "gen-marster-index",
  data() {
    return {
      dateNow: {},
      formObj: {
        shipNam: null,
        tradeId: null,
        inoutId: null,
        statusType: null,
        unitNam: null,
        unitCod: null,
      },
      tableload: false,
      tableBodyload: false,
      tableData: [],
      tableBodyData: [],
      dialogFormVisibleFee: false,
      dialogFormVisibleContChange: false,
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      downTableOption: {
        //业务员下拉菜单展示项
        shipDataColum: [
          { field: "CALL_NO", title: "呼号" },
          { field: "C_SHIP_NAM", title: "船名" },
          { field: "E_SHIP_NAM", title: "英文船名" },
        ],
        //下拉菜单的路径
        shipDataurl: "/ship/baseCod/shipdata/page",
        shipDataname: "shipNam",
        shipDatareturnApi: [{ field: "C_SHIP_NAM", returnName: "shipNam" }],
        //单位
        unitNam: "unitNam",
        unitColum: [
          { field: "UNIT_COD", title: "单位代码" },
          { field: "UNIT_NAM", title: "单位名称" },
        ],
        unitreturnApi: [
          { field: "UNIT_COD", returnName: "unitCod" },
          { field: "UNIT_NAM", returnName: "unitNam" },
        ],
        uniturl: "/basecode/basecod/getUnitNam",
      },
      selectRow: null,
      selectColumn: null,
      contNo: null,
      unitCod: null,
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  mounted() {
    this.myEcharts();
    this.myEcharts2();
    this.myEcharts3();
  },
  created() {
    this.getDate();
  },
  methods: {
    getDate() {
      this.tableload = true;
      selectGet(
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
          this.tableload = false;
        })
        .catch(() => {
          this.tableload = false;
        });
    },
    loadContentMethod({ row }) {
      return new Promise((resolve) => {
        setTimeout(() => {
          selectBodyGet(row.fsSeqNo).then((response) => {
            const childCols = [
              { type: "seq", title: "序号" },
              { field: "informNoOutyard", title: "疏港合同" },
              { field: "informNoDcjf", title: "堆存费结算单号" },
              { field: "outyardMaxdate", title: "疏港日期（最大日期）" },
              { field: "outyardMindate", title: "疏港日期（最小日期）" },
              { field: "unitCod", title: "公司代码" },
              { field: "weightWgtDcjf", title: "计费吨" },
              { field: "feeMnyDcjf", title: "堆存费金额" },
              { field: "weightWgtOutyard", title: "疏港合同计划量" },
            ];
            this.tableBodyData = response.data.data;
            row.childCols = childCols;
            row.childData = response.data.data;
            resolve();
          });
        }, 500);
      });
    },
    expandVisibleMethod({ row }) {
      if (row.ysNum === 0) {
        return false;
      }
      return true;
    },
    resetForm() {
      this.formObj.unitCod = null;
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    feeClick({ row }) {
      this.dateNow = row;
      this.dialogFormVisibleFee = true;
    },
    dictItemVisible() {
      this.dialogFormVisibleFee = false;
      this.dialogFormVisibleContChange = false;
    },
    openContChange() {
      if (this.contNo == null) {
        this.$message.error("请选择一条记录后，进行操作");
        return;
      }
      this.dialogFormVisibleContChange = true;
    },
    footerMethod({ columns, data }) {
      return [
        columns.map((column, columnIndex) => {
          if (columnIndex === 0) {
            return "和值";
          }
          if (["weightWgtDcjf", "feeMnyDcjf","weightWgtOutyard"].includes(column.property)) {
            return XEUtils.sum(data, column.property);
          }
          return null;
        }),
      ];
    },
    formatterInoutId({ cellValue }) {
      if (cellValue == "1") {
        return "进口";
      } else if (cellValue == "0") {
        return "出口";
      }
    },
    formatterTradeId({ cellValue }) {
      if (cellValue == "1") {
        return "内贸";
      } else if (cellValue == "0") {
        return "外贸";
      }
    },
    myEcharts() {
      // 基于准备好的dom，初始化echarts实例
      var bar_dv = this.$refs.chart1;
      var myChart = this.$echarts.init(bar_dv);
      // 指定图表的配置项和数据
      var option = {
        title: {
          text: "公司占比统计",
          left: "center",
        },
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b}: {c} ({d}%)",
        },
        legend: {
          orient: "vertical",
          left: 10,
          data: ["一公司", "二公司", "三公司", "四公司", "五公司", "粮食公司"],
        },
        series: [
          {
            name: "访问来源",
            type: "pie",
            radius: ["50%", "70%"],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: "center",
            },
            emphasis: {
              label: {
                show: true,
                fontSize: "30",
                fontWeight: "bold",
              },
            },
            labelLine: {
              show: false,
            },
            data: [
              { value: 335, name: "一公司" },
              { value: 310, name: "二公司" },
              { value: 234, name: "三公司" },
              { value: 135, name: "四公司" },
              { value: 1548, name: "五公司" },
              { value: 1548, name: "粮食公司" },
            ],
          },
        ],
      };

      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    },
    myEcharts2() {
      // 基于准备好的dom，初始化echarts实例
      var bar_dv = this.$refs.chart2;
      var myChart = this.$echarts.init(bar_dv);
      // 指定图表的配置项和数据
      var option = {
        title: {
          text: "公司总金额",
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow",
          },
        },
        legend: {
          data: ["总金额"],
        },
        grid: {
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true,
        },
        xAxis: {
          type: "value",
          boundaryGap: [0, 0.01],
        },
        yAxis: {
          type: "category",
          data: ["一公司", "二公司", "三公司", "四公司", "五公司", "粮食公司"],
        },
        series: [
          {
            name: "总金额",
            type: "bar",
            data: [19325, 23438, 31000, 121594, 134141, 681807],
          },
        ],
      };

      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    },
    rowClassName({ row, rowIndex }) {
      if (row.contTrace === 1 && this.selectRow != row) {
        return "row-red";
      } else if (this.selectRow == row) {
        return "row-click";
      }
    },
    cellClickEvent({ row, column }) {
      this.unitCod = row.unitCod;
      this.contNo = row.contNo;
      this.selectRow = row;
      this.selectColumn = column;
    },
    myEcharts3() {
      // 基于准备好的dom，初始化echarts实例
      var bar_dv = this.$refs.chart3;
      var myChart = this.$echarts.init(bar_dv);
      // 指定图表的配置项和数据
      var option = {
        title: {
          text: "堆存费占比",
          left: "center",
        },
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b}: {c} ({d}%)",
        },
        legend: {
          orient: "vertical",
          left: 10,
          data: ["一公司", "二公司", "三公司", "四公司", "五公司", "粮食公司"],
        },
        series: [
          {
            name: "访问来源",
            type: "pie",
            radius: ["50%", "70%"],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: "center",
            },
            emphasis: {
              label: {
                show: true,
                fontSize: "30",
                fontWeight: "bold",
              },
            },
            labelLine: {
              show: false,
            },
            data: [
              { value: 335, name: "一公司" },
              { value: 310, name: "二公司" },
              { value: 234, name: "三公司" },
              { value: 135, name: "四公司" },
              { value: 1548, name: "五公司" },
              { value: 1548, name: "粮食公司" },
            ],
          },
        ],
      };

      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    },
  },
};
</script>
<style lang="scss" >
.business-jg-index {
  .el-tag {
    width: 100%;
  }
  .el-divider--horizontal {
    margin: 10px 0;
  }
  .vxe-toolbar {
    height: 25px;
  }
  .mytable-style .vxe-body--row.row-red {
    background-color: lightskyblue;
  }
  .mytable-style .vxe-body--row.row-click {
    background-color: #e6f7ff;
  }
}
</style>>