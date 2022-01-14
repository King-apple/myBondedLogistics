<template>
  <div class="mod-config">
    <basic-container>
      <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      </el-form>

      <div class="avue-crud">
        <el-table
                :data="dataList"
                border
                v-loading="dataListLoading">
                      <el-table-column
                    prop="processNo"
                    header-align="center"
                    align="center"
                    label="审批流程">
            </el-table-column>
                      <el-table-column
                    prop="orderBy"
                    header-align="center"
                    align="center"
                    label="审批序号">
            </el-table-column>
                      <el-table-column
                    prop="linkNam"
                    header-align="center"
                    align="center"
                    label="部门名称">
            </el-table-column>
                      <el-table-column
                    prop="roleNam"
                    header-align="center"
                    align="center"
                    label="角色">
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
            </el-table-column>
                      <el-table-column
                    prop="isListbyuser"
                    header-align="center"
                    align="center"
                    label="是否按照用户名查询">
            </el-table-column>
                    <el-table-column
                  header-align="center"
                  align="center"
                  label="操作">
            <template slot-scope="scope">
              <el-button type="text" size="small" icon="el-icon-edit" @click="addOrUpdateHandle(scope.row.fId)">修改</el-button>
              <el-button type="text" size="small" icon="el-icon-delete" @click="deleteHandle(scope.row.fId)">删除</el-button>
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
import { getRoleList } from '@/api/baoshuisystem/contractcheck'
import { getContractCheckRecord, getUserInfo, getDeptInfo, getContractType, getCheckStatusDic } from '@/api/baoshuisystem/contract'
  import {fetchList, delObj} from '@/api/baoshuisystem/approvalprocess'
  import TableForm from './approvalprocess-form'
  import {mapGetters} from 'vuex'
  export default {
    data () {
      return {
        dataForm: {
          key: ''
        },
        roles: [],
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
       getUserInfo().then(data => {
        this.userInfo = data.data.data.sysUser
        console.log("111",data)
        getRoleList(data.data.data.roles).then(data => {
          this.roles = data.data.data
          console.log("r,",this.roles)
        })
      })

        fetchList(Object.assign({
          current: this.pageIndex,
          size: this.pageSize
        })).then(response => {
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
