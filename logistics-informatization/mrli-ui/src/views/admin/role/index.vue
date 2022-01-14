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
  <div class="app-container calendar-list-container">
    <basic-container>
      <avue-crud
        ref="crud"
        :option="tableOption"
        :data="list"
        :page="page"
        v-model="form"
        :table-loading="listLoading"
        :before-open="handleOpenBefore"
        @on-load="getList"
        @search-change="searchChange"
        @refresh-change="refreshChange"
        @size-change="sizeChange"
        @current-change="currentChange"
        @row-update="update"
        @row-save="create"
      >
        <template slot="menuLeft">
          <el-button
            v-if="roleManager_btn_add"
            class="filter-item"
            type="primary"
            icon="el-icon-edit"
            @click="handleCreate"
            >添加</el-button
          >
        </template>
        <template slot="dsScopeForm" slot-scope="scope">
          <div v-if="form.dsType == 1">
            <el-tree
              ref="scopeTree"
              :data="dsScopeData"
              :check-strictly="true"
              :props="defaultProps"
              :default-checked-keys="checkedDsScope"
              class="filter-tree"
              node-key="id"
              highlight-current
              show-checkbox
            />
          </div>
        </template>

        <template slot="menu" slot-scope="scope">
          <el-button
            v-if="roleManager_btn_edit"
            type="text"
            size="mini"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row, scope.index)"
            >编辑</el-button
          >
          <el-button
            v-if="roleManager_btn_del"
            type="text"
            size="mini"
            icon="el-icon-delete"
            @click="handleDelete(scope.row, scope.index)"
            >删除</el-button
          >
          <el-button
            v-if="roleManager_btn_perm"
            type="text"
            size="mini"
            icon="el-icon-plus"
            @click="handlePermission(scope.row, scope.index)"
            >权限</el-button
          >
          <el-button
            v-if="roleManager_btn_perm"
            type="text"
            size="mini"
            icon="el-icon-plus"
            @click="handlePermissionofData(scope.row, scope.index)"
            >数据权限</el-button
          >
        </template>
      </avue-crud>
    </basic-container>
    <vxe-modal
      v-if="dialogPermissionVisible"
      v-model="dialogPermissionVisible"
      title="权限分配"
      width="600"
      height="800"
      resize
      storage
      transfer
    >
      <vxe-table
        show-overflow
        highlight-hover-row
        row-key
        :show-header="false"
        :data="treeData"
        row-id="id"
        ref="menuTree"
        height="710px"
        :checkbox-config="checkbosconfig"
        :tree-config="{
          children: 'children',
          accordion: true,
          line: true,
          iconOpen: 'fa fa-caret-down',
          iconClose: 'fa fa-caret-right'
        }"
      >
        <vxe-table-column type="checkbox" tree-node></vxe-table-column>
      </vxe-table>
      <div align="center">
        <el-button
          type="primary"
          size="mini"
          icon="el-icon-check"
          @click="updatePermession(roleId)"
          >更新</el-button
        >
        <el-button size="mini" icon="el-icon-close" plain @click="cancal()"
          >取消</el-button
        >
      </div>   
    </vxe-modal>
    <vxe-modal
      v-if="dialogPermissionVisibleOfData"
      v-model="dialogPermissionVisibleOfData"
      id="myModal6"
      title="数据权限"
      width="1200"
      height="800"
      min-width="1100"
      min-height="800"
      resize
      storage
      transfer
    >
      <DataRoleList :objrow="objrow" @dictItemVisible="cancal"></DataRoleList>
    </vxe-modal>
  </div>
</template>

