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
  <div class="portside-yqbak-personedit">
    <vxe-form
      :data="dataForm"
      title-width="100"
      :rules="formRules"
      ref="editHeadForm"
    >
      <vxe-form-item title="姓名" field="personName" size="mini" span="6">
        <vxe-input v-model="dataForm.personName"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="性别" field="personSex" size="mini" span="6">
        <vxe-select v-model="dataForm.personSex" placeholder="请选择">
          <vxe-option value="1" label="男"></vxe-option>
          <vxe-option value="0" label="女"></vxe-option>
        </vxe-select>
      </vxe-form-item>
      <vxe-form-item title="岗位" field="personJob" size="mini" span="6">
        <vxe-input v-model="dataForm.personJob"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="登船事由" field="aboardBak" size="mini" span="6">
        <vxe-input v-model="dataForm.aboardBak"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="身份证号" field="personId" size="mini" span="6">
        <vxe-input v-model="dataForm.personId"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="联系方式" field="personPhone" size="mini" span="6">
        <vxe-input v-model="dataForm.personPhone"></vxe-input>
      </vxe-form-item>
      <vxe-form-item
        title="现住址"
        field="personHomePlace"
        size="mini"
        span="6"
      >
        <vxe-input v-model="dataForm.personHomePlace"></vxe-input>
      </vxe-form-item>
      <vxe-form-item
        title="上下班路线"
        field="personSxblx"
        size="mini"
        span="6"
      >
        <vxe-input v-model="dataForm.personSxblx"></vxe-input>
      </vxe-form-item>
      <vxe-form-item
        title="集中居住地"
        field="personJzjzd"
        size="mini"
        span="24"
      >
        <vxe-input v-model="dataForm.personJzjzd"></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="登船证有效期" field="aboardIdTime" size="mini" span="8">
        <vxe-input
          v-model="dataForm.aboardIdTime"
          type="date"
          clearable
          valueFormat="yyyy-MM-dd"
          @change="changeTime"
        ></vxe-input>
      </vxe-form-item>
      <vxe-form-item title="管控状态" field="gkzt" size="mini" span="8">
        <vxe-select v-model="dataForm.gkzt" placeholder="请选择">
          <vxe-option value="社区检测" label="社区检测"></vxe-option>
          <vxe-option value="集中封闭管理" label="集中封闭管理"></vxe-option>
        </vxe-select>
      </vxe-form-item>
      <vxe-form-item title="接种新冠疫苗" field="ifJz" size="mini" span="8">
        <vxe-select
          v-model="dataForm.ifJz"
          placeholder="请选择"
          @change="changeIfjz"
        >
          <vxe-option :value="1" label="是"></vxe-option>
          <vxe-option :value="0" label="否"></vxe-option>
        </vxe-select>
      </vxe-form-item>
      <vxe-form-item title="告知书" field="urlGzs" size="mini" span="8">
        <el-upload
          v-if="ifSave"
          :headers="headers"
          :show-file-list="false"
          :on-success="handleurlGzsSuccess"
          :on-preview="handlePictureCardurlGzsPreview"
          class="avatar-uploader"
          action="/admin/sys-file/upload"
        >
          <img
            v-if="dataForm.urlGzs != null"
            :src="urlGzs"
            class="avatar"
            id="urlGzsId"
          />
          <i v-else class="el-icon-plus avatar-uploader-icon" />
        </el-upload>
         <viewer v-if="!ifSave" :images="urlGzsList">
             <img v-for="src in urlGzsList" :src="src" :key="src" class="avatar" id="urlGzsIdView">
        </viewer>
      </vxe-form-item>
      <vxe-form-item title="登船证" field="urlDcz" size="mini" span="8">
        <el-upload
          v-if="ifSave"
          :headers="headers"
          :show-file-list="false"
          :on-success="handleurlDczSuccess"
          class="avatar-uploader"
          action="/admin/sys-file/upload"
        >
          <img
            v-if="dataForm.urlDcz != null"
            :src="urlDcz"
            class="avatar"
            id="urlDczId"
          />
          <i v-else class="el-icon-plus avatar-uploader-icon" />
        </el-upload>
        <viewer v-if="!ifSave"  :images="urlDczList">
             <img v-for="src in urlDczList" :src="src" :key="src" class="avatar" id="urlDczIdView">
        </viewer>
      </vxe-form-item>
      <vxe-form-item title="接种证明" field="urlJzzm" size="mini" span="8">
        <el-upload
          v-if="ifSave"
          :headers="headers"
          :show-file-list="false"
          :on-success="handleurlJzzmSuccess"
          class="avatar-uploader"
          action="/admin/sys-file/upload"
        >
          <img
            v-if="dataForm.urlJzzm != null"
            :src="urlJzzm"
            class="avatar"
            id="urlJzzmId"
          />
          <i v-else class="el-icon-plus avatar-uploader-icon" />
        </el-upload>
        <viewer v-if="!ifSave"  :images="urlJzzmList">
             <img v-for="src in urlJzzmList" :src="src" :key="src" class="avatar" id="urlJzzmIdView">
        </viewer>
      </vxe-form-item>
      <vxe-form-item title="其他文件" field="urlOther" size="mini" span="24">
        <el-upload
          :headers="headers"
          class="upload-list"
          action="/admin/sys-file/upload"
          :before-upload="beforeUrlOther"
          :on-success="handleurlOtherSuccess"
          :on-remove="handleurlOtherRemove"
          :file-list="fileList"
          :on-exceed="handleurlOtherExceed"
          :limit="3"
          multiple
        >
          <el-button size="small" type="primary" v-if="ifSave"
            >点击上传</el-button
          >
          <div slot="tip" class="el-upload__tip">
            文件超过1M
          </div>
        </el-upload>
      </vxe-form-item>
    </vxe-form>
    <el-divider content-position="center">同住家庭成员信息</el-divider>
    <vxe-toolbar perfect size="mini" v-if="ifSave">
      <template v-slot:buttons>
        <vxe-button type="text" class="fa fa-plus" @click="saveFamliy()"
          >新增</vxe-button
        >
        <vxe-button
          type="text"
          icon="fa fa-trash"
          @click="$refs.tableFamliy.removeCheckboxRow()"
          >删除</vxe-button
        >
      </template>
    </vxe-toolbar>
    <vxe-table
      border
      show-overflow
      keep-source
      size="mini"
      :data="tableDataFamliy"
      :loading="tableloadFamliy"
      :round="true"
      align="center"
      height="130px"
      ref="tableFamliy"
      :edit-rules="validRulesFamliy"
      :edit-config="{ trigger: 'click', mode: 'row' }"
      :mouse-config="{ selected: true }"
      :checkbox-config="{ range: true }"
    >
      <vxe-table-column type="checkbox" width="60"></vxe-table-column>
      <vxe-table-column
        field="personName"
        title="姓名"
        :edit-render="{ autofocus: '.vxe-input--inner' }"
      >
        <template v-slot:edit="{ row }">
          <vxe-input v-model="row.personName"></vxe-input>
        </template>
      </vxe-table-column>
      <vxe-table-column
        field="personPhone"
        title="联系方式"
        :edit-render="{ autofocus: '.vxe-input--inner' }"
      >
        <template v-slot:edit="{ row }">
          <vxe-input v-model="row.personPhone"></vxe-input>
        </template>
      </vxe-table-column>
      <vxe-table-column
        field="unitName"
        title="公司"
        :edit-render="{ autofocus: '.vxe-input--inner' }"
      >
        <template v-slot:edit="{ row }">
          <vxe-input v-model="row.unitName"></vxe-input>
        </template>
      </vxe-table-column>
      <vxe-table-column
        field="personPlace"
        title="住址"
        :edit-render="{ autofocus: '.vxe-input--inner' }"
      >
        <template v-slot:edit="{ row }">
          <vxe-input v-model="row.personPlace"></vxe-input>
        </template>
      </vxe-table-column>
      <vxe-table-column
        field="personJob"
        title="岗位"
        :edit-render="{ autofocus: '.vxe-input--inner' }"
      >
        <template v-slot:edit="{ row }">
          <vxe-input v-model="row.personJob"></vxe-input>
        </template>
      </vxe-table-column>
      <vxe-table-column
        field="peronType"
        title="人员关系"
        :edit-render="{ autofocus: '.vxe-input--inner' }"
      >
        <template v-slot:edit="{ row }">
          <vxe-input v-model="row.peronType"></vxe-input>
        </template>
      </vxe-table-column>
    </vxe-table>
    <el-divider content-position="center">疫苗接种情况</el-divider>
    <vxe-toolbar perfect size="mini" v-if="ifSave">
      <template v-slot:buttons>
        <vxe-button type="text" class="fa fa-plus" @click="saveYm()"
          >新增</vxe-button
        >
        <vxe-button
          type="text"
          icon="fa fa-trash"
          @click="$refs.tableYm.removeCheckboxRow()"
          >删除</vxe-button
        >
      </template>
    </vxe-toolbar>
    <vxe-table
      border
      show-overflow
      keep-source
      size="mini"
      :data="tableDataYm"
      :loading="tableloadYm"
      :round="true"
      align="center"
      height="130px"
      ref="tableYm"
      :edit-rules="validRulesYm"
      :edit-config="{ trigger: 'click', mode: 'row' }"
      :mouse-config="{ selected: true }"
      :checkbox-config="{ range: true }"
    >
      <vxe-table-column type="checkbox" width="60"></vxe-table-column>
      <vxe-table-column
        field="veroDate"
        title="疫苗日期"
        :edit-render="{ autofocus: '.vxe-input--inner' }"
      >
        <template v-slot:edit="{ row }">
          <vxe-input
            v-model="row.veroDate"
            type="date"
            clearable
            valueFormat="yyyy-MM-dd"
            :transfer="true"
          ></vxe-input>
        </template>
      </vxe-table-column>
      <vxe-table-column
        field="veroName"
        title="疫苗名称"
        :edit-render="{ autofocus: '.vxe-input--inner' }"
      >
        <template v-slot:edit="{ row }">
          <vxe-input v-model="row.veroName"></vxe-input>
        </template>
      </vxe-table-column>
      <vxe-table-column
        field="veroNumAll"
        title="总针数"
        :edit-render="{ autofocus: '.vxe-input--inner' }"
      >
        <template v-slot:edit="{ row }">
          <vxe-input v-model="row.veroNumAll" type="number"></vxe-input>
        </template>
      </vxe-table-column>
      <vxe-table-column
        field="veroNum"
        title="当前针"
        :edit-render="{ autofocus: '.vxe-input--inner' }"
      >
        <template v-slot:edit="{ row }">
          <vxe-input v-model="row.veroNum" type="number"></vxe-input>
        </template>
      </vxe-table-column>
    </vxe-table>
    <div align="center">
      <el-button
        type="primary"
        size="mini"
        icon="el-icon-check"
        @click="handleSubmit"
        v-if="ifSave"
        >保存</el-button
      >
      <el-button size="mini" icon="el-icon-close" plain @click="closeWindows"
        >取消</el-button
      >
    </div>
  </div>
