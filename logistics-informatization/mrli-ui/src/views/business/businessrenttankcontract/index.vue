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
          title="作业委托人："
          field="shiperNam"
          size="mini"
          span="5"
        >
          <!-- <vxe-input
            v-model="formObj.shiperNam"
            clearable
            size="mini"
          ></vxe-input> -->
          <EditDown
            :objs="formObj"
            :property="downTableOption.shiperNam"
            :tableColumns="downTableOption.shiperColum"
            :urlapi="downTableOption.shiperurl"
            :returnApi="downTableOption.shiperreturnApi"
            sizenow="mini"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item title="合同号：" field="contractNo" size="mini" span="5">
          <vxe-input
            v-model="formObj.contractNo"
            clearable
            size="mini"
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item
          title="起始时间："
          field="beginTime"
          size="mini"
          span="5"
        >
          <vxe-input
            v-model="formObj.beginTime"
            clearable
            size="mini"
            type="date"
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="结束时间：" field="endTime" size="mini" span="5">
          <vxe-input
            v-model="formObj.endTime"
            clearable
            size="mini"
            type="date"
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
          <vxe-button type="text" class="fa fa-eye" @click="viewContract()"
            >详情查看</vxe-button
          >
          <vxe-button type="text" class="fa fa-file-excel-o" @click="excelOut()"
            >EXCEL导出</vxe-button
          >
          <vxe-button type="text" class="fa fa-trash" @click="deleteContract()"
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
      <el-divider content-position="left">
        <strong>合同信息</strong>
      </el-divider>
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
        height="290px"
        ref="tableMan"
        @cell-click="rowclick"
        @cell-dblclick="cellDBLClickEvent"
      >
        <vxe-table-column type="seq" width="60" title="序号"></vxe-table-column>
        <vxe-table-column
          field="contractNo"
          title="合同号"
          width="150"
        ></vxe-table-column>
        <vxe-table-column
          field="status"
          title="状态"
          :formatter="formatterStatus"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="shiperNam"
          title="作业委托人"
          width="200"
        ></vxe-table-column>
        <!-- <vxe-table-column field="portUnitNam" title="港口经营人" width="200"></vxe-table-column>
        <vxe-table-column field="unitNam" title="作业公司" width="200"></vxe-table-column> -->

        <vxe-table-column
          field="jbrA"
          title="甲方经办人"
          width="200"
        ></vxe-table-column>
        <vxe-table-column
          field="jbrB"
          title="乙方经办人"
          width="200"
        ></vxe-table-column>
        <vxe-table-column
          field="createTime"
          title="录入时间"
          width="200"
        ></vxe-table-column>
        <vxe-table-column
          field="remark"
          title="备注"
          width="200"
        ></vxe-table-column>
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
      <el-divider content-position="left">
        <strong>合同明细</strong>
      </el-divider>
      <vxe-table
        border
        show-overflow
        keep-source
        highlight-current-row
        size="mini"
        :data="tableMxData"
        :loading="tableMxload"
        :round="true"
        align="center"
        height="280px"
        ref="tableMX"
      >
        <vxe-table-column type="seq" width="60" title="序号"></vxe-table-column>
        <vxe-table-column
          field="tankNo"
          title="罐号"
          width="150"
        ></vxe-table-column>
        <vxe-table-column
          field="tankCapacity"
          title="罐容量"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="amt"
          title="金额"
          width="200"
        ></vxe-table-column>
        <vxe-table-column
          field="beginDate"
          title="开始日期"
          width="200"
          :formatter="['formatDate', 'yyyy-MM-dd']"
        ></vxe-table-column>
        <vxe-table-column
          field="endDate"
          title="结束日期"
          width="200"
          :formatter="['formatDate', 'yyyy-MM-dd']"
        ></vxe-table-column>
      </vxe-table>
      <!-- <vxe-pager
        perfect
        size="mini"
        :loading="tableMxload"
        :current-page="tableMxPage.currentPage"
        :page-size="tableMxPage.pageSize"
        :total="tableMxPage.total"
        :layouts="[
          'PrevPage',
          'JumpNumber',
          'NextPage',
          'FullJump',
          'Sizes',
          'Total',
        ]"
        @page-change="handleMxPageChange"
      ></vxe-pager> -->
      <vxe-modal
        v-if="dialogFormVisibleedit"
        v-model="dialogFormVisibleedit"
        :title="editTitle"
        width="800"
        height="600"
        resize
        storage
        fullscreen
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
        title="租赁合同新增"
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
      <vxe-modal
        v-if="dialogFormVisibleView"
        v-model="dialogFormVisibleView"
        title="租赁合同详情"
        width="800"
        height="600"
        resize
        storage
        fullscreen
      >
        <ViewContract
          :datanow="datanow"
          @dictItemVisible="dictItemVisible"
          @selectListplan="getDate"
        ></ViewContract>
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
  getContractMx,
  excelOutPost,
} from "@/api/business/businessrenttankcontract";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import XEUtils from "xe-utils";
import EditDown from "../../baseUtile/EditDown";
import Edit from "./edit";
import Add from "./add";
import ViewContract from "./viewContract";
export default {
  components: { Edit, Add, ViewContract, EditDown },
  name: "businessrenttankcontract",
  data() {
    return {
      formObj: {
        shiperCod: null,
        shiperNam: null,
        contractNo: null,
        beginTime: null,
        endTime: null,
      },
      editTitle: "租赁合同信息",
      dialogFormVisibleedit: false,
      dialogFormVisibleAdd: false,
      dialogFormVisibleView: false,
      tableData: [],
      tableMxData: [],
      datanow: {},
      dataHeadNow: {},
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      tableMxPage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      tableload: false,
      tableMxload: false,
      downTableOption: {
        //作业委托人
        shiperNam: "shiperNam",
        shiperColum: [
          { field: "SHIPPER_COD", title: "货主代码" },
          { field: "SHIPPER_DOC", title: "货主名称" },
        ],
        shiperreturnApi: [
          { field: "SHIPPER_COD", returnName: "shiperCod" },
          { field: "SHIPPER_DOC", returnName: "shiperNam" },
        ],
        shiperurl: "/basecode/basecod/getShipperDoc",
      },
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
          if (0 == response.data.data.records.length) {
            this.tableMxData = [];
          } else {
            this.getMxDate(response.data.data.records[0].contractNo);
          }
          this.tablePage.total = response.data.data.total;
          this.tableload = false;
        })
        .catch(() => {
          this.tableload = false;
        });
    },
    getMxDate(contractNo) {
      this.tableMxload = true;
      getContractMx(contractNo)
        .then((response) => {
          this.tableMxData = response.data.data;
          this.tableMxload = false;
        })
        .catch(() => {
          this.tableMxload = false;
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
          this.editTitle = "租赁合同修改";
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
    viewContract() {
      if (this.dataHeadNow != null && this.dataHeadNow.contractNo != null) {
        this.datanow = this.dataHeadNow;
        this.dialogFormVisibleView = true;
      } else {
        this.$message.error("请选择一个进行查看");
      }
    },
    deleteContract() {
      let that = this;
      if (this.dataHeadNow != null && this.dataHeadNow.contractNo != null) {
        if (this.dataHeadNow.status == "09") {
          this.$message.error("当前合同已封存，请解封存后进行操作");
          return;
        }
        this.$confirm("是否确认删除", "提示", {
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
    excelOut() {
      if (
        this.formObj.shiperCod != null &&
        this.formObj.beginTime != null &&
        this.formObj.endTime != null
      ) {
        let loadingInstance = Loading.service({
          fullscreen: true,
          text: "EXCEL生成中……",
        });
        excelOutPost(this.formObj)
          .then(() => {
            loadingInstance.close();
          })
          .catch(() => {
            loadingInstance.close();
          });
      } else {
        this.$message.error("请选择作业委托人和起始时间、结束时间");
      }
    },
    ifLock(type) {
      if (this.dataHeadNow != null && this.dataHeadNow.contractNo != null) {
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
      this.getMxDate(this.dataHeadNow.contractNo);
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    handleMxPageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getMxDate();
    },
    closeEdit() {
      this.getDate();
      this.dialogFormVisibleedit = false;
      this.dialogFormVisibleAdd = false;
    },
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
      this.dialogFormVisibleAdd = false;
      this.dialogFormVisibleView = false;
    },
    formatterStatus({ row }) {
      if (row.status == "01") {
        return "正常";
      } else if (row.status == "09") {
        return "已封存";
      }
    },
    // 格式化日期，默认 yyyy-MM-dd HH:mm:ss
    formatDate({ cellValue }, format) {
      return XEUtils.toDateString(cellValue, format || "yyyy-MM-dd HH:mm:ss");
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