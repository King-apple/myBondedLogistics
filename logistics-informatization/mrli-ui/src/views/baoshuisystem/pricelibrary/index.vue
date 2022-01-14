<template>
  <div class="mod-config">
    <basic-container>
      <el-form
        :inline="true"
        :model="dataForm"
        @keyup.enter.native="getDataList()"
        ref="dataForm"
      >
        <el-form-item label="客户名称" prop="company">
          <el-select
            v-model="dataForm.company"
            placeholder="请选择"
            filterable
            clearable
            style="width: 100%"
          >
            <el-option
              v-for="(item, index) in companyOptions"
              :key="index"
              :label="item.company"
              :value="item.company"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="货名" prop="cargoNam">
          <el-select
            v-model="dataForm.cargoNam"
            placeholder="货名"
            filterable
            clearable
            style="width: 100%"
          >
            <el-option
              v-for="(item, index) in cargoNamOptions"
              :key="index"
              :label="item.cargoNam"
              :value="item.cargoNam"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="货类" prop="cargoKindNam">
          <el-select
            v-model="dataForm.cargoKindNam"
            placeholder="货类"
            filterable
            clearable
            style="width: 100%"
          >
            <el-option
              v-for="(item, index) in carGoKindOptions"
              :key="index"
              :label="item.cargoKindNam"
              :value="item.cargoKindNam"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="费目" prop="feeNam">
          <el-select
            v-model="dataForm.feeNam"
            placeholder="费目"
            filterable
            clearable
            style="width: 100%"
          >
            <el-option
              v-for="(item, index) in feeNameList"
              :key="index"
              :label="item.feeNam"
              :value="item.feeNam"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="存放地点" prop="locate">
          <el-select
            v-model="dataForm.locate"
            placeholder="堆存地点"
            filterable
            clearable
            style="width: 100%"
          >
            <el-option
              v-for="(item, index) in portLocationOptions"
              :key="index"
              :label="item.location"
              :value="item.location"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button
            v-if="permissions.pricelibrary_add"
            icon="el-icon-search"
            type="primary"
            @click="getDataList()"
            >搜索</el-button
          >
          <el-button type="warning" @click="resetForm('dataForm')"
            >重置</el-button
          >
        </el-form-item>
        <br />
        <el-form-item>
          <el-button
            v-if="permissions.pricelibrary_add"
            icon="el-icon-plus"
            type="primary"
            @click="addOrUpdateHandle()"
            >新增</el-button
          >
        </el-form-item>
      </el-form>

      <div class="avue-crud" style="width: 100%">
        <el-table :data="dataList" border v-loading="dataListLoading">
          <el-table-column
            prop="company"
            header-align="center"
            align="center"
            label="客户名称"
            sortable
          >
          </el-table-column>

          <el-table-column
            prop="cargoNam"
            header-align="center"
            align="center"
            label="货物名称"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="cargoKindNam"
            header-align="center"
            align="center"
            label="货类名称"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="format"
            header-align="center"
            align="center"
            label="规格型号"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="pkgKindNam"
            header-align="center"
            align="center"
            label="包装"
            sortable
          >
          </el-table-column>

          <el-table-column
            prop="locate"
            header-align="center"
            align="center"
            label="存放地点"
            sortable
          >
          </el-table-column>

          <el-table-column
            prop="tradeId"
            header-align="center"
            align="center"
            label="内外贸"
            :formatter="tradeIdFormat"
            sortable
          >
          </el-table-column>

          <el-table-column
            prop="inoutId"
            header-align="center"
            align="center"
            label="进出口"
            :formatter="inoutIdFormat"
            sortable
          >
          </el-table-column>

          <el-table-column
            prop="feeNam"
            header-align="center"
            align="center"
            label="费目"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="feeRat"
            header-align="center"
            align="center"
            label="费率值"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="feeUnit"
            header-align="center"
            align="center"
            label="费率单位"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="locate"
            header-align="center"
            align="center"
            label="存放地点"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="tradeId"
            header-align="center"
            align="center"
            :formatter="tradeIdFormat"
            label="内外贸"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="inoutId"
            header-align="center"
            align="center"
            :formatter="inoutIdFormat"
            label="进出口"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="markTxt"
            header-align="center"
            align="center"
            label="备注"
          >
          </el-table-column>

          <el-table-column
            header-align="center"
            align="center"
            fixed="right"
            label="操作"
          >
            <template slot-scope="scope">
              <el-button
                v-if="permissions.pricelibrary_edit"
                type="text"
                size="small"
                icon="el-icon-edit"
                @click="addOrUpdateHandle(scope.row.fId)"
                >修改</el-button
              >
              <el-button
                v-if="permissions.pricelibrary_del"
                type="text"
                size="small"
                icon="el-icon-delete"
                @click="deleteHandle(scope.row.fId)"
                >删除</el-button
              >
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
    </basic-container>
  </div>
