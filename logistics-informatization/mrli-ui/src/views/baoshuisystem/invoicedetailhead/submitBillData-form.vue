<template>
  <el-dialog
    :title="title"
    :close-on-click-modal="false"
    :visible.sync="visible"
    width="60%"
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      label-width="80px"
    >
      <el-row>
        <el-col :span="6">
          <el-form-item label="收款人" prop="moneyWayNam">
            <el-input
              v-model="dataForm.moneyWayNam"
              placeholder="收款人"
            ></el-input> </el-form-item
        ></el-col>
        <el-col :span="6"
          ><el-form-item label="审核人" prop="endCheckerNam">
            <el-input
              v-model="dataForm.endCheckerNam"
              placeholder="审核人"
            ></el-input> </el-form-item
        ></el-col>
        <el-col :span="6">
          <el-form-item label="销方地址" prop="xfShipperAdd">
            <el-input
              v-model="dataForm.xfShipperAdd"
              placeholder="销方地址"
            ></el-input> </el-form-item
        ></el-col>
        <el-col :span="6">
          <el-form-item label="销方银行" prop="xfShipperAcn">
            <el-input
              v-model="dataForm.xfShipperAcn"
              placeholder="销方银行"
            ></el-input> </el-form-item
        ></el-col>
      </el-row>
      <el-row>
        <el-col :span="6"
          ><el-form-item label="销方纳税人识别号" prop="xfShipperIrdNo">
            <el-input
              v-model="dataForm.xfShipperIrdNo"
              placeholder="销方纳税人识别号"
            ></el-input> </el-form-item
        ></el-col>
        <el-col :span="6"
          ><el-form-item label="销方电话" prop="xfdh">
            <el-input
              v-model="dataForm.xfdh"
              placeholder="销方电话"
            ></el-input> </el-form-item
        ></el-col>
        <el-col :span="6"
          ><el-form-item label="是否分票" prop="ifSplit">
            <el-select
              v-model="dataForm.ifSplit"
              placeholder="请选择"
              filterable
              clearable
            >
              <el-option
                v-for="(item, index) in this.ifSplitOptions"
                :key="index"
                :label="item.label"
                :value="item.label"
              >
              </el-option>
            </el-select> </el-form-item
        ></el-col>
        <el-col :span="6">
          <el-form-item label="购方地址" prop="gfAddress">
            <el-input
              v-model="dataForm.gfAddress"
              placeholder="购方地址"
            ></el-input> </el-form-item
        ></el-col>
      </el-row>
      <el-row>
        <el-col :span="6">
          <el-form-item label="购方电话" prop="gfPhone">
            <el-input
              v-model="dataForm.gfPhone"
              placeholder="购方电话"
            ></el-input> </el-form-item
        ></el-col>
        <el-col :span="6"
          ><el-form-item label="购方开户行" prop="gfBank">
            <el-input
              v-model="dataForm.gfBank"
              placeholder="购方开户行"
            ></el-input> </el-form-item
        ></el-col>
        <el-col :span="6">
          <el-form-item label="购方账号" prop="gfAccont">
            <el-input
              v-model="dataForm.gfAccont"
              placeholder="购方账号"
            ></el-input> </el-form-item
        ></el-col>
        <el-col :span="6"> </el-col>
      </el-row>

      <el-row>
        <el-col :span="6"
          ><el-form-item label="船号" prop="shipNo">
            <el-input
              v-model="dataForm.shipNo"
              placeholder="船号"
              disabled
            ></el-input> </el-form-item
        ></el-col>
        <el-col :span="6">
          <el-form-item label="船名" prop="shipNam">
            <el-input
              v-model="dataForm.shipNam"
              placeholder="船名"
              @focus="openShip"
            ></el-input> </el-form-item
        ></el-col>
        <el-col :span="6">
          <el-form-item label="英文船名" prop="eShipNam">
            <el-input
              v-model="dataForm.eShipNam"
              placeholder="英文船名"
            ></el-input> </el-form-item
        ></el-col>
        <el-col :span="6"
          ><el-form-item label="航次" prop="voyage">
            <el-input
              v-model="dataForm.voyage"
              placeholder="航次"
            ></el-input> </el-form-item
        ></el-col>
      </el-row>

      <el-row>
        <el-col :span="6">
          <el-form-item label="内外贸" prop="tradeId">
            <el-select
              v-model="dataForm.tradeId"
              placeholder="请选择"
              filterable
              clearable
            >
              <el-option
                v-for="(item, index) in this.tradeIdtions"
                :key="index"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select> </el-form-item
        ></el-col>
        <el-col :span="6">
          <el-form-item label="进出口" prop="inoutId">
            <el-select
              v-model="dataForm.inoutId"
              placeholder="请选择"
              filterable
              clearable
            >
              <el-option
                v-for="(item, index) in this.inoutIdOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select> </el-form-item
        ></el-col>
        <el-col :span="6">
          <el-form-item label="靠泊时间" prop="toBerthTim">
            <el-date-picker
              v-model="dataForm.toBerthTim"
              type="date"
              placeholder="选择日期"
              value-format="yyyy-MM-dd"
              style="width: 94%"
            >
            </el-date-picker> </el-form-item
        ></el-col>
        <el-col :span="6">
          <el-form-item label="离港时间" prop="levPortTim">
            <el-date-picker
              v-model="dataForm.levPortTim"
              type="date"
              placeholder="选择日期"
              value-format="yyyy-MM-dd"
              style="width: 94%"
            >
            </el-date-picker> </el-form-item
        ></el-col>
      </el-row>

      <el-row>
        <el-col :span="6">
          <el-form-item label="单号" prop="billNo">
            <el-input
              v-model="dataForm.billNo"
              placeholder="单号"
            ></el-input> </el-form-item
        ></el-col>
        <el-col :span="6"
          ><el-form-item label="净吨" prop="netWgt">
            <el-input
              v-model="dataForm.netWgt"
              placeholder="净吨"
            ></el-input> </el-form-item
        ></el-col>
        <el-col :span="6"
          ><el-form-item label="往来港代码" prop="fromPortCod">
            <el-input
              v-model="dataForm.fromPortCod"
              placeholder="往来港代码"
            ></el-input> </el-form-item
        ></el-col>
        <el-col :span="6">
          <el-form-item label="往来港名称" prop="fromPortNam">
            <el-input
              v-model="dataForm.fromPortNam"
              placeholder="往来港名称"
            ></el-input> </el-form-item
        ></el-col>
      </el-row>

      <el-row>
        <el-col :span="6"
          ><el-form-item label="客户地址" prop="shipperAdd">
            <el-input
              v-model="dataForm.shipperAdd"
              placeholder="客户地址"
            ></el-input> </el-form-item
        ></el-col>
        <el-col :span="6"
          ><el-form-item label="开户行" prop="shipperAcn">
            <el-input
              v-model="dataForm.shipperAcn"
              placeholder="开户行"
            ></el-input> </el-form-item
        ></el-col>
        <el-col :span="6"
          ><el-form-item label="纳税人识别号" prop="shipperIrdNo">
            <el-input
              v-model="dataForm.shipperIrdNo"
              placeholder="纳税人识别号"
            ></el-input> </el-form-item
        ></el-col>
        <el-col :span="6"
          ><el-form-item label="红冲发票信息表编号" prop="negativeInfo">
            <el-input
              v-model="dataForm.negativeInfo"
              placeholder="红冲发票信息表编号"
            ></el-input> </el-form-item
        ></el-col>
      </el-row>

      <el-row>
        <el-col :span="24">
          <el-form-item label="备注" prop="remark">
            <el-input
              v-model="dataForm.remark"
              placeholder="备注"
            ></el-input> </el-form-item
        ></el-col>
        <el-col :span="6"></el-col>
        <el-col :span="6"></el-col>
      </el-row>

      <div v-for="(item, index) in this.billDataBodyList" :key="index">
        <form
          :model="billDataBodyList[index]"
          :rules="feeRule"
          ref="feeForm"
          label-width="80px"
        >
          费目{{ index + 1 }}
          <el-row>
            <el-col :span="6">
              <el-form-item label="费目" prop="projectNam">
                <el-input
                  v-model="item.projectNam"
                  placeholder="费目"
                  disabled
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="费率" prop="projectNam">
                <el-input
                  v-model="item.unit"
                  placeholder="费率"
                  disabled
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="单价" prop="projectNam">
                <el-input
                  v-model="item.unitPrice"
                  placeholder="单价"
                  disabled
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="税率" prop="gsTaxRat">
                <el-input v-model="item.gsTaxRat" placeholder="税率"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="税额" prop="gsTaxFee">
                <el-input v-model="item.gsTaxFee" placeholder="税额"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="税后额" prop="gsSeFee">
                <el-input
                  v-model="item.gsSeFee"
                  placeholder="税后额"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="单位" prop="cFeeUnit">
                <el-input v-model="item.cFeeUnit" placeholder="单位"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="税收分类编码" prop="slflbm">
                <el-input
                  v-model="item.slflbm"
                  placeholder="税收分类编码"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </form>
      </div>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>

    <el-dialog
      title="进口船名"
      :close-on-click-modal="false"
      :visible="shipVisible"
      width="80%"
      @close="shipVisible = false"
      append-to-body
    >
      <el-form :inline="true" :model="shipForm">
        <el-form-item label="进口船名" prop="shipNam">
          <el-input
            v-model="shipForm.shipNam"
            placeholder="进口船名"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-search" type="primary" @click="getShipList()"
            >搜索</el-button
          >
        </el-form-item>
      </el-form>
      <el-table
        highlight-current-row
        :data="shipNamOptions"
        @selection-change="shipSelectionChange"
        border
        v-loading="shipLoading"
      >
        <el-table-column type="selection"> </el-table-column>
        <el-table-column
          prop="shipNo"
          header-align="center"
          align="center"
          label="船舶编号"
          sortable
        >
        </el-table-column>
        <el-table-column
          prop="shipNam"
          header-align="center"
          align="center"
          label="船舶名称"
          sortable
        >
        </el-table-column>
        <el-table-column
          prop="toPortTim"
          header-align="center"
          align="center"
          label="进港时间"
        >
        </el-table-column>
      </el-table>
      <div style="float: right; height: 100px; margin-top: 10px">
        <el-button @click="shipVisible = false">取消</el-button>

        <el-button type="primary" @click="closeShip" :disabled="buttonDisable"
          >确定</el-button
        >
      </div>

      <div class="avue-crud__pagination" style="margin-top: 50px">
        <el-pagination
          @size-change="shipSizeChangeHandle"
          @current-change="shipCurrentChangeHandle"
          :current-page="shipPageIndex"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="shipPageSize"
          :total="shipTotalPage"
          background
          layout="total, sizes, prev, pager, next, jumper"
        >
        </el-pagination>
      </div>
    </el-dialog>
  </el-dialog>
