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
  <div class="admin-newuser-index">
    <basic-container>
      <el-row :gutter="15">
        <el-col :span="6">
          <vxe-form :data="form">
            <vxe-form-item title="名称：" field="depName" size="mini" span="12">
              <vxe-input
                v-model="form.depName"
                type="input"
                clearable
                @change="handleSearch"
              ></vxe-input>
            </vxe-form-item>
          </vxe-form>
          <vxe-table
            resizable
            border="inner"
            highlight-current-row
            ref="xTree"
            size="small"
            :show-header="false"
            :data="treeData"
            height="750px"
            :tree-config="{ children: 'children' }"
            :keyboard-config="{ isArrow: true, isEnter: true }"
            @cell-click="selectDep"
          >
            <vxe-column field="name" title="name" tree-node> </vxe-column>
          </vxe-table>
        </el-col>
        <el-col :span="18">
          <vxe-form :data="formObj">
            <vxe-form-item title="姓名" field="username" size="mini" span="4">
              <vxe-input
                v-model="formObj.username"
                type="input"
                clearable
              ></vxe-input>
            </vxe-form-item>
            <vxe-form-item span="4" size="mini" align="center">
              <vxe-button
                status="primary"
                icon="fa fa-search"
                @click="selectTable()"
                >查询</vxe-button
              >
              <vxe-button type="reset" icon="fa fa-repeat">重置</vxe-button>
            </vxe-form-item>
          </vxe-form>
          <vxe-toolbar perfect size="mini">
            <template v-slot:buttons>
              <vxe-button type="text" icon="fa fa-plus" @click="openAdd()"
                >新增</vxe-button
              >
            </template>
          </vxe-toolbar>
          <vxe-table
            border
            show-overflow
            keep-source
            highlight-hover-row
            highlight-current-row
            size="mini"
            :data="tableData"
            :loading="tableload"
            :round="true"
            align="center"
            height="700px"
            ref="tableCnt"
          >
            <vxe-table-column
              field="username"
              title="用户名"
            ></vxe-table-column>
            <vxe-table-column
              field="manName"
              title="用户姓名"
            ></vxe-table-column>
            <vxe-table-column field="phone" title="手机号"></vxe-table-column>
            <vxe-table-column
              field="createTime"
              title="创建时间"
            ></vxe-table-column>
            <vxe-table-column
              field="lockFlag"
              title="状态"
              :formatter="formatterStatus"
            ></vxe-table-column>
            <vxe-table-column title="操作" width="300" fixed="right">
              <template v-slot="{ row }">
                <vxe-button type="text" icon="fa fa-edit" @click="openEdit(row)"
                  >编辑</vxe-button
                >
                 <vxe-button type="text" icon="fa fa-edit" @click="openAddJs(row)"
                  >增加角色</vxe-button
                >
                <vxe-button
                  type="text"
                  icon="el-icon-delete"
                  @click="deletes(row)"
                  >删除</vxe-button
                >
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
        </el-col> </el-row
      ><vxe-modal
        v-if="dialogFormVisibleedit"
        v-model="dialogFormVisibleedit"
        title="用户信息维护"
        width="800"
        height="600"
        resize
        storage
      >
        <Add
          :datanow="dataNow"
          @dictItemVisible="dictItemVisible"
          @selectList="selectTable"
        ></Add>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleAddJs"
        v-model="dialogFormVisibleAddJs"
        title="维护角色"
        width="600"
        height="750"
        resize
        storage
      >
        <AddJs
          :datanow="dataNow"
          @dictItemVisible="dictItemVisible"
          @selectList="selectTable"
        ></AddJs>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import { addObj, delObj, fetchList, putObj } from "@/api/admin/user";
import { mapGetters } from "vuex";
import { deptRoleList } from "@/api/admin/role";
import { fetchTree } from "@/api/admin/dept";
import { tableOption } from "@/const/crud/admin/user";
import EditDown from "@/views/baseUtile/EditDown";
import XEUtils from "xe-utils";
import Add from "./add";
import AddJs from "./addJs";
export default {
  components: { EditDown, Add,AddJs },
  name: "admin-newuser-index",
  data() {
    return {
      treeData: [],
      tableData: [],
      originData: [],
      tableload: false,
      dataNow: null,
      dialogFormVisibleedit: false,
      dialogFormVisibleAddJs:false,
      form: { deptId: null, deptIdS: [] },
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      formObj: {}
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  watch: {
    role() {
      this.form.role = this.role;
    }
  },
  created() {
    this.init();
    this.selectTable();
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

    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    selectTable() {
      this.tablePage.currentPage = 1;
      this.getDate();
    },
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
      this.dialogFormVisibleAddJs=false;
    },
    init() {
      fetchTree().then(response => {
        this.treeData = response.data.data;
        this.originData = this.treeData;
      });
    },
    selectDep({ row }) {
      let deptList = XEUtils.toTreeArray([row]);
      this.formObj.deptIdS = "";
      //获取所有叶子
      for (let i = 0; i < deptList.length; i++) {
        this.formObj.deptIdS = this.formObj.deptIdS + deptList[i].id + ",";
      }
      this.formObj.deptIdS = this.formObj.deptIdS.substr(
        0,
        this.formObj.deptIdS.length - 1
      );
      this.selectTable();
    },
    handleSearch() {
      let filterName = XEUtils.toValueString(this.form.depName).trim();
      if (filterName) {
        let options = { children: "children" };
        let searchProps = ["name"];
        this.treeData = XEUtils.searchTree(
          this.originData,
          item =>
            searchProps.some(
              key => XEUtils.toValueString(item[key]).indexOf(filterName) > -1
            ),
          options
        );
        // 搜索之后默认展开所有子节点
        this.$nextTick(() => {
          this.$refs.xTree.setAllTreeExpand(true);
        });
      } else {
        this.treeData = this.originData;
      }
    },
    openEdit(row) {
      this.dataNow = row;
      this.dataNow.password = undefined
      this.dialogFormVisibleedit = true;
    },
    openAdd() { 
        this.dataNow = null;
      this.dialogFormVisibleedit = true;
    },
    openAddJs(row){
       this.dataNow = row;
      this.dataNow.password = undefined
     this.dialogFormVisibleAddJs=true
    },
    formatterStatus({ cellValue }) {
      if (cellValue == 0) {
        return "有效";
      } else if (cellValue == 9) {
        return "锁定";
      }
    },
    deletes(row) {
      this.$XModal
        .confirm(
          "此操作将永久删除该用户(用户名:" + row.username + "), 是否继续?"
        )
        .then(() => {
          if (type == "confirm") {
            delObj(row.userId)
              .then(() => {
                this.list.splice(index, 1);
                this.$notify.success("删除成功");
              })
              .catch(() => {
                this.$notify.error("删除失败");
              });
          }
        });
    }
  }
};
</script>
<style lang="scss" scoped>
.admin-newuser-index {
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