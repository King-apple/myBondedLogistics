/*
 *    Copyright (c) 2018-2025, mrli All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: mrli
 */

import request from '@/router/axios'

export function getMenu(id) {
  return request({
    url: '/admin/menu',
    params: {parentId: id},
    method: 'get'
  })
}
export function getDataRole(id) {
  return request({
    url: '/admin/sysroledata/page?menuId='+id,
    method: 'get'
  })
}
export function getUserDataRole(id) {
  return request({
    url: '/admin/sysroledata/sysroleList?roleId='+id,
    method: 'get'
  })
}
export function addDataRoleObj(obj) {
  return request({
    url: '/admin/sysroledata',
    method: 'post',
    data: obj
  })
}
export function delDataRoleObj(id) {
  return request({
    url: '/admin/sysroledata/' + id,
    method: 'delete'
  })
}
export function getTopMenu() {
  return request({
    url: '/admin/menu',
    params: {type: 'top'},
    method: 'get'
  })
}

export function fetchMenuTree(lazy, parentId) {
  return request({
    url: '/admin/menu/tree',
    method: 'get',
    params: {lazy: lazy, parentId: parentId}
  })
}
export function fetchMenuTreeOfData(lazy, parentId) {
  return request({
    url: '/admin/menu/treeofDataRole',
    method: 'get',
    params: {lazy: lazy, parentId: parentId}
  })
}
export function addObjOfData(obj) {
  return request({
    url: '/admin/sysroledata/savetodata',
    method: 'post',
    data: obj
  })
}
export function addObj(obj) {
  return request({
    url: '/admin/menu',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return request({
    url: '/admin/menu/' + id,
    method: 'get'
  })
}

export function delObj(id) {
  return request({
    url: '/admin/menu/' + id,
    method: 'delete'
  })
}

export function putObj(obj) {
  return request({
    url: '/admin/menu',
    method: 'put',
    data: obj
  })
}
