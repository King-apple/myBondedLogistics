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
  <div class="caccentre-monitortocar-index">
    <basic-container>
    <div class="imagesBody" v-on:click="createView()"></div>
    </basic-container>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import { Loading } from "element-ui";
import Vue from "vue";
import VXETable from "vxe-table";

export default {
  components: {},
  name: "mioyardhead",
  data() {
    return {};
  },
  watch: {
  
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  mounted() {},
  created() {
     this.createView();
  },
  methods: {
      createView(){
            window.open('http://221.202.72.170:2029/pages/monitor/monitorseq.html?tkn=54aad30d902a421ca7ab90a99b1fea23', "_blank");
      }
  }
};
</script>
<style lang="scss" scoped>
.caccentre-monitortocar-index {
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
    background: url(images.png);
    background-repeat: no-repeat; //不重复
    background-size: 100% 100%; // 满屏
  }
}
</style>>