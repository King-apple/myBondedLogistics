import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/portside/aboardwaixie/page',
    method: 'get',
    params: query
  })
}
export function addObj(obj) {
    return request({
      url: '/portside/aboardwaixie/save',
      method: 'post',
      data: obj
    })
  }

  export function deleteObj(pkid) {
    return request({
      url: '/portside/aboardwaixie/delete?pkid='+pkid,
      method: 'post'
    })
  }