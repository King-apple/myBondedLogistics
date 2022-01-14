<template>
  <div class="mod-config">
    <basic-container>
      <el-form
        :inline="true"
        :model="dataForm"
        @keyup.enter.native="getDataList()"
      >
        <el-form-item>
          <el-form-item label="单据编号" prop="ioyardListNo">
            <el-input
              v-model="dataForm.ioyardListNo"
              placeholder="单据编号"
            ></el-input>
          </el-form-item>
          <el-form-item label="合同编号" prop="contNo">
            <el-input
              v-model="dataForm.contNo"
              placeholder="合同编号"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="getDataList">查询</el-button>
          </el-form-item>
          <el-button
            v-if="permissions.ioyardlisthead_add"
            icon="el-icon-plus"
            type="primary"
            @click="addOrUpdateHandle()"
            >新增</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button
            v-if="permissions.ioyardlisthead_add"
            icon="el-icon-upload2"
            type="success"
            @click="exportExcel()"
            :disabled="single"
            >导出</el-button
          >
        </el-form-item>
      </el-form>

      <div class="avue-crud">
        <el-table
          :data="dataList"
          border
          v-loading="dataListLoading"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55"> </el-table-column>
          <el-table-column
            header-align="center"
            align="center"
            label="展开"
            type="expand"
          >
            <template slot-scope="props">
              <div>
                <table class="tab">
                  <tr>
                    <th colspan="9">入库</th>
                    <th colspan="7">出库</th>
                  </tr>
                  <tr>
                    <th>入库日期</th>
                    <th>入库方式</th>
                    <th>作业委托人</th>
                    <th>品名</th>
                    <th>包装</th>
                    <th>堆存地点</th>
                    <th>入库量(件数)</th>
                    <th>入库过磅量(吨)</th>
                    <th>入库结算量(吨)</th>
                    <th>出库日期</th>
                    <th>出库方式</th>
                    <th>出库量(件数)</th>
                    <th>出库过磅数量(吨)</th>
                    <th>出库结算数量(吨)</th>
                    <th>备注</th>
                    <th>操作</th>
                  </tr>
                  <tr
                    v-for="(item, index) in props.row.ioyardListBodyList"
                    :key="index"
                  >
                    <td>{{ item.inYardDat }}</td>
                    <td>{{ item.inYardWay =="卸船" ? item.inYardWay+'('+item.inShipNam+')':item.inYardWay}}</td>
                    <td>{{ item.consignNam }}</td>
                    <td>{{ item.cargoNam }}</td>
                    <td>{{ item.cpkgKindNam }}</td>
                    <td>{{ item.yardNam }}</td>
                    <td>{{ item.inPiecesNum }}</td>
                    <td>{{ item.inWeightWgt }}</td>
                    <td>{{ item.inSettleWgt }}</td>
                    <td>{{ item.outYardDat }}</td>
                    <td>{{ item.outYardWay =="装船" ? item.outYardWay+'('+item.outShipNam+')':item.outYardWay}}</td>
                    <td>{{ item.outPiecesNum }}</td>
                    <td>{{ item.outWeightWgt }}</td>
                    <td>{{ item.outSettleWgt }}</td>
                    <td>{{ item.markTxt }}</td>
                    <td>
                      <el-button
                        v-if="permissions.ioyardlisthead_edit"
                        type="text"
                        size="small"
                        icon="el-icon-edit"
                        @click="alertDialog(item)"
                        >修改</el-button
                      >
                      <el-button
                        v-if="permissions.ioyardlistbody_del"
                        type="text"
                        size="small"
                        icon="el-icon-delete"
                        @click="deleteItemHandle(item.fId)"
                        >还原</el-button
                      >
                    </td>
                  </tr>
                </table>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            prop="ioyardListNam"
            header-align="center"
            align="center"
            label="单据名称"
          >
          </el-table-column>
          <el-table-column
            prop="ioyardListNo"
            header-align="center"
            align="center"
            label="编号"
          >
          </el-table-column>
          <el-table-column
            prop="contNo"
            header-align="center"
            align="center"
            label="合同编号"
          >
          </el-table-column>
          <el-table-column
            prop="listDat"
            header-align="center"
            align="center"
            label="日期"
          >
          </el-table-column>

          <el-table-column header-align="center" align="center" label="操作">
            <template slot-scope="scope">
              <el-button
                v-if="permissions.ioyardlisthead_del"
                type="text"
                size="small"
                icon="el-icon-delete"
                @click="deleteHandle(scope.row.fId)"
                >还原</el-button
              >
              <el-button
                v-if="permissions.ioyardlisthead_del"
                type="text"
                size="small"
                icon="el-icon-folder-opened"
                @click="openFileDialog(scope.row)"
                >查看附件</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>
      <el-dialog title="修改明细" :visible.sync="dialogFormVisible">
        <el-form :model="inoutlistForm">
          <el-form-item label="入库结算量" prop="hz">
            <el-input
              v-model="inoutlistForm.inSettleWgt"
              placeholder="入库结算量"
            ></el-input>
          </el-form-item>
          <el-form-item label="出库结算量" prop="hz">
            <el-input
              v-model="inoutlistForm.outSettleWgt"
              placeholder="出库结算量"
            ></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="selectClick()">确 定</el-button>
        </div>
      </el-dialog>

      <el-dialog title="查看附件" :visible.sync="fileDialogVisible">
        <el-form>
          <el-form-item label="附件(点击下载查看)" id="fileFor">
            <el-row v-for="(item, index) in sysFileList" :key="index">
              <el-col :span="24">
                <span
                  class="onespan"
                  @click="downloadFile(item.fileName, item.bucketName)"
                >
                  附件{{ index + 1 }} ：<u style="cursor: pointer">{{
                    item.original
                  }}</u></span
                >
              </el-col>
            </el-row>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="fileDialogVisible = false">取 消</el-button>
        </div>
      </el-dialog>
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
  </div>
