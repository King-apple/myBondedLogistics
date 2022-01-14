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
  <div class="execution">
    <basic-container>
      <el-form :inline="true" :model="searchForm" class="demo-form-inline">
        <el-form-item label="日期">
          <el-date-picker
            v-model="searchForm.workDte"
            type="date"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
            placeholder="选择日期"
            @change="timchange($event)"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="范围：">
          <el-date-picker
            v-model="searchForm.workTimrange"
            type="datetimerange"
            :readonly="dataReadonly"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            >></el-date-picker
          >
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getList">查询</el-button>
          <el-button type="primary" @click="dosjcl">数据汇总</el-button>
          <el-button type="primary" @click="createxml">生成xml</el-button>
        </el-form-item>
      </el-form>
      <el-tabs v-model="activeName" type="card">
        <el-tab-pane label="调表1、2、4、5、15" name="first">
          <el-divider content-position="left">调表1 吞吐量</el-divider>
          <avue-crud
            ref="crud"
            :data="tableData"
            :permission="permissionList"
            :table-loading="tableLoading"
            :option="tableOption"
          >
          </avue-crud>
          <el-divider content-position="left">调表3 火车作业情况</el-divider>
          <avue-crud
            ref="crud"
            :data="tableData3"
            :permission="permissionList"
            :table-loading="tableLoading"
            :option="tableOption3"
          >
          </avue-crud>
          <el-divider content-position="left">调表4 港存物资(一)</el-divider>
          <avue-crud
            ref="crud"
            :data="tableData4"
            :permission="permissionList"
            :table-loading="tableLoading"
            :option="tableOption4"
          >
          </avue-crud>
          <el-divider content-position="left">调表5 港存物资(二)</el-divider>
          <avue-crud
            ref="crud"
            :data="tableData5"
            :permission="permissionList"
            :table-loading="tableLoading"
            :option="tableOption5"
          >
          </avue-crud>
          <el-divider content-position="left"
            >调表15 集装箱吞吐量日报表</el-divider
          >
          <avue-crud
            ref="crud"
            :data="tableData15"
            :permission="permissionList"
            :table-loading="tableLoading"
            :option="tableOption15"
          >
          </avue-crud>
        </el-tab-pane>
        <el-tab-pane label="调表2、6" name="second">
          <el-divider content-position="left"
            >调表2 船舶在港及外贸船舶在港动态分析</el-divider
          >
          <avue-crud
            ref="crud"
            :data="tableData2"
            :permission="permissionList"
            :table-loading="tableLoading"
            :option="tableOption2"
          >
          </avue-crud>
          <el-divider content-position="left">调表6 船舶在港动态</el-divider>
          <avue-crud
            ref="crud"
            :data="tableData6"
            :permission="permissionList"
            :table-loading="tableLoading"
            :option="tableOption6"
          >
          </avue-crud>
        </el-tab-pane>
        <el-tab-pane label="调表8" name="third">
          <el-divider content-position="left">调表8 重点物资</el-divider>
          <avue-form :option="option7" v-model="data7"></avue-form>
        </el-tab-pane>
      </el-tabs>
    </basic-container>
  </div>
</template>

