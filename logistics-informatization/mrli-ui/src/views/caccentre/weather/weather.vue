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
  <div class="caccentre-weather-index">
    <basic-container>
      <div class="imagesBody">
        <el-row>
          
          <el-col :span="6" :offset="18">
            <div style="background-color: rgba(0, 102, 204, 0.619607843137255)">
              <div align="center">
                <span><font color="white"  size="5">气象预警</font></span>
              </div>
              <span><font color="white">{{content}}</font></span>
            </div>
          </el-col>
        </el-row>
        <el-row style="top:400px">
          <el-col :span="6" :offset="16">
            <div class="user_skills">
              <el-table
                size="mini"
                :data="datafuther"
                align="center"
                height="260px"
                :cell-style="cellStyle"
                border
                fit
                highlight-current-row
              >
                <el-table-column label="未来天气情况" align="center">
                  <el-table-column
                    prop="ydate"
                    label="时间"
                    width="100"
                    :formatter="dateFormat"
                  ></el-table-column>
                  <el-table-column
                    prop="maxTemp"
                    label="最高温度"
                    width="100"
                  ></el-table-column>
                  <el-table-column
                    prop="minTemp"
                    label="最低温度"
                    width="100"
                  ></el-table-column>
                  <el-table-column
                    prop="weatherComment"
                    label="天气"
                    width="100"
                  ></el-table-column>
                  <el-table-column
                    prop="wind"
                    label="风况"
                    width="100"
                  ></el-table-column>
                </el-table-column>
              </el-table>
            </div>
          </el-col>
        </el-row>
      </div>
    </basic-container>
  </div>
</template>

<script>
import { futherList,selectWarning } from "@/api/caccentre/weather";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import moment from "moment";
import VXETable from "vxe-table";
export default {
  components: {},
  name: "caccentre-weather-index",
  data() {
    return {
      datafuther: [],
      tableload: false,
      content:"",
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  mounted() {},
  created() {
    this.getDate();
    this.getWarning();
  },
  methods: {
    getDate() {
      this.tableload = true;
      futherList()
        .then((response) => {
          this.datafuther = response.data.data;
          this.tableload = false;
        })
        .catch(() => {
          this.tableload = false;
        });
    },
    getWarning() {
      selectWarning()
        .then((response) => {
          this.content = response.data.data.warningContent;
        })
        .catch(() => {
      
        });
    },
    dateFormat: function (row, column) {
      var date = row[column.property];
      if (date == undefined) {
        return "";
      }
      return moment(date).format("YYYY-MM-DD");
    },
    cellStyle(row, column, rowIndex, columnIndex) {
      return "color:yellow";
    },
  },
};
</script>
<style lang="scss" scoped>
.caccentre-weather-index {
  .el-tag {
    width: 100%;
  }
  .el-divider--horizontal {
    margin: 10px 0;
  }
  .vxe-toolbar {
    height: 25px;
  }
  .imagesBody {
    height: 800px;
    width: 100%;
    background: url(tq.jpg);
    background-repeat: no-repeat; //不重复
    background-size: 100% 100%; // 满屏
  }
  .user_skills {
    width: 510px;
    height: 300px;
    margin: left;
  }

  .user_skills /deep/ .el-table,
  .el-table__expanded-cell {
    background-color: RGBA(0, 0, 0, 0.3);
  }

  .user_skills /deep/ .el-table tr {
    background-color: RGBA(0, 0, 0, 0.3) !important;
  }
  .user_skills /deep/ .el-table--enable-row-transition .el-table__body td,
  .el-table .cell {
    background-color: RGBA(0, 0, 0, 0.3);
  }
  .el-table::before {
    //去除底部白线
    left: 0;
    bottom: 0;
    width: 100%;
    height: 0px;
  }
}
</style>