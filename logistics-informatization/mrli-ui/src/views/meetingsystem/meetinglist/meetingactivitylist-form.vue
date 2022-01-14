<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-form-item label="标题" prop="title">
				<el-input v-model="dataForm.title" placeholder="会议活动标题"></el-input>
			</el-form-item>
			<el-form-item label="举办时间" prop="value1">
				<el-date-picker v-model="value1" type="datetimerange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
				</el-date-picker>
			</el-form-item>
			<!--<el-form-item label="会议开始时间" prop="startTime">
      <el-input v-model="dataForm.startTime" placeholder="会议开始时间"></el-input>
    </el-form-item>
    <el-form-item label="会议结束时间" prop="endTime">
      <el-input v-model="dataForm.endTime" placeholder="会议结束时间"></el-input>
    </el-form-item>-->
			<el-form-item label="地址" prop="address">
				<el-input v-model="dataForm.address" placeholder="会议地点"></el-input>
			</el-form-item>
			<!--<el-form-item label="会议图片" prop="img">
				<el-input v-model="dataForm.img" placeholder="会议图片"></el-input>
			</el-form-item>-->

			<el-form-item label="会议图片">
				<el-upload :headers="headers" :show-file-list="false" :on-success="handleAvatarSuccess" class="avatar-uploader" action="/meetingsystem/meetingfile/upload">
					<img v-if="dataForm.img" id="avatar" :src="avatarUrl" class="avatar">
					<i v-else class="el-icon-plus avatar-uploader-icon" />
				</el-upload>
			</el-form-item>

			<el-form-item label="简介" prop="remark">
				<el-input v-model="dataForm.remark" placeholder="会议简介"></el-input>
			</el-form-item>
			<!--<el-form-item label="插入时间" prop="insDate">
      <el-input v-model="dataForm.insDate" placeholder="插入时间"></el-input>
    </el-form-item>-->
		</el-form>
		<span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" v-if="canSubmit">确定</el-button>
    </span>
	</el-dialog>
</template>

<script>
	import { getObj, addObj, putObj } from '@/api/meetingsystem/meetingactivitylist.js'
	import { dateToString } from "@/util/date";
	import { getImage } from '@/api/meetingsystem/meetingfile.js'
	import store from '@/store'
	import { handleImg, handleDown } from '@/util/util'
	export default {
		data() {
			return {
				visible: false,
				canSubmit: false,
				dataForm: {
					
					title: '',
					startTime: '',
					endTime: '',
					address: '',
					img: '',
					remark: '',
					insDate: ''
				},
				dataRule: {
					title: [{
						required: true,
						message: '会议活动标题不能为空',
						trigger: 'blur'
					}],
					//        startTime: [
					//          { required: true, message: '会议开始时间不能为空', trigger: 'blur' }
					//        ],
//			        value1: [
//			          { required: true, message: '会议时间不能为空', trigger: 'blur' }
//			        ],
					address: [{
						required: true,
						message: '会议地点不能为空',
						trigger: 'blur'
					}],
					img: [{
						required: true,
						message: '会议图片不能为空',
						trigger: 'blur'
					}],
					remark: [{
						required: true,
						message: '会议简介不能为空',
						trigger: 'blur'
					}],
					insDate: [{
						required: true,
						message: '插入时间不能为空',
						trigger: 'blur'
					}]
				},
				value1: [],
				avatarUrl: '',
				headers: {
			        'Authorization': 'Bearer ' + store.getters.access_token,
			      },
			}
		},
		created() {
			//  	value1: [new Date(2000, 10, 10, 10, 10), new Date(2000, 10, 11, 10, 10)],
		},
		methods: {
			init(id) {
				this.dataForm.img=""
				this.dataForm.id = id || 0;
				this.visible = true;
				this.canSubmit = true;
				this.$nextTick(() => {
					this.$refs['dataForm'].resetFields()
					this.dataForm.img = "default.png"
					this.avatarUrl = window.location.origin + "/meetingsystem/meetingfile/getimg/sign/default.png"
					if(this.dataForm.id) {
						getObj(this.dataForm.id).then(response => {
							this.dataForm.img = ""
							this.avatarUrl = ""
							
							this.dataForm = response.data.data
							var arr1 = response.data.data.startTime.split(" ");
							var sdate = arr1[0].split('-');
							var stime = arr1[1].split(':');
							var date = new Date(sdate[0], sdate[1] - 1, sdate[2], stime[0], stime[1], stime[2]);

							var arr2 = response.data.data.endTime.split(" ");
							var sdate2 = arr2[0].split('-');
							var stime2 = arr2[1].split(':');
							var date2 = new Date(sdate2[0], sdate[1] - 1, sdate[2], stime2[0], stime2[1], stime2[2]);
							
							this.avatarUrl = window.location.origin + "/meetingsystem/meetingfile/getimg/sign/"+response.data.data.img
							this.value1 = [date, date2]
						})
					}
				})
			},
			handleAvatarSuccess(res, file) {
				console.log(res)
				console.log(file)
				this.avatarUrl = URL.createObjectURL(file.raw)
				this.dataForm.img = res.data.fileName
			},
			// 表单提交
			dataFormSubmit() {
				
				if(this.value1.length!=2){
					this.$message.error("请填写会议举办时间！");
					return;
				}
				this.$refs['dataForm'].validate((valid) => {
					if(valid) {
						this.canSubmit = false;
						this.dataForm.startTime = dateToString(this.value1[0])
						this.dataForm.endTime = dateToString(this.value1[1])
						if(this.dataForm.id) {
							putObj(this.dataForm).then(data => {
								this.$notify.success('修改成功')
								this.visible = false
								this.$emit('refreshDataList')
							}).catch(() => {
								this.canSubmit = true;
							});
						} else {
							delete this.dataForm["id"]
							console.log(this.dataForm)
							addObj(this.dataForm).then(data => {
								this.$notify.success('添加成功')
								this.visible = false
								this.$emit('refreshDataList')
							}).catch(() => {
								this.canSubmit = true;
							});
						}
					}
				})
			}
		}
	}
</script>
<style>
	.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }

  .avatar-uploader-icon {
    font-size: 28px !important;
    color: #8c939d !important;
    width: 400px !important;
    height: 177px !important;
    line-height: 140px !important;
    text-align: center !important;
  }

  .avatar {
    width: 400px;
    height: 177px;
    display: block;
  }
</style>