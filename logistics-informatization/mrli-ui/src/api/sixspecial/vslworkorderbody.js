import request from '@/router/axios'

 
export function ListB(query) {
    return request({
        url: '/sixspecial/vslworkorderbody/page',
        method: 'get',
        params: query
    })
}
export function ListJhB(query) {
  return request({
      url: '/sixspecial/vslworkorderbody/ListJhB',
      method: 'get',
      params: query
  })
}
export function saveb(obj) {
    return request({
        url: '/sixspecial/vslworkorderbody/saveB',
        method: 'post',
        data:  {body: obj} 
    })
  }
  
  export function deletb(pkid) {
    return request({
      url: '/sixspecial/vslworkorderbody/deleteB?pkid='+pkid,
      method: 'post',
    })
  }