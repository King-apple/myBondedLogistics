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
    url: '/transport/hpmx/page',
    method: 'get',
    params: query
  })
}
export function fetchListhp(query) {
  return request({
    url: '/transport/hpmx/pagehp',
    method: 'get',
    params: query
  })
}
export function fetchListOfCargo(query) {
  return request({
    url: '/transport/hpmc//tj/by/cargoTypeNam',
    method: 'get',
    params: query
  })
}
export function fetchHzWeekTj(query) {
  return request({
    url: '/transport/bcmc/tj/by/hzWeekTj',
    method: 'get',
    params: query
  })
}
export function fetchListOfUnit(query) {
  return request({
    url: '/transport/hpmc//tj/by/UnitTypeNam',
    method: 'get',
    params: query
  })
}
export function fetchListOfDep(query) {
  return request({
    url: '/transport/hpmc//tj/by/DepTypeNam',
    method: 'get',
    params: query
  })
}
export function fetchListOfBz(query) {
  return request({
    url: '/transport/hpmc/tj/by/BzTypeNam',
    method: 'get',
    params: query
  })
}
export function fetchListOfCbs(query) {
  return request({
    url: '/transport/bcmc/BzCbs',
    method: 'get',
    params: query
  })
}
export function fetchListOfHp(query) {
  return request({
    url: '/transport/checkweight/ByHp',
    method: 'get',
    params: query
  })
}
export function fetchListOfhzAllTj(query) {
  return request({
    url: '/transport/checkweight/HzAllTj',
    method: 'get',
    params: query
  })
}
export function upBbdySave(query) {
  return request({
    url: '/transport/hpbc/upBbdySave',
    method: 'put',
    params: query
  })
}
export function upGbIdSave(query) {
  return request({
    url: '/transport/hpmx/upGbIdSave',
    method: 'put',
    params: query
  })
}
export function getStabc(query) {
  return request({
    url: '/transport/hpmx/getStabc',
    method: 'get',
    params: query
  })
}
export function getStahz(query) {
  return request({
    url: '/transport/hpbc/getStahz',
    method: 'get',
    params: query
  })
}
export function getSumwcl(query) {
  return request({
    url: '/transport/hpmx/getSumwcl',
    method: 'get',
    params: query
  })
}
export function getSumcsl(query) {
  return request({
    url: '/transport/hpmx/getSumcsl',
    method: 'get',
    params: query
  })
}
export function specTxtList(query) {
  return request({
    url: '/transport/hpmx/SpecTxtPage',
    method: 'get',
    params: query
  })
}
export function dealHead(id) {
  return request({
    url: '/business/cargoreadyinformhead/dealHead?informNo='+id,
    method: 'get'
  })
}
export function refreshHead(id,fphm) {
  return request({
    url: '/business/cargoreadyinformhead/dealHead?informNo='+id+"&fphm="+fphm,
    method: 'get'
  })
}
export function dealAssign(id) {
  return request({
    url: '/business/cargoreadyinformhead/dealAssign?informNo='+id,
    method: 'get'
  })
}
export function dealCargo(id) {
  return request({
    url: '/business/cargoreadyinformhead/dealCargo?informNo='+id,
    method: 'get'
  })
}
export function getHead(id) {
  return request({
    url: '/transport/hpmx/'+id,
    method: 'get'
  })
}
export function getCargo(id) {
  return request({
    url: '/business/contcargo/list?fphm='+id,
    method: 'get'
  })
}
export function getAssign(id) {
  return request({
    url: '/business/contassign/list?fphm='+id,
    method: 'get'
  })
}
export function getCspec(id) {
  return request({
    url: '/business/contcspec/list?fphm='+id,
    method: 'get'
  })
}
export function addObj(obj) {
  return request({
    url: '/transport/hpmx',
    method: 'post',
    data: obj
  })
}
export function commit(fphm) {
  return request({
    url: '/transport/hpmx/commit?fphm='+fphm,
    method: 'post'
  })
}

export function getFile(id) {
  return request({
    url: '/transport/hpmx/getFile?fphm=' + id,
    method: 'get'
  })
}
export function getContUrl(id) {
  return request({
    url: '/transport/hpmx/getContUrl?fphm=' + id,
    method: 'get'
  })
}
export function delObj(id) {
  return request({
    url: '/transport/hpmx/' + id,
    method: 'delete'
  })
}
export function backObj(id) {
  return request({
    url: '/transport/hpmx/back?fphm=' + id,
    method: 'post'
  })
}
export function saveAll(head,cargoBody,assignBody,cspecbody,type) {
  return request({
    url: '/transport/hpmx/saveContract',
    method: 'post',
    data: {head:head,cargoBody:cargoBody,assignBody:assignBody,cspecbody:cspecbody,saveType:type}
  })
}
export function selectContChangeOneGet(id) {
  return request({
    url: '/business/conttrace/contAll?id='+id,
    method: 'get',
  })
}
