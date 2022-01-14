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
  <div class="business-transportcontract-fee">
    <basic-container>
      <vxe-toolbar custom perfect size="mini">
        <template v-slot:buttons>
          <vxe-button type="text" class="fa fa-plus" @click="addOpen()">
            新增
          </vxe-button>
          <vxe-button
            type="text"
            class="fa fa-pencil-square-o"
            @click="editOpen()"
          >
            修改
          </vxe-button>
          <vxe-button type="text" class="fa fa-trash" @click="deletFee()">
            删除
          </vxe-button>
        </template>
      </vxe-toolbar>
      <vxe-table
        border
        show-overflow
        keep-source
        highlight-current-row
        highlight-hover-row
        size="mini"
        :data="tableFeeData"
        :loading="tableFeeload"
        align="center"
        height="500px"
        ref="tabletransportfee"
        @cell-click="rowClick"
      >
        <vxe-table-column field="dcBillNo" title="堆存费补交单号" width="150" />
        <vxe-table-column field="advanceDc" title="补交金额" />
        <vxe-table-column field="exFeeCod" title="费目代码" />
        <vxe-table-column field="exFeeNam" title="费目名称" />
        <vxe-table-column field="upDc" title="状态" :formatter="formatStatus" />
        <vxe-table-column field="unitNam" title="公司名称" />
      </vxe-table>
      <vxe-modal
        v-if="dialogFormVisible"
        v-model="dialogFormVisible"
        title="维护"
        width="500"
        height="400"
        resize
        storage
      >
        <vxe-form
          :data="dataForm"
          size="mini"
          ref="editHeadForm"
          :rules="formRules"
          title-width="100"
        >
          <vxe-form-item
            title="堆存单号"
            field="dcBillNo"
            size="mini"
            span="24"
          >
            <vxe-input v-model="dataForm.dcBillNo"></vxe-input>
          </vxe-form-item>
          <vxe-form-item title="金额" field="advanceDc" size="mini" span="24">
            <vxe-input v-model="dataForm.advanceDc"></vxe-input>
          </vxe-form-item>
          <vxe-form-item title="费目" field="exFeeNam" size="mini" span="24">
            <EditDown
              :objs="dataForm"
              property="exFeeNam"
              :tableColumns="downTableOption.specNoColum"
              :urlapi="downTableOption.specNourl"
              :returnApi="downTableOption.specNoreturnApi"
              sizenow="mini"
            ></EditDown>
          </vxe-form-item>
        </vxe-form>
        <div align="center">
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-check"
            @click="handleSubmit"
            >保存</el-button
          >
          <el-button
            size="mini"
            icon="el-icon-close"
            plain
            @click="closeWindows"
            >取消</el-button
          >
        </div>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import { fetchList, addObj, delObj } from "@/api/business/transportDc";
import { mapGetters } from "vuex";
import EditDown from "@/views/baseUtile/EditDown";
import { xj, jf } from "@/util/util";
import moment from "moment";
import { Loading, Message } from "element-ui";
export default {
  props: ["datanow"],
  components: { EditDown },
  data() {
    return {
      dataForm: { exFeeNam: null, exFeeCod: null },
      tableFeeData: [],
      tableFeeload: false,
      dataHeadNow: null,
      dialogFormVisible: false,
      formRules: {
        dcBillNo: [{ required: true, message: "请输入堆存单号" }],
        advanceDc: [{ required: true, message: "请输入金额" }]
      },
      downTableOption: {
        //操作附加费
        specNoColum: [
          { field: "FEE_NAME_COD", title: "费目代码" },
          { field: "C_FEE_NAM", title: "费目名称" },
          { field: "EX_FEE_COD", title: "公开代码" },
          { field: "EX_FEE_NAM", title: "公开费目" }
        ],
        specNoreturnApi: [
          { field: "EX_FEE_COD", returnName: "exFeeCod" },
          { field: "EX_FEE_NAM", returnName: "exFeeNam" }
        ],
        specNourl: "/business/contract/cfeeNamPage"
      }
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() {
    this.getDate();
  },
  methods: {
    handleSubmit() {
      this.$refs.editHeadForm
        .validate(valid => {})
        .then(valid => {
          let loadingInstance = Loading.service({
            fullscreen: true,
            text: "正在保存………"
          });
          addObj(this.dataForm)
            .then(data => {
              this.$message.success("保存成功");
              loadingInstance.close();
              this.dialogFormVisible = false;
              this.getDate();
            })
            .catch(() => {
              loadingInstance.close();
            });
        });
    },
    getDate() {
      //数据获取
      this.tableFeeload = true;
      fetchList(this.datanow.contNo, this.datanow.unitCod)
        .then(response => {
          this.tableFeeData = response.data.data;
          this.tablePage.total = response.data.data.total;
          this.tableFeeload = false;
          this.dataHeadNow = null;
        })
        .catch(() => {
          this.tableFeeload = false;
        });
    },
    addNow() {},
    rowClick({ row }) {
      this.dataHeadNow = row;
    },
    closeWindows() {
      this.dialogFormVisible = false;
    },
    addOpen() {
      this.dataForm = {};
      this.dataForm.exFeeNam = "超期堆存费";
      this.dataForm.exFeeCod = "0131";
      this.dataForm.unitCod = this.datanow.unitCod;
      this.dataForm.unitNam = this.datanow.unitNam;
      this.dataForm.contNo = this.datanow.contNo;
      this.dataForm.exId = this.datanow.pkid;
      this.dialogFormVisible = true;
    },
    editOpen() {
      if (this.dataHeadNow == null) {
        this.$message.error("请选择一个进行修改");
        return;
      }
      this.dataForm = this.dataHeadNow;
      this.dialogFormVisible = true;
    },
    formatStatus({ cellValue }) {
      if (cellValue == 1) {
        return "已提交";
      }
      if (cellValue == 0 || cellValue == null) {
        return "待提交";
      }
      if (cellValue == 9) {
        return "已支付";
      }
    },
    deletFee() {
      if (this.dataHeadNow == null) {
        this.$message.error("请选择一个进行删除");
        return;
      }
      this.$XModal.confirm("您确定要删除吗？").then(type => {
        if (type == "confirm") {
          delObj(this.dataHeadNow.pkid,this.dataHeadNow.unitCod)
            .then(data => {
              this.$message.success("删除成功");
              this.getDate();
            })
            .catch(() => {
            });
        }
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.business-transportcontract-fee {
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