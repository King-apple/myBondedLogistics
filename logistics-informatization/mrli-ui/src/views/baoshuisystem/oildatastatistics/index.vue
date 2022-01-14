<template>
  <div class="mod-config">
    <basic-container>
      <el-form
        :inline="true"
        :model="dataForm"
        @keyup.enter.native="getDataList()"
        ref="dataForm"
      >
        <el-form-item label="油品类型" prop="oilKnd">
          <el-input v-model="dataForm.oilKnd" placeholder="油品类型"></el-input>
        </el-form-item>
        <el-form-item label="登录账号" prop="loginAccount">
          <el-input
            v-model="dataForm.loginAccount"
            placeholder="登录账号"
          ></el-input>
        </el-form-item>
        <el-form-item label="入库货主单位" prop="inConsignNam">
          <el-input
            v-model="dataForm.inConsignNam"
            placeholder="入库货主单位"
          ></el-input>
        </el-form-item>
        <el-form-item label="出库货主单位" prop="outConsignNam">
          <el-input
            v-model="dataForm.outConsignNam"
            placeholder="出库货主单位"
          ></el-input>
        </el-form-item>
        <el-form-item label="入库商品名称" prop="inCargoNam">
          <el-input
            v-model="dataForm.inCargoNam"
            placeholder="入库商品名称"
          ></el-input>
        </el-form-item>
        <el-form-item label="出库商品名称" prop="outCargoNam">
          <el-input
            v-model="dataForm.outCargoNam"
            placeholder="出库商品名称"
          ></el-input>
        </el-form-item>
        <el-form-item label="入库报送日期范围" prop="inAddDteStr">
          <el-date-picker
            v-model="dataForm.inAddDteStr"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            align="right"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="出库报送日期范围" prop="outAddDteStr">
          <el-date-picker
            v-model="dataForm.outAddDteStr"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            align="right"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button
            type="success"
            icon="el-icon-search"
            @click="getDataListQuery"
            >查询</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button
            type="warning"
            icon="el-icon-refresh"
            @click="resetForm('dataForm')"
            >重置</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button
            v-if="permissions.oildatastatistics_add"
            icon="el-icon-plus"
            type="primary"
            @click="addOrUpdateHandle()"
            >新增</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button
            v-if="permissions.oildatastatistics_export"
            icon="el-icon-download"
            type="warning"
            @click="exportCheck()"
            >导出</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-upload
          	v-if="permissions.oildatastatistics_import"
            :headers="headers"
            :show-file-list="false"
            :on-success="uploadSuccess"
            class="avatar-uploader"
            action="/baoshuisystem/oildatastatistics/import"
            :on-progress="onProgress"
            :on-change="onChange"
            v-loading="fileLoading"
          >
            <el-button icon="el-icon-upload2" size="small" type="success"
              >导入</el-button
            >
          </el-upload>
        </el-form-item>
        <el-form-item>
          <el-button
          	v-if="permissions.oildatastatistics_import"
            icon="el-icon-download"
            type="warning"
            @click="downloadTemplateMethod()"
            >下载导入模板</el-button
          >
        </el-form-item>
      </el-form>

      <div class="avue-crud" style="width: 100%">
        <el-table
          :data="dataList"
          border
          :summary-method="getSummaries"
          show-summary
          v-loading="dataListLoading"
        >
          <el-table-column
            prop="oilKnd"
            header-align="center"
            align="center"
            label="油品类型"
          >
          </el-table-column>
          <el-table-column
            prop="loginAccount"
            header-align="center"
            align="center"
            label="登录账号"
          >
          </el-table-column>
          <el-table-column
            prop="inAccountNum"
            header-align="center"
            align="center"
            label="账册号"
          >
          </el-table-column>
          <el-table-column
            prop="inOperationUnit"
            header-align="center"
            align="center"
            label="经营单位"
          >
          </el-table-column>
          <el-table-column
            prop="inOperationUnitCod"
            header-align="center"
            align="center"
            label="代码"
          >
          </el-table-column>
          <el-table-column
            prop="inUpdateDte"
            header-align="center"
            align="center"
            label="更新日期"
          >
          </el-table-column>
          <el-table-column
            prop="inAddDte"
            header-align="center"
            align="center"
            label="入库报送日期"
          >
          </el-table-column>
          <el-table-column
            prop="inConsignNam"
            header-align="center"
            align="center"
            label="货主单位"
          >
          </el-table-column>
          <el-table-column
            prop="inCargoNam"
            header-align="center"
            align="center"
            label="商品名称"
          >
          </el-table-column>
          <el-table-column
            prop="inType"
            header-align="center"
            align="center"
            label="入库方式"
          >
          </el-table-column>
          <el-table-column
            prop="inBlno"
            header-align="center"
            align="center"
            label="提单号"
          >
          </el-table-column>
          <el-table-column
            prop="inStorageTank"
            header-align="center"
            align="center"
            label="储罐名称"
          >
          </el-table-column>
          <el-table-column
            prop="inQuantityPlan"
            header-align="center"
            align="center"
            label="计划数量（千克）"
          >
          </el-table-column>
          <el-table-column
            prop="inDensityStandard"
            header-align="center"
            align="center"
            label="密度标准"
          >
          </el-table-column>
          <el-table-column
            prop="inDensity"
            header-align="center"
            align="center"
            label="密度值"
          >
          </el-table-column>
          <el-table-column
            prop="inStartTim"
            header-align="center"
            align="center"
            label="计划开始时间"
          >
          </el-table-column>
          <el-table-column
            prop="inEndTim"
            header-align="center"
            align="center"
            label="计划结束时间"
          >
          </el-table-column>
          <el-table-column
            prop="inConfirmTim"
            header-align="center"
            align="center"
            label="进仓确认报送时间"
          >
          </el-table-column>
          <el-table-column
            prop="inQuantityReal"
            header-align="center"
            align="center"
            label="实际数量（千克）"
          >
          </el-table-column>
          <el-table-column
            prop="inCustomsDeclarationNum"
            header-align="center"
            align="center"
            label="进仓报关单号"
          >
          </el-table-column>
          <el-table-column
            prop="outAccountNum"
            header-align="center"
            align="center"
            label="账册号"
          >
          </el-table-column>
          <el-table-column
            prop="outOperationUnit"
            header-align="center"
            align="center"
            label="经营单位"
          >
          </el-table-column>
          <el-table-column
            prop="outOperationUnitCod"
            header-align="center"
            align="center"
            label="代码"
          >
          </el-table-column>
          <el-table-column
            prop="outUpdateDte"
            header-align="center"
            align="center"
            label="更新日期"
          >
          </el-table-column>
          <el-table-column
            prop="outAddDte"
            header-align="center"
            align="center"
            label="出库报送日期"
          >
          </el-table-column>
          <el-table-column
            prop="outConsignNam"
            header-align="center"
            align="center"
            label="货主单位"
          >
          </el-table-column>
          <el-table-column
            prop="outCargoNam"
            header-align="center"
            align="center"
            label="商品名称"
          >
          </el-table-column>
          <el-table-column
            prop="outType"
            header-align="center"
            align="center"
            label="出库方式"
          >
          </el-table-column>
          <el-table-column
            prop="outBlno"
            header-align="center"
            align="center"
            label="提单号"
          >
          </el-table-column>
          <el-table-column
            prop="outStorageTank"
            header-align="center"
            align="center"
            label="储罐名称"
          >
          </el-table-column>
          <el-table-column
            prop="outQuantityPlan"
            header-align="center"
            align="center"
            label="计划数量（千克）"
          >
          </el-table-column>
          <el-table-column
            prop="outDensityStandard"
            header-align="center"
            align="center"
            label="密度标准"
          >
          </el-table-column>
          <el-table-column
            prop="outDensity"
            header-align="center"
            align="center"
            label="密度值"
          >
          </el-table-column>
          <el-table-column
            prop="outStartTim"
            header-align="center"
            align="center"
            label="计划开始时间"
          >
          </el-table-column>
          <el-table-column
            prop="outEndTim"
            header-align="center"
            align="center"
            label="计划结束时间"
          >
          </el-table-column>
          <el-table-column
            prop="outConfirmTim"
            header-align="center"
            align="center"
            label="出仓确认报送时间"
          >
          </el-table-column>
          <el-table-column
            prop="outQuantityReal"
            header-align="center"
            align="center"
            label="实际数量（千克）"
          >
          </el-table-column>
          <el-table-column
            prop="outCustomsDeclarationNum"
            header-align="center"
            align="center"
            label="出仓报关单号"
          >
          </el-table-column>
          <el-table-column
            prop="inventory"
            header-align="center"
            align="center"
            label="库存情况"
          >
          </el-table-column>
          <el-table-column
            prop="inMarkTxt"
            header-align="center"
            align="center"
            label="入库备注"
          >
          </el-table-column>
          <el-table-column
            prop="outMarkTxt"
            header-align="center"
            align="center"
            label="出库备注"
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
                v-if="permissions.oildatastatistics_edit"
                type="text"
                size="small"
                icon="el-icon-edit"
                @click="addOrUpdateHandle(scope.row.fId)"
                >修改</el-button
              >
              <el-button
                v-if="permissions.oildatastatistics_del"
                type="text"
                size="small"
                icon="el-icon-delete"
                @click="deleteHandle(scope.row.fId)"
                >删除</el-button
              >
              <el-button
                v-if="permissions.oildatastatistics_del"
                type="text"
                size="small"
                icon="el-icon-document-copy"
                @click="addCopy(scope.row.fId)"
                >复制</el-button
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
import { fetchList, delObj, handleDown, exportCheckBefore, downloadTemplate } from '@/api/baoshuisystem/oildatastatistics'
import TableForm from './oildatastatistics-form'
import { mapGetters } from 'vuex'
import XLSX from 'xlsx'
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
      expertStatus: 0,
      fileLoading: null
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
    getSummaries(param) {
      const { columns, data } = param;
      const sums = [];
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '合计';
          return;
        }
        const values = data.map(item => Number(item[column.property]));
        if (!values.every(value => isNaN(value))) {
          sums[index] = values.reduce((prev, curr) => {
            if (index === 12 || index === 18 || index === 30 || index === 36) {
              const value = Number(curr);
              if (!isNaN(value)) {
                return prev + curr;
              } else {
                return prev;
              }
            }
          });
        }
      });

      return sums;
    },
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true
      fetchList(Object.assign(
        {
          current: this.pageIndex,
          size: this.pageSize
        },
        this.dataForm
      )).then(response => {
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
    resetForm(formName) {
      if (this.$refs[formName] !== undefined) {
        this.$refs[formName].resetFields();
      }
      this.getDataList();
    },
    async getDataListQuery() {
      this.getDataList()
      this.expertStatus = 1
    },
    // 导出
    exportData() {
      if (this.expertStatus == 1) {
        handleDown("油品出入库数据统计表.xlsx", this.dataForm);
      } else {
        this.dataForm = {}
        handleDown("油品出入库数据统计表.xlsx", this.dataForm);
      }
    },
    // 新增 / 修改
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id)
      })
    },
    //复制
    addCopy(id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.copyMethod(id)
      })
    },
    // 删除
    deleteHandle(id) {
      this.$confirm('是否确认删除该条数据吗？', '提示', {
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
    // 导出前校验
    exportCheck() {
      this.dataListLoading = true
      exportCheckBefore(Object.assign(
        this.dataForm
      )).then(response => {
        console.log(response);
        if (response.data && response.data.msg) {
          this.$message.error(response.data.msg)
        } else {
          this.exportData()
        }
      })
      this.dataListLoading = false
    },
    // 下载导入模板
    downloadTemplateMethod() {
      downloadTemplate("油品出入库数据统计表.xlsx");
    },
    uploadSuccess: function (e) {
      // this.fileLoading = false
      if (e.data == null) {
        console.log(e)
        this.$notify.error(e.msg)
      } else {
        console.log(e)
        this.getDataList()
        this.$message.success(e.data)
      }

    },
    onProgress(event, file, fileList) {
      // this.$notify.error("开始上传")
      // this.fileLoading = true
    },
    onChange(file, fileList) {
      this.fileLoading = !this.fileLoading
    }
  }
}
</script>
