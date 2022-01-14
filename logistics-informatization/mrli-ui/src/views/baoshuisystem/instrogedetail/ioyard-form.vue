<template>
  <el-dialog
    title="手动上图"
    :close-on-click-modal="false"
    :visible.sync="visible"
    width="80%"
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="100px"
    >
      <el-row>
        <el-col :span="6">
          <el-form-item label="公司名称" prop="unitCod">
            <el-select
              v-model="dataForm.unitCod"
              style="width: 100%"
              filterable
              placeholder="请选择"
              @change="getSubunit"
            >
              <el-option
                v-for="(item, index) in unitOptions"
                :key="index"
                :label="item.unitNam"
                :value="item.unitCod"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="入库日期" prop="ioyardDte">
            <el-date-picker
              v-model="dataForm.ioyardDte"
              type="date"
              placeholder="选择日期"
              value-format="yyyy-MM-dd"
              style="width: 100%"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="船名" prop="shipNam">
            <el-input v-model="dataForm.shipNam" placeholder="船名"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="作业委托人" prop="consignCod">
            <el-select
              v-model="dataForm.consignCod"
              style="width: 100%"
              filterable
              placeholder="请选择"
              @change="consignNamChange"
            >
              <el-option
                v-for="(item, index) in shipperDocOptions"
                :key="index"
                :label="item.shipperDoc"
                :value="item.shipperCod"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="6">
          <el-form-item label="提单号" prop="billNo">
            <el-input v-model="dataForm.billNo" placeholder="提单号"></el-input>
          </el-form-item>
        </el-col>
        <!-- <el-col :span="6">
          <el-form-item label="进出口" prop="inoutId">
            <el-select
              v-model="dataForm.inoutId"
              style="width: 100%"
              filterable
              placeholder="请选择"
            >
              <el-option
                v-for="(item, index) in inoutIdOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="内外贸" prop="tradeId">
            <el-select
              v-model="dataForm.tradeId"
              style="width: 100%"
              filterable
              placeholder="请选择"
            >
              <el-option
                v-for="(item, index) in tradeIdOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col> -->
        <el-col :span="6">
          <el-form-item label="货票号" prop="ioyardNo">
            <el-input
              v-model="dataForm.ioyardNo"
              placeholder="货票号"
            ></el-input> </el-form-item
        ></el-col>
        <el-col :span="6">
          <el-form-item label="重量" prop="weightWgt">
            <el-input
              v-model="dataForm.weightWgt"
              placeholder="重量"
              style="width: 59%"
            ></el-input>
            <el-select
              v-model="dataForm.format"
              style="width: 40%"
              filterable
              placeholder="单位"
              clearable
            >
              <el-option
                v-for="(item, index) in weightUnitOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="体积" prop="cargoVol">
            <el-input
              v-model="dataForm.cargoVol"
              placeholder="体积"
            ></el-input> </el-form-item
        ></el-col>
      </el-row>
      <el-row>
        <el-col :span="6"
          ><el-form-item label="入库方式" prop="inYardWay">
            <el-select
              v-model="dataForm.inYardWay"
              style="width: 100%"
              filterable
              placeholder="请选择"
            >
              <el-option
                v-for="(item, index) in inTypeOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6"
          ><el-form-item label="初次入库日期" prop="firstInDte">
            <el-date-picker
              v-model="dataForm.firstInDte"
              type="date"
              placeholder="选择日期"
              value-format="yyyy-MM-dd"
              style="width: 100%"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <!-- <el-col :span="6">
          <el-form-item label="出库方式" prop="outYardWay">
            <el-select
              v-model="dataForm.outYardWay"
              style="width: 100%"
              filterable
              placeholder="请选择"
            >
              <el-option
                v-for="(item, index) in outTypeOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col> -->
        <el-col :span="6"
          ><el-form-item label="合同号" prop="contNo">
            <el-select
              v-model="dataForm.contNo"
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
        </el-col>

        <el-col :span="6"
          ><el-form-item label="保管单位" prop="subunitCod">
            <el-select
              v-model="dataForm.subunitCod"
              placeholder="请选择"
              filterable
              clearable
              @change="subunitCodChange"
            >
              <el-option
                v-for="(item, index) in this.subunitOptions"
                :key="index"
                :label="item.subunitNam"
                :value="item.subunitCod"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="6">
          <el-form-item label="货名" prop="cargoNamCod">
            <el-select
              v-model="dataForm.cargoNamCod"
              style="width: 100%"
              filterable
              placeholder="请选择"
              @change="cargoNamChanged"
            >
              <el-option
                v-for="(item, index) in cargoOptions"
                :key="index"
                :label="item.cargoNam"
                :value="item.cargoNamCod"
              >
              </el-option>
            </el-select> </el-form-item
        ></el-col>
        <el-col :span="6"
          ><el-form-item label="包装" prop="cPkgKindNam">
            <el-input
              v-model="dataForm.cPkgKindNam"
              placeholder="包装"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="件数" prop="piecesNum">
            <el-input
              v-model="dataForm.piecesNum"
              placeholder="件数"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="6">
          <el-form-item label="核注清单号" prop="inCheckNum">
            <el-select
              v-model="dataForm.inCheckNum"
              style="width: 100%"
              clearable
              filterable
              placeholder="请选择"
            >
              <el-option
                v-for="(item, index) in inNumberList"
                :key="index"
                :label="item.inCheckNum"
                :value="item.inCheckNum"
                @click.native="getMessage(item)"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="报关单号" prop="inCustomsDeclaration">
            <el-select
              v-model="dataForm.inCustomsDeclaration"
              style="width: 100%"
              filterable
              placeholder="请选择"
              clearable
            >
              <el-option
                v-for="(item, index) in declarationList"
                :key="index"
                :label="item.inCustomsDeclaration"
                :value="item.inCustomsDeclaration"
                @click.native="getMessage1(item)"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

      </el-row>
      <el-row>
        <el-col :span="18">
          <el-form-item label="备注" prop="remarkTxt">
            <el-input
              v-model="dataForm.remarkTxt"
              placeholder="备注"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
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
        </el-col>
      </el-row>
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
import { getObj, addObj, putObj } from '@/api/baoshuisystem/ioyard'
import { getInNumberListAndCustoms,getId,getBgId } from '@/api/baoshuisystem/inoutyardbody'
import {
  getCunitNam,
  getSubunit,
  getInoutIdDic,
  getTradeIdDic,
  getInoutType,
  getShipperDoc,
  getCargoNam,
  getInyardType,
  getOutYardType,
  getWeightUnit
} from '@/api/baoshuisystem/getdictdata'
import {
  getEffectiveInfo, fileSave, delFile, handleDown
} from '@/api/baoshuisystem/contract'
import store from '@/store'
export default {
  data() {
    return {
      headers: {
        'Authorization': 'Bearer ' + store.getters.access_token,
      },
      visible: false,
      canSubmit: false,
      dataForm: {
        unitCod: '',
        classId: '',
        ioyardDte: '',
        ioyardNo: '',
        tallySeqNo: '',
        inoutYardNo: '',
        subunitCod: '',
        subunitNam: '',
        shipNo: '',
        shipNam: '',
        billNo: '',
        inShipNo: '',
        inShipNam: '',
        inoutId: '',
        tradeId: '',
        trainNo: '',
        trainCod: '',
        ioyardId: '',
        inYardWay: '',
        inPortWay: '',
        firstInDte: '',
        outYardWay: '',
        outPortWay: '',
        yardNo: '',
        yardNam: '',
        locNo: '',
        consignCod: '',
        consignNam: '',
        shipperCod: '',
        shipperDoc: '',
        cargoNo: '',
        cargoKindCod: '',
        cargoKindNam: '',
        cargoNamCod: '',
        cargoNam: '',
        pkgKindCod: '',
        cPkgKindNam: '',
        format: '吨',
        piecesNum: '',
        piecesWgt: '',
        weightWgt: '',
        cargoVol: '',
        oldYardNo: '',
        oldYardNam: '',
        oldLocNo: '',
        oldPkgKindCod: '',
        oldCPkgKindNam: '',
        oldFormat: '',
        oldPiecesNum: '',
        oldPiecesWgt: '',
        oldWeightWgt: '',
        oldCargoVol: '',
        feeId: '',
        ifendId: '',
        endTim: '',
        remarkTxt: '',
        operNam: '',
        recordTim: '',
        proveNo: '',
        inProveNo: '',
        changeId: '',
        outTrainCod: '',
        inTrainNo: '',
        inTrainCod: '',
        teleNo: '',
        sanothNam: '',
        stationNam: '',
        contNo: '',
        inYardNo: '',
        inInoutId: '',
        inTradeId: '',
        inContNo: '',
        inBillNo: '',
        washId: '',
        modiPortTxt: '',
        carNo: '',
        steelNo: '',
        colorId: '',
        material: '',
        tradeContNo: '',
        steelSeqNo: '',
        jfDteIn: '',
        jfDteOut: '',
        inCustomsDeclaration: '',
        inCheckNum: '',
      },
      dataRule: {
        unitCod: [
          { required: true, message: '保管单位不能为空', trigger: 'blur' }
        ],
        yardNo: [
          { required: true, message: '库场代码不能为空', trigger: 'blur' }
        ],
        yardNam: [
          { required: true, message: '库场名称不能为空', trigger: 'blur' }
        ],

        // billNo: [
        //   { required: true, message: '提单号不能为空', trigger: 'blur' }
        // ],
        inoutId: [
          { required: true, message: '进出口不能为空', trigger: 'blur' }
        ],
        tradeId: [
          { required: true, message: '内外贸不能为空', trigger: 'blur' }
        ],

        ioyardDte: [
          { required: true, message: '出入库日期不能为空', trigger: 'blur' }
        ],
        classId: [
          { required: true, message: '班次不能为空', trigger: 'blur' }
        ],
        // ioyardNo: [
        //   { required: true, message: '货票号不能为空', trigger: 'blur' }
        // ],
        tallySeqNo: [
          { required: true, message: '理货流水号不能为空', trigger: 'blur' }
        ],
        inoutYardNo: [
          { required: true, message: '出入库单号不能为空', trigger: 'blur' }
        ],
        subunitCod: [
          { required: true, message: '科室队代码不能为空', trigger: 'blur' }
        ],
        subunitNam: [
          { required: true, message: '科室队名称不能为空', trigger: 'blur' }
        ],
        shipNo: [
          { required: true, message: '船号不能为空', trigger: 'blur' }
        ],

        inShipNo: [
          { required: true, message: '进口船号不能为空', trigger: 'blur' }
        ],
        inShipNam: [
          { required: true, message: '进口船名不能为空', trigger: 'blur' }
        ],

        trainNo: [
          { required: true, message: '车次不能为空', trigger: 'blur' }
        ],
        trainCod: [
          { required: true, message: '车皮号不能为空', trigger: 'blur' }
        ],
        ioyardId: [
          { required: true, message: '出入库标志不能为空', trigger: 'blur' }
        ],
        inYardWay: [
          { required: true, message: '入库方式不能为空', trigger: 'blur' }
        ],
        inPortWay: [
          { required: true, message: '入港方式不能为空', trigger: 'blur' }
        ],
        // firstInDte: [
        //   { required: true, message: '初次入库日期不能为空', trigger: 'blur' }
        // ],
        outYardWay: [
          { required: true, message: '出库方式不能为空', trigger: 'blur' }
        ],
        outPortWay: [
          { required: true, message: '出港方式不能为空', trigger: 'blur' }
        ],

        locNo: [
          { required: true, message: '货位号不能为空', trigger: 'blur' }
        ],
        consignCod: [
          { required: true, message: '代理货主代码不能为空', trigger: 'blur' }
        ],
        // consignNam: [
        //   { required: true, message: '代理货主不能为空', trigger: 'blur' }
        // ],
        shipperCod: [
          { required: true, message: '港口货主代码不能为空', trigger: 'blur' }
        ],
        shipperDoc: [
          { required: true, message: '港口货主不能为空', trigger: 'blur' }
        ],
        cargoNo: [
          { required: true, message: '货号不能为空', trigger: 'blur' }
        ],
        cargoKindCod: [
          { required: true, message: '货类代码不能为空', trigger: 'blur' }
        ],
        cargoKindNam: [
          { required: true, message: '货类名称不能为空', trigger: 'blur' }
        ],
        cargoNamCod: [
          { required: true, message: '货名代码不能为空', trigger: 'blur' }
        ],
        cargoNam: [
          { required: true, message: '货名不能为空', trigger: 'blur' }
        ],


        format: [
          { required: true, message: '规格型号不能为空', trigger: 'blur' }
        ],
        piecesNum: [
          { required: true, message: '件数不能为空', trigger: 'blur' }
        ],
        piecesWgt: [
          { required: true, message: '件重不能为空', trigger: 'blur' }
        ],
        weightWgt: [
          { required: true, message: '重量不能为空', trigger: 'blur' }
        ],
        // cargoVol: [
        //   { required: true, message: '体积不能为空', trigger: 'blur' }
        // ],
        oldYardNo: [
          { required: true, message: '原库场号不能为空', trigger: 'blur' }
        ],
        oldYardNam: [
          { required: true, message: '原库场名不能为空', trigger: 'blur' }
        ],
        oldLocNo: [
          { required: true, message: '原货位不能为空', trigger: 'blur' }
        ],
        oldPkgKindCod: [
          { required: true, message: '原包装代码不能为空', trigger: 'blur' }
        ],
        oldCPkgKindNam: [
          { required: true, message: '原包装不能为空', trigger: 'blur' }
        ],
        oldFormat: [
          { required: true, message: '原规格不能为空', trigger: 'blur' }
        ],
        oldPiecesNum: [
          { required: true, message: '原件数不能为空', trigger: 'blur' }
        ],
        oldPiecesWgt: [
          { required: true, message: '原件重不能为空', trigger: 'blur' }
        ],
        oldWeightWgt: [
          { required: true, message: '原吨数不能为空', trigger: 'blur' }
        ],
        oldCargoVol: [
          { required: true, message: '原体积不能为空', trigger: 'blur' }
        ],
        feeId: [
          { required: true, message: '堆存费统计标志不能为空', trigger: 'blur' }
        ],
        ifendId: [
          { required: true, message: '完标不能为空', trigger: 'blur' }
        ],
        endTim: [
          { required: true, message: '完工时间不能为空', trigger: 'blur' }
        ],

        proveNo: [
          { required: true, message: 'proveNo不能为空', trigger: 'blur' }
        ],
        inProveNo: [
          { required: true, message: '过户单号不能为空', trigger: 'blur' }
        ],
        changeId: [
          { required: true, message: 'changeId不能为空', trigger: 'blur' }
        ],
        outTrainCod: [
          { required: true, message: 'outTrainCod不能为空', trigger: 'blur' }
        ],
        inTrainNo: [
          { required: true, message: 'inTrainNo不能为空', trigger: 'blur' }
        ],
        inTrainCod: [
          { required: true, message: 'inTrainCod不能为空', trigger: 'blur' }
        ],
        teleNo: [
          { required: true, message: '车站电报呼号不能为空', trigger: 'blur' }
        ],
        sanothNam: [
          { required: true, message: '车站省略名不能为空', trigger: 'blur' }
        ],
        stationNam: [
          { required: true, message: '站名不能为空', trigger: 'blur' }
        ],
        contNo: [
          { required: true, message: '合同号不能为空', trigger: 'blur' }
        ],
        inYardNo: [
          { required: true, message: 'inYardNo不能为空', trigger: 'blur' }
        ],
        inInoutId: [
          { required: true, message: '入库进出口标志不能为空', trigger: 'blur' }
        ],
        inTradeId: [
          { required: true, message: '入库内外贸标志不能为空', trigger: 'blur' }
        ],
        inContNo: [
          { required: true, message: 'inContNo不能为空', trigger: 'blur' }
        ],
        inBillNo: [
          { required: true, message: 'inBillNo不能为空', trigger: 'blur' }
        ],
        washId: [
          { required: true, message: '冲账标志不能为空', trigger: 'blur' }
        ],
        modiPortTxt: [
          { required: true, message: 'modiPortTxt不能为空', trigger: 'blur' }
        ],
        carNo: [
          { required: true, message: 'carNo不能为空', trigger: 'blur' }
        ],
        steelNo: [
          { required: true, message: 'steelNo不能为空', trigger: 'blur' }
        ],
        colorId: [
          { required: true, message: 'colorId不能为空', trigger: 'blur' }
        ],
        material: [
          { required: true, message: 'material不能为空', trigger: 'blur' }
        ],
        tradeContNo: [
          { required: true, message: 'tradeContNo不能为空', trigger: 'blur' }
        ],
        steelSeqNo: [
          { required: true, message: '卷板相关不能为空', trigger: 'blur' }
        ],
        jfDteIn: [
          { required: true, message: 'jfDteIn不能为空', trigger: 'blur' }
        ],
        jfDteOut: [
          { required: true, message: 'jfDteOut不能为空', trigger: 'blur' }
        ]
      },
      unitOptions: [],
      subunitOptions: [],
      inoutIdOptions: [],
      tradeIdOptions: [],
      inTypeOptions: [],
      shipperDocOptions: [],
      cargoOptions: [],
      inNumberList: [],
      declarationList: [],
      contractOptions: [],
      outTypeOptions: [],
      subunitOptions: [],
      fileLoading: false,
      fileList: [],
      fileOptions: [],
      weightUnitOptions: [],

    }
  },
  watch: {
    "dataForm.inCheckNum": {
      handler(newVal, oldVal) {
        if (newVal == "") {
          this.dataForm.weightWgt = ""
          this.dataForm.piecesNum = ""
        }
      }
    },
    "dataForm.inCustomsDeclaration": {
      handler(newVal, oldVal) {
        if (newVal == "") {
          this.dataForm.weightWgt = ""
          this.dataForm.piecesNum = ""
        }
      }
    }
  },
  methods: {

    init() {
      this.dataForm.unitCod = '14'
      this.getSubunit()
      this.setSubunitNam()
      this.fileOptions = []
      this.fileList = []
      getInoutIdDic().then(resp => {
        this.inoutIdOptions = resp.data.data
      })
      getTradeIdDic().then(resp => {
        this.tradeIdOptions = resp.data.data
      })
      getInyardType().then(resp => {
        this.inTypeOptions = resp.data.data
      })
      getOutYardType().then(resp => {
        this.outTypeOptions = resp.data.data
      })
      getInNumberListAndCustoms().then(resp => {
        this.inNumberList = resp.data.data.list
        this.declarationList = resp.data.data.list2
      })
      getShipperDoc().then(resp => {
        this.shipperDocOptions = resp.data.data
      })
      getCargoNam().then(resp => {
        this.cargoOptions = resp.data.data
      })
      getEffectiveInfo().then(resp => {
        this.contractOptions = resp.data.data
      })
      getWeightUnit().then(resp => {
        this.weightUnitOptions = resp.data.data
        console.log("this.weightUnitOptions",this.weightUnitOptions)
      })
      this.visible = true;
      this.canSubmit = true;
      getCunitNam().then(resp => {
        this.unitOptions = resp.data.data
      })



      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()

      })
    },
    getMessage(e) {
      console.log("1111",e.pieceNum)
      getId(e.inCheckNum).then(res =>{
        console.log("res",res.data.data)
        if(res.data.data =="吨"){
          this.dataForm.weightWgt = e.roughWet
        }else if(res.data.data =="千克"){
           
            this.dataForm.weightWgt =  Number(e.roughWet)/1000
        }else{
          this.dataForm.weightWgt = e.roughWet
          this.$notify.info("单位既不是吨也不是千克")
        }
      })
 
      this.dataForm.piecesNum = e.pieceNum
      this.dataForm.inCustomsDeclaration =e.inCustomsDeclaration
      // if (this.dataForm.inCustomsDeclaration) {
      //   this.$set(this.dataForm, "inCheckNum", "")
      // }
    },
    getMessage1(e){
      getBgId(e.inCustomsDeclaration).then(res =>{
        if(res.data.data =="吨"){
          this.dataForm.weightWgt = e.roughWet
        }else if(res.data.data =="千克"){
            this.dataForm.weightWgt = Number(e.roughWet)/1000
        }else{
          this.dataForm.weightWgt = e.roughWet
          this.$notify.info("单位既不是吨也不是千克")
        }
      })
      this.dataForm.piecesNum = e.pieceNum
      this.dataForm.inCheckNum =e.inCheckNum
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.canSubmit = false;
          this.dataForm.tallySeqNo = '1'
          this.dataForm.ioyardId = '1'
          this.dataForm.feeId = '0'
          this.dataForm.ifendId = '0'
          this.dataForm.shipNo = '13888'
          // this.dataForm.shipNam = '市出作业'
          //this.dataForm.outYardWay = '1'
          this.dataForm.inoutId = '1'
          this.dataForm.tradeId = '0'
          this.dataForm.customsDeclaration = this.dataForm.inCustomsDeclaration
          console.log(this.dataForm)
          addObj(this.dataForm).then(data => {
            for (var i in this.fileList) {
              this.fileList[i].tableName = 'inStrogeDetail'
              this.fileList[i].tableFid = data.data.data.inoutYardNo
            }
            fileSave(this.fileList)
            this.$notify.success('添加成功')
            this.visible = false
            this.$emit('refreshDataList')
          }).catch(() => {
            this.canSubmit = true;
          });
        }
      })
    },

    getSubunit() {
      this.dataForm.subunitCod = ''
      this.dataForm.subunitNam = ''
      getSubunit(this.dataForm.unitCod).then(resp => {
        this.subunitOptions = resp.data.data
      })
    },
    setSubunitNam() {
      for (var i in this.subunitOptions) {
        if (this.dataForm.subunitCod == this.subunitOptions[i].subunitCod) {
          this.dataForm.subunitNam = this.dataForm.subunitNam
        }
      }
    },
    cargoNamChanged() {
      for (var i in this.cargoOptions) {
        if (this.dataForm.cargoNamCod == this.cargoOptions[i].cargoNamCod) {
          this.dataForm.cargoNam = this.cargoOptions[i].cargoNam
          this.dataForm.cargoNo = '1'
          this.dataForm.cargoKindCod = this.cargoOptions[i].cargoKindCod
          this.dataForm.cargoKindNam = this.cargoOptions[i].cargoKindNam
        }
      }
    },
    subunitCodChange() {
      for (var i in this.subunitOptions) {
        if (this.dataForm.subunitCod == this.subunitOptions[i].subunitCod) {
          this.dataForm.subunitNam = this.subunitOptions[i].subunitNam
          this.dataForm.yardNam = this.subunitOptions[i].subunitNam
          this.dataForm.yardNo = this.subunitOptions[i].subunitCod
        }
      }
    },
    consignNamChange() {
      for (var i in this.shipperDocOptions) {
        if (this.dataForm.consignCod == this.shipperDocOptions[i].shipperCod) {
          this.dataForm.consignNam = this.shipperDocOptions[i].shipperDoc
        }
      }
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
        // if (this.dataForm.fId != 0) {
        //   var fileOne = [{
        //     bucketName: bucketName,
        //     fileName: fileName,
        //     url: url,
        //     original: originalFilename,
        //     fileSize: fileSize,
        //     tableName: 'contract',
        //     tableFid: this.dataForm.fId
        //   }]

        //   fileSave(fileOne).then(response => {
        //     if (response.data.data != null) {
        //       this.fileList[this.fileList.length - 1].id = response.data.data
        //     }
        //   })
        // }

        this.fileLoading = false
      }

    },
    onProgress(event, file, fileList) {
      this.fileLoading = true
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
    downloadFile: function (index) {
      var filename = this.fileList[index].fileName
      var bucket = this.fileList[index].bucketName
      handleDown(filename, bucket)
    },
  }
}
</script>
