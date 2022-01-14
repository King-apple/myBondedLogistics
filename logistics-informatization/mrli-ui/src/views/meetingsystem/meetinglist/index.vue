<template>
	<div class="mod-config">
		<basic-container>
			<el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
				<el-form-item>
					<el-button v-if="permissions.meet_meetingactivitylist_add" icon="el-icon-plus" type="primary" @click="addOrUpdateHandle()">发布活动</el-button>
				</el-form-item>
			</el-form>

			<div class="avue-crud">
				<div class="list_view" v-for="(item,index) in dataList" :key="index">
					<div class="img">
						<el-image style="width: 100px; height: 100px" :src="item.img" fit="fill"></el-image>
					</div>
					<div class="middle">
						<span class="content title" style="cursor:pointer" @click="addOrUpdateHandle(item.id)">{{item.title}}</span>
						<span class="content"><span class="endtime">
      				<el-image style="width: 20px; height: 20px;" 
      				src="/images/endtime.png"
      				fit="fill"></el-image>
      			<span style="margin-right:20px">截止时间：{{item.endTime}}</span></span>
						<el-image style="width: 20px; height: 20px;" src="/images/address.png" fit="fill"></el-image>
						<span>{{item.address}}</span> </span>
						<span class="content time_user_count">会议时间：{{item.startTime}} - {{item.endTime}}    发布者：{{item.updateUser}}   参会人数：{{item.peopleCount}}</span>
						<div class="content">
							<el-image style="width: 20px; height: 20px;" src="/images/yonghu.png" fit="fill"></el-image><span class="agament managercolor" style="cursor:pointer" @click="clickFunc(item.id)">参会者管理</span>
							<!--<el-image style="width: 20px; height: 20px;" src="/images/baoming.png" fit="fill"></el-image><span class="agament managercolor" style="cursor:pointer">报名管理</span>-->
							<el-image style="width: 20px; height: 20px;" src="/images/qiandao.png" fit="fill"></el-image><span class="agament managercolor" style="cursor:pointer" @click="clickFuncMeet(item.id)">签到管理</span>
							<el-image style="width: 20px; height: 20px;" src="/images/shanchu.png" fit="fill"></el-image><span class="agament managercolor" style="cursor:pointer" @click="deleteHandle(item.id)">删除</span>
						</div>
					</div>
					<div>
						<span class="edit" style="cursor:pointer" @click="addOrUpdateHandle(item.id)">编辑</span>
					</div>
				</div>

				<!--<el-table :data="dataList" border v-loading="dataListLoading">
					<el-table-column prop="id" header-align="center" align="center" label="id">
					</el-table-column>
					<el-table-column prop="title" header-align="center" align="center" label="会议活动标题">
					</el-table-column>
					<el-table-column prop="startTime" header-align="center" align="center" label="会议开始时间">
					</el-table-column>
					<el-table-column prop="endTime" header-align="center" align="center" label="会议结束时间">
					</el-table-column>
					<el-table-column prop="address" header-align="center" align="center" label="会议地点">
					</el-table-column>
					<el-table-column prop="img" header-align="center" align="center" label="会议图片">
					</el-table-column>
					<el-table-column prop="remark" header-align="center" align="center" label="会议简介">
					</el-table-column>
					<el-table-column prop="insDate" header-align="center" align="center" label="插入时间">
					</el-table-column>
					<el-table-column header-align="center" align="center" label="操作">
						<template slot-scope="scope">
							<el-button v-if="permissions.meet_meetingactivitylist_edit" type="text" size="small" icon="el-icon-edit" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
							<el-button v-if="permissions.meet_meetingactivitylist_del" type="text" size="small" icon="el-icon-delete" @click="deleteHandle(scope.row.id)">删除</el-button>
						</template>
					</el-table-column>
				</el-table>-->
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
	import { fetchList, delObj } from '@/api/meetingsystem/meetingactivitylist.js'
	import TableForm from './meetingactivitylist-form'
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
				addOrUpdateVisible: false
			}
		},
		components: {
			TableForm
		},
		created() {
			//  	this.sys_user_add = this.permissions['meet_meetingactivitylist_add']
			//    this.sys_user_edit = this.permissions['meet_meetingactivitylist_edit']
			//    this.sys_user_del = this.permissions['meet_meetingactivitylist_del']
			//    console.log(this.sys_user_add)
			this.getDataList()
		},
		computed: {
			...mapGetters(['permissions'])
		},
		methods: {
			// 获取数据列表
			async getDataList() {
				this.dataListLoading = true
				fetchList(Object.assign({
					current: this.pageIndex,
					size: this.pageSize
				})).then(response => {
					console.log(response.data.data.records)
					response.data.data.records.forEach(function(e){  
   						console.log(e)
   						e.img = window.location.origin + "/meetingsystem/meetingfile/getimg/sign/"+e.img
					});
					this.dataList = response.data.data.records
					console.log(this.dataList)
					this.dataList.img = response.data.data.records
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
					this.$refs.addOrUpdate.init(id)
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
			clickFunc(id) {
				this.$router.push({path:'/meeting/people',query: {id: id}})
				// console.log("222222222222222")
			},
			clickFuncMeet(id){
				console.log(id)
				this.$router.push({path:'/meetingsystem/meetingmanage/index',query: {id: id,flg:'true'}})
			}
		}
	}
</script>

<style lang="less" scoped>
	.list_view {
		width: 90%;
		height: 100%;
		padding: 11px;
	}
	
	.img {
		float: left;
	}
	
	.middle {
		margin-left: 97px;
		
	}
	
	.content {
		margin-left: 21px;
		overflow-wrap: break-word;
		word-break: normal;
		width: auto;
		display: block;
		white-space: pre-wrap;
		word-wrap: break-word;
		overflow: hidden;
	}
	
	.agament {
		padding-right: 40px;
	}
	
	.title {
		font-size: 18px;
		color: #0073EB;
	}
	
	.endtime {
		font-weight: 800;
	}
	
	.time_user_count {
		font-size: 11;
		color: #C7C7C7;
	}
	
	.managercolor {
		color: #15A0FF;
	}
	
	.endtime .el-image__inner {
		margin-top: 3px;
		height: 20px;
		line-height: 16px;
		margin-bottom: 6px;
		z-index: 1000;
	}
	.edit{
		float: right;
		margin: -38px;
		background-color: #007cff;
		padding: 3px 18px;
		border-radius: 3px;
		font-size: 15px;
		color: white;
	}
</style>