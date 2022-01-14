<template>
	<el-dialog :title="!dataForm.id ? '业务审核' : '业务审核'" width="70%" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-row class="table-row">
				<el-col :span="12">
					<el-form-item label="出入库" label-width="140px" prop="inOut">
						<el-input v-model="dataForm.inOut==0 ? '出库' : '入库'" :disabled="true" placeholder="出入库"></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item label="单据类型" label-width="140px" prop="inoutYardKnd">
						<el-input v-model="dataForm.inoutYardKnd" :disabled="true" placeholder="单据类型"></el-input>
					</el-form-item>
				</el-col>
			</el-row>

			<el-row class="table-row">
				<el-col :span="12">
					<el-form-item label="业务类型" label-width="140px" prop="businessKnd">
						<el-input v-model="dataForm.businessKnd" :disabled="true" placeholder="业务类型"></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item label="单据名称" label-width="140px" prop="inoutYardNam">
						<el-input v-model="dataForm.inoutYardNam" :disabled="true" placeholder="单据名称"></el-input>
					</el-form-item>
				</el-col>
			</el-row>

			<el-row class="table-row">
				<el-col :span="12">
					<el-form-item label="单据编号" label-width="140px" prop="inoutYardNo">
						<el-input v-model="dataForm.inoutYardNo" :disabled="true" placeholder="单据编号"></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item label="入驻企业名称" label-width="140px" prop="settledCompany">
						<el-input v-model="dataForm.settledCompany" :disabled="true" placeholder="入驻企业名称"></el-input>
					</el-form-item>
				</el-col>
			</el-row>

			<el-row class="table-row">
				<el-col :span="12">
					<el-form-item label="进区报关单号" label-width="140px" prop="inCustomsDeclaration">
						<el-input v-model="dataForm.inCustomsDeclaration" :disabled="true" placeholder="进区报关单号"></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item label="进区核注清单号" label-width="140px" prop="inCheckNum">
						<el-input v-model="dataForm.inCheckNum" :disabled="true" placeholder="进区核注清单号"></el-input>
					</el-form-item>
				</el-col>
			</el-row>

			<el-row class="table-row">
				<el-col :span="12">
					<el-form-item label="电子账册号" label-width="140px" prop="electronicAccountNum">
						<el-input v-model="dataForm.electronicAccountNum" :disabled="true" placeholder="电子账册号"></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item label="入区类别" label-width="140px" prop="inCategory">
						<el-input v-model="dataForm.inCategory" :disabled="true" placeholder="入区类别"></el-input>
					</el-form-item>
				</el-col>
			</el-row>

			<el-row class="table-row">
				<el-col :span="12">
					<el-form-item label="报关代理企业" label-width="140px" prop="customsBroker">
						<el-input v-model="dataForm.customsBroker" :disabled="true" placeholder="报关代理企业"></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item label="货主代码" label-width="140px" prop="consignCod">
						<el-input v-model="dataForm.consignCod" :disabled="true" placeholder="货主代码"></el-input>
					</el-form-item>
				</el-col>
			</el-row>

			<el-row class="table-row">
				<el-col :span="12">
					<el-form-item label="作业委托人" label-width="140px" prop="consignNam">
						<el-input v-model="dataForm.consignNam" :disabled="true" placeholder="作业委托人"></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item label="添加人" label-width="140px" prop="operNam">
						<el-input v-model="dataForm.operNam" :disabled="true" placeholder="添加人"></el-input>
					</el-form-item>
				</el-col>
			</el-row>

			<el-row class="table-row">
				<el-col :span="12">
					<el-form-item label="添加时间" label-width="140px" prop="recordTim">
						<el-input v-model="dataForm.recordTim" :disabled="true" placeholder="添加时间"></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item label="修改人" label-width="140px" prop="updateNam">
						<el-input v-model="dataForm.updateNam" :disabled="true" placeholder="修改人"></el-input>
					</el-form-item>
				</el-col>
			</el-row>

			<el-row class="table-row">
				<el-col :span="12">
					<el-form-item label="修改时间" label-width="140px" prop="updateTim">
						<el-input v-model="dataForm.updateTim" :disabled="true" placeholder="修改时间"></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item label="报关代理企业代码" label-width="140px" prop="customsBrokerCod">
						<el-input v-model="dataForm.customsBrokerCod" :disabled="true" placeholder="报关代理企业代码"></el-input>
					</el-form-item>
				</el-col>
			</el-row>

			<el-row class="table-row">
				<el-col :span="24">
					<el-form-item label="入驻企业字母代码" label-width="140px" prop="settledCod">
						<el-input v-model="dataForm.settledCod" :disabled="true" placeholder="入驻企业字母代码"></el-input>
					</el-form-item>
				</el-col>
			</el-row>

			<el-form-item label="附件" prop="urlFile" v-loading="fileLoading" element-loading-text="文件上传中" element-loading-spinner="el-icon-loading">

				<ul id="example-1">
					<li v-for="(item, index) in fileOptions" :key="index">
						{{ item.originalFilename }}
						<i class="el-icon-download" style="
                float: right;
                left: 40%;
                height: 32px;
                line-height: 32px;
                position: absolute;
              " @click="downloadFile(index)"></i>
						<!--<i class="el-icon-delete" style="
                float: right;
                margin-right: 50%;
                height: 32px;
                line-height: 32px;
              " @click="deleteFile(index)"></i>-->
					</li>
				</ul>
			</el-form-item>

			<el-divider></el-divider>
			<el-card class="box-card" v-for="(item,index) in dataBodyForm" :key="index" style="border-color:red;border:1px;">
				<span>{{index+1}}</span>
				<el-row class="table-row">
					<el-col :span="12">
						<el-form-item label="合同编号" label-width="140px" prop="合同编号">
							<el-input v-model="item.contNo" :disabled="true" placeholder="合同编号"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="H.S（商品编码）" label-width="140px" prop="cargoNo">
							<el-input v-model="item.cargoNo" :disabled="true" placeholder="H.S（商品编码）"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row class="table-row">
					<el-col :span="12">
						<el-form-item label="品名" label-width="140px" prop="cargoNam">
							<el-input v-model="item.cargoNam" :disabled="true" placeholder="品名"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="规格" label-width="140px" prop="format">
							<el-input v-model="item.format" :disabled="true"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row class="table-row">
					<el-col :span="12">
						<el-form-item label="件数" label-width="140px" prop="pieceNum">
							<el-input v-model="item.pieceNum" :disabled="true" placeholder="件数"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="桶数" label-width="140px" prop="bucketNum">
							<el-input v-model="item.bucketNum" :disabled="true" placeholder="桶数"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row class="table-row">
					<el-col :span="12">
						<el-form-item label="毛重" label-width="140px" prop="roughWet">
							<el-input v-model="item.roughWet" :disabled="true" placeholder="毛重"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="净重" label-width="140px" prop="netWet">
							<el-input v-model="item.netWet" :disabled="true" placeholder="净重"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row class="table-row">
					<el-col :span="12">
						<el-form-item label="单价" label-width="140px" prop="unitPrice">
							<el-input v-model="item.unitPrice" :disabled="true" placeholder="单价"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="单价类型" label-width="140px" prop="unitPriceKnd">
							<el-input v-model="item.unitPriceKnd" :disabled="true" placeholder="单价类型"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row class="table-row">
					<el-col :span="12">
						<el-form-item label="总价" label-width="140px" prop="totalPrice">
							<el-input v-model="item.totalPrice" :disabled="true" placeholder="总价"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="单位" label-width="140px" prop="unit">
							<el-input v-model="item.unit" :disabled="true" placeholder="单位"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row class="table-row">
					<el-col :span="12">
						<el-form-item label="运输工具" label-width="140px" prop="transportMeans">
							<el-input v-model="item.transportMeans" :disabled="true" placeholder="运输工具"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="进口国别" label-width="140px" prop="importCountry">
							<el-input v-model="item.importCountry" :disabled="true" placeholder="进口国别"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row class="table-row">
					<el-col :span="12">
						<el-form-item label="目的国" label-width="140px" prop="distinationCountry">
							<el-input v-model="item.distinationCountry" :disabled="true" placeholder="目的国"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="自重" label-width="140px" prop="deadLoad">
							<el-input v-model="item.deadLoad" :disabled="true" placeholder="自重"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row class="table-row">
					<el-col :span="12">
						<el-form-item label="磅房编号" label-width="140px" prop="checkNo">
							<el-input v-model="item.checkNo" :disabled="true" placeholder="磅房编号"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="货类代码" label-width="140px" prop="cargoKindNo">
							<el-input v-model="item.cargoKindNo" :disabled="true" placeholder="货类代码"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row class="table-row">
					<el-col :span="24">
						<el-form-item label="货类名称" label-width="140px" prop="cargoKindNam">
							<el-input v-model="item.cargoKindNam" :disabled="true" placeholder="货类名称"></el-input>
						</el-form-item>
					</el-col>

				</el-row>
				<el-row class="table-row">
					<el-col :span="24">
						<el-form-item label="是否需要插入衡重申请单" label-width="140px" :formatter="changeBoundFlag" prop="boundFlag">
							<el-input v-model="item.boundFlag==0 ? '否' : '是'" :disabled="true" placeholder="是否需要插入衡重申请单"></el-input>
						</el-form-item>
					</el-col>

				</el-row>
				<el-row class="table-row">
					<el-col :span="12">
						<el-form-item label="进出口" label-width="140px" prop="inoutId">
							<el-input v-model="item.inoutId" :disabled="true" placeholder="进出口"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="内外贸" label-width="140px" prop="tradeId">
							<el-input v-model="item.tradeId" :disabled="true" placeholder="内外贸"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row class="table-row">
					<el-col :span="12">
						<el-form-item label="提单号" label-width="140px" prop="billNo">
							<el-input v-model="item.billNo" :disabled="true" placeholder="提单号"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="上下限额" label-width="140px" prop="addMinRat">
							<el-input v-model="item.addMinRat" :disabled="true" placeholder="上下限额"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row class="table-row">
					<el-col :span="12">
						<el-form-item label="单位代码" label-width="140px" prop="unitCod">
							<el-input v-model="item.unitCod" :disabled="true" placeholder="单位代码"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="单位名称" label-width="140px" prop="unitNam">
							<el-input v-model="item.unitNam" :disabled="true" placeholder="单位名称"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row class="table-row">
					<el-col :span="12">
						<el-form-item label="科室队代码" label-width="140px" prop="subunitCod">
							<el-input v-model="item.subunitCod" :disabled="true" placeholder="科室队代码"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="科室队名称" label-width="140px" prop="subunitNam">
							<el-input v-model="item.subunitNam" :disabled="true" placeholder="科室队名称"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row class="table-row">
					<el-col :span="12">
						<el-form-item label="结算方式代码" label-width="140px" prop="feeWayId">
							<el-input v-model="item.feeWayId" :disabled="true" placeholder="结算方式代码"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item label="结算方式" label-width="140px" prop="feeWayNam">
							<el-input v-model="item.feeWayNam" :disabled="true" placeholder="结算方式"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-divider></el-divider>
			</el-card>

		</el-form>

		<span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="aduitSubmit(1)" v-if="canSubmit">审核通过</el-button>
      <el-button type="danger" @click="aduitSubmit(2)" v-if="canSubmit">审核不通过</el-button>
    </span>
	</el-dialog>
