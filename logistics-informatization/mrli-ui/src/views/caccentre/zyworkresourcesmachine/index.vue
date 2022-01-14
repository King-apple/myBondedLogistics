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
  <div class="zyworkresourcesmachine">
    <basic-container>
      <vxe-form :data="formObj">
        <vxe-form-item title="时间" field="selectDate" size="mini" span="4">
          <vxe-input v-model="formObj.selectDate" type="date"></vxe-input>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="center">
          <vxe-button status="primary" icon="fa fa-search" @click="getDate()">查询</vxe-button>
          <vxe-button status="danger" icon="fa fa-spinner" @click="dealDate()">处理</vxe-button>
        </vxe-form-item>
      </vxe-form>
      <vxe-toolbar perfect size="mini">
        <template v-slot:buttons>
          <vxe-button type="text" class="fa fa-pencil" @click="editMan()">编辑人员情况</vxe-button>
          <vxe-button type="text" icon="fa fa-floppy-o" @click="save()">保存</vxe-button>
        </template>
      </vxe-toolbar>
      <vxe-table
        border
        show-overflow
        keep-source
        size="mini"
        :data="tableData"
        :loading="tableload"
        :round="true"
        align="center"
        height="1000px"
        ref="tableMachine"
        :edit-config="{trigger: 'click', mode: 'row'}"
        :mouse-config="{selected: true}"
         :span-method="mergeMethod"
        :checkbox-config="{range: true}"
        :keyboard-config="{isArrow: true, isDel: true, isEnter: true, isTab: true, isEdit: true}"
      >
        <vxe-table-column field="zyWorkDte" title="日期" :formatter="['formatDate', 'yyyy-MM-dd']"></vxe-table-column>
        <vxe-table-column field="zyMachineName" title="机械类别"></vxe-table-column>
        <vxe-table-column
          field="zyMachineNum"
          title="机械在册"
          :edit-render="{autofocus: '.vxe-input--inner'}"
        >
          <template v-slot:edit="{ row }">
            <vxe-input type="number" v-model="row.zyMachineNum"></vxe-input>
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="zyMachineWorkNum"
          title="机械出勤"
          :edit-render="{autofocus: '.vxe-input--inner'}"
        >
          <template v-slot:edit="{ row }">
            <vxe-input type="number" v-model="row.zyMachineWorkNum"></vxe-input>
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="zyMachinePNum"
          title="8：00派牌"
          :edit-render="{autofocus: '.vxe-input--inner'}"
        >
          <template v-slot:edit="{ row }">
            <vxe-input type="number" v-model="row.zyMachinePNum"></vxe-input>
          </template>
        </vxe-table-column>
        <vxe-table-column field="zyManWorkNum" title="人员出勤数"></vxe-table-column>
        <vxe-table-column field="zyManJNum" title="加班人数"></vxe-table-column>
      </vxe-table>
      <vxe-modal
        v-if="dialogFormVisibleedit"
        v-model="dialogFormVisibleedit"
        title="人员信息维护"
        width="700"
        height="900"
        resize
        storage
      >
        <Edit :datenow="dateNow" @dictItemVisible="dictItemVisible" @selectList="getDate"></Edit>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import {
  fetchList,
  dealByDate,
  saveAll
} from "@/api/caccentre/zyworkresourcesmachine";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import moment from "moment";
import Edit from "./edit";
export default {
  components: { Edit },
  name: "zyworkresourcesman",
  data() {
    return {
      formObj: { selectDate: moment(new Date()).format("YYYY-MM-DD") },
      dialogFormVisibleedit: false,
      dateNow: "",
      mergeCellsTableMachine:[],
      tableData: [],
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 50 // 每页显示多少条
      },
      tableload: false
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() {
    this.getDate();
  },
  methods: {
    getDate() {
      if (this.formObj.selectDate == null || this.formObj.selectDate == "") {
        this.$message.error("请选择时间");
        return;
      }
      this.tableload = true;
      fetchList(
        Object.assign(
          {
            current: this.tablePage.currentPage,
            size: this.tablePage.pageSize
          },
          this.formObj
        )
      )
        .then(response => {
          this.tableData = response.data.data.records;
           for (let i in this.tableData) {
            if (this.tableData[i].spanMap != null) {
              this.mergeCellsTableMachine = this.tableData[i].spanMap;
            }
          }
          this.tablePage.total = response.data.data.total;
          this.tableload = false;
        })
        .catch(() => {
          this.tableload = false;
        });
    },

    async save() {
      const errMap = await this.$refs.tableMachine
        .fullValidate()
        .catch(errMap => errMap);
      if (errMap) {
      } else {
        let loadingInstance = Loading.service({ fullscreen: true });
        saveAll(this.$refs.tableMachine.getRecordset())
          .then(data => {
            this.$message.success("编辑成功");
            loadingInstance.close();
            this.getDate();
          })
          .catch(() => {
            this.getDate();
            loadingInstance.close();
          });
      }
    },
    dealDate() {
      if (this.formObj.selectDate == null || this.formObj.selectDate == "") {
        this.$message.error("请选择时间");
        return;
      }
      dealByDate(this.formObj.selectDate)
        .then(data => {
          this.$message.success("处理成功");
          loadingInstance.close();
          this.getDate();
        })
        .catch(() => {
          this.getDate();
          loadingInstance.close();
        });
    },
    editMan() {
      if (this.formObj.selectDate == null || this.formObj.selectDate == "") {
        this.$message.error("请选择时间");
        return;
      }
      this.dateNow = this.formObj.selectDate;
      this.dialogFormVisibleedit = true;
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
    },
    mergeMethod({ row, column }) {
      const { mergeCellsTableMachine } = this;
      const xTable = this.$refs.tableMachine;
      const _rowIndex = xTable._getRowIndex(row);
      const _columnIndex = xTable._getColumnIndex(column);
      for (let mIndex = 0; mIndex < mergeCellsTableMachine.length; mIndex++) {
        const { row, col, rowspan, colspan } = mergeCellsTableMachine[mIndex];
        if (row === _rowIndex && col === _columnIndex) {
          return { rowspan, colspan };
        }
        if (
          _rowIndex >= row &&
          _rowIndex < row + rowspan &&
          _columnIndex >= col &&
          _columnIndex < col + colspan
        ) {
          return { rowspan: 0, colspan: 0 };
        }
      }
      return { rowspan: 1, colspan: 1 };
    },
  }
};
</script>
<style lang="scss" scoped>
.zyworkresourcesmachine {
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
</style>