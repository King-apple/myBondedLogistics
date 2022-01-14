
import request from '@/router/axios'

export function getPage(query) {
  return request({
    url: '/caccentre/rmmessage/getPage',
    method: 'get',
    params: query
  })
}