<template>
  <div class="mod-config">
    <basic-container>
      <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
        <el-form-item label="合同类型">
            <el-select
            v-model="dataForm.contractType"
            placeholder="请选择"
            filterable
            clearable
          >
            <el-option
              v-for="(
                contractTypeOptionsItem, contractTypeOptionsIndex
              ) in contractTypeOptions"
              :key="contractTypeOptionsIndex"
              :label="contractTypeOptionsItem.description"
              :value="contractTypeOptionsItem.description"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="业务部门">
          <el-input v-model="dataForm.dept" placeholder="业务部门"></el-input>
        </el-form-item>
        <el-form-item label="合同编号">
          <el-input v-model="dataForm.contNo" placeholder="合同编号"></el-input>
        </el-form-item>
        <el-form-item label="我司签约名头">
          <el-select
              v-model="dataForm.firstNam"
              placeholder="请选择"
              filterable
              clearable
              style="width: 100%"
            >
              <el-option
                v-for="item in options"
                :key="item.organizer"
                :label="item.organizer"
                :value="item.organizer"
              >
              </el-option>
            </el-select>
        </el-form-item>
         <el-form-item label="对方签约公司">
          <el-select
            v-model="dataForm.secondNam"
            placeholder="请选择"
            filterable
            clearable
            style="width: 100%"
          >
            <el-option
              v-for="(item, index) in secondNamOptions"
              :key="index"
              :label="item.company"
              :value="item.company"
            >
            </el-option>
          </el-select>
        </el-form-item>
         <el-form-item label="船名">
          <el-input v-model="dataForm.shipNam" placeholder="船名"></el-input>
        </el-form-item>
         <el-form-item label="货类名称">
           <el-select
            v-model="dataForm.cargoKindNam"
            placeholder="请选择"
            filterable
            clearable
            style="width: 100%"
          >
            <el-option
              v-for="(item, index) in cargoKindOptions"
              :key="index"
              :label="item.cargoKindNam"
              :value="item.cargoKindNam"
            >
            </el-option>
          </el-select>
        </el-form-item>
         <el-form-item label="货物名称">
          <el-select
            v-model="dataForm.cargoNam"
            placeholder="请选择"
            filterable
            clearable
          >
            <el-option
              v-for="(item, index) in cargoNamOptions"
              :key="index"
              :label="item.cargoNam"
              :value="item.cargoNam"
            >
            </el-option>
          </el-select>
        </el-form-item>
          <el-form-item label="结算依据">
          <el-select
              v-model="dataForm.settlementBasis"
              placeholder="请选择"
              filterable
              clearable
              style="width: 100%"
            >
              <el-option
                v-for="(item, index) in settlementBasisOptions"
                :key="index"
                :label="item.description"
                :value="item.description"
              >
              </el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="日期">
            <el-date-picker
            v-model="dataForm.params"
            type="daterange"
            :picker-options="pickerOptions"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            align="right"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
           </el-form-item>
        <el-form-item label="经办人">
          <el-input v-model="dataForm.operator" placeholder="经办人"></el-input>
        </el-form-item>
        <div>
          <el-form-item>
            <el-button type="primary" size="mini" icon="el-icon-search" @click="getDataList">查询</el-button>
            <el-button type="primary" size="mini" @click="exportLedger" icon="el-icon-download">导出</el-button>
            <el-button
                type="warning"
                icon="el-icon-refresh"
                size="mini"
                @click="resetForm('dataForm')"
                >重置</el-button
              >
          </el-form-item>
        </div>
       
      </el-form>

      <div class="avue-crud">
        <el-table
                :data="dataList"
                border
                v-loading="dataListLoading"
                @selection-change="handleSelectionChange">
                <el-table-column
                  type="selection"
                  width="55">
                </el-table-column>
                      <el-table-column
                    prop="contractType"
                    header-align="center"
                    align="center"
                    width="140vw"
                    show-overflow-tooltip
                    label="合同类型">
            </el-table-column>
                      <el-table-column
                    prop="dept"
                    header-align="center"
                    align="center"
                    width="120vw"
                    label="部门">
            </el-table-column>
                      <el-table-column
                    prop="contNo"
                    header-align="center"
                    align="center"
                    width="180vw"
                    label="合同编号">
            </el-table-column>
             <el-table-column
                    prop="firstNam"
                    header-align="center"
                    align="center"
                    width="180vw"
                    show-overflow-tooltip
                    label="我司签约名头">
            </el-table-column>
                      <el-table-column
                    prop="secondNam"
                    header-align="center"
                    align="center"
                    width="180vw"
                    show-overflow-tooltip
                    label="对方签约单位">
            </el-table-column>
                      <el-table-column
                    prop="shipNam"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="船名">
            </el-table-column>
                      <el-table-column
                    prop="cargoKindNam"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="货类名称">
            </el-table-column>
                      <el-table-column
                    prop="cargoNam"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="货物名称">
            </el-table-column>
                      <el-table-column
                    prop="cargoNum"
                    header-align="center"
                    align="center"
                    width="90vw"
                    label="标的数量">
            </el-table-column>
                      <el-table-column
                    prop="cargoFee"
                    header-align="center"
                    align="center"
                    width="100vw"
                    show-overflow-tooltip
                    label="标的额">
            </el-table-column>
                      <el-table-column
                    prop="settlementBasis"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="结算依据">
            </el-table-column>
                      <el-table-column
                    prop="startDte"
                    header-align="center"
                    align="center"
                    width="100vw"
                    label="开始日期">
            </el-table-column>
                      <el-table-column
                    prop="endDte"
                    header-align="center"
                    align="center"
                     width="100vw"
                    label="结束日期">
            </el-table-column>
                      <el-table-column
                    prop="operator"
                    header-align="center"
                    align="center"
                    show-overflow-tooltip
                    label="经办人">
            </el-table-column>
                    <el-table-column
                  header-align="center"
                  align="center"
                  fixed="right"
                  width="100px"
                  label="操作">
            <template slot-scope="scope">
              <el-button type="text" size="small" icon="el-icon-edit" @click="addOrUpdateHandle(scope.row)">查看</el-button>
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
  import {fetchList,exportMessage, delObj} from '@/api/baoshuisystem/settlementcontractledger'
  import{filePageList,getContractType,getInsertValue,getSettlementBasisDic }from '@/api/baoshuisystem/contract'
  import TableForm from './settlementcontractledger-form'
  import { getClientInfoAll as allClientInfo } from '@/api/baoshuisystem/clientinfo'
  import {mapGetters} from 'vuex'
  import { all as allCargoKind } from '@/api/baoshuisystem/goodscategory'
  import { getContractObjectInfoAll as carGoNamesList } from '@/api/baoshuisystem/contractobject'
  export default {
    data () {
      return {
        dataForm: {
          key: '',
          params: [],
        },
        multipleSelection: [],
        dataList: [],
         options: [],
        contractTypeOptions: [],
        cargoNamOptions: [],
        settlementBasisOptions: [],
        secondNamOptions: [],
        cargoKindOptions: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        addOrUpdateVisible: false,
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
    created () {
      this.getQueryInfo()
      this.getDataList()
      
    },
    computed: {
      ...mapGetters(['permissions'])
    },
    methods: {
      async getQueryInfo(){
      await getContractType().then(response => {
        this.contractTypeOptions = response.data.data
      }),
       await getInsertValue().then(response => {
        this.insData = response.data.data
        this.options = this.insData.organizerInfos
      }),
       carGoNamesList().then(response => {
        this.cargoNamOptions = response.data.data
      }),
      allCargoKind().then(response => {
        this.cargoKindOptions = response.data.data
      })
      allClientInfo().then(response => {
        this.secondNamOptions = response.data.data
      }),
      await getSettlementBasisDic().then(response => {
        this.settlementBasisOptions = response.data.data
      })
      },
    resetForm(formName) {
      //使用前，先绑定el-form的 ref
      this.dataForm = {};
      this.getDataList();
    },
      exportLedger(){
        if(this.multipleSelection.length>0){
          exportMessage("合同结算台账.xlsx", this.multipleSelection );
        }else{
          this.$message("请至少选择一条数据")
        }
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
         this.$nextTick(() =>{
            filePageList(Object.assign({
          current: this.pageIndex,
          size: this.pageSize
        }, this.dataForm)).then(response => {
          this.dataList = response.data.data.records
          this.totalPage = response.data.data.total
        })
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
          this.$refs.addOrUpdate.init(id.contNo)
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
