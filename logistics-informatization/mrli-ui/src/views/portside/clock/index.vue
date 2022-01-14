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
         <vxe-form-item title="开始时间" field="beginTime" size="mini" span="4">
          <vxe-input
            v-model="formObj.beginTime"
            type="date"
            clearable
          ></vxe-input>
           </vxe-form-item>
            <vxe-form-item title="结束时间" field="endTime" size="mini" span="4">
          <vxe-input
            v-model="formObj.endTime"
            type="date"
            clearable
          ></vxe-input>
           </vxe-form-item>
           <vxe-form-item title="姓名" field="userName" size="mini" span="4">
          <vxe-input
            v-model="formObj.userName"
            clearable
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="地点" field="extPlacePkid" size="mini" span="4">
          <EditDown
            :objs="formObj"
            property="extPlacePkid"
            :tableColumns="downTableOption.placeColum"
            :urlapi="downTableOption.placeurl"
            :returnApi="downTableOption.placereturnApi"
            sizenow="mini"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="center">
          <vxe-button
            status="primary"
            icon="fa fa-search"
            @click="selectTable()"
            >查询</vxe-button
          >
          <vxe-button type="reset" icon="fa fa-repeat" @click="resetForm">重置</vxe-button>
        </vxe-form-item>
      </vxe-form>
      <vxe-toolbar perfect size="mini">
        <template v-slot:buttons>
        </template>
      </vxe-toolbar>
      <vxe-table
        border
        show-overflow
        keep-source
        size="mini"
        :data="tableData"
        :loading="tableload"
        :round="true"
        align="center"
        height="700px"
        ref="tableCar"
      >
        <vxe-table-column field="CREATE_TIME" title="时间" width="180" :formatter="['formatDate', 'yyyy-MM-dd HH:mm:ss']"></vxe-table-column>
        <vxe-table-column field="PLACE_NAME" title="地点" width="200" ></vxe-table-column>
        <vxe-table-column field="PERSON_NAME" title="姓名" width="100" ></vxe-table-column>
         <vxe-table-column field="PERSON_TYPE" width="90" title="类型" :formatter="formatterType"></vxe-table-column>
        <vxe-table-column field="PERSON_WORK" title="职位" width="120"></vxe-table-column>
         <vxe-table-column field="UNIT_NAME" title="公司"></vxe-table-column>
        <vxe-table-column field="PERSON_PHONE" title="电话" width="120"></vxe-table-column>
        <vxe-table-column field="PERSON_SEX" width="90" title="性别" :formatter="formatterSex"></vxe-table-column>
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
    </basic-container>
  </div>
</template>

<script>
import { fetchList } from "@/api/portside/clock";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import EditDown from "../../baseUtile/EditDown";
export default {
  components: { EditDown },
  name: "portside-aboardpersion-index",
  data() {
    return {
      formObj: { unitName: null, unitCod: null,shipNo:null,shipNam:null,selectTime:null,extPlacePkid:null,beginTime:null,endTime:null },
      dialogFormVisibleedit: false,
      tableData: [],
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      tableload: false,
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
      //地点下拉菜单展示项
        placeColum: [
          { field: "PKID", title: "地点代码" },
          { field: "PLACE_NAME", title: "地点名称" }
        ],
        placereturnApi: [
          { field: "PKID", returnName: "extPlacePkid" },
        ],
        placeurl: "/portside/clockplacecod/codepage",
        
      }
    };
  },
  computed: {
    ...mapGetters([
      "userInfo",
      "permissions"])
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
      this.formObj.shipNo=null;
       console.log(this.formObj.shipNo)
    },
    formatterType({ cellValue }) {
      if (cellValue == "1") {
        return "内部员工";
      } else if (cellValue == "2") {
        return "外来人员";
      }
    },
    formatterSex({ cellValue }) {
      if (cellValue == "1") {
        return "男";
      } else if (cellValue == "0") {
        return "女";
      }
    },
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
</style>>