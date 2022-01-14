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
  <div class="businessrenttankcontractView">
    <el-divider content-position="left">
      <strong>合同信息</strong>
    </el-divider>
    <vxe-form
      :data="dataForm"
      size="mini"
      ref="editHeadForm"
      :rules="formRules"
      title-width="120"
    >
      <vxe-form-item title="作业委托人" field="shiperNam" size="mini" span="6">
        <vxe-input
          v-model="dataForm.shiperNam"
          :disabled="disabledContract"
        ></vxe-input>
        <!-- <EditDown
          v-if="ajaxOver"
          :objs="dataForm"
          :property="downTableOption.shiperNam"
          :tableColumns="downTableOption.shiperColum"
          :urlapi="downTableOption.shiperurl"
          :returnApi="downTableOption.shiperreturnApi"
          @datereturn="shiperNamReturn"
          sizenow="mini"
        ></EditDown> -->
      </vxe-form-item>
      <vxe-form-item
        title="港口经营人"
        field="portUnitNam"
        size="mini"
        span="6"
      >
        <vxe-input
          v-model="dataForm.portUnitNam"
          :disabled="disabledContract"
        ></vxe-input>
        <!-- <EditDown
          v-if="ajaxOver"
          :objs="dataForm"
          :property="downTableOption.portUnitNam"
          :tableColumns="downTableOption.portUnitColum"
          :urlapi="downTableOption.portUniturl"
          :returnApi="downTableOption.portUnitreturnApi"
          sizenow="mini"
        ></EditDown> -->
      </vxe-form-item>
      <vxe-form-item title="合同号" field="contractNo" size="mini" span="6">
        <vxe-input v-model="dataForm.contractNo" :disabled="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="作业公司" field="unitNam" size="mini" span="6">
        <vxe-input
          v-model="dataForm.unitNam"
          :disabled="disabledContract"
        ></vxe-input>
        <!-- <EditDown
          v-if="ajaxOver"
          :objs="dataForm"
          :property="downTableOption.unitNam"
          :tableColumns="downTableOption.unitColum"
          :urlapi="downTableOption.uniturl"
          :returnApi="downTableOption.unitreturnApi"
          sizenow="mini"
        ></EditDown> -->
      </vxe-form-item>
    </vxe-form>
    <el-divider content-position="left">
      <strong>双方信息</strong>
    </el-divider>
    <vxe-form
      :data="dataForm"
      size="mini"
      ref="editHeadForm"
      :rules="formRules"
      title-width="120"
    >
      <vxe-form-item title="甲方经办人" field="jbrA" size="mini" span="8">
        <vxe-input
          v-model="dataForm.jbrA"
          :disabled="disabledContract"
        ></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="甲方联系电话" field="teleA" size="mini" span="8">
        <vxe-input
          v-model="dataForm.teleA"
          :disabled="disabledContract"
        ></vxe-input>
      </vxe-form-item>
      <vxe-form-item
        title="甲方发票名头"
        field="billTitleA"
        size="mini"
        span="8"
      >
        <vxe-input
          v-model="dataForm.billTitleA"
          :disabled="disabledContract"
        ></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="乙方经办人" field="jbrB" size="mini" span="8">
        <vxe-input
          v-model="dataForm.jbrB"
          :disabled="disabledContract"
        ></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="乙方联系电话" field="teleB" size="mini" span="8">
        <vxe-input
          v-model="dataForm.teleB"
          :disabled="disabledContract"
        ></vxe-input>
      </vxe-form-item>
      <!-- <vxe-form-item
        title="乙方发票名头"
        field="billTitleB"
        size="mini"
        span="8"
      >
        <vxe-input
          v-model="dataForm.billTitleB"
          :disabled="disabledContract"
        ></vxe-input> -->
      <!-- </vxe-form-item> -->
    </vxe-form>

    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="租罐信息" name="first">
        <el-row :gutter="24">
          <el-col :span="24">
            <!-- <vxe-toolbar perfect size="mini">
              <template v-slot:buttons>
                <vxe-button type="text" class="fa fa-plus" @click="saveCargo()">新增</vxe-button>
                <vxe-button
                  type="text"
                  icon="fa fa-trash"
                  @click="$refs.tableTank.removeCheckboxRow()"
                  >删除</vxe-button
                >
              </template>
            </vxe-toolbar> -->
            <vxe-table
              border
              show-overflow
              keep-source
              size="mini"
              :data="tableDataTank"
              :loading="tableloadTank"
              :round="true"
              align="center"
              height="400px"
              ref="tableTank"
              @cell-click="rowclick"
              :edit-config="{ trigger: 'click', mode: 'row' }"
              :mouse-config="{ selected: true }"
              :checkbox-config="{ range: true }"
            >
              <!-- <vxe-table-column type="checkbox" width="60"></vxe-table-column> -->
              <vxe-table-column
                type="seq"
                width="60"
                title="序号"
              ></vxe-table-column>
              <vxe-table-column
                field="shiperNam"
                title="港口货主"
                width="200"
              ></vxe-table-column>
              <vxe-table-column
                field="cargoNam"
                title="货物"
                width="150"
              ></vxe-table-column>
              <vxe-table-column
                field="pkgKindNam"
                title="包装"
                width="150"
              ></vxe-table-column>
              <vxe-table-column field="yardNam" title="地点" width="150">
              </vxe-table-column>
              <vxe-table-column field="tankNo" title="罐号" width="150">
              </vxe-table-column>
              <vxe-table-column field="tankCapacity" title="罐容" width="150">
              </vxe-table-column>
              <vxe-table-column field="feeUnit" title="计量单位" width="150">
              </vxe-table-column>
              <vxe-table-column field="feeRate" title="费率" width="150">
              </vxe-table-column>
              <vxe-table-column field="amt" title="金额" width="150">
              </vxe-table-column>
              <vxe-table-column field="beginDate" title="开始日期" width="150" >
                <!-- :edit-render="{
                  name: '$input',
                  props: { type: 'date' },
                  events: { input: dateChangeEvent },
                }" -->
              </vxe-table-column>
              <vxe-table-column field="endDate" title="结束日期" width="150">
                <!-- :edit-render="{
                  name: '$input',
                  props: { type: 'date' },
                  events: { input: dateChangeEvent },
                }" -->
              </vxe-table-column>
            </vxe-table>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="合同条款" name="second">
        <!-- <vxe-form :data="dataForm" size="mini">
          <vxe-form-item
            title="查询条件："
            field="selecttk"
            size="mini"
            span="4"
          >
            <vxe-input v-model="dataForm.selecttk" @input="getText"></vxe-input>
          </vxe-form-item>
        </vxe-form> -->
        <el-row :gutter="15">
          <!-- <el-col :span="13">
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
          </el-col> -->
          <el-col :span="24">
            <el-input
              v-model="dataForm.article"
              type="textarea"
              :autosize="{ minRows: 21, maxRows: 50 }"
              :disabled="disabledContract"
            ></el-input>
          </el-col>
        </el-row>
      </el-tab-pane>
    </el-tabs>
    <div align="center">
      <!-- <el-button
        type="primary"
        size="mini"
        icon="el-icon-check"
        @click="handleSubmit"
        >保存</el-button
      > -->
      <el-button
        type="primary"
        size="mini"
        icon="fa fa-print"
        @click="printContract"
        >线上合同打印</el-button
      >
      <el-button size="mini" icon="el-icon-close" plain @click="closeWindows"
        >取消</el-button
      >
    </div>
  </div>
