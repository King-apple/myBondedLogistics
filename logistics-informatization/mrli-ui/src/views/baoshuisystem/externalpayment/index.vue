<template>
  <div class="mod-config">
    <basic-container>
      <el-form
        :inline="true"
        :model="dataForm"
        @keyup.enter.native="getDataList()"
        ref="dataForm"
      >
        <div style="display: flex">
          <el-form-item label="付款编号" prop="cargoNamCustoms">
            <el-input
              v-model="dataForm.billCod"
              style="width: 7vw"
              placeholder="请输入付款编号"
            ></el-input>
          </el-form-item>
          <el-form-item label="客户" prop="secondNam">
            <el-input
              v-model="dataForm.secondNam"
              style="width: 7vw"
              placeholder="请选择客户"
            ></el-input>
          </el-form-item>
          <el-form-item label="合同编号" prop="contCod">
            <el-select
              clearable
              v-model="dataForm.contCod"
              placeholder="请选择合同编号:"
              style="width: 8vw"
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
          <el-form-item label="货类名称" prop="cargoKindNam">
            <el-select
              clearable
              v-model="dataForm.cargoKindNam"
              placeholder="请选择货类名称:" 
              filterable
              style="width: 7vw"
            >
              <el-option
                v-for="(item, index) in carGoKindOptions"
                :key="index"
                :label="item.cargoKindNam"
                :value="item.cargoKindNam"
                @click.native="selectCGK(item)"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="日期范围" prop="params">
            <el-date-picker
              style="width: 15vw"
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
              size="mini"
              @click="getDataListQuery"
              >查询</el-button
            >
          </el-form-item>
          <el-form-item>
            <el-button
              type="warning"
              icon="el-icon-refresh"
              size="mini"
              @click="resetForm('dataForm')"
              >重置</el-button
            >
          </el-form-item>
        </div>
        <el-form-item>
          <el-button
            icon="el-icon-plus"
            type="primary"
            size="mini"
            @click="addOrUpdateHandle()"
            >新增</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button
            icon="el-icon-plus"
            type="info"
            size="mini"
            @click="manuallyAdd(dataListFrom.dept)"
            >手动新增</el-button
          >
        </el-form-item>
      </el-form>

      <div class="avue-crud" style="width: 100%">
        <el-table :data="dataList" border v-loading="dataListLoading">
          <el-table-column type="expand" fixed="left">
            <template slot-scope="props">
              <el-form
                label-position="left"
                inline
                class="demo-table-expand myForm"
              >
                <div v-for="(item, index) of props.row.bbBodyList" :key="index">
                  <el-form-item label="预收预付款">
                    {{ props.row.bbBodyList[index].itemNam }}
                  </el-form-item>
                  <el-form-item label="费目">
                    {{ props.row.bbBodyList[index].feeNam }}
                  </el-form-item>
                  <el-form-item label="费率值">
                    {{ props.row.bbBodyList[index].feeRat }}
                  </el-form-item>
                  <el-form-item label="费率单位">
                    {{ props.row.bbBodyList[index].feeUnit }}
                  </el-form-item>
                  <el-form-item label="预收预付款">
                    {{ props.row.bbBodyList[index].advinceMoney }}
                  </el-form-item>
                  <el-form-item label="应收应付款">
                    {{ props.row.bbBodyList[index].dueMoney }}
                  </el-form-item>
                  <el-form-item label="项目">
                    {{ props.row.bbBodyList[index].itemNam }}
                  </el-form-item>
                  <el-form-item label="备注">
                    {{ props.row.bbBodyList[index].markTxt }}
                  </el-form-item>
                  <hr />
                </div>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column
            prop="billCod"
            header-align="center"
            align="center"
            label="付款编号"
            sortable
            width="200px"
          >
          </el-table-column>
          <el-table-column
            prop="contCod"
            header-align="center"
            align="center"
            label="合同编号"
            sortable
            width="160px"
          >
          </el-table-column>
          <el-table-column
            width="100px"
            prop="dept"
            header-align="center"
            align="center"
            sortable
            label="承办部门"
          >
          </el-table-column>
          <el-table-column
            prop="billDate"
            header-align="center"
            align="center"
            label="日期"
            width="100px"
            sortable
          >
          </el-table-column>
          <el-table-column
            prop="secondNam"
            header-align="center"
            align="center"
            label="客户"
            sortable
            width="100px"
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
            prop="cargoNam"
            header-align="center"
            align="center"
            label="货名"
            sortable
            width="100px"
          >
          </el-table-column>
          <el-table-column
            prop="feeQuantity"
            header-align="center"
            align="center"
            label="计费数量"
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
            prop="operator"
            header-align="center"
            align="center"
            label="经办人"
            sortable
            width="100px"
          >
          </el-table-column>
          <el-table-column
            prop="operNam"
            header-align="center"
            align="center"
            label="添加人"
            sortable
            width="100px"
          >
          </el-table-column>
          <el-table-column
            prop="recordTim"
            header-align="center"
            align="center"
            label="添加时间"
            sortable
            width="150px"
          >
          </el-table-column>
          <el-table-column
            prop="updateNam"
            header-align="center"
            align="center"
            label="修改人"
            sortable
            width="100px"
          >
          </el-table-column>
          <el-table-column
            prop="updateTim"
            header-align="center"
            align="center"
            label="修改时间"
            sortable
            width="100px"
          >
          </el-table-column>
          <el-table-column
            fixed="right"
            width="200px"
            header-align="center"
            align="center"
            label="操作"
          >
            <template slot-scope="scope">
              <el-button
                type="text"
                size="small"
                icon="el-icon-edit"
                @click="addOrUpdateHandle(scope.row.fId)"
                >修改
              </el-button>
              <el-button
                type="text"
                size="small"
                icon="el-icon-delete"
                @click="deleteHandle(scope.row)"
                >删除
              </el-button>
              <el-button
                type="text"
                size="small"
                icon="el-icon-delete"
                @click="expoprt(scope.row)"
                >点击导出
              </el-button>
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
        title="新增"
        :visible.sync="dialogVisible"
        width="60%"
        :close-on-click-modal="false"
      >
        <el-form
          :model="dataListFrom"
          ref="dataListFrom"
          @keyup.enter.native="dataFormSubmit()"
          label-width="80px"
        >
          <el-row>
            <el-col :span="8">
              <el-form-item
                label="合同编号"
                prop="contCod"
                style="width: 300px"
                :rules="[
                  {
                    required: true,
                    message: '请输入合同编号',
                    trigger: 'blur',
                  },
                ]"
              >
                <el-input
                  v-model="dataListFrom.contCod"
                  placeholder="合同编号:"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                label="日期:"
                prop="billDate"
                :rules="[
                  { required: true, message: '请输入日期', trigger: 'blur' },
                ]"
              >
                <el-date-picker
                  v-model="dataListFrom.billDate"
                  type="date"
                  placeholder="日期"
                  value-format="yyyy-MM-dd"
                  style="width: 200px"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                label="客户"
                prop="secondNam"
                style="width: 280px"
                :rules="[
                  { required: true, message: '请输入客户', trigger: 'blur' },
                ]"
              >
                <el-input
                  v-model="dataListFrom.secondNam"
                  placeholder="客户"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item
                label="计费数量"
                prop="feeQuantity"
                style="width: 280px"
                :rules="[
                  {
                    required: true,
                    message: '请输入计费数量',
                    trigger: 'blur',
                  },
                ]"
              >
                <el-input
                  v-model="dataListFrom.feeQuantity"
                  @input="oninput"
                  placeholder="计费数量"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                label="承办单位"
                prop="firstNam"
                style="width: 280px"
              >
                <el-select
                  v-model="dataListFrom.firstNam"
                  placeholder="请选择"
                  filterable
                  clearable
                  style="width: 100%"
                >
                  <el-option
                    v-for="item in options"
                    :key="item.organizer"
                    :label="item.organizer"
                    :value="item.organizer"
                    @click.native="selectOp(item)"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                label="承办部门"
                prop="dept"
                style="width: 280px"
                :rules="[
                  {
                    required: true,
                    message: '请输入承办部门',
                    trigger: 'blur',
                  },
                ]"
              >
                <el-input
                  v-model="dataListFrom.dept"
                  placeholder="承办部门"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item
                label="付款编号"
                prop="billCod"
                style="width: 280px"
              >
                <el-input
                  v-model="dataListFrom.billCod"
                  placeholder="付款编号"
                  v-if="flag == 1"
                  disabled
                ></el-input>
                <el-input
                  v-model="dataListFrom.billCod"
                  placeholder="点击确定按钮生成"
                  v-else
                  disabled
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                label="入境船名"
                prop="inShipName"
                style="width: 280px"
              >
                <el-input
                  v-model="dataListFrom.inShipName"
                  placeholder="船名"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                label="出境船名"
                prop="outShipName"
                style="width: 280px"
              >
                <el-input
                  v-model="dataListFrom.outShipName"
                  placeholder="船名"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item
                label="货类名称"
                prop="cargoKindNam"
                style="width: 180px"
              >
                <el-select
                  v-model="dataListFrom.cargoKindNam"
                  placeholder="货类名称"
                  filterable
                  clearable
                >
                  <el-option
                    v-for="(item, index) in carGoKindOptions"
                    :key="index"
                    :label="item.cargoKindNam"
                    :value="item.cargoKindNam"
                    @click.native="selectCGK(item)"
                  >
                  </el-option>
                </el-select>
                <el-input
                  v-model="dataListFrom.cargoKindCod"
                  placeholder="货类编码"
                  disabled
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                label="货物名称"
                prop="cargoNam"
                style="width: 180px"
              >
                <el-select
                  v-model="dataListFrom.cargoNam"
                  placeholder="货物名称"
                  filterable
                  clearable
                >
                  <el-option
                    v-for="(item, index) in cargoNamOptions"
                    :key="index"
                    :label="item.cargoNam"
                    :value="item.cargoNam"
                    @click.native="selectCNO(item)"
                  >
                  </el-option>
                </el-select>
                <el-input
                  v-model="dataListFrom.cargoCod"
                  placeholder="货物代码"
                  disabled
                ></el-input>
              </el-form-item>
          
            </el-col>
          </el-row>
          <el-card class="b">
            <div slot="header" class="clearfix">
              <span>应预收款</span>
              <el-button
                style="float: right; padding: 3px 0"
                type="text"
                @click="addFee"
                >点击新增</el-button
              >
            </div>
            <div v-for="(accountsListItem, index) of accountsList" :key="index">
              <div style="display: flex">
                <el-form-item
                  :label="'费目' + (index + 1)"
                  style="width: 280px"
                  prop="feeNam"
                >
                  <el-select
                    v-model="accountsListItem.feeNam"
                    placeholder="请选择"
                    filterable
                    @focus="getFeeNam"
                    @loading="feeLoading"
                  >
                    <el-option
                      v-for="(item, feeIndex) in feeNameList"
                      :key="feeIndex"
                      :label="item.feeNam"
                      :value="item.feeNam"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item
                  label="费率单位"
                  style="width: 280px"
                  prop="feeUnit"
                
                >
                  <el-select
                    v-model="accountsListItem.feeUnit"
                    placeholder="请选择"
                    filterable
                    @focus="feeAll"
                    @loading="feeLoading"
                  >
                    <el-option
                      v-for="(item1, feeUnitIndex) in feeUnitList"
                      :key="feeUnitIndex"
                      :label="item1.feeUnit"
                      :value="item1.feeUnit"
                    >
                    </el-option
                  ></el-select>
                </el-form-item>
                <el-form-item label="费率值" style="width: 280px" prop="feeRat">
                  <el-input
                    v-model="accountsListItem.feeRat"
                    placeholder="费率值"
                  ></el-input>
                </el-form-item>
              </div>
              <div style="display: flex">
                <el-form-item
                  label="预收款"
                  style="width: 200px"
                  prop="advinceMoney"
                >
                  <el-input
                    v-model="accountsListItem.advinceMoney"
                    placeholder="预收款"
                  ></el-input>
                </el-form-item>
                <el-form-item label="数量" style="width: 200px">
                  <el-input
                    v-model="accountsListItem.feeQuantity"
                    placeholder="数量"
                    @input="oninputAccountsFeeQuantity($event, index)"
                  ></el-input>
                </el-form-item>
                <el-form-item
                  label="应收款"
                  style="width: 200px"
                  prop="dueMoney"
                >
                  <el-input
                    v-model="accountsListItem.dueMoney"
                    placeholder="应收款"
                  ></el-input>
                </el-form-item>
                <el-form-item label="备注">
                  <el-input
                    type="textarea"
                    :rows="1"
                    v-model="accountsListItem.markTxt"
                    style="width: 100px"
                    placeholder="请输入内容"
                  ></el-input>
                  <span style="margin-left: 20px"></span>
                </el-form-item>
                <el-button
                  type="danger"
                  size="mini"
                  @click="deleteRow(index, accountsListItem)"
                  icon="el-icon-remove-outline"
                  >删除</el-button
                >
              </div>
            </div>
            <div style="display: flex; justify-content: space-between; width: auto;">
              <el-form-item label="付款单位">
            <el-input v-model="dataForm.paymentUnit" placeholder="付款单位" style="width:120px"></el-input>
          </el-form-item>
              <span><strong><font color="red">预收款合计:</font>{{getCount(accountsList)}}</strong></span>
              <span><strong><font color="red">数量合计:</font>{{getTaxMoneyCount(accountsList)}}</strong></span>
              <span><strong><font color="red">应收款合计:</font>{{getTotalMoneyCount(accountsList)}}</strong></span>
            </div>
          </el-card>
          <el-card class="b">
            <div slot="header" class="clearfix">
              <span>应预付款</span>
              <el-button
                style="float: right; padding: 3px 0"
                type="text"
                @click="addAccount"
                >点击新增</el-button
              >
              <div v-for="(advanceListItem, index) of advanceList" :key="index">
                <div style="display: flex">
                  <el-form-item
                    :label="'费目' + (index + 1)"
                    style="width: 280px"
                    prop="feeNam"
                  >
                    <el-select
                      v-model="advanceListItem.feeNam"
                      placeholder="请选择"
                      filterable
                      @focus="getFeeNam"
                      @loading="feeLoading"
                    >
                      <el-option
                        v-for="(item, feeIndex) in feeNameList"
                        :key="feeIndex"
                        :label="item.feeNam"
                        :value="item.feeNam"
                      >
                      </el-option>
                    </el-select>
                  </el-form-item>
                  <el-form-item
                    label="费率单位"
                    style="width: 280px"
                    prop="feeUnit"
                  >
                    <el-select
                      v-model="advanceListItem.feeUnit"
                      placeholder="请选择"
                      filterable
                      @focus="feeAll"
                      @loading="feeLoading"
                    >
                      <el-option
                        v-for="(item1, feeUnitIndex) in feeUnitList"
                        :key="feeUnitIndex"
                        :label="item1.feeUnit"
                        :value="item1.feeUnit"
                      >
                      </el-option
                    ></el-select>
                  </el-form-item>
                  <el-form-item
                    label="费率值"
                    style="width: 280px"
                    prop="feeRat"
                  >
                    <el-input
                      v-model="advanceListItem.feeRat"
                      placeholder="费率值"
                    ></el-input>
                  </el-form-item>
                </div>
                <div style="display: flex">
                  <el-form-item
                    label="预收款"
                    style="width: 200px"
                    prop="advinceMoney"
                  >
                    <el-input
                      v-model="advanceListItem.advinceMoney"
                      placeholder="预收款"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="数量" style="width: 200px">
                    <el-input
                      v-model="advanceListItem.feeQuantity"
                      placeholder="数量"
                    ></el-input>
                  </el-form-item>
                  <el-form-item
                    label="应预付款"
                    style="width: 200px"
                    prop="dueMoney"
                  >
                    <el-input
                      v-model="advanceListItem.dueMoney"
                      placeholder="应预付款"
                    ></el-input>
                  </el-form-item>
                  <el-form-item label="备注" prop="markTxt">
                    <el-input
                      type="textarea"
                      :rows="1"
                      v-model="advanceListItem.markTxt"
                      style="width: 100px"
                      placeholder="请输入内容"
                    ></el-input>
                    <span style="margin-left: 20px"></span>
                  </el-form-item>
                  <el-button
                    type="danger"
                    size="mini"
                    @click="deleteAccount(index, advanceListItem)"
                    icon="el-icon-remove-outline"
                    >删除</el-button
                  >
                </div>
              </div>
            </div>
            <div style="display: flex; justify-content: space-between; width: auto;">
            <el-form-item label="收款单位">
            <el-input v-model="dataForm.ceceivingUnit" placeholder="收款单位" style="width:120px"></el-input>
             </el-form-item>
              <span><strong><font color="red">预收款合计:</font>{{getCount(advanceList)}}</strong></span>
              <span><strong><font color="red">数量合计:</font>{{getTaxMoneyCount(advanceList)}}</strong></span>
              <span><strong><font color="red">应收款合计:</font>{{getTotalMoneyCount(advanceList)}}</strong></span>
            </div>
          </el-card>
          <el-form-item
            label="附件"
            prop="urlFile"
            v-loading="fileLoading"
            element-loading-text="文件上传中"
            element-loading-spinner="el-icon-loading"
          >
            <el-upload
              :headers="headers"
              :show-file-list="false"
              :on-success="uploadSuccess"
              class="avatar-uploader"
              action="/baoshuisystem/sys-file/upload"
              :on-progress="onProgress"
            >
              <el-button size="small" type="primary">点击上传</el-button>
            </el-upload>
            <ul id="example-1">
              <li v-for="(item, index) in fileOptions" :key="index">
                {{ item.originalFilename }}
                <i
                  class="el-icon-download"
                  style="
                    float: right;
                    left: 40%;
                    height: 32px;
                    line-height: 32px;
                    position: absolute;
                  "
                  @click="downloadFile(index)"
                ></i>
                <i
                  class="el-icon-delete"
                  style="
                    float: right;
                    margin-right: 50%;
                    height: 32px;
                    line-height: 32px;
                  "
                  @click="deleteFile(index)"
                ></i>
              </li>
            </ul>
          </el-form-item>
        </el-form>

        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="clickSure">确 定</el-button>
        </span>
      </el-dialog>
    </basic-container>
  </div>
