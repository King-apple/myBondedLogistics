import request from '@/router/axios'


export function fetchList(query) {
  return request({
    url: '/ship/shipagent/page',
    method: 'get',
    params: query
  })
}
export function saveObj(obj) {
  return request({
    url: '/ship/shipagent',
    method: 'post',
    data: obj
  })
}