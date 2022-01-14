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
  <div class="caccentre-shipagent-index">
    <basic-container>
      <!--查询区-->
      
      <!--功能按钮-->
       <vxe-toolbar perfect size="mini">
        <template v-slot:buttons>
           <vxe-button type="text" class="fa fa-plus" @click="add('add')"
            >新增</vxe-button
          >
          <vxe-button
            type="text"
            class="fa fa-pencil-square-o"
            @click="add('edit')"
            >修改</vxe-button
          >
          <vxe-button
                type="text"
                class="fa fa-trash"
                @click="deleteShipAgent()"
                >删除</vxe-button
              >
          
        </template>
      </vxe-toolbar>
      <!--表格-->
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
        height="650px"
        ref="tableMan"
        @cell-click="rowclick"
      >
        <vxe-table-column type="seq" width="50" title="序号"></vxe-table-column>
        <vxe-table-column
          field="agentCod"
          title="代码"
          width="70"
        ></vxe-table-column>
        <vxe-table-column
          field="agentNam"
          title="代理名称"
          width="240"
        ></vxe-table-column>
        <vxe-table-column
          field="engAgentNam"
          title="英文名称"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="agentShort"
          title="代理简称"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="corporation"
          title="法人代表"
          width="120"
        ></vxe-table-column>
        <vxe-table-column
          field="telephone"
          title="电话"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="eMail"
          title="E_MAIL"
          width="200"
        ></vxe-table-column>
      </vxe-table>
      <!--分页区-->
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
          'Total',
        ]"
        @page-change="handlePageChange"
      ></vxe-pager>
      <!-----------------------次级页面-------------------------------------------------->
       
      <vxe-modal
        v-if="dialogFormVisibleAdd"
        v-model="dialogFormVisibleAdd"
        title="船舶代理信息维护"
        width="800"
        height="360"
        resize
        storage
      >
        <Add
          :datanow="dataNext" @dictItemVisible="dictItemVisible" @selectList="getDate"
        ></Add>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleedit"
        v-model="dialogFormVisibleedit"
        title="船舶代理信息修改"
        width="800"
        height="360"
        resize
        storage
      >
         <Edit
          :datanow="dataNext"
          @dictItemVisible="dictItemVisible"
          @selectListplan="getDate"
        ></Edit>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import { fetchList,delObj } from "@/api/caccentre/shipagent";
import { tableOption } from "@/const/crud/caccentre/shipagent";
import { mapGetters } from "vuex";
import EditDown from "../../baseUtile/EditDown";
import Add from "./add";
import Edit from "./Edit";
export default {
  name: "caccentre-shipagent-index",
  components: { EditDown,Add,Edit },
  data() {
    return {
      formObj: {
        shipNam: null,
        eShipNam: null,
      },
      searchForm: {},
      tableData: [],
      dataHeadNow: {},
      dialogFormVisibleedit: false,
      dialogFormVisibleAdd: false,
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      tableload: false,
      tableOption: tableOption,
      downTableOption: {
        //中文船名
        shipNam: "shipNam",
        shipNamColum: [
          { field: "SHIP_NAM", title: "中文船名" },
        ],
        shipNamreturnApi: [
          { field: "SHIP_NAM", returnName: "shipNam" },
        ],

        shipNamurl: "/caccentre/shipquery/getshipNam",
        //英文船名
        eShipNam: "eShipNam",
        eShipNamColum: [
          { field: "E_SHIP_NAM", title: "中文船名" },
        ],
        eShipNamreturnApi: [
          { field: "E_SHIP_NAM", returnName: "eShipNam" },
        ],

        eShipNamurl: "/caccentre/shipquery/geteShipNam",
      },
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  created() {
    this.getDate();
  },
  methods: {
    getDate() {
      this.tableload = true;
      this.dataHeadNow = {};
      fetchList(
        Object.assign(
          {
            current: this.tablePage.currentPage,
            size: this.tablePage.pageSize,
          },
          this.formObj
        )
      )
        .then((response) => {
          this.tableData = response.data.data.records;
          this.tablePage.total = response.data.data.total;
          this.tableload = false;
        })
        .catch(() => {
          this.tableload = false;
        });
    },

    add(type) {
      if (type === "edit") {
        console.log(this.dataHeadNow);
        if (this.dataHeadNow != null && this.dataHeadNow.agentCod != null) {
          
          this.dataNext = this.dataHeadNow;
          this.dialogFormVisibleedit = true;
        } else {
          this.$message.error("请选择一个数据进行修改");
        }
      } else if (type === "add") {
        this.dataNext = {};
      this.dialogFormVisibleAdd = true;
      }
    },
    deleteShipAgent() {
      if (this.dataHeadNow == null || this.dataHeadNow.agentCod == null) {
        this.$message.error("请选择一条动态进行删除");
        return;
      }
      this.$XModal.confirm("您确定要删除吗？").then((type) => {
        if (type == "confirm") {
          delObj(this.dataHeadNow.agentCod)
            .then((response) => {
              this.$message.success("删除成功");
              this.getDate();
            })
            .catch(() => {});
        }
      });
    },
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
      this.dialogFormVisibleAdd = false;
    },
    cellDBLClickEvent({ row }) {
      this.dateNow = row;
      this.dialogFormVisibleView = true;
    },
    rowclick({ row }) {
      this.dataHeadNow = row;
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    sizeChange(pageSize) {
      this.tablePage.pageSize = pageSize;
    },
    currentChange(current) {
      this.tablePage.currentPage = current;
    },
    searchChange(form, done) {
      this.searchForm = form;
      this.tablePage.currentPage = 1;
      this.getList(this.tablePage, form);
      done();
    },
    refreshChange() {
      this.getList(this.tablePage);
    },
  },
};
</script>