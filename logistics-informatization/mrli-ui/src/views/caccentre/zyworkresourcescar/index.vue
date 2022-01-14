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
  <div class="zyworkresourcescar">
    <basic-container>
      <vxe-form :data="formObj">
        <vxe-form-item title="时间" field="selectDate" size="mini" span="4">
          <vxe-input v-model="formObj.selectDate" type="date" clearable></vxe-input>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="center">
          <vxe-button status="primary" icon="fa fa-search" @click="getDate()">查询</vxe-button>
        </vxe-form-item>
      </vxe-form>
      <el-row :gutter="15">
        <el-col :span="12">
          <vxe-toolbar perfect size="mini">
            <template v-slot:buttons>
              <vxe-button type="text" class="fa fa-plus" @click="add()">新增</vxe-button>
              <vxe-button type="text" icon="fa fa-floppy-o" @click="edit()">修改</vxe-button>
            </template>
          </vxe-toolbar>
          <vxe-table
            border
            show-overflow
            highlight-current-row
            size="mini"
            :data="tableData"
            :loading="tableload"
            :round="true"
            align="center"
            height="600px"
            ref="tableOne"
            @cell-click="rowclick"
          >
            <vxe-table-column
              field="zyWorkDte"
              title="日期"
              :formatter="['formatDate', 'yyyy-MM-dd']"
            ></vxe-table-column>
            <vxe-table-column title="装载机">
              <vxe-table-column field="zyZzjNum" title="在册"></vxe-table-column>
              <vxe-table-column field="zyZzjWokrNum" title="出勤"></vxe-table-column>
            </vxe-table-column>
            <vxe-table-column title="自卸车">
              <vxe-table-column field="zyZxcNum" title="在册"></vxe-table-column>
              <vxe-table-column field="zyZxcWorkNum" title="出勤"></vxe-table-column>
            </vxe-table-column>
            <vxe-table-column title="挂车">
              <vxe-table-column field="zyGcNum" title="在册"></vxe-table-column>
              <vxe-table-column field="zyGcWorkNum" title="出勤"></vxe-table-column>
            </vxe-table-column>
            <vxe-table-column title="美国鹰">
              <vxe-table-column field="zyMgyNum" title="在册"></vxe-table-column>
              <vxe-table-column field="zyMgyWorkNum" title="出勤"></vxe-table-column>
            </vxe-table-column>
          </vxe-table>
          <vxe-pager
            perfect
            size="mini"
            :loading="tableload"
            :current-page="tablePage.currentPage"
            :page-size="tablePage.pageSize"
            :total="tablePage.total"
            :layouts="['PrevPage', 'JumpNumber', 'NextPage', 'FullJump', 'Sizes', 'Total']"
            @page-change="handlePageChange"
          ></vxe-pager>
        </el-col>
        <el-col :span="12">
          <vxe-toolbar perfect size="mini">
            <template v-slot:buttons>
              <vxe-button type="text" class="fa fa-plus" @click="addBody()">新增</vxe-button>
              <vxe-button type="text" icon="fa fa-floppy-o" @click="saveBody()">保存</vxe-button>
            </template>
          </vxe-toolbar>
          <vxe-table
            border
            show-overflow
            keep-source
            size="mini"
            :data="tableDataTwo"
            :loading="tableloadTwo"
            :round="true"
            align="center"
            height="600px"
            ref="tableTwo"
            :edit-rules="tableTwovalidRules"
            :edit-config="{trigger: 'click', mode: 'row'}"
            :mouse-config="{selected: true}"
            :checkbox-config="{range: true}"
          >
            <vxe-table-column
              field="zyUnitName"
              title="作业公司"
              :edit-render="{name: 'EditDownTable', props: {checkField: 'zyUnitName',tableColumn:this.downTableOption.unitColum,urlapi:this.downTableOption.uniturl,returnApi:this.downTableOption.unitreturnApi}}"
            ></vxe-table-column>
            <vxe-table-column field="zyZzjNum" title="装载机" :edit-render="{name: 'input'}"></vxe-table-column>
            <vxe-table-column field="zyZxcNum" title="自卸车" :edit-render="{name: 'input'}"></vxe-table-column>
            <vxe-table-column field="zyGcNum" title="挂车" :edit-render="{name: 'input'}"></vxe-table-column>
            <vxe-table-column field="zyMgyNum" title="美国鹰" :edit-render="{name: 'input'}"></vxe-table-column>
          </vxe-table>
        </el-col>
      </el-row>
      <vxe-modal
        v-if="dialogFormVisibleedit"
        v-model="dialogFormVisibleedit"
        title="基本信息维护"
        width="500"
        height="400"
        resize
        storage
      >
        <EditHead :datanow="dataNext" @dictItemVisible="dictItemVisible" @selectList="getDate"></EditHead>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import {
  fetchList,
  fetchBodyList,
  saveAll
} from "@/api/caccentre/zyworkresourcescar";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import EditHead from "./editHead";
export default {
  components: { EditHead },
  name: "zyworkresourcescar",
  data() {
    return {
      formObj: {},
      dataHeadNow: {},
      dataNext: {},
      dialogFormVisibleedit: false,
      tableData: [],
      tableDataTwo: [],
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      tableload: false,
      tableloadTwo: false,
      tableTwovalidRules: {
        zyUnitName: [{ required: true, message: "必须选择公司" }]
      },
      downTableOption: {
        //费目下拉菜单展示项
        unitColum: [
          { field: "SHOT_NAM", title: "公司代码" },
          { field: "UNIT_COD", title: "公司名称" }
        ],
        //费目下拉菜单的返回项
        unitreturnApi: [
          { field: "UNIT_COD", returnName: "zyUnitCod" },
          { field: "SHOT_NAM", returnName: "zyUnitName" }
        ],
        //费目下拉菜单的路径
        uniturl: "/caccentre/basecod/unit/page?sqlParam=1"
      }
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
    getBody() {
      this.tableloadTwo = true;
      fetchBodyList(this.dataHeadNow.zyHeadNo)
        .then(response => {
          this.tableDataTwo = response.data.data;
          this.tableloadTwo = false;
        })
        .catch(() => {
          this.tableloadTwo = false;
        });
    },
    rowclick({ row }) {
      this.dataHeadNow = row;
      this.getBody();
    },
    add() {
      this.dataNext = {};
      this.dialogFormVisibleedit = true;
    },
    edit() {
      if (this.dataHeadNow != null && this.dataHeadNow.zyHeadNo != null) {
        this.dataNext = this.dataHeadNow;
        this.dialogFormVisibleedit = true;
      }
    },
    addBody() {
      if (this.dataHeadNow != null && this.dataHeadNow.zyHeadNo != null) {
        let record = {
          zyHeadNo: this.dataHeadNow.zyHeadNo,
          zyZzjNum: 0,
          zyZxcNum: 0,
          zyGcNum: 0,
          zyMgyNum: 0
        };
        this.$refs.tableTwo
          .insert(record)
          .then(({ row }) =>
            this.$refs.tableTwo.setActiveCell(row, "zyUnitName")
          );
      } else {
        this.$message.error("请选择头信息进行新增");
      }
    },
    async saveBody() {
      const errMap = await this.$refs.tableTwo
        .fullValidate()
        .catch(errMap => errMap);
      if (errMap) {
      } else {
        let loadingInstance = Loading.service({ fullscreen: true });
        saveAll(this.$refs.tableTwo.getRecordset())
          .then(data => {
            this.$message.success("编辑成功");
            loadingInstance.close();
            this.getBody();
          })
          .catch(() => {
            loadingInstance.close();
          });
      }
    },
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
    },
    async save() {},
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    }
  }
};
</script>
<style lang="scss" scoped>
.zyworkresourcescar {
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