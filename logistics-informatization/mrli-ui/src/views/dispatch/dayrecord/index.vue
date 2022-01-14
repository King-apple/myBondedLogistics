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
        <vxe-form-item title="作业日期：" field="crtTime" span="6">
          <vxe-input
            v-model="formObj.crtTime"
            type="date"
            placeholder="请选择日期"
            clearable
          ></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="班次：" field="classId" size="mini" span="6">
          <vxe-select v-model="formObj.classId" clearable size="mini">
            <vxe-option value="1" label="1班次0800-2000"></vxe-option>
            <vxe-option value="2" label="2班次2000-0800"></vxe-option>
          </vxe-select>
        </vxe-form-item>
        <vxe-form-item title="队别：" field="subunitNam" size="mini" span="6">
          <EditDown
            :objs="formObj"
            :property="downTableOption.subNamname"
            :tableColumns="downTableOption.subNamColum"
            :urlapi="downTableOption.subNamurl"
            :returnApi="downTableOption.subNamreturnApi"
            sizenow="mini"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item span="6" size="mini" align="center">
          <vxe-button status="primary" icon="fa fa-search" @click="getDate()"
            >查询</vxe-button
          >
        </vxe-form-item>
      </vxe-form>

      <el-row>
        <el-col :span="8">
          <vxe-table
            border
            show-overflow
            keep-source
            highlight-current-row
            size="mini"
            :data="tableDataClassNam"
            :loading="tableloadClassNam"
            :round="true"
            align="center"
            height="550px"
            ref="tableClassNam"
            @cell-click="rowclick"
          >
            <vxe-table-column
              field="classCod"
              title="班组代码"
              width="20%"
            ></vxe-table-column>
            <vxe-table-column
              field="classNam"
              title="班组名称"
              width="55%"
            ></vxe-table-column>
            <vxe-table-column title="操作" width="25%" show-overflow>
              <template v-slot="{ row }">
                <vxe-button
                  type="text"
                  icon="fa fa-bolt"
                  @click="editEvent(row)"
                  >刷新班组</vxe-button
                >
              </template>
            </vxe-table-column>
          </vxe-table>
        </el-col>
        <el-col :span="1"> </el-col>
        <el-col :span="8">
          <vxe-table
            border
            show-overflow
            keep-source
            highlight-current-row
            size="mini"
            :data="tableDataDayRecord"
            :loading="tableloadDayRecord"
            :round="true"
            align="center"
            height="550px"
            ref="tableDayRecord"
            @cell-click="dayrowclick"
          >
            <vxe-table-column
              field="workerCod"
              title="姓名代码"
              width="30%"
            ></vxe-table-column>
            <vxe-table-column
              field="manNam"
              title="姓名"
              width="40%"
            ></vxe-table-column>
            <vxe-table-column
              field="status"
              title="出勤状态"
              width="30%"
            ></vxe-table-column>
          </vxe-table>
        </el-col>
        <el-col :span="1"> </el-col>
        <el-col :span="2">
          <vxe-form size="mini">
            <vxe-form-item size="mini" align="center">
              <vxe-button icon="fa fa fa-floppy-o" @click="saveAll()"
                >保　存　</vxe-button
              >
            </vxe-form-item>
            <vxe-form-item size="mini" align="center">
              <vxe-button @click="updateZt('+')">加　班（+）</vxe-button>
            </vxe-form-item>
            <vxe-form-item size="mini" align="center">
              <vxe-button @click="updateZt('-')">不加班（-）</vxe-button>
            </vxe-form-item>
            <vxe-form-item size="mini" align="center">
              <vxe-button @click="updateZt('/')">出　勤（/）</vxe-button>
            </vxe-form-item>
            <vxe-form-item size="mini" align="center">
              <vxe-button @click="updateZt('#')">迟　到（#）</vxe-button>
            </vxe-form-item>
            <vxe-form-item size="mini" align="center">
              <vxe-button @click="updateZt('*')">缺　勤（*）</vxe-button>
            </vxe-form-item>
            <vxe-form-item size="mini" align="center">
              <vxe-button @click="updateZt('P')">病　假（P）</vxe-button>
            </vxe-form-item>
            <vxe-form-item size="mini" align="center">
              <vxe-button @click="updateZt('C')">事　假（C）</vxe-button>
            </vxe-form-item>
            <vxe-form-item size="mini" align="center">
              <vxe-button @click="updateZt('A')">工　伤（A）</vxe-button>
            </vxe-form-item>
            <vxe-form-item size="mini" align="center">
              <vxe-button @click="updateZt('S')">丧　假（S）</vxe-button>
            </vxe-form-item>
            <vxe-form-item size="mini" align="center">
              <vxe-button @click="updateZt('N')">农　忙（N）</vxe-button>
            </vxe-form-item>
            <vxe-form-item size="mini" align="center">
              <vxe-button @click="updateZt('H')">婚　假（H）</vxe-button>
            </vxe-form-item>
          </vxe-form>
        </el-col>
      </el-row>
    </basic-container>
  </div>
