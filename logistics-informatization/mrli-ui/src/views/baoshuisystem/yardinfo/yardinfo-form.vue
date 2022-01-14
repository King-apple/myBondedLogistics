<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible"
    width="70%"
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="30%"
      label-position="right"
    >
      <el-row>
        <el-col :span="12">
          <el-form-item label="入库单编号" prop="inYardNo">
            <el-input
              class="inline-input readonlyClass"
              v-model="dataForm.inYardNo"
              placeholder="入库单编号"
              style="width: 100%"
              @input="focusInyardNo"
              :disabled="readonly"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="作业委托人" prop="consignNam">
            <el-select
              class="readonlyClass"
              v-model="dataForm.consignNam"
              placeholder="作业委托人"
              filterable
              style="width: 100%"
              :disabled="readonly"
            >
              <el-option
                v-for="(item, index) in clientInfoOptions"
                :key="index"
                :label="item.company"
                :value="item.company"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item label="货名" prop="cargoNam">
            <el-select
              class="readonlyClass"
              v-model="dataForm.cargoNam"
              placeholder="货物名称"
              filterable
              style="width: 100%"
              :disabled="readonly"
            >
              <el-option
                v-for="(item, index) in cargoNamOptions"
                :key="index"
                :label="item.cargoNam"
                :value="item.cargoNam"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="船名" prop="shipNam">
            <el-input
              class="readonlyClass"
              v-model="dataForm.shipNam"
              placeholder="船名"
              :disabled="readonly"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="入库件数" prop="inPiecesNum">
            <el-input
              v-model="dataForm.inPiecesNum"
              placeholder="入库件数"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="入库重量" prop="inWeightWgt">
            <el-input
              v-model="dataForm.inWeightWgt"
              placeholder="入库重量"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="入库日期" prop="inYardDat">
            <el-date-picker
              class="readonlyClass"
              v-model="dataForm.inYardDat"
              type="date"
              placeholder="入库日期"
              value-format="yyyy-MM-dd"
              style="width: 100%"
              :disabled="readonly"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="入库结算日期" prop="inSettleDat">
            <el-date-picker
              v-model="dataForm.inSettleDat"
              type="date"
              placeholder="入库结算日期"
              value-format="yyyy-MM-dd"
              style="width: 100%"
              :disabled="readonly"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="出库件数" prop="outPiecesNum">
            <el-input
              v-model="dataForm.outPiecesNum"
              placeholder="出库件数"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="出库重量" prop="outWeightWgt">
            <el-input
              v-model="dataForm.outWeightWgt"
              placeholder="出库重量"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item label="剩余件数" prop="surplusPiecesNum">
            <el-input
              v-model="dataForm.surplusPiecesNum"
              placeholder="剩余件数"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="剩余重量" prop="surplusWeightWgt">
            <el-input
              v-model="dataForm.surplusWeightWgt"
              placeholder="剩余重量"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="堆存地点" prop="yardNam">
            <el-select
              v-model="dataForm.yardNam"
              placeholder="堆存地点"
              filterable
              style="width: 100%"
              class="readonlyClass"
              :disabled="readonly"
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
        <el-col :span="12">
          <el-form-item label="库区" prop="areaNam">
            <el-select
              v-model="dataForm.areaNam"
              placeholder="库区"
              filterable
              style="width: 100%"
            >
              <el-option
                v-for="(item, index) in portAreaOptions"
                :key="index"
                :label="item.value"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="货票编号" prop="bh">
            <el-input v-model="dataForm.bh" placeholder="货票编号"></el-input>
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
import { getObj, addObj, putObj, getPortArea } from '@/api/baoshuisystem/yardinfo'
import { fetchList as ioyardFetchList } from '@/api/baoshuisystem/ioyard'
import { getClientInfoAll } from '@/api/baoshuisystem/clientinfo'
import { getContractObjectInfoAll } from '@/api/baoshuisystem/contractobject'
import { getAll as storagelocationAll } from '@/api/baoshuisystem/storagelocation'

