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
  <div class="zyworkcarinfo-index">
    <basic-container>
      <vxe-form :data="formObj">
        <vxe-form-item title="时间" field="selectDate" size="mini" span="4">
          <vxe-input
            v-model="formObj.selectDate"
            type="date"
            clearable
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="公司" field="unitName" size="mini" span="4">
          <EditDown
            :objs="formObj"
            property="unitName"
            :tableColumns="downTableOption.unitColum"
            :urlapi="downTableOption.uniturl"
            :returnApi="downTableOption.unitreturnApi"
            sizenow="mini"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item title="类型" field="unloadId" size="mini" span="4">
          <vxe-select v-model="formObj.unloadId" placeholder="请选择类型">
            <vxe-option key="+" value="+" label="市出"></vxe-option>
            <vxe-option key="-" value="-" label="市入"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="center">
          <vxe-button status="primary" icon="fa fa-search" @click="getDate()"
            >查询</vxe-button
          >
          <vxe-button type="reset" icon="fa fa-repeat">重置</vxe-button>
          <!--<vxe-button status="primary" icon="fa fa-search" @click="copyLast()"
            >复制</vxe-button
          >-->
        </vxe-form-item>
      </vxe-form>
      <vxe-toolbar perfect size="mini">
        <template v-slot:buttons>
          <vxe-button type="text" class="fa fa-plus" @click="add()"
            >新增</vxe-button
          >
          <vxe-button type="text" icon="fa fa-floppy-o" @click="save()"
            >保存</vxe-button
          >
          <vxe-button
            type="text"
            icon="fa fa-trash"
            @click="$refs.tableCar.removeCheckboxRow()"
            >删除</vxe-button
          >
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
        height="600px"
        ref="tableCar"
        :edit-rules="validRules"
        :edit-config="{ trigger: 'click', mode: 'row' }"
        :mouse-config="{ selected: true }"
        :checkbox-config="{ range: true }"
      >
        <vxe-table-column type="checkbox" width="60"></vxe-table-column>
        <vxe-table-column
          field="workDate"
          title="日期"
          :formatter="['formatDate', 'yyyy-MM-dd']"
          :edit-render="{}"
        >
          <template v-slot:edit="{ row, rowIndex }">
            <vxe-input
              type="date"
              v-model="row.workDate"
              placeholder="请选择日期"
              valueFormat="yyyy-MM-dd"
              transfer
            ></vxe-input>
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="unitName"
          title="作业公司"
          :edit-render="{
            name: 'EditDownTable',
            props: {
              checkField: 'unitName',
              tableColumn: this.downTableOption.unitColum,
              urlapi: this.downTableOption.uniturl,
              returnApi: this.downTableOption.unitreturnApi
            }
          }"
        ></vxe-table-column>
        <vxe-table-column
          field="cargoName"
          title="货名"
          :edit-render="{
            name: 'EditDownTable',
            props: {
              checkField: 'cargoName',
              tableColumn: this.downTableOption.cargoColum,
              urlapi: this.downTableOption.cargourl,
              returnApi: this.downTableOption.cargoreturnApi
            }
          }"
        ></vxe-table-column>
        <vxe-table-column
          field="unloadId"
          title="类型"
          :edit-render="{ autofocus: '.vxe-input--inner' }"
          :formatter="formatterInoutId"
        >
          <template v-slot:edit="{ row }">
            <vxe-select v-model="row.unloadId" placeholder="请选择类型">
              <vxe-option key="+" value="+" label="市出"></vxe-option>
              <vxe-option
                key="-"
                value="-"
                label="市入"
              ></vxe-option> </vxe-select></template
        ></vxe-table-column>
        <vxe-table-column
          field="workWgt"
          title="吨数"
          :edit-render="{ autofocus: '.vxe-input--inner' }"
        >
          <template v-slot:edit="{ row }">
            <vxe-input type="number" v-model="row.workWgt"></vxe-input>
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="infoBak"
          title="备注"
          :edit-render="{ autofocus: '.vxe-input--inner' }"
        >
          <template v-slot:edit="{ row }">
            <vxe-input v-model="row.infoBak"></vxe-input>
          </template>
        </vxe-table-column>
      </vxe-table>
      <vxe-pager
        perfect
        size="mini"
        :loading="tableload"
        :current-page="tablePage.currentPage"
        :page-size="tablePage.pageSize"
        :total="tablePage.total"
        :layouts="[
          'PrevPage',
          'JumpNumber',
          'NextPage',
          'FullJump',
          'Sizes',
          'Total'
        ]"
        @page-change="handlePageChange"
      ></vxe-pager>
    </basic-container>
  </div>
