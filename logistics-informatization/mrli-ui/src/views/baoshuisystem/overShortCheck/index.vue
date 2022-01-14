<template>
  <div class="mod-config">
    <basic-container>
      <el-form
        :inline="true"
        :model="dataForm"
        ref="dataForm"
        @keyup.enter.native="getDataList()"
      >
        <el-form-item label="入库号" prop="ioyardNo">
          <el-input v-model="dataForm.ioyardNo" placeholder="入库号"></el-input>
        </el-form-item>

        <el-form-item label="班组" prop="bz">
          <el-input v-model="dataForm.bz" placeholder="班组"></el-input>
        </el-form-item>
        <el-form-item label="作业委托人" prop="consignNam">
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
        <el-form-item label="船名" prop="shipNam">
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
        <el-form-item label="货名" prop="cargoNam">
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
        <el-form-item>
          <el-button type="primary" @click="getDataList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="resetForm('dataForm')"
            >重置</el-button
          >
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
          <!-- <el-table-column
            prop="unitCod"
            header-align="center"
            align="center"
            label="单位代码"
          >
          </el-table-column> -->
          <el-table-column
            prop="ioyardDte"
            header-align="center"
            align="center"
            label="入库日期"
          >
          </el-table-column>
          <el-table-column
            prop="ioyardNo"
            header-align="center"
            align="center"
            label="货票编号"
          >
          </el-table-column>
          <!-- <el-table-column
            prop="inoutYardNo"
            header-align="center"
            align="center"
            label="入库单号"
          >
          </el-table-column> -->
          <el-table-column
            prop="contNo"
            header-align="center"
            align="center"
            label="合同编号"
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
            prop="billNo"
            header-align="center"
            align="center"
            label="提单号"
          >
          </el-table-column>
          <el-table-column
            prop="inoutId"
            header-align="center"
            align="center"
            label="进出口"
            :formatter="inoutIdFormatter"
          >
          </el-table-column>
          <el-table-column
            prop="tradeId"
            header-align="center"
            align="center"
            label="内外贸"
            :formatter="tradeIdFormatter"
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
            prop="consignNam"
            header-align="center"
            align="center"
            label="代理货主"
          >
          </el-table-column>
          <el-table-column
            prop="cargoKindNam"
            header-align="center"
            align="center"
            label="货类"
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
            prop="cPkgKindNam"
            header-align="center"
            align="center"
            label="包装"
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
            label="完成量"
          >
          </el-table-column>
          <el-table-column
            header-align="center"
            align="center"
            label="审核类型"
          >
          <template slot-scope="scope">
            {{getStatus(scope.row.overShortType)}}
          </template>
          </el-table-column>
          <el-table-column
            header-align="center"
            align="center"
            label="操作"
            fixed="right"
            width="200px"
          >
            <template slot-scope="scope">
              <el-button
                type="text"
                size="small"
                icon="el-icon-caret-right"
                v-if="permissions.over_short_check"
                @click="check(scope.row)"
                >审批</el-button
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

      <el-dialog title="货溢短量审核" :visible.sync="dialogFormVisible">
        <el-form :model="checkForm" ref="checkForm" label-position="top">
          <el-form-item label="审核类型">
            <el-input v-model="status" readonly></el-input>
          </el-form-item>
          <el-form-item label="审批意见">
            <el-input
              v-model="checkForm.opinionExplain"
              placeholder="审批意见"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="下一环节审批人"
            v-if="usernameOptions.length > 0"
          >
            <el-select
              v-model="checkForm.username"
              style="width: 100%"
              filterable
              placeholder="请选择"
            >
              <el-option
                v-for="(item, index) in usernameOptions"
                :key="index"
                :label="item.manName"
                :value="item.username"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <div>
            <span>附件:</span>
            <div v-for="(item, index) in sysFileOptions" :key="index">
              <span
                @click="downloadFile(item.fileName, item.bucketName)"
                style="cursor: pointer"
              >
                {{ item.original }}</span
              >
            </div>
          </div>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="selectClick(2)">审批驳回</el-button>
          <el-button type="primary" @click="selectClick(1)">审批通过</el-button>
        </div>
      </el-dialog>
      <!-- 弹窗, 新增 / 修改 -->
    </basic-container>
  </div>
</template>

