<template>
  <div class="mod-config">
    <basic-container>
      <el-form
        :inline="true"
        :model="dataForm"
        @keyup.enter.native="getDataList()"
        ref="dataForm"
        size="mini"
      >
        <el-form-item label="入库日期" prop="inDte">
           <el-date-picker
		      v-model="dataForm.inDte"
		      type="month"
		      value-format="yyyy-MM"
		      placeholder="选择月">
		    </el-date-picker>
        </el-form-item>
        <!--<el-form-item label="货种" prop="inDte">
           <el-select
            v-model="dataForm.cargoNamCustoms"
            placeholder="请选择"
            filterable
            clearable
          >
            <el-option
             v-for="(item, index) in cargonamcustomsOptions"
              :key="index"
              :label="item.cargoNam"
              :value="item.cargoNam"
            >
            </el-option>
          </el-select>
        </el-form-item>-->
        
        <el-form-item label="货类" prop="inDte">
           <el-select
            v-model="dataForm.cargoKindNam"
            placeholder="请选择"
            filterable
            clearable
          >
            <el-option
             v-for="(item, index) in cargonamcustomsOptions"
              :key="index"
              :label="item.cargoKindNam"
              :value="item.cargoKindNam"
            >
            </el-option>
          </el-select>
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
            icon="el-icon-download"
            style="background: #da81f5; color: white"
            v-if="permissions.manipulated_cargo_export"
            @click="expoprt()"
            >导出</el-button
          >
        </el-form-item>
      </el-form>

      <div >
        <el-table :data="dataList"
        	v-loading="dataListLoading" 
        	style="width: 100%">
          	<el-table-column :label="title" align="center">
          		<el-table-column :label="titleSecond" align="center">
	          		<el-table-column :label="mounthTitle" align="center">
	          			<el-table-column label="货类" prop="cargoKindNam">
	          			</el-table-column>
	          			<el-table-column label="货主" prop="consignNam">
	          			</el-table-column>
	          			<el-table-column label="当月入库量(吨)" prop="minNetWet">
	          			</el-table-column>
	          			<el-table-column label="当月出库量(吨)" prop="moutNetWet">
	          			</el-table-column>
	          		</el-table-column>
	          		<el-table-column :label="yearTitle" align="center">
	          			<el-table-column label="年累计入库量(吨)" prop="yinNetWet">
	          			</el-table-column>
	          			<el-table-column label="年累计出库量(吨)" prop="youtNetWet">
	          			</el-table-column>
	          			<el-table-column label="本期库存" prop="ykucun">
	          			</el-table-column>
	          			
	          		</el-table-column>
	          	</el-table-column>
          	</el-table-column>
        </el-table>
        
      </div>

      <!--<div class="avue-crud__pagination">
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
      </div>-->

    
    </basic-container>
  </div>
</template>

