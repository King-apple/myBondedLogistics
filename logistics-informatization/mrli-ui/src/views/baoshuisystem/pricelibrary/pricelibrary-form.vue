<template>
  <el-dialog
    :title="!dataForm.fId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible"
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="80px"
    >
      <el-row>
        <el-col :span="12">
          <el-form-item label="客户名称" prop="company">
            <el-select
              v-model="dataForm.company"
              placeholder="请选择"
              filterable
              @change="secondNamChange"
              style="width:100%"
            >
              <el-option
                v-for="(item, index) in clientOptions"
                :key="index"
                :label="item.company"
                :value="item.company"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="客户代码" prop="companyCode">
            <el-input
              v-model="dataForm.companyCode"
              placeholder="客户代码"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="货物名称" prop="cargoNam">
            <el-select
              v-model="dataForm.cargoNam"
              placeholder="请选择"
              filterable
              @change="contractObjectChange"
              style="width:100%"
            >
              <el-option
                v-for="(item, index) in contractObjectOptions"
                :key="index"
                :label="item.cargoNam"
                :value="item.cargoNam"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="货物编码" prop="cargoCod">
            <el-input v-model="dataForm.cargoCod" placeholder="货物编码" disabled></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="货类名称" prop="cargoKindNam">
            <el-input v-model="dataForm.cargoKindNam" placeholder="货类名称" disabled></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="货类编码" prop="cargoKindCod">
            <el-input v-model="dataForm.cargoKindCod" placeholder="货类编码" disabled></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="规格型号" prop="format">
            <el-input v-model="dataForm.format" placeholder="规格型号"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="包装" prop="pkgKindNam">
            <el-select
              v-model="dataForm.pkgKindNam"
              placeholder="请选择"
              filterable
              style="width:100%"
            >
              <el-option
                v-for="(item, index) in pkgKindNamDict"
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
        <el-col :span="12">
          <el-form-item label="费目" prop="feeNam">
            <el-select
              v-model="dataForm.feeNam"
              placeholder="请选择"
              filterable
              @change="feeNamChange"
              style="width:100%"
            >
              <el-option
                v-for="(item, index) in feeNamOptions"
                :key="index"
                :label="item.feeNam"
                :value="item.feeNam"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="费目编码" prop="feeNameCod">
            <el-input
              v-model="dataForm.feeNameCod"
              placeholder="费目编码"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="费率值" prop="feeRat">
            <el-input v-model="dataForm.feeRat" placeholder="费率值"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="费率单位" prop="feeUnit">
            <el-select
              v-model="dataForm.feeUnit"
              placeholder="请选择"
              filterable
              @change="feeUnitChange"
              style="width:100%"
            >
              <el-option
                v-for="(item, index) in feeUnitOptions"
                :key="index"
                :label="item.feeUnit"
                :value="item.feeUnit"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="存放地点" prop="locate">
            <el-select
              v-model="dataForm.locate"
              placeholder="请选择"
              filterable
              style="width:100%"
            >
              <el-option
                v-for="(item, index) in locateOptions"
                :key="index"
                :label="item.location"
                :value="item.location"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="进出口" prop="inoutId">
            <el-select
              v-model="dataForm.inoutId"
              placeholder="请选择"
              filterable
              style="width:100%"
            >
              <el-option
                v-for="(item, index) in inoutIDict"
                :key="index"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="内外贸" prop="tradeId">
            <el-select
              v-model="dataForm.tradeId"
              placeholder="请选择"
              filterable
              style="width:100%"
            >
              <el-option
                v-for="(item, index) in tradeIdDict"
                :key="index"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="备注" prop="markTxt">
            <el-input v-model="dataForm.markTxt" placeholder="备注"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" v-if="canSubmit"
        >确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { getObj, addObj, putObj } from '@/api/baoshuisystem/pricelibrary'
import { getClientInfoAll } from '@/api/baoshuisystem/clientinfo'
import { getAll as getFeeUnitsAll } from '@/api/baoshuisystem/feeunits'
import { getContractObjectInfoAll } from '@/api/baoshuisystem/contractobject'
import { getFeenamesAll as feeList } from '@/api/baoshuisystem/feenames'
import { getAll as getLocationsAll } from '@/api/baoshuisystem/storagelocation'
import { getPkgKindNamDic, getInoutIdDic, getTradeIdDic } from '@/api/baoshuisystem/getdictdata'

