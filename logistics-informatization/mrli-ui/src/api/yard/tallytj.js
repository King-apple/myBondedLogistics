import request from '@/router/axios'

export function fetchList(query) {
    return request({
        url: '/yard/tallyTj/shiptj',
        method: 'get',
        params: query
    })
}