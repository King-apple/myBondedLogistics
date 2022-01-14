<template>
  <el-dialog
    title="子合同列表"
    :close-on-click-modal="false"
    :visible.sync="childVisible"
    width="92%"
  >
    <div class="mod-config">
      <basic-container>
        <div class="avue-crud" style="width: 100%">
          <el-table
            highlight-current-row
            :data="dataList"
            border
            autocomplete="off"
            v-loading="dataListLoading"
            ref="singleTable"
            @current-change="handleCurrentChange"
          >
            <!-- <el-table-column
            prop="fId"
            header-align="center"
            align="center"
            label="主键"
            hidden="hidden"
          >
          </el-table-column> -->

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
                      <el-form-item label="标的额">
                        <el-input
                          v-model="props.row.dept"
                          placeholder="标的额"
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
                          placeholder="合同标的"
                          readonly
                        ></el-input> </el-form-item
                    ></el-col>
                    <el-col :span="8"
                      ><el-form-item label="合同标的数量">
                        <el-input
                          v-model="props.row.cargoNum"
                          placeholder="合同标的数量"
                          readonly
                        ></el-input> </el-form-item
                    ></el-col>
                    <el-col :span="8">
                      <el-form-item label="标的额">
                        <el-input
                          v-model="props.row.cargoFee"
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
                  <el-form-item style="border: #bdbdbd 1px solid" id="feeI">
                    <div
                      v-for="(item, index) in props.row.contractRateList"
                      :key="index"
                      id="fee"
                    >
                      费目{{ index + 1 }}
                      <el-row id="feeRow">
                        <el-col :span="6"
                          ><el-form-item label="费目名称：">
                            <el-input
                              v-model="item.feeNam"
                              placeholder="费目名称："
                              readonly
                            ></el-input> </el-form-item
                        ></el-col>
                        <el-col :span="6"
                          ><el-form-item label="费率值：">
                            <el-input
                              v-model="item.feeRat"
                              placeholder="费率值："
                              readonly
                            ></el-input> </el-form-item
                        ></el-col>
                        <el-col :span="6"
                          ><el-form-item label="费目名称：">
                            <el-input
                              v-model="item.feeNam"
                              placeholder="费目名称："
                              readonly
                            ></el-input> </el-form-item
                        ></el-col>
                        <el-col :span="6"
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
              prop="cargoNam"
              header-align="center"
              align="center"
              label="合同标的"
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
                    scope.row.isCancel != 1
                  "
                  >修改</el-button
                >
                <el-button
                  type="text"
                  size="small"
                  icon="el-icon-delete"
                  @click="deleteHandle(scope.row.fId)"
                  v-if="scope.row.isCancel != 1 && scope.row.conFile != 1"
                  >作废</el-button
                >
                <el-button
                  type="text"
                  size="small"
                  icon="el-icon-delete"
                  @click="conFile(scope.row.fId)"
                  v-if="scope.row.contractStatus == 2 && scope.row.conFile != 1"
                  >归档</el-button
                >
                <el-button
                  type="text"
                  size="small"
                  icon="el-icon-folder-opened"
                  @click="addOrUpdateHandle(scope.row.contNo)"
                  v-if="
                    scope.row.isCancel != 1 && scope.row.contNoMaster == null
                  "
                  >子合同</el-button
                >
                <el-button
                  type="text"
                  size="small"
                  icon="el-icon-s-unfold"
                  v-if="scope.row.contractStatus != 0"
                  @click="checkProcessHandle(scope.row.fId, scope.row.times)"
                  >查看审核进度</el-button
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

        <!-- 弹窗, 新增 / 修改 -->
        <process-form
          v-if="checkProcessVisible"
          ref="checkProcess"
          @refreshDataList="getDataList"
        ></process-form>
      </basic-container>
    </div>
  </el-dialog>
</template>

<script>
import { fetchList, delObj, filePageList, handleDown, getContractType, putObj, expoprtExcel, getContractCheckProcess, getInoutIdDic, getTradeIdDic } from '@/api/baoshuisystem/contract'
import TableForm from './contract-form'
import ProcessForm from './contractCheckProcess'
import { mapGetters } from 'vuex'
import { fetchList as carGoNamesList } from '@/api/baoshuisystem/contractobject'
import { saveRecords } from '@/api/baoshuisystem/approvalrecord'

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
      childVisible: false,
      contractTypeOptions: [],
      cargoNamOptions: [],
      contractCheckProcessOptions: [],
      params: {},
      currentRow: null,
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
    ProcessForm
  },
  created() {
    //this.getDataList()
    this.dataForm.cargoNam = ""
  },
  computed: {
    ...mapGetters(['permissions'])
  },
  methods: {
    init(contNo, fId) {
      this.dataForm.contNo = contNo
      this.dataForm.contNoMaster = fId
      this.childVisible = true
      this.getDataList();

    },
    // 获取数据列表
    async getDataList() {
      console.log(this.permissions)
      this.dataListLoading = true
      getContractCheckProcess().then(data => {
        this.contractCheckProcessOptions = data.data.data
      })
      filePageList(Object.assign({
        current: this.pageIndex,
        size: this.pageSize,
      }, this.dataForm)).then(response => {
        this.dataList = response.data.data.records
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
            }
          })
        })
      })
      carGoNamesList().then(response => {
        this.cargoNamOptions = response.data.data.records
      })


      await getContractType().then(response => {
        this.contractTypeOptions = response.data.data
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
    checkProcessHandle(id, times) {
      this.checkProcessVisible = true
      this.$nextTick(() => {
        this.$refs.checkProcess.init(id, times)
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
        this.$message.success('删除成功')
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
      this.setCurrent();
    },
    handleCurrentChange(val) {
      this.currentRow = val;
    },
    setCurrent(row) {
      this.$nextTick(() => {
        this.$refs.singleTable.setCurrentRow(row);
      });

    },
    goToCheck() {
      if (this.currentRow == null) {
        this.$alert('未选择合同', '提醒', {
          confirmButtonText: '确定',
          callback: action => {

          }
        });
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
        this.$confirm('合同提交后不可修改作废，是否确认提交审核该合同?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var times = this.currentRow.times
          if (times == 0 || times == null) {
            times = 1;
          } else {
            times++
          }
          var map = { fId: this.currentRow.fId, contractStatus: '1', times: times }
          putObj(map).then(response => {
            saveRecords({ processCode: "0", cFid: this.currentRow.fId, times: times }).then(data => {
              this.$message.success('提交成功')
              this.getDataList()
            })

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
      console.log(this.currentRow)
      if (!this.currentRow) {
        this.$alert('未选择合同', '提醒', {
          confirmButtonText: '确定',
          callback: action => {

          }
        });
      } else {
        expoprtExcel(this.currentRow.fId)
      }

    },
    contractStatusFormat(row, column, cellValue, index) {
      for (var i in this.contractCheckProcessOptions) {
        if (cellValue == this.contractCheckProcessOptions[i].value) {
          return this.contractCheckProcessOptions[i].label
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
        }).catch(() => {
          this.canSubmit = true
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消提交'
        });
      });
    }
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
.formTab label {
  height: 30px !important;
}
.formTab .el-col {
  margin-bottom: -10px !important;
}
.formTab button {
  margin-top: 30px !important;
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

#feeI label {
  width: 10%;
}
#feeI .el-form-item__content {
  width: 90%;
}
#feeRow {
  margin-left: 5%;
}
#fileFor label {
  width: 10%;
}
#fileFor span {
  border-right: black 0px solid;
}
</style>