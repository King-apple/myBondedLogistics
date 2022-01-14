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
<div class="caccentre-ship-indexmd">
  <vxe-form :data="searchForm" size="mini">
        <vxe-form-item
          title="临时下锚时间"
          field="lsptime"
          size="mini"
          span="4"
        >
         <vxe-select v-model="searchForm.lsptime" clearable size="mini">
            <vxe-option value="1" label="有"></vxe-option>
            <vxe-option value="0" label="无"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item title="抵锚时间" field="ptime" size="mini" span="4">
          <vxe-select v-model="searchForm.ptime" clearable size="mini">
            <vxe-option value="1" label="有"></vxe-option>
            <vxe-option value="0" label="无"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item title="船名：" field="shipNam" size="mini" span="3">
          <vxe-input v-model="searchForm.shipNam" clearable size="mini"></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="内外贸：" field="tradeId" size="mini" span="2">
          <vxe-select v-model="searchForm.tradeId" clearable size="mini">
            <vxe-option value="0" label="外贸"></vxe-option>
            <vxe-option value="1" label="内贸"></vxe-option>
            </vxe-select>
        </vxe-form-item>
        
        <vxe-form-item span="4" size="mini" align="center">
          <vxe-button status="primary" icon="fa fa-search" @click="searchChange()"
            >查询</vxe-button
          >
          <vxe-button type="reset" icon="fa fa-repeat">重置</vxe-button>
        </vxe-form-item>
      </vxe-form>
 <vxe-toolbar perfect custom size="mini">
            <template v-slot:buttons>
              <vxe-button type="text" >锚地船舶数量：{{CountMdd }}</vxe-button>
              <vxe-button type="text" class="fa fa-print" @click="printAll()">打印</vxe-button>
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
        :loading="tableLoading"
        :round="true"
        align="center"
        height="600px"
        ref="tableShip"
        :custom-config="{storage: true, }"
        :sort-config="{remote:true}"
         @sort-change="sortChangeEvent"
      >
        <vxe-table-column type="seq" title="序号" fixed="left" width="50"></vxe-table-column>
        <vxe-table-column field="shipNam" title="船名" fixed="left" width="100" sortable></vxe-table-column>
        <vxe-table-column field="tradeName" title="内外贸" width="80" sortable sortBy='{"table":"a","nameExt":"tradeId"}'></vxe-table-column>
        <vxe-table-column field="inCargoNam" title="进口货名" width="100" sortable></vxe-table-column>
        <vxe-table-column field="inShipCap" title="进口载重" width="100" sortable></vxe-table-column>
        <vxe-table-column field="outCargoNam" title="出口货名" width="100" sortable></vxe-table-column>
        <vxe-table-column field="outShipCap" title="出口载重" width="100" sortable></vxe-table-column>
        <vxe-table-column field="shipLongNum" title="船长" width="80" sortable sortBy='{"table":"a"}'></vxe-table-column>
        <vxe-table-column field="dgcs" title="抵港吃水" width="100" sortable sortBy='{"table":"c"}'></vxe-table-column>
        <vxe-table-column field="lgcs" title="离港吃水" width="100" sortable sortBy='{"table":"c"}'></vxe-table-column>
        <vxe-table-column field="berthCod" title="锚位" width="80" sortable></vxe-table-column>
        <vxe-table-column field="toLsportTim" title="临时抛锚时间" width="140" sortable></vxe-table-column>
        <vxe-table-column field="toPortTim" title="抵港时间" width="140" sortable></vxe-table-column>
        <vxe-table-column field="agentShort" title="代理" width="100" sortable></vxe-table-column>
        <vxe-table-column field="placeN" title="北纬" width="80" sortable></vxe-table-column>
        <vxe-table-column field="placeE" title="东经" width="80" sortable></vxe-table-column>
        <vxe-table-column title="操作" width="350"  fixed="right">
          <template v-slot="{ row }">
            <vxe-button
               v-if="cxBtn"
              type="text"
              icon="el-icon-check"
              @click="shipdetail(row)"
            >查看</vxe-button>
            <vxe-button
             v-if="whBtn"
              type="text"
              icon="el-icon-warning"
              @click="shipSub(row)"
            >配工船</vxe-button>
            <vxe-button
              v-if="whBtn"
              type="text"
              icon="el-icon-warning"
              @click="shipStatus(row)"
            >靠泊</vxe-button>
             <vxe-button
              v-if="cxBtn"
              type="text"
              icon="el-icon-warning"
              @click="shipContract(row)"
            >合同</vxe-button>
            <vxe-button
             v-if="whBtn"
              type="text"
              icon="el-icon-warning"
              @click="shiplg(row)"
            >离港</vxe-button>
          </template>
        </vxe-table-column>
      </vxe-table>
      <vxe-pager
        perfect
        size="mini"
        :loading="tableLoading"
        :current-page="page.currentPage"
        :page-size="page.pageSize"
        :total="page.total"
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
           <vxe-modal  v-if="dialogFormVisible"
        v-model="dialogFormVisible"
        title="船舶信息维护"
        width="1000"
        height="800"
        resize
        storage
        fullscreen>
        <Form :objrow="objrow" @getList="getList" @dictItemVisible="dictItemVisible" readonly="true"></Form>
      </vxe-modal>
       <vxe-modal  v-if="shipsubFormVisible"
        v-model="shipsubFormVisible"
        title="配工船维护"
        width="1000"
        height="800"
        resize
        storage>
        <shipsub :objrowsub="objrowsub" @getList="getList" @dictItemVisible="dictItemVisible" readonly="true"></shipsub><!--form标签 -->
      </vxe-modal>
      <vxe-modal  v-if="shiplgFormVisible"
        v-model="shiplgFormVisible"
        title="船舶离港"
        width="1000"
        height="800"
        resize
        storage
        fullscreen>
        <shiplg :objrowlg="objrowlg" :objrow="objrow" @getList="getListPar" @dictItemVisible="shiplgItemVisible" readonly="true"></shiplg><!--form标签 -->
      </vxe-modal>
       <vxe-modal
        title="动态维护"  
         v-if="shipstatusFormVisible"
        v-model="shipstatusFormVisible"
        width="580"
        height="720"
        resize
        storage>
        <Edit  :ship="dataHeadNow"
          :editType="editType"
          :editNow="dateNow"
           @selectList="getList" @dictItemVisible="dictItemVisible" readonly="true"></Edit><!--form标签 -->
      </vxe-modal>
       <vxe-modal  v-if="shipContractFormVisible"
        v-model="shipContractFormVisible"
        title="合同列表"
        width="1100"
        height="880"
        resize
        storage>
        <shipcontract :objrowcon="objrowcon" @getList="getList" @dictItemVisible="dictItemVisible" readonly="true"></shipcontract><!--form标签 -->
      </vxe-modal>
           <vxe-modal
        v-if="dialogFormVisibledetail"
        v-model="dialogFormVisibledetail"
        title="船舶信息维护"
        width="1000"
        height="800"
        resize
        storage
        fullscreen
      >
         <Formdetail
          :datanow="dataNext"
          @dictItemVisiblenew="dictItemVisiblenew"
          @selectList="getList"
        ></Formdetail>
        </vxe-modal>
       </div>
