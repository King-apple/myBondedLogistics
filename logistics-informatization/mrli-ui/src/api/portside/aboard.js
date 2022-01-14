import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/portside/aboardperson/page',
    method: 'get',
    params: query
  })
}
export function fetchShList(query) {
  return request({
    url: '/portside/aboardoutside/page',
    method: 'get',
    params: query
  })
}
export function sh(id) {
  return request({
    url: '/portside/aboardoutside/sh?pkid='+id,
    method: 'post'
  })
}
export function personModelByPkid(id) {
  return request({
    url: '/portside/yqcompanyperson/'+id,
    method: 'get'
  })
}
export function excelOutPost(query) {
  return request({
      url: '/portside/aboardperson/excelOut',
      params: query,
      method: 'post',
      responseType: 'arraybuffer'
  }).then((response) => { // 处理返回的文件流
    const blob = new Blob([response.data], { type: 'application/xls' })
    const filename='登船人员详情.xls'
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