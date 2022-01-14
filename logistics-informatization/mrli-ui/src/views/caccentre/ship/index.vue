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
<div class="caccentre-ship-index">
        <basic-container>
         
  <el-tabs v-model="activeName" type="card"  @tab-click="handleClick">
    <el-tab-pane label="预报船信息" name="first">
       <vxe-form :data="searchForm" size="mini">
        <vxe-form-item
          title="时间段："
          field="timebegin"
          size="mini"
          span="4"
        >
          <vxe-input
            v-model="searchForm.timebegin"
            placeholder="开始时间"
            type="datetime"
            valueFormat="yyyy-MM-dd HH:mm:ss"
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="至" field="timeend" size="mini" span="4">
          <vxe-input
            v-model="searchForm.timeend"
            placeholder="结束时间"
            type="datetime"
            valueFormat="yyyy-MM-dd HH:mm:ss"
          ></vxe-input>
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
              <vxe-button type="text" >预报船舶数量：{{CountYbb }}</vxe-button>
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
        :custom-config="{storage: true, checkMethod: checkColumnMethod}"
        :sort-config="{remote:true}"
         @sort-change="sortChangeEvent"
      >
        <vxe-table-column type="seq" title="序号" fixed="left" width="50"></vxe-table-column>
        <vxe-table-column field="shipNam" title="船名" fixed="left" width="100" sortable></vxe-table-column>
        <vxe-table-column field="callNo" title="呼号"  fixed="left" width="80" ></vxe-table-column>
        <vxe-table-column field="ccountryNam" title="国籍" width="80" sortable></vxe-table-column>
        <vxe-table-column field="tradeName" title="内外贸" width="80" sortable></vxe-table-column>
        <vxe-table-column field="inCargoNam" title="进口货名" width="100" sortable></vxe-table-column>
        <vxe-table-column field="inShipCap" title="进口载重" width="100" sortable></vxe-table-column>
        <vxe-table-column field="outCargoNam" title="出口货名" width="100" sortable></vxe-table-column>
        <vxe-table-column field="outShipCap" title="出口载重" width="100" sortable></vxe-table-column>
       <vxe-table-column field="agentShort" title="代理" width="150" sortable></vxe-table-column>
        <vxe-table-column field="cleanprot" title="清污协议" width="120" sortable></vxe-table-column>
       <vxe-table-column field="edtArivTim" title="预抵时间" width="140" sortable :formatter="['formatDate', 'yyyy-MM-dd HH:mm:ss']"></vxe-table-column>
       <vxe-table-column field="toLsportTim" title="临时抛锚时间" width="140" sortable :formatter="['formatDate', 'yyyy-MM-dd HH:mm:ss']"></vxe-table-column>
       <vxe-table-column field="shipLongNum" title="船长" width="80" sortable sortBy='{"table":"a"}'></vxe-table-column>
       <vxe-table-column field="shipWideNum" title="船宽" width="80" sortable sortBy='{"table":"c"}'></vxe-table-column>
       <vxe-table-column field="typeDeepNum" title="型深" width="80" sortable sortBy='{"table":"c"}'></vxe-table-column>
       <vxe-table-column field="dgcs" title="抵港吃水" width="100" sortable></vxe-table-column>
       <vxe-table-column field="lgcs" title="离港吃水" width="100" sortable></vxe-table-column>
       <vxe-table-column field="netWgt" title="净吨" width="80" sortable></vxe-table-column>
       <vxe-table-column field="shipCap" title="载重吨" width="90" sortable></vxe-table-column>
       <vxe-table-column field="impHatchNum" title="舱口数" width="90" sortable></vxe-table-column>
       <vxe-table-column field="fromPortNam" title="开来港" width="100" sortable></vxe-table-column>
       <vxe-table-column field="prePortNam" title="上一港" width="100" sortable></vxe-table-column>
       <vxe-table-column field="toPortNam" title="目的港" width="100" sortable></vxe-table-column>
       <vxe-table-column field="tallyNam" title="理货单位" width="150" sortable></vxe-table-column>
        <vxe-table-column title="操作" width="140"  fixed="right">
          <template v-slot="{ row }">
            <vxe-button
              v-if="cxBtn"
              type="text"
              icon="fa fa-edit"
              @click="shipedit(row)"
            >编辑</vxe-button>
            <vxe-button
              v-if="cxBtn"
              type="text"
              icon="el-icon-warning"
              @click="shipContract(row)"
            >合同</vxe-button>
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
        <Form :objrow="objrow" @getList="getListParent" @dictItemVisible="dictItemVisible"></Form>
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
        v-if="dialogFormVisibleedit"
        v-model="dialogFormVisibleedit"
        title="船舶信息维护"
        width="1000"
        height="800"
        resize
        storage
        fullscreen
      >
         <Edit
          :datanow="dataNext"
          @dictItemVisiblenew="dictItemVisiblenew"
          @selectList="getList"
        ></Edit>
      </vxe-modal>
       
    </el-tab-pane>
    <el-tab-pane label="锚地船信息" name="second"><shipmd ref="shipmdPage"/></el-tab-pane>
    <el-tab-pane label="在港船信息" name="third"><shipzg/></el-tab-pane>
    <el-tab-pane label="离港船信息" name="fouth" ><shiplg ref="shiplgPage"/></el-tab-pane>
    <el-tab-pane v-if="dyBtn" label="集装箱在港船舶动态" name="five" ><dyofshipdynamic/></el-tab-pane>
    <el-tab-pane v-if="dyBtn" label="码头在场车辆" name="six" ><cardynamic/></el-tab-pane>
  </el-tabs>
  
   </basic-container>
      </div>
