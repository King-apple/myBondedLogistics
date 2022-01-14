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
		
		<el-form-item label="条码编号" label-width="100px" prop="outportNo">
          <el-input
            v-model="dataForm.outportNo"
            placeholder="条码编号"
          ></el-input>
        </el-form-item>
         <el-form-item>
          <el-button type="primary" @click="getDataList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="warning" @click="resetForm()">重置</el-button>
        </el-form-item>
       <el-form-item>
          <el-button v-if="permissions.onedaycensus_export" icon="el-icon-plus" type="warning" @click="exportExcel()">导出</el-button>
        </el-form-item>
      </el-form>

      <div class="avue-crud">
        <el-table
                :data="dataList"
                border
                v-loading="dataListLoading">
                      <el-table-column
                    prop="recordDte"
                    header-align="center"
                    align="center"
                    label="日期">
            </el-table-column>
                      <el-table-column
                    prop="cargoNam"
                    header-align="center"
                    align="center"
                    label="货名">
            </el-table-column>
                      <el-table-column
                    prop="piecesNum"
                    header-align="center"
                    align="center"
                    label="每车件数">
            </el-table-column>
                      <el-table-column
                    prop="carNo"
                    header-align="center"
                    align="center"
                    label="车牌号">
            </el-table-column>
                      <el-table-column
                    prop="outportNo"
                    header-align="center"
                    align="center"
                    label="条码编号">
            </el-table-column>
                      <el-table-column
                    prop="agentNam"
                    header-align="center"
                    align="center"
                    label="提货单位">
            </el-table-column>
                      <el-table-column
                    prop="operNam"
                    header-align="center"
                    align="center"
                    label="开票人">
            </el-table-column>
                      <el-table-column
                    prop="outportRemark"
                    header-align="center"
                    align="center"
                    label="备注">
            </el-table-column>
                    <el-table-column
                  header-align="center"
                  align="center"
                  label="操作">
            <template slot-scope="scope">
              <el-button v-if="permissions.onedaycensus_edit" type="text" size="small" icon="el-icon-edit" @click="addOrUpdateHandle(scope.row.recordDte)">修改</el-button>
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
  import {fetchList, delObj,exportExcel} from '@/api/baoshuisystem/outportcertificate'
  import TableForm from './outportcertificate-form'
  import {mapGetters} from 'vuex'
  import { dateToString } from '@/util/date'
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
//    picker.$emit('pick', [start, end]);
      this.value1 = [start,end]
      this.getDataList()
    },
    computed: {
      ...mapGetters(['permissions'])
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        if(this.value1!=null && this.value1.length>=2){
        	this.dataForm.timeStart = dateToString(this.value1[0]).substring(0,10)
	 		this.dataForm.timeEnd = dateToString(this.value1[1]).substring(0,10)
        }
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
      exportExcel () {
      	if(this.value1!=null && this.value1.length>=2){
        	this.dataForm.timeStart = dateToString(this.value1[0]).substring(0,10)
	 		this.dataForm.timeEnd = dateToString(this.value1[1]).substring(0,10)
        }
        exportExcel("一日游出港证登记表.xlsx",this.dataForm).then((res) => {
        	console.log(res)
        	
        })
      },
      resetForm() {
      this.dataForm = {};
      this.getDataList();
    },
    }
  }
</script>