export default {
  data() {
    return {
      visible: false,
      canSubmit: false,
      clientOptions: [],
      contractObjectOptions: [],
      feeNamOptions: [],
      feeUnitOptions: [],
      locateOptions: [],
      pkgKindNamDict: [],
      tradeIdDict: [],
      inoutIDict: [],
      dataForm: {
        fId: 0,
        companyCod: '',
        company: '',
        cargoCod: '',
        cargoNam: '',
        cargoKindCod: '',
        cargoKindNam: '',
        format: '',
        pkgKindNam: '',
        feeNameCod: '',
        feeNam: '',
        feeRat: '',
        feeUnit: '',
        markTxt: '',
        locate: '',
        tradeId: '',
        inout_id: '',
        operNam: '',
        recordTim: '',
        updateNam: '',
        updateTim: '',
        delNam: '',
        delTim: ''
      },
      dataRule: {
        companyCod: [
          { required: true, message: '客户代码不能为空', trigger: 'blur' }
        ],
        company: [
          { required: true, message: '客户名称不能为空', trigger: 'blur' }
        ],
        cargoCod: [
          { required: true, message: '货物编码不能为空', trigger: 'blur' }
        ],
        cargoNam: [
          { required: true, message: '货物名称不能为空', trigger: 'blur' }
        ],
        cargoKndCod: [
          { required: true, message: '货类编码不能为空', trigger: 'blur' }
        ],
        cargoKndNam: [
          { required: true, message: '货类名称不能为空', trigger: 'blur' }
        ],
        format: [
          { required: true, message: '规格型号不能为空', trigger: 'blur' }
        ],
        pkgKindNam: [
          { required: true, message: '包装不能为空', trigger: 'blur' }
        ],
        feeNameCod: [
          { required: true, message: '费目编码不能为空', trigger: 'blur' }
        ],
        feeNam: [
          { required: true, message: '费目不能为空', trigger: 'blur' }
        ],
        feeRat: [
          { required: true, message: '费率值不能为空', trigger: 'blur' }
        ],
        feeUnit: [
          { required: true, message: '费率单位不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    init(id) {
      this.dataForm.fId = id || 0;
      this.visible = true;
      this.canSubmit = true;
      this.getClientOptions();
      this.getContractObjectOptions();
      this.getFeeUnitOptions(),
      this.getPkgKindNamDicOptions(),
      this.getLocateOptions(),
      this.getTradeIdDicOptions(),
      this.getInoutIdDicOptions(),
      this.getFeeNamOptions(),
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.fId) {
            getObj(this.dataForm.fId).then(response => {
              this.dataForm = response.data.data
              this.dataForm.inoutId = response.data.data.inoutId + ''
              this.dataForm.tradeId = response.data.data.tradeId + ''
            })
          }
        })
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.canSubmit = false;
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

    getClientOptions() {
      getClientInfoAll().then(data => {
        this.clientOptions = data.data.data;

      })
    },
    getContractObjectOptions() {
      getContractObjectInfoAll().then(data => {
        this.contractObjectOptions = data.data.data;
      })
    },
    getFeeNamOptions() {
      feeList().then(data => {
        this.feeNamOptions = data.data.data
      })
    },
    getFeeUnitOptions() {
      getFeeUnitsAll().then(data => {
        this.feeUnitOptions = data.data.data
      })
    },
    getPkgKindNamDicOptions() {
      getPkgKindNamDic().then(data => {
        this.pkgKindNamDict = data.data.data;
      })
    },
    getLocateOptions() {
      getLocationsAll().then(data => {
        this.locateOptions = data.data.data
      })
    },
    getTradeIdDicOptions() {
      getTradeIdDic().then(data => {
        this.tradeIdDict = data.data.data
      })
    },
    getInoutIdDicOptions() {
      getInoutIdDic().then(data => {
        this.inoutIDict = data.data.data
      })
    },
    secondNamChange: function (value) {
      var obj = this.clientOptions.find((item) => {//这里的userList就是上面遍历的数据源
        return item.company === value;//筛选出匹配数据
      });
      this.dataForm.companyCode = obj.companyCode
    },
    contractObjectChange: function (value) {
      var obj = this.contractObjectOptions.find((item) => {//这里的userList就是上面遍历的数据源
        return item.cargoNam === value;//筛选出匹配数据
      });
      this.dataForm.cargoCod = obj.cargoCod
      this.dataForm.cargoKindCod = obj.cargoKindCod
      this.dataForm.cargoKindNam = obj.cargoKindNam
    },

    feeNamChange: function (value) {
      var obj = this.feeNamOptions.find((item) => {//这里的userList就是上面遍历的数据源
        return item.feeNam === value;//筛选出匹配数据
      });
      this.dataForm.feeNameCod = obj.feeNameCod
    },
    feeUnitChange: function (value) {
      var obj = this.feeUnitOptions.find((item) => {
        return item.feeUnit === value;//筛选出匹配数据
      });
    },
  }
}
</script>
