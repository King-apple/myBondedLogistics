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
  <div class="dayrecord">
    <basic-container>
      <vxe-form :data="formObj" size="mini">
        <vxe-form-item title="呼号：" field="callNo" size="mini" span="6">
          <EditDown
            :objs="formObj"
            :property="downTableOption.callNo"
            :tableColumns="downTableOption.subNamColum"
            :urlapi="downTableOption.subNamurl"
            :returnApi="downTableOption.subNamreturnApi"
            sizenow="mini"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item span="6" size="mini" align="center">
          <vxe-button status="primary" icon="fa fa-search" @click="getDate()"
            >查询</vxe-button
          >
        </vxe-form-item>
      </vxe-form>

      <el-row>
        <el-col :span="24">
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
            height="550px"
            ref="tableShip"
          >
            <vxe-table-column
              field="callNo"
              title="呼号"
              width="8%"
            ></vxe-table-column>
            <vxe-table-column
              field="cshipNam"
              title="中文船名"
              width="9%"
            ></vxe-table-column>
            <vxe-table-column
              field="eshipNam"
              title="英文船名"
              width="9%"
            ></vxe-table-column>
            <vxe-table-column
              field="ccountryNam"
              title="船籍"
              width="9%"
            ></vxe-table-column>
            <vxe-table-column
              field="shipOwnerNam"
              title="船主"
              width="9%"
            ></vxe-table-column>
            <vxe-table-column
              field="shipTypNam"
              title="船型"
              width="8%"
            ></vxe-table-column>
            <vxe-table-column
              field="totalWgt"
              title="总吨"
              width="8%"
            ></vxe-table-column>
            <vxe-table-column
              field="netWgt"
              title="净吨"
              width="8%"
            ></vxe-table-column>
            <vxe-table-column
              field="shipCap"
              title="载重吨"
              width="8%"
            ></vxe-table-column>
            <vxe-table-column
              field="shipLongNum"
              title="船长"
              width="8%"
            ></vxe-table-column>
            <vxe-table-column
              field="shipWideNum"
              title="船宽"
              width="8%"
            ></vxe-table-column>
            <vxe-table-column title="操作" width="8%" show-overflow>
              <template v-slot="{ row }">
                <vxe-button
                  type="text"
                  icon="fa fa-bolt"
                  @click="editEvent(row)"
                  >查看</vxe-button
                >
              </template>
            </vxe-table-column>
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
              'Total',
            ]"
            @page-change="handlePageChange"
          ></vxe-pager>
        </el-col>
      </el-row>
      <vxe-modal
        v-if="dialogFormVisibleedit"
        v-model="dialogFormVisibleedit"
        title="信息查看"
        width="1000"
        height="700"
        resize
        storage
      >
        <Edit :datanow="dataNow" @dictItemVisible="dictItemVisible" @selectList="getDate"></Edit>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import {
  fetchList,
  getObj,
  addObj,
  putObj,
  delObj,
} from "@/api/caccentre/shipdata";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import EditDown from "../../baseUtile/EditDown";
import Edit from "./edit";
import moment from "moment";
export default {
  components: { Edit,EditDown },
  name: "dayrecord",
  data() {
    return {
      formObj: {
        callNo: null,
      },
      dialogFormVisibleedit: false,
      tableDataShip: [],
      tableloadShip: false,
      dataNow: {},
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      downTableOption: {
        //队别下拉菜单展示项
        subNamColum: [
          { field: "callNo", title: "呼号" },
          { field: "cshipNam", title: "中文船名" },
           { field: "eshipNam", title: "英文船名" },
        ],
        //下拉菜单的路径
        subNamurl: "/caccentre/shipdata/page",
        callNo: "callNo",
        subNamreturnApi: [
          { field: "callNo", returnName: "callNo" } 
        ],
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
        
      this.tableloadShip = true;
      fetchList(
        Object.assign(
          {
            current: this.tablePage.currentPage,
            size: this.tablePage.pageSize,
            codeName: this.formObj.callNo,
          } 
        )
      )
        .then((response) => {
          this.tableDataShip = response.data.data.records;;
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
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
    },
    editEvent(row) {        
          this.dataNow = row;
          console.log(this.dataNow);
          this.dialogFormVisibleedit = true;         
        
    },
  },
};
</script>
<style lang="scss" scoped>
.dayrecord {
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