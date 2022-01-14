<template>
	<div class="main" :style="{backgroundImage: 'url(' + coverImgUrl + ')', backgroundSize:'100% 100%'}">
		<div class="plo" style="border-radius: 10px;padding: 10px;margin-left:10px;background-color: rgb(119, 239, 194);margin-top: 20px;width: 90px;color: white;bgcolor=#00FF00;">
			<span @click="goTopage" >跳转报表</span>
		</div>
		<div class="headTitle">
			<span :style="{fontSize:fontSize+'px',color:titleColor}">{{title}}</span>
		</div>

		<div class="middle">
			<div class="middle_img_head">
				<span>扫描二维码,即可完成签到</span>
			</div>
			<div>
				<img class="img" :src="QRCodeImg" />
				<div class="middle_img_bottom">
					<span>共签到{{peopleCount}}人</span>
				</div>
			</div>
			
			
			<vue-seamless-scroll :data="applyList" v-if="viewFlg" class="seamless-warp" :class-option="classOption">
				<div class="middle-second" v-for="(item,index) in applyList" :key="index">
					<div style="/*! display: inline; */background-color: bisque;height: 42px;line-height: 186px;">
						<!--margin-top: 71px;-->
						<img style="margin-left: 10px;border-radius: 70px;width:100px;height:100px;" v-if="item.wxImg" :src="item.wxImg" />
						<img style="margin-left: 10px;border-radius: 70px;" v-else src="../../../../public/images/yonghu.png" width="143px" />
					</div>
					<span style="display: inline;margin-top:-35px;height: 150px;line-height: 25px;padding: 23px;margin-left:118px;font-size: 22px;color:white;height:40px;width:88%;display: flow-root">
						{{item.company}} {{item.name}} {{item.position}}
					</span>
					<div style="">
						
						<!--<span style="margin-right: 15px;display: flow-root">   </span>
						<span>  </span>-->
					</div>

				</div>
			</vue-seamless-scroll>
			
			<div v-else class="content_span">
				<span>欢迎参加</span>
				<span style="display:block;">请扫码签到</span>
			</div>
			
			
			
		</div>
	</div>
</template>