<script>
import {
  addObj,
  delObj,
  fetchList,
  fetchRoleTree,
  permissionUpd,
  putObj,
  fetchRoleTreeOfData,
  permissionUpdOfData
} from "@/api/admin/role";
import { tableOption } from "@/const/crud/admin/role";
import { fetchTree } from "@/api/admin/dept";
import { fetchMenuTree, fetchMenuTreeOfData } from "@/api/admin/menu";
import { mapGetters } from "vuex";
import DataRoleList from "./dataRoleList";
export default {
  name: "TableRole",
  components: { DataRoleList },
  data() {
    return {
      searchForm: {},
      tableOption: tableOption,
      dsScopeData: [],
      objrow: {},
      treeData: [],
      treeDataOfData: [],
      checkedKeys: [],
      checkedDsScope: [],
      defaultProps: {
        label: "name",
        value: "id"
      },
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      menuIds: "",
      list: [],
      listLoading: true,
      form: {},
      roleId: undefined,
      checkbosconfig: { labelField: "name", checkRowKeys: null },
      roleCode: undefined,
      rolesOptions: undefined,
      dialogPermissionVisible: false,
      dialogPermissionVisibleOfData: false,
      roleManager_btn_add: false,
      roleManager_btn_edit: false,
      roleManager_btn_del: false,
      roleManager_btn_perm: false
    };
  },
  created() {
    this.roleManager_btn_add = this.permissions["sys_role_add"];
    this.roleManager_btn_edit = this.permissions["sys_role_edit"];
    this.roleManager_btn_del = this.permissions["sys_role_del"];
    this.roleManager_btn_perm = this.permissions["sys_role_perm"];
  },
  computed: {
    ...mapGetters(["elements", "permissions"])
  },
  methods: {
    getList(page, params) {
      this.listLoading = true;
      fetchList(
        Object.assign(
          {
            current: page.currentPage,
            size: page.pageSize
          },
          params,
          this.searchForm
        )
      )
        .then(response => {
          this.list = response.data.data.records;
          this.page.total = response.data.data.total;
          this.listLoading = false;
        })
        .catch(() => {
          this.listLoading = false;
        });
    },
    refreshChange() {
      this.getList(this.page);
    },
    searchChange(form, done) {
      this.searchForm = form;
      this.getList(this.page, form);
      done();
    },
    sizeChange(pageSize) {
      this.page.pageSize = pageSize;
    },
    currentChange(current) {
      this.page.currentPage = current;
    },
    handleCreate() {
      this.$refs.crud.rowAdd();
    },
    handleOpenBefore(show) {
      fetchTree().then(response => {
        this.dsScopeData = response.data.data;
        if (this.form.dsScope) {
          this.checkedDsScope = this.form.dsScope.split(",");
        } else {
          this.checkedDsScope = [];
        }
      });
      show();
    },
    handleUpdate(row, index) {
      this.$refs.crud.rowEdit(row, index);
    },
    cancal() {
      this.dialogPermissionVisible = false;
      this.dialogPermissionVisibleOfData = false;
    },
    handlePermission(row) {
      fetchRoleTree(row.roleId)
        .then(response => {
          this.checkedKeys = response.data.data;
          return fetchMenuTree();
        })
        .then(response => {
          this.treeData = response.data.data;
          // 解析出所有的太监节点
          this.checkedKeys = this.resolveAllEunuchNodeId(
            this.treeData,
            this.checkedKeys,
            []
          );
          this.checkbosconfig.checkRowKeys = this.checkedKeys;
          this.dialogPermissionVisible = true;
          this.roleId = row.roleId;
          this.roleCode = row.roleCode;
        });
    },
    handlePermissionofData(row) {
      this.objrow = row;
      this.dialogPermissionVisibleOfData = true;
    },
    /**
     * 解析出所有的太监节点id
     * @param json 待解析的json串
     * @param idArr 原始节点数组
     * @param temp 临时存放节点id的数组
     * @return 太监节点id数组
     */
    resolveAllEunuchNodeId(json, idArr, temp) {
      for (let i = 0; i < json.length; i++) {
        const item = json[i];
        // 存在子节点，递归遍历;不存在子节点，将json的id添加到临时数组中
        if (item.children && item.children.length !== 0) {
          this.resolveAllEunuchNodeId(item.children, idArr, temp);
        } else {
          temp.push(idArr.filter(id => id === item.id));
        }
      }
      return temp;
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    getNodeData(data, done) {
      done();
    },
    handleDelete(row, index) {
      var _this = this;
      this.$confirm(
        '是否确认删除名称为"' + row.roleName + '"' + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(function() {
          return delObj(row.roleId);
        })
        .then(() => {
          this.getList(this.page);
          this.$notify.success("删除成功");
        });
    },
    create(row, done, loading) {
      if (this.form.dsType === 1) {
        this.form.dsScope = this.$refs.scopeTree.getCheckedKeys().join(",");
      }
      addObj(this.form)
        .then(() => {
          this.getList(this.page);
          done();
          this.$notify.success("创建成功");
        })
        .catch(() => {
          loading();
        });
    },
    update(row, index, done, loading) {
      if (this.form.dsType === 1) {
        this.form.dsScope = this.$refs.scopeTree.getCheckedKeys().join(",");
      }
      putObj(this.form)
        .then(() => {
          this.getList(this.page);
          done();
          this.$notify.success("修改成功");
        })
        .catch(() => {
          loading();
        });
    },
    updatePermession(roleId) {
      this.menuIds = "";
      let records = this.$refs.menuTree.getCheckboxRecords();
      for (let i in records) {
         this.menuIds =  this.menuIds + records[i].id + ",";
      }
       this.menuIds.substring(0,  this.menuIds.length - 1);
      let records2 = this.$refs.menuTree.getCheckboxIndeterminateRecords();
      for (let i in records2) {
         this.menuIds =  this.menuIds + records2[i].id + ",";
      }
      permissionUpd(roleId, this.menuIds).then(() => {
        this.dialogPermissionVisible = false;
        this.$store.dispatch("GetMenu", { type: false });
        this.$notify.success("修改成功");
      });
    },
    updatePermessionOfData(roleId) {
      this.menuIds = "";
      this.menuIds = this.$refs.menuTreeOfData
        .getCheckedKeys()
        .join(",")
        .concat(",")
        .concat(this.$refs.menuTreeOfData.getHalfCheckedKeys().join(","));
      permissionUpdOfData(roleId, this.menuIds).then(() => {
        this.dialogPermissionVisible = false;
        this.$store.dispatch("GetMenu", { type: false });
        this.$notify.success("修改成功");
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.el-dialog__wrapper {
  .el-dialog {
    width: 61% !important;
    .dialog-main-tree {
      max-height: 400px;
      overflow-y: auto;
    }
  }
  .el-form-item__label {
    width: 20% !important;
    padding-right: 20px;
  }
  .el-form-item__content {
    margin-left: 20% !important;
  }
}
</style>
