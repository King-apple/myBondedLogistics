<template>
	<div class="mod-config">
		<basic-container>
			<el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
				<el-form-item>
					<el-button v-if="permissions.meeting_meetingmanage_add && viewFlg" icon="el-icon-plus" type="primary" @click="addOrUpdateHandle()">新增</el-button>
					<el-button icon="el-icon-back" type="danger" @click="back()">返回</el-button>
				</el-form-item>
			</el-form>

			<div class="avue-crud">

				<div class="list_view" v-for="(item,index) in dataList" :key="index">
					<div class="img">
						<el-image style="width: 100px; height: 100px" :src="item.img" fit="fill"></el-image>
					</div>
					<div class="middle">
						<span class="content title" style="cursor:pointer" @click="addOrUpdateHandle(item.id)">{{item.meetName}}</span>
						<span class="content"><span class="endtime">
      				<el-image style="width: 20px; height: 20px;" 
      				src="/images/endtime.png"
      				fit="fill"></el-image>
      			<span style="margin-right:20px">截止时间：{{item.endTime}}</span></span>
						<!--<el-image style="width: 20px; height: 20px;" src="/images/address.png" fit="fill"></el-image>
						<span>{{item.address}}</span>-->
						</span>
						<span class="content time_user_count">会议时间：{{item.startTime}} - {{item.endTime}}    发布者：{{item.updateUser}}   参会人数：{{item.peopleCount}}</span>
						<div class="content">
							<el-image style="width: 20px; height: 20px;" src="/images/setting.png" fit="fill"></el-image><span class="agament managercolor" style="cursor:pointer" @click="addOrUpdateHandle(item.id)">基本设置</span>
							<el-image style="width: 20px; height: 20px;" src="/images/qiandao2.png" fit="fill"></el-image><span class="agament managercolor" style="cursor:pointer" @click="clickFunc(item.id,item.meetingId,'first')">签到记录</span>
							<el-image style="width: 20px; height: 20px;" src="/images/phone.png" fit="fill"></el-image><span class="agament managercolor" style="cursor:pointer" @click="clickFunc(item.id,item.meetingId,'second')">签到页配置</span>
							<!--<el-image style="width: 20px; height: 20px;" src="/images/phone.png" fit="fill"></el-image><span class="agament managercolor" style="cursor:pointer" @click="clickFunc(item.id,'third')">签到成功页配置</span>-->
							<el-image style="width: 20px; height: 20px;" src="/images/view.png" fit="fill"></el-image><span class="agament managercolor" style="cursor:pointer" @click="clickFunc(item.id,item.meetingId,'fourth')">大屏设置</span>
							<el-image style="width: 20px; height: 20px;" src="/images/del2.png" fit="fill"></el-image><span class="agament managercolor" style="cursor:pointer" @click="deleteHandle(item.id)">删除</span>
						</div>
					</div>
					<div>
						<span class="edit" style="cursor:pointer" @click="goToBigView(item.meetingId)">进入大屏</span>
					</div>
				</div>
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
	import { fetchList, delObj } from '@/api/meetingsystem/meetingmanage'
	import TableForm from './meetingmanage-form'

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
				viewFlg: false
			}
		},
		components: {
			TableForm
		},
		created() {

		},
		mounted() {
			console.log("ssssssssssssssss")
			this.getDataList()
		},
		computed: {
			...mapGetters(['permissions'])
		},
		methods: {
			// 获取数据列表
			getDataList() {
				this.dataListLoading = true
				this.dataForm.meetingId = this.$route.query.id
				if(this.$route.query.flg){
					this.viewFlg=true
				}
				fetchList(Object.assign(this.dataForm, {
					current: this.pageIndex,
					size: this.pageSize
				})).then(response => {
					this.dataList = response.data.data.records
					this.dataList.forEach(function(ele) {
				    	ele.img = window.location.origin + "/meetingsystem/meetingfile/getimg/sign/" +  ele.img
				    });
//					this.dataList[0].img = window.location.origin + "/meetingsystem/meetingfile/getimg/sign/" + this.dataList[0].img
					console.log(this.dataList)
					console.log(this.dataList.img)
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
					this.$refs.addOrUpdate.init(id, this.$route.query.id)
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
			clickFunc(id,meetingId, activeName) {
				console.log(id);
				this.$router.push({
					path: '/tabSetting',
					query: {
						id: meetingId,
						meetSetId: id,
						activeName: activeName
					}
				})
			},
			goToBigView(id) {
				console.log(id);
				const newPage = this.$router.resolve({
					path: '/bigView',
					query: {
						meetingId: id
					}
				})
				window.open(newPage.href,'_blank')
			},
			back() {
				this.$router.go(-1);
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
	
	.edit {
		float: right;
		margin: -38px;
		background-color: #007cff;
		padding: 3px 18px;
		border-radius: 3px;
		font-size: 15px;
		color: white;
	}
</style>