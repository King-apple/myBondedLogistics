<template>
  <div class="talbe-index">
    <vxe-pulldown class="edit-down-pulldown" ref="xDown" transfer>
      <template>
        <vxe-input :size="sizenow" class="edit-down-input" v-model="obj[property]" clearable @input="keyupEvent" suffix-icon="el-icon-more" @click="clickEvent" @suffix-click="suffixClick"></vxe-input>
      </template>
      <template v-slot:dropdown>
        <div class="edit-down-wrapper">
          <vxe-grid
            highlight-hover-row
            auto-resize
            height="auto"
            :loading="loading"
            :pager-config="tablePage"
            :data="tableData"
            :columns="tableColumn"
            @cell-click="selectEvent"
            @page-change="pageChangeEvent">
          </vxe-grid>
        </div>
      </template>
    </vxe-pulldown>
</div>
</template>

<script>
import {
  fetchList
} from "@/api/basecod/basecod";
export default {
  name: 'EditDown',
   props:[
    "objs","property","tableColumns","urlapi","returnApi","sizenow"
  ],
  data () {
    return {
      obj: null,
      column: null,
      loading: false,
      tableData: [],
      url:null,
      tableColumn: [],
      tablePage: {
        total: 0,
        currentPage: 1,
        pageSize: 10
      }
    }
  },
  watch: {
    params () {
      this.load()
    }
  },
  created () {
    this.load()
  },
  methods: {
    load () {
      this.obj =this.objs
      this.tableColumn=this.tableColumns
      this.url=this.urlapi;
      this.getData().then(data => {
        this.tableData = data
      })
    },
    getData () {
      return new Promise(resolve => {
        setTimeout(() => {
          fetchList(
             Object.assign(
             {
               current: this.tablePage.currentPage,
               size: this.tablePage.pageSize,
               codeName:this.objs[this.property]
             }
             ),this.url
           )
         .then(response => {
          resolve(response.data.data.records);
           this.tablePage.total = response.data.data.total;
           this.loading = false;
          })
         .catch(() => {
           this.loading = false;
          });
         }, 100)
      })
    },
    clickEvent () {
      if(!this.$refs.xDown.isPanelVisible()){
 this.tablePage.currentPage=1
      this.keyupEvent();
      this.$refs.xDown.showPanel()
      }
    },
    keyupEvent () {
      const cellValue = this.objs[this.property]
      this.loading = true
      this.getData().then(data => {
        this.loading = false
        if (cellValue) {
          this.tableData = data
        } else {
          this.tableData = data
        }
      })
    },
    suffixClick () {
      this.$refs.xDown.togglePanel()
    },
    pageChangeEvent ({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage
      this.tablePage.pageSize = pageSize
      this.loading = true
      this.getData().then(data => {
        this.loading = false
        this.tableData = data
      })
    },
    selectEvent (params) {
      let returnApis=this.returnApi;
      for( var i in returnApis){
        this.objs[returnApis[i].returnName]=params.row[returnApis[i].field]
      }
      this.$refs.xDown.hidePanel()
    }
  }
}
</script>

<style lang="scss" scoped>

.edit-down-pulldown {
  z-index:10;
  width: 100%;
}
.vxe-header--column{
  background-color:#86c1ff;
}
.edit-down-wrapper {
  width: 600px;
  height: 300px;
  background-color: #fff;
  border: 1px solid #dcdfe6;
  box-shadow: 0 0 6px 2px rgba(0, 0, 0, 0.1);
}
</style>
