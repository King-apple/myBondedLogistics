
import request from '@/router/axios'

export function getPage(query) {
  return request({
    url: '/caccentre/tallyyd/getPage',
    method: 'get',
    params: query
  })
}