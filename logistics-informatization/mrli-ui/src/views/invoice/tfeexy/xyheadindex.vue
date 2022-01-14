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
  <div class="invoice-xyhead-xyhead">
    <basic-container>
      <vxe-form :data="formObj">
        <vxe-form-item title="发票号码" field="fapiaoNo" size="mini" span="3">
          <vxe-input
            v-model="formObj.fapiaoNo"
            clearable
          ></vxe-input>
        </vxe-form-item>

           <vxe-form-item title="单据号" field="ywdh" size="mini" span="3">
          <vxe-input
            v-model="formObj.ywdh"
            clearable
          ></vxe-input>
        </vxe-form-item>


      <vxe-form-item title="备注" field="bz" size="mini" span="3"  :title-prefix="{ message: '船号、船名的模糊查询！', icon: 'fa fa-exclamation-circle' }">
          <vxe-input
            v-model="formObj.bz"
            clearable
          ></vxe-input>
        </vxe-form-item>


      <vxe-form-item title="购方名称" field="gfmc" size="mini" span="6">
          <vxe-input
            v-model="formObj.gfmc"
            clearable
          ></vxe-input>
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

            <!-- <vxe-button  type="text"  class="fa fa-refresh"  @click="hongcongrecord()" >发票红冲</vxe-button>-->
            <vxe-button  type="text"  class="fa fa-refresh"   @click="updateXfdh()">发票红冲</vxe-button>
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
        :checkbox-config="{range: true}"
         highlight-hover-row
         highlight-current-row
      >

<vxe-table-column type="checkbox" width="60"></vxe-table-column>
<vxe-table-column field="fpzl" title="发票种类"  width="80" align="left"></vxe-table-column>
<vxe-table-column field="fapiaoDm" title="发票代码"  width="100" align="left"></vxe-table-column>
<vxe-table-column field="fapiaoNo" title="发票号码"  width="100" align="left"></vxe-table-column>
<vxe-table-column field="ywdh" title="单据号"  width="130" align="left"></vxe-table-column>
<vxe-table-column field="gfmc" title="购方名称"  width="300" align="left"></vxe-table-column>
<vxe-table-column field="jshj" title="价税合计" align="right"  width="80"></vxe-table-column>
<vxe-table-column field="bz" title="备注"  width="300" align="left"></vxe-table-column>
<vxe-table-column field="gfsh" title="购方税号"  width="150" align="left"></vxe-table-column>
<vxe-table-column field="kpr" title="开票人"  width="80" align="left"></vxe-table-column>
<vxe-table-column field="fapiaoTime" title="开票时间"  width="150" align="left"></vxe-table-column>
<vxe-table-column field="hcTime" title="冲红时间"  width="150" align="left"></vxe-table-column>
<vxe-table-column field="hcOpt" title="冲红人"  width="150" align="left"></vxe-table-column>
<vxe-table-column field="fapiaoDmhc" title="发票代码红冲"  width="150" align="left"></vxe-table-column>
<vxe-table-column field="fapiaoHmhc" title="发票号码红冲"  width="150" align="left"></vxe-table-column>
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



    <vxe-modal   
      v-if="dialogFormVisibleXfdhView"
        v-model="dialogFormVisibleXfdhView"
        title="信息维护"
        width="800"
        height="400"
        resize
        storage>
 <vxe-form :data="selectRow" size="mini" title-width="150">
         <vxe-form-item title="专用发票红字信息表编号[货主认证过的发票此项必填]" field="xxbNo" size="mini" span="24" >
        <vxe-input v-model="selectRow.xxbNo"></vxe-input>
         </vxe-form-item>
        <vxe-form-item title="红冲原因" field="hcYy" size="mini" span="24" >
           <vxe-input v-model="selectRow.hcYy"></vxe-input>
         </vxe-form-item>
       </vxe-form>


        <div align="center">
        <el-button type="primary" size="mini" icon="el-icon-check" @click="updateXfdhNow">确定</el-button>
         <el-button size="mini" icon="el-icon-close" plain @click="dictItemVisible">取消</el-button>
        </div>



      </vxe-modal>




  <el-drawer
  title="发票明细"
  :visible.sync="drawer"
  :with-header="false"
  size="30%"
  direction="btt">
  <span>{{selectRow.ywdh}}</span>

   <vxe-toolbar perfect size="mini">
            <template v-slot:buttons>
              <vxe-button
                type="text"
                >发票明细</vxe-button
              >
            </template>
          </vxe-toolbar>
 <vxe-table
            border
            show-overflow
            keep-source
            show-footer
            highlight-hover-row
            highlight-current-row
            size="mini"
            :data="tableDataBody"
            :loading="tableloadBody"
            :round="true"
            align="center"
            height="340px"
            ref="tableloadBodyRef"
            :edit-config="{trigger: 'click', mode: 'row'}"
          >
          <vxe-table-column field="pkid" title="pkid"  align="left" ></vxe-table-column>
           <vxe-table-column field="xmmc" title="项目名称"   align="left" ></vxe-table-column>
            <vxe-table-column field="ggxh" title="货名"   align="left" ></vxe-table-column>
            <vxe-table-column field="jldw" title="计量单位" align="left" ></vxe-table-column>
            <vxe-table-column field="xmsl" title="项目数量"   align="left" ></vxe-table-column>
 <vxe-table-column field="xmdj" title="项目单价"  align="left" ></vxe-table-column>
  <vxe-table-column field="bhsje" title="不含税金额"   align="left" ></vxe-table-column>
   <vxe-table-column field="se" title="税额"   align="left" ></vxe-table-column>

          </vxe-table>
    </el-drawer>

    </basic-container>
  </div>
