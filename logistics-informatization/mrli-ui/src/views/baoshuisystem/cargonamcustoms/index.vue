<template>
  <div class="mod-config">
    <basic-container>
      <el-form
        :inline="true"
        :model="dataForm"
        @keyup.enter.native="getDataList()"
      >
        <el-form-item label="商品编码" prop="cargoCod">
          <el-input
            v-model="dataForm.cargoCod"
            placeholder="商品编码"
          ></el-input>
        </el-form-item>
        <el-form-item label="货品名" prop="cargoNam">
          <el-input
            v-model="dataForm.cargoNam"
            placeholder="货品名"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            v-if="permissions.cargonamcustoms_view"
            icon="el-icon-search"
            type="primary"
            @click="getDataList()()"
            >查询</el-button
          >
          <el-button 
          icon="el-icon-delete" 
          type="warning" 
          @click="deleteInput()"
            >清空</el-button
          >
        </el-form-item>
        <br>
        <el-form-item>
          <el-button
            v-if="permissions.cargonamcustoms_add"
            icon="el-icon-plus"
            type="primary"
            @click="addOrUpdateHandle()"
            >新增</el-button
          >
        </el-form-item>
      </el-form>

      <div class="avue-crud" style="width:100%">
        <el-table :data="dataList" border v-loading="dataListLoading">
          <el-table-column
            prop="cargoCod"
            header-align="center"
            align="center"
            label="商品编码"
          >
          </el-table-column>
          <el-table-column
            prop="cargoNam"
            header-align="center"
            align="center"
            label="货品名"
          >
          </el-table-column>
          <el-table-column
            prop="markTxt"
            header-align="center"
            align="center"
            label="备注"
          >
          </el-table-column>

          <el-table-column header-align="center" align="center" label="操作">
            <template slot-scope="scope">
              <el-button
                v-if="permissions.cargonamcustoms_del"
                type="text"
                size="small"
                icon="el-icon-delete"
                @click="deleteHandle(scope.row)"
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
import { fetchList, delObj, getAll } from '@/api/baoshuisystem/cargonamcustoms'
import TableForm from './cargonamcustoms-form'
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
      this.$confirm('是否确认删除' + id.cargoNam, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function () {
        console.log(id);
        return delObj(id.fId)
      }).then(data => {
        this.$message.success('删除成功')
        this.getDataList()
      })
    },
    deleteInput() {
      this.dataForm.cargoNam = null
      this.dataForm.cargoCod = null
    }
  }
}
</script>
