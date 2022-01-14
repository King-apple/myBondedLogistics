import request from '@/router/axios'

export function classList(query) {
    return request({
        url: '/dispatch/dayrecord/listbz',
        method: 'get',
        params: query
    })
}

export function fetchList(query) {
    return request({
        url: '/dispatch/dayrecord/list',
        method: 'get',
        params: query
    })
}

export function sjcl(query) {
    return request({
        url: '/dispatch/dayrecord/sjcl',
        method: 'get',
        params: query
    })
  }
  export function sjpd(query) {
    return request({
        url: '/dispatch/dayrecord/sjpd',
        method: 'get',
        params: query
    })
  }

export function save(obj) {
    return request({
        url: '/dispatch/dayrecord/saveall',
        method: 'post',
        data: { body: obj }
    }) 
}