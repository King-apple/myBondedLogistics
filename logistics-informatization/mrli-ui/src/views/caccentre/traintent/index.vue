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
    <basic-container>
      <el-form :inline="true" :model="searchForm" class="demo-form-inline">
        <el-form-item label="作业日期">
          <el-date-picker
            v-model="searchForm.datePlan"
            type="date"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
            placeholder="选择日期"
            @change="datechange($event)"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="公司">
          <el-select
            v-model="searchForm.unitNam"
            filterable
            clearable
            remote
            :remote-method="remoteUnit"
            @change="unitchange($event)"
            placeholder="请选择"
          >
            <el-option
              v-for="item in units"
              :key="item.unitCod"
              :label="item.unitNam"
              :value="item"
            >
              <span style="float: left">{{ item.unitCod }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{
                item.unitNam
              }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="车次号">
          <el-select
            v-model="searchForm.trainNo"
            filterable
            clearable
            remote
            :remote-method="remoteMethod"
            placeholder="请选择"
          >
            <el-option
              v-for="item in cities"
              :key="item.trainNo"
              :label="item.trainNo"
              :value="item.trainNo"
            >
              <span style="float: left">{{ item.unitNam }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{
                item.trainNo
              }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getList">查询</el-button>
          <el-button type="primary" @click="handleApt">新增</el-button>
          <el-button type="primary" @click="updateTrain">修改股位</el-button>
        </el-form-item>
      </el-form>
      <el-container>
        <avue-form ref="form" v-model="obj0" :option="option"></avue-form>
      </el-container>
      <el-container>
        <avue-crud
          ref="crud"
          :page="page"
          :data="tableData"
          :permission="permissionList"
          :table-loading="tableLoading"
          :option="tableOption"
          @on-load="getList"
          @search-change="searchChange"
          @refresh-change="refreshChange"
          @size-change="sizeChange"
          @current-change="currentChange"
          @row-update="handleUpdate"
          @row-save="handleSave"
          @row-del="rowDel"
        >
          <template slot-scope="{ row, type, size }" slot="menu">
            <el-button
              icon="el-icon-edit"
              :size="size"
              :type="type"
              @click.stop="handleApt1(row)"
              >修改</el-button
            >
          </template>
        </avue-crud>
      </el-container>
      <div v-if="dialogFormVisible">
        <el-dialog
          :visible.sync="dialogFormVisible"
          title="登记帐信息"
          width="90%"
          :show-close="false"
          @close="dictItemVisible"
        >
          <Form :objrow="this.objrow" @dictItemVisible="dictItemVisible"></Form>
        </el-dialog>
      </div>
    </basic-container>
  </div>
</template>

<script>
import {
  fetchList,
  getObj,
  addObj,
  putObj,
  delObj,
  getTrain,
  getUnit,
  getNo,
  getModel,
  updateTrain,
} from "@/api/caccentre/traintent";
import { tableOption, option } from "@/const/crud/caccentre/traintent";
import { mapGetters } from "vuex";
import moment from "moment";
import Form from "./form";
export default {
  components: {
    Form,
  },
  name: "traintent",
  data() {
    return {
      searchForm: {
        datePlan: moment(new Date()).format("YYYY-MM-DD"),
        classId: "",
        unitNam: "",
        trainNo: "",
      },
      tableData: [],
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 10, // 每页显示多少条
      },
      tableLoading: false,
      tableOption: tableOption,
      obj0: {},
      option: {
        menuBtn: false,
        submitBtn: false,
        emptyBtn: false,
        menu: false,
        border: true,
        size: "small",
        stripe: true,
        menuAlign: "center",
        align: "center",
        column: [
          {
            type: "input",
            label: "值班员",
            prop: "masterNam",
            span: 8,
            readonly: true,
          },
          {
            type: "datetime",
            label: "到港时间",
            prop: "toPortTim",
            span: 8,
            readonly: true,
          },
          {
            type: "radio",
            label: "装卸",
            prop: "unloadId",
            span: 8,
            disabled: true,
            dicData: [
              {
                label: "装",
                value: "+",
              },
              {
                label: "卸",
                value: "-",
              },
            ],
          },
          {
            label: "股位",
            prop: "trackName",
            span: 8,
            type: "select",
            props: {
              label: "codeName",
              value: "codeName",
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
                  this.obj0.trackCod = values[0];
                  this.obj0.trackNam = values[1];
                } else {
                }
              }
            },
          },
          {
            type: "input",
            label: "车数",
            prop: "trainNum",
            span: 8,
            readonly: true,
          },
          {
            type: "radio",
            label: "上报否",
            prop: "upId",
            span: 8,
            disabled: true,
            dicData: [
              {
                label: "是",
                value: "1",
              },
              {
                label: "否",
                value: "0",
              },
            ],
          },
        ],
      },
      cities: [],
      units: [],
      objrow: {},
      dialogFormVisible: false,
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
    permissionList() {
      return {
        addBtn: this.vaildData(this.permissions.caccentre_traintent_add, false),
        delBtn: this.vaildData(this.permissions.caccentre_traintent_del, false),
        editBtn: this.vaildData(
          this.permissions.caccentre_traintent_edit,
          false
        ),
      };
    },
  },
  mounted: function () {
    getUnit(Object.assign(this.searchForm))
      .then((response) => {
        this.units = response.data.data;
      })
      .catch(() => {
        this.tableLoading = false;
      });
  },
  methods: {
    getList(page, params) {
      this.tableLoading = true;
      fetchList(
        Object.assign(
          {
            current: page.currentPage,
            size: page.pageSize,
          },
          params,
          this.searchForm
        )
      )
        .then((response) => { 
          if (response.data.data == null) {
            this.obj0.masterNam = "";
            this.obj0.toPortTim = "";
            this.obj0.unloadId = "";
            this.obj0.trackName = "";
            this.obj0.trainNum = "";
            this.obj0.upId = "";
            this.tableData = [];
            this.page.total = 0;
          } else {
            this.obj0.masterNam = response.data.data.records[0].masterNam;
            this.obj0.toPortTim = response.data.data.records[0].toPortTim;
            this.obj0.unloadId = response.data.data.records[0].unloadId;
            this.obj0.trackName = response.data.data.records[0].trackNam;
            this.obj0.trainNum = response.data.data.records[0].trainNum;
            this.obj0.upId = response.data.data.records[0].upId;
            this.tableData = response.data.data.records;
            this.page.total = response.data.data.total;
          }
          this.tableLoading = false;
        })
        .catch(() => {
          this.tableLoading = false;
        });
    },
    rowDel: function (row, index) {
      this.$confirm("是否确认删除", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delObj(row.trainNo, row.trainCod);
        })
        .then((data) => {
          this.$message.success("删除成功");
          this.getList(this.page);
        });
    },
    handleUpdate: function (row, index, done, loading) {
      putObj(row)
        .then((data) => {
          this.$message.success("修改成功");
          done();
          this.getList(this.page);
        })
        .catch(() => {
          loading();
        });
    },
    handleSave: function (row, done, loading) {
      addObj(row)
        .then((data) => {
          this.$message.success("添加成功");
          done();
          this.getList(this.page);
        })
        .catch(() => {
          loading();
        });
    },
    sizeChange(pageSize) {
      this.page.pageSize = pageSize;
    },
    currentChange(current) {
      this.page.currentPage = current;
    },
    searchChange(form, done) {
      this.searchForm = form;
      this.getList(this.page, form);
      done();
    },
    refreshChange() {
      this.getList(this.page);
    },
    remoteMethod(query) {
      this.searchForm.trainNo = query;
      getTrain(Object.assign(this.searchForm))
        .then((response) => {
          this.cities = response.data.data;
        })
        .catch(() => {
          this.tableLoading = false;
        });
    },
    remoteUnit(query) {
      this.searchForm.classId = query;
      getUnit(Object.assign(this.searchForm))
        .then((response) => {
          this.units = response.data.data;
        })
        .catch(() => {
          this.tableLoading = false;
        });
    },
    datechange: function (e) {
      this.searchForm.trainNo = "";
      this.searchForm.datePlan = e;
      getTrain(Object.assign(this.searchForm))
        .then((response) => {
          this.cities = response.data.data;
        })
        .catch(() => {
          this.tableLoading = false;
        });
    },
    unitchange: function (e) {
      this.searchForm.trainNo = "";
      this.searchForm.unitNam = e.unitNam;
      this.searchForm.classId = e.unitCod;
      getTrain(Object.assign(this.searchForm))
        .then((response) => {
          this.cities = response.data.data;
        })
        .catch(() => {
          this.tableLoading = false;
        });
    },
    handleApt: function () {
      let that = this;
      if (this.obj0.upId == 1) {
        this.$message.success("已上报，不能新增");
        return;
      }
      if (this.searchForm.classId == "") {
        this.$message.success("请先选择公司");
        return;
      }
      if (this.searchForm.trainNo == "") {
        getNo(Object.assign(this.searchForm)).then((response) => {
          that.searchForm.trainNo = response.data.data;
          that.objrow = that.searchForm;
          that.objrow.trackNam = "";
          that.objrow.cargoNam = "";
          that.dialogFormVisible = true;
        });
      } else {
        getModel(Object.assign(this.searchForm)).then((response) => {
          if (response.data.data == null) {
            that.objrow = that.searchForm;
            that.objrow.trackNam = "";
            that.objrow.cargoNam = "";
            that.objrow.xgid = "0";
          } else {
            that.objrow = response.data.data;
            that.objrow.trainCod = "";
            that.objrow.oldPiecesNum = "";
            that.objrow.oldWeightWgt = "";
            that.objrow.containerNo1 = "";
            that.objrow.containerNo2 = "";
            that.objrow.remarkTxt = "";
            that.objrow.xgid = "0";
            that.objrow.trainNum = that.obj0.trainNum;
            that.objrow.datePlan = that.searchForm.datePlan;
          }
          that.dialogFormVisible = true;
        });
      }
    },
    handleApt1: function (row) {
      if (this.obj0.upId == 1) {
        this.$message.success("已上报，不能修改");
        return;
      }
      this.objrow = row;
      this.objrow.xgid = "1";
      this.dialogFormVisible = true;
    },
    dictItemVisible: function () {
      this.getList(this.page);
      this.dialogFormVisible = false;
    },
    updateTrain: function () {
      if (this.obj0.upId == 1) {
        this.$message.success("已上报，不能修改");
        return;
      }
      if (
        this.searchForm.trainNo == "" ||
        this.obj0.trackCod == "" ||
        this.obj0.trackCod == null
      ) {
        this.$message.success("请选择一条记录");
        return;
      }
      this.searchForm.trackCod = this.obj0.trackCod;
      this.searchForm.trackNam = this.obj0.trackNam;
      updateTrain(Object.assign(this.searchForm)).then((response) => {
        this.$message.success("修改成功");
        this.getList(this.page);
      });
    },
  },
};
</script>