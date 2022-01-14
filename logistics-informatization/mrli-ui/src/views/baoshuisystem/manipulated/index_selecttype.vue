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
        <el-form-item label="日期" prop="inDte">
           <el-date-picker
		      v-model="dataForm.nowYear"
		      type="year"
		      value-format="yyyy"
		      placeholder="选择月">
		    </el-date-picker>
        </el-form-item>
        
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
            v-if="permissions.manipulated_selecttype_export"
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
          		<el-table-column  align="center" >
          			<el-table-column  align="center" prop="mon">
          			</el-table-column>
          		</el-table-column>
          		
          		<el-table-column :label="nowYearLabel" align="center" >
          		<el-table-column label="入库量" align="center" prop="yearNewInNetWet">
          		</el-table-column>
          		</el-table-column>
          		
          		<el-table-column :label="oldYearLabel" align="center" >
          		<el-table-column label="入库量" align="center" prop="yearOldInNetWet">
          		</el-table-column>
          		</el-table-column>
          		
          		<el-table-column label="同比" align="center" >
          		<el-table-column label="%" align="center" prop="yearInComparedWet">
          		</el-table-column>
          		</el-table-column>
          		
          		<el-table-column :label="nowYearLabel" align="center" >
          		<el-table-column label="出库量" align="center" prop="yearNewOutNetWet">
          		</el-table-column>
          		</el-table-column>
          		
          		<el-table-column :label="oldYearLabel" align="center" >
          		<el-table-column label="出库量" align="center" prop="yearOldOutNetWet">
          		</el-table-column>
          		</el-table-column>
          		
      			<el-table-column label="同比" align="center" >
          		<el-table-column label="%" align="center" prop="yearOutComparedWet">
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
  getManipulatedExcel,
  getOutAmount,
  getOutAmountExcel,
  getCargoKind,
  getCargoKindExcel
} from "@/api/baoshuisystem/customsaccountingstatistics";
import { getClientInfoAll } from "@/api/baoshuisystem/clientinfo";
//import { getContractObjectInfoAll } from "@/api/baoshuisystem/contractobject";
import { getCargonamcustomsAll, all } from "@/api/baoshuisystem/goodscategory.js";
//import TableForm from "./customsaccountingstatistics-form";
import { mapGetters } from "vuex";
export default {
  data() {
    return {
      expertStatus: 0,
      dataForm: {
        key: "",
        inDte: "",
        nowYear:"",
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
      title: '',
      oldYearLabel: '',
      nowYearLabel: '',
    };
  },
 
  created() {
  	this.getCargo();
	 var date=new Date;
	 var year=date.getFullYear(); 
//	 var month=date.getMonth()+1;
//	 month =(month<10 ? "0"+month:month); 
//	 var mydate = (year.toString()+'-'+month.toString());
//	 var myoldYeardate = ((year-1).toString()+'-'+month.toString());
//	 var myyearOldMonthdate = (year.toString()+'-'+(month-1).toString());
//	this.dataForm.inDte = mydate
//	this.dataForm.year = year
//	this.dataForm.oldYearMonth = myoldYeardate
//	this.dataForm.yearOldMonth = myyearOldMonthdate
//	this.dataForm.oldYear = (year-1).toString()
  	this.nowYearLabel= year+"年"
    this.oldYearLabel= (year-1)+"年"
    this.dataForm.nowYear = year+""
    this.title = "请选择货类"
//  this.getDataList();
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  methods: {
    async expoprt() {
    	console.log(this.dataForm)
	    	if(this.dataForm.nowYear == null){
		 	this.$message.error('请填写日期！')
		 	return ;
		 }
    	
    	if(this.dataForm.cargoKindNam == null || this.dataForm.cargoKindNam == ""){
		 	this.$message.error('请填写货类！')
		 	return ;
		 }
    	await getCargoKindExcel(this.dataForm.cargoKindNam+'货类信息报表.xlsx', this.dataForm);
//    if (this.expertStatus == 1) {
//      await handleDown("海关账册录入统计表.xlsx", this.dataForm);
//    } else {
//      this.dataForm = {}
//      await handleDown("海关账册录入统计表.xlsx", this.dataForm);
//    }

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
	 if(this.dataForm.nowYear == null){
	 	this.$message.error('请填写日期！')
	 	return ;
	 }
    	
    	if(this.dataForm.cargoKindNam == null || this.dataForm.cargoKindNam == ""){
	 	this.$message.error('请填写货类！')
	 	return ;
	 }
    	console.log(this.dataForm)
    	var intvalue=Number(this.dataForm.nowYear);
    	this.dataForm.oldYear = (intvalue-1)+""
    	
    	this.oldYearLabel= (intvalue-1)+"年"
    	this.nowYearLabel=this.dataForm.nowYear+"年"
    	this.title = this.dataForm.cargoKindNam
    	
    	
      this.dataListLoading = true;
      getCargoKind(
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
//      this.totalPage = response.data.data.total;
      });
      this.dataListLoading = false;

      await getClientInfoAll().then((response) => {
        this.clientInfoOptions = response.data.data;
      });

      

//    await getAreaType().then((response) => {
//      this.areaTypeDict = response.data.data;
//    });
    },
    async getCargo(){
    	await all().then((response) => {
      	console.log(response)
        this.cargonamcustomsOptions = response.data.data;
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