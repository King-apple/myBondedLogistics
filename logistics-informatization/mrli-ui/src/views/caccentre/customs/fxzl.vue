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
    <div class="customs">
      <basic-container>
        <vxe-form :data="formObj">
         
          <vxe-form-item title="时间" field="timeBegin" size="mini" span="3">
            <vxe-input
              v-model="formObj.timeBegin"
              placeholder="时间"
              type="date"
              valueFormat="yyyyMMdd"
            ></vxe-input>
          </vxe-form-item>-
          <vxe-form-item title="-" field="timeEnd" size="mini" span="3">
            <vxe-input
              v-model="formObj.timeEnd"
              placeholder="时间"
              type="date"
              valueFormat="yyyyMMdd"
            ></vxe-input>
          </vxe-form-item>
          <vxe-form-item title="船名" field="rmField2" size="mini" span="3" >
            <vxe-input
            v-model="formObj.rmField2"
            clearable
          ></vxe-input> 
         </vxe-form-item>                
          <vxe-form-item title="航次" field="rmField3" size="mini" span="3" >
            <vxe-input
            v-model="formObj.rmField3"
            clearable
          ></vxe-input>      
        </vxe-form-item>
          <vxe-form-item title="提单号" field="rmField4" size="mini" span="3" > 
            <vxe-input
            v-model="formObj.rmField4"
            clearable
          ></vxe-input>
          </vxe-form-item>      
      
          <vxe-form-item span="4" size="mini" align="center">
            <vxe-button   status="primary" icon="fa fa-search"   @click="selectTable()"  >查询</vxe-button>
            <vxe-button type="reset" icon="fa fa-repeat" @click="resetForm">重置</vxe-button>
          </vxe-form-item>
          
        </vxe-form>
        <!-- <vxe-toolbar perfect size="mini">
          <template v-slot:buttons>
              <vxe-button type="text" class="fa fa-plus" @click="add()"
              >新增</vxe-button
            >
          </template>
        </vxe-toolbar> -->
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
        <vxe-table-column type="seq" title="序号" width=5%></vxe-table-column>
        <vxe-table-column
          field="rmField2"
          title="船名"
          width=15%
        ></vxe-table-column>
        <vxe-table-column
          field="rmField3"
          title="航次"
          width=15%
        ></vxe-table-column>
        
        <vxe-table-column
          field="rmField4"
          title="提单号"
          width=20%
        ></vxe-table-column>
        <vxe-table-column
          field="rmField13"
          title="重量"
          width=10%
        ></vxe-table-column>
        <vxe-table-column
          field="rmField14"
          title="件数"
          width=10%
        ></vxe-table-column>
        <vxe-table-column
          field="rmField12"
          title="放行指令"
          width=10%
        ></vxe-table-column>      
       
        <vxe-table-column
          field="rmField5"
          title="操作时间"
          width=15%
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
    import { getPage } from "@/api/caccentre/fxzl";
    import { mapGetters } from "vuex";
    import { Loading } from "element-ui";
    import Vue from "vue";
    import VXETable from "vxe-table";
    import EditDown from "../../baseUtile/EditDown";
    export default {
      components: { EditDown },
      name: "customs",
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