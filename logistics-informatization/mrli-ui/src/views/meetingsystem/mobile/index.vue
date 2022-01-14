<template>
	<div class="main" style="{width:width+'px',height:height+'px'}">
		<div class="head" :style="{backgroundImage: 'url(' + head_background + ')', backgroundSize:'100%'}">
			<!--<img :src="" class="head_background" />-->
			<img :src="wx_head" class="head_img" />
		</div>
		<div class= "middle_cs">
			<div class = "middle_left">
				<span class="middle_left_text">
					{{peopleCount}}
				</span>
				<div>
				<span class="middle_left_text_bottom">
					签到人数
				</span>
				</div>
			</div>
			<div class = "middle_right">
				<div class = "middle_right_top">
					<img src="../../../../public/images/mobiel_time.png" width="10" height="10" style="margin-left: 24px;width: 20px;height: 20px;margin-top: 20px;">
					<span style="display:inline-block;margin-left:5px;">签到开始时间:</span>
					<span style="display:inline-block;margin-left:40px;">{{manageInfo.startTime}}</span>
				</div>
				<div style="height:1px;background-color: #A6A9AD;">
					
				</div>
				<div class = "middle_right_bottom">
					<img src="../../../../public/images/mobiel_time.png" width="10" height="10" style="margin-left: 24px;width: 20px;height: 20px;margin-top: 20px;">
					<span style="display:inline-block;margin-left:5px;">签到结束时间:</span>
					<span style="display:inline-block;margin-left:40px;">{{manageInfo.endTime}}</span>
				</div>
			</div>
		</div>
		<div class="middle_two" v-show="afterSignFlg">
			<img src="../../../../public/images/qiandaosuccess.png" style="margin-top:5px;" width="40" height="40"	>
			<span >您已签到！</span>
		</div>
		
		<div class="middle_two_span" v-show="afterSignFlg">
			<span>签到时间： {{signTime}}</span>
		</div>
		
		<div class="middle_three" v-show="authenticationFlg" >
			<span style="padding: 10px;">签到凭证</span>
			<input type="text" placeholder="手机号" v-model="dataForm.mobile" style="height: 37px;width: 56%;background-color: #F5F5F5;border: 0;border-radius: 5px;"/>
		</div>
		
		<!--<div class="bottom" style="margin-top:100px;" v-show="beforsignFlg">
			<span >签到</span>
		</div>-->
		
		
		<div class="bottom" style="margin-top:50px;" v-show="gotoFlg" @click="submitQianDao()">
			<span >签  到</span>
		</div>
		
		<div class="bottom" style="margin-top:50px;" v-show="authenticationButtonFlg" @click="submitUserInfo()">
			<span >提 交 凭 证</span>
		</div>
		
		<div class="bottom" style="margin-top:50px;" v-show="timeOver">
			<span >{{timeOver_value}}</span>
		</div>
		
		
		
	</div>
</template>

