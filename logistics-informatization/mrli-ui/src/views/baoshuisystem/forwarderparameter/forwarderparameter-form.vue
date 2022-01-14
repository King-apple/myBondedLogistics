<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="序号(付款)" prop="serialNumberPay">
      <el-input v-model="dataForm.serialNumberPay" placeholder="序号(付款)"></el-input>
    </el-form-item>
    <el-form-item label="合同编号" prop="contCod">
      <el-input v-model="dataForm.contCod" placeholder="合同编号"></el-input>
    </el-form-item>
    <el-form-item label="日期(单据)" prop="billDate">
      <el-input v-model="dataForm.billDate" placeholder="日期(单据)"></el-input>
    </el-form-item>
    <el-form-item label="客户" prop="secondNam">
      <el-input v-model="dataForm.secondNam" placeholder="客户"></el-input>
    </el-form-item>
    <el-form-item label="货名" prop="cargoNam">
      <el-input v-model="dataForm.cargoNam" placeholder="货名"></el-input>
    </el-form-item>
    <el-form-item label="操作量(吨)" prop="feeQuantity">
      <el-input v-model="dataForm.feeQuantity" placeholder="操作量(吨)"></el-input>
    </el-form-item>
    <el-form-item label="件数" prop="nember">
      <el-input v-model="dataForm.nember" placeholder="件数"></el-input>
    </el-form-item>
    <el-form-item label="船名" prop="shipNam">
      <el-input v-model="dataForm.shipNam" placeholder="船名"></el-input>
    </el-form-item>
    <el-form-item label="装/卸日期" prop="loadUnloadDate">
      <el-input v-model="dataForm.loadUnloadDate" placeholder="装/卸日期"></el-input>
    </el-form-item>
    <el-form-item label="往来款(元)" prop="currentAccount">
      <el-input v-model="dataForm.currentAccount" placeholder="往来款(元)"></el-input>
    </el-form-item>
    <el-form-item label="付款内容" prop="payContent">
      <el-input v-model="dataForm.payContent" placeholder="付款内容"></el-input>
    </el-form-item>
    <el-form-item label="付款单位" prop="payUnit">
      <el-input v-model="dataForm.payUnit" placeholder="付款单位"></el-input>
    </el-form-item>
    <el-form-item label="收款单位" prop="firstNam">
      <el-input v-model="dataForm.firstNam" placeholder="收款单位"></el-input>
    </el-form-item>
    <el-form-item label="金额" prop="money">
      <el-input v-model="dataForm.money" placeholder="金额"></el-input>
    </el-form-item>
    <el-form-item label="收到发票" prop="receiptInvoice">
      <el-input v-model="dataForm.receiptInvoice" placeholder="收到发票"></el-input>
    </el-form-item>
    <el-form-item label="业务结算" prop="businessSettlementNam">
      <el-input v-model="dataForm.businessSettlementNam" placeholder="业务结算"></el-input>
    </el-form-item>
    <el-form-item label="开票申请" prop="applicationInvoice">
      <el-input v-model="dataForm.applicationInvoice" placeholder="开票申请"></el-input>
    </el-form-item>
    <el-form-item label="开出发票" prop="openInvoice">
      <el-input v-model="dataForm.openInvoice" placeholder="开出发票"></el-input>
    </el-form-item>
    <el-form-item label="序号(结算)" prop="serialNumberSet">
      <el-input v-model="dataForm.serialNumberSet" placeholder="序号(结算)"></el-input>
    </el-form-item>
    <el-form-item label="结算日期(月份)" prop="settlemnentDataMonth">
      <el-input v-model="dataForm.settlemnentDataMonth" placeholder="结算日期(月份)"></el-input>
    </el-form-item>
    <el-form-item label="阶梯价格" prop="ladderPrice">
      <el-input v-model="dataForm.ladderPrice" placeholder="阶梯价格"></el-input>
    </el-form-item>
    <el-form-item label="寄出发票" prop="sendInvoice">
      <el-input v-model="dataForm.sendInvoice" placeholder="寄出发票"></el-input>
    </el-form-item>
    <el-form-item label="收到款项" prop="receivedPay">
      <el-input v-model="dataForm.receivedPay" placeholder="收到款项"></el-input>
    </el-form-item>
    <el-form-item label="利润" prop="profits">
      <el-input v-model="dataForm.profits" placeholder="利润"></el-input>
    </el-form-item>
    <el-form-item label="余款" prop="spareMoney">
      <el-input v-model="dataForm.spareMoney" placeholder="余款"></el-input>
    </el-form-item>
    <el-form-item label="差额" prop="differenceMoney">
      <el-input v-model="dataForm.differenceMoney" placeholder="差额"></el-input>
    </el-form-item>
    <el-form-item label="备注" prop="markTxt">
      <el-input v-model="dataForm.markTxt" placeholder="备注"></el-input>
    </el-form-item>
    <el-form-item label="添加人" prop="operNam">
      <el-input v-model="dataForm.operNam" placeholder="添加人"></el-input>
    </el-form-item>
    <el-form-item label="添加人时间" prop="recordTim">
      <el-input v-model="dataForm.recordTim" placeholder="添加人时间"></el-input>
    </el-form-item>
    <el-form-item label="修改人" prop="updateNam">
      <el-input v-model="dataForm.updateNam" placeholder="修改人"></el-input>
    </el-form-item>
    <el-form-item label="修改时间" prop="updateTim">
      <el-input v-model="dataForm.updateTim" placeholder="修改时间"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" v-if="canSubmit">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
    import {getObj, addObj, putObj} from '@/api/baoshuisystem/forwarderparameter'

    export default {
    data () {
      return {
        visible: false,
        canSubmit: false,
        dataForm: {
          fId: 0,
          serialNumberPay: '',
          contCod: '',
          billDate: '',
          secondNam: '',
          cargoNam: '',
          feeQuantity: '',
          nember: '',
          shipNam: '',
          loadUnloadDate: '',
          currentAccount: '',
          payContent: '',
          payUnit: '',
          firstNam: '',
          money: '',
          receiptInvoice: '',
          businessSettlementNam: '',
          applicationInvoice: '',
          openInvoice: '',
          serialNumberSet: '',
          settlemnentDataMonth: '',
          ladderPrice: '',
          sendInvoice: '',
          receivedPay: '',
          profits: '',
          spareMoney: '',
          differenceMoney: '',
          markTxt: '',
          operNam: '',
          recordTim: '',
          updateNam: '',
          updateTim: '',
          delNam: '',
          delTim: ''
        },
        dataRule: {
          serialNumberPay: [
            { required: true, message: '序号(付款)不能为空', trigger: 'blur' }
          ],
          contCod: [
            { required: true, message: '合同编号不能为空', trigger: 'blur' }
          ],
          billDate: [
            { required: true, message: '日期(单据)不能为空', trigger: 'blur' }
          ],
          secondNam: [
            { required: true, message: '客户不能为空', trigger: 'blur' }
          ],
          cargoNam: [
            { required: true, message: '货名不能为空', trigger: 'blur' }
          ],
          feeQuantity: [
            { required: true, message: '操作量(吨)不能为空', trigger: 'blur' }
          ],
          nember: [
            { required: true, message: '件数不能为空', trigger: 'blur' }
          ],
          shipNam: [
            { required: true, message: '船名不能为空', trigger: 'blur' }
          ],
          loadUnloadDate: [
            { required: true, message: '装/卸日期不能为空', trigger: 'blur' }
          ],
          currentAccount: [
            { required: true, message: '往来款(元)不能为空', trigger: 'blur' }
          ],
          payContent: [
            { required: true, message: '付款内容不能为空', trigger: 'blur' }
          ],
          payUnit: [
            { required: true, message: '付款单位不能为空', trigger: 'blur' }
          ],
          firstNam: [
            { required: true, message: '收款单位不能为空', trigger: 'blur' }
          ],
          money: [
            { required: true, message: '金额不能为空', trigger: 'blur' }
          ],
          receiptInvoice: [
            { required: true, message: '收到发票不能为空', trigger: 'blur' }
          ],
          businessSettlementNam: [
            { required: true, message: '业务结算不能为空', trigger: 'blur' }
          ],
          applicationInvoice: [
            { required: true, message: '开票申请不能为空', trigger: 'blur' }
          ],
          openInvoice: [
            { required: true, message: '开出发票不能为空', trigger: 'blur' }
          ],
          serialNumberSet: [
            { required: true, message: '序号(结算)不能为空', trigger: 'blur' }
          ],
          settlemnentDataMonth: [
            { required: true, message: '结算日期(月份)不能为空', trigger: 'blur' }
          ],
          ladderPrice: [
            { required: true, message: '阶梯价格不能为空', trigger: 'blur' }
          ],
          sendInvoice: [
            { required: true, message: '寄出发票不能为空', trigger: 'blur' }
          ],
          receivedPay: [
            { required: true, message: '收到款项不能为空', trigger: 'blur' }
          ],
          profits: [
            { required: true, message: '利润不能为空', trigger: 'blur' }
          ],
          spareMoney: [
            { required: true, message: '余款不能为空', trigger: 'blur' }
          ],
          differenceMoney: [
            { required: true, message: '差额不能为空', trigger: 'blur' }
          ],
          markTxt: [
            { required: true, message: '备注不能为空', trigger: 'blur' }
          ],
          operNam: [
            { required: true, message: '添加人不能为空', trigger: 'blur' }
          ],
          recordTim: [
            { required: true, message: '添加人时间不能为空', trigger: 'blur' }
          ],
          updateNam: [
            { required: true, message: '修改人不能为空', trigger: 'blur' }
          ],
          updateTim: [
            { required: true, message: '修改时间不能为空', trigger: 'blur' }
          ],
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.fId = id || 0;
        this.visible = true;
        this.canSubmit = true;
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.fId) {
            getObj(this.dataForm.fId).then(response => {
                this.dataForm = response.data.data
            })
          }
        })
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
