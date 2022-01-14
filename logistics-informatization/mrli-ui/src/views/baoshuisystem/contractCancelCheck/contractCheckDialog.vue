<template>
  <el-dialog
    title="审批"
    :close-on-click-modal="false"
    :visible.sync="visible"
    width="80%"
  >
    <el-form
      ref="form"
      :model="dataForm"
      label-width="80px"
      v-loading="loading"
    >
      <el-form-item label="">
        <el-form label-position="left" inline class="demo-table-expand">
          <div class="valueDiv span2">
            <span>合同编号</span>
            <span>{{ contractForm.contNo }}</span>
            <span>承办单位</span>
            <span>{{ contractForm.firstNam }}</span>
          </div>
          <div class="valueDiv span2">
            <span>部门</span>
            <span>{{ contractForm.dept }}</span>
            <span>经办人</span>
            <span>{{ contractForm.operator }}</span>
          </div>
          <div class="valueDiv span2">
            <span>对方签约单位</span>
            <span>{{ contractForm.secondNam }}</span>
            <span>合同类型</span>
            <span>{{ contractForm.contractType }}</span>
          </div>
          <div class="valueDiv span3">
            <span>合同标的</span>
            <span
              >{{ contractForm.cargoNam }}-{{ contractForm.cargoKindNam }}</span
            >
            <span>合同标的数量</span>
            <span>{{ contractForm.cargoNum }}</span>
            <span>标的额</span>
            <span>{{ contractForm.cargoFee }}</span>
          </div>
          <div class="valueDiv span3">
            <span>合同开始日期</span>
            <span>{{ contractForm.startDte }}</span>
            <span>合同结束日期</span>
            <span>{{ contractForm.endDte }}</span>
            <span>合同签订日期</span>
            <span>{{ contractForm.conDte }}</span>
          </div>
          <div class="valueDiv span4">
            <span>结算依据</span>
            <span>{{ contractForm.settlementBasis }}</span>
            <span>结算方式</span>
            <span>{{ contractForm.clearingForm }}</span>
            <span>客户联系人</span>
            <span>{{ contractForm.contact }}</span>
            <span>联系电话</span>
            <span>{{ contractForm.phone }}</span>
          </div>
          <div class="valueDiv span3">
            <span>船名</span>
            <span>{{ contractForm.shipNam }}</span>
            <span>规格</span>
            <span>{{ contractForm.format }}</span>
            <span>包装</span>
            <span>{{ contractForm.pkgKindNam }}</span>
          </div>
          <div class="valueDiv">
            <span class="onespanLabel">备注</span>
            <span class="onespan">{{ contractForm.markTxt }}</span>
          </div>
          <div class="valueDiv">
            <span class="onespanLabel">作废原因</span>
            <span class="onespan">{{ contractForm.cancelReason }}</span>
          </div>
          <div class="valueDiv span4">
            <div
              v-for="(item, index) in contractForm.contractRateList"
              :key="index"
              class="valueDiv"
            >
              <span>费目名称：</span>
              <span> {{ item.feeNam }} </span>
              <span>费率值(元)：</span>
              <span> {{ item.feeRat }}</span>
              <span>费率单位：</span>
              <span> {{ item.feeUnit }} </span>
              <span> 备注：</span>
              <span>{{ item.markTxt }}</span>
            </div>
          </div>
          <div class="valueDiv">
            <div
              v-for="(item, index) in contractForm.sysFileList"
              :key="index"
              class="valueDiv"
            >
              <span class="onespanLabel">附件:</span>
              <span
                class="onespan"
                @click="downloadFile(item.fileName, item.bucketName)"
                style="cursor: pointer"
              >
                {{ item.original }}</span
              >
            </div>
          </div>
          <el-row style="width: 100%">
            <div style="width: 100%">
              <div
                class="shyj"
                style="
                  float: left;
                  border: 1px solid black;
                  line-height: 159px;
                  text-align: center;
                "
              >
                审核意见
              </div>
              <div>
                <div
                  v-for="item in valList"
                  :key="item.linkNam"
                  style="float: left"
                  class="shyj"
                >
                  <div>
                    <div style="border: 1px solid black">
                      {{ item.linkNam }}
                    </div>
                    <div
                      v-html="item.opinion"
                      style="
                        border: 1px solid black;
                        height: 127px;
                        overflow-y: auto;
                      "
                    ></div>
                  </div>
                </div>
              </div>
            </div>
          </el-row>
        </el-form>
        <el-input
          type="textarea"
          :rows="4"
          placeholder="请输入审批意见"
          v-model="dataForm.optionExplain"
          style="margin-top: 10px"
        >
        </el-input>
        <div style="float: right; margin-top: 20px">
          <el-button
            type="primary"
            @click="agreeOrDisAgree(dataForm.fId, '1', dataForm.optionExplain)"
            >同意</el-button
          >
          <el-button
            type="danger"
            @click="agreeOrDisAgree(dataForm.fId, '2', dataForm.optionExplain)"
            >驳回</el-button
          >
        </div>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>
