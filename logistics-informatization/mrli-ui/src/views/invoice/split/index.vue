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
  <div class="invoice-split-index">
    <basic-container>
      <vxe-form :data="formObj" size="mini" @reset="resetForm">
        <vxe-form-item
          title="时间段："
          field="timebegin"
          size="mini"
          span="5"
        >
          <vxe-input
            v-model="formObj.timebegin"
            placeholder="开始时间"
            type="date"
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="至" field="timeend" size="mini" span="5">
          <vxe-input
            v-model="formObj.timeend"
            placeholder="结束时间"
            type="date"
          ></vxe-input>
        </vxe-form-item>
         <vxe-form-item title="计费单号" field="feeBillNo" size="mini" span="5">
          <vxe-input v-model="formObj.feeBillNo" clearable size="mini"></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="船号：" field="shipNo" size="mini" span="4">
          <vxe-input v-model="formObj.shipNo" clearable size="mini"></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="船名：" field="shipNam" size="mini" span="5">
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
        <vxe-form-item title="状态：" field="ifUpXy" size="mini" span="5">
          <vxe-select v-model="formObj.ifUpXy" clearable size="mini">
            <vxe-option value="1" label="已上传"></vxe-option>
            <vxe-option value="0" label="未上传"></vxe-option>
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
         <el-divider content-position="left">
           <strong>计费信息</strong>
         </el-divider>
        <el-col :span="15">
          <vxe-toolbar perfect size="mini">
            <template v-slot:buttons>
              <vxe-button
                type="text"
                class="fa fa-plus"
                @click="commitXy()"
                >生成开票信息</vxe-button
              >
              <vxe-button
                type="text"
                class="fa fa-trash"
                @click="deleteAll()"
                >作废</vxe-button
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
            :round="true"
            align="center"
            height="300px"
            ref="tableloadHeadRef"
             @cell-click="rowclick"
             :cell-style="cellStyle"   
          >
            <vxe-table-column field="feeBillNo" title="计费单号" width="100"></vxe-table-column>
            <vxe-table-column field="ifUpXy" title="状态" :formatter="formatterHeadStatus" width="80"></vxe-table-column>
             <vxe-table-column field="upDf" title="开票状态" :formatter="formatterHeadStatusKp" width="80"></vxe-table-column>
            <vxe-table-column field="shipNo" title="船号"  width="80"></vxe-table-column>
            <vxe-table-column field="shipNam" title="船名"  width="80"></vxe-table-column>
            <vxe-table-column field="contNo" title="合同号"  width="90"></vxe-table-column>
            <vxe-table-column field="inoutId" title="进出口" :formatter="formatterInoutId" width="60"></vxe-table-column>
            <vxe-table-column field="tradeId" title="内外贸" :formatter="formatterTradeId" width="60"></vxe-table-column>
            <vxe-table-column field="shipperDoc" title="货主"  ></vxe-table-column>
            <vxe-table-column field="fpzl" title="发票类型" width="80" ></vxe-table-column>
            <vxe-table-column field="recordTim" title="创建时间"  ></vxe-table-column>
          </vxe-table>
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
        <el-col :span="9">
            <vxe-toolbar perfect size="mini">
            <template v-slot:buttons>
              <vxe-button
                type="text"
                class="fa fa-arrows-h"
                @click="splitWeight()"
                >重量拆分</vxe-button
              >
            </template>
          </vxe-toolbar>
            <vxe-table
            border
            show-overflow
            keep-source
            show-footer
            highlight-hover-row
            highlight-current-row
            size="mini"
            :data="tableDataBody"
            :loading="tableloadBody"
            :round="true"
            align="center"
            height="340px"
            ref="tableloadBodyRef"
            :edit-config="{trigger: 'click', mode: 'row'}"
             @cell-click="rowBodyclick"
              :footer-method="footerMethod"
          >
           <vxe-table-column field="cfeeNam" title="费目名称" ></vxe-table-column>
            <vxe-table-column field="feeRat" title="费率" ></vxe-table-column>
            <vxe-table-column field="weightWgt" title="吨数" ></vxe-table-column>
            <vxe-table-column field="feeMny" title="金额" ></vxe-table-column>
            <vxe-table-column field="temp3" title="拆分备注"  :edit-render="{autofocus: '.vxe-input--inner'}" >
                 <template v-slot:edit="{ row }">
                     <vxe-input type="number" v-model="row.temp3" ></vxe-input>
                </template>
            </vxe-table-column>
          </vxe-table>
        </el-col>
      </el-row>
      <el-row :gutter="10">
         <el-divider content-position="left">
           <strong>发票信息</strong>
         </el-divider>
          <vxe-toolbar perfect size="mini">
            <template v-slot:buttons>
               <vxe-button
                type="text"
                class="fa fa-refresh"
                @click="getXyHead()"
                >刷新</vxe-button
              >
               <vxe-button
                type="text"
                class="fa fa-refresh"
                @click="updateSh()"
                >发票开具</vxe-button
              >
              <vxe-button
                type="text"
                class="fa fa-plus"
                @click="updateXfdh()"
                >购/销方信息维护</vxe-button
              >

                 <vxe-button
                type="text"
                class="fa fa-plus"
                @click="youHui()"
                >优惠</vxe-button
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
            :data="tableDataXyHead"
            :loading="tableloadXyHead"
            :round="true"
            align="center"
            height="300px"  
            @cell-click="selectXyclick"
          >
            <vxe-table-column field="fpzl" title="发票种类" width="80"></vxe-table-column>
            <vxe-table-column field="fapiaoNo" title="发票号码" width="100"></vxe-table-column>
           <vxe-table-column field="fapiaoDm" title="发票代码" width="100"></vxe-table-column>
           <vxe-table-column field="ywdh" title="业务单号"  width="130"></vxe-table-column>
           <vxe-table-column field="gfmc" title="购方名称" width="200"></vxe-table-column>
            <vxe-table-column field="hjje" title="合计金额" width="100"></vxe-table-column>
            <vxe-table-column field="hjse" title="合计税额" width="100"></vxe-table-column>
           <vxe-table-column field="bz" title="备注" width="100"></vxe-table-column>
            <vxe-table-column field="kpr" title="开票人" width="80"></vxe-table-column>
          <vxe-table-column field="shroracle" title="审核人" width="80"></vxe-table-column>
            <vxe-table-column field="skr" title="收款人" width="80"></vxe-table-column>
                    <vxe-table-column field="zfTime" title="作废时间" width="160"></vxe-table-column>
             <vxe-table-column field="zfOpt" title="作废人" width="80"></vxe-table-column>
            <vxe-table-column field="messageError" title="反馈信息"  width="250"></vxe-table-column>
     
          </vxe-table>
      </el-row>



      <vxe-modal
        v-if="dialogFormVisibleView"
        v-model="dialogFormVisibleView"
        title="拆分重量"
        width="400"
        height="200"
        resize
        storage
      >
       <vxe-form :data="splitRow" size="mini">
        <vxe-form-item title="拆分吨数" field="weightWgt" size="mini" span="24">
         <vxe-input v-model="selectWeight" type="number"></vxe-input>
         </vxe-form-item>
       </vxe-form>
        <div align="center">
        <el-button type="primary" size="mini" icon="el-icon-check" @click="splitSubmit">确定</el-button>
         <el-button size="mini" icon="el-icon-close" plain @click="dictItemVisible">取消</el-button>
        </div>
      </vxe-modal>


      <vxe-modal   
      v-if="dialogFormVisibleXfdhView"
        v-model="dialogFormVisibleXfdhView"
        title="开票信息维护"
        width="800"
        height="400"
        resize
        storage>
          <vxe-form :data="selectXy" size="mini" title-width="90">
       <vxe-form-item title="销方电话" field="xfdh" size="mini" span="12">
           <EditDown
            :objs="selectXy"
            :property="downTableOption.xf"
            :tableColumns="downTableOption.xfColum"
            :urlapi="downTableOption.xfurl"
            :returnApi="downTableOption.xfreturnApi"
            sizenow="mini"
          ></EditDown>
         </vxe-form-item>
          <vxe-form-item title="购方电话" field="gfdh" size="mini" span="12">
         <vxe-input v-model="selectXy.gfdh"></vxe-input>
         </vxe-form-item>


          <vxe-form-item title="销方地址" field="xfdz" size="mini" span="12">
         <vxe-input v-model="selectXy.xfdz"></vxe-input>
         </vxe-form-item>
         <vxe-form-item title="购方地址" field="gfdz" size="mini" span="12">
         <vxe-input v-model="selectXy.gfdz"></vxe-input>
         </vxe-form-item>
         <vxe-form-item title="销方开户行" field="xfkhh" size="mini" span="12">
         <vxe-input v-model="selectXy.xfkhh"></vxe-input>
         </vxe-form-item>
         <vxe-form-item title="购方开户行" field="gfkhh" size="mini" span="12">
         <vxe-input v-model="selectXy.gfkhh"></vxe-input>
         </vxe-form-item>
          <vxe-form-item title="销方银行账号" field="xfyhzh" size="mini" span="12">
         <vxe-input v-model="selectXy.xfyhzh"></vxe-input>
         </vxe-form-item>
          <vxe-form-item title="购方银行账号" field="gfyhzh" size="mini" span="12">
         <vxe-input v-model="selectXy.gfyhzh"></vxe-input>
         </vxe-form-item>
         <vxe-form-item title="终端号" field="zdh" size="mini" span="12" >
            <vxe-input v-if="selectXy.unitXy.zd.indexOf(',')==-1" v-model="selectXy.zdh"></vxe-input>
            <vxe-select  v-if="selectXy.unitXy.zd.indexOf(',')!=-1" v-model="selectXy.zdh"  size="mini">
              <template v-for="(item,i) in selectXy.unitXy.zd.split(',')">
                 <vxe-option :value="item" :label="item" ></vxe-option> 
              </template>
          </vxe-select>
         </vxe-form-item>
       </vxe-form>
        <div align="center">
        <el-button type="primary" size="mini" icon="el-icon-check" @click="updateXfdhNow">确定</el-button>
         <el-button size="mini" icon="el-icon-close" plain @click="dictItemVisible">取消</el-button>
        </div>
      </vxe-modal>


            <vxe-modal   
      v-if="dialogFormVisibleYouhuiView"
        v-model="dialogFormVisibleYouhuiView"
        title="优惠"
        width="700"
        height="200"
        resize
        storage>
          <vxe-form :data="selectXy" size="mini" title-width="90">

          <vxe-form-item title="优惠政策" field="yhzc" size="mini" span="12">
         <vxe-input v-model="selectXy.yhzc"></vxe-input>
         </vxe-form-item>
          <vxe-form-item title="优惠说明" field="yhsm" size="mini" span="12">
         <vxe-input v-model="selectXy.yhsm"></vxe-input>
         </vxe-form-item>
        

       </vxe-form>
        <div align="center">
        <el-button type="primary" size="mini" icon="el-icon-check" @click="updateXfdhNow">确定</el-button>
         <el-button size="mini" icon="el-icon-close" plain @click="dictItemVisible">取消</el-button>
        </div>
      </vxe-modal>


    </basic-container>
  </div>
