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
    url: '/baoshuisystem/inoutyardbody/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/baoshuisystem/inoutyardbody',
    method: 'post',
    data: obj
  })
}
export function pageOne(obj) {
  return request({
    url: '/baoshuisystem/inoutyardbody/pageOne',
    method: 'get',
    params: obj
  })
}
export function getObj(id) {
  return request({
    url: '/baoshuisystem/inoutyardbody/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/baoshuisystem/inoutyardbody/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/baoshuisystem/inoutyardbody',
    method: 'put',
    data: obj
  })
}



export function updateByForCheckNum(obj) {
  return request({
    url: '/baoshuisystem/inoutyardbody/updateByForCheckNum',
    method: 'put',
    data: obj
  })
}

export function updateByForCustomsDeclaration(obj) {
  return request({
    url: '/baoshuisystem/inoutyardbody/updateByForCustomsDeclaration',
    method: 'put',
    data: obj
  })
}


export function updateCopy(obj) {
  return request({
    url: '/baoshuisystem/inoutyardbody/updateCopy',
    method: 'put',
    data: obj
  })
}

export function modifyShowFlag(obj) {
  return request({
    url: '/baoshuisystem/inoutyardbody/showFlag',
    method: 'put',
    data: obj
  })
}


export function getInNumberListAndCustoms() {
  return request({
    url: '/baoshuisystem/inoutyardbody/getInNumberListAndCustoms',
    method: 'get',
  })
}
export function getId(id) {
  return request({
    url: `/baoshuisystem/inoutyardbody/getId/${id}`,
    method: 'get',
  })
}
export function getBgId(id) {
  return request({
    url: `/baoshuisystem/inoutyardbody/getBgId/${id}`,
    method: 'get',
  })
}