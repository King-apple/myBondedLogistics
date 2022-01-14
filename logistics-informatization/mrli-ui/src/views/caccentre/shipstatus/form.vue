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
    <avue-form ref="form" :option="option" v-model="obj" @submit="handleSubmit">
      <template slot-scope="scope" slot="menuForm">
        <el-button type="primary" icon="el-icon-close" plain @click="closeWindows">取消</el-button>
      </template>
    </avue-form>
  </div>
</template>

<script>
import request from '@/router/axios'
import moment from "moment";
import "@/styles/common/edit.scss";
export default {
  props: ["objrow"],
  data() {
    return {
      obj: {},
      option: {
        labelWidth: 100,
        emptyBtn: false,
        column: [
             {
            label: "序号",
            prop: "seqNo",
            type: "input"
          },
            {
            label: "船号",
            prop: "shipNo",
            type: "input"
          },
          {
            label: "船名",
            prop: "shipNam",
            type: "input"
          },
          //  {
          //   label: "开始时间",
          //   prop: "beginTim",
          //   type: "datetime",
          // },  
          // {
          //   label: "结束时间",
          //   prop: "endTim",
          //   type: "datetime",
          // },
          {
            label: "动态",
            prop: "status",
            type: "select",
            props: {
              label: "codeName",
              value: "codeName"
            },
            typeslot: true,
            remote: true,
            dicUrl: `/basecode/basecod/shipstatus/{{key}}`,
            typeformat(item, label, value) {
              return `${item["codeName"]}`;
            },
            change: ({ value, column }) => {
              if(value != null) {
              var values = value.split("/");
               if (values.length > 1) {
              this.obj.statCod = values[0];
              this.obj.statNam = values[1];
 console.log("ddddddddddddddddddddd::::::::::"+values[1]+"this.obj.statNam::::"+this.obj.statNam);

              }else{

              }
              } 
            },
            rules: [
              {
                required: true,
                message: "请选择动态",
                trigger: "blur"
              }
            ]
          },
 {
            label: "动态代码",
            prop: "statCod",
            type: "input",
            hide: true
          },
          {
            label: "动态名称",
            prop: "statNam",
            type: "input",
            hide: true
          }

        
        ]
      }
    };
  },

  mounted: function() {
  this.obj = this.objrow;
  console.log(this.objrow);
  this.obj.seqNo=this.objrow.seqNo+1;
this.obj.statCod='';
this.obj.status='';
   console.log(this.obj);
  },
     
 methods: {

     handleSubmit: function(obj) {

          console.log(JSON.stringify(this.obj));
      return request({
         url: '/caccentre/shipstatus',
         method: 'post',
         data: this.obj
      }).then(data => {        
          this.$message.success("保存成功");
          this.$refs.form.resetForm();
          this.$emit("dictItemVisible");
         
        })
       ;
    },


    closeWindows: function() {
      this.$emit("dictItemVisible");
    },


  }
  
};
</script>