</template>

<script>
import { getInoutIdDic, getTradeIdDic } from '@/api/baoshuisystem/getdictdata'
import { fetchList, delObj } from '@/api/baoshuisystem/pricelibrary'
import { getClientInfoAll } from '@/api/baoshuisystem/clientinfo'
import { getContractObjectInfoAll } from '@/api/baoshuisystem/contractobject'
import { all as carGoKindAll } from '@/api/baoshuisystem/goodscategory'
import { getAll as storagelocationAll } from '@/api/baoshuisystem/storagelocation'
import { getFeenamesAll } from '@/api/baoshuisystem/feenames'

import TableForm from './pricelibrary-form'
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
      tradeIdDicOptions: [],
      inoutIdDicOptions: [],
      companyOptions: [],
      cargoNamOptions: [],
      carGoKindOptions: [],
      portLocationOptions: [],
      feeNameList: [],
    }
  },
  components: {
    TableForm
  },
  created() {
    this.getDataList()
    this.getTradeIdDicOptions()
    this.getInoutIdDicOptions()
    this.getClientInfoOptions()
    this.getContractObjectInfOptions()
    this.getcarGoKindOptions()
    this.getAllLocation()
    this.getFeenamesAllOptions()
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
    resetForm(formName) {
      if (this.$refs[formName] != undefined) {
        this.$refs[formName].resetFields();
      }
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
    getInoutIdDicOptions() {
      getInoutIdDic().then(response => {
        this.inoutIdDicOptions = response.data.data
      })
    },
    getTradeIdDicOptions() {
      getTradeIdDic().then(response => {
        this.tradeIdDicOptions = response.data.data
      })
    },
    getClientInfoOptions() {
      getClientInfoAll().then(response => {
        this.companyOptions = response.data.data
      })
    },
    getContractObjectInfOptions() {
      getContractObjectInfoAll().then(response => {
        this.cargoNamOptions = response.data.data
      })
    },
    getAllLocation() {
      storagelocationAll().then(response => {
        this.portLocationOptions = response.data.data
      })
    },
    getcarGoKindOptions() {
      carGoKindAll().then(response => {
        this.carGoKindOptions = response.data.data
      })
    },
    getFeenamesAllOptions() {
      getFeenamesAll().then(response => {
        console.log(response)
        this.feeNameList = response.data.data
      })
    },
    inoutIdFormat(row, column, cellValue, index) {
      for (var i in this.inoutIdDicOptions) {
        if (cellValue == this.inoutIdDicOptions[i].value) {
          return this.inoutIdDicOptions[i].label
        }
      }
    },
    tradeIdFormat(row, column, cellValue, index) {
      for (var i in this.tradeIdDicOptions) {
        if (cellValue == this.tradeIdDicOptions[i].value) {
          return this.tradeIdDicOptions[i].label
        }
      }
    }
  }
}
</script>