<script>
import { fetchList, sjpd, sjcl, cxml } from "@/api/caccentre/mdisptab11";
import { fetchList2 } from "@/api/caccentre/mdisptab21";
import { fetchList3 } from "@/api/caccentre/mdisptab31";
import { fetchList4 } from "@/api/caccentre/mdisptab41";
import { fetchList5 } from "@/api/caccentre/mdisptab51";
import { fetchList6 } from "@/api/caccentre/mdisptab61";
import { fetchList7 } from "@/api/caccentre/mdisptab7mt";
import { fetchList15 } from "@/api/caccentre/mdisptabx151";
import { tableOption } from "@/const/crud/caccentre/mdisptab11";
import { tableOption2 } from "@/const/crud/caccentre/mdisptab21";
import { tableOption3 } from "@/const/crud/caccentre/mdisptab31";
import { tableOption4 } from "@/const/crud/caccentre/mdisptab41";
import { tableOption5 } from "@/const/crud/caccentre/mdisptab51";
import { option7 } from "@/const/crud/caccentre/mdisptab7mt";
import { tableOption6 } from "@/const/crud/caccentre/mdisptab61";
import { tableOption15 } from "@/const/crud/caccentre/mdisptabx151";
import { mapGetters } from "vuex";
import moment from "moment";
export default {
  name: "mdisptab11",
  data() {
    return {
      activeName: "first",
      dataReadonly: true,
      searchForm: {
        workDte: moment(new Date()).format("YYYY-MM-DD"),
        workTimrange: [
          moment(new Date()).add("day", -1).format("YYYY-MM-DD") + " 08:00",
          moment(new Date()).format("YYYY-MM-DD") + " 08:00",
        ],
      },
      tableData: [],
      tableData2: [],
      tableData3: [],
      tableData4: [],
      tableData5: [],
      tableData6: [],
      data7: {},
      tableData15: [],
      tableLoading: false,
      tableOption: tableOption,
      tableOption2: tableOption2,
      tableOption3: tableOption3,
      tableOption4: tableOption4,
      tableOption5: tableOption5,
      tableOption6: tableOption6,
      tableOption15: tableOption15,
      option7: option7,
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 30, // 每页显示多少条
      },
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
    permissionList() {
      return {
        addBtn: this.vaildData(
          this.permissions.caccentre_mdisptab11_add,
          false
        ),
        delBtn: this.vaildData(
          this.permissions.caccentre_mdisptab11_del,
          false
        ),
        editBtn: this.vaildData(
          this.permissions.caccentre_mdisptab11_edit,
          false
        ),
      };
    },
  },
  methods: {
    getList(page, params) {
      this.tableLoading = true;
      fetchList(
        Object.assign(
          {
            current: page.currentPage,
            size: page.pageSize,
          },
          params,
          this.searchForm
        )
      )
        .then((response) => {
          this.tableData = response.data.data.records;
          this.page.total = response.data.data.total;
          this.tableLoading = false;
        })
        .catch(() => {
          this.tableLoading = false;
        });

      fetchList2(
        Object.assign(
          {
            current: page.currentPage,
            size: page.pageSize,
          },
          params,
          this.searchForm
        )
      )
        .then((response) => {
          this.tableData2 = response.data.data.records;
          this.page.total = response.data.data.total;
          this.tableLoading = false;
        })
        .catch(() => {
          this.tableLoading = false;
        });

      fetchList3(
        Object.assign(
          {
            current: page.currentPage,
            size: page.pageSize,
          },
          params,
          this.searchForm
        )
      )
        .then((response) => {
          this.tableData3 = response.data.data.records;
          this.page.total = response.data.data.total;
          this.tableLoading = false;
        })
        .catch(() => {
          this.tableLoading = false;
        });

      fetchList4(
        Object.assign(
          {
            current: page.currentPage,
            size: page.pageSize,
          },
          params,
          this.searchForm
        )
      )
        .then((response) => {
          this.tableData4 = response.data.data.records;
          this.page.total = response.data.data.total;
          this.tableLoading = false;
        })
        .catch(() => {
          this.tableLoading = false;
        });

      fetchList5(
        Object.assign(
          {
            current: page.currentPage,
            size: page.pageSize,
          },
          params,
          this.searchForm
        )
      )
        .then((response) => {
          this.tableData5 = response.data.data.records;
          this.page.total = response.data.data.total;
          this.tableLoading = false;
        })
        .catch(() => {
          this.tableLoading = false;
        });

      fetchList6(
        Object.assign(
          {
            current: page.currentPage,
            size: page.pageSize,
          },
          params,
          this.searchForm
        )
      )
        .then((response) => {
          this.tableData6 = response.data.data.records;
          this.page.total = response.data.data.total;
          this.tableLoading = false;
        })
        .catch(() => {
          this.tableLoading = false;
        });

      fetchList7(
        Object.assign(
          {
            current: page.currentPage,
            size: page.pageSize,
          },
          params,
          this.searchForm
        )
      )
        .then((response) => {
          if (response.data.data.total == 0) {
            this.data7 = {};
          } else {
            this.data7 = response.data.data.records[0];
          }
          this.page.total = response.data.data.total;
          this.tableLoading = false;
        })
        .catch(() => {
          this.tableLoading = false;
        });

      fetchList15(
        Object.assign(
          {
            current: page.currentPage,
            size: page.pageSize,
          },
          params,
          this.searchForm
        )
      )
        .then((response) => {
          this.tableData15 = response.data.data.records;
          this.page.total = response.data.data.total;
          this.tableLoading = false;
        })
        .catch(() => {
          this.tableLoading = false;
        });
    },

    timchange: function (e) {
      this.searchForm.workTimrange = [
        moment(e).add("day", -1).format("YYYY-MM-DD") + " 08:00",
        moment(e).format("YYYY-MM-DD") + " 08:00",
      ];
    },

    dosjcl() {
      let that = this;
      this.tableLoading = true;
      sjpd(this.searchForm.workDte)
        .then((data) => {
          if (data.data.data == "1") {
            this.$confirm("该天数据已经产生，是否确定重新处理", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning",
            })
              .then(
                function () {
                  return sjcl(that.searchForm);
                },
                function () {
                  that.tableLoading = false;
                }
              )
              .then((data) => {
                this.$message.success("处理成功");
                this.getList(this.page);
                this.tableLoading = false;
              });
          } else {
            sjcl(this.searchForm).then((data) => {
              this.$message.success("处理成功");
              this.getList(this.page);
              this.tableLoading = false;
            });
          }
        })
        .catch(() => {
          this.tableLoading = false;
        });
    },

    createxml() {
      let that = this;
      this.tableLoading = true;
      cxml(this.searchForm.workDte)
        .then((data) => {
          this.tableLoading = false;
          this.$message.success("生成成功");
        })

        .catch(() => {
          this.tableLoading = false;
        });
    },
  },
};
</script>