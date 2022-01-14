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
  <div class="portside-yqbak-person">
    <basic-container>
      <vxe-form :data="formObj">
        <vxe-form-item title="姓名" field="companyName" size="mini" span="3">
          <vxe-input
            v-model="formObj.companyName"
            clearable
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="center">
          <vxe-button
            status="primary"
            icon="fa fa-search"
            @click="selectTable()"
            >查询</vxe-button
          >
          <vxe-button type="reset" icon="fa fa-repeat" @click="resetForm">重置</vxe-button>
        </vxe-form-item>
      </vxe-form>
      <vxe-toolbar custom perfect size="mini">
        <template v-slot:buttons>
            <vxe-button  v-if="!ifSelectAll" type="text" class="fa fa-plus" @click="add()"
            >新增</vxe-button
          >
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
        ref="tableCar"
        :sort-config="{remote:true}"
         @sort-change="sortChangeEvent"
      >
       <vxe-table-column field="companyName" title="公司名称" width="200" sortable></vxe-table-column>
        <vxe-table-column field="personName" title="姓名" sortable></vxe-table-column>
         <vxe-table-column field="personSex" title="性别" sortable formatter="formatSex"></vxe-table-column>
          <vxe-table-column field="personJob" title="岗位" sortable></vxe-table-column>
          <vxe-table-column field="aboardBak" title="登船事由" sortable></vxe-table-column>
          <vxe-table-column field="personId" title="身份证号" sortable></vxe-table-column>
          <vxe-table-column field="personPhone" title="联系方式" sortable></vxe-table-column>
           <vxe-table-column field="personHomePlace" title="现住址" sortable></vxe-table-column>
           <vxe-table-column field="personSxblx" title="上下班路线" sortable></vxe-table-column>
           <vxe-table-column field="personJzjzd" title="集中居住地" sortable></vxe-table-column>
           <vxe-table-column field="gkzt" title="管控状态" sortable></vxe-table-column>
           <vxe-table-column field="ifJz" title="是否接种新冠疫苗" :formatter="formatterIfJz" sortable></vxe-table-column>
           <vxe-table-column title="操作" width="100" show-overflow>
          <template v-slot="{ row }">
            <vxe-button
              v-if="!ifSelectAll"
              type="text"
              icon="fa fa-edit"
              @click="edit(row)"
            ></vxe-button>
          </template>
        </vxe-table-column>
        </vxe-table-colgroup>
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
        title="基本信息维护"
        width="1000"
        height="900"
        resize
        storage
      >
        <PersonEdit
          :datanow="dataNow"
          @dictItemVisible="dictItemVisible"
          @selectList="selectTable"
        ></PersonEdit>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import { personList } from "@/api/portside/yqbak";
import { mapGetters } from "vuex";
import PersonEdit from "./personEdit";
import EditDown from "../../baseUtile/EditDown";
export default {
  components: { EditDown, PersonEdit },
  name: "portside-yqbak-person",
  data() {
    return {
      formObj: {
        unitName: null,
        unitCod: null,
        shipNo: null,
        shipNam: null,
        selectTime: null
      },
      dialogFormVisibleedit: false,
      tableData: [],
      dataNow: {},
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      tableload: false
    };
  },
  computed: {
    ...mapGetters(["userInfo", "permissions"])
  },
  created() {
        this.ifSelectAll = this.permissions["DCZYRYBA_BAK_CXSY"];
    this.getDate();
  },
  methods: {
    getDate() {
      this.tableload = true;
      personList(
        Object.assign(
          {
            current: this.tablePage.currentPage,
            size: this.tablePage.pageSize,
            ifSelectAll:this.ifSelectAll
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
    add() {
      this.dataNow = null;
      this.dialogFormVisibleedit = true;
    },
    edit(row) {
      this.dataNow = row;
      this.dialogFormVisibleedit = true;
    },
    delete() {},
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
      this.dataNow = null;
    },
    sortChangeEvent({ column, property, order }) {
      this.formObj.sqlSortNameOfMrLI = property;
      this.formObj.sqlSortOfMrLI = order;
      this.selectTable();
    },
    formatterIfJz({ cellValue }) {
      if (cellValue == 1) {
        return "是";
      } else {
        return "否";
      }
    }
  }
};
</script>
<style lang="scss" scoped>
.portside-yqbak-person {
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