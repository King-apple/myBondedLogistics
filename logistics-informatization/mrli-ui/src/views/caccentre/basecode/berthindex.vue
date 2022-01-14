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
  <div class="caccentre-basecode-berthindex">
    <basic-container>
      <!--查询区-->
      
      <!--功能按钮-->
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
          <vxe-button
                type="text"
                class="fa fa-trash"
                @click="deleteCberth()"
                >删除</vxe-button
              >
          
        </template>
      </vxe-toolbar>
      <!--表格-->
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
      >
        <vxe-table-column type="seq" width="50" title="序号"></vxe-table-column>
        <vxe-table-column
          field="berthCod"
          title="泊位号"
          width="70"
        ></vxe-table-column>
        <vxe-table-column
          field="berthNam"
          title="泊位名称"
          width="80"
        ></vxe-table-column>
        <vxe-table-column
          field="berthCodNew"
          title="泊位(上传)"
          width="80"
        ></vxe-table-column>
        <vxe-table-column
          field="berthCapa"
          title="靠泊能力"
          width="75"
        ></vxe-table-column>
        <vxe-table-column
          field="berthLen"
          title="长度"
          width="60"
        ></vxe-table-column>
        <vxe-table-column
          field="berthFDeep"
          title="前水深"
          width="60"
        ></vxe-table-column>
        <vxe-table-column
          field="berthMDeep"
          title="中水深"
          width="60"
        ></vxe-table-column>
        <vxe-table-column
          field="berthBDeep"
          title="后水深"
          width="60"
        ></vxe-table-column>
        <vxe-table-column
          field="berthDeep"
          title="平均水深"
          width="70"
        ></vxe-table-column>
        <vxe-table-column
          field="berthBittRang"
          title="缆桩范围"
          width="80"
        ></vxe-table-column>
        <vxe-table-column
          field="berthBittDist"
          title="缆桩距离"
          width="70"
        ></vxe-table-column>
        <vxe-table-column
          field="berthKnd"
          title="泊位性质"
          width="80"
        ></vxe-table-column>
        <vxe-table-column
          field="berthUse"
          title="泊位用途"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="ck"
          title="货类"
          width="60"
        >
        <vxe-table-column
          field="cargoKindCod"
          title="代码"
          width="60"
        ></vxe-table-column>
        <vxe-table-column
          field="cargoKindName"
          title="货类名称"
          width="100"
        ></vxe-table-column>
        </vxe-table-column>
         <vxe-table-column
          field="stypp"
          title="服务"
          width="60"
        >
        <vxe-table-column
          field="serveTyp"
          title="代码"
          width="50"
        ></vxe-table-column>
        <vxe-table-column
          field="serveTypName"
          title="服务类型"
          width="100"
        ></vxe-table-column>
        </vxe-table-column>
        <vxe-table-column
          field="btypp"
          title="结构"
          width="60"
        >
        <vxe-table-column
          field="berthTyp"
          title="代码"
          width="50"
        ></vxe-table-column>
        <vxe-table-column
          field="berthTypName"
          title="结构类型 "
          width="100"
        ></vxe-table-column>
        </vxe-table-column>
        <vxe-table-column
          field="xpos"
          title="X坐标"
          width="80"
        ></vxe-table-column>
        <vxe-table-column
          field="ypos"
          title="Y坐标"
          width="80"
        ></vxe-table-column>
        <vxe-table-column
          field="berthId"
          title="泊位标识"
          width="70"
        ></vxe-table-column>
        <vxe-table-column
          field="ucodd"
          title="单位代码 "
          width="60"
        >
        <vxe-table-column
          field="unitCod"
          title="代码 "
          width="50"
        ></vxe-table-column>
        <vxe-table-column
          field="unitNam"
          title="单位名称 "
          width="120"
        ></vxe-table-column>
        </vxe-table-column>
        <vxe-table-column
          field="portAreaC"
          title="港区 "
          width="80"
        >
        <vxe-table-column
          field="portAreaCod"
          title="代码 "
          width="50"
        ></vxe-table-column>
         <vxe-table-column
          field="portArea"
          title="港区 "
          width="100"
        ></vxe-table-column>
        </vxe-table-column>
      </vxe-table>
      <!--分页区-->
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
      <!-----------------------次级页面-------------------------------------------------->
       
      <vxe-modal
        v-if="dialogFormVisibleAdd"
        v-model="dialogFormVisibleAdd"
        title="泊位代码信息维护"
        width="900"
        height="500"
        resize
        storage
      >
        <Add
          :datanow="dataNext" @dictItemVisible="dictItemVisible" @selectList="getDate"
        ></Add>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleedit"
        v-model="dialogFormVisibleedit"
        title="泊位代码信息修改"
        width="900"
        height="500"
        resize
        storage
      >
         <Edit
          :datanow="dataNext"
          @dictItemVisible="dictItemVisible"
          @selectListplan="getDate"
        ></Edit>
      </vxe-modal>
     
    </basic-container>
  </div>
