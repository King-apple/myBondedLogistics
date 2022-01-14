<template>
  <div class="mod-config">
    <basic-container>
      <el-form
        :inline="true"
        :model="dataForm"
        @keyup.enter.native="getDataList()"
      >
        <el-form-item label="客户" prop="secondNam">
          <el-input
            v-model="dataForm.secondNam"
            placeholder="请输入客户名称"
            style="width: 8vw"
          ></el-input>
        </el-form-item>
        <el-form-item label="货物名称" prop="cargoNam">
          <el-input
            v-model="dataForm.cargoNam"
            placeholder="请选择客户"
            style="width: 8vw"
          ></el-input>
        </el-form-item>
        <el-form-item label="货类名称" prop="cargoKindNam">
          <el-select
            clearable
            v-model="dataForm.cargoKindNam"
            placeholder="请选择货类名称:"
            filterable
            style="width: 8vw"
          >
            <el-option
              v-for="(item, index) in carGoKindOptions"
              :key="index"
              :label="item.cargoKindNam"
              :value="item.cargoKindNam"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="合同编号" prop="contNo">
          <el-select
            clearable
            v-model="dataForm.contNo"
            placeholder="请选择合同编号:"
            filterable
          >
            <el-option
              v-for="item in settledContractOptions"
              :key="item.fId"
              :label="item.contNo"
              :value="item.contNo"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="结算单编号" prop="billCod">
          <el-select
            clearable
            v-model="dataForm.billCod"
            placeholder="请选择结算单编号:"
            filterable
          >
            <el-option
              v-for="item in billCodOptions"
              :key="item.fId"
              :label="item.billCod"
              :value="item.billCod"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <div>
          <el-form-item label="日期范围" prop="params">
            <el-date-picker
              v-model="dataForm.params"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              align="right"
              value-format="yyyy-MM-dd"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button
              type="success"
              icon="el-icon-search"
              @click="getDataListQuery"
              >查询</el-button
            >
          </el-form-item>
          <el-form-item>
            <el-button
              type="warning"
              icon="el-icon-refresh"
              @click="resetForm()"
              >重置</el-button
            >
          </el-form-item>
        </div>
        <el-form-item>
          <el-button
            icon="el-icon-plus"
            type="primary"
            @click="addOrUpdateHandle()"
            >新增</el-button
          >
        </el-form-item>
      </el-form>

      <div class="avue-crud" style="width:100%;">
        <el-table :data="dataList" border v-loading="dataListLoading">
          <el-table-column type="expand" fixed="left">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand tableData">
                <div
                  v-for="(item, index) of props.row.ioYardSBBodyList"
                  :key="index"
                >
                  <el-form-item label="入库日期">
                    {{ props.row.ioYardSBBodyList[index].inYardDat }}
                  </el-form-item>
                  <el-form-item label="入库结算数量">
                    {{ props.row.ioYardSBBodyList[index].inSettleWgt }}
                  </el-form-item>
                  <el-form-item label="天数">
                    {{ props.row.ioYardSBBodyList[index].dayNum }}
                  </el-form-item>
                  <el-form-item label="出库日期">
                    {{ props.row.ioYardSBBodyList[index].outYardDat }}
                  </el-form-item>
                  <el-form-item label="出库结算数量">
                    {{ props.row.ioYardSBBodyList[index].outSettleWgt }}
                  </el-form-item>
                  <el-form-item label="吨天数">
                    {{ props.row.ioYardSBBodyList[index].tonDayNum }}
                  </el-form-item>
                  <el-form-item label="备注">
                    {{ props.row.ioYardSBBodyList[index].markTxt }}
                  </el-form-item>
                  <hr />
                </div>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column
            prop="billCod"
            header-align="center"
            align="center"
            label="结算单编号"
            width="180px"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="ioyardListNo"
            header-align="center"
            align="center"
            label="出入库明细编号"
            sortable

            width="185px"
          >
          </el-table-column>
           <el-table-column
            prop="contNo"
            header-align="center"
            align="center"
            label="合同编号"
            width="165px"
            sortable

          >
          </el-table-column>
          <el-table-column
            prop="secondNam"
            header-align="center"
            align="center"
            label="客户名称"
            sortable
            width="100px"
          >
          </el-table-column>
          <el-table-column
            prop="inYardWay"
            header-align="center"
            align="center"
            label="入境方式"
            width="100px"
            sortable
          >
          </el-table-column>

          <el-table-column
            prop="firstNam"
            header-align="center"
            align="center"
            sortable
            label="承办单位"
            width="220px"

          >
          </el-table-column>
          <el-table-column
            prop="dept"
            header-align="center"
            align="center"
            width="100px"
            label="承办部门"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="cargoNam"
            header-align="center"
            align="center"
            label="货物名称"
            sortable
            width="100px"
          >
          </el-table-column>

          <el-table-column
            prop="cargoKindNam"
            header-align="center"
            align="center"
            label="货类名称"
            sortable
            width="100px"
          >
          </el-table-column>
          <el-table-column
            prop="inShipName"
            sortable
            header-align="center"
            align="center"
             width="100px"
            label="入境船名"
          >
          </el-table-column>
          <el-table-column
            prop="outShipName"
            sortable
            header-align="center"
            align="center"
             width="100px"
            label="出境船名"
          >
          </el-table-column>
          <el-table-column
            prop="outYardWay"
            header-align="center"
            align="center"
            label="出境方式"
            sortable
            width="100px"
          >
          </el-table-column>

          <el-table-column
            prop="markTxt"
            header-align="center"
            align="center"
            label="备注"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="operDat"
            header-align="center"
            align="center"
            label="制表日期"
            width="110px"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="operNam"
            header-align="center"
            align="center"
            label="添加人"
            width="100px"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="recordTim"
            header-align="center"
            align="center"
            label="添加时间"
            width="160px"
            sortable

          >
          </el-table-column>
          <el-table-column
            prop="updateNam"
            header-align="center"
            align="center"
            label="修改人"
            width="100px"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="updateTim"
            header-align="center"
            align="center"
            label="修改时间"
            width="100px"

            sortable
          >
          </el-table-column>
          <el-table-column
            prop="invoiceType"
            header-align="center"
            align="center"
            label="发票类型"
            sortable
            width="100px"
          >
          </el-table-column>
          <el-table-column
            header-align="center"
            width="250px"
            fixed="right"
            align="center"
            label="操作"
          >
            <template slot-scope="scope">
              <el-button
                fixed="right"
                type="text"
                size="small"
                icon="el-icon-edit"
                @click="addOrUpdateHandle(scope.row.fId)"
                >修改</el-button
              >
              <el-button
                fixed="right"
                type="text"
                size="small"
                icon="el-icon-edit"
                @click="modifyDetail(scope.row)"
                >修改明细</el-button
              >
              <el-button
                type="text"
                size="small"
                icon="el-icon-delete"
                @click="deleteHandle(scope.row.fId)"
                >删除</el-button
              >
              <el-button
                type="text"
                size="small"
                icon="el-icon-delete"
                @click="expoprt(scope.row)"
                >点击导出
              </el-button>
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
        @refreshDataList="getDataList"
      ></table-form>
      <el-dialog
      title="修改明细"
      :visible.sync="modifyDetailVisible"
      width="85%"
      :close-on-click-modal="false"
      >
      <el-form
        :model="dataForm"
        ref="dataForm"
        label-width="80px"
      >
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>修改明细</span>
          <el-button style="float: right; padding: 3px 0" type="text" @click="addClick">点击新增</el-button>
        </div>
        <div v-for="(statementsItem, index) of statementsList" :key="index" >
          <div style="display: flex">
            <el-form-item label="入库日期" prop="inYardDat">
                <el-date-picker
                  v-model="statementsItem.inYardDat"
                  type="date"
                  placeholder="入库日期"
                  value-format="yyyy-MM-dd"
                  format="yyyy 年 MM 月 dd 日"
                  style="width: 175px"
                ></el-date-picker>
              </el-form-item>
              <el-form-item label="出库日期" prop="outYardDat">
                <el-date-picker
                  v-model="statementsItem.outYardDat"
                  type="date"
                  placeholder="出库日期"
                  value-format="yyyy-MM-dd"
                  format="yyyy 年 MM 月 dd 日"
                  style="width: 175px"
                ></el-date-picker>
              </el-form-item>
              <el-form-item label="入库数量" prop="inSettleWgt">
                <el-input
                  v-model="statementsItem.inSettleWgt"
                  placeholder="入库结算数量"
                  style="width: 70px"
                  @input="oninput($event, index)"
                ></el-input>
              </el-form-item>
             <el-form-item label="出库数量" prop="outSettleWgt">
                <el-input
                  v-model="statementsItem.outSettleWgt"
                  placeholder="出库数量"
                  style="width: 70px"
                  @input="oninput2($event, index)"
                ></el-input>
              </el-form-item>
               <el-form-item label="天数"  prop="dayNum">
                <el-input
                  v-model="statementsItem.dayNum"
                  placeholder="天数"
                  style="width: 60px"
                  disabled
                ></el-input>
              </el-form-item>
              <el-form-item label="吨天数" >
                 <el-input
                  v-model="statementsItem.tonDayNum"
                  placeholder="吨天数"
                  style="width: 70px"
                  disabled
                ></el-input>
              </el-form-item>
              <el-form-item  style="margin-left: -60px;">
              <el-button
              type="danger"
              @click="deleteRow(index,statementsList[index])"
              icon="el-icon-remove-outline"
              >删除</el-button>
               </el-form-item>

          </div>
          <div  v-for="(arrList, item) of statementsList[index].list" :key="item" >
              <el-row>
                <el-col :span="5">
                  <el-form-item label="费目" >
                  <el-select
                  v-model="arrList.feeNam"
                  placeholder="费目"
                  filterable
                  @focus="getFeeNam"
                  @loading="feeLoading"
                  @change="feeNamChange($event,index)"
                  style="width:125px"
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
                </el-col>
                <el-col :span="4">
                  <el-form-item label="费率单位" >
                    <el-select
                      v-model="arrList.feeUnit"
                      placeholder="请选择"
                      filterable
                      @focus="feeAll"
                      @loading="feeLoading"
                      style="width:120px"
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
                </el-col>
                <el-col :span="3.5">
                  <el-form-item label="费目值" >
                  <el-input
                    v-model="arrList.feeRat"
                    placeholder="费率值"
                    style="width: 100px"
                    @input="changeMoney($event, item,index)"
                  ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="4">
                  <el-form-item label="金额" >
                  <el-input
                    v-model="arrList.money"
                    placeholder="金额"
                    style="width: 100px"
                     @input="oninputTwoAccountsList($event, item,index)"
                  ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="4">
                  <el-form-item label="备注" >
                  <el-input
                    type="textarea"
                    :rows="1"
                    v-model="arrList.markTxt"
                    placeholder="备注"
                    style="width: 100px"
                  ></el-input>

                  </el-form-item>
                </el-col>
              </el-row>
          </div>
        </div>
         <div style="display: flex; justify-content: space-between; width: auto;">
              <span><strong><font color="red">天数合计:</font>{{getDay(statementsList)}}</strong></span>
              <span><strong><font color="red">吨天数合计:</font>{{getDonDayCount(statementsList)}}</strong></span>
              <span><strong><font color="red">入库数量合计:</font>{{getInCount(statementsList)}}</strong></span>
              <span><strong><font color="red">出库数量合计:</font>{{getOutCount(statementsList)}}</strong></span>
              <span><strong><font color="red">金额合计:</font>{{getMoneyCount(statementsList)}}</strong></span>
            </div>
      </el-card>
       </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="modifyDetailVisible = false">取 消</el-button>
          <el-button type="primary" @click="detailSure">确 定</el-button>
        </span>
      </el-dialog>
    </basic-container>
  </div>
