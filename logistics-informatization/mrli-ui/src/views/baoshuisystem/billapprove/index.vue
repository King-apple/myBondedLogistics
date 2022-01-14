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
            style="width: 100px"
          ></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select
            v-model="dataForm.approveStatus"
            placeholder="请选择状态"
            style="width: 100px"
          >
            <el-option
              v-for="(item, index) in contractCheckProcessOptions"
              :key="index"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否作废" prop="isCancel" id="isCancel">
          <el-select
            v-model="dataForm.isCancel"
            placeholder="请选择"
            filterable
            clearable
            style="width: 100px"
          >
            <el-option
              v-for="(item, index) in isCancelOptions"
              :key="index"
              :label="item.key"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="日期范围" prop="params">
          <el-date-picker
            v-model="dataForm.params"
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
          <el-button type="warning" icon="el-icon-refresh" @click="resetForm()"
            >重置</el-button
          >
        </el-form-item>
        <br />
        <el-form-item>
          <el-button
            icon="el-icon-s-operation"
            style="background: skyblue; color: white"
            @click="waittingBussinessCheckBills()"
            >查询待业务审核单据</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button
            icon="el-icon-plus"
            type="primary"
            @click="addOrUpdateHandle()"
            >新增</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button
            icon="el-icon-set"
            type="warning"
            :disabled="single"
            @click="openProcessView()"
            >审批流程设定</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button
            icon="el-icon-search"
            type="primary"
            @click="exportB(selectioned)"
            >导出</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button
            icon="el-icon-upload2"
            type="danger"
            @click="submitCheck(selectioned)"
            :disabled="submitCheckSingle"
            v-if="permissions.billApprove_submit_check"
            >提交业务审核</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button
            icon="el-icon-s-check"
            type="success"
            @click="businessCheckDialogVisible(selectioned)"
            :disabled="businessCheckDialogVisibleSingle"
            v-if="permissions.billApprove_check"
            >业务审核</el-button
          >
        </el-form-item>
      </el-form>

      <div class="avue-crud" style="width: 100%">
        <el-table
          :data="dataList"
          border
          v-loading="dataListLoading"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55"> </el-table-column>
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
            prop="billCod"
            header-align="center"
            align="center"
            label="单据编号"
            sortable
            width="185px"
          >
          </el-table-column>
          <el-table-column
            prop="billType"
            header-align="center"
            align="center"
            label="单据类型"
            sortable
            width="100px"
          >
          </el-table-column>
          <el-table-column
            header-align="center"
            align="center"
            label="业务审核状态"
            width="100px"
            prop="checkStatus"
            :formatter="checkStatusFormatter"
          >
          </el-table-column>
          <el-table-column
            header-align="center"
            align="center"
            sortable
            label="状态"
          >
            <template slot-scope="scope">
              {{ getStatus(scope.row.approveStatus) }}
            </template>
          </el-table-column>
          <el-table-column
            prop="billDat"
            header-align="center"
            align="center"
            label="日期"
            sortable
            width="100px"
          >
          </el-table-column>
          <el-table-column
            prop="firstNam"
            header-align="center"
            align="center"
            label="承办单位"
            sortable
            width="220px"
          >
          </el-table-column>
          <el-table-column
            prop="dept"
            header-align="center"
            align="center"
            label="部门"
            sortable
            width="100px"
          >
          </el-table-column>
          <el-table-column
            prop="operator"
            header-align="center"
            align="center"
            label="经办人"
            sortable
            width="100px"
          >
          </el-table-column>
          <el-table-column
            prop="secondNam"
            header-align="center"
            align="center"
            label="合同客户名称"
            sortable
            width="130px"
          >
          </el-table-column>
          <el-table-column
            prop="contNo"
            header-align="center"
            align="center"
            label="合同编号"
            sortable
            width="150px"
          >
          </el-table-column>
          <el-table-column
            prop="inShipName"
            sortable
            header-align="center"
            align="center"
            width="100px"
            label="入境船名"
          >
          </el-table-column>
          <el-table-column
            prop="outShipName"
            sortable
            header-align="center"
            align="center"
            width="100px"
            label="出境船名"
          >
          </el-table-column>
          <el-table-column
            prop="businessType"
            header-align="center"
            align="center"
            label="业务种类"
            sortable
            width="110px"
          >
          </el-table-column>
          <el-table-column
            prop="cargoNam"
            header-align="center"
            align="center"
            label="货物名称"
            sortable
            width="110px"
          >
          </el-table-column>
          <el-table-column
            prop="accountNam"
            header-align="center"
            align="center"
            label="结算账户名称"
            sortable
            width="220px"
          >
          </el-table-column>
          <el-table-column
            prop="settleWgt"
            header-align="center"
            align="center"
            label="计费数量"
            sortable
            width="110px"
          >
          </el-table-column>
          <el-table-column
            prop="money"
            header-align="center"
            align="center"
            label="金额（元）"
            sortable
            width="110px"
          >
          </el-table-column>
          <el-table-column
            prop="advinceMoney"
            header-align="center"
            align="center"
            label="垫款金额（元）"
            sortable
            width="130px"
          >
          </el-table-column>
          <el-table-column
            prop="markTxt"
            header-align="center"
            align="center"
            label="备注"
            sortable
          >
          </el-table-column>

          <el-table-column
            prop="operNam"
            header-align="center"
            align="center"
            label="添加人"
            sortable
            width="100px"
          />
          <el-table-column
            prop="recordTim"
            header-align="center"
            align="center"
            label="添加时间"
            width="200px"
            sortable
          />
          <el-table-column
            fixed="right"
            header-align="center"
            align="center"
            label="操作"
            width="300px"
          >
            <template slot-scope="scope">
              <el-button
                type="text"
                size="small"
                v-if="
                  (scope.row.approveStatus == 0 ||
                    scope.row.approveStatus == 3) &&
                  scope.row.isCancel != 1
                "
                icon="el-icon-edit"
                @click="addOrUpdateHandle(scope.row.fId)"
                >修改</el-button
              >
              <el-button
                type="text"
                size="small"
                v-if="
                  (scope.row.approveStatus == 0 ||
                    scope.row.approveStatus == 3 ||
                    scope.row.approveStatus == 2) &&
                  scope.row.isCancel != 1
                "
                icon="el-icon-delete"
                @click="deleteHandle(scope.row.fId)"
                >作废</el-button
              >
              <el-button
                type="text"
                size="small"
                icon="el-icon-s-unfold"
                @click="checkProcessHandle(scope.row.fId, scope.row.times)"
                >查看审批进度</el-button
              >
              <el-button
                type="text"
                size="small"
                v-if="
                  scope.row.approveStatus == 0 &&
                  scope.row.isCancel != 1 &&
                  scope.row.checkStatus == 2
                "
                @click="goToApprovalHandle(scope.row)"
                >点击提交审批</el-button
              >
              <el-button
                type="text"
                size="small"
                v-if="scope.row.approveStatus == 2 && scope.row.isCancel != 1"
                >审批完成</el-button
              >
              <el-button
                type="text"
                size="small"
                v-if="scope.row.approveStatus == 3 && scope.row.isCancel != 1"
                @click="goToApprovalHandle(scope.row)"
                >审批失败</el-button
              >
              <el-button
                type="text"
                size="small"
                icon="el-icon-edit"
                @click="
                  auditHandle(scope.row.fId, scope.row.approvalProcessSetFid)
                "
                >查看详情</el-button
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

      <audit-form
        v-if="auditVisible"
        ref="auditRef"
        @refreshDataList="getDataList"
      ></audit-form>

      <!-- 弹窗, 审核进度-->
      <process-form
        v-if="checkProcessVisible"
        ref="checkProcess"
        @refreshDataList="getDataList"
      ></process-form>
    </basic-container>
    <el-dialog title="审批流程 " :visible.sync="dialogTableVisible" width="80%">
      <span style="color: red; font: 12px"
        >跨部门审批时，不参与审批的部门则不选择"审核人员"！</span
      >
      <el-table :data="gridData">
        <!--<el-table-column property="processNo" label="审批流程" width="150" :formatter="processNoFormatter"></el-table-column>-->
        <el-table-column
          property="orderBy"
          label="审批序号"
          width="200"
        ></el-table-column>
        <el-table-column property="linkNam" label="部门名称"></el-table-column>
        <el-table-column property="roleNam" label="角色"></el-table-column>
        <!--<el-table-column property="markTxt" label="备注"></el-table-column>-->

        <el-table-column label="审核人员" property="userName">
          <template slot-scope="scope" label-width="350px">
            <el-select
              v-model="selectTypeValue[scope.row.fId]"
              @change="
                (value) => {
                  changeValue(value, scope.row, scope.$index);
                }
              "
              multiple
              placeholder="请选择"
              style="width: 100%"
            >
              <el-option
                v-for="item in scope.row.userNameList"
                :key="item.userId"
                :label="item.manName"
                :value="item.userId"
              >
              </el-option>
            </el-select>
          </template>
        </el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogTableVisible = false">取消</el-button>
        <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
      </span>
    </el-dialog>

    <el-dialog
      title="业务审核"
      :visible.sync="checkDialogVisible"
      width="30%"
      v-loading="checkDialogLoading"
    >
      <span slot="footer" class="dialog-footer">
        <el-button @click="businessCheck(3)">审核驳回</el-button>
        <el-button type="primary" @click="businessCheck(2)">审核通过</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  fetchList,
  delObj,
  putObj,
  getAllBills,
  getStatement,
  exportBill,
  putApproval,
  submitCheck
} from "@/api/baoshuisystem/billapprove";
import {
  fetchList as approvalprocessfetchList,
  getObj,
  addObj,
} from "@/api/baoshuisystem/approvalprocess";
import {
  fetchList as approvalprocessuserFetchList,
  addApprovalProcessuser,
  delApprovalProcessuser,
  getInfoList,
} from "@/api/baoshuisystem/approvalprocessuser";
import {
  saveForOpear,
  getOpearAudit,
  getProcessSets
} from "@/api/baoshuisystem/approvalprocessset";

