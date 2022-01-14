<template>
  <span>
    <i class="fa fa-exclamation-triangle fa-lg" @click="handleOpen"></i>
    <el-dialog title="需求新增" :visible.sync="box" width="30%" append-to-body>
      <el-form :model="form" ref="forms" label-width="80px">
        <el-form-item label="菜单名称" prop="menu" :rules="[{ required: true, message: '菜单名称不能为空'}]">
          <el-input v-model="form.menu" placeholder="请输入菜单名称"></el-input>
        </el-form-item>
        <el-form-item label="菜单路径" prop="menuUrl" :rules="[{ required: true, message: '菜单路径不能为空'}]">
          <el-input v-model="form.menuUrl" placeholder="请输入菜单路径"></el-input>
        </el-form-item>
        <el-form-item label="问题类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择">
            <el-option
              v-for="item in typeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="优先级" prop="priority">
          <el-select v-model="form.priority" placeholder="请选择">
            <el-option
              v-for="item in priorityOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="问题描述" prop="detail" :rules="[{ required: true, message: '问题描述不能为空'}]">
          <el-input v-model="form.detail" type="textarea" placeholder="请输入问题描述"></el-input>
        </el-form-item>
        <el-form-item label="文件附件" prop="fileUrl">
          <!-- <el-input v-model="form.fileUrl" placeholder="请文件附件"></el-input> -->
          <el-upload
            accept="image/jpeg, image/gif, image/png, image/jpg"
            multiple
            :limit="3"
            :headers="headers"
            :on-exceed="handleExceed"
            :on-error="uploadError"
            :on-success="uploadSuccess"
            :before-upload="onBeforeUpload"
            :file-list="fileList"
            action="/admin/sys-file/upload"
            ref="my-upload"
          >
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">请上传图片格式文件</div>
          </el-upload>
        </el-form-item>
        <el-form-item
          label="联系人"
          prop="contactPerson"
          :rules="[{ required: true, message: '联系人不能为空'}]"
        >
          <el-input v-model="form.contactPerson" placeholder="请输入联系人"></el-input>
        </el-form-item>
        <el-form-item
          label="联系电话"
          prop="contactTel"
          :rules="[{ required: true, message: '联系电话不能为空'}]"
        >
          <el-input v-model="form.contactTel" placeholder="请输入联系电话"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleSave" :loading="loadingbut">{{loadingbuttext}}</el-button>
        <el-button type="primary" @click="closeWindows">取 消</el-button>
      </span>
    </el-dialog>
  </span>
</template>

<script>
import { validatenull } from "@/util/validate";
import { mapGetters } from "vuex";
import request from "@/router/axios";
import store from "@/store";
export function putObj(obj) {
  return request({
    url: "/admin/sysfixme/",
    method: "post",
    data: obj
  });
}
export default {
  name: "top-bug",
  data() {
    return {
      headers: {
        Authorization: "Bearer " + store.getters.access_token
      },
      box: false,
      priorityOptions: [
        {
          value: "1",
          label: "普通"
        },
        {
          value: "2",
          label: "重要"
        },
        {
          value: "3",
          label: "紧急"
        }
      ],
      typeOptions: [
        {
          value: "1",
          label: "BUG"
        },
        {
          value: "2",
          label: "需求"
        }
      ],
      form: {
        priority: "1",
        type: "1",
        menu: "",
        menuUrl: window.location.href  ,
        detail: "",
        fileUrl: "",
        contactPerson: "",
        contactTel: ""
      },
      limitNum: 3,
      fileList: [],
      loadingbut: false, //审核提交加载中
      loadingbuttext: "确 定"
    };
  },
  created() {},
  mounted() {},
  computed: {},
  props: [],
  methods: {
    handleOpen() {
      this.box = true;
      return;
    },
    handleSave: function(form, done) {
      this.$refs["forms"].validate(valid => {
        if (valid) {
          this.loadingbut = true;
          this.loadingbuttext = "提交中...";
          putObj(this.form)
            .then(data => {
              this.$message.success("提交成功");
              this.loadingbut = false;
              this.loadingbuttext = "确 定";
              this.$refs["forms"].resetFields();
              this.$refs["my-upload"].clearFiles();
              this.box = false;
              done();
            })
            .catch(() => {
              this.loadingbut = false;
              this.loadingbuttext = "确 定";
            });
        } else {
          this.$message({
            message: "请检查必填项",
            type: "error"
          });
          return false;
        }
      });
    },
    // uploadSuccess(res, file) {
    //   // this.form.fileUrl = URL.createObjectURL(file.raw);
    //   // this.ruleForm2.avatar = res.data.url
    //   this.form.fileUrl = res.data.url
    // },
    handleExceed(files, fileList) {
      this.$message.warning("只能选择3个文件!");
    },
    // 文件状态改变时的钩子
    // fileChange(res,file, fileList) {
    //   alert(res);
    //   console.log(res);
    //   console.log("change");
    //   console.log(file);
    //   // this.form.fileUrl = file.raw;
    //   this.form.fileUrl = res.data.url
    //   console.log(fileList);
    // },
    // 上传文件之前的钩子, 参数为上传的文件,若返回 false 或者返回 Promise 且被 reject，则停止上传
    onBeforeUpload(file) {
      console.log("before upload");
      console.log(file);
      // let extension = file.name.substring(file.name.lastIndexOf(".") + 1);
      const isIMG =
        file.type === "image/jpg" ||
        file.type === "image/jpeg" ||
        file.type === "image/png";
      let size = file.size / 1024 / 1024;
      // alert(isIMG);
      // if (extension != 'png'||'jpg'||'gif'||'jpeg') {
      if (!isIMG) {
        this.$notify.warning({
          title: "警告",
          message: `只能上传图片格式（JPEG，gif，png，jpg）的文件`
        });
      }
      if (size > 1) {
        this.$notify.warning({
          title: "警告",
          message: `文件大小不得超过1M`
        });
      }
    },
    // 文件上传成功时的钩子
    uploadSuccess(res, file, fileList) {
      this.form.fileUrl = res.data.url + ";" + this.form.fileUrl;
      this.$notify.success({
        title: "成功",
        message: `文件上传成功`
      });
    },
    // 文件上传失败时的钩子
    uploadError(err, file, fileList) {
      this.$notify.error({
        title: "错误",
        message: `文件上传失败`
      });
    },
    closeWindows: function() {
      this.$refs["forms"].resetFields();
      this.$refs["my-upload"].clearFiles();
      this.box = false;
    }
  },

  components: {}
};
</script>

<style lang="scss" scoped>
</style>
