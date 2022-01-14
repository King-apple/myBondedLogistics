<template>
  <div class="mod-config">
    <basic-container>
      <el-form
        :inline="true"
        :model="dataForm"
        ref="dataForm"
        @keyup.enter.native="getDataList()"
      >
        <el-form-item label="按称别组分类查询" prop="cbs">
          <el-select v-model="dataForm.cbs" placeholder="">
            <el-option :label="item" :value="item" v-for="(item,index) in allGroup" :key="index"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getDataList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="resetForm('dataForm')">重置</el-button>
        </el-form-item>
      </el-form>

      <div class="avue-crud">
        <el-table
          :data="dataList"
          class="a"
          border
          :summary-method="getSummaries"
          show-summary
          height="500"
          v-loading="dataListLoading"
          ref="singleTable"
        >

          <el-table-column
            prop="lxdm"
            header-align="center"
            align="center"
            label="代码"
          >
          </el-table-column>
          <el-table-column
            prop="bh"
            header-align="center"
            align="center"
            label="货票编号"
          >
          </el-table-column>

          <el-table-column
            prop="hm"
            header-align="center"
            align="center"
            width="100px"
            label="货名"
          >
          </el-table-column>
          <el-table-column
            prop="tdh"
            header-align="center"
            align="center"
            width="150px"
            label="提单号"
          >
          </el-table-column>
          <el-table-column
            prop="cbh"
            header-align="center"
            align="center"
            label="船号"
            width="120px"
          >
          </el-table-column>
          <el-table-column
            prop="cm"
            header-align="center"
            align="center"
            width="150px"
            label="船名"
          >
          </el-table-column>
          <el-table-column
            prop="jhl"
            header-align="center"
            align="center"
            label="计划量"
          >
          </el-table-column>
          <el-table-column
            prop="wcl"
            header-align="center"
            align="center"
            label="完成量"
          >
          </el-table-column>
          <el-table-column
            prop="syl"
            header-align="center"
            align="center"
            label="剩余量"
          >
          </el-table-column>
          <el-table-column
            prop="csl"
            header-align="center"
            align="center"
            label="车次"
          >
          </el-table-column>
          <el-table-column
            prop="cbs"
            header-align="center"
            align="center"
            label="称组"
          >
          </el-table-column>
          <el-table-column
            prop="sqdw"
            header-align="center"
            align="center"
            label="申请单位"
            width="120vw"
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

      <!-- 弹窗, 新增 / 修改 -->
    </basic-container>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { fetchList,getGroup} from '@/api/baoshuisystem/outstandinginvoice'
export default {
  data() {
    return {
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
    getGroupInfo(){
      getGroup().then(res =>{
       this.allGroup = res.data.data
      })
    },
    getSummaries(param) {
        const { columns, data } = param;
        const sums = [];
        columns.forEach((column, index) => {
          if (index === 5) {
            sums[index] = '合计:';
            return;
          }
          const values = data.map(item => Number(item[column.property]));
          if (!values.every(value => isNaN(value)) && index>4 && index<=9) {
            sums[index] = values.reduce((prev, curr) => {
              const value = Number(curr);
              if (!isNaN(value)) {
                return Number((prev + curr));
              } else {
                return prev;
              }
            }, 0).toFixed(2);


          } else {
            sums[index] = '';
          }
        });

        return sums;
      },
    // 获取数据列表
    getDataList() {
      fetchList(Object.assign({
        current: this.pageIndex,
        size: this.pageSize,

      }, this.dataForm)).then(response => {
        this.dataList = response.data.data.records
        console.log("res",response)
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
        this.getDataList();
      }
      this.setCurrent();
    },
    setCurrent(row) {
      this.$nextTick(() => {
        this.$refs.singleTable.setCurrentRow(row);
      });

    },
    searchHpmx(bh) {
      getOne(bh).then(data => {
        this.destroyForm.bz = data.data.data.bz
        this.destroyForm.hz = data.data.data.hz
      })
    },
    inoutIdFormatter(row) {
      for (var i in this.inoutIdOptions) {
        if (row.inoutId == this.inoutIdOptions[i].value) {
          return this.inoutIdOptions[i].label
        }
      }
    },
    tradeIdFormatter(row) {
      for (var i in this.tardeIdOptions) {
        if (row.tradeId == this.tardeIdOptions[i].value) {
          return this.tardeIdOptions[i].label
        }
      }
    },
    inYardWayFormatter(row) {
      for (var i in this.inyardTypeOptions) {
        if (row.inYardWay == this.inyardTypeOptions[i].value) {
          return this.inyardTypeOptions[i].label
        }
      }
    }
  }
}
</script>
<style lang="less">
// .el-table{
//   overflow: auto;
// }
// .el-table .el-table__body-wrapper,
// .el-table .el-table__header-wrapper,
// .el-table .el-table__footer-wrapper{
//   overflow: visible;
// }
// .el-table::after{
//   position:relative !important;
// }
</style>