</template>

<script>
import { addPerson, familyList, veroList } from "@/api/portside/yqbak";
import store from "@/store";
import "@/styles/common/edit.scss";
import request from "@/router/axios";
import { mapGetters } from "vuex";
import EditDown from "@/views/baseUtile/EditDown";
import { handleImg } from "@/util/util";
import moment from 'moment';
export default {
  components: { EditDown },
  props: ["datanow", "ifView"],
  data() {
    return {
      dataForm: {
        companyId: null,
        companyName: null,
        urlGzs: null,
        urlDcz: null,
        urlJzzm: null
      },
      headers: {
        Authorization: "Bearer " + store.getters.access_token
      },
      formRules: {
        personName: [{ required: true, message: "请输入姓名" }],
        ifJz: [{ required: true, message: "请选择是否接种新冠疫苗" }],
        urlGzs: [{ required: true, message: "请上传告知书" }],
        urlDcz: [{ required: true, message: "请上传登船证" }],
        urlJzzm: [{ required: false, message: "请上传接种证明" }],
        personSex: [{ required: true, message: "请选择性别" }],
        personId: [{ required: true, message: "请填写身份证" }],
        personPhone: [{ required: true, message: "请填写手机号" }],
        personHomePlace: [{ required: true, message: "请填写现住址" }],
        aboardIdTime: [{ required: true, message: "请选择登船证有效期时间" }],
      },
      validRulesFamliy: {
        personName: [{ required: true, message: "请输入姓名" }]
      },
      validRulesYm: {
        veroName: [{ required: true, message: "请输入疫苗名称" }]
      },
      tableDataFamliy: [],
      tableloadFamliy: false,
      tableDataYm: [],
      urlGzs: "",
      urlGzsList:[],
      urlDcz: "",
      urlDczList:[],
      urlJzzm: "",
      urlJzzmList:[],
      fileList: [],
      ifSave: true,
      fileListBak: [],
      tableloadYm: false,
      urlGzsdialogVisible: false,
      downTableOption: {
        //动态代码
        company: "companyName",
        companyColum: [
          { field: "pkid", title: "代码" },
          { field: "companyName", title: "名称" }
        ],
        companyreturnApi: [
          { field: "pkid", returnName: "companyId" },
          { field: "companyName", returnName: "companyName" }
        ],
        companyurl: "/portside/yqcompany/page"
      }
    };
  },
  computed: {
    ...mapGetters(["permissions"])
  },
  created() {
    if (this.datanow != null) {
      this.dataForm = this.datanow;
      if (this.dataForm.urlGzs != null && this.dataForm.urlGzs != "") {
        handleImg(this.dataForm.urlGzs, "urlGzsId");
        handleImg(this.dataForm.urlGzs, "urlGzsIdView");
      }
      if (this.dataForm.urlJzzm != null && this.dataForm.urlJzzm != "") {
        handleImg(this.dataForm.urlJzzm, "urlJzzmId");
        handleImg(this.dataForm.urlJzzm, "urlJzzmIdView");
      }
      if (this.dataForm.urlDcz != null && this.dataForm.urlDcz != "") {
        handleImg(this.dataForm.urlDcz, "urlDczId");
        handleImg(this.dataForm.urlDcz, "urlDczIdView");
      }
      if (this.dataForm.urlOther != null && this.dataForm.urlOther != "") {
        var urls = this.dataForm.urlOther.split(";");
        var num;
        for (num in urls) {
          this.fileList.push(JSON.parse(urls[num]));
          this.fileListBak.push(JSON.parse(urls[num]));
        }
      }
      this.urlGzsList.push(this.dataForm.urlGzs);
      this.urlDczList.push(this.dataForm.urlDcz);
       this.urlJzzmList.push(this.dataForm.urlJzzm);
      this.getVero(this.dataForm.pkid);
      this.getFamily(this.dataForm.pkid);
      if (this.ifView != null && this.ifView == "yes") {
        this.ifSave = false;
      }
    }
  },
  mounted: function() {},
  methods: {
    async handleSubmit() {
      if (this.dataForm.ifJz == 1) {
        if (
          this.$refs.tableYm.getTableData().fullData == null ||
          this.$refs.tableYm.getTableData().fullData.length == 0
        ) {
          this.$message.error("请填写具体接种疫苗情况");
          return;
        }
      }
      const errtableYm = await this.$refs.tableYm
        .fullValidate()
        .catch(errtableYm => errtableYm);
      if (errtableYm) {
        return;
      }
      const errtableFamliy = await this.$refs.tableFamliy
        .fullValidate()
        .catch(errtableFamliy => errtableFamliy);
      if (errtableFamliy) {
        return;
      }
      this.$refs.editHeadForm
        .validate(valid => {})
        .then(valid => {
          addPerson(
            this.dataForm,
            this.$refs.tableFamliy.getRecordset(),
            this.$refs.tableYm.getRecordset()
          )
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
    },
    changeIfjz({ value }) {
      if (value == 1) {
        this.formRules.urlJzzm[0].required = true;
      } else if (value == 0) {
        this.formRules.urlJzzm[0].required = false;
      }
    },
    getFamily(id) {
      this.tableloadFamliy = true;
      familyList(id)
        .then(response => {
          this.tableDataFamliy = response.data.data;
          this.tableloadFamliy = false;
        })
        .catch(() => {
          this.tableloadFamliy = false;
        });
    },
    getVero(id) {
      this.tableloadYm = true;
      veroList(id)
        .then(response => {
          this.tableDataYm = response.data.data;
          this.tableloadYm = false;
        })
        .catch(() => {
          this.tableloadYm = false;
        });
    },
    saveFamliy() {
      let record = {};
      this.$refs.tableFamliy
        .insert(record)
        .then(({ row }) =>
          this.$refs.tableFamliy.setActiveCell(row, "personName")
        );
    },
    saveYm() {
      let record = {};
      this.$refs.tableYm
        .insert(record)
        .then(({ row }) => this.$refs.tableYm.setActiveCell(row, "veroDate"));
    },
    handleurlGzsSuccess(res, file) {
      this.urlGzs = URL.createObjectURL(file.raw);
      this.dataForm.urlGzs = res.data.url;
    },
    handleurlDczSuccess(res, file) {
      this.urlDcz = URL.createObjectURL(file.raw);
      this.dataForm.urlDcz = res.data.url;
    },
    handleurlJzzmSuccess(res, file) {
      this.urlJzzm = URL.createObjectURL(file.raw);
      this.dataForm.urlJzzm = res.data.url;
    },
    handleurlOtherSuccess(res, file, fileList) {
      this.fileListBak.push(file);
      this.fileListDeal(this.fileListBak);
    },
    changeTime({ value, $event }){
        if(value<=moment(new Date()).format("YYYY-MM-DD")){
          this.$message.error("登船证有效期已失效，请及时更新信息");
        }
    },
    beforeUrlOther(file) {
      //判断是否有重复名称
      var num;
      if (file.size > 1048576) {
        this.$message.error("文件上传不能大于1M");
        return false;
      }
      for (num in this.fileList) {
        if (this.fileList[num].name == file.name) {
          this.$message.error("上传文件重复");
          return false;
        }
      }
    },
    handleurlOtherExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 3 个文件，本次选择了 ${
          files.length
        } 个文件，共选择了 ${files.length + fileList.length} 个文件`
      );
    },
    fileListDeal(fileList) {
      this.fileList = [];
      var num;
      var urlOther = "";
      for (num in fileList) {
        var url;
        if (fileList[num].urlReal == null) {
          url = URL.createObjectURL(fileList[num].raw);
          this.fileList.push({
            name: fileList[num].name,
            url: url,
            urlReal: fileList[num].response.data.url
          });
          urlOther =
            urlOther +
            JSON.stringify({
              name: fileList[num].name,
              url: url,
              urlReal: fileList[num].response.data.url
            }) +
            ";";
        } else {
          this.fileList.push({
            name: fileList[num].name,
            url: url,
            urlReal: fileList[num].urlReal
          });
          urlOther =
            urlOther +
            JSON.stringify({
              name: fileList[num].name,
              url: url,
              urlReal: fileList[num].urlReal
            }) +
            ";";
        }
      }
      if (urlOther != "") {
        urlOther = urlOther.substr(0, urlOther.length - 1);
      }
      this.dataForm.urlOther = urlOther;
    },
    handlePictureCardurlGzsPreview() {
      this.urlGzsdialogVisible = true;
    },
    handleurlOtherRemove(file) {
      var num;
      for (num in this.fileListBak) {
        if (this.fileListBak[num].name == file.name) {
          this.fileListBak.splice(num, 1);
          break;
        }
      }
      this.fileListDeal(this.fileListBak);
    }
  }
};
</script>
<style lang="scss">
.portside-yqbak-personedit {
  .el-tag {
    width: 100%;
  }
  .el-divider--horizontal {
    margin: 10px 0;
  }
  .vxe-toolbar {
    height: 25px;
  }
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #409eff;
  }

  .avatar-uploader-icon {
    font-size: 28px !important;
    color: #8c939d !important;
    width: 140px !important;
    height: 140px !important;
    line-height: 140px !important;
    text-align: center !important;
  }

  .avatar {
    width: 140px;
    height: 140px;
    display: block;
  }
  .upload-list {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
}
</style>