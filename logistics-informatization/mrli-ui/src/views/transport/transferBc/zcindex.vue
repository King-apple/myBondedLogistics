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
  <div class="transport-transferBc-zsindex">
    <basic-container>
      <!--查询区-->
      <vxe-form :data="formObj" size="mini">
        <vxe-form-item title="总计磅单数" field="bdNum" size="mini" span="4">
          <vxe-input v-model="formObj.bdNum" disabled></vxe-input>
        </vxe-form-item>
        <vxe-form-item title="转移到称别组" field="cbs" size="mini" span="10">
          <EditDown
            :objs="formObj"
            :property="downTableOption.cbsNam"
            :tableColumns="downTableOption.cbsColum"
            :urlapi="downTableOption.cbsurl"
            :returnApi="downTableOption.cbsreturnApi"
            sizenow="mini"
            pageSize="30"
            height="450"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item span="4" size="mini" align="left">
          <vxe-button status="primary" icon="fa fa-check" @click="commitAll()"
            >确定</vxe-button
          >
        </vxe-form-item>
      </vxe-form>
      <!--功能按钮-->
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
        ref="tableTransfer"
      >
        <vxe-table-column type="seq" width="50" title="序号"></vxe-table-column>
        <vxe-table-column
          field="cbs"
          title="称别组"
          width="70"
        ></vxe-table-column>
        <vxe-table-column
          field="lxdm"
          title="代码"
          width="50"
        ></vxe-table-column>
        <vxe-table-column
          field="bh"
          title="货票号"
          width="80"
        ></vxe-table-column>
        <vxe-table-column
          field="hm"
          title="货名"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="tdh"
          title="提单号"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="cbh"
          title="船编号"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="cm"
          title="船名"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="jhl"
          title="计划量"
          width="120"
        ></vxe-table-column>
        <vxe-table-column
          field="wcl"
          title="完成量"
          width="120"
        ></vxe-table-column>
        <vxe-table-column
          field="syl"
          title="剩余量"
          width="120"
        ></vxe-table-column>
        <vxe-table-column
          field="csl"
          title="捣运车"
          width="100"
        ></vxe-table-column>
        <vxe-table-column
          field="sqdw"
          title="申请单位"
          width="200"
        ></vxe-table-column>
        <vxe-table-column
          field="bpwc"
          title="完成否"
          :formatter="formatterBpwc"
          width="60"
        ></vxe-table-column>
        <vxe-table-column
          field="nwm"
          title="内外贸"
          width="60"
        ></vxe-table-column>
        <vxe-table-column
          field="zbrq"
          title="值班日期"
          width="140"
        ></vxe-table-column>
      </vxe-table>
    </basic-container>
  </div>
</template>

<script>
import { fetchList, transferAll } from "@/api/transport/transferbc";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import EditDown from "../../baseUtile/EditDown";
export default {
  props: ["dataList"],
  components: { EditDown },
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
      tableData: [],
      dateNow: {},
      tablePage: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 20, // 每页显示多少条
      },
      tableload: false,
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
    this.formObj.bdNum = this.dataList.length;
    this.tableData = this.dataList;
  },
  methods: {
    commitAll() {
      let loadingInstance = Loading.service({
        fullscreen: true,
        text: "正在保存………",
      });
      transferAll(this.tableData, this.formObj.cbs)
        .then((data) => {
          loadingInstance.close();
          this.$message.success("保存成功");
          this.$emit("dictItemVisible");
          this.$$emit("selectParent");
        })
        .catch(() => {
          loadingInstance.close();
        });
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
.transport-transferBc-zsindex {
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