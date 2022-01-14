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
  <div class="yard-tallytj-index">
    <basic-container>
      <vxe-form :data="formObj" size="mini" @reset="resetForm">
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
        <vxe-form-item span="4" size="mini" align="center">
          <vxe-button status="primary" icon="fa fa-search" @click="getDate()"
            >查询</vxe-button
          >
          <vxe-button type="reset" icon="fa fa-repeat">重置</vxe-button>
        </vxe-form-item>
      </vxe-form>
      <el-row>
        <el-col :span="20">
          <div class="shipDiv">
            <img class="shipTop" src="./RDRVROOM.png" />
            <img class="shipBody" src="./Shipleft.png" />
            <div
              style="top: -220px; left: 70px; position: relative; z-index: 999"
            >
              <el-card class="box-card">
                <vxe-table
                  border
                  keep-source
                  highlight-current-row
                  size="mini"
                  :data="shipData1"
                  :round="true"
                  align="center"
                  height="190px"
                  style="width: 170px; top: -10px; left: -20px"
                >
                  <vxe-table-colgroup title="1仓">
                    <vxe-table-column
                      field="billNo"
                      title="提单号"
                      width="70"
                    ></vxe-table-column>
                    <vxe-table-column
                      field="weightSum"
                      title="重量"
                      width="80"
                    ></vxe-table-column>
                  </vxe-table-colgroup>
                </vxe-table>
              </el-card>
            </div>
            <div
              style="
                top: -430px;
                left: 240px;
                position: relative;
                z-index: 1000;
              "
            >
              <el-card class="box-card">
                <vxe-table
                  border
                  keep-source
                  highlight-current-row
                  size="mini"
                  :data="shipData2"
                  :round="true"
                  align="center"
                  height="190px"
                  style="width: 170px; top: -10px; left: -20px"
                >
                  <vxe-table-colgroup title="2仓">
                    <vxe-table-column
                      field="billNo"
                      title="提单号"
                      width="70"
                    ></vxe-table-column>
                    <vxe-table-column
                      field="weightSum"
                      title="重量"
                      width="80"
                    ></vxe-table-column>
                  </vxe-table-colgroup>
                </vxe-table>
              </el-card>
            </div>
            <div
              style="
                top: -640px;
                left: 410px;
                position: relative;
                z-index: 1000;
              "
            >
              <el-card class="box-card">
                <vxe-table
                  border
                  keep-source
                  highlight-current-row
                  size="mini"
                  :data="shipData3"
                  :round="true"
                  align="center"
                  height="190px"
                  style="width: 170px; top: -10px; left: -20px"
                >
                  <vxe-table-colgroup title="3仓">
                    <vxe-table-column
                      field="billNo"
                      title="提单号"
                      width="70"
                    ></vxe-table-column>
                    <vxe-table-column
                      field="weightSum"
                      title="重量"
                      width="80"
                    ></vxe-table-column>
                  </vxe-table-colgroup>
                </vxe-table>
              </el-card>
            </div>
            <div
              style="
                top: -850px;
                left: 580px;
                position: relative;
                z-index: 1000;
              "
            >
              <el-card class="box-card" body-style="overflow:auto;">
                <vxe-table
                  border
                  keep-source
                  highlight-current-row
                  size="mini"
                  :data="shipData4"
                  :round="true"
                  align="center"
                  height="190px"
                  style="width: 170px; top: -10px; left: -20px"
                >
                  <vxe-table-colgroup title="4仓">
                    <vxe-table-column
                      field="billNo"
                      title="提单号"
                      width="70"
                    ></vxe-table-column>
                    <vxe-table-column
                      field="weightSum"
                      title="重量"
                      width="80"
                    ></vxe-table-column>
                  </vxe-table-colgroup>
                </vxe-table>
              </el-card>
            </div>
            <div
              style="
                top: -1060px;
                left: 750px;
                position: relative;
                z-index: 1000;
              "
            >
              <el-card class="box-card">
                <vxe-table
                  border
                  keep-source
                  highlight-current-row
                  size="mini"
                  :data="shipData5"
                  :round="true"
                  align="center"
                  height="190px"
                  style="width: 170px; top: -10px; left: -20px"
                >
                  <vxe-table-colgroup title="5仓">
                    <vxe-table-column
                      field="billNo"
                      title="提单号"
                      width="70"
                    ></vxe-table-column>
                    <vxe-table-column
                      field="weightSum"
                      title="重量"
                      width="80"
                    ></vxe-table-column>
                  </vxe-table-colgroup>
                </vxe-table>
              </el-card>
            </div>
            <div
              style="
                top: -1270px;
                left: 920px;
                position: relative;
                z-index: 1000;
              "
            >
              <el-card class="box-card">
                <vxe-table
                  border
                  keep-source
                  highlight-current-row
                  size="mini"
                  :data="shipData6"
                  :round="true"
                  align="center"
                  height="190px"
                  style="width: 170px; top: -10px; left: -20px"
                >
                  <vxe-table-colgroup title="6仓">
                    <vxe-table-column
                      field="billNo"
                      title="提单号"
                      width="70"
                    ></vxe-table-column>
                    <vxe-table-column
                      field="weightSum"
                      title="重量"
                      width="80"
                    ></vxe-table-column>
                  </vxe-table-colgroup>
                </vxe-table>
              </el-card>
            </div>
            <div
              style="
                top: -1480px;
                left: 1090px;
                position: relative;
                z-index: 1000;
              "
            >
              <el-card class="box-card">
                <vxe-table
                  border
                  keep-source
                  highlight-current-row
                  size="mini"
                  :data="shipData7"
                  :round="true"
                  align="center"
                  height="190px"
                  style="width: 170px; top: -10px; left: -20px"
                >
                  <vxe-table-colgroup title="7仓">
                    <vxe-table-column
                      field="billNo"
                      title="提单号"
                      width="70"
                    ></vxe-table-column>
                    <vxe-table-column
                      field="weightSum"
                      title="重量"
                      width="80"
                    ></vxe-table-column>
                  </vxe-table-colgroup>
                </vxe-table>
              </el-card>
            </div>
          </div>
        </el-col>
        <el-col :span="4">
          <el-divider content-position="center">
            <strong>船舶基本信息</strong>
          </el-divider>
          <vxe-form :data="shipMessage" size="mini" title-width="60">
            <vxe-form-item title="船名" field="shipNam" size="mini" span="24">
              <vxe-input v-model="shipMessage.shipNam" readonly></vxe-input>
            </vxe-form-item>
            <vxe-form-item title="总件数" field="jSum" size="mini" span="24">
              <vxe-input v-model="shipMessage.jSum" readonly></vxe-input>
            </vxe-form-item>
            <vxe-form-item
              title="总吨数"
              field="weightSum"
              size="mini"
              span="24"
            >
              <vxe-input v-model="shipMessage.weightSum" readonly></vxe-input>
            </vxe-form-item>
            <vxe-form-item
              title="靠泊时间"
              field="kbTime"
              size="mini"
              span="24"
            >
              <vxe-input v-model="shipMessage.kbTime" readonly></vxe-input>
            </vxe-form-item>
            <vxe-form-item title="目的港" field="ddPort" size="mini" span="24">
              <vxe-input v-model="shipMessage.ddPort" readonly></vxe-input>
            </vxe-form-item>
            <vxe-form-item
              title="离港时间"
              field="lbTime"
              size="mini"
              span="24"
            >
              <vxe-input v-model="shipMessage.lbTime" readonly></vxe-input>
            </vxe-form-item>
          </vxe-form>
        </el-col>
      </el-row>
    </basic-container>
  </div>
