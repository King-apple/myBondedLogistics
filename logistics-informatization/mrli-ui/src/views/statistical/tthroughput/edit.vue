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
  <div class="tthroughputEdit">
    <vxe-form
      :data="dataForm"
      size="mini"
      ref="editHeadForm"
      :rules="formRules"
      title-width="120"
    >
      <vxe-form-item title="总量" field="totalWgt" size="mini" span="12">
        <vxe-input v-model="dataForm.totalWgt"></vxe-input>
      </vxe-form-item>
    <vxe-form-item title="去年同期" field="totalWgtLastyear" size="mini" span="12">
        <vxe-input v-model="dataForm.totalWgtLastyear"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="同比" field="totalWgtOveryear" size="mini" span="12">
        <vxe-input v-model="dataForm.totalWgtOveryear"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="增幅" field="totalWgtGrowthrate" size="mini" span="12">
        <vxe-input v-model="dataForm.totalWgtGrowthrate"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="外贸小计" field="outTotWgt" size="mini" span="12">
        <vxe-input v-model="dataForm.outTotWgt"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="外贸进口" field="outInWgt" size="mini" span="12">
        <vxe-input v-model="dataForm.outInWgt"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="外贸出口" field="outForWgt" size="mini" span="12">
        <vxe-input v-model="dataForm.outForWgt"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="内贸小计" field="inTotWgt" size="mini" span="12">
        <vxe-input v-model="dataForm.inTotWgt"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="内贸进口" field="inInWgt" size="mini" span="12">
        <vxe-input v-model="dataForm.inInWgt"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="内贸出口" field="inForWgt" size="mini" span="12">
        <vxe-input v-model="dataForm.inForWgt"></vxe-input>
      </vxe-form-item>
    </vxe-form>
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
  saveAll,
} from "@/api/statistical/tthroughput";
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
      // activeName: "first",
      // // ajaxOver: false,
      // // ajaxOverFirstMan: false,
      // tableDataTank: [],
      // tableDataTankSave: [],
      // // tableDataAssign: [],
      // // tableDataScep: [],
      // tableDataText: [],
      // tableloadTank: false,
      // // tableloadAssign: false,
      // tableloadText: false,
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
        billTitleA: "",
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
  },
  mounted: function () {},
  methods: {
    handleSubmit() {
      // var type = "add";
      // if (this.dataForm.contractNo != null) {
      //   type = "edit";
      // }
      // this.$refs.editHeadForm
      //   .validate((valid) => {})
      //   .then((valid) => {
      //     for (let i in this.tableDataTank) {
      //       if (
      //         this.tableDataTank[i].endDate < this.tableDataTank[i].beginDate
      //       ) {
      //         this.$message.error("租罐信息中结束时间不能小于结束时间");
      //         return;
      //       }
      //     }
      //     let loadingInstance = Loading.service({
      //       fullscreen: true,
      //       text: "正在保存………",
      //     });
      //     this.tableDataTankSave = this.$refs.tableTank.getCheckboxRecords();
      //     console.log(tableDataTankSave);
      //     if (this.tableDataTankSave.length == 0) {
      //       this.$message.error("租罐信息不能无选中信息");
      //       return;
      //     }
          saveAll(this.dataForm)
            .then((data) => {
              this.$message.success("保存成功");
              // loadingInstance.close();
              this.$emit("selectListplan");
              this.$emit("dictItemVisible");
            })
            .catch(() => {});
        // });
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