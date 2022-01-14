<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    width="70%"
    :close-on-click-modal="false"
    :visible.sync="visible"
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="80px"
    >
      
      <el-row class="table-row">
      	 <el-col :span="12">
          <el-form-item
            label="合同编号"
            label-width="140px"
            prop="contNo"
          >
            <el-select
              clearable
              v-model="dataForm.contNo"
              style="width: 100%"
              placeholder="合同编号:"
              filterable
            >
              <el-option
                v-for="(item, index) in contractOptions"
                @click.native="selectContNo(item)"
                :key="index"
                :label="item.contNo"
                :value="item.contNo"
              >
              </el-option>
            </el-select>
            <!--<el-input v-model="dataForm.settledCompany" placeholder="入驻企业名称"></el-input>-->
          </el-form-item>
        </el-col>
      	
        <el-col :span="12">
          <el-form-item
            label="入驻企业名称"
            label-width="140px"
            prop="settledCompany"
          >
            <el-select
              clearable
              v-model="dataForm.settledCompany"
              style="width: 100%"
              placeholder="货主名:"
              filterable
            >
              <el-option
                v-for="(item, index) in settledCompanyOptions"
                @click.native="selectFn(item)"
                :key="index"
                :label="item.companyName"
                :value="item.companyName"
              >
              </el-option>
            </el-select>
            <!--<el-input v-model="dataForm.settledCompany" placeholder="入驻企业名称"></el-input>-->
          </el-form-item>
        </el-col>
      </el-row>
      <el-row class="table-row">
      	 <el-col :span="12">
          <el-form-item label="单据编号" label-width="140px" prop="inoutYardNo">
            <el-input
              v-model="dataForm.inoutYardNo"
              readonly="readonly"
              placeholder="单据编号"
            ></el-input>
          </el-form-item>
        </el-col>
      	
        <el-col :span="12">
          <el-form-item label="业务类型" label-width="140px" prop="businessKnd">
            <el-select
              clearable
              v-model="dataForm.businessKnd"
              style="width: 100%"
              placeholder="业务类型:"
              filterable
            >
              <el-option
                v-for="(item, index) in businessKndOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
            <!--<el-input v-model="dataForm.businessKnd" placeholder="H.S（商品编码）"></el-input>-->
          </el-form-item>
        </el-col>
        <!--<el-col :span="12">
          <el-form-item
            label="原进区核注清单号"
            label-width="140px"
            prop="inCheckNum"
          >
            <el-input
              v-model="dataForm.inCheckNum"
              placeholder="原进区核注清单号"
            ></el-input>
          </el-form-item>
        </el-col>-->
      </el-row>

      <!--<el-row class="table-row">
        <el-col :span="12">
          <el-form-item
            label="原进区报关单号"
            label-width="140px"
            prop="inCustomsDeclaration"
          >
            <el-input
              v-model="dataForm.inCustomsDeclaration"
              placeholder="进区报关单号"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="出区核注清单号"
            label-width="140px"
            prop="outCheckNum"
          >
            <el-input
              v-model="dataForm.outCheckNum"
              placeholder="出区核注清单号"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>-->

      <el-row class="table-row">
        <el-col :span="12">
          <el-form-item
            label="电子账册号"
            label-width="140px"
            prop="electronicAccountNum"
          >
            <el-input
              v-model="dataForm.electronicAccountNum"
              readonly="readonly"
              placeholder="电子账册号"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="出区类别" label-width="140px" prop="outCategory">
            <el-select
              clearable
              v-model="dataForm.outCategory"
              style="width: 100%"
              placeholder="入区类别:"
              filterable
            >
              <el-option
                v-for="(item, index) in outCategoryOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
            <!--<el-input v-model="dataForm.inCategory" placeholder="入区类别"></el-input>-->
          </el-form-item>
        </el-col>
      </el-row>

      <el-row class="table-row">
        <el-col :span="12">
          <el-form-item
            label="报关代理企业"
            label-width="140px"
            prop="customsBroker"
          >
            <!--<el-input v-model="dataForm.customsBroker" placeholder="报关代理企业"></el-input>-->
            <el-select
              clearable
              v-model="dataForm.customsBroker"
              style="width: 100%"
              placeholder="报关代理企业:"
              filterable
            >
              <el-option
                v-for="(item, index) in customsBrokerOptions"
                @click.native="selectFnSecond(item)"
                :key="index"
                :label="item.customsBrokerName"
                :value="item.customsBrokerName"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="作业委托人" label-width="140px" prop="consignNam">
            <el-select
              clearable
              v-model="dataForm.consignNam"
              style="width: 100%"
              placeholder="作业委托人:"
              filterable
            >
              <el-option
                v-for="(item, index) in consignNamOptions"
                @click.native="selectFnThrid(item)"
                :key="index"
                :label="item.company"
                :value="item.company"
              >
              </el-option>
            </el-select>
            <!--<el-input v-model="dataForm.consignNam" placeholder="货主单位"></el-input>-->
          </el-form-item>
        </el-col>
      </el-row>

      <el-row class="table-row">
        <el-col :span="12">
          <el-form-item
            label="单价类型"
            label-width="140px"
            prop="unitPriceKnd"
          >
            <el-select
              clearable
              v-model="dataForm.unitPriceKnd"
              style="width: 100%"
              placeholder="单价类型 "
              filterable
            >
              <el-option
                v-for="(item, index) in unitPriceKndOptions"
                :key="index"
                :label="item.label"
                :value="item.label"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="总价单位" label-width="140px" prop="unit">
            <el-select
              clearable
              v-model="dataForm.unit"
              style="width: 100%"
              placeholder="总价单位 "
              filterable
            >
              <el-option
                v-for="(item, index) in unitOptions"
                :key="index"
                :label="item.label"
                :value="item.label"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row class="table-row">
        <el-col :span="12">
          <el-form-item label="净重单位" label-width="140px" prop="netWetUnit">
            <el-select
              clearable
              v-model="dataForm.netWetUnit"
              style="width: 100%"
              placeholder="净重单位 "
              filterable
            >
              <el-option
                v-for="(item, index) in netWetUnitOptions"
                :key="index"
                :label="item.label"
                :value="item.label"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
         <el-col :span="12">
          <el-form-item
            label="净重总和"
            label-width="140px"
            prop="netWetSum"
          >
            <el-input
              v-model="dataForm.netWetSum"
              placeholder="净重总和"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      
      <el-row>
      	<el-col :span="12">
      		<el-form-item
            label="毛重总和"
            label-width="140px"
            prop="roughWetSum"
          >
            <el-input
              v-model="dataForm.roughWetSum"
              placeholder="毛重总和"
            ></el-input>
          </el-form-item>
      	</el-col>
      	<el-col :span="12">
      		<el-form-item
            label="件数总和"
            label-width="140px"
            prop="pieceNumSum"
          >
            <el-input
              v-model="dataForm.pieceNumSum"
              placeholder="件数总和"
            ></el-input>
          </el-form-item>
      	</el-col>
      </el-row>
      <el-row>
      	<el-col :span="12">
      		<el-form-item
            label="总价总和"
            label-width="140px"
            prop="totalPriceSum"
          >
            <el-input
              v-model="dataForm.totalPriceSum"
              placeholder="总价总和"
            ></el-input>
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
import { getObj, addObj, putObj, getInoutYardNo } from '@/api/baoshuisystem/inoutyardhead'
import { remote } from '@/api/admin/dict'
import { fetchList, getAll as getAllSettledcompanyinfo } from '@/api/baoshuisystem/settledcompanyinfo'
import { getAll as customsbrokerinfo } from '@/api/baoshuisystem/customsbrokerinfo'
import { getClientInfoAll } from '@/api/baoshuisystem/clientinfo'
import store from '@/store'
import { getEffectiveInfo} from '@/api/baoshuisystem/contract'
import { getBusinessOptions } from '@/api/baoshuisystem/getdictdata'
import { fileSave, getFileInfo, delFile, handleDown } from '@/api/baoshuisystem/upload-file'
export default {
  data() {
    return {
      visible: false,
      canSubmit: false,
      dataForm: {
        fId: 0,
        inOut: '0',
        inoutYardKnd: '保税物流中心出库',
        businessKnd: '',
        inoutYardNam: '',
        inoutYardNo: '',
        settledCompany: '',
        warehouseNam: '',
        inCustomsDeclaration: '',
        inCheckNum: '',
        electronicAccountNum: '',
        inCategory: '',
        customsBroker: '',
        consignCod: '',
        consignNam: '',
        outCheckNum: '',
        outCategory: '',
        operNam: '',
        recordTim: '',
        updateNam: '',
        updateTim: '',
        delNam: '',
        delTim: '',
        customsBrokerCod: '',
        settledCod: '',
        warehouseCod: '',
        businessCheckStatus: 0,
        operCheckStatus: 0,
        unitPriceKnd: '',
        unit: '',
        netWetUnit: '',
		contNo: '',
		roughWetSum: '',
		pieceNumSum: '',
		totalPriceSum: '',
		netWetSum: '',
      },
      settledCompanyOptions: [],
      outCategoryOptions: [],
      customsBrokerOptions: [],
      consignNamOptions: [],
      unitPriceKndOptions: [],
      unitOptions: [],
      netWetUnitOptions: [],
      contractOptions: [],
//    businessKndOptions: [{
//      label: "一日游业务",
//      value: "一日游业务"
//    }, {
//      label: "仓储业务",
//      value: "仓储业务"
//    },],
	  businessKndOptions:[],
      headers: {
        'Authorization': 'Bearer ' + store.getters.access_token,
      },
      fileLoading: false,
      fileOptions: [],
      fileList: [],
      dataRule: {
        //					inOut: [{
        //						required: true,
        //						message: '出入库不能为空',
        //						trigger: 'blur'
        //					}],
        //					inoutYardKnd: [{
        //						required: true,
        //						message: '单据类型不能为空',
        //						trigger: 'blur'
        //					}],
        contNo: [{
          required: true,
          message: '合同编号不能为空',
          trigger: 'blur'
        }],
        businessKnd: [{
          required: true,
          message: '业务类型不能为空',
          trigger: 'blur'
        }],
        //					inoutYardNam: [{
        //						required: true,
        //						message: '单据名称不能为空',
        //						trigger: 'blur'
        //					}],
        //					inoutYardNo: [{
        //						required: true,
        //						message: '单据编号不能为空',
        //						trigger: 'blur'
        //					}],
        settledCompany: [{
          required: true,
          message: '入驻企业名称不能为空',
          trigger: 'blur'
        }],
        //					warehouseNam: [{
        //						required: true,
        //						message: '仓库名称不能为空',
        //						trigger: 'blur'
        //					}],
        //					inCustomsDeclaration: [{
        //						required: true,
        //						message: '进区报关单号不能为空',
        //						trigger: 'blur'
        //					}],
        //					inCheckNum: [{
        //						required: true,
        //						message: '进区核注清单号不能为空',
        //						trigger: 'blur'
        //					}],
        //					electronicAccountNum: [{
        //						required: true,
        //						message: '电子账册号不能为空',
        //						trigger: 'blur'
        //					}],
        //					inCategory: [{
        //						required: true,
        //						message: '入区类别不能为空',
        //						trigger: 'blur'
        //					}],
        //					customsBroker: [{
        //						required: true,
        //						message: '报关代理企业不能为空',
        //						trigger: 'blur'
        //					}],
        //					consignCod: [{
        //						required: true,
        //						message: '货主代码不能为空',
        //						trigger: 'blur'
        //					}],
        //					consignNam: [{
        //						required: true,
        //						message: '货主单位不能为空',
        //						trigger: 'blur'
        //					}],
        //					outCheckNum: [{
        //						required: true,
        //						message: '出区核注清单号不能为空',
        //						trigger: 'blur'
        //					}],
        //					outCategory: [{
        //						required: true,
        //						message: '出区类别不能为空',
        //						trigger: 'blur'
        //					}],
        //					operNam: [{
        //						required: true,
        //						message: '添加人不能为空',
        //						trigger: 'blur'
        //					}],
        //					recordTim: [{
        //						required: true,
        //						message: '添加时间不能为空',
        //						trigger: 'blur'
        //					}],
        //					updateNam: [{
        //						required: true,
        //						message: '修改人不能为空',
        //						trigger: 'blur'
        //					}],
        //					updateTim: [{
        //						required: true,
        //						message: '修改时间不能为空',
        //						trigger: 'blur'
        //					}],
        //					delNam: [{
        //						required: true,
        //						message: '删除人不能为空',
        //						trigger: 'blur'
        //					}],
        //					delTim: [{
        //						required: true,
        //						message: '删除时间不能为空',
        //						trigger: 'blur'
        //					}],
        //					customsBrokerCod: [{
        //						required: true,
        //						message: '报关代理企业代码不能为空',
        //						trigger: 'blur'
        //					}],
        //					settledCod: [{
        //						required: true,
        //						message: '入驻企业字母代码不能为空',
        //						trigger: 'blur'
        //					}],
        //					warehouseCod: [{
        //						required: true,
        //						message: '仓库代码不能为空',
        //						trigger: 'blur'
        //					}]
      }
    }
  },
  methods: {
    init(id) {
      this.fileList = []
      this.fileOptions = []
      this.contract(); //获取合同
      this.getCompanyList();
      this.getRemote();
      this.dataForm.fId = id || 0;
      this.getFileInfoUpdate()
      this.visible = true;
      this.canSubmit = true;
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.fId) {
          getObj(this.dataForm.fId).then(response => {
            this.dataForm = response.data.data
          })
        }
      })
    },

    getCompanyList() {
      getAllSettledcompanyinfo(Object.assign({

      })).then((res) => {
        this.settledCompanyOptions = res.data.data //入驻企业详情-》入驻企业名称
        console.log(res)
      })

      customsbrokerinfo(Object.assign({})).then((res) => {
        console.log(res)
        this.customsBrokerOptions = res.data.data //报关代理详情-》入驻企业名称
      })

      getClientInfoAll().then((res) => {
        console.log(res)
        this.consignNamOptions = res.data.data //货主信息
      })
    },

    getRemote() {
      remote("area_type").then((res) => {
        console.log(res)
        this.outCategoryOptions = res.data.data //入区类别
      })

      remote("unitprice_type").then((res) => {
        console.log(res)
        this.unitPriceKndOptions = res.data.data //单价类型
      })

      remote("price_unit").then((res) => {
        console.log(res)
        this.unitOptions = res.data.data //价格单位
      })

      remote("net_wet_unit").then((res) => {
        console.log(res)
        this.netWetUnitOptions = res.data.data //净重单位
      })
      
      getBusinessOptions().then(res => {
          this.businessKndOptions = res.data.data
      })
    },
    selectFn(e) {
      console.log(e);
      console.log(e.companyCode);
      this.dataForm.electronicAccountNum = e.electronicAccountNum //电子注册账号
      this.dataForm.settledCod = e.companyCode //入驻企业代码

      getInoutYardNo(e.companyCode).then((res) => {
        this.dataForm.inoutYardNo = res.data.data //单据编号
      })
    },
    contract() {
      // contractList(Object.assign({
      //   current: this.pageIndex,
      //   size: this.pageSize
      // })).then((res) => {
      //   console.log(res)
      //   this.contractOptions = res.data.data.records
      // })
      getEffectiveInfo().then((res) => {
        this.contractOptions = res.data.data;
      });
    },
     selectContNo(e) {
      console.log(e)
      this.dataForm.cFid = e.fId
      this.dataForm.contNo = e.contNo
    },
    selectFnSecond(e) {
      console.log(e)
      this.dataForm.customsBrokerCod = e.customsBrokerCode //报关代理企业代码
    },
    selectFnThrid(e) {
      console.log(e)
      this.dataForm.consignCod = e.companyCode //货主代码
    },

    uploadSuccess: function (e) {
      console.log(e)
      if (e.data == null) {
        this.$notify.success("文件上传失败")
      } else {
        this.fileOptions.push({
          "originalFilename": e.data.originalFilename
        })
        //this.$notify.success(e.msg)
        var bucketName = e.data.bucketName
        var fileName = e.data.fileName
        var url = e.data.url
        var originalFilename = e.data.originalFilename
        var fileSize = e.data.fileSize
        this.fileList.push({
          bucketName: bucketName,
          fileName: fileName,
          url: url,
          original: originalFilename,
          fileSize: fileSize
        })

        if (this.dataForm.fId != 0) {
          var fileOne = [{
            bucketName: bucketName,
            fileName: fileName,
            url: url,
            original: originalFilename,
            fileSize: fileSize,
            tableName: 'inout_yard_head',
            tableFid: this.dataForm.fId
          }]
          console.log(fileOne)
          fileSave(fileOne).then(response => {
            console.log(response)
            if (response.data.data != null) {
              this.fileList[this.fileList.length - 1].id = response.data.data
            }
          })
        }

        this.fileLoading = false
      }

    },
    deleteFile(index) {

      var id = this.fileList[index].id
      this.fileList.splice(index, 1);
      this.fileOptions.splice(index, 1);
      if (this.dataForm.fId != 0) {
        delFile(id).then(response => {
          //alert(response)
          console.log(response)
        })
      }

    },
    getFileInfoUpdate() {
      var map = {
        tableName: 'inout_yard_head',
        tableFid: this.dataForm.fId
      }
      getFileInfo(map).then(response => {
        console.log(response)
        this.fileList = response.data.data
        for (const key in this.fileList) {
          this.fileOptions.push({
            "originalFilename": this.fileList[key].original
          })
        }
      })
    },

    onProgress(event, file, fileList) {
      this.fileLoading = true
    },

    downloadFile: function (index) {
      var filename = this.fileList[index].fileName
      var bucket = this.fileList[index].bucketName
      handleDown(filename, bucket)
    },

    // 表单提交
    dataFormSubmit() {
      console.log(this.dataForm)
      this.dataForm.inCheckNumFlg = '0'
      this.dataForm.inOutFlg = '1' //走审核判断进去核注清单号和出去核注清单号
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.canSubmit = false;
          if (this.dataForm.fId) {
            putObj(this.dataForm).then(data => {
              this.$notify.success('修改成功')
              this.visible = false
              this.$emit('refreshDataList')
            }).catch(() => {
              this.canSubmit = true;
            });
          } else {
            addObj(this.dataForm).then(data => {
              this.$notify.success('添加成功')
              this.visible = false
              this.$emit('refreshDataList')
              console.log(data)
              console.log(this.fileList)
              for (var i in this.fileList) {
                this.fileList[i].tableName = 'inout_yard_head'
                this.fileList[i].tableFid = data.data.data.fId
              }
              fileSave(this.fileList).then(response => {
                console.log(response)
                if (response.data.data != null) {
                  this.fileList[this.fileList.length - 1].id = response.data.data
                }
              })

            }).catch(() => {
              this.canSubmit = true;
            });
          }
        }
      })
    },
  }
}
</script>

<style>
.el-main {
  margin: 0 auto;
  background-color: #e9eef3;
  color: #333;
  text-align: center;
  line-height: 160px;
}
</style>