</template>

<script>
import { fetchList } from "@/api/yard/tallytj";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import EditDown from "../../baseUtile/EditDown";
export default {
  components: { EditDown },
  name: "yard-tallytj-index",
  data() {
    return {
      formObj: {
        shipNo: null,
        shipNam: null,
        tradeId: null,
        inoutId: null,
        ifendId: null,
        contType: null,
        unloadId: null,
        lbTime: null,
        kbTime: null,
        ddPort: null,
      },
      shipMessage: {
        ddPort: null,
        lbTime: null,
        kbTime: null,
        weightSum: null,
        jSum: null,
        shipNam: null,
      },
      shipData1: [],
      shipData2: [],
      shipData3: [],
      shipData4: [],
      shipData5: [],
      shipData6: [],
      shipData7: [],
      downTableOption: {
        //业务员下拉菜单展示项
        shipDataColum: [
          { field: "SHIP_NO", title: "船号" },
          { field: "SHIP_NAM", title: "船名" },
          { field: "TRADE_NAM", title: "内外贸" },
          { field: "INOUT_NAM", title: "进出口" },
          { field: "INOUT_ID", title: "装卸别" },
        ],
        //下拉菜单的路径
        shipDataurl: "/yard/tallyTj/shipOfZg",
        shipDataname: "shipNam",
        shipDatareturnApi: [
          { field: "INOUT_ID", returnName: "unloadId" },
          { field: "SHIP_NO", returnName: "shipNo" },
          { field: "SHIP_NAM", returnName: "shipNam" },
          { field: "LEV_PORT_TIM", returnName: "lbTime" },
          { field: "TO_BERTH_TIM", returnName: "kbTime" },
          { field: "TO_PORT_NAM", returnName: "ddPort" },
        ],
      },
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  created() {},
  mounted: function () {
    this.shipMessage = {
      ddPort: null,
      lbTime: null,
      kbTime: null,
      weightSum: null,
      jSum: null,
      shipNam: null,
    };
  },
  methods: {
    getDate() {
      if (
        this.formObj.shipNam == null ||
        this.formObj.shipNam == undefined ||
        this.formObj.shipNam == ""
      ) {
        this.$message.error("请选择一条船进行查询");
        return;
      }
      let loadingInstance = Loading.service({
        fullscreen: true,
        text: "正在统计………",
      });
      this.shipMessage.shipNam = this.formObj.shipNam;
      this.shipMessage.lbTime = this.formObj.lbTime;
      this.shipMessage.kbTime = this.formObj.kbTime;
      this.shipMessage.ddPort = this.formObj.ddPort;

      fetchList(Object.assign({}, this.formObj))
        .then((response) => {
          var dataMapAll = response.data.data;
          if (dataMapAll != null) {
            this.shipMessage.weightSum = dataMapAll.weightSum;
            this.shipMessage.jSum = dataMapAll.jianSum;
            if (dataMapAll.chList != null) {
              if (dataMapAll.chList.c1 != null) {
                this.shipData1 = dataMapAll.chList.c1;
              }
              if (dataMapAll.chList.c2 != null) {
                this.shipData2 = dataMapAll.chList.c2;
              }
              if (dataMapAll.chList.c3 != null) {
                this.shipData3 = dataMapAll.chList.c3;
              }
              if (dataMapAll.chList.c4 != null) {
                this.shipData4 = dataMapAll.chList.c4;
              }
              if (dataMapAll.chList.c5 != null) {
                this.shipData5 = dataMapAll.chList.c5;
              }
              if (dataMapAll.chList.c6 != null) {
                this.shipData6 = dataMapAll.chList.c6;
              }
              if (dataMapAll.chList.c7 != null) {
                this.shipData7 = dataMapAll.chList.c7;
              }
            }
          }
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
    resetForm() {},
  },
};
</script>
<style lang="scss" scoped>
.yard-tallytj-index {
  .el-tag {
    width: 100%;
  }
  .vxe-toolbar {
    height: 25px;
  }
  .shipTop {
    top: 190px;
    left: 1030px;
    position: relative;
    height: 250px;
    width: 250px;
    z-index: 1;
  }
  .shipBody {
    top: 100px;
    height: 400px;
    width: 100%;
  }
  .shipDiv {
    width: 1280px;
    height: 700px;
    background-color: #c0c0c0;
  }
  .text {
    font-size: 14px;
  }


  .box-card {
    position: relative;
    height: 210px;
    width: 160px;
  }
}
</style>>