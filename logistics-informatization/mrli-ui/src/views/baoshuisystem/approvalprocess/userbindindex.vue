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
  <div class="user">
    <basic-container>
      <el-row :span="24">
        <avue-crud
          ref="crud"
          :option="option"
          v-model="form"
          :page="page"
          :table-loading="listLoading"
          :data="list"
          @on-load="getList"
          @search-change="searchChange"
          @refresh-change="refreshChange"
          @size-change="sizeChange"
          @current-change="currentChange"
        >
          <template slot="username" slot-scope="scope">
            <span>{{ scope.row.username }}</span>
          </template>
          <template slot="role" slot-scope="scope">
            <span v-for="(role, index) in scope.row.roleList" :key="index">
              <el-tag>{{ role.roleName }} </el-tag>&nbsp;&nbsp;
            </span>
          </template>
          <template slot="deptId" slot-scope="scope">
            {{ scope.row.deptName }}
          </template>
          <template slot="lockFlag" slot-scope="scope">
            <el-tag>{{ scope.label }}</el-tag>
          </template>
          <template slot="menu" slot-scope="scope">
            <el-button
              type="text"
              size="mini"
              icon="el-icon-delete"
              @click="deletes(scope.row, scope.index)"
              >解绑
            </el-button>
          </template>
        </avue-crud>
      </el-row>
    </basic-container>
  </div>
</template>

<script>
import { addObj, delObj, fetchList, putObj } from '@/api/baoshuisystem/userbind'
import { deptRoleList } from '@/api/admin/role'
import { tableOption } from '@/const/crud/baoshuisystem/userbind'
import { addApprovalProcessuser, delApprovalProcessuser, getInfoList } from '@/api/baoshuisystem/approvalprocessuser'
import { mapGetters } from 'vuex'

export default {
  name: 'SysUser',
  data() {
    return {
      searchForm: {},
      treeOption: {
        nodeKey: 'id',
        addBtn: false,
        menu: false,
        props: {
          label: 'name',
          value: 'id'
        }
      },
      treeData: [],
      option: tableOption,
      treeDeptData: [],
      checkedKeys: [],
      treeSubunitData: [],
      roleProps: {
        label: 'roleName',
        value: 'roleId'
      },
      defaultProps: {
        label: 'name',
        value: 'id'
      },
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条,
        isAsc: false// 是否倒序
      },
      list: [],
      listLoading: true,
      role: [],
      form: { deptId: null },
      rolesOptions: []
    }
  },
  props: {
    organizerData: Array
  },
  methods: {
    async getList(page, params) {
      let that = this
      this.searchForm.processFid = that.organizerData[0].fId
      this.listLoading = true
      let data = await getInfoList(Object.assign({
        current: page.currentPage,
        size: page.pageSize,
        //选中的承办单位主键
        // oFid: that.organizerData[0].fId,
      }, params, this.searchForm))
      this.list = data.data.data.records
      this.page.total = data.data.data.total
      this.listLoading = false
    },
    searchChange(param, done) {
      this.searchForm = param
      this.getList(this.page, param)
      done()
    },
    sizeChange(pageSize) {
      this.page.pageSize = pageSize
    },
    currentChange(current) {
      this.page.currentPage = current
    },
    refreshChange() {
      this.getList(this.page)
    },
    deletes(row, index) {
      let params = {
        processFid: this.organizerData[0].fId,
        userId: row.userId
      }
      delApprovalProcessuser(params).then(() => {
        this.$notify.success('解绑成功')
        this.getList(this.page)
      }).catch(() => { this.$notify.error('解绑失败') })
      this.getList(this.page)
    },
  }
}
</script>