</template>

<script>
import { saveBillData } from "@/api/baoshuisystem/invoicedetailhead";
import {
  getTradeIdDic,
  getShip,
  getInoutIdDic
} from '@/api/baoshuisystem/getdictdata'
export default {
  data() {
    var validatePass2 = (rule, value, callback) => {
      if (isNaN(Number(value)) == true && (value != undefined)) {
        callback(new Error('请输入数字'));
      } else {
        callback();
      }
    };
    return {
      title: "补充录入数据",
      disabled: false,
      visible: false,
      dataForm: {
        billDataBodyList: []
      },
      feeForm: {},
      billDataBodyList: [],
      shipVisible: false,
      shipForm: {},
      shipNamOptions: [],
      ifSplitOptions: [{ label: '是' }, { label: '否' }],
      shipLoading: false,
      buttonDisable: true,
      shipPageIndex: 1,
      shipPageSize: 10,
      shipTotalPage: 0,
      inoutIdOptions: [],
      tradeIdtions: [],
      dataRule: {
        moneyWayNam: [
          { required: true, message: "收款人不能为空", trigger: "blur" },
        ],
        endCheckerNam: [
          { required: true, message: "审核人不能为空", trigger: "blur" },
        ],
        xfShipperAdd: [
          { required: true, message: "销方地址不能为空", trigger: "blur" },
        ],
        xfShipperAcn: [
          { required: true, message: "销方银行不能为空", trigger: "blur" },
        ],
        xfShipperIrdNo: [
          { required: true, message: "销方纳税人识别号不能为空", trigger: "blur" },
        ],
        xfdh: [
          { required: true, message: "销方电话不能为空", trigger: "blur" },
        ],
        ifSplit: [
          { required: true, message: "是否分票不能为空", trigger: "blur" },
        ],
        gfAddress: [
          { required: true, message: "购方地址不能为空", trigger: "blur" },
        ],
        gfPhone: [
          { required: true, message: "购方电话不能为空", trigger: "blur" },
        ],
        gfBank: [
          { required: true, message: "购方开户行不能为空", trigger: "blur" },
        ],
        gfAccont: [
          { required: true, message: "购方账号不能为空", trigger: "blur" },
        ],
        netWgt: [
          { validator: validatePass2, trigger: "blur" },
        ],
      },

      feeRule: {
        gsTaxRat: [
          { required: true, message: "税率不能为空", trigger: "blur" },
        ],
      }
    };

  },
  computed: {

  },
  methods: {

    init(id) {
      this.dataForm = {}
      this.billDataBodyList = id.invoiceDetailBodyList
      this.visible = true
      this.dataForm.feeBillNo = id.fId
      this.dataForm.shipperCod = id.secondCod
      this.dataForm.shipperDoc = id.secondNam
      var contNo = id.billCod.split('-')
      this.dataForm.contNo = contNo[0] + '-' + contNo[1] + '-' + contNo[2] + '-' + contNo[3]
      this.dataForm.unitNam = id.accountNam
      this.dataForm.upDf = '0'
      this.dataForm.kpKind = id.invoiceType
      this.dataForm.billApprovalNo = id.billCod
      this.dataForm.pkid = id.fId
      for (var i in this.billDataBodyList) {
        this.billDataBodyList[i].cFeeNam = this.billDataBodyList[i].projectNam
        this.billDataBodyList[i].feeRat = this.billDataBodyList[i].unitPrice
        this.billDataBodyList[i].weightWgt = this.billDataBodyList[i].amount
        this.billDataBodyList[i].feeMny = this.billDataBodyList[i].money
        this.billDataBodyList[i].pkid = this.billDataBodyList[i].fId
      }
      getTradeIdDic().then(resp => {
        this.tradeIdtions = resp.data.data
      })
      getInoutIdDic().then(resp => {
        this.inoutIdOptions = resp.data.data
      })
      this.$nextTick(() => {
        // this.$refs["dataForm"].resetFields();

      });
    },

    // 表单提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate((valid) => {
        if (valid) {

          console.log(this.dataForm)
          this.dataForm.billDataBodyList = this.billDataBodyList
          for (var i in this.dataForm.billDataBodyList) {
            if (this.dataForm.billDataBodyList[i].gsTaxRat == '' || this.dataForm.billDataBodyList[i].gsTaxRat == undefined) {
              this.$notify.error("税率不能为空");
              return
            } else if (isNaN(Number(this.dataForm.billDataBodyList[i].gsTaxRat))) {
              this.$notify.error("税率只能为数字");
              return
            }
            if (this.dataForm.billDataBodyList[i].gsTaxFee == '' || this.dataForm.billDataBodyList[i].gsTaxFee == undefined) {
              this.$notify.error("税额不能为空");
              return
            } else if (isNaN(Number(this.dataForm.billDataBodyList[i].gsTaxFee))) {
              this.$notify.error("税额只能为数字");
              return
            }
            if (this.dataForm.billDataBodyList[i].gsSeFee == '' || this.dataForm.billDataBodyList[i].gsSeFee == undefined) {
              this.$notify.error("税后额不能为空");
              return
            } else if (isNaN(Number(this.dataForm.billDataBodyList[i].gsSeFee))) {
              this.$notify.error("税后额只能为数字");
              return
            }
            if (this.dataForm.billDataBodyList[i].cFeeUnit == '' || this.dataForm.billDataBodyList[i].cFeeUnit == undefined) {
              this.$notify.error("单位不能为空");
              return
            }
            if (this.dataForm.billDataBodyList[i].slflbm == '' || this.dataForm.billDataBodyList[i].slflbm == undefined) {
              this.$notify.error("税收分类编码不能为空");
              return
            }
          }
          saveBillData(this.dataForm).then(resp => {
            if (resp.data.data.msg == '上传成功') {
              this.$notify.success("上传成功")
              this.visible = false
            } else {
              this.$notify.error(resp.data.data.msg)
              this.visible = false
            }

          })
        }
      });
    },
    // 每页数
    shipSizeChangeHandle(val) {
      this.shipPageSize = val
      this.shipPageIndex = 1
      this.getShipList()
    },
    // 当前页
    shipCurrentChangeHandle(val) {
      this.shipPageIndex = val
      this.getShipList()
    },
    getShipList() {
      this.shipLoading = true
      getShip(Object.assign({
        current: this.shipPageIndex,
        size: this.shipPageSize
      }, this.shipForm)).then(response => {
        this.shipNamOptions = response.data.data.records
        this.shipLoading = false
        this.shipTotalPage = response.data.data.total
      })

    },
    openShip() {
      this.shipVisible = true
      this.getShipList();
    },
    closeShip(row) {
      this.shipVisible = false
      this.$set(this.dataForm, "shipNam", this.shipSelect[0].shipNam);
      this.$set(this.dataForm, "shipNo", this.shipSelect[0].shipNo);
    },
    shipSelectionChange(val) {
      this.shipSelect = val
      if (this.shipSelect.length == 1) {
        this.buttonDisable = false
      } else {
        this.buttonDisable = true
      }
    },
  },
};
</script>
<style scoped>
</style>
