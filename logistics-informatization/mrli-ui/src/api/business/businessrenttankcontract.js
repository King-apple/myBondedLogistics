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
    url: '/business/businessrenttankcontract/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/business/businessrenttankcontract',
    method: 'post',
    data: obj
  })
}
export function save(obj) {
  return request({
    url: '/business/businessrenttankcontract/saveAgreement',
    method: 'post',
    data: obj
  })
}
export function deleteObj(obj) {
  return request({
    url: '/business/businessrenttankcontract/deleteContract',
    method: 'post',
    data: obj
  })
}
export function getObj(id) {
  return request({
    url: '/business/businessrenttankcontract/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/business/businessrenttankcontract/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/business/businessrenttankcontract',
    method: 'put',
    data: obj
  })
}
export function specTxtList(query) {
  return request({
    url: '/business/businessrenttankcontract/SpecTxtPage',
    method: 'get',
    params: query
  })
}

export function agreementList(id) {
  return request({
    url: '/business/businessrenttankcontract/getAgreementList?shiperCod='+id,
    method: 'get'
  })
}

export function saveAll(head,tankBody,type) {
  return request({
    url: '/business/businessrenttankcontract/saveContract',
    method: 'post',
    data: {head:head,tankBody:tankBody,saveType:type}
  })
}
export function getContractMx(id) {
  return request({
    url: '/business/businessrenttankcontract/getContractMx?contractNo='+id,
    method: 'get'
  })
}
  export function lockObj(obj,type) {
    return request({
      url: '/business/businessrenttankcontract/lockContract',
      method: 'post',
      data: {obj:obj,type:type}
    })
  }

  export function getUnitNamByUser(query) {
    return request({
      url: '/basecode/basecod/getUnitNamByUser',
      method: 'get',
      params: query
    })
  }

  export function excelOutPost(obj) {
    return request({
        url: '/business/businessrenttankcontract/excelOut',
        method: 'post',
        data: obj,
        responseType: 'arraybuffer'
    }).then((response) => { // 处理返回的文件流
      console.log(obj);
        const blob = new Blob([response.data], {type: 'application/xls'})
        const filename = obj.shiperCod+obj.beginTime+'-'+obj.endTime + '.xls'
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
export function getContUrl(id) {
  return request({
    url: '/business/businessrenttankcontract/getContUrl?contractNo=' + id,
    method: 'get'
  })
}
