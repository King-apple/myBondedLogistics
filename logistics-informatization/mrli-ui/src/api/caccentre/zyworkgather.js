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
    url: '/caccentre/zyworkgather/page',
    method: 'get',
    params: query
  })
}
export function getObjByDate(query) {
  return request({
    url: '/caccentre/zyworkgather/byDate',
    method: 'get',
    params: query
  })
}
export function getFistList(id) {
  return request({
    url: '/caccentre/zyworkwgtfinish/listById?id=' + id,
    method: 'get'
  })
}
export function getShipOneList(id) {
  return request({
    url: '/caccentre/zyworkshipfinish/listById?id=' + id,
    method: 'get'
  })
}
export function getShipTwoList(id) {
  return request({
    url: '/caccentre/zywork0800ship/listById?id=' + id,
    method: 'get'
  })
}
export function getShipThridList(date) {
  return request({
    url: '/caccentre/mdayfastship/getShipList?date=' + date,
    method: 'get'
  })
}
export function getTrainOneAndTwoList(id,unloadId) {
  return request({
    url: '/caccentre/zyworktrainfinish/listById?id='+id+"&unloadId="+unloadId,
    method: 'get'
  })
}
export function getCarOneAndTwoList(id,unloadId) {
  return request({
    url: '/caccentre/zyworkcarfinish/listById?id='+id+"&unloadId="+unloadId,
    method: 'get'
  })
}
export function getTrainThirdList(id) {
  return request({
    url: '/caccentre/zyworktrainonline/listById?id='+id,
    method: 'get'
  })
}
export function getTrainFourthList(id) {
  return request({
    url: '/caccentre/zyworktrainnstation/listById?id='+id,
    method: 'get'
  })
}
export function getTrainFourthObject(id) {
  return request({
    url: '/caccentre/zyworktrainnstation/getModelByHeadId?id='+id,
    method: 'get'
  })
}
export function getMachineObject(date) {
  return request({
    url: '/caccentre/zyworkresourcesmachine/getListByDate?Date='+date,
    method: 'get'
  })
}
export function getCarObject(date) {
  return request({
    url: '/caccentre/zyworkresourcescarhead/getListByDate?Date='+date,
    method: 'get'
  })
}
export function getManObject(date) {
  return request({
    url: '/caccentre/zyworkresourcesman/getlistByDate?date='+date,
    method: 'get'
  })
}
export function dealHead(date) {
  return request({
    url: '/caccentre/zyworkgather/dealHead?date=' + date,
    method: 'post'
  })
}
export function dealfinish(date, id) {
  return request({
    url: '/caccentre/zyworkgather/dealFinish?date=' + date + "&id=" + id,
    method: 'post'
  })
}
export function dealship(date, id) {
  return request({
    url: '/caccentre/zyworkgather/dealShip?date=' + date + "&id=" + id,
    method: 'post'
  })
}
export function dealtrain(date, id) {
  return request({
    url: '/caccentre/zyworkgather/dealTrain?date=' + date + "&id=" + id,
    method: 'post'
  })
}
export function dealcar(date, id) {
  return request({
    url: '/caccentre/zyworkgather/dealcar?date=' + date + "&id=" + id,
    method: 'post'
  })
}
export function addObj(obj) {
  return request({
    url: '/caccentre/zyworkgather',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/caccentre/zyworkgather/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/caccentre/zyworkgather/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/caccentre/zyworkgather',
    method: 'put',
    data: obj
  })
}
export function putObjShipOne(obj) {
  return request({
    url: '/caccentre/zyworkshipfinish',
    method: 'put',
    data: obj
  })
}
export function putObjShipTwo(obj) {
  return request({
    url: '/caccentre/zywork0800ship',
    method: 'put',
    data: obj
  })
}
export function putObjTrain(obj) {
  return request({
    url: '/caccentre/zyworktrainfinish',
    method: 'put',
    data: obj
  })
}
export function putObjTrainThird(obj) {
  return request({
    url: '/caccentre/zyworktrainonline',
    method: 'put',
    data: obj
  })
}
export function putObjCar(obj) {
  return request({
    url: '/caccentre/zyworkcarfinish',
    method: 'put',
    data: obj
  })
}
export function putObjTrainFourth(obj) {
  return request({
    url: '/caccentre/zyworktrainnstation',
    method: 'put',
    data: obj
  })
}

