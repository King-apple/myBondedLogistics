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
  <div class="zyworkgather">
    <basic-container>
      <vxe-form :data="formObj">
        <vxe-form-item title="时间" field="dateNow" size="mini" span="6">
          <vxe-input
            v-model="formObj.dateNow"
            type="date"
            valueFormat="yyyy-MM-dd"
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item span="2" size="mini" align="right">
          <vxe-button status="primary" icon="fa fa-search" @click="select()"
            >查询</vxe-button
          >
        </vxe-form-item>
        <vxe-form-item span="2" size="mini" align="center">
          <vxe-button status="primary" icon="fa fa-edit" @click="editDate()"
            >编辑</vxe-button
          >
        </vxe-form-item>
        <vxe-form-item span="2" size="mini" align="center">
          <vxe-button status="danger" icon="fa fa-spinner" @click="dealDate()"
            >处理</vxe-button
          >
        </vxe-form-item>
        <vxe-form-item span="2" size="mini" align="left">
          <vxe-button status="danger" icon="fa fa-print" @click="print()"
            >打印</vxe-button
          >
        </vxe-form-item>
        <vxe-form-item
          title="值班主任"
          field="zyMasterMan"
          size="mini"
          span="5"
        >
          <vxe-input v-model="dataForm.zyMasterMan" disabled></vxe-input>
        </vxe-form-item>
        <vxe-form-item
          title="调度员"
          field="zyDispatcherMan"
          size="mini"
          span="5"
        >
          <vxe-input v-model="dataForm.zyDispatcherMan" disabled></vxe-input>
        </vxe-form-item>
        <vxe-form-item
          title="气象情况"
          field="zyWeatherTxt"
          size="mini"
          span="24"
        >
          <vxe-input v-model="dataForm.zyWeatherTxt" disabled></vxe-input>
        </vxe-form-item>
      </vxe-form>
      <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
        <el-tab-pane label="吞吐量完成情况" name="first">
          <vxe-form :data="dataForm">
            <vxe-form-item
              title="本昼夜完工船艘次"
              field="zyFinishShip"
              size="mini"
              span="12"
            >
              <vxe-input v-model="dataForm.zyAtberthShip" readonly></vxe-input>
            </vxe-form-item>
            <vxe-form-item
              title="在泊船艘次"
              field="zyAtberthShip"
              size="mini"
              span="12"
            >
              <vxe-input v-model="dataForm.zyFinishShip" readonly></vxe-input>
            </vxe-form-item>
            <vxe-form-item
              title="昼夜进出港情况"
              field="zyIoportStatus"
              size="mini"
              span="24"
            >
              <el-input
                type="textarea"
                :autosize="{ minRows: 5, maxRows: 10 }"
                v-model="dataForm.zyIoportStatus"
                readonly
              ></el-input>
            </vxe-form-item>
          </vxe-form>
          <el-row></el-row>
          <vxe-table
            border
            show-overflow
            size="mini"
            :data="tableDataFist"
            :loading="tableloadend"
            :round="true"
            highlight-hover-row
            align="center"
            height="400px"
            ref="tableFist"
          >
            <vxe-table-column
              field="zyWorkType"
              title="完成量"
            ></vxe-table-column>
            <vxe-table-column
              field="zyBulkWgt"
              title="散杂货（万吨）"
            ></vxe-table-column>
            <vxe-table-column
              field="zyOilWgt"
              title="油品（万吨）"
            ></vxe-table-column>
            <vxe-table-column title="集装箱">
              <vxe-table-column
                field="zyCtnTeu"
                title="万TEU"
              ></vxe-table-column>
              <vxe-table-column
                field="zyCtnWgt"
                title="重量吨"
              ></vxe-table-column>
            </vxe-table-column>
            <vxe-table-column
              field="zyWorkSum"
              title="合计（万吨）"
            ></vxe-table-column>
          </vxe-table>
        </el-tab-pane>

        <el-tab-pane label="船舶情况" name="second">
          <vxe-toolbar perfect size="mini">
            <template v-slot:buttons>
              <vxe-button
                type="text"
                class="fa fa-check-circle"
                @click="dealShip()"
              >
                处 理</vxe-button
              >
            </template>
          </vxe-toolbar>
          <el-row :gutter="15">
            <el-col :span="12">
              <el-divider content-position="center">
                <strong>昼夜船舶完成情况</strong>
              </el-divider>
              <vxe-table
                border
                show-overflow
                :round="true"
                size="mini"
                class="mytable-style-shipone"
                :data="tableDataShipOne"
                highlight-hover-row
                :loading="ShipOneLoad"
                :span-method="mergeMethodShipOne"
                @cell-dblclick="editShipOne"
                align="center"
                ref="tableShipone"
                height="550px"
              >
                <vxe-table-column title="作业公司">
                  <vxe-table-column
                    field="zyWorkType"
                    title="类型"
                  ></vxe-table-column>
                  <vxe-table-column
                    field="zyUnitName"
                    title="公司"
                  ></vxe-table-column>
                </vxe-table-column>
                <vxe-table-column
                  field="zyBerthShip"
                  title="靠泊"
                ></vxe-table-column>
                <vxe-table-column
                  field="zyFinishShip"
                  title="完工"
                ></vxe-table-column>
                <vxe-table-column
                  field="zyLeaveShip"
                  title="离泊"
                ></vxe-table-column>
                <vxe-table-column
                  field="zyFleaveShip"
                  title="待离"
                ></vxe-table-column>
                <vxe-table-column
                  field="zyWorkWgt"
                  title="操作量（万吨）"
                ></vxe-table-column>
                <vxe-table-column
                  field="zyWorkRemark"
                  title="备注"
                ></vxe-table-column>
              </vxe-table>
            </el-col>
            <el-col :span="12">
              <el-divider content-position="center">
                <strong>8:00在泊船情况</strong>
              </el-divider>
              <vxe-table
                border
                show-overflow
                :round="true"
                size="mini"
                :data="tableDataShiptwo"
                :span-method="mergeMethodShipTwo"
                :loading="ShiptwoLoad"
                highlight-hover-row
                align="center"
                height="550px"
                ref="tableShiptwo"
                @cell-dblclick="editShipTwo"
              >
                <vxe-table-column title="作业公司">
                  <vxe-table-column
                    field="zyWorkType"
                    title="类型"
                  ></vxe-table-column>
                  <vxe-table-column
                    field="zyUnitName"
                    title="公司"
                  ></vxe-table-column>
                </vxe-table-column>
                <vxe-table-column
                  field="zyAtberthShip"
                  title="在泊船"
                ></vxe-table-column>
                <vxe-table-column
                  field="zyWorkNumber"
                  title="开工路数"
                ></vxe-table-column>
                <vxe-table-column
                  field="zyWorkRemark"
                  title="备注"
                ></vxe-table-column>
              </vxe-table>
            </el-col>
          </el-row>
          <el-col :span="24">
            <el-divider content-position="center">
              <strong>昼夜重点船舶情况</strong>
            </el-divider>
            <vxe-table
              border
              show-overflow
              :round="true"
              highlight-hover-row
              size="mini"
              :data="tableDataShipThrid"
              :loading="shipThirdListLoad"
              align="center"
              ref="tableShipThrid"
            >
              <vxe-table-column
                field="unitNam"
                title="作业公司"
              ></vxe-table-column>
              <vxe-table-column
                field="berthCod"
                title="泊位"
              ></vxe-table-column>
              <vxe-table-column field="shipNam" title="船名"></vxe-table-column>
              <vxe-table-column
                field="agentNam"
                title="代理"
              ></vxe-table-column>
              <vxe-table-column
                field="unloadId"
                title="装/卸"
              ></vxe-table-column>
              <vxe-table-column
                field="cargoNam"
                title="货名"
              ></vxe-table-column>
              <vxe-table-column
                field="weightWgt"
                title="重量"
              ></vxe-table-column>
              <vxe-table-column
                field="workWgt"
                title="日完成量"
              ></vxe-table-column>
              <vxe-table-column
                field="shipWorkWgt"
                title="累计完成"
              ></vxe-table-column>
              <vxe-table-column
                field="toPortTim"
                title="抵港时间"
              ></vxe-table-column>
              <vxe-table-column
                field="toBerthTim"
                title="靠泊时间"
              ></vxe-table-column>
              <vxe-table-column
                field="beginTim"
                title="开工时间"
              ></vxe-table-column>
              <vxe-table-column
                field="endTim"
                title="完工时间"
              ></vxe-table-column>
              <vxe-table-column
                field="levPortTim"
                title="离港时间"
              ></vxe-table-column>
            </vxe-table>
          </el-col>
        </el-tab-pane>

        <el-tab-pane label="火车情况" name="third">
          <vxe-toolbar perfect size="mini">
            <template v-slot:buttons>
              <vxe-button
                type="text"
                class="fa fa-check-circle"
                @click="dealTrain()"
              >
                处 理</vxe-button
              >
            </template>
          </vxe-toolbar>
          <el-row :gutter="15">
            <el-col :span="24">
              <el-tag type="danger">
                <font size="3"
                  >昼夜火车装卸车总计{{
                    trainHeadContentZcNum + trainHeadContentXcNum
                  }}车。其中装车{{ trainHeadContentZcNum }}车，卸车{{
                    trainHeadContentXcNum
                  }}车。</font
                >
              </el-tag>
            </el-col>
            <el-col :span="12">
              <el-divider content-position="center">
                <strong>昼夜装车情况</strong>
              </el-divider>
              <vxe-table
                border
                :round="true"
                show-overflow
                size="mini"
                class="mytable-style-shipone"
                :data="tableDataTrainOne"
                highlight-hover-row
                :loading="trainOneListLoad"
                :span-method="mergeMethodTrainOne"
                @cell-dblclick="editTrain"
                align="center"
                ref="tableTrainone"
                height="400px"
              >
                <vxe-table-column title="作业公司">
                  <vxe-table-column
                    field="zyWorkType"
                    title="类型"
                  ></vxe-table-column>
                  <vxe-table-column
                    field="zyUnitName"
                    title="公司"
                  ></vxe-table-column>
                </vxe-table-column>
                <vxe-table-column
                  field="zyUnitSum"
                  title="装车合计"
                ></vxe-table-column>
                <vxe-table-column
                  field="zyCargoNam"
                  title="货种"
                ></vxe-table-column>
                <vxe-table-column
                  field="zyTrainNum"
                  title="装车"
                ></vxe-table-column>
                <vxe-table-column
                  field="zyWorkRemark"
                  title="备注"
                ></vxe-table-column>
                <vxe-table-column
                  field="focusId"
                  title="打印"
                  :formatter="formatterFocusId"
                ></vxe-table-column>
              </vxe-table>
            </el-col>
            <el-col :span="12">
              <el-divider content-position="center">
                <strong>昼夜卸车情况</strong>
              </el-divider>
              <vxe-table
                border
                show-overflow
                :round="true"
                size="mini"
                :data="tableDataTrainTwo"
                :span-method="mergeMethodTrainTwo"
                :loading="trainTwoListLoad"
                highlight-hover-row
                align="center"
                height="400px"
                w
                ref="tableTraintwo"
                @cell-dblclick="editTrain"
              >
                <vxe-table-column title="作业公司">
                  <vxe-table-column
                    field="zyWorkType"
                    title="类型"
                  ></vxe-table-column>
                  <vxe-table-column
                    field="zyUnitName"
                    title="公司"
                  ></vxe-table-column>
                </vxe-table-column>
                <vxe-table-column
                  field="zyUnitSum"
                  title="装车合计"
                ></vxe-table-column>
                <vxe-table-column
                  field="zyCargoNam"
                  title="货种"
                ></vxe-table-column>
                <vxe-table-column
                  field="zyTrainNum"
                  title="卸车"
                ></vxe-table-column>
                <vxe-table-column
                  field="zyWorkRemark"
                  title="备注"
                ></vxe-table-column>
                <vxe-table-column
                  field="focusId"
                  title="打印"
                  :formatter="formatterFocusId"
                ></vxe-table-column>
              </vxe-table>
            </el-col>
          </el-row>
          <el-row :gutter="15">
            <el-col :span="12">
              <el-divider content-position="center">
                <strong>火车在线情况</strong>
              </el-divider>
              <vxe-table
                border
                show-overflow
                :round="true"
                size="mini"
                :data="tableDataTrainThird"
                :span-method="mergeMethodTrainThird"
                :loading="trainThirdListLoad"
                highlight-hover-row
                align="center"
                height="400px"
                ref="tableTrainThird"
                @cell-dblclick="editTrainThird"
              >
                <vxe-table-column title="作业公司">
                  <vxe-table-column
                    field="zyWorkType"
                    title="类型"
                  ></vxe-table-column>
                  <vxe-table-column
                    field="zyUnitName"
                    title="公司"
                  ></vxe-table-column>
                </vxe-table-column>
                <vxe-table-column
                  field="zyCargoNam"
                  title="货种"
                ></vxe-table-column>
                <vxe-table-column
                  field="zyTrackNam"
                  title="作业道线"
                ></vxe-table-column>
                <vxe-table-column
                  field="zyTrainNum"
                  title="作业车数"
                ></vxe-table-column>
                <vxe-table-column
                  field="focusId"
                  title="打印"
                  :formatter="formatterFocusId"
                ></vxe-table-column>
              </vxe-table>
            </el-col>
            <el-col :span="12">
              <el-divider content-position="center">
                <strong>鲅鱼圈北站情况</strong>
              </el-divider>
              <vxe-toolbar perfect size="mini">
                <template v-slot:buttons>
                  <vxe-button
                    v-if="dataForm.zySeqNo != null"
                    type="text"
                    class="fa fa-pencil"
                    @click="editTrainFourth()"
                    >编辑</vxe-button
                  >
                </template>
              </vxe-toolbar>
              <vxe-table
                border
                show-overflow
                :round="true"
                size="mini"
                :data="tableDataTrainFourth"
                :span-method="mergeMethodTrainFourth"
                :loading="trainFourthListLoad"
                highlight-hover-row
                align="center"
                height="400px"
                ref="tableTrainFourth"
              >
                <vxe-table-column field="zyBc" title="班次"></vxe-table-column>
                <vxe-table-column field="zyZt" title="状态"></vxe-table-column>
                <vxe-table-column field="zyNum" title="列数"></vxe-table-column>
                <vxe-table-column
                  field="zyByName"
                  title="保有量"
                ></vxe-table-column>
                <vxe-table-column
                  field="zyCarNum"
                  title="车数"
                ></vxe-table-column>
                <vxe-table-column field="zyBak" title="备注"></vxe-table-column>
              </vxe-table>
            </el-col>
          </el-row>
        </el-tab-pane>

        <el-tab-pane label="市出市入情况" name="fourth">
          <vxe-toolbar perfect size="mini">
            <template v-slot:buttons>
              <vxe-button
                type="text"
                class="fa fa-check-circle"
                @click="dealCar()"
              >
                处 理</vxe-button
              >
              <vxe-button
                type="text"
                class="fa fa-check-circle"
                @click="printCar()"
              >
                车辆情况统计</vxe-button
              >
              <vxe-button
                type="text"
                class="fa fa-check-circle"
                @click="printCar2()"
              >
                闸口情况统计</vxe-button
              >
            </template>
          </vxe-toolbar>
          <el-row :gutter="15">
            <el-col :span="24">
              <el-tag type="danger">
                <font size="3"
                  >昼夜市出市入总计{{
                    (
                      (carHeadContentZcWeight + carHeadContentXcWeight) /
                      10000
                    ).toFixed(2)
                  }}万吨。其中市出{{
                    (carHeadContentZcWeight / 10000).toFixed(2)
                  }}万吨，市入{{
                    (carHeadContentXcWeight / 10000).toFixed(2)
                  }}万吨。</font
                >
              </el-tag>
            </el-col>
            <el-col :span="12">
              <el-divider content-position="center">
                <strong>昼夜市出情况</strong>
              </el-divider>
              <vxe-table
                border
                show-overflow
                :round="true"
                size="mini"
                :data="tableDataCarOne"
                :span-method="mergeMethodCarOne"
                :loading="CarOneListLoad"
                @cell-dblclick="editCar"
                highlight-hover-row
                align="center"
                height="650px"
                ref="tableCarOne"
              >
                <vxe-table-column title="作业公司">
                  <vxe-table-column
                    field="zyWorkType"
                    title="类型"
                  ></vxe-table-column>
                  <vxe-table-column
                    field="zyUnitName"
                    title="公司"
                  ></vxe-table-column>
                </vxe-table-column>
                <vxe-table-column
                  field="zyWorkWgtAll"
                  title="市出合计"
                ></vxe-table-column>
                <vxe-table-column
                  field="zyCargoName"
                  title="货种"
                ></vxe-table-column>
                <vxe-table-column
                  field="zyWorkWgt"
                  title="市出"
                ></vxe-table-column>
                <vxe-table-column
                  field="zyWorkRemark"
                  title="备注"
                ></vxe-table-column>
                <vxe-table-column
                  field="focusId"
                  title="打印"
                  :formatter="formatterFocusId"
                ></vxe-table-column>
              </vxe-table>
            </el-col>
            <el-col :span="12">
              <el-divider content-position="center">
                <strong>昼夜市入情况</strong>
              </el-divider>
              <vxe-table
                border
                show-overflow
                :round="true"
                size="mini"
                :data="tableDataCarTwo"
                :span-method="mergeMethodCarTwo"
                :loading="CarTwoListLoad"
                @cell-dblclick="editCar"
                highlight-hover-row
                align="center"
                height="650px"
                ref="tableCarTwo"
              >
                <vxe-table-column title="作业公司">
                  <vxe-table-column
                    field="zyWorkType"
                    title="类型"
                  ></vxe-table-column>
                  <vxe-table-column
                    field="zyUnitName"
                    title="公司"
                  ></vxe-table-column>
                </vxe-table-column>
                <vxe-table-column
                  field="zyWorkWgtAll"
                  title="市入合计"
                ></vxe-table-column>
                <vxe-table-column
                  field="zyCargoName"
                  title="货种"
                ></vxe-table-column>
                <vxe-table-column
                  field="zyWorkWgt"
                  title="市入"
                ></vxe-table-column>
                <vxe-table-column
                  field="zyWorkRemark"
                  title="备注"
                ></vxe-table-column>
                <vxe-table-column
                  field="focusId"
                  title="打印"
                  :formatter="formatterFocusId"
                ></vxe-table-column>
              </vxe-table>
            </el-col>
          </el-row>
        </el-tab-pane>

        <el-tab-pane label="生产资源情况（当天）" name="fifth">
          <el-row :gutter="15">
            <el-col :span="12">
              <el-divider content-position="center">
                <strong>机械公司出勤及派牌情况</strong>
              </el-divider>
              <vxe-table
                border
                show-overflow
                :round="true"
                size="mini"
                :data="tableDataMachine"
                :span-method="mergeMethodMachine"
                :loading="machineListLoad"
                highlight-hover-row
                align="center"
                height="650"
                ref="tableMachine"
              >
                <vxe-table-column
                  field="zyMachineName"
                  title="机械类别"
                ></vxe-table-column>
                <vxe-table-column
                  field="zyMachineNum"
                  title="机械在册"
                ></vxe-table-column>
                <vxe-table-column
                  field="zyMachineWorkNum"
                  title="机械出勤"
                ></vxe-table-column>
                <vxe-table-column
                  field="zyMachinePNum"
                  title="8：00派牌"
                ></vxe-table-column>
                <vxe-table-column
                  field="zyManWorkNum"
                  title="人员出勤数"
                ></vxe-table-column>
                <vxe-table-column
                  field="zyManJNum"
                  title="加班人数"
                ></vxe-table-column>
              </vxe-table>
            </el-col>
            <el-col :span="12">
              <el-row>
                <el-divider content-position="center">
                  <strong>汽运公司出勤及派牌情况</strong>
                </el-divider>
                <vxe-table
                  border
                  show-overflow
                  :round="true"
                  size="mini"
                  :data="tableDataCar"
                  :span-method="mergeMethodCar"
                  :loading="carListLoad"
                  highlight-hover-row
                  align="center"
                  height="300px"
                  ref="tableCar"
                >
                  <vxe-table-column
                    field="zyUnitName"
                    title="机械及车辆类别"
                  ></vxe-table-column>
                  <vxe-table-column
                    field="zyZzjNum"
                    title="装载机（斗子"
                  ></vxe-table-column>
                  <vxe-table-column
                    field="zyZxcNum"
                    title="自卸车（翻斗）"
                  ></vxe-table-column>
                  <vxe-table-column
                    field="zyGcNum"
                    title="挂车"
                  ></vxe-table-column>
                  <vxe-table-column
                    field="zyMgyNum"
                    title="美国鹰"
                  ></vxe-table-column>
                  <vxe-table-column
                    field="hjNum"
                    title="合计"
                  ></vxe-table-column>
                </vxe-table>
              </el-row>
              <el-row>
                <el-divider content-position="center">
                  <strong>人力出勤及派牌情况</strong>
                </el-divider>
                <vxe-table
                  border
                  show-overflow
                  :round="true"
                  size="mini"
                  :data="tableDataMan"
                  :span-method="mergeMethodMan"
                  :loading="manListLoad"
                  highlight-hover-row
                  align="center"
                  height="330px"
                  ref="tableMan"
                >
                  <vxe-table-column
                    field="zyUnitCod"
                    title="作业公司"
                    :formatter="formatterUnit"
                  ></vxe-table-column>
                  <vxe-table-column
                    field="zyZdNum"
                    title="正队"
                  ></vxe-table-column>
                  <vxe-table-column
                    field="zyFiveNum"
                    title="五队"
                  ></vxe-table-column>
                  <vxe-table-column
                    field="zyJNum"
                    title="加班"
                  ></vxe-table-column>
                  <vxe-table-column
                    field="hjNum"
                    title="合计"
                  ></vxe-table-column>
                  <vxe-table-column
                    field="zyPNum"
                    title="8：00派牌"
                  ></vxe-table-column>
                </vxe-table>
              </el-row>
            </el-col>
          </el-row>
        </el-tab-pane>

        <el-tab-pane
          label="生产考核及上昼夜生产情况及本昼夜生产重点"
          name="six"
        >
          <vxe-form :data="dataForm" title-width="200px">
            <vxe-form-item
              title="船舶计划兑现率考核"
              field="zySplanCheck"
              size="mini"
              span="24"
            >
              <el-input
                type="textarea"
                :autosize="{ minRows: 4, maxRows: 10 }"
                v-model="dataForm.zySplanCheck"
                readonly
              ></el-input>
            </vxe-form-item>
            <vxe-form-item
              title="船舶生产组织考核"
              field="zySworkCheck"
              size="mini"
              span="24"
            >
              <el-input
                type="textarea"
                :autosize="{ minRows: 4, maxRows: 10 }"
                v-model="dataForm.zySworkCheck"
                readonly
              ></el-input>
            </vxe-form-item>
            <vxe-form-item
              title="火车计划兑现率考核"
              field="zyTplanCheck"
              size="mini"
              span="24"
            >
              <el-input
                type="textarea"
                :autosize="{ minRows: 4, maxRows: 10 }"
                v-model="dataForm.zyTplanCheck"
                readonly
              ></el-input>
            </vxe-form-item>
            <vxe-form-item
              title="上昼夜生产情况"
              field="zyLastStatus"
              size="mini"
              span="24"
            >
              <el-input
                type="textarea"
                :autosize="{ minRows: 4, maxRows: 10 }"
                v-model="dataForm.zyLastStatus"
                readonly
              ></el-input>
            </vxe-form-item>
            <vxe-form-item
              title="本昼夜生产重点"
              field="zyWorkKeypoint"
              size="mini"
              span="24"
            >
              <el-input
                type="textarea"
                :autosize="{ minRows: 4, maxRows: 10 }"
                v-model="dataForm.zyWorkKeypoint"
                readonly
              ></el-input>
            </vxe-form-item>
            <vxe-form-item
              title="生产亮点"
              field="zyWorkHighlight"
              size="mini"
              span="24"
            >
              <el-input
                type="textarea"
                :autosize="{ minRows: 4, maxRows: 10 }"
                v-model="dataForm.zyWorkHighlight"
                readonly
              ></el-input>
            </vxe-form-item>
          </vxe-form>
        </el-tab-pane>
      </el-tabs>
      <vxe-modal
        v-model="ifBegin"
        title="系统正在处理..."
        width="1100"
        height="150"
        :showHeader="false"
      >
        <el-steps
          :active="activeDeal"
          align-center
          finish-status="success"
          style
        >
          <el-step title="处理基本信息"></el-step>
          <el-step title="处理船舶及吞吐量完成情况"></el-step>
          <el-step title="处理船舶情况"></el-step>
          <el-step title="处理火车情况"></el-step>
          <el-step title="处理市出市入情况"></el-step>
        </el-steps>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleedit"
        v-model="dialogFormVisibleedit"
        id="myModal6"
        title="信息维护"
        width="1000"
        height="900"
        resize
        storage
      >
        <Edit
          :datenow="formObj.dateNow"
          @dictItemVisible="dictItemVisible"
          @selectList="getModle"
        ></Edit>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleShipOne"
        v-model="dialogFormVisibleShipOne"
        title="昼夜船舶完成情况备注维护"
        width="600"
        height="400"
        resize
        storage
      >
        <EditShipOne
          :datanow="objNext"
          @dictItemVisible="dictItemVisible"
          @selectList="shipOneList"
        ></EditShipOne>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleShipTwo"
        v-model="dialogFormVisibleShipTwo"
        title="8:00在泊船情况备注维护"
        width="600"
        height="400"
        resize
        storage
      >
        <EditShipTwo
          :datanow="objNext"
          @dictItemVisible="dictItemVisible"
          @selectList="shipTwoList"
        ></EditShipTwo>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleTrain"
        v-model="dialogFormVisibleTrain"
        title="昼夜火车情况备注维护"
        width="600"
        height="400"
        resize
        storage
      >
        <EditTrain
          :datanow="objNext"
          @dictItemVisible="dictItemVisible"
          @selectList="trainselect"
        ></EditTrain>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleTrainThird"
        v-model="dialogFormVisibleTrainThird"
        title="火车在线情况维护"
        width="600"
        height="400"
        resize
        storage
      >
        <EditTrainThird
          :datanow="objNext"
          @dictItemVisible="dictItemVisible"
          @selectList="trainThirdselect"
        ></EditTrainThird>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleCar"
        v-model="dialogFormVisibleCar"
        title="昼夜市入市出备注维护"
        width="600"
        height="400"
        resize
        storage
      >
        <EditCar
          :datanow="objNext"
          @dictItemVisible="dictItemVisible"
          @selectList="carselect"
        ></EditCar>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleTrainFourth"
        v-model="dialogFormVisibleTrainFourth"
        title="鲅鱼圈北站情况维护"
        width="500"
        height="500"
        resize
        storage
      >
        <EditTrainFourth
          :dateId="idNow"
          @dictItemVisible="dictItemVisible"
          @selectList="trainFourthList"
        ></EditTrainFourth>
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
  delObj,
  getObjByDate,
  getFistList,
  dealHead,
  dealfinish,
  dealtrain,
  getShipOneList,
  getShipTwoList,
  getShipThridList,
  dealship,
  getTrainOneAndTwoList,
  getTrainThirdList,
  getTrainFourthList,
  getMachineObject,
  getCarObject,
  getManObject,
  getCarOneAndTwoList,
  dealcar
} from "@/api/caccentre/zyworkgather";
import Edit from "./edit";
import { Loading } from "element-ui";
import EditShipOne from "./editShipOne";
import EditShipTwo from "./editShipTwo";
import EditTrain from "./editTrain";
import EditTrainThird from "./editTrainThird";
import EditTrainFourth from "./editTrainFourth";
import EditCar from "./editCar";
import { mapGetters } from "vuex";
import moment from "moment";
export default {
  components: {
    Edit,
    EditShipOne,
    EditShipTwo,
    EditTrain,
    EditTrainThird,
    EditTrainFourth,
    EditCar
  },
  name: "zyworkgather",
  data() {
    return {
      formObj: { dateNow: null },
      searchForm: {},
      dataForm: {},
      objNext: {},
      idNow: "",
      activeDeal: 1,
      ifBegin: false,
      dialogFormVisibleedit: false,
      dialogFormVisibleShipOne: false,
      dialogFormVisibleShipTwo: false,
      dialogFormVisibleTrain: false,
      dialogFormVisibleCar: false,
      dialogFormVisibleTrainFourth: false,
      dialogFormVisibleTrainThird: false,
      tableData: [],
      tableDataFist: [],
      tableDataShipOne: [],
      tableDataShiptwo: [],
      tableDataShipThrid: [],
      tableDataTrainOne: [],
      tableDataTrainTwo: [],
      tableDataTrainThird: [],
      tableDataTrainFourth: [],
      tableDataCarOne: [],
      tableDataCarTwo: [],
      tableDataMachine: [],
      tableDataCar: [],
      tableDataMan: [],
      mergeCellsShipTwo: [],
      mergeCellsShipOne: [],
      mergeCellsTrainOne: [],
      mergeCellsTrainTwo: [],
      mergeCellsTrainThird: [],
      mergeCellsTrainFourth: [],
      mergeCellsCarOne: [],
      mergeCellsCarTwo: [],
      mergeCellsMachine: [],
      mergeCellsCar: [],
      mergeCellsMan: [],
      activeName: "first",
      activeNameOfOne: ["1", "2", "3"],
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      tableloadend: false,
      shipThirdListLoad: false,
      ShipOneLoad: false,
      ShiptwoLoad: false,
      trainOneListLoad: false,
      trainTwoListLoad: false,
      trainThirdListLoad: false,
      trainFourthListLoad: false,
      CarOneListLoad: false,
      CarTwoListLoad: false,
      machineListLoad: false,
      carListLoad: false,
      manListLoad: false,
      trainHeadContentZcNum: 0,
      trainHeadContentXcNum: 0,
      carHeadContentZcWeight: 0,
      carHeadContentXcWeight: 0
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() {},
  mounted: function() {
    this.formObj.dateNow = moment(new Date()).format("YYYY-MM-DD");
    this.getModle();
  },
  methods: {
    getModle() {
      if (this.formObj.dateNow == null || this.formObj.dateNow == "") {
        this.$message.error("请选择时间");
        return;
      }
      this.$refs.tableShipone.recalculate();
      this.tableloadend = true;
      getObjByDate(Object.assign({ date: this.formObj.dateNow }))
        .then(response => {
          this.dataForm = response.data.data;
          this.firstList(response.data.data.zySeqNo);
          this.shipOneList(response.data.data.zySeqNo);
          this.shipTwoList(response.data.data.zySeqNo);
          this.shipThirdList();
          this.trainOneList(response.data.data.zySeqNo);
          this.trainTwoList(response.data.data.zySeqNo);
          this.trainThirdList(response.data.data.zySeqNo);
          this.trainFourthList(response.data.data.zySeqNo);
          this.carOneList(response.data.data.zySeqNo);
          this.carTwoList(response.data.data.zySeqNo);
          this.tableloadend = false;
        })
        .catch(() => {
          this.tableloadend = false;
        });
      this.machineList();
      this.carList();
      this.manList();
    },
    firstList(id) {
      this.tableloadend = true;
      getFistList(id)
        .then(response => {
          this.tableDataFist = response.data.data;
          this.tableloadend = false;
        })
        .catch(() => {
          this.tableloadend = false;
        });
    },
    shipOneList(id) {
      this.ShipOneLoad = true;
      this.mergeCellsShipOne = [];
      getShipOneList(id)
        .then(response => {
          this.tableDataShipOne = response.data.data;
          for (let i in this.tableDataShipOne) {
            if (this.tableDataShipOne[i].spanMap != null) {
              this.mergeCellsShipOne.push(this.tableDataShipOne[i].spanMap);
            }
          }
          this.ShipOneLoad = false;
        })
        .catch(() => {
          this.ShipOneLoad = false;
        });
    },
    shipTwoList(id) {
      this.ShiptwoLoad = true;
      getShipTwoList(id)
        .then(response => {
          this.tableDataShiptwo = response.data.data;
          for (let i in this.tableDataShiptwo) {
            if (this.tableDataShiptwo[i].spanMap != null) {
              this.mergeCellsShipTwo.push(this.tableDataShiptwo[i].spanMap);
            }
          }
          this.ShiptwoLoad = false;
        })
        .catch(() => {
          this.ShiptwoLoad = false;
        });
    },
    shipThirdList() {
      this.shipThirdListLoad = true;
      getShipThridList(this.formObj.dateNow)
        .then(response => {
          this.tableDataShipThrid = response.data.data;
          this.shipThirdListLoad = false;
        })
        .catch(() => {
          this.shipThirdListLoad = false;
        });
    },
    trainselect(id, unloadId) {
      if (unloadId == "+") {
        this.trainOneList(id);
      } else if (unloadId == "-") {
        this.trainTwoList(id);
      }
    },
    trainThirdselect(id) {
      this.trainThirdList(id);
    },
    trainOneList(id) {
      this.trainOneListLoad = true;
      getTrainOneAndTwoList(id, "1")
        .then(response => {
          this.tableDataTrainOne = response.data.data;
          for (let i in this.tableDataTrainOne) {
            if (this.tableDataTrainOne[i].spanMap != null) {
              this.mergeCellsTrainOne = this.tableDataTrainOne[i].spanMap;
            }
            if (this.tableDataTrainOne[i].zyUnitName == "合计") {
              this.trainHeadContentZcNum = this.tableDataTrainOne[i].zyUnitSum;
            }
          }
          this.trainOneListLoad = false;
        })
        .catch(() => {
          this.trainOneListLoad = false;
        });
    },
    trainTwoList(id) {
      this.trainTwoListLoad = true;
      getTrainOneAndTwoList(id, "2")
        .then(response => {
          this.tableDataTrainTwo = response.data.data;
          for (let i in this.tableDataTrainTwo) {
            if (this.tableDataTrainTwo[i].spanMap != null) {
              this.mergeCellsTrainTwo = this.tableDataTrainTwo[i].spanMap;
            }
            if (this.tableDataTrainTwo[i].zyUnitName == "合计") {
              this.trainHeadContentXcNum = this.tableDataTrainTwo[i].zyUnitSum;
            }
          }
          this.trainTwoListLoad = false;
        })
        .catch(() => {
          this.trainTwoListLoad = false;
        });
    },
    carselect(id, unloadId) {
      if (unloadId == "+") {
        this.carOneList(id);
      } else if (unloadId == "-") {
        this.carTwoList(id);
      }
    },
    carOneList(id) {
      this.CarOneListLoad = true;
      getCarOneAndTwoList(id, "1")
        .then(response => {
          this.tableDataCarOne = response.data.data;
          for (let i in this.tableDataCarOne) {
            if (this.tableDataCarOne[i].spanMap != null) {
              this.mergeCellsCarOne = this.tableDataCarOne[i].spanMap;
            }
            if (this.tableDataCarOne[i].zyUnitName == "合计(除集装箱)") {
              this.carHeadContentZcWeight = this.tableDataCarOne[
                i
              ].zyWorkWgtAll;
            }
          }
          this.CarOneListLoad = false;
        })
        .catch(() => {
          this.CarOneListLoad = false;
        });
    },
    carTwoList(id) {
      this.CarTwoListLoad = true;
      getCarOneAndTwoList(id, "2")
        .then(response => {
          this.tableDataCarTwo = response.data.data;
          for (let i in this.tableDataCarTwo) {
            if (this.tableDataCarTwo[i].spanMap != null) {
              this.mergeCellsCarTwo = this.tableDataCarTwo[i].spanMap;
            }
            if (this.tableDataCarTwo[i].zyUnitName == "合计(除集装箱)") {
              this.carHeadContentXcWeight = this.tableDataCarTwo[
                i
              ].zyWorkWgtAll;
            }
          }
          this.CarTwoListLoad = false;
        })
        .catch(() => {
          this.CarTwoListLoad = false;
        });
    },
    trainThirdList(id) {
      this.trainThirdListLoad = true;
      getTrainThirdList(id)
        .then(response => {
          this.tableDataTrainThird = response.data.data;
          for (let i in this.tableDataTrainThird) {
            if (this.tableDataTrainThird[i].spanMap != null) {
              this.mergeCellsTrainThird = this.tableDataTrainThird[i].spanMap;
            }
          }
          this.trainThirdListLoad = false;
        })
        .catch(() => {
          this.trainThirdListLoad = false;
        });
    },
    trainFourthList(id) {
      this.trainFourthListLoad = true;
      getTrainFourthList(id)
        .then(response => {
          this.tableDataTrainFourth = response.data.data;
          for (let i in this.tableDataTrainFourth) {
            if (this.tableDataTrainFourth[i].spanMap != null) {
              this.mergeCellsTrainFourth = this.tableDataTrainFourth[i].spanMap;
            }
          }
          this.trainFourthListLoad = false;
        })
        .catch(() => {
          this.trainFourthListLoad = false;
        });
    },
    select() {
      this.getModle();
    },
    machineList() {
      this.machineListLoad = true;
      getMachineObject(this.formObj.dateNow)
        .then(response => {
          this.tableDataMachine = response.data.data;
          for (let i in this.tableDataMachine) {
            if (this.tableDataMachine[i].spanMap != null) {
              this.mergeCellsMachine = this.tableDataMachine[i].spanMap;
            }
          }
          this.machineListLoad = false;
        })
        .catch(() => {
          this.machineListLoad = false;
        });
    },
    carList() {
      this.carListLoad = true;
      getCarObject(this.formObj.dateNow)
        .then(response => {
          this.tableDataCar = response.data.data;
          for (let i in this.tableDataCar) {
            if (this.tableDataCar[i].spanMap != null) {
              this.mergeCellsCar = this.tableDataCar[i].spanMap;
            }
          }
          this.carListLoad = false;
        })
        .catch(() => {
          this.carListLoad = false;
        });
    },
    manList() {
      this.manListLoad = true;
      getManObject(this.formObj.dateNow)
        .then(response => {
          this.tableDataMan = response.data.data;
          this.manListLoad = false;
        })
        .catch(() => {
          this.manListLoad = false;
        });
    },
    dealDate() {
      this.$XModal
        .confirm("您确定要处理吗？处理后，会删除原有数据")
        .then(type => {
          if (type == "confirm") {
            if (
              this.formObj.dateNow == null ||
              this.formObj.dateNow == "" ||
              this.formObj.dateNow == undefined
            ) {
              this.$message.error("请选择时间");
              return;
            }
            this.ifBegin = true;
            this.activeDeal = 1;
            dealHead(this.formObj.dateNow)
              .then(response => {
                var id = response.data.data;
                this.activeDeal = 2;
                this.dealSecond(id);
              })
              .catch(() => {
                this.ifBegin = false;
              });
          }
        });
    },
    dealSecond(id) {
      dealfinish(this.formObj.dateNow, id)
        .then(response => {
          this.activeDeal = 2;
          this.dealThird(id);
        })
        .catch(() => {
          this.getModle();
        });
    },
    dealThird(id) {
      dealship(this.formObj.dateNow, id)
        .then(response => {
          this.activeDeal = 3;
          this.dealfourth(id);
        })
        .catch(() => {
          this.getModle();
        });
    },
    dealfourth(id) {
      dealtrain(this.formObj.dateNow, id)
        .then(response => {
          this.activeDeal = 4;
          this.dealCarAll(id);
        })
        .catch(() => {
          this.getModle();
        });
    },
    dealCarAll(id) {
      dealcar(this.formObj.dateNow, id)
        .then(response => {
          this.activeDeal = 5;
          this.$message.success("处理成功");
          this.ifBegin = false;
          this.getModle();
        })
        .catch(() => {
          this.getModle();
        });
    },
    editDate() {
      if (
        this.formObj.dateNow == null ||
        this.formObj.dateNow == "" ||
        this.formObj.dateNow == undefined
      ) {
        this.$message.error("请选择时间");
        return;
      }
      this.dialogFormVisibleedit = true;
    },
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
      this.dialogFormVisibleShipOne = false;
      this.dialogFormVisibleShipTwo = false;
      this.dialogFormVisibleTrain = false;
      this.dialogFormVisibleTrainFourth = false;
      this.dialogFormVisibleCar = false;
      this.dialogFormVisibleTrainThird = false;
    },
    handleClick() {
      this.$refs.tableShipone.reloadData(this.tableDataShipOne);
      this.$refs.tableFist.reloadData(this.tableDataFist);
      this.$refs.tableShiptwo.reloadData(this.tableDataShiptwo);
      this.$refs.tableShipThrid.reloadData(this.tableDataShipThrid);
      this.$refs.tableTrainone.reloadData(this.tableDataTrainOne);
      this.$refs.tableTraintwo.reloadData(this.tableDataTrainTwo);
      this.$refs.tableTrainThird.reloadData(this.tableDataTrainThird);
      this.$refs.tableTrainFourth.reloadData(this.tableDataTrainFourth);
      this.$refs.tableMachine.reloadData(this.tableDataMachine);
      this.$refs.tableCar.reloadData(this.tableDataCar);
      this.$refs.tableMan.reloadData(this.tableDataMan);
      this.$refs.tableCarTwo.reloadData(this.tableDataCarTwo);
      this.$refs.tableCarOne.reloadData(this.tableDataCarOne);
    },
    editShipOne({ row }) {
      if (row.zySeqNo != null) {
        this.objNext = row;
        this.dialogFormVisibleShipOne = true;
      }
    },
    editShipTwo({ row }) {
      if (row.zySeqNo != null) {
        this.objNext = row;
        this.dialogFormVisibleShipTwo = true;
      }
    },
    editTrain({ row }) {
      if (row.zySeqNo != null) {
        this.objNext = row;
        this.dialogFormVisibleTrain = true;
      }
    },
    editCar({ row }) {
      if (row.zySeqNo != null) {
        this.objNext = row;
        this.dialogFormVisibleCar = true;
      }
    },
    editTrainThird({ row }) {
      if (row.zySeqNo != null) {
        this.objNext = row;
        this.dialogFormVisibleTrainThird = true;
      }
    },
    editTrainFourth() {
      if (this.dataForm.zySeqNo != null) {
        this.idNow = this.dataForm.zySeqNo;
        this.dialogFormVisibleTrainFourth = true;
      } else {
        this.$message.error("请先处理数据后进行维护");
      }
    },
    mergeMethodTrainOne({ row, column }) {
      const { mergeCellsTrainOne } = this;
      const xTable = this.$refs.tableTrainone;
      const _rowIndex = xTable._getRowIndex(row);
      const _columnIndex = xTable._getColumnIndex(column);
      for (let mIndex = 0; mIndex < mergeCellsTrainOne.length; mIndex++) {
        const { row, col, rowspan, colspan } = mergeCellsTrainOne[mIndex];
        if (row === _rowIndex && col === _columnIndex) {
          return { rowspan, colspan };
        }
        if (
          _rowIndex >= row &&
          _rowIndex < row + rowspan &&
          _columnIndex >= col &&
          _columnIndex < col + colspan
        ) {
          return { rowspan: 0, colspan: 0 };
        }
      }
      return { rowspan: 1, colspan: 1 };
    },
    mergeMethodTrainTwo({ row, column }) {
      const { mergeCellsTrainTwo } = this;
      const xTable = this.$refs.tableTraintwo;
      const _rowIndex = xTable._getRowIndex(row);
      const _columnIndex = xTable._getColumnIndex(column);
      for (let mIndex = 0; mIndex < mergeCellsTrainTwo.length; mIndex++) {
        const { row, col, rowspan, colspan } = mergeCellsTrainTwo[mIndex];
        if (row === _rowIndex && col === _columnIndex) {
          return { rowspan, colspan };
        }
        if (
          _rowIndex >= row &&
          _rowIndex < row + rowspan &&
          _columnIndex >= col &&
          _columnIndex < col + colspan
        ) {
          return { rowspan: 0, colspan: 0 };
        }
      }
      return { rowspan: 1, colspan: 1 };
    },
    mergeMethodTrainThird({ row, column }) {
      const { mergeCellsTrainThird } = this;
      const xTable = this.$refs.tableTrainThird;
      const _rowIndex = xTable._getRowIndex(row);
      const _columnIndex = xTable._getColumnIndex(column);
      for (let mIndex = 0; mIndex < mergeCellsTrainThird.length; mIndex++) {
        const { row, col, rowspan, colspan } = mergeCellsTrainThird[mIndex];
        if (row === _rowIndex && col === _columnIndex) {
          return { rowspan, colspan };
        }
        if (
          _rowIndex >= row &&
          _rowIndex < row + rowspan &&
          _columnIndex >= col &&
          _columnIndex < col + colspan
        ) {
          return { rowspan: 0, colspan: 0 };
        }
      }
      return { rowspan: 1, colspan: 1 };
    },
    mergeMethodTrainFourth({ row, column }) {
      const { mergeCellsTrainFourth } = this;
      const xTable = this.$refs.tableTrainFourth;
      const _rowIndex = xTable._getRowIndex(row);
      const _columnIndex = xTable._getColumnIndex(column);
      for (let mIndex = 0; mIndex < mergeCellsTrainFourth.length; mIndex++) {
        const { row, col, rowspan, colspan } = mergeCellsTrainFourth[mIndex];
        if (row === _rowIndex && col === _columnIndex) {
          return { rowspan, colspan };
        }
        if (
          _rowIndex >= row &&
          _rowIndex < row + rowspan &&
          _columnIndex >= col &&
          _columnIndex < col + colspan
        ) {
          return { rowspan: 0, colspan: 0 };
        }
      }
      return { rowspan: 1, colspan: 1 };
    },
    mergeMethodShipOne({ row, column }) {
      const { mergeCellsShipOne } = this;
      const xTable = this.$refs.tableShipone;
      const _rowIndex = xTable._getRowIndex(row);
      const _columnIndex = xTable._getColumnIndex(column);
      for (let mIndex = 0; mIndex < mergeCellsShipOne.length; mIndex++) {
        const { row, col, rowspan, colspan } = mergeCellsShipOne[mIndex];
        if (row === _rowIndex && col === _columnIndex) {
          return { rowspan, colspan };
        }
        if (
          _rowIndex >= row &&
          _rowIndex < row + rowspan &&
          _columnIndex >= col &&
          _columnIndex < col + colspan
        ) {
          return { rowspan: 0, colspan: 0 };
        }
      }
      return { rowspan: 1, colspan: 1 };
    },
    mergeMethodShipTwo({ row, column }) {
      const { mergeCellsShipTwo } = this;
      const xTable = this.$refs.tableShiptwo;
      const _rowIndex = xTable._getRowIndex(row);
      const _columnIndex = xTable._getColumnIndex(column);
      for (let mIndex = 0; mIndex < mergeCellsShipTwo.length; mIndex++) {
        const { row, col, rowspan, colspan } = mergeCellsShipTwo[mIndex];
        if (row === _rowIndex && col === _columnIndex) {
          return { rowspan, colspan };
        }
        if (
          _rowIndex >= row &&
          _rowIndex < row + rowspan &&
          _columnIndex >= col &&
          _columnIndex < col + colspan
        ) {
          return { rowspan: 0, colspan: 0 };
        }
      }
      return { rowspan: 1, colspan: 1 };
    },
    mergeMethodCar({ row, column }) {
      const { mergeCellsCar } = this;
      const xTable = this.$refs.tableCar;
      const _rowIndex = xTable._getRowIndex(row);
      const _columnIndex = xTable._getColumnIndex(column);
      for (let mIndex = 0; mIndex < mergeCellsCar.length; mIndex++) {
        const { row, col, rowspan, colspan } = mergeCellsCar[mIndex];
        if (row === _rowIndex && col === _columnIndex) {
          return { rowspan, colspan };
        }
        if (
          _rowIndex >= row &&
          _rowIndex < row + rowspan &&
          _columnIndex >= col &&
          _columnIndex < col + colspan
        ) {
          return { rowspan: 0, colspan: 0 };
        }
      }
      return { rowspan: 1, colspan: 1 };
    },
    mergeMethodMachine({ row, column }) {
      const { mergeCellsMachine } = this;
      const xTable = this.$refs.tableMachine;
      const _rowIndex = xTable._getRowIndex(row);
      const _columnIndex = xTable._getColumnIndex(column);
      for (let mIndex = 0; mIndex < mergeCellsMachine.length; mIndex++) {
        const { row, col, rowspan, colspan } = mergeCellsMachine[mIndex];
        if (row === _rowIndex && col === _columnIndex) {
          return { rowspan, colspan };
        }
        if (
          _rowIndex >= row &&
          _rowIndex < row + rowspan &&
          _columnIndex >= col &&
          _columnIndex < col + colspan
        ) {
          return { rowspan: 0, colspan: 0 };
        }
      }
      return { rowspan: 1, colspan: 1 };
    },
    mergeMethodMan({ row, column }) {
      const { mergeCellsMan } = this;
      const xTable = this.$refs.tableMan;
      const _rowIndex = xTable._getRowIndex(row);
      const _columnIndex = xTable._getColumnIndex(column);
      for (let mIndex = 0; mIndex < mergeCellsMan.length; mIndex++) {
        const { row, col, rowspan, colspan } = mergeCellsMan[mIndex];
        if (row === _rowIndex && col === _columnIndex) {
          return { rowspan, colspan };
        }
        if (
          _rowIndex >= row &&
          _rowIndex < row + rowspan &&
          _columnIndex >= col &&
          _columnIndex < col + colspan
        ) {
          return { rowspan: 0, colspan: 0 };
        }
      }
      return { rowspan: 1, colspan: 1 };
    },
    mergeMethodCarTwo({ row, column }) {
      const { mergeCellsCarTwo } = this;
      const xTable = this.$refs.tableCarTwo;
      const _rowIndex = xTable._getRowIndex(row);
      const _columnIndex = xTable._getColumnIndex(column);
      for (let mIndex = 0; mIndex < mergeCellsCarTwo.length; mIndex++) {
        const { row, col, rowspan, colspan } = mergeCellsCarTwo[mIndex];
        if (row === _rowIndex && col === _columnIndex) {
          return { rowspan, colspan };
        }
        if (
          _rowIndex >= row &&
          _rowIndex < row + rowspan &&
          _columnIndex >= col &&
          _columnIndex < col + colspan
        ) {
          return { rowspan: 0, colspan: 0 };
        }
      }
      return { rowspan: 1, colspan: 1 };
    },
    mergeMethodCarOne({ row, column }) {
      const { mergeCellsCarOne } = this;
      const xTable = this.$refs.tableCarOne;
      const _rowIndex = xTable._getRowIndex(row);
      const _columnIndex = xTable._getColumnIndex(column);
      for (let mIndex = 0; mIndex < mergeCellsCarOne.length; mIndex++) {
        const { row, col, rowspan, colspan } = mergeCellsCarOne[mIndex];
        if (row === _rowIndex && col === _columnIndex) {
          return { rowspan, colspan };
        }
        if (
          _rowIndex >= row &&
          _rowIndex < row + rowspan &&
          _columnIndex >= col &&
          _columnIndex < col + colspan
        ) {
          return { rowspan: 0, colspan: 0 };
        }
      }
      return { rowspan: 1, colspan: 1 };
    },
    print() {
      if (
        this.formObj.dateNow == null ||
        this.formObj.dateNow == "" ||
        this.formObj.dateNow == undefined
      ) {
        this.$message.error("请选择时间");
        return;
      }
      if (this.dataForm.zySeqNo == null) {
        this.$message.error("请处理当前时间数据后再打印");
      }
      window.open(
        "http://10.135.149.248:8080/webroot/decision/view/report?viewlet=ddzhpt/昼夜生产情况汇总.cpt&zy_seq_no=" +
          this.dataForm.zySeqNo +
          "&work_dte=" +
          moment(this.formObj.dateNow).format("YYYYMMDD")
      );
    },
    formatterUnit({ cellValue }) {
      if (cellValue == "51") {
        return "六公司";
      } else if (cellValue == "04") {
        return "一公司";
      } else if (cellValue == "46") {
        return "二公司";
      } else if (cellValue == "43") {
        return "粮食公司";
      } else if (cellValue == "67") {
        return "三公司";
      } else if (cellValue == "02") {
        return "三公司";
      } else if (cellValue == "16") {
        return "沈哈红运";
      } else if (cellValue == "99") {
        return "合计";
      }
    },
    formatterFocusId({ cellValue }) {
      if (cellValue == "1") {
        return "打印";
      } else if (cellValue == "0") {
        return "不打印";
      }
    },
    dealShip() {
      if (
        this.dataForm.zySeqNo == null ||
        this.dataForm.zySeqNo == "" ||
        this.dataForm.zySeqNo == undefined
      ) {
        this.$message.error("请选择时间查询后，进行单模块处理");
        return;
      }
      let loadingInstance = Loading.service({
        fullscreen: true,
        text: "正在处理………"
      });
      dealship(this.formObj.dateNow, this.dataForm.zySeqNo)
        .then(response => {
          this.$message.success("处理成功");
          this.shipOneList(this.dataForm.zySeqNo);
          this.shipTwoList(this.dataForm.zySeqNo);
          this.shipThirdList();
          loadingInstance.close();
        })
        .catch(() => {
          loadingInstance.close();
        });
    },
    dealTrain() {
      if (
        this.dataForm.zySeqNo == null ||
        this.dataForm.zySeqNo == "" ||
        this.dataForm.zySeqNo == undefined
      ) {
        this.$message.error("请选择时间查询后，进行单模块处理");
        return;
      }
      let loadingInstance = Loading.service({
        fullscreen: true,
        text: "正在处理………"
      });
      dealtrain(this.formObj.dateNow, this.dataForm.zySeqNo)
        .then(response => {
          this.$message.success("处理成功");
          this.trainOneList(this.dataForm.zySeqNo);
          this.trainTwoList(this.dataForm.zySeqNo);
          this.trainThirdList(this.dataForm.zySeqNo);
          this.trainFourthList(this.dataForm.zySeqNo);
          loadingInstance.close();
        })
        .catch(() => {
          loadingInstance.close();
        });
    },
    dealCar() {
      if (
        this.dataForm.zySeqNo == null ||
        this.dataForm.zySeqNo == "" ||
        this.dataForm.zySeqNo == undefined
      ) {
        this.$message.error("请选择时间查询后，进行单模块处理");
        return;
      }
      let loadingInstance = Loading.service({
        fullscreen: true,
        text: "正在处理………"
      });
      dealcar(this.formObj.dateNow, this.dataForm.zySeqNo)
        .then(response => {
          this.$message.success("处理成功");
          this.carOneList(this.dataForm.zySeqNo);
          this.carTwoList(this.dataForm.zySeqNo);
          loadingInstance.close();
        })
        .catch(() => {
          loadingInstance.close();
        });
    },
    printCar() {
      var endTime = moment(this.formObj.dateNow).format("YYYYMMDD");
      var beginTime = moment(this.formObj.dateNow)
        .add(-1, "d")
        .format("YYYYMMDD");
      endTime = endTime + "0659";
      beginTime = beginTime + "0700";
      window.open(
        "http://10.168.111.173:9081/Container/reportJsp/parameterReport.jsp?report=ykport0/checkPoint_dz&time_s=" +
          beginTime +
          "&time_e=" +
          endTime
      );
      return;
    },
    printCar2() {
      var endTime = moment(this.formObj.dateNow).format("YYYYMMDD");
      var beginTime = moment(this.formObj.dateNow)
        .add(-1, "d")
        .format("YYYYMMDD");
      endTime = endTime + "0659";
      beginTime = beginTime + "0700";
      window.open(
        "http://10.168.111.173:9081/Container/reportJsp/parameterReport.jsp?report=ykport0/checkPoint&time_s=" +
          beginTime +
          "&time_e=" +
          endTime
      );
      return;
    }
  }
};
</script>
<style lang="scss" scoped>
.zyworkgather {
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
</style>