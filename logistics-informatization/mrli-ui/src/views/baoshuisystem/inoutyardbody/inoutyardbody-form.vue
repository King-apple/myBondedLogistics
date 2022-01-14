<template>
  <el-dialog
    :title="!dataForm.fId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible"
    width="70%"
  >
    <el-form
      :model="dataForm"
      v-if="this.flg == 1"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="80px"

    >
      <el-row class="table-row">
        <el-col :span="12">
          <el-form-item label="合同编号" label-width="140px" prop="contNo">
            <!--<el-select
              clearable
              v-model="dataForm.contNo"
              style="width: 100%"
              placeholder="合同编号:"
              filterable
            >
              <el-option
                v-for="(item, index) in settledContractOptions"
                @click.native="selectFn(item)"
                :key="index"
                :label="item.contNo"
                :value="item.contNo"
              >
              </el-option>
            </el-select>-->
             <el-input
              v-model="dataForm.contNo"
              disabled
              placeholder="合同编号"
            ></el-input>
          </el-form-item>
        </el-col>
       <el-col :span="12">
          <!--<el-form-item label="品名" prop="cargoNam" label-width="140px">
            <el-input
              v-model="dataForm.cargoNam"
              disabled
              placeholder="品名"
            ></el-input>
          </el-form-item>-->

           <el-form-item
            label="品名"
            prop="cargoNam"
            label-width="140px"
          >
            <el-select
              clearable
              v-model="dataForm.cargoNam"
              placeholder="品名"
              style="width: 100%"
              filterable
            >
              <el-option
                v-for="(item, index) in settledCargoNamOptions"
                @click.native="selectCargoNamFn(item)"
                :key="index"
                :label="item.cargoNam"
                :value="item.cargoNam"
              >
              </el-option>
            </el-select>
            <!--<el-input v-model="dataForm.cargoNo" placeholder="H.S（商品编码）"></el-input>-->
          </el-form-item>
        </el-col>
      </el-row>
      <el-row class="table-row">
         <el-col :span="12">
          <el-form-item
            label="H.S（商品编码）"
            label-width="140px"
            prop="cargoNo"
          >
            <!--<el-select
              clearable
              v-model="dataForm.cargoNo"
              style="width: 100%"
              placeholder="H.S（商品编码）"
              filterable
            >
              <el-option
                v-for="(item, index) in settledCargoNoOptions"
                @click.native="selectCargoNoFn(item)"
                :key="index"
                :label="item.cargoCod"
                :value="item.cargoCod"
              >
              </el-option>
            </el-select>-->
            <el-input v-model="dataForm.cargoNo" :disabled="true" placeholder="H.S（商品编码）"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="规格" prop="format" label-width="140px">
            <el-input v-model="dataForm.format" placeholder="规格"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

       <el-row class="table-row">
        <el-col :span="12">
          <el-form-item label="报关单号" prop="inCustomsDeclaration" label-width="140px">
            <el-input
              v-model="dataForm.inCustomsDeclaration"
              placeholder="报关单号"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="核注清单号" prop="inCheckNum" label-width="140px">
            <el-input v-model="dataForm.inCheckNum" placeholder="核注清单号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row class="table-row">
        <el-col :span="12">
          <el-form-item label="件数" prop="pieceNum" label-width="140px">
            <el-input v-model="dataForm.pieceNum" placeholder="件数"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="净重" prop="netWet" label-width="140px">
            <el-input
              v-model="dataForm.netWet"

              @input="netWetValue"
              placeholder="净重"
            ></el-input><!--onkeyup="value=value.match(/\d+\.?\d{0,4}/)[0]"-->
          </el-form-item>
        </el-col>
      </el-row>
      <el-row class="table-row">
        <el-col :span="12">
          <el-form-item label="单价" prop="unitPrice" label-width="140px">
            <el-input
              v-model="dataForm.unitPrice"
              onkeyup="value=value.match(/\d+\.?\d{0,4}/)[0]"
              @input="unitPriceValue"
              placeholder="单价"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="总价" prop="totalPrice" label-width="140px">
            <el-input
              v-model="dataForm.totalPrice"
              placeholder="总价"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row class="table-row">
        <el-col :span="12">
          <el-form-item
            label="运输工具"
            prop="transportMeans"
            label-width="140px"
          >
            <el-input
              v-model="dataForm.transportMeans"
              placeholder="运输工具"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="进口国别"
            prop="importCountry"
            label-width="140px"
          >
            <el-select
              clearable
              v-model="dataForm.importCountry"
              style="width: 100%"
              placeholder="进口国别 "
              filterable
            >
              <el-option
                v-for="(item, index) in importCountryOptions"
                :key="index"
                :label="item.countryName"
                :value="item.countryName"
              >
              </el-option>
            </el-select>
            <!--<el-input v-model="dataForm.importCountry" placeholder="进口国别"></el-input>-->
          </el-form-item>
        </el-col>
      </el-row>

      <el-row class="table-row">
        <el-col :span="12">
          <el-form-item label="自重" prop="deadLoad" label-width="140px">
            <el-input v-model="dataForm.deadLoad" placeholder="自重"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="毛重" prop="roughWet" label-width="140px">
            <el-input v-model="dataForm.roughWet" placeholder="毛重"></el-input>
          </el-form-item>
        </el-col>
        <!--<el-col :span="12">
          <el-form-item label="存放区域" prop="locate" label-width="140px">
            <el-input v-model="dataForm.locate" placeholder="存放区域"></el-input>
          </el-form-item>
        </el-col>-->

      </el-row>

      <el-row class="table-row">
      	<el-col :span="12">
          <el-form-item label="货类名称" v-show="this.flg == 1" prop="cargoKindNam" label-width="140px">
          	<el-select
              clearable
              v-model="dataForm.cargoKindNam"
              style="width: 100%"
              placeholder="货类名称 "
              filterable
            >
              <el-option
                v-for="(item, index) in cargoKindOptions"
                @click.native="selectcargoKindFn(item)"
                :key="index"
                :label="item.cargoKindNam"
                :value="item.cargoKindNam"
              >
              </el-option>
            </el-select>
            <!--<el-input v-model="dataForm.cargoKindNam" placeholder="货类名称"></el-input>-->
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="备注" prop="markTxt" label-width="140px">
            <el-input v-model="dataForm.markTxt" placeholder="备注"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <el-form
      :model="dataForm"
      v-else
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="80px"
    >
      <el-row class="table-row">
        <el-col :span="12">
          <el-form-item label="合同编号" prop="contNo" label-width="140px">
            <!--<el-select
              clearable
              v-model="dataForm.contNo"
              style="width: 100%"
              placeholder="合同编号:"
              filterable
            >
              <el-option
                v-for="(item, index) in settledContractOptions"
                @click.native="selectFn(item)"
                :key="index"
                :label="item.contNo"
                :value="item.contNo"
              >
              </el-option>
            </el-select>-->
            <el-input
              v-model="dataForm.contNo"
              disabled
              placeholder="合同编号"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <!--<el-form-item label="品名" prop="cargoNam" label-width="140px">
            <el-input v-model="dataForm.cargoNam" placeholder="品名"></el-input>
          </el-form-item>-->
          <el-form-item
            label="品名"
            prop="cargoNam"
            label-width="140px"
          >
            <el-select
              clearable
              v-model="dataForm.cargoNam"
              placeholder="品名"
              style="width: 100%"
              filterable
            >
              <el-option
                v-for="(item, index) in settledCargoNamOptions"
                @click.native="selectCargoNamFn(item)"
                :key="index"
                :label="item.cargoNam"
                :value="item.cargoNam"
              >
              </el-option>
            </el-select>
            <!--<el-input v-model="dataForm.cargoNo" placeholder="H.S（商品编码）"></el-input>-->
          </el-form-item>
        </el-col>
      </el-row>

      <el-row class="table-row">
        <el-col :span="12">
          <el-form-item
            label="H.S（商品编码）"
            prop="cargoNo"
            label-width="140px"
          >
            <!--<el-select
              clearable
              v-model="dataForm.cargoNo"
              placeholder="H.S（商品编码）"
              style="width: 100%"
              filterable
            >
              <el-option
                v-for="(item, index) in settledCargoNoOptions"
                @click.native="selectCargoNoFn(item)"
                :key="index"
                :label="item.cargoCod"
                :value="item.cargoCod"
              >
              </el-option>
            </el-select>-->
            <el-input v-model="dataForm.cargoNo" :disabled="true" placeholder="H.S（商品编码）"></el-input>
          </el-form-item>
        </el-col>
       <el-col :span="12">
          <el-form-item label="出区报关单号" prop="outCustomsDeclaration" label-width="140px">
            <el-input v-model="dataForm.outCustomsDeclaration" placeholder="出区报关单号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

       <el-row class="table-row">
        <el-col :span="12">
          <el-form-item label="原进区报关单号" prop="inCustomsDeclaration" label-width="140px">
            <el-input v-model="dataForm.inCustomsDeclaration" placeholder="原进区报关单号"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="原进区核注清单号" prop="inCheckNum" label-width="140px">
            <el-input v-model="dataForm.inCheckNum" placeholder="原进区核注清单号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

       <el-row class="table-row">
        <el-col :span="12">
          <el-form-item label="出区核注清单号" prop="outCheckNum" label-width="140px">
            <el-input v-model="dataForm.outCheckNum" placeholder="出区核注清单号"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="毛重" prop="roughWet" label-width="140px">
            <el-input v-model="dataForm.roughWet" placeholder="毛重"></el-input>
          </el-form-item>
        </el-col>
        <!--<el-col :span="12">
          <el-form-item label="存放区域" prop="locate" label-width="140px">
            <el-input v-model="dataForm.locate" placeholder="存放区域"></el-input>
          </el-form-item>
        </el-col>-->
      </el-row>



      <el-row class="table-row">
        <el-col :span="12">
          <el-form-item label="件数" prop="pieceNum" label-width="140px">
            <el-input v-model="dataForm.pieceNum" placeholder="件数"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="桶数" prop="bucketNum" label-width="140px">
            <el-input
              v-model="dataForm.bucketNum"
              placeholder="桶数"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row class="table-row">
        <el-col :span="12">
          <el-form-item
            label="目的国"
            prop="distinationCountry"
            label-width="140px"
          >
           <el-select
              clearable
              v-model="dataForm.distinationCountry"
              style="width: 100%"
              placeholder="目的国 "
              filterable
            >
              <el-option
                v-for="(item, index) in importCountryOptions"
                :key="index"
                :label="item.countryName"
                :value="item.countryName"
              >
              </el-option>
            </el-select>
            <!--<el-input
              v-model="dataForm.distinationCountry"
              placeholder="目的国"
            ></el-input>-->

          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="净重" prop="netWet" label-width="140px">
            <el-input
              v-model="dataForm.netWet"

              @input="netWetValue"
              placeholder="净重"
            ></el-input><!--onkeyup="value=value.match(/\d+\.?\d{0,4}/)[0]"-->
          </el-form-item>
        </el-col>
      </el-row>
      <el-row class="table-row">
        <el-col :span="12">
          <el-form-item label="单价" prop="unitPrice" label-width="140px">
            <el-input
              v-model="dataForm.unitPrice"
              onkeyup="value=value.match(/\d+\.?\d{0,4}/)[0]"
              @input="unitPriceValue"
              placeholder="单价"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="总价" prop="totalPrice" label-width="140px">
            <el-input
              v-model="dataForm.totalPrice"
              placeholder="总价"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row class="table-row">
        <el-col :span="12">
          <el-form-item
            label="运输工具"
            prop="transportMeans"
            label-width="140px"
          >
            <el-input
              v-model="dataForm.transportMeans"
              placeholder="运输工具"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="进口国别"
            prop="importCountry"
            label-width="140px"
          >
            <el-select
              clearable
              v-model="dataForm.importCountry"
              style="width: 100%"
              placeholder="进口国别 "
              filterable
            >
              <el-option
                v-for="(item, index) in importCountryOptions"
                :key="index"
                :label="item.countryName"
                :value="item.countryName"
              >
              </el-option>
            </el-select>
            <!--<el-input v-model="dataForm.importCountry" placeholder="进口国别"></el-input>-->
          </el-form-item>
        </el-col>
      </el-row>

      <el-row class="table-row">
        <el-col :span="12">
          <el-form-item label="自重" prop="deadLoad" label-width="140px">
            <el-input v-model="dataForm.deadLoad" placeholder="自重"></el-input>
          </el-form-item>
        </el-col>
         <el-col :span="12">
          <el-form-item label="规格" prop="format" label-width="140px">
            <el-input v-model="dataForm.format" placeholder="规格"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row class="table-row">
