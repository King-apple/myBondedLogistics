<template>
	<div class="mod-config">
		<basic-container>
			<el-form :inline="true" :model="dataForm" ref="dataForm" @keyup.enter.native="getDataList()">

				<el-form-item label="单据编号" label-width="69px" prop="inoutYardNo">
					<el-input v-model="dataForm.inoutYardNo" placeholder="单据编号" style="width:100px"></el-input>
				</el-form-item>

				<el-form-item label="作业委托人" label-width="100px" prop="consignNam">
					<el-input v-model="dataForm.consignNam" placeholder="作业委托人" style="width:100px"></el-input>
				</el-form-item>

				<el-form-item label="时间" prop="recordTim">
					<el-date-picker v-model="value1" type="datetimerange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
					</el-date-picker>
				</el-form-item>

				<!-- <el-form-item label="进区核注清单号" label-width="110px" prop="inCheckNum">
          <el-input
            v-model="dataForm.inCheckNum"
            placeholder="进区核注清单号"
            clearable
          ></el-input>
        </el-form-item>-->
				<el-form-item label="出区核注清单号" label-width="140px" prop="outCheckNum">
					<el-input v-model="dataForm.outCheckNum" placeholder="出区核注清单号" clearable></el-input>
				</el-form-item>
				<el-form-item label="报关单号维护状态" label-width="150px" prop="inCustomsDeclarationStatus">
					<el-select v-model="dataForm.inCustomsDeclarationStatus" placeholder="报关单号维护状态:" filterable style="width: 10vw">
						<el-option v-for="item in inCustomsDeclarationStatusList" :key="item.fId" :label="item.label" :value="item.value">
						</el-option>
					</el-select>
				</el-form-item>

				<el-form-item>
					<el-button type="primary" @click="getDataList">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="warning" @click="resetForm('dataForm')">重置</el-button>
				</el-form-item>

				<!--<el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">-->
				<br />
				<el-form-item>
					<el-button v-if="permissions.inoutyardheadWareOut_add" icon="el-icon-plus" type="primary" @click="addOrUpdateHandle()">新增</el-button>
				</el-form-item>
				<el-form-item>
					<el-button v-if="permissions.inoutyardheadWareOut_export" icon="el-icon-download" type="warning" :disabled="single" @click="downloadFile('保税仓库货物出库单', '模板')">导出</el-button>
				</el-form-item>
				<el-form-item>
					<el-button v-if="permissions.inoutyardhead_export" icon="el-icon-info" type="primary" :disabled="single" @click="expoprt('保税仓库货物出库单', '模板')">查看</el-button>
				</el-form-item>
				<el-form-item>
					<el-upload v-if="permissions.inoutyardheadWareOut_import" :headers="headers" :show-file-list="false" :on-success="uploadSuccess" class="avatar-uploader" action="/baoshuisystem/excelUploadDownload/uploadForInoutyardBodyAndHead" :on-progress="onProgress" :on-change="onChange" v-loading="fileLoading">
						<el-button icon="el-icon-upload2" size="small" type="success">导入</el-button>
					</el-upload>
				</el-form-item>
				<el-form-item>
					<el-button v-if="permissions.inoutyardheadWareOut_import" icon="el-icon-download" type="warning" @click="downloadFile2('保税仓库货物出库单', '')">下载导入模板</el-button>
				</el-form-item>
				<el-form-item>
					<el-button v-if="permissions.inoutyardheadWareOut_Busi" type="danger" size="small" :disabled="single" icon="el-icon-tickets" @click="addOrUpdateBusinessHandle('保税仓库货物出库单', '模板')">业务审核</el-button>
				</el-form-item>
				<!--<el-form-item>
					<el-button v-if="permissions.inoutyardheadWareOut_Oper" type="danger" size="small" :disabled="single" icon="el-icon-tickets" @click="addOrUpdateOpearHandle()">操作审核</el-button>
				</el-form-item>-->
			</el-form>

			<div class="avue-crud" style="width:100%;">
				<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="handleSelectionChange">
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
					<el-table-column prop="recordTim" header-align="center" align="center" label="操作时间" width="130" sortable>
					</el-table-column>
					<el-table-column prop="warehouseNam" header-align="center" align="center" label="仓库名称" width="150">
					</el-table-column>
					<el-table-column prop="inoutYardNo" header-align="center" align="center" label="单据编号" width="105" sortable>
					</el-table-column>
					<el-table-column prop="contNo" header-align="center" align="center" label="合同编号" width="160" sortable>
					</el-table-column>
					<el-table-column prop="inCustomsDeclaration" header-align="center" align="center" label="进区报关单号" width="105">
					</el-table-column>
					<el-table-column prop="inCheckNum" header-align="center" align="center" label="进区核注清单号" width="165">
					</el-table-column>
					<el-table-column prop="outCheckNum" header-align="center" align="center" label="出区核注清单号" width="165">
					</el-table-column>
					<el-table-column prop="electronicAccountNum" header-align="center" align="center" label="电子账册号" width="105">
					</el-table-column>
					<el-table-column prop="customsBroker" header-align="center" align="center" label="报关代理企业" width="135" sortable>
					</el-table-column>
					<el-table-column prop="consignNam" header-align="center" align="center" label="作业委托人" width="135" sortable>
					</el-table-column>

					<el-table-column prop="operNam" header-align="center" align="center" label="添加人" width="105" sortable>
					</el-table-column>

					

					<!--<el-table-column prop="operCheckStatus" 
						header-align="center" 
						align="center" 
						:formatter="typeFormatter2" 
						label="操作状态" width="95" 
						fixed="right"
						sortable>
						<template slot-scope="scope">
							<div slot="reference" class="name-wrapper">
								<el-tag :type="scope.row.operCheckStatus|getbusinessCheckStatus"
									close-transition>{{scope.row.operCheckStatus | getbusinessCheckStatusText}}</el-tag>
							</div>
						</template>
					</el-table-column>-->
					<el-table-column prop="businessCheckStatus" header-align="center" align="center" label="业务状态" width="95" fixed="right" sortable>
						<template slot-scope="scope">
							<div slot="reference" class="name-wrapper">
								<el-tag :type="scope.row.businessCheckStatus|getbusinessCheckStatus" close-transition>{{scope.row.businessCheckStatus | getbusinessCheckStatusText}}</el-tag>
							</div>
						</template>

					</el-table-column>
					<el-table-column header-align="center" min-width="170px" align="center" label="操作" fixed="right">
						<template slot-scope="scope">
							<el-button v-if="permissions.inoutyardhead_edit" type="text" size="small" icon="el-icon-edit" @click="addOrUpdateHandle(scope.row.fId)" v-show="scope.row.businessCheckStatus=='0' || scope.row.businessCheckStatus=='2'">修改</el-button>
							<el-button v-if="permissions.inoutyardhead_del" type="text" size="small" icon="el-icon-delete" @click="deleteHandle(scope.row.fId,scope.row)">删除</el-button>
							<el-button v-if="permissions.inoutyardbody_view" type="text" size="small" icon="el-icon-plus" @click="openyardBody(scope.row)">表体</el-button>
							<!--<el-button
                v-if="permissions.inoutyardhead_in"
                type="text"
                size="small"
                icon="el-icon-edit"
                @click="updateView(scope.row)"
                v-show="scope.row.operCheckStatus=='0' || scope.row.operCheckStatus=='2'"
                >核注清单号维护</el-button
              >-->
							<!--<el-button
                v-if="permissions.inoutyardheadWareOut_Busi"
                type="text"
                size="small"
                icon="el-icon-tickets"
                @click="addOrUpdateBusinessHandle(scope.row.fId)"
                >业务审核</el-button
              >
              <el-button
                v-if="permissions.inoutyardheadWareOut_Oper"
                type="text"
                size="small"
                icon="el-icon-tickets"
                @click="addOrUpdateOpearHandle(scope.row.fId)"
                >操作审核</el-button
              >-->
						</template>
					</el-table-column>
				</el-table>
			</div>

			<div class="avue-crud__pagination">
				<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex" :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" background layout="total, sizes, prev, pager, next, jumper">
				</el-pagination>
			</div>
			<!-- 弹窗, 新增 / 修改 -->
			<table-form v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></table-form>

			<!-- 弹窗, 新增 / 修改 -->
			<table-business-form v-if="addOrUpdateBusinessVisible" ref="addOrBusinessUpdate" @refreshDataList="getDataList"></table-business-form>

			<!-- 弹窗, 新增 / 修改 -->
			<table-opear-form v-if="addOrUpdateOpearVisible" ref="addOrOpearUpdate" @refreshDataList="getDataList"></table-opear-form>
		</basic-container>

		<el-dialog title="核注清单号维护" :visible.sync="dialogVisible" width="30%">
			<el-form :model="form">
				<el-form-item label="出区核注清单号" :label-width="formLabelWidth">
					<el-input v-model="form.outCheckNum" autocomplete="off" @input="change($event)"></el-input>
				</el-form-item>
				<el-form-item label="原进区核注清单号" :label-width="formLabelWidth">
					<el-input v-model="form.inCheckNum" autocomplete="off" @input="change($event)"></el-input>
				</el-form-item>
			</el-form>
			<el-button @click="dialogVisible = false">取 消</el-button>
			<el-button type="primary" @click="updateInCheckNum">确 定</el-button>
		</el-dialog>

		<el-dialog title="保税仓库入库单预览" :visible.sync="PrintdialogVisible" width="90%">
			<div class="table-html-wrap" v-html="tableHtml" style="overflow-x:auto;" id="printTest"></div>
			<div style="margin-top: 5%; margin-left: 80%">
				<el-button @click="PrintdialogVisible = false">取 消</el-button>
				<el-button type="primary" v-print="printObj">打 印</el-button>
			</div>
		</el-dialog>

		<el-dialog title="业务审核" :visible.sync="BUsinessVisible" width="90%">
			<div class="table-html-wrap" v-html="tableBUsinessHtml" style="overflow-x:auto;" id="printBusinessTest"></div>
			<div style="margin-top: 5%; margin-left: 80%">
				<el-button @click="BUsinessVisible = false">取 消</el-button>
				<el-button type="primary" @click="aduitSubmit(1)" v-if="canSubmit">审核通过</el-button>
				<el-button type="danger" @click="aduitSubmit(2)" v-if="canSubmit">审核不通过</el-button>
			</div>
		</el-dialog>
		
		
		<el-dialog title="导出" :visible.sync="dialogVisibleExport" width="20%">
			<span>选择要导出的模板</span>
			<el-form :model="secondForm">
				<el-form-item label="下载">
					<el-select v-model="secondForm.model" placeholder="请选择模板">
						<el-option label="模板一(海关批注)" value="模板一"></el-option>
						<el-option label="模板二" value="模板二"></el-option>
					</el-select>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
		    <el-button @click="dialogVisibleExport = false">取 消</el-button>
		    <el-button type="primary" @click="downloadFile3">确 定</el-button>
		  </span>
		</el-dialog>
		
		<el-dialog title="查看" :visible.sync="dialogVisibleExportSecond" width="20%">
			<span>选择要查看的模板</span>
			<el-form :model="secondForm">
				<el-form-item label="下载">
					<el-select v-model="secondForm.model" placeholder="请选择模板">
						<el-option label="模板一(海关批注)" value="模板一"></el-option>
						<el-option label="模板二" value="模板二"></el-option>
					</el-select>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
		    <el-button @click="dialogVisibleExportSecond = false">取 消</el-button>
		    <el-button type="primary" @click="downloadFile4">查 看</el-button>
		  </span>
		</el-dialog>
		
		
		
		
	</div>
