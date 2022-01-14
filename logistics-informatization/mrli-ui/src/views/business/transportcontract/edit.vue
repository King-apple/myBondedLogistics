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
  <div class="business-transportcontract-edit">
    <basic-container>
      <el-row :gutter="15">
        <el-col :span="15">
          <el-divider content-position="center">
            <strong>基础信息</strong>
          </el-divider>
          <vxe-form
            :data="dataForm"
            size="mini"
            ref="editHeadForm"
            :rules="formRules"
            title-width="100"
          >
            <vxe-form-item title="申请号" field="requNo" size="mini" span="6">
              <EditDown
                :objs="dataForm"
                property="requNo"
                :tableColumns="downTableOption.requNoColum"
                :urlapi="downTableOption.requNourl"
                :returnApi="downTableOption.requNoreturnApi"
                sizenow="mini"
                @datereturn="firstNamReturn"
                :disabled="ifEdit"
              ></EditDown>
            </vxe-form-item>
            <vxe-form-item
              title="提货截止日期"
              field="contEndDate"
              size="mini"
              span="6"
            >
              <vxe-input v-model="dataForm.contEndDate" type="date" :disabled="viewIf"></vxe-input>
            </vxe-form-item>
            <vxe-form-item
              title="货物种类"
              field="cargoKindCodName"
              size="mini"
              span="6"
            >
              <EditDown
                :objs="dataForm"
                property="cargoKindCodName"
                :tableColumns="downTableOption.cargoKindColum"
                :urlapi="downTableOption.cargoKindurl"
                :returnApi="downTableOption.cargoKindreturnApi"
                sizenow="mini"
                :disabled="viewIf"
              ></EditDown>
            </vxe-form-item>
            <vxe-form-item
              title="作业公司"
              field="unitNam"
              size="mini"
              span="6"
            >
              <vxe-input v-model="dataForm.unitNam" :disabled="viewIf"></vxe-input>
            </vxe-form-item>
            <vxe-form-item title="直取标记" field="zqId" size="mini" span="6">
              <vxe-select
                v-model="dataForm.zqId"
                :options="yesOrNoList"
                :disabled="viewIf"
              ></vxe-select>
            </vxe-form-item>
            <vxe-form-item
              title="货物免收"
              field="storeDay"
              size="mini"
              span="6"
            >
              <vxe-input
                v-model="dataForm.storeDay"
                placeholder="免收多少天堆存费"
                :disabled="viewIf"
              ></vxe-input>
            </vxe-form-item>
            <vxe-form-item
              title="超期堆存费吨"
              field="exceedTimeNum"
              size="mini"
              span="6"
            >
              <vxe-input
                v-model="dataForm.exceedTimeNum"
                placeholder="超出免堆期，按照多少天/吨计算"
                :disabled="viewIf"
              ></vxe-input>
            </vxe-form-item>
            <vxe-form-item
              title="合同预付款"
              field="advanceNum"
              size="mini"
              span="6"
            >
              <vxe-input v-model="dataForm.advanceNum" :disabled="viewIf"></vxe-input>
            </vxe-form-item>
            <vxe-form-item
              title="合同条款"
              field="contTxt"
              size="mini"
              span="24"
            >
              <vxe-textarea
                v-model="dataForm.contTxt"
                placeholder="默认尺寸"
                :autosize="{ minRows: 4, maxRows: 4 }"
                :disabled="viewIf"
              ></vxe-textarea>
            </vxe-form-item>
            <vxe-form-item
              title="特殊条款"
              field="remarkTxt"
              size="mini"
              span="24"
            >
              <vxe-textarea v-model="dataForm.remarkTxt" :disabled="viewIf"></vxe-textarea>
            </vxe-form-item>
            <vxe-form-item title="出发地" field="startNam" size="mini" span="8">
              <vxe-input v-model="dataForm.startNam" :disabled="viewIf"></vxe-input>
            </vxe-form-item>
            <vxe-form-item title="目的地" field="endNam" size="mini" span="8">
              <vxe-input v-model="dataForm.endNam" :disabled="viewIf"></vxe-input>
            </vxe-form-item>
            <vxe-form-item
              title="承运车队"
              field="carPortNam"
              size="mini"
              span="8"
            >
              <vxe-input v-model="dataForm.carPortNam" :disabled="viewIf"></vxe-input>
            </vxe-form-item>
            <vxe-form-item
              title="作业委托"
              field="consignNam"
              size="mini"
              span="12"
            >
              <EditDown
                :objs="dataForm"
                property="consignNam"
                :tableColumns="downTableOption.consignNamColum"
                :urlapi="downTableOption.consignNamurl"
                :returnApi="downTableOption.consignNamreturnApi"
                sizenow="mini"
                @datereturn="businessCodReturn"
                :disabled="viewIf"
              ></EditDown>
            </vxe-form-item>
            <vxe-form-item
              title="港口经营方"
              field="officeNam"
              size="mini"
              span="12"
            >
              <vxe-input v-model="dataForm.officeNam" :disabled="viewIf"></vxe-input>
            </vxe-form-item>
            <vxe-form-item
              title="经办方"
              field="consignMan"
              size="mini"
              span="12"
            >
              <EditDown
                :objs="dataForm"
                property="consignMan"
                :tableColumns="downTableOption.consignManColum"
                :urlapi="downTableOption.consignManurl"
                :returnApi="downTableOption.consignManreturnApi"
                sizenow="mini"
                :disabled="viewIf"
              ></EditDown>
            </vxe-form-item>
            <vxe-form-item
              title="经办方"
              field="officeMan"
              size="mini"
              span="12"
            >
              <EditDown
                :objs="dataForm"
                property="officeMan"
                :tableColumns="downTableOption.officeManColum"
                :urlapi="downTableOption.officeManurl"
                :returnApi="downTableOption.officeManreturnApi"
                sizenow="mini"
                :disabled="viewIf"
              ></EditDown>
            </vxe-form-item>
            <vxe-form-item
              title="联系电话"
              field="consignPhone"
              size="mini"
              span="12"
            >
              <vxe-input v-model="dataForm.consignPhone" :disabled="viewIf"></vxe-input>
            </vxe-form-item>
            <vxe-form-item
              title="联系电话"
              field="officePhone"
              size="mini"
              span="12"
            >
              <vxe-input v-model="dataForm.officePhone" :disabled="viewIf"></vxe-input>
            </vxe-form-item>
            <vxe-form-item
              title="结算单位"
              field="settlementUnitNam"
              size="mini"
              span="12"
            >
              <EditDown
                :objs="dataForm"
                property="settlementUnitNam"
                :tableColumns="downTableOption.shipperColum"
                :urlapi="downTableOption.shipperurl"
                :returnApi="downTableOption.shipperreturnApi"
                sizenow="mini"
                :disabled="viewIf"
              ></EditDown>
            </vxe-form-item>
            <vxe-form-item
              title="港口费发票名头"
              field="invoiceOwnerNam"
              size="mini"
              span="12"
            >
              <EditDown
                :objs="dataForm"
                property="invoiceOwnerNam"
                :tableColumns="downTableOption.invoiceOwnerColum"
                :urlapi="downTableOption.invoiceOwnerurl"
                :returnApi="downTableOption.invoiceOwnerreturnApi"
                sizenow="mini"
                :disabled="viewIf"
              ></EditDown>
            </vxe-form-item>
            <vxe-form-item
              title="签订日期"
              field="contDte"
              size="mini"
              span="12"
            >
              <vxe-input v-model="dataForm.contDte" type="date" :disabled="viewIf"></vxe-input>
            </vxe-form-item>
          </vxe-form>
        </el-col>
        <el-col :span="9">
          <el-divider content-position="center">
            <strong>结算信息</strong>
          </el-divider>
          <vxe-toolbar perfect size="mini">
            <template v-slot:buttons>
              <vxe-button type="text" class="fa fa-plus" @click="addFee()"
               v-if="!viewIf" >新增</vxe-button
              >
              <vxe-button
                type="text"
                icon="fa fa-trash"
                @click="$refs.transportFee.removeCheckboxRow()"
               v-if="!viewIf" >删除</vxe-button
              >
            </template>
          </vxe-toolbar>
          <vxe-table
            border
            show-overflow
            keep-source
            stripe
            highlight-current-row
            highlight-hover-row
            size="mini"
            :data="tableDataFee"
            :loading="tableloadFee"
            :round="true"
            align="center"
            height="400px"
            ref="transportFee"
            :edit-config="{ trigger: 'click', mode: 'row' }"
          >
            <vxe-table-column type="checkbox" width="60" />
            <vxe-table-column
              field="cfeeNam"
              title="费用名称"
              :edit-render="{
                name: 'EditDownTable',
                props: {
                  checkField: 'cfeeNam',
                  tableColumn: this.downTableOption.specNoColum,
                  urlapi: this.downTableOption.specNourl,
                  returnApi: this.downTableOption.specNoreturnApi,
                  sizenow: 'mini'
                }
              }"
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
              field="balaUnitNam"
              title="结算单位"
              :edit-render="{
                name: 'EditDownTable',
                props: {
                  checkField: 'balaUnitNam',
                  tableColumn: this.downTableOption.balaUnitNamColum,
                  urlapi: this.downTableOption.balaUnitNamurl,
                  returnApi: this.downTableOption.balaUnitNamreturnApi,
                  sizenow: 'mini'
                }
              }"
            />
          </vxe-table>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-divider content-position="center">
            <strong>货物信息</strong>
          </el-divider>
          <vxe-toolbar perfect size="mini">
            <template v-slot:buttons> </template>
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
            height="250px"
            ref="transportCargo"
            :edit-rules="validRules"
            :edit-config="{ trigger: 'click', mode: 'row' }"
            :mouse-config="{ selected: true }"
          >
            <vxe-table-column field="shipperDoc" title="港口货主" />
            <vxe-table-column field="billNo" title="提单号" />
            <vxe-table-column field="cargoNam" title="货名" />
            <vxe-table-column field="cPkgKindNam" title="包装" />
            <vxe-table-column
              field="feeRat"
              title="费率"
              :edit-render="{ autofocus: '.vxe-input--inner' }"
              ><template v-slot:edit="{ row }">
                <vxe-input
                  type="number"
                  v-model="row.feeRat"
                  @input="inputFee"
                ></vxe-input> </template
            ></vxe-table-column>
            <vxe-table-column field="inPortWayName" title="集港方式" />
            <vxe-table-column field="inYardWayName" title="入库方式" />
            <vxe-table-column field="outPortWayName" title="疏港方式" />
            <vxe-table-column field="outYardWayName" title="出库方式" />
            <vxe-table-column field="piecesNum" title="件数" />
            <vxe-table-column field="weightWgt" title="吨数" />
            <vxe-table-column field="cargoVol" title="体积" />
            <vxe-table-column field="format" title="规格" />
            <vxe-table-column field="limitWgt" title="上下限" />
            <vxe-table-column field="shipNam" title="船名" />
            <vxe-table-column field="addrNam" title="地点" />
            <vxe-table-column field="inPortDte" title="进港时间" />
            <vxe-table-column field="safetyKndCod" title="险种" />
            <vxe-table-column field="cfeeUnit" title="计量单位" />
            <vxe-table-column field="shipperDocPrice" title="价格货主" />
            <vxe-table-column field="cargoKindNamPrice" title="价格货种" />
          </vxe-table>
        </el-col>
      </el-row>
      <div align="center">
        <el-button
          type="primary"
          size="mini"
          icon="el-icon-check"
          @click="handleSubmit"
          v-if="!viewIf"
          >保存</el-button
        >
        <el-button size="mini" icon="el-icon-close" plain @click="closeWindows"
        
          >取消</el-button
        >
      </div>
    </basic-container>
  </div>
