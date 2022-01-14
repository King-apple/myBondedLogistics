<template>
  <el-dialog
    :title="!dataForm.fId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible"
    width="60%"
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="80px"
    >
      <el-row>
        <el-col :span="8">
          <el-form-item label="单据类型" prop="billType">
            <el-input
              v-model="dataForm.billType"
              :readonly="true"
              style="width: 100%"
              placeholder="单据类型"
              class="blodClass"
            ></el-input>
            <!-- <el-select
              v-model="dataForm.billType"
              :readonly="true"
              placeholder="请选择单据类型:"
              filterable
              style="width: 100%"
            >
              <el-option
                v-for="item in statementList"
                :key="item.id"
                :label="item.remarks"
                :value="item.remarks"
                @click.native="getItem(item)"
              >
              </el-option>
            </el-select> -->
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="单据编号" prop="billCod">
            <el-input
              v-model="dataForm.billCod"
              :readonly="true"
              style="width: 100%"
              placeholder="单据编号"
              class="blodClass"
            ></el-input>
            <!-- <el-select
              :readonly="flag"
              v-model="dataForm.billCod"
              placeholder="请选择单据类型:"
              filterable
              style="width: 100%"
            >
              <el-option
                v-for="item in billCodList"
                :key="item.fId"
                :label="item.billCod"
                :value="item.billCod"
                @click.native="getBillCod(item)"
              >
              </el-option>
            </el-select> -->
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="日期" prop="billDat">
            <el-date-picker
              v-model="dataForm.billDat"
              type="date"
              placeholder="日期"
              value-format="yyyy-MM-dd"
              style="width: 100%"
              class="blodClass"
              :readonly="true"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="合同编号" prop="contNo">
            <el-input
              v-model="dataForm.contNo"
              :readonly="true"
              style="width: 100%"
              placeholder="合同编号"
              class="blodClass"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="承办单位" prop="firstNam">
            <el-input
              v-model="dataForm.firstNam"
              :readonly="true"
              placeholder="承办单位"
              style="width: 100%"
              class="blodClass"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="单位代码" prop="firstCod">
            <el-input
              v-model="dataForm.firstCod"
              :readonly="true"
              style="width: 100%"
              placeholder="单位代码"
              class="blodClass"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="部门" prop="dept">
            <el-input
              v-model="dataForm.dept"
              :readonly="true"
              placeholder="部门"
              style="width: 100%"
              class="blodClass"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="客户代码" prop="secondCod">
            <el-input
              v-model="dataForm.secondCod"
              :readonly="true"
              style="width: 100%"
              placeholder="客户代码"
              class="blodClass"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="客户名称" prop="secondNam">
            <el-input
              v-model="dataForm.secondNam"
              :readonly="true"
              style="width: 100%"
              placeholder="客户名称"
              class="blodClass"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="8">
          <el-form-item label="船名" prop="shipNam">
            <el-input
              v-model="dataForm.shipNam"
              :readonly="true"
              style="width: 100%"
              placeholder="船名"
              class="blodClass"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="业务种类" prop="businessType">
            <el-input
              v-model="dataForm.businessType"
              :readonly="true"
              style="width: 100%"
              placeholder="业务种类"
              class="blodClass"
            ></el-input>
            <!-- <el-select
              clearable
              v-model="dataForm.businessType"
              placeholder="业务种类:"
              :readonly="true"
              filterable
            >
              <el-option
                v-for="item in bussinessList"
                :key="item.id"
                :label="item.remarks"
                :value="item.remarks"
              >
              </el-option>
            </el-select> -->
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="货物代码" prop="cargoCod">
            <el-input
              v-model="dataForm.cargoCod"
              :readonly="true"
              style="width: 100%"
              placeholder="货物代码"
              class="blodClass"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="货物名称" prop="cargoNam">
            <el-input
              v-model="dataForm.cargoNam"
              :readonly="true"
              style="width: 100%"
              placeholder="货物名称"
              class="blodClass"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="结算账户" prop="accountNam">
            <el-input
              v-model="dataForm.accountNam"
              :readonly="true"
              style="width: 100%"
              class="blodClass"
              placeholder="结算账户名称"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="计费数量" prop="settleWgt">
            <el-input
              v-model="dataForm.settleWgt"
              :readonly="true"
              style="width: 100%"
              class="blodClass"
              placeholder="计费数量"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="金额" prop="money">
            <el-input
              v-model="dataForm.money"
              :readonly="true"
              style="width: 100%"
              placeholder="金额(元)"
              class="blodClass"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="垫款金额" prop="advinceMoney">
            <el-input
              v-model="dataForm.advinceMoney"
              :readonly="true"
              style="width: 100%"
              placeholder="垫款金额(元)"
              class="blodClass"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="备注" prop="markTxt">
            <el-input
              v-model="dataForm.markTxt"
              :readonly="true"
              style="width: 100%"
              class="blodClass"
              placeholder="备注"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item
        label="附件"
        prop="urlFile"
        v-loading="fileLoading"
        element-loading-text="文件上传中"
        element-loading-spinner="el-icon-loading"
      >
        <!--<el-upload
          :headers="headers"
          :show-file-list="false"
          :on-success="uploadSuccess"
          class="avatar-uploader"
          action="/baoshuisystem/sys-file/upload"
          :on-progress="onProgress"
        >
          <el-button size="small" type="primary">点击上传</el-button>
        </el-upload>-->
        <ul id="example-1">
          <li v-for="(item, index) in fileOptions" :key="index">
            {{ item.original }}
            <i
              class="el-icon-download"
              style="
                float: right;
                left: 40%;
                height: 32px;
                line-height: 32px;
                position: absolute;
              "
              @click="downloadFile(index)"
            ></i>
          </li>
        </ul>
      </el-form-item>

      <el-row>
        <!--<el-col :span="24">
      <el-form-item label="审批意见" prop="opinion">
        <el-input v-model="dataForm.opinion" type="textarea" :rows="3" placeholder="审批意见"></el-input>
      </el-form-item>
     </el-col>-->

        <el-col :span="24">
          <el-form-item label="审批意见说明" prop="opinionExplain">
            <el-input
              v-model="dataForm.opinionExplain"
              type="textarea"
              :rows="3"
              placeholder="审批意见说明"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <span slot="footer" class="dialog-footer" v-if="checkPermission">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit('1')" v-if="canSubmit"
        >审核通过</el-button
      >
      <el-button type="danger" @click="dataFormSubmit('2')" v-if="canSubmit"
        >审核驳回</el-button
      >
    </span>
  </el-dialog>
