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
  <div class="dayrecord">
    <basic-container>
      <vxe-form :data="formObj" size="mini">
        <vxe-form-item span="24">
          <el-divider content-position="left">配载计划头信息</el-divider>
        </vxe-form-item>
        <vxe-form-item title="船号" field="shipNo" span="4"> 
          <EditDown
            :objs="formObj"
            :property="downTableOption.shipNoname"
            :tableColumns="downTableOption.shipNoColum"
            :urlapi="downTableOption.shipNourl"
            :returnApi="downTableOption.shipNoreturnApi"
            @datereturn="getBDate"
            sizenow="mini"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item title="船名" field="shipNam" span="4">
          <vxe-input v-model="formObj.shipNam" ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="舱号" field="hatchNo" span="4">
          <vxe-input v-model="formObj.hatchNo" @change="getBDate"></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="内外贸" field="tradeId" span="4">
          <vxe-select v-model="formObj.tradeId" >                  
                  <vxe-option :value="0" label="外贸"></vxe-option>
                  <vxe-option :value="1" label="内贸"></vxe-option>
                </vxe-select> 
        </vxe-form-item>
        <vxe-form-item title="装卸别" field="unloadId" span="4">
          <vxe-select v-model="formObj.unloadId" >
                  <vxe-option value="+" label="装"></vxe-option>
                  <vxe-option value="-" label="卸"></vxe-option>
                </vxe-select>           
        </vxe-form-item>
        <vxe-form-item title="备注" field="remark" span="4">
          <vxe-input v-model="formObj.remark"></vxe-input>
        </vxe-form-item>
      </vxe-form>

      <el-row>
        <el-col :span="24">
          <el-divider content-position="left">配载计划体信息</el-divider>
        </el-col>
        <el-col :span="24">
          <vxe-table
            border
            show-overflow
            resizable
            keep-source
            highlight-current-row
            size="mini"
            :data="tableDatab"
            :loading="tableloadb"
            :round="true"
            :edit-config="{ trigger: 'click', mode: 'cell' }"
            align="center"
            height="300px"
            ref="tableNamb"            
          >
          <vxe-table-column type="checkbox" width="3%"></vxe-table-column>
            <vxe-table-column
              field="subunitNam"
              title="保管单位"
              width="8%"
              show-overflow
            ></vxe-table-column>
            <vxe-table-column
              field="workPlaceNam"
              title="作业地点"
              width="8%"
              show-overflow
            ></vxe-table-column>
            <vxe-table-column
              field="billNo"
              title="提单号"
              width="7%"
            ></vxe-table-column>
            <vxe-table-column
              field="hatchNo"
              title="舱号"
              width="5%"
            ></vxe-table-column>
            <vxe-table-column
              field="cargoNam"
              title="货名"
              width="7%"
              show-overflow
            ></vxe-table-column>
            <vxe-table-column
              field="piecesNum"
              title="件数"
              width="7%"
               :edit-render="{ name: '$input', props: { type: 'int' } }"
            ></vxe-table-column>
            <vxe-table-column
              field="weightWgt"
              title="重量"
              width="8%"
              :edit-render="{name: '$input', props: { type: 'float', digits: 3 } }"
            ></vxe-table-column>
            <vxe-table-column
              field="cargoVol"
              title="体积"
              width="6%"
              :edit-render="{name: '$input', props: { type: 'float', digits: 3 } }"
            ></vxe-table-column>
            <vxe-table-column
              field="checkId"
              title="过磅否"
              width="6%"
              :formatter="formattergb"
            ></vxe-table-column>
            <vxe-table-column
              field="checkNo"
              title="衡重申请单号"
              width="7%"
            ></vxe-table-column>
          </vxe-table>
        </el-col>
      </el-row>
      <div align="center">
      <el-button type="primary" size="mini" icon="el-icon-check" @click="handleSubmit">保存</el-button>
      <el-button size="mini" icon="el-icon-close"   @click="closeWindows">取消</el-button>
    </div>
    </basic-container>
  </div>
</template>

<script>
import { save } from "@/api/sixspecial/vslworkorderhead";
import { ListJhB } from "@/api/sixspecial/vslworkorderbody";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import EditDown from "../../baseUtile/EditDown";
import moment from "moment";
export default {
  props: ["datanow"],
  components: { EditDown },
  name: "pzjh",
  data() {
    return {
      formObj: {
        shipNo: null,
        pkid:null,
        shipNam:null,
         hatchNo:null,
         tradeId:null,
         unloadId:null,
      },
      tableData: [],
      tableload: false,
      tableDatab: [],
      tableloadb: false,
      dataHeadNow: {},
       downTableOption: {
        //计划下拉菜单展示项
        shipNoColum: [
          { field: "shipNo", title: "船号" ,width:"20%"},
          { field: "shipNam", title: "船名",width:"25%" },
          { field: "hatchNo", title: "舱号" ,width:"15%"},
          { field: "tradeId", title: "内外贸" ,width:"20%"  },
          { field: "unloadId", title: "装卸别" ,width:"20%" },
        ],
        //下拉菜单的路径
        shipNourl: "/sixspecial/vslworkorderhead/getJhList",
        shipNoname: "shipNo",
        shipNoreturnApi: [
          { field: "shipNo", returnName: "shipNo" },
          { field: "shipNam", returnName: "shipNam" },
          { field: "hatchNo", returnName: "hatchNo" },
          { field: "tradeId", returnName: "tradeId" },
          { field: "unloadId", returnName: "unloadId" } 
        ],
      },
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  created() {
    
    console.log(this.datanow);
    if(this.datanow.hasOwnProperty("pkid")){     
      this.formObj=this.datanow;
      this.getBDate();
    }else{
      this.formObj.classId=this.datanow.classId;
      this.formObj.crtTime=this.datanow.crtTime;
    }
  },
  methods: {
    getBDate() {
      this.tableloadb = true;
      ListJhB(Object.assign({ shipNo: this.formObj.shipNo ,hatchNo:this.formObj.hatchNo}))
        .then((response) => {
          this.tableDatab = response.data.data;
          this.tableloadb = false;
        })
        .catch(() => {
          this.tableloadb = false;
        });
    },
     
    formattergb({ cellValue }) {
      if (cellValue == "0") {
        return "否";
      } else if (cellValue == "1") {
        return "是";
      }
    },
    handleSubmit() {
      if(this.formObj.hatchNo==""||this.formObj.hatchNo==null||this.formObj.hatchNo=="undefined "){
         this.$message.error("请输入舱号");
          return;
      }
      let selectRecords = this.$refs.tableNamb.getCheckboxRecords()      
      save(         
          this.formObj,selectRecords
         
      )
        .then(response => {
          this.$message.success(response.data.data); 
          this.$emit("dictItemVisible");
        })
        .catch(() => {});
    },
    closeWindows() {
      this.$emit("dictItemVisible");
    }
  },
};
</script>
<style lang="scss" scoped>
.dayrecord {
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