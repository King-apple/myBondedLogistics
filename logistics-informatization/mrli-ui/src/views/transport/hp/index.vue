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
  <div class="transport-hp-index">
    <basic-container>
      <!--查询区-->
      <vxe-form :data="formObj" size="mini">
        <vxe-form-item title="货票信息" field="hpxx" size="mini" span="3">
          <vxe-select v-model="formObj.hpxx" size="mini">
            <vxe-option value="A" label="当前货票"></vxe-option>
            <vxe-option value="B" label="历史货票"></vxe-option>
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
        <vxe-form-item title="货名" field="hm" size="mini" span="4">
          <EditDown
            :objs="formObj"
            :property="downTableOption.hmNam"
            :tableColumns="downTableOption.hmColum"
            :urlapi="downTableOption.hmurl"
            :returnApi="downTableOption.hmreturnApi"
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
      </vxe-form>
      <!--功能按钮-->
      <vxe-toolbar perfect size="mini">
        <template v-slot:buttons>
          <vxe-button type="text" class="fa fa-bar-chart" @click="viewCont()"
            >-工班统计</vxe-button
          >
          <vxe-button type="text" class="fa fa-bar-chart" @click="hzWeekTj()">-衡重量周统计</vxe-button>
           <vxe-button type="text" class="fa fa-bar-chart" @click="hzAllTj()"
            >-衡重量多功能统计报表</vxe-button
          >
          <vxe-button type="text" class="fa fa-bar-chart" @click="HpCont()"
            >-生产系统货票查询</vxe-button
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
          width="110"
        ></vxe-table-column>
        <vxe-table-column
          field="wcl"
          title="完成量"
          width="110"
        ></vxe-table-column>
        <vxe-table-column
          field="syl"
          title="剩余量"
          width="110"
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
          width="180"
        ></vxe-table-column>
        <vxe-table-column
          field="lasttiem"
          title="货票完成时间"
          width="140"
        ></vxe-table-column>
        <vxe-table-column
          field="gbId"
          title="上传复核"
          :formatter="formatterGb_id"
          width="80"
        ></vxe-table-column>
        <vxe-table-column
          field="gbTim"
          title="上传时间"
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
        title="工班统计"
        width="900"
        height="700"
        resize
        storage
      >
        <el-row>
          <el-col :span="12"
            ><div class="grid-content">货票号：{{ formT.bh }}</div></el-col
          >
          <el-col :span="12"
            ><div class="grid-content">提单号：{{ formT.tdh }}</div></el-col
          >
        </el-row>
        <el-row>
          <div>
            <div class="gridleft">
              <el-button size="medium" @click="upBbdy()">增加备注</el-button>
            </div>
            <div class="gridright">
              <el-button size="medium" @click="upGbId()" v-if="commitIf"
                >确认并传给复核</el-button
              >
            </div>
          </div>
        </el-row>

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
          @cell-click="rowclick2"
        >
          <vxe-table-column
            type="seq"
            width="50"
            title="序号"
          ></vxe-table-column>
          <vxe-table-column
            field="rqcbs"
            title="日期称别组"
            width="180"
          ></vxe-table-column>
          <vxe-table-column
            field="csl"
            title="车数量"
            width="90"
          ></vxe-table-column>
          <vxe-table-column
            field="hz"
            title="货重"
            width="90"
          ></vxe-table-column>
          <vxe-table-column
            field="zcsl"
            title="累计车数"
            width="90"
          ></vxe-table-column>
          <vxe-table-column
            field="wcl"
            title="累计货重"
            width="90"
          ></vxe-table-column>
          <vxe-table-column
            field="bbdy"
            title="备注"
            width="220"
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
        v-if="dialogFormVisibleView2"
        v-model="dialogFormVisibleView2"
        title="增加备注"
        width="560"
        height="350"
        resize
        storage
      >
        <el-row>
          <el-col :span="12"
            ><div class="grid-content">
              货票号：{{ dataHeadNow2.bh }}
            </div></el-col
          >
          <el-col :span="12"
            ><div class="grid-content">
              日期班组：{{ dataHeadNow2.rqcbs }}
            </div></el-col
          >
        </el-row>

        <vxe-form :data="formBbdy" size="mini">
          <el-row>
            <el-col :span="12"><div>备注：</div></el-col>
          </el-row>
          <vxe-textarea
            field="bbdy"
            v-model="dataHeadNow2.bbdy"
            placeholder="备注"
            :autosize="{ minRows: 6, maxRows: 8 }"
          >
          </vxe-textarea>
          <vxe-button status="primary" icon="fa fa-save" @click="upBbdySt()"
            >保存</vxe-button
          >
        </vxe-form>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleViewHzWeekTj"
        v-model="dialogFormVisibleViewHzWeekTj"
        title="衡重量周统计报表"
        width="980"
        height="480"
        resize
        storage
      >
      <HzWeekTj
          @dictItemVisible="dictItemVisible"
        ></HzWeekTj>
       </vxe-modal>

     <vxe-modal
        v-if="dialogFormVisibleViewHzAllTj"
        v-model="dialogFormVisibleViewHzAllTj"
        title="衡重量多功能统计报表"
        width="980"
        height="500"
        resize
        storage
      >
        <HzAllTj @dictItemVisible="dictItemVisible"></HzAllTj>
      </vxe-modal>

      <vxe-modal
        v-if="dialogFormVisibleViewTjByHp"
        v-model="dialogFormVisibleViewTjByHp"
        title="生产系统货票查询"
        width="1600"
        height="900"
        resize
        storage
      >
        <TjbyHp @dictItemVisible="dictItemVisible"></TjbyHp>
      </vxe-modal>

    </basic-container>
  </div>
