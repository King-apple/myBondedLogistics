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
  <!-- 需求信息查询 -->
  <template>
    <div class="demandinfo">
      <basic-container>
        <vxe-form :data="formObj">        
          
         <vxe-form-item title="需求号" field="ydid" size="mini" span="3" >
            <vxe-input
            v-model="formObj.ydid"
            clearablez
          ></vxe-input> 
         </vxe-form-item>                
          <vxe-form-item title="预约号" field="xqslh" size="mini" span="3" >
            <vxe-input
            v-model="formObj.xqslh"
            clearable
          ></vxe-input>      
        </vxe-form-item>
          <vxe-form-item title="品名" field="hzpm" size="mini" span="3" > 
            <vxe-input
            v-model="formObj.hzpm"
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
        <vxe-table-column type="seq" title="序号" width=3%></vxe-table-column>
        <vxe-table-column
          field="ydid"
          title="需求号"
          
        ></vxe-table-column>
        <vxe-table-column
          field="xqslh"
          title="预约号"
          
        ></vxe-table-column>
        
        <vxe-table-column
          field="ysfs"
          title="运单运输方式"
          
        ></vxe-table-column>
        <vxe-table-column
          field="zcrq"
          title="需求装车日期"
         
        ></vxe-table-column>
        <vxe-table-column
          field="fzhzzm"
          title="发站名称"
          
        ></vxe-table-column>
        <vxe-table-column
          field="dzhzzm"
          title="到站名称"
          
        ></vxe-table-column>      
          
        <vxe-table-column
          field="hzpm"
          title="品名汉字1"
          
        ></vxe-table-column>   
        <vxe-table-column
          field="fhdwmc"
          title="发货单位名称"
          
        ></vxe-table-column>   
        <vxe-table-column
          field="shdwmc"
          title="收货单位名称"
          
        ></vxe-table-column>   
        <vxe-table-column
          field="ztgjjc"
          title="状态轨迹简称"
          
        ></vxe-table-column>      
        <vxe-table-column
        field="lastModifyTime"
        title="时间戳"
       
      ></vxe-table-column> 
      <vxe-table-column
          field="ifdzlh"
          title="是否电子领货"
         
        ></vxe-table-column> 
        <vxe-table-column
          field="tyrjzsx"
          title="托运人记载事项"
          
        ></vxe-table-column> 
        <vxe-table-column
          field="fplx"
          title="增值税票类型"
          
        ></vxe-table-column> 
        <vxe-table-column
          field="spfmc"
          title="受票方名称"
          
        ></vxe-table-column> 
        <vxe-table-column
          field="nsrsbh"
          title="纳税人识别号"
         
        ></vxe-table-column> 
        <vxe-table-column
          field="nsrdz"
          title="受票方地址"
          
        ></vxe-table-column> 
        <vxe-table-column
        field="nsrdh"
        title="受票方电话"
        
      ></vxe-table-column>
      <vxe-table-column
          field="nsrkhh"
          title="开户行名称"
          
        ></vxe-table-column>
        <vxe-table-column
          field="nsrzh"
          title="开户行帐号"
          
        ></vxe-table-column>
        <vxe-table-column
          field="tbqydm"
          title="提报企业代码"
          
        ></vxe-table-column>
        <vxe-table-column
          field="tbqymc"
          title="提报企业名称"
          
        ></vxe-table-column>
        <vxe-table-column
          field="fzyxhz"
          title="发专用线汉字"
          
        ></vxe-table-column>
        <vxe-table-column
          field="dzyxhz"
          title="到专用线汉字"
          
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
    import { getPage } from "@/api/trainofgr/demandinfo";
    import { mapGetters } from "vuex";
    import { Loading } from "element-ui";
    import Vue from "vue";
    import VXETable from "vxe-table";
    
    export default {
      
      name: "demandinfo",
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