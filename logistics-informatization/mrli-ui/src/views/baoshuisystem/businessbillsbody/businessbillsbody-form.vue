<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
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
      <el-form-item label="项目" prop="itemNam">
        <el-input v-model="dataForm.itemNam" placeholder="项目"></el-input>
      </el-form-item>
      <el-form-item label="费用名称" prop="feeNam">
        <el-input v-model="dataForm.feeNam" placeholder="费用名称"></el-input>
      </el-form-item>
      <el-form-item label="费率单位" prop="feeUnit">
        <el-input v-model="dataForm.feeUnit" placeholder="费率单位"></el-input>
      </el-form-item>
      <el-form-item label="费率值" prop="feeRat">
        <el-input v-model="dataForm.feeRat" placeholder="费率值"></el-input>
      </el-form-item>
      <el-form-item label="应收应付款" prop="dueMoney">
        <el-input
          v-model="dataForm.dueMoney"
          placeholder="应收应付款"
        ></el-input>
      </el-form-item>
      <el-form-item label="预收预付款" prop="advinceMoney">
        <el-input
          v-model="dataForm.advinceMoney"
          placeholder="预收预付款"
        ></el-input>
      </el-form-item>
      <el-form-item label="金额" prop="money">
        <el-input v-model="dataForm.money" placeholder="金额"></el-input>
      </el-form-item>
      <el-form-item label="税额" prop="taxMoney">
        <el-input v-model="dataForm.taxMoney" placeholder="税额"></el-input>
      </el-form-item>
      <el-form-item label="价税合计" prop="totalMoney">
        <el-input
          v-model="dataForm.totalMoney"
          placeholder="价税合计"
        ></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="markTxt">
        <el-input v-model="dataForm.markTxt" placeholder="备注"></el-input>
      </el-form-item>
      <el-form-item label="添加人" prop="operNam">
        <el-input v-model="dataForm.operNam" placeholder="添加人"></el-input>
      </el-form-item>
      <el-form-item label="添加时间" prop="recordTim">
        <el-input
          v-model="dataForm.recordTim"
          placeholder="添加时间"
        ></el-input>
      </el-form-item>
      <el-form-item label="修改人" prop="updateNam">
        <el-input v-model="dataForm.updateNam" placeholder="修改人"></el-input>
      </el-form-item>
      <el-form-item label="修改时间" prop="updateTim">
        <el-input
          v-model="dataForm.updateTim"
          placeholder="修改时间"
        ></el-input>
      </el-form-item>
      <el-form-item label="删除人" prop="delNam">
        <el-input v-model="dataForm.delNam" placeholder="删除人"></el-input>
      </el-form-item>
      <el-form-item label="删除时间" prop="delTim">
        <el-input v-model="dataForm.delTim" placeholder="删除时间"></el-input>
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
import { getBody, addObj, putList } from '@/api/baoshuisystem/businessbillsbody'

export default {
  data() {
    return {
      visible: false,
      canSubmit: false,
      dataForm: {
        fId: 0,
        hFid: '',
        billCod: '',
        itemNam: '',
        feeNam: '',
        feeUnit: '',
        feeRat: '',
        dueMoney: '',
        advinceMoney: '',
        money: '',
        taxMoney: '',
        totalMoney: '',
        markTxt: '',
        operNam: '',
        recordTim: '',
        updateNam: '',
        updateTim: '',
        delNam: '',
        delTim: ''
      },
      // dataRule: {
      //   hFid: [
      //     { required: true, message: '头表主键不能为空', trigger: 'blur' }
      //   ],
      //   billCod: [
      //     { required: true, message: '付款编号不能为空', trigger: 'blur' }
      //   ],
      //   itemNam: [
      //     { required: true, message: '项目不能为空', trigger: 'blur' }
      //   ],
      //   feeNam: [
      //     { required: true, message: '费用名称不能为空', trigger: 'blur' }
      //   ],
      //   feeUnit: [
      //     { required: true, message: '费率单位不能为空', trigger: 'blur' }
      //   ],
      //   feeRat: [
      //     { required: true, message: '费率值不能为空', trigger: 'blur' }
      //   ],
      //   dueMoney: [
      //     { required: true, message: '应收应付款不能为空', trigger: 'blur' }
      //   ],
      //   advinceMoney: [
      //     { required: true, message: '预收预付款不能为空', trigger: 'blur' }
      //   ],
      //   money: [
      //     { required: true, message: '金额不能为空', trigger: 'blur' }
      //   ],
      //   taxMoney: [
      //     { required: true, message: '税额不能为空', trigger: 'blur' }
      //   ],
      //   totalMoney: [
      //     { required: true, message: '价税合计不能为空', trigger: 'blur' }
      //   ],
      //   markTxt: [
      //     { required: true, message: '备注不能为空', trigger: 'blur' }
      //   ],
      //   operNam: [
      //     { required: true, message: '添加人不能为空', trigger: 'blur' }
      //   ],
      //   recordTim: [
      //     { required: true, message: '添加时间不能为空', trigger: 'blur' }
      //   ],
      //   updateNam: [
      //     { required: true, message: '修改人不能为空', trigger: 'blur' }
      //   ],
      //   updateTim: [
      //     { required: true, message: '修改时间不能为空', trigger: 'blur' }
      //   ],
      //   delNam: [
      //     { required: true, message: '删除人不能为空', trigger: 'blur' }
      //   ],
      //   delTim: [
      //     { required: true, message: '删除时间不能为空', trigger: 'blur' }
      //   ]
      // }
    }
  },
  methods: {
    init(id) {
      this.dataForm.fId = id.fId || 0;
      this.visible = true;
      this.canSubmit = true;
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.fId) {
          getBody(this.dataForm.fId).then(response => {
            this.dataForm = response.data.data
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
            putList(this.dataForm).then(data => {
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
