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
  <div class="mioyardhead">
    <basic-container>
      <vxe-form :data="formObj" size="mini" @reset="resetForm">
        <vxe-form-item title="单号：" field="informNo" size="mini" span="6">
          <vxe-input v-model="formObj.informNo" clearable size="mini"></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="合同号：" field="contNo" size="mini" span="6">
          <vxe-input v-model="formObj.contNo" clearable size="mini"></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="船号：" field="shipNo" size="mini" span="6">
          <vxe-input v-model="formObj.shipNo" clearable size="mini"></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="船名：" field="shipNam" size="mini" span="6">
          <EditDown
            :objs="formObj"
            :property="downTableOption.shipDataname"
            :tableColumns="downTableOption.shipDataColum"
            :urlapi="downTableOption.shipDataurl"
            :returnApi="downTableOption.shipDatareturnApi"
            sizenow="mini"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item title="内外贸：" field="tradeId" size="mini" span="5">
          <vxe-select v-model="formObj.tradeId" clearable size="mini">
            <vxe-option value="1" label="内贸"></vxe-option>
            <vxe-option value="0" label="外贸"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item title="进出口：" field="inoutId" size="mini" span="5">
          <vxe-select v-model="formObj.inoutId" clearable size="mini">
            <vxe-option value="1" label="进口"></vxe-option>
            <vxe-option value="0" label="出口"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item title="状态：" field="ifendId" size="mini" span="5">
          <vxe-select v-model="formObj.ifendId" clearable size="mini">
            <vxe-option value="1" label="封存"></vxe-option>
            <vxe-option value="0" label="未封存"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item title="合同类型：" field="contType" size="mini" span="5">
          <vxe-select v-model="formObj.contType" clearable size="mini">
            <vxe-option value="01" label="正常"></vxe-option>
            <vxe-option value="02" label="大件设备"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="center">
          <vxe-button status="primary" icon="fa fa-search" @click="getDate()">查询</vxe-button>
          <vxe-button type="reset" icon="fa fa-repeat">重置</vxe-button>
        </vxe-form-item>
      </vxe-form>
      <vxe-toolbar perfect size="mini">
        <template v-slot:buttons>
          <vxe-button type="text" class="fa fa-plus" @click="editHead('add')">新增</vxe-button>
          <vxe-button type="text" class="fa fa-pencil-square-o" @click="editHead('edit')">修改</vxe-button>
          <vxe-button type="text" class="fa fa-lock" @click="lockOne()">封存</vxe-button>
          <vxe-button type="text" class="fa fa-unlock-alt" @click="unlockOne()">解封</vxe-button>
          <vxe-button type="text" class="fa fa-file-excel-o" @click="excelOut()">EXCEL导出</vxe-button>
         <!-- <vxe-button type="text" class="fa fa-file-excel-o" @click="cs()">测试区块链</vxe-button>-->
        </template>
      </vxe-toolbar>
      <el-row>
        <el-col :span="24">
          <vxe-table
            border
            show-overflow
            keep-source
            highlight-current-row
            size="mini"
            :data="tableDataMioYardHead"
            :loading="tableloadMioYardHead"
            :cell-style="cellStyle"
            :round="true"
            align="center"
            height="300px"
            ref="tableMioYardHead"
            @cell-click="rowclick"
          >
            <vxe-table-column field="informNo" title="单号" width="110px"></vxe-table-column>
            <vxe-table-column field="contNo" width="100" title="合同号"></vxe-table-column>
            <vxe-table-column field="contType" title="合同类型" width="80" :formatter="formatterType"></vxe-table-column>
            <vxe-table-column field="ifendId" title="是否封存" width="80" :formatter="formatterIfEnd"></vxe-table-column>
            <vxe-table-column field="consignNam" title="作业委托人" width="200"></vxe-table-column>
            <vxe-table-column field="tradeId" title="内外贸" width="80" :formatter="formatterTradeId"></vxe-table-column>
            <vxe-table-column field="inoutId" title="进出口" width="80" :formatter="formatterInoutId"></vxe-table-column>
            <vxe-table-column field="yardNam" title="场地" width="80"></vxe-table-column>
            <vxe-table-column field="shipNo" title="船号" width="100"></vxe-table-column>
            <vxe-table-column field="shipNam" title="船名" width="100"></vxe-table-column>
            <vxe-table-column field="dcType" title="堆存方式" width="100"></vxe-table-column>
            <vxe-table-column field="outNo" title="出库单号" width="100"></vxe-table-column>
            <vxe-table-column field="inYardWay" title="入库方式" width="100"></vxe-table-column>
            <vxe-table-column field="outYardWay" title="出库方式" width="100"></vxe-table-column>
            <vxe-table-column field="subunitNam" title="保管单位" width="100"></vxe-table-column>
            <vxe-table-column field="recordDte" title="记录日期" width="150"></vxe-table-column>
            <vxe-table-column field="cargoNam" title="货名" width="100"></vxe-table-column>
            <vxe-table-column field="cargoKindNam" title="价格货类" width="100"></vxe-table-column>
            <vxe-table-column field="cargoKindNamDl" title="大货类" width="100"></vxe-table-column>
            <vxe-table-column field="checkNam" title="检算员" width="100"></vxe-table-column>
            <vxe-table-column field="ifendDate" title="封存日期" width="100"></vxe-table-column>
          </vxe-table>
          <vxe-pager
            perfect
            size="mini"
            :loading="tableloadMioYardHead"
            :current-page="tablePage.currentPage"
            :page-size="tablePage.pageSize"
            :total="tablePage.total"
            :layouts="['PrevPage', 'JumpNumber', 'NextPage', 'FullJump', 'Sizes', 'Total']"
            @page-change="handlePageChange"
          ></vxe-pager>
        </el-col>
        <el-col :span="24">
          <vxe-toolbar perfect size="mini">
            <template v-slot:buttons>
              <vxe-button type="text" class="fa fa-plus" @click="getCargoData()">数据提取</vxe-button>
              <vxe-button type="text" class="fa fa-floppy-o" @click="saveAll()">保存</vxe-button>
            </template>
          </vxe-toolbar>
          <vxe-table
            border
            show-overflow
            keep-source
            size="mini"
            :data="tableDataMioYardBody"
            :loading="tableloadMioYardBody"
            :round="true"
            align="center"
            height="340px"
            ref="tableMioYardBody"
            :edit-config="{trigger: 'click', mode: 'row'}"
            :mouse-config="{selected: true}"
            :checkbox-config="{range: true}"
          >
            <vxe-table-column type="seq" width="100" title="序号"></vxe-table-column>
            <vxe-table-column field="billNo" title="提单号"></vxe-table-column>
            <vxe-table-column
              field="inyardDte"
              title="入库日期"
              :formatter="['formatDate', 'yyyy-MM-dd']"
            ></vxe-table-column>
            <vxe-table-column field="ipiecesNum" title="入库件数"></vxe-table-column>
            <vxe-table-column field="iweightWgt" title="入库吨数"></vxe-table-column>
            <vxe-table-column
              field="outyardDte"
              title="出库日期"
              :formatter="['formatDate', 'yyyy-MM-dd']"
            ></vxe-table-column>
            <vxe-table-column field="opiecesNum" title="出库件数"></vxe-table-column>
            <vxe-table-column field="oweightWgt" title="出库吨数"></vxe-table-column>
            <vxe-table-column title="单件重<=45">
              <vxe-table-column
                field="pieceNum1"
                title="件数"
                width="100"
                :edit-render="{autofocus: '.vxe-input--inner'}"
              >
                <template v-slot:edit="{ row }">
                  <vxe-input type="number" v-model="row.pieceNum1"></vxe-input>
                </template>
              </vxe-table-column>
              <vxe-table-column
                field="weightWgt1"
                title="件重"
                width="100"
                :edit-render="{autofocus: '.vxe-input--inner'}"
              >
                <template v-slot:edit="{ row }">
                  <vxe-input type="number" v-model="row.weightWgt1"></vxe-input>
                </template>
              </vxe-table-column>
            </vxe-table-column>
            <vxe-table-column title="单件重>45">
              <vxe-table-column
                field="pieceNum2"
                title="件数"
                width="100"
                :edit-render="{autofocus: '.vxe-input--inner'}"
              >
                <template v-slot:edit="{ row }">
                  <vxe-input type="number" v-model="row.pieceNum2"></vxe-input>
                </template>
              </vxe-table-column>
              <vxe-table-column
                field="weightWgt2"
                title="件重"
                width="100"
                :edit-render="{autofocus: '.vxe-input--inner'}"
              >
                <template v-slot:edit="{ row }">
                  <vxe-input type="number" v-model="row.weightWgt2"></vxe-input>
                </template>
              </vxe-table-column>
            </vxe-table-column>
          </vxe-table>
        </el-col>
      </el-row>
      <vxe-modal
        v-if="dialogFormVisibleedit"
        v-model="dialogFormVisibleedit"
        title="信息维护"
        width="1000"
        height="600"
        resize
        storage
      >
        <Edit :datanow="dateNow" @dictItemVisible="dictItemVisible" @selectList="getDate"></Edit>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import {
  fetchList,
  fetchBodyList,
  saveBody,
  lockHead,
  unlockHead,
  saveBodyData,
  excelOutPost,
  csPost,
} from "@/api/yard/mioyardhead";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import Edit from "./edit";
import EditDown from "../../baseUtile/EditDown";
export default {
  components: { Edit, EditDown },
  name: "mioyardhead",
  data() {
    return {
      formObj: {
        shipNo: null,
        shipNam: null,
        tradeId: null,
        inoutId: null,
        ifendId: null,
        contType: null,
      },
      dialogFormVisibleedit: false,
      tableDataMioYardHead: [],
      tableDataMioYardBody: [],
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      tableloadMioYardHead: false,
      tableloadMioYardBody: false,
      dataHeadNow: {},
      dateNow: {},
      downTableOption: {
        //费目下拉菜单展示项
        agentColum: [
          { field: "AGENT_COD", title: "客户代码" },
          { field: "AGENT_NAM", title: "客户名称" },
        ],
        //费目下拉菜单的返回项
        agentreturnApi: [{ field: "AGENT_NAM", returnName: "agentNam_02" }],
        //费目下拉菜单的路径
        agenturl: "/ship/baseCod/agent/page",
        agentname: "agentNam_02",
        //业务员下拉菜单展示项
        shipDataColum: [
          { field: "CALL_NO", title: "呼号" },
          { field: "C_SHIP_NAM", title: "船名" },
          { field: "E_SHIP_NAM", title: "英文船名" },
        ],
        //下拉菜单的路径
        shipDataurl: "/ship/baseCod/shipdata/page",
        shipDataname: "shipNam",
        shipDatareturnApi: [{ field: "C_SHIP_NAM", returnName: "shipNam" }],
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
      this.tableloadMioYardHead = true;
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
          this.tableDataMioYardHead = response.data.data.records;
          this.tablePage.total = response.data.data.total;
          this.tableloadMioYardHead = false;
        })
        .catch(() => {
          this.tableloadMioYardHead = false;
        });
    },
    getBody() {
      this.tableloadMioYardBody = true;
      fetchBodyList(this.dataHeadNow.informNo)
        .then((response) => {
          this.tableDataMioYardBody = response.data.data;
          this.tableloadMioYardBody = false;
        })
        .catch(() => {
          this.tableloadMioYardBody = false;
        });
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    getCargoData() {
      if (this.dataHeadNow != null && this.dataHeadNow.informNo != null) {
        let loadingInstance = Loading.service({
          fullscreen: true,
          text: "数据获取中……",
        });
        saveBody(this.dataHeadNow.informNo)
          .then((data) => {
            this.$message.success("处理成功");
            loadingInstance.close();
            this.getBody();
          })
          .catch(() => {
            loadingInstance.close();
          });
      } else {
        this.$message.error("请选择一条数据进行提取");
      }
    },
    rowclick({ row }) {
      this.dataHeadNow = row;
      this.getBody();
    },
    lockOne() {
      if (this.dataHeadNow != null && this.dataHeadNow.informNo != null) {
        let loadingInstance = Loading.service({
          fullscreen: true,
          text: "封存中……",
        });
        lockHead(this.dataHeadNow.informNo)
          .then((data) => {
            this.$message.success("封存成功");
            loadingInstance.close();
            this.getDate();
          })
          .catch(() => {
            loadingInstance.close();
          });
      } else {
        this.$message.error("请选择一条数据进行提取");
      }
    },
    unlockOne() {
      if (this.dataHeadNow != null && this.dataHeadNow.informNo != null) {
        let loadingInstance = Loading.service({
          fullscreen: true,
          text: "解封中……",
        });
        unlockHead(this.dataHeadNow.informNo)
          .then((data) => {
            this.$message.success("解封成功");
            loadingInstance.close();
            this.getDate();
          })
          .catch(() => {
            loadingInstance.close();
          });
      } else {
        this.$message.error("请选择一条数据进行提取");
      }
    },
    excelOut() {
      if (this.dataHeadNow != null && this.dataHeadNow.informNo != null) {
        let loadingInstance = Loading.service({
          fullscreen: true,
          text: "EXCEL生成中……",
        });
        excelOutPost(this.dataHeadNow.informNo)
          .then(() => {
            loadingInstance.close();
          })
          .catch(() => {
            loadingInstance.close();
          });
      } else {
        this.$message.error("请选择一条数据进行提取");
      }
    },
    editHead(type) {
      if (type === "edit") {
        if (this.dataHeadNow != null && this.dataHeadNow.informNo != null) {
          this.dateNow = this.dataHeadNow;
          this.dialogFormVisibleedit = true;
        } else {
          this.$message.error("请选择一个数据进行修改");
        }
      } else if (type === "add") {
        this.dateNow = {};
        this.dialogFormVisibleedit = true;
      }
    },
    async saveAll() {
      const errMap = await this.$refs.tableMioYardBody
        .fullValidate()
        .catch((errMap) => errMap);
      if (errMap) {
      } else {
        let loadingInstance = Loading.service({ fullscreen: true });
        saveBodyData(this.$refs.tableMioYardBody.getRecordset())
          .then((data) => {
            this.$message.success("保存成功");
            loadingInstance.close();
            this.getDate();
          })
          .catch(() => {
            loadingInstance.close();
          });
      }
    },
    cs(){
       let loadingInstance = Loading.service({
          fullscreen: true,
          text: "测试区块链中……",
        });
      csPost()
          .then((data) => {
            loadingInstance.close();
          })
          .catch(() => {
            loadingInstance.close();
          });
    },
    formatterTradeId({ cellValue }) {
      if (cellValue == "1") {
        return "内贸";
      } else if (cellValue == "0") {
        return "外贸";
      }
    },
    formatterType({ cellValue }) {
      if (cellValue == "01") {
        return "正常";
      } else if (cellValue == "02") {
        return "大件";
      }
    },
    formatterInoutId({ cellValue }) {
      if (cellValue == "1") {
        return "进口";
      } else if (cellValue == "0") {
        return "出口";
      }
    },
    formatterIfEnd({ cellValue }) {
      if (cellValue == 1) {
        return "封存";
      } else {
        return "未封存";
      }
    },
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
    },
    resetForm() {
      this.getDate();
    },
    cellStyle({ row, rowIndex, column, columnIndex }) {
      if (column.property === "ifendId") {
        if (row.ifendId == 1) {
          return {
            color: "red",
          };
        } else {
          return {
            color: "blue",
          };
        }
      }
    },
  },
};
</script>
<style lang="scss" scoped>
.mioyardhead {
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