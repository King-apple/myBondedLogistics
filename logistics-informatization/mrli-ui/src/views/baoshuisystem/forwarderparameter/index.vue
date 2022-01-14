<template>
  <div class="mod-config">
    <basic-container>
      <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
        <el-form-item>
          <el-button  icon="el-icon-plus" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        </el-form-item>
      </el-form>

      <div class="avue-crud">
        <el-table
                :data="dataList"
                border
                v-loading="dataListLoading">
                      <el-table-column
                    prop="fId"
                    header-align="center"
                    align="center"
                    label="主键">
            </el-table-column>
                      <el-table-column
                    prop="serialNumberPay"
                    header-align="center"
                    align="center"
                    label="序号(付款)">
            </el-table-column>
                      <el-table-column
                    prop="contCod"
                    header-align="center"
                    align="center"
                    label="合同编号">
            </el-table-column>
                      <el-table-column
                    prop="billDate"
                    header-align="center"
                    align="center"
                    label="日期(单据)">
            </el-table-column>
                      <el-table-column
                    prop="secondNam"
                    header-align="center"
                    align="center"
                    label="客户">
            </el-table-column>
                      <el-table-column
                    prop="cargoNam"
                    header-align="center"
                    align="center"
                    label="货名">
            </el-table-column>
                      <el-table-column
                    prop="feeQuantity"
                    header-align="center"
                    align="center"
                    label="操作量(吨)">
            </el-table-column>
                      <el-table-column
                    prop="nember"
                    header-align="center"
                    align="center"
                    label="件数">
            </el-table-column>
                      <el-table-column
                    prop="shipNam"
                    header-align="center"
                    align="center"
                    label="船名">
            </el-table-column>
                      <el-table-column
                    prop="loadUnloadDate"
                    header-align="center"
                    align="center"
                    label="装/卸日期">
            </el-table-column>
                      <el-table-column
                    prop="currentAccount"
                    header-align="center"
                    align="center"
                    label="往来款(元)">
            </el-table-column>
                      <el-table-column
                    prop="payContent"
                    header-align="center"
                    align="center"
                    label="付款内容">
            </el-table-column>
                      <el-table-column
                    prop="payUnit"
                    header-align="center"
                    align="center"
                    label="付款单位">
            </el-table-column>
                      <el-table-column
                    prop="firstNam"
                    header-align="center"
                    align="center"
                    label="收款单位">
            </el-table-column>
                      <el-table-column
                    prop="money"
                    header-align="center"
                    align="center"
                    label="金额">
            </el-table-column>
                      <el-table-column
                    prop="receiptInvoice"
                    header-align="center"
                    align="center"
                    label="收到发票">
            </el-table-column>
                      <el-table-column
                    prop="businessSettlementNam"
                    header-align="center"
                    align="center"
                    label="业务结算">
            </el-table-column>
                      <el-table-column
                    prop="applicationInvoice"
                    header-align="center"
                    align="center"
                    label="开票申请">
            </el-table-column>
                      <el-table-column
                    prop="openInvoice"
                    header-align="center"
                    align="center"
                    label="开出发票">
            </el-table-column>
                      <el-table-column
                    prop="serialNumberSet"
                    header-align="center"
                    align="center"
                    label="序号(结算)">
            </el-table-column>
                      <el-table-column
                    prop="settlemnentDataMonth"
                    header-align="center"
                    align="center"
                    label="结算日期(月份)">
            </el-table-column>
                      <el-table-column
                    prop="ladderPrice"
                    header-align="center"
                    align="center"
                    label="阶梯价格">
            </el-table-column>
                      <el-table-column
                    prop="sendInvoice"
                    header-align="center"
                    align="center"
                    label="寄出发票">
            </el-table-column>
                      <el-table-column
                    prop="receivedPay"
                    header-align="center"
                    align="center"
                    label="收到款项">
            </el-table-column>
                      <el-table-column
                    prop="profits"
                    header-align="center"
                    align="center"
                    label="利润">
            </el-table-column>
                      <el-table-column
                    prop="spareMoney"
                    header-align="center"
                    align="center"
                    label="余款">
            </el-table-column>
                      <el-table-column
                    prop="differenceMoney"
                    header-align="center"
                    align="center"
                    label="差额">
            </el-table-column>
                      <el-table-column
                    prop="markTxt"
                    header-align="center"
                    align="center"
                    label="备注">
            </el-table-column>
                      <el-table-column
                    prop="operNam"
                    header-align="center"
                    align="center"
                    label="添加人">
            </el-table-column>
                      <el-table-column
                    prop="recordTim"
                    header-align="center"
                    align="center"
                    label="添加人时间">
            </el-table-column>
                      <el-table-column
                    prop="updateNam"
                    header-align="center"
                    align="center"
                    label="修改人">
            </el-table-column>
                      <el-table-column
                    prop="updateTim"
                    header-align="center"
                    align="center"
                    label="修改时间">
            </el-table-column>
                      <el-table-column
                    prop="delNam"
                    header-align="center"
                    align="center"
                    label="删除人">
            </el-table-column>
                      <el-table-column
                    prop="delTim"
                    header-align="center"
                    align="center"
                    label="删除时间">
            </el-table-column>
                    <el-table-column
                  header-align="center"
                  align="center"
                  label="操作">
            <template slot-scope="scope">
              <el-button type="text" size="small" icon="el-icon-edit" @click="addOrUpdateHandle(scope.row.fId)">修改</el-button>
              <el-button  type="text" size="small" icon="el-icon-delete" @click="deleteHandle(scope.row.fId)">删除</el-button>
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
                layout="total, sizes, prev, pager, next, jumper">
        </el-pagination>
      </div>
      <!-- 弹窗, 新增 / 修改 -->
      <table-form v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></table-form>
    </basic-container>
  </div>
</template>

<script>
  import {fetchList, delObj} from '@/api/baoshuisystem/forwarderparameter'
  import TableForm from './forwarderparameter-form'
  import {mapGetters} from 'vuex'
  export default {
    data () {
      return {
        dataForm: {
          key: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        addOrUpdateVisible: false
      }
    },
    components: {
      TableForm
    },
    created () {
      this.getDataList()
    },
    computed: {
      ...mapGetters(['permissions'])
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        fetchList(Object.assign({
          current: this.pageIndex,
          size: this.pageSize
        })).then(response => {
          this.dataList = response.data.data.records
          this.totalPage = response.data.data.total
        })
        this.dataListLoading = false
      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 删除
      deleteHandle (id) {
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
      }
    }
  }
</script>
