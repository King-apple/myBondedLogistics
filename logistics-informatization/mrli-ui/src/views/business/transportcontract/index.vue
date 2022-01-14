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
  <div class="business-transportcontract-index">
    <basic-container>
      <vxe-form :data="formObj" size="mini">
        <vxe-form-item title="开始：" field="timeBegin" size="mini" span="4">
          <vxe-input
            v-model="formObj.timeBegin"
            placeholder="开始时间"
            type="date"
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="至  " field="timeEnd" size="mini" span="4">
          <vxe-input
            v-model="formObj.timeEnd"
            placeholder="结束时间"
            type="date"
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="合同号：" field="contNo" size="mini" span="4">
          <vxe-input v-model="formObj.contNo" clearable size="mini"></vxe-input>
        </vxe-form-item>
        <vxe-form-item span="5" size="mini" align="center">
          <vxe-button
            status="primary"
            icon="fa fa-search"
            @click="selectTable()"
            >查询
          </vxe-button>
          <vxe-button type="reset" icon="fa fa-repeat" @click="resetForm">
            重置
          </vxe-button>
        </vxe-form-item>
      </vxe-form>
      <vxe-toolbar custom perfect size="mini">
        <template v-slot:buttons>
          <vxe-button type="text" class="fa fa-plus" @click="addNow()">
            新增
          </vxe-button>
          <vxe-button type="text" class="fa fa-eye" @click="viewNow()">
            查看
          </vxe-button>
          <vxe-button
            type="text"
            class="fa fa-pencil-square-o"
            @click="editNow()"
          >
            修改
          </vxe-button>
          <vxe-button type="text" class="fa fa-lock" @click="lockAndnolock(1)">
            封存
          </vxe-button>
          <vxe-button
            type="text"
            class="fa fa-unlock"
            @click="lockAndnolock(0)"
          >
            解封
          </vxe-button>
          <vxe-button type="text" class="fa fa-trash" @click="deleteNowCont()">
            作废
          </vxe-button>
          <vxe-button type="text" class="fa fa-money" @click="dcFeeOpen()">
            预交堆存费管理
          </vxe-button>
          <vxe-button type="text" class="fa fa-truck" @click="createOutYard()">
            生成出库单
          </vxe-button>
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
        :cell-style="cellStyle"
        :round="true"
        align="center"
        height="700px"
        ref="tabletransportcontract"
        :sort-config="{ remote: true }"
        @sort-change="sortChangeEvent"
        @cell-click="rowClick"
      >
        <vxe-table-column field="contNo" title="合同号" width="120" sortable />
        <vxe-table-column
          field="lockId"
          title="封存状态"
          width="95"
          :formatter="formatFc"
        />
        <vxe-table-column
          field="cargoKindCodName"
          title="货物种类"
          width="95"
          sortable
        />
        <vxe-table-column
          field="consignNam"
          title="代理货主"
          width="200"
          sortable
        />
        <vxe-table-column
          field="consignMan"
          title="代理人"
          width="95"
          sortable
        />
        <vxe-table-column
          field="consignPhone"
          title="代理电话"
          width="120"
          sortable
        />
        <vxe-table-column
          field="officeNam"
          title="单位名称"
          width="200"
          sortable
        />
        <vxe-table-column
          field="officeMan"
          title="经办人"
          width="95"
          sortable
        />
        <vxe-table-column
          field="officePhone"
          title="经办人电话"
          width="120"
          sortable
        />
        <vxe-table-column
          field="contDte"
          title="合同日期"
          width="150"
          sortable
        />
        <vxe-table-column
          field="ifendId"
          title="是否完结"
          width="95"
          formatter="formatYoN"
          sortable
        />
        <vxe-table-column
          field="zqId"
          title="是否直取"
          width="95"
          formatter="formatYoN"
          sortable
        />
        <vxe-table-column
          field="deposeId"
          title="是否作废"
          width="95"
          formatter="formatYoN"
          sortable
        />
        <vxe-table-column field="operNam" title="操作人" width="95" sortable />
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
        title="编辑合同"
        width="1600"
        height="900"
        resize
        storage
        fullscreen
      >
        <Edit
          :datanow="dataNow"
          :viewIf="viewIf"
          @dictItemVisible="dictItemVisible"
          @selectList="selectTable"
        />
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleDcFee"
        v-model="dialogFormVisibleDcFee"
        title="预付堆存费管理"
        width="700"
        height="650"
        resize
        storage
      >
        <DcFee
          :datanow="dataNow"
          @dictItemVisible="dictItemVisible"
          @selectList="selectTable"
        />
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import {
  fetchList,
  lockOrNot,
  deleteCont,
  createOutYardApi
} from "@/api/business/transportcontract";
import { mapGetters } from "vuex";
import EditDown from "@/views/baseUtile/EditDown";
import { Loading, Message } from "element-ui";
import Edit from "./edit.vue";
import DcFee from "./dcFee.vue";
export default {
  components: { EditDown, Edit, DcFee },
  data() {
    return {
      formObj: { timeBegin: null, timeEnd: null },
      tableData: [],
      tableFeeData: [],
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20 // 每页显示多少条
      },
      tableload: false,
      tableFeeload: false,
      dialogFormVisibleedit: false,
      dialogFormVisibleDcFee: false,
      dataNow: {}
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() {
    this.selectTable();
  },
  methods: {
    getDate() {
      //数据获取
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
          this.dataNow = null;
        })
        .catch(() => {
          this.tableload = false;
        });
    },
    handlePageChange({ currentPage, pageSize }) {
      //分页
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    selectTable() {
      this.tablePage.currentPage = 1;
      this.getDate();
    },
    resetForm() {
      //重置查询
    },
    sortChangeEvent({ column, property, order }) {
      //排序
      this.formObj.sqlSortNameOfMrLI = property;
      this.formObj.sqlSortOfMrLI = order;
      this.selectTable();
    },
    dictItemVisible() {
      //关闭弹出
      this.dialogFormVisibleedit = false;
      this.dialogFormVisibleDcFee = false;
    },
    addNow() {
      this.dataNow = null;
      this.dialogFormVisibleedit = true;
    },
    editNow() {
      if (this.dataNow == null) {
        this.$message.error("请选择一个进行修改");
        return;
      }
      this.dialogFormVisibleedit = true;
      this.viewIf = false;
    },
    viewNow() {
      if (this.dataNow == null) {
        this.$message.error("请选择一个进行查看");
        return;
      }
      this.viewIf = true;
      this.dialogFormVisibleedit = true;
    },
    rowClick({ row }) {
      //单击表格
      this.dataNow = row;
    },
    rowDbClick({ row }) {
      //双击表格
      this.dataNow = row;
    },
    formatFc({ cellValue }) {
      if (cellValue == 1) {
        return "是";
      }
      if (cellValue == 0 || cellValue == null) {
        return "否";
      }
    },
    lockAndnolock(lockId) {
      if (this.dataNow == null) {
        this.$message.error("请选择一个进行操作");
        return;
      }
      lockOrNot(this.dataNow.pkid, this.dataNow.unitCod, lockId)
        .then(data => {
          this.$message.success("操作成功");
          this.getDate();
        })
        .catch(() => {});
    },
    deleteNowCont() {
      if (this.dataNow == null) {
        this.$message.error("请选择一个进行操作");
        return;
      }
      this.$XModal.confirm("您确定要作废吗？").then(type => {
        if (type == "confirm") {
          deleteCont(this.dataNow.pkid, this.dataNow.unitCod)
            .then(data => {
              this.$message.success("操作成功");
              this.getDate();
            })
            .catch(() => {});
        }
      });
    },
    createOutYard() {
      if (this.dataNow == null) {
        this.$message.error("请选择一个进行操作");
        return;
      }
      let loadingInstance = Loading.service({
        fullscreen: true,
        text: "正在生成………"
      });
      createOutYardApi(this.dataNow.pkid, this.dataNow.unitCod, false)
        .then(data => {
          if (data.data == "exist") {
            this.$XModal
              .confirm("已经生成出库单，是否删除后重新生成")
              .then(type => {
                if (type == "confirm") {
                  createOutYardApi(
                    this.dataNow.pkid,
                    this.dataNow.unitCod,
                    true
                  )
                    .then(data => {
                      this.$message.success("生成成功");
                      this.getDate();
                      loadingInstance.close();
                    })
                    .catch(() => {
                      loadingInstance.close();
                    });
                }
              });
          } else {
            this.$message.success("生成成功");
            loadingInstance.close();
          }
        })
        .catch(() => {
          loadingInstance.close();
        });
    },
    dcFeeOpen() {
      if (this.dataNow == null) {
        this.$message.error("请选择一个进行操作");
        return;
      }
      this.dialogFormVisibleDcFee = true;
    },
    cellStyle({ row, rowIndex, column, columnIndex }) {
      if (column.property === "lockId") {
        if (row.lockId == 1) {
          return {
            color: "red"
          };
        } else {
          return {
            color: "blue"
          };
        }
      } else if (column.property === "ifendId") {
        if (row.ifendId == 1) {
          return {
            color: "red"
          };
        } else {
          return {
            color: "blue"
          };
        }
      } else if (column.property === "zqId") {
        if (row.zqId == 1) {
          return {
            color: "red"
          };
        } else {
          return {
            color: "blue"
          };
        }
      } else if (column.property === "deposeId") {
        if (row.deposeId == 1) {
          return {
            color: "red"
          };
        } else {
          return {
            color: "blue"
          };
        }
      } else if (column.property === "contNo") {
        return {
          color: "red"
        };
      }
    }
  }
};
</script>
<style lang="scss" scoped>
.business-transportcontract-index {
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