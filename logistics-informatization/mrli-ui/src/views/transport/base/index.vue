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
  <div class="transport-base-index">
    <basic-container>
      <!--查询区-->
      <vxe-form :data="formObj" size="mini">
        <vxe-form-item title="货票信息" field="hpxx" size="mini" span="3">
          <vxe-select v-model="formObj.hpxx" size="mini">
            <vxe-option value="A" label="当前货票"></vxe-option>
            <vxe-option value="B" label="历史货票"></vxe-option>
            <vxe-option value="C" label="全部货票"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item title="货票是否完成" field="bpwc" size="mini" span="3">
          <vxe-select v-model="formObj.bpwc" clearable size="mini">
            <vxe-option value="0" label="未完成"></vxe-option>
            <vxe-option value="1" label="已完成"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item title="称别组" field="cbs" size="mini" span="2">
          <EditDown
            :objs="formObj"
            :property="downTableOption.cbsNam"
            :tableColumns="downTableOption.cbsColum"
            :urlapi="downTableOption.cbsurl"
            :returnApi="downTableOption.cbsreturnApi"
            sizenow="mini"
            pageSize="30"
            height="450"
            width="150"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item title="时间段：" field="zbrqbegin" size="mini" span="3">
          <vxe-input
            v-model="formObj.zbrqbegin"
            placeholder="开始时间"
            type="date"
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="至" field="zbrqend" size="mini" span="3">
          <vxe-input
            v-model="formObj.zbrqend"
            placeholder="结束时间"
            type="date"
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="船编号" field="cbh" size="mini" span="4">
          <EditDown
            :objs="formObj"
            :property="downTableOption.cbhNam"
            :tableColumns="downTableOption.cbhColum"
            :urlapi="downTableOption.cbhurl"
            :returnApi="downTableOption.cbhreturnApi"
            sizenow="mini"
            pageSize="100"
            height="750"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="left">
          <vxe-button status="primary" icon="fa fa-search" @click="getDate()"
            >查询</vxe-button
          >
        </vxe-form-item>
        <!--
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
        </vxe-form-item> -->
      </vxe-form>
      <!--功能按钮-->
      <vxe-toolbar perfect size="mini">
        <template v-slot:buttons>
          <vxe-button type="text" class="fa fa-bar-chart" @click="viewCont()"
            >-详情统计</vxe-button
          >
          <vxe-button type="text" class="fa fa-bar-chart" @click="hlCont()"
            >-分类货物统计</vxe-button
          >
          <vxe-button type="text" class="fa fa-bar-chart" @click="sqdwCont()"
            >-申请单位报表</vxe-button
          >
          <vxe-button type="text" class="fa fa-bar-chart" @click="depCont()"
            >-作业公司报表</vxe-button
          >
          <vxe-button type="text" class="fa fa-bar-chart" @click="BzCont()"
            >-班组统计报表</vxe-button
          >
          <vxe-button type="text" class="fa fa-bar-chart" @click="CbsCont()"
            >-称别统计报表</vxe-button
          >
        </template>
      </vxe-toolbar>
      <!--表格-->
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
        <vxe-table-column type="seq" width="50" title="序号"></vxe-table-column>
        <vxe-table-column
          field="lxdm"
          title="代码"
          width="50"
        ></vxe-table-column>
        <vxe-table-column
          field="bh"
          title="货票号"
          width="80"
        ></vxe-table-column>
        <vxe-table-column
          field="hm"
          title="货名"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="tdh"
          title="提单号"
          width="110"
        ></vxe-table-column>
        <vxe-table-column
          field="cbh"
          title="船编号"
          width="80"
        ></vxe-table-column>
        <vxe-table-column
          field="cm"
          title="船名"
          width="120"
        ></vxe-table-column>
        <vxe-table-column
          field="jhl"
          title="计划量"
          width="120"
        ></vxe-table-column>
        <vxe-table-column
          field="wcl"
          title="完成量"
          width="120"
        ></vxe-table-column>
        <vxe-table-column
          field="syl"
          title="剩余量"
          width="120"
        ></vxe-table-column>
        <vxe-table-column
          field="csl"
          title="捣运车"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="cbs"
          title="称别组"
          width="70"
        ></vxe-table-column>
        <vxe-table-column
          field="sqdw"
          title="申请单位"
          width="200"
        ></vxe-table-column>
        <vxe-table-column
          field="bpwc"
          title="完成否"
          :formatter="formatterBpwc"
          width="60"
        ></vxe-table-column>
        <vxe-table-column
          field="nwm"
          title="内外贸"
          width="60"
        ></vxe-table-column>
        <vxe-table-column
          field="zbrq"
          title="值班日期"
          width="140"
        ></vxe-table-column>
      </vxe-table>
      <!--分页区-->
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
        title="详情统计"
        width="560"
        height="700"
        resize
        storage
      >
        <el-row>
          <el-col :span="12"
            ><div class="grid-content">船编号：{{ formT.cbhh }}</div></el-col
          >
          <el-col :span="12"
            ><div class="grid-content">船名：{{ formT.cmm }}</div></el-col
          >
        </el-row>

        <!-- <vxe-form :data="formT" size="mini">
      <vxe-form-item title="船编号" field="cbhh" size="mini" span="8">
        <vxe-input v-model="formT.cbhh" readonly></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="船名" field="cmm" size="mini" span="8">
        <vxe-input v-model="formT.cmm" readonly></vxe-input>
      </vxe-form-item>
    </vxe-form> -->
        <vxe-table
          border
          show-overflow
          keep-source
          highlight-current-row
          size="mini"
          :data="tableData2"
          :loading="tableload2"
          :round="true"
          align="center"
          height="600px"
          ref="tableSta"
          @cell-click="rowclick"
        >
          <vxe-table-column
            type="seq"
            width="50"
            title="序号"
          ></vxe-table-column>
          <vxe-table-column
            field="rqcbsf"
            title="日期称别组"
            width="200"
          ></vxe-table-column>
          <vxe-table-column
            field="countcar"
            title="车数量"
            width="100"
          ></vxe-table-column>
          <vxe-table-column
            field="weightSum"
            title="货重"
            width="150"
          ></vxe-table-column>
        </vxe-table>
        <vxe-pager
          perfect
          size="mini"
          :loading="tableload2"
          :current-page="tablePage2.currentPage"
          :page-size="tablePage2.pageSize"
          :total="tablePage2.total"
          :layouts="[
            'PrevPage',
            'JumpNumber',
            'NextPage',
            'FullJump',
            'Sizes',
            'Total',
          ]"
          @page-change="handlePageChange2"
        ></vxe-pager>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleViewTjByCargo"
        v-model="dialogFormVisibleViewTjByCargo"
        title="分类货物统计"
        width="800"
        height="750"
        resize
        storage
      >
        <TjbyCargo @dictItemVisible="dictItemVisible"></TjbyCargo>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleViewTjByUnit"
        v-model="dialogFormVisibleViewTjByUnit"
        title="申请单位报表"
        width="800"
        height="750"
        resize
        storage
      >
        <TjbyUnit @dictItemVisible="dictItemVisible"></TjbyUnit>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleViewTjByDep"
        v-model="dialogFormVisibleViewTjByDep"
        title="作业公司报表"
        width="800"
        height="750"
        resize
        storage
      >
        <TjbyDep @dictItemVisible="dictItemVisible"></TjbyDep>
      </vxe-modal>

      <vxe-modal
        v-if="dialogFormVisibleViewTjByBz"
        v-model="dialogFormVisibleViewTjByBz"
        title="班组统计报表"
        width="800"
        height="750"
        resize
        storage
      >
        <TjbyBz @dictItemVisible="dictItemVisible"></TjbyBz>
      </vxe-modal>

      <vxe-modal
        v-if="dialogFormVisibleViewTjByCbs"
        v-model="dialogFormVisibleViewTjByCbs"
        title="称别统计报表"
        width="800"
        height="750"
        resize
        storage
      >
        <TjbyCbs @dictItemVisible="dictItemVisible"></TjbyCbs>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import { fetchList, getStabc, getSumwcl } from "@/api/transport/hpmx";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import EditDown from "../../baseUtile/EditDown";
