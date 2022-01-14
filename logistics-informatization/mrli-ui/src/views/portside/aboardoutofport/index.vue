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
  <div class="portside-aboardpersion-index">
    <basic-container>
      <vxe-form :data="formObj">
        <vxe-form-item title="申请时间" field="selectTime" size="mini" span="5">
          <vxe-input
            v-model="formObj.selectTime"
            type="date"
            clearable
          ></vxe-input>
           </vxe-form-item>
           <vxe-form-item title="姓名" field="personName" size="mini" span="5">
          <vxe-input
            v-model="formObj.personName"
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
          <vxe-button type="reset" icon="fa fa-repeat">重置</vxe-button>
        </vxe-form-item>
      </vxe-form>
      <vxe-toolbar perfect size="mini">
        <template v-slot:buttons>
             <vxe-button
                type="text"
                class="fa fa-plus"
                @click="commitSh()"
                >审核</vxe-button
              >
              <vxe-button
                type="text"
                class="fa fa-plus"
                @click="viewFj()"
                >附件查看</vxe-button
              >
        </template>
      </vxe-toolbar>
      <vxe-table
       border
            show-overflow
            keep-source
            highlight-hover-row
            highlight-current-row
        size="mini"
        :data="tableData"
        :loading="tableload"
        :round="true"
        align="center"
        height="700px"
        ref="tableCar"
        :cell-style="cellStyle" 
         @cell-click="rowclick"
      >
        <vxe-table-column field="shipName" title="船名" ></vxe-table-column>
        <vxe-table-column field="shipNo" title="船号" ></vxe-table-column>
        <vxe-table-column field="personName" title="人名"></vxe-table-column>
        <vxe-table-column field="personId" title="身份证号"></vxe-table-column>
        <vxe-table-column field="personPhone" title="联系方式"></vxe-table-column>
        <vxe-table-column field="aboardBak" title="上船理由"></vxe-table-column>
        <vxe-table-column field="status" title="状态" :formatter="formatterStauts"></vxe-table-column>
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
        v-if="dialogFormVisibleFj"
        v-model="dialogFormVisibleFj"
        title="附件"
        width="800"
        height="800"
        resize
        storage
      >
      <div align="center">
       <el-row>
            <el-col :span="4">
               告知书
            </el-col>
            <el-col :span="20">
              <img
               v-if="model.imageUrlGzs != null"
            :src="model.imageUrlGzs"
            class="avatar"
              id="urlJzzmId"
               width="300" 
               height="300"
                @dblclick="openUrl(model.imageUrlGzs)"
              />
            </el-col>
       </el-row>
       <el-row>
            <el-col :span="4">
               登船证明
            </el-col>
            <el-col :span="20">
              <img
               v-if="model.imageUrlDcz != null"
            :src="model.imageUrlDcz"
            class="avatar"
              id="urlJzzmId"
                width="300" 
               height="300"
                 @dblclick="openUrl(model.imageUrlDcz)"
              />
            </el-col>
       </el-row>
       <el-row>
            <el-col :span="4">
               接种证明
            </el-col>
            <el-col :span="20">
              <img
               v-if="model.imageUrlJzzm != null"
            :src="model.imageUrlJzzm"
            class="avatar"
              id="urlJzzmId"
                width="300" 
               height="300"
               @dblclick="openUrl(model.imageUrlJzzm)"
              />
            </el-col>
       </el-row>
       <el-row>
            <el-col :span="4">
               核算检测证明
            </el-col>
            <el-col :span="20">
              <img
               v-if="model.imageUrlHsjczm != null"
            :src="model.imageUrlHsjczm"
            class="avatar"
              id="urlJzzmId"
               width="300" 
               height="300"
                @dblclick="openUrl(model.imageUrlHsjczm)"
              />
            </el-col>
       </el-row>
       <el-row >
         <el-col>
          <el-button type="primary" size="mini" icon="el-icon-check" @click="closeFj"
          >关闭</el-button>
           </el-col>
       </el-row>
      </div>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import { fetchShList, sh } from "@/api/portside/aboard";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import EditDown from "../../baseUtile/EditDown";
export default {
  components: { EditDown },
  name: "portside-aboardpersion-index",
  data() {
    return {
      formObj: {},
      dialogFormVisibleedit: false,
      tableData: [],
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      tableload: false,
      dialogFormVisibleFj: false,
      model: {},
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
    this.getDate();
  },
  methods: {
    getDate() {
      this.tableload = true;
      this.model = null;
      fetchShList(
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
    rowclick({ row }) {
      this.model = row;
    },
    commitSh() {
      if (this.model == null || this.model.pkid == null) {
        this.$message.error("请选择一条申请进行审核");
        return;
      }
      sh(this.model.pkid)
        .then(data => {
          this.$message.success("审核成功");
          this.getDate();
        })
        .catch(() => {});
    },
    resetForm() {},
    formatterStauts({ cellValue }) {
      if (cellValue === 1) {
        return "待审核";
      } else if (cellValue === 2) {
        return "已审核";
      }
    },
    cellStyle({ row, rowIndex, column, columnIndex }) {
      if (column.property === "status") {
        if (row.status === 1) {
          return {
            color: "green"
          };
        } else if (row.status === 2) {
          return {
            color: "red"
          };
        }
      }
    },
    viewFj() {
      if (this.model == null || this.model.pkid == null) {
        this.$message.error("请选择一条申请进行查看");
        return;
      }
      this.dialogFormVisibleFj = true;
    },
    closeFj() {
      this.dialogFormVisibleFj = false;
    },
    openUrl(url) {
      var myWindow = window.open(url, "", "width=600,height=700");
      myWindow.document.write(
        "<img src=" + url + " width='100%' height='100%'/>"
      );
      myWindow.focus();
    }
  }
};
</script>
<style lang="scss" scoped>
.portside-aboardpersion-index {
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