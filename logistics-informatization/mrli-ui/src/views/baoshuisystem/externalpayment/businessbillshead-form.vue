<template>
  <el-dialog
    :title="!dataForm.fId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible"
    width="60%"
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
          <el-form-item label="合同编号" prop="contCod" style="width: 300px">
            <el-select
              clearable
              v-model="dataForm.contCod"
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
          <el-form-item label="日期:" prop="billDate">
            <el-date-picker
              v-model="dataForm.billDate"
              type="date"
              placeholder="日期"
              value-format="yyyy-MM-dd"
              style="width: 200px"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="客户" prop="secondNam" style="width: 280px">
            <el-input
              v-model="dataForm.secondNam"
              placeholder="客户"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item
            label="计费数量"
            prop="feeQuantity"
            style="width: 280px"
          >
            <el-input
              v-model="dataForm.feeQuantity"
              @input="oninput"
              placeholder="计费数量"
            ></el-input>
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
        <el-col :span="8">
          <el-form-item label="承办部门" prop="dept" style="width: 280px">
            <el-input
              v-model="dataForm.dept"
              placeholder="承办部门"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <!-- <el-col :span="12">
          <el-form-item label="计费单位" prop="feeNam" style="width: 300px">
            <el-input
              v-model="dataForm.feeNam"
              placeholder="计费单位"
            ></el-input>
          </el-form-item>
        </el-col> -->
      <el-row>
        <!-- <el-col :span="8">
          <el-form-item label="公司名称" prop="companyNam" style="width: 280px">
            <el-input
              v-model="dataForm.companyNam"
              placeholder="公司名称"
              disabled
            ></el-input>
          </el-form-item>
        </el-col> -->
        <el-col :span="8">
          <el-form-item label="付款编号" prop="billCod" style="width: 280px">
            <el-input
              v-model="dataForm.billCod"
              placeholder="付款编号"
              v-if="flag == 1"
              disabled
            ></el-input>
            <el-input
              v-model="dataForm.billCod"
              placeholder="点击确定按钮生成"
              v-else
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="入境船名" prop="inShipName" style="width: 280px">
            <el-input
              v-model="dataForm.inShipName"
              placeholder="船名"
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

      <el-row>
        <!-- <el-col :span="8">
          <el-form-item
            label="承办单位代码"
            prop="firstNam "
            style="width: 300px"
          >
            <el-input
              v-model="dataForm.firstCod"
              placeholder="承办单位代码"
              disabled
            ></el-input>
          </el-form-item>
        </el-col> -->
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
      </el-row>
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>应预收款</span>
          <el-button
            style="float: right; padding: 3px 0"
            type="text"
            @click="addFee"
            >点击新增</el-button
          >
        </div>
        <div v-for="(accountsListItem, index) of accountsList" :key="index">
          <div style="display: flex">
            <el-form-item
              :label="'费目' + (index + 1)"
              style="width: 280px"
              prop="feeNam"
            >
              <el-select
                v-model="accountsListItem.feeNam"
                placeholder="请选择"
                filterable
                @focus="getFeeNam"
                @loading="feeLoading"
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
            <el-form-item label="费率单位" style="width: 280px" prop="feeUnit">
              <el-select
                v-model="accountsListItem.feeUnit"
                placeholder="请选择"
                filterable
                @focus="getFeeNam"
                @loading="feeLoading"
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
            <el-form-item label="费率值" style="width: 280px" prop="feeRat">
              <el-input
                v-model="accountsListItem.feeRat"
                placeholder="费率值"
              ></el-input>
            </el-form-item>
          </div>
          <div style="display: flex">
            <el-form-item
              label="预收款"
              style="width: 200px"
              prop="advinceMoney"
            >
              <el-input
                v-model="accountsListItem.advinceMoney"
                placeholder="预收款"
              ></el-input>
            </el-form-item>
            <el-form-item label="数量" style="width: 200px">
              <el-input
                v-model="accountsListItem.feeQuantity"
                placeholder="数量"
                @input="oninputAccountsFeeQuantity($event, index)"
              ></el-input>
            </el-form-item>
            <el-form-item label="应收款" style="width: 200px" prop="dueMoney">
              <el-input
                v-model="accountsListItem.dueMoney"
                placeholder="应收款"
              ></el-input>
            </el-form-item>
            <el-form-item label="备注">
              <el-input
                type="textarea"
                :rows="1"
                v-model="accountsListItem.markTxt"
                style="width: 100px"
                placeholder="请输入内容"
              ></el-input>
              <span style="margin-left: 20px"></span>
            </el-form-item>
            <el-button
              type="danger"
              size="mini"
              @click="deleteRow(index, accountsListItem)"
              icon="el-icon-remove-outline"
              >删除</el-button
            >
          </div>
        </div>
        <div style="display: flex; justify-content: space-between; width: auto">
          <el-form-item label="付款单位">
            <el-input
              v-model="dataForm.paymentUnit"
              placeholder="付款单位"
              style="width: 120px"
            ></el-input>
          </el-form-item>
          <span
            ><strong
              ><font color="red">预收款合计:</font
              >{{ getCount(accountsList) }}</strong
            ></span
          >
          <span
            ><strong
              ><font color="red">数量合计:</font
              >{{ getTaxMoneyCount(accountsList) }}</strong
            ></span
          >
          <span
            ><strong
              ><font color="red">应收款合计:</font
              >{{ getTotalMoneyCount(accountsList) }}</strong
            ></span
          >
        </div>
      </el-card>
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>应预付款</span>
          <el-button
            style="float: right; padding: 3px 0"
            type="text"
            @click="addAccount"
            >点击新增</el-button
          >
          <div v-for="(advanceListItem, index) of advanceList" :key="index">
            <div style="display: flex">
              <el-form-item
                :label="'费目' + (index + 1)"
                style="width: 280px"
                prop="feeNam"
              >
                <el-select
                  v-model="advanceListItem.feeNam"
                  placeholder="请选择"
                  filterable
                  @focus="getFeeNam"
                  @loading="feeLoading"
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
              <el-form-item
                label="费率单位"
                style="width: 280px"
                prop="feeUnit"
              >
                <el-select
                  v-model="advanceListItem.feeUnit"
                  placeholder="请选择"
                  filterable
                  @focus="feeAll"
                  @loading="feeLoading"
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
              <el-form-item label="费率值" style="width: 280px" prop="feeRat">
                <el-input
                  v-model="advanceListItem.feeRat"
                  placeholder="费率值"
                ></el-input>
              </el-form-item>
            </div>
            <div style="display: flex">
              <el-form-item
                label="预付款"
                style="width: 200px"
                prop="advinceMoney"
              >
                <el-input
                  v-model="advanceListItem.advinceMoney"
                  placeholder="预收款"
                ></el-input>
              </el-form-item>
              <el-form-item label="数量" style="width: 200px">
                <el-input
                  v-model="advanceListItem.feeQuantity"
                  placeholder="数量"
                ></el-input>
              </el-form-item>
              <el-form-item
                label="应预付款"
                style="width: 200px"
                prop="dueMoney"
              >
                <el-input
                  v-model="advanceListItem.dueMoney"
                  placeholder="应预付款"
                ></el-input>
              </el-form-item>
              <el-form-item label="备注" prop="markTxt">
                <el-input
                  type="textarea"
                  :rows="1"
                  v-model="advanceListItem.markTxt"
                  style="width: 100px"
                  placeholder="请输入内容"
                ></el-input>
                <span style="margin-left: 20px"></span>
              </el-form-item>
              <el-button
                type="danger"
                size="mini"
                @click="deleteAccount(index, advanceListItem)"
                icon="el-icon-remove-outline"
                >删除</el-button
              >
            </div>
          </div>
        </div>
        <div style="display: flex; justify-content: space-between; width: auto">
          <el-form-item label="收款单位">
            <el-input
              v-model="dataForm.ceceivingUnit"
              placeholder="收款单位"
              style="width: 120px"
            ></el-input>
          </el-form-item>
          <span
            ><strong
              ><font color="red">预付款合计:</font
              >{{ getCount(advanceList) }}</strong
            ></span
          >
          <span
            ><strong
              ><font color="red">数量合计:</font
              >{{ getTaxMoneyCount(advanceList) }}</strong
            ></span
          >
          <span
            ><strong
              ><font color="red">应付款合计:</font
              >{{ getTotalMoneyCount(advanceList) }}</strong
            ></span
          >
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
import { all as carGoKindAll } from '@/api/baoshuisystem/goodscategory'
import { getObj as getObjDept } from "@/api/admin/dept";
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
import {
  putList,
  getBody,
  delBody,
} from "@/api/baoshuisystem/businessbillsbody";
import { onlyNumOnePoint } from "@/util/util";
import store from "@/store";
import {
  getObj,
  addObj,
  putObj,
  getitemNameType,
  addList,
} from "@/api/baoshuisystem/externalpayment";
import { getAll as feeunitAll } from '@/api/baoshuisystem/feeunits'
import {
  fetchList as feeFetch,
  saveList as contractSaveList,
  getRateList,
  removeBycFid,
} from "@/api/baoshuisystem/feenames";
import { getObj as getCompany } from "@/api/baoshuisystem/parameterlist";
import XEUtils from 'xe-utils';
export default {
  data() {
    return {
      options: [],
      cargoNamOptions: [],
      headers: {
        Authorization: "Bearer " + store.getters.access_token,
      },
      itemNameTypeDict: [],
      advanceList: [],
      fileList: [],
      carGoKindOptions: [],
      accountsList: '',
      List: [],
      cargoNamOptions: [],
      fileOptions: [],
      visible: false,
      canSubmit: false,
      flag: 0,
      fileLoading: false,
      feeLoading: false,
      feeList: [],
      feeNameList: [],
      bbBodyList: "",
      disabled: false,
      dataForm: {
        fId: 0,
        firstNam: "",
        companyNam: "",
        billKnd: "付款单",
        billNam: "对外付款审批单",
        billCod: "",
        contCod: "",
        cargoKindNam: "",
        cargoKindCod: "",
        dept: "",
        billDate: "",
        secondCod: "",
        secondNam: "",
        shipNam: "",
        cargoCod: "",
        cargoNam: "",
        feeQuantity: "",
        feeNam: "",
        operator: "",
        operNam: "",
        recordTim: "",
        updateNam: "",
        updateTim: "",
        delNam: "",
        delTim: "",
        money: "",
        params: [],
        feeUnit: "",
        feeRat: "",
        markTxt: "",
        inShipName: "",
        outShipName: "",
        inputType: "",
        //付款单位
        paymentUnit: "",
        //收款单位
        ceceivingUnit: "",
      },
      settledContractOptions: [],
      dataRule: {

        // billKnd: [
        //   { required: true, message: "单据类型不能为空", trigger: "blur" },
        // ],
        billNam: [
          { required: true, message: "单据名称不能为空", trigger: "blur" },
        ],
        contCod: [
          { required: true, message: "合同编号不能为空", trigger: "blur" },
        ],
        dept: [
          { required: true, message: "承办部门不能为空", trigger: "blur" },
        ],
        billDate: [
          { required: true, message: "日期不能为空", trigger: "blur" },
        ],
        // secondCod: [
        //   { required: true, message: "客户代码不能为空", trigger: "blur" },
        // ],
        secondNam: [
          { required: true, message: "客户不能为空", trigger: "blur" },
        ],
        shipNam: [{ required: true, message: "船名不能为空", trigger: "blur" }],
        feeQuantity: [
          { required: true, message: "计费数量不能为空", trigger: "blur" },
        ],
        operator: [
          { required: true, message: "经办人不能为空", trigger: "blur" },
        ],
      },
    };
  },
  computed: {
    accountsDataNew() {
      return JSON.parse(JSON.stringify(this.accountsList))
    },
    advanceDataNew() {
      return JSON.parse(JSON.stringify(this.advanceList))
    }
  },
  watch: {
    "dataForm.cargoKindNam": {
      handler(newVal, oldVal) {
        if (newVal == "") {
          this.dataForm.cargoKindCod = ""
        }
      }
    },
    "dataForm.cargoNam": {
      handler(newVal, oldVal) {
        if (newVal == "") {
          this.dataForm.cargoCod = ""
        }
      }
    },
    advanceDataNew: {
      // 监听总数的变化,通过计算属性进行反序列化,否则 新旧值会相同
      handler(newVal, oldVal) {
        let obj = newVal;
        let obj2 = oldVal;
        let arrlen = this.advanceList.length
        for (let index = 0; index < arrlen; index++) {
          if (obj2 != null && obj2[index] != null && obj[index].feeQuantity != obj2[index].feeQuantity) {
            this.advanceList[index].dueMoney = (Number(newVal[index].feeRat) * Number(newVal[index].feeQuantity)).toFixed(2);
          }
        }
      }, deep: true,//深度监听 监听数组对象的属性增删改查
      immediate: true //初始化就监听
    },


    accountsDataNew: {
      // 监听总数的变化,通过计算属性进行反序列化,否则 新旧值会相同
      handler(newVal, oldVal) {
        let obj = newVal;
        let obj2 = oldVal;
        let arrlen = this.accountsList.length
        for (let index = 0; index < arrlen; index++) {
          if (obj2 != null && obj2[index] != null && obj[index].feeQuantity != obj2[index].feeQuantity) {
            this.accountsList[index].advinceMoney = (Number(newVal[index].feeRat) * Number(newVal[index].feeQuantity)).toFixed(2)
          }
        }
      }, deep: true,//深度监听 监听数组对象的属性增删改查
      immediate: true //初始化就监听
    },
    "dataForm.feeQuantity": {
      //feeQuantity改变 数组中的就改变
      handler(newVal, oldVal) {
        let obj = newVal;
        let obj2 = oldVal;
        let arrlen = this.accountsList.length
        let arrlen2 = this.advanceList.length
        for (let index = 0; index < arrlen; index++) {
          this.accountsList[index].feeQuantity = newVal
        }
        for (let index = 0; index < arrlen2; index++) {
          this.advanceList[index].feeQuantity = newVal
        }
      },
    }
  },
  methods: {
    getCount(data) {
      let temp = 0;
      for (let index = 0; index < data.length; index++) {
        const element = data[index];
        temp += Number(element.advinceMoney)
      }
      return XEUtils.isNaN(temp) ? 0 : temp.toFixed(2)
    },
    getTaxMoneyCount(data) {
      let temp = 0;
      for (let index = 0; index < data.length; index++) {
        const element = data[index];
        temp += Number(element.feeQuantity)

      }
      return XEUtils.isNaN(temp) ? 0 : temp.toFixed(2)
    },
    getTotalMoneyCount(data) {
      let temp = 0;
      for (let index = 0; index < data.length; index++) {
        const element = data[index];
        temp += Number(element.dueMoney)

      }
      return XEUtils.isNaN(temp) ? 0 : temp.toFixed(2)
    },

    init(id) {
      this.getCompanyName();
      this.getInfo();
      this.feeAll();
      //通过id判断新增还是修改
      if (id == undefined) {
        this.accountsList = [];
        this.advanceList = [];
        this.flag = 0;
        this.disabled = false;
        //新增自动获取日期
        this.getDate();
      } else {
        this.flag = 1;
        this.disabled = true;
      }
      this.dataForm.fId = id || 0;
      this.visible = true;
      this.canSubmit = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
        if (this.dataForm.fId) {
          this.getFileInfoUpdate();
          getObj(this.dataForm.fId).then((response) => {
            this.dataForm = response.data.data;
            getBody(this.dataForm.fId).then((response) => {
              //从map中取出两个list分别赋给
              this.advanceList = response.data.data.second;
              this.accountsList = response.data.data.first;
            });
          });
        }
      });
      this.getSelectContractList();
      this.feeUnitList = [];
      this.fileOptions = [];
      this.fileList = []

    },
    getDate() {
      this.dataForm.billDate = new Date()
    },
    getSelectContractList() {
      getEffectiveInfo().then((res) => {
        this.settledContractOptions = res.data.data;
      });
    },
    getFeeNam: function () {
      if (this.feeNameList.length == 0) {
        this.feeLoading = true;
        feeFetch().then((response) => {
          this.feeNameList = response.data.data.records;
          this.fee;
        });

      }
    },
    onlyNumOnePoint,
    oninputAccountsFeeQuantity(event, index) {
      this.accountsList[index].feeQuantity = this.onlyNumOnePoint(event);
    },
    selectOp(data) {
      this.dataForm.firstCod = data.organizerCode;
    },
    selectCNO(data) {
      this.dataForm.cargoCod = data.cargoCod;
    },
    feeAll() {
      feeunitAll().then((response) => {
        this.feeUnitList = response.data.data;
        this.feeLoading = false;
      });
    },
    selectCGK(item) {
      this.dataForm.cargoKindCod = item.cargoKindCod
    },
    onlyNumOnePoint,
    oninput(obj) {
      this.dataForm.feeQuantity = this.onlyNumOnePoint(obj);
    },
    getCompanyName() {
      getCompany(7).then((response) => {
        this.dataForm.companyNam = response.data.data.paramValue;
      });
    },
    addFee: function () {
      this.accountsList.push({
        feeNam: "",
        itemName: "",
        dueMoney: '',
        feeRat: '',
        feeQuantity: this.dataForm.feeQuantity
      });
    },
    addAccount() {
      this.advanceList.push({
        feeNam: "",
        itemName: "",
        dueMoney: '',
        feeRat: '',
        feeQuantity: this.dataForm.feeQuantity

      });
    },
    deleteRow(index, data) {
      if (data.fId != null) {
        this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            delBody(data).then((response) => {
              this.$message({
                type: "success",
                message: "删除成功!",
              });
              this.accountsList.splice(index, 1);
            });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除",
            });
          });
      } else {
        this.accountsList.splice(index, 1);
      }
    },
    deleteAccount(index, data) {
      if (data.fId != null) {
        this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            delBody(data).then((response) => {
              this.$message({
                type: "success",
                message: "删除成功!",
              });
              this.advanceList.splice(index, 1);
            });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除",
            });
          });
      } else {
        this.advanceList.splice(index, 1);
      }
    },
    selectFn(item) {
      this.dataForm.dept = item.dept;
      this.dataForm.secondNam = item.secondNam;
      this.dataForm.secondCod = item.secondCod;
      this.dataForm.cargoCod = item.cargoCod;
      this.dataForm.cargoNam = item.cargoNam;
      this.dataForm.firstNam = item.firstNam;
      this.dataForm.firstCod = item.firstCod;
      this.dataForm.cargoKindCod = item.cargoKindCod
      this.dataForm.cargoKindNam = item.cargoKindNam
      const newArr = JSON.parse(JSON.stringify(item.contractRateList));
      newArr.forEach(res => {
        res.dueMoney = 0
        res.feeQuantity = 0
      })
      this.advanceList = newArr;
      const newArrTwo = JSON.parse(JSON.stringify(item.contractRateList));
      newArrTwo.forEach(res => {
        res.dueMoney = 0
        res.feeQuantity = 0
      })
      this.accountsList = newArrTwo;
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate((valid) => {
        if ((this.dataForm.cargoNam == '' || this.dataForm.cargoNam == undefined) && (this.dataForm.cargoKindNam == '' || this.dataForm.cargoKindNam == undefined)) {
          this.$message.error('请选择货名或货类')
          return;
        }
        if (this.advanceList.length != 0) {
          for (let index = 0; index < this.advanceList.length; index++) {
            const element = this.advanceList[index];
            if (element.advinceMoney == undefined || element.dueMoney == undefined) {
              this.$message.error('请至少填写预收款和预付款')
              return;
            }
          }
        }
        if (this.accountsList.length != 0) {
          for (let index = 0; index < this.accountsList.length; index++) {
            const element = this.accountsList[index];
            if (element.feeUnit == undefined || element.feeRat == undefined
              || element.advinceMoney == undefined || element.feeQuantity == undefined
              || element.dueMoney == undefined) {
              this.$message.error('请填写所有项')
              return;
            }
          }
        }
        if (valid) {
          this.canSubmit = false;
          if (this.dataForm.fId) {
            putObj(this.dataForm)
              .then((data) => {
                //文件修改
                fileUpdate(this.fileList)
                this.advanceList.forEach((item, index) => {
                  item.itemNam = "应付款";
                });
                this.accountsList.forEach((item, index) => {
                  item.itemNam = "应收款";
                });
                this.bbBodyList = this.advanceList.concat(this.accountsList);
                this.bbBodyList.forEach((item, index) => {
                  if (item.hfid == null || item.hfid == "") {
                    item.hfid = Number(data.data.data.fId);
                  }
                  if (item.billCod == null || item.billCod == "") {
                    item.billCod = data.data.data.billCod;
                  }
                });
                putList(this.bbBodyList).then((response) => {
                  this.$notify.success("修改成功");
                  this.visible = false;
                  this.$emit("refreshDataList");
                })
              })
              .catch(() => {
                this.canSubmit = true;
              });
          } else {
            this.dataForm.inputType = 0
            addObj(this.dataForm)
              .then((data) => {
                //文件添加
                for (var i in this.fileList) {
                  this.fileList[i].tableName = 'externalpayment'
                  this.fileList[i].tableFid = data.data.data.fId
                }
                fileSave(this.fileList)
                this.advanceList.forEach((item, index) => {
                  item.itemNam = "应付款";
                });
                this.accountsList.forEach((item, index) => {
                  item.itemNam = "应收款";
                });
                this.bbBodyList = this.advanceList.concat(this.accountsList);
                this.bbBodyList.forEach((item, index) => {
                  item.hfid = Number(data.data.data.fId);
                  item.billCod = data.data.data.billCod;
                });
                this.visible = false;
                addList(this.bbBodyList).then((response) => {
                  this.$notify.success("添加成功");
                  this.$emit("refreshDataList");
                });
              })
              .catch(() => {
                this.canSubmit = true;
              });
          }
        }
      });
    },
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
              tableName: "externalpayment",
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
    getInfo() {
      getUserInfo().then((res) => {
        this.dataForm.deptId = res.data.data.sysUser.deptSubId
        getObjDept(res.data.data.sysUser.deptSubId).then((res) => {
          this.dataForm.dept = res.data.data.name;
        });
      });
      carGoKindAll().then(response => {
        this.carGoKindOptions = response.data.data
      }),
        getitemNameType().then((response) => {
          this.itemNameTypeDict = response.data.data;
        });
      getInsertValue().then((response) => {
        this.options = response.data.data.organizerInfos;
        this.cargoNamOptions = response.data.data.contractObjects
      });
    },
    getFileInfoUpdate: function () {
      var map = { tableName: 'externalpayment', tableFid: this.dataForm.fId };
      getFileInfo(map).then((response) => {
        this.fileList = response.data.data;
        for (const key in this.fileList) {
          this.fileOptions.push({
            "originalFilename": this.fileList[key].original,
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
  },
};
</script>
<style scope>
.el-button--mini,
.el-button--mini.is-round {
  padding: 7px 15px;
  height: 34px;
}
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