</template>

<script>
import {
  fetchList,
  delObj,
  expertExternal,
  findBusinessList,
  expertBusiness,
  getObj,
  addObj,
  putObj,
  getitemNameType,
  getInvoicTpye,
  addList,
} from "@/api/baoshuisystem/externalpayment";
import {
  fetchList as feeFetch,
  saveList as contractSaveList,
  getRateList,
  removeBycFid,
} from "@/api/baoshuisystem/feenames";
import TableForm from "./businessbillshead-form";
import { mapGetters } from "vuex";
import { all as carGoKindAll } from "@/api/baoshuisystem/goodscategory";
import {
  getSelectContract,
  getEffectiveInfo,
  fileSave,
  getFileInfo,
  delFile,
  handleDown,
  fileUpdate,
  getInsertValue,
  getUserInfo,
} from "@/api/baoshuisystem/contract";
import { getObj as getCompany } from "@/api/baoshuisystem/parameterlist";
import store from "@/store";
import { onlyNumOnePoint } from "@/util/util";
import { getObj as getObjDept } from "@/api/admin/dept";
import { getAll as feeunitAll } from "@/api/baoshuisystem/feeunits";
export default {
  data() {
    return {
      fileLoading: false,
      carGoKindOptions: [],
      settledContractOptions: [],
      flag: 0,
      headers: {
        Authorization: "Bearer " + store.getters.access_token,
      },
      dataForm: {
        key: "",
      },
      dataList: [],
      pageIndex: 1,
      options: [],
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      addOrUpdateVisible: false,
      dialogVisible: false,
      accountsList: [],
      advanceList: [],
      feeUnitList: [],
      fileOptions: [],
      cargoNamOptions: [],
      feeNameList: [],
      fileList: [],
      feeLoading: false,
      dataListFrom: {
        fId: 0,
        firstNam: "",
        companyNam: "",
        billKnd: "付款单",
        billNam: "对外付款审批单",
        billCod: "",
        contCod: "",
        cargoKindNam: "",
        cargoKindCod: "",
        firstCod: "",
        dept: "",
        billDate: "",
        secondCod: "",
        secondNam: "",
        shipNam: "",
        cargoCod: "",
        cargoNam: "",
        feeQuantity: "",
        feeNam: "",
        operator: "",
        operNam: "",
        recordTim: "",
        updateNam: "",
        updateTim: "",
        delNam: "",
        delTim: "",
        money: "",
        params: [],
        feeUnit: "",
        feeRat: "",
        markTxt: "",
        inShipName: "",
        outShipName: "",
        inputType: "",
        //付款单位
        paymentUnit:"",
        //收款单位
        ceceivingUnit:"",
      },
    };
  },
  components: {
    TableForm,
  },
  created() {
    this.getDataList();
    this.getSelectContractList();
    this.getCarGoKind();
    this.getInfo();
  },
  computed: {
    ...mapGetters(["permissions"]),
  },
  methods: {
     deleteRow(index, data) {
      if (data.fId != null) {
        this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            delBody(data).then((response) => {
              this.$message({
                type: "success",
                message: "删除成功!",
              });
              this.accountsList.splice(index, 1);
            });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除",
            });
          });
      } else {
        this.accountsList.splice(index, 1);
      }
    },
    deleteAccount(index, data) {
      if (data.fId != null) {
        this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            delBody(data).then((response) => {
              this.$message({
                type: "success",
                message: "删除成功!",
              });
              this.advanceList.splice(index, 1);
            });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除",
            });
          });
      } else {
        this.advanceList.splice(index, 1);
      }
    },
     getCount(data){
       let temp = 0;
       for (let index = 0; index < data.length; index++) {
         const element = data[index];
          temp += Number(element.advinceMoney)
        
       }
      return temp == undefined ? 0 : temp.toFixed(2)
    },
    getTaxMoneyCount(data){
       let temp = 0;
       for (let index = 0; index < data.length; index++) {
         const element = data[index];
          temp += Number(element.feeQuantity)
        
       }
      return temp == undefined ? 0 : temp.toFixed(2)
    },
    getTotalMoneyCount(data){
       let temp = 0;
       for (let index = 0; index < data.length; index++) {
         const element = data[index];
          temp += Number(element.dueMoney)
        
       }
      return temp == undefined ? 0 : temp.toFixed(2)
    },
    feeAll() {
      feeunitAll().then((response) => {
        this.feeUnitList = response.data.data;
        this.feeLoading = false;
      });
    },
    getFeeNam: function () {
      if (this.feeNameList.length == 0) {
        this.feeLoading = true;
        feeFetch().then((response) => {
          this.feeNameList = response.data.data.records;
          this.fee;
        });
      }
    },
    selectOp(data) {
      this.dataListFrom.firstCod = data.organizerCode;
    },
    selectCGK(item) {
      //this.dataListFrom.cargoKindCod = item.cargoKindCod;
      this.$set(this.dataListFrom,"cargoKindCod",item.cargoKindCod)
    },
    selectCNO(data) {
      console.log(data)
      // this.dataListFrom.cargoCod = data.cargoCod;
      this.$set(this.dataListFrom,"cargoCod",data.cargoCod)
    },
    clickSure() {
      this.$refs.dataListFrom.validate((valid) => {
        if ((this.dataListFrom.cargoNam == "" ||this.dataListFrom.cargoNam == undefined)
         &&(this.dataListFrom.cargoKindNam == "" ||this.dataListFrom.cargoKindNam == undefined) ) {
          this.$message.error("请选择货名或货类");
          return;
        }
        if (this.advanceList.length != 0) {
          for (let index = 0; index < this.advanceList.length; index++) {
            const element = this.advanceList[index];
            if (element.advinceMoney == undefined ||element.dueMoney == undefined) {
              this.$message.error("请至少填写预收款和预付款");
              return;
            }
          }
        }
        if (this.accountsList.length != 0) {
          for (let index = 0; index < this.accountsList.length; index++) {
            const element = this.accountsList[index];
            if (element.advinceMoney == undefined ||element.dueMoney == undefined ) {
              this.$message.error("请至少填写预收款和预付款");
              return;
            }
          }
        }
        if (valid) {
          // 向后台发送请求
          this.$set(this.dataListFrom, "billKnd", "付款单");
          this.$set(this.dataListFrom, "billNam", "对外付款审批单");
          this.$set(this.dataListFrom, "inputType", 1);
          addObj(this.dataListFrom)
            .then((data) => {
              //文件添加
              for (var i in this.fileList) {
                this.fileList[i].tableName = 'bussinessbill'
                this.fileList[i].tableFid = data.data.data.fId
              }
              fileSave(this.fileList)
              this.advanceList.forEach((item, index) => {
                item.itemNam = "应付款";
              });
              this.accountsList.forEach((item, index) => {
                item.itemNam = "应收款";
              });
              this.allList = this.advanceList.concat(this.accountsList);
              this.allList.forEach((item, index) => {
                if (item.hfid == null || item.hfid == "") {
                  item.hfid = Number(data.data.data.fId);
                }
                if (item.billCod == null || item.billCod == "") {
                  item.billCod = data.data.data.billCod;
                }
              });
              addList(this.allList).then((response) => {
                this.dialogVisible = false;
                this.$notify.success("添加成功");
                this.getDataList();
              });
            })
        } else {   this.$notify.success("请联系开发人员！！");
          //就像用户提示发生错误的消息
        }
      });
    },
    addFee: function () {
      this.accountsList.push({
        feeNam: "",
        itemName: "",
        dueMoney: 0,
        feeRat: 0,
        advinceMoney: 0,
        feeQuantity: this.dataListFrom.feeQuantity == undefined ? 0 :this.dataListFrom.feeQuantity ,
      });
    },
    addAccount() {
      this.advanceList.push({
        feeNam: "",
        itemName: "",
        dueMoney:0,
        feeRat: 0,
        advinceMoney: 0,
        feeQuantity:this.dataListFrom.feeQuantity == undefined ? 0 :this.dataListFrom.feeQuantity ,
      });
    },
  async  getInfo() {
      getUserInfo().then((res) => {
        getObjDept(res.data.data.sysUser.deptSubId).then((res) => {
          this.dataListFrom.dept = res.data.data.name;
        });
      });

      await getInsertValue().then((response) => {
        console.log("response", response);
        this.options = response.data.data.organizerInfos;
        this.cargoNamOptions = response.data.data.contractObjects;
      });
    },
    onlyNumOnePoint,
    oninput(obj) {
      this.dataListFrom.feeQuantity = this.onlyNumOnePoint(obj);
    },
    oninputAccountsFeeQuantity(event, index) {
      this.accountsList[index].feeQuantity = this.onlyNumOnePoint(event);
    },
    getCompanyName() {
      getCompany(7).then((response) => {
        this.dataListFrom.companyNam = response.data.data.paramValue;
      });
    },
    manuallyAdd(data) {
      this.dataListFrom = {};
      this.dataListFrom.dept = data;
      this.accountsList = [];
      this.advanceList = [];
      this.getCompanyName();
      this.dialogVisible = true;
      this.feeUnitList = [];
      this.fileOptions = [];
      this.getInfo()
    },
    getCarGoKind() {
      carGoKindAll().then((response) => {
        this.carGoKindOptions = response.data.data;
      });
    },
    getDept(id) {
      getObj(id).then((res) => {
        this.deptName = res.data.data.name;
      });
      return this.deptName;
    },
    async expoprt(data) {
      expertExternal("对外付款审批单.xlsx", data);
    },
    async getSelectContractList() {
      await getSelectContract().then((res) => {
        this.settledContractOptions = res.data.data;
      });
    },
    async getDataListQuery() {
      this.getDataList();
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
      )
        .then((response) => {
          this.dataList = response.data.data.records;
          this.totalPage = response.data.data.total;
        })
        .catch((err) => console.log(err));
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
        this.flag = 1;
      });
    },
    resetForm(formName) {
      //使用前，先绑定el-form的 ref
      this.dataForm = {};
      this.getDataList();
    },

    // 删除
    deleteHandle(id) {
      this.$confirm("是否确认删除结算编号" + id.billCod + "?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delObj(id.fId);
        })
        .then((data) => {
          this.$message.success("删除成功");
          this.getDataList();
        });
    },
    uploadSuccess: function (e) {
      if (e.data == null) {
        this.$notify.success("文件上传失败");
      } else {
        this.fileOptions.push({ originalFilename: e.data.originalFilename });
        //this.$notify.success(e.msg)
        var bucketName = e.data.bucketName;
        var fileName = e.data.fileName;
        var url = e.data.url;
        var originalFilename = e.data.originalFilename;
        var fileSize = e.data.fileSize;
        this.fileList.push({
          bucketName: bucketName,
          fileName: fileName,
          url: url,
          original: originalFilename,
          fileSize: fileSize,
        });
        if (this.dataListFrom.fId != 0) {
          var fileOne = [
            {
              bucketName: bucketName,
              fileName: fileName,
              url: url,
              original: originalFilename,
              fileSize: fileSize,
              tableName: "business_bills_head",
              tableFid: this.dataListFrom.fId,
            },
          ];
          fileSave(fileOne).then((response) => {
            if (response.data.data != null) {
              this.fileList[this.fileList.length - 1].id = response.data.data;
            }
          });
        }

        this.fileLoading = false;
      }
    },
    deleteFile: function (index) {
      var id = this.fileList[index].id;
      this.fileList.splice(index, 1);
      this.fileOptions.splice(index, 1);
      if (this.dataListFrom.fId != 0) {
        delFile(id).then((response) => {});
      }
    },
    onProgress(event, file, fileList) {
      this.fileLoading = true;
    },

    downloadFile: function (index) {
      var filename = this.fileList[index].fileName;
      var bucket = this.fileList[index].bucketName;
      handleDown(filename, bucket);
    },
  },
};
</script>
<style scope>
.myForm {
  font-size: 0;
}

.myForm label {
  width: 90px;
  color: #99a9bf;
}

.myForm .el-form-item {
  margin-right: -338px;
  margin-bottom: 0;
  width: 33%;
}

.to_flex {
  display: flex;
  justify-content: flex-start;
}
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.b {
  width: 900px;
}
</style>
