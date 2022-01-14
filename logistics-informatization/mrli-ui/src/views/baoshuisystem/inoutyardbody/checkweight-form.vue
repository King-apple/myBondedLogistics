<template>
  <el-dialog
    :title="title"
    :close-on-click-modal="false"
    :visible.sync="visible"
    width="80%"
  >
    <el-form
      :model="dataForm"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="80px"
      v-loading="loading"
      :rules="dataRule"
    >
      <div style="height: 50px">
        <el-row>
          <el-col :span="8">
            <div style="text-align: right">
              请将下列货物予以衡重。衡重业务费用将以
            </div></el-col
          >
          <el-col :span="8">
            <el-form-item
              prop="feeWayNam"
              style="width: 50%; text-align: left; float: left"
            >
              <el-select
                clearable
                v-model="dataForm.feeWayNam"
                style="width: 100%"
                placeholder="结算方式:"
                filterable
                @change="feeWayNamChange($event, index)"
              >
                <el-option
                  v-for="(item, index) in cFeeWayOptions"
                  :key="index"
                  :label="item.feeWay"
                  :value="item.feeWay"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <span style="float: left">向贵公司支付</span>
          </el-col>
          <el-col :span="8"> </el-col>
        </el-row>
      </div>
      <el-row class="table-row">
        <el-col :span="12">
          <el-form-item label="作业类型" prop="shipNam" label-width="140px">
            <el-select
              clearable
              v-model="dataForm.shipNam"
              style="width: 100%"
              placeholder="作业类型:"
              filterable
              @change="shipNamChange($event, index)"
            >
              <el-option
                v-for="(item, index) in checkWeightWorkTypeOptions"
                :key="index"
                :label="item.meaning"
                :value="item.meaning"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="作业类型号" prop="shipNo" label-width="140px">
            <el-input v-model="dataForm.shipNo" placeholder="船号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row class="table-row">
        <el-col :span="12">
          <el-form-item label="提单号" prop="billNo" label-width="140px">
            <el-input v-model="dataForm.billNo" placeholder="提单号"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="日期" prop="recordDte" label-width="140px">
            <el-date-picker
              v-model="dataForm.recordDte"
              type="date"
              placeholder="日期"
              value-format="yyyy-MM-dd"
              style="width: 100%"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row class="table-row">
        <el-col :span="12">
          <el-form-item label="车次号" prop="trainNo" label-width="140px">
            <el-input
              v-model="dataForm.trainNo"
              placeholder="车次号"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="车皮号" prop="trainCod" label-width="140px">
            <el-input
              v-model="dataForm.trainCod"
              placeholder="车皮号"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row class="table-row">
        <el-col :span="8">
          <el-form-item label="货名" prop="cargoNam" label-width="140px">
            <!-- <el-select
              clearable
              v-model="dataForm.cargoNam"
              style="width: 100%"
              placeholder="作业类型:"
              filterable
              @change="cargoNamChange($event, index)"
              @click="cargoClick()"
            >
              <el-option
                v-for="(item, index) in cargoNamOptions"
                :key="index"
                :label="item.cargoNam"
                :value="item.cargoNam"
              >
              </el-option>
            </el-select> -->
            <el-input
              @click.native="cargoClick"
              v-model="dataForm.cargoNam"
              placeholder="货名"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="货号" prop="cargoNo" label-width="140px">
            <el-input v-model="dataForm.cargoNo" placeholder="货号"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="件数" prop="piecesNum" label-width="140px">
            <el-input
              v-model="dataForm.piecesNum"
              placeholder="件数"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row class="table-row">
        <el-col :span="8">
          <el-form-item label="重量" prop="weightWgt" label-width="140px">
            <el-input
              v-model="dataForm.weightWgt"
              placeholder="重量"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8" v-if="dataForm.checkNo">
          <el-form-item label="衡重单号" prop="checkNo" label-width="140px">
            <el-input
              v-model="dataForm.checkNo"
              placeholder="衡重单号"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="重量单位" prop="wgtUnit" label-width="140px">
            <el-input
              v-model="dataForm.wgtUnit"
              placeholder="重量单位"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row class="table-row">
        <el-col :span="8">
          <el-form-item label="堆存地点" prop="workPlace" label-width="140px">
            <el-select
              clearable
              v-model="dataForm.workPlace"
              style="width: 100%"
              placeholder="堆存地点:"
              filterable
              @change="workPlaceChange($event, index)"
            >
              <el-option
                v-for="(item, index) in checkweightStorageLocOptions"
                :key="index"
                :label="item.label"
                :value="item.label"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="装卸别" prop="inoutId" label-width="140px">
            <el-select
              clearable
              v-model="dataForm.inoutId"
              style="width: 100%"
              placeholder="装卸别:"
              filterable
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
        <el-col :span="8">
          <el-form-item label="运往地点" prop="toPlaceNam" label-width="140px">
            <el-input
              v-model="dataForm.toPlaceNam"
              placeholder="运往地点"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row class="table-row">
        <el-col :span="8">
          <el-form-item label="管货库区" prop="subunitNam" label-width="140px">
            <el-select
              clearable
              v-model="dataForm.subunitNam"
              style="width: 100%"
              placeholder="管货库区:"
              filterable
              @change="cSubunitChange($event, index)"
            >
              <el-option
                v-for="(item, index) in cSubunitOptions"
                :key="index"
                :label="item.subunitNam"
                :value="item.subunitNam"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="库区代码" prop="subunitCod" label-width="140px">
            <el-input
              v-model="dataForm.subunitCod"
              placeholder="库区代码"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item prop="checkId" label-width="140px">
            <el-checkbox v-model="dataForm.checkId">商检标志</el-checkbox>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row class="table-row">
        <el-col :span="24">
          <el-form-item
            label="申请单位"
            prop="requireUnitNam"
            label-width="140px"
          >
            <el-input
              v-model="dataForm.requireUnitNam"
              placeholder="申请单位"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row class="table-row">
        <el-col :span="24">
          <el-form-item label="代结单位" prop="agentNam" label-width="140px">
            <el-input
              v-model="dataForm.agentNam"
              placeholder="代结单位"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row class="table-row">
        <el-col :span="24">
          <el-form-item label="备注" prop="remarkTxt" label-width="140px">
            <el-input
              v-model="dataForm.remarkTxt"
              placeholder="备注"
              type="textarea"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="danger" v-if="delFlg" @click="deleteHandle()"
        >删除</el-button
      >
      <el-button type="primary" @click="dataFormSubmit()" v-if="canSubmit"
        >确定</el-button
      >
    </span>
    <el-dialog
      title="货物选择"
      :close-on-click-modal="false"
      :visible="cargoVisible"
      width="80%"
      append-to-body
      @close="cargoVisible = false"
    >
      <el-form :inline="true" :model="cargoForm">
        <el-form-item label="货名" prop="cargoNam">
          <el-input v-model="cargoForm.cargoNam" placeholder="货名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            icon="el-icon-search"
            type="primary"
            @click="getCargoForm()"
            >搜索</el-button
          >
        </el-form-item>
      </el-form>
      <el-table
        highlight-current-row
        :data="cargoDataList"
        @current-change="cargoHandleCurrentChange"
        border
        v-loading="cargoListLoading"
      >
        <el-table-column
          prop="cargoNamCod"
          header-align="center"
          align="center"
          label="货名代码"
          sortable
        >
        </el-table-column>
        <el-table-column
          prop="cargoNam"
          header-align="center"
          align="center"
          label="货名"
          sortable
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
          label="货类号"
          sortable
        >
        </el-table-column>
        <el-table-column
          prop="shipperCod"
          header-align="center"
          align="center"
          label="申请单位代码"
          sortable
        >
        </el-table-column>
        <el-table-column
          prop="shipperDoc"
          header-align="center"
          align="center"
          label="申请单位名称"
          sortable
        >
        </el-table-column>
      </el-table>
      <div style="float: right; height: 100px; margin-top: 10px">
        <el-button @click="cargoVisible = false">取消</el-button>

        <el-button type="primary" @click="cargoSaveClick">确定</el-button>
      </div>

      <div class="avue-crud__pagination" style="margin-top: 50px">
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
    </el-dialog>
  </el-dialog>
