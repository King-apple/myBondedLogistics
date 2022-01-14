<template>
  <el-dialog
    :title="!dataForm.fId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible"
    width="70%"
    append-to-body
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="120px"
      v-loading="loading"
      autocomplete="off"
    >
      <el-row hidden="hidden">
        <el-col :span="24">
          <el-form-item label="合同名称" prop="contNam">
            <el-input
              v-model="dataForm.contNam"
              placeholder="合同名称"
              readonly
              style="text-align: center"
            ></el-input> </el-form-item
        ></el-col>
      </el-row>

      <el-row>
        <el-col :span="24"
          ><el-form-item label="合同编号" prop="contNo" v-if="dataForm.contNo">
            <el-input
              v-model="dataForm.contNo"
              placeholder="合同编号"
              :disabled="true"
            ></el-input> </el-form-item
        ></el-col>
      </el-row>

      <el-row>
        <el-col :span="8"
          ><el-form-item label="承办单位" prop="firstNam">
            <el-select
              v-model="dataForm.firstNam"
              placeholder="请选择"
              filterable
              clearable
              @change="firstNamChange"
              style="width: 100%"
            >
              <el-option
                v-for="item in options"
                :key="item.organizer"
                :label="item.organizer"
                :value="item.organizer"
              >
              </el-option>
            </el-select> </el-form-item
        ></el-col>
        <el-col :span="8"
          ><el-form-item label="部门" prop="dept">
            <el-select
              v-model="dataForm.dept"
              placeholder="请选择"
              filterable
              clearable
              @change="deptNamChange"
              style="width: 100%"
              v-if="dataForm.fId"
              :disabled="true"
            >
              <el-option
                v-for="(item, index) in deptInfoOptions"
                :key="index"
                :label="item.deptNam"
                :value="item.deptNam"
              >
              </el-option>
            </el-select>
            <el-select
              v-model="dataForm.dept"
              placeholder="请选择"
              filterable
              clearable
              @change="deptNamChange"
              style="width: 100%"
              v-if="!dataForm.fId"
            >
              <el-option
                v-for="(item, index) in deptInfoOptions"
                :key="index"
                :label="item.deptNam"
                :value="item.deptNam"
              >
              </el-option>
            </el-select> </el-form-item
        ></el-col>
        <el-col :span="8"
          ><el-form-item label="经办人" prop="operator">
            <el-input
              v-model="dataForm.operator"
              placeholder="经办人"
            ></el-input> </el-form-item
        ></el-col>
      </el-row>

      <el-row>
        <el-col :span="8"
          ><el-form-item label="对方签约单位" prop="secondNam">
            <el-select
              v-model="dataForm.secondNam"
              placeholder="请选择"
              filterable
              clearable
              @change="secondNamChange"
              style="width: 100%"
            >
              <el-option
                v-for="(item, index) in secondNamOptions"
                :key="index"
                :label="item.company"
                :value="item.company"
              >
              </el-option>
            </el-select> </el-form-item
        ></el-col>
        <el-col :span="8"
          ><el-form-item label="合同类型" prop="contractType">
            <el-select
              v-model="dataForm.contractType"
              placeholder="请选择"
              filterable
              clearable
              style="width: 100%"
            >
              <el-option
                v-for="(item, index) in contractTypeOptions"
                :key="index"
                :label="item.description"
                :value="item.description"
              >
              </el-option>
            </el-select> </el-form-item
        ></el-col>
        <span
          style="
            position: absolute;
            margin-left: 4.5%;
            margin-top: 5px;
            color: red;
          "
          >*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </span>
        <el-col :span="4">
          <el-form-item label="合同标的" prop="cargoNam">
            <el-select
              v-model="dataForm.cargoNam"
              placeholder="货物名称"
              filterable
              clearable
              @change="cargoChange"
              style="width: 200%"
            >
              <el-option
                v-for="(item, index) in cargoNamOptions"
                :key="index"
                :label="item.cargoNam"
                :value="item.cargoNam"
              >
              </el-option>
            </el-select> </el-form-item
        ></el-col>
        <el-col :span="4"
          ><el-form-item
            prop="cargoKindNam"
            style="margin-left: -40% !important"
          >
            <el-select
              v-model="dataForm.cargoKindNam"
              placeholder="货类名称"
              filterable
              clearable
              @change="cargokindChange"
              style="width: 100%"
            >
              <el-option
                v-for="(item, index) in carGoKindOptions"
                :key="index"
                :label="item.cargoKindNam"
                :value="item.cargoKindNam"
              >
              </el-option>
            </el-select> </el-form-item
        ></el-col>
      </el-row>

      <el-row>
        <el-col :span="6"
          ><el-form-item label="合同标的数量" prop="cargoNum">
            <el-input
              v-model="dataForm.cargoNum"
              placeholder="合同标的数量"
              @input="cargoNuminput"
            ></el-input> </el-form-item
        ></el-col>
        <el-col :span="2">
          <el-form-item prop="cargoNumUnit" label-width="0" style="width: 100%">
            <el-select
              v-model="dataForm.cargoNumUnit"
              placeholder="请选择"
              filterable
              clearable
              style="width: 100%"
            >
              <el-option
                v-for="(item1, feeUnitIndex) in feeUnitList"
                :key="feeUnitIndex"
                :label="item1.feeUnit"
                :value="item1.feeUnit"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="标的额" prop="cargoFee">
            <el-input
              v-model="dataForm.cargoFee"
              placeholder="标的额"
              style="width: 100%"
              @input="cargoFeeinput"
            ></el-input> </el-form-item
        ></el-col>
        <el-col :span="2">
          <el-form-item prop="cargoFeeUnit" label-width="0" style="width: 100%">
            <el-select
              v-model="dataForm.cargoFeeUnit"
              placeholder="请选择"
              filterable
              clearable
              style="width: 100%"
            >
              <el-option
                v-for="(item, index) in cargoFeeUnitOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="规格" prop="format">
            <el-input
              v-model="dataForm.format"
              placeholder="规格"
            ></el-input> </el-form-item
        ></el-col>
      </el-row>

      <el-form-item label="甲方代码" prop="firstCod" hidden="hidden">
        <el-input v-model="dataForm.firstCod" placeholder="甲方代码"></el-input>
      </el-form-item>
      <el-form-item label="乙方代码" prop="secondCod" hidden="hidden">
        <el-input
          v-model="dataForm.secondCod"
          placeholder="乙方代码"
        ></el-input>
      </el-form-item>
      <el-form-item label="货类编码" prop="cargoKindCod" hidden="hidden">
        <el-input
          v-model="dataForm.cargoKindCod"
          placeholder="货类编码"
        ></el-input>
      </el-form-item>
      <el-form-item label="货类名称" prop="cargoKindNam" hidden="hidden">
        <el-input
          v-model="dataForm.cargoKindNam"
          placeholder="货类名称"
        ></el-input>
      </el-form-item>
      <el-row>
        <el-col :span="8">
          <el-form-item label="合同开始日期" prop="startDte">
            <el-date-picker
              v-model="dataForm.startDte"
              type="date"
              placeholder="合同开始日期"
              value-format="yyyy-MM-dd"
              style="width: 100%"
            >
            </el-date-picker> </el-form-item
        ></el-col>
        <el-col :span="8"
          ><el-form-item label="合同结束日期" prop="endDte">
            <el-date-picker
              v-model="dataForm.endDte"
              type="date"
              placeholder="合同结束日期"
              value-format="yyyy-MM-dd"
              style="width: 100%"
            >
            </el-date-picker> </el-form-item
        ></el-col>
        <el-col :span="8">
          <el-form-item label="合同签订日期" prop="conDte">
            <el-date-picker
              v-model="dataForm.conDte"
              type="date"
              placeholder="合同签订日期"
              value-format="yyyy-MM-dd"
              style="width: 100%"
            >
            </el-date-picker> </el-form-item
        ></el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="包装" prop="pkgKindNam">
            <el-select
              v-model="dataForm.pkgKindNam"
              placeholder="包装"
              filterable
              clearable
              style="width: 100%"
            >
              <el-option
                v-for="(item, index) in pkgKindNamOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select> </el-form-item
        ></el-col>
        <el-col :span="8"
          ><el-form-item label="客户联系人" prop="contact">
            <el-input
              v-model="dataForm.contact"
              placeholder="客户联系人"
            ></el-input> </el-form-item
        ></el-col>
        <el-col :span="8">
          <el-form-item label="联系电话" prop="phone">
            <el-input
              v-model="dataForm.phone"
              placeholder="联系电话"
            ></el-input> </el-form-item
        ></el-col>
      </el-row>

      <el-row>
        <el-col :span="8">
          <el-form-item label="存放地点" prop="locate">
            <el-select
              v-model="dataForm.locate"
              placeholder="存放地点"
              filterable
              clearable
              style="width: 100%"
            >
              <el-option
                v-for="(item, index) in portLocationOptions"
                :key="index"
                :label="item.location"
                :value="item.location"
              >
              </el-option>
            </el-select> </el-form-item
        ></el-col>
        <el-col :span="8"
          ><el-form-item label="内外贸" prop="tradeId">
            <el-select
              v-model="dataForm.tradeId"
              placeholder="请选择"
              filterable
              clearable
              style="width: 100%"
            >
              <el-option
                v-for="item in tradeIdDicOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select> </el-form-item
        ></el-col>
        <el-col :span="8">
          <el-form-item label="进出口" prop="inoutId">
            <el-select
              v-model="dataForm.inoutId"
              placeholder="请选择"
              filterable
              clearable
              style="width: 100%"
            >
              <el-option
                v-for="item in inoutIdDicOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select> </el-form-item
        ></el-col>
      </el-row>

      <el-row>
        <el-col :span="8">
          <el-form-item label="结算依据" prop="settlementBasis">
            <el-select
              v-model="dataForm.settlementBasis"
              placeholder="请选择"
              filterable
              clearable
              style="width: 100%"
            >
              <el-option
                v-for="(item, index) in settlementBasisOptions"
                :key="index"
                :label="item.description"
                :value="item.description"
              >
              </el-option>
            </el-select> </el-form-item
        ></el-col>
        <el-col :span="8"
          ><el-form-item label="结算方式" prop="clearingForm">
            <el-select
              v-model="dataForm.clearingForm"
              placeholder="请选择"
              filterable
              style="width: 100%"
              clearable
            >
              <el-option
                v-for="(item, index) in clearingFormOptions"
                :key="index"
                :label="item.description"
                :value="item.description"
              >
              </el-option>
            </el-select> </el-form-item
        ></el-col>

        <el-col :span="8">
          <el-form-item label="船名" prop="shipNam">
            <el-input
              v-model="dataForm.shipNam"
              placeholder="船名"
            ></el-input> </el-form-item
        ></el-col>
      </el-row>

      <el-form-item
        v-for="(item, index) in feeList"
        :label="'费目' + (index + 1)"
        :key="index"
      >
        <el-row>
          <el-col :span="6">
            <el-form-item label="费目" prop="feeNam">
              <el-select
                v-model="feeList[index].feeNam"
                placeholder="请选择"
                filterable
                @focus="getFeeNam"
                @loading="feeLoading"
                clearable
                @change="feeNamChange($event, index)"
                style="width: 100%"
              >
                <el-option
                  v-for="(item, feeIndex) in feeNameList"
                  :key="feeIndex"
                  :label="item.feeNam"
                  :value="item.feeNam"
                >
                </el-option> </el-select></el-form-item
          ></el-col>
          <el-form-item label="费目编码" prop="feeNameCod" hidden="hidden">
            <el-input
              v-model="feeList[index].feeNameCod"
              placeholder="费目编码"
            ></el-input>
          </el-form-item>
          <el-col :span="6">
            <el-form-item label="存放地点" prop="locate">
              <el-select
                v-model="feeList[index].locate"
                placeholder="存放地点"
                filterable
                clearable
                style="width: 100%"
              >
                <el-option
                  v-for="(item, index) in portLocationOptions"
                  :key="index"
                  :label="item.location"
                  :value="item.location"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6"
            ><el-form-item label="费率值" prop="feeRat">
              <el-input
                v-model="feeList[index].feeRat"
                placeholder="费率值"
              ></el-input> </el-form-item
          ></el-col>
          <el-col :span="6">
            <el-form-item label="费率单位" prop="feeUnit">
              <el-select
                v-model="feeList[index].feeUnit"
                placeholder="请选择"
                filterable
                @focus="getFeeNam"
                clearable
                @loading="feeLoading"
                style="width: 100%"
              >
                <el-option
                  v-for="(item1, feeUnitIndex) in feeUnitList"
                  :key="feeUnitIndex"
                  :label="item1.feeUnit"
                  :value="item1.feeUnit"
                >
                </el-option>
              </el-select> </el-form-item
          ></el-col>
        </el-row>
        <el-form-item label="备注" prop="markTxt" id="mark">
          <el-input
            v-model="feeList[index].markTxt"
            placeholder="备注"
            type="textarea"
            style="width: 80%; margin-bottom: 10%"
          ></el-input>
          <el-button
            size="small"
            class="feeBtn"
            type="danger"
            style="margin-bottom: 10%; margin-left: 5%"
            @click="delFee(index)"
            >删除该费目</el-button
          >
        </el-form-item>
      </el-form-item>
      <el-button
        size="small"
        class="feeBtn"
        type="success"
        style="margin-left: 10%"
        @click="addFee"
        >添加费目</el-button
      >
      <!-- <el-button
        size="small"
        class="feeBtn"
        type="danger"
        style=""
        @click="delFee"
        >删除费目</el-button
      > -->
      <el-form-item label="备注" prop="markTxt">
        <el-input
          v-model="dataForm.markTxt"
          placeholder="备注"
          type="textarea"
          @input="oninput($event)"
        ></el-input>
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
          :on-error="uploadError"
          :on-change="onchange"
          :before-upload="beforeUpload"
          class="avatar-uploader"
          action="/baoshuisystem/sys-file/upload"
          :on-progress="onProgress"
        >
          <el-button size="small" type="primary">点击上传</el-button>
        </el-upload>
        <ul id="example-1">
          <li v-for="(item, index) in fileOptions" :key="index">
            {{ item.originalFilename }}
            <i
              class="el-icon-download"
              style="
                float: right;
                left: 40%;
                height: 32px;
                line-height: 32px;
                position: absolute;
              "
              @click="downloadFile(index)"
            ></i>
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
            <el-input
              v-model="fileList[index].remark"
              placeholder="备注"
              style="text-align: center"
            ></el-input>
          </li>
        </ul>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" v-if="canSubmit"
        >确定</el-button
      >
    </span>
  </el-dialog>
