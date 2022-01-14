<template>
</template>

<script>
	import {getByMeetingId} from '@/api/meetingsystem/meetingpagesetting'
	import {getWxInfo} from '@/api/meetingsystem/wxInfo'
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
        dataForm:{},
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
		urltest: "",
		obj:{},
		wx_head: '',
		head_background: '',
		manageInfo: {},
		peopleCount: 0,
		timeOver:false,
		authenticationFlg:false,
      }
    },
    created () {
    	var rrr = decodeURIComponent(window.location.search);
    	let local = encodeURIComponent(window.location.href);//获取当前url
    	this.urltest = rrr+"__++++__"+local
    	this.value = this.$route.query.param
    	this.obj = this.getUrlCode()
    	if(this.$route.query.id){
    		this.dataForm.meetingId=this.$route.query.id
    		this.upload.meetingId = this.$route.query.id
    	}
      this.getDataList()
    },
    computed: {
     
    },
    methods: {
    	
    	getDataList(){
    		this.dataList.meetingId = this.$route.query.param
    		//this.obj.code
    		getWxInfo(this.dataList.meetingId,this.obj.code).then((res) =>{
    			console.log(res)
    			this.$router.push({path:'/mobile',query: {param:this.dataList.meetingId ,wxInfo:JSON.stringify(res.data.data.wxInfo)}})
    		})
    		
    	},
    	getUrlCode() { // 截取url中的code方法
                var url = window.location.search
                this.winUrl = url
                var theRequest = new Object()
                if (url.indexOf("?") != -1) {
                    var str = url.substr(1)
                    var strs = str.split("&")
                    for(var i = 0; i < strs.length; i ++) {
                        theRequest[strs[i].split("=")[0]]=(strs[i].split("=")[1])
                    }
                }
                return theRequest
            }

    }
    	
  }
</script>

<style>
</style>