</template>

<script>
import { fetchList,getCountYb,getObj } from "@/api/caccentre/ship";
import { tableOption } from "@/const/crud/caccentre/ship";
import { mapGetters } from "vuex";
import shipmd from "./indexmd";
import shiplg from "./indexlg";
import shipzg from "./indexzg";
import dyofshipdynamic from "./dyofshipdynamic";
import cardynamic from "./cardynamic";
import Edit from "./indexshipedit";
import shipcontract from "../../business/businesscontractship/index";

export default {
  components: { shipmd, shiplg, shipzg, Edit,shipcontract,dyofshipdynamic,cardynamic},
  name: "ship",
  data() {
    return {
      searchForm: {},
      tableData: [],
      dialogFormVisible: false,
      shipContractFormVisible: false,
      dialogFormVisibleedit: false,
      activeName: "third",
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      tableLoading: false,
      tableOption: tableOption,
      CountYbb: "",
      objrow: {} //编辑弹出form方法
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
      this.CountYb();
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
    printAll() {     
      window.open(
        "http://10.135.149.248:8080/webroot/decision/view/report?viewlet=ddzhpt/预报船舶.cpt"
      );
    },

    shipedit(row) {
        var that = this;
        getObj(row.shipNo)
        .then((response) => {
          that.dataNext = response.data.data; //后台执行结果，赋给dataNext
          this.dialogFormVisibleedit = true;
        })
        .catch(() => {
          loading();
        });
    },
    dictItemVisiblenew() {
      this.dialogFormVisibleedit = false;
    },
    
    CountYb() {
      getCountYb()
        .then((response) => {
          this.CountYbb = response.data.data;
        })
        .catch(() => {});
    },
    //点击编辑弹出form-------开始
    handleApt: function(row) {
      console.log(row.shipNo);
      this.objrow = row;
      this.dialogFormVisible = true;
    },
    handleClick() {
      this.$refs.tableShip.reloadData(
        this.$refs.tableShip.getTableData().fullData
      );
      this.$refs.shipmdPage.loadTable();
      this.$refs.shiplgPage.loadTable();
    },
    dictItemVisible: function() {
      this.objrow = {};
      this.dialogFormVisible = false;
    },
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
    handlePageChange({ currentPage, pageSize }) {
      this.page.currentPage = currentPage;
      this.page.pageSize = pageSize;
      this.getList();
    },
    searchChange() {
      this.page.currentPage = 1;
      this.searchForm.sqlSortNameOfMrLI = null;
      this.searchForm.sqlSortOfMrLI = null;
       this.searchForm.sqlSortByTable =null;
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
    },
    checkColumnMethod({ column }) {
      if (column.property === "role") {
        return false;
      }
      return true;
    },
  }
};
</script>
<style lang="scss" scoped>
.caccentre-ship-index {
  .el-divider--horizontal {
    margin: 8px 0;
  }
  .vxe-toolbar {
    height: 30px;
  }
}
</style>