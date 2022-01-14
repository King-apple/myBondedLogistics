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
  <!-- 内外贸船舶在港停时 -->
  <template>
    <div class="tradetime">
      <basic-container>
        <vxe-form :data="formObj">        
          <vxe-form-item title="统计月份" field="month" size="mini" span="3" >
            <vxe-input
              v-model="formObj.month"
              placeholder="统计月份"
              type="month"
              valueFormat="yyyyMM"
            ></vxe-input> 
         </vxe-form-item>  
         <vxe-form-item span="4" size="mini" align="center">
          <vxe-button   status="primary" icon="fa fa-search"   @click="selectTable()"  >查询</vxe-button>
          <vxe-button type="reset" icon="fa fa-repeat" @click="resetForm">重置</vxe-button>
        </vxe-form-item>          
          </vxe-form>       
        <vxe-table
          border
          show-overflow
          keep-source
          resizable
          size="mini"
          :data="tableData"
          :loading="tableload"
          :round="true"          
          align="center"
          height="700px"
          ref="tableCar"
        >
        <vxe-table-column type="seq" title="序号" width=4%></vxe-table-column>        
        <vxe-table-colgroup title="项目">
         <vxe-table-column field="itemCod" title="代码"></vxe-table-column>        
         <vxe-table-column field="itemNam" title="名称"></vxe-table-column>      
        </vxe-table-colgroup>
        <vxe-table-column field="totUnitNam" title="单位"></vxe-table-column>        
        <vxe-table-column field="totalWgt" title="总计"></vxe-table-column>        
        <vxe-table-colgroup title="外贸船">
          <vxe-table-column field="forShipWgt"  title="合计"></vxe-table-column>
          <vxe-table-column field="forForWgt" title="外国船"></vxe-table-column>    
          <vxe-table-column field="forNatWgt"  title="本国船"></vxe-table-column>   
          <vxe-table-column field="forTeuWgt"  title="集装箱"></vxe-table-column>            
        </vxe-table-colgroup>
        <vxe-table-column field="inShipWgt"  title="内贸船"></vxe-table-column>         
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
    import { getPage } from "@/api/statistical/tradetime";
    import { mapGetters } from "vuex";
    import { Loading } from "element-ui";
    import Vue from "vue";
    import VXETable from "vxe-table";
    
    export default {
      
      name: "tradetime",
      data() {
        return {
          formObj: {
            
          },
          dialogFormVisibleedit: false,
          tableData: [],
          dataNow: {},
          tablePage: {
            total: 0, // 总页数
            currentPage: 1, // 当前页数
            pageSize: 20 // 每页显示多少条
          },
          tableload: false
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
          getPage(
            
            Object.assign(
              {
                current: this.tablePage.currentPage,
                size: this.tablePage.pageSize
              },
              this.formObj,            
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
         
        },
        
        dictItemVisible() {
          this.dialogFormVisibleedit = false;
          this.dataNow = null;
        }
      }
    };
    </script>
    <style lang="scss" scoped>
  
    </style>