<template>
  <el-dialog
    title="提交审批"
    :close-on-click-modal="false"
    :visible.sync="visible"
    width="30%"
    height="800px"
    append-to-body
  >
    <el-form
      :inline="true"
      :model="dataForm"
      ref="dataForm"
      autocomplete="off"
      label-position="left"
      class="formTab"
    >
      <el-form-item label="请选择是否完整审核">
       <el-select
            v-model="contractFlag.completeApprovalFlag"
            placeholder="请选择"
            clearable
          >
            <el-option
              v-for="i in contract"
              :key="i.id"
              :label="i.label"
              :value="i.value"
            >
            </el-option>
          </el-select>
      </el-form-item>
      <div v-for="item in dataList" :key="item.linkNam">
        <el-form-item
          :label="item.linkNam"
          style="width: 100%"
          class="selClass"
        >
          <el-select
            v-model="dataForm[item.linkNam]"
            placeholder="请选择"
            clearable
            multiple
            autocomplete="off"
            style="width: 180%"
          >
            <el-option
              v-for="(userItem, userIndex) in userMap[item.linkNam]"
              :key="userIndex"
              :label="userItem.manName"
              :value="userItem.username"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </div>

      <el-button type="primary" @click="submitCheck" style="margin-left: 81%"
        >提交审核</el-button
      >
    </el-form>
  </el-dialog>
</template>
<script>
import store from '@/store'
import { completeApprovalFlag } from '@/api/baoshuisystem/contract'
import { fetchList, getProcessUser, getApprovalProcessList } from '@/api/baoshuisystem/approvalprocess'
import { saveUserRecords } from '@/api/baoshuisystem/approvalrecord'
import { getContractFileDic,getCompleteApprove } from '@/api/baoshuisystem/getdictdata'
import { saveUserProcessSets } from '@/api/baoshuisystem/approvalprocessset'
import XEUtils from 'xe-utils'
export default {
  data() {
    return {
      headers: {
        'Authorization': 'Bearer ' + store.getters.access_token,
      },
      visible: false,
      dataForm: {},
      dataList: [],
      dataListAll: [],
      userList: [],
      userMap: {},
      dataRow: {},
      index: 0,
      dwIndex: 0,
      contract:{},
      contractFlag:{},
      max: 0,
      processNo: '',
      color: ["#E6E6E6", "green", "red"],
      canSubmit: true,
    }
  },
  methods: {
    init(row, processNo) {
      this.dataForm ={}
      this.contractFlag = {}
      this.dataRow = row
      this.$set(this.contractFlag,"fId",row.fId)
      this.visible = true;
      this.dataForm.fId = row.fId || 0;
      this.dataForm.times = row.times;
      this.getDataList();
      this.dataForm.processNo = processNo
      this.dataForm.cancelReason = row.cancelReason
      getCompleteApprove().then(res =>{
        this.contract = res.data.data

      }

      )
    },
    getDataList() {
      this.nodeList = []
      var map = 0
      getProcessUser(map).then(data => {
        this.dataListAll = data.data.data
        this.dataList = this.dataListAll[1]
        this.userList = this.dataListAll[0]
        this.userMap = {}
        for (var i in this.dataList) {
          this.userMap[this.dataList[i].linkNam] = []
          for (var j in this.userList) {
            if (this.userList[j].processFid == this.dataList[i].fId) {
              this.userMap[this.dataList[i].linkNam].push(this.userList[j])
            }

          }
        }

      })
    },
     async submitCheck() {
      if (this.contractFlag.completeApprovalFlag == 0) {

        //完整的情况
        await completeApprovalFlag(this.contractFlag).then(res =>{

          if(!res.data.data){
              this.$message.success('修改失败')
              return
          }
          }
        )
        var map = { processNo: this.dataForm.processNo }
        console.log("this.canSubmit")
        if (this.canSubmit) {
          this.canSubmit = false
          getApprovalProcessList(map).then(resp => {
            var times = this.dataRow.times
            if (times == 0 || times == null) {
              times = 1;
            } else {
              times++
            }
            this.dataForm.times = times;
            console.log(resp.data.data)
            for (var i in resp.data.data) {
              var k1 = resp.data.data[i].orderBy
              console.log(i, this.dataForm[resp.data.data[i].linkNam][0])
              if (this.dataForm[resp.data.data[i].linkNam][0] == undefined) {

                if (i >= resp.data.data.length - 1 || (resp.data.data[Number(i) - 1].orderBy == k1 && this.dataForm[resp.data.data[i - 1].linkNam][0] == undefined) || (resp.data.data[Number(i) - 1].orderBy != k1 && resp.data.data[Number(i) + 1].orderBy != k1)) {
                  this.$message.error(resp.data.data[i].linkNam + "未选择审批人")
                  this.canSubmit = true
                  return
                }
              }
            }
            
            this.dataForm.completeApprovalFlag=0
            saveUserProcessSets(this.dataForm).then(response => {
              if (response.data.data.code == 0) {
                this.visible = false
                this.canSubmit = true
                this.$message.success('提交成功')
              } else {
                this.$message.error(response.data.data.msg)
                this.canSubmit = true
              }
            })
          })
        } else {
          this.$notify.error("请勿重复提交")
        }

       }else if(this.contractFlag.completeApprovalFlag == 1){
        //不完整的情况
         await completeApprovalFlag(this.contractFlag).then(res =>{

          if(!res.data.data){
              this.$message.success('修改失败')
              return
          }
          }
        )
        var map = { processNo: this.dataForm.processNo }
        if (this.canSubmit) {
          this.canSubmit = false
          getApprovalProcessList(map).then(resp => {
            var times = this.dataRow.times
            if (times == 0 || times == null) {
              times = 1;
            } else {
              times++
            }
            this.dataForm.times = times;
            console.log(resp.data.data)
            var a =false;
            for (var i in resp.data.data) {
              var k1 = resp.data.data[i].orderBy
              if(!XEUtils.isUndefined(this.dataForm[resp.data.data[i].linkNam][0] )){
                a = !XEUtils.isUndefined(this.dataForm[resp.data.data[i].linkNam][0])
              }else{
                a= XEUtils.isUndefined(this.dataForm[resp.data.data[i].linkNam][0])
              }
            }
             if(!a){
                this.$message.error('请至少提交一个部门')
                this.canSubmit = true
                return
              }
             this.dataForm.completeApprovalFlag=1
            saveUserProcessSets(this.dataForm).then(response => {
              if (response.data.data.code == 0) {
                this.visible = false
                this.canSubmit = true
                this.$message.success('提交成功')
              } else {
                this.$message.error(response.data.data.msg)
                this.canSubmit = true
              }
            })
          })
        } else {
          this.$notify.error("请勿重复提交")
        }
      }

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
