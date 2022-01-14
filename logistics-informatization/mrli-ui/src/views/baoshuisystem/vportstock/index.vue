<template>
  <div class="mod-config">
    <basic-container>
      <el-form
        :inline="true"
        ref="dataForm"
        :model="dataForm"
        @keyup.enter.native="getDataList()"
        :rules="dataRule"
      >
        <el-form-item label="入库日期" prop="startDte" class="searchClassV">
          <el-date-picker
            v-model="dataForm.startDte"
            type="date"
            placeholder="选择日期"
            value-format="yyyy-MM-dd"
            style="width: 94%"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item
          label="至"
          prop="endDte"
          class="searchClassV"
          style="margin-left: -1%"
        >
          <el-date-picker
            v-model="dataForm.endDte"
            type="date"
            placeholder="选择日期"
            value-format="yyyy-MM-dd"
            style="width: 94%"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="作业公司" prop="unitCod" class="searchClassV">
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
          class="searchClassV"
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
        <el-form-item label="内外贸" prop="tradeId" class="searchClassV">
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
        <el-form-item label="货名" prop="cargoNamCod" class="searchClassV">
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
          <!-- <el-input
            v-model="dataForm.shipName"
            placeholder="进口船名"
            @input="openShip"
          ></el-input> -->
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
      </el-form>

      <div class="avue-crud">
        <el-table
          :data="dataList"
          border
          v-loading="dataListLoading"
          :summary-method="getSummaries"
          show-summary
        >
          <el-table-column
            prop="ioyardDte"
            header-align="center"
            align="center"
            label="入库日期"
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
            prop="inShipNo"
            header-align="center"
            align="center"
            label="船舶代码"
          >
          </el-table-column>
          <el-table-column
            prop="inShipNam"
            header-align="center"
            align="center"
            label="船名"
          >
          </el-table-column>
          <el-table-column
            prop="inoutId"
            header-align="center"
            align="center"
            label="进出口"
            :formatter="inoutIdFormat"
          >
          </el-table-column>
          <el-table-column
            prop="tradeId"
            header-align="center"
            align="center"
            label="内外贸"
            :formatter="tradeIdFormat"
          >
          </el-table-column>
          <el-table-column
            prop="inPortWay"
            header-align="center"
            align="center"
            label="进出港方式"
            :formatter="inPortWayFormatter"
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
            label="库场"
          >
          </el-table-column>
          <el-table-column
            prop="consignCod"
            header-align="center"
            align="center"
            label="委托人代码"
          >
          </el-table-column>
          <el-table-column
            prop="consignNam"
            header-align="center"
            align="center"
            label="委托人代理"
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
            prop="cargoNo"
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
            prop="cargoKindNam"
            header-align="center"
            align="center"
            label="货类"
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
    </basic-container>
  </div>
</template>

<script>
import { fetchList } from '@/api/baoshuisystem/vportstock'
import {
  getTradeIdDic,
  getInoutIdDic,
  getCunitNam,
  getCargoNam,
  getSubunit,
  getShip,
  getShipperDoc,
  getInportWayAll
} from '@/api/baoshuisystem/getdictdata'
import { mapGetters } from 'vuex'
export default {
  data() {
    return {
      dataForm: {
        key: '',
        params: { startDte: '', endDte: '' }
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      unitOptions: [],
      inoutIdOptions: [],
      tradeIdtions: [],
      cargoOptions: [],
      subunitOptions: [],
      shipNamOptions: [],
      consignOptions: [],
      dataListLoading: false,
      addOrUpdateVisible: false,
      shipForm: {
        key: ''
      },
      unitOptions: [],
      shipVisible: false,
      shipNamOptions: [],
      shipSelect: [],
      shipLoading: false,
      shipPageIndex: 1,
      shipPageSize: 10,
      shipTotalPage: 0,
      buttonDisable: true,
      currentRow: null,
      inportWayOptions: [],
      dataRule: {

        unitCod: [
          { required: true, message: '作业公司不能为空', trigger: 'blur' }
        ],
        startDte: [
          { required: true, message: '开始日期不能为空', trigger: 'blur' }

        ],
        endDte: [
          { required: true, message: '结束日期不能为空', trigger: 'blur' }

        ],
      }
    }
  },
  components: {

  },
  created() {
    // this.getDataList()
    this.getDictData()
  },
  computed: {
    ...mapGetters(['permissions'])
  },
  methods: {
    // 获取数据列表
    getDataList() {
      this.dataForm.params = { startDte: this.dataForm.startDte, endDte: this.dataForm.endDte }
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
      getInportWayAll().then(resp => {
        this.inportWayOptions = resp.data.data
      })
    },
    unitCodChange() {
      console.log(this.dataForm)
      getSubunit(this.dataForm.unitCod).then(resp => {
        this.subunitOptions = resp.data.data
      })
    },
    inoutIdFormat(row, column, cellValue, index) {
      for (var i in this.inoutIdOptions) {
        if (cellValue == this.inoutIdOptions[i].value) {
          return this.inoutIdOptions[i].label
        }
      }
    },
    tradeIdFormat(row, column, cellValue, index) {
      for (var i in this.tradeIdtions) {
        if (cellValue == this.tradeIdtions[i].value) {
          return this.tradeIdtions[i].label
        }
      }
    },
    checkNull(str) {
      if (str != undefined && str != '' && str != null) {
        return true;
      } else {
        return false
      }
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
    closeShip() {
      this.shipVisible = false
      this.$set(this.dataForm, "shipNam", this.shipSelect[0].shipNam);
    },
    handleCurrentChange(val) {
      this.currentRow = val;
      this.openSing()
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
              return (prev + curr);
            } else {
              return prev;
            }
          }, 0);
          sums[index] += ' ';
        } else {
          sums[index] = '';
        }
      });
      for (var i in sums) {
        if (!isNaN(Number(sums[i])) && sums[i] != '') {
          sums[i] = Number(sums[i].trim()).toFixed(2)
          console.log("i", sums[i])
        }
      }
      console.log(sums)
      return sums;
    },
    inPortWayFormatter(row) {
      for (var i in this.inportWayOptions) {
        if (row.inPortWay == this.inportWayOptions[i].code) {
          return this.inportWayOptions[i].meaning
        }
      }
    }
  }
}
</script>
<style>
.searchClassV {
  width: 19%;
}
.searchClassV .el-form-item__content {
  width: 65% !important;
}
</style>