<script>

import { fetchList, updateRec, getApprovalRecords } from '@/api/baoshuisystem/approvalrecord'
import { getProcessSetGroup, getProcessSets } from '@/api/baoshuisystem/approvalprocessset'
import { handleDown } from '@/api/baoshuisystem/contractcheck'
import { updateApprovalprocessset } from '@/api/baoshuisystem/contract'
import store from '@/store'
export default {
  data() {
    return {
      headers: {
        'Authorization': 'Bearer ' + store.getters.access_token,
      },
      visible: false,
      dataForm: {},
      contractForm: {},
      infoList: [],
      infoList: [],
      valList: [],
      processInfo: {},
      loading: false
    }
  },
  methods: {
    init(id, row) {
      this.dataForm = {},
        this.visible = true;
      this.dataForm.fId = id || 0;
      this.contractForm = row || {}
      var map = { billFid: this.contractForm.fId, processNo: '3' }
      getProcessSets(map).then(data => {
        this.infoList = data.data.data || []
        this.processInfo = {}
        console.log(this.infoList)
        for (var i in this.infoList) {

          if (this.infoList[i].opinion == undefined || this.infoList[i].opinion == '') {
            this.infoList[i].approvalTim = ""
          }

          //processInfo[this.infoList[i].linkNam] = this.infoList[i]
          if (this.processInfo[this.infoList[i].linkNam] == undefined) {
            this.processInfo[this.infoList[i].linkNam] = { opinion: '', approvalTim: '', linkNam: '', orderBy: '' }
          }
          this.processInfo[this.infoList[i].linkNam].opinion += (this.infoList[i].manName + ':' + (this.infoList[i].opinion || '') + "<br/>" + (this.infoList[i].opinionExplain || '') + "<br/>" + (this.infoList[i].approvalTim || '') + "<br/><br/>")
          //this.processInfo[this.infoList[i].linkNam].approvalTim = this.infoList[i].approvalTim
          this.processInfo[this.infoList[i].linkNam].linkNam = this.infoList[i].linkNam
          this.processInfo[this.infoList[i].linkNam].orderBy = this.infoList[i].orderBy

        }
        this.valList = []

        for (var key in this.processInfo) {

          this.valList.push(this.processInfo[key])
        }

      })
    },
    agreeOrDisAgree(fId, isAgree, optionExplain) {
      var opinion = ""
      var optionExplain = optionExplain
      if (isAgree == 1) {
        opinion = "同意"
      } else if (isAgree == 2) {
        opinion = "不同意"
      }
      var map = { fId: fId, isAgree: isAgree, opinion: opinion, opinionExplain: optionExplain, processNo: '3' }
      this.loading = true
      updateApprovalprocessset(map).then(data => {
        this.loading = false
        this.$notify.success("审批成功")
        this.visible = false;
        this.$emit('refreshDataList')
      })
    },
    downloadFile: function (filename, bucketName) {

      handleDown(filename, bucketName)
    },
  }
}
</script>
<style scoped>
.feeBtn {
  margin-bottom: 5%;
}
.shyj {
  width: calc(100% / 7);
}
</style>