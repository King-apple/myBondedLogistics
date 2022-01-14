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
    url: '/caccentre/shipplanbody/page',
    method: 'get',
    params: query
  })
}

export function getcont(query) {
  return request({
    url: '/caccentre/shipplanbody/getCont',
    method: 'get',
    params: query
  })
}
export function List(query) {
  return request({
    url: '/caccentre/shipplanhead/page',
    method: 'get',
    params: query
  })
}

export function deleteb(pkid) {
  return request({
    url: '/caccentre/shipplanbody/deleteB?pkid='+pkid,
    method: 'post',
  })
}

export function insertOne(obj) {
  return request({
    url: '/caccentre/shipplanbody/insertShipPlanBody',
    method: 'post',
    data: obj
  })
}

export function copyPlan(obj) {
  return request({
    url: '/caccentre/shipplanbody/copyPlan',
    method: 'post',
    data: obj
  })
}

export function copyText(obj) {
  return request({
    url: '/caccentre/shipplanbody/copyText',
    method: 'post',
    data: obj
  })
}

export function getPlanUnitNam(datePlan) {
  return request({
    url: '/caccentre/shipplanbody/getPlanUnitNam?datePlan=' + datePlan,
    method: 'get'
  })
}
export function insertPlanData(obj1,obj2) {
  return request({
    url: '/caccentre/shipplanbody/insertPlanData',
    method: 'post',
    data: {obj1:obj1,obj2:obj2}
  })
}

export function sjpd(tim) {
  return request({
    url: '/caccentre/shipplanbody/sjpd?tim=' + tim,
    method: 'put'
  })
}

export function saveBodyData(obj) {
  return request({
      url: '/caccentre/shipplanbody/saveall',
      method: 'post',
      data: { body: obj }
  })
}

export function excelOutPost(obj) {
  return request({
    url: '/caccentre/shipplanbody/excelOut?planDte='+obj,
    method: 'post' ,
    responseType: 'blob'
  }).then((response) => { // 处理返回的文件流
    console.log(response.data);
      const blob = new Blob([response.data], {type: 'application/xls'})
      const filename = obj + '.xls'
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