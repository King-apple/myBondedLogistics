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
  <div class="transport-blockchain-index">
    <basic-container>
      <!--查询区-->
      <vxe-form :data="formObj" size="mini">
        <vxe-form-item title="计量号" field="jlh" size="mini" span="4">
          <vxe-input v-model="formObj.jlh" clearable size="mini"></vxe-input>
          <!--<EditDown
            :objs="formObj"
            :property="downTableOption.ch"
            :tableColumns="downTableOption.carnoColum"
            :urlapi="downTableOption.carnourl"
            :returnApi="downTableOption.carnoreturnApi"
            sizenow="mini"
            pageSize="10"
          ></EditDown>-->
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="left">
          <vxe-button status="primary" icon="fa fa-search" @click="getDate()"
            >查询</vxe-button
          >
        </vxe-form-item>
        <!--
        <vxe-form-item title="内外贸：" field="tradeId" size="mini" span="6">
          <vxe-select v-model="formObj.tradeId" clearable size="mini">
            <vxe-option value="1" label="内贸"></vxe-option>
            <vxe-option value="0" label="外贸"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item title="进出口：" field="inoutId" size="mini" span="5">
          <vxe-select v-model="formObj.inoutId" clearable size="mini">
            <vxe-option value="1" label="进口"></vxe-option>
            <vxe-option value="0" label="出口"></vxe-option>
          </vxe-select>
        </vxe-form-item> -->
      </vxe-form>
      <!--功能按钮-->
      <vxe-toolbar perfect size="mini">
        <template v-slot:buttons> </template>
      </vxe-toolbar>
      <!--表格-->
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
        height="650px"
        ref="tableMan"
      >
        <vxe-table-column field="jlh" title="计量号"></vxe-table-column>
        <vxe-table-column field="bh" title="货票号"></vxe-table-column>
        <vxe-table-column field="ch" title="车号"></vxe-table-column>
        <vxe-table-column field="mz" title="过重重量"></vxe-table-column>
        <vxe-table-column field="zcsj" title="重车时间"></vxe-table-column>
        <vxe-table-column field="kcsj" title="空车时间"></vxe-table-column>
        <vxe-table-column field="cz" title="过空重量"></vxe-table-column>
        <vxe-table-column field="hz" title="货重"></vxe-table-column>
        <vxe-table-column field="zccb" title="过重称别"></vxe-table-column>
        <vxe-table-column field="kccb" title="过空称别"></vxe-table-column>
      </vxe-table>
    </basic-container>
  </div>
</template>

<script>
import { fetchList } from "@/api/transport/blockchain";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import EditDown from "../../baseUtile/EditDown";
export default {
  components: { EditDown },
  name: "transport-blockchain-index",
  data() {
    return {
      formObj: { jlh: null },
      tableData: [],
      dateNow: {},
      dataHeadNow: {},

      tableload: false,
      downTableOption: {
        //船编号
        ch: "ch",
        carnoColum: [
          { field: "JLH", title: "计量号" },
          { field: "BH", title: "衡中申请单号" },
          { field: "CH", title: "车号" },
        ],
        carnoreturnApi: [
          { field: "CH", returnName: "ch" },
          { field: "JLH", returnName: "jlh" },
        ],
        carnourl: "/transport/bcmc/bcmcPageOfDownUp",
      },
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  created() {},
  methods: {
    getDate() {
      if (
        this.formObj.jlh == null ||
        this.formObj.jlh == undefined ||
        this.formObj.jlh == ""
      ) {
        this.$message.error("请选择一个车进行查询");
        return;
      }
      let loadingInstance = Loading.service({
        fullscreen: true,
        text: "正在进入区块链进行查询………",
      });
      this.dataHeadNow = {};
      fetchList(this.formObj.jlh)
        .then((response) => {
          this.tableData = response.data.data;
          loadingInstance.close();
        })
        .catch(() => {
          loadingInstance.close();
        });
    },

    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
  },
};
</script>
<style lang="scss" scoped>
.transport-blockchain-index {
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
</style>>