</template>

<script>
import { getObj, addObj, putObj, checkweight, getCheckNo, delObjForCheckNo } from '@/api/baoshuisystem/checkweight'
import { getObj as getObjBody, addObj as addObjBody, putObj as putObjBody } from '@/api/baoshuisystem/inoutyardbody'
import { getObj as addObjHead, putObj as putObjHead } from '@/api/baoshuisystem/inoutyardhead'
import { getCargoPage } from '@/api/baoshuisystem/getdictdata'

import {
  getSettlementMethodDic
  , getCheckWeightWorkTypeDic
  , getInoutIdDic
  , getTradeIdDic
  , getCFeeWayAllDic
  , getCDatacodesAllDic
  , getCSubunitAllDic
  , getCheckweightLoadDic
  , getCheckweightStorageLocDic
} from '@/api/baoshuisystem/getdictdata'
import { getContractObjectInfoAll as contractobjectAll } from '@/api/baoshuisystem/contractobject'


export default {
  data() {
    return {
      visible: false,
      canSubmit: false,
      dataForm: {
        recordDte: null,
        checkNo: '',
        trustNo: 1,
        cargoNo: '',
        cargoNam: '',
        cargoKindCod: '',
        feeWayId: '',
        feeWayNam: '',
        shipNo: '',
        shipNam: '',
        berthCod: '',
        inoutId: '',
        tradeId: '',
        billNo: '',
        piecesNum: '',
        weightWgt: '',
        wgtUnit: '',
        addMinRat: '',
        toPlaceNam: '',
        checkId: '',
        requireUnitNam: '',
        agentCod: '',
        agentNam: '',
        workPlace: '',
        workPlaceCod: '',
        unitCod: '',
        unitNam: '',
        subunitCod: '',
        subunitNam: '',
        ifendId: '',
        remarkTxt: '',
        operNam: '',
        recordTim: '',
        updateNam: '',
        updateTim: '',
        countWayCod: '',
        countWay: '',
        trainNo: '',
        trainCod: '',
        inoutYardNo: '',
        carId: '',
        gCarId: ''
      },
      title: '',
      settlementMethodOptions: [],
      checkWeightWorkTypeOptions: [],
      inoutIdOptions: [],
      tradeIdDicoptions: [],
      cargoNamOptions: [],
      cSubunitOptions: [],
      checkweightStorageLocOptions: [],
      cFeeWayOptions: [],
      cargoForm: {
        cargoNamCod: '',
        cargoNam: '',
        cargoNo: '',
        cargoKindCod: '',
        shipperCod: '',
        shipperDoc: ''
      },
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      cargoVisible: false,
      index: 0,
      loading: false,
      cargoListLoading: false,
      cargoDataList: [],
      cargoCurrentRow: null,
      settledCheckIdOptions: [{
        label: "通过",
        value: 0
      }, {
        label: "未通过",
        value: 1
      }],
      settledworkPlaceOptions: [{
        label: "港内",
        value: "1"
      }, {
        label: "港外",
        value: "0"
      }],
      delFlg: 0,
      dataRule: {
        checkNo: [
          { required: true, message: '衡重单号不能为空', trigger: 'blur' }
        ],
        cargoNo: [
          { required: true, message: '货号不能为空', trigger: 'blur' }
        ],
        cargoNam: [
          { required: true, message: '货名不能为空', trigger: 'blur' }
        ],
        cargoKindCod: [
          { required: true, message: '货类代码无不能为空', trigger: 'blur' }
        ],

        feeWayNam: [
          { required: true, message: '结算方式不能为空', trigger: 'blur' }
        ],
        shipNo: [
          { required: true, message: '船号不能为空', trigger: 'blur' }
        ],
        shipNam: [
          { required: true, message: '船名不能为空', trigger: 'blur' }
        ],
        billNo: [
          { required: true, message: '提单号   +无不能为空', trigger: 'blur' }
        ],
        weightWgt: [
          { required: true, message: '重量    +不能为空', trigger: 'blur' }
        ],
        wgtUnit: [
          { required: true, message: '重量单位(吨)   +不能为空', trigger: 'blur' }
        ],
        requireUnitNam: [
          { required: true, message: '申请单位     +表中的consign_nam不能为空', trigger: 'blur' }
        ],
        agentNam: [
          { required: true, message: '代理公司        +无不能为空', trigger: 'blur' }
        ],
        inoutId: [
          { required: true, message: '代理公司        +无不能为空', trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
    init(id, hFid, flg) {
      this.dataForm = {
        recordDte: null,
        checkNo: '',
        trustNo: 1,
        cargoNo: '',
        cargoNam: '',
        cargoKindCod: '',
        feeWayId: '',
        feeWayNam: '',
        shipNo: '',
        shipNam: '',
        berthCod: '',
        inoutId: '',
        tradeId: '',
        billNo: '',
        piecesNum: '',
        weightWgt: '',
        wgtUnit: '',
        addMinRat: '',
        toPlaceNam: '',
        checkId: '',
        requireUnitNam: '',
        agentCod: '',
        agentNam: '',
        workPlace: '',
        workPlaceCod: '',
        unitCod: '',
        unitNam: '',
        subunitCod: '',
        subunitNam: '',
        ifendId: '',
        remarkTxt: '',
        operNam: '',
        recordTim: '',
        updateNam: '',
        updateTim: '',
        countWayCod: '',
        countWay: '',
        trainNo: '',
        trainCod: '',
        inoutYardNo: '',
        carId: '',
        gCarId: ''
      }
      this.dataForm.id = id || 0;
      this.visible = true;
      this.canSubmit = true;
      this.dataForm.shipNo = ''
      this.dataForm.shipNam = ''
      this.dataForm.bodyId = id

      this.title = "衡重申请单"
      getSettlementMethodDic().then(response => {
        this.settlementMethodOptions = response.data.data
      })
      getCDatacodesAllDic().then(response => {
        this.checkWeightWorkTypeOptions = response.data.data
      })
      getCheckweightLoadDic().then(response => {
        this.inoutIdOptions = response.data.data
      })
      getTradeIdDic().then(response => {
        this.tradeIdDicoptions = response.data.data
      })
      contractobjectAll().then(response => {
        this.cargoNamOptions = response.data.data
      })
      getCSubunitAllDic().then(response => {
        this.cSubunitOptions = response.data.data
      })
      getCheckweightStorageLocDic().then(response => {
        this.checkweightStorageLocOptions = response.data.data
      })
      getCFeeWayAllDic().then(response => {
        this.cFeeWayOptions = response.data.data
      })
      this.getCargoForm()
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()

        if (this.dataForm.id) {
          this.loading = true
          addObjHead(hFid).then((headres) => {
            this.dataForm.requireUnitNam = headres.data.data.consignNam
            this.dataForm.agentCod = '14'
            this.dataForm.agentNam = '营口港务集团物流公司'
            this.dataForm.unitCod = '14'
            this.dataForm.unitNam = '营口港务集团物流公司'
            this.dataForm.ifendId = 0
            this.dataForm.wgtUnit = headres.data.data.netWetUnit
            this.dataForm.inoutYardNo = headres.data.data.inoutYardNo

            getObjBody(this.dataForm.id).then((res) => {
              // this.dataForm.cargoNo = res.data.data.cargoNo
              // this.dataForm.cargoNam = res.data.data.cargoNam
              this.dataForm.piecesNum = res.data.data.pieceNum
              this.dataForm.weightWgt = res.data.data.netWet == null || res.data.data.netWet == '' ? null : res.data.data.netWet / 1000
              if (res.data.data.checkNo) { //如果是空的需新建
                this.dataForm.checkNo = res.data.data.checkNo

                getCheckNo(res.data.data.checkNo).then(response => {
                  this.delFlg = 1
                  this.dataForm = response.data.data
                  if (this.dataForm == null) {
                    this.dataForm = {
                      recordDte: null,
                      checkNo: '',
                      trustNo: 1,
                      cargoNo: '',
                      cargoNam: '',
                      cargoKindCod: '',
                      feeWayId: '',
                      feeWayNam: '',
                      shipNo: '',
                      shipNam: '',
                      berthCod: '',
                      inoutId: '',
                      tradeId: '',
                      billNo: '',
                      piecesNum: '',
                      weightWgt: '',
                      wgtUnit: '',
                      addMinRat: '',
                      toPlaceNam: '',
                      checkId: '',
                      requireUnitNam: '',
                      agentCod: '',
                      agentNam: '',
                      workPlace: '',
                      workPlaceCod: '',
                      unitCod: '',
                      unitNam: '',
                      subunitCod: '',
                      subunitNam: '',
                      ifendId: '',
                      remarkTxt: '',
                      operNam: '',
                      recordTim: '',
                      updateNam: '',
                      updateTim: '',
                      countWayCod: '',
                      countWay: '',
                      trainNo: '',
                      trainCod: '',
                      inoutYardNo: '',
                      carId: '',
                      gCarId: ''
                    }
                  }
                  if (this.dataForm != null && this.dataForm.tradeId != undefined) {
                    this.dataForm.tradeId = this.dataForm.tradeId.toString()
                  }
                  if (this.dataForm != null && this.dataForm.inoutId != undefined) {
                    this.dataForm.inoutId = this.dataForm.inoutId.toString()
                  }
                  this.dataForm.bodyId = id
                  if (this.dataForm.checkId == 1) {
                    this.dataForm.checkId = true
                  } else {
                    this.dataForm.checkId = false
                  }
                  this.loading = false
                })

              } else {
                this.dataForm.wgtUnit = '吨'
                this.dataForm.checkId = true
                checkweight("14").then((res) => {
                  this.dataForm.checkNo = res.data.data
                })
                this.loading = false
              }

              //							getObj(this.dataForm.recordDte).then(response => {
              //							this.dataForm = response.data.data
              //						})
            })

          })

        }


      })

    },
    // 表单提交
    dataFormSubmit() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          if (this.dataForm.checkId == true) {
            this.dataForm.checkId = 1
          } else {
            this.dataForm.checkId = 0
          }
          this.canSubmit = false;
          this.dataForm.tradeId = '1'
          console.log("--", this.dataForm)
          if (this.dataForm.recordTim) {
            putObj(this.dataForm).then(data => {
              this.$notify.success('修改成功')
              this.visible = false
              this.$emit('refreshDataList')
            }).catch(() => {
              this.canSubmit = true;
            });
          } else {
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
    },
    selectFn(item) {
      this.dataForm.workPlaceCod = item.value
    },
    // 删除
    deleteHandle() {
      let that = this;
      that.$confirm('是否确认删除衡重单号为' + that.dataForm.checkNo, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function () {
        if (that.dataForm.checkId == true) {
          that.dataForm.checkId = 1
        } else {
          that.dataForm.checkId = 0
        }
        return delObjForCheckNo(that.dataForm)
      }).then(data => {
        that.$message.success('删除成功')

        that.visible = false
      })
    },

    feeWayNamChange(value, index) {
      var obj = this.cFeeWayOptions.find((item) => {//这里的userList就是上面遍历的数据源
        return item.feeWay === value;//筛选出匹配数据
      });
      this.dataForm.feeWayId = obj.feeWayCod
    },
    shipNamChange(value, index) {
      console.log("2", this.dataForm)
      var obj = this.checkWeightWorkTypeOptions.find((item) => {//这里的userList就是上面遍历的数据源
        return item.meaning === value;//筛选出匹配数据
      });
      console.log("2", this.dataForm)
      this.dataForm.shipNo = obj.itemTyp
    },
    cargoNamChange(value, index) {
      var obj = this.cargoNamOptions.find((item) => {//这里的userList就是上面遍历的数据源
        return item.cargoNam === value;//筛选出匹配数据
      });
      this.dataForm.cargoNo = obj.cargoCod
    },
    cSubunitChange(value, index) {
      var obj = this.cSubunitOptions.find((item) => {//这里的userList就是上面遍历的数据源
        return item.subunitNam === value;//筛选出匹配数据
      });
      this.dataForm.subunitCod = obj.subunitCod
    },
    workPlaceChange(value, index) {
      var obj = this.checkweightStorageLocOptions.find((item) => {//这里的userList就是上面遍历的数据源
        return item.label === value;//筛选出匹配数据
      });
      this.dataForm.workPlaceCod = obj.value
    },
    cargoClick() {
      this.cargoVisible = true
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getCargoForm()
    }, // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val
      this.getCargoForm()
    },
    getCargoForm() {
      getCargoPage(Object.assign(this.cargoForm, {
        current: this.pageIndex,
        size: this.pageSize
      })).then(response => {
        this.cargoDataList = response.data.data.records
        this.totalPage = response.data.data.total
      })
    },
    cargoHandleCurrentChange(val) {
      this.cargoCurrentRow = val;
    },
    cargoSaveClick() {
      if (this.cargoCurrentRow != undefined && this.cargoCurrentRow != '') {
        this.dataForm.cargoNam = this.cargoCurrentRow.cargoNam
        this.dataForm.cargoNo = this.cargoCurrentRow.cargoNo
        this.dataForm.cargokindCod = this.cargoCurrentRow.cargokindCod
        this.dataForm.requireUnitNam = this.cargoCurrentRow.shipperDoc
        this.cargoVisible = false

      } else {
        this.cargoVisible = false
      }
    }
  }
}
</script>