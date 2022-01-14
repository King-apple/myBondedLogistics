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
  <div class="dayrecord">
    <basic-container>
      <vxe-form :data="formObj" size="mini">
        <vxe-form-item title="作业日期：" field="crtTime" span="4">
          <vxe-input
            v-model="formObj.crtTime"
            type="date"
            placeholder="请选择日期"
            clearable
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="班次：" field="classId" size="mini" span="4">
          <vxe-select v-model="formObj.classId" clearable size="mini">
            <vxe-option value="1" label="1班次0800-2000"></vxe-option>
            <vxe-option value="2" label="2班次2000-0800"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item title="船号" field="shipNo" span="4">
          <EditDown
            :objs="formObj"
            :property="downTableOption.shipNoname"
            :tableColumns="downTableOption.shipNoColum"
            :urlapi="downTableOption.shipNourl"
            :returnApi="downTableOption.shipNoreturnApi"
            sizenow="mini"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item span="12" size="mini" align="left">
          <vxe-button status="primary" icon="fa fa-search" @click="getDate()"
            >查询</vxe-button
          >
          <vxe-button status="primary" icon="fa fa-plus" @click="add('h')"
            >新增</vxe-button
          >
          <!-- <vxe-button status="primary" icon="fa fa-plus-circle" @click="addc()"
            >追加</vxe-button
          > -->
          <!--<vxe-button status="primary" icon="fa fa-retweet" @click="jb()"
            >交班</vxe-button
          >-->
          <vxe-button status="primary" icon="fa fa-retweet" @click="jbOfShip()"
            >船舶交班</vxe-button
          >
          <!-- <vxe-button status="primary" icon="fa fa-retweet" @click="edicommit()"
            >EDI上传</vxe-button
          > -->
        </vxe-form-item>
      </vxe-form>

      <el-row>
        <el-col :span="24">
          <el-divider content-position="left">作业通知头信息</el-divider>
        </el-col>
        <el-col :span="24">
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
            :round="true"
            align="center"
            height="250px"
            ref="tableNam"
            @cell-click="rowclick"
          >
            <vxe-table-column
              field="shipNo"
              title="船号"
              width="13%"
            ></vxe-table-column>
            <vxe-table-column
              field="shipNam"
              title="船名"
              width="13%"
            ></vxe-table-column>
            <vxe-table-column
              field="hatchNo"
              title="舱号"
              width="11%"
            ></vxe-table-column>
            <vxe-table-column
              field="tradeId"
              title="内外贸"
              width="11%"
              :formatter="formatternw"
            ></vxe-table-column>
            <vxe-table-column
              field="unloadId"
              title="装卸别"
              width="12%"
              :formatter="formatterzx"
            ></vxe-table-column>
            <vxe-table-column
              field="workType"
              title="混舱"
              width="11%"
              :formatter="formatterhc"
            ></vxe-table-column>
            <vxe-table-column
              field="updateNam"
              title="创建人"
              width="13%"
            ></vxe-table-column>
            <vxe-table-column field="updateTim" title="创建时间" width="16%">
            </vxe-table-column>
          </vxe-table>
        </el-col>
        <el-col :span="24">
          <el-divider content-position="left">作业通知体信息</el-divider>
        </el-col>
        <vxe-toolbar perfect size="mini">
          <template v-slot:buttons>
            <vxe-button type="text" icon="fa fa-plus" @click="add('b')"
              >新增</vxe-button
            >
            <vxe-button type="text" icon="fa fa-floppy-o" @click="saveAll()"
              >保存</vxe-button
            >
            <vxe-button type="text" icon="fa fa-trash" @click="del()"
              >删除</vxe-button
            >
            <template v-if="ifhc">
              <vxe-button type="text" icon="fa fa-exchange" @click="hc('02')"
                >混舱</vxe-button
              >
            </template>
            <template v-else>
              <vxe-button type="text" icon="fa fa-exchange" @click="hc('01')"
                >正常</vxe-button
              >
            </template>
          </template>
        </vxe-toolbar>
        <el-col :span="24">
          <vxe-table
            border
            show-overflow
            keep-source
            stripe
            highlight-current-row
            highlight-hover-row
            size="mini"
            :data="tableDatab"
            :loading="tableloadb"
            :round="true"
            align="center"
            height="300px"
            ref="tableNamb"
            :edit-config="{ trigger: 'click', mode: 'cell', showStatus: true }"
            @cell-click="rowclickb"
          >
            <vxe-table-column
              field="subunitNam"
              title="保管单位"
              width="8%"
              show-overflow
            ></vxe-table-column>
            <vxe-table-column
              field="workPlaceNam"
              title="作业地点"
              width="8%"
              show-overflow
            ></vxe-table-column>
            <vxe-table-column
              field="billNo"
              title="提单号"
              width="12%"
            ></vxe-table-column>
            <vxe-table-column
              field="hatchNo"
              title="舱号"
              width="4%"
            ></vxe-table-column>
            <vxe-table-column
              field="cargoNam"
              title="货名"
              width="7%"
              show-overflow
            ></vxe-table-column>
            <vxe-table-column
              field="piecesNum"
              title="件数"
              width="5%"
              :edit-render="{ name: '$input', props: { type: 'int' } }"
            ></vxe-table-column>
            <vxe-table-column
              field="weightWgt"
              title="重量"
              width="7%"
              :edit-render="{
                name: '$input',
                props: { type: 'float', digits: 3 }
              }"
            ></vxe-table-column>
            <vxe-table-column
              field="cargoVol"
              title="体积"
              width="7%"
              :edit-render="{
                name: '$input',
                props: { type: 'float', digits: 3 }
              }"
            ></vxe-table-column>
            <vxe-table-column
              field="endPiecesNum"
              title="完成件数"
              width="7%"
            ></vxe-table-column>
            <vxe-table-column
              field="endWeightWgt"
              title="完成重量"
              width="7%"
            ></vxe-table-column>
            <vxe-table-column
              field="endCargoVol"
              title="完成体积"
              width="7%"
            ></vxe-table-column>
            <vxe-table-column
              field="checkId"
              title="过磅否"
              width="7%"
              :formatter="formattergb"
            ></vxe-table-column>
            <vxe-table-column
              field="checkNo"
              title="衡重申请单号"
              width="7%"
            ></vxe-table-column>
            <vxe-table-column
              field="status"
              title="状态"
              width="7%"
              :formatter="formatterzt"
            ></vxe-table-column>
          </vxe-table>
        </el-col>
      </el-row>
      <vxe-modal
        v-if="dialogFormVisibleedit"
        v-model="dialogFormVisibleedit"
        title="新增"
        width="1200"
        height="700"
        resize
        storage
      >
        <Edit :datanow="dataNow" @dictItemVisible="dictItemVisible"></Edit>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisibleadd"
        v-model="dialogFormVisibleadd"
        title="新增"
        width="1200"
        height="700"
        resize
        storage
        fullscreen
      >
        <Add :datanow="dataNow" @dictItemVisible="dictItemVisible"></Add>
      </vxe-modal>
      <vxe-modal
        v-if="dialogFormVisiblejb"
        v-model="dialogFormVisiblejb"
        title="交班"
        width="1200"
        height="800"
        resize
        storage
      >
        <JbSelectShip
          :crtTime="formObj.crtTime"
          :classId="formObj.classId"
          @dictItemVisible="dictItemVisible"
        >
        </JbSelectShip>
      </vxe-modal>
    </basic-container>
  </div>