<script>
import {
 getSelectCondition
} from '@/api/baoshuisystem/ioyard'
import { destroyImage, getOne, getOutyardType, getOverShortCargo, submitCheck } from '@/api/baoshuisystem/instrogedetail'
import { getSelectContract, getFileInfo, handleDown } from '@/api/baoshuisystem/contract'
import { selectNextCheckUser } from '@/api/baoshuisystem/approvalprocess'
import { getProcessByBillFidAndUserInfo as getApprovalOne } from '@/api/baoshuisystem/approvalprocessset'
import { getInoutIdDic, getTradeIdDic, getInyardType } from '@/api/baoshuisystem/getdictdata'
import { getInoutHeadBody } from '@/api/baoshuisystem/inoutyardhead'
import { mapGetters } from 'vuex'
import { dateFormat } from "@/util/date";
export default {
  data() {
    return {
      dataForm: {
        key: '',
        // ioyardId: '0',
        // bh: '',
        // inoutYardNo: '',
        processNo: 2,
        isAgree: 0

      },
      checkForm: {},
      dataList: [],
      shipList: [],
      cargoList: [],
      conSignList: [],
      contNoList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      addOrUpdateVisible: false,
      dialogFormVisible: false,
      usernameOptions: [],
      contractOptions: [],
      outStrogeForm: {},
      outyardTypeOptions: [],
      inoutIdOptions: [],
      tardeIdOptions: [],
      inyardTypeOptions: [],
      sysFileOptions: [],
      prepareFlag: 0,
      dialogTitle: 0,
      status:"",
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
    }
  },
  components: {

  },
  created() {
    this.getDataList()
    this.getSelectAll()
  },
  computed: {
    ...mapGetters(['permissions'])
  },
  methods: {
    getStatus(type){
    if(type == "0"){
          return this.dataForm.overShortType = "货溢"
      }else if(type == "1"){
          return this.dataForm.overShortType = "短量"
      }
    },
     getSelectAll() {
      getSelectCondition().then(res => {
        this.contNoList = res.data.data.listCont
        this.shipList = res.data.data.listShip
        this.cargoList = res.data.data.listCargo
        this.conSignList = res.data.data.listConSign
      })
    },
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true
      this.checkForm = {
        bh: '',
        inyardNo: '',
        hz: '',
        bz: '',
        prepareFlag: '',
        gbhz: '',
        bghz: '',
        jqbgdh: '',
        fId: ''
      }
      getOverShortCargo(Object.assign({
        current: this.pageIndex,
        size: this.pageSize,

      }, this.dataForm)).then(response => {
        this.dataList = response.data.data.records
        this.totalPage = response.data.data.total
      })
      getSelectContract().then(data => {
        this.contractOptions = data.data.data
      })
      getOutyardType().then(data => {
        this.outyardTypeOptions = data.data.data
      })
      getInoutIdDic().then(data => {
        this.inoutIdOptions = data.data.data
      })
      getTradeIdDic().then(data => {
        this.tardeIdOptions = data.data.data
      })
      getInyardType().then(data => {
        this.inyardTypeOptions = data.data.data
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
    check(row) {
      console.log("row",row)
      if(row.overShortType == 1){
        this.status = "短量"
        console.log(this.status)
      }else if(row.overShortType == 0){
        this.status = "货溢"
      }
      this.dialogFormVisible = true
      this.$set(this.checkForm, "billFid", row.inoutYardNo)
      this.$set(this.checkForm, "overShortType", row.overShortType)
      this.$set(this.checkForm, "processNo", '2')
      var map = { billFid: row.inoutYardNo, processNo: '2' }
      var fileMap = { table: 'ioyard', tableFid: row.inoutYardNo }
      getFileInfo(fileMap).then(resp => {
        this.sysFileOptions = resp.data.data
      })
      getApprovalOne(map).then(resp => {
        this.$set(this.checkForm, "fId", resp.data.data[0].fId)
        this.$set(this.checkForm, "orderBy", resp.data.data[0].orderBy)
        var map1 = { processNo: '2', orderBy: resp.data.data[0].orderBy }
        selectNextCheckUser(map1).then(resp => {
          this.usernameOptions = resp.data.data

        })
      })

    },

    selectClick(isAgree) {
      this.checkForm.isAgree = isAgree
      if (isAgree == 1) {
        this.checkForm.opinion = '同意'
      } else if (isAgree == 2) {
        this.checkForm.opinion = '不同意'
      }

      if (this.usernameOptions.length > 0 && (this.checkForm.username == undefined || this.checkForm.username == '')) {
        this.$notify.error("请选择审核人")
        return

      }
      submitCheck(this.checkForm).then(data => {
        var msg = data.data.data
        if (msg.msg == null) {
          this.$notify.success("成功")
        } else {
          this.$notify.error("失败")
        }
        // this.$notify.success(msg)
        this.dialogFormVisible = false
        this.dataListLoading = false
        this.$set(this.dataForm, 'value', '')
        this.getDataList()
      })



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
        this.checkForm.bz = data.data.data.bz
        this.checkForm.gbhz = data.data.data.hz
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
    },
    getInoutHeadBody(jqbgdh) {
      var map = { inCustomsDeclaration: jqbgdh, inOut: 0 }
      getInoutHeadBody(map).then(resp => {
        console.log("resp", resp)
        if (resp.data.data.length > 0) {
          var bgzl = 0
          for (var i in resp.data.data) {
            bgzl += parseFloat(resp.data.data[i].roughWet == null ? 0 : resp.data.data[i].roughWet)
          }
          this.$set(this.checkForm, "bghz", bgzl)

        } else {
          this.$alert('未查询到此出库单', '提示', {
            confirmButtonText: '确定',
            callback: action => {
              this.$message({
                type: 'info',
                message: `action: ${action}`
              });
            }
          });
        }
      })
    },
    downloadFile: function (filename, bucketName) {

      handleDown(filename, bucketName)
    },
  }
}
</script>
<style>
.fileDow :hover {
  color: cornflowerblue;
}
</style>