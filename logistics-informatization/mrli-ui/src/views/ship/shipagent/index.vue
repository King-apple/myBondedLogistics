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
  <div class="ship-shipagent-index">
    <basic-container>
      <vxe-form :data="formObj" size="mini">
        <vxe-form-item
          title="代码/名称："
          field="codeName"
          size="mini"
          span="6"
        >
          <vxe-input v-model="formObj.codeName" clearable></vxe-input>
        </vxe-form-item>
        <vxe-form-item span="6" size="mini" align="center">
          <vxe-button
            status="primary"
            icon="fa fa-search"
            @click="selectTable()"
            >查询</vxe-button
          >
        </vxe-form-item>
      </vxe-form>

      <el-row>
        <el-col :span="24">
          <vxe-toolbar perfect size="mini">
            <template v-slot:buttons>
              <vxe-button type="text" class="fa fa-plus" @click="addEvent()">
                新 增</vxe-button
              >
              <vxe-button
                type="text"
                class="fa fa-pencil-square-o"
                @click="editEvent()"
              >
                编 辑</vxe-button
              >
              <vxe-button
                type="text"
                class="fa fa-pencil-square-o"
                @click="viewEvent()"
              >
                查 看</vxe-button
              >
            </template>
          </vxe-toolbar>
          <vxe-table
            border
            show-overflow
            keep-source
            highlight-current-row
            size="mini"
            :data="tableDataShip"
            :loading="tableloadShip"
            :round="true"
            align="center"
            height="650px"
            ref="tableShip"
            @cell-click="rowclick"
          >
            <vxe-table-column field="agentCod" title="代码"></vxe-table-column>
            <vxe-table-column field="agentNam" title="名称"></vxe-table-column>
            <vxe-table-column field="address" title="地址"></vxe-table-column>
            <vxe-table-column
              field="agentAccount"
              title="账号"
            ></vxe-table-column>
            <vxe-table-column field="agentBank" title="银行"></vxe-table-column>
            <vxe-table-column field="eMail" title="邮箱"></vxe-table-column>
            <vxe-table-column field="fax" title="传真"></vxe-table-column>
            <vxe-table-column
              field="postalcode"
              title="邮编"
            ></vxe-table-column>
            <vxe-table-column field="phone" title="电话"></vxe-table-column>
            <vxe-table-column
              field="relatMan"
              title="联系人"
            ></vxe-table-column>
            <vxe-table-column
              field="markDte"
              title="注册时间"
            ></vxe-table-column>
          </vxe-table>
          <vxe-pager
            perfect
            size="mini"
            :loading="tableloadShip"
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
        </el-col>
      </el-row>

      <vxe-modal
        v-if="dialogFormVisibleedit"
        v-model="dialogFormVisibleedit"
        title="信息编辑"
        width="600"
        height="700"
        resize
        storage
      >
        <Edit
          :datanow="dataNow"
          :dataType="dataType"
          @dictItemVisible="dictItemVisible"
          @selectList="selectTable"
        ></Edit>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleView"
        v-model="dialogFormVisibleView"
        title="查看"
        width="600"
        height="700"
        resize
        storage
      >
        <ViewNow
          :datanow="dataNow"
          @dictItemVisible="dictItemVisible"
          @selectList="selectTable"
        ></ViewNow>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import { fetchList } from "@/api/ship/shipagent";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import EditDown from "../../baseUtile/EditDown";
import Edit from "./edit";
import ViewNow from "./viewNow";
import moment from "moment";
export default {
  components: { Edit, EditDown, ViewNow },
  name: "ship-shipagent-index",
  data() {
    return {
      formObj: {
        callNo: null
      },
      dialogFormVisibleedit: false,
      dialogFormVisibleView: false,
      tableDataShip: [],
      dataType: "add",
      tableloadShip: false,
      dataNow: {},
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      downTableOption: {
        //队别下拉菜单展示项
        subNamColum: [
          { field: "callNo", title: "呼号" },
          { field: "cshipNam", title: "中文船名" },
          { field: "eshipNam", title: "英文船名" }
        ],
        //下拉菜单的路径
        subNamurl: "/caccentre/shipdata/page",
        callNo: "callNo",
        subNamreturnApi: [{ field: "callNo", returnName: "callNo" }]
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
      this.tableloadShip = true;
      fetchList(
        Object.assign({
          current: this.tablePage.currentPage,
          size: this.tablePage.pageSize,
          codeName: this.formObj.codeName
        })
      )
        .then(response => {
          this.tableDataShip = response.data.data.records;
          this.tablePage.total = response.data.data.total;
          this.tableloadShip = false;
        })
        .catch(() => {
          this.tableloadShip = false;
        });
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    rowclick({ row }) {
      this.dataNow = row;
      console.log(this.dataNow);
    },
    selectTable() {
      this.tablePage.currentPage = 1;
      this.tablePage.pageSize = 20;
      this.getDate();
    },
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
       this.dialogFormVisibleView = false;
    },
    editEvent() {
      if (this.dataNow == null || this.dataNow.agentCod == null) {
        this.$message.error("请选择一个船东进行编辑");
        return;
      }
      this.dataType = null;
      this.dialogFormVisibleedit = true;
    },
    addEvent() {
      this.dataNow = null;
      this.dataType = "add";
      this.dialogFormVisibleedit = true;
    },
    viewEvent() {
      if (this.dataNow == null || this.dataNow.agentCod == null) {
        this.$message.error("请选择一个船东进行查看");
        return;
      }
      this.dialogFormVisibleView = true;
    }
  }
};
</script>
<style lang="scss" scoped>
.ship-shipagent-index {
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