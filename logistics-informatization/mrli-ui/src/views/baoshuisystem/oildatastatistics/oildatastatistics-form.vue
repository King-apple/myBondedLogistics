<template>
  <el-dialog
    :title="!dataForm.fId ? '新增' : '修改'"
    :close-on-click-modal="false"
    width="80%"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="126px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="油品类型" prop="oilKnd">
            <el-input v-model="dataForm.oilKnd" placeholder="油品类型"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="登录账号" prop="loginAccount">
            <el-input v-model="dataForm.loginAccount" placeholder="登录账号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
       <el-col :span="6">
          <el-form-item label="库存情况">
            <el-input v-model="dataForm.inventory" placeholder="库存情况" readonly="readonly"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <div>
        <span>入库</span>
        <hr />
      </div>
      <el-row>
        <el-col :span="8">
          <el-form-item label="经营单位" prop="inOperationUnit">
            <!-- <el-input v-model="dataForm.inOperationUnit" placeholder="经营单位"></el-input> -->
            <el-select clearable v-model="dataForm.inOperationUnit" style="width:100%" placeholder="经营单位:" filterable>
							<el-option v-for="(item,index) in operationUnitOptions" @click.native="selectIn(item)" :key="index" :label="item.companyName" :value="item.companyName">
							</el-option>
						</el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="账册号" prop="inAccountNum">
            <el-input v-model="dataForm.inAccountNum" placeholder="账册号" readonly="readonly"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="代码" prop="inOperationUnitCod">
            <el-input v-model="dataForm.inOperationUnitCod" placeholder="代码" readonly="readonly"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="更新日期" prop="inUpdateDte">
            <el-date-picker
              v-model="dataForm.inUpdateDte"
              type="datetime"
              placeholder="更新日期"
              value-format="yyyy-MM-dd HH:mm:ss"
              style="width: 100%"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="入库报送日期" prop="inAddDte">
            <el-date-picker
              v-model="dataForm.inAddDte"
              type="datetime"
              placeholder="入库报送日期"
              value-format="yyyy-MM-dd HH:mm:ss"
              style="width: 100%"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="作业委托人:" prop="inConsignNam">
            <el-select
              v-model="dataForm.inConsignNam"
              placeholder="作业委托人:"
              filterable
              style="width: 100%"
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
        <el-col :span="8">
          <el-form-item label="商品名称" prop="inCargoNam">
            <el-input v-model="dataForm.inCargoNam" placeholder="商品名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="入库方式" prop="inType">
            <el-input v-model="dataForm.inType" placeholder="入库方式"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="提单号" prop="inBlno">
            <el-input v-model="dataForm.inBlno" placeholder="提单号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="储罐名称" prop="inStorageTank">
            <el-input v-model="dataForm.inStorageTank" placeholder="储罐名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="计划数量（千克）" prop="inQuantityPlan">
            <el-input v-model="dataForm.inQuantityPlan" placeholder="计划数量（千克）"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="密度标准" prop="inDensityStandard">
            <el-input v-model="dataForm.inDensityStandard" placeholder="密度标准"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="密度值" prop="inDensity">
            <el-input v-model="dataForm.inDensity" placeholder="密度值"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="计划开始时间" prop="inStartTim">
            <el-date-picker
              v-model="dataForm.inStartTim"
              type="datetime"
              placeholder="计划开始时间"
              value-format="yyyy-MM-dd HH:mm:ss"
              style="width: 100%"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="计划结束时间" prop="inEndTim">
            <el-date-picker
              v-model="dataForm.inEndTim"
              type="datetime"
              placeholder="计划结束时间"
              value-format="yyyy-MM-dd HH:mm:ss"
              style="width: 100%"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="进仓确认报送时间" prop="inConfirmTim">
            <el-date-picker
              v-model="dataForm.inConfirmTim"
              type="datetime"
              placeholder="进仓确认报送时间"
              value-format="yyyy-MM-dd HH:mm:ss"
              style="width: 100%"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="实际数量（千克）" prop="inQuantityReal">
            <el-input v-model="dataForm.inQuantityReal" placeholder="实际数量（千克）"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="进仓报关单号" prop="inCustomsDeclarationNum">
            <el-input v-model="dataForm.inCustomsDeclarationNum" placeholder="进仓报关单号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="入库备注">
            <el-input v-model="dataForm.inMarkTxt" placeholder="入库备注"   type="textarea":rows="1"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <div style="margin-top:10px;">
        <span>出库</span>
        <hr />
      </div>
      <el-row>
        <el-col :span="8">
          <el-form-item label="经营单位" prop="outOperationUnit">
            <!-- <el-input v-model="dataForm.outOperationUnit" placeholder="经营单位"></el-input> -->
            <el-select clearable v-model="dataForm.outOperationUnit" style="width:100%" placeholder="经营单位:" filterable>
							<el-option v-for="(item,index) in operationUnitOptions" @click.native="selectOut(item)" :key="index" :label="item.companyName" :value="item.companyName">
							</el-option>
						</el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="账册号" prop="outAccountNum">
            <el-input v-model="dataForm.outAccountNum" placeholder="账册号" readonly="readonly"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="代码" prop="outOperationUnitCod">
            <el-input v-model="dataForm.outOperationUnitCod" placeholder="代码" readonly="readonly"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="更新日期" prop="outUpdateDte">
            <el-date-picker
              v-model="dataForm.outUpdateDte"
              type="datetime"
              placeholder="更新日期"
              value-format="yyyy-MM-dd HH:mm:ss"
              style="width: 100%"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="出库报送日期" prop="outAddDte">
            <el-date-picker
              v-model="dataForm.outAddDte"
              type="datetime"
              placeholder="出库报送日期"
              value-format="yyyy-MM-dd HH:mm:ss"
              style="width: 100%"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="8">
        
          <el-form-item label="作业委托人:" prop="outConsignNam">
            <el-select
              v-model="dataForm.outConsignNam"
              placeholder="作业委托人:"
              filterable

              style="width: 100%"
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
        <el-col :span="8">
          <el-form-item label="商品名称" prop="outCargoNam">
            <el-input v-model="dataForm.outCargoNam" placeholder="商品名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="出库方式" prop="outType">
            <el-input v-model="dataForm.outType" placeholder="出库方式"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="提单号" prop="outBlno">
            <el-input v-model="dataForm.outBlno" placeholder="提单号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="储罐名称" prop="outStorageTank">
            <el-input v-model="dataForm.outStorageTank" placeholder="储罐名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="计划数量（千克）" prop="outQuantityPlan">
            <el-input v-model="dataForm.outQuantityPlan" placeholder="计划数量（千克）"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="密度标准" prop="outDensityStandard">
            <el-input v-model="dataForm.outDensityStandard" placeholder="密度标准"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="密度值" prop="outDensity">
            <el-input v-model="dataForm.outDensity" placeholder="密度值"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="计划开始时间" prop="outStartTim">
            <el-date-picker
              v-model="dataForm.outStartTim"
              type="datetime"
              placeholder="计划开始时间"
              value-format="yyyy-MM-dd HH:mm:ss"
              style="width: 100%"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="计划结束时间" prop="outEndTim">
            <el-date-picker
              v-model="dataForm.outEndTim"
              type="datetime"
              placeholder="计划结束时间"
              value-format="yyyy-MM-dd HH:mm:ss"
              style="width: 100%"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="出仓确认报送时间" prop="outConfirmTim">
            <el-date-picker
              v-model="dataForm.outConfirmTim"
              type="datetime"
              placeholder="出仓确认报送时间"
              value-format="yyyy-MM-dd HH:mm:ss"
              style="width: 100%"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="实际数量（千克）" prop="outQuantityReal">
            <el-input v-model="dataForm.outQuantityReal" placeholder="实际数量（千克）"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="出仓报关单号" prop="outCustomsDeclarationNum">
            <el-input v-model="dataForm.outCustomsDeclarationNum" placeholder="出仓报关单号"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="出库备注">
            <el-input v-model="dataForm.outMarkTxt"    style="width: 100%" placeholder="出库备注"   type="textarea":rows="1"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer" v-if = "copyFlag ">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" v-if="canSubmit">确定</el-button>
    </span>
    <span slot="footer" class="dialog-footer" v-if = "copyFlag2">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormCopy()" v-if="canSubmit">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
    import {getObj, addObj, putObj} from '@/api/baoshuisystem/oildatastatistics'
    import { fetchList } from '@/api/baoshuisystem/settledcompanyinfo'
    import XEUtils from 'xe-utils'
    import { getClientInfoAll } from "@/api/baoshuisystem/clientinfo";
    export default {
    data () {
      return {
        clientInfoOptions: [],
        copyFlag:false,
        copyFlag2:false,
        visible: false,
        canSubmit: false,
        dataForm: {
          fId: 0,
          inAccountNum: '',
          inOperationUnit: '',
          inOperationUnitCod: '',
          inUpdateDte: '',
          oilKnd: '',
          loginAccount: '',
          inAddDte: '',
          inConsignNam: '',
          inCargoNam: '',
          inType: '',
          inBlno: '',
          inStorageTank: '',
          inQuantityPlan: '',
          inDensityStandard: '',
          inDensity: '',
          inStartTim: '',
          inEndTim: '',
          inConfirmTim: '',
          inQuantityReal: '',
          inCustomsDeclarationNum: '',
          outAccountNum: '',
          outOperationUnit: '',
          outOperationUnitCod: '',
          outUpdateDte: '',
          outAddDte: '',
          outConsignNam: '',
          outCargoNam: '',
          outType: '',
          outBlno: '',
          outStorageTank: '',
          outQuantityPlan: '',
          outDensityStandard: '',
          outDensity: '',
          outStartTim: '',
          outEndTim: '',
          outConfirmTim: '',
          outQuantityReal: '',
          outCustomsDeclarationNum: '',
          inventory: '',
          operNam: '',
          recordTim: '',
          updateNam: '',
          updateTim: '',
          delNam: '',
          delTim: '',
          inMarkTxt: '',
          outMarkTxt: '',
        },
        // operationUnitOptions: [
        //   {companyCode:"CHDL",companyName:"营口港船货代理有限责任公司",electronicAccountNum:"L0951B18A004"},
        //   {companyCode:"BSCY",companyName:"营口港务集团保税货物储运有限公司",electronicAccountNum:"K09502E00002"}
        // ],
        operationUnitOptions: [],
        dataRule: {
          oilKnd: [
            { required: true, message: '油品类型不能为空', trigger: 'blur' }
          ],
          loginAccount: [
            { required: true, message: '登录账号不能为空', trigger: 'blur' }
          ],
          inOperationUnit: [
            { required: true, message: '入库经营单位不能为空', trigger: 'blur' }
          ],
          outOperationUnit: [
            { required: true, message: '出库经营单位不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    watch:{
      "dataForm.outQuantityReal":{
          handler(a,b){
          this.dataForm.inventory =XEUtils.subtract( XEUtils.toNumber(this.dataForm.inQuantityReal),XEUtils.toNumber(a))   
          this.dataForm.inventory =  XEUtils.toFixed(this.dataForm.inventory, 2) 
         }
            
      },
      "dataForm.inQuantityReal":{
        handler(a,b){
          this.dataForm.inventory =XEUtils.subtract(XEUtils.toNumber(a),XEUtils.toNumber(this.dataForm.outQuantityReal)) 
          this.dataForm.inventory =  XEUtils.toFixed(this.dataForm.inventory, 2) 
        }
       },
    },
    methods: {
      dataFormCopy(){
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.canSubmit = false;
             let a = XEUtils.clone(this.dataForm,true)
              a.fId=0
              addObj(a).then(data => {
                    this.$notify.success('添加成功')
                    this.visible = false
                    this.$emit('refreshDataList')
                }).catch(() => {
                    this.canSubmit = true;
                });
          }
        })
      },
      copyMethod(id){
        this.copyFlag = false
        this.copyFlag2  = true
        this.dataForm.fId = id || 0;
        this.visible = true;
        this.getInfo()
        this.canSubmit = true;
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.fId) {
            getObj(this.dataForm.fId).then(response => {
                this.dataForm = response.data.data
            })
          }
        })
        fetchList(Object.assign({
				})).then((res) => {
					this.operationUnitOptions = res.data.data.records //入驻企业详情-》入驻企业名称
				})
      },
      init (id) {
        this.copyFlag = true
        this.copyFlag2  = false
        this.dataForm.fId = id || 0;
        this.visible = true;
        this.canSubmit = true;
        this.getInfo()
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.fId) {
            getObj(this.dataForm.fId).then(response => {
                this.dataForm = response.data.data
            })
          }
        })
        fetchList(Object.assign({
				})).then((res) => {
					this.operationUnitOptions = res.data.data.records //入驻企业详情-》入驻企业名称
					console.log(res)
				})
      },
      selectIn(e) {
				this.dataForm.inAccountNum = e.electronicAccountNum //账册号
				this.dataForm.inOperationUnitCod = e.companyCode //代码
			},
      selectOut(e) {
				this.dataForm.outAccountNum = e.electronicAccountNum //账册号
				this.dataForm.outOperationUnitCod = e.companyCode //代码
			},
      async getInfo(){
        await getClientInfoAll().then((response) => {
                this.clientInfoOptions = response.data.data;
              });
      },
      // 表单提交
      dataFormSubmit () {
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
      }
    }
  }
</script>
<style scoped>
.el-col{
  margin-bottom: 0;
}
</style>
