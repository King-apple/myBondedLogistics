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
  <div class="feemain">
    <el-collapse v-model="activeNames">
      <el-collapse-item title="客户信息" name="1">
        <div>
          <avue-form :option="option" v-model="obj" ref="khForm"></avue-form>
        </div>
      </el-collapse-item>
      <el-collapse-item title="费目明细" name="2">
        <vxe-toolbar perfect size="mini">
          <template v-slot:buttons>
            <vxe-button
              v-if="addBtn"
              type="text"
              class="fa fa-plus"
              @click="insertEvent()"
              >新增</vxe-button
            >
            <vxe-button
              v-if="addBtn"
              type="text"
              icon="fa fa-trash"
              @click="$refs.xTable.removeCheckboxRow()"
              >删除</vxe-button
            >
          </template>
        </vxe-toolbar>
        <vxe-table
          border
          show-overflow
          keep-source
          ref="xTable"
          size="mini"
          :data="tableData"
          height="180px"
          :mouse-config="{ selected: true }"
          :checkbox-config="{ range: true }"
          :edit-rules="validRules"
          :edit-config="{ trigger: 'click', showStatus: true, mode: 'row' }"
        >
          <!--快捷键编辑:keyboard-config="{isArrow: true, isDel: true, isEnter: true, isTab: true, isEdit: true}"-->
          <vxe-table-column type="checkbox" width="60"></vxe-table-column>
          <vxe-table-column field="feeCod" title="费目代码"></vxe-table-column>
          <vxe-table-column
            field="feeNam"
            title="费目名称"
            :edit-render="{
              name: 'EditDownTable',
              props: {
                checkField: 'feeNam',
                tableColumn: this.downTableOption.feeColum,
                urlapi: this.downTableOption.feeurl,
                returnApi: this.downTableOption.returnApi,
              },
            }"
          ></vxe-table-column>
          <vxe-table-column
            field="amount"
            title="数量"
            :edit-render="{
              name: '$input',
              props: { type: 'float', step: '1' },
            }"
          ></vxe-table-column>
          <vxe-table-column
            field="fat"
            title="费率"
            :edit-render="{ name: 'input', defaultValue: 0 }"
          ></vxe-table-column>
          <vxe-table-column
            field="feeMny"
            title="金额"
            :edit-render="{ name: 'input' }"
          ></vxe-table-column>
        </vxe-table>
      </el-collapse-item>
      <el-collapse-item v-if="obj.bj == '正常'" title="船舶信息" name="3">
        <avue-form :option="optionShip" v-model="obj"></avue-form>
      </el-collapse-item>
      <el-collapse-item v-if="obj.bj == '补交'" title="其他信息" name="3">
        <avue-form :option="optionQt" v-model="obj">
          <template slot-scope="scope" slot="bussMan">
            <EditDown
              :objs="obj"
              :property="downTableOption.bussmanname"
              :tableColumns="downTableOption.bussmanColum"
              :urlapi="downTableOption.bussmanurl"
              :returnApi="downTableOption.bussmanreturnApi"
            ></EditDown>
          </template>
          <template slot-scope="scope" slot="planMan">
            <EditDown
              :objs="obj"
              :property="downTableOption.planmanname"
              :tableColumns="downTableOption.bussmanColum"
              :urlapi="downTableOption.planmanurl"
              :returnApi="downTableOption.planmanreturnApi"
            ></EditDown>
          </template>
        </avue-form>
      </el-collapse-item>
    </el-collapse>
    <div align="center">
      <el-button
        v-if="!ifView"
        type="primary"
        size="mini"
        icon="el-icon-check"
        @click="handleSubmit"
        :disabled="buttonload"
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
  getAgentObj,
  commitFee,
  getfeeList,
  getWFeeBillList,
} from "@/api/ship/cdshipgrpt";
import request from "@/router/axios";
import "@/styles/common/edit.scss";
import moment from "moment";
import { mapGetters } from "vuex";
import EditDownTable from "../../baseUtile/EditDownTable";
import EditDown from "../../baseUtile/EditDown";
import Vue from "vue";
import VXETable from "vxe-table";
import hljs from "highlight.js";
import { Loading } from "element-ui";
import { mapState } from "vuex";
Vue.component(EditDownTable.name, EditDownTable);
// 创建一个下拉表格渲染
VXETable.renderer.add("EditDownTable", {
  autofocus: ".vxe-input--inner",
  renderEdit(h, renderOpts, params) {
    return [
      <edit-down-table
        params={params}
        renderOpts={renderOpts}
      ></edit-down-table>,
    ];
  },
});

