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
        <vxe-form-item
          title="结算单号："
          field="feeBillNo"
          size="mini"
          span="6"
        >
          <vxe-input
            v-model="formObj.feeBillNo"
            clearable
            size="mini"
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="合同号：" field="contractNo" size="mini" span="6">
          <vxe-input
            v-model="formObj.contractNo"
            clearable
            size="mini"
          ></vxe-input>
        </vxe-form-item>
        <!-- <vxe-form-item title="结算单号：" field="shiperNam" size="mini" span="6">
          <vxe-input v-model="formObj.shiperNam" clearable size="mini"></vxe-input>
        </vxe-form-item> -->
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
          <!-- <vxe-button type="text" class="fa fa-pencil-square-o" @click="add('edit')">修改</vxe-button> -->
          <vxe-button type="text" class="fa fa-eye" @click="viewJsd()"
            >详情查看</vxe-button
          >
          <vxe-button type="text" class="fa fa-upload" @click="check()"
            >审核</vxe-button
          >
          <vxe-button type="text" class="fa fa-trash" @click="deleteJsd()"
            >作废</vxe-button
          >
          <vxe-button type="text" class="fa fa-lock" @click="upload()"
            >上传国税</vxe-button
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
          field="feeBillNo"
          title="结算单号"
          width="150"
        ></vxe-table-column>
        <vxe-table-column
          field="contNo"
          title="合同号"
          width="150"
        ></vxe-table-column>
        <vxe-table-column
          field="checkId"
          title="审核状态"
          :formatter="formatterCheckId"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="upId"
          title="上报状态"
          :formatter="formatterUpId"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="contUnit"
          title="合同单位"
          width="200"
        ></vxe-table-column>
        <vxe-table-column
          field="unitNam"
          title="收入单位"
          width="200"
        ></vxe-table-column>
        <vxe-table-column
          field="workDte"
          title="发单日期"
          width="100"
          :formatter="['formatDate', 'yyyy-MM-dd']"
        ></vxe-table-column>
        <vxe-table-column
          field="sumMoney"
          title="计费金额"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="kpKind"
          title="发票类型"
          width="200"
        ></vxe-table-column>
        <vxe-table-column
          field="shipperAcn"
          title="银行账号"
          width="200"
        ></vxe-table-column>
        <vxe-table-column
          field="shipperAdd"
          title="电话地址"
          width="200"
        ></vxe-table-column>
        <!-- <vxe-table-column field="remark" title="备注" width="200"></vxe-table-column> -->
        <!-- <vxe-table-column field="beginDate" title="开始日期" width="100"></vxe-table-column>
        <vxe-table-column field="endDate" title="结束日期" width="100"></vxe-table-column>
        <vxe-table-column field="shiperNam" title="作业委托人" width="100"></vxe-table-column>
        <vxe-table-column field="ifPay" title="是否交租罐费" width="100"></vxe-table-column>
        <vxe-table-column field="shiperPayNam" title="作业委托人（交）" width="100"></vxe-table-column>
        <vxe-table-column field="jbr" title="经办人" width="100"></vxe-table-column>
        <vxe-table-column field="tele" title="联系人电话" width="100"></vxe-table-column>
        <vxe-table-column field="billTitle" title="发票名头"  width="100"></vxe-table-column>
        <vxe-table-column field="remark" title="备注"  width="80"></vxe-table-column>       -->
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
        title="结算单审核"
        width="800"
        height="600"
        resize
        storage
        fullscreen
        @close="closeEdit()"
      >
        <CheckFee
          :datanow="datanow"
          @dictItemVisible="dictItemVisible"
          @selectListplan="getDate"
        ></CheckFee>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleView"
        v-model="dialogFormVisibleView"
        title="结算单详情"
        width="800"
        height="600"
        resize
        storage
        fullscreen
        @close="closeEdit()"
      >
        <ViewFee
          :datanow="datanow"
          @dictItemVisible="dictItemVisible"
          @selectListplan="getDate"
        ></ViewFee>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleAdd"
        v-model="dialogFormVisibleAdd"
        title="租赁计费新增"
        width="800"
        height="600"
        resize
        storage
        fullscreen
        @close="closeEdit()"
      >
        <Add
          :datanow="datanow"
          @dictItemVisible="dictItemVisible"
          @selectListplan="getDate"
        ></Add>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import {
  fetchList,
  addObj,
  deleteObj,
  uploadToGs,
  // lockObj,
} from "@/api/account/cargoshipfeeyphead";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
// import Edit from "./edit";
import CheckFee from "./checkFee";
import Add from "./add";
import ViewFee from "./viewFee";
export default {
  components: { Add, ViewFee, CheckFee },
  name: "cargoshipfeeyphead",
  data() {
    return {
      formObj: {},
      editTitle: "租赁合同信息",
      dialogFormVisibleedit: false,
      dialogFormVisibleAdd: false,
      dialogFormVisibleView: false,
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
        if (this.dataHeadNow != null && this.dataHeadNow.contractNo != null) {
          if (this.dataHeadNow.status == "09") {
            this.$message.error("当前合同已封存，请解封存后进行操作");
            return;
          }
          this.editTitle = "租赁计费修改";
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
    viewJsd() {
      if (this.dataHeadNow != null && this.dataHeadNow.feeBillNo != null) {
        this.datanow = this.dataHeadNow;
        this.dialogFormVisibleView = true;
      } else {
        this.$message.error("请选择一个进行查看");
      }
    },
    check() {
      if (this.dataHeadNow != null && this.dataHeadNow.feeBillNo != null) {
        this.datanow = this.dataHeadNow;
        this.dialogFormVisibleedit = true;
      } else {
        this.$message.error("请选择一个进行审核");
      }
    },
    deleteJsd() {
      let that = this;
      if (this.dataHeadNow != null && this.dataHeadNow.pkid != null) {
        if (this.dataHeadNow.upId == "1") {
          this.$message.error("当前协议已上报国税，不能作废");
          return;
        }
        this.$confirm("是否确认作废", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(function () {
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
    upload(type) {
      let that = this;
      if (this.dataHeadNow != null && this.dataHeadNow.pkid != null) {
        if (this.dataHeadNow.checkId !="1") {
          this.$message.error("请审核通过后再上传国税");
          return;
        }
        if (this.dataHeadNow.upId =="1") {
          this.$message.error("当前结算单已经上传，不能重复上传");
          return;
        }
        this.$confirm("是否确认上传国税", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(function () {
            return uploadToGs(that.dataHeadNow);
          })
          .then((response) => {
            this.$message.success("上传国税成功");
            this.getDate();
          });
      } else {
        this.$message.error("请选择一个进行上传国税操作");
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
    closeEdit() {
      this.getDate();
      this.dialogFormVisibleedit = false;
      this.dialogFormVisibleAdd = false;
      this.dialogFormVisibleView = false;
    },
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
      this.dialogFormVisibleAdd = false;
      this.dialogFormVisibleView = false;
    },
    formatterCheckId({ row }) {
      if (row.checkId == "1") {
        return "审核通过";
      } else if (row.checkId == "0") {
        return "未审核";
      } else if (row.checkId == "2") {
        return "审核未通过";
      }
    },
    formatterUpId({ row }) {
      if (row.upId == "0") {
        return "未上报";
      } else if (row.upId == "1") {
        return "已上报";
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