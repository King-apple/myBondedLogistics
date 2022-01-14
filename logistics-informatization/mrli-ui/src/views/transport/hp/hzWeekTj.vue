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
  <div class="transport-hp-hzWeekTj">
    <basic-container>
       <!--查询区-->
      <vxe-form :data="formObj" size="mini">
        <vxe-form-item
          title="起始时间："
          field="dateBegin"
          size="mini"
          span="8"
        >
          <vxe-input
            v-model="formObj.dateBegin"
            placeholder="起始时间"
            type="date"
            :disabled-method="disabledDateMethod"
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="left">
          <vxe-button status="primary" icon="fa fa-search" @click="getDate()"
            >查询</vxe-button></vxe-form-item>
            <vxe-form-item span="4" size="mini" align="left">
          <vxe-button status="primary" icon="fa fa-print" @click="getPrint()"
            >打印</vxe-button>
        </vxe-form-item>
      </vxe-form>
     <el-row type="flex" justify="center" >
        <el-col :span="10"><div class="grid-content" align="center">衡重量周统计报表</div></el-col>
      </el-row>
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
        height="180px"
        ref="tableOne"
      >
      
        <vxe-table-column  field="subunitName" width="150" title="部门"></vxe-table-column>
        <vxe-table-column field="TWeekHead" title="本周完成情况">
           <vxe-table-column
          field="SumThisWeek"
          title="本周生产"
        ></vxe-table-column>
            <vxe-table-column
              field="SumLastWeek"
              title="上周"
            ></vxe-table-column>
            <vxe-table-column
              field="hbWeek"
              title="环比"
            ></vxe-table-column>
        </vxe-table-column>
        <vxe-table-column
          field="TMonthHead"
          title="本月完成情况"
        >
          <vxe-table-column
              field="SumThisMonth"
              title="本月"
            ></vxe-table-column>
            <vxe-table-column
              field="SumLastYearMonth"
              title="同期"
            ></vxe-table-column>
            <vxe-table-column
              field="tbMonth"
              title="同比"
            ></vxe-table-column>
        </vxe-table-column>
        <vxe-table-column
          field="TYearHead"
          title="年累计完成情况"
        >
            <vxe-table-column
              field="SumThisYear"
              title="年累计"
            ></vxe-table-column>
            <vxe-table-column
              field="SumLastYear"
              title="同期"
            ></vxe-table-column>
            <vxe-table-column
              field="tbYear"
              title="同比"
            ></vxe-table-column>
        </vxe-table-column>
      
       
      </vxe-table>
    </basic-container>
  </div>
</template>

<script>
import { fetchHzWeekTj } from "@/api/transport/hpmx";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import EditDown from "../../baseUtile/EditDown";
import moment from "moment";
export default {
  components: { EditDown },
  name: "transport-hp-hzWeekTj",
  data() {
    return {
      formObj: { tradeId: null, cargoName: null, dateBegin: null },
      tableData: [],
      
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
      if (this.formObj.dateBegin == null) {
        this.$message.error("请选择起始时间");
        return;
      }
      this.tableload = true;
      console.log("dateBegin::"+this.formObj.dateBegin);
      fetchHzWeekTj(Object.assign(this.formObj))
        .then((response) => {
          this.tableData=response.data.data;
          this.tableload = false;
        })
        .catch(() => {
          this.tableload = false;
        });
    },
    
    getPrint() {
      if (this.formObj.dateBegin == null) {
        this.$message.error("请选择月份");
        return;
      }
      this.tableload = true;
      window.open ("https://www.ykgportal.com/ureport/preview?_t=1,6&_u=oss_bcmcHzWeekTj.ureport.xml&dateBegin="+this.formObj.dateBegin, "newwindow", "height=800, width=900, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no") 
      this.tableload = false;
    },
   disabledDateMethod ({ date }) { 
     const dd = moment(date).format('dddd'); 
     if(dd!='Monday'){
        return true; 
     }
  
   },
  },
};
</script>
<style lang="scss" scoped>
.transport-hp-hzWeekTj {
  .el-tag {
    width: 100%;
  }
  .el-divider--horizontal {
    margin: 10px 0;
  }
  .vxe-toolbar {
    height: 25px;
  }
  .grid-content {
  margin:20 auto;
  font-size: 18px;
}
 
}
</style>