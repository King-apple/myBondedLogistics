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
    url: "/baoshuisystem/checkweight/page",
    method: "get",
    params: query
  });
}

export function addObj(obj) {
  return request({
    url: "/baoshuisystem/checkweight",
    method: "post",
    data: obj
  });
}

export function getObj(id) {
  return request({
    url: "/baoshuisystem/checkweight/" + id,
    method: "get"
  });
}

export function delObj(checkNo) {
  return request({
    url: "/baoshuisystem/checkweight/" + checkNo,
    method: "delete"
  });
}

export function putObj(obj) {
  return request({
    url: "/baoshuisystem/checkweight",
    method: "put",
    data: obj
  });
}

export function checkweight(unitCod) {
  return request({
    url: "/baoshuisystem/checkweight/autoCreate/" + unitCod,
    method: "get"
  });
}

export function getCheckNo(checkNo) {
  return request({
    url: "/baoshuisystem/checkweight/getCheckNo/" + checkNo,
    method: "get"
  });
}

export function delObjForCheckNo(obj) {
  return request({
    url: "/baoshuisystem/checkweight/delObjForCheckNo/",
    method: "delete",
    data: obj
  });
}