<script>
	import {getByMeetingId} from '@/api/meetingsystem/meetingpagesetting'
	import {getObj,addObj} from '@/api/meetingsystem/meetingwx'
	import {gettattestation,saveTattestation} from '@/api/meetingsystem/meetingpeople'
	import {bjDate,dateFormat} from '@/util/date'
	export default { 
    data () {
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
		value:"",
		obj:{},
		width: '',
		height:'',
		wx_head: '',
		head_background: '',
		manageInfo: {},
		peopleCount: 0,
		timeOver:false,
		authenticationFlg:false,
		authenticationButtonFlg:false,
		wxInfo:{},
		timeOver_value: "",
		afterSignFlg:false,
		gotoFlg:false,
		phone: "",
		signTime:"",
		
      }
    },
    created () {
    	this.height= document.documentElement.clientHeight;
        this.width= document.documentElement.clientWidth;
        console.log(this.width)
    	console.log(window.location.origin)
    	console.log(this.$route.query.param)
    	console.log(JSON.parse(this.$route.query.wxInfo))
//  	console.log(JSON.parse(this.$route.query.meetingWx))
    	
    	this.wxInfo = JSON.parse(this.$route.query.wxInfo)
    	this.wx_head=this.wxInfo.headimgurl
    	
      this.getDataList()
    },
    computed: {
     
    },
    methods: {
    	
    	getDataList(){
    		this.dataList.meetingId = this.$route.query.param
    		this.dataForm.meetingId = this.$route.query.param
    		getByMeetingId(this.dataList.meetingId).then((res) =>{ //获取手机端显示样式
				console.log(res)
				if(res.data.data.pageInfo){
					this.head_background = window.location.origin + "/meetingsystem/meetingfile/getimg/sign/"+res.data.data.pageInfo.phoneUrl
				} else {
					this.head_background = require('../../../../public/images/backimg.png')
				}
				
				
				this.manageInfo = res.data.data.manageInfo
				this.peopleCount = res.data.data.peopleInfo
				console.log(this.head_background)
				
				getObj(this.wxInfo.openid).then((response) => {
	    			var testDate = new Date();
					let starttimeFlg = bjDate(res.data.data.manageInfo.startTime,dateFormat(testDate))
					if(starttimeFlg=="1"){
						this.timeOver = true;
						this.timeOver_value = "会议还没有开始"
						return;
					}
					let endtimeFlg = bjDate(res.data.data.manageInfo.endTime,dateFormat(testDate))
					if(endtimeFlg=="2"){
						this.timeOver = true;
						this.timeOver_value = "会议已经结束"
						return;
					}
	    			
	    			console.log(response)
	    			if(!response.data.data){
	    				this.authenticationFlg=true
	    				this.authenticationButtonFlg=true
	    			}else {
	    				this.authenticationFlg=false
	    				this.authenticationButtonFlg=false
	    				//签到状态
	    				let param = {
	    					meetingId: this.dataList.meetingId,
	    					phone: response.data.data.phone
	    				}
	    				this.phone = response.data.data.phone
	    				gettattestation(param).then((resp) => {
	    					console.log(resp)
	    					
	    					if(resp.data.data.length==0){
	    						 this.$message.error("您不属于当前会议，请咨询会议管理人员！");
	    						this.authenticationFlg=false
			    				this.authenticationButtonFlg=false
			    				this.timeOver = false
			    				this.gotoFlg = false
	    					}else{
	    						if(resp.data.data[0].signStatus){
	    							this.gotoFlg = false
	    							this.afterSignFlg=true
	    							this.signTime = resp.data.data[0].signTime
	    						}else{
	    							this.gotoFlg = true
	    							this.afterSignFlg=false
	    						}
	    						
	    					}
	    				})
	    				
	    				
	    				
	    				
	    			}
				
			})	
			})
    		
    		
    },
    submitUserInfo(){
    	let obj = Object.assign(this.dataForm, this.wxInfo);
    	addObj(obj).then((res) => {
    		console.log(res);
    		this.getDataList();
    	})
    	console.log("提交凭证")
    },
    
    submitQianDao(){
    	console.log("去签到")
    	let param = {
					meetingId: this.dataList.meetingId,
					phone: this.phone,
					wxImg: this.wx_head
				}
    	saveTattestation(param).then((res) => {
    		this.getDataList();
    		console.log(res)
    	})
    	
    }
    

    }
    	
  }
</script>

<style lang="less" scoped>
	.main{
		/*width:414px;*/
		height: 773px;
		background-color:white;
	}
	
	.head{
		width:100%;
		height: 180px;
		background-color: #0099FF;
	}
	
	.head_background{
		position: absolute;
		z-index: 1;
		height:180px;
		/*width:100%;*/
	}
	
	.head_img{
		z-index: 1000;
		margin-bottom: 125px;
		position: absolute;
		width: 100px;
		height: 100px;
		top: 46px;
		left: 36%;
		border-radius: 10px;
	}
	
	.middle_cs{
		width:100%;
		height: 140px;
		background-color:#00FFFF;
	}
	
	.middle_left{
		float:left;
		width: 110px;
		height: 140px;
		background-color:#F0FFFF;
		text-align: center;
	}
	
	.middle_left_text{
		color:red;
		font-size: 61px;
	}
	
	.middle_left_text_bottom{
		font-size: 25px;
	}
	
	.middle_right{
		background-color: #009900;
		height: 140px;
	}
	
	.middle_right_top{
		height: 69.5px;
		background-color:whitesmoke;
	}
	
	.middle_right_bottom{
		height: 69.5px;
		background-color:whitesmoke;
	}
	
	.middle_two{
		margin-top:20px;
		width:100%;
		height: 60px;
		background-color: #E60000;
		position:relative;
		text-align: center;
		line-height: 60px;
		color:white;
		font-size: 30px;
	}
	
	.middle_two_span{
		margin-top:20px;
		height: 60px;
		text-align: center;
		font-size: 20px;
	}
	
	.middle_three{
		margin-top:20px;
		width:100%;
		height: 60px;
		position:relative;
		/*text-align: center;*/
		line-height: 60px;
	}
	
	.button{
		width:100%;
		height: 50px;
		background-color:#ED3F14;
		margin: auto;
		top: 0;
		left: 0;
		bottom: 0;
		right: 0;
		position: absolute;
	}
	
	.bottom{
		width:100%;
		height: 60px;
		background-color: lightpink;
		text-align: center;
		line-height: 60px;
		border-radius: 7px;
		font-size: 20px;
		font-weight: bold;
	}
	
</style>