</template>

<script>
import {
  saveAll,
  specTxtList,
  agreementList,
  getContractMx,
  getContUrl,
} from "@/api/business/businessrenttankcontract";
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
export default {
  props: ["datanow"],
  components: { EditDown },
  data() {
    return {
      activeName: "first",
      // ajaxOver: false,
      // ajaxOverFirstMan: false,
      tableDataTank: [],
      // tableDataAssign: [],
      // tableDataScep: [],
      tableDataText: [],
      tableloadTank: false,
      // tableloadAssign: false,
      tableloadText: false,
      ContUrl: null,
      // tableloadScep: false,
      // advanceNumReadonly: true,
      // validRules: {},
      formRules: {
        // csid: [{ required: true, message: "请选择水尺" }],
      },
      // amtAll: 0,
      // weightAll: 0,
      textnum: 1,
      dataForm: {
        shiperCod: "",
        shiperNam: "",
        unitCod: "",
        unitNam: "",
        portUnitCod: "",
        portUnitNam: "",
      },
      listifpay: [
        { label: "是", value: 1 },
        { label: "否", value: 0 },
      ],
      downTableOption: {
        //作业公司
        unitNam: "unitNam",
        unitColum: [
          { field: "UNIT_COD", title: "单位代码" },
          { field: "UNIT_NAM", title: "单位名称" },
        ],
        unitreturnApi: [
          { field: "UNIT_COD", returnName: "unitCod" },
          { field: "UNIT_NAM", returnName: "unitNam" },
          { field: "UNIT_COD", returnName: "unitcod" },
          { field: "UNIT_NAM", returnName: "unitNam" },
        ],
        uniturl: "/basecode/basecod/getUnitNam",
        //港口经营人
        portUnitNam: "portUnitNam",
        portUnitColum: [
          { field: "UNIT_COD", title: "单位代码" },
          { field: "UNIT_NAM", title: "单位名称" },
        ],
        portUnitreturnApi: [
          { field: "UNIT_COD", returnName: "portUnitCod" },
          { field: "UNIT_NAM", returnName: "portUnitNam" },
          { field: "UNIT_COD", returnName: "portUnitcod" },
          { field: "UNIT_NAM", returnName: "portUnitNam" },
        ],
        portUniturl: "/basecode/basecod/getUnitNam",
        //作业委托人
        shiperNam: "shiperNam",
        shiperColum: [
          { field: "SHIPPER_COD", title: "货主代码" },
          { field: "SHIPPER_DOC", title: "货主名称" },
        ],
        shiperreturnApi: [
          { field: "SHIPPER_COD", returnName: "shiperCod" },
          { field: "SHIPPER_DOC", returnName: "shiperNam" },
        ],
        shiperurl: "/basecode/basecod/getShipperDoc",
        //作业委托人（交）
        shiperPayNam: "shiperPayNam",
        shiperPayColum: [
          { field: "SHIPPER_COD", title: "货主代码" },
          { field: "SHIPPER_DOC", title: "货主名称" },
        ],
        shiperPayreturnApi: [
          { field: "SHIPPER_COD", returnName: "shiperPayCod" },
          { field: "SHIPPER_DOC", returnName: "shiperPayNam" },
        ],
        shiperPayurl: "/basecode/basecod/getShipperDoc",
      },
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      disabledContract: true,
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
    if (this.datanow.contractNo != null) {
      this.openContractMx(this.datanow.contractNo);
    }
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
    openContractMx(contractNo) {
      getContractMx(contractNo)
        .then((response) => {
          this.tableDataTank = response.data.data;
        })
        .catch(() => {});
    },
    // handleSubmit() {
    //   this.$refs.editHeadForm
    //     .validate((valid) => {})
    //     .then((valid) => {
    //       save(this.dataForm)
    //         .then((data) => {
    //           this.$message.success("保存成功");
    //           this.$emit("selectListplan");
    //           this.$emit("dictItemVisible");
    //         })
    //         .catch(() => {});
    //     });
    // },
    // handleSubmit() {
    //   var type = "add";
    //   if (this.dataForm.contractNo != null) {
    //     type = "edit";
    //   }
    //   this.$refs.editHeadForm
    //     .validate((valid) => {})
    //     .then((valid) => {
    //       for (let i in this.tableDataTank) {
    //         if (
    //           this.tableDataTank[i].endDate < this.tableDataTank[i].beginDate
    //         ) {
    //           this.$message.error("租罐信息中结束时间不能小于结束时间");
    //           return;
    //         }
    //       }
    //       let loadingInstance = Loading.service({
    //         fullscreen: true,
    //         text: "正在保存………",
    //       });
    //       this.tableDataTankSave=this.$refs.tableTank.getCheckboxRecords();
    //       console.log(tableDataTankSave);
    //       if(this.tableDataTankSave.length==0){
    //           this.$message.error("租罐信息不能无选中信息");
    //           return;
    //       }
    //       saveAll(this.dataForm, this.tableDataTankSave, type)
    //         .then((data) => {
    //           this.$message.success("保存成功");
    //           loadingInstance.close();
    //           this.$emit("selectListplan");
    //           this.$emit("dictItemVisible");
    //         })
    //         .catch(() => {});
    //     });
    // },
    rowclick({ row }) {},
    insertText(row) {
      if (this.dataForm.article == null) {
        this.dataForm.article = this.textnum + "." + row.MARK_TXT + "\n";
      } else {
        this.dataForm.article =
          this.dataForm.article + this.textnum + "." + row.MARK_TXT + "\n";
      }
      this.textnum = this.textnum + 1;
      this.dataForm.txtNum = this.textnum;
    },
    printContract() {
      if(this.datanow.status != "09"){
        this.$message.error("当前合同未封存，不能打印");
        retur;
      }
      if (this.datanow.contractNo != null) {
        var url = "http://10.168.111.173:9081/Container/reportJsp/parameterReport.jsp?report=ykport0/ykport4_renttank.raq&arg1=";
        window.open(url + this.datanow.contractNo);
      } else {
        this.$message.error("合同号有误，不能打印");
      }
    },
    // dateChangeEvent({ row, column }) {
    //   if (row.endDate < row.beginDate) {
    //     this.$message.error("结束时间不能小于结束时间");
    //     row.amt = 0;
    //     return;
    //   }
    //   var beginDate = Date.parse(row.beginDate);
    //   var endDate = Date.parse(row.endDate);
    //   var days1 = (endDate - beginDate) / (1 * 24 * 60 * 60 * 1000);
    //   var days = parseInt(Math.abs(endDate - beginDate) / 1000 / 60 / 60 / 24);
    //   var amt = (row.feeRate * 10000 * days) / 10000;
    //   row.amt = amt;
    // },
    handleClick() {
      this.$refs.tableText.reloadData(this.tableDataText);
      this.$refs.tableTank.reloadData(
        this.$refs.tableTank.getTableData().fullData
      );
      // this.$refs.tableAssign.reloadData(this.$refs.tableAssign.getTableData().fullData);
      this.$refs.tableScep.reloadData(
        this.$refs.tableScep.getTableData().fullData
      );
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    shiperNamReturn() {
      this.tableloadTank = true;
      agreementList(this.dataForm.shiperCod)
        .then((response) => {
          this.tableDataTank = response.data.data;
          this.tableloadTank = false;
          console.log(response);
        })
        .catch(() => {
          this.tableloadTank = false;
        });
    },
    closeWindows() {
      this.$emit("selectListplan");
      this.$emit("dictItemVisible");
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