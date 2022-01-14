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
  <div class="vslstowageplan">
    <basic-container>
      <vxe-form :data="formObj" size="mini">
        <!-- <vxe-form-item title="统计单位：" field="unitNam" size="mini" span="6">
          <EditDown
            :objs="formObj"
            :property="downTableOption.unitNam"
            :tableColumns="downTableOption.unitNamColum"
            :urlapi="downTableOption.unitNamurl"
            :returnApi="downTableOption.unitNamreturnApi"
            sizenow="mini"
          ></EditDown>
        </vxe-form-item> -->
        <vxe-form-item title="船号：" field="shipNo" size="mini" span="6">
          <!-- <vxe-input
            v-model="formObj.shipNo"
            clearable
            size="mini"
          ></vxe-input> -->
          <EditDown
            :objs="formObj"
            :property="downTableOption.shipNo"
            :tableColumns="downTableOption.shipNoColum"
            :urlapi="downTableOption.shipNourl"
            :returnApi="downTableOption.shipNoreturnApi"
            sizenow="mini"
          ></EditDown>
        </vxe-form-item>
        <!-- <vxe-form-item
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
        </vxe-form-item> -->
        <vxe-form-item span="2" size="mini" align="center">
          <vxe-button status="primary" icon="fa fa-search" @click="getDate()"
            >查询</vxe-button
          >
        </vxe-form-item>
      </vxe-form>
      <vxe-toolbar perfect size="mini">
        <template v-slot:buttons>
          <vxe-button type="text" class="fa fa-sliders" @click="add('add')"
            >新增</vxe-button
          >
          <vxe-button type="text" class="fa fa-trash" @click="add('edit')"
            >修改</vxe-button
          >
          <vxe-button type="text" class="fa fa-trash" @click="add('detail')"
            >详情</vxe-button
          >
          <vxe-button type="text" class="fa fa-male" @click="toDisable()"
            >作废</vxe-button
          >
        </template>
      </vxe-toolbar>
      <el-row>
        <el-col :span="24">
          <el-divider content-position="left">配载计划</el-divider>
        </el-col>
        <el-col :span="24">
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
            height="250px"
            ref="table"
            @cell-click="rowclick"
            @cell-dblclick="cellDBLClickEvent"
          >
            <vxe-table-column
              type="seq"
              width="60"
              title="序号"
            ></vxe-table-column>
            <vxe-table-column
              field="shipNo"
              title="船号"
              width="150"
            ></vxe-table-column>
            <vxe-table-column
              field="shipNam"
              title="船名"
              width="80"
            ></vxe-table-column>
            <vxe-table-column
              field="agentNam"
              title="船公司"
              width="200"
            ></vxe-table-column>
            <vxe-table-column
              field="tradeId"
              title="内外贸"
              width="150"
              :formatter="formatternw"
            ></vxe-table-column>
            <vxe-table-column
              field="inoutId"
              title="进出口"
              width="80"
              :formatter="formatterjc"
            ></vxe-table-column>
            <vxe-table-column
              field="unloadId"
              title="装卸别"
              width="150"
              :formatter="formatterzx"
            ></vxe-table-column>
            <vxe-table-column
              field="hatchNum"
              title="舱口数"
              width="80"
            ></vxe-table-column>
            <vxe-table-column
              field="berthCod"
              title="泊位"
              width="150"
            ></vxe-table-column>
            <vxe-table-column
              field="unitNam"
              title="生产公司"
              width="250"
            ></vxe-table-column>
            <vxe-table-column
              field="status"
              title="状态"
              width="150"
              :formatter="formatterzt"
            ></vxe-table-column>
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
        </el-col>
        <el-col :span="24">
          <el-divider content-position="left">配载计划明细</el-divider>
        </el-col>
        <vxe-toolbar perfect size="mini">
          <template v-slot:buttons>
            <vxe-button type="text" icon="fa fa-floppy-o" @click="saveAll()"
              >保存</vxe-button
            >
          </template>
        </vxe-toolbar>
        <el-col :span="24">
          <vxe-table
            border
            show-overflow
            keep-source
            highlight-current-row
            size="mini"
            :data="tableMxData"
            :loading="tableMxload"
            :round="true"
            align="center"
            height="300px"
            ref="tableMx"
            :edit-config="{ trigger: 'click', mode: 'cell', showStatus: true }"
          >
            <vxe-table-column
              type="seq"
              width="60"
              title="序号"
            ></vxe-table-column>
            <vxe-table-column
              field="hatchNo"
              title="舱号"
              width="100"
              sortable
              :filters="[{ data: '' }]"
              :filter-method="filterHatchNoMethod"
            >
              <template #filter="{ $panel, column }">
                <select
                  class="my-select"
                  v-model="option.data"
                  v-for="(option, index) in column.filters"
                  :key="index"
                  @change="$panel.changeOption($event, !!option.data, option)"
                >
                  <option
                    v-for="(label, cIndex) in hatchNoList"
                    :key="cIndex"
                    :value="label"
                  >
                    {{ label }}
                  </option>
                </select>
              </template>
            </vxe-table-column>
            <vxe-table-column
              field="trustMan"
              title="作业委托人"
              width="120"
            ></vxe-table-column>
            <vxe-table-column
              field="shipperDoc"
              title="港口货主"
              width="120"
            ></vxe-table-column>
            <vxe-table-column
              field="contNo"
              title="合同号"
              width="100"
            ></vxe-table-column>
            <vxe-table-column
              field="billNo"
              title="提单号"
              width="280"
            ></vxe-table-column>
            <vxe-table-column
              field="cargoNam"
              title="货名"
              width="150"
            ></vxe-table-column>
            <vxe-table-column
              field="piecesNum"
              title="件数"
              width="80"
              :edit-render="{ name: '$input', props: { type: 'int' } }"
            ></vxe-table-column>
            <vxe-table-column
              field="weightWgt"
              title="重量"
              width="150"
              :edit-render="{
                name: '$input',
                props: { type: 'float', digits: 3 },
              }"
            ></vxe-table-column>
            <vxe-table-column
              field="cargoVol"
              title="体积"
              width="60"
            ></vxe-table-column>
            <vxe-table-column
              field="subunitNam"
              title="保管单位"
              width="150"
            ></vxe-table-column>
            <vxe-table-column
              field="workPlaceNam"
              title="作业地点"
              width="150"
            ></vxe-table-column>
            <vxe-table-column
              field="checkId"
              title="是否过磅"
              width="80"
              :formatter="formattergb"
            ></vxe-table-column>
            <vxe-table-column
              field="checkNo"
              title="衡重申请单号"
              width="100"
            ></vxe-table-column>
            <vxe-table-column
              field="poundNo"
              title="计划磅房号"
              width="80"
            ></vxe-table-column>
          </vxe-table>
        </el-col>
      </el-row>
      <vxe-modal
        v-if="dialogFormVisibleAdd"
        v-model="dialogFormVisibleAdd"
        title="装卸船理货配载计划"
        width="600"
        height="600"
        resize
        storage
        fullscreen
        @close="closeEdit()"
      >
        <Add
          :datanow="datanow"
          @dictItemVisible="dictItemVisible"
          @selectListplan="getDate"
        ></Add>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleedit"
        v-model="dialogFormVisibleedit"
        title="装卸船理货配载计划"
        width="600"
        height="600"
        resize
        storage
        fullscreen
        @close="closeEdit()"
      >
        <Edit
          :datanow="datanow"
          @dictItemVisible="dictItemVisible"
          @selectListplan="getDate"
        ></Edit>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibledetail"
        v-model="dialogFormVisibledetail"
        title="装卸船理货配载计划"
        width="600"
        height="600"
        resize
        storage
        fullscreen
        @close="closeEdit()"
      >
        <Detail
          :datanow="datanow"
          @dictItemVisible="dictItemVisible"
          @selectListplan="getDate"
        ></Detail>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import {
  fetchList,
  ListB,
  deleteObj,
  checkPlanBody,
  saveb,
  // dataSummary,
  // dataUpload,
  // excelOutPost,
} from "@/api/sixspecial/stowageplan";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import XEUtils from "xe-utils";
import EditDown from "../../baseUtile/EditDown";
import moment from "moment";
import Edit from "./edit";
import Add from "./add";
import Detail from "./detail";
// import Add from "./add";
export default {
  components: { Add, Edit, EditDown, Detail },
  name: "tkindcargo",
  data() {
    return {
      formObj: {
        shipNo: null,
        // shipNam:null,
        // unitCod: "00",
        // unitNam: "营港集团",
        // recordDteString: moment(new Date()).format("YYYY-MM-DD"),
      },
      editTitle: "吞吐量统计表",
      dialogFormVisibleedit: false,
      dialogFormVisibleAdd: false,
      dialogFormVisibledetail: false,
      tableData: [],
      tableMxData: [],
      datanow: {},
      dataHeadNow: {},
      hatchNoList: ["", 1, 2, 3, 4, 5, 6, 7, 8],
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      tableload: false,
      tableMxload: false,
      downTableOption: {
        //统计单位
        shipNo: "shipNo",
        shipNoColum: [
          { field: "SHIP_NO", title: "船号" },
          { field: "SHIP_NAM", title: "船名" },
        ],
        shipNoreturnApi: [
          { field: "SHIP_NO", returnName: "shipNo" },
          // { field: "SHIP_NAM", returnName: "shipNam" },
        ],
        shipNourl: "/basecode/basecod/getShipNo",
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
          this.dataHeadNow = response.data.data.records[0];
          this.$refs.table.setCurrentRow(this.tableData[0]);
          this.getBDate();
        })
        .catch(() => {
          this.tableload = false;
        });
    },
    getBDate() {
      this.tableMxload = true;
      if (
        this.dataHeadNow == {} ||
        this.dataHeadNow == null ||
        this.dataHeadNow.pkid == "" ||
        this.dataHeadNow.pkid == null
      ) {
        this.tableMxData = [];
        this.tableMxload = false;
      } else {
        ListB(this.dataHeadNow.pkid)
          .then((response) => {
            this.tableMxData = response.data.data;
            this.tableMxload = false;
          })
          .catch(() => {
            this.tableMxload = false;
          });
      }
    },
    cellDBLClickEvent({ row }) {
      if (row != null && row.pkid != null) {
        // this.editTitle = this.dataHeadNow.contNo + "合同修改";
        this.datanow = row;
        this.dialogFormVisibledetail = true;
      } else {
        this.$message.error("请选择一个数据进行查看");
      }
      // this.datanow = row;
      // this.dialogFormVisibleView = true;
    },
    add(type) {
      if (type === "edit") {
        if (this.dataHeadNow != null && this.dataHeadNow.pkid != null) {
          // checkPlanBody(this.dataHeadNow.pkid)
          //   .then((response) => {
          //     this.tableMxData = response.data.data;
          //     if (this.tableMxData.length > 0) {
          //       this.$message.error(
          //         "当前配载计划已生成作业通知单，不能修改，请在作业通知单中追加"
          //       );
          //       return;
          //     } else {
          this.datanow = this.dataHeadNow;
          this.dialogFormVisibleedit = true;
          //   }
          // })
          // .catch(() => {
          //   this.$message.error("校验失败，请联系管理员");
          //   return;
          // });
          // if (this.dataHeadNow.zt != 1) {
          //   this.$message.error("当前状态无法修改合同");
          //   return;
          // }
          // this.editTitle = this.dataHeadNow.contNo + "合同修改";
        } else {
          this.$message.error("请选择一个数据进行修改");
        }
      }
      if (type === "detail") {
        if (this.dataHeadNow != null && this.dataHeadNow.pkid != null) {
          // this.editTitle = this.dataHeadNow.contNo + "合同修改";
          this.datanow = this.dataHeadNow;
          this.dialogFormVisibledetail = true;
        } else {
          this.$message.error("请选择一个数据进行查看");
        }
      } else if (type === "add") {
        this.dateNow = {};
        this.dialogFormVisibleAdd = true;
      }
    },
    toDisable() {
      let that = this;
      if (this.dataHeadNow != null && this.dataHeadNow.pkid != null) {
        if (this.dataHeadNow.status == "1") {
          this.$confirm("是否确认作废", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
            .then(function () {
              console.log(1);
              return deleteObj(that.dataHeadNow);
            })
            .then((response) => {
              this.$message.success("作废成功");
              this.getDate();
            });
        } else {
          this.$message.error("当前状态不能作废");
        }

        // deleteObj(this.dataHeadNow)
        //   .then((response) => {
        //     this.$message.success("作废成功");
        //     this.getDate();
        //   })
        //   .catch(() => {});
      } else {
        this.$message.error("请选择一个进行作废");
      }
    },
    async saveAll() {
      const errMap = await this.$refs.tableMx
        .fullValidate()
        .catch((errMap) => errMap);
      if (errMap) {
      } else {
        this.tableMxload = true;
        var updaterecords = this.$refs.tableMx.getRecordset().updateRecords;
        if (updaterecords.length == 0) {
          this.$message.error("没有修改的数据");
          return;
        }
        saveb(updaterecords)
          .then((data) => {
            this.$message.success("保存成功");
            this.tableMxload = false;
            this.getBDate();
          })
          .catch(() => {
            this.tableMxload = false;
          });
      }
    },
    formatterType({ cellValue }) {
      if (cellValue == null) {
        return "/";
      } else {
        return cellValue + "%";
      }
    },
    formatternw({ cellValue }) {
      if (cellValue == "1") {
        return "内贸";
      } else if (cellValue == "0") {
        return "外贸";
      }
    },
    formatterjc({ cellValue }) {
      if (cellValue == "1") {
        return "进口";
      } else if (cellValue == "0") {
        return "出口";
      }
    },
    formatterzx({ cellValue }) {
      if (cellValue == "-") {
        return "卸";
      } else if (cellValue == "+") {
        return "装";
      }
    },
    formattergb({ cellValue }) {
      if (cellValue == "0") {
        return "否";
      } else if (cellValue == "1") {
        return "是";
      }
    },
    formatterzt({ cellValue }) {
      if (cellValue == "0") {
        return "作废";
      } else if (cellValue == "1") {
        return "有效";
      } else if (cellValue == "3") {
        return "完工";
      }
    },
    rowclick({ row }) {
      this.dataHeadNow = row;
      this.getBDate();
    },
    filterHatchNoMethod({ option, row }) {
      return row.hatchNo === option.data;
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
      this.dialogFormVisibledetail = false;
      this.dialogFormVisibleAdd = false;
    },
  },
};
</script>
<style lang="scss" scoped>
.my-select {
  margin: 10px;
  width: 100px;
  height: 32px;
}
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