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
    url: '/sixspecial/vslstowageplanhead/page',
    method: 'get',
    params: query
  })
}
export function ListB(exid) {
  return request({
    url: '/sixspecial/vslstowageplanbody/list?exid='+exid,
    method: 'get',
  })
}
export function checkPlanBody(exid) {
  return request({
    url: '/sixspecial/vslstowageplanbody/checkPlanBody?exid='+exid,
    method: 'get',
  })
}
export function shipList(query) {
  return request({
    url: '/sixspecial/vslstowageplanhead/getShip',
    method: 'get',
    params: query
  })
}
export function shipListByShipno(query) {
  return request({
    url: '/sixspecial/vslstowageplanhead/getShipByShipno',
    method: 'get',
    params: query
  })
}
export function shipTonList(query) {
  return request({
    url: '/sixspecial/vslstowageplanhead/getShipTon',
    method: 'get',
    params: query
  })
}
export function shipTonListEdit(query) {
  return request({
    url: '/sixspecial/vslstowageplanhead/getShipTonEdit',
    method: 'get',
    params: query
  })
}
export function deleteObj(obj) {
  return request({
    url: '/sixspecial/vslstowageplanhead/deletePlan',
    method: 'post',
    data: obj
  })
}
export function dataSummary(recordDteString,unitCod) {
  return request({
    url: '/statistical/tthroughput/dataSummary',
    method: 'post',
    data: { recordDteString: recordDteString,unitCod:unitCod }
  })
}
// export function dataUpload(workDteString) {
//   return request({
//     url: '/statistical/tkindtuntuplangs/dataUpload',
//     method: 'post',
//     data: { workDteString: workDteString }
//   })
// }
export function excelOutPost(obj) {
  return request({
    url: '/statistical/tthroughput/excelOut',
    method: 'post',
    data: obj,
    responseType: 'arraybuffer'
  }).then((response) => { // 处理返回的文件流
    console.log(obj);
    const blob = new Blob([response.data], { type: 'application/xls' })
    const filename = '营口港区吞吐量完成情况' + obj.recordDteString + '.xls'
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
export function saveAll(ship,shipTon) {
  return request({
    url: '/sixspecial/vslstowageplanhead/saveAll',
    method: 'post',
    data: {ship:ship,shipTon:shipTon}
  })
}
export function saveEdit(head,shipTon) {
  return request({
    url: '/sixspecial/vslstowageplanhead/saveEdit',
    method: 'post',
    data: {head:head,shipTon:shipTon}
  })
}
export function saveb(obj) {
  return request({
      url: '/sixspecial/vslstowageplanhead/saveB',
      method: 'post',
      data:  {body: obj} 
  })
}