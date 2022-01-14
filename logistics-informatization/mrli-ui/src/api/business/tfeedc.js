import request from '@/router/axios'

export function fetchList(query) {
  return request({
    url: '/business/tfeedc/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return request({
    url: '/business/tfeedc',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/business/tfeedc/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/business/tfeedc/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/business/tfeedc',
    method: 'put',
    data: obj
  })
}

export function getUnit(query){
  return request({
    url: '/basecode/basecod/getUnit',
    method: 'get' ,
    params: query
  })
}