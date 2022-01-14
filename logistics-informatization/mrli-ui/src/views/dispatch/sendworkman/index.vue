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
        <vxe-form-item title="作业日期：" field="crtTime" span="5">
          <vxe-input
            v-model="formObj.crtTime"
            type="date"
            placeholder="请选择日期"
            clearable
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="班次：" field="classId" size="mini" span="5">
          <vxe-select v-model="formObj.classId" clearable size="mini">
            <vxe-option value="1" label="1班次0800-2000"></vxe-option>
            <vxe-option value="2" label="2班次2000-0800"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item title="队别：" field="subunitNam" size="mini" span="5">
          <EditDown
            :objs="formObj"
            :property="downTableOption.subNamname"
            :tableColumns="downTableOption.subNamColum"
            :urlapi="downTableOption.subNamurl"
            :returnApi="downTableOption.subNamreturnApi"
            sizenow="mini"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item title="作业类型：" field="workType" size="mini" span="5">
          <vxe-select v-model="formObj.workType" clearable size="mini">
            <vxe-option value="0" label="船舶职业"></vxe-option>
            <vxe-option value="1" label="火车作业"></vxe-option>
            <vxe-option value="2" label="汽车作业"></vxe-option>
            <vxe-option value="3" label="直取作业"></vxe-option>
            <vxe-option value="4" label="灌包作业"></vxe-option>
            <vxe-option value="5" label="转栈作业"></vxe-option>
            <vxe-option value="9" label="杂项作业"></vxe-option>
            <vxe-option value="8" label="外单位杂作业"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="center">
          <vxe-button status="primary" icon="fa fa-search" @click="getDate()"
            >查询</vxe-button
          >
        </vxe-form-item>
      </vxe-form>

      <el-row>
        <el-col :span="24">
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
            height="250px"
            ref="tableNam"
            @scroll="hscroll"
            @cell-click="rowclick"
            @cell-mouseenter="changeFlag"
          >
            <vxe-table-column
              field="workbillNo"
              title="工票号"
              width="10%"
            ></vxe-table-column>
            <vxe-table-column
              field="lineNo"
              title="线号"
              width="5%"
            ></vxe-table-column>
            <vxe-table-column
              field="tallyUnitNam"
              title="通知单位"
              width="10%"
            ></vxe-table-column>
            <vxe-table-column
              field="berthCod"
              title="泊位"
              width="5%"
            ></vxe-table-column>
            <vxe-table-column
              field="shipNam"
              title="船名"
              width="10%"
            ></vxe-table-column>
            <vxe-table-column
              field="cabinNo"
              title="舱号"
              width="5%"
            ></vxe-table-column>
            <vxe-table-column
              field="unloadId"
              title="装卸别"
              width="5%"
            ></vxe-table-column>
            <vxe-table-column
              field="trainNo"
              title="车次号"
              width="10%"
            ></vxe-table-column>
            <vxe-table-column
              field="workPlace"
              title="作业位置"
              width="10%"
            ></vxe-table-column>
            <vxe-table-column
              field="scargoNam"
              title="劳资货名"
              width="10%"
            ></vxe-table-column>
            <vxe-table-column
              field="operProcNam"
              title="操作过程"
              width="10%"
            ></vxe-table-column>
            <vxe-table-column
              field="operWayNam"
              title="操作方法"
              width="10%"
            ></vxe-table-column>
          </vxe-table>
        </el-col>
        <el-col :span="24">
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
            height="300px"
            ref="tableNamb"
            @scroll="bscroll"
            @cell-click="browclick"
            @cell-mouseenter="bchangeFlag"
            @cell-dblclick="grap"
          >
            <vxe-table-column
              field="workTypNam"
              title="作业类型"
              width="10%"
            ></vxe-table-column>
            <vxe-table-column
              field="specWorkNam"
              title="工人特种作业"
              width="15%"
            ></vxe-table-column>
            <vxe-table-column
              field="workManTxt"
              title="调度工人安排"
              width="15%"
            ></vxe-table-column>
            <vxe-table-column
              field="director"
              title="指导员"
              width="7%"
            ></vxe-table-column>
            <vxe-table-column
              field="tallyMan"
              title="理货员"
              width="8%"
            ></vxe-table-column>
            <vxe-table-column
              field="remarkTxt"
              title="调度备注"
              width="10%"
            ></vxe-table-column>
            <vxe-table-column
              field="recordTim"
              title="调度配工时间"
              width="15%"
            ></vxe-table-column>
            <vxe-table-column
              field="manStr"
              title="具体工人安排"
              width="20%"
              show-overflow
            >
            </vxe-table-column>
          </vxe-table>
        </el-col>
      </el-row>
       <vxe-modal
        v-if="dialogFormVisibleedit"
        v-model="dialogFormVisibleedit"
        title="工人安排"
        width="1400"
        height="700"
        resize
        storage
      >
        <Edit :datanow="dataNow" @dictItemVisible="dictItemVisible"  ></Edit>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import { fetchList } from "@/api/dispatch/sendworkman";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import EditDown from "../../baseUtile/EditDown";
