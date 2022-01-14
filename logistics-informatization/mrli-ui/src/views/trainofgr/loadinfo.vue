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
  <!-- 装车信息查询 -->
  <template>
    <div class="loadinfo">
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
             clearable
           ></vxe-input>      
         </vxe-form-item>
           <vxe-form-item title="主车号" field="zch" size="mini" span="3" > 
             <vxe-input
             v-model="formObj.zch"
             clearable
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
          field="ifzc"
          title="是否装车"
          
        ></vxe-table-column>
        <vxe-table-column
          field="zcpbh"
          title="篷布号"
         
        ></vxe-table-column>
        <vxe-table-column
          field="zccyrqdzl"
          title="承运人确定重量"
          
        ></vxe-table-column>
        <vxe-table-column
          field="zcwbsj"
          title="装车完毕时间"
          
        ></vxe-table-column>
        <vxe-table-column
          field="zcsfh"
          title="施封号"
          
        ></vxe-table-column>
        <vxe-table-column
          field="zcsj"
          title="装车时间"
          
        ></vxe-table-column>
        <vxe-table-column
          field="zch"
          title="主车号"
          
        ></vxe-table-column>
        <vxe-table-column
        field="zcddsj"
        title="车辆调到时间"
        
      ></vxe-table-column>
      <vxe-table-column
          field="zcczcx"
          title="车种车型"
          
        ></vxe-table-column>
        <vxe-table-column
          field="zcpbs"
          title="篷布数"
          
        ></vxe-table-column>
        <vxe-table-column
          field="zckssj"
          title="装车开始时间"
          
        ></vxe-table-column>
        <vxe-table-column
          field="zcsfs"
          title="施封数"
          
        ></vxe-table-column>
        <vxe-table-column
          field="zchcbz"
          title="车辆标重"
          
        ></vxe-table-column>
        <vxe-table-column
          field="zcdcsj"
          title="车辆调出时间"
          
        ></vxe-table-column>
        <vxe-table-column
          field="zcxh2"
          title="箱号2"
          
        ></vxe-table-column>
        <vxe-table-column
        field="zcxh1"
        title="箱号1"
        
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
    import { getPage } from "@/api/trainofgr/loadinfo";
    import { mapGetters } from "vuex";
    import { Loading } from "element-ui";
    import Vue from "vue";
    import VXETable from "vxe-table";
    
    export default {
      
      name: "loadinfo",
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