export default {
  data() {
    return {
      visible: false,
      canSubmit: false,
      dataForm: {
        fId: 0,
        inYardNo: '',
        consignNam: '',
        cargoNam: '',
        shipNam: '',
        inPiecesNum: '',
        inWeightWgt: '',
        inYardDat: '',
        inSettleDat: '',
        outPiecesNum: '',
        outWeightWgt: '',
        surplusPiecesNum: '',
        surplusWeightWgt: '',
        yardNam: '',
        areaNam: '',
        areaStart: '',
        areaEnd: '',
        operNam: '',
        recordTim: '',
        updateNam: '',
        updateTim: '',
        delNam: '',
        delTim: '',
      },
      inYardNoOptions: [],
      clientInfoOptions: [],
      cargoNamOptions: [],
      portAreaOptions: [],
      portLocationOptions: [],
      readonly: false,
      pickerOptions: {
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }, {
          text: '昨天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '一周前',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }]
      },
      dataRule: {
        // inYardNo: [
        //   { required: true, message: '入库单编号不能为空', trigger: 'blur' }
        // ],
        // consignNam: [
        //   { required: true, message: '货主不能为空', trigger: 'blur' }
        // ],
        // cargoNam: [
        //   { required: true, message: '货名不能为空', trigger: 'blur' }
        // ],
        // shipNam: [
        //   { required: true, message: '船名不能为空', trigger: 'blur' }
        // ],
        // inPiecesNum: [
        //   { required: true, message: '入库件数不能为空', trigger: 'blur' }
        // ],
        // inWeightWgt: [
        //   { required: true, message: '入库重量不能为空', trigger: 'blur' }
        // ],
        // inYardDat: [
        //   { required: true, message: '入库日期不能为空', trigger: 'blur' }
        // ],
        // inSettleDat: [
        //   { required: true, message: '入库结算日期不能为空', trigger: 'blur' }
        // ],
        // outPiecesNum: [
        //   { required: true, message: '出库件数不能为空', trigger: 'blur' }
        // ],
        // outWeightWgt: [
        //   { required: true, message: '出库重量不能为空', trigger: 'blur' }
        // ],
        // surplusPiecesNum: [
        //   { required: true, message: '剩余件数不能为空', trigger: 'blur' }
        // ],
        // surplusWeightWgt: [
        //   { required: true, message: '剩余重量不能为空', trigger: 'blur' }
        // ],
        // yardNam: [
        //   { required: true, message: '堆存地点不能为空', trigger: 'blur' }
        // ],
        // areaNam: [
        //   { required: true, message: '库区不能为空', trigger: 'blur' }
        // ],
        // areaStart: [
        //   { required: true, message: '起点不能为空', trigger: 'blur' }
        // ],
        // areaEnd: [
        //   { required: true, message: '终点不能为空', trigger: 'blur' }
        // ],
        // operNam: [
        //   { required: true, message: '添加人不能为空', trigger: 'blur' }
        // ],
        // recordTim: [
        //   { required: true, message: '添加时间不能为空', trigger: 'blur' }
        // ],
        // updateNam: [
        //   { required: true, message: '修改人不能为空', trigger: 'blur' }
        // ],
        // updateTim: [
        //   { required: true, message: '修改时间不能为空', trigger: 'blur' }
        // ],
        // delNam: [
        //   { required: true, message: '删除人不能为空', trigger: 'blur' }
        // ],
        // delTim: [
        //   { required: true, message: '删除时间不能为空', trigger: 'blur' }
        // ]
      }
    }
  },
  methods: {
    init(id) {
      this.dataForm.fId = id || 0;
      this.visible = true;
      this.canSubmit = true;
      this.getAllClient();
      this.getAllContractObject();
      this.getMyPortrArea();
      this.getAllLocation();
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.fId) {
          getObj(this.dataForm.fId).then(response => {
            this.dataForm = response.data.data
          })
          this.readonly = true

        } else {
          this.readonly = false
        }
      })
      if (this.dataForm.fId == 0) {

      }
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.canSubmit = false;
          if (this.dataForm.inPiecesNum == '') {
            this.dataForm.inPiecesNum = 0;
          }
          if (this.dataForm.inWeightWgt == '') {
            this.dataForm.inWeightWgt = 0;
          }
          if (this.dataForm.outPiecesNum == '') {
            this.dataForm.outPiecesNum = 0;
          }
          if (this.dataForm.outWeightWgt == '') {
            this.dataForm.outWeightWgt = 0;
          }
          if (this.dataForm.surplusPiecesNum == '') {
            this.dataForm.surplusPiecesNum = this.dataForm.inPiecesNum;
          }
          if (this.dataForm.surplusWeightWgt == '') {
            this.dataForm.surplusWeightWgt = this.dataForm.inWeightWgt;
          }
          if (this.dataForm.fId) {

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
    inYardNoQuerySearch(queryString, cb) {
      var inYardNoOptions = this.inYardNoOptions;
      var results = [];
      for (var i in inYardNoOptions) {
        var map = { value: inYardNoOptions[i].inoutYardNo };

        results.push(map);
      }
      // 调用 callback 返回建议列表的数据
      cb(results);
    },


    getAllClient() {
      getClientInfoAll().then(response => {
        this.clientInfoOptions = response.data.data
      })
    },
    getAllContractObject() {
      getContractObjectInfoAll().then(response => {
        this.cargoNamOptions = response.data.data
      })
    },
    getMyPortrArea() {
      getPortArea().then(response => {
        this.portAreaOptions = response.data.data
      })
    },
    getAllLocation() {
      storagelocationAll().then(response => {
        this.portLocationOptions = response.data.data
      })
    },
    focusInyardNo(InyardNo) {
      var flag = 0;
      var map = { inoutYardNo: this.dataForm.inYardNo }
      ioyardFetchList().then(response => {
        this.inYardNoOptions = response.data.data.records
        for (var i in this.inYardNoOptions) {
          if (this.dataForm.inYardNo == this.inYardNoOptions[i].inoutYardNo) {
            this.dataForm.consignNam = this.inYardNoOptions[i].consignNam
            this.dataForm.cargoNam = this.inYardNoOptions[i].cargoNam
            this.dataForm.shipNam = this.inYardNoOptions[i].shipNam
            this.dataForm.inPiecesNum = this.inYardNoOptions[i].piecesNum
            this.dataForm.inWeightWgt = this.inYardNoOptions[i].weightWgt
            this.dataForm.inYardDat = this.inYardNoOptions[i].ioyardDte
            this.dataForm.yardNam = this.inYardNoOptions[i].yardNo
            this.dataForm.bh = this.inYardNoOptions[i].ioyardNo
            flag = 1
            break;
          }
          if (flag == 0) {
            this.dataForm = {
              fId: this.dataForm.fId,
              inYardNo: this.dataForm.inYardNo,
              consignNam: '',
              cargoNam: '',
              shipNam: '',
              inPiecesNum: '',
              inWeightWgt: '',
              inYardDat: '',
              inSettleDat: '',
              outPiecesNum: '',
              outWeightWgt: '',
              surplusPiecesNum: '',
              surplusWeightWgt: '',
              yardNam: '',
              areaNam: '',
              areaStart: '',
              areaEnd: '',
              operNam: '',
              recordTim: '',
              updateNam: '',
              updateTim: '',
              delNam: '',
              delTim: '',
            }
          }
        }
      })


    }
  }
}
</script>
