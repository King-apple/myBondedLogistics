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
  <div class="zyworkresourcescar">
    <basic-container>
      <vxe-form :data="formObj">
        <vxe-form-item title="时间" field="yearFee" size="mini" span="3">
          <vxe-input v-model="formObj.yearFee" type="year" valueFormat="yyyy" clearable></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="单位名称" field="unitNam" size="mini" span="6">
          <EditDown
            :objs="formObj"
            :property="downTableOption.unitNam"
            :tableColumns="downTableOption.unitColum"
            :urlapi="downTableOption.uniturl"
            :returnApi="downTableOption.unitreturnApi"
            sizenow="mini"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item title="价格货类" field="cargoKindNam" size="mini" span="4">
          <EditDown
            :objs="formObj"
            :property="downTableOption.cargoname"
            :tableColumns="downTableOption.cargoColum"
            :urlapi="downTableOption.cargourl"
            :returnApi="downTableOption.cargoreturnApi"
            sizenow="mini"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item title="内外贸" field="tradeId" size="mini" span="3">
        <vxe-select v-model="formObj.tradeId"  >
            <vxe-option value="0" label="外贸"></vxe-option>
            <vxe-option value="1" label="内贸"></vxe-option> 
          </vxe-select>
        </vxe-form-item>
         <vxe-form-item title="进出口" field="inoutId" size="mini" span="3">
        <vxe-select v-model="formObj.inoutId"  >
            <vxe-option value="0" label="出口"></vxe-option>
            <vxe-option value="1" label="进口"></vxe-option> 
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="center">
          <vxe-button status="primary" icon="fa fa-search" @click="getDate()">查询</vxe-button>
          <vxe-button status="primary" icon="fa fa-trash" @click="clear()">清空</vxe-button>
        </vxe-form-item>
      </vxe-form>
      <el-row :gutter="15">
        <vxe-toolbar perfect size="mini">
          <template v-slot:buttons>
            <vxe-button type="text" class="fa fa-plus" @click="add()">新增</vxe-button>
            <vxe-button type="text" icon="fa fa-floppy-o" @click="edit()">修改</vxe-button>
            <vxe-button type="text" icon="fa fa-minus" @click="Del()">删除</vxe-button>
          </template>
        </vxe-toolbar>
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
          <vxe-table-column field="seqNo" title="序号" width="8%"></vxe-table-column>
          <vxe-table-column field="yearFee" title="年份" width="4%"></vxe-table-column>
          <vxe-table-column field="monthFee" title="月份" width="4%"></vxe-table-column>
          <vxe-table-column field="unitName" title="单位" width="16%"></vxe-table-column>
          <vxe-table-column field="cargoVolLower" title="单船下限" width="6%"></vxe-table-column>
          <vxe-table-column field="cargoVolUpper" title="单船上限" width="6%"></vxe-table-column>
          <vxe-table-column field="weightLower" title="下限吨" width="5%"></vxe-table-column>
          <vxe-table-column field="weightUpper" title="上限吨" width="5%"></vxe-table-column>
          <vxe-table-column field="noDays" title="免收天数" width="6%"></vxe-table-column>
          <vxe-table-column field="beginDay" title="起始天数" width="6%"></vxe-table-column>
          <vxe-table-column field="endDay" title="结束天数" width="7%"></vxe-table-column>
          <vxe-table-column field="feeRat" title="最终费率" width="6%"></vxe-table-column>
          <vxe-table-column field="tradeId" title="内外贸" width="5%" :formatter="formatterTrad"></vxe-table-column>
          <vxe-table-column field="inoutId" title="进出口" width="5%" :formatter="formatterIo"></vxe-table-column>
          <vxe-table-column field="cargoKindNam" title="价格货类" width="8%"></vxe-table-column>
          <vxe-table-column field="effect" title="有效" width="4%" :formatter="formatterUse"></vxe-table-column>
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
      </el-row>
      <vxe-modal
        v-if="dialogFormVisibleedit"
        v-model="dialogFormVisibleedit"
        title="基本信息维护"
        width="800"
        height="600"
        resize
        storage
      >
        <EditHead :datanow="dataNext" @dictItemVisible="dictItemVisible" @selectList="getDate"></EditHead>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import { fetchList, delObj } from "@/api/business/tfeedc";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import EditHead from "./editHead";
