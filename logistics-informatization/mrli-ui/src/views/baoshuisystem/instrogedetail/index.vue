<template>
  <div class="mod-config">
    <basic-container>
      <el-form
        :inline="true"
        :model="dataForm"
        ref="dataForm"
        @keyup.enter.native="getDataList()"
      >
        <el-form-item label="货票编号" prop="bh" hidden="hidden">
          <el-input v-model="dataForm.bh" placeholder="货票编号"></el-input>
        </el-form-item>
        <el-form-item label="班组" prop="bz">
          <el-input v-model="dataForm.bz" placeholder="班组"></el-input>
        </el-form-item>
        <el-form-item label="作业委托人" prop="sqdw">
          <el-input v-model="dataForm.sqdw" placeholder="作业委托人"></el-input>
        </el-form-item>
        <el-form-item label="船名" prop="cm">
          <el-input v-model="dataForm.cm" placeholder="船名"></el-input>
        </el-form-item>
        <el-form-item label="货名" prop="hm">
          <el-input v-model="dataForm.hm" placeholder="货名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getDataList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="resetForm('dataForm')"
            >重置</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addOrUpdateHandle('dataForm')"
            >手动上图</el-button
          >
        </el-form-item>
      </el-form>

      <div class="avue-crud">
        <el-table
          :data="dataList"
          border
          height="400"
          v-loading="dataListLoading"
          ref="singleTable"
          @expand-change="getBcmxList"
        >
          <el-table-column
            prop="unitCod"
            header-align="center"
            align="center"
            label="单位代码"
          >
          </el-table-column>
          <el-table-column
            prop="sqdw"
            header-align="center"
            align="center"
            label="申请单位"
            width="200px"
          >
          </el-table-column>
          <el-table-column
            prop="bz"
            header-align="center"
            align="center"
            label="班次"
          >
          </el-table-column>

          <el-table-column
            prop="bh"
            header-align="center"
            align="center"
            label="货票编号"
          >
          </el-table-column>

          <!-- <el-table-column
            prop="cbh"
            header-align="center"
            align="center"
            label="船号"
          >
          </el-table-column> -->
          <el-table-column
            prop="cm"
            header-align="center"
            align="center"
            label="船名"
          >
          </el-table-column>
          <el-table-column
            prop="tdh"
            header-align="center"
            align="center"
            label="提单号"
            width="100px"
          >
          </el-table-column>
          <!-- <el-table-column
            prop="jck"
            header-align="center"
            align="center"
            label="进出口"
          >
          </el-table-column>
          <el-table-column
            prop="nwm"
            header-align="center"
            align="center"
            label="内外贸"
          >
          </el-table-column> -->

          <!-- <el-table-column
            prop="zykq"
            header-align="center"
            align="center"
            label="库场号"
            width="100px"
          >
          </el-table-column> -->

          <el-table-column
            prop="strogeName"
            header-align="center"
            align="center"
            label="库场名称"
          >
          </el-table-column>
          <el-table-column
            prop="locationNo"
            header-align="center"
            align="center"
            label="货位号"
          >
          </el-table-column>
          <el-table-column
            prop="agentCod"
            header-align="center"
            align="center"
            label="代理委托人代码"
          >
          </el-table-column>
          <el-table-column
            prop="agentNam"
            header-align="center"
            align="center"
            label="代理委托人"
            width="200px"
          >
          </el-table-column>

          <el-table-column
            prop="cargoNo"
            header-align="center"
            align="center"
            label="货号"
          >
          </el-table-column>
          <el-table-column
            prop="cargoKindCod"
            header-align="center"
            align="center"
            label="货类代码"
          >
          </el-table-column>
          <el-table-column
            prop="hl"
            header-align="center"
            align="center"
            label="货类名称"
            width="200px"
          >
          </el-table-column>
          <el-table-column
            prop="hm"
            header-align="center"
            align="center"
            label="货名代码"
          >
          </el-table-column>
          <el-table-column
            prop="cargoNam"
            header-align="center"
            align="center"
            label="货名"
          >
          </el-table-column>
          <el-table-column
            prop="packingCode"
            header-align="center"
            align="center"
            label="包装代码"
          >
          </el-table-column>
          <el-table-column
            prop="package"
            header-align="center"
            align="center"
            label="包装"
          >
          </el-table-column>
          <el-table-column
            prop="specification"
            header-align="center"
            align="center"
            label="规格型号"
          >
          </el-table-column>
          <el-table-column
            prop="jianshu"
            header-align="center"
            align="center"
            label="件数"
          >
          </el-table-column>
          <el-table-column
            prop="piecesWgt"
            header-align="center"
            align="center"
            label="件重"
          >
          </el-table-column>
          <el-table-column
            prop="volume"
            header-align="center"
            align="center"
            label="体积"
          >
          </el-table-column>
          <!-- <el-table-column
            prop="stackingFeeFlag"
            header-align="center"
            align="center"
            label="堆存费统计标志"
            width="100px"
          >
          </el-table-column> -->
          <!-- <el-table-column
            prop="ifendId"
            header-align="center"
            align="center"
            label="完标"
          >
          </el-table-column> -->
          <el-table-column
            prop="operator"
            header-align="center"
            align="center"
            label="操作员"
          >
          </el-table-column>
          <el-table-column
            prop="recordTim"
            header-align="center"
            align="center"
            label="记录时间"
          >
          </el-table-column>
          <el-table-column
            prop="hz"
            header-align="center"
            align="center"
            label="重量"
          >
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
                @click="upImageDialogVisible(scope.row)"
                v-if="scope.row.contractNo == null"
                >上图</el-button
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

      <el-dialog title="合同选择" :visible.sync="dialogFormVisible">
        <el-form
          :model="upImageForm"
          :rules="upImageRules"
          ref="upImageForm"
          label-position="top"
        >
          <el-form-item label="合同编号" prop="contractNo">
            <el-select
              v-model="upImageForm.contractNo"
              style="width: 100%"
              filterable
              placeholder="请选择"
            >
              <el-option
                v-for="(item, index) in contractOptions"
                :key="index"
                :label="item.contNo"
                :value="item.contNo"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="入库件数" prop="jianshu">
            <el-input
              v-model.NUMBER="upImageForm.jianshu"
              placeholder="入库件数"
            ></el-input>
          </el-form-item>

          <el-form-item label="入库日期" prop="inStrogeDate">
            <el-date-picker
              v-model="upImageForm.inStrogeDate"
              type="date"
              placeholder="选择日期"
              style="width: 100%"
              value-format="yyyy-MM-dd"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="过磅重量" prop="gbhz">
            <el-input v-model="upImageForm.gbhz" placeholder="货重"></el-input>
          </el-form-item>
          <el-form-item label="报关重量" prop="bghz">
            <el-input
              v-model="upImageForm.bghz"
              placeholder="货重"
              style="width: 40%"
            ></el-input>
            <el-input
              v-model="upImageForm.jqbgdh"
              placeholder="进区报关单号"
              style="width: 40%; margin-left: 1%"
            ></el-input>
            <el-button
              @click="getInoutHeadBody(upImageForm.jqbgdh)"
              type="primary"
              style="margin-left: 1%"
              >查询报关重量</el-button
            >
          </el-form-item>
          <el-form-item label="堆存地点" prop="zykq">
            <el-select
              v-model="upImageForm.zykq"
              style="width: 100%"
              filterable
              placeholder="请选择"
            >
              <el-option
                v-for="(item, index) in yardNoOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="入库方式" prop="inStrogeMethod">
            <el-select
              v-model="upImageForm.inStrogeMethod"
              style="width: 100%"
              filterable
              placeholder="请选择"
            >
              <el-option
                v-for="(item, index) in inyardTypeOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="selectClick(upImageForm.gbhz)"
            >过磅重量上图</el-button
          >
          <el-button type="primary" @click="selectClick(upImageForm.bghz)"
            >报关重量上图</el-button
          >
        </div>
      </el-dialog>

      <el-dialog
        title="上图"
        :close-on-click-modal="false"
        :visible="upImageVisible"
        width="80%"
        append-to-body
        @close="closeUpImageDialog"
      >
        <el-form :inline="true" :model="bcmcxForm" ref="bcmcxForm">
          <el-form-item label="班组" prop="bz">
            <el-select
              v-model="bcmcxForm.bz"
              style="width: 100%"
              filterable
              placeholder="请选择"
            >
              <el-option
                v-for="(item, index) in portRqcbsOption"
                :key="index"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="称别组" prop="cbs">
            <el-select
              v-model="bcmcxForm.cbs"
              style="width: 100%"
              filterable
              placeholder="请选择"
            >
              <el-option
                v-for="(item, index) in cbzOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="日期" prop="cbsDate" class="searchClass">
            <el-date-picker
              v-model="bcmcxForm.cbsDate"
              type="date"
              placeholder="选择日期"
              value-format="yyMMdd"
              style="width: 94%"
            >
            </el-date-picker>
          </el-form-item>

          <el-form-item>
            <el-button
              @click="searchBcmcx"
              type="primary"
              style="float: left; margin-bottom: 10px"
              >查询</el-button
            ></el-form-item
          >
        </el-form>
        <el-button
          type="primary"
          style="float: left; margin-bottom: 10px"
          @click="upImage(thisRow)"
          :disabled="upImageSingle"
          >上图</el-button
        >
        <div style="float: right">货重：{{ totalhz }} 件数：{{ totaljs }}</div>
        <el-table
          :data="bcmcxDataList"
          border
          height="400"
          v-loading="dataListLoading"
          ref="bcmcxTable"
          @selection-change="handleSelectionChange"
          :summary-method="getSummaries"
          show-summary
        >
          <el-table-column type="selection" width="55"> </el-table-column>

          <el-table-column
            prop="ch"
            header-align="center"
            align="center"
            label="车牌号"
          >
          </el-table-column>
          <el-table-column
            prop="bh"
            header-align="center"
            align="center"
            label="货票编号"
            width="150px"
          >
          </el-table-column>
          <el-table-column
            prop="hz"
            header-align="center"
            align="center"
            label="货重"
          >
          </el-table-column>
          <el-table-column
            prop="jianshu"
            header-align="center"
            align="center"
            label="件数"
          >
          </el-table-column>
          <el-table-column
            prop="cbs"
            header-align="center"
            align="center"
            label="称别组"
          >
          </el-table-column>
          <el-table-column
            prop="liushuihao"
            header-align="center"
            align="center"
            label="流水号"
          >
          </el-table-column>
          <el-table-column
            prop="rqcbs"
            header-align="center"
            align="center"
            label="班次"
          >
          </el-table-column>
          <el-table-column
            prop="zcsj"
            header-align="center"
            align="center"
            label="重车时间"
          >
          </el-table-column>
          <el-table-column
            prop="kcsj"
            header-align="center"
            align="center"
            label="空车时间"
          >
          </el-table-column>
        </el-table>
      </el-dialog>
      <!-- 弹窗, 新增 / 修改 -->
      <table-form v-if="addOrUpdateVisible" ref="addOrUpdate"></table-form>
    </basic-container>
  </div>
