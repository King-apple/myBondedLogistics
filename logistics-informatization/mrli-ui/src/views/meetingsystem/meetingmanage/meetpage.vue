<template>
	<div>
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-form-item label="背景图片" prop="phoneUrl">
				<el-upload :headers="headers" :show-file-list="false" :on-success="handleAvatarSuccess" class="avatar-uploader" action="/meetingsystem/meetingfile/upload">
					<img v-if="dataForm.phoneUrl" id="avatar" :src="avatarUrl" class="avatar">
					<i v-else class="el-icon-plus avatar-uploader-icon" />
				</el-upload>
				<!--<el-input v-model="dataForm.phoneUrl" placeholder="phoneUrl"></el-input>-->
			</el-form-item>
			<el-form-item label="是否显示头像" prop="headFlg">
				<el-radio v-model="dataForm.headFlg" label="1">是</el-radio>
				<el-radio v-model="dataForm.headFlg" label="0">否</el-radio>
				<!--<el-input v-model="dataForm.headFlg" placeholder="headFlg"></el-input>-->
			</el-form-item>
			<!--<el-form-item label="外键id" prop="meetingId">
      <el-input v-model="dataForm.meetingId" placeholder="外键id"></el-input>
    </el-form-item>-->
		</el-form>
		<span slot="footer" class="dialog-footer">
      <!--<el-button @click="visible = false">取消</el-button>-->
      <el-button type="primary" @click="dataFormSubmit()" >确定</el-button>
    </span>
	</div>
</template>

<script>
	import { fetchList, getObj, addObj, putObj } from '@/api/meetingsystem/meetingpagesetting'
	import store from '@/store'

	export default {
		data() {
			return {
				canSubmit: false,
				dataForm: {
					phoneUrl: '',
					headFlg: '0',
					meetingId: this.$route.query.id
				},
				headers: {
					'Authorization': 'Bearer ' + store.getters.access_token,
				},
				avatarUrl: '',
				dataRule: {
					phoneUrl: [{
						required: true,
						message: '背景图片不能为空',
						trigger: 'blur'
					}],
					headFlg: [{
						required: true,
						message: 'headFlg不能为空',
						trigger: 'blur'
					}],
					meetingId: [{
						required: true,
						message: '外键id不能为空',
						trigger: 'blur'
					}]
				}
			}
		},
		created() {
			this.getDataList()
		},
		methods: {
			// 获取数据列表
			getDataList() {
				
				getObj(this.dataForm.meetingId).then(response => {
					console.log(response)
					if(response.data.data) {
						this.dataForm = response.data.data
						this.avatarUrl = window.location.origin + "/meetingsystem/meetingfile/getimg/sign/"+response.data.data.phoneUrl
					} else {
						this.dataForm.phoneUrl = "default.png"
						this.avatarUrl = window.location.origin + "/meetingsystem/meetingfile/getimg/sign/default.png"
					}

				})
			},

			//    init (id) {
			//      this.dataForm.id = id || 0;
			//      this.visible = true;
			//      this.canSubmit = true;
			//      this.$nextTick(() => {
			//        this.$refs['dataForm'].resetFields()
			//        if (this.dataForm.id) {
			//          getObj(this.dataForm.id).then(response => {
			//              this.dataForm = response.data.data
			//          })
			//        }
			//      })
			//    },
			handleAvatarSuccess(res, file) {
				console.log(res)
				console.log(file)
				this.avatarUrl = URL.createObjectURL(file.raw)
				this.dataForm.phoneUrl = res.data.fileName
			},
			// 表单提交
			dataFormSubmit() {
				console.log(this.dataForm)
				this.$refs['dataForm'].validate((valid) => {
					if(valid) {

						this.canSubmit = false;
						if(this.dataForm.id) {
							putObj(this.dataForm).then(data => {
								this.$notify.success('修改成功')
								this.visible = false
								this.$emit('refreshDataList')
							}).catch(() => {
								this.canSubmit = true;
							});
						} else {
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