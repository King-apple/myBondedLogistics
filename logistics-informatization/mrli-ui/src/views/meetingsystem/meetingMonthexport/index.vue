<template>
	<div class="mod-config">
		<basic-container>
			<el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
				<el-form-item label="时间" prop="phone">
					<!--<el-date-picker v-model="value1" type="datetimerange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
						</el-date-picker>-->
					<el-date-picker v-model="value1" type="month" placeholder="选择月">
					</el-date-picker>
				</el-form-item>

				<el-form-item>
					<el-button icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
				</el-form-item>
				<el-form-item>
					<!--<el-button  icon="el-icon-plus" type="primary" @click="addOrUpdateHandle()">新增</el-button>-->
					<el-button icon="el-icon-plus" type="warning" :loading="exportLoading" @click="handleExport">导出</el-button>
					<!--<el-button  icon="el-icon-upload2" type="info" plain size="mini" @click="handleImport()">导入</el-button>
          <el-button  icon="el-icon-back" type="danger" @click="back()">返回</el-button>-->
				</el-form-item>
			</el-form>

			<div class="avue-crud">
				<el-table :data="dataList" border v-loading="dataListLoading">
					<el-table-column prop="id" header-align="center" align="center" label="id">
					</el-table-column>
					<el-table-column prop="name" header-align="center" align="center" label="姓名">
					</el-table-column>
					<el-table-column prop="phone" header-align="center" align="center" label="手机号">
					</el-table-column>
					<el-table-column prop="company" header-align="center" align="center" label="公司/单位">
					</el-table-column>
					<el-table-column prop="position" header-align="center" align="center" label="职位">
					</el-table-column>
					<el-table-column prop="email" header-align="center" align="center" label="邮箱">
					</el-table-column>
					<el-table-column prop="source" header-align="center" align="center" label="来源">
					</el-table-column>
					<el-table-column prop="status" header-align="center" align="center" label="报名状态" :formatter="typeFormatter">
					</el-table-column>
					<el-table-column prop="insDate" header-align="center" align="center" label="插入时间">
					</el-table-column>
					<!--<el-table-column header-align="center" align="center" label="操作">
						<template slot-scope="scope">
							<el-button type="text" size="small" icon="el-icon-edit" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
							<el-button type="text" size="small" icon="el-icon-delete" @click="deleteHandle(scope.row.id)">删除</el-button>
						</template>
					</el-table-column>-->
				</el-table>
			</div>

			<div class="avue-crud__pagination">
				<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex" :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" background layout="total, sizes, prev, pager, next, jumper">
				</el-pagination>
			</div>

			<!-- 用户导入对话框 -->
			<el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
				<el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers" :action="upload.url + '?updateSupport=' + upload.updateSupport+ '&meetingId=' + upload.meetingId" :disabled="upload.isUploading" :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
					<i class="el-icon-upload"></i>
					<div class="el-upload__text">
						将文件拖到此处，或
						<em>点击上传</em>
					</div>
					<div class="el-upload__tip" slot="tip">
						<!--<el-checkbox v-model="upload.updateSupport" />是否更新已经存在的用户数据-->
						<el-link type="info" style="font-size:12px" @click="importTemplate">下载模板</el-link>
					</div>
					<div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
				</el-upload>
				<div slot="footer" class="dialog-footer">
					<el-button type="primary" @click="submitFileForm">确 定</el-button>
					<el-button @click="upload.open = false">取 消</el-button>
				</div>
			</el-dialog>

			<!-- 弹窗, 新增 / 修改 -->
			<table-form v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></table-form>
		</basic-container>
	</div>
</template>

