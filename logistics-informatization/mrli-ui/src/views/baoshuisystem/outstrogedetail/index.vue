<template>
  <div class="mod-config">
    <basic-container>
      <el-form
        :inline="true"
        :model="dataForm"
        ref="dataForm"
        @keyup.enter.native="getDataList()"
      >
        <el-form-item label="货票号" prop="bh">
          <el-input v-model="dataForm.bh" placeholder="货票号"></el-input>
        </el-form-item>

        <el-form-item label="班组" prop="bz">
          <el-input v-model="dataForm.bz" placeholder="班组"></el-input>
        </el-form-item>
        <el-form-item label="作业委托人" prop="sqdw">
          <el-select
            v-model="dataForm.sqdw"
            placeholder="请选择"
            filterable
            clearable
          >
            <el-option
              v-for="(item, index) in conSignList"
              :key="index"
              :label="item.consignNam"
              :value="item.consignNam"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="船名" prop="cm">
          <el-select
            v-model="dataForm.cm"
            placeholder="请选择"
            filterable
            clearable
          >
            <el-option
              v-for="(item, index) in shipList"
              :key="index"
              :label="item.shipNam"
              :value="item.shipNam"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="货名" prop="hm">
          <el-select
            v-model="dataForm.hm"
            placeholder="请选择"
            filterable
            clearable
          >
            <el-option
              v-for="(item, index) in cargoList"
              :key="index"
              :label="item.cargoNam"
              :value="item.cargoNam"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="提单号" prop="billNO">
          <el-select
            v-model="dataForm.billNO"
            placeholder="请选择"
            filterable
            clearable
          >
            <el-option
              v-for="(item, index) in billNOList"
              :key="index"
              :label="item.billNo"
              :value="item.billNo"
            >
            </el-option>
          </el-select>
        </el-form-item>
           <el-form-item label="报关单号" prop="customsDeclaration">
          <el-select
            v-model="dataForm.customsDeclaration"
            placeholder="请选择"
            filterable
            clearable
          >
            <el-option
              v-for="(item, index) in customsDeclarationList"
              :key="index"
              :label="item.customsDeclaration"
              :value="item.customsDeclaration"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getDataList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="resetForm('dataForm')"
            >重置</el-button
          >
        </el-form-item>
      </el-form>

      <div class="avue-crud">
        <el-table
          :data="dataList"
          border
          height="500"
          v-loading="dataListLoading"
          ref="singleTable"
          :summary-method="getSummaries"
          show-summary
        >
          <!-- <el-table-column
            prop="unitCod"
            header-align="center"
            align="center"
            label="单位代码"
          >
          </el-table-column> -->
          <el-table-column
            prop="ioyardDte"
            header-align="center"
            align="center"
            label="入库日期"
          >
          </el-table-column>

          <el-table-column
            prop="settlementDtePre"
            header-align="center"
            align="center"
            label="预出库日期"
          >
          </el-table-column>

          <el-table-column
            prop="settlementDtePre"
            header-align="center"
            align="center"
            :formatter="settlementDtePreFormatter"
            label="最新结算起始日期"
          >
          </el-table-column>
          <!-- <el-table-column
            prop="inoutYardNo"
            header-align="center"
            align="center"
            label="入库单号"
          >
          </el-table-column> -->
          <el-table-column
            prop="contNo"
            header-align="center"
            align="center"
            label="合同编号"
          >
          </el-table-column>
          <el-table-column
            prop="shipNam"
            header-align="center"
            align="center"
            label="船名"
          >
          </el-table-column>
          <el-table-column
            prop="billNo"
            header-align="center"
            align="center"
            label="提单号"
          >
          </el-table-column>
          <el-table-column
            prop="customsDeclaration"
            header-align="center"
            align="center"
            label="报关单号"
          >
          </el-table-column>
          <el-table-column
            prop="inoutId"
            header-align="center"
            align="center"
            label="进出口"
            :formatter="inoutIdFormatter"
          >
          </el-table-column>
          <el-table-column
            prop="tradeId"
            header-align="center"
            align="center"
            label="内外贸"
            :formatter="tradeIdFormatter"
          >
          </el-table-column>
          <el-table-column
            prop="inYardWay"
            header-align="center"
            align="center"
            label="入库方式"
            :formatter="inYardWayFormatter"
          >
          </el-table-column>
          <el-table-column
            prop="consignNam"
            header-align="center"
            align="center"
            label="代理委托人"
          >
          </el-table-column>
          <el-table-column
            prop="cargoKindNam"
            header-align="center"
            align="center"
            label="货类"
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
            prop="cPkgKindNam"
            header-align="center"
            align="center"
            label="包装"
          >
          </el-table-column>
          <el-table-column
            prop="piecesNum"
            header-align="center"
            align="center"
            label="件数"
          >
          </el-table-column>
          <el-table-column
            prop="weightWgt"
            header-align="center"
            align="center"
            label="完成量"
          >
          </el-table-column>
          <el-table-column
            prop="surplusPieceNum"
            header-align="center"
            align="center"
            label="剩余件数"
          >
          </el-table-column>
          <el-table-column
            prop="surplusWeight"
            header-align="center"
            align="center"
            label="剩余量"
          >
          </el-table-column>
          settlement_dte_later
           <el-table-column
            prop="settlementDteLater"
            header-align="center"
            align="center"
            label="上次预出库日期"
          >
          </el-table-column>
          <el-table-column
            prop="overShortFlag"
            header-align="center"
            align="center"
            label="货溢短量状态"
            :formatter="overShortFlagFormatter"
          >
          </el-table-column>
           <el-table-column
            header-align="center"
            align="center"
            label="审核类型"
          >
          <template slot-scope="scope" >
              {{getStatus(scope.row.overShortType)}}
          </template>

          </el-table-column>
          <el-table-column
            header-align="center"
            align="center"
            label="操作"
            fixed="right"
            width="200px"
          >
            <template slot-scope="scope">

              <el-button
                type="text"
                size="small"
                icon="el-icon-edit"
                @click="destroyImage(scope.row, 0)"
                >销图</el-button
              >
              <el-button
                type="text"
                size="small"
                icon="el-icon-s-unfold"
                @click="destroyImage(scope.row, 1)"
                >预出库</el-button
              >
             <!-- <el-button
                type="text"
                size="small"
                icon="el-icon-caret-right"
                @click="overShortCheck(scope.row)"
                v-if="
                  permissions.submit_over_short_check &&
                  ((scope.row.overShortFlag == 0 ||
                    scope.row.overShortFlag == 3 ||
                    scope.row.overShortFlag == '' |
                    scope.row.overShortFlag == undefined)&&(
                    scope.row.overShortTime == undefined ||
                    scope.row.overShortTime == 0))
                "
                >提交货溢短量审核</el-button
              > -->
              <el-button
                type="text"
                size="small"
                icon="el-icon-caret-right"
                @click="overShortCheck(scope.row)"
                v-if="
                  permissions.submit_over_short_check &&
                  (scope.row.overShortFlag != 1 &&scope.row.overShortTime != 1)"
                >提交货溢短量审核</el-button
              >

              <el-button
                type="text"
                size="small"
                icon="el-icon-caret-right"
                @click="doOverShort(scope.row)"
                v-if="
                  permissions.submit_over_short_check &&
                  (scope.row.overShortFlag == 2 && scope.row.overShortType == 1)
                "
                >短量处理</el-button
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

      <el-dialog
        :title="dialogTitle == 0 ? '销图' : '预出库'"
        :visible.sync="dialogFormVisible"
      >
        <el-form
          :model="destroyForm"
          :rules="destroyRules"
          ref="destroyForm"
          label-position="top"
        >
          <el-form-item label="磅单编号">
            <el-input
              v-model="destroyForm.bh"
              placeholder="磅单编号"
            ></el-input>
            <el-button type="primary" @click="searchHpmx(destroyForm.bh)"
              >查询</el-button
            >
          </el-form-item>
          <el-form-item label="入库号" prop="inyardNo" hidden="hidden">
            <el-input
              v-model="destroyForm.inyardNo"
              placeholder="入库号"
            ></el-input>
          </el-form-item>
          <el-form-item label="班次" prop="bz">
            <el-input v-model="destroyForm.bz" placeholder="班次"></el-input>
          </el-form-item>

          <el-form-item label="过磅重量" prop="gbhz">
            <el-input v-model="destroyForm.gbhz" placeholder="重量"></el-input>
          </el-form-item>
          <el-form-item label="报关重量" prop="bghz">
            <el-input
              v-model="destroyForm.bghz"
              placeholder="报关重量"
              style="width: 40%"
            ></el-input>
            <el-input
              v-model="destroyForm.jqbgdh"
              placeholder="进区报关单号"
              style="width: 40%; margin-left: 1%"
            ></el-input>
            <el-button
              @click="getInoutHeadBody(destroyForm.jqbgdh)"
              type="primary"
              style="margin-left: 1%"
              >查询报关重量</el-button
            >
          </el-form-item>
          <el-form-item label="出库件数" prop="jianshu" :rules="numberRule">
            <el-input
              v-model="destroyForm.jianshu"
              placeholder="出库件数"
            ></el-input>
          </el-form-item>
          <el-form-item label="出库日期" prop="inStrogeDate">
            <el-date-picker
              v-model="destroyForm.inStrogeDate"
              type="date"
              placeholder="选择日期"
              style="width: 100%"
              value-format="yyyy-MM-dd"
            >
            </el-date-picker>
          </el-form-item>

          <el-form-item label="堆存地点" prop="yardNo">
            <el-input
              v-model="destroyForm.yardNo"
              placeholder="堆存地点"
              disabled
            ></el-input>
          </el-form-item>
          <el-form-item label="出库方式" prop="inStrogeMethod">
            <el-select
              v-model="destroyForm.inStrogeMethod"
              style="width: 100%"
              filterable
              placeholder="请选择"
            >
              <el-option
                v-for="(item, index) in outyardTypeOptions"
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
          <el-button type="primary" @click="selectClick(destroyForm.gbhz)"
            >过磅重量销图</el-button
          >
          <el-button type="primary" @click="selectClick(destroyForm.bghz)"
            >报关重量销图</el-button
          >
        </div>
      </el-dialog>

      <el-dialog
        title="选择货管审核人"
        :visible.sync="overShortCheckDialogVisible"
        width="25%"
      >
        <el-form
          :model="checkUserForm"
          ref="checkUserForm"
          label-position="top"
        >
          <el-select
            v-model="checkUserForm.username"
            style="width: 100%"
            filterable
            placeholder="请选择"
            @change="selChange(index)"
          >
            <el-option
              v-for="(item, index) in checkUserOptions"
              :key="index"
              :label="item.manName"
              :value="item.username"
            >
            </el-option>
          </el-select>
          <el-form-item label="审核类型">
            <el-select
            v-model="checkUserForm.overShortType"
            style="width: 100%"
            filterable
            placeholder="请选择"
            @change="selChange(index)"
          >
            <el-option
              v-for="item in overQuantityOptions"
              :key="item.id"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
          </el-form-item>


          <el-form-item
            label="附件"
            prop="urlFile"
            v-loading="fileLoading"
            element-loading-text="文件上传中"
            element-loading-spinner="el-icon-loading"
          >
            <el-upload
              :headers="headers"
              :show-file-list="false"
              :on-success="uploadSuccess"
              class="avatar-uploader"
              action="/baoshuisystem/sys-file/upload"
              :on-progress="onProgress"
            >
              <el-button size="small" type="primary">点击上传</el-button>
            </el-upload>
            <ul id="example-1">
              <li v-for="(item, index) in fileOptions" :key="index">
                <el-row>
                  <el-col style="word-wrap: break-word" :span="12">
                    <span
                      @click="downloadFile(index)"
                      style="cursor: pointer"
                      >{{ item.originalFilename }}</span
                    >
                  </el-col>
                  <el-col :span="12">
                    <i
                      class="el-icon-delete"
                      style="
                        float: right;
                        margin-right: 50%;
                        height: 32px;
                        line-height: 32px;
                      "
                      @click="deleteFile(index)"
                    ></i>
                  </el-col>
                </el-row>
              </li>
            </ul>
          </el-form-item>
          <el-button
            @click="submitCheck"
            type="primary"
            style="margin-left: 75%; margin-top: 1%"
            >提交审核</el-button
          >
        </el-form>
      </el-dialog>
      <!-- 弹窗, 新增 / 修改 -->
    </basic-container>
  </div>
