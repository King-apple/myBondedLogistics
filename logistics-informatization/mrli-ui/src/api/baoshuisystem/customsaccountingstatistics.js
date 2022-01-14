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
    url: '/baoshuisystem/customsaccountingstatistics/page',
    method: 'get',
    params: query
  })
}

export function saveObj(obj) {
  return request({
    url: '/baoshuisystem/customsaccountingstatistics/save',
    method: 'post',
    data: obj
  })
}
export function addObj() {
  return request({
    url: '/baoshuisystem/customsaccountingstatistics',
    method: 'post',
  })
}
export function getObj(id) {
  return request({
    url: '/baoshuisystem/customsaccountingstatistics/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/baoshuisystem/customsaccountingstatistics/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/baoshuisystem/customsaccountingstatistics',
    method: 'put',
    data: obj
  })
}

export function findAllCustomsAccountingStatistics() {
  return request({
    url: '/baoshuisystem/all',
    method: 'get',
  })
}

export async function getAreaType() {
  return  await request({
    url: '/admin/dict/type/area_type',
    method: 'get'
  })
}

export function handleDown(filename,customsAccountingStatisticsVo) {
  return request({
    url: '/baoshuisystem/customsaccountingstatistics/e1',
    method: 'post',
    responseType: 'arraybuffer',
    data:customsAccountingStatisticsVo
  }).then((response) => { // 处理返回的文件流
    const blob = new Blob([response.data], {type: 'application/zip '})
    const link = document.createElement('a')
    link.href = URL.createObjectURL(blob)
    link.download = filename
    document.body.appendChild(link)
    link.click()
    window.setTimeout(function () {
      URL.revokeObjectURL(blob)
      document.body.removeChild(link)
    }, 0)
  })
}

export function getManipulatedExcel(filename,customsAccountingStatisticsVo) {
  return request({
    url: '/baoshuisystem/customsaccountingstatistics/getManipulatedExcel',
    method: 'post',
    responseType: 'arraybuffer',
    data:customsAccountingStatisticsVo
  }).then((response) => { // 处理返回的文件流
    const blob = new Blob([response.data], {type: 'application/zip '})
    const link = document.createElement('a')
    link.href = URL.createObjectURL(blob)
    link.download = filename
    document.body.appendChild(link)
    link.click()
    window.setTimeout(function () {
      URL.revokeObjectURL(blob)
      document.body.removeChild(link)
    }, 0)
  })
}


export function expoprtManipulatedForNewExcel(filename,customsAccountingStatisticsVo) {
  return request({
    url: '/baoshuisystem/customsaccountingstatistics/expoprtManipulatedForNewExcel',
    method: 'post',
    responseType: 'arraybuffer',
    data:customsAccountingStatisticsVo
  }).then((response) => { // 处理返回的文件流
    const blob = new Blob([response.data], {type: 'application/zip '})
    const link = document.createElement('a')
    link.href = URL.createObjectURL(blob)
    link.download = filename
    document.body.appendChild(link)
    link.click()
    window.setTimeout(function () {
      URL.revokeObjectURL(blob)
      document.body.removeChild(link)
    }, 0)
  })
}


export function exportManipulatedCargoExcel(filename,customsAccountingStatisticsVo) {
  return request({
    url: '/baoshuisystem/customsaccountingstatistics/exportManipulatedCargoExcel',
    method: 'post',
    responseType: 'arraybuffer',
    data:customsAccountingStatisticsVo
  }).then((response) => { // 处理返回的文件流
    const blob = new Blob([response.data], {type: 'application/zip '})
    const link = document.createElement('a')
    link.href = URL.createObjectURL(blob)
    link.download = filename
    document.body.appendChild(link)
    link.click()
    window.setTimeout(function () {
      URL.revokeObjectURL(blob)
      document.body.removeChild(link)
    }, 0)
  })
}



export function getManipulatedForNew(query) {
  return request({
    url: '/baoshuisystem/customsaccountingstatistics/getManipulatedForNew',
    method: 'get',
    params: query
  })
}


export function getManipulatedCargo(query) {
  return request({
    url: '/baoshuisystem/customsaccountingstatistics/getManipulatedCargo',
    method: 'get',
    params: query
  })
}


export function getOutAmount(query) {
  return request({
    url: '/baoshuisystem/customsaccountingstatistics/getOutAmount',
    method: 'get',
    params: query
  })
}

export function getCargoKind(query) {
  return request({
    url: '/baoshuisystem/customsaccountingstatistics/getCargoKind',
    method: 'get',
    params: query
  })
}

export function countAll(data) {
  return request({
    url: '/baoshuisystem/customsaccountingstatistics/count',
    method: 'post',
    data: data
  })
}





export function getOutAmountExcel(filename,customsOutAmountVo) {
  return request({
    url: '/baoshuisystem/customsaccountingstatistics/getOutAmountExcel',
    method: 'post',
    responseType: 'arraybuffer',
    data:customsOutAmountVo
  }).then((response) => { // 处理返回的文件流
    const blob = new Blob([response.data], {type: 'application/zip '})
    const link = document.createElement('a')
    link.href = URL.createObjectURL(blob)
    link.download = filename
    document.body.appendChild(link)
    link.click()
    window.setTimeout(function () {
      URL.revokeObjectURL(blob)
      document.body.removeChild(link)
    }, 0)
  })
}



export function getCargoKindExcel(filename,customsOutAmountVo) {
  return request({
    url: '/baoshuisystem/customsaccountingstatistics/getCargoKindExcel',
    method: 'post',
    responseType: 'arraybuffer',
    data:customsOutAmountVo
  }).then((response) => { // 处理返回的文件流
    const blob = new Blob([response.data], {type: 'application/zip '})
    const link = document.createElement('a')
    link.href = URL.createObjectURL(blob)
    link.download = filename
    document.body.appendChild(link)
    link.click()
    window.setTimeout(function () {
      URL.revokeObjectURL(blob)
      document.body.removeChild(link)
    }, 0)
  })
}