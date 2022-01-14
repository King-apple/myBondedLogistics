<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="签到名称" prop="meetName">
      <el-input v-model="dataForm.meetName" placeholder="签到名称"></el-input>
    </el-form-item>
    <el-form-item label="签到时间">
				<el-date-picker v-model="value1" type="datetimerange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
				</el-date-picker>
		</el-form-item>
    <el-form-item label="签到凭证" prop="voucher">
       <el-checkbox v-model="dataForm.voucher">手机号</el-checkbox>
      <!-- <el-input v-model="dataForm.voucher" placeholder="0:不使用手机， 1:使用手机"></el-input> -->
    </el-form-item>
    <el-form-item label="是否允许现场报名" prop="onRegistration">
      <el-radio v-model="dataForm.onRegistration" label="1">是</el-radio>
      <el-radio v-model="dataForm.onRegistration" label="0">否</el-radio>
      <!-- <el-input v-model="dataForm.onRegistration" placeholder="是否现场报名 0：否 1：是"></el-input> -->
    </el-form-item>
    <el-form-item label="是否完善信息" prop="information">
       <el-radio v-model="dataForm.information" label="1">是</el-radio>
      <el-radio v-model="dataForm.information" label="0">否</el-radio>
      <!-- <el-input v-model="dataForm.information" placeholder="是否完善信息：0：否  1：是"></el-input> -->
    </el-form-item>
    <el-form-item label="是否启用手机验证码" prop="phoneVerify">
      <el-radio v-model="dataForm.phoneVerify" label="1">是</el-radio>
      <el-radio v-model="dataForm.phoneVerify" label="0">否</el-radio>
      <!-- <el-input v-model="dataForm.phoneVerify" placeholder="是否手机验证码  0：否  1：是"></el-input> -->
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" v-if="canSubmit">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
    import {getObj, addObj, putObj} from '@/api/meetingsystem/meetingmanage'
    import { dateToString } from "@/util/date";
    export default {
    data () {
      return {
        visible: false,
        canSubmit: false,
        dataForm: {
          id: 0,
          meetName: '',
          startTime: '',
          endTime: '',
          voucher: true,
          onRegistration: '0',
          information: '0',
          phoneVerify: '0',
          meetingId: '',
          img: '',
          insDate: ''
        },
        dataRule: {
          meetName: [
            { required: true, message: '签到名称不能为空', trigger: 'blur' }
          ],
          startTime: [
            { required: true, message: '签到开始时间不能为空', trigger: 'blur' }
          ],
          endTime: [
            { required: true, message: '签到结束时间不能为空', trigger: 'blur' }
          ]
        },
        value1: [],
      }
    },
    methods: {
      init (id,meetingId) {
        this.dataForm.id = id || 0;
        this.dataForm.meetingId = meetingId || 0;
        this.visible = true;
        this.canSubmit = true;
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            getObj(this.dataForm.id).then(response => {
                this.dataForm = response.data.data
                this.dataForm = response.data.data
                var arr1 = response.data.data.startTime.split(" ");
                var sdate = arr1[0].split('-');
                var stime = arr1[1].split(':');
                var date = new Date(sdate[0], sdate[1] - 1, sdate[2], stime[0], stime[1], stime[2]);

                var arr2 = response.data.data.endTime.split(" ");
                var sdate2 = arr2[0].split('-');
                var stime2 = arr2[1].split(':');
                var date2 = new Date(sdate2[0], sdate[1] - 1, sdate[2], stime2[0], stime2[1], stime2[2]);
                this.value1 = [date, date2]
                if(this.dataForm.voucher == "1"){
                	this.dataForm.voucher = true;
                }
                
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.canSubmit = false;
            this.dataForm.startTime = dateToString(this.value1[0])
						this.dataForm.endTime = dateToString(this.value1[1])
            
            if(this.dataForm.voucher){
              this.dataForm.voucher="1"
            }else{
              this.dataForm.voucher="0"
            }

            console.log(this.dataForm)
            if (this.dataForm.id) {
                putObj(this.dataForm).then(data => {
                    this.$notify.success('修改成功')
                    this.visible = false
                    this.$emit('refreshDataList')
                }).catch(() => {
                    this.canSubmit = true;
                });
            } else {
            	delete this.dataForm["id"]
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