</template>

<script>
import {
  pageListOfSql,
  commitXy,
  hongchongXy,
  fetchBodyListByywdh,
  hongchongXy2
} from "@/api/invoice/xyhead";

import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import EditDown from "../../baseUtile/EditDown";
export default {
  components: { EditDown },
  name: "portside-yqbak-index",
  data() {
    return {
      drawer: false,
      formObj: {},
      dialogFormVisibleedit: false,
      tableData: [],
      selectRow: {},
      tableloadBody: false,
      tableDataBody: [],
      dataNow: {},
      selectXy: {},
      tzdbh:null,
      hcyy:null,
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      tableload: false,
     dialogFormVisibleXfdhView: false
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
      this.tableload = true;
      pageListOfSql(
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

//红冲
     updateXfdh() {
      let selectRecords = this.$refs.tableCar.getCheckboxRecords();
      if (selectRecords.length!= 1) {
        this.$message.error("请选择一条");
        return;
      }
      this.selectRow=selectRecords[0];
      this.dialogFormVisibleXfdhView = true;
    },
//确定
 updateXfdhNow() {
   if(this.selectRow.hcYy==null){
      this.$message.error("必须写明红冲原因");
     return;
   }
      hongchongXy2(this.selectRow)
        .then(data => {
          this.dialogFormVisibleXfdhView = false;
          this.$message.success("红冲成功");
          this.getXyHead();//重新加载查询页
        })
        .catch(() => {});
    },
 getXyHead() {
      this.tableloadXyHead = true;
      fetchXyList(this.selectRow.pkid)
        .then(response => {
          this.tableDataXyHead = response.data.data;
          this.tableloadXyHead = false;
          this.selectXy = null;
        })
        .catch(() => {
          this.tableloadXyHead = false;
        });
    },


    rowclick({ row }) {
      this.drawer = true;
      this.selectRow = row;
      this.getBody(row.ywdh);
    },


    //红冲
    hongcongrecord() {
      let selectRecords = this.$refs.tableCar.getCheckboxRecords();
      if (selectRecords.length == 0) {
        this.$message.error("请选择一条");
        return;
      }

      this.$XModal.confirm("您确定要红冲发票吗？").then(type => {

        if (type == "confirm") {
          let loadingInstance = Loading.service({
            fullscreen: true,
            text: "正在红冲………"
          });
          for (var m = 0; m < selectRecords.length; m++) {

            console.log(selectRecords[m].pkid)

            hongchongXy(selectRecords[m].pkid)
              .then(data => {
                this.$message.success("红冲成功");
                this.getDate();
                loadingInstance.close();
              })
              .catch(() => {
                loadingInstance.close();
              });
          }
        }
      });
    },

    //获得t_fee_body_xy体数据
    getBody(ywdh) {
      this.tableloadBody = true;
      fetchBodyListByywdh(ywdh)
        .then(response => {
          this.tableDataBody = response.data.data;
          if (this.tableDataBody != null && this.tableDataBody.length > 0) {
            for (var i = 0; i < this.tableDataBody.length; i++) {
              if (this.tableDataBody[i].temp3 == null) {
                this.tableDataBody[i].temp3 = 1;
              }
              this.tableDataBody[i].pkid = i;
            }
          }
          this.tableloadBody = false;
        })
        .catch(() => {
          this.tableloadBody = false;
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
    resetForm() {},
    add() {
      this.dataNow = null;
      this.dialogFormVisibleedit = true;
    },
    edit(row) {
      this.dataNow = row;
      this.dialogFormVisibleedit = true;
    },
    delete() {},
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
      this.dialogFormVisibleXfdhView = false;
      this.dataNow = null;
    }

  }
};
</script>
<style lang="scss" scoped>
.invoice-xyhead-xyhead {
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