</template>

<script>
import { fetchList, delObj, upImage, getInyardType, instrfetchList, getBcmcx } from '@/api/baoshuisystem/instrogedetail'
import { getSelectContract, getEffectiveInfo } from '@/api/baoshuisystem/contract'
import { getInoutHeadBody } from '@/api/baoshuisystem/inoutyardhead'
import { getDepositPlace, getportRqcbs, getPortCbz } from '@/api/baoshuisystem/getdictdata'
import { dateFormat } from "@/util/date";
import TableForm from './ioyard-form'
import { mapGetters } from 'vuex'
import XEUtils from 'xe-utils'
export default {
  data() {
    return {
      dataForm: {
        key: '',
        bh: '',
        inoutYardNo: '',
        ioyardId: '1',
      },
      bcmcxForm: {
        key: '',
        params: { recordDte: '' },
        bz: ''
      },
      upImageForm: {},
      dataList: [],
      bcmcxDataList: [],
      bcmcxDataListSave: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      addOrUpdateVisible: false,
      dialogFormVisible: false,
      upImageVisible: false,
      upImageSingle: true,
      addOrUpdateVisible: false,
      contractOptions: [],
      inyardTypeOptions: [],
      multipleSelection: [],
      yardNoOptions: [],
      portRqcbsOption: [],
      cbzOptions: [],
      weightUnitOptions: [],
      thisRow: {},
      totalhz: 0,
      totaljs: 0,
      index: 0,
      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
      upImageRules: {
        contractNo: [
          { required: true, message: '合同编号不能为空', trigger: 'blur' }
        ],
        jianshu: [
          { required: true, message: '入库件数不能为空', trigger: 'blur' },
          {
            validator(rule, value, callback) {
              if (!isNaN(Number(value))) {
                callback()
              } else {
                callback(new Error('请输入数字'))
              }
            },
            trigger: 'blur'
          }
        ],
        hz: [
          { required: true, message: '货重不能为空', trigger: 'blur' },
          {
            validator(rule, value, callback) {
              if (XEUtils.isFloat(value) || !isNaN(Number(value))) {

                callback()
              } else {
                callback(new Error('请输入数字'))
              }
            },
            trigger: 'blur'
          }
        ],
        inStrogeMethod: [
          { required: true, message: '入库方式不能为空', trigger: 'blur' }
        ],
      },
      formLabelWidth: '120px'
    }
  },
  components: {
    TableForm
  },
  created() {
    // this.getDataList()
  },
  updated() {
    this.$nextTick(() => {
      if (this.$refs.bcmcxTable != undefined) {
        this.$refs.bcmcxTable.doLayout()
      }

    })
  },
  computed: {
    ...mapGetters(['permissions'])
  },
  methods: {
    // 获取数据列表
    getDataList() {

      this.dataListLoading = true
      this.upImageForm = {
        bh: '',
        inoutYardNo: '',
        gbhz: '',
        bghz: '',
        zykq: ''
      }
      instrfetchList(Object.assign({
        current: this.pageIndex,
        size: this.pageSize,

      }, this.dataForm)).then(response => {
        this.dataList = response.data.data.records
        this.totalPage = response.data.data.total
        this.dataListLoading = false
      })
      getEffectiveInfo().then(data => {
        this.contractOptions = data.data.data
      })
      getInyardType().then(data => {
        this.inyardTypeOptions = data.data.data
      })
      getportRqcbs().then(data => {
        this.portRqcbsOption = data.data.data
      })
      getPortCbz().then(data => {
        this.cbzOptions = data.data.data
      })

    },
    // 新增 / 修改
    addOrUpdateHandle() {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init()
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
    upImage(thisRow) {
      this.dataListLoading = false

      var hz = 0;
      var jianshu = 0;
      for (var i in this.multipleSelection) {
        jianshu += parseInt((this.multipleSelection[i].jianshu == '' || this.multipleSelection[i].jianshu == undefined) ? 0 : this.multipleSelection[i].jianshu)
        hz += parseFloat((this.multipleSelection[i].hz == '' || this.multipleSelection[i].hz == undefined) ? 0 : this.multipleSelection[i].hz)
      }
      if (!isNaN(parseFloat(hz))) {
        hz = hz.toFixed(2);
      }
      getDepositPlace().then(resp => {
        this.yardNoOptions = resp.data.data
      })
      this.dialogFormVisible = true
      this.$set(this.upImageForm, "bh", thisRow.bh)
      this.$set(this.upImageForm, "inoutYardNo", thisRow.inoutYardNo)
      this.$set(this.upImageForm, "jianshu", jianshu)
      this.$set(this.upImageForm, "gbhz", hz)
      var selDate = dateFormat(new Date())
      if (this.bcmcxForm.cbsDate != null && this.bcmcxForm.cbsDate != '') {
        var selDate = '20' + this.bcmcxForm.cbsDate.substring(0, 2) + '-' + this.bcmcxForm.cbsDate.substring(2, 4) + '-' + this.bcmcxForm.cbsDate.substring(4, 6) + ' 00:00:00'
      }
      console.log(selDate)
      this.$set(this.upImageForm, "inStrogeDate", selDate)
    },

    selectClick(hz) {
      console.log("form", this.upImageForm)
      this.upImageForm.hz = hz
      this.$refs['upImageForm'].validate((valid) => {
        if (valid) {
          this.dataListLoading = true
          this.upImageForm.bcmcxtabList = ''
          this.upImageForm.bcmcxtabList = JSON.stringify(this.multipleSelection)

          upImage(this.upImageForm).then(data => {

            this.$notify.success(data.data.data.data)
            this.dialogFormVisible = false
            this.upImageVisible = false
            this.dataListLoading = false
            this.bcmcxForm.bz = ''
            this.bcmcxForm.params = { recordDte: '' }
            this.$set(this.dataForm, 'value', '')
            this.getDataList()
            this.thisRow = {}
          })
        }
      })

    },
    resetForm(formName) {
      if (this.$refs[formName] !== undefined) {
        this.$refs[formName].resetFields();
        // this.getDataList();
      }
      this.setCurrent();
    },
    setCurrent(row) {
      this.$nextTick(() => {
        this.$refs.singleTable.setCurrentRow(row);
      });

    },
    getBcmxList(row) {
      var that = this
      this.bcmcxDataList = []
      getBcmcx(row.bh).then(response => {
        this.bcmcxDataListSave = response.data.data
        for (var i in response.data.data) {
          this.bcmcxDataList[i] = { ch: '', bh: '', hz: '', jianshu: '' }
          that.$set(this.bcmcxDataList, i, response.data.data[i])
        }

      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      this.totalhz = 0
      this.totaljs = 0
      if (this.multipleSelection == undefined || this.multipleSelection == '' || this.multipleSelection.length == 0) {
        this.upImageSingle = true
      } else {
        this.upImageSingle = false
      }
      for (var i in this.multipleSelection) {
        this.totalhz = parseFloat(this.totalhz) + parseFloat(this.multipleSelection[i].hz)
        this.totaljs += parseFloat((this.multipleSelection[i].jianshu == '' || this.multipleSelection[i].jianshu == undefined) ? 0 : this.multipleSelection[i].jianshu)
      }
      if (!isNaN(parseFloat(this.totalhz))) {
        this.totalhz = this.totalhz.toFixed(2);
      }
    },
    searchBcmcx() {
      var bcmcxData = []
      this.bcmcxDataListSave.forEach(element => {
        bcmcxData.push(element)
      });
      var i = bcmcxData.length;
      while (i--) {
        var bz = bcmcxData[i].rqcbs.substring(6, 7)
        var date = bcmcxData[i].rqcbs.substring(0, 6)
        console.log("---", date)
        if (this.bcmcxForm.bz != undefined && this.bcmcxForm.bz.trim() != '' && bz.indexOf(this.bcmcxForm.bz) == -1) {
          bcmcxData.splice(i, 1);
          continue
        }
        if (this.bcmcxForm.cbs != undefined && this.bcmcxForm.cbs != '' && bcmcxData[i].cbs.indexOf(this.bcmcxForm.cbs) == -1) {
          bcmcxData.splice(i, 1);
          continue
        }
        if (this.bcmcxForm.cbsDate != undefined && this.bcmcxForm.cbsDate != '' && date.indexOf(this.bcmcxForm.cbsDate) == -1) {
          bcmcxData.splice(i, 1);
          continue
        }
        // if (this.bcmcxForm.params.recordDte != '' && this.bcmcxForm.params.recordDte != undefined) {
        //   if (this.bcmcxForm.params.recordDte[0] > bcmcxData[i].zcsj || this.bcmcxForm.params.recordDte[1] < bcmcxData[i].zcsj) {
        //     bcmcxData.splice(i, 1);

        //   }
        // }
      }
      this.bcmcxDataList = bcmcxData
    },
    upImageDialogVisible(row) {
      this.upImageVisible = true
      var that = this
      this.bcmcxDataList = []
      this.thisRow = row
      getBcmcx(row.bh).then(response => {
        this.bcmcxDataListSave = response.data.data
        for (var i in response.data.data) {
          this.bcmcxDataList[i] = { ch: '', bh: '', hz: '', jianshu: '' }
          that.$set(this.bcmcxDataList, i, response.data.data[i])
        }

      })
    },
    closeUpImageDialog() {
      this.bcmcxForm.bz = ''
      this.bcmcxForm.params = { recordDte: '' }
      this.upImageVisible = false
    },
    getInoutHeadBody(jqbgdh) {
      var map = { inCustomsDeclaration: jqbgdh, inOut: 1 }
      getInoutHeadBody(map).then(resp => {
        console.log("resp", resp)
        if (resp.data.data.length > 0) {
          var bgzl = 0
          for (var i in resp.data.data) {
            bgzl += parseFloat(resp.data.data[i].roughWet == null ? 0 : resp.data.data[i].roughWet)
          }
          this.$set(this.upImageForm, "bghz", bgzl)

        } else {
          this.$alert('未查询到此入库单', '提示', {
            confirmButtonText: '确定',
            callback: action => {
              this.$message({
                type: 'info',
                message: `action: ${action}`
              });
            }
          });
        }
      })
    },
    getSummaries(param) {
      const { columns, data } = param;
      const sums = [];
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '合计';
          return;
        }
        const values = data.map(item => Number(item[column.property]));
        if (!values.every(value => isNaN(value)) && (column.property == 'hz' || column.property == 'jianshu')) {
          sums[index] = values.reduce((prev, curr) => {
            const value = Number(curr);
            if (!isNaN(value)) {
              return (prev + curr);
            } else {
              return prev;
            }
          }, 0);
          sums[index] += ' ';
        } else {
          sums[index] = '';
        }
      });
      for (var i in sums) {
        if (!isNaN(Number(sums[i])) && sums[i] != '') {
          sums[i] = Number(sums[i].trim()).toFixed(2)
          console.log("i", sums[i])
        }
      }
      console.log(sums)
      return sums;
    },
  }
}
</script>
