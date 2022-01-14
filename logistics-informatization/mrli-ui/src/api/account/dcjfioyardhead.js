import request from '@/router/axios'

export function fetchList(query) {
    return request({
        url: '/account/dcjfioyardhead/page',
        method: 'get',
        params: query
    })
}
export function fetchBodyList(id) {
    return request({
        url: '/account/dcjfioyardbody/getListById?id=' + id,
        method: 'get'
    })
}
export function fetchCargoList(id,dno) {
  return request({
      url: '/account/dccargofeebody/getListById?id=' + id+'&dno='+dno,
      method: 'get'
  })
}
export function zdFee(id) {
  return request({
      url: '/account/dcjfioyardhead/zdfee?id=' + id,
      method: 'post'
  })
}
export function lockHead(id) {
    return request({
        url: '/yard/mioyardhead/lock?informNo='+id,
        method: 'post',
    })
}
export function unlockHead(id) {
    return request({
        url: '/yard/mioyardhead/unlock?informNo='+id,
        method: 'post',
    })
}