import Edit from "./pg";
import moment from "moment";
export default {
  components: { EditDown ,Edit},
  name: "sendworkman",
  data() {
    return {
      formObj: {
        crtTime: moment(new Date()).format("YYYY-MM-DD"),
        classId: null,
        subunitCod: null,
        subunitNam: null,
        workType: null,
      },
      dialogFormVisibleedit: false,
      flag: false,
      tableData: [],
      tableload: false,
      dataHeadNow: {},
      dataNow: {},
      downTableOption: {
        //队别下拉菜单展示项
        subNamColum: [
          { field: "SUBUNIT_COD", title: "代码" },
          { field: "SUBUNIT_NAM", title: "名称" },
        ],
        //下拉菜单的路径
        subNamurl: "/basecode/basecod/getSubNam",
        subNamname: "subunitNam",
        subNamreturnApi: [
          { field: "SUBUNIT_COD", returnName: "subunitCod" },
          { field: "SUBUNIT_NAM", returnName: "subunitNam" },
        ],
      },
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  created() {},
  methods: {
    getDate() {
      if (this.formObj.classId == null) {
        this.$message.success("请选择班次");
        return;
      }
      if (this.formObj.subunitCod == null) {
        this.$message.success("请选择队别");
        return;
      }
      this.tableload = true;
      fetchList(Object.assign(this.formObj))
        .then((response) => {
          this.tableData = response.data.data;
          console.log(this.tableData);
          this.tableload = false;
        })
        .catch(() => {
          this.tableload = false;
        });
    },
    changeFlag() {
      this.flag = false;
    },
    bchangeFlag() {
      this.flag = true;
    },
    rowclick({ row }) {
      this.dataHeadNow = row;
      this.$refs.tableNamb.setCurrentRow(row);
    },
    hscroll() {
      if (!this.flag) {
        this.dataNow = this.$refs.tableNam.getScroll();
        this.$refs.tableNamb.scrollTo(
          this.dataNow.scrollLeft,
          this.dataNow.scrollTop
        );
      }
    },
    browclick({ row }) {
      this.dataHeadNow = row;
      this.$refs.tableNam.setCurrentRow(row);
    },
    bscroll() {
      if (this.flag) {
        this.dataNow = this.$refs.tableNamb.getScroll();
        this.$refs.tableNam.scrollTo(
          this.dataNow.scrollLeft,
          this.dataNow.scrollTop
        );
      }
    },
    grap({ row }) {
      this.dataNow = row;
       this.dataNow.subunitCod = this.formObj.subunitCod;
        this.dataNow.crtTime = this.formObj.crtTime;
      this.dialogFormVisibleedit = true;
    },
     dictItemVisible() {
      this.dialogFormVisibleedit = false;
      this.getDate();
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