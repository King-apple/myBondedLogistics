<template>
  <el-dialog
    :title="!dataForm.fId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible"
    width="60%"
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="80px"
    >
      <el-row>
        <el-col :span="8">
          <el-form-item label="单据类型" prop="billType">
            <el-select
              v-model="dataForm.billType"
              placeholder="请选择单据类型:"
              filterable
              :disabled="typeFlag"
              style="width: 100%"
            >
              <el-option
                v-for="item in statementList"
                :key="item.id"
                :label="item.remarks"
                :value="item.remarks"
                @click.native="getItem(item)"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="单据编号" prop="billCod">
            <el-select
              :disabled="flag"
              v-model="dataForm.billCod"
              placeholder="请选择单据类型:"
              filterable
              style="width: 100%"
              v-if="codFlag == 1"
            >
              <el-option
                v-for="item in billCodList"
                :key="item.fId"
                :label="item.billCod"
                :value="item.billCod"
                @click.native="getBillCod(item)"
              >
              </el-option>
            </el-select>
            <el-input
              v-model="dataForm.billCod"
              placeholder="请选择单据类型:"
              filterable
              style="width: 100%"
              v-if="codFlag == 2"
            >
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="日期" prop="billDat">
            <el-date-picker
              v-model="dataForm.billDat"
              type="date"
              placeholder="日期"
              value-format="yyyy-MM-dd"
              style="width: 100%"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="合同编号" prop="contNo">
              <el-select
              :disabled="contNoFlag"
              clearable
              v-model="dataForm.contNo"
              placeholder="合同编号:"
              filterable
            >
              <el-option
                v-for="item in settledContractOptions"
                @click.native="selectFn(item)"
                :key="item.fId"
                :label="item.contNo"
                :value="item.contNo"
              >
              </el-option>
            </el-select>


          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="承办单位" prop="firstNam">
            <el-select
              v-model="dataForm.firstNam"
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
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="部门" prop="dept">
            <el-input
              v-model="dataForm.dept"
              placeholder="部门"
              style="width: 100%"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <!-- <el-col :span="8">
          <el-form-item label="单位代码" prop="firstCod">
            <el-input
              v-model="dataForm.firstCod"
              style="width: 100%"
              placeholder="单位代码"
            ></el-input>
          </el-form-item>
        </el-col> -->
      </el-row>
      <el-row>
        <!-- <el-col :span="8">
          <el-form-item label="客户代码" prop="secondCod">
            <el-input
              v-model="dataForm.secondCod"
              style="width: 100%"
              placeholder="客户代码"
            ></el-input>
          </el-form-item>
        </el-col> -->
        <el-col :span="8">
          <el-form-item label="客户名称" prop="secondNam">
             <el-select
              v-model="dataForm.secondNam"
              placeholder="客户名称"
              filterable
              clearable
              @change="secondNamChange"
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
        </el-col>
        <el-col :span="8">
          <el-form-item label="业务种类" prop="businessType">
            <el-select
              clearable
              v-model="dataForm.businessType"
              placeholder="业务种类:"
              filterable
              style="width: 100%"
            >
              <el-option
                v-for="item in bussinessList"
                :key="item.id"
                :label="item.remarks"
                :value="item.remarks"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="金额" prop="money">
            <el-input
              v-model="dataForm.money"
              style="width: 100%"
              placeholder="金额(元)"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row> </el-row>
      <el-row>
        <el-col :span="8">
           <el-form-item label="货物名称" prop="cargoNam">
            <el-select
              v-model="dataForm.cargoNam"
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
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="结算账户" prop="accountNam">
            <el-select
              v-model="dataForm.accountNam"
              placeholder="结算账户名称"
              filterable
              clearable
              style="width: 100%"
            >
              <el-option
                v-for="item in options"
                :key="item.organizer"
                :label="item.organizer"
                :value="item.organizer"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="计费数量" prop="settleWgt">
            <el-input
              v-model="dataForm.settleWgt"
              style="width: 100%"
              placeholder="计费数量"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="垫款金额" prop="advinceMoney">
            <el-input
              v-model="dataForm.advinceMoney"
              style="width: 100%"
              placeholder="垫款金额(元)"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="入境船名" prop="inShipName" style="width: 280px">
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
          <el-form-item label="备注" prop="markTxt">
            <el-input
              v-model="dataForm.markTxt"
              style="width: 100%"
              placeholder="备注"
            ></el-input>
          </el-form-item>
        </el-col>
         <el-col :span="8">
          <el-form-item label="货类名称" prop="markTxt">
              <el-select
              v-model="dataForm.cargoKindNam"
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
          </el-form-item>
        </el-col>
      </el-row>
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
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" v-if="canSubmit"
        >确定</el-button
      >
    </span>
  </el-dialog>
