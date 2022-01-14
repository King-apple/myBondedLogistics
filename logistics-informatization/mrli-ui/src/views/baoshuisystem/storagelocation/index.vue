<template>
  <div class="mod-config">
    <basic-container>
      <el-form
        :inline="true"
        :model="dataForm"
        @keyup.enter.native="getDataList()"
      >
        <el-form-item label="存放位置" prop="location">
          <el-input
            v-model="dataForm.location"
            placeholder="存放位置"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            v-if="permissions.storagelocation_add"
            icon="el-icon-search"
            type="primary"
            @click="getDataList()"
            >搜索</el-button
          >
        </el-form-item>
        <div></div>
        <el-form-item>
          <el-button
            v-if="permissions.storagelocation_add"
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
            prop="location"
            header-align="center"
            align="center"
            label="存放位置"
          >
          </el-table-column>
          <el-table-column
            prop="operator"
            header-align="center"
            align="center"
            label="添加人"
          >
          </el-table-column>
          <el-table-column
            prop="recordTim"
            header-align="center"
            align="center"
            label="添加时间"
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
                v-if="permissions.storagelocation_del"
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
import { fetchList, delObj } from '@/api/baoshuisystem/storagelocation'
import TableForm from './storagelocation-form'
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