import TableForm from "./billapprove-form";
import ProcessForm from "./billapproveCheckProcess";
import { mapGetters } from "vuex";
import {
  getSelectContract,
  getEffectiveInfo,
  getContractCheckProcess,
} from "@/api/baoshuisystem/contract";
import { remote } from "@/api/admin/dict";
import AuditForm from "./billapprove-formAudit";
export default {
  data() {
    return {
      isCancelOptions: [
        {
          key: "否",
          value: 0,
        },
        {
          key: "是",
          value: "1",
        },
      ],
      times: 0,
      contractCheckProcessOptions: [],
      selectioned: "",
      statementList: [],
      settledContractOptions: [],
      dataForm: {
        key: "",
        isCancel: 0,
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      auditVisible: false,
      dataListLoading: false,
      addOrUpdateVisible: false,
      gridData: [],
      dialogTableVisible: false,
      formLabelWidth: "120px",
      dialogFormVisible: false,
      value1: ["选项1", "选项2"],
      single: true,
      approvalprocess: [],
      distProcessNo: [],
      sendData: [],
      billFid: "",
      linkNamS: "",
      roleNamS: "",
      selectTypeValue: {},
      updFlg: "0",
      approveStatusArr: [],
      checkProcessVisible: false,
      checkDialogVisible: false,
      submitCheckSingle: true,
      businessCheckDialogVisibleSingle: true,
      checkDialogLoading: false
    };
  },
  components: {
    TableForm,
    ProcessForm,
    AuditForm,
  },
  created() {
    this.getDataList();
    this.getSelectContractList();
    this.getAllBillsInfo();
    this.getStatementInfo();
  },
  computed: {
    ...mapGetters(["permissions"]),
    ...mapGetters(["userInfo"]),
  },

  methods: {
    getoApprovalPage() {
      this.$router.push({
        path: "/examination",
        // query: {
        //   id: id
        // }
      });
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
      getContractCheckProcess().then((data) => {
        this.contractCheckProcessOptions = data.data.data;
      });
      fetchList(
        Object.assign(
          {
            current: this.pageIndex,
            size: this.pageSize,
          },
          this.dataForm
        )
      ).then((response) => {
        console.log(response);
        this.dataList = response.data.data.records;
        this.totalPage = response.data.data.total;
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
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true;
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id);
      });
    },
    // 删除
    deleteHandle(id) {
      this.$confirm("是否将本条数据作废" + id, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delObj(id);
        })
        .then((data) => {
          this.$message.success("操作成功");
          this.getDataList();
        });
    },
    //进入审批状态的时候 审批次数就加一
    goToApprovalHandle(data) {
      //提示信息
      this.$confirm("进入审批流程将无法修改和删除, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {

          var map = { processNo: 1, billFid: data.fId }
          getProcessSets(map).then(res => {
            console.log(res.data.data)
            if (res.data.data != undefined && res.data.data.length > 0) {
              //给个状态让修改和删除失效
              data.approveStatus = 1;
              data.times += 1;
              putObj(data).then((res) => {
                putApproval(data).then((res) => {
                  this.$message({
                    type: "success",
                    message: "已进入流程!",
                  });
                });
              });
            } else {
              this.$message({
                type: "error",
                message: "请先设定审批流程！",
              });
            }
          })

        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消",
          });
        });
    },
    handleSelectionChange(selection) {
      this.selectioned = selection;
      this.approvalprocess.processFid = selection.map((item) => item.fId);
      this.billFid = selection.map((item) => item.fId);
      //		      this.linkNamS = selection.map(item => item.linkNam);
      //		      this.roleNamS = selection.map(item => item.roleNam);
      this.approveStatusArr = selection.map((item) => item.approveStatus);
      this.single = selection.length != 1;
      //如果已经审核完成，按钮禁止点击
      if (this.approveStatusArr[0] === 2) {
        this.single = true;
      }
      if (this.selectioned.length == 1 && (this.selectioned[0].checkStatus == '' || this.selectioned[0].checkStatus == undefined || this.selectioned[0].checkStatus == 0 || this.selectioned[0].checkStatus == 3)) {
        this.submitCheckSingle = false
      } else {
        this.submitCheckSingle = true
      }

      if (this.selectioned.length == 1 && (this.selectioned[0].checkStatus == 1) && this.selectioned[0].checkUser == this.userInfo.username) {
        this.businessCheckDialogVisibleSingle = false
      } else {
        this.businessCheckDialogVisibleSingle = true
      }
      //				this.multiple = !selection.length;
    },
    async openProcessView() {
      if (this.approveStatusArr[0] === 2 || this.approveStatusArr[0] === 1) {
        this.$message({
          type: "success",
          message: "当前单据正在审核或者已审核通过！",
        });
        return;
      }

      this.sendData = [];
      this.gridData = [];
      this.selectTypeValue = [];
      let processNo = await remote("process_no");
      console.log(processNo);
      this.distProcessNo = processNo;
      console.log(processNo);
      this.approvalprocess.processNo = "1";
      let res = await approvalprocessfetchList(this.approvalprocess);
      console.log(res);
      for (var i = 0; i < res.data.data.records.length; i++) {
        let parmas = {
          processFid: res.data.data.records[i].fId,
        };
        console.log(res.data.data.records[i].fId);
        let response = await approvalprocessuserFetchList(parmas);
        res.data.data.records[i].userNameList = response.data.data.records;
      }

      console.log(res);
      this.gridData = res.data.data.records;

      let detail = await getOpearAudit(this.billFid[0]);
      let daer = detail.data.data;
      console.log(daer);
      this.updFlg = "0";
      if (detail.data.data) {
        this.updFlg = "1";
      }
      for (var i = 0; i < daer.length; i++) {
        let keyValue = {};
        var a = daer[i].NAME.split(",").map(Number);
        for (var j = 0; j < this.gridData.length; j++) {
          if (this.gridData[j].fId == daer[i].PROCESS_FID) {
            console.log(this.gridData[j].fId);
            this.gridData[j].userList = a;
          }
        }
        console.log(this.gridData);
        keyValue[daer[i].PROCESS_FID] = a;
        this.selectTypeValue = Object.assign(this.selectTypeValue, keyValue);
      }
      console.log("selectTypeValue", this.selectTypeValue);

      //		    	console.log(this.gridData)
      //		    	approvalprocessfetchList(this.approvalprocess).then((res) => {
      //		    		console.log(res.data.data.records.length);
      //					let ad = []
      //		    		for(var i=0;i<res.data.data.records.length-1;i++){
      //		    			let parmas = {
      //		    				processFid: res.data.data.records[i].fid
      //		    			}
      //		    			console.log(res.data.data.records[i].fid)
      //		    			approvalprocessuserFetchList(parmas).then((response) => {
      //
      //		    			console.log(res.data.data.records[i])
      //		    			let userNameList = {
      //		    				userNameList : response.data.data.records
      //		    			}
      //
      //		    			ad.push(Object.assign(res.data.data.records[i],userNameList))
      //		    			console.log(ad)
      //		    			console.log(this.gridData)
      //		    		})
      //
      //		    		}
      //
      //		    	})
      this.dialogTableVisible = true;
    },
    dataFormSubmit() {
      console.log(this.sendData);
      if (this.updFlg == "1") {
        for (var l = 0; l < this.gridData.length; l++) {
          let item = this.gridData[l];
          item.billFid = this.billFid[0];
          item.linkNamS = this.linkNamS[0];
          item.roleNamS = this.roleNamS[0];
          this.selectTypeValue = { ...this.selectTypeValue };
          let key = item.fId;
          let keyValue = {};
          keyValue[key] = item;
          this.sendData = Object.assign(this.sendData, keyValue);
        }

        console.log(this.sendData);

        saveForOpear(this.sendData).then((res) => {
          console.log(res);
          this.$message({
            type: "success",
            message: "更改成功！",
          });
          this.dialogTableVisible = false;
        });
      } else {
        saveForOpear(this.sendData).then((res) => {
          console.log(res);
          this.$message({
            type: "success",
            message: "设置成功！",
          });
        });
      }
    },
    //审批进度
    checkProcessHandle(id, times) {
      this.checkProcessVisible = true;
      this.$nextTick(() => {
        this.$refs.checkProcess.init(id, times);
      });
    },
    changeValue(value, item, index) {
      console.log(value, item, index);
      item.userList = value;
      item.billFid = this.billFid[0];
      item.linkNamS = this.linkNamS[0];
      item.roleNamS = this.roleNamS[0];
      //		      	this.$set(item, 'userName', value)
      //				this.gridData[0].userName=value
      this.selectTypeValue = { ...this.selectTypeValue };

      //		      	this.$forceUpdate()
      let key = item.fId;
      let keyValue = {};
      keyValue[key] = item;
      this.sendData = Object.assign(this.sendData, keyValue);
      console.log(this.sendData);
    },
    processNoFormatter(item) {
      //		   	console.log(item.processNo)
      //		   	console.log(this.distProcessNo.data.data[item.processNo])
      //return this.distProcessNo.data.data[item.processNo].label
      return "业务单据审批";
    },

    exportB(data) {
      if (data.length != 1) {
        this.$message.info("请选择一条数据");
      } else {
        //调接口
        console.log(data[0]);
        exportBill("审批单.xlsx", data[0]);
      }
    },
    getStatus(data) {
      let status = "";
      this.contractCheckProcessOptions.map((res) => {
        if (data == res.value) {
          status = res.label;
        }
      });
      return status;
    },
    submitCheck(selectioned) {
      var map = { fId: selectioned[0].fId, checkStatus: '1' }
      this.dataListLoading = true
      submitCheck(map).then(data => {
        this.$notify.success('提交成功')
        this.getDataList();
        this.dataListLoading = false
      }).catch(() => {
        this.getDataList();
        this.dataListLoading = false
      });

    },
    businessCheckDialogVisible(selectioned) {
      this.checkDialogVisible = true
    },
    businessCheck(checkStatus) {
      var map = { fId: this.selectioned[0].fId, checkStatus: checkStatus }
      this.checkDialogLoading = true
      submitCheck(map).then(data => {
        this.$notify.success('审核完成')
        this.getDataList();
        this.checkDialogLoading = false
        this.checkDialogVisible = false
      }).catch(() => {
        this.getDataList();
        this.checkDialogLoading = false
        this.checkDialogVisible = false
      });

    },
    checkStatusFormatter(row, column) {
      if (row.checkStatus == undefined || row.checkStatus == 0 || row.checkStatus == '') {
        return '未审核'
      } else if (row.checkStatus == 1) {
        return '待审核'
      } else if (row.checkStatus == 2) {
        return row.checkManname + '：审核通过'
      } else if (row.checkStatus == 3) {
        return row.checkManname + '：审核未通过'
      }
    },
    // 新增 / 修改
    auditHandle(id, approvalProcessSetFid) {
      this.auditVisible = true;
      this.$nextTick(() => {
        this.$refs.auditRef.init(id, approvalProcessSetFid, false);
      });
    },
    waittingBussinessCheckBills() {
      this.dataForm.checkUser = this.userInfo.username
      this.dataForm.checkStatus = '1'
      this.getDataList()
      this.dataForm.checkUser = ''
      this.dataForm.checkStatus = ''
    }
  },
};
</script>
