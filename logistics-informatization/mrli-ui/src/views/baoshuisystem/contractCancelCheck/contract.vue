<template>
  <div class="mod-config">
    <basic-container>
      <el-form
        :inline="true"
        :model="dataForm"
        ref="dataForm"
        @keyup.enter.native="getDataList()"
      >
        <el-row>
          <el-col :span="6">
            <el-form-item label="合同编号" prop="contNo">
              <el-input
                v-model="dataForm.contNo"
                placeholder="合同编号"
              ></el-input> </el-form-item
          ></el-col>
          <el-col :span="6">
            <el-form-item label="合同类型" prop="contractType">
              <el-select
                v-model="dataForm.contractType"
                placeholder="请选择"
                filterable
                clearable
              >
                <el-option
                  v-for="(
                    contractTypeOptionsItem, contractTypeOptionsIndex
                  ) in contractTypeOptions"
                  :key="contractTypeOptionsIndex"
                  :label="contractTypeOptionsItem.description"
                  :value="contractTypeOptionsItem.description"
                >
                </el-option>
              </el-select> </el-form-item
          ></el-col>
          <el-col :span="6">
            <el-form-item label="合同标的" prop="cargoNam">
              <el-select
                v-model="dataForm.cargoNam"
                placeholder="请选择"
                filterable
                clearable
                @change="$forceUpdate()"
              >
                <el-option
                  v-for="(item, index) in cargoNamOptions"
                  :key="index"
                  :label="item.cargoKindNam"
                  :value="item.cargoKindNam"
                >
                </el-option>
              </el-select> </el-form-item
          ></el-col>
          <el-col :span="6">
            <el-form-item label="审批状态" prop="isAgree">
              <el-select
                v-model="dataForm.isAgree"
                placeholder="请选择"
                @change="$forceUpdate()"
                clearable
              >
                <el-option
                  v-for="(item, index) in isAgreeOptions"
                  :key="index"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="合同开始日期" prop="params">
          <el-date-picker
            v-model="dataForm.params"
            type="daterange"
            :picker-options="pickerOptions"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            align="right"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item>

        <!-- 
        <el-form-item label="当前角色" prop="linkNam">
          <el-select
            v-model="dataForm.linkNam"
            placeholder="请选择"
            filterable
            clearable
            @change="$forceUpdate()"
          >
            <el-option
              v-for="(item, index) in linkNamOptions"
              :key="index"
              :label="item"
              :value="item"
            >
            </el-option>
          </el-select>
        </el-form-item> -->

        <el-form-item>
          <el-button type="primary" @click="getData">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="warning" @click="resetForm('dataForm')"
            >重置</el-button
          >
        </el-form-item>
      </el-form>

      <div class="avue-crud" style="width: 100%">
        <el-table :data="dataList" border v-loading="dataListLoading">
          <!-- <el-table-column
            prop="fId"
            header-align="center"
            align="center"
            label="主键"
            hidden="hidden"
          >
          </el-table-column> -->

          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form
                label-position="left"
                inline
                class="demo-table-expand infor"
              >
                <el-row>
                  <el-col :span="8"
                    ><el-form-item label="合同编号">
                      <el-input
                        v-model="props.row.contNo"
                        placeholder="合同编号"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="8"
                    ><el-form-item label="承办单位">
                      <el-input
                        v-model="props.row.firstNam"
                        placeholder="承办单位"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="8">
                    <el-form-item label="标的额">
                      <el-input
                        v-model="props.row.dept"
                        placeholder="标的额"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                </el-row>

                <el-row>
                  <el-col :span="8"
                    ><el-form-item label="经办人">
                      <el-input
                        v-model="props.row.operator"
                        placeholder="经办人"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="8"
                    ><el-form-item label="对方签约单位">
                      <el-input
                        v-model="props.row.secondNam"
                        placeholder="对方签约单位"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="8">
                    <el-form-item label="合同类型">
                      <el-input
                        v-model="props.row.contractType"
                        placeholder="合同类型"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                </el-row>

                <el-row>
                  <el-col :span="8"
                    ><el-form-item label="合同标的">
                      <el-input
                        v-model="props.row.cargoNam"
                        placeholder="合同标的"
                        readonly
                        style="width: 49%"
                      ></el-input>
                      <el-input
                        v-model="props.row.cargoKindNam"
                        placeholder="货类"
                        readonly
                        style="width: 49%"
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="8"
                    ><el-form-item label="合同标的数量">
                      <el-input
                        v-model="props.row.cargoNum"
                        placeholder="合同标的数量"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="8">
                    <el-form-item label="标的额">
                      <el-input
                        v-model="props.row.cargoFee"
                        placeholder="标的额"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                </el-row>

                <el-row>
                  <el-col :span="8"
                    ><el-form-item label="合同开始日期">
                      <el-input
                        v-model="props.row.startDte"
                        placeholder="合同开始日期"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="8"
                    ><el-form-item label="合同结束日期">
                      <el-input
                        v-model="props.row.endDte"
                        placeholder="合同结束日期"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="8">
                    <el-form-item label="合同签订日期">
                      <el-input
                        v-model="props.row.conDte"
                        placeholder="合同签订日期"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                </el-row>

                <el-row>
                  <el-col :span="8"
                    ><el-form-item label="结算依据">
                      <el-input
                        v-model="props.row.settlementBasis"
                        placeholder="结算依据"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="8"
                    ><el-form-item label="结算方式">
                      <el-input
                        v-model="props.row.clearingForm"
                        placeholder="结算方式"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="8">
                    <el-form-item label="客户联系人">
                      <el-input
                        v-model="props.row.contact"
                        placeholder="客户联系人"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                </el-row>

                <el-row>
                  <el-col :span="8"
                    ><el-form-item label="联系电话">
                      <el-input
                        v-model="props.row.phone"
                        placeholder="联系电话"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="8"
                    ><el-form-item label="规格">
                      <el-input
                        v-model="props.row.format"
                        placeholder="规格"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="8">
                    <el-form-item label="船名">
                      <el-input
                        v-model="props.row.shipNam"
                        placeholder="船名"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                </el-row>

                <el-row>
                  <el-col :span="6"
                    ><el-form-item label="包装">
                      <el-input
                        v-model="props.row.pkgKindNam"
                        placeholder="包装"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="6"
                    ><el-form-item label="存放地点">
                      <el-input
                        v-model="props.row.locate"
                        placeholder="存放地点"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="6">
                    <el-form-item label="内外贸">
                      <el-input
                        v-model="props.row.tradeId"
                        placeholder="内外贸"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="6">
                    <el-form-item label="进出口">
                      <el-input
                        v-model="props.row.inoutId"
                        placeholder="进出口"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                </el-row>
                <el-form-item id="feeI">
                  <div
                    v-for="(item, index) in props.row.contractRateList"
                    :key="index"
                    id="fee"
                  >
                    费目{{ index + 1 }}
                    <el-row id="feeRow" style="margin-top: -3%">
                      <el-col :span="6"
                        ><el-form-item label="费目名称：">
                          <el-input
                            v-model="item.feeNam"
                            placeholder="费目名称："
                            readonly
                          ></el-input> </el-form-item
                      ></el-col>
                      <el-col :span="6"
                        ><el-form-item label="费率值：">
                          <el-input
                            v-model="item.feeRat"
                            placeholder="费率值："
                            readonly
                          ></el-input> </el-form-item
                      ></el-col>
                      <el-col :span="6"
                        ><el-form-item label="费率单位：">
                          <el-input
                            v-model="item.feeUnit"
                            placeholder="费率单位："
                            readonly
                          ></el-input> </el-form-item
                      ></el-col>
                      <el-col :span="6"
                        ><el-form-item label="备注：">
                          <el-input
                            v-model="item.markTxt"
                            placeholder="备注："
                            readonly
                          ></el-input> </el-form-item
                      ></el-col>
                    </el-row>
                  </div>
                </el-form-item>
                <el-row id="rem">
                  <el-col :span="24"
                    ><el-form-item label="备注">
                      <el-input
                        v-model="props.row.markTxt"
                        placeholder="备注"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                </el-row>
                <el-form-item label="附件" id="fileFor">
                  <el-row
                    v-for="(item, index) in props.row.sysFileList"
                    :key="index"
                  >
                    <el-col :span="24">
                      <span
                        class="onespan"
                        @click="downloadFile(item.fileName, item.bucketName)"
                        style="cursor: pointer"
                      >
                        附件{{ index + 1 }} ：{{ item.original }}</span
                      >
                    </el-col>
                    附件{{ index + 1 }}说明：
                    <el-input
                      v-model="item.remark"
                      placeholder="附件说明"
                      readonly
                      style="width: 50%"
                    ></el-input>
                  </el-row>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column
            prop="contNam"
            header-align="center"
            align="center"
            label="合同名称"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="contNo"
            header-align="center"
            align="center"
            label="合同编号"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="firstNam"
            header-align="center"
            align="center"
            label="承办单位"
            sortable
          >
          </el-table-column>

          <el-table-column
            prop="cargoNam"
            header-align="center"
            align="center"
            label="合同标的(货名)"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="cargoKindNam"
            header-align="center"
            align="center"
            label="合同标的(货类)"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="linkNam"
            header-align="center"
            align="center"
            label="审批单位"
            sortable
          >
          </el-table-column>

          <el-table-column
            header-align="center"
            align="center"
            label="合同期限"
            sortable
          >
            <template slot-scope="props">
              <span>{{ props.row.startDte }}至{{ props.row.endDte }}</span>
            </template>
          </el-table-column>

          <el-table-column
            header-align="center"
            align="center"
            label="操作"
            fixed="right"
          >
            <template slot-scope="scope">
              <el-button
                type="text"
                size="small"
                icon="el-icon-edit"
                v-if="scope.row.isAgree == 0"
                @click="agreeOrDisAgree(scope.row.rFid, scope.row)"
                >点击审批</el-button
              >
              <el-button
                type="text"
                size="small"
                icon="el-icon-s-unfold"
                v-if="scope.row.contractStatus != 0"
                @click="checkProcessHandle(scope.row.fId, scope.row.times)"
                >查看审核进度</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>

      <div class="avue-crud__pagination">
        <el-pagination
          @size-change="sizeChangeHandle"
          @current-change="currentChangeHandle"
          :current-page="pageIndex"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="pageSize"
          :total="totalPage"
          background
          layout="total, sizes, prev, pager, next, jumper"
        >
        </el-pagination>
      </div>
      <!-- 弹窗, 新增 / 修改 -->
      <table-form
        v-if="addOrUpdateVisible"
        ref="addOrUpdate"
        @refreshDataList="getData"
      ></table-form>
      <!-- 弹窗, 新增 / 修改 -->
      <process-form
        v-if="checkProcessVisible"
        ref="checkProcess"
        @refreshDataList="getDataList"
      ></process-form>
    </basic-container>
  </div>
