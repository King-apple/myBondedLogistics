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
  <div class="businessContView">
    <el-divider content-position="left">
      <strong>合同信息</strong>
    </el-divider>
    <vxe-form :data="dataForm" size="mini" ref="editHeadForm" :rules="formRules">
      <vxe-form-item title="甲方" field="firstNam" size="mini" span="5">
        <vxe-input v-model="dataForm.firstNam" :disabled="disabledCont"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="结算单位" field="settlementUnitNam" size="mini" span="5">
        <vxe-input v-model="dataForm.settlementUnitNam" :disabled="disabledCont"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="发票名头" field="invoiceAgainstNam" size="mini" span="5">
        <vxe-input v-model="dataForm.invoiceAgainstNam" :disabled="disabledCont"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="乙方" field="secondNam" size="mini" span="5">
        <vxe-input v-model="dataForm.secondNam" :disabled="disabledCont"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="作业单位" field="unitNam" size="mini" span="4">
        <vxe-input v-model="dataForm.unitNam" :disabled="disabledCont"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="内外贸" field="tradeId" size="mini" span="3">
        <vxe-select v-model="dataForm.tradeId" :options="tradeList" :disabled="disabledCont"></vxe-select>
      </vxe-form-item>
      <vxe-form-item title="进出口" field="inoutId" size="mini" span="3">
        <vxe-select v-model="dataForm.inoutId" :options="inoutList" :disabled="disabledCont"></vxe-select>
      </vxe-form-item>
      <vxe-form-item title="协议标志" field="prepayId" size="mini" span="3">
        <vxe-select v-model="dataForm.prepayId" :options="prepayIdList" :disabled="disabledCont"></vxe-select>
      </vxe-form-item>
      <vxe-form-item title="支付方式" field="zffs" size="mini" span="3">
        <vxe-select v-model="dataForm.zffs" :options="zffsList" :disabled="disabledCont"></vxe-select>
      </vxe-form-item>
      <vxe-form-item title="线上支付方式" field="onlinePayType" size="mini" span="3">
        <vxe-select
          v-model="dataForm.onlinePayType"
          :options="onlinePayList"
          :disabled="disabledCont"
        ></vxe-select>
      </vxe-form-item>
      <vxe-form-item title="线上签章" field="fcId" size="mini" span="3">
        <vxe-select v-model="dataForm.fcId" :options="fcIdList" :disabled="disabledCont"></vxe-select>
      </vxe-form-item>
      <vxe-form-item title="水尺" field="csid" size="mini" span="3">
        <vxe-select v-model="dataForm.csid" :options="csidList" :disabled="disabledCont"></vxe-select>
      </vxe-form-item>
      <vxe-form-item title="合同类型" field="tkType" size="mini" span="3">
        <vxe-select v-model="dataForm.tkType" :options="tkTypeList" :disabled="disabledCont"></vxe-select>
      </vxe-form-item>
    </vxe-form>
    <el-divider content-position="left">
      <strong>船舶信息</strong>
    </el-divider>
    <vxe-form :data="dataForm" size="mini">
      <vxe-form-item title="船名" field="shipNam" size="mini" span="5">
        <vxe-input v-model="dataForm.shipNam" :disabled="disabledCont"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="船主" field="shipOwnerNam" size="mini" span="5">
        <vxe-input v-model="dataForm.shipOwnerNam" :disabled="disabledCont"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="派船方式" field="sendShipCod" size="mini" span="5">
        <vxe-select
          v-model="dataForm.sendShipCod"
          :options="sendShipCodList"
          :disabled="disabledCont"
        ></vxe-select>
      </vxe-form-item>
      <vxe-form-item title="到港时间(预)" field="toPortTim" size="mini" span="5">
        <vxe-input v-model="dataForm.toPortTim" :disabled="disabledCont"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="来往港" field="cPortNam" size="mini" span="4">
        <vxe-input v-model="dataForm.cPortNam" :disabled="disabledCont"></vxe-input>
      </vxe-form-item>
    </vxe-form>
    <el-divider content-position="left">
      <strong>双方信息</strong>
    </el-divider>
    <vxe-form :data="dataForm" size="mini">
      <vxe-form-item title="甲方经办人" field="firstMan" size="mini" span="5">
        <vxe-input v-model="dataForm.firstMan" :disabled="disabledCont"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="甲方联系方式" field="firstPhone" size="mini" span="5">
        <vxe-input v-model="dataForm.firstPhone" :disabled="disabledCont"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="甲方传真" field="firstFax" size="mini" span="5">
        <vxe-input v-model="dataForm.firstFax" :disabled="disabledCont"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="甲方开户行" field="firstBankNam" size="mini" span="5">
        <vxe-input v-model="dataForm.firstBankNam" :disabled="disabledCont"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="甲方账号" field="firstAcntNo" size="mini" span="4">
        <vxe-input v-model="dataForm.firstAcntNo" :disabled="disabledCont"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="乙方经办人" field="secondMan" size="mini" span="5">
        <vxe-input v-model="dataForm.secondMan" :disabled="disabledCont"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="乙方联系方式" field="secondPhone" size="mini" span="5">
        <vxe-input v-model="dataForm.secondPhone" :disabled="disabledCont"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="乙方传真" field="secondFax" size="mini" span="5">
        <vxe-input v-model="dataForm.secondFax" :disabled="disabledCont"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="乙方开户行" field="secondBankNam" size="mini" span="5">
        <vxe-input v-model="dataForm.secondBankNam" :disabled="disabledCont"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="乙方账号" field="secondAcntNo" size="mini" span="4">
        <vxe-input v-model="dataForm.secondAcntNo" :disabled="disabledCont"></vxe-input>
      </vxe-form-item>
    </vxe-form>
    <!--<el-divider content-position="left">
      <strong>其他信息</strong>
    </el-divider>
    <vxe-form :data="dataForm" size="mini">
      <vxe-form-item title="货物出入港开始时间：" field="date2" size="mini" span="4">
        <vxe-input v-model="dataForm.date2"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="货物出入港结束时间：" field="date3" size="mini" span="4">
        <vxe-input v-model="dataForm.date3"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="限定提完货物天数在" field="limitDay" size="mini" span="4">
        <vxe-input v-model="dataForm.limitDay"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="货物在港内免费堆存" field="storeDay" size="mini" span="3">
        <vxe-input v-model="dataForm.storeDay"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="超期堆存费" field="exceedTimeNum" size="mini" span="3">
        <vxe-input v-model="dataForm.exceedTimeNum"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="本合同副本份数" field="contNum" size="mini" span="3">
        <vxe-input v-model="dataForm.contNum"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="是否老港" field="portMny" size="mini" span="3">
        <vxe-select v-model="dataForm.portMny" :options="portMnyList"></vxe-select>
      </vxe-form-item>
    </vxe-form>-->
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="堆存信息" name="first">
        <vxe-form :data="dataForm" size="mini">
          <vxe-form-item title="堆存费" field="dcAmt" size="mini" span="4">
            <vxe-input
              v-model="dataForm.dcAmt"
              @input="inputSpec"
              type="number"
              :disabled="disabledCont"
            ></vxe-input>
          </vxe-form-item>
          <vxe-form-item title="预付款" field="advanceNum" size="mini" span="4">
            <vxe-input
              v-model="dataForm.advanceNum"
              :readonly="advanceNumReadonly"
              :disabled="disabledCont"
            ></vxe-input>
          </vxe-form-item>
        </vxe-form>
        <el-row :gutter="15">
          <el-col :span="13">
            <vxe-table
              border
              show-overflow
              keep-source
              size="mini"
              :data="tableDataCargo"
              :loading="tableloadCargo"
              :round="true"
              align="center"
              height="400px"
              ref="tableCargo"
              @cell-click="rowclick"
            >
              <vxe-table-column type="seq" width="60" title="序号"></vxe-table-column>
              <vxe-table-column field="shipperDoc" title="港口货主" width="100"></vxe-table-column>
              <vxe-table-column field="cargoNam" title="货物" width="100"></vxe-table-column>
              <vxe-table-column field="format" title="规格" width="80"></vxe-table-column>
              <vxe-table-column field="CPkgKindNam" title="包装" width="80"></vxe-table-column>
              <vxe-table-column field="piecesWgt" title="件重" width="80"></vxe-table-column>
              <vxe-table-column field="piecesNum" title="件数" width="80"></vxe-table-column>
              <vxe-table-column field="weightWgt" title="吨重" width="80"></vxe-table-column>
              <vxe-table-column field="cargoVol" title="体积" width="80"></vxe-table-column>
              <vxe-table-column field="priceNum" title="货价" width="80"></vxe-table-column>
              <vxe-table-column field="safetyKndCod" title="险种" width="80"></vxe-table-column>
              <vxe-table-column field="cFeeUnit" title="计量单位" width="80"></vxe-table-column>
            </vxe-table>
          </el-col>
          <el-col :span="11">
            <vxe-table
              border
              show-overflow
              keep-source
              size="mini"
              :data="tableDataAssign"
              :loading="tableloadAssign"
              :round="true"
              align="center"
              height="400px"
              ref="tableAssign"
            >
              <vxe-table-column type="seq" width="60" title="序号"></vxe-table-column>
              <vxe-table-column field="inPortId" title="进出港" width="80"></vxe-table-column>
              <vxe-table-column field="portWay" title="集疏港方式" width="80"></vxe-table-column>
              <vxe-table-column field="carArrangMan" title="运力安排" width="80"></vxe-table-column>
              <vxe-table-column field="weightWgt" title="吨数" width="80"></vxe-table-column>
              <vxe-table-column field="piecesNum" title="件数" width="80"></vxe-table-column>
              <vxe-table-column field="cargoVol" title="体积" width="80"></vxe-table-column>
              <vxe-table-column field="workTechnics" title="作业工艺" width="100"></vxe-table-column>
              <vxe-table-column field="addrNam" title="堆存地点" width="100"></vxe-table-column>
              <vxe-table-column field="feeRat" title="费率" width="80"></vxe-table-column>
            </vxe-table>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="作业附加费" name="second">
        <vxe-form :data="dataForm" size="mini">
          <vxe-form-item title="堆存费" field="dcAmt" size="mini" span="4">
            <vxe-input
              v-model="dataForm.dcAmt"
              @input="inputSpec"
              type="number"
              :disabled="disabledCont"
            ></vxe-input>
          </vxe-form-item>
          <vxe-form-item title="预付款" field="advanceNum" size="mini" span="4">
            <vxe-input
              v-model="dataForm.advanceNum"
              :readonly="advanceNumReadonly"
              :disabled="disabledCont"
            ></vxe-input>
          </vxe-form-item>
        </vxe-form>
        <vxe-table
          border
          show-overflow
          keep-source
          size="mini"
          :data="tableDataScep"
          :loading="tableloadScep"
          :round="true"
          align="center"
          height="400px"
          ref="tableScep"
        >
          <vxe-table-column type="checkbox" width="60"></vxe-table-column>
          <vxe-table-column field="specNo" title="作业代码"></vxe-table-column>
          <vxe-table-column field="specNam" title="作业过程"></vxe-table-column>
          <vxe-table-column field="exFeeCod" title="公开代码"></vxe-table-column>
          <vxe-table-column field="exFeeNam" title="公开费目"></vxe-table-column>
          <vxe-table-column field="feeRat" title="费率"></vxe-table-column>
          <vxe-table-column field="weightWgt" title="吨数"></vxe-table-column>
          <vxe-table-column field="unitName" title="单位"></vxe-table-column>
        </vxe-table>
      </el-tab-pane>
      <el-tab-pane label="合同条款" name="third">
        <el-row :gutter="15">
          <el-col :span="24">
            <el-input
              v-model="dataForm.zyWorkRemark"
              type="textarea"
              :autosize="{ minRows:21, maxRows: 50}"
              :disabled="disabledCont"
            ></el-input>
          </el-col>
        </el-row>
      </el-tab-pane>
    </el-tabs>
    <div align="center">
      <el-button
        type="primary"
        size="mini"
        icon="fa fa-chevron-circle-left"
        @click="backContract"
      >合同收回</el-button>
      <el-button type="primary" size="mini" icon="fa fa-file-text" @click="fileView">文件详情</el-button>
      <el-button type="primary" size="mini" icon="fa fa-print" @click="printContract">线上合同打印</el-button>
      <el-button size="mini" icon="el-icon-close" @click="closeWindows">取消</el-button>
    </div>
    <vxe-modal
      v-if="dialogFormVisibleView"
      v-model="dialogFormVisibleView"
      title="文件详情"
      width="650"
      height="650"
      resize
      storage
    >
      <vxe-table
        border
        show-overflow
        keep-source
        size="mini"
        :data="tableDataFile"
        :loading="tableloadFile"
        :round="true"
        align="center"
        height="550px"
        ref="tableScep"
      >
        <vxe-table-column type="seq" width="50" title="序号"></vxe-table-column>
        <vxe-table-column field="SHIP_BILL_NO" width="100" title="平台清单号"></vxe-table-column>
        <vxe-table-column field="SHIP_NO" width="80" title="船号"></vxe-table-column>
        <vxe-table-column field="FILE_NAME" title="文件名"></vxe-table-column>
        <vxe-table-column title="操作" width="80" show-overflow>
          <template v-slot="{ row }">
            <vxe-button type="text" icon="fa fa-download" @click="downLoadFile(row)">下载</vxe-button>
          </template>
        </vxe-table-column>
      </vxe-table>
    </vxe-modal>
  </div>
