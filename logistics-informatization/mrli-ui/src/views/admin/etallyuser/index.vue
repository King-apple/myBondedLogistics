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
  <div class="mioyardhead">
    <basic-container>
      <vxe-form :data="searchForm" size="mini">
        <vxe-form-item title="单位" field="unitCod" span="4"> 
          <EditDown
            :objs="searchForm"
            :property="downTableOption.unitname"
            :tableColumns="downTableOption.unitColum"
            :urlapi="downTableOption.uniturl"
            :returnApi="downTableOption.unitreturnApi"
            sizenow="mini"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item title="用户名" field="operNam" span="4">
          <vxe-input v-model="searchForm.operNam" ></vxe-input>
        </vxe-form-item>  
        <vxe-form-item span="4" size="mini" align="left">
          <vxe-button status="primary" icon="fa fa-search" @click="getDate()"
            >查询</vxe-button
          >
          
        </vxe-form-item>     
      </vxe-form>      
      <vxe-toolbar perfect size="mini">
        <template v-slot:buttons>
          <vxe-button type="text" icon="fa fa-plus" @click="insertEvent()"
            >新增</vxe-button
          >
          <vxe-button type="text" icon="fa fa-floppy-o" @click="saveAll()"
            >保存</vxe-button
          >
          <vxe-button type="text" icon="fa fa-trash" @click="del()"
            >删除</vxe-button
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
            :data="tableData"
            :loading="tableload"
            :round="true"
            align="center"
            height="550"
            ref="tableShipBody"
            :edit-config="{ trigger: 'click', mode: 'cell', showStatus: true }"
            :mouse-config="{ selected: true }"
            :keyboard-config="{
              isArrow: true,
              isDel: true,
              isEnter: true,
              isTab: true,
              isEdit: true,
              isChecked: true,
            }"
            @cell-click="rowclick"
            
          >
            
            <vxe-table-column
              title="单位"
              field="unitCod"
              width="16%"
              :edit-render="{
                name: 'EditDownTable',
                props: {
                  checkField: 'unitCod',
                  tableColumn: this.downTableOption.unitColum,
                  urlapi: this.downTableOption.uniturl,
                  returnApi: this.downTableOption.unitreturnApi,
                },
              }"
            ></vxe-table-column>
            <vxe-table-column
              title="部门"
              field="subunitCod"
              width="16%"
              :edit-render="{
                name: 'EditDownTable',
                props: {
                  checkField: 'unitCod',
                  tableColumn: this.downTableOption.unitColum,
                  urlapi: this.downTableOption.uniturl,
                  returnApi: this.downTableOption.unitreturnApi,
                },
              }"
            ></vxe-table-column>
            <vxe-table-column
              title="手机账号"
              field="userNam"
              width="17%"
              :edit-render="{ name: 'input' }"
            ></vxe-table-column>
            <vxe-table-column
              title="生产账号"
              field="operCod"
              width="17%"
              :edit-render="{ name: 'input' }"
            ></vxe-table-column>
            <vxe-table-column
              title="姓名"
              field="operNam"
              width="17%"
              :edit-render="{ name: 'input' }"
            ></vxe-table-column>
            <vxe-table-column
              title="密码"
              field="password"
              width="17%"
              :edit-render="{ name: 'input' }"
            ></vxe-table-column>
             
          </vxe-table>
          <vxe-pager
            perfect
            size="mini"
            :loading="tableload"
            :current-page="tablePage.currentPage"
            :page-size="tablePage.pageSize"
            :total="tablePage.total"
            :page-sizes="[20, 50, 100, 200, 300]"
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
import {
  fetchList, 
  saveData  ,
  del
} from "@/api/admin/etallyuser";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import EditDown from "../../baseUtile/EditDown";
import moment from "moment";
export default {
  components: { EditDown },
  name: "mioyardhead",
  data() {
    return {    
      searchForm: { unitCod:null,operNam:null},
      tableData: [],
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      tableload: false,
      
      downTableOption: {  

        //单位下拉菜单展示项
        unitColum: [
          { field: "UNIT_COD", title: "单位代码" },
          { field: "UNIT_NAM", title: "单位名称" },
        ],
        //单位下拉菜单的返回项
        unitreturnApi: [
          { field: "UNIT_COD", returnName: "unitCod" }, 
        ],
        unitname: "unitCod",
        //单位下拉菜单的路径
        uniturl: "/basecode/basecod/getUnitNam?id=0",

        
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
      fetchList(
        Object.assign(
          {
            current: this.tablePage.currentPage,
            size: this.tablePage.pageSize,
          },
          this.searchForm
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

    

    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
      

    async insertEvent(row) {      
      let record = {
        ostype: 1,
        status: 1 
      };
      let { row: newRow } = await this.$refs.tableShipBody.insertAt(
        record,
        row
      );
      await this.$refs.tableShipBody.setActiveCell(newRow, "berthCod");
    },

    async saveAll() {
      const errMap = await this.$refs.tableShipBody
        .fullValidate()
        .catch((errMap) => errMap);
      if (errMap) {
      } else {
        this.tableload = true;
        saveData(this.$refs.tableShipBody.getRecordset())
          .then((response) => {
            this.$message.success(response.data.data);
            this.tableload = false;
            this.getDate();
          })
          .catch(() => {
            this.tableload = false;
          });
      }
    },
 rowclick({ row }) {
      this.dataNow = row;
    },
    del() {
      if (this.dataNow == null || this.dataNow.id == null) {
        this.$message.error("请选择一条数据进行删除");
        return;
      }
      this.tableload = true;
      this.$XModal
        .confirm("您确定要删除" + this.dataNow.userNam + "吗？")
        .then((type) => {
          if (type == "confirm") {
            del(this.dataNow.id)
              .then((response) => {
                this.$message.success(response.data.data);
                this.tableload = false;
                this.getDate();
              })
              .catch(() => {
                this.tableload = false;
              });
          } else {
            this.tableload = false;
          }
        });
    },
  
  },
};
</script>
<style lang="scss" scoped>
.mioyardhead {
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