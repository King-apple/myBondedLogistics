<template>
	<div>
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    
    <el-form-item label="标题" prop="title">
      <el-input v-model="dataForm.title" placeholder="标题"></el-input>
    </el-form-item>
    <el-form-item label="标题大小" prop="titleSize">
      <el-input v-model="dataForm.titleSize" placeholder="标题大小"></el-input>
    </el-form-item>
    <el-form-item label="标题颜色" prop="color">
      <el-input type="color" v-model="dataForm.color" placeholder="标题大小" style="width: 97px;"></el-input>
    </el-form-item>
    
    <el-form-item label="图片路径" prop="url">
    	<el-upload :headers="headers" :show-file-list="false" :on-success="handleAvatarSuccess" class="avatar-uploader" action="/meetingsystem/meetingfile/upload">
			<img v-if="dataForm.url" id="avatar" :src="avatarUrl" class="avatar">
			<i v-else class="el-icon-plus avatar-uploader-icon" />
		</el-upload>
      <!--<el-input v-model="dataForm.url" placeholder="图片路径"></el-input>-->
    </el-form-item>
    <!--<el-form-item label="会议外键id" prop="meetingId">
      <el-input v-model="dataForm.meetingId" placeholder="会议外键id"></el-input>
    </el-form-item>-->
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
</div>
</template>

<script>
    import {getObj, addObj, putObj, getObjForMeetingId} from '@/api/meetingsystem/meetingbigview'
    import {getObj as getManage} from '@/api/meetingsystem/meetingactivitylist'
	import store from '@/store'
    export default {
    data () {
      return {
        dataForm: {
          url: '',
          title: '',
          titleSize: '',
          meetingId: this.$route.query.id
        },
        headers: {
					'Authorization': 'Bearer ' + store.getters.access_token,
				},
		avatarUrl: '',
        dataRule: {
          url: [
            { required: true, message: '图片路径不能为空', trigger: 'blur' }
          ],
//        title: [
//          { required: true, message: '标题不能为空', trigger: 'blur' }
//        ],
          titleSize: [
            { required: true, message: '标题大小不能为空', trigger: 'blur' }
          ],
          meetingId: [
            { required: true, message: '会议外键id不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    created() {
			this.getDataList()
		},
    methods: {
    	getDataList() {
				getObjForMeetingId(this.dataForm.meetingId).then(response => {
					console.log(response)
					if(response.data.data) {
						this.dataForm = response.data.data
						this.avatarUrl = window.location.origin + "/meetingsystem/meetingfile/getimg/sign/"+response.data.data.url
					}else{
						getManage(this.dataForm.meetingId).then((res) => {
							console.log(res)
							this.dataForm.title = res.data.data.title
							this.dataForm.url = "default.png"
							this.avatarUrl = window.location.origin + "/meetingsystem/meetingfile/getimg/sign/default.png"
							this.dataForm.titleSize="50"
							this.dataForm.color="#FFFFFF"
						})
					}

				})
			},
			handleAvatarSuccess(res, file) {
				console.log(res)
				console.log(file)
				this.avatarUrl = URL.createObjectURL(file.raw)
				this.dataForm.url = res.data.fileName
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
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.canSubmit = false;
            
            console.log(this.dataForm)
            if(!this.dataForm.color){
            	this.dataForm.color = "#000000"
            }
            if (this.dataForm.id) {
                putObj(this.dataForm).then(data => {
                    this.$notify.success('修改成功')
                    this.$emit('refreshDataList')
                }).catch(() => {
                    this.canSubmit = true;
                });
            } else {
                addObj(this.dataForm).then(data => {
                    this.$notify.success('添加成功')
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