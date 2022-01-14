<template>
  <div class="mod-config">
    <basic-container>
      <el-form
        :inline="true"
        :model="dataForm"
        @keyup.enter.native="getDataList()"
        label-position="top"
        class="formTab"
        ref="dataForm"
      >
        <el-form-item label="衡重单号" prop="checkNo">
          <el-input
            v-model="dataForm.checkNo"
            placeholder="衡重单号"
          ></el-input>
        </el-form-item>
        <el-form-item label="货名" prop="cargoNam">
          <el-input v-model="dataForm.cargoNam" placeholder="货名"></el-input>
        </el-form-item>
        <el-form-item label="出入库单号" prop="inoutYardNo">
          <el-input
            v-model="dataForm.inoutYardNo"
            placeholder="出入库单号"
          ></el-input>
        </el-form-item>
        <el-form-item label="申请单位" prop="requireUnitNam">
          <el-input
            v-model="dataForm.requireUnitNam"
            placeholder="申请单位"
          ></el-input>
        </el-form-item>
        <el-form-item label="审核状态" prop="checkStatus">
          <el-select
            v-model="dataForm.checkStatus"
            placeholder="请选择"
            filterable
            clearable
          >
            <el-option
              v-for="(item, index) in checkStatusOptions"
              :key="index"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="衡重单日期" prop="params.recordDte">
          <el-date-picker
            v-model="dataForm.params.recordDte"
            type="datetimerange"
            :picker-options="pickerOptions"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            align="right"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getDataList">查询</el-button>
          <el-button type="warning" @click="resetForm('dataForm')"
            >重置</el-button
          >
          <el-button type="success" @click="check(1)" :disabled="singleSelect"
            >审核通过</el-button
          >
          <el-button type="danger" @click="check(2)" :disabled="singleSelect"
            >审核驳回</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button
            v-if="permissions.checkweight_add"
            icon="el-icon-plus"
            type="primary"
            @click="addOrUpdateHandle()"
            >新增</el-button
          >
        </el-form-item>
      </el-form>

      <div class="avue-crud" style="width: 100%">
        <el-table
          :data="dataList"
          border
          v-loading="dataListLoading"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection"> </el-table-column>
          <el-table-column
            prop="checkNo"
            header-align="center"
            align="center"
            label="衡重单号"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="checkStatus"
            header-align="center"
            align="center"
            label="审核状态"
            sortable
            :formatter="checkStatusFormatter"
          >
          </el-table-column>
          <el-table-column
            prop="recordDte"
            header-align="center"
            align="center"
            label="衡重单日期"
            sortable
          >
          </el-table-column>

          <el-table-column
            prop="trustNo"
            header-align="center"
            align="center"
            label="委托单号"
          >
          </el-table-column>
          <el-table-column
            prop="cargoNo"
            header-align="center"
            align="center"
            label="货号"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="cargoNam"
            header-align="center"
            align="center"
            label="货名"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="cargoKindCod"
            header-align="center"
            align="center"
            label="货类代码"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="feeWayId"
            header-align="center"
            align="center"
            label="结算方式代码"
          >
          </el-table-column>
          <el-table-column
            prop="feeWayNam"
            header-align="center"
            align="center"
            label="结算方式"
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
            prop="berthCod"
            header-align="center"
            align="center"
            label="泊位"
          >
          </el-table-column>
          <el-table-column
            prop="inoutId"
            header-align="center"
            align="center"
            label="进出口"
          >
          </el-table-column>
          <el-table-column
            prop="tradeId"
            header-align="center"
            align="center"
            label="内外贸"
          >
          </el-table-column>
          <el-table-column
            prop="billNo"
            header-align="center"
            align="center"
            label="提单号"
          >
          </el-table-column>
          <el-table-column
            prop="piecesNum"
            header-align="center"
            align="center"
            label="件数"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="weightWgt"
            header-align="center"
            align="center"
            label="重量"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="wgtUnit"
            header-align="center"
            align="center"
            label="重量单位"
          >
          </el-table-column>
          <el-table-column
            prop="addMinRat"
            header-align="center"
            align="center"
            label="上下限额"
          >
          </el-table-column>
          <el-table-column
            prop="toPlaceNam"
            header-align="center"
            align="center"
            label="运往地点"
          >
          </el-table-column>
          <el-table-column
            prop="checkId"
            header-align="center"
            align="center"
            label="检车标记"
          >
          </el-table-column>
          <el-table-column
            prop="requireUnitNam"
            header-align="center"
            align="center"
            label="申请单位"
          >
          </el-table-column>

          <el-table-column
            prop="agentNam"
            header-align="center"
            align="center"
            label="代理公司"
          >
          </el-table-column>
          <el-table-column
            prop="workPlace"
            header-align="center"
            align="center"
            label="作业地点"
          >
          </el-table-column>

          <el-table-column
            prop="unitNam"
            header-align="center"
            align="center"
            label="单位名称"
            sortable
          >
          </el-table-column>

          <el-table-column
            prop="subunitNam"
            header-align="center"
            align="center"
            label="科室队名称"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="ifendId"
            header-align="center"
            align="center"
            label="完标"
          >
          </el-table-column>
          <el-table-column
            prop="remarkTxt"
            header-align="center"
            align="center"
            label="备注"
          >
          </el-table-column>
          <el-table-column
            prop="operNam"
            header-align="center"
            align="center"
            label="操作员"
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
            prop="countWayCod"
            header-align="center"
            align="center"
            label="计重方式代码"
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
            prop="trainNo"
            header-align="center"
            align="center"
            label="车次号"
          >
          </el-table-column>
          <el-table-column
            prop="trainCod"
            header-align="center"
            align="center"
            label="车皮号"
          >
          </el-table-column>
          <el-table-column
            prop="inoutYardNo"
            header-align="center"
            align="center"
            label="出入库单号"
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
                v-if="permissions.checkweight_edit"
                type="text"
                size="small"
                icon="el-icon-edit"
                @click="addOrUpdateCheckHandle(scope.row)"
                >修改</el-button
              >
              <el-button
                v-if="permissions.checkweight_del"
                type="text"
                size="small"
                icon="el-icon-delete"
                @click="deleteHandle(scope.row.checkNo)"
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
import { fetchList, delObj, putObj } from '@/api/baoshuisystem/checkweight'
import { getCheckStatusDic } from '@/api/baoshuisystem/getdictdata'
import TableForm from './checkweight-form'
import { mapGetters } from 'vuex'
export default {
  data() {
    return {
      dataForm: {
        key: '',
        params: { recordDte: '' },
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      addOrUpdateVisible: false,
      multipleSelection: [],
      singleSelect: true,
      checkStatusOptions: [],
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
    }
  },
  components: {
    TableForm
  },
  created() {
    this.getDataList()
    getCheckStatusDic().then(resp => {
      this.checkStatusOptions = resp.data.data
    })
  },
  computed: {
    ...mapGetters(['permissions', 'userInfo'])
  },
  methods: {
    // 获取数据列表
    getDataList() {
      console.log(this.userInfo)
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
    // 新增 / 修改
    addOrUpdateCheckHandle(row) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(row)
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
    check(status) {
      var now = new Date();
      var s1 = now.getFullYear() + "-" + now.getMonth() + "-" + now.getDate() + " " + now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds();
      var map = {
        checkNo: this.multipleSelection[0].checkNo,
        checkStatus: status,
        checkUser: this.userInfo.username,
        checkTime: s1
      }
      putObj(map).then(data => {
        this.$notify.success('修改成功')
        this.getDataList()
      }).catch(() => {
        this.getDataList()
      });
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      if (this.multipleSelection.length == 1 && (this.multipleSelection[0].checkStatus == undefined || this.multipleSelection[0].checkStatus == '' || this.multipleSelection[0].checkStatus == 0)) {
        this.singleSelect = false
      } else {
        this.singleSelect = true
      }
    },
    checkStatusFormatter(row, column, cellValue, index) {
      if (cellValue == undefined || cellValue == 0 || cellValue == '') {
        return '未审核'
      } else if (cellValue == 1) {
        return '审核通过'
      } else if (cellValue == 2) {
        return '审核失败'
      }
    }
  }
}
</script>
<style>
.mod-config .formTab .el-form-item__content {
  display: inline-block;
}
.formTab .el-form-item__label {
  padding: 0 12px 0 0;
}
</style>
