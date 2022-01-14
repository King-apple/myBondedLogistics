<template>
  <div>
    <div   @click="back()">
      <el-button
      class="button_style"
        icon="el-icon-back"
        type="danger"
        >返回</el-button>
        
    </div>
    <div class="main">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="签到记录" name="first">
          <meetpeople />
        </el-tab-pane>
        <el-tab-pane label="签到页配置" name="second">
        	<meetpage></meetpage>
        </el-tab-pane>
        <!--<el-tab-pane label="签到成功页配置" name="third"
          >签到成功页配置</el-tab-pane
        >-->
        <el-tab-pane label="大屏配置" name="fourth">
        	<meetbigview />
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script>
import Meetpeople from "./meetpeople";
import Meetpage from "./meetpage";
import Meetbigview from "./meetbigview";
export default {
  components: {
    Meetpeople,
    Meetpage,
    Meetbigview,
  },
  data() {
    return {
      activeName: "second",
    };
  },
  created () {
    	console.log(this.$route.query.id)
    	if(this.$route.query.id){
    		// this.dataForm.meetingId=this.$route.query.id
    		this.activeName=this.$route.query.activeName
    	}
      // this.getDataList()
    },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
    back(){
      this.$router.go(-1);
    }
  },
};
</script>
<style>
.main {
  padding: 19px;
}
.button_style{
    margin-left: 94%;
}
</style>