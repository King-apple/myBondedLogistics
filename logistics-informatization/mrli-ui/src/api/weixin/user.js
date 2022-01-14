import request from '@/router/axios'

export function fetchList(query) {
    return request({
        url: '/portside/weixinuser/page',
        method: 'get',
        params: query
    })
}
export function userAndGroupList(query) {
    return request({
        url: '/portside/weixinuserandgroup/listByUserId',
        method: 'get',
        params: query
    })
}
export function saveGroup(query) {
    return request({
        url: '/portside/weixinuserandgroup/save',
        method: 'post',
        params: query
    })
}