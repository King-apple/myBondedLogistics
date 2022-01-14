<template>
  <div class="mod-config">
    <basic-container>
      <el-form
        :inline="true"
        :model="dataForm"
        ref="dataForm"
        autocomplete="off"
        @keyup.enter.native="getDataList()"
        label-position="left"
        class="formTab"
      >
        <el-form-item label="合同编号" prop="contNo">
          <el-input v-model="dataForm.contNo" placeholder="合同编号"></el-input>
        </el-form-item>
        <el-form-item label="部门" prop="dept" id="dept" style="width: 15%">
          <el-input v-model="dataForm.dept" placeholder="部门"></el-input>
        </el-form-item>
        <el-form-item
          label="船名"
          prop="shipNam"
          style="width: 10%"
          id="shipNam"
        >
          <el-input
            v-model="dataForm.shipNam"
            placeholder="船名"
            style="width: 100%"
          ></el-input>
        </el-form-item>
        <el-form-item label="合同类型" prop="contractType">
          <el-select
            v-model="dataForm.contractType"
            placeholder="请选择"
            filterable
            clearable
          >
            <el-option
              v-for="(
                contractTypeOptionsItem, contractTypeOptionsIndex
              ) in contractTypeOptions"
              :key="contractTypeOptionsIndex"
              :label="contractTypeOptionsItem.description"
              :value="contractTypeOptionsItem.description"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          label="是否作废"
          prop="isCancel"
          id="isCancel"
          style="width: 12%"
        >
          <el-select
            v-model="dataForm.isCancel"
            placeholder="请选择"
            filterable
            clearable
            style="width: 100%"
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
        <el-form-item
          label="合同标的"
          prop="cargoNam"
          id="cargoNam"
          style="width: 15%"
        >
          <el-select
            v-model="dataForm.cargoNam"
            placeholder="请选择"
            filterable
            clearables
            @change="$forceUpdate()"
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

        <el-form-item
          label="开始日期"
          prop="params"
          id="startDate"
          style="width: 32%"
        >
          <el-date-picker
            v-model="dataForm.params"
            type="daterange"
            :picker-options="pickerOptions"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            align="right"
            value-format="yyyy-MM-dd"
            style="width: 100%"
          >
          </el-date-picker>
        </el-form-item>

        <el-form-item label="对方签约单位" prop="secondNam" style="width: 25%">
          <el-select
            v-model="dataForm.secondNam"
            placeholder="请选择"
            filterable
            clearable
            style="width: 100%"
          >
            <el-option
              v-for="(item, index) in secondNamOptions"
              :key="index"
              :label="item.company"
              :value="item.company"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          label="是否归档"
          prop="isFile"
          id="isFile"
          style="width: 12%"
        >
          <el-select
            v-model="dataForm.conFile"
            placeholder="请选择"
            filterable
            clearable
            style="width: 100%"
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

        <el-form-item
          label="状态"
          prop="contractStatus"
          id="status"
          style="width: 12%"
        >
          <el-select
            v-model="dataForm.contractStatus"
            placeholder="请选择"
            filterable
            clearable
            style="width: 100%"
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
        <el-form-item
          label="货类"
          prop="cargoKindNam"
          id="cargoKindNam"
          style="width: 10%"
        >
          <el-select
            v-model="dataForm.cargoKindNam"
            placeholder="请选择"
            filterable
            clearable
            style="width: 100%"
          >
            <el-option
              v-for="(item, index) in cargoKindOptions"
              :key="index"
              :label="item.cargoKindNam"
              :value="item.cargoKindNam"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item style="margin-top: -2.5%"> </el-form-item>

        <el-row style="margin-top: -2.5%">
          <el-form-item>
            <el-button type="primary" @click="getDataList">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="warning" @click="resetForm('dataForm')"
              >重置</el-button
            >
          </el-form-item>
          <el-form-item>
            <el-button
              icon="el-icon-plus"
              type="primary"
              @click="addOrUpdateHandle()"
              v-if="permissions.contract_add"
              >新增</el-button
            >
          </el-form-item>
          <el-form-item>
            <el-button
              icon="el-icon-check"
              type="warning"
              @click="goToCheck(0)"
              :disabled="single"
              >提交审核</el-button
            >
          </el-form-item>

          <el-form-item>
            <el-button icon="el-icon-upload2" type="success" @click="expoprt()"
              >打印</el-button
            >
          </el-form-item>
        </el-row>
      </el-form>
      <div class="avue-crud" style="width: 100%">
        <el-table
          highlight-current-row
          :data="dataList"
          border
          autocomplete="off"
          v-loading="dataListLoading"
          ref="singleTable"
          @current-change="handleCurrentChange"
          @expand-change="getProList"
          :row-class-name="tableRowClassName"
        >
          <!-- <el-table-column
            prop="fId"
            header-align="center"
            align="center"
            label="主键"
            hidden="hidden"
          >
          </el-table-column> -->
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
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form
                label-position="left"
                inline
                class="demo-table-expand infor"
              >
                <el-row>
                  <el-col :span="8"
                    ><el-form-item label="合同编号">
                      <el-input
                        v-model="props.row.contNo"
                        placeholder="合同编号"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="8"
                    ><el-form-item label="承办单位">
                      <el-input
                        v-model="props.row.firstNam"
                        placeholder="承办单位"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="8">
                    <el-form-item label="部门">
                      <el-input
                        v-model="props.row.dept"
                        placeholder="部门"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                </el-row>

                <el-row>
                  <el-col :span="8"
                    ><el-form-item label="经办人">
                      <el-input
                        v-model="props.row.operator"
                        placeholder="经办人"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="8"
                    ><el-form-item label="对方签约单位">
                      <el-input
                        v-model="props.row.secondNam"
                        placeholder="对方签约单位"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="8">
                    <el-form-item label="合同类型">
                      <el-input
                        v-model="props.row.contractType"
                        placeholder="合同类型"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                </el-row>

                <el-row>
                  <el-col :span="8"
                    ><el-form-item label="合同标的">
                      <el-input
                        v-model="props.row.cargoNam"
                        placeholder="货名"
                        readonly
                        style="width: 49%"
                      ></el-input>
                      <el-input
                        v-model="props.row.cargoKindNam"
                        placeholder="货类"
                        readonly
                        style="width: 49%"
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="8"
                    ><el-form-item label="合同标的数量">
                      <el-input
                        placeholder="合同标的数量"
                        readonly
                        :value="props.row.cargoNum + props.row.cargoNumUnit"
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="8">
                    <el-form-item label="标的额">
                      <el-input
                        :value="props.row.cargoFee + props.row.cargoFeeUnit"
                        placeholder="标的额"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                </el-row>

                <el-row>
                  <el-col :span="8"
                    ><el-form-item label="合同开始日期">
                      <el-input
                        v-model="props.row.startDte"
                        placeholder="合同开始日期"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="8"
                    ><el-form-item label="合同结束日期">
                      <el-input
                        v-model="props.row.endDte"
                        placeholder="合同结束日期"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="8">
                    <el-form-item label="合同签订日期">
                      <el-input
                        v-model="props.row.conDte"
                        placeholder="合同签订日期"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                </el-row>

                <el-row>
                  <el-col :span="8"
                    ><el-form-item label="结算依据">
                      <el-input
                        v-model="props.row.settlementBasis"
                        placeholder="结算依据"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="8"
                    ><el-form-item label="结算方式">
                      <el-input
                        v-model="props.row.clearingForm"
                        placeholder="结算方式"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="8">
                    <el-form-item label="客户联系人">
                      <el-input
                        v-model="props.row.contact"
                        placeholder="客户联系人"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                </el-row>

                <el-row>
                  <el-col :span="8"
                    ><el-form-item label="联系电话">
                      <el-input
                        v-model="props.row.phone"
                        placeholder="联系电话"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="8"
                    ><el-form-item label="规格">
                      <el-input
                        v-model="props.row.format"
                        placeholder="规格"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="8">
                    <el-form-item label="船名">
                      <el-input
                        v-model="props.row.shipNam"
                        placeholder="船名"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                </el-row>

                <el-row>
                  <el-col :span="6"
                    ><el-form-item label="包装">
                      <el-input
                        v-model="props.row.pkgKindNam"
                        placeholder="包装"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="6"
                    ><el-form-item label="存放地点">
                      <el-input
                        v-model="props.row.locate"
                        placeholder="存放地点"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="6">
                    <el-form-item label="内外贸">
                      <el-input
                        v-model="props.row.tradeId"
                        placeholder="内外贸"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                  <el-col :span="6">
                    <el-form-item label="进出口">
                      <el-input
                        v-model="props.row.inoutId"
                        placeholder="进出口"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                </el-row>
                <el-form-item id="feeI">
                  <div
                    v-for="(item, index) in props.row.contractRateList"
                    :key="index"
                    id="fee"
                  >
                    费目{{ index + 1 }}
                    <el-row id="feeRow" style="margin-top: -1%; width: 120%">
                      <el-col :span="4"
                        ><el-form-item label="费目名称：">
                          <el-input
                            v-model="item.feeNam"
                            placeholder="费目名称："
                            readonly
                          ></el-input> </el-form-item
                      ></el-col>
                      <el-col :span="4"
                        ><el-form-item label="费率值(元)：">
                          <el-input
                            v-model="item.feeRat"
                            placeholder="费率值(元)："
                            readonly
                          ></el-input> </el-form-item
                      ></el-col>
                      <el-col :span="4"
                        ><el-form-item label="费率单位">
                          <el-input
                            v-model="item.feeUnit"
                            placeholder="费率单位"
                            readonly
                          ></el-input> </el-form-item
                      ></el-col>
                      <el-col :span="4"
                        ><el-form-item label="存放地点">
                          <el-input
                            v-model="item.locate"
                            placeholder="存放地点"
                            readonly
                          ></el-input> </el-form-item
                      ></el-col>
                      <el-col :span="4"
                        ><el-form-item label="备注：">
                          <el-input
                            v-model="item.markTxt"
                            placeholder="备注："
                            readonly
                          ></el-input> </el-form-item
                      ></el-col>
                    </el-row>
                  </div>
                </el-form-item>
                <el-row id="rem">
                  <el-col :span="24"
                    ><el-form-item label="备注">
                      <el-input
                        v-model="props.row.markTxt"
                        placeholder="备注"
                        readonly
                      ></el-input> </el-form-item
                  ></el-col>
                </el-row>
                <el-form-item label="附件" id="fileFor">
                  <el-row
                    v-for="(item, index) in props.row.sysFileList"
                    :key="index"
                  >
                    <el-col :span="24">
                      <span
                        class="onespan"
                        @click="downloadFile(item.fileName, item.bucketName)"
                        style="cursor: pointer"
                      >
                        附件{{ index + 1 }} ：{{ item.original }}</span
                      >
                    </el-col>
                    附件{{ index + 1 }}说明：
                    <el-input
                      v-model="item.remark"
                      placeholder="附件说明"
                      readonly
                      style="width: 50%"
                    ></el-input>
                  </el-row>
                </el-form-item>
                <el-row>
                  <el-col :span="24">
                    <span>审批进度</span>
                    <div style="margin-left: 10%">
                      <div
                        v-for="item in valList"
                        :key="item.linkNam"
                        style="float: left; width: 15%"
                      >
                        <div>
                          <div style="border: 1px solid black">
                            {{ item.linkNam }}
                          </div>
                          <div
                            v-html="item.opinion"
                            style="
                              border: 1px solid black;
                              height: 100px;
                              overflow-y: auto;
                            "
                          ></div>
                        </div>
                      </div>
                    </div>
                  </el-col>
                </el-row>
              </el-form>
            </template>
          </el-table-column>

          <el-table-column
            prop="contNo"
            header-align="center"
            align="center"
            label="合同编号"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="firstNam"
            header-align="center"
            align="center"
            label="承办单位"
            sortable
          >
          </el-table-column>
     <el-table-column
            prop="secondNam"
            header-align="center"
            align="center"
            label="对方签约单位"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="cargoNam"
            header-align="center"
            align="center"
            label="合同标的(货名)"
            sortable
          >
          </el-table-column>

          <el-table-column
            prop="cargoKindNam"
            header-align="center"
            align="center"
            label="合同标的(货类)"
            sortable
          >
          </el-table-column>

          <el-table-column
            header-align="center"
            align="center"
            label="合同期限"
            value-format="yyyy-MM-dd"
            sortable
          >
            <template slot-scope="props">
              <span>{{ props.row.startDte }}至{{ props.row.endDte }}</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="conDte"
            header-align="center"
            align="center"
            label="合同签订日期"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="contractStatus"
            header-align="center"
            align="center"
            :formatter="contractStatusFormat"
            label="审批状态"
            sortable
          >
          </el-table-column>
          <!-- <el-table-column
            prop="conFile"
            header-align="center"
            align="center"
            :formatter="contractFileFormat"
            label="归档状态"
            sortable
            hidden
          >
          </el-table-column> -->
          <el-table-column
            prop="cancelStatus"
            header-align="center"
            align="center"
            :formatter="contractStatusFormat"
            label="作废状态"
            sortable
          >
          </el-table-column>

          <el-table-column header-align="center" align="center" label="操作">
            <template slot-scope="scope">
              <el-button
                type="text"
                size="small"
                icon="el-icon-edit"
                @click="addOrUpdateHandle(scope.row.fId)"
                v-if="
                  (scope.row.contractStatus == 0 ||
                    scope.row.contractStatus == 3) &&
                  scope.row.isCancel != 1 &&
                  permissions.contract_edit &&
                  (scope.row.cancelStatus == 0 ||
                    scope.row.cancelStatus == null) &&
                  userInfo.username == scope.row.operNam
                "
                >修改</el-button
              >
              <el-button
                type="text"
                size="small"
                icon="el-icon-delete"
                @click="deleteHandle(scope.row.fId)"
                v-if="
                  scope.row.isCancel != 1 &&
                  scope.row.conFile != 1 &&
                  permissions.contract_del &&
                  (scope.row.contractStatus != 2 ||
                    scope.row.cancelStatus == 2) &&
                  userInfo.username == scope.row.operNam
                "
                >作废</el-button
              >
              <el-button
                type="text"
                size="small"
                icon="el-icon-delete"
                @click="submitDeleteCheck(scope.row.fId)"
                v-if="
                  scope.row.contractStatus == 2 &&
                  scope.row.conFile != 1 &&
                  permissions.contract_del &&
                  (scope.row.cancelStatus == null ||
                    scope.row.cancelStatus == 0 ||
                    scope.row.cancelStatus == 3)
                "
                >提交作废审核</el-button
              >
              <el-button
                type="text"
                size="small"
                icon="el-icon-delete"
                @click="conFile(scope.row.fId)"
                v-if="
                  scope.row.contractStatus == 2 &&
                  scope.row.conFile != 1 &&
                  (scope.row.cancelStatus == 0 ||
                    scope.row.cancelStatus == null) &&
                  scope.row.iscancel != 1
                "
                >归档</el-button
              >
              <el-button
                type="text"
                size="small"
                icon="el-icon-view"
                @click="contractChildHandle(scope.row.contNo, scope.row.fId)"
                v-if="scope.row.isCancel != 1 && scope.row.contNoMaster == null"
                >查看子合同</el-button
              >
              <el-button
                type="text"
                size="small"
                icon="el-icon-folder-opened"
                @click="addOrUpdateHandle(scope.row.contNo)"
                v-if="
                  scope.row.isCancel != 1 &&
                  scope.row.contNoMaster == null &&
                  scope.row.conFile == 0
                "
                >子合同</el-button
              >
              <el-button
                type="text"
                size="small"
                icon="el-icon-s-unfold"
                v-if="scope.row.contractStatus != 0"
                @click="checkProcessHandle(scope.row.fId, scope.row.times, '0')"
                >查看审核进度</el-button
              >
              <el-button
                type="text"
                size="small"
                icon="el-icon-s-unfold"
                v-if="
                  scope.row.cancelStatus != 0 &&
                  scope.row.cancelStatus != '' &&
                  scope.row.cancelStatus != null
                "
                @click="checkProcessHandle(scope.row.fId, scope.row.times, '3')"
                >查看作废审核进度</el-button
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

      <!-- 弹窗, 提交审核-->
      <check-submit
        v-if="checkSubmitVisible"
        ref="checkSubmit"
        @refreshDataList="getDataList"
      ></check-submit>

      <!-- 弹窗,子合同 -->
      <child-form
        v-if="ChildFormVisible"
        ref="contractChild"
        @refreshDataList="getDataList"
      ></child-form>

      <el-dialog title="合同预览" :visible.sync="PrintdialogVisible">
        <div class="table-html-wrap" v-html="tableHtml" id="printTest"></div>
        <div style="margin-top: 5%; margin-left: 80%">
          <el-button @click="PrintdialogVisible = false">取 消</el-button>
          <el-button type="primary" v-print="'#printTest'">打 印</el-button>
        </div>
      </el-dialog>
      <el-dialog title="作废原因" :visible.sync="cancelReasonVisible">
        <el-input type="textarea" v-model="visibleReasonOne" />
        <el-button
          type="primary"
          @click="gotoCancelCheck(3)"
          style="margin-top: 10px; margin-left: 83%"
          >选择作废审批人</el-button
        >
      </el-dialog>
    </basic-container>
  </div>
