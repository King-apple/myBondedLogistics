<template>
  <div class="mod-config">
    <basic-container>
      <el-form
        :inline="true"
        :model="dataForm"
        @keyup.enter.native="getDataList()"
      >
        <el-form-item>
          <el-button
            icon="el-icon-plus"
            type="primary"
            @click="addOrUpdateHandle()"
            >新增</el-button
          >
        </el-form-item>

        <el-upload
          :headers="headers"
          :show-file-list="false"
          :on-success="uploadSuccess"
          class="avatar-uploader"
          action="/baoshuisystem/excelUploadDownload/upload"
          :on-progress="onProgress"
          :on-change="onChange"
          v-loading="fileLoading"
        >
          <el-button size="small" type="primary">点击上传</el-button>
        </el-upload>
        <el-form-item>
          <el-button
            icon="el-icon-plus"
            type="primary"
            @click="downloadFile('保税物流中心入库单')"
            >点击下载保税物流中心入库单</el-button
          >
        </el-form-item>

        <el-form-item>
          <el-button
            icon="el-icon-plus"
            type="primary"
            @click="downloadFile('保税物流中心入库单模板')"
            >点击下载保税物流中心入库单模板</el-button
          >
        </el-form-item>
      </el-form>

      <div class="avue-crud">
        <el-table :data="dataList" border v-loading="dataListLoading">
          <el-table-column
            prop="fId"
            header-align="center"
            align="center"
            label="主键"
          >
          </el-table-column>
          <el-table-column
            prop="inOut"
            header-align="center"
            align="center"
            label="出入库"
          >
          </el-table-column>
          <el-table-column
            prop="inoutYardKnd"
            header-align="center"
            align="center"
            label="单据类型"
          >
          </el-table-column>
          <el-table-column
            prop="businessKnd"
            header-align="center"
            align="center"
            label="业务类型"
          >
          </el-table-column>
          <el-table-column
            prop="inoutYardNam"
            header-align="center"
            align="center"
            label="单据名称"
          >
          </el-table-column>
          <el-table-column
            prop="inoutYardNo"
            header-align="center"
            align="center"
            label="单据编号"
          >
          </el-table-column>
          <el-table-column
            prop="settledCompany"
            header-align="center"
            align="center"
            label="入驻企业名称"
          >
          </el-table-column>
          <el-table-column
            prop="warehouseNam"
            header-align="center"
            align="center"
            label="仓库名称"
          >
          </el-table-column>
          <el-table-column
            prop="inCustomsDeclaration"
            header-align="center"
            align="center"
            label="进区报关单号"
          >
          </el-table-column>
          <el-table-column
            prop="inCheckNum"
            header-align="center"
            align="center"
            label="进区核注清单号"
          >
          </el-table-column>
          <el-table-column
            prop="electronicAccountNum"
            header-align="center"
            align="center"
            label="电子账册号"
          >
          </el-table-column>
          <el-table-column
            prop="inCategory"
            header-align="center"
            align="center"
            label="入区类别"
          >
          </el-table-column>
          <el-table-column
            prop="customsBroker"
            header-align="center"
            align="center"
            label="报关代理企业"
          >
          </el-table-column>
          <el-table-column
            prop="consignCod"
            header-align="center"
            align="center"
            label="货主代码"
          >
          </el-table-column>
          <el-table-column
            prop="consignNam"
            header-align="center"
            align="center"
            label="货主单位"
          >
          </el-table-column>
          <el-table-column
            prop="outCheckNum"
            header-align="center"
            align="center"
            label="出区核注清单号"
          >
          </el-table-column>
          <el-table-column
            prop="outCategory"
            header-align="center"
            align="center"
            label="出区类别"
          >
          </el-table-column>
          <el-table-column
            prop="operNam"
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
            prop="updateNam"
            header-align="center"
            align="center"
            label="修改人"
          >
          </el-table-column>
          <el-table-column
            prop="updateTim"
            header-align="center"
            align="center"
            label="修改时间"
          >
          </el-table-column>
          <el-table-column
            prop="delNam"
            header-align="center"
            align="center"
            label="删除人"
          >
          </el-table-column>
          <el-table-column
            prop="delTim"
            header-align="center"
            align="center"
            label="删除时间"
          >
          </el-table-column>
          <el-table-column
            prop="customsBrokerCod"
            header-align="center"
            align="center"
            label="报关代理企业代码"
          >
          </el-table-column>
          <el-table-column
            prop="settledCod"
            header-align="center"
            align="center"
            label="入驻企业字母代码"
          >
          </el-table-column>
          <el-table-column
            prop="warehouseCod"
            header-align="center"
            align="center"
            label="仓库代码"
          >
          </el-table-column>
          <el-table-column header-align="center" align="center" label="操作">
            <template slot-scope="scope">
              <el-button
                v-if="permissions.inoutyardhead_edit"
                type="text"
                size="small"
                icon="el-icon-edit"
                @click="addOrUpdateHandle(scope.row.fId)"
                >修改</el-button
              >
              <el-button
                v-if="permissions.inoutyardhead_del"
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
    </basic-container>
  </div>
</template>

<script>
import { fetchList, delObj, fileUpload } from '@/api/baoshuisystem/excelImportExport'
import { mapGetters } from 'vuex'
import store from '@/store'
export default {
  data() {
    return {
      headers: {
        'Authorization': 'Bearer ' + store.getters.access_token,
      },
      dataForm: {
        key: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      addOrUpdateVisible: false,
      fileLoading: false
    }
  },
  components: {

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
      // fetchList(Object.assign({
      //   current: this.pageIndex,
      //   size: this.pageSize
      // })).then(response => {
      //   this.dataList = response.data.data.records
      //   this.totalPage = response.data.data.total
      // })
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

    uploadSuccess: function (e) {
      // this.fileLoading = false
      if (e.data == null) {
        console.log(e)
        this.$notify.error(e.msg)
      } else {
        console.log(e)
        this.$message.success(e.data)
      }

    },
    onProgress(event, file, fileList) {
      // this.$notify.error("开始上传")
      // this.fileLoading = true
    },
    onChange(file, fileList) {
      this.fileLoading = !this.fileLoading
    },
    downloadFile: function (filename) {

      // expoprtExcel({ fId: 59, inoutYardNam: "保税物流中心入库单", operNam: '模板' })
    },
  }
}
</script>
