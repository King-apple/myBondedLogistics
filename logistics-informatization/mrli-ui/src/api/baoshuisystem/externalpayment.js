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
    url: "/baoshuisystem/businessbillshead/page",
    method: "get",
    params: query
  });
}
export function findBusinessList(query) {
  return request({
    url: "/baoshuisystem/businessbillshead/pageBusiness",
    method: "get",
    params: query
  });
}

export function addObj(obj) {
  return request({
    url: "/baoshuisystem/businessbillshead",
    method: "post",
    data: obj
  });
}

export function getObj(id) {
  return request({
    url: "/baoshuisystem/businessbillshead/" + id,
    method: "get"
  });
}

export function delObj(id) {
  return request({
    url: "/baoshuisystem/businessbillshead/" + id,
    method: "delete"
  });
}

export function putObj(obj) {
  return request({
    url: "/baoshuisystem/businessbillshead",
    method: "put",
    data: obj
  });
}
export async function getitemNameType() {
  return await request({
    url: "/admin/dict/type/itemname_type",
    method: "get"
  });
}
export async function getInvoicTpye() {
  return await request({
    url: "/admin/dict/type/invoice_tpye",
    method: "get"
  });
}
export async function addList(data) {
  return await request({
    url: "/baoshuisystem/businessbillsbody/addList",
    method: "post",
    data: data
  });
}
export async function expertExternal(filename, data) {
  return await request({
    url: "/baoshuisystem/businessbillshead/download",
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
export async function expertBusiness(filename, data) {
  return await request({
    url: "/baoshuisystem/businessbillshead/downloadBus",
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

export async function getCargoKindOperation(data) {
  return await request({
    url: "/baoshuisystem/businessbillshead/getCargoKindOperation",
    method: "get",
    params: data
  });
}

export function getIncomeStatistcs(data) {
  return request({
    url: "/baoshuisystem/businessbillshead/getIncomeStatistcs",
    method: "get",
    params: data
  });
}

export function cargoKindOperationPrintToExcel(obj) {
  return request({
    url: "/baoshuisystem/businessbillshead/cargoKindOperationPrintToExcel",
    method: "post",
    data: obj,
    responseType: "blob"
  }).then(response => {
    // 处理返回的文件流
    const blob = response.data;
    const link = document.createElement("a");
    link.href = URL.createObjectURL(blob);
    link.download = "货代部分货种操作量表.xls";
    document.body.appendChild(link);
    link.click();
    window.setTimeout(function() {
      URL.revokeObjectURL(blob);
      document.body.removeChild(link);
    }, 0);
  });
}
export function incomeStatistcsPrintToExcel(obj) {
  return request({
    url: "/baoshuisystem/businessbillshead/incomeStatistcsPrintToExcel",
    method: "post",
    data: obj,
    responseType: "blob"
  }).then(response => {
    // 处理返回的文件流
    const blob = response.data;
    const link = document.createElement("a");
    link.href = URL.createObjectURL(blob);
    link.download = "货代部分货种操作量表.xls";
    document.body.appendChild(link);
    link.click();
    window.setTimeout(function() {
      URL.revokeObjectURL(blob);
      document.body.removeChild(link);
    }, 0);
  });
}
