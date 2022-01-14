<template>
  <el-dialog
    title="查看"
    :close-on-click-modal="false" width="80%"
    :visible.sync="visible">
       <el-table
        :data="listAll"
        border
        >
        <el-table-column
          prop="inYardNo"
          header-align="center"
          align="center"
          width="140vw"
          show-overflow-tooltip
          label="入库单编号">
        </el-table-column>
        <el-table-column
          prop="inRecordTim"
          header-align="center"
          align="center"
          width="140vw"
          show-overflow-tooltip
          label="计划入库日期">
        </el-table-column>
        <el-table-column
          prop="outYardNo"
          header-align="center"
          align="center"
          width="140vw"
          show-overflow-tooltip
          label="出库单编号">
        </el-table-column>
        <el-table-column
          prop="outRecordTim"
          header-align="center"
          align="center"
          width="140vw"
          show-overflow-tooltip
          label="计划出库日期">
        </el-table-column>
        <el-table-column
          prop="ioyardListNo"
          header-align="center"
          align="center"
          width="140vw"
          show-overflow-tooltip
          label="出入库明细表编号">
        </el-table-column>
        <el-table-column
          prop="ioyardListNo"
          header-align="center"
          align="center"
          width="140vw"
          show-overflow-tooltip
          label="出入库明细表编号">
        </el-table-column>inYardDat
        <el-table-column
          prop="inYardDat"
          header-align="center"
          align="center"
          width="140vw"
          show-overflow-tooltip
          label="实际入库日期">
        </el-table-column>
        <el-table-column
          prop="inWeightWgt"
          header-align="center"
          align="center"
          width="140vw"
          show-overflow-tooltip
          label="实际入库吨数">
        </el-table-column>
        <el-table-column
          prop="outYardDat"
          header-align="center"
          align="center"
          width="140vw"
          show-overflow-tooltip
          label="实际出库日期">
        </el-table-column>
        <el-table-column
          prop="outWeightWgt"
          header-align="center"
          align="center"
          width="140vw"
          show-overflow-tooltip
          label="实际出库吨数">
          </el-table-column>
         <el-table-column
          prop="billDatCollection"
          header-align="center"
          align="center"
          width="140vw"
          show-overflow-tooltip
          label="收款时间">
          </el-table-column>
            <el-table-column
          prop="billCodCollection"
          header-align="center"
          align="center"
          width="140vw"
          show-overflow-tooltip
          label="收款单编号">
          </el-table-column>
          <el-table-column
          prop="moneyCollection"
          header-align="center"
          align="center"
          width="140vw"
          show-overflow-tooltip
          label="收款金额">
          </el-table-column>
          <el-table-column
          prop="billCodSettlement"
          header-align="center"
          align="center"
          width="140vw"
          show-overflow-tooltip
          label="结算单编号">
          </el-table-column>
          <el-table-column
          prop="billDatSettlement"
          header-align="center"
          align="center"
          width="140vw"
          show-overflow-tooltip
          label="结算单日期">
          </el-table-column>
          <el-table-column
          prop="moneyRebates"
          header-align="center"
          align="center"
          width="140vw"
          show-overflow-tooltip
          label="返款金额">
          </el-table-column>
          <!-- <el-table-column
          prop="balance"
          header-align="center"
          align="center"
          width="140vw"
          show-overflow-tooltip
          label="客户余额">
          </el-table-column>
          <el-table-column
          prop="profit"
          header-align="center"
          align="center"
          width="140vw"
          show-overflow-tooltip
          label="利润">
          </el-table-column> -->

        </el-table>


    <!-- <div >
      <div>
        <h5>计划出入库</h5>
        <div v-for="(i,index) in listAll" :key="index">
          {{i.inYardNo}} {{i.inRecordTim}} {{i.outYardNo}} {{i.outRecordTim}}
        </div>
      </div>





    </div> -->

    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" v-if="canSubmit">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
    import {getListALL} from '@/api/baoshuisystem/settlementcontractledger'

    export default {
    data () {
      return {
        visible: false,
        canSubmit: false,
        listAll:[],
        dataForm: {
          fId: 0,
          contractType: '',
          dept: '',
          contNo: '',
          secondNam: '',
          shipNam: '',
          cargoKindNam: '',
          cargoNam: '',
          cargoNum: '',
          cargoFee: '',
          settlementBasis: '',
          startDte: '',
          endDte: '',
          operator: '',
          feeRat: '',
          feeNam: '',
          inYardNo: '',
          outYardNo: '',
          inRecordTim: '',
          outRecordTim: '',
          inRoughWet: '',
          outRoughWet: '',
          ioyardListNo: '',
          inYardDat: '',
          outYardDat: '',
          inWeightWgt: '',
          outWeightWgt: '',
          billDatCollection: '',
          billDatSettlement: '',
          billCodCollection: '',
          billCodSettlement: '',
          moneyCollection: '',
          moneySettlement: '',
          moneyRebates: '',
          balance: '',
          profit: ''
        },
        dataRule: {
          contractType: [
            { required: true, message: '合同类型不能为空', trigger: 'blur' }
          ],
          dept: [
            { required: true, message: '部门不能为空', trigger: 'blur' }
          ],
          contNo: [
            { required: true, message: '合同编号不能为空', trigger: 'blur' }
          ],
          secondNam: [
            { required: true, message: '对方签约单位不能为空', trigger: 'blur' }
          ],
          shipNam: [
            { required: true, message: '船名不能为空', trigger: 'blur' }
          ],
          cargoKindNam: [
            { required: true, message: '货类名称不能为空', trigger: 'blur' }
          ],
          cargoNam: [
            { required: true, message: '合同标的不能为空', trigger: 'blur' }
          ],
          cargoNum: [
            { required: true, message: '合同标的数量不能为空', trigger: 'blur' }
          ],
          cargoFee: [
            { required: true, message: '标的额不能为空', trigger: 'blur' }
          ],
          settlementBasis: [
            { required: true, message: '结算依据不能为空', trigger: 'blur' }
          ],
          startDte: [
            { required: true, message: '合同开始日期不能为空', trigger: 'blur' }
          ],
          endDte: [
            { required: true, message: '合同结束日期不能为空', trigger: 'blur' }
          ],
          operator: [
            { required: true, message: '经办人不能为空', trigger: 'blur' }
          ],
          feeRat: [
            { required: true, message: '费率值不能为空', trigger: 'blur' }
          ],
          feeNam: [
            { required: true, message: '费目名称不能为空', trigger: 'blur' }
          ],
          inYardNo: [
            { required: true, message: '入库单据编号不能为空', trigger: 'blur' }
          ],
          outYardNo: [
            { required: true, message: '出库单据编号不能为空', trigger: 'blur' }
          ],
          inRecordTim: [
            { required: true, message: '计划入库日期不能为空', trigger: 'blur' }
          ],
          outRecordTim: [
            { required: true, message: '计划出库日期不能为空', trigger: 'blur' }
          ],
          inRoughWet: [
            { required: true, message: '计划入库吨数（毛重）不能为空', trigger: 'blur' }
          ],
          outRoughWet: [
            { required: true, message: '计划出库吨数（毛重）不能为空', trigger: 'blur' }
          ],
          ioyardListNo: [
            { required: true, message: '编号不能为空', trigger: 'blur' }
          ],
          inYardDat: [
            { required: true, message: '入库日期不能为空', trigger: 'blur' }
          ],
          outYardDat: [
            { required: true, message: '出库日期不能为空', trigger: 'blur' }
          ],
          inWeightWgt: [
            { required: true, message: '实际入库吨数不能为空', trigger: 'blur' }
          ],
          outWeightWgt: [
            { required: true, message: '实际出库吨数不能为空', trigger: 'blur' }
          ],
          billDatCollection: [
            { required: true, message: '收款时间日期不能为空', trigger: 'blur' }
          ],
          billDatSettlement: [
            { required: true, message: '结算单日期不能为空', trigger: 'blur' }
          ],
          billCodCollection: [
            { required: true, message: '收款单编号不能为空', trigger: 'blur' }
          ],
          billCodSettlement: [
            { required: true, message: '结算单编号不能为空', trigger: 'blur' }
          ],
          moneyCollection: [
            { required: true, message: '收款金额不能为空', trigger: 'blur' }
          ],
          moneySettlement: [
            { required: true, message: '结算金额不能为空', trigger: 'blur' }
          ],
          moneyRebates: [
            { required: true, message: '返款金额(付款单)不能为空', trigger: 'blur' }
          ],
          balance: [
            { required: true, message: '客户余额不能为空', trigger: 'blur' }
          ],
          profit: [
            { required: true, message: '利润（不含税）不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        // this.dataForm.fId = id || 0;
        this.visible = true;
        this.canSubmit = true;
        getListALL(id).then(response =>{

          this.listAll = response.data.data
          console.log(this.listAll)
        })
        // this.$nextTick(() => {
        //   this.$refs['dataForm'].resetFields()
        //   if (this.dataForm.fId) {
        //     getObj(this.dataForm.fId).then(response => {
        //         this.dataForm = response.data.data
        //     })
        //   }
        // })
      },
      // 表单提交
      dataFormSubmit () {
        this.visible = false

      }
    }
  }
</script>