</template>

<script>
import {
  fetchListhp,
  getStahz,
  upBbdySave,
  upGbIdSave,
} from "@/api/transport/hpmx";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import EditDown from "../../baseUtile/EditDown";
import TjbyHp from "./tjByHp";
import HzAllTj from "./hzAllTj";
import HzWeekTj from './hzWeekTj'
export default {
  components: { EditDown,TjbyHp,HzWeekTj,HzAllTj},
  name: "transport-hp-index",
  data() {
    return {
      formObj: { cbs: null, firstCod: null, hpxx: "A", hm: null },
      formT: {},
      formBbdy: {},
      tableData: [],
      tableData2: [],
      dateNow: {},
      dataHeadNow: {},
      dataHeadNow2: {},
      dialogFormVisibleView: false,
      dialogFormVisibleView2: false,
      dialogFormVisibleViewHzWeekTj: false,
      dialogFormVisibleViewHzAllTj: false,
      dialogFormVisibleViewTjByUnit: false,
      dialogFormVisibleViewTjByDep: false,
      dialogFormVisibleViewTjByHp: false,
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
      tableload3: false,
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
        //货名
        hmNam: "hm",
        hmColum: [
          { field: "CARGO_NAM_COD", title: "货名代码" },
          { field: "CARGO_NAM", title: "货名" },
        ],
        hmreturnApi: [{ field: "CARGO_NAM", returnName: "hm" }],
        hmurl: "/basecode/basecod/getCargoNamAllPage",
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
      commitIf: true,
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
      fetchListhp(
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

    cellDBLClickEvent({ row }) {
      this.dateNow = row;
      this.dialogFormVisibleView = true;
    },

    upGbId() {
      if (this.dataHeadNow.bh != null && this.dataHeadNow.bpwc == "1") {
        console.log("upGbId()bh:" + this.dataHeadNow.bh);
        console.log("upGbId()bpw;c:" + this.dataHeadNow.bpwc);

        this.upGbIdSt();
      } else {
        this.$message.error("该货票可能未完成");
      }
    },
    upGbIdSt() {
      let loadingInstance = Loading.service({
        fullscreen: true,
        text: "正在上传………",
      });
      upGbIdSave(
        Object.assign({
          current: this.tablePage2.currentPage,
          size: this.tablePage2.pageSize,
          bh: this.formT.bh,
        })
      )
        .then((data) => {
          this.$message.success("货票：" + this.formT.bh + " 上传复核成功");
          loadingInstance.close();
          this.getDate2();
          this.getDate();
          this.dialogFormVisibleView1 = false;
          this.dialogFormVisibleView = false;
        })
        .catch(() => {
          loadingInstance.close();
        });
    },

    upBbdy() {
      if (this.dataHeadNow2 != null && this.dataHeadNow2.rqcbs != null) {
        this.dialogFormVisibleView2 = true;
        console.log("rqcbsq:" + this.dataHeadNow2.rqcbsq);
      } else {
        this.$message.error("请选择一个进行查看");
      }
    },
    upBbdySt() {
      let loadingInstance = Loading.service({
        fullscreen: true,
        text: "正在保存………",
      });
      upBbdySave(
        Object.assign({
          current: this.tablePage2.currentPage,
          size: this.tablePage2.pageSize,
          bh: this.dataHeadNow2.bh,
          rqcbsq: this.dataHeadNow2.rqcbsq,
          bbdy: this.dataHeadNow2.bbdy,
        })
      )
        .then((data) => {
          this.$message.success("保存成功");
          loadingInstance.close();
          this.getDate2();
          this.dialogFormVisibleView2 = false;
        })
        .catch(() => {
          loadingInstance.close();
        });
    },
    viewCont() {
      this.tablePage2.currentPage = 1;
      if (this.dataHeadNow != null && this.dataHeadNow.bh != null) {
        this.dateNow = this.dataHeadNow;
        if (this.dataHeadNow.gbId == "1") {
          this.commitIf = false;
        } else {
          this.commitIf = true;
        }
        this.dataHeadNow2 = null;
        this.getDate2();
        this.dialogFormVisibleView = true;
      } else {
        this.$message.error("请选择一个进行查看");
      }
    },
    getDate2() {
      this.tableload2 = true;
      getStahz(
        Object.assign({
          current: this.tablePage2.currentPage,
          size: this.tablePage2.pageSize,
          bh: this.dataHeadNow.bh,
        })
      )
        .then((response) => {
          this.tableData2 = response.data.data.records;
          this.tablePage2.total = response.data.data.total;
          this.tableload2 = false;
          (this.formT.bh = this.dataHeadNow.bh),
            (this.formT.tdh = this.dataHeadNow.tdh),
            console.log("bh==" + this.formT.bh),
            console.log("getStahz()bpwc==" + this.dataHeadNow.bpwc);
        })
        .catch(() => {
          this.tableload2 = false;
        });
    },
    add(type) {},
    dictItemVisible() {
      this.dialogFormVisibleViewTjByCargo = false;
      this.dialogFormVisibleViewTjByUnit = false;
      this.dialogFormVisibleViewTjByDep = false;
      this.dialogFormVisibleViewHzWeekTj=false;
      this.dialogFormVisibleViewHzAllTj = false;
      this.dialogFormVisibleViewTjByHp = false;
    },
    hzWeekTj(){
      this.dialogFormVisibleViewHzWeekTj=true;
    },
    hzAllTj(){
      this.dialogFormVisibleViewHzAllTj=true;
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
    HpCont() {
      this.dialogFormVisibleViewTjByHp = true;
    },
    rowclick({ row }) {
      this.dataHeadNow = row;
    },
    rowclick2({ row }) {
      this.dataHeadNow2 = row;
    },
    handlePageChange({ currentPage, pageSize }) {
      this.dataHeadNow = null;
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    handlePageChange2({ currentPage, pageSize }) {
      this.tablePage2.currentPage = currentPage;
      this.tablePage2.pageSize = pageSize;
      this.getDate2();
    },

    formatterGb_id({ cellValue }) {
      console.log(cellValue);
      if (cellValue == "1") {
        return "已上传";
      }
      if (cellValue == "0") {
        return "未上传";
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
.transport-hp-index {
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
.gridleft {
  float: left;
  margin: 0px auto 10px;
}
.gridright {
  float: right;
  margin: 0px auto 10px;
}
</style>>