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
    url: '/business/contract/page',
    method: 'get',
    params: query
  })
}
export function fetchListSecond(query) {
  return request({
    url: '/business/contract/pageOfSecond',
    method: 'get',
    params: query
  })
}
export function fetchListShip(query) {
  return request({
    url: '/business/contract/shippage',
    method: 'get',
    params: query
  })
}

export function getSumContrat(query) {
  return request({
    url: '/business/contract/getSumContrat',
    method: 'get',
    params: query
  })
}

export function specTxtList(query) {
  return request({
    url: '/business/contract/SpecTxtPage',
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
    url: '/business/contract/'+id,
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
    url: '/business/contract',
    method: 'post',
    data: obj
  })
}
export function commit(fphm) {
  return request({
    url: '/business/contract/commit?fphm='+fphm,
    method: 'post'
  })
}
export function copyOtherCont(fphm) {
  return request({
    url: '/business/contract/saveSecondCont?fphm='+fphm,
    method: 'post'
  })
}
export function getFile(id) {
  return request({
    url: '/business/contract/getFile?fphm=' + id,
    method: 'get'
  })
}
export function getContUrl(id) {
  return request({
    url: '/business/contract/getContUrl?fphm=' + id,
    method: 'get'
  })
}
export function delObj(id) {
  return request({
    url: '/business/contract/' + id,
    method: 'delete'
  })
}
export function backObj(id) {
  return request({
    url: '/business/contract/back?fphm=' + id,
    method: 'post'
  })
}
export function cwcheckCont(id) {
  return request({
    url: '/business/contstate/checkCont?fphm=' + id,
    method: 'post'
  })
}
export function saveAll(head,cargoBody,assignBody,cspecbody,type) {
  return request({
    url: '/business/contract/saveContract',
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
export function selectFee(obj) {
  return request({
    url: '/business/contract/getPrice',
    method: 'get',
    params: obj
  })
}
export function selectUnit() {
  return request({
    url: '/basecode/basecod/getOneUnitNam',
    method: 'get',
  })
}
