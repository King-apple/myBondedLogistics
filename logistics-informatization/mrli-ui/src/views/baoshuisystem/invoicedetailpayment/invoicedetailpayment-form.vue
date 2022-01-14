<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="头表主键" prop="hFid">
      <el-input v-model="dataForm.hFid" placeholder="头表主键"></el-input>
    </el-form-item>
    <el-form-item label="缴费方式" prop="payment">
      <el-input v-model="dataForm.payment" placeholder="缴费方式"></el-input>
    </el-form-item>
    <el-form-item label="金额" prop="money">
      <el-input v-model="dataForm.money" placeholder="金额"></el-input>
    </el-form-item>
    <el-form-item label="添加人" prop="operNam">
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
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" v-if="canSubmit">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
    import {getObj, addObj, putObj} from '@/api/baoshuisystem/invoicedetailpayment'

    export default {
    data () {
      return {
        visible: false,
        canSubmit: false,
        dataForm: {
          fId: 0,
          hFid: '',
          payment: '',
          money: '',
          operNam: '',
          recordTim: '',
          updateNam: '',
          updateTim: '',
          delNam: '',
          delTim: ''
        },
        dataRule: {
          hFid: [
            { required: true, message: '头表主键不能为空', trigger: 'blur' }
          ],
          payment: [
            { required: true, message: '缴费方式不能为空', trigger: 'blur' }
          ],
          money: [
            { required: true, message: '金额不能为空', trigger: 'blur' }
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
