<template>
  <el-dialog
    title="审批进度"
    :close-on-click-modal="false"
    :visible.sync="visible"
    width="80%"
    height="800px"
    append-to-body
  >
    <el-row style="text-align: center">
      <div style="overflow: hidden; display: inline-block">
        <div
          class="processDiv"
          v-for="(item, index) in nodeList"
          label="节点"
          :key="index"
          style="width: 100px; margin: 0 50px; height: 150px"
        >
          <div>
            <div
              v-for="(item, dwIndex) in nodeList[index].linkName"
              label="单位"
              :key="dwIndex"
              style="
                width: 100px;
                height: 60px;
                margin-bottom: 10px;
                line-height: 60px;
                text-align: center;
                color: white;
                border-radius: 10px;
              "
              :style="{ background: color[nodeList[index].isAgree[dwIndex]] }"
            >
              {{ nodeList[index].linkName[dwIndex] }}
            </div>
          </div>
          <img
            v-if="max != nodeList[index].orderBy"
            style="width: 100px; height: 100px"
            src="/img/jiantou.png"
          />
        </div>
      </div>
    </el-row>
    <el-row style="text-align: center; margin-bottom: 20px">
      <div style="overflow: hidden; display: inline-block">
        <div>
          <div
            v-for="item in valList"
            :key="item.linkNam"
            style="float: left; width: 160px"
          >
            <div>
              <div style="border: 1px solid black">{{ item.linkNam }}</div>
              <div
                v-html="item.opinion"
                style="border: 1px solid black; height: 100px; overflow-y: auto"
              ></div>
            </div>
          </div>
        </div>
      </div>
    </el-row>
  </el-dialog>
</template>
<script>
import store from '@/store'
import { fetchList, getRecordGroup, getApprovalRecords } from '@/api/baoshuisystem/approvalrecord'
import { getProcessSetGroup, getProcessSets } from '@/api/baoshuisystem/approvalprocessset'
export default {
  data() {
    return {
      headers: {
        'Authorization': 'Bearer ' + store.getters.access_token,
      },
      visible: false,
      dataForm: {},
      dataList: [],
      nodeList: [],
      infoList: [],
      valList: [],
      processInfo: {},
      index: 0,
      dwIndex: 0,
      max: 0,
      color: ["#E6E6E6", "green", "red"]
    }
  },
  methods: {
    init(id, times, processNo) {
      this.visible = true;
      this.dataForm.fId = id || 0;
      this.dataForm.times = times;
      this.dataForm.processNo = processNo
      this.getDataList();
    },
    getDataList() {
      this.nodeList = []

      var map = { billFid: this.dataForm.fId, processNo: this.dataForm.processNo }
      getProcessSetGroup(map).then(data => {
        this.dataList = data.data.data

        for (var i in this.dataList) {
          var linkName = this.dataList[i].linkNam.split(',');
          var isAgree = this.dataList[i].isAgree.split(',');
          var linkNameDistinct = []
          var isAgreeDistinct = []
          var isAgreeMax = Math.max(...isAgree)
          for (var j in linkName) {

            if (linkNameDistinct.indexOf(linkName[j]) == -1) {
              linkNameDistinct.push(linkName[j])
              isAgreeDistinct.push(isAgree[j])
            }
            if (isAgree[j] != 0) {
              for (var k in linkNameDistinct) {
                if (linkNameDistinct[k] == linkName[j]) {
                  if (isAgree[j] > isAgreeDistinct[k]) {
                    isAgreeDistinct[k] = isAgree[j]
                  }

                }
              }
            }

          }
          var map = { orderBy: this.dataList[i].orderBy, linkName: linkNameDistinct, isAgree: isAgreeDistinct }
          this.nodeList.push(map);
        }
        console.log("nodeList", this.nodeList)
        this.max = this.nodeList[this.nodeList.length - 1].orderBy
        var arr = []

      })
      getProcessSets(map).then(data => {
        this.infoList = data.data.data || []
        this.processInfo = {}

        for (var i in this.infoList) {

          if (this.infoList[i].opinion == undefined || this.infoList[i].opinion == '') {
            this.infoList[i].approvalTim = ""
          }

          //processInfo[this.infoList[i].linkNam] = this.infoList[i]
          if (this.processInfo[this.infoList[i].linkNam] == undefined) {
            this.processInfo[this.infoList[i].linkNam] = { opinion: '', approvalTim: '', linkNam: '', orderBy: '' }
          }
          console.log("", this.infoList[i].manName)
          var opinionExplain = "";
          if (this.infoList[i].opinionExplain) {
            opinionExplain = (this.infoList[i].opinionExplain || '') + "<br/>"
          }
          this.processInfo[this.infoList[i].linkNam].opinion += (this.infoList[i].manName + ':' + (this.infoList[i].opinion || '') + "<br/>" + opinionExplain + (this.infoList[i].approvalTim || '') + "<br/><br/>")

          //this.processInfo[this.infoList[i].linkNam].approvalTim = this.infoList[i].approvalTim
          this.processInfo[this.infoList[i].linkNam].linkNam = this.infoList[i].linkNam
          this.processInfo[this.infoList[i].linkNam].orderBy = this.infoList[i].orderBy

        }
        this.valList = []

        for (var key in this.processInfo) {

          this.valList.push(this.processInfo[key])
        }

      })
    }
  }
}
</script>
<style scoped>
.feeBtn {
  margin-bottom: 5%;
}
.processDiv {
  float: left;
  align-items: center; /*实现垂直居中*/
  display: flex;
}
</style>