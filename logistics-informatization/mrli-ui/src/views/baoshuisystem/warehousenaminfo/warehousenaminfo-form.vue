<template>
  <el-dialog
    :title="!dataForm.fId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="入出库" prop="inOut">
      <el-select
        v-model="dataForm.inOut"
        placeholder="请选择"
        filterable
        clearable
        style="width: 100%"
      >
        <el-option
          v-for="(item, index) in inoutOptions"
          :key="index"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select> 
    </el-form-item>
    <el-form-item label="单据名称" prop="inoutYardNam">
      <el-input v-model="dataForm.inoutYardNam" placeholder="单据名称"></el-input>
    </el-form-item>
    <el-form-item label="仓库编号" prop="warehouseCod">
      <el-input v-model="dataForm.warehouseCod" placeholder="仓库编号"></el-input>
    </el-form-item>
    <el-form-item label="仓库名称" prop="warehouseNam">
      <el-input v-model="dataForm.warehouseNam" placeholder="仓库名称"></el-input>
    </el-form-item>
    <el-form-item label="电子账册号" prop="electronicAccountNum">
      <el-input v-model="dataForm.electronicAccountNum" placeholder="电子账册号"></el-input>
    </el-form-item>
    <el-form-item label="模板标识" prop="exportCod">
      <el-input v-model="dataForm.exportCod" placeholder="模板标识"></el-input>
    </el-form-item>
    <el-form-item label="模板名称" prop="exportNam">
      <el-input v-model="dataForm.exportNam" placeholder="模板名称"></el-input>
    </el-form-item>
    <el-form-item label="备注" prop="markTxt">
      <el-input v-model="dataForm.markTxt" placeholder="备注"></el-input>
    </el-form-item>
    
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" v-if="canSubmit">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
    import {getObj, addObj, putObj} from '@/api/baoshuisystem/warehousenaminfo'
    import {getInoutFlag} from '@/api/baoshuisystem/getdictdata'

    export default {
    data () {
      return {
        visible: false,
        canSubmit: false,
        inoutOptions: [],
        dataForm: {
          fId: 0,
          inOut: null,
          inoutYardNam: '',
          warehouseCod: '',
          warehouseNam: '',
          electronicAccountNum: '',
          exportCod: '',
          exportNam: '',
          markTxt: '',
          operNam: '',
          recordTim: '',
          updateNam: '',
          updateTim: '',
          delNam: '',
          delTim: ''
        },
        dataRule: {
          inoutYardNam: [
            { required: true, message: '单据名称不能为空', trigger: 'blur' }
          ],
          inOut: [
            { required: true, message: '入出库不能为空', trigger: 'blur' }
          ],
          warehouseCod: [
            { required: true, message: '仓库编号不能为空', trigger: 'blur' }
          ],
          warehouseNam: [
            { required: true, message: '仓库名称不能为空', trigger: 'blur' }
          ],
          electronicAccountNum: [
            { required: true, message: '电子账册号不能为空', trigger: 'blur' }
          ],
          exportCod: [
            { required: true, message: '模板标识不能为空', trigger: 'blur' }
          ],
          exportNam: [
            { required: true, message: '模板名称不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.fId = id || 0;
        this.visible = true;
        this.canSubmit = true;
        this.getInfo();
      },
      getInfo: async function () {
        getInoutFlag().then(data => {
          this.inoutOptions = data.data.data
        });
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
