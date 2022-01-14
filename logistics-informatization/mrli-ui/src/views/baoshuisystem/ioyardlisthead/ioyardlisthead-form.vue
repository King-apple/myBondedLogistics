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
      <el-form-item label="合同编号" prop="contNo">
        <el-select
          v-model="dataForm.contNo"
          style="width: 100%"
          filterable
          placeholder="请选择"
        >
          <el-option
            v-for="(item, index) in contractOptions"
            :key="index"
            :label="item.contNo"
            :value="item.contNo"
          >
          </el-option>
        </el-select>
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
import { getObj, addObj, putObj } from '@/api/baoshuisystem/ioyardlisthead'
import { getSelectContract, getEffectiveInfo } from '@/api/baoshuisystem/contract'

export default {
  data() {
    return {
      visible: false,
      canSubmit: false,
      contractOptions: [],
      dataForm: {
        fId: 0,
        ioyardListNam: '',
        ioyardListNo: '',
        contNo: '',
        listDat: '',
        operNam: '',
        recordTim: '',
        updateNam: '',
        updateTim: '',
        delNam: '',
        delTim: ''
      },
      dataRule: {
        ioyardListNam: [
          { required: true, message: '单据名称不能为空', trigger: 'blur' }
        ],
        ioyardListNo: [
          { required: true, message: '编号不能为空', trigger: 'blur' }
        ],
        contNo: [
          { required: true, message: '合同编号不能为空', trigger: 'blur' }
        ],
        listDat: [
          { required: true, message: '日期不能为空', trigger: 'blur' }
        ],
        operNam: [
          { required: true, message: '制表人不能为空', trigger: 'blur' }
        ],
        recordTim: [
          { required: true, message: '制表时间不能为空', trigger: 'blur' }
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
        } else {
          getEffectiveInfo().then(data => {
            this.contractOptions = data.data.data
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
            this.dataForm.inYardWay = null;
            this.dataForm.outYardWay = null;
            putObj(this.dataForm).then(data => {
              this.$notify.success('修改成功')
              this.visible = false
              this.$emit('refreshDataList')
            }).catch(() => {
              this.canSubmit = true;
            });
          } else {
            addObj(this.dataForm).then(data => {
              console.log(data.data.data.data)
              if (data.data.data.data != null) {
                if (data.data.data.data == 1) {
                  this.$notify.success("添加成功")
                } else if (data.data.data.data == 0) {
                  this.$notify.error("该计划没有出入库明细，请重新选择")
                }
              }

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
