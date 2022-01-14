<template>
  <div class="mod-config">
    <basic-container>
      <el-form
        :inline="true"
        :model="dataForm"
        @keyup.enter.native="getDataList()"
      >
        <el-form-item label="委托日期" prop="recordTim">
          <el-date-picker
            v-model="dataForm.recordTim"
            type="date"
            placeholder="选择日期"
            value-format="yyyy-MM-dd"
            style="width: 94%"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="进口船名" prop="shipNam">
          <el-input
            v-model="dataForm.shipNam"
            placeholder="进口船名"
            @focus="openShip"
          ></el-input>
        </el-form-item>
        <el-form-item label="公司" prop="unitCod" class="searchClass">
          <el-select
            v-model="dataForm.unitCod"
            placeholder="请选择"
            filterable
            clearable
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
        <el-form-item>
          <el-button type="primary" @click="getDataList">查询</el-button>
        </el-form-item>
      </el-form>

      <div class="avue-crud">
        <el-table
          :data="dataList"
          border
          v-loading="dataListLoading"
          @current-change="handleCurrentChange"
        >
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-row>
                  <el-col :span="20">
                    <el-form-item label="货运员备注:" style="width: 60%">
                      <span>{{ props.row.cargoTxt }}</span>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="20">
                    <el-form-item label="理货站备注:">
                      <span>{{ props.row.tallyTxt }}</span>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form>
            </template>
          </el-table-column>
          <!-- <el-table-column
            prop="shipNo"
            header-align="center"
            align="center"
            label="船号"
          >
          </el-table-column> -->
          <el-table-column
            prop="shipNam"
            header-align="center"
            align="center"
            label="船名"
          >
          </el-table-column>
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
            prop="inoutId"
            header-align="center"
            align="center"
            :formatter="inoutIdFormat"
            label="进出口"
          >
          </el-table-column>
          <el-table-column
            prop="tradeId"
            header-align="center"
            align="center"
            :formatter="tradeIdFormat"
            label="内外贸"
          >
          </el-table-column>
          <el-table-column
            prop="recordTim"
            header-align="center"
            align="center"
            label="记录时间"
          >
          </el-table-column>
          <el-table-column
            prop="cargoMan"
            header-align="center"
            align="center"
            label="货运员"
          >
          </el-table-column>
          <el-table-column
            prop="endTim"
            header-align="center"
            align="center"
            label="完工时间"
          >
          </el-table-column>
          <!-- <el-table-column
            prop="cargoManCod"
            header-align="center"
            align="center"
            label="货运员代码"
          >
          </el-table-column> -->

          <!-- <el-table-column
            prop="tallyMan"
            header-align="center"
            align="center"
            label="理货员"
          >
          </el-table-column> -->

          <!-- <el-table-column
            prop="toPortTim"
            header-align="center"
            align="center"
            label="到港时间"
          >
          </el-table-column> -->
          <el-table-column
            prop="fromPortNam"
            header-align="center"
            align="center"
            label="来港"
          >
          </el-table-column>
          <el-table-column
            prop="toPortNam"
            header-align="center"
            align="center"
            label="到港"
          >
          </el-table-column>

          <!-- <el-table-column
            prop="operNam"
            header-align="center"
            align="center"
            label="操作员"
          >
          </el-table-column>
          <el-table-column
            prop="updateTim"
            header-align="center"
            align="center"
            label="最终修改时间"
          >
          </el-table-column>
          <el-table-column
            prop="updateNam"
            header-align="center"
            align="center"
            label="最终修改人"
          >
          </el-table-column>
          <el-table-column
            prop="tallyManCod"
            header-align="center"
            align="center"
            label="理货员代码"
          >
          </el-table-column> -->

          <!-- <el-table-column
            prop="countId"
            header-align="center"
            align="center"
            label="合同统计（0否1是）"
          >
          </el-table-column> -->
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

      <el-dialog
        title="单船登记帐查询"
        :close-on-click-modal="false"
        :visible="singVisible"
        width="80%"
        @close="singVisible = false"
      >
        <!-- <el-form :inline="true" :model="singForm">
          <el-form-item label="进口船名" prop="shipNam">
            <el-input
              v-model="singForm.shipNam"
              placeholder="进口船名"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button
              icon="el-icon-search"
              type="primary"
              @click="getSingList()"
              >搜索</el-button
            >
          </el-form-item>
        </el-form> -->
        <el-table
          highlight-current-row
          :data="singNamOptions"
          border
          v-loading="singLoading"
          height="500px"
        >
          <el-table-column type="selection"> </el-table-column>
          <el-table-column
            prop="contNo"
            header-align="center"
            align="center"
            label="合同号"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="trustMan"
            header-align="center"
            align="center"
            label="作业委托人"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="billNo"
            header-align="center"
            align="center"
            label="运提单号"
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
            prop="cPkgKindNam"
            header-align="center"
            align="center"
            label="包装"
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
            prop="markCod"
            header-align="center"
            align="center"
            label="标记"
          >
          </el-table-column>
          <el-table-column
            prop="markKnd"
            header-align="center"
            align="center"
            label="商检报检单号"
          >
          </el-table-column>
          <el-table-column
            prop="countWay"
            header-align="center"
            align="center"
            label="计重方式"
          >
          </el-table-column>
          <el-table-column
            prop="poundNo"
            header-align="center"
            align="center"
            label="磅房"
          >
          </el-table-column>
          <el-table-column
            prop="checkId"
            header-align="center"
            align="center"
            label="商检"
          >
          </el-table-column>
          <el-table-column
            prop="workPlace"
            header-align="center"
            align="center"
            label="堆存地点"
          >
          </el-table-column>
          <el-table-column
            prop="subunitNam"
            header-align="center"
            align="center"
            label="作业库区"
          >
          </el-table-column>
          <el-table-column
            prop="portWay"
            header-align="center"
            align="center"
            label="集疏港方式"
          >
          </el-table-column>
          <el-table-column
            prop="trafficPlan"
            header-align="center"
            align="center"
            label="运力安排"
          >
          </el-table-column>
          <el-table-column
            prop="piecesNum"
            header-align="center"
            align="center"
            label="委托件数"
          >
          </el-table-column>
          <el-table-column
            prop="weightWgt"
            header-align="center"
            align="center"
            label="委托重量"
          >
          </el-table-column>
          <el-table-column
            prop="cargoVol"
            header-align="center"
            align="center"
            label="委托体积"
          >
          </el-table-column>
          <el-table-column
            prop="workWayCod"
            header-align="center"
            align="center"
            label="作业方式代码"
          >
          </el-table-column>
          <el-table-column
            prop="workWay"
            header-align="center"
            align="center"
            label="作业方式"
          >
          </el-table-column>
          <el-table-column
            prop="addMinRat"
            header-align="center"
            align="center"
            label="正负范围"
          >
          </el-table-column>
          <el-table-column
            prop="endPiecesNum"
            header-align="center"
            align="center"
            label="完成件数"
          >
          </el-table-column>
          <el-table-column
            prop="endWeightWgt"
            header-align="center"
            align="center"
            label="完成重量"
          >
          </el-table-column>
          <el-table-column
            prop="endCargoVol"
            header-align="center"
            align="center"
            label="完成体积"
          >
          </el-table-column>
          <el-table-column
            prop="endTim"
            header-align="center"
            align="center"
            label="完工时间"
          >
          </el-table-column>
          <el-table-column
            prop="endTim"
            header-align="center"
            align="center"
            label="完工时间"
          >
          </el-table-column>
          <el-table-column
            prop="setownerNam"
            header-align="center"
            align="center"
            label="收发货人"
          >
          </el-table-column>
          <el-table-column
            prop="toPortCod"
            header-align="center"
            align="center"
            label="到达港区代码"
          >
          </el-table-column>
          <el-table-column
            prop="toPortNam"
            header-align="center"
            align="center"
            label="到达港区"
          >
          </el-table-column>
        </el-table>
        <div style="float: right; height: 100px; margin-top: 10px">
          <el-button @click="singVisible = false">取消</el-button>

          <el-button
            type="primary"
            @click="closeSing"
            :disabled="singButtonDisable"
            >确定</el-button
          >
        </div>

        <div class="avue-crud__pagination" style="margin-top: 50px">
          <el-pagination
            @size-change="singSizeChangeHandle"
            @current-change="singCurrentChangeHandle"
            :current-page="singPageIndex"
            :page-sizes="[10, 20, 50, 100]"
            :page-size="singPageSize"
            :total="singTotalPage"
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
import { fetchList } from '@/api/baoshuisystem/singship'
import { fetchList as shiptonList } from '@/api/baoshuisystem/shipton'
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
      singForm: {
        key: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      addOrUpdateVisible: false,
      unitOptions: [],
      tradeIdtions: [],
      inoutIdOptions: [],
      shipVisible: false,
      shipNamOptions: [],
      shipSelect: [],
      shipLoading: false,

      shipPageIndex: 1,
      shipPageSize: 10,
      shipTotalPage: 0,
      buttonDisable: true,
      currentRow: null,
      singVisible: false,
      singNamOptions: [],
      singLoading: false,
      singButtonDisable: true,
      singPageIndex: 1,
      singPageSize: 10,
      singTotalPage: 0,
    }
  },
  components: {

  },
  created() {
    this.getDataList()
    this.getDictData()
  },
  computed: {
    ...mapGetters(['permissions'])
  },
  methods: {
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true
      fetchList(Object.assign({
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
      this.openSing(val)
    },

    // 每页数
    singSizeChangeHandle(val) {
      this.shipPageSize = val
      this.shipPageIndex = 1
      this.getSingList()
    },
    // 当前页
    singCurrentChangeHandle(val) {
      this.shipPageIndex = val
      this.getSingList()
    },
    getSingList(val) {
      this.singLoading = true
      this.singForm.inoutId = val.inoutId
      this.singForm.shipNo = val.shipNo
      this.singForm.unitCod = val.unitCod
      this.singForm.recordTim = val.recordTim
      shiptonList(Object.assign({
        current: this.shipPageIndex,
        size: this.shipPageSize
      }, this.singForm)).then(response => {
        this.singNamOptions = response.data.data.records
        this.singLoading = false
        this.singTotalPage = response.data.data.total
      })

    },
    openSing(val) {
      this.singVisible = true
      this.getSingList(val);
    },
    closeSing() {
      this.singVisible = false
      // this.$set(this.dataForm, "shipNam", this.shipSelect[0].shipNam);
    },
    // 删除
    // deleteHandle(id) {
    //   this.$confirm('是否确认删除ID为' + id, '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning'
    //   }).then(function () {
    //     return delObj(id)
    //   }).then(data => {
    //     this.$message.success('删除成功')
    //     this.getDataList()
    //   })
    // }
  }
}
</script>
