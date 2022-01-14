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
    url: '/invoice/tfeehead/page',
    method: 'get',
    params: query
  })
}
export function fetchBodyList(id) {
  return request({
    url: '/invoice/tfeebody/listByfeeBillNo?feeBillNo='+id,
    method: 'get',
  })
}
export function fetchXyList(id) {
  return request({
    url: '/invoice/tfeeheadxy/list?pkid='+id,
    method: 'get',
  })
}
export function saveAll(head,body) {
  return request({
    url: '/invoice/tfeehead/commitHead',
    method: 'post',
    data: {head:head,body:body}
  })
}

export function putXyHead(obj) {
  return request({
    url: '/invoice/tfeeheadxy',
    method: 'put',
    data: obj
  })
}
export function commitXy(id) {
  return request({
    url: '/invoice/tfeeheadxy/updateXy?feeBillNo='+id,
    method: 'post',
  })
}
export function deleteXy(id) {
  return request({
    url: '/invoice/tfeeheadxy/deleteXy?pkid='+id,
    method: 'post',
  })
}
