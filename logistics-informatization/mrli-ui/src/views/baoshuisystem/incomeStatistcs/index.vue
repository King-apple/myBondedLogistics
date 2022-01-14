<template>
  <div class="mod-config">
    <basic-container>
      <el-form
        :inline="true"
        :model="dataForm"
        @keyup.enter.native="getDataList()"
      >
        <el-form-item label="年份" prop="year">
          <el-date-picker
            v-model="dataForm.year"
            type="year"
            placeholder="选择年"
            value-format="yyyy"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getDataList">查询</el-button>
          <el-button
            type="success"
            @click="printToExcel"
            v-if="permissions.incomeStatistcs_print"
            >打印</el-button
          >
        </el-form-item>
      </el-form>

      <div class="avue-crud">
        <el-table :data="dataList" border v-loading="dataListLoading">
          <el-table-column
            prop="month"
            header-align="center"
            align="center"
            label="月份"
          >
          </el-table-column>

          <el-table-column
            prop="billDate"
            header-align="center"
            align="center"
            label="结算日期"
          >
          </el-table-column>

          <el-table-column
            prop="secondNam"
            header-align="center"
            align="center"
            label="客户"
          >
          </el-table-column>

          <el-table-column
            prop="totalMoney"
            header-align="center"
            align="center"
            label="利润"
          >
          </el-table-column>
          <el-table-column
            prop="agencyTotalMoneyAfterTax"
            header-align="center"
            align="center"
            label="代理费净收入"
          >
          </el-table-column>
          <el-table-column
            prop="agencyCustomsDeclarationFee"
            header-align="center"
            align="center"
            label="代理报关费利润"
          >
          </el-table-column>
          <el-table-column
            prop="oneDayTour"
            header-align="center"
            align="center"
            label="一日游利润"
          >
          </el-table-column>
          <el-table-column
            prop="incomeWithTax"
            header-align="center"
            align="center"
            label="收入合计（含税）"
          >
          </el-table-column>
          <el-table-column
            prop="incomeWithoutTax"
            header-align="center"
            align="center"
            label="净收入合计（税后）"
          >
          </el-table-column>

          <el-table-column header-align="center" align="center" label="操作">
            <template slot-scope="scope">
              <el-button
                type="text"
                size="small"
                icon="el-icon-edit"
                v-if="permissions.incomeStatistcs_edit"
                @click="addOrUpdateHandle(scope.row)"
                >修改</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>
      <el-dialog
        title="修改信息"
        :close-on-click-modal="false"
        :visible="operateVisible"
        width="40%"
        @close="operateVisible = false"
      >
        <el-form :inline="true" :model="operateForm">
          <el-form-item label="一日游费用" prop="oneDayTour">
            <el-input
              v-model="operateForm.oneDayTour"
              placeholder="一日游费用"
              @focus="openShip"
            ></el-input>
          </el-form-item>

          <el-form-item>
            <el-button
              icon="el-icon-check"
              type="primary"
              @click="saveOperate()"
              >保存</el-button
            >
          </el-form-item>
        </el-form>
      </el-dialog>
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
    </basic-container>
  </div>
</template>

<script>
import {
  findBusinessList,
  getIncomeStatistcs,
  incomeStatistcsPrintToExcel,
  putObj
} from '@/api/baoshuisystem/externalpayment'
import {
  getTradeIdDic,
  getLoadingShip
} from '@/api/baoshuisystem/getdictdata'
import { mapGetters } from 'vuex'
export default {
  data() {
    return {
      dataForm: {
        key: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      addOrUpdateVisible: false,
      operateForm: {},
      operateVisible: false,
      tradeIdOptions: [],
      loadingShipOptions: []
    }
  },
  components: {

  },
  created() {
    var now = new Date();
    var year = now.getFullYear();
    var defaultDate = `${year}`;
    this.$set(this.dataForm, 'year', defaultDate);
    this.getDataList()

  },
  computed: {
    ...mapGetters(['permissions'])
  },
  methods: {
    // 获取数据列表
    getDataList() {
      console.log(this.dataForm)
      getTradeIdDic().then(resp => {
        this.tradeIdOptions = resp.data.data
      })
      getLoadingShip().then(resp => {
        this.loadingShipOptions = resp.data.data
      })
      this.dataListLoading = true
      getIncomeStatistcs(Object.assign({
        current: this.pageIndex,
        size: this.pageSize
      }, this.dataForm)).then(response => {
        this.dataList = response.data.data.records
        this.totalPage = response.data.data.total
      })
      this.dataListLoading = false
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
    addOrUpdateHandle(row) {
      this.operateForm.fId = row.fId
      this.operateVisible = true
      this.$set(this.operateForm, 'tradeId', row.tradeId);
      this.$set(this.operateForm, 'loadingShip', row.loadingShip);
    },
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
    printToExcel() {
      incomeStatistcsPrintToExcel(this.dataForm);

    },
    saveOperate() {
      putObj(this.operateForm).then(resp => {
        this.operateVisible = false
        this.getDataList()
      })
    },
    loadingShipFormatter(row, column, cellValue, index) {
      for (var i in this.loadingShipOptions) {
        if (row.loadingShip == this.loadingShipOptions[i].value) {
          return this.loadingShipOptions[i].label
        }
      }
    },
    tradeIdFormatter(row, column, cellValue, index) {
      for (var i in this.tradeIdOptions) {
        if (row.tradeId == this.tradeIdOptions[i].value) {
          return this.tradeIdOptions[i].label
        }
      }
    }
  }
}
</script>
