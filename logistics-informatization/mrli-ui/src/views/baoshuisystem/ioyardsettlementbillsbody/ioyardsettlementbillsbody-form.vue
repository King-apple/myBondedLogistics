<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="头表主键" prop="fFid">
      <el-input v-model="dataForm.fFid" placeholder="头表主键"></el-input>
    </el-form-item>
    <el-form-item label="结算单编号" prop="billCod">
      <el-input v-model="dataForm.billCod" placeholder="结算单编号"></el-input>
    </el-form-item>
    <el-form-item label="入库日期" prop="inYardDat">
      <el-input v-model="dataForm.inYardDat" placeholder="入库日期"></el-input>
    </el-form-item>
    <el-form-item label="入库结算数量" prop="inSettleWgt">
      <el-input v-model="dataForm.inSettleWgt" placeholder="入库结算数量"></el-input>
    </el-form-item>
    <el-form-item label="出库日期" prop="outYardDat">
      <el-input v-model="dataForm.outYardDat" placeholder="出库日期"></el-input>
    </el-form-item>
    <el-form-item label="出库结算数量" prop="outSettleWgt">
      <el-input v-model="dataForm.outSettleWgt" placeholder="出库结算数量"></el-input>
    </el-form-item>
    <el-form-item label="天数" prop="dayNum">
      <el-input v-model="dataForm.dayNum" placeholder="天数"></el-input>
    </el-form-item>
    <el-form-item label="吨天数" prop="tonDayNum">
      <el-input v-model="dataForm.tonDayNum" placeholder="吨天数"></el-input>
    </el-form-item>
    <el-form-item label="出入库管理费费率" prop="managementRat">
      <el-input v-model="dataForm.managementRat" placeholder="出入库管理费费率"></el-input>
    </el-form-item>
    <el-form-item label="出入库管理费" prop="managementMoney">
      <el-input v-model="dataForm.managementMoney" placeholder="出入库管理费"></el-input>
    </el-form-item>
    <el-form-item label="保税仓储费费率" prop="storageRat">
      <el-input v-model="dataForm.storageRat" placeholder="保税仓储费费率"></el-input>
    </el-form-item>
    <el-form-item label="保税仓储费" prop="storageMoney">
      <el-input v-model="dataForm.storageMoney" placeholder="保税仓储费"></el-input>
    </el-form-item>
    <el-form-item label="合计" prop="totalMoney">
      <el-input v-model="dataForm.totalMoney" placeholder="合计"></el-input>
    </el-form-item>
    <el-form-item label="备注" prop="markTxt">
      <el-input v-model="dataForm.markTxt" placeholder="备注"></el-input>
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
    import {getObj, addObj, putObj} from '@/api/baoshuisystem/ioyardsettlementbillsbody'

    export default {
    data () {
      return {
        visible: false,
        canSubmit: false,
        dataForm: {
          fId: 0,
          fFid: '',
          billCod: '',
          inYardDat: '',
          inSettleWgt: '',
          outYardDat: '',
          outSettleWgt: '',
          dayNum: '',
          tonDayNum: '',
          managementRat: '',
          managementMoney: '',
          storageRat: '',
          storageMoney: '',
          totalMoney: '',
          markTxt: '',
          operNam: '',
          recordTim: '',
          updateNam: '',
          updateTim: '',
          delNam: '',
          delTim: ''
        },
        dataRule: {
          fFid: [
            { required: true, message: '头表主键不能为空', trigger: 'blur' }
          ],
          billCod: [
            { required: true, message: '结算单编号不能为空', trigger: 'blur' }
          ],
          inYardDat: [
            { required: true, message: '入库日期不能为空', trigger: 'blur' }
          ],
          inSettleWgt: [
            { required: true, message: '入库结算数量不能为空', trigger: 'blur' }
          ],
          outYardDat: [
            { required: true, message: '出库日期不能为空', trigger: 'blur' }
          ],
          outSettleWgt: [
            { required: true, message: '出库结算数量不能为空', trigger: 'blur' }
          ],
          dayNum: [
            { required: true, message: '天数不能为空', trigger: 'blur' }
          ],
          tonDayNum: [
            { required: true, message: '吨天数不能为空', trigger: 'blur' }
          ],
          managementRat: [
            { required: true, message: '出入库管理费费率不能为空', trigger: 'blur' }
          ],
          managementMoney: [
            { required: true, message: '出入库管理费不能为空', trigger: 'blur' }
          ],
          storageRat: [
            { required: true, message: '保税仓储费费率不能为空', trigger: 'blur' }
          ],
          storageMoney: [
            { required: true, message: '保税仓储费不能为空', trigger: 'blur' }
          ],
          totalMoney: [
            { required: true, message: '合计不能为空', trigger: 'blur' }
          ],
          markTxt: [
            { required: true, message: '备注不能为空', trigger: 'blur' }
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
