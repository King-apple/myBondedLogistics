<template>
  <div class="mod-config">
    <basic-container>
      <el-form
        :inline="true"
        :model="dataForm"
        @keyup.enter.native="getDataList()"
      >
        <el-form-item label="单据类型" prop="billType">
          <el-select
            clearable
            v-model="dataForm.billType"
            placeholder="请选择单据类型:"
            filterable
          >
            <el-option
              v-for="item in statementList"
              :key="item.id"
              :label="item.remarks"
              :value="item.remarks"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="单据编号" prop="billCod">
          <el-input
            v-model="dataForm.billCod"
            placeholder="请选择单据编号"
          ></el-input>
        </el-form-item>
        <el-form-item label="合同编号" prop="contNo">
          <el-select
            clearable
            v-model="dataForm.contNo"
            placeholder="请选择合同编号:"
            filterable
          >
            <el-option
              v-for="item in settledContractOptions"
              :key="item.fId"
              :label="item.contNo"
              :value="item.contNo"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="客户名称" prop="secondNam">
          <el-input
            v-model="dataForm.secondNam"
            placeholder="客户名称"
          ></el-input>
        </el-form-item>
        <el-form-item label="货物名称" prop="cargoNam">
          <el-input
            v-model="dataForm.cargoNam"
            placeholder="货物名称"
          ></el-input>
        </el-form-item>
        <el-form-item label="日期范围" prop="params">
          <el-date-picker
            v-model="dataForm.params"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            align="right"
            value-format="yyyy-MM-dd hh:mm:ss"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item
          label="状态"
          prop="isCancel"
          id="status"
          style="width: 23%"
        >
          <el-select
            v-model="dataForm.approveStatus"
            placeholder="请选择"
            filterable
            style="width: 100%"
          >
            <el-option
              v-for="(item, index) in approveStatusOption"
              :key="index"
              :label="item.label"
              :value="item.value"
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
          <el-button type="warning" icon="el-icon-refresh" @click="resetForm()"
            >重置</el-button
          >
        </el-form-item>
        <br />
        <!--<el-form-item>
					<el-button icon="el-icon-plus" type="primary" @click="addOrUpdateHandle()">新增</el-button>
				</el-form-item>
				<el-form-item>
					<el-button icon="el-icon-set" type="warning"  @click="openProcessView()">审批流程设定</el-button>
				</el-form-item>
				<el-form-item>
					<el-button icon="el-icon-plus" type="success" @click="getoApprovalPage()">提交审批</el-button>
				</el-form-item>
				<el-form-item>
					<el-button icon="el-icon-search" type="primary" @click="seeApprovalProcess()">查看审批进度</el-button>
				</el-form-item>-->
      </el-form>

      <div class="avue-crud" style="width: 100%">
        <el-table
          :data="dataList"
          border
          v-loading="dataListLoading"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55"> </el-table-column>
          <el-table-column type="index" label="序号"> </el-table-column>
          <el-table-column
            prop="billCod"
            header-align="center"
            align="center"
            label="单据编号"
            width="190"
          >
          </el-table-column>
          <el-table-column
            prop="billType"
            header-align="center"
            align="center"
            label="单据类型"
            width="100"
          >
          </el-table-column>
          <el-table-column
            prop="billDat"
            header-align="center"
            align="center"
            label="日期"
            width="100"
          >
          </el-table-column>
          <el-table-column
            prop="firstNam"
            header-align="center"
            align="center"
            label="承办单位"
            width="140"
          >
          </el-table-column>
          <el-table-column
            prop="dept"
            header-align="center"
            align="center"
            label="部门"
            width="100"
          >
          </el-table-column>
          <el-table-column
            prop="operator"
            header-align="center"
            align="center"
            label="经办人"
            width="80"
          >
          </el-table-column>
          <el-table-column
            prop="secondNam"
            header-align="center"
            align="center"
            label="合同客户名称"
            width="130"
          >
          </el-table-column>
          <el-table-column
            prop="contNo"
            header-align="center"
            align="center"
            label="合同编号"
            width="180"
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
            prop="businessType"
            header-align="center"
            align="center"
            label="业务种类"
          >
          </el-table-column>
          <el-table-column
            prop="cargoNam"
            header-align="center"
            align="center"
            label="货物名称"
          >
          </el-table-column>
          <el-table-column
            prop="accountNam"
            header-align="center"
            align="center"
            label="结算账户名称"
            width="180"
          >
          </el-table-column>
          <el-table-column
            prop="settleWgt"
            header-align="center"
            align="center"
            label="计费数量"
          >
          </el-table-column>
          <el-table-column
            prop="money"
            header-align="center"
            align="center"
            label="金额（元）"
            width="130"
          >
          </el-table-column>
          <el-table-column
            prop="advinceMoney"
            header-align="center"
            align="center"
            label="垫款金额（元）"
            width="130"
          >
          </el-table-column>
          <el-table-column
            prop="markTxt"
            header-align="center"
            align="center"
            label="备注"
            width="130"
          >
          </el-table-column>
          <el-table-column
            prop="approveStatus"
            header-align="center"
            align="center"
            label="状态"
            :formatter="changeManProp"
            width="130"
          >
          </el-table-column>
          <el-table-column
            prop="operNam"
            header-align="center"
            align="center"
            label="添加人"
          />
          <el-table-column
            prop="recordTim"
            header-align="center"
            align="center"
            label="添加时间"
            width="140"
          />
          <el-table-column
            fixed="right"
            header-align="center"
            align="center"
            label="操作"
            width="200px"
          >
            <template slot-scope="scope">
              <el-button
                type="text"
                size="small"
                v-if="scope.row.approveStatus == 1 && scope.row.isAgree == 0"
                icon="el-icon-edit"
                @click="
                  addOrUpdateHandle(
                    scope.row.fId,
                    scope.row.approvalProcessSetFid
                  )
                "
                >审核</el-button
              >
              <el-button
                type="text"
                size="small"
                icon="el-icon-s-unfold"
                v-if="scope.row.approveStatus != 0"
                @click="checkProcessHandle(scope.row.fId, scope.row.times)"
                >查看审批进度</el-button
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
      <!-- 弹窗, 审核进度-->
      <process-form
        v-if="checkProcessVisible"
        ref="checkProcess"
        @refreshDataList="getDataList"
      ></process-form>
    </basic-container>
  </div>