<el-col :span="12">
          <el-form-item label="货类名称" v-show="this.flg == 2" prop="cargoKindNam" label-width="140px">
          	<el-select
              clearable
              v-model="dataForm.cargoKindNam"
              style="width: 100%"
              placeholder="货类名称 "
              filterable
            >
              <el-option
                v-for="(item, index) in cargoKindOptions"
                @click.native="selectcargoKindFn(item)"
                :key="index"
                :label="item.cargoKindNam"
                :value="item.cargoKindNam"
              >
              </el-option>
            </el-select>
            <!--<el-input v-model="dataForm.cargoKindNam" placeholder="货类名称"></el-input>-->
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="备注" prop="markTxt" label-width="140px">
            <el-input v-model="dataForm.markTxt" placeholder="备注"></el-input>
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
import { getObj, addObj, putObj } from '@/api/baoshuisystem/inoutyardbody'
import { fetchList } from '@/api/baoshuisystem/contract'
import { getSelectContract,getObj as getObjContract } from '@/api/baoshuisystem/contract'
import { remote } from '@/api/admin/dict'
import { all as goodscategoryAll } from '@/api/baoshuisystem/goodscategory'
import { fetchList as countryList } from '@/api/baoshuisystem/countryinfo'
import { fetchList as cargoList ,getCargonamcustomsAll } from '@/api/baoshuisystem/cargonamcustoms'


