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
  <div class="business-jg-contChangeList">
    <vxe-toolbar perfect size="mini">
      <template v-slot:buttons>
        <vxe-button type="text" class="fa fa-plus" @click="view()">详情</vxe-button>
      </template>
    </vxe-toolbar>
    <vxe-table
      border
      show-overflow
      keep-source
      highlight-current-row
      size="mini"
      :data="tableData"
      :loading="tableload"
      :round="true"
      align="center"
      height="500px"
      ref="contChangeRef"
      @cell-click="cellClickEvent"
    >
      <vxe-table-column field="CONT_NO" title="合同号"></vxe-table-column>
      <vxe-table-column field="UNIT_COD" title="公司代码"></vxe-table-column>
      <vxe-table-column field="CREATE_TIME" title="痕迹时间"></vxe-table-column>
      <vxe-table-column field="CREATE_OPT" title="痕迹人"></vxe-table-column>
    </vxe-table>
    <vxe-modal
      v-if="dialogFormVisibleView"
      v-model="dialogFormVisibleView"
      title="合同详情"
      width="1200"
      height="900"
      resize
      storage
      fullscreen
    >
      <ViewChange :datanow="clickData" @dictItemVisible="dictItemVisible"></ViewChange>
    </vxe-modal>
  </div>
</template>

<script>
import { selectContChangeGet } from "@/api/business/jg";
import request from "@/router/axios";
import "@/styles/common/edit.scss";
import { mapGetters } from "vuex";
import moment from "moment";
import Vue from "vue";
import VXETable from "vxe-table";
import hljs from "highlight.js";
import { Loading, Message } from "element-ui";
import ViewChange from "../cont/viewChange";
export default {
  props: ["unitCod", "contNo"],
  components: { ViewChange },
  data() {
    return {
      tableload: false,
      tableData: [],
      clickData: null,
      dialogFormVisibleView: false,
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  created() {
    this.getDate();
  },
  mounted: function () {},
  methods: {
    getDate() {
      this.tableload = true;
      selectContChangeGet(this.contNo, this.unitCod)
        .then((response) => {
          this.tableData = response.data.data;
          this.tableload = false;
        })
        .catch(() => {
          this.tableload = false;
        });
    },

    closeWindows() {
      this.$emit("dictItemVisible");
    },
    cellClickEvent({ row, column }) {
      this.clickData = row;
    },
    view() {
      if (this.clickData == null) {
        this.$message.error("请选择一个合同痕迹进行查询");
        return;
      }
      this.dialogFormVisibleView = true;
    },
    dictItemVisible() {
      this.dialogFormVisibleView = false;
    },
  },
};
</script>
<style lang="scss">
.business-jg-contChangeList {
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