</template>

<script>
import {
  dealHead,
  dealAssign,
  dealCargo,
  specTxtList,
  saveAll,
  getHead,
  getCargo,
  getAssign,
  getCspec,
  refreshHead,
  backObj,
  getFile,
  getContUrl,
} from "@/api/business/cont";
import request from "@/router/axios";
import "@/styles/common/edit.scss";
import { mapGetters } from "vuex";
import moment from "moment";
import { mapState } from "vuex";
import Vue from "vue";
import VXETable from "vxe-table";
import hljs from "highlight.js";
import { Loading, Message } from "element-ui";
import { xj, jf } from "@/util/util";

export default {
  props: ["datanow"],
  data() {
    return {
      activeName: "first",
      ajaxOver: false,
      tableDataCargo: [],
      tableDataAssign: [],
      tableDataScep: [],
      tableDataText: [],
      tableDataFile: [],
      tableloadCargo: false,
      tableloadAssign: false,
      tableloadText: false,
      tableloadScep: false,
      tableloadFile: false,
      advanceNumReadonly: true,
      ContUrl: null,
      validRules: {},
      formRules: {
        csid: [{ required: true, message: "请选择水尺" }],
      },
      amtAll: 0,
      weightAll: 0,
      textnum: 1,
      dataForm: {
        plantNo: null,
        shipNo: null,
        shipNam: null,
        inoutId: null,
        tradeId: null,
        toPortTim: null,
        firstNam: "",
        secondNam: "",
      },
      unitNameList: [
        { label: "元/吨天", value: "元/吨天" },
        { label: "元/吨", value: "元/吨" },
        { label: "人/小时", value: "人/小时" },
        { label: "元/条", value: "元/条" },
        { label: "元/立方米", value: "元/立方米" },
      ],
      tkTypeList: [
        { label: "散货条款", value: "01" },
        { label: "大件条款", value: "02" },
        { label: "油品条款", value: "03" },
      ],
      sendShipCodList: [
        { label: "对派", value: "1" },
        { label: "外派", value: "0" },
      ],
      onlinePayList: [
        { label: "现金", value: "01" },
        { label: "电票", value: "02" },
        { label: "现金+电票", value: "03" },
      ],
      prepayIdList: [
        { label: "月结", value: 1 },
        { label: "次结", value: 2 },
      ],
      csidList: [
        { label: "是", value: "1" },
        { label: "否", value: "2" },
      ],
      fcIdList: [
        { label: "线上", value: "01" },
        { label: "线下", value: "00" },
      ],
      inoutList: [
        { label: "进口", value: 1 },
        { label: "出口", value: 0 },
      ],
      tradeList: [
        { label: "内贸", value: 1 },
        { label: "外贸", value: 0 },
      ],
      portMnyList: [
        { label: "是", value: 1 },
        { label: "否", value: 0 },
      ],
      zffsList: [
        { label: "线下", value: 0 },
        { label: "线上", value: 1 },
      ],
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      disabledCont: true,
      dialogFormVisibleView: false,
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
    ...mapState({
      userInfo: (state) => state.user.userInfo,
    }),
  },
  created() {
    this.openContract(this.datanow.fphm);
    this.getText();
  },
  mounted: function () {},
  methods: {
    getText() {
      this.tableloadText = true;
      specTxtList(
        Object.assign({
          current: this.tablePage.currentPage,
          size: this.tablePage.pageSize,
          key: this.dataForm.selecttk,
        })
      )
        .then((response) => {
          this.tableDataText = response.data.data.records;
          this.tablePage.total = response.data.data.total;
          this.tableloadText = false;
        })
        .catch(() => {
          this.tableloadText = false;
        });
    },
    getFiles() {
      this.tableloadFile = true;
      getFile(this.dataForm.fphm)
        .then((response) => {
          this.tableDataFile = response.data.data;
          this.tableloadFile = false;
        })
        .catch(() => {
          this.tableloadFile = false;
        });
    },
    downLoadFile(row) {
      var url = "http://10.168.113.158:15881/shipsystemnew";
      window.open(url + row.FILE_PATH);
    },
    handleSubmit() {
      var type = "add";
      if (this.dataForm.fphm != null) {
        type = "edit";
      }
      this.$refs.editHeadForm
        .validate((valid) => {})
        .then((valid) => {
          let loadingInstance = Loading.service({
            fullscreen: true,
            text: "正在保存………",
          });
          saveAll(
            this.dataForm,
            this.tableDataCargo,
            this.tableDataAssign,
            this.$refs.tableScep.getTableData().fullData,
            type
          )
            .then((data) => {
              this.$message.success("保存成功");
              loadingInstance.close();
              this.$emit("selectListplan");
              this.$emit("dictItemVisible");
            })
            .catch(() => {});
        });
    },
    closeWindows() {
      this.$emit("dictItemVisible");
    },
    backContract() {
      backObj(this.dataForm.fphm)
        .then((response) => {
          this.$message.success("收回成功");
          this.$emit("selectListplan");
          this.$emit("dictItemVisible");
        })
        .catch(() => {});
    },
    fileView() {
      this.dialogFormVisibleView = true;
      this.getFiles();
    },
    printContract() {
      if (this.dataForm.urlFile!= null) {
       // var url='http://www.portx.cn/image/files'
        window.open(this.dataForm.urlFile);
      }else{
        this.$message.error("没有生成线上合同");
      }
    },
    formatterTradeId({ cellValue }) {
      if (cellValue == 1) {
        return "内贸";
      } else if (cellValue == 0) {
        return "外贸";
      }
    },
    rowclick({ row }) {},
    formatterInoutId({ cellValue }) {
      if (cellValue == 1) {
        return "进口";
      } else if (cellValue == 0) {
        return "出口";
      }
    },
    inputSpec() {
      var specAmt = 0;
      var tabledate = this.$refs.tableScep.getTableData().fullData;
      for (var i in tabledate) {
        specAmt = xj(
          specAmt,
          tabledate[i].feeRat * tabledate[i].weightWgt * 1.1
        );
      }
      this.dataForm.advanceNum = xj(
        xj(this.amtAll, this.dataForm.dcAmt),
        specAmt
      );
    },
    deleteScep() {},
    insertText(row) {
      if (this.dataForm.zyWorkRemark == null) {
        this.dataForm.zyWorkRemark = this.textnum + "." + row.MARK_TXT + "\n";
      } else {
        this.dataForm.zyWorkRemark =
          this.dataForm.zyWorkRemark + this.textnum + "." + row.MARK_TXT + "\n";
      }
      this.textnum = this.textnum + 1;
      this.dataForm.txtNum = this.textnum;
    },
    handleClick() {
      this.$refs.tableCargo.reloadData(this.tableDataCargo);
      this.$refs.tableAssign.reloadData(this.tableDataAssign);
      this.$refs.tableScep.reloadData(this.tableDataScep);
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    openContract(fphm) {
      let loadingInstance = Loading.service({
        fullscreen: true,
        text: "正在打开合同………",
      });
      getHead(fphm)
        .then((response) => {
          this.dataForm = response.data.data;
          getCargo(fphm)
            .then((response) => {
              this.tableDataCargo = response.data.data;
            })
            .catch(() => {});
          getAssign(fphm)
            .then((response) => {
              this.tableDataAssign = response.data.data;
              loadingInstance.close();
            })
            .catch(() => {});
          getCspec(fphm)
            .then((response) => {
              this.tableDataScep = response.data.data;
            })
            .catch(() => {});
          getContUrl(fphm)
            .then((response) => {
              this.ContUrl = response.data.data;
            })
            .catch(() => {});
        })
        .catch(() => {});
    },
  },
};
</script>
<style lang="scss" scoped>
.businessContView {
  .el-divider--horizontal {
    margin: 8px 0;
  }
  .vxe-toolbar {
    height: 25px;
  }
}
</style>