</template>

<script>
	import { getObj, addObj, putObj, getInoutYardNo, updateByIdForBusinessCheckStatus, updateByIdForOperCheckStatus } from '@/api/baoshuisystem/inoutyardhead'
	import { fetchList } from '@/api/baoshuisystem/settledcompanyinfo'
	import { remote } from '@/api/admin/dict'
	import { fetchList as customsbrokerinfo } from '@/api/baoshuisystem/customsbrokerinfo'
	import { getClientInfoAll } from '@/api/baoshuisystem/clientinfo'
	import store from '@/store'
	import { fileSave, getFileInfo, delFile, handleDown } from '@/api/baoshuisystem/upload-file'
	import { fetchList as fetchListBody, delObj } from '@/api/baoshuisystem/inoutyardbody'
	export default {
		data() {
			return {
				visible: false,
				canSubmit: false,
				dataForm: {
					fId: 0,
					inOut: '',
					inoutYardKnd: '保税仓库货物入库',
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
					operCheckStatus: 0
				},
				dataHfidForm: {},
				dataBodyForm: [],
				settledCompanyOptions: [],
				inCategoryOptions: [],
				customsBrokerOptions: [],
				consignNamOptions: [],
				businessKndOptions: [{
					label: "一日游业务",
					value: "一日游业务"
				}, {
					label: "仓储业务",
					value: "仓储业务"
				}, ],
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
					//						message: '作业委托人不能为空',
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
			changeBoundFlag(row, column) {
				console.log(row)
				const manProp = row[column.property];
				var string = null;
				if(manProp == 0) {
					string = "副";
				} else {
					string = "主";
				}
				return string;
			},
			init(id) {
				this.fileList = []
				this.fileOptions = []
				this.getCompanyList();
				this.getRemote();
				this.dataForm.fId = id || 0;
				this.dataHfidForm.hFid = id || 0;
				this.getFileInfoUpdate()
				this.visible = true;

				this.$nextTick(() => {
					this.$refs['dataForm'].resetFields()
					if(this.dataForm.fId) {
						getObj(this.dataForm.fId).then(response => {
							this.dataForm = response.data.data
							console.log(this.dataForm)
							if(response.data.data.businessCheckStatus == "1") {
								this.canSubmit = false;
							} else {
								this.canSubmit = true;
							}
						})
						fetchListBody(this.dataHfidForm).then((res) => {
							//res.data.data.records
							console.log(res)
							this.dataBodyForm = res.data.data.records
						})
					}
				})
			},

			getCompanyList() {
				fetchList(Object.assign({

				})).then((res) => {
					this.settledCompanyOptions = res.data.data.records //入驻企业详情-》入驻企业名称
					console.log(res)
				})

				customsbrokerinfo(Object.assign({})).then((res) => {
					console.log(res)
					this.customsBrokerOptions = res.data.data.records //报关代理详情-》入驻企业名称
				})

				getClientInfoAll().then((res) => {
					console.log(res)
					this.consignNamOptions = res.data.data //货主信息
				})
			},

			getRemote() {
				remote("area_type").then((res) => {
					console.log(res)
					this.inCategoryOptions = res.data.data //入区类别
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
			selectFnSecond(e) {
				this.dataForm.customsBrokerCod = e.customsBrokerCod //报关代理企业代码
			},
			selectFnThrid(e) {
				console.log(e)
				this.dataForm.consignCod = e.companyCode //货主代码
			},

			uploadSuccess: function(e) {
				console.log(e)
				if(e.data == null) {
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

					if(this.dataForm.fId != 0) {
						var fileOne = [{
							bucketName: bucketName,
							fileName: fileName,
							url: url,
							original: originalFilename,
							fileSize: fileSize,
							tableName: 'contract',
							tableFid: this.dataForm.fId
						}]
						console.log(fileOne)
						fileSave(fileOne).then(response => {
							console.log(response)
							if(response.data.data != null) {
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
				if(this.dataForm.fId != 0) {
					delFile(id).then(response => {
						//alert(response)
						console.log(response)
					})
				}

			},
			getFileInfoUpdate() {
				console.log(this.dataForm)
				var map = {
					tableName: 'inout_yard_head',
					tableFid: this.dataForm.fId
				}
				getFileInfo(map).then(response => {
					console.log(response)
					this.fileList = response.data.data
					for(const key in this.fileList) {
						this.fileOptions.push({
							"originalFilename": this.fileList[key].original
						})
					}
				})
			},

			onProgress(event, file, fileList) {
				this.fileLoading = true
			},

			downloadFile: function(index) {
				var filename = this.fileList[index].fileName
				var bucket = this.fileList[index].bucketName
				handleDown(filename, bucket)
			},
			aduitSubmit(e) {
				console.log(e)
				if(e == 1) { //审核成功
					this.dataForm.businessCheckStatus = 1
					updateByIdForBusinessCheckStatus(this.dataForm).then((res) => {
						console.log(res)
						this.$notify.success("已审核成功")
						this.visible = false
						this.$emit('refreshDataList')
					})
				}

				if(e == 2) { //审核失败
					this.dataForm.businessCheckStatus = 2
					updateByIdForBusinessCheckStatus(this.dataForm).then((res) => {
						console.log(res)
						this.$notify.success("已审核失败")
						this.visible = false
						this.$emit('refreshDataList')
					})
				}

			},

		}
	}
</script>

<style>
	.el-main {
		margin: 0 auto;
		background-color: #E9EEF3;
		color: #333;
		text-align: center;
		line-height: 160px;
	}
</style>