</template>

<script>
import { ListH, wjjb, hc, commitedi } from "@/api/sixspecial/vslworkorderhead";
import { ListB, saveb, deletb } from "@/api/sixspecial/vslworkorderbody";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import EditDown from "../../baseUtile/EditDown";
import Edit from "./pzjh";
import JbSelectShip from "./jbSelectShip";
import Add from "./add";
import moment from "moment";
export default {
  components: { EditDown, Edit, Add, JbSelectShip },
  name: "sendworkman",
  data() {
    return {
      formObj: {
        shipNo: null,
        crtTime: moment(new Date()).format("YYYY-MM-DD"),
        classId: "1"
      },
      downTableOption: {
        //计划下拉菜单展示项
        shipNoColum: [
          { field: "shipNo", title: "船号", width: "50%" },
          { field: "shipNam", title: "船名", width: "50%" }
        ],
        //下拉菜单的路径
        shipNourl: "/sixspecial/vslworkorderhead/getJhList",
        shipNoname: "shipNo",
        shipNoreturnApi: [{ field: "shipNo", returnName: "shipNo" }]
      },
      dialogFormVisibleedit: false,
      dialogFormVisibleadd: false,
      dialogFormVisiblejb: false,
      tableData: [],
      tableload: false,
      tableDatab: [],
      tableloadb: false,
      dataHeadNow: {},
      dataBodyNow: {},
      dataNow: {},
      ifhc: true
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
      if (this.formObj.classId == null) {
        this.$message.success("请选择班次");
        return;
      }
      this.tableload = true;
      ListH(Object.assign(this.formObj))
        .then(response => {
          this.tableData = response.data.data;
          if (response.data.data.length > 0) {
            this.dataHeadNow = response.data.data[0];
            this.$refs.tableNam.setCurrentRow(this.tableData[0]);
            this.getBDate();
          } else {
            this.tableDatab = [];
          }
          this.tableload = false;
        })
        .catch(() => {
          this.tableload = false;
        });
    },
    jb() {
      this.tableload = true;
      this.$XModal.confirm("您确定要交班吗？").then(type => {
        if (type == "confirm") {
          wjjb(Object.assign(this.formObj))
            .then(response => {
              this.$message.success(response.data.data);
              this.tableload = false;
              this.getDate();
            })
            .catch(() => {
              this.tableload = false;
            });
        } else {
          this.tableload = false;
        }
      });
    },
    getBDate() {
      this.tableloadb = true;
      ListB(Object.assign({ pkid: this.dataHeadNow.pkid }))
        .then(response => {
          this.tableDatab = response.data.data;
          this.tableloadb = false;
        })
        .catch(() => {
          this.tableloadb = false;
        });
    },
    formatternw({ cellValue }) {
      if (cellValue == "1") {
        return "内贸";
      } else if (cellValue == "0") {
        return "外贸";
      }
    },
    formatterzx({ cellValue }) {
      if (cellValue == "-") {
        return "卸";
      } else if (cellValue == "+") {
        return "装";
      }
    },
    formatterhc({ cellValue }) {
      if (cellValue == "02") {
        return "混舱";
      } else {
        return "正常";
      }
    },
    formattergb({ cellValue }) {
      if (cellValue == "0") {
        return "否";
      } else if (cellValue == "1") {
        return "是";
      }
    },
    formatterzt({ cellValue }) {
      if (cellValue == "0") {
        return "作废";
      } else if (cellValue == "1") {
        return "作业中";
      } else if (cellValue == "3") {
        return "完工";
      }
    },
    jbOfShip() {
      if (
        this.formObj.crtTime == null ||
        this.formObj.classId == null ||
        this.formObj.classId == ""
      ) {
        this.$message.error("请选择工班日期");
        return;
      }
      this.dialogFormVisiblejb = true;
    },
    hc(pd) {
      if (this.dataHeadNow == null || this.dataHeadNow.pkid == null) {
        this.$message.error("请选择一条数据");
        return;
      }
      this.tableloadb = true;
      this.$XModal
        .confirm(
          "确定对船名" +
            this.dataHeadNow.shipNam +
            "舱号" +
            this.dataHeadNow.hatchNo +
            "操作吗？"
        )
        .then(type => {
          if (type == "confirm") {
            hc(Object.assign({ pkid: this.dataHeadNow.pkid, workType: pd }))
              .then(response => {
                this.$message.success(response.data.data);
                this.tableloadb = false;
                this.getDate();
                if (this.ifhc) {
                  this.ifhc = false;
                } else {
                  this.ifhc = true;
                }
              })
              .catch(() => {
                this.tableloadb = false;
              });
          } else {
            this.tableloadb = false;
          }
        });
    },
    rowclick({ row }) {
      this.dataHeadNow = row;
      this.getBDate();
    },
    rowclickb({ row }) {
      this.dataBodyNow = row;
    },
    async saveAll() {
      const errMap = await this.$refs.tableNamb
        .fullValidate()
        .catch(errMap => errMap);
      if (errMap) {
      } else {
        this.tableloadb = true;
        var updaterecords = this.$refs.tableNamb.getRecordset().updateRecords;
        if (updaterecords.length == 0) {
          this.$message.error("没有修改的数据");
          this.tableloadb = false;
          return;
        }
        saveb(updaterecords)
          .then(data => {
            this.$message.success("保存成功");
            this.tableloadb = false;
            this.getBDate();
          })
          .catch(() => {
            this.tableloadb = false;
          });
      }
    },
    del() {
      if (this.dataBodyNow == null || this.dataBodyNow.pkid == null) {
        this.$message.error("请选择一条数据进行删除");
        return;
      }
      this.tableloadb = true;
      this.$XModal
        .confirm("您确定要删除" + this.dataBodyNow.billNo + "提单数据吗？")
        .then(type => {
          if (type == "confirm") {
            deletb(this.dataBodyNow.pkid)
              .then(response => {
                if (response.data.data == "h") {
                  this.$message.success("删除成功");
                  this.tableloadb = false;
                  this.getDate();
                } else {
                  this.$message.success(response.data.data);
                  this.tableloadb = false;
                  this.getBDate();
                }
              })
              .catch(() => {
                this.tableloadb = false;
              });
          } else {
            this.tableloadb = false;
          }
        });
    },
    add(flag) {
      if (flag == "h") {
        this.dataNow = this.formObj;
      } else if (flag == "b") {
        this.dataNow = this.dataHeadNow;
      }
      this.dialogFormVisibleedit = true;
    },
    addc() {
      this.dataNow = this.formObj;
      this.dialogFormVisibleadd = true;
    },
    dictItemVisible() {
      this.dialogFormVisibleedit = false;
      this.dialogFormVisibleadd = false;
      this.dialogFormVisiblejb = false;
      this.getDate();
    },
    edicommit() {
      commitedi()
        .then(response => {})
        .catch(() => {
          this.tableloadb = false;
        });
    }
  }
};
</script>
<style lang="scss" scoped>
.dayrecord {
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