</template>

<script>
import {
  classList,
  fetchList,
  save,
  sjcl,
  sjpd,
} from "@/api/dispatch/dayrecord";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import EditDown from "../../baseUtile/EditDown";
import moment from "moment";
export default {
  components: { EditDown },
  name: "dayrecord",
  data() {
    return {
      formObj: {
        crtTime: moment(new Date()).format("YYYY-MM-DD"),
        classId: null,
        subunitCod: null,
        subunitNam: null,
      },
      dialogFormVisibleedit: false,
      tableDataDayRecord: [],
      tableloadDayRecord: false,
      tableDataClassNam: [],
      tableloadClassNam: false,
      dataHeadNow: {},
      dataNow: {},
      downTableOption: {
        //队别下拉菜单展示项
        subNamColum: [
          { field: "SUBUNIT_COD", title: "代码" },
          { field: "SUBUNIT_NAM", title: "名称" },
        ],
        //下拉菜单的路径
        subNamurl: "/basecode/basecod/getSubNam",
        subNamname: "subunitNam",
        subNamreturnApi: [
          { field: "SUBUNIT_COD", returnName: "subunitCod" },
          { field: "SUBUNIT_NAM", returnName: "subunitNam" },
        ],
      },
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  created() {},
  methods: {
    getDate() {
      if (this.formObj.classId == null) {
        this.$message.success("请选择班次");
        return;
      }
      if (this.formObj.subunitCod == null) {
        this.$message.success("请选择队别");
        return;
      }
      this.tableloadClassNam = true;
      classList(Object.assign(this.formObj))
        .then((response) => {
          this.tableDataClassNam = response.data.data;
          this.tableloadClassNam = false;
        })
        .catch(() => {
          this.tableloadClassNam = false;
        });
    },

    getDay() {
      this.tableloadDayRecord = true;
      fetchList(
        Object.assign({
          classId: this.formObj.classId,
          classCod: this.dataHeadNow.classCod,
          subunitCod: this.formObj.subunitCod,
          crtTime:this.formObj.crtTime,
        })
      )
        .then((response) => {
          this.tableDataDayRecord = response.data.data;
          this.tableloadDayRecord = false;
        })
        .catch(() => {
          this.tableloadDayRecord = false;
        });
    },

    rowclick({ row }) {
      this.dataHeadNow = row;
      this.getDay();
    },

    dayrowclick({ row }) {
      this.dataNow = row;
    },

    updateZt(zt) {
      this.dataNow.status = zt;
    },

    async saveAll() {
      const errMap = await this.$refs.tableDayRecord
        .fullValidate()
        .catch((errMap) => errMap);
      if (errMap) {
      } else {
        this.tableloadDayRecord = true;
        save(this.$refs.tableDayRecord.getRecordset())
          .then((data) => {
            this.$message.success(data.data.data);
            this.tableloadDayRecord = false;
            this.getDay();
          })
          .catch(() => {
            this.tableloadDayRecord = false;
          });
      }
    },
    editEvent(row) {
      this.dataHeadNow = row;
      let that = this;
      sjpd(
        Object.assign({
          crtTime: this.formObj.crtTime,
          classId: this.formObj.classId,
          classCod: this.dataHeadNow.classCod,
          subunitCod: this.formObj.subunitCod,
        })
      )
        .then((data) => {
          if (data.data.data == "1") {
            this.$confirm("该日数据已经产生，是否确认重新处理", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning",
            })
              .then(
                function () {
                  return sjcl(
                    Object.assign({
                      crtTime: that.formObj.crtTime,
                      classId: that.formObj.classId,
                      classCod: that.dataHeadNow.classCod,
                      classNam: that.dataHeadNow.classNam,
                      subunitCod: that.formObj.subunitCod,
                      subunitNam: that.formObj.subunitNam,
                    })
                  );
                },
                function () {}
              )
              .then((data) => {
                this.$message.success(data.data.data);
                this.getDay();
              });
          } else {
            sjcl(
              Object.assign({
                crtTime: that.formObj.crtTime,
                classId: that.formObj.classId,
                classCod: that.dataHeadNow.classCod,
                classNam: that.dataHeadNow.classNam,
                subunitCod: that.formObj.subunitCod,
                subunitNam: that.formObj.subunitNam,
              })
            ).then((data) => {
              this.$message.success(data.data.data);
              this.getDay();
            });
          }
        })
        .catch(() => {});
    },
  },
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