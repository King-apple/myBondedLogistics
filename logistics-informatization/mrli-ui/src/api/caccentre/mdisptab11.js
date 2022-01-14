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
    url: '/caccentre/mdisptab11/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/caccentre/mdisptab11',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/caccentre/mdisptab11/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/caccentre/mdisptab11/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/caccentre/mdisptab11',
    method: 'put',
    data: obj
  })
}
export function sjcl(obj) {
  return request({
    url: '/caccentre/mdisptab11/sjcl',
    method: 'put',
    data: obj
  })
}
export function sjpd(tim) {
  return request({
    url: '/caccentre/mdisptab11/sjpd?tim=' + tim,
    method: 'put'
  })
}
export function cxml(tim) {
  return request({
    url: '/caccentre/mdisptab11/cxml?tim=' + tim,
    method: 'put'
  })
}