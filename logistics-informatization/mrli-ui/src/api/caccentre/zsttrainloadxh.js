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
 * 货物港存日报（分单位）
 */

import request from '@/router/axios'

export function getPage(query) {
  return request({
    url: '/caccentre/zsttrainloadxh/getPage',
    method: 'get',
    params: query
  })
}
export function getDataToPd(query) {
  return request({
    url: '/caccentre/zsttrainloadxh/getDataToPd',
    method: 'get',
    params: query
  })
}
export function dataProcess(query) {
  return request({
    url: '/caccentre/zsttrainloadxh/dataProcess',
    method: 'get',
    params: query
  })
}
