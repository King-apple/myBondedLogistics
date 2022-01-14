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
  <div class="cdchipgrpt">
    <basic-container>
      <vxe-form
        :data="searchForm"
        @submit="searchEvent"
        @reset="resetEvent"
        size="mini"
      >
        <vxe-form-item
          title="船号[船代]："
          field="shipNoS"
          size="mini"
          span="4"
        >
          <vxe-input v-model="searchForm.shipNoS" clearable></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="船号[集团]：" field="shipNo" size="mini" span="4">
          <vxe-input v-model="searchForm.shipNo" clearable></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="船名：" field="shipNam" size="mini" span="4">
          <EditDown
            :objs="searchForm"
            :property="downTableOption.shipDataname"
            :tableColumns="downTableOption.shipDataColum"
            :urlapi="downTableOption.shipDataurl"
            :returnApi="downTableOption.shipDatareturnApi"
            sizenow="mini"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item title="内外贸：" field="tradeId" size="mini" span="4">
          <vxe-select v-model="searchForm.tradeId" clearable>
            <vxe-option value="1" label="内贸"></vxe-option>
            <vxe-option value="0" label="外贸"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item title="进出口：" field="flag00" size="mini" span="4">
          <vxe-select v-model="searchForm.flag00" clearable>
            <vxe-option value="1" label="进口"></vxe-option>
            <vxe-option value="0" label="出口"></vxe-option>
            <vxe-option value="2" label="进出口"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item title="预报状态：" field="zt" size="mini" span="4">
          <vxe-select v-model="searchForm.zt" clearable>
            <vxe-option value="6" label="待审核"></vxe-option>
            <vxe-option value="7" label="审核不通过"></vxe-option>
            <vxe-option value="8" label="待上报集团"></vxe-option>
            <vxe-option value="1" label="已上报集团"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item title="预报时间：" field="ybtime" size="mini" span="9">
          <el-date-picker
            v-model="searchForm.ybtime"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
          ></el-date-picker>
        </vxe-form-item>
        <vxe-form-item title="离港时间：" field="lgTime" size="mini" span="9">
          <el-date-picker
            v-model="searchForm.lgTime"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
          ></el-date-picker>
        </vxe-form-item>
        <vxe-form-item title="支付状态：" field="zfzt" size="mini" span="6">
          <vxe-select v-model="searchForm.zfzt" clearable>
            <vxe-option value="0" label="线下支付"></vxe-option>
            <vxe-option value="1" label="网转线下"></vxe-option>
            <vxe-option value="2" label="已支付（已签章）"></vxe-option>
            <vxe-option value="40" label="已支付（未签章）"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item title="结算状态：" field="jszt" size="mini" span="4">
          <vxe-select v-model="searchForm.jszt" clearable>
            <vxe-option value="0" label="未生成"></vxe-option>
            <vxe-option value="1" label="待上报"></vxe-option>
            <vxe-option value="2" label="待审核"></vxe-option>
            <vxe-option value="3" label="已审核"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item
          title="客户名称："
          field="agentNam_02"
          size="mini"
          span="6"
        >
          <EditDown
            :objs="searchForm"
            :property="downTableOption.agentname"
            :tableColumns="downTableOption.agentColum"
            :urlapi="downTableOption.agenturl"
            :returnApi="downTableOption.agentreturnApi"
            sizenow="mini"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item span="10" size="mini" align="center">
          <vxe-button type="submit" status="primary" icon="fa fa-search"
            >查询</vxe-button
          >
          <vxe-button type="reset" icon="fa fa-repeat">重置</vxe-button>
        </vxe-form-item>
      </vxe-form>
      <el-divider></el-divider>
      <avue-crud
        ref="crud"
        :page="page"
        :data="tableData"
        :table-loading="tableLoading"
        :option="tableOption"
        @on-load="getList"
        @search-change="searchChange"
        @refresh-change="refreshChange"
        @size-change="sizeChange"
        @current-change="currentChange"
        @row-update="handleUpdate"
        @row-save="handleSave"
        @row-del="rowDel"
      >
        <template slot="tradeId" slot-scope="scope">
          <span v-if="scope.row.tradeId == '0'">外贸</span>
          <span v-if="scope.row.tradeId == '1'">内贸</span>
        </template>
        <template slot="zt" slot-scope="scope">
          <template
            v-if="
              scope.row.tradeId == '1' &&
              scope.row.inCargoCod != 'JZX' &&
              scope.row.outCargoCod != 'JZX'
            "
          >
            <span v-if="scope.row.zt == 6">
              <font color="green">待审核</font>
            </span>
            <span v-if="scope.row.zt == 7">
              <font color="green">审核不通过</font>
            </span>
            <span v-if="scope.row.zt == 8">
              <font color="green">待上报集团</font>
            </span>
            <span v-if="scope.row.zt == 1">
              <font color="red">已上报集团</font>
            </span>
          </template>
          <template
            v-if="
              scope.row.tradeId == '0' ||
              scope.row.inCargoCod == 'JZX' ||
              scope.row.outCargoCod == 'JZX'
            "
          >
            <span v-if="scope.row.shipNo == null">
              <font color="green">待上报集团</font>
            </span>
            <span v-if="scope.row.shipNo != null">
              <font color="red">已上报集团</font>
            </span>
          </template>
        </template>
        <template slot="fapiaoLei" slot-scope="scope">
          <span v-if="scope.row.fapiaoLei == 1">增值税专用发票</span>
          <span v-if="scope.row.fapiaoLei == 2">增值税普通发票</span>
          <span v-if="scope.row.fapiaoLei == 3">通用机打发票</span>
        </template>
        <template slot="zfzt" slot-scope="scope">
          <span v-if="scope.row.zfzt == 0">
            <font color="blue">线下支付</font>
          </span>
          <span
            v-if="
              scope.row.zfzt == 10 ||
              scope.row.zfzt == 20 ||
              scope.row.zfzt == 30
            "
          >
            <font color="green">待支付</font>
          </span>
          <span v-if="scope.row.zfzt == 1">
            <font color="blue">网转现下</font>
          </span>
          <span v-if="scope.row.zfzt == 2">
            <font color="red">已支付（已签章）</font>
          </span>
          <span v-if="scope.row.zfzt == 40">
            <font color="blue">已支付（未签章）</font>
          </span>
        </template>
        <template slot="flag00" slot-scope="scope">
          <span v-if="scope.row.flag00 == 0">出口</span>
          <span v-if="scope.row.flag00 == 1">进口</span>
          <span v-if="scope.row.flag00 == 2">进出口</span>
        </template>
        <template slot="dsjBegin" slot-scope="scope">
          <span v-if="scope.row.feeBillNoS == 0">
            <font color="green">未生成</font>
          </span>
          <span v-if="scope.row.feeBillNoS != 0">
            <span v-if="scope.row.dsjBegin == null">
              <font color="blue">待上报</font>
            </span>
            <span v-if="scope.row.dsjBegin == '1'">
              <span v-if="scope.row.dsj == '1'">
                <font color="red">已审核</font>
              </span>
              <span v-if="scope.row.dsj == null">
                <font color="red">待审核</font>
              </span>
              <span v-if="scope.row.dsj == '0'">
                <font color="red">待审核</font>
              </span>
            </span>
            <span v-if="scope.row.dsjBegin == '0'">
              <font color="blue">未上报</font>
            </span>
          </span>
        </template>
        <template slot="unloadId" slot-scope="scope">
          <span
            v-if="scope.row.inCargoNam != null && scope.row.outCargoNam != null"
            >装/卸</span
          >
          <span
            v-if="scope.row.inCargoNam != null && scope.row.outCargoNam == null"
            >卸</span
          >
          <span
            v-if="scope.row.inCargoNam == null && scope.row.outCargoNam != null"
            >装</span
          >
        </template>
        <template slot-scope="{ row, type, size }" slot="menu">
          <el-dropdown trigger="click">
            <span class="el-dropdown-link">
              操作
              <i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item
                v-if="yfBtn"
                icon="el-icon-ship"
                @click.native="bj(row)"
                divided
                >预付款</el-dropdown-item
              >
              <el-dropdown-item
                v-if="row.feeBillNoS != 0 && jsBtn"
                icon="el-icon-ship"
                @click.native="js(row)"
                divided
                >结算</el-dropdown-item
              >
              <el-dropdown-item
                v-if="row.zt == 8 && sbBtn"
                icon="fa fa-arrow-up"
                @click.native="commitShiptoJt(row)"
                divided
                >上报</el-dropdown-item
              >
              <el-dropdown-item
                v-if="row.zt == 1 && jjBtn"
                icon="fa fa-arrow-up"
                @click.native="openJjd(row)"
                divided
                >船舶交接单</el-dropdown-item
              >
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </avue-crud>
      <vxe-modal
        v-if="dialogFormVisible"
        v-model="dialogFormVisible"
        id="myModal6"
        title="报价列表"
        width="1200"
        height="800"
        min-width="1100"
        min-height="800"
        resize
        storage
        transfer
      >
        <FeeList
          :objrow="objrow"
          :fromMethod="fromMethod"
          @dictItemVisible="dictItemVisible"
          @selectList="refreshChange"
        ></FeeList>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisiblejs"
        v-model="dialogFormVisiblejs"
        id="myModal6"
        title="结算信息"
        width="1200"
        height="800"
        min-width="1100"
        min-height="800"
        resize
        storage
        transfer
      >
        <JsList
          :objrow="objrow"
          @dictItemVisible="dictItemVisible"
          @selectList="refreshChange"
        ></JsList>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisiblejj"
        v-model="dialogFormVisiblejj"
        id="myModal6"
        title="船舶交接单"
        width="1200"
        height="800"
        min-width="1100"
        min-height="800"
        resize
        storage
        fullscreen
      >
        <ShipJjList
          :objrow="objrow"
          @dictItemVisible="dictItemVisible"
          @selectList="refreshChange"
        ></ShipJjList>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import {
  fetchList,
  getObj,
  addObj,
  putObj,
  commitShip,
} from "@/api/ship/cdshipgrpt";
import { tableOption } from "@/const/crud/ship/cdshipgrpt";
import { mapGetters } from "vuex";
import Feemain from "./feemain";
import FeeList from "./feeList";
import JsList from "./jsList";
import ShipJjList from "./shipJjList";
import EditDown from "../../baseUtile/EditDown";
export default {
  components: { Feemain, FeeList, JsList, EditDown, ShipJjList },
  name: "cdshipgrpt",
  data() {
    return {
      searchForm: {
        shipNoS: null,
        shipNo: null,
        agentNam_02: null,
        shipNam: null,
        ybtime: [],
        lgTime: [],
        tradeId: "",
        flag00: "",
        zt: "",
        zfzt: "",
        jszt: "",
      },
      downTableOption: {
        //费目下拉菜单展示项
        agentColum: [
          { field: "AGENT_COD", title: "客户代码" },
          { field: "AGENT_NAM", title: "客户名称" },
        ],
        //费目下拉菜单的返回项
        agentreturnApi: [{ field: "AGENT_NAM", returnName: "agentNam_02" }],
        //费目下拉菜单的路径
        agenturl: "/ship/baseCod/agent/page",
        agentname: "agentNam_02",
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
      },
      tableData: [],
      objrow: {},
      fromMethod: null,
      value10: false,
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      tableLoading: false,
      tableOption: tableOption,
      dialogFormVisible: false,
      dialogFormVisiblejs: false,
      dialogFormVisiblejj: false,
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  created() {
    this.yfBtn = this.permissions["SHIP_GRPT_YF_SELECT"];
    this.jsBtn = this.permissions["SHIP_GRPT_JS_SELECT"];
    this.jjBtn = this.permissions["SHIP_GRPT_JE_SELECT"];
    this.sbBtn = this.permissions["SHIP_GRPT_COMMIT"];
  },
  methods: {
    getList(page, params) {
      this.tableLoading = true;
      fetchList(
        Object.assign(
          {
            current: page.currentPage,
            size: page.pageSize,
          },
          params,
          this.searchForm
        )
      )
        .then((response) => {
          this.tableData = response.data.data.records;
          this.page.total = response.data.data.total;
          this.tableLoading = false;
        })
        .catch(() => {
          this.tableLoading = false;
        });
    },
    searchEvent() {
      this.page = {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      };
      this.getList(this.page);
    },
    resetEvent() {},
    rowDel: function (row, index) {
      this.$confirm("是否确认删除ID为" + row.shipNoS, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delObj(row.shipNoS);
        })
        .then((data) => {
          this.$message.success("删除成功");
          this.getList(this.page);
        });
    },
    handleUpdate: function (row, index, done, loading) {
      putObj(row)
        .then((data) => {
          this.$message.success("修改成功");
          done();
          this.getList(this.page);
        })
        .catch(() => {
          loading();
        });
    },
    commitShiptoJt(row) {
      this.$confirm("是否确认上报" + row.shipNam + "到集团", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return commitShip(row.shipNoS);
        })
        .then((data) => {
          this.$message.success("上报成功");
          this.getList(this.page);
        });
    },
    handleSave: function (row, done, loading) {
      addObj(row)
        .then((data) => {
          this.$message.success("添加成功");
          done();
          this.getList(this.page);
        })
        .catch(() => {
          loading();
        });
    },
    dictItemVisible: function () {
      this.objrow = {};
      this.dialogFormVisible = false;
      this.dialogFormVisiblejs = false;
      this.dialogFormVisiblejj = false;
    },
    bj(row) {
      this.objrow = row;
      this.dialogFormVisible = true;
    },
    js(row) {
      this.objrow = row;
      this.dialogFormVisiblejs = true;
    },
    sizeChange(pageSize) {
      this.page.pageSize = pageSize;
    },
    openJjd(row) {
      this.dialogFormVisiblejj = true;
      this.objrow = row;
    },
    currentChange(current) {
      this.page.currentPage = current;
    },
    searchChange(form, done) {
      this.searchForm = form;
      this.getList(this.page, form);
      done();
    },
    refreshChange() {
      this.getList(this.page);
    },
  },
};
</script>
<style >
.cdchipgrpt .el-range-editor.el-input__inner {
  border-radius: 0.25em;
  outline: 0;
  padding: 0 0.6em;
  color: #606266;
  border: 1px solid #dcdfe6;
  background-color: #fff;
  width: 100%;
  height: 100%;
  box-shadow: none;
}
.cdchipgrpt .el-dropdown-link {
  cursor: pointer;
  color: #409eff;
}
.cdchipgrpt .el-icon-arrow-down {
  font-size: 12px;
}
.cdchipgrpt .demonstration {
  display: block;
  color: #8492a6;
  font-size: 14px;
  margin-bottom: 20px;
}
</style>
