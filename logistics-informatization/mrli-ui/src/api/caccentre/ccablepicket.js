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

 
export function List(query) {
  return request({
    url: '/caccentre/ccablepicket/page',
    method: 'get',
    params: query
  })
}

export function deleteb(pkid) {
  return request({
    url: '/caccentre/ccablepicket/del?pkid='+pkid,
    method: 'post',
  })
}
 
 

export function saveBodyData(obj) {
  return request({
      url: '/caccentre/ccablepicket/saveall',
      method: 'post',
      data: { body: obj }
  })
}

 