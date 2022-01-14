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
    url: "/baoshuisystem/billapprove/page",
    method: "get",
    params: query
  });
}

export function addObj(obj) {
  return request({
    url: "/baoshuisystem/billapprove",
    method: "post",
    data: obj
  });
}

export function getObj(id) {
  return request({
    url: "/baoshuisystem/billapprove/" + id,
    method: "get"
  });
}

export function delObj(id) {
  return request({
    url: "/baoshuisystem/billapprove/" + id,
    method: "delete"
  });
}

export function putObj(obj) {
  return request({
    url: "/baoshuisystem/billapprove",
    method: "put",
    data: obj
  });
}
putApproval;
export function getAllBills() {
  return request({
    url: "/baoshuisystem/billapprove/getAllBills",
    method: "get"
  });
}
export function putApproval(data) {
  return request({
    url: "/baoshuisystem/billapprove/updateInfo",
    method: "post",
    data: data
  });
}
export function getStatement() {
  return request({
    url: "/admin/dict/type/statement_type",
    method: "get"
  });
}
export function getBussiness() {
  return request({
    url: "/admin/dict/type/business_type",
    method: "get"
  });
}

export function setAudit(obj) {
  return request({
    url: "/baoshuisystem/billapprove/setAudit",
    method: "post",
    data: obj
  });
}

export async function exportBill(filename, data) {
  return await request({
    url: "/baoshuisystem/billapprove/export",
    method: "post",
    responseType: "arraybuffer",
    data: data
  }).then(response => {
    // 处理返回的文件流
    const blob = new Blob([response.data], { type: "application/zip " });
    const link = document.createElement("a");
    link.href = URL.createObjectURL(blob);
    link.download = filename;
    document.body.appendChild(link);
    link.click();
    window.setTimeout(function() {
      URL.revokeObjectURL(blob);
      document.body.removeChild(link);
    }, 0);
  });
}

export function submitCheck(obj) {
  return request({
    url: "/baoshuisystem/billapprove/submitCheck",
    method: "post",
    data: obj
  });
}