import moment from "moment";
import EditDown from "../../baseUtile/EditDown";
export default {
  components: { EditHead, EditDown },
  name: "tfeedc",
  data() {
    return {
      formObj: {
        yearFee: moment(new Date()).format("YYYY"),
        unitCod: null,
        unitNam: null,
        cargoKindCod: null,
        cargoKindNam: null,
        inoutId:null
      },
      dataHeadNow: {},
      dataNext: {},
      dialogFormVisibleedit: false,
      tableData: [],

      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      tableload: false,
      tradList: [
        {
          label: "外贸",
          value: "0"
        },
        {
          label: "内贸",
          value: "1"
        }
      ],
      ioList: [
        {
          label: "出口",
          value: "0"
        },
        {
          label: "进口",
          value: "1"
        }
      ],
      useList: [
        {
          label: "无效",
          value: 0
        },
        {
          label: "有效",
          value: 1
        }
      ],
      downTableOption: {
        //单位
        unitNam: "unitNam",
        unitColum: [
          { field: "UNIT_COD", title: "单位代码" },
          { field: "UNIT_NAM", title: "单位名称" }
        ],
        unitreturnApi: [
          { field: "UNIT_COD", returnName: "unitCod" },
          { field: "UNIT_NAM", returnName: "unitNam" }
        ],
        uniturl: "/basecode/basecod/getUnitNam",
        //货物
        cargoname: "cargoKindNam",
        cargoColum: [
          { field: "CARGO_KIND_COD_PRICE", title: "价格货类代码" },
          { field: "CARGO_KIND_NAM_PRICE", title: "价格货类名称" }
        ],
        cargoreturnApi: [
          { field: "CARGO_KIND_COD_PRICE", returnName: "cargoKindCod" },
          { field: "CARGO_KIND_NAM_PRICE", returnName: "cargoKindNam" }
        ],
        cargourl: "/basecode/basecod/getCargoNam"
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

    rowclick({ row }) {
      this.dataHeadNow = row;
    },
    add() {
      this.dataNext = {};
      this.dialogFormVisibleedit = true;
    },
    edit() {
      if (this.dataHeadNow != null && this.dataHeadNow.seqNo != null) {
        this.dataNext = this.dataHeadNow;
        this.dialogFormVisibleedit = true;
      } else {
        this.$message.success("请选择一条记录");
      }
    },
    Del() {
      let that = this;
      if (this.dataHeadNow != null && this.dataHeadNow.seqNo != null) {
        this.$confirm("是否确认删除", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(function() {
            return delObj(that.dataHeadNow.seqNo);
          })
          .then(data => {
            this.$message.success("删除成功");
            this.getDate(this.page);
          });
      } else {
        this.$message.success("请选择一条记录");
      }
    },
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
    },
    async save() {},
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    formatterTrad({ cellValue }) {
      let item = this.tradList.find(item => item.value === cellValue);
      return item ? item.label : "";
    },
    formatterIo({ cellValue }) {
      let item = this.ioList.find(item => item.value === cellValue);
      return item ? item.label : "";
    },
    formatterUse({ cellValue }) {
      let item = this.useList.find(item => item.value === cellValue);
      return item ? item.label : "";
    },
    clear() {
      this.formObj.unitCod = null;
      this.formObj.unitNam = null;
      this.formObj.cargoKindCod = null;
      this.formObj.cargoKindNam = null;
    }
  }
};
</script>
<style lang="scss" scoped>
.zyworkresourcescar {
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