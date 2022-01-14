<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="货名" prop="cargoNam">
      <el-input v-model="dataForm.cargoNam" placeholder="货名"></el-input>
    </el-form-item>
    <el-form-item label="每车件数" prop="piecesNum">
      <el-input v-model="dataForm.piecesNum" placeholder="每车件数"></el-input>
    </el-form-item>
    <el-form-item label="车牌号" prop="carNo">
      <el-input v-model="dataForm.carNo" placeholder="车牌号"></el-input>
    </el-form-item>
    <el-form-item label="条码编号" prop="outportNo">
      <el-input v-model="dataForm.outportNo" placeholder="条码编号"></el-input>
    </el-form-item>
    <el-form-item label="提货单位" prop="agentNam">
      <el-input v-model="dataForm.agentNam" placeholder="提货单位"></el-input>
    </el-form-item>
    <el-form-item label="开票人" prop="operNam">
      <el-input v-model="dataForm.operNam" placeholder="开票人"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" v-if="canSubmit">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
    import {getObj, addObj, putObj} from '@/api/baoshuisystem/outportcertificate'

    export default {
    data () {
      return {
        visible: false,
        canSubmit: false,
        dataForm: {
          recordDte: 0,
          cargoNam: '',
          piecesNum: '',
          carNo: '',
          outportNo: '',
          agentNam: '',
          operNam: ''
        },
        dataRule: {
          cargoNam: [
            { required: true, message: '货名不能为空', trigger: 'blur' }
          ],
          piecesNum: [
            { required: true, message: '每车件数不能为空', trigger: 'blur' }
          ],
          carNo: [
            { required: true, message: '车牌号不能为空', trigger: 'blur' }
          ],
          outportNo: [
            { required: true, message: '条码编号不能为空', trigger: 'blur' }
          ],
          agentNam: [
            { required: true, message: '提货单位不能为空', trigger: 'blur' }
          ],
          operNam: [
            { required: true, message: '开票人不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.recordDte = id || 0;
        this.visible = true;
        this.canSubmit = true;
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.recordDte) {
            getObj(this.dataForm.recordDte).then(response => {
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
            if (this.dataForm.recordDte) {
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
