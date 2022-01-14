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
<!-- 卸车作业完成 -->
<template>
  <div class="zsttrainload-xh">
    <basic-container>
      <vxe-form :data="formObj">
        <vxe-form-item title="日期" field="tjTime" size="mini" span="3">
          <vxe-input v-model="formObj.tjTime" placeholder="日期" type="date" valueFormat="yyyyMMdd"></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="班次" field="classId" size="mini" span="3">
          <vxe-select v-model="formObj.classId">
            <vxe-option value="1" label="1班次20:00-08:00"></vxe-option>
            <vxe-option value="2" label="2班次08:00-20:00"></vxe-option>
          </vxe-select>
          </vxe-input>
        </vxe-form-item>
        <vxe-form-item title="作业区" field="unitCod" size="mini" span="6">
          <EditDown :objs="formObj" :property="downTableOption.unitNam" :tableColumns="downTableOption.unitColum"
            :urlapi="downTableOption.uniturl" :returnApi="downTableOption.unitreturnApi" sizenow="mini"></EditDown>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="center">
          <vxe-button status="primary" icon="fa fa-search" @click="selectTable()">查询</vxe-button>
          <vxe-button type="reset" icon="fa fa-repeat" @click="resetForm">重置</vxe-button>
        </vxe-form-item>
        </vxe-form-item>
        <vxe-form-item>
          <vxe-button status="primary" @click="insert()">数据处理</vxe-button>
        </vxe-form-item>
      </vxe-form>
      <vxe-table border show-overflow keep-source resizable size="mini" :data="tableData" :loading="tableload"
        :round="true" align="center" height="700px" ref="tableCar">
        <vxe-table-column type="seq" title="序号" width=4%></vxe-table-column>
        <vxe-table-colgroup title="港口组织机构">
          <vxe-table-column field="ykportCod" width=4%></vxe-table-column>
          <vxe-table-column field="ykportNam" width=6%></vxe-table-column>
        </vxe-table-colgroup>
        <vxe-table-column field="beginTim" title="起始时间" width='8%'></vxe-table-column>
        <vxe-table-column field="endTim" title="终止时间" width=8%></vxe-table-column>
        <vxe-table-column field="classId" title="班次" width=4%></vxe-table-column>
        <vxe-table-colgroup title="作业区">
          <vxe-table-column field="unitCod" title="" width=3%></vxe-table-column>
          <vxe-table-column field="unitNam" title="" width=15%></vxe-table-column>
        </vxe-table-colgroup>
        <vxe-table-column field="cargoKindCod" title="货种代码"></vxe-table-column>
        <vxe-table-column field="cargoKindNam" title="货种名称"></vxe-table-column>
        <vxe-table-column field="trackCod" title="股道"></vxe-table-column>
        <vxe-table-column field="trackNam" title="股道"></vxe-table-column>
        <vxe-table-column field="planWgt" title="本班倒车计划"></vxe-table-column>
        <vxe-table-column field="weightWgt" title="本班倒车"></vxe-table-column>
        <vxe-table-column field="takeWgt" title="上班待卸"></vxe-table-column>
        <vxe-table-column field="beforplanWgt" title="本班卸车计划"></vxe-table-column>

      </vxe-table>
      <vxe-pager perfect size="mini" :loading="tableload" :current-page="tablePage.currentPage"
        :page-size="tablePage.pageSize" :total="tablePage.total" :layouts="[
            'PrevPage',
            'JumpNumber',
            'NextPage',
            'FullJump',
            'Sizes',
            'Total'
          ]" @page-change="handlePageChange"></vxe-pager>

    </basic-container>
  </div>
</template>

<script>
  import { getPage, dataProcess, getDataToPd } from "@/api/caccentre/zsttrainloadxh";
  import { mapGetters } from "vuex";
  import { Loading } from "element-ui";
  import Vue from "vue";
  import VXETable from "vxe-table";
  import EditDown from "../../baseUtile/EditDown";
  export default {
    components: { EditDown },
    name: "zsttrainload-xh",
    data() {
      return {
        formObj: {
          unitNam: null,
          unitCod: null,
          classId: null
        },

        dialogFormVisibleedit: false,
        tableData: [],
        dataNow: {},
        tablePage: {
          total: 0, // 总页数
          currentPage: 1, // 当前页数
          pageSize: 20 // 每页显示多少条
        },
        tableload: false,
        downTableOption: {
          //作业区下拉展示项
          unitNam: "unitNam",
          unitColum: [
            { field: "UNIT_COD", title: "单位代码" },
            { field: "UNIT_NAM", title: "单位名称" },
          ],
          unitreturnApi: [
            { field: "UNIT_COD", returnName: "unitCod" },
            { field: "UNIT_NAM", returnName: "unitNam" },
          ],
          uniturl: "/basecode/basecod/getUnitNam",
        },
      };
    },
    computed: {
      ...mapGetters(["userInfo", "permissions"])
    },
    created() {
      this.getDate();
    },
    methods: {
      getDate() {
        this.tableload = true;
        getPage(

          Object.assign(
            {
              current: this.tablePage.currentPage,
              size: this.tablePage.pageSize
            },
            this.formObj,
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
      insert() {
        this.insertData();
      },
      insertData() {

        getDataToPd(
          Object.assign({ beginDate: this.formObj.tjTime }) ,
        )
          .then((data) => {
            alert(data.data.data)
            if (data.data.data == '0') {
              this.doData();
            } else {
              this.$XModal
                .confirm("该日数据已经产生，是否要重新处理?")
                .then(type => {
                  if (type == "confirm") {
                    this.doData();
                  }
                });
            }
          })
          .catch(() => {

          });

      },
      doData() {
        let loadingInstance = Loading.service({
          fullscreen: true,
          text: "正在处理………",
        });
        dataProcess(
          Object.assign({ beginDate: this.formObj.tjTime }) ,
        )
          .then((data) => {
            this.$message.success("数据处理成功");
            loadingInstance.close();
          })
          .catch(() => {
            loadingInstance.close();
          });
      },
      handlePageChange({ currentPage, pageSize }) {
        this.tablePage.currentPage = currentPage;
        this.tablePage.pageSize = pageSize;
        this.getDate();
      },
      selectTable() {
        this.tablePage.currentPage = 1;
        this.getDate();
      },
      resetForm() {

      },

      dictItemVisible() {
        this.dialogFormVisibleedit = false;
        this.dataNow = null;
      }
    }
  };
</script>
<style lang="scss" scoped>

</style>