</template>

<script>
import {
  fetchList,
  fetchBodyList,
  fetchXyList,
  saveAll,
  putXyHead,
  commitXy,
  deleteXy
} from "@/api/invoice/split";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import XEUtils from "xe-utils";
import EditDown from "../../baseUtile/EditDown";
import { xj, jf, accMul, clone } from "@/util/util";
export default {
  components: { EditDown },
  name: "invoice-split-index",
  data() {
    return {
      dateNow: {},
      formObj: {
        shipNam: null,
        tradeId: null,
        inoutId: null,
        statusType: null,
        unitNam: null,
        unitCod: null
      },
      tableload: false,
      tableloadBody: false,
      tableloadXyHead: false,
      tableData: [],
      tableDataBody: [],
      tableDataXyHead: [],

      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      downTableOption: {
        //业务员下拉菜单展示项
        shipDataColum: [
          { field: "CALL_NO", title: "呼号" },
          { field: "C_SHIP_NAM", title: "船名" },
          { field: "E_SHIP_NAM", title: "英文船名" }
        ],
        //下拉菜单的路径
        shipDataurl: "/ship/baseCod/shipdata/page",
        shipDataname: "shipNam",
        shipDatareturnApi: [{ field: "C_SHIP_NAM", returnName: "shipNam" }],
        //单位
        unitNam: "unitNam",
        unitColum: [
          { field: "UNIT_COD", title: "单位代码" },
          { field: "UNIT_NAM", title: "单位名称" }
        ],
        unitreturnApi: [
          { field: "UNIT_COD", returnName: "unitCod" },
          { field: "UNIT_NAM", returnName: "unitNam" }
        ],
        uniturl: "/basecode/basecod/getUnitNam",
        //销方信息
        xf: "xfdh",
        xfColum: [
          { field: "XF_SHIPPER_ADD", title: "地址" },
          { field: "XF_SHIPPER_ACN", title: "银行" },
          { field: "XF_SHIPPER_IRD_NO", title: "卡号" },
          { field: "XF_SHIPPER_DH", title: "电话" }
        ],
        xfreturnApi: [
          { field: "XF_SHIPPER_ADD", returnName: "xfdz" },
          { field: "XF_SHIPPER_ACN", returnName: "xfkhh" },
          { field: "XF_SHIPPER_IRD_NO", returnName: "xfyhzh" },
          { field: "XF_SHIPPER_DH", returnName: "xfdh" },
          { field: "PKID", returnName: "xfpkid" }
        ],
        xfurl: "/invoice/torgxyxf/xfDate"
      },
      selectRow: null,
      selectBodyRow: null,
      selectXy: {},
      splitRow: null,
      selectWeight: null,
      dialogFormVisibleView: false,
      dialogFormVisibleXfdhView: false,
      dialogFormVisibleYouhuiView: false
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  mounted() {},
  
  created() {
    this.getDate();
  },
  methods: {
    getDate() {
      this.tableload = true;
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
    getBody() {
      this.tableloadBody = true;
      fetchBodyList(this.selectRow.feeBillNo)
        .then(response => {
          this.tableDataBody = response.data.data;
          if (this.tableDataBody != null && this.tableDataBody.length > 0) {
            for (var i = 0; i < this.tableDataBody.length; i++) {
              if (this.tableDataBody[i].temp3 == null) {
                this.tableDataBody[i].temp3 = 1;
              }
              this.tableDataBody[i].pkid = i;
            }
          }
          this.tableloadBody = false;
        })
        .catch(() => {
          this.tableloadBody = false;
        });
    },
    getXyHead() {
      this.tableloadXyHead = true;
      console.log(this.selectRow)
      fetchXyList(this.selectRow.pkid)
        .then(response => {
          this.tableDataXyHead = response.data.data;
          this.tableloadXyHead = false;
          this.selectXy = null;
        })
        .catch(() => {
          this.tableloadXyHead = false;
        });
    },
    updateXfdh() {
      if (this.selectXy == null || this.selectXy.ywdh == null) {
        this.$message.error("请选择一条进行信息修改");
        return;
      }
      this.dialogFormVisibleXfdhView = true;
    },
   youHui() {
      if (this.selectXy == null || this.selectXy.ywdh == null) {
        this.$message.error("请选择一条记录");
        return;
      }
      console.log(this.selectXy)
      this.dialogFormVisibleYouhuiView = true;
    },

    rowclick({ row }) {
      this.selectRow = row;
      this.getBody();
      this.getXyHead();
    },
    selectXyclick({ row }) {
      this.selectXy = row;
    },
    rowBodyclick({ row }) {
      this.selectBodyRow = row;
    },
    updateXfdhNow() {
      putXyHead(this.selectXy)
        .then(data => {
          this.dialogFormVisibleXfdhView = false;
          this.$message.success("修改成功");
          this.getXyHead();
        })
        .catch(() => {});
    },
    resetForm() {
      this.formObj.unitCod = null;
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    dictItemVisible() {
      this.dialogFormVisibleView = false;
      this.dialogFormVisibleXfdhView = false;
    },
    splitWeight() {
      if (this.selectBodyRow == null) {
        this.$message.error("请选择一条费目进行拆分");
        return;
      }
      if (this.selectRow.ifUpXy == "1") {
        this.$message.error("已经上传无法拆分");
        return;
      }
      this.selectWeight = this.selectBodyRow.weightWgt;
      this.dialogFormVisibleView = true;
    },
    splitSubmit() {
      if (this.selectBodyRow.weightWgt != this.selectWeight) {
        var feeMnyAll = this.selectBodyRow.feeMny;
        var gsSeFeeAll = this.selectBodyRow.gsSeFee;
        var gsTaxFeeAll = this.selectBodyRow.gsTaxFee;
        var weight = jf(this.selectBodyRow.weightWgt, this.selectWeight);
        var feeMny =
          Math.round(accMul(accMul(weight, this.selectBodyRow.feeRat), 100)) /
          100;
        var gsSeFee = Math.round((weight / 1.06) * 100) / 100;
        var gsTaxFee = jf(feeMny, gsSeFee);
        var objnew = this.objClone(this.selectBodyRow);
        objnew.weightWgt = this.selectWeight;
        objnew.feeMny =
          Math.round(accMul(accMul(objnew.weightWgt, objnew.feeRat), 100)) /
          100;
        objnew.gsSeFee = Math.round((objnew.weightWgt / 1.06) * 100) / 100;
        objnew.gsTaxFee = jf(objnew.feeMny, objnew.gsSeFee);
        if (
          jf(feeMnyAll, xj(feeMny, objnew.feeMny)) > 0 ||
          jf(gsSeFeeAll != xj(objnew.gsSeFee, gsSeFee)) > 0 ||
          jf(gsTaxFeeAll != xj(gsTaxFee, objnew.gsTaxFee)) > 0
        ) {
          this.$message.error(
            "此拆分方法，因四舍五入问题，会产生差额，差额：" +
              jf(feeMnyAll, xj(feeMny, objnew.feeMny))
          );
          return;
        }
        this.selectBodyRow.weightWgt = jf(
          this.selectBodyRow.weightWgt,
          this.selectWeight
        );
        this.selectBodyRow.feeMny =
          Math.round(
            accMul(
              accMul(this.selectBodyRow.weightWgt, this.selectBodyRow.feeRat),
              100
            )
          ) / 100;
        this.selectBodyRow.gsSeFee =
          Math.round((this.selectBodyRow.weightWgt / 1.06) * 100) / 100;
        this.selectBodyRow.gsTaxFee = jf(
          this.selectBodyRow.feeMny,
          this.selectBodyRow.gsSeFee
        );
        this.tableDataBody.push(objnew);
      }
      this.dialogFormVisibleView = false;
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
    formatterHeadStatus({ cellValue }) {
      if (cellValue == "1") {
        return "已上传";
      } else if (cellValue == "0") {
        return "未上传";
      }
    },
    formatterHeadStatusKp({ cellValue }) {
      if (cellValue == "0") {
        return "未开票";
      } else if (cellValue == "2") {
        return "已开票";
      }else if (cellValue == "3") {
        return "作废";
      }
    },
    cellStyle({ row, rowIndex, column, columnIndex }) {
      if (column.property === "ifUpXy") {
        if (row.ifUpXy === "0") {
          return {
            color: "green"
          };
        } else if (row.ifUpXy === "1") {
          return {
            color: "red"
          };
        }
      }else if(column.property === "upDf"){
       if (row.upDf === "0") {
          return {
            color: "green"
          };
        } else if (row.upDf === "2") {
          return {
            color: "red"
          };
        }else if (row.upDf === "3") {
          return {
            color: "blue"
          };
        }
      }
    },
    footerMethod({ columns, data }) {
      return [
        columns.map((column, columnIndex) => {
          if (columnIndex === 0) {
            return "合计";
          }
          if (["feeMny"].includes(column.property)) {
            return XEUtils.sum(data, column.property);
          }
          return null;
        })
      ];
    },
    commitXy() {
      if (this.selectRow == null) {
        this.$message.error("请选择一个计费单据进行发票打印");
        return;
      }
      this.$XModal.confirm("您确定要打印发票吗？").then(type => {
        if (type == "confirm") {
          let loadingInstance = Loading.service({
            fullscreen: true,
            text: "正在提交………"
          });
          saveAll(this.selectRow, this.tableDataBody)
            .then(data => {
              this.$message.success("提交成功");
              this.getBody();
              this.getXyHead();
              this.getDate();
              loadingInstance.close();
            })
            .catch(() => {
              loadingInstance.close();
            });
        }
      });
    },
    deleteAll(){
       if (this.selectRow == null) {
        this.$message.error("请选择一个计费单据进行发票作废");
        return;
      }
      this.$XModal.confirm("您确定要作废发票吗？").then(type => {
        if (type == "confirm") {
          let loadingInstance = Loading.service({
            fullscreen: true,
            text: "正在作废………"
          });
          deleteXy(this.selectRow.pkid)
            .then(data => {
              this.$message.success("作废成功");
              this.getBody();
              this.getXyHead();
              this.getDate();
              loadingInstance.close();
            })
            .catch(() => {
              loadingInstance.close();
            });
        }
      });
    },
    updateSh() {
      if (this.selectXy == null || this.selectXy.ywdh == null) {
        this.$message.error("请选择一条开票信息");
        return;
      }
      this.$XModal.confirm("您确定上报开票信息吗？").then(type => {
        if (type == "confirm") {
          let loadingInstance = Loading.service({
            fullscreen: true,
            text: "正在上报………"
          });
          commitXy(this.selectXy.ywdh)
            .then(data => {
              this.getXyHead();
              loadingInstance.close();
            })
            .catch(() => {
              loadingInstance.close();
            });
        }
      });
    },
    objClone(obj) {
      var objnew = {
        cancelTime: obj.cancelTime,
        cargoKindCod: obj.cargoKindCod,
        cargoKindNam: obj.cargoKindNam,
        cargoNam: obj.cargoNam,
        cargoNamCod: obj.cargoNamCod,
        checkerNam: obj.checkerNam,
        checkerNamDte: obj.checkerNamDte,
        contNo: obj.contNo,
        cfeeNam: obj.cfeeNam,
        cFeeNam: obj.cfeeNam,
        cfeeUnit: obj.cfeeUnit,
        cFeeUnit: obj.cfeeUnit,
        endCheckerNam: obj.endCheckerNam,
        endCheckerNamDte: obj.endCheckerNamDte,
        exFeeCod: obj.exFeeCod,
        exFeeNam: obj.exFeeNam,
        fapiaoDm: obj.fapiaoDm,
        fapiaoKind: obj.fapiaoKind,
        fapiaoNo: obj.fapiaoNo,
        fapiaoTime: obj.fapiaoTime,
        feeBillNo: obj.feeBillNo,
        feeMny: obj.feeMny,
        feeNameCod: obj.feeNameCod,
        feeRat: obj.feeRat,
        gsSeFee: obj.gsSeFee,
        gsTaxFee: obj.gsTaxFee,
        gsTaxRat: obj.gsTaxRat,
        ifendId: obj.ifendId,
        operNam: obj.operNam,
        priceNo: obj.priceNo,
        recordTim: obj.recordTim,
        seqNo: obj.seqNo,
        shipperCod: obj.shipperCod,
        shipperDoc: obj.shipperDoc,
        slflbm: obj.slflbm,
        takeUnitCod: obj.takeUnitCod,
        takeUnitNam: obj.takeUnitNam,
        temp1: obj.temp1,
        temp2: obj.temp2,
        temp3: obj.temp3,
        unitCod: obj.unitCod,
        unitNam: obj.unitNam,
        updateNam: obj.updateNam,
        updateTim: obj.updateTim,
        weightWgt: obj.weightWgt
      };
      return objnew;
    }
  }
};
</script>
<style lang="scss" >
.invoice-split-index {
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