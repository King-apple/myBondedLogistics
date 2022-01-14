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

import request from "@/router/axios";

export function fetchList(query) {
  return request({
    url: "/baoshuisystem/yardinfo/page",
    method: "get",
    params: query
  });
}

export function addObj(obj) {
  return request({
    url: "/baoshuisystem/yardinfo",
    method: "post",
    data: obj
  });
}

export function getObj(id) {
  return request({
    url: "/baoshuisystem/yardinfo/" + id,
    method: "get"
  });
}

export function delObj(id) {
  return request({
    url: "/baoshuisystem/yardinfo/" + id,
    method: "delete"
  });
}

export function putObj(obj) {
  return request({
    url: "/baoshuisystem/yardinfo",
    method: "put",
    data: obj
  });
}

export function getPortArea() {
  return request({
    url: "/admin/dict/type/port_area",
    method: "get"
  });
}

export function allList() {
  return request({
    url: "/baoshuisystem/yardinfo/all",
    method: "get"
  });
}

export function cargoSpaceMerage(yardInfoList) {
  return request({
    url: "/baoshuisystem/yardinfo/cargoSpaceMerage",
    method: "post",
    data: yardInfoList
  });
}

export function cargoSpaceSplit(yardInfo) {
  return request({
    url: "/baoshuisystem/yardinfo/cargoSpaceSplit",
    method: "post",
    data: yardInfo
  });
}

export function getSelectConditionInfo() {
  return request({
    url: "/baoshuisystem/yardinfo/getSelectConditionInfo",
    method: "get",

  });
}