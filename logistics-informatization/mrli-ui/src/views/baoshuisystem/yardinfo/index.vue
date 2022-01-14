<template>
  <div class="mod-config">
    <basic-container>
      <el-form
        :inline="true"
        :model="dataForm"
        @keyup.enter.native="getDataList()"
      >
        <el-form-item label="库区" prop="areaNam">
          <el-select
            v-model="dataForm.areaNam"
            placeholder="请选择"
            filterable
            clearable
            style="width: 100%"
          >
            <el-option
              v-for="(item, index) in portAreaOptions"
              :key="index"
              :label="item.value"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="货票编号" prop="areaNam">
          <el-input v-model="dataForm.bh" placeholder="货票编号"></el-input>
        </el-form-item>
        <el-form-item label="作业委托人" prop="consignNam">
           <el-select
            v-model="dataForm.consignNam"
            placeholder="请选择"
            filterable
            clearable
          >
            <el-option
              v-for="(item, index) in conSignList"
              :key="index"
              :label="item.consignNam"
              :value="item.consignNam"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="货名" prop="cargoNam">
          <el-select
            v-model="dataForm.cargoNam"
            placeholder="请选择"
            filterable
            clearable
          >
            <el-option
              v-for="(item, index) in cargoList"
              :key="index"
              :label="item.cargoNam"
              :value="item.cargoNam"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button
            v-if="permissions.baoshuisystem_yardinfo_add"
            icon="el-icon-search"
            type="primary"
            @click="getDataList()"
            >查询</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button
            v-if="permissions.baoshuisystem_yardinfo_add"
            icon="el-icon-plus"
            type="primary"
            @click="addOrUpdateHandle()"
            >新增</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button
            v-if="permissions.baoshuisystem_yardinfo_add"
            icon="el-icon-download"
            type="warning"
            @click="cargoSpaceMerageDialogOpen()"
            :disabled="single"
            >货位合并</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button
            v-if="permissions.baoshuisystem_yardinfo_split"
            icon="el-icon-download"
            type="warning"
            @click="cargoSpaceSplitDialogOpen()"
            :disabled="splitSingle"
            >货位拆分</el-button
          >
        </el-form-item>
      </el-form>

      <div class="avue-crud">
        <el-table
          :data="dataList"
          border
          v-loading="dataListLoading"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55"> </el-table-column>
          <!-- <el-table-column
            prop="fId"
            header-align="center"
            align="center"
            label="主键"
            hidden="hidden"
          >
          </el-table-column> -->
          <el-table-column
            prop="inYardNo"
            header-align="center"
            align="center"
            label="入库单编号"
          >
          </el-table-column>
          <el-table-column
            prop="bh"
            header-align="center"
            align="center"
            label="货票编号"
          >
          </el-table-column>
          <el-table-column
            prop="consignNam"
            header-align="center"
            align="center"
            label="作业委托人"
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
            prop="shipNam"
            header-align="center"
            align="center"
            label="船名"
          >
          </el-table-column>
          <el-table-column
            prop="inPiecesNum"
            header-align="center"
            align="center"
            label="入库件数"
          >
          </el-table-column>
          <el-table-column
            prop="inWeightWgt"
            header-align="center"
            align="center"
            label="入库重量"
          >
          </el-table-column>
          <el-table-column
            prop="inYardDat"
            header-align="center"
            align="center"
            label="入库日期"
          >
          </el-table-column>
          <el-table-column
            prop="inSettleDat"
            header-align="center"
            align="center"
            label="入库结算日期"
          >
          </el-table-column>
          <el-table-column
            prop="outPiecesNum"
            header-align="center"
            align="center"
            label="出库件数"
          >
          </el-table-column>
          <el-table-column
            prop="outWeightWgt"
            header-align="center"
            align="center"
            label="出库重量"
          >
          </el-table-column>
          <el-table-column
            prop="surplusPiecesNum"
            header-align="center"
            align="center"
            label="剩余件数"
          >
          </el-table-column>
          <el-table-column
            prop="surplusWeightWgt"
            header-align="center"
            align="center"
            label="剩余重量"
          >
          </el-table-column>
          <el-table-column
            prop="yardNam"
            header-align="center"
            align="center"
            label="堆存地点"
          >
          </el-table-column>
          <el-table-column
            prop="areaNam"
            header-align="center"
            align="center"
            label="库区"
          >
          </el-table-column>
          <el-table-column
            prop="areaStart"
            header-align="center"
            align="center"
            label="起点"
          >
          </el-table-column>
          <el-table-column
            prop="areaEnd"
            header-align="center"
            align="center"
            label="终点"
          >
          </el-table-column>

          <el-table-column
            header-align="center"
            align="center"
            label="操作"
            fixed="right"
          >
            <template slot-scope="scope">
              <el-button
                v-if="permissions.baoshuisystem_yardinfo_edit"
                type="text"
                size="small"
                icon="el-icon-edit"
                @click="addOrUpdateHandle(scope.row.fId)"
                >货位调整</el-button
              >
              <el-button
                v-if="permissions.baoshuisystem_yardinfo_del"
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
      <el-dialog title="提示" :visible.sync="dialogVisible" width="30%">
        <el-select
          v-model="merageAreaNam"
          placeholder="请选择"
          filterable
          clearable
          style="width: 100%"
        >
          <el-option
            v-for="(item, index) in portAreaOptions"
            :key="index"
            :label="item.value"
            :value="item.value"
          >
          </el-option>
        </el-select>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="cargoSpaceMerage">确 定</el-button>
      </el-dialog>

      <el-dialog title="提示" :visible.sync="splitDialogVisible" width="30%">
        <el-form :inline="true" :model="splitDataForm">
          <el-form-item label="选择库区" prop="merageAreaNam">
            <el-select
              v-model="splitDataForm.areaNam"
              placeholder="请选择"
              filterable
              clearable
              style="width: 100%"
            >
              <el-option
                v-for="(item, index) in portAreaOptions"
                :key="index"
                :label="item.value"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="拆分件数" prop="jianshu">
            <el-input
              v-model="splitDataForm.surplusPiecesNum"
              placeholder="拆分件数"
            ></el-input>
          </el-form-item>
          <el-form-item label="拆分重量" prop="weight">
            <el-input
              v-model="splitDataForm.surplusWeightWgt"
              placeholder="拆分重量"
            ></el-input>
          </el-form-item>
        </el-form>
        <el-button @click="splitDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="cargoSpaceSplit">确 定</el-button>
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
      <table-form
        v-if="addOrUpdateVisible"
        ref="addOrUpdate"
        @refreshDataList="getDataList"
      ></table-form>
    </basic-container>
  </div>
