import request from '@/router/axios'

export function fetchList(query) {
    return request({
        url: '/yard/mioyardhead/page',
        method: 'get',
        params: query
    })
}
export function fetchBodyList(id) {
    return request({
        url: '/yard/mioyardbody/getListById?id=' + id,
        method: 'get'
    })
}
export function saveHead(obj) {
    return request({
        url: '/yard/mioyardhead/save',
        method: 'post',
        data: obj
    })
}
export function saveBody(id) {
    return request({
        url: '/yard/mioyardbody/putData?informNo=' + id,
        method: 'post',
    })
}
export function saveBodyData(obj) {
    return request({
        url: '/yard/mioyardbody/saveall',
        method: 'post',
        data: { body: obj }
    })
}
export function lockHead(id) {
    return request({
        url: '/yard/mioyardhead/lock?informNo=' + id,
        method: 'post',
    })
}
export function unlockHead(id) {
    return request({
        url: '/yard/mioyardhead/unlock?informNo=' + id,
        method: 'post',
    })
}
export function csPost() {
    return request({
        url: '/yard/mioyardhead/getcs',
        method: 'post',
    })
}
export function excelOutPost(id) {
    return request({
        url: '/yard/mioyardhead/excelOut?informNo=' + id,
        method: 'post',
        responseType: 'arraybuffer'
    }).then((response) => { // 处理返回的文件流
        const blob = new Blob([response.data], {type: 'application/xls'})
        const filename = id + '.xls'
        const link = document.createElement('a')
        link.href = URL.createObjectURL(blob)
        link.download = filename
        document.body.appendChild(link)
        link.click()
        window.setTimeout(function () {
          URL.revokeObjectURL(blob)
          document.body.removeChild(link)
        }, 0)
    })
}