</template>

<script>
import {
  applyCargoList,
  getContTxt,
  addObj,
  fetchCargoList,
  fetchFeeList
} from "@/api/business/transportcontract";
import { mapGetters } from "vuex";
import EditDown from "@/views/baseUtile/EditDown";
import { xj, jf } from "@/util/util";
import moment from "moment";
import { Loading, Message } from "element-ui";
export default {
  props: ["datanow","viewIf"],
  components: { EditDown },
  data() {
    return {
      dataForm: {
        requNo: null,
        unitCod: null,
        unitNam: null,
        consignCod: null,
        consignNam: null,
        cargoKindId: null,
        cargoKindCodName: null,
        settlementUnitNam: null,
        settlementUnitCod: null,
        invoiceOwnerNam: null,
        invoiceOwnerCod: null,
        officeNam: null,
        officeCod: null,
        consignMan: null,
        officeMan: null,
        consignPhone: null,
        advanceNum: 0
      },
      formRules: {
        requNo: [{ required: true, message: "请选择申请单" }],
        cargoKindId: [{ required: true, message: "请选择货物种类" }],
        unitNam: [{ required: true, message: "请选择作业公司" }],
        contTxt: [{ required: true, message: "请录入合同条款" }],
        settlementUnitNam: [{ required: true, message: "请选择结算单位" }],
        invoiceOwnerNam: [{ required: true, message: "请选择发票名头" }]
      },
      validRules: {
        feeRat: [{ required: true, message: "请輸入费率" }]
      },
      tableDataCargo: [],
      tableloadCargo: false,
      ifEdit: false,
      tableDataFee: [],
      tableloadFee: false,
      yesOrNoList: [
        { label: "是", value: 1 },
        { label: "否", value: 0 }
      ],
      downTableOption: {
        //操作附加费
        specNoColum: [
          { field: "FEE_NAME_COD", title: "费目代码" },
          { field: "C_FEE_NAM", title: "费目名称" },
          { field: "EX_FEE_COD", title: "公开代码" },
          { field: "EX_FEE_NAM", title: "公开费目" }
        ],
        specNoreturnApi: [
          { field: "FEE_NAME_COD", returnName: "feeNamCod" },
          { field: "C_FEE_NAM", returnName: "cfeeNam" },
          { field: "EX_FEE_COD", returnName: "exFeeCod" },
          { field: "EX_FEE_NAM", returnName: "exFeeNam" }
        ],
        specNourl: "/business/contract/cfeeNamPage",
        //申请号
        requNoColum: [
          { field: "REQU_NO", title: "申请单号" },
          { field: "MEANING", title: "货物种类" },
          { field: "UNIT_NAM", title: "作业公司" },
          { field: "CONSIGN_NAM", title: "作业委托人" }
        ],
        requNoreturnApi: [
          { field: "REQU_NO", returnName: "requNo" },
          { field: "CARGO_KIND_ID", returnName: "cargoKindId" },
          { field: "ZQ_ID", returnName: "zqId" },
          { field: "MEANING", returnName: "cargoKindCodName" },
          { field: "UNIT_NAM", returnName: "unitNam" },
          { field: "UNIT_COD", returnName: "unitCod" },
          { field: "UNIT_NAM", returnName: "officeNam" },
          { field: "UNIT_COD", returnName: "officeCod" },
          { field: "CONSIGN_NAM", returnName: "consignNam" },
          { field: "CONSIGN_COD", returnName: "consignCod" },
          { field: "CONSIGN_NAM", returnName: "settlementUnitNam" },
          { field: "CONSIGN_COD", returnName: "settlementUnitCod" },
          { field: "CONSIGN_NAM", returnName: "invoiceOwnerNam" },
          { field: "CONSIGN_COD", returnName: "invoiceOwnerCod" }
        ],
        requNourl: "/business/transportcontract/getSq",
        //货类
        cargoKindColum: [
          { field: "CODE", title: "代码" },
          { field: "MEANING", title: "货物种类" }
        ],
        cargoKindreturnApi: [
          { field: "MEANING", returnName: "cargoKindCodName" },
          { field: "CODE", returnName: "cargoKindId" }
        ],
        cargoKindurl: "/basecode/basecod/getDataCodes?baseType=CARGO_KIND_ID",
        //作业委托
        consignNamColum: [
          { field: "SHIPPER_COD", title: "货主代码" },
          { field: "SHIPPER_DOC", title: "货主名称" }
        ],
        consignNamreturnApi: [
          { field: "SHIPPER_COD", returnName: "consignCod" },
          { field: "SHIPPER_DOC", returnName: "consignNam" },
          { field: "SHIPPER_COD", returnName: "settlementUnitCod" },
          { field: "SHIPPER_DOC", returnName: "settlementUnitNam" },
          { field: "SHIPPER_COD", returnName: "invoiceOwnerCod" },
          { field: "SHIPPER_DOC", returnName: "invoiceOwnerNam" }
        ],
        consignNamurl: "/basecode/basecod/getShipperDoc",
        //结算单位
        shipperColum: [
          { field: "SHIPPER_COD", title: "货主代码" },
          { field: "SHIPPER_DOC", title: "货主名称" }
        ],
        shipperreturnApi: [
          { field: "SHIPPER_COD", returnName: "settlementUnitCod" },
          { field: "SHIPPER_DOC", returnName: "settlementUnitNam" },
          { field: "SHIPPER_COD", returnName: "invoiceOwnerCod" },
          { field: "SHIPPER_DOC", returnName: "invoiceOwnerNam" }
        ],
        shipperurl: "/basecode/basecod/getShipperDoc",
        //发票单位
        invoiceOwnerColum: [
          { field: "SHIPPER_COD", title: "货主代码" },
          { field: "SHIPPER_DOC", title: "货主名称" }
        ],
        invoiceOwnerreturnApi: [
          { field: "SHIPPER_COD", returnName: "invoiceOwnerCod" },
          { field: "SHIPPER_DOC", returnName: "invoiceOwnerNam" }
        ],
        invoiceOwnerurl: "/basecode/basecod/getShipperDoc",
        //甲方经办人
        consignManColum: [
          { field: "RELA_MAN", title: "姓名" },
          { field: "HANDSET", title: "手机号" }
        ],
        consignManreturnApi: [
          { field: "RELA_MAN", returnName: "consignMan" },
          { field: "HANDSET", returnName: "consignPhone" }
        ],
        consignManurl: "/basecode/basecod/getjbrLov",
        //乙方经办人
        officeManColum: [{ field: "OPER_NAM", title: "姓名" }],
        officeManreturnApi: [{ field: "OPER_NAM", returnName: "officeMan" }],
        officeManurl: "/business/basecode/getYfjbrLov",
        //费用结算结算单位
        balaUnitNamColum: [
          { field: "UNIT_COD", title: "单位代码" },
          { field: "UNIT_NAM", title: "单位名称" }
        ],
        balaUnitNamreturnApi: [
          { field: "UNIT_COD", returnName: "balaUnitCod" },
          { field: "UNIT_NAM", returnName: "balaUnitNam" }
        ],
        balaUnitNamurl: "/basecode/basecod/getUnitNam"
      }
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() {
    if (this.datanow != null) {
      this.dataForm = this.datanow;
      this.ifEdit = true;
      this.getBody();
      this.getFee();
    } else {
      this.dataForm.contDte = moment().format("YYYY/MM/DD");
    }
  },
  methods: {
    handleSubmit() {
      this.$refs.editHeadForm
        .validate(valid => {})
        .then(valid => {
          let loadingInstance = Loading.service({
            fullscreen: true,
            text: "正在保存………"
          });
          addObj(
            this.dataForm,
            this.$refs.transportCargo.getTableData().fullData,
            this.$refs.transportFee.getRecordset()
          )
            .then(data => {
              this.$message.success("保存成功");
              loadingInstance.close();
              this.$emit("selectList");
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
    getBody() {
      this.tableloadCargo = true;
      fetchCargoList(this.dataForm.pkid, this.dataForm.unitCod)
        .then(response => {
          this.tableDataCargo = response.data.data;
          this.tableloadCargo = false;
        })
        .catch(() => {
          this.tableloadCargo = false;
        });
    },
    getFee() {
      this.tableloadFee = true;
      fetchFeeList(this.dataForm.pkid, this.dataForm.unitCod)
        .then(response => {
          this.tableDataFee = response.data.data;
          this.tableloadFee = false;
        })
        .catch(() => {
          this.tableloadFee = false;
        });
    },
    addFee() {
      let record = {
        cFeeNam: null,
        balaUnitNam: this.dataForm.unitNam,
        balaUnitCod: this.dataForm.unitCod
      };
      this.$refs.transportFee
        .insert(record)
        .then(({ row }) =>
          this.$refs.transportFee.setActiveCell(row, "cFeeNam")
        );
    },
    firstNamReturn() {
      this.downTableOption.consignManurl =
        this.downTableOption.consignManurl +
        "?shipperCod=" +
        this.dataForm.consignCod;
      this.contTxtReturn();
      if (this.dataForm.requNo != null) {
        this.tableloadCargo = true;
        applyCargoList(this.dataForm.requNo)
          .then(response => {
            this.tableDataCargo = response.data.data;
            this.tableloadCargo = false;
          })
          .catch(() => {
            this.tableloadCargo = false;
          });
      }
    },
    businessCodReturn() {
      this.downTableOption.consignManurl =
        this.downTableOption.consignManurl +
        "?shipperCod=" +
        this.dataForm.consignCod;
    },
    contTxtReturn() {
      if (this.dataForm.unitCod != null && this.dataForm.cargoKindId != null) {
        getContTxt(this.dataForm.unitCod, this.dataForm.cargoKindId)
          .then(response => {
            this.dataForm.contTxt = response.data.data;
          })
          .catch(() => {});
      }
    },
    inputFee() {
      this.jsAdvanceNum();
    },
    jsAdvanceNum() {
      var cargoData = this.$refs.transportCargo.getTableData().fullData;
      this.dataForm.advanceNum = 0;
      for (var i in cargoData) {
        if (cargoData[i].feeRat == null || cargoData[i].feeRat == "") {
        } else {
          this.dataForm.advanceNum = xj(
            this.dataForm.advanceNum,
            cargoData[i].feeRat * cargoData[i].weightWgt * 1.1
          );
        }
      }
    }
  }
};
</script>
<style lang="scss" scoped>
.business-transportcontract-index {
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