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
      <vxe-form :data="formObj" size="mini" title-width="60">
        <vxe-form-item title="编号" field="bh" size="mini" span="8">
          <EditDown
            :objs="formObj"
            :property="downTableOption.bh"
            :tableColumns="downTableOption.bhColum"
            :urlapi="downTableOption.bhurl"
            :returnApi="downTableOption.bhreturnApi"
            sizenow="mini"
            height="450"
          ></EditDown>
        </vxe-form-item>
        <vxe-form-item span="8" field="cbz" title="称别组" size="mini">
          <vxe-input v-model="formObj.cbz" disabled></vxe-input>
        </vxe-form-item>
        <vxe-form-item span="8" size="mini" align="center">
          <vxe-button status="primary" icon="fa fa-check" @click="commitAll()"
            >置回</vxe-button
          >
        </vxe-form-item>
        <vxe-form-item span="8" field="hm" title="货名" size="mini">
          <vxe-input v-model="formObj.hm" disabled></vxe-input>
        </vxe-form-item>
        <vxe-form-item span="8" field="hl" title="货类" size="mini">
          <vxe-input v-model="formObj.hl" disabled></vxe-input>
        </vxe-form-item>
        <vxe-form-item span="8" field="cm" title="船名" size="mini">
          <vxe-input v-model="formObj.cm" disabled></vxe-input>
        </vxe-form-item>
        <vxe-form-item span="24" field="sqdw" title="申请单位" size="mini">
          <vxe-input v-model="formObj.sqdw" disabled></vxe-input>
        </vxe-form-item>
        <vxe-form-item span="8" field="jhl" title="计划量" size="mini">
          <vxe-input v-model="formObj.jhl" disabled></vxe-input>
        </vxe-form-item>
        <vxe-form-item span="8" field="tdh" title="提单号" size="mini">
          <vxe-input v-model="formObj.tdh" disabled></vxe-input>
        </vxe-form-item>
        <vxe-form-item span="8" field="jck" title="进出口" size="mini">
          <vxe-input v-model="formObj.jck" disabled></vxe-input>
        </vxe-form-item>
        <vxe-form-item span="8" field="nwm" title="内外贸" size="mini">
          <vxe-input v-model="formObj.nwm" disabled></vxe-input>
        </vxe-form-item>
        <vxe-form-item span="8" field="ckm" title="仓库" size="mini">
          <vxe-input v-model="formObj.ckm" disabled></vxe-input>
        </vxe-form-item>
      </vxe-form>
    </basic-container>
  </div>
</template>

<script>
import { fetchList, backOne } from "@/api/transport/transferbc";
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import EditDown from "../../baseUtile/EditDown";
export default {
  components: { EditDown },
  name: "transport-base-index",
  data() {
    return {
      formObj: {
        bh: null,
        cm: null,
        cbs: null,
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
        //编号
        bh: "bh",
        bhColum: [
          { field: "bh", title: "编号" },
          { field: "cm", title: "船名" },
          { field: "cbs", title: "称别组" },
        ],
        bhreturnApi: [
          { field: "bh", returnName: "bh" },
          { field: "cm", returnName: "cm" },
          { field: "cbs", returnName: "cbs" },
          { field: "sqdw", returnName: "sqdw" },
          { field: "hm", returnName: "hm" },
          { field: "hl", returnName: "hl" },
          { field: "jhl", returnName: "jhl" },
          { field: "tdh", returnName: "tdh" },
          { field: "nwm", returnName: "nwm" },
          { field: "jck", returnName: "jck" },
          { field: "ckm", returnName: "ckm" },
        ],
        bhurl: "/transport/hpmc/pageOfDown",
      },
    };
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  created() {},
  methods: {
    commitAll() {
      if (
        this.formObj.bh == null ||
        this.formObj.bh == undefined ||
        this.formObj.bh == ""
      ) {
        this.$message.error("没有选择磅单");
        return;
      }
      let loadingInstance = Loading.service({
        fullscreen: true,
        text: "正在置回……",
      });
      backOne(this.formObj.bh)
        .then((data) => {
          loadingInstance.close();
          this.$message.success("置回成功");
          this.$emit("dictItemVisible");
          this.$$emit("selectParent");
        })
        .catch(() => {
          loadingInstance.close();
        });
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