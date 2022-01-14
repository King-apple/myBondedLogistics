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
  <div class="businessrenttankagreement">
    <basic-container>
      <vxe-form :data="formObj" size="mini">
        <vxe-form-item title="公司：" field="unitNam" size="mini" span="6">
          <vxe-input
            v-model="formObj.unitNam"
            clearable
            size="mini"
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="罐容量：" field="capacity" size="mini" span="6">
          <vxe-input
            v-model="formObj.capacity"
            clearable
            size="mini"
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="center">
          <vxe-button status="primary" icon="fa fa-search" @click="getDate()"
            >查询</vxe-button
          >
        </vxe-form-item>
      </vxe-form>
      <vxe-toolbar perfect size="mini">
        <template v-slot:buttons>
          <vxe-button type="text" class="fa fa-plus" @click="add('add')"
            >新增</vxe-button
          >
          <vxe-button
            type="text"
            class="fa fa-pencil-square-o"
            @click="add('edit')"
            >修改</vxe-button
          >
          <vxe-button type="text" class="fa fa-eye" @click="viewAgreement()">详情查看</vxe-button>
          <vxe-button type="text" class="fa fa-trash" @click="deleteAgreement()"
            >作废</vxe-button
          >
          <!-- <vxe-button type="text" class="fa fa-upload" @click="update()">提交</vxe-button> -->
          <vxe-button type="text" class="fa fa-lock" @click="ifLock('lock')"
            >封存</vxe-button
          >
          <vxe-button type="text" class="fa fa-unlock" @click="ifLock('unlock')"
            >解封存</vxe-button
          >
        </template>
      </vxe-toolbar>
      <vxe-table
        border
        show-overflow
        keep-source
        highlight-current-row
        size="mini"
        :data="tableData"
        :loading="tableload"
        :round="true"
        align="center"
        height="650px"
        ref="tableMan"
        @cell-click="rowclick"
        @cell-dblclick="cellDBLClickEvent"
      >
        <vxe-table-column type="seq" width="60" title="序号"></vxe-table-column>
        <vxe-table-column
          field="status"
          title="状态"
          :formatter="formatterStatus"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="tankNo"
          title="罐号"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="tankCapacity"
          title="罐容"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="feeUnit"
          title="单位"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="cargoNam"
          title="货名"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="pkgKindNam"
          title="包装"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="feeRate"
          title="费率"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="beginDate"
          title="开始日期"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="endDate"
          title="结束日期"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="shiperNam"
          title="作业委托人"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="ifPay"
          title="是否交租罐费"
          width="100"
          :formatter="formatterIfpay"
        ></vxe-table-column>
        <vxe-table-column
          field="shiperPayNam"
          title="作业委托人（交）"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="jbr"
          title="经办人"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="tele"
          title="联系人电话"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="billTitle"
          title="发票名头"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="remark"
          title="备注"
          width="80"
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
          'Total',
        ]"
        @page-change="handlePageChange"
      ></vxe-pager>
      <vxe-modal
        v-if="dialogFormVisibleedit"
        v-model="dialogFormVisibleedit"
        :title="editTitle"
        width="900"
        height="600"
        resize
        storage
        @close="closeEdit()"
      >
        <Edit
          :datanow="datanow"
          @dictItemVisible="dictItemVisible"
          @selectListplan="getDate"
        ></Edit>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleAdd"
        v-model="dialogFormVisibleAdd"
        title="租赁协议新增"
        width="900"
        height="600"
        resize
        storage
        @close="closeEdit()"
      >
        <Add
          :datanow="datanow"
          @dictItemVisible="dictItemVisible"
          @selectListplan="getDate"
        ></Add>
      </vxe-modal>
       <vxe-modal
        v-if="dialogFormVisibleView"
        v-model="dialogFormVisibleView"
        title="租赁协议详情"
        width="900"
        height="600"
        resize
        storage
      >
        <ViewAgreement
          :datanow="datanow"
          @dictItemVisible="dictItemVisible"
          @selectListplan="getDate"
        ></ViewAgreement>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import {
  fetchList,
  addObj,
  delObj,
  deleteObj,
  lockObj,
} from "@/api/business/businessrenttankagreement";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import Edit from "./edit";
import Add from "./add";
import ViewAgreement from "./viewAgreement";
export default {
  components: { Edit, Add ,ViewAgreement},
  name: "businessrenttankprice",
  data() {
    return {
      formObj: { firstNam: null, shipNam: null, firstCod: null },
      editTitle: "租赁协议信息",
      dialogFormVisibleedit: false,
      dialogFormVisibleAdd: false,
      dialogFormVisibleView:false,
      tableData: [],
      datanow: {},
      dataHeadNow: {},
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      tableload: false,
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  created() {
    this.getDate();
  },
  methods: {
    getDate() {
      this.tableload = true;
      this.dataHeadNow = {};
      fetchList(
        Object.assign(
          {
            current: this.tablePage.currentPage,
            size: this.tablePage.pageSize,
          },
          this.formObj
        )
      )
        .then((response) => {
          this.tableData = response.data.data.records;
          this.tablePage.total = response.data.data.total;
          this.tableload = false;
        })
        .catch(() => {
          this.tableload = false;
        });
    },
    cellDBLClickEvent({ row }) {
      this.datanow = row;
      this.dialogFormVisibleView = true;
    },
    add(type) {
      if (type === "edit") {
        if (this.dataHeadNow != null && this.dataHeadNow.id != null) {
          if (this.dataHeadNow.status == "09") {
            this.$message.error("当前协议已封存，请解封存后进行操作");
            return;
          }
          this.editTitle = "租赁协议修改";
          this.datanow = this.dataHeadNow;
          this.dialogFormVisibleedit = true;
        } else {
          this.$message.error("请选择一个数据进行修改");
        }
      } else if (type === "add") {
        this.datanow = {};
        this.dialogFormVisibleAdd = true;
      }
    },
    viewAgreement() {
      if (this.dataHeadNow != null && this.dataHeadNow.id != null) {
        this.datanow = this.dataHeadNow;
        this.dialogFormVisibleView = true;
      } else {
        this.$message.error("请选择一个进行查看");
      }
    },
    deleteAgreement() {
      let that = this;
      if (this.dataHeadNow != null && this.dataHeadNow.id != null) {
        if (this.dataHeadNow.status == "09") {
          this.$message.error("当前协议已封存，请解封存后进行操作");
          return;
        }
        this.$confirm("是否确认删除", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(function() {
            return deleteObj(that.dataHeadNow);
          })
          .then((response) => {
            this.$message.success("作废成功");
            this.getDate();
          });
        // deleteObj(this.dataHeadNow)
        //   .then((response) => {
        //     this.$message.success("作废成功");
        //     this.getDate();
        //   })
        //   .catch(() => {});
      } else {
        this.$message.error("请选择一个进行作废");
      }
    },
    ifLock(type) {
      if (this.dataHeadNow != null && this.dataHeadNow.id != null) {
        if (type === "lock") {
          if (this.dataHeadNow.status == "09") {
            this.$message.error("当前状态已封存不能再次封存");
            return;
          }
          lockObj(this.dataHeadNow, type)
            .then((response) => {
              this.$message.success("封存成功");
              this.getDate();
            })
            .catch(() => {});
        } else if (type === "unlock") {
          if (this.dataHeadNow.status == "01") {
            this.$message.error("当前状态未封存不能解封存");
            return;
          }
          lockObj(this.dataHeadNow, type)
            .then((response) => {
              this.$message.success("解封存成功");
              this.getDate();
            })
            .catch(() => {});
        }
      } else {
        this.$message.error("请选择一个进行封存/解封存操作");
      }
    },
    rowclick({ row }) {
      this.dataHeadNow = row;
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
      this.dialogFormVisibleAdd = false;
      this.dialogFormVisibleView = false;
    },
    closeEdit() {
      this.getDate();
      this.dialogFormVisibleedit = false;
      this.dialogFormVisibleAdd = false;
    },
    formatterStatus({ row }) {
      if (row.status == "01") {
        return "正常";
      } else if (row.status == "09") {
        return "已封存";
      }
    },
    formatterIfpay({ row }) {
      if (row.ifPay == "01") {
        return "是";
      } else if (row.ifPay == "00") {
        return "否";
      }
    },
  },
};
</script>
<style lang="scss" scoped>
.zyworkresourcesman {
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