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
  <div class="transport-base-hzAllTj">
    <basic-container>
      <!--查询区-->
      <vxe-form :data="formObj" size="mini">
        <vxe-form-item title="开始日期：" field="dataNowS" size="mini" span="5">
          <vxe-input
            v-model="formObj.dataNowS"
            placeholder="请选择"
            type="date"
          >
          </vxe-input>
        </vxe-form-item>
        <vxe-form-item title="结束日期：" field="dataNowE" size="mini" span="5">
          <vxe-input
            v-model="formObj.dataNowE"
            placeholder="请选择"
            type="date"
          >
          </vxe-input>
        </vxe-form-item>
        <vxe-form-item title="称别组：" field="cbs" size="mini" span="4">
          <EditDown
            :objs="formObj"
            :property="downTableOption.cbsNam"
            :tableColumns="downTableOption.cbsColum"
            :urlapi="downTableOption.cbsurl"
            :returnApi="downTableOption.cbsreturnApi"
            sizenow="mini"
            pageSize="30"
            height="550"
            width="150"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item title="货名：" field="hm" size="mini" span="4">
          <EditDown
            :objs="formObj"
            :property="downTableOption.hmNam"
            :tableColumns="downTableOption.hmColum"
            :urlapi="downTableOption.hmurl"
            :returnApi="downTableOption.hmreturnApi"
            sizenow="mini"
            pageSize="100"
            height="550"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item title="货类：" field="cargoKind" size="mini" span="5">
          <vxe-select
            v-model="formObj.cargoKind" clearable
            :options="cargoKindList"
          ></vxe-select>
        </vxe-form-item>
        <vxe-form-item title="作业类型：" field="workKind" size="mini" span="5">
          <vxe-select v-model="formObj.workKind" clearable size="mini">
            <vxe-option value="A" label="市出作业"></vxe-option>
            <vxe-option value="B" label="市入作业"></vxe-option>
            <vxe-option value="C" label="船舶作业"></vxe-option>
            <vxe-option value="D" label="火车作业"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item title="内外贸：" field="nw" size="mini" span="4">
          <vxe-select v-model="formObj.nw" clearable size="mini">
            <vxe-option value="内" label="内贸"></vxe-option>
            <vxe-option value="外" label="外贸"></vxe-option>
          </vxe-select>
        </vxe-form-item> 
       <vxe-form-item title="申请单位：" field="sqdw" size="mini" span="7">
          <vxe-input v-model="formObj.sqdw" clearable size="mini"></vxe-input>
        </vxe-form-item>
       <vxe-form-item title="作业公司：" field="departmentName" size="mini" span="8">
          <EditDown
          :objs="formObj"
          :property="downTableOption.unitNam"
          :tableColumns="downTableOption.unitColum"
          :urlapi="downTableOption.uniturl"
          :returnApi="downTableOption.unitreturnApi"
          sizenow="mini"
        ></EditDown>
        </vxe-form-item>

        <vxe-form-item span="3" size="mini" align="left">
          <vxe-button status="primary" icon="fa fa-search" @click="getDate()"
            >查询</vxe-button
          >
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
        height="250px"
        ref="tableOne"
      >
        <vxe-table-column type="seq" width="50" title="序号"></vxe-table-column>
        <vxe-table-column
          field="sumHz"
          width="160"
          title="衡重量"
        ></vxe-table-column>
        <vxe-table-column
          field="sumCsl"
          width="100"
          title="衡重车次"
        ></vxe-table-column>
        <vxe-table-column
          field="tbHz"
          width="200"
          title="同比衡重量"
        ></vxe-table-column>
        <vxe-table-column
          field="zjHz"
          width="200"
          title="增减量"
        ></vxe-table-column>
        <vxe-table-column
          field="tbHz100"
          width="80"
          title="同比%"
        ></vxe-table-column>
      </vxe-table>
    </basic-container>
  </div>
</template>

<script>
import { fetchListOfhzAllTj } from "@/api/transport/hpmx";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import EditDown from "../../baseUtile/EditDown";
export default {
  components: { EditDown },
  name: "transport-base-hzAllTj",
  data() {
    return {
      formObj: { dataNowS: null, dataNowE: null,cbs:null,hm:null,cargoKind:null,workKind:null,nw:null,sqdw:null,department:null,departmentName:null },
      tableData: [],
      cargoKindList: [
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
        //称别组
        cbsNam: "cbs",
        cbsColum: [{ field: "CBS", title: "称别组" }],
        cbsreturnApi: [{ field: "CBS", returnName: "cbs" }],

        cbsurl: "/transport/hpmx/getCbsDoc",
         //货名
        hmNam: "hm",
        hmColum: [
          { field: "CARGO_NAM_COD", title: "货名代码" },
          { field: "CARGO_NAM", title: "货名" },
        ],
        hmreturnApi: [{ field: "CARGO_NAM", returnName: "hm" }],
        hmurl: "/basecode/basecod/getCargoNamAllPage",
        //单位
        unitNam: "departmentName",
        unitColum: [
          { field: "UNIT_COD", title: "单位代码" },
          { field: "UNIT_NAM", title: "单位名称" },
        ],
        unitreturnApi: [
          { field: "UNIT_COD", returnName: "department" },
          { field: "UNIT_NAM", returnName: "departmentName" },
        ],
        uniturl: "/basecode/basecod/getUnitNam",
      },
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  created() {},
  methods: {
    getDate() {
      if (this.formObj.dataNowS == null) {
        this.$message.error("请选择开始时间");
        return;
      }
      if (this.formObj.dataNowE == null) {
        this.$message.error("请选择结束时间");
        return;
      }
      this.tableload = true;
      fetchListOfhzAllTj(Object.assign(this.formObj))
        .then((response) => {
          this.tableData = response.data.data;
          this.tableload = false;
        })
        .catch(() => {
          this.tableload = false;
        });
    },
  },
};
</script>
<style lang="scss" scoped>
.transport-base-hzAllTj {
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