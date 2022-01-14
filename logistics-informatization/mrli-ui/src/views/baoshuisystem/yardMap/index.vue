<template>
  <div class="mod-config">
    <basic-container>
      <el-form
        :inline="true"
        :model="dataForm"
        @keyup.enter.native="getDataList()"
      >
      </el-form>

      <div class="avue-crud">
        <div
          v-for="(item, index) in portAreaList"
          :key="index"
          style="
            width: 100%;
            height: 120px;
            margin-top: 5px;
            background: #1d5376;
            border-radius: 5px;
            margin-left: 50px;
          "
        >
          <div
            style="
              float: left;
              width: 100px;
              text-align: center;
              line-height: 100px;
              margin-left: -100px;
            "
          >
            {{ item.label }}
          </div>
          <div
            style="
              float: left;
              width: 10%;
              height: 110px;
              margin-top: 5px;
              margin-left: 5px;
              border-radius: 5px;
              font-size: 12px;
              background: #238efd;
              color: #fff;
            "
            v-for="(item1, index1) in portValAreaList[item.label]"
            :key="index1"
          >
            <div>
              <div style="transform: scale(0.9, 0.9)">
                货主:{{ item1.consignNam }}
              </div>
            </div>
            <div>
              <div style="transform: scale(0.9, 0.9)">
                船名:{{ item1.shipNam }}
              </div>
            </div>
            <div>
              <div style="transform: scale(0.9, 0.9)">
                货名:{{ item1.cargoNam }}
              </div>
            </div>
            <div style="float: left">
              <div style="transform: scale(0.9, 0.9)">
                件数:{{ item1.surplusPiecesNum }}
              </div>
            </div>
            <div style="float: left">
              <div style="transform: scale(0.9, 0.9)">
                重量:{{ item1.surplusWeightWgt }}
              </div>
            </div>
            <div style="clear: both; float: none"></div>
            <div>
              <div style="transform: scale(0.9, 0.9)">
                日期:{{ item1.inYardDat }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </basic-container>
  </div>
</template>

<script>
import { fetchList, delObj, getPortArea, allList } from '@/api/baoshuisystem/yardinfo'
import { mapGetters } from 'vuex'
export default {
  data() {
    return {
      dataForm: {
        key: ''
      },
      dataList: [],
      portAreaList: [],
      portValAreaList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      addOrUpdateVisible: false
    }
  },
  components: {

  },
  created() {
    this.getDataList()
  },
  computed: {
    ...mapGetters(['permissions'])
  },
  methods: {
    // 获取数据列表
    getDataList() {
      this.dataListLoading = true
      allList().then(response => {
        this.dataList = response.data.data
        console.log(this.dataList)
        getPortArea().then(response => {
          this.portAreaList = response.data.data
          for (var i in this.portAreaList) {
            var key = this.portAreaList[i].label
            this.portValAreaList[key] = []

            for (var j in this.dataList) {
              if (this.dataList[j].areaNam == this.portAreaList[i].label) {
                this.portValAreaList[key].push(this.dataList[j]);
              }
            }
          }

        })
      })

      this.dataListLoading = false
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val
      this.getDataList()
    },

  }
}
</script>
