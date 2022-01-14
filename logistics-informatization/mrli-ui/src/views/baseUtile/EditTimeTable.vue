<template>
  <div class="talbe-index">
    <template>
      <vxe-input
        :type="type"
        :size="sizenow"
        class="edit-down-input"
        v-model="row[column.property]"
        clearable
        ref="editTimeTable"
        @blur="inputChange"
        @dblclick="changeType"
        placeholder="请录入时间格式，双击后可以选择时间"
      >
      </vxe-input>
    </template>
  </div>
</template>

<script>
import moment from "moment";
export default {
  name: "EditDown",
  props: {
    params: Object,
    renderOpts: Object
  },
  data() {
    return {
      obj: {},
      row: null,
      column: null,
      type: "input",
      loading: false,
      tableData: [],
      url: null,
      sizenow: "mini",
      props: null,
      heightnow: "auto",
      widthnow: "600",
      editdownwrapper: {
        width: "600px"
      }
    };
  },
  watch: {
    params() {}
  },
  created() {
    const { row, column } = this.params;
    const { renderOpts } = this;
    const { props = {} } = renderOpts;
    this.row = row;
    this.column = column;
    this.props = props;
  },
  methods: {
    inputChange({ value }) {
      if (this.props.type == "datetimeInput") {
        if (value != null && value != "" && value.indexOf("-") == -1) {
          if (this.props.valueFormat != null) {
            if (this.props.valueFormat.length != value.length) {
              this.$message.error("时间格式不对");
              this.row[this.column.property] = null;
              return;
            }
            if (this.props.valueFormat == "MMDDHH") {
              //默认填上时间
              let datenow = new Date();
              var year = datenow.getFullYear().toString();
              if(Number(value.substring(0, 2))<Number((datenow.getMonth() + 1).toString())){
                year=parseInt(year)+1;
              }
              this.row[this.column.property] =
                year +
                "-" +
                value.substring(0, 2) +
                "-" +
                value.substring(2, 4) +
                " " +
                value.substring(4, 6)+":00";
              return;
            }
            if (this.props.valueFormat.length == 12) {
              //格式化时间
              this.row[this.column.property] =
                value.substring(0, 4) +
                "-" +
                value.substring(4, 6) +
                "-" +
                value.substring(6, 8) +
                " " +
                value.substring(8, 10) +
                ":" +
                value.substring(10, 12);
            }
            if (this.props.valueFormat.length == 10) {
              //格式化时间
              this.row[this.column.property] =
                value.substring(0, 4) +
                "-" +
                value.substring(4, 6) +
                "-" +
                value.substring(6, 8) +
                " " +
                value.substring(8, 10);
            }
            if (this.props.valueFormat.length == 8) {
              //格式化时间
              this.row[this.column.property] =
                value.substring(0, 4) +
                "-" +
                value.substring(4, 6) +
                "-" +
                value.substring(6, 8);
            }
            if (this.props.valueFormat.length == 6) {
              //格式化时间
              this.row[this.column.property] =
                value.substring(0, 4) + "-" + value.substring(4, 6);
            }
          }
        }
      }
    },
    changeType() {
      if (this.type == "datetime") {
        this.type = "input";
      } else if (this.type == "input") {
        this.type = "datetime";
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
.vxe-header--column {
  background-color: #86c1ff;
}
.edit-down-wrapper {
  // height: 300px;
  background-color: #fff;
  border: 1px solid #dcdfe6;
  box-shadow: 0 0 6px 2px rgba(0, 0, 0, 0.1);
}
</style>
