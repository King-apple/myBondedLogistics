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
  <div class="jbSelectShip">
    <basic-container>
      <el-row>
        <el-col :span="11">
             <el-divider content-position="center">
                <strong>本工班船舶</strong>
              </el-divider>
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
            height="600px"
            ref="tableShip"
          >
          <vxe-table-column type="checkbox" ></vxe-table-column>
            <vxe-table-column
              field="shipNo"
              title="船号"
              width="50%"
            ></vxe-table-column>
            <vxe-table-column
              field="shipNam"
              title="船名"
              width="50%"
            ></vxe-table-column>
            </vxe-table-column>
          </vxe-table>
        </el-col>
        <el-col :span="2">
            <div  align="center" style="margin-top:260px">
            <el-row>
            <vxe-button type="text" icon="fa fa-arrow-right fa-2x" @click="add()"
              ></vxe-button>
            </el-row>
             <el-row>
            <vxe-button type="text" icon="fa fa-arrow-left fa-2x" @click="remove()"
              ></vxe-button>
            </el-row>
            </div>
        </el-col>
        <el-col :span="11">
            <el-divider content-position="center">
                <strong>要交班船舶</strong>
              </el-divider>
            <vxe-table
            border
            show-overflow
            keep-source
            stripe
            highlight-current-row
            highlight-hover-row
            size="mini"
            :data="tableDataBean"
            :round="true"
            align="center"
            height="600px"
            ref="tableShipBean"
          >
           <vxe-table-column type="checkbox" ></vxe-table-column>
            <vxe-table-column
              field="shipNo"
              title="船号"
              width="50%"
            ></vxe-table-column>
            <vxe-table-column
              field="shipNam"
              title="船名"
              width="50%"
            ></vxe-table-column>
            </vxe-table-column>
          </vxe-table>
        </el-col>
      </el-row>
       <el-row>
           <el-col>
                 <div align="center">
      <el-button
        type="primary"
        size="mini"
        icon="el-icon-check"
        @click="handleSubmit"
        >确定</el-button
      >
      <el-button size="mini" icon="el-icon-close" plain @click="closeWindows"
        >取消</el-button
      >
    </div>
           </el-col>
       </el-row>
    </basic-container>
  </div>
</template>

<script>
import { listShip,wjjbOfShip } from "@/api/sixspecial/vslworkorderhead";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";
import EditDown from "../../baseUtile/EditDown";
import Edit from "./pzjh";
import Add from "./add";
import moment from "moment";
export default {
  props: ["crtTime", "classId"],
  components: { EditDown, Edit, Add },
  name: "sendworkman",
  data() {
    return {
      formObj: {
        classId: "1",
        crtTime: null
      },
      tableData: [],
      tableDataBean: [],
      tableload: false
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() {
    this.formObj.classId = this.classId;
    this.formObj.crtTime = this.crtTime;
    this.getDate();
  },
  methods: {
    getDate() {
      if (this.formObj.classId == null) {
        this.$message.success("请选择班次");
        return;
      }
      this.tableload = true;
      listShip(Object.assign(this.formObj))
        .then(response => {
          this.tableData = response.data.data;
          this.tableload = false;
        })
        .catch(() => {
          this.tableload = false;
        });
    },
    remove() {
      let selectRecords = this.$refs.tableShipBean.getCheckboxRecords();
      let beanList = [];
      for (var n = 0; n < selectRecords.length; n++) {
        var ifPush = true;
        if (this.tableDataBean != null && this.tableDataBean.length > 0) {
          for (var m = 0; m < this.tableDataBean.length; m++) {
            if (selectRecords[n].shipNo == this.tableDataBean[m].shipNo) {
              ifPush = false;
            }
          }
        }
        if (ifPush) {
          beanList.push(selectRecords[n]);
        }
        this.tableData.push(selectRecords[n]);
      }
      this.tableDataBean = beanList;
    },
    add() {
      let selectRecords = this.$refs.tableShip.getCheckboxRecords();
      for (var n = 0; n < selectRecords.length; n++) {
        var ifPush = true;
        if (this.tableDataBean != null && this.tableDataBean.length > 0) {
          for (var m = 0; m < this.tableDataBean.length; m++) {
            if (selectRecords[n].shipNo == this.tableDataBean[m].shipNo) {
              ifPush = false;
            }
          }
        }
        if (ifPush) {
          this.$refs.tableShip.removeCheckboxRow();
          this.tableDataBean.push(selectRecords[n]);
        }
      }
    },
    handleSubmit() {
      this.$XModal.confirm("您确定要交班吗？").then(type => {
        if (type == "confirm") {
          wjjbOfShip(this.formObj,this.tableDataBean)
            .then(response => {
              this.$message.success(response.data.data);
               this.$emit("dictItemVisible");
            })
            .catch(() => {
              this.tableload = false;
            });
        } else {
          this.tableload = false;
        }
      });
    },
    closeWindows() {
      this.$emit("dictItemVisible");
    }
  }
};
</script>
<style lang="scss" scoped>
.jbSelectShip {
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