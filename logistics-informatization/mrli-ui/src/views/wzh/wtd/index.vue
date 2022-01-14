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
  <div class="wzh-wzhentrust-index">
    <basic-container>
      <vxe-form :data="formObj">
        <vxe-form-item title="货主名称" field="consignNam" size="mini" span="4">
          <vxe-input v-model="formObj.consignNam" clearable />
        </vxe-form-item>
        <vxe-form-item span="5" size="mini" align="center">
          <vxe-button
            status="primary"
            icon="fa fa-search"
            @click="selectTable()"
            >查询
          </vxe-button>
          <vxe-button type="reset" icon="fa fa-repeat" @click="resetForm">
            重置
          </vxe-button>
        </vxe-form-item>
      </vxe-form>
      <vxe-toolbar custom perfect size="mini">
        <template v-slot:buttons>
          <vxe-button type="text" class="fa fa-check-square-o" @click="commitData()">
            审核
          </vxe-button>
          <vxe-button type="text" class="fa fa-times" @click="backData()">
            回退
          </vxe-button>
        </template>
      </vxe-toolbar>
      <vxe-table
        border
        show-overflow
        keep-source
        stripe
        highlight-current-row
        highlight-hover-row
        size="mini"
        :data="tableData"
        :loading="tableload"
        :round="true"
        align="center"
        height="700px"
        ref="tablewzhentrust"
        :sort-config="{ remote: true }"
        @sort-change="sortChangeEvent"
        @cell-click="rowClick"
        @cell-dblclick="rowDbClick"
      >
        <vxe-table-column
          field="status"
          title="状态"
          :formatter="formatterStatus"
          sortable
          width="95"
        />
        <vxe-table-column field="consignNam"  width="200" title="货主名称" sortable />
        <vxe-table-column field="shipperNam"  width="200" title="委托方名称" sortable />
        <vxe-table-column field="shipName"  width="200" title="船名" :formatter="formatterShip" sortable />
        <vxe-table-column field="voyage" width="120" title="航次" sortable />
        <vxe-table-column field="cargoName" width="120" title="货名" sortable />
        <vxe-table-column field="weight" width="90" title="重量" sortable />
        <vxe-table-column field="piecesNum" width="90" title="件数" sortable />
        <vxe-table-column field="commitOpt" width="120" title="公司审核人" sortable />
        <vxe-table-column field="consignOpt" width="120" title="货主审核人" sortable />
        <vxe-table-column field="shipperOpt" width="120" title="委托方审核人" sortable />
        <vxe-table-column field="createTime" width="120" title="创建时间" sortable />
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
      <vxe-modal
        v-if="dialogFormVisibleedit"
        v-model="dialogFormVisibleedit"
        title="维护"
        width="700"
        height="800"
        resize
        storage
      >
        <Edit
          :datanow="dataNow"
          @dictItemVisible="dictItemVisible"
          @selectList="selectTable"
        />
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import { fetchList, commitBean, backBean } from "@/api/wzh/wzhentrust";
import { mapGetters } from "vuex";
import EditDown from "@/views/baseUtile/EditDown";
import Edit from "./edit.vue";

export default {
  components: { EditDown, Edit },
  data() {
    return {
      formObj: {},
      tableData: [],
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      tableload: false,
      dialogFormVisibleedit: false,
      dataNow: {}
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() {
    this.selectTable();
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
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    selectTable() {
      this.tablePage.currentPage = 1;
      this.getDate();
    },
    resetForm() {},
    sortChangeEvent({ column, property, order }) {
      this.formObj.sqlSortNameOfMrLI = property;
      this.formObj.sqlSortOfMrLI = order;
      this.selectTable();
    },
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
      this.dataNow = null;
    },
    rowClick({ row }) {
      //单击表格
      this.dataNow = row;
    },
    rowDbClick({ row }) {
      //双击表格
    },
    commitData() {
      if (this.dataNow == null) {
        this.$message.error("请选择一个数据进行审核");
      }
      commitBean(this.dataNow.pkid)
        .then(response => {
          this.$message.error("审核成功");
          this.getDate();
       }).catch(() => {});
    },
    backData() {
      if (this.dataNow == null) {
        this.$message.error("请选择一个数据进行回退");
      }
      backBean(this.dataNow.pkid)
        .then(response => {
          this.$message.error("回退成功");
          this.getDate();
       }).catch(() => {});
    },
    formatterStatus({ cellValue }) {
      if (cellValue == 0) {
        return "待提交";
      } else if (cellValue == 1) {
        return "已审核";
      } else if (cellValue == 2) {
        return "已回退";
      }
    },
    formatterShip({row, cellValue }){
      return  row.shipNameE+"("+row.shipName+")"+row.shipNo;
    },
  }
};
</script>
<style lang="scss" scoped>
.wzh-wzhentrust-index {
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