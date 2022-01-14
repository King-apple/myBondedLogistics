<template>
	<el-dialog :title="!dataForm.id ? '操作审核' : '操作审核'" width="30%" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-row class="table-row">
				<el-col :span="24">
					<el-form-item label="出区核注清单号" label-width="140px" prop="outCheckNum">
						<el-input v-model="dataForm.outCheckNum" placeholder="出区核注清单号"></el-input>
					</el-form-item>
				</el-col>
			</el-row>
			
			<el-row class="table-row">
				<el-col :span="24">
					<el-form-item label="原进区核注清单号"  label-width="140px" prop="inCheckNum">
					<el-input v-model="dataForm.inCheckNum" placeholder="原进区核注清单号"></el-input>
				</el-form-item>
				</el-col>
			</el-row>
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
	import { fileSave, getFileInfo, delFile, handleDown } from '@/api/baoshuisystem/upload-file'
	export default {
		data() {
			return {
				visible: false,
				canSubmit: false,
				dataForm: {
					fId: 0,
					inOut: '',
					inoutYardKnd: '',
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
				dataRule: {
					businessKnd: [{
						required: true,
						message: '业务类型不能为空',
						trigger: 'blur'
					}],
					settledCompany: [{
						required: true,
						message: '入驻企业名称不能为空',
						trigger: 'blur'
					}],
				}
			}
		},
		methods: {
			init(id) {
				this.fileList = []
				this.fileOptions = []
				this.dataForm.fId = id || 0;
				this.dataHfidForm.hFid = id || 0;
				this.visible = true;

				this.$nextTick(() => {
					this.$refs['dataForm'].resetFields()
					if(this.dataForm.fId) {
						getObj(this.dataForm.fId).then(response => {
							this.dataForm = response.data.data
							console.log(this.dataForm)
							if(response.data.data.operCheckStatus == "1") {
								this.canSubmit = false;
							} else {
								this.canSubmit = true;
							}
						})
					}
				})
			},
			aduitSubmit(e) {
				console.log(e)
				if(e == 1) { //审核成功
					this.dataForm.operCheckStatus = 1
					updateByIdForOperCheckStatus(this.dataForm).then((res) => {
						console.log(res)
						this.$notify.success("已审核成功")
						this.visible = false
						this.$emit('refreshDataList')
					})
				}

				if(e == 2) { //审核失败
					this.dataForm.operCheckStatus = 2
					updateByIdForOperCheckStatus(this.dataForm).then((res) => {
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