</template>

<script>
	import { fetchList, getObj, putObj, delObj, updateByIdForBusinessCheckStatus } from '@/api/baoshuisystem/inoutyardhead'
	import TableForm from './inoutyardhead-form'
	import TableBusinessForm from './inoutyardheadAndBody-form'
	import TableOpearForm from './inoutyardOpear-form.vue'
	import { dateFormat } from "@/util/date";
	import { mapGetters } from 'vuex'
	import { exportExcelForInoutyardBodyAndHead, exportPrint } from '@/api/baoshuisystem/excelImportExport'
	import XLSX from 'xlsx'

	import store from '@/store'
	export default {
		data() {
			return {
				headers: {
					'Authorization': 'Bearer ' + store.getters.access_token,
				},
				dataForm: {},
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				addOrUpdateVisible: false,
				addOrUpdateBusinessVisible: false,
				addOrUpdateOpearVisible: false,
				dialogVisible: false,
				form: {},
				formLabelWidth: '126px',
				// 非单个禁用
				single: true,
				exportDetail: {},
				fileLoading: null,
				value1: [],
				tableHtml: "",
				PrintdialogVisible: false,
				BUsinessVisible: false,
				tableBUsinessHtml: "",
				canSubmit: false,
				inCustomsDeclarationStatusList: [{
					label: '已维护完成',
					value: 'success'
				}, {
					label: '未维护',
					value: 'fail'
				}],
				secondForm: {
					model: '模板一(海关批注)'
				},
				dialogVisibleExport:false,
				dialogVisibleExportSecond: false,
				printObj: {
	              id: "printTest",//打印区域 Dom ID
	              popTitle: '打印页面标题文字',
	              extraCss: 'https://www.google.com,https://www.google.com',
	              extraHead: '<meta http-equiv="Content-Language"content="zh-cn"/>,<style> #printTest { height: auto !important;width:100%;text-align: center;padding: 0.5rem;word-wrap: break-word;word-break: break-all; } <style>'  ,//  可以传进去  style tag 标签；注意要逗号分隔   解决特定区域不显示问题；
           }
			}
		},
		filters: {
			//tag类型
			getbusinessCheckStatus(bindStatus) {
				const bindColor = {
					"0": 'info',
					"1": 'success',
					"2": 'danger'

				};
				return bindColor[bindStatus]
			},
			//颜色名称
			getbusinessCheckStatusText(bindStatus) {
				console.log(bindStatus)
				const bindColor = {
					"0": '未审核',
					"1": '已审核',
					"2": '审核失败',
				};
				return bindColor[bindStatus]
			}

		},
		components: {
			TableForm,
			TableBusinessForm,
			TableOpearForm
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
				this.dataForm.startTime = null
				this.dataForm.endTime = null
				if(this.value1 != null && this.value1.length > 0) {
					this.dataForm.startTime = dateFormat(this.value1[0])
					this.dataForm.endTime = dateFormat(this.value1[1])
				}
				this.dataListLoading = true
				this.dataForm.inOut = "0" //入库单查询
				this.dataForm.inoutYardKnd = "保税仓库货物出库" //单据类型
				fetchList(Object.assign(this.dataForm, {
					current: this.pageIndex,
					size: this.pageSize
				})).then(response => {
					console.log(response.data.data.records)
					this.dataList = response.data.data.records
					this.totalPage = response.data.data.total
				})
				this.dataListLoading = false
			},
			// 每页数
			sizeChangeHandle(val) {
				this.pageSize = val
				this.pageIndex = 1
				this.getDataList()
			},
			resetForm() {
				this.dataForm = {};
				this.getDataList();
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
			// 新增 / 修改
			addOrUpdateBusinessHandle(filename, type) {
				//    this.addOrUpdateBusinessVisible = true
				//    this.$nextTick(() => {
				//      this.$refs.addOrBusinessUpdate.init(this.exportDetail.fId[0])
				//    })

				let params = {
					fId: this.exportDetail.fId[0],
					inoutYardNam: "保税仓库货物出库单",
					operNam: type
				}
				console.log(params)
				getObj(this.exportDetail.fId[0]).then(response => {
					if(response.data.data.businessCheckStatus == "1") {
						this.canSubmit = false;
					} else {
						this.canSubmit = true;
					}
				})
				exportPrint(params).then((response) => { // 处理返回的文件流
					console.log(response)
					if(response.status === 200) {
						this.BUsinessVisible = true;
						var data = new Uint8Array(response.data)
						var workbook = XLSX.read(data, {
							type: 'array'
						})
						var worksheet = workbook.Sheets[workbook.SheetNames[0]];
						console.log(worksheet)
						var a = worksheet['!ref']
						var cm = worksheet['!rows'].length
						a = "A1:N" + cm
						worksheet['!ref'] = a
						var innerHTML = XLSX.utils.sheet_to_html(worksheet);
						//						var newStr = innerHTML.slice(0, innerHTML.indexOf("v=\"货主或代理企业签章  负责人签字\"") - 1) + ' style="text-align: left;height:120px;" valign="top" ' + innerHTML.slice(-(innerHTML.length - innerHTML.indexOf("v=\"货主或代理企业签章  负责人签字\"")))
						//						newStr = newStr.slice(0, newStr.indexOf("v=\"保税仓库签章  负责人签字\"") - 1) + ' style="text-align: left;height:120px;" valign="top" ' + newStr.slice(-(newStr.length - newStr.indexOf("v=\"保税仓库签章  负责人签字\"")))
						//						newStr = newStr.slice(0, newStr.indexOf("v=\"海关批注\"") - 1) + ' style="text-align: left;height:120px;" valign="top" ' + newStr.slice(-(newStr.length - newStr.indexOf("v=\"海关批注\"")))
						this.tableBUsinessHtml = innerHTML;
					}
				})

				//      dealt(this.currentRow.fId)
				//expoprtExcel(this.currentRow.fId)

			},
			// 新增 / 修改
			addOrUpdateOpearHandle() {
				this.addOrUpdateOpearVisible = true
				this.$nextTick(() => {
					this.$refs.addOrOpearUpdate.init(this.exportDetail.fId[0])
				})
			},

			// 删除
			deleteHandle(id, item) {
				this.$confirm('是否确认删除单据编号为' + item.inoutYardNo, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(function() {
					return delObj(id)
				}).then(data => {
					this.$message.success('删除成功')
					this.getDataList()
				})
			},
			openyardBody(e) {
				this.$router.push({
					path: '/inoutyardbodyWarehouse/body',
					query: {
						id: e.fId,
						businessCheckStatus: e.businessCheckStatus,
						flg: 2,
						contNo: e.contNo,
						cFid: e.cFid
					}
				})
			},
			updateInCheckNum() {
				this.form.inCheckNumFlg = "1"
				putObj(this.form).then((res) => {
					console.log(res)

					if(res.data.code == 0) {
						this.getDataList();
						this.dialogVisible = false;
						this.$notify.success('成功更改')
					} else {
						this.$notify.error('更改失败')
					}

				})
			},
			updateView(e) {
				this.dialogVisible = true;
				this.form.fId = e.fId;
				this.form.inCheckNum = e.inCheckNum
				this.form.outCheckNum = e.outCheckNum
			},
			downloadFile: function(filename, type) {
				this.dialogVisibleExport = true
				this.type = type
//				let params = {
//					fId: this.exportDetail.fId[0],
//					inoutYardNam: "保税仓库货物出库单",
//					operNam: type
//				}
//				console.log(params)
//				exportExcelForInoutyardBodyAndHead(params)
			},
			expoprt(filename, type) {
				this.dialogVisibleExportSecond = true
				this.type = type
//				let params = {
//					fId: this.exportDetail.fId[0],
//					inoutYardNam: "保税仓库货物出库单",
//					operNam: type
//				}
//				console.log(params)
//				exportPrint(params).then((response) => { // 处理返回的文件流
//					console.log(response)
//					if(response.status === 200) {
//						this.PrintdialogVisible = true;
//						var data = new Uint8Array(response.data)
//						var workbook = XLSX.read(data, {
//							type: 'array'
//						})
//						var worksheet = workbook.Sheets[workbook.SheetNames[0]];
//						var a = worksheet['!ref']
//						var cm = worksheet['!rows'].length
//						a = "A1:N" + cm
//						worksheet['!ref'] = a
//						var innerHTML = XLSX.utils.sheet_to_html(worksheet);
//						//						var newStr = innerHTML.slice(0, innerHTML.indexOf("v=\"货主或代理企业签章  负责人签字\"") - 1) + ' style="text-align: left;height:120px;" valign="top" ' + innerHTML.slice(-(innerHTML.length - innerHTML.indexOf("v=\"货主或代理企业签章  负责人签字\"")))
//						//						newStr = newStr.slice(0, newStr.indexOf("v=\"保税仓库签章  负责人签字\"") - 1) + ' style="text-align: left;height:120px;" valign="top" ' + newStr.slice(-(newStr.length - newStr.indexOf("v=\"保税仓库签章  负责人签字\"")))
//						//						newStr = newStr.slice(0, newStr.indexOf("v=\"海关批注\"") - 1) + ' style="text-align: left;height:120px;" valign="top" ' + newStr.slice(-(newStr.length - newStr.indexOf("v=\"海关批注\"")))
//						this.tableHtml = innerHTML;
//					}
//				})

				//      dealt(this.currentRow.fId)
				//expoprtExcel(this.currentRow.fId)

			},
			downloadFile2: function(filename, type) {
				let params = {
					fId: 0,
					inoutYardNam: "保税仓库货物出库单",
					operNam: type
				}
				console.log(params)
				exportExcelForInoutyardBodyAndHead(params)
			},
			handleSelectionChange(selection) {
				this.exportDetail.fId = selection.map(item => item.fId);
				this.single = selection.length != 1;
				//				this.multiple = !selection.length;
			},
			uploadSuccess: function(e) {
				// this.fileLoading = false
				if(e.data == null) {
					console.log(e)
					this.$notify.error(e.msg)
				} else {
					console.log(e)
					this.getDataList()
					this.$message.success(e.data)
				}

			},
			onProgress(event, file, fileList) {
				// this.$notify.error("开始上传")
				// this.fileLoading = true
			},
			onChange(file, fileList) {
				this.fileLoading = !this.fileLoading
			},

			change(e) {
				this.$forceUpdate()
			},
			aduitSubmit(e) {
				console.log(e)
				let params = {}
				if(e == 1) { //审核成功
					params.businessCheckStatus = 1
					params.fId = this.exportDetail.fId[0]
					updateByIdForBusinessCheckStatus(params).then((res) => {
						console.log(res)
						this.$notify.success("已审核成功")
						this.visible = false
						this.$emit('refreshDataList')
						this.getDataList()
						this.BUsinessVisible = false;
					})
				}

				if(e == 2) { //审核失败
					params.businessCheckStatus = 2
					params.fId = this.exportDetail.fId[0]
					updateByIdForBusinessCheckStatus(params).then((res) => {
						console.log(res)
						this.$notify.success("已审核失败")
						this.visible = false
						this.$emit('refreshDataList')
						this.getDataList()
						this.BUsinessVisible = false;
					})
				}

			},
			typeFormatter(e) {
				console.log(e)
				if(e.businessCheckStatus == "0") {
					return "未审核"
				} else if(e.businessCheckStatus == "1") {
					return "审核成功"
				} else {
					return "审核失败"
				}
			},
			typeFormatter2(e) {
				console.log(e)
				if(e.operCheckStatus == "0") {
					return "未审核"
				} else if(e.operCheckStatus == "1") {
					return "审核成功"
				} else {
					return "审核失败"
				}
			},
			downloadFile3: function() {
				console.log(this.secondForm)
				this.dialogVisibleExport = false
				let params = {
					fId: this.exportDetail.fId[0],
					inoutYardNam: "保税仓库货物出库单",
					operNam: this.type,
					model: this.secondForm.model
				}
				console.log(params)
				exportExcelForInoutyardBodyAndHead(params)
			},
			downloadFile4: function() {
				let params = {
					fId: this.exportDetail.fId[0],
					inoutYardNam: "保税仓库货物出库单",
					operNam: this.type,
					model: this.secondForm.model
				}
				console.log(params)
				exportPrint(params).then((response) => { // 处理返回的文件流
					console.log(response)
					if(response.status === 200) {
						this.PrintdialogVisible = true;
						var data = new Uint8Array(response.data)
						var workbook = XLSX.read(data, {
							type: 'array'
						})
						var worksheet = workbook.Sheets[workbook.SheetNames[0]];
						var a = worksheet['!ref']
						var cm = worksheet['!rows'].length
						a = "A1:N" + cm
						worksheet['!ref'] = a
						var innerHTML = XLSX.utils.sheet_to_html(worksheet);
						//						var newStr = innerHTML.slice(0, innerHTML.indexOf("v=\"货主或代理企业签章  负责人签字\"") - 1) + ' style="text-align: left;height:120px;" valign="top" ' + innerHTML.slice(-(innerHTML.length - innerHTML.indexOf("v=\"货主或代理企业签章  负责人签字\"")))
						//						newStr = newStr.slice(0, newStr.indexOf("v=\"保税仓库签章  负责人签字\"") - 1) + ' style="text-align: left;height:120px;" valign="top" ' + newStr.slice(-(newStr.length - newStr.indexOf("v=\"保税仓库签章  负责人签字\"")))
						//						newStr = newStr.slice(0, newStr.indexOf("v=\"海关批注\"") - 1) + ' style="text-align: left;height:120px;" valign="top" ' + newStr.slice(-(newStr.length - newStr.indexOf("v=\"海关批注\"")))
						this.tableHtml = innerHTML;
					}
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
		/*white-space: nowrap;*/
		text-align: center;
		padding: 0.5rem;
		height: 18px;
		/*line-height: 5px;*/
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
	
	.table-html-wrap /deep/ tbody tr:nth-last-of-type(2) td {
		height: 1px;
		display: none;
	}
	
	.table-html-wrap /deep/ tbody tr:nth-of-type(2) td:nth-last-of-type(1) {
		width: 118px;
	}
	.table-html-wrap /deep/ tbody tr:nth-of-type(2) td:nth-last-of-type(2) {
		width: 64px
	}
	
	
	
	.table-html-wrap /deep/ tbody tr:last-of-type td {
		height: 100px;
		text-align: left;
		vertical-align: top;
	}
	
	.table-html-wrap /deep/ tbody tr:nth-of-type(2) td {
		text-align: right !important;
		border: 0px solid white !important;
		border-bottom: 1px solid black !important;
	}
</style>