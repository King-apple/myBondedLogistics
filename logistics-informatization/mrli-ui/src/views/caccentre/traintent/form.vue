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
      <template slot="menuForm">
        <el-button type="primary" icon="el-icon-close" plain @click="closeWindows">取消</el-button>
      </template>
    </avue-form>
  </div>
</template>

<script>
import request from "@/router/axios";
import "@/styles/common/edit.scss";
import moment from "moment";
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
            label: "股位",
            prop: "trackName",
            type: "select",
            props: {
              label: "codeName",
              value: "codeName"
            },
            remote: true,
            dicUrl: `/basecode/basecod/track/{{key}}`,
            typeformat(item, label, value) {
              return `${item["codeName"]}`;
            },
            change: ({ value, column }) => {
              if (value != null) {
                var values = value.split("-");
                if (values.length > 1) {
                  this.obj.trackCod = values[0];
                  this.obj.trackNam = values[1];
                } else {
                }
              }
            },
            rules: [
              {
                required: true,
                message: "请选择股位",
                trigger: "blur"
              }
            ]
          },
          {
            type: "input",
            label: "车数",
            prop: "trainNum"
          },
          {
            type: "datetime",
            label: "到港时间",
            prop: "portTim",
            format: "yyyy-MM-dd hh:mm:ss"
          },
          {
            type: "input",
            label: "车皮号",
            prop: "trainCod"
          },
          {
            label: "货名",
            prop: "cargoName",
            type: "select",
            props: {
              label: "codeName",
              value: "codeIds"
            },
            remote: true,
            dicUrl:
              `/caccentre/traintent/trainplan?key={{key}}&classId=` +
              this.objrow.classId,
            typeformat(item, label, value) {
              return `${item["codeName"]}`;
            },
            change: ({ value, column }) => {
              if (value != null) {
                var values = value.split("-");
                if (values.length > 1) {
                  this.obj.planNo = values[0];
                  this.obj.cargoNam = values[1];
                  this.obj.cargoNamCod = values[2];
                  this.obj.teleNo = values[3];
                  this.obj.sanothNam = values[4];
                  this.obj.stationNam = values[5];
                  this.obj.ownerCod = values[6];
                  this.obj.ownerNam = values[7];
                  this.obj.shipperCod = values[8];
                  this.obj.shipperDoc = values[9];
                  this.obj.consignCod = values[10];
                  this.obj.consignNam = values[11];
                  this.obj.cargoKindCod = values[12];
                  this.obj.cargoKindNam = values[13];
                  this.obj.format = values[14];
                  this.obj.pkgKindCod = values[15];
                  this.obj.cPkgKindNam = String(values[16]);
                  this.obj.subunitCod = values[17];
                  this.obj.subunitNam = values[18];
                  this.obj.cargoNo = values[19];
                } else {
                }
              }
            },
            rules: [
              {
                required: true,
                message: "请选择股位",
                trigger: "blur"
              }
            ]
          },
          {
            type: "input",
            label: "原发件数",
            prop: "oldPiecesNum"
          },
          {
            type: "input",
            label: "原发吨数",
            prop: "oldWeightWgt"
          },
          {
            type: "input",
            label: "站名",
            prop: "stationNam",
            readonly: true
          },
          {
            type: "input",
            label: "计划号",
            prop: "planNo",
            readonly: true
          },
          {
            type: "input",
            label: "港口货主",
            prop: "shipperDoc",
            readonly: true
          },
          {
            type: "input",
            label: "代理货主",
            prop: "consignNam",
            readonly: true
          },
          {
            type: "input",
            label: "包装",
            prop: "cPkgKindNam",
            readonly: true
          },
          {
            type: "input",
            label: "规格型号",
            prop: "format",
            readonly: true
          },
          {
            type: "input",
            label: "收发货人",
            prop: "ownerNam",
            readonly: true
          },
          {
            type: "input",
            label: "箱号1",
            prop: "containerNo1"
          },
          {
            type: "input",
            label: "箱号2",
            prop: "containerNo2"
          },
          {
            type: "input",
            label: "备注",
            prop: "remarkTxt"
          }
        ]
      }
    };
  },
  mounted: function() {
    console.log(this.objrow);
    this.obj = this.objrow;
    this.obj.trackName = this.objrow.trackNam;
    this.obj.cargoName = this.objrow.cargoNam;
    this.obj.oldTrainCod = this.objrow.trainCod;
    this.obj.portTim = moment(new Date()).format("YYYY-MM-DD HH:mm:ss");
    console.log(this.obj);
  },
  methods: {
    handleSubmit: function(obj, done) {
      if (this.objrow.xgid == "1") {
        return request({
          url: "/caccentre/traintent",
          method: "put",
          data: this.obj
        }).then(data => {
          console.log(data);
          this.$message.success(data.data.data);
          this.$refs.form.resetForm();
          this.$emit("dictItemVisible");
        });
      } else {
        return request({
          url: "/caccentre/traintent",
          method: "post",
          data: this.obj
        }).then(data => {
          console.log(data);
          this.$message.success(data.data.data);
          this.$refs.form.resetForm();
          this.$emit("dictItemVisible");
        });
      }
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