<script>
import {
  fetchList,
  delObj,
  putObj,
  getObj,
  handleDown,
  getAreaType,
  getManipulatedCargo,
  getManipulatedExcel,
  exportManipulatedCargoExcel,
} from "@/api/baoshuisystem/customsaccountingstatistics";
import { getClientInfoAll } from "@/api/baoshuisystem/clientinfo";
//import { getContractObjectInfoAll } from "@/api/baoshuisystem/contractobject";
import { getCargonamcustomsAll } from "@/api/baoshuisystem/cargonamcustoms";
import { all } from "@/api/baoshuisystem/goodscategory.js";
//import TableForm from "./customsaccountingstatistics-form";
import { mapGetters } from "vuex";
export default {
  data() {
    return {
      expertStatus: 0,
      dataForm: {
        key: "",
        inDte: "",
      },
      params: {},
      //contractObjectOptions: [],
      cargonamcustomsOptions: [],
      clientInfoOptions: [],
      areaTypeDict: [],
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      addOrUpdateVisible: false,
      title: '保税物流中心各货种操作量',
      mounthTitle: '10月份',
      titleSecond: '',
      yearTitle: ''
      
    };
  },
 
  created() {
  	 var date=new Date;
	 var year=date.getFullYear(); 
	 var month=date.getMonth()+1;
	 var day=date.getDate();
	 month =(month<10 ? "0"+month:month); 
	 day =(day<10 ? "0"+day:day); 
	 var mydate = (year.toString()+'-'+month.toString());
  	this.dataForm.inDte = mydate
  	this.dataForm.year = year
  	if(this.dataForm.inDte){
  		this.title = '保税物流中心各货种操作量'
  		this.mounthTitle= '当月出入库量'
  		this.titleSecond = year.toString()+'-'+month.toString()+'-'+day
  	}
    this.getDataList();
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  methods: {
    async expoprt() {
    	await exportManipulatedCargoExcel(this.title+'.xlsx', this.dataForm);
    },
    async updateCustom(id) {
    
      this.centerDialogVisible = true;
      await getObj(id).then((response) => {
        this.form = response.data.data;
      });
    },
    async getDataListQuery() {
      this.getDataList()
      this.expertStatus = 1
    },
    // 获取数据列表
    async getDataList() {
    	if(this.dataForm.inDte == null){
	 	this.$message.error('请填写日期！')
	 	return ;
	 }
    	if(this.dataForm.inDte){
    		console.log(this.dataForm.inDte)
    		this.dataForm.yearStart = this.dataForm.inDte.substring(0,4)+'-01'
    		this.dataForm.yearEnd = this.dataForm.inDte
    		this.dataForm.oldYearStart = this.dataForm.inDte.substring(0,4)-1+'-01'
    		this.dataForm.oldYearEnd = this.dataForm.inDte.substring(0,4)-1+this.dataForm.inDte.substring(5,7)
    		
    		this.title = '保税物流中心各货种操作量'
  			this.mounthTitle= '当月出入库量'
  			this.yearTitle = "01-"+this.dataForm.inDte.substring(5,7)+"月年累计出入库量(吨)"
  	}
    	console.log(this.dataForm);
      this.dataListLoading = true;
    
      this.dataListLoading = false;
      
      getManipulatedCargo(
        Object.assign(
          {
            current: this.pageIndex,
            size: this.pageSize,
          },
          this.dataForm
        )
      ).then((response) => {
      	console.log(response)
        this.dataList = response.data.data;
        this.totalPage = response.data.data.total;
      });

      await getClientInfoAll().then((response) => {
        this.clientInfoOptions = response.data.data;
      });

      await all().then((response) => {
        this.cargonamcustomsOptions = response.data.data;
      });

      await getAreaType().then((response) => {
        this.areaTypeDict = response.data.data;
      });
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageIndex = 1;
      this.getDataList();
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.getDataList();
    },
    // 新增 / 修改
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true;
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id);
      });
    },

    resetForm(formName) {
      if (this.$refs[formName] !== undefined) {
        this.$refs[formName].resetFields();
      }
      this.getDataList();
    },
    // 删除
    deleteHandle(id) {
      this.$confirm("是否确认删除被本条数据", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delObj(id);
        })
        .then((data) => {
          this.$message.success("删除成功");
          this.getDataList();
        });
    },
    outCargoAccountFormat(row, colunm) {
      if (row.outCargoAccount != null) {
        var value = row.outCargoAccount.toString();
        if (!value) return "0.00";
        var intPart = Number(value).toFixed(0); //获取整数部分
        var intPartFormat = intPart.toString().replace(/(\d)(?=(?:\d{3})+$)/g, "$1,"); //将整数部分逢三一断
        var floatPart = ".00"; //预定义小数部分
        var value2Array = value.split(".");
        //=2表示数据有小数位
        if (value2Array.length == 2) {
          floatPart = value2Array[1].toString(); //拿到小数部分
          if (floatPart.length == 1) {
            //补0,
            return intPartFormat + "." + floatPart + "0";
          } else {
            return intPartFormat + "." + floatPart;
          }
        } else {
          return intPartFormat + floatPart;
        }
      } else {
        return '';
      }
    },
    inCargoAccountFormat(row, colunm) {
      if (row.inCargoAccount != null) {
        var value = row.inCargoAccount.toString();
        if (!value) return "0.00";
        var intPart = Number(value).toFixed(0); //获取整数部分
        var intPartFormat = intPart.toString().replace(/(\d)(?=(?:\d{3})+$)/g, "$1,"); //将整数部分逢三一断
        var floatPart = ".00"; //预定义小数部分
        var value2Array = value.split(".");
        //=2表示数据有小数位
        if (value2Array.length == 2) {
          floatPart = value2Array[1].toString(); //拿到小数部分
          if (floatPart.length == 1) {
            //补0,
            return intPartFormat + "." + floatPart + "0";
          } else {
            return intPartFormat + "." + floatPart;
          }
        } else {
          return intPartFormat + floatPart;
        }
      } else {
        return '';
      }
    },
  },
};
</script>
<style>
.lb {
  width: 16%;
}
.lb .el-form-item__content {
  width: 60%;
}
</style>