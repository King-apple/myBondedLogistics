
import request from '@/router/axios'

export function getPage(query) {
  return request({
    url: '/trainofgr/loadinfo/getPage',
    method: 'get',
    params: query
  })
}