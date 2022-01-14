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
          <el-form-item label="结算编号" prop="cargoNamCustoms">
            <el-input
              v-model="dataForm.billCod"
              placeholder="请输入结算编号"
              style="width: 7vw"
            ></el-input>
          </el-form-item>
          <el-form-item label="结算单位" prop="secondNam">
            <el-input
              v-model="dataForm.secondNam"
              placeholder="请选择结算单位"
              style="width: 7vw"
            ></el-input>
          </el-form-item>
          <el-form-item label="合同编号" prop="cargoNamCustoms">
            <el-select
              clearable
              v-model="dataForm.contCod"
              style="width: 8vw"
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
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="日期范围" prop="params">
            <el-date-picker
              v-model="dataForm.params"
              style="width: 15vw"
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
                class="demo-table-expand myData"
              >
                <div v-for="(item, index) of props.row.bbBodyList" :key="index">
                  <el-form-item :label="'费目' + (index + 1)">
                    {{ props.row.bbBodyList[index].feeNam }}
                  </el-form-item>
                  <el-form-item label="费率值">
                    {{ props.row.bbBodyList[index].feeRat }}
                  </el-form-item>
                  <el-form-item label="金额">
                    {{ props.row.bbBodyList[index].money }}
                  </el-form-item>
                  <el-form-item label="税率">
                    {{ props.row.bbBodyList[index].taxRate }}
                  </el-form-item>
                  <el-form-item label="税额">
                    {{ props.row.bbBodyList[index].taxMoney }}
                  </el-form-item>
                  <el-form-item label="价税">
                    {{ props.row.bbBodyList[index].totalMoney }}
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
            sortable
            fixed="left"
            width="200px"
            header-align="center"
            align="center"
            label="结算编号"
          >
          </el-table-column>
          <el-table-column
            prop="contCod"
            sortable
            width="150px"
            header-align="center"
            align="center"
            label="合同编号"
          >
          </el-table-column>
          <el-table-column
            prop="dept"
            sortable
            width="100px"
            header-align="center"
            align="center"
            label="承办部门"
          >
          </el-table-column>
          <el-table-column
            prop="billDate"
            width="90px"
            sortable
            header-align="center"
            align="center"
            label="日期"
          >
          </el-table-column>
          <el-table-column
            prop="secondNam"
            width="100px"
            sortable
            header-align="center"
            align="center"
            label="结算单位"
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
            sortable
            header-align="center"
            align="center"
            label="货名"
          >
          </el-table-column>
          <el-table-column
            prop="cargoKindNam"
            sortable
            header-align="center"
            align="center"
            label="货类"
          >
          </el-table-column>
          <el-table-column
            prop="feeQuantity"
            width="100px"
            sortable
            header-align="center"
            align="center"
            label="计费数量"
          >
          </el-table-column>
          <el-table-column
            prop="firstNam"
            width="215px"
            sortable
            header-align="center"
            align="center"
            label="承办单位"
          >
          </el-table-column>
          <el-table-column
            sortable
            header-align="center"
            align="center"
            label="利润"
          >
            <template slot-scope="scope">
              {{ getProfits(scope.row) }}
            </template>
          </el-table-column>
          <el-table-column
            sortable
            prop="operator"
            width="100px"
            header-align="center"
            align="center"
            label="经办人"
          >
          </el-table-column>
          <el-table-column
            sortable
            prop="operNam"
            width="100px"
            header-align="center"
            align="center"
            label="添加人"
          >
          </el-table-column>
          <el-table-column
            sortable
            prop="recordTim"
            width="140px"
            header-align="center"
            align="center"
            label="添加时间"
          >
          </el-table-column>
          <el-table-column
            sortable
            prop="updateNam"
            width="100px"
            header-align="center"
            align="center"
            label="修改人"
          >
          </el-table-column>
          <el-table-column
            sortable
            prop="updateTim"
            width="140px"
            header-align="center"
            align="center"
            label="修改时间"
          >
          </el-table-column>
          <el-table-column
            fixed="right"
            width="255px"
            header-align="center"
            align="center"
            label="操作"
          >
            <template slot-scope="scope">
              <el-button
                type="text"
                icon="el-icon-edit"
                @click="addOrUpdateHandle(scope.row.fId)"
                >修改
              </el-button>
              <el-button
                type="text"
                icon="el-icon-delete"
                @click="deleteHandle(scope.row)"
                >删除
              </el-button>
              <el-button
                type="text"
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
    </basic-container>
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
              style="width: 280px"
              :rules="[
                {
                  required: true,
                  message: '合同编号',
                  trigger: 'blur',
                },
              ]"
            >
              <el-input
                clearable
                v-model="dataListFrom.contCod"
                placeholder="合同编号:"
                filterable
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="日期:"
              prop="billDate"
              :rules="[
                {
                  required: true,
                  message: '日期',
                  trigger: 'blur',
                },
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
            <el-form-item label="承办部门" prop="dept" style="width: 280px">
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
              label="计费数量"
              prop="feeQuantity"
              style="width: 280px"
              :rules="[
                {
                  required: true,
                  message: '计费数量',
                  trigger: 'blur',
                },
              ]"
            >
              <el-input
                v-model.lazy="dataListFrom.feeQuantity"
                @input="oninputThree"
                placeholder="计费数量"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="发票类型"
              prop="invoiceType"
              style="width: 300px"
              :rules="[
                {
                  required: true,
                  message: '发票类型',
                  trigger: 'blur',
                },
              ]"
            >
              <el-select
                clearable
                v-model="dataListFrom.invoiceType"
                placeholder="发票类型:"
                filterable
              >
                <el-option
                  v-for="(item, index) in invoicTpyeDict"
                  :key="index"
                  :label="item.label"
                  :value="item.label"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="承办单位"
              prop="firstNam"
              style="width: 280px"
              :rules="[
                {
                  required: true,
                  message: '承办单位',
                  trigger: 'blur',
                },
              ]"
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
                  :key="item.fid"
                  :label="item.organizer"
                  :value="item.organizer"
                  @click.native="selectOp(item)"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row> </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="结算编号" prop="billCod" style="width: 280px">
              <el-input
                v-model="dataListFrom.billCod"
                placeholder="结算编号"
                v-if="flag == 1"
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
              label="结算单位"
              prop="secondNam"
              style="width: 280px"
              :rules="[
                {
                  required: true,
                  message: '合同编号',
                  trigger: 'blur',
                },
              ]"
            >
              <el-input
                v-model="dataListFrom.secondNam"
                placeholder="结算单位"
              ></el-input>
            </el-form-item>
          </el-col>
          <!-- <el-col :span="8">
            <el-form-item label="利润" style="width: 280px">
              <el-input
                v-model="profitsData"
                placeholder="费用信息添加完之后显示"
                disabled
              ></el-input>
            </el-form-item>
          </el-col> -->
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item
              label="入境船名"
              prop="inShipName"
              style="width: 280px"
            >
              <el-input
                v-model="dataForm.inShipName"
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
                v-model="dataForm.outShipName"
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
            <el-form-item label="货物名称" prop="cargoNam" style="width: 180px">
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

        <el-card class="a">
          <div slot="header" class="clearfix">
            <span>应收款</span>
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
                style="width: 260px"
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
                style="width: 260px"
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
              <el-form-item label="费率值" style="width: 260px" prop="feeRat">
                <el-input
                  v-model="accountsListItem.feeRat"
                  placeholder="费率值"
                ></el-input>
              </el-form-item>
              <el-form-item label="数量" style="width: 260px">
                <el-input
                  v-model.lazy="accountsListItem.feeQuantity"
                  placeholder="数量"
                  @input="oninputAccountsFeeQuantity($event, index)"
                ></el-input>
              </el-form-item>
            </div>
            <div style="display: flex">
              <el-form-item label="金额" style="width: 210px">
                <el-input
                  v-model="accountsListItem.money"
                  placeholder="金额"
                  @input="oninputTwoAccountsList($event, index)"
                ></el-input>
              </el-form-item>
              <el-form-item label="税额" style="width: 208px">
                <el-input
                  v-model="accountsListItem.taxMoney"
                  placeholder="税额"
                  @input="oninputTwoAccountsListTaxMoney($event, index)"
                ></el-input>
              </el-form-item>
              <el-form-item label="价税合计" style="width: 208px">
                <el-input
                  v-model="accountsListItem.totalMoney"
                  placeholder="价税合计"
                  @input="accountsTwo($event, index)"
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
              <div>
                <el-button
                  class="deleteButton"
                  type="danger"
                  size="small"
                  @click="deleteRow(index, accountsListItem)"
                  icon="el-icon-remove-outline"
                  >删除</el-button
                >
              </div>
            </div>
          </div>
          <div
            style="display: flex; justify-content: space-between; width: auto"
          >
            <el-form-item label="付款单位">
              <el-input
                v-model="dataForm.paymentUnit"
                placeholder="付款单位"
                style="width: 120px"
              ></el-input>
            </el-form-item>
            <span
              ><strong
                ><font color="red">金额合计:</font
                >{{ getCount(accountsList) }}</strong
              ></span
            >
            <span
              ><strong
                ><font color="red">税额合计:</font
                >{{ getTaxMoneyCount(accountsList) }}</strong
              ></span
            >
            <span
              ><strong
                ><font color="red">税价合计:</font
                >{{ getTotalMoneyCount1(accountsList) }}</strong
              ></span
            >
            <span
              ><strong
                ><font color="red">数量合计:</font
                >{{ getFeeQuanCount(accountsList) }}</strong
              ></span
            >
          </div>
        </el-card>
        <el-card class="a">
          <div slot="header" class="clearfix">
            <span>应付款</span>
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
                  style="width: 300px"
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
                  style="width: 300px"
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
                <el-form-item label="费率值" style="width: 300px" prop="feeRat">
                  <el-input
                    v-model="advanceListItem.feeRat"
                    placeholder="费率值"
                  ></el-input>
                </el-form-item>

                <el-form-item label="数量" style="width: 300px">
                  <el-input
                    v-model="advanceListItem.feeQuantity"
                    placeholder="数量"
                    @input="advanceListFeeQuantity($event, index)"
                  ></el-input>
                </el-form-item>
              </div>
              <div style="display: flex">
                <el-form-item label="金额" style="width: 210px">
                  <el-input
                    v-model="advanceListItem.money"
                    placeholder="金额"
                    @input="oninputTwoAdvanceList($event, index)"
                  ></el-input>
                </el-form-item>
                <el-form-item label="税额" style="width: 208px">
                  <el-input
                    v-model="advanceListItem.taxMoney"
                    placeholder="税额"
                    @input="oninputTwoAdvanceListTaxMoney($event, index)"
                  ></el-input>
                </el-form-item>
                <el-form-item label="价税合计" style="width: 208px">
                  <el-input
                    v-model="advanceListItem.totalMoney"
                    placeholder="价税合计"
                    @input="advanceTwo($event, index)"
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
                <div>
                  <el-button
                    type="danger"
                    size="small"
                    @click="deleteAccount(index, advanceListItem)"
                    icon="el-icon-remove-outline"
                    >删除</el-button
                  >
                </div>
              </div>
            </div>
          </div>
          <div
            style="display: flex; justify-content: space-between; width: auto"
          >
            <el-form-item label="收款单位">
              <el-input
                v-model="dataForm.ceceivingUnit"
                placeholder="收款单位"
                style="width: 120px"
              ></el-input>
            </el-form-item>
            <span
              ><strong
                ><font color="red">金额合计:</font
                >{{ getCount2(advanceList) }}</strong
              ></span
            >
            <span
              ><strong
                ><font color="red">税额合计:</font
                >{{ getTaxMoneyCount(advanceList) }}</strong
              ></span
            >
            <span
              ><strong
                ><font color="red">税价合计:</font
                >{{ getTotalMoneyCount2(advanceList) }}</strong
              ></span
            >
            <span
              ><strong
                ><font color="red">数量合计:</font
                >{{ getFeeQuanCount(advanceList) }}</strong
              ></span
            >
            <span
              ><strong
                ><font color="red">利润:</font>{{ getProfitsData() }}</strong
              ></span
            >
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
  </div>