export default {
  props: ["objrow", "objshipnew", "ifView"],
  components: { EditDown },
  data() {
    const feeMnyValid = ({ cellValue }) => {
      return new Promise((resolve, reject) => {
        var regNeg = /^[0-9]+.?[0-9]*$/;
        if (regNeg.test(cellValue)) {
          resolve();
        } else {
          reject(new Error("格式不对"));
        }
      });
    };
    return {
      obj: {},
      tableData: [],
      buttonload: false,
      activeNames: ["1", "2", "3"],
      validRules: {
        feeNam: [{ required: true, message: "必须选择费目" }],
        feeMny: [
          { required: true, message: "必须录入金额" },
          { validator: feeMnyValid },
        ],
        amount: [{ required: true, message: "必须填写数量" }],
      },
      downTableOption: {
        //费目下拉菜单展示项
        feeColum: [
          { field: "feeNameCod", title: "费目代码" },
          { field: "cfeeName", title: "费目名称" },
        ],
        //费目下拉菜单的返回项
        returnApi: [
          { field: "feeNameCod", returnName: "feeCod" },
          { field: "cfeeName", returnName: "feeNam" },
        ],
        //费目下拉菜单的路径
        feeurl: "/ship/baseCod/feecod/page",
        bussmanname: "bussMan",
        //业务员下拉菜单展示项
        bussmanColum: [
          { field: "OPER_NAM", title: "业务员" },
          { field: "AUTH_TYP", title: "职位" },
        ],
        //下拉菜单的返回项
        bussmanreturnApi: [{ field: "OPER_NAM", returnName: "bussMan" }],
        //下拉菜单的路径
        bussmanurl: "/ship/baseCod/bussMan/page?sqlParam=1",
        planmanname: "planMan",
        planmanreturnApi: [{ field: "OPER_NAM", returnName: "planMan" }],
        planmanurl: "/ship/baseCod/bussMan/page?sqlParam=2",
      },
      optionQt: {
        emptyBtn: false,
        submitBtn: false,
        size: "mini",
        column: [
          {
            type: "input",
            label: "公司名称",
            prop: "unitName",
            disabled: true,
            placeholder: " ",
            display: true,
            span: 6,
            readonly: true,
          },
          {
            type: "input",
            label: "负责人",
            prop: "takeMan",
            placeholder: " ",
            display: true,
            span: 6,
          },
          {
            type: "date",
            label: "报价日期",
            prop: "payDate",
            placeholder: " ",
            display: true,
            span: 6,
            format: "yyyy-MM-dd",
            valueFormat: "yyyy-MM-dd",
          },
          {
            type: "input",
            label: "电话",
            prop: "takePhone",
            placeholder: " ",
            display: true,
            span: 6,
          },
          {
            type: "input",
            label: "传真",
            prop: "takeFax",
            placeholder: " ",
            display: true,
            span: 6,
          },
          {
            type: "input",
            label: "船代业务员",
            prop: "bussMan",
            display: true,
            span: 6,
            formslot: true,
          },
          {
            type: "input",
            label: "船代计划员",
            prop: "planMan",
            display: true,
            span: 6,
            formslot: true,
          },
        ],
      },
      optionShip: {
        emptyBtn: false,
        submitBtn: false,
        size: "mini",
        column: [
          {
            type: "input",
            label: "总吨",
            prop: "totalWgt",
            disabled: true,
            placeholder: "0",
            display: true,
            span: 6,
            readonly: true,
          },
          {
            type: "input",
            label: "净吨",
            prop: "netWgt",
            disabled: true,
            placeholder: "0",
            display: true,
            span: 6,
            readonly: true,
          },
          {
            type: "input",
            label: "载重吨",
            prop: "shipCap",
            disabled: true,
            placeholder: "0",
            display: true,
            span: 6,
            readonly: true,
          },
          {
            type: "input",
            label: "船长",
            prop: "shipLongNum",
            disabled: true,
            placeholder: "0",
            display: true,
            span: 6,
            readonly: true,
          },
          {
            type: "input",
            label: "来港",
            prop: "fromPortNam",
            disabled: true,
            placeholder: "0",
            display: true,
            span: 6,
            readonly: true,
          },
          {
            type: "input",
            label: "往港",
            prop: "toPortNam",
            disabled: true,
            placeholder: "0",
            display: true,
            span: 6,
            readonly: true,
          },
          {
            type: "input",
            label: "清洗仓公司",
            prop: "cleanprot",
            disabled: true,
            placeholder: "无",
            display: true,
            span: 6,
            readonly: true,
          },
          {
            type: "input",
            label: "进口货名",
            prop: "inCargoNam",
            disabled: true,
            placeholder: "无",
            display: true,
            span: 6,
            readonly: true,
          },
          {
            type: "input",
            label: "进口重量",
            prop: "inShipCap",
            disabled: true,
            placeholder: "0",
            display: true,
            span: 6,
            readonly: true,
          },
          {
            type: "input",
            label: "出口货名",
            prop: "outCargoNam",
            disabled: true,
            placeholder: "无",
            display: true,
            span: 6,
            readonly: true,
          },
          {
            type: "input",
            label: "出口重量",
            prop: "outShipCap",
            disabled: true,
            placeholder: "0",
            display: true,
            span: 6,
            readonly: true,
          },
        ],
      },
      option: {
        labelWidth: 70,
        emptyBtn: false,
        submitBtn: false,
        size: "mini",
        column: [
          //-----------------页面显示区域------------------------
          {
            type: "input",
            label: "单号",
            prop: "payNo",
            disabled: true,
            placeholder: "保存自动生成",
            display: true,
            span: 5,
            readonly: true,
          },
          {
            type: "input",
            label: "船名",
            prop: "shipNam",
            placeholder: " ",
            disabled: true,
            display: true,
            span: 4,
            readonly: true,
          },
          {
            type: "input",
            label: "船号",
            prop: "shipNoS",
            placeholder: " ",
            disabled: true,
            display: true,
            span: 4,
            readonly: true,
          },
          {
            type: "input",
            label: "客户",
            prop: "agentNam",
            placeholder: " ",
            disabled: true,
            display: true,
            span: 11,
            readonly: true,
          },
          {
            type: "input",
            label: "地址",
            prop: "agentAddr",
            placeholder: " ",
            disabled: true,
            display: true,
            span: 10,
            readonly: true,
          },
          {
            type: "input",
            label: "电话",
            prop: "agentPhoen",
            placeholder: " ",
            disabled: true,
            display: true,
            span: 4,
            readonly: true,
          },
          {
            type: "input",
            label: "邮编",
            prop: "agentPostcode",
            placeholder: " ",
            disabled: true,
            display: true,
            span: 4,
            readonly: true,
          },
          {
            type: "input",
            label: "开户行",
            prop: "agentBank",
            placeholder: " ",
            disabled: true,
            display: true,
            span: 5,
            readonly: true,
          },

          {
            type: "input",
            label: "账号",
            prop: "agnetAccount",
            placeholder: " ",
            disabled: true,
            display: true,
            span: 4,
            readonly: true,
          },
          {
            type: "input",
            label: "传真",
            prop: "agentFax",
            placeholder: " ",
            disabled: true,
            display: true,
            span: 4,
            readonly: true,
          },
          {
            type: "input",
            label: "报价人",
            prop: "payMan",
            placeholder: " ",
            display: true,
            disabled: true,
            span: 4,
            readonly: true,
          },
          {
            type: "number",
            label: "最终价",
            prop: "voyageMny",
            span: 4,
            rules: [
              {
                required: true,
                message: "请输入报价",
                trigger: "blur",
              },
            ],
          },
          {
            type: "select",
            label: "方式",
            prop: "payWay",
            placeholder: " ",
            display: true,
            span: 4,
            dicData: [
              {
                label: "电汇",
                value: "0",
              },
              {
                label: "支票",
                value: "1",
              },
              {
                label: "现金",
                value: "2",
              },
              {
                label: "月结",
                value: "3",
              },
              {
                label: "有款",
                value: "4",
              },
              {
                label: "刷卡",
                value: "5",
              },
              {
                label: "次结",
                value: "6",
              },
              {
                label: "平台结算",
                value: "7",
              },
            ],
            rules: [
              {
                required: true,
                message: "请选择方式",
                trigger: "blur",
              },
            ],
          },
          {
            type: "textarea",
            label: "明细",
            prop: "cdRemark",
            placeholder: " ",
            minRows: 2,
            display: true,
          },
          {
            type: "textarea",
            label: "备注",
            prop: "remark",
            placeholder: " ",
            minRows: 2,
            display: true,
          },
          //----------------------------------------------------
          {
            type: "input",
            label: "$column.comments",
            prop: "agentCod",
            display: false,
          },

          {
            type: "input",
            label: "$column.comments",
            prop: "agentKind",
            display: false,
          },

          {
            type: "input",
            label: "$column.comments",
            prop: "bussMan",
            display: false,
          },
          {
            type: "input",
            label: "$column.comments",
            prop: "cdPrepay",
            display: false,
          },

          {
            type: "input",
            label: "$column.comments",
            prop: "confirmId",
            display: false,
          },
          {
            type: "input",
            label: "$column.comments",
            prop: "confirmMan",
            display: false,
          },
          {
            type: "input",
            label: "$column.comments",
            prop: "jsId",
            display: false,
          },
          {
            type: "input",
            label: "$column.comments",
            prop: "jsMny",
            display: false,
          },
          {
            type: "input",
            label: "$column.comments",
            prop: "oldVoyageBal",
            display: false,
          },
          {
            type: "input",
            label: "$column.comments",
            prop: "operNam",
            display: false,
          },
          {
            type: "input",
            label: "$column.comments",
            prop: "payDte",
            display: false,
          },

          {
            type: "input",
            label: "$column.comments",
            prop: "planMan",
            display: false,
          },
          {
            type: "input",
            label: "$column.comments",
            prop: "recordTim",
            display: false,
          },

          {
            type: "input",
            label: "$column.comments",
            prop: "seqNo",
            display: false,
          },
          {
            type: "input",
            label: "$column.comments",
            prop: "shipBillNo",
            display: false,
          },

          {
            type: "input",
            label: "$column.comments",
            prop: "takeFax",
            display: false,
          },
          {
            type: "input",
            label: "$column.comments",
            prop: "takeMan",
            display: false,
          },
          {
            type: "input",
            label: "$column.comments",
            prop: "takePhone",
            display: false,
          },
          {
            type: "input",
            label: "$column.comments",
            prop: "unitCod",
            display: false,
          },
          {
            type: "input",
            label: "$column.comments",
            prop: "unitNam",
            display: false,
          },
          {
            type: "input",
            label: "$column.comments",
            prop: "updateNam",
            display: false,
          },
          {
            type: "input",
            label: "$column.comments",
            prop: "updateTim",
            display: false,
          },
          {
            type: "input",
            label: "$column.comments",
            prop: "verNo",
            display: false,
          },
        ],
      },
    };
  },
  computed: {
    ...mapState({
      userInfo: (state) => state.user.userInfo,
    }),
    ...mapGetters(["permissions"]),
  },
  created: function () {
    this.addBtn = this.permissions["SHIP_GRPT_YF_ADD"];
  },
  mounted: function () {
    this.obj = this.objrow;
    if (this.obj.payNo == null) {
      this.getAgnet();
      if (this.obj.bj == "补交") {
        this.obj.unitName = "营口港船货代理有限责任公司";
        this.obj.unitCod = "68";
        this.obj.takeMan = "高丽湘";
        this.obj.takePhone = "0417-6264918";
        this.obj.takeFax = "6269015";
        this.obj.payDate = new Date();
        this.obj.planMan = "";
        this.obj.bussMan = "";
      } else {
        if (this.obj.tradeId == 0) {
          this.getwFeeList();
        }
        this.obj.payMan = this.userInfo.manName;
      }
      this.obj.voyageMny = 0;
    } else {
      if (this.obj.agentAddr == null || this.obj.agentAddr == "") {
        this.getAgnet();
      }
      this.obj = Object.assign(this.obj,
      {totalWgt:this.objshipnew.totalWgt,
      netWgt:this.objshipnew.netWgt,
      shipCap:this.objshipnew.shipCap,
      shipLongNum:this.objshipnew.shipLongNum,
      fromPortNam:this.objshipnew.fromPortNam,
      toPortNam:this.objshipnew.toPortNam,
      cleanprot:this.objshipnew.cleanprot,
      inCargoNam:this.objshipnew.inCargoNam,
      inShipCap:this.objshipnew.inShipCap,
      outCargoNam:this.objshipnew.outCargoNam,
      outShipCap:this.objshipnew.outShipCap,});
      getfeeList(this.obj.payNo)
        .then((response) => {
          this.tableData = response.data.data;
        })
        .catch(() => {});
      if (this.obj.unitName == null || this.obj.unitName == "") {
        this.obj.unitName = "营口港船货代理有限责任公司";
        this.obj.unitCod = "68";
      }
    }
  },
  methods: {
    async getwFeeList() {
      getWFeeBillList(this.obj.shipNoS)
        .then((response) => {
          let data = response.data.data;
          let list = data.feeBody;
          for (var i in list) {
            this.$refs.xTable.insert(list[i]);
          }
          this.obj.voyageMny = data.amt;
        })
        .catch(() => {});
    },
    getAgnet() {
      getAgentObj(this.obj.agentCod_01)
        .then((response) => {
          let data = response.data.data;
          this.obj.agentAddr = data.agentAddr;
          this.obj.agentPhoen = data.agentPhoen;
          this.obj.agentPostcode = data.agentPostcode;
          this.obj.agentBank = data.agentBank;
          this.obj.agnetAccount = data.agnetAccount;
          this.obj.agentFax = data.agentFax;
        })
        .catch(() => {});
    },
    async handleSubmit() {
      this.buttonload = true;
      const errMap = await this.$refs.xTable
        .fullValidate()
        .catch((errMap) => errMap);
      if (errMap) {
        this.buttonload = false;
      } else {
        this.$refs["khForm"].validate((valid) => {
          if (valid) {
            let loadingInstance = Loading.service({ fullscreen: true });
            console.log(this.$refs.xTable.getUpdateRecords());
            commitFee(this.obj, this.$refs.xTable.getRecordset())
              .then((data) => {
                this.$message.success("编辑成功");
                loadingInstance.close();
                this.buttonload = false;
                //this.getList(this.page);
                this.$emit("getList");
                this.$emit("getListDate");
                this.$emit("dictItemVisible");
              })
              .catch(() => {
                // loading();
                loadingInstance.close();
                this.buttonload = false;
              });
          } else {
            this.buttonload = false;
          }
        });
      }
    },
    closeWindows: function () {
      this.$emit("dictItemVisible");
    },
    ///////////////////////////////表格操作区域方法////////////////////
    insertEvent() {
      this.$refs.xTable
        .insert()
        .then(({ row }) => this.$refs.xTable.setActiveCell(row, "feeNam"));
    },
  },
};
</script>
<style >
.feemain .el-collapse-item__content {
  padding-bottom: 0px;
}
.feemain .avue-form__row {
  height: 35px;
}
.feemain .el-tabs__item {
  height: 30px;
  line-height: 25px;
}
.feemain .vxe-toolbar {
  height: 25px;
}
</style>