import request from '@/router/axios'

export function fetchList(jlh) {
  return request({
    url: '/yard/blockchain/page?jlh='+jlh,
    method: 'get',
  })
}