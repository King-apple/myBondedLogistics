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
    url: '/meetingsystem/meetingpeople/page',
    method: 'get',
    params: query
  })
}

export function getList(query) {
  return request({
    url: '/meetingsystem/meetingpeople/getList',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/meetingsystem/meetingpeople',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/meetingsystem/meetingpeople/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/meetingsystem/meetingpeople/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/meetingsystem/meetingpeople',
    method: 'put',
    data: obj
  })
}


export function exportInfo(obj) {
  return request({
    url: '/meetingsystem/meetingpeople/export',
    method: 'get',
    params: obj
  })
}

export function exportForDay(obj) {
  return request({
    url: '/meetingsystem/meetingpeople/exportForDay',
    method: 'get',
    params: obj
  })
}

export function exportForDayPrint(name) {
  return request({
    url: '/meetingsystem/meetingpeople/exportForDayPrint',
    method: 'post',
    data:name,
    responseType: 'arraybuffer'
})
}

export function exportForMonth(obj) {
  return request({
    url: '/meetingsystem/meetingpeople/exportForMonth',
    method: 'get',
    params: obj
  })
}



export function importTemplate(obj) {
  return request({
    url: '/meetingsystem/meetingpeople/importTemplate',
    method: 'get',
    params: obj
  })
}


export function gettattestation(obj) {
  return request({
    url: '/meetingsystem/meetingpeople/gettattestation',
    method: 'get',
    params: obj
  })
}


export function saveTattestation(obj) {
  return request({
    url: '/meetingsystem/meetingpeople/saveTattestation',
    method: 'post',
    data: obj
  })
}