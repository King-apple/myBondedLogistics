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
    url: "/baoshuisystem/instrogedetail/page",
    method: "get",
    params: query
  });
}

export function getOne(bh) {
  return request({
    url: "/baoshuisystem/instrogedetail/" + bh,
    method: "get"
  });
}

export function upImage(inStrogeDetail) {
  return request({
    url: "/baoshuisystem/instrogedetail/upImage",
    method: "post",
    header: {
      "content-type": "application/x-www-form-urlencoded"
    },
    data: inStrogeDetail
  });
}

export function destroyImage(inStrogeDetail) {
  return request({
    url: "/baoshuisystem/instrogedetail/destroyImage",
    method: "post",
    header: {
      "content-type": "application/x-www-form-urlencoded"
    },
    data: inStrogeDetail
  });
}

export function getInyardType() {
  return request({
    url: "/admin/dict/type/inyard_type",
    method: "get"
  });
}

export function getOutyardType() {
  return request({
    url: "/admin/dict/type/outyard_type",
    method: "get"
  });
}

export function instrfetchList(query) {
  return request({
    url: "/baoshuisystem/instrogedetail/instr/page",
    method: "get",
    params: query
  });
}

export function getBcmcx(bh) {
  return request({
    url: "/baoshuisystem/instrogedetail/getBcmcx/" + bh,
    method: "get"
  });
}

export function submitOverShortCheck(inStrogeDetail) {
  return request({
    url: "/baoshuisystem/instrogedetail/submitOverShortCheck",
    method: "post",
    data: inStrogeDetail
  });
}

export function getOverShortCargo(query) {
  return request({
    url: "/baoshuisystem/instrogedetail/getOverShortCargo",
    method: "get",
    params: query
  });
}

export function submitCheck(inStrogeDetail) {
  return request({
    url: "/baoshuisystem/instrogedetail/submitCheck",
    method: "post",
    data: inStrogeDetail
  });
}

export function getOverShortMax() {
  return request({
    url: "/baoshuisystem/instrogedetail/getOverShortMax",
    method: "get",
  });
}