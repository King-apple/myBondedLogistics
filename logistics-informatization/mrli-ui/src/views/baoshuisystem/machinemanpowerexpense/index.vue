<template>
  <div class="mod-config">
    <basic-container>
      <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      	 <el-form-item label="时间" prop="recordTim">
			<el-date-picker
		      v-model="value1"
		      type="daterange"
		      range-separator="至"
		      start-placeholder="开始日期"
		      end-placeholder="结束日期">
		    </el-date-picker>
		</el-form-item>
		<el-form-item label="单位名称" label-width="100px" prop="settledCompany">
          <el-input
            v-model="dataForm.subunitNam"
            placeholder="单位名称"
          ></el-input>
        </el-form-item>
		<el-form-item>
          <el-button type="primary" @click="getDataList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="warning" v-if="permissions.machinemanpowerexpense_export" @click="exportExcel()"
            >导出</el-button
          >
        </el-form-item>
		
        <!--<el-form-item>
          <el-button v-if="permissions.machinemanpowerexpense_add" icon="el-icon-plus" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        </el-form-item>-->
      </el-form>

      <div class="avue-crud">
        <el-table
                :data="dataList"
                border
                v-loading="dataListLoading">
                <el-table-column
                    type="index"
                    header-align="center"
                    align="center"
                    label="序号">
            </el-table-column>
                      <!--<el-table-column
                    prop="fId"
                    header-align="center"
                    align="center"
                    label="主键">
            </el-table-column>-->
                      <!--<el-table-column
                    prop="workbillNo"
                    header-align="center"
                    align="center"
                    label="工票号">
            </el-table-column>-->
                      <el-table-column
                    prop="workDte"
                    header-align="center"
                    align="center"
                    label="作业日期" width="120" :formatter="workDteFormat">
            </el-table-column>
                      <el-table-column
                    prop="subunitNam"
                    header-align="center"
                    align="center"
                    label="单位名称">
            </el-table-column>
                      <el-table-column
                    prop="machTypeNam"
                    header-align="center"
                    align="center"
                    label="车型">
            </el-table-column>
                      <el-table-column
                    prop="machKndCod"
                    header-align="center"
                    align="center"
                    label="车型号">
            </el-table-column>
                      <el-table-column
                    prop="workTypeNam"
                    header-align="center"
                    align="center"
                    label="作业内容">
            </el-table-column>
                      <el-table-column
                    prop="hours"
                    header-align="center"
                    align="center"
                    label="小时/吨">
            </el-table-column>
                      <el-table-column
                    prop="unitPrice"
                    header-align="center"
                    align="center"
                    label="单价">
            </el-table-column>
                      <el-table-column
                    prop="sum"
                    header-align="center"
                    align="center"
                    label="作业金额(元)" width="100">
            </el-table-column>
                      <el-table-column
                    prop="checkDte"
                    header-align="center"
                    align="center"
                    label="检算日期"
                    :formatter="checkDteFormat">
            </el-table-column>
                      <el-table-column
                    prop="settleDte"
                    header-align="center"
                    align="center"
                    label="结算日期"
                    :formatter="settleDteFormat">
            </el-table-column>
                      <el-table-column
                    prop="receiptInvoiceDte"
                    header-align="center"
                    align="center"
                    label="收到发票日期" width="120"
                    :formatter="receiptInvoiceDteFormat">
            </el-table-column>
                      <el-table-column
                    prop="settledAmount"
                    header-align="center"
                    align="center"
                    label="已结算金额">
            </el-table-column>
                      <el-table-column
                    prop="outstandingAmount"
                    header-align="center"
                    align="center"
                    label="未结算金额">
            </el-table-column>
                    <el-table-column
                  header-align="center"
                  align="center"
                  label="操作">
            <template slot-scope="scope">
              <el-button v-if="permissions.machinemanpowerexpense_edit" type="text" size="small" icon="el-icon-edit" @click="addOrUpdateHandle(scope.row)">修改</el-button>
              <!--<el-button v-if="permissions.machinemanpowerexpense_del" type="text" size="small" icon="el-icon-delete" @click="deleteHandle(scope.row.fId)">删除</el-button>-->
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
  import {fetchList, delObj , getMachineManpowerExcel} from '@/api/baoshuisystem/machinemanpowerexpense'
  import TableForm from './machinemanpowerexpense-form'
  import { dateToString } from '@/util/date'
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
        value1: [],
      }
    },
    components: {
      TableForm
    },
    created () {
      const end = new Date();
      const start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
      this.value1 = [start,end]
      this.getDataList()
    },
    computed: {
      ...mapGetters(['permissions'])
    },
    methods: {
      // 获取数据列表
      getDataList () {
      	if(this.value1 == null || this.value1.length<2){
	 	this.$message.error('请填写日期！')
	 	return ;
	 }
	  this.dataForm.startTime = dateToString(this.value1[0]).substring(0,10)
	  this.dataForm.endTime = dateToString(this.value1[1]).substring(0,10)
        this.dataListLoading = true
        fetchList(Object.assign(this.dataForm,{
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
      addOrUpdateHandle (item) {
      	console.log(item)
      	
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(item)
          this.getDataList()
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
      },
      workDteFormat:function(date){
      	if(date.workDte == null || date.workDte==""){
      		return null;
      	}
      	return date.workDte.substring(0,10)
      },
      checkDteFormat:function(date){
      	if(date.checkDte == null || date.checkDte==""){
      		return null;
      	}
      	return date.checkDte.substring(0,10)
      },
      settleDteFormat:function(date){
      	if(date.settleDte == null || date.settleDte==""){
      		return null;
      	}
      	return date.settleDte.substring(0,10)
      },
      receiptInvoiceDteFormat:function(date){
      	if(date.receiptInvoiceDte == null || date.receiptInvoiceDte==""){
      		return null;
      	}
      	return date.receiptInvoiceDte.substring(0,10)
      },
      async exportExcel(){
	     if(this.value1 == null || this.value1.length<2){
		 	this.$message.error('请填写日期！')
		 	return ;
		 }
//		  this.dataForm.startTime = dateToString(this.value1[0]).substring(0,10)
//		  this.dataForm.endTime = dateToString(this.value1[1]).substring(0,10)
	      await getMachineManpowerExcel('机械人力作业费明细表.xlsx', this.dataForm)
	    }
    }
  }
</script>