</template>

<script>
import { onlyNumOnePoint } from "@/util/util";
import {
  fetchList,
  delObj,
  expertBusiness,
  getBillCodInfo
} from "@/api/baoshuisystem/ioyardsettlementbillshead";
import moment from "moment";
import TableForm from "./ioyardsettlementbillshead-form";
import { all as carGoKindAll } from "@/api/baoshuisystem/goodscategory";
import { mapGetters } from "vuex";
import {
  getSelectContract,
  getEffectiveInfo,
} from "@/api/baoshuisystem/contract";
import {
  getFeenamesAll as feeFetch,
  saveList as contractSaveList,
  getRateList,
  removeBycFid,
} from "@/api/baoshuisystem/feenames";
import { getAll as feeunitAll } from "@/api/baoshuisystem/feeunits";
import {
  getObj as getIoYardMessage,
  addObj as addIoYardMessage,
  delObj as delIoYardMessage,
  putObj as putIoYardMessage,
} from "@/api/baoshuisystem/ioyardsettlementbillsbody";
import{getObj as getrate}from "@/api/baoshuisystem/settlementbodyrate";
import{getMessage } from "@/api/baoshuisystem/settlementheadrate";
import XEUtils from 'xe-utils';
export default {
  data() {
    return {
      tempBillfId:"",
      tempBillCod:"",
      carGoKindOptions: [],
      feeLoading:false,
      feeNameList:[],
      feeUnitList:[],
      billCodOptions: [],
      dataForm: {
        key: "",
      },
      addLsit:[],
      statementsList:[],
      settledContractOptions: [],
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      addOrUpdateVisible: false,
      modifyDetailVisible:false,
    };
  },
  components: {
    TableForm,
  },
  created() {
    this.getDataList();
    this.getSelectContractList();
    this.getCarGoKind();
  },
  mounted() {

  },

  computed: {
    statementsDataNew() {
      return JSON.parse(JSON.stringify(this.statementsList));
    },
  },
  watch: {
      statementsDataNew: {
      handler(newVal, oldVal) {
        for (let index = 0; index < this.statementsList.length; index++) {
            const element = this.statementsList[index];
            if(element.list){
             element.list.forEach(res =>{
               if(res.feeNam == "仓储费"){
                  res.money = (res.feeRat * element.tonDayNum).toFixed(2)
               }
               if(res.feeRat == null||res.feeRat == "" ||res.feeRat == undefined){
                 res.feeRat =0
               }
              if(res.money == null||res.money == "" ||res.money == undefined){
                 res.money =0.00
               }
             })
            }
            //计算总和
            if (element.inYardDat != null &&element.outYardDat != null) {
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
                  if (element.outSettleWgt != null &&element.dayNum != null) {
                    element.tonDayNum = element.outSettleWgt * element.dayNum;
                  }
                }else{
                  element.dayNum = 0;
                }
              }
          }
        },
      deep: true, //深度监听 监听数组对象的属性增删改查
      immediate: true, //初始化就监听
      }
  },
  methods: {
    changeMoney(event, item,index){
       //限制输入2位小数
      this.statementsList[index].list[item].feeRat = this.onlyNumOnePoint(event);
      this.statementsList[index].list[item].feeRat =  this.statementsList[index].list[item].feeRat.replace(
        /^(\-)*(\d+)\.(\d\d).*$/,
        "$1$2.$3"
      );
      this.statementsList[index].list.forEach(res =>{
          if(this.statementsList[index].list[item].feeNam == "出入库管理费"){
            this.statementsList[index].list[item].money =
            (this.statementsList[index].outSettleWgt*this.statementsList[index].list[item].feeRat).toFixed(2)
          }
          if(this.statementsList[index].list[item].feeNam == "仓储费"){
            this.statementsList[index].list[item].money =
            (this.statementsList[index].tonDayNum*this.statementsList[index].list[item].feeRat).toFixed(2)
          }
        })
      },

    getDay(data){
      let temp = 0;
      for (let index = 0; index < data.length; index++) {
        const element = data[index];
        if(element.dayNum){
              temp += Number(element.dayNum)
        }
      }
      return temp == "" ? 0 : temp
    },
    getDonDayCount(data){
      let temp = 0;
      for (let index = 0; index < data.length; index++) {
        const element = data[index];
        if(element.tonDayNum){
              temp += Number(element.tonDayNum)
        }
      }
      return temp == "" ? 0 : temp.toFixed(2)
    },
    getInCount(data){
      let temp = 0;
      for (let index = 0; index < data.length; index++) {
        const element = data[index];
        if(element.inSettleWgt){
              temp += Number(element.inSettleWgt)
        }
      }
      return temp == "" ? 0 : temp.toFixed(3)
    },
    getOutCount(data){
      let temp = 0;
      for (let index = 0; index < data.length; index++) {
        const element = data[index];
        if(element.outSettleWgt){
              temp += Number(element.outSettleWgt)
        }
      }
      return temp == "" ? 0 : temp.toFixed(3)
    },
    getMoneyCount(data){
      let temp = 0;
      for (let index = 0; index < data.length; index++) {
        const element = data[index];
        if(element.list != null ){
           element.list.map(res =>{
            temp +=Number(res.money)
          })
        }
      }
     return temp == "" ? 0 : temp.toFixed(2)
    },
    deleteRow(index,data){
        if (data.fFid != "") {
        this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            delIoYardMessage(data).then((response) => {
              this.$message({
                type: "success",
                message: "删除成功!",
              });
              this.statementsList.splice(index, 1);
            });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除",
            });
          });
      } else {
        this.statementsList.splice(index, 1);
      }
    },
     onlyNumOnePoint,
    oninput(event, index) {
      this.statementsList[index].inSettleWgt = this.onlyNumOnePoint(event);

    },
    oninput2(event, index) {
       this.statementsList[index].outSettleWgt = this.onlyNumOnePoint(event);
       let temp = this.statementsList[index].outSettleWgt
        this.statementsList[index].list.forEach(res =>{
          if(res.feeNam == "出入库管理费"){
           res.money = Number(res.feeRat)*temp
          }

        })
    },
    oninputTwoAccountsList(event, item,index) {
      //限制输入2位小数
      this.statementsList[index].list[item].money = this.onlyNumOnePoint(event);
      this.statementsList[index].list[item].money =  this.statementsList[index].list[item].money.replace(
        /^(\-)*(\d+)\.(\d\d).*$/,
        "$1$2.$3"
      );
    },
    detailSure(){
      this.statementsList.forEach((element) => {
                  element.billCod = this.tempBillCod
                  element.fFid = this.tempBillfId
                });
      putIoYardMessage(this.statementsList).then(res =>{
        this.$notify.success("修改明细成功");
        this.modifyDetailVisible = false;
        this.getDataList()
      })
    },
    async  addClick(){
     await  getMessage(this.statementsList[0].fFid).then((item) =>{
          this.addLsit =  XEUtils.clone(item.data.data,true)
          this.addLsit.forEach((res,index) =>{
                this.$set(res,"money",0)
          })
      })
      this.statementsList.push({
        billCod:"",
        fFid:"",
        dayNum:"",
        inSettleWgt:"",
        inYardDat:"",
        outSettleWgt:"",
        outYardDat:"",
        tonDayNum:"",
        list:this.addLsit
      })
    },
    feeNamChange: function (data,index) {
      let tempCod = ""
      this.feeNameList.forEach(res =>{
        if(res.feeNam == data){
          tempCod = res.feeNameCod
        }
      })
      if(this.statementsList[index] && this.statementsList[index].list){
        this.statementsList[index].list.feeNameCod = tempCod
      }
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
    addBill: function () {
      this.statementsList.push({
        feeNam: "",
        itemName: "",
        inSettleWgt:'',
        outSettleWgt:'',
      });
    },
    getCarGoKind() {
      carGoKindAll().then((response) => {
        this.carGoKindOptions = response.data.data;
      });
    },
    async expoprt(data) {
       expertBusiness("出入库结算单.xlsx", data)
    },
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;
      fetchList(
        Object.assign(
          {
            current: this.pageIndex,
            size: this.pageSize,
          },
          this.dataForm
        )
      ).then((response) => {
        this.dataList = response.data.data.records;
        this.billCodOptions = this.dataList
        this.totalPage = response.data.data.total;
      });
      this.dataListLoading = false;
    },
    async getSelectContractList() {
      await getEffectiveInfo().then((res) => {
        this.settledContractOptions = res.data.data;
      });
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageIndex = 1;
      this.getDataList();
    },
    async getDataListQuery() {
      this.getDataList();
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.getDataList();
    },
    // 新增 / 修改
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true;
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id);
      });
    },
    modifyDetail(data){

      this.tempBillfId = data.fId
      this.tempBillCod = data.billCod
      this.modifyDetailVisible = true;
       getIoYardMessage( this.tempBillfId).then((res) => {
          this.statementsList = res.data.data;
          this.statementsList.forEach((res,index) => {
            getrate(res.fId).then(item =>{
               this.$set(this.statementsList[index],"list",item.data.data)
                 item.data.data.forEach(e =>{
                   if(e.feeNam == "出入库管理费"){
                      e.money = (res.outSettleWgt * e.feeRat).toFixed(2)
                   }
                   if(e.feeNam == "仓储费"){
                      e.money = (res.tonDayNum * e.feeRat).toFixed(2)
                   }
                 })
            })
          })
       });

    },
    resetForm() {
      //使用前，先绑定el-form的 ref
      this.dataForm = {};
      this.getDataList();
    },
    // 删除
    deleteHandle(id) {
      this.$confirm("是否确认删除?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delObj(id);
        })
        .then((data) => {
          this.$message.warning("删除成功");
          this.getDataList();
        });
    },
  },
};
</script>
<style scope>
.tableData {
  font-size: 0;
}

.tableData label {
  width: 100px;
  color: #99a9bf;
}

.tableData .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width:10%;
}
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.to_flex {
  display: flex;
  justify-content: flex-start;
}

</style>
