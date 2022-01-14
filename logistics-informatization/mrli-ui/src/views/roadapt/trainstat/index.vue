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
      <div class="grid-content bg-purple">
        <el-form :inline="true" :model="searchForm" class="demo-form-inline">
          <el-form-item label="日期：">
            <el-date-picker
              v-model="searchForm.srcTim"
              type="date"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              placeholder="选择日期"
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

          <el-form-item label="股道">
            <el-select
              v-model="searchForm.trackNam"
              filterable
              clearable
              remote
              :remote-method="remoteTrack"
              @change="trackchange($event)"
              placeholder="请选择"
            >
              <el-option
                v-for="item in tracks"
                :key="item.codeId"
                :label="item.codeName"
                :value="item"
              >
                <span style="float: left">{{ item.codeId }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{
                  item.codeName
                }}</span>
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="货类">
            <el-select
              v-model="searchForm.cargoKindNam"
              filterable
              clearable
              remote
              :remote-method="remoteCargoKind"
              @change="cargoKindchange($event)"
              placeholder="请选择"
            >
              <el-option
                v-for="item in cargoKinds"
                :key="item.cargoKindCod"
                :label="item.cargoKindNam"
                :value="item"
              >
                <span style="float: left">{{ item.cargoKindCod }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{
                  item.cargoKindNam
                }}</span>
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="货名">
            <el-select
              v-model="searchForm.cargoNam"
              filterable
              clearable
              remote
              :remote-method="remoteCargo"
              @change="cargochange($event)"
              placeholder="请选择"
            >
              <el-option
                v-for="item in cargos"
                :key="item.cargoNam"
                :label="item.codeName"
                :value="item"
              >
                <span style="float: left">{{ item.cargoNam }}</span>
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="装卸">
            <el-select v-model="searchForm.unloadId" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="空重">
            <el-select v-model="searchForm.natureId" placeholder="请选择">
              <el-option
                v-for="item in optionk"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="条件">
            <el-select v-model="searchForm.classId" placeholder="请选择">
              <el-option
                v-for="item in optiont"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-search"
              size="small"
              @click="getList"
              >查询</el-button
            >
          </el-form-item>
        </el-form>
      </div>

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
        <template slot="unloadId" slot-scope="scope">
          <span v-if="scope.row.unloadId == '+'">装</span>
          <span v-if="scope.row.unloadId == '-'">卸</span>
        </template>
        <template slot="natureId" slot-scope="scope">
          <span v-if="scope.row.natureId == '0'">空</span>
          <span v-if="scope.row.natureId == '1'">重</span>
        </template>
        <template slot-scope="{ row, type, size }" slot="menu">
          <el-button :size="size" :type="type" @click="editEvent(row)"
            >实际</el-button
          >
        </template>
      </avue-crud>
      <vxe-modal
        v-if="dialogFormVisible"
        v-model="dialogFormVisible"
        title="完成情况"
        width="800"
        height="500"
        resize
        storage
      >
        <Edit :datarow="objrow" @dictItemVisible="dictItemVisible"></Edit>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import {
  unitList,
  fetchList,
  getUnit,
  getTrain,
  getTrack,
  getCargoKind,
  getCargo,
  getObj,
  addObj,
  putObj,
  delObj,
} from "@/api/roadapt/trainstat";
import { tableOption } from "@/const/crud/roadapt/trainstat";
import { mapGetters } from "vuex";
import moment from "moment";
import VXETable from "vxe-table";
import Edit from "./edit";

export default {
  components: { Edit },
  name: "trainstat",
  data() {
    return {
      searchForm: {
        srcTim: moment(new Date()).format("YYYY-MM-DD"),
        unitCod: "",
        unitNam: "",
        trackCod: "",
        trackNam: "",
        cargoKindCod: "",
        cargoKindNam: "",
        cargoCod: "",
        cargoNam: "",
        unloadId: "",
        natureId: "",
        classId: "",
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
      options: [
        {
          value: "+",
          label: "装",
        },
        {
          value: "-",
          label: "卸",
        },
      ],
      optionk: [
        {
          value: "0",
          label: "空",
        },
        {
          value: "1",
          label: "重",
        },
      ],
      optiont: [
        {
          value: "1",
          label: ">30分钟",
        },
        {
          value: "2",
          label: ">60分钟",
        },
      ],
      cities: [],
      tracks: [],
      cargoKinds: [],
      cargos: [],
      units: [],
      objrow: {},
      dialogFormVisible: false,
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
    permissionList() {
      return {
        addBtn: this.vaildData(this.permissions.roadapt_trainstat_add, false),
        delBtn: this.vaildData(this.permissions.roadapt_trainstat_del, false),
        editBtn: this.vaildData(this.permissions.roadapt_trainstat_edit, false),
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
    getTrack("")
      .then((response) => {
        this.tracks = response.data.data;
      })
      .catch(() => {
        this.tableLoading = false;
      });
    getCargoKind(Object.assign(this.searchForm))
      .then((response) => {
        this.cargoKinds = response.data.data;
      })
      .catch(() => {
        this.tableLoading = false;
      });
    getCargo(Object.assign(this.searchForm))
      .then((response) => {
        this.cargos = response.data.data;
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
          this.tableData = response.data.data.records;
          this.page.total = response.data.data.total;
          this.tableLoading = false;
        })
        .catch(() => {
          this.tableLoading = false;
        });
    },
    rowDel: function (row, index) {
      this.$confirm("是否确认删除ID为" + row.trainStatSeq, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delObj(row.trainStatSeq);
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
    unitchange: function (e) {
      this.searchForm.unitCod = e.unitCod;
      this.searchForm.unitNam = e.unitNam;
    },
    trackchange: function (e) {
      this.searchForm.trackCod = e.codeId;
      this.searchForm.trackNam = e.codeName;
    },
    cargoKindchange: function (e) {
      this.searchForm.cargoKindCod = e.cargoKindCod;
      this.searchForm.cargoKindNam = e.cargoKindNam;
    },
    cargochange: function (e) {
      this.searchForm.cargoNam = e.cargoNam;
    },
    remoteUnit(query) {
      this.searchForm.unitCod = query;
      getUnit(Object.assign(this.searchForm))
        .then((response) => {
          this.units = response.data.data;
        })
        .catch(() => {
          this.tableLoading = false;
        });
    },
    remoteTrack(query) {
      this.searchForm.trackCod = query;
      getTrack(query)
        .then((response) => {
          this.tracks = response.data.data;
        })
        .catch(() => {
          this.tableLoading = false;
        });
    },
    remoteCargoKind(query) {
      this.searchForm.cargoKindCod = query;
      getCargoKind(Object.assign(this.searchForm))
        .then((response) => {
          this.cargoKinds = response.data.data;
        })
        .catch(() => {
          this.tableLoading = false;
        });
    },
    remoteCargo(query) {
      this.searchForm.cargoNam = query;
      getCargo(Object.assign(this.searchForm))
        .then((response) => {
          this.cargos = response.data.data;
        })
        .catch(() => {
          this.tableLoading = false;
        });
    },
    dictItemVisible() {
      this.dialogFormVisible = false;
    },
    editEvent(row) {
      this.objrow = row;
      this.dialogFormVisible = true;
    },
  },
};
</script>