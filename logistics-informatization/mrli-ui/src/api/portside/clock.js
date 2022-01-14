import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/portside/personclock/page',
    method: 'get',
    params: query
  })
}