</template>

<script>
import { fetchList, delObj, getPortArea, cargoSpaceMerage, cargoSpaceSplit,getSelectConditionInfo } from '@/api/baoshuisystem/yardinfo'
import TableForm from './yardinfo-form'
import { mapGetters } from 'vuex'
export default {
  data() {
    return {
      dataForm: {
        key: ''
      },
      splitDataForm: {
        key: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      cargoList: [],
      conSignList: [],
      portAreaOptions: [],
      dataListLoading: false,
      addOrUpdateVisible: false,
      dialogVisible: false,
      merageAreaNam: '',
      jianshu: '',
      weight: '',
      single: true,
      splitDialogVisible: false,
      splitSingle: true,
    }
  },
  components: {
    TableForm
  },
  created() {
    this.getMyPortrArea()
    this.getDataList()
    this.getSelectAll()
  },
  computed: {
    ...mapGetters(['permissions'])
  },
  methods: {
    getSelectAll() {
      getSelectConditionInfo().then(res => {
        this.cargoList = res.data.data.listCargo
        this.conSignList = res.data.data.listConSign
      })
    },
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
    getMyPortrArea() {
      getPortArea().then(response => {
        this.portAreaOptions = response.data.data
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      if (this.multipleSelection.length < 2) {
        this.single = true
      } else {
        this.single = false
      }

      if (this.multipleSelection.length == 1) {
        this.splitSingle = false
      } else {
        this.splitSingle = true
      }
    },
    cargoSpaceMerageDialogOpen() {

      this.dialogVisible = true;

    },
    cargoSpaceSplitDialogOpen() {
      this.splitDialogVisible = true
    },
    cargoSpaceMerage() {
      this.dialogVisible = false;
      if (this.merageAreaNam == '' || this.merageAreaNam == undefined) {
        this.$message.error('请选择合并后库区！')
        return;
      }
      var selection = this.multipleSelection
      for (var i in selection) {
        selection[i].areaNam = this.merageAreaNam
      }

      cargoSpaceMerage(selection).then(response => {
        this.getDataList()
      })
      this.merageAreaNam = ''
    },
    cargoSpaceSplit() {
      var selection = this.multipleSelection[0]
      this.splitDataForm.fId = selection.fId
      cargoSpaceSplit(this.splitDataForm).then(response => {
        this.splitDialogVisible = false
        this.$message.success('拆分成功')
      })
    }

  }
}
</script>
