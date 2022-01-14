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
      <el-form-item label="存放位置" prop="location">
        <el-input v-model="dataForm.location" placeholder="存放位置"></el-input>
      </el-form-item>

      <el-form-item label="备注" prop="markTxt">
        <el-input
          type="textarea"
          v-model="dataForm.markTxt"
          placeholder="备注"
        ></el-input>
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
import { getObj, addObj, putObj } from '@/api/baoshuisystem/storagelocation'

export default {
  data() {
    return {
      visible: false,
      canSubmit: false,
      dataForm: {
        fId: 0,
        location: '',
        operator: '',
        recordTim: '',
        markTxt: '',
        updateNam: '',
        updateTim: '',
        delNam: '',
        delTim: ''
      },
      dataRule: {
        location: [
          { required: true, message: '存放位置不能为空', trigger: 'blur' }
        ]

      }
    }
  },
  methods: {
    init(id) {
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
    }
  }
}
</script>