</template>

<script>
import { fetchList,deletOne} from "@/api/caccentre/cberth";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import EditDown from "../../baseUtile/EditDown";
import Add from "./add";
import Edit from "./Edit";
export default {
  components: {Add,EditDown,Edit},
  name: "transport-base-index",
  data() {
    return {
      formObj: {
        cbs: null,
        cbh: null,
        shipNam: null,
        firstCod: null,
        hpxx: "A",
      },
      formT: {},
      tableData: [],
      tableData2: [],
      dateNow: {},
      dataHeadNow: {},
      dialogFormVisibleedit: false,
      dialogFormVisibleAdd: false,
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      tablePage2: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      tableload: false,
      tableload2: false,
      wclSum: "",
      jhlSum: "",
      sylSum: "",
      cslSum: "",
      downTableOption: {
        //称别组
        cbsNam: "cbs",
        cbsColum: [{ field: "CBS", title: "称别组" }],
        cbsreturnApi: [{ field: "CBS", returnName: "cbs" }],

        cbsurl: "/transport/hpmx/getCbsDoc",
        //船编号
        cbhNam: "cbh",
        cbhColum: [
          { field: "CBH", title: "船编号" },
          { field: "CM", title: "船名" },
        ],
        cbhreturnApi: [
          { field: "CBH", returnName: "cbh" },
          { field: "CM", returnName: "cm" },
        ],

        cbhurl: "/transport/hpmx/getCbhDoc",
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
          this.formObjs
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
    add(type) {
      if (type === "edit") {
        if (this.dataHeadNow != null && this.dataHeadNow.berthCod != null) {
          
          this.dataNext = this.dataHeadNow;
          this.dialogFormVisibleedit = true;
        } else {
          this.$message.error("请选择一个数据进行修改");
        }
      } else if (type === "add") {
        this.dataNext = {};
      this.dialogFormVisibleAdd = true;
      }
    },
    deleteCberth() {
      if (this.dataHeadNow == null || this.dataHeadNow.berthCod == null) {
        this.$message.error("请选择一条动态进行删除");
        return;
      }
      this.$XModal.confirm("您确定要删除吗？").then((type) => {
        if (type == "confirm") {
          deletOne(this.dataHeadNow.berthCod)
            .then((response) => {
              this.$message.success("删除成功");
              this.getDate();
            })
            .catch(() => {});
        }
      });
    },
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
      this.dialogFormVisibleAdd = false;
    },
    
    cellDBLClickEvent({ row }) {
      this.dateNow = row;
      this.dialogFormVisibleView = true;
    },
    viewCont() {
      this.tablePage2.currentPage = 1;
      if (this.dataHeadNow != null && this.dataHeadNow.bh != null) {
        this.dateNow = this.dataHeadNow;
        this.dialogFormVisibleView = true;
        this.getDate2();
      } else {
        this.$message.error("请选择一个进行查看");
      }
    },
    getDate2() {
      this.tableload2 = true;
      getStabc(
        Object.assign({
          current: this.tablePage2.currentPage,
          size: this.tablePage2.pageSize,
          bh: this.dataHeadNow.bh,
        })
      )
        .then(
          (response) => {
            this.tableData2 = response.data.data.records;
            this.tablePage2.total = response.data.data.total;
            this.tableload2 = false;
          },
          (this.formT.cbhh = this.dataHeadNow.cbh),
          (this.formT.cmm = this.dataHeadNow.cm),
          console.log("cbhh==" + this.formT.cbhh),
          console.log("cmm==" + this.formT.cmm)
        )
        .catch(() => {
          this.tableload2 = false;
        });
    },
    
    
    rowclick({ row }) {
      this.dataHeadNow = row;
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.getDate();
    },
    handlePageChange2({ currentPage, pageSize }) {
      this.tablePage2.currentPage = currentPage;
      this.tablePage2.pageSize = pageSize;
      this.getDate2();
    },

    formatterBpwc({ cellValue }) {
      if (cellValue == 1) {
        return "已完成";
      } else {
        return "未完成";
      }
    },

    
    
  },
};
</script>
<style lang="scss" scoped>
.transport-base-index {
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
.grid-content {
  margin: 10px auto;
}
</style>>