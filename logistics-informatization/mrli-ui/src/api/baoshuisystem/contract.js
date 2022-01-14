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
    url: "/baoshuisystem/contract/page",
    method: "get",
    params: query
  });
}

export function addObj(obj) {
  return request({
    url: "/baoshuisystem/contract",
    method: "post",
    data: obj
  });
}

export function getObj(id) {
  return request({
    url: "/baoshuisystem/contract/" + id,
    method: "get"
  });
}

export function delObj(id) {
  return request({
    url: "/baoshuisystem/contract/" + id,
    method: "delete"
  });
}

export function putObj(obj) {
  return request({
    url: "/baoshuisystem/contract",
    method: "put",
    data: obj
  });
}

export async function getUserInfo() {
  return await request({
    url: "/admin/user/info",
    method: "get",
    async: false
  });
}

export async function getDeptInfo(obj) {
  return await request({
    url: "/admin/dept/" + obj,
    method: "get"
  });
}

export async function getInsertValue() {
  return await request({
    url: "/baoshuisystem/contract/getContractInsertValue",
    method: "get"
  });
}

export async function getContractType() {
  return await request({
    url: "/admin/dict/type/contract_type",
    method: "get"
  });
}

export async function getSettlementBasisDic() {
  return await request({
    url: "/admin/dict/type/settlement_basis_dic",
    method: "get"
  });
}

export async function getClearingFormDic() {
  return await request({
    url: "/admin/dict/type/clearing_form_dic",
    method: "get"
  });
}

export function fileSave(fileVal) {
  return request({
    url: "/baoshuisystem/sys-file/fileSave",
    method: "post",
    header: {
      "content-type": "application/x-www-form-urlencoded"
    },
    data: fileVal
  });
}

export function fileUpdate(fileVal) {
  return request({
    url: "/baoshuisystem/sys-file/fileUpdate",
    method: "post",
    header: {
      "content-type": "application/x-www-form-urlencoded"
    },
    data: fileVal
  });
}

export function getFileInfo(map) {
  return request({
    url: "/baoshuisystem/sys-file/getSysFile",
    method: "post",
    header: {
      "content-type": "application/x-www-form-urlencoded"
    },
    data: map
  });
}

export function delFile(id) {
  return request({
    url: "/baoshuisystem/sys-file/" + id,
    method: "delete"
  });
}

export function handleDown(filename, bucket) {
  return request({
    url: "/baoshuisystem/sys-file/" + bucket + "/" + filename,
    method: "get",
    responseType: "blob"
  }).then(response => {
    // 处理返回的文件流
    const blob = response.data;
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

export function filePageList(query) {
  return request({
    url: "/baoshuisystem/contract/filePage",
    method: "get",
    params: query
  });
}

export function getAllContractrate() {
  return request({
    url: "/baoshuisystem/contractrate/all",
    method: "get"
  });
}

export async function getContractCode(params) {
  return await request({
    url: "/baoshuisystem/contract/getContractCode",
    method: "get",
    params: params
  });
}

export function getChildCode(query) {
  return request({
    url: "/baoshuisystem/contract/getChildCode",
    method: "get",
    params: query
  });
}
export function dealt(fid) {
  return request({
    url: "/baoshuisystem/contract/exportExcel?fid=" + fid,
    method: "post",
    responseType: "arraybuffer"
  });
}
export function expoprtExcel(fid) {
  return request({
    url: "/baoshuisystem/contract/exportExcel?fid=" + fid,
    method: "post",
    responseType: "arraybuffer"
  }).then(response => {
    // 处理返回的文件流
    console.log(response);
    const blob = new Blob([response.data], { type: "application/xls" });
    var filename = response.headers.contentdisposition;

    filename = decodeURI(filename);
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

export function getContractCheckRecord(query) {
  return request({
    url: "/baoshuisystem/contract/getContractCheckRecord",
    method: "get",
    params: query
  });
}

export function getCheckStatusDic() {
  return request({
    url: "/admin/dict/type/check_status_dic",
    method: "get"
  });
}

export async function getContractCheckProcess() {
  return await request({
    url: "/admin/dict/type/contract_check_process",
    method: "get"
  });
}

//合同下拉列表
export function getSelectContract() {
  return request({
    url: "/baoshuisystem/contract/getSelectContract",
    method: "get"
  });
}
//合同审核通过不是作废
export function getEffectiveInfo() {
  return request({
    url: "/baoshuisystem/contract/getEffectiveInfo",
    method: "get"
  });
}

export function getInoutIdDic() {
  return request({
    url: "/admin/dict/type/import_export",
    method: "get"
  });
}

export function getTradeIdDic() {
  return request({
    url: "/admin/dict/type/trade_type",
    method: "get"
  });
}

export function updateApprovalprocessset(obj) {
  return request({
    url: "/baoshuisystem/contract/updateApprovalprocessset",
    method: "put",
    data: obj
  });
}

export function completeApprovalFlag(obj) {
  return request({
    url: "/baoshuisystem/contract/modify/completeApprovalFlag",
    method: "post",
    data: obj
  });
}
