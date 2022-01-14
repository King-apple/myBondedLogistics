import request from '@/router/axios'

export function pageList(query) {
  return request({
    url: '/caccentre/shipperdoc/pageList',
    method: 'get',
    params: query
  })
}
export function pageListOfSql(query) {
  return request({
    url: '/caccentre/shipperdoc/pageListOfsql',
    method: 'get',
    params: query
  })
}