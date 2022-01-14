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
  <div class="tkindcargo">
    <basic-container>
      <vxe-form :data="formObj" size="mini">
        <!-- <vxe-form-item title="公司：" field="unitNam" size="mini" span="6">
          <vxe-input v-model="formObj.unitNam" clearable size="mini"></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="罐容量：" field="capacity" size="mini" span="6">
          <vxe-input
            v-model="formObj.capacity"
            clearable
            size="mini"
          ></vxe-input>
        </vxe-form-item> -->
        <vxe-form-item title="统计单位：" field="unitNam" size="mini" span="6">
          <!-- <vxe-input
            v-model="formObj.unitNam"
            clearable
            size="mini"
          ></vxe-input> -->
          <EditDown
            :objs="formObj"
            :property="downTableOption.unitNam"
            :tableColumns="downTableOption.unitNamColum"
            :urlapi="downTableOption.unitNamurl"
            :returnApi="downTableOption.unitNamreturnApi"
            sizenow="mini"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item
          title="统计月份："
          field="recordDteString"
          size="mini"
          span="5"
        >
          <vxe-input
            v-model="formObj.recordDteString"
            clearable
            size="mini"
            type="month"
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="center">
          <vxe-button status="primary" icon="fa fa-search" @click="getDate()"
            >查询</vxe-button
          >
        </vxe-form-item>
      </vxe-form>
      <vxe-toolbar perfect size="mini">
        <template v-slot:buttons>
          <vxe-button type="text" class="fa fa-trash" @click="excelOut()"
            >导出EXCEL表</vxe-button
          >
        </template>
      </vxe-toolbar>
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
        @cell-dblclick="cellDBLClickEvent"
      >
        <vxe-table-column type="seq" width="60" title="序号"></vxe-table-column>
        <vxe-table-column
          field="cargoKindNam"
          title="货物分类"
          width="120"
        ></vxe-table-column>
        <!-- <vxe-table-column
          field="cargoKindNam"
          title="单位"
          width="200"
        ></vxe-table-column> -->
        <vxe-table-column title="营口港">
          <vxe-table-column
            field="totalWgt"
            title="总量"
            width="120"
          ></vxe-table-column>
          <vxe-table-column
            field="totalWgtLastYear"
            title="去年同期"
            width="120"
            :formatter="formatterType"
          ></vxe-table-column>
          <vxe-table-column
            field="totalWgtOverYear"
            title="同比"
            width="120"
            :formatter="formatterType"
          ></vxe-table-column>
          <vxe-table-column
            field="totalWgtGrowthRate"
            title="增幅"
            width="120"
            :formatter="formatterType"
          ></vxe-table-column>
          <vxe-table-column title="外贸">
            <vxe-table-column
              field="outTotWgt"
              title="小计"
              width="140"
            ></vxe-table-column>
            <vxe-table-column
              field="outForWgt"
              title="进口"
              width="140"
            ></vxe-table-column>
            <vxe-table-column
              field="outInWgt"
              title="出口"
              width="140"
            ></vxe-table-column>
          </vxe-table-column>
          <vxe-table-column title="内贸">
            <vxe-table-column
              field="inTotWgt"
              title="小计"
              width="140"
            ></vxe-table-column>
            <vxe-table-column
              field="inForWgt"
              title="进口"
              width="140"
            ></vxe-table-column>
            <vxe-table-column
              field="inInWgt"
              title="出口"
              width="140"
            ></vxe-table-column>
          </vxe-table-column>
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
          'Total',
        ]"
        @page-change="handlePageChange"
      ></vxe-pager>
    </basic-container>
  </div>
</template>

<script>
import {
  fetchList,
  // dataSummary,
  // dataUpload,
  excelOutPost,
} from "@/api/statistical/tkindcargo";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import EditDown from "../../baseUtile/EditDown";
import moment from "moment";
// import Edit from "./edit";
// import Add from "./add";
export default {
  components: { EditDown },
  name: "tkindcargo",
  data() {
    return {
      formObj: {
        unitCod: "00",
        unitNam: "营港集团",
        recordDteString: moment(new Date()).format("YYYY-MM-DD"),
      },
      editTitle: "吞吐量统计表",
      dialogFormVisibleedit: false,
      dialogFormVisibleAdd: false,
      tableData: [],
      datanow: {},
      dataHeadNow: {},
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      tableload: false,
      downTableOption: {
        //统计单位
        unitNam: "unitNam",
        unitNamColum: [
          { field: "UNIT_COD", title: "单位代码" },
          { field: "UNIT_NAM", title: "单位名称" },
        ],
        unitNamreturnApi: [
          { field: "UNIT_COD", returnName: "unitCod" },
          { field: "UNIT_NAM", returnName: "unitNam" },
        ],
        unitNamurl: "/basecode/basecod/getTjUnitCod",
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
    cellDBLClickEvent({ row }) {
      this.datanow = row;
      this.dialogFormVisibleView = true;
    },
    summary() {
      let that = this;
      this.$confirm(
        "是否确认进行【" + this.formObj.workDteString + "】数据汇总",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return dataSummary(that.formObj.workDteString);
        })
        .then((response) => {
          this.$message.success("数据汇总成功");
          this.getDate();
        });
    },
    upload() {
      let that = this;
      this.$confirm(
        "是否确认进行【" + this.formObj.workDteString + "】数据上报",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return dataUpload(that.formObj.workDteString);
        })
        .then((response) => {
          this.$message.success("数据上报成功");
          this.getDate();
        });
    },
    excelOut() {
      if (this.formObj.workDteString != null) {
        let loadingInstance = Loading.service({
          fullscreen: true,
          text: "EXCEL生成中……",
        });
        excelOutPost(this.formObj)
          .then(() => {
            loadingInstance.close();
          })
          .catch(() => {
            loadingInstance.close();
          });
      } else {
        this.$message.error("请选择统计月份");
      }
    },
    formatterType({ cellValue }) {
      if (cellValue == null) {
        return "/";
      }else {
        return cellValue;
      }
    },
    rowclick({ row }) {
      this.dataHeadNow = row;
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    editCont() {
      this.dialogFormVisibleplant = false;
    },
    closeEdit() {
      this.getDate();
      this.dialogFormVisibleedit = false;
      this.dialogFormVisibleAdd = false;
    },
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
      this.dialogFormVisibleAdd = false;
    },
  },
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