</template>

<script>
import { fetchList, delObj, filePageList, handleDown, getContractType, putObj, expoprtExcel, getContractCheckProcess, getInoutIdDic, getTradeIdDic, dealt } from '@/api/baoshuisystem/contract'
import TableForm from './contract-form'
import ProcessForm from './contractCheckProcess'
import ChildForm from './contract-child'
import checkSubmit from './contract-checksubmit'
import { mapGetters } from 'vuex'
import { getClientInfoAll as allClientInfo } from '@/api/baoshuisystem/clientinfo'
import { getContractObjectInfoAll as carGoNamesList } from '@/api/baoshuisystem/contractobject'
import { all as allCargoKind } from '@/api/baoshuisystem/goodscategory'
import { getContractFileDic } from '@/api/baoshuisystem/getdictdata'
import XLSX from 'xlsx'
import { getProcessSetGroup, getProcessSets } from '@/api/baoshuisystem/approvalprocessset'
import XEUtils from 'xe-utils'


export default {
  data() {
    return {
      dataForm: {
        key: '',
        isCancel: '',
        contNo: '',
        dept: '',
        shipNam: '',
        contractType: '',
        cargoNam: '',
        params: [],
        conDteParams: []

      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      addOrUpdateVisible: false,
      checkProcessVisible: false,
      ChildFormVisible: false,
      checkSubmitVisible: false,
      contractTypeOptions: [],
      cargoNamOptions: [],
      contractCheckProcessOptions: [],
      contractFileOptions: [],
      secondNamOptions: [],
      cargoKindOptions: [],
      params: {},
      currentRow: null,
      infoList: [],
      valList: [],
      tableHtml: "",
      PrintdialogVisible: false,
      single: true,
      processInfo: {},
      cancelReasonVisible: false,
      visibleReasonOne: '',
      isCancelOptions: [{
        key: "否",
        value: ""
      }, {
        key: "是",
        value: "1"
      }],
      inoutIdDicOptions: [],
      tradeIdDicOptions: [],
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
      value1: [new Date(2000, 10, 10, 10, 10), new Date(2000, 10, 11, 10, 10)],
      value2: ''
    };

  },
  components: {
    TableForm,
    ProcessForm,
    ChildForm,
    checkSubmit
  },
  created() {
    this.getDataList()
    this.dataForm.cargoNam = ""
  },
  computed: {
    ...mapGetters(['permissions']),
    ...mapGetters(['userInfo'])
  },
  methods: {
    // 获取数据列表
    async getDataList() {
      console.log(this.userInfo)
      this.visibleReasonOne = ''
      this.dataListLoading = true
      getContractCheckProcess().then(data => {
        this.contractCheckProcessOptions = data.data.data
      })
      filePageList(Object.assign({
        current: this.pageIndex,
        size: this.pageSize,
      }, this.dataForm)).then(response => {
        this.dataList = response.data.data.records
        console.log("ssss",this.dataList)
        this.totalPage = response.data.data.total
        this.dataListLoading = false
        getInoutIdDic().then(response => {
          this.inoutIdDicOptions = response.data.data
          getTradeIdDic().then(response => {
            this.tradeIdDicOptions = response.data.data
            for (var i in this.dataList) {
              for (var j in this.inoutIdDicOptions) {
                if (this.dataList[i].inoutId == this.inoutIdDicOptions[j].value) {
                  this.dataList[i].inoutId = this.inoutIdDicOptions[j].label
                }
              }
              for (var j in this.tradeIdDicOptions) {
                if (this.dataList[i].tradeId == this.tradeIdDicOptions[j].value) {
                  this.dataList[i].tradeId = this.tradeIdDicOptions[j].label
                }
              }
              if (XEUtils.isNumber(this.dataList[i].cargoNum)) {
                this.dataList[i].cargoNum = Number(this.dataList[i].cargoNum)
                  .toFixed(2)
                  .replace(/(\d)(?=(\d{3})+\.)/g, ($0, $1) => {
                    return $1 + ",";
                  })
                  .replace(/\.$/, "");
                // this.dataList[i].cargoNum += '';
                // if (!this.dataList[i].cargoNum.includes('.')) this.dataList[i].cargoNum += '.';
                // this.dataList[i].cargoNum = (this.dataList[i].cargoNum).toString().replace(/(\d)(?=(\d{3})+\.)/g, function ($0, $1) {
                //   return $1 + ',';
                // }).replace(/\.$/, '');
              }
              if (XEUtils.isNumber(this.dataList[i].cargoFee)) {
                this.dataList[i].cargoFee = Number(this.dataList[i].cargoFee)
                  .toFixed(2)
                  .replace(/(\d)(?=(\d{3})+\.)/g, ($0, $1) => {
                    return $1 + ",";
                  })
                  .replace(/\.$/, "");
              }
            }
          })
        })

      })
      carGoNamesList().then(response => {
        this.cargoNamOptions = response.data.data
      })
      getContractFileDic().then(response => {
        this.contractFileOptions = response.data.data
      })
      allCargoKind().then(response => {
        this.cargoKindOptions = response.data.data
      })
      await getContractType().then(response => {
        this.contractTypeOptions = response.data.data
      })
      allClientInfo().then(response => {
        this.secondNamOptions = response.data.data
      })
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

    //审批进度
    checkProcessHandle(id, times, processNo) {
      this.checkProcessVisible = true
      this.$nextTick(() => {
        this.$refs.checkProcess.init(id, times, processNo)
      })
    },

    //审批进度
    contractChildHandle(contNo, fId) {
      this.ChildFormVisible = true
      this.$nextTick(() => {
        this.$refs.contractChild.init(contNo, fId)
      })
    },
    // 删除
    deleteHandle(id) {
      this.$confirm('是否确认作废？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function () {
        return delObj(id)
      }).then(data => {
        this.$message.success('作废成功')
        this.getDataList()
      })
    },

    downloadFile: function (filename, bucketName) {

      handleDown(filename, bucketName)
    },
    resetForm(formName) {
      if (this.$refs[formName] !== undefined) {
        this.$refs[formName].resetFields();
      }
      this.single = true
      this.setCurrent();
    },
    handleCurrentChange(val) {
      if (val != null && (val.contractStatus == 0 || val.contractStatus == 3)) {
        this.single = false
      } else {
        this.single = true
      }
      this.currentRow = val;
    },
    setCurrent(row) {
      this.$nextTick(() => {

        this.$refs.singleTable.setCurrentRow(row);
      });

    },
    goToCheck(processNo) {
      if (this.currentRow == null) {
        this.$alert('未选择合同', '提醒', {
          confirmButtonText: '确定',
          callback: action => {

          }
        })
      } else if (this.currentRow.contractStatus == 1 || this.currentRow.contractStatus == 2) {
        this.$alert('该合同已提交审批', '提醒', {
          confirmButtonText: '确定',
          callback: action => {

          }
        });
      } else if (this.currentRow.isCheck == 1) {
        this.$alert('作废合同不可审批', '提醒', {
          confirmButtonText: '确定',
          callback: action => {

          }
        });
      } else {
        this.$confirm('合同提交后不可修改，是否确认提交审核该合同?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {

          this.checkSubmitVisible = true
          this.$nextTick(() => {
            this.$refs.checkSubmit.init(this.currentRow, processNo)
          })

        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消提交'
          });
        });



      }

    },
    expoprt() {
      if (!this.currentRow) {
        this.$alert('未选择合同', '提醒', {
          confirmButtonText: '确定',
          callback: action => {

          }
        });
      } else {

        dealt(this.currentRow.fId).then((response) => { // 处理返回的文件流
          if (response.status === 200) {
            this.PrintdialogVisible = true;
            var data = new Uint8Array(response.data)
            var workbook = XLSX.read(data, {
              type: 'array'
            })
            var worksheet = workbook.Sheets[workbook.SheetNames[0]];
            var a = worksheet['!ref']
            a = "A1:D13"
            worksheet['!ref'] = a
            var innerHTML = XLSX.utils.sheet_to_html(worksheet);
            this.tableHtml = innerHTML;
          }
        })
        //expoprtExcel(this.currentRow.fId)
      }

    },
    contractStatusFormat(row, column, cellValue, index) {
      for (var i in this.contractCheckProcessOptions) {
        if (cellValue == this.contractCheckProcessOptions[i].value) {
          return this.contractCheckProcessOptions[i].label
        }
      }

    },
    contractFileFormat(row, column, cellValue, index) {
      for (var i in this.contractFileOptions) {
        if (cellValue == this.contractFileOptions[i].value) {
          return this.contractFileOptions[i].label
        }
      }

    },
    conFile(fid) {
      var formD = {}
      formD.conFile = 1
      formD.fId = fid
      this.$confirm('是否确定归档(归档后合同不可作废)?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        putObj(formD).then(data => {
          this.$notify.success('归档成功')
          this.getDataList()
        }).catch(() => {
          this.canSubmit = true
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消提交'
        });
      });
    },

    getProList(row) {
      this.dataListLoading = true;
      var map = { billFid: row.fId, processNo: '0' }
      getProcessSets(map).then(data => {
        this.infoList = data.data.data || []
        this.processInfo = {}

        for (var i in this.infoList) {

          if (this.infoList[i].opinion == undefined || this.infoList[i].opinion == '') {
            this.infoList[i].approvalTim = ""
          }

          //processInfo[this.infoList[i].linkNam] = this.infoList[i]
          if (this.processInfo[this.infoList[i].linkNam] == undefined) {
            this.processInfo[this.infoList[i].linkNam] = { opinion: '', approvalTim: '', linkNam: '', orderBy: '' }
          }
          this.processInfo[this.infoList[i].linkNam].opinion += (this.infoList[i].manName + ':' + (this.infoList[i].opinion || '') + "<br/>" + (this.infoList[i].opinionExplain || '') + "<br/>" + (this.infoList[i].approvalTim || '') + "<br/><br/>")

          //this.processInfo[this.infoList[i].linkNam].approvalTim = this.infoList[i].approvalTim
          this.processInfo[this.infoList[i].linkNam].linkNam = this.infoList[i].linkNam
          this.processInfo[this.infoList[i].linkNam].orderBy = this.infoList[i].orderBy

        }
        this.valList = []

        for (var key in this.processInfo) {

          this.valList.push(this.processInfo[key])
        }
        this.dataListLoading = false
      })
    },
    tableRowClassName({ row, rowIndex }) {
      if (row.contractStatus == 3) {
        return 'warning-row';
      }
      return '';
    },
    submitDeleteCheck(fId) {
      this.visibleReasonOne = ''
      this.cancelReasonVisible = true
    },
    gotoCancelCheck(processNo) {
      this.cancelReasonVisible = false
      this.checkSubmitVisible = true
      this.currentRow.cancelReason = this.visibleReasonOne
      this.$nextTick(() => {
        this.$refs.checkSubmit.init(this.currentRow, processNo)
      })
    },

  }
}
</script>
<style>
.demo-table-expand table {
  border: 1px black solid;
}
.table-col {
  border-bottom: 1px #bdbdbd solid;
}
.titleDiv {
  width: 100%;
  height: 50px;
  border: 1px black solid;
  text-align: center;
  line-height: 50px;
  font-weight: bolder;
  font-size: x-large;
}
.valueDiv {
  width: 100%;
  border: 1px black solid;
  text-align: center;
  margin: 0px -1px -1px 0px;
}
.span2 span {
  height: 100%;
  width: 25%;
  display: inline-block;
  border-right: 0.5px black solid;
}
.span4 span {
  height: 100%;
  width: calc(100% / 8);
  display: inline-block;
  border-right: 0.5px black solid;
}
.span3 span {
  height: 100%;
  width: calc(100% / 6);
  display: inline-block;
  border-right: 0.5px black solid;
}
.onespan {
  height: 100%;
  width: 80%;
  display: inline-block;
  border-right: 0.5px black solid;
}
.onespanLabel {
  height: 100%;
  width: 20%;
  display: inline-block;
  border-right: 0.5px black solid;
}

