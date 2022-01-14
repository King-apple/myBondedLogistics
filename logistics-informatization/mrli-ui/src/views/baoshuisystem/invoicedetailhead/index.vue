<template>
  <div class="mod-config">
    <basic-container>
      <el-form
        :inline="true"
        :model="dataForm"
        @keyup.enter.native="getDataList()"
      >
        <div style="display: flex">
          <el-form-item label="结算单编号">
             <el-input
              v-model="dataForm.billCod"
              placeholder="结算单编号:"
              style="width: 7vw"
            ></el-input>
          </el-form-item>
          <el-form-item label="账户" prop="accountNam">
            <el-input
              v-model="dataForm.accountNam"
              placeholder="请输入账户名"
              style="width: 7vw"
            ></el-input>
          </el-form-item>
          <el-form-item label="发票类型" prop="invoiceType">
            <el-select
              v-model="dataForm.invoiceType"
              placeholder="发票类型:"
              filterable
              style="width: 7vw"
            >
              <el-option
                v-for="(item, index) in invoicTpyeDict"
                :key="index"
                :label="item.label"
                :value="item.label"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="客户" prop="secondNam">
            <el-select
              v-model="dataForm.secondNam"
              placeholder="请选择"
              filterable
              clearable
              style="width: 7vw"
            >
              <el-option
                v-for="(item, index) in secondNamOptions"
                :key="index"
                :label="item.company"
                :value="item.company"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="日期范围" prop="params">
            <el-date-picker
              v-model="dataForm.params"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :picker-options="pickerOptions"
              align="right"
              value-format="yyyy-MM-dd"
              style="width: 15vw"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button
              type="success"
              icon="el-icon-search"
              @click="getDataListQuery"
              >查询</el-button
            >
          </el-form-item>
          <el-form-item>
            <el-button
              type="warning"
              icon="el-icon-refresh"
              @click="resetForm('dataForm')"
              >重置</el-button
            >
          </el-form-item>
        </div>
        <el-form-item>
          <el-button
            icon="el-icon-plus"
            type="primary"
            @click="addOrUpdateHandle()"
            >新增</el-button
          >
        </el-form-item>
      </el-form>

      <div class="avue-crud">
        <el-table :data="dataList" border v-loading="dataListLoading">
          <!-- <el-table-column
            label="序号"
            type="index"
            width="50"
            align="center"
            fixed="left"
          > -->
          <!-- <template slot-scope="scope">
              <span>{{ (pageIndex - 1) * pageSize + scope.$index + 1 }}</span>
            </template> -->
          <el-table-column type="expand" fixed="left">
            <template slot-scope="props">
              <el-form
                label-position="left"
                inline
                class="demo-table-expand myData"
              >
                <div
                style="width: 550px;"
                  v-for="(item, index) of props.row.invoiceDetailBodyList"
                  :key="index"
                  
                >
                  <el-form-item :label="'费目' + (index + 1)">
                    {{ item.projectNam }}
                  </el-form-item>
                  <el-form-item label="单位">
                    {{ item.unit }}
                  </el-form-item>
                  <el-form-item label="费率值">
                    {{ item.unitPrice }}
                  </el-form-item>
                  <el-form-item label="数量">
                    {{ item.amount }}
                  </el-form-item>
                  <el-form-item label="金额">
                    {{ item.money }}
                  </el-form-item>
                  <hr />
                </div>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column
            prop="billCod"
            header-align="center"
            align="center"
            label="结算单编号"
            sortable
            width="185px"
          >
          </el-table-column>
          <el-table-column
            prop="accountNam"
            header-align="center"
            align="center"
            label="对应账户"
            width="215px"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="invoiceType"
            header-align="center"
            align="center"
            label="发票类型"
            width="100px"
            sortable
          >
          </el-table-column>

          <el-table-column
            prop="secondNam"
            header-align="center"
            align="center"
            label="客户名称"
            width="100px"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="totalMoney"
            header-align="center"
            align="center"
            label="合计金额"
            width="100px"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="inShipName"
            sortable
            header-align="center"
            align="center"
            width="100px"
            label="入境船名"
          >
          </el-table-column>
          <el-table-column
            prop="outShipName"
            sortable
            header-align="center"
            align="center"
            width="100px"
            label="出境船名"
          >
          </el-table-column>
          <el-table-column
            prop="operDat"
            header-align="center"
            align="center"
            label="日期"
            width="100px"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="operNam"
            header-align="center"
            align="center"
            label="添加人"
            width="90px"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="recordTim"
            header-align="center"
            align="center"
            label="添加时间"
            width="150px"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="updateNam"
            header-align="center"
            align="center"
            label="修改人"
            width="90px"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="updateTim"
            header-align="center"
            align="center"
            width="150px"
            label="修改时间"
            sortable
          >
          </el-table-column>
          <el-table-column
            header-align="center"
            align="center"
            width="200px"
            fixed="right"
            label="操作"
          >
            <template slot-scope="scope">
              <!-- <el-button type="text" size="small" icon="el-icon-edit" @click="checkHandle(scope.row.fId)">查看</el-button> -->
              <el-button
                type="text"
                size="small"
                icon="el-icon-edit"
                @click="addOrUpdateHandle(scope.row.fId)"
                >修改</el-button
              >
              <el-button
                type="text"
                size="small"
                icon="el-icon-delete"
                @click="deleteHandle(scope.row.fId)"
                >删除</el-button
              >
              <el-button
                type="text"
                size="small"
                icon="el-icon-delete"
                @click="expoprt(scope.row)"
                >导出
              </el-button>
              <el-button
                type="text"
                size="small"
                icon="el-icon-delete"
                v-if="
                  permissions.invoiceDetail_submitBill &&
                  scope.row.uploadBillFlag != 1
                "
                @click="submitBillData(scope.row)"
                >上传数据至发票系统
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <div class="avue-crud__pagination">
        <el-pagination
          @size-change="sizeChangeHandle"
          @current-change="currentChangeHandle"
          :current-page="pageIndex"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="pageSize"
          :total="totalPage"
          background
          layout="total, sizes, prev, pager, next, jumper"
        >
        </el-pagination>
      </div>
      <!-- 弹窗, 新增 / 修改 -->
      <table-form
        v-if="addOrUpdateVisible"
        ref="addOrUpdate"
        @refreshDataList="getDataList"
      ></table-form>
      <billdata-form
        v-if="billDataVisible"
        ref="billData"
        @refreshDataList="getDataList"
      ></billdata-form>

      <el-dialog
        title="打印预览"
        :visible.sync="PrintdialogVisible"
        width="50%"
      >
        <div class="table-html-wrap" v-html="tableHtml" id="printTest"></div>
        <div style="margin-top: 5%; margin-left: 80%">
          <el-button @click="PrintdialogVisible = false">取 消</el-button>
          <el-button type="primary" v-print="'#printTest'">打 印</el-button>
        </div>
      </el-dialog>
    </basic-container>
  </div>
