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

export function fetchList(contNo,unitCod) {
  return request({
    url: '/business/transportdc/list?contNo='+contNo+'&unitCod='+unitCod,
    method: 'get'
  })
}
export function addObj(head) {
  return request({
    url: '/business/transportdc/saveFee',
    method: 'post',
    data: {head:head}
  })
}
export function delObj(pkid,unitCod) {
  return request({
    url: '/business/transportdc/deleteOne?pkid='+pkid+'&unitCod='+unitCod,
    method: 'post'
  })
}