export default {
  data() {
    return {
      visible: false,
      canSubmit: false,
      dataForm: {
        fid: 0,
        hFid: '',
        cFid: '',
        contNo: '',
        cargoNo: '',
        cargoNam: '',
        format: '',
        pieceNum: '',
        bucketNum: '',
        roughWet: '',
        netWet: '',
        unitPrice: '',
        unitPriceKnd: '',
        totalPrice: '',
        unit: '',
        transportMeans: '',
        importCountry: '',
        distinationCountry: '',
        deadLoad: '',
        checkNo: '',
        markTxt: '',
        operNam: '',
        recordTim: '',
        updateNam: '',
        updateTim: '',
        delNam: '',
        delTim: '',
        cargoKindNo: '',
        cargoKindNam: '',
        inoutId: '',
        tradeId: '',
        billNo: '',
        //					addMinRat: '',
        unitCod: '',
        unitNam: '',
        //					subunitCod: '',
        //					subunitNam: '',
        //					feeWayId: '',
        //					feeWayNam: '',
        locate: '',
        inCustomsDeclaration: '',
        inCheckNum: '',
        outCheckNum: '',
        outCustomsDeclaration: ''

      },
      settledContractOptions: [],
      //				unitPriceKndOptions: [],
      importCountryOptions: [],
      settledCargoNoOptions: [],
      settledCargoNamOptions: [],
      cargoKindOptions: [],

      //				unitOptions: [],
      boundFlagOptions: [{
        label: "否",
        value: "0"
      },
      {
        label: "是",
        value: "1"
      }
      ],
      flg: 0,
      dataRule: {
        cargoNo: [{
          required: true,
          message: 'H.S（商品编码）不能为空',
          trigger: 'blur'
        }],
        cargoNam: [{
          required: true,
          message: '品名不能为空',
          trigger: 'blur'
        }],
        netWet: [{
          required: true,
          message: '净重不能为空',
          trigger: 'blur'
        }],
        unitPrice: [{
          required: true,
          message: '单价不能为空',
          trigger: 'blur'
        }],
        totalPrice: [{
          required: true,
          message: '总价不能为空',
          trigger: 'blur'
        }],
        boundFlag: [{
          required: true,
          message: '是否需要插入衡重申请单(0:否,1:是)不能为空',
          trigger: 'blur'
        }],
        cargoKindNam: [{
        	required: true,
          message: '货类不能为空',
          trigger: 'blur'
        }]
      }
    }
  },
  methods: {
    init(id, hFid, flg,contNo,cFid) {
      this.flg = flg
      this.dataForm.contNo = contNo;
      this.dataForm.cFid = cFid
      this.dataForm.inOut = this.flg==1 ? 1:0
      this.getSelectContractList();
      //				this.getunitPriceKndType();
      //				this.getimportCountryType()
      this.cargoList();
      this.goodscategoryAll();
      this.country()
      this.dataForm.fId = id || 0;
      this.dataForm.hFid = hFid
      this.visible = true;
      this.canSubmit = true;
      this.dataForm.unitNam = '营口港务集团物流公司';
      this.dataForm.unitCod = 14;
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.fId) {
          getObj(this.dataForm.fId).then(response => {
            console.log(response.data.data)
            this.dataForm = response.data.data
          })
        }
      })
    },
    getSelectContractList() {
    	getObjContract(this.dataForm.cFid).then((res) => {
			  console.log(res)
			  this.dataForm.cFid = res.data.data.fId
		      this.dataForm.contNo = res.data.data.contNo
		      this.dataForm.inoutId = res.data.data.inoutId
		      this.dataForm.tradeId = res.data.data.tradeId
    	})
//    getSelectContract().then((res) => {
//      console.log(res)
//      this.settledContractOptions = res.data.data
//    })
    },
    selectFn(e) {
      console.log(e)
      this.dataForm.cFid = e.fId
      this.dataForm.contNo = e.contNo
      //				this.dataForm.cargoKindNo = e.cargoKindCod
      //				this.dataForm.cargoKindNam = e.cargoKindNam
      //				this.dataForm.cargoNo = e.cargoCod
      //				this.dataForm.cargoNam = e.cargoNam
      this.dataForm.inoutId = e.inoutId
      this.dataForm.tradeId = e.tradeId
    },
    selectCargoNoFn(e) {
      this.dataForm.cargoNam = e.cargoNam
    },
    selectcargoKindFn(e){

    	this.dataForm.cargoKindNo = e.cargoKindCod
    },

    selectCargoNamFn(e) {
    	console.log(e)
      this.dataForm.cargoNo = e.cargoCod
    },
    cargoList() {
      cargoList(Object.assign({
      })).then((res) => {
        console.log(res)
        this.settledCargoNoOptions = res.data.data.records
      })

      getCargonamcustomsAll().then((res) => {
        console.log(res)
        this.settledCargoNamOptions = res.data.data
      })

    },
    goodscategoryAll(){
    	goodscategoryAll().then((res) => {
    		console.log(res)
    		this.cargoKindOptions = res.data.data
    	})
    },
    selectCargoKindNam(e) {
      console.log(e)

    },
    country() {
      countryList(Object.assign({
        current: this.pageIndex,
        size: this.pageSize
      })).then((res) => {
        console.log(res)
        this.importCountryOptions = res.data.data.records
      })
    },
    netWetValue(val) {
    	console.log(val)
    	if(isNaN(val)){
			this.dataForm.totalPrice = 0
		}else{
			this.dataForm.netWet = val.match(/\d+\.?\d{0,4}/)[0]
	      console.log(this.dataForm)
	      if (this.dataForm.netWet && this.dataForm.unitPrice) {
	        console.log(val, this.dataForm.unitPrice)
	        let num = this.dataForm.netWet.match(/\d+\.?\d{0,4}/)[0] * this.dataForm.unitPrice
	        this.dataForm.totalPrice = num.toFixed(2)
	      } else {
	        this.dataForm.totalPrice = 0
	      }
		}


    },
    unitPriceValue(val) {
      this.dataForm.unitPrice = val.match(/\d+\.?\d{0,4}/)[0]
      console.log(this.dataForm)
      if (this.dataForm.netWet && this.dataForm.unitPrice) {
        console.log(val, this.dataForm.unitPrice)
        let num = this.dataForm.netWet * this.dataForm.unitPrice.match(/\d+\.?\d{0,4}/)[0]
        this.dataForm.totalPrice = num.toFixed(2)
      } else {
        this.dataForm.totalPrice = 0
      }
    },
    // 表单提交
    dataFormSubmit() {
      console.log(this.dataForm)
      //				return;
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.canSubmit = false;
          if (this.dataForm.fId) {
            this.dataForm.showFlag = 1
            putObj(this.dataForm).then(data => {
              this.$notify.success('修改成功')
              this.visible = false
              this.$emit('refreshDataList')
            }).catch(() => {
              this.canSubmit = true;
            });
          } else {
            this.dataForm.showFlag = 1
            addObj(this.dataForm).then(data => {
              this.$notify.success('添加成功')
              this.visible = false
              this.$emit('refreshDataList')
            }).catch(() => {
              this.canSubmit = true;
            });
          }
        }
      })
    }
  }
}
</script>
<style>
.text {
  font-size: 14px;
}

.item {
  padding: 18px 0;
}

.box-card {
  width: 480px;
}
</style>
