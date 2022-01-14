/*
 *    Copyright (c) 2018-2025, mrli All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: mrli
 */

import request from "@/router/axios";

export function fetchList(query) {
  return request({
    url: "/baoshuisystem/ykbfsingclass/page",
    method: "get",
    params: query
  });
}
export function getAll(query) {
  return request({
    url: "/baoshuisystem/ykbfsingclass/noPage",
    method: "get",
    params: query
  });
}
export function getObj(id) {
  return request({
    url: "/baoshuisystem/ykbfsingclass/" + id,
    method: "get"
  });
}

export function printToExcel(obj) {
  return request({
    url: "/baoshuisystem/ykbfsingclass/printToExcel",
    method: "post",
    data: obj,
    responseType: "arraybuffer"
  }).then(response => {
    // 处理返回的文件流
    const blob = new Blob([response.data], { type: "application/zip " });
    const link = document.createElement("a");
    link.href = URL.createObjectURL(blob);
    link.download = "日操作量表.xls";
    document.body.appendChild(link);
    link.click();
    window.setTimeout(function() {
      URL.revokeObjectURL(blob);
      document.body.removeChild(link);
    }, 0);
  });
}



export function exportList(obj) {
  return request({
    url: "/baoshuisystem/ykbfsingclass/exportList",
    method: "post",
    data: obj,
    responseType: "arraybuffer"
  }).then(response => {
    // 处理返回的文件流
    const blob = new Blob([response.data], { type: "application/zip " });
    const link = document.createElement("a");
    link.href = URL.createObjectURL(blob);
    link.download = "单个工班查询管理.xls";
    document.body.appendChild(link);
    link.click();
    window.setTimeout(function() {
      URL.revokeObjectURL(blob);
      document.body.removeChild(link);
    }, 0);
  });
}