import TjbyCargo from "./tjByCargo";
import TjbyUnit from "./tjByUnit";
import TjbyDep from "./tjByDep";
import TjbyBz from "./tjByBz";
import TjbyCbs from "./tjByCbs";
export default {
  components: { EditDown, TjbyCargo, TjbyUnit, TjbyDep, TjbyBz, TjbyCbs },
  name: "transport-base-index",
  data() {
    return {
      formObj: {
        cbs: null,
        cbh: null,
        shipNam: null,
        firstCod: null,
        hpxx: "A",
      },
      formT: {},
      tableData: [],
      tableData2: [],
      dateNow: {},
      dataHeadNow: {},
      dialogFormVisibleView: false,
      dialogFormVisibleViewTjByCargo: false,
      dialogFormVisibleViewTjByUnit: false,
      dialogFormVisibleViewTjByDep: false,
      dialogFormVisibleViewTjByBz: false,
      dialogFormVisibleViewTjByCbs: false,
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      tablePage2: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      tableload: false,
      tableload2: false,
      wclSum: "",
      jhlSum: "",
      sylSum: "",
      cslSum: "",
      downTableOption: {
        //称别组
        cbsNam: "cbs",
        cbsColum: [{ field: "CBS", title: "称别组" }],
        cbsreturnApi: [{ field: "CBS", returnName: "cbs" }],

        cbsurl: "/transport/hpmx/getCbsDoc",
        //船编号
        cbhNam: "cbh",
        cbhColum: [
          { field: "CBH", title: "船编号" },
          { field: "CM", title: "船名" },
        ],
        cbhreturnApi: [
          { field: "CBH", returnName: "cbh" },
          { field: "CM", returnName: "cm" },
        ],

        cbhurl: "/transport/hpmx/getCbhDoc",
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
      this.Sumwcl();
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
          this.tableload = false;
        })
        .catch(() => {
          this.tableload = false;
        });
    },

    footerMethod({ columns, data }) {
      const footerData = [
        columns.map((column, _columnIndex) => {
          if (_columnIndex === 1) {
            return "合计";
          }
          if (["wcl"].includes(column.property)) {
            return this.wclSum;
          }

          if (["jhl"].includes(column.property)) {
            return this.jhlSum;
          }
          if (["syl"].includes(column.property)) {
            return this.sylSum;
          }
          if (["csl"].includes(column.property)) {
            return this.cslSum;
          }
          return null;
        }),
      ];
      return footerData;
    },

    Sumwcl() {
      getSumwcl(Object.assign(this.formObj))
        .then((response) => {
          this.wclSum = response.data.data.wclSum;
          this.jhlSum = response.data.data.jhlSum;
          this.sylSum = response.data.data.sylSum;
          this.cslSum = response.data.data.cslSum;
        })
        .catch(() => {});
    },
    cellDBLClickEvent({ row }) {
      this.dateNow = row;
      this.dialogFormVisibleView = true;
    },
    viewCont() {
      this.tablePage2.currentPage = 1;
      if (this.dataHeadNow != null && this.dataHeadNow.bh != null) {
        this.dateNow = this.dataHeadNow;
        this.dialogFormVisibleView = true;
        this.getDate2();
      } else {
        this.$message.error("请选择一个进行查看");
      }
    },
    getDate2() {
      this.tableload2 = true;
      getStabc(
        Object.assign({
          current: this.tablePage2.currentPage,
          size: this.tablePage2.pageSize,
          bh: this.dataHeadNow.bh,
        })
      )
        .then(
          (response) => {
            this.tableData2 = response.data.data.records;
            this.tablePage2.total = response.data.data.total;
            this.tableload2 = false;
          },
          (this.formT.cbhh = this.dataHeadNow.cbh),
          (this.formT.cmm = this.dataHeadNow.cm),
          console.log("cbhh==" + this.formT.cbhh),
          console.log("cmm==" + this.formT.cmm)
        )
        .catch(() => {
          this.tableload2 = false;
        });
    },
    add(type) {},
    dictItemVisible() {
      this.dialogFormVisibleViewTjByCargo = false;
      this.dialogFormVisibleViewTjByUnit = false;
      this.dialogFormVisibleViewTjByDep = false;
      this.dialogFormVisibleViewTjByBz = false;
      this.dialogFormVisibleViewTjByCbs = false;
    },
    hlCont() {
      this.dialogFormVisibleViewTjByCargo = true;
    },
    sqdwCont() {
      this.dialogFormVisibleViewTjByUnit = true;
    },
    depCont() {
      this.dialogFormVisibleViewTjByDep = true;
    },
    BzCont() {
      this.dialogFormVisibleViewTjByBz = true;
    },
    CbsCont() {
      this.dialogFormVisibleViewTjByCbs = true;
    },
    rowclick({ row }) {
      this.dataHeadNow = row;
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    handlePageChange2({ currentPage, pageSize }) {
      this.tablePage2.currentPage = currentPage;
      this.tablePage2.pageSize = pageSize;
      this.getDate2();
    },

    formatterBpwc({ cellValue }) {
      if (cellValue == 1) {
        return "已完成";
      } else {
        return "未完成";
      }
    },

    formatterZt({ row }) {
      if ((row.zffs = 1)) {
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
      } else if (row.zffs == 0 && row.fcId == "00") {
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
      } else if (cellValue == "00") {
        return "线下";
      }
    },
  },
};
</script>
<style lang="scss" scoped>
.transport-base-index {
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
.grid-content {
  margin: 10px auto;
}
</style>>