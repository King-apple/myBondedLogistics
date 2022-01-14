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
    <div class="caccentre-zsmstorstatus">
      <basic-container>
        <vxe-form :data="formObj">
         
          <vxe-form-item title="统计时间" field="tjTime" size="mini" span="5">
            <vxe-input
              v-model="formObj.tjTime"
              placeholder="统计时间"
              type="date"
              valueFormat="yyyy-MM-dd"
            ></vxe-input>
          </vxe-form-item>
          <vxe-form-item title="查询类型" field="comp" size="mini" span="3" >
          <vxe-select v-model="formObj.comp" @change="changGq">            
            <vxe-option value="公司" label="公司"></vxe-option>
            <vxe-option value="港区" label="港区"></vxe-option>
          </vxe-select>          
        </vxe-form-item>
        
       <vxe-form-item title="公司" field="unitCod" size="mini" span="4" v-if="formObj.comp=='公司'">
            <EditDown
            :objs="formObj"
            :property="downTableOption.unitNam"
            :tableColumns="downTableOption.unitColum"
            :urlapi="downTableOption.uniturl"
            :returnApi="downTableOption.unitreturnApi"
            sizenow="mini"
          ></EditDown>
      </vxe-form-item>
      <vxe-form-item title="港区" field="ykportCod" size="mini" span="4" v-if="formObj.comp=='港区'">
        <vxe-select v-model="formObj.ykportCod" >         
          <vxe-option value="Y0003" label="盘锦港区"></vxe-option>
          <vxe-option value="Y0004" label="鲅鱼圈港区"></vxe-option>
          <vxe-option value="Y0005" label="绥中港区"></vxe-option>          
        </vxe-select> 
        </vxe-form-item>
          <vxe-form-item span="4" size="mini" align="center">
            <vxe-button   status="primary" icon="fa fa-search"   @click="selectTable()"  >查询</vxe-button>
            <vxe-button type="reset" icon="fa fa-repeat" @click="resetForm">重置</vxe-button>
          </vxe-form-item>
          <vxe-form-item>
            <vxe-button  status="primary"  @click="insert()" >数据处理</vxe-button>
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
        <vxe-table-column type="seq" title="序号" width="50"></vxe-table-column>
        <vxe-table-column
          field="ykportNam"
          title="港区"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="unitNam"
          title="公司"
          width="200"
        ></vxe-table-column>
        
        <vxe-table-column
          field="workTim"
          title="接收日期"
          width="140"
        ></vxe-table-column>
        <vxe-table-column
          field="inoutNam"
          title="进出口"
          width="90"
        ></vxe-table-column>
        <vxe-table-column
          field="tradeNam"
          title="内外贸"
          width="90"
        ></vxe-table-column>
        <vxe-table-column
          field="cargoKindCod"
          title="货类代码"
          width="140"
        ></vxe-table-column>      
       
        <vxe-table-column
          field="cargoKindNam"
          title="货类名称"
          width="120"
        ></vxe-table-column>
        <vxe-table-column
          field="yardNam"
          title="库场"
          width="80"
        ></vxe-table-column>
        <vxe-table-column
          field="weightWgt"
          title="重量"
          width="100"
        ></vxe-table-column>
        
      </vxe-table-column>
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
        
      </basic-container>
    </div>
  </template>
  
  <script>
  import {
     getPage  ,
     dataProcess   

  } from "@/api/caccentre/zsmstorstatus";
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
        formObj: { 
          
          unitNam:null,
          unitCod:null,   
          ykportCod:null,
        },
        downTableOption: {
        //单位
        unitNam: "unitNam",
        unitColum: [
          { field: "UNIT_COD", title: "单位代码" },
          { field: "UNIT_NAM", title: "单位名称" },
        ],
        unitreturnApi: [
          { field: "UNIT_COD", returnName: "unitCod" },
          { field: "UNIT_NAM", returnName: "unitNam" },
        ],
        uniturl: "/basecode/basecod/getUnitNam",
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
      changGq(value){
         if(value.value=='公司'){
          this.formObj.unitCod=null;
          this.formObj.unitNam=null;
         }else if(value.value=='港区'){
            this.formObj.ykportCod=null;
         }
      },
      
      insert(){
        
        this.insertData();          
      },
      insertData(){       
        let loadingInstance = Loading.service({
        fullscreen: true,
        text: "正在处理………",
      }); 
      dataProcess(
        
        Object.assign({beginDate:this.formObj.tjTime})
       
      )
       .then((data) => {
           this.$message.success("数据处理成功"); 
           loadingInstance.close();
         } )
         
         .catch(() => {
           loadingInstance.close();
         });
      },    
        
      resetForm() {
       
      },
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
        this.dataNow = null;
      }
    }
  };
  </script>
  <style lang="scss" scoped>
  .caccentre-zsmstorstatus-index {
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
  </style>