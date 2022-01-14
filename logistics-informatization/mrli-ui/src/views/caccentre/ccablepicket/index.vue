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
            :export-config="{}"
            size="mini"
            :data="tableData"
            :loading="tableload"
            :round="true"
            align="center"
            height="600"
            ref="ccablepicket"
            :edit-config="{ trigger: 'click', mode: 'cell', showStatus: true }"
            :mouse-config="{ selected: true }"
            :keyboard-config="{
              isArrow: true,
              isEnter: true,
              isTab: true,
              isEdit: true,
              enterToTab: true,
            }"
            :row-style="rowStyle"
            :sort-config="{ remote: true }"
            @cell-click="rowclick" 
          >
          <vxe-table-column
              title="缆桩号"
              field="picketNo"
              width="20%"
              :edit-render="{ name: 'input' }"
            ></vxe-table-column>
            <vxe-table-column
              title="泊位号"
              field="berthCod"
              width="20%" 
              :edit-render="{
                name: 'EditDownTable',
                props: {
                  checkField: 'berthCod',
                  tableColumn: this.downTableOption.berthColum,
                  urlapi: this.downTableOption.berthurl,
                  returnApi: this.downTableOption.berthreturnApi,
                },
              }"
            ></vxe-table-column>
            <vxe-table-column
              title="泊位名称"
              field="berthNam"
              width="20%" 
            ></vxe-table-column>
           <vxe-table-column
              title="最大拉力"
              field="picketPull"
              width="20%" 
              :edit-render="{ name: 'input' }"
            ></vxe-table-column>             
            <vxe-table-column
              title="备注"
              field="markTxt"
              width="20%"
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
  List,   
  saveBodyData,   
  deleteb,
} from "@/api/caccentre/ccablepicket";
 
import { mapGetters } from "vuex"; 
import EditDown from "../../baseUtile/EditDown"; 
import moment from "moment";
export default {
  components: { EditDown },
  name: "mioyardhead",
  data() {
    return { 
      tableData: [],
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      tableload: false,
      
      downTableOption: {
        //泊位下拉菜单展示项
        berthColum: [
          { field: "BERTH_COD", title: "泊位代码" },
          { field: "BERTH_NAM", title: "泊位名称" } 
        ],
        //泊位下拉菜单的返回项
        berthreturnApi: [
          { field: "BERTH_COD", returnName: "berthCod" },
          { field: "BERTH_NAM", returnName: "berthNam" }],
       
        //泊位下拉菜单的路径
        berthurl: "/basecode/basecod/getBerth"        
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
      List(
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
          this.dataNow = {};
          this.getHead();
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

     rowclick({ row }) {
      this.dataNow = row;
    },
    async insertEvent(row) {
       let   newRow  = await this.$refs.ccablepicket.insertAt(row);
      await this.$refs.ccablepicket.setActiveCell(newRow, "picketNo");
    },
    async saveAll() {
      const errMap = await this.$refs.ccablepicket
        .fullValidate()
        .catch((errMap) => errMap);
      if (errMap) {
      } else {
        this.tableload = true;
        saveBodyData(this.$refs.ccablepicket.getRecordset())
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
    
    del() {
      if (this.dataNow == null || this.dataNow.picketNo == null) {
        this.$message.error("请选择一条数据进行删除");
        return;
      }
      this.tableload = true;
      deleteb(this.dataNow.picketNo)
        .then((response) => {
          this.$message.success(response.data.data);
          this.tableload = false;
          this.getDate();
        })
        .catch(() => {
          this.tableload = false;
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