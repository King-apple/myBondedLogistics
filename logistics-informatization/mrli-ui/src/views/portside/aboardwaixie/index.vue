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
  <div class="portside-aboardwaixie-index">
    <basic-container>
      <vxe-form :data="formObj">
        <vxe-form-item title="姓名" field="personName" size="mini" span="3">
          <vxe-input v-model="formObj.personName" clearable></vxe-input>
        </vxe-form-item>

        <vxe-form-item
          title="公司"
          field="unitName"
          size="mini"
          span="3"
          v-if="userInfo.unitCod == 'G1'"
        >
          <EditDown
            :objs="formObj"
            property="unitName"
            :tableColumns="downTableOption.unitColum"
            :urlapi="downTableOption.uniturl"
            :returnApi="downTableOption.unitreturnApi"
            sizenow="mini"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="center">
          <vxe-button
            status="primary"
            icon="fa fa-search"
            @click="selectTable()"
            >查询</vxe-button
          >
          <vxe-button type="reset" icon="fa fa-repeat" @click="resetForm"
            >重置</vxe-button
          >
        </vxe-form-item>
      </vxe-form>
      <vxe-toolbar perfect size="mini">
        <template v-slot:buttons>
          <vxe-button type="text" class="fa fa-plus" @click="addPerson()"
            >新增</vxe-button
          >
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
      >
        <vxe-table-column
          field="manName"
          title="人名"
          width="150"
        ></vxe-table-column>
        <vxe-table-column
          field="manPhone"
          title="联系方式"
          width="95"
        ></vxe-table-column>
        <vxe-table-column
          field="manId"
          title="身份证"
          width="95"
        ></vxe-table-column>
        <vxe-table-column
          field="sex"
          title="性别"
          :formatter="formatSex"
        ></vxe-table-column>
        <vxe-table-column field="manAboardId" title="登船证"></vxe-table-column>
        <vxe-table-column
          field="adminTyp"
          title="岗位"
          width="200"
        ></vxe-table-column>
        <vxe-table-column field="subunitNam" title="部门"></vxe-table-column>
        <vxe-table-column
          field="status"
          title="状态"
          :formatter="formatPersonStatus"
        ></vxe-table-column>
        <vxe-table-column
          field="createTime"
          title="创建时间"
          width="150"
        ></vxe-table-column>
        <vxe-table-column title="操作" width="100" show-overflow>
          <template v-slot="{ row }">
            <vxe-button
              type="text"
              icon="fa fa-edit"
              @click="editEvent(row)"
            ></vxe-button>
            <vxe-button
              type="text"
              icon="fa fa-trash-o"
              @click="removeEvent(row)"
            ></vxe-button>
          </template>
        </vxe-table-column>
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
        width="500"
        height="600"
        resize
        storage
      >
        <AddOrEdit
          :datanow="dataNow"
          @dictItemVisible="dictItemVisible"
          @selectList="selectTable"
        ></AddOrEdit>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import { fetchList, deleteObj } from "@/api/portside/aboardwaixie";
import { mapGetters } from "vuex";
import AddOrEdit from "./addOrEdit";
import EditDown from "../../baseUtile/EditDown";
export default {
  components: { EditDown, AddOrEdit },
  name: "portside-aboardpersion-index",
  data() {
    return {
      formObj: {
        unitName: null,
        unitCod: null,
        shipNo: null,
        shipNam: null,
        selectTime: null
      },
      dialogFormVisibleedit: false,
      tableData: [],
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      dataNow: {},
      tableload: false,
      downTableOption: {
        //作业公司下拉菜单展示项
        unitColum: [
          { field: "SHOT_NAM", title: "公司代码" },
          { field: "UNIT_COD", title: "公司名称" }
        ],
        //作业公司下拉菜单的返回项
        unitreturnApi: [
          { field: "UNIT_COD", returnName: "unitCod" },
          { field: "SHOT_NAM", returnName: "unitName" }
        ],
        //作业公司下拉菜单的路径
        uniturl: "/caccentre/basecod/unit/page",
        //货名下拉菜单展示项
        cargoColum: [
          { field: "CARGO_NAM_COD", title: "货物代码" },
          { field: "CARGO_NAM", title: "货物名称" },
          { field: "CARGO_KIND_COD_PRICE", title: "价格货类代码" },
          { field: "CARGO_KIND_NAM_PRICE", title: "价格货类名称" },
          { field: "CARGO_KIND_COD_DL", title: "大货类代码" },
          { field: "CARGO_KIND_NAM_DL", title: "大货类名称" }
        ],
        //货名下拉菜单的返回项
        cargoreturnApi: [
          { field: "CARGO_NAM_COD", returnName: "cargoNameCod" },
          { field: "CARGO_NAM", returnName: "cargoName" }
        ],
        //货名下拉菜单的路径
        cargourl: "/yard/mioyardhead/getCargoNam",
        //预报船信息
        shipNam: "shipNam",
        shipNamColum: [
          { field: "SHIP_NO", title: "船号" },
          { field: "SHIP_NAM", title: "船名" },
          { field: "TO_PORT_TIM", title: "到港时间" },
          { field: "TO_PORT_NAM", title: "到达港" }
        ],
        shipNamreturnApi: [
          { field: "SHIP_NO", returnName: "shipNo" },
          { field: "SHIP_NAM", returnName: "shipNam" }
        ],
        shipNamurl: "/caccentre/ship/getWmShipOfNotLev"
      }
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
      if (this.userInfo.unitCod != "G1") {
        this.formObj.unitCod = this.userInfo.unitCod;
      }
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
      this.formObj.shipNo = null;
      console.log(this.formObj.shipNo);
    },
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
      this.dataNow = null;
    },
    addPerson() {
      this.dataNow = null;
      this.dialogFormVisibleedit = true;
    },
    rowclick({ row }) {
      this.dataNow = row;
    },
    editEvent(row) {
      this.dataNow = row;
      this.dialogFormVisibleedit = true;
    },
    removeEvent(row) {
      this.$XModal.confirm("您确定要作废吗？").then(type => {
        if (type == "confirm") {
          deleteObj(row.pkid)
            .then(data => {
              this.$message.success("作废成功");
             this.selectTable();
            })
            .catch(() => {});
        }
      });
    },
    formatSex({ cellValue }) {
      if (cellValue == "1") {
        return "男";
      } else if (cellValue == "0") {
        return "女";
      }
    },
    formatPersonStatus({ cellValue }) {
      if (cellValue == "1") {
        return "有效";
      } else if (cellValue == "0") {
        return "无效";
      }
    }
  }
};
</script>
<style lang="scss" scoped>
.portside-aboardwaixie-index {
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