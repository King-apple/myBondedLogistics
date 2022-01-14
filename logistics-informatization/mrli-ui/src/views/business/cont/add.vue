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
  <div class="businessContAdd">
    <el-divider content-position="left">
      <strong>合同信息</strong>
    </el-divider>
    <vxe-form
      :data="dataForm"
      size="mini"
      ref="editHeadForm"
      :rules="formRules"
    >
      <vxe-form-item title="甲方" field="firstNam" size="mini" span="5">
        <EditDown
          v-if="ajaxOver"
          :objs="dataForm"
          :property="downTableOption.shipperNam"
          :tableColumns="downTableOption.shipperColum"
          :urlapi="downTableOption.shipperurl"
          :returnApi="downTableOption.shipperreturnApi"
          @datereturn="firstNamReturn"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item
        title="结算单位"
        field="settlementUnitNam"
        size="mini"
        span="5"
      >
        <vxe-input
          v-model="dataForm.settlementUnitNam"
          :disabled="disabledCont"
        ></vxe-input>
      </vxe-form-item>
      <vxe-form-item
        title="发票名头"
        field="invoiceAgainstNam"
        size="mini"
        span="5"
      >
        <vxe-input
          v-model="dataForm.invoiceAgainstNam"
          :disabled="disabledCont"
        ></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="乙方" field="secondNam" size="mini" span="5">
        <EditDown
          v-if="ajaxOver"
          :objs="dataForm"
          :property="downTableOption.unitNam"
          :tableColumns="downTableOption.unitColum"
          :urlapi="downTableOption.uniturl"
          :returnApi="downTableOption.unitreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="作业单位" field="unitNam" size="mini" span="4">
        <vxe-input
          v-model="dataForm.unitNam"
          :disabled="disabledCont"
        ></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="内外贸" field="tradeId" size="mini" span="3">
        <vxe-select
          v-model="dataForm.tradeId"
          :options="tradeList"
        ></vxe-select>
      </vxe-form-item>
      <vxe-form-item title="进出口" field="inoutId" size="mini" span="3">
        <vxe-select
          v-model="dataForm.inoutId"
          :options="inoutList"
        ></vxe-select>
      </vxe-form-item>
      <vxe-form-item title="协议标志" field="prepayId" size="mini" span="3">
        <vxe-select
          v-model="dataForm.prepayId"
          :options="prepayIdList"
        ></vxe-select>
      </vxe-form-item>
      <vxe-form-item title="支付方式" field="zffs" size="mini" span="3">
        <vxe-select v-model="dataForm.zffs" :options="zffsList"></vxe-select>
      </vxe-form-item>
      <vxe-form-item
        title="线上支付方式"
        field="onlinePayType"
        size="mini"
        span="3"
      >
        <vxe-select
          v-model="dataForm.onlinePayType"
          :options="onlinePayList"
        ></vxe-select>
      </vxe-form-item>
      <vxe-form-item title="线上签章" field="fcId" size="mini" span="3">
        <vxe-select v-model="dataForm.fcId" :options="fcIdList"></vxe-select>
      </vxe-form-item>
      <vxe-form-item title="水尺" field="csid" size="mini" span="3">
        <vxe-select v-model="dataForm.csid" :options="csidList"></vxe-select>
      </vxe-form-item>
      <vxe-form-item title="合同类型" field="tkType" size="mini" span="3">
        <vxe-select
          v-model="dataForm.tkType"
          :options="tkTypeList"
        ></vxe-select>
      </vxe-form-item>
    </vxe-form>
    <el-divider content-position="left">
      <strong>船舶信息</strong>
    </el-divider>
    <vxe-form :data="dataForm" size="mini"  :rules="formRules">
      <vxe-form-item title="船名" field="shipNam" size="mini" span="5">
        <EditDown
          v-if="ajaxOver"
          :objs="dataForm"
          :property="downTableOption.shipNam"
          :tableColumns="downTableOption.shipNamColum"
          :urlapi="downTableOption.shipNamurl"
          :returnApi="downTableOption.shipNamreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="船主" field="shipOwnerNam" size="mini" span="5">
        <vxe-input v-model="dataForm.shipOwnerNam"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="派船方式" field="sendShipCod" size="mini" span="5">
        <vxe-select
          v-model="dataForm.sendShipCod"
          :options="sendShipCodList"
        ></vxe-select>
      </vxe-form-item>
      <vxe-form-item
        title="到港时间(预)"
        field="toPortTim"
        size="mini"
        span="5"
      >
        <vxe-input v-model="dataForm.toPortTim"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="来往港" field="cPortNam" size="mini" span="4">
        <vxe-input v-model="dataForm.cPortNam"></vxe-input>
      </vxe-form-item>
    </vxe-form>
    <el-divider content-position="left">
      <strong>双方信息</strong>
    </el-divider>
    <vxe-form :data="dataForm" size="mini">
      <vxe-form-item title="甲方经办人" field="firstMan" size="mini" span="5">
        <EditDown
          v-if="ajaxOverFirstMan"
          :objs="dataForm"
          :property="downTableOption.first"
          :tableColumns="downTableOption.firstColum"
          :urlapi="downTableOption.firsturl"
          :returnApi="downTableOption.firstreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
      <vxe-form-item
        title="甲方联系方式"
        field="firstPhone"
        size="mini"
        span="5"
      >
        <vxe-input v-model="dataForm.firstPhone"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="甲方传真" field="firstFax" size="mini" span="5">
        <vxe-input v-model="dataForm.firstFax"></vxe-input>
      </vxe-form-item>
      <vxe-form-item
        title="甲方开户行"
        field="firstBankNam"
        size="mini"
        span="5"
      >
        <vxe-input v-model="dataForm.firstBankNam"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="甲方账号" field="firstAcntNo" size="mini" span="4">
        <vxe-input v-model="dataForm.firstAcntNo"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="乙方经办人" field="secondMan" size="mini" span="5">
        <vxe-input v-model="dataForm.secondMan"></vxe-input>
      </vxe-form-item>
      <vxe-form-item
        title="乙方联系方式"
        field="secondPhone"
        size="mini"
        span="5"
      >
        <vxe-input v-model="dataForm.secondPhone"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="乙方传真" field="secondFax" size="mini" span="5">
        <vxe-input v-model="dataForm.secondFax"></vxe-input>
      </vxe-form-item>
      <vxe-form-item
        title="乙方开户行"
        field="secondBankNam"
        size="mini"
        span="5"
      >
        <vxe-input v-model="dataForm.secondBankNam"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="乙方账号" field="secondAcntNo" size="mini" span="4">
        <vxe-input v-model="dataForm.secondAcntNo"></vxe-input>
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
            ></vxe-input>
          </vxe-form-item>
          <vxe-form-item title="预付款" field="advanceNum" size="mini" span="4">
            <vxe-input
              v-model="dataForm.advanceNum"
              :readonly="advanceNumReadonly"
            ></vxe-input>
          </vxe-form-item>
        </vxe-form>
        <el-row :gutter="15">
          <el-col :span="13">
            <el-divider content-position="center">
              <strong>货物信息</strong>
            </el-divider>
            <el-tag size="mini" style="width: 100%">
              <template v-if="clickCargo.shipperDoc != null"
                >当前选中的货物信息:
                <font v-if="clickCargo.cargoNam != null"
                  >货名：{{ clickCargo.cargoNam }}</font
                ><font>,件数：{{ clickCargo.piecesNum }}</font
                ><font>，吨数：{{ clickCargo.piecesWgt }}</font>
              </template>
              <template v-if="clickCargo.shipperDoc == null">
                当前没有选中货物
              </template>
            </el-tag>
            <vxe-toolbar perfect size="mini">
              <template v-slot:buttons>
                <vxe-button type="text" class="fa fa-plus" @click="saveCargo()"
                  >新增</vxe-button
                >
                <vxe-button
                  type="text"
                  icon="fa fa-trash"
                  @click="$refs.tableCargo.removeCheckboxRow()"
                  >删除</vxe-button
                >
              </template>
            </vxe-toolbar>
            <vxe-table
              border
              show-overflow
              keep-source
              size="mini"
              :data="tableDataCargo"
              :loading="tableloadCargo"
              :round="true"
              align="center"
              height="380px"
              ref="tableCargo"
              @cell-click="rowclick"
              :edit-config="{ trigger: 'click', mode: 'row' }"
              :mouse-config="{ selected: true }"
              :checkbox-config="{ range: true }"
            >
              <vxe-table-column type="checkbox" width="60"></vxe-table-column>
              <vxe-table-column
                field="cargoNam"
                title="货物"
                width="150"
                :edit-render="{
                  name: 'EditDownTable',
                  props: {
                    checkField: 'cargoNam',
                    tableColumn: this.downTableOption.cargoColum,
                    urlapi: this.downTableOption.cargourl,
                    returnApi: this.downTableOption.cargoreturnApi,
                  },
                }"
              ></vxe-table-column>
              <vxe-table-column
                field="CPkgKindNam"
                title="包装"
                width="100"
                :edit-render="{
                  name: 'EditDownTable',
                  props: {
                    checkField: 'CPkgKindNam',
                    tableColumn: this.downTableOption.pkgColum,
                    urlapi: this.downTableOption.pkgurl,
                    returnApi: this.downTableOption.pkgreturnApi,
                  },
                }"
              ></vxe-table-column>
              <vxe-table-column
                field="piecesWgt"
                title="件重"
                width="80"
                :edit-render="{ autofocus: '.vxe-input--inner' }"
              >
                <template v-slot:edit="{ row }">
                  <vxe-input
                    type="number"
                    v-model="row.piecesWgt"
                    @input="inputSpec"
                  ></vxe-input>
                </template>
              </vxe-table-column>
              <vxe-table-column
                field="piecesNum"
                title="件数"
                width="80"
                :edit-render="{ autofocus: '.vxe-input--inner' }"
              >
                <template v-slot:edit="{ row }">
                  <vxe-input
                    type="number"
                    v-model="row.piecesNum"
                    @input="inputSpec"
                  ></vxe-input>
                </template>
              </vxe-table-column>
              <vxe-table-column
                field="weightWgt"
                title="吨重"
                width="80"
                :edit-render="{ autofocus: '.vxe-input--inner' }"
              >
                <template v-slot:edit="{ row }">
                  <vxe-input
                    type="number"
                    v-model="row.weightWgt"
                    @input="inputSpec"
                  ></vxe-input>
                </template>
              </vxe-table-column>
              <vxe-table-column
                field="cargoVol"
                title="体积"
                width="80"
                :edit-render="{ autofocus: '.vxe-input--inner' }"
              >
                <template v-slot:edit="{ row }">
                  <vxe-input
                    type="number"
                    v-model="row.cargoVol"
                    @input="inputSpec"
                  ></vxe-input>
                </template>
              </vxe-table-column>
              <vxe-table-column
                field="priceNum"
                title="货价"
                width="80"
                :edit-render="{ autofocus: '.vxe-input--inner' }"
              >
                <template v-slot:edit="{ row }">
                  <vxe-input
                    type="text"
                    v-model="row.priceNum"
                    @input="inputSpec"
                  ></vxe-input>
                </template>
              </vxe-table-column>
              <vxe-table-column
                field="safetyKndCod"
                title="险种"
                width="80"
              ></vxe-table-column>
              <vxe-table-column
                field="format"
                title="规格"
                width="80"
                :edit-render="{ autofocus: '.vxe-input--inner' }"
              >
                <template v-slot:edit="{ row }">
                  <vxe-input
                    type="text"
                    v-model="row.format"
                    @input="inputSpec"
                  ></vxe-input>
                </template>
              </vxe-table-column>
              <vxe-table-column
                field="cFeeUnit"
                title="计量单位"
                width="100"
                :edit-render="{
                  name: 'EditDownTable',
                  props: {
                    checkField: 'cFeeUnit',
                    tableColumn: this.downTableOption.feeUntColum,
                    urlapi: this.downTableOption.feeUnturl,
                    returnApi: this.downTableOption.feeUntreturnApi,
                  },
                }"
              ></vxe-table-column>
            </vxe-table>
          </el-col>
          <el-col :span="11">
            <el-divider content-position="center">
              <strong>堆存信息</strong>
            </el-divider>
            <vxe-toolbar perfect size="mini">
              <template v-slot:buttons>
                <vxe-button type="text" class="fa fa-plus" @click="saveAssign()"
                  >新增</vxe-button
                >
                <vxe-button
                  type="text"
                  icon="fa fa-trash"
                  @click="$refs.tableAssign.removeCheckboxRow()"
                  >删除</vxe-button
                >
              </template>
            </vxe-toolbar>
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
              :checkbox-config="{ range: true }"
            >
              <vxe-table-column type="checkbox" width="60"></vxe-table-column>
              <vxe-table-column
                field="inPortId"
                title="进出港"
                width="80"
              ></vxe-table-column>
              <vxe-table-column
                field="portWay"
                title="集疏港方式"
                width="80"
              ></vxe-table-column>
              <vxe-table-column
                field="carArrangMan"
                title="运力安排"
                width="80"
              ></vxe-table-column>
              <vxe-table-column
                field="yardWay"
                title="进出库方式"
                width="80"
              ></vxe-table-column>
              <vxe-table-column
                field="weightWgt"
                title="吨数"
                width="80"
              ></vxe-table-column>
              <vxe-table-column
                field="piecesNum"
                title="件数"
                width="80"
              ></vxe-table-column>
              <vxe-table-column
                field="cargoVol"
                title="体积"
                width="80"
              ></vxe-table-column>
              <vxe-table-column
                field="addrNam"
                title="堆存地点"
                width="100"
              ></vxe-table-column>
              <vxe-table-column
                field="feeRat"
                title="费率"
                width="80"
              ></vxe-table-column>
              <vxe-table-column
                field="workTechnics"
                title="作业工艺"
                width="50"
              ></vxe-table-column>
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
            ></vxe-input>
          </vxe-form-item>
          <vxe-form-item title="预付款" field="advanceNum" size="mini" span="4">
            <vxe-input
              v-model="dataForm.advanceNum"
              :readonly="advanceNumReadonly"
              type="number"
            ></vxe-input>
          </vxe-form-item>
        </vxe-form>
        <vxe-toolbar perfect size="mini">
          <template v-slot:buttons>
            <vxe-button type="text" class="fa fa-plus" @click="saveScep()"
              >新增</vxe-button
            >
            <vxe-button
              type="text"
              icon="fa fa-trash"
              @click="$refs.tableScep.removeCheckboxRow()"
              >删除</vxe-button
            >
          </template>
        </vxe-toolbar>
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
          :edit-rules="validRules"
          :edit-config="{ trigger: 'click', mode: 'row' }"
          :mouse-config="{ selected: true }"
          :checkbox-config="{ range: true }"
        >
          <vxe-table-column type="checkbox" width="60"></vxe-table-column>
          <vxe-table-column
            field="specNo"
            title="作业代码"
            :edit-render="{
              name: 'EditDownTable',
              props: {
                checkField: 'specNo',
                tableColumn: this.downTableOption.specNoColum,
                urlapi: this.downTableOption.specNourl,
                returnApi: this.downTableOption.specNoreturnApi,
              },
            }"
          ></vxe-table-column>
          <vxe-table-column field="specNam" title="作业过程"></vxe-table-column>
          <vxe-table-column
            field="exFeeCod"
            title="公开代码"
          ></vxe-table-column>
          <vxe-table-column
            field="exFeeNam"
            title="公开费目"
          ></vxe-table-column>
          <vxe-table-column
            field="feeRat"
            title="费率"
            :edit-render="{ autofocus: '.vxe-input--inner' }"
          >
            <template v-slot:edit="{ row }">
              <vxe-input
                type="number"
                v-model="row.feeRat"
                @input="inputSpec"
              ></vxe-input>
            </template>
          </vxe-table-column>
          <vxe-table-column
            field="weightWgt"
            title="吨数"
            :edit-render="{ autofocus: '.vxe-input--inner' }"
          >
            <template v-slot:edit="{ row }">
              <vxe-input
                type="number"
                v-model="row.weightWgt"
                @input="inputSpec"
              ></vxe-input>
            </template>
          </vxe-table-column>
          <vxe-table-column
            field="unitName"
            title="单位"
            :edit-render="{ autofocus: '.vxe-input--inner' }"
          >
            <template v-slot:edit="{ row }">
              <vxe-select
                v-model="row.unitName"
                :options="unitNameList"
              ></vxe-select>
            </template>
          </vxe-table-column>
        </vxe-table>
      </el-tab-pane>
      <el-tab-pane label="合同条款" name="third">
        <vxe-form :data="dataForm" size="mini">
          <vxe-form-item
            title="查询条件："
            field="selecttk"
            size="mini"
            span="4"
          >
            <vxe-input v-model="dataForm.selecttk" @input="getText"></vxe-input>
          </vxe-form-item>
        </vxe-form>
        <el-row :gutter="15">
          <el-col :span="13">
            <vxe-table
              border
              show-overflow
              keep-source
              size="mini"
              :data="tableDataText"
              :loading="tableloadText"
              :round="true"
              align="center"
              height="400px"
              ref="tableText"
            >
              <vxe-table-column
                field="SEQ_NO"
                title="条款代码"
                width="10%"
              ></vxe-table-column>
              <vxe-table-column
                field="MARK_TXT"
                title="条款名称"
                width="80%"
              ></vxe-table-column>
              <vxe-table-column title="操作" width="10%" show-overflow>
                <template v-slot="{ row }">
                  <vxe-button
                    type="text"
                    icon="fa fa-arrow-circle-right fa-2x"
                    @click="insertText(row)"
                  ></vxe-button>
                </template>
              </vxe-table-column>
            </vxe-table>
            <vxe-pager
              perfect
              size="mini"
              :loading="tableloadText"
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
          <el-col :span="11">
            <el-input
              v-model="dataForm.zyWorkRemark"
              type="textarea"
              :autosize="{ minRows: 21, maxRows: 50 }"
            ></el-input>
          </el-col>
        </el-row>
      </el-tab-pane>
    </el-tabs>
    <div align="center">
      <el-button
        type="primary"
        size="mini"
        icon="el-icon-check"
        @click="handleSubmit"
        >保存</el-button
      >
      <el-button size="mini" icon="el-icon-close" plain @click="closeWindows"
        >取消</el-button
      >
    </div>
    <vxe-modal
      v-if="dialogFormVisibleEditAssign"
      v-model="dialogFormVisibleEditAssign"
      title="新增堆存信息"
      width="800"
      height="600"
      resize
      storage
    >
      <EditAssign
        :datanow="clickCargo"
        @dictItemVisible="dictItemVisible"
        @saveAssignTable="saveAssignTable"
      ></EditAssign>
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
  selectUnit,
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
import EditDown from "../../baseUtile/EditDown";
import EditAssign from "./editAssign";
export default {
  props: ["datanow"],
  components: { EditDown, EditAssign },
  data() {
    return {
      activeName: "first",
      ajaxOver: false,
      ajaxOverFirstMan: false,
      tableDataCargo: [],
      tableDataAssign: [],
      tableDataScep: [],
      tableDataText: [],
      tableloadCargo: false,
      tableloadAssign: false,
      tableloadText: false,
      tableloadScep: false,
      dialogFormVisibleEditAssign: false,
      clickCargo: {},
      advanceNumReadonly: true,
      validRules: {},
      formRules: {
        csid: [{ required: true, message: "请选择水尺" }],
        firstNam: [{ required: true, message: "请选择甲方" }],
        secondNam: [{ required: true, message: "请选择乙方" }],
        shipNam: [{ required: true, message: "请选择船名" }],
        tradeId: [{ required: true, message: "请选择内外贸" }],
        inoutId: [{ required: true, message: "请选择进出口" }],
        prepayId: [{ required: true, message: "请选择协议标志" }],
        zffs: [{ required: true, message: "请选择支付方式" }],
        onlinePayType: [{ required: true, message: "请选择线上支付方式" }],
        fcId: [{ required: true, message: "请选择签章方式" }],
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
        secondCod: null,
        secondNam: "",
        unitNam: null,
        unitCod: null,
        firstMan: null,
        firstFax: null,
        firstPhone: null,
        advanceNum: 0,
        dcAmt: 0,
        zyWorkRemark: null,
        fcId: "01",
        onlinePayType: "01",
        zffs: 1,
        csid: "2",
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
      downTableOption: {
        //操作附加费
        specNoColum: [
          { field: "FEE_NAME_COD", title: "作业代码" },
          { field: "C_FEE_NAM", title: "作业过程" },
          { field: "EX_FEE_COD", title: "公开代码" },
          { field: "EX_FEE_NAM", title: "公开费目" },
        ],
        specNoreturnApi: [
          { field: "FEE_NAME_COD", returnName: "specNo" },
          { field: "C_FEE_NAM", returnName: "specNam" },
          { field: "EX_FEE_COD", returnName: "exFeeCod" },
          { field: "EX_FEE_NAM", returnName: "exFeeNam" },
        ],
        specNourl: "/business/contract/cfeeNamPage",
        //货主
        shipperNam: "firstNam",
        shipperColum: [
          { field: "SHIPPER_COD", title: "货主代码" },
          { field: "SHIPPER_DOC", title: "货主名称" },
        ],
        shipperreturnApi: [
          { field: "SHIPPER_COD", returnName: "firstCod" },
          { field: "SHIPPER_DOC", returnName: "firstNam" },
          { field: "SHIPPER_COD", returnName: "settlementUnitCod" },
          { field: "SHIPPER_DOC", returnName: "settlementUnitNam" },
          { field: "SHIPPER_COD", returnName: "invoiceAgainstCod" },
          { field: "SHIPPER_DOC", returnName: "invoiceAgainstNam" },
        ],
        shipperurl: "/basecode/basecod/getShipperDoc",
        //单位
        unitNam: "secondNam",
        unitColum: [
          { field: "UNIT_COD", title: "单位代码" },
          { field: "UNIT_NAM", title: "单位名称" },
        ],
        unitreturnApi: [
          { field: "UNIT_COD", returnName: "unitCod" },
          { field: "UNIT_NAM", returnName: "unitNam" },
          { field: "UNIT_COD", returnName: "secondCod" },
          { field: "UNIT_NAM", returnName: "secondNam" },
        ],
        uniturl: "/basecode/basecod/getUnitNam",
        //预报船信息
        shipNam: "shipNam",
        shipNamColum: [
          { field: "SHIP_NO", title: "船号" },
          { field: "SHIP_NAM", title: "船名" },
          { field: "TO_PORT_TIM", title: "到港时间" },
          { field: "TO_PORT_NAM", title: "到达港" },
        ],
        shipNamreturnApi: [
          { field: "SHIP_NO", returnName: "shipNo" },
          { field: "SHIP_NAM", returnName: "shipNam" },
          { field: "SEND_SHIP_COD", returnName: "sendShipCod" },
          { field: "SHIP_OWNER_NAM", returnName: "shipOwnerNam" },
          { field: "SHIP_OWNER_COD", returnName: "shipOwnerCod" },
          { field: "TO_PORT_TIM", returnName: "toPortTim" },
          { field: "TO_PORT_NAM", returnName: "cPortNam" },
          { field: "TO_PORT_COD", returnName: "portCod" },
        ],
        shipNamurl: "/caccentre/ship/getWmShipOfNotLev",
        //甲方人员信息
        first: "firstMan",
        firstColum: [
          { field: "FIRST_MAN", title: "姓名" },
          { field: "FIRST_PHONE", title: "电话" },
        ],
        firstreturnApi: [
          { field: "FIRST_MAN", returnName: "firstMan" },
          { field: "FIRST_PHONE", returnName: "firstPhone" },
        ],
        firsturl: "/business/contract/getUserOfFirst",
        //堆存方式
        dcname: "dcType",
        dcColum: [
          { field: "WAY_COD", title: "代码" },
          { field: "WAY_NAM", title: "名称" },
        ],
        dcreturnApi: [{ field: "WAY_NAM", returnName: "dcType" }],
        dcurl: "/basecode/basecod/getDcWay",
        //入库方式
        inname: "inYardWay",
        inColum: [
          { field: "CODE", title: "代码" },
          { field: "MEANING", title: "名称" },
        ],
        inreturnApi: [{ field: "MEANING", returnName: "inYardWay" }],
        inurl: "/basecode/basecod/getInOutWay",
        //出库方式
        outname: "outYardWay",
        outColum: [
          { field: "CODE", title: "代码" },
          { field: "MEANING", title: "名称" },
        ],
        outreturnApi: [{ field: "MEANING", returnName: "outYardWay" }],
        outurl: "/basecode/basecod/getInOutWay",
        //货物
        cargoname: "cargoNam",
        cargoColum: [
          { field: "CARGO_NAM_COD", title: "货物代码" },
          { field: "CARGO_NAM", title: "货物名称" },
          { field: "CARGO_KIND_COD_PRICE", title: "价格货类代码" },
          { field: "CARGO_KIND_NAM_PRICE", title: "价格货类名称" },
          { field: "CARGO_KIND_COD_DL", title: "大货类代码" },
          { field: "CARGO_KIND_NAM_DL", title: "大货类名称" },
        ],
        cargoreturnApi: [
          { field: "CARGO_NAM_COD", returnName: "cargoNamCod" },
          { field: "CARGO_NAM", returnName: "cargoNam" },
        ],
        cargourl: "/yard/mioyardhead/getCargoNam",
        //库场
        yardname: "yardNam",
        yardColum: [
          { field: "YARD_NO", title: "库场代码" },
          { field: "YARD_NAM", title: "库场名称" },
          { field: "YARD_TYP_COD", title: "类库代码" },
          { field: "YARD_TYP_NAM", title: "类库名称" },
        ],
        yardreturnApi: [{ field: "YARD_NAM", returnName: "yardNam" }],
        yardurl: "/basecode/basecod/getYard",
        //包装
        pkgname: "CPkgKindNam",
        pkgColum: [
          { field: "PKG_KIND_COD", title: "包装代码" },
          { field: "C_PKG_KIND_NAM", title: "包装名称" },
        ],
        pkgreturnApi: [
          { field: "PKG_KIND_COD", returnName: "pkgKindCod" },
          { field: "C_PKG_KIND_NAM", returnName: "CPkgKindNam" },
        ],
        pkgurl: "/basecode/basecod/getPkgKind",
        //计量单位
        feeUntname: "cFeeUnit",
        feeUntColum: [
          { field: "CODE", title: "代码" },
          { field: "C_FEE_UNIT", title: "名称" },
        ],
        feeUntreturnApi: [
          { field: "CODE", returnName: "feeUnitCod" },
          { field: "C_FEE_UNIT", returnName: "cFeeUnit" },
        ],
        feeUnturl: "/basecode/basecod/getFeeUnt",
      },
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      disabledCont: false,
      pknum: 0,
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
    ...mapState({
      userInfo: (state) => state.user.userInfo,
    }),
  },
  created() {
    this.disabledCont = true;
    this.ajaxOver = true;
    if (this.datanow.fphm != null) {
    } else {
    }
    this.getText();
  },
  mounted: function () {
    this.dataForm.tkType = "01";
    this.getUnitCod();
  },
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
    getUnitCod() {
      selectUnit(
      )
        .then((response) => {
          this.dataForm.unitCod=response.data.data.codeId
          this.dataForm.unitNam=response.data.data.codeName
           this.dataForm.secondCod=response.data.data.codeId
          this.dataForm.secondNam=response.data.data.codeName
        })
        .catch(() => {
         
        });
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
            this.$refs.tableCargo.getTableData().fullData,
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
            .catch(() => {
              loadingInstance.close();
            });
        });
    },
    closeWindows() {
      this.$emit("dictItemVisible");
    },
    dictItemVisible() {
      this.dialogFormVisibleEditAssign = false;
    },
    formatterTradeId({ cellValue }) {
      if (cellValue == 1) {
        return "内贸";
      } else if (cellValue == 0) {
        return "外贸";
      }
    },
    rowclick({ row }) {
      this.clickCargo = row;
    },
    formatterInoutId({ cellValue }) {
      if (cellValue == 1) {
        return "进口";
      } else if (cellValue == 0) {
        return "出口";
      }
    },
    inputSpec() {
      this.jsmoneyall();
    },
    saveScep() {
      let record = {
        weightWgt: 0,
        feeRat: 0,
        unitName: "元/吨",
      };
      this.$refs.tableScep
        .insert(record)
        .then(({ row }) => this.$refs.tableScep.setActiveCell(row, "specNo"));
    },
    saveCargo() {
      if (this.dataForm.firstNam == null || this.dataForm.firstNam == "") {
        this.$message.error("请选择甲方");
        return;
      }
      if (this.dataForm.tradeId == null) {
        this.$message.error("请选择内外贸");
        return;
      }
      if (this.dataForm.inoutId == null) {
        this.$message.error("请选择进出口");
        return;
      }
      let record = {
        pknum: this.pknum,
        shipperDoc: this.dataForm.firstNam,
        shipperCod: this.dataForm.firstCod,
        shipperDocPrice: this.dataForm.firstNam,
        shipperCodPrice: this.dataForm.firstCod,
        tradeId: this.dataForm.tradeId,
        inoutId: this.dataForm.inoutId,
        format: ".",
        weightWgt: 0,
      };
      this.$refs.tableCargo
        .insert(record)
        .then(({ row }) =>
          this.$refs.tableCargo.setActiveCell(row, "cargoNam")
        );
    },
    saveAssign() {
      if (
        this.clickCargo.shipperDoc == null ||
        this.clickCargo.shipperDoc == "" ||
        this.clickCargo.shipperDoc == undefined
      ) {
        this.$message.error("没有选择货物信息");
        return;
      }
      if (
        this.clickCargo.cargoNam == null ||
        this.clickCargo.cargoNam == "" ||
        this.clickCargo.cargoNam == undefined
      ) {
        this.$message.error("选择的货物信息没有选择货名");
        return;
      }
      if (
        this.clickCargo.CPkgKindNam == null ||
        this.clickCargo.CPkgKindNam == "" ||
        this.clickCargo.CPkgKindNam == undefined
      ) {
        this.$message.error("选择的货物信息没有选择包装");
        return;
      }
      this.dialogFormVisibleEditAssign = true;
    },
    saveAssignTable(obj) {
      this.$refs.tableAssign.insert(obj);
      this.jsmoneyall();
    },
    jsmoneyall() {
      var amtAll = 0;
      this.tableDataAssign = this.$refs.tableAssign.getTableData().fullData;
      for (let i in this.tableDataAssign) {
        var weightNow = this.tableDataAssign[i].weightWgt;
        if (
          this.tableDataAssign[i].cargoVol != null &&
          this.tableDataAssign[i].cargoVol > weightNow
        ) {
          weightNow = this.tableDataAssign[i].cargoVol;
        }
        amtAll = xj(amtAll, this.tableDataAssign[i].feeRat * weightNow * 1.1);
        this.weightAll = xj(this.weightAll, weightNow);
      }
      var specAmt = 0;
      var tabledate = this.$refs.tableScep.getTableData().fullData;
      for (var i in tabledate) {
        specAmt = xj(
          specAmt,
          tabledate[i].feeRat * tabledate[i].weightWgt * 1.1
        );
      }
      amtAll = xj(xj(amtAll, this.dataForm.dcAmt), specAmt);
      this.amtAll = amtAll;
      this.dataForm.advanceNum = amtAll;
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
      this.$refs.tableText.reloadData(this.tableDataText);
      this.$refs.tableCargo.reloadData(
        this.$refs.tableCargo.getTableData().fullData
      );
      this.$refs.tableAssign.reloadData(
        this.$refs.tableAssign.getTableData().fullData
      );
      this.$refs.tableScep.reloadData(
        this.$refs.tableScep.getTableData().fullData
      );
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    refreshPlant(plantNo, fphm) {},
    getContractFromPlantno(plantNo) {},
    openContract(fphm) {
      let loadingInstance = Loading.service({
        fullscreen: true,
        text: "正在打开合同………",
      });
      getHead(fphm)
        .then((response) => {
          this.dataForm = response.data.data;
          this.amtAll = this.dataForm.advanceNum;
          if (this.dataForm.txtNum == null) {
            this.textnum = 1;
          } else {
            this.textnum = this.dataForm.txtNum;
          }
          this.ajaxOver = true;
          this.downTableOption.firsturl =
            this.downTableOption.firsturl +
            "?firstCod=" +
            this.dataForm.firstCod;
          getCargo(fphm)
            .then((response) => {
              this.tableDataCargo = response.data.data;
            })
            .catch(() => {});
          getAssign(fphm)
            .then((response) => {
              this.tableDataAssign = response.data.data;
              var amtAll = 0;
              for (let i in this.tableDataAssign) {
                var weightNow = this.tableDataAssign[i].weightWgt;
                if (
                  this.tableDataAssign[i].cargoVol != null &&
                  this.tableDataAssign[i].cargoVol > weightNow
                ) {
                  weightNow = this.tableDataAssign[i].cargoVol;
                }
                amtAll = xj(
                  amtAll,
                  this.tableDataAssign[i].feeRat * weightNow * 1.1
                );
                this.weightAll = xj(this.weightAll, weightNow);
              }
              this.amtAll = amtAll;
              loadingInstance.close();
            })
            .catch(() => {});
          getCspec(fphm)
            .then((response) => {
              this.tableDataScep = response.data.data;
            })
            .catch(() => {});
        })
        .catch(() => {});
    },
    firstNamReturn() {
      this.ajaxOverFirstMan = true;
      this.downTableOption.firsturl =
        this.downTableOption.firsturl + "?firstCod=" + this.dataForm.firstCod;
    },
  },
};
</script>
<style lang="scss" scoped>
.businessContAdd {
  .el-divider--horizontal {
    margin: 8px 0;
  }
  .vxe-toolbar {
    height: 25px;
  }
}
</style>