<script>
	import { fetchList, delObj, exportInfo, importTemplate, exportForMonth } from '@/api/meetingsystem/meetingpeople'
	import { download } from '@/util/util'
	import TableForm from './meetingpeople-form'
	import { dateToString } from "@/util/date";
	import { mapGetters } from 'vuex'
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
				//      dataForm:{},
				// 用户导入参数
				upload: {
					// 是否显示弹出层（用户导入）
					open: false,
					// 弹出层标题（用户导入）
					title: "",
					// 是否禁用上传
					isUploading: false,
					// 是否更新已经存在的用户数据
					updateSupport: 0,
					meetingId: "",
					// 设置上传的请求头部
					//			headers: {
					//				Authorization: "Bearer " + getToken()
					//			},
					// 上传的地址
					url: "/meetingsystem/meetingpeople/importData"
				},
				// 导出遮罩层
				exportLoading: false,
				value1: '',
			}
		},
		components: {
			TableForm
		},
		created() {
			console.log(window.location.origin)
			if(this.$route.query.id) {
				this.dataForm.meetingId = this.$route.query.id
				this.upload.meetingId = this.$route.query.id
			}
			this.getDataList()
		},
		computed: {
			...mapGetters(['permissions'])
		},
		methods: {
			typeFormatter: function(row) {
				//  		console.log(row)
				switch(row.status) {
					case '0':
						return "无限制";
					case '1':
						return "报名成功";
				}
			},
			// 获取数据列表
			getDataList() {
				this.dataForm.signStatus = 1
				this.dataListLoading = true
				fetchList(Object.assign(this.dataForm, {
					current: this.pageIndex,
					size: this.pageSize
				})).then(response => {
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
			// 当前页
			currentChangeHandle(val) {
				this.pageIndex = val
				this.getDataList()
			},
			// 新增 / 修改
			addOrUpdateHandle(id) {
				this.addOrUpdateVisible = true
				this.$nextTick(() => {
					this.$refs.addOrUpdate.init(id, this.dataForm.meetingId)
				})
			},
			// 删除
			deleteHandle(id) {
				this.$confirm('是否确认删除ID为' + id, '提示', {
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
			back() {
				this.$router.go(-1); //返回上一层
			},

			/** 导出按钮操作 */
			handleExport() {
				this.dataForm.startTime = null
				this.dataForm.endTime = null
				if(this.value1) {
					this.dataForm.startTime = dateToString(this.value1)
				}
				const queryParams = {};
				queryParams.meetingId = this.dataForm.meetingId
				this.$confirm('是否确认导出所有数据项?', "警告", {
					confirmButtonText: "确定",
					cancelButtonText: "取消",
					type: "warning"
				}).then(() => {
					this.exportLoading = true;
					console.log(queryParams)
					return exportForMonth(this.dataForm);
				}).then(response => {
					console.log(response.data)
					download(response.data.msg);
					this.exportLoading = false;
				}).catch(() => {});
			},

			/** 导入按钮操作 */
			handleImport() {
				this.upload.title = "用户导入";
				this.upload.open = true;
			},
			/** 下载模板操作 */
			importTemplate() {
				importTemplate().then(response => {
					console.log(response)
					download(response.data.data.msg);
				});
			},
			// 文件上传中处理
			handleFileUploadProgress(event, file, fileList) {
				this.upload.isUploading = true;
			},
			// 文件上传成功处理
			handleFileSuccess(response, file, fileList) {
				this.upload.open = false;
				this.upload.isUploading = false;
				this.$refs.upload.clearFiles();
				this.getDataList()
				console.log(response)
				this.$alert(response.data, "导入结果", {
					dangerouslyUseHTMLString: true
				});
			},
			// 提交上传文件
			submitFileForm() {
				this.$refs.upload.submit();
			},
			handleQuery() {
				this.dataForm.startTime = null
				this.dataForm.endTime = null
				console.log(this.value1)
				if(this.value1) {
					this.dataForm.startTime = dateToString(this.value1)
				}
				console.log(this.dataForm)
				this.getDataList()
			}

		}
	}
</script>
<style>
	/deep/ .el-form-item__label {
		float: none;
		word-break: break-word;
	}
</style>