</template>

<script>
import {
  getInsertValue, fileSave, getFileInfo, delFile, handleDown, getAllContractrate, getContractCode, getChildCode, fileUpdate
  , getInoutIdDic, getTradeIdDic
} from '@/api/baoshuisystem/contract'
import { fetchList, delObj, expertA, getInfo, saveBillData } from '@/api/baoshuisystem/invoicedetailhead'
import TableForm from './invoicedetailhead-form'
import billdataForm from './submitBillData-form'
import { getInvoicTpye } from "@/api/baoshuisystem/externalpayment";
import { mapGetters } from 'vuex'
import XLSX from 'xlsx'
export default {
  data() {
    return {
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      secondNamOptions: [],
      dataForm: {
        key: ''
      },
      bbBodyList: [],
      billCodList: [],
      tableHtml: "",
      PrintdialogVisible: false,
      invoicTpyeDict: '',
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      addOrUpdateVisible: false,
      checkFlag: false,
      billDataVisible: false,

    }
  },
  components: {
    TableForm,
    billdataForm
  },
  created() {
    this.getDataList()
    this.getInfo()
    this.getCust()
  },
  computed: {
    ...mapGetters(['permissions'])
  },
  methods: {
    getInfo() {
      getInvoicTpye().then((response) => {
        this.invoicTpyeDict = response.data.data;
      });
      getInfo().then((res) => {
        this.billCodList = res.data.data.dalston;
      });
    },
    // expoprt(data){
    //   console.log("data",data)
    //   expertA(data).then((response) => { // 处理返回的文件流
    //   if (response.status === 200) {
    //     this.PrintdialogVisible = true;
    //     var item = new Uint8Array(response.data)
    //     var workbook = XLSX.read(item, {
    //       type: 'array'
    //     })
    //     var worksheet = workbook.Sheets[workbook.SheetNames[0]];

    //     var innerHTML = XLSX.utils.sheet_to_html(worksheet);
    //     this.tableHtml = innerHTML;
    //   }
    // })
    // },
    expoprt(data) {
      expertA("开票明细.xlsx", data)
    },
    getCust() {
      getInsertValue().then(response => {
        this.secondNamOptions = response.data.data.clientInfos
      })
    },
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true
      fetchList(Object.assign(
        {
          current: this.pageIndex,
          size: this.pageSize,
        },
        this.dataForm
      )).then(response => {
        this.dataList = response.data.data.records

        this.totalPage = response.data.data.total
      })
      this.dataListLoading = false
    },
    getDataListQuery() {
      this.getDataList()
    },
    resetForm() {
      this.dataForm = {}
      this.getDataList();
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val
      this.getDataList()
    },
    // 新增 / 修改
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id)
      })
    },
    submitBillData(id) {
      this.billDataVisible = true
      this.$nextTick(() => {
        this.$refs.billData.init(id)
      })
    },
    // checkHandle (id) {
    //    this.addOrUpdateVisible = true
    //   this.$nextTick(() => {
    //     this.$refs.addOrUpdate.init(id,this.checkFlag)
    //   })
    // },
    // 删除
    deleteHandle(id) {
      this.$confirm('是否确认删除ID为' + id, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function () {
        return delObj(id)
      }).then(data => {
        this.$message.success('删除成功')
        this.getDataList()
      })
    },

  }
}
</script>