</template>


<script>
import {
  fetchList,
  addObj,
  putObj,
  getObj,
  getCountShipt,
  getShipStatus,getCountMd
} from "@/api/caccentre/shipmd";
import { tableOption } from "@/const/crud/caccentre/shipmd";
import { mapGetters } from "vuex";
import Form from "./indexshipeditmd";
import shipsub from "../shipsub/index";
import shiplg from "./shiplg";
import Edit from "../shipstatus/edit";
import Formdetail from "./indexshipdetail";
import shipcontract from "../../business/businesscontractship/index";
import Vue from "vue";
import VXETable from "vxe-table";
export default {
  components: { Form, shipsub, shiplg, Edit,shipcontract,Formdetail }, //加载的form名称，对应上方的标签名称
  name: "shipmd",
  data() {
    return {
      searchForm: {},
      tableData: [],
      dateNow: null,
      editType: null,
      dialogFormVisible: false,
      shipsubFormVisible: false,
      shiplgFormVisible: false,
      shipstatusFormVisible: false,
      shipContractFormVisible: false,
      dialogFormVisibledetail: false,
      activeName: "first",
      CountMdd: "",
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      tableLoading: false,
      tableOption: tableOption,
      objrow: {}, //编辑弹出form方法
      objrowsub: {}, //配工船弹出方法
      dataHeadNow: {}, //靠泊弹出方法
      objrowlg: {}
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  created() {
    this.searchChange();
     this.whBtn = this.permissions["CACCENTRE_SHIP_INDEX_WH"];
    this.cxBtn = this.permissions["CACCENTRE_SHIP_INDEX_CX"];
    this.dyBtn = this.permissions["CACCENTRE_SHIP_INDEX_DY"];
  },
  methods: {
    getList() {
      this.tableLoading = true;
      this.CountMd();
      fetchList(
        Object.assign(
          {
            current: this.page.currentPage,
            size: this.page.pageSize
          },
          this.searchForm
        )
      )
        .then(response => {
          this.tableData = response.data.data.records;
          this.page.total = response.data.data.total;
          this.tableLoading = false;
        })
        .catch(() => {
          this.tableLoading = false;
        });
        
    },
    loadTable() {
      this.$refs.tableShip.reloadData(
        this.$refs.tableShip.getTableData().fullData
      );
    },
    CountMd() {
      getCountMd()
        .then((response) => {
          this.CountMdd = response.data.data;
        })
        .catch(() => {});
    },
    printAll() {     
      window.open(
        "http://10.135.149.248:8080/webroot/decision/view/report?viewlet=ddzhpt/抵锚船舶.cpt"
      );
    },
    shipdetail(row) {
        var that = this;
        getObj(row.shipNo)
        .then((response) => {
          that.dataNext = response.data.data; //后台执行结果，赋给dataNext
          this.dialogFormVisibledetail = true;
        })
        .catch(() => {
          loading();
        });
    },
    dictItemVisiblenew() {
      this.dialogFormVisibledetail = false;
    },
    //点击编辑弹出form-------开始
    handleApt: function(row) {
      this.objrow = row;
      this.dialogFormVisible = true;
    },
    dictItemVisible: function() {
      this.objrow = {};
      this.dialogFormVisible = false;
    },
    //--------------结束
    //-----点击配工船--------开始
    shipSub: function(row) {
      this.objrowsub = row; //将本行数据赋给objrowsub，传到下个页面
      this.shipsubFormVisible = true;
    },
    shipsubItemVisible: function() {
      this.objrow = {};
      this.shipsubFormVisible = false;
    },
    //--------------结束
    //-----点击合同--------开始
    shipContract: function(row) {
      this.objrowcon = row; //将本行数据赋给objrowsub，传到下个页面
      this.shipContractFormVisible = true;
    },
    shipsubItemVisible: function() {
      this.objrow = {};
      this.shipContractFormVisible = false;
    },
    //--------------结束
    //-----点击离港--------开始
    shiplg: function(row) {
      this.objrowlg = row; //将本行数据赋给objrowlg，传到下个页面
      getCountShipt(row.shipNo)
        .then(response => {
          let wl_count_end = response.data.data;
          console.log(row.shipNo);
          if (wl_count_end > 0) {
            this.$alert("船舶未完工，不能进行离港！", "错误");
          } else {
            getShipStatus(row.shipNo).then(response => {
              this.objrowlg = response.data.data;
              this.objrow = row;
              this.objrow.statusId = "2";
              this.shiplgFormVisible = true;
            });
          }
        })
        .catch(response => {
          console.log(response);
        });
    },
    shiplgItemVisible: function() {
      this.objrowlg = {};
      this.shiplgFormVisible = false;
    },
    //--------------结束
    dictItemVisible() {
      this.objrow = {};
      this.dialogFormVisible = false;
      this.shipsubFormVisible = false;
      this.shiplgFormVisible = false;
      this.shipstatusFormVisible = false;
    },
    //-----点击靠泊--------开始
    shipStatus: function(row) {
      this.dataHeadNow = row; //将本行数据赋给dataHeadNow，传到下个页面
      this.dateNow = row;
      this.editType = null;
      this.shipstatusFormVisible = true;
    },
    shipstatusItemVisible: function() {
      this.objrow = {};
      this.shipstatusFormVisible = false;
    },
    //--------------结束
    handleUpdate: function(row, index, done, loading) {
      putObj(row)
        .then(data => {
          this.$message.success("修改成功");
          done();
          this.getList(this.page);
        })
        .catch(() => {
          loading();
        });
    },
    handleSave: function(row, done, loading) {
      addObj(row)
        .then(data => {
          this.$message.success("添加成功");
          done();
          this.getList(this.page);
        })
        .catch(() => {
          loading();
        });
    },

    handlePageChange({ currentPage, pageSize }) {
      this.page.currentPage = currentPage;
      this.page.pageSize = pageSize;
      this.getList();
    },
    searchChange() {
      this.page.currentPage = 1;
      this.searchForm.sqlSortNameOfMrLI = null;
      this.searchForm.sqlSortOfMrLI = null;
      this.searchForm.sqlSortByTable = null;
      this.getList();
    },
    sortChangeEvent({ column, property, order }) {
      this.searchForm.sqlSortNameOfMrLI = property;
      if (column.sortBy != null && column.sortBy != undefined) {
        var jsonOject = JSON.parse(column.sortBy);
        if (jsonOject.table != null) {
          this.searchForm.sqlSortByTable = jsonOject.table;
        }
        if (jsonOject.nameExt != null) {
          this.searchForm.sqlSortNameOfMrLI = jsonOject.nameExt;
        }
      }
      this.searchForm.sqlSortOfMrLI = order;
      this.getList();
    }
  }
};
</script>
<style lang="scss" scoped>
.caccentre-ship-indexmd {
  .el-divider--horizontal {
    margin: 8px 0;
  }
  .vxe-toolbar {
    height: 30px;
  }
}
</style>