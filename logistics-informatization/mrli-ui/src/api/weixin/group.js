import request from '@/router/axios'

export function fetchList(query) {
    return request({
        url: '/portside/weixingroup/page',
        method: 'get',
        params: query
    })
}
export function bodyList(query) {
    return request({
        url: '/portside/weixinqxandgroup/page',
        method: 'get',
        params: query
    })
}
export function qxAndGroupList(query) {
    return request({
        url: '/portside/weixinqxandgroup/listByGroupId',
        method: 'get',
        params: query
    })
}
export function saveQx(query) {
    return request({
        url: '/portside/weixinqxandgroup/saveQx',
        method: 'post',
        params: query
    })
}
export function saveGroup(query) {
    return request({
        url: '/portside/weixingroup/save',
        method: 'post',
        data: query
    })
}