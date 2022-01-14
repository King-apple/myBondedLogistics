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
  <div class="mdayfastship">
    <basic-container>
      <vxe-form :data="formObj">
        <vxe-form-item title="时间" field="workDte" size="mini" span="4">
          <vxe-input v-model="formObj.workDte" type="date" valueFormat="yyyy/MM/dd" clearable></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="船名：" field="shipNam" size="mini" span="6">
          <vxe-input
            v-model="formObj.shipNam"
            clearable
            size="mini"
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="泊位：" field="berthCod" size="mini" span="6">
          <!-- <vxe-input
            v-model="formObj.unitNam"
            clearable
            size="mini"
          ></vxe-input> -->
          <EditDown
            :objs="formObj"
            :property="downTableOption.berthCod"
            :tableColumns="downTableOption.berthColum"
            :urlapi="downTableOption.berthurl"
            :returnApi="downTableOption.berthreturnApi"
            sizenow="mini"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="center">
          <vxe-button status="primary" icon="fa fa-search" @click="getDate()">查询</vxe-button>
          <!-- <vxe-button status="primary" icon="fa fa-trash" @click="clear()">清空</vxe-button> -->
        </vxe-form-item>
      </vxe-form>
      <el-row :gutter="15">
        <vxe-toolbar perfect size="mini">
          <template v-slot:buttons>
            <!-- <vxe-button type="text" class="fa fa-plus" @click="add()">新增</vxe-button> -->
            <vxe-button type="text" icon="fa fa-floppy-o" @click="edit()">数据处理</vxe-button>
            <!-- <vxe-button type="text" icon="fa fa-minus" @click="Del()">删除</vxe-button> -->
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
          @checkbox-change="selectChangeEvent"
        >
          <vxe-table-column type="checkbox" width="60"></vxe-table-column>
          <vxe-table-column type="seq" title="序号" width="60"></vxe-table-column>
          <!-- <vxe-table-column field="seqNo" title="序号" width="3%"></vxe-table-column> -->
          <vxe-table-column field="unitCod" title="作业单位代码" width="6%"></vxe-table-column>
          <vxe-table-column field="unitNam" title="作业单位" width="15%"></vxe-table-column>
          <vxe-table-column field="agentNam" title="代理" width="12%"></vxe-table-column>
          <vxe-table-column field="berthCod" title="泊位" width="4%"></vxe-table-column>
          <vxe-table-column field="shipNam" title="船名" width="5%"></vxe-table-column>
          <vxe-table-column field="shipLongNum" title="船长" width="6%"></vxe-table-column>
          <vxe-table-column field="unloadId" title="+/-" width="4%"></vxe-table-column>
          <vxe-table-column field="cargoNam" title="货名" width="5%"></vxe-table-column>
          <vxe-table-column field="weightWgt" title="重量" width="5%"></vxe-table-column>
          <vxe-table-column field="toPortTim" title="抵港时间" width="10%"></vxe-table-column>
          <vxe-table-column field="toBerthTim" title="靠泊时间" width="10%"></vxe-table-column>
          <vxe-table-column field="beginTim" title="开工时间" width="10%"></vxe-table-column>
          <vxe-table-column field="endTim" title="完工时间" width="10%"></vxe-table-column>
          <vxe-table-column field="levPortTim" title="离港时间" width="10%"></vxe-table-column>
          <vxe-table-column field="workWgt" title="日完成" width="5%"></vxe-table-column>
          <vxe-table-column field="shipWorkWgt" title="累计量" width="8%"></vxe-table-column>
          <vxe-table-column field="remarkTxt" title="备注" width="16%"></vxe-table-column>
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
      </el-row>
    </basic-container>
  </div>
</template>

<script>
import {
  fetchList,
  delObj,
  updateCkid,
  dealData,
} from "@/api/caccentre/mdayfastship";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import EditDown from "../../baseUtile/EditDown";
import moment from "moment";
// import EditDown from "../../baseUtile/EditDown";
export default {
  components: {EditDown},
  name: "mdayfastship",
  data() {
    return {
      formObj: {
        berthCod:"",
        workDte: moment(new Date()).format("YYYY/MM/DD"),
      },
      dataHeadNow: {},
      dataNext: {},
      tableData: [],
downTableOption: {
        berthCod:'berthCod',
        //泊位下拉菜单展示项
        berthColum: [
          { field: "BERTH_COD", title: "泊位代码" },
          { field: "BERTH_NAM", title: "泊位名称" }
          // { field: "UNIT_NAM", title: "公司名称" }
        ],
        //泊位下拉菜单的返回项
        berthreturnApi: [
          { field: "BERTH_COD", returnName: "berthCod" } 
        ],
        //泊位下拉菜单的路径
        berthurl: "/basecode/basecod/getBerth",
      },
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      tableload: false,
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
          for (var i in this.tableData) {
            if (this.tableData[i].ckId == 1) {
              this.$refs.tableOne.setCheckboxRow([this.tableData[i]], true);
            }
          }
          this.tableload = false;
        })
        .catch(() => {
          this.tableload = false;
        });
    },
    selectChangeEvent({ checked, records, row }) {
      console.log(row);
      console.log(checked);
      // var ifCheck;
      // if(checked){
      //   ifcheck="1"
      // }else{
      //   ifcheck=""
      // }
      updateCkid({
        ckId: checked ? "1" : "",
        workDte: row.workDte,
        unitCod: row.unitCod,
        shipNo: row.shipNo,
        unloadId: row.unloadId,
      })
        .then((data) => {})
        .catch(() => {
          // loading();
        });
    },
    rowclick({ row }) {
      this.dataHeadNow = row;
    },
    add() {
      this.dataNext = {};
      this.dialogFormVisibleedit = true;
    },
    edit() {
      let that = this;
      this.$confirm("是否确认处理数据", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(function () {
        dealData(that.formObj.workDte)
          .then((response) => {
            this.$message.success("处理成功");
            this.getDate();
          })
          .catch(() => {
            this.$message.success("处理失败");
          });
      });
    },
    Del() {
      let that = this;
      if (this.dataHeadNow != null && this.dataHeadNow.seqNo != null) {
        this.$confirm("是否确认删除", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(function () {
            return delObj(that.dataHeadNow.seqNo);
          })
          .then((data) => {
            this.$message.success("删除成功");
            this.getDate(this.page);
          });
      } else {
        this.$message.success("请选择一条记录");
      }
    },
    async save() {},
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
  },
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