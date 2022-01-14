import request from '@/router/axios'

export function fetchList(query) {
    return request({
        url: '/account/cargoshipfeeyphead/page',
        method: 'get',
        params: query
    })
}
export function fetchBodyList(id) {
    return request({
        url: '/account/cargoshipfeeyphead/getListById?id=' + id,
        method: 'get'
    })
}
export function fetchCargoList(id,dno) {
  return request({
      url: '/account/cargoshipfeeyphead/getListById?id=' + id+'&dno='+dno,
      method: 'get'
  })
}
export function zdFee(id) {
  return request({
      url: '/account/cargoshipfeeyphead/zdfee?id=' + id,
      method: 'post'
  })
}
export function lockHead(id) {
    return request({
        url: '/yard/cargoshipfeeyphead/lock?informNo='+id,
        method: 'post',
    })
}
export function unlockHead(id) {
    return request({
        url: '/yard/cargoshipfeeyphead/unlock?informNo='+id,
        method: 'post',
    })
}
export function getCont(id) {
    return request({
      url: '/account/cargoshipfeeyphead/getCont?contNo='+id,
      method: 'get'
    })
  }
  export function contMxlist(id) {
    return request({
      url: '/account/cargoshipfeeyphead/contMxlist?contNo='+id,
      method: 'get'
    })
  }
  export function saveAll(head,body,type) {
    return request({
      url: '/account/cargoshipfeeyphead/saveShipFee',
      method: 'post',
      data: {head:head,body:body,saveType:type}
    })
  }
  export function getFeeBodyList(id) {
    return request({
      url: '/account/cargoshipfeeyphead/getFeeBodyList?feeBillNo='+id,
      method: 'get'
    })
  }
  
  export function checkObj(obj,type) {
    return request({
      url: '/account/cargoshipfeeyphead/chackJsd',
      method: 'post',
      data: {obj:obj,type:type}
    })
  }
  export function deleteObj(obj) {
    return request({
      url: '/account/cargoshipfeeyphead/deleteJsd',
      method: 'post',
      data: obj
    })
  }
  export function uploadToGs(obj) {
    return request({
      url: '/account/cargoshipfeeyphead/uploadToGs',
      method: 'post',
      data: obj
    })
  }