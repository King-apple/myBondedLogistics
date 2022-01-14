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
<div class="caccentre-ship-indexlg">
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
        :custom-config="{storage: true}"
        :sort-config="{remote:true}"
         @sort-change="sortChangeEvent"
      >
        <vxe-table-column type="seq" title="序号" fixed="left" width="50"></vxe-table-column>
       
        <vxe-table-column field="shipNam" title="船名" fixed="left" width="120" sortable></vxe-table-column>
         <vxe-table-column field="callNo" title="船呼号"  width="80" ></vxe-table-column>
        <vxe-table-colgroup title="作业单位">
           <vxe-table-column field="unitCod" title="代码"  width="90" sortable></vxe-table-column>
           <vxe-table-column field="unitNam" title="名称"  width="240" sortable></vxe-table-column>
        </vxe-table-colgroup>
        <vxe-table-column field="berthCod" title="泊位"  width="80" ></vxe-table-column>
        <vxe-table-column field="tradeName" title="内外贸"  width="80" ></vxe-table-column>
        <vxe-table-column field="inCargoNam" title="进口货名"  width="80" ></vxe-table-column>
        <vxe-table-column field="inShipCap" title="进口载重"  width="80" ></vxe-table-column>
        <vxe-table-column field="outCargoNam" title="出口货名" width="80" :formatter="formatterZh"></vxe-table-column>
        <vxe-table-column field="outShipCap" title="出口载重" width="150" sortable></vxe-table-column>
        <vxe-table-column field="agentNam" title="代理" width="240" sortable></vxe-table-column>
        <vxe-table-column field="levPortTim" title="离港时间" width="160" sortable></vxe-table-column>
        <vxe-table-column title="操作" width="140"  fixed="right">
          <template v-slot="{ row }">
            <vxe-button
              v-if="cxBtn"
              type="text"
              icon="el-icon-check"
              @click="shipdetail(row)"
            >查看</vxe-button>
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
        storage>
        <Form :objrow="objrow" @getList="getList" @dictItemVisible="dictItemVisible" readonly="true"></Form>
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
  getObj,
  addObj,
  putObj,
  delObj
} from "@/api/caccentre/shiplg";
import { tableOption } from "@/const/crud/caccentre/shiplg";
import { mapGetters } from "vuex";
import Form from "./indexshipeditlg";
import Formdetail from "./indexshipdetail";
import shipcontract from "../../business/businesscontractship/index";

export default {
  components: { Form,shipcontract,Formdetail }, //加载的form名称，对应上方的标签名称
  name: "shiplg",
  data() {
    return {
      searchForm: {},
      tableData: [],
      dialogFormVisible: false,
      shipContractFormVisible: false,
      dialogFormVisibledetail: false,
      activeName: "first",
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      tableLoading: false,
      tableOption: tableOption,
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
    rowDel: function(row, index) {
      this.$confirm("是否确认删除ID为" + row.pShipNo, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return delObj(row.pShipNo);
        })
        .then(data => {
          this.$message.success("删除成功");
          this.getList(this.page);
        });
    },
    loadTable() {
      this.$refs.tableShip.reloadData(
        this.$refs.tableShip.getTableData().fullData
      );
    },
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
     formatterZh({ cellValue }) {
      if (cellValue == 1) {
        return "是";
      } else {
        return "否";
      }
    },
  }
};
</script>
<style lang="scss" scoped>
.caccentre-ship-indexlg {
  .el-divider--horizontal {
    margin: 8px 0;
  }
  .vxe-toolbar {
    height: 30px;
  }
}
</style>