</template>

<script>
import { getObj, addObj, putObj, getAllBills, getStatement, getBussiness, setAudit } from '@/api/baoshuisystem/billapprove'
import { getFileInfo, handleDown } from '@/api/baoshuisystem/contract'
import store from "@/store";
export default {
  data() {
    return {
      headers: {
        Authorization: "Bearer " + store.getters.access_token,
      },
      flag: true,
      statementList: [],
      billCodList: [],
      fileOptions: [],
      visible: false,
      canSubmit: false,
      fileLoading: false,
      checkPermission: false,
      bussinessList: [],
      dataForm: {
        fId: 0,
        fFid: '',
        billCod: '',
        billType: '',
        billDat: '',
        firstNam: '',
        firstCod: '',
        dept: '',
        operator: '',
        secondCod: '',
        secondNam: '',
        contNo: '',
        shipNam: '',
        businessType: '',
        cargoCod: '',
        cargoNam: '',
        accountNam: '',
        settleWgt: '',
        money: '',
        advinceMoney: '',
        markTxt: '',
        operNam: '',
        recordTim: '',
        updateNam: '',
        updateTim: '',
        delNam: '',
        delTim: ''
      },
      dataRule: {
        // fFid: [
        //   { required: true, message: '单据主键不能为空', trigger: 'blur' }
        // ],
        // billCod: [
        //   { required: true, message: '单据编号不能为空', trigger: 'blur' }
        // ],
        billType: [
          { required: true, message: '单据类型不能为空', trigger: 'blur' }
        ],
        billDat: [
          { required: true, message: '日期不能为空', trigger: 'blur' }
        ],
        firstNam: [
          { required: true, message: '承办单位不能为空', trigger: 'blur' }
        ],
        // firstCod: [
        //   { required: true, message: '承办单位代码不能为空', trigger: 'blur' }
        // ],
        dept: [
          { required: true, message: '部门不能为空', trigger: 'blur' }
        ],
        // operator: [
        //   { required: true, message: '经办人不能为空', trigger: 'blur' }
        // ],
        // secondCod: [
        //   { required: true, message: '合同客户代码不能为空', trigger: 'blur' }
        // ],
        secondNam: [
          { required: true, message: '合同客户名称不能为空', trigger: 'blur' }
        ],
        contNo: [
          { required: true, message: '合同编号不能为空', trigger: 'blur' }
        ],
        // shipNam: [
        //   { required: true, message: '船名不能为空', trigger: 'blur' }
        // ],
        businessType: [
          { required: true, message: '业务种类不能为空', trigger: 'blur' }
        ],
        // cargoCod: [
        //   { required: true, message: '货物代码不能为空', trigger: 'blur' }
        // ],
        cargoNam: [
          { required: true, message: '货物名称不能为空', trigger: 'blur' }
        ],
        accountNam: [
          { required: true, message: '结算账户名称不能为空', trigger: 'blur' }
        ],
        settleWgt: [
          { required: true, message: '计费数量不能为空', trigger: 'blur' }
        ],
        money: [
          { required: true, message: '金额（元）不能为空', trigger: 'blur' }
        ],
        // advinceMoney: [
        //   { required: true, message: '垫款金额（元）不能为空', trigger: 'blur' }
        // ],
        // markTxt: [
        //   { required: true, message: '备注不能为空', trigger: 'blur' }
        // ],
        // operNam: [
        //   { required: true, message: '添加人不能为空', trigger: 'blur' }
        // ],
        // recordTim: [
        //   { required: true, message: '添加时间不能为空', trigger: 'blur' }
        // ],
        // updateNam: [
        //   { required: true, message: '修改人不能为空', trigger: 'blur' }
        // ],
        // updateTim: [
        //   { required: true, message: '修改时间不能为空', trigger: 'blur' }
        // ],
        // delNam: [
        //   { required: true, message: '删除人不能为空', trigger: 'blur' }
        // ],
        // delTim: [
        //   { required: true, message: '删除时间不能为空', trigger: 'blur' }
        // ]
      }
    }
  },
  methods: {
    getBillCod(item) {
      if (item.billKnd == "付款单" || item.billKnd == "业务结算单") {
        //承办单位
        this.dataForm.fFid = item.fId
        this.dataForm.firstNam = item.firstNam
        //承办单位代码
        this.dataForm.firstCod = item.firstCod
        //货物代码
        this.dataForm.cargoCod = item.cargoCod
        //承办单位（部门）
        this.dataForm.dept = item.dept
        //合同客户名称
        this.dataForm.secondNam = item.secondNam
        //合同客户代码
        this.dataForm.secondCod = item.secondCod
        //合同编号
        this.dataForm.contNo = item.contCod
        //船名
        this.dataForm.shipNam = item.shipNam
        //货物名称
        this.dataForm.cargoNam = item.cargoNam
        //计费数量 
        this.dataForm.settleWgt = item.feeQuantity

        //金额

        //垫款金额（元）
      } else {
        //承办单位
        this.dataForm.fFid = item.fId
        this.dataForm.firstNam = item.firstNam
        //承办单位代码
        this.dataForm.firstCod = item.firstCod
        //货物代码
        this.dataForm.cargoCod = item.cargoCod
        //合同客户名称
        this.dataForm.secondNam = item.secondNam
        //合同客户代码
        this.dataForm.secondCod = item.secondCod
        //合同编号
        this.dataForm.contNo = item.contNo
        //船名
        this.dataForm.shipNam = item.shipNam
        //货物名称
        this.dataForm.cargoNam = item.cargoNam
        //计费数量 
        this.dataForm.settleWgt = item.settleWgt
      }

      console.log("imt222e", item)
    },
    getItem(item) {
      if ("业务结算单" == item.remarks) {
        this.flag = false
        this.$set(this.dataForm, "billCod", "")
        getAllBills().then(res => {
          this.billCodList = res.data.data.angle
        })
      }
      if ("付款单" == item.remarks) {
        this.flag = false
        this.$set(this.dataForm, "billCod", "")
        getAllBills().then(res => {
          this.billCodList = res.data.data.brixton
        })
      }
      if ("出入库结算单" == item.remarks) {
        this.flag = false
        this.$set(this.dataForm, "billCod", "")
        getAllBills().then(res => {
          this.billCodList = res.data.data.camden
        })
      }
      if ("收款通知单" == item.remarks) {
        this.$set(this.dataForm, "billCod", "")
        this.flag = true
      }
    },
    async getAllBillsInfo() {
      await getAllBills().then(res => {
        this.billCodList = res.data.data.dalston
      })
    },
    getStatementInfo() {
      getStatement().then(res => {
        this.statementList = res.data.data
      })
      getBussiness().then(res => {
        this.bussinessList = res.data.data
      })
    },
    init(id, approvalProcessSetFid, checkPermission) {
      this.checkPermission = checkPermission
      this.getAllBillsInfo();
      this.getStatementInfo();
      this.dataForm.fId = id || 0;


      this.visible = true;
      this.canSubmit = true;
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.fId) {
          getObj(this.dataForm.fId).then(response => {
            this.dataForm = response.data.data
            this.dataForm.approvalProcessSetFid = approvalProcessSetFid
            let map = { tableFid: this.dataForm.fId, tableName: 'billapprove' }
            getFileInfo(map).then(resp => {
              this.fileOptions = resp.data.data
            })

          })
        }
      })
    },
    // 表单提交
    dataFormSubmit(flg) {
      console.log(flg)
      console.log(this.dataForm)
      this.dataForm.flg = flg
      console.log(this.dataForm)
      let flgName = '审核通过'
      if (flg == "2") {
        flgName = '审核驳回'
      }


      //提示信息
      this.$confirm('确定要' + flgName + '吗, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        setAudit(this.dataForm).then((res) => {
          console.log(res)
          this.$notify.success('修改成功')
          this.visible = false
          this.$emit('refreshDataList')
        }).catch(() => {
          this.canSubmit = true;
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        });
      });






      //      this.$refs['dataForm'].validate((valid) => {
      //        if(this.dataForm.billType != "收款通知单"){
      //          if ((this.dataForm.billCod == '' || this.dataForm.billCod == undefined)) {
      //            this.$message.error('请选择编号')
      //            return;
      //        }
      //      }
      //        if (valid) {
      //          this.canSubmit = false;
      //          if (this.dataForm.fId) {
      //              putObj(this.dataForm).then(data => {
      //                  this.$notify.success('修改成功')
      //                  this.visible = false
      //                  this.$emit('refreshDataList')
      //              }).catch(() => {
      //                  this.canSubmit = true;
      //              });
      //          } else {
      //            
      //              addObj(this.dataForm).then(data => {
      //                  this.$notify.success('添加成功')
      //                  this.visible = false
      //                  this.$emit('refreshDataList')
      //              }).catch(() => {
      //                  this.canSubmit = true;
      //              });
      //          }
      //        }
      //      })
    },

    downloadFile: function (index) {
      console.log(this.fileOptions)
      var filename = this.fileOptions[index].fileName;
      var bucket = this.fileOptions[index].bucketName;
      handleDown(filename, bucket);
    },
  }
}
</script>
