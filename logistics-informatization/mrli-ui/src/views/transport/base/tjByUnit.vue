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
  <div class="transport-base-tjByUnit">
    <basic-container>
      <!--查询区-->
      <vxe-form :data="formObj" size="mini">
        <vxe-form-item title="月份：" field="dateNow" size="mini" span="6">
          <vxe-input
            v-model="formObj.dateNow"
            placeholder="月选择"
            type="month"
            valueFormat="yyyy-MM"
          >
          </vxe-input>
        </vxe-form-item>
       <vxe-form-item title="内外贸：" field="tradeId" size="mini" span="5">
          <vxe-select v-model="formObj.tradeId" clearable size="mini">
            <vxe-option value="内" label="内贸"></vxe-option>
            <vxe-option value="外" label="外贸"></vxe-option>
          </vxe-select>
        </vxe-form-item>
       <vxe-form-item title="申请单位" field="sqdw" size="mini" span="7">
          <vxe-input v-model="formObj.sqdw" clearable size="mini"></vxe-input>
        </vxe-form-item>
        <!-- <vxe-form-item title="申请单位" field="sqdwName" size="mini" span="8">
          <EditDown
            :objs="formObj"
            :property="downTableOption.sqdwName"
            :tableColumns="downTableOption.sqdwNameColum"
            :urlapi="downTableOption.sqdwNameurl"
            :returnApi="downTableOption.sqdwNamereturnApi"
            sizenow="mini"
          ></EditDown>
        </vxe-form-item>-->
        <vxe-form-item span="3" size="mini" align="left">
          <vxe-button status="primary" icon="fa fa-search" @click="getDate()"
            >查询</vxe-button
          >
        </vxe-form-item>
        <vxe-form-item span="3" size="mini" align="left">
          <vxe-button status="primary" icon="fa fa-print" @click="getPrint()"
            >打印</vxe-button>
        </vxe-form-item>
      </vxe-form>
      <!--功能按钮-->
      
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
        height="550px"
        ref="tableOne"
      >
        <vxe-table-column type="seq" width="50" title="序号"></vxe-table-column>
        <vxe-table-column field="sqdwunitName" width="240" title="申请单位"></vxe-table-column>
        <vxe-table-column
          field="nweightSumByMon"
          title="内贸吨数"
        ></vxe-table-column>
        <vxe-table-column
          field="wweightSumByMon"
          title="外贸吨数"
        ></vxe-table-column>
        <vxe-table-column
          field="weigthSumByMon"
          title="衡重吨数"
        ></vxe-table-column>
        <vxe-table-column
          field="weightSum"
          title="全年累计吨数"
        ></vxe-table-column>
      </vxe-table>
    </basic-container>
  </div>
</template>

<script>
import { fetchListOfUnit } from "@/api/transport/hpmx";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import EditDown from "../../baseUtile/EditDown";
export default {
  components: { EditDown },
  name: "transport-base-tjByUnit",
  data() {
    return {
      formObj: {tradeId:null,sqdw:null,datanow:null},
      tableData: [],
      tableload: false,
      // downTableOption: {
      //   //申请单位
      //   sqdwName: "sqdwName",
      //   sqdwNameColum: [
      //     { field: "CARGO_NAM_COD", title: "单位代码" },
      //      { field: "CARGO_NAM", title: "单位名称" },
          
      //   ],
      //   sqdwNamereturnApi: [
      //     { field: "sqdw", returnName: "sqdwName" },
      //   ],
      //   //cargoNameurl: "/basecode/basecod/getCargoNamAllPage",//待修改
      // },
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  created() {},
  methods: {
    getDate() {
      if(this.formObj.dateNow==null){
        this.$message.error("请选择月份");
        return;
      }
      this.tableload = true;
      fetchListOfUnit(
        Object.assign(
          this.formObj
        )
      )
        .then((response) => {
          this.tableData = response.data.data;
          this.tableload = false;
        })
        .catch(() => {
          this.tableload = false;
        });
    },
    getPrint() {
      if (this.formObj.dateNow == null) {
        this.$message.error("请选择月份");
        return;
      }
      this.tableload = true;
      window.open ("https://www.ykgportal.com/ureport/preview?_t=1&_u=oss_hpmcTjPrint.ureport.xml&dateNow="+this.formObj.dateNow+"&sqdw="+this.formObj.sqdw, "newwindow", "height=800, width=900, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no") 
      this.tableload = false;
    },
  },
};
</script>
<style lang="scss" scoped>
.transport-base-tjByUnit {
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