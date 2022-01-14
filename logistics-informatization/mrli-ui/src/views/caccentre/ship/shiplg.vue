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
  <div class="execution">
    <avue-form :option="option" v-model="obj" @submit="handleSubmit">
      

      <template slot-scope="scope" slot="menuForm">
        <el-button type="primary" icon="el-icon-close" plain @click="closeWindows">取消</el-button>
      </template>
    </avue-form>
  </div>
</template>

<script>
 import {
   shipLg}from "@/api/caccentre/shipzg";
import request from "@/router/axios";
import "@/styles/common/edit.scss";
import moment from "moment";
export default {
  props: ["objrowlg","objrow"],
  data() {
    return {
      obj: {},
      objrowship:this.objrow,
      option: {
        labelWidth: 100,
        emptyBtn: false,

        column: [
          {
            type: "input",
            label: "开始时间",
            span: 12,
            prop: "scrLevBeginTim",//SCR_LEV_BEGIN_TIM
            readonly:true,
            placeholder:" ",
          },
          {
            type: "datetime",
            label: "离港开始时间",
            span: 12,
            prop: "scrLeaveTime",//scr_leave_time
             "format": "yyyy-MM-dd HH:mm:ss",
            "value-format": "yyyy-MM-dd HH:mm:ss",
            "addDisplay": false,
            "editDisplay": false,
            blur: () => {
              if(this.obj.scrLeaveTime!=null){

                if(moment(new Date())<moment(this.obj.scrLeaveTime)){
                  this.obj.scrLeaveTime=null;
                this.$alert('离港时间应该小于当前时间！', '错误');
                }
                if(moment(this.objrowship.toBerthTim)>moment(this.obj.scrLeaveTime)){
                  console.log(this.objrowship.toBerthTim);
                  this.obj.scrLeaveTime=null;
                this.$alert('离港时间应该大于靠泊时间'+this.objrowship.toBerthTim+'！', '错误');
                  if(moment(this.objrowship.toPortTim)>moment(this.obj.scrLeaveTime)){
                    console.log(this.objrowship.toPortTim);
                    this.obj.scrLeaveTime=null;
                  this.$alert('离港时间应该大于到港时间'+this.objrowship.toPortTim+'！', '错误');
                  }
                }
              }
            }
          },
         
          {
            type: "input",
            label: "结束时间",
            span: 12,
            prop: "scrLevEndTim",//SCR_LEV_END_TIM
            readonly:true,
            placeholder:" ",
          },
         
          {
            type: "input",
            label: "离港首吃水",
            span: 12,
            prop: "scrLevHeadWat"//SCR_LEV_HEAD_WAT
          },
          {
            type: "input",
            label: "状态",
            span: 12,
            prop: "scrLevStatus",//SCR_LEV_STATUS
            readonly:true,
            placeholder:" ",
          },
          {
            type: "input",
            label: "离港艉吃水",
            span: 12,
            prop: "ScrLevTailWat",//SCR_LEV_TAIL_WAT
            
          },
          {
            type: "input",
            label: "隐藏时间",
            span: 12,
            prop: "ScrLevTimEnd",//SCR_LEV_TIM_END
            "display":false,
          },
          
        ]
      }
    };
  },
  mounted: function() {
    this.obj = this.objrowlg;
    console.log(this.objrowship);
    
  },
  methods: {
    handleSubmit: function(obj,done) {
      
       console.log("123:::"+this.objrowship.statusId);
      
           if(obj.scrLeaveTime===""||obj.scrLeaveTime === null||String(obj.scrLeaveTime).trim()==""){
            this.$alert('离港时间不能为空!', '错误');
            done();
	        	return;
	        }
          

      shipLg(obj,this.objrowship)
        .then(data => {
          console.log("33333:::");
          this.$message.success("离港成功");
          //done();
          //this.getList(this.page);
          this.$emit("getList");
          this.$emit("dictItemVisible");
        })
        .catch(() => {
          // loading();
        });
    },
    closeWindows: function() {
      this.$emit("dictItemVisible");
    }
  }

  
};
export function putObj(obj) {
  return request({
    url: "/caccentre/ship",
    method: "put",
    data: obj
  });
}
</script>
<style >
</style>