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
      <template slot="provinceType" slot-scope="{item,value,label}">
        <span>{{item.codeName}}</span>
      </template>
      <template slot="codeType" slot-scope="{item,value,label}">
        <span></span>
      </template>
      <template slot="codesType" slot-scope="{item,value,label}">
        <span></span>
      </template>
      <template slot-scope="scope" slot="menuForm">
        <el-button type="primary" icon="el-icon-close" plain @click="closeWindows">取消</el-button>
      </template>
    </avue-form>
  </div>
</template>

<script>
import request from "@/router/axios";
export function putObj(obj) {
  return request({
    url: "/roadapt/etworkappointmentbody/",
    method: "post",
    data: obj
  });
}
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
          // {
          //   label: '库场代码',
          //   prop: 'yardNo',
          //   type: 'input'
          // },{
          //   label: '库场名称',
          //   prop: 'yardName',
          //   type: 'input'
          // },{
          //   label: '库场大类',
          //   prop: 'yardType',
          //   type: 'input'
          // },{
          //   label: '库场大类代码',
          //   prop: 'yardTypeCode',
          //   type: 'input'
          // },
          {
            label: "托运人单位",
            prop: "carCompanyName",
            type: "input"
          },
          {
            label: "货物名称",
            prop: "cargoName",
            type: "input"
          },
          {
            label: "件数",
            prop: "num",
            type: "input"
          },
          {
            label: "重量",
            prop: "weight",
            type: "input"
          },
          {
            label: "车号",
            prop: "trkTrkno",
            type: "input"
          },
          {
            label: "场地",
            prop: "yardNam",
            type: "select",
            props: {
              label: "codeName",
              value: "codeIds"
            },
            typeslot: true,
            remote: true,
            dicUrl: `/basecode/basecod/yard/{{key}}`,
            // dicUrl: `/roadapt/baseCod/list`,
            typeformat(item, label, value) {
              return `${item["codeName"]}`;
            },
            change: ({ value, column }) => {
              var values = value.split(",");
              this.obj.yardNo = values[0];
              this.obj.yardName = values[1];
              this.handleGetSortNo(values[0]);
              console.log(value);
              // this.obj.yardType=values[3];
              // this.obj.yardTypeCode=values[2];
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
            prop: "techNam",
            type: "select",
            props: {
              label: "codeName",
              value: "codeIds"
            },
            // typeslot: true,
            remote: true,
            dicUrl: `/basecode/basecod/process/{{key}}`,
            typeformat(item, label, value) {
              return `${item["codeName"]}`;
            },
            change: ({ value, column }) => {
              var values = value.split(",");
              this.obj.techNo = values[0];
              this.obj.techName = values[1];

              console.log(value);
              // this.obj.yardType=values[3];
              // this.obj.yardTypeCode=values[2];
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
            label: "工艺代码",
            prop: "techNo",
            type: "input",
            display: false
          },
          // {
          //   label: "工艺",
          //   prop: "techNam",
          //   type: "input"
          // },
          {
            label: "路数",
            prop: "lineNo",
            type: "input"
          },
          {
            label: "目前排号",
            prop: "yardDortNoF",
            type: "input"
          },
          {
            label: "场地排号",
            prop: "yardDortNo",
            type: "input"
          }
        ]
      }
    };
  },
  mounted: function() {
    this.obj = this.objrow;
    console.log(this.objrow);
  },
  methods: {
    handleSubmit: function(obj, done) {
      // return request({
      //   url: "/roadapt/etworkappointmentbody/",
      //   method: "post",
      //   data: obj
      // });
      // console.log(this.objrow);
      // console.log(JSON.stringify(this.objrow));
      putObj(obj)
        .then(data => {
          this.$message.success("预约成功");
          done();
          // this.getList(this.page);
          this.$emit("getList");
          this.$emit("dictItemVisible");
        })
        .catch(() => {
          // loading();
        });
    },
    handleGetSortNo: function(obj) {
      // if (yardNo != null && yardNo != ".") {
        getSortNo(obj)
          .then(data => {
            console.log(data.data.data.codeIds);
            var values = data.data.data.codeIds.split(",");
            this.obj.yardDortNoF = values[0];
            this.obj.yardDortNo = values[1];
          })
          .catch(() => {});
      // }
    },
    closeWindows: function() {
      this.$emit("dictItemVisible");
    }
  }
};
</script>

<style lang="scss" scoped>
</style>