</template>

<script>
import {
  findBusinessList,
  delObj,
  expertBusiness,
  getObj,
  addObj,
  putObj,
  getitemNameType,
  getInvoicTpye,
  addList,
} from "@/api/baoshuisystem/externalpayment";
import TableForm from "./businessbillshead-form";
import { all as carGoKindAll } from "@/api/baoshuisystem/goodscategory";
import { mapGetters } from "vuex";
import { onlyNumOnePoint } from "@/util/util";
import { getObj as getObjDept } from "@/api/admin/dept";
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
import { getAll as feeunitAll } from "@/api/baoshuisystem/feeunits";
import {
  getFeenamesAll as feeFetch,
  saveList as contractSaveList,
  getRateList,
  removeBycFid,
} from "@/api/baoshuisystem/feenames";
import { getObj as getCompany } from "@/api/baoshuisystem/parameterlist";
import store from "@/store";
import XEUtils from 'xe-utils';
export default {
  data() {
    return {
      dialogVisible: false,
      deptName: "",
      cargoNamOptions: [],
      settledContractOptions: [],
      carGoKindOptions: [],
      advanceList: [],
      flag: 0,
      feeLoading: false,
      accountsList: [],
      dataForm: {
        key: "",
        //利润
        profits: "",
      },
      headers: {
        Authorization: "Bearer " + store.getters.access_token,
      },
      fileLoading: false,
      dataListFrom: {
        fId: 0,
        companyNam: "",
        billKnd: "业务结算单",
        billNam: "业务结算单",
        billCod: "",
        contCod: "",
        dept: "",
        billDate: "",
        firstNam: "",
        firstCod: "",
        secondCod: "",
        secondNam: "",
        shipNam: "",
        cargoKindNam: "",
        cargoKindCod: "",
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
        invoiceType: "",
        inShipName: "",
        outShipName: "",
        inputType: "",
        //付款单位
        paymentUnit: "",
        //收款单位
        ceceivingUnit: "",
      },
      fileList: [],
      fileOptions: [],
      feeNameList: [],
      feeUnitList: [],
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      addOrUpdateVisible: false,
      invoicTpyeDict: [],
      options: [],
      allList: [],
      profits: 0,
      profitsed: 0,
      countMoney: 0,
      countMoney2: 0,
      totalMoney1:0,
      totalMoney2:0,
    };
  },
  components: {
    TableForm,
  },
  created() {
    this.getCarGoKind();
    this.getDataList();
    this.getSelectContractList();
    this.getInfo();
  },
  mounted() { },
  computed: {
    getProfits() {
      return function (data) {
        let arrayList = data.bbBodyList;
        let collectionCount = 0;
        let accounts = 0;
        let profits = 0;
        arrayList.forEach((item) => {
          //获取应付
          if (item.itemNam == "应收款") {
            collectionCount += item.money;
          } else {
            accounts += item.money;
          }
        });
        profits = collectionCount - accounts;
        //注意，在这里一定要将function中的值返回，不然会没有效果
        return profits >= 0 ? profits.toFixed(2) : 0;
      };
    },
    //计算利润不存入数据库只展现
  
  },
  methods: {
    getProfitsData() {
      return (this.totalMoney1 - this.totalMoney2).toFixed(2)
    },
    getCount(data) {
      let temp = 0;
      data.forEach(res => {
        temp += Number(res.money)
      })
      this.countMoney = temp
      return this.countMoney == "" ? 0 : temp.toFixed(2)
    },
    getCount2(data) {
      let temp = 0;
      data.forEach(res => {
        temp += Number(res.money)
      })
      this.countMoney2 = temp
      return this.countMoney2 == "" ? 0 : temp.toFixed(2)
    },
    getTaxMoneyCount(data) {
      let temp = 0;
      data.forEach(res => {
        temp += Number(res.taxMoney)
      })
      return temp == "" ? 0 : temp.toFixed(2)
    },
    getTotalMoneyCount1(data) {
      let temp = 0;
      data.forEach(res => {
        temp += Number(res.totalMoney)
      })
      this.totalMoney1 = temp
      return this.totalMoney1 == "" ? 0 : temp.toFixed(2)
    },
    getTotalMoneyCount2(data) {
      let temp = 0;
      data.forEach(res => {
        temp += Number(res.totalMoney)
      })
      this.totalMoney2= temp
      return this.totalMoney2 == "" ? 0 : temp.toFixed(2)
    },
    getFeeQuanCount(data) {
      let temp = 0;
      data.forEach(res => {
        temp += Number(res.feeQuantity)
      })

      return XEUtils.isNaN(temp) ? 0 : temp.toFixed(2)
    },
    getCompanyName() {
      getCompany(7).then((response) => {
        this.dataListFrom.companyNam = response.data.data.paramValue;
      });
    },
    clickSure() {
      this.$refs.dataListFrom.validate((valid) => {
        if ((this.dataListFrom.cargoNam == "" || this.dataListFrom.cargoNam == undefined)
          && (this.dataListFrom.cargoKindNam == "" || this.dataListFrom.cargoKindNam == undefined)) {
          this.$message.error("请选择货名或货类");
          return;
        }
        if (this.advanceList.length != 0) {
          for (let index = 0; index < this.advanceList.length; index++) {
            const element = this.advanceList[index];
            if (element.feeNam == undefined || element.totalMoney == undefined
              || element.money == undefined || element.taxMoney == undefined
              || element.feeRat == undefined || element.feeQuantity == undefined) {
              this.$message.error('请填写所有项')
              return;
            }
          }
        }
        if (this.accountsList.length != 0) {
          for (let index = 0; index < this.accountsList.length; index++) {
            const element = this.accountsList[index];
            if (element.feeNam == undefined || element.totalMoney == undefined
              || element.money == undefined || element.taxMoney == undefined
              || element.feeRat == undefined || element.feeQuantity == undefined) {
              this.$message.error('请填写所有项')
              return;
            }
          }
        }
        if (valid) {
          this.$set(this.dataListFrom, "billKnd", "业务结算单");
          this.$set(this.dataListFrom, "billNam", "业务结算单");
          this.$set(this.dataListFrom, "inputType", 1);
          addObj(this.dataListFrom).then(data => {
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
              item.hfid = Number(data.data.data.fId);
              item.billCod = data.data.data.billCod;
            });
            addList(this.allList).then((response) => {
              this.dialogVisible = false
              this.$notify.success("添加成功");
              this.getDataList();
            });
          })
        } else {

        }
      })

    },
    manuallyAdd(data) {
      this.dataListFrom.dept = data
      this.accountsList = []
      this.advanceList = []
      this.getCompanyName()
      this.dialogVisible = true
      this.feeUnitList = [];
      this.fileOptions = [];
      this.dataListFrom = {}
    },
    feeAll() {
      feeunitAll().then((response) => {
        this.feeUnitList = response.data.data;
        this.feeLoading = false;
      });
    },
    deleteRow(index, data) {
      if (data.fId != null) {
        this.$confirm("此操作将删除该内容, 是否继续?", "提示", {
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
    getFeeNam: function () {
      if (this.feeNameList.length == 0) {
        this.feeLoading = true;
        feeFetch().then((response) => {
          this.feeNameList = response.data.data
          this.fee
        });
        feeunitAll().then((response) => {
          this.feeUnitList = response.data.data;
          this.feeLoading = false;
        });
      }
    },
    addFee: function () {
      this.accountsList.push({
        feeNam: "",
        totalMoney: 0,
        itemName: "",
        money: 0,
        taxMoney: 0,
        feeRat: 0,
        feeQuantity: this.dataListFrom.feeQuantity == "" ? 0 : this.dataListFrom.feeQuantity,
      });
    },
    addAccount() {
      this.advanceList.push({
        feeNam: "",
        itemName: "",
        totalMoney: 0,
        money: 0,
        taxMoney: 0,
        feeRat: 0,
        feeQuantity: this.dataListFrom.feeQuantity == "" ? 0 : this.dataListFrom.feeQuantity,
      });
    },
    getInfo() {
      getUserInfo().then((res) => {
        getObjDept(res.data.data.sysUser.deptSubId).then((res) => {
          this.dataListFrom.dept = res.data.data.name;
        });
      });
      carGoKindAll().then((response) => {
        this.carGoKindOptions = response.data.data;
      }),
        getInsertValue().then((response) => {
          this.options = response.data.data.organizerInfos;
          this.cargoNamOptions = response.data.data.contractObjects;
        });
      getitemNameType().then((response) => {
        this.itemNameTypeDict = response.data.data;
      });
      getInvoicTpye().then((response) => {
        this.invoicTpyeDict = response.data.data;
      });
    },
    onlyNumOnePoint,
    oninputThree(obj) {
      this.dataListFrom.feeQuantity = this.onlyNumOnePoint(obj);
    },
    oninputAccountsFeeQuantity(event, index) {
      this.accountsList[index].feeQuantity = this.onlyNumOnePoint(event);
    },
    oninputTwoAccountsList(event, index) {
      //限制输入2位小数
      this.accountsList[index].money = this.onlyNumOnePoint(event);
      this.accountsList[index].money = this.accountsList[index].money.replace(
        /^(\-)*(\d+)\.(\d\d).*$/,
        "$1$2.$3"
      );
    },
    oninputTwoAccountsListTaxMoney(event, index) {
      this.accountsList[index].taxMoney = this.onlyNumOnePoint(event);
      this.accountsList[index].taxMoney = this.accountsList[index].taxMoney.replace(/^(\-)*(\d+)\.(\d\d).*$/, "$1$2.$3");
    },
    accountsTwo(event, index) {
      this.accountsList[index].totalMoney = this.onlyNumOnePoint(event);
      this.accountsList[index].totalMoney = this.accountsList[index].totalMoney.replace(/^(\-)*(\d+)\.(\d\d).*$/, "$1$2.$3");
    },
    advanceListFeeQuantity(event, index) {
      this.advanceList[index].feeQuantity = this.onlyNumOnePoint(event);
    },
    oninputTwoAdvanceList(event, index) {
      this.advanceList[index].money = this.onlyNumOnePoint(event);
      this.advanceList[index].money = this.advanceList[index].money.replace(
        /^(\-)*(\d+)\.(\d\d).*$/,
        "$1$2.$3"
      );
    }, advanceTwo(event, index) {
      this.advanceList[index].totalMoney = this.onlyNumOnePoint(event);
      this.advanceList[index].totalMoney = this.advanceList[
        index
      ].totalMoney.replace(/^(\-)*(\d+)\.(\d\d).*$/, "$1$2.$3");
    }, oninputTwoAdvanceListTaxMoney(event, index) {
      this.advanceList[index].taxMoney = this.onlyNumOnePoint(event);
      this.advancesList[index].taxMoney = this.advanceList[
        index
      ].taxMoney.replace(/^(\-)*(\d+)\.(\d\d).*$/, "$1$2.$3");
    },
    selectCNO(data) {
      // this.dataListFrom.cargoCod = data.cargoCod;
      this.$set(this.dataListFrom, "cargoCod", data.cargoCod)
    },
    selectOp(data) {
      this.dataListFrom.firstCod = data.organizerCode;
      console.log("da",data)
    },
    selectCGK(item) {
      // this.dataListFrom.cargoKindCod = item.cargoKindCod;
      this.$set(this.dataListFrom, "cargoKindCod", item.cargoKindCod)
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
      expertBusiness("业务结算单.xlsx", data);
    },
    async getSelectContractList() {
      await getEffectiveInfo().then((res) => {
        this.settledContractOptions = res.data.data;
      });
    },
    async getDataListQuery() {
      this.getDataList();
    },
    // 获取数据列表
    async getDataList() {
      this.dataListLoading = true;
      findBusinessList(
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
        delFile(id).then((response) => { });
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
.myData {
  font-size: 0;
}

.myData label {
  width: 60px;
  color: #99a9bf;
}

.myData .el-form-item {
  margin-right: -429px;
  margin-bottom: 15px;
  width: 30%;
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

.box-card a {
  width: 900px;
}
</style>
