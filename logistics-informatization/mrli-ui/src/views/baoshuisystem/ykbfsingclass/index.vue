<template>
  <div class="mod-config">
    <basic-container>
      <el-form
        :inline="true"
        :model="dataForm"
        @keyup.enter.native="getDataList()"
      >
        <el-form-item label="工班日期" prop="startDte">
          <!-- <el-date-picker
            v-model="dataForm.startDte"
            type="date"
            placeholder="选择日期"
            value-format="yyMMdd"
            style="width: 94%"
          >
          </el-date-picker> -->
          <el-date-picker
              v-model="dataForm.startDte"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              align="right"
              value-format="yyMMdd">
          </el-date-picker>



        </el-form-item>
        <el-form-item label="班次" prop="rqcbs" class="searchClass">
          <el-select
            v-model="dataForm.rqcbs"
            placeholder="请选择"
            filterable
            clearable
            style="width: 100%"
          >
            <el-option
              v-for="(item, index) in this.rqcbsOptions"
              :key="index"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="称别组" prop="cbs" class="searchClass">
          <el-select
            v-model="dataForm.cbs"
            placeholder="请选择"
            filterable
            clearable
            style="width: 100%"
          >
            <el-option
              v-for="(item, index) in this.cbsOptions"
              :key="index"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="货名">
           <el-select
              v-model="dataForm.hm"
              placeholder="货名"
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
          <el-form-item label="申请单位">
          <el-select
            v-model="dataForm.sqdw"
            placeholder="请选择"
            filterable
            clearable
          >
            <el-option
              v-for="(item, index) in this.consignOptions"
              :key="index"
              :label="item.shipperDoc"
              :value="item.shipperDoc"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getDataList">查询</el-button>
        </el-form-item>
        <el-form-item >
          <el-button type="primary" @click="printToExcelBtn">生成日操作量表</el-button>
        </el-form-item>
         <el-form-item >
          <el-button type="primary" @click="exportLists">导出列表</el-button>
        </el-form-item>
      </el-form>

      <div class="avue-crud">
        <el-table
          :data="dataList"
          border
          :summary-method="getSummaries"
          show-summary
          v-loading="dataListLoading"

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
            label="货票号"
          >
          </el-table-column>
          <el-table-column
            prop="hm"
            header-align="center"
            align="center"
            label="货名"
          >
          </el-table-column>
          <el-table-column
            prop="tdh"
            header-align="center"
            align="center"
            label="提单号"
          >
          </el-table-column>
          <el-table-column
            prop="cm"
            header-align="center"
            align="center"
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
            prop="csl"
            header-align="center"
            align="center"
            label="完成车"
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
            prop="benc"
            header-align="center"
            align="center"
            label="本班车"
          >
          </el-table-column>
          <el-table-column
            prop="weic"
            header-align="center"
            align="center"
            label="未回车"
          >
          </el-table-column>
          <el-table-column
            prop="benhz"
            header-align="center"
            align="center"
            label="本班货重"
          >
          </el-table-column>
          <el-table-column
            prop="cbs"
            header-align="center"
            align="center"
            label="称别组"
          >
          </el-table-column>
          <el-table-column
            prop="sqdw"
            header-align="center"
            align="center"
            label="申请单位"
          >
          </el-table-column>
          <el-table-column
            prop="rqcbs"
            header-align="center"
            align="center"
            label="班次"
          >
          </el-table-column>
          <el-table-column
            prop="inoutFlag"
            header-align="center"
            align="center"
            label="出入库"
          >
             <template slot-scope="scope">
              <el-button
                v-if="scope.row.inoutFlag == null"
                type="text"
                size="small"
                icon="el-icon-edit"
                @click="inOrOut(scope.row)"
                >出入库</el-button
              >
              <el-button
                v-if="scope.row.inoutFlag == 0"
                type="text"
                size="small"
                icon="el-icon-edit"
                @click="modifyInout(scope.row)"
                >出库</el-button
              >
              <el-button
                v-if="scope.row.inoutFlag == 1"
                type="text"
                size="small"
                icon="el-icon-edit"
                @click="modifyInout(scope.row)"
                >入库</el-button
              >
          </template>
          </el-table-column>
          <el-table-column
            prop="onInventory"
            header-align="center"
            align="center"
            label="上期库存"
          >
         <template slot-scope="scope">
             <el-button
                v-if="scope.row.onInventory==null"
                type="text"
                size="small"
                icon="el-icon-edit"
                @click="modifyInventory(scope.row)"
                >库存量</el-button
              >
              <el-button
                v-else
                type="text"
                size="small"
                @click="modifyInventory(scope.row)"
                >{{scope.row.onInventory}}</el-button
              >
         </template>
          </el-table-column>
          <el-table-column
            prop="thisInventory"
            header-align="center"
            align="center"
            label="本期库存"
          >
          </el-table-column>
        </el-table>

      </div>
      <el-dialog     :close-on-click-modal="false" title="选择出库或入库" :visible.sync="visible" >
        <template>
          <el-radio v-model="dataFormList.inoutFlag" label="0">出库</el-radio>
          <el-radio v-model="dataFormList.inoutFlag" label="1">入库</el-radio>
        </template>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="makeSure(count)">确 定</el-button>
        </span>
      </el-dialog>


      <el-dialog     :close-on-click-modal="false" title="选择库存量" :visible.sync="inventoryVisible" >
        <template>
          <el-form :model="dataFormList">
          <el-form-item label="上期库存量" prop="onInventory" :rules="[
      { required: true, message: '上期库存量不能为空',trigger: 'blur'},
    ]">
            <el-input v-model="dataFormList.onInventory" placeholder="上期库存量" @input="getOnInventory($event,dataFormList)"></el-input>
          </el-form-item>
          <el-form-item label="本期库存量"  prop="thisInventory" :rules="[
      { required: true, message: '本期库存量不能为空',trigger: 'blur'},
    ]">
            <el-input v-model="dataFormList.thisInventory" placeholder="本期库存量" @input="getThisInventory($event,dataFormList)"></el-input>
          </el-form-item>
          </el-form>
        </template>
        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="inventorySure()">确 定</el-button>
        </span>
      </el-dialog>
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
  getShipperDoc
} from '@/api/baoshuisystem/getdictdata'
import {
  getInsertValue,
} from "@/api/baoshuisystem/contract";
import {
  fetchList,
  printToExcel,
  exportList,
  getAll,
} from '@/api/baoshuisystem/ykbfsingclass'
import {
  addObj,getObj,putObj
} from '@/api/baoshuisystem/singclassinfo'
import {
  getPortCbz,
  getportRqcbs
} from '@/api/baoshuisystem/getdictdata'
import { mapGetters } from 'vuex'
import XEUtils from 'xe-utils'
import { onlyNumOnePoint } from "@/util/util";
export default {
  data() {
    return {
      expotDataList:[],
      cargoNamOptions: [],
      inOut:false,
      dataForm: {
        key: ''
      },
      dataTableList:[],
      dataFormList:{},
      showclose:false,
      dataList: [],
      consignOptions: [],
      pageIndex: 1,
      pageSize: 10,
      inventoryVisible:false,
      totalPage: 0,
      count:0,
      copyList:{},
      visible: false,
      dataListLoading: false,
      addOrUpdateVisible: false,
      cbsOptions: [],
      rqcbsOptions: []
    }
  },
  components: {

  },
  created() {
    // this.getDataList()
    this.getDic()
    this.getInfo()
  },
  computed: {
    ...mapGetters(['permissions'])
  },
  methods: {
    getInfo(){
      getInsertValue().then((response) => {
          this.cargoNamOptions = response.data.data.contractObjects
        });
         getShipperDoc().then(resp => {
        this.consignOptions = resp.data.data
      })
    },
    //修改上期获得本期
    onlyNumOnePoint,
    getOnInventory(data,item){
      if(item.inoutFlag==0){
        this.dataFormList.onInventory = this.onlyNumOnePoint(data);
        this.dataFormList.thisInventory = (XEUtils.subtract(XEUtils.toNumber(this.dataFormList.onInventory),XEUtils.toNumber(item.benhz))).toFixed(2)
      }else{
        this.dataFormList.onInventory = this.onlyNumOnePoint(data);
        this.dataFormList.thisInventory =  (XEUtils.add(XEUtils.toNumber(this.dataFormList.onInventory),XEUtils.toNumber(item.benhz))).toFixed(2)
      }
    },

    getThisInventory(data){
       this.dataFormList.thisInventory = this.onlyNumOnePoint(data);
    },
    inventorySure(){
      this.inventoryVisible = false
      this.dataFormList.bhrqcbs = this.dataFormList.bh+this.dataFormList.rqcbs
        if( this.dataFormList.inoutFlag){
        putObj(this.dataFormList).then(res =>{
                  this.$notify.success("修改数量成功")
                  this.inventoryVisible = false
                  this.getDataList()
                })

      }else{
        this.$message({
            type: 'info',
            message: '请先填写出入库'
          });
          this.inventoryVisible = true
      }

    },
    makeSure(count){
      if(count== 0){
        //点击确定新增
        addObj(this.dataFormList).then(res =>{
          this.$notify.success("修改出入库成功")
          this.visible = false
           this.getDataList()
        })
        .catch(() => {
          this.$notify.warning("修改失败")
          this.visible = false;
        });
      }else{
        putObj(this.dataFormList).then(res =>{
          this.$notify.success("修改出入库成功")
          this.visible = false
           this.getDataList()
        })
      }
    },
    inOrOut(data){
        this.dataFormList = XEUtils.clone(data,true)
        console.log("this.dataFormList",this.dataFormList)
        this.visible = true;
        this.count = 0
    },
     modifyInout(data){
        this.dataFormList = XEUtils.clone(data,true)
        this.visible = true
        this.count = 1
     },
     modifyInventory(data){
      //  this.dataFormList = JSON.parse(JSON.stringify(data))
      this.dataFormList = XEUtils.clone(data,true)
      this.inventoryVisible = true
     },
    // 获取数据列表
    async getDataList() {
      this.dataListLoading = true
      await fetchList(Object.assign({
        current: this.pageIndex,
        size: this.pageSize
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
    getDic() {
      getPortCbz().then(resp => {
        this.cbsOptions = resp.data.data
      })
      getportRqcbs().then(resp => {
        this.rqcbsOptions = resp.data.data
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
        if (!values.every(value => isNaN(value)) && (
          column.property == 'wcl'
          || column.property == 'csl'
          || column.property == 'jhl'
          || column.property == 'syl'
          || column.property == 'benc'
          || column.property == 'weic'
          || column.property == 'benhz'
          || column.property == 'benhz')) {
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
    printToExcelBtn() {
      if (this.dataForm.startDte == undefined || this.dataForm.startDte == '') {
        this.$notify.error("请选择日期！")
        return
      }
      if(Number(this.dataForm.startDte[1]) - Number(this.dataForm.startDte[0]) == 1){
        printToExcel(this.dataForm).then(resp => {

      })
      }else{
        this.$notify.error("只能导出日操作量")
        return
      }

    },
    async exportLists(){
      await getAll(this.dataForm).then(res =>{
       this.expotDataList = res.data.data
      })
      console.log(this.dataList)
      await exportList(this.expotDataList).then(res =>{})
      //  console.log(this.dataList)
     },

  }
}
</script>
<style>
.searchClass {
  width: 19%;
}
.searchClass .el-form-item__content {
  width: 75% !important;
}
</style>
