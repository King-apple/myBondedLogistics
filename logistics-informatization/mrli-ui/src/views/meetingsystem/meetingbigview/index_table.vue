<template>
	<div class="main" :style="{backgroundImage: 'url(' + coverImgUrl + ')', backgroundSize:'100% 100%'}">

		<div class="table-html-wrap" v-html="tableBUsinessHtml" id="printBusinessTest" style="color: white;font-size: 31px;margin-top: 80px;overflow-x: auto;
overflow-y: auto;
height: 602px;"></div>
			<!--<div style="margin-top: 5%; margin-left: 80%">
				<el-button @click="BUsinessVisible = false">取 消</el-button>
				<el-button type="primary" @click="aduitSubmit(1)" v-if="canSubmit">审核通过</el-button>
				<el-button type="danger" @click="aduitSubmit(2)" v-if="canSubmit">审核不通过</el-button>
			</div>-->
	</div>
</template>

<script>
	import { fetchList, delObj, getObjForMeetingId } from '@/api/meetingsystem/meetingbigview'
	import {exportForDayPrint} from '@/api/meetingsystem/meetingpeople'
	import { getmeetingId } from '@/api/meetingsystem/meetingmanage'
	import {getObj as getManage} from '@/api/meetingsystem/meetingactivitylist'
	import { mapGetters } from 'vuex'
	import XLSX from 'xlsx'
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
				peopleCount: 0,
				 BUsinessVisible: false,
	  tableBUsinessHtml: "",

			}
		},
		created() {

			this.getData()
this.getDataList()
			//			this.pla()

		},
		components: {
		},
		mounted() {

		},
		computed: {
			...mapGetters(['permissions'])
		},
		methods: {
			getData() {
				
				this.dataForm.meetingId = this.$route.query.meetingId
				this.dataForm.signStatus = 1
				exportForDayPrint(this.dataForm).then((response) => { // 处理返回的文件流
			console.log(response)
			if(response.status === 200) {
				this.BUsinessVisible = true;
				var data = new Uint8Array(response.data)
				console.log(data)
				var workbook = XLSX.read(data, {
					type: 'array'
				})
				var worksheet = workbook.Sheets[workbook.SheetNames[0]];
				console.log(worksheet)
				var a = worksheet['!ref']
//				var cm = worksheet['!rows'].length
//	            a = "A1:D12" //+cm
	            worksheet['!ref'] = a
	            var innerHTML = XLSX.utils.sheet_to_html(worksheet);
	            var newStr = innerHTML.slice(0, innerHTML.indexOf("v=\"入驻企业签章  负责人签字\"")-1) + ' style="text-align: left;height:120px;" valign="top" ' + innerHTML.slice(-(innerHTML.length-innerHTML.indexOf("v=\"入驻企业签章  负责人签字\"")))
	           	newStr = newStr.slice(0, newStr.indexOf("v=\"海关批注\"")-1) + ' style="text-align: left;height:120px;" valign="top" ' + newStr.slice(-(newStr.length-newStr.indexOf("v=\"海关批注\"")))
	            this.tableBUsinessHtml = innerHTML;
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

//				this.setintervalFun = setInterval(this.getData, 3000)
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
		},
		beforeDestroy() {
			clearInterval(this.setintervalFun)

		},
	}
</script>
<style lang="less" scoped>
	#id{
		margin-top: 100px;
	}
	
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
  white-space: nowrap;
  text-align: center;
  padding: 0.5rem;
  background-color: black;
  opacity:0.6; 
  color:white;
}

.table-html-wrap /deep/ tbody tr:nth-of-type(1) {
  font-weight: bold;
  font-size: 40px;
  border: 0px solid white !important;
}
.table-html-wrap /deep/ tbody tr:nth-of-type(1) td {
  border: 0px solid white !important;
}
.table-html-wrap /deep/ tbody tr:nth-of-type(2) {
  border: 0px solid white !important;
}
.table-html-wrap /deep/ tbody tr:nth-of-type(2) td {
  text-align: center !important;
  border: 0px solid white !important;
  border-bottom: 1px solid black !important;
}
</style>
