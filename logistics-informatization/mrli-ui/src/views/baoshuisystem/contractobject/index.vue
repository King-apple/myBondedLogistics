<template>
  <div class="mod-config">
    <basic-container>
      <el-form :inline="true" :model="contractForm">
        <el-form-item label="货名:" prop="cargoNam">
          <el-input
            v-model="contractForm.cargoNam"
            placeholder="货名"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-search" type="primary" @click="getDataList()"
            >查询</el-button
          >
          <el-button icon="el-icon-delete" type="warning" @click="deleteInput()"
            >清空</el-button
          >
        </el-form-item>
      </el-form>

      <el-form
        :inline="true"
        :model="dataForm"
        @keyup.enter.native="getDataList()"
      >
        <el-form-item>
          <el-button
            v-if="permissions.contractobject_add"
            icon="el-icon-plus"
            type="primary"
            @click="addOrUpdateHandle()"
            >新增</el-button
          >
        </el-form-item>
      </el-form>

      <div class="avue-crud">
        <el-table :data="dataList" border v-loading="dataListLoading">
          <el-table-column
            type="index"
            header-align="center"
            align="center"
            label="序号"
          >
          </el-table-column>
          <el-table-column
            prop="cargoKindCod"
            header-align="center"
            align="center"
            label="货类编码"
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
            prop="cargoCod"
            header-align="center"
            align="center"
            label="货物编码"
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
            prop="markTxt"
            header-align="center"
            align="center"
            label="备注"
            sortable
          >
          </el-table-column>
          <!-- <el-table-column
            prop="operNam"
            header-align="center"
            align="center"
            label="添加人"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="recordTim"
            header-align="center"
            align="center"
            label="添加时间"
            sortable
          > 
          </el-table-column>-->
          <el-table-column header-align="center" align="center" label="操作">
            <template slot-scope="scope">
              <el-button
                v-if="permissions.contractobject_edit"
                type="text"
                size="small"
                icon="el-icon-edit"
                @click="addOrUpdateHandle(scope.row.fid)"
                >修改</el-button
              >
              <el-button
                v-if="permissions.contractobject_del"
                type="text"
                size="small"
                icon="el-icon-delete"
                @click="deleteHandle(scope.row.fid)"
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
import { fetchList, delObj } from '@/api/baoshuisystem/contractobject'
import TableForm from './contractobject-form'
import { mapGetters } from 'vuex'
export default {
  data() {
    return {
      contractForm: {
        contractObject: null
      },
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
  created() {
    this.getDataList()
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
      },
        this.contractForm
      )).then(response => {
        this.dataList = response.data.data.records
        this.totalPage = response.data.data.total
      })
      console.log(this.contractForm)
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
    deleteHandle(id, contractObject) {
      this.$confirm('是否确认删除此条信息', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function () {
        return delObj(id)
      }).then(data => {
        this.$message.success('删除成功')
        this.getDataList()
      },
      )
    },
    deleteInput() {
      this.contractForm.cargoNam = null
    }
  }
}
</script>
