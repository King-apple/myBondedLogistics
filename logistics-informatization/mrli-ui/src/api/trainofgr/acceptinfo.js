
import request from '@/router/axios'

export function getPage(query) {
  return request({
    url: '/trainofgr/acceptinfo/getPage',
    method: 'get',
    params: query
  })
}