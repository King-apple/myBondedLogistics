<template>
  <div class="mod-config">
    <basic-container>
      <el-form
        :inline="true"
        :model="dataForm"
        ref="dataForm"
        @keyup.enter.native="getDataList()"
      >
        <el-form-item label="时间" prop="recordTim">
          <el-date-picker
            v-model="value1"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          >
          </el-date-picker>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="getDataList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button
            type="warning"
            v-if="permissions.oneday_export"
            @click="exportExcel()"
            >导出</el-button
          >
        </el-form-item>
      </el-form>

      <div class="avue-crud" style="width: 100%">
        <el-table :data="dataList" border v-loading="dataListLoading">
          <el-table-column label="一日游统计表" align="center">
            <el-table-column label="进区" align="center">
              <el-table-column label="序号" type="index" align="center">
              </el-table-column>
              <el-table-column label="日期" prop="inRecordTim" align="center">
              </el-table-column>
              <el-table-column
                label="核注清单号"
                prop="inCheckNum"
                align="center"
              >
              </el-table-column>
              <el-table-column
                label="净重（千克）"
                prop="inSumNetWet"
                align="center"
              >
              </el-table-column>
              <el-table-column
                label="总价（USD）"
                prop="inSumTotalPrice"
                align="center"
              >
              </el-table-column>
              <el-table-column
                label="作业委托人"
                prop="consignNam"
                align="center"
              >
              </el-table-column>
            </el-table-column>
            <el-table-column label="出区" align="center">
              <el-table-column label="序号" type="index" align="center">
              </el-table-column>
              <el-table-column label="日期" prop="outRecordTim" align="center">
              </el-table-column>
              <el-table-column
                label="核注清单号"
                prop="outOutCheckNum"
                align="center"
              >
              </el-table-column>
              <el-table-column
                label="净重（千克）"
                prop="outSumNetWet"
                align="center"
              >
              </el-table-column>
              <el-table-column
                label="总价（USD）"
                prop="outSumTotalPrice"
                align="center"
              >
              </el-table-column>
              <el-table-column
                label="作业委托人"
                prop="outConsignNam"
                align="center"
              >
              </el-table-column>
             <el-table-column
                label="报关代理企业"
                prop="customsBroker"
                align="center"
              >
              </el-table-column>
            </el-table-column>
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
    </basic-container>
  </div>
</template>

<script>
import { fetchList, getObj, putObj, delObj, updateByIdForBusinessCheckStatus, getOneDay, getOneDayExcel } from '@/api/baoshuisystem/inoutyardhead'
import { mapGetters } from 'vuex'
import { dateToString } from '@/util/date'
import { exportExcelForInoutyardBodyAndHead, exportPrint } from '@/api/baoshuisystem/excelImportExport'
import store from '@/store'
export default {
  data() {
    return {
      headers: {
        'Authorization': 'Bearer ' + store.getters.access_token,
      },
      dataForm: {},
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      addOrUpdateVisible: false,
      addOrUpdateBusinessVisible: false,
      addOrUpdateOpearVisible: false,
      dialogVisible: false,
      form: {},
      formLabelWidth: '110px',
      // 非单个禁用
      single: true,
      exportDetail: {},
      fileLoading: null,
      value1: [],
      tableHtml: "",
      PrintdialogVisible: false,
      BUsinessVisible: false,
      tableBUsinessHtml: "",
      canSubmit: false,
    }
  },


  created() {
    const end = new Date();
    const start = new Date();
    start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
    this.value1 = [start, end]
    this.getDataList()
  },
  computed: {
    ...mapGetters(['permissions'])
  },
  methods: {
    // 获取数据列表
    getDataList() {
      if (this.value1 == null || this.value1.length < 2) {
        this.$message.error('请填写日期！')
        return;
      }
      this.dataForm.startTime = dateToString(this.value1[0]).substring(0, 10)
      this.dataForm.endTime = dateToString(this.value1[1]).substring(0, 10)
      getOneDay(Object.assign(this.dataForm, {
        current: this.pageIndex,
        size: this.pageSize
      })).then(response => {
        console.log(response.data.data)
        this.dataList = response.data.data.records
        this.totalPage = response.data.data.total
      })
      this.dataListLoading = false
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
    resetForm() {
      this.dataForm = {};
      this.getDataList();
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val
      this.getDataList()
    },
    // 新增 / 修改
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id)
      })
    },
    async exportExcel() {
      if (this.value1 == null || this.value1.length < 2) {
        this.$message.error('请填写日期！')
        return;
      }
      this.dataForm.startTime = dateToString(this.value1[0]).substring(0, 10)
      this.dataForm.endTime = dateToString(this.value1[1]).substring(0, 10)
      await getOneDayExcel('一日游统计表.xlsx', this.dataForm)
    }




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
}
/*
#feeI label {
  width: 10%;
}
#feeI .el-form-item__content {
  width: 90%;
} */
#feeRow {
  margin-left: 5%;
}
#fileFor label {
  width: 10%;
}
#fileFor span {
  border-right: black 0px solid;
}
#isCancel .el-form-item__content {
  width: 45%;
}
#shipNam .el-form-item__content {
  width: 60%;
}
#dept .el-form-item__content {
  width: 70%;
}
#cargoNam .el-form-item__content {
  width: 60%;
}
#startDate .el-form-item__content {
  width: 79%;
}
#signDte .el-form-item__content {
  width: 79%;
}
</style>

<style scoped lang="scss">
.table-html-wrap /deep/ table {
  // border-right: 1px solid #e8eaec;
  // border-bottom: 1px solid #e8eaec;
  border-collapse: collapse;
  margin: auto;
}

.table-html-wrap /deep/ table td {
  border-left: 1px solid black;
  border-bottom: 1px solid black;
  border-right: 1px solid black;
  white-space: nowrap;
  text-align: center;
  padding: 0.5rem;
}

.table-html-wrap /deep/ tbody tr:nth-of-type(1) {
  font-weight: bold;
  font-size: 20px;
  border: 0px solid white !important;
}
.table-html-wrap /deep/ tbody tr:nth-of-type(1) td {
  border: 0px solid white !important;
}
.table-html-wrap /deep/ tbody tr:nth-of-type(2) {
  border: 0px solid white !important;
}
.table-html-wrap /deep/ tbody tr:nth-of-type(2) td {
  text-align: right !important;
  border: 0px solid white !important;
  border-bottom: 1px solid black !important;
}
</style>
