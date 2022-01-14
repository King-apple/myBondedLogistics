import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/baoshuisystem/companyshipnightplan/page',
    method: 'get',
    params: query
  })
}

export function getUnitNam() {
  return request({
    url: '/baoshuisystem/companyshipnightplan/getUnitNam',
    method: 'get',
  })
}


export function geteHeadMessag(data) {
  return request({
    url:'/baoshuisystem/companyshipnightplan/getHeadMessage',
    method: 'post',
    data:data
  })
}