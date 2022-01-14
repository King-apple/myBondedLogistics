<template>
  <div class="mod-config">
    <basic-container>
      <el-form
        :inline="true"
        :model="dataForm"
        @keyup.enter.native="getDataList()"
      >
        <el-form-item label="货票编号" prop="ioyardNo" style="width: 22%">
          <el-input
            v-model="dataForm.ioyardNo"
            placeholder="货票编号"
          ></el-input>
        </el-form-item>
                <el-form-item label="报关单号" prop="customsDeclaration">
          <el-select
            v-model="dataForm.customsDeclaration"
            placeholder="请选择"
            filterable
            clearable
          >
            <el-option
              v-for="(item, index) in customsDeclarationList"
              :key="index"
              :label="item.customsDeclaration"
              :value="item.customsDeclaration"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="提单号" prop="billNo" style="width: 22%">
          <el-select
            v-model="dataForm.billNO"
            placeholder="请选择"
            filterable
            clearable
          >
            <el-option
              v-for="(item, index) in billNOList"
              :key="index"
              :label="item.billNo"
              :value="item.billNo"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="合同编号" prop="contNo">
          <!-- <el-input v-model="dataForm.contNo" placeholder="合同编号"></el-input> -->
          <el-select
            v-model="dataForm.contNo"
            placeholder="请选择"
            filterable
            clearable
          >
            <el-option
              v-for="(item, index) in contNoList"
              :key="index"
              :label="item.contNo"
              :value="item.contNo"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="品名" prop="cargoNam">
          <!-- <el-input v-model="dataForm.cargoNam" placeholder="品名"></el-input> -->
          <el-select
            v-model="dataForm.cargoNam"
            placeholder="请选择"
            filterable
            clearable
          >
            <el-option
              v-for="(item, index) in cargoList"
              :key="index"
              :label="item.cargoNam"
              :value="item.cargoNam"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="船名" prop="shipNam">
          <!-- <el-input v-model="dataForm.shipNam" placeholder="船名"></el-input> -->
          <el-select
            v-model="dataForm.shipNam"
            placeholder="请选择"
            filterable
            clearable
          >
            <el-option
              v-for="(item, index) in shipList"
              :key="index"
              :label="item.shipNam"
              :value="item.shipNam"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="作业委托人" prop="consignNam">
          <!-- <el-input
            v-model="dataForm.consignNam"
            placeholder="作业委托人"
          ></el-input> -->
          <el-select
            v-model="dataForm.consignNam"
            placeholder="请选择"
            filterable
            clearable
          >
            <el-option
              v-for="(item, index) in conSignList"
              :key="index"
              :label="item.consignNam"
              :value="item.consignNam"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          id="bz"
          label="出入库标志"
          prop="ioyardId"
          style="width: 15%"
        >
          <el-select
            v-model="dataForm.ioyardId"
            placeholder="请选择"
            filterable
            clearable
          >
            <el-option
              v-for="(item, index) in this.ioyardIdOptions"
              :key="index"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出入库日期" prop="params.recordDte">
          <el-date-picker
            v-model="dataForm.params.recordDte"
            type="daterange"
            :picker-options="pickerOptions"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            align="right"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="getIoyardDataList">查询</el-button>
        </el-form-item>
        <div style="margin-bottom:10px;font-weight:bold;">
          <span style="font-size: 16px"
            >合计：入库量：{{ countList.length == 0 ? 0 : countList[0].weightWgt }} 吨&nbsp;&nbsp;&nbsp;&nbsp;
            入库件数 ：{{ countList.length == 0 ? 0 : countList[0].piecesNum }}
            件&nbsp;&nbsp;&nbsp;&nbsp;
          </span>
          <span style="font-size: 16px"
            >出库量：{{ countList.length == 0 ? 0 : countList[1].weightWgt }} 吨&nbsp;&nbsp;&nbsp;&nbsp;
            出库件数 ：{{
              countList.length == 0 ? 0 : countList[1].piecesNum
            }}
            件</span
          >
        </div>

        <!-- <el-form-item>
          <el-button
            v-if="permissions.ioyard_add"
            icon="el-icon-plus"
            type="primary"
            @click="addOrUpdateHandle()"
            >新增</el-button
          >
        </el-form-item> -->
      </el-form>

      <div class="avue-crud" style="width:100%;">
        <el-table
          :data="dataList"
          border
          v-loading="dataListLoading"
          height="500px"
          ref="dataList"
          :summary-method="getSummaries"
          show-summary
        >
          <!-- <el-table-column
            prop="unitCod"
            header-align="center"
            align="center"
            label="单位代码"
          >
          </el-table-column> -->
          <el-table-column
            prop="ioyardId"
            header-align="center"
            align="center"
            label="出入库标志"
            :formatter="ioyardIdFormatter"
          >
          </el-table-column>
          <el-table-column
            prop="contNo"
            header-align="center"
            align="center"
            label="合同号"
          >
          </el-table-column>
          <el-table-column
            prop="consignNam"
            header-align="center"
            align="center"
            label="代理委托人"
          >
          </el-table-column>
          <el-table-column
            prop="ioyardDte"
            header-align="center"
            align="center"
            label="出入库日期"
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
            label="货名"
          >
          </el-table-column>
          <el-table-column
            prop="piecesNum"
            header-align="center"
            align="center"
            label="件数"
          >
          </el-table-column>
          <el-table-column
            prop="weightWgt"
            header-align="center"
            align="center"
            label="重量"
          >
          </el-table-column>
          <el-table-column
            prop="ioyardNo"
            header-align="center"
            align="center"
            label="货票编号"
          >
          </el-table-column>
          <el-table-column
            prop="billNo"
            header-align="center"
            align="center"
            label="提单号"
          >
          </el-table-column>
          <el-table-column
            prop="customsDeclaration"
            header-align="center"
            align="center"
            label="报关单号"
          >
          </el-table-column>

          <el-table-column
            prop="inYardWay"
            header-align="center"
            align="center"
            label="入库方式"
            :formatter="inYardWayFormatter"
          >
          </el-table-column>

          <el-table-column
            prop="classId"
            header-align="center"
            align="center"
            label="班次"
          >
          </el-table-column>

          <el-table-column
            prop="subunitNam"
            header-align="center"
            align="center"
            label="科室队名称"
          >
          </el-table-column>

          <el-table-column
            prop="firstInDte"
            header-align="center"
            align="center"
            label="初次入库日期"
          >
          </el-table-column>
          <el-table-column
            prop="outYardWay"
            header-align="center"
            align="center"
            label="出库方式"
            :formatter="outYardWayFormatter"
          >
          </el-table-column>

          <el-table-column
            prop="yardNam"
            header-align="center"
            align="center"
            label="库场名称"
          >
          </el-table-column>
          <el-table-column
            prop="locNo"
            header-align="center"
            align="center"
            label="货位号"
          >
          </el-table-column>
          <el-table-column
            prop="remarkTxt"
            header-align="center"
            align="center"
            label="备注"
          >
          </el-table-column>
          <el-table-column
            prop="operNam"
            header-align="center"
            align="center"
            label="操作员"
          >
          </el-table-column>
          <!-- <el-table-column
            prop="cargoKindNam"
            header-align="center"
            align="center"
            label="货类名称"
          >
          </el-table-column>


          <el-table-column
            prop="recordTim"
            header-align="center"
            align="center"
            label="记录时间"
          >
          </el-table-column> -->

          <el-table-column
            header-align="center"
            align="center"
            label="操作"
            fixed="right"
          >
            <template slot-scope="scope">
              <el-button
                v-if="permissions.ioyard_del"
                type="text"
                size="small"
                icon="el-icon-delete"
                @click="deleteHandle(scope.row.inoutYardNo)"
                >还原</el-button
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
import {
  fetchList, delObj, getInoutIdDic, getTradeIdDic,
  getIoyardIdDic, getInYardWayDic, getoutYardWayDic,
  inoutyardRecordRemove, getSelectCondition
} from '@/api/baoshuisystem/ioyard'
import {
  getInyardType, getOutYardType
} from '@/api/baoshuisystem/getdictdata'
import { mapGetters } from 'vuex'
export default {
  data() {
    return {
      dataForm: {
        key: '',
        params: { recordDte: '' },
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      inoutIdOptions: [],
      tradeIdOptions: [],
      ioyardIdOptions: [],
      inYardWayOptions: [],
      outYardWayOptions: [],
      shipList: [],
      cargoList: [],
      conSignList: [],
      contNoList: [],
      billNOList: [],
      customsDeclarationList:[],
      countList: [],
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

  },
  created() {
    this.getDataList()
    this.getDic()
    this.getSelectAll()
    this.dataForm.searchValue = "0"
  },
  updated() {
    this.$nextTick(() => {
      this.$refs.dataList.doLayout()
    })
  },
  computed: {
    ...mapGetters(['permissions'])
  },
  methods: {
    getSelectAll() {
      getSelectCondition().then(res => {
        this.contNoList = res.data.data.listCont
        this.shipList = res.data.data.listShip
        this.cargoList = res.data.data.listCargo
        this.conSignList = res.data.data.listConSign
         this.billNOList =  res.data.data.listBillNO
        this.customsDeclarationList = res.data.data.listCustomsDeclaration
      })
    },
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true
      var preflag = 0;
      if (this.dataForm.ioyardId == 10) {
        this.dataForm.ioyardId = ''
        preflag = 1
        this.dataForm.prepareOutyardFlag = 1
      }
      fetchList(Object.assign({
        current: this.pageIndex,
        size: this.pageSize
      }, this.dataForm)).then(response => {
        if (response.data.data.countList != null) {
          this.countList = response.data.data.countList
          if (this.countList.length == 1) {
            if (this.countList[0].ioyardId == 0) {
              var map = { ioyardId: '1', weightWgt: 0, piecesNum: 0 }
              this.countList.unshift(map)
            } else {
              var map = { ioyardId: '0', weightWgt: 0, piecesNum: 0 }
              this.countList.push(map)
            }
          }
        }
        this.dataList = response.data.data.record.records
        this.totalPage = response.data.data.record.total

        if (preflag == 1) {
          this.$set(this.dataForm, "ioyardId", 10)
          this.$set(this.dataForm, "prepareOutyardFlag", '')
        }
        preflag = 0
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
      this.$confirm('是否确认还原ID为' + id, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function () {
        var map = { inoutYardNo: id }
        return inoutyardRecordRemove(map)
      }).then(data => {
        if (data.data.data.msg != undefined) {
          this.$message.success(data.data.data.msg)
        } else {
          this.$message.success(data.data.data.data)
        }

        this.getDataList()
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
      for (var i in this.tradeIdOptions) {
        if (row.tradeId == this.tradeIdOptions[i].value) {
          return this.tradeIdOptions[i].label
        }
      }
    },
    ioyardIdFormatter(row) {
      for (var i in this.ioyardIdOptions) {
        if (row.ioyardId == this.ioyardIdOptions[i].value) {
          if (row.ioyardId == 1) {
            if (row.prepareOutyardFlag != '' && row.prepareOutyardFlag != undefined) {
              return "预出库"
            } else {
              return this.ioyardIdOptions[i].label
            }
          } else {
            return this.ioyardIdOptions[i].label
          }

        }
      }
    },
    inYardWayFormatter(row) {
      for (var i in this.inYardWayOptions) {
        if (row.inYardWay == this.inYardWayOptions[i].value) {
          return this.inYardWayOptions[i].label
        }
      }
    },
    outYardWayFormatter(row) {
      for (var i in this.outYardWayOptions) {
        if (row.outYardWay == this.outYardWayOptions[i].value) {
          return this.outYardWayOptions[i].label
        }
      }
    },
    getDic() {
      getInoutIdDic().then(data => {
        this.inoutIdOptions = data.data.data
      })
      getTradeIdDic().then(data => {
        this.tradeIdOptions = data.data.data
      })
      getIoyardIdDic().then(data => {
        this.ioyardIdOptions = data.data.data
        this.ioyardIdOptions.push({ label: '预出库', value: 10 })
      })
      getInyardType().then(data => {
        this.inYardWayOptions = data.data.data
      })
      getOutYardType().then(data => {
        this.outYardWayOptions = data.data.data
      })
    },
    getSummaries(param) {
      const { columns, data } = param;
      const sums = [];
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '合计';
          return;
        }
        const values = data.map(item => Number(item[column.property]));
        if (!values.every(value => isNaN(value)) && (column.property == 'piecesNum' || column.property == 'weightWgt')) {
          sums[index] = values.reduce((prev, curr) => {
            const value = Number(curr);
            if (!isNaN(value)) {
              return (prev + curr);
            } else {
              return prev;
            }
          }, 0);
          sums[index] += ' ';
        } else {
          sums[index] = '';
        }
      });
      for (var i in sums) {
        if (!isNaN(Number(sums[i])) && sums[i] != '') {
          sums[i] = Number(sums[i].trim()).toFixed(2)
        }
      }
      return sums;
    },

    getIoyardDataList() {
      this.dataForm.searchValue = '1'
      this.getDataList()
    }
  }
}
</script>
<style>
#bz .el-form-item__content {
  width: 50%;
}
</style>
