<template>
  <el-dialog
    :title="!dataForm.fid ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible"
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="120px"
    >
      <el-form-item label="货类编码:" prop="cargoKindCod" hidden="hidden">
        <el-input
          v-model="dataForm.cargoKindCod"
          placeholder="货类编码"
        ></el-input>
      </el-form-item>
      <el-form-item label="货类名称" prop="cargoNam">
        <el-select
          v-model="dataForm.cargoKindNam"
          placeholder="请选择"
          filterable
          @change="cargoChange"
          style="width: 100%"
        >
          <el-option
            v-for="(item, index) in cargoNamOptions"
            :key="index"
            :label="item.cargoKindNam"
            :value="item.cargoKindNam"
          >
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="货物编码:" prop="cargoCod">
        <el-input v-model="dataForm.cargoCod" placeholder="货物编码"></el-input>
      </el-form-item>
      <el-form-item label="货物名称:" prop="cargoNam">
        <el-input v-model="dataForm.cargoNam" placeholder="货物名称"></el-input>
      </el-form-item>
      <el-form-item label="备注:" prop="markTxt">
        <el-input v-model="dataForm.markTxt" placeholder="备注"></el-input>
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
import { getObj, addObj, putObj, getContractObjectInfoAll } from '@/api/baoshuisystem/contractobject'
import { all } from '@/api/baoshuisystem/goodscategory'
export default {
  data() {
    return {
      visible: false,
      canSubmit: false,
      cargoNamOptions: [],
      dataForm: {
        fid: 0,
        cargoKindCod: '',
        cargoKindNam: '',
        cargoCod: '',
        cargoNam: '',
        cargoNamCustoms: '',
        remark: '',
        operNam: '',
        recordTim: '',
        updateNam: '',
        updateTim: '',
        delNam: '',
        delTim: '',

      },
      dataRule: {
        // cargoKindCod: [
        //   { required: true, message: '合同标的编码不能为空', trigger: 'blur' }
        // ],
        cargoKindNam: [
          { required: true, message: '合同标的名称不能为空', trigger: 'blur' }
        ],
        cargoCod: [
          { required: true, message: '货物编码不能为空', trigger: 'blur' }
        ],
        cargoNam: [
          { required: true, message: '货物名称不能为空', trigger: 'blur' }
        ],
        cargoNamCustoms: [
          { required: true, message: '货物名称（海关）不能为空', trigger: 'blur' }
        ],
        markTxt: [
          { required: false, message: '备注不能为空', trigger: 'blur' }
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
    init(id) {
      this.dataForm.fid = id || 0;
      this.visible = true;
      this.canSubmit = true;
      all().then(data => {
        this.cargoNamOptions = data.data.data
      })
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.fid) {
          getObj(this.dataForm.fid).then(response => {
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
          if (this.dataForm.fid) {
            console.log(this.dataForm)
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


    },
    cargoChange: function (value) {
      var obj = this.cargoNamOptions.find((item) => {//这里的userList就是上面遍历的数据源
        return item.cargoKindNam === value;//筛选出匹配数据
      });
      this.dataForm.cargoKindCod = obj.cargoKindCod
      this.dataForm.cargoNam = obj.cargoNam
    },
  }
}
</script>
