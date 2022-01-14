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
    <div class="shipstatuszs">
      <basic-container>          
        <vxe-form :data="formObj">
            <vxe-form-item title="船号：" field="shipNo" size="mini" span="6">
                <EditDown
                  :objs="formObj"
                  :property="downTableOption.shipNo"
                  :tableColumns="downTableOption.subNamColum"
                  :urlapi="downTableOption.subNamurl"
                  :returnApi="downTableOption.subNamreturnApi"
                  sizenow="mini"
                ></EditDown>
              </vxe-form-item>
          <vxe-form-item span="4" size="mini" align="center">
            <vxe-button status="primary" icon="fa fa-search" @click="getDate()">查询</vxe-button>
          </vxe-form-item>
        </vxe-form>
        <el-row :gutter="15">
          <el-col :span="7">
            <!-- <vxe-toolbar perfect size="mini">
              <template v-slot:buttons>
                <vxe-button type="text" class="fa fa-plus" @click="add()">新增</vxe-button>
                <vxe-button type="text" icon="fa fa-floppy-o" @click="edit()">修改</vxe-button>
              </template>
            </vxe-toolbar> -->
            <vxe-table
              border
              show-overflow
              highlight-current-row
              size="mini"
              :data="tableData"
              :loading="tableload"
              :round="true"
              align="center"
              height="600px"
              ref="tableOne"
              @cell-click="rowclick"
            >
              <vxe-table-column   field="shipNo" title="船号"width="20%"></vxe-table-column>
              <vxe-table-column   field="shipNam" title="船名" width="25%"></vxe-table-column>
              <vxe-table-column   field="shipTypeName" title="标志" width="20%"></vxe-table-column>
              <vxe-table-column   field="toPortTim" title="抵港时间" width="35%"></vxe-table-column>
            </vxe-table>
            
            <vxe-pager
            perfect
            size="mini"
            :loading="tableload"
            :current-page="tablePage.currentPage"
            :page-size="tablePage.pageSize"
            :total="tablePage.total"
            :layouts="['PrevPage', 'JumpNumber', 'NextPage', 'FullJump', 'Sizes', 'Total']"
            @page-change="handlePageChange"
          ></vxe-pager>
          </el-col>
          <el-col :span="17">
            
            <vxe-table
              border
              show-overflow
              keep-source
              size="mini"
              :data="tableDataTwo"
              :loading="tableloadTwo"
              :round="true"
              align="center"
              height="600px"
              ref="tableTwo"
              :edit-rules="tableTwovalidRules"
              :edit-config="{trigger: 'click', mode: 'row'}"
              :mouse-config="{selected: true}"
              :checkbox-config="{range: true}"
            >
              
             
              <vxe-table-column field="endTim" title="时间" width="6%"></vxe-table-column>
              <!-- <vxe-table-column field="unitCod" title="作业" width="50"></vxe-table-column> -->
              <vxe-table-column field="unitNam" title="作业单位" width="23%" ></vxe-table-column>
              <vxe-table-column field="classId" title="班次" width="4%" ></vxe-table-column>
              <vxe-table-column field="unloadId" title="装卸别" width="5%"></vxe-table-column>
              <vxe-table-column field="brithCod" title="泊位代码" width="5%"></vxe-table-column>
              <vxe-table-column field="brttnNam" title="泊位名称"width="5%" ></vxe-table-column>
              <vxe-table-column field="cOne" title="结余1舱" width="5%"></vxe-table-column>
              <vxe-table-column field="cTwo" title="结余2舱"width="5%" ></vxe-table-column>
              <vxe-table-column field="cThree" title="结余3舱" width="5%"></vxe-table-column>
              <vxe-table-column field="cFour" title="结余4舱" width="5%"></vxe-table-column>
              <vxe-table-column field="cFive" title="结余5舱" width="5%"></vxe-table-column>
              <vxe-table-column field="cSix" title="结余6舱" width="5%"></vxe-table-column>
              <vxe-table-column field="cSeven" title="结余7舱" width="5%"></vxe-table-column>
              <vxe-table-column field="cEight" title="结余8舱" width="5%"></vxe-table-column>
              <vxe-table-column field="cNine" title="结余9舱" width="5%"></vxe-table-column>
              <vxe-table-column field="cJiaban" title="甲板" width="5%"></vxe-table-column>
              <vxe-table-column field="c08" title="08完成量" width="5%"></vxe-table-column>
              <vxe-table-column field="c20" title="20完成量" width="5%"></vxe-table-column>
              <vxe-table-column field="cAll" title="合计" width="5%"></vxe-table-column>

            </vxe-table>
          </el-col>
        </el-row>
        <!-- <vxe-modal
          v-if="dialogFormVisibleedit"
          v-model="dialogFormVisibleedit"
          title="基本信息维护"
          width="500"
          height="400"
          resize
          storage
        >
          <EditHead :datanow="dataNext" @dictItemVisible="dictItemVisible" @selectList="getDate"></EditHead>
        </vxe-modal> -->
      </basic-container>
    </div>
  </template>
  
  <script>
  import {
    fetchList,fetchBodyList,
  } from "@/api/caccentre/shipstatuszs";
  import { mapGetters } from "vuex";
  import { Loading } from "element-ui";
  import Vue from "vue";
  import VXETable from "vxe-table";
  import EditDown from "../../baseUtile/EditDown";
//   import EditHead from "./editHead";
  export default {
    // components: { EditHead },
    components: { EditDown },
    name: "shipstatuszs",
    data() {
      return {
        formObj: {
          shipNo:null , 
              },
        dataHeadNow: {},
        dataNext: {},
        dialogFormVisibleedit: false,
        tableData: [],
        tableDataTwo: [],
        tablePage: {
          total: 0, // 总页数
          currentPage: 1, // 当前页数
          pageSize: 20 // 每页显示多少条
        },
        tableload: false,
        tableloadTwo: false,
        // tableTwovalidRules: {
        //   zyUnitName: [{ required: true, message: "必须选择船号" }]
        // },
        downTableOption: {
        //船名下拉菜单展示项
        subNamColum: [
          { field: "shipNo", title: "船号" },
          { field: "shipNam", title: "船名" },          
        ],
        //下拉菜单的路径        
        subNamurl: "/caccentre/ship/getShipNoList",
        shipNo: "shipNo",
        subNamreturnApi: [{ field: "shipNo", returnName: "shipNo" }],
      },
      };
    },
    computed: {
      ...mapGetters(["permissions"])
    },
    created() {
      this.getDate();
    },
    methods: {
      getDate() {
        this.tableload = true;
        fetchList(
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
      getBody() {
        this.tableloadTwo = true;
        
        fetchBodyList(this.dataHeadNow.shipNo)
        
          .then(response => {
            this.tableDataTwo = response.data.data;
            this.tableloadTwo = false;
          })
          .catch(() => {
            this.tableloadTwo = false;
          });
      },
      rowclick({ row }) {
        this.dataHeadNow = row;
        this.getBody();
      },
     
      dictItemVisible() {
        this.dialogFormVisibleedit = false;
      },
      handlePageChange({ currentPage, pageSize }) {
        this.tablePage.currentPage = currentPage;
        this.tablePage.pageSize = pageSize;
        this.getDate();
      }
     
    }
  };
  </script>
  <style lang="scss" scoped>
  .shipstatuszs {
    .el-tag {
      width: 100%;
    }
    .el-divider--horizontal {
      margin: 10px 0;
    }
    .vxe-pager.size--mini {
    
    height: 50px;
    }
  }
  </style>>