
import request from '@/router/axios'

export function getPage(query) {
  return request({
    url: '/caccentre/onwaytrack/getPage',
    method: 'get',
    params: query
  })
}