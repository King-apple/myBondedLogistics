import request from '@/router/axios'

export function fetchList(query) {
    return request({
        url: '/portside/weixinqxroute/page',
        method: 'get',
        params: query
    })
}
export function fetchBodyList(query) {
    return request({
        url: '/portside/weixinqxroutemx/page',
        method: 'get',
        params: query
    })
}
export function addCommit(obj) {
    return request({
        url: '/portside/weixinqxroute/edit',
        method: 'post',
        data: obj
    })
}
export function routeAndGroupList(query) {
    return request({
        url: '/portside/weixinqxroutemx/list',
        method: 'get',
        params: query
    })
}
export function saveGroup(query) {
    return request({
        url: '/portside/weixinqxroutemx/saveQx',
        method: 'post',
        params: query
    })
}