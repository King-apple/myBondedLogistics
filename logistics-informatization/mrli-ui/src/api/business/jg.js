import request from '@/router/axios'

export function selectGet(query) {
  return request({
    url: '/business/fsstatus/page',
    method: 'get',
    params: query
  })
}

export function selectFeeBodyPageGet(query) {
    return request({
      url: '/business/fsstatus/feebody/page',
      method: 'get',
      params: query
    })
  }
  export function selectBodyGet(id) {
    return request({
      url: '/business/fsstatusbody/list?id='+id,
      method: 'get',
    })
  }
  export function selectContChangeGet(contNo,unitCod) {
    return request({
      url: '/business/fsstatus/contChange/list?contNo='+contNo+'&unitCod='+unitCod,
      method: 'get',
    })
  }
 