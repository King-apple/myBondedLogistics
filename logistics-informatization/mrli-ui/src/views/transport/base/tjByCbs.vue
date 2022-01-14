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
  <div class="transport-base-tjByCbs">
    <basic-container>
      <!--查询区-->
      <vxe-form :data="formObj" size="mini">
        <vxe-form-item title="开始日期：" field="dataNowS" size="mini" span="9">
          <vxe-input v-model="formObj.dataNowS" placeholder="请选择" type="datetime">
          </vxe-input>
        </vxe-form-item>
         <vxe-form-item title="结束日期：" field="dataNowE" size="mini" span="9">
          <vxe-input v-model="formObj.dataNowE" placeholder="请选择" type="datetime">
          </vxe-input>
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
          field="cbs"
          width="100"
          title="称别"
        ></vxe-table-column>
        <vxe-table-column
          field="chSum"
          width="100"
          title="车数"
        ></vxe-table-column>
        <vxe-table-column
          field="hzSum"
          title="货重"
          width="200"
        ></vxe-table-column>
      </vxe-table>
    </basic-container>
  </div>
</template>

<script>
import { fetchListOfCbs } from "@/api/transport/hpmx";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import EditDown from "../../baseUtile/EditDown";
export default {
  components: { EditDown },
  name: "transport-base-tjByCbs",
  data() {
    return {
      formObj: { dataNowS: null, dataNowE: null },
      tableData: [],
      tableload: false,   
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
      fetchListOfCbs(Object.assign(this.formObj))
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
.transport-base-tjByCbs {
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