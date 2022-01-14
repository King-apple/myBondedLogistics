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
  <div class="zyworkresourcesman">
    <basic-container>
      <vxe-form :data="formObj">
        <vxe-form-item title="时间" field="selectDate" size="mini" span="4">
          <vxe-input v-model="formObj.selectDate" type="date"  clearable></vxe-input>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="center">
          <vxe-button status="primary" icon="fa fa-search" @click="getDate()">查询</vxe-button>
        </vxe-form-item>
      </vxe-form>
      <vxe-toolbar perfect size="mini">
        <template v-slot:buttons>
          <vxe-button type="text" class="fa fa-plus" @click="add()">新增</vxe-button>
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
        height="600px"
        ref="tableMan"
        :edit-rules="validRules"
        :edit-config="{trigger: 'click', mode: 'row'}"
        :mouse-config="{selected: true}"
        :checkbox-config="{range: true}"
      >
        <vxe-table-column
          field="zyWorkDte"
          title="日期"
          :formatter="['formatDate', 'yyyy-MM-dd']"
          :edit-render="{}"
        >
          <template v-slot:edit="{ row, rowIndex }">
            <vxe-input
              type="date"
              v-model="row.zyWorkDte"
              placeholder="请选择日期"
              valueFormat="yyyy-MM-dd"
              transfer
            ></vxe-input>
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="zyZdNum"
          title="正队"
          :edit-render="{autofocus: '.vxe-input--inner'}"
        >
          <template v-slot:edit="{ row }">
            <vxe-input type="number" v-model="row.zyZdNum"></vxe-input>
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="zyFiveNum"
          title="五队"
          :edit-render="{autofocus: '.vxe-input--inner'}"
        >
          <template v-slot:edit="{ row }">
            <vxe-input type="number" v-model="row.zyFiveNum"></vxe-input>
          </template>
        </vxe-table-column>
        <vxe-table-column field="zyJNum" title="加班" :edit-render="{autofocus: '.vxe-input--inner'}">
          <template v-slot:edit="{ row }">
            <vxe-input type="number" v-model="row.zyJNum" ></vxe-input>
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="zyPNum"
          title="8：00派牌"
          :edit-render="{autofocus: '.vxe-input--inner'}"
        >
          <template v-slot:edit="{ row }">
            <vxe-input type="number" v-model="row.zyPNum"></vxe-input>
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
        :layouts="['PrevPage', 'JumpNumber', 'NextPage', 'FullJump', 'Sizes', 'Total']"
        @page-change="handlePageChange"
      ></vxe-pager>
    </basic-container>
  </div>
</template>

<script>
import { fetchList, saveAll } from "@/api/caccentre/zyworkresourcesman";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import moment from "moment";
export default {
  components: {},
  name: "zyworkresourcesman",
  data() {
    return {
      formObj: {selectDate:null},
      dialogFormVisibleedit: false,
      tableData: [],
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      tableload: false,
      validRules: {
        zyWorkDte: [{ required: true, message: "必须选择时间" }]
      }
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() {
    this.formObj.selectDate=moment(new Date()).format("YYYY-MM-DD");
    this.getDate();
  },
  methods: {
    getDate() {
      this.tableload = true;
      fetchList(
        Object.assign({
          current: this.tablePage.currentPage,
          size: this.tablePage.pageSize
        },this.formObj)
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
                zyZdNum: 0,
                zyJNum:0,
                zyPNum:0,
                zyFiveNum:0,
        }
      this.$refs.tableMan
        .insert(record)
        .then(({ row }) => this.$refs.tableMan.setActiveCell(row, "zyWorkDte"));
    },
    async save() {
      const errMap = await this.$refs.tableMan
        .fullValidate()
        .catch(errMap => errMap);
      if (errMap) {
      } else {
        let loadingInstance = Loading.service({ fullscreen: true });
        saveAll(this.$refs.tableMan.getRecordset())
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
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    }
  }
};
</script>
<style lang="scss" scoped>
.zyworkresourcesman {
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