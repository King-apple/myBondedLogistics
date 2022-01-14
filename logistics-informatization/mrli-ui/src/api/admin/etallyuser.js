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
    url: '/oldetally/etallyuser/page',
    method: 'get',
    params: query
  })
}
export function saveData(obj) {
  return request({
      url: '/oldetally/etallyuser/saveall',
      method: 'post',
      data: { body: obj }
  })
}
export function del(pkid) {
  return request({
    url: '/oldetally/etallyuser/del?pkid='+pkid,
    method: 'post',
  })
}