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
  <div class="businessrenttankcontractAdd">
    <el-divider content-position="left">
      <strong>结算单信息</strong>
    </el-divider>
    <vxe-form
      :data="dataForm"
      size="mini"
      ref="editHeadForm"
      :rules="formRules"
      title-width="120"
      
    >
      <vxe-form-item title="合同号" field="contNo" size="mini" span="6">
        <vxe-input v-model="dataForm.contNo" :disabled="true"></vxe-input>
        <!-- <EditDown
          v-if="ajaxOver"
          :objs="dataForm"
          :property="downTableOption.contNo"
          :tableColumns="downTableOption.contNoColum"
          :urlapi="downTableOption.contNourl"
          :returnApi="downTableOption.contNoreturnApi"
          @datereturn="contNoReturn"
          sizenow="mini"
        ></EditDown> -->
      </vxe-form-item>
      <!-- <vxe-form-item
        title="港口经营人"
        field="portUnitNam"
        size="mini"
        span="6"
      >
        <EditDown
          v-if="ajaxOver"
          :objs="dataForm"
          :property="downTableOption.portUnitNam"
          :tableColumns="downTableOption.portUnitColum"
          :urlapi="downTableOption.portUniturl"
          :returnApi="downTableOption.portUnitreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item> -->
      <vxe-form-item title="结算单号" field="feeBillNo" size="mini" span="6">
        <vxe-input v-model="dataForm.feeBillNo" :disabled="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="作业委托人" field="consignNam" size="mini" span="6">
        <vxe-input v-model="dataForm.consignNam" :disabled="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="收入单位" field="unitNam" size="mini" span="6">
        <vxe-input v-model="dataForm.unitNam" :disabled="true"></vxe-input>
      </vxe-form-item>

      <vxe-form-item title="发票类型" field="kpKind" size="mini" span="6">
        <vxe-input v-model="dataForm.kpKind" :disabled="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="银行账号" field="shipperAcn" size="mini" span="6">
        <vxe-input v-model="dataForm.shipperAcn" :disabled="true"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="电话地址" field="shipperAdd" size="mini" span="6">
        <vxe-input v-model="dataForm.shipperAdd" :disabled="true"></vxe-input>
      </vxe-form-item>
    </vxe-form>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="结算单明细信息" name="first">
        <el-row :gutter="24">
          <el-col :span="24">
            <vxe-table
              border
              show-overflow
              keep-source
              size="mini"
              :data="tableData"
              :loading="tableload"
              :round="true"
              align="center"
              height="600px"
              ref="tableContract"
              show-footer
              :footer-method="footerMethod"
              @cell-click="rowclick"
              :edit-config="{ trigger: 'click', mode: 'row' }"
              :mouse-config="{ selected: true }"
              :checkbox-config="{ range: true }"
            >
              <vxe-table-column
                type="seq"
                width="60"
                title="序号"
              ></vxe-table-column>
              <vxe-table-column
                field="shipperDoc"
                title="付款单位"
                width="200"
              ></vxe-table-column>
              <vxe-table-column
                field="cfeeNam"
                title="费目"
                width="150"
              ></vxe-table-column>
              <vxe-table-column
                field="cargoNam"
                title="货名"
                width="150"
              ></vxe-table-column>
              <vxe-table-column field="cfeeUnit" title="单位" width="150">
              </vxe-table-column>
              <vxe-table-column
                field="weightWgt"
                title="计费吨/次/时间"
                width="150"
              >
              </vxe-table-column>
              <vxe-table-column field="feeRat" title="费率" width="150">
              </vxe-table-column>
              <vxe-table-column field="gsTaxRat" title="税率" width="150">
              </vxe-table-column>
              <vxe-table-column field="gsSeFee" title="收入" width="150">
              </vxe-table-column>
              <vxe-table-column field="gsTaxFee" title="税金" width="150">
              </vxe-table-column>
              <vxe-table-column field="feeMny" title="金额" width="150">
              </vxe-table-column>
              <vxe-table-column field="markNam" title="摘要" width="150">
              </vxe-table-column>
              <!-- <vxe-table-column
                field="beginDate"
                title="开始日期"
                width="150"
                :edit-render="{
                  name: '$input',
                  props: { type: 'date' },
                  events: { input: dateChangeEvent },
                }"
              >
              </vxe-table-column>
              <vxe-table-column
                field="endDate"
                title="结束日期"
                width="150"
                :edit-render="{
                  name: '$input',
                  props: { type: 'date' },
                  events: { input: dateChangeEvent },
                }"
              >
              </vxe-table-column> -->
            </vxe-table>
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
      <el-button size="mini" icon="el-icon-close" plain @click="closeWindows"
        >取消</el-button
      >
    </div>
  </div>
