<template>
  <div class="mod-config">
    <basic-container>
      <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()" ref="dataForm">
        <el-form-item
          label="入出库"
          prop="inOut"
        >
          <el-select
            v-model="dataForm.inOut"
            placeholder="请选择"
            filterable
            clearable
            style="width: 100%"
          >
            <el-option
              v-for="(item, index) in inoutOptions"
              :key="index"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="单据名称" prop="inoutYardNam">
          <el-input
            v-model="dataForm.inoutYardNam"
            placeholder="单据名称"
          ></el-input>
        </el-form-item>
        <el-form-item label="仓库名称" prop="warehouseNam">
          <el-input
            v-model="dataForm.warehouseNam"
            placeholder="仓库名称"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            type="success"
            icon="el-icon-search"
            @click="getDataList()"
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
        <br>
        <el-form-item>
          <el-button v-if="permissions.warehousenaminfo_add" icon="el-icon-plus" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        </el-form-item>
      </el-form>

      <div class="avue-crud" style="width:100%;">
        <el-table
                :data="dataList"
                border
                v-loading="dataListLoading">
                      <el-table-column
                    prop="inOut"
                    header-align="center"
                    :formatter="inoutFormat"
                    align="center"
                    label="出入库">
            </el-table-column>
                      <el-table-column
                    prop="inoutYardNam"
                    header-align="center"
                    align="center"
                    label="单据名称">
            </el-table-column>
                      <el-table-column
                    prop="warehouseCod"
                    header-align="center"
                    align="center"
                    label="仓库编号">
            </el-table-column>
                      <el-table-column
                    prop="warehouseNam"
                    header-align="center"
                    align="center"
                    label="仓库名称">
            </el-table-column>
                      <el-table-column
                    prop="electronicAccountNum"
                    header-align="center"
                    align="center"
                    label="电子账册号">
            </el-table-column>
                      <!-- <el-table-column
                    prop="exportCod"
                    header-align="center"
                    align="center"
                    label="模板标识">
            </el-table-column> -->
                      <el-table-column
                    prop="exportNam"
                    header-align="center"
                    align="center"
                    label="模板名称">
            </el-table-column>
                      <el-table-column
                    prop="markTxt"
                    header-align="center"
                    align="center"
                    label="备注">
            </el-table-column>
                      <el-table-column
                    prop="operNam"
                    header-align="center"
                    align="center"
                    label="添加人">
            </el-table-column>
                      <el-table-column
                    prop="recordTim"
                    header-align="center"
                    align="center"
                    label="添加时间">
            </el-table-column>
                      <!-- <el-table-column
                    prop="updateNam"
                    header-align="center"
                    align="center"
                    label="修改人">
            </el-table-column>
                      <el-table-column
                    prop="updateTim"
                    header-align="center"
                    align="center"
                    label="修改时间">
            </el-table-column>
                      <el-table-column
                    prop="delNam"
                    header-align="center"
                    align="center"
                    label="删除人">
            </el-table-column>
                      <el-table-column
                    prop="delTim"
                    header-align="center"
                    align="center"
                    label="删除时间">
            </el-table-column> -->
                    <el-table-column
                  header-align="center"
                  align="center"
                  label="操作">
            <template slot-scope="scope">
              <el-button v-if="permissions.warehousenaminfo_edit" type="text" size="small" icon="el-icon-edit" @click="addOrUpdateHandle(scope.row.fId)">修改</el-button>
              <el-button v-if="permissions.warehousenaminfo_del" type="text" size="small" icon="el-icon-delete" @click="deleteHandle(scope.row.fId)">删除</el-button>
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
                layout="total, sizes, prev, pager, next, jumper">
        </el-pagination>
      </div>
      <!-- 弹窗, 新增 / 修改 -->
      <table-form v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></table-form>
    </basic-container>
  </div>
</template>

<script>
  import {fetchList, delObj} from '@/api/baoshuisystem/warehousenaminfo'
  import {getInoutFlag} from '@/api/baoshuisystem/getdictdata'
  import TableForm from './warehousenaminfo-form'
  import {mapGetters} from 'vuex'
  export default {
    data () {
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
        inoutOptions: []
      }
    },
    components: {
      TableForm
    },
    created () {
      this.getDataList()
    },
    computed: {
      ...mapGetters(['permissions'])
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        getInoutFlag().then(data => {
          this.inoutOptions = data.data.data
        })
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
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 删除
      deleteHandle (id) {
        this.$confirm('是否确认删除该条数据吗', '提示', {
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
      resetForm(formName) {
        if (this.$refs[formName] !== undefined) {
          this.$refs[formName].resetFields();
        }
        this.getDataList();
      },
      inoutFormat(row, column, cellValue, index) {
        for (var i in this.inoutOptions) {
          if (cellValue == this.inoutOptions[i].value) {
            return this.inoutOptions[i].label
          }
        }

      },
    }
  }
</script>