</template>

<script>
import { fetchList, saveAll,copyApi} from "@/api/caccentre/zyworkcarinfo";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import EditDown from "../../baseUtile/EditDown";
import moment from "moment";
export default {
  components: { EditDown },
  name: "zyworkcarinfo-index",
  data() {
    return {
      formObj: { unitName: null, unitCod: null, selectDate: null },
      dialogFormVisibleedit: false,
      tableData: [],
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      tableload: false,
      validRules: {
        workDate: [{ required: true, message: "必须选择时间" }],
        unitName: [{ required: true, message: "必须选择公司" }],
        cargoName: [{ required: true, message: "必须选择货物" }],
        unloadId: [{ required: true, message: "必须选择类型" }]
      },
      downTableOption: {
        //作业公司下拉菜单展示项
        unitColum: [
          { field: "SHOT_NAM", title: "公司代码" },
          { field: "UNIT_COD", title: "公司名称" }
        ],
        //作业公司下拉菜单的返回项
        unitreturnApi: [
          { field: "UNIT_COD", returnName: "unitCod" },
          { field: "SHOT_NAM", returnName: "unitName" }
        ],
        //作业公司下拉菜单的路径
        uniturl: "/caccentre/basecod/unit/page?sqlParam=1",
        //货名下拉菜单展示项
        cargoColum: [
          { field: "CARGO_NAM_COD", title: "货物代码" },
          { field: "CARGO_NAM", title: "货物名称" },
          { field: "CARGO_KIND_COD_PRICE", title: "价格货类代码" },
          { field: "CARGO_KIND_NAM_PRICE", title: "价格货类名称" },
          { field: "CARGO_KIND_COD_DL", title: "大货类代码" },
          { field: "CARGO_KIND_NAM_DL", title: "大货类名称" }
        ],
        //货名下拉菜单的返回项
        cargoreturnApi: [
          { field: "CARGO_NAM_COD", returnName: "cargoNameCod" },
          { field: "CARGO_NAM", returnName: "cargoName" }
        ],
        //货名下拉菜单的路径
        cargourl: "/yard/mioyardhead/getCargoNam"
      }
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() {
    this.formObj.selectDate = moment(new Date()).format("YYYY-MM-DD");
    this.getDate();
  },
  methods: {
    getDate() {
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
          this.tablePage.total = response.data.data.total;
          this.tableload = false;
        })
        .catch(() => {
          this.tableload = false;
        });
    },
    add() {
      let record = {
        workWgt: 0,
        unitCod: this.formObj.unitCod,
        unitName: this.formObj.unitName,
        unloadId: this.formObj.unloadId,
        workDate: this.formObj.selectDate
      };
      this.$refs.tableCar
        .insert(record)
        .then(({ row }) => this.$refs.tableCar.setActiveCell(row, "workWgt"));
    },
    async save() {
      const errMap = await this.$refs.tableCar
        .fullValidate()
        .catch(errMap => errMap);
      if (errMap) {
      } else {
        let loadingInstance = Loading.service({ fullscreen: true });
        saveAll(this.$refs.tableCar.getRecordset())
          .then(data => {
            this.$message.success("编辑成功");
            loadingInstance.close();
            this.getDate();
          })
          .catch(() => {
            loadingInstance.close();
          });
      }
    },
    formatterInoutId({ cellValue }) {
      if (cellValue == "+") {
        return "市出";
      } else if (cellValue == "-") {
        return "市入";
      }
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    resetForm() {},
    copyLast() {
      if (this.formObj.selectDate == null || this.formObj.selectDate == "") {
        this.$message.error("请选择时间");
        return;
      }
         let loadingInstance = Loading.service({ fullscreen: true });
       copyApi(this.formObj.selectDate)
          .then(data => {
            this.$message.success("复制成功");
            loadingInstance.close();
            this.getDate();
          })
          .catch(() => {
            loadingInstance.close();
          });
    }
  }
};
</script>
<style lang="scss" scoped>
.zyworkcarinfo-index {
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