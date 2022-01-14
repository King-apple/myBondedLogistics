
import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/caccentre/ship/getPage',
    method: 'get',
    params: query
  })
}
export function fetchBodyList(id) {
  return request({
    url: '/caccentre/shipstatuszs/listById?id='+id,
    method: 'get'
  })
}

export function getList(query) {
  return request({
    url: '/caccentre/ship/getShipNo',
    method: 'get',
    params: query
  })
}

