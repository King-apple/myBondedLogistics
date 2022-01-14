import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/baoshuisystem/outstandinginvoice/page',
    method: 'get',
    params: query
  })
}

export function getGroup() {
    return request({
      url: '/baoshuisystem/outstandinginvoice/getGroup',
      method: 'get',
    })
  }