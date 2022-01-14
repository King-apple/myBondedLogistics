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
  <!-- 到达信息查询 -->
  <template>
    <div class="arriveinfo">
      <basic-container>
        <vxe-form :data="formObj">        
          
          <vxe-form-item title="需求号" field="ydid" size="mini" span="3" >
             <vxe-input
             v-model="formObj.ydid"
             clearablez
           ></vxe-input> 
          </vxe-form-item>        
          <vxe-form-item title="运单号码" field="ydhm" size="mini" span="3" >
            <vxe-input
            v-model="formObj.ydhm"
            clearablez
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
        <vxe-table-column type="seq" title="序号"width="50" ></vxe-table-column>
        <vxe-table-column
          field="ydid"
          title="需求号"
          
        ></vxe-table-column>
        <vxe-table-column
          field="ydhm"
          title="运单号码"
          
        ></vxe-table-column>
        
        <vxe-table-column
          field="ifdd"
          title="是否到达"
          
        ></vxe-table-column>
        <vxe-table-column
          field="ddsj"
          title="到达时间"
         
        ></vxe-table-column>
        <vxe-table-column
          field="ddcc"
          title="到达车次"
          
        ></vxe-table-column>
        
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
    import { getPage } from "@/api/trainofgr/arriveinfo";
    import { mapGetters } from "vuex";
    import { Loading } from "element-ui";
    import Vue from "vue";
    import VXETable from "vxe-table";
    
    export default {
      
      name: "waybillinfo",
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