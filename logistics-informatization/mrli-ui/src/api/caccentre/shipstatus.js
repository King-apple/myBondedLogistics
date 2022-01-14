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
    url: '/caccentre/shipstatus/page',
    method: 'get',
    params: query
  })
}
export function fetchHeadList(query) {
  return request({
    url: '/caccentre/shipstatus/shippage',
    method: 'get',
    params: query
  })
}
export function fetchBodyList(shipNo) {
  return request({
    url: '/caccentre/shipstatus/shipstatus/list?shipNo='+shipNo,
    method: 'get',
  })
}
export function getMaxSeqNo(shipNo) {
  return request({
    url: '/caccentre/shipstatus/getMaxSeqNo?shipNo='+shipNo,
    method: 'get',
  })
}
export function getStatusSum(shipNo) {
  return request({
    url: '/caccentre/shipstatus/getStatusSum?shipNo='+shipNo,
    method: 'get',
  })
}
export function putObj(obj) {
  return request({
    url: '/caccentre/shipstatus',
    method: 'put',
    data: obj
  })
}
export function deletOne(pkid,statCod,beginTim,shipNo) {
  return request({
    url: '/caccentre/shipstatus/deleteOne?pkid='+pkid+"&statCod="+statCod+"&beginTim="+beginTim+"&shipNo="+shipNo,
    method: 'post',
  })
}


