import request from '@/router/axios'
export function futherList(query) {
    return request({
      url: '/caccentre/weatherfuture/listAll',
      method: 'get',
      params: query
    })
  }

  export function selectWarning() {
    return request({
      url: '/caccentre/weatherwarning/getWarningOfLast',
      method: 'get',
    })
  }