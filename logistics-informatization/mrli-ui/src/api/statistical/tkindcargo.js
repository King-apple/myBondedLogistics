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
    url: '/statistical/tkindcargo/page',
    method: 'get',
    params: query
  })
}
// export function dataSummary(workDteString) {
//   return request({
//     url: '/statistical/tkindtuntuplangs/dataSummary',
//     method: 'post',
//     data: { workDteString: workDteString }
//   })
// }
// export function dataUpload(workDteString) {
//   return request({
//     url: '/statistical/tkindtuntuplangs/dataUpload',
//     method: 'post',
//     data: { workDteString: workDteString }
//   })
// }
export function excelOutPost(obj) {
  return request({
    url: '/statistical/tkindtuntuplangs/excelOut',
    method: 'post',
    data: obj,
    responseType: 'arraybuffer'
  }).then((response) => { // 处理返回的文件流
    console.log(obj);
    const blob = new Blob([response.data], { type: 'application/xls' })
    const filename = '月吞吐量计划（局汇总）' + obj.workDteString + '.xls'
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