</template>

<script>
import { fetchList, submitOverShortCheck, upImage, destroyImage, getOne, getOutyardType,getOverShortMax } from '@/api/baoshuisystem/instrogedetail'
import { getSelectContract } from '@/api/baoshuisystem/contract'
import { getInoutIdDic, getTradeIdDic, getInyardType, getOverShortFlag } from '@/api/baoshuisystem/getdictdata'
import { getInoutHeadBody } from '@/api/baoshuisystem/inoutyardhead'
import { getApprovalProcessList } from '@/api/baoshuisystem/approvalprocess'
import { selectAllProcessUser } from '@/api/baoshuisystem/approvalprocessuser'
import { delSysFile } from '@/api/baoshuisystem/upload-file'
import { putObj,getSelectCondition } from '@/api/baoshuisystem/ioyard'
import { mapGetters } from 'vuex'
import { dateFormat } from "@/util/date";
import { getOverQuantity } from "@/api/baoshuisystem/getdictdata";
import {
  handleDown, delFile, fileSave, fileUpdate
} from '@/api/baoshuisystem/contract'
import store from '@/store'
import XEUtils from 'xe-utils'
import { Row } from 'element-ui'
export default {
  data() {
    return {
      headers: {
        'Authorization': 'Bearer ' + store.getters.access_token,
      },
      dataForm: {
        key: '',
        ioyardId: '0',
        bh: '',
        inoutYardNo: '',
        customsDeclaration:'',
        billNO:'',
      },
      destroyForm: {},
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      addOrUpdateVisible: false,
      dialogFormVisible: false,
      contractOptions: [],
      outStrogeForm: {},
      outyardTypeOptions: [],
      inoutIdOptions: [],
      tardeIdOptions: [],
      overQuantityOptions:[],
      inyardTypeOptions: [],
      overShortFlagOptions: [],
      fileLoading: false,
      prepareFlag: 0,
      dialogTitle: 0,
      shipList: [],
      cargoList: [],
      conSignList: [],
      contNoList: [],
      countList: [],
      billNOList: [],
      customsDeclarationList: [],
      overShortMax:0,
      index: 0,
      overShortCheckDialogVisible: false,
      checkUserOptions: [],
      fileOptions: [],
      fileList: [],
      checkUserForm: { username: "" },
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
      formLabelWidth: '120px',
      destroyRules: {

      },
      numberRule: [{
        validator(rule, value, callback) {
          if (!isNaN(Number(value))) {
            callback()
          } else {
            callback(new Error('请输入数字'))
          }
        },
        trigger: 'blur'
      }]
    }
  },
  components: {

  },
  created() {
    this.getDataList()
    this.getSelectAll()
  },
  updated() {
    this.$nextTick(() => {
      this.$refs.singleTable.doLayout()
    })
  },
  computed: {
    ...mapGetters(['permissions'])
  },
  methods: {
    getStatus(type){
      if(type == "0"){
          return this.dataForm.overShortType = "货溢"
      }else if(type == "1"){
          return this.dataForm.overShortType = "短量"
      }else{
        return " "
      }
    },
    getSelectAll() {
      getSelectCondition().then(res => {
        this.contNoList = res.data.data.listCont
        this.shipList = res.data.data.listShip
        this.cargoList = res.data.data.listCargo
        this.conSignList = res.data.data.listConSign
        this.billNOList =  res.data.data.listBillNO
        this.customsDeclarationList = res.data.data.listCustomsDeclaration
      })
    },
    // 获取数据列表
    getDataList() {
      this.fileOptions = [];
      this.dataListLoading = true
      this.destroyForm = {
        bh: '',
        inyardNo: '',
        hz: '',
        bz: '',
        prepareFlag: '',
        gbhz: '',
        bghz: '',
        jqbgdh: ''
      }
      fetchList(Object.assign({
        current: this.pageIndex,
        size: this.pageSize,

      }, this.dataForm)).then(response => {
        this.dataList = response.data.data.records
        this.totalPage = response.data.data.total
      })
      getSelectContract().then(data => {
        this.contractOptions = data.data.data
      })
      getOutyardType().then(data => {
        this.outyardTypeOptions = data.data.data
      })
      getInoutIdDic().then(data => {
        this.inoutIdOptions = data.data.data
      })
      getTradeIdDic().then(data => {
        this.tardeIdOptions = data.data.data
      })
      getInyardType().then(data => {
        this.inyardTypeOptions = data.data.data
      })
      getOverShortFlag().then(data => {
        this.overShortFlagOptions = data.data.data
      })
      getOverQuantity().then(data =>{
        this.overQuantityOptions = data.data.data
      })
      getOverShortMax().then(res => this.overShortMax = res.data.data.paramValue)
      this.dataListLoading = false
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
    destroyImage(row, flag) {
      this.dataListLoading = false
      //this.destroyForm.bh = bh
      this.destroyForm.inoutYardNo = row.inoutYardNo
      this.destroyForm.gbhz = row.surplusWeight
      this.destroyForm.jianshu = row.surplusPieceNum
      // this.destroyForm.gbhz = row.piecesNum
      this.prepareFlag = flag
      this.dialogTitle = flag
      this.dialogFormVisible = true
      this.$set(this.destroyForm, "inStrogeDate", dateFormat(new Date()))
      this.$set(this.destroyForm, "yardNo", row.yardNo)
      this.$set(this.destroyForm, "surplusWeight", row.surplusWeight)
      this.$set(this.destroyForm, "overShortType", row.overShortType)
      this.$set(this.destroyForm, "overShortFlag", row.overShortFlag)
      this.$set(this.destroyForm, "overShortTimes", row.overShortTimes)
      this.$set(this.destroyForm, "weightWgt", row.weightWgt)

    },

    selectClick(hz) {
      //销图和预出库时判断出库量是否大于剩余量
       if( hz > this.destroyForm.surplusWeight){
        //大于则判断over_short_type是否为0并且over_short_flag为2并且over_times不为1为0，
        //货溢量不能超过设定的入库量的百分比
        if(this.destroyForm.overShortType == 0 &&
        this.destroyForm.overShortFlag == 2 &&
         this.destroyForm.overShortTimes == 0 &&
         hz<= XEUtils.add(this.destroyForm.surplusWeight,XEUtils.multiply(this.destroyForm.weightWgt,this.overShortMax)) ){
            this.destroyForm.overShortTimes = 1
        } else {
          this.$notify.error("未提交货溢审核或货溢审核未通过或货溢量超出设定值");
          return;
        }
      }

      if (this.prepareFlag == 1) {
        this.destroyForm.prepareFlag = 1
      } else {
        this.destroyForm.prepareFlag = ''
      }
      this.destroyForm.hz = hz
      this.$refs['destroyForm'].validate((valid) => {
        if (valid) {
          this.dataListLoading = true
          destroyImage(this.destroyForm).then(data => {
            var msg = data.data.data
            if (msg.msg == null) {
              this.$notify.success(msg.data)
            } else {
              this.$notify.error(msg.msg)
            }
            // this.$notify.success(msg)
            this.dialogFormVisible = false
            this.dataListLoading = false
            this.$set(this.dataForm, 'value', '')
            this.getDataList()
          })
        }
      })

    },
    resetForm(formName) {
      if (this.$refs[formName] !== undefined) {
        this.$refs[formName].resetFields();
        this.getDataList();
      }
      this.setCurrent();
    },
    setCurrent(row) {
      this.$nextTick(() => {
        this.$refs.singleTable.setCurrentRow(row);
      });

    },
    searchHpmx(bh) {
      getOne(bh).then(data => {
        this.destroyForm.bz = data.data.data.bz
        this.destroyForm.gbhz = data.data.data.hz
      })
    },
    inoutIdFormatter(row) {
      for (var i in this.inoutIdOptions) {
        if (row.inoutId == this.inoutIdOptions[i].value) {
          return this.inoutIdOptions[i].label
        }
      }
    },
    tradeIdFormatter(row) {
      for (var i in this.tardeIdOptions) {
        if (row.tradeId == this.tardeIdOptions[i].value) {
          return this.tardeIdOptions[i].label
        }
      }
    },
    inYardWayFormatter(row) {
      for (var i in this.inyardTypeOptions) {
        if (row.inYardWay == this.inyardTypeOptions[i].value) {
          return this.inyardTypeOptions[i].label
        }
      }
    },
    getInoutHeadBody(jqbgdh) {

      var map = { inCustomsDeclaration: jqbgdh, inOut: 0 }
      getInoutHeadBody(map).then(resp => {
        if (resp.data.data.length > 0) {
          var bgzl = 0
          for (var i in resp.data.data) {
            bgzl += parseFloat(resp.data.data[i].roughWet == null ? 0 : resp.data.data[i].roughWet)
          }
          this.$set(this.destroyForm, "bghz", bgzl)

        } else {
          this.$alert('未查询到此出库单', '提示', {
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
    overShortCheck(row) {
      this.checkUserForm = { username: "" }
      this.checkUserForm.billFid = row.inoutYardNo
      this.checkUserForm.processNo = "2"
      this.checkUserForm.fId = row.inoutYardNo
      this.overShortCheckDialogVisible = true
      var map = { processNo: '2' }
      getApprovalProcessList(map).then(resp => {
        var pFid = ""
        if (resp.data.data.length > 0) {
          for (var i in resp.data.data) {
            if (resp.data.data[i].orderBy == '1') {
              pFid = resp.data.data[i].fId
              this.checkUserForm.processFid = resp.data.data[i].fId
              this.checkUserForm.orderBy = '1'
              this.checkUserForm.linkNam = resp.data.data[i].linkNam
              this.checkUserForm.roleNam = resp.data.data[i].roleNam
              break
            }
          }
        }
        var map1 = { processFid: pFid }
        selectAllProcessUser(map1).then(resp1 => {
          this.checkUserOptions = resp1.data.data
        })
      })
    },
    selChange(index) {
      for (var i in this.checkUserOptions) {
        if (this.checkUserForm.username == this.checkUserOptions[i].username) {
          this.checkUserForm.manName = this.checkUserOptions[i].manName
          this.checkUserForm.userId = this.checkUserOptions[i].userId
        }
      }
    },
    submitCheck() {
      this.checkUserForm.isAgree = '0'
      if (this.checkUserForm.manName == undefined ||
      this.checkUserForm.manName == '' || this.checkUserForm.overShortType== undefined ||
      this.checkUserForm.overShortType==  '' ) {
        this.$notify.error("请选择审核人和审核类型")
        return
      }
      submitOverShortCheck(this.checkUserForm).then(resp => {
        for (var i in this.fileList) {
          this.fileList[i].tableName = 'ioyard'
          this.fileList[i].tableFid = this.checkUserForm.fId
        }
        var map = { tableName: 'ioyard', tableFid: this.checkUserForm.fId }
        delSysFile(map).then(resp => {
          fileSave(this.fileList).then(resp => {
            this.overShortCheckDialogVisible = false
            this.getDataList()
          })
          for (const key in this.feeList) {
            this.feeList[key].cFid = data.data.data.fId
          }
        })


      })
    },
    overShortFlagFormatter(row) {
      for (var i in this.overShortFlagOptions) {
        if (row.overShortFlag == '' || row.overShortFlag == undefined) {
          row.overShortFlag = 0
        }
        if (row.overShortFlag == this.overShortFlagOptions[i].value) {
          return this.overShortFlagOptions[i].label
        }
      }
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
        if (!values.every(value => isNaN(value)) && (column.property == 'piecesNum' || column.property == 'weightWgt')) {
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
        }
      }
      return sums;
    },
    uploadSuccess: function (e) {
      if (e.data == null) {
        this.$notify.success("文件上传失败")
      } else {
        this.fileOptions.push({ "originalFilename": e.data.originalFilename })
        //this.$notify.success(e.msg)
        var bucketName = e.data.bucketName
        var fileName = e.data.fileName
        var url = e.data.url
        var originalFilename = e.data.originalFilename
        var fileSize = e.data.fileSize
        this.fileList.push({
          bucketName: bucketName,
          fileName: fileName,
          url: url,
          original: originalFilename,
          fileSize: fileSize
        })
        // var fileOne = [{
        //   bucketName: bucketName,
        //   fileName: fileName,
        //   url: url,
        //   original: originalFilename,
        //   fileSize: fileSize,
        //   tableName: 'ioyard',
        //   tableFid: this.checkUserForm.fId,
        //   delFlag: '1'
        // }]

        // fileSave(fileOne).then(response => {
        //   if (response.data.data != null) {
        //     this.fileList[this.fileList.length - 1].id = response.data.data
        //     this.fileList[this.fileList.length - 1].delFlag = '0'
        //   }
        // })

        this.fileLoading = false
      }

    },
    onProgress(event, file, fileList) {
      this.fileLoading = true
    },
    downloadFile: function (index) {
      var filename = this.fileList[index].fileName
      var bucket = this.fileList[index].bucketName
      handleDown(filename, bucket)
    },
    deleteFile: function (index) {

      var id = this.fileList[index].id
      this.fileList.splice(index, 1);
      this.fileOptions.splice(index, 1);
      // if (this.dataForm.fId != 0) {
      //   delFile(id).then(response => {
      //     //alert(response)
      //   })
      // }

    },
    doOverShort(row) {
      var map = { inoutYardNo: row.inoutYardNo, surplusPieceNum: 0, surplusWeight: 0.0 }
      putObj(map).then(resp => {
        if (resp.data.data == true) {
          this.$notify.success("提交成功")
          this.getDataList()
        }
      })
    },
    settlementDtePreFormatter(row, column, cellValue, index) {
      if (cellValue != null) {
        var timeSt = (new Date(cellValue).getTime() / 1000) + (24 * 60 * 60);
        // return new Date(cellValue).getTime()
        // return timeSt
        var timeSt = new Date(parseInt(timeSt) * 1000).toLocaleString().replace(/:\d{1,2}$/, ' ').replaceAll('/', '-')
        return timeSt.split(' ')[0]

      }



    }
  }
}
</script>
<style>
.blodClass input {
  font-weight: bolder;
}
</style>
