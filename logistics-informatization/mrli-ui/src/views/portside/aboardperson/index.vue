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
        <vxe-form-item title="开始" field="beginTime" size="mini" span="2">
          <vxe-input
            v-model="formObj.beginTime"
            type="date"
            clearable
          ></vxe-input>
           </vxe-form-item>
            <vxe-form-item title="结束" field="endTime" size="mini" span="2">
          <vxe-input
            v-model="formObj.endTime"
            type="date"
            clearable
          ></vxe-input>
           </vxe-form-item>
            <vxe-form-item title="船名" field="shipNam" size="mini" span="3">
        <EditDown
          :objs="formObj"
          :property="downTableOption.shipNam"
          :tableColumns="downTableOption.shipNamColum"
          :urlapi="downTableOption.shipNamurl"
          :returnApi="downTableOption.shipNamreturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>
           <vxe-form-item title="姓名" field="personName" size="mini" span="2">
          <vxe-input
            v-model="formObj.personName"
            clearable
          ></vxe-input>
        </vxe-form-item>
       
        <vxe-form-item title="公司" field="unitName" size="mini" span="3" v-if="userInfo.unitCod=='G1'">
          <EditDown
            :objs="formObj"
            property="unitName"
            :tableColumns="downTableOption.unitColum"
            :urlapi="downTableOption.uniturl"
            :returnApi="downTableOption.unitreturnApi"
            sizenow="mini"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item title="内外贸" field="tradeId" size="mini" span="3">
          <vxe-select v-model="formObj.tradeId" placeholder="请选择内外贸">
            <vxe-option :key="1" value="1" label="内贸"></vxe-option>
            <vxe-option :key="0" value="0" label="外贸"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item title="类型" field="type" size="mini" span="3">
          <vxe-select v-model="formObj.type" placeholder="请选择类型">
            <vxe-option key="1" value="1" label="上船"></vxe-option>
            <vxe-option key="2" value="2" label="下船"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item span="5" size="mini" align="center">
          <vxe-button
            status="primary"
            icon="fa fa-search"
            @click="selectTable()"
            >查询</vxe-button
          >
          <vxe-button type="reset" icon="fa fa-repeat" @click="resetForm">重置</vxe-button>
          <vxe-button
            status="primary"
            icon="fa fa-download"
            @click="excelOut()"
            >EXCEL导出</vxe-button
          >
        </vxe-form-item>
      </vxe-form>
      <vxe-toolbar custom perfect size="mini">
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
        :loading="tableload"
        :round="true"
        align="center"
        height="700px"
        ref="tableCar"
         :sort-config="{remote:true}"
         @sort-change="sortChangeEvent"
      >
        <vxe-table-column field="createTime" title="上/下船时间" width="150" sortable></vxe-table-column>
        <vxe-table-column field="workDate" title="日期" width="95" :formatter="['formatDate', 'yyyy-MM-dd']" sortable></vxe-table-column>
        <vxe-table-column field="type" title="类型" width="95" :formatter="formatterType" sortable></vxe-table-column>
        <vxe-table-column field="shipName" title="船名" sortable></vxe-table-column>
        <vxe-table-column field="tradeId" title="内外贸" sortable formatter="formatTradeId"></vxe-table-column>
        <vxe-table-column field="personName" title="人名" sortable  :cell-render="{name: 'TableColumnLink', events: {click: personNamelinkEvent}}"></vxe-table-column>
        <vxe-table-column field="unitName" title="公司" width="200"  sortable></vxe-table-column>
        <vxe-table-column field="companyName" title="外来公司" width="200"  sortable></vxe-table-column>
        <vxe-table-column field="personId" title="身份证号" ></vxe-table-column>
        <vxe-table-column field="personPhone" title="联系方式" ></vxe-table-column>
        <vxe-table-column field="aboardBak" title="上/下船理由" ></vxe-table-column>
        <vxe-table-column field="temp" title="温度" sortable></vxe-table-column>
        <vxe-table-column field="recordOpt" title="记录人" sortable></vxe-table-column>
        <vxe-table-column field="ifCdqq" title="个人防护情况是否穿戴齐全" sortable :formatter="formatterIfCdqq"></vxe-table-column>
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
      <el-drawer
      v-if="drawer"
         :visible.sync="drawer"
          :with-header="false"  size="50%">
       <PersonEdit
          :datanow="dataNow"
          @dictItemVisible="dictItemVisible"
          ifView="yes"
      ></PersonEdit>
    </el-drawer>
    </basic-container>
  </div>
</template>

<script>
import {
  fetchList,
  excelOutPost,
  personModelByPkid
} from "@/api/portside/aboard";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import PersonEdit from "../yqbak/personEdit";
import EditDown from "../../baseUtile/EditDown";
export default {
  components: { EditDown, PersonEdit },
  name: "portside-aboardpersion-index",
  data() {
    return {
      formObj: {
        unitName: null,
        unitCod: null,
        shipNo: null,
        shipNam: null,
        selectTime: null,
        type:null,
      },
      drawer: false,
      dialogFormVisibleedit: false,
      tableData: [],
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      tableload: false,
      dataNow: {},
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
        uniturl: "/caccentre/basecod/unit/page",
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
        cargourl: "/yard/mioyardhead/getCargoNam",
        //预报船信息
        shipNam: "shipNam",
        shipNamColum: [
          { field: "SHIP_NO", title: "船号" },
          { field: "SHIP_NAM", title: "船名" },
          { field: "TO_PORT_TIM", title: "到港时间" },
          { field: "TO_PORT_NAM", title: "到达港" }
        ],
        shipNamreturnApi: [
          { field: "SHIP_NO", returnName: "shipNo" },
          { field: "SHIP_NAM", returnName: "shipNam" }
        ],
        shipNamurl: "/caccentre/ship/getWmShipOfNotLev"
      }
    };
  },
  computed: {
    ...mapGetters(["userInfo", "permissions"])
  },
  created() {
    this.getDate();
  },
  methods: {
    getDate() {
     
      if (this.userInfo.unitCod != "G1") {
        this.formObj.unitCod = this.userInfo.unitCod;
      }
      this.tableload = true;
      fetchList(
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
    excelOut() {
      this.$XModal.confirm("您确定要导出吗？").then(type => {
        if (type == "confirm") {
          let loadingInstance = Loading.service({
            fullscreen: true,
            text: "EXCEL生成中……"
          });
          excelOutPost(this.formObj)
            .then(() => {
              loadingInstance.close();
            })
            .catch(() => {
              loadingInstance.close();
            });
        }
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
    resetForm() {
      this.formObj.shipNo = null;
      this.formObj.tradeId = null;
    },
    sortChangeEvent({ column, property, order }) {
      this.formObj.sqlSortNameOfMrLI = property;
      this.formObj.sqlSortOfMrLI = order;
      this.selectTable();
    },
    dictItemVisible() {
      this.drawer = false;
    },
    formatterType({ cellValue }) {
      if (cellValue == "1") {
        return "上 船";
      } else if (cellValue == "2") {
        return "下 船";
      }
    },
    formatterIfCdqq({ cellValue }){
     if (cellValue == 1) {
        return "是";
      } else if (cellValue == 0) {
        return "否";
      }
    },
    personNamelinkEvent({ row }) {
      personModelByPkid(row.personId)
        .then(response => {
          this.dataNow = response.data.data;
          this.drawer = true;
        })
        .catch(() => {});
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
</style>