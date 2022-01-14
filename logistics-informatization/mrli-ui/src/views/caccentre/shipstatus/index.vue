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
  <div class="caccentre-shipstatus-index">
    <basic-container>
      <vxe-form :data="formObj" size="mini">
        <vxe-form-item title="船名：" field="shipNam" size="mini" span="4">
          <vxe-input v-model="formObj.shipNam" clearable size="mini"></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="船号：" field="shipNo" size="mini" span="4">
          <vxe-input v-model="formObj.shipNo" clearable size="mini"></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="船舶状态：" field="statusQ" size="mini" span="4">
          <vxe-select v-model="formObj.statusQ" clearable size="mini">
            <vxe-option value="0" label="预报船舶"></vxe-option>
            <vxe-option value="1" label="锚地船舶"></vxe-option>
            <vxe-option value="2" label="在港船舶"></vxe-option>
            <vxe-option value="3" label="离港船舶"></vxe-option>
          </vxe-select>
        </vxe-form-item>

        <vxe-form-item span="4" size="mini" align="center">
          <vxe-button
            status="primary"
            icon="fa fa-search"
            @click="selectTable()"
            >查询</vxe-button
          >
        </vxe-form-item>
      </vxe-form>
      <el-row :gutter="10">
        <el-col :span="12">
          <vxe-toolbar perfect size="mini">
            <template v-slot:buttons> </template>
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
            height="700px"
            ref="tableShip"
            @cell-click="rowclick"
          >
            <vxe-table-column
              type="seq"
              width="50"
              title="序号"
            ></vxe-table-column>
            <vxe-table-column
              field="berthCod"
              title="泊位号"
              width="60"
            ></vxe-table-column>
            <vxe-table-column
              field="shipNo"
              title="船号"
              width="65"
            ></vxe-table-column>
            <vxe-table-column
              field="shipNam"
              title="船名"
              width="85"
            ></vxe-table-column>
            <vxe-table-column
              field="unloadId"
              title="装卸别"
              width="60"
            ></vxe-table-column>
             <vxe-table-column
              field="cargoNam"
              title="货名"
              width="70"
            ></vxe-table-column>
            <vxe-table-column
              field="weightNam"
              title="货量"
              width="70"
            ></vxe-table-column>
            <vxe-table-column
              field="agentShort"
              title="代理"
              width="75"
            ></vxe-table-column>
            <vxe-table-column
              field="toPortTim"
              title="抵锚时间"
              width="135"
              
            ></vxe-table-column>
            <vxe-table-column
              field="edtArivTim"
              title="预抵时间"
              width="135"
              :formatter="['formatDate', 'yyyy-MM-dd HH:mm:ss']"
            ></vxe-table-column>
            <vxe-table-column
              field="levPortTim"
              title="离港时间"
              width="150"
              :formatter="['formatDate', 'yyyy-MM-dd HH:mm:ss']"
            ></vxe-table-column>
            
          </vxe-table>
          <vxe-pager
            perfect
            size="mini"
            :loading="tableload"
            :current-page="tablePage.currentPage"
            :page-size="tablePage.pageSize"
            :total="tablePage.total"
            @page-change="handlePageChange"
          ></vxe-pager>
        </el-col>
        <el-col :span="12">
          <vxe-toolbar perfect size="mini">
            <template v-slot:buttons>
              <vxe-button type="text" class="fa fa-plus" @click="addStatus()"
                >新增</vxe-button
              >
              <!--<vxe-button
                type="text"
                class="fa fa-pencil-square-o"
                @click="editStatus()"
                >修改</vxe-button
              >-->
              <vxe-button
                type="text"
                class="fa fa-trash"
                @click="deleteStatus()"
                >删除</vxe-button
              >
            </template>
          </vxe-toolbar>
          <vxe-table
            border
            show-overflow
            keep-source
            highlight-current-row
            size="mini"
            show-footer
            :footer-method="footerMethod"
            :data="tableBodyData"
            :loading="tableBodyLoad"
            :round="true"
            align="center"
            height="700px"
            ref="tableStatus"
            @cell-click="rowclickBody"
          >
            <vxe-table-column
              field="beginTim"
              title="开始时间"
              width="135"
            ></vxe-table-column>
            <vxe-table-column
              field="status"
              title="状态"
              width="80"
            ></vxe-table-column>
            <vxe-table-column
              field="endTim"
              title="结束时间"
              width="135"
            ></vxe-table-column>
            <vxe-table-column
              field="cabinStr"
              title="所开舱"
              width="70"
            ></vxe-table-column>
            <vxe-table-column
              field="teuNum"
              title="TEU"
              width="70"
            ></vxe-table-column>
            <vxe-table-column
              field="weightWgt"
              title="作业进度"
              width="70"
            ></vxe-table-column>
            <vxe-table-column
              field="totWgt"
              title="累计完成"
              width="70"
            ></vxe-table-column>
            <vxe-table-column field="unitNam" title="单位"></vxe-table-column>
          </vxe-table>
        </el-col>
      </el-row>
      <vxe-modal
        v-if="dialogFormVisibleEditView"
        v-model="dialogFormVisibleEditView"
        title="动态维护"
        width="580"
        height="720"
        resize
        storage
      >
        <Edit
          :ship="dataHeadNow"
          :editType="editType"
          :editNow="dateNow"
          @dictItemVisible="dictItemVisible"
          @selectList="getBody"
        ></Edit>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import {
  fetchHeadList,
  fetchBodyList,
  deletOne,
  getMaxSeqNo,
  getStatusSum,
} from "@/api/caccentre/shipstatus";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import EditDown from "../../baseUtile/EditDown";
import Edit from "./edit";
export default {
  components: { EditDown, Edit },
  name: "caccentre-shipstatus-index",
  data() {
    return {
      formObj: {},
      tableData: [],
      tableBodyData: [],
      dateNow: {},
      editType: null,
      dataHeadNow: {},
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      tableload: false,
      tableBodyLoad: false,
      dialogFormVisibleEditView: false,
      MaxSeqNo: "",
      shipstatus: "",
      teuSum:"",
      wgtSum:"",

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
      fetchHeadList(
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
    getBody(shipNo) {
      this.tableBodyLoad = true;
      this.dateNow = null;
      this.SumStatus();
      fetchBodyList(shipNo)
        .then((response) => {
          this.tableBodyData = response.data.data;
          this.tableBodyLoad = false;
        })
        .catch(() => {
          this.tableBodyLoad = false;
        });
    },

    footerMethod({ columns, data }) {
      const footerData = [
        columns.map((column, _columnIndex) => {
          if (_columnIndex === 0) {
            return "合计";
          }
          if (["teuNum"].includes(column.property)) {
            return this.teuSum;
          }

          if (["weightWgt"].includes(column.property)) {
            return this.wgtSum;
          }
          return null;
        }),
      ];
      return footerData;
    },

    SumStatus() {
      getStatusSum(Object.assign(this.dataHeadNow.shipNo))
        .then((response) => {
          this.teuSum = response.data.data.teuSum;
          this.wgtSum = response.data.data.wgtSum;
        })
        .catch(() => {});
    },


    dictItemVisible() {
      this.dialogFormVisibleEditView = false;
    },
    selectTable() {
      this.tablePage.currentPage = 1;
      this.tablePage.pageSize = 20;
      this.getDate();
    },
    rowclick({ row }) {
      this.dataHeadNow = row;
      this.getBody(row.shipNo);
    },
    rowclickBody({ row }) {
      this.dateNow = row;
    },
    addStatus() {
      if (this.dataHeadNow == null || this.dataHeadNow.shipNo == null) {
        this.$message.error("请选择一条船进行动态维护");
        return;
      }
      this.editType = null;
      this.dialogFormVisibleEditView = true;
    },
    editStatus() {
      if (this.dateNow == null || this.dateNow.pkid == null) {
        this.$message.error("请选择一条动态进行动态维护");
        return;
      }
      this.editType = "edit";
      this.dialogFormVisibleEditView = true;
    },
    deleteStatus() {
      if (this.dateNow == null || this.dateNow.pkid == null) {
        this.$message.error("请选择一条动态进行删除");
        return;
      }
      if (this.dateNow.statCod == 14) {
        this.$alert("移泊动态不可删除，请联系管理员删除！");
        return;
      }
      
      getMaxSeqNo(this.dataHeadNow.shipNo)
       .then((response) => {
          this.MaxSeqNo = response.data.data.MaxSeqNo;
          this.shipstatus = response.data.data.status;

          if(this.shipstatus=="离港"){
            this.$alert("船已离港，状态不可删除!");
          }
          else{
              if(this.dateNow.seqNo==this.MaxSeqNo){
                this.$XModal.confirm("您确定要删除吗？").then((type) => {
                   console.log("111:::"+type);
                  if (type == "confirm") {
                    deletOne(this.dateNow.pkid,this.dateNow.statCod,this.dateNow.beginTim,this.dataHeadNow.shipNo)
                    .then((response) => {
                      this.$message.success("删除成功");
                    this.getBody(this.dateNow.shipNo);
                    })
                    .catch(() => {});
                  }
                });
              }
              else{
              this.$alert("不是最后一条动态，不可删除！");
              }
          };

        })
        .catch(() => {});
        
    },

    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
  },
};
</script>
<style lang="scss" scoped>
.caccentre-shipstatus-index {
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