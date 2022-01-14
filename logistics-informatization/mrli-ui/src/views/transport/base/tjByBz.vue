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
  <div class="transport-base-tjByBz">
    <basic-container>
      <!--查询区-->
      <vxe-form :data="formObj" size="mini">
        <vxe-form-item title="日期：" field="dataNow" size="mini" span="6">
          <vxe-input v-model="formObj.dataNow" placeholder="请选择" type="date">
          </vxe-input>
        </vxe-form-item>
        <vxe-form-item title="称别组：" field="cbs" size="mini" span="5">
          <EditDown
            :objs="formObj"
            :property="downTableOption.cbsNam"
            :tableColumns="downTableOption.cbsColum"
            :urlapi="downTableOption.cbsurl"
            :returnApi="downTableOption.cbsreturnApi"
            sizenow="mini"
            pageSize="30"
            height="450"
            width="150"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item title="班组：" field="bz" size="mini" span="5">
          <vxe-select v-model="formObj.bz" size="mini">
            <vxe-option value="A" label="早班"></vxe-option>
            <vxe-option value="B" label="晚班"></vxe-option>
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
        height="550px"
        ref="tableOne"
      >
        <vxe-table-column type="seq" width="50" title="序号"></vxe-table-column>
        <vxe-table-column
          field="lxdm"
          width="50"
          title="联系单号"
        ></vxe-table-column>
        <vxe-table-column
          field="bh"
          width="80"
          title="货票编号"
        ></vxe-table-column>
        <vxe-table-column
          field="tdh"
          title="提单号"
          width="120"
        ></vxe-table-column>
        <vxe-table-column
          field="hm"
          title="货名"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="cm"
          title="船名"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="jhl"
          title="计划量"
          width="80"
        ></vxe-table-column>
        <vxe-table-column
          field="wcl"
          title="完成量"
          width="80"
        ></vxe-table-column>
        <vxe-table-column
          field="syl"
          title="剩余量"
          width="80"
        ></vxe-table-column>
        <vxe-table-column
          field="bbwcl"
          title="本班完成量"
          width="80"
        ></vxe-table-column>
        <vxe-table-column
          field="dys"
          title="倒运数"
          width="80"
        ></vxe-table-column>
        <vxe-table-column
          field="sqdw"
          title="申请单位"
          width="200"
        ></vxe-table-column>
      </vxe-table>
    </basic-container>
  </div>
</template>

<script>
import { fetchListOfBz } from "@/api/transport/hpmx";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import EditDown from "../../baseUtile/EditDown";
export default {
  components: { EditDown },
  name: "transport-base-tjByBz",
  data() {
    return {
      formObj: { dataNow: null, cbs: null, bz: null },
      tableData: [],
      tableload: false,    
      downTableOption: {
        //称别组
        cbsNam: "cbs",
        cbsColum: [{ field: "CBS", title: "称别组" }],
        cbsreturnApi: [{ field: "CBS", returnName: "cbs" }],

        cbsurl: "/transport/hpmx/getCbsDoc",
      },
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  created() {},
  methods: {
    getDate() {
      if (this.formObj.dataNow == null) {
        this.$message.error("请选择月份");
        return;
      }
      if (this.formObj.cbs == null) {
        this.$message.error("称别组");
        return;
      }
      if (this.formObj.bz == null) {
        this.$message.error("班组");
        return;
      }
      this.tableload = true;
      fetchListOfBz(Object.assign(this.formObj))
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
.transport-base-tjByBz {
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