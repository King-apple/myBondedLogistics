import request from '@/router/axios'

export function fetchList(query) {
    return request({
        url: '/portside/weixinqx/page',
        method: 'get',
        params: query
    })
}
export function addCommit(obj) {
    return request({
        url: '/portside/weixinqx/edit',
        method: 'post',
        data: obj
    })
}