.infor .el-col {
  margin-bottom: -10px;
}
.infor .el-form-item {
  width: 100%;
}
.infor .el-form-item__content {
  width: 65%;
}
.infor label {
  width: 30%;
}
#rem .el-form-item__content {
  width: 90%;
}
#rem label {
  width: 10%;
}
#fee .el-form-item__content {
  width: 60%;
}
#fee label {
  width: 40% !important;
}
/*
#feeI label {
  width: 10%;
}
#feeI .el-form-item__content {
  width: 90%;
} */
#feeRow {
  margin-left: 5%;
}
#fileFor label {
  width: 10%;
}
#fileFor span {
  border-right: black 0px solid;
}
#isCancel .el-form-item__content {
  width: 45%;
}
#isFile .el-form-item__content {
  width: 45%;
}
#shipNam .el-form-item__content {
  width: 60%;
}
#dept .el-form-item__content {
  width: 70%;
}
#cargoNam .el-form-item__content {
  width: 60%;
}
#startDate .el-form-item__content {
  width: 79%;
}
#signDte .el-form-item__content {
  width: 79%;
}
#status .el-form-item__content {
  width: 70%;
}
#cargoKindNam .el-form-item__content {
  width: 60%;
}
.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}
</style>

<style scoped lang="scss">
.table-html-wrap /deep/ table {
  // border-right: 1px solid #e8eaec;
  // border-bottom: 1px solid #e8eaec;
  border-collapse: collapse;
  margin: auto;
}

.table-html-wrap /deep/ table td {
  border-left: 1px solid black;
  border-bottom: 1px solid black;
  border-right: 1px solid black;
  white-space: nowrap;
  text-align: center;
  padding: 0.5rem;
}

.table-html-wrap /deep/ tbody tr:nth-of-type(1) {
  font-weight: bold;
  font-size: 20px;
  border: 0px solid white !important;
}
.table-html-wrap /deep/ tbody tr:nth-of-type(1) td {
  border: 0px solid white !important;
}
.table-html-wrap /deep/ tbody tr:nth-of-type(2) {
  border: 0px solid white !important;
}
.table-html-wrap /deep/ tbody tr:nth-of-type(2) td {
  text-align: right !important;
  border: 0px solid white !important;
  border-bottom: 1px solid black !important;
}
#signDte {
  margin-left: -2%;
}
#isFile {
  margin-left: -2%;
}
</style>
