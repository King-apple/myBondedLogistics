<template>
  <div class="mod-config">
    <basic-container>
      <el-form
        :inline="true"
        :model="dataForm"
        ref="dataForm"
        @keyup.enter.native="getDataList()"
      >
        <el-form-item label="计划日期" >
          <el-date-picker
              v-model="dataForm.planDte"
              type="date"
              placeholder="日期"
              value-format="yyyy-MM-dd"
              style="width: 100%"
            >
            </el-date-picker>
        </el-form-item>
        <el-form-item label="计划单位" >
          <el-select v-model="dataForm.unitCod" placeholder="" clearable>
            <el-option  :label="item.meaning" :value="item.code" v-for="(item,index) in allGroup" :key="index"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getDataList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="resetForm('dataForm')">重置</el-button>
        </el-form-item>
         <el-form-item>
          <el-button type="primary" @click="getPlanMan(dataForm.planDte,dataForm.unitCod)">点击查看计划人员</el-button>
        </el-form-item>
      </el-form>

      <div class="avue-crud">
        <el-table
          :data="dataList"
          border
          height="500"
          v-loading="dataListLoading"
          ref="singleTable"
        >
          <el-table-column
            prop="berthCod"
            header-align="center"
            align="center"
            label="单位代码"
          >
          </el-table-column>
          <el-table-column
            prop="shipNam"
            header-align="center"
            align="center"
            label="船名"
          >
          </el-table-column>

          <el-table-column
            prop="cargoNam"
            header-align="center"
            align="center"
            width="150px"
            label="货名"
          >
          </el-table-column>
          <el-table-column
            prop="unloadId"
            header-align="center"
            align="center"
            width="150px"
            label="装卸"
          >
          </el-table-column>
          <el-table-column
            prop="toPortTim"
            header-align="center"
            align="center"
            label="到港时间"
            width="160px"
          >
          </el-table-column>
          <el-table-column
            prop="toBerthTim"
            header-align="center"
            align="center"
            width="160px"
            label="靠泊时间"
          >
          </el-table-column>
          <el-table-column
            prop="beginTim"
            header-align="center"
            align="center"
            width="160px"
            label="开工时间"
          >
          </el-table-column>
          <el-table-column
            prop="endTim"
            header-align="center"
            align="center"
            width="160px"
            label="完工时间"
          >
          </el-table-column>
          <el-table-column
            prop="planMoveTim"
            header-align="center"
            align="center"
            width="160px"
            label="移泊时间"
          >
          </el-table-column>
          <el-table-column
            prop="levPortTim"
            header-align="center"
            align="center"
            width="160px"
            label="离港时间"
          >
          </el-table-column>
          <el-table-column
            prop="shipCap"
            header-align="center"
            align="center"
            label="载货量"
          >
          </el-table-column>
           <el-table-column
            prop="surplusTon"
            header-align="center"
            align="center"
            label="全船剩吨"
          >
          </el-table-column>
           <el-table-column
            prop="lastSurTon"
            header-align="center"
            align="center"
            label="上昼夜剩吨"
          >
          </el-table-column>
          <el-table-column
            prop="lastWorkTon"
            header-align="center"
            align="center"
            label="上昼夜完成吨"
          >
          </el-table-column>
          <el-table-column
            prop="planWorkTon"
            header-align="center"
            align="center"
            label="本昼夜计划完成吨"
          >
          </el-table-column>
          <el-table-column
            prop="firstTxt"
            header-align="center"
            align="center"
            label="16点安排"
          >
          </el-table-column>
          <el-table-column
            prop="secondTxt"
            header-align="center"
            align="center"
            label="00点安排"
          >
          </el-table-column>
          <el-table-column
            prop="thirdTxt"
            header-align="center"
            align="center"
            label="08点安排"
          >
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
      <el-dialog title="相关计划人员" :visible.sync="planLoading">
      <el-table :data="gridData">
        <el-table-column property="masterMan" label="计划主任" width="150"></el-table-column>
        <el-table-column property="planMan" label="计划员" width="200"></el-table-column>
        <el-table-column property="contentTxt" label="重点说明"></el-table-column>
      </el-table>
    </el-dialog>
      <!-- 弹窗, 新增 / 修改 -->
    </basic-container>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { fetchList,getUnitNam,geteHeadMessag} from '@/api/baoshuisystem/companyshipnightplan'
export default {
  data() {
    return {
      gridData: [],
      dataForm: {

      },
      destroyForm: {},
      dataList: [],
      pageIndex: 1,
      allGroup:[],
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      addOrUpdateVisible: false,
      dialogFormVisible: false,
      contractOptions: [],
      planLoading:false,
      outStrogeForm: {},
      outyardTypeOptions: [],
      inoutIdOptions: [],
      tardeIdOptions: [],
      inyardTypeOptions: [],
      prepareFlag: 0,
      dialogTitle: 0,
      index: 0,
      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
      formLabelWidth: '120px',
      destroyRules: {

      },
      numberRule: [{
        validator(rule, value, callback) {
          if (!isNaN(Number(value))) {
            callback()
          } else {
            callback(new Error('请输入数字'))
          }
        },
        trigger: 'blur'
      }]
    }
  },
  components: {

  },
  created() {
    this.getGroupInfo()
    this.getDataList()

  },
  updated(){
    this.$nextTick(() => {
      this.$refs['singleTable'].doLayout();
    })
  },
  computed: {
    ...mapGetters(['permissions'])
  },
  methods: {
    getPlanMan(dataTime,dataNam){
      if(dataTime != null && dataNam != null){
          geteHeadMessag(this.dataForm).then(res =>{
            this.gridData = res.data.data
            this.planLoading = true
      })

      }else{
        this.$message.info("请确保查询条件状态都不为空")
      }
    },
    getGroupInfo(){
      getUnitNam().then(res =>{
      this.allGroup = res.data.data
      console.log("this.allGroup",this.allGroup)
      })
    },
    // 获取数据列表
    getDataList() {
      fetchList(Object.assign({
        current: this.pageIndex,
        size: this.pageSize,
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
    resetForm(formName) {
      this.dataForm = {};
      this.getDataList();
    },

 }
}
</script>
<style >

</style>
