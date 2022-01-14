<template>
	<div class="mod-config">
		<basic-container>
			<el-col :span="20" :xs="24">
				<el-form :model="dataForm" ref="queryForm" :inline="true" v-show="true" label-width="68px">
					<el-form-item label="姓名" prop="name">
						<el-input v-model="dataForm.name" placeholder="请输入姓名" clearable size="small" style="width: 150px" @keyup.enter.native="handleQuery" />
					</el-form-item>

					<el-form-item label="电话" prop="phone">
						<el-input v-model="dataForm.phone" placeholder="请输入电话" clearable size="small" style="width: 150px" @keyup.enter.native="handleQuery" />
					</el-form-item>

					<el-form-item label="公司" prop="company">
						<el-input v-model="dataForm.company" placeholder="请输入公司" clearable size="small" style="width: 150px" @keyup.enter.native="handleQuery" />
					</el-form-item>

					<el-form-item label="职位" prop="position">
						<el-input v-model="dataForm.position" placeholder="请输入职位" clearable size="small" style="width: 150px" @keyup.enter.native="handleQuery" />
					</el-form-item>

					<el-form-item label="时间" prop="phone">
						<el-date-picker v-model="value1" type="datetimerange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
						</el-date-picker>
					</el-form-item>

					<el-form-item label="签到状态" prop="signStatus">
						<el-select v-model="dataForm.signStatus" filterable placeholder="全部" clearable size="small" style="width: 240px">
							<el-option v-for="dict in statusOptions" :key="dict.id" :label="dict.lable" :value="dict.id" />
						</el-select>
					</el-form-item>

					<el-form-item>
						<el-button icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
						<el-button icon="el-icon-upload" type="warning" @click="handleExport">导出</el-button>
					</el-form-item>
				</el-form>
			</el-col>

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
					<el-table-column prop="signTime" header-align="center" align="center" label="签到时间">
					</el-table-column>
					<el-table-column prop="signStatus" header-align="center" align="center" label="签到状态" :formatter="signStatusFormatter">
					</el-table-column>
					<!-- <el-table-column
                  header-align="center"
                  align="center"
                  label="操作">
            <template slot-scope="scope">
              <el-button  type="text" size="small" icon="el-icon-edit" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
              <el-button  type="text" size="small" icon="el-icon-delete" @click="deleteHandle(scope.row.id)">删除</el-button>
            </template>
          </el-table-column> -->
				</el-table>
			</div>

			<div class="avue-crud__pagination">
				<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex" :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" background layout="total, sizes, prev, pager, next, jumper">
				</el-pagination>
			</div>
			<!-- 弹窗, 新增 / 修改 -->
			<table-form v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></table-form>
		</basic-container>
	</div>
</template>
<script>
	import { fetchList, delObj, exportInfo, importTemplate } from '@/api/meetingsystem/meetingpeople'
	import { download } from '@/util/util'
	import { dateToString } from "@/util/date";
	export default {
		data() {
			return {
				activeName: 'second',
				dataForm: {
					key: ''
				},
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				addOrUpdateVisible: false,
				dataForm: {},
				statusOptions: [{
					"lable": "成功",
					"id": "1"
				}],
				value1: [],
			};
		},
		created() {
			console.log(this.$route.query.id)
			if(this.$route.query.id) {
				this.dataForm.meetingId = this.$route.query.id
				// this.upload.meetingId = this.$route.query.id
			}
			this.getDataList()
		},
		methods: {
			typeFormatter: function(row) {
				switch(row.status) {
					case '0':
						return "无限制";
					case '1':
						return "报名成功";
				}
			},
			signStatusFormatter: function(row) {
				switch(row.signStatus) {
					case '1':
						return "签到成功";
					default:
						return "未报名";
				}
			},
			// 获取数据列表
			getDataList() {
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
			/** 导出按钮操作 */
			handleExport() {
				const queryParams = {};
				queryParams.meetingId = this.dataForm.meetingId
				this.$confirm('是否确认导出所有数据项?', "警告", {
					confirmButtonText: "确定",
					cancelButtonText: "取消",
					type: "warning"
				}).then(() => {
					this.exportLoading = true;
					console.log(queryParams)
					return exportInfo(this.dataForm);
				}).then(response => {
					console.log(response.data)
					download(response.data.msg);
					this.exportLoading = false;
				}).catch(() => {});
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

			handleQuery() {
				this.dataForm.startTime=null
				this.dataForm.endTime=null
				if(this.value1!=null&&this.value1.length>0){
					this.dataForm.startTime = dateToString(this.value1[0])
					this.dataForm.endTime = dateToString(this.value1[1])
				}
				console.log(this.dataForm)
				this.getDataList()
			}

		}
	};
</script>