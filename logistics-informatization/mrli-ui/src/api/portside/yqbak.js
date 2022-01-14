import request from '@/router/axios'

export function companyList(query) {
  return request({
    url: '/portside/yqcompany/page',
    method: 'get',
    params: query
  })
}
export function personList(query) {
  return request({
    url: '/portside/yqcompanyperson/page',
    method: 'get',
    params: query
  })
}
export function familyList(id) {
  return request({
    url: '/portside/yqcompanypersonfamily/listByExtId?extId='+id,
    method: 'get',
  })
}
export function veroList(id) {
  return request({
    url: '/portside/yqcompanypersonvero/listByExtId?extId='+id,
    method: 'get'
  })
}
export function addCompany(obj) {
    return request({
      url: '/portside/yqcompany/save',
      method: 'post',
      data: obj
    })
  }
  export function addPerson(obj,famliyList,ymList) {
    return request({
      url: '/portside/yqcompanyperson/save',
      method: 'post',
      data: {head:obj,famliyList:famliyList,ymList:ymList}
    })
  }