</template>
<script>
import {
  getObj, addObj, putObj, getUserInfo, getDeptInfo, getInsertValue, getContractType, getSettlementBasisDic,
  getClearingFormDic, fileSave, getFileInfo, delFile, handleDown, getAllContractrate, getContractCode, getChildCode, fileUpdate
  , getInoutIdDic, getTradeIdDic
} from '@/api/baoshuisystem/contract'

import { getAll as feeunitAll } from '@/api/baoshuisystem/feeunits'
import { all as carGoKindAll } from '@/api/baoshuisystem/goodscategory'
import { getFeenamesAll as feeFetch, saveList as contractSaveList, getRateList, removeBycFid } from '@/api/baoshuisystem/feenames'
import { fetchList as deptInfoFetch } from '@/api/baoshuisystem/deptinfo'
import { getAll as storagelocationAll } from '@/api/baoshuisystem/storagelocation'
import { getPkgKindNamDic, getCargoFeeUnit } from '@/api/baoshuisystem/getdictdata'

import store from '@/store'
import XEUtils from 'xe-utils'
export default {
  data() {
    return {
      headers: {
        'Authorization': 'Bearer ' + store.getters.access_token,
      },
      visible: false,
      canSubmit: false,
      loading: true,
      fileLoading: false,
      feeLoading: false,
      options: [],
      insData: {},
      cargoNamOptions: [],
      secondNamOptions: [],
      contractTypeOptions: [],
      settlementBasisOptions: [],
      clearingFormOptions: [],
      fileOptions: [],
      fileList: [],
      feeList: [],
      feeNameList: [],
      deptInfoOptions: [],
      codeList: {},
      feeUnitList: [],
      inoutIdDicOptions: [],
      tradeIdDicOptions: [],
      carGoKindOptions: [],
      portLocationOptions: [],
      pkgKindNamOptions: [],
      cargoFeeUnitOptions: [],
      dataForm: {
      },
      dataRule: {
        // contNam: [
        //   { required: true, message: '合同名称不能为空', trigger: 'blur' }
        // ],
        // contNo: [
        //   { required: true, message: '合同编号不能为空', trigger: 'blur' }
        // ],
        firstNam: [
          { required: true, message: '承办单位不能为空', trigger: 'blur' }
        ],
        dept: [
          { required: true, message: '部门不能为空', trigger: 'blur' }
        ],
        operator: [
          { required: true, message: '经办人不能为空', trigger: 'blur' }
        ],
        secondNam: [
          { required: true, message: '对方签约单位不能为空', trigger: 'blur' }
        ],
        contractType: [
          { required: true, message: '合同类型不能为空', trigger: 'blur' }
        ],
        // cargoNam: [
        //   { required: true, message: '合同标的不能为空', trigger: 'blur' }
        // ],
        cargoNum: [
          {
            validator(rule, value, callback) {
              var reg = new RegExp(',', "g")
              if (value == null) {
                callback()
                return
              }
              value = value.toString().replace(reg, '');
              if (XEUtils.isFloat(value) || !isNaN(Number(value) || value == undefined || value == '')) {

                callback()
              } else {
                callback(new Error('请输入数字'))
              }
            },
            trigger: 'blur'
          }
        ],
        cargoFee: [
          {
            validator(rule, value, callback) {
              var reg = new RegExp(',', "g")

              if (value == null) {
                callback()
                return
              }
              value = value.toString().replace(reg, '');
              if (XEUtils.isFloat(value) || !isNaN(Number(value) || value == undefined || value == '')) {
                callback()
              } else {
                callback(new Error('请输入数字'))
              }
            },
            trigger: 'blur'
          }
        ],
        startDte: [
          { required: true, message: '合同开始日期不能为空', trigger: 'blur' }
        ],
        endDte: [
          { required: true, message: '合同结束日期不能为空', trigger: 'blur' }
        ],
        conDte: [
          { required: true, message: '合同签订日期不能为空', trigger: 'blur' }
        ],
        settlementBasis: [
          { required: true, message: '结算依据不能为空', trigger: 'blur' }
        ],
        clearingForm: [
          { required: true, message: '结算方式不能为空', trigger: 'blur' }
        ],
        contact: [
          { required: true, message: '客户联系人不能为空', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '联系电话不能为空', trigger: 'blur' }
        ],
        locate: [
          { required: true, message: '存放地点不能为空', trigger: 'blur' }
        ],
        tradeId: [
          // { required: true, message: '内外贸不能为空', trigger: 'blur' }
        ],
        inoutId: [
          // { required: true, message: '进出口不能为空', trigger: 'blur' }
        ],

        // markTxt: [
        //   { required: true, message: '备注不能为空', trigger: 'blur' }
        // ],
        urlFile: [
          {}
        ]
      }
    }
  },
  methods: {
    init(id) {
      this.dataForm = {
        fId: 0,
        contNam: '',
        contNo: '',
        firstNam: '',
        dept: '',
        operator: '',
        secondNam: '',
        contractType: '',
        cargoNam: '',
        cargoFee: '',
        cargoKindNam: '',
        cargoKindCod: '',
        startDte: '',
        endDte: '',
        conDte: '',
        settlementBasis: '',
        clearingForm: '',
        contact: '',
        phone: '',
        remark: '',
        urlFile: '',
        isCancel: '',
        times: '',
        contractStatus: '',
        operNam: '',
        recordTim: '',
        updateNam: '',
        updateTim: '',
        delNam: '',
        delTim: '',
        firstCod: '',
        secondCod: '',
        format: '',
        shipNam: '',
        contNoMaster: '',
        cargoCod: '',
        pkgKindNam: '',
        deptNo: '',
        cargoNum: ''
      }
      this.fileLoading = false
      this.fileOptions = []
      this.fileList = []
      this.feeList = []
      this.loading = true;
      this.dataForm.fId = id || 0;
      this.visible = true;
      this.canSubmit = true;
      this.feeNameList = [];
      this.feeUnitList = [];
      this.getAllLocation();
      this.getInfo();
      feeunitAll().then(response => {
        this.feeUnitList = response.data.data
      })
      getCargoFeeUnit().then(response => {
        this.cargoFeeUnitOptions = response.data.data
      })

    },
    // 表单提交
    dataFormSubmit() {
      this.$refs['dataForm'].validate((valid) => {
        var validStatus = 0
        console.log(this.dataForm)
        if ((this.dataForm.cargoNam == '' || this.dataForm.cargoNam == undefined) && (this.dataForm.cargoKindNam == '' || this.dataForm.cargoKindNam == undefined)) {
          validStatus = 1
        }
        if (validStatus == 1) {
          this.$message.error('请选择合同标的')
          return;
        }
        console.log(this.feeList)
        if (this.feeList.length == 0 || this.feeList[0].feeNam == undefined || this.feeList[0].feeNam == '') {
          this.$message.error('请选择至少一条费目')
          return;
        }
        for (var i in this.feeList) {
          console.log(this.feeList[i])
          if (!XEUtils.isFloat(this.feeList[i].feeRat) && isNaN(Number(this.feeList[i].feeRat))) {
            this.$message.error('费率值必须为数字！')
            return;
          }
        }
        if (valid) {

          this.canSubmit = false;
          var reg = new RegExp(',', "g")
          if (this.dataForm.cargoFee != null) {
            this.dataForm.cargoFee = this.dataForm.cargoFee.toString().replace(reg, '');
          }
          if (this.dataForm.cargoNum != null) {
            this.dataForm.cargoNum = this.dataForm.cargoNum.toString().replace(reg, '');
          }
          if (this.dataForm.fId) {
            putObj(this.dataForm).then(data => {

              removeBycFid(this.dataForm.fId).then(data => {
                for (const key in this.feeList) {
                  this.feeList[key].cFid = this.dataForm.fId
                }
                if (data.data.data == true) {
                  contractSaveList(this.feeList)
                  this.$notify.success('修改成功')
                } else {
                  contractSaveList(this.feeList)
                  this.$notify.success('修改成功')
                  // this.$notify.error("修改失败")
                }
                this.visible = false
                fileUpdate(this.fileList)
                this.$emit('refreshDataList')

              })

            }).catch(() => {
              this.canSubmit = true;

            });
          } else {
            addObj(this.dataForm).then(data => {
              if (data.data.data == 0) {
                this.$notify.error("当前合同编号已存在，请重新添加")
                this.visible = false
                this.$emit('refreshDataList')
              } else {
                this.$notify.success(data.data.data.fId)
                // this.$notify.success('添加成功')
                for (var i in this.fileList) {
                  this.fileList[i].tableName = 'contract'
                  this.fileList[i].tableFid = data.data.data.fId
                }
                fileSave(this.fileList)
                for (const key in this.feeList) {
                  this.feeList[key].cFid = data.data.data.fId
                }
                if (this.feeList.length > 0) {
                  contractSaveList(this.feeList).then(data => {

                  })
                }

                this.visible = false
                this.$emit('refreshDataList')
              }

            }).catch(() => {
              this.canSubmit = true;
            });
          }
        }

      })
    },

    getInfo: async function () {
      if (this.$refs['dataForm'] != null) {
        this.$refs['dataForm'].resetFields()
      }
      await getInsertValue().then(response => {
        this.insData = response.data.data
        this.options = this.insData.organizerInfos
        // this.dataForm.dept = this.insData.deptName
        this.dataForm.operator = this.insData.username
        this.secondNamOptions = this.insData.clientInfos
        //this.dataForm.contNo = this.insData.cont_no
        this.cargoNamOptions = this.insData.contractObjects

      })
      var py = ""
      // await getDeptInfo(this.dataForm.dept).then(response => {
      //   this.dataForm.dept = response.data.data.name
      //   py = Pinyin.getWordsCode(this.dataForm.dept)

      // })
      deptInfoFetch().then(data => {
        this.deptInfoOptions = data.data.data.records
      })
      var params = { paramName: py.toUpperCase() }
      var childCode = "";
      // await getContractCode(params).then(response => {
      //   this.dataForm.contNo = this.insData.cont_no += (py.toUpperCase() + '-' + response.data.data.paramCode + '-' + response.data.data.paramDate)
      //   childCode = response.data.data.paramChildCode
      // })

      await getContractType().then(response => {
        this.contractTypeOptions = response.data.data
      })

      await getSettlementBasisDic().then(response => {
        this.settlementBasisOptions = response.data.data
      })

      await getClearingFormDic().then(response => {
        this.clearingFormOptions = response.data.data
      })

      getInoutIdDic().then(response => {
        this.inoutIdDicOptions = response.data.data
      })
      getTradeIdDic().then(response => {
        this.tradeIdDicOptions = response.data.data
      })
      carGoKindAll().then(response => {
        this.carGoKindOptions = response.data.data
      })
      getPkgKindNamDic().then(response => {
        this.pkgKindNamOptions = response.data.data
      })
      this.$nextTick(() => {

        if (this.dataForm.fId && this.dataForm.fId.indexOf("YGWL") == -1) {
          this.$refs['dataForm'].resetFields()
          this.getFileInfoUpdate();
          this.getRateList({ cFid: this.dataForm.fId })
          getObj(this.dataForm.fId).then(response => {
            this.dataForm = response.data.data
            this.dataForm.inoutId = this.dataForm.inoutId + ''
            this.dataForm.tradeId = this.dataForm.tradeId + ''
            var pkg = this.dataForm.pkgKindNam;
            if (pkg != null) {
              pkg = pkg + ''
            }
            this.dataForm.pkgKindNam = pkg
          })
        } else if (this.dataForm.fId != 0 && this.dataForm.fId.indexOf("YGWL") != -1) {
          getChildCode({ contNo: this.dataForm.fId }).then(response => {
            this.dataForm.contNo = this.dataForm.fId + ('-' + response.data.data[0])
            this.dataForm.fId = 0
            this.dataForm.contNoMaster = response.data.data[1]

          })
        } else {

          this.feeList = [{
            fee_nam: ""
          }]
        }
      })


      this.loading = false
      this.dataForm.contNam = "营口港务集团有限公司物流总公司合同审批单"
    },
    secondNamChange: function (value) {
      var obj = this.secondNamOptions.find((item) => {//这里的userList就是上面遍历的数据源
        return item.company === value;//筛选出匹配数据
      });
      this.dataForm.secondCod = obj.companyCode
      this.dataForm.contact = obj.contactName
      this.dataForm.phone = obj.contactPhone
    },
    firstNamChange: function (value) {
      var obj = this.options.find((item) => {//这里的userList就是上面遍历的数据源
        return item.organizer === value;//筛选出匹配数据
      });
      this.dataForm.firstCod = obj.organizerCode
    },
    deptNamChange: function (value) {
      var obj = this.deptInfoOptions.find((item) => {//这里的userList就是上面遍历的数据源
        return item.deptNam === value;//筛选出匹配数据
      });
      this.dataForm.deptNo = obj.deptNo
    },
    cargoChange: function (value) {
      var obj = this.cargoNamOptions.find((item) => {//这里的userList就是上面遍历的数据源
        return item.cargoNam === value;//筛选出匹配数据
      });
      this.dataForm.cargoCod = obj.cargoCod
    },
    cargokindChange: function (value) {
      var obj = this.carGoKindOptions.find((item) => {//这里的userList就是上面遍历的数据源
        return item.cargoKindNam === value;//筛选出匹配数据
      });
      this.dataForm.cargoKindCod = obj.cargoKindCod
    },
    uploadSuccess: function (e) {
      this.fileLoading = false
      if (e.data == null) {
        this.$notify.error("文件上传失败")
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
        if (this.dataForm.fId != 0) {
          var fileOne = [{
            bucketName: bucketName,
            fileName: fileName,
            url: url,
            original: originalFilename,
            fileSize: fileSize,
            tableName: 'contract',
            tableFid: this.dataForm.fId
          }]

          fileSave(fileOne).then(response => {
            if (response.data.data != null) {
              this.fileList[this.fileList.length - 1].id = response.data.data
            }
          })
        }
      }

    },
    deleteFile: function (index) {

      var id = this.fileList[index].id
      this.fileList.splice(index, 1);
      this.fileOptions.splice(index, 1);
      if (this.dataForm.fId != 0) {
        delFile(id).then(response => {
          //alert(response)
        })
      }

    },
    getFileInfoUpdate: function () {
      var map = { tableFid: this.dataForm.fId }
      getFileInfo(map).then(response => {

        this.fileList = response.data.data
        for (const key in this.fileList) {
          this.fileOptions.push({ "originalFilename": this.fileList[key].original })
        }
      })
    },

    onProgress(event, file, fileList) {
      this.fileLoading = true
      let loadProgress = Math.floor(event.percent) //这就是当前上传的进度
      console.log(loadProgress)
    },

    downloadFile: function (index) {
      var filename = this.fileList[index].fileName
      var bucket = this.fileList[index].bucketName
      handleDown(filename, bucket)
    },
    addFee: function () {
      this.feeList.push({
        fee_nam: ""
      })
    },
    delFee: function (index) {
      this.feeList.splice(index, 1);
    },
    getFeeNam: function () {
      if (this.feeNameList.length == 0) {
        this.feeLoading = true;
        feeFetch().then(response => {
          this.feeNameList = response.data.data
          this.feeLoading = false;
        })

      }
    },
    oninput(e) {
      this.$forceUpdate();
    },
    feeNamChange: function (value, index) {
      var obj = this.feeNameList.find((item) => {//这里的userList就是上面遍历的数据源
        return item.feeNam === value;//筛选出匹配数据
      });
      this.feeList[index].feeNameCod = obj.feeNameCod
    },

    getRateList: function (cFid) {
      getRateList(cFid).then(response => {
        this.feeList = response.data.data.records
      })
    },
    getAllLocation() {
      storagelocationAll().then(response => {
        this.portLocationOptions = response.data.data
      })
    },
    cargoNuminput() {
      var deg = this.dataForm.cargoNum.toString().split('.')[1]
      this.dataForm.cargoNum = this.dataForm.cargoNum.toString().split('.')[0]
      var reg = new RegExp(',', "g")
      var fee = this.dataForm.cargoNum.toString().replace(reg, '');

      if (!fee.includes('.')) fee += '.';
      fee = fee.replace(/(\d)(?=(\d{3})+\.)/g, function ($0, $1) {
        return $1 + ',';
      }).replace(/\.$/, '');
      if (deg != undefined) {
        fee = fee + '.' + deg
      }
      this.$set(this.dataForm, "cargoNum", fee);


      // var reg = new RegExp(',', "g")
      // var fee = this.dataForm.cargoNum.toString().replace(reg, '');
      // if (!fee.includes('.')) fee += '.';
      // fee = fee.replace(/(\d)(?=(\d{3})+\.)/g, function ($0, $1) {
      //   return $1 + ',';
      // }).replace(/\.$/, '');

      // this.$set(this.dataForm, "cargoNum", fee);
    },
    cargoFeeinput() {
      var deg = this.dataForm.cargoFee.toString().split('.')[1]
      this.dataForm.cargoFee = this.dataForm.cargoFee.toString().split('.')[0]
      var reg = new RegExp(',', "g")
      var fee = this.dataForm.cargoFee.toString().replace(reg, '');

      if (!fee.includes('.')) fee += '.';
      fee = fee.replace(/(\d)(?=(\d{3})+\.)/g, function ($0, $1) {
        return $1 + ',';
      }).replace(/\.$/, '');
      if (deg != undefined) {
        fee = fee + '.' + deg
      }
      this.$set(this.dataForm, "cargoFee", fee);
    },
    uploadError() {
      this.$notify.error("文件上传失败，请重试")
      this.fileLoading = false
    },
    onchange(file, fileList) {
      console.log("onchange", file)
      console.log("onchange", fileList)
    },
    beforeUpload(file) {
      console.log("beforeUpload", file)
    }
  }
}
</script>
<style scoped>
.feeBtn {
  margin-bottom: 5%;
}
#mark {
  margin-bottom: 5%;
  height: 40px;
}
</style>
