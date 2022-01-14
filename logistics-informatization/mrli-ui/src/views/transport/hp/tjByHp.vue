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
  <div class="transport-base-tjByHp">
    <basic-container>
      <!--查询区-->
      <vxe-form :data="formObj" size="mini">
        <vxe-form-item
          title="作业公司"
          field="dataName"
          size="mini"
          span="6"
        >
          <EditDown
            :objs="formObj"
            :property="downTableOption.unitNam"
            :tableColumns="downTableOption.unitColum"
            :urlapi="downTableOption.uniturl"
            :returnApi="downTableOption.unitreturnApi"
            sizenow="mini"
          ></EditDown>
        </vxe-form-item>
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

        <vxe-form-item title="读取否" field="dq" size="mini" span="4">
          <vxe-select v-model="formObj.dq" size="mini">
            <vxe-option value="" label=""></vxe-option>
            <vxe-option value="A" label="未读取"></vxe-option>
            <vxe-option value="B" label="已读取"></vxe-option>
          </vxe-select>
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
        height="740px"
        ref="tableOne"
      >
        <vxe-table-column type="seq" width="50" title="序号"></vxe-table-column>
        <vxe-table-column
          field="recordDte"
          width="160"
          title="申请日期"
        ></vxe-table-column>
        <vxe-table-column
          field="checkNo"
          width="100"
          title="编号"
        ></vxe-table-column>
        <vxe-table-column
          field="cargoNam"
          width="200"
          title="货名"
        ></vxe-table-column>
        <vxe-table-column
          field="shipNam"
          width="200"
          title="船名"
        ></vxe-table-column>
        <vxe-table-column
          field="shipNo"
          width="80"
          title="船号"
        ></vxe-table-column>
        <vxe-table-column
          field="weightWgt"
          width="100"
          title="重量"
        ></vxe-table-column>
        <vxe-table-column
          field="billNo"
          width="200"
          title="提单号"
        ></vxe-table-column>
        <vxe-table-column
          field="trustNo"
          width="80"
          title="委托号"
        ></vxe-table-column>
        <vxe-table-column
          field="requireUnitNam"
          width="200"
          title="申请单位"
        ></vxe-table-column>
        <vxe-table-column
          field="remarkTxt"
          width="200"
          title="备注"
        ></vxe-table-column>
        <vxe-table-column
          field="inoutId"
          width="50"
          title="进出口"
        ></vxe-table-column>
        <vxe-table-column
          field="tradeId"
          width="50"
          title="内外贸"
        ></vxe-table-column>
        <vxe-table-column
          field="addMinRat"
          width="50"
          title="限额"
        ></vxe-table-column>
        <vxe-table-column
          field="checkId"
          width="50"
          title="商检否"
        ></vxe-table-column>
        <vxe-table-column
          field="subunitNam"
          width="150"
          title="作业库区"
        ></vxe-table-column>
        <vxe-table-column
          field="workPlace"
          width="150"
          title="堆存地点"
        ></vxe-table-column>
        <vxe-table-column
          field="cargoKindCod"
          width="50"
          title="货类代码"
        ></vxe-table-column>
        <vxe-table-column
          field="berthCod"
          width="50"
          title="泊位号"
        ></vxe-table-column>
        <vxe-table-column
          field="etId"
          width="50"
          title="电标"
        ></vxe-table-column>
      </vxe-table>
    </basic-container>
  </div>
</template>

<script>
import { fetchListOfHp } from "@/api/transport/hpmx";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import EditDown from "../../baseUtile/EditDown";
export default {
  components: { EditDown },
  name: "transport-base-tjByHp",
  data() {
    return {
      formObj: { dataNowS: null, dataNowE: null,dataName:null,bm:null,dq:null },
      tableData: [],
      tableload: false,
      downTableOption: {
        //单位
        unitNam: "dataName",
        unitColum: [
          { field: "BM", title: "连接地址" },
          { field: "DATANAME", title: "单位名称" },
        ],
        unitreturnApi: [
          { field: "BM", returnName: "bm" },
          { field: "DATANAME", returnName: "dataName" },
        ],
        uniturl: "/transport/checkweight/NetData",
      },
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  created() {},
  methods: {
    getDate() {
      if (this.formObj.dataName == null) {
        this.$message.error("请选择生产公司");
        return;
      }
      if (this.formObj.dataNowS == null) {
        this.$message.error("请选择开始时间");
        return;
      }
      if (this.formObj.dataNowE == null) {
        this.$message.error("请选择结束时间");
        return;
      }
      this.tableload = true;
      fetchListOfHp(Object.assign(this.formObj))
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
.transport-base-tjByHp {
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