</template>

<script>
import { getFeeBodyList } from "@/api/account/cargoshipfeeyphead";
import request from "@/router/axios";
import "@/styles/common/edit.scss";
import { mapGetters } from "vuex";
import moment from "moment";
import { mapState } from "vuex";
import Vue from "vue";
import VXETable from "vxe-table";
import XEUtils from "xe-utils";
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
      tableData: [],
      tableDataText: [],
      tableload: false,
      tableloadText: false,
      formRules: {
        // csid: [{ required: true, message: "请选择水尺" }],
      },
      textnum: 1,
      dataForm: {
        contNo: "",
        consignCod: "",
        consignNam: "",
        kpKind: "",
        shipperAcn: "",
        shipperAdd: "",
        unitCod: "",
        unitNam: "",
      },
      downTableOption: {
        //作业公司
        contNo: "contNo",
        contNoColum: [
          { field: "CONTRACT_NO", title: "合同号" },
          { field: "CREATE_TIME", title: "日期" },
          { field: "SHIPER_COD", title: "委托人代码" },
          { field: "SHIPER_NAM", title: "委托人" },
        ],
        contNoreturnApi: [
          { field: "CONTRACT_NO", returnName: "contNo" },
          { field: "SHIPER_COD", returnName: "consignCod" },
          { field: "SHIPER_NAM", returnName: "consignNam" },
          { field: "UNIT_COD", returnName: "unitCod" },
          { field: "UNIT_NAM", returnName: "unitNam" },
          { field: "KP_KIND", returnName: "kpKind" },
          { field: "SHIPPER_ACN", returnName: "shipperAcn" },
          { field: "SHIPPER_ADD", returnName: "shipperAdd" },
        ],
        contNourl: "/basecode/basecod/getAccountContNo",
      },
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
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
    if (this.datanow.feeBillNo != null) {
      this.openFeeBody(this.datanow.feeBillNo);
    }
  },
  mounted: function () {},
  methods: {
    // getText() {
    //   this.tableloadText = true;
    //   specTxtList(
    //     Object.assign({
    //       current: this.tablePage.currentPage,
    //       size: this.tablePage.pageSize,
    //       key: this.dataForm.selecttk,
    //     })
    //   )
    //     .then((response) => {
    //       this.tableDataText = response.data.data.records;
    //       this.tablePage.total = response.data.data.total;
    //       this.tableloadText = false;
    //     })
    //     .catch(() => {
    //       this.tableloadText = false;
    //     });
    // },
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
    //   if (this.dataForm.feeBillNo != null) {
    //     type = "edit";
    //   }
    //   this.$refs.editHeadForm
    //     .validate((valid) => {})
    //     .then((valid) => {
    //       let loadingInstance = Loading.service({
    //         fullscreen: true,
    //         text: "正在保存………",
    //       });
    //       saveAll(this.dataForm, this.tableData, type)
    //         .then((data) => {
    //           this.$message.success("保存成功");
    //           loadingInstance.close();
    //           this.$emit("selectListplan");
    //           this.$emit("dictItemVisible");
    //         })
    //         .catch(() => {
    //           loadingInstance.close();
    //         });
    //     });
    // },
    rowclick({ row }) {},
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
    openFeeBody(feeBillNo) {
      getFeeBodyList(feeBillNo)
        .then((response) => {
          this.tableData = response.data.data;
        })
        .catch(() => {});
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
        }),
      ];
    },
    // contNoReturn() {
    //   this.tableload = true;
    //   var contNo=this.dataForm.contNo
    //   // getCont(contNo)
    //   //   .then((response) => {
    //   //     this.dataForm = response.data.data;
    //       contMxlist(contNo)
    //         .then((response) => {
    //           this.tableData = response.data.data;
    //           console.log(this.tableData);
    //         })
    //         .catch(() => {
    //           this.tableload = false;
    //         });
    //       this.tableload = false;
    //     // })
    //     // .catch(() => {
    //     //   this.tableload = false;
    //     // });
    // },
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