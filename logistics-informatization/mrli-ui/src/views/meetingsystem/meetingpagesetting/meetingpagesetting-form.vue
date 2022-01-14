<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="phoneUrl" prop="phoneUrl">
      <el-input v-model="dataForm.phoneUrl" placeholder="phoneUrl"></el-input>
    </el-form-item>
    <el-form-item label="headFlg" prop="headFlg">
      <el-input v-model="dataForm.headFlg" placeholder="headFlg"></el-input>
    </el-form-item>
    <el-form-item label="外键id" prop="meetingId">
      <el-input v-model="dataForm.meetingId" placeholder="外键id"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" v-if="canSubmit">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
    import {getObj, addObj, putObj} from '@/api/meetingsystem/meetingpagesetting'

    export default {
    data () {
      return {
        visible: false,
        canSubmit: false,
        dataForm: {
          id: 0,
          phoneUrl: '',
          headFlg: '',
          meetingId: ''
        },
        dataRule: {
          phoneUrl: [
            { required: true, message: 'phoneUrl不能为空', trigger: 'blur' }
          ],
          headFlg: [
            { required: true, message: 'headFlg不能为空', trigger: 'blur' }
          ],
          meetingId: [
            { required: true, message: '外键id不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0;
        this.visible = true;
        this.canSubmit = true;
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            getObj(this.dataForm.id).then(response => {
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
            if (this.dataForm.id) {
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
