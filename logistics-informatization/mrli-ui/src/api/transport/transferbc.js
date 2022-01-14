import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/transport/hpmx/page',
    method: 'get',
    params: query
  })
}
export function transferAll(list,cb) {
    return request({
      url: '/transport/hpmx/transferAll',
      method: 'post',
      data: {list:list,cb:cb}
    })
  }

  export function backOne(bh) {
    return request({
      url: '/transport/hpmx/backHistory?bh='+bh,
      method: 'post',
    })
  }