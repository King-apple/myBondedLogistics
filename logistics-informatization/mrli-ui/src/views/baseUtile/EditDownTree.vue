<template>
  <div class="talbe-index">
    <vxe-pulldown class="edit-down-pulldown"   ref="xDown" transfer>
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
          :disabled="disabled"
        ></vxe-input>
      </template>
      <template v-slot:dropdown>
          <vxe-input
          :size="sizenow"
          class="edit-down-input"
          v-model="selectVal"
          v-on:input="keyupEvent"
        ></vxe-input>
        <div class="edit-down-wrapper" :style="editdownwrapper" >
          <vxe-grid
            highlight-hover-row
            auto-resize
            :height="heightnow"
            :width="widthnow"
            border
            :size="sizenow"
            :loading="loading"
            :data="tableData"
            :columns="tableColumn"
            :tree-config="{children: 'children'}"
            @radio-change="selectEvent"
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
    "objs",
    "property",
    "tableColumns",
    "urlapi",
    "returnApi",
    "sizenow",
    "height",
    "width",
    "disabled"
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
      widthnow:"600",
      editdownwrapper:{
       width:'600px',
      },
      selectVal:"",
    };
  },
  watch: {
    params() {
      this.load();
    },
  },
  created() {
    if (this.height != null) {
      this.heightnow = this.height;
    }else{
      this.heightnow="300"
    }
    if (this.width != null) {
      if(this.width<200){
        this.tablePage.layouts= [ 'PrevPage', 'Number', 'NextPage']
      }
      this.editdownwrapper.width = this.width+"px";
    }
    if(this.pageSize!=null){
      this.tablePage.pageSize=Number(this.pageSize);
    }
    this.load();
  },
  methods: {
    load() {
      this.obj = this.objs;
      this.tableColumn = this.tableColumns;
      this.url = this.urlapi;

      this.getData().then((data) => {
        this.tableData = data;
      });
    },
    getData() {
      if (this.url != null) {
        var selectVal = "";
        if (
          this.objs[this.property] != null &&
          this.objs[this.property] != "" &&
          this.objs[this.property] != "undefined"
        ) {
          selectVal = this.selectVal.toString().replace(/\'/g, "");
        }
        return new Promise((resolve) => {
          setTimeout(() => {
            fetchList(
              Object.assign({
                codeName: selectVal,
              }),
              this.urlapi
            )
              .then((response) => {
                resolve(response.data.data);
                this.loading = false;
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
        this.keyupEvent();
        this.$refs.xDown.showPanel();
      } else {
        this.$refs.xDown.showPanel();
      }
    },
    keyupEvent() {
      const cellValue = this.objs[this.property];
      this.loading = true;
      this.getData().then((data) => {
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
    clearAll() {
      let returnApis = this.returnApi;
      for (var i in returnApis) {
        this.objs[returnApis[i].returnName] = null;
      }
    },
    selectEvent(params) {
      let returnApis = this.returnApi;
      for (var i in returnApis) {
        this.objs[returnApis[i].returnName] = params.row[returnApis[i].field];
      }
      this.$refs.xDown.hidePanel();
      this.$emit("datereturn", params.row);
    },
  },
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
