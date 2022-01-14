
import request from '@/router/axios'

export function getPage(query) {
  return request({
    url: '/trainofgr/ctnnotrack/getPage',
    method: 'get',
    params: query
  })
}