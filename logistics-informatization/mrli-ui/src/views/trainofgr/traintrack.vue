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
  <!-- 车号追踪 -->
  <template>
    <div class="traintrack">
      <basic-container>
        <vxe-form :data="formObj">        
          <vxe-form-item title="车号" field="carNo" size="mini" span="3" >
            <vxe-input
            v-model="formObj.carNo"
            
          ></vxe-input> 
         </vxe-form-item>                
          <vxe-form-item title="车次" field="trainNo" size="mini" span="3" >
            <vxe-input
            v-model="formObj.trainNo"
            
          ></vxe-input>      
        </vxe-form-item>
          <vxe-form-item title="品名" field="cdyName" size="mini" span="3" > 
            <vxe-input
            v-model="formObj.cdyName"
            
          ></vxe-input>
          </vxe-form-item>
          <vxe-form-item title="发站" field="cdyStation" size="mini" span="3" > 
            <vxe-input
            v-model="formObj.cdyStation"
            
          ></vxe-input>
          </vxe-form-item>
          <vxe-form-item title="货票号" field="wbNbr" size="mini" span="3" > 
            <vxe-input
            v-model="formObj.wbNbr"
            
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
        <vxe-table-column type="seq" title="序号" width=3%></vxe-table-column>
        
        <vxe-table-column
          field="carNo"
          title="车号"
          
        ></vxe-table-column>
        <vxe-table-column
          field="trainNo"
          title="车次"
          width=4%
        ></vxe-table-column>        
        <vxe-table-column
          field="cdyAdm"
          title="发局"
          
        ></vxe-table-column>
        <vxe-table-column
          field="cdyStation"
          title="发站"
         
        ></vxe-table-column>
        <vxe-table-column
          field="arrDep"
          title="已过/在"
          width=4%
        ></vxe-table-column>
        <vxe-table-column
          field="eventAdm"
          title="报告局"
          
        ></vxe-table-column>      
          
        <vxe-table-column
          field="eventStation"
          title="报告站"
          
        ></vxe-table-column>   
        <vxe-table-column
          field="eventDate"
          title="报告时间"
          
        ></vxe-table-column>   
        <vxe-table-column
          field="destAdm"
          title="终到局"
          
        ></vxe-table-column>   
        <vxe-table-column
          field="destStation"
          title="终到站"
          
        ></vxe-table-column>      
        <vxe-table-column
        field="cdyName"
        title="品名"
       
      ></vxe-table-column> 
      <vxe-table-column
          field="shpName"
          title="托运人"
         
        ></vxe-table-column> 
        <vxe-table-column
          field="conName"
          title="收货人"
          
        ></vxe-table-column> 
        <vxe-table-column
          field="carLe"
          title="空重"
          width=3%
        ></vxe-table-column> 
        <vxe-table-column
          field="carKind"
          title="车种"
          width=4%
        ></vxe-table-column> 
        <vxe-table-column
          field="carType"
          title="车型"
          width=4%
        ></vxe-table-column> 
        <vxe-table-column
          field="wbNbr"
          title="货票号"
          
        ></vxe-table-column> 
        <vxe-table-column
        field="wbId"
        title="货票ID"
        
      ></vxe-table-column>
      <vxe-table-column
          field="yjdalc"
          title="预计到达里程"
          
        ></vxe-table-column>
        <vxe-table-column
          field="yjdasj"
          title="预计到达时间"
          
        ></vxe-table-column>
        <vxe-table-column
          field="trainOrder"
          title="车辆排序"
          width=4% 
        ></vxe-table-column>
        <vxe-table-column
          field="jybz"
          title="编组"
          width=3%
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
    import { getPage } from "@/api/trainofgr/traintrack";
    import { mapGetters } from "vuex";
    import { Loading } from "element-ui";
    import Vue from "vue";
    import VXETable from "vxe-table";
    
    export default {
      
      name: "traintrack",
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