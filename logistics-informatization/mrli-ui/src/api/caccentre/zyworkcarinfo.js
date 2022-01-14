import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/caccentre/zyworkcarfinishinfo/page',
    method: 'get',
    params: query
  })
}
export function saveAll(obj) {
    return request({
      url: '/caccentre/zyworkcarfinishinfo/saveall',
      method: 'post',
      data: {body:obj}
    })
  }

  export function copyApi(date) {
    return request({
      url: '/caccentre/zyworkcarfinishinfo/copyLast?date='+date,
      method: 'post'
    })
  }