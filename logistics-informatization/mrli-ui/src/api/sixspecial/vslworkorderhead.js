import request from '@/router/axios'

 
export function ListH(query) {
    return request({
        url: '/sixspecial/vslworkorderhead/page',
        method: 'get',
        params: query
    })
}
export function listShip(query) {
  return request({
      url: '/sixspecial/vslworkorderhead/listShip',
      method: 'get',
      params: query
  })
}

export function save(obja,objb) {
    return request({
        url: '/sixspecial/vslworkorderhead/save',
        method: 'post',
        data: { head:obja,body: objb }
    })
}

export function wjjb(query) {
    return request({
        url: '/sixspecial/vslworkorderhead/wjjb',
        method: 'post',
        data: query
    })
}
export function commitedi(query) {
  return request({
      url: '/sixspecial/vslworkorderhead/commitedi',
      method: 'post',
      data: query
  })
}
export function wjjbOfShip(query,list) {
  return request({
      url: '/sixspecial/vslworkorderhead/wjjbOfShip',
      method: 'post',
      data: {head:query,shipList:list}
  })
}
export function saveAll(ship,shipTon) {
    return request({
      url: '/sixspecial/vslworkorderhead/saveAll',
      method: 'post',
      data: {ship:ship,shipTon:shipTon}
    })
  }

  export function shipList(query) {
    return request({
      url: '/sixspecial/vslworkorderhead/getShip',
      method: 'get',
      params: query
    })
  }

  export function hc(query) {
    return request({
        url: '/sixspecial/vslworkorderhead/hc',
        method: 'post',
        params: query
    })
  }