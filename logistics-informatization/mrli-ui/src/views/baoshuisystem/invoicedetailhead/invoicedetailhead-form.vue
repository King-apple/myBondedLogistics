<template>
  <el-dialog
    :title="title"
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
          <el-form-item label="结算编号" prop="billCod">
            <el-select
              v-model="dataForm.billCod"
              placeholder="请选择单据类型:"
              filterable
              :disabled="disabled"
              style="width: 70%"
            >
              <el-option
                v-for="item in billCodList"
                :key="item.fId"
                :label="item.billCod"
                :value="item.billCod"
                @click.native="getBillCod(item)"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="对应账户" prop="accountNam">
            <el-input
              v-model="dataForm.accountNam"
              style="width: 70%"
              placeholder="对应账户"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="发票类型" prop="invoiceType">
            <el-select
              v-model="dataForm.invoiceType"
              placeholder="发票类型:"
              filterable
              style="width: 70%"
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
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="客户名称" prop="secondNam">
            <el-input
              v-model="dataForm.secondNam"
              style="width: 70%"
              placeholder="客户名称"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="合计金额" prop="totalMoney">
            <el-input
              v-model="dataForm.totalMoney"
              style="width: 70%"
              placeholder="合计金额"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="日期" prop="operDat">
            <el-date-picker
              v-model="dataForm.operDat"
              type="date"
              placeholder="日期"
              value-format="yyyy-MM-dd"
              style="width: 70%"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="入境船名" prop="inShipName" style="width: 280px">
            <el-input
              v-model="dataForm.inShipName"
              placeholder="船名"
              style="width: 70%"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="出境船名" prop="outShipName" style="width: 70%">
            <el-input
              v-model="dataForm.outShipName"
              placeholder="船名"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="转账方式">
        <el-checkbox-group v-model="dataForm.payment">
          <el-checkbox label="转账"></el-checkbox>
          <el-checkbox label="现金"></el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-card class="box-card" v-if="statements.billKnd == '付款单'">
        <div slot="header" class="clearfix">
          <span>结算明细</span>
          <!-- <el-button
            style="float: right; padding: 3px 0"
            type="text"
            @click="addFee"
            >点击新增</el-button> -->
        </div>
        <div v-for="(settlementListItem, index) of settlementList" :key="index">
          <div>
            <div style="display: flex">
              <el-form-item :label="'费目' + (index + 1)">
                <el-select
                  v-model="settlementListItem.feeNam"
                  placeholder="费目"
                  filterable
                  @focus="getFeeNam"
                  style="width: 100px"
                  disabled
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
              <el-form-item label="单位">
                <el-select
                  v-model="settlementListItem.feeUnit"
                  placeholder="请选择"
                  filterable
                  disabled
                  style="width: 100px"
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
              <el-form-item label="数量">
                <el-input
                  v-model="settlementListItem.feeQuantity"
                  placeholder="数量"
                  disabled
                  style="width: 80px"
                ></el-input>
              </el-form-item>
              <el-form-item label="单价">
                <el-input
                  v-model="settlementListItem.feeRat"
                  placeholder="单价"
                  disabled
                  style="width: 80px"
                ></el-input>
              </el-form-item>
              <el-form-item label="金额">
                <el-input
                  v-model="settlementListItem.dueMoney"
                  placeholder="金额"
                  disabled
                  style="width: 80px"
                ></el-input>
              </el-form-item>
              <!-- <el-button
                type="danger"
                size=" mini"
                @click="deleteRow(index,settlementListItem, dataForm)"
                >删除</el-button> -->
            </div>
          </div>
        </div>
        <div
          style="display: flex; align-items: center; justify-content: center"
        >
          <span
            ><strong
              ><font color="red">数量合计:</font
              >{{ getTaxMoneyCount(settlementList) }}</strong
            ></span
          >
          <span style="padding-left: 50px"
            ><strong
              ><font color="red">费用合计:</font
              >{{ getTotalMoneyCount(settlementList) }}</strong
            ></span
          >
        </div>
      </el-card>
      <el-card class="box-card" v-else-if="statements.billKnd == '业务结算单'">
        <div slot="header" class="clearfix">
          <span>结算明细</span>
          <!-- <el-button
            style="float: right; padding: 3px 0"
            type="text"
            @click="addBus"
            >点击新增</el-button> -->
        </div>
        <div v-for="(businessListItem, index) of businessList" :key="index">
          <div style="display: flex">
            <el-form-item :label="'费目' + (index + 1)" style="width: 300px">
              <el-select
                v-model="businessListItem.feeNam"
                placeholder="费目"
                filterable
                disabled
              >
                <el-option
                  v-for="(item, feeIndex) in feeNameList"
                  :key="feeIndex"
                  :label="item.feeNam"
                  :value="item.feeNam"
                  @focus="getFeeNam"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="单位">
              <el-select
                v-model="businessListItem.feeUnit"
                placeholder="请选择"
                filterable
                disabled
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
            <el-form-item label="数量">
              <el-input
                v-model="businessListItem.feeQuantity"
                style="width: 100px"
                placeholder="数量"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item label="单价">
              <el-input
                v-model="businessListItem.feeRat"
                placeholder="单价"
                disabled
                style="width: 100px"
              ></el-input>
            </el-form-item>
            <el-form-item label="金额">
              <el-input
                v-model="businessListItem.totalMoney"
                style="width: 100px"
                placeholder="金额"
                disabled
              ></el-input>
            </el-form-item>
            <!-- <el-button
              type="danger"
              size=" mini"
              @click="deleteRowB(index,businessListItem, dataForm)"
              >删除</el-button> -->
          </div>
        </div>
        <div
          style="display: flex; align-items: center; justify-content: center"
        >
          <span
            ><strong
              ><font color="red">数量合计:</font
              >{{ getTaxMoneyCount(businessList) }}</strong
            ></span
          >
          <span style="padding-left: 50px"
            ><strong
              ><font color="red">费用合计:</font
              >{{ getMoneyCountB(businessList) }}</strong
            ></span
          >
        </div>
      </el-card>
      <el-card class="box-card" v-else>
        <div slot="header" class="clearfix">
          <span>结算明细</span>
        </div>
        <div v-if="dataForm.fId == ''">
          <div v-for="(inListItem, index) of inList" :key="index">
            <span>出入库明细{{ index + 1 }}</span>
            <div v-for="(initem, item) of inListItem.list" :key="item">
              <div style="display: flex">
                <el-form-item :label="'费目' + (item + 1)">
                  <el-input
                    v-model="initem.feeNam"
                    placeholder="费目"
                    disabled
                    style="width: 100px"
                  ></el-input>
                </el-form-item>
                <el-form-item label="费率单位">
                  <el-input
                    v-model="initem.feeUnit"
                    placeholder="费率单位"
                    disabled
                    style="width: 100px"
                  ></el-input>
                </el-form-item>
                <el-form-item label="费率值">
                  <el-input
                    v-model="initem.feeRat"
                    placeholder="费率值"
                    disabled
                    style="width: 70px"
                  ></el-input>
                </el-form-item>
                <el-form-item label="数量">
                  <el-input
                    v-model="initem.amount"
                    placeholder="数量"
                    disabled
                    style="width: 70px"
                  ></el-input>
                </el-form-item> 
               
                <el-form-item label="金额">
                  <el-input
                    v-model="initem.money"
                    placeholder="金额"
                    disabled
                    style="width: 100px"
                  ></el-input>
                </el-form-item>
              </div>
            </div>
          </div>
          <div
            style="display: flex; align-items: center; justify-content: center"
          >
            <span
              ><strong
                ><font color="red">数量合计:</font
                >{{ getInListTaxMoneyCount(inList) }}</strong
              ></span
            >
            <span style="padding-left: 50px"
              ><strong
                ><font color="red">费用合计:</font
                >{{ getInListMoneyCount(inList) }}</strong
              ></span
            >
          </div>
        </div>
        <div v-if="dataForm.fId">
          <div v-for="(lyListItem, index) of lyList" :key="index">
            <div style="display: flex">
              <el-form-item :label="'费目' + (index + 1)">
                <el-input
                  v-model="lyListItem.feeNam"
                  placeholder="费目"
                  disabled
                  style="width: 100px"
                ></el-input>
              </el-form-item>
              <el-form-item label="费率单位">
                <el-input
                  v-model="lyListItem.feeUnit"
                  placeholder="费率单位"
                  disabled
                  style="width: 100px"
                ></el-input>
              </el-form-item>
              <el-form-item label="费率值">
                <el-input
                  v-model="lyListItem.feeRat"
                  placeholder="费率值"
                  disabled
                  style="width: 100px"
                ></el-input>
              </el-form-item>
              <el-form-item label="数量">
                <el-input
                  v-model="lyListItem.amount"
                  placeholder="数量"
                  disabled
                  style="width: 100px"
                ></el-input>
              </el-form-item>
              <el-form-item label="金额">
                <el-input
                  v-model="lyListItem.money"
                  placeholder="金额"
                  disabled
                  style="width: 100px"
                ></el-input>
              </el-form-item>
            </div>
          </div>
          <div
            style="display: flex; align-items: center; justify-content: center"
          >
            <span
              ><strong
                ><font color="red">数量合计:</font
                >{{ getAmout(lyList) }}</strong
              ></span
            >
            <span style="padding-left: 50px"
              ><strong
                ><font color="red">费用合计:</font
                >{{ getAmoutMoney(lyList) }}</strong
              ></span
            >
          </div>
        </div>
      </el-card>
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
  getCostTpye,
  getInfo,
} from "@/api/baoshuisystem/invoicedetailhead";
import { getInvoicTpye } from "@/api/baoshuisystem/externalpayment";
import {
  addObj as addBody,
  getObj as getBody,
  delObj as delBody,
  putObj as putBody,
} from "@/api/baoshuisystem/invoicedetailbody";
import { getAll as feeunitAll } from "@/api/baoshuisystem/feeunits";
import { getContractObjectInfoAll } from "@/api/baoshuisystem/contractobject";
import {
  fetchList as feeFetch,
  saveList as contractSaveList,
  getRateList,
  removeBycFid,
} from "@/api/baoshuisystem/feenames";
import {
  getUserInfo,
} from "@/api/baoshuisystem/contract";
import { getObj as getObjDept } from "@/api/admin/dept";
import XEUtils from 'xe-utils';
export default {
  data() {
    return {
      title: "",
      disabled: false,
      statements: [],
      contractObjectOptions: "",
      //对外付款结算单
      settlementList: [],
      //对外付款结算单所对应的表
      settToList: [],
      //业务结算审批单
      businessList: [],
      feeNameList: [],
      //业务结算审批单所对应的表
      busList: [],
      lyList: [],
      inoutList: [],
      inList: [],
      costType: [],
      billCodList: [],
      visible: false,
      canSubmit: false,
      invoicTpyeDict: "",
      dataForm: {
        fId: "",
        approveFid: "",
        billFid: "",
        billCod: "",
        accountNam: "",
        invoiceType: "",
        secondCod: "",
        secondNam: "",
        totalMoney: "",
        shipNam: "",
        operDat: "",
        operNam: "",
        recordTim: "",
        updateNam: "",
        updateTim: "",
        delNam: "",
        delTim: "",
        payment: [],
        inShipName: "",
        outShipName: "",
      },
      dataRule: {
        approveFid: [
          { required: true, message: "审批单主键不能为空", trigger: "blur" },
        ],
        billFid: [
          { required: true, message: "结算单主键不能为空", trigger: "blur" },
        ],
        billCod: [
          { required: true, message: "结算单编号不能为空", trigger: "blur" },
        ],
        accountNam: [
          { required: true, message: "对应账户不能为空", trigger: "blur" },
        ],
        invoiceType: [
          { required: true, message: "发票类型不能为空", trigger: "blur" },
        ],
        secondCod: [
          { required: true, message: "客户代码不能为空", trigger: "blur" },
        ],
        secondNam: [
          { required: true, message: "客户名称不能为空", trigger: "blur" },
        ],
        totalMoney: [
          { required: true, message: "合计金额不能为空", trigger: "blur" },
        ],
        operDat: [{ required: true, message: "日期不能为空", trigger: "blur" }],
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
      },
    };
  },
  computed: {
    settlementListData() {
      if (this.settlementList) {
        return JSON.parse(JSON.stringify(this.settlementList));
      } else {
        return "";
      }
    },
    businessListData() {
      if (this.businessList) {
        return JSON.parse(JSON.stringify(this.businessList));
      } else {
        return "";
      }
    },

  },
  watch: {
    //对外付款
    settlementListData: {
      handler(newVal, oldVal) {
      }, deep: true, //深度监听 监听数组对象的属性增删改查
      immediate: true, //初始化就监听
    },
    // 结算单
    businessListData: {
      handler(newVal, oldVal) {

      }, deep: true, //深度监听 监听数组对象的属性增删改查
      immediate: true, //初始化就监听
    },

  },
  methods: {
    getAmoutMoney(data) {
      let count = 0
      data.forEach(item => {
        count += Number(item.money)
      })
      return count.toFixed(2)
    },
    getAmout(data) {
      let count = 0
      data.forEach(item => {
        count += Number(item.amount)
      })
      return count.toFixed(3)
    },
    getInListTaxMoneyCount(data) {
      let count = 0
      data.forEach(item => {
        item.list.forEach(res => {
          if(!XEUtils.isUndefined(res.amount) )
          count += Number(res.amount)
        })
      
      })
      return count.toFixed(3)
    },
    getInListMoneyCount(data) {
      let count = 0
      data.forEach(item => {
        item.list.forEach(res => {
          count += Number(res.money)
        })
      })
      return count.toFixed(2)
    },
    getMoneyCount(data) {
      let temp = 0;
      for (let index = 0; index < data.length; index++) {
        const element = data[index];
        temp += Number(element.money)

      }
      return temp == "" ? 0 : temp.toFixed(2)
    },
     getMoneyCountB(data) {
      let temp = 0;
      for (let index = 0; index < data.length; index++) {
        const element = data[index];
        temp += Number(element.totalMoney)

      }
      return temp == "" ? 0 : temp.toFixed(2)
    },
    getTaxMoneyCount(data) {
      let temp = 0;
      console.log("element",data)
      for (let index = 0; index < data.length; index++) {
        const element = data[index];
         temp += Number(element.feeQuantity)
     
      }
      return temp == "" ? 0 : temp.toFixed(2)
    },
    getTotalMoneyCount(data) {
      let temp = 0;
      for (let index = 0; index < data.length; index++) {
        const element = data[index];
        temp += Number(element.dueMoney)

      }
      return temp == "" ? 0 : temp.toFixed(2)
    },
    //对外付款审批新增
    addFee() {
      this.settlementList.push({
        hFid: "",
        feeName: "",
        feeUnit: "",
        feeQuantity: "",
        feeRat: "",
        advinceMoney: "",
      });
    },
    //业务结算新增
    addBus() {
      this.businessList.push({
        hFid: "",
        feeName: "",
        feeUnit: "",
        feeQuantity: "",
        feeRat: "",
        moeny: "",
      });
    },
    addInout() {
      this.inoutList.push({
        hFid: "",
        storageRat: "0",
      });
    },

    getDate() {
      this.dataForm.operDat = new Date()

    },
    getCost() {
      getCostTpye().then((res) => {
        this.costType = res.data.data;
      });
    },
    feeAll() {
      feeunitAll().then((response) => {
        this.feeUnitList = response.data.data;
      });
    },
    deleteRow(index, data, dataForm) {
      if (dataForm.fId != 0) {
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

              this.settlementList.splice(index, 1);
            });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除",
            });
          });
      } else {
        this.settlementList.splice(index, 1);
      }
    },
    deleteRowB(index, data, dataForm) {
      if (dataForm.fId != 0) {
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
              this.businessList.splice(index, 1);
            });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除",
            });
          });
      } else {
        this.businessList.splice(index, 1);
      }
    },
    init(id) {
      this.getInfoAll();
      this.getCost();
      this.feeAll();
      this.getDate();
      getUserInfo().then((res) => {
        this.dataForm.deptId = res.data.data.sysUser.deptSubId
      });
      if (id == undefined) {
        this.title = "新增";
        this.settlementList = [];
        this.businessList = [];
        this.inoutList = [];
        this.disabled = false;
        this.$set(this.dataForm, "payment", [])
        this.inList = []
      } else {
        this.title = "修改";
        this.disabled = true;
      }
      this.dataForm.fId = id || 0;
      this.visible = true;
      this.canSubmit = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
        if (this.dataForm.fId) {
          getObj(this.dataForm.fId).then((response) => {
            getInfo().then((res) => {
              this.billCodList = res.data.data.dalston;
            });
            this.dataForm = response.data.data;
            if (this.dataForm.payment) {
              this.dataForm.payment = this.dataForm.payment.split(",");
            } else{
              this.dataForm.payment=[]
            }
            this.billCodList.map((res) => {
              if (res.billCod == this.dataForm.billCod) {
                this.statements = res;
              }
            });
            getBody(this.dataForm.fId).then((res) => {
              if (this.statements.billKnd == "付款单") {
                //对外付款审批
                this.settToList = res.data.data;
                let item = [];
                this.settToList.map((list) => {
                  let newData = {};
                  newData.fId = list.fId;
                  newData.hFid = list.hFid;
                  newData.feeNam = list.projectNam;
                  newData.feeUnit = list.unit;
                  newData.feeRat = list.unitPrice;
                  newData.dueMoney = list.money;
                  newData.feeQuantity = list.amount;
                  item.push(newData);
                });
                this.settlementList = JSON.parse(JSON.stringify(item));
              } else if (this.statements.billKnd === "业务结算单") {
                //业务结算单
                this.busList = res.data.data;
                let item = [];
                this.busList.map((list) => {
                  let newData = {};
                  newData.fId = list.fId;
                  newData.hFid = list.hFid;
                  newData.feeNam = list.projectNam;
                  newData.feeUnit = list.unit;
                  newData.feeRat = list.unitPrice;
                  newData.totalMoney = list.money;
                  newData.feeQuantity = list.amount;
                  item.push(newData);
                });
                this.businessList = JSON.parse(JSON.stringify(item));
              } else {
                this.lyList = res.data.data;
                let item = [];
                this.lyList.map((list) => {
                  let newData = {};
                  newData.fId = list.fId;
                  newData.hFid = list.hFid;
                  newData.feeNam = list.projectNam;
                  newData.feeUnit = list.unit;
                  newData.feeRat = list.unitPrice;
                  newData.money = list.money;
                  this.$set(newData, "amount", list.amount)
                  item.push(newData);
                });
                this.lyList = JSON.parse(JSON.stringify(item));
              }
            });
          });
        }
      });
    },
    getInfoAll() {
      getContractObjectInfoAll().then((response) => {
        this.contractObjectOptions = response.data.data;
      });
      getInvoicTpye().then((response) => {
        this.invoicTpyeDict = response.data.data;
      });
      getInfo().then((res) => {
        this.billCodList = res.data.data.dalston;
      });
    },
    getFeeNam: function () {
      if (this.feeNameList.length == 0) {
        feeFetch().then((response) => {
          this.feeNameList = response.data.data.records;
        });

      }
    },
    getBillCod(item) {
      this.statements = item;
      if (item.billKnd == "付款单") {
        //TODO 业务单主键
        //结算单主键
        this.dataForm.billFid = item.fId;
        //结算单编号
        this.dataForm.billCod = item.billCod;
        //客户代码
        this.dataForm.secondCod = item.secondCod;
        //客户名称
        this.dataForm.secondNam = item.secondNam;
        //账户名
        this.dataForm.accountNam = item.firstNam;
        //船名
        this.dataForm.inShipName = item.inShipName;
        this.dataForm.outShipName = item.outShipName;
        this.settlementList = item.bbBodyList;
        this.dataForm.totalMoney = 0
        let arrlen = this.settlementList.length;
        let count = 0;
        for (let index = 0; index < arrlen; index++) {
          count += Number(this.settlementList[index].dueMoney);
        }
        this.dataForm.totalMoney = Number(count).toFixed(2)
      } else if (item.billKnd == "业务结算单") {
        //TODO 业务单主键
        //结算单主键
        this.dataForm.billFid = item.fId;
        //结算单编号
        this.dataForm.billCod = item.billCod;
        //客户代码
        this.dataForm.secondCod = item.secondCod;
        //客户名称
        this.dataForm.secondNam = item.secondNam;
        //账户名
        this.dataForm.accountNam = item.firstNam;
        //船名
         this.dataForm.inShipName = item.inShipName;
        this.dataForm.outShipName = item.outShipName;
        this.businessList = item.bbBodyList;
        this.dataForm.invoiceType = item.invoiceType
        this.dataForm.totalMoney = 0
        let arrlen = this.businessList.length;
        let count = 0;
        for (let index = 0; index < arrlen; index++) {
          count += Number(this.businessList[index].totalMoney);
        }
        this.dataForm.totalMoney = Number(count).toFixed(2)
      } else {
        //结算单主键
        this.dataForm.billFid = item.fId;
        //结算单编号
        this.dataForm.billCod = item.billCod;
        //客户代码
        this.dataForm.secondCod = item.secondCod;
        //客户名称
        this.dataForm.secondNam = item.secondNam;
        //船名
          this.dataForm.inShipName = item.inShipName;
        this.dataForm.outShipName = item.outShipName;
        //账户名
        this.dataForm.accountNam = item.firstNam;
        this.dataForm.invoiceType = item.invoiceType
        this.inList = item.ioYardSBBodyList
        this.dataForm.totalMoney = 0;
        let count = 0;
        this.inList.forEach((item, index) => {
          item.list.forEach(res => {
            
            count += Number(res.money);
            if (res.feeNam == "出入库管理费") {
              res.amount = item.outSettleWgt
            } else if(res.feeNam == "仓储费") {
              res.amount = item.tonDayNum
            }
          })
        })
        this.dataForm.totalMoney = Number(count).toFixed(2)
      }

    },

    // 表单提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate((valid) => {
        if (valid) {
          this.canSubmit = false;
          if (this.dataForm.fId) {
            putObj(this.dataForm)
              .then((data) => {
                let item = [];
                if (this.statements.billKnd == "付款单") {
                  //对外付款审批
                  this.settlementList.map((list) => {
                    let newData = {};
                    newData.fId = list.fId;
                    newData.hFid = data.data.data.fId;
                    newData.projectNam = list.feeNam;
                    newData.unit = list.feeUnit;
                    newData.unitPrice = list.feeRat;
                    newData.money = list.dueMoney;
                    this.$set(newData, "amount", list.feeQuantity)
                    // newData.amount = list.feeQuantity;
                    item.push(newData);
                  });
                  this.settToList = JSON.parse(JSON.stringify(item));

                  putBody(this.settToList).then((res) => {
                    this.visible = false;
                    this.$emit("refreshDataList");
                    this.$notify.success("修改成功");
                  });
                } else if (this.statements.billKnd == "业务结算单") {
                  //结算付款
                  this.businessList.map((list) => {
                    let newData = {};
                    newData.fId = list.fId;
                    newData.hFid = data.data.data.fId;
                    newData.projectNam = list.feeNam;
                    newData.unit = list.feeUnit;
                    newData.unitPrice = list.feeRat;
                    newData.money = list.money;
                    newData.amount = list.feeQuantity;
                    item.push(newData);
                  });
                  this.busList = JSON.parse(JSON.stringify(item));
                  if (this.busList.length != 0) {
                    putBody(this.busList).then((res) => {
                      this.visible = false;
                      this.$notify.success("修改成功");
                      this.$emit("refreshDataList");
                    });
                  }
                } else {
                  let arr = this.manageList.concat(this.storageList);
                  putBody(arr).then((res) => {
                    this.visible = false;
                    this.$notify.success("修改成功");
                    this.$emit("refreshDataList");
                  });
                }
              })
              .catch(() => {
                this.canSubmit = true;
              });
          } else {
            this.dataForm.uploadBillFlag = 0
            addObj(this.dataForm)
              .then((data) => {
                //对付付款审批单
                let item = [];
                this.settlementList.map((list) => {
                  let newData = {};
                  newData.hFid = data.data.data.fId;
                  newData.projectNam = list.feeNam;
                  newData.unit = list.feeUnit;
                  newData.unitPrice = list.feeRat;
                  newData.money = list.dueMoney;
                  newData.amount = list.feeQuantity;
                  item.push(newData);
                });
                this.settToList = JSON.parse(JSON.stringify(item));
                //业务结算审批单
                let busListItem = [];
                this.businessList.map((list) => {
                  let newData = {};
                  newData.hFid = data.data.data.fId;
                  newData.projectNam = list.feeNam;
                  newData.unit = list.feeUnit;
                  newData.unitPrice = list.feeRat;
                  newData.money = list.totalMoney;
                  newData.amount = list.feeQuantity;
                  busListItem.push(newData);
                });
                this.busList = JSON.parse(JSON.stringify(busListItem));
                let inOutitem = [];
                this.inList.map((list) => {
                  list.list.forEach(element => {
                    let newData = {};
                    newData.hFid = data.data.data.fId;
                    newData.projectNam = element.feeNam;
                    newData.unit = element.feeUnit;
                    newData.unitPrice = element.feeRat;
                    newData.money = element.money;
                    newData.amount = element.amount;
                    inOutitem.push(newData);
                  });
                });
                this.lyList = JSON.parse(JSON.stringify(inOutitem));

                if (this.settToList.length != 0 && this.settToList[0].projectNam != undefined) {
                  addBody(this.settToList).then((res) => {
                    this.$notify.success("添加成功");
                    this.visible = false;
                    this.$emit("refreshDataList");
                  });
                } else if (this.busList.length != 0 && this.busList[0].projectNam != undefined) {
                  addBody(this.busList).then((res) => {
                    this.$notify.success("添加成功");
                    this.visible = false;
                    this.$emit("refreshDataList");
                  });
                } else {
                  addBody(this.lyList).then((res) => {
                    this.$notify.success("添加成功");
                    this.visible = false;
                    this.$emit("refreshDataList");
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
<style scoped>
/* .el-button--mini, .el-button--mini.is-round {
    padding: 7px 15px;
    height: 34px;
} */
.el-button--danger {
  color: #fff;
  background-color: #f56c6c;
  border-color: #f56c6c;
  width: 81px;
  height: 31px;
}
</style>
