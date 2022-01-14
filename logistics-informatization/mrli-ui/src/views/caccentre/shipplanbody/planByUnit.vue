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
  <div class="shipplanbody-PlanByUnit">
    <basic-container>
      <el-row>
        <el-col :span="8"
          ><div class="grid-content">日期：{{ dataForm.datePlan }}</div></el-col
        >
        <el-col :span="8"
          ><div class="grid-content">
            主任：{{ dataForm.masterMan }}
          </div></el-col
        >
        <el-col :span="8"
          ><div class="grid-content">
            计划员：{{ dataForm.planMan }}
          </div></el-col
        >
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
        height="500px"
        ref="tableOne"
        :mouse-config="{ selected: true }"
        :checkbox-config="{ range: true, checkMethod: checCheckboxkMethod }"
      >
        <vxe-table-column type="seq" width="50" title="序号"></vxe-table-column>
        <vxe-table-column type="checkbox" width="60"></vxe-table-column>
        <vxe-table-column
          field="unitNam"
          width="300"
          title="接收公司名称"
        ></vxe-table-column>
      </vxe-table>
      <div align="center" class="grid-content">
        <el-button
          type="primary"
          size="mini"
          icon="el-icon-check"
          @click="copyPlanData()"
          >接收计划</el-button
        >
        <el-button size="mini" icon="el-icon-close" plain @click="closeWindows"
          >取消</el-button
        >
      </div>
    </basic-container>
  </div>
</template>

<script>
import { getPlanUnitNam,insertPlanData } from "@/api/caccentre/shipplanbody";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import EditDown from "../../baseUtile/EditDown";
export default {
  props: ["dataForm"], //加载传过来的
  components: { EditDown },
  name: "shipplanbody-PlanByUnit",
  data() {
    return {
      tableData: [],
      tableload: false,
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  created() {
    this.getDate();
  },
  methods: {
    getDate() {
      this.tableload = true;
      getPlanUnitNam(this.dataForm.datePlan)
        .then((response) => {
          this.tableData = response.data.data;
          this.tableload = false;
        })
        .catch(() => {
          this.tableload = false;
        });
    },
    checCheckboxkMethod ({ row }) {
              return row.dataKnd != '2'
            },
    copyPlanData() {
      var list = this.$refs.tableOne.getCheckboxRecords();
      let length = list.length;
      if(length == 0){
        this.$XModal.alert("请选择一家公司！");
      }else{
        insertPlanData(list,this.dataForm)
          .then(data => {
            if(data.data.data=="接收成功"){ 
              this.$message.success("接收成功");
              this.$emit("dictItemVisible");
            }else{
            this.$XModal.alert(data.data.data);
            }           
          })
            .catch(() => {});
      }      
    },
    closeWindows() {
      this.$emit("dictItemVisible");
    },
  },
};
</script>
<style lang="scss" scoped>
.shipplanbody-PlanByUnit {
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
    margin: 10px auto;
  }
}
</style>