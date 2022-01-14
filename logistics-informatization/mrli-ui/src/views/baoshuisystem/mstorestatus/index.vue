<template>
  <div class="mod-config">
    <basic-container>
      <el-form
        :inline="true"
        :model="dataForm"
        @keyup.enter.native="getDataList()"
        ref="dataForm"
        :rules="dataRule"
      >
        <el-form-item
          label="日期"
          prop="recordTim"
          class="searchClass"
          required
        >
          <el-date-picker
            v-model="dataForm.recordTim"
            type="date"
            placeholder="选择日期"
            value-format="yyyy-MM-dd"
            style="width: 94%"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="公司" prop="unitCod" class="searchClass" required>
          <el-select
            v-model="dataForm.unitCod"
            placeholder="请选择"
            filterable
            clearable
            @change="unitCodChange"
            style="width: 100%"
          >
            <el-option
              v-for="(item, index) in this.unitOptions"
              :key="index"
              :label="item.unitNam"
              :value="item.unitCod"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          label="进出口"
          prop="inoutId"
          id="inoutId"
          class="searchClass"
        >
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
          </el-select>
        </el-form-item>
        <el-form-item label="内外贸" prop="tradeId" class="searchClass">
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
          </el-select>
        </el-form-item>
        <el-form-item label="货名" prop="cargoNamCod" class="searchClass">
          <el-select
            v-model="dataForm.cargoNamCod"
            placeholder="请选择"
            filterable
            clearable
            style="width: 100%"
          >
            <el-option
              v-for="(item, index) in this.cargoOptions"
              :key="index"
              :label="item.cargoNam"
              :value="item.cargoNamCod"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="保管单位" prop="subunitCod">
          <el-select
            v-model="dataForm.subunitCod"
            placeholder="请选择"
            filterable
            clearable
          >
            <el-option
              v-for="(item, index) in this.subunitOptions"
              :key="index"
              :label="item.subunitNam"
              :value="item.subunitCod"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="进口船名" prop="shipNam">
          <el-input
            v-model="dataForm.shipNam"
            placeholder="进口船名"
            @focus="openShip"
          ></el-input>
        </el-form-item>

        <el-form-item label="委托人代理" prop="consignCod">
          <el-select
            v-model="dataForm.consignCod"
            placeholder="请选择"
            filterable
            clearable
          >
            <el-option
              v-for="(item, index) in this.consignOptions"
              :key="index"
              :label="item.shipperDoc"
              :value="item.shipperCod"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="港口货主" prop="shipperCod">
          <el-select
            v-model="dataForm.shipperCod"
            placeholder="请选择"
            filterable
            clearable
          >
            <el-option
              v-for="(item, index) in this.consignOptions"
              :key="index"
              :label="item.shipperDoc"
              :value="item.shipperCod"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="getDataList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="printToExcel">打印</el-button>
        </el-form-item>
      </el-form>

      <div class="avue-crud mstorTab">
        <el-table
          :data="dataList"
          height="500px"
          border
          :summary-method="getSummaries"
          show-summary
          ref="dataList"
          style="width: 100%; margin-top: 20px"
          v-loading="dataListLoading"
        >
          <el-table-column
            prop="unitCod"
            header-align="center"
            align="center"
            label="单位代码"
          >
          </el-table-column>
          <el-table-column
            prop="unitNam"
            header-align="center"
            align="center"
            label="单位名称"
          >
          </el-table-column>
          <el-table-column
            prop="recordTim"
            header-align="center"
            align="center"
            label="日期"
          >
          </el-table-column>
          <el-table-column
            prop="subunitCod"
            header-align="center"
            align="center"
            label="保管单位代码"
          >
          </el-table-column>
          <el-table-column
            prop="subunitNam"
            header-align="center"
            align="center"
            label="保管单位"
          >
          </el-table-column>
          <el-table-column
            prop="shipNo"
            header-align="center"
            align="center"
            label="船号"
          >
          </el-table-column>
          <el-table-column
            prop="shipNam"
            header-align="center"
            align="center"
            label="船名"
          >
          </el-table-column>

          <el-table-column
            prop="inoutNam"
            header-align="center"
            align="center"
            label="进出口 "
          >
          </el-table-column>

          <el-table-column
            prop="tradeNam"
            header-align="center"
            align="center"
            label="内外贸"
          >
          </el-table-column>
          <el-table-column
            prop="inPortWayNam"
            header-align="center"
            align="center"
            label="进出港方式"
          >
          </el-table-column>

          <el-table-column
            prop="consignCod"
            header-align="center"
            align="center"
            label="代理货主代码"
          >
          </el-table-column>
          <el-table-column
            prop="consignNam"
            header-align="center"
            align="center"
            label="代理货主"
          >
          </el-table-column>
          <el-table-column
            prop="shipperCod"
            header-align="center"
            align="center"
            label="港口货主代码"
          >
          </el-table-column>
          <el-table-column
            prop="shipperDoc"
            header-align="center"
            align="center"
            label="港口货主"
          >
          </el-table-column>
          <el-table-column
            prop="yardNo"
            header-align="center"
            align="center"
            label="库场号"
          >
          </el-table-column>
          <el-table-column
            prop="yardNam"
            header-align="center"
            align="center"
            label="库场名"
          >
          </el-table-column>

          <!-- <el-table-column
            prop="cargoNo"
            header-align="center"
            align="center"
            label="货号"
          >
          </el-table-column> -->

          <el-table-column
            prop="cargoKindNam"
            header-align="center"
            align="center"
            label="货类"
          >
          </el-table-column>
          <el-table-column
            prop="cargoNamCod"
            header-align="center"
            align="center"
            label="货名代码"
          >
          </el-table-column>
          <el-table-column
            prop="cargoNam"
            header-align="center"
            align="center"
            label="货名"
          >
          </el-table-column>

          <el-table-column
            prop="piecesNum"
            header-align="center"
            align="center"
            label="件数"
          >
          </el-table-column>
          <el-table-column
            prop="weightWgt"
            header-align="center"
            align="center"
            label="重量"
          >
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

      <el-dialog
        title="进口船名"
        :close-on-click-modal="false"
        :visible="shipVisible"
        width="80%"
        @close="shipVisible = false"
      >
        <el-form :inline="true" :model="shipForm">
          <el-form-item label="进口船名" prop="shipNam">
            <el-input
              v-model="shipForm.shipNam"
              placeholder="进口船名"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button
              icon="el-icon-search"
              type="primary"
              @click="getShipList()"
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
          <el-button @click="cargoVisible = false">取消</el-button>

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
      <!-- 弹窗, 新增 / 修改 -->
    </basic-container>
  </div>
