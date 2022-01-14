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
    url: '/caccentre/ship/pagemd',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/caccentre/ship',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/caccentre/ship/' + id,
    method: 'get'
  })
}

export function getCountMd() {
  return request({
    url: '/caccentre/ship/getCountMd',
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/caccentre/ship/' + id,
    method: 'delete'
  })
}

export function getCountShipt(id) {
  return request({
    url: '/caccentre/ship/getCountShipt/' + id,
    method: 'get'
  })
}

export function getShipStatus(id) {
  return request({
    url: '/caccentre/ship/getShipStatus/' + id,
    method: 'get'
  })
}

export function putObj(obj) {
  return request({
    url: '/caccentre/ship',
    method: 'put',
    data: obj
  })
}
export function shipLg(obj,objnew) {
  return request({
    url: '/caccentre/ship/shipLg',
    method: 'post',
    data: {"objectnew":objnew,"obj":obj}
  })
}
