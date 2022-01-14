<template>
  <div class="talbe-index">
    <vxe-pulldown class="edit-down-pulldown" ref="xDown" transfer>
      <template>
        <vxe-input
          :size="sizenow"
          class="edit-down-input"
          v-model="obj[property]"
          ref="editDownInput"
          v-on:input="keyupEvent"
          suffix-icon="el-icon-more"
          @click="clickEvent"
          @suffix-click="suffixClick"
          readonly
        ></vxe-input>
      </template>
      <template v-slot:dropdown>
        <vxe-input
          :size="sizenow"
          class="edit-down-input"
          v-model="selectVal"
          v-on:input="keyupEvent"
        ></vxe-input>
        <div class="edit-down-wrapper" :style="editdownwrapper">
          <vxe-grid
            highlight-hover-row
            auto-resize
            :height="heightnow"
            :width="widthnow"
            border
            :size="sizenow"
            ref="xTable1"
            :loading="loading"
            :pager-config="tablePage"
            :data="tableData"
            :columns="tableColumn"
            :checkbox-config="{ checkStrictly: true }"
            @checkbox-change="selectEvent"
            @page-change="pageChangeEvent"
          ></vxe-grid>
        </div>
      </template>
    </vxe-pulldown>
  </div>
</template>

<script>
import { fetchList } from "@/api/basecod/basecod";
import { isNumber } from "@/util/util";
export default {
  name: "EditDown",
  props: [
    "objs", //与form一致
    "property", //当前数据的名称
    "returnlist", //返回的选中数组，如果是”[]“，说明返回以中间为参数的，单数据数组
    "tableColumns", //显示字段
    "urlapi", //后端url
    "seeName", //前端多选的选中显示哪个字段的内容
    "sizenow", //控件比例
    "height", //显示表格高度
    "dataId", //数据主键，用来判断选中非选中
    "width", //显示表格宽度
    "pageSize", //每页显示多少数据量
    "pageLayOutType" //分页空间的格式
  ],
  data() {
    return {
      obj: {},
      column: null,
      loading: false,
      tableData: [],
      url: null,
      selectVal: null,
      tableColumn: [{ type: "checkbox", width: 50 }],
      heightnow: "auto",
      widthnow: "600",
      tablePage: {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        layouts: [
          "Sizes",
          "PrevJump",
          "PrevPage",
          "Number",
          "NextPage",
          "NextJump",
          "FullJump",
          "Total"
        ]
      },
      editdownwrapper: {
        width: "600px"
      },
      disabledInput: false
    };
  },
  watch: {
    params() {
      this.load();
    }
  },
  created() {
    if (this.pageLayOutType != null) {
      if (this.pageLayOutType == "mini") {
        this.tablePage.layouts = [
          "Sizes",
          "PrevPage",
          "NextPage",
          "FullJump",
          "Total"
        ];
      }
    }
    if (this.disabled) {
      this.disabledInput = this.disabled;
    }
    if (this.height != null) {
      this.heightnow = this.height;
    } else {
      this.heightnow = "300";
    }
    if (this.width != null) {
      if (this.width < 200) {
        this.tablePage.layouts = ["PrevPage", "Number", "NextPage"];
      }
      this.editdownwrapper.width = this.width + "px";
    }
    if (this.pageSize != null) {
      this.tablePage.pageSize = Number(this.pageSize);
    }
    this.load();
  },
  methods: {
    load() {
      this.obj = this.objs;
      for (let i = 0; i < this.tableColumns.length; i++) {
        this.tableColumn.push(this.tableColumns[i]);
      }
      this.url = this.urlapi;
      this.getData().then(data => {
        this.tableData = data;
      });
    },
    getData() {
      if (this.url != null) {
        var selectVal = "";
        if (
          this.selectVal != null &&
          this.selectVal != "" &&
          this.selectVal != "undefined"
        ) {
          selectVal = this.selectVal.toString().replace(/\'/g, "");
        }
        return new Promise(resolve => {
          setTimeout(() => {
            fetchList(
              Object.assign({
                current: this.tablePage.currentPage,
                size: this.tablePage.pageSize,
                codeName: selectVal
              }),
              this.urlapi
            )
              .then(response => {
                resolve(response.data.data.records);
                this.tablePage.total = response.data.data.total;
                this.loading = false;
                 let returnlist = this.returnlist;
                let dataSelectList = this.objs[returnlist];
                let tableData = response.data.data.records;
                let dataId = this.dataId;
                for (var i in tableData) {
                  for (var n in dataSelectList) {
                    if (tableData[i][dataId] == dataSelectList[n][dataId]) {
                      this.$refs.xTable1.setCheckboxRow([tableData[i]], true);
                    }
                  }
                }
              })
              .catch(() => {
                this.loading = false;
              });
          }, 100);
        });
      }
    },
    clickEvent() {
      if (!this.$refs.xDown.isPanelVisible()) {
        this.tablePage.currentPage = 1;
        this.keyupEvent();
        this.$refs.xDown.showPanel();
      } else {
        this.$refs.xDown.showPanel();
      }
    },
    keyupEvent() {
      const cellValue = this.objs[this.property];
      this.loading = true;
      this.getData().then(data => {
        this.loading = false;
        if (cellValue) {
          this.tableData = data;
        } else {
          this.tableData = data;
        }
      });
    },
    suffixClick() {
      console.log("1");
      this.$refs.xDown.togglePanel();
    },
    pageChangeEvent({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage;
      this.tablePage.pageSize = pageSize;
      this.loading = true;
      this.getData().then(data => {
        this.loading = false;
        this.tableData = data;
      });
    },
    selectEvent({ checked, row }) {
      let returnlist = this.returnlist;
      let property = this.property;
      let seeName = this.seeName;
      let dataId = this.dataId;
      if (checked) {
        //选中执行
        if (
          this.objs[returnlist] == null ||
          this.objs[returnlist].length == 0
        ) {
          this.objs[returnlist] = [row];
        } else {
          this.objs[returnlist].push(row);
        }
      } else {
        //取消时候执行
        let dataList = this.objs[returnlist];
        let dateBak = [];
        for (let i = 0; i < dataList.length; i++) {
          if (row[dataId] != dataList[i][dataId]) {
            dateBak.push(dataList[i]);
          }
        }
        dataList = dateBak;
        this.objs[returnlist] = dataList;
      }
      this.objs[property] = null;
      for (let i = 0; i < this.objs[returnlist].length; i++) {
        if (this.objs[property] == null) {
          this.objs[property] = this.objs[returnlist][i][seeName];
        } else {
          this.objs[property] =
            this.objs[property] + "," + this.objs[returnlist][i][seeName];
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
