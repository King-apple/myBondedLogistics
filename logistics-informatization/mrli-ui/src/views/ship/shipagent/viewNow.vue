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
  <div class="ship-shipagent-viewNow">
    <vxe-form
      :data="dataForm"
      title-align="right"
      title-width="100"
      size="mini"
      ref="editHeadForm"
    >
      <vxe-form-item title="船东代码" field="agentCod" size="mini" span="24">
        <vxe-input v-model="dataForm.agentCod" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="船东名称" field="agentNam" size="mini" span="24">
        <vxe-input v-model="dataForm.agentNam" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="地址" field="address" disabled="mini" span="24">
        <vxe-input v-model="dataForm.address" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item
        title="开户账号"
        field="agentAccount"
        size="mini"
        span="24"
      >
        <vxe-input v-model="dataForm.agentAccount" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="银行" field="agentBank" size="mini" span="24">
        <vxe-input v-model="dataForm.agentBank" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="邮箱" field="eMail" size="mini" span="24">
        <vxe-input v-model="dataForm.eMail" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="传真" field="fax" size="mini" span="24">
        <vxe-input v-model="dataForm.fax" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="邮编" field="postalcode" size="mini" span="24">
        <vxe-input v-model="dataForm.postalcode" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="联系人" field="relatMan" size="mini" span="24">
        <vxe-input v-model="dataForm.relatMan" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="电话" field="phone" size="mini" span="24">
        <vxe-input v-model="dataForm.phone" disabled></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="注册时间" field="markDte" size="mini" span="24">
        <vxe-input
          v-model="dataForm.markDte"
          valueFormat="yyyy-MM-dd"
          type="date"
          disabled
        ></vxe-input>
      </vxe-form-item>
    </vxe-form>
    <div align="center">
      <vxe-button size="mini" icon="el-icon-close" plain @click="closeWindows"
        >取消</vxe-button
      >
    </div>
  </div>
</template>

<script>
import { saveObj } from "@/api/ship/shipagent";
import request from "@/router/axios";
import "@/styles/common/edit.scss";
import { mapGetters } from "vuex";
import moment from "moment";
import { mapState } from "vuex";
import Vue from "vue";
import VXETable from "vxe-table";
import hljs from "highlight.js";
import { Loading, Message } from "element-ui";
import EditDown from "../../baseUtile/EditDown";
export default {
  props: ["datanow","dataType"],
  components: { EditDown },
  data() {
    return {
      dataForm: {},
      formRules: {
        agentCod: [{ required: true, message: "请填写代码" }],
        agentNam: [{ required: true, message: "请填写名称" }]
      },
      downTableOption: {
        //货主
        shipAgentNam: "shipAgentNam",
        shipAgentNamColum: [
          { field: "agentNam", title: "船东名称" },
          { field: "agentCod", title: "船东代码" }
        ],
        shipAgentNamreturnApi: [
          { field: "agentNam", returnName: "shipAgentNam" },
          { field: "agentCod", returnName: "shipAgentCod" }
        ],
        shipAgentNamurl: "/ship/shipagent/page"
      }
    };
  },
  computed: {},
  created() {
      this.dataForm = this.datanow;
  },
  mounted: function() {},
  methods: {
    closeWindows() {
      this.$emit("dictItemVisible");
    },
  }
};
</script>
<style lang="scss" scoped>
.ship-shipagent-viewNow {
  .el-divider--horizontal {
    margin: 5px 0;
  }
}
</style>