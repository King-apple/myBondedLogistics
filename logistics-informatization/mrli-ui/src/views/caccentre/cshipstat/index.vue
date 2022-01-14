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
   <!--船舶动态代码-->
<template>
  <div class="caccentre-cshipstat-index">
    <basic-container>
      <!--查询区-->
      
      <!--功能按钮-->
      <vxe-toolbar perfect size="mini">
        <template v-slot:buttons>
           <vxe-button type="text" class="fa fa-plus" @click="add('add')"
            >新增</vxe-button
          >
          <vxe-button
            type="text"
            class="fa fa-pencil-square-o"
            @click="add('edit')"
            >修改</vxe-button
          >
          <vxe-button
                type="text"
                class="fa fa-trash"
                @click="deleteStatus()"
                >删除</vxe-button
              >
          
        </template>
      </vxe-toolbar>
      <!--表格-->
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
        height="650px"
        ref="tableMan"
        @cell-click="rowclick"
      >
        <vxe-table-column  field="seqNo"   title="序号"      width="80" ></vxe-table-column>
        <vxe-table-column  field="statCod" title="动态代码"     ></vxe-table-column>
        <vxe-table-column  field="status"  title="动态"         ></vxe-table-column>
        <vxe-table-column  field="code"    title="停时类别代码"  ></vxe-table-column>
        <vxe-table-column  field="cstatus" title="停时类别"      ></vxe-table-column>
        <vxe-table-column  field="markTxt" title="备注"       ></vxe-table-column>       
        
      </vxe-table>
      <!--分页区-->
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
      <!-----------------------次级页面-------------------------------------------------->
       
      <vxe-modal
        v-if="dialogFormVisibleAdd"
        v-model="dialogFormVisibleAdd"
        title="船舶动态代码新增"
        width="900"
        height="500"
        resize
        storage
      >
        <Add
          :datanow="dataNext" @dictItemVisible="dictItemVisible" @selectList="getDate"
        ></Add>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleedit"
        v-model="dialogFormVisibleedit"
        title="泊位代码信息修改"
        width="900"
        height="500"
        resize
        storage
      >
         <Edit
          :datanow="dataNext"
          @dictItemVisible="dictItemVisible"
          @selectListplan="getDate"
        ></Edit>
      </vxe-modal>
     
    </basic-container>
  </div>
</template>

<script>
import { fetchList,deletOne} from "@/api/caccentre/cshipstat";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import EditDown from "../../baseUtile/EditDown";
import Add from "./add";
import Edit from "./Edit";
export default {
  components: {Add,EditDown,Edit},
  name: "caccentre-cshipstat-index",
  data() {
    return {
      formObj: {        
      },
      formT: {},
      tableData: [],
      tableData2: [],
      dateNow: {},
      dataHeadNow: {},
      dialogFormVisibleedit: false,
      dialogFormVisibleAdd: false,
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      tablePage2: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      tableload: false,
      tableload2: false,
      
     
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
          this.formObjs
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
    add(type) {
      
      if (type === "edit") {
        if (this.dataHeadNow != null && this.dataHeadNow.statCod != null) {          
          this.dataNext = this.dataHeadNow;
          this.dialogFormVisibleedit = true;
        } else {
          this.$message.error("请选择一个数据进行修改");
        }
      } else if (type === "add") {
        this.dataNext = {};
      this.dialogFormVisibleAdd = true;
      }
    },
    deleteStatus() {
      if (this.dataHeadNow == null || this.dataHeadNow.statCod == null) {
        this.$message.error("请选择一条动态进行删除");
        return;
      }
      this.$XModal.confirm("您确定要删除吗？").then((type) => {
        if (type == "confirm") {
          deletOne(this.dataHeadNow.statCod)
            .then((response) => {
              this.$message.success("删除成功");
              this.getDate();
            })
            .catch(() => {});
        }
      });
    },
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
      this.dialogFormVisibleAdd = false;
    },
    
    cellDBLClickEvent({ row }) {
      this.dateNow = row;
      this.dialogFormVisibleView = true;
    },
   
    rowclick({ row }) {
      this.dataHeadNow = row;
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
.transport-base-index {
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
.grid-content {
  margin: 10px auto;
}
</style>>