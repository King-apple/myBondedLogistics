
import request from '@/router/axios'

export function getPage(query) {
  return request({
    url: '/statistical/tradetime/getPage',
    method: 'get',
    params: query
  })
}