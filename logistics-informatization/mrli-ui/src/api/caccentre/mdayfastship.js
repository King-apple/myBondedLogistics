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
    url: '/caccentre/mdayfastship2/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/caccentre/mdayfastship2',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/caccentre/mdayfastship2/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/caccentre/mdayfastship2/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/caccentre/mdayfastship2',
    method: 'put',
    data: obj
  })
}


export function updateCkid(obj) {
  return request({
    url: '/caccentre/mdayfastship2/updateCkid',
    method: 'post',
    data: obj
  })
}

export function dealData(date) {
  return request({
    url: '/caccentre/mdayfastship2/dealData?workDte=' + date,
    method: 'post'
  })
}