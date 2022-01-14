<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="审批流程" prop="processNo">
      <el-input v-model="dataForm.processNo" placeholder="审批流程"></el-input>
    </el-form-item>
    <el-form-item label="审批序号" prop="orderBy">
      <el-input v-model="dataForm.orderBy" placeholder="审批序号"></el-input>
    </el-form-item>
    <el-form-item label="部门名称" prop="linkNam">
      <el-input v-model="dataForm.linkNam" placeholder="部门名称"></el-input>
    </el-form-item>
    <el-form-item label="角色" prop="roleNam">
      <el-input v-model="dataForm.roleNam" placeholder="角色"></el-input>
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
    <el-form-item label="删除人" prop="delNam">
      <el-input v-model="dataForm.delNam" placeholder="删除人"></el-input>
    </el-form-item>
    <el-form-item label="删除时间" prop="delTim">
      <el-input v-model="dataForm.delTim" placeholder="删除时间"></el-input>
    </el-form-item>
    <el-form-item label="是否按照用户名查询" prop="isListbyuser">
      <el-input v-model="dataForm.isListbyuser" placeholder="是否按照用户名查询"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" v-if="canSubmit">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
    import {getObj, addObj, putObj} from '@/api/baoshuisystem/approvalprocess'

    export default {
    data () {
      return {
        visible: false,
        canSubmit: false,
        dataForm: {
          fId: 0,
          processNo: '',
          orderBy: '',
          linkNam: '',
          roleNam: '',
          markTxt: '',
          operNam: '',
          recordTim: '',
          delNam: '',
          delTim: '',
          isListbyuser: ''
        },
        dataRule: {
          processNo: [
            { required: true, message: '审批流程不能为空', trigger: 'blur' }
          ],
          orderBy: [
            { required: true, message: '审批序号不能为空', trigger: 'blur' }
          ],
          linkNam: [
            { required: true, message: '部门名称不能为空', trigger: 'blur' }
          ],
          roleNam: [
            { required: true, message: '角色不能为空', trigger: 'blur' }
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
          delNam: [
            { required: true, message: '删除人不能为空', trigger: 'blur' }
          ],
          delTim: [
            { required: true, message: '删除时间不能为空', trigger: 'blur' }
          ],
          isListbyuser: [
            { required: true, message: '是否按照用户名查询不能为空', trigger: 'blur' }
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
