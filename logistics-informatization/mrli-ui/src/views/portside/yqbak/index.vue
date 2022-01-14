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
  <div class="portside-yqbak-index">
    <basic-container>
      
      <vxe-toolbar perfect size="mini">
        <template v-slot:buttons>
            <vxe-button type="text" class="fa fa-plus" @click="ifokAdd()"  v-if="!ifSelectAll"
            >新增</vxe-button
          >
        </template>
      </vxe-toolbar>
      <vxe-table
        border
        show-overflow
        keep-source
        stripe
        highlight-current-row
        highlight-hover-row
        size="mini"
        :data="tableData"
        :loading="tableload"
        :round="true"
        align="center"
        height="700px"
        ref="tableCar"
      >
        <vxe-table-column field="companyName" title="企业名称" ></vxe-table-column>
        <vxe-table-column field="companyFr" title="企业法人"></vxe-table-column>
         <vxe-table-column field="companyQx" title="经营期限"></vxe-table-column>
          <vxe-table-column field="companyDm" title="企业代码"></vxe-table-column>
          <vxe-table-column field="companyPlace" title="企业地址" ></vxe-table-column>
          <vxe-table-column field="companyTypeId" title="公司类型" :formatter="formatterCompanyType"></vxe-table-column>
          <vxe-table-column field="companyWork" title="企业经营范围"></vxe-table-column>
           <vxe-table-column field="companyPersonNum" title="企业现有人数"></vxe-table-column>
           <vxe-table-column field="companyBabm" title="在边检系统备案业务编号"></vxe-table-column>
           <vxe-table-column field="companyBayxq" title="在边检系统备案有效期"></vxe-table-column>
           <vxe-table-column field="companyFkllr" title="企业疫情防控联络人"></vxe-table-column>
           <vxe-table-column field="companyPhone" title="联络电话"></vxe-table-column>
           <vxe-table-column title="操作" width="100" show-overflow>
          <template v-slot="{ row }">
            <vxe-button
              v-if="!ifSelectAll"
              type="text"
              icon="fa fa-edit"
              @click="edit(row)"
            ></vxe-button>
          </template>
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
       <vxe-modal
        v-if="dialogFormVisibleedit"
        v-model="dialogFormVisibleedit"
        title="基本信息维护"
        width="700"
        height="800"
        resize
        storage
      >
        <CompanyEdit
          :datanow="dataNow"
          @dictItemVisible="dictItemVisible"
          @selectList="selectTable"
        ></CompanyEdit>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleSm"
        v-model="dialogFormVisibleSm"
        title="声明"
        width="400"
        height="600"
        resize
        storage
      >
      <div align="center">
       <el-row>
         <el-col>
         一、各涉港企业认真落实企业疫情防控主体责任，要把企业员工的生命安全和身体健康放在首位。指派专门人员认真填报登船人员信息，并对填报信息的准确性负责。
         </el-col>
       </el-row>
        <el-row >
         <el-col>
          二、各涉港企业登船作业人员及国际航行船舶相关联人员在开展业务过程中必须按照交通部《指南》、省防指《指引》、营口市《关于严格执行(关于对进境营口港外贸船舶船员实施核酸检测的暂行办法)从严管理外贸登船人员的通知》（营疫防经发指境外[2021]4号）、《关于营口港统筹管理涉港企业疫情防控工作的通知》（营疫防经发指境外[2021]5号）、《关于营口港统筹管理涉港企业疫情防控工作的通知》《关于加强对国际航行船舶登船人员疫情管控的工作方案》（营疫防经发指境外[2021]7号）等文件的具体要求做好相关人员防护，禁止进入生活区等密闭空间休息、就餐、如厕，不得指派未接种新冠疫苗的作业人员登船作业，登船人员必须采取集中封闭管理，定期进行核酸检测。在港期间服从港口疫情防控管理，全面落实各项防控措施。
            </el-col>
       </el-row>
       <el-row >
         <el-col>
           三、若违反以上条款和相关文件规定，造成的一切后果由涉港企业承担全部责任。造成严重影响的，同意吊销个人及单位从业资质，并无条件接受各级防指和相关部门的惩处。
            </el-col>
       </el-row>
       <el-row >
         <el-col>
           <vxe-checkbox v-model="ifOk" content="我已阅读，并同意以上条款"></vxe-checkbox>
            </el-col>
       </el-row>
       <el-row >
         <el-col>
          <el-button type="primary" size="mini" icon="el-icon-check" @click="add">确认</el-button>
           </el-col>
       </el-row>
      </div>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import { companyList } from "@/api/portside/yqbak";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import CompanyEdit from "./companyEdit";
import EditDown from "../../baseUtile/EditDown";
export default {
  components: { EditDown, CompanyEdit },
  name: "portside-yqbak-index",
  data() {
    return {
      formObj: {
        unitName: null,
        unitCod: null,
        shipNo: null,
        shipNam: null,
        selectTime: null
      },
      ifOk: false,
      dialogFormVisibleSm: false,
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
        this.ifSelectAll = this.permissions["SGBS_BAK_CXSY"];
    this.getDate();
  },
  methods: {
    getDate() {
      this.tableload = true;
      companyList(
        Object.assign(
          {
            current: this.tablePage.currentPage,
            size: this.tablePage.pageSize,
            ifSelectAll:this.ifSelectAll
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
      if (this.ifOk) {
        this.dataNow = null;
        this.dialogFormVisibleedit = true;
      } else {
        this.$message.error("同意后才可以继续");
        return;
      }
    },
    ifokAdd() {
      this.dialogFormVisibleSm = true;
    },
    edit(row) {
      this.dataNow = row;
      this.dialogFormVisibleedit = true;
    },
    delete() {},
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
      this.dialogFormVisibleSm = false;
      this.dataNow = null;
    },
    formatterCompanyType({ cellValue }) {
      console.log(cellValue)
      if (cellValue == '1') {
        return "合资";
      } else if(cellValue == '2') {
        return "独资";
      }else if(cellValue == '3') {
        return "国有";
      }else if(cellValue == '4') {
        return "私营";
      }else if(cellValue == '5') {
        return "股份制";
      }else if(cellValue == '6') {
        return "有限责任";
      }else if(cellValue == '99') {
        return "其他";
      }
    }
  }
};
</script>
<style lang="scss" scoped>
.portside-yqbak-index {
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