</template>

<script>
import {
  fetchList,
  delObj,
  putObj,
  getAllBills,
  getStatement,
} from "@/api/baoshuisystem/billapprove";
import { fetchList as approvalprocessfetchList, getObj, addObj } from '@/api/baoshuisystem/approvalprocess'
import { fetchList as approvalprocessuserFetchList, addApprovalProcessuser, delApprovalProcessuser, getInfoList } from '@/api/baoshuisystem/approvalprocessuser'
import { saveForOpear, getOpearAudit, getAuditList } from '@/api/baoshuisystem/approvalprocessset'


import TableForm from "./billapprove-formAudit";
import ProcessForm from "./billapproveCheckProcess";
import { mapGetters } from "vuex";
import {
  getSelectContract,
  getEffectiveInfo,
} from "@/api/baoshuisystem/contract";
import { remote } from '@/api/admin/dict'

export default {
  data() {
    return {
      statementList: [],
      settledContractOptions: [],
      dataForm: {
        key: "",
        approveStatus: '1'
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      addOrUpdateVisible: false,
      formLabelWidth: '120px',
      dialogFormVisible: false,
      options: [{
        value: '选项1',
        label: '黄金糕'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }, {
        value: '选项4',
        label: '龙须面'
      }, {
        value: '选项5',
        label: '北京烤鸭'
      }],
      value1: ['选项1', '选项2'],
      single: true,
      approvalprocess: [],
      distProcessNo: [],
      sendData: [],
      billFid: '',
      linkNamS: '',
      roleNamS: '',
      selectTypeValue: {},
      updFlg: "0",
      approveStatusOption: [
        {
          key: 1,
          value: '审批中'
        },
        {
          key: 2,
          value: '审批成功'
        },
        {
          key: 3,
          value: '审批驳回'
        }
      ],
      checkProcessVisible: false,
    };
  },
  components: {
    TableForm,
    ProcessForm,
  },
  created() {
    this.getDataList();
    this.getSelectContractList();
    this.getAllBillsInfo();
    this.getStatementInfo();
    this.checkStatus();
  },
  computed: {
    ...mapGetters(["permissions"]),
  },

  methods: {
    async checkStatus() {
      let checkStatusDic = await remote("billApproval_check_status")
      console.log(checkStatusDic)
      this.approveStatusOption = checkStatusDic.data.data
    },
    getoApprovalPage() {
      this.$router.push({
        path: '/examination',
        // query: {
        //   id: id
        // }
      })
    },
    getStatementInfo() {
      getStatement().then((res) => {
        this.statementList = res.data.data;
      });
    },
    async getAllBillsInfo() {
      await getAllBills().then((res) => {
        console.log("res", res);
      });
    },
    async getSelectContractList() {
      await getEffectiveInfo().then((res) => {
        this.settledContractOptions = res.data.data;
      });
    },
    async getDataListQuery() {
      this.getDataList();
    },
    resetForm() {
      //使用前，先绑定el-form的 ref
      this.dataForm = {};
      this.getDataList();
    },
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;
      //				if(this.dataForm.params != null && this.dataForm.params.length>0){
      //					this.dataForm.params[0] = this.dataForm.params[0] + " 00:00:00"
      //					this.dataForm.params[1] = this.dataForm.params[1] + " 23:59:59"
      //				}

      console.log(this.dataForm)
      getAuditList(
        Object.assign({
          current: this.pageIndex,
          size: this.pageSize
        },
          this.dataForm
        )
      ).then((response) => {
        console.log(response)
        if (response.data.data) {
          this.dataList = response.data.data.records;
          this.totalPage = response.data.data.total;
        }

      });
      this.dataListLoading = false;
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
    addOrUpdateHandle(id, approvalProcessSetFid) {
      this.addOrUpdateVisible = true;
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id, approvalProcessSetFid, true);
      });
    },
    // 删除
    deleteHandle(id) {
      this.$confirm("是否确认删除ID为" + id, "提示", {
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
    goToApprovalHandle(data) {
      //提示信息
      this.$confirm('进入审批流程将无法修改和删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //给个状态让修改和删除失效
        data.approveStatus = 1
        putObj(data).then(res => {
          this.$message({
            type: 'success',
            message: '已进入流程!'
          });
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        });
      });

    },
    handleSelectionChange(selection) {
      console.log(selection)
      this.approvalprocess.processFid = selection.map(item => item.fId);
      this.billFid = selection.map(item => item.fId);
      //		      this.linkNamS = selection.map(item => item.linkNam);
      //		      this.roleNamS = selection.map(item => item.roleNam);
      //		      console.log(selection)
      this.single = selection.length != 1;
      //				this.multiple = !selection.length;
    },
    dataFormSubmit() {
      console.log(this.sendData)
      if (this.updFlg == "1") {
        for (var l = 0; l < this.gridData.length; l++) {
          let item = this.gridData[l]
          item.billFid = this.billFid[0]
          item.linkNamS = this.linkNamS[0]
          item.roleNamS = this.roleNamS[0]
          this.selectTypeValue = { ...this.selectTypeValue }
          let key = item.fId
          let keyValue = {};
          keyValue[key] = item;
          this.sendData = Object.assign(this.sendData, keyValue)
        }

        console.log(this.sendData)

        saveForOpear(this.sendData).then((res) => {
          console.log(res);
          this.$message({
            type: 'success',
            message: '更改成功！'
          });
        })
      } else {
        saveForOpear(this.sendData).then((res) => {
          console.log(res);
          this.$message({
            type: 'success',
            message: '设置成功！'
          });
        })
      }

    },
    changeValue(value, item, index) {
      console.log(value, item, index)
      item.userList = value
      item.billFid = this.billFid[0]
      item.linkNamS = this.linkNamS[0]
      item.roleNamS = this.roleNamS[0]
      //		      	this.$set(item, 'userName', value)
      //				this.gridData[0].userName=value
      this.selectTypeValue = { ...this.selectTypeValue }

      //		      	this.$forceUpdate()
      let key = item.fId
      let keyValue = {};
      keyValue[key] = item;
      this.sendData = Object.assign(this.sendData, keyValue)
      console.log(this.sendData)
    },
    processNoFormatter(item) {
      //		   	console.log(item.processNo)
      //		   	console.log(this.distProcessNo.data.data[item.processNo])
      return this.distProcessNo.data.data[item.processNo].label
    },
    changeManProp(row, column) {
      //状态（0：未审批，1：审批中，2：审批完成，3审批失败）
      if ("0" == row.approveStatus) {
        return "未审批"
      } else if ("1" == row.approveStatus) {
        return "审批中"
      } else if ("2" == row.approveStatus) {
        return "审批完成"
      } else {
        return "审批失败"
      }
    },
    seeApprovalProcess() {

    },
    //审批进度
    checkProcessHandle(id, times) {
      this.checkProcessVisible = true;
      this.$nextTick(() => {
        this.$refs.checkProcess.init(id, times);
      });
    }
  },
};
</script>