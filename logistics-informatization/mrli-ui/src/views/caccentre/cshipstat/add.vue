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
  <div class="caccentre-cshipstat-addedit">
    <vxe-form :data="dataForm" title-width="100" :rules="formRules" ref="editForm">
       <!-- <vxe-form-item title="序号" field="seqNo" size="mini" span="12">
        <vxe-input v-model="dataForm.seqNo"></vxe-input>
      </vxe-form-item> -->
      <vxe-form-item title="动态代码" field="statCod" size="mini" span="12">
        <vxe-input v-model="dataForm.statCod"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="动态" field="status" size="mini" span="12">
        <vxe-input v-model="dataForm.status"></vxe-input>
      </vxe-form-item>      
      <vxe-form-item title="停时类别代码" field="code" size="mini" span="12">
        <EditDown
          :objs="dataForm"
          :property="downTableOption.code"
          :tableColumns="downTableOption.codeColum"
          :urlapi="downTableOption.codeurl"
          :returnApi="downTableOption.codereturnApi"
          sizenow="mini"
        ></EditDown>
      </vxe-form-item>      
      <vxe-form-item title="停时类别" field="cstatus" size="mini" span="12">
        <vxe-input v-model="dataForm.cstatus" ></vxe-input>
      </vxe-form-item>   
      <vxe-form-item title="备注" field="markTxt" size="mini" span="12">
        <vxe-input v-model="dataForm.markTxt" ></vxe-input>
      </vxe-form-item>  
    </vxe-form>
    <div align="center">
      <el-button type="primary" size="mini" icon="el-icon-check" @click="handleSubmit">保存</el-button>
      <el-button size="mini" icon="el-icon-close" plain @click="closeWindows">取消</el-button>
    </div>
  </div>
</template>

<script>
import { addObj } from "@/api/caccentre/cshipstat";
import request from "@/router/axios";
import "@/styles/common/edit.scss";
import { mapGetters } from "vuex";
import EditDown from "@/views/baseUtile/EditDown";
export default {
  props: ["datanow"],
  components: { EditDown },
  name: "caccentre-cshipstat-add",
  data() {
    return {
      dataForm: { code: null ,cstatus:null},
      formRules: {
        statCod: [{ required: true, message: "请输入动态代码" }],
        status: [{ required: true, message: "请输入动态" }],        
      },
      downTableOption: {              
        //停时类别代码
        code: "code",
        codeColum: [
          { field: "CODE", title: "停时类别代码" },
          { field: "MEANING", title: "停时类别" },
        ],
        codereturnApi: [
          { field: "CODE", returnName: "code" },
          { field: "MEANING", returnName: "cstatus" }
          ],
          codeurl: "/basecode/basecod/getshipstatus",     
        
      }
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() {
    
      if(this.datanow.statCod!=null){ 
      this.dataForm = this.datanow;
      
    }else{
     
     }
    ;
  },
  mounted: function() {
  
  },
  methods: {
    handleSubmit() {
       if (
            this.dataForm.statCod == null 
          ) {
            this.$message.error("动态代码不能为空！");
            return;
          }
        if (
            this.dataForm.status == null 
          ) {
            this.$message.error("动态不能为空！");
            return;
          }
        
      this.$refs.editForm
        .validate(valid => {})
        .then(valid => {
          addObj(this.dataForm)          
            .then(data => {
              this.$message.success("保存成功");
              this.$emit("selectList");
              this.$emit("dictItemVisible");
            })
            .catch(() => {});
        });
    },
    closeWindows() {
      this.$emit("dictItemVisible");
    }
  }
};
</script>
<style >
</style>