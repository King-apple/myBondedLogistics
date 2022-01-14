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
    <avue-form ref="form"  :option="option" v-model="obj" @submit="handleSubmit">
      <template slot="menuForm">
        <el-button type="primary" icon="el-icon-close" plain @click="closeWindows">取消</el-button>
      </template>
    </avue-form>
  </div>
</template>

<script>
import request from "@/router/axios";
import '@/styles/common/edit.scss'
export function getSortNo(yardNo) {
  return request({
    url: "/roadapt/baseCod/getYardDortNo",
    method: "post",
    params:{"yardNo":yardNo}
  });
}
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
            type: "input",
            label: "货主",
            prop: "consignNam",
            readonly:true
          },
          {
            type: "input",
            label: "货名",
            prop: "cargoName",
            readonly:true
          },
          {
            label: "件数",
            prop: "num",
            type: "input",
            readonly:true
          },
          {
            label: "重量",
            prop: "weight",
            type: "input",
            readonly:true
          },
          {
            label: "车号",
            prop: "trkTrkno",
            type: "input",
            readonly:true
          },
          {
            label: "场地",
            prop: "yardName",
            type: "select",
            props: {
              label: "codeName",
              value: "codeIds"
            },
            remote: true,
            dicUrl: `/basecode/basecod/yard/{{key}}`, 
            typeformat(item, label, value) {
              return `${item["codeName"]}`;
            },
            change: ({ value, column }) => {
              var values = value.split(",");
              this.obj.yardNo = values[0];
              this.obj.yardNam = values[1];
              this.handleGetSortNo(values[0]);
                console.log(value);
            },
            rules: [
              {
                required: true,
                message: "请选择场地",
                trigger: "blur"
              }
            ]
          },
          {
            label: "工艺",
            prop: "techName",
            type: "select",
            props: {
              label: "codeName",
              value: "codeIds"
            },
            remote: true,
             dicUrl: `/basecode/basecod/process/{{key}}`,
            typeformat(item, label, value) {
              return `${item["codeName"]}`;
            },
            change: ({ value, column }) => {
              var values = value.split(",");
              this.obj.techNo = values[0];
              this.obj.techNam = values[1]; 
               console.log(value);
            },
            rules: [
              {
                required: true,
                message: "请选择工艺",
                trigger: "blur"
              }
            ]
          },

          {
            label: "目前排号",
            prop: "yardSortNoF",
            type: "input" 
          },
          {
            label: "场地排号",
            prop: "yardSortNo",
            type: "input" 
          }
        ]
      }
    };
  },
  mounted: function() {
    this.obj = this.objrow; 
    this.obj.yardName=this.obj.yardNam;
    this.obj.techName=this.obj.techNam;
  },
  methods: {
    handleSubmit: function(obj) {
      return request({
         url: '/roadapt/etworkcarsort',
         method: 'put',
         data: obj
      }).then(data => {
          this.$message.success("修改成功");
          this.$refs.form.resetForm();
          this.$emit("dictItemVisible");
         
        })
       ;
    },
     handleGetSortNo: function(obj) { 
        getSortNo(obj)
          .then(data => {
            console.log(data.data.data.codeIds);
            var values = data.data.data.codeIds.split(",");
            this.obj.yardSortNoF = values[0];
            this.obj.yardSortNo = values[1];
          })
          .catch(() => {});
      
    },
    closeWindows: function() {   
     this.$refs.form.resetForm();
        this.$emit("dictItemVisible");
    }
  }
};
</script>

<style lang="scss" scoped>
</style>

