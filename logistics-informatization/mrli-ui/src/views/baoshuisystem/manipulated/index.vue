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
            v-if="permissions.manipulated_data_export"
            @click="expoprt()"
            >导出</el-button
          >
        </el-form-item>
      </el-form>

      <div >
        <el-table :data="dataList"
        	v-loading="dataListLoading" 
        	show-summary
        	style="width: 100%">
          	<el-table-column :label="title" align="center">
          		<el-table-column :label="mounthTitle" align="center">
          			<el-table-column label="货类" prop="cargoKindNam">
          			</el-table-column>
          			<el-table-column label="入库量(吨)" prop="minNetWet">
          			</el-table-column>
          			<el-table-column label="出库量(吨)" prop="moutNetWet">
          			</el-table-column>
          			<el-table-column label="一线出口贸易额(万美元)" prop="moutCargoAccount">
          			</el-table-column>
          		</el-table-column>
          		<el-table-column label="年累计" align="center">
          			<el-table-column label="年累计入库量(吨)" prop="yinNetWet">
          			</el-table-column>
          			<el-table-column label="入库货值(万美元)" prop="yinCargoAccount">
          			</el-table-column>
          			<el-table-column label="年累计出库量(吨)" prop="youtNetWet">
          			</el-table-column>
          			<el-table-column label="出库货值(万美元)" prop="youtCargoAccount">
          			</el-table-column>
          			<el-table-column label="年累计一线出口贸易额(万美元)" prop="yfirstOutCargoAccount">
          			</el-table-column>
          			<el-table-column label="年累计操作量" prop="ysumNetWet">
          			</el-table-column>
          			<el-table-column label="累计操作量同比" prop="ynetWetScale">
          			</el-table-column>
          			<el-table-column label="年累计进出口货值" prop="ysumCargoAccount">
          			</el-table-column>
          			<el-table-column label="累计进出口货值同比" prop="ycargoAccountScale">
          			</el-table-column>
          			
          		</el-table-column>
          	</el-table-column>
        </el-table>
        <el-table :data="dataListLast" :row-style="{height:'100px'}" style="font-size: 20px;font-weight: 600;">
        	<el-table-column prop="param"   align="left">
        		
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
  getManipulatedForNew,
  getManipulatedExcel,
  expoprtManipulatedForNewExcel,
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
      title: 'xx年x月及x-x月保税货物数据统计',
      mounthTitle: '10月份',
      dataListLast:[
      {param:'2021年9月操作量4.35万吨,一线出口贸易额0.648亿美元。2021年1-9月操作量587.47万吨,同比2020年下降41.88%,年累计进出口货值28.60亿美元,同比下降3.83%。一日游9月份完成55票,年累计完成652票'}
      ]
    };
  },
 
  created() {
  	 var date=new Date;
	 var year=date.getFullYear(); 
	 var month=date.getMonth()+1;
	 month =(month<10 ? "0"+month:month); 
	 var mydate = (year.toString()+'-'+month.toString());
  	this.dataForm.inDte = mydate
  	this.dataForm.year = year
  	if(this.dataForm.inDte){
  		this.title = year+'年'+this.dataForm.inDte.substring(5,7)+'月及01-'+this.dataForm.inDte.substring(5,7)+'月保税货物数据统计'
  		this.mounthTitle= this.dataForm.inDte.substring(5,7)+'月份'
  	}
    this.getDataList();
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  methods: {
    async expoprt() {
    	await expoprtManipulatedForNewExcel(this.title+'.xlsx', this.dataForm);
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
    		let a = this.dataForm.inDte.substring(5,7)
    		let month2 = a-1
    		month2 =(month2<10 ? "0"+month2:month2); 
    		console.log(month2)
    		this.dataForm.inDteStart = this.dataForm.inDte.substring(0,4)+'-'+month2+'-25'
    		if((a-1) == 0){
    			this.dataForm.inDteStart = this.dataForm.inDte.substring(0,4)-1+'-12-25'
    		}
    		this.dataForm.inDteEnd = this.dataForm.inDte+'-25'
    		
//  		this.dataForm.yearStart = this.dataForm.inDte.substring(0,4)+'-01'
//  		this.dataForm.yearEnd = this.dataForm.inDte

			this.dataForm.yearStart = this.dataForm.inDte.substring(0,4)-1+'-12-25'
    		this.dataForm.yearEnd = this.dataForm.inDte+'-25'
    		
    		this.dataForm.oldYearStart = this.dataForm.inDte.substring(0,4)-2+'-12-25'
    		this.dataForm.oldYearEnd = this.dataForm.inDte.substring(0,4)-1+'-'+this.dataForm.inDte.substring(5,7)+'-25'
    		
    		this.title = this.dataForm.inDte.substring(0,4)+'年'+this.dataForm.inDte.substring(5,7)+'月及01-'+this.dataForm.inDte.substring(5,7)+'月保税货物数据统计'
  		this.mounthTitle= this.dataForm.inDte.substring(5,7)+'月份'
  	}
    	console.log(this.dataForm);
//  	return;
    	
      this.dataListLoading = true;
      getManipulatedForNew(
        Object.assign(
          {
            current: this.pageIndex,
            size: this.pageSize,
          },
          this.dataForm
        )
      ).then((response) => {
      	console.log(response)
        this.dataList = response.data.data.list;
        this.dataListLast[0].param = response.data.data.param;
        this.totalPage = response.data.data.total;
      });
      this.dataListLoading = false;

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