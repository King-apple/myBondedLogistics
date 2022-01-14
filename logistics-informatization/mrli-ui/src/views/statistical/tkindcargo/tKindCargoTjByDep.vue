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
  <div class="statistical-tkindcargo-tKindCargoTjByDep">
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
        
        <vxe-form-item span="2" size="mini" align="left">
          <vxe-button status="primary" icon="fa fa-search" @click="getData()"
            >查询</vxe-button></vxe-form-item>
            <vxe-form-item span="2" size="mini" align="left">
          <vxe-button status="primary" icon="fa fa-flask" @click="tjData()"
            >处理</vxe-button></vxe-form-item>
            <vxe-form-item span="6" size="mini" align="left">
        </vxe-form-item>
      </vxe-form>
     <el-row type="flex" justify="center" >
        <el-col :span="12"><div class="grid-content" align="center">营口港区月吞吐量完成情况</div></el-col>
      </el-row>
           <el-row type="flex" justify="end" >
        <el-col :span="12" ><div class="dw" align="right">计算单位：万吨</div></el-col>
      </el-row>
      <vxe-toolbar perfect size="mini" >
        <template v-slot:buttons >
          <vxe-button type="text" icon="fa fa-floppy-o"  @click="saveAll()"
            >保存</vxe-button
          >
        </template>
      </vxe-toolbar>
      <!--表格-->
      <vxe-table
        border
        show-overflow
        keep-source
        highlight-current-row
        size="mini"
        :data="tableData"
        :merge-cells="mergeCells"
        :loading="tableload"
        :round="true"
        align="center"
        height="648px"
        ref="tableOne"
        :edit-config="{ trigger: 'click', mode: 'cell', showStatus: true }"
      >
      
          <vxe-table-column
          field="name1" 
          title=""
        ></vxe-table-column>
        <vxe-table-column
          field="name2" 
          title=""
        ></vxe-table-column>
        <vxe-table-column
          field="name3" 
          title=""
        ></vxe-table-column>
 
           <vxe-table-column
          field="sumThismon"
          title="本月完成"
        ></vxe-table-column>
            <vxe-table-column
              field="sumLastmon"
              title="去年同期"
            ></vxe-table-column>
            <vxe-table-column
              field="tbMon"
              title="同比"
            ></vxe-table-column>
            <vxe-table-column
              field="zfMon"
              title="增幅"
            ></vxe-table-column>
          <vxe-table-column
              field="sumThisyearm"
              title="年累计完成"
            ></vxe-table-column>
            <vxe-table-column
              field="sumLastyearm"
              title="去年同期累计完成"
            ></vxe-table-column>
            <vxe-table-column
              field="tbYearm"
              title="同比"
            ></vxe-table-column>
            <vxe-table-column
              field="zfYearm"
              title="增幅"
            ></vxe-table-column>
            <vxe-table-column
              field="forecast"
              title="下月预测"
              :edit-render="{ name: 'input' }"
            ></vxe-table-column>

       
      </vxe-table>
    </basic-container>
  </div>
</template>

