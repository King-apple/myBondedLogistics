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
  <div class="transport-base-tjByCargo">
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
        <vxe-form-item title="货名" field="cargoName" size="mini" span="5">
          <vxe-select
            v-model="formObj.cargoName"
            :options="cargoNameList"
          ></vxe-select>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="left">
          <vxe-button status="primary" icon="fa fa-search" @click="getDate()"
            >查询</vxe-button></vxe-form-item>
            <vxe-form-item span="4" size="mini" align="left">
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
        <vxe-table-column field="cargoTypeName" title="货类"></vxe-table-column>
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
import { fetchListOfCargo } from "@/api/transport/hpmx";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import EditDown from "../../baseUtile/EditDown";
export default {
  components: { EditDown },
  name: "transport-base-tjByCargo",
  data() {
    return {
      formObj: { tradeId: null, cargoName: null, datanow: null },
      tableData: [],
      cargoNameList: [
        { label: "石油天然气制品", value: "石油天然气制品" },
        { label: "非金属矿石", value: "非金属矿石" },
        { label: "木材", value: "木材" },
        { label: "有色金属", value: "有色金属" },
        { label: "化工原料及制品", value: "化工原料及制品" },
        { label: "农丶林丶牧丶渔业产品", value: "农丶林丶牧丶渔业产品" },
        { label: "钢铁", value: "钢铁" },
        { label: "轻工医药产品", value: "轻工医药产品" },
        { label: "盐", value: "盐" },
        { label: "其它货类", value: "其它货类" },
        { label: "煤炭及制品", value: "煤炭及制品" },
        { label: "金属矿石", value: "金属矿石" },
        { label: "集装箱", value: "集装箱" },
        { label: "机械丶设备丶电器", value: "机械丶设备丶电器" },
        { label: "矿建材料", value: "矿建材料" },
        { label: "化肥及农药", value: "化肥及农药" },
        { label: "水泥", value: "水泥" },
        { label: "粮食", value: "粮食" },
        { label: "镍矿石", value: "镍矿石" },
      ],
      tableload: false,
      downTableOption: {
        //货名
        cargoName: "cargoName",
        cargoNameColum: [
          { field: "CARGO_NAM_COD", title: "货名代码" },
          { field: "CARGO_NAM", title: "货名" },
        ],
        cargoNamereturnApi: [{ field: "CARGO_NAM", returnName: "cargoName" }],
        cargoNameurl: "/basecode/basecod/getCargoNamAllPage",
      },
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  created() {},
  methods: {
    getDate() {
      if (this.formObj.dateNow == null) {
        this.$message.error("请选择月份");
        return;
      }
      this.tableload = true;
      fetchListOfCargo(Object.assign(this.formObj))
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
      window.open ("https://www.ykgportal.com/ureport/preview?_t=1&_u=oss_hpmcTjPrint.ureport.xml&dateNow="+this.formObj.dateNow+"&tradeId="+this.formObj.tradeId+"&cargoName="+this.formObj.cargoName, "newwindow", "height=800, width=900, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no") 
      this.tableload = false;
    },
  },
};
</script>
<style lang="scss" scoped>
.transport-base-tjByCargo {
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