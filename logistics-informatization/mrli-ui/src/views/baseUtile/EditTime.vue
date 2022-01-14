<template>
  <div class="talbe-index">
    <template>
      <vxe-input
        :type="type"
        :size="sizenow"
        class="edit-down-input"
        v-model="objs[property]"
        clearable
        ref="editTime"
        @blur="inputChange"
        @dblclick="changeType"
        :placeholder="placeholder"
        :readonly="readonly"
      >
      </vxe-input>
    </template>
  </div>
</template>

<script>
export default {
  name: "EditDown",
  props: [
    "objs",
    "property",
    "pageSize",
    "valueFormat",
    "inputType",
    "sizenow",
    "ifSs"
  ],
  data() {
    return {
      obj: {},
      column: null,
      loading: false,
      tableData: [],
      url: null,
      tableColumn: [],
      heightnow: "auto",
      widthnow: "600",
      readonly: false,
      type: "input",
      placeholder: "请录入时间格式，双击后可以对时间进行选择"
    };
  },
  watch: {
    params() {
      this.load();
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      this.obj = this.objs;
      if (this.inputType != "datetimeInput") {
        this.type = this.inputType;
        this.placeholder = "请选择时间";
        this.readonly = true;
      }
    },
    inputChange({ value }) {
      if (this.inputType == "datetimeInput") {
        if (value != null && value != "" && value.indexOf("-") == -1) {
          if (this.valueFormat != null) {
            if (this.valueFormat.length != value.length) {
              this.$message.error("时间格式不对");
              this.objs[this.property] = null;
              return;
            }
            if (this.valueFormat.length == 12) {
              //格式化时间
              this.objs[this.property] =
                value.substring(0, 4) +
                "-" +
                value.substring(4, 6) +
                "-" +
                value.substring(6, 8) +
                " " +
                value.substring(8, 10) +
                ":" +
                value.substring(10, 12);
              if (this.ifSs!=null&&this.ifSs=='ok') {
                this.objs[this.property] =
                  this.objs[this.property] + ":" + "00";
              }
            }
            if (this.valueFormat.length == 10) {
              //格式化时间
              this.objs[this.property] =
                value.substring(0, 4) +
                "-" +
                value.substring(4, 6) +
                "-" +
                value.substring(6, 8) +
                " " +
                value.substring(8, 10);
            }
            if (this.valueFormat.length == 8) {
              //格式化时间
              this.objs[this.property] =
                value.substring(0, 4) +
                "-" +
                value.substring(4, 6) +
                "-" +
                value.substring(6, 8);
            }
            if (this.valueFormat.length == 6) {
              //格式化时间
              this.objs[this.property] =
                value.substring(0, 4) + "-" + value.substring(4, 6);
            }
          }
        }
      }
    },
    changeType() {
      if (this.inputType == "datetimeInput") {
        if (this.type == "datetime") {
          this.type = "input";
          this.placeholder = "请录入时间格式，双击后可以对时间进行选择";
          this.readonly = false;
        } else if (this.type == "input") {
          this.placeholder = "已经切换至选择时间";
          this.type = "datetime";
          //this.readonly = true;
        }
      }
    }
  }
};
</script>
<style lang="scss" scoped>
.edit-down-pulldown {
  z-index: 10;
  width: 100%;
}
</style>