</template>

<script>
import { getHeadBodyPage, delObj, putObjBody, exportFile, delItemObj } from '@/api/baoshuisystem/ioyardlisthead'
import { getInyardType, getOutyardType } from '@/api/baoshuisystem/instrogedetail'
import { getFileInfo, handleDown } from '@/api/baoshuisystem/contract'
import { getInIoyard } from '@/api/baoshuisystem/ioyard'
import TableForm from './ioyardlisthead-form'
import { mapGetters } from 'vuex'
import Table from '../../gen/table.vue'
export default {
  data() {
    return {
      dataForm: {
        key: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      addOrUpdateVisible: false,
      inyardTypeOptions: [],
      outyardTypeOptions: [],
      dialogFormVisible: false,
      inoutlistForm: {},
      single: true,
      fileDialogVisible: false,
      sysFileList: []
    }
  },
  components: {
    TableForm
  },

  created() {
    this.getDataList()

  },
  computed: {
    ...mapGetters(['permissions'])
  },
  methods: {
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true
      getHeadBodyPage(Object.assign({
        current: this.pageIndex,
        size: this.pageSize
      }, this.dataForm)).then(response => {
        this.dataList = response.data.data.records
        this.totalPage = response.data.data.total
        getInyardType().then(data => {
          this.inyardTypeOptions = data.data.data
          getOutyardType().then(data => {
            this.outyardTypeOptions = data.data.data
            for (var k in this.dataList) {
              for (const i in this.dataList[k].ioyardListBodyList) {
                for (var j in this.inyardTypeOptions) {
                  if (this.dataList[k].ioyardListBodyList[i].inYardWay == this.inyardTypeOptions[j].value) {
                    this.dataList[k].ioyardListBodyList[i].inYardWay = this.inyardTypeOptions[j].label
                  }

                }
                for (var j in this.outyardTypeOptions) {
                  if (this.dataList[k].ioyardListBodyList[i].outYardWay == this.outyardTypeOptions[j].value) {
                    this.dataList[k].ioyardListBodyList[i].outYardWay = this.outyardTypeOptions[j].label
                  }

                }
              }
            }

          })
        })
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
    // 新增 / 修改
    addOrUpdateHandle(id) {
      this.addOrUpdateVisible = true
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id)
      })
    },
    // 删除
    deleteHandle(id) {
      this.$confirm('是否确认还原该条出入库明细所有数据', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function () {
        return delObj(id)
      }).then(data => {
        this.$message.success('还原成功')
        this.getDataList()
      })
    },
    deleteItemHandle(id) {
      this.$confirm('是否确认还原该条出入库明细数据', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function () {
        return delItemObj(id)
      }).then(data => {
        this.$message.success('还原成功')
        this.getDataList()
      })
    },
    alertDialog(item) {
      this.inoutlistForm = item
      this.dialogFormVisible = true;
    },
    selectClick() {
      delete this.inoutlistForm['inYardWay'];
      delete this.inoutlistForm['outYardWay'];
      putObjBody(this.inoutlistForm).then(data => {
        this.$message.success(data.data)
        this.dialogFormVisible = false;
        this.getDataList();
      })
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val;
      this.single = val.length != 1;
    },
    exportExcel() {

      exportFile(this.multipleSelection[0])
    },
    openFileDialog(row) {
      this.fileDialogVisible = true
      if (row.ioyardListBodyList.length > 0) {

        getInIoyard(row.ioyardListBodyList[0].ioyardListNo).then(resp => {
          var map = { tableName: 'inStrogeDetail', tableFid: resp.data.data }
          getFileInfo(map).then(resp => {
            this.sysFileList = resp.data.data
            if (this.sysFileList.length <= 0) {
              this.$message.error("没有附件")
              this.fileDialogVisible = false
            }
          })
        })
      } else {
        this.$message.error("没有附件")
        this.fileDialogVisible = false
      }


    },
    downloadFile: function (filename, bucketName) {
      handleDown(filename, bucketName)
    },
  }
}
</script>
<style >
.tab {
  margin: auto;
  width: 100%;
}
.tab tr {
  /* border: 1px solid black !important; */
}
.tab th {
  /* border: 1px solid black !important; */
  text-align: center;
}
.tab td {
  /* border: 1px solid black !important; */
  text-align: center;
}
</style>