</template>

<script>
import { all as carGoKindAll } from '@/api/baoshuisystem/goodscategory'
import {
  getObj,
  addObj,
  putObj,
  getAllBills,
  getStatement,
  getBussiness,
} from "@/api/baoshuisystem/billapprove";
import store from "@/store";
import {
  fileSave,
  getFileInfo,
  delFile,
  handleDown,
  getEffectiveInfo,
  fileUpdate,
  getInsertValue,

  getUserInfo,
} from "@/api/baoshuisystem/contract";
import { getObj as getObjDept } from "@/api/admin/dept";
export default {
  data() {
    return {
      options: [],
      headers: {
        Authorization: "Bearer " + store.getters.access_token,
      },
      flag: true,
      statementList: [],
      billCodList: [],
      settledContractOptions: [],
      fileOptions: [],
      cargoNamOptions: [],
      secondNamOptions: [],
      visible: false,
      canSubmit: false,
      fileLoading: false,
      bussinessList: [],
      carGoKindOptions: [],
      contNoFlag: false,
      typeFlag: false,
      codFlag: 1,
      options: [],
      dataForm: {
        fId: 0,
        fFid: "",
        billCod: "",
        billType: "",
        billDat: "",
        firstNam: "",
        firstCod: "",
        dept: "",
        isCancel: 0,
        operator: "",
        secondCod: "",
        secondNam: "",
        contNo: "",
        shipNam: "",
        businessType: "",
        cargoCod: "",
        cargoNam: "",
        accountNam: "",
        settleWgt: "",
        money: "",
        advinceMoney: "",
        markTxt: "",
        operNam: "",
        recordTim: "",
        updateNam: "",
        updateTim: "",
        delNam: "",
        delTim: "",
        times: "",
        cargoKindCod: "",
        cargoKindNam: "",
      },
      dataRule: {

        billType: [
          { required: true, message: "单据类型不能为空", trigger: "blur" },
        ],
        billDat: [{ required: true, message: "日期不能为空", trigger: "blur" }],
        firstNam: [
          { required: true, message: "承办单位不能为空", trigger: "blur" },
        ],
        dept: [{ required: true, message: "部门不能为空", trigger: "blur" }],
        secondNam: [
          { required: true, message: "合同客户名称不能为空", trigger: "blur" },
        ],
        contNo: [
          { required: true, message: "合同编号不能为空", trigger: "blur" },
        ],
        shipNam: [{ required: true, message: "船名不能为空", trigger: "blur" }],
        businessType: [
          { required: true, message: "业务种类不能为空", trigger: "blur" },
        ],

        accountNam: [
          { required: true, message: "结算账户名称不能为空", trigger: "blur" },
        ],
        settleWgt: [
          { required: true, message: "计费数量不能为空", trigger: "blur" },
        ],
        money: [
          { required: true, message: "金额（元）不能为空", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
     getSelectContractList() {
      getEffectiveInfo().then((res) => {
        this.settledContractOptions = res.data.data;
      });
    },
    getBillCod(item) {
      if (item.billKnd == "付款单" || item.billKnd == "业务结算单") {
        //承办单位
        this.dataForm.fFid = item.fId;
        this.dataForm.firstNam = item.firstNam;
        //承办单位代码
        this.dataForm.firstCod = item.firstCod;
        //货物代码
        this.dataForm.cargoCod = item.cargoCod;
        // //承办单位（部门）
        // this.dataForm.dept = item.dept
        //合同客户名称
        this.dataForm.secondNam = item.secondNam;
        //合同客户代码
        this.dataForm.secondCod = item.secondCod;
        //合同编号
        this.dataForm.contNo = item.contCod;
        //船名
        this.dataForm.inShipName = item.inShipName;
        this.dataForm.outShipName = item.outShipName;
        //货物名称
        this.dataForm.cargoNam = item.cargoNam;
        this.dataForm.cargoKindCod = item.cargoKindCod;
        this.dataForm.cargoKindNam = item.cargoKindNam;
        //计费数量
        this.dataForm.settleWgt = item.feeQuantity;
        //金额
        //this.dataForm.money = item.money
        //垫款金额（元）
      } else {
        //承办单位
        this.dataForm.fFid = item.fId;
        this.dataForm.firstNam = item.firstNam;
        //承办单位代码
        this.dataForm.firstCod = item.firstCod;
        this.dataForm.cargoKindCod = item.cargoKindCod;
        this.dataForm.cargoKindNam = item.cargoKindNam;
        //货物代码
        this.dataForm.cargoCod = item.cargoCod;
        //合同客户名称
        this.dataForm.secondNam = item.secondNam;
        //合同客户代码
        this.dataForm.secondCod = item.secondCod;
        //合同编号
        this.dataForm.contNo = item.contNo;
        //船名

        this.dataForm.inShipName = item.inShipName;
        this.dataForm.outShipName = item.outShipName;
        //货物名称
        this.dataForm.cargoNam = item.cargoNam;
        //计费数量
        this.dataForm.settleWgt = item.settleWgt;
      }
    },
    //  selectOp(data) {
    //     this.dataForm.accountNam = data.organizerCode;
    //   },
    selectCGK(item) {
      this.dataForm.cargoKindCod = item.cargoKindCod
    },
    getItem(item) {
      if ("业务结算单" == item.remarks) {
        this.flag = false;
        this.codFlag = 1;
        this.$set(this.dataForm, "billCod", "");
        getAllBills().then((res) => {
          this.billCodList = res.data.data.angle;
        });
      }
      if ("付款单" == item.remarks) {
        this.flag = false;
        this.codFlag = 1;
        this.$set(this.dataForm, "billCod", "");
        getAllBills().then((res) => {
          this.billCodList = res.data.data.brixton;
        });
      }
      if ("出入库结算单" == item.remarks) {
        this.codFlag = 1;
        this.flag = false;
        this.$set(this.dataForm, "billCod", "");
        getAllBills().then((res) => {
          this.billCodList = res.data.data.camden;
        });
      }
      if ("收款通知单" == item.remarks) {
        this.$set(this.dataForm, "billCod", "");
        this.flag = true;
        this.codFlag = 2;
      }
    },
    async getAllBillsInfo() {
      await getAllBills().then((res) => {
        this.billCodList = res.data.data.dalston;
      });
    },
    getStatementInfo() {
      getStatement().then((res) => {
        this.statementList = res.data.data;
      });
      getBussiness().then((res) => {
        this.bussinessList = res.data.data;
      });
    },

    init(id) {
      this.getMyDept();
      this.getAllBillsInfo();
      this.getStatementInfo();
      this.dataForm.fId = id || 0;
      this.visible = true;
      this.canSubmit = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
        if (this.dataForm.fId) {
          this.typeFlag = true;
          this.flag = true;
          this.contNoFlag = true;
          this.getFileInfoUpdate();
          getObj(this.dataForm.fId).then((response) => {
            this.dataForm = response.data.data;
          });
        } else {
          this.typeFlag = false;
          this.contNoFlag = false;
        }
      });
      this.fileOptions = [];
      this.getSelectContractList();
    },
    secondNamChange: function (value) {
      var obj = this.secondNamOptions.find((item) => {//这里的userList就是上面遍历的数据源
        return item.company === value;//筛选出匹配数据
      });
      this.dataForm.secondCod = obj.companyCode
    },
    getMyDept() {
      getUserInfo().then((res) => {
        this.dataForm.deptId = res.data.data.sysUser.deptSubId
        getObjDept(res.data.data.sysUser.deptSubId).then((res) => {
          this.dataForm.dept = res.data.data.name;
        });
      });
      getInsertValue().then((response) => {
        this.options = response.data.data.organizerInfos;
        this.cargoNamOptions = response.data.data.contractObjects
        this.secondNamOptions =  response.data.data.clientInfos
      });
      carGoKindAll().then(response => {
        this.carGoKindOptions = response.data.data
      })
    },
    selectCNO(data) {
      this.dataForm.cargoCod = data.cargoCod;
    },
    selectFn(item) {
      this.dataForm.firstNam = item.firstNam;
      this.dataForm.secondNam = item.secondNam;
      console.log(item.secondNam)
      this.dataForm.secondCod = item.secondCod;
      this.dataForm.cargoKindCod = item.cargoKindCod;
      this.dataForm.cargoKindNam = item.cargoKindNam;
      this.dataForm.cargoNam = item.cargoNam;
      this.dataForm.cargoCod = item.cargoCod;
    },
    // 表单提交
    dataFormSubmit() {
      this.$refs["dataForm"].validate((valid) => {
        if (this.dataForm.billType != "收款通知单") {
          if (
            this.dataForm.billCod == "" ||
            this.dataForm.billCod == undefined
          ) {
            this.$message.error("请选择编号");
            return;
          }
        }
        if ((this.dataForm.cargoNam == '' || this.dataForm.cargoNam == undefined) && (this.dataForm.cargoKindNam == '' || this.dataForm.cargoKindNam == undefined)) {
          this.$message.error('请选择货名或货类')
          return;
        }
        if (valid) {
          this.canSubmit = false;
          if (this.dataForm.fId) {
            putObj(this.dataForm)
              .then((data) => {
                //文件修改
                if (this.fileList != undefined && this.fileList.length != 0) {
                  fileUpdate(this.fileList);
                }
                this.$notify.success("修改成功");
                this.visible = false;
                this.$emit("refreshDataList");
              })
              .catch(() => {
                this.canSubmit = true;
              });
          } else {
            addObj(this.dataForm)
              .then((data) => {
                //文件添加
                for (var i in this.fileList) {
                  this.fileList[i].tableName = "billapprove";
                  this.fileList[i].tableFid = data.data.data.fId;
                }
                if (this.fileList != undefined && this.fileList.length != 0) {
                  fileSave(this.fileList);
                }
                this.$notify.success("添加成功");
                this.visible = false;
                this.$emit("refreshDataList");
              })
              .catch(() => {
                this.canSubmit = true;
              });
          }
        }
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
        if (this.fileList == undefined) {
          this.fileList = []
        }
        this.fileList.push({
          bucketName: bucketName,
          fileName: fileName,
          url: url,
          original: originalFilename,
          fileSize: fileSize,
        });
        if (this.dataForm.fId != 0) {
          var fileOne = [
            {
              bucketName: bucketName,
              fileName: fileName,
              url: url,
              original: originalFilename,
              fileSize: fileSize,
              tableName: "billapprove",
              tableFid: this.dataForm.fId,
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
      if (this.dataForm.fId != 0) {
        delFile(id).then((response) => { });
      }
    },
    getFileInfoUpdate: function () {
      var map = { tableName: 'billapprove', tableFid: this.dataForm.fId };
      getFileInfo(map).then((response) => {
        this.fileList = response.data.data;
        for (const key in this.fileList) {
          this.fileOptions.push({
            originalFilename: this.fileList[key].original,
          });
        }
      });
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