<script>
	import { fetchList, delObj, getObjForMeetingId } from '@/api/meetingsystem/meetingbigview'
	import { getList } from '@/api/meetingsystem/meetingpeople'
	import { getmeetingId } from '@/api/meetingsystem/meetingmanage'
	import {getObj as getManage} from '@/api/meetingsystem/meetingactivitylist'
	import { mapGetters } from 'vuex'
	import vueSeamlessScroll from 'vue-seamless-scroll'
	export default {
		data() {
			return {
				dataForm: {},
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				addOrUpdateVisible: false,
				coverImgUrl: "",
				fontSize: "",
				title: "",
				applyList: [],
				playLatest: null,
				setintervalFun: null,
				QRCodeImg:'',
				viewFlg:false,
				titleColor: "white",
				peopleCount: 0

			}
		},
		created() {

			this.getDataList()

			//			this.pla()

		},
		components: {
			vueSeamlessScroll,
		},
		mounted() {

			//			this.playLatestConsult();
		},
		computed: {
			classOption() {
				return {
					step: 0.8, // 数值越大速度滚动越快
					limitMoveNum: 2, // 开始无缝滚动的数据量 this.dataList.length
					hoverStop: true, // 是否开启鼠标悬停stop
					direction: 1, // 0向下 1向上 2向左 3向右
					openWatch: true, // 开启数据实时监控刷新dom
					singleHeight: 0, // 单步运动停止的高度(默认值0是无缝不停止的滚动) direction => 0/1
					singleWidth: 0, // 单步运动停止的宽度(默认值0是无缝不停止的滚动) direction => 2/3
					waitTime: 2000 // 单步运动停止的时间(默认值1000ms)
				}
			},
			...mapGetters(['permissions'])
		},
		methods: {
			getData() {
				getList(this.dataForm).then(response => {
					console.log(response)
					if(response.data.data) {
						if(response.data.data.length>0){
							this.viewFlg=true
						}
						this.applyList = response.data.data
						this.peopleCount = this.applyList.length
					}

				})
			},
			// 获取数据列表
			getDataList() {
				this.dataListLoading = true
				this.dataForm.meetingId = this.$route.query.meetingId
				console.log(this.dataForm.meetingId)

				getObjForMeetingId(this.dataForm.meetingId).then(response => {
					console.log(response)
					if(response.data.data){
						this.fontSize = response.data.data.titleSize
						this.title = response.data.data.title
						this.titleColor = response.data.data.color
						if(response.data.data.url){
							this.coverImgUrl = window.location.origin + "/meetingsystem/meetingfile/getimg/sign/" + response.data.data.url
						}else{
							this.coverImgUrl = require('../../../../public/images/backimg.png')
						}
					} else {
						getManage(this.dataForm.meetingId).then((res) => {
							this.fontSize = 50
							this.title = res.data.data.title
							this.titleColor = '#FFFFFF'
							this.coverImgUrl = require('../../../../public/images/backimg.png')
						})
						
						
					}
					
					
					
				})
				
				getmeetingId(this.dataForm.meetingId).then(response => {
					console.log(response)
					this.QRCodeImg = window.location.origin + "/meetingsystem/meetingfile/getimg/sign/" + response.data.data[0].img
				})

				this.setintervalFun = setInterval(this.getData, 3000)
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
			// 监测滚动事件
			//			getListScroll(e) {
			//				console.log(e)
			//				console.log(e.target.scrollTop)
			//				// 16x后，到了倒数4个元素的时候要开始拼接数组，因为初始有四个
			//				if((e.target.scrollTop / 5) % 5 === 0) {
			//					this.applyList = this.applyList
			//				}
			//			},
			goTopage(){
				const newPage = this.$router.resolve({
					path: '/bigViewTable',
					query: {
						meetingId: this.$route.query.meetingId
					}
				})
				window.open(newPage.href,'_blank')
			}
		},
		beforeDestroy() {
			clearInterval(this.setintervalFun)

		},
	}
</script>
<style lang="less" scoped>
	.main {
		width: 100%;
		height: 100%;
		position: fixed;
		background-size: 100% 100%;
		margin: 0 auto;
	}
	
	.headTitle {
		width: auto;
		text-align: center;
		margin-top: 100px;
		font-size: 49px;
	}
	
	.middle {
		/*margin: 0 auto;
		padding: 95px 20px;
		background-color: rgba(34, 34, 34, 0.4);
		height: 410px;
		margin: auto 41px;
		margin-top: 104px;
		border-radius: 3px;*/
		padding: 98px 20px;
		background-color: rgba(34, 34, 34, 0.4);
		height: 529px;
		margin: auto 41px;
	    margin-top: auto;
		margin-top: 61px;
		border-radius: 3px;
		width:95%;
	}
	
	.middle-second {
		margin-left: 80px;
		height: 145px;
		width:610px;
	}
	
	.img {
		float: left;
		margin-left: 300px;
		width:210px;
		height:210px;
		/*margin-top: -28px;*/
		margin-top: 37px;
	}
	
	.el-card .is-always-shadow {
		background-color: #1F94F4;
	}
	
	.botDesc-right-consult-list {
		margin-top: 16px;
		height: 273px;
		overflow: scroll;
		overflow-y: hidden;
		overflow-x: hidden;
	}
	
	.seamless-warp {
		height: calc(100% - 40px);
		overflow: hidden;
	}
	
	.content_span{
	    /*margin-left: 811px;*/
	    color: white;
	    font-size: 27px;
	    text-align: center;
	}
	
	.middle_img_head{
		width: 186px;
		/*height: 55px;*/
		color: white;
		margin-left: 311px;
		height: 0px;
	}
	
	.middle_img_bottom{
		width: 118px;
	    height: 0px;
	    display: block;
	    color: white;
	    text-align: center;
	    margin-left: 340px;
	}


</style>