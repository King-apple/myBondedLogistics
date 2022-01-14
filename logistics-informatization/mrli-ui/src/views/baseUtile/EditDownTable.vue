<template>
  <div class="talbe-index">
    <vxe-pulldown class="edit-down-pulldown" ref="xDown" transfer >
      <template>
        <vxe-input
          class="edit-down-input"
          v-model="row[column.property]"
          clearable
          @change="keyupEvent"
          suffix-icon="el-icon-more"
          @click="clickEvent"
           @clear="clearAll"
           :size="sizenow"
          @suffix-click="suffixClick"
          @keyup="keyUpEnter"
        ></vxe-input>
      </template>
      <template v-slot:dropdown>
        <div class="edit-down-wrapper">
          <vxe-grid
            highlight-hover-row
            show-overflow
            auto-resize
            height="auto"
            :size="sizenow"
            :loading="loading"
            :pager-config="tablePage"
            :data="tableData"
            :columns="tableColumn"
            @cell-click="selectEvent"
            @page-change="pageChangeEvent"
          ></vxe-grid>
        </div>
      </template>
    </vxe-pulldown>
  </div>
</template>

<script>
import { fetchList } from "@/api/basecod/basecod";
export default {
  name: "EditDownTable",
  props: {
    params: Object,
    renderOpts: Object,
  },
  data() {
    return {
      row: null,
      column: null,
      loading: false,
      tableData: [],
      url: null,
      tableColumn: [],
      tablePage: {
        total: 0,
        currentPage: 1,
        pageSize: 10
      },
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
      const { row, column } = this.params;
      const { renderOpts } = this;
      const { props = {} } = renderOpts;
      this.row = row;
      this.column = column;
      this.tableColumn = props.tableColumn;
      this.url = props.urlapi;
      this.sizenow=props.sizenow;
      this.getData().then(data => {
        this.tableData = data;
      });
    },
    keyUpEnter(value,$event){
      var enter=$event;
      if(enter.key=='Enter'){
          this.clickEvent();
      }
    },
    getData() {
      return new Promise(resolve => {
        setTimeout(() => {
          fetchList(
            Object.assign({
              current: this.tablePage.currentPage,
              size: this.tablePage.pageSize,
              codeName: this.row[this.column.property]
                ? this.row[this.column.property].replace(/\s*/g, "")
                : ""
            }),
            this.url
          )
            .then(response => {
              resolve(response.data.data.records);
              this.tablePage.total = response.data.data.total;
              this.loading = false;
            })
            .catch(() => {
              this.loading = false;
            });
        }, 100);
      });
    },
    clickEvent() {
      if (!this.$refs.xDown.isPanelVisible()) {
        this.tablePage.currentPage = 1;
        this.keyupEvent();
        this.$refs.xDown.showPanel();
      }
    },
    keyupEvent() {
      const { row, column } = this;
      const cellValue = row[column.property];
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
    clearAll() {
     const { renderOpts, row, column } = this;
      const { props = {} } = renderOpts;
      let returnApi = props.returnApi;
      for (var i in returnApi) {
        row[returnApi[i].returnName] = null;
      }
    },
    selectEvent(params) {
      const { renderOpts, row, column } = this;
      const { props = {} } = renderOpts;
      let returnApi = props.returnApi;
      for (var i in returnApi) {
        row[returnApi[i].returnName] = params.row[returnApi[i].field];
      }
      if (props.returnfaction != null && props.returnfaction != undefined) {
        props.returnfaction(params.row);
      }
      this.$refs.xDown.hidePanel();
    }
  }
};
</script>

<style lang="scss" scoped>
.edit-down-pulldown {
  z-index: 9999 !important;
  width: 100%;
}
.edit-down-wrapper {
  width: 600px;
  height: 300px;
  background-color: #fff;
  border: 1px solid #dcdfe6;
  box-shadow: 0 0 6px 2px rgba(0, 0, 0, 0.1);
}
</style>
