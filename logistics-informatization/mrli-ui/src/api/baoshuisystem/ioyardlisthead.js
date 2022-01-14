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
    url: '/baoshuisystem/ioyardlisthead/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/baoshuisystem/ioyardlisthead',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/baoshuisystem/ioyardlisthead/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/baoshuisystem/ioyardlisthead/' + id,
    method: 'delete'
  })
}

export function delItemObj(id) {
  return request({
    url: '/baoshuisystem/ioyardlistbody/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/baoshuisystem/ioyardlisthead',
    method: 'put',
    data: obj
  })
}

export function getHeadBodyPage(query) {
  return request({
    url: '/baoshuisystem/ioyardlisthead/getHeadBodyPage',
    method: 'get',
    params: query
  })

}


export function putObjBody(obj) {
  return request({
    url: '/baoshuisystem/ioyardlistbody',
    method: 'put',
    data: obj
  })
}

export function exportFile(name) {
  return request({
    url: '/baoshuisystem/ioyardlisthead/exportFile',
    method: 'post',
    data:name,
    responseType: 'arraybuffer'
}).then((response) => { // 处理返回的文件流
    const blob = new Blob([response.data], {type: 'application/xls'})
    var filename =   response.headers.contentdisposition
    filename = decodeURI(filename)
    const link = document.createElement('a')
    link.href = URL.createObjectURL(blob)
    link.download = filename
    document.body.appendChild(link)
    link.click()
    window.setTimeout(function () {
      URL.revokeObjectURL(blob)
      document.body.removeChild(link)
    }, 0)
}) 
}