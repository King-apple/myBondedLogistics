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
      <el-row type="flex"  justify="center">
       <vxe-form :data="formObj" size="mini">
            <vxe-form-item
              title="选择班组："
              field="classNam"
              size="mini"
              span="10"
            >
              <EditDown
                :objs="formObj"
                :property="downTableOption.subNamname"
                :tableColumns="downTableOption.subNamColum"
                :urlapi="downTableOption.subNamurl"
                :returnApi="downTableOption.subNamreturnApi"
                sizenow="mini"
                :width="300"
              ></EditDown>
            </vxe-form-item>
            <vxe-form-item span="14" size="mini" align="center">
              <vxe-button
                status="primary"
                icon="fa fa-search"
                @click="getDate()"
                >查询</vxe-button
              >
              <vxe-button status="primary" icon="fa fa-minus" @click="del()"
                >删除</vxe-button
              >
              <vxe-button status="primary" icon="fa fa-plus" @click="add()"
                >增加</vxe-button
              >           
            
              <vxe-button status="primary" icon="fa fa-reply" @click="back()"
                >返回</vxe-button
              >
            </vxe-form-item>
          </vxe-form>
      </el-row>
      <el-row :gutter="5">
        <el-col :span="6">
          <span style="color: red; font-size: 6px">工人配工情况</span>
          <vxe-table
            border
            show-overflow
            keep-source
            highlight-current-row
            size="mini"
            :data="tableDataSend"
            :loading="tableloadSend"
            :round="true"
            align="center"
            height="150px"
            ref="tableSend"
          >
            <vxe-table-column
              field="subunitCod"
              title="装卸队别"
              width="35%"
            ></vxe-table-column>
            <vxe-table-column
              field="subunitNam"
              title="装卸名称"
              width="45%"
            ></vxe-table-column>
            <vxe-table-column
              field="manNum"
              title="人数"
              width="20%"
            ></vxe-table-column>
          </vxe-table>
          <vxe-table
            border
            show-overflow
            keep-source
            show-footer
            highlight-current-row
            size="mini"
            :data="tableDataClassNum"
            :loading="tableloadClassNum"
            :footer-method="footerMethod"
            :round="true"
            align="center"
            height="350px"
            ref="tableClassNum"
            @cell-click="rowclick"
          >
            <vxe-table-column
              field="classCod"
              title="班别"
              width="20%"
            ></vxe-table-column>
            <vxe-table-column
              field="classNam"
              title="班组名称"
              width="35%"
            ></vxe-table-column>
            <vxe-table-column
              field="manNum"
              title="人数"
              width="20%"
            ></vxe-table-column>
            <vxe-table-column
              field="weightWgt"
              title="理货吨数"
              width="25%"
            ></vxe-table-column>
          </vxe-table>
        </el-col>

        <el-col :span="10">
          
          <span style="color: red; font-size: 6px">出工</span>
          <vxe-table
            border
            show-overflow
            keep-source
            highlight-current-row
            size="mini"
            :data="tableDataClass"
            :loading="tableloadClass"
            :round="true"
            align="center"
            height="500px"
            ref="tableClass"
          >
            <vxe-table-column
              field="workerCod"
              title="工号"
              width="17%"
            ></vxe-table-column>
            <vxe-table-column
              field="trainCod"
              title="车皮号"
              width="15%"
            ></vxe-table-column>
            <vxe-table-column
              field="manNam"
              title="姓名"
              width="13%"
            ></vxe-table-column>
            <vxe-table-column
              field="weightWgt"
              title="理货吨数"
              width="15%"
            ></vxe-table-column>
            <vxe-table-column
              field="beginTim"
              title="开工时间"
              width="17%"
            ></vxe-table-column>
            <vxe-table-column
              field="endTim"
              title="完工时间"
              width="17%"
            ></vxe-table-column>
            <vxe-table-column type="checkbox" width="6%"></vxe-table-column>
          </vxe-table>
        </el-col>
        <el-col :span="6">
           
          <span style="color: red; font-size: 6px">未出工</span>
          <vxe-table
            border
            show-overflow
            keep-source
            highlight-current-row
            size="mini"
            :data="tableDataDay"
            :loading="tableloadDay"
            :round="true"
            :edit-config="{ trigger: 'click', mode: 'cell' }"
            align="center"
            height="500px"
            ref="tableDay"
          >
            <vxe-table-column type="checkbox" width="15%"></vxe-table-column>
            <vxe-table-column
              field="workerCod"
              title="工号"
              width="33%"
            ></vxe-table-column>
            <vxe-table-column
              field="manNam"
              title="姓名"
              width="28%"
            ></vxe-table-column>
            <vxe-table-column
              field="packId"
              title="灌包"
              width="25%"
              :edit-render="{ name: '$select', options: gbList }"
            ></vxe-table-column>
          </vxe-table>
        </el-col>
        <el-col :span="2" v-if="dialogFormVisibleedit">
          
          <span style="color: red; font-size: 6px">选车皮</span>
          <vxe-table
            border
            show-overflow
            keep-source
            highlight-current-row
            highlight-hover-row
            size="mini"
            :data="tableDataTrain"
            :loading="tableloadTrain"
            :round="true"
            :radio-config="{ trigger: 'row' }"
            align="center"
            height="500px"
            ref="tableTrain"
          >
            <vxe-table-column type="radio" width="30%"></vxe-table-column>
            <vxe-table-column field="trainCod" title="车皮号" width="70%">
            </vxe-table-column>
          </vxe-table>
        </el-col>
      </el-row>
    </basic-container>
  </div>
