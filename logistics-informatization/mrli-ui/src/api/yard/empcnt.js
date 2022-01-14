import request from '@/router/axios'

export function fetchList(query) {
    return request({
        url: '/yard/etworkcntemp/page',
        method: 'get',
        params: query
    })
}
export function changCnt(list,status) {
    return request({
        url: '/yard/etworkcntemp/changcnt',
        method: 'post',
        data: {list:list,status:status}
    })
}
export function saveHead(obj) {
    return request({
        url: '/yard/mioyardhead/save',
        method: 'post',
        data: obj
    })
}