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
    url: '/business/transportcontract/page',
    method: 'get',
    params: query
  })
}
export function applyCargoList(id) {
  return request({
    url: '/business/transportcontract/getTranCargo?reqNo='+id,
    method: 'get'
  })
}
export function fetchCargoList(pkid,unitCod) {
  return request({
    url: '/business/transportcontcargo/list?pkid='+pkid+'&unitCod='+unitCod,
    method: 'get'
  })
}
export function fetchFeeList(pkid,unitCod) {
  return request({
    url: '/business/transportcontfee/list?pkid='+pkid+'&unitCod='+unitCod,
    method: 'get'
  })
}
export function getContTxt(unitCod,cargoKindCod) {
  return request({
    url: '/business/basecode/getContTxt?cargoKindCod='+cargoKindCod+'&unitCod='+unitCod,
    method: 'get'
  })
}

export function addObj(head,cargos,fees) {
  return request({
    url: '/business/transportcontract/saveContract',
    method: 'post',
    data: {head:head,cargoBody:cargos,feeBody:fees}
  })
}
export function lockOrNot(pkid,unitCod,lockId) {
  return request({
    url: '/business/transportcontract/lockOrNot?pkid='+pkid+'&unitCod='+unitCod+'&lockId='+lockId,
    method: 'post',
  })
}
export function deleteCont(pkid,unitCod) {
  return request({
    url: '/business/transportcontract/deleteCont?pkid='+pkid+'&unitCod='+unitCod,
    method: 'post',
  })
}
export function createOutYardApi(pkid,unitCod,ifDelet) {
  return request({
    url: '/business/transportcontract/createOutYard?pkid='+pkid+'&unitCod='+unitCod+"&ifDelete="+ifDelet,
    method: 'post',
  })
}