</template>

<script>
import { fetchList, printToExcel } from '@/api/baoshuisystem/mstorestatus'
import {
  getTradeIdDic,
  getInoutIdDic,
  getCunitNam,
  getCargoNam,
  getSubunit,
  getShip,
  getShipperDoc
} from '@/api/baoshuisystem/getdictdata'
import { mapGetters } from 'vuex'
export default {
  data() {
    return {
      dataForm: {
        key: ''
      },
      shipForm: {
        key: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      shipPageIndex: 1,
      shipPageSize: 10,
      shipTotalPage: 0,
      dataListLoading: false,
      addOrUpdateVisible: false,
      unitOptions: [],
      inoutIdOptions: [],
      tradeIdtions: [],
      cargoOptions: [],
      subunitOptions: [],
      shipNamOptions: [],
      consignOptions: [],
      shipVisible: false,
      shipSelect: [],
      shipLoading: false,
      buttonDisable: true,
      dataRule: {
        recordTim: [{ required: true, message: "日期不能为空", trigger: 'blur' }],
        unitCod: [{ required: true, message: "公司不能为空", trigger: 'blur' }]

      }
    }
  },
  components: {

  },
  created() {
    // this.getDataList()
    this.getDictData()
    this.getNowTime()
  },
  updated() {
    this.$nextTick(() => {
      this.$refs.dataList.doLayout()
    })
  },
  computed: {
    ...mapGetters(['permissions'])
  },
  methods: {
    // 获取数据列表
    getDataList() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.dataListLoading = true
          fetchList(Object.assign({
            current: this.pageIndex,
            size: this.pageSize
          }, this.dataForm)).then(response => {
            this.dataList = response.data.data.records
            this.totalPage = response.data.data.total
            this.dataListLoading = false
          })
        }
      })


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
    getDictData() {
      getCunitNam().then(resp => {
        this.unitOptions = resp.data.data
      })
      getTradeIdDic().then(resp => {
        this.tradeIdtions = resp.data.data
      })
      getInoutIdDic().then(resp => {
        this.inoutIdOptions = resp.data.data
      })
      getCargoNam().then(resp => {
        this.cargoOptions = resp.data.data
      })

      getShipperDoc().then(resp => {
        this.consignOptions = resp.data.data
      })

    },
    unitCodChange() {
      console.log(this.dataForm)
      getSubunit(this.dataForm.unitCod).then(resp => {
        this.subunitOptions = resp.data.data
      })
    },
    shipSelectionChange(val) {
      this.shipSelect = val
      if (this.shipSelect.length == 1) {
        this.buttonDisable = false
      } else {
        this.buttonDisable = true
      }
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
    },
    getSummaries(param) {
      const { columns, data } = param;
      const sums = [];
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '合计';
          return;
        }
        const values = data.map(item => Number(item[column.property]));
        if (!values.every(value => isNaN(value)) && (column.property == 'piecesNum' || column.property == 'weightWgt')) {
          sums[index] = values.reduce((prev, curr) => {
            const value = Number(curr);
            if (!isNaN(value)) {
              return prev + curr;
            } else {
              return prev;
            }
          }, 0);
          sums[index] += ' ';
        } else {
          sums[index] = '';
        }
      });

      return sums;
    },
    getNowTime() {
      var now = new Date();
      var year = now.getFullYear(); //得到年份
      var month = now.getMonth(); //得到月份
      var date = now.getDate(); //得到日期
      month = month + 1;
      month = month.toString().padStart(2, "0");
      date = date.toString().padStart(2, "0");
      var defaultDate = `${year}-${month}-${date}`;
      this.$set(this.dataForm, "recordTim", defaultDate);
    },
    printToExcel() {
      printToExcel(this.dataForm);
    }
  }
}
</script>
<style>
.searchClass {
  width: 19%;
}
.searchClass .el-form-item__content {
  width: 75% !important;
}
</style>
<style lang="less">
.mstorTab {
  // .el-table {
  //   //overflow: auto;
  // }
  // .el-table__body-wrapper,
  // .el-table__header-wrapper,
  // .el-table__footer-wrapper {
  //   overflow: visible;
  // }
  // .el-table::after {
  //   position: relative !important;
  // }
}
</style>