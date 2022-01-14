<template>
  <el-dialog
    :title="!dataForm.fId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible"
    width="80%"
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="80px"
    >
      <el-row>
        <el-col :span="8">
          <el-form-item label="合同编号" prop="contNo" style="width: 280px">
            <el-select
              clearable
              v-model="dataForm.contNo"
              placeholder="合同编号:"
              filterable
              :disabled="disabled"
            >
              <el-option
                v-for="item in settledContractOptions"
                @click.native="selectFn(item)"
                :key="item.fId"
                :label="item.contNo"
                :value="item.contNo"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="出入库" prop="ioyardListNo" style="width: 280px">
            <el-select
              clearable
              v-model="dataForm.ioyardListNo"
              placeholder="出入库明细编号"
              filterable
              :disabled="disabled"
            >
              <el-option
                v-for="item in ioyardListNoOptions"
                @click.native="selectNo(item)"
                :key="item.fId"
                :label="item.ioyardListNo"
                :value="item.ioyardListNo"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="承办单位" prop="firstNam" style="width: 280px">
            <el-select
              v-model="dataForm.firstNam"
              placeholder="请选择"
              filterable
              clearable
              style="width: 100%"
            >
              <el-option
                v-for="item in options"
                :key="item.organizer"
                :label="item.organizer"
                :value="item.organizer"
                @click.native="selectOp(item)"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="制表日期" prop="operDat">
            <el-date-picker
              v-model="dataForm.operDat"
              type="date"
              placeholder="日期"
              value-format="yyyy-MM-dd"
              style="width: 200px"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item
            label="发票类型"
            prop="invoiceType"
            style="width: 280px"
          >
            <el-select
              clearable
              v-model="dataForm.invoiceType"
              placeholder="发票类型:"
              filterable
            >
              <el-option
                v-for="(item, index) in invoicTpyeDict"
                :key="index"
                :label="item.label"
                :value="item.label"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="备注" prop="markTxt" style="width: 280px">
            <el-input v-model="dataForm.markTxt" placeholder="备注"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="出境方式" prop="outYardWay" style="width: 280px">
            <el-input
              v-model="dataForm.outYardWay"
              placeholder="出境方式"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="结算单号" prop="billCod" style="width: 280px">
            <el-input
              v-model="dataForm.billCod"
              placeholder="新增后自动生成"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="入境方式" prop="inYardWay" style="width: 280px">
            <el-input
              v-model="dataForm.inYardWay"
              placeholder="入境方式"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="承办部门" prop="dept" style="width: 280px">
            <el-input
              v-model="dataForm.dept"
              placeholder="承办部门"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="客户名称" prop="secondNam" style="width: 280px">
            <el-input
              v-model="dataForm.secondNam"
              placeholder="客户名称"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-row>
          <el-col :span="8">
            <el-form-item
              label="入境船名"
              prop="inShipName"
              style="width: 280px"
            >
              <el-input
                v-model="dataForm.inShipName"
                placeholder="船名"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item
            label="货类名称"
            prop="cargoKindNam"
            style="width: 180px"
          >
            <el-select
              v-model="dataForm.cargoKindNam"
              placeholder="货类名称"
              filterable
              clearable
            >
              <el-option
                v-for="(item, index) in carGoKindOptions"
                :key="index"
                :label="item.cargoKindNam"
                :value="item.cargoKindNam"
                @click.native="selectCGK(item)"
              >
              </el-option>
            </el-select>
            <el-input
              v-model="dataForm.cargoKindCod"
              placeholder="货类编码"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="货物名称" prop="cargoNam" style="width: 180px">
            <el-select
              v-model="dataForm.cargoNam"
              placeholder="货物名称"
              filterable
              clearable
            >
              <el-option
                v-for="(item, index) in cargoNamOptions"
                :key="index"
                :label="item.cargoNam"
                :value="item.cargoNam"
                @click.native="selectCNO(item)"
              >
              </el-option>
            </el-select>
            <el-input
              v-model="dataForm.cargoCod"
              placeholder="货物代码"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item
            label="出境船名"
            prop="outShipName"
            style="width: 280px"
          >
            <el-input
              v-model="dataForm.outShipName"
              placeholder="船名"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>费目明细</span>
          <el-button
            style="float: right; padding: 3px 0"
            type="text"
            @click="addFee"
            >点击新增</el-button
          >
          <hr />
          <div v-for="(feeItemInforItem, index) of feeItemInfor" :key="index">
            <div style="display: flex">
              <el-form-item label="费目名">
                <el-select
                  v-model="feeItemInforItem.feeNam"
                  placeholder="请选择"
                  filterable
                  @focus="getFeeNam"
                  @loading="feeLoading"
                  @change="feeNamChange($event, index)"
                  style="width: 120px"
                >
                  <el-option
                    v-for="(item, feeIndex) in feeNameList"
                    :key="feeIndex"
                    :label="item.feeNam"
                    :value="item.feeNam"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="费率单位">
                <el-select
                  v-model="feeItemInforItem.feeUnit"
                  placeholder="请选择"
                  filterable
                  @focus="feeAll"
                  @loading="feeLoading"
                  style="width: 120px"
                >
                  <el-option
                    v-for="(item1, feeUnitIndex) in feeUnitList"
                    :key="feeUnitIndex"
                    :label="item1.feeUnit"
                    :value="item1.feeUnit"
                  >
                  </el-option
                ></el-select>
              </el-form-item>
              <el-form-item label="费率值">
                <el-input
                  v-model="feeItemInforItem.feeRat"
                  placeholder="费率值"
                  style="width: 120px"
                ></el-input>
              </el-form-item>
              <el-form-item label="备注">
                <el-input
                  v-model="feeItemInforItem.markTxt"
                  placeholder="备注"
                  style="width: 120px"
                ></el-input>
              </el-form-item>
              <el-form-item>
                <el-button
                  type="danger"
                  @click="deleteRow(index, feeItemInfor, dataForm)"
                  icon="el-icon-remove-outline"
                  >删除</el-button
                >
              </el-form-item>
            </div>
          </div>
        </div>
      </el-card>
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
  getObj,
  addObj,
  putObj,
  getAllInFo,
  expertBusiness,
} from "@/api/baoshuisystem/ioyardsettlementbillshead";
import {
  addObj as addFeeMessage, getObj as getFeeMeaage, delObj as delFeeMessage
  , putObj as putFeeMessage
} from "@/api/baoshuisystem/settlementheadrate";
import { addObj as addrate } from "@/api/baoshuisystem/settlementbodyrate";
import {
  getFeenamesAll as feeFetch,
  saveList as contractSaveList,
  getRateList,
  removeBycFid,
} from "@/api/baoshuisystem/feenames";
import { getInvoicTpye } from "@/api/baoshuisystem/externalpayment";
import {
  fileSave,
  getFileInfo,
  delFile,
  handleDown,
  getEffectiveInfo,
  fileUpdate,
  getInsertValue,
  getUserInfo
} from "@/api/baoshuisystem/contract";
import { getObj as getCompany } from "@/api/baoshuisystem/parameterlist";
import {
  getInyardType,
  getOutyardType,
} from "@/api/baoshuisystem/instrogedetail";
import moment from "moment";
import { getObj as getObjDept } from "@/api/admin/dept";
import { onlyNumOnePoint } from "@/util/util";
import {
  getObj as getIoYardMessage,
  addObj as addIoYardMessage,
  delObj as delIoYardMessage,
  putObj as putIoYardMessage,
} from "@/api/baoshuisystem/ioyardsettlementbillsbody";
import { all as carGoKindAll } from "@/api/baoshuisystem/goodscategory";
import { getAll as feeunitAll } from "@/api/baoshuisystem/feeunits";
import store from "@/store";
import ElementUI from 'element-ui';
export default {
  data() {
    return {
      feeLoading: [],
      feeNameList: [],
      options: [],
      carGoKindOptions: [],
      headers: {
        Authorization: "Bearer " + store.getters.access_token,
      },
      fileOptions: [],
      cargoNamOptions: [],
      fileLoading: false,
      tempManagement: 6,
      tempCharges: 0.2,
      fileList: [],
      addCount: 0,
      statementsList: [],
      inyardTypeOptions: [],
      outyardTypeOptions: [],
      ioyardListNoOptions: [],
      settledContractOptions: "",
      invoicTpyeDict: "",
      visible: false,
      canSubmit: false,
      inCount: 0,
      outCount: 0,
      dayCount: 0,
      tonDayCount: 0,
      disabled: false,
      allCount: 0,
      managementMoneyCount: 0,
      storageMoneyCount: 0,
      feeUnitList: [],
      feeNameCod: "",
      feeItemInfor: [],
      dataForm: {
        fId: "",
        companyNam: "",
        billNam: "出入库结算单",
        secondCod: "",
        secondNam: "",
        listFid: "",
        ioyardListNo: "",
        inYardWay: "",
        contFid: "",
        contNo: "",
        firstNam: "",
        firstCod: "",
        dept: "",
        cargoCod: "",
        cargoNam: "",
        outYardWay: "",
        billCod: "",
        markTxt: "",
        operDat: "",
        operNam: "",
        recordTim: "",
        updateNam: "",
        updateTim: "",
        delNam: "",
        delTim: "",
        invoiceType: "",
        managementMoney: "",
        storageMoney: "",
        outWgt: "",
        tonDay: "",
        cargoKindNam: "",
        cargoKindCod: "",
        inShipName: "",
        outShipName: "",
      },
      dataRule: {
        // billNam: [
        //   { required: true, message: "单据名称不能为空", trigger: "blur" },
        // ],
        secondCod: [
          { required: true, message: "客户代码不能为空", trigger: "blur" },
        ],
        secondNam: [
          { required: true, message: "客户名称不能为空", trigger: "blur" },
        ],
        listFid: [
          {
            required: true,
            message: "出入库明细表主键不能为空",
            trigger: "blur",
          },
        ],
        ioyardListNo: [
          {
            required: true,
            message: "出入库明细编码不能为空",
            trigger: "blur",
          },
        ],
        inYardWay: [
          { required: true, message: "入境船名不能为空", trigger: "blur" },
        ],
        contFid: [
          { required: true, message: "合同主键不能为空", trigger: "blur" },
        ],
        contNo: [
          { required: true, message: "合同编号不能为空", trigger: "blur" },
        ],
        firstNam: [
          { required: true, message: "承办单位不能为空", trigger: "blur" },
        ],
        firstCod: [
          { required: true, message: "承办单位代码不能为空", trigger: "blur" },
        ],
        dept: [
          { required: true, message: "承办部门不能为空", trigger: "blur" },
        ],
        cargoCod: [
          { required: true, message: "货物代码不能为空", trigger: "blur" },
        ],
        // cargoNam: [
        //   { required: true, message: "货物名称不能为空", trigger: "blur" },
        // ],
        outYardWay: [
          { required: true, message: "出境船名不能为空", trigger: "blur" },
        ],
        // billCod: [
        //   { required: true, message: "结算单编号不能为空", trigger: "blur" },
        // ],
        // markTxt: [{ required: true, message: "备注不能为空", trigger: "blur" }],
        operDat: [
          { required: true, message: "制表日期不能为空", trigger: "blur" },
        ],
        operNam: [
          { required: true, message: "添加人不能为空", trigger: "blur" },
        ],
        recordTim: [
          { required: true, message: "添加时间不能为空", trigger: "blur" },
        ],
        updateNam: [
          { required: true, message: "修改人不能为空", trigger: "blur" },
        ],
        updateTim: [
          { required: true, message: "修改时间不能为空", trigger: "blur" },
        ],
        delNam: [
          { required: true, message: "删除人不能为空", trigger: "blur" },
        ],
        delTim: [
          { required: true, message: "删除时间不能为空", trigger: "blur" },
        ],
        invoiceType: [
          { required: true, message: "发票类型不能为空", trigger: "blur" },
        ],
      },
    };
  },
  computed: {
    statementsDataNew() {
      return JSON.parse(JSON.stringify(this.statementsList));
    },
  },
  watch: {
    statementsDataNew: {
      handler(newVal, oldVal) {
        console.log(newVal)
        let temp1 = 0;
        let temp2 = 0;
        let temp3 = 0;
        let temp4 = 0;
        let temp5 = 0;
        let temp6 = 0;
        let temp7 = 0;
        for (let index = 0; index < newVal.length; index++) {
          console.log(newVal[index])
          temp1 += Number(newVal[index].inSettleWgt)
          temp2 += Number(newVal[index].outSettleWgt)
          temp3 += Number(newVal[index].dayNum)
          temp4 += Number(newVal[index].tonDayNum)
          temp5 += Number(newVal[index].totalMoney)
          temp6 += Number(newVal[index].managementMoney)
          temp7 += Number(newVal[index].storageMoney)
          this.inCount = temp1
          this.outCount = temp2
          this.dayCount = temp3
          this.tonDayCount = temp4
          this.allCount = temp5.toFixed(2)
          this.managementMoneyCount = temp6.toFixed(2)
          this.storageMoneyCount = temp7.toFixed(2)
        }
        for (let index = 0; index < this.statementsList.length; index++) {
          const element = this.statementsList[index];
          //计算总和
          if (element.inYardDat != null && element.outYardDat != null) {
            let date1 = new Date(element.inYardDat);
            let date2 = new Date(element.outYardDat);
            //获取当前时间
            let m1 = moment(date1);
            //获取需要对比的时间
            let m2 = moment(date2);
            //计算相差多少天 day可以是second minute
            let day = m2.diff(m1, "day") + 1;
            if (day > 0) {
              element.dayNum = day;
            }
            if (element.outSettleWgt != null && element.dayNum != null) {
              element.tonDayNum = element.outSettleWgt * element.dayNum;
              element.managementMoney = (element.outSettleWgt * this.tempManagement).toFixed(2);
              element.storageMoney = (element.tonDayNum * this.tempCharges).toFixed(2);
              element.totalMoney = (Number(element.managementMoney) + Number(element.storageMoney)).toFixed(2);
            }
          }
        }
      },
      deep: true, //深度监听 监听数组对象的属性增删改查
      immediate: true, //初始化就监听
    },

    "dataForm.ioyardListNo": {
      handler(newVal, oldVal) {
        if (newVal == "") {
          this.statementsList = [];
        }
      },
    },
    "dataForm.contNo": {
      handler(newVal, oldVal) {
        if (newVal == "") {
          this.dataForm.cargoKindNam = "";
          this.dataForm.cargoNam = "";
          this.dataForm.firstNam = "";
          this.dataForm.invoiceType = "";
        }
      },
    },
    "dataForm.cargoKindNam": {
      handler(newVal, oldVal) {
        if (newVal == "") {
          this.dataForm.cargoKindCod = "";
        }
      },
    },
    "dataForm.cargoNam": {
      handler(newVal, oldVal) {
        if (newVal == "") {
          this.dataForm.cargoCod = "";
        }
      },
    },
  },
  methods: {
    deleteRow(index, data, dataForm) {
      this.addCount--
      console.log(data[index].fid)
      console.log("this.addCount", this.addCount)
      if (dataForm.fId != "" && data[index].fid != "") {
        this.$confirm("此操作将永久删除该费目, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            delFeeMessage(data[index]).then((response) => {
              this.$message({
                type: "success",
                message: "删除成功!",
              });
              data.splice(index, 1);
            });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除",
            });
          });
      } else {
        data.splice(index, 1);
      }
    },
    feeNamChange: function (data, index) {
      let tempCod = ""
      this.feeNameList.forEach(res => {
        if (res.feeNam == data) {
          tempCod = res.feeNameCod
        }
      })
      this.feeItemInfor[index].feeNameCod = tempCod
    },

    feeAll() {
      feeunitAll().then((response) => {
        this.feeUnitList = response.data.data;
        this.feeLoading = false;
      });
    },
    getFeeNam: function () {
      if (this.feeNameList.length == 0) {
        this.feeLoading = true;
        feeFetch().then((response) => {
          this.feeNameList = response.data.data

        });
        feeunitAll().then((response) => {
          this.feeUnitList = response.data.data;
          this.feeLoading = false;
        });
      }
    },
    addFee: function () {
      this.addCount++
      console.log("this.addCount", this.addCount)
      this.feeItemInfor.push({
        fid: "",
        hFid: "",
        feeNameCod: "",
        feeNam: "",
        feeRat: 0,
        feeUnit: "",
        markTxt: "",
      });
    },
    init(id) {
      this.getCompanyName();
      this.getInfoShip();
      this.getInfo();
      this.getSelectContractList();
      this.dataForm.fId = id || "";
      if (id == undefined) {
        this.disabled = false
        this.statementsList = [];
        this.feeItemInfor = []
        //新增自动获取日期
        this.getDate();
      }
      this.visible = true;
      this.canSubmit = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
        if (this.dataForm.fId) {
          this.addCount = 0
          this.disabled = true
          getObj(this.dataForm.fId).then((response) => {
            this.getFileInfoUpdate();
            this.dataForm = response.data.data;
            // getIoYardMessage(this.dataForm.fId).then((res) => {
            //   this.statementsList = res.data.data;
            // });
            getFeeMeaage(this.dataForm.fId).then((res) => {
              this.feeItemInfor = res.data.data;
            });
          });
        }
      });
      this.fileOptions = [];
      this.fileList = []
    },
    onlyNumOnePoint,
    oninput(event, index) {
      this.statementsList[index].inSettleWgt = this.onlyNumOnePoint(event);

    },
    oninput2(event, index) {
      this.statementsList[index].outSettleWgt = this.onlyNumOnePoint(event);
    },
    getInfo() {
      getUserInfo().then((res) => {
        this.dataForm.deptId = res.data.data.sysUser.deptSubId
        getObjDept(res.data.data.sysUser.deptSubId).then((res) => {
          this.dataForm.dept = res.data.data.name;
        });
      });
      getInsertValue().then((response) => {
        this.options = response.data.data.organizerInfos;
        this.cargoNamOptions = response.data.data.contractObjects;
      });
      carGoKindAll().then((response) => {
        this.carGoKindOptions = response.data.data;
      }),
        getInvoicTpye().then((response) => {
          this.invoicTpyeDict = response.data.data;
        });
    },
    getCompanyName() {
      getCompany(7).then((response) => {
        this.dataForm.companyNam = response.data.data.paramValue;
      });
    },
    getDate() {
      this.dataForm.operDat = new Date();
    },
    selectCGK(item) {
      this.dataForm.cargoKindCod = item.cargoKindCod;
    },
    selectOp(data) {
      this.dataForm.firstCod = data.organizerCode;
    },
    selectFn(item) {
      if(item.contractRateList!= undefined){
          this.feeItemInfor = Object.assign(item.contractRateList)
      }
      this.dataForm.contFid = item.fId;
      this.dataForm.secondNam = item.secondNam;
      this.dataForm.secondCod = item.secondCod;
      this.dataForm.cargoCod = item.cargoCod;
      this.dataForm.cargoNam = item.cargoNam;
      this.dataForm.firstNam = item.firstNam;
      this.dataForm.firstCod = item.firstCod;
      getAllInFo(item.contNo).then((response) => {
        this.ioyardListNoOptions = response.data.data;
      });
    },
    getSelectContractList() {
      getEffectiveInfo().then((res) => {
        this.settledContractOptions = res.data.data;
      });
    },
    getManagementMoney(index) {
      this.statementsList[index].managementMoney =
        this.statementsList[index].outSettleWgt * this.tempManagement;
      return this.statementsList[index].managementMoney == ""
        ? ""
        : this.statementsList[index].managementMoney;
    },
    getStorageMoney(index) {
      this.statementsList[index].storageMoney =
        this.statementsList[index].tonDayNum * this.tempCharges;
      return this.statementsList[index].storageMoney == ""
        ? ""
        : this.statementsList[index].storageMoney;
    },
    getTotalMoney(index) {
      this.statementsList[index].totalMoney =
        this.statementsList[index].managementMoney +
        this.statementsList[index].storageMoney;
      return this.statementsList[index].totalMoney == ""
        ? ""
        : this.statementsList[index].totalMoney;
    },
    selectCNO(data) {
      this.dataForm.cargoCod = data.cargoCod;
    },
    selectNo(item) {
      console.log(item)
      this.dataForm.listFid = item.fId;
      //获取单据名称
      let temp = "";
      temp = item.ioyardListBodyList[0].inYardWay;
      this.inyardTypeOptions.forEach((element) => {
        if (element.value == temp) {
          this.dataForm.inYardWay = element.label;
        }
      });
      this.dataForm.outYardWay = item.ioyardListBodyList[0].outYardWay;
      let temp2 = "";
      temp2 = item.ioyardListBodyList[0].outYardWay;
      this.outyardTypeOptions.forEach((element) => {
        if (element.value == temp2) {
          this.dataForm.outYardWay = element.label;
        }
      });
      this.statementsList = item.ioyardListBodyList;
      console.log("this.statementsList", this.statementsList)
    },
    getInfoShip() {
      getInyardType().then((data) => {
        this.inyardTypeOptions = data.data.data;
      });

      getOutyardType().then((data) => {
        this.outyardTypeOptions = data.data.data;
      });
    },
    getDay(index) {
      if (
        this.statementsList[index].inYardDat != null &&
        this.statementsList[index].outYardDat != null
      ) {
        let date1 = new Date(this.statementsList[index].inYardDat);
        let date2 = new Date(this.statementsList[index].outYardDat);
        //获取当前时间
        let m1 = moment(date1);
        //获取需要对比的时间
        let m2 = moment(date2);
        //计算相差多少天 day可以是second minute
        let day = m2.diff(m1, "day") + 1;
        if (day > 0) {
          this.statementsList[index].dayNum = day;
          return this.statementsList[index].dayNum;
        } else {
          return "请重新选择时间";
        }
      } else {
        return null;
      }
    },
    getTonDayNum(index) {
      if (
        this.statementsList[index].outSettleWgt != null &&
        this.statementsList[index].dayNum != null
      ) {
        this.statementsList[index].tonDayNum =
          this.statementsList[index].outSettleWgt *
          this.statementsList[index].dayNum;
        return this.statementsList[index].tonDayNum;
      } else {
        return null;
      }
    },
    addBill: function () {
      this.statementsList.push({
        feeNam: "",
        itemName: "",
        inSettleWgt: '',
        outSettleWgt: '',
        managementMoney: 0,
        storageMoney: 0,
      });
    },
    // deleteRow(index, data,statementsList) {
    //   if (statementsList[index] != "") {
    //     this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
    //       confirmButtonText: "确定",
    //       cancelButtonText: "取消",
    //       type: "warning",
    //     })
    //       .then(() => {
    //         delIoYardMessage(statementsList[index]).then((response) => {
    //           this.$message({
    //             type: "success",
    //             message: "删除成功!",
    //           });
    //           this.statementsList.splice(index, 1);
    //         });
    //       })
    //       .catch(() => {
    //         this.$message({
    //           type: "info",
    //           message: "已取消删除",
    //         });
    //       });
    //   } else {
    //     this.statementsList.splice(index, 1);
    //   }
    // },
    //导出
    uploadSuccess: function (e) {
      if (e.data == null) {
        this.$notify.success("文件上传失败");
      } else {
        this.fileOptions.push({ originalFilename: e.data.originalFilename });
        //this.$notify.success(e.msg)
        var bucketName = e.data.bucketName;
        var fileName = e.data.fileName;
        var url = e.data.url;
        var originalFilename = e.data.originalFilename;
        var fileSize = e.data.fileSize;
        this.fileList.push({
          bucketName: bucketName,
          fileName: fileName,
          url: url,
          original: originalFilename,
          fileSize: fileSize,
        });
        if (this.dataForm.fId != 0) {
          var fileOne = [
            {
              bucketName: bucketName,
              fileName: fileName,
              url: url,
              original: originalFilename,
              fileSize: fileSize,
              tableName: "ioyardsettementbill",
              tableFid: this.dataForm.fId,
            },
          ];
          fileSave(fileOne).then((response) => {
            if (response.data.data != null) {
              this.fileList[this.fileList.length - 1].id = response.data.data;
            }
          });
        }

        this.fileLoading = false;
      }
    },
    deleteFile: function (index) {
      var id = this.fileList[index].id;
      this.fileList.splice(index, 1);
      this.fileOptions.splice(index, 1);
      if (this.dataForm.fId != 0) {
        delFile(id).then((response) => { });
      }
    },
    getFileInfoUpdate: function () {
      var map = { tableName: 'ioyardsettementbill', tableFid: this.dataForm.fId };
      getFileInfo(map).then((response) => {
        this.fileList = response.data.data;
        for (const key in this.fileList) {
          this.fileOptions.push({
            originalFilename: this.fileList[key].original,
          });
        }
      });
    },
    onProgress(event, file, fileList) {
      this.fileLoading = true;
    },
    downloadFile: function (index) {
      var filename = this.fileList[index].fileName;
      var bucket = this.fileList[index].bucketName;
      handleDown(filename, bucket);
    },

    // 表单提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate((valid) => {
        // if (
        //   (this.dataForm.cargoNam == "" ||
        //     this.dataForm.cargoNam == undefined) &&
        //   (this.dataForm.cargoKindNam == "" ||
        //     this.dataForm.cargoKindNam == undefined)
        // ) {
        //   this.$message.error("请选择货名或货类");
        //   return;
        // }
        if (this.feeItemInfor.length != 0) {
          for (let index = 0; index < this.feeItemInfor.length; index++) {
            const element = this.feeItemInfor[index];
            if (element.feeNam == null || element.feeRat == null
              || element.feeUnit == null || element.feeNam == " " || element.feeRat == " "
              || element.feeUnit == " ") {
              this.$message.error('请填写有效费目信息')
              return;
            }
          }
        } else {
          this.$message.error("请至少选择的一条费目");
          return;
        }
        if (valid) {
          this.canSubmit = false;
          if (this.dataForm.fId) {
            let crk = 0;
            let bsc = 0;
            let outwgt = 0;
            let tonday = 0;
            this.statementsList.forEach((res) => {
              crk += Number(res.managementMoney);
              if (res.storageMoney) {
                bsc += Number(res.storageMoney);
              }
              outwgt += Number(res.outSettleWgt);
              tonday += Number(res.tonDayNum);
            });
            this.dataForm.managementMoney = crk;
            this.dataForm.storageMoney = bsc;
            this.dataForm.outWgt = outwgt;
            this.dataForm.tonDay = tonday;
            putObj(this.dataForm)
              .then((data) => {
                //文件修改
                fileUpdate(this.fileList)
                this.feeItemInfor.map(element => {
                  element.hFid = data.data.data.fId
                });
                putFeeMessage(this.feeItemInfor).then((res) => {
                  this.$notify.success("修改成功");
                  this.visible = false;
                  this.$emit("refreshDataList");
                });
                //子表修改
                // if(this.addCount != 0){
                //    this.feeItemInfor.map(element => {
                //   element.hFid =data.data.data.fId
                // });
                // putFeeMessage(this.feeItemInfor).then((res) => {
                //   this.$notify.success("修改成功");
                //   this.visible = false;
                //   this.$emit("refreshDataList");
                // });
                // }else{
                //   this.$notify.success("修改成功");
                //   this.visible = false;
                //   this.$emit("refreshDataList");
                // }
              })
              .catch(() => {
                this.canSubmit = true;
              });
          } else {
            let crk = 0;
            let bsc = 0;
            let outwgt = 0;
            let tonday = 0;
            this.statementsList.forEach((res) => {
              crk += Number(res.managementMoney);
              if (res.storageMoney) {
                bsc += Number(res.storageMoney);
              }
              outwgt += Number(res.outSettleWgt);
              tonday += Number(res.tonDayNum);
            });
            this.dataForm.managementMoney = crk;
            this.dataForm.outWgt = outwgt;
            this.dataForm.tonDay = tonday;
            this.dataForm.storageMoney = bsc;
            addObj(this.dataForm)
              .then((data) => {
                //文件添加
                for (var i in this.fileList) {
                  this.fileList[i].tableName = 'ioyardsettementbill'
                  this.fileList[i].tableFid = data.data.data.fId
                }
                fileSave(this.fileList)
                this.statementsList.forEach((element) => {
                  element.billCod = data.data.data.billCod;
                  element.fFid = data.data.data.fId;
                });
                this.feeItemInfor.map(element => {
                  element.hFid = data.data.data.fId
                });
                if (this.feeItemInfor == null || this.statementsList == null) {
                  this.$notify.info("添加失败");
                  this.visible = true;
                  return
                } else {
                  addFeeMessage(this.feeItemInfor).then((res) => {
                    this.$notify.success("添加费目成功");
                    addIoYardMessage(this.statementsList).then((res) => {
                      this.$notify.success("添加明细成功");
                      addrate(data.data.data.fId).then(res => {
                        this.visible = false;
                        this.$emit("refreshDataList");
                      })
                    });
                  });
                }
              })
              .catch(() => {
                this.canSubmit = true;
              });
          }
        }
      });
    },
  },
};
</script>
<style >
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.box-card {
  width: 250px;
}
</style>
