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
    url: '/caccentre/trainplan/page',
    method: 'get',
    params: query
  })
}
export function fetchSubList(planNo) {
  return request({
    url: '/caccentre/subtrainplan/list?planNo='+planNo,
    method: 'get'
  })
}
export function save(obj) {
  return request({
    url: '/caccentre/trainplan/saveTrainPlan',
    method: 'post',
    data: obj
  })
}
export function saveSg(obj) {
  return request({
    url: '/caccentre/trainplan/saveTrainPlanSg',
    method: 'post',
    data: obj
  })
}
export function update(obj) {
  return request({
    url: '/caccentre/trainplan/updateTrainPlan',
    method: 'post',
    data: obj
  })
}
export function updateSg(obj) {
  return request({
    url: '/caccentre/trainplan/updateTrainPlanSg',
    method: 'post',
    data: obj
  })
}
export function deleteObj(obj) {
  return request({
    url: '/caccentre/trainplan/deleteTrainPlan',
    method: 'post',
    data: obj
  })
}
export function addObj(obj) {
  return request({
    url: '/caccentre/trainplan',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/caccentre/trainplan/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/caccentre/trainplan/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/caccentre/trainplan',
    method: 'put',
    data: obj
  })
}
export function getUnit(query) {
  return request({
    url: '/caccentre/traintent/getUnit',
    method: 'get',
    params: query
  })
}
