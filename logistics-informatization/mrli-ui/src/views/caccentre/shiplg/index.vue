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
        <vxe-form-item title="船号：" field="shipNo" size="mini" span="6">
          <EditDown
            :objs="formObj"
            :property="downTableOption.shipNo"
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
      <vxe-toolbar perfect size="mini">
        <template v-slot:buttons>
          <vxe-button type="text" icon="fa fa-floppy-o" @click="saveAll()"
            >保存</vxe-button
          >
           <vxe-button type="text" icon="fa fa-trash" @click="del()"
            >删除船动态</vxe-button
          >
        </template>
      </vxe-toolbar>
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
            @cell-click="rowclickShip"
            :edit-config="{ trigger: 'click', mode: 'cell', showStatus: true }"
          >
            <vxe-table-column
              field="callNo"
              title="呼号"
              width="8%"
              fixed="left"
            ></vxe-table-column>
            <vxe-table-column
              field="shipNo"
              title="船号"
              width="8%"
              fixed="left"
            ></vxe-table-column>
            <vxe-table-column
              field="shipNam"
              title="船名"
              width="9%"
              fixed="left"
            ></vxe-table-column>
            <vxe-table-column
              field="arivportId"
              title="船舶状态"
              width="9%"
              fixed="left"
              :edit-render="{ name: '$select', options: ztList }"
            ></vxe-table-column>
            <vxe-table-column
              field="levRecordTim"
              title="离港记录时间"
              width="12%"
              fixed="left"
              :edit-render="{
                name: '$input',
                props: { type: 'datetime', clearable: true },
              }"
            ></vxe-table-column>
            <vxe-table-column
              field="levPortTim"
              title="离港时间"
              width="12%"
              fixed="left"
              :edit-render="{
                name: '$input',
                props: { type: 'datetime', clearable: true },
              }"
            ></vxe-table-column>
            <vxe-table-column
              field="unitNam"
              title="单位"
              width="15%"
            ></vxe-table-column>
            <vxe-table-column
              field="berthCod"
              title="泊位"
              width="8%"
            ></vxe-table-column>
            <vxe-table-column
              field="ccountryNam"
              title="船籍"
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
              field="perPortNam"
              title="上一港"
              width="8%"
            ></vxe-table-column>
            <vxe-table-column
              field="fromPortNam"
              title="来港"
              width="8%"
            ></vxe-table-column>
            <vxe-table-column
              field="toPortNam"
              title="往港"
              width="8%"
            ></vxe-table-column>
            <vxe-table-column
              field="tradeId"
              title="内外贸"
              width="8%"
              :formatter="formatternw"
            ></vxe-table-column>
            <vxe-table-column
              field="inCargoNam"
              title="进口货种"
              width="8%"
            ></vxe-table-column>
            <vxe-table-column
              field="inShipCap"
              title="进口货重"
              width="8%"
            ></vxe-table-column>
            <vxe-table-column
              field="outCargoNam"
              title="出口货种"
              width="8%"
            ></vxe-table-column>
            <vxe-table-column
              field="outShipCap"
              title="出口货重"
              width="8%"
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
              'Total',
            ]"
            @page-change="handlePageChange"
          ></vxe-pager>
        </el-col>
      </el-row>
    </basic-container>
  </div>
</template>

<script>
import { getList, saveShip,deletShip } from "@/api/caccentre/ship";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import EditDown from "../../baseUtile/EditDown";
import Edit from "./edit";
import moment from "moment";
export default {
  components: { Edit, EditDown },
  name: "dayrecord",
  data() {
    return {
      formObj: {
        shipNo: null,
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
      ztList: [
        { label: "离港船", value: "3" },
        { label: "在港/锚地船", value: "1" },
        { label: "预报船", value: "0" },
      ],
      downTableOption: {
        //队别下拉菜单展示项
        subNamColum: [
          { field: "shipNo", title: "船号" },
          { field: "shipNam", title: "船名" },
          { field: "berthCod", title: "泊位" },
          { field: "toPortTim", title: "抵港时间" },
        ],
        //下拉菜单的路径
        subNamurl: "/caccentre/ship/getList",
        shipNo: "shipNo",
        subNamreturnApi: [{ field: "shipNo", returnName: "shipNo" }],
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
      getList(
        Object.assign({
          current: this.tablePage.currentPage,
          size: this.tablePage.pageSize,
          codeName: this.formObj.shipNo,
        })
      )
        .then((response) => {
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
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
    },
    editEvent(row) {
      this.dataNow = row;
      console.log(this.dataNow);
      this.dialogFormVisibleedit = true;
    },
    formatternw({ cellValue }) {
      if (cellValue == "1") {
        return "内贸";
      } else if (cellValue == "0") {
        return "外贸";
      }
    },
    async saveAll() {
      const errMap = await this.$refs.tableShip
        .fullValidate()
        .catch((errMap) => errMap);
      if (errMap) {
      } else {
        this.tableload = true;
        var updaterecords = this.$refs.tableShip.getRecordset().updateRecords;
        if (updaterecords.length == 0) {
          this.$message.error("没有修改的数据");
          return;
        }
        var ships = eval(updaterecords);
 
        for (var i in ships) {
          if (ships[i].arivportId == "1") {
            if (ships[i].levRecordTim != null && ships[i].levRecordTim != "") {
              this.$message.error(
                "船号" + ships[i].shipNo + "离港记录时间必须为空"
              );
              return;
            }
            if (ships[i].levPortTim != null && ships[i].levPortTim != "") {
              this.$message.error(
                "船号" + ships[i].shipNo + "离港时间必须为空"
              );
              return;
            }
          }
        }
        saveShip(updaterecords)
          .then((data) => {
            this.$message.success("保存成功");
            this.tableload = false;
            this.getDate();
          })
          .catch(() => {
            this.tableload = false;
          });
      }
    },
    rowclickShip({ row }) {
      this.dateNow = row;
    },
    del() {
      if (this.dataNow == null || this.dataNow.shipNo == null) {
        this.$message.error("请选择一条船舶进行删除");
        return;
      }
      this.$XModal.confirm("您确定要删除船舶动态数据吗？").then((type) => {
        if (type == "confirm") {
          deletShip(this.dataNow.shipNo)
            .then((response) => {
              this.$message.success("删除成功");
              this.tableload = false;
             this.getDate();
            })
            .catch(() => {this.tableload = false;});
        }
      });
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