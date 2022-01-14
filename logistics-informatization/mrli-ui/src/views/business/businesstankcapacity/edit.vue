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
    <vxe-form
      :data="dataForm"
      size="mini"
      ref="editHeadForm"
      :rules="formRules"
      title-width="100"
    >
      <vxe-form-item title="公司" field="unitNam" size="mini" span="24">
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
      <vxe-form-item title="罐容量" field="capacity" size="mini" span="24">
        <vxe-input v-model="dataForm.capacity"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="备注" field="remark" size="medium" span="24">
        <vxe-textarea v-model="dataForm.remark"></vxe-textarea>
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
import { save } from "@/api/business/businesstankcapacity";
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
      // ajaxOver: false,
      // ajaxOverFirstMan: false,
      // tableDataCargo: [],
      // tableDataAssign: [],
      // tableDataScep: [],
      // tableDataText: [],
      // tableloadCargo: false,
      // tableloadAssign: false,
      // tableloadText: false,
      // tableloadScep: false,
      // advanceNumReadonly: true,
      // validRules: {},
      formRules: {
        unitNam: [{ required: true, message: "请选择公司" }],
        capacity: [{ required: true, message: "请填写罐容量" }],
      },
      // amtAll: 0,
      // weightAll: 0,
      // textnum: 1,
      dataForm: {
        // plantNo: null,
        // shipNo: null,
        // shipNam: null,
        // inoutId: null,
        // tradeId: null,
        // toPortTim: null,
        // firstNam: "",
        unitCod: "",
        unitNam: "",
        // firstMan: null,
        // firstFax: null,
        // firstPhone: null,
      },
      downTableOption: {
        //单位
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
      if (this.dataForm.unitCod == "" || this.dataForm.unitCod == null) {
        this.$message.error("请选择公司");
        return;
      }
      if (this.dataForm.capacity == null) {
        this.$message.error("请填写罐容量");
        return;
      }
      this.$refs.editHeadForm
        .validate((valid) => {})
        .then((valid) => {
          save(this.dataForm)
            .then((data) => {
              this.$message.success("保存成功");
              this.$emit("selectListplan");
              this.$emit("dictItemVisible");
            })
            .catch(() => {});
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