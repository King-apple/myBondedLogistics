import "babel-polyfill";
import "classlist-polyfill";
import Vue from "vue";
import axios from "./router/axios";
import VueAxios from "vue-axios";
import App from "./App";
import "./permission"; // 权限
import "./error"; // 日志
import "./cache"; //页面缓冲
import router from "./router/router";
import store from "./store";
import { loadStyle } from "./util/util";
import * as urls from "@/config/env";
import { iconfontUrl, iconfontVersion } from "@/config/env";
import * as filters from "./filters"; // 全局filter
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import "./styles/common.scss";
import AvueFormDesign from "@sscfaith/avue-form-design";
import basicContainer from "./components/basic-container/main";
import "xe-utils";
import VXETable from "vxe-table";
import "vxe-table/lib/index.css";
import "font-awesome/css/font-awesome.min.css";
import XEUtils from "xe-utils";
import EditDownTable from "./views/baseUtile/EditDownTable";
import EditTimeTable from "./views/baseUtile/EditTimeTable";
import echarts from "echarts";
import Viewer from "v-viewer";
import "viewerjs/dist/viewer.css";
import Print from "vue-print-nb";
Vue.use(Print);
Vue.prototype.$echarts = echarts;
// 插件 json 展示
Vue.use(router);
Vue.use(Viewer);
Viewer.setDefaults({
  Options: {
    inline: true,
    button: true,
    navbar: true,
    title: true,
    toolbar: true,
    tooltip: true,
    movable: true,
    zoomable: true,
    rotatable: true,
    scalable: true,
    transition: true,
    fullscreen: true,
    keyboard: true,
    url: "data-source"
  }
});
Vue.use(AvueFormDesign);

window.axios = axios;
Vue.use(VueAxios, axios);
Vue.use(VXETable);
// 给 vue 实例挂载全局窗口对象
Vue.prototype.$XModal = VXETable.modal;
Vue.use(ElementUI, {
  size: "small",
  menuType: "text"
});

Vue.use(AVUE, {
  size: "small",
  menuType: "text"
});

// 注册全局容器
Vue.component("basicContainer", basicContainer);

// 加载相关url地址
Object.keys(urls).forEach(key => {
  Vue.prototype[key] = urls[key];
});

// 加载过滤器
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key]);
});
Vue.component(EditDownTable.name, EditDownTable);
Vue.component(EditTimeTable.name, EditTimeTable);
VXETable.formats.mixin({
  // 格式化性别
  formatSex({ cellValue }) {
    return cellValue ? (cellValue === "1" ? "男" : "女") : "";
  },
  // 格式化状态
  formatStatus({ cellValue }) {
    if (cellValue == 1 || cellValue == "1") {
      return "启用";
    }
    if (cellValue == 0 || cellValue == "0") {
      return "不启用";
    }
  },
  formatYoN({ cellValue }) {
    if (cellValue == 1 || cellValue == "1") {
      return "是";
    }
    if (cellValue == 0 || cellValue == "0") {
      return "否";
    }
  },
  // 格式化内外贸
  formatTradeId({ cellValue }) {
    if (cellValue == 1 || cellValue == "1") {
      return "内";
    }
    if (cellValue == 0 || cellValue == "0") {
      return "外";
    }
  },
  // 格式化下拉选项
  formatSelect({ cellValue }, list) {
    const item = list.find(item => item.value === cellValue);
    return item ? item.label : "";
  },
  // 格式化日期，默认 yyyy-MM-dd HH:mm:ss
  formatDate({ cellValue }, format) {
    return XEUtils.toDateString(cellValue, format || "yyyy-MM-dd HH:mm:ss");
  },
  // 格式金额，默认2位数
  formatAmount({ cellValue }, digits) {
    return XEUtils.commafy(cellValue, { digits: digits || 2 });
  },
  // 格式化银行卡，默认每4位隔开
  formatBankcard({ cellValue }) {
    return XEUtils.commafy(cellValue, { spaceNumber: 4, separator: " " });
  },
  // 四舍五入,默认两位数
  formatFixedNumber({ cellValue }, digits) {
    return XEUtils.toNumber(cellValue).toFixed(digits || 2);
  },
  // 截取小数,默认两位数
  formatCutNumber({ cellValue }, digits) {
    return XEUtils.toFixedString(cellValue, digits || 2);
  },
  // 转换 moment 类型为字符串
  toMomentString({ cellValue }, format) {
    return cellValue ? cellValue.format(format) : "";
  }
});
// 创建一个下拉表格渲染
VXETable.renderer.add("EditDownTable", {
  autofocus: ".vxe-input--inner",
  renderEdit(h, renderOpts, params) {
    return [
      <edit-down-table
        params={params}
        renderOpts={renderOpts}
      ></edit-down-table>
    ];
  }
});
// 创建表格内的时间选择器
VXETable.renderer.add("EditTimeTable", {
  autofocus: ".vxe-input--inner",
  renderEdit(h, renderOpts, params) {
    return [
      <EditTimeTable params={params} renderOpts={renderOpts}></EditTimeTable>
    ];
  }
});
// 创建一个下拉表格渲染
VXETable.renderer.add("TableColumnLink", {
  // 默认显示模板
  renderDefault(h, renderOpts, params) {
    let { row, column } = params;
    let { events } = renderOpts;
    return [
      <a
        class="my-link"
        style="color:blue"
        onClick={() => events.click(params)}
      >
        {row[column.property]}
      </a>
    ];
  }
});
// 动态加载阿里云字体库
iconfontVersion.forEach(ele => {
  loadStyle(iconfontUrl.replace("$key", ele));
});

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
