import request from '@/router/axios'
export function fetchList(query) {
    return request({
      url: '/caccentre/zyworkresourcescarhead/page',
      method: 'get',
      params: query
    })
  }