</template>

<script>
import { fetchList, delObj, filePageList, handleDown, getRoleList } from '@/api/baoshuisystem/contractcheck'
import { getContractCheckRecord, getUserInfo, getDeptInfo, getContractType, getCheckStatusDic, getInoutIdDic, getTradeIdDic } from '@/api/baoshuisystem/contract'
import { getOrgUserInfo } from '@/api/baoshuisystem/organizeruser'
import TableForm from './contractCheckDialog'
import ProcessForm from '@/views/baoshuisystem/contract/contractCheckProcess'
import { mapGetters } from 'vuex'
import { fetchList as carGoNamesList, getContractObjectInfoAll } from '@/api/baoshuisystem/contractobject'
import { Textarea } from 'vxe-table'
export default {
  data() {
    return {
      dataForm: {
        key: '',
        isAgree: '0'
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      addOrUpdateVisible: false,
      inoutIdDicOptions: [],
      tradeIdDicOptions: [],
      userInfo: {},
      isAgree: "0",
      cargoNamOptions: [],
      contractTypeOptions: [],
      isAgreeOptions: [],
      roles: [],
      linkNamOptions: [],
      checkProcessVisible: false,

      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      value1: [new Date(2000, 10, 10, 10, 10), new Date(2000, 10, 11, 10, 10)],
      value2: ''
    }
  },
  components: {
    TableForm,
    ProcessForm
  },
  created() {
    this.getDataList()
  },
  computed: {
    ...mapGetters(['permissions'])
  },
  methods: {
    // 获取数据列表
    async getDataList() {
      this.dataListLoading = true

      await getUserInfo().then(data => {
        this.userInfo = data.data.data.sysUser
        console.log(data)
        getRoleList(data.data.data.roles).then(data => {
          this.roles = data.data.data
          this.dataForm.approvalProcessArray = []
          for (const i in this.roles) {
            this.linkNamOptions.push(this.roles[i].roleCode)

            var array = JSON.stringify({ 'linkCode': this.roles[i].roleCode, 'processNo': 0 })
            this.dataForm.approvalProcessArray.push(array)
            if (i == 0) {
              this.dataForm.linkNam = this.roles[i].roleName
            }
          }
          this.dataForm.approvalProcessArray.push("{ 'linkCode': '', 'processNo': '' }")
          console.log("---", this.dataForm.approvalProcess)
          this.userInfo.dept = JSON.stringify(this.userInfo.dept);
          getOrgUserInfo(this.userInfo.userId).then(data => {

            this.userInfo.company = []
            for (var key in data.data.data) {
              this.userInfo.company.push(data.data.data[key].organizer)
            }
            this.getData();
            this.userInfo.company = this.userInfo.company.toString()
          })
        })
        // getDeptInfo(this.userInfo.deptId).then(response => {
        //   //this.userInfo.dept = response.data.data.name
        //   console.log(this.userInfo)

        // })
        this.dataListLoading = false
      })

      await getContractType().then(response => {
        this.contractTypeOptions = response.data.data
      })
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val
      this.getDataList()
    },

    agreeOrDisAgree(fId, row) {

      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(fId, row)
      })
    },
    downloadFile: function (filename, bucketName) {

      handleDown(filename, bucketName)
    },
    resetForm(formName) {
      if (this.$refs[formName] !== undefined) {
        this.$refs[formName].resetFields();
      }
      this.setCurrent();
    },
    getData() {
      if (this.dataForm.isAgree == '2') {
        this.dataForm.recIsCancel = '1'
      }
      getContractCheckRecord(Object.assign({
        current: this.pageIndex,
        size: this.pageSize,
        linkNam: this.dataForm.linkNam,
        linkNamArray: this.dataForm.linkNamArray,
        company: this.userInfo.company,
        isAgree: this.dataForm.isAgree,
        contNo: this.dataForm.contNo,
        contractType: this.dataForm.contractType,
        cargoNam: this.dataForm.cargoNam,
        params: this.dataForm.params,
        approvalProcessArray: this.dataForm.approvalProcessArray,
        recIsCancel: this.dataForm.recIsCancel,
        isCancel: this.dataForm.isCancel,
        processNo: '3'
      })).then(response => {
        this.dataList = response.data.data.records
        this.totalPage = response.data.data.total
        console.log("k--", response.data.data.records)
        if (this.dataForm.isAgree == '0') {
          var len1 = this.dataList.length
          for (var i = 0, vaflag = true, len = len1; i < len; vaflag ? i++ : i, len = len1) {
            if (this.dataList.length != 0 && this.dataList[i].cancelStatus != '1') {
              this.dataList.splice(i, 1)
              vaflag = false
            } else {
              vaflag = true
            }
            len1 = this.dataList.length
          }
        }
        getInoutIdDic().then(response => {
          this.inoutIdDicOptions = response.data.data
          getTradeIdDic().then(response => {
            this.tradeIdDicOptions = response.data.data
            for (var i in this.dataList) {
              for (var j in this.inoutIdDicOptions) {
                if (this.dataList[i].inoutId == this.inoutIdDicOptions[j].value) {
                  this.dataList[i].inoutId = this.inoutIdDicOptions[j].label
                }
              }
              for (var j in this.tradeIdDicOptions) {
                if (this.dataList[i].tradeId == this.tradeIdDicOptions[j].value) {
                  this.dataList[i].tradeId = this.tradeIdDicOptions[j].label
                }
              }
            }
          })
        })
      })
      if (this.cargoNamOptions.length == 0) {
        getContractObjectInfoAll().then(response => {
          this.cargoNamOptions = response.data.data
        })
      }
      if (this.isAgreeOptions.length == 0) {
        getCheckStatusDic().then(response => {
          console.log(response)
          this.isAgreeOptions = response.data.data
        })
      }


    },
    //审批进度
    checkProcessHandle(id, times) {
      this.checkProcessVisible = true
      this.$nextTick(() => {
        this.$refs.checkProcess.init(id, times, '3')
      })
    },
  }
}
</script>
<style>
.demo-table-expand table {
  border: 1px black solid;
}
.table-col {
  border-bottom: 1px #bdbdbd solid;
}
.checkDialog {
  height: 500px;
}
.checkDialog input {
  height: 500px;
}
.titleDiv {
  width: 100%;
  height: 50px;
  border: 1px black solid;
  text-align: center;
  line-height: 50px;
  font-weight: bolder;
  font-size: x-large;
}
.valueDiv {
  width: 100%;
  border: 1px black solid;
  text-align: center;
  margin: 0px -1px -1px 0px;
}
.span2 span {
  height: 100%;
  width: 25%;
  display: inline-block;
  border-right: 0.5px black solid;
}
.span4 span {
  height: 100%;
  width: calc(100% / 8);
  display: inline-block;
  border-right: 0.5px black solid;
}
.span3 span {
  height: 100%;
  width: calc(100% / 6);
  display: inline-block;
  border-right: 0.5px black solid;
}
.onespan {
  height: 100%;
  width: 80%;
  display: inline-block;
  border-right: 0.5px black solid;
}
.onespanLabel {
  height: 100%;
  width: 20%;
  display: inline-block;
  border-right: 0.5px black solid;
}
.infor .el-col {
  margin-bottom: -10px;
}
.infor .el-form-item {
  width: 100%;
}
.infor .el-form-item__content {
  width: 65%;
}
.infor label {
  width: 30%;
}
#rem .el-form-item__content {
  width: 90%;
}
#rem label {
  width: 10%;
}
#fee .el-form-item__content {
  width: 60%;
}
#fee label {
  width: 40% !important;
  text-align: center;
}

#feeI label {
  width: 10%;
}
#feeI .el-form-item__content {
  width: 90%;
}
#feeRow {
  margin-left: 5%;
}
#fileFor label {
  width: 10%;
}
#fileFor span {
  border-right: black 0px solid;
}
</style>