<script>
import { fetchTKindCargoTjByDep,countData } from "@/api/statistical/tthroughput";
import { getObjTjbydep,saveTj } from "@/api/statistical/tkindcargotjbydep";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import EditDown from "../../baseUtile/EditDown";
import moment from "moment";
export default {
  components: { EditDown },
  name: "statistical-tkindcargo-tKindCargoTjByDep",
  data() {
    return {
      formObj: { dateNow: null },
      tableData: [],
      mergeCells: [ { row: 0, col: 0, rowspan: 2, colspan: 2 },
                { row: 2, col: 0, rowspan: 0, colspan: 3 },
                { row: 3, col: 0, rowspan: 5, colspan: 0 },
                { row: 3, col: 1, rowspan: 0, colspan: 2 },
                { row: 4, col: 1, rowspan: 0, colspan: 2 },
                { row: 5, col: 1, rowspan: 0, colspan: 2 },
                { row: 6, col: 1, rowspan: 0, colspan: 2 },
                { row: 7, col: 1, rowspan: 0, colspan: 2 },
                { row: 8, col: 0, rowspan: 0, colspan: 3 },
                { row: 9, col: 0, rowspan: 2, colspan: 0 },
                { row: 9, col: 1, rowspan: 0, colspan: 2 },
                { row: 10, col: 1, rowspan: 0, colspan: 2 },
                { row: 11, col: 0, rowspan: 2, colspan: 2 },
                { row: 13, col: 0, rowspan: 4, colspan: 0 },
                { row: 13, col: 2, rowspan: 2, colspan: 0 },
                { row: 15, col: 2, rowspan: 2, colspan: 0 },
                 ],
      tData: [],      
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
  created() {
    this.getMonthNowData();
  },
  methods: {
    getMonthNowData(){
      var myDate = new Date();
      var dateY=myDate.getFullYear();       //获取当前月份(0-11,0代表1月);   获取完整的年份(4位,1970-????)
      var dateM=(myDate.getMonth()+1)>9?(myDate.getMonth()+1).toString():'0' + (myDate.getMonth()+1);
      var dateNow=dateY+"-"+dateM;
      console.log("dateNow::"+dateNow);
      this.formObj.dateNow =dateNow;
      getObjTjbydep(Object.assign(this.formObj))
              .then((response) => {
                if(response.data.data.length==0){
                  console.log("数据尚未处理！"+response.data.data.length);
                  this.$message.success(dateNow+"月数据尚未处理！");
                }
                this.tableData=response.data.data;
                this.reloadYs();
                this.tableload = false;
                });
    },
    getData() {
       if (this.formObj.dateNow == null) {
        this.$message.error("请选择月份");
        return;
      }
      this.tableload = true;
      console.log("dateNow::"+this.formObj.dateNow);
      getObjTjbydep(Object.assign(this.formObj))
            .then((response) => {
              if(response.data.data.length==0){
                  console.log("数据尚未处理！"+response.data.data.length);
                  this.$message.success(this.formObj.dateNow+"月数据尚未处理！");
                }
              this.tableData=response.data.data;
              this.reloadYs();
              this.tableload = false;
              });
    },
    tjData() {
      console.log("dateNow::111::"+this.formObj.dateNow);
      if (this.formObj.dateNow == null) {
        this.$message.error("请选择月份");
        return;
      }
      countData(this.formObj.dateNow)
        .then((response) => {
          this.countTj = response.data.data;
          console.log("countTj::::"+this.countTj);
          if(this.countTj>0){
            this.$XModal.confirm("该月数据已处理，是否重新处理？").then((type) => {
             if (type == "confirm") {
                this.tableload = true;

                fetchTKindCargoTjByDep(Object.assign(this.formObj))
                  .then((response) => {
                    console.log("response::"+response.data.data.data);
                    if(response.data.data.data=="ok"){ 
                      getObjTjbydep(Object.assign(this.formObj))
                      .then((response) => {
                        this.tableData=response.data.data;
                        this.tableload = false;
                        this.reloadYs();
                        this.$message.success("处理成功");
                        });
                      }
                      else{this.$message.error("处理数据失败！");
                      this.tableload = false;};
                    
                  })
                  .catch(() => {
                    this.tableload = false;
                  });
                
              }
            });
          }
          else{
            this.tableload = true;
            console.log("dateNow::"+this.formObj.dateNow);
            fetchTKindCargoTjByDep(Object.assign(this.formObj))
              .then((response) => {
                console.log("response::"+response.data.data.data);
                if(response.data.data.data=="ok"){ 
                  getObjTjbydep(Object.assign(this.formObj))
                  .then((response) => {
                    this.tableData=response.data.data;
                    this.tableload = false;
                    this.reloadYs();
                    this.$message.success("处理成功");
                    });
                  }
                  else{this.$message.error("处理数据失败！");
                  this.tableload = false;};
                
              })
              .catch(() => {
                this.tableload = false;
              });
              this.loadTabledate();
              
          }
          })
         .catch(() => {});
    },

    async saveAll() {
      const errMap = await this.$refs.tableOne
        .fullValidate()
        .catch((errMap) => errMap);
      if (errMap) {
      } else {
        this.tableload = true;
        saveTj(this.$refs.tableOne.getRecordset())
          .then((response) => {
            this.$message.success(response.data.data);
            this.tableload = false;
            this.getData();
          })
          .catch(() => {
            this.tableload = false;
          });
      }
    },

    getPrint() {
      if (this.formObj.dateNow == null) {
        this.$message.error("请选择月份");
        return;
      }
      this.tableload = true;
      window.open ("http://10.188.6.228:5006/ureport/preview?_t=1,6&_u=oss_bcmcHzWeekTj.ureport.xml&dateNow="+this.formObj.dateNow, "newwindow", "height=800, width=900, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no") 
      this.tableload = false;
    },
   disabledDateMethod ({ date }) { 
     const dd = moment(date).format('dddd'); 
     if(dd!='Monday'){
        return true; 
     }
  
   },
   reloadYs(){
this.mergeCells=[           
                { row: 0, col: 0, rowspan: 2, colspan: 2 },
                { row: 2, col: 0, rowspan: 0, colspan: 3 },
                { row: 3, col: 0, rowspan: 5, colspan: 0 },
                { row: 3, col: 1, rowspan: 0, colspan: 2 },
                { row: 4, col: 1, rowspan: 0, colspan: 2 },
                { row: 5, col: 1, rowspan: 0, colspan: 2 },
                { row: 6, col: 1, rowspan: 0, colspan: 2 },
                { row: 7, col: 1, rowspan: 0, colspan: 2 },
                { row: 8, col: 0, rowspan: 0, colspan: 3 },
                { row: 9, col: 0, rowspan: 2, colspan: 0 },
                { row: 9, col: 1, rowspan: 0, colspan: 2 },
                { row: 10, col: 1, rowspan: 0, colspan: 2 },
                { row: 11, col: 0, rowspan: 2, colspan: 2 },
                { row: 13, col: 0, rowspan: 4, colspan: 0 },
                { row: 13, col: 2, rowspan: 2, colspan: 0 },
                { row: 15, col: 2, rowspan: 2, colspan: 0 },
                
              ]
   },
  },
};
</script>
<style lang="scss" scoped>
.statistical-tkindcargo-tKindCargoTjByDep {
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
  font-size: 22px;
  margin: 12px auto 0;
}
.dw {
  font-size: 12px;
  line-height:15px;
}
.vxe-toolbar{
  text-align: right;
}
 
}
</style>