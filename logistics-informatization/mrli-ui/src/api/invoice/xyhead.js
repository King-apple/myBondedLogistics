import request from '@/router/axios'


export function pageListOfSql(query) {
  return request({
    url: '/invoice/tfeeheadxy/pageListOfsql',
    method: 'get',
    params: query
  })
}

export function commitXy(obj) {
    return request({
      url: '/invoice/tfeeheadxy/updateXyList',
      method: 'post',
      data:obj
    })
  }
  

  export function fetchBodyList(id) {
    return request({
      url: '/invoice/tfeebodyxy/listByfeeBillNo?feeBillNo='+id,
      method: 'get',
    })
  }

  export function fetchBodyListByywdh(id) {
    return request({
      url: '/invoice/tfeebodyxy/listByfeeBillNo?ywdh='+id,
      method: 'get',
    })
  }


  export function deleteXy(id) {
    return request({
      url: '/invoice/tfeeheadxy/deleteXy?pkid='+id,
      method: 'post',
    })
  }
  export function hongchongXy2(obj) {
    return request({
      url: '/invoice/tfeeheadxy/hongchongXy2',
      method: 'post',
      data:obj
    })
  }
  export function hongchongXy(id) {
    return request({
      url: '/invoice/tfeeheadxy/hongchongXy?pkid='+id,
      method: 'post',
    })
  }

  export function fetchXyList(id) {
    return request({
      url: '/invoice/tfeeheadxy/list?pkid='+id,
      method: 'get',
    })
  }