</template>

<script>
import {
  getWorkSend,
  getWorkClass,
  getClassNum,
  getDay,
  getTrain,
  addgr,
  delgr,
  goback,
} from "@/api/dispatch/sendworkman";
import { fetchList } from "@/api/dispatch/dayrecord";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import EditDown from "../../baseUtile/EditDown";
import moment from "moment";
import XEUtils from "xe-utils";
export default {
  props: ["datanow"],
  components: { EditDown },
  name: "pg",
  data() {
    return {
      formObj: {
        classCod: null,
        classNam: null,
      },
      dialogFormVisibleedit: false,
      tableDataSend: [],
      tableloadSend: false,
      tableDataClassNum: [],
      tableloadClassNum: false,
      tableDataClass: [],
      tableloadClass: false,
      tableDataDay: [],
      tableloadDay: false,
      tableDataTrain: [],
      tableloadTrain: false,
      dataForm: {},
      dataSendNow: {},
      wgt: 0,
      nums: 0,
      num: 0,
      downTableOption: {
        //队别下拉菜单展示项
        subNamColum: [
          { field: "classCod", title: "代码", width: "30%" },
          { field: "classNam", title: "名称", width: "70%" },
        ],
        //下拉菜单的路径
        subNamurl: "",
        subNamname: "classNam",
        subNamreturnApi: [
          { field: "classCod", returnName: "classCod" },
          { field: "classNam", returnName: "classNam" },
        ],
      },
      gbList: [
        { label: "", value: "" },
        { label: "是", value: "1" },
        { label: "否", value: "0" },
      ],
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  created() {
    this.dataForm = this.datanow;
    this.downTableOption.subNamurl =
      "/dispatch/sendworkman/listbz?subcod=" + this.dataForm.subunitCod;
    if (this.dataForm.trainNo != null && this.dataForm.trainNo > 0) {
      this.dialogFormVisibleedit = true;
    }
    this.getSend();
    this.getNum();
    this.getTrainCod();
  },
  methods: {
    getDate() {
      this.getClass();
      this.getDay();
    },
    getSend() {
      this.tableloadSend = true;
      getWorkSend(
        Object.assign({
          workbillNo: this.dataForm.workbillNo,
          lineNo: this.dataForm.lineNo,
          subunitCod: this.dataForm.subunitCod,
        })
      )
        .then((response) => {
          this.tableDataSend = response.data.data;
          this.nums = response.data.data[0].manNum;
          this.tableloadSend = false;
        })
        .catch(() => {
          this.tableloadSend = false;
        });
    },
    getClass() {
      this.tableloadClass = true;
      getWorkClass(
        Object.assign({
          workbillNo: this.dataForm.workbillNo,
          lineNo: this.dataForm.lineNo,
          subunitCod: this.dataForm.subunitCod,
          classCod: this.formObj.classCod,
        })
      )
        .then((response) => {
          this.tableDataClass = response.data.data;
          this.tableloadClass = false;
        })
        .catch(() => {
          this.tableloadClass = false;
        });
    },
    getDay() {
      this.tableloadDay = true;
      fetchList(
        Object.assign({
          markId: 1,
          crtTime: this.dataForm.crtTime,
          classId: this.dataForm.classId,
          subunitCod: this.dataForm.subunitCod,
          classCod: this.formObj.classCod,
        })
      )
        .then((response) => {
          this.tableDataDay = response.data.data;
          this.tableloadDay = false;
        })
        .catch(() => {
          this.tableloadDay = false;
        });
    },
    getNum() {
      this.tableloadClassNum = true;
      getClassNum(
        Object.assign({
          workbillNo: this.dataForm.workbillNo,
          lineNo: this.dataForm.lineNo,
          subunitCod: this.dataForm.subunitCod,
        })
      )
        .then((response) => {
          this.tableDataClassNum = response.data.data;
          this.tableloadClassNum = false;
        })
        .catch(() => {
          this.tableloadClassNum = false;
        });
    },
    getTrainCod() {
      this.tableloadTrain = true;
      getTrain(
        Object.assign({
          workbillNo: this.dataForm.workbillNo,
          lineNo: this.dataForm.lineNo,
        })
      )
        .then((response) => {
          this.tableDataTrain = response.data.data;
          this.tableloadTrain = false;
        })
        .catch(() => {
          this.tableloadTrain = false;
        });
    },
    add() {
      if (this.wgt > 0) {
        this.$message.error("已经作业不能安排工人");
        return;
      }
      let selectRecords = this.$refs.tableDay.getCheckboxRecords();
      let selectTrain = null;
      if (selectRecords.length == 0) {
        this.$message.error("请选择工人");
        return;
      }
      if (this.dataForm.trainNo != null && this.dataForm.trainNo > 0) {
        selectTrain = this.$refs.tableTrain.getRadioRecord();
        /*if (selectTrain == null) {
          this.$message.error("请选择车皮");
          return;
        }*/
      }
      this.tableloadClass = true;
      addgr(this.dataForm, selectRecords, selectTrain)
        .then((data) => {
          this.$message.success(data.data.data);
          this.tableloadClass = false;
          this.getNum();
          this.getClass();
          this.getDay();
        })
        .catch(() => { this.tableloadClass = false;});
    },
    del() {
      if (this.wgt > 0) {
        this.$message.error("已经作业不能删除工人");
        return;
      }
      let selectRecords = this.$refs.tableClass.getCheckboxRecords();
      if (selectRecords.length == 0) {
        this.$message.error("请选择配工工人");
        return;
      }

      this.tableloadClass = true;
      delgr(selectRecords)
        .then((data) => {
          this.$message.success(data.data.data);
          this.tableloadClass = false;
          this.getNum();
          this.getClass();
          this.getDay();
        })
        .catch(() => { this.tableloadClass = false;});
    },
    back() {
      if (this.num != this.nums) {
        this.$message.error("安排工人人数与一级配工不等");
        return;
      }
      this.tableloadClass = true;
      goback(
        Object.assign({
          workbillNo: this.dataForm.workbillNo,
          lineNo: this.dataForm.lineNo,
        })
      )
        .then((data) => {
          this.tableloadClass = false;
          this.$emit("dictItemVisible");
        })
        .catch(() => { this.tableloadClass = false;});
    },
    rowclick({ row }) {
      this.dataSendNow = row;
      this.formObj.classCod = this.dataSendNow.classCod;
      this.formObj.classNam = this.dataSendNow.classNam;
      this.getClass();
      this.getDay();
    },
    footerMethod({ columns, data }) {
      const footerData = [
        columns.map((column, _columnIndex) => {
          if (_columnIndex === 1) {
            return "合计：";
          }
          if (["manNum"].includes(column.property)) {
            this.num = XEUtils.sum(data, column.property);
            return XEUtils.sum(data, column.property);
          }
          if (["weightWgt"].includes(column.property)) {
            this.wgt = XEUtils.sum(data, column.property);
            return XEUtils.sum(data, column.property);
          }
          return null;
        }),
      ];
      return footerData;
    },
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