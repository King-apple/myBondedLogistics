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

import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/baoshuisystem/contract/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/baoshuisystem/contract',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/baoshuisystem/contract/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/baoshuisystem/contract/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/baoshuisystem/contract',
    method: 'put',
    data: obj
  })
}


export async function getUserInfo() {
  return  await request({
    url: '/admin/user/info',
    method: 'get',
    async:false
  })
}

export async function getDeptInfo(obj) {
  return  await request({
    url: '/admin/dept/'+obj,
    method: 'get'
  })
}

export async function getInsertValue() {
  return  await request({
    url: '/baoshuisystem/contract/getContractInsertValue',
    method: 'get'
  })
}

export async function getContractType() {
    return  await request({
      url: '/admin/dict/type/合同类型',
      method: 'get'
    })
}

export async function getSettlementBasisDic() {
  return  await request({
    url: '/admin/dict/type/settlement_basis_dic',
    method: 'get'
  })
}

export async function getClearingFormDic() {
  return  await request({
    url: '/admin/dict/type/clearing_form_dic',
    method: 'get'
  })
}

export  function fileSave(fileVal) {
  return   request({
    url: '/baoshuisystem/sys-file/fileSave',
    method: 'post',
    header: {
      'content-type': 'application/x-www-form-urlencoded',
    },
    data:fileVal
  })
}

export function getFileInfo(map) {
  return   request({
    url: '/baoshuisystem/sys-file/getSysFile',
    method: 'post',
    header: {
      'content-type': 'application/x-www-form-urlencoded',
    },
    data:map
  })
}

export function delFile(id) {
  return request({
    url: '/baoshuisystem/sys-file/' + id,
    method: 'delete'
  })
}

export function handleDown(filename, bucket) {
  return request({
    url: '/baoshuisystem/sys-file/' + bucket + '/' + filename,
    method: 'get',
    responseType: 'blob'
  }).then((response) => { // 处理返回的文件流
    const blob = response.data
    const link = document.createElement('a')
    link.href = URL.createObjectURL(blob)
    link.download = filename
    document.body.appendChild(link)
    link.click()
    window.setTimeout(function() {
      URL.revokeObjectURL(blob)
      document.body.removeChild(link)
    }, 0)
  })
}

export function filePageList(query) {
  return request({
    url: '/baoshuisystem/contract/filePage',
    method: 'get',
    params: query
  })
}


export function getAllContractrate() {
  return  request({
    url: '/baoshuisystem/contractrate/all',
    method: 'get'
  })
}

export function getRoleList(data) {
  return  request({
    url: '/admin/role/getRoleList',
    method: 'post',
    data:data
  })
}

