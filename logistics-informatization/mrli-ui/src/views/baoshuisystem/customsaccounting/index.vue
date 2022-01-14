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
        <el-form-item label="作业委托人" prop="consignNam">
          <el-select
            clearable
            v-model="dataForm.consignNam"
            placeholder="作业委托人"
            filterable
          >
            <el-option
              v-for="(item, index) in clientInfoOptions"
              :key="index"
              :label="item.company"
              :value="item.company"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="货品名" prop="cargoNamCustoms" class="lb">
          <el-select
            clearable
            v-model="dataForm.cargoNamCustoms"
            placeholder="货品名"
            filterable
          >
            <el-option
              v-for="(item, index) in cargonamcustomsOptions"
              :key="index"
              :label="item.cargoNam"
              :value="item.cargoNam"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="类别(进区)" prop="inCategory" class="lb">
          <el-select
            clearable
            v-model="dataForm.inCategory"
            placeholder="类别(进区)"
            filterable
          >
            <el-option
              v-for="(item, index) in areaTypeDict"
              :key="index"
              :label="item.label"
              :value="item.label"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="类别(出区)" prop="outCategory" class="lb">
          <el-select
            clearable
            v-model="dataForm.outCategory"
            placeholder="类别(出区)"
            filterable
          >
            <el-option
              v-for="(item, index) in areaTypeDict"
              :key="index"
              :label="item.label"
              :value="item.label"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="核注清单号(入库)" prop="inCheckNum">
          <el-input
            v-model="dataForm.inCheckNum"
            placeholder="核注清单号(入库)"
          ></el-input>
        </el-form-item>
        <el-form-item label="核注清单号(出库)" prop="outCheckNum">
          <el-input
            v-model="dataForm.outCheckNum"
            placeholder="核注清单号(出库)"
          ></el-input>
        </el-form-item>
        <el-form-item label="报关单号(入库)" prop="inCustomsDeclarationNum">
          <el-input
            v-model="dataForm.inCustomsDeclarationNum"
            placeholder="报关单号(入库)"
          ></el-input>
        </el-form-item>
        <el-form-item label="报关单号(出库)" prop="outCustomsDeclarationNum">
          <el-input
            v-model="dataForm.outCustomsDeclarationNum"
            placeholder="报关单号(出库)"
          ></el-input>
        </el-form-item>
        <el-form-item label="货票号(入库)" prop="inWaybillNum">
          <el-input
            v-model="dataForm.inWaybillNum"
            placeholder="货票号(入库)"
          ></el-input>
        </el-form-item>
        <el-form-item label="货票号(出库)" prop="outWaybillNum">
          <el-input
            v-model="dataForm.outWaybillNum"
            placeholder="货票号(出库)"
          ></el-input>
        </el-form-item>
        <el-form-item label="入库日期范围" prop="inDteStr">
          <el-date-picker
            v-model="dataForm.inDteStr"
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
        <el-form-item label="出库日期范围" prop="outDteStr">
          <el-date-picker
            v-model="dataForm.outDteStr"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            align="right"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
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
            type="warning"
            icon="el-icon-refresh"
            @click="resetForm('dataForm')"
            >重置</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button
            v-if="permissions.customsaccounting_add"
            icon="el-icon-plus"
            type="primary"
            @click="addOrUpdateHandle()"
            hidden
            >新增</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button
            icon="el-icon-download"
            style="background: #da81f5; color: white"
            @click="expoprt()"
            >导出</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button
             icon="el-icon-plus"
            type="primary"
            @click="addMarge()"
            >新增</el-button
          >
        </el-form-item>
        <div>
            <div>
              <span>入库毛重:{{countAll.inRoughWet}}</span>
              <span>入库净重:{{countAll.inNetWet}}</span>
              <span>入库货品额:{{countAll.inCargoAccount}}</span>
              <span>出库毛重:{{countAll.outRoughWet}}</span>
              <span>出库净重:{{countAll.outNetWet}}</span>  


            </div>
        </div>
        
      </el-form>

      <div class="avue-crud" style="width: 100%">
        <el-table :data="dataList" border v-loading="dataListLoading"  height="550">
          <el-table-column
            label="序号"
            type="index"
            width="50"
            align="center"
            fixed="left"
          >
            <template slot-scope="scope">
              <span>{{ (pageIndex - 1) * pageSize + scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="consignNam"
            header-align="center"
            align="center"
            label="作业委托人"
            sortable
            show-overflow-tooltip
            width="160px"
          >
          </el-table-column>
          <el-table-column
            prop="cargoNamCustoms"
            header-align="center"
            align="center"
            label="货品名"
            sortable
            width="135px"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="cargoKindNam"
            header-align="center"
            align="center"
            label="货类"
            sortable
            width="135px"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="inDte"
            header-align="center"
            align="center"
            label="入库日期"
            sortable
            width="135px"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="inRoughWet"
            header-align="center"
            align="center"
            label="入库毛重（kg）"
            sortable
            width="135px"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="inNetWet"
            header-align="center"
            align="center"
            label="入库净重（kg）"
            sortable
            width="135px"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="inCargoAccount"
            header-align="center"
            align="center"
            label="入库货品额（美元）"
            sortable
            width="160px"
            show-overflow-tooltip
            :formatter="inCargoAccountFormat"
          >
          </el-table-column>
          <el-table-column
            prop="inOriginCountry"
            header-align="center"
            align="center"
            label="原产国（入库）"
            sortable
            width="135px"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="inFinalDestination"
            header-align="center"
            align="center"
            label="最终目的地（入库）"
            sortable
            width="155px"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="inCheckNum"
            header-align="center"
            align="center"
            label="核注清单号（入库）"
            sortable
            width="155px"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="inNuclearCouplet"
            header-align="center"
            align="center"
            label="核对联（入库）"
            sortable
            width="135px"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="inCustomsDeclarationNum"
            header-align="center"
            align="center"
            label="报关单号（入库）"
            show-overflow-tooltip
            sortable
            width="145px"
          >
          </el-table-column>
          <el-table-column
            prop="inIdentificationCod"
            header-align="center"
            align="center"
            label="识别代码（入库）"
            sortable
            width="145px"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="inWaybillNum"
            header-align="center"
            align="center"
            label="货票号（入库）"
            sortable
            width="145px"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="inCategory"
            header-align="center"
            align="center"
            label="类别（进区）"
            sortable
            width="145px"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="outDte"
            header-align="center"
            align="center"
            label="出库日期"
            sortable
            width="135px"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="outRoughWet"
            header-align="center"
            align="center"
            label="出库毛重（kg )"
            sortable
            width="135px"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="outNetWet"
            header-align="center"
            align="center"
            label="出库净重（kg）"
            sortable
            width="145px"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="outCargoAccount"
            header-align="center"
            align="center"
            label="出库货品额（美元）"
            sortable
            width="155px"
            show-overflow-tooltip
            :formatter="outCargoAccountFormat"
          >
          </el-table-column>
          <el-table-column
            prop="outOriginCountry"
            header-align="center"
            align="center"
            label="原产国（出库）"
            sortable
            width="155px"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="outFinalDestination"
            header-align="center"
            align="center"
            label="最终目的地（出库）"
            sortable
            width="155px"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="outCheckNum"
            header-align="center"
            align="center"
            label="核注清单号（出库）"
            show-overflow-tooltip
            sortable
            width="155px"
          >
          </el-table-column>
          <el-table-column
            prop="outNuclearCouplet"
            header-align="center"
            align="center"
            label="核对联（出库）"
            sortable
            show-overflow-tooltip
            width="155px"
          >
          </el-table-column>
          <el-table-column
            prop="outCustomsDeclarationNum"
            header-align="center"
            align="center"
            label="报关单号（出库）"
            sortable
            width="155px"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="outIdentificationCod"
            header-align="center"
            align="center"
            label="识别代码（出库）"
            sortable
            width="155px"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="outWaybillNum"
            header-align="center"
            align="center"
            label="货票号（出库）"
            sortable
            width="155px"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="outCategory"
            header-align="center"
            align="center"
            label="类别（出区）"
            sortable
            width="145px"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="operNam"
            header-align="center"
            align="center"
            label="添加人"
            sortable
            width="135px"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            prop="recordTim"
            header-align="center"
            align="center"
            label="添加时间"
            sortable
            width="135px"
            show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
            fixed="right"
            header-align="center"
            align="center"
            label="操作"
            width="222px"
          >
            <template slot-scope="scope">
              <el-button
                v-if="permissions.customsaccounting_edit"
                type="text"
                size="small"
                icon="el-icon-edit"
                @click="addOrUpdateHandle(scope.row.fId)"
                >修改</el-button
              >
              <el-button
                v-if="permissions.customsaccounting_edit"
                type="text"
                size="small"
                icon="el-icon-edit"
                @click="updateCustom(scope.row.fId)"
                >修改报关单号</el-button
              >
              <el-button
                v-if="permissions.customsaccounting_del"
                type="text"
                size="small"
                icon="el-icon-delete"
                @click="deleteHandle(scope.row.fId)"
                >删除</el-button
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
      <!-- 弹窗, 新增 / 修改 -->
      <table-form
        v-if="addOrUpdateVisible"
        ref="addOrUpdate"
        @refreshDataList="getDataList"
      ></table-form>

      <el-dialog
        title="修改报关单号"
        :visible.sync="centerDialogVisible"
        width="30%"
        center
      >
        <el-form
          :model="form"
          label-width="120px"
        >
          <el-form-item label="报关单号(入库)" >
            <el-input
              v-model="form.inCustomsDeclarationNum"
              placeholder="识别代码（入库）"
            ></el-input>
          </el-form-item>
          <el-form-item label="报关单号(出库)" >
            <el-input
              v-model="form.outCustomsDeclarationNum"
              placeholder="识别代码（出库）"
            ></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="centerDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateSubmit()">确 定</el-button>
        </span>
      </el-dialog>


     <el-dialog
    width="70%"
    title="新增"
    :close-on-click-modal="false"
    :visible.sync="invisible"
  >
   <el-form
        :inline="true"
        :model="checkForm"
        @keyup.enter.native="getcheckList()"
      >
       <el-form-item label="进区核注清单号" >
          <el-input
            v-model="checkForm.inCheckNum"
            placeholder="进区核注清单号"
            clearable
          ></el-input>
        </el-form-item>
         <el-form-item>
          <el-button
            type="success"
            icon="el-icon-search"
            @click="getCheckDataListQuery"
            >查询</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button
            type="warning"
            icon="el-icon-refresh"
            @click="resetCheckForm()"
            >重置</el-button
          >
        </el-form-item>
         <el-form-item>
          <el-button
            type="warning"
            icon="el-icon-refresh"
            @click="clickProduce(multipleSelection)"
            >生成</el-button
          >
        </el-form-item>
      </el-form>
    <el-table :data="dataListForm" border v-loading="dataListFormLoading"   @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55"> </el-table-column>
          <el-table-column
            label="序号"
            type="index"
            width="50"
            align="center"
            fixed="left"
          >
            <template slot-scope="scope">
              <span>{{ (pageIndexCheck - 1) * pageSizeCheck + scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="cargoNo"
            header-align="center"
            align="center"
            label="H.S"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="cargoNam"
            header-align="center"
            align="center"
            label="品名"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="format"
            header-align="center"
            align="center"
            label="规格"
          >
          </el-table-column>
          <el-table-column
            prop="inCustomsDeclaration"
            header-align="center"
            align="center"
            width="115"
            label="报关单号"
          >
          </el-table-column>
          <el-table-column
            prop="inCheckNum"
            header-align="center"
            align="center"
            width="115"
            label="核注清单号"
          >
          </el-table-column>
          <el-table-column
            prop="pieceNum"
            header-align="center"
            align="center"
            label="件数"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="transportMeans"
            header-align="center"
            align="center"
            label="运输工具"
          >
          </el-table-column>
          <el-table-column
            prop="importCountry"
            header-align="center"
            align="center"
            label="进口国别"
          >
          </el-table-column>
          <el-table-column
            prop="deadLoad"
            header-align="center"
            align="center"
            label="自重"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="markTxt"
            header-align="center"
            align="center"
            label="备注"
          >
          </el-table-column>

        </el-table>
       <span slot="footer" class="dialog-footer">
      <el-button @click="invisible = false">取消</el-button>
      <el-button type="primary" @click="inFormSubmit()"
        >确定</el-button
      >
    </span>
      <div class="avue-crud__pagination">
        <el-pagination
          @size-change="sizeChangeHandleAdd"
          @current-change="currentChangeHandleAdd"
          :current-page="pageIndexCheck"
          :page-sizes="[5, 10, 20, 100]"
          :page-size="pageSizeCheck"
          :total="totalPageCheck"
          background
          layout="total, sizes, prev, pager, next, jumper"
        >
        </el-pagination>
      </div>
    </el-dialog>
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
  addObj,
  saveObj,
  getAreaType,
  countAll
} from "@/api/baoshuisystem/customsaccountingstatistics";
import {pageOne,modifyShowFlag}from "@/api/baoshuisystem/inoutyardbody";
import { getClientInfoAll } from "@/api/baoshuisystem/clientinfo";
//import { getContractObjectInfoAll } from "@/api/baoshuisystem/contractobject";
import { getCargonamcustomsAll } from "@/api/baoshuisystem/cargonamcustoms";
import TableForm from "./customsaccountingstatistics-form";
import { mapGetters } from "vuex";
export default {
  data() {
    return {
      multipleSelection:[],
      countAll:{},
      checkForm:{
       key:"",
      },
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
      expertStatus: 0,
      centerDialogVisible: false,
      dataForm: {
        key: "",
      },
      inForm:{},
      params: {},
      invisible:false,
      //contractObjectOptions: [],
      cargonamcustomsOptions: [],
      clientInfoOptions: [],
      areaTypeDict: [],
      dataList: [],
      form:{},
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListForm:[],
      dataListLoading: false,
      dataListFormLoading: false,
      addOrUpdateVisible: false,
      pageIndexCheck: 1,
      pageSizeCheck: 5,
      totalPageCheck: 0,
    };
  },
  components: {
    TableForm,
  },
  created() {
    this.getDataList();
    this.getcheckList();
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  methods: {
    getcheckList(){
      this.dataListFormLoading = true
      pageOne(
        Object.assign(
          {
            current: this.pageIndexCheck,
            size: this.pageSizeCheck,
          },
          this.checkForm
        )
      ).then((response) => {
        this.dataListForm = response.data.data.records;
        this.totalPageCheck = response.data.data.total;
      });
       this.dataListFormLoading = false
    },
    inFormSubmit(){
      this.invisible = false
    },
    addMarge(){
      // addObj().then((data) => {
      //   this.dataListForm = data.data.data
      //   console.log(this.dataListForm)
      // })
      this.invisible = true
    },
    async expoprt() {
      if (this.expertStatus == 1) {
        await handleDown("海关账册录入统计表.xlsx", this.dataForm);
      } else {
        this.dataForm = {}
        await handleDown("海关账册录入统计表.xlsx", this.dataForm);
      }

    },
    async updateCustom(id) {

      this.centerDialogVisible = true;
      await getObj(id).then((response) => {
        this.form = response.data.data;
      });
    },
    handleSelectionChange(val) {
      //  this.multipleSelection = val;
     this.multipleSelection = val
    },
    clickProduce(data){
      if(data.length>0 && data.length <= 5 ){
          this.$confirm('点击生成海关账册统计表, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          console.log(data)
          saveObj(data).then(res =>{
            modifyShowFlag(data).then(res =>{
            this.$notify.success("生成成功");
            this.getcheckList()
            this.getDataList();
          })
        })
        })
      }else{
           this.$message.info("请至少选择一条数据，最多选择五条数据");
      }
    },
    async updateSubmit() {
      await putObj(this.form).then((response) => {
        this.$notify.success("修改成功");
        this.centerDialogVisible = false;
        this.form = {}
        this.getDataList();
      });
    },
    async getDataListQuery() {
      this.getDataList()
      countAll(this.dataForm).then(res =>{
        if(res.data.data != null){
           this.countAll = res.data.data
        }
      })
      this.expertStatus = 1
    },
    getCheckDataListQuery() {
      this.getcheckList()
    },
    // 获取数据列表
    async getDataList() {
      this.dataListLoading = true;
      fetchList(
        Object.assign(
          {
            current: this.pageIndex,
            size: this.pageSize,
          },
          this.dataForm
        )
      ).then((response) => {
        this.dataList = response.data.data.records;
        console.log("this.dataList ",this.dataList )
        this.totalPage = response.data.data.total;
      });
      this.dataListLoading = false;

      await getClientInfoAll().then((response) => {
        this.clientInfoOptions = response.data.data;
      });

      await getCargonamcustomsAll().then((response) => {
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
    // 每页数
    sizeChangeHandleAdd(val) {
      this.pageSizeCheck = val;
      this.pageIndexCheck = 1;
      this.getcheckList();
    },
    // 当前页
    currentChangeHandleAdd(val) {
      this.pageIndexCheck = val;
      this.getcheckList();
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
    resetCheckForm(){
      this.checkForm = {}
      this.getcheckList();
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
