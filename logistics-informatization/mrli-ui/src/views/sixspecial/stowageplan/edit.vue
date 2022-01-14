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
  <div class="vslstowageplanAdd">
    <el-row :gutter="5">
      <el-col :span="6">
        <!-- <vxe-form :data="shipObj" size="mini">
          <vxe-form-item title="船名：" field="shipNam" size="mini" span="10">
            <vxe-input
              v-model="shipObj.shipNam"
              clearable
              size="mini"
            ></vxe-input>
          </vxe-form-item>
          <vxe-form-item span="1" size="mini" align="center">
            <vxe-button
              status="primary"
              icon="fa fa-search"
              @click="getShipDate()"
              >查询</vxe-button
            >
          </vxe-form-item>
        </vxe-form> -->
        <vxe-table
          border
          show-overflow
          keep-source
          highlight-current-row
          size="mini"
          :data="tableShipData"
          :loading="tableShipload"
          :round="true"
          align="center"
          height="400px"
          ref="tableShip"
          @cell-click="rowclick"
          @cell-dblclick="cellDBLClickEvent"
        >
          <vxe-table-column
            field="shipNo"
            title="船号"
            width="100"
          ></vxe-table-column>
          <vxe-table-column
            field="shipNam"
            title="船名"
            width="120"
          ></vxe-table-column>
          <vxe-table-column
            field="berthCod"
            title="泊位"
            width="100"
          ></vxe-table-column>
        </vxe-table>
      </el-col>
      <el-col :span="18">
        <vxe-form :data="shipTonObj" size="mini">
          <!-- <vxe-form-item title="" field="shipNo" size="mini" span="3">
            <vxe-input
            v-model="shipTonObj.shipNo"
            clearable
            size="mini"
          ></vxe-input>
          </vxe-form-item>
          <vxe-form-item title="合同号：" field="shipNo" size="mini" span="3">
          <vxe-input
            v-model="shipTonObj.shipNo"
            clearable
            size="mini"
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="委托人：" field="shipNo" size="mini" span="3">
          <vxe-input
            v-model="shipTonObj.shipNo"
            clearable
            size="mini"
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="货主：" field="shipNo" size="mini" span="3">
          <vxe-input
            v-model="shipTonObj.shipNo"
            clearable
            size="mini"
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="作业地点：" field="shipNo" size="mini" span="3">
          <vxe-input
            v-model="shipTonObj.shipNo"
            clearable
            size="mini"
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="保管单位：" field="shipNo" size="mini" span="3">
          <vxe-input
            v-model="shipTonObj.shipNo"
            clearable
            size="mini"
          ></vxe-input>
        </vxe-form-item>-->
          <vxe-form-item title="提单：" field="billNo" size="mini" span="3">
            <vxe-input
              v-model="shipTonObj.billNo"
              clearable
              size="mini"
            ></vxe-input>
          </vxe-form-item>
          <!-- <vxe-form-item title="货名：" field="shipNo" size="mini" span="3">
          <vxe-input
            v-model="shipTonObj.shipNo"
            clearable
            size="mini"
          ></vxe-input>
        </vxe-form-item>-->
          <vxe-form-item span="1" size="mini" align="center">
            <vxe-button
              status="primary"
              icon="fa fa-search"
              @click="queryShipTonDate()"
              >查询</vxe-button
            >
          </vxe-form-item>
        </vxe-form>
        <vxe-table
          class="mytable-style"
          border
          show-overflow
          keep-source
          highlight-current-row
          size="mini"
          :data="tableShipTonData"
          :loading="tableShipTonload"
          :round="true"
          :row-class-name="rowClassName"
          :sort-config="{
            trigger: 'cell',
            defaultSort: { field: 'piecesNum', order: 'desc' },
            orders: ['desc', 'asc', null],
          }"
          align="center"
          height="400px"
          ref="tableShipTon"
          @cell-click="rowBodyclick"
          @cell-dblclick="cellBodyDBLClickEvent"
          @checkbox-change="selectChangeEvent"
        >
          <vxe-table-column type="checkbox" width="60"></vxe-table-column>
          <vxe-table-column
            field="contNo"
            title="合同号"
            width="100"
          ></vxe-table-column>
          <vxe-table-column
            field="trustMan"
            title="作业委托人"
            width="150"
          ></vxe-table-column>
          <vxe-table-column
            field="shipperDoc"
            title="港口货主"
            width="150"
          ></vxe-table-column>
          <vxe-table-column
            field="workPlace"
            title="作业地点"
            width="120"
          ></vxe-table-column>
          <vxe-table-column
            field="subunitNam"
            title="保管单位"
            width="120"
            sortable
            :filters="[{ data: '' }]"
            :filter-method="filterSubunitNamMethod"
          >
            <template #filter="{ $panel, column }">
              <select
                class="my-select"
                v-model="option.data"
                v-for="(option, index) in column.filters"
                :key="index"
                @change="$panel.changeOption($event, !!option.data, option)"
              >
                <option
                  v-for="(label, cIndex) in subunitNamList"
                  :key="cIndex"
                  :value="label"
                >
                  {{ label }}
                </option>
              </select>
            </template></vxe-table-column
          >
          <vxe-table-column
            field="billNo"
            title="提单"
            width="350"
          ></vxe-table-column>
          <vxe-table-column
            field="cargoNam"
            title="货名"
            width="100"
            sortable
            :filters="[{ data: '' }]"
            :filter-method="filterCargoNamMethod"
          >
            <template #filter="{ $panel, column }">
              <select
                class="my-select"
                v-model="option.data"
                v-for="(option, index) in column.filters"
                :key="index"
                @change="$panel.changeOption($event, !!option.data, option)"
              >
                <option
                  v-for="(label, cIndex) in cargoNamList"
                  :key="cIndex"
                  :value="label"
                >
                  {{ label }}
                </option>
              </select>
            </template></vxe-table-column
          >
          <vxe-table-column
            field="piecesNum"
            title="件数"
            width="70"
            sortable
          ></vxe-table-column>
          <vxe-table-column
            field="weightWgt"
            title="重量"
            width="80"
          ></vxe-table-column>
          <vxe-table-column
            field="cargoVol"
            title="体积"
            width="80"
          ></vxe-table-column>
        </vxe-table>
        <vxe-modal
          v-model="showEdit"
          title="编辑"
          width="800"
          min-width="600"
          min-height="300"
          :loading="submitLoading"
          resize
          destroy-on-close
        >
          <template v-slot>
            <vxe-form
              :data="formData"
              :items="formItems"
              title-align="right"
              title-width="100"
              @submit="submitEvent"
            ></vxe-form>
          </template>
        </vxe-modal>
      </el-col>
    </el-row>
    <el-row :gutter="15">
      <el-col :span="3">
        <vxe-table
          class="mytable-style"
          border
          show-overflow
          keep-source
          highlight-current-row
          size="mini"
          :data="tablePlanData1"
          :loading="tablePlanload"
          :round="true"
          :row-class-name="rowClassName3"
          align="center"
          height="400px"
          ref="tablePlanBody1"
          show-footer
          :footer-method="footerMethod"
          @cell-click="rowPlanobjclick"
          @cell-dblclick="cellPlanobjDBLClickEvent"
        >
          <vxe-table-column field="hatch" width="220">
            <template v-slot:header>
              <span style="color: red">1舱</span>
              <vxe-button
                type="text"
                icon="fa fa-plus"
                @click="putIn(1)"
              ></vxe-button>
              <vxe-button
                type="text"
                icon="fa fa-minus"
                @click="getOut(1)"
              ></vxe-button>
            </template>
          </vxe-table-column>
          <vxe-table-column field="piecesNum" title="件"> </vxe-table-column>
          <vxe-table-column field="weightWgt" title="重"> </vxe-table-column>
        </vxe-table>
      </el-col>
      <el-col :span="3">
        <vxe-table
          class="mytable-style"
          border
          show-overflow
          keep-source
          highlight-current-row
          size="mini"
          :data="tablePlanData2"
          :loading="tablePlanload"
          :round="true"
          :row-class-name="rowClassName3"
          align="center"
          height="400px"
          ref="tablePlanBody2"
          show-footer
          :footer-method="footerMethod"
          @cell-click="rowPlanobjclick"
          @cell-dblclick="cellPlanobjDBLClickEvent"
        >
          <vxe-table-column field="hatch" width="220">
            <template v-slot:header>
              <span style="color: red">2舱</span>
              <vxe-button
                type="text"
                icon="fa fa-plus"
                @click="putIn(2)"
              ></vxe-button>
              <vxe-button
                type="text"
                icon="fa fa-minus"
                @click="getOut(2)"
              ></vxe-button>
            </template>
          </vxe-table-column>
          <vxe-table-column field="piecesNum" title="件"> </vxe-table-column>
          <vxe-table-column field="weightWgt" title="重"> </vxe-table-column>
        </vxe-table>
      </el-col>
      <el-col :span="3">
        <vxe-table
          class="mytable-style"
          border
          show-overflow
          keep-source
          highlight-current-row
          size="mini"
          :data="tablePlanData3"
          :loading="tablePlanload"
          :round="true"
          :row-class-name="rowClassName3"
          align="center"
          height="400px"
          ref="tablePlanBody3"
          show-footer
          :footer-method="footerMethod"
          @cell-click="rowPlanobjclick"
          @cell-dblclick="cellPlanobjDBLClickEvent"
        >
          <vxe-table-column field="hatch" width="220">
            <template v-slot:header>
              <span style="color: red">3舱</span>
              <vxe-button
                type="text"
                icon="fa fa-plus"
                @click="putIn(3)"
              ></vxe-button>
              <vxe-button
                type="text"
                icon="fa fa-minus"
                @click="getOut(3)"
              ></vxe-button>
            </template>
          </vxe-table-column>
          <vxe-table-column field="piecesNum" title="件"> </vxe-table-column>
          <vxe-table-column field="weightWgt" title="重"> </vxe-table-column>
        </vxe-table>
      </el-col>
      <el-col :span="3">
        <vxe-table
          class="mytable-style"
          border
          show-overflow
          keep-source
          highlight-current-row
          size="mini"
          :data="tablePlanData4"
          :loading="tablePlanload"
          :round="true"
          :row-class-name="rowClassName3"
          align="center"
          height="400px"
          ref="tablePlanBody4"
          show-footer
          :footer-method="footerMethod"
          @cell-click="rowPlanobjclick"
          @cell-dblclick="cellPlanobjDBLClickEvent"
        >
          <vxe-table-column field="hatch" width="220">
            <template v-slot:header>
              <span style="color: red">4舱</span>
              <vxe-button
                type="text"
                icon="fa fa-plus"
                @click="putIn(4)"
              ></vxe-button>
              <vxe-button
                type="text"
                icon="fa fa-minus"
                @click="getOut(4)"
              ></vxe-button>
            </template>
          </vxe-table-column>
          <vxe-table-column field="piecesNum" title="件"> </vxe-table-column>
          <vxe-table-column field="weightWgt" title="重"> </vxe-table-column>
        </vxe-table>
      </el-col>
      <el-col :span="3">
        <vxe-table
          class="mytable-style"
          border
          show-overflow
          keep-source
          highlight-current-row
          size="mini"
          :data="tablePlanData5"
          :loading="tablePlanload"
          :round="true"
          :row-class-name="rowClassName3"
          align="center"
          height="400px"
          ref="tablePlanBody5"
          show-footer
          :footer-method="footerMethod"
          @cell-click="rowPlanobjclick"
          @cell-dblclick="cellPlanobjDBLClickEvent"
        >
          <vxe-table-column field="hatch" width="220">
            <template v-slot:header>
              <span style="color: red">5舱</span>
              <vxe-button
                type="text"
                icon="fa fa-plus"
                @click="putIn(5)"
              ></vxe-button>
              <vxe-button
                type="text"
                icon="fa fa-minus"
                @click="getOut(5)"
              ></vxe-button>
            </template>
          </vxe-table-column>
          <vxe-table-column field="piecesNum" title="件"> </vxe-table-column>
          <vxe-table-column field="weightWgt" title="重"> </vxe-table-column>
        </vxe-table>
      </el-col>
      <el-col :span="3">
        <vxe-table
          class="mytable-style"
          border
          show-overflow
          keep-source
          highlight-current-row
          size="mini"
          :data="tablePlanData6"
          :loading="tablePlanload"
          :round="true"
          :row-class-name="rowClassName3"
          align="center"
          height="400px"
          ref="tablePlanBody6"
          show-footer
          :footer-method="footerMethod"
          @cell-click="rowPlanobjclick"
          @cell-dblclick="cellPlanobjDBLClickEvent"
        >
          <vxe-table-column field="hatch" width="220">
            <template v-slot:header>
              <span style="color: red">6舱</span>
              <vxe-button
                type="text"
                icon="fa fa-plus"
                @click="putIn(6)"
              ></vxe-button>
              <vxe-button
                type="text"
                icon="fa fa-minus"
                @click="getOut(6)"
              ></vxe-button>
            </template>
          </vxe-table-column>
          <vxe-table-column field="piecesNum" title="件"> </vxe-table-column>
          <vxe-table-column field="weightWgt" title="重"> </vxe-table-column>
        </vxe-table>
      </el-col>
      <el-col :span="3">
        <vxe-table
          class="mytable-style"
          border
          show-overflow
          keep-source
          highlight-current-row
          size="mini"
          :data="tablePlanData7"
          :loading="tablePlanload"
          :round="true"
          :row-class-name="rowClassName3"
          align="center"
          height="400px"
          ref="tablePlanBody7"
          show-footer
          :footer-method="footerMethod"
          @cell-click="rowPlanobjclick"
          @cell-dblclick="cellPlanobjDBLClickEvent"
        >
          <vxe-table-column field="hatch" width="220">
            <template v-slot:header>
              <span style="color: red">7舱</span>
              <vxe-button
                type="text"
                icon="fa fa-plus"
                @click="putIn(7)"
              ></vxe-button>
              <vxe-button
                type="text"
                icon="fa fa-minus"
                @click="getOut(7)"
              ></vxe-button>
            </template>
          </vxe-table-column>
          <vxe-table-column field="piecesNum" title="件"> </vxe-table-column>
          <vxe-table-column field="weightWgt" title="重"> </vxe-table-column>
        </vxe-table>
      </el-col>
      <el-col :span="3">
        <vxe-table
          class="mytable-style"
          border
          show-overflow
          keep-source
          highlight-current-row
          size="mini"
          :data="tablePlanData8"
          :loading="tablePlanload"
          :round="true"
          :row-class-name="rowClassName3"
          align="center"
          height="400px"
          ref="tablePlanBody8"
          show-footer
          :footer-method="footerMethod"
          @cell-click="rowPlanobjclick"
          @cell-dblclick="cellPlanobjDBLClickEvent"
        >
          <vxe-table-column field="hatch" width="220">
            <template v-slot:header>
              <span style="color: red">8舱</span>
              <vxe-button
                type="text"
                icon="fa fa-plus"
                @click="putIn(8)"
              ></vxe-button>
              <vxe-button
                type="text"
                icon="fa fa-minus"
                @click="getOut(8)"
              ></vxe-button>
            </template>
          </vxe-table-column>
          <vxe-table-column field="piecesNum" title="件"> </vxe-table-column>
          <vxe-table-column field="weightWgt" title="重"> </vxe-table-column>
        </vxe-table>
      </el-col>
    </el-row>
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
  </div>
</template>

<script>
import {
  shipListByShipno,
  shipTonList,
  shipTonListEdit,
  saveEdit,
  ListB,
} from "@/api/sixspecial/stowageplan";
import request from "@/router/axios";
import "@/styles/common/edit.scss";
import { mapGetters } from "vuex";
import moment from "moment";
import { mapState } from "vuex";
import Vue from "vue";
import VXETable from "vxe-table";
import hljs from "highlight.js";
import XEUtils from "xe-utils";
import { Loading, Message } from "element-ui";
import { xj, jf } from "@/util/util";
import EditDown from "../../baseUtile/EditDown";
export default {
  props: ["datanow"],
  components: { EditDown },
  data() {
    return {
      shipObj: {},
      shipAddObj: {},
      shipTonObj: {},
      shipTonAddObj: {},
      planBodyObj: {},
      planBodyAddObj: {},
      formRules: {
        // csid: [{ required: true, message: "请选择水尺" }],
      },
      // amtAll: 0,
      // weightAll: 0,
      textnum: 1,
      hatchNo: null,
      tableShipData: [],
      tableShipTonData: [],
      PlanBodyListData: [],
      subunitNamList: [],
      cargoNamList: [],
      tablePlanData: [],
      tablePlanData1: [],
      tablePlanData2: [],
      tablePlanData3: [],
      tablePlanData4: [],
      tablePlanData5: [],
      tablePlanData6: [],
      tablePlanData7: [],
      tablePlanData8: [],
      planBodySaveData: [],
      planBodySaveDataTmp: [],
      tableShipload: false,
      tableShipTonload: false,
      tablePlanload: false,
      submitLoading: false,
      showEdit: false,
      dataForm: {
        shiperCod: "",
        shiperNam: "",
        unitCod: "",
        unitNam: "",
        portUnitCod: "",
        portUnitNam: "",
        billTitleA: "",
        shipNo: "",
      },
      formData: {
        billNo: null,
        cargoNam: null,
        piecesNum: null,
        subunitNam: null,
      },
      formItems: [
        {
          field: "billNo",
          title: "提单",
          span: 12,
          itemRender: { name: "$input" },
        },
        {
          field: "cargoNam",
          title: "货名",
          span: 12,
          itemRender: { name: "$input" },
        },
        {
          field: "piecesNum",
          title: "件数",
          span: 12,
          itemRender: { name: "$input", props: { placeholder: "请输入件数" } },
        },
        {
          field: "subunitNam",
          title: "保管单位",
          span: 12,
          itemRender: { name: "$input" },
        },
        {
          field: "hatch",
          title: "舱号",
          span: 12,
          itemRender: { name: "$input" },
        },
        {
          align: "center",
          span: 24,
          titleAlign: "left",
          itemRender: {
            name: "$buttons",
            children: [
              { props: { type: "submit", content: "确定", status: "primary" } },
              // { props: { type: "reset", content: "取消" } },
            ],
          },
        },
        // { field: 'name', title: 'Name', span: 12, itemRender: { name: '$input', props: { placeholder: '请输入名称' } } },
        // { field: 'name', title: 'Name', span: 12, itemRender: { name: '$input', props: { placeholder: '请输入名称' } } },
        // { field: 'name', title: 'Name', span: 12, itemRender: { name: '$input', props: { placeholder: '请输入名称' } } },
      ],
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
    ...mapState({
      userInfo: (state) => state.user.userInfo,
    }),
  },
  created() {
    this.ajaxOver = true;
    this.dataForm = this.datanow;
    this.getShipDate();
    this.getPlanBodyList();
    // this.getShipTonDate();
  },
  mounted: function () {},
  methods: {
    getShipDate() {
      this.tableShipload = true;
      // this.dataHeadNow = {};
      shipListByShipno(
        Object.assign({
          shipNo: this.datanow.shipNo,
        })
      )
        .then((response) => {
          // console.log(response);
          this.tableShipData = response.data.data;
          // this.tablePage.total = response.data.data.total;
          this.tableShipload = false;
          this.$refs.tableShip.setCurrentRow(this.tableShipData[0]);
          this.getShipTonDate(response.data.data[0]);
        })
        .catch(() => {
          this.tableShipload = false;
        });
    },
    getShipTonDate(row) {
      this.shipAddObj = row;
      // console.log(row);
      shipTonListEdit(
        Object.assign({
          shipNo: row.shipNo,
        })
      )
        .then((response) => {
          this.tableShipTonData = response.data.data;
          //过滤保管单位和货名，放到对应的过滤器中
          var listSubmitNam = [this.tableShipTonData[0].subunitNam];
          var listCargoNam = [this.tableShipTonData[0].cargoNam];
          for (var i = 1; i < this.tableShipTonData.length; i++) {
            var ifSa = false;
            var ifSb = false;
            for (var j = 0; j < listSubmitNam.length; j++) {
              if (this.tableShipTonData[i].subunitNam === listSubmitNam[j]) {
                ifSa = true;
                break;
              }
            }
            if (!ifSa) {
              listSubmitNam.push(this.tableShipTonData[i].subunitNam);
            }

            for (var k = 0; k < listCargoNam.length; k++) {
              if (this.tableShipTonData[i].cargoNam === listCargoNam[k]) {
                ifSb = true;
                break;
              }
            }
            if (!ifSb) {
              listCargoNam.push(this.tableShipTonData[i].cargoNam);
            }
          }
          this.subunitNamList = listSubmitNam;
          this.cargoNamList = listCargoNam;
          this.tableShipTonload = false;
        })
        .catch(() => {
          this.tableShipTonload = false;
        });
    },
    queryShipTonDate() {
      shipTonList(
        Object.assign({
          shipNo: this.datanow.shipNo,
          billNo: this.shipTonObj.billNo,
        })
      )
        .then((response) => {
          this.tableShipTonData = response.data.data;
          this.tableShipTonData.forEach((item) => {
            this.planBodySaveData.forEach((row) => {
              if (
                item.billNo === row.billNo &&
                item.cargoNo === row.cargoNo &&
                item.contNo === row.contNo &&
                item.shipNo === row.shipNo &&
                item.workPlaceCod === row.workPlaceCod
              ) {
                item.piecesNum = item.piecesNum - row.piecesNumNew;
                item.weightWgt = item.weightWgt - row.weightWgtNew;
              }
            });
          });
          this.tableShipTonload = false;
        })
        .catch(() => {
          this.tableShipTonload = false;
        });
    },
    getPlanBodyList() {
      ListB(this.datanow.pkid)
        .then((response) => {
          this.PlanBodyListData = response.data.data;
          this.PlanBodyListData.forEach((shipTonAddObj) => {
            const objString =
              shipTonAddObj.billNo +
              "/" +
              shipTonAddObj.cargoNam +
              "/" +
              shipTonAddObj.piecesNum +
              "/" +
              shipTonAddObj.weightWgt +
              "/" +
              shipTonAddObj.subunitNam;
            shipTonAddObj.piecesNumNew = shipTonAddObj.piecesNum;
            shipTonAddObj.weightWgtNew = shipTonAddObj.weightWgt;
            // this.shipTonAddObj.hatchNo = this.hatchNo;
            // this.shipTonAddObj.piecesNum = this.formData.piecesNum;
            const newRow = {
              hatch: objString,
              piecesNum: shipTonAddObj.piecesNum,
              weightWgt: shipTonAddObj.weightWgt,
              billNo: shipTonAddObj.billNo,
              cargoNo: shipTonAddObj.cargoNo,
              contNo: shipTonAddObj.contNo,
              shipNo: shipTonAddObj.shipNo,
              workPlaceCod: shipTonAddObj.workPlaceCod,
              ifPlan: shipTonAddObj.ifPlan,
              // date: XEUtils.toDateString(new Date(), "yyyy-MM-dd"),
            };
            this.$refs["tablePlanBody" + shipTonAddObj.hatchNo]
              .insert(newRow)
              .then(() =>
                this.$refs[
                  "tablePlanBody" + shipTonAddObj.hatchNo
                ].setActiveRow(newRow)
              );
            this.planBodySaveData.push(shipTonAddObj);
          });
          // this.tablePage.total = response.data.data.total;
          this.tableShipTonload = false;
        })
        .catch(() => {
          this.tableShipTonload = false;
        });
    },
    handleSubmit() {
      if (this.shipAddObj.shipNo == null || this.planBodySaveData.length == 0) {
        this.$message.error("请选择到船登记账信息");
        return;
      }
      // console.log(this.shipAddObj.shipNo==null);
      // console.log(this.planBodySaveData.length==0);
      let loadingInstance = Loading.service({
        fullscreen: true,
        text: "正在保存………",
      });
      // this.tableDataTankSave = this.$refs.tableTank.getCheckboxRecords();
      // console.log(tableDataTankSave);
      // if (this.tableDataTankSave.length == 0) {
      //   this.$message.error("租罐信息不能无选中信息");
      //   return;
      // }

      saveEdit(this.datanow, this.planBodySaveData)
        .then((data) => {
          this.$message.success("保存成功");
          loadingInstance.close();
          this.$emit("selectListplan");
          this.$emit("dictItemVisible");
        })
        .catch(() => {
          loadingInstance.close();
        });
    },
    rowclick({ row }) {
      this.shipAddObj = row;
      shipTonList(
        Object.assign({
          shipNo: row.shipNo,
        })
      )
        .then((response) => {
          this.tableShipTonData = response.data.data;
          // this.tablePage.total = response.data.data.total;
          this.tableShipTonload = false;
        })
        .catch(() => {
          this.tableShipTonload = false;
        });

      // planList(
      //   Object.assign({
      //     shipNo: row.shipNo,
      //   })
      // )
      //   .then((response) => {
      //     console.log(response);
      //     this.tableShipTonData = response.data.data;
      //     // this.tablePage.total = response.data.data.total;
      //     this.tablePlanload = false;
      //   })
      //   .catch(() => {
      //     this.tablePlanload = false;
      //   });
    },
    putIn(no) {
      let selectRecords = this.$refs.tableShipTon.getCheckboxRecords();
      this.hatchNo = no;
      if (0 == selectRecords.length) {
        this.formData = {
          billNo: this.shipTonAddObj.billNo,
          cargoNam: this.shipTonAddObj.cargoNam,
          piecesNum: this.shipTonAddObj.piecesNum,
          subunitNam: this.shipTonAddObj.subunitNam,
          hatch: no,
        };
        if (
          this.shipTonAddObj.piecesNum === null ||
          this.shipTonAddObj.piecesNum === 0
        ) {
          this.$message.error("件数为0，不能操作");
          return;
        }
        // this.selectRow = row;
        this.showEdit = true;
      } else {
        const tablePlanBody = this.$refs["tablePlanBody" + this.hatchNo];
        selectRecords.forEach((shipTonAddObj) => {
          const tablePlanBody = this.$refs["tablePlanBody" + this.hatchNo];
          const objString =
            shipTonAddObj.billNo +
            "/" +
            shipTonAddObj.cargoNam +
            "/" +
            shipTonAddObj.piecesNum +
            "/" +
            shipTonAddObj.weightWgt +
            "/" +
            shipTonAddObj.subunitNam;
          if (
            shipTonAddObj.piecesNum === null ||
            shipTonAddObj.piecesNum === 0
          ) {
            this.$message.error("件数为0，不能操作");
            return;
          }
          shipTonAddObj.hatchNo = this.hatchNo;
          shipTonAddObj.piecesNumNew = shipTonAddObj.piecesNum;
          shipTonAddObj.piecesNum = 0;
          shipTonAddObj.weightWgtNew = shipTonAddObj.weightWgt;
          shipTonAddObj.weightWgt = 0;

          shipTonAddObj.ifNew = 0;
          shipTonAddObj.ifDel = 0;
          // if (shipTonAddObj.piecesNum < 0) {
          //   this.$message.error("件数不能大于当前剩余件数");
          //   return;
          // }
          // shipTonAddObj.hatchNo = this.hatchNo;
          // shipTonAddObj.piecesNum = this.formData.piecesNum;
          const newRow = {
            hatch: objString,
            piecesNum: this.shipTonAddObj.piecesNumNew,
            weightWgt: this.shipTonAddObj.weightWgtNew,
            billNo: shipTonAddObj.billNo,
            cargoNo: shipTonAddObj.cargoNo,
            contNo: shipTonAddObj.contNo,
            shipNo: shipTonAddObj.shipNo,
            workPlaceCod: shipTonAddObj.workPlaceCod,
            // date: XEUtils.toDateString(new Date(), "yyyy-MM-dd"),
          };
          tablePlanBody
            .insert(newRow)
            .then(() => tablePlanBody.setActiveRow(newRow));
          this.planBodySaveData.push(shipTonAddObj);
          this.$refs.tableShipTon.reloadData(this.tableShipTonData);
          // piecesNum = piecesNum + this.shipTonAddObj.piecesNum;
        });
      }
    },
    submitEvent() {
      var jsonval = JSON.parse(JSON.stringify(this.shipTonAddObj));

      const tablePlanBody = this.$refs["tablePlanBody" + this.hatchNo];
      if (jsonval.piecesNum - this.formData.piecesNum > 0) {
        jsonval.weightWgtNew = jsonval.piecesWeight * this.formData.piecesNum;
        this.shipTonAddObj.weightWgt =
          this.shipTonAddObj.weightWgt - jsonval.weightWgtNew;
      } else if (jsonval.piecesNum - this.formData.piecesNum == 0) {
        jsonval.weightWgtNew =
          jsonval.piecesWeight * this.formData.piecesNum +
          jsonval.lastPiecesWeight;
        this.shipTonAddObj.weightWgt = 0;
      }
      const objString =
        jsonval.billNo +
        "/" +
        jsonval.cargoNam +
        "/" +
        this.formData.piecesNum +
        "/" +
        jsonval.weightWgtNew +
        "/" +
        jsonval.subunitNam;

      jsonval.hatchNo = this.hatchNo;
      this.shipTonAddObj.piecesNum =
        jsonval.piecesNum - this.formData.piecesNum;
      jsonval.piecesNumNew = this.formData.piecesNum;

      jsonval.ifNew = 0;
      jsonval.ifDel = 0;

      if (jsonval.piecesNum < 0) {
        this.$message.error("件数不能大于当前剩余件数");
        return;
      }
      const newRow = {
        hatch: objString,
        piecesNum: jsonval.piecesNumNew,
        weightWgt: jsonval.weightWgtNew,
        billNo: jsonval.billNo,
        cargoNo: jsonval.cargoNo,
        contNo: jsonval.contNo,
        shipNo: jsonval.shipNo,
        workPlaceCod: jsonval.workPlaceCod,
        // date: XEUtils.toDateString(new Date(), "yyyy-MM-dd"),
      };
      tablePlanBody
        .insert(newRow)
        .then(() => tablePlanBody.setActiveRow(newRow));
      this.planBodySaveData.push(jsonval);
      this.$refs.tableShipTon.reloadData(this.tableShipTonData);
      // console.log(this.planBodySaveData);
      this.jsonval = null;
      this.showEdit = false;
    },
    getOut(no) {
      const row = this.planBodyObj;
      if (row.ifPlan == "1") {
        this.$message.error("已生成配载计划不能删除");
        return;
      }
      // console.log(row);
      const tablePlanBody = this.$refs["tablePlanBody" + no];
      tablePlanBody.remove(row);
      this.planBodySaveDataTmp = [];
      this.planBodySaveData.forEach((tmp) => {
        if (
          tmp.billNo === row.billNo &&
          tmp.cargoNo === row.cargoNo &&
          tmp.contNo === row.contNo &&
          tmp.shipNo === row.shipNo &&
          tmp.workPlaceCod === row.workPlaceCod
        ) {
          if (tmp.ifNew == "1") {
            console.log("123");
            tmp.ifDel = "1";
            this.planBodySaveDataTmp.push(tmp);
            console.log(this.planBodySaveDataTmp);
          }
        } else {
          this.planBodySaveDataTmp.push(tmp);
        }
      });
      this.planBodySaveData = this.planBodySaveDataTmp;
      this.tableShipTonData.forEach((item) => {
        if (
          item.billNo === row.billNo &&
          item.cargoNo === row.cargoNo &&
          item.contNo === row.contNo &&
          item.shipNo === row.shipNo &&
          item.workPlaceCod === row.workPlaceCod
        ) {
          item.piecesNum = item.piecesNum + row.piecesNum;
          item.weightWgt = item.weightWgt + row.weightWgt;
        }
      });
      this.$refs.tableShipTon.reloadData(this.tableShipTonData);
    },
    sumNum(list, field) {
      let count = 0;
      list.forEach((item) => {
        count += Number(item[field]);
      });
      return count;
    },
    footerMethod({ columns, data }) {
      const means = [];
      let sumCell = null;
      let sumCellweight = null;
      columns.forEach((column, columnIndex) => {
        if (columnIndex === 0) {
          // means.push("件数");
        } else {
          if (["piecesNum"].includes(column.property)) {
            sumCell = this.sumNum(data, column.property);
          }
          if (["weightWgt"].includes(column.property)) {
            sumCellweight = this.sumNum(data, column.property);
          }
        }
      });
      means.push("件数:" + sumCell + ",重量:" + sumCellweight);
      // 返回一个二维数组的表尾合计
      return [means];
    },
    selectChangeEvent({ checked, records }) {
      console.log(checked ? "勾选事件" : "取消事件", records);
    },
    rowClassName({ row, rowIndex }) {
      if (
        row.piecesNum === null ||
        row.piecesNum === "" ||
        row.piecesNum === 0
      ) {
        return "row-red";
      }
    },
    rowClassName3({ row, rowIndex }) {
      if (row.ifPlan === "1") {
        return "row-red";
      }
    },
    // cellStyle({ row, rowIndex, column }) {
    //   if (column.property === "piecesNum") {
    //     if (
    //       row.piecesNum === null ||
    //       row.piecesNum === "" ||
    //       row.piecesNum === 0
    //     ) {
    //       console.log(1);
    //       return {
    //         backgroundColor: "red",
    //         color: "red",
    //       };
    //     }
    //   }
    // },
    cellDBLClickEvent({ row }) {
      this.datanow = row;
      // this.dialogFormVisibleView = true;
    },
    rowBodyclick({ row }) {
      this.shipTonAddObj = row;
    },
    cellBodyDBLClickEvent({ row }) {
      this.shipTonAddObj = row;
      // this.dialogFormVisibleView = true;
    },
    rowPlanobjclick({ row }) {
      this.planBodyObj = row;
    },
    cellPlanobjDBLClickEvent({ row }) {
      this.planBodyObj = row;
      // this.dialogFormVisibleView = true;
    },
    filterCargoNamMethod({ option, row }) {
      return row.cargoNam === option.data;
    },
    filterSubunitNamMethod({ option, row }) {
      return row.subunitNam === option.data;
    },
    closeWindows() {
      this.$emit("selectListplan");
      this.$emit("dictItemVisible");
    },
  },
};
</script>
<style>
.my-select {
  margin: 10px;
  width: 100px;
  height: 32px;
}
.mytable-style .vxe-body--row.row-red {
  background-color: red;
  color: #fff;
}
</style>