<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
   
   	<el-row class="table-row">
		<el-col :span="12">
			<el-form-item label="工票号" label-width="140px" prop="workbillNo">
		      <el-input v-model="dataForm.workbillNo" disabled="disabled" placeholder="工票号"></el-input>
		    </el-form-item>
		</el-col>
		<el-col :span="12">
			<el-form-item label="作业日期" label-width="140px" prop="workDte">
			    <el-date-picker
			      v-model="dataForm.workDte"
			      type="date"
			      disabled="disabled"
			      placeholder="选择日期">
			    </el-date-picker>
			</el-form-item>
		</el-col>
	</el-row>
	
	<el-row class="table-row">
		<el-col :span="12">
			<el-form-item label="单位名称" label-width="140px" prop="subunitNam">
		      <el-input v-model="dataForm.subunitNam" disabled="disabled" placeholder="单位名称"></el-input>
		    </el-form-item>
		</el-col>
		<el-col :span="12">
			<el-form-item label="车型" label-width="140px" prop="machTypeNam">
	      	  <el-input v-model="dataForm.machTypeNam" disabled="disabled" placeholder="车型"></el-input>
	    	</el-form-item>
		</el-col>
	</el-row>
	
	<el-row class="table-row">
		<el-col :span="12">
			<el-form-item label="车型号" label-width="140px" prop="machKndCod">
		      <el-input v-model="dataForm.machKndCod" disabled="disabled" placeholder="车型号"></el-input>
		    </el-form-item>
		</el-col>
		<el-col :span="12">
			 <el-form-item label="作业内容" label-width="140px" prop="workTypeNam">
		      <el-input v-model="dataForm.workTypeNam" disabled="disabled" placeholder="作业内容"></el-input>
		     </el-form-item>
		</el-col>
	</el-row>
   
   <el-row class="table-row">
		<el-col :span="12">
			<el-form-item label="小时/吨" label-width="140px" prop="hours">
		      <el-input v-model="dataForm.hours" disabled="disabled"  placeholder="小时/吨"></el-input>
		    </el-form-item>
		</el-col>
		<el-col :span="12">
			 <el-form-item label="作业金额(元)" label-width="140px" prop="sum">
		      <el-input v-model="dataForm.sum" disabled="disabled" placeholder="作业金额（元）"></el-input>
		    </el-form-item>
		</el-col>
	</el-row>
	
	<el-divider></el-divider>

    <el-row class="table-row">
		<el-col :span="12">
			<el-form-item label="单价" label-width="140px" prop="unitPrice">
		      <el-input v-model="dataForm.unitPrice" 
              onkeyup="value=value.match(/\d+\.?\d{0,4}/)[0]"
              @input="unitPrice"
               placeholder="单价"></el-input>
		    </el-form-item>
		</el-col>
		<el-col :span="12">
			<el-form-item label="检算日期" label-width="140px" prop="checkDte">
			      <el-date-picker
				      v-model="dataForm.checkDte"
				      type="date"
				      value-format="yyyy-MM-dd HH:mm:ss"
				      placeholder="选择日期">
			      </el-date-picker>
		    </el-form-item>
			   
		</el-col>
	</el-row>
    
    
   <el-row class="table-row">
		<el-col :span="12">
			<el-form-item label="结算日期" label-width="140px" prop="settleDte">
			      	  <el-date-picker
					      v-model="dataForm.settleDte"
					      type="date"
					      value-format="yyyy-MM-dd HH:mm:ss"
					      placeholder="选择日期">
				      </el-date-picker>
			    </el-form-item>
		</el-col>
		<el-col :span="12">
			<el-form-item label="收到发票日期" label-width="140px" prop="receiptInvoiceDte">
		      <el-date-picker
				      v-model="dataForm.receiptInvoiceDte"
				      type="date"
				      value-format="yyyy-MM-dd HH:mm:ss"
				      placeholder="选择日期">
		      </el-date-picker>
		    </el-form-item>
		</el-col>
	</el-row>
	
	<el-row class="table-row">
		<el-col :span="12">
			<el-form-item label="已结算金额" label-width="140px" prop="settledAmount">
			     <el-input v-model="dataForm.settledAmount" placeholder="已结算金额"></el-input>
			  </el-form-item>
		</el-col>
		<el-col :span="12">
			<el-form-item label="未结算金额" label-width="140px" prop="outstandingAmount">
		      <el-input v-model="dataForm.outstandingAmount" placeholder="未结算金额"></el-input>
		    </el-form-item>
		</el-col>
	</el-row>
  
    
    
   
    
    <!--<el-form-item label="添加人" prop="operNam">
      <el-input v-model="dataForm.operNam" placeholder="添加人"></el-input>
    </el-form-item>
    <el-form-item label="添加时间" prop="recordTim">
      <el-input v-model="dataForm.recordTim" placeholder="添加时间"></el-input>
    </el-form-item>
    <el-form-item label="修改人" prop="updateNam">
      <el-input v-model="dataForm.updateNam" placeholder="修改人"></el-input>
    </el-form-item>
    <el-form-item label="修改时间" prop="updateTim">
      <el-input v-model="dataForm.updateTim" placeholder="修改时间"></el-input>
    </el-form-item>
    <el-form-item label="删除人" prop="delNam">
      <el-input v-model="dataForm.delNam" placeholder="删除人"></el-input>
    </el-form-item>
    <el-form-item label="删除时间" prop="delTim">
      <el-input v-model="dataForm.delTim" placeholder="删除时间"></el-input>
    </el-form-item>-->
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" v-if="canSubmit">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
    import {getObj, addObj, putObj} from '@/api/baoshuisystem/machinemanpowerexpense'

    export default {
    data () {
      return {
        visible: false,
        canSubmit: false,
        dataForm: {
          fId: 0,
          workbillNo: '',
          workDte: '',
          subunitNam: '',
          machTypeNam: '',
          machKndCod: '',
          workTypeNam: '',
          hours: '',
          unitPrice: '',
          sum: '',
          checkDte: '',
          settleDte: '',
          receiptInvoiceDte: '',
          settledAmount: '',
          outstandingAmount: '',
          operNam: '',
          recordTim: '',
          updateNam: '',
          updateTim: '',
          delNam: '',
          delTim: ''
        },
        dataRule: {
          workbillNo: [
            { required: true, message: '工票号不能为空', trigger: 'blur' }
          ],
          workDte: [
            { required: true, message: '作业日期不能为空', trigger: 'blur' }
          ],
          subunitNam: [
            { required: true, message: '单位名称不能为空', trigger: 'blur' }
          ],
          machTypeNam: [
            { required: true, message: '车型不能为空', trigger: 'blur' }
          ],
          machKndCod: [
            { required: true, message: '车型号不能为空', trigger: 'blur' }
          ],
          workTypeNam: [
            { required: true, message: '作业内容不能为空', trigger: 'blur' }
          ],
          hours: [
            { required: true, message: '小时/吨不能为空', trigger: 'blur' }
          ],
          unitPrice: [
            { required: true, message: '单价不能为空', trigger: 'blur' }
          ],
          sum: [
            { required: true, message: '作业金额（元）不能为空', trigger: 'blur' }
          ],
          checkDte: [
            { required: true, message: '检算日期不能为空', trigger: 'blur' }
          ],
          settleDte: [
            { required: true, message: '结算日期不能为空', trigger: 'blur' }
          ],
          receiptInvoiceDte: [
            { required: true, message: '收到发票日期不能为空', trigger: 'blur' }
          ],
          settledAmount: [
            { required: true, message: '已结算金额不能为空', trigger: 'blur' }
          ],
          outstandingAmount: [
            { required: true, message: '未结算金额不能为空', trigger: 'blur' }
          ],
          operNam: [
            { required: true, message: '添加人不能为空', trigger: 'blur' }
          ],
          recordTim: [
            { required: true, message: '添加时间不能为空', trigger: 'blur' }
          ],
          updateNam: [
            { required: true, message: '修改人不能为空', trigger: 'blur' }
          ],
          updateTim: [
            { required: true, message: '修改时间不能为空', trigger: 'blur' }
          ],
          delNam: [
            { required: true, message: '删除人不能为空', trigger: 'blur' }
          ],
          delTim: [
            { required: true, message: '删除时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (item) {
      	this.dataForm= {
          fId: 0,
          workbillNo: '',
          workDte: '',
          subunitNam: '',
          machTypeNam: '',
          machKndCod: '',
          workTypeNam: '',
          hours: '',
          unitPrice: '',
          sum: '',
          checkDte: '',
          settleDte: '',
          receiptInvoiceDte: '',
          settledAmount: '',
          outstandingAmount: '',
          operNam: '',
          recordTim: '',
          updateNam: '',
          updateTim: '',
          delNam: '',
          delTim: ''
        },
      	console.log(item)
        this.dataForm.fId = item.fId || 0;
        this.dataForm = item
        this.visible = true;
        this.canSubmit = true;
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.fId) {
            getObj(this.dataForm.fId).then(response => {
                this.dataForm = response.data.data
//              this.dataForm.workDte = response.data.data.workDte.substring(0,10)
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
      	console.log(this.dataForm)
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
      unitPrice(val) {
      this.dataForm.unitPrice = val.match(/\d+\.?\d{0,4}/)[0]
      console.log(this.dataForm)
      if (this.dataForm.hours && this.dataForm.unitPrice) {
        console.log(val, this.dataForm.unitPrice)
        let num = this.dataForm.unitPrice.match(/\d+\.?\d{0,4}/)[0] * this.dataForm.hours
        this.dataForm.sum = num.toFixed(2)
      } else {
        this.dataForm.sum = 0
      }
    },
      
    }
  }
</script>
