
import request from '@/router/axios'

export function getPage(query) {
  return request({
    url: '/caccentre/